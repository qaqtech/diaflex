package ft.com.report;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.generic.GenericImpl;
import ft.com.marketing.MemoReturnFrm;

import java.io.IOException;

import java.sql.Connection;

import java.sql.PreparedStatement;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WebReport extends DispatchAction {


    public WebReport() {
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
                util.updAccessLog(req,res,"PP report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"PP report", "init");
            }
            }
            return rtnPg;
            }

    public ActionForward loadPP(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"PP report", "loadPP start");
        ArrayList    vwPrpLst = info.getMomoRtnLst();

        if (vwPrpLst == null) {
            vwPrpLst = new ArrayList();
            
          ArrayList outLst = db.execSqlLst(" Vw Lst ",
                                  "Select prp  from rep_prp where mdl = 'MEMO_RTRN' and flg='Y' order by rnk ",
                                  new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);

            while (rs.next()) {
                vwPrpLst.add(rs.getString("prp"));
            }
            rs.close();
            pst.close();
            info.setMomoRtnLst(vwPrpLst);
        }

        vwPrpLst = info.getMomoRtnLst();
        WebReportForm udf      = (WebReportForm) af;

        ArrayList ppLst    = new ArrayList();
        ArrayList    params   = null;
        int       ct       = db.execUpd(" Del Old Pkts ", "Delete from gt_srch_rslt", new ArrayList());
        String    gtInsert =
            "insert into gt_srch_rslt(srch_id, stk_idn, flg ) select distinct 1, mstk_idn, 'PP' from PP_PNDG_V ";
        int    ct1    = db.execCall("gt insert", gtInsert, new ArrayList());
        String pktPrp = "pkgmkt.pktPrp(0,?)";

        params = new ArrayList();
        params.add("MEMO_RTRN");
        ct = db.execCall(" Srch Prp ", pktPrp, params);

        String getCol = util.getGTPrpLst(vwPrpLst.size());
        String ppData =
            "select a.rnk , a.vnm ,  a.mstk_idn, a.net_rte , a.net_dis ,  a.rap_rte, a.byr ,a.ofr_rte ,  a.ofr_dis , a.byr_dis , a.byr_quot  "
            + getCol + "" + " from PP_PNDG_V a, gt_srch_rslt b "
            + "where a.mstk_idn = b.stk_idn and b.flg = 'PP' order by a.sk1, b.vnm, a.rnk, a.byr_quot desc";

          ArrayList outLst = db.execSqlLst("ppData", ppData, new ArrayList());
         PreparedStatement  pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        int count = 0;

        while (rs.next()) {
            count++;

            HashMap ppLstMap = new HashMap();

            ppLstMap.put("Count", String.valueOf(count));
            ppLstMap.put("byr", util.nvl(rs.getString("byr")));
            ppLstMap.put("idn", util.nvl(rs.getString("mstk_idn")));
            ppLstMap.put("raprte", util.nvl(rs.getString("rap_rte")));
            ppLstMap.put("ofrrte", util.nvl(rs.getString("ofr_rte")));
            ppLstMap.put("ofrdis", util.nvl(rs.getString("ofr_dis")));
            ppLstMap.put("byrdis", util.nvl(rs.getString("byr_dis")));
            ppLstMap.put("byrrte", util.nvl(rs.getString("byr_quot")));
            ppLstMap.put("rnk", util.nvl(rs.getString("rnk")));
            ppLstMap.put("vnm", util.nvl(rs.getString("vnm")));
            ppLstMap.put("netdis", util.nvl(rs.getString("net_dis")));
            ppLstMap.put("netrte", util.nvl(rs.getString("net_rte")));

            for (int j = 0; j < vwPrpLst.size(); j++) {
                String prp = (String) vwPrpLst.get(j);
                String fld = "prp_";

                if (j < 9) {
                    fld = "prp_00" + (j + 1);
                } else {
                    fld = "prp_0" + (j + 1);
                }

                String val = util.nvl(rs.getString(fld));

                ppLstMap.put(prp, val);
            }

            ppLst.add(ppLstMap);
        }
        rs.close();
        pst.close();
        udf.setPpListMap(ppLst);
        session.setAttribute("page", "PREMIUM PLUS");
            util.updAccessLog(req,res,"PP report", "loadPP end");
        return am.findForward("loadPP");
        }
    }

    public ActionForward loadLB(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"PP report", "loadLB start");
        ArrayList    vwPrpLst = info.getMomoRtnLst();

        if (vwPrpLst == null) {
            vwPrpLst = new ArrayList();
   
          ArrayList outLst = db.execSqlLst(" Vw Lst ",
                                  "Select prp  from rep_prp where mdl = 'MEMO_RTRN' and flg='Y' order by rnk ",
                                  new ArrayList());
          PreparedStatement  pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);

            while (rs.next()) {
                vwPrpLst.add(rs.getString("prp"));
            }
            rs.close();
            pst.close();
            info.setMomoRtnLst(vwPrpLst);
        }

        vwPrpLst = info.getMomoRtnLst();
        WebReportForm udf      = (WebReportForm) af;

        ArrayList ppLst    = new ArrayList();
        ArrayList    params   = null;
        int       ct       = db.execUpd(" Del Old Pkts ", "Delete from gt_srch_rslt", new ArrayList());
        String    gtInsert =
            "insert into gt_srch_rslt(srch_id, stk_idn, flg) select distinct 1, mstk_idn, 'LB' from LB_PNDG_V  ";
        int    ct1    = db.execCall("gt insert", gtInsert, new ArrayList());
        String pktPrp = "pkgmkt.pktPrp(0,?)";

        params = new ArrayList();
        params.add("MEMO_RTRN");
        ct = db.execCall(" Srch Prp ", pktPrp, params);

        String getCol = util.getGTPrpLst(vwPrpLst.size());
        String ppData =
            "select a.rnk , a.vnm ,  a.mstk_idn, a.net_rte , a.net_dis ,  a.rap_rte, a.byr ,a.ofr_rte ,  a.ofr_dis , a.byr_dis , a.byr_quot  "
            + getCol + "" + " from LB_PNDG_V a, gt_srch_rslt b "
            + "where a.mstk_idn = b.stk_idn and b.flg = 'LB' order by a.sk1, b.vnm, a.rnk, a.byr_quot desc";

          ArrayList outLst = db.execSqlLst("ppData", ppData, new ArrayList());
          PreparedStatement  pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        int count = 0;

        while (rs.next()) {
            count++;

            HashMap ppLstMap = new HashMap();

            ppLstMap.put("Count", String.valueOf(count));
            ppLstMap.put("byr", util.nvl(rs.getString("byr")));
            ppLstMap.put("idn", util.nvl(rs.getString("mstk_idn")));
            ppLstMap.put("raprte", util.nvl(rs.getString("rap_rte")));
            ppLstMap.put("ofrrte", util.nvl(rs.getString("ofr_rte")));
            ppLstMap.put("ofrdis", util.nvl(rs.getString("ofr_dis")));
            ppLstMap.put("byrdis", util.nvl(rs.getString("byr_dis")));
            ppLstMap.put("byrrte", util.nvl(rs.getString("byr_quot")));
            ppLstMap.put("rnk", util.nvl(rs.getString("rnk")));
            ppLstMap.put("vnm", util.nvl(rs.getString("vnm")));
            ppLstMap.put("netdis", util.nvl(rs.getString("net_dis")));
            ppLstMap.put("netrte", util.nvl(rs.getString("net_rte")));

            for (int j = 0; j < vwPrpLst.size(); j++) {
                String prp = (String) vwPrpLst.get(j);
                String fld = "prp_";

                if (j < 9) {
                    fld = "prp_00" + (j + 1);
                } else {
                    fld = "prp_0" + (j + 1);
                }

                String val = util.nvl(rs.getString(fld));

                ppLstMap.put(prp, val);
            }

            ppLst.add(ppLstMap);
        }
        rs.close();
            pst.close();
        udf.setPpListMap(ppLst);
        session.setAttribute("page", "LOOK AND BID");
            util.updAccessLog(req,res,"PP report", "loadLB end");
        return am.findForward("loadPP");
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
