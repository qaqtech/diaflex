package ft.com;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;

import ft.com.masters.ModulePropertyForm;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import org.json.JSONException;
import org.json.JSONObject;

public class DynamicPropertyAction extends DispatchAction {
    private final String formName   = "modulepform";


    public DynamicPropertyAction() {
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
            util.updAccessLog(req,res,"Edit Properties Module", "load start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        DynamicPropertyForm udf = (DynamicPropertyForm) af;

        String maxsrt=null;
        ArrayList ary=null;
        ArrayList error = (ArrayList)req.getAttribute("errors");
        if(error!=null && error.size() >0){
            ArrayList errSizeList = (ArrayList)error.get(0);
             if(errSizeList!=null && errSizeList.size() ==0){
             
                 udf.reset();
             }
        }else{
          udf.reset();
        }
        String mdl = util.nvl(req.getParameter("mdl"));
        String sname = util.nvl(req.getParameter("sname"));
        String par = util.nvl(req.getParameter("par"));
        if (mdl.equals("")) {
            mdl = util.nvl((String) udf.getValue("mdl"));
            sname = util.nvl((String) udf.getValue("sname"));
            par = util.nvl((String) udf.getValue("par"));
        }
        if (mdl.equals("")) {
            mdl = util.nvl((String) info.getValue("mdl"));
            sname = util.nvl((String) info.getValue("sname"));
            par = util.nvl((String) info.getValue("par"));
        }
        ArrayList params = new ArrayList();
        params.add(mdl);
        info.setValue("mdl",mdl);
        info.setValue("sname",sname);
        info.setValue("par",par);

        String    srchFields = helper.getSrchFields(daos, "mdl");
        String    srchQ      = " and mdl = ? ";
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
                String srt = (String)gen.getValue("srt");
                // util.SOP(" i : "+i + " | Idn : "+ lIdn);
                // util.SOP(gen.toString());
                for (int j = 0; j < daos.size(); j++) {
                    UIFormsFields dao     = (UIFormsFields) daos.get(j);
                    String        lFld    = dao.getFORM_FLD();
                    String        lFrmFld = lFld;

                    if (frmRec > 1) {
                        lFrmFld = lFld + "_" + lIdn +"_"+srt;
                    }

                    String fldAlias = util.nvl(dao.getALIAS());
                    String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);

                    udf.setValue(lFrmFld, util.nvl((String) gen.getValue(lFld)));
                    udf.setValue(lFrmFld + "_dsp", util.nvl((String) gen.getValue(aliasFld)));
                }
            }
        }
        String MaxSrt="select max(srt) srt from rep_prp where mdl=?";
        ary=new ArrayList();
        ary.add(mdl);
            ArrayList outLst = db.execSqlLst("maxsrt",MaxSrt, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        if(rs.next()){
          maxsrt=util.nvl(rs.getString(1));
        }
            rs.close();
            pst.close();
        req.setAttribute("maxsrt", maxsrt);
        req.setAttribute(formName, list);
     
        udf.setValue("mdl", mdl);
        if(par.equals("V"))
        getmdlprpV(req,mdl,sname);
        else if(par.equals("A"))
        getmdlprpA(req,mdl,sname);
        else if(par.equals("SRCH") || par.equals("STK_CRT") || par.equals("GENERIC") || par.equals("MIX") || par.equals("SMX") || par.equals("ADVSRCH"))
        getmdlprpSRCH(req,mdl,sname,par); 
        else if(par.equals("VW"))
        getmdlprpVW(req,mdl); 
            util.updAccessLog(req,res,"Edit Properties Module", "load end");
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
            util.updAccessLog(req,res,"Edit Properties Module", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }
            HashMap dbinfo = info.getDmbsInfoLst();
            String rep_path = util.nvl((String)dbinfo.get("REP_PATHNEW"),util.nvl((String)dbinfo.get("REP_PATH")));
            String client = (String)dbinfo.get("CNT");
            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        DynamicPropertyForm udf = (DynamicPropertyForm) af;
        ArrayList errorList = new ArrayList();
        int lmt = Integer.parseInt(util.nvl(uiForms.getREC(), "5"));

        if (udf.getAddnew() != null) {
          

            for (int i = 1; i <= lmt; i++) {
                int    lIdn     = i;
                boolean isValid = false;
                ArrayList params   = new ArrayList();
                String insIntoQ = " insert into rep_prp (mdl  ";
                String insValQ  = " values( ? ";
                params.add(udf.getValue("mdl"));
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
                    int    ct  = db.execUpd(" Ins Menu", sql, params);
                    
                }
                if(isValid){
                    errorList.add(errors);
                    errors = new ArrayList();
                }
            }
        }

        if (udf.getModify() != null) {
            

            String    getMenu = "select " + helper.getSrchFields(daos, "mdl", false) + " from rep_prp where mdl=? order by srt ";
            ArrayList    params  = new ArrayList();
            params.add(udf.getValue("mdl"));
            ArrayList outLst = db.execSqlLst("getUIFormFields for update", getMenu, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            int count = 0;
            while (rs.next()) {
                String    lIdn  = rs.getString(1);
                String val = rs.getString(2);
                String srt = rs.getString("srt");
                String updQ  = " update rep_prp set  ";
                String condQ = " where mdl = ? and prp=?" ;
                boolean isValid = false;
                String cnt = String.valueOf(count+1);
                params = new ArrayList();

                String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn+"_"+srt));

                if (isChecked.length() > 0) {
                 
                    // params.add(Integer.toString(menuIdn));
                    for (int j = 0; j < daos.size(); j++) {
                        UIFormsFields dao   = (UIFormsFields) daos.get(j);
                        String        lFld  = dao.getFORM_FLD();    // (String)flds.get(j);
                        String        fldNm = lFld + "_" + lIdn+"_"+srt;
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
                    params.add(lIdn);
                    params.add(val);
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
            if(info.getValue("par").equals("GENERIC") || info.getValue("par").equals("A")  || info.getValue("par").equals("V")){
                String memservicemethod="genricSrch";
                if(info.getValue("par").equals("A")  || info.getValue("par").equals("V"))
                memservicemethod="genericPrprVw";
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost(rep_path+"/diaflexWebService/REST/memservice/refresh");
                postRequest.setHeader("Accept", "application/json");
                postRequest.setHeader("Content-type", "application/json");
                JSONObject jObj = new JSONObject();
                try{
                   jObj.put("cnt", info.getDbTyp());
                   jObj.put("method", memservicemethod);
                   jObj.put("mdlnme", info.getValue("mdl"));
                   jObj.put("sessionname", info.getValue("sname"));
                } catch (JSONException jsone) {
                jsone.printStackTrace();
                }
                             
                StringEntity insetValue = new StringEntity(jObj.toString());
                insetValue.setContentType(MediaType.APPLICATION_JSON);
                postRequest.setEntity(insetValue);                       
                HttpResponse responsejson = httpClient.execute(postRequest);                       
                if (responsejson.getStatusLine().getStatusCode() !=200) {
                throw new RuntimeException("Failed : HTTP error code : "+responsejson.getStatusLine().getStatusCode());
                }                                                                           
                BufferedReader br = new BufferedReader(new InputStreamReader((responsejson.getEntity().getContent())));
                String outsetValue="";
                String jsonStr="";
                //System.out.println("OutsetValue from Server .... \n"+br.readLine());
                while ((outsetValue = br.readLine()) != null) {
                    //    System.out.println(outsetValue);
                   jsonStr =jsonStr+outsetValue ;
                }
                //    System.out.println(jsonStr);
                httpClient.getConnectionManager().shutdown();
                if(!jsonStr.equals("")){
                    JSONObject  jObject = new JSONObject(jsonStr);
                    String  status = (String)jObject.get("STATUS");
                    System.out.println(status);
                }
            }
        ArrayList ary  = new ArrayList();
        ary.add(udf.getValue("mdl"));
        int updCt = db.execCall(" Rank Update ", " upd_rep_prp_rnk(?) ", ary);

        req.setAttribute("errors", errorList);
//        util.initPrp();
            util.updAccessLog(req,res,"Edit Properties Module", "save end");
        return load(am, af, req, res);
        }
    }
    public void  getmdlprpV(HttpServletRequest req ,String mdl,String sname){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList  repMemoLst = new ArrayList();
        repMemoLst =(ArrayList)session.getAttribute(sname);
            repMemoLst = new ArrayList();
        String rep_prpVw = "select prp from rep_prp where mdl='"+mdl+"' and flg='Y' order by srt, rnk";
        ArrayList outLst = db.execSqlLst("rep_prp",rep_prpVw, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs1 = (ResultSet)outLst.get(1);
        try {
            while (rs1.next()) {
                repMemoLst.add(rs1.getString("prp"));
            }
            rs1.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute(sname, repMemoLst);
    }
    public void  getmdlprpA(HttpServletRequest req ,String mdl,String sname){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList  repMemoLst = new ArrayList();
        repMemoLst =(ArrayList)session.getAttribute(sname);
            repMemoLst = new ArrayList();
        String rep_prpVw = "select prp from rep_prp where mdl='"+mdl+"' and flg='Y' order by srt, rnk";
        ArrayList outLst = db.execSqlLst("rep_prp",rep_prpVw, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs1 = (ResultSet)outLst.get(1);
        try {
            while (rs1.next()) {
                repMemoLst.add(rs1.getString("prp"));
            }
            rs1.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute(sname, repMemoLst);
    }
    
    public void  getmdlprpSRCH(HttpServletRequest req,String mdl,String sname,String par){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList srchPrpLst = new ArrayList();
            String getSrchPrp = "Select prp , flg from rep_prp where mdl = '"+mdl+"' and flg in('M','S','Y','CTA') order by rnk ";

        ArrayList outLst = db.execSqlLst(" Srch Prp ", getSrchPrp, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
            try {
            

            while (rs.next()) {
                 ArrayList srchMdl = new ArrayList();
                 srchMdl.add(util.nvl(rs.getString("prp")));
                 srchMdl.add(util.nvl(rs.getString("flg")));
                 srchPrpLst.add(srchMdl);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        if(par.equals("MIX"))
        info.setMixScrhLst(srchPrpLst);
        if(par.equals("SMX"))
        info.setSmxScrhLst(srchPrpLst);
        if(par.equals("ADVSRCH"))
        info.setAdvPrpLst(srchPrpLst);
        if(par.equals("SRCH"))
        info.setSrchPrpLst(srchPrpLst);
        if(par.equals("STK_CRT")){
        session.setAttribute(sname, srchPrpLst);
        info.setStkCrtprplist(srchPrpLst);
        }
        if(par.equals("GENERIC")){
        session.setAttribute(sname, srchPrpLst);
        info.setGncPrpLst(srchPrpLst);
        }
    }
    public void  getmdlprpVW(HttpServletRequest req,String mdl){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList srchPrpLst = new ArrayList();
            String getSrchPrp = "Select prp , flg from rep_prp where mdl = '"+mdl+"' and flg <> 'N' order by rnk ";

        ArrayList outLst = db.execSqlLst(" Srch Prp ", getSrchPrp, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
            try {
            

            while (rs.next()) {
                 srchPrpLst.add(util.nvl(rs.getString("prp")));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        info.setVwPrpLst(srchPrpLst);
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
                util.updAccessLog(req,res,"Edit Properties Module", "init");
            }
            }
            return rtnPg;
         }
}
