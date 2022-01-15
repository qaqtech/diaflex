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

import java.io.IOException;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

import java.util.logging.Level;

import org.apache.struts.action.Action;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class NmeRelAction extends DispatchAction {
    
    private final String formName = "customerterms";
    
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
            util.updAccessLog(req,res,"Customer Terms", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeRelFrm udf = (NmeRelFrm)af;
        
        String lNmeIdn = util.nvl(udf.getNmeIdn(),"0");
        String lRelIdn = util.nvl(udf.getIdn(),"0");
        
        String srch = util.nvl((String)udf.getSearch());
        String modify = util.nvl((String)udf.getModify());
        String addnew = util.nvl((String)udf.getAddnew());
        String view = util.nvl((String)udf.getView());
        String reset = util.nvl(udf.getReset());
        ArrayList checkList = new ArrayList();
        checkList.add("aadat_idn");
        checkList.add("mbrk1_idn");
        checkList.add("mbrk2_idn");
        checkList.add("mbrk3_idn");
        checkList.add("mbrk4_idn");
        ArrayList checkListcomm = new ArrayList();
        checkListcomm.add("aadat_comm");
        checkListcomm.add("mbrk1_comm");
        checkListcomm.add("mbrk2_comm");
        checkListcomm.add("mbrk3_comm");
        checkListcomm.add("mbrk4_comm");
        if(srch.length() > 0) {
            return search(am, af, req, res);
        } else if(view.length() > 0) {
            return load(am, af, req, res);
        }else if(reset.length() > 0) {
            return reset(am, af, req, res); 
        
        } else {
            if(addnew.length() > 0) {
                ArrayList params = new ArrayList();
                String insIntoQ = " insert into nmerel (nmerel_idn, nme_idn, flg ";
                String insValQ = "values(nmerel_seq.nextval, ?, ?";
                params.add(lNmeIdn);
                params.add("NEW");
                for(int j=0; j < daos.size(); j++) {
                    UIFormsFields dao = (UIFormsFields)daos.get(j);
                    String lFld = dao.getFORM_FLD();
                    String fldNm = lFld ;
                    String lVal = (String)udf.getValue(fldNm);
                    String lReqd = dao.getREQ_YN();//util.nvl((String)formFields.get(lFld+"REQ"));
                    String        lDsc = dao.getDSP_TTL();
                    if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                        String errorMsg = lDsc + " is  Required";

                        errors.add(errorMsg);
                        params.clear();

                    }    
                    else {
                        if(!lFld.equals("ttl_trm_pct")){
                        String lTblFld = dao.getTBL_FLD().toLowerCase();//util.nvl((String)formFields.get(lFld+"TF"));
                        insIntoQ += ", " + lTblFld;
                        if((dao.getFLD_TYP().equals("DT")) && (lVal.length() == 10))
                            insValQ += ", to_date(?, 'dd-mm-rrrr')";
                        else
                            insValQ  += ", ?";
                       
                        params.add(lVal);
                        }
                    }       
                }
                if(params.size() > 0 && errors.size()==0) {
                   
                        String sql = insIntoQ + ") " + insValQ + ")";
                        int ct = db.execUpd(" Ins Menu", sql, params);
                }
            } else {
              
                String srchFields = getSrchFields(daos,udf);
                String bseFlds = "nmerel_idn, nme_idn, dflt_yn dfn";
                srchFields = helper.getSrchFields(daos, bseFlds, false);
                String getMenu = "select "+ srchFields + " from nmerel where nme_idn = ? and nmerel_idn = ? and vld_dte is null ";
                
                ArrayList params = new ArrayList();
                params.add(lNmeIdn);
                params.add(lRelIdn);

            ArrayList outLst = db.execSqlLst(formName + " for update", getMenu, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
                while(rs.next()) {
                    int lIdn = rs.getInt(1);
                
                String updQ = " update nmerel set nmerel_idn = nmerel_idn, nme_idn = nme_idn, flg = 'MOD' " ;
                String condQ = " where nmerel_idn = ? ";
                params = new ArrayList();
               
                
                    //params.add(Integer.toString(menuIdn));
                    for(int j=0; j < daos.size(); j++) {
                        UIFormsFields dao = (UIFormsFields)daos.get(j);
                        
                        String lFld = dao.getFORM_FLD();//(String)flds.get(j);
                        String fldNm = lFld ;
                        String lVal = (String)udf.getValue(fldNm);
                        String lReqd = dao.getREQ_YN();//util.nvl((String)formFields.get(lFld+"REQ"));
                        String        lDsc = dao.getDSP_TTL();
                        if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                            String errorMsg = lDsc + " is  Required";

                            errors.add(errorMsg);
                            params.clear();

                        }    
                        else {
                            if(!lFld.equals("ttl_trm_pct")){
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
                            if(checkList.contains(lTblFld)){
                                String chkFld = lTblFld+"_CB";
                                lVal= util.nvl((String)udf.getValue(chkFld)).equals("Y")?"":lVal ;
                            }
                            if(checkListcomm.contains(lTblFld)){
                                    String chkFldcomm = checkList.get(checkListcomm.indexOf(lTblFld))+"_CB";
                                    lVal= util.nvl((String)udf.getValue(chkFldcomm)).equals("Y")?"":lVal ;
                            }
                            
                            params.add(lVal);
                            }
                        }       
                    }
          
                if(params.size() > 0 && errors.size()==0) { 
                    params.add(Integer.toString(lIdn));
                   
                    int ct = db.execUpd(" upd menu "+ lIdn, updQ + condQ, params);
                    System.out.println("SQL:-"+updQ + condQ);
                    System.out.println("ArrayList:-"+params.toString());
                }
            }
            rs.close(); pst.close();
        }
        }
        util.SOP(errors.toString());
        req.setAttribute("errors", errors);
        if(errors.size()>0){
            String srchFields = helper.getSrchFields(daos, "nmerel.nmerel_idn, nmerel.nme_idn, nmerel.dflt_yn dyn");
            String srchQ = " and nmerel.nme_idn = ?  and nmerel.vld_dte is null ";
            ArrayList params = new ArrayList();
            params.add(lNmeIdn);
            ArrayList list  = helper.getSrchList(uiForms, "nmerel", srchFields, srchQ, params, daos, "SRCH");
            req.setAttribute("NmeRelList", list);
            return am.findForward("view");
        }
            util.updAccessLog(req,res,"Customer Terms", "save end");
        return load(am, af, req, res);
        }
    }
    
    public ActionForward delete(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Customer Terms", "delete start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeRelFrm udf = (NmeRelFrm)af;
        String msg="";
        ArrayList mjanIsidnLst=new ArrayList();
        ArrayList mjanApidnLst=new ArrayList();
        ArrayList msalidnLst=new ArrayList();
        ArrayList params = new ArrayList();
        String delNmeIdn = util.nvl(udf.getNmeIdn());
        if(delNmeIdn.equals("")){
            delNmeIdn = req.getParameter("nmeIdn");
        }
        String delIdn =  util.nvl(udf.getIdn());
        if(delIdn.equals("")){
            delIdn = req.getParameter("relIdn");
        }
        String mjanQ="select distinct a.idn\n" + 
        "                           from jandtl a, mjan b \n" + 
        "                           where a.idn = b.idn and a.stt = 'IS'  \n" + 
        "                          and b.nme_idn =?\n" + 
        "                          and b.nmerel_idn=?";
        params = new ArrayList();
        params.add(delNmeIdn);
        params.add(delIdn);

            ArrayList outLst = db.execSqlLst("mjanQ", mjanQ, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
        mjanIsidnLst.add(util.nvl(rs.getString("idn")));
        }
            rs.close(); pst.close();
        mjanQ=" select distinct c.idn  from jandtl a, mstk b , mjan c\n" + 
        "             where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='AP' and c.typ like '%AP' and c.stt='IS'\n" + 
        "             and c.nme_idn=? and c.nmerel_idn=?\n" + 
        "             and b.stt in ('MKAP','MKWA')";
                params = new ArrayList();
                params.add(delNmeIdn);
                params.add(delIdn);

            outLst = db.execSqlLst("mjanQ", mjanQ, params);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
        mjanApidnLst.add(util.nvl(rs.getString("idn")));
        }
            rs.close(); pst.close();
        String msalQ="select distinct c.idn  from jansal a, mstk b , msal c \n" + 
        "             where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='SL' \n" + 
        "             and c.typ in ('SL') and c.stt='IS'\n" + 
        "             and c.nme_idn=? and c.nmerel_idn=?";
        params = new ArrayList();
        params.add(delNmeIdn);
        params.add(delIdn);

            outLst = db.execSqlLst("msalQ", msalQ,params);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            msalidnLst.add(util.nvl(rs.getString("idn")));
        }
            rs.close(); pst.close();
        if(mjanIsidnLst.size()==0 && msalidnLst.size()==0 && mjanApidnLst.size()==0){
        String delQ = " update nmerel set vld_dte = sysdate where nme_idn = ? and nmerel_idn = ?";
        params = new ArrayList();
        params.add(delNmeIdn);
        params.add(delIdn);
        int cnt = db.execUpd(" Del "+ delIdn, delQ, params);
        }
        if(mjanIsidnLst.size()>0){
            String mjanIdn=mjanIsidnLst.toString();
            mjanIdn = mjanIdn.replaceAll("\\[", "");
            mjanIdn = mjanIdn.replaceAll("\\]", "");
            msg=" Memo Issue Idn:-"+mjanIdn;
        }
        if(mjanApidnLst.size()>0){
            String mjanIdn=mjanApidnLst.toString();
            mjanIdn = mjanIdn.replaceAll("\\[", "");
            mjanIdn = mjanIdn.replaceAll("\\]", "");
            msg=" Memo Approve Idn:-"+mjanIdn;
        }
        if(msalidnLst.size()>0){
            String msalIdn=msalidnLst.toString();
            msalIdn = msalIdn.replaceAll("\\[", "");
            msalIdn = msalIdn.replaceAll("\\]", "");
            msg=" SaleIdn:-"+msalIdn;
        }
        if(!msg.equals("")){
            req.setAttribute("MSG", "Please take return "+msg+" then Only Possible To delete Term");
        }
            util.updAccessLog(req,res,"Customer Terms", "delete end");
        return load(am, af, req, res);
        }
    }
    
    
    public ActionForward active(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Customer Terms", "active start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeRelFrm udf = (NmeRelFrm)af;
        ArrayList params = new ArrayList();
        String delNmeIdn = util.nvl(udf.getNmeIdn());
        if(delNmeIdn.equals("")){
            delNmeIdn = req.getParameter("nmeIdn");
        }
        String delIdn =  util.nvl(udf.getIdn());
        if(delIdn.equals("")){
            delIdn = req.getParameter("relIdn");
        }
        
        String delQ = " update nmerel set vld_dte = null where nme_idn = ? and nmerel_idn = ?";
        params = new ArrayList();
        params.add(delNmeIdn);
        params.add(delIdn);
        int cnt = db.execUpd(" Del "+ delIdn, delQ, params);

        util.updAccessLog(req,res,"Customer Terms", "active end");
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
            util.updAccessLog(req,res,"Customer Terms", "search start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeRelFrm udf = (NmeRelFrm)af;
        
        HashMap tbls = new HashMap();
        String nmeIdn = udf.getNmeIdn();
        ArrayList params = new ArrayList();
        String srchQ = "", srchFields = "" ;
        srchQ = " and nmerel.nme_idn = ? and nmerel.vld_dte is null ";
        params.add(nmeIdn);
        String tblNmes = "" ;
        for(int i=0; i < tbls.size(); i++) {
            String delim = "" ;
            if(i > 0)
                delim = ", ";
            tblNmes += delim + (String)tbls.get(i);
        }
        
        srchFields = getSrchFields(daos,udf);
        //srchFields = " " + tblNme + ".nmerel_idn, "+ tblNme + ".nme_idn, "+ tblNme + ".dflt_yn dyn ";
        //srchFields = helper.getSrchFields(daos, "nmerel_idn, nme_idn, dflt_yn dyn", false);
        ArrayList list = null ;
        list = getSrchList(srchFields, srchQ, params, daos, "SRCH",uiForms,req,udf);
        //list = helper.getSrchList(uiForms, "nmerel", srchFields, srchQ, params, daos, "SRCH");
        //udf.setList(list);
        req.setAttribute("NmeRelList", list);
            util.updAccessLog(req,res,"Customer Terms", "search end");
        if(util.nvl(udf.getIdn(),"0") == "0") 
            return am.findForward("searchlst");
        else
            return am.findForward("view");
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
            util.updAccessLog(req,res,"Customer Terms", "load start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeRelFrm udf = (NmeRelFrm)af;
        
       
        
         
        ArrayList params     = new ArrayList();
        String lNmeIdn    = util.nvl(udf.getNmeIdn());
        if(lNmeIdn.equals(""))
            lNmeIdn = util.nvl(req.getParameter("nmeIdn"));
        params.add(lNmeIdn);
       
       
     
       
        String srchFields = helper.getSrchFields(daos, "nmerel.nmerel_idn, nmerel.nme_idn, nmerel.dflt_yn dyn");
        String srchQ = " and nmerel.nme_idn = ?  ";
     
       
       
         ArrayList list  = helper.getSrchList(uiForms, "nmerel", srchFields, srchQ, params, daos, "SRCH");
       
        
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
        req.setAttribute("NmeRelList", list);
        udf.setNmeIdn(lNmeIdn);
        HashMap vldInvalidterms=vldInvalidterms(req,res,lNmeIdn); 
        req.setAttribute("vldInvalidterms", vldInvalidterms);
            util.updAccessLog(req,res,"Customer Terms", "load end");
        return am.findForward("view");
        }
        
    }
    public HashMap vldInvalidterms(HttpServletRequest req,HttpServletResponse res,String lNmeIdn){
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
        HashMap vldInvalidterms=new HashMap();
        String nmerelVldQ = "select decode(vld_dte,'','','red') color,vld_dte,nmerel_idn from nmerel where nme_idn=?" ;
        ArrayList params = new ArrayList();
        params.add(lNmeIdn);
        ArrayList outLst = db.execSqlLst("nmerelVldQ", nmerelVldQ, params);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
        while (rs.next()) {
                    vldInvalidterms.put(util.nvl(rs.getString("nmerel_idn")),util.nvl(rs.getString("color")));
                }
                rs.close(); pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        return vldInvalidterms;
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
            util.updAccessLog(req,res,"Customer Terms", "edit start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        ArrayList mjanIsidnLst=new ArrayList();
        ArrayList mjanApidnLst=new ArrayList();
        ArrayList msalidnLst=new ArrayList();
        NmeRelFrm udf = (NmeRelFrm)af;
        
        ArrayList params     = new ArrayList();
        String lNmeIdn    = util.nvl(udf.getNmeIdn());
        if(lNmeIdn.equals(""))
            lNmeIdn = util.nvl(req.getParameter("nmeIdn"));
        
       
        String  lRelIdn = util.nvl(req.getParameter("relIdn"));
       
        
            String mjanQ="select distinct a.idn\n" + 
            "                           from jandtl a, mjan b \n" + 
            "                           where a.idn = b.idn and a.stt = 'IS'  \n" + 
            "                          and b.nme_idn =?\n" + 
            "                          and b.nmerel_idn=?";
            params = new ArrayList();
            params.add(lNmeIdn);
            params.add(lRelIdn);

            ArrayList outLst = db.execSqlLst("mjanQ", mjanQ, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
            mjanIsidnLst.add(util.nvl(rs.getString("idn")));
            }
            rs.close(); pst.close();
            mjanQ=" select distinct c.idn  from jandtl a, mstk b , mjan c\n" + 
            "             where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='AP' and c.typ like '%AP' and c.stt='IS'\n" + 
            "             and c.nme_idn=? and c.nmerel_idn=?\n" + 
            "             and b.stt in ('MKAP','MKWA')";
                    params = new ArrayList();
                    params.add(lNmeIdn);
                    params.add(lRelIdn);
    
            outLst = db.execSqlLst("mjanQ", mjanQ, params);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
            mjanApidnLst.add(util.nvl(rs.getString("idn")));
            }
            rs.close(); pst.close();
            String msalQ="select distinct c.idn  from jansal a, mstk b , msal c \n" + 
            "             where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='SL' \n" + 
            "             and c.typ in ('SL') and c.stt='IS'\n" + 
            "             and c.nme_idn=? and c.nmerel_idn=?";
            params = new ArrayList();
            params.add(lNmeIdn);
            params.add(lRelIdn);
            outLst = db.execSqlLst("msalQ", msalQ,params);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                msalidnLst.add(util.nvl(rs.getString("idn")));
            }
            rs.close(); pst.close();
            if(mjanIsidnLst.size()==0 && msalidnLst.size()==0 && mjanApidnLst.size()==0){
        
        String srchFields = helper.getSrchFields(daos, "nmerel.nmerel_idn, nmerel.nme_idn, nmerel.dflt_yn dyn");
        String srchQ = " and nmerel.nme_idn = ?  and  nmerel.nmerel_idn = ?  ";
       
        params     = new ArrayList();
        params.add(lNmeIdn);
        params.add(lRelIdn);

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
        
        
        srchFields = helper.getSrchFields(daos, "nmerel.nmerel_idn, nmerel.nme_idn, nmerel.dflt_yn dyn");
         srchQ = " and nmerel.nme_idn = ?  and nmerel.vld_dte is null ";
        params     = new ArrayList();
        params.add(lNmeIdn);
        
        
          list  = helper.getSrchList(uiForms, "nmerel", srchFields, srchQ, params, daos, "SRCH");
        
        //udf.setList(list);
        //info.setNmeSrchList(list);
        req.setAttribute("NmeRelList", list);
        udf.setNmeIdn(lNmeIdn);
        udf.setIdn(lRelIdn);
        req.setAttribute("method", "edit");
        HashMap vldInvalidterms=vldInvalidterms(req,res,lNmeIdn); 
        req.setAttribute("vldInvalidterms", vldInvalidterms);
        }else{
             String msg="";
            if(mjanIsidnLst.size()>0){
                String mjanIdn=mjanIsidnLst.toString();
                mjanIdn = mjanIdn.replaceAll("\\[", "");
                mjanIdn = mjanIdn.replaceAll("\\]", "");
                msg=" Memo Issue Idn:-"+mjanIdn;
            }
            if(mjanApidnLst.size()>0){
                String mjanIdn=mjanApidnLst.toString();
                mjanIdn = mjanIdn.replaceAll("\\[", "");
                mjanIdn = mjanIdn.replaceAll("\\]", "");
                msg=" Memo Approve Idn:-"+mjanIdn;
            }
            if(msalidnLst.size()>0){
                String msalIdn=msalidnLst.toString();
                msalIdn = msalIdn.replaceAll("\\[", "");
                msalIdn = msalIdn.replaceAll("\\]", "");
                msg=" SaleIdn:-"+msalIdn;
            }
            if(!msg.equals("")){
                req.setAttribute("MSG", "Please take return "+msg+" then Only Possible To Update Term");
            }
            return load(am, af, req, res);
        }
            util.updAccessLog(req,res,"Customer Terms", "edit end");
        return am.findForward("view");
        }
        
    }
    public ActionForward loadApprove(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Customer Terms", "loadApprove start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeRelFrm udf = (NmeRelFrm)af;
        ArrayList params = new ArrayList();
        String srchQ = "", srchFields = "" ;
        srchQ = " and nvl(flg,'NEW') in(?,?) ";
        params.add("NEW");
        params.add("MOD");
        srchFields = getSrchFields(daos,udf);
        ArrayList list = getSrchList(srchFields, srchQ, params, daos, "APPROVE",uiForms,req,udf);
        //udf.setList(list);
        info.setNmeRelList(list);
        util.SOP(" NmeSearch Count : "+ list.size());
            util.updAccessLog(req,res,"Customer Terms", "loadApprove end");
        return am.findForward("approvelst");
        }
    }
    
    
    public ActionForward approvePg(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Customer Terms", "loadApprove start");
        util.updAccessLog(req,res,"Customer Terms", "loadApprove end");
        return am.findForward("approve");
        }
    }
    public ActionForward loadwebusr(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Customer Terms", "loadwebusr start");
        NmeRelFrm udf = (NmeRelFrm)af;
        String lNmeIdn = util.nvl(req.getParameter("nmeIdn"));
        String  lRelIdn = util.nvl(req.getParameter("relIdn"));
        ArrayList trmslst=new ArrayList();
        ArrayList usrIdLst=new ArrayList();
        HashMap userDtlMap=new HashMap();
//        if(lNmeIdn.equals(""))
//        util.nvl((String)udf.getValue("lNmeIdn"));
//        if(lRelIdn.equals(""))
//        util.nvl((String)udf.getValue("oldlRelIdn"));
        String nmerelVldQ = "select dtls,nmerel_idn from nme_rel_v where nme_idn=? order by nmerel_idn" ;
        ArrayList params = new ArrayList();
        params.add(lNmeIdn);

            ArrayList outLst = db.execSqlLst("nmerelVldQ", nmerelVldQ, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        try {
        while (rs.next()) {
            ArrayList trms=new ArrayList();   
            trms.add(util.nvl(rs.getString("nmerel_idn")));
            trms.add(util.nvl(rs.getString("dtls")));
            trmslst.add(trms);
        }
        rs.close(); pst.close();
           String usrQ = "select a.usr_id ,a.usr,c.dtls \n" + 
           "           from web_usrs a , mnme b , nme_rel_all_v c \n" + 
           "           where a.rel_idn = c.nmerel_idn and c.nme_idn = b.nme_idn  \n" + 
           "           and a.to_dt is null and b.vld_dte is null\n" + 
           "           and b.nme_idn = ? and c.nmerel_idn=? order by a.usr" ;
           params = new ArrayList();
           params.add(lNmeIdn);
           params.add(lRelIdn);
             outLst = db.execSqlLst("usrQ", usrQ, params);  
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
             while (rs.next()) {
                 HashMap usrsdtl=new HashMap();  
                 String usrid=util.nvl(rs.getString("usr_id"));
                 usrsdtl.put("USR",util.nvl(rs.getString("usr")));
                 usrsdtl.put("TRM",util.nvl(rs.getString("dtls")));
                 usrIdLst.add(usrid);
                 userDtlMap.put(usrid,usrsdtl);
             }
             rs.close(); pst.close();
         } catch (SQLException sqle) {
         // TODO: Add catch code
         sqle.printStackTrace();
        }
        req.setAttribute("trmslst", trmslst);
        req.setAttribute("userDtlMap", userDtlMap);
        session.setAttribute("usrIdLst", usrIdLst);
        if((trmslst==null || trmslst.size()==0) && (usrIdLst==null || usrIdLst.size()==0))
        req.setAttribute("valid", "No Result Found");
        
        if((trmslst==null || trmslst.size()==0) && (usrIdLst!=null || usrIdLst.size()>0))
        req.setAttribute("valid", "Business Term Not yet Approved or Exists");
        
        if((trmslst!=null || trmslst.size()>0) && (usrIdLst==null || usrIdLst.size()==0))
        req.setAttribute("valid", "Web User Not Found");
        
        udf.setValue("lNmeIdn", lNmeIdn);
        udf.setValue("oldlRelIdn", lRelIdn);
            util.updAccessLog(req,res,"Customer Terms", "loadwebusr end");
        return am.findForward("loadwebusr");
        }
    }
    public ActionForward savewebusr(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Customer Terms", "savewebusr start");
        NmeRelFrm udf = (NmeRelFrm)af;
        String lNmeIdn = util.nvl((String)udf.getValue("lNmeIdn"));
        String  oldlRelIdn = util.nvl((String)udf.getValue("oldlRelIdn"));
        ArrayList params = new ArrayList();
        ArrayList usrIdLst = (ArrayList)session.getAttribute("usrIdLst");
        int upJan=0;
        for(int i=0;i<usrIdLst.size();i++){
           String lUsrIdn=util.nvl((String)usrIdLst.get(i));
           String isChecked = util.nvl((String)udf.getValue(lUsrIdn));
           if(isChecked.equals("yes")){
           String  newlRelIdn = util.nvl((String)udf.getValue("TRM_"+lUsrIdn));
           params = new ArrayList();
           params.add(newlRelIdn);
           params.add(lUsrIdn);
           upJan = db.execUpd("update Web", "update web_usrs set rel_idn=? where usr_id=?", params);
        }
        }

        
        if(upJan>0){
            req.setAttribute("msg", "Business Term Updated Sucessfully.");    
        }else{
            req.setAttribute("msg", "Business Term Not Updated.");    
        }
            util.updAccessLog(req,res,"Customer Terms", "savewebusr end");
        return am.findForward("loadwebusr");
        }
    }
    public ActionForward approve(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Customer Terms", "approve start");
        String relIdn = util.nvl(req.getParameter("idn"));
        String nmeApp = "update nmerel set flg= 'CNF' where nmerel_idn = ? ";
        ArrayList ary = new ArrayList();
        ary.add(relIdn);
        
        int ct = db.execUpd("upadteRel", nmeApp,ary);
        ary = new ArrayList();
        String party = "select nme_idn, nme from nme_v a " +
            " where exists (select 1 from nmerel b where a.nme_idn = b.nme_idn and " +
            " b.vld_dte is null and flg = 'CNF') and a.ss_flg='Y' order by nme";
        if(info.getIsSalesExec()) {
            party = "select nme_idn, nme from nme_v a " +
                        " where a.emp_idn = ? " +
                "  and exists (select 1 from nmerel b where a.nme_idn = b.nme_idn and " +
                        " b.vld_dte is null and flg = 'CNF') and ss_flg='Y' order by nme";
            ary.add(info.getDfNmeIdn());
        }  
        ArrayList partyList = new ArrayList();

            ArrayList outLst = db.execSqlLst("byr", party, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                ByrDao byr = new ByrDao();
                byr.setByrIdn(rs.getInt("nme_idn"));
                byr.setByrNme(util.nvl(rs.getString("nme")));
                partyList.add(byr);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        session.setAttribute("srchpartyList", partyList);
            util.updAccessLog(req,res,"Customer Terms", "approve end");
        return am.findForward("approve");
        
        }
    }
    public ArrayList getSrchList(String srchFields, String srchQ, ArrayList params, ArrayList daos, String typ,UIForms  uiForms,HttpServletRequest req,NmeRelFrm udf) {
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
      String frmTbl = "";
        ArrayList list = new ArrayList();
        try {
          if(frmTbl.length() == 0) {
            frmTbl = "nmerel";
            if(util.nvl(uiForms.getFRM_TBL()).length() > 0)
              frmTbl = uiForms.getFRM_TBL();
          }
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
                NmeRel srchDao = new NmeRel();
                String relIdn = rs.getString(1);
                String nmeIdn = rs.getString(2);
                String dflt_yn = rs.getString("dyn");
                if(dflt_yn.equals("Y")) {
                    if(util.nvl(udf.getIdn(),"0") == "0") 
                        info.setValue("relidn", relIdn);
                        //udf.setIdn(relIdn);
                }    
                srchDao.setIdn(relIdn);
                srchDao.setNmeIdn(nmeIdn);
                //nme.setValue("idn", nmeIdn);
                for (int i = 0; i < daos.size(); i++) {
                    UIFormsFields dao = (UIFormsFields)daos.get(i);
                    String tblFld = dao.getTBL_FLD().toLowerCase();
                    String lFld = dao.getFORM_FLD();
                    String dbVal = util.nvl(rs.getString(tblFld));
                    String fldAlias = util.nvl(dao.getALIAS());
                    String aliasFld = "", aliasVal = "" ;
                    
                    String dfltVal = dao.getDFLT_VAL();
                    try{
                        if((dfltVal.length() > 0) && (dbVal.length() == 0)) {
                          dbVal = dfltVal;
                        }
                    } catch(Exception e) {
                      
                    }
                    try {
                        if (fldAlias.length() > 0) {
                            aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);
                            aliasVal = util.nvl(rs.getString(aliasFld));
                            
                        }
                    } catch (SQLException sqle) {
                        // TODO: Add catch code
                        //sqle.printStackTrace();
                       
                    }
                                     
                    if(!(typ.equalsIgnoreCase("APPROVE"))) {
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
                }
                list.add(srchDao);
            }
            rs.close(); pst.close();
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
                util.updAccessLog(req,res,"Customer Terms", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Customer Terms", "init");
            }
            }
            return rtnPg;
         }
    
  public String getSrchFields(ArrayList daos,NmeRelFrm udf) {
      String srchFields;
      srchFields = "";
      JspUtil util=new JspUtil();
      ArrayList ukFld = new ArrayList();
      for(int j=0; j < daos.size(); j++) {
          UIFormsFields dao = (UIFormsFields)daos.get(j);
          String lTblFld = dao.getTBL_FLD().toLowerCase();
          String lFld = dao.getFORM_FLD().toLowerCase();
          String tblNme = dao.getTBL_NME();
          if(util.nvl(dao.getFLG()).equals("UK"))
              ukFld.add(lTblFld);
          String delim = ", ";
          String dfltVal = dao.getDFLT_VAL();
          try{
          String ctVal = util.nvl((String)udf.getValue(lFld));
          if((dfltVal.length() > 0) && (ctVal.length() ==0))
          udf.setValue(lFld, dfltVal);
          } catch(Exception e) {
          }
        
          /*if (j==0) {
              delim = "";
          } */   
          //nmerel_idn, nme_idn, dflt_yn dyn
          if(j == 0) 
              srchFields = " " + tblNme + ".nmerel_idn, "+ tblNme + ".nme_idn, "+ tblNme + ".dflt_yn dyn ";
              
          srchFields += delim + tblNme + "." + lTblFld   ;
          if(util.nvl(dao.getALIAS()).length() > 0)
              srchFields += delim + dao.getALIAS();
          
      }
      return srchFields ;
  }
  /*
    public String getSrchFields(ArrayList daos) {
        String srchFields = " nmerel_idn, nme_idn, dflt_yn dyn " ;
        ukFld = new ArrayList();
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lTblFld = dao.getTBL_FLD().toLowerCase();
            if(util.nvl(dao.getFLG()).equals("UK"))
                ukFld.add(lTblFld);
            String delim = ", ";
            //if(!(dao.getFLD_TYP().equals("I")))
                srchFields += delim + lTblFld   ;
            
        }
        return srchFields ;
    }
    */
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
          util.updAccessLog(req,res,"Customer Terms", "reset start");
          HashMap  formFields = info.getFormFields(formName);

          if ((formFields == null) || (formFields.size() == 0)) {
              formFields = util.getFormFields(formName);
          }

          UIForms  uiForms = (UIForms) formFields.get("DAOS");
          ArrayList  daos    = uiForms.getFields();
          FormsUtil helper  = new FormsUtil();
          ArrayList errors  = new ArrayList();
          helper.init(db, util, info);
      NmeRelFrm udf = (NmeRelFrm)af;
      ArrayList params     = new ArrayList();
      String lNmeIdn    = util.nvl(udf.getNmeIdn());
      if(lNmeIdn.equals(""))
          lNmeIdn = util.nvl(req.getParameter("nmeIdn"));
      udf.reset();
      udf.setNmeIdn(lNmeIdn);
      
      String srchFields = getSrchFields(daos,udf);
      String srchQ = " and nmerel.nme_idn = ?  and nmerel.vld_dte is null ";
      params.add(lNmeIdn);
      
       ArrayList list  = helper.getSrchList(uiForms, "nmerel", srchFields, srchQ, params, daos, "SRCH");
      
      
      req.setAttribute("NmeRelList", list);
      req.setAttribute("method","reset");
          util.updAccessLog(req,res,"Customer Terms", "reset end");
      return am.findForward("view");
      }
      
  }
  
    public ActionForward getNmeRelExcl(ActionMapping mapping, ActionForm form,
                               HttpServletRequest req,
                               HttpServletResponse response) throws Exception {
           HttpSession session = req.getSession(false);
           InfoMgr info = (InfoMgr)session.getAttribute("info");
           DBUtil util = new DBUtil();
           DBMgr db = new DBMgr(); 
           db.setCon(info.getCon());
           util.setDb(db);
           util.setInfo(info);
           db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
           util.setLogApplNm(info.getLoApplNm());
       StringBuffer sb = new StringBuffer();
       String fld = util.nvl(req.getParameter("fld"));
       String fldval = util.nvl(req.getParameter("fldval"));
       ArrayList ary = new ArrayList();
       boolean exists=false;
       
       String sql = "select Get_CUR_EXTRA_PCT(?) pct from dual" ;
       if(fld.equals("trms_idn"))
           sql = "select Get_DAYTERMS_EXTRA_PCT(?) pct from dual";
       ary = new ArrayList();
       ary.add(fldval);

           ArrayList outLst = db.execSqlLst("Verify CUR", sql, ary);
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet rs = (ResultSet)outLst.get(1);
       if(rs.next()){
           response.setContentType("text/xml"); 
           response.setHeader("Cache-Control", "no-cache"); 
           response.getWriter().write("<message>"+util.nvl(rs.getString("pct"))+"</message>");
           exists=true;
       }
       rs.close(); pst.close();
       if(!exists){
         response.setContentType("text/xml"); 
         response.setHeader("Cache-Control", "no-cache"); 
         response.getWriter().write("<message>0</message>");
       }
       rs.close(); pst.close();
       return null;
       }
    public NmeRelAction() {
        super();
    }
}
