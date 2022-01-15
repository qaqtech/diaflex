package ft.com.pricing;
//~--- non-JDK imports --------------------------------------------------------
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.contact.NmeGrpDtlFrm;
import ft.com.contact.NmeRelFrm;
import ft.com.dao.GenDAO;
import ft.com.dao.MprcDao;
import ft.com.dao.NmeRel;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;

import ft.com.pricing.PriceGPForm;

import java.io.IOException;

import java.sql.PreparedStatement;

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
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PriceGPAction extends DispatchAction {
    ArrayList            daos       = null;
    ArrayList            errors     = null;
    HashMap            formFields = null;
    private final String formName   = "pricegpform";
    FormsUtil            helper     = null;
    UIForms              uiForms    = null;
    DBMgr                db;
    InfoMgr              info;
    HttpSession          session;
    PriceGPForm           udf;
    ArrayList               ukFld;
    DBUtil               util;

        public PriceGPAction() {
        super();
    }

  public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
          throws Exception {
      String rtnPg=init(req,res);
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
      udf = (PriceGPForm) af;
      ArrayList errorList = new ArrayList();
      int lmt = Integer.parseInt(util.nvl(uiForms.getREC(), "5"));

      if (udf.getAddnew() != null) {
          util.SOP(formName + " AddNew ");

          for (int i = 1; i <= lmt; i++) {
              int    lIdn     = i;
              boolean isValid = false;
              ArrayList params   = new ArrayList();
              String insIntoQ = " insert into pri_grp ( idn ";
              String insValQ  = "values(PRI_GRP_SEQ.nextval ";

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
                      System.out.println(sql);
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

          String    getMenu = "select " + helper.getSrchFields(daos, "nme", false) + " from pri_grp  ";
          ArrayList    params  = new ArrayList();
        ArrayList outLst = db.execSqlLst("getUIFormFields for update", getMenu, params);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
          while (rs.next()) {
              String    lIdn  = rs.getString(1);
              String updQ  = " update pri_grp set idn = idn ";
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
                    String iquery="select idn from pri_grp where nme='"+lIdn+"'";
                    ArrayList outLst1 = db.execSqlLst("iquery",iquery,  new ArrayList());
                    PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
                    ResultSet rs1 = (ResultSet)outLst1.get(1);
                    if(rs1.next()){
                      lIdn=String.valueOf(rs1.getInt(1));
                    }
                      rs1.close();
                      pst1.close();
                      params.add(lIdn);

                      int ct = db.execDirUpd(" upd menu " + lIdn, updQ + condQ, params);
                  }
                  
                  if(isValid){
                      errorList.add(errors);
                      errors = new ArrayList();
                  } 
          }
          rs.close();
          pst.close();
      }

      req.setAttribute("errors", errorList);

      return load(am, af, req, res);
      }
  }

  public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
          throws Exception {
      String rtnPg=init(req,res);
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
      udf = (PriceGPForm) af;
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
      String srchFields = helper.getSrchFields(daos, "nme");
      String srchQ      = " ";

      // helper.getSrchList(uiForms, frmTbl, srchFields, srchQ, params, daos, typ)
      ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ, new ArrayList(), daos, "VIEW");

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

      // udf = (PriceGPForm)helper.getForm(uiForms, "", srchFields, srchQ, new ArrayList(), daos, "VIEW") ;
      req.setAttribute(formName, list);

      // udf.setList(list);
      // info.setNmeSrchList(list);
      util.SOP(" NmeSearch Count : " + list.size());

      return am.findForward("view");
      }
  }
  
      public String init(HttpServletRequest req , HttpServletResponse res) {
           
          String rtnPg="sucess";
          String invalide="";
          session = req.getSession(false);
          db = (DBMgr)session.getAttribute("db");
          info = (InfoMgr)session.getAttribute("info");
          util = ((DBUtil)session.getAttribute("util") == null)?new DBUtil():(DBUtil)session.getAttribute("util");
              String connExists=util.nvl(util.getConnExists());  
              if(!connExists.equals("N"))
              invalide=util.nvl(util.chkTimeOut(),"N");
              if(session.isNew() || db==null)
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
                  }
              }
          if(rtnPg.equals("sucess")){
              formFields = info.getFormFields(formName);

              if ((formFields == null) || (formFields.size() == 0)) {
                  formFields = util.getFormFields(formName);
              }

              uiForms = (UIForms) formFields.get("DAOS");
              daos    = uiForms.getFields();
              errors  = new ArrayList();
              helper  = new FormsUtil();
              helper.init(db, util, info);
          }
          return rtnPg;
          }
  }
