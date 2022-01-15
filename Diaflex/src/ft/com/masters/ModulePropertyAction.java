package ft.com.masters;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;

import ft.com.generic.GenericImpl;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import java.sql.ResultSet;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ModulePropertyAction extends DispatchAction {

    private final String formName   = "modulepform";



    public ModulePropertyAction() {
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
            util.updAccessLog(req,res,"Module Prp", "load start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        ModulePropertyForm udf = (ModulePropertyForm) af;
        ResultSet rs=null;
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
        String mdl = req.getParameter("mdl");

        if (mdl == null) {
            mdl = (String) udf.getValue("mdl");
        }
        if (mdl == null) {
            mdl = (String) info.getValue("mdl");
        }
        ArrayList params = new ArrayList();
        params.add(mdl);
       

        info.setValue("mdl",mdl);

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
        rs = db.execSql("maxsrt",MaxSrt, ary);
        if(rs.next()){
          maxsrt=util.nvl(rs.getString(1));
        }
        req.setAttribute("maxsrt", maxsrt);
        req.setAttribute(formName, list);
        rs.close();
        udf.setValue("mdl", mdl);
            util.updAccessLog(req,res,"Module Prp", "load end");
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
            util.updAccessLog(req,res,"Module Prp", "save start");
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
        ModulePropertyForm udf = (ModulePropertyForm) af;
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
            ResultSet rs      = db.execSql("getUIFormFields for update", getMenu, params);
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
        }
      
        ArrayList ary  = new ArrayList();
        ary.add(udf.getValue("mdl"));
        int updCt = db.execCall(" Rank Update ", " upd_rep_prp_rnk(?) ", ary);

        req.setAttribute("errors", errorList);
//        util.initPrp();
                  DefaultHttpClient httpClient = new DefaultHttpClient();
                  HttpPost postRequest = new HttpPost(rep_path+"/diaflexWebService/REST/memservice/refresh");
                  postRequest.setHeader("Accept", "application/json");
                  postRequest.setHeader("Content-type", "application/json");
                  JSONObject jObj = new JSONObject();
                  try{
                      jObj.put("cnt", info.getDbTyp());
                     jObj.put("method", "initPrp");
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
            util.updAccessLog(req,res,"Module Prp", "save end");
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
                rtnPg=util.checkUserPageRights("masters/reportmodule.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Module Prp", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Module Prp", "init");
            }
            }
            return rtnPg;
         }
}
