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

import ft.com.pri.PriceGPForm;

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
    
        public PriceGPAction() {
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
        util.updAccessLog(req,res,"PriceGPForm", "save");
        HashMap  formFields = info.getFormFields("pricegpform");

        if ((formFields == null) || (formFields.size() == 0)) {
            formFields = util.getFormFields("pricegpform");
        }

        UIForms  uiForms = (UIForms) formFields.get("DAOS");
        ArrayList  daos    = uiForms.getFields();
        ArrayList  errors  = new ArrayList();
        FormsUtil helper  = new FormsUtil();
        helper.init(db, util, info);
        
      PriceGPForm udf = (PriceGPForm) af;
      ArrayList errorList = new ArrayList();
      int lmt = Integer.parseInt(util.nvl(uiForms.getREC(), "5"));

      if (udf.getAddnew() != null) {
        

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
         

          String    getMenu = "select " + helper.getSrchFields(daos, "idn", false) + " from pri_grp  ";
          ArrayList    params  = new ArrayList();
        ArrayList  rsList = db.execSqlLst("getUIFormFields for update", getMenu, params);
         PreparedStatement pst=(PreparedStatement)rsList.get(0);
         ResultSet  rs = (ResultSet)rsList.get(1);
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
      if (udf.getCopy() != null) {
      String copyfrom=util.nvl((String)udf.getValue("copyfrom")); 
      String newgrpname=util.nvl((String)udf.getValue("grpname"));
      String newgrpdsc=util.nvl((String)udf.getValue("grpdsc"));
      String copysheet=util.nvl((String)udf.getValue("copysheetRd"));
      if(!newgrpname.equals("") && !copyfrom.equals("")){
      if(newgrpdsc.equals(""))
      newgrpdsc=newgrpname;  
      ArrayList params  = new ArrayList();
      ArrayList ary  = new ArrayList();
      String maxsrt="",copyidn="",copymprp="",newprpidn="";
      ResultSet rs=null;
      ResultSet rs1=null;
      String MaxSrt="select max(srt)+10 srt from pri_grp";
      rs = db.execSql("maxsrt",MaxSrt, params);
      if(rs.next()){
      maxsrt=util.nvl(rs.getString(1));
      }
      rs.close();
      String inserQprigrp="Insert Into pri_grp(idn,nme,srt,prmtyp,grp_yn,grp_seq,grp_nme,ref_sub_nme,stt,flg1,flg2,flg3,dsc)\n" + 
      "select PRI_GRP_SEQ.nextval,?,?,prmtyp,grp_yn,grp_seq,grp_nme,ref_sub_nme,stt,flg1,flg2,flg3,?\n" + 
      "from pri_grp where nme=?";
      params=new ArrayList();
      params.add(newgrpname);
      params.add(maxsrt);
      params.add(newgrpdsc);
      params.add(copyfrom);
      int ctpg  = db.execDirUpd(" Ins Menu", inserQprigrp, params);
      if(ctpg>0){
      String inserQprigrpprp= "Insert Into pri_grp_prp(idn,nme,mprp,srt,typ,seq,dsp_flg,dsp_all,stt,flg1,flg2,flg3)\n" + 
       "select PRI_GRP_PRP_SEQ.nextval,?,mprp,srt,typ,seq,dsp_flg,dsp_all,stt,flg1,flg2,flg3\n" + 
       "from pri_grp_prp where nme=?";
          params=new ArrayList();
          params.add(newgrpname);
          params.add(copyfrom);
      int ctpgp  = db.execDirUpd(" Ins Menu", inserQprigrpprp, params);
      if(ctpgp>0){
      String sqlfrom="select idn,nme,mprp,srt,typ,seq,dsp_flg,dsp_all,stt,flg1,flg2,flg3\n" + 
      "from pri_grp_prp where nme=?"; 
          params=new ArrayList();
          params.add(copyfrom);
      rs = db.execSql("pri_grp_prp", sqlfrom, params);
      while (rs.next()) { 
      copyidn=util.nvl(rs.getString("idn"));
      copymprp=util.nvl(rs.getString("mprp"));
          String sqlto="select idn from pri_grp_prp where nme=? and mprp=?";  
          ary  = new ArrayList();
          ary.add(newgrpname);
          ary.add(copymprp);
          rs1 = db.execSql("pri_grp_prp", sqlto, ary);  
          while (rs1.next()) { 
          newprpidn=util.nvl(rs1.getString("idn"));
          }
          rs1.close();
          String inserQprigrpprpdtl= "Insert Into pri_grp_prp_dtl(pri_grp_prp_idn,mprp,vfr,vto,nfr,nto,sfr,sto,dta_typ,flg,srt)\n" + 
          "select ?,mprp,vfr,vto,nfr,nto,sfr,sto,dta_typ,flg,srt\n" + 
          "from pri_grp_prp_dtl where pri_grp_prp_idn=?";
          ary=new ArrayList();
          ary.add(newprpidn);
          ary.add(copyidn);
          int ct  = db.execDirUpd(" Ins Menu", inserQprigrpprpdtl, ary);
      }
      rs.close();
      if(copysheet.equals("Y")){
          ary=new ArrayList();
          ary.add(copyfrom);
          ary.add(newgrpname);
          int calCt = db.execCall("COPY_SHEETS", "PRC_DATA_PKG.COPY_SHEETS(pGrpFrm => ?,pGrpTo => ?)", ary);
      }
      req.setAttribute("MSG", "Sucessfully Copyied in :"+newgrpname);
      }
      
      }
      }else{
          req.setAttribute("MSG", "Please verify");
      }
      }
      req.setAttribute("errors", errorList);
        util.updAccessLog(req,res,"PriceGPForm", "save end");
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
        util.updAccessLog(req,res,"PriceGPForm", "load");
        HashMap  formFields = info.getFormFields("pricegpform");

        if ((formFields == null) || (formFields.size() == 0)) {
            formFields = util.getFormFields("pricegpform");
        }
          ResultSet rs=null;
          String maxsrt=null;
        UIForms  uiForms = (UIForms) formFields.get("DAOS");
        ArrayList  daos    = uiForms.getFields();
        ArrayList  errors  = new ArrayList();
        FormsUtil helper  = new FormsUtil();
        helper.init(db, util, info);
        
      PriceGPForm udf = (PriceGPForm) af;
      ArrayList error = (ArrayList)req.getAttribute("errors");
      if(error!=null && error.size() >0){
          ArrayList errSizeList = (ArrayList)error.get(0);
           if(errSizeList!=null && errSizeList.size() ==0){
           
               udf.reset();
           }
      }else{
        udf.reset();
      }
        
          String flg2 = util.nvl(req.getParameter("flg2"),"ALL");

          if (flg2.equals("ALL")) {
              flg2 = util.nvl((String) info.getValue("flg2"),"ALL1");
          }
          ArrayList params = new ArrayList();
          
          

          info.setValue("flg2",flg2);
        
      ArrayList parmas     = new ArrayList();
      String srchFields = helper.getSrchFields(daos, "idn");
      String srchQ      = " ";
        if(!flg2.equals("ALL") && !flg2.equals("ALL1")){
         srchQ      = " and flg2 = ? ";
      params.add(flg2);
        }
      ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ, params, daos, "VIEW");

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
      req.setAttribute("pricegpform", list);

      // udf.setList(list);
      // info.setNmeSrchList(list);
      util.SOP(" NmeSearch Count : " + list.size());
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
      HashMap pageDtl=(HashMap)allPageDtl.get("PRICING_GROUP");
      if(pageDtl==null || pageDtl.size()==0){
      pageDtl=new HashMap();
      pageDtl=util.pagedef("PRICING_GROUP");
      allPageDtl.put("PRICING_GROUP",pageDtl);
      }
      info.setPageDetails(allPageDtl);
      udf.setValue("copysheetRd", "N");
        util.updAccessLog(req,res,"PriceGPForm", "load end");
        
          String MaxSrt="select max(srt) srt from pri_grp";
          rs = db.execSql("maxsrt",MaxSrt, new ArrayList());
          if(rs.next()){
            maxsrt=util.nvl(rs.getString(1));
          }
          rs.close();
          req.setAttribute("maxsrt", maxsrt);
      return am.findForward("view");
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
