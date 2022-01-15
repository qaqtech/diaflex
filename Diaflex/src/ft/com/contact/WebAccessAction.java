package ft.com.contact;
import java.sql.Connection;
//~--- non-JDK imports --------------------------------------------------------

import ft.com.*;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.*;

import java.io.IOException;

import java.sql.PreparedStatement;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WebAccessAction extends DispatchAction {
    private final String formName   = "webaccess";


    public WebAccessAction() {
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
            util.updAccessLog(req,res,"Web Access", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        WebAccessFrm udf = (WebAccessFrm) af;
        MailAction mailFt = new MailAction();
        String    lNmeIdn  = util.nvl(udf.getNmeIdn(), "0");
        String    Idn = util.nvl(udf.getIdn(), "0");
        String    srch     = util.nvl((String) udf.getSearch());
        String    modify   = util.nvl((String) udf.getModify());
        String    addnew   = util.nvl((String) udf.getAddnew());
        String    view     = util.nvl((String) udf.getView());
        String    reset   = util.nvl(udf.getReset());

        if (srch.length() > 0) {
            return search(am, af, req, res);
        } else if(reset.length() > 0) {
            return reset(am, af, req, res);
        } else {
            if (addnew.length() > 0) {   
                String usrIdnSeq ="0";
                ArrayList params   = new ArrayList();

                ArrayList outLst = db.execSqlLst("user Idn","select WEB_USR_ID_SEQ.nextval usrSeq from dual" , new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                if(rs.next())
                usrIdnSeq = util.nvl(rs.getString("usrSeq"));
                rs.close(); pst.close();
                pst.close();
                params.add(usrIdnSeq);
                String insIntoQ = " insert into web_usrs (usr_id ";
                String insValQ  = "values(? ";
                for (int j = 0; j < daos.size(); j++) {
                    UIFormsFields dao   = (UIFormsFields) daos.get(j);
                    String        lFld  = dao.getFORM_FLD();
                    String        fldNm = lFld;
                    String        lVal  = (String) udf.getValue(fldNm);
                    String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));
                    String lDsc = dao.getDSP_TTL();
                    if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                                String errorMsg = lDsc + " is  Required";

                                errors.add(errorMsg);
                                params.clear();

                    }  else {
                        
                          String lTblFld = dao.getTBL_FLD().toLowerCase();    // util.nvl((String)formFields.get(lFld+"TF"));

                         insIntoQ += ", " + lTblFld;
                        if((dao.getFLD_TYP().equals("DT")) && (lVal.length() == 10))
                            insValQ += ", to_date(?, 'dd-mm-rrrr')";
                        else
                            insValQ  += ", ?";
                        params.add(lVal);
                    }
                }

                if (params.size() > 0 && errors.size()==0) { 
                    String sql = insIntoQ + ") " + insValQ + ")";
                    int    ct  = db.execUpd(" Ins Menu", sql, params);
                    if(ct>0){                     
                        mailFt.sendmail(req ,res ,usrIdnSeq ,"WEB_REG" ,"");                      
                    }
                }
            } else {
                

                String srchFields = getSrchFields(daos);
                String getMenu    = "select usr_id "+
                                    " from web_usrs  where usr_id = ? ";
                ArrayList params = new ArrayList();

                params.add(Idn);


                ArrayList outLst = db.execSqlLst(formName + " for update", getMenu, params);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                    int    lIdn  = rs.getInt(1);
                    String updQ  = " update web_usrs set usr_id = usr_id ";
                    String condQ = " where usr_id = ? ";

                    params = new ArrayList();

                   
                        // params.add(Integer.toString(menuIdn));
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
                       
                        int ct = db.execDirUpd(" upd webusrs " + lIdn, updQ + condQ, params);
                    }
                }
                rs.close(); pst.close();
            }
        }

    
        req.setAttribute("errors", errors);
        if(errors.size()>0){
            String  srchFields = getSrchFields(daos);
            String  srchQ = " and nme_idn = ? ";
           
            ArrayList params = new ArrayList();
            params.add(lNmeIdn);
           
            ArrayList list = getSrchList(srchFields, srchQ, params, daos, "SRCH",uiForms,req,udf);

            req.setAttribute("webaccesslst", list);
            return am.findForward("view");
        }
            util.updAccessLog(req,res,"Web Access", "save end");
        return search(am, af, req, res);
        }
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
            util.updAccessLog(req,res,"Web Access", "delete start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        WebAccessFrm udf = (WebAccessFrm) af;
        MailAction mailFt = new MailAction();
        String    delNmeIdn = req.getParameter("nmeIdn");
        String    delIdn    = req.getParameter("idn");
        String    delQ      = "update usr_role set stt = 'IA' where usr_idn=? ";
        ArrayList    params    = new ArrayList();
        params.add(delIdn);

        int cnt = db.execUpd(" Del " + delIdn, delQ, params);
        
         delQ      = "update web_usrs set to_dt = sysdate where usr_id=?";
        params    = new ArrayList();
        params.add(delIdn);
        cnt = db.execUpd(" Del " + delIdn, delQ, params);
        util.updAccessLog(req,res,"Web Access Delete", delIdn);
        if(cnt>0){
            mailFt.sendmail(req ,res ,delIdn ,"WEB_DEL" ,"");

        }
            util.updAccessLog(req,res,"Web Access", "delete end");
        return search(am, af, req, res);
        }
    }
    
    public ActionForward sendusrPwd(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Web Access", "sendusrPwd start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        WebAccessFrm udf = (WebAccessFrm) af;
        MailAction mailFt = new MailAction();
        String    delNmeIdn = req.getParameter("nmeIdn");
        String    delIdn    = req.getParameter("idn");
        mailFt.sendmail(req ,res ,delIdn ,"WEB_ACCESS" ,"");
        util.updAccessLog(req,res,"Web Access", "sendusrPwd end");
        return search(am, af, req, res);
        }
    }

    public ActionForward active(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Web Access", "active start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        WebAccessFrm udf = (WebAccessFrm) af;
        MailAction mailFt = new MailAction();
        String    delNmeIdn = req.getParameter("nmeIdn");
        String    delIdn    = req.getParameter("idn");
        String    rlnIdn    = req.getParameter("rlnIdn");
        String    delQ      = "update usr_role set stt = 'A' where usr_idn=? ";
        ArrayList    params    = new ArrayList();
        params.add(delIdn);

        int cnt = db.execUpd(" Del " + delIdn, delQ, params);
        
         delQ      = "update web_usrs set to_dt = null , act_dt = sysdate where usr_id=?";
        params    = new ArrayList();
        params.add(delIdn);
        cnt = db.execUpd(" Del " + delIdn, delQ, params);
        util.updAccessLog(req,res,"Web Access active", delIdn);
            
        delQ      = "update nmerel set vld_dte = null where nmerel_idn=?";
        params    = new ArrayList();
        params.add(rlnIdn);
        cnt = db.execUpd(" Del " + delIdn, delQ, params);
            if(cnt>0){
             mailFt.sendmail(req ,res ,delIdn ,"WEB_REG",  "");
            }
            util.updAccessLog(req,res,"Web Access", "active end");
        return search(am, af, req, res);
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
            util.updAccessLog(req,res,"Web Access", "search start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        WebAccessFrm udf = (WebAccessFrm) af;
        HashMap tbls       = new HashMap();
        String    nmeIdn     = udf.getNmeIdn();
        ArrayList    params     = new ArrayList();
        String    srchQ      = "",
                  srchFields = "";

        srchQ = " and nme_idn = ? ";
        params.add(nmeIdn);

        String tblNmes = "";

        for (int i = 0; i < tbls.size(); i++) {
            String delim = "";

            if (i > 0) {
                delim = ", ";
            }

            tblNmes += delim + (String) tbls.get(i);
        }

        srchFields = getSrchFields(daos);

        ArrayList list = getSrchList(srchFields, srchQ, params, daos, "SRCH",uiForms,req,udf);

        // udf.setList(list);
        req.setAttribute("webaccesslst", list);
        HashMap vldInvalid=vldInvalid(req,res,nmeIdn); 
        req.setAttribute("vldInvalid", vldInvalid);
            util.updAccessLog(req,res,"Web Access", "search end");
        if (util.nvl(udf.getIdn(), "0") == "0") {
            return am.findForward("searchlst");
        } else {
            return am.findForward("view");
        }
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
            util.updAccessLog(req,res,"Web Access", "load start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        WebAccessFrm udf = (WebAccessFrm) af;
        MailAction mailFt = new MailAction();
        HashMap tbls       = new HashMap();
        String    nmeIdn     = udf.getNmeIdn();
      
        ArrayList    params     = new ArrayList();
        String    srchQ      = "",
                  srchFields = "";

        srchQ = " and nme_idn = ? ";
      
        params.add(nmeIdn);

        String tblNmes = "";

        for (int i = 0; i < tbls.size(); i++) {
            String delim = "";

            if (i > 0) {
                delim = ", ";
            }

            tblNmes += delim + (String) tbls.get(i);
        }

        srchFields = getSrchFields(daos);

        ArrayList list = getSrchList(srchFields, srchQ, params, daos, "SRCH",uiForms,req,udf);

        // udf.setList(list);
        req.setAttribute("webaccesslst", list);
        HashMap vldInvalid=vldInvalid(req,res,nmeIdn); 
        req.setAttribute("vldInvalid", vldInvalid);
            util.updAccessLog(req,res,"Web Access", "load end");
        return am.findForward("view");
        }
    }

    public HashMap vldInvalid(HttpServletRequest req,HttpServletResponse res,String lNmeIdn){
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
        HashMap vldInvalidterms=new HashMap();
        String nmerelVldQ = "select decode(to_dt,'','','red') color,usr_id\n" + 
        "from web_usrs, nmerel_all_trm_v b where 1 =1  and web_usrs.rel_idn = b.nmerel_idn and nme_idn =?" ;
        ArrayList params = new ArrayList();
        params.add(lNmeIdn);
        ArrayList outLst = db.execSqlLst("nmerelVldQ", nmerelVldQ, params);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
        while (rs.next()) {
                    vldInvalidterms.put(util.nvl(rs.getString("usr_id")),util.nvl(rs.getString("color")));
                }
                rs.close(); pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        return vldInvalidterms;
    }
    public ArrayList getSrchList(String srchFields, String srchQ, ArrayList params, ArrayList daos, String typ,UIForms  uiForms,HttpServletRequest req,WebAccessFrm udf) {
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
        ArrayList list = new ArrayList();

        try {
            String frmTbl = "web_usrs";

            if (util.nvl(uiForms.getFRM_TBL()).length() > 0) {
                frmTbl = uiForms.getFRM_TBL();
            }

            String whereCl = "";

            if (util.nvl(uiForms.getWHERE_CL()).length() > 0) {
                whereCl = " and " + uiForms.getWHERE_CL();
            }

            String getDataQ = " select " + srchFields + " from " + frmTbl + " where 1 =1 " + whereCl + srchQ
                              + " order by 1 ";

            ArrayList outLst = db.execSqlLst(" get Search data", getDataQ, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                WebUsrs srchDao = new WebUsrs();
                String  lIdn    = rs.getString(1);
                String  nmeIdn  = udf.getNmeIdn();

                /*
                 * String dflt_yn = rs.getString("dyn");
                 * if(dflt_yn.equals("Y")) {
                 *   if(util.nvl(udf.getIdn(),"0") == "0")
                 *       udf.setIdn(addrIdn);
                 * }
                 */
                srchDao.setIdn(lIdn);
                srchDao.setNmeIdn(nmeIdn);

                // nme.setValue("idn", nmeIdn);
                for (int i = 0; i < daos.size(); i++) {
                    UIFormsFields dao      = (UIFormsFields) daos.get(i);
                    String        tblFld   = dao.getTBL_FLD().toLowerCase();
                    String        lFld     = dao.getFORM_FLD();
                    String        lFldTyp  = dao.getFLD_TYP();
                    String        dbVal    = util.nvl(rs.getString(tblFld));
                    String        fldAlias = util.nvl(dao.getALIAS());
                    String        aliasFld = "",
                                  aliasVal = "";

                 

                    try {
                        if (fldAlias.length() > 0) {
                            aliasFld = fldAlias.substring(fldAlias.lastIndexOf(" ") + 1);
                            aliasVal = util.nvl(rs.getString(aliasFld));
                        }
                    } catch (SQLException sqle) {

                        // TODO: Add catch code
                        // sqle.printStackTrace();
                        
                    }

                    if ((typ.equalsIgnoreCase("VIEW"))) {
                        udf.setValue(lFld, dbVal);
                      

                        if (aliasFld.length() > 0) {
                            udf.setValue(aliasFld, aliasVal);
                           
                        }
                    }

                    srchDao.setValue(lFld, dbVal);

                    if (aliasFld.length() > 0) {
                        srchDao.setValue(aliasFld, aliasVal);
                    }
                }

                list.add(srchDao);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }

        return list;
    }

    public ActionForward reset(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Web Access", "reset start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        WebAccessFrm udf = (WebAccessFrm) af;
        ArrayList params     = new ArrayList();
        String lNmeIdn    = util.nvl(udf.getNmeIdn());
        if(lNmeIdn.equals(""))
            lNmeIdn = util.nvl(req.getParameter("nmeIdn"));
        udf.reset();
        udf.setNmeIdn(lNmeIdn);
        
        String  srchFields = getSrchFields(daos);
        String  srchQ = " and nme_idn = ? ";
        
     
        params.add(lNmeIdn);
        
        ArrayList list = getSrchList(srchFields, srchQ, params, daos, "SRCH",uiForms,req,udf);
        req.setAttribute("webaccesslst", list);
        req.setAttribute("method","reset");
            util.updAccessLog(req,res,"Web Access", "reset end");
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
            util.updAccessLog(req,res,"Web Access", "edit start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        WebAccessFrm udf = (WebAccessFrm) af;
        MailAction mailFt = new MailAction();
        
        ArrayList params     = new ArrayList();
        String lNmeIdn = util.nvl(req.getParameter("nmeIdn"));
        
        String usrIdn = util.nvl(req.getParameter("idn"));
        
        String  srchFields = getSrchFields(daos);
        String  srchQ = " and usr_id = ? and nme_idn = ? ";
        
        params.add(usrIdn);
        params.add(lNmeIdn);
        
        ArrayList list = getSrchList(srchFields, srchQ, params, daos, "VIEW",uiForms,req,udf);
       
       
        
       
        
         srchFields = getSrchFields(daos);
        srchQ = "  and nme_idn = ? ";
        
        params = new ArrayList();
        params.add(lNmeIdn);
        
        list = getSrchList(srchFields, srchQ, params, daos, "SRCH",uiForms,req,udf);
        
        //udf.setList(list);
        //info.setNmeSrchList(list);
        
        udf.setNmeIdn(lNmeIdn);
        udf.setIdn(usrIdn);
        req.setAttribute("webaccesslst", list);
        req.setAttribute("method", "edit");
            util.updAccessLog(req,res,"Web Access", "edit end");
        return am.findForward("view");
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
                util.updAccessLog(req,res,"Web Access", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Web Access", "init");
            }
            }
            return rtnPg;
         }

    public String getSrchFields(ArrayList daos) {
        String srchFields;

        srchFields = "";
        JspUtil util=new JspUtil();
        ArrayList ukFld = new ArrayList();

        for (int j = 0; j < daos.size(); j++) {
            UIFormsFields dao     = (UIFormsFields) daos.get(j);
            String        lTblFld = dao.getTBL_FLD().toLowerCase();
            String        tblNme  = dao.getTBL_NME();

            if (util.nvl(dao.getFLG()).equals("UK")) {
                ukFld.add(lTblFld);
            }

            String delim = ", ";

            /*
             * if (j==0) {
             *   delim = "";
             * }
             */
            if (j == 0) {
                srchFields = " " + tblNme + ".usr_id";
            }

            srchFields += delim + tblNme + "." + lTblFld;

            if (util.nvl(dao.getALIAS()).length() > 0) {
                srchFields += delim + dao.getALIAS();
            }
        }

        return srchFields;
    }
    
    public ActionForward assignRole(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                util.updAccessLog(req,res,"Web Access", "assignRole start");
            WebAccessFrm udf = (WebAccessFrm) af;

            String usrIdn = util.nvl(req.getParameter("idn"));
            if(usrIdn.equals(""))
                usrIdn = (String)udf.getValue("usrIdn");
            ArrayList roleNmeLst = new ArrayList();
            ArrayList roleList = new ArrayList();
            String roleDtlSql = "select role_idn , nvl(role_dsc , role_nm) rolDsc , srt , typ , decode(typ,null,'CH','RD') fldTyp , decode(typ,null,role_nm,typ) fldNme  from mrole where stt='A' and to_dte is null order by typ , srt";

                ArrayList outLst = db.execSqlLst("roleDtl", roleDtlSql,new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
               HashMap roleDtl = new HashMap();
              String FldNme = rs.getString("fldNme");
                roleDtl.put("FldNme", util.nvl(rs.getString("fldNme")));
                roleDtl.put("FldTyp", util.nvl(rs.getString("fldTyp")));
                roleDtl.put("roleIdn",util.nvl(rs.getString("role_idn")));
                roleDtl.put("rolDsc",util.nvl(rs.getString("rolDsc")));
                roleDtl.put("typ", util.nvl(rs.getString("typ")));
                roleList.add(roleDtl);
                if(!roleNmeLst.contains(FldNme))
                    roleNmeLst.add(FldNme);
            }
            rs.close(); pst.close();
            req.setAttribute("roleDtlList", roleList);
             
            session.setAttribute("roleNmeList", roleNmeLst);
            
            String sql = "select  a.role_idn , decode(b.typ,null,role_nm,b.typ) fldNme  from usr_role a , mrole b where a.role_idn = b.role_idn " + 
            " and a.stt='A' and a.usr_idn = ?  group by a.role_idn, decode(b.typ,null,role_nm,b.typ) ";
            ArrayList ary = new ArrayList();
            ary.add(usrIdn);

                outLst = db.execSqlLst("usrRole", sql, ary);
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                String roleIdn = util.nvl(rs.getString("role_idn"));
                String fldNme =  util.nvl(rs.getString("fldNme"));
               
                udf.setValue(fldNme, roleIdn);
            }
            rs.close(); pst.close();
            session.setAttribute("usrIdn",usrIdn);
            udf.setValue("usrIdn", usrIdn);
                util.updAccessLog(req,res,"Web Access", "assignRole end");
            return am.findForward("role");
            }
        }
       
       public ActionForward saveRole(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
               util.updAccessLog(req,res,"Web Access", "saveRole start");
             WebAccessFrm udf = (WebAccessFrm)af;
            String usrIdn = (String)udf.getValue("usrIdn");
            ArrayList roleNmeLst = (ArrayList)session.getAttribute("roleNmeList");
            ArrayList ary = new ArrayList();
            ary.add(usrIdn);
            if(roleNmeLst.size()>0){
                int up =0;
                int dt= db.execCall("update", "update usr_role set stt='IA' where usr_idn=? ", ary);
                for(int i = 0 ; i < roleNmeLst.size() ; i++){
                    String fldNme = (String)roleNmeLst.get(i);
                    String roleIdn = util.nvl((String)udf.getValue(fldNme));
                    if(!roleIdn.equals("")){
                        String updateRole = "update usr_role set stt='A' where role_idn= ? and usr_idn=? ";
                        ary = new ArrayList();
                        ary.add(roleIdn);
                        ary.add(usrIdn);
                        up = db.execUpd("update", updateRole,ary);
                        if(up < 1){
                            String insertRole = "insert into usr_role(ur_idn,role_idn,usr_idn,stt) "+
                                "select SEQ_USR_ROLE_IDN.nextval , ? , ?, ? from dual ";
                            ary = new ArrayList();
                            ary.add(roleIdn);
                            ary.add(usrIdn);
                            ary.add("A");
                            up = db.execUpd("insertRole",insertRole,ary);
                            }                        
                    }
                    if(up > 0)
                    req.setAttribute("msg", "Role Update Successfully");
                }
                ary = new ArrayList();
                ary.add(usrIdn);
                up = db.execUpd("update", "delete usr_role where stt='IA' and usr_idn=? ",ary);
               
            }
               util.updAccessLog(req,res,"Web Access", "saveRole end");
             return assignRole(am, af, req, res);
           }
       }

}


//~ Formatted by Jindent --- http://www.jindent.com
