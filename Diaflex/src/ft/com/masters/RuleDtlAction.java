package ft.com.masters;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;
import ft.com.generic.GenericImpl;
import ft.com.role.RoleForm;
import ft.com.role.UserRoleForm;

import java.io.IOException;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class RuleDtlAction  extends DispatchAction {
    private final String formName   = "rule_dtl";
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
            util.updAccessLog(req,res,"rule Dtl form", "load start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        RuleDtlForm    udf = (RuleDtlForm) af;
        ResultSet rs=null;
        String maxsrt=null;
        ArrayList error = (ArrayList)req.getAttribute("errors");
        if(error!=null && error.size() >0){
            ArrayList errSizeList = (ArrayList)error.get(0);
             if(errSizeList!=null && errSizeList.size() ==0){
             
                 udf.reset();
             }
        }else{
          udf.reset();
        }
    String ruleIdn = req.getParameter("mruleIdn");

    if (ruleIdn == null) {
        ruleIdn = (String)udf.getValue("ruleIdn");
    }
       if (ruleIdn == null) {
           ruleIdn = (String) info.getValue("ruleIdn");
       }
    ArrayList params = new ArrayList();

    params.add(ruleIdn);

    info.setValue("ruleIdn",ruleIdn);
    String    srchFields = helper.getSrchFields(daos, "rule_dtl_idn");
    String    srchQ      = " and rule_idn = ?  and til_dte is null ";
    ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ , params, daos, "VIEW");
       if (list.size() > 0) {
           int lmt    = list.size();
           int frmRec = Integer.parseInt(util.nvl(uiForms.getREC(), "0"));

           if (frmRec == 1) {
               lmt = 1;
           }

           for (int i = 0; i < lmt; i++) {
               GenDAO gen  = (GenDAO) list.get(i);
               String lIdn = gen.getIdn();
               String count = String.valueOf(i+1);
               //String srt = (String)gen.getValue("srt");
               // util.SOP(" i : "+i + " | Idn : "+ lIdn);
               // util.SOP(gen.toString());
               for (int j = 0; j < daos.size(); j++) {
                   UIFormsFields dao     = (UIFormsFields) daos.get(j);
                   String        lFld    = dao.getFORM_FLD();
                   String        lFrmFld = lFld;

                   if (frmRec > 1) {
                       lFrmFld = lFld + "_" + lIdn ;
                   }

                   String fldAlias = util.nvl(dao.getALIAS());
                   String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);

                   udf.setValue(lFrmFld, util.nvl((String) gen.getValue(lFld)));
                   udf.setValue(lFrmFld + "_dsp", util.nvl((String) gen.getValue(aliasFld)));
               }
           }
       }
       req.setAttribute(formName, list);
       udf.setValue("ruleIdn", ruleIdn);
       HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
       HashMap pageDtl=(HashMap)allPageDtl.get("RULE_FORM");
       if(pageDtl==null || pageDtl.size()==0){
       pageDtl=new HashMap();
       pageDtl=util.pagedef("RULE_FORM");
       allPageDtl.put("RULE_FORM",pageDtl);
       }
       util.updAccessLog(req,res,"Rule form", "load end");
   
   }
    return am.findForward("view");
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
            util.updAccessLog(req,res,"rule form", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        RuleDtlForm    udf = (RuleDtlForm) af;
        ArrayList errorList = new ArrayList();
        int lmt = Integer.parseInt(util.nvl(uiForms.getREC(), "5"));
        String ruleIdn = (String) udf.getValue("ruleIdn");
    if (udf.getAddnew() != null) {
        util.SOP(formName + " AddNew ");

        for (int i = 1; i <= lmt; i++) {
            int    lIdn     = i;
            boolean isValid = false;
            ArrayList params   = new ArrayList();
            String insIntoQ = " insert into rule_dtl ( rule_dtl_idn, rule_idn ";
            String insValQ  = "values(rule_dtl_seq.nextval ,?  ";

            params.add(ruleIdn);

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
                    
                    if(lReqd.equalsIgnoreCase("Y"))
                    isValid = true;
                }
            }


                if (params.size() > 0 && errors.size()==0) {
                    String sql = insIntoQ + ") " + insValQ + ")";
                    int    ct  = db.execDirUpd(" Ins Menu", sql, params);
                }
                
                if(isValid){
                    errorList.add(errors);
                    errors = new ArrayList();
                }
        }
    }

    if (udf.getModify() != null) {
        util.SOP(formName + " : Modify ");

        String srchFields = helper.getSrchFields(daos, "rule_dtl_idn", false);
        String srchQ      = " and rule_idn = ? ";

        // ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ, params, daos, "VIEW");
        String getMenu    = "select " + srchFields + " from rule_dtl where 1 = 1 " + srchQ + " order by 1";
        ArrayList params     = new ArrayList();

        params.add(ruleIdn);

        ResultSet rs = db.execSql("getUIFormFields for update", getMenu, params);

        while (rs.next()) {
            int    lIdn  = rs.getInt(1);
            String srt = (String)rs.getString("srt_fr");
            String updQ  = " update rule_dtl set rule_dtl_idn = rule_dtl_idn , ";
            String condQ = " where rule_dtl_idn = ? ";
            boolean isValid = false;
            params = new ArrayList();

            String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn));

            if (isChecked.length() > 0) {

                // params.add(Integer.toString(menuIdn));
                for (int j = 0; j < daos.size(); j++) {
                    UIFormsFields dao   = (UIFormsFields) daos.get(j);
                    String        lFld  = dao.getFORM_FLD();    // (String)flds.elementAt(j);
                    String        fldNm = lFld + "_" + lIdn;
                    String        lVal  = util.nvl((String) udf.getValue(fldNm));
                    String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));

                    String        lDsc = dao.getDSP_TTL();
                    // util.nvl((String)formFields.get(lFld+"REQ"));

                    if ((lReqd.equalsIgnoreCase("Y")) && ((lVal.length() == 0) || (lVal.equals("0")))) {
                        String errorMsg = lIdn + "-" + lDsc + " Required.";

                        errors.add(errorMsg);
                        params.clear();

                    } else  {
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
                            
                            if(j==0)
                                updQ += lTblFld + " = " + dfltParam;
                            else
                                updQ += ", " + lTblFld + " = " + dfltParam;
                            params.add(lVal);
                            
                            if(lReqd.equalsIgnoreCase("Y"))
                            isValid = true;
                        }
                }
            }

            if (params.size() > 0 && errors.size()==0) {
                params.add(Integer.toString(lIdn));
                int ct = db.execDirUpd(" upd menu " + lIdn, updQ + condQ, params);
            }
            
            if(isValid){
                errorList.add(errors);
                errors = new ArrayList();
            }
        }
        
    }
        if (udf.getDelete() != null) {
           util.SOP(formName + " : Delete ");

           String srchFields = helper.getSrchFields(daos, "rule_dtl_idn", false);
           String srchQ      = " and rule_idn = ? ";

           // ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ, params, daos, "VIEW");
           String getMenu    = "select " + srchFields + " from rule_dtl where 1 = 1 " + srchQ + " order by 1";
           ArrayList params     = new ArrayList();

           params.add(ruleIdn);

           ResultSet rs = db.execSql("getUIFormFields for update", getMenu, params);

           while (rs.next()) {
               int    lIdn  = rs.getInt(1);
               String srt = (String)rs.getString("srt_fr");
               String updQ  = " update rule_dtl set rule_dtl_idn = rule_dtl_idn , ";
               String condQ = " where rule_dtl_idn = ? ";
               boolean isValid = false;
               params = new ArrayList();

               String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn));

               if (isChecked.length() > 0) {

                   // params.add(Integer.toString(menuIdn));
                   for (int j = 0; j < daos.size(); j++) {
                       UIFormsFields dao   = (UIFormsFields) daos.get(j);
                       String        lFld  = dao.getFORM_FLD();    // (String)flds.elementAt(j);
                       String        fldNm = lFld + "_" + lIdn;
                       String        lVal  = util.nvl((String) udf.getValue(fldNm));
                       String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));

                       String        lDsc = dao.getDSP_TTL();
                       // util.nvl((String)formFields.get(lFld+"REQ"));

                       if ((lReqd.equalsIgnoreCase("Y")) && ((lVal.length() == 0) || (lVal.equals("0")))) {
                           String errorMsg = lIdn + "-" + lDsc + " Required.";

                           errors.add(errorMsg);
                           params.clear();

                       } else {
                            String dfltParam = " ? ";
                            String lTblFld = dao.getTBL_FLD().toLowerCase();    // util.nvl((String)formFields.get(lFld+"TF"));
                            if (lTblFld.equals("til_dte")) {                              
                                     dfltParam = " to_date(sysdate ,'dd-mm-rrrr')";
                                 }    
                            
                            if(j==0)
                                updQ += lTblFld + " = " + dfltParam;
                            else
                                updQ += ", " + lTblFld + " = " + dfltParam;
                        if (!lTblFld.equals("til_dte"))
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
                   errorList.add(errors);
                   errors = new ArrayList();
               }
           }
           rs.close();
       }
    req.setAttribute("errors", errorList);
    udf.setValue("ruleIdn", ruleIdn);
    return load(am, af, req, res);
   }
  }
    public RuleDtlAction() {
        super();
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
                rtnPg=util.checkUserPageRights("masters/mruleMst.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"MPrp form", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"MPrp form", "init");
            }
            }
            return rtnPg;
         }
}
