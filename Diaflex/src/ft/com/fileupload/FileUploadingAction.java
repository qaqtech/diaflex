package ft.com.fileupload;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.JsonDao;

import ft.com.dao.JsonOutPut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;

import java.sql.Connection;

import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;

import java.util.Iterator;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import org.json.JSONException;
import org.json.JSONObject;

public class FileUploadingAction extends DispatchAction {
    
  
    
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
            util.updAccessLog(req,res,"File Load", "load start");
        FileUploadForm uploadForm = (FileUploadForm)form;
            uploadForm.reset();
        FileUploadInterface fileUploadInt = new FileUploadImpl();
        ArrayList fileList = fileUploadInt.fileUploadTypList(req,res,uploadForm);
        uploadForm.setUploadList(fileList);
            util.updAccessLog(req,res,"File Load", "load end");
        return am.findForward("load");
        }
    }
    
    public ActionForward upload(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"File Load", "load start");
        FileUploadForm uploadForm = (FileUploadForm)form;
        FileUploadInterface fileUploadInt = new FileUploadImpl();
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            String repPath = (String)dbinfo.get("REP_PATH");
            FormFile uploadFile = uploadForm.getFileUpload();
            String uploadTyp = (String)uploadForm.getValue("typ");
            String dteFrmt = util.nvl(req.getParameter("DTE_"+uploadTyp));
            if(dteFrmt.equals(""))
                dteFrmt="DD-MON-YYYY";
            uploadForm.setValue("DTEFMT", dteFrmt);
           ArrayList dataList = new ArrayList();
            int   file_idn = 0;
            boolean hedVerified=true;
            String msg=null;
            if(uploadFile!=null){
                String fileName = uploadFile.getFileName();
              if(!fileName.equals("")){
                 int fileSeq = fileUploadInt.InsertFileHdr(req, res, uploadTyp);
                 if(fileSeq>0){
                 fileName = cnt+"_"+file_idn+"_"+fileName;
                 String path = getServlet().getServletContext().getRealPath("/") + fileName; 
                 File readFile = new File(path);
                     if(!readFile.exists()){
                     FileOutputStream fileOutStream = new FileOutputStream(readFile);

                     fileOutStream.write(uploadFile.getFileData());

                     fileOutStream.flush();

                     fileOutStream.close();
                     }
                     
                 if(fileName.indexOf(".xls") > -1){
                         
                             FileInputStream myInput = new FileInputStream(readFile);
                             POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
                             HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
                             HSSFSheet mySheet = myWorkBook.getSheetAt(0);
                             Iterator rowIter = mySheet.rowIterator();
                             int previousCell;
                             int currentCell;
                             while (rowIter.hasNext()) {
                             HSSFRow myRow = (HSSFRow) rowIter.next();
                             Iterator cellIter = myRow.cellIterator();
                             String rowData = "";
                             previousCell = -1;
                             currentCell = 0;
                             while (cellIter.hasNext()) {
                                     HSSFCell myCell = (HSSFCell) cellIter.next();
                                     currentCell = myCell.getColumnIndex();
                                     if (previousCell != currentCell-1)  {
                                     rowData = rowData+",";
                                     }
                                     rowData = rowData+","+myCell; 
                             previousCell = currentCell;
                             }
                             rowData = rowData.replaceFirst(",","");    
                             dataList.add(rowData);
                         }
                     }
                 
                     
                 if(fileName.indexOf(".csv") > -1 || fileName.indexOf(".CSV") > -1){
                 
                 FileReader fileReader = new FileReader(readFile);
                 LineNumberReader lnr = new LineNumberReader(fileReader);
                 String line = "";
                 while ((line = lnr.readLine()) != null){
                 dataList.add(line); 
                 }
                     fileReader.close();
                 }
                 if(fileName.indexOf(".gz") > -1){
                         GZIPInputStream in = new GZIPInputStream(new FileInputStream(readFile));
                         Reader decoder = new InputStreamReader(in);
                         BufferedReader br = new BufferedReader(decoder);
                         String line;
                         while ((line = br.readLine()) != null) {
                             dataList.add(line); 
                         }
                         decoder.close();
                 }
                 
                 if(fileName.indexOf(".gzip") > -1){
                         GZIPInputStream in = new GZIPInputStream(new FileInputStream(readFile));
                         Reader decoder = new InputStreamReader(in);
                         BufferedReader br = new BufferedReader(decoder);
                          
                         String line;
                         while ((line = br.readLine()) != null) {
                             dataList.add(line); 
                         }
                         decoder.close();
                 }
                 
                 if(fileName.indexOf(".zip") > -1){
                 try{
                 ZipFile zf = new ZipFile(readFile);
                 Enumeration entries = zf.entries();
                 while (entries.hasMoreElements()) {
                   ZipEntry ze = (ZipEntry) entries.nextElement();
                    long size = ze.getSize();
                     if (size > 0) {
                       BufferedReader br = new BufferedReader(
                           new InputStreamReader(zf.getInputStream(ze)));
                       String line;
                       while ((line = br.readLine()) != null) {
                           dataList.add(line); 
                       }
                       br.close();
                     }
                 }
                 } catch (IOException e) {
                 e.printStackTrace();
                 }
                 }
                ArrayList isUploadmsg=null;
              if(dataList.size() > 1){
                fileUploadInt.InsertFileData(req, res, dataList, fileSeq);
                 session.setAttribute(uploadTyp+"_FILEDATA", dataList);
                 session.setAttribute("file_seq", fileSeq);
                  HashMap returnData=fileUploadInt.VerifiedHeadData(req, res, dataList, new HashMap());
                  String hedmsg = (String)returnData.get("MSG");
                  if(hedmsg.equals("SUCCESS")){
                    ArrayList hedMappingNfList = (ArrayList)returnData.get("NFMAPPINGLST");
                    if(hedMappingNfList.size()>0){
                      hedVerified=false;
                      msg="Following Header mapping not found ";
                      req.setAttribute("MAPPINGNFLIST", hedMappingNfList);
                     req.setAttribute("MSG", msg);
                    String rmk="Mapping Not Found:-"+hedMappingNfList.toString();
                     fileUploadInt.updateFileLog(req, res, 1, fileSeq, rmk);                                      
                    }
                  }else{
                      msg=hedmsg;
                      hedVerified=false;
                  }
                  if(hedVerified){
                      
                      HashMap returnMap = fileUploadInt.VerifiedFileData(req, res,dteFrmt, dataList);
                      String stt=(String)returnMap.get("STT");
                         if(stt.equals("SUCCESS")){
                             ArrayList newDataList = (ArrayList)returnMap.get("NEWDATALIST");
                             HashMap PRPNOTFD = (HashMap)returnMap.get("PRPNOTFD");
                             HashMap HEADDTL = (HashMap)returnMap.get("HEADDTL");
                             if(PRPNOTFD.size()>0){
                                  req.setAttribute("HEADDTL", HEADDTL); 
                                  req.setAttribute("PRPNOTFN", PRPNOTFD);
                                 session.setAttribute(uploadTyp+"_FILEDATA",newDataList);
                             }else{
                                 
                             isUploadmsg= fileUploadInt.uploadFile(req, res,uploadTyp, fileSeq);
                             }
                             if(isUploadmsg!=null){
                                 msg=(String)isUploadmsg.get(1);
                               msg=msg+" Seq No:-"+fileSeq;
                                 req.setAttribute("msg", msg); 
                            }
                                 
                         }else{
                             req.setAttribute("msg", (String)returnMap.get("MSG"));
                         }
                      
                  }
                      
                 }else{
                     msg="File is Empty" ; 
                 }
                 
                 
                 }else
                     msg="File not found for upload" ;
                 }else
              msg="File not found for upload" ;
            
            }else{
               msg="File not found for upload" ;
                }
              
              
               req.setAttribute("msg", msg);
                return am.findForward("load"); 
        }
      }      
    public ActionForward MappHeader(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"File Load", "load start");
        FileUploadForm uploadForm = (FileUploadForm)form;
        FileUploadInterface fileUploadInt = new FileUploadImpl();
         String uploadTyp = (String)uploadForm.getValue("typ");
        JspUtil jspUtil= new JspUtil();
         ArrayList dataList = (ArrayList)session.getAttribute(uploadTyp+"_FILEDATA");
            String mprp_idnArr = "@";
            String alt_prpArr="@";
            Enumeration reqNme = req.getParameterNames();
            while (reqNme.hasMoreElements()) {
                String paramNm = (String) reqNme.nextElement();
                if(paramNm.indexOf("RD_") > -1) {
                    String val = req.getParameter(paramNm);
                    if(val.equals("MAP")){
                        String[] splitPrp = paramNm.split("_");
                        String prpVal = splitPrp[1];
                        String atrVal = util.nvl(req.getParameter("FILVAL_"+prpVal));
                        String mprpIdn = util.nvl(req.getParameter("FILOURVAL_"+prpVal));
                        mprp_idnArr=mprp_idnArr+","+mprpIdn;
                        alt_prpArr=alt_prpArr+","+atrVal;
                        
                    }
            }
            }
            mprp_idnArr=mprp_idnArr+",@";
            alt_prpArr=alt_prpArr+",@";
            String[] mprpIdnStr = mprp_idnArr.split(",");
            String[] alt_prpArrStr = alt_prpArr.split(",");
            JSONObject jObj = new JSONObject();
           jObj.put("mprp_idnArr", mprpIdnStr);
            jObj.put("alt_prpArr", alt_prpArrStr);
            jObj.put("cnt", info.getDbTyp());
            System.out.print(jObj.toString());
           String serviceUrl="http://apps.faunatechnologies.com/diaflexWebService/REST/fileUpload/allMprpMapping";
            JSONObject jsonOutPut=null;
            boolean isSuccess=true;

            try {
                
                JsonDao jsonDao = new JsonDao();
                jsonDao.setServiceUrl(serviceUrl);
                jsonDao.setJsonObject(jObj);
                String jsonInString = jspUtil.getJsonString(jsonDao);
                System.out.println(jsonInString);
                if (!jsonInString.equals("FAIL")) {
                    jsonOutPut = new JSONObject(jsonInString);
                } else
                    isSuccess = false;

            } catch (JSONException jsone) {
                // TODO: Add catch code
                jsone.printStackTrace();
                isSuccess = false;
            }
            if(isSuccess){
            String status = util.nvl((String)jsonOutPut.get("STATUS"));
            String jsmsg = util.nvl((String)jsonOutPut.get("MESSAGE"));
            ArrayList isUploadmsg=null;
            int fileSeq = (Integer)session.getAttribute("file_seq");
            if(status.equals("SUCCESS")){
               String DTEFMT = (String)uploadForm.getValue("DTEFMT");
                HashMap returnMap = fileUploadInt.VerifiedFileData(req, res,DTEFMT, dataList);
                    String stt=(String)returnMap.get("STT");
                 if(stt.equals("SUCCESS")){
                ArrayList newDataList = (ArrayList)returnMap.get("NEWDATALIST");
                HashMap PRPNOTFD = (HashMap)returnMap.get("PRPNOTFD");
                HashMap HEADDTL = (HashMap)returnMap.get("HEADDTL");
                if(PRPNOTFD.size()>0){
                     req.setAttribute("HEADDTL", HEADDTL); 
                     req.setAttribute("PRPNOTFN", PRPNOTFD);
                    session.setAttribute(uploadTyp+"_FILEDATA",newDataList);
                  }else{
                    
                    isUploadmsg= fileUploadInt.uploadFile(req, res,uploadTyp, fileSeq);
                  }
                if(isUploadmsg!=null){
                    String msg=(String)isUploadmsg.get(1);
                        msg=msg+" Seq No:-"+fileSeq;
                    req.setAttribute("msg", msg); 
                }
                    
                }else
                req.setAttribute("msg", (String)returnMap.get("MSG"));
               
            }else{
                req.setAttribute("msg", jsmsg);
            }
            
            
            }else{
                 req.setAttribute("msg", "Error in process");
             }
            
         return am.findForward("load");   
        }
    }
    public ActionForward PrpHeader(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"File Load", "load start");
            JspUtil jspUtil = new JspUtil();
        FileUploadForm uploadForm = (FileUploadForm)form;
          FileUploadInterface fileUploadInt = new FileUploadImpl();
            String uploadTyp = (String)uploadForm.getValue("typ");
            String prp_idnArr = "@";
            String prp_AtrVal = "@";
            Enumeration reqNme = req.getParameterNames();
            while (reqNme.hasMoreElements()) {
                String paramNm = (String) reqNme.nextElement();
                if(paramNm.indexOf("RD_") > -1) {
                    String val = req.getParameter(paramNm);
                 if(val.equals("MAP")){
                 String[] splitPrp = paramNm.split("_");
                 if(splitPrp.length==3){
                 String herVal = splitPrp[1];
                 String prpVal = splitPrp[2];
                 String atrVal = util.nvl(req.getParameter("PRPVAL_"+herVal+"_"+prpVal)); 
                 String prpIDn = util.nvl(req.getParameter("FILOURVAL_"+herVal+"_"+prpVal)); 
                 prp_idnArr=prp_idnArr+","+prpIDn;
                 prp_AtrVal=prp_AtrVal+","+atrVal;
                 }
             }
            }
            }
            prp_idnArr=prp_idnArr+",@";
            prp_AtrVal=prp_AtrVal+",@";
            String[] prpIdnStr = prp_idnArr.split(",");
            String[] alt_prpArrStr = prp_AtrVal.split(",");
            JSONObject jObj = new JSONObject();
            jObj.put("prp_idnArr", prpIdnStr);
            jObj.put("alt_valArr", alt_prpArrStr);
            jObj.put("cnt", info.getDbTyp());
            System.out.print(jObj.toString());
            String serviceUrl="http://apps.faunatechnologies.com/diaflexWebService/REST/fileUpload/allPrpMapping";
            JSONObject jsonOutPut=null;
            boolean isSuccess=true;

            try {
                
                JsonDao jsonDao = new JsonDao();
                jsonDao.setServiceUrl(serviceUrl);
                jsonDao.setJsonObject(jObj);
                String jsonInString = jspUtil.getJsonString(jsonDao);
                System.out.println(jsonInString);
                if (!jsonInString.equals("FAIL")) {
                    jsonOutPut = new JSONObject(jsonInString);
                } else
                    isSuccess = false;

            } catch (JSONException jsone) {
                // TODO: Add catch code
                jsone.printStackTrace();
                isSuccess = false;
            }
            if(isSuccess){
            String status = util.nvl((String)jsonOutPut.get("STATUS"));
            String jsmsg = util.nvl((String)jsonOutPut.get("MESSAGE"));
          if(status.equals("SUCCESS")){
                int fileSeq = (Integer)session.getAttribute("file_seq");
              ArrayList  isUploadmsg= fileUploadInt.uploadFile(req, res,uploadTyp, fileSeq);
              if(isUploadmsg!=null){
                    String msg=(String)isUploadmsg.get(1);
                        msg=msg+" Seq No:-"+fileSeq;
                    req.setAttribute("msg", msg); 
                }
            }else{
                req.setAttribute("msg", jsmsg);
            }
            
            }else
                req.setAttribute("msg", "Error in  process");
    
            return am.findForward("load");   
        }
    }
    
    public FileUploadingAction() {
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
                util.updAccessLog(req,res,"File Load", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"File Load", "init");
            }
            }
            return rtnPg;
            }
}
