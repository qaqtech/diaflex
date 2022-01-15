package ft.com.contact;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.*;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.*;
import ft.com.masters.CountryAction;
import ft.com.masters.CountryFrm;
import ft.com.masters.DayTermsFrm;

import ft.com.masters.DeptPrcForm;
import ft.com.masters.SubSizeDefForm;

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

public class NmeDtlAction extends DispatchAction {
    private final String formName = "nmedtl";

    public NmeDtlAction() {
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
            util.updAccessLog(req,res,"Nme Dtl", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeDtlFrm udf = (NmeDtlFrm) af;
        ArrayList errorList = new ArrayList();
        int lmt = Integer.parseInt(util.nvl(uiForms.getREC(), "5"));
        String    nmeIdn = udf.getNmeIdn();
        if (udf.getAddnew() != null) {
         

            for (int i = 1; i <= lmt; i++) {
                int    lIdn     = i;
                boolean isValid = false;
                ArrayList params   = new ArrayList();
                String insIntoQ = " insert into nme_dtl (nme_dtl_idn, nme_idn ";
                String insValQ  = "values(nme_dtl_seq.nextval, ?";

                params.add(nmeIdn);
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
                        int    ct  = db.execUpd(" Ins Menu", sql, params);
                    }
                    
                    if(isValid){
                        if(errors.size()>0)
                        errorList.add(errors);
                        errors = new ArrayList();
                    }
            }
        }

        if (udf.getModify() != null) {
           

            String    getMenu = "select " + helper.getSrchFields(daos, "nme_dtl_idn", false) + " from nme_dtl where nme_idn = ? and vld_dte is null ";
            ArrayList    params  = new ArrayList();
            params.add(nmeIdn);

            ArrayList outLst = db.execSqlLst("getUIFormFields for update", getMenu, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                int    lIdn  = rs.getInt(1);
                String updQ  = " update nme_dtl set nme_dtl_idn = nme_dtl_idn ";
                String condQ = " where nme_dtl_idn = ? ";
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
            rs.close(); pst.close();
        }
        if (udf.getDelete() != null) {
            String lIdnlst="";
            String    getMenu = "select " + helper.getSrchFields(daos, "nme_dtl_idn", false) + " from nme_dtl where nme_idn = ? and vld_dte is null ";
            ArrayList    params  = new ArrayList();
            params.add(nmeIdn);

            ArrayList outLst = db.execSqlLst("getUIFormFields for update", getMenu, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                int    lIdn  = rs.getInt(1);
                String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn));

                if (isChecked.length() > 0) {
                    lIdnlst=lIdnlst+","+lIdn;
                }
            }
            rs.close(); pst.close();
            if(!lIdnlst.equals("")){
                lIdnlst=lIdnlst.replaceFirst("\\,", "");
                int ct = db.execUpd(" del msg", "update nme_dtl set vld_dte=sysdate where nme_dtl_idn in("+lIdnlst+")", new ArrayList());
            }
        }
        req.setAttribute("errors", errorList);
            util.updAccessLog(req,res,"Nme Dtl", "save end");
            HashMap dbmsSysInfo = info.getDmbsInfoLst();
            String isSalForce =util.nvl((String)dbmsSysInfo.get("SLFORCE"));
            if(isSalForce.equals("YES")){
                try {
            SaleForce saleForce = new SaleForce();
            saleForce.init(db, util, info);
            saleForce.UpdateContact(req,nmeIdn);
                } catch (Exception e) {
                    // TODO: Add catch code
                    e.printStackTrace();
                }
            }
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
            util.updAccessLog(req,res,"Nme Dtl", "load start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeDtlFrm udf = (NmeDtlFrm) af;
        String    nmeIdn = req.getParameter("nmeIdn");
        if(nmeIdn==null)
          nmeIdn = udf.getNmeIdn();
        
        ArrayList error = (ArrayList)req.getAttribute("errors");
        if(error!=null && error.size() >0){
            ArrayList errSizeList = (ArrayList)error.get(0);
             if(errSizeList!=null && errSizeList.size() ==0){
             
                 udf.reset();
             }
        }else{
          udf.reset();
        }
        ArrayList parmas     = new ArrayList();
        String srchFields = helper.getSrchFields(daos, "nme_dtl_idn");
        String srchQ      = " and nme_idn = ? and nme_dtl.vld_dte is null ";
        parmas.add(nmeIdn);
        // helper.getSrchList(uiForms, frmTbl, srchFields, srchQ, params, daos, typ)
        ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ, parmas, daos, "VIEW");

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

        // udf = (CountryFrm)helper.getForm(uiForms, "", srchFields, srchQ, new ArrayList(), daos, "VIEW") ;
        req.setAttribute(formName, list);
        udf.setNmeIdn(nmeIdn);
        // udf.setList(list);
        // info.setNmeSrchList(list);
     
        util.updAccessLog(req,res,"Nme Dtl", "load end");
        return am.findForward("view");
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
          util.updAccessLog(req,res,"Nme Dtl", "delete start");
      NmeDtlFrm udf = (NmeDtlFrm) af;
    String Idn = req.getParameter("nme_dtl_idn");
    String deleteAddInfo = "update nme_dtl set vld_dte=sysdate where nme_dtl_idn= ?";
    ArrayList ary = new ArrayList();
    ary.add(Idn);
    int ct = db.execUpd("deleteContactinfo", deleteAddInfo, ary);
          util.updAccessLog(req,res,"Nme Dtl", "delete end");
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
                rtnPg=util.checkUserPageRights("contact/advcontact.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Nme Dtl", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Nme Dtl", "init");
            }
            }
            return rtnPg;
         }
}






