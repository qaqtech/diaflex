package ft.com.masters;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.dao.*;

import ft.com.generic.GenericImpl;

import java.io.IOException;

import java.sql.Connection;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DayTermsAction extends DispatchAction {
    private final String formName = "terms";

    public DayTermsAction() {
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
            util.updAccessLog(req,res,"Terms form", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            ArrayList errors  = new ArrayList();
        DayTermsFrm udf = (DayTermsFrm) af;
        ArrayList errorLst  = new ArrayList();
        ArrayList errorMsgs = new ArrayList();

        // Map values = udf.getValues();
        // util.SOP("@save "+ values.toString());
        // HashMap allFormFields = info.getFormFieslds();
        // ArrayList flds = (formFields.get("FLDS") == null)?new ArrayList():(ArrayList)formFields.get("FLDS");
        // flds.clear();
        if (udf.getAddnew() != null) {
            

            ArrayList actions = new ArrayList();

            for (int i = 1; i <= 10; i++) {
                ArrayList params   = new ArrayList();
                String insIntoQ = " insert into mtrms (idn ";
                String insValQ  = "values(SEQMTRMS.nextval";
                boolean isValid = false;
                for (int j = 0; j < daos.size(); j++) {
                    UIFormsFields dao   = (UIFormsFields) daos.get(j);
                    String        lFld  = dao.getFORM_FLD();
                    String        fldNm = lFld + "_" + i + "_new";
                    String        lVal  = (String) udf.getValue(fldNm);
                    String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));
                    String        lDsc = dao.getDSP_TTL();
                    if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                        String errorMsg = i+ "-" + lDsc + " Required.";

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
                    errorLst.add(errors);
                    errors = new ArrayList();
                }
            }
        }

        if (udf.getModify() != null) {
           

            String    getData = "select " + getSrchFields(daos)
                                + " from mtrms where till_dt is null order by dys, pct ";
            ResultSet rs      = db.execSql("getTerms", getData, new ArrayList());

            while (rs.next()) {
                int    lIdn      = rs.getInt(1);
                String updQ      = " update mtrms set idn = ? ";
                String condQ     = " where idn = ? ";
                ArrayList params    = new ArrayList();
                String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn));
                boolean isValid = false;
             

                if (isChecked.length() > 0) {
                    params.add(Integer.toString(lIdn));

                    for (int j = 0; j < daos.size(); j++) {
                        UIFormsFields dao   = (UIFormsFields) daos.get(j);
                        String        lFld  = dao.getFORM_FLD();    // (String)flds.get(j);
                        String        fldNm = lFld + "_" + lIdn;
                        String        lVal  = (String) udf.getValue(fldNm);
                        String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));

                        String        lDsc = dao.getDSP_TTL();
                        if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                            String errorMsg = j+ "-" + lDsc + " Required.";

                            errors.add(errorMsg);
                            params.clear();
                        } else {
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
                            
                            if(lReqd.equalsIgnoreCase("Y"))
                               isValid = true;
                        }
                    }
                }

                if (params.size() > 0 && errors.size()==0) {
                    params.add(Integer.toString(lIdn));
                  
                    int ct = db.execUpd(" upd menu " + lIdn, updQ + condQ, params);
                }
                if(isValid){
                    errorLst.add(errors);
                    errors = new ArrayList();
                }                                           
            }
            rs.close();
        }

        util.SOP(errorLst.toString());

        // saveMessages(req, messages);
        if (udf.getDelete() != null) {
            String lIdnlst="";
            String    getData = "select " + getSrchFields(daos)
                                + " from mtrms where till_dt is null order by dys, pct ";
            ResultSet rs      = db.execSql("getTerms", getData, new ArrayList());

            while (rs.next()) {
                int    lIdn      = rs.getInt(1);
                String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn));
                if (isChecked.length() > 0) {
                    lIdnlst=lIdnlst+","+lIdn;
                }
            }
            rs.close();
            if(!lIdnlst.equals("")){
                lIdnlst=lIdnlst.replaceFirst("\\,", "");
                int ct = db.execUpd(" del msg", "update mtrms set till_dt=sysdate where idn in("+lIdnlst+")", new ArrayList());
            }
        }
        req.setAttribute("errors", errorLst);
            util.updAccessLog(req,res,"Terms form", "save end");
        return load(am, af, req, res);

        // return am.findForward("diaflexMenu");
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
            util.updAccessLog(req,res,"Terms form", "delete start");
        DayTermsFrm udf = (DayTermsFrm) af;

        int    idn  = udf.getId();
        String delQ = " update mtrms set till_dt=sysdate where idn = ? ";
        ArrayList ary  = new ArrayList();

        ary.add(String.valueOf(idn));

        int ct = db.execUpd(" del trms", delQ, ary);
            util.updAccessLog(req,res,"Terms form", "delete end");
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
            util.updAccessLog(req,res,"Terms form", "load start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
        DayTermsFrm udf = (DayTermsFrm) af;
        ArrayList error = (ArrayList)req.getAttribute("errors");
        if(error!=null && error.size() >0){
            ArrayList errSizeList = (ArrayList)error.get(0);
             if(errSizeList!=null && errSizeList.size() ==0){
             
                 udf.reset();
             }
        }else{
          udf.reset();
        }

        String    getData = "select " + getSrchFields(daos) + " from mtrms where till_dt is null order by dys, pct ";
        ResultSet rs      = db.execSql("getMenuLoad", getData, new ArrayList());

        while (rs.next()) {
            int menuIdn = rs.getInt(1);

            for (int i = 0; i < daos.size(); i++) {
                UIFormsFields dao    = (UIFormsFields) daos.get(i);
                String        lFld   = dao.getFORM_FLD();
                String        tblFld = dao.getTBL_FLD().toLowerCase();

                try {
                    udf.setValue(lFld + "_" + menuIdn, util.nvl(rs.getString(tblFld)));
                } catch (Exception fe) {
                           }
            }
        }
        rs.close();
            util.updAccessLog(req,res,"Terms form", "load end");
        return am.findForward("dayterms");
        }
    }

    public String getSrchFields(ArrayList daos) {
        String srchFields = " idn";
        JspUtil util=new JspUtil();
        ArrayList ukFld = new ArrayList();

        for (int j = 0; j < daos.size(); j++) {
            UIFormsFields dao     = (UIFormsFields) daos.get(j);
            String        lTblFld = dao.getTBL_FLD().toLowerCase();

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
                util.updAccessLog(req,res,"Terms form", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Terms form", "init");
            }
            }
            return rtnPg;
         }
}


//~ Formatted by Jindent --- http://www.jindent.com
