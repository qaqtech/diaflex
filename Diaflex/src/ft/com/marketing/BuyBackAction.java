package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.MailAction;
import ft.com.dao.ByrDao;
import ft.com.dao.PktDtl;

import java.io.IOException;

import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.ResultSet;

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

public class BuyBackAction extends DispatchAction {
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
      BuyBackForm udf = (BuyBackForm)af;
      util.updAccessLog(req,res,"pre Buy Back", "load");
     ArrayList byrList = new ArrayList();
     ResultSet rs      = null;
     String    getByr  ="SELECT distinct a.nme_idn,\n" + 
         "    c.nme byr \n" + 
         "    FROM mjan a,\n" + 
         "    jandtl b,\n" + 
         "    nme_v c\n" + 
         "  WHERE a.nme_idn = c.nme_idn\n" + 
         "  AND a.idn       = b.idn\n" + 
         "  AND a.stt      <> 'RT'\n" + 
         "  and a.typ in ('BB','WB') and b.stt in ('IS','PR','WFP') order by c.nme";

     rs = db.execSql("byr", getByr, new ArrayList());

     while (rs.next()) {
         ByrDao byr = new ByrDao();

         byr.setByrIdn(rs.getInt("nme_idn"));
         byr.setByrNme(rs.getString("byr"));
         byrList.add(byr);
     }
     rs.close();
     //        udf.setValue("byrList", byrList);
     udf.setByrLstFetch(byrList);
     udf.setByrLst(byrList);

     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("BUYBACK_CONFIRMATION");
     if(pageDtl==null || pageDtl.size()==0){
     pageDtl=new HashMap();
     pageDtl=util.pagedef("BUYBACK_CONFIRMATION");
     allPageDtl.put("BUYBACK_CONFIRMATION",pageDtl);
     }
     info.setPageDetails(allPageDtl);
    util.updAccessLog(req,res,"pre Buy Back", "End");
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
       BuyBackForm udf = (BuyBackForm)af;
      util.updAccessLog(req,res,"pre Buy Back", "fetch");
     String    pnd     = req.getParameter("pnd");
     ArrayList params = new ArrayList();
     ArrayList pkts    = new ArrayList();
     String    memoSrchTyp = util.nvl((String) udf.getValue("memoSrch"));
     String    vnmLst      = "";
     String    memoIds     = "";
     String buytyp = util.nvl((String)udf.getValue("buytyp"));
     if (memoSrchTyp.equals("ByrSrch")) {
         Enumeration reqNme = req.getParameterNames();

         while (reqNme.hasMoreElements()) {
             String paramNm = (String) reqNme.nextElement();

             if (paramNm.indexOf("cb_memo") > -1) {
                 String val = req.getParameter(paramNm);

                 if (memoIds.equals("")) {
                     memoIds = val;
                 } else {
                     memoIds = memoIds + "," + val;
                 }
             }
         }
     } else {
         memoIds =util.nvl((String)udf.getValue("memoIdn"));
         vnmLst=util.nvl((String)udf.getValue("vnmLst"));
     }
      if(buytyp.equals(""))
          buytyp="ALL";
     if (pnd != null) {
         memoIds = util.nvl(req.getParameter("memoId"));
         String nmeId = util.nvl(req.getParameter("nmeId"));
         String trmId = util.nvl(req.getParameter("trmId"));
         if(!nmeId.equals("") && !trmId.equals("")){
         String getmemoIds="select idn from mjan where nme_idn=? and trms_idn=? and stt='IS' order by idn";    
         params = new ArrayList();
         params.add(nmeId);
         params.add(trmId);
         ResultSet rs1 = db.execSql("get memo Ids", getmemoIds,  params);
         while(rs1.next()){
         if (memoIds.equals(""))
         memoIds = util.nvl(rs1.getString("idn"));
         else
         memoIds = memoIds + "," + util.nvl(rs1.getString("idn"));
         }
             rs1.close();
         }
     }
           
     if(!vnmLst.equals("")){
         boolean isRtn = true;
         vnmLst=util.getVnm(vnmLst);
         int cnt=0;
         String checkSql ="select distinct c.nmerel_idn  from jandtl a, mstk b , mjan c " + 
         "where a.mstk_idn = b.idn and c.idn = a.idn and a.stt  in ('IS','PR','WFP') and c.typ in ('BB','WB') and c.stt='IS' " + 
         " and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) and b.stt in ('MKBB','MKPR','MKWP')";
         if(!buytyp.equals("ALL")){
             checkSql = checkSql+" and a.stt in ('"+buytyp+"')" ;
         }
         ResultSet rs1 = db.execSql("check rel", checkSql, new ArrayList());
         while(rs1.next()){
             cnt++;
         }
         rs1.close();
         if(cnt==1){  
             isRtn = false;
             String saleIdSql = "select distinct c.idn  from jandtl a, mstk b , mjan c " + 
             "  where a.mstk_idn = b.idn and c.idn = a.idn and a.stt  in ('IS','PR','WFP') and c.typ in ('BB','WB') and c.stt='IS' " + 
             "  and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) and b.stt in ('MKBB','MKPR','MKWP')";
           if(!buytyp.equals("ALL")){
               saleIdSql = saleIdSql+" and a.stt in ('"+buytyp+"')" ;
           }else{
             saleIdSql = saleIdSql+" and a.stt not in ('DLV','RT') ";
           }
             rs1 = db.execSql("saleID",saleIdSql, new ArrayList());
             while(rs1.next()){
                 String appIdnval = util.nvl(rs1.getString("idn"));
                 if (memoIds.equals("")) {
                     memoIds = appIdnval;
                 } else {
                    memoIds = memoIds + "," + appIdnval;
                 }
             }
             rs1.close();
         }
         if(isRtn){
             req.setAttribute("RTMSG", "Please verify packets");
            return load(am, af, req, res);
         }
         
     }
     if(!memoIds.equals("")) {
    ResultSet rs =null;
         memoIds = util.getVnm(memoIds);
     int cout = 0;

     rs = db.execSql("check", "select distinct nme_idn from mjan where idn in (" + memoIds + ")", new ArrayList());

     while (rs.next()) {
         cout++;
     }
         rs.close();
     if (cout < 2) {
         double exh_rte = 0;
         rs = db.execSql("check", "select max(exh_rte) exhRte , a.nmerel_idn , nvl(b.cur,'USD') cur " + 
         " from mjan a , nmerel b where idn in ("+memoIds+") " + 
         " and a.nmerel_idn = b.nmerel_idn  group by a.nmerel_idn, b.cur ", new ArrayList());
         while(rs.next()){
            exh_rte = Double.parseDouble(util.nvl(rs.getString("exhRte"),"1"));
            udf.setValue("exhRte", exh_rte); 
            udf.setValue("cur", util.nvl(rs.getString("cur")).toUpperCase());
         }
         rs.close();
         String getAvgDis = "select sum(a.qty) qty,sum(a.cts) cts,trunc(sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)), 2) vlu,trunc(((sum(trunc(a.cts,2)*(nvl(a.fnl_sal, a.quot)/"+exh_rte+")) / sum(trunc(a.cts,2)*b.rap_rte))*100) - 100, 2) avg_dis ," +
                           " trunc(((sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts,2))))) avg_Rte , b.stt from jandtl a, mstk b " + 
                            " where a.mstk_idn = b.idn and a.stt <> 'RT' " + 
                             " and a.idn in (" + memoIds + ") and b.stt in ('MKBB','MKPR','MKWP') ";
         if(!vnmLst.equals(""))
         getAvgDis = getAvgDis+" and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
         
       if(!buytyp.equals("ALL")){
           getAvgDis = getAvgDis+" and a.stt in ('"+buytyp+"')" ;
       }
         getAvgDis = getAvgDis+" group by b.stt";
         params = new ArrayList();
       
         ResultSet mrs = db.execSql(" Memo Info", getAvgDis , params);
         while(mrs.next()){
           String stt = util.nvl(mrs.getString("stt"));
             
           String str="pn_";
           if(stt.equals("MKPR"))
               str="pr_";
          if(stt.equals("MKWP"))
            str="wfp_";
           udf.setValue(str+"avgPrc",mrs.getString("avg_dis"));
           udf.setValue(str+"avgDis",mrs.getString("avg_Rte"));
           udf.setValue(str+"qty",mrs.getString("qty"));
           udf.setValue(str+"cts",mrs.getString("cts"));
           udf.setValue(str+"vlu",mrs.getString("vlu"));
         }
         mrs.close();
         String getMemoInfo =
             " select a.nme_idn, a.nmerel_idn,byr.get_trms(a.nmerel_idn) trms, byr.get_nm(a.nme_idn) byr,a.typ, a.qty, a.cts, a.vlu, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte "
             + " from mjan a  where a.idn in (" + memoIds + ") and a.stt <> 'RT' and a.typ in ('BB','WB')";

         params = new ArrayList();
         mrs = db.execSql(" Memo Info", getMemoInfo, params);

         if (mrs.next()) {
             udf.setNmeIdn(mrs.getInt("nme_idn"));
             udf.setRelIdn(mrs.getInt("nmerel_idn"));
             udf.setByr(mrs.getString("byr"));
             udf.setTyp(mrs.getString("typ"));
             udf.setDte(mrs.getString("dte"));
             udf.setValue("trmsLb", mrs.getString("trms"));
             HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(mrs.getInt("nme_idn")));
             udf.setValue("email",util.nvl((String)buyerDtlMap.get("EML")));
             udf.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
             udf.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
                 String getPktData =
                     " select a.idn , a.qty , mstk_idn, nvl(fnl_sal, quot) memoQuot, quot, nvl(fnl_sal, quot) fnl_sal , b.vnm "
                     + ", b.cts, nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte,to_char(trunc(a.cts,2) * b.rap_rte, '99999990.00') rapVlu, a.stt "
                     + " ,  decode(rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)) dis,  decode(rap_rte, 1, '', trunc(((nvl(fnl_sal, quot)/"+exh_rte+"/greatest(b.rap_rte,1))*100)-100,2)) mDis "
                     + " from jandtl a, mstk b where a.mstk_idn = b.idn " + " and a.idn in (" + memoIds+ ") and b.stt in ('MKBB','MKPR','MKWP')";
                     
                 if(!vnmLst.equals("")){
                 getPktData = getPktData + " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
                 }
           if(!buytyp.equals("ALL")){
                getPktData = getPktData+" and a.stt in ('"+buytyp+"')" ;
           }else{
             getPktData = getPktData+" and a.stt  in ('IS','PR','WFP') ";
           }
                 // " and decode(typ, 'I','IS', 'E', 'IS', 'WH', 'IS', 'AP') = stt ";
                 getPktData = getPktData + "" + " order by b.sk1";
                 
                 params = new ArrayList();
                 rs     = db.execSql(" memo pkts", getPktData, params);

                 while (rs.next()) {
                     PktDtl pkt    = new PktDtl();
                     long   pktIdn = rs.getLong("mstk_idn");

                     pkt.setPktIdn(pktIdn);
                     pkt.setMemoId(util.nvl(rs.getString("idn")));
                     pkt.setRapRte(util.nvl(rs.getString("rap_rte")));
                     pkt.setQty(util.nvl(rs.getString("qty")));
                     pkt.setCts(util.nvl(rs.getString("cts")));
                     pkt.setRte(util.nvl(rs.getString("rte")));
                     pkt.setMemoQuot(util.nvl(rs.getString("memoQuot")));
                     pkt.setByrRte(util.nvl(rs.getString("quot")));
                     pkt.setFnlRte(util.nvl(rs.getString("fnl_sal")));
                     pkt.setDis(rs.getString("dis"));
                     pkt.setByrDis(rs.getString("mDis"));
                     pkt.setVnm(rs.getString("vnm"));
                     String lStt = rs.getString("stt");

                     pkt.setStt(lStt);
                   udf.setValue("stt_" + pktIdn, lStt+"_"+pktIdn);
                     pkt.setValue("rapVlu", util.nvl(rs.getString("rapVlu")));
                     String getPktPrp =
                         " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                         + " from stk_dtl a, mprp b, rep_prp c "
                         + " where a.mprp = b.prp and b.prp = c.prp and a.grp=1 and c.mdl = 'MEMO_RTRN' and a.mstk_idn = ? "
                         + " order by c.rnk, c.srt ";

                     params = new ArrayList();
                     params.add(Long.toString(pktIdn));

                     ResultSet rs1 = db.execSql(" Pkt Prp", getPktPrp, params);

                     while (rs1.next()) {
                         String lPrp = rs1.getString("mprp");
                         String lVal = rs1.getString("val");

                         pkt.setValue(lPrp, lVal);
                     }
                     rs1.close();
                     pkts.add(pkt);
                 }
                 rs.close();
             

             req.setAttribute("view", "Y");
         }
         mrs.close();
     } else {
         req.setAttribute("error", "please select memoIds of one buyer");
         req.setAttribute("view", "N");
         return  load(am, af, req, res);
     }}else {
         req.setAttribute("error", "please select memoIds of one buyer");
         req.setAttribute("view", "N");
         return  load(am, af, req, res);
     }
     
     ArrayList chargesLst=new ArrayList();
     String chargesQ="Select  c.idn,c.typ,c.dsc,c.stg optional,c.flg,c.fctr,c.db_call,c.rmk  From Rule_Dtl A, Mrule B , Charges_Typ C Where A.Rule_Idn = B.Rule_Idn And \n" + 
     "       c.typ = a.Chr_Fr and b.mdl = 'JFLEX' and b.nme_rule = 'BUYBK_CHARGES' and c.stt='A' and a.til_dte is null order by a.srt_fr , a.dsc , a.chr_fr";
    ResultSet  mrs = db.execSql("chargesQ", chargesQ, new ArrayList());
     while(mrs.next()){
         HashMap dtl=new HashMap();
         dtl.put("idn",util.nvl(mrs.getString("idn")));
         dtl.put("dsc",util.nvl(mrs.getString("dsc")));
         dtl.put("autoopt",util.nvl(mrs.getString("optional")));
         dtl.put("flg",util.nvl(mrs.getString("flg")));
         dtl.put("typ",util.nvl(mrs.getString("typ")));
         dtl.put("fctr",util.nvl(mrs.getString("fctr")));
         dtl.put("fun",util.nvl(mrs.getString("db_call")));
         dtl.put("rmk",util.nvl(mrs.getString("rmk")));
         chargesLst.add(dtl);
     }
     mrs.close();
     udf.setPkts(pkts);
     session.setAttribute("chargesLst", chargesLst);
     ArrayList trnsChargeList = new ArrayList();
     String buyBackMemoChg = "select (a.charges*b.fctr) chg , b.dsc , a.ref_idn , b.typ from Trns_Charges a , charges_typ b where a.ref_idn in ("+memoIds+")\n" + 
     "and a.charges_idn = b.idn and a.stt='A' and a.ref_typ='MEMO'";
     ResultSet rs = db.execSql("buyBackChg", buyBackMemoChg, new ArrayList());
      while(rs.next()){
       String typ = util.nvl(rs.getString("typ"));
       String chg = util.nvl(rs.getString("chg"));
        String memo = util.nvl(rs.getString("ref_idn"));
       udf.setValue(typ, chg);
       udf.setValue("CH_MEMO", memo);
      }
           rs.close();
     req.setAttribute("TrnsCharge", trnsChargeList);
     req.setAttribute("view", "Y");
     info.setValue("RTRN_PKTS", pkts);
     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("BUYBACK_CONFIRMATION");
     if(pageDtl==null || pageDtl.size()==0){
     pageDtl=new HashMap();
     pageDtl=util.pagedef("BUYBACK_CONFIRMATION");
     allPageDtl.put("BUYBACK_CONFIRMATION",pageDtl);
     }
     info.setPageDetails(allPageDtl);
     util.updAccessLog(req,res,"pre Buy Back", "End");
    return load(am, af, req, res);
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

      ArrayList params = null;
      boolean isRtn = false;
      BuyBackForm udf = (BuyBackForm)af;
      util.updAccessLog(req,res,"pre Buy Back", "Save");
      HashMap dbinfo = info.getDmbsInfoLst();
      ArrayList pkts       = new ArrayList();
      int       dlvMemoIdn = 0;
      String       refMemoIdn = "";
      String    memoTyp    = "BAP",
                lFlg       = "NN";
      String buttonPressed = "";
      ArrayList rtnPktLst = new ArrayList();
      ArrayList dlvPktLst = new ArrayList();
      ArrayList prPktLst = new ArrayList();
      ArrayList wfpPktLst = new ArrayList();
     String exh_rte = util.nvl((String)udf.getValue("exh_rte"));
     
      pkts = (ArrayList) info.getValue("RTRN_PKTS");
          //(ArrayList) info.getValue(memoIdn + "_PKTS");    // udf.getPkts();

      for (int i = 0; i < pkts.size(); i++) {
          PktDtl pkt     = (PktDtl) pkts.get(i);
          long   lPktIdn = pkt.getPktIdn();
          String memoIdn = pkt.getMemoId();
          String vnm = pkt.getVnm();
          String lSttLst    = util.nvl((String)udf.getValue("stt_" + lPktIdn));
          String lstt[] = lSttLst.split("_");
          String lStt = lstt[0];
          refMemoIdn = memoIdn;

          String updPkt = "";

          params = new ArrayList();
          params.add(String.valueOf(memoIdn));
          params.add(Long.toString(lPktIdn));
         
          if (lStt.equals("DLV")) {
              if (dlvMemoIdn == 0) {
                
                  ArrayList ary = new ArrayList();

                  ary.add(Integer.toString(udf.getNmeIdn()));
                  ary.add(Integer.toString(udf.getRelIdn()));
                  ary.add("IS");
                  ary.add(memoTyp);
                  ary.add("NN");
                  ary.add(String.valueOf(memoIdn));
                  ary.add(exh_rte);
                  ArrayList out = new ArrayList();

                  out.add("I");

                  CallableStatement cst = null;

                  cst = db.execCall(
                      "MKE_HDR ",
                      "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ? , pTyp => ? , pflg => ? , pFrmId => ?,  pXrt => ? , pMemoIdn => ?)",
                      ary, out);
                  dlvMemoIdn = cst.getInt(ary.size()+1);
                cst.close();
                cst=null;
              }

              updPkt = " memo_pkg.BB_TRNS(pMemoId => ?, pStkIdn => ?, pStt => ? )";
              params = new ArrayList();
              params.add(memoIdn);
              params.add(Long.toString(lPktIdn));
              params.add(lStt);
              int ct = db.execCall(" App Pkts", updPkt, params);
              
              String app_memoPkt = "memo_pkg.App_Memo_Pkt(pFrmMemoId => ? , pAppMemoId => ?, pStkIdn => ?, pAppFlg => ?)";
              params = new ArrayList();
              params.add(String.valueOf(memoIdn));
              params.add(String.valueOf(dlvMemoIdn));
              params.add(Long.toString(lPktIdn));
              params.add(lFlg);
              ct = db.execCall("app_memo_pkt",app_memoPkt , params);
              if(ct>0)
              dlvPktLst.add(vnm);
              
            String updJanQty = "jan_qty(?) ";
            params = new ArrayList();
            params.add(String.valueOf(memoIdn));
           ct = db.execCall("JanQty", updJanQty, params);
          }else{
              
              
            updPkt = " memo_pkg.BB_TRNS(pMemoId => ?, pStkIdn => ?, pStt => ? )";
            params = new ArrayList();
            params.add(memoIdn);
            params.add(Long.toString(lPktIdn));
            params.add(lStt);
            int ct = db.execCall(" App Pkts", updPkt, params);
            req.setAttribute("memoId", memoIdn);
            if (lStt.equals("RT")) {
              String pndgAlc = "memo_pkg.pndg_alc(?, ?)";
              ArrayList ary     = new ArrayList();
                 ary.add(String.valueOf(memoIdn));
                 ary.add(Long.toString(lPktIdn));
                ct = db.execCall("pngd", pndgAlc, ary);
               rtnPktLst.add(vnm);     
            }else  if (lStt.equals("PR")){
                prPktLst.add(vnm);
              
            }else if(lStt.equals("WFP")){
              wfpPktLst.add(vnm);
            }
              
          }
          }
         
      
          if(dlvMemoIdn!=0){
            refMemoIdn =String.valueOf(dlvMemoIdn);
          }
        int ct=0;
        ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
        if(chargesLst!=null && chargesLst.size()>0){
        for(int i=0;i<chargesLst.size();i++){
          HashMap dtl=new HashMap();
           dtl=(HashMap)chargesLst.get(i);
          String typcharge=(String)dtl.get("typ");
          String charge = util.nvl((String)udf.getValue(typcharge));
          String idn=(String)dtl.get("idn");
            
            String updTran = "update Trns_Charges set charges = ? where ref_idn = ? and ref_typ=? and charges_idn=? ";
           params = new ArrayList();
           params.add(charge);
           params.add(refMemoIdn);
           params.add("MEMO");
           params.add(idn);
           ct = db.execUpd("insertTrnas", updTran, params);
            if(ct <=0){
          String insertQ="Insert Into Trns_Charges (trns_idn, ref_typ, ref_idn,charges_idn,charges,CHARGES_PCT,stt,flg)\n" + 
          "VALUES (TRNS_CHARGES_SEQ.nextval, ?, ?,?,?,'A','N')";
             params = new ArrayList();
             params.add("MEMO");
             params.add(refMemoIdn);
             params.add(idn);
             params.add(charge);
             params.add(charge);
             ct = db.execUpd("insertTrnas", insertQ, params);
            }
         }}

 

     
    String msg = "";
      if (dlvMemoIdn > 0) {
          
      
          

        
          params = new ArrayList();
          params.add(String.valueOf(dlvMemoIdn));

          ct = db.execCall("calQuot", "MEMO_PKG.Cal_Fnl_Quot(pIdn => ?)", params);

          
          String updJanQty = " jan_qty(?) ";
          params    = new ArrayList();
          params.add(Integer.toString(dlvMemoIdn));
          ct = 0;
          ct = db.execCall("JanQty", updJanQty, params);
          
             msg ="Packets "+dlvPktLst.toString()+" Are Deliver Successfully with Dlv ID"+dlvMemoIdn+"</br>";
      req.setAttribute("memoId", Integer.toString(dlvMemoIdn));
      }
    if(rtnPktLst.size() >0)
      msg = msg+" Packets "+rtnPktLst.toString()+" Are Return Successfully </br>";
    if(prPktLst.size() >0)
      msg = msg+" Packets "+prPktLst.toString()+" payment recevie Successfully </br>";
   if(wfpPktLst.size() >0)
          msg = msg+" Packets "+wfpPktLst.toString()+" are waiting for payment. </br>";
    req.setAttribute("msg", msg);
          util.updAccessLog(req,res,"pre Buy Back", "end");
          return load(am, af, req, res);
      }
  }
      public BuyBackAction() {
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
                util.updAccessLog(req,res,"pre Buy Back", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"pre Buy Back", "init");
            }
            }
            return rtnPg;
            }
}
