package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.UIForms;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

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

public class SingleToMixAction extends DispatchAction {
      
    public SingleToMixAction() {
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
   
      SingleToMixForm form = (SingleToMixForm)af;
      form.resetAll();
      ArrayList    ary          = null;
      ArrayList  sttLst = new ArrayList();
      GenericInterface genericInt = new GenericImpl();
        info.setValue("TYP", "SIN");
        info.setValue("SRCHFMDL", "TRAN_CRT_SFRM");
        info.setValue("SRCHTMDL", "TRAN_CRT_STO");
        info.setValue("VWFMDL", "TRN_SFRM_VW");
        info.setValue("VWTMDL", "TRN_STO_VW");
        info.setValue("STTFM", "TRANS_SINPKT");
        info.setValue("STTTO", "TRANS_MIXPKT");

      
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
       int frmSrchId=0;
       SingleToMixForm form = (SingleToMixForm)af;
       String trnsTyp= util.nvl(req.getParameter("transmode"));
       String vwFMdl = (String)info.getValue("VWFMDL");
       String vwTMdl = (String)info.getValue("VWTMDL");
       String typ = (String)info.getValue("TYP");

       if(trnsTyp.equals("PKT")){
         String sttTo = (String)form.getValue("sttPTo");

         String vnmTo = util.nvl((String)form.getValue("vnmTo"));
         if(!vnmTo.equals("")){
         db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());

         
         vnmTo = util.getVnm(vnmTo);
         String insttoGt= "  Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, quot,sk1,flg , qty , cts ,stt ) " +
         " select  a.pkt_ty, a.idn, a.vnm, nvl(a.cmp,a.upr) ,a.sk1,'Z' , a.qty , a.cts , a.stt "+
         "   from mstk a where a.vnm in ("+vnmTo+") and a.stt=? and a.pkt_ty in ('MIX','MX')";
         
            
         ArrayList ary = new ArrayList();
         ary.add(sttTo);
         ct = db.execDirUpd("instGt", insttoGt, ary);
         
         ary = new ArrayList();
         ary.add(vwTMdl);
         ct = db.execCall("srch", "PKGMKT.PKTPRP(pMdl =>?)", ary);
         }
         req.setAttribute("stt", sttTo);
         session.setAttribute("ParamsMap", new HashMap());
         form.setValue("FrmSrchId","0");

         
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
           if(paramsMap.size()>2){
           frmSrchId = util.genericSrchEntries(paramsMap);
               form.setValue("FrmSrchId",String.valueOf(frmSrchId));

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
             session.setAttribute("ParamsMap", paramsMap);

             int toSrchId = util.genericSrchEntries(paramsMap);
             db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
             String  srch_pkg="DP_MIX_SRCH(pSrchId => ?, pMdl=> ?)";
             ArrayList ary = new ArrayList();
             ary.add(String.valueOf(toSrchId));
             ary.add(vwTMdl);
             ct = db.execCall("srch", srch_pkg ,ary);
             
           }
           form.setValue("vnmFrm","");
           form.setValue("vnmTo", "");
           req.setAttribute("stt", sttTo);

         }
       
     if(ct>0){
       ArrayList pktList = SearchResult(req, res, vwTMdl);
       if(pktList!=null && pktList.size()>1){
       req.setAttribute("ToPktList", pktList);
       req.setAttribute("viewTo", "yes");
       }else{
           if(pktList!=null && pktList.size()>0){
         HashMap PktDtl = (HashMap)session.getAttribute("PktHashMap");
         String toId = (String)pktList.get(0);
          String stt=(String)form.getValue("sttCTo");
           HashMap ToPktDtl = (HashMap)PktDtl.get(toId);
         session.setAttribute("ToPktDtl", ToPktDtl);
         if(trnsTyp.equals("PKT")){
              String sttFo = (String)form.getValue("sttPFm");
               stt = sttFo;
              db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());

           String vnmFo = util.nvl((String)form.getValue("vnmFrm"));
           vnmFo = util.getVnm(vnmFo);
           String instGt= "  Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, quot,sk1,flg , qty , cts,stt ) " +
           " select  a.pkt_ty, a.idn, a.vnm, nvl(a.cmp,a.upr) ,a.sk1,'Z' , a.qty , a.cts,a.stt  "+
           "   from mstk a where a.vnm in ("+vnmFo+") and a.stt= ? and a.pkt_ty = 'NR'";
              
           ArrayList ary = new ArrayList();
           ary.add(sttFo);
           ct = db.execUpd("instGt", instGt, ary);
           
           ary = new ArrayList();
           ary.add(vwFMdl);
           ct = db.execCall("srch", "PKGMKT.PKTPRP(pMdl =>?)", ary);
            }else{
             if(frmSrchId>0){
           db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
           
               String srch_pkg = "srch_pkg.STK_SRCH(pSrchId => ?, pMdl=> ?)";
               ArrayList ary = new ArrayList();
               ary.add(String.valueOf(frmSrchId));
               ary.add(vwFMdl);
               ct = db.execCall("srch", srch_pkg ,ary);
               

             }
             req.setAttribute("stt", stt);
           req.setAttribute("toID", toId);

         }
         if(ct>0){
           ArrayList FrmpktList = SearchResult(req, res, vwFMdl);
           session.setAttribute("FrmPktList", FrmpktList);
         }
         req.setAttribute("viewFm", "yes");

       }else
       req.setAttribute("viewTo", "yes");
     
           }}else{
             req.setAttribute("viewTo", "yes");
            ASPrprViw(req, res, vwTMdl);
           }

     
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
       SingleToMixForm form = (SingleToMixForm)af;
      String toID = util.nvl(req.getParameter("toID"));
     String vwFMdl = (String)info.getValue("VWFMDL");
     String typ = (String)info.getValue("TYP");

      if(!toID.equals("")){
     HashMap pktMap = (HashMap)session.getAttribute("PktHashMap");
     HashMap ToPktDtl = (HashMap)pktMap.get(toID);
     session.setAttribute("ToPktDtl", ToPktDtl);
       }
       int ct=0;
     String FmSrchID = util.nvl((String)form.getValue("FrmSrchId"),"0");
      String vnmFm = util.nvl((String)form.getValue("vnmFrm"));
     String stt = (String)form.getValue("sttCFm");

     if(FmSrchID!=null && !FmSrchID.equals("0")){
       db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());

       String srch_pkg = "srch_pkg.STK_SRCH(pSrchId => ?, pMdl=> ?)";
       ArrayList ary = new ArrayList();
       ary.add(String.valueOf(FmSrchID));
       ary.add(vwFMdl);
       ct = db.execCall("srch", srch_pkg ,ary);
        
     }else if(!vnmFm.equals("")){
         vnmFm = util.getVnm(vnmFm);
       String sttFm = (String)form.getValue("sttPFm");
           stt =sttFm;
       db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());

     String instGt= "  Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, quot,sk1,flg , qty , cts , stt ) " +
     " select  a.pkt_ty, a.idn, a.vnm, nvl(a.cmp,a.upr) ,a.sk1,'Z' , a.qty , a.cts , a.stt "+
     "   from mstk a where a.vnm in ("+vnmFm+") and a.stt= ? and a.pkt_ty ='NR'";
       
     ArrayList ary = new ArrayList();
     ary.add(sttFm);
     ct = db.execUpd("instGt", instGt, ary);
     
     ary = new ArrayList();
     ary.add(vwFMdl);
     ct = db.execCall("srch", "PKGMKT.PKTPRP(pMdl =>?)", ary);
     
     }
     if(ct>0){
       req.setAttribute("stt", stt);
       req.setAttribute("toID", toID);
       ArrayList pktList = SearchResult(req, res, vwFMdl);
       session.setAttribute("FrmPktList", pktList);
     }
     req.setAttribute("viewFm", "yes");
     
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
       SingleToMixForm form = (SingleToMixForm)af;
       String toPkt = req.getParameter("toPkt");
       String fmStt = req.getParameter("fmStt");
         String vwTMdl = (String)info.getValue("VWTMDL");

     ArrayList FrmPktList = (ArrayList)session.getAttribute("FrmPktList");
       int  ct=0;
     for (int i = 0; i < FrmPktList.size(); i++) {
         String stk_idn = (String)FrmPktList.get(i);
         String isChecked = util.nvl(req.getParameter("CHK_"+stk_idn));
         if (!isChecked.equals("")) {
           String updateMstk =
               "update mstk set  stt=? , pkt_ty=? where idn=?";
           ArrayList ary = new ArrayList();
           ary.add(fmStt+"_MRG");
           ary.add(toPkt);
           ary.add(stk_idn);
            ct = db.execUpd("upMstk", updateMstk, ary);

          
         }}
       if(ct>0){
         String fnlQty = util.nvl((String)form.getValue("FNL_QTY"));
         String fnlCts = util.nvl((String)form.getValue("FNL_CTS"));
         String fnlRte = util.nvl((String)form.getValue("FNL_RTE"));
         
         String updateMstk =
             "update mstk set qty=?  where idn=?";
         ArrayList ary = new ArrayList();
         ary.add(fnlQty);
         ary.add(toPkt);
          ct = db.execUpd("upMstk", updateMstk, ary);

        String  stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";

         ary = new ArrayList();
         ary.add(toPkt);
         ary.add("CRTWT");
         ary.add(fnlCts);
         ct = db.execCall("stockUpd", stockUpd, ary);
          
         ary = new ArrayList();
         ary.add(toPkt);
         ary.add("RTE");
         ary.add(fnlRte);
         ct = db.execCall("stockUpd", stockUpd, ary);
         
         
         ary = new ArrayList();
         ary.add(toPkt);
         ary.add("MIX_RTE");
         ary.add(fnlRte);
         ct = db.execCall("stockUpd", stockUpd, ary);
         
        
           
           if(ct>0){
             ary = new ArrayList();
             ary.add(toPkt);
             
             String instGt= "  Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, quot,sk1,flg , qty , cts ) " +
             " select  a.pkt_ty, a.idn, a.vnm, nvl(a.cmp,a.upr) ,a.sk1,'Z' , a.qty , a.cts  "+
             "   from mstk a where a.idn = ? ";
             
             db.execUpd("gt insert", instGt, ary);
             
                ary = new ArrayList();
                ary.add(vwTMdl);
                ct = db.execCall("srch", "PKGMKT.PKTPRP(pMdl =>?)", ary);
                  req.setAttribute("msg", "process done Successful....");

             
                ArrayList pktList = SearchResult(req, res, vwTMdl);
                req.setAttribute("pktList", pktList);
                req.setAttribute("viewFNL","yes");
             
              }
           
       }else{
         req.setAttribute("msg", "Some error in process..");

       }
         form.reset();
         session.setAttribute("FrmSrchList",new  ArrayList());
         session.setAttribute("ToPktDtl",new HashMap());
         session.setAttribute("FrmPktList",new ArrayList());
         
       }

   
     return am.findForward("load");

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
       SingleToMixForm form = (SingleToMixForm)af;
     String vwFMdl = (String)info.getValue("VWFMDL");
     String vwTMdl = (String)info.getValue("VWTMDL");

     String typ = (String)info.getValue("TYP");
     int ct=0;
     String FrmSrchId = util.nvl((String)form.getValue("FrmSrchId"),"0");
     String vnmFm = util.nvl((String)form.getValue("vnmFrm"));
     String stt = (String)form.getValue("sttCFm");
     String toPkt = (String)session.getAttribute("newPkt");
       if(toPkt!=null){
         db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());

         String instGt= "  Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, quot,sk1,flg , qty , cts , stt ) " +
         " select  a.pkt_ty, a.idn, a.vnm, nvl(a.cmp,a.upr) ,a.sk1,'Z' , a.qty , a.cts , a.stt  "+
         "   from mstk a where a.vnm in ('"+toPkt+"') and a.pkt_ty in ('MIX','MX')";
         
         ArrayList ary = new ArrayList();
         ct = db.execUpd("instGt", instGt, ary);
         
         ary = new ArrayList();
         ary.add(vwTMdl);
         ct = db.execCall("srch", "PKGMKT.PKTPRP(pMdl =>?)", ary);
         
         ArrayList pktList = SearchResult(req, res, vwTMdl);
         HashMap PktDtl = (HashMap)session.getAttribute("PktHashMap");
         String toId = (String)pktList.get(0);
           HashMap ToPktDtl = (HashMap)PktDtl.get(toId);
         session.setAttribute("ToPktDtl", ToPktDtl);
         
         
       }
     if(!vnmFm.equals("")){
       vnmFm = util.getVnm(vnmFm);
     String sttFm = (String)form.getValue("sttPFm");
         stt =sttFm;
     db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());

     String instGt= "  Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, quot,sk1,flg , qty , cts , stt ) " +
     " select  a.pkt_ty, a.idn, a.vnm, nvl(a.cmp,a.upr) ,a.sk1,'Z' , a.qty , a.cts , a.stt "+
     "   from mstk a where a.vnm in ("+vnmFm+") and a.stt= ?  and a.pkt_ty = 'NR'";
     
     ArrayList ary = new ArrayList();
     ary.add(sttFm);
     ct = db.execUpd("instGt", instGt, ary);
     
     ary = new ArrayList();
     ary.add(vwFMdl);
     ct = db.execCall("srch", "PKGMKT.PKTPRP(pMdl =>?)", ary);
     
       
     
     }else{
       db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());

       String srch_pkg = "srch_pkg.STK_SRCH(pSrchId => ?, pMdl=> ?)";
       ArrayList ary = new ArrayList();
       ary.add(String.valueOf(FrmSrchId));
       ary.add(vwFMdl);
       ct = db.execCall("srch", srch_pkg ,ary);
       
     }
     if(ct>0){
     req.setAttribute("stt", stt);
     ArrayList pktList = SearchResult(req, res, vwFMdl);
     session.setAttribute("FrmPktList", pktList);
     }
     req.setAttribute("viewFm", "yes");
     
     return am.findForward("load");
     
   }
   }
  
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
              pktPrpMap.put("qty",util.nvl(rs.getString("qty"),"0"));
              pktPrpMap.put("cts",util.nvl(rs.getString("cts"),"0"));
                pktPrpMap.put("quot",util.nvl(rs.getString("quot")));
                pktPrpMap.put("stt",util.nvl(rs.getString("stt")));

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
              util.updAccessLog(req,res,"Single To Mix", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Single To Mix", "init");
          }
          }
          return rtnPg;
          }

   
}
