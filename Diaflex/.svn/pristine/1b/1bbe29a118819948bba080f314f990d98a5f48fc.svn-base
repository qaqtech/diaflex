package ft.com.mpur;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.contact.WebAccessFrm;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;

import ft.com.dashboard.UserRightsForm;
import ft.com.fileupload.FileUploadForm;
import ft.com.fileupload.FileUploadImpl;

import ft.com.fileupload.FileUploadInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.io.LineNumberReader;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

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

public class PurchaseAction extends DispatchAction {
    private final String formName   = "mpurForm";

    public PurchaseAction() {
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
            util.updAccessLog(req,res,"Purchase Form", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
            PurchaseForm udf = (PurchaseForm) af; 
        ArrayList errorList = new ArrayList();
        int lmt = Integer.parseInt(util.nvl(uiForms.getREC(), "5"));
        String search = util.nvl((String)udf.getValue("submit"));
        if (udf.getAddnew() != null) {
          

            for (int i = 1; i <= lmt; i++) {
                int    lIdn     = i;
                boolean isValid = false;
                ArrayList params   = new ArrayList();
                String insIntoQ = " insert into mpur( pur_idn ";
                String insValQ  = "values( SEQ_PUR_ID.nextval ";

                for (int j = 0; j < daos.size(); j++) {
                    UIFormsFields dao   = (UIFormsFields) daos.get(j);
                    String        lFld  = dao.getFORM_FLD();
                    String        fldNm = lFld ;
                    String        lVal  = util.nvl((String) udf.getValue(fldNm));
                    String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));

                    String        lDsc = dao.getDSP_TTL();
                    if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                        String errorMsg = lIdn + "-" + lDsc + " Required.";

                        errors.add(errorMsg);
                        params.clear();

                    } else {
                        String lTblFld = dao.getTBL_FLD().toLowerCase();    // util.nvl((String)formFields.get(lFld+"TF"));

                        insIntoQ += ", " + lTblFld;
                        if((dao.getFLD_TYP().equals("DT")) && (lVal.length() == 10))
                            insValQ += ", to_date(?, 'dd-mm-rrrr')";
                        else
                            insValQ  += ", ?";
                        params.add(lVal);
                        if(lReqd.equalsIgnoreCase("Y"))
                        isValid = true;
                    }
                }

                    if (params.size() > 0 && errors.size()==0) {
                        String sql = insIntoQ + ") " + insValQ + ")";
                        int    ct  = db.execUpd(" Ins Menu", sql, params);
                    }
                    
                    if(isValid){
                        errorList.add(errors);
                        errors = new ArrayList();
                    }
            }
        }

        if (udf.getModify() != null) {
           
            String purIdn = util.nvl((String)udf.getValue("pur_idn"));
            ArrayList    params  = new ArrayList();
            params.add(purIdn);
            String    getMenu = "select " + helper.getSrchFields(daos, "pur_idn", false) + " from mpur where pur_idn = ? ";
            
          ArrayList  rsLst = db.execSqlLst("getUIFormFields for update", getMenu, params);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
            ResultSet  rs =(ResultSet)rsLst.get(1);
            while (rs.next()) {
                int    lIdn  = rs.getInt(1);
                String updQ  = " update mpur set pur_idn = pur_idn ";
                String condQ = " where pur_idn = ? ";
                boolean isValid = false;
                params = new ArrayList();
                for (int j = 0; j < daos.size(); j++) {
                        UIFormsFields dao   = (UIFormsFields) daos.get(j);
                        String        lFld  = dao.getFORM_FLD();    // (String)flds.get(j);
                        String        fldNm = lFld;
                        String        lVal  = (String) udf.getValue(fldNm);
                        String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));

                            String lDsc = dao.getDSP_TTL();
                        if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                                    String errorMsg = lDsc + " is  Required";

                                    errors.add(errorMsg);
                                    params.clear();

                                 
                            }     else {
                            String dfltParam = " ? ";
                            String lTblFld = dao.getTBL_FLD().toLowerCase();    // util.nvl((String)formFields.get(lFld+"TF"));
                            if (dao.getFLD_TYP().equals("DT")) {
                                if(lVal.length() == 10) {
                                    //lVal = 
                                    dfltParam = " to_date(?,'dd-mm-rrrr')";
                                }    
                                else
                                    lVal = "";
                            }    
                            
                             updQ += ", " + lTblFld + " = " + dfltParam;
                            params.add(lVal);
                        }
                    }
                

                if (params.size() > 0 && errors.size()==0) {
                    params.add(Integer.toString(lIdn));
                 

                    int ct = db.execUpd(" upd webusrs " + lIdn, updQ + condQ, params);
                }else{
                    errorList.add(errors);
                }
            }
            rs.close();
            stmt.close();
        }
        if(!search.equals("")){
            return search(am, af, req, res);    
        }
        req.setAttribute("errors", errorList);
        util.updAccessLog(req,res,"Purchase Form", "save end");
        return load(am, af, req, res);
        }
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
            util.updAccessLog(req,res,"Purchase Form", "load start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
            PurchaseForm udf = (PurchaseForm) af; 
        udf.reset();
        HashMap dbinfo = info.getDmbsInfoLst();
        String days = (String)dbinfo.get("DEFAULTDAYSPUR");
        ArrayList parmas     = new ArrayList();
        String srchFields = helper.getSrchFields(daos, "pur_idn");
        String srchQ      = " and trunc(mpur.pur_dte)>=trunc(sysdate)-"+days+" ";

        // helper.getSrchList(uiForms, frmTbl, srchFields, srchQ, params, daos, typ)
        ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ, new ArrayList(), daos, "VIEW");

       
        // udf = (CountryFrm)helper.getForm(uiForms, "", srchFields, srchQ, new ArrayList(), daos, "VIEW") ;
        req.setAttribute("purchageList", list);

        // udf.setList(list);
        // info.setNmeSrchList(list);
   
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("PUR_FORM");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("PUR_FORM");
        allPageDtl.put("PUR_FORM",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        int accessidn=util.updAccessLog(req,res,"Purchase Form", "load end");
        req.setAttribute("accessidn", String.valueOf(accessidn));
        String dtePrpQ="select to_char(trunc(sysdate), 'dd-mm-rrrr') todte from dual";
        ResultSet rs = db.execSql("Date calc", dtePrpQ,new ArrayList());
        while (rs.next()) { 
        udf.setValue("pur_dte",(util.nvl(rs.getString("todte"))));
        }
            rs.close();
        return am.findForward("view");
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
            util.updAccessLog(req,res,"Purchase Form", "search start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
            PurchaseForm udf = (PurchaseForm) af;    
        ArrayList parmas     = new ArrayList();
        String nmeID = util.nvl((String)req.getParameter("nmeID")).trim();
        String typ = util.nvl((String)udf.getValue("Typ")).trim();
        String pkt_ty = util.nvl((String)udf.getValue("pkt_ty")).trim();
        String dtefr = util.nvl((String)udf.getValue("dtefr")).trim();
        String dteto = util.nvl((String)udf.getValue("dteto")).trim();
        String searchpur_idn = util.nvl((String)udf.getValue("searchpur_idn")).trim();
        String srchFields = helper.getSrchFields(daos, "pur_idn");
        String srchQ      = " ";
        String dteQ      = " ";
        if(!nmeID.equals("")) 
            srchQ+=" and mpur.vndr_idn='"+nmeID+"' ";    
        if(!typ.equals("")) 
            srchQ+=" and mpur.typ='"+typ+"' ";
       if(!pkt_ty.equals("")) 
            srchQ+=" and mpur.flg='"+pkt_ty+"' ";
       if(!searchpur_idn.equals("")){
           searchpur_idn = util.getVnm(searchpur_idn);
           searchpur_idn=searchpur_idn.replaceAll("'", "");
           String[] stkIdnsArr=searchpur_idn.split(",");
           for (int i = 0; i < stkIdnsArr.length; i++){
           String pidn=stkIdnsArr[i].trim();
           try{  
               Double.parseDouble(pidn);
           }catch(NumberFormatException nfe){  
               searchpur_idn=searchpur_idn.replaceAll(","+pidn, "");
               searchpur_idn=searchpur_idn.replaceAll(pidn, "");
           } 
           }
           if(!searchpur_idn.equals(""))
           srchQ+=" and mpur.pur_idn in("+searchpur_idn+") "; 
       }
        if(!dtefr.equals("") || !dteto.equals("")){
            if(!dtefr.equals("") && !dteto.equals(""))
             srchQ+=" and trunc(mpur.pur_dte) between to_date('"+dtefr+"' , 'dd-mm-yyyy') and to_date('"+dteto+"' , 'dd-mm-yyyy') ";   
            if(!dtefr.equals("") && dteto.equals("")) 
             srchQ+=" and trunc(mpur.pur_dte) between to_date('"+dtefr+"' , 'dd-mm-yyyy') and to_date('"+dtefr+"' , 'dd-mm-yyyy') "; 
            if(dtefr.equals("") && !dteto.equals("")) 
             srchQ+=" and trunc(mpur.pur_dte) between to_date('"+dteto+"' , 'dd-mm-yyyy') and to_date('"+dteto+"' , 'dd-mm-yyyy') ";
        }
        // helper.getSrchList(uiForms, frmTbl, srchFields, srchQ, params, daos, typ)
        ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ, new ArrayList(), daos, "VIEW");

       
        // udf = (CountryFrm)helper.getForm(uiForms, "", srchFields, srchQ, new ArrayList(), daos, "VIEW") ;
        req.setAttribute("purchageList", list);

        // udf.setList(list);
        // info.setNmeSrchList(list);
    
        udf.reset();
         int accessidn=util.updAccessLog(req,res,"Purchase Form", "search end");
        req.setAttribute("accessidn", String.valueOf(accessidn));;
        return am.findForward("view");
        }
    }
    
    public ActionForward edit(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Purchase Form", "edit start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
            PurchaseForm udf = (PurchaseForm) af; 
        ArrayList daos = uiForms.getFields();
     
        String purIdn = util.nvl(req.getParameter("purIdn"));
        
        String srchFields = helper.getSrchFields(daos, "pur_idn");
        String srchQ      = " and pur_idn= ? ";
        ArrayList params     = new ArrayList();
        params.add(purIdn);
       
        // helper.getSrchList(uiForms, frmTbl, srchFields, srchQ, params, daos, typ)
        ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ, params , daos, "VIEW");
        if (list.size() > 0) {
            int lmt    = list.size();
            int frmRec = Integer.parseInt(util.nvl(uiForms.getREC(), "0"));

            if (frmRec == 1) {
                lmt = 1;
            }

            for (int i = 0; i < lmt; i++) {
                GenDAO gen  = (GenDAO) list.get(i);
                String lIdn = gen.getIdn();

                // util.SOP(" i : "+i + " | Idn : "+ lIdn);
                // util.SOP(gen.toString());
                for (int j = 0; j < daos.size(); j++) {
                    UIFormsFields dao     = (UIFormsFields) daos.get(j);
                    String        lFld    = dao.getFORM_FLD();
                    String        lFrmFld = lFld;

                  

                    String fldAlias = util.nvl(dao.getALIAS());
                    String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);

                    udf.setValue(lFrmFld, util.nvl((String) gen.getValue(lFld)));
                    if(aliasFld.length() > 0){
                        udf.setValue(aliasFld,util.nvl((String) gen.getValue(aliasFld)));
                    }
                }
            }
        }
        
        
        udf.setValue("pur_idn",purIdn);
        req.setAttribute("purchageList", list);
        req.setAttribute("method", "edit");
        int accessidn=util.updAccessLog(req,res,"Purchase Form", "edit end");
        req.setAttribute("accessidn", String.valueOf(accessidn));;
        return am.findForward("view");
        }
    }
    public ActionForward loadFile(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Purchase Form", "loadFile start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
            PurchaseForm udf = (PurchaseForm) af; 
        String fileLoadFld = util.nvl((String)udf.getValue("loadFileIdn"));
        FormFile uploadFile = (FormFile)udf.getValue(fileLoadFld);
        HashMap dbmsInfo = info.getDmbsInfoLst();
    
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        ArrayList dataList = new ArrayList();
        HashMap params = new HashMap();
        FileUploadInterface fileUploadInt = new FileUploadImpl();//uploadForm.getFileUploadInt();
        int   file_idn = 0;
        if(uploadFile!=null){
            String fileName = uploadFile.getFileName();
            
            if(!fileName.equals("")){
            
            ResultSet rs = db.execSql("loadSQL", "select load_file_seq.nextval from dual", new ArrayList());
            
            if(rs.next())
            file_idn = rs.getInt(1);
            String client = (String)dbmsInfo.get("CNT");
            rs.close();
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
                            while (rowIter.hasNext()) {
                            HSSFRow myRow = (HSSFRow) rowIter.next();
                            Iterator cellIter = myRow.cellIterator();
                            String rowData = "";
                            
                            while (cellIter.hasNext()) {
                                    HSSFCell myCell = (HSSFCell) cellIter.next();
                                    rowData = rowData+","+myCell; 
                            }
                            rowData = rowData.replaceFirst(",","");    
                            dataList.add(rowData);
                        }
                    }
            
                    
            if(fileName.indexOf(".csv") > -1){
           
            FileReader fileReader = new FileReader(readFile);
            LineNumberReader lnr = new LineNumberReader(fileReader);
            String line = "";
            while ((line = lnr.readLine()) != null){
                dataList.add(line); 
            } 
                    fileReader.close();
            } 
            }
            
            
            params.put("typ", "PUR");
            params.put("fileIdn", String.valueOf(file_idn));
            params.put("dataList", dataList);
            
            int ct = fileUploadInt.addFileData(req,res , params);
            String msg = "";
            if(ct > 0){
               ArrayList ary = new ArrayList();
               ary.add(String.valueOf(file_idn));
               ary.add(fileLoadFld);
               ArrayList out = new ArrayList();
               out.add("V");
               CallableStatement cst = db.execCall("purTrf","pur_pkg.pur_trf(p_idn => ? , p_pur_id => ?, pMsg  => ?)", ary, out);
                msg = cst.getString(ary.size()+1);
              cst.close();
              cst=null;
                fileUploadInt.sendmail(req,res,"PUR",msg);
            }
            
            req.setAttribute("fileMsg", msg);
           
            
        }
            util.updAccessLog(req,res,"Purchase Form", "loadFile end");
        return load(am, af, req, res);
        }
    }
    public ActionForward delete(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Purchase Form", "delete start");
        PurchaseForm udf = (PurchaseForm) af;
        String purIdn = util.nvl(req.getParameter("purIdn"));
        
        ArrayList ary = new ArrayList();
        ary.add(purIdn);
        ArrayList out = new ArrayList();
        out.add("V");
        CallableStatement cst = db.execCall("purTrf","pur_pkg.DEL_UPL_TRNS(pPurIdn => ?, pMsg => ?)", ary, out);
        String msg = cst.getString(ary.size()+1);
        req.setAttribute("fileMsg", msg);
          cst.close();
          cst=null;
            util.updAccessLog(req,res,"Purchase Form DEL", purIdn);
            util.updAccessLog(req,res,"Purchase Form", "delete end");
        return load(am, af, req, res);
        }
    }
    public ActionForward authenticate(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Purchase Form", "authenticate start");
        PurchaseForm udf = (PurchaseForm) af;
        String purIdn = util.nvl(req.getParameter("purIdn"));
        
        ArrayList ary = new ArrayList();
        ary.add(purIdn);
        int ct = db.execUpd("Upd mpur", "update mpur set flg1=decode(nvl(flg1,'N'),'N','Y','N') where pur_idn=?", ary);
        util.updAccessLog(req,res,"Purchase Form", "authenticate end");
        return load(am, af, req, res);
        }
    }
    public ActionForward error(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Purchase Form", "error start");
        PurchaseForm udf = (PurchaseForm) af;
        String purIdn = util.nvl(req.getParameter("purIdn"));
            util.updAccessLog(req,res,"Purchase Form", "error end");
        return am.findForward("error");
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
                util.updAccessLog(req,res,"Purchase Form", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Purchase Form", "init");
            }
            }
            return rtnPg;
            }
}
   

