package ft.com.mixre;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.JspUtil;

import ft.com.assort.AssortIssueForm;
import ft.com.assort.AssortIssueImpl;
import ft.com.assort.AssortIssueInterface;
import ft.com.dao.GtPktDtl;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MixAssortIssue extends DispatchAction {

    public ActionForward load(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            MixAssortIssueForm mixAsrt = (MixAssortIssueForm)form;
            mixAsrt.resetAll();
            String grp=req.getParameter("grp");
            if(grp==null)
                grp="BPRC";
            ArrayList mprcList = getPrc(db,grp);
            mixAsrt.setMprcList(mprcList);
            GenericInterface genericInt = new GenericImpl();
            
            ArrayList empList = getEmp(db);
            mixAsrt.setEmpList(empList);
            ArrayList searchList = genericInt.genricSrch(req,res,"MIXAST_SRCH","MIXAST_SRCH");

            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("ASSORT_ISSUE");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("ASSORT_ISSUE");
            allPageDtl.put("ASSORT_ISSUE",pageDtl);
            }
            info.setPageDetails(allPageDtl);
                                                      
            info.setGncPrpLst(searchList);

            return am.findForward("load");
        }
    }

    public ActionForward fecth(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            MixAssortIssueForm assortForm = (MixAssortIssueForm)form;
            AssortIssueInterface assortInt = new AssortIssueImpl();

            util.updAccessLog(req, res, "Assort Issue", "fecth");

            String delQ = " Delete from gt_srch_rslt ";
            int ct = db.execUpd(" Del Old Pkts ", delQ, new ArrayList());

            String mprcIdn = util.nvl((String)assortForm.getValue("mprcIdn"));
            String stoneId = util.nvl((String)assortForm.getValue("vnmLst"));
            String empId = util.nvl((String)assortForm.getValue("empIdn"));
            String issueIdn = util.nvl((String)assortForm.getValue("issueIdn"));
            String stnStt = util.nvl((String)assortForm.getValue("stt"));
            String pkttyp =  util.nvl(req.getParameter("pkttyp"));
                  if(pkttyp.equals(""))
                      pkttyp="MIX";
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXAST_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXAST_SRCH");

            info.setGncPrpLst(genricSrchLst);
            //      ArrayList genricSrchLst = info.getGncPrpLst();
            HashMap prp = info.getPrp();
            HashMap mprp = info.getMprp();
            String prcStt = "";
            String isStt = "";
            String grp = "";
            String inStt="";
            if (!mprcIdn.equals("0")) {
                ArrayList ary = new ArrayList();
                String issuStt = " select is_stt , in_stt, grp  from  mprc  where idn = ? ";
                ary.add(mprcIdn);
              ArrayList  rsLst = db.execSqlLst("issueStt", issuStt, ary);
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs =(ResultSet)rsLst.get(1);
                while (rs.next()) {
                    prcStt = util.nvl(rs.getString("in_stt"));
                    isStt = util.nvl(rs.getString("is_stt"));
                    grp = util.nvl(rs.getString("grp"));
                    inStt=prcStt;
                }
                rs.close();
                stmt.close();
            }
            if(!stnStt.equals("") && !stnStt.equals("0") )
                prcStt=stnStt;
            HashMap params = new HashMap();
            params.put("stt", prcStt);
            params.put("vnm", stoneId);
            params.put("empIdn", empId);
            params.put("mprcIdn", mprcIdn);
            params.put("issueIdn", issueIdn);
            params.put("grp", grp);
            params.put("pkttyp", pkttyp);
            HashMap paramsMap = new HashMap();
            for (int i = 0; i < genricSrchLst.size(); i++) {
                ArrayList prplist = (ArrayList)genricSrchLst.get(i);
                String lprp = (String)prplist.get(0);
                String flg = (String)prplist.get(1);
                String typ = util.nvl((String)mprp.get(lprp + "T"));
                String prpSrt = lprp;
                if (flg.equals("M")) {

                    ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                    ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                    for (int j = 0; j < lprpS.size(); j++) {
                        String lSrt = (String)lprpS.get(j);
                        String lVal = (String)lprpV.get(j);
                        String reqVal1 = util.nvl((String)assortForm.getValue(lprp + "_" + lVal), "");
                        if (!reqVal1.equals("")) {
                            paramsMap.put(lprp + "_" + lVal, reqVal1);
                        }

                    }
                } else {
                    String fldVal1 = util.nvl((String)assortForm.getValue(lprp + "_1"));
                    String fldVal2 = util.nvl((String)assortForm.getValue(lprp + "_2"));
                    if (typ.equals("T")) {
                        fldVal1 = util.nvl((String)assortForm.getValue(lprp + "_1"));
                        fldVal2 = fldVal1;
                    }
                    if (fldVal2.equals(""))
                        fldVal2 = fldVal1;
                    if (!fldVal1.equals("") && !fldVal2.equals("")) {
                        paramsMap.put(lprp + "_1", fldVal1);
                        paramsMap.put(lprp + "_2", fldVal2);
                    }
                }
            }
            HashMap stockList = null;
            String mdl="";
            if (paramsMap.size() > 0) {
            
                paramsMap.put("stt", prcStt);
                if(pkttyp.equals("RGH")){
               
                    paramsMap.put("mdl", "RGH_VIEW");
                     paramsMap.put("PRCD", "ROUGH");
                        mdl="RGH_VIEW";
                }else{
                    paramsMap.put("mdl", "MIX_VIEW");
                    paramsMap.put("MIX", "Y");
                        mdl="MIX_VIEW";
                }
                util.genericSrch(paramsMap);
                stockList = SearchResult(req, res, "",mdl);
            } else {
                stockList = FecthResult(req, res, params);
            }
            if (stockList.size() > 0) {
                HashMap totals = GetTotal(req, res);
                req.setAttribute("totalMap", totals);
                assortInt.IssueEdit(req, res, isStt);
            }
            util.updAccessLog(req, res, "Assort Issue", "stockList : " + stockList.size());
            req.setAttribute("view", "Y");
            assortForm.setValue("prcId", mprcIdn);
            assortForm.setValue("empId", empId);
            assortForm.setValue("iss_stt", isStt);
            assortForm.setValue("in_stt",inStt);
            assortForm.setValue("stnStt",stnStt);
            req.setAttribute("iss_stt", isStt);
            req.setAttribute("stnStt", stnStt);
            String lstNme = "ASIS_" + info.getLogId() + "_" + util.getToDteGiveFrmt("yyyyMMdd");
            gtMgr.setValue(lstNme, stockList);
            req.setAttribute("lstNme", lstNme);

            util.updAccessLog(req, res, "Assort Issue", "End");
            return am.findForward("load");
        }
    }

    public HashMap FecthResult(HttpServletRequest req,HttpServletResponse res, HashMap params){
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
        String vnm = (String)params.get("vnm");
        String stt = (String)params.get("stt");
        String pkt_ty = (String)params.get("pkttyp");
        String mdl="MIX_VIEW";
        if(pkt_ty.equals("RGH"))
            mdl="RGH_VIEW";
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
               "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty ,quot, cts , sk1 , rmk,rap_rte,rap_dis ) " + 
               " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' ,nvl(qty,0)-nvl(qty_iss,0) qty,b.upr,nvl(cts,0)-nvl(cts_iss,0) cts , sk1 , tfl3,b.rap_rte,decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))  "+
               "     from mstk b "+
               " where  cts > 0  and stt = ? and pkt_ty = ? ";
              srchRefQ = srchRefQ+" and ( b.vnm in ("+vnmSub+") or b.tfl3 in ("+vnmSub+") or b.cert_no in ("+vnmSub+")) " ;
              ary = new ArrayList();
              ary.add(stt);
              ary.add(pkt_ty);
              ct = db.execUpd(" ins gt_srch_rslt", srchRefQ,ary); 
          }
           
       }else{
         srchRefQ =   "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty ,quot, cts , sk1 , rmk,rap_rte,rap_dis ) " + 
           " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , nvl(qty,0)-nvl(qty_iss,0) qty,b.upr,nvl(cts,0)-nvl(cts_iss,0) cts , sk1 , tfl3,b.rap_rte,decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))  "+
           "     from mstk b "+
           " where  cts > 0  and stt = ? and pkt_ty = ? ";
          
           ary = new ArrayList();
           ary.add(stt);
           ary.add(pkt_ty);
           ct = db.execUpd(" ins gt_srch_rslt", srchRefQ,ary);
           
       }
        
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add(mdl);
       
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        stockList = SearchResult(req ,res, vnm,mdl);
      }
        return stockList;
    }
    
    public HashMap SearchResult(HttpServletRequest req ,HttpServletResponse res, String vnmLst,String mdl ){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap pktList = new HashMap();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
      
        ArrayList vwPrpLst = rghPrpview(req,res,mdl);
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , to_char(quot,'99999990.90') quot, to_char(cts,'99990.000') cts ,to_char(quot*cts,'99990.90') amt, rmk,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis ";

        

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

        
        String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by sk1 , cts ";
        
        ArrayList ary = new ArrayList();
        ary.add("Z");
        ArrayList  rsLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while(rs.next()) {
                 String stk_idn = util.nvl(rs.getString("stk_idn"));
                GtPktDtl pktPrpMap = new GtPktDtl();
                pktPrpMap.setStt(util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.setVnm(vnm);
                    String tfl3 = util.nvl(rs.getString("rmk"));
                    if(vnmLst.indexOf(tfl3)!=-1 && !tfl3.equals("")){
                        if(vnmLst.indexOf("'")==-1)
                            vnmLst =  vnmLst.replaceAll(tfl3,"");
                        else
                            vnmLst =  vnmLst.replaceAll("'"+tfl3+"'", "");
                    } else if(vnmLst.indexOf(vnm)!=-1 && !vnm.equals("")){
                        if(vnmLst.indexOf("'")==-1)
                            vnmLst =  vnmLst.replaceAll(vnm,"");
                        else
                            vnmLst =  vnmLst.replaceAll("'"+vnm+"'", "");
                    }
                pktPrpMap.setPktIdn(rs.getLong("stk_idn"));
                pktPrpMap.setValue("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.setValue("cts",util.nvl(rs.getString("cts")));
                    pktPrpMap.setValue("amt",util.nvl(rs.getString("amt")));
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      if(prp.equals("RTE"))
                      fld="quot";
                      String val = util.nvl(rs.getString(fld)) ;
                      if (prp.toUpperCase().equals("RAP_DIS"))
                      val = util.nvl(rs.getString("r_dis")) ;  
                        pktPrpMap.setValue(prp, val);
                         }
                              
                    pktList.put(stk_idn,pktPrpMap);
                }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        if(!vnmLst.equals("")){
            vnmLst = util.pktNotFound(vnmLst);
            req.setAttribute("vnmNotFnd", vnmLst);
        }
      }
        return pktList;
    }
    
    
    public ActionForward Issue(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            ArrayList stkIdnList = new ArrayList();
            MixAssortIssueForm mixForm = (MixAssortIssueForm)form;
            Enumeration reqNme = req.getParameterNames();
            String stkIdnstr = "";
            String empId = (String)mixForm.getValue("empId");
            String prcId = (String)mixForm.getValue("prcId");
            String stnStt = util.nvl((String)mixForm.getValue("stnStt"));
            String pkttyp = util.nvl(req.getParameter("pkttyp"),"MIX");
            while (reqNme.hasMoreElements()) {
                String paramNm = (String)reqNme.nextElement();
                if (paramNm.indexOf("cb_stk") > -1) {
                    String stkIdn = req.getParameter(paramNm);
                    stkIdnstr = stkIdnstr + "," + stkIdn;
                    stkIdnList.add(stkIdn);
                }
            }
            String issStt = util.nvl((String)mixForm.getValue("iss_stt")).trim();
            String in_stt = util.nvl((String)mixForm.getValue("in_stt")).trim();
            if(!stnStt.equals("") && !stnStt.equals("0"))
                in_stt=stnStt;
            ArrayList issEditPrp = (ArrayList)gtMgr.getValue("AssortIssueEditPRP");
            int issueIdn=0;
          boolean isSuccess=true;
            try {
                db.setAutoCommit(false); 
                if (stkIdnList != null && stkIdnList.size() > 0) {

                    ArrayList params = new ArrayList();
                    params.add(prcId);
                    params.add(empId);
                    ArrayList out = new ArrayList();
                    out.add("I");
                    CallableStatement cst =
                        db.execCall("findIssueId", "ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)",
                                    params, out);
                   issueIdn = cst.getInt(3);
                  cst.close();
                  cst=null;
                    for (int i = 0; i < stkIdnList.size(); i++) {
                        String stkIdn = (String)stkIdnList.get(i);
                        String qtyVal = util.nvl(req.getParameter("OLDQTY_" + stkIdn),"0").trim();
                        String ctsVal = util.nvl(req.getParameter("OLDCTS_" + stkIdn)).trim();
                        String newCts = util.nvl((String)mixForm.getValue("CTS_" + stkIdn)).trim();
                        String rteVal = util.nvl(req.getParameter("OLDRTE_" + stkIdn),"0").trim();
                        String newrteVal =  util.nvl((String)mixForm.getValue("RTE_" + stkIdn),"0").trim();
                        if (!ctsVal.equals("") && !newCts.equals("")) {
                            String newQty = util.nvl((String)mixForm.getValue("QTY_" + stkIdn),"0").trim();
                            double oldDcts = Double.parseDouble(ctsVal);
                            double newDCts = Double.parseDouble(newCts);
                            double oldDrte = Double.parseDouble(rteVal);
                            double newDrte = Double.parseDouble(newrteVal);
                            int qtyDval = Integer.parseInt(qtyVal);
                            int newDqty = Integer.parseInt(newQty);
                            if (newDCts < oldDcts) {
                           
                            
                                String lotNo = util.nvl(req.getParameter("LOTNO_" + stkIdn));
                                String lotIdn = "0";
                                if (!lotNo.equals("")) {
                                    String lotDsc = "select idn from mlot where dsc = ?";
                                    params = new ArrayList();
                                    params.add(lotNo);
                                    ArrayList rsLst = db.execSqlLst("lotDsc", lotDsc, params);
                                    PreparedStatement pst = (PreparedStatement)rsLst.get(0);
                                    ResultSet rs = (ResultSet)rsLst.get(1);
                                    while (rs.next()) {
                                        lotIdn = rs.getString("idn");
                                    }
                                    rs.close();
                                    pst.close();

                                }
                                params = new ArrayList();
                                params.add(lotIdn);
                                params.add(lotNo);
                                params.add(newQty);
                                params.add(pkttyp);
                                params.add(in_stt);
                                out = new ArrayList();
                                out.add("I");
                                out.add("V");
                                String genPkt =
                                    "DP_GEN_PKT(pLotIdn => ? ,pLotDsc  => ?,pQty=>?, pPktTy  => ? " + "  ,  pStt  => ? \n" +
                                    "  , lStkIdn => ? , lVnm  => ? )";
                                cst = db.execCall("genPkt", genPkt, params, out);
                                long lStkIdn = cst.getLong(params.size() + 1);
                                String vnm = cst.getString(params.size() + 2);
                                if (lStkIdn > 0) {
                                    int lotNum=0;
                                    String ptVnm  =util.nvl(req.getParameter("VNM_"+stkIdn));
                                    String lastCnt =
                                        "select rgh_pkg.get_lot_vnm('" + ptVnm + "', '.', 'STK') lotNum from dual";
                                  ArrayList rsLst = db.execSqlLst("lastCnt", lastCnt, new ArrayList());
                                  PreparedStatement pst = (PreparedStatement)rsLst.get(0);
                                  ResultSet rsv = (ResultSet)rsLst.get(1);
                                    while (rsv.next()) {
                                    lotNum = rsv.getInt("lotNum");
                                       
                                    }
                                    lotNum=lotNum+1;
                                    rsv.close();
                                    pst.close();
                                     String newVnm = ptVnm+"."+lotNum;
                                    String insertStkDtl =
                                        "insert into stk_dtl(mstk_idn,grp,mprp,val,num,dte,psrt,prt1,srt,lab,txt) " +
                                        "select ?,grp,mprp,val,num,dte,psrt,prt1,srt,lab,txt  from stk_dtl where mstk_idn=? and mprp not in ('CRTWT','QTY') and grp=?";
                                    params = new ArrayList();
                                    params.add(String.valueOf(lStkIdn));
                                    params.add(stkIdn);
                                    params.add("1");
                                    int ct = db.execUpd("insert stkDtl", insertStkDtl, params);

                                    String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                                    ArrayList ary = new ArrayList();
                                    ary.add(String.valueOf(lStkIdn));
                                    ary.add("CRTWT");
                                    ary.add(String.valueOf(newDCts));
                                    ct = db.execCall("stockUpd", stockUpd, ary);
                                    if(oldDrte!=newDrte){
                                        double oldPktRte = ((oldDrte*oldDcts)+(newDrte*newDCts))/(oldDcts-newDCts);
                                        oldPktRte = util.roundToDecimals(oldPktRte, 2);
                                        stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                                         ary = new ArrayList();
                                         ary.add(stkIdn);
                                         ary.add("RTE");
                                         ary.add(String.valueOf(oldPktRte));
                                         ct = db.execCall("stockUpd", stockUpd, ary);
                                    }

                                    params = new ArrayList();
                                    params.add(String.valueOf(lStkIdn));
                                    ct = db.execCall("apply_rtn_prp", "STK_SRT(?)", params);

                                    params = new ArrayList();
                                    params.add(stkIdn);
                                    params.add(newVnm);
                                    params.add(String.valueOf(lStkIdn));
                                    
                                    ct = db.execUpd("mstk", "update mstk set pkt_rt=? , vnm = ?  where idn=?", params);
                                    
                                  
                                    
                                    params = new ArrayList();
                                    params.add(newQty);
                                    params.add(String.valueOf(newDCts));
                                    params.add(stkIdn);
                                    ct = db.execUpd("mstk", "update mstk set qty=nvl(qty,0)-nvl(?,0) , cts=nvl(cts,0)-nvl(?,0) where idn=?", params);
                                    String cts="";
                                    String mstkCts = "select cts from mstk where idn=?";
                                    params = new ArrayList();
                                    params.add(stkIdn);
                                   rsLst = db.execSqlLst("mstkCts", mstkCts, params);
                                   pst = (PreparedStatement)rsLst.get(0);
                                   rsv = (ResultSet)rsLst.get(1);
                                    while(rsv.next()){
                                        cts = rsv.getString("cts");
                                    }
                                    rsv.close();
                                    pst.close();
                                     stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                                     ary = new ArrayList();
                                    ary.add(stkIdn);
                                    ary.add("CRTWT");
                                    ary.add(cts);
                                    ct = db.execCall("stockUpd", stockUpd, ary);
                                    
                                    for (int j = 0; j < issEditPrp.size(); j++) {
                                        String lprp = (String)issEditPrp.get(j);
                                        String lprpVal = util.nvl((String)mixForm.getValue(lprp + "_" + stkIdn));
                                       mixForm.setValue(lprp+"_"+lStkIdn, lprpVal);
                                    }
                                    stkIdn = String.valueOf(lStkIdn);
                                }else{
                                    isSuccess=false; 
                                }


                            }else{
                                if(!in_stt.equals(stnStt)){
                                    params = new ArrayList();
                                    params.add(in_stt);
                                    params.add(stkIdn);
                                    db.execUpd("update mstk", "update mstk set stt=? where idn=?", params);
                                }
                            }
                            if(isSuccess){
                            params = new ArrayList();
                            params.add(String.valueOf(issueIdn));
                            params.add(stkIdn);
                            String issuePkt = "MIX_IR_PKG.ISS_PKT(pIssId => ?, pStkIdn => ?)";
                            int ct = db.execCall("issuePkt", issuePkt, params);

                            for (int j = 0; j < issEditPrp.size(); j++) {
                                String lprp = (String)issEditPrp.get(j);
                                String lprpVal = util.nvl((String)mixForm.getValue(lprp + "_" + stkIdn));
                                if (!lprpVal.equals("0") && !lprpVal.equals("") && !lprp.equals("CRTWT")) {
                                    params = new ArrayList();
                                    params.add(String.valueOf(issueIdn));
                                    params.add(stkIdn);
                                    params.add(lprp);
                                    params.add(lprpVal);
                                    ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", params);

                                    params = new ArrayList();
                                    params.add(stkIdn);
                                    params.add(lprp);
                                    params.add(lprpVal);


                                    String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                                    ct = db.execCall("stockUpd", stockUpd, params);

                                }
                            }
                            
                            params = new ArrayList();
                            params.add(String.valueOf(issueIdn));
                            params.add(stkIdn);
                            params.add("CRTWT");
                            params.add(newCts);
                            ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", params);

                            params = new ArrayList();
                            params.add(stkIdn);
                            params.add("CRTWT");
                            params.add(newCts);
                            String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                            ct = db.execCall("stockUpd", stockUpd, params);
                            
                            
                        }}
                    }
                }
               
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
                db.doRollBack();
                isSuccess=false;
            } catch (NumberFormatException nfe) {
                // TODO: Add catch code
                nfe.printStackTrace();
                db.doRollBack();
                isSuccess=false;
            } finally {
                db.setAutoCommit(true);   
            }
         
         if(isSuccess){
                    db.doCommit();
                    req.setAttribute( "msg","Requested packets get Issue with Issue Id "+issueIdn);
                    req.setAttribute( "issueidn",String.valueOf(issueIdn));
          }else{
                db.doRollBack();
                req.setAttribute( "msg","Error in Issue process");
         }
                  

            mixForm.reset();
            int accessidn = util.updAccessLog(req, res, "Assort Issue", "End");
            req.setAttribute("accessidn", String.valueOf(accessidn));
            req.setAttribute("grp", "grp");
            return am.findForward("load");
        }
    }


    public ArrayList getPrc(DBMgr db,String grp) {

        ArrayList ary = new ArrayList();
        ArrayList prcList = new ArrayList();


        String prcSql = "select idn, prc , in_stt,is_stt,grp from mprc where  is_stt <> 'NA' and stt = ? and grp=? ";

        prcSql += " order by srt";
        ary = new ArrayList();
        ary.add("A");
        ary.add(grp);
      ArrayList  rsLst = db.execSqlLst("prcSql", prcSql, ary);
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while (rs.next()) {
                Mprc mprc = new Mprc();
                String mprcId = rs.getString("idn");
                mprc.setMprcId(rs.getString("idn"));
                mprc.setPrc(rs.getString("prc"));
                mprc.setIn_stt(rs.getString("in_stt"));
                mprc.setIs_stt(rs.getString("is_stt"));
                mprc.setGrp(rs.getString("grp"));
                prcList.add(mprc);

            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }


        return prcList;
    }

    public ArrayList getEmp(DBMgr db) {
        ArrayList ary = new ArrayList();
        ArrayList empList = new ArrayList();
        String empSql = "select nme_idn, nme from nme_v where typ = 'EMPLOYEE' order by nme";
      ArrayList  rsLst = db.execSqlLst("empSql", empSql, ary);
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while (rs.next()) {
                MNme emp = new MNme();
                emp.setEmp_idn(rs.getString("nme_idn"));
                emp.setEmp_nme(rs.getString("nme"));
                empList.add(emp);
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }

        return empList;
    }

    public ArrayList rghPrpview(HttpServletRequest req , HttpServletResponse res,String mdl){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute(mdl);
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
      
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
                ArrayList ary = new ArrayList();
                ary.add(mdl);
                ary.add("Y");
            
              ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = ? and flg=? order by rnk ",
                               ary);
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs1 =(ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                stmt.close();
                session.setAttribute(mdl, asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return asViewPrp;
    }
    
    public HashMap GetTotal(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap gtTotalMap = new HashMap();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
       
        String gtTotal ="Select sum(qty) qty, sum(cts) cts from gt_srch_rslt where flg = ?";
        ArrayList ary = new ArrayList();
        ary.add("Z");
        ArrayList  rsLst = db.execSqlLst("getTotal", gtTotal, ary);
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            if (rs.next()) {
             gtTotalMap.put("qty", util.nvl(rs.getString("qty")));
             gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return gtTotalMap ;
    }
    public MixAssortIssue() {
        super();
    }

    public String init(HttpServletRequest req, HttpServletResponse res, HttpSession session, DBUtil util) {
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
                util.updAccessLog(req, res, "Mix Issue", "init");
            }
        }
        return rtnPg;
    }
}
