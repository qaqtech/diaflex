package ft.com.mixakt;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.GtPktDtl;
import ft.com.dao.ObjBean;
import ft.com.dao.UIForms;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import ft.com.marketing.SearchQuery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.LineNumberReader;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

public class StonesTransferAction extends DispatchAction {
    
    public StonesTransferAction() {
        super();
    }
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
         StonesTransferForm udf = (StonesTransferForm)af;
          udf.resetAll();
          String typ = util.nvl(req.getParameter("TYP"));
          udf.setValue("TYP", typ);
          String  initil="TRANS";
          if(typ.equals("MIX"))
              initil="TRANSMIX";
          if(typ.equals("RGH"))
              initil="TRANSRGH";
          GtMgrReset(req,"",initil);
          GenericInterface genericInt = new GenericImpl();
               
          HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
          HashMap pageDtl=(HashMap)allPageDtl.get("STONESTRANS");
          if(pageDtl==null || pageDtl.size()==0){
          pageDtl=new HashMap();
          pageDtl=util.pagedef("STONESTRANS");
          allPageDtl.put("STONESTRANS",pageDtl);
          }
          info.setPageDetails(allPageDtl);
          
          
          
          ArrayList SinSttList = new ArrayList();
          ArrayList MixSttList = new ArrayList();
          ArrayList RghSttList = new ArrayList();
          if(pageDtl!=null && pageDtl.size() >0){  
           ArrayList  pageList= ((ArrayList)pageDtl.get("STT") == null)?new ArrayList():(ArrayList)pageDtl.get("STT");
             for(int j=0;j<pageList.size();j++){
                 HashMap pageDtlMap=(HashMap)pageList.get(j);
                 String fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 String fld_nme=(String)pageDtlMap.get("fld_nme");
                 ObjBean objBean = new ObjBean();
                 objBean.setNme(fld_nme);
                 objBean.setDsc(fld_ttl);
                 SinSttList.add(objBean);              
             }
              
               
            ArrayList MpageList= ((ArrayList)pageDtl.get("MSTT") == null)?new ArrayList():(ArrayList)pageDtl.get("MSTT");
            
            for(int j=0;j<MpageList.size();j++){
              HashMap pageDtlMap=(HashMap)MpageList.get(j);
              String fld_ttl=(String)pageDtlMap.get("fld_ttl");
              String fld_nme=(String)pageDtlMap.get("fld_nme");
              ObjBean objBean = new ObjBean();
              objBean.setNme(fld_nme);
              objBean.setDsc(fld_ttl);
               MixSttList.add(objBean);              
             }
            
            if(typ.equals("RGH")){
                  MpageList= ((ArrayList)pageDtl.get("RGHSTT") == null)?new ArrayList():(ArrayList)pageDtl.get("RGHSTT");
                 
                 for(int j=0;j<MpageList.size();j++){
                   HashMap pageDtlMap=(HashMap)MpageList.get(j);
                   String fld_ttl=(String)pageDtlMap.get("fld_ttl");
                   String fld_nme=(String)pageDtlMap.get("fld_nme");
                   ObjBean objBean = new ObjBean();
                   objBean.setNme(fld_nme);
                   objBean.setDsc(fld_ttl);
                    RghSttList.add(objBean);   
                          
                  }
             }
                
              
            }
          String lstNme = "TRANS_"+typ+info.getLogId()+"_"+util.getToDteGiveFrmt("ddMMyyyyhhmmss");

          if(typ.equals("MIX")){
              lstNme = "TRANSMIX_"+typ+info.getLogId()+"_"+util.getToDteGiveFrmt("ddMMyyyyhhmmss");
          ArrayList searchList = genericInt.genricSrch(req,res,"TRAN_CRT_MIX","TRAN_CRT_MIX");
          gtMgr.setValue("FRMSRCH"+typ, searchList);
           gtMgr.setValue("TOSRCH"+typ, searchList);
              udf.setFrmSttList(MixSttList);
              udf.setToSttList(MixSttList);
          }else if(typ.equals("RGH")){
              lstNme = "TRANSRGH_"+typ+info.getLogId()+"_"+util.getToDteGiveFrmt("ddMMyyyyhhmmss");
          ArrayList searchList = genericInt.genricSrch(req,res,"TRAN_CRT_RGH","TRAN_CRT_RGH");
          gtMgr.setValue("FRMSRCH"+typ, searchList);
          gtMgr.setValue("TOSRCH"+typ, searchList);
          udf.setFrmSttList(RghSttList);
          udf.setToSttList(RghSttList);
          }else{
              
              ArrayList searchList = genericInt.genricSrch(req,res,"TRAN_CRT_SIN","TRAN_CRT_SIN");
               gtMgr.setValue("FRMSRCH"+typ, searchList);
              searchList = genericInt.genricSrch(req,res,"TRAN_CRT_MIX","TRAN_CRT_MIX");
              gtMgr.setValue("TOSRCH"+typ, searchList);
              udf.setFrmSttList(SinSttList);
              udf.setToSttList(MixSttList);
          }   
          ASPrprViw(req,res,"TRN_MIX_VW");
          req.setAttribute("lstNme", lstNme);
          req.setAttribute("TYP", typ);
          finalizeObject(db, util);
          return am.findForward("load");   
      }
     }
    
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
         StonesTransferForm udf = (StonesTransferForm)af;
          String lstNme=util.nvl(req.getParameter("lstNme"));
          String typ = util.nvl(req.getParameter("TYP"));
          String mdl="TRN_SIN_VW";
          if(typ.equals("MIX"))
              mdl="TRN_MIX_VW";
          if(typ.equals("RGH"))
              mdl="TRN_RGH_VW";
          HashMap dbinfo = info.getDmbsInfoLst();
          String del_mixtomix = util.nvl((String)dbinfo.get("DEL_MIXTOMIX"));
          ArrayList genricSrchLst = (ArrayList)gtMgr.getValue("FRMSRCH"+typ);
          FormFile uploadFile = udf.getLoadFile();
          String file = util.nvl((String)udf.getValue("file"));
          String stt = (String)udf.getValue("sttFm");
          HashMap mprp = info.getMprp();
          HashMap prp = info.getPrp();
          String vnm = util.nvl((String)udf.getValue("vnmFrmLst"));
          HashMap stockList=null;
          ArrayList vnmFileLst=new ArrayList();
          HashMap fileDataMap = new HashMap();
          HashMap paramsMap = new HashMap();
          paramsMap.put("TYP", typ);
          
          if(uploadFile!=null){
          ArrayList ary=new ArrayList();
          ArrayList prpFileLst=new ArrayList();
          String fileName = uploadFile.getFileName();
          fileName = fileName.replaceAll(".csv", util.getToDteTime()+".csv");
          if(!fileName.equals("")){
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
          int lineNo=0;
          while ((line = lnr.readLine()) != null){
              String vnmFromFile = "";
              String prpFromFile = "";
              if((line.length() - (line.replaceAll(",","").length())) == 1)
                  line = line + ", ";
              if(line.substring(line.length()-1).equals(","))
                  line = line + " ";
                  String[] vals = line.split(",");
             if(vals.length > 1 && lineNo > 0) {
                  vnmFromFile = vals[0];
                  prpFromFile = vals[1];
                  vnmFileLst.add(vnmFromFile);
                  if(!prpFileLst.contains(prpFromFile))
                  prpFileLst.add(prpFromFile);
                  fileDataMap.put(vnmFromFile, prpFromFile);
              }
              lineNo++;
             
          }
          fileReader.close();

              vnm = vnmFileLst.toString();
              vnm = vnm.replace('[',' ');
              vnm = vnm.replace(']',' ');
              vnm=vnm.replaceAll(" ", "");
              
              String get1Vnm=(String)vnmFileLst.get(0);
              ary=new ArrayList();
              String sqlQ="select stt from mstk where vnm='"+get1Vnm+"'";
            ArrayList rsLst = db.execSqlLst("Details stt",sqlQ,ary);
            PreparedStatement pst = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
              while(rs.next()){
              stt=util.nvl(rs.getString("stt"));
              }
              rs.close();
              pst.close();
              if(prpFileLst.size()>0){
                  vnmFileLst=new ArrayList();
                  ArrayList lprpVBox_typ = (ArrayList)prp.get("BOX_TYPV");
                  ArrayList lprpPBox_id = (ArrayList)prp.get("BOX_IDP");
                  ArrayList lprpVBox_id = (ArrayList)prp.get("BOX_IDV");
                  ArrayList lprpvalcollection=new ArrayList();
                  String get1=(String)prpFileLst.get(0);
                  String mprpFinal="BOX_ID";
                  if(lprpVBox_typ.indexOf(get1) > -1)
                  mprpFinal="BOX_TYP";
                  
                  if(mprpFinal.equals("BOX_ID")){
                  for(int i=0;i<prpFileLst.size();i++){
                  String prpFile=(String)prpFileLst.get(i);
                  int index=lprpPBox_id.indexOf(prpFile);
                      if(index > -1){
                          lprpvalcollection.add(lprpVBox_id.get(index));
                      }
                  }       
                  }else
                  lprpvalcollection.addAll(prpFileLst);
                  
                  if(lprpvalcollection.size()> 0){
                      String lprpValLst = lprpvalcollection.toString();
                      lprpValLst = lprpValLst.replace('[',' ');
                      lprpValLst = lprpValLst.replace(']',' ');
                      lprpValLst=lprpValLst.trim();
                      lprpValLst=util.getVnm(lprpValLst);
                      
                      String filestt=util.nvl((String)udf.getValue("sttTo"));
                      String conQ=" and a.stt=?";
                      ary=new ArrayList();
                      ary.add(mprpFinal);
                      if(filestt.equals("MF_IN")){
                          conQ+=" and  a.idn = a.vnm";
                      }
                      ary.add(filestt);
                      sqlQ="select a.vnm,a.stt\n" + 
                      "from \n" + 
                      "mstk a,stk_dtl b\n" + 
                      "where \n" + 
                      "a.idn=b.mstk_idn and b.mprp=? and b.grp=1 and b.val in("+lprpValLst+") "+conQ+" and a.pkt_ty='MIX'";
                     rsLst = db.execSqlLst("Details VNM",sqlQ,ary);
                    pst = (PreparedStatement)rsLst.get(0);
                     rs = (ResultSet)rsLst.get(1);
                      while(rs.next()){
                      vnmFileLst.add(util.nvl(rs.getString("vnm")));
                      }
                      rs.close();
                      pst.close();
                  }
              }
          }
          }
          if(vnm.equals("")){
          info.setGncPrpLst(genricSrchLst);
              for(int i=0;i<genricSrchLst.size();i++){
                  ArrayList prplist =(ArrayList)genricSrchLst.get(i);
                  String lprp = (String)prplist.get(0);
                  String flg= (String)prplist.get(1);
                  String ltyp = util.nvl((String)mprp.get(lprp+"T"));
                  String prpSrt = lprp ;  
                  if(flg.equals("M")  || flg.equals("CTA")) {
                  
                      ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                      ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                      ArrayList lprpP = (ArrayList)prp.get(prpSrt + "P");
                      if(flg.equals("M")) {
                      for(int j=0; j < lprpS.size(); j++) {
                      String lSrt = (String)lprpS.get(j);
                      String lVal = (String)lprpV.get(j);    
                      String reqVal1 = util.nvl((String)udf.getValue(lprp + "F_" + lVal),"");
                      if(!reqVal1.equals("")){
                      paramsMap.put(lprp + "_" + lVal, reqVal1);
                      }}
                        }else if(flg.equals("CTA")){
                        String reqVal1 = util.nvl((String)udf.getValue(lprp + "_F1"),"");
                        if(!reqVal1.equals("")){
                        ArrayList fmtVal = util.getStrToArr(reqVal1);
                        for(int j=0; j < lprpS.size(); j++) {
                        String lSrt = (String)lprpS.get(j);
                        String lVal = (String)lprpV.get(j);   
                        String lprt = (String)lprpP.get(j);
                        if(fmtVal.contains(lVal) || fmtVal.contains(lprt)){
                        reqVal1 = lVal;
                        paramsMap.put(lprp + "_" + lVal, reqVal1);
                        }
                        } 
                        }        
                    }
                  }else{
                  String fldVal1 = util.nvl((String)udf.getValue(lprp+"_F1"));
                  String fldVal2 = util.nvl((String)udf.getValue(lprp+"_F2"));
                  if(ltyp.equals("T")){
                      fldVal1 = util.nvl((String)udf.getValue(lprp+"_F1"));
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
              if(paramsMap.size()>1){
              paramsMap.put("stt", stt);
              paramsMap.put("mdl", mdl);
              if(typ.equals("MIX"))
                paramsMap.put("MIX", "Y");
              if(typ.equals("RGH"))
                paramsMap.put("PRCD", "ROUGH");
              util.genericSrch(paramsMap);
              stockList = SearchResult(req,res,mdl);
              }else{
                  paramsMap.put("stt", stt);
                  paramsMap.put("mdl", mdl);
                 
                  
                  stockList = FetchResult(req,res,paramsMap);
              }
          }else{
              paramsMap.put("stt", stt);
              paramsMap.put("mdl", mdl);
              paramsMap.put("vnm", vnm);
             
              stockList = FetchResult(req,res,paramsMap);
          }
          
          SearchQuery srchQury = new SearchQuery();
          if(stockList!=null)
          stockList = (HashMap)srchQury.sortByComparatorMap(stockList);
          gtMgr.setValue(lstNme+"_FMLST", stockList);
          
          genricSrchLst = (ArrayList)gtMgr.getValue("TOSRCH"+typ);
           stt = (String)udf.getValue("sttTo");
           vnm = util.nvl((String)udf.getValue("vnmToLst"));
          if(!file.equals("")){
              vnm = vnmFileLst.toString();
              vnm = vnm.replace('[',' ');
              vnm = vnm.replace(']',' ');
          }
           stockList=new HashMap();
           paramsMap = new HashMap();
          paramsMap.put("TYP", typ);
          if(vnm.equals("")){
              info.setGncPrpLst(genricSrchLst);

              for(int i=0;i<genricSrchLst.size();i++){
                  ArrayList prplist =(ArrayList)genricSrchLst.get(i);
                  String lprp = (String)prplist.get(0);
                  String flg= (String)prplist.get(1);
                  String ltyp = util.nvl((String)mprp.get(lprp+"T"));
                  String prpSrt = lprp ;  
                  if(flg.equals("M") || flg.equals("CTA")) { 
                  
                      ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                      ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                      ArrayList lprpP = (ArrayList)prp.get(prpSrt + "P");

                      if(flg.equals("M")){
                      for(int j=0; j < lprpS.size(); j++) {
                      String lSrt = (String)lprpS.get(j);
                      String lVal = (String)lprpV.get(j);    
                      String reqVal1 = util.nvl((String)udf.getValue(lprp + "T_" + lVal),"");
                      if(!reqVal1.equals("")){
                      paramsMap.put(lprp + "_" + lVal, reqVal1);
                      }
                         
                      }
                      }else if(flg.equals("CTA")){
                      String reqVal1 = util.nvl((String)udf.getValue(lprp + "_T1"),"");
                      if(!reqVal1.equals("")){
                      ArrayList fmtVal = util.getStrToArr(reqVal1);
                      for(int j=0; j < lprpS.size(); j++) {
                      String lSrt = (String)lprpS.get(j);
                      String lVal = (String)lprpV.get(j);   
                      String lprt = (String)lprpP.get(j);
                      if(fmtVal.contains(lVal) || fmtVal.contains(lprt)){
                      reqVal1 = lVal;
                      paramsMap.put(lprp + "_" + lVal, reqVal1);
                      }
                      } 
                      }        
                      }
                  }else{
                  String fldVal1 = util.nvl((String)udf.getValue(lprp+"_T1"));
                  String fldVal2 = util.nvl((String)udf.getValue(lprp+"_T2"));
                  if(ltyp.equals("T")){
                      fldVal1 = util.nvl((String)udf.getValue(lprp+"_T1"));
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
              if(paramsMap.size()>1){
              paramsMap.put("stt", stt);
              if(typ.equals("RGH")){
                paramsMap.put("PRCD", "ROUGH");
                paramsMap.put("mdl", "TRN_RGH_VW");
              }else{
              paramsMap.put("mdl", "TRN_MIX_VW");
              paramsMap.put("MIX", "Y");
              }
              util.genericSrch(paramsMap);
              
              if(del_mixtomix.equals("Y")){
                      int ct =db.execUpd(" Del Old Pkts ", "delete gt_srch_rslt where stt='MF_IN' and stk_idn <> vnm", new ArrayList());
              }
              stockList = SearchResult(req,res,mdl);
              }else{
                  paramsMap.put("stt", stt);
                
                  if(typ.equals("RGH")){
                    paramsMap.put("PRCD", "ROUGH");
                    paramsMap.put("mdl", "TRN_RGH_VW");
                  }else{
                    paramsMap.put("mdl", "TRN_MIX_VW");
                      paramsMap.put("TYP", "MIX");
                  }
                  stockList = FetchResult(req,res,paramsMap);
              }
          }else{
              paramsMap.put("stt", stt);
              paramsMap.put("vnm", vnm);
              if(typ.equals("RGH")){
                paramsMap.put("PRCD", "ROUGH");
                paramsMap.put("mdl", "TRN_RGH_VW");
              }else{
                paramsMap.put("mdl", "TRN_MIX_VW");
                  paramsMap.put("TYP", "MIX");
              }
              stockList = FetchResult(req,res,paramsMap);
          }
          stockList = (HashMap)srchQury.sortByComparatorMap(stockList);
          gtMgr.setValue(lstNme+"_TOLST", stockList);
          
          
          req.setAttribute("lstNme", lstNme);
          req.setAttribute("TYP", typ);
          req.setAttribute("view", "Y");
          finalizeObject(db, util);
          return am.findForward("load");  
      }}
    
    
    
    public ActionForward Save(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
         StonesTransferForm udf = (StonesTransferForm)af;
          ArrayList FrmstkIdnLst = new ArrayList();
          ArrayList TostkIdnLst = new ArrayList();
          Enumeration reqNme = req.getParameterNames();
          while(reqNme.hasMoreElements()) {
              String paramNm = (String) reqNme.nextElement();
             if (paramNm.indexOf("CHK_") > -1) {
                  String val = req.getParameter(paramNm);
                 FrmstkIdnLst.add(val);
              }
              if (paramNm.indexOf("CHKR_") > -1){
                   String val = req.getParameter(paramNm);
                  TostkIdnLst.add(val);
               }
          }
          int ct=0;
         
         
          String msg="";
          ArrayList ary=null;
          String lstNme=util.nvl(req.getParameter("lstNme"));
          String typ = util.nvl(req.getParameter("TYP"));
          String trans = util.nvl((String)udf.getValue("submit"));
          String repair = util.nvl((String)udf.getValue("repairing"));
          String avgCal =util.nvl(req.getParameter("avgCal"));
          String fStt = util.nvl((String)udf.getValue("sttFm"));
          String tStt = util.nvl((String)udf.getValue("sttTo"));
          String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
          
        try {
              db.setAutoCommit(false); 
              String TostkIdn = req.getParameter("toID");
            if(trans.equals("Transfer")){
             if (TostkIdnLst != null && TostkIdnLst.size() > 0) {
                 int tosize =TostkIdnLst.size();
                  for(int i = 0; i < tosize ; i++){
                      String stkIdn = (String)TostkIdnLst.get(i);
                   int curQty =Integer.parseInt(util.nvl(req.getParameter("CUR_TQTY_"+stkIdn),"0"));
                   double curCts =Double.parseDouble(util.nvl(req.getParameter("CUR_TCTS_"+stkIdn),"0"));
                    double curRte =Double.parseDouble(util.nvl(req.getParameter("CUR_TRTE_"+stkIdn),"0"));
                   int addQty = Integer.parseInt(util.nvl((String)udf.getValue("ADD_QTY_"+stkIdn),"0"));
                   double  addCts = Double.parseDouble(util.nvl((String)udf.getValue("ADD_CTS_"+stkIdn),"0"));
                   double  ttlRte = Double.parseDouble(util.nvl((String)udf.getValue("ADD_RTE_"+stkIdn),"0"));
                   double ttlCts = util.roundToDecimals(curCts+addCts, 3);
                   double fnlRte=0.0;
                     ary = new ArrayList();
                     ary.add(String.valueOf(addQty));
                     ary.add(String.valueOf(addCts));
                     String updQty = "update mstk set qty=(nvl(qty,0)+ nvl(?, 0)) , cts = (nvl(cts,0) + nvl(?, 0)) where idn=?";
                    if(avgCal.equals("Y")){
                          fnlRte = ((curCts*curRte)+ (addCts*ttlRte))/ttlCts ;
                         ary.add(fnlRte);
                        updQty = "update mstk set upr=? where idn=?";   
                      }
                     ary.add(stkIdn);
                     ct = db.execUpd("update mstk", updQty, ary);
                      
                    
                      if(ct>0){
                          ary = new ArrayList();
                          ary.add("ADD");
                          ary.add(stkIdn);
                          ary.add(String.valueOf(addCts));
                          ary.add(String.valueOf(ttlRte));
                          ct =db.execCallDir("MIX_TO_MIX_TRF_AVG", "MIX_TO_MIX_TRF_AVG(pTyp => ?  , pStkIdn => ? , pCts => ?, pRte => ? )", ary);
                          
                       
                          
                          ary = new ArrayList();
                          ary.add(stkIdn);
                          ary.add("RTE");
                          ary.add(String.valueOf(ttlRte));
                         String stockUpd =
                              "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                          db.execCallDir("update stk Dtl", stockUpd, ary);
                      }
                      if(tosize > 1){
                         String insertSql= "INSERT INTO MIX_TRF_LOG(LOG_DTE, FRM_IDN, TO_IDN, QTY, CTS,UPR,TYP,RMK,UNM)"+
                                            "VALUES(SYSDATE, ? ,?, ?, ?,?,?,?, PACK_VAR.GET_USR)";
                         
                          ary = new ArrayList();
                          ary.add("0");
                          ary.add(stkIdn);
                          ary.add(String.valueOf(addQty));
                          ary.add(String.valueOf(addCts));
                          ary.add(String.valueOf(ttlRte));
                          if(typ.equals("RGH"))
                              ary.add("RGH BOX TRNS");
                          else
                              ary.add("BOX TRNS");
                           
                          ary.add("BOX Transfer");
                          ct = db.execUpd("log insert", insertSql, ary);
                          
                      }else{
                          TostkIdn=stkIdn;
                      }

                     }
             }else{
                
                      if(TostkIdn.equals("NEW")){
                         
                         
                         String qty = (String)udf.getValue("FNL_QTY_NEW");
                         String cts = (String)udf.getValue("FNL_CTS_NEW");
                         String pRte = (String)udf.getValue("FNL_RTE_NEW");
                         String insMst =
                             "MIX_PKG.GEN_PKT(pStt => ?, pPktTyp => ?, pQty =>?, pCts => ?,pIdn =>?,pVnm =>?)";
                         ary = new ArrayList();
                         ary.add(tStt);
                         ary.add(util.nvl(typ,"MIX"));
                         ary.add(qty);
                         ary.add(cts);
                         ArrayList out = new ArrayList();
                         out.add("I");
                         out.add("V");
                         String lotNo = util.nvl((String)udf.getValue("LOTNO"));
                         String lotIdn="";
                         if(!lotNo.equals("")){
                             String lotDsc = "select idn from mlot where dsc ='"+lotNo+"'";
                             ArrayList params = new ArrayList();
                             
                             ArrayList rsLst = db.execSqlLst("lotDsc", lotDsc, params);
                             PreparedStatement pst = (PreparedStatement)rsLst.get(0);
                             ResultSet rs = (ResultSet)rsLst.get(1);
                             if(rs.next()) {
                                 lotIdn = rs.getString("idn");
                             }else{
                                ResultSet rs1=db.execDirSql("lotIdn", "select SEQMLOT.NEXTVAL from dual", new ArrayList());
                                 if(rs1.next()){
                                     lotIdn = rs1.getString(1);
                                    ArrayList  params1 = new ArrayList();
                                     params1.add(lotIdn);
                                     params1.add(lotNo);
                                     params1.add("1");
                                     params1.add("1");
                                     db.execDirUpd("insert mot", "insert into mlot(idn,dsc,cnum,cinr) select ? , ? , ? ,? from dual ", params1);
                                     
                                 }
                                 rs1.close();
                                 
                                 
                             }
                             rs.close();
                             pst.close();
                             insMst = "MIX_PKG.GEN_PKT(pStt => ?, pPktTyp => ?, pQty =>?, pCts => ?, pLotIdn => ? ,pIdn =>?,pVnm =>?)";
                             ary.add(lotIdn);                     
                         }
                         
                         CallableStatement cst = db.execCall("findMstkId", insMst, ary, out);
                         TostkIdn =String.valueOf(cst.getLong(ary.size() + 1));
                           cst.close();
                           cst=null;
                         ArrayList vwPrpToLst = (ArrayList)session.getAttribute("TRN_"+util.nvl(typ,"MIX")+"_VW");
                         if(vwPrpToLst==null)
                             vwPrpToLst=new ArrayList();
                         vwPrpToLst.add("RTE");
                         String box_id="";
                         for (int i = 0; i < vwPrpToLst.size(); i++) {
                             String lprp = (String)vwPrpToLst.get(i);
                             String val =util.nvl((String)udf.getValue(lprp));
                             if ((val != null) && (val.length() > 0)) {
                                 if(lprp.equals("RTE"))
                                     val=pRte;
                                 if(lprp.equals("CRTWT"))
                                     val=cts;
                                 ary = new ArrayList();
                                 ary.add(TostkIdn);
                                 ary.add(lprp);
                                 ary.add(val);
                                 String stockUpd =
                                     "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                                 db.execCallDir("update stk Dtl", stockUpd, ary);
                             }
                             if(lprp.equals("BOX_ID") && (val != null) && (!val.equals("0") && (val.length() > 0))){
                                   String box_typ = "";
                                 box_id=val;
                                   if(val.indexOf("-")> -1)
                                       box_typ=val.substring(0, val.indexOf("-"));
                                   if(val.indexOf("_")> -1 && box_typ.equals(""))
                                      box_typ=val.substring(0, val.indexOf("_"));
//                                 String box_typ = val.substring(0, val.indexOf("-"));
//                                 if(box_typ!=null && box_typ.length()>1)
//                                     box_typ = val.substring(0, val.indexOf("_"));
                                 ary = new ArrayList();
                                 ary.add(TostkIdn);
                                 ary.add("BOX_TYP");
                                 ary.add(box_typ);
                                 String stockUpd =
                                     "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                                 db.execCallDir("update stk Dtl", stockUpd, ary);
                                 
                             }
                             
                             }
                         
                             ary = new ArrayList();
                             ary.add(TostkIdn);
                             ary.add("RTE");
                             ary.add(pRte);
                             String stockUpd =
                                 "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                             db.execCallDir("update stk Dtl", stockUpd, ary);
                             
                             if(!vwPrpToLst.contains("LOC")){
                             ary = new ArrayList();
                             ary.add(TostkIdn);
                             ary.add("LOC");
                             ary.add("MUMBAI");
                              stockUpd =
                                 "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                             db.execCallDir("update stk Dtl", stockUpd, ary);
                             }
                             
                             if(vwPrpToLst.contains("STK_CTG")){
                             ary = new ArrayList();
                             ary.add(TostkIdn);
                             ary.add("STK_CTG");
                            ary.add(TostkIdn);
                              stockUpd =
                                 "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> GET_STK_CTG(?)  )";
                             db.execCallDir("update stk Dtl", stockUpd, ary);
                             }
                             
                             ary = new ArrayList();
                             ary.add(TostkIdn);
                             ary.add("FA_PRI");
                             ary.add(pRte);
                            stockUpd =
                                 "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                             db.execCallDir("update stk Dtl", stockUpd, ary);
                             
                             ary = new ArrayList();
                             ary.add(TostkIdn);
                             ary.add("RECPT_DT");
                           
                              stockUpd =
                             "Stk_Pkg.Pkt_Prp_Upd(pIdn => ?, pPrp => ?, pVal => To_Char(sysdate, 'dd-MON-rrrr'))";
                             db.execCallDir("update stk Dtl", stockUpd, ary);
                             
                             ary = new ArrayList();
                             ary.add(TostkIdn);
                             db.execCallDir("update STK SRT", "STK_SRT(Pid => ?)", ary);
                             pRte = util.nvl(pRte);
                             box_id = util.nvl(box_id);
                             if(pRte.equals("") && !box_id.equals("")){
                                 String sql="select GET_MIX_BOX_PRI('MKT','BOX_ID',?) rte from dual";
                                 ary = new ArrayList();
                                 ary.add(box_id);
                                 ResultSet rs = db.execSql("sql", sql, ary);
                                 if(rs.next()){
                                     pRte=rs.getString("rte");
                                 }
                                 rs.close();
                                 ary = new ArrayList();
                                 ary.add(TostkIdn);
                                 ary.add("RTE");
                                 ary.add(pRte);
                                  stockUpd =
                                     "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                                 db.execCallDir("update stk Dtl", stockUpd, ary);
                                 
                                 ary = new ArrayList();
                                 ary.add(TostkIdn);
                                 ary.add("CP");
                                 ary.add(pRte);
                                  stockUpd =
                                     "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                                 db.execCallDir("update stk Dtl", stockUpd, ary);
                                 
                             }
                             
                             String insertSql= "INSERT INTO MIX_TRF_LOG(LOG_DTE, FRM_IDN, TO_IDN, QTY, CTS,UPR,TYP,RMK,UNM)"+
                                                "VALUES(SYSDATE, ? ,?, ?, ?,?,?,?, PACK_VAR.GET_USR)";
                             
                              ary = new ArrayList();
                              ary.add("0");
                              ary.add(TostkIdn);
                              ary.add(qty);
                              ary.add(cts);
                              ary.add(pRte);
                             if(typ.equals("RGH"))
                             ary.add("BOX TRNS");
                             else
                              ary.add("RGH BOX TRNS");
                              ary.add("BOX Transfer");
                              ct = db.execUpd("log insert", insertSql, ary);
                         }
                 TostkIdnLst.add(TostkIdn);
                 
             }}
                    if (FrmstkIdnLst != null && FrmstkIdnLst.size() > 0) {
                        String grp="";
                        String grp1Str="select grp1 from df_Stk_stt where stt=?";
                        ary = new ArrayList();
                        ary.add(fStt);
                        ArrayList outLst = db.execSqlLst("grp1Str", grp1Str, ary);
                        
                        PreparedStatement pst = (PreparedStatement)outLst.get(0);
                        ResultSet rs = (ResultSet)outLst.get(1);
                        while(rs.next()){
                           grp=rs.getString("grp1");
                        }
                        rs.close();
                        pst.close();
                        
                        
                        for (int i = 0; i < FrmstkIdnLst.size(); i++) {
                       
                            String stkIdn = (String)FrmstkIdnLst.get(i);
                            int  trfQty =Integer.parseInt(util.nvl((String)udf.getValue("TRN_QTY_"+stkIdn),"0"));
                            double  trfCts =Double.parseDouble(util.nvl((String)udf.getValue("TRN_CTS_"+stkIdn),"0"));
                            double  ttlRte = Double.parseDouble(util.nvl((String)udf.getValue("TRN_RTE_"+stkIdn),"0"));
                            
                            if(typ.equals("MIX") || typ.equals("RGH")){
                            int curQty =Integer.parseInt(util.nvl(req.getParameter("CUR_FQTY_"+stkIdn),"0"));
                            double curCts =Double.parseDouble(util.nvl(req.getParameter("CUR_FCTS_"+stkIdn),"0"));
                            double curRte =Double.parseDouble(util.nvl(req.getParameter("CUR_FRTE_"+stkIdn),"0"));
                            double fCts = curCts-trfCts;
                            trfQty = Integer.parseInt(util.nvl((String)udf.getValue("TRN_QTY_"+stkIdn),"0"));
                            trfCts = Double.parseDouble(util.nvl((String)udf.getValue("TRN_CTS_"+stkIdn),"0"));
                             double ttlCts = util.roundToDecimals(curCts-trfCts, 3);
                                
                               
                            if(typ.equals("RGH")){
                           double fnlRte = ((curCts*curRte)- (trfCts*ttlRte))/fCts ;
                                fnlRte = util.roundToDecimals(fnlRte,0);
                                ary = new ArrayList();
                                ary.add(stkIdn);
                                ary.add("CP");
                                ary.add(String.valueOf(fnlRte));
                                String  stockUpd =
                                    "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                                db.execCallDir("update stk Dtl", stockUpd, ary);
                                
                            }
                           
                            if(ct>0){
                                ary = new ArrayList();
                                ary.add("MINS");
                                ary.add(stkIdn);
                                ary.add(String.valueOf(trfCts));
                                ary.add(String.valueOf(ttlRte));
                                ct =db.execCallDir("MIX_TO_MIX_TRF_AVG", "MIX_TO_MIX_TRF_AVG(pTyp => ?  , pStkIdn => ? , pCts => ?, pRte => ? )", ary);
                                
                            }
                            
                            if(trans.equals("Transfer")){
                            String insertSql= "INSERT INTO MIX_TRF_LOG(LOG_DTE, FRM_IDN, TO_IDN, QTY, CTS,TYP,RMK,UPR,UNM)"+
                                               "VALUES(SYSDATE, ? ,?, ?, ?,?,?,?, PACK_VAR.GET_USR)";
                            
                             ary = new ArrayList();
                             ary.add(stkIdn);
                             ary.add(TostkIdn);
                             ary.add(String.valueOf(trfQty));
                             ary.add(String.valueOf(trfCts));
                             if(typ.equals("RGH"))
                             ary.add("RGH BOX TRNS");
                             else
                              ary.add("BOX TRNS");  
                             ary.add("BOX Transfer");
                             ary.add(String.valueOf(ttlRte));
                             ct = db.execUpd("log insert", insertSql, ary);
                            }else{
                                String insertSql= "INSERT INTO MIX_TRF_LOG(LOG_DTE, FRM_IDN, TO_IDN, QTY, CTS,TYP,RMK,UPR,UNM)"+
                                                   "VALUES(SYSDATE, ? ,?, ?, ?,?,?,?, PACK_VAR.GET_USR)";
                                
                                 ary = new ArrayList();
                                 ary.add(stkIdn);
                                 ary.add("0");
                                 ary.add(String.valueOf(trfQty));
                                 ary.add(String.valueOf(trfCts));
                                ary.add("RPRLOSS");
                                 ary.add("Repairing Loss");
                                 ary.add(String.valueOf(ttlRte));
                                 ct = db.execUpd("log insert", insertSql, ary);
                            }
                            
                                 if(ttlCts>0 || fStt.equals("MKAV")){
                                 
                                ary = new ArrayList();
                                ary.add(String.valueOf(trfQty));
                                ary.add(String.valueOf(trfCts));
                                ary.add(stkIdn);
                                String updQty = "update mstk set qty=(nvl(qty,0)- nvl(?, 0)) , cts =  greatest(nvl(cts,0) - nvl(?, 0), 0)  where idn=?";
                                ct = db.execUpd("update mstk", updQty, ary);
                                
                                
                                     
                             String insMst =
                                    "MIX_PKG.GEN_PKT(pStt => ?,pPktRt=>?, pPktTyp => ?, pQty =>?, pCts => ?,pIdn =>?,pVnm =>?)";
                                ary = new ArrayList();
                                ary.add(grp+"_MRG");
                                ary.add(stkIdn);
                                ary.add(util.nvl(typ,"MIX"));
                                ary.add(String.valueOf(trfQty));
                                ary.add(String.valueOf(trfCts));
                                ArrayList out = new ArrayList();
                                out.add("I");
                                out.add("V");
                                
                             CallableStatement cst = db.execCall("findMstkId", insMst, ary, out);
                             String mrgPkt =String.valueOf(cst.getLong(ary.size() + 1));
                              cst.close();
                              cst=null;
                            }else{
                                ary = new ArrayList();
                                ary.add(grp+"_MRG");
                                ary.add(stkIdn);
                                String updMstk = "update mstk set stt=?  where idn=?";
                                ct = db.execUpd("update mstk", updMstk, ary);
                            }
                            
                           
                            
                            
                            }else{
                                ary = new ArrayList();
                                ary.add("MIX_MRG");
                                ary.add(stkIdn);
                                String updQty = "update mstk set stt=?  where idn=?";
                                ct = db.execUpd("update mstk", updQty, ary);
                                
                                String insertSql= "INSERT INTO MIX_TRF_LOG(LOG_DTE, FRM_IDN, TO_IDN, QTY, CTS,TYP,RMK,UPR,UNM)"+
                                                   "VALUES(SYSDATE, ? ,?, ?, ?,?,?,?, PACK_VAR.GET_USR)";
                                
                                 ary = new ArrayList();
                                 ary.add(stkIdn);
                                 ary.add(TostkIdn);
                                 ary.add(String.valueOf(trfQty));
                                 ary.add(String.valueOf(trfCts));
                                if(typ.equals("RGH"))
                                ary.add("RGH BOX TRNS");
                                else
                                 ary.add("BOX TRNS");
                                 ary.add("BOX Transfer");
                                 ary.add(String.valueOf(ttlRte));
                                 ct = db.execUpd("log insert", insertSql, ary);
                                
                            }
                        }
                    }
            if(typ.equals("RGH")){
                if(TostkIdnLst.size()==1 && FrmstkIdnLst.size()>=1){
                    double ttlVal=0;
                    double ttlCts=0;
                    double cpRte=0;
                    for (int i = 0; i < FrmstkIdnLst.size(); i++) {
                    String stkIdn = (String)FrmstkIdnLst.get(i);
                    double  ttlRte = Double.parseDouble(util.nvl((String)udf.getValue("TRN_RTE_"+stkIdn),"0"));
                    double  trfCts =Double.parseDouble(util.nvl((String)udf.getValue("TRN_CTS_"+stkIdn),"0"));
                    double curVal = (trfCts*ttlRte);
                     cpRte = (ttlVal+curVal)/(ttlCts+trfCts);
                    ttlCts=ttlCts+trfCts;
                    ttlVal=ttlVal+curVal;
                }
                    cpRte = util.roundToDecimals(cpRte,0);
                    
                 String stkIdn = (String)TostkIdnLst.get(0);
                    ary = new ArrayList();
                    ary.add(stkIdn);
                    ary.add("CP");
                    ary.add(String.valueOf(cpRte));
                   String  stockUpd =
                        "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                    db.execCallDir("update stk Dtl", stockUpd, ary);
                    
                    
                }
                
                if(TostkIdnLst.size()>=1 && FrmstkIdnLst.size()==1){
                    String stkIdn = (String)FrmstkIdnLst.get(0);
                    double  ttlRte = Double.parseDouble(util.nvl((String)udf.getValue("TRN_RTE_"+stkIdn),"0"));
                    ary = new ArrayList();
                    ary.add(stkIdn);
                    ary.add("CP");
                     ary.add(String.valueOf(ttlRte));
                    String  stockUpd =
                        "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                    db.execCallDir("update stk Dtl", stockUpd, ary);
                    
                
                }
                
            }
            if(cnt.equalsIgnoreCase("AG")){
                ary = new ArrayList();
                ary.add("MKAV");
                ary.add("MIXTRAN");
                String  MERGE =
                    "DP_MERGE_MIX_PKTS(pStt => ?, pTyp => ?)";
                db.execCallDir("update stk Dtl", MERGE, ary);
            }
          } catch (Exception e) {
            db.doRollBack();
                    // TODO: Add catch code
                    e.printStackTrace();
          } finally {
              db.setAutoCommit(true);           
          }
        if(ct>0){
        db.doCommit();
            msg="Process Done Successfully...";
        }else{
            db.doRollBack();
            msg="Some error in process";
        }
         
         req.setAttribute("msg", msg);     
         udf.reset();
          req.setAttribute("lstNme", lstNme);
          req.setAttribute("TYP", typ);
          GtMgrReset(req, lstNme, "");
          finalizeObject(db, util);
       return am.findForward("load");  
      }
      
    }
    
    public HashMap FetchResult(HttpServletRequest req,HttpServletResponse res, HashMap  paramMap ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String stt = util.nvl((String)paramMap.get("stt"));
        String vnm = util.nvl((String)paramMap.get("vnm"));
        String typ = util.nvl((String)paramMap.get("TYP"));
        String mdl=util.nvl((String)paramMap.get("mdl"));
        ArrayList ary = null;
        HashMap stockList= new HashMap();
   
        ArrayList delAry=new ArrayList();
        delAry.add("Z");
        String delQ = " Delete from gt_srch_rslt where flg in (?)";
        int ct =db.execUpd(" Del Old Pkts ", delQ, delAry);
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn , vnm , pkt_dte, stt , flg , qty , cts , rmk,sk1 ,quot) " + 
        " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty-nvl(qty_iss,0), cts-nvl(cts_iss,0), tfl3 ,sk1 , nvl(upr,cmp) "+
        "     from mstk b "+
        " where stt =?  and  (nvl(b.cts, 0) - nvl(b.cts_iss, 0)) > 0 ";
         if(!vnm.equals("")){
         vnm = util.getVnm(vnm);
         srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))" ;
        }
        
        if(typ.equals("MIX"))
            srchRefQ = srchRefQ+" and b.pkt_ty <> 'NR'";
        else if(typ.equals("RGH"))
            srchRefQ = srchRefQ+" and b.pkt_ty = 'RGH'";
        else
            srchRefQ = srchRefQ+" and b.pkt_ty = 'NR'";
        ary = new ArrayList();
        ary.add(stt);
      
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add(mdl);
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        stockList = SearchResult(req ,res , mdl);
            
        finalizeObject(db, util);
        return stockList;
    }
    
    
    public HashMap SearchResult(HttpServletRequest req ,HttpServletResponse res, String mdl ){
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
        String  srchQ =  " select stk_idn , pkt_ty,sk1,  vnm, pkt_dte, stt , qty ,to_char(quot*cts,'99990.90') amt,to_char(cts,'99990.900') cts , srch_id , rmk ,to_char(quot,'99990.90') quot , GET_MIX_PRI(stk_idn,'MKT') fnlPri  ";

        

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
        ArrayList rsLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        try {
            while(rs.next()) {
                String stk_idn= util.nvl(rs.getString("stk_idn"));
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.put("vnm",vnm);
                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                pktPrpMap.put("qty",util.nvl(rs.getString("qty"),"0"));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts"),"0").trim());
                pktPrpMap.put("quot",util.nvl(rs.getString("quot")).trim());
                pktPrpMap.put("stt",util.nvl(rs.getString("stt")));
                    pktPrpMap.put("amt",util.nvl(rs.getString("amt")));
                pktPrpMap.put("fnlPri",util.nvl(rs.getString("fnlPri")));
                    pktPrpMap.put("SK1",util.nvl(rs.getString("sk1")));
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
              
                }
            rs.close(); 
            pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
      
        finalizeObject(db, util);
        return pktList;
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
             
                
              ArrayList rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = ? and flg='Y' order by rnk ",
                               ary);
              PreparedStatement pst = (PreparedStatement)rsLst.get(0);
              ResultSet rs1 = (ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close(); 
                pst.close();
                session.setAttribute(mdl, asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        finalizeObject(db, util);
        return asViewPrp;
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
    
    public void GtMgrReset(HttpServletRequest req,String lstNme,String initial){
          HttpSession session = req.getSession(false);
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
         HashMap gtMgrMap = (HashMap)gtMgr.getValues();
         if(lstNme!=null && !lstNme.equals("")){
             gtMgrMap.remove(lstNme+"_FRMSRCH");
             gtMgrMap.remove(lstNme);
             gtMgrMap.remove(lstNme+"_TOSRCH");
             gtMgrMap.remove(lstNme+"_FRMSRCH");
             gtMgrMap.remove(lstNme+"_TOSRCH");
             gtMgrMap.remove(lstNme+"_FMLST");
             gtMgrMap.remove(lstNme+"_TOLST");
         }else{
             ArrayList KeyList = new ArrayList();
         Set<String> keys = gtMgrMap.keySet();
             for(String key: keys){
                 if(key.indexOf(initial)!=-1)
                     KeyList.add(key);
             }
             for(int i=0;i<KeyList.size();i++){
                 String ky =(String)KeyList.get(i); 
                 gtMgrMap.remove(ky);
             }
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
                util.updAccessLog(req,res,"Mix Assort Rtn", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Mix Assort Rtn", "init");
            }
            }
            return rtnPg;
            }
   
}
