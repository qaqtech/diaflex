package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.ExcelUtilObj;
import ft.com.GenMail;
import ft.com.GtMgr;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.MailAction;
import ft.com.dao.GtPktDtl;
import ft.com.dao.MemoType;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.OutputStream;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;


public class SearchAction extends DispatchAction {
    
    
    
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          SearchForm srchForm ;
          SearchQuery query = new SearchQuery();
          HashMap dbinfo = info.getDmbsInfoLst();
          String sz = (String)dbinfo.get("SIZE");
        if(info.getVwPrpLst()==null)
        util.initSrch();  
//        if(info.getSrchPrp()==null)
//            util.initPrpSrch();
        srchForm = (SearchForm)form;
        srchForm.reset();
        srchForm.setIsEx("");
        String typ = util.nvl(req.getParameter("Typ"));
        if(typ.equals("")){
          typ = "SRCH";
          info.setIsFancy("SRCH");
        }
          int df_nme_idn = info.getDf_nme_id();
          int df_Rel_idn = info.getDf_rln_id();
        GenericInterface genericInt = new GenericImpl();
        genericInt.genericPrprVw(req,res,"INCLUDE_EXEC_VW","INCLUDE_EXEC_VW");
        ArrayList byrLst= query.getByrList(req,res);
        srchForm.setByrList(byrLst);
//        ArrayList partyLst = query.getPartyList(req,res);
//        srchForm.setPartyList(partyLst);
          if(df_nme_idn!=0){
        ArrayList termsList = query.getTerm(req,res, info.getDf_nme_id());
        srchForm.setTrmList(termsList);
          }
          if(df_Rel_idn!=0){
        ArrayList dmdList = query.getDmdList(req,res, info.getDf_rln_id());
        srchForm.setDmdList(dmdList);
        req.setAttribute("dmdList", dmdList);
        ArrayList webReqList = query.getWebRequest(req, res, info.getDf_rln_id());
        req.setAttribute("webReqList", webReqList);
       ArrayList hrReqList = query.getHrRequest(req, res, info.getDf_rln_id());
       req.setAttribute("hrReqList", hrReqList);
        ArrayList lstMemoList = query.getLstMemo(req, res, info.getDf_rln_id());
        req.setAttribute("lstMemoList", lstMemoList);
          ArrayList lstMemoListNG = query.getLstMemoNG(req, res, info.getDf_rln_id());
          req.setAttribute("lstMemoListNG", lstMemoListNG);
       ArrayList grpNmeList = query.getGrpList(req, res, info.getDf_rln_id());
        req.setAttribute("grpNmeList", grpNmeList);
        ArrayList contactList = query.getContactList(req, res, info.getDf_nme_id());
        req.setAttribute("contDtlList", contactList);
          }
        srchForm.setByrId("0");
        srchForm.setParty(String.valueOf(info.getDf_nme_id()));
        srchForm.setByrRln(String.valueOf(info.getDf_rln_id()));
        srchForm.setValue("partytext", info.getDfNme());
        srchForm.setValue("xrt", info.getDf_xrt());
        info.setMultiSrchLst(new ArrayList());
        info.setSvdmdDis(new ArrayList());
        String srchId = util.nvl((String)info.getDmbsInfoLst().get("DFT_SRCHID"));
        if(!srchId.equals("")){
            dftSrch(am, form, req,res, srchId);
        }
        session.setAttribute("SrchTyp",typ);
//       query.StockViewLyt(req, res);
       String isMix = util.nvl(req.getParameter("form"));
      String pgDef = "SEARCH_RESULT";
      if(isMix.equals("MIX") || isMix.equals("MX"))
          pgDef = "MIXSRCH_RESULT";
      if(isMix.equals("SMX"))
          pgDef = "SMXSRCH_RESULT";
      if(isMix.equals("RGH"))
         pgDef = "RGHSRCH_RESULT";
          String searchView = util.nvl(req.getParameter("searchView"),"GRID");
          info.setSearchView(searchView);
     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);
     if(pageDtl==null || pageDtl.size()==0){
     pageDtl=new HashMap();
     pageDtl=util.pagedef(pgDef);
     allPageDtl.put(pgDef,pageDtl);
     }
     info.setPageDetails(allPageDtl);
      info.setIsMix(isMix);
//      ArrayList rfiddevice = ((ArrayList)info.getRfiddevice() == null)?new ArrayList():(ArrayList)info.getRfiddevice();
//      if(rfiddevice.size() == 0) {
//      util.rfidDevice();    
//      }
      ArrayList memoList= (query.getSrchType(req,res,srchForm,"Y") == null)?new ArrayList():(ArrayList)query.getSrchType(req,res,srchForm,"Y");
      util.getcrtwtPrp(sz,req,res);
      util.setMdl("MEMOSRCH");
      util.updAccessLog(req,res,"Search Page", "Search Page Load done srch Typ Size "+memoList.size());
     finalizeObject(db, util);
     return am.findForward("load");
      }
  }
    public ActionForward genRapExcel(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            SearchForm srchForm ;
            SearchQuery query = new SearchQuery();
        util.updAccessLog(req,res,"Search Page", "Generate Rap Excel");
        int ct = db.execCall("Rap","GEN_FILE_RAP", new ArrayList());
            finalizeObject(db, util);
        return am.findForward("mailSuccess");
        }
    }
    public ActionForward genIdexExcel(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            SearchForm srchForm ;
            SearchQuery query = new SearchQuery();
        util.updAccessLog(req,res,"Search Page", "Generate Idex Excel");
        int ct = db.execCall("Idex","GEN_FILE_IDEX", new ArrayList());
            finalizeObject(db, util);
        return am.findForward("mailSuccess");
        }
    }
    public void dftSrch(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res, String srchIDN) throws Exception {
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
        SearchForm srchForm ;
        SearchQuery query = new SearchQuery();
        srchForm = (SearchForm)form;
        String sz = util.nvl((String)info.getDmbsInfoLst().get("SIZE"));
        util.updAccessLog(req,res,"Search Page", "Search Page modify "+srchIDN);
        if(sz.equals(""))
            sz="SIZE";
        HashMap prp = info.getPrp();
        ArrayList ary = new ArrayList();
        String getDtls =
            " select a.mprp ,a.vfr , a.vto , a.sfr ,a.sto, b.dta_typ " + " from srch_dtl a, mprp b where a.mprp = b.prp and a.srch_id = ? ";
        ary = new ArrayList();
        ary.add(String.valueOf(srchIDN));
      ArrayList  outLst = db.execSqlLst(" Dmd Id :" + srchIDN, getDtls, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String mprp = util.nvl(rs.getString("mprp"));
            String sfr = util.nvl(rs.getString("sfr"));
            String sto = util.nvl(rs.getString("sto"));
            String vfr = util.nvl(rs.getString("vfr"));
            String vto = util.nvl(rs.getString("vto"));
          
            if (rs.getString("dta_typ").equals("C")) {
                ArrayList prpSrt = (ArrayList)prp.get(mprp + "S");
                ArrayList prpVal = (ArrayList)prp.get(mprp + "V");
                int startAt = prpVal.indexOf(vfr);
                int endAt = prpVal.indexOf(vto);

                List rngVals = prpSrt.subList(startAt, endAt);

                for (int i = 0; i < rngVals.size(); i++) {
                    String lSrt = (String)rngVals.get(i);
                    String lVal = (String)prpVal.get(prpSrt.indexOf(lSrt));
                  
                    srchForm.setValue(mprp + "_" + lVal, lVal);

                }
            }
            if (rs.getString("dta_typ").equals("D")) {
                sfr = sfr.substring(6, 8)+"-"+sfr.substring(4, 6)+"-"+sfr.substring(0, 4);
                sto = sto.substring(6, 8)+"-"+sto.substring(4, 6)+"-"+sto.substring(0, 4);
            }
            if(mprp.equals("CRTWT")){
                srchForm.setValue("wt_fr_c", sfr);
                srchForm.setValue("wt_to_c", sto);
                srchForm.setValue("MTXT_CRTWT", sfr+"-"+sto);
            }else{
            srchForm.setValue(mprp + "_1", sfr);
            srchForm.setValue(mprp + "_2", sto);
            }

        }
        rs.close();
        pst.close();
        String getSubDtls =
        " select a1.mprp, a1.sfr, a1.sto, a1.vfr, b1.srt psrt, b1.val pval "+
        " from (select a.mprp, a.sfr ,a.vfr, a.sto, decode(mprp, 'CRTWT', get_sz(sfr), a.vfr) sz, "+
        " decode(a.mprp, 'CRTWT', '"+sz+"', a.mprp) prp from srch_dtl_sub a where a.srch_id = ?) a1, prp b1 "+
        " where a1.prp = b1.mprp and a1.sz = b1.val order by a1.mprp ";

        ary = new ArrayList();
        ary.add(String.valueOf(srchIDN));

        String dspTxt = "", lPrp = "";
       outLst = db.execSqlLst(" SubSrch :" + srchIDN, getSubDtls, ary);
       pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String mprp = util.nvl(rs.getString("mprp"));
            String sfr = util.nvl(rs.getString("sfr"));
            String sto = util.nvl(rs.getString("sto"));
            String vfr = util.nvl(rs.getString("vfr"));
            String psrt = util.nvl(rs.getString("psrt"));
            String pval = util.nvl(rs.getString("pval"));


           
            if (lPrp.equals(""))
                lPrp = mprp;

            if (lPrp.equals(mprp)) {
           if (mprp.equals("CRTWT")) 
                 dspTxt += " , " + pval;
             else    
                 dspTxt += " , " + vfr;
             }else {
             srchForm.setValue("MTXT_" + lPrp, dspTxt.substring(3).replaceAll("NA", ""));
                 if (mprp.equals("CRTWT")) 
                       dspTxt = " , " + pval;
                  else
                      dspTxt = " , " + vfr;
              lPrp = mprp;
             }
           
            if (mprp.equals("CRTWT")) {
                srchForm.setValue(mprp + "_" + psrt, pval);
                srchForm.setValue(mprp + "_1_" + psrt, sfr);
                srchForm.setValue(mprp + "_2_" + psrt, sto);
                srchForm.setValue("wt_fr_c","");
                srchForm.setValue("wt_to_c", "");
                srchForm.setValue("MTXT_" + lPrp,"");
            } else
                srchForm.setValue(mprp + "_" + vfr, vfr);
        }
        rs.close();
        pst.close();
        if(dspTxt.length() > 3)
          srchForm.setValue("MTXT_" + lPrp, dspTxt.substring(3).replaceAll("NA", ""));
        
      util.updAccessLog(req,res,"Search Page", "Search Page modify done");
        finalizeObject(db, util);
        req.setAttribute("Modify", "Y");
    }
  public ActionForward loadSrch(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
          return mapping.findForward(rtnPg);   
      }else{ 
          SearchForm srchForm ;
      srchForm =(SearchForm)form;
      String srchIDN = req.getParameter("srchId");
       util.updAccessLog(req,res,"Search Page", "Search Page modify srchID "+srchIDN);
      ArrayList ary = new ArrayList();
      String getDtls = "select a.mprp , a.sfr ,a.sto from srch_dtl a where a.srch_id = ? ";
      ary = new ArrayList();
      ary.add(srchIDN);
        ArrayList  outLst = db.execSqlLst(" Dmd Id :"+ srchIDN, getDtls, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet  rs = (ResultSet)outLst.get(1);
      while(rs.next()){
        String mprp =util.nvl(rs.getString("mprp")) ;
        String sfr = util.nvl( rs.getString("sfr"));
        String sto = util.nvl(rs.getString("sto"));
        srchForm.setValue(mprp+"_1", sfr);
        srchForm.setValue(mprp+"_2", sto);
      }
      rs.close();
      pst.close();
      String getSubDtls = "select a.mprp, a.sfr ,a.sto from srch_dtl_sub a where a.srch_id = ? ";
      ary = new ArrayList();
      ary.add(srchIDN);
    
          
     outLst = db.execSqlLst(" SubSrch :"+ srchIDN, getSubDtls, ary);
      pst = (PreparedStatement)outLst.get(0);
     rs = (ResultSet)outLst.get(1);
      while(rs.next()){
        String mprp =util.nvl(rs.getString("mprp")) ;
        String sfr = util.nvl( rs.getString("sfr"));
        srchForm.setValue(mprp+"_"+sfr, sfr);
      }
      rs.close();
      pst.close();
      srchForm.setValue("oldsrchId", srchIDN);
          util.setMdl("MEMOSRCH");
          finalizeObject(db, util);
      return mapping.findForward("load");
      }
  }
  
    
    public ActionForward srch(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
            return mapping.findForward(rtnPg);   
        }else{
        SearchForm srchForm ;
        SearchQuery query = new SearchQuery();
    srchForm = (SearchForm)form;

      util.updAccessLog(req,res,"Search Page", "Search start");
        util.initUsr(srchForm.getByrRln());
    info.setSrchBaseOn("NA");
    info.setPri_chng_typ("");
    info.setPri_chng_val("");
    info.setSrchsttLst(new ArrayList());
    info.setSrchByrId(Integer.parseInt(srchForm.getByrId()));
    info.setRlnId(Integer.parseInt(srchForm.getByrRln()));
    info.setSrchPryID(Integer.parseInt(srchForm.getParty()));
    HashMap dbinfo = info.getDmbsInfoLst();
    String cnt = (String)dbinfo.get("CNT");
     String srchTyp = (String)srchForm.getValue("typ");
    ExcelUtil excelUtil = new ExcelUtil();
    excelUtil.init(db, util, info);
    String buttonPressed = "",trDis="";
    
    String goTo = "srch";
    if(cnt.equals("xljf")){
        trDis=util.nvl(util.gettradeDis(String.valueOf(info.getRlnId())));
    }
        String SLRB =util.nvl((String)srchForm.getValue("SLRB"));
            req.setAttribute("SLRB", SLRB);
    String srchRef =util.nvl((String)srchForm.getValue("srchRef"));
    String srchRefVal = util.nvl((String)srchForm.getValue("srchRefVal"));
    String srchSold = util.nvl(req.getParameter("soldsrch"));
    String soldsrchPkt = util.nvl(req.getParameter("soldsrchPkt"));
    String soldDay = "0";
    if(srchSold.equals("YES")){
       soldDay = util.nvl(req.getParameter("sold_day"));
    }
    if(soldsrchPkt.equals("YES")){
        srchSold="YES";
    }
    int lSrchId=0;
    boolean callnotseen=false;
    boolean callnotseenPm=false;
    if(srchForm.getValue("refine_srch")!=null)
    buttonPressed = "refine";
    if (srchForm.getValue("pb_multiSrch")!=null)
    buttonPressed = "addsrch";
    if(srchForm.getValue("pb_updateSrch")!=null)
    buttonPressed = "updsrch";
    if(srchForm.getValue("srch")!=null)
    buttonPressed = "srch";
    if(srchForm.getValue("srchpkt")!=null)
    buttonPressed = "srch";
    if(srchForm.getValue("srchpktfancy")!=null)
    buttonPressed = "srch";
    if(srchForm.getValue("pb_rslt")!=null)
    buttonPressed = "rslt";
    if(srchForm.getValue("pb_watch")!=null)
    buttonPressed = "wl";
    if(srchForm.getValue("pb_dmd")!=null)
    buttonPressed = "dmd";
    if(srchForm.getValue("upd_dmd")!=null)
    buttonPressed = "upddmd";
    if(srchForm.getValue("sv_dmd")!=null)
    buttonPressed = "svdmd";
    if(srchForm.getValue("crt_excel")!=null)
    buttonPressed = "crtexcel";
    if(srchForm.getValue("mail_excel")!=null)
    buttonPressed = "mailExcel";
    if(srchForm.getValue("mixsrchpkt")!=null)
    buttonPressed = "mixsrch";
    if(srchForm.getValue("mixsrch")!=null)
    buttonPressed = "mixsrch";
    if(srchForm.getValue("pairs")!=null)
     buttonPressed = "srch";
    if(srchForm.getValue("notseen")!=null){
    buttonPressed = "srch";
    callnotseen=true;
    }
    if(srchForm.getValue("callnotseenPm")!=null){
        buttonPressed = "srch";
        callnotseenPm=true;
        if(util.nvl((String)srchForm.getValue("callnotseenRadio")).equals("INCLUDE")){
            callnotseen=true;
            callnotseenPm=false;
        }    
    }
    if(srchForm.getValue("rghSrch")!=null){
                buttonPressed = "rghSrch";     
            }
    String pgDef = "SEARCH_RESULT";
    if(buttonPressed.equals("mixsrch"))
        pgDef = "MIXSRCH_RESULT";
    if(buttonPressed.equals("rghSrch"))
         pgDef = "RGHSRCH_RESULT";
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);
     if(pageDtl==null || pageDtl.size()==0){
    pageDtl=new HashMap();
    pageDtl=util.pagedef(pgDef);
    allPageDtl.put(pgDef,pageDtl);
    }
    info.setPageDetails(allPageDtl);
    String xrtFrm = util.nvl((String)srchForm.getValue("xrt"));
      util.updAccessLog(req,res,"Search Crt", "buttonPressed "+buttonPressed);
    if(!buttonPressed.equals("refine")){
    info.setRlnId(Integer.parseInt(srchForm.getByrRln()));
    info.setSrchByrId(Integer.parseInt(srchForm.getByrId()));
    info.setRlnId(Integer.parseInt(srchForm.getByrRln()));
    info.setSrchPryID(Integer.parseInt(srchForm.getParty()));
    
    String isInb = util.nvl((String)srchForm.getValue("INB"));
    String newXrt=util.nvl((String)srchForm.getValue("xrt"));
    if(isInb.equals("Y")){
        double inbXrt = Double.parseDouble(util.nvl((String)dbinfo.get("INBRTE"),"0"));
       newXrt = String.valueOf(Double.parseDouble(xrtFrm) + inbXrt);
    }
    String isSlrb = util.nvl((String)srchForm.getValue("SLRB"));
    if(isSlrb.equals("Y") && !isInb.equals("Y")){
           double slrbXrt = Double.parseDouble(util.nvl((String)dbinfo.get("SLRBRTE"),"0"));
           newXrt = String.valueOf(Double.parseDouble(xrtFrm) + slrbXrt);
    }
    srchForm.setValue("xrt", newXrt);
    if(!xrtFrm.equals(""))
      info.setXrt(Double.parseDouble(newXrt));
    }
      srchForm.setValue("CUR", info.getRln());
    String memoTyp = util.nvl((String)srchForm.getValue("typ"));
    int ct = db.execUpd("delete gtSrch", "DELETE FROM gt_srch_rslt ", new ArrayList());
        ct = db.execUpd("delete gtSrch", "DELETE FROM gt_srch_multi ", new ArrayList());
        ct = db.execUpd("delete gtSrch", "delete from gt_pkt_scan ", new ArrayList());
    FormFile uploadFile = srchForm.getFileUpload();
        HashMap fileDataMap = new HashMap();
    if(uploadFile!=null){
    String fileName = uploadFile.getFileName();
    fileName = fileName.replaceAll(".csv", util.getToDteTime()+".csv");
    if(!fileName.equals("")){
    String fileTyp = uploadFile.getContentType();
    String path = getServlet().getServletContext().getRealPath("/") + fileName;
    File readFile = new File(path);
    if(!readFile.exists()){

    FileOutputStream fileOutStream = new FileOutputStream(readFile);

    fileOutStream.write(uploadFile.getFileData());

    fileOutStream.flush();

    fileOutStream.close();

    }

    FileReader fileReader = new FileReader(readFile);
    LineNumberReader lnr = new LineNumberReader(fileReader);
    String line = "";
    String stoneId = "";
    String reportNo = "";
        ArrayList fileFields = new ArrayList();
        fileFields.add("vnm");
        fileFields.add("prc");
        fileFields.add("dis");
        fileFields.add("lmt");
        srchRef = "vnm";
       
        while ((line = lnr.readLine()) != null){
            String vnm = "";
            String[] vals = line.split(",");
            HashMap fileData = new HashMap();
            int lmt = 4 ;
            if (vals.length < 4)
                lmt = vals.length;
            for(int i=0; i < lmt; i++) {
                fileData.put((String)fileFields.get(i), vals[i]) ;   
                if(i==0) {
                    vnm = vals[i];
                    srchRefVal = srchRefVal+","+vnm;
                }   
            }
            if(fileData.size() < 3) {
                for(int i=fileData.size() - 1; i < 3; i++) {
                    fileData.put((String)fileFields.get(i),"");
                }
            }
            fileDataMap.put(vnm, fileData);
            /* 
             * Code by SK (Live as on 08Mar 
             * Modified with the above code by PC for accepting price as well
            StringTokenizer vals = new StringTokenizer(line, ",");
            ArrayList dataLst = new ArrayList();
            while(vals.hasMoreTokens()) {
                dataLst.add(vals.nextToken());
            }
            srchRef = "vnm";
            for(int j=0;j<dataLst.size();j++ ){
                srchRefVal = srchRefVal+","+dataLst.get(j);
            }
            */
        }
        fileReader.close();
        }
        }
      String lstNme = "SRCHRST_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");

      gtMgr.setValue(lstNme+"_SEL",new ArrayList());

        ArrayList vwPrpLst = info.getVwPrpLst();
        // Activity Starts
        if(util.nvl(info.getIsMix(),"NR").equals("SMX"))
            vwPrpLst = info.getSmxPrpLst();
        req.setAttribute("file", fileDataMap);
        if(buttonPressed.equalsIgnoreCase("wl")) {
          query.popWL(req,srchForm.getByrRln(),(String)srchForm.getValue("grpNme"));
          HashMap searchList = query.SearchResultGT(req, res , "'Z'" , vwPrpLst);
            HashMap dtlMap = new HashMap();

            ArrayList selectstkIdnLst =new ArrayList();
            Set<String> keys = searchList.keySet();
                for(String key: keys){
               selectstkIdnLst.add(key);
                }
            dtlMap.put("selIdnLst",selectstkIdnLst);
            dtlMap.put("pktDtl", searchList);
            dtlMap.put("flg", "Z");
            HashMap totals = util.getTTL(dtlMap);
        
          info.setMultiSrchLst(new ArrayList());
          info.setSvdmdDis(new ArrayList());
          HashMap refineDtl = query.refineObj(req, searchList, info.getRefPrpLst());
          HashMap srchDtl = util.useDifferentMap(refineDtl);
          gtMgr.setValue(lstNme,searchList);
          gtMgr.setValue(lstNme+"_TTL",totals );
          gtMgr.setValue("lstNme", lstNme);
          gtMgr.setValue(lstNme+"_SEL",new ArrayList());
          gtMgr.setValue(lstNme+"_SRCHLST", srchDtl);
          gtMgr.setValue(lstNme+"_REF", refineDtl);
          util.updAccessLog(req,res,"Search Result", "Watch List");
        } else {
        if((!srchRef.equals("") && !srchRef.equals("none"))){
        query.srchRef(srchRef, srchRefVal , fileDataMap, srchForm,req,res,srchSold);
        util.updAccessLog(req,res,"Search Result", "Search By Packet");
    req.setAttribute("srchRefVal", util.getVnm(srchRefVal));
    req.setAttribute("srchRef", srchRef);
    } else {

    if(buttonPressed.equalsIgnoreCase("updsrch")) {
    String oldSrchId = (String)srchForm.getValue("oldsrchId");
    query.delSearch(req,oldSrchId);
    }
    
    if(!buttonPressed.equalsIgnoreCase("rslt") && !buttonPressed.equals("svdmd") ){
    lSrchId = query.saveSearch(req, srchForm, buttonPressed, soldDay);
      util.updAccessLog(req,res,"Search Crt", "Srch Id "+lSrchId);
    if(buttonPressed.equalsIgnoreCase("refine"))
    info.setRefSrchId(lSrchId);
    else
    info.setSrchId(lSrchId);

    }

    if((buttonPressed.equalsIgnoreCase("updsrch")) || (buttonPressed.equalsIgnoreCase("addsrch")) ||( buttonPressed.equalsIgnoreCase("svdmd"))||(buttonPressed.equalsIgnoreCase("dmd"))|| (buttonPressed.equalsIgnoreCase("upddmd"))) {
    if(buttonPressed.equalsIgnoreCase("addsrch")){

    HashMap multiSrchMap = info.getMultiSrchDsc();
    String dsc = util.nvl(query.srchDscription(req, Integer.toString(lSrchId)));
    if(!dsc.equals("")){
    multiSrchMap.put(Integer.toString(lSrchId), dsc );
    info.setMultiSrchDsc(multiSrchMap);
    }
    srchForm.reset();
    }
    if((buttonPressed.equalsIgnoreCase("dmd"))||( buttonPressed.equalsIgnoreCase("upddmd")) ||( buttonPressed.equalsIgnoreCase("svdmd")) ){
    ArrayList ary = new ArrayList();
    String dsc = util.nvl((String)srchForm.getValue("dmdDsc")).trim();
    String oldDmdId = util.nvl((String)srchForm.getValue("oldDmdId"));
    ary.add(String.valueOf(info.getByrId()));
    String checksdsc = "select * from mdmd where upper(dsc) = upper('"+dsc+"') and name_id = ? and todte is null  and mdl='DMD'  ";
    if(buttonPressed.equalsIgnoreCase("upddmd")) {
         checksdsc = "select * from mdmd where upper(dsc) = upper('"+dsc+"') and name_id = ? and todte is null  and mdl='DMD' and dmd_id != ? ";
         ary.add(oldDmdId);
     }
      ArrayList  outLst = db.execSqlLst("mdmd", checksdsc, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);

    if(rs.next()){
        req.setAttribute("dmdMsg", "Demand Name already exist Please changes and try again.");
        ArrayList byrLst= query.getByrList(req,res);
        srchForm.setByrList(byrLst);
        srchForm.setByrId(srchForm.getByrId());
    //        ArrayList partyLst = query.getPartyList(req,res);
    //        srchForm.setPartyList(partyLst);
        srchForm.setParty(srchForm.getParty());
        srchForm.setValue("partytext", info.getByrNm());
        ArrayList trmList = query.getTerm(req,res, info.getSrchPryID());
        srchForm.setTrmList(trmList);
        ArrayList dmdList = query.getDmdList(req,res, info.getRlnId());
        srchForm.setDmdList(dmdList);
        req.setAttribute("dmdList", dmdList);
        ArrayList webReqList = query.getWebRequest(req, res, info.getRlnId());
        req.setAttribute("webReqList", webReqList);
        ArrayList hrReqList = query.getHrRequest(req, res, info.getDf_rln_id());
        req.setAttribute("hrReqList", hrReqList);
      ArrayList grpNmeList = query.getGrpList(req, res, info.getDf_rln_id());
       req.setAttribute("grpNmeList", grpNmeList);
      ArrayList contactList = query.getContactList(req, res, info.getDf_nme_id());
      req.setAttribute("contDtlList", contactList);
        srchForm.setByrRln(srchForm.getByrRln());
         srchForm.setValue("xrt", xrtFrm);
        info.setMultiSrchLst(new ArrayList());
        info.setSvdmdDis(new ArrayList());
        ArrayList memoList= (query.getSrchType(req,res,srchForm,"Y") == null)?new ArrayList():(ArrayList)query.getSrchType(req,res,srchForm,"Y");
        srchForm.setValue("typ",util.nvl(util.nvl((String)info.getDmbsInfoLst().get(util.nvl((String)info.getIsMix(),"NR")+"_MEMO_TYP")),"Z"));
        util.setMdl("MEMOSRCH");
        finalizeObject(db, util);
        return mapping.findForward("load");
    }
        rs.close();
        pst.close();
    if( buttonPressed.equalsIgnoreCase("upddmd"))
    {
    if(!oldDmdId.equals("")) {
    ArrayList rmAry = new ArrayList();
    rmAry.add(oldDmdId);
    int updCt = db.execCall(" Rmv Dmd ", " dmd_pkg.rmv(?) ", rmAry);
    }
    }
        if(( buttonPressed.equalsIgnoreCase("svdmd"))){
            lSrchId=Integer.parseInt(req.getParameter("svsrchId"));
        }
    int dmdID = query.addDmd(req,res, lSrchId, dsc);
    if(( buttonPressed.equalsIgnoreCase("svdmd"))){
        ArrayList byrLst= query.getByrList(req,res);
        srchForm.setByrList(byrLst);
        srchForm.setByrId(srchForm.getByrId());
    //        ArrayList partyLst = query.getPartyList(req,res);
    //        srchForm.setPartyList(partyLst);
        srchForm.setParty(srchForm.getParty());
        srchForm.setValue("partytext", info.getByrNm());
        ArrayList trmList = query.getTerm(req,res, info.getSrchPryID());
        srchForm.setTrmList(trmList);
        ArrayList dmdList = query.getDmdList(req,res, info.getRlnId());
        srchForm.setDmdList(dmdList);
        req.setAttribute("dmdList", dmdList);
        ArrayList webReqList = query.getWebRequest(req, res, info.getRlnId());
        req.setAttribute("webReqList", webReqList);
        ArrayList hrReqList = query.getHrRequest(req, res, info.getDf_rln_id());
        req.setAttribute("hrReqList", hrReqList);
      ArrayList grpNmeList = query.getGrpList(req, res, info.getDf_rln_id());
       req.setAttribute("grpNmeList", grpNmeList);
      ArrayList contactList = query.getContactList(req, res, info.getDf_nme_id());
      req.setAttribute("contDtlList", contactList);
        srchForm.setByrRln(srchForm.getByrRln());
         srchForm.setValue("xrt", xrtFrm);
    //        info.setMultiSrchLst(new ArrayList());
         ArrayList dmdDis=info.getSvdmdDis();
         String Srchd=String.valueOf(lSrchId);
        dmdDis.add(Srchd);
        info.setSvdmdDis(dmdDis);
        ArrayList memoList= (query.getSrchType(req,res,srchForm,"Y") == null)?new ArrayList():(ArrayList)query.getSrchType(req,res,srchForm,"Y");
        srchForm.setValue("typ",util.nvl(util.nvl((String)info.getDmbsInfoLst().get(util.nvl((String)info.getIsMix(),"NR")+"_MEMO_TYP")),"Z"));
        util.setMdl("MEMOSRCH");
        return mapping.findForward("load");
    }
    req.setAttribute("memoMsg", "Demand save successfully..");
    
    }
    if((buttonPressed.equalsIgnoreCase("updsrch")) || (buttonPressed.equalsIgnoreCase("addsrch"))){
    ArrayList byrLst= query.getByrList(req,res);
    srchForm.setByrList(byrLst);
    srchForm.setByrId(srchForm.getByrId());
    //    ArrayList partyLst = query.getPartyList(req,res);
    //    srchForm.setPartyList(partyLst);
    srchForm.setParty(srchForm.getParty());
    srchForm.setValue("partytext", info.getByrNm());
    ArrayList trmList = query.getTerm(req, res , info.getSrchPryID());
    srchForm.setTrmList(trmList);
    ArrayList dmdList = query.getDmdList(req, res, info.getRlnId());
    srchForm.setDmdList(dmdList);
    ArrayList webReqList = query.getWebRequest(req, res, info.getRlnId());
    req.setAttribute("webReqList", webReqList);
        ArrayList hrReqList = query.getHrRequest(req, res, info.getDf_rln_id());
        req.setAttribute("hrReqList", hrReqList);
      ArrayList grpNmeList = query.getGrpList(req, res, info.getDf_rln_id());
       req.setAttribute("grpNmeList", grpNmeList);
      ArrayList contactList = query.getContactList(req, res, info.getDf_nme_id());
      req.setAttribute("contDtlList", contactList);
        ArrayList memoList= (query.getSrchType(req,res,srchForm,"Y") == null)?new ArrayList():(ArrayList)query.getSrchType(req,res,srchForm,"Y");
    req.setAttribute("dmdList", dmdList);
    srchForm.setByrRln(srchForm.getByrRln());
    srchForm.setValue("xrt", xrtFrm);
    util.setMdl("MEMOSRCH");
    goTo = "load";
    }
    }

    if(buttonPressed.equalsIgnoreCase("rslt") && lSrchId!=0) {
    ArrayList srchIds = info.getMultiSrchLst();
    ArrayList selectedSrchIds = new ArrayList();
    for(int i=0; i < srchIds.size(); i++) {
    String lId = (String)srchIds.get(i);
    String reqVal = util.nvl(req.getParameter("ml_ch_"+lId));
    if(reqVal.length() > 0)
    selectedSrchIds.add(lId);
    }
    info.setMultiSrchLst(selectedSrchIds);
    }
    }
    if((buttonPressed.equalsIgnoreCase("srch"))|| (buttonPressed.equalsIgnoreCase("rghSrch")) || (buttonPressed.equalsIgnoreCase("rslt")) || buttonPressed.equalsIgnoreCase("refine")|| buttonPressed.equalsIgnoreCase("dmd")||buttonPressed.equalsIgnoreCase("upddmd")||buttonPressed.equalsIgnoreCase("mixsrch")) {
    if(srchRef.equals("") || (srchRef.equals("none"))){
    Boolean searchStock = query.searchStock(req,res, "Z" , buttonPressed,soldDay);
    }
    if(callnotseen){
        ArrayList ntSeen = new ArrayList();
        ntSeen.add(String.valueOf(info.getByrId()));
        int updCt = db.execCall(" DP_FIND_NOT_SEEN ", "DP_FIND_NOT_SEEN(pByrId => ?)", ntSeen);
    }
    if(callnotseenPm){
        String srchRefFindNotSeen =util.nvl((String)srchForm.getValue("srchRefFindNotSeen"));
        String srchRefValFindNotSeen = util.nvl((String)srchForm.getValue("srchRefValFindNotSeen"));
        query.srchRefFindNotSeen(srchRefFindNotSeen, srchRefValFindNotSeen,srchForm,req,res);
    }
    if(buttonPressed.equals("mixsrch")){
        vwPrpLst = info.getMixPrpLst();
        req.setAttribute("view", "MIX");
    }
        if(buttonPressed.equals("rghSrch")){
            vwPrpLst = info.getRghVwList();
            req.setAttribute("view", "MIX");
        }
    HashMap searchList=new HashMap();
    String flg="Z";
    if(fileDataMap!=null && fileDataMap.size()>0){
    String updGt = " update gt_srch_rslt set flg = 'M' where flg = 'Z' ";
    db.execUpd("upd gt R", updGt, new ArrayList());
    searchList = query.SearchResultGT(req, res , "'M'" , vwPrpLst);
        lstNme = "BID"+lstNme;
    goTo="bid";
        flg="B";
    }else{
        if(!soldDay.equals("0"))
            vwPrpLst=info.getSoldPrpLst();
        if((!srchRef.equals("") && !srchRef.equals("none")) && srchSold.equals("YES")){
            vwPrpLst=info.getSoldPrpLst();
        }
    searchList = query.SearchResultGT(req, res , "'Z'" , vwPrpLst);
    }
        ArrayList selectstkIdnLst =new ArrayList();
        Set<String> keys = searchList.keySet();
            for(String key: keys){
           selectstkIdnLst.add(key);
            }
        ArrayList SOLDSTONELIST = (ArrayList)req.getAttribute("SOLDSTONELIST");
        if(SOLDSTONELIST!=null && SOLDSTONELIST.size()>0){
            for(int i=0;i<SOLDSTONELIST.size();i++){
                selectstkIdnLst.remove(SOLDSTONELIST.get(i));
            }
           HashMap dtlMap = new HashMap();
            dtlMap.put("selIdnLst",SOLDSTONELIST);
            dtlMap.put("pktDtl", searchList);
            dtlMap.put("flg", "S");
            HashMap soldtotals = util.getTTL(dtlMap);
          req.setAttribute("SOLDTTL", soldtotals);
        }
        HashMap dtlMap = new HashMap();
        dtlMap.put("selIdnLst",selectstkIdnLst);
        dtlMap.put("pktDtl", searchList);
        dtlMap.put("flg", flg);
        HashMap totals = util.getTTL(dtlMap);
        
        
        
        
      HashMap refineDtl = query.refineObj(req, searchList, info.getRefPrpLst());
      HashMap srchDtl = util.useDifferentMap(refineDtl);
    info.setMultiSrchLst(new ArrayList());
    info.setSvdmdDis(new ArrayList());
    util.updAccessLog(req,res,"Search Crt", "Srch pkt List "+searchList.size());
      gtMgr.setValue(lstNme,searchList);
      gtMgr.setValue(lstNme+"_TTL",totals );
      gtMgr.setValue("lstNme", lstNme);
      gtMgr.setValue(lstNme+"_SRCHLST", srchDtl);
      gtMgr.setValue(lstNme+"_REF", refineDtl);
      req.setAttribute("lstNme", lstNme);
    if(srchRef.equals("") || (srchRef.equals("none"))){
    
    String desc= util.nvl(util.srchDscription(String.valueOf(lSrchId)));
    req.setAttribute("searchCrt",desc);
    
     
    }
     

    if(!srchRef.equals("") && (!srchRef.equals("none"))){
    } else
    loadSrchPrp(mapping, form, req, res);

    }
        
            
            if((buttonPressed.equalsIgnoreCase("crtexcel")) || (buttonPressed.equalsIgnoreCase("mailExcel")) )
            {
                if(srchRef.equals("") || (srchRef.equals("none"))){
                Boolean searchStock = query.searchStock(req,res, "Z" , buttonPressed,soldDay);
                }
                String fileNm = "SearchResultExcelSrch_"+cnt+".xls";
                HSSFWorkbook hwb = new HSSFWorkbook();
                String CONTENT_TYPE = "getServletContext()/vnd-excel";
                if(buttonPressed.equalsIgnoreCase("crtexcel") || (buttonPressed.equalsIgnoreCase("mailExcel"))){
                    req.setAttribute("BYRNME", info.getByrNm());
               hwb = excelUtil.getDataAllInXl("SRCH", req, info.getVwMdl());
               }
            if (buttonPressed.equalsIgnoreCase("mailExcel")) {
            
            // query.MailExcel(hwb, fileNm, req , info.getByrId());
            srchForm.reset();
            ArrayList byrLst= query.getByrList(req,res);
            srchForm.setByrList(byrLst);
            srchForm.setByrId(srchForm.getByrId());
    //            ArrayList partyLst = query.getPartyList(req,res);
    //            srchForm.setPartyList(partyLst);
            srchForm.setParty(srchForm.getParty());
            srchForm.setValue("partytext", info.getByrNm());
            ArrayList trmList = query.getTerm(req,res, info.getSrchPryID());
            srchForm.setTrmList(trmList);
             ArrayList dmdList = query.getDmdList(req,res,info.getRlnId());
            srchForm.setDmdList(dmdList);
            req.setAttribute("dmdList", dmdList);
                ArrayList webReqList = query.getWebRequest(req, res, info.getRlnId());
                req.setAttribute("webReqList", webReqList);
                ArrayList hrReqList = query.getHrRequest(req, res, info.getDf_rln_id());
                req.setAttribute("hrReqList", hrReqList);
              ArrayList grpNmeList = query.getGrpList(req, res, info.getDf_rln_id());
               req.setAttribute("grpNmeList", grpNmeList);
              ArrayList contactList = query.getContactList(req, res, info.getDf_nme_id());
              req.setAttribute("contDtlList", contactList);
                ArrayList memoList= (query.getSrchType(req,res,srchForm,"Y") == null)?new ArrayList():(ArrayList)query.getSrchType(req,res,srchForm,"Y");
            srchForm.setByrRln(srchForm.getByrRln());
             srchForm.setValue("xrt", info.getXrt());
            info.setMultiSrchLst(new ArrayList());
            info.setSvdmdDis(new ArrayList());
            req.setAttribute("mail","Y");
                query.MailExcelmass(hwb, fileNm, req,res);
                ArrayList ByrEmailIds= util.getByerEmailid();
                req.setAttribute("ByrEmailIds", ByrEmailIds);
                req.setAttribute("load","search");
                HashMap mailDtl = util.getMailFMT("DFLT_TXT_MAIL");
                String dfltmailtxt=util.nvl((String)mailDtl.get("MAILBODY"));
                req.setAttribute("dfltmailtxt", dfltmailtxt);
                goTo = "mail";
              util.updAccessLog(req,res,"Search page", "Mail Excel");
             
                return mapping.findForward(goTo);
            }
            else
            {
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);

            OutputStream out = res.getOutputStream();
            hwb.write(out);
            out.flush();
            out.close();
            util.updAccessLog(req,res,"Search page", "create Excel");
                finalizeObject(db, util);
            return loadSrchPrp(mapping, form, req, res);
            }


            }
        

    }
    //if(goTo.equals("srch"))
    //    HashMap memoTypMap = (HashMap)session.getAttribute("memoTypMap");
    //    if(memoTypMap==null && memoTypMap.size()>0){
    //        query.getSrchType(req, res);
    //    }
    //    String srchMemoTyp = (String)memoTypMap.get(srchTyp);
    //        if(buttonPressed.equals("mixsrch") && cnt.equals("kj"))
    //        srchMemoTyp="E";
    ArrayList memoList= (query.getMemoType(req,res) == null)?new ArrayList():(ArrayList)query.getMemoType(req,res);
    //    srchForm.setValue("memoList", memoList);
    srchForm.setMemoList(memoList);
    session.setAttribute("SrchMemoTyp", util.nvl(util.nvl((String)info.getDmbsInfoLst().get(util.nvl((String)info.getIsMix(),"NR")+"_MEMO_TYP")),"Z"));
    HashMap bidMap = query.bidPkt(req, res , 0);
    req.setAttribute("bidMap", bidMap);
    String memTyp=util.nvl(util.nvl((String)info.getDmbsInfoLst().get(util.nvl((String)info.getIsMix(),"NR")+"_MEMO_TYP")),"Z");
    srchForm.setValue("typ",util.nvl(util.nvl((String)info.getDmbsInfoLst().get(util.nvl((String)info.getIsMix(),"NR")+"_MEMO_TYP")),"Z"));
    req.setAttribute("memoTyp",util.nvl(util.nvl((String)info.getDmbsInfoLst().get(util.nvl((String)info.getIsMix(),"NR")+"_MEMO_TYP")),"Z"));
    ArrayList expDaysList = query.getExpDay(req, res);
    if(expDaysList !=null && expDaysList.size()>0){
    //    srchForm.setExpDayList(expDaysList);
    srchForm.setValue("ExpDayList", expDaysList);
    }
     ArrayList byrCbList = query.getBuyerCabin(req, res);
     if(byrCbList !=null && byrCbList.size()>0){
    //     srchForm.setByrCbList(byrCbList);
         srchForm.setValue("ByrCbList", byrCbList);
     }
    //     util.imageDtls();
        String mTyp=util.nvl(util.nvl((String)info.getDmbsInfoLst().get(util.nvl((String)info.getIsMix(),"NR")+"_MEMO_TYP")),"Z");

    //     util.StockViewLyt();
     ArrayList chargesLst=util.chargesLst("SRCH_CHARGES");
     session.setAttribute("chargesLst", chargesLst);
     String ttlMemoVal = query.TotalMemoVal(req,info.getByrId(),info.getRlnId(),cnt,"ALL");
      req.setAttribute("ttlMemoVal", ttlMemoVal);
     ArrayList sttAlwLst = query.MemoSttAlw(req, res, mTyp);
     req.setAttribute("sttAlwLst", sttAlwLst);
     srchForm.setValue("isDLV","Yes");
     HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(info.getSrchPryID()));
     srchForm.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
     srchForm.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
     srchForm.setValue("otherref",util.nvl((String)buyerDtlMap.get("OTHERREF")));
     srchForm.setValue("gstin",util.nvl((String)buyerDtlMap.get("GSTIN")));
     srchForm.setValue("trDis",trDis);
        ArrayList  pageList= ((ArrayList)pageDtl.get("LOYALTY") == null)?new ArrayList():(ArrayList)pageDtl.get("LOYALTY");
      if(pageList!=null && pageList.size() >0){
         srchForm.setValue("LOYALTY", "YES");
         ArrayList loyalty=util.getloyalty(info.getSrchPryID());
         srchForm.setValue("loyCtg", util.nvl((String)loyalty.get(0)));
         srchForm.setValue("loyPct", util.nvl((String)loyalty.get(1)));
         srchForm.setValue("loyDiff", util.nvl((String)loyalty.get(2)));
         srchForm.setValue("loyVlu", util.nvl((String)loyalty.get(3)));
         srchForm.setValue("mem_dis", util.nvl((String)loyalty.get(4)));
          srchForm.setValue("loyallow", util.nvl((String)loyalty.get(5)));
          srchForm.setValue("memallow", util.nvl((String)loyalty.get(6)));
          srchForm.setValue("ttlSalVlu", util.nvl((String)loyalty.get(7)));
          srchForm.setValue("osamount", util.nvl((String)loyalty.get(8)));
          query.iememov(req,info.getSrchPryID());
      }
        pageList= ((ArrayList)pageDtl.get("MEM_DIS") == null)?new ArrayList():(ArrayList)pageDtl.get("MEM_DIS");
        if(pageList!=null && pageList.size() >0){
            
        }
            req.setAttribute("SOLDSRCH", srchSold);
        finalizeObject(db, util);
    return mapping.findForward(goTo);
    }
    }
    
    public ActionForward showSelected(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
            return mapping.findForward(rtnPg);   
        }else{
            SearchForm srchForm ;
            SearchQuery query = new SearchQuery();
    srchForm = (SearchForm)form;
    util.updAccessLog(req,res,"Search Page", "Show Selected");
    ArrayList memoList= (query.getMemoType(req,res) == null)?new ArrayList():(ArrayList)query.getMemoType(req,res);
    srchForm.setMemoList(memoList);
    HashMap searchList = query.SearchResult(req,res, "'M'" , info.getVwPrpLst());
    HashMap totals = util.getTtls(req);
    req.setAttribute("searchList", searchList);
    req.setAttribute("total",totals);
    
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("SRCH_RSLT_SELECT");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("SRCH_RSLT_SELECT");
        allPageDtl.put("SRCH_RSLT_SELECT",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            finalizeObject(db, util);
    return mapping.findForward("showSelected");
        }
    }
    
    public ActionForward MPTOSRCH(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
            return mapping.findForward(rtnPg);  
        }else{
        SearchForm srchForm ;
        SearchQuery query = new SearchQuery();
    srchForm = (SearchForm)form;
    String deleteGt = "delete from gt_srch_rslt where stk_idn in (select stk_idn from gt_srch_rslt where flg='MT') and flg!='MT'";
    int ct = db.execUpd("gtUpdate", deleteGt, new ArrayList());
    db.execUpd("updateGt", "update gt_srch_rslt set flg='M'  where flg='MT' ", new ArrayList());
    ArrayList memoList= (query.getMemoType(req,res) == null)?new ArrayList():(ArrayList)query.getMemoType(req,res);
//    srchForm.setValue("memoList", memoList);
    srchForm.setMemoList(memoList);
    req.setAttribute("flgORDER", "Y");
    HashMap searchList = query.SearchResult(req,res, "'Z','M'" , info.getVwPrpLst());
    String memoTyp = util.nvl((String)session.getAttribute("SrchMemoTyp"));
    srchForm.setValue("typ", memoTyp);
    HashMap totals = util.getTtls(req);
    req.setAttribute("searchList", searchList);
    req.setAttribute("total",totals);
    return mapping.findForward("showSelected");
    }
    }
  public ActionForward showCompare(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
          return mapping.findForward(rtnPg);   
      }else{
          SearchForm srchForm ;
          SearchQuery query = new SearchQuery();
  util.updAccessLog(req,res,"Search Page", "Compare");
  ArrayList viewPrp=new ArrayList();
  HashMap stockPrp = new HashMap();
  String lstNme = util.nvl((String)gtMgr.getValue("lstNme"));
  HashMap pktDtls = (HashMap)gtMgr.getValue(lstNme);
  ArrayList idnList = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
  if(idnList!=null && idnList.size()>0){
  for(int i=0;i<idnList.size();i++){
  String stkIdn = (String)idnList.get(i);
  GtPktDtl pktDtl = (GtPktDtl)pktDtls.get(stkIdn);
  String stkidn=util.nvl(pktDtl.getValue("stk_idn"));
  stockPrp.put(stkidn+"_VNM", util.nvl(pktDtl.getValue("vnm")));
  stockPrp.put(stkidn+"_diamondImg",util.nvl(pktDtl.getValue("diamondImg"),"N"));
  stockPrp.put("jewImg",util.nvl(pktDtl.getValue("jewImg"),"N"));
  stockPrp.put(stkidn+"_srayImg",util.nvl(pktDtl.getValue("srayImg"),"N"));
  stockPrp.put(stkidn+"_agsImg",util.nvl(pktDtl.getValue("agsImg"),"N"));
  stockPrp.put(stkidn+"_mrayImg",util.nvl(pktDtl.getValue("mrayImg"),"N"));
  stockPrp.put(stkidn+"_ringImg",util.nvl(pktDtl.getValue("ringImg"),"N"));
  stockPrp.put(stkidn+"_lightImg",util.nvl(pktDtl.getValue("lightImg"),"N"));
  stockPrp.put(stkidn+"_refImg",util.nvl(pktDtl.getValue("refImg"),"N"));
  stockPrp.put(stkidn+"_videos",util.nvl(pktDtl.getValue("videos"),"N"));
  stockPrp.put(stkidn+"_certImg",util.nvl(pktDtl.getValue("certImg"),"N"));
  stockPrp.put(stkidn+"_RTE",util.nvl(pktDtl.getValue("quot")));
  stockPrp.put(stkidn+"_RAP_RTE",util.nvl(pktDtl.getValue("rap_rte")));
  stockPrp.put(stkidn+"_RAP_DIS",util.nvl(pktDtl.getValue("r_dis")));
  }
  }
  if(idnList.size()>4 || idnList.size()==0 ){
  if(idnList.size()==0)
  req.setAttribute("msg", "Please Select The Stone ");
  else
  req.setAttribute("msg", "Not Possible To Compare More Than 4 Stone . ");
  }else{
  if(idnList.size() > 0) {
  String idnPcs = idnList.toString();
  idnPcs = idnPcs.replace('[','(');
  idnPcs = idnPcs.replace(']',')');
  String sql="select mstk_idn, lab,b.dta_typ, mprp, a.srt, grp , decode(b.dta_typ, 'C', a.val,'N', to_char(a.num), 'D', to_char(dte,'dd-mm-rrrr'), nvl(txt,'')) val\n" +
  "from stk_dtl a, mprp b , rep_prp c\n" +
  "where a.mprp = b.prp and mstk_idn in "+idnPcs+
  "and b.dta_typ in ('C','N','T','D') and c.prp = b.prp and c.flg = 'Y' and mdl='PKT_PRP_VW' and grp=1 \n" +
  "order by grp, psrt, mprp,lab";
  System.out.println(sql);
    ArrayList  outLst = db.execSqlLst("Details",sql,new ArrayList());
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet  rs = (ResultSet)outLst.get(1);

  try {
  while(rs.next()){
  String mprp =util.nvl(rs.getString("mprp"));
  String val = util.nvl(rs.getString("val"));
  String msktIdn = util.nvl(rs.getString("mstk_idn"));
  if(!mprp.equals("RTE") && !mprp.equals("RAP_RTE") && !mprp.equals("RAP_DIS"))
  stockPrp.put(msktIdn+"_"+mprp, val);
  }
      rs.close();
      pst.close();
  } catch (SQLException sqle) {
  // TODO: Add catch code
  sqle.printStackTrace();
  }
  }


    ArrayList  outLst = db.execSqlLst(" Vw Lst ", "Select prp from rep_prp where mdl = 'PKT_PRP_VW' and flg='Y' order by rnk ",
  new ArrayList());
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet  rs1 = (ResultSet)outLst.get(1);
  while (rs1.next()) {
  viewPrp.add(rs1.getString("prp"));
  }
  rs1.close();
      pst.close();
  req.setAttribute("PRPPKT_PRP_VW", viewPrp);
  req.setAttribute("stockList",stockPrp);
  req.setAttribute("idnList", idnList);
  }
          finalizeObject(db, util);
  return mapping.findForward("compare");
      }
  }


//    public ActionForward showSearchResult(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
//        HttpSession session = req.getSession(false);
//        InfoMgr info = (InfoMgr)session.getAttribute("info");
//        DBUtil util = new DBUtil();
//        DBMgr db = new DBMgr();
//        String rtnPg="sucess";
//        if(info!=null){
//        Connection conn=info.getCon();
//        if(conn!=null){
//        db.setCon(info.getCon());
//        util.setDb(db);
//        util.setInfo(info);
//        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//        util.setLogApplNm(info.getLoApplNm());
//        rtnPg=init(req,res,session,util);
//        }else{
//        rtnPg="connExists";   
//        }
//        }else
//        rtnPg="sessionTO";
//        if(!rtnPg.equals("sucess")){
//            return mapping.findForward(rtnPg);  
//        }else{
//            SearchForm srchForm ;
//            SearchQuery query = new SearchQuery();
//    util.updAccessLog(req,res,"Search Page", "Show Search");
//    srchForm = (SearchForm)form;
//            HashMap dbinfo = info.getDmbsInfoLst();
//            String cnt = (String)dbinfo.get("CNT");
//            String trDis="",loyalty="";
//            if(cnt.equals("xljf")){
//            trDis=util.nvl(util.gettradeDis(String.valueOf(info.getRlnId())));
//            }
//            if(cnt.equals("kj")){
//               
//              ArrayList loyaltyLst=util.getloyalty(info.getSrchPryID());
//              srchForm.setValue("loyCtg", util.nvl((String)loyaltyLst.get(0)));
//              srchForm.setValue("loyPct", util.nvl((String)loyaltyLst.get(1)));
//              srchForm.setValue("loyDiff", util.nvl((String)loyaltyLst.get(2)));
//              srchForm.setValue("loyVlu", util.nvl((String)loyaltyLst.get(3)));
//                srchForm.setValue("mem_dis", util.nvl((String)loyaltyLst.get(4)));
//                srchForm.setValue("loyallow", util.nvl((String)loyaltyLst.get(5)));
//                srchForm.setValue("memallow", util.nvl((String)loyaltyLst.get(6)));
//                srchForm.setValue("ttlSalVlu", util.nvl((String)loyaltyLst.get(7)));
//                srchForm.setValue("osamount", util.nvl((String)loyaltyLst.get(8)));
//            }
//    ArrayList memoList= (query.getMemoType(req,res) == null)?new ArrayList():(ArrayList)query.getMemoType(req,res);
////    srchForm.setValue("memoList", memoList);
//    srchForm.setMemoList(memoList);
//    ResultSet rs=null;
//    String sql="update gt_srch_rslt set flg='Z' where flg='M'";
//    int ct = db.execUpd("Update",sql,new ArrayList());
//    HashMap searchList = query.SearchResult(req,res, "'Z'" , info.getVwPrpLst());
//    HashMap totals = util.getTtls(req);
//    req.setAttribute("searchList", searchList);
//    req.setAttribute("total",totals);
//    HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(info.getSrchPryID()));
//    srchForm.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
//    srchForm.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
//    srchForm.setValue("otherref",util.nvl((String)buyerDtlMap.get("OTHERREF")));
//    srchForm.setValue("trDis",trDis);
//    srchForm.setValue("loyalty",loyalty);
//    return mapping.findForward("srch");
//        }
//    }


//    public ActionForward refBack(ActionMapping mapping,
//    ActionForm form,
//    HttpServletRequest req,
//    HttpServletResponse res) throws Exception {
//        HttpSession session = req.getSession(false);
//        InfoMgr info = (InfoMgr)session.getAttribute("info");
//        DBUtil util = new DBUtil();
//        DBMgr db = new DBMgr();
//        String rtnPg="sucess";
//        if(info!=null){
//        Connection conn=info.getCon();
//        if(conn!=null){
//        db.setCon(info.getCon());
//        util.setDb(db);
//        util.setInfo(info);
//        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//        util.setLogApplNm(info.getLoApplNm());
//        rtnPg=init(req,res,session,util);
//        }else{
//        rtnPg="connExists";   
//        }
//        }else
//        rtnPg="sessionTO";
//        if(!rtnPg.equals("sucess")){
//            return mapping.findForward(rtnPg);  
//        }else{
//            SearchForm srchForm ;
//            SearchQuery query = new SearchQuery();
//        srchForm = (SearchForm)form;
//        util.updAccessLog(req,res,"Search Page", "Original Search");
//        String updGt = " update gt_srch_rslt set flg = 'Z' where flg in ('R','N','M') ";
//        db.execUpd("upd gt R", updGt, new ArrayList());
//            HashMap dbinfo = info.getDmbsInfoLst();
//            String cnt = (String)dbinfo.get("CNT");
//            String trDis="",loyalty="";
//            if(cnt.equals("xljf")){
//            trDis=util.nvl(util.gettradeDis(String.valueOf(info.getRlnId())));
//            }
//            if(cnt.equals("kj")){
//              ArrayList loyaltyLst=util.getloyalty(info.getSrchPryID());
//              srchForm.setValue("loyCtg", util.nvl((String)loyaltyLst.get(0)));
//              srchForm.setValue("loyPct", util.nvl((String)loyaltyLst.get(1)));
//              srchForm.setValue("loyDiff", util.nvl((String)loyaltyLst.get(2)));
//              srchForm.setValue("loyVlu", util.nvl((String)loyaltyLst.get(3)));
//                srchForm.setValue("mem_dis", util.nvl((String)loyaltyLst.get(4)));
//                srchForm.setValue("loyallow", util.nvl((String)loyaltyLst.get(5)));
//                srchForm.setValue("memallow", util.nvl((String)loyaltyLst.get(6)));
//                srchForm.setValue("ttlSalVlu", util.nvl((String)loyaltyLst.get(7)));
//                srchForm.setValue("osamount", util.nvl((String)loyaltyLst.get(8)));
//            }
//        HashMap searchList = query.SearchResult(req,res, "'Z'" , info.getVwPrpLst());
//      
////        req.setAttribute("searchList", searchList);
//        HashMap totals = util.getTtls(req);
//        info.setMultiSrchLst(new ArrayList());
//        info.setSvdmdDis(new ArrayList());
//        req.setAttribute("searchList", searchList);
//        req.setAttribute("total",totals);
//        
//        ArrayList memoList= (query.getMemoType(req,res) == null)?new ArrayList():(ArrayList)query.getMemoType(req,res);
////        srchForm.setValue("memoList", memoList);
//            srchForm.setMemoList(memoList);
//            HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(info.getSrchPryID()));
//            srchForm.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
//            srchForm.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
//            srchForm.setValue("otherref",util.nvl((String)buyerDtlMap.get("OTHERREF")));
//            srchForm.setValue("trDis",trDis);
//            srchForm.setValue("loyalty",loyalty);
//         return mapping.findForward("srch");
//        }
//    }
//    public ActionForward refSrch(ActionMapping mapping,
//    ActionForm form,
//    HttpServletRequest req,
//    HttpServletResponse res) throws Exception {
//        HttpSession session = req.getSession(false);
//        InfoMgr info = (InfoMgr)session.getAttribute("info");
//        DBUtil util = new DBUtil();
//        DBMgr db = new DBMgr();
//        String rtnPg="sucess";
//        if(info!=null){
//        Connection conn=info.getCon();
//        if(conn!=null){
//        db.setCon(info.getCon());
//        util.setDb(db);
//        util.setInfo(info);
//        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//        util.setLogApplNm(info.getLoApplNm());
//        rtnPg=init(req,res,session,util);
//        }else{
//        rtnPg="connExists";   
//        }
//        }else
//        rtnPg="sessionTO";
//        if(!rtnPg.equals("sucess")){
//            return mapping.findForward(rtnPg);  
//        }else{
//            SearchForm srchForm ;
//            SearchQuery query = new SearchQuery();
//        srchForm = (SearchForm)form;
//        ArrayList srchPrp = info.getRefPrpLst();
//        ArrayList vwPrpLst = info.getVwPrpLst();
//         util.updAccessLog(req,res,"Search Page", "Refine Search");
//         HashMap prp = info.getPrp();
//         ArrayList ary = null;
//         int ct = 0;
//            HashMap dbinfo = info.getDmbsInfoLst();
//            String cnt = (String)dbinfo.get("CNT");
//            String trDis="",loyalty="";
//            if(cnt.equals("xljf")){
//            trDis=util.nvl(util.gettradeDis(String.valueOf(info.getRlnId())));
//            }
//            if(cnt.equals("kj")){
//              ArrayList loyaltyLst=util.getloyalty(info.getSrchPryID());
//              srchForm.setValue("loyCtg", util.nvl((String)loyaltyLst.get(0)));
//              srchForm.setValue("loyPct", util.nvl((String)loyaltyLst.get(1)));
//              srchForm.setValue("loyDiff", util.nvl((String)loyaltyLst.get(2)));
//              srchForm.setValue("loyVlu", util.nvl((String)loyaltyLst.get(3)));
//                srchForm.setValue("mem_dis", util.nvl((String)loyaltyLst.get(4)));
//                srchForm.setValue("loyallow", util.nvl((String)loyaltyLst.get(5)));
//                srchForm.setValue("memallow", util.nvl((String)loyaltyLst.get(6)));
//                srchForm.setValue("ttlSalVlu", util.nvl((String)loyaltyLst.get(7)));
//                srchForm.setValue("osamount", util.nvl((String)loyaltyLst.get(8)));
//            }
//         String zcnt = "select count(*) cnt, flg from gt_srch_rslt group by flg ";
//         ResultSet rsCnt = db.execSql("count", zcnt, new ArrayList());
//         while(rsCnt.next()){
//             System.out.println("flg : "+rsCnt.getString("flg")+" : count"+rsCnt.getString("cnt") );
//         }
//         rsCnt.close();
//         String updGt = " update gt_srch_rslt set flg = 'Z' where flg = 'N' ";
//         db.execUpd("upd gt R", updGt, new ArrayList());
//         
//     for(int i=0 ; i < srchPrp.size() ; i++){   
//          ArrayList srchV =(ArrayList)srchPrp.get(i);
//           String lprp = (String)srchV.get(0);
//          String flg = (String)srchV.get(1);
//         
//           if(!flg.equals("H")){
//           int inxPrp = vwPrpLst.indexOf(lprp);
//           String fld = "";
//          String srtfld = "";
//           if (inxPrp < 9){
//           fld = "prp_00" + (inxPrp + 1);
//           srtfld = "srt_00" + (inxPrp + 1);
//          }else{
//           fld = "prp_0" + (inxPrp + 1);
//           srtfld = "srt_0" + (inxPrp + 1);
//          }
//            if(lprp.equals("RTE"))
//                srtfld = "quot";
//          ArrayList prpSrt = (ArrayList)prp.get(lprp+"S");
//          if(prpSrt==null){
//              String minVal = req.getParameter(lprp+"_1");
//              String maxVal = req.getParameter(lprp+"_2");
//            
//            String sql = "update gt_srch_rslt set flg = 'N' " +
//                  " where "+srtfld+" not between  "+minVal+" and "+maxVal+" "+
//                  " and flg  = 'Z'";
//              ct = db.execUpd("update", sql, new ArrayList());
//              
//              System.out.println("prp:"+lprp+" Count:"+ct);
//              
//          }
//        }}
//        
//        String rapUp = util.nvl(req.getParameter("rapUp"));
//        
//       
//        HashMap searchList = query.SearchResult(req,res, "'Z'" , info.getVwPrpLst());
//        
//        HashMap totals = util.getTtls(req);
//        info.setMultiSrchLst(new ArrayList());
//        info.setSvdmdDis(new ArrayList());
//        req.setAttribute("searchList", searchList);
//        req.setAttribute("total",totals);
////        String memoTyp = req.getParameter("memoTyp");
////        HashMap memoTypMap = (HashMap)session.getAttribute("memoTypMap");
////        String srchMemoTyp = (String)memoTypMap.get(memoTyp);
//        ArrayList memoList= (query.getMemoType(req,res) == null)?new ArrayList():(ArrayList)query.getMemoType(req,res);
////        srchForm.setValue("memoList", memoList);
//        srchForm.setMemoList(memoList);
//        srchForm.setValue("typ",util.nvl(util.nvl((String)info.getDmbsInfoLst().get(util.nvl((String)info.getIsMix(),"NR")+"_MEMO_TYP")),"Z"));
//        req.setAttribute("memoTyp",util.nvl(util.nvl((String)info.getDmbsInfoLst().get(util.nvl((String)info.getIsMix(),"NR")+"_MEMO_TYP")),"Z"));
//            HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(info.getSrchPryID()));
//            srchForm.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
//            srchForm.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
//            srchForm.setValue("otherref",util.nvl((String)buyerDtlMap.get("OTHERREF")));
//            srchForm.setValue("trDis",trDis);
//            srchForm.setValue("loyalty",loyalty);
//        return mapping.findForward("srch");
//        }
//    }
//        
  public ActionForward sortSrch(ActionMapping mapping,
  ActionForm form,
  HttpServletRequest req,
  HttpServletResponse res) throws Exception {
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
          return mapping.findForward(rtnPg);  
      }else{
         SearchForm srchForm ;
       
         srchForm = (SearchForm)form;
          HashMap mprp = info.getMprp();
          HashMap prp = info.getPrp(); 
           String lstNme = (String)gtMgr.getValue("lstNme");
         String ref = util.nvl(req.getParameter("REF"));
         SearchQuery query = new SearchQuery();
          if(ref.equals("YES")){
           lstNme = lstNme+"REF";
            req.setAttribute("REF", "YES");
          }
          String sort_srch = util.nvl(req.getParameter("sort_srch"));
          if(sort_srch.equals("YES")){
           HashMap pktDtl = (HashMap)gtMgr.getValue(lstNme);
         HashMap newPktDtl = new HashMap();
         ArrayList pktStkIdnList =new ArrayList();
         Set<String> keys = pktDtl.keySet();
                for(String key: keys){
               pktStkIdnList.add(key);
                }  
         for(int j=0;j<pktStkIdnList.size();j++){
             String stkIdn =(String)pktStkIdnList.get(j);
             GtPktDtl pkts =(GtPktDtl)pktDtl.get(stkIdn);
             String sk1="";
             for(int i=1;i<6;i++){
               String srt = "srt_"+i;
               String lprp = util.nvl((String)srchForm.getValue(srt));
                 if(!lprp.equals("0")){
                 String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
                 if(lprpTyp.equals("C")){
                ArrayList lprpSrt = (ArrayList)prp.get(lprp+"S");
                ArrayList lprpPrt = (ArrayList)prp.get(lprp+"P");
                ArrayList lprpVal = (ArrayList)prp.get(lprp+"V");
                String lval = pkts.getValue(lprp);
                 int valInx = lprpPrt.indexOf(lval);
                 if(valInx==-1)
                     valInx = lprpVal.indexOf(lval);
                 if(valInx!=-1){
                String valSrt = (String)lprpSrt.get(valInx);
                sk1 = sk1+util.lpad(valSrt, 4, "0");
                }}else if(lprp.equals("CRTWT")){
                   double lval = Double.parseDouble(util.nvl2(pkts.getValue(lprp), "0").trim());
                   lval = lval * 100;
                   sk1 = sk1+util.lpad(String.valueOf(lval) ,4, "0");
                 }else if(lprp.equals("RTE")){
                   String lval = util.nvl2(pkts.getValue(lprp),"0");
                   int  lvalLst = Math.round(Float.parseFloat(lval));
                   sk1 = sk1+util.lpad(String.valueOf(lvalLst) ,6, "0");
                 }
                 }
              srchForm.setValue(srt, lprp);
            }
             sk1 = sk1+String.valueOf(pkts.getSk1());
             if(!sk1.equals(""))
             pkts.setSk1(new BigDecimal(sk1));
             newPktDtl.put(stkIdn, pkts);
         }
         newPktDtl = (HashMap)query.sortByComparator(newPktDtl);
          gtMgr.setValue(lstNme+"_SRT", newPktDtl);
          req.setAttribute("SRT", "YES");
          }
         HashMap dbinfo = info.getDmbsInfoLst();
         String cnt = (String)dbinfo.get("CNT");
         String trDis="",loyalty="";
         if(cnt.equals("xljf")){
         trDis=util.nvl(util.gettradeDis(String.valueOf(info.getRlnId())));
         }
           HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
           HashMap pageDtl=(HashMap)allPageDtl.get("SEARCH_RESULT");
           if(pageDtl==null || pageDtl.size()==0){
           pageDtl=new HashMap();
           pageDtl=util.pagedef("SEARCH_RESULT");
           allPageDtl.put("SEARCH_RESULT",pageDtl);
           }
          
       ArrayList  pageList= ((ArrayList)pageDtl.get("LOYALTY") == null)?new ArrayList():(ArrayList)pageDtl.get("LOYALTY");
        if(pageList!=null && pageList.size() >0){
        srchForm.setValue("LOYALTY", "YES");
           ArrayList loyaltyLst=util.getloyalty(info.getSrchPryID());
           srchForm.setValue("loyCtg", util.nvl((String)loyaltyLst.get(0)));
           srchForm.setValue("loyPct", util.nvl((String)loyaltyLst.get(1)));
           srchForm.setValue("loyDiff", util.nvl((String)loyaltyLst.get(2)));
           srchForm.setValue("loyVlu", util.nvl((String)loyaltyLst.get(3)));
             srchForm.setValue("mem_dis", util.nvl((String)loyaltyLst.get(4)));
             srchForm.setValue("loyallow", util.nvl((String)loyaltyLst.get(5)));
             srchForm.setValue("memallow", util.nvl((String)loyaltyLst.get(6)));
             srchForm.setValue("ttlSalVlu", util.nvl((String)loyaltyLst.get(7)));
             query.iememov(req,info.getSrchPryID());
         }
          
         info.setMultiSrchLst(new ArrayList());
         info.setSvdmdDis(new ArrayList());
         
         //        String memoTyp = req.getParameter("memoTyp");
         //        HashMap memoTypMap = (HashMap)session.getAttribute("memoTypMap");
         //        String srchMemoTyp = (String)memoTypMap.get(memoTyp);
         ArrayList memoList= (query.getMemoType(req,res) == null)?new ArrayList():(ArrayList)query.getMemoType(req,res);
         //        srchForm.setValue("memoList", memoList);
         srchForm.setMemoList(memoList);
         srchForm.setValue("typ","Z");
         req.setAttribute("memoTyp","Z");
             HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(info.getSrchPryID()));
             srchForm.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
             srchForm.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
             srchForm.setValue("otherref",util.nvl((String)buyerDtlMap.get("OTHERREF")));
           srchForm.setValue("gstin",util.nvl((String)buyerDtlMap.get("GSTIN")));
             srchForm.setValue("trDis",trDis);
             srchForm.setValue("loyalty",loyalty);
           finalizeObject(db, util);
          return mapping.findForward("srch");
       }
   }
    
  public ActionForward refSrchObj(ActionMapping mapping,
    ActionForm form,
    HttpServletRequest req,
    HttpServletResponse res) throws Exception {
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
            return mapping.findForward(rtnPg);  
        }else{
            SearchForm srchForm ;
            SearchQuery query = new SearchQuery();
        srchForm = (SearchForm)form;
          String lstNme = (String)gtMgr.getValue("lstNme");

         util.updAccessLog(req,res,"Search Page", "Refine Search");
         HashMap prp = info.getPrp();
         ArrayList ary = null;
         int ct = 0;
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            String trDis="",loyalty="";
            if(cnt.equals("xljf")){
            trDis=util.nvl(util.gettradeDis(String.valueOf(info.getRlnId())));
            }
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("SEARCH_RESULT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("SEARCH_RESULT");
            allPageDtl.put("SEARCH_RESULT",pageDtl);
            }
            ArrayList  pageList= ((ArrayList)pageDtl.get("LOYALTY") == null)?new ArrayList():(ArrayList)pageDtl.get("LOYALTY");
            if(pageList!=null && pageList.size() >0){
             srchForm.setValue("LOYALTY", "YES");
              ArrayList loyaltyLst=util.getloyalty(info.getSrchPryID());
              srchForm.setValue("loyCtg", util.nvl((String)loyaltyLst.get(0)));
              srchForm.setValue("loyPct", util.nvl((String)loyaltyLst.get(1)));
              srchForm.setValue("loyDiff", util.nvl((String)loyaltyLst.get(2)));
              srchForm.setValue("loyVlu", util.nvl((String)loyaltyLst.get(3)));
                srchForm.setValue("mem_dis", util.nvl((String)loyaltyLst.get(4)));
                srchForm.setValue("loyallow", util.nvl((String)loyaltyLst.get(5)));
                srchForm.setValue("memallow", util.nvl((String)loyaltyLst.get(6)));
                srchForm.setValue("ttlSalVlu", util.nvl((String)loyaltyLst.get(7)));
                query.iememov(req,info.getSrchPryID());
            }
       
        HashMap refPktDtl = (HashMap)gtMgr.getValue(lstNme+"_REF");
          HashMap pktDtl = (HashMap)gtMgr.getValue(lstNme);
            HashMap newPktDtl =null;
          ArrayList pktStkIdnList =new ArrayList();
          Set<String> keys = pktDtl.keySet();
                 for(String key: keys){
                pktStkIdnList.add(key);
                 }
          ArrayList vwPrpLst =info.getRefPrpLst();
            HashMap mprp= info.getMprp();
          for(int i=0;i<vwPrpLst.size();i++){
          newPktDtl = new HashMap();
          String lprp = (String)vwPrpLst.get(i);
          String lprpTyp = (String)mprp.get(lprp+"T");
          ArrayList valList = (ArrayList)refPktDtl.get(lprp);
              for(int j=0;j<pktStkIdnList.size();j++){
                  String stkIdn =(String)pktStkIdnList.get(j);
                  GtPktDtl pkts =(GtPktDtl)pktDtl.get(stkIdn);
              if(lprpTyp.equals("C")){
                String lprpVal = pkts.getValue(lprp);
                if(valList.contains(lprpVal))
                  newPktDtl.put(stkIdn,pkts);
              }else{
                  String valFm = (String)valList.get(0);
                  String valTo = (String)valList.get(1);

                  Double valfnum = Double.parseDouble(valFm);
                  Double valtnum = Double.parseDouble(valTo);
                  String gtVal = util.nvl(pkts.getValue(lprp),"0");
                  Double gtValNum = Double.parseDouble(gtVal);
                  if(gtValNum<=valtnum && gtValNum >=valfnum){
                    newPktDtl.put(stkIdn,pkts);
                  }
              }
          
          }
          pktDtl = newPktDtl;
          pktStkIdnList =new ArrayList();
            keys = pktDtl.keySet();
           for(String key: keys){
             pktStkIdnList.add(key);
               }
        }
          HashMap dtlMap = new HashMap();
          dtlMap.put("selIdnLst",pktStkIdnList);
          dtlMap.put("pktDtl", pktDtl);
          dtlMap.put("flg", "Z");
          HashMap ttlMap = util.getTTL(dtlMap);
          pktDtl = (HashMap)query.sortByComparator(pktDtl);
          gtMgr.setValue(lstNme+"REF",pktDtl);
          gtMgr.setValue(lstNme+"REF_TTL",ttlMap);
          req.setAttribute("REF", "YES");
        info.setMultiSrchLst(new ArrayList());
        info.setSvdmdDis(new ArrayList());
      
    //        String memoTyp = req.getParameter("memoTyp");
    //        HashMap memoTypMap = (HashMap)session.getAttribute("memoTypMap");
    //        String srchMemoTyp = (String)memoTypMap.get(memoTyp);
        ArrayList memoList= (query.getMemoType(req,res) == null)?new ArrayList():(ArrayList)query.getMemoType(req,res);
    //        srchForm.setValue("memoList", memoList);
        srchForm.setMemoList(memoList);
        srchForm.setValue("typ","Z");
        req.setAttribute("memoTyp","Z");
            HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(info.getSrchPryID()));
            srchForm.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
            srchForm.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
            srchForm.setValue("otherref",util.nvl((String)buyerDtlMap.get("OTHERREF")));
            srchForm.setValue("gstin",util.nvl((String)buyerDtlMap.get("GSTIN")));
            srchForm.setValue("trDis",trDis);
            srchForm.setValue("loyalty",loyalty);
            finalizeObject(db, util);
        return mapping.findForward("srch");
        }
    }
        
  public ActionForward Modify(ActionMapping mapping,  ActionForm form,  HttpServletRequest req,  HttpServletResponse res) throws Exception {
      return mapping.findForward("load");
  }
  
    public ActionForward DmdList(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
            return mapping.findForward(rtnPg); 
        }else{
            SearchForm srchForm ;
            SearchQuery query = new SearchQuery();
    srchForm = (SearchForm)form;
          util.updAccessLog(req,res,"Search Page", "dmd List");
    ArrayList actDmdLst = new ArrayList();
    ArrayList ary = new ArrayList();
    ary.add("DMD");
    String fetchDmd = "select a.dmd_id , a.dsc,b.nme from mdmd a,nme_v b where a.mdl=? and a.name_id=b.nme_idn and a.todte is null ";
    String byrIdn = util.nvl(srchForm.getByrId());
    String partyIdn = util.nvl(srchForm.getParty());
    
    if(!partyIdn.equals("") && !partyIdn.equals("0") && (byrIdn.equals("") || byrIdn.equals("0"))){
    fetchDmd = "select a.dmd_id , a.dsc,b.nme from mdmd a,nme_v b where a.mdl=? and a.name_id=b.nme_idn and a.name_id=? and a.todte is null ";
    ary.add(partyIdn);
    info.setSrchPryID(Integer.parseInt(partyIdn));
    }
        
    if(!byrIdn.equals("") && !byrIdn.equals("0")){    
    fetchDmd = "Select A.Dmd_Id , A.Dsc,B.Nme From Mdmd A,Nme_V B Where A.Mdl=? And A.Name_Id=B.Nme_Idn\n" + 
    "and b.emp_idn=? and a.todte is null ";
    ary.add(byrIdn);
    info.setSrchByrId(Integer.parseInt(byrIdn));
    if(!partyIdn.equals("") && !partyIdn.equals("0")){
    fetchDmd = "Select A.Dmd_Id , A.Dsc,B.Nme From Mdmd A,Nme_V B Where A.Mdl=? And A.Name_Id=B.Nme_Idn\n" + 
        "and b.emp_idn=? and a.name_id=? and a.todte is null ";
    ary.add(partyIdn);
    info.setSrchPryID(Integer.parseInt(partyIdn));
    }
    }
          ArrayList  outLst = db.execSqlLst("activeList",fetchDmd, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
       
    while(rs.next()){
    ArrayList actDmd = new ArrayList();
    actDmd.add(rs.getString("dmd_id"));
    actDmd.add(rs.getString("nme"));
    actDmd.add(rs.getString("dsc"));

    actDmdLst.add(actDmd);
    }
        rs.close();
            pst.close();
    req.setAttribute("dmdList", actDmdLst);
    if(info.getVwPrpLst()==null){
    util.initSrch();
//    util.initPrp();
    }
    ArrayList byrLst= query.getByrListDmd(req,res);
    srchForm.setByrList(byrLst);
    ArrayList partyLst = query.getPartyListDmd(req,res);
    srchForm.setPartyList(partyLst);
//    ArrayList imagelistDtl= (util.imageDtls() == null)?new ArrayList():(ArrayList)util.imageDtls();
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("SEARCH_RESULT");
    if(pageDtl==null || pageDtl.size()==0){
    pageDtl=new HashMap();
    pageDtl=util.pagedef("SEARCH_RESULT");
    allPageDtl.put("SEARCH_RESULT",pageDtl);
    }
    info.setPageDetails(allPageDtl);
    util.updAccessLog(req,res,"Active Demand", "Active Demand");
            finalizeObject(db, util);
    return mapping.findForward("DmdList");
        }
    }

    public ActionForward rmvDmd(ActionMapping mapping,
    ActionForm form,
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
            return mapping.findForward(rtnPg);  
        }else{
            SearchForm srchForm ;
            SearchQuery query = new SearchQuery();
        String dmdId = req.getParameter("dmd");
        ArrayList rmAry = new ArrayList();
        rmAry.add(dmdId);
        int updCt = db.execCall(" Rmv Dmd ", " dmd_pkg.rmv(?) ", rmAry);
        util.updAccessLog(req,res,"Remove Demand", "Remove Demand");
        return DmdList(mapping, form, req, res);
        }
    }
    
  public ActionForward memo(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
              SearchForm srchForm ;
              SearchQuery query = new SearchQuery();
          synchronized(this){
          util.updAccessLog(req,res,"Create Memo", "Create Memo");
          query = new SearchQuery();
          srchForm = (SearchForm)form;
          ExcelUtil excelUtil = new ExcelUtil();
          excelUtil.init(db, util, info);
          HashMap dbinfo = info.getDmbsInfoLst();
          String cnt = (String)dbinfo.get("CNT");
          String externalLnk = util.nvl((String)dbinfo.get("EXTERNALLNK"));
          String NBRte = util.nvl((String)dbinfo.get("INBRTE"),"0");
          String rapsync = util.nvl((String)dbinfo.get("RAPSYNC"));
         int memoId = 0;
          String web_block = util.nvl((String)srchForm.getValue("web_block"),"NA");
          String offerBtn = util.nvl((String)srchForm.getValue("offer"));
          String saveofferBtn = util.nvl((String)srchForm.getValue("saveoffer"));
          String priceChange = util.nvl((String)srchForm.getValue("priceChange"));
              Enumeration reqNme = req.getParameterNames();
              ArrayList ary = new ArrayList();
              String selBkt = "cb_memo_";
              ArrayList memoLst = new ArrayList();
              int cbmemo=0;
              int matchMemo=0;
              while(reqNme.hasMoreElements()) {
                  String paramNm = (String)reqNme.nextElement();
                
                  if(paramNm.indexOf("cb_memo_") > -1) {
                      String val = req.getParameter(paramNm);
                 cbmemo=cbmemo+1;
                      String pktNm = val ;
                 System.out.println("paramNm :"+paramNm +" BKT:"+selBkt+val);
                 if(paramNm.equals(selBkt+val)){
                          memoLst.add(pktNm);
                   matchMemo=matchMemo+1;
                 }
               }}
            System.out.println("cbMemo : "+cbmemo);
            System.out.println("MatcbMemo :"+matchMemo);
              System.out.println("MEMO LIST"+memoLst.size());
              double extRte = info.getXrt();
                String lstNme = util.nvl(req.getParameter("lstNme"));
          if(saveofferBtn.length()>0){
              finalizeObject(db, util);
              int ct = db.execUpd("delete gt","Delete from gt_srch_rslt", new ArrayList());
             
                if(!lstNme.equals("")){                  
                  HashMap pktDtls = (HashMap)gtMgr.getValue(lstNme);
                  if(memoLst!=null && memoLst.size()>0){
                      int gtCnt=0;
                    for(int i=0;i<memoLst.size();i++){
                      String mstkIdn = (String)memoLst.get(i);
                      GtPktDtl pktDtl = (GtPktDtl)pktDtls.get(mstkIdn);
                      String gtInsrt="Insert into gt_srch_rslt ( stk_Idn, qty, cts,rap_rte, cmp, quot, rap_dis,flg,srt_090) select ? ,?,?,?,? ,?,?,?,? from dual";
                       ary = new ArrayList();
                      ary.add(mstkIdn);
                      ary.add(pktDtl.getValue("qty"));
                      ary.add(pktDtl.getValue("cts"));
                      ary.add(pktDtl.getValue("rap_rte"));
                      ary.add(pktDtl.getValue("cmp"));
                      ary.add(pktDtl.getValue("quot"));
                      ary.add(pktDtl.getValue("r_dis"));
                      ary.add("M");
                      ary.add(pktDtl.getValue("memo_wtdiff"));
                     int ct1 =db.execUpd("gtInsert", gtInsrt, ary);
                      gtCnt=gtCnt+ct1;
                    }
                    
                    System.out.print("GT LIST"+gtCnt);
                  }  }
          return saveOfferSrch(am, srchForm, req, res);
          }
          Boolean  excelDnl = null,mailExcl=null ,mailPdf_excel=null;
          excelDnl = Boolean.valueOf(false);
          mailExcl = Boolean.valueOf(false);
              mailPdf_excel = Boolean.valueOf(false);
          if(req.getParameter("mail")!=null)
            mailExcl = Boolean.valueOf(true);
          if(req.getParameter("excel")!=null)
             excelDnl = Boolean.valueOf(true);
          if(req.getParameter("mailPdf_excel")!=null)
                 mailPdf_excel = Boolean.valueOf(true);
          String flg = "NN";
          String salConfirm = util.nvl((String)srchForm.getValue("saleConfirm"));
          String salConfirmix = util.nvl((String)srchForm.getValue("saleConfirmmix"));
          String submitsal = util.nvl((String)srchForm.getValue("submitsal"));
          String memoTyp = (String)srchForm.getValue("typ");
          MemoType memoObj = query.getMemoTypeDtl(req, res, memoTyp);
          String hdr_stt =util.nvl(memoObj.getHdr_stt(),"IS");
          String dtl_stt = util.nvl(memoObj.getDtl_stt(),"IS");
          String isDeal = util.nvl(req.getParameter("deal"));
          String symbol = util.nvl((String)srchForm.getValue("SYMBOL"));
          if(isDeal.equals("deal")){
             flg = "DEAL" ;
          }
          String isMix = util.nvl(req.getParameter("isMix"));
         
              if(isMix.equals("RGH")){
                  String val = req.getParameter("cb_memo_");
                  memoLst.add(val);
              }
          if(memoLst.size()>0){
              if(offerBtn.length()>0){
                  lstNme = util.nvl((String)gtMgr.getValue("lstNme"));
                  if(!lstNme.equals("")){                  
                    HashMap pktDtls = (HashMap)gtMgr.getValue(lstNme);
                    ArrayList stkIdnList = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
                    if(stkIdnList!=null && stkIdnList.size()>0){
                        HashMap bidPktDtl = new HashMap();
                        for(int i=0;i<stkIdnList.size();i++){
                          String stkIdn = (String)stkIdnList.get(i);
                          GtPktDtl pktDtl = (GtPktDtl)pktDtls.get(stkIdn);
                            bidPktDtl.put(stkIdn,pktDtl);
                        }
                        gtMgr.setValue("BID"+lstNme ,bidPktDtl);
                        HashMap dtlMap = new HashMap();
                      dtlMap.put("selIdnLst",stkIdnList);
                      dtlMap.put("pktDtl", bidPktDtl);
                      dtlMap.put("flg", "B");
                      HashMap ttlMap = util.getTTL(dtlMap);
                      gtMgr.setValue("BID"+lstNme+"_TTL",ttlMap);
                    } 
                  }
                  return am.findForward("bid");
              }else if(isMix.equals("MIX") || isMix.equals("RGH")){
                   String mrmk =util.nvl((String)srchForm.getValue("rmk"));
                   String mrmk1 =util.nvl((String)srchForm.getValue("rmk1"));
                   if(!mrmk1.equals(""))
                       mrmk=mrmk+"@"+mrmk1;
                   String consign = util.nvl((String)srchForm.getValue("consign"));
                   if(!consign.equals("")){
                       memoTyp="CS";
                       dtl_stt="IS";
                       hdr_stt="IS";
                   }
                   if(!salConfirm.equals("") || !salConfirmix.equals("") || !submitsal.equals("")){
                   memoTyp = "IAP";
                   hdr_stt = "IS";
                   dtl_stt="AP";
                   }
                   
                   for(int i =0 ; i < memoLst.size() ; i++){
                       boolean isSpilt = false;
                       if(memoId==0){
                           String genHdr = "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ?  , pTyp => ? ,pflg => ? , pThru=> ? ,pXrt=>?,pRem => ?, pMemoIdn => ?)";
                           ary = new ArrayList();
                           ary.add(String.valueOf(info.getByrId()));
                           ary.add(String.valueOf(info.getRlnId()));
                           ary.add(hdr_stt);
                           ary.add(memoTyp);
                           ary.add(flg);
                           ary.add(req.getParameter("thruPer"));
                           ary.add(String.valueOf(info.getXrt()));
                           ary.add(mrmk);
                           ArrayList out = new ArrayList();
                           out.add("I");
                           CallableStatement cst = null;
                           cst =db.execCall("MKE_HDR ", genHdr , ary , out);
                           memoId = cst.getInt(ary.size()+1);
                         cst.close();
                         cst=null;
                           
                           if(isMix.equals("MIX") && cnt.equals("kj")){
                               ary = new ArrayList();
                               ary.add(String.valueOf(info.getXrt()));
                               ary.add(String.valueOf(memoId));
                               db.execUpd("update SLRB", "update mjan set EXH_RTE=1,fnl_exh_rte=?  where idn=?", ary);
                           }
                       }
                       String stkIdn = (String)memoLst.get(i);
                       if(!memoTyp.equals("Z")){
                       for(int j=1;j<=10;j++){
                           
                       String slQty = util.nvl(req.getParameter("QTY_"+stkIdn+"_"+j));
                       String slCts = util.nvl(req.getParameter("CTS_"+stkIdn+"_"+j));
                       String slPrc = util.nvl(req.getParameter("PRC_"+stkIdn+"_"+j));
                       String slRmk = util.nvl(req.getParameter("RMK_"+stkIdn+"_"+j));
                       if(!slCts.equals("") && !slPrc.equals("")){
                           isSpilt=true;
                           String mixRtn ="mix_memo_pkg.part_iss_pkt(pMemoIdn => ? , pRtPkt => ? , pQty => ?, pCts => ?, pRte => ? ,pRem => ?,pStkIdn => ?, pMsg => ? ) ";
                           ary = new ArrayList();
                           ary.add(String.valueOf(memoId));
                           ary.add(stkIdn);
                           ary.add(slQty);
                           ary.add(slCts);
                           ary.add(slPrc);
                           ary.add(slRmk);
                           ArrayList out = new ArrayList();
                           out.add("I");
                           out.add("V");
                           CallableStatement ct = db.execCall("mixRtn", mixRtn, ary,out);
                           
                       }
                       }}
                       if(!isSpilt){
                       String qty = util.nvl(req.getParameter("qty_"+stkIdn));
                       String cts = util.nvl(req.getParameter("cts_"+stkIdn));
                       String quot = util.nvl(req.getParameter("MixRte_"+stkIdn));
                       String rmk = util.nvl(req.getParameter("rmk_"+stkIdn));
                       ary = new ArrayList();
                       ary.add(Integer.toString(memoId));
                       ary.add(stkIdn);
                       ary.add(qty);
                       ary.add(cts);
                       ary.add(quot);
                       ary.add(dtl_stt);
                       ary.add(rmk);
                       int ct1 = db.execCall("pop Memo pkt", "MEMO_PKG.Pop_Memo_Pkt(pMemoIdn => ? , pStkIdn => ?, pQty => ?, pCts => ?  ,  pQuot => ? ,  pStt => ? , pRem => ?)", ary);
                       }
                       
                   }
                   ary = new ArrayList();
                   ary.add(Integer.toString(memoId));
                   int ct2 = db.execCall("jan_qty", "jan_qty(?)", ary);
                   if(ct2>0)
                   req.setAttribute("msg", "memo get Create with memo no."+ memoId);
                   ary = new ArrayList();
                   ary.add(Integer.toString(memoId));
                   ct2 = db.execCall("jan_qty", "memo_pkg.cal_fnl_quot(?)", ary);     
                   session.setAttribute("memoId", String.valueOf(memoId));
               }else{
          
                  if(!salConfirm.equals("")){
                      memoTyp = "IAP";
                      hdr_stt = "IS";
                      dtl_stt="AP";
                  }
                  
              int ct = db.execUpd("delete gt","Delete from gt_srch_rslt", new ArrayList());
  //              ct = db.execUpd("delete gt","Delete from gt_pkt_scan", new ArrayList());
  //                              
  //                String pktLst = memoLst.toString();
  //                 pktLst= pktLst.replace("["," ");
  //                 pktLst= pktLst.replace("]"," ");
  //                pktLst= pktLst.replaceAll(" ","");
  //                System.out.println(pktLst);  
  //              int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
  //              String[] vnmLst = pktLst.split(",");
  //              int loopCnt = 1 ;
  //              float loops = ((float)vnmLst.length)/stepCnt;
  //              loopCnt = Math.round(loops);
  //                 if(vnmLst.length <= stepCnt || new Float(loopCnt)>=loops) {
  //                  
  //              } else
  //                  loopCnt += 1 ;
  //              if(loopCnt==0)
  //                  loopCnt += 1 ;
  //              int fromLoc = 0 ;
  //              int toLoc = 0 ;
  //              for(int i=1; i <= loopCnt; i++) {
  //                  
  //                int aryLoc = Math.min(i*stepCnt, vnmLst.length) ;
  //                
  //                String lookupVnm = vnmLst[aryLoc-1];
  //                     if(i == 1)
  //                         fromLoc = 0 ;
  //                     else
  //                         fromLoc = toLoc+1;
  //                     
  //                     toLoc = Math.min(pktLst.lastIndexOf(lookupVnm) + lookupVnm.length(), pktLst.length());
  //                     String vnmSub = pktLst.substring(fromLoc, toLoc);
  //                  
  //              vnmSub="'"+vnmSub+"'";
  //              ArrayList params = new ArrayList();
  //              //        params.add(vnmSub);
  //              String insScanPkt = " insert into gt_pkt_scan select * from TABLE(PARSE_TO_TBL("+vnmSub+"))";
  //                ct = db.execDirUpd(" ins scan", insScanPkt,params);
  //                System.out.println(insScanPkt);  
  //              }
  //
  //                String gtInsrt="Insert into gt_srch_rslt ( stk_Idn, qty, cts,rap_rte, cmp, quot, rap_dis,flg)\n" + 
  //                "     select b.idn ,b.qty,b.cts,b.rap_rte,nvl(b.upr,b.cmp),nvl(b.upr,b.cmp), decode(b.rap_rte ,'1',null, trunc((nvl(b.upr,b.cmp)/b.rap_rte*100)-100, 2)),'M'\n" + 
  //                "     from mstk b , gt_pkt_scan a where b.idn=a.vnm" ;
  //              ct = db.execDirUpd(" Srch Prp ", gtInsrt, new ArrayList()); 
  //               
  //              double extRte = info.getXrt();
  //
  //              String calQuot = "pkgmkt.Cal_Quot( pRlnId=> ?, pXrt=> ?)";
  //              ary = new ArrayList();
  //              ary.add(Integer.toString(info.getRlnId()));
  //              ary.add(String.valueOf(extRte));
  //              ct = db.execCall(" Srch calQ ", calQuot, ary);
  //              
            
                    if(!lstNme.equals("")){                  
                      HashMap pktDtls = (HashMap)gtMgr.getValue(lstNme);
                      if(memoLst!=null && memoLst.size()>0){
                        for(int i=0;i<memoLst.size();i++){
                          String mstkIdn = (String)memoLst.get(i);
                         
                            
                          GtPktDtl pktDtl = (GtPktDtl)pktDtls.get(mstkIdn);
                            String rdis = util.nvl(pktDtl.getValue("r_dis"));
                            if(rdis.equals(""))
                                rdis="0";
                          String gtInsrt="Insert into gt_srch_rslt ( stk_Idn, qty, cts,rap_rte, cmp, quot, rap_dis,flg,srt_090) select ? ,?,?,?,? ,?,?,?,? from dual";
                           ary = new ArrayList();
                          ary.add(mstkIdn);
                          ary.add(pktDtl.getValue("qty"));
                          ary.add(pktDtl.getValue("cts"));
                          ary.add(pktDtl.getValue("rap_rte"));
                          ary.add(pktDtl.getValue("cmp"));
                          ary.add(pktDtl.getValue("quot"));
                          ary.add(pktDtl.getValue("r_dis"));
                          ary.add("M");
                          ary.add(util.nvl(pktDtl.getValue("memo_wtdiff")));
                          ct =db.execUpd("gtInsert", gtInsrt, ary);
                        }
                      }  
                    
                  }
                             // if(ct > 0){
                  String cabin=util.nvl((String)srchForm.getValue("cabin"));
              String genHdr = "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ?  , pTyp => ? ,pflg => ? , pThru=> ? ,pXrt=>?,pRem => ?, pMemoIdn => ?)";
              ary = new ArrayList();
              ary.add(String.valueOf(info.getByrId()));
              ary.add(String.valueOf(info.getRlnId()));
              ary.add(hdr_stt);
              ary.add(memoTyp);
              ary.add(flg);
            if(memoTyp.equals("E")){
              ary.add(util.nvl((String)srchForm.getValue("day")));
              ary.add(util.nvl((String)srchForm.getValue("extme")));
              ary.add(util.nvl((String)srchForm.getValue("extconf")));
              genHdr = "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ?  , pTyp => ? ,pflg => ?  , pExpDys=>? , pExpTm => ? , pExtCnfPct=>? , pThru=> ? ,pXrt=>?,pRem => ?, pMemoIdn => ?)";
             }
                  
            if((memoTyp.equals("I") && !cabin.equals("")) || ((cnt.equals("smf") && memoTyp.equals("IAP")))){
                  ary.add(cabin);
                  genHdr = "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ? , pTyp => ? ,pflg => ? , pCabin=>? , pThru=> ? ,pXrt=>?,pRem => ?, pMemoIdn => ?)";
               }
              ary.add(req.getParameter("thruPer"));
              ary.add(String.valueOf(extRte));
              ary.add(util.nvl((String)srchForm.getValue("rmk")));
              ArrayList out = new ArrayList();
              out.add("I");
              CallableStatement cst = null;
              cst =db.execCall("MKE_HDR ", genHdr , ary , out);
              
              memoId = cst.getInt(ary.size()+1);
                cst.close();
                cst=null;
              ary = new ArrayList();
              ary.add(Integer.toString(memoId));
              ary.add(dtl_stt);
              ary.add("M");
              int ct1 = db.execCall("pop Memo from gt", "MEMO_PKG.POP_MEMO_FRM_GT(?,?,?)", ary);
              
              ary = new ArrayList();
              ary.add(Integer.toString(memoId));
              int ct2 = db.execCall("jan_qty", "jan_qty(?)", ary);
              if(ct2>0)
                 req.setAttribute("msg", "memo get Create with memo no."+ memoId);
                  
                  String SLRBRTE = util.nvl((String)req.getParameter("SLRBRTE"));
                  if(!SLRBRTE.equals("")){
                      ary = new ArrayList();
                      ary.add(SLRBRTE);
                      ary.add(Integer.toString(memoId));
                      db.execUpd("update SLRB", "update mjan set flg1=?  where idn=?", ary);
                  }
                  
                  String wtdiff = util.nvl((String)srchForm.getValue("wtdiff"));
                  if(!wtdiff.equals("")){
                      ary = new ArrayList();
                      ary.add(wtdiff);
                      ary.add(Integer.toString(memoId));
                      db.execUpd("update SLRB", "update mjan set flg2=?  where idn=?", ary);
                  }
                  
                  String rmk2 = util.nvl((String)srchForm.getValue("rmk2"));
                  if(!rmk2.equals("")){
                      ary = new ArrayList();
                      ary.add(rmk2);
                      ary.add(Integer.toString(memoId));
                      db.execUpd("update rmk2", "update mjan set rmk2=?  where idn=?", ary);
                  }
                  
                  if(memoTyp.equals("BB")){
                      ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
                          if(chargesLst!=null && chargesLst.size()>0){
                          for(int i=0;i<chargesLst.size();i++){
                          HashMap dtl=new HashMap();
                          dtl=(HashMap)chargesLst.get(i);
                          String idn=(String)dtl.get("idn");
                          String dsc=(String)dtl.get("dsc");
                          String typcharge=(String)dtl.get("typ");
                          String fctr=(String)dtl.get("fctr");
                          String fun=(String)dtl.get("fun");
                          String autoopt=util.nvl((String)dtl.get("autoopt"));
                          String autooptional= util.nvl((String)srchForm.getValue(typcharge+"_AUTOOPT"));  
                          String calcdis= util.nvl((String)srchForm.getValue(typcharge+"_save"),"0");  
                          String vlu= util.nvl((String)srchForm.getValue("vluamt"));
                          String exhRte=util.nvl(String.valueOf(info.getXrt()),"1");
                          String extrarmk= util.nvl((String)srchForm.getValue(typcharge+"_rmksave")); 
                              if(!vlu.equals("") && !vlu.equals("0")  && !calcdis.equals("NaN")  && !vlu.equals("NaN")){
                          if((!calcdis.equals("") && !calcdis.equals("0")) || (autoopt.equals("OPT"))){
                          String insertQ="Insert Into Trns_Charges (trns_idn, ref_typ, ref_idn,charges_idn,amt,amt_usd,charges,CHARGES_PCT,net_usd,rmk,stt,flg)\n" + 
                          "VALUES (TRNS_CHARGES_SEQ.nextval, 'MEMO', ?,?,?,?,?,?,?,?,'A',?)";   
                          ary=new ArrayList();
                          ary.add(String.valueOf(memoId));
                          ary.add(idn);
                          ary.add(vlu);
                          float amt_usd=Float.parseFloat(vlu)/Float.parseFloat(exhRte);
                          ary.add(String.valueOf(amt_usd));
                          ary.add(calcdis);
                          ary.add(calcdis);
                          float net_usd=amt_usd+Float.parseFloat(calcdis);
                          ary.add(String.valueOf(net_usd));
                          ary.add(extrarmk);
                          ary.add(autooptional);
                          ct = db.execUpd("Insert", insertQ, ary);
                          }
                          }
                          }
                          }       
                  }
              ary = new ArrayList();
              ary.add(Integer.toString(memoId));
              ct2 = db.execCall("jan_qty", "memo_pkg.cal_fnl_quot(?)", ary);     
              session.setAttribute("memoId", String.valueOf(memoId));
              
           //}
  //           if(rapsync.equals("Y"))
  //                new SyncOnRap(cnt).start();
               util.updAccessLog(req,res,"Create Memo", "memo id "+memoId);
                  if(!web_block.equals("NA") && !memoTyp.equals("Z")){
                    if(memoLst!=null && memoLst.size()>0){
                      for(int i=0;i<memoLst.size();i++){
                        String mstkIdn = (String)memoLst.get(i);
                          ary = new ArrayList();
                          ary.add(mstkIdn);
                          ary.add("WEB_BLOCK");
                          ary.add(web_block);
                          ary.add("C");
                          ary.add("1");
                          String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pPrpTyp => ?, pgrp => ? )";
                          ct = db.execCall("stockUpd",stockUpd, ary);
                      }}
                  }
                  
                  if(!symbol.equals("")){
                    if(memoLst!=null && memoLst.size()>0){
                      for(int i=0;i<memoLst.size();i++){
                        String mstkIdn = (String)memoLst.get(i);
                          ary = new ArrayList();
                          ary.add(mstkIdn);
                          ary.add("SYMBOL");
                          ary.add(symbol);
                          ary.add("C");
                          ary.add("1");
                          String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pPrpTyp => ?, pgrp => ? )";
                          ct = db.execCall("stockUpd",stockUpd, ary);
                      }}
                  }
              }
              String noteperson = util.nvl((String)srchForm.getValue("noteperson"));
              if(!noteperson.equals("")){
              ary=new ArrayList();
              ary.add(noteperson);
              ary.add(String.valueOf(memoId));
              String updateFlg = "update mjan set NOTE_PERSON=? where idn=?";
              int cntmj = db.execUpd("update mjan", updateFlg, ary);
              }
          }else{
              
              ArrayList memoList= (query.getMemoType(req,res) == null)?new ArrayList():(ArrayList)query.getMemoType(req,res);
  //            srchForm.setValue("memoList", memoList);
              srchForm.setMemoList(memoList);
              req.setAttribute("memoMsg","Memo Creation failed......");
            util.updAccessLog(req,res,"Create Memo", "Create Memo filled");
              finalizeObject(db, util);
             return load(am, form, req, res);
          }
          
       
          session.setAttribute("SrchTyp", util.nvl(srchForm.getSrchTyp()));
          int ct = db.execUpd("delete gt","Delete from gt_srch_rslt", new ArrayList());
          
          if(memoTyp.equals("IAP")|| memoTyp.equals("EAP")|| memoTyp.equals("WAP") || memoTyp.equals("BCAP") || memoTyp.equals("LAP") || memoTyp.equals("MAP") || memoTyp.equals("SAP")){
            String isDLV = util.nvl((String)srchForm.getValue("isDLV"));
            String salstt ="SL";
            if(isDLV.equals("No"))
                salstt="LS";
                if(!salConfirm.equals("")){
                  
                  ary = new ArrayList();
                  ary.add(Integer.toString(memoId));
                  ary.add(salstt);
                  ArrayList out = new ArrayList();
                  out.add("I");
                 CallableStatement cst  = db.execCall("sale Confirm", "SL_PKG.Gen_Hdr_Frm_Memo(pMemoIdn =>?, pTyp => ?, pIdn => ? )", ary , out);
               int saleIdn = cst.getInt(ary.size()+1);
                cst.close();
                cst=null;
                  req.setAttribute("SLMSG", "Packets get Sale  with sale Id" +saleIdn);
                  req.setAttribute("saleId", String.valueOf(saleIdn));
                  // return am.findForward("memoSale");
                  req.setAttribute("performaLink","Y");
                GtMgrReset(req);
                  if(cnt.equals("smf"))
                  return am.findForward("memoAppSH");
                  else
                  return am.findForward("memoApp");
                  
              }else if((!salConfirmix.equals("") || !submitsal.equals("")) && isMix.equals("MIX")){
              ArrayList salstkidnLst=new ArrayList();
              int appSlIdn = 0;
               extRte = info.getXrt();
              String invaddridn="";
              ary=new ArrayList();
              ary.add(String.valueOf(memoId));
              String updateFlg = "update mjan set mail_stt='SND' where idn=?";
              int cntmj = db.execUpd("update mjan", updateFlg, ary);
              String salQ="select mstk_idn from jandtl where idn=? ";
              ResultSet rs = db.execSql("salQ", salQ, ary);
              while (rs.next()) {
              salstkidnLst.add(util.nvl((String)rs.getString("mstk_idn")));
              }
                  rs.close();
              ary=new ArrayList();
              String trmQ="Select B.Addr_Idn\n" + 
              "From Mjan A,Maddr B\n" + 
              "where a.nme_idn=b.nme_idn and a.idn=? and rownum=1";
              ary.add(String.valueOf(memoId));
              rs = db.execSql("trmQ", trmQ, ary);
              while (rs.next()) {
              invaddridn=util.nvl((String)rs.getString("Addr_Idn"));
              }
                  rs.close();
              if(salstkidnLst.size()>0){
                  for(int i=0;i<salstkidnLst.size();i++){
                  String mstkIdn = (String)salstkidnLst.get(i);
                  if (appSlIdn == 0) {
                  ary = new ArrayList();
                  ary.add(String.valueOf(info.getByrId()));
                  ary.add(String.valueOf(info.getRlnId()));
                  ary.add(String.valueOf(info.getRlnId()));
                  ary.add(String.valueOf(info.getByrId()));
                  ary.add(invaddridn);
                  ary.add(salstt);
                  ary.add("IS");
                  ary.add(Integer.toString(memoId));
                  ary.add("NN");
                  ary.add(String.valueOf(extRte));
                  ArrayList out = new ArrayList();
                  out.add("I");
                  CallableStatement cst = null;

                  cst = db.execCall(
                  "MKE_HDR ",
                  "sl_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pTrmsIdn => ?, pInvNmeIdn => ?, pInvAddrIdn => ?, pTyp => ?, pStt => ?, pFrmId => ? , pFlg => ? ,  pExhRte => ? ,pIdn => ? )", ary, out);
                  appSlIdn = cst.getInt(ary.size()+1);
                    cst.close();
                    cst=null;
                  }
                  if (appSlIdn != 0) {
                  ary = new ArrayList();
                  ary.add("SL");
                  ary.add(mstkIdn);
                  ary.add(String.valueOf(memoId));

                  int upJan = db.execUpd("updateJAN", "update jandtl set stt=? where mstk_idn=? and idn= ? ", ary);
                      String rmk = util.nvl(req.getParameter("rmk_"+mstkIdn));
                  ary = new ArrayList();
                  ary.add(String.valueOf(appSlIdn));
                  ary.add(String.valueOf(memoId));
                  ary.add(mstkIdn);
                  ary.add(salstt);
                  ary.add(rmk);
                  int SalPkt = db.execCall("sale from memo",
                  "sl_pkg.Mix_Sal_Pkt(pIdn => ?, pMemoIdn => ? , pStkIdn => ? , pStt => ? , pRmk => ?)",ary);
                  }
                  }
                  if (appSlIdn != 0) {
                  String updJanQty = " jan_qty(?) ";

                  ary = new ArrayList();
                  ary.add(String.valueOf(memoId));
                  int ct1 = db.execCall("JanQty", updJanQty, ary);
                  ary = new ArrayList();
                  ary.add(String.valueOf(appSlIdn));
                  int ctq = db.execCall("sl_pkg.upd_qty", "sl_pkg.UPD_QTY(pIdn => ?)", ary);
                  }
                  if(!salConfirmix.equals("")){
                      String net = util.nvl(req.getParameter("net"),"NO");
                      String blind = util.nvl(req.getParameter("blind"),"NO");
                      ary = new ArrayList();
                      ary.add(net);
                      ary.add(String.valueOf(appSlIdn));
                      cntmj = db.execUpd("update msal", "update msal set net_yn=? where idn=?", ary);
                      ary = new ArrayList();
                      ary.add(blind);
                      ary.add(String.valueOf(appSlIdn));
                      cntmj = db.execUpd("update msal", "update msal set blind_yn=? where idn=?", ary);
                      ary = new ArrayList();
                      ary.add(util.nvl(req.getParameter("thruPer")));
                      ary.add(String.valueOf(appSlIdn));
                      cntmj = db.execUpd("update msal", "update msal set thru=? where idn=?", ary);
                  }
              }
                  req.setAttribute("SLMSG", "Packets get Sale  with sale Id" +appSlIdn);
                  req.setAttribute("memoId", String.valueOf(appSlIdn));
                util.updAccessLog(req,res,"Create Memo", "Sale id "+appSlIdn);
                GtMgrReset(req);
                
                  if(!salConfirmix.equals("")){
                  req.setAttribute("APP","Y");
                      finalizeObject(db, util);
                  return am.findForward("saleDelivery");
                  }
                      
                  if(cnt.equals("smf"))
                  return am.findForward("memoAppSalSH");
                  else
                  return am.findForward("memoAppSal");   
              }else{
              req.setAttribute("memoId", String.valueOf(memoId));
              req.setAttribute("APP","Y");
                  if(symbol.equals("INV")){
                      ary = new ArrayList();
                      ary.add(String.valueOf(memoId));
                      ct = db.execCall("updateQ","update mjan set mail_stt='REJ' where idn=?", ary);
                  }else{
                  MailAction mailObj = new MailAction();
                  mailObj.sendAppMemoMail(memoId, req, res);
                  }
                  if(!externalLnk.equals("")){
                  String usr=util.nvl((String)dbinfo.get("DFLTUSR"));;
                  ary = new ArrayList();
                  ArrayList appPktLst = new ArrayList();
                  String vnmQ="select b.vnm from jandtl a,mstk b \n" + 
                  "Where A.Mstk_Idn=B.Idn And a.idn=?";
                  ary.add(String.valueOf(memoId));
                    ArrayList  outLst = db.execSqlLst("vnmQ", vnmQ, ary);
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet  rs = (ResultSet)outLst.get(1);
                    
                  while (rs.next()) {
                      appPktLst.add(util.nvl((String)rs.getString("vnm")));
                  }
                      rs.close();
                      pst.close();
                  String vnm = appPktLst.toString();
                  vnm = vnm.replaceAll("\\[", "");
                  vnm = vnm.replaceAll("\\]", "").replaceAll(" ", "");
                  ary = new ArrayList();
                  ary.add(String.valueOf(info.getRlnId()));
                  String usrQ="select usr from web_usrs where rel_idn=?";
                  rs = db.execSql("usrQ", usrQ, ary);
                  while (rs.next()) {
                  usr=util.nvl(rs.getString("usr"));    
                  }
                      rs.close();
                  externalLnk=externalLnk.replaceAll("USR", usr);
                  externalLnk=externalLnk.replaceAll("VNM", vnm);
                  req.setAttribute("externalLnk",externalLnk);
                  }
                GtMgrReset(req);
                    
                if(!priceChange.equals(""))
                return am.findForward("price");
                if(cnt.equals("smf"))
                  return am.findForward("memoAppSH");
                else  if(cnt.equalsIgnoreCase("PM") || cnt.equalsIgnoreCase("ASHA")){
                    session.setAttribute("memoId", String.valueOf(memoId));
                    return am.findForward("memo");
                }else
                  return am.findForward("memoApp");
              }
              
          }
        
          GtMgrReset(req);
          if(!priceChange.equals(""))
          return am.findForward("price");
          
          if (srchForm.getValue("memortn")!=null && (memoTyp.equals("I") || memoTyp.equals("E"))){
          req.setAttribute("APP", "Y");
          req.setAttribute("memoId", String.valueOf(memoId));
              finalizeObject(db, util);
          return am.findForward("memortrn");
          }
              finalizeObject(db, util);  
          return am.findForward("memo");
          }
          }
      }


    public ActionForward loadSrchPrp(ActionMapping mapping, ActionForm form,
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
                  return mapping.findForward(rtnPg);
              }else{
              ArrayList suggBoxLst = info.getSuggBoxLst();
                if(suggBoxLst==null)
               suggBoxLst = new ArrayList();
              SearchForm srchForm ;
              SearchQuery query = new SearchQuery();
              srchForm = (SearchForm)form;
              boolean setsrchstt=false;
              String sz = util.nvl((String)info.getDmbsInfoLst().get("SIZE"));
              if(sz.equals(""))
                  sz="SIZE";
              String goTo = "srch";
              
    //             if(info.getSrchPrp()==null)
    //                      util.initPrpSrch();
              if(info.getVwPrpLst()==null)
                       util.initSrch(); 
              ArrayList crtwtPrp = (info.getCrtwtPrpLst() == null)?new ArrayList():(ArrayList)info.getCrtwtPrpLst();
              if(crtwtPrp.size() == 0) {
                       util.getcrtwtPrp(sz,req,res);    
              }       
                  
              String pgDef = "SEARCH_RESULT";
              HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
              HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);
              if(pageDtl==null || pageDtl.size()==0){
              pageDtl=new HashMap();
              pageDtl=util.pagedef(pgDef);
              allPageDtl.put(pgDef,pageDtl);
              }
              info.setPageDetails(allPageDtl);
               ArrayList   pageList= ((ArrayList)pageDtl.get("REFINE") == null)?new ArrayList():(ArrayList)pageDtl.get("REFINE");
              
              ArrayList ary = new ArrayList();
              ArrayList selectedPrp = new ArrayList();
              Boolean reSrch = Boolean.valueOf(false);
              Boolean modify = Boolean.valueOf(false);
              if (srchForm.getValue("refine_srch")!= null){
                  reSrch = Boolean.valueOf(true);
                  util.updAccessLog(req,res,"Search Page", "Refine Search");
              }
              if (req.getParameter("modify") != null) {
                  modify = Boolean.valueOf(true);
                  goTo = "load";
                  util.setMdl("MEMOSRCH");
                  util.updAccessLog(req,res,"Search Page", "Modify Search");
              
              srchForm.reset();
              String srchTyp=info.getSrchBaseOn();
              if(!srchTyp.equals("NA") && req.getParameter("modify")!=null){
              srchForm.setValue("srchRef",srchTyp);
              String srchval=info.getSrchBaseOnLst();
              srchval=srchval.replaceAll("'", "");
              srchForm.setValue("srchRefVal",srchval);
              srchForm.setValue("PRI_CHNG_TYP",info.getPri_chng_typ());
              srchForm.setValue("PRI_CHNG_VAL",info.getPri_chng_val());
              ArrayList srchsttLstArr = (info.getSrchsttLst() == null)?new ArrayList():(ArrayList)info.getSrchsttLst();
              for(int i=0;i<srchsttLstArr.size();i++){
              srchForm.setValue("SRCHSTT_"+srchsttLstArr.get(i),srchsttLstArr.get(i));
              }
              req.setAttribute("setDisplayFromMod","Y");
              info.setSrchId(0);
              info.setRefSrchId(0);
              }
              int srchIDN = info.getSrchId();
              if (reSrch)
                  srchIDN = info.getRefSrchId();
              if(req.getParameter("srchId")!=null){
                  srchIDN= Integer.parseInt(req.getParameter("srchId"));
                  goTo = "load";
                  util.setMdl("MEMOSRCH");
                  srchForm.setValue("oldsrchid",srchIDN);
                  String getdtlQ=" select  c.nme_idn , a.rln_id,get_xrt(b.cur) xrt,c.nme,nvl(c.emp_idn,0) emp_idn \n" + 
                  "                  from msrch a,nmerel b ,nme_v c \n" + 
                  "                  where a.srch_id=?\n" + 
                  "                  and a.rln_id=b.nmerel_idn\n" + 
                  "                  and b.nme_idn=c.nme_idn";
                    ary = new ArrayList();
                    ary.add(String.valueOf(srchIDN));
                    ArrayList  outLst = db.execSqlLst("msrch", getdtlQ, ary); 
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet  rs = (ResultSet)outLst.get(1);
                    
                    if(rs.next()){
                    info.setSrchPryID(rs.getInt("nme_idn"));
                    info.setRlnId(rs.getInt("rln_id"));
                    info.setXrt(rs.getDouble("xrt"));
                    info.setByrNm(rs.getString("nme"));
                    info.setSrchByrId(rs.getInt("emp_idn"));
                    }
                  rs.close();
                  pst.close();
              }
              if(req.getAttribute("srchIdn")!=null){
                  int dmdIDN=
     Integer.parseInt((String)req.getAttribute("dmdIdn"));
                  String dmdDsc = (String)req.getAttribute("DmdDsc");
                  srchIDN=
     Integer.parseInt((String)req.getAttribute("srchIdn"));
                  goTo = "load";
                  util.setMdl("MEMOSRCH");
                  srchForm.setValue("oldDmdId",dmdIDN);
                  srchForm.setValue("dmdDsc", dmdDsc);
              }
               
              String getSrchTyp =" select cval from srch_addon where srch_id = ? and cprp='STT'";
              ArrayList arys = new ArrayList();
              arys.add(String.valueOf(srchIDN));
                ArrayList  outLst = db.execSqlLst(" srchId= :" + srchIDN, getSrchTyp, arys);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet  rs1 = (ResultSet)outLst.get(1);
              while (rs1.next()) {
              String sttSrch = util.nvl(rs1.getString("cval"));
              srchForm.setValue("SRCHSTT_"+sttSrch,sttSrch);
              setsrchstt=true; 
              }
              rs1.close();
              pst.close();
              if(srchTyp.equals("NA")){
              getSrchTyp =" select PRI_CHNG_TYP,PRI_CHNG_VAL from msrch where srch_id = ?";
              arys = new ArrayList();
              arys.add(String.valueOf(srchIDN));
                 outLst = db.execSqlLst(" srchId= :" + srchIDN, getSrchTyp, arys);
                 pst = (PreparedStatement)outLst.get(0);
                rs1 = (ResultSet)outLst.get(1);
              while (rs1.next()) {
                      srchForm.setValue("PRI_CHNG_TYP",util.nvl(rs1.getString("PRI_CHNG_TYP")));
                      srchForm.setValue("PRI_CHNG_VAL",util.nvl(rs1.getString("PRI_CHNG_VAL")));
              }
              rs1.close();
              pst.close();
              }
              if(!setsrchstt){
                  srchForm.setValue("SRCHSTT_MKAV","MKAV");
              }
                  
              HashMap vals = new HashMap();
              ArrayList srchList = new ArrayList();
              HashMap prp = info.getPrp();
              ary = new ArrayList();
              String getDtls =
                         " select a.mprp ,decode(b.dta_typ, 'T',a.txt, a.vfr) vfr, decode(b.dta_typ, 'T',a.txt, a.vto) vto , decode(b.dta_typ, 'C', a.sfr , 'D', a.sfr , decode(a.mprp, 'CRTWT', to_char(trunc(nfr,2),'9990.00'), to_char(nfr))) sfr  " +
                         "   , decode(b.dta_typ, 'C', a.sto, 'D', a.sto ,"+ 
                         "decode(a.mprp, 'CRTWT', to_char(trunc(nto,2),'9990.00'),  to_char(nto))) sto , b.dta_typ " + " from srch_dtl a, mprp b where a.mprp = b.prp and a.srch_id = ? ";
                      ary = new ArrayList();
              ary.add(String.valueOf(srchIDN));

                outLst = db.execSqlLst(" Dmd Id :" + srchIDN, getDtls, ary);
                pst = (PreparedStatement)outLst.get(0);
              ResultSet  rs = (ResultSet)outLst.get(1);
              while (rs.next()) {
                  String mprp = util.nvl(rs.getString("mprp"));
                  String sfr = util.nvl(rs.getString("sfr"));
                  String sto = util.nvl(rs.getString("sto"));
                  String vfr = util.nvl(rs.getString("vfr"));
                  String vto = util.nvl(rs.getString("vto"));
                  if(!selectedPrp.contains(mprp))
                      selectedPrp.add(mprp);
                  if (!reSrch)
                      srchList.add(mprp);
                  if (rs.getString("dta_typ").equals("C")) {
                      String dspTxt ="";
                      ArrayList prpSrt = (ArrayList)prp.get(mprp + "S");
                      ArrayList prpVal = (ArrayList)prp.get(mprp + "V");
                      int startAt = prpVal.indexOf(vfr);
                      if(startAt==-1)
                          startAt = prpSrt.indexOf(sfr);
                      int endAt = prpVal.indexOf(vto);
                      if(endAt==-1)
                          endAt = prpSrt.indexOf(sto);
                      List rngVals = prpSrt.subList(startAt, endAt+1);

                      for (int i = 0; i < rngVals.size(); i++) {
                          String lSrt = (String)rngVals.get(i);
                          String lVal =
     (String)prpVal.get(prpSrt.indexOf(lSrt));
                          if (!reSrch)
                              vals.put(mprp + "_" + lVal, lSrt);
                          srchForm.setValue(mprp + "_" + lVal, lVal);
                          dspTxt += " , " + lVal;
                      }
                      if(dspTxt.length() > 3)
                        srchForm.setValue("MTXT_" + mprp, dspTxt.substring(3).replaceAll("NA", ""));

                  }
                  if (rs.getString("dta_typ").equals("D")) {
                      sfr = sfr.substring(6, 8)+"-"+sfr.substring(4, 6)+"-"+sfr.substring(0, 4);
                      sto = sto.substring(6, 8)+"-"+sto.substring(4, 6)+"-"+sto.substring(0, 4);
                  }
                 
                  if(mprp.equals("CRTWT")){
                      srchForm.setValue("wt_fr_c", sfr);
                      srchForm.setValue("wt_to_c", sto);
                      srchForm.setValue("MTXT_CRTWT", sfr+"-"+sto);
                  }else{
                  srchForm.setValue(mprp + "_1", sfr);
                  srchForm.setValue(mprp + "_2", sto);
                  }

              }
              rs.close();
              pst.close();
              ArrayList suggBoxArrList = new ArrayList();
             
              String getSubDtls =
             " select a1.mprp, a1.sfr, a1.sto, a1.vfr, b1.srt psrt, b1.val pval,c1.dta_typ typ "+
             " from (select a.mprp, a.sfr ,a.vfr, a.sto, decode(mprp, 'CRTWT', get_sz(sfr), a.vfr) sz, "+
             " decode(a.mprp, 'CRTWT', '"+sz+"', a.mprp) prp from srch_dtl_sub a where a.srch_id = ?) a1, prp b1 ,mprp c1 "+
             " where a1.prp = b1.mprp and a1.sz = decode(c1.dta_typ, 'T',a1.sz, b1.val) and b1.mprp=c1.prp  order by a1.mprp ";

              ary = new ArrayList();
              ary.add(String.valueOf(srchIDN));

              String dspTxt = "", lPrp = "";
                outLst = db.execSqlLst(" SubSrch :" + srchIDN, getSubDtls, ary);
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
              while (rs.next()) {
                  HashMap suggBoxMap=new HashMap();
                  String mprp = util.nvl(rs.getString("mprp"));
                  String sfr = util.nvl(rs.getString("sfr"));
                  String sto = util.nvl(rs.getString("sto"));
                  String vfr = util.nvl(rs.getString("vfr"));
                  String psrt = util.nvl(rs.getString("psrt"));
                  String pval = util.nvl(rs.getString("pval"));
                  String typ = util.nvl(rs.getString("typ"));
                  if(suggBoxLst.contains(mprp)){
                          suggBoxMap.put(mprp+"_V",vfr);
                          suggBoxMap.put(mprp+"_K",sfr);
                          suggBoxArrList.add(suggBoxMap);
                      }
                
                  if (!reSrch)
                      srchList.add(mprp);
                  if (lPrp.equals(""))
                      lPrp = mprp;

                  if (lPrp.equals(mprp)) {
                 if (mprp.equals("CRTWT"))
                       dspTxt += " , " + pval;
                   else
                       dspTxt += " , " + vfr;
                   }else {
                   srchForm.setValue("MTXT_" + lPrp, dspTxt.substring(3).replaceAll("NA", ""));
                       if (mprp.equals("CRTWT"))
                             dspTxt = " , " + pval;
                        else
                            dspTxt = " , " + vfr;
                    lPrp = mprp;
                   }
                  if (!reSrch) {
                    if (mprp.equals("CRTWT")) {
                      vals.put(mprp + "_" + psrt, pval);
                      vals.put(mprp + "_1_" + psrt, sfr);
                      vals.put(mprp + "_2_" + psrt, sto);
                    }
                    else {
                      vals.put(mprp + "_" + vfr, vfr);
                      vals.put(mprp + "_1_" + psrt, sfr);
                      vals.put(mprp + "_2_" + psrt, sto);
                    }
                  }
                  if(typ.equals("T")){
                      String frm = util.nvl((String)srchForm.getValue(mprp+"_1"));
                      if(frm.equals(""))
                          frm=vfr;
                      else
                        frm=frm+","+vfr;
                      srchForm.setValue(mprp + "_1",frm);
                  }else
                  if (mprp.equals("CRTWT")) {
                      srchForm.setValue(mprp + "_" + psrt, pval);
                      srchForm.setValue(mprp + "_1_" + psrt, sfr);
                      srchForm.setValue(mprp + "_2_" + psrt, sto);
                      srchForm.setValue("wt_fr_c","");
                      srchForm.setValue("wt_to_c", "");
                      srchForm.setValue("MTXT_" + lPrp,"");
                  } else
                      srchForm.setValue(mprp + "_" + vfr, vfr);
              }
              rs.close();
              pst.close();
              req.setAttribute("suggBoxArrList", suggBoxArrList);
              if(dspTxt.length() > 3)
                srchForm.setValue("MTXT_" + lPrp, dspTxt.substring(3).replaceAll("NA", ""));

              srchForm.setValue("oldsrchId", srchIDN);
              if (!reSrch) {
                  info.setSrchValMap(vals);
                  info.setSrchMPrpList(srchList);
                  ArrayList byrLst= query.getByrList(req,res);
                  srchForm.setByrList(byrLst); //MAYURDONE
     //            ArrayList partyLst = query.getPartyList(req,res);
     //            srchForm.setPartyList(partyLst);

                  ArrayList trmList = query.getTerm(req,res, info.getSrchPryID());
                  srchForm.setTrmList(trmList);
                  ArrayList dmdList = query.getDmdList(req,res, info.getRlnId());
                  srchForm.setDmdList(dmdList);
                  req.setAttribute("dmdList", dmdList);
                  ArrayList webReqList = query.getWebRequest(req, res, info.getRlnId());
                  req.setAttribute("webReqList", webReqList);
                  ArrayList hrReqList = query.getHrRequest(req, res, info.getDf_rln_id());
                  req.setAttribute("hrReqList", hrReqList);
                ArrayList grpNmeList = query.getGrpList(req, res, info.getDf_rln_id());
                 req.setAttribute("grpNmeList", grpNmeList);
                ArrayList contactList = query.getContactList(req, res, info.getDf_nme_id());
                req.setAttribute("contDtlList", contactList);
                  ArrayList memoList = null;
                  if(goTo.equals("load")){
                      query.getSrchType(req,res,srchForm,"N");
                  }else{
                      memoList = query.getMemoType(req,res);
     //              srchForm.setValue("memoList", memoList);
                    srchForm.setMemoList(memoList);
                  }

                  srchForm.setByrId(String.valueOf(info.getSrchByrId()));
                  srchForm.setByrRln(String.valueOf(info.getRlnId()));
                  srchForm.setParty(String.valueOf(info.getSrchPryID()));
                  srchForm.setValue("xrt", info.getXrt());
                  srchForm.setValue("partytext", info.getByrNm());

              }
              if(util.nvl(info.getIsEx()).equals("is2Ex")){
                srchForm.setValue("is2Ex", info.getIsEx());
              }
              if(util.nvl(info.getIsEx()).equals("is3Ex")){
                srchForm.setValue("is3Ex", info.getIsEx());
              }
              if(util.nvl(info.getIsEx()).equals("is3VG")){
                srchForm.setValue("is3VG", info.getIsEx());
              }
              if(util.nvl(info.getIsEx()).equals("is3VG_UP")){
                    srchForm.setValue("is3VG_UP", info.getIsEx());
              }
              req.setAttribute("selectedPrp", selectedPrp);
                  finalizeObject(db, util);
              }
              return mapping.findForward(goTo);
              }
          }



    public ActionForward loadFav(ActionMapping mapping,
    ActionForm form,
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
            return mapping.findForward(rtnPg);   
        }else{
            SearchForm srchForm ;
            SearchQuery query = new SearchQuery();
         srchForm =(SearchForm)form;
        util.updAccessLog(req,res,"Load Fav", "Load Fav");
        String favIDN = req.getParameter("favId");
        String rlnId = req.getParameter("rlnId");
        HashMap vals = new HashMap();
        ArrayList ary = new ArrayList();
        String getDtls = 
           "Select b.dsc dsc, a.mprp mprp, a.sfr sfr, a.sto sto "+
            "from favsrch_dtl a, mfavsrch b "+
            " where a.fav_id = b.fav_id and  a.fav_id=? and b.tdt is null  ";
        ary = new ArrayList();
       
        ary.add(favIDN);
          ArrayList  outLst = db.execSqlLst(" Dmd Id :"+ favIDN, getDtls, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);

        while(rs.next()){
          
            String mprp =util.nvl(rs.getString("mprp")) ;
            String sfr = util.nvl( rs.getString("sfr"));
            String sto = util.nvl(rs.getString("sto"));
            srchForm.setValue(mprp+"_1", sfr);
            srchForm.setValue(mprp+"_2", sto);
        }
       
        rs.close();
            pst.close();
        info.setFavMaps(vals);
        srchForm.setByrRln(rlnId);
        util.setMdl("MEMOSRCH");
            finalizeObject(db, util);
        return mapping.findForward("loadFav");
        }
    }
    
    
    public ActionForward loadDmd(ActionMapping mapping,
    ActionForm form,
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
            return mapping.findForward(rtnPg);  
        }else{
            SearchForm srchForm ;
            SearchQuery query = new SearchQuery();
    ArrayList ary = null;
    if(info.getVwPrpLst()==null)
    util.initSrch();
//    if(info.getSrchPrp()==null)
//    util.initPrpSrch();
    HashMap dbinfo = info.getDmbsInfoLst();
    String sz = (String)dbinfo.get("SIZE");
    util.updAccessLog(req,res,"Load Demand", "Load Demand");
    String dmdIDN = req.getParameter("dmd");
    int srchId = query.loadDmd(req,res, Integer.valueOf(dmdIDN));
    if(srchId!=0){
    ary = new ArrayList();
    ary.add(dmdIDN);
//      ResultSet rs = db.execSql("dmdDsc", "select a.dsc , a.name_id , a.rln_id,get_xrt(b.cur) xrt,byr.get_nm(a.name_id) nme from mdmd a,nmerel b where a.dmd_id=? and a.rln_id=nmerel_idn", ary); ///Mayur
    String getdtlQ=" select a.dsc , a.name_id , a.rln_id,get_xrt(b.cur) xrt,c.nme,nvl(c.emp_idn,0) emp_idn\n" + 
    " from mdmd a,nmerel b ,nme_v c\n" + 
    " where a.dmd_id=? \n" + 
    " and a.rln_id=b.nmerel_idn\n" + 
    " and b.nme_idn=c.nme_idn";
        ArrayList  outLst = db.execSqlLst("dmdDsc", getdtlQ, ary); 
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet  rs = (ResultSet)outLst.get(1);

      if(rs.next()){

      req.setAttribute("DmdDsc",util.nvl(rs.getString("dsc")));
      info.setSrchPryID(rs.getInt("name_id"));
      info.setRlnId(rs.getInt("rln_id"));
      info.setXrt(rs.getDouble("xrt"));
      info.setByrNm(rs.getString("nme"));
      info.setSrchByrId(rs.getInt("emp_idn"));
      }

    rs.close();
    pst.close();
    req.setAttribute("srchIdn",String.valueOf(srchId));
    req.setAttribute("dmdIdn",String.valueOf(dmdIDN));
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("SEARCH_RESULT");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("SEARCH_RESULT");
        allPageDtl.put("SEARCH_RESULT",pageDtl);
        }
        info.setPageDetails(allPageDtl);
//        ArrayList rfiddevice = ((ArrayList)info.getRfiddevice() == null)?new ArrayList():(ArrayList)info.getRfiddevice();
//        if(rfiddevice.size() == 0) {
//        util.rfidDevice();    
//        }
        util.getcrtwtPrp(sz,req,res);
    }
        info.setIsMix("");
            finalizeObject(db, util);
    return loadSrchPrp(mapping, form, req, res);
        }
    }
    
    public ActionForward BackSrch(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            SearchForm srchForm ;
            SearchQuery query = new SearchQuery();
        srchForm =(SearchForm)form;
        srchForm.reset();
        ArrayList memoList= (query.getSrchType(req,res,srchForm,"Y") == null)?new ArrayList():(ArrayList)query.getSrchType(req,res,srchForm,"Y");
        ArrayList termsList = query.getTerm(req,res, info.getDf_nme_id());
        srchForm.setTrmList(termsList);
        ArrayList dmdList = query.getDmdList(req,res, info.getDf_rln_id());
        srchForm.setDmdList(dmdList);
        req.setAttribute("dmdList", dmdList);
        ArrayList webReqList = query.getWebRequest(req, res, info.getDf_rln_id());
        req.setAttribute("webReqList", webReqList);
            ArrayList hrReqList = query.getHrRequest(req, res, info.getDf_rln_id());
            req.setAttribute("hrReqList", hrReqList);
      ArrayList grpNmeList = query.getGrpList(req, res, info.getDf_rln_id());
       req.setAttribute("grpNmeList", grpNmeList);
          ArrayList contactList = query.getContactList(req, res, info.getDf_nme_id());
          req.setAttribute("contDtlList", contactList);
        srchForm.setByrId("0");
        srchForm.setParty(String.valueOf(info.getDf_nme_id()));
        srchForm.setByrRln(String.valueOf(info.getDf_rln_id()));
        srchForm.setValue("partytext", info.getByrNm());
        srchForm.setIsEx("");
            util.setMdl("MEMOSRCH");
            finalizeObject(db, util);
        return am.findForward("load");
        }
    }
    public ActionForward dmdRslt(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            SearchForm srchForm ;
            SearchQuery query = new SearchQuery();
    srchForm =(SearchForm)form;
    ArrayList srchIdnList = new ArrayList();
    int ct = db.execUpd("delete gtSrch", "DELETE FROM gt_srch_rslt ", new ArrayList());
    ct = db.execUpd("delete gtSrch", "DELETE FROM gt_srch_multi ", new ArrayList());
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            String trDis="",loyalty="";
            if(cnt.equals("xljf")){
            trDis=util.nvl(util.gettradeDis(String.valueOf(info.getRlnId())));
            }
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("SEARCH_RESULT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("SEARCH_RESULT");
            allPageDtl.put("SEARCH_RESULT",pageDtl);
            }
            ArrayList  pageList= ((ArrayList)pageDtl.get("LOYALTY") == null)?new ArrayList():(ArrayList)pageDtl.get("LOYALTY");
            if(pageList!=null && pageList.size() >0){
             srchForm.setValue("LOYALTY", "YES");
              ArrayList loyaltyLst=util.getloyalty(info.getSrchPryID());
              srchForm.setValue("loyCtg", util.nvl((String)loyaltyLst.get(0)));
              srchForm.setValue("loyPct", util.nvl((String)loyaltyLst.get(1)));
              srchForm.setValue("loyDiff", util.nvl((String)loyaltyLst.get(2)));
              srchForm.setValue("loyVlu", util.nvl((String)loyaltyLst.get(3)));
                srchForm.setValue("mem_dis", util.nvl((String)loyaltyLst.get(4)));
                srchForm.setValue("loyallow", util.nvl((String)loyaltyLst.get(5)));
                srchForm.setValue("memallow", util.nvl((String)loyaltyLst.get(6)));
                srchForm.setValue("ttlSalVlu", util.nvl((String)loyaltyLst.get(7)));
                srchForm.setValue("osamount", util.nvl((String)loyaltyLst.get(8)));
                query.iememov(req,info.getSrchPryID());
            }
    String dmdIdns = util.nvl(req.getParameter("dmdIdn"));
    String byrIdn = util.nvl(req.getParameter("byrIdn"));
    String rlnIdn = util.nvl(req.getParameter("rlnIdn"));
    util.initUsr(rlnIdn);
    ArrayList ary = new ArrayList();
    ary.add(rlnIdn);
          ArrayList  outLst = db.execSqlLst("Exchange Rte", "Select nme_idn , get_xrt(cur) xrt from nmerel where nmerel_idn=?", ary);
   
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);

    if(rs.next()){
    info.setXrt(rs.getDouble(2));
    }
            rs.close();
            pst.close();
    dmdIdns = dmdIdns.replaceFirst(",", "");
    String[] dmdLst = dmdIdns.split(",");
    for(int i=0;i < dmdLst.length ;i++){
    String dmdIdn = dmdLst[i];
    int srchId = query.loadDmd(req,res ,Integer.valueOf(dmdIdn));
    srchIdnList.add(String.valueOf(srchId));
    }
    info.setByrId(Integer.parseInt(byrIdn));
    info.setRlnId(Integer.parseInt(rlnIdn));
    info.setMultiSrchLst(srchIdnList);
    Boolean searchStock = query.searchStock(req,res, "Z","","0");
    info.setMultiSrchLst(new ArrayList());
    info.setSvdmdDis(new ArrayList());
          String lstNme = "SRCHRST_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");

          HashMap searchList = query.SearchResultGT(req, res , "'Z'" , info.getVwPrpLst());
            HashMap dtlMap = new HashMap();

            ArrayList selectstkIdnLst =new ArrayList();
            Set<String> keys = searchList.keySet();
                for(String key: keys){
               selectstkIdnLst.add(key);
                }
            dtlMap.put("selIdnLst",selectstkIdnLst);
            dtlMap.put("pktDtl", searchList);
            dtlMap.put("flg", "Z");
            HashMap totals = util.getTTL(dtlMap);
           HashMap refineDtl = query.refineObj(req, searchList, info.getRefPrpLst());
          HashMap srchDtl = util.useDifferentMap(refineDtl);
          gtMgr.setValue(lstNme,searchList);
          gtMgr.setValue(lstNme+"_TTL",totals );
          gtMgr.setValue("lstNme", lstNme);
          gtMgr.setValue(lstNme+"_SEL",new ArrayList());
          gtMgr.setValue(lstNme+"_SRCHLST", srchDtl);
          gtMgr.setValue(lstNme+"_REF", refineDtl);
          
    info.setMultiSrchLst(new ArrayList());
    info.setSvdmdDis(new ArrayList());
   
    ArrayList memoList= (query.getMemoType(req,res) == null)?new ArrayList():(ArrayList)query.getMemoType(req,res);
//    srchForm.setValue("memoList", memoList);
    srchForm.setMemoList(memoList);
    srchForm.setValue("typ",util.nvl(util.nvl((String)info.getDmbsInfoLst().get(util.nvl((String)info.getIsMix(),"NR")+"_MEMO_TYP")),"Z"));
    session.setAttribute("SrchMemoTyp", util.nvl(util.nvl((String)info.getDmbsInfoLst().get(util.nvl((String)info.getIsMix(),"NR")+"_MEMO_TYP")),"Z"));
    req.setAttribute("memoTyp",util.nvl(util.nvl((String)info.getDmbsInfoLst().get(util.nvl((String)info.getIsMix(),"NR")+"_MEMO_TYP")),"Z"));
    ArrayList expDaysList = query.getExpDay(req, res);
    if(expDaysList !=null && expDaysList.size()>0){
//     srchForm.setExpDayList(expDaysList);
         srchForm.setValue("ExpDayList", expDaysList);
     }
    ArrayList byrCbList = query.getBuyerCabin(req, res);
    if(byrCbList !=null && byrCbList.size()>0){
//    srchForm.setByrCbList(byrCbList);
    srchForm.setValue("ByrCbList", byrCbList);
    }
            String memTyp=util.nvl(util.nvl((String)info.getDmbsInfoLst().get(util.nvl((String)info.getIsMix(),"NR")+"_MEMO_TYP")),"Z");

    ArrayList sttAlwLst = query.MemoSttAlw(req, res, memTyp);
    req.setAttribute("sttAlwLst", sttAlwLst);
//            util.imageDtls();
    
//            util.StockViewLyt();
            HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(info.getSrchPryID()));
            srchForm.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
            srchForm.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
            srchForm.setValue("otherref",util.nvl((String)buyerDtlMap.get("OTHERREF")));
            srchForm.setValue("gstin",util.nvl((String)buyerDtlMap.get("GSTIN")));
            srchForm.setValue("trDis",trDis);
            srchForm.setValue("loyalty",loyalty);
            finalizeObject(db, util);
    return am.findForward("srch");
        }
    }
  public ActionForward srchBack(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          SearchForm srchForm ;
          SearchQuery query = new SearchQuery();
      srchForm =(SearchForm)form;
          HashMap dbinfo = info.getDmbsInfoLst();
          String cnt = (String)dbinfo.get("CNT");
          String trDis="",loyalty="";
          if(cnt.equals("xljf")){
          trDis=util.nvl(util.gettradeDis(String.valueOf(info.getRlnId())));
          }
          HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
          HashMap pageDtl=(HashMap)allPageDtl.get("SEARCH_RESULT");
          if(pageDtl==null || pageDtl.size()==0){
          pageDtl=new HashMap();
          pageDtl=util.pagedef("SEARCH_RESULT");
          allPageDtl.put("SEARCH_RESULT",pageDtl);
          }
              ArrayList  pageList= ((ArrayList)pageDtl.get("LOYALTY") == null)?new ArrayList():(ArrayList)pageDtl.get("LOYALTY");
              if(pageList!=null && pageList.size() >0){
               srchForm.setValue("LOYALTY", "YES");
            ArrayList loyaltyLst=util.getloyalty(info.getSrchPryID());
            srchForm.setValue("loyCtg", util.nvl((String)loyaltyLst.get(0)));
            srchForm.setValue("loyPct", util.nvl((String)loyaltyLst.get(1)));
            srchForm.setValue("loyDiff", util.nvl((String)loyaltyLst.get(2)));
            srchForm.setValue("loyVlu", util.nvl((String)loyaltyLst.get(3)));
              srchForm.setValue("mem_dis", util.nvl((String)loyaltyLst.get(4)));
              srchForm.setValue("loyallow", util.nvl((String)loyaltyLst.get(5)));
              srchForm.setValue("memallow", util.nvl((String)loyaltyLst.get(6)));
              srchForm.setValue("ttlSalVlu", util.nvl((String)loyaltyLst.get(7)));
              query.iememov(req,info.getSrchPryID());
          }
       
         ArrayList memoList= (query.getMemoType(req,res) == null)?new ArrayList():(ArrayList)query.getMemoType(req,res);
  //        srchForm.setValue("memoList", memoList);
      srchForm.setMemoList(memoList);
          srchForm.setValue("typ","Z");
          session.setAttribute("SrchMemoTyp", "Z");
          req.setAttribute("memoTyp","Z");
          ArrayList expDaysList = query.getExpDay(req, res);
          if(expDaysList !=null && expDaysList.size()>0){
          //     srchForm.setExpDayList(expDaysList);
               srchForm.setValue("ExpDayList", expDaysList);
           }
          ArrayList byrCbList = query.getBuyerCabin(req, res);
          if(byrCbList !=null && byrCbList.size()>0){
          //    srchForm.setByrCbList(byrCbList);
          srchForm.setValue("ByrCbList", byrCbList);
          }
//          util.imageDtls();
    
//          util.StockViewLyt();
          String memTyp=util.nvl(util.nvl((String)info.getDmbsInfoLst().get(util.nvl((String)info.getIsMix(),"NR")+"_MEMO_TYP")),"Z");

          ArrayList sttAlwLst = query.MemoSttAlw(req, res,memTyp);
          req.setAttribute("sttAlwLst", sttAlwLst);
          HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(info.getSrchPryID()));
          srchForm.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
          srchForm.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
          srchForm.setValue("otherref",util.nvl((String)buyerDtlMap.get("OTHERREF")));
          srchForm.setValue("gstin",util.nvl((String)buyerDtlMap.get("GSTIN")));
          srchForm.setValue("trDis",trDis);
          srchForm.setValue("loyalty",loyalty);
          finalizeObject(db, util);
      return am.findForward("srch");
      }
  }
    public ActionForward sort(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
                       SearchForm srchForm ;
                       SearchQuery query = new SearchQuery();
                       srchForm =(SearchForm)form;
                       String orderBy = req.getParameter("order").toUpperCase();
                       String orderPrp = req.getParameter("prp");
                       String datafrm = req.getParameter("datafrm");
                       String lstNme = (String)gtMgr.getValue("lstNme");
                       String datafrmlstNme="";
                       if(datafrm.equals("BID"))
                       datafrmlstNme=datafrm+lstNme;
                       else
                       datafrmlstNme=lstNme+datafrm;
               
                       HashMap newPktDtl = (HashMap)gtMgr.getValue(datafrmlstNme);
                       newPktDtl = (HashMap)query.sortByComparator(newPktDtl,orderBy,orderPrp);
                       gtMgr.setValue(datafrmlstNme, newPktDtl);
               
                       if(datafrm.equals("REF"))
                       req.setAttribute("REF", "YES");
               
                       if(datafrm.equals("_MTPR"))
                       req.setAttribute("MTPR", "YES");
               
                       if(datafrm.equals("_SRT"))
                       req.setAttribute("SRT", "YES");
               
//                       util.imageDtls();
//                       util.StockViewLyt();
                       HashMap dbinfo = info.getDmbsInfoLst();
                        String cnt = (String)dbinfo.get("CNT");
                        String trDis="",loyalty="";
                        if(cnt.equals("xljf")){
                        trDis=util.nvl(util.gettradeDis(String.valueOf(info.getRlnId())));
                        }
               HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
               HashMap pageDtl=(HashMap)allPageDtl.get("SEARCH_RESULT");
               if(pageDtl==null || pageDtl.size()==0){
               pageDtl=new HashMap();
               pageDtl=util.pagedef("SEARCH_RESULT");
               allPageDtl.put("SEARCH_RESULT",pageDtl);
               }
               ArrayList  pageList= ((ArrayList)pageDtl.get("LOYALTY") == null)?new ArrayList():(ArrayList)pageDtl.get("LOYALTY");
               if(pageList!=null && pageList.size() >0){
                srchForm.setValue("LOYALTY", "YES");
                          ArrayList loyaltyLst=util.getloyalty(info.getSrchPryID());
                          srchForm.setValue("loyCtg", util.nvl((String)loyaltyLst.get(0)));
                          srchForm.setValue("loyPct", util.nvl((String)loyaltyLst.get(1)));
                          srchForm.setValue("loyDiff", util.nvl((String)loyaltyLst.get(2)));
                          srchForm.setValue("loyVlu", util.nvl((String)loyaltyLst.get(3)));
                            srchForm.setValue("mem_dis", util.nvl((String)loyaltyLst.get(4)));
                            srchForm.setValue("loyallow", util.nvl((String)loyaltyLst.get(5)));
                            srchForm.setValue("memallow", util.nvl((String)loyaltyLst.get(6)));
                            srchForm.setValue("ttlSalVlu", util.nvl((String)loyaltyLst.get(7)));
                            query.iememov(req,info.getSrchPryID());
                        }
                        info.setMultiSrchLst(new ArrayList());
                        info.setSvdmdDis(new ArrayList());
                        ArrayList memoList= (query.getMemoType(req,res) == null)?new ArrayList():(ArrayList)query.getMemoType(req,res);
                        srchForm.setMemoList(memoList);
                        srchForm.setValue("typ","Z");
                        req.setAttribute("memoTyp","Z");
                            HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(info.getSrchPryID()));
                            srchForm.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
                            srchForm.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
                            srchForm.setValue("otherref",util.nvl((String)buyerDtlMap.get("OTHERREF")));
               srchForm.setValue("gstin",util.nvl((String)buyerDtlMap.get("GSTIN")));
                            srchForm.setValue("trDis",trDis);
                            srchForm.setValue("loyalty",loyalty);
                         return am.findForward("srch");
           }
       }
//  public ActionForward sort(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
//         HttpSession session = req.getSession(false);
//         InfoMgr info = (InfoMgr)session.getAttribute("info");
//         DBUtil util = new DBUtil();
//         DBMgr db = new DBMgr();
//         String rtnPg="sucess";
//         if(info!=null){
//         Connection conn=info.getCon();
//         if(conn!=null){
//         db.setCon(info.getCon());
//         util.setDb(db);
//         util.setInfo(info);
//         db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//         util.setLogApplNm(info.getLoApplNm());
//         rtnPg=init(req,res,session,util);
//         }else{
//         rtnPg="connExists";   
//         }
//         }else
//         rtnPg="sessionTO";
//         if(!rtnPg.equals("sucess")){
//             return am.findForward(rtnPg);   
//         }else{
//             SearchForm srchForm ;
//             SearchQuery query = new SearchQuery();
//         srchForm =(SearchForm)form;
//         ArrayList vwPrpLst = new ArrayList();
//             String isMix = util.nvl(info.getIsMix());
//             if(isMix.equals("MIX"))
//             vwPrpLst = info.getMixPrpLst();
//             if(isMix.equals("SMX"))
//             vwPrpLst = info.getSmxPrpLst();
//             else
//             vwPrpLst = info.getVwPrpLst();
//         String order = req.getParameter("order");
//          String prp = req.getParameter("prp");
//         int indexPrp = vwPrpLst.indexOf(prp)+1;
//         String orderBy="";
//         String updGt = " update gt_srch_rslt set flg = 'Z' where flg = 'M' ";
//         db.execUpd("upd gt R", updGt, new ArrayList());
//         if(prp.equals("RAP_DIS")){
//          orderBy ="rap_dis";
//         }else if(indexPrp > 9){
//         orderBy = "srt_0"+indexPrp;
//         }else{
//         orderBy = "srt_00"+indexPrp;
//         }
//         util.updAccessLog(req,res,"Search Page", "Search Sort");
//         HashMap searchList = query.SearchResult(req, res , "'Z'" , vwPrpLst);
//         HashMap totals = util.getTtls(req);
//         String sql=" Select sk1, stk_idn, quot, rap_dis from gt_srch_rslt where flg = 'Z' order by "+orderBy+" "+order+",sk1";
//         ArrayList stkIdnList = new ArrayList();
//         ResultSet rs = db.execSql(" Srch Dsp ", sql, new ArrayList());
//         while(rs.next()){
//         stkIdnList.add(rs.getString("stk_idn"));
//         }
//         rs.close();
//         util.imageDtls();
//  
//         util.StockViewLyt();
//         req.setAttribute("pktStkIdnList", stkIdnList);
//         req.setAttribute("searchList", searchList);
//         req.setAttribute("total",totals);
//         return loadSrchPrp(am, form, req, res);
//         }
//     }
  public ActionForward saveExcel(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          SearchForm srchForm ;
          SearchQuery query = new SearchQuery();
          GenericInterface genericInt = new GenericImpl();
      HashMap dbinfo = info.getDmbsInfoLst();
      String cnt = (String)dbinfo.get("CNT");
      String isMix = util.nvl(info.getIsMix(),"NR");
      String CONTENT_TYPE = "getServletContext()/vnd-excel";
      String fileNm = "memoInExcel"+util.getToDteTime()+"_"+cnt+".xls";
      util.updAccessLog(req,res,"Search Page", "Create Excel");
      HSSFWorkbook hwb = new HSSFWorkbook();
      res.setContentType(CONTENT_TYPE);
      res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
      ExcelUtilObj excelUtil = new ExcelUtilObj();
      excelUtil.init(db, util, info,gtMgr);
      OutputStream out = res.getOutputStream();
        String lstNme =  (String)gtMgr.getValue("lstNme");
          String viewFm = util.nvl(info.getViewForm());
          if(viewFm.equals("MP"))
              lstNme =lstNme+"_MTPR";
          String mdl="MEMO_VW"; 
          ArrayList vwPrpList = info.getVwPrpLst();
          ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
          HashMap excelDtl = new HashMap();
          excelDtl.put("HDR", itemHdr);
          excelDtl.put("lstNme", lstNme);
          excelDtl.put("GRNTTL", "Y");
          excelDtl.put("MDL", info.getVwMdl());
          excelDtl.put("GRNTTL", "Y");
          excelDtl.put("LOGO", "Y");
          excelDtl.put("BYRNME", info.getByrNm());
          excelDtl.put("EXHRTE", String.valueOf(info.getXrt()));
          excelDtl.put("VWLST", vwPrpList);
          if((util.nvl((String)info.getDmbsInfoLst().get("NR_CUST_MDL"),"N").equals("Y"))){
            mdl="CUS_MEMO_VW";
            excelDtl.put("MDL", "CUS_MEMO_VW");
            excelDtl.put("HDR",util.getCustomHdr(itemHdr,genericInt.genericPrprVw(req, res, "", mdl)));
            if(cnt.equalsIgnoreCase("kj"))
            excelDtl.put("GRNTTL", "N");
          }
          hwb = excelUtil.getGenXlObj(excelDtl, req);
     
      hwb.write(out);
      out.flush();
      out.close();
          finalizeObject(db, util);
     return null;
      }
  }
    
    public ActionForward mailExcel(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            SearchForm srchForm ;
            SearchQuery query = new SearchQuery();
        srchForm =(SearchForm)form;
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        GenericInterface genericInt = new GenericImpl();
        util.updAccessLog(req,res,"Search Page", "Mail Excel");
        String typ=req.getParameter("typ");
        String subject= util.nvl(req.getParameter("subject"));
        String msgpost= util.nvl((String)srchForm.getValue("msg"));
        FormFile myFile =(FormFile)srchForm.getValue("theFile");
        HashMap params = new HashMap();
        ArrayList mailList = new ArrayList();
        String isMix = util.nvl(info.getIsMix(),"NR");
        Enumeration reqNme = req.getParameterNames();
         while(reqNme.hasMoreElements()) {
            String paramNm = (String)reqNme.nextElement();
          
            if(paramNm.indexOf("chk") > -1) {
                String val = req.getParameter(paramNm);
                String pktNm = val ;
                if(paramNm.equals("chk_"+val));
                   mailList.add(pktNm);
         }}
        String fileNm = "MEMOExcel_"+getToDteTime()+"_"+cnt+".xls";
         params.put("msg", msgpost);
         params.put("sub", subject);
         params.put("formFile", myFile);
         params.put("mailList", mailList);
         params.put("fileName", fileNm);
         params.put("byrIdn", info.getByrId());
          ExcelUtilObj excelUtil = new ExcelUtilObj();
          excelUtil.init(db, util, info,gtMgr);
          OutputStream out = res.getOutputStream();
            String lstNme =  (String)gtMgr.getValue("lstNme");
            ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
              HashMap excelDtl = new HashMap();
              excelDtl.put("HDR", itemHdr);
            String viewFm = util.nvl(info.getViewForm());
            if(viewFm.equals("MP"))
                lstNme =lstNme+"_MTPR";
            
              excelDtl.put("lstNme", lstNme);
              excelDtl.put("GRNTTL", "Y");
              excelDtl.put("MDL", info.getVwMdl());
              excelDtl.put("GRNTTL", "Y");
              excelDtl.put("LOGO", "Y");
              excelDtl.put("BYRNME", info.getByrNm());
              excelDtl.put("EXHRTE", String.valueOf(info.getXrt()));
           if((util.nvl((String)info.getDmbsInfoLst().get("NR_CUST_MDL"),"N").equals("Y"))){
              String mdl="CUS_MEMO_VW";
              excelDtl.put("MDL", "CUS_MEMO_VW");
              excelDtl.put("HDR",util.getCustomHdr(itemHdr,genericInt.genericPrprVw(req, res, "", mdl)));
              if(cnt.equalsIgnoreCase("kj"))
              excelDtl.put("GRNTTL", "N");
            }

             HSSFWorkbook hwb = excelUtil.getGenXlObj(excelDtl, req);
          query.MailExcel(hwb, params, req , res);
            finalizeObject(db, util);
       return am.findForward("mail");
        }
    }
    public ActionForward pairsrch(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
            return mapping.findForward(rtnPg);  
        }else{
            SearchForm srchForm ;
            SearchQuery query = new SearchQuery();
    srchForm = (SearchForm)form;
    util.updAccessLog(req,res,"Match Pair", "Match Pair");
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            String trDis="",loyalty="";
            if(cnt.equals("xljf")){
            trDis=util.nvl(util.gettradeDis(String.valueOf(info.getRlnId())));
            }
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("SEARCH_RESULT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("SEARCH_RESULT");
            allPageDtl.put("SEARCH_RESULT",pageDtl);
            }
            ArrayList  pageList= ((ArrayList)pageDtl.get("LOYALTY") == null)?new ArrayList():(ArrayList)pageDtl.get("LOYALTY");
            if(pageList!=null && pageList.size() >0){
             srchForm.setValue("LOYALTY", "YES");
              ArrayList loyaltyLst=util.getloyalty(info.getSrchPryID());
              srchForm.setValue("loyCtg", util.nvl((String)loyaltyLst.get(0)));
              srchForm.setValue("loyPct", util.nvl((String)loyaltyLst.get(1)));
              srchForm.setValue("loyDiff", util.nvl((String)loyaltyLst.get(2)));
              srchForm.setValue("loyVlu", util.nvl((String)loyaltyLst.get(3)));
                srchForm.setValue("mem_dis", util.nvl((String)loyaltyLst.get(4)));
                srchForm.setValue("loyallow", util.nvl((String)loyaltyLst.get(5)));
                srchForm.setValue("memallow", util.nvl((String)loyaltyLst.get(6)));
                srchForm.setValue("ttlSalVlu", util.nvl((String)loyaltyLst.get(7)));
                srchForm.setValue("osamount", util.nvl((String)loyaltyLst.get(8)));
                query.iememov(req,info.getSrchPryID());
            }
            String delQ = " Delete from gt_srch_rslt ";
            int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            
       String mstkidn = req.getParameter("mstkIdn");
      ArrayList ary = new ArrayList();
      ary.add(mstkidn);
     int updCt = db.execCall(" Match Pair ", "MATCH_PAIR_PKG.SET_PAIR_CRT(pStkIdn => ?) ", ary);
            
            String srchRefQ =
            " Insert into gt_srch_rslt (rln_idn, srch_id, pkt_ty, stk_idn, vnm, rmk , qty, cts, pkt_dte, stt,prte, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis,diamondImg,jewImg,srayImg,agsImg,mrayImg,ringImg,lightImg,refImg,videos,certImg,video_3d ) " +
            " select ?, 1 srchId , b.pkt_ty, b.idn, b.vnm, b.tfl3 , b.qty,"+
            " decode(b.pkt_ty, 'NR', b.cts, b.cts - nvl(b.cts_iss, 0)) cts" +
            ", b.dte, b.stt,b.fcpr, nvl(b.upr, b.cmp) rte, b.rap_rte" +
            " , b.cert_lab, b.cert_no, 'P' flg, b.sk1, nvl(b.upr, b.cmp), decode(b.rap_rte ,'1',null, trunc((nvl(b.upr,b.cmp)/b.rap_rte*100)-100, 2)),diamondImg,jewImg,srayImg,agsImg,mrayImg,ringImg,lightImg,refImg,videos,certImg,video_3d " +
            " from mstk b , match_pkts_for c"+
            " where b.idn=c.stk_idn and c.rtpkt = ? ";   
            ary = new ArrayList();
            ary.add(Integer.toString(info.getRlnId()));
            ary.add(mstkidn);
           ct = db.execUpd(" Srch Ref ", srchRefQ, ary);
            
            String calQuot = "pkgmkt.Cal_Quot( pRlnId=> ?, pXrt=> ?)";
            ary = new ArrayList();
            ary.add(Integer.toString(info.getRlnId()));
            ary.add(String.valueOf(info.getXrt()));
            ct = db.execCall(" Srch calQ ", calQuot, ary);
            
            String mdl=info.getVwMdl();    
            String pktPrp = "pkgmkt.pktPrp(0,?)";
            ary = new ArrayList();
            ary.add(mdl);
            ct = db.execCall(" Srch Prp ", pktPrp, ary);
            
            
          String lstNme = "SRCHRST_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
         
         HashMap searchList = query.SearchResultGT(req,res, "'P'" , info.getVwPrpLst());
            HashMap dtlMap = new HashMap();

            ArrayList selectstkIdnLst =new ArrayList();
            Set<String> keys = searchList.keySet();
                for(String key: keys){
               selectstkIdnLst.add(key);
                }
            dtlMap.put("selIdnLst",selectstkIdnLst);
            dtlMap.put("pktDtl", searchList);
            dtlMap.put("flg", "P");
            HashMap totals = util.getTTL(dtlMap);
           ArrayList memoList= (query.getMemoType(req,res) == null)?new ArrayList():(ArrayList)query.getMemoType(req,res);
//    srchForm.setValue("memoList", memoList);
    srchForm.setMemoList(memoList);
       ArrayList expDaysList = query.getExpDay(req,res);
        if(expDaysList !=null && expDaysList.size()>0){
//        srchForm.setExpDayList(expDaysList);
        srchForm.setValue("ExpDayList", expDaysList);
        }
          gtMgr.setValue(lstNme+"_MTPR",searchList);
          gtMgr.setValue(lstNme+"_MTPR_TTL",totals );
            gtMgr.setValue(lstNme+"_MTPR_SEL",new ArrayList());
          gtMgr.setValue("lstNme", lstNme);
           req.setAttribute("lstNme", lstNme);
            req.setAttribute("MTPR", "YES");
            info.setViewForm("MP");
            HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(info.getSrchPryID()));
            srchForm.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
            srchForm.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
            srchForm.setValue("otherref",util.nvl((String)buyerDtlMap.get("OTHERREF")));
            srchForm.setValue("gstin",util.nvl((String)buyerDtlMap.get("GSTIN")));
            srchForm.setValue("trDis",trDis);
            srchForm.setValue("loyalty",loyalty);
            finalizeObject(db, util);
     return mapping.findForward("pair");
        }
    }
    public ActionForward reset(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            SearchForm srchForm ;
            SearchQuery query = new SearchQuery();
         srchForm =(SearchForm)form;
         srchForm.reset();
        srchForm.setIsEx("");
         return am.findForward("reset");
        }
    }
  public ActionForward saveOffer(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
          return mapping.findForward(rtnPg);   
      }else{
          SearchForm srchForm ;
          SearchQuery query = new SearchQuery();
     srchForm = (SearchForm)form;
   //  MailAction mail = new MailAction();
      util.updAccessLog(req,res,"Search Page", "Offer");
      String memooffer = util.nvl((String)srchForm.getValue("memooffer"));
      Enumeration reqNme = req.getParameterNames();
      ArrayList ary = new ArrayList();
      String selBkt = "cb_memo_";
      ArrayList memoLst = new ArrayList();
      while(reqNme.hasMoreElements()) {
          String paramNm = (String)reqNme.nextElement();
        
          if(paramNm.indexOf("cb") > -1) {
              String val = req.getParameter(paramNm);
             
              String pktNm = val ;
              if(paramNm.equals(selBkt+val))
                  memoLst.add(pktNm);
       }}
          int memoId=0;
          int ct = db.execUpd("delete gt","Delete from gt_srch_rslt", new ArrayList());
          String lstNme = util.nvl(req.getParameter("lstNme"));
            if(!lstNme.equals("")){                  
              HashMap pktDtls = (HashMap)gtMgr.getValue(lstNme);
              if(memoLst!=null && memoLst.size()>0){
                for(int i=0;i<memoLst.size();i++){
                  String mstkIdn = (String)memoLst.get(i);
                  GtPktDtl pktDtl = (GtPktDtl)pktDtls.get(mstkIdn);
                    String fqout = util.nvl(req.getParameter("bid_prc_"+mstkIdn));
                    String comm = util.nvl(req.getParameter("comm_"+mstkIdn));
                  String gtInsrt="Insert into gt_srch_rslt ( stk_Idn, qty, cts,rap_rte, cmp, quot,fquot, rap_dis,flg,rmk,srt_090) select ? ,?,?,?,? ,?,?,?,?,?,? from dual";
                   ary = new ArrayList();
                  ary.add(mstkIdn);
                  ary.add(pktDtl.getValue("qty"));
                  ary.add(pktDtl.getValue("cts"));
                  ary.add(pktDtl.getValue("rap_rte"));
                  ary.add(pktDtl.getValue("cmp"));
                  ary.add(pktDtl.getValue("quot"));
                    ary.add(fqout);
                  ary.add(pktDtl.getValue("r_dis"));
                  ary.add("M");
                    ary.add(comm);
                    ary.add(pktDtl.getValue("memo_wtdiff"));
                 int ct1 =db.execUpd("gtInsert", gtInsrt, ary);
                }
              }  }
      if(memoLst.size()>0){
          
        
          String genHdr = "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ?  , pTyp => ? ,pflg => ? , pXrt=>? , pMemoIdn => ?)";
          ary = new ArrayList();
          ary.add(String.valueOf(info.getByrId()));
          ary.add(String.valueOf(info.getRlnId()));
          ary.add("RT");
          ary.add("OFR");
          ary.add("NN");
          ary.add(String.valueOf(info.getXrt()));
          ArrayList out = new ArrayList();
          out.add("I");
          CallableStatement cst = null;
          cst =db.execCall("MKE_HDR ", genHdr , ary , out);
          
           memoId = cst.getInt(ary.size()+1);
        cst.close();
        cst=null;
          ary = new ArrayList();
          ary.add(Integer.toString(memoId));
          ary.add("RT");
          ary.add("M");
          int ct1 = db.execCall("pop Memo from gt", "MEMO_PKG.POP_MEMO_FRM_GT(?,?,?)", ary);
          
          
          if(ct1>0)
             req.setAttribute("msg", "memo get Create with memo no."+ memoId);
         
         boolean isMail = false;
         String mailCnt ="";
   
        
          int pepCnt =0;
          ArrayList msgLst = new ArrayList();
          String pTyp = "BID";
          HashMap pktDtls=new HashMap();
          if(!lstNme.equals(""))                 
            pktDtls = (HashMap)gtMgr.getValue(lstNme);
          for(int j=0;j<memoLst.size();j++){
               String stkIdn = (String)memoLst.get(j); 
               HashMap pepParams = new HashMap();
               String rte = util.nvl(req.getParameter("bid_prc_"+stkIdn));
              String stt =  util.nvl(req.getParameter("STT_"+stkIdn));
                  GtPktDtl pktDtl = (GtPktDtl)pktDtls.get(stkIdn);
              if(stt.equals("MKLB"))
                  pTyp="LB";
              if(stt.equals("MKPP"))
                  pTyp = "PP";
               if(!rte.equals("")) {
              
          try {
                  String vnm = util.nvl(req.getParameter("bid_vnm_" + stkIdn));
                  String squot =util.nvl(req.getParameter("BIDRte_" + stkIdn));
                  if(squot.equals("")){
                   squot =util.nvl(req.getParameter("quot_" + stkIdn));
                   if(squot.equals(""))
                       squot =util.nvl(pktDtl.getValue("quot"));
                  }
                  String comm =util.nvl(req.getParameter("comm_" + stkIdn));
                  String lmtrte =util.nvl(req.getParameter("lmtRte_" + stkIdn));
                  String addBid =
                      "web_pkg.bid_add( pStkIdn => ?,  pRlnId => ?, pQuot => ?, pOfrRte=> ? , pRmk => ?, pLmt => ?,pToDte => ?, pTyp=> ?, pXrt => ? ,pFlg => ? , pCnt=>?, pMsg=>?)";
                  ArrayList ary1 = new ArrayList();
                  ary1.add(stkIdn);
                  ary1.add(String.valueOf(info.getRlnId()));
                  ary1.add(squot);
                  ary1.add(rte);
                  ary1.add(comm);
                  ary1.add(lmtrte);
                  ary1.add(util.nvl(req.getParameter("DTE_" + stkIdn)));
                  ary1.add(pTyp);
                  ary1.add(String.valueOf(info.getXrt()));
                  ary1.add("DF");
                  out = new ArrayList();
                  out.add("I");
                  out.add("V");
                  CallableStatement cts = db.execCall("add BId", addBid, ary1, out);
                  int count = cts.getInt(ary1.size() + 1);
                  String msg = cts.getString(ary1.size() + 2);
                  cts.close();
              if(count>0){
                  isMail = true;
                  msgLst.add(msg);
                  pepCnt++;
                  pepParams.put("pepRte",rte);
                  pepParams.put("stkIdn",stkIdn);
                  pepParams.put("count",String.valueOf(pepCnt));
                  pepParams.put("pepDis",util.nvl(req.getParameter("bid_dis_"+stkIdn)));
                  pepParams.put("pepDte", util.nvl(req.getParameter("DTE_" + stkIdn)));
               //   sb = mail.mailPepTR(request, pepParams);
              ///    mailCnt = mailCnt + sb.toString();
                  }
              } catch (SQLException sqle) {
                  // TODO: Add catch code
                  sqle.printStackTrace();
              }
                     
              }}
          if(isMail){
             // mail.MailFormat(request,  mailCnt ,"Expected Price Packet from");
          }
          req.setAttribute("msgLst", msgLst);
          
      }
    HashMap bidMap = query.bidPkt(req,res,info.getRlnId());
    req.setAttribute("bidMap", bidMap);
          finalizeObject(db, util);
          String pgRtn="bid";
    String biddirt = util.nvl((String)srchForm.getValue("biddirt"));
          if(biddirt.equals("Y")){
              session.setAttribute("memoId",String.valueOf(memoId));
              pgRtn="memo";
          }
  return mapping.findForward(pgRtn);
      }
  }
    public ActionForward loadBID(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
            return mapping.findForward(rtnPg);   
        }else{
            SearchForm srchForm ;
            SearchQuery query = new SearchQuery();
       srchForm = (SearchForm)form;
        util.updAccessLog(req,res,"Search Page", "Give Offer");
        String delQ = " Delete from gt_srch_rslt";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        String updBID = "pkgmkt.popBID( pRlnId=> ? , pTyp=> ?, pMdl => ?) ";
        ArrayList ary = new ArrayList();
        ary.add(String.valueOf(info.getRlnId()));
        ary.add("A");
        ary.add(info.getVwMdl());
        ct = db.execCall(" Pop BID", updBID, ary);
        
        String updPP = "pkgmkt.popPP( pRlnId=> ? ,  pMdl => ? , pTyp => ? ) ";
        ary = new ArrayList();
        ary.add(String.valueOf(info.getRlnId()));
        ary.add(info.getVwMdl());
        ary.add("PP");
        ct = db.execCall(" Pop BID", updPP, ary);
       
        HashMap bidMap = query.bidPkt(req, res , info.getRlnId());
        HashMap searchList = query.SearchResult(req,res , "'PP','O','P'" , info.getVwPrpLst());
        HashMap totals = util.getTtls(req);
        req.setAttribute("searchList", searchList);
        req.setAttribute("total",totals);
        req.setAttribute("bidMap", bidMap);
            finalizeObject(db, util);
        return mapping.findForward("bid");
        }
    }

    
  public ActionForward loadWL(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("GtMgr");
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
          return mapping.findForward(rtnPg);  
      }else{
          SearchForm srchForm ;
          SearchQuery query = new SearchQuery();
     srchForm = (SearchForm)form;
      util.updAccessLog(req,res,"Search Page", "Load Watchlist");
          HashMap dbinfo = info.getDmbsInfoLst();
          String cnt = (String)dbinfo.get("CNT");
          String trDis="",loyalty="";
          if(cnt.equals("xljf")){
          trDis=util.nvl(util.gettradeDis(String.valueOf(info.getRlnId())));
          }
          HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
          HashMap pageDtl=(HashMap)allPageDtl.get("SEARCH_RESULT");
          if(pageDtl==null || pageDtl.size()==0){
          pageDtl=new HashMap();
          pageDtl=util.pagedef("SEARCH_RESULT");
          allPageDtl.put("SEARCH_RESULT",pageDtl);
          }
          ArrayList  pageList= ((ArrayList)pageDtl.get("LOYALTY") == null)?new ArrayList():(ArrayList)pageDtl.get("LOYALTY");
          if(pageList!=null && pageList.size() >0){
           srchForm.setValue("LOYALTY", "YES");
            ArrayList loyaltyLst=util.getloyalty(info.getSrchPryID());
            srchForm.setValue("loyCtg", util.nvl((String)loyaltyLst.get(0)));
            srchForm.setValue("loyPct", util.nvl((String)loyaltyLst.get(1)));
            srchForm.setValue("loyDiff", util.nvl((String)loyaltyLst.get(2)));
            srchForm.setValue("loyVlu", util.nvl((String)loyaltyLst.get(3)));
              srchForm.setValue("mem_dis", util.nvl((String)loyaltyLst.get(4)));
              srchForm.setValue("loyallow", util.nvl((String)loyaltyLst.get(5)));
              srchForm.setValue("memallow", util.nvl((String)loyaltyLst.get(6)));
              srchForm.setValue("ttlSalVlu", util.nvl((String)loyaltyLst.get(7)));
              srchForm.setValue("osamount", util.nvl((String)loyaltyLst.get(8)));
              query.iememov(req,info.getSrchPryID());
          }
        String lstNme = "SRCHRST_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");

        gtMgr.setValue(lstNme+"_SEL",new ArrayList());

    ArrayList vwPrpLst = info.getVwPrpLst();
    String delQ = " Delete from gt_srch_rslt";
    int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
    String grpNme = util.nvl(req.getParameter("grpNme"));
    query.popWL(req,(String)srchForm.getValue("rlnIdn"), grpNme);
        HashMap searchList = query.SearchResultGT(req, res , "'Z'" , vwPrpLst);
          HashMap dtlMap = new HashMap();

          ArrayList selectstkIdnLst =new ArrayList();
          Set<String> keys = searchList.keySet();
              for(String key: keys){
             selectstkIdnLst.add(key);
              }
          dtlMap.put("selIdnLst",selectstkIdnLst);
          dtlMap.put("pktDtl", searchList);
          dtlMap.put("flg", "Z");
          HashMap totals = util.getTTL(dtlMap);
         info.setMultiSrchLst(new ArrayList());
    info.setSvdmdDis(new ArrayList());
        HashMap refineDtl = query.refineObj(req, searchList, info.getRefPrpLst());
        HashMap srchDtl = util.useDifferentMap(refineDtl);
        gtMgr.setValue(lstNme,searchList);
        gtMgr.setValue(lstNme+"_TTL",totals );
        gtMgr.setValue("lstNme", lstNme);
        gtMgr.setValue(lstNme+"_SEL",new ArrayList());
        gtMgr.setValue(lstNme+"_SRCHLST", srchDtl);
        gtMgr.setValue(lstNme+"_REF", refineDtl);
          HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(info.getSrchPryID()));
          srchForm.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
          srchForm.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
          srchForm.setValue("otherref",util.nvl((String)buyerDtlMap.get("OTHERREF")));
          srchForm.setValue("gstin",util.nvl((String)buyerDtlMap.get("GSTIN")));
          srchForm.setValue("trDis",trDis);
          srchForm.setValue("loyalty",loyalty);
          finalizeObject(db, util);
    return mapping.findForward("srch");
      }
  }
        
  public ActionForward mailExcelMass(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
      int accessidn=util.updAccessLog(req,res,"Mail Excel ", "Mail Excel");
      SearchForm srchForm ;
      SearchQuery query = new SearchQuery();
  srchForm =(SearchForm)form;
  HashMap dbinfo = info.getDmbsInfoLst();
  String cnt = (String)dbinfo.get("CNT");
  String typ=req.getParameter("typ");
  GenericInterface genericInt = new GenericImpl();
  String fileNm = "MEMOExcel_"+getToDteTime()+".xls";
  String isMix = util.nvl(info.getIsMix(),"NR");
   ExcelUtilObj excelUtil = new ExcelUtilObj();
    excelUtil.init(db, util, info,gtMgr);
  
      String lstNme =  (String)gtMgr.getValue("lstNme");
      ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HashMap excelDtl = new HashMap();
        excelDtl.put("HDR", itemHdr);
      String viewFm = util.nvl(info.getViewForm());
      if(viewFm.equals("MP"))
          lstNme =lstNme+"_MTPR";
      
        excelDtl.put("lstNme", lstNme);
        excelDtl.put("GRNTTL", "Y");
        excelDtl.put("GRNTTL", "Y");
        excelDtl.put("LOGO", "Y");
        excelDtl.put("BYRNME", info.getByrNm());
        excelDtl.put("EXHRTE", String.valueOf(info.getXrt()));
    if(isMix.equals("MIX"))
        excelDtl.put("MDL", "MIX_VW");
    else if(isMix.equals("SMX"))
        excelDtl.put("MDL", "SMX_VW");
    else{
        excelDtl.put("MDL", info.getVwMdl());
        if((util.nvl((String)info.getDmbsInfoLst().get("NR_CUST_MDL"),"N").equals("Y"))){
          String mdl="CUS_MEMO_VW";
          excelDtl.put("MDL", "CUS_MEMO_VW");
          excelDtl.put("HDR",util.getCustomHdr(itemHdr,genericInt.genericPrprVw(req, res, "", mdl)));
          if(cnt.equalsIgnoreCase("kj"))
          excelDtl.put("GRNTTL", "N");
        }
    }
  HSSFWorkbook  hwb = excelUtil.getGenXlObj(excelDtl, req);
    
    String pdfPg = util.nvl(req.getParameter("jspNme"));
        
    if(!pdfPg.equals("")){
         String memoId = util.nvl(req.getParameter("memoId"));
        String reportUrl=(String)dbinfo.get("HOME_DIR");
        String repPath = (String)dbinfo.get("REP_PATH");
         String pdfUrl = repPath+"/reports/rwservlet?"+cnt+"&report="+reportUrl+"\\reports\\"+pdfPg+"&p_id="+memoId+"&p_stt=ALL&p_access="+accessidn; 
       req.setAttribute("PDF", "Y");
      req.setAttribute("PDFURL", pdfUrl);
     }
  query.MailExcelmass(hwb, fileNm, req,res);
          
  ArrayList ByrEmailIds=util.getByerEmailid();
  req.setAttribute("ByrEmailIds", ByrEmailIds);
  util.updAccessLog(req,res,"Search Page", "Mail Images");
  HashMap mailDtl = util.getMailFMT("DFLT_TXT_MAIL");
  String dfltmailtxt=util.nvl((String)mailDtl.get("MAILBODY"));
  req.setAttribute("dfltmailtxt", dfltmailtxt);
      finalizeObject(db, util);
  return am.findForward("mail");
  }
  }
  
    public ActionForward details(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        SearchForm srchForm ;
        SearchQuery query = new SearchQuery();
        GenericInterface genericInt = new GenericImpl();
    srchForm =(SearchForm)form;
    genericInt.genericPrprVw(req,res,"DIA_DTL_VW","DIA_DTL_VW");
    genericInt.genericPrprVw(req,res,"MAK_DTL_VW","MAK_DTL_VW");
    genericInt.genericPrprVw(req,res,"INC_DTL_VW","INC_DTL_VW");
    genericInt.genericPrprVw(req,res,"PAR_DTL_VW","PAR_DTL_VW");
    String mstkidn= util.nvl((String)req.getParameter(("mstkidn")));
    String vnm= util.nvl((String)req.getParameter(("vnm")));
    String LOOK= util.nvl((String)req.getParameter(("LOOK")));
    HashMap pktDtl=new HashMap();
    String dtlQ="select a.mstk_idn, a.lab,b.dta_typ, a.mprp, a.srt, a.grp , c.mdl , decode(b.dta_typ, 'C', a.val,'N', to_char(a.num), 'D', to_char(a.dte,'dd-mm-rrrr'), nvl(a.txt,'')) val " +
    " from stk_dtl a, mprp b , rep_prp c" +
    " where a.mprp = b.prp and a.mstk_idn="+mstkidn+" " +
    " and b.dta_typ in ('C','N','T','D') and c.prp = b.prp and c.flg = 'Y' and " +
    " c.mdl in('DIA_DTL_VW','MAK_DTL_VW','INC_DTL_VW','PAR_DTL_VW' ) and a.grp=1 " +
    " order by c.mdl , c.rnk";
          ArrayList  outLst = db.execSqlLst("Packet Dtl", dtlQ , new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);

    while(rs.next()){
    String mprp=util.nvl(rs.getString("mprp"));
    String mdl=util.nvl(rs.getString("mdl"));
    pktDtl.put(mdl+"_"+mprp,util.nvl2(rs.getString("val"),"-"));
    }
    rs.close();
    pst.close();
    String certQ="select cert_no,diamondImg,jewImg,srayImg,agsImg,mrayImg,ringImg,lightImg,refImg,videos,certImg,video_3d from mstk where idn="+mstkidn;
          outLst = db.execSqlLst("Cert No", certQ , new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
    rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    req.setAttribute("cert_no",util.nvl2(rs.getString("cert_no"),"-"));
    pktDtl.put("diamondImg",util.nvl(rs.getString("diamondImg"),"N"));
        pktDtl.put("jewImg",util.nvl(rs.getString("jewImg"),"N"));
        pktDtl.put("srayImg",util.nvl(rs.getString("srayImg"),"N"));
        pktDtl.put("agsImg",util.nvl(rs.getString("agsImg"),"N"));
        pktDtl.put("mrayImg",util.nvl(rs.getString("mrayImg"),"N"));
        pktDtl.put("ringImg",util.nvl(rs.getString("ringImg"),"N"));
        pktDtl.put("lightImg",util.nvl(rs.getString("lightImg"),"N"));
        pktDtl.put("refImg",util.nvl(rs.getString("refImg"),"N"));
        pktDtl.put("videos",util.nvl(rs.getString("videos"),"N"));
        pktDtl.put("certImg",util.nvl(rs.getString("certImg"),"N"));
        pktDtl.put("video_3d",util.nvl(rs.getString("video_3d"),"N"));
    }
        rs.close();
      pst.close(); 
    req.setAttribute("pktDtl", pktDtl);
    req.setAttribute("vnm", vnm);
    req.setAttribute("mstkidn", mstkidn);
    req.setAttribute("LOOK", LOOK);
//    util.imageDtls();
    if(pktDtl!=null && pktDtl.size()>0)
    req.setAttribute("View", "Y");
            String pgDef = "SEARCH_RESULT";
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);
             if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef(pgDef);
            allPageDtl.put(pgDef,pageDtl);
            }
    util.updAccessLog(req,res,"Search Page", "Packet Details");
            finalizeObject(db, util);
    return am.findForward("details");
        }
    }
    
    public ActionForward saveOfferSrch(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            SearchForm srchForm ;
            SearchQuery query = new SearchQuery();
    query = new SearchQuery();
    srchForm = (SearchForm)form;
    Enumeration reqNme = req.getParameterNames();
    ArrayList ary = new ArrayList();
    String selBkt = "cb_memo_offer_";
    ArrayList memoLst = new ArrayList();
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            String trDis="",loyalty="";
            if(cnt.equals("xljf")){
            trDis=util.nvl(util.gettradeDis(String.valueOf(info.getRlnId())));
            }
            
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("SEARCH_RESULT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("SEARCH_RESULT");
            allPageDtl.put("SEARCH_RESULT",pageDtl);
            }
            ArrayList  pageList= ((ArrayList)pageDtl.get("LOYALTY") == null)?new ArrayList():(ArrayList)pageDtl.get("LOYALTY");
            if(pageList!=null && pageList.size() >0){
             srchForm.setValue("LOYALTY", "YES");
              ArrayList loyaltyLst=util.getloyalty(info.getSrchPryID());
              srchForm.setValue("loyCtg", util.nvl((String)loyaltyLst.get(0)));
              srchForm.setValue("loyPct", util.nvl((String)loyaltyLst.get(1)));
              srchForm.setValue("loyDiff", util.nvl((String)loyaltyLst.get(2)));
              srchForm.setValue("loyVlu", util.nvl((String)loyaltyLst.get(3)));
                srchForm.setValue("mem_dis", util.nvl((String)loyaltyLst.get(4)));
                srchForm.setValue("loyallow", util.nvl((String)loyaltyLst.get(5)));
                srchForm.setValue("memallow", util.nvl((String)loyaltyLst.get(6)));
                srchForm.setValue("ttlSalVlu", util.nvl((String)loyaltyLst.get(7)));
                srchForm.setValue("osamount", util.nvl((String)loyaltyLst.get(8)));
                query.iememov(req,info.getSrchPryID());
            }
    while(reqNme.hasMoreElements()) {
    String paramNm = (String)reqNme.nextElement();
    if(paramNm.indexOf("offer") > -1) {
    String val = req.getParameter(paramNm);
    String pktNm = val ;
    if(paramNm.equals(selBkt+val))
    memoLst.add(pktNm);
    }}
    if(memoLst.size()>0){
        
        String genHdr = "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ?  , pTyp => ? ,pflg => ? , pXrt=>? , pMemoIdn => ?)";
        ary = new ArrayList();
        ary.add(String.valueOf(info.getByrId()));
        ary.add(String.valueOf(info.getRlnId()));
        ary.add("RT");
        ary.add("OFR");
        ary.add("NN");
        ary.add(String.valueOf(info.getXrt()));
        ArrayList out = new ArrayList();
        out.add("I");
        CallableStatement cst = null;
        cst =db.execCall("MKE_HDR ", genHdr , ary , out);
        
        int memoId = cst.getInt(ary.size()+1);
      cst.close();
      cst=null;
        ary = new ArrayList();
        ary.add(Integer.toString(memoId));
        ary.add("RT");
        ary.add("M");
        int ct1 = db.execCall("pop Memo from gt", "MEMO_PKG.POP_MEMO_FRM_GT(?,?,?)", ary);
        
        String lstNme = util.nvl(req.getParameter("lstNme"));
        HashMap pktDtls=new HashMap();
          if(!lstNme.equals(""))                
            pktDtls = (HashMap)gtMgr.getValue(lstNme);
        
    int pepCnt =0;
    ArrayList msgLst = new ArrayList();
    String pTyp = "BID";
    for(int j=0;j<memoLst.size();j++){
    String stkIdn = (String)memoLst.get(j);
    HashMap pepParams = new HashMap();
    String rte = util.nvl(req.getParameter("bid_prc_"+stkIdn));
    String stt = util.nvl(req.getParameter("STT_"+stkIdn));
        GtPktDtl pktDtl = (GtPktDtl)pktDtls.get(stkIdn);
   out = new ArrayList();
    if(stt.equals("MKLB"))
    pTyp="LB";
    if(stt.equals("MKPP"))
    pTyp = "PP";
    if(!rte.equals("")) {

    try {
    String vnm = util.nvl(req.getParameter("bid_vnm_" + stkIdn));
    String squot =util.nvl(req.getParameter("BIDRte_" + stkIdn));
    String dte = util.nvl(req.getParameter("DTE_" + stkIdn));
        if(dte.length() < 3){
          dte =util.getToDte();
        }
    if(squot.equals("")){
    squot =util.nvl(req.getParameter("quot_" + stkIdn));
        if(squot.equals(""))
            squot =util.nvl(pktDtl.getValue("quot"));
    }
    String comm =util.nvl(req.getParameter("comm_" + stkIdn));
    String lmtrte =util.nvl(req.getParameter("lmtRte_" + stkIdn));
    String addBid =
    "web_pkg.bid_add(pStkIdn => ?, pRlnId => ?, pQuot => ?, pOfrRte=> ? , pRmk => ?, pLmt => ?,pToDte => ?, pTyp=> ?, pXrt => ? ,pFlg => ? , pCnt=>?, pMsg=>?)";
    ArrayList ary1 = new ArrayList();
    ary1.add(stkIdn);
    ary1.add(String.valueOf(info.getRlnId()));
    ary1.add(squot);
    ary1.add(rte);
    ary1.add(comm);
    ary1.add(lmtrte);
    ary1.add(dte);
    ary1.add(pTyp);
    ary1.add(String.valueOf(info.getXrt()));
    ary1.add("DF");
    out = new ArrayList();
    out.add("I");
    out.add("V");
    CallableStatement ct = db.execCall("add BId", addBid, ary1, out);
    int count = ct.getInt(ary1.size() + 1);
    String msg = ct.getString(ary1.size() + 2);
    ct.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }

    }}
    }
           
    HashMap bidMap = query.bidPkt(req, res , 0);
    req.setAttribute("bidMap", bidMap);
    ArrayList memoList= (query.getMemoType(req,res) == null)?new ArrayList():(ArrayList)query.getMemoType(req,res);
//    srchForm.setValue("memoList", memoList);
    srchForm.setMemoList(memoList);
    util.updAccessLog(req,res,"Search Page", "Save Offer");
            HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(info.getSrchPryID()));
            srchForm.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
            srchForm.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
            srchForm.setValue("otherref",util.nvl((String)buyerDtlMap.get("OTHERREF")));
            srchForm.setValue("gstin",util.nvl((String)buyerDtlMap.get("GSTIN")));
            srchForm.setValue("trDis",trDis);
            srchForm.setValue("loyalty",loyalty);
//            util.imageDtls();
       
//            util.StockViewLyt();
            finalizeObject(db, util);
    return am.findForward("srch");
        }
    }
    public ActionForward    multiImgDtl(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          SearchForm srchForm ;
          SearchQuery query = new SearchQuery();
         srchForm = (SearchForm)form;
         String where=util.nvl(req.getParameter("where"));
          if(where.equals(""))
          where=util.nvl((String)srchForm.getValue("where"));
          srchForm.reset();
          String sub="Show Multiple Types Images";
          String pgDef = "SEARCH_RESULT";
          HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
          HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);
          ArrayList pageList=new ArrayList();
          HashMap pageDtlMap=new HashMap();
          pageList= ((ArrayList)pageDtl.get("MULTIIMG_SUB") == null)?new ArrayList():(ArrayList)pageDtl.get("MULTIIMG_SUB");
          if(pageList!=null && pageList.size() >0){
          pageDtlMap=(HashMap)pageList.get(0);
          sub=util.nvl((String)pageDtlMap.get("dflt_val"));
          }
          srchForm.setValue("subject", sub);
          
          if(where.equals("SRCH")){
              srchForm.setValue("to_eml", info.getByrEml());  
              srchForm.setValue("cc_eml", info.getSaleExeEml());
              srchForm.setValue("flg", "M");
          }
          if(where.equals("PKTLOOKUP")){
              srchForm.setValue("flg", "Z");
          }
          srchForm.setValue("where", where);
//          util.imageDtls();
          finalizeObject(db, util);
         return am.findForward("multiImgDtl");   
      }
    }    
    
    public ActionForward multiImgSend(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {

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

            SearchForm srchForm ;

            SearchQuery query = new SearchQuery();

        srchForm = (SearchForm)form;

        String subject=util.nvl((String)srchForm.getValue("subject"),"NA");

        String to_eml=util.nvl((String)srchForm.getValue("to_eml"));

        String cc_eml=util.nvl((String)srchForm.getValue("cc_eml"));

        String bcc_eml=util.nvl((String)srchForm.getValue("bcc_eml"));

        String flg=util.nvl((String)srchForm.getValue("flg"));

        String where=util.nvl((String)srchForm.getValue("where"));

            GenericInterface genericInt = new GenericImpl();

        ArrayList vwPrpLst=new ArrayList();

        if(where.equals("SRCH"))

        vwPrpLst = info.getVwPrpLst();

        if(where.equals("PKTLOOKUP"))

        vwPrpLst= (session.getAttribute("PKTLKUP_VW") == null)?new ArrayList():(ArrayList)session.getAttribute("PKTLKUP_VW");

        HashMap dbmsInfo = info.getDmbsInfoLst();

        String client = (String)dbmsInfo.get("CNT");

        String sh = (String)dbmsInfo.get("SHAPE");

        String colval = (String)dbmsInfo.get("COL");

        String polval = (String)dbmsInfo.get("POL");

        String clrlval = (String)dbmsInfo.get("CLR");

        String symval = (String)dbmsInfo.get("SYM");

        String cutval = (String)dbmsInfo.get("CUT");

        String flval = (String)dbmsInfo.get("FL");    

        boolean send=false;

        StringBuffer msg=new StringBuffer();

        ResultSet rs=null;

        ArrayList ary=new ArrayList();

        ArrayList types=new ArrayList();

        ArrayList imgList=new ArrayList();

        HashMap imgDtl=new HashMap();

        HashMap dtls=new HashMap();

        String gtCol="";

            ArrayList imagelistDtl =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ImaageDtls") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ImaageDtls");

        Enumeration reqNme = req.getParameterNames();

        while(reqNme.hasMoreElements()) {

        String paramNm = (String)reqNme.nextElement();

        if(paramNm.indexOf("cb_img") > -1) {

        gtCol = req.getParameter(paramNm);

        types.add(gtCol);

        }

        }

            ArrayList cusPrpLst = genericInt.genericPrprVw(req, res, "MAILIMG_VW", "MAILIMG_VW");

            String lstNme = util.nvl((String)gtMgr.getValue("lstNme"));

            HashMap pktDtls = (HashMap)gtMgr.getValue(lstNme);

            ArrayList idnList = (ArrayList)gtMgr.getValue(lstNme+"_SEL");

            if(idnList!=null && idnList.size()>0){

            for(int i=0;i<idnList.size();i++){

            String stkIdn = (String)idnList.get(i);

            GtPktDtl pktDtl = (GtPktDtl)pktDtls.get(stkIdn);

        imgDtl=new HashMap();

        imgDtl.put("PacketNo",util.nvl(pktDtl.getValue("vnm")));

        imgDtl.put("carat",util.nvl(pktDtl.getValue("cts")));

        imgDtl.put("Shape",util.nvl(pktDtl.getValue(sh)));

        imgDtl.put("color",util.nvl(pktDtl.getValue(colval)));

            imgDtl.put("clr",util.nvl(pktDtl.getValue(clrlval)));

            imgDtl.put("pol",util.nvl(pktDtl.getValue(polval)));

            imgDtl.put("sym",util.nvl(pktDtl.getValue(symval)));

            imgDtl.put("cut",util.nvl(pktDtl.getValue(cutval)));

            imgDtl.put("fl",util.nvl(pktDtl.getValue(flval)));

        imgDtl.put("cert_lab",util.nvl(pktDtl.getValue("cert_lab")));

        imgDtl.put("cert_no",util.nvl(pktDtl.getValue("cert_no")));

    

            imgDtl.put("diamondImg",util.nvl(pktDtl.getValue("diamondImg"),"N"));

            imgDtl.put("jewImg",util.nvl(pktDtl.getValue("jewImg"),"N"));

            imgDtl.put("srayImg",util.nvl(pktDtl.getValue("srayImg"),"N"));

            imgDtl.put("agsImg",util.nvl(pktDtl.getValue("agsImg"),"N"));

            imgDtl.put("mrayImg",util.nvl(pktDtl.getValue("mrayImg"),"N"));

            imgDtl.put("ringImg",util.nvl(pktDtl.getValue("ringImg"),"N"));

            imgDtl.put("lightImg",util.nvl(pktDtl.getValue("lightImg"),"N"));

            imgDtl.put("refImg",util.nvl(pktDtl.getValue("refImg"),"N"));

            imgDtl.put("videos",util.nvl(pktDtl.getValue("videos"),"N"));

            imgDtl.put("certImg",util.nvl(pktDtl.getValue("certImg"),"N"));

            imgDtl.put("video_3d",util.nvl(pktDtl.getValue("video_3d"),"N"));

                if(cusPrpLst!=null && cusPrpLst.size()>0){

                    for(int x=0;x<cusPrpLst.size();x++){

                        String cusPrp = (String)cusPrpLst.get(x);

                        imgDtl.put(cusPrp,util.nvl(pktDtl.getValue(cusPrp)));

                    }

                }

        imgList.add(imgDtl);

    

        }

            }

        String img="";

    

        String hdr = "<html><head><title></title>\n"+

        "<style type=\"text/css\">\n"+

        "body{\n" +

        " margin-left: 10px;\n" +

        " margin-top: 10px;\n" +

        " margin-right: 0px;\n" +

        " margin-bottom: 0px;\n" +

        " font-family: Arial, Helvetica, sans-serif;\n" +

        " font-size: 12px;\n" +

        " color: #333333;\n" +

        "}\n" +

        "</style>\n"+

        "</head>";

        msg.append(hdr);

        msg.append("<body>");
        int colospan=11;
    

        msg.append("<table border=\"1\" >");

        for(int i=0; i<imgList.size();i++){

    

        msg.append("<tr>");

        msg.append("<th>Packet No.</th>");

        msg.append("<th>Shape</th>");

        msg.append("<th>Carat</th>");

        msg.append("<th>Clarity</th>");

        msg.append("<th>Color</th>");

        msg.append("<th>Cut</th>");

        msg.append("<th>Pol</th>");

        msg.append("<th>Sym</th>");

        msg.append("<th>FL</th>");

        msg.append("<th>Lab</th>");

        msg.append("<th>Cert_No</th>");

        if(cusPrpLst!=null && cusPrpLst.size()>0){

         for(int x=0;x<cusPrpLst.size();x++){

           String cusPrp = (String)cusPrpLst.get(x);

             msg.append("<th>"+cusPrp+"</th>");

         }
            colospan = colospan+cusPrpLst.size();
        }

        msg.append("</tr>");

        msg.append("<tr>");

        HashMap Dtl=(HashMap)imgList.get(i);

        String vnm=(String)Dtl.get("PacketNo");

        String serPathVid="";

        msg.append("<td>"+Dtl.get("PacketNo")+"</td>");

        msg.append("<td>"+Dtl.get("Shape")+"</td>");

        msg.append("<td>"+Dtl.get("carat")+"</td>");

        msg.append("<td>"+Dtl.get("clr")+"</td>");

        msg.append("<td>"+Dtl.get("color")+"</td>");

        msg.append("<td>"+Dtl.get("cut")+"</td>");

        msg.append("<td>"+Dtl.get("pol")+"</td>");

        msg.append("<td>"+Dtl.get("sym")+"</td>");

        msg.append("<td>"+Dtl.get("fl")+"</td>");

        msg.append("<td>"+Dtl.get("cert_lab")+"</td>");

        msg.append("<td>"+Dtl.get("cert_no")+"</td>");

            if(cusPrpLst!=null && cusPrpLst.size()>0){

             for(int x=0;x<cusPrpLst.size();x++){

                        String cusPrp = (String)cusPrpLst.get(x);

                 msg.append("<td>"+Dtl.get(cusPrp)+"</td>");

             }}

        msg.append("</tr>");

    

        msg.append("<tr>");

        msg.append("<td colspan="+colospan+"><table><tr>");

        for(int j=0;j<imagelistDtl.size();j++){

        dtls=new HashMap();

        dtls=(HashMap)imagelistDtl.get(j);

        gtCol=util.nvl((String)dtls.get("GTCOL"));

        if(types.contains(gtCol)){

            String val=util.nvl((String)Dtl.get(gtCol));

            String path=util.nvl((String)dtls.get("PATH"));

            String typ=util.nvl((String)dtls.get("TYP"));

            String allowon=util.nvl((String)dtls.get("ALLOWON"));

            if(!val.equals("N")){

            if(typ.equals("V") && (allowon.indexOf("MULTI") > -1)){

            if(val.indexOf(".js") <=-1 || val.indexOf(".mov") > -1){

            path=path+val;

            path=path.replaceAll("SPECIAL", ".mov");

            path=path.replaceAll("imaged", "videos");

            serPathVid=path;

            }else{

            String imgPath = (String)dbmsInfo.get("IMG_PATH");

            String videoFoundUrl = imgPath+"XraySample/video.jpg";

            serPathVid = "";

            if(val.equals("3.js")){

            serPathVid = imgPath+"viewer.html?d="+vnm+"&sm=1&sd=50&sv=0,1,2,3,4&z=1&v=3";

            }else if(val.equals("2.js")) {

            serPathVid = imgPath+"viewer.html?d="+vnm+"&sm=1&sd=50&sv=0,1,2,3,4&z=1&v=2";

            }else if(val.equals("1.js")) {

            serPathVid = imgPath+"viewer.html?d="+vnm+"&sm=1&sd=50&sv=0,1,2,3,4&z=1&v=1";

            }else if(val.equals("0.js")) {

            serPathVid = imgPath+"viewer.html?d="+vnm+"&sm=1&sd=50&sv=0,1,2,3,4&z=1&v=1";

            }else if(val.indexOf("json") > -1) {
                String conQ=vnm;
                if(val.indexOf("/") > -1){
                conQ=val.substring(0,val.indexOf("/"));
                }
            serPathVid = imgPath+"Vision360.html?sd=50&z=1&v=2&sv=0&d="+conQ;
            }else {

            serPathVid = "";

            }

            }

                img="<td valign=\"middle\"><a href="+serPathVid+" target=\"_blank\">PLAY VIDEO</a></td>";

                msg.append(img);

            }else{

            path=path+val;

            img="<td><a href="+path+" target=\"_blank\"><img height=\"100\" width=\"100\" src="+path+" title="+gtCol+"></a></td><td></td>";    

            msg.append(img);

        }

        }

        }

        }

        msg.append("</tr></table></td></tr>");

        }

    

        msg.append("</table>");

        msg.append("</body>");

        msg.append("</html>");

        GenMail mail = new GenMail();

        info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));

        info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));

        info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));

        info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));

        mail.setInfo(info);

        mail.init();

               ArrayList param = new ArrayList();

               String salExcEml ="";  

               String salNm ="";

                if(client.equals("hk") && !to_eml.equals("")){

               String[] emlLst = to_eml.split(",");

               if(emlLst.length<=1){

               param.add(emlLst[0].trim());

               String Qrymid =" select lower(byr.get_nm(emp_idn)) salNm ,  lower(byr.get_eml(emp_idn,'N')) salEml from nme_cntct_v where  eml= ? ";

                   rs= db.execSql("Image Dtl",Qrymid,param);

                   if(rs.next()) {

                           salNm = util.nvl(rs.getString("salNm"));

                       salExcEml = util.nvl(rs.getString("salEml"));

                       

                }
                    rs.close();
                }

                }

               String senderID =util.nvl((String)dbmsInfo.get("SENDERID"));

               String senderNm =(String)dbmsInfo.get("SENDERNM");

               if(senderID.equals("NA"))

                  senderID=info.getSaleExeEml();   

               if(!salExcEml.equals("") && !salNm.equals("")){

                   senderID=salExcEml;

                   senderNm=salNm;

               } 

        mail.setReplyTo(senderID);

        mail.setSender(senderID, senderNm);

        mail.setSubject(subject);

    

        if(!to_eml.equals("")){

            send=true;

            String[] emlLst = to_eml.split(",");

            if(emlLst!=null){

            for(int i=0 ; i <emlLst.length; i++)

            {

            mail.setTO(emlLst[i]);

            }

            }

        }

        if(!cc_eml.equals("")){

                send=true;

                String[] emlLst = cc_eml.split(",");

                if(emlLst!=null){

                for(int i=0 ; i <emlLst.length; i++)

                {

                mail.setCC(emlLst[i]);

                }

                }

        }

        if(!bcc_eml.equals("")){

            send=true;

                    String[] emlLst = bcc_eml.split(",");

                    if(emlLst!=null){

                    for(int i=0 ; i <emlLst.length; i++)

                    {

                    mail.setBCC(emlLst[i]);

                    }

                    }

        }

        srchForm.setValue("where", where);

        req.setAttribute("msg", "Mail delivered successfully...");

        String mailMag = msg.toString();

        mail.setMsgText(mailMag);

        if(imgList.size()>0 && send){

        HashMap logDetails=new HashMap();

        logDetails.put("BYRID","0");

        logDetails.put("TYP","MULTIIMGSND");

        logDetails.put("RELID","0");

        String mailLogIdn=util.mailLogDetails(req,logDetails,"I");

        logDetails.put("MSGLOGIDN",mailLogIdn);

        logDetails.put("MAILDTL",mail.send(""));

        util.mailLogDetails(req,logDetails,"U");

        }else

        req.setAttribute("msg", "Mail sending failed...");

        return multiImgDtl(am, srchForm, req, res);

        }

    

        }


    public ActionForward loadPndPktsToIssue(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
            return mapping.findForward(rtnPg);  
        }else{
        util.updAccessLog(req,res,"Search Page", "loadPndPktsToIssue Start");
        SearchForm srchForm ;
        srchForm = (SearchForm)form;
        String days=util.nvl((String)req.getParameter("days"));
        if(days.equals(""))
        days=util.nvl((String)srchForm.getValue("BTN_DAYS"));
        if(days.equals(""))
        days="0";
        dayscomp(req,res,days);
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("PKT_PND_TO_ISSUE");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("PKT_PND_TO_ISSUE");
            allPageDtl.put("PKT_PND_TO_ISSUE",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            srchForm.reset();
            req.setAttribute("days", days);
            srchForm.setValue("BTN_DAYS", days);
            util.updAccessLog(req,res,"Search Page", "loadPndPktsToIssue end");
        return mapping.findForward("loadPndPktsToIssue");
        }
    }
    
    public void dayscomp(HttpServletRequest req,HttpServletResponse res,String days)throws Exception{
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList param=new ArrayList();
        ArrayList pndPktsToIssueLst=new ArrayList();
        HashMap pndPktsToIssueDtl=new HashMap();
        HashMap pktPrpMap=new HashMap();
        String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
        String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
        String usrFlg=util.nvl((String)info.getUsrFlg());
        ArrayList rolenmLst=(ArrayList)info.getRoleLst();
        String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
        String conQ="";
        if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
        }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
        conQ +=" and nvl(n.grp_nme_idn,0) =? "; 
        } 
        
        if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                if(util.nvl((String)info.getDmbsInfoLst().get("EMP_LOCK")).equals("Y")){
                    conQ += " and (n.emp_idn= ? or n.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
                }
        }
        
        String rsltQ="with WR_PKTS as (\n" + 
        "select distinct j.nme_idn, jd.mstk_idn \n" + 
        "from mjan j, jandtl jd \n" + 
        "where j.idn = jd.idn\n" + 
        "and trunc(j.dte) between trunc(sysdate)-? and trunc(sysdate)\n" + 
        "and j.typ in ('Z', 'WR')\n" + 
        "), \n" + 
        "MM_PKTS as (\n" + 
        "select distinct j.nme_idn, jd.mstk_idn ,decode(jd.stt, 'AP', 1, 0) cnt\n" + 
        "from mjan j, jandtl jd ,WR_PKTS wr\n" + 
        "where j.idn = jd.idn and jd.mstk_idn=wr.mstk_idn and j.nme_idn=wr.nme_idn\n" + 
        "and trunc(j.dte) between trunc(sysdate)-? and trunc(sysdate)\n" + 
        "and j.typ in ('I', 'E')\n" + 
        "and jd.stt <> 'CL'\n" + 
        ")\n" + 
        "select n.nme, count(distinct wr.mstk_idn) all_pkts, count(distinct mm.mstk_idn) memo_pkts,count(distinct mm.cnt) app_pkts, wr.nme_idn\n" + 
        "from wr_pkts wr, mm_pkts mm, nme_v n\n" + 
        "where wr.nme_idn = mm.nme_idn and wr.nme_idn = n.nme_idn\n" +conQ +
        " group by n.nme, wr.nme_idn\n" + 
        " order by n.nme";
        param=new ArrayList();
        param.add(days);
        param.add(days);
        if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
        }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
        param.add(dfgrpnmeidn);
        } 
        if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                if(util.nvl((String)info.getDmbsInfoLst().get("EMP_LOCK")).equals("Y")){
                    param.add(dfNmeIdn);
                    param.add(dfNmeIdn);
                }
        }
        
      ArrayList  outLst = db.execSqlLst("WR_PKTS", rsltQ, param);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);

        while (rs.next()) {
            pktPrpMap = new HashMap();
            String nme_idn = util.nvl(rs.getString("nme_idn"));
            pktPrpMap.put("NME",util.nvl(rs.getString("nme")));
            pktPrpMap.put("ALL_PKTS",util.nvl(rs.getString("all_pkts")));
            pktPrpMap.put("MEMO_PKTS",util.nvl(rs.getString("memo_pkts")));
            pndPktsToIssueDtl.put(nme_idn, pktPrpMap);
            pndPktsToIssueLst.add(nme_idn);
        }
        rs.close();
        pst.close();
        req.setAttribute("pndPktsToIssueLst", pndPktsToIssueLst);
        req.setAttribute("pndPktsToIssueDtl", pndPktsToIssueDtl);
    } 
    
    public ActionForward loadsearchFromPndissue(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"Search Page", "loadsearchFromPndissue Load");
          SearchForm srchForm ;
          SearchQuery query = new SearchQuery();
          HashMap dbinfo = info.getDmbsInfoLst();
          String sz = (String)dbinfo.get("SIZE");
        if(info.getVwPrpLst()==null)
        util.initSrch();  
//        if(info.getSrchPrp()==null)
//            util.initPrpSrch();
        srchForm = (SearchForm)form;
        srchForm.reset();
        srchForm.setIsEx("");
        String typ = util.nvl(req.getParameter("Typ"));
        if(typ.equals("")){
          typ = "SRCH";
          info.setIsFancy("SRCH");
        }
        GenericInterface genericInt = new GenericImpl();
        genericInt.genericPrprVw(req,res,"INCLUDE_EXEC_VW","INCLUDE_EXEC_VW");
        String nme_idn = util.nvl(req.getParameter("nme_idn"));
        String days = util.nvl(req.getParameter("days"));
        ArrayList param=new ArrayList();
          String isMix = "";
          String vnmLst="";
          String findrlnIdnMstkidn="";
          String nmerel_idn="0";
          String byrnme="0";
          String xrt="0";
          
          String rsltQ="WITH P_PKTS as (\n" + 
          "  select distinct mstk_idn \n" + 
          "  from mjan j, jandtl jd \n" + 
          "  where j.idn = jd.idn \n" + 
          "  and trunc(j.dte) between trunc(sysdate)-? and trunc(sysdate)\n" + 
          "  and j.typ in ('Z', 'WR')\n" + 
          "  and j.nme_idn = ?\n" + 
          "  minus\n" + 
          "  select distinct mstk_idn \n" + 
          "  from mjan j, jandtl jd \n" + 
          "  where j.idn = jd.idn \n" + 
          "  and trunc(j.dte) between trunc(sysdate)-? and trunc(sysdate)\n" + 
          "  and j.typ in ('I', 'E')\n" + 
          "  and j.nme_idn = ?\n" + 
          "  and jd.stt <> 'CL')\n" + 
          "  select m.idn,m.vnm,m.pkt_ty from mstk m, p_pkts p\n" + 
          "  where m.idn = p.mstk_idn";
          param=new ArrayList();
          param.add(days);
          param.add(nme_idn);
          param.add(days);
          param.add(nme_idn);
        ArrayList  outLst = db.execSqlLst("WR_PKTS", rsltQ, param);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet  rs = (ResultSet)outLst.get(1);

          while (rs.next()) {
              String vnm=util.nvl(rs.getString("vnm"));
              if(vnmLst.equals(""))
              vnmLst=vnm;
              else
              vnmLst+=","+vnm;
              if(isMix.equals(""))
              isMix=util.nvl(rs.getString("pkt_ty"));
              if(findrlnIdnMstkidn.equals(""))
              findrlnIdnMstkidn=util.nvl(rs.getString("idn"));
          }
          rs.close();
          pst.close();
          
          String findrlnQ="With Idn_PKTS as (\n" + 
          "  select max(j.idn) idn\n" + 
          "  from mjan j, jandtl jd \n" + 
          "  where j.idn = jd.idn and jd.mstk_idn=?\n" + 
          "  and j.typ in ('Z', 'WR') and trunc(j.dte) between trunc(sysdate)-? and trunc(sysdate) \n" + 
          "  and j.nme_idn = ?\n" + 
          ")\n" + 
          "select mj.nmerel_idn,byr.get_nm(mj.nme_idn) byrnme,mj.exh_rte \n" + 
          "from Idn_PKTS wr,mjan mj\n" + 
          "where wr.idn=mj.idn";
          param=new ArrayList();
          param.add(findrlnIdnMstkidn);
          param.add(days);
          param.add(nme_idn);
        outLst = db.execSqlLst("findrlnQ", findrlnQ, param);
         pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
          while (rs.next()) {
          nmerel_idn=util.nvl(rs.getString("nmerel_idn"));
          byrnme=util.nvl(rs.getString("byrnme"));
          xrt=util.nvl(rs.getString("exh_rte"));
          }
          rs.close();
          pst.close();
        ArrayList byrLst= query.getByrList(req,res);
        srchForm.setByrList(byrLst);
        ArrayList termsList = query.getTerm(req,res, Integer.parseInt(nme_idn));
        srchForm.setTrmList(termsList);
          
        ArrayList dmdList = query.getDmdList(req,res, Integer.parseInt(nmerel_idn));
        srchForm.setDmdList(dmdList);
        req.setAttribute("dmdList", dmdList);
        ArrayList webReqList = query.getWebRequest(req, res, Integer.parseInt(nmerel_idn));
        req.setAttribute("webReqList", webReqList);
          ArrayList hrReqList = query.getHrRequest(req, res, info.getDf_rln_id());
          req.setAttribute("hrReqList", hrReqList);
        ArrayList lstMemoList = query.getLstMemo(req, res, Integer.parseInt(nmerel_idn));
        req.setAttribute("lstMemoList", lstMemoList);
          ArrayList lstMemoListNG = query.getLstMemoNG(req, res, Integer.parseInt(nmerel_idn));
          req.setAttribute("lstMemoListNG", lstMemoListNG);
       ArrayList grpNmeList = query.getGrpList(req, res, Integer.parseInt(nmerel_idn));
        req.setAttribute("grpNmeList", grpNmeList);
        ArrayList contactList = query.getContactList(req, res, Integer.parseInt(nme_idn));
        req.setAttribute("contDtlList", contactList);
        srchForm.setByrId("0");
        srchForm.setParty(nme_idn);
        srchForm.setByrRln(nmerel_idn);
        srchForm.setValue("partytext", byrnme);
        srchForm.setValue("xrt", xrt);
        info.setMultiSrchLst(new ArrayList());
        info.setSvdmdDis(new ArrayList());
        session.setAttribute("SrchTyp",typ);
//       query.StockViewLyt(req, res);
      String pgDef = "SEARCH_RESULT";
      if(isMix.equals("MIX") || isMix.equals("MX"))
          pgDef = "MIXSRCH_RESULT";
      if(isMix.equals("SMX"))
          pgDef = "SMXSRCH_RESULT";
          String searchView = util.nvl(req.getParameter("searchView"),"GRID");
          info.setSearchView(searchView);
     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);
     if(pageDtl==null || pageDtl.size()==0){
     pageDtl=new HashMap();
     pageDtl=util.pagedef(pgDef);
     allPageDtl.put(pgDef,pageDtl);
     }
     info.setPageDetails(allPageDtl);
      info.setIsMix(isMix);
//      ArrayList rfiddevice = ((ArrayList)info.getRfiddevice() == null)?new ArrayList():(ArrayList)info.getRfiddevice();
//      if(rfiddevice.size() == 0) {
//      util.rfidDevice();    
//      }
      ArrayList memoList= (query.getSrchType(req,res,srchForm,"Y") == null)?new ArrayList():(ArrayList)query.getSrchType(req,res,srchForm,"Y");
      util.getcrtwtPrp(sz,req,res);
      util.setMdl("MEMOSRCH");
      util.updAccessLog(req,res,"Search Page", "loadsearchFromPndissue End");
      srchForm.setValue("srchRef", "vnm"); 
      srchForm.setValue("srchRefVal",vnmLst); 
     finalizeObject(db, util);
     return am.findForward("load");
      }
    }

    public String getToDteTime() {
        Date date = new Date();
        String DATE_FORMAT = "ddMMMyyyy_kk.mm.ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String toDte = sdf.format(date) ;
        return toDte;
    }
    
  public void GtMgrReset(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
        String lstNme = (String)gtMgr.getValue("lstNme");
        HashMap gtMgrMap = (HashMap)gtMgr.getValues();
        gtMgrMap.remove(lstNme+"_SEL");
        gtMgrMap.remove(lstNme);
        gtMgrMap.remove(lstNme+"_TTL");
        gtMgrMap.remove(lstNme+"_SRCHLST");
        gtMgrMap.remove(lstNme+"_REF");
        gtMgrMap.remove(lstNme+"REF");
        gtMgrMap.remove(lstNme+"REF_TTL");
        gtMgrMap.remove("BID"+lstNme);
        gtMgrMap.remove("BID"+lstNme+"_TTL");
        gtMgrMap.remove("lstNme");
        gtMgrMap.remove(lstNme+"_ALLLST");
        gtMgrMap = null;
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
                util.updAccessLog(req,res,"Search Page", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Search Page", "init");
            }
            }
            return rtnPg;
            }
    public void finalizeObject(DBMgr db, DBUtil util){
        try {
            db=null;
            util=null;
        } catch (Throwable t) {
            // TODO: Add catch code
            t.printStackTrace();
        }
       
    }
    public SearchAction() {
        super();
    }
    
  
}
