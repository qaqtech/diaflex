package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class UpdateFinalExchangeRate extends DispatchAction {
    public UpdateFinalExchangeRate() {
        super();
    }
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr();
            String rtnPg="sucess";
            if(info!=null){
            Connection conn=info.getCon();
            if(conn!=null){
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            rtnPg=init(req,res,session,util);
            }else{
            rtnPg="connExists";   
            }
            }else
            rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,res,"Update Final Exchange Rate", "load");
        UpdateFinalExChangeRateForm udf = (UpdateFinalExChangeRateForm)af;
            udf.reset();
            return am.findForward("load");
        }
        }
    public ActionForward Fetch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg="sucess";
        if(info!=null){
        Connection conn=info.getCon();
        if(conn!=null){
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        rtnPg=init(req,res,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,res,"Update Final Exchange Rate", "fetch");
        UpdateFinalExChangeRateForm udf = (UpdateFinalExChangeRateForm)af;
          
        HashMap memoDtl = new HashMap();
        ArrayList memoLst =new ArrayList();
        String memoNo = util.nvl((String)udf.getValue("memoIdn"));
        String dfr = util.nvl((String)udf.getValue("frmDte"));
        String dto = util.nvl((String)udf.getValue("toDte"));
        String conQ=" ";

        memoNo = util.getVnm(memoNo);
            if(!memoNo.equals("")){
            conQ+= " and a.idn in("+memoNo+") ";
            }else if(!dfr.equals("") && !dto.equals("")){
                conQ+= " and trunc(a.dte) between to_date('"+dfr+"' , 'dd-mm-yyyy') and to_date('"+dto+"' , 'dd-mm-yyyy') ";    
            }else{
                conQ+= " and trunc(a.dte)=trunc(sysdate)";
            }
        String srchQ = "select a.idn , a.typ , sum(b.qty) qty , sum(b.cts) cts ,  to_char(sum(b.cts*nvl(b.fnl_sal,b.quot)),'999999999990.00') vlu ,to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte,a.exh_rte,a.fnl_xrt   from mjan a , jandtl b\n" + 
       "where 1=1 and a.idn=b.idn and b.typ in('IAP','WAP','EAP','BAP','HAP','BAP','KAP','BIDAP','OAP') and b.stt not in ('RT','APRT','WART') "+conQ;
            srchQ+=" group by a.idn, a.typ, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss'), A.Exh_Rte, A.Fnl_Xrt order by a.idn"; 
          ArrayList outLst = db.execSqlLst(" Memo Lst ",srchQ ,new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            try {
                while(rs.next()) {
                    memoDtl = new HashMap();
                    memoDtl.put("MemoId",util.nvl(rs.getString("idn")));
                    memoDtl.put("Type",util.nvl(rs.getString("typ")));
                    memoDtl.put("Qty",util.nvl(rs.getString("qty")));
                    memoDtl.put("Cts",util.nvl(rs.getString("cts")));
                    memoDtl.put("Value",util.nvl(rs.getString("vlu")));
                    memoDtl.put("Date",util.nvl(rs.getString("dte")));
                    memoDtl.put("exh_rte",util.nvl(rs.getString("exh_rte")));
                    memoDtl.put("fnl_xrt",util.nvl(rs.getString("fnl_xrt")));
                    memoLst.add(memoDtl);
                
                }
                rs.close();
                pst.close();
              
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            udf.reset(); 
        session.setAttribute("MemoLst", memoLst);
        req.setAttribute("view", "Y");
        return am.findForward("load");
        }
    }
    public ActionForward update(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg="sucess";
        if(info!=null){
        Connection conn=info.getCon();
        if(conn!=null){
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        rtnPg=init(req,res,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
         util.updAccessLog(req,res,"Update Final Exchange Rate", "upload");
        UpdateFinalExChangeRateForm udf = (UpdateFinalExChangeRateForm)af;
            boolean msg=false;
            ArrayList memoLst = (ArrayList)session.getAttribute("MemoLst");
            ArrayList   params = new ArrayList();
            String delQ = " delete from  gt_pkt  ";
            int ct =db.execUpd(" Del  Pkts ", delQ, new ArrayList());
                    for(int i=0;i< memoLst.size();i++){
                        HashMap stockPkt = (HashMap)memoLst.get(i);
                         String MemoId = util.nvl((String)stockPkt.get("MemoId"));
                         String isChecked = util.nvl((String)udf.getValue(MemoId));
                        if(isChecked.equals("yes")){    
                          String fnlexhFld = util.nvl((String)udf.getValue("FNLEXH_"+MemoId));
                          if(fnlexhFld.equals("")){
                          params = new ArrayList();
                          params.add(MemoId);
                          params.add("Z");
                          String insrtQry = "  insert into gt_pkt(mstk_idn,flg) select ? , ? from dual";
                          ct = db.execUpd(" insert gt_pkt ",insrtQry , params);    
                          }else{
                              params = new ArrayList();
                              params.add(fnlexhFld);
                              params.add(MemoId);
                              String insrtQry = "  update mjan set fnl_xrt=? where idn=?";
                              ct = db.execUpd(" insert gt_pkt ",insrtQry , params);  
                          }
                           if(!msg)
                           msg=true; 
                       }          
                   }
         if(msg){
         ct=  db.execCall(" memo_pkg.upd_exh_rte ", "memo_pkg.upd_exh_rte", new ArrayList());
         if(ct>0){
             req.setAttribute("msg", "Exchange Rate Updated Sussesfully....");
         } else{
         req.setAttribute("msg", "Exchange Rate Not  Updataion Failed Please Try Again.. ");
         }
         }
         udf.reset();
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
                rtnPg=util.checkUserPageRights("",util.getFullURL(req));
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Update Final Exchange Rate", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Update Final Exchange Rate", "init");
            }
            }
            return rtnPg;
            }
}
