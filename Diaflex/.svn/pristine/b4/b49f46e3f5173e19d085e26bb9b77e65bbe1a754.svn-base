package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.assort.AssortIssueImpl;
import ft.com.assort.AssortIssueInterface;
import ft.com.dao.UIForms;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.sql.CallableStatement;
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

public class TransferPktAction extends DispatchAction {
      
    public TransferPktAction() {
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
        TransferPktForm form = (TransferPktForm)af;
        form.resetAll();
      ArrayList    ary          = null;
      ArrayList  sttLst = new ArrayList();
      GenericInterface genericInt = new GenericImpl();
        String typ=util.nvl(req.getParameter("typ"),"MIX");
        if(typ.equals("MIX")){
          info.setValue("TYP", "MIX");
          info.setValue("SRCHFMDL", "TRAN_CRT_FRM");
          info.setValue("SRCHTMDL", "TRAN_CRT_TO");
          info.setValue("VWFMDL", "TRN_FRM_VW");
          info.setValue("VWTMDL", "TRN_TO_VW");
          info.setValue("STTFM", "TRANS_MIXPKT");
          info.setValue("STTTO", "TRANS_MIXPKT");


        }else{
          info.setValue("TYP", "SIN");
          info.setValue("SRCHFMDL", "TRAN_CRT_SFRM");
          info.setValue("SRCHTMDL", "TRAN_CRT_STO");
          info.setValue("VWFMDL", "TRN_SFRM_VW");
          info.setValue("VWTMDL", "TRN_STO_VW");
          info.setValue("STTFM", "TRANS_SINPKT");
          info.setValue("STTTO", "TRANS_MIXPKT");

        }
      String sttMdl = (String)info.getValue("STTFM");
      
      String    sttOptn = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                               + "b.mdl = ? and b.nme_rule ='TRANS_PKT_STT' and a.til_dte is null order by a.srt_fr ";
      ary = new ArrayList();
      ary.add(sttMdl);
      ArrayList  rsLst = db.execSqlLst("memoPrint", sttOptn, ary);
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
      while (rs.next()) {
          UIForms memoOpn = new UIForms();

          memoOpn.setFORM_NME(rs.getString("chr_fr"));
          memoOpn.setFORM_TTL(rs.getString("dsc"));
          sttLst.add(memoOpn);
      }
      rs.close();
        stmt.close();
        form.setSttList(sttLst);
      sttLst = new ArrayList();
       String sttToMdl = (String)info.getValue("STTTO");

        sttOptn = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                               + "b.mdl = ? and b.nme_rule ='TRANS_PKT_STT' and a.til_dte is null order by a.srt_fr ";
      ary = new ArrayList();
      ary.add(sttToMdl);
        rsLst = db.execSqlLst("memoPrint", sttOptn, ary);
      stmt =(PreparedStatement)rsLst.get(0);
        rs =(ResultSet)rsLst.get(1);
      while (rs.next()) {
          UIForms memoOpn = new UIForms();

          memoOpn.setFORM_NME(rs.getString("chr_fr"));
          memoOpn.setFORM_TTL(rs.getString("dsc"));
          sttLst.add(memoOpn);
      }
      rs.close();
        stmt.close();
      form.setSttToList(sttLst);
        
        String srchFMdl = (String)info.getValue("SRCHFMDL");
        session.setAttribute("TRANCRTFRMLst", new ArrayList());
      ArrayList FrmSrchList = genericInt.genricSrch(req,res,"TRANCRTFRMLst",srchFMdl);
      session.setAttribute("FrmSrchList", FrmSrchList);
      
      String srchTMdl = (String)info.getValue("SRCHTMDL");
      session.setAttribute("TRANCRTTOLst", new ArrayList());

      ArrayList ToSrchList = genericInt.genricSrch(req,res,"TRANCRTTOLst",srchTMdl);
      info.setGncPrpLst(ToSrchList);
      
      return am.findForward("load");
    }
    
  }
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
                 throws Exception {
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
         int ct=0;
         int toSrchId=0;
         TransferPktForm form = (TransferPktForm)af;
         String trnsTyp= util.nvl(req.getParameter("transmode"));
         String vwFMdl = (String)info.getValue("VWFMDL");
         String vwTMdl = (String)info.getValue("VWTMDL");
         String typ = (String)info.getValue("TYP");

         if(trnsTyp.equals("PKT")){
           String sttFm = (String)form.getValue("sttPFm");

           String vnmFrm = util.nvl((String)form.getValue("vnmFrm"));
           String vnmTo = util.nvl((String)form.getValue("vnmTo"));
           db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());

           vnmFrm = util.getVnm(vnmFrm);
          String instGt= "  Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, quot,sk1,flg , qty , cts ) " + 
           " select  a.pkt_ty, a.idn, a.vnm, nvl(a.cmp,a.upr) ,a.sk1,'Z' , a.qty , a.cts  "+
           "   from mstk a where a.vnm in ("+vnmFrm+") and a.stt= ?";
          
          if(typ.equals("MIX"))
              instGt = instGt+" and a.pkt_ty <> 'NR'";
          else
             instGt = instGt+" and a.pkt_ty ='NR'";
  
              
          ArrayList ary = new ArrayList();
          ary.add(sttFm);
           ct = db.execUpd("instGt", instGt, ary);
          
          ary = new ArrayList();
          ary.add(vwFMdl);
          ct = db.execCall("srch", "PKGMKT.PKTPRP(pMdl =>?)", ary);
            

          
           
         }else{
             String sttFm = (String)form.getValue("sttCFm");
             String sttTo = (String)form.getValue("sttCTo");

           ArrayList FrmSrchList = (ArrayList)session.getAttribute("FrmSrchList");
            HashMap mprp = info.getMprp();
             HashMap prp = info.getPrp();
           HashMap paramsMap = new HashMap();
           for(int i=0;i<FrmSrchList.size();i++){
               ArrayList prplist =(ArrayList)FrmSrchList.get(i);
               String lprp = (String)prplist.get(0);
               String flg= (String)prplist.get(1);
               String ltyp = util.nvl((String)mprp.get(lprp+"T"));
               String prpSrt = lprp ;  
               if(flg.equals("M")) {
               
                   ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                   ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                   for(int j=0; j < lprpS.size(); j++) {
                   String lSrt = (String)lprpS.get(j);
                   String lVal = (String)lprpV.get(j);    
                   String reqVal1 = util.nvl((String)form.getValue(lprp + "_" + lVal),"");
                   if(!reqVal1.equals("")){
                   paramsMap.put(lprp + "_" + lVal, reqVal1);
                   }
                      
                   }
               }else{
               String fldVal1 = util.nvl((String)form.getValue(lprp));
               String fldVal2 = util.nvl((String)form.getValue(lprp));
               if(ltyp.equals("T")){
                   fldVal1 = util.nvl((String)form.getValue(lprp));
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
             paramsMap.put("stt", sttFm);
             paramsMap.put("rlnId", "0");
             int frmSrchId=0;
             if(paramsMap.size()>2){
             frmSrchId = util.genericSrchEntries(paramsMap);
             }
           ArrayList genricSrchLst = (ArrayList)session.getAttribute("TRANCRTTOLst");
           info.setGncPrpLst(genricSrchLst);
           
                   paramsMap = new HashMap();
                      for(int i=0;i<genricSrchLst.size();i++){
                          ArrayList prplist =(ArrayList)genricSrchLst.get(i);
                          String lprp = (String)prplist.get(0);
                          String flg= (String)prplist.get(1);
                          String ltyp = util.nvl((String)mprp.get(lprp+"T"));
                          String prpSrt = lprp ;  
                          if(flg.equals("M")) {
                          
                              ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                              ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                              for(int j=0; j < lprpS.size(); j++) {
                              String lSrt = (String)lprpS.get(j);
                              String lVal = (String)lprpV.get(j);    
                              String reqVal1 = util.nvl((String)form.getValue(lprp + "_" + lVal),"");
                              if(!reqVal1.equals("")){
                              paramsMap.put(lprp + "_" + lVal, reqVal1);
                              }
                                 
                              }
                          }else{
                          String fldVal1 = util.nvl((String)form.getValue(lprp+"_1"));
                          String fldVal2 = util.nvl((String)form.getValue(lprp+"_2"));
                          if(ltyp.equals("T")){
                              fldVal1 = util.nvl((String)form.getValue(lprp+"_1"));
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
           paramsMap.put("stt", sttTo);
           paramsMap.put("rlnId", "0");
             if(paramsMap.size()>2){
           toSrchId = util.genericSrchEntries(paramsMap);
           session.setAttribute("ParamsMap", paramsMap);
             }
             form.setValue("ToSrchId",String.valueOf(toSrchId));

             if(frmSrchId>0){
               db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
               String srch_pkg = "srch_pkg.STK_SRCH(pSrchId => ?, pMdl=> ?)";
               if(typ.equals("MIX"))
                 srch_pkg="DP_MIX_SRCH(pSrchId => ?, pMdl=> ?)";
               ArrayList ary = new ArrayList();
               ary.add(String.valueOf(frmSrchId));
               ary.add(vwFMdl);
               ct = db.execCall("srch", srch_pkg ,ary);
             }
             form.setValue("vnmFm","");
             form.setValue("vnmTo", "");
           }
         
       if(ct>0){
         ArrayList pktList = SearchResult(req, res, vwFMdl);
         if(pktList!=null && pktList.size()>1){
         req.setAttribute("FrmPktList", pktList);
         req.setAttribute("viewFm", "yes");
         }else{
             if(pktList!=null && pktList.size()>0){
           HashMap PktDtl = (HashMap)session.getAttribute("PktHashMap");
           String frmId = (String)pktList.get(0);
            String stt=(String)form.getValue("sttCTo");
             HashMap FrmPktDtl = (HashMap)PktDtl.get(frmId);
           session.setAttribute("FrmPktDtl", FrmPktDtl);
           if(trnsTyp.equals("PKT")){
                String sttTo = (String)form.getValue("sttPTo");
                 stt = sttTo;
                db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());

             String vnmTo = util.nvl((String)form.getValue("vnmTo"));
             vnmTo = util.getVnm(vnmTo);
             String instGt= "  Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, quot,sk1,flg , qty , cts ) " +
             " select  a.pkt_ty, a.idn, a.vnm, nvl(a.cmp,a.upr) ,a.sk1,'Z' , a.qty , a.cts  "+
             "   from mstk a where a.vnm in ("+vnmTo+") and a.stt= ? and a.pkt_ty <> 'NR'";
                
             ArrayList ary = new ArrayList();
             ary.add(sttTo);
             ct = db.execUpd("instGt", instGt, ary);
             
             ary = new ArrayList();
             ary.add(vwTMdl);
             ct = db.execCall("srch", "PKGMKT.PKTPRP(pMdl =>?)", ary);
              }else{
               if(toSrchId>0){
             db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
             
                String   srch_pkg="DP_MIX_SRCH(pSrchId => ?, pMdl=> ?)";
                 ArrayList ary = new ArrayList();
                 ary.add(String.valueOf(toSrchId));
                 ary.add(vwTMdl);
                 ct = db.execCall("srch", srch_pkg ,ary);
                 

               }
               req.setAttribute("stt", stt);
             req.setAttribute("frmID", frmId);

           }
           if(ct>0){
             ArrayList TopktList = SearchResult(req, res, vwTMdl);
             session.setAttribute("ToPktList", TopktList);
           }
           req.setAttribute("viewTo", "yes");

         }
       }}

        return am.findForward("load");
     }
   }
    
    public ActionForward Trans(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
                 throws Exception {
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
         TransferPktForm form = (TransferPktForm)af;
        String frmID = util.nvl(req.getParameter("frmID"));
       String vwTMdl = (String)info.getValue("VWTMDL");
       String typ = (String)info.getValue("TYP");

        if(!frmID.equals("")){
       HashMap pktMap = (HashMap)session.getAttribute("PktHashMap");
       HashMap FrmPktDtl = (HashMap)pktMap.get(frmID);
       session.setAttribute("FrmPktDtl", FrmPktDtl);
         }
         int ct=0;
       String ToSrchID = util.nvl((String)form.getValue("ToSrchId"),"0");
        String vnmTo = util.nvl((String)form.getValue("vnmTo"));
       String stt = (String)form.getValue("sttCTo");

       if(ToSrchID!=null && !ToSrchID.equals("0")){
         db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());

         String  srch_pkg="DP_MIX_SRCH(pSrchId => ?, pMdl=> ?)";
         ArrayList ary = new ArrayList();
         ary.add(String.valueOf(ToSrchID));
         ary.add(vwTMdl);
         ct = db.execCall("srch", srch_pkg ,ary);
          
       }else if(!vnmTo.equals("")){
           vnmTo = util.getVnm(vnmTo);
         String sttTo = (String)form.getValue("sttPTo");
             stt =sttTo;
         db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());

       String instGt= "  Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, quot,sk1,flg , qty , cts ) " +
       " select  a.pkt_ty, a.idn, a.vnm, nvl(a.cmp,a.upr) ,a.sk1,'Z' , a.qty , a.cts  "+
       "   from mstk a where a.vnm in ("+vnmTo+") and a.stt= ?";
         if(typ.equals("MIX"))
             instGt = instGt+" and a.pkt_ty <> 'NR'";
         else
            instGt = instGt+" and a.pkt_ty ='NR'";
         
       ArrayList ary = new ArrayList();
       ary.add(sttTo);
       ct = db.execUpd("instGt", instGt, ary);
       
       ary = new ArrayList();
       ary.add(vwTMdl);
       ct = db.execCall("srch", "PKGMKT.PKTPRP(pMdl =>?)", ary);
       
       }
       if(ct>0){
         req.setAttribute("stt", stt);
         req.setAttribute("frmID", frmID);
         ArrayList pktList = SearchResult(req, res, vwTMdl);
         session.setAttribute("ToPktList", pktList);
       }
       req.setAttribute("viewTo", "yes");
       
       return am.findForward("load");
       
     }
    
    }

    public ActionForward closePop(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
                 throws Exception {
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
         TransferPktForm form = (TransferPktForm)af;
       String vwTMdl = (String)info.getValue("VWTMDL");
       String typ = (String)info.getValue("TYP");

       int ct=0;
       String ToSrchID = util.nvl((String)form.getValue("ToSrchId"),"0");
       String vnmTo = util.nvl((String)form.getValue("vnmTo"));
       String stt = (String)form.getValue("sttCTo");

       if(!vnmTo.equals("")){
         vnmTo = util.getVnm(vnmTo);
       String sttTo = (String)form.getValue("sttPTo");
           stt =sttTo;
       db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());

       String instGt= "  Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, quot,sk1,flg , qty , cts ) " +
       " select  a.pkt_ty, a.idn, a.vnm, nvl(a.cmp,a.upr) ,a.sk1,'Z' , a.qty , a.cts  "+
       "   from mstk a where a.vnm in ("+vnmTo+") and a.stt= ?  and a.pkt_ty <> 'NR'";
       
       ArrayList ary = new ArrayList();
       ary.add(sttTo);
       ct = db.execUpd("instGt", instGt, ary);
       
       ary = new ArrayList();
       ary.add(vwTMdl);
       ct = db.execCall("srch", "PKGMKT.PKTPRP(pMdl =>?)", ary);
       
       }else{
         db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());

         String  srch_pkg="DP_MIX_SRCH(pSrchId => ?, pMdl=> ?)";
         ArrayList ary = new ArrayList();
         ary.add(String.valueOf(ToSrchID));
         ary.add(vwTMdl);
         ct = db.execCall("srch", srch_pkg ,ary);
         
       }
       if(ct>0){
       req.setAttribute("stt", stt);
       ArrayList pktList = SearchResult(req, res, vwTMdl);
       session.setAttribute("ToPktList", pktList);
       }
       req.setAttribute("viewTo", "yes");
       
       return am.findForward("load");
       
     }
     }
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
                 throws Exception {
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
         TransferPktForm form = (TransferPktForm)af;
       String vwTMdl = (String)info.getValue("VWTMDL");
       String rootPkt = util.nvl(req.getParameter("frmPkt"));


      try {
              db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());

                ArrayList stkIdnLst =
                    (ArrayList)session.getAttribute("ToPktList");
                for (int i = 0; i < stkIdnLst.size(); i++) {
                    String stk_idn = (String)stkIdnLst.get(i);
                    String isChecked = util.nvl((String)form.getValue(stk_idn));
                    if (!isChecked.equals("")) {
                        String fnlQty = util.nvl(req.getParameter("FNL_QTY_" + stk_idn));
                        String fnlCts = util.nvl(req.getParameter("FNL_CTS_" + stk_idn));
                        String fnlRte = util.nvl(req.getParameter("FNL_RTE_" + stk_idn));
                        String cuQty = util.nvl(req.getParameter("CU_QTY_" + stk_idn));
                        String cuCts = util.nvl(req.getParameter("CU_CTS_" + stk_idn));
                        String cuRte = util.nvl(req.getParameter("CU_RTE_" + stk_idn));
                        String trfQty = util.nvl(req.getParameter("TRF_QTY_" + stk_idn));
                        String trfCts = util.nvl(req.getParameter("TRF_CTS_" + stk_idn));
                        String trfRte = util.nvl(req.getParameter("TRF_RTE_" + stk_idn));
                      
                      String appSale = util.nvl(req.getParameter("APP_" + stk_idn));

                        String updateMstk =
                            "update mstk set qty=? where idn=?";
                        ArrayList ary = new ArrayList();
                        ary.add(fnlQty);
                        ary.add(stk_idn);
                        int ct = db.execUpd("upMstk", updateMstk, ary);

                        ary = new ArrayList();
                        ary.add(stk_idn);
                        ary.add("CRTWT");
                        ary.add(fnlCts);
                        String stockUpd =
                            "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                        ct = db.execCall("stockUpd", stockUpd, ary);

                        ary = new ArrayList();
                        ary.add(stk_idn);
                        ary.add("CP");
                        ary.add(fnlRte);
                        stockUpd =
                                "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                        ct = db.execCall("stockUpd", stockUpd, ary);
                        if(appSale.equals("yes")){
                          ary = new ArrayList();
                          ary.add(stk_idn);
                          ary.add("SAL_RTE");
                          ary.add(fnlRte);
                          stockUpd =
                                  "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                          ct = db.execCall("stockUpd", stockUpd, ary);
                          
                        }
                        if(ct>0){
                          ary = new ArrayList();
                          ary.add(stk_idn);
                          
                          String instGt= "  Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, quot,sk1,flg , qty , cts ) " +
                          " select  a.pkt_ty, a.idn, a.vnm, nvl(a.cmp,a.upr) ,a.sk1,'Z' , a.qty , a.cts  "+
                          "   from mstk a where a.idn = ? ";
                          
                          db.execUpd("gt insert", instGt, ary);
                          
                          String mixLog="insert into mix_trans_log(idn,dte,mstk_idn,trans_typ,trans_idn,qty,cts,rte,trans_qty,trans_cts,trans_rte,fnl_qty,fnl_cts,fnl_rte)" +
                              " select seq_mix_trans_log.nextval,sysdate, ? , 'TRF', ? , ? , ? , ? , ? ,? ,? , ? ,? ,? from dual";
                          ary = new ArrayList();
                          ary.add(rootPkt);
                          ary.add(stk_idn);
                          ary.add(cuQty);
                          ary.add(cuCts);
                          ary.add(cuRte);
                          ary.add(trfQty);
                          ary.add(trfCts);
                          ary.add(trfRte);
                          ary.add(fnlQty);
                          ary.add(fnlCts);
                          ary.add(fnlRte);
                          db.execUpd("mix_log", mixLog, ary);
                          
                        }
                    }
                }
                if (!rootPkt.equals("")) {
                    String fnlsQty = util.nvl((String)form.getValue("FNL_QTY"),"0");
                    String fnlsCts = util.nvl((String)form.getValue("FNL_CTS"),"0");
                    String fnlsRte = util.nvl((String)form.getValue("FNL_RTE"),"0");
                  String adjQty = util.nvl((String)form.getValue("TRF_QTY"),"0");
                  String adjCts = util.nvl((String)form.getValue("TRF_CTS"),"0");
                  String adjRte = util.nvl((String)form.getValue("TRF_RTE"),"0");
                  String cuQty = util.nvl(req.getParameter("CU_QTY"),"0");
                  String cuCts = util.nvl(req.getParameter("CU_CTS"),"0");
                  String cuRte = util.nvl(req.getParameter("CU_RTE"),"0");
                  
                  int fnlQty = Integer.parseInt(fnlsQty)+Integer.parseInt(adjQty);
                  float fnlCts = Float.parseFloat(fnlsCts)+ Float.parseFloat(adjCts);
                  float fnlRte =  Float.parseFloat(fnlsRte)+ Float.parseFloat(adjRte);
                  
                  int trfQty = Integer.parseInt(cuQty)-Integer.parseInt(fnlsQty);
                  float trfCts = Float.parseFloat(cuCts)- Float.parseFloat(fnlsCts);
                  float trfRte =  Float.parseFloat(cuRte)- Float.parseFloat(fnlsRte);


                    String updateMstk = "update mstk set qty=? where idn=?";
                    ArrayList ary = new ArrayList();
                    ary.add(String.valueOf(fnlQty));
                    ary.add(rootPkt);
                    int ct = db.execUpd("upMstk", updateMstk, ary);

                    ary = new ArrayList();
                    ary.add(rootPkt);
                    ary.add("CRTWT");
                    ary.add(String.valueOf(fnlCts));
                    String stockUpd =
                        "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                    ct = db.execCall("stockUpd", stockUpd, ary);

                    ary = new ArrayList();
                    ary.add(rootPkt);
                    ary.add("CP");
                    ary.add(String.valueOf(fnlRte));
                    stockUpd =
                            "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                    ct = db.execCall("stockUpd", stockUpd, ary);
                  if(ct>0){
                    ary = new ArrayList();
                    ary.add(rootPkt);
                    
                    String instGt= "  Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, quot,sk1,flg , qty , cts ) " +
                    " select  a.pkt_ty, a.idn, a.vnm, nvl(a.cmp,a.upr) ,a.sk1,'Z' , a.qty , a.cts  "+
                    "   from mstk a where a.idn = ? ";
                    
                    db.execUpd("gt insert", instGt, ary);
                    
                       String mixLog="insert into mix_trans_log(idn,dte,mstk_idn,trans_typ,qty,cts,rte,trans_qty,trans_cts,trans_rte,fnl_qty,fnl_cts,fnl_rte,adj_qty,adj_cts,adj_rte)" +
                           " select seq_mix_trans_log.nextval,sysdate, ? , 'ROTTRF', ? , ? , ? , ? , ? ,? ,? , ? ,? ,?,?,?,? from dual";
                       ary = new ArrayList();
                       ary.add(rootPkt);
                       ary.add(cuQty);
                       ary.add(cuCts);
                       ary.add(cuRte);
                       ary.add(String.valueOf(trfQty));
                       ary.add(String.valueOf(trfCts));
                       ary.add(String.valueOf(trfRte));
                       ary.add(String.valueOf(fnlQty));
                       ary.add(String.valueOf(fnlCts));
                       ary.add(String.valueOf(fnlRte));
                       ary.add(adjQty);
                       ary.add(adjCts);
                       ary.add(adjRte);
                       
                       db.execUpd("mix_log", mixLog, ary);
                       
                     }
                  ary = new ArrayList();
                  ary.add(vwTMdl);
                  ct = db.execCall("srch", "PKGMKT.PKTPRP(pMdl =>?)", ary);
                  
                }
             req.setAttribute("msg", "process done Successful....");
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
                req.setAttribute("msg","process Failed...");
            } finally {
              session.setAttribute("ToPktList", new ArrayList());
              form.reset();
              
            }
          ArrayList pktList = SearchResult(req, res, vwTMdl);
         req.setAttribute("pktList", pktList);
         req.setAttribute("viewFNL","yes");
       return am.findForward("load");

     }}
    public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res, String mdl ){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap pktList = new HashMap();
      ArrayList stkIdnLst = new ArrayList();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
       
        ArrayList vwPrpLst = ASPrprViw(req,res,mdl);
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , srch_id , rmk , quot ";

        

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
                String stk_idn= util.nvl(rs.getString("stk_idn"));
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.put("vnm",vnm);
                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                  pktPrpMap.put("quot",util.nvl(rs.getString("quot")));

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
                     
                        
                        pktPrpMap.put(prp, val);
                         }
                              
                    pktList.put(stk_idn, pktPrpMap);
                stkIdnLst.add(stk_idn);
                }
            rs.close(); 
            stmt.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
      
      session.setAttribute("PktHashMap", pktList);
        return stkIdnLst;
    }
    
    public ArrayList ASPrprViw(HttpServletRequest req , HttpServletResponse res,String mdl){
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
         
              ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = ? and flg='Y' order by rnk ",
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
    
    public ActionForward LoadPkt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
                 throws Exception {
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
         TransferPktForm form = (TransferPktForm)af;
        
    
       return am.findForward("loadPkt");

     }
    }
   
    public ActionForward CreatePkt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
                 throws Exception {
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
         TransferPktForm form = (TransferPktForm)af;
       String vwTMdl = (String)info.getValue("VWTMDL");
       String vnmTo = util.nvl((String)form.getValue("vnmTo"));
       String stt = (String)form.getValue("sttCTo");
          if(!vnmTo.equals(""))
        stt = (String)form.getValue("sttPTo");

        if(stt==null)
            stt = util.nvl(req.getParameter("stt"));
      
       String count = util.nvl(req.getParameter("COUNT"));
       ArrayList vwPrpLst = (ArrayList)session.getAttribute(vwTMdl);
       String vnmStr="";
          if(!count.equals("")){
              int loop = Integer.parseInt(count);
              for(int i=0;i<loop;i++){

                    String insMst =
                        "DP_INS_MSTK(pStt => ?, pPktTyp => ?, pNewIdn =>?, pNewVnm =>?)";
                     ArrayList ary = new ArrayList();
                    ary.add(stt);
                    ary.add("MIX");
                    
                    
                    ArrayList out = new ArrayList();
                    out.add("I");
                    out.add("V");
                    CallableStatement cst = db.execCall("findMstkId", insMst, ary, out);
                    int newIdn = cst.getInt(ary.size()+1);
                    String vnm = cst.getString(ary.size()+1);
                cst.close();
                cst=null;
                    if(newIdn>0){
                    for(int j=0;j<vwPrpLst.size();j++){
                      String lprp =(String)vwPrpLst.get(j);
                      String val = util.nvl((String)form.getValue(lprp+"_"+i));

                    ary = new ArrayList();
                    ary.add(String.valueOf(newIdn));
                    ary.add(lprp);
                    ary.add(val);
                    String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                    int ct = db.execCall("stockUpd",stockUpd, ary);
                    
                    }
                      vnmStr= vnmStr+","+vnm;
                      session.setAttribute("newPkt",vnm);

                    }
                  
              }
              if(!vnmStr.equals("")){
              if(!vnmTo.equals("")){
                vnmTo = vnmTo+""+vnmStr;
                form.setValue("vnmTo", vnmTo);
              }
                req.setAttribute("msg", "New Paackets created :-"+vnmStr);
              }else{
                  req.setAttribute("msg", "some error in process..");
                }
            
          }
        
       return am.findForward("loadPkt");

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
                util.updAccessLog(req,res,"Transfer Pkt", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Transfer Pkt", "init");
            }
            }
            return rtnPg;
            }


  }
