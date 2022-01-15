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

public class NmeCommAction extends DispatchAction {
    private final String formName = "customercomm";

    public NmeCommAction() {
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
            util.updAccessLog(req,res,"Customer Comm", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeCommFrm udf = (NmeCommFrm) af;
        String    lNmeIdn    = util.nvl(udf.getNmeIdn(), "0");
        String    lRelIdn    = util.nvl(udf.getIdn(), "0");
        String    srch       = util.nvl((String) udf.getSearch());
        String    modify     = util.nvl((String) udf.getModify());
        String    addnew     = util.nvl((String) udf.getAddnew());
        String    view       = util.nvl((String) udf.getView());

      

        if (srch.length() > 0) {
            return search(am, af, req, res);
        } else if (view.length() > 0) {
            return load(am, af, req, res);
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

            /*
             * if(addnew.length() > 0) {
             *   ArrayList params = new ArrayList();
             *   String insIntoQ = " insert into nmerel (nmerel_idn, nme_idn, flg ";
             *   String insValQ = "values(nmerel_seq.nextval, ?, ?";
             *   params.add(lNmeIdn);
             *   params.add("NEW");
             *   for(int j=0; j < daos.size(); j++) {
             *       UIFormsFields dao = (UIFormsFields)daos.get(j);
             *       String lFld = dao.getFORM_FLD();
             *       String fldNm = lFld ;
             *       String lVal = (String)udf.getValue(fldNm);
             *       String lReqd = dao.getREQ_YN();//util.nvl((String)formFields.get(lFld+"REQ"));
             *       if((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0)) {
             *           String errorMsg = lFld + ", Required";
             *           errors.add(errorMsg);
             *           params.clear();
             *           break;
             *       }
             *       else {
             *           String lTblFld = dao.getTBL_FLD().toLowerCase();//util.nvl((String)formFields.get(lFld+"TF"));
             *           insIntoQ += ", " + lTblFld   ;
             *           insValQ += ",?";
             *           params.add(lVal);
             *       }
             *   }
             *   if(params.size() > 0) {
             *       String sql = insIntoQ + ") " + insValQ + ")";
             *       int ct = db.execUpd(" Ins Menu", sql, params);
             *   }
             * } else {
             *   util.SOP(formName + " : Modify ");
             *   String srchFields = getSrchFields(daos);
             *   String getMenu = "select "+ srchFields + " from nmerel where nme_idn = ? and nmerel_idn = ?";
             *
             *   ArrayList params = new ArrayList();
             *   params.add(lNmeIdn);
             *   params.add(lRelIdn);
             *   ResultSet rs = db.execSql(formName + " for update", getMenu, params);
             *   while(rs.next()) {
             *       int lIdn = rs.getInt(1);
             *
             *   String updQ = " update nmerel set nmerel_idn = nmerel_idn and nme_idn = nme_idn and flg = 'MOD' " ;
             *   String condQ = " where nmerel_idn = ? ";
             *   params = new ArrayList();
             *   String isChecked = util.nvl((String)udf.getValue("upd_"+lIdn));
             *   isChecked = "yes";
             *   if(isChecked.length() > 0) {
             *       //params.add(Integer.toString(menuIdn));
             *       for(int j=0; j < daos.size(); j++) {
             *           UIFormsFields dao = (UIFormsFields)daos.get(j);
             *
             *           String lFld = dao.getFORM_FLD();//(String)flds.get(j);
             *           String fldNm = lFld ;
             *           String lVal = (String)udf.getValue(fldNm);
             *           String lReqd = dao.getREQ_YN();//util.nvl((String)formFields.get(lFld+"REQ"));
             *           if((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0)) {
             *               String errorMsg = lIdn + "," + lFld + ", Required";
             *               errors.add(errorMsg);
             *               params.clear();
             *               break;
             *           }
             *           else {
             *               String lTblFld = dao.getTBL_FLD().toLowerCase(); //util.nvl((String)formFields.get(lFld+"TF"));
             *               updQ += ", " + lTblFld + " = ? "  ;
             *               params.add(lVal);
             *           }
             *       }
             *   }
             *   if(params.size() > 0) {
             *       params.add(Integer.toString(lIdn));
             *       util.SOP(" Menu UpdQ "+ updQ + condQ);
             *       util.SOP(params.toString());
             *
             *       int ct = db.execUpd(" upd menu "+ lIdn, updQ + condQ, params);
             *   }
             * }
             * }
             */
        }

        // util.SOP(errors.toString());
        // req.setAttribute("errors", errors);
        util.updAccessLog(req,res,"Customer Comm", "save end");
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
            util.updAccessLog(req,res,"Customer Comm", "delete start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeCommFrm udf = (NmeCommFrm) af;
        String    delNmeIdn = req.getParameter("nmeIdn");
        String    delIdn    = req.getParameter("delIdn");
        String    delQ      = " update nmerel set vld_dte = sysdate where nme_idn = ? and nmerel_idn = ?";
        ArrayList    params    = new ArrayList();

        params.add(delNmeIdn);
        params.add(delIdn);

        int cnt = db.execUpd(" Del " + delIdn, delQ, params);
        udf.setNmeIdn(delNmeIdn);
        udf.setIdn("");
            util.updAccessLog(req,res,"Customer Comm", "delete end");
        return load(am, af, req, res);
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
            util.updAccessLog(req,res,"Customer Comm", "search start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeCommFrm udf = (NmeCommFrm) af;
        HashMap tbls       = new HashMap();
        String    nmeIdn     = udf.getNmeIdn();
        ArrayList    params     = new ArrayList();
        String    srchQ      = "",
                  srchFields = "";

        srchQ = " and dflt_yn = 'Y' and nme_idn = ?  ";
        params.add(nmeIdn);

        String tblNmes = "";

        for (int i = 0; i < tbls.size(); i++) {
            String delim = "";

            if (i > 0) {
                delim = ", ";
            }

            tblNmes += delim + (String) tbls.get(i);
        }

        // srchFields = getSrchFields(daos);
        srchFields = helper.getSrchFields(daos, " nmerel_idn, nme_idn, dflt_yn dyn ");

        ArrayList list = helper.getSrchList(uiForms, "nmerel", srchFields, srchQ, params, daos, "SRCH");

        // getSrchList(srchFields, srchQ, params, daos, "SRCH") ;

        // udf.setList(list);
        if (list.size() > 0) {
            GenDAO gen = (GenDAO) list.get(0);

            for (int i = 0; i < daos.size(); i++) {
                udf.setIdn(gen.getIdn());
            }
        }

        req.setAttribute("NmeRelList", list);
    
            util.updAccessLog(req,res,"Customer Comm", "search end");
        if (util.nvl(udf.getIdn(), "0") == "0") {
            return am.findForward("searchlst");
        } else {
            return load(am, af, req, res);
        }

        // return am.findForward("view");
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
            util.updAccessLog(req,res,"Customer Comm", "load start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeCommFrm udf = (NmeCommFrm) af;
        String    lNmeIdn = util.nvl(udf.getNmeIdn());
        if(lNmeIdn.equals(""))
            lNmeIdn = util.nvl(req.getParameter("nmeIdn"));
        
        String    lRelIdn = util.nvl(udf.getIdn());
        if(lRelIdn.equals("")){
             lRelIdn = util.nvl(req.getParameter("Idn"));
        }
        
       

        String srchFields = getSrchFields(daos);
        String srchQ      = " and nme_idn = ?  ";
        ArrayList params     = new ArrayList();

        params.add(lNmeIdn);
        if(!lRelIdn.equals("")){
         srchQ = srchQ + " and nmerel_idn = ?" ;
         params.add(lRelIdn);
        }
    
        srchFields = helper.getSrchFields(daos, " nmerel_idn, nme_idn, dflt_yn dyn ");

        ArrayList list = helper.getSrchList(uiForms, "nmerel", srchFields, srchQ, params, daos, "SRCH");

        if (list.size() > 0) {
            GenDAO gen = (GenDAO) list.get(0);

            for (int i = 0; i < daos.size(); i++) {
                udf.setIdn(gen.getIdn());

                UIFormsFields dao      = (UIFormsFields) daos.get(i);
                String        lFld     = dao.getFORM_FLD();
                String        fldAlias = util.nvl(dao.getALIAS());
                String        aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);

                udf.setValue(lFld, util.nvl((String) gen.getValue(lFld)));
                udf.setValue(lFld + "_dsp", util.nvl((String) gen.getValue(aliasFld)));
            }
        }

        req.setAttribute("NmeRelList", list);
       
        udf.setNmeIdn(lNmeIdn);
        udf.setIdn(lRelIdn);
            util.updAccessLog(req,res,"Customer Comm", "load end");
        return am.findForward("view");
        }
    }

    public ActionForward loadApprove(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Customer Comm", "loadApprove start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeCommFrm udf = (NmeCommFrm) af;
        ArrayList    params     = new ArrayList();
        String    srchQ      = "",
                  srchFields = "";

        srchQ = " and nvl(flg,'NEW') in(?,?) ";
        params.add("NEW");
        params.add("MOD");
        srchFields = getSrchFields(daos);

        ArrayList list = getSrchList(srchFields, srchQ, params, daos, "APPROVE",uiForms,req,udf);

        // udf.setList(list);
        info.setNmeRelList(list);
            util.updAccessLog(req,res,"Customer Comm", "loadApprove end");

        return am.findForward("approvelst");
        }
    }

    public ArrayList getSrchList(String srchFields, String srchQ, ArrayList params, ArrayList daos, String typ,UIForms  uiForms,HttpServletRequest req,NmeCommFrm udf) {
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
            String    getDataQ = " select " + srchFields + " from nmerel where 1 =1 " + srchQ + " order by dflt_yn, 1 ";

            ArrayList outLst = db.execSqlLst(" get Search data", getDataQ, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                NmeRel srchDao = new NmeRel();
                String relIdn  = rs.getString(1);
                String nmeIdn  = rs.getString(2);
                String dflt_yn = rs.getString("dyn");

                if (dflt_yn.equals("Y")) {
                    System.out.println(" Current Idn : " + udf.getIdn());

                    if (util.nvl(udf.getIdn(), "0").equals("0")) {
                        info.setValue("relidn", relIdn);
                    }

                    // udf.setIdn(relIdn);
                }

                srchDao.setIdn(relIdn);
                srchDao.setNmeIdn(nmeIdn);

                // nme.setValue("idn", nmeIdn);
                for (int i = 0; i < daos.size(); i++) {
                    UIFormsFields dao    = (UIFormsFields) daos.get(i);
                    String        tblFld = dao.getTBL_FLD().toLowerCase();
                    String        lFld   = dao.getFORM_FLD();
                    String        dbVal  = util.nvl(rs.getString(tblFld));

                    if (!(typ.equalsIgnoreCase("APPROVE"))) {
                        if ((typ.equalsIgnoreCase("VIEW")) || (dflt_yn.equals("Y"))) {
                            udf.setValue(lFld, dbVal);
                            
                        }

                        srchDao.setValue(lFld, dbVal);
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
                util.updAccessLog(req,res,"Customer Comm", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Customer Comm", "init");
            }
            }
            return rtnPg;
         }
    public String getSrchFields(ArrayList daos) {
        String srchFields = " nmerel_idn, nme_idn, dflt_yn dyn ";
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

            // if(!(dao.getFLD_TYP().equals("I")))
            srchFields += delim + lTblFld;
        }

        return srchFields;
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
            util.updAccessLog(req,res,"Customer Comm", "reset start");
        NmeCommFrm udf = (NmeCommFrm) af;
        udf.reset();
            util.updAccessLog(req,res,"Customer Comm", "reset end");
        return am.findForward("reset");
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
