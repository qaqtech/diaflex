package ft.com.assorthk;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.Mprc;

import java.io.IOException;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class DeptSmryAction extends DispatchAction {
    
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg="sucess";
        if(info!=null){  
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        rtnPg=init(req,res,session,util);
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,res,"Dept Smry", "load start");
     String query ="select c.srt, 'AV' flg, c.val dept, a.prc, a.idn,  count(*) qty ,   to_char(sum(trunc(cts,2)),'9999999990.00') cts   "+
                   " from mprc a, mstk b, stk_dtl c "+
                   " where a.in_stt = b.stt and b.idn = c.mstk_idn and c.grp = 1 and c.mprp = 'DEPT' "+
                   " group by c.srt, 'AV', c.val, a.prc, a.idn "+
                   " UNION "+
                   " select c.srt, 'IS' flg, c.val dept, a.prc, a.idn , count(*) qty,  to_char(sum(trunc(cts,2)),'999999999990.00') cts"+
                   " from mprc a, mstk b, stk_dtl c "+
                   " where a.is_stt = b.stt and b.idn = c.mstk_idn and c.grp = 1 and c.mprp = 'DEPT' "+
                  " group by c.val, c.srt, a.prc , a.idn "+
                  "union\n" + 
                  "select c.srt, 'IS' flg, c.val dept, 'Lab Confirm' prc, null idn , count(*) qty,  to_char(sum(trunc(cts,2)),'999999999990.00') cts \n" + 
                  "from mstk b, stk_dtl c\n" + 
                  "where b.idn = c.mstk_idn and c.grp = 1 and c.mprp = 'DEPT' and b.stt in ('LB_CF')\n" + 
                  "group by c.val, c.srt\n" + 
                  "union\n" + 
                  "select c.srt, 'IS' flg, c.val dept, 'Lab Confirm Resend' prc, null idn , count(*) qty,  to_char(sum(trunc(cts,2)),'999999999990.00') cts \n" + 
                  "from mstk b, stk_dtl c\n" + 
                  "where b.idn = c.mstk_idn and c.grp = 1 and c.mprp = 'DEPT' and b.stt in ('LB_CFRS')\n" + 
                  "group by c.val, c.srt"+ 
                  " order by 1, 2 ";
      
     HashMap pktDtl = new HashMap();
     ResultSet rs = db.execSql("Get process", query, new ArrayList());
      while(rs.next()) {
           String dept = util.nvl(rs.getString("dept"));
          String prcId = util.nvl(rs.getString("idn"));
          String qty = util.nvl(rs.getString("qty"));
          String cts = util.nvl(rs.getString("cts"));
          String flg = util.nvl(rs.getString("flg"));
          String key = dept+"_"+prcId+"_"+flg;
          pktDtl.put(key+"_QTY",qty);
          pktDtl.put(key+"_CTS",cts);
          
     }
        rs.close();
      req.setAttribute("pktDtlList", pktDtl);
        ArrayList ary = new ArrayList();
      String usrFlg = info.getUsrFlg();
      String deptSql = " select a.dept dept from  mdept a , dept_emp_valid b where a.idn = b.dept_idn\n" + 
                        " and b.emp_idn=? and b.vld_dte is null and b.stt='A' ";
      ary = new ArrayList();
      ary.add(String.valueOf(info.getDfNmeIdn()));
      if(usrFlg.equals("SYS")){
          deptSql = "select a.val dept from prp a where mprp = 'DEPT' and val <> 'NA' and vld_till is null order by srt " ;
                           
          ary = new ArrayList();
      }
      rs = db.execSql("deptList", deptSql, ary);
      ArrayList deptList = new ArrayList();
      while(rs.next()){
          deptList.add(util.nvl(rs.getString("dept")));
      }
        rs.close();
     req.setAttribute("deptList", deptList);
     ArrayList mprcList = new ArrayList();
     String mprcSql = "select idn , prc from mprc where vld_till is null and stt='A' order by srt ";
     rs = db.execSql("mprcSql", mprcSql, new ArrayList());
     while(rs.next()){
         Mprc prc = new Mprc();
         prc.setMprcId(rs.getString("idn"));
         prc.setPrc(rs.getString("prc"));
         mprcList.add(prc);
     }
        rs.close();
     req.setAttribute("mprcList", mprcList);
            util.updAccessLog(req,res,"Dept Smry", "load end");
      return am.findForward("load");
        }
    }
      
    public String init(HttpServletRequest req , HttpServletResponse res,HttpSession session,DBUtil util) {
            String rtnPg="sucess";
            String invalide="";
            String connExists=util.nvl(util.getConnExists());  
            if(!connExists.equals("N"))
            invalide=util.nvl(util.chkTimeOut(),"N");
            if(session.isNew())
            rtnPg="sessionTO";    
            if(connExists.equals("N"))
            rtnPg="connExists";     
            if(invalide.equals("Y"))
            rtnPg="chktimeout";
            if(rtnPg.equals("sucess")){
            boolean sout=util.getLoginsession(req,res,session.getId());
            if(!sout){
            rtnPg="sessionTO";
            System.out.print("New Session Id :="+session.getId());
            }else{
                util.updAccessLog(req,res,"Dept Smry", "init");
            }
            }
            return rtnPg;
            }
    
    public DeptSmryAction() {
        super();
    }
}
