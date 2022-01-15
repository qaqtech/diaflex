package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AssortValueReport extends DispatchAction {
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
         HttpSession session = req.getSession(false);
         InfoMgr info = (InfoMgr)session.getAttribute("info");
         GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
             AssortLabPendingForm udf = (AssortLabPendingForm)af;

             String isRest = util.nvl(req.getParameter("isRest"));
             if(isRest.equals("YES")){
                 udf.resetAll();
             }
             String dteFrm = util.nvl((String)udf.getValue("dteFrm"));
             String dteTo = util.nvl((String)udf.getValue("dteTo"));
             ArrayList dataDtl = new ArrayList();
             if(!dteFrm.equals(""))
                dteFrm = "to_date('"+dteFrm+"' , 'dd-mm-yyyy')";
             else
                dteFrm = " trunc(sysdate) ";
                
            if(!dteTo.equals(""))
             dteTo = "to_date('"+dteTo+"' , 'dd-mm-yyyy')";
            else
             dteTo = " trunc(sysdate) ";
             
             String query = "select " + 
             "       sd.val boxTyp , sd.srt\n" + 
             "         ,  to_char(sum(m.cts*decode(ird.flg1, 'ASRT_MX', 1, 0)),'999990.00') newCts\n" + 
             "         ,  to_char(sum(m.cts*decode(ird.flg1, 'ASRT_MX', 0, 1)),'999990.00') oldCts\n" + 
             "         ,  to_char(sum(m.cts*nvl(m.upr, m.cmp)*decode(ird.flg1, 'ASRT_MX', 1, 0)),'999,990.00') newVal\n" + 
             "         ,  to_char(sum(m.cts*nvl(m.upr, m.cmp)*decode(ird.flg1, 'ASRT_MX', 0, 1)),'999,990.00') oldVal\n" + 
             "          , to_char(sum(m.cts*nvl(m.upr, m.cmp)*decode(ird.flg1, 'ASRT_MX', 1, 0))-sum(m.cts*nvl(m.upr, m.cmp)*decode(ird.flg1, 'ASRT_MX', 0,1)) ,'999,990.00') diff "+
             "       from iss_rtn_dtl ird, mstk m, stk_dtl sd , iss_rtn ir\n" + 
             "     where ird.iss_stk_idn = m.idn  \n" + 
             "     and m.pkt_ty <> 'NR' and ird.iss_id = ir.iss_id " +
             "     and  trunc(ir.iss_dt)      between "+dteFrm+" and "+dteTo+""+
             "       and m.idn = sd.mstk_idn and sd.grp = 1 and sd.mprp = 'BOX_TYP'  \n" + 
             "       group by sd.val,sd.srt\n" + 
             "       order by sd.srt";
             ArrayList rsLst = db.execSqlLst("issIdnLst", query, new ArrayList());
             PreparedStatement pst = (PreparedStatement)rsLst.get(0);
             ResultSet rs = (ResultSet)rsLst.get(1);
             while(rs.next()){
             HashMap dtls = new HashMap();
             dtls.put("BOXTYP", rs.getString("boxTyp"));
             dtls.put("RTNCTS", rs.getString("newCts"));
             dtls.put("ISSCTS", rs.getString("oldCts"));
              dtls.put("DIFF",rs.getString("diff"));
             dtls.put("RTNVAL", rs.getString("newVal"));
             dtls.put("ISSVAL", rs.getString("oldVal"));
             dataDtl.add(dtls);
             }
             rs.close();
             pst.close();
             String lstNme = "ASSDIFF_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
             gtMgr.setValue("lstNmeASSDIFF", lstNme);
             gtMgr.setValue(lstNme, dataDtl);
             return am.findForward("load");  
         }
        }
    public ActionForward BackToRPT(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
                
        return am.findForward("load");  
    }
    
    public ActionForward loadBoxId(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
         HttpSession session = req.getSession(false);
         InfoMgr info = (InfoMgr)session.getAttribute("info");
         GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
             
             AssortLabPendingForm udf = (AssortLabPendingForm)af;
             String dteFrm = util.nvl((String)udf.getValue("dteFrm"));
             String dteTo = util.nvl((String)udf.getValue("dteTo"));
             ArrayList dataDtl = new ArrayList();
                 if(!dteFrm.equals(""))
                    dteFrm = "to_date('"+dteFrm+"' , 'dd-mm-yyyy')";
                 else
                    dteFrm = " trunc(sysdate) ";
                    
                 if(!dteTo.equals(""))
                 dteTo = "to_date('"+dteTo+"' , 'dd-mm-yyyy')";
                 else
                 dteTo = " trunc(sysdate) ";
             
             
             String boxTyp = util.nvl(req.getParameter("boxTyp"));
             String query = "select \n" + 
             "       sd.val boxTyp, bi.val BoxId\n" + 
             "         , to_char(sum(m.cts*decode(ird.flg1, 'ASRT_MX', 1, 0)),'999990.00') newCts\n" + 
             "         , to_char(sum(m.cts*decode(ird.flg1, 'RE_ASRT', 1, 0)),'999990.00') oldCts\n" + 
             "         , to_char(sum(m.cts*nvl(m.upr, m.cmp)*decode(ird.flg1, 'ASRT_MX', 1, 0)),'999,990.00') newVal\n" + 
             "         , to_char(sum(m.cts*nvl(m.upr, m.cmp)*decode(ird.flg1, 'RE_ASRT', 1, 0)),'999,990.00') oldVal\n" + 
            "         ,to_char(sum(m.cts*nvl(m.upr, m.cmp)*decode(ird.flg1, 'ASRT_MX', 1, 0))- sum(m.cts*nvl(m.upr, m.cmp)*decode(ird.flg1, 'RE_ASRT', 1, 0)),'999,990.00') diff "+
             "       from iss_rtn_dtl ird, mstk m, stk_dtl sd, stk_dtl bi, iss_rtn ir\n" + 
             "     where ird.iss_stk_idn = m.idn and ir.iss_id=ird.iss_id \n" + 
             "    and  trunc(ir.iss_dt)    between "+dteFrm+" and "+dteTo+""+
             "       and m.idn = sd.mstk_idn and sd.grp = 1 and sd.mprp = 'BOX_TYP' and sd.val = ?  \n" + 
             "       and m.idn = bi.mstk_idn and bi.grp = 1 and bi.mprp = 'BOX_ID'  \n" + 
             "       group by sd.val, bi.val";
             ArrayList ary = new ArrayList();
             ary.add(boxTyp);
         ArrayList rsLst = db.execSqlLst("issIdnLst", query, ary);
             PreparedStatement pst = (PreparedStatement)rsLst.get(0);
             ResultSet rs = (ResultSet)rsLst.get(1);
             while(rs.next()){
             HashMap dtls = new HashMap();
              String boxId =rs.getString("BoxId");
              boxId = boxId.replace(boxTyp+"-", "");
             dtls.put("BOXID",boxId);
             dtls.put("RTNCTS", rs.getString("newCts"));
             dtls.put("ISSCTS", rs.getString("oldCts"));
              dtls.put("DIFF",rs.getString("diff"));
             dtls.put("RTNVAL", rs.getString("newVal"));
             dtls.put("ISSVAL", rs.getString("oldVal"));
             dataDtl.add(dtls);
             }
             rs.close();
             pst.close();
             String lstNme = (String)gtMgr.getValue("lstNmeASSDIFF");
             gtMgr.setValue(lstNme+"_BOXID",dataDtl);
             udf.setValue("boxtyp", boxTyp);
             return am.findForward("loadBoxId");  
         }
        }
    
    
    
    public AssortValueReport() {
        super();
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
                util.updAccessLog(req,res,"Assort Lab Pending", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Assort Lab Pending", "init");
            }
            }
            return rtnPg;
    }
}
