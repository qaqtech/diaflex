    package ft.com.masters;

    import ft.com.DBMgr;
    import ft.com.DBUtil;
    import ft.com.FormsUtil;
    import ft.com.InfoMgr;
    import ft.com.dao.GenDAO;
    import ft.com.dao.UIForms;
    import ft.com.dao.UIFormsFields;

import ft.com.generic.GenericImpl;

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

    public class RapSyncPrpAction extends DispatchAction {
       
        private final String formName   = "rapSyncprpForm";


        public RapSyncPrpAction() {
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
                util.updAccessLog(req,res,"RapSync Prp form", "load start");
                HashMap  formFields = info.getFormFields(formName);

                if ((formFields == null) || (formFields.size() == 0)) {
                    formFields = util.getFormFields(formName);
                }

                UIForms  uiForms = (UIForms) formFields.get("DAOS");
                ArrayList  daos    = uiForms.getFields();
                FormsUtil helper  = new FormsUtil();
                ArrayList errors  = new ArrayList();
                helper.init(db, util, info);
            RapSyncMprpForm udf = (RapSyncMprpForm) af;
            ArrayList error = (ArrayList)req.getAttribute("errors");
            if(error!=null && error.size() >0){
                ArrayList errSizeList = (ArrayList)error.get(0);
                 if(errSizeList!=null && errSizeList.size() ==0){
                 
                     udf.reset();
                 }
            }else{
              udf.reset();
            }
            String mprp = req.getParameter("mprp");

            if (mprp == null) {
                mprp = (String) udf.getValue("mprp");
            }
            if (mprp == null) {
                mprp = (String) info.getValue("mprp");
            }
            ArrayList params = new ArrayList();
            params.add(mprp);
           

            info.setValue("mprp",mprp);

            String    srchFields = helper.getSrchFields(daos, "idn");
            String    srchQ      = " and mprp = ? ";
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
                    }
                }
            }

            req.setAttribute(formName, list);
            req.setAttribute("mprp", mprp);
            udf.setValue("mprp", mprp);
                util.updAccessLog(req,res,"RapSync Prp form", "load end");
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
                util.updAccessLog(req,res,"RapSync Prp form", "save start");
                HashMap  formFields = info.getFormFields(formName);

                if ((formFields == null) || (formFields.size() == 0)) {
                    formFields = util.getFormFields(formName);
                }

                UIForms  uiForms = (UIForms) formFields.get("DAOS");
                ArrayList  daos    = uiForms.getFields();
                FormsUtil helper  = new FormsUtil();
                ArrayList errors  = new ArrayList();
                helper.init(db, util, info);
            RapSyncMprpForm udf = (RapSyncMprpForm) af;
            ArrayList errorList = new ArrayList();
            int lmt = Integer.parseInt(util.nvl(uiForms.getREC(), "5"));

            if (udf.getAddnew() != null) {
              

                for (int i = 1; i <= lmt; i++) {
                    int    lIdn     = i;
                    boolean isValid = false;
                    ArrayList params   = new ArrayList();
                    String insIntoQ = " insert into RAP_SYNC_PRP_VALS (idn,mprp  ";
                    String insValQ  = " values(RAP_SYNC_PRP_VALS_SEQ.nextval,? ";
                    params.add(udf.getValue("mprp"));
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
                        int    ct  = db.execDirUpd(" Ins Menu", sql, params);
                    }
                    if(isValid){
                        errorList.add(errors);
                        errors = new ArrayList();
                    }
                }
            }

            if (udf.getModify() != null) {
               

                String    getMenu = "select " + helper.getSrchFields(daos, "idn", false) + " from RAP_SYNC_PRP_VALS where mprp=? ";
                ArrayList    params  = new ArrayList();
                params.add(udf.getValue("mprp"));
                ResultSet rs      = db.execSql("getUIFormFields for update", getMenu, params);
                int count = 0;
                while (rs.next()) {
                    String    lIdn  = rs.getString(1);
                    String val = rs.getString(2);
                    String updQ  = " update RAP_SYNC_PRP_VALS set  ";
                    String condQ = " where idn = ? and mprp=? and val=?" ;
                    boolean isValid = false;
                    String cnt = String.valueOf(count+1);
                    params = new ArrayList();

                    String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn));

                    if (isChecked.length() > 0) {
                     
                        // params.add(Integer.toString(menuIdn));
                        for (int j = 0; j < daos.size(); j++) {
                            UIFormsFields dao   = (UIFormsFields) daos.get(j);
                            String        lFld  = dao.getFORM_FLD();    // (String)flds.get(j);
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
                        params.add(udf.getValue("mprp"));
                        params.add(val);
                        int ct = db.execDirUpd(" upd menu " + lIdn, updQ + condQ, params);
                    }
                    if(isValid){
                        errorList.add(errors);
                        errors = new ArrayList();
                    }
                    count++;
                }
                rs.close();
            }

            req.setAttribute("errors", errorList);
//            util.initPrp();
                util.updAccessLog(req,res,"RapSync Prp form", "save end");
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
                    rtnPg=util.checkUserPageRights("masters/rapsyncmprp.do?","");
                    if(rtnPg.equals("unauthorized"))
                    util.updAccessLog(req,res,"RapSync Prp form", "Unauthorized Access");
                    else
                    util.updAccessLog(req,res,"RapSync Prp form", "init");
                }
                }
                return rtnPg;
             }
    }
