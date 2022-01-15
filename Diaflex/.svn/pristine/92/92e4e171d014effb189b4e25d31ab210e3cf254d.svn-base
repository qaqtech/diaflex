package ft.com.contact;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.*;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.*;

import java.io.IOException;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class NmeGrpAction extends DispatchAction {
    private final String formName   = "nmegrp";

    public NmeGrpAction() {
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
            util.updAccessLog(req,res,"Nme Grp", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeGrpFrm udf = (NmeGrpFrm) af;

        String lEditIdn = util.nvl(udf.getIdn(), "0");
        String srch     = util.nvl((String) udf.getSearch());
        String modify   = util.nvl((String) udf.getModify());
        String addnew   = util.nvl((String) udf.getAddnew());
        String view     = util.nvl((String) udf.getView());
        int    lmt      = Integer.parseInt(util.nvl(uiForms.getREC(), "10"));

       

        if (srch.length() > 0) {
            return search(am, af, req, res);
        } else if (view.length() > 0) {
            return load(am, af, req, res);
        } else {
            if (addnew.length() > 0) {
             

                for (int i = 1; i <= lmt; i++) {
                    int    lIdn     = i;
                    ArrayList params   = new ArrayList();
                    String insIntoQ = " insert into nme_grp (nme_grp_idn, nme_idn ";
                    String insValQ  = "values(nme_grp_seq.nextval, ?";

                    params.add(udf.getNmeIdn());

                    for (int j = 0; j < daos.size(); j++) {
                        UIFormsFields dao   = (UIFormsFields) daos.get(j);
                        String        lFld  = dao.getFORM_FLD();
                        String        fldNm = lFld + "_" + lIdn;
                        String        lVal  = (String) udf.getValue(fldNm);
                        String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));

                        if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0)) {
                            String errorMsg = lIdn + "," + lFld + ", Required";

                            errors.add(errorMsg);
                            params.clear();

                            break;
                        } else {
                            String lTblFld = dao.getTBL_FLD().toLowerCase();    // util.nvl((String)formFields.get(lFld+"TF"));

                            insIntoQ += ", " + lTblFld;
                            if((dao.getFLD_TYP().equals("DT")) && (lVal.length() == 10))
                                insValQ += ", to_date(?, 'dd-mm-rrrr')";
                            else
                                insValQ  += ", ?";
                            params.add(lVal);
                        }
                    }

                    if (params.size() > 0) {
                        String sql = insIntoQ + ") " + insValQ + ")";
                        int    ct  = db.execUpd(" Ins Menu", sql, params);
                    }
                }
            } else {
                

                String getMenu = "select " + getSrchFields(daos) + " from nme_grp  ";
                ArrayList params  = new ArrayList();

                // params.add(udf.getId());

                ArrayList outLst = db.execSqlLst("getUIForm for update", getMenu, params);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                    int    lIdn  = rs.getInt(1);
                    String updQ  = " update nme_grp set nme_grp_idn = nme_grp_idn ";
                    String condQ = " where nme_grp_idn = ? ";

                    params = new ArrayList();

                    String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn));

                    if (isChecked.length() > 0) {

                        // params.add(Integer.toString(menuIdn));
                        for (int j = 0; j < daos.size(); j++) {
                            UIFormsFields dao   = (UIFormsFields) daos.get(j);
                            String        lFld  = dao.getFORM_FLD();    // (String)flds.get(j);
                            String        fldNm = lFld + "_" + lIdn;
                            String        lVal  = (String) udf.getValue(fldNm);
                            String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));

                            if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0)) {
                                String errorMsg = lIdn + "," + lFld + ", Required";

                                errors.add(errorMsg);
                                params.clear();

                                break;
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
                            }
                        }
                    }

                    if (params.size() > 0) {
                        params.add(Integer.toString(lIdn));
                      

                        int ct = db.execUpd(" upd menu " + lIdn, updQ + condQ, params);
                    }
                }
                rs.close(); pst.close();
            }
        }

        util.SOP(errors.toString());
        req.setAttribute("errors", errors);
            util.updAccessLog(req,res,"Nme Grp", "save end");
        return load(am, af, req, res);
        }
    }

    public ActionForward reset(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Nme Grp", "reset start");
        NmeGrpFrm udf = (NmeGrpFrm) af;
        udf.reset();
            util.updAccessLog(req,res,"Nme Grp", "reset end");
        return am.findForward("reset");
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
            util.updAccessLog(req,res,"Nme Grp", "delete start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeGrpFrm udf = (NmeGrpFrm) af;
        String    delIdn  = udf.getNmeIdn();
        String    delQ    = " update mnme set vld_dte = sysdate where nme_idn = ? ";
        ArrayList    params  = new ArrayList();

        params.add(delIdn);

        int cnt = db.execUpd(" Del " + delIdn, delQ, params);
            util.updAccessLog(req,res,"Nme Grp", "delete end");
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
            util.updAccessLog(req,res,"Nme Grp", "search start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeGrpFrm udf = (NmeGrpFrm) af;

        String lNmeIdn    = util.nvl(udf.getNmeIdn(), "0");
        ArrayList params     = new ArrayList();
        String srchQ      = "",
               srchFields = "";

        srchFields = getSrchFields(daos);
        srchQ      = " and nme_idn = ? and vld_dte is null";
        params.add(lNmeIdn);

        ArrayList list = getSrchList(srchFields, srchQ, params, daos, "VIEW",uiForms,req,udf);

       
            util.updAccessLog(req,res,"Nme Grp", "search end");
        return am.findForward("view");
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
            util.updAccessLog(req,res,"Nme Grp", "load start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeGrpFrm udf = (NmeGrpFrm) af;

        String lNmeIdn    = util.nvl(udf.getNmeIdn(), "0");
        String srchFields = getSrchFields(daos);
        String srchQ      = " and nme_idn = ? and vld_dte is null";
        ArrayList params     = new ArrayList();

        params.add(lNmeIdn);

        ArrayList list = getSrchList(srchFields, srchQ, params, daos, "VIEW",uiForms,req,udf);

        util.SOP(" NmeGrpSearch Count : " + list.size());
            util.updAccessLog(req,res,"Nme Grp", "load end");
        return am.findForward("view");
        }
    }

    public ArrayList getSrchList(String srchFields, String srchQ, ArrayList params, ArrayList daos, String typ,UIForms  uiForms,HttpServletRequest req,NmeGrpFrm udf) {
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
            String    getDataQ = " select " + srchFields + " from nme_grp where 1 =1 " + srchQ + " order by 1,2 ";

            ArrayList outLst = db.execSqlLst(" get Search data", getDataQ, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                int lIdn = rs.getInt(1);

                for (int i = 0; i < daos.size(); i++) {
                    UIFormsFields dao    = (UIFormsFields) daos.get(i);
                    String        lFld   = dao.getFORM_FLD();
                    String        tblFld = dao.getTBL_FLD().toLowerCase();

                    try {
                        udf.setValue(lFld + "_" + lIdn, util.nvl(rs.getString(tblFld)));
                    } catch (Exception fe) {
                       
                    }
                }
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }

        return list;
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
                util.updAccessLog(req,res,"Nme Grp", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Nme Grp", "init");
            }
            }
            return rtnPg;
         }
    public String getSrchFields(ArrayList daos) {
        String srchFields = " nme_grp_idn ";
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
}


//~ Formatted by Jindent --- http://www.jindent.com
