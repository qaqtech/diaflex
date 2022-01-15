package ft.com.contact;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.GenMail;
import ft.com.InfoMgr;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;

import ft.com.dao.UIFormsFields;

import java.io.IOException;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Hashtable;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class nmeEdit extends DispatchAction {
    ArrayList            daos       = null;
    ArrayList            errors     = null;
    HashMap            formFields = null;
    FormsUtil            helper     = null;
    UIForms              uiForms    = null;
    DBMgr                db;
    InfoMgr              info;
    HttpSession          session;
    NmeEditFrm           udf;
    ArrayList               ukFld;
    DBUtil               util;
    
    public nmeEdit() {
        super();
    }
    
    
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        session = req.getSession(false);
        info = (InfoMgr)session.getAttribute("info");
        util = new DBUtil();
        db = new DBMgr();
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
            util.updAccessLog(req,res,"Contact Master", "load start");
        udf = (NmeEditFrm) af;
        
        String formName = util.nvl(req.getParameter("formName"));
        String idn = util.nvl(req.getParameter("idn"));
        formFields = info.getFormFields(formName);

        if ((formFields == null) || (formFields.size() == 0)) {
            formFields = util.getFormFields(formName);
        }

        uiForms = (UIForms) formFields.get("DAOS");
        daos    = uiForms.getFields();
        errors  = new ArrayList();
        helper  = new FormsUtil();
        helper.init(db, util, info);
        
        String srchQ      = "";
        ArrayList params     = new ArrayList();
        String lNmeIdn    = util.nvl(udf.getNmeIdn());
        if(lNmeIdn.equals(""))
            lNmeIdn = util.nvl(req.getParameter("nmeIdn"));
        
        String  lRelIdn = util.nvl(udf.getIdn());
        if(lRelIdn.equals(""))
             lRelIdn = util.nvl(req.getParameter("Idn"));
        
        String srchFields = getSrchFields(daos);   //For contact form
        if(formName.equals("nmedtl"))    
            srchFields = helper.getSrchFields(daos, "nme_dtl_idn");
        if(formName.equals("customerterms"))    
            srchFields = helper.getSrchFields(daos, "nmerel.nmerel_idn, nmerel.nme_idn, nmerel.dflt_yn dyn");
        if(formName.equals("customercomm"))    
            srchFields = helper.getSrchFields(daos, "nmerel_idn, nme_idn, dflt_yn dyn ");
        if(formName.equals("address")) 
            srchFields = helper.getSrchFields(daos, "addr_idn, nme_idn, dflt_yn dyn");
        
        
        srchQ = " and nme_idn = ? ";
        
        
        if(formName.equals("customerterms"))     
            srchQ = " and nmerel.nme_idn = ?  and nmerel.nmerel_idn = ?  ";
        if(formName.equals("address"))     
            srchQ = "  and nme_idn = ? and addr_idn = ? ";
        if(formName.equals("nmedtl"))     
            srchQ = " and nme_idn = ?  and nme_dtl_idn = ?  ";
        if(formName.equals("customercomm"))     
            srchQ = " and nme_idn = ? and nmerel_idn = ?" ;
        
        
        params.add(lNmeIdn);
        
        if(formName.equals("address") || formName.equals("customerterms") || formName.equals("nmedtl") || formName.equals("customercomm"))    
                params.add(idn);
        
        
        ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ, params, daos, "VIEW");
        if(!lNmeIdn.equals("")){
        if (list.size() > 0) {
            int lmt    = list.size();
            int frmRec = Integer.parseInt(util.nvl(uiForms.getREC(), "0"));

            if (frmRec == 1) {
                lmt = 1;
            }
            
                           
            for (int i = 0; i < lmt; i++) {
                int fldId = 0 ;
                fldId = i+1 ;
                GenDAO gen  = (GenDAO) list.get(i);
                String lIdn = gen.getIdn();
                ArrayList prpVal = new ArrayList();    
                for (int j = 0; j < daos.size(); j++) {
                    UIFormsFields dao     = (UIFormsFields) daos.get(j);
                    String        lFld    = dao.getFORM_FLD();
                    String        fldTtl  = util.nvl(dao.getDSP_TTL(), lFld);
                    String        lFrmFld = lFld+ "_" +  fldId;
                    String fldTyp = util.nvl(dao.getFLD_TYP());
                    String        mprpFld = util.nvl((String) gen.getValue(lFld));
                    if(fldTyp.equals("S")){
                    String lovQ = dao.getLOV_QRY();
                    HashMap lovKV = util.getLOV(lovQ);
                    ArrayList keys = (ArrayList)lovKV.get("K");
                    ArrayList vals = (ArrayList)lovKV.get("V");
                    String ldspVal = (String)vals.get(keys.indexOf(mprpFld));
                        mprpFld = ldspVal;    
                    }
                    String fldAlias = util.nvl(dao.getALIAS());
                    String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);
                    if(!aliasFld.equals("")){
                    mprpFld=util.nvl((String) gen.getValue(aliasFld));   
                    }
                    udf.setValue(lFrmFld, mprpFld);
                    prpVal.add(mprpFld);
                }
                
            
        }
        }}
        
        udf.setNmeIdn(lNmeIdn);
        req.setAttribute("nmeIdn", lNmeIdn);
        req.setAttribute("form", formName);
        session.setAttribute(formName, list);
        
            util.updAccessLog(req,res,"Contact Master", "load end");
        return am.findForward("load");
        }
    }
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        session = req.getSession(false);
        info = (InfoMgr)session.getAttribute("info");
        util = new DBUtil();
        db = new DBMgr();
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
            util.updAccessLog(req,res,"Contact Master", "save start");
        udf = (NmeEditFrm) af;
        String lNmeIdn    = util.nvl(udf.getNmeIdn());
        String formName = util.nvl((String)req.getParameter("formName"));
        ArrayList list = (ArrayList)session.getAttribute(formName);
        ArrayList oldValList = new ArrayList();        
        ArrayList newValList = new ArrayList();
        ArrayList ttlList = new ArrayList();  
        String changereqby="";
        if (list.size() > 0) {
            int lmt    = list.size();
            int frmRec = Integer.parseInt(util.nvl(uiForms.getREC(), "0"));

            if (frmRec == 1) {
                lmt = 1;
            }
            
                           
            for (int i = 0; i < lmt; i++) {
                ArrayList subOldValList = new ArrayList();        
                ArrayList subNewValList = new ArrayList();
                int fldId = 0 ;
                fldId = i+1 ;
                GenDAO gen  = (GenDAO) list.get(i);
                String lIdn = gen.getIdn();
                ArrayList prpVal = new ArrayList();    
                for (int j = 0; j < daos.size(); j++) {
                    UIFormsFields dao     = (UIFormsFields) daos.get(j);
                    String        lFld    = dao.getFORM_FLD();
                    String        fldTtl  = util.nvl(dao.getDSP_TTL(), lFld);
                    String        lFrmFld = lFld+ "_" +  fldId;
                    String fldTyp = util.nvl(dao.getFLD_TYP());
                    String        lFrmFldNew = lFld+ "_" +  fldId+"_New";
                    String        oldValue = util.nvl((String) gen.getValue(lFld));
                    if(fldTyp.equals("S")){
                    String lovQ = dao.getLOV_QRY();
                    HashMap lovKV = util.getLOV(lovQ);
                    ArrayList keys = (ArrayList)lovKV.get("K");
                    ArrayList vals = (ArrayList)lovKV.get("V");
                    String ldspVal = (String)vals.get(keys.indexOf(oldValue));
                    oldValue = ldspVal;    
                    }
                    String fldAlias = util.nvl(dao.getALIAS());
                    String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);
                    if(!aliasFld.equals("")){
                    oldValue=util.nvl((String) gen.getValue(aliasFld));   
                    }
                    ttlList.add(fldTtl);
                    subOldValList.add(oldValue);
                    String newValue = util.nvl((String) udf.getValue(lFrmFldNew));
                    subNewValList.add(newValue);
            }
            oldValList.add(subOldValList);
            newValList.add(subNewValList);    
        }
        }
        
        
        HashMap mailDtl = getAppMailMAP("MAIL_CUSTREQ");
        boolean isUpdate = false;
        HashMap dbmsInfo = info.getDmbsInfoLst();
        GenMail mail = new GenMail();
        info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
        info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
        info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
        info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
        String senderID =(String)dbmsInfo.get("SENDERID");
        String senderNm =(String)dbmsInfo.get("SENDERNM");
        
        StringBuffer sb = new StringBuffer() ;
        
        sb = util.appendTo(sb, "<html><head><title>Update Profile</title>" +
        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />" +
        "<style>\n"+
        "body {\n" +
        " margin-left: 0px;\n" +
        " margin-top: 0px;\n" +
        " margin-right: 0px;\n" +
        " margin-bottom: 0px;\n" +
        " background-color: #FFFFFF;\n" +
        " font-family: Geneva, Arial, Helvetica, sans-serif;\n" +
        " font-size: 9pt;\n" +
        " color: #000000; \n" +
        " } \n" +
        "</style></head><body>"); 
        if(!lNmeIdn.equals("") && !lNmeIdn.equals("0")){
        String byr = util.nvl((String)mailDtl.get("ISBYR"));
        String selex = util.nvl((String)mailDtl.get("ISSAL"));
        ArrayList ary = new ArrayList();
        ary.add(lNmeIdn);
        String sqlQ =
            "select nme,lower(byr.get_eml(nme_idn,'N')) byreml,lower(byr.get_eml(emp_idn,'N')) sxeml from nme_v where nme_idn=? ";

            ArrayList outLst = db.execSqlLst("Email Id", sqlQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        if (rs.next()) {
            changereqby=util.nvl(rs.getString("nme"));
            String byreml = util.nvl(rs.getString("byreml"));
            String sxeml = util.nvl(rs.getString("sxeml"));
            if (byreml.length() > 0 && byr.equals("Y"))
                mail.setTO(byreml);
            if (sxeml.length() > 0 && selex.equals("Y"))
                mail.setCC(sxeml);
        }
            rs.close(); pst.close();
        }
        sb.append("<div>Below are the Profile changes requested by-<b>"+changereqby+"</b></div><br>");
        sb = util.appendTo(sb, "<table border=\"1\" cellspacing=\"2\" cellpadding=\"5\">");
        sb = util.appendTo(sb, "<tr><td>Field</td><td>Old Value</td><td>New Value</td></tr>");
        
        for(int i=0;i<oldValList.size();i++){
            ArrayList o = (ArrayList)oldValList.get(i);
            ArrayList n = (ArrayList)newValList.get(i);
            for(int j=0;j<o.size();j++){
                
            String fld = (String)o.get(0); 
            
            if(!formName.equals("nmedtl"))     
                fld = (String)ttlList.get(j); 
            
            if(!n.get(j).equals("") && !n.get(j).equals("Select")) {
            if(!o.get(j).equals(n.get(j))){
                  isUpdate = true;
                  sb = util.appendTo(sb, "<tr><td>"+fld+"</td><td>"+o.get(j)+"</td><td>"+n.get(j)+"</td></tr>");
            }
            }    
          } 
        }
        sb = util.appendTo(sb, "</table>");
        sb = util.appendTo(sb, "</body></html>");
        mail.setInfo(info);    
        mail.init();
        mail.setSender(senderID, senderNm);
        mail.setSubject("Contact Profile Update "+util.getToDteTime()+" from "+info.getUsr());
        String eml = util.nvl((String)mailDtl.get("MTO"));
        String[] emlLst = eml.split(",");
        if(emlLst!=null){
        for(int i=0 ; i <emlLst.length; i++)
        {
        mail.setBCC(emlLst[i]);
        }
        }
        eml = util.nvl((String)mailDtl.get("MLIST"));
        emlLst = eml.split(",");
        if(emlLst!=null){
        for(int i=0 ; i <emlLst.length; i++)
         {
          mail.setBCC(emlLst[i]);
         }
        }
        //mail.setBCC("mayur.boob@faunatechnologies.com");
        mail.setMsgText(sb.toString());
        
        if(isUpdate) {
           mail.send(sb.toString());
           req.setAttribute("msg","Change Request Sent Successfully");
        }else{
           req.setAttribute("msg","No Change Request");  
        }
            util.updAccessLog(req,res,"Contact Master", "load end");
        return am.findForward("load");
        }
    }
    public HashMap getAppMailMAP(String typ){
        HashMap appMailMap = new HashMap();
        String    memoPrntOptn = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                                 + "b.mdl = 'JFLEX' and b.nme_rule = ? and a.til_dte is null order by a.srt_fr ";
        ArrayList ary = new ArrayList();
        ary.add(typ);

        ArrayList outLst = db.execSqlLst("memoPrint", memoPrntOptn, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                appMailMap.put(rs.getString("dsc"), rs.getString("chr_fr"));
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
       return appMailMap;
    }
    public ActionForward saveOld(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        session = req.getSession(false);
        info = (InfoMgr)session.getAttribute("info");
        util = new DBUtil();
        db = new DBMgr();
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
            util.updAccessLog(req,res,"Contact Master", "saveOld start");
        udf = (NmeEditFrm) af;
        Hashtable newProf = new Hashtable();
        ArrayList profFld = new ArrayList();
        ArrayList errors  = new ArrayList();
        
        String    srch    = util.nvl((String) udf.getSearch());
        String    modify  = util.nvl((String) udf.getModify());
        String    addnew  = util.nvl((String) udf.getValue("btn"));
        String    view    = util.nvl((String) udf.getView());
        String formName = util.nvl((String)req.getParameter("formName"));
        String    lNmeIdn = util.nvl((String)req.getParameter("nmeIdn"));
        ArrayList reqList = (ArrayList)session.getAttribute(formName);
        int lmt = reqList.size();
        String srchFrmTbl = "";
        String updTblQ = "";
        String condTblQ = "";
        if(formName.equals("contact")) {
            srchFrmTbl = "mnme";
            updTblQ = "update mnme set nme_idn = nme_idn";
            condTblQ = "where nme_idn = ?";
        }    
        if(formName.equals("nmedtl")) {
            srchFrmTbl = "nme_dtl";
            updTblQ = "update nme_dtl set nme_dtl_idn = nme_dtl_idn";
            condTblQ = "where nme_dtl_idn = ?";
        }
        
        if(!formName.equals("customercomm")){
        
                String srchFields = getSrchFields(daos);
                
                if(formName.equals("nmedtl"))
                    srchFields = helper.getSrchFields(daos, "nme_dtl_idn", false);
                
                int fldId = 1;
                String getMenu    = "select " + srchFields + " from "+ srchFrmTbl +"  where nme_idn = ? and vld_dte is null";
                ArrayList params     = new ArrayList();

                params.add(lNmeIdn);

            ArrayList outLst = db.execSqlLst(formName + " for update", getMenu, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                    int    lIdn  = rs.getInt(1);
                    String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn));
                    isChecked = "yes";
                    if (isChecked.length() > 0) {

                //    for(int i=0; i < lmt; i++) {
                        params = new ArrayList();    
                        String updQ  = updTblQ;
                        String condQ = condTblQ;
                        
                        
                        
                        for (int j = 0; j < daos.size(); j++) {
                            
                            UIFormsFields dao   = (UIFormsFields) daos.get(j);
                            String        lFld  = dao.getFORM_FLD();    // (String)flds.get(j);
                            String        fldNm = lFld + "_" +  fldId;
                            String        lVal  = (String) udf.getValue(fldNm);
                            String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));
                            String        lDsc = dao.getDSP_TTL();
                                        
                            
                            if(!formName.equals("nmedtl")) {
                                        profFld.add(lDsc);
                                        newProf.put(lDsc,lVal);            
                            }
                            
                            
                            if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                                String errorMsg = lDsc + " is  Required";

                                errors.add(errorMsg);
                                params.clear();

                             
                            } else {
                                String lTblFld = dao.getTBL_FLD().toLowerCase();    // util.nvl((String)formFields.get(lFld+"TF"));

                                if (dao.getFLD_TYP().equals("DT")) {
                                    updQ += ", " + lTblFld + " = to_date(?,'dd-mm-rrrr') ";
                                } else {
                                    updQ += ", " + lTblFld + " = ? ";
                                }

                                params.add(lVal);
                            }
                            
                            
                        }
                            
                        if(formName.equals("nmedtl")) {
                                profFld.add(params.get(0));
                                newProf.put(params.get(0),params.get(1));            
                        }
                            
                            params.add(String.valueOf(lIdn));
                        
                        if (params.size() > 0 && errors.size()==0) { 
                            int ct = db.execUpd(" upd menu " + lIdn, updQ + condQ, params);
                        }    
                        
                 //   }
                    }
                
                    fldId++;    
                }
                rs.close(); pst.close();
        } else {
            
                String updQ = " byr_trms.comm_agent(pNmeIdn => ?"
                              + ", pAadat => ?, pAadatC  => ?, pMB1  => ?, pMB1C  => ?\n"
                              + ", pMB2 => ?, pMB2C => ?, pMB3 => ?, pMB3C => ?, pMB4 => ?, pMB4C => ?)";
                ArrayList params = new ArrayList();

                params.add(lNmeIdn);
                params.add(util.nvl((String)udf.getValue("aadat_idn_CB")).equals("Y")?"":udf.getValue("aadat_idn"));
                params.add(udf.getValue("aadat_comm"));
                params.add(util.nvl((String)udf.getValue("mbrk1_idn_CB")).equals("Y")?"":udf.getValue("mbrk1_idn"));
                params.add(udf.getValue("mbrk1_comm"));
                params.add(util.nvl((String)udf.getValue("mbrk2_idn_CB")).equals("Y")?"":udf.getValue("mbrk2_idn"));
                params.add(udf.getValue("mbrk2_comm"));
                params.add(util.nvl((String)udf.getValue("mbrk3_idn_CB")).equals("Y")?"":udf.getValue("mbrk3_idn"));
                params.add(udf.getValue("mbrk3_comm"));
                params.add(util.nvl((String)udf.getValue("mbrk4_idn_CB")).equals("Y")?"":udf.getValue("mbrk4_idn"));
                params.add(udf.getValue("mbrk4_comm"));
                db.execCall(" Upd Comm Agent", updQ, params);
            
            
            } 
        
        Hashtable existingProf = (Hashtable)session.getAttribute("existingProf");
        boolean isUpdate = false;
        HashMap dbmsInfo = info.getDmbsInfoLst();
        GenMail mail = new GenMail();
        info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
        info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
        info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
        info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
        String senderID =(String)dbmsInfo.get("SENDERID");
        String senderNm =(String)dbmsInfo.get("SENDERNM");
        
        StringBuffer sb = new StringBuffer() ;
        sb = util.appendTo(sb, "<html><head><title>Update Profile</title>" +
        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />" +
        "<style>\n"+
        "body {\n" +
        " margin-left: 0px;\n" +
        " margin-top: 0px;\n" +
        " margin-right: 0px;\n" +
        " margin-bottom: 0px;\n" +
        " background-color: #FFFFFF;\n" +
        " font-family: Geneva, Arial, Helvetica, sans-serif;\n" +
        " font-size: 9pt;\n" +
        " color: #000000; \n" +
        " } \n" +
        "</style></head><body>"); 
        
        sb = util.appendTo(sb, "<table border=\"1\" cellspacing=\"2\" cellpadding=\"5\">");
        sb = util.appendTo(sb, "<tr><td></td><td>Old Value</td><td>New Value</td></tr>");
        
        for(int i=0;i<profFld.size();i++){
        String fld = (String)profFld.get(i);
            if(!existingProf.get(fld).equals(newProf.get(fld))){
                  isUpdate = true;
                  sb = util.appendTo(sb, "<tr><td>"+fld+"</td><td>"+existingProf.get(fld)+"</td><td>"+newProf.get(fld)+"</td></tr>");
            }   
        }
        sb = util.appendTo(sb, "</table>");
        sb = util.appendTo(sb, "</body></html>");
        
        
        mail.setInfo(info);    
        mail.init();
        mail.setSender(senderID, senderNm);
        mail.setSubject("Profile Update");
        //mail.setBCC("rajiv.patil@faunatechnologies.com");
        mail.setMsgText(sb.toString());
        
        if(isUpdate) {
           mail.send(sb.toString());
        }
        
        
        
        if(errors.size()==0) {
        udf.setNmeIdn(lNmeIdn);
        util.SOP(errors.toString());
        req.setAttribute("errors", errors);
        }
        
            util.updAccessLog(req,res,"Contact Master", "saveOld end");
        return load(am, af, req, res);
        }
    }
    
    
    
    
    public String getSrchFields(ArrayList daos) {
        String srchFields = " nme_idn ";

        ukFld = new ArrayList();

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

            
            srchFields += delim + lTblFld;
            if (util.nvl(dao.getALIAS()).length() > 0) {
                srchFields += delim + dao.getALIAS();
            }
        }

        return srchFields;
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
                util.updAccessLog(req,res,"Contact Master", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Contact Master", "init");
            }
            }
            return rtnPg;
         }
    
}
