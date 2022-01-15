package ft.com.pri;
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

import ft.com.masters.ModulePropertyAction;

import ft.com.pri.PriceGPDefForm;

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

public class PriceGPDefAction extends DispatchAction {
  

      public PriceGPDefAction() {
          super();
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
         PriceGPDefForm udf = (PriceGPDefForm) af;
            util.updAccessLog(req,res,"PriceGPDefAction", "load");
            HashMap  formFields = info.getFormFields("pricegpdefform");

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields("pricegpdefform");
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            ArrayList  errors  = new ArrayList();
            FormsUtil helper  = new FormsUtil();
            helper.init(db, util, info);
          ArrayList dsp=new ArrayList();
          ArrayList error = (ArrayList)req.getAttribute("errors");
          if(error!=null && error.size() >0){
             
          }else{
            udf.reset();
          }
          String nme = req.getParameter("nme");

          if (nme == null) {
              nme = (String) info.getValue("nme");
          }
          ArrayList params = new ArrayList();
          params.add(nme);
         

          info.setValue("nme",nme);

          String    srchFields = helper.getSrchFields(daos, "idn,nme");
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
                  String count = String.valueOf(i+1);
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
                      if(j==0){
                        String dsp_flg=util.nvl((String) gen.getValue("dsp_flg"));
                        if(dsp_flg.equals("D"))
                            dsp.add(String.valueOf(i+1));
                      }
                  }
                
                  
              }
          }

          req.setAttribute("pricegpdefform", list);
          util.SOP(" NmeSearch Count : " + list.size());
          //udf.setValue("nme", nme);
          req.setAttribute("dsp_flg", dsp);
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
         PriceGPDefForm udf = (PriceGPDefForm) af;
            util.updAccessLog(req,res,"PriceGPDefAction", "save");
            HashMap  formFields = info.getFormFields("pricegpdefform");

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields("pricegpdefform");
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            ArrayList  errors  = new ArrayList();
            FormsUtil helper  = new FormsUtil();
            helper.init(db, util, info);
          ArrayList errorList = new ArrayList();
          int lmt = Integer.parseInt(util.nvl(uiForms.getREC(), "5"));

          if (udf.getAddnew() != null) {
             

              for (int i = 1; i <= lmt; i++) {
                  int    lIdn     = i;
                  boolean isValid = false;
                  ArrayList params   = new ArrayList();
                  String insIntoQ = " insert into pri_grp_prp (idn,nme ";
                  String insValQ  = " values(PRI_GRP_PRP_SEQ.nextval, ? ";
                  params.add(info.getValue("nme"));
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
                      System.out.println(sql);
                      int    ct  = db.execUpd(" Ins Menu", sql, params);
                      
                  }
                  if(isValid){
                      errorList.add(errors);
                      errors = new ArrayList();
                  }
              }
          }

          if (udf.getModify() != null) {
          

              String    getMenu = "select " + helper.getSrchFields(daos, "idn,nme", false) + " from pri_grp_prp where nme=? order by srt ";
              ArrayList    params  = new ArrayList();
              params.add(info.getValue("nme"));
            ArrayList  rsList = db.execSqlLst("getUIFormFields for update", getMenu, params);
             PreparedStatement pst=(PreparedStatement)rsList.get(0);
             ResultSet  rs = (ResultSet)rsList.get(1);
              int count = 1;
              while (rs.next()) {
                  String    lIdn  = rs.getString(1);
                  String val = rs.getString(2);
                  String updQ  = " update pri_grp_prp set ";
                  String condQ = " where idn = ? " ;
                  boolean isValid = false;
                  String cnt = String.valueOf(count+1);
                  params = new ArrayList();

                  String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn));

                  if (isChecked.length() > 0) {
                   
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
                              if(j==0){
                                updQ += lTblFld + " = " + dfltParam;
                              }
                              else{
                               updQ += ", " + lTblFld + " = " + dfltParam;
                              }
                              params.add(lVal);
                              
                              if(lReqd.equalsIgnoreCase("Y"))
                              isValid = true;
                          }
                      }
                  }

                  if (params.size() > 0 && errors.size()==0) { 
                      params.add(lIdn);
                      System.out.println();
                    System.out.println(updQ);
                    System.out.println(condQ);
                      int ct = db.execUpd(" upd menu " + lIdn, updQ + condQ, params);
                  }
                  if(isValid){
                      errorList.add(errors);
                      errors = new ArrayList();
                  }
                  count++;
              }
              rs.close();
              pst.close();
          }
        

          req.setAttribute("errors", errorList);
//          util.initPrp();
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
                util.updAccessLog(req,res,"PriceGPPrpDefAction", "init");
                }
                }
                return rtnPg;
             }
      }

