package ft.com.fileupload;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import ft.com.marketing.SearchQuery;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import java.io.OutputStream;

import java.io.Reader;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

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

public class FileUploadAction extends DispatchAction {
    public ActionForward loadRT(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"File Load", "loadRT start");
            ArrayList chkfileprprpLst=new ArrayList();
            HashMap dtl=new HashMap();
            String sql = "select dfcol, mprp, val from CHK_FILE_PRP where flg = 'NF' order by dfcol, val ";

            ArrayList outLst = db.execSqlLst("fileReport", sql, new ArrayList());   
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()) {
            dtl=new HashMap();
            dtl.put("dfcol",rs.getString("dfcol"));
            dtl.put("mprp",rs.getString("mprp"));
            dtl.put("val",rs.getString("val"));
            chkfileprprpLst.add(dtl);
            }
            rs.close(); pst.close();
            req.setAttribute("chkfileprprpLst", chkfileprprpLst);
        util.updAccessLog(req,res,"File Load", "loadRT end");
        return am.findForward("loadRT");
        }
    }
    public ActionForward loadDel(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"File Load", "loadDel start");
        FileUploadForm uploadForm = (FileUploadForm)form;
        uploadForm.reset();
        
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("DELETE_RECEIPT");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("DELETE_RECEIPT");
        allPageDtl.put("DELETE_RECEIPT",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        uploadForm.setValue("delreceipt", "REC");
        util.updAccessLog(req,res,"File Load", "loadDel end");
        return am.findForward("loadDel");
        }
    }
    public ActionForward delStk(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
    util.updAccessLog(req,res,"File Load", "delstk start");
    FileUploadForm uploadForm = (FileUploadForm)form;
    HashMap dbinfo = info.getDmbsInfoLst();
    int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
    String delreceipt = util.nvl((String)uploadForm.getValue("delreceipt"));
    String recpNo = util.nvl((String)uploadForm.getValue("recNum"));

    String msg ="";
    ArrayList ary = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList msgLst = new ArrayList();
    out.add("V");
    if(delreceipt.equals("VNM")){
    int cout = db.execUpd("delete GT_Pkt "," delete from gt_pkt ", new ArrayList());
    String vnm = util.getVnm(recpNo);
    ary.add("Y");
        if(!vnm.equals("")){
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
         String insScanPkt = "insert into gt_pkt(mstk_idn) select idn from mstk where vnm in ("+vnmSub+")";
         cout= db.execDirUpd(" ins scan", insScanPkt,new ArrayList());
         System.out.println(insScanPkt);  
        }
     }
    CallableStatement ct = db.execCall("del_stk_rcpt BY PktID", "DP_DEL_STK_RCPT(pPkt => ? , pMsg => ?)", ary , out);
    msg = ct.getString(ary.size()+1);
    ct.close();
    msgLst.add(msg);
    }else{
    recpNo = util.getVnmCsv(recpNo);
    String[] recpLst = recpNo.split(",");
    if(recpLst!=null){
    for(int i=0 ; i <recpLst.length; i++){
    ary = new ArrayList();
    ary.add(recpLst[i]);
    String proQ="DP_DEL_STK_RCPT(pRecpt => ? , pMsg => ?)";
    if(delreceipt.equals("MEMO"))
    proQ="DP_DEL_STK_RCPT(pMemo => ? , pMsg => ?)";
    CallableStatement ct = db.execCall("del_stk_rcpt", proQ, ary , out);
    msg = ct.getString(ary.size()+1);
    ct.close();
    msgLst.add(msg);
    }
    }
    }
    req.setAttribute("msg", msgLst);
    util.updAccessLog(req,res,"File Load", "delstk end");
    return am.findForward("loadDel");
    }
    }

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
        FileUploadInterface fileUploadInt = new FileUploadImpl();
        ArrayList fileList = fileUploadInt.fileUploadTyp(req,res,uploadForm);
        uploadForm.setUploadList(fileList);
            util.updAccessLog(req,res,"File Load", "load end");
        return am.findForward("load");
        }
    }
    public ActionForward uploadError(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"File Load", "uploadError start");
        FileUploadForm uploadForm = (FileUploadForm)form;
        FileUploadInterface fileUploadInt = new FileUploadImpl();
        String seqNo = (String)uploadForm.getValue("seqNum");
        String typ = (String)uploadForm.getValue("typ");
        String flg = util.nvl(req.getParameter("flg"));
        HashMap params = new HashMap();
        params.put("seqNo", seqNo);
        params.put("typ", typ);
        params.put("flg", flg);
        ArrayList pktDtlList = fileUploadInt.FileUploadErrorDtl(req, res, params);
        req.setAttribute("pktDtlList", pktDtlList);
            util.updAccessLog(req,res,"File Load", "uploadError end");
        return am.findForward("ErrorDtl");
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
            util.updAccessLog(req,res,"File Load", "upload start");
        HashMap params = new HashMap();
        ArrayList dataList = new ArrayList();
        HashMap dbmsInfo = info.getDmbsInfoLst();
        FileUploadForm uploadForm = (FileUploadForm)form;
        FileUploadInterface fileUploadInt = new FileUploadImpl();
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        String repPath = (String)dbinfo.get("REP_PATH");
        String uploadTyp = (String)uploadForm.getValue("typ");

        String disdte = util.nvl((String)uploadForm.getValue("disdte"));
        String rctdte = util.nvl((String)uploadForm.getValue("dte"));
        String rctLoc = util.nvl((String)uploadForm.getValue("rctLoc"));
        params.put("typ", uploadTyp);
        params.put("disdte", disdte);
        params.put("rctdte", rctdte);
        params.put("rctLoc", rctLoc);
        FormFile uploadFile = uploadForm.getFileUpload();
        int   file_idn = 0;
        if(uploadFile!=null){
            String fileName = uploadFile.getFileName();
            
            if(!fileName.equals("")){
            

                ArrayList outLst = db.execSqlLst("loadSQL", "select load_file_seq.nextval from dual", new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
            if(rs.next())
            file_idn = rs.getInt(1);
                rs.close(); pst.close();
            String client = (String)dbmsInfo.get("CNT");
            fileName = client+"_"+file_idn+"_"+fileName;
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
            }
            
            
            
            params.put("fileIdn", String.valueOf(file_idn));
            params.put("dataList", dataList);
            
            int ct = fileUploadInt.addFileData(req,res , params);
            String msg = "";
            if(ct > 0){
                msg = util.nvl(fileUploadInt.fileTRFTOCSV(req,res, params));
                String pageNme= fileUploadInt.fileReportName(req,res, uploadTyp);
                String theURL = repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\"+pageNme+"";
                req.setAttribute("url", theURL);
                fileUploadInt.sendmail(req,res,uploadTyp,msg);
            }
            
            req.setAttribute("fileMsg", msg);
            req.setAttribute("lineNo", dataList.size());
            
        }
        ArrayList fileList = fileUploadInt.fileUploadTyp(req,res,uploadForm);
        uploadForm.setUploadList(fileList);
        int accessidn=util.updAccessLog(req,res,"File Load", "upload end");
        req.setAttribute("accessidn", String.valueOf(accessidn));;
        return am.findForward("load");
        }
    }
    
    public ActionForward save(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"File Load", "save start");
            FileUploadForm uploadForm = (FileUploadForm)form;
            FileUploadInterface fileUploadInt = new FileUploadImpl();
        ArrayList pktDtlList = new ArrayList();
        String sttRt = util.nvl((String)uploadForm.getValue("sttRt"));
        String errRt = util.nvl((String)uploadForm.getValue("errRt"));
       
        String seqNo = (String)uploadForm.getValue("seqNum");
        if(!sttRt.equals(""))
        pktDtlList = fileUploadInt.FileUploadStt(req,res,seqNo);
        else
        pktDtlList = fileUploadInt.FileUploadErr(req,res,seqNo);
        req.setAttribute("pktDtlList", pktDtlList);
        req.setAttribute("STTRT", sttRt);
        req.setAttribute("ERRRT", errRt);
        req.setAttribute("View", "Y");
        uploadForm.reset();
        uploadForm.setValue("seqNum", seqNo);
            util.updAccessLog(req,res,"File Load", "save end");
        return am.findForward("loadSTT");
        }
    }
    
    public ActionForward loadSTT(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"File Load", "loadSTT start");
            util.updAccessLog(req,res,"File Load", "loadSTT end");
        return am.findForward("loadSTT");
        }
    }
    public ActionForward UploadStt(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"File Load", "uploadstt start");
        FileUploadForm uploadForm = (FileUploadForm)form;
        uploadForm.reset();
        uploadForm.setValue("typ", "MFG");
            util.updAccessLog(req,res,"File Load", "uploadstt end");
        return am.findForward("uploadStt");
        }
    }
    public ActionForward getUploadStt(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"File Load", "getUploadStt start");
        FileUploadForm uploadForm = (FileUploadForm)form;
        FileUploadInterface fileUploadInt = new FileUploadImpl();
        String seqNo = (String)uploadForm.getValue("seqNum");
        String typ = (String)uploadForm.getValue("typ");
        HashMap params = new HashMap();
        params.put("seqNo", seqNo);
        params.put("typ", typ);
        ArrayList pktDtlList = fileUploadInt.FileUploadSttTm(req, res, params);
        req.setAttribute("pktDtlList", pktDtlList);
        req.setAttribute("view", "Y");
        req.setAttribute("typ",typ);
        session.setAttribute("uploadParam", params);
            util.updAccessLog(req,res,"File Load", "getUploadStt end");
        return am.findForward("uploadStt");
        }
    }
    public ActionForward loadMFT(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"File Load", "loadmft start");
        FileUploadForm uploadForm = (FileUploadForm)form;
        GenericInterface genericInt = new GenericImpl();
        uploadForm.reset();
        ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MftGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MftGNCSrch"); 
        info.setGncPrpLst(assortSrchList);
        
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("MFG_EXL");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("MFG_EXL");
        allPageDtl.put("MFG_EXL",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"File Load", "loadmft end");
        return am.findForward("loadRP");
        }
    }
    public ActionForward loadINWRD(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"File Load", "loadINWRD start");
            FileUploadForm uploadForm = (FileUploadForm)form;
            FileUploadInterface fileUploadInt = new FileUploadImpl();
            GenericInterface genericInt = new GenericImpl();
        uploadForm.reset();
        ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MftGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MftGNCSrch"); 
        info.setGncPrpLst(assortSrchList);
        
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("MFG_EXL");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("MFG_EXL");
        allPageDtl.put("MFG_EXL",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"File Load", "loadINWRD end");
        return am.findForward("inwardRP");
        }
    }
    public ActionForward rcpt(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"File Load", "rcpt start");
            FileUploadForm uploadForm = (FileUploadForm)form;
        Enumeration reqNme = req.getParameterNames();
        ArrayList rcptLst = new ArrayList();
        while(reqNme.hasMoreElements()) {
            String paramNm = (String)reqNme.nextElement();
            if(paramNm.indexOf("cb_rcpt_") > -1) {
                String val = util.nvl(req.getParameter(paramNm));
                if(!val.equals("")){
                    val = val.trim();
                    rcptLst.add(val);
                }
            }
            }
        if(rcptLst!=null && rcptLst.size()>0){
            String rcptPcs = rcptLst.toString();
            rcptPcs = rcptPcs.replace('[','(');
            rcptPcs = rcptPcs.replace(']',')');
            rcptPcs = rcptPcs.replace("'", "");
            String updateMstk = " update mstk a set a.stt='WT_CHK' " + 
                                "  where a.idn in (select b.mstk_idn from stk_dtl b where b.mprp='RECPT_NO' " +
                                 " and b.num in "+rcptPcs+" and b.grp=1) and a.stt='MF_FL'";
     
            int ct = db.execUpd("updateMstk", updateMstk, new ArrayList());
            req.setAttribute("msg", "No. Of stone update "+ct);
        }
        uploadForm.reset();
            util.updAccessLog(req,res,"File Load", "rcpt end");
        return am.findForward("loadRP");
        }
    }
    public ActionForward viewRC(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"File Load", "viewrc start");
            FileUploadForm uploadForm = (FileUploadForm)form;
            FileUploadInterface fileUploadInt = new FileUploadImpl();
        String  mdl="MFT_VIEW";
        
        String rtTyp = util.nvl((String)uploadForm.getValue("dtlRpt"));
        if(rtTyp.equals(""))
            mdl = "SUR_RT";
        
        String inwardRp = util.nvl((String)uploadForm.getValue("inwardRpt"));
            String inwardRppkt = util.nvl((String)uploadForm.getValue("inwardRptPkt"));
        
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MftGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MftGNCSrch"); 
        info.setGncPrpLst(genricSrchLst);
//        ArrayList genricSrchLst = info.getGncPrpLst();
        HashMap paramsMap = new HashMap();
            HashMap prpdb = info.getPrp();
            HashMap mprp = info.getMprp();
//        for(int i=0;i<genricSrchLst.size();i++){
//        ArrayList prplist =(ArrayList)genricSrchLst.get(i);
//        String lprp = (String)prplist.get(0);
//        String fldVal1 = util.nvl((String)uploadForm.getValue(lprp+"_1"));
//        String fldVal2 = util.nvl((String)uploadForm.getValue(lprp+"_2"));
//        if(fldVal2.equals(""))
//            fldVal2 = fldVal1;
//         if(!fldVal1.equals("") && !fldVal2.equals("")){
//            paramsMap.put(lprp+"_1",fldVal1);
//            paramsMap.put(lprp+"_2",fldVal2);
//         }
//              
//        }
                    for(int i=0;i<genricSrchLst.size();i++){
            ArrayList prplist =(ArrayList)genricSrchLst.get(i);
            String lprp = (String)prplist.get(0);
            String flg= (String)prplist.get(1);
            String typ = util.nvl((String)mprp.get(lprp+"T"));
            String prpSrt = lprp ;  
            if(flg.equals("M")) {
            
                ArrayList lprpS = (ArrayList)prpdb.get(prpSrt + "S");
                ArrayList lprpV = (ArrayList)prpdb.get(prpSrt + "V");
                for(int j=0; j < lprpS.size(); j++) {
                String lSrt = (String)lprpS.get(j);
                String lVal = (String)lprpV.get(j);    
                String reqVal1 = util.nvl((String)uploadForm.getValue(lprp + "_" + lVal),"");
                if(!reqVal1.equals("")){
                paramsMap.put(lprp + "_" + lVal, reqVal1);
                }
                   
                }
            }else{
            String fldVal1 = util.nvl((String)uploadForm.getValue(lprp+"_1"));
            String fldVal2 = util.nvl((String)uploadForm.getValue(lprp+"_2"));
            if(typ.equals("T")){
                fldVal1 = util.nvl((String)uploadForm.getValue(lprp+"_1"));
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
        paramsMap.put("stt", "RECPT");
        paramsMap.put("mdl", mdl);
        util.genericSrch(paramsMap);
        
        if(!inwardRp.equals("") || !inwardRppkt.equals("")){
            ArrayList vwPrpLst = util.getMemoXl("SUR_RT");
            ArrayList itemHdr = new ArrayList();   
            itemHdr.add("PACKETCODE");
            itemHdr.add("CTS");
            
            
            for (int i = 0; i < vwPrpLst.size(); i++) {
                
                String prp = util.nvl((String)vwPrpLst.get(i));
                
                if(prp.equals("RTE")){
                        itemHdr.add(prp); 
                        itemHdr.add("AMOUNT");            
                }else {
                        itemHdr.add(prp);     
                }
                
            }
            
            int inxLot = vwPrpLst.indexOf("LOTNO")+1;
            String lotPrp = "prp_00"+inxLot;
            if(inxLot > 9)
                 lotPrp = "prp_0"+inxLot;
         
         
         ArrayList pktList = new ArrayList();
         HashMap pktDtlList = new HashMap();   
            
         String srchQ = "select sk1, cts crtwt, decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis)) r_dis , stk_idn, rap_rte ,vnm, "+
                       "to_char(trunc(cts,2),'99999990.00') cts, quot rte, trunc(cts,2)*rap_Rte rap_vlu , rmk ,exh_rte,cmnt cmnt ,to_char(trunc(cts,2) * quot, '99999990.00') amt, " +
             "sum(qty) over(partition by "+lotPrp+") ttlqty, to_char((sum(trunc(cts,2)) over(partition by "+lotPrp+")),99999999.99) grp_cts , to_char((sum(trunc(cts,2)*quot) over(partition by "+lotPrp+")),99999999.99) grp_vlu  ";
                       
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
            
            String rsltQ = srchQ += " from gt_srch_rslt where flg='Z' ";
            
                
            String kapanNo="";
            String ttlCts = "";
            String ttlAmt = "";
            String ttlqty = "";

            ArrayList outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
                while(rs.next()) {     
                pktDtlList = new HashMap();
                    
                String lkapanId = util.nvl(rs.getString(lotPrp));    
                String lttlCts =  util.nvl(rs.getString("grp_cts"));
                String lttlAmt =  util.nvl(rs.getString("grp_vlu"));
                String lttlqty =  util.nvl(rs.getString("ttlqty"));
                    
                    if(inwardRppkt.equals("")) {
                if(kapanNo.equals("")){
                        kapanNo= lkapanId;
                        ttlCts = lttlCts;
                        ttlAmt= lttlAmt;
                        ttlqty= lttlqty;
                        
                    if(lkapanId.equals(kapanNo)){
                    pktDtlList.put("ISKAPAN", "Y");
                    pktDtlList.put("KAPAN", kapanNo);
                    }
                        
                    pktList.add(pktDtlList);
                    pktDtlList = new HashMap();
                }
                
                if(!lkapanId.equals(kapanNo)){
                    pktDtlList.put("ISTOTAL", "Y");
                    pktDtlList.put("ttlCts", ttlCts);
                    pktDtlList.put("ttlAmt", ttlAmt);
                    pktDtlList.put("ttlqty", ttlqty);
                    pktList.add(pktDtlList);
                    pktDtlList = new HashMap();
                    pktDtlList.put("ISKAPAN", "Y");
                    pktDtlList.put("KAPAN", lkapanId);
                    pktList.add(pktDtlList);
                    pktDtlList = new HashMap();
                    kapanNo = lkapanId ;
                    ttlCts = lttlCts;
                    ttlAmt = lttlAmt;
                    ttlqty= lttlqty;
                    
                }}
                
                pktDtlList.put("PACKETCODE", util.nvl(rs.getString("vnm")));
                pktDtlList.put("CTS", util.nvl(rs.getString("cts")));    
                pktDtlList.put("AMOUNT", util.nvl(rs.getString("amt")));
                    
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      if(prp.equals("RAP_RTE"))
                         fld="rap_rte";
                      if(prp.equals("RAP_DIS"))
                         fld="r_dis";
                      if(prp.equals("RTE"))
                         fld="rte";
                    
                      String val = util.nvl(rs.getString(fld)) ;
                      
                        
                        pktDtlList.put(prp, val);
                }    
                    
                pktList.add(pktDtlList);
                    
            }
            rs.close(); pst.close();
                if(!kapanNo.equals("")){
                    pktDtlList = new HashMap();
                    pktDtlList.put("ISTOTAL", "Y");
                    pktDtlList.put("ttlCts", ttlCts);
                    pktDtlList.put("ttlAmt", ttlAmt);
                    pktDtlList.put("ttlqty", ttlqty);
                    pktList.add(pktDtlList);
                }
                
                
            session.setAttribute("pktList", pktList);
            session.setAttribute("itemHdr", itemHdr);
            req.setAttribute("view", "Y");
            
         return am.findForward("inwardRP");  
         
         
         
        }else{
        String summary = util.nvl((String)uploadForm.getValue("summary"));
        if(summary.equals("")){
        ArrayList pktDtlList = fileUploadInt.SearchResult(req ,res, mdl);
        req.setAttribute("view", "Y");
        HashMap totalMap = fileUploadInt.GetTotal(req,res, "Z");
        req.setAttribute("totalMap", totalMap);
        uploadForm.setPktDtlList(pktDtlList);
        }else{
        ArrayList pktDtl = fileUploadInt.GetTotalByRCNo(req, res);
        session.setAttribute("pktDtl", pktDtl);
        req.setAttribute("SUMMARY", "Y");
        }
        uploadForm.setValue("dtlRpt", "");
        uploadForm.setValue("suratRt", "");
        uploadForm.setValue("summary", "");
        uploadForm.setValue("inwardRpt", "");
        req.setAttribute("mdl", mdl);
            util.updAccessLog(req,res,"File Load", "viewrc end");
        return am.findForward("loadRP");
        }
        }
    }
    
    public ActionForward loadTemp(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"File Load", "loadtemp start");
            FileUploadForm uploadForm = (FileUploadForm)form;
        uploadForm.reset();
        
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("FILE_TEMPLATE");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("FILE_TEMPLATE");
        allPageDtl.put("FILE_TEMPLATE",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"File Load", "loadtemp end");
        return am.findForward("loadTemp");
        }
    }
    
    public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"File Load", "createXL start");
        HashMap dbinfo = info.getDmbsInfoLst();
        String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
            int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            int pktListsz=pktList.size();
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
            String contentTypezip = "application/zip";
            String fileNmzip = "ResultExcel"+util.getToDteTime();
            OutputStream outstm = res.getOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(outstm);
            ZipEntry entry = new ZipEntry(fileNmzip+".xls");
            zipOut.putNextEntry(entry);
            res.setHeader("Content-Disposition","attachment; filename="+fileNmzip+".zip");
            res.setContentType(contentTypezip);
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
               hwb.write(bos);
               bos.writeTo(zipOut);      
              zipOut.closeEntry();
               zipOut.flush();
               zipOut.close();
               outstm.flush();
               outstm.close(); 
            }else{
                String CONTENT_TYPE = "getServletContext()/vnd-excel";
                OutputStream out = res.getOutputStream();
                String fileNm = "ResultExcel"+util.getToDteTime()+".xls";
                res.setContentType(CONTENT_TYPE);
                res.setHeader("Content-Disposition", "attachment;filename="+fileNm);                
                hwb.write(out);
                out.flush();
                out.close();
            }
            util.updAccessLog(req,res,"File Load", "createXL end");
        return null;
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
                util.updAccessLog(req,res,"File Load", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"File Load", "init");
            }
            }
            return rtnPg;
            }
    public FileUploadAction() {
        super();
//        this.fileUploadInt  = fileUploadInt;
}


   
   
}
