package ft.com.contact;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import java.sql.SQLException;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

import ft.com.dao.*;
import ft.com.*;

import ft.com.generic.GenericImpl;
import ft.com.marketing.SearchQuery;

import ft.com.masters.ProcessMasterFrm;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

import java.util.logging.Level;

import org.apache.struts.action.Action;

public class AddressAction extends DispatchAction {
    
    private final String formName = "address";
  
    
    public ActionForward save(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            HashMap dbmsSysInfo = info.getDmbsInfoLst();
            util.updAccessLog(req,res,"Address", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        AddressFrm udf = (AddressFrm)af;
        
        String lNmeIdn = util.nvl(udf.getNmeIdn(),"0");
        String lAddrIdn = util.nvl(udf.getIdn(),"0");
        
        String srch = util.nvl((String)udf.getSearch());
        String modify = util.nvl((String)udf.getModify());
        String addnew = util.nvl((String)udf.getAddnew());
        String view = util.nvl((String)udf.getView());
        String reset = util.nvl(udf.getReset());
       
        if(srch.length() > 0) {
            return search(am, af, req, res);
        } else if(view.length() > 0) {
            return load(am, af, req, res);
        }else if(reset.length() > 0) {
            return reset(am, af, req, res);
        }else {
            if(addnew.length() > 0) {
                ArrayList params = new ArrayList();
                String insIntoQ = " insert into maddr (addr_idn, nme_idn ";
                String insValQ = "values(addr_seq.nextval, ?";
                params.add(lNmeIdn);
                for(int j=0; j < daos.size(); j++) {
                    UIFormsFields dao = (UIFormsFields)daos.get(j);
                    String lFld = dao.getFORM_FLD();
                    String fldNm = lFld ;
                    String lVal = (String)udf.getValue(fldNm);
                    String lReqd = dao.getREQ_YN();//util.nvl((String)formFields.get(lFld+"REQ"));
                    String lDsc = dao.getDSP_TTL();
                    if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                            String errorMsg = lDsc + " is  Required";

                            errors.add(errorMsg);
                            params.clear();

                         
                    }    
                    else {
                        String lTblFld = dao.getTBL_FLD().toLowerCase();//util.nvl((String)formFields.get(lFld+"TF"));
                        insIntoQ += ", " + lTblFld;
                        if((dao.getFLD_TYP().equals("DT")) && (lVal.length() == 10))
                            insValQ += ", to_date(?, 'dd-mm-rrrr')";
                        else
                            insValQ  += ", ?";
                        params.add(lVal);
                    }       
                }
                if(params.size() > 0 && errors.size()==0) { 
                    String sql = insIntoQ + ") " + insValQ + ")";
                    int ct = db.execUpd(" Ins Menu", sql, params);
                }
            } else {
               
                String srchFields = getSrchFields(daos);
                String frmTbl = "maddr";
                if(util.nvl(uiForms.getFRM_TBL()).length() > 0)
                  frmTbl = uiForms.getFRM_TBL();
          
                String whereCl = "" ;
                if(util.nvl(uiForms.getWHERE_CL()).length() > 0)
                  whereCl = " and " + uiForms.getWHERE_CL();
          
                String srchQ = " and nme_idn = ? and addr_idn = ?";
                String getDataQ =
                  " select " + srchFields + " from "+ frmTbl + " where 1 =1 " + whereCl + 
                  srchQ + " order by 1 ";
          
                //String getMenu = "select "+ srchFields + " from maddr where nme_idn = ? and addr_idn = ?";
                
                ArrayList params = new ArrayList();
                params.add(lNmeIdn);
                params.add(lAddrIdn);
            ArrayList outLst = db.execSqlLst(formName + " for update", getDataQ, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
                while(rs.next()) {
                    int lIdn = rs.getInt(1);
                
                String updQ = " update maddr set nme_idn = nme_idn " ;
                String condQ = " where addr_idn = ? ";
                params = new ArrayList();
               
                    //params.add(Integer.toString(menuIdn));
                    for(int j=0; j < daos.size(); j++) {
                        UIFormsFields dao = (UIFormsFields)daos.get(j);
                        
                        String lFld = dao.getFORM_FLD();//(String)flds.get(j);
                        String fldNm = lFld ;
                        String lVal = (String)udf.getValue(fldNm);
                        String lReqd = dao.getREQ_YN();//util.nvl((String)formFields.get(lFld+"REQ"));
                        String lDsc = dao.getDSP_TTL();
                        if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                                    String errorMsg = lDsc + " is  Required";

                                    errors.add(errorMsg);
                                    params.clear();

                        }    
                        else {
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
                
                if(params.size() > 0 && errors.size()==0) { 
                    params.add(Integer.toString(lIdn));
                  
                   
                    
                    int ct = db.execUpd(" upd menu "+ lIdn, updQ + condQ, params);
                }
            }
            rs.close();
            pst.close();
        }
        }
       
        req.setAttribute("errors", errors);
        if(errors.size()>0){
            String srchFields = helper.getSrchFields(daos, "addr_idn, nme_idn, dflt_yn dyn");
            String srchQ = " and nme_idn = ? and maddr.vld_dte is null ";
            ArrayList params = new ArrayList();
            params.add(lNmeIdn);
            ArrayList list = null;
            list = helper.getSrchList(uiForms, "maddr", srchFields, srchQ, params, daos, "SRCH");
            req.setAttribute("addrList", list);
            return am.findForward("load");
        }
            util.updAccessLog(req,res,"Address", "save end");
            String isSalForce =util.nvl((String)dbmsSysInfo.get("SLFORCE"));
            if(isSalForce.equals("YES")){
                try {
                   
                        SaleForce saleForce = new SaleForce();
                        saleForce.init(db, util, info);
                        saleForce.UpdateContact(req,lNmeIdn);
                   
                } catch (Exception e) {
                    // TODO: Add catch code
                    e.printStackTrace();
                }}
      return search(am, af, req, res);
        }
    }
    
    public ActionForward delete(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Address", "delete start");
        AddressFrm udf = (AddressFrm)af;
        String delNmeIdn = req.getParameter("nmeIdn");
        String delIdn = req.getParameter("addrIdn");
        
        String delQ = " update maddr set vld_dte = sysdate where nme_idn = ? and addr_idn = ?";
        ArrayList params = new ArrayList();
        params.add(delNmeIdn);
        params.add(delIdn);
        int cnt = db.execUpd(" Del "+ delIdn, delQ, params);
            util.updAccessLog(req,res,"Address", "delete end");
        return load(am, af, req, res);
        }
    }
    
    public ActionForward search(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Address", "search start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        AddressFrm udf = (AddressFrm)af;
        String lNmeIdn = udf.getNmeIdn();
        /*
        String getDfltAddrIdn = " select addr_idn from maddr where nme_idn = ? and dflt_yn = 'Y'";
        ArrayList params = new ArrayList();
        params.add(nmeIdn);
        ResultSet rs = db.execSql("@srch dflt addr", getDfltAddrIdn, params);
        if(rs.next()) {
          udf.setIdn(rs.getString(1));
        }
        String srchQ = "", srchFields = "" ;
        params = new ArrayList();  
        srchQ = " and nme_idn = ? ";
        params.add(nmeIdn);
        String tblNmes = "" ;
        for(int i=0; i < tbls.size(); i++) {
            String delim = "" ;
            if(i > 0)
                delim = ", ";
            tblNmes += delim + (String)tbls.get(i);
        }
        
        srchFields = getSrchFields(daos);
        ArrayList list = getSrchList(srchFields, srchQ, params, daos, "SRCH") ;
        */
        String srchFields = helper.getSrchFields(daos, "addr_idn, nme_idn, dflt_yn dyn");
        String srchQ = " and nme_idn = ? and maddr.vld_dte is null ";
        ArrayList params = new ArrayList();
        params.add(lNmeIdn);
        //params.add(lAddrIdn);
        ArrayList list = null;
        //list = getSrchList(srchFields, srchQ, params, daos, "VIEW") ;
        list = helper.getSrchList(uiForms, "maddr", srchFields, srchQ, params, daos, "SRCH");
        
        if(list.size() > 0) {
          GenDAO gen = (GenDAO)list.get(0);
          for(int i=0; i < daos.size(); i++){
            udf.setIdn(gen.getIdn());
            UIFormsFields dao = (UIFormsFields)daos.get(i);
            String lFld = dao.getFORM_FLD();
            String fldAlias = util.nvl(dao.getALIAS());
            String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);
            
            udf.setValue(lFld, util.nvl((String)gen.getValue(lFld))); 
            udf.setValue(lFld+"_dsp", util.nvl((String)gen.getValue(aliasFld))); 
          }
        }  
        //udf.setList(list);
        req.setAttribute("addrList", list);
            util.updAccessLog(req,res,"Address", "search end");
        if(util.nvl(udf.getIdn(),"0") == "0") 
          return am.findForward("searchlst");
        else
          return load(am, af, req, res);
            //return am.findForward("view");
        }
    }
    public ActionForward reset(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Address", "reset start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        AddressFrm udf = (AddressFrm)af;
        ArrayList params     = new ArrayList();
        String lNmeIdn    = util.nvl(udf.getNmeIdn());
        if(lNmeIdn.equals(""))
            lNmeIdn = util.nvl(req.getParameter("nmeIdn"));
        udf.reset();
        udf.setNmeIdn(lNmeIdn);
        
        String srchFields = helper.getSrchFields(daos, "addr_idn, nme_idn, dflt_yn dyn");
        String srchQ = " and nme_idn = ? and maddr.vld_dte is null ";
        params.add(lNmeIdn);
        ArrayList list = null;
        //list = getSrchList(srchFields, srchQ, params, daos, "VIEW") ;
        list = helper.getSrchList(uiForms, "maddr", srchFields, srchQ, params, daos, "SRCH");
        
        req.setAttribute("addrList", list);
        req.setAttribute("method","reset");
            util.updAccessLog(req,res,"Address", "reset end");
        return am.findForward("load");
        }
    }
    public ActionForward load(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Address", "load start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        AddressFrm udf = (AddressFrm)af;
        
        ArrayList params     = new ArrayList();
        String lNmeIdn    = util.nvl(udf.getNmeIdn());
        if(lNmeIdn.equals(""))
            lNmeIdn = util.nvl(req.getParameter("nmeIdn"));
        
        
        
       
        String srchFields = helper.getSrchFields(daos, "addr_idn, nme_idn, dflt_yn dyn");
        String srchQ = " and nme_idn = ? and maddr.vld_dte is null ";
        params.add(lNmeIdn);
       

        ArrayList list = null;
        //list = getSrchList(srchFields, srchQ, params, daos, "VIEW") ;
        list = helper.getSrchList(uiForms, "maddr", srchFields, srchQ, params, daos, "SRCH");
        
        
        if(list.size() > 0) {
          GenDAO gen = (GenDAO)list.get(0);
          for(int i=0; i < daos.size(); i++){
            udf.setIdn(gen.getIdn());
            UIFormsFields dao = (UIFormsFields)daos.get(i);
            String lFld = dao.getFORM_FLD();
            String fldAlias = util.nvl(dao.getALIAS());
            String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);
            
            udf.setValue(lFld, util.nvl((String)gen.getValue(lFld))); 
            udf.setValue(lFld+"_dsp", util.nvl((String)gen.getValue(aliasFld))); 
          }
        }  
        
        
        //udf.setList(list);
        //info.setNmeSrchList(list);
        req.setAttribute("addrList", list);
        udf.setNmeIdn(lNmeIdn);
            util.updAccessLog(req,res,"Address", "load end");
        return am.findForward("load");
        }
    }
    
    
    public ActionForward edit(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Address", "edit start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        AddressFrm udf = (AddressFrm)af;
        
        ArrayList params     = new ArrayList();
        String lNmeIdn    = util.nvl(udf.getNmeIdn());
        if(lNmeIdn.equals(""))
            lNmeIdn = util.nvl(req.getParameter("nmeIdn"));
        
        String addrIdn = util.nvl(req.getParameter("addrIdn"));
        
       
        String srchFields = helper.getSrchFields(daos, "addr_idn, nme_idn, dflt_yn dyn");
        String srchQ = " and nme_idn = ? and maddr.vld_dte is null ";
        params.add(lNmeIdn);
        if(!addrIdn.equals("")){
         srchQ = srchQ + " and addr_idn =?" ;
         params.add(addrIdn);
        }
      

        ArrayList list = null;
        //list = getSrchList(srchFields, srchQ, params, daos, "VIEW") ;
        list = helper.getSrchList(uiForms, "maddr", srchFields, srchQ, params, daos, "SRCH");
        
        
        if(list.size() > 0) {
          GenDAO gen = (GenDAO)list.get(0);
          for(int i=0; i < daos.size(); i++){
            udf.setIdn(gen.getIdn());
            UIFormsFields dao = (UIFormsFields)daos.get(i);
            String lFld = dao.getFORM_FLD();
            String fldAlias = util.nvl(dao.getALIAS());
            String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);
            
            udf.setValue(lFld, util.nvl((String)gen.getValue(lFld))); 
            udf.setValue(lFld+"_dsp", util.nvl((String)gen.getValue(aliasFld))); 
          }
        }  
        
        srchFields = helper.getSrchFields(daos, "addr_idn, nme_idn, dflt_yn dyn");
        srchQ = " and nme_idn = ? and maddr.vld_dte is null ";
        params     = new ArrayList();
        params.add(lNmeIdn);
        list = helper.getSrchList(uiForms, "maddr", srchFields, srchQ, params, daos, "SRCH");
        //udf.setList(list);
        //info.setNmeSrchList(list);
        req.setAttribute("addrList", list);
        udf.setNmeIdn(lNmeIdn);
        udf.setIdn(addrIdn);
        req.setAttribute("method", "edit");
            util.updAccessLog(req,res,"Address", "edit end");
        return am.findForward("load");
        }
    }
    public ArrayList getSrchList(String srchFields, String srchQ, ArrayList params, ArrayList daos, String typ,UIForms  uiForms,HttpServletRequest req,AddressFrm udf) {
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
            String frmTbl = "maddr";
            if(util.nvl(uiForms.getFRM_TBL()).length() > 0)
                frmTbl = uiForms.getFRM_TBL();
            
            String whereCl = "" ;
            if(util.nvl(uiForms.getWHERE_CL()).length() > 0)
                whereCl = " and " + uiForms.getWHERE_CL();
            
            String getDataQ =
                " select " + srchFields + " from "+ frmTbl + " where 1 =1 " + whereCl + 
                srchQ + " order by 1 ";
            ArrayList outLst = db.execSqlLst(" get Search data", getDataQ, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                MAddr srchDao = new MAddr();
                String addrIdn = rs.getString(1);
                String nmeIdn = rs.getString(2);
                String dflt_yn = rs.getString("dyn");
                if(dflt_yn.equals("Y")) {
                    if(util.nvl(udf.getIdn(),"0") == "0") {
                        //udf.setIdn(addrIdn);
                        info.setValue("addridn", addrIdn);
                    }    
                }
                srchDao.setIdn(addrIdn);
                srchDao.setNmeIdn(nmeIdn);
                //nme.setValue("idn", nmeIdn);
                for (int i = 0; i < daos.size(); i++) {
                    UIFormsFields dao = (UIFormsFields)daos.get(i);
                    String tblFld = dao.getTBL_FLD().toLowerCase();
                    String lFld = dao.getFORM_FLD();
                    String dbVal = util.nvl(rs.getString(tblFld));
                    String fldAlias = util.nvl(dao.getALIAS());
                    String aliasFld = "", aliasVal = "" ;
                    try {
                        if (fldAlias.length() > 0) {
                            aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);
                            aliasVal = util.nvl(rs.getString(aliasFld));
                            
                        }
                    } catch (SQLException sqle) {
                        // TODO: Add catch code
                        //sqle.printStackTrace();
                     
                    }
                    if((typ.equalsIgnoreCase("VIEW")) || (dflt_yn.equals("Y"))) {
                        udf.setValue(lFld, dbVal);
                    
                        if(aliasFld.length() > 0) {
                            udf.setValue(aliasFld, aliasVal);
                            
                        }
                    }    
                    srchDao.setValue(lFld, dbVal);
                    if(aliasFld.length() > 0) 
                        srchDao.setValue(aliasFld, aliasVal);
                }
                list.add(srchDao);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }return list ;
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
                util.updAccessLog(req,res,"Address", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Address", "init");
            }
            }
            return rtnPg;
         }
    public String getSrchFields(ArrayList daos) {
        String srchFields;
        srchFields = "";
        JspUtil util=new JspUtil();
        ArrayList ukFld = new ArrayList();
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lTblFld = dao.getTBL_FLD().toLowerCase();
            String tblNme = dao.getTBL_NME();
            if(util.nvl(dao.getFLG()).equals("UK"))
                ukFld.add(lTblFld);
            String delim = ", ";
            /*if (j==0) {
                delim = "";
            } */   
            if(j == 0) 
                srchFields = " " + tblNme + ".addr_idn, "+ tblNme + ".nme_idn, "+ tblNme + ".dflt_yn dyn ";
                
            srchFields += delim + tblNme + "." + lTblFld   ;
            if(util.nvl(dao.getALIAS()).length() > 0)
                srchFields += delim + dao.getALIAS();
            
        }
        return srchFields ;
    }
    public AddressAction() {
        super();
    }
}
