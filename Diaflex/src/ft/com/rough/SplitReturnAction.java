package ft.com.rough;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.GtPktDtl;
import ft.com.dao.MNme;

import ft.com.dao.Mprc;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;
import ft.com.mix.MixAssortRtnForm;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class SplitReturnAction extends DispatchAction {
    public SplitReturnAction() {
        super();
    }

    public ActionForward load(ActionMapping am, ActionForm af,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            rtnPg = init(req, res, session, util);
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            SplitReturnForm udf = (SplitReturnForm)af;
            udf.resetAll();
            String mdl = util.nvl(req.getParameter("mdl"));
            if (mdl.equals(""))
                mdl = "RGH_VIEW";
            ArrayList mprcList = getPrc(req, res);
            udf.setMprcList(mprcList);

            ArrayList empList = getEmp(req, res);
            udf.setEmpList(empList);
            udf.setValue("mdl", mdl);
            req.setAttribute("mdl", mdl);
            String form = util.nvl(req.getParameter("form"));
            String dfPg = "MIX_SPLIT";
            if (form.equals("RGH"))
                dfPg = "RGH_SPLIT";
            HashMap allPageDtl =
                (info.getPageDetails() == null) ? new HashMap() :
                (HashMap)info.getPageDetails();
            HashMap pageDtl = (HashMap)allPageDtl.get(dfPg);
            if (pageDtl == null || pageDtl.size() == 0) {
                pageDtl = new HashMap();
                pageDtl = util.pagedef(dfPg);
                allPageDtl.put(dfPg, pageDtl);
            }
            info.setPageDetails(allPageDtl);
            return am.findForward("load");
        }
    }

    public ArrayList getEmp(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        ArrayList empList = new ArrayList();
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            init(req, res, session, util);
            ArrayList ary = new ArrayList();

            String empSql =
                "select nme_idn, nme from nme_v where typ = 'EMPLOYEE' order by nme";
          ArrayList outLst = db.execSqlLst("empSql", empSql, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            try {
                while (rs.next()) {
                    MNme emp = new MNme();
                    emp.setEmp_idn(rs.getString("nme_idn"));
                    emp.setEmp_nme(rs.getString("nme"));
                    empList.add(emp);
                }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        }
        return empList;
    }

    public ArrayList getPrc(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        ArrayList prcList = new ArrayList();
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            init(req, res, session, util);

            ArrayList ary = new ArrayList();
            HashMap prcSttMap = new HashMap();


            String grp = util.nvl(req.getParameter("grp"));

            String prcSql =
                "select idn, prc , in_stt from mprc where  stt = ? ";
            if (grp.equals("RHGPUR")) {
                prcSql += " and is_stt in ('RGH_SPT_IS','RGH_IN_IS') ";
            }
            if (!grp.equals("")) {
                prcSql += " and grp in (" + grp + ") ";
            }
            prcSql += " order by srt";
            ary = new ArrayList();
            ary.add("A");
          ArrayList outLst = db.execSqlLst("prcSql", prcSql, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            try {
                while (rs.next()) {
                    Mprc mprc = new Mprc();
                    String mprcId = rs.getString("idn");
                    mprc.setMprcId(rs.getString("idn"));
                    mprc.setPrc(rs.getString("prc"));
                    mprc.setIn_stt(rs.getString("in_stt"));
                    prcList.add(mprc);
                    prcSttMap.put(mprcId, mprc);
                }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }


        }
        return prcList;
    }

    public ActionForward fecth(ActionMapping am, ActionForm form,
                               HttpServletRequest req,
                               HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            rtnPg = init(req, res, session, util);
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            SplitReturnForm udf = (SplitReturnForm)form;
            HashMap stockList = new HashMap();
            String mprcIdn = util.nvl((String)udf.getValue("mprcIdn"));
            String empId = util.nvl((String)udf.getValue("empIdn"));
            String issueId = util.nvl((String)udf.getValue("issueId"));
            if(issueId.equals(""))
                issueId  = util.nvl(req.getParameter("issID"));
            String grpList = util.nvl((String)udf.getValue("grpList"));
            String lotNo = util.nvl((String)udf.getValue("lotNo"));
            String mdl = util.nvl((String)udf.getValue("mdl"));
            String AtrlotNo = util.nvl((String)udf.getValue("AtrlotNo"));
            String formNme = util.nvl(req.getParameter("form"));
            String dfPg = "MIX_SPLIT";
            if (formNme.equals("RGH"))
                dfPg = "RGH_SPLIT";
            HashMap allPageDtl =
                (info.getPageDetails() == null) ? new HashMap() :
                (HashMap)info.getPageDetails();
            HashMap pageDtl = (HashMap)allPageDtl.get(dfPg);
            ArrayList pageList = new ArrayList();
            HashMap pageDtlMap = new HashMap();
            String fld_nme = "", fld_ttl = "", val_cond = "", dflt_val =
                "", fld_typ = "", form_nme = "", flg1 = "";
            String prcStt = "";
            String grp = "";
            if (!issueId.equals("") && mprcIdn.equals("0")) {
                ArrayList ary = new ArrayList();
                String issuStt =
                    " select b.is_stt , b.idn,b.grp from iss_rtn a , mprc b where a.prc_id = b.idn and a.iss_id= ? ";
                ary.add(issueId);
              ArrayList outLst = db.execSqlLst("issueStt", issuStt, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                    prcStt = util.nvl(rs.getString("is_stt"));
                    mprcIdn = util.nvl(rs.getString("idn"));
                    grp = util.nvl(rs.getString("grp"));
                }
                rs.close();
                pst.close();
            } else if (!mprcIdn.equals("0")) {
                ArrayList ary = new ArrayList();
                String issuStt =
                    " select b.is_stt , b.grp from  mprc b where b.idn = ? ";
                ary.add(mprcIdn);
              ArrayList outLst = db.execSqlLst("issueStt", issuStt, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                    prcStt = util.nvl(rs.getString("is_stt"));
                    grp = util.nvl(rs.getString("grp"));
                }
                rs.close();
                pst.close();
            }
            if (!prcStt.equals("")) {
                HashMap params = new HashMap();
                params.put("stt", prcStt);
                params.put("empIdn", empId);
                params.put("issueId", issueId);
                params.put("mprcIdn", mprcIdn);
                params.put("grpList", grpList);
                params.put("mdl", mdl);
                params.put("lotNo", lotNo);
                params.put("altLotNO", AtrlotNo);
                stockList = FecthResult(req, res, params);
                if (stockList.size() > 0) {
                    HashMap totals = GetTotal(req, res);
                    req.setAttribute("totalMap", totals);
                }
            }
            HashMap pktCountMap = new HashMap();

            String pktCount =
                "select count(*) cnt , pkt_rt, a.iss_id from iss_rtn_dtl a , gt_srch_rslt b\n" +
                "where a.iss_id= b.srch_id and a.pkt_rt = b.stk_idn GROUP BY pkt_rt,a.iss_id ";
          ArrayList outLst = db.execSqlLst("pktCount", pktCount, new ArrayList());
          PreparedStatement pst1 = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String cnt = rs.getString("cnt");
                String pkt = rs.getString("pkt_rt");
                String iss_id = rs.getString("iss_id");
                pktCountMap.put(iss_id + "_" + pkt, cnt);
            }
            rs.close();
            pst1.close();

            String pktRghCount =
                "select count(*) cnt ,  to_char(sum(ct.num),'999990.000') ttlCts , pkt_rt \n" +
                "from iss_rtn_dtl a , gt_srch_rslt b , stk_dtl ct\n" +
                "where a.pkt_rt is not null and a.pkt_rt=b.stk_idn \n" +
                "and a.iss_stk_idn=ct.mstk_idn and ct.mprp=? and ct.grp=1\n" +
                "GROUP BY pkt_rt";
            ArrayList ary = new ArrayList();
            ary.add("CRTWT");
            ArrayList rsLst = db.execSqlLst("pktRghSql", pktRghCount, ary);
            PreparedStatement pst = (PreparedStatement)rsLst.get(0);
            ResultSet rs1 = (ResultSet)rsLst.get(1);
            while (rs1.next()) {
                String stkIdn = rs1.getString("pkt_rt");
                pktCountMap.put(stkIdn + "_CNT",
                                util.nvl(rs1.getString("cnt")));
             
                pktCountMap.put(stkIdn + "_CTS",
                                util.nvl(rs1.getString("ttlCts")));
            }
            rs1.close();
            pst.close();
            req.setAttribute("pktCountMap", pktCountMap);


            req.setAttribute("view", "Y");
            udf.setValue("prcId", mprcIdn);
            udf.setValue("empId", empId);
            req.setAttribute("prcId", mprcIdn);
            req.setAttribute("IsStt", prcStt);
            req.setAttribute("mdl", mdl);
            req.setAttribute("issueId", issueId);
            req.setAttribute("grp", grp);
            String lstNme =
                "RGHRTN_" + info.getLogId() + "_" + util.getToDteGiveFrmt("yyyyMMdd");
            gtMgr.setValue(lstNme + "_SEL", new ArrayList());
            gtMgr.setValue(lstNme, stockList);
            gtMgr.setValue("lstNmeRGHRTN", lstNme);
            if (stockList.size() > 0) {
                HashMap dtlMap = new HashMap();
                ArrayList selectstkIdnLst = new ArrayList();
                Set<String> keys = stockList.keySet();
                for (String key : keys) {
                    selectstkIdnLst.add(key);
                }
                dtlMap.put("selIdnLst", selectstkIdnLst);
                dtlMap.put("pktDtl", stockList);
                dtlMap.put("flg", "M");
                HashMap ttlMap = util.getTTL(dtlMap);
                gtMgr.setValue(lstNme + "_TTL", ttlMap);
                pageList =
                        ((ArrayList)pageDtl.get("PLANDTL") == null) ? new ArrayList() :
                        (ArrayList)pageDtl.get("PLANDTL");
                if (pageList != null && pageList.size() > 0) {
                    for (int j = 0; j < pageList.size(); j++) {
                        pageDtlMap = (HashMap)pageList.get(j);
                        dflt_val =
                                util.nvl((String)pageDtlMap.get("dflt_val"));
                        if (dflt_val.equals("Y")) {
                            HashMap planDtl = getPlanDtl(req, res);
                            gtMgr.setValue(lstNme + "_PLN", planDtl);
                        }
                    }
                }
                RtnEdit(req, res, prcStt);
            }
            req.setAttribute("mdl", mdl);
            return am.findForward("load");
        }
    }

    public HashMap getPlanDtl(HttpServletRequest req,
                              HttpServletResponse res) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        HashMap planDtl = new HashMap();
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            init(req, res, session, util);
            String planDtlSql =
                "select a.DFLT_YN, a.plan_seq , a.qty , a.cts ,a.rte ,a.vlu,a.ref_idn , to_char(a.vlu/b.cts,'99999999990.00') rrte from PLAN_M a , gt_srch_rslt b\n" +
                "where a.ref_idn=b.stk_idn order by a.ref_idn,plan_seq";
          ArrayList outLst = db.execSqlLst("", planDtlSql, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            try {
                while (rs.next()) {
                    String planSeq = util.nvl(rs.getString("plan_seq"));
                    String ref_idn = util.nvl(rs.getString("ref_idn"));
                    HashMap calDtl = new HashMap();
                    calDtl.put("CTS", util.nvl(rs.getString("cts")));
                    calDtl.put("QTY", util.nvl(rs.getString("qty")));
                    calDtl.put("RTE", util.nvl(rs.getString("rte")));
                    calDtl.put("VLU", util.nvl(rs.getString("vlu")));
                    calDtl.put("RRTE", util.nvl(rs.getString("rrte")));
                    calDtl.put("DFLT_YN", util.nvl(rs.getString("DFLT_YN")));
                    planDtl.put(ref_idn + "_" + planSeq, calDtl);
                }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        }

        return planDtl;
    }

    public HashMap GetTotal(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        HashMap gtTotalMap = new HashMap();
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            init(req, res, session, util);

            String gtTotal =
                "Select sum(qty) qty, to_char(sum(cts),'999990.000') cts from gt_srch_rslt where flg = ?";
            ArrayList ary = new ArrayList();
            ary.add("Z");
          ArrayList outLst = db.execSqlLst("getTotal", gtTotal, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            try {
                if (rs.next()) {
                    gtTotalMap.put("qty", util.nvl(rs.getString("qty")));
                    gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
                }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        }
        return gtTotalMap;
    }


    public HashMap FecthResult(HttpServletRequest req, HttpServletResponse res,
                               HashMap params) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        HashMap stockList = new HashMap();
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            init(req, res, session, util);

            String stt = (String)params.get("stt");
            String issId = (String)params.get("issueId");
            String mprcIdn = (String)params.get("mprcIdn");
            String empIdn = (String)params.get("empIdn");
            String mdl = (String)params.get("mdl");
            String lotNo = util.nvl((String)params.get("lotNo"));
            String altLotNo = util.nvl((String)params.get("altLotNO"));

            ArrayList ary = null;
            String delQ = " Delete from gt_srch_rslt ";
            int ct = db.execUpd(" Del Old Pkts ", delQ, new ArrayList());

            String srchRefQ =
                "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty ,quot, cts , srch_id , rmk , rln_idn , sk1 ) " +
                " select  a.pkt_ty, a.idn, a.vnm,  a.dte, b.stt , 'Z' , a.qty ,a.upr, a.cts , b.iss_id , a.tfl3 , b.iss_emp_id , a.sk1 " +
                "   from mstk a , iss_rtn_dtl b , iss_rtn c where a.idn = b.iss_stk_idn and b.stt in ('IS','LK')  " +
                " and a.stt =? and b.iss_id=c.iss_id and c.prc_id=? and a.cts > 0  and b.pkt_rt is null  ";
            ary = new ArrayList();
            ary.add(stt);
            ary.add(mprcIdn);
            if (!issId.equals("")) {
                srchRefQ = srchRefQ + " and b.iss_id = ? ";
                ary.add(issId);
            }
            if (!empIdn.equals("0")) {

                srchRefQ = srchRefQ + " and b.iss_emp_id = ? ";
                ary.add(empIdn);
            }
            if (!lotNo.equals("")) {
                srchRefQ =
                        srchRefQ + " and EXISTS ( select 1 from stk_dtl c where a.idn = c.mstk_idn and c.mprp='LOTNO' and c.txt = ?)";
                ary.add(lotNo);
            }
            if (!altLotNo.equals("")) {
                srchRefQ =
                        srchRefQ + " and EXISTS ( select 1 from stk_dtl d where a.idn = d.mstk_idn and d.mprp='ALT_LOTNO' and d.txt = ?)";
                ary.add(altLotNo);
            }
            ct = db.execDirUpd(" Srch Prp ", srchRefQ, ary);

            String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
            ary = new ArrayList();
            ary.add(mdl);
            ct = db.execCall(" Srch Prp ", pktPrp, ary);

            stockList = SearchResult(req, res, "", mdl);
        }
        return stockList;
    }
    public HashMap FecthResultExits(HttpServletRequest req,HttpServletResponse res, HashMap params){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap stockList = new HashMap();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
          HashMap dbinfo = info.getDmbsInfoLst();
          int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
          String pkt_ty = util.nvl((String)params.get("Pkt_ty"));
        String vnm = (String)params.get("vnm");
        String stt = (String)params.get("stt");
        ArrayList ary = null;
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        
        ary = new ArrayList();
      
        String srchRefQ = "";
       if(!vnm.equals("")){
          vnm = util.getVnm(vnm);
          String[] vnmLst = vnm.split(",");
          int loopCnt = 1 ;
          float loops = ((float)vnmLst.length)/stepCnt;
          loopCnt = Math.round(loops);
             if(vnmLst.length <= stepCnt || new Float(loopCnt)>=loops) {
              
          } else
              loopCnt += 1 ;
          if(loopCnt==0)
              loopCnt += 1 ;
          int fromLoc = 0 ;
          int toLoc = 0 ;
          for(int i=1; i <= loopCnt; i++) {
              
            int aryLoc = Math.min(i*stepCnt, vnmLst.length) ;
            
            String lookupVnm = vnmLst[aryLoc-1];
                 if(i == 1)
                     fromLoc = 0 ;
                 else
                     fromLoc = toLoc+1;
                 
                 toLoc = Math.min(vnm.lastIndexOf(lookupVnm) + lookupVnm.length(), vnm.length());
                 String vnmSub = vnm.substring(fromLoc, toLoc);
              
              vnmSub=vnmSub.toUpperCase();
              vnmSub= vnmSub.replaceAll (" ", "");
              srchRefQ = 
               "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk,rap_rte,rap_dis ) " + 
               " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , sk1 , tfl3,b.rap_rte,decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))  "+
               "     from mstk b "+
               " where  cts > 0 and stt = ? and pkt_ty = ? ";
              srchRefQ = srchRefQ+" and ( b.vnm in ("+vnmSub+") or b.tfl3 in ("+vnmSub+") or b.cert_no in ("+vnmSub+")) " ;
              ary = new ArrayList();
              ary.add(stt);
              ary.add(pkt_ty);
              ct = db.execUpd(" ins gt_srch_rslt", srchRefQ,ary); 
          }
           
       }else{
         srchRefQ =   "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty ,cts , sk1 , rmk,rap_rte,rap_dis ) " + 
           " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , sk1 , tfl3,b.rap_rte,decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))  "+
           "     from mstk b "+
           " where  cts > 0  and stt = ? and pkt_ty = ? ";
          
           ary = new ArrayList();
           ary.add(stt);
           ary.add(pkt_ty);
           ct = db.execUpd(" ins gt_srch_rslt", srchRefQ,ary);
           
       }
        
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add("RGH_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        stockList = SearchResult(req ,res, vnm,"RGH_VIEW");
      }
        return stockList;
    }

    public HashMap SearchResult(HttpServletRequest req,
                                HttpServletResponse res, String vnmLst,
                                String mdl) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        HashMap pktList = new HashMap();
        double vrfyCts = 0;
        int vrfyQty = 0;
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            init(req, res, session, util);
            SearchQuery query = new SearchQuery();

            ArrayList vwPrpLst = ASPrprViw(req, res, mdl);
            session.setAttribute("SPLITVIEWLST", vwPrpLst);
            String srchQ =
                " select stk_idn , pkt_ty,stt, sk1, vnm, pkt_dte, stt , qty , cts , srch_id ,rmk , byr.get_nm(rln_idn) emp ,to_char(quot,'99999990.90') quot,to_char(quot*cts,'99990.90') amt ";


            for (int i = 0; i < vwPrpLst.size(); i++) {
                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                    fld += "00" + j;
                else if (j < 100)
                    fld += "0" + j;
                else if (j > 100)
                    fld += j;


                srchQ += ", " + fld;

            }


            String rsltQ =
                srchQ + " from gt_srch_rslt where flg =? order by sk1 , cts ";

            ArrayList ary = new ArrayList();
            ary.add("Z");
          ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            try {
                while (rs.next()) {
                    String cts = util.nvl(rs.getString("cts"), "0");
                    double ctsd = Double.parseDouble(cts);
                    vrfyCts = vrfyCts + ctsd;
                    String qty = util.nvl(rs.getString("qty"), "0");
                    int qtyd = Integer.parseInt(qty);
                    vrfyQty = vrfyQty + qtyd;
                    String stkIdn = util.nvl(rs.getString("stk_idn"));
                    GtPktDtl pktPrpMap = new GtPktDtl();
                    pktPrpMap.setValue("stt", util.nvl(rs.getString("stt")));
                    pktPrpMap.setValue("emp", util.nvl(rs.getString("emp")));
                    String vnm = util.nvl(rs.getString("vnm"));
                    pktPrpMap.setValue("vnm", vnm);
                    String tfl3 = util.nvl(rs.getString("rmk"));
                    if (vnmLst.indexOf(tfl3) != -1 && !tfl3.equals("")) {
                        if (vnmLst.indexOf("'") == -1 && !tfl3.equals(""))
                            vnmLst = vnmLst.replaceAll(tfl3, "");
                        else
                            vnmLst = vnmLst.replaceAll("'" + tfl3 + "'", "");
                    } else if (vnmLst.indexOf(vnm) != -1 && !vnm.equals("")) {
                        if (vnmLst.indexOf("'") == -1)
                            vnmLst = vnmLst.replaceAll(vnm, "");
                        else
                            vnmLst = vnmLst.replaceAll("'" + vnm + "'", "");
                    }
                    pktPrpMap.setValue("stk_idn", stkIdn);
                    pktPrpMap.setValue("qty", util.nvl(rs.getString("qty")));
                    pktPrpMap.setValue("amt", util.nvl(rs.getString("amt")));
                    pktPrpMap.setValue("cts", cts);
                    pktPrpMap.setSk1(new BigDecimal(util.nvl(rs.getString("sk1"),
                                                             "0")));
                    pktPrpMap.setValue("stt", util.nvl(rs.getString("stt")));
                    pktPrpMap.setValue("issIdn",
                                       util.nvl(rs.getString("srch_id")));
                    pktPrpMap.setValue("upr", util.nvl(rs.getString("quot")));
                    for (int j = 0; j < vwPrpLst.size(); j++) {
                        String prp = (String)vwPrpLst.get(j);

                        String fld = "prp_";
                        if (j < 9)
                            fld = "prp_00" + (j + 1);
                        else
                            fld = "prp_0" + (j + 1);
                        if (prp.equals("CRTWT"))
                            fld = "cts";
                        
                        if(prp.equals("RTE"))
                        fld="quot";
                        
                        String val = util.nvl(rs.getString(fld));


                        pktPrpMap.setValue(prp, val);
                    }

                    pktList.put(stkIdn, pktPrpMap);
                }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {

                // TODO: Add catch code
                sqle.printStackTrace();
            }
            pktList = (HashMap)query.sortByComparator(pktList);
            ArrayList list = (ArrayList)req.getAttribute("msgList");
            if (list != null && list.size() > 0) {
            } else {
                if (!vnmLst.equals("")) {
                    vnmLst = util.pktNotFound(vnmLst);
                    req.setAttribute("vnmNotFnd", vnmLst);
                }
            }
        }
        vrfyCts = util.roundToDecimals(vrfyCts, 2);
        req.setAttribute("VRFCTS", String.valueOf(vrfyCts));
        req.setAttribute("VRFQTY", String.valueOf(vrfyQty));
        return pktList;
    }


    public HashMap SearchResultList(HttpServletRequest req,
                                    HttpServletResponse res, String vnmLst,
                                    ArrayList viewLst) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        HashMap pktList = new HashMap();
        double vrfyCts = 0;
        int vrfyQty = 0;
        double ttlCpVal=0;
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            init(req, res, session, util);
            SearchQuery query = new SearchQuery();
            int cpindex = viewLst.indexOf("CP")+1;
             String cpLprp ="srt_00"+cpindex;
             if(cpindex>9)
                 cpLprp ="srt_0"+cpindex;
             if(cpindex<=1)
                 cpLprp="quot";
            String srchQ =
                " select stk_idn , pkt_ty,stt, sk1, vnm, pkt_dte, stt , qty , cts , to_char(cts*"+cpLprp+",'9999999990.90') cpval , srch_id ,rmk , byr.get_nm(rln_idn) emp, quot ";
            String rsltQ =
                srchQ + " from gt_srch_rslt where flg =? order by sk1 , cts ";

            ArrayList ary = new ArrayList();
            ary.add("Z");
         
            ArrayList outLst = db.execSqlLst("result", rsltQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            try {
                while (rs.next()) {
                    String cts = util.nvl(rs.getString("cts"), "0");
                    double ctsd = Double.parseDouble(cts);
                    vrfyCts = vrfyCts + ctsd;
                    String qty = util.nvl(rs.getString("qty"), "0");
                    int qtyd = Integer.parseInt(qty);
                    vrfyQty = vrfyQty + qtyd;
                    String stkIdn = util.nvl(rs.getString("stk_idn"));
                    GtPktDtl pktPrpMap = new GtPktDtl();
                    pktPrpMap.setValue("stt", util.nvl(rs.getString("stt")));
                    pktPrpMap.setValue("emp", util.nvl(rs.getString("emp")));
                    String vnm = util.nvl(rs.getString("vnm"));
                    pktPrpMap.setValue("vnm", vnm);
                    String tfl3 = util.nvl(rs.getString("rmk"));
                    pktPrpMap.setValue("upr", util.nvl(rs.getString("quot")));
                    String cpval = util.nvl(rs.getString("cpval"));
                    if(!cpval.equals("")){
                        ttlCpVal=ttlCpVal+Double.parseDouble(cpval);
                    }
                    if (vnmLst.indexOf(tfl3) != -1 && !tfl3.equals("")) {
                        if (vnmLst.indexOf("'") == -1 && !tfl3.equals(""))
                            vnmLst = vnmLst.replaceAll(tfl3, "");
                        else
                            vnmLst = vnmLst.replaceAll("'" + tfl3 + "'", "");
                    } else if (vnmLst.indexOf(vnm) != -1 && !vnm.equals("")) {
                        if (vnmLst.indexOf("'") == -1)
                            vnmLst = vnmLst.replaceAll(vnm, "");
                        else
                            vnmLst = vnmLst.replaceAll("'" + vnm + "'", "");
                    }
                    pktPrpMap.setValue("stk_idn", stkIdn);
                    pktPrpMap.setValue("qty", util.nvl(rs.getString("qty")));
                    pktPrpMap.setValue("cts", cts);
                    pktPrpMap.setSk1(new BigDecimal(util.nvl(rs.getString("sk1"),
                                                             "0")));
                    pktPrpMap.setValue("stt", util.nvl(rs.getString("stt")));
                    pktPrpMap.setValue("issIdn",
                                       util.nvl(rs.getString("srch_id")));
                    if (viewLst != null && viewLst.size() > 0) {
                        String vwLst = viewLst.toString();
                        vwLst = vwLst.replaceAll("\\[", "");
                        vwLst = vwLst.replaceAll("\\]", "");
                        vwLst = vwLst.replaceAll(",", "','");
                        vwLst = vwLst.replaceAll(" ", "");
                        vwLst = "('" + vwLst + "')";

                        String stkdtlSql =
                            " select a.mprp, decode(b.dta_typ, 'C', a.val, 'N', a.num, 'D', a.dte, a.txt) val \n" +
                            "from stk_dtl a, mprp b\n" +
                            "where a.mprp = b.prp and b.prp in " + vwLst +
                            " and a.grp=? and a.mstk_idn =? ";
                        ArrayList params = new ArrayList();
                        params.add("1");
                        params.add(stkIdn);
                        ArrayList outLst1 =
                            db.execSqlLst("result", stkdtlSql, params);
                        PreparedStatement pst1 =
                            (PreparedStatement)outLst1.get(0);
                        ResultSet rs1 = (ResultSet)outLst1.get(1);
                        while (rs1.next()) {
                            String lmprp = util.nvl(rs1.getString("mprp"));
                            String lval = util.nvl(rs1.getString("val"));
                            pktPrpMap.setValue(lmprp, lval);
                        }
                        rs1.close();
                        pst1.close();

                    }

                    pktList.put(stkIdn, pktPrpMap);
                }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {

                // TODO: Add catch code
                sqle.printStackTrace();
            }
            pktList = (HashMap)query.sortByComparator(pktList);
            ArrayList list = (ArrayList)req.getAttribute("msgList");
            if (list != null && list.size() > 0) {
            } else {
                if (!vnmLst.equals("")) {
                    vnmLst = util.pktNotFound(vnmLst);
                    req.setAttribute("vnmNotFnd", vnmLst);
                }
            }
        }
        vrfyCts = util.roundToDecimals(vrfyCts, 2);
        ttlCpVal=util.roundToDecimals(ttlCpVal, 2);
        req.setAttribute("VRFCTS", String.valueOf(vrfyCts));
        req.setAttribute("VRFQTY", String.valueOf(vrfyQty));
        req.setAttribute("TTLCPVAL", String.valueOf(ttlCpVal));
        return pktList;
    }


    public ActionForward Verify(ActionMapping am, ActionForm form,
                                HttpServletRequest req,
                                HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            rtnPg = init(req, res, session, util);
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            ArrayList ary = null;
            String count = util.nvl(req.getParameter("COUNT"));
            String mstkIdn = util.nvl(req.getParameter("mstkIdn"));
            String stt = util.nvl(req.getParameter("stt"));
            String grp = util.nvl(req.getParameter("grp"));
            String pvnm = util.nvl(req.getParameter("vnm"));
            String mdl = util.nvl(req.getParameter("mdl"), "RGH_VIEW");
            String pkt_ty = "RGH";
            if (grp.indexOf("MIX") != -1)
                pkt_ty = "MIX";
            String issId = req.getParameter("issIdn");
            String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
            ArrayList vwPrpLst =
                (ArrayList)session.getAttribute("SPLITVIEWLST");
            HashMap rte_dtl = new HashMap();
            if (!count.equals("")) {
                int loop = Integer.parseInt(count);
                for (int i = 0; i < loop; i++) {
                    String cts = util.nvl(req.getParameter("CRTWT_" + i));
                    String idn = util.nvl(req.getParameter("IDN_" + i));
                    String qty = util.nvl(req.getParameter("QTY_" + i));
                    String vnm = util.nvl(req.getParameter("VNM_" + i));
                    String lotNo = util.nvl(req.getParameter("LOTNO_" + i));
                    double lagat =
                        Double.parseDouble(util.nvl(req.getParameter("LAGAT_" +
                                                                     i), "0"));
                    String lotIdn = "";
                    if (!cts.equals("")) {

                        int newIdn = 0;
                        if (idn.equals("")) {

                            if (!lotNo.equals("")) {
                                String lotDsc =
                                    "select idn from mlot where dsc ='" +
                                    lotNo + "'";
                                ArrayList params = new ArrayList();

                                ArrayList rsLst =
                                    db.execSqlLst("lotDsc", lotDsc, params);
                                PreparedStatement pst =
                                    (PreparedStatement)rsLst.get(0);
                                ResultSet rs = (ResultSet)rsLst.get(1);
                                while (rs.next()) {
                                    lotIdn = rs.getString("idn");
                                }
                                rs.close();
                                pst.close();

                            }
                            String insMst =
                                "MIX_PKG.GEN_PKT(pStt => ?,pQty => ? ,pPktRt => ?, pPktTyp => ?,pIdn =>?,pVnm =>?)";
                            ary = new ArrayList();
                            ary.add(stt);
                            ary.add(qty);
                            ary.add(mstkIdn);
                            ary.add(pkt_ty);
                            if (!lotIdn.equals("")) {
                                insMst =
                                        "MIX_PKG.GEN_PKT(pStt => ?,pQty => ? ,pPktRt => ?, pPktTyp => ?,pLotIdn => ? , pIdn =>?,pVnm =>?)";
                                ary.add(lotIdn);
                            }
                            ArrayList out = new ArrayList();
                            out.add("I");
                            out.add("V");
                            CallableStatement cst =
                                db.execCall("findMstkId", insMst, ary, out);
                            newIdn = cst.getInt(ary.size() + 1);
                          cst.close();
                          cst=null;
                            ary = new ArrayList();
                            ary.add(vnm);
                            ary.add(String.valueOf(newIdn));
                            int ct =
                                db.execUpd("updmstk", "update mstk set vnm = ? where idn = ?",
                                           ary);
                        } else {
                            newIdn = Integer.parseInt(idn);
                            ary = new ArrayList();
                            ary.add(vnm);
                            ary.add(qty);
                            ary.add(String.valueOf(newIdn));
                            int ct =
                                db.execUpd("updmstk", "update mstk set vnm = ?,qty=? where idn = ?",
                                           ary);
                        }

                        for (int j = 0; j < vwPrpLst.size(); j++) {
                            String lprp = (String)vwPrpLst.get(j);
                            String val =
                                util.nvl(req.getParameter(lprp + "_" + i));
                            if (lprp.equals("CRTWT") || lprp.equals("RTE")) {
                                if (lprp.equals("RTE")) {
                                    if (val.equals(""))
                                        val = "0";
                                    double rteVal =
                                        Double.parseDouble(util.nvl(val, "0"));
                                    double calRte =
                                        util.roundToDecimals(rteVal + lagat,
                                                             2);
                                    val = String.valueOf(calRte);
                                    if (cnt.equalsIgnoreCase("CM"))
                                        val =
util.getMixPri(String.valueOf(newIdn));
                                }
                            }
                            ary = new ArrayList();
                            ary.add(String.valueOf(newIdn));
                            ary.add(lprp);
                            ary.add(val);
                            String stockUpd =
                                "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                            int ct = db.execCallDir("stockUpd", stockUpd, ary);

                            if (!idn.equals("")) {

                                ary = new ArrayList();
                                ary.add(issId);
                                ary.add(String.valueOf(newIdn));
                                ary.add(lprp);
                                ary.add(val);
                                String rtbPktPrp =
                                    "MIX_IR_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn => ?, pPrp => ?, pVal => ?)";
                                ct =
 db.execCallDir("stockUpd", rtbPktPrp, ary);
                            }


                        }
                        ary = new ArrayList();
                        ary.add(String.valueOf(newIdn));
                        int ct =
                            db.execCall("apply_rtn_prp", "STK_SRT(?)", ary);


                        if (lotIdn.equals("")) {

                            String lotDsc =
                                "select idn from mlot where dsc ='" + lotNo +
                                "'";
                            ArrayList params = new ArrayList();

                            ArrayList rsLst =
                                db.execSqlLst("lotDsc", lotDsc, params);
                            PreparedStatement pst =
                                (PreparedStatement)rsLst.get(0);
                            ResultSet rs = (ResultSet)rsLst.get(1);
                            while (rs.next()) {
                                lotIdn = rs.getString("idn");
                            }
                            rs.close();
                            pst.close();
                            if (!lotIdn.equals("")) {
                                ary = new ArrayList();
                                ary.add(lotIdn);
                                ary.add(qty);
                                ary.add(String.valueOf(newIdn));
                                int ut =
                                    db.execUpd("update mstk", "update mstk set mlot_idn=? , qty=? where idn=?",
                                               ary);

                            }
                        }
                        if (idn.equals("")) {

                            ary = new ArrayList();
                            ary.add(issId);
                            ary.add(mstkIdn);
                            ary.add(String.valueOf(newIdn));
                            String issPkt =
                                "MIX_IR_PKG.ISS_PKT(pIssId => ?, pRtPkt => ?, pStkIdn => ?)";
                            ct = db.execCall("iss Pkt", issPkt, ary);
                        }


                    }
                }


            }
            ary = new ArrayList();
            ary.add(issId);
            ary.add(mstkIdn);
            ArrayList out = new ArrayList();
            out.add("V");
            out.add("V");

            String verifyPkt =
                "MIX_IR_PKG.VERIFY_PKT(pIssId => ?, pRtPkt => ?, pVerified => ?, pMsg => ?)";
            CallableStatement cts =
                db.execCall("iss Pkt", verifyPkt, ary, out);
            String isVerify = cts.getString(ary.size() + 1);
            String pMsg = cts.getString(ary.size() + 2);
            ArrayList msgLst = new ArrayList();
            msgLst.add(isVerify);
            msgLst.add(pMsg);
            req.setAttribute("msgLst", msgLst);

            req.setAttribute("stt", stt);
            req.setAttribute("grp", grp);
            req.setAttribute("vnm", pvnm);
            req.setAttribute("mdl", mdl);
            req.setAttribute("mstkIdn", mstkIdn);
            req.setAttribute("issIdn", issId);
            req.setAttribute("verify", "y");
            return SplitStone(am, form, req, res);

        }
    }

    public ActionForward unLockPkt(ActionMapping am, ActionForm af,
                                   HttpServletRequest req,
                                   HttpServletResponse response) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());
        db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");

        String stkIdn = req.getParameter("stkIdn");
        String issId = req.getParameter("issId");

        String updateIssDtl =
            "update iss_rtn_dtl set stt=? where iss_stk_idn=? and iss_id=?";
        ArrayList ary = new ArrayList();
        ary.add("IS");
        ary.add(stkIdn);
        ary.add(issId);
        int upd = db.execCall("updateissDtl", updateIssDtl, ary);
        if (upd > 0) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write("<message>yes</message>");
        } else {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write("<message>No</message>");
        }


        return null;
    }
    
    
  
    public ActionForward ExitsSplit(ActionMapping am, ActionForm form,
                                   HttpServletRequest req,
                                   HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            rtnPg = init(req, res, session, util);
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            SplitReturnForm udf = (SplitReturnForm)form;
            GenericInterface genericInt = new GenericImpl();
            ArrayList assortSrchList = genericInt.genricSrch(req,res,"RGH_MRG_SRCH","RGH_MRG_SRCH");
            info.setGncPrpLst(assortSrchList);
            String issID = util.nvl(req.getParameter("issID"));
            ArrayList  nextSttList  = InStatus(req, res, issID);
            session.setAttribute("InsttList", nextSttList);
            return am.findForward("exitsSplit");
        }
    }
    public ActionForward fecthExit(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

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
            SplitReturnForm udf = (SplitReturnForm)form;
         String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            GenericInterface genericInt = new GenericImpl();
        HashMap mprp = info.getMprp();
          HashMap prp = info.getPrp();
        String stoneId = util.nvl((String)udf.getValue("vnmLst"));
        String stt = util.nvl((String)udf.getValue("stt"));
            ArrayList genricSrchLst = genericInt.genricSrch(req,res,"RGH_MRG_SRCH","RGH_MRG_SRCH");
            info.setGncPrpLst(genricSrchLst);
       
        HashMap params = new HashMap();
        params.put("stt", stt);
        params.put("vnm",stoneId);
        params.put("Pkt_ty", "RGH");
        HashMap paramsMap = new HashMap();
        for(int i=0;i<genricSrchLst.size();i++){
            ArrayList prplist =(ArrayList)genricSrchLst.get(i);
            String lprp = (String)prplist.get(0);
            String flg= (String)prplist.get(1);
            String typ = util.nvl((String)mprp.get(lprp+"T"));
            String prpSrt = lprp ;  
            if(flg.equals("M")) {
            
                ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                for(int j=0; j < lprpS.size(); j++) {
                String lSrt = (String)lprpS.get(j);
                String lVal = (String)lprpV.get(j);    
                String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                if(!reqVal1.equals("")){
                paramsMap.put(lprp + "_" + lVal, reqVal1);
                }
                   
                }
            }else if(flg.equals("SM")){
                String reqVal1 = util.nvl((String)udf.getValue(lprp));
                    if(!reqVal1.equals("")){
                    String[] srtLst = reqVal1.split(",");
                    if(srtLst.length>0) {  
                    paramsMap.put(lprp, srtLst);
                    }}
            }else{
            String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
            String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
            if(typ.equals("T")){
                fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                fldVal2 = fldVal1;
            }
            if(fldVal2.equals(""))
            fldVal2=fldVal1;
            if(!fldVal1.equals("") && !fldVal2.equals("")){
                paramsMap.put(lprp+"_1", fldVal1);
                paramsMap.put(lprp+"_2", fldVal2);
            }
            }
        }
        HashMap stockList = null;
        if(paramsMap.size()>0){
        paramsMap.put("stt", stt);
        paramsMap.put("mdl", "RGH_VIEW");
        paramsMap.put("PRCD", "ROUGH");
        util.genericSrch(paramsMap);
        stockList = SearchResult(req,res, "","RGH_VIEW");
        }else{
          stockList = FecthResultExits(req,res, params);
        }
          
        req.setAttribute("stockList", stockList);
        req.setAttribute("view", "Y");
           
        return ExitsSplit(am, form, req, res);
        }
    }
    
    public ActionForward IssueExitsPkt(ActionMapping am, ActionForm form,
                                   HttpServletRequest req,
                                   HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            rtnPg = init(req, res, session, util);
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            SplitReturnForm udf = (SplitReturnForm)form;
            ArrayList stkIdnList = new ArrayList();
            String stkIdnstr="";
            Enumeration reqNme = req.getParameterNames(); 
             while (reqNme.hasMoreElements()) {
                String paramNm = (String) reqNme.nextElement();
                if(paramNm.indexOf("cb_stk_") > -1) {
                    String stk_idn = req.getParameter(paramNm);
                    stkIdnList.add(stk_idn);
                        stkIdnstr = stkIdnstr+","+stk_idn;
                    }
              }
             rtnPg = "exitsSplit";
            String issId = util.nvl(req.getParameter("issID"));
            if(!stkIdnstr.equals(""))
                stkIdnstr = stkIdnstr.replaceFirst(",", "");
            String msg = "";
            if (stkIdnList.size() > 0) {
                String insScanPkt = " insert into gt_srch_rslt(stk_idn,flg) select vnm ,'S' from TABLE(PARSE_TO_TBL('"+stkIdnstr+"'))";
                 int ct = db.execDirUpd(" ins scan", insScanPkt,new ArrayList());
                if(ct>0){
                db.setAutoCommit(false);
                boolean isCommit = true;
                    try {
                        ArrayList params = new ArrayList();
                        params.add(issId);
                        params.add("1");
                        params.add("IS");
                        String issuePkt =
                            "ISS_RTN_PKG.ALL_ISS_PKT(pIssId =>?, pGrp => ?, pStt => ?)";
                        ct = db.execCall("issuePkt", issuePkt, params);
                    } catch (Exception e) {
                        // TODO: Add catch code
                        isCommit = false;
                        e.printStackTrace();
                        
                    } finally {
                       
                        db.setAutoCommit(true);
                    }
                    if(isCommit){
                        db.doCommit();
                      msg="SUCCESS";
                        rtnPg="exitsSplit";
                    }else{
                        db.doRollBack();
                        msg="Sorry no stone found for process";
                        rtnPg="exitsSplit";
                    }
                }
                
            }else
                msg="Sorry no stone found for process";
    
    req.setAttribute("msg", msg);
            return am.findForward(rtnPg);
        }
     }
    public ActionForward bulkSplit(ActionMapping am, ActionForm form,
                                   HttpServletRequest req,
                                   HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            rtnPg = init(req, res, session, util);
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            SplitReturnForm udf = (SplitReturnForm)form;
            String issID = util.nvl(req.getParameter("issID"));
            String mstkIdn = util.nvl(req.getParameter("mstkIdn"));
            String mdl = util.nvl(req.getParameter("mdl"));
            String stt = util.nvl(req.getParameter("stt"));
            String delQ = " Delete from gt_srch_rslt ";
            int ct = db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            String inStt="";
            String isStt = "";
            String otStt="";
            String typ="";
            String inSttSql = "select a.IN_STT , a.is_stt ,a.ot_stt , a.typ from mprc a , iss_rtn b\n" + 
            "where a.idn=b.prc_id and b.iss_id=?";
            ArrayList ary = new ArrayList();
            ary.add(issID);
            ArrayList rsList = db.execSqlLst("inStt", inSttSql, ary);
            PreparedStatement pst1=(PreparedStatement)rsList.get(0);
            ResultSet rs1 = (ResultSet)rsList.get(1);
            while(rs1.next()){
                inStt = util.nvl(rs1.getString("IN_STT"));
                isStt=util.nvl(rs1.getString("is_stt"));
                typ=util.nvl(rs1.getString("typ"));
                otStt = util.nvl(rs1.getString("ot_stt"));
            }
            
            rs1.close();
            pst1.close();
            ary = new ArrayList();
            String gtInsert =
                " Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk , quot, sk1 ) \n" +
                "       select  d.pkt_ty, d.idn, d.vnm,  d.dte, d.stt , 'I' , d.qty , d.cts , c.iss_id , d.tfl3 , nvl(d.upr,cmp), d.sk1 \n" +
                "        from mprc a , iss_rtn b , iss_rtn_dtl c ,mstk d where\n" +
                "         a.idn=b.PRC_ID and b.ISS_ID=c.ISS_ID and c.stt='IS'and c.iss_id=? \n" +
                "         and c.iss_stk_idn=d.idn ";
            ary.add(issID);
            if (!mstkIdn.equals("")) {
                gtInsert = gtInsert + " and c.iss_stk_idn = ?";
                ary.add(mstkIdn);
            }
            if(inStt.equals("NA")){
                gtInsert = gtInsert + " and c.flg1 in (select b.in_stt from prc_to_prc a , mprc b\n" + 
                "where a.prc_to_id=b.idn and a.flg1=? and b.stt=? ) " ;
                ary.add("IN");
                ary.add("A");
            }else
                gtInsert = gtInsert + "  and c.FLG1=a.IN_STT ";

            ct = db.execCallDir("insert gt", gtInsert, ary);


            gtInsert =
                    " Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk , quot, sk1 ) \n" +
                    "       select  d.pkt_ty, d.idn, d.vnm,  d.dte, d.stt , 'P' , d.qty , d.cts , c.iss_id , d.tfl3 , nvl(d.upr,cmp), d.sk1 \n" +
                    "        from mprc a , iss_rtn b , iss_rtn_dtl c ,mstk d where\n" +
                    "         a.idn=b.PRC_ID and b.ISS_ID=c.ISS_ID and c.stt='IS'  and c.iss_id=? \n" +
                    "         and c.iss_stk_idn=d.idn ";
            ary = new ArrayList();
            ary.add(issID);
            if (!mstkIdn.equals("")) {
                gtInsert = gtInsert + " and c.pkt_rt = ?";
                ary.add(mstkIdn);
            } else {
                gtInsert = gtInsert + " and c.FLG1=a.IS_STT ";
            }
            ct = db.execCallDir("insert gt", gtInsert, ary);


            gtInsert =
                    " Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk ,quot,  sk1 ) \n" +
                    "       select  d.pkt_ty, d.idn, d.vnm,  d.dte, d.stt , 'R' , d.qty , d.cts , c.iss_id , d.tfl3 , nvl(d.upr,cmp), d.sk1 \n" +
                    "        from mprc a , iss_rtn b , iss_rtn_dtl c ,mstk d where\n" +
                    "         a.idn=b.PRC_ID and b.ISS_ID=c.ISS_ID and c.stt='RT'  and c.iss_id=? \n" +
                    "         and c.iss_stk_idn=d.idn ";
            ary = new ArrayList();
            ary.add(issID);
            if (!mstkIdn.equals("")) {
                gtInsert = gtInsert + " and c.pkt_rt  = ?";
                ary.add(mstkIdn);
            } else {
                gtInsert = gtInsert + " and c.FLG1=a.IS_STT ";
            }
            ct = db.execCallDir("insert gt", gtInsert, ary);
            ArrayList prpPrcList = new ArrayList();
            ArrayList comprpPrcList = new ArrayList();
            ArrayList dfltPrpList = new ArrayList();
                    try {

                String issStt = null;
                String prpDtlLst =
                    "select c.flg, c.mprp ,a.prc_id, b.is_stt from iss_rtn a , mprc b , prc_prp_alw c\n" +
                    "where a.PRC_ID=b.idn and a.iss_id=? \n" +
                    "and b.idn=c.PRC_IDN  and c.flg <> 'RTNEDIT' order by c.srt ";
                ary = new ArrayList();
                ary.add(issID);

                ArrayList outLst = db.execSqlLst("prp List", prpDtlLst, ary);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
               
                    while (rs.next()) {
                        String flg = util.nvl(rs.getString("flg"));
                        String mprp = util.nvl(rs.getString("mprp"));
                        issStt = util.nvl(rs.getString("is_stt"));
                        if (!flg.equals("RTNEDIT"))
                            prpPrcList.add(mprp);
                        if (flg.equals("FTCH"))
                            dfltPrpList.add(mprp);
                        if(flg.equals("COMP"))
                            comprpPrcList.add(mprp);

                    }

                    rs.close();
                    pst.close();
                } catch (SQLException sqle) {
                    // TODO: Add catch code
                    sqle.printStackTrace();
                }
            
            if(prpPrcList.size()<=0){
                prpPrcList = ASPrprViw(req, res, mdl);
                dfltPrpList = ASPrprViw(req, res, "MIX_DFLT");
             }
            HashMap dfltMap = new HashMap();
            session.setAttribute("SPLITVIEWLST", prpPrcList);
            req.setAttribute("SPLITCOMPVIEWLST", comprpPrcList);
            String srchQ =
                " select stk_idn , pkt_ty,stt, sk1, vnm, pkt_dte, stt , qty , cts , quot, trunc(cts*quot,3) amt, srch_id ,rmk , byr.get_nm(rln_idn) emp , flg  ";
            String rsltQ = srchQ + " from gt_srch_rslt order by flg,sk1 ";

            ary = new ArrayList();
            ArrayList outLst = db.execSqlLst(" get vals", rsltQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            String Pflg = "";
            HashMap splitPrpMap = new HashMap();
            ArrayList pktList = new ArrayList();
            double vrfyCts = 0.0;
          double vrfyAmt = 0.0;
            int vrfyQty = 0;
            
            while (rs.next()) {
                String lflg = rs.getString("flg");
                if (Pflg.equals(""))
                    Pflg = lflg;
                if (!Pflg.equals(lflg)) {
                    splitPrpMap.put(Pflg, pktList);
                    splitPrpMap.put(Pflg + "C",String.valueOf(util.roundToDecimals(vrfyCts, 3)));
                    splitPrpMap.put(Pflg + "Q", String.valueOf(vrfyQty));
                    splitPrpMap.put(Pflg + "V", String.valueOf(vrfyAmt));
                    splitPrpMap.put(Pflg + "A", String.valueOf(util.roundToDecimals(vrfyAmt/vrfyCts,2)));
                    pktList = new ArrayList();
                    Pflg = lflg;
                    vrfyCts = 0.0;
                    vrfyQty = 0;
                    vrfyAmt=0.0;
                }

                String cts = util.nvl(rs.getString("cts"), "0");
                double ctsd = Double.parseDouble(cts);
                vrfyCts = vrfyCts + ctsd;
                String qty = util.nvl(rs.getString("qty"), "0");
                int qtyd = Integer.parseInt(qty);
                vrfyQty = vrfyQty + qtyd;
              String amt = util.nvl(rs.getString("amt"), "0");
              double amtd = Double.parseDouble(amt);
              vrfyAmt = vrfyAmt + amtd;
                String stkIdn = util.nvl(rs.getString("stk_idn"));
                GtPktDtl pktPrpMap = new GtPktDtl();
                pktPrpMap.setValue("stt", util.nvl(rs.getString("stt")));
                pktPrpMap.setValue("emp", util.nvl(rs.getString("emp")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.setValue("vnm", vnm);
                pktPrpMap.setValue("stk_idn", stkIdn);
                pktPrpMap.setValue("qty", util.nvl(rs.getString("qty")));
                pktPrpMap.setValue("cts", cts);
                pktPrpMap.setSk1(new BigDecimal(util.nvl(rs.getString("sk1"),
                                                         "0")));
                pktPrpMap.setValue("stt", util.nvl(rs.getString("stt")));
                pktPrpMap.setValue("issIdn",
                                   util.nvl(rs.getString("srch_id")));
                if (prpPrcList != null && prpPrcList.size() > 0) {
                    String vwLst = prpPrcList.toString();
                    vwLst = vwLst.replaceAll("\\[", "");
                    vwLst = vwLst.replaceAll("\\]", "");
                    vwLst = vwLst.replaceAll(",", "','");
                    vwLst = vwLst.replaceAll(" ", "");
                    vwLst = "('" + vwLst + "')";

                    String stkdtlSql =
                        " select a.mprp, decode(b.dta_typ, 'C', a.val, 'N', a.num, 'D', a.dte, a.txt) val \n" +
                        "from stk_dtl a, mprp b\n" +
                        "where a.mprp = b.prp and b.prp in " + vwLst +
                        " and a.grp=? and a.mstk_idn =? ";
                    ArrayList params = new ArrayList();
                    params.add("1");
                    params.add(stkIdn);
                    ArrayList outLst1 =
                        db.execSqlLst("result", stkdtlSql, params);
                    PreparedStatement pst2= (PreparedStatement)outLst1.get(0);
                    ResultSet rs2 = (ResultSet)outLst1.get(1);
                    while (rs2.next()) {
                        String lprp = util.nvl(rs2.getString("mprp"));
                        String lVal = util.nvl(rs2.getString("val"));
                        pktPrpMap.setValue(lprp, lVal);
                        if (dfltPrpList.contains(lprp)) {
                            String dfltVal =
                                util.nvl((String)dfltMap.get(lprp));
                            if (dfltVal.equals(""))
                                dfltVal = lVal;
                            if (dfltVal.equals(lVal))
                                dfltMap.put(lprp, lVal);
                            else
                                dfltMap.put(lprp, "DIFF");
                        }

                    }
                    rs2.close();
                    pst2.close();

                }
                pktList.add(pktPrpMap);

            }
            rs.close();
            pst.close();

            if (!Pflg.equals("")) {
                splitPrpMap.put(Pflg, pktList);
                splitPrpMap.put(Pflg + "C",
                                String.valueOf(util.roundToDecimals(vrfyCts,
                                                                    3)));
                splitPrpMap.put(Pflg + "Q", String.valueOf(vrfyQty));
              splitPrpMap.put(Pflg + "V", String.valueOf(vrfyAmt));
              splitPrpMap.put(Pflg + "A", String.valueOf(util.roundToDecimals(vrfyAmt/vrfyCts,2)));
            }
            ArrayList nextSttList = new ArrayList();
            HashMap prcSttMap = new HashMap();
            prcSttMap.put("key", otStt);
            prcSttMap.put("val", otStt);
            nextSttList.add(prcSttMap);
            if(otStt.equals("NA")){
             nextSttList  = OutStatus(req, res, issID);
            }
            
            req.setAttribute("mdl", mdl);
            req.setAttribute("ISSTT", isStt);
            req.setAttribute("TYP", typ);
            req.setAttribute("SPLITPRPMAP", splitPrpMap);
            session.setAttribute("NextSttList", nextSttList);

            session.setAttribute("SPLITDIFF", dfltMap);
            return am.findForward("bulkSplit");
        }
    }


    public ActionForward bulkSplitRtn(ActionMapping am, ActionForm form,
                                      HttpServletRequest req,
                                      HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            rtnPg = init(req, res, session, util);
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            SplitReturnForm udf = (SplitReturnForm)form;
            Enumeration reqNme = req.getParameterNames();
            String issID = util.nvl(req.getParameter("issID"));
            String dpApply = util.nvl(req.getParameter("dpApply"));
            ArrayList stkIdnList = new ArrayList();

            while (reqNme.hasMoreElements()) {
                String paramNm = (String)reqNme.nextElement();
                if (paramNm.indexOf("cb_pkt_") > -1) {
                    String val = util.nvl(req.getParameter(paramNm));
                    stkIdnList.add(val);
                }
            }
            String msg = "";
            if (stkIdnList.size() > 0) {

                db.setAutoCommit(false);
                boolean isCommit = true;


                try {

                    String in_stt = "", ot_stt = "", is_stt = "";
                    double wt_diff = 0.0;
                    String mprcStt =
                        " select ot_stt, in_stt,is_stt , nvl(wt_diff,0.02) wt_diff " +
                        " from mprc a, iss_rtn b where a.idn = b.prc_id and b.iss_id = ? ";
                    ArrayList ary = new ArrayList();
                    ary.add(issID);
                    ArrayList outLst =
                        db.execSqlLst(" get vals", mprcStt, ary);
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs = (ResultSet)outLst.get(1);
                    while (rs.next()) {
                        in_stt = util.nvl(rs.getString("in_stt"));
                        ot_stt = util.nvl(rs.getString("ot_stt"));
                     
                        is_stt = util.nvl(rs.getString("is_stt"));
                        wt_diff = rs.getDouble("wt_diff");
                    }
                    rs.close();
                    pst.close();
                    for (int i = 0; i < stkIdnList.size(); i++) {
                        String stkIdn = (String)stkIdnList.get(i);
                        String stt = req.getParameter("cb_stt_"+stkIdn);

                        try {
                            String updatissRtn =
                                " Update iss_rtn_dtl set stt = ? , AUD_MODIFIED_BY=pack_var.get_usr where iss_id = ? and iss_stk_idn=?";
                            ary = new ArrayList();
                            ary.add("RT");
                            ary.add(issID);
                            ary.add(stkIdn);
                            int ct =
                                db.execDirUpd("updatissRtn", updatissRtn, ary);
                            if (ct > 0) {
                              
                                    String updateMstk =
                                        "Update mstk set stt = ? where idn= ?";
                                    
                                    ary = new ArrayList();
                                    ary.add(stt);
                                    ary.add(stkIdn);
                                    ct = db.execDirUpd("updateMstk", updateMstk, ary);
                                    if (ct >  0)
                                        isCommit = true;
                                    else
                                        isCommit = false;
                                
                            }
                        } catch (Exception e) {
                            // TODO: Add catch code
                            isCommit = false;
                            e.printStackTrace();
                        }


                    }

                    if (isCommit) {

                        try {
                            double issCts=0;
                            double rtnCts=0;
                            int cnt=1;
                            String verified =
                               "select sum(nvl(iss_num, 0))  isscts\n" + 
                               " from iss_rtn_dtl d, iss_rtn_prp p where d.iss_id = p.iss_id \n" + 
                               " and d.ISS_STK_IDN=p.iss_stk_idn and p.mprp = ? and d.iss_id = ? and pkt_rt is null\n" ;
                              
                              
                            ary = new ArrayList();
                            ary.add("CRTWT");
                            ary.add(issID);
                            outLst = db.execSqlLst(" get vals", verified, ary);
                            pst = (PreparedStatement)outLst.get(0);
                            rs = (ResultSet)outLst.get(1);
                            while (rs.next()) {
                                issCts = rs.getDouble("issCts");
                            }
                            rs.close();
                            pst.close();
                            
                            verified = "select sum(nvl(rtn_num, 0))  rtncts\n" + 
                               " from iss_rtn_dtl d, iss_rtn_prp p where d.iss_id = p.iss_id \n" + 
                               " and d.ISS_STK_IDN=p.iss_stk_idn and p.mprp = ? and d.iss_id = ? and pkt_rt is not null"   ; 
                            ary = new ArrayList();
                            ary.add("CRTWT");
                            ary.add(issID);
                            outLst = db.execSqlLst(" get vals", verified, ary);
                            pst = (PreparedStatement)outLst.get(0);
                            rs = (ResultSet)outLst.get(1);
                           while (rs.next()) {
                               
                               rtnCts = rs.getDouble("rtncts");
                            }
                            rs.close();
                            pst.close();
                            
                                double diff = issCts - rtnCts;
                                if (diff <= wt_diff) {
                                    String updateIssPkt =
                                        " Update iss_rtn_dtl set stt = ? , AUD_MODIFIED_BY=pack_var.get_usr where iss_id = ?  and pkt_rt is null ";
                                    ary = new ArrayList();
                                    ary.add("RT");
                                    ary.add(issID);
                                    int ct = db.execDirUpd("updateIssPkt", updateIssPkt,
                                                      ary);
                                    
                                    
                                    if (ct > 0) {
                                        String updateMstk =
                                            " Update mstk set stt = ? where idn in (select iss_stk_idn from iss_rtn_dtl where iss_id = ? and pkt_rt is null) ";
                                        ary = new ArrayList();
                                        ary.add(is_stt +"_MRG");
                                        ary.add(issID);
                                        ct =db.execDirUpd("updateMstk", updateMstk, ary);
                                        if (ct < 0)
                                            isCommit = false;
                                        if(dpApply.equals("Y")){
                                        ary = new ArrayList();
                                        ary.add(issID);
                                        ct = db.execCallDir("CPCOST", "DP_APPLY_IR_COST(pIssId=>?)", ary);
                                        }
                                    } else
                                        isCommit = false;
                                    if (isCommit) {
                                        msg ="Return done Successfully for  issue id " + issID;
                                    } else {
                                        msg = "Some error in process";
                                        db.doRollBack();
                                    }

                                } else {

                                    msg ="Packet Return Successfully Current Carat on Issue" + diff;
                                }


                           

                        } catch (SQLException sqle) {
                            // TODO: Add catch code
                            sqle.printStackTrace();
                            isCommit = false;
                        }
                    }

                } catch (SQLException sqle) {
                    // TODO: Add catch code
                    sqle.printStackTrace();
                    isCommit = false;
                    db.doRollBack();
                } finally {
                    db.setAutoCommit(true);
                }
                if (isCommit)
                    db.doCommit();
                else
                    db.doRollBack();

            } else {
                msg = "Please Specified packets for return..";
            }
            req.setAttribute("msg", msg);
            return bulkSplit(am, form, req, res);
        }
    }

    public ActionForward bulkSavePkt(ActionMapping am, ActionForm form,
                                     HttpServletRequest req,
                                     HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            rtnPg = init(req, res, session, util);
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            SplitReturnForm udf = (SplitReturnForm)form;
            ArrayList vwPrpLst =
                (ArrayList)session.getAttribute("SPLITVIEWLST");
            String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
            String issIdn = util.nvl(req.getParameter("issID"));
            String cts = util.nvl(req.getParameter("CB_CRTWT"));
            String qty = util.nvl(req.getParameter("CB_QTY"));
            String stt = util.nvl(req.getParameter("ISSTT"));
            String lotNo = util.nvl(req.getParameter("CB_LOTNO"));
            String pkt_ty = util.nvl(req.getParameter("cb_pkt_ty"));
            String mstkIdn = util.nvl(req.getParameter("mstkIdn"), "0");
            if (mstkIdn.equals(""))
                mstkIdn = "0";
            String lotIdn = "";
            int newIdn = 0;
            String vnm = "";
            if (!cts.equals("")) {

                if (!lotNo.equals("")) {
                    String lotDsc =
                        "select idn from mlot where dsc ='" + lotNo + "'";
                    ArrayList params = new ArrayList();

                    ArrayList rsLst = db.execSqlLst("lotDsc", lotDsc, params);
                    PreparedStatement pst = (PreparedStatement)rsLst.get(0);
                    ResultSet rs = (ResultSet)rsLst.get(1);
                    while (rs.next()) {
                        lotIdn = rs.getString("idn");
                    }
                    rs.close();
                    pst.close();

                }
                String insMst =
                    "MIX_PKG.GEN_PKT(pStt => ?,pQty => ? ,pPktRt => ?, pPktTyp => ?,pIdn =>?,pVnm =>?)";
                ArrayList ary = new ArrayList();
                ary.add(stt);
                ary.add(qty);
                ary.add(mstkIdn);
                ary.add(pkt_ty);
                if (!lotIdn.equals("")) {
                    insMst =
                            "MIX_PKG.GEN_PKT(pStt => ?,pQty => ? ,pPktRt => ?, pPktTyp => ?,pLotIdn => ? , pIdn =>?,pVnm =>?)";
                    ary.add(lotIdn);
                }
                ArrayList out = new ArrayList();
                out.add("I");
                out.add("V");
                CallableStatement cst =
                    db.execCall("findMstkId", insMst, ary, out);
                newIdn = cst.getInt(ary.size() + 1);
                vnm = cst.getString(ary.size() + 2);
              cst.close();
              cst=null;
                if (newIdn > 0) {
                    ary = new ArrayList();
                    ary.add(issIdn);
                    ary.add(mstkIdn);
                    ary.add(String.valueOf(newIdn));
                    for (int j = 0; j < vwPrpLst.size(); j++) {
                        String lprp = (String)vwPrpLst.get(j);
                        String val = util.nvl(req.getParameter("CB_" + lprp));
                        if (lprp.equals("RTE")) {
                            if (cnt.equalsIgnoreCase("CM") && val.equals(""))
                                val = util.getMixPri(String.valueOf(newIdn));
                        }
                        ary = new ArrayList();
                        ary.add(String.valueOf(newIdn));
                        ary.add(lprp);
                        ary.add(val);
                        String stockUpd =
                            "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                        int ct = db.execCallDir("stockUpd", stockUpd, ary);
                        
                    }
                    
                    if(cnt.equalsIgnoreCase("lif")){
                        ary = new ArrayList();
                        ary.add(String.valueOf(newIdn));
                        int ct = db.execCallDir("exRteApply", "DP_APPLY_EXH_RTE(STK_IDN=>?)", ary); 
                        
                    }

                 
                 


                    if (lotIdn.equals("")) {

                        String lotDsc =
                            "select idn from mlot where dsc ='" + lotNo + "'";
                        ArrayList params = new ArrayList();

                        ArrayList rsLst =
                            db.execSqlLst("lotDsc", lotDsc, params);
                        PreparedStatement pst =
                            (PreparedStatement)rsLst.get(0);
                        ResultSet rs = (ResultSet)rsLst.get(1);
                        while (rs.next()) {
                            lotIdn = rs.getString("idn");
                        }
                        rs.close();
                        pst.close();
                        if (!lotIdn.equals("")) {
                            ary = new ArrayList();
                            ary.add(lotIdn);
                            ary.add(qty);
                            ary.add(String.valueOf(newIdn));
                            int ut =
                                db.execUpd("update mstk", "update mstk set mlot_idn=? , qty=? where idn=?",
                                           ary);

                        }
                    }

                    ary = new ArrayList();
                    ary.add(issIdn);
                    ary.add(mstkIdn);
                    ary.add(String.valueOf(newIdn));
                    String issPkt =
                        "MIX_IR_PKG.ISS_PKT(pIssId => ?, pRtPkt => ?, pStkIdn => ?)";
                   int ct = db.execCall("iss Pkt", issPkt, ary);
                }
            }

            return bulkSplit(am, form, req, res);
        }

    }

    public ActionForward prcIssue(ActionMapping am, ActionForm af,
                                  HttpServletRequest req,
                                  HttpServletResponse response) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());
        db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        StringBuffer sb = new StringBuffer();
        String prcId = req.getParameter("prcId");
        int fav = 0;
        String inStt = "";
        String checkFlg = "select in_stt from mprc where idn=?";
        ArrayList ary = new ArrayList();
        ary.add(prcId);
        ArrayList outLst = db.execSqlLst("issId", checkFlg, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        if(rs.next()) {
            inStt=util.nvl(rs.getString("in_stt"));
        }
        rs.close();
        pst.close();
        String issIdDtl =
            "select DISTINCT b.ISS_ID from mprc a , iss_rtn b , iss_rtn_dtl c\n" +
            "where a.idn=b.PRC_ID and b.ISS_ID=c.ISS_ID and c.stt='IS' and c.FLG1=a.IN_STT and a.IDN=?  order by b.iss_id desc";

        ary = new ArrayList();
        ary.add(prcId);
        if(inStt.equals("NA")){
            issIdDtl ="select DISTINCT b.ISS_ID from mprc a , iss_rtn b , iss_rtn_dtl c\n" + 
            "where a.idn=b.PRC_ID and b.ISS_ID=c.ISS_ID and c.stt=? and  a.IDN=?\n" + 
            "and c.flg1 in (select b.in_stt from prc_to_prc a , mprc b\n" + 
            "where a.prc_to_id=b.idn and a.flg1=? and b.stt=?)";
            ary = new ArrayList();
            ary.add("IS");
            ary.add(prcId);
            ary.add("IN");
            ary.add("A");
        }
         
        outLst = db.execSqlLst("issId", issIdDtl, ary);
         pst = (PreparedStatement)outLst.get(0);
       rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String issId = rs.getString("ISS_ID");
            sb.append("<issId>");
            sb.append("<id>" + issId + "</id>");
            sb.append("</issId>");
            fav = 1;
        }
        rs.close();
        pst.close();
        if (fav == 1)
            response.getWriter().write("<issIds>" + sb.toString() +
                                       "</issIds>");
        return null;
    }

    public ActionForward mizSzRng(ActionMapping am, ActionForm af,
                                  HttpServletRequest req,
                                  HttpServletResponse response) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());
        db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        StringBuffer sb = new StringBuffer();
        String prcId = req.getParameter("prcId");
        int fav = 0;
        String issIdDtl = "select PCS_FR,PCS_TO from msz_pcs where dsc=?\n";

        ArrayList ary = new ArrayList();
        ary.add(prcId);
        ArrayList outLst = db.execSqlLst("issId", issIdDtl, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String issId = rs.getString("ISS_ID");
            sb.append("<issId>");
            sb.append("<id>" + issId + "</id>");
            sb.append("</issId>");
            fav = 1;
        }
        rs.close();
        pst.close();
        if (fav == 1)
            response.getWriter().write("<issIds>" + sb.toString() +
                                       "</issIds>");
        return null;
    }

    public ActionForward SplitStone(ActionMapping am, ActionForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            rtnPg = init(req, res, session, util);
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
            String stkIdn = util.nvl(req.getParameter("mstkIdn"));
            if (stkIdn.equals(""))
                stkIdn = util.nvl((String)req.getAttribute("mstkIdn"));

            String vnm = util.nvl(req.getParameter("vnm"));
            if (vnm.equals(""))
                vnm = util.nvl((String)req.getAttribute("vnm"));

            String issId = util.nvl(req.getParameter("issIdn"));
            if (issId.equals(""))
                issId = util.nvl((String)req.getAttribute("issIdn"));

            String grp = util.nvl(req.getParameter("grp"));
            if (grp.equals(""))
                grp = util.nvl((String)req.getAttribute("grp"));

            String stt = util.nvl(req.getParameter("stt"));
            if (stt.equals(""))
                stt = util.nvl((String)req.getAttribute("stt"));

            String ctsval = util.nvl(req.getParameter("ttlcts"), "0");
            if (ctsval.equals("0"))
                ctsval = util.nvl((String)req.getAttribute("ttlcts"), "0");

            String qtyVal = util.nvl(req.getParameter("ttlQty"), "0");
            if (qtyVal.equals("0"))
                qtyVal = util.nvl((String)req.getAttribute("ttlQty"), "0");
            String mdl = util.nvl(req.getParameter("mdl"));
            if (mdl.equals(""))
                mdl = util.nvl((String)req.getAttribute("mdl"), "RGH_VIEW");
            boolean isSplit = false;
            ArrayList ary = null;
            String delQ = " Delete from gt_srch_rslt ";
            int ct = db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            ArrayList issEditLst = new ArrayList();
            ArrayList fetchLst = new ArrayList();
            String issStt = null;
            String prpDtlLst =
                "select c.flg, c.mprp , b.is_stt from iss_rtn a , mprc b , prc_prp_alw c\n" +
                "where a.PRC_ID=b.idn and a.iss_id=? \n" +
                "and b.idn=c.PRC_IDN  order by c.srt ";
            ary = new ArrayList();
            ary.add(issId);

            ArrayList outLst = db.execSqlLst("prp List", prpDtlLst, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            try {
                while (rs.next()) {
                    String flg = util.nvl(rs.getString("flg"));
                    String mprp = util.nvl(rs.getString("mprp"));
                    issStt = util.nvl(rs.getString("is_stt"));
                    if (flg.equals("FTCH"))
                        fetchLst.add(mprp);

                    issEditLst.add(mprp);
                    if (flg.equals("SPLIT"))
                        isSplit = true;
                }

                rs.close();
                pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }


            String srchRefQ =
                "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk , rln_idn , sk1,quot ) " +
                " select  a.pkt_ty, a.idn, a.vnm,  a.dte, b.stt , 'Z' , a.qty , a.cts , b.iss_id , a.tfl3 , b.iss_emp_id , a.sk1 , nvl(a.upr,a.cmp) " +
                "   from mstk a , iss_rtn_dtl b where a.idn = b.iss_stk_idn and b.iss_id = ? and b.pkt_rt=?  " +
                " and a.cts > 0   ";


            ary = new ArrayList();
            ary.add(issId);
            ary.add(stkIdn);
            ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
            HashMap stockList = new HashMap();
            HashMap prpMap = new HashMap();
            if (isSplit) {
                session.setAttribute("SPLITVIEWLST", issEditLst);
                stockList = SearchResultList(req, res, "", issEditLst);

                if (fetchLst != null && fetchLst.size() > 0) {
                    String vwLst = fetchLst.toString();
                    vwLst = vwLst.replaceAll("\\[", "");
                    vwLst = vwLst.replaceAll("\\]", "");
                    vwLst = vwLst.replaceAll(",", "','");
                    vwLst = vwLst.replaceAll(" ", "");
                    vwLst = "('" + vwLst + "')";

                    String stkdtlSql =
                        " select a.mprp, decode(b.dta_typ, 'C', a.val, 'N', a.num, 'D', a.dte, a.txt) val \n" +
                        "from stk_dtl a, mprp b\n" +
                        "where a.mprp = b.prp and b.prp in " + vwLst +
                        " and a.grp=? and a.mstk_idn =? ";
                    ArrayList params = new ArrayList();
                    params.add("1");
                    params.add(stkIdn);
                    ArrayList outLst1 =
                        db.execSqlLst("result", stkdtlSql, params);
                    PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
                    ResultSet rs1 = (ResultSet)outLst1.get(1);
                    while (rs1.next()) {

                        prpMap.put(util.nvl(rs1.getString("mprp")),
                                   util.nvl(rs1.getString("val")));
                    }
                    rs1.close();
                    pst1.close();

                }
                req.setAttribute("RootPrpMap", prpMap);
            } else {
                String pktPrp = "DP_GT_PRP_UPD(?)";
                ary = new ArrayList();
                ary.add(mdl);
                ct = db.execCall(" Srch Prp ", pktPrp, ary);
                stockList = SearchResult(req, res, "", mdl);

                String stkDtlPrp =
                    "select a.mprp, decode(b.dta_typ, 'C', a.val, 'N', a.num, 'D', a.dte, a.txt) val \n" +
                    " from stk_dtl a, mprp b, rep_prp c \n" +
                    " where a.mprp = b.prp and b.prp = c.prp and a.grp=1 and c.mdl = 'RGH_DFLT' and a.mstk_idn =? \n" +
                    " order by c.rnk, c.srt";
                ary = new ArrayList();
                ary.add(stkIdn);
                rs = db.execSql("stkDtlPrp", stkDtlPrp, ary);
                while (rs.next()) {
                    prpMap.put(util.nvl(rs.getString("mprp")),
                               util.nvl(rs.getString("val")));
                }
                rs.close();

                req.setAttribute("RootPrpMap", prpMap);
            }

            req.setAttribute("SplitStoneList", stockList);


            if (cnt.equals("FA")) {
                String lastCnt = "select DF_GET_VNM('MIX') lotNum from dual";
                rs = db.execSql("lastCnt", lastCnt, new ArrayList());
                while (rs.next()) {
                    int lotNum = rs.getInt("lotNum");
                    req.setAttribute("lstCnt", lotNum);
                }
                rs.close();
            } else {
                String lastCnt =
                    "select rgh_pkg.get_lot_vnm('" + vnm + "', '.', 'STK') lotNum from dual";
                rs = db.execSql("lastCnt", lastCnt, new ArrayList());
                while (rs.next()) {
                    int lotNum = rs.getInt("lotNum");
                    req.setAttribute("lstCnt", lotNum);
                }
                rs.close();
            }
            req.setAttribute("stt", stt);
            req.setAttribute("isStt", issStt);
            req.setAttribute("grp", grp);
            String VRFCTS = util.nvl((String)req.getAttribute("VRFCTS"), "0");
            double ttlCtsF = Double.parseDouble(ctsval);
            double vrfCts = Double.parseDouble(VRFCTS);
            double balCts = (ttlCtsF - vrfCts);
            balCts = util.roundToDecimals(balCts, 3);
            req.setAttribute("BALCTS", String.valueOf(balCts));
            String VRFQTY = util.nvl((String)req.getAttribute("VRFQTY"), "0");
            int qtyval = Integer.parseInt(qtyVal);
            int vrfQty = Integer.parseInt(VRFQTY);
            int balqty = (qtyval - vrfQty);
            req.setAttribute("BALQTY", String.valueOf(balqty));
            req.setAttribute("mdl", mdl);
            HashMap allPageDtl =
                (info.getPageDetails() == null) ? new HashMap() :
                (HashMap)info.getPageDetails();
            HashMap pageDtl = (HashMap)allPageDtl.get(issStt + "_SPLIT");
            if (pageDtl == null || pageDtl.size() == 0) {
                pageDtl = new HashMap();
                pageDtl = util.pagedef(issStt + "_SPLIT");
                allPageDtl.put(issStt + "_SPLIT", pageDtl);
            }
            info.setPageDetails(allPageDtl);
            return am.findForward("loadPop");

        }
    }

    public ActionForward Return(ActionMapping am, ActionForm form,
                                HttpServletRequest req,
                                HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            rtnPg = init(req, res, session, util);
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            SplitReturnForm udf = (SplitReturnForm)form;
            String lstNme = (String)gtMgr.getValue("lstNmeRGHRTN");
            HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme);
            ArrayList msgLst = new ArrayList();
            if (stockListMap != null && stockListMap.size() > 0) {
                ArrayList stockIdnLst = new ArrayList();
                Set<String> keys = stockListMap.keySet();
                for (String key : keys) {
                    stockIdnLst.add(key);
                }
                String form1 = util.nvl(req.getParameter("form"));
                       String dfPg = "MIX_SPLIT";
                       if(form1.equals("RGH"))
                       dfPg = "RGH_SPLIT";
                HashMap allPageDtl =
                    (info.getPageDetails() == null) ? new HashMap() :
                    (HashMap)info.getPageDetails();
                HashMap pageDtl = (HashMap)allPageDtl.get(dfPg);
                ArrayList pageList =
                    ((ArrayList)pageDtl.get("RGH_TTL") == null) ?
                    new ArrayList() : (ArrayList)pageDtl.get("RGH_TTL");
                boolean isRgh = false;
                if (pageList != null && pageList.size() > 0) {
                    for (int j = 0; j < pageList.size(); j++) {
                        HashMap pageDtlMap = (HashMap)pageList.get(j);
                        String dflt_val =
                            util.nvl((String)pageDtlMap.get("dflt_val"));
                        if (dflt_val.equals("Y")) {
                            isRgh = true;
                        }
                    }
                }
                for (int i = 0; i < stockIdnLst.size(); i++) {
                    String stk_idn = (String)stockIdnLst.get(i);
                    GtPktDtl pktDtl = (GtPktDtl)stockListMap.get(stk_idn);
                    String issIdn = (String)pktDtl.getValue("issIdn");

                    String isChecked =
                        util.nvl((String)udf.getValue("CHK_" + stk_idn));
                    if (isChecked.equals("yes")) {

                        if (isRgh) {
                            
                            String rejcts =
                                util.nvl((String)udf.getValue("REJ_CTS_" +
                                                              stk_idn));
                           
                            ArrayList params = new ArrayList();
                            if(!rejcts.equals("")){
                            params.add(issIdn);
                            params.add(stk_idn);
                            params.add("REJ_CTS");
                            params.add(rejcts);
                            String rtbPktPrp =
                                "MIX_IR_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn => ?, pPrp => ?, pVal => ?)";
                            int ct =
                                db.execCallDir("stockUpd", rtbPktPrp, params);

                            params = new ArrayList();
                            params.add(stk_idn);
                            params.add("REJ_CTS");
                            params.add(rejcts);
                            String stockUpd =
                                "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                            ct = db.execCallDir("stockUpd", stockUpd, params);
                            }
                            String wtLoss =
                                util.nvl((String)udf.getValue("WT_LOSS_" +
                                                              stk_idn));
                            if(!wtLoss.equals("")){
                            params.add(issIdn);
                            params.add(stk_idn);
                            params.add("WT_LOSS");
                            params.add(rejcts);
                            String rtbPktPrp =
                                "MIX_IR_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn => ?, pPrp => ?, pVal => ?)";
                         int  ct =db.execCallDir("stockUpd", rtbPktPrp, params);

                            params = new ArrayList();
                            params.add(stk_idn);
                            params.add("WT_LOSS");
                            params.add(rejcts);
                         String stockUpd =
                                "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                           ct = db.execCallDir("stockUpd", stockUpd, params);
                            }
                            String plan =
                                util.nvl((String)udf.getValue("FNPLN_" +
                                                              stk_idn));
                            if(!plan.equals("")){
                                params.add(issIdn);
                                params.add(stk_idn);
                                params.add("PLAN");
                                params.add(plan);
                                String rtbPktPrp =
                                    "MIX_IR_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn => ?, pPrp => ?, pVal => ?)";
                                int  ct =db.execCallDir("stockUpd", rtbPktPrp, params);

                                params = new ArrayList();
                                params.add(stk_idn);
                                params.add("PLAN");
                                params.add(plan);
                                String stockUpd =
                                    "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                                ct = db.execCallDir("stockUpd", stockUpd, params);
                            }

                        }

                        ArrayList ary = new ArrayList();
                        ary.add(issIdn);
                        ary.add(stk_idn);
                        ArrayList out = new ArrayList();
                        out.add("V");
                        String rtnPkt =
                            "MIX_IR_PKG.RTN_PKT(pIssId => ?, pRtPkt => ?,pMsg=>?)";
                        CallableStatement cts =
                            db.execCall("return Pkt", rtnPkt, ary, out);
                        String pmsg = util.nvl(cts.getString(ary.size() + 1));
                        msgLst.add(pmsg);
                        if(pmsg.indexOf("Successfully")!=-1){
                            ary = new ArrayList();
                            ary.add(issIdn);
                           int ct = db.execCallDir("stockUpd", "DP_APPLY_IR_COST(pIssId => ? )", ary);
                        }
                      
                    }

                }
            } else {
                msgLst.add("Error in process");
            }
            req.setAttribute("msgList", msgLst);
            return am.findForward("load");

        }
    }

    public ArrayList ASPrprViw(HttpServletRequest req, HttpServletResponse res,
                               String mdl) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        ArrayList asViewPrp = (ArrayList)session.getAttribute(mdl);
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            init(req, res, session, util);

            try {
                if (asViewPrp == null) {

                    asViewPrp = new ArrayList();
                    ArrayList ary = new ArrayList();
                    ary.add(mdl);
                    ResultSet rs1 =
                        db.execSql(" Vw Lst ", "Select prp  from rep_prp where mdl = ? and flg='Y' order by rnk ",
                                   ary);
                    while (rs1.next()) {

                        asViewPrp.add(rs1.getString("prp"));
                    }
                    rs1.close();
                    session.setAttribute(mdl, asViewPrp);
                }

            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        }
        return asViewPrp;
    }
    
    public ActionForward updatePlanStatus(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      
      ResultSet rs = null;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      ArrayList ary = new ArrayList();
      StringBuffer sb = new StringBuffer();
      String ref_idn = util.nvl(req.getParameter("ref_idn"));
      String plan_seq = util.nvl(req.getParameter("plan_seq"));
      String issue_id = util.nvl(req.getParameter("issue_id"));
      String planSql = "update PLAN_M set DFLT_YN='N' where issue_id=?  and ref_idn=?";
      ary = new ArrayList();
      ary.add(issue_id);
        ary.add(ref_idn);
      int ct = db.execUpd(" planSql ",planSql,ary);
       
        planSql = "update PLAN_M set DFLT_YN='Y' where issue_id=?  and ref_idn=? and plan_seq=?";  
        ary.add(plan_seq);
        ct = db.execUpd(" planSql ",planSql,ary);
        
        res.getWriter().write("<planUpdate>Success</planUpdate>");
       
        
        return null;
    }
    
    public ActionForward calculateCp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      
      ResultSet rs = null;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      ArrayList ary = new ArrayList();
      StringBuffer sb = new StringBuffer();
      String purId = util.nvl(req.getParameter("purId"));
        String pktPrp = "DP_APPLY_PUR_CP(?)";
        ary = new ArrayList();
        ary.add(purId);
      int  ct = db.execCall(" Srch Prp ", pktPrp, ary);
      if(ct > 0){ 
     res.getWriter().write("<Message>SUCCESS</Message>");
      }else{
       res.getWriter().write("<Message>FAIL</Message>");
          }
       
       
        return null;
    }



    public ArrayList InStatus(HttpServletRequest req,
                                HttpServletResponse res, String issueId) {
        //HashMap prcStt = new HashMap();
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        ArrayList prcStt = new ArrayList();
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            init(req, res, session, util);
            String getPrcToPrc =
                "select a1.in_stt,d.dsc from mprc a1, iss_rtn a, prc_to_prc b , df_stk_stt d\n" +
                "where a.prc_id = b.prc_id and a1.idn = b.prc_to_id  and a.iss_id = ? \n" +
                "and a1.in_stt=d.stt  and  b.flg1 in ('IN','A')\n" +
                "order by b.srt";

            ArrayList ary = new ArrayList();
            ary.add(issueId);

            ResultSet rs = db.execSql(" get options", getPrcToPrc, ary);
            try {
                while (rs.next()) {

                    HashMap prcSttMap = new HashMap();
                    prcSttMap.put("key", rs.getString("in_stt"));
                    prcSttMap.put("val", rs.getString("dsc"));
                    prcStt.add(prcSttMap);
                }
                rs.close();
            } catch (SQLException e) {

            }
        }
        return prcStt;
    }
    
    
    public ArrayList OutStatus(HttpServletRequest req,
                                HttpServletResponse res, String issueId) {
        //HashMap prcStt = new HashMap();
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        ArrayList prcStt = new ArrayList();
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            init(req, res, session, util);
            String getPrcToPrc =
                "select a1.in_stt,d.dsc from mprc a1, iss_rtn a, prc_to_prc b , df_stk_stt d\n" +
                "where a.prc_id = b.prc_id and a1.idn = b.prc_to_id  and a.iss_id = ? \n" +
                "and a1.in_stt=d.stt  and  b.flg1 in ('OUT','A')\n" +
                "order by b.srt";

            ArrayList ary = new ArrayList();
            ary.add(issueId);

            ResultSet rs = db.execSql(" get options", getPrcToPrc, ary);
            try {
                while (rs.next()) {

                    HashMap prcSttMap = new HashMap();
                    prcSttMap.put("key", rs.getString("in_stt"));
                    prcSttMap.put("val", rs.getString("dsc"));
                    prcStt.add(prcSttMap);
                }
                rs.close();
            } catch (SQLException e) {

            }
        }
        return prcStt;
    }

    public void RtnEdit(HttpServletRequest req, HttpServletResponse res,
                        String issStt) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());
        db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList prpList = new ArrayList();
        String labIssueEdit =
            "select b.mprp from mprc a , prc_prp_alw b " + " where a.idn = b.prc_idn and a.is_stt=? and b.flg='RTNEDIT'  order by b.srt ";
        ArrayList ary = new ArrayList();
        ary.add(issStt);
        ResultSet rs = db.execSql("labIssue", labIssueEdit, ary);
        try {
            while (rs.next()) {
                prpList.add(rs.getString("mprp"));
            }
            rs.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        gtMgr.setValue("AssortRtnEditPRP", prpList);
    }

  public ActionForward savePrp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    String msg="FAIL";
   if(info!=null){
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
     int ct=0;
     String lprp=util.nvl(req.getParameter("lprp"));
      String lprpVal=util.nvl(req.getParameter("prpVal"));
      String issId=util.nvl(req.getParameter("issId"));
      String stkIdn=util.nvl(req.getParameter("stkIdn"));
      
      if(lprp.equals("QTY")){
        ArrayList ary = new ArrayList();
        ary.add(lprpVal);
        ary.add(stkIdn);
        ct = db.execUpd("update qty", "update mstk set qty=? where idn=?", ary);
      }else{
      ArrayList ary = new ArrayList();
      ary.add(stkIdn);
      ary.add(lprp);
      ary.add(lprpVal);
     String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? )";
      ct = db.execCall("stockUpd",stockUpd, ary);
      if(lprp.equals("CRTWT") || lprp.equals("RTE")){
         ary = new ArrayList();
         ary.add(issId);
         ary.add(stkIdn);
          ary.add(lprp);
          ary.add(lprpVal);
       int  ctn = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
      }
      }
      if(ct>0)
          msg="SUCCESS";
      else
          msg="FAIL";
      
    }
      res.setContentType("text/xml");
      res.setHeader("Cache-Control", "no-cache");
      res.getWriter().write("<msg>" + msg + "</msg>");
    return null;
  }
  

    public void GtMgrReset(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
        String lstNme = (String)gtMgr.getValue("lstNmeRGHRTN");
        HashMap gtMgrMap = (HashMap)gtMgr.getValues();
        gtMgrMap.remove(lstNme);
        gtMgrMap.remove(lstNme + "_SEL");
        gtMgrMap.remove(lstNme + "_TTL");
        gtMgrMap.remove("lstNmeRGHRTN");
    }

    public String init(HttpServletRequest req, HttpServletResponse res,
                       HttpSession session, DBUtil util) {
        String rtnPg = "sucess";
        String invalide = "";
        String connExists = util.nvl(util.getConnExists());
        if (!connExists.equals("N"))
            invalide = util.nvl(util.chkTimeOut(), "N");
        if (session.isNew())
            rtnPg = "sessionTO";
        if (connExists.equals("N"))
            rtnPg = "connExists";
        if (invalide.equals("Y"))
            rtnPg = "chktimeout";
        if (rtnPg.equals("sucess")) {
            boolean sout = util.getLoginsession(req, res, session.getId());
            if (!sout) {
                rtnPg = "sessionTO";
                System.out.print("New Session Id :=" + session.getId());
            } else {
                rtnPg = util.checkUserPageRights("", util.getFullURL(req));
                if (rtnPg.equals("unauthorized"))
                    util.updAccessLog(req, res, "Split Return Action",
                                      "Unauthorized Access");
                else
                    util.updAccessLog(req, res, "Split Return Action", "init");
            }
        }
        return rtnPg;
    }
}
