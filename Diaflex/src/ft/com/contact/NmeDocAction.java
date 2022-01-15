package ft.com.contact;

//~--- non-JDK imports --------------------------------------------------------

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.dao.NmeDoc;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;

import java.io.BufferedOutputStream;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;

import java.net.URLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class NmeDocAction extends DispatchAction {
    private final String formName   = "nmedocs";
    private static String bucketName     = "";
    private static String keyName        = "";
    private static String uploadFileName = "";
    final static int size=1024;
    public NmeDocAction() {
        super();
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
            util.updAccessLog(req,res,"Nme Docs", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeDocFrm udf = (NmeDocFrm) af;
        HashMap dbSysInfo = info.getDmbsInfoLst();
        int       lmt   = Integer.parseInt(util.nvl(uiForms.getREC(), "5"));

        if (udf.getAddnew() != null) {
            for (int i = 1; i <= lmt; i++) {
                int       lIdn = i;

                ArrayList outLst = db.execSqlLst("doc_idn", "select nme_docs_seq.nextval from dual", new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                rs.next();

                String   nme_docs_idn = rs.getString(1);
                rs.close();
                pst.close();
                FormFile fileObj      = null;
                ArrayList   params       = new ArrayList();
                String   insIntoQ     = " insert into nme_docs (nme_docs_idn, nme_idn ";
                String   insValQ      = "values(? , ?";

                params.add(nme_docs_idn);
                params.add(udf.getNmeIdn());

                for (int j = 0; j < daos.size(); j++) {
                    String        lVal  = "";
                    UIFormsFields dao   = (UIFormsFields) daos.get(j);
                    String        lFld  = dao.getFORM_FLD();
                    String        fldNm = lFld + "_" + lIdn;
                    String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));

                    if (lFld.equals("doc_nme")) {
                        fileObj = (FormFile) udf.getValue(fldNm);
                        lVal    = fileObj.getFileName();
                    } else {
                        lVal = (String) udf.getValue(fldNm);
                    }

                    if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0)) {
                        String errorMsg = lFld + ", Required";

                        errors.add(errorMsg);
                        params.clear();

                        break;
                    } else {
                        String lTblFld = dao.getTBL_FLD().toLowerCase();

                        insIntoQ += ", " + lTblFld;
                        if((dao.getFLD_TYP().equals("DT")) && (lVal.length() == 10))
                            insValQ += ", to_date(?, 'dd-mm-rrrr')";
                        else
                            insValQ  += ", ?";
                        params.add(lVal);
                    }
                }

                if (params.size() > 0) {
                    boolean crossverify=true;
                    String s3on = util.nvl((String)dbSysInfo.get("S3ON"),"N");
                    String s3path = util.nvl(util.nvl((String)dbSysInfo.get("DOC_S3DOC_PATH")).trim(),util.nvl((String)dbSysInfo.get("S3DOC_PATH")).trim());
                    String docPath = (String)dbSysInfo.get("DOC_PATH");
                    String webPath = (String)dbSysInfo.get("REP_URL");
                    String docDwnPath = util.nvl(util.nvl((String)dbSysInfo.get("DOC_DOC_DWN")).trim(),util.nvl((String)dbSysInfo.get("DOC_DWN")).trim());
                    String sql       = insIntoQ + ", doc_path , doc_lnk , doc_ext ) " + insValQ + " , ? , ? , ? )";
                    String fileName  = fileObj.getFileName();
                    String modifyNme = nme_docs_idn + "_" + fileName;
                    String filePath  = docPath+""+ modifyNme;

                     //String filePath = "E:\\"+modifyNme;
                    String linkPath =docDwnPath+""+ modifyNme;
                    if(s3on.equals("Y")){
                        filePath=s3path+""+ modifyNme;
                        linkPath=s3path+""+ modifyNme;
                        if (!fileName.equals("")) {
                        String path = getServlet().getServletContext().getRealPath("/") + fileName;
                            File readFile = new File(path);
                            if(!readFile.exists()){
                            FileOutputStream fileOutStream = new FileOutputStream(readFile);
                            fileOutStream.write(fileObj.getFileData());
                            fileOutStream.flush();
                            fileOutStream.close();
                            }
                            bucketName     = util.nvl(util.nvl((String)dbSysInfo.get("DOC_BKTNME")).trim(),util.nvl((String)dbSysInfo.get("BKTNME")).trim());
                            keyName        = util.nvl(util.nvl((String)dbSysInfo.get("DOC_S3KEY")).trim(),util.nvl((String)dbSysInfo.get("S3KEY")).trim());
                            uploadFileName = path;
                            System.out.println(uploadFileName);
                            String s3saveto=util.nvl(util.nvl((String)dbSysInfo.get("DOC_s3SAVETO")).trim(),util.nvl((String)dbSysInfo.get("s3SAVETO")).trim());
                            AWSCredentials myCredentials = new BasicAWSCredentials(
                                                                   keyName, util.nvl(util.nvl((String)dbSysInfo.get("DOC_S3VAL")).trim(),util.nvl((String)dbSysInfo.get("S3VAL")).trim()));
                            AmazonS3 s3client = new AmazonS3Client(myCredentials);   
                            try {
                                System.out.println("Uploading a new object to S3 from a file\n");
                                File file = new File(uploadFileName);
                                s3client.putObject(new PutObjectRequest(
                                                             bucketName, s3saveto+modifyNme, file));

                             } catch (AmazonServiceException ase) {
                                System.out.println("Caught an AmazonServiceException, which " +
                                            "means your request made it " +
                                        "to Amazon S3, but was rejected with an error response" +
                                        " for some reason.");
                                System.out.println("Error Message:    " + ase.getMessage());
                                System.out.println("HTTP Status Code: " + ase.getStatusCode());
                                System.out.println("AWS Error Code:   " + ase.getErrorCode());
                                System.out.println("Error Type:       " + ase.getErrorType());
                                System.out.println("Request ID:       " + ase.getRequestId());
                                crossverify=false;
                            } catch (AmazonClientException ace) {
                                System.out.println("Caught an AmazonClientException, which " +
                                            "means the client encountered " +
                                        "an internal error while trying to " +
                                        "communicate with S3, " +
                                        "such as not being able to access the network.");
                                System.out.println("Error Message: " + ace.getMessage());
                                crossverify=false;
                            }
                        }
                    }
                    params.add(filePath);//c:
                    params.add(linkPath);//hkd
                    params.add(fileObj.getContentType());

                    if (!fileName.equals("") && s3on.equals("N")) {
                        File fileToCreate = new File(filePath);

                        if (!fileToCreate.exists()) {
                            FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);

                            fileOutStream.write(fileObj.getFileData());
                            fileOutStream.flush();
                            fileOutStream.close();
                        }
                    }
                    if(crossverify){
                    int ct = db.execUpd(" Ins Menu", sql, params);
                    }
                }
                rs.close();
            }

            search(am, af, req, res);
        } else {}
            util.updAccessLog(req,res,"Nme Docs", "save end");
        return am.findForward("view");
        }
    }

    public ActionForward valified(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        ArrayList ary = new ArrayList();
        String nmeIdn = req.getParameter("nmeDocId");
        String stt = req.getParameter("stt");
        String updNmeDoc = "update nme_docs set flg='Y' where nme_docs_idn=? ";
        ary.add(nmeIdn);
        if(stt.equals("false")){
            updNmeDoc = "update nme_docs set flg=null where nme_docs_idn=? ";
        }
        int ct = db.execUpd("updNmeDoc", updNmeDoc, ary);
        return null;
        }
    }

    public ActionForward search(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Nme Docs", "search start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeDocFrm udf = (NmeDocFrm) af;

        String lNmeIdn    = util.nvl(udf.getNmeIdn(), "0");
        ArrayList params     = new ArrayList();
        String srchQ      = "",
               srchFields = "";

        srchFields = getSrchFields(daos);
        srchQ      = " and nme_idn = ? and vld_dte is null";
        params.add(lNmeIdn);

        ArrayList list = getSrchList(srchFields, srchQ, params, daos, "VIEW",uiForms,req,udf);

       
        session.setAttribute("docList", list);
        session.setAttribute("nmeId", lNmeIdn);
            util.updAccessLog(req,res,"Nme Docs", "search end");
        return am.findForward("view");
        }
    }

    public ArrayList getSrchList(String srchFields, String srchQ, ArrayList params, ArrayList daos, String typ,UIForms  uiForms,HttpServletRequest req,NmeDocFrm udf) {
        HttpSession session = req.getSession(false);
                InfoMgr info = (InfoMgr)session.getAttribute("info");
                DBUtil util = new DBUtil();
                DBMgr db = new DBMgr();
                Connection conn=info.getCon();
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
        ArrayList list    = new ArrayList();
        ArrayList editLst = new ArrayList();

        try {
            String getDataQ = " select " + srchFields + ", doc_lnk , doc_ext , doc_path from nme_docs where 1=1 "
                              + srchQ;

            ArrayList outLst = db.execSqlLst(" get Search data", getDataQ, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                NmeDoc docDeo = new NmeDoc();
                int    lIdn   = rs.getInt(1);

                docDeo.setNmn_doc_id(String.valueOf(lIdn));

                for (int i = 0; i < daos.size(); i++) {
                    UIFormsFields dao    = (UIFormsFields) daos.get(i);
                    String        lFld   = dao.getFORM_FLD();
                    String        tblFld = dao.getTBL_FLD().toLowerCase();

                    try {
                        udf.setValue(lFld + "_" + lIdn, util.nvl(rs.getString(tblFld)));
                    } catch (Exception fe) {
                        
                    }
                }

                docDeo.setDoc_lnk(util.nvl(rs.getString("doc_lnk")));
                docDeo.setDoc_path(util.nvl(rs.getString("doc_path")));
                docDeo.setDoc_typ(util.nvl(rs.getString("doc_ext")));
                editLst.add(String.valueOf(lIdn));
                list.add(docDeo);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }

        session.setAttribute("editList", editLst);

        return list;
    }

    public ActionForward delete(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Nme Docs", "delete start");
        NmeDocFrm udf = (NmeDocFrm) af;

        String delId = req.getParameter("delId");
        ArrayList ary   = new ArrayList();

        ary.add(delId);

        String upDateQur = "update nme_docs set vld_dte = sysdate where nme_docs_idn= ?";
        int    ct        = db.execUpd("delete", upDateQur, ary);
            util.updAccessLog(req,res,"Nme Docs", "delete end");
        return search(am, af, req, res);
        }
    }

    public String getSrchFields(ArrayList daos) {
        String srchFields = " nme_docs_idn ";
        JspUtil util=new JspUtil();
        ArrayList ukFld = new ArrayList();

        for (int j = 0; j < daos.size(); j++) {
            UIFormsFields dao     = (UIFormsFields) daos.get(j);
            String        lTblFld = dao.getTBL_FLD().toLowerCase();
            String        fldTyp  = dao.getFLD_TYP();

            if (fldTyp.equalsIgnoreCase("DT")) {
                lTblFld = "to_char(" + lTblFld + ", 'dd-mm-rrrr') " + lTblFld;
            }

            if (util.nvl(dao.getFLG()).equals("UK")) {
                ukFld.add(lTblFld);
            }

            String delim = ", ";

            /*
             * if (j==0) {
             *   delim = "";
             * }
             */
            srchFields += delim + lTblFld;
        }

        return srchFields;
    }
    public ActionForward download(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                 util.updAccessLog(req,res,"Nme Docs", "download start");

           String  path               = req.getParameter("path");
           String  contTyp            = util.nvl(req.getParameter("typ"));
                 String  sign          = util.nvl(req.getParameter("sign"));
           File    fileToBeDownloaded = new File(path);
           HashMap dbSysInfo = info.getDmbsInfoLst();
           String filenme=util.nvl(fileToBeDownloaded.getName());
           String s3on = util.nvl((String)dbSysInfo.get("S3ON"),"N");
           String dwusingurl =util.nvl(req.getParameter("dwusingurl"));
           String docPathNew = util.nvl(util.nvl((String)dbSysInfo.get("DOC_S3DOC_PATH")).trim());
           if(contTyp.equals("")){
               if(filenme.indexOf("jpeg") > -1) {
                   contTyp="image/jpeg";
               }
               if(filenme.indexOf("jpg") > -1) {
                   contTyp="image/jpg";
               }
               if(filenme.indexOf("png") > -1) {
                   contTyp="image/png";
               }
               if(filenme.indexOf("mov") > -1) {
                   contTyp="video/quicktime";
               }
               if(filenme.indexOf("wmv") > -1) {
                   contTyp="video/x-ms-wmv";
               }
               if(filenme.indexOf("mp4") > -1) {
                   contTyp="video/mp4";
               }  
               if(filenme.indexOf("txt") > -1) {
                   contTyp="text/plain";
               } 
               if(filenme.indexOf("doc") > -1) {
                   contTyp="application/msword";
               } 
               if(filenme.indexOf("pdf") > -1) {
                   contTyp="application/pdf";
               } 
               if(filenme.indexOf("gif") > -1) {
                   contTyp="image/gif";
               }
               if(filenme.indexOf("html") > -1) {
                   contTyp="text/html";
               }
           }
           if(s3on.equals("Y") || dwusingurl.equals("Y")){
               res.setContentType(contTyp);
               if (filenme.indexOf(" ") > -1)
               filenme = filenme.replaceAll(" ", "%20");
               if (filenme.indexOf("&") > -1)
               filenme = filenme.replaceAll("&", "%26");
               res.setHeader("Content-Disposition", "attachment; filename = "+filenme);
               if(!docPathNew.equals("") && sign.equals("Y")){
                   path=util.getSignUrl(docPathNew,path);
                   System.out.println(path);
               }else{
                   if (path.indexOf(" ") > -1)
                   path = path.replaceAll(" ", "%20");
                   if (path.indexOf("&") > -1)
                   path = path.replaceAll("&", "%26");
               }
               URL url = new URL(path);
               URLConnection connection = url.openConnection();
               InputStream stream = connection.getInputStream();
               BufferedOutputStream outs = new BufferedOutputStream(res.getOutputStream());
               int len;
               byte[] buf = new byte[1024];
               while ((len = stream.read(buf)) > 0) {
               outs.write(buf, 0, len);
               }
               outs.close();
               outs.flush();
           }else{ 
               boolean exist = fileToBeDownloaded.exists();
               if (exist) {

               // OutputStream outStream=response.getOutputStream();
               ServletOutputStream out = res.getOutputStream();

               res.setContentType(contTyp);
               res.setHeader("Content-Disposition", "attachment; filename=" + filenme);
               res.setHeader("Content-Length", String.valueOf(fileToBeDownloaded.length()));

               FileInputStream fis = new FileInputStream(fileToBeDownloaded);
               int             i   = 0;

               while ((i = fis.read()) != -1) {
                   out.write(i);
               }

               fis.close();
               out.flush();
               out.close();
           }
           }
                 util.updAccessLog(req,res,"Nme Docs", "download end");
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
                rtnPg=util.checkUserPageRights("contact/advcontact.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Nme Docs", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Nme Docs", "init");
            }
            }
            return rtnPg;
         }
}


//~ Formatted by Jindent --- http://www.jindent.com
