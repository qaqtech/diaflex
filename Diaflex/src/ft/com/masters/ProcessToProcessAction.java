package ft.com.masters;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;

import ft.com.generic.GenericImpl;

import java.io.IOException;

import java.sql.Connection;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessToProcessAction extends DispatchAction {
    private final String formName   = "prctoprc";


    public ProcessToProcessAction() {
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
            util.updAccessLog(req,res,"Prc To Prc", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            ArrayList errors  = new ArrayList();
        ProcessMasterFrm udf = (ProcessMasterFrm) af;
        ArrayList errorList = new ArrayList();
        int    lmt    = Integer.parseInt(util.nvl(uiForms.getREC(), "5"));
        String prcIdn = (String) udf.getValue("prcIdn");

        if (udf.getAddnew() != null) {
         

            for (int i = 1; i <= lmt; i++) {
                int    lIdn     = i;
                boolean isValid = false;
                ArrayList params   = new ArrayList();
                String insIntoQ = " insert into prc_to_prc ( prc_prc_id , prc_id ";
                String insValQ  = "values(seq_prc_to_prc.nextval ,?  ";

                params.add(prcIdn);

                for (int j = 0; j < daos.size(); j++) {
                    UIFormsFields dao   = (UIFormsFields) daos.get(j);
                    String        lFld  = dao.getFORM_FLD();
                    String        fldNm = lFld + "_" + lIdn;
                    String        lVal  = util.nvl((String) udf.getValue(fldNm));
                    String        lReqd = dao.getREQ_YN();
                    String        lDsc = dao.getDSP_TTL();
                    // util.nvl((String)formFields.get(lFld+"REQ"));

                    if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                        String errorMsg = lIdn + "-" + lDsc + ", Required.";

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
                        isValid =true;
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
      
            String    getMenu = "select " + getSrchFields(daos) + " from prc_to_prc where 1=1   ";
            ArrayList    params  = new ArrayList();
            ResultSet rs      = db.execSql("getUIFormFields for update", getMenu, params);

            while (rs.next()) {
                int    lIdn  = rs.getInt(1);
                String updQ  = " update prc_to_prc set prc_prc_id = prc_prc_id";
                String condQ = " where prc_prc_id = ? ";
                boolean isValid = false;
                params = new ArrayList();

                String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn));

                if (isChecked.length() > 0) {

                    // params.add(Integer.toString(menuIdn));
                    for (int j = 0; j < daos.size(); j++) {
                        UIFormsFields dao   = (UIFormsFields) daos.get(j);
                        String        lFld  = dao.getFORM_FLD();    // (String)flds.get(j);
                        String        fldNm = lFld + "_" + lIdn;
                        String        lVal  = util.nvl((String) udf.getValue(fldNm));
                        String        lReqd = dao.getREQ_YN();
                        String        lDsc = dao.getDSP_TTL();
                        // util.nvl((String)formFields.get(lFld+"REQ"));

                        if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                            String errorMsg = lIdn + "-" + lDsc + ", Required.";

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
                            isValid =true;
                        }
                    }
                }

                if (params.size() > 0 && errors.size()==0) { 
                    params.add(Integer.toString(lIdn));

                    int ct = db.execUpd(" upd menu " + lIdn, updQ + condQ, params);
                }
                if(isValid){
                    errorList.add(errors);
                    errors = new ArrayList();
                }
            }
            rs.close();
        }

        req.setAttribute("errors", errorList);
            util.updAccessLog(req,res,"Prc To Prc", "save end");
        return load(am, af, req, res);
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
                rtnPg=util.checkUserPageRights("masters/processMstAction.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Prc To Prc", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Prc To Prc", "int");
            }
            }
            return rtnPg;
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
            util.updAccessLog(req,res,"Prc To Prc", "load start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            ArrayList errors  = new ArrayList();
        ProcessMasterFrm udf = (ProcessMasterFrm) af;
        ArrayList error = (ArrayList)req.getAttribute("errors");
        if(error!=null && error.size() >0){
            ArrayList errSizeList = (ArrayList)error.get(0);
             if(errSizeList!=null && errSizeList.size() ==0){
             
                 udf.reset();
             }
        }else{
          udf.reset();
        }
        String prcIdn = req.getParameter("pIdn");

        if (prcIdn == null) {
            prcIdn = (String) udf.getValue("prcIdn");
        }
        if (prcIdn == null) {
            prcIdn = (String)info.getValue("prcIdn");
        }
        ArrayList parmas = new ArrayList();

        parmas.add(prcIdn);
        
        info.setValue("prcIdn",prcIdn);
        
        ResultSet rs = db.execSql("procceNme", " select prc from mprc where idn =? ", parmas);

        if(rs.next()){
        info.setValue("PRC", util.nvl(rs.getString("prc")));
        }
            rs.close();
        String    srchFields = getSrchFields(daos);
        String    srchQ      = " ";
        ArrayList list       = getSrchList(srchFields, srchQ, parmas, daos, "VIEW",uiForms,req,udf);

        req.setAttribute("processList", list);

        // udf.setList(list);
        // info.setNmeSrchList(list);
       
        udf.setValue("prcIdn", prcIdn);
        rs.close();
            util.updAccessLog(req,res,"Prc To Prc", "load end");
        return am.findForward("view");
        }
    }

    public ArrayList getSrchList(String srchFields, String srchQ, ArrayList params, ArrayList daos, String typ,UIForms  uiForms,HttpServletRequest req,ProcessMasterFrm udf) {
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
        ArrayList list   = new ArrayList();
        String    frmTbl = "";
        try {
            if (frmTbl.length() == 0) {
                frmTbl = "prc_to_prc";

                if (util.nvl(uiForms.getFRM_TBL()).length() > 0) {
                    frmTbl = uiForms.getFRM_TBL();
                }
            }

            String whereCl = "";

            if (util.nvl(uiForms.getWHERE_CL()).length() > 0) {
                whereCl = " and " + uiForms.getWHERE_CL();
            }

            String getDataQ = " select " + srchFields + " from " + frmTbl + " where 1 =1 " + whereCl + srchQ
                              + " order by 2 ";
            ResultSet rs = db.execSql(" get Search data", getDataQ, params);

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

                list.add(Integer.toString(lIdn));
            }
            rs.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }

        return list;
    }


    public String getSrchFields(ArrayList daos) {
        String srchFields;
        JspUtil util=new JspUtil();
        srchFields = "";
        ArrayList ukFld      = new ArrayList();

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

            // nmerel_idn, nme_idn, dflt_yn dyn
            if (j == 0) {
                srchFields = " " + tblNme + ".prc_prc_id ";
            }

            srchFields += delim + tblNme + "." + lTblFld;

            if (util.nvl(dao.getALIAS()).length() > 0) {
                srchFields += delim + dao.getALIAS();
            }
        }

        return srchFields;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
