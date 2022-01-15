package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.lab.LabResultImpl;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

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

public class DailyAllocationAction extends DispatchAction {
   
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
             util.updAccessLog(req,res,"Daily Allocation", "load start");
             GenericInterface genericInt = new GenericImpl();
        DailyAllocationForm udf = (DailyAllocationForm)af;
        String dteStr = " trunc(sysdate) ";
        String dte = util.nvl((String)udf.getValue("dte"));
        if(!dte.equals(""))
            dteStr = "to_date('"+dte+"' , 'dd-mm-yyyy')";
        ArrayList ary = null;
         String delQ = " Delete from gt_srch_rslt ";
         int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "memoPrpList","memoPrpList");
        String allocationSql = " insert into gt_srch_rslt(stk_idn, flg, stt, vnm, cts, rap_rte, cmp, quot, sk1) "+
                               " select distinct a.idn, b.typ flg, a.stt , a.vnm, a.cts, a.rap_rte, nvl(a.upr, a.cmp), nvl(a.upr, a.cmp) "+
                              " , a.sk1 from mstk a, dly_alloc_v b "+
                              " where a.idn = b.mstk_idn and trunc(b.alc_dte) = "+dteStr;

        ct = db.execUpd("allocationSql", allocationSql, new ArrayList());
         ArrayList ppList = new ArrayList();
         String pktPrp = "pkgmkt.pktPrp(0,?)";
         ary = new ArrayList();
         ary.add("MEMO_RTRN");
         ct = db.execCall(" Srch Prp ", pktPrp, ary);
         String getCol = util.getGTPrpLst(vwPrpLst.size());
        
         String reportSql = " select a.byr , a.typ , a.vnm, a.rnk , a.mstk_idn " +
             " , decode(a.stt,'ALC','Yes','No') stt , b.cmp , a.alloc_prc_seq , a.req_idn , a.net_rte , b.flg , "+
                            " a.req_quot , a.quot , trunc((100* (a.net_rte - b.cmp)/ b.cmp), 2) diff, a.memo_id , a.alc_dte "+getCol+ 
                            "  from dly_alloc_v a, gt_srch_rslt b "+
                             " where a.mstk_idn = b.stk_idn  order by a.typ, a.sk1, b.vnm, a.rnk ";

             ArrayList outLst = db.execSqlLst("reportSql", reportSql, new ArrayList());
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet rs = (ResultSet)outLst.get(1);
         int count = 0;
         while(rs.next()){
             count++;

             HashMap ppLstMap = new HashMap();

             ppLstMap.put("Count", String.valueOf(count));
             ppLstMap.put("byr", util.nvl(rs.getString("byr")));
             ppLstMap.put("idn", util.nvl(rs.getString("mstk_idn")));
             ppLstMap.put("typ", util.nvl(rs.getString("typ")));
             ppLstMap.put("vnm", util.nvl(rs.getString("vnm")));
             ppLstMap.put("stt", util.nvl(rs.getString("stt")));
             ppLstMap.put("flg", util.nvl(rs.getString("flg")));
             ppLstMap.put("rnk", util.nvl(rs.getString("rnk")));
             ppLstMap.put("allocPrcSeq", util.nvl(rs.getString("alloc_prc_seq")));
             ppLstMap.put("req_idn", util.nvl(rs.getString("req_idn")));
             ppLstMap.put("netRte", util.nvl(rs.getString("net_rte")));
             ppLstMap.put("req_quot", util.nvl(rs.getString("req_quot")));
             ppLstMap.put("quot", util.nvl(rs.getString("quot")));
             ppLstMap.put("memoId", util.nvl(rs.getString("memo_id")));
             ppLstMap.put("alcDte", util.nvl(rs.getString("alc_dte")));
             ppLstMap.put("cmp", util.nvl(rs.getString("cmp")));
             ppLstMap.put("diff", util.nvl(rs.getString("diff")));
                 
             for (int j = 0; j < vwPrpLst.size(); j++) {
                 String prp = (String) vwPrpLst.get(j);
                 String fld = "prp_";

                 if (j < 9) {
                     fld = "prp_00" + (j + 1);
                 } else {
                     fld = "prp_0" + (j + 1);
                 }

                 String val = util.nvl(rs.getString(fld));
                 if(rs.getString("stt").equalsIgnoreCase("No")) 
                    val = "";   
                 ppLstMap.put(prp, val);
             }
             ppList.add(ppLstMap);
         }
         rs.close(); pst.close();
         udf.setPpPktList(ppList);
         udf.reset();
             util.updAccessLog(req,res,"Daily Allocation", "load start");
        return am.findForward("load");
         }
     }
    
    public DailyAllocationAction() {
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
                rtnPg=util.checkUserPageRights("report/dailyAllocationAction.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Daily Allocation", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Daily Allocation", "init");
            }
            }
            return rtnPg;
            }
}
