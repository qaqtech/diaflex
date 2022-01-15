package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class DailyLabIssueReceive extends DispatchAction {
   
    public DailyLabIssueReceive() {
        super();
    }

    public ActionForward load(ActionMapping am, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
      util.updAccessLog(req,res,"Daily Lab Issue Receive", "load start");
      DailyLabIssueReceiveForm dailylabissrtnForm = (DailyLabIssueReceiveForm)form;
      dailylabissrtnForm.reset();
      dailylabissrtnForm.setValue("typ", "IS");
             util.updAccessLog(req,res,"Daily Lab Issue Receive", "load end");
       return am.findForward("load");
         }
     }
    
    public ActionForward fetch(ActionMapping am, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
       util.updAccessLog(req,res,"Daily Lab Issue Receive", "fetch start");
       DailyLabIssueReceiveForm dailylabissrtnForm = (DailyLabIssueReceiveForm)form;
       
       String typ = util.nvl((String)dailylabissrtnForm.getValue("typ"));
       String frmdate = util.nvl((String)dailylabissrtnForm.getValue("frmdate"));
       String todate = util.nvl((String)dailylabissrtnForm.getValue("todate"));
       String dept = util.nvl((String)dailylabissrtnForm.getValue("dept"));
       String rpHdr = "Daily Lab Report";
       ArrayList params = new ArrayList();
       
       String delQ = " Delete from gt_srch_multi ";
       int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
       String gtInsert = " insert into gt_srch_multi (cert_lab , cts , quot , stk_idn) " +
       "select upper(REPLACE(e.nme,'Lab-','')) nme , a.cts , nvl(f.num ,nvl(a.upr,a.cmp)) , a.idn from mstk a , iss_rtn b , iss_rtn_dtl c,stk_dtl d , nme_v e, stk_dtl f " + 
       "where  b.iss_id = c.iss_id and c.iss_stk_idn = a.idn and c.iss_stk_idn=d.mstk_idn and b.emp_id = e.nme_idn " + 
       " and f.mstk_idn = d.mstk_idn and f.mprp='FA_PRI' and f.grp=1"+
       " and a.idn = d.mstk_idn and d.mprp='DEPT' and d.grp=1 ";
       if(dept.length()>0){
           gtInsert = gtInsert +" and d.val = ? ";
           params.add(dept);
       }
       if(typ.equals("IS")){
       rpHdr = rpHdr+"(Issue)";
       int prcIdn = util.getProcess("LB_IS");
       int prcRIIdn = util.getProcess("LB_RI");
       String prcStr = String.valueOf(prcIdn)+","+String.valueOf(prcRIIdn);
       prcStr = util.getVal(prcStr);
       gtInsert = gtInsert+" and b.prc_id in ("+prcStr+") ";
       if(frmdate.length()>0 && todate.length() >0){
           gtInsert = gtInsert+" and trunc(c.iss_dt) between to_date('"+frmdate+"' , 'dd-mm-yyyy') and to_date('"+todate+"' , 'dd-mm-yyyy') " ; 
        
       }else{
           gtInsert = gtInsert+" and trunc(c.iss_dt)==trunc(sysdate) " ; 
       }
     }else{
           rpHdr = rpHdr+"(Return)";
           int prcIsIdn = util.getProcess("LB_IS");
           int prcRIIdn = util.getProcess("LB_RI");
           String prcStr = String.valueOf(prcIsIdn)+","+String.valueOf(prcRIIdn);
          
           gtInsert = gtInsert+" and b.prc_id in ("+prcStr+") and c.stt='RT' ";
           if(frmdate.length()>0 && todate.length() >0){
               gtInsert = gtInsert+" and   trunc(c.rtn_dt) between to_date('"+frmdate+"' , 'dd-mm-yyyy') and to_date('"+todate+"' , 'dd-mm-yyyy') " ; 
            
           }else{
           gtInsert = gtInsert+" and trunc(c.rtn_dt)==trunc(sysdate) " ; 
       }
       }
       ct = db.execUpd("gt insert", gtInsert, params);
       ArrayList labList = new ArrayList();
       HashMap pktDtlMap = new HashMap();
       String gtFetch = "  select count(*) cnt, to_char(sum(cts),'99999999990.00') cts, to_char(sum (cts*quot),'9999999999990.00') amt, to_char(sum(cts*quot) / sum(cts),'99999999990.00') avg_Rte ,  cert_lab\n" + 
                        "  from gt_srch_multi group by cert_lab ";

             ArrayList outLst = db.execSqlLst("gt fetch", gtFetch, new ArrayList());
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet rs = (ResultSet)outLst.get(1);
       while(rs.next()){
           String lab = rs.getString("cert_lab");
           labList.add(lab);
           pktDtlMap.put(lab+"_CNT",util.nvl(rs.getString("cnt")));
           pktDtlMap.put(lab+"_CTS", util.nvl(rs.getString("cts")));
           pktDtlMap.put(lab+"_AMT", util.nvl(rs.getString("amt")));
           pktDtlMap.put(lab+"_AVG", util.nvl(rs.getString("avg_Rte")));
       }
             rs.close(); pst.close();
         String gtFetchttl = "  select count(*) cnt, to_char(sum(cts),'99999999990.00') cts, to_char(sum (cts*quot),'99999999990.00') amt, to_char(sum(cts*quot) / sum(cts),'99999999990.00') avg_Rte\n" + 
                          "  from gt_srch_multi";

             outLst = db.execSqlLst("gt fetch", gtFetchttl, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while(rs.next()){
             pktDtlMap.put("TTLCNT",util.nvl(rs.getString("cnt")));
             pktDtlMap.put("TTLCTS", util.nvl(rs.getString("cts")));
             pktDtlMap.put("TTLAMT", util.nvl(rs.getString("amt")));
             pktDtlMap.put("TTLAVG", util.nvl(rs.getString("avg_Rte")));
         }
             rs.close(); pst.close();
       if(labList.size()>0){
       req.setAttribute("labList",labList);
       req.setAttribute("pktDtlMap", pktDtlMap);
       req.setAttribute("view", "Y");
       }
       dailylabissrtnForm.setValue("HED", rpHdr);
       util.updAccessLog(req,res,"Daily Lab Issue Receive", "fetch end");
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
                util.updAccessLog(req,res,"Daily Lab Issue Receive", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Daily Lab Issue Receive", "init");
            }
            }
            return rtnPg;
            }
}
