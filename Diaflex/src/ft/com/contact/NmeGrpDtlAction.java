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

public class NmeGrpDtlAction extends DispatchAction {
    private final String formName = "nmegrpdtl";

    /*
     * public ArrayList getSrchList(String srchFields, String srchQ, ArrayList params, ArrayList daos, String typ) {
     * ArrayList list = new ArrayList();
     * try {
     *     String getDataQ =
     *         " select " + srchFields + " from nme_grp_dtl where 1 =1 " +
     *         srchQ + " order by 1 ";
     *     ResultSet rs = db.execSql(" get Search data", getDataQ, params);
     *     while (rs.next()) {
     *         MAddr srchDao = new MAddr();
     *         String addrIdn = rs.getString(1);
     *         String nmeIdn = rs.getString(2);
     *         String dflt_yn = rs.getString("dyn");
     *         if(dflt_yn.equals("Y")) {
     *             if(util.nvl(udf.getIdn(),"0") == "0")
     *                 udf.setIdn(addrIdn);
     *         }
     *         srchDao.setIdn(addrIdn);
     *         srchDao.setNmeIdn(nmeIdn);
     *         //nme.setValue("idn", nmeIdn);
     *         for (int i = 0; i < daos.size(); i++) {
     *             UIFormsFields dao = (UIFormsFields)daos.get(i);
     *             String tblFld = dao.getTBL_FLD().toLowerCase();
     *             String lFld = dao.getFORM_FLD();
     *             String dbVal = util.nvl(rs.getString(tblFld));
     *             if((typ.equalsIgnoreCase("VIEW")) || (dflt_yn.equals("Y"))) {
     *                 udf.setValue(lFld, dbVal);
     *                 util.SOP(formName + " View " + lFld + " : "+ dbVal);
     *             }
     *             srchDao.setValue(lFld, dbVal);
     *         }
     *         list.add(srchDao);
     *     }
     *
     * } catch (SQLException sqle) {
     *     // TODO: Add catch code
     *     sqle.printStackTrace();
     * }return list ;
     * }
     *
     * public String getSrchFields(ArrayList daos) {
     * String srchFields = " nme_grp_dtl_idn, nme_grp_idn " ;
     * ukFld = new ArrayList();
     * for(int j=0; j < daos.size(); j++) {
     *     UIFormsFields dao = (UIFormsFields)daos.get(j);
     *     String lTblFld = dao.getTBL_FLD().toLowerCase();
     *     if(util.nvl(dao.getFLG()).equals("UK"))
     *         ukFld.add(lTblFld);
     *     String delim = ", ";
     *     srchFields += delim + lTblFld   ;
     *
     * }
     * return srchFields ;
     * }
     */
    public NmeGrpDtlAction() {
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
            util.updAccessLog(req,res,"Nme Grp Dtl", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeGrpDtlFrm udf    = (NmeGrpDtlFrm) af;
        int          lmt    = Integer.parseInt(util.nvl(uiForms.getREC(), "10"));
        String       reqIdn = util.nvl(udf.getGrpIdn());
        if (reqIdn.equals("")) {
        reqIdn = util.nvl((String)req.getParameter("idn"));
        }
        if (udf.getAddnew() != null) {
           

            for (int i = 1; i <= lmt; i++) {
                int    lIdn     = i;
                ArrayList params   = new ArrayList();
                String insIntoQ = " insert into nme_grp_dtl (nme_grp_dtl_idn, nme_grp_idn ";
                String insValQ  = "values(nme_grp_dtl_seq.nextval, ?";

                params.add(reqIdn);

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
        }

        if (udf.getModify() != null) {
        

            String bseFlds    = " nme_grp_dtl_idn, nme_grp_idn";
            String srchFields = helper.getSrchFields(daos, bseFlds, false);
            String getMenu    = "select " + srchFields + " from nme_grp_dtl where nme_grp_idn = ? ";
            ArrayList params     = new ArrayList();

            params.add(reqIdn);


            ArrayList outLst = db.execSqlLst("getUIFormFields for update", getMenu, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                int    lIdn  = rs.getInt(1);
                String updQ  = " update nme_grp_dtl set nme_grp_dtl_idn = nme_grp_dtl_idn ";
                String condQ = " where nme_grp_dtl_idn = ? ";

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

        req.setAttribute("errors", errors);
            util.updAccessLog(req,res,"Nme Grp Dtl", "save end");
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
            util.updAccessLog(req,res,"Nme Grp Dtl", "load start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeGrpDtlFrm udf    = (NmeGrpDtlFrm) af;

        udf.reset();

        ArrayList params = null;
        String reqIdn = util.nvl((String)udf.getGrpIdn());
        String nmeIdn = util.nvl(udf.getNmeIdn(), "0");
        String goTo   = "view";
            if(reqIdn.equals(""))
              reqIdn = util.nvl((String)req.getParameter("grpIdn"));
        /*
         *
         * if(!nmeIdn.equals("0")) {
         *   String getGrpIdn = " select nme_grp_idn from nme_grp where nme_idn = ? ";
         *   params = new ArrayList();
         *   params.add(nmeIdn);
         *   ResultSet rs = db.execSql(" get grp idn ", getGrpIdn, params);
         *   if(rs.next()) {
         *       reqIdn = rs.getString(1);
         *   } else
         *       reqIdn = "0";
         * }
         *
         * if(reqIdn.equals("0")) {
         *   goTo = "define";
         * } else {
         */
        String bseFlds    = " nme_grp_dtl_idn, nme_grp_idn";
        String srchFields = helper.getSrchFields(daos, bseFlds);
        String srchQ      = " and nme_grp_idn = ? ";

        params = new ArrayList();
        params.add(reqIdn);

        // ArrayList list = helper.getSrchList(uiForms, "nme_grp_dtl", srchFields, srchQ, params, daos, "SRCH");
        String    getMenu = "select " + srchFields + " from nme_grp_dtl where 1 = 1   " + srchQ + " order by flg desc,frm_dte";

            ArrayList outLst = db.execSqlLst(" load form list", getMenu, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            int lIdn = rs.getInt(1);

            for (int i = 0; i < daos.size(); i++) {
                UIFormsFields dao      = (UIFormsFields) daos.get(i);
                String        lFld     = dao.getFORM_FLD();
                String        tblFld   = dao.getTBL_FLD().toLowerCase();
                String        fldAlias = util.nvl(dao.getALIAS());
                String        aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);

                try {
                    udf.setValue(lFld + "_" + lIdn, util.nvl(rs.getString(tblFld)));

                    if (aliasFld.length() > 0) {
                        udf.setValue(lFld + "_" + lIdn + "_dsp", util.nvl(rs.getString(aliasFld)));
                    }
                } catch (Exception fe) {
                          }
            }
        }
        rs.close(); pst.close();
        // }
            udf.setGrpIdn(reqIdn);
            util.updAccessLog(req,res,"Nme Grp Dtl", "end");
        return am.findForward(goTo);
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
            util.updAccessLog(req,res,"Nme Grp Dtl", "delete start");
        NmeGrpDtlFrm udf = (NmeGrpDtlFrm) af;

        /*
         * HashMap formFields = info.getFormFields(formName);
         * if((formFields == null) || (formFields.size() == 0))
         *   formFields = util.getFormFields(formName);
         *
         * UIForms uiForms = (UIForms)formFields.get("DAOS");
         * ArrayList daos = uiForms.getFields();
         */
       // String reqIdn = udf.getNmeGrpIdn();
        String delId  = util.nvl((String)udf.getDelIdn());
        if(delId.equals("")){
            delId = util.nvl((String)req.getParameter("delId"));
        }
            
        String delQ   = " update nme_grp_dtl set vld_dte = sysdate, flg = 'DEL' where nme_grp_dtl_idn = ? ";
        ArrayList params = new ArrayList();

        params.add(delId);

        int ct = db.execUpd(" del req", delQ, params);
            util.updAccessLog(req,res,"Nme Grp Dtl", "delete end");
        return  load(am, af, req, res);
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
            util.updAccessLog(req,res,"Nme Grp Dtl", "active start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeGrpDtlFrm udf    = (NmeGrpDtlFrm) af;
        String reqIdn = util.nvl(udf.getNmeGrpIdn()); 
        String    delIdn    = util.nvl((String)req.getParameter("delId"));      
        ArrayList    params    = new ArrayList();        
        String updateQ = "update nme_grp_dtl set vld_dte = null, flg = null where nme_grp_dtl_idn=? ";
        params    = new ArrayList();
        params.add(delIdn);
        int   cnt = db.execUpd(" Active ", updateQ, params);
            util.updAccessLog(req,res,"Nme Grp Dtl", "active end");
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
            util.updAccessLog(req,res,"Nme Grp Dtl", "active start");
        NmeGrpDtlFrm udf = (NmeGrpDtlFrm) af;
        udf.reset();
            util.updAccessLog(req,res,"Nme Grp Dtl", "active end");
        return am.findForward("reset");
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
                util.updAccessLog(req,res,"Nme Grp Dtl", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Nme Grp Dtl", "init");
            }
            }
            return rtnPg;
         }
}


//~ Formatted by Jindent --- http://www.jindent.com
