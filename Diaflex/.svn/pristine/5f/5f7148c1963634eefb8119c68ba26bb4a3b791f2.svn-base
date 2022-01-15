package ft.com.contact;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;

import ft.com.dao.UIFormsFields;

import java.io.IOException;

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
import java.sql.Connection;
import java.sql.PreparedStatement;

public class PropertieMapAction extends DispatchAction {
    private final String formName   = "prpMapFrm";


    public PropertieMapAction() {
        super();
    }
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Properties Map", "load start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        PropertieMapForm udf = (PropertieMapForm) form;
        String srchFields = helper.getSrchFields(daos, "mprp");
       
        String mprp = util.nvl(req.getParameter("mprp"));
        String nmeIdn = util.nvl(req.getParameter("nmeIdn"));
        if(mprp.equals("")){
            mprp = (String)udf.getValue("mprp");
            nmeIdn = (String)udf.getValue("nmeIdn");
        }
        udf.reset();
       
        String srchQ      = " and mprp=? and name_id=?";
        ArrayList ary = new ArrayList();
        ary.add(mprp);
        ary.add(nmeIdn);
        // helper.getSrchList(uiForms, frmTbl, srchFields, srchQ, params, daos, typ)
        ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ, ary, daos, "VIEW");

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
                        lFrmFld = lFld + "_" + lIdn +"_"+count;
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
         udf.setValue("nmeIdn",nmeIdn);
         udf.setValue("mprp", mprp);
         req.setAttribute("mprp", mprp);
        // udf.setList(list);
        // info.setNmeSrchList(list);
      
        util.updAccessLog(req,res,"Properties Map", "load end");
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
            util.updAccessLog(req,res,"Properties Map", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        PropertieMapForm udf = (PropertieMapForm)af;
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        ArrayList errorList = new ArrayList();
        String lprp = (String)udf.getValue("mprp");
        String lsrt = (String)mprp.get(lprp+"S");
        ArrayList pSrt = (ArrayList)prp.get(lprp+"S");
        ArrayList pVal = (ArrayList)prp.get(lprp+"V");
        String  nmeIdn = (String)udf.getValue("nmeIdn");
        int lmt = Integer.parseInt(util.nvl(uiForms.getREC(), "5"));

        if (udf.getAddnew() != null) {
           

            for (int i = 1; i <= lmt; i++) {
                int    lIdn     = i;
                String prpVal = "";
                boolean isValid = false;
                ArrayList params   = new ArrayList();
                String insIntoQ = " insert into prp_map_web ( mprp , psrt , name_id , ";
                String insValQ  = " values(?, ? , ? , ";
                params.add(lprp);
                params.add(lsrt);
                params.add(nmeIdn);
                for (int j = 0; j < daos.size(); j++) {
                    UIFormsFields dao   = (UIFormsFields) daos.get(j);
                    String        lFld  = dao.getFORM_FLD();
                    String        fldNm = lFld + "_" + lIdn;
                    String        lVal  = util.nvl((String)udf.getValue(fldNm));
                    String        lDsc = dao.getDSP_TTL();
                    String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));
                      if(j==0)
                        prpVal = lVal;
                        
                    
                    if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                        String errorMsg = lIdn + "-" + lDsc + ", Required.";

                        errors.add(errorMsg);
                        params.clear();
                    } else {
                        String lTblFld = dao.getTBL_FLD().toLowerCase();    // util.nvl((String)formFields.get(lFld+"TF"));
                        if(j==0){
                            insIntoQ +=  lTblFld;
                            insValQ  += "?";
                        }else{
                        insIntoQ += ", " + lTblFld;
                        insValQ  += ",?";
                        }
                        params.add(lVal);
                        if(lReqd.equalsIgnoreCase("Y"))
                        isValid = true;
                    }
                }

                if (params.size() > 0 && errors.size()==0) {
                    if(pVal!=null)
                     lsrt = (String)pSrt.get(pVal.indexOf(prpVal));
                     String sql = insIntoQ +  " , srt ) " + insValQ + " ,?)";
                    params.add(lsrt);
                    int    ct  = db.execUpd(" Ins Menu", sql, params);
                }
                
                if(isValid){
                    errorList.add(errors);
                    errors = new ArrayList();
                }
            }
        }

        if (udf.getModify() != null) {
           
            String orderby = "";
            if (util.nvl(uiForms.getORDER_BY()).length() > 0) {
                orderby = " order by "+uiForms.getORDER_BY();
            }
            String    getMenu = "select "+helper.getSrchFields(daos, "mprp", false) + " from prp_map_web where mprp=? and name_id=?  "+orderby;
            ArrayList    params  = new ArrayList();
            params.add(lprp);
            params.add(nmeIdn);

            int count = 0;
            ArrayList outLst = db.execSqlLst("getUIFormFields for update", getMenu, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String    lIdn  = rs.getString(1);
                String val = rs.getString(2);
                String w2 = rs.getString(3);
                String updQ  = " update prp_map_web set ";
                String condQ = " where mprp=? and name_id=? and val=?  and w2 = ? ";
                boolean isValid = false;
                
                String cnt = String.valueOf(count+1);
                params = new ArrayList();
                
                String lsrt1 = "";
                String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn+"_"+cnt));

                if (isChecked.length() > 0) {

                    // params.add(Integer.toString(menuIdn));
                    for (int j = 0; j < daos.size(); j++) {
                        UIFormsFields dao   = (UIFormsFields) daos.get(j);
                        String        lFld  = dao.getFORM_FLD();    // (String)flds.get(j);
                        String        fldNm = lFld + "_" + lIdn+"_"+cnt;
                        String        lVal  = (String)udf.getValue(fldNm);
                        String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));
                        String        lDsc = dao.getDSP_TTL();
                        if(j==0){
                           
                            lsrt1 =(String)pSrt.get(pVal.indexOf(lVal));
                           
                        }
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
                    params.add(lsrt);
                    params.add(lprp);
                    params.add(nmeIdn);
                    params.add(val);
                    params.add(w2);
                    int ct = db.execDirUpd(" upd menu " + lIdn, updQ+" , srt =? " + condQ, params);
                }
                if(isValid){
                    errorList.add(errors);
                    errors = new ArrayList();
                }
                count++;
            }
            rs.close(); pst.close();
        }

        req.setAttribute("errors", errorList);
            util.updAccessLog(req,res,"Properties Map", "save end");
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
             util.updAccessLog(req,res,"Properties Map", "delete start");
         String mprp = util.nvl(req.getParameter("mprp"));
         String nmeIdn = util.nvl(req.getParameter("nmeIdn"));
         String col = util.nvl(req.getParameter("col"));
         String deleteMap = "delete from prp_map_web where idx=? and name_id=? and val=? ";
         ArrayList ary = new ArrayList();
         ary.add(mprp);
         ary.add(nmeIdn);
         ary.add(col);
         int ct = db.execUpd("delete", deleteMap, ary);
             util.updAccessLog(req,res,"Properties Map", "delete end");
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
                util.updAccessLog(req,res,"Properties Map", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Properties Map", "init");
            }
            }
            return rtnPg;
         }
}
