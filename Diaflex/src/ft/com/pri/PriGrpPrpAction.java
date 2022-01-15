package ft.com.pri;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;

import ft.com.dao.UIFormsFields;
import ft.com.masters.CountryFrm;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PriGrpPrpAction extends DispatchAction {
   
    public PriGrpPrpAction() {
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
          util.updAccessLog(req,res,"PriceGPPRPAction", "init");
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
      
          util.updAccessLog(req,res,"PriGrpPrpAction", "load");
          HashMap  formFields = info.getFormFields("priGrpPrpForm");

          if ((formFields == null) || (formFields.size() == 0)) {
              formFields = util.getFormFields("priGrpPrpForm");
          }

          UIForms  uiForms = (UIForms) formFields.get("DAOS");
          ArrayList  daos    = uiForms.getFields();
          ArrayList  errors  = new ArrayList();
          FormsUtil helper  = new FormsUtil();
          helper.init(db, util, info);
        PriGrpForm udf = (PriGrpForm) af;
        ArrayList error = (ArrayList)req.getAttribute("errors");
        if(error!=null && error.size() >0){
            ArrayList errSizeList = (ArrayList)error.get(0);
             if(errSizeList!=null && errSizeList.size() ==0){
             
                 udf.reset();
             }
        }else{
          udf.reset();
        }
        String grpIdn = req.getParameter("grpIdn");

        if (grpIdn == null) {
            grpIdn = (String)udf.getValue("grpIdn");
        }
        if (grpIdn == null) {
            grpIdn = (String)info.getValue("grpIdn");
        }
        ArrayList params = new ArrayList();

        params.add(grpIdn);

        String keyQ = "select nme from pri_grp where idn =? ";

        info.setValue("nme", util.getPageKeyNme("city:country", keyQ, params));
        info.setValue("grpIdn", grpIdn);
        params = new ArrayList();
        params.add(info.getValue("nme"));
        String    srchFields = helper.getSrchFields(daos, "idn");
        String    srchQ      = " and nme = ? ";
        ArrayList list       = helper.getSrchList(uiForms, "", srchFields, srchQ, params, daos, "VIEW");
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

                    if (frmRec > 1) {
                        lFrmFld = lFld + "_" + lIdn;
                    }

                    String fldAlias = util.nvl(dao.getALIAS());
                    String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);

                    udf.setValue(lFrmFld, util.nvl((String) gen.getValue(lFld)));
                    udf.setValue(lFrmFld + "_dsp", util.nvl((String) gen.getValue(aliasFld)));
                }
            }
        }
        req.setAttribute("priGrpPrpForm", list);
       
        udf.setValue("grpIdn", grpIdn);

        return am.findForward("view");
        }
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
      
          util.updAccessLog(req,res,"PriGrpPrpAction", "save");
          HashMap  formFields = info.getFormFields("priGrpPrpForm");

          if ((formFields == null) || (formFields.size() == 0)) {
              formFields = util.getFormFields("priGrpPrpForm");
          }

          UIForms  uiForms = (UIForms) formFields.get("DAOS");
          ArrayList  daos    = uiForms.getFields();
          ArrayList  errors  = new ArrayList();
          FormsUtil helper  = new FormsUtil();
          helper.init(db, util, info);
         PriGrpForm udf = (PriGrpForm) af;
        ArrayList errorList = new ArrayList();
        int    lmt        = Integer.parseInt(util.nvl(uiForms.getREC(), "5"));
        String nme = (String)info.getValue("nme");

        if (udf.getAddnew() != null) {
            

            for (int i = 1; i <= lmt; i++) {
                int    lIdn     = i;
                boolean isValid = false;
                ArrayList params   = new ArrayList();
                String insIntoQ = " insert into pri_grp_prp ( idn, nme ";
                String insValQ  = "values(pri_grp_prp_seq.nextval ,?  ";

                params.add(nme);

                for (int j = 0; j < daos.size(); j++) {
                    UIFormsFields dao   = (UIFormsFields) daos.get(j);
                    String        lFld  = dao.getFORM_FLD();
                    String        fldNm = lFld + "_" + lIdn;
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
                        int    ct  = db.execDirUpd(" Ins Menu", sql, params);
                    }
                    
                    if(isValid){
                        errorList.add(errors);
                        errors = new ArrayList();
                    }
            }
        }

        if (udf.getModify() != null) {
            

            String srchFields = helper.getSrchFields(daos, "idn", false);
            String srchQ      = " and nme = ? ";

            // ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ, params, daos, "VIEW");
            String getMenu    = "select " + srchFields + " from pri_grp_prp where 1 = 1 " + srchQ + " order by 1";
            ArrayList params     = new ArrayList();

            params.add(nme);

            ResultSet rs = db.execSql("getUIFormFields for update", getMenu, params);

            while (rs.next()) {
                int    lIdn  = rs.getInt(1);
                String updQ  = " update pri_grp_prp set idn = idn ";
                String condQ = " where idn = ? ";
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
                        String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));

                        String        lDsc = dao.getDSP_TTL();
                        // util.nvl((String)formFields.get(lFld+"REQ"));

                        if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                            String errorMsg = lIdn + "-" + lDsc + " Required.";

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

                    int ct = db.execDirUpd(" upd menu " + lIdn, updQ + condQ, params);
                }
                
                if(isValid){
                    errorList.add(errors);
                    errors = new ArrayList();
                }
            }
            rs.close();
        }

        req.setAttribute("errors", errorList);

        return load(am, af, req, res);
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
      
          util.updAccessLog(req,res,"PriGrpPrpAction", "delete");
        
        String grpIdn = req.getParameter("grpIdn");
        String deleteCity = "update pri_grp_prp set vld_dte=sysdate where idn= ?";
        ArrayList ary = new ArrayList();
        ary.add(grpIdn);
        int ct = db.execUpd("deleteCity", deleteCity, ary);
        return load(am, af, req, res);
        
    }
    }
}
