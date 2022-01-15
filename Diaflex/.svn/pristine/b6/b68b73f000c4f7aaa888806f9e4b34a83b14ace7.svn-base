package ft.com.rough;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.mpur.PurchaseDtlAction;
import ft.com.rough.RoughPurchaseDtlForm;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class RoughPurchaseDtlAction extends DispatchAction {
    private final String formName   = "roughPurDtlForm";

    public RoughPurchaseDtlAction() {
        super();
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
                rtnPg=util.checkUserPageRights("",util.getFullURL(req));
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Purchase Form", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Purchase Form", "init");
            }
            }
            return rtnPg;
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
            util.updAccessLog(req,res,"Purchase Form", "load start");
            HashMap  formFields = info.getFormFields(formName);
            HashMap avgDtl = new HashMap();
            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        RoughPurchaseDtlForm udf = (RoughPurchaseDtlForm) af;
      
        
        String purIdn = req.getParameter("purIdn");
       String lotDsc = req.getParameter("lotDsc");
        if (purIdn == null) {
            purIdn = (String)udf.getValue("purIdn");
        }
        if (purIdn == null) {
            purIdn = (String)info.getValue("purIdn");
        }
            if(lotDsc==null){
                lotDsc = (String)udf.getValue("lotDsc");
            }
        udf.reset();
        ArrayList params = new ArrayList();
       
        params.add(purIdn);
        info.setValue("purIdn", purIdn);
        String    srchFields = helper.getSrchFields(daos, "pur_dtl_idn");
        String    srchQ      = " and pur_idn = ? ";
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
       
       
        udf.setValue("purIdn", purIdn);
        udf.setValue("lotDsc", lotDsc);
        req.setAttribute("avgDtl", avgDtl);
            req.setAttribute("lotDsc", lotDsc);
            String lastCnt ="select rgh_pkg.get_lot_vnm('"+lotDsc+"', '_', 'PUR') lotNum from dual";
           ResultSet  rs = db.execSql("lastCnt", lastCnt, new ArrayList());
             while(rs.next()){
                 req.setAttribute("lstCnt", rs.getInt("lotNum"));
             }
            rs.close();
        String ttlcts="";
            
            String ttlCtsSql ="select a.TTL_CTS  from mpur a\n" + 
             "where a.pur_idn=?";
            params = new ArrayList();
            params.add(purIdn);
            rs = db.execDirSql("ttlCts", ttlCtsSql, params);
            while(rs.next()){
                ttlcts = rs.getString("TTL_CTS");
            }
            rs.close();
        String calCts ="";
            
        String CtsSql ="select to_char(sum(b.CTS),'99999999999990.00') calCts from mpur a, pur_dtl b\n" + 
        "where a.PUR_IDN=b.PUR_IDN\n" + 
        "and a.pur_idn=? and  b.stt not in ('IA')  and nvl(b.flg, 'NA') <> 'TRF' ";
        params = new ArrayList();
        params.add(purIdn);
        rs = db.execDirSql("ttlCts", CtsSql, params);
        while(rs.next()){
            calCts = rs.getString("calCts");
        }
            rs.close();
            req.setAttribute("ttlCts", ttlcts);
            req.setAttribute("calCts", calCts);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("PUR_DTL");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("PUR_DTL");
        allPageDtl.put("PUR_DTL",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Purchase Form", "load end");
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
            util.updAccessLog(req,res,"Purchase Form", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }
            String cnt=util.nvl((String)info.getDmbsInfoLst().get("CNT")).toUpperCase();

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        RoughPurchaseDtlForm udf = (RoughPurchaseDtlForm) af;
        ArrayList errorList = new ArrayList();
        int    lmt        = Integer.parseInt(util.nvl(uiForms.getREC(), "5"));
        String purIdn = (String) udf.getValue("purIdn");
        String  lotDsc = (String)udf.getValue("lotDsc");
            String vnm=lotDsc+"_0";
        if (udf.getAddnew() != null) {
           
            for (int i = 1; i <= lmt; i++) {
                int    lIdn     = i;
                String purDtlSeq="";
              
                boolean isValid = false;
                String getLogId = " select pur_dtl_seq.nextval from dual " ;
                ResultSet rs = db.execSql(" Log id ", getLogId, new ArrayList());
                if(rs.next()) {
                    purDtlSeq = rs.getString(1);
                  }
                    rs.close();
                ArrayList params   = new ArrayList();
                String insIntoQ = " insert into pur_dtl ( pur_dtl_idn, pur_idn ";
                String insValQ  = "values(? ,?  ";
                String lrte="";
                params.add(purDtlSeq);
                params.add(purIdn);

                for (int j = 0; j < daos.size(); j++) {
                    UIFormsFields dao   = (UIFormsFields) daos.get(j);
                    String        lFld  = dao.getFORM_FLD();
                    String        fldNm = lFld + "_" + lIdn;
                    String        lVal  = util.nvl((String) udf.getValue(fldNm));
                    String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));
                    if(lFld.equals("rte"))
                        lrte=lVal;
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
                        if(ct>0){
                        if((cnt.indexOf("LK")!=-1 || cnt.indexOf("LIF")!=-1) && !lrte.equals("")){
                            params=new ArrayList();
                            params.add(purIdn);
                            params.add(purDtlSeq);
                            params.add(lrte);
                          ct = db.execCall("cp update", "DP_APPLY_PUR_PKT_CP(pPurIdn => ? , pPurDtlIdn => ? , pPurRte => ?)", params);

                        }}
                    }
                    
                    if(isValid){
                        errorList.add(errors);
                        errors = new ArrayList();
                    }
            }
        }

        if (udf.getModify() != null) {
            

            String srchFields = helper.getSrchFields(daos, "pur_dtl_idn", false);
            String srchQ      = " and pur_idn = ? ";

            // ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ, params, daos, "VIEW");
            String getMenu    = "select " + srchFields + " from pur_dtl where 1 = 1 " + srchQ + " order by srt";
            ArrayList params     = new ArrayList();

            params.add(purIdn);

            ResultSet rs = db.execSql("getUIFormFields for update", getMenu, params);

            while (rs.next()) {
                int    lIdn  = rs.getInt(1);
                String updQ  = " update pur_dtl set pur_dtl_idn = pur_dtl_idn ";
                String condQ = " where pur_dtl_idn = ? ";
                boolean isValid = false;
                params = new ArrayList();
                String lrte="";
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
                        if(lFld.equals("rte"))
                            lrte=lVal;
                        if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                            String errorMsg = lIdn + "-" + lDsc + " Required.";

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

                    if((cnt.indexOf("LK")!=-1 || cnt.indexOf("LIF")!=-1) && !lrte.equals("")){
                        params=new ArrayList();
                        params.add(purIdn);
                        params.add(Integer.toString(lIdn));
                        params.add(lrte);
                      ct = db.execCall("cp update", "DP_APPLY_PUR_PKT_CP(pPurIdn => ? , pPurDtlIdn => ? , pPurRte => ?)", params);

                    }
                }
                
                if(isValid){
                    errorList.add(errors);
                    errors = new ArrayList();
                }
            }
            rs.close();
        }
        
        if(udf.getTfrToMkt()!=null){
            String srchFields = helper.getSrchFields(daos, "pur_dtl_idn", false);
            String srchQ      = " and pur_idn = ? ";

            // ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ, params, daos, "VIEW");
            String getMenu    = "select " + srchFields + " from pur_dtl where 1 = 1 " + srchQ + " order by 1";
            ArrayList params     = new ArrayList();

            params.add(purIdn);

            ResultSet rs = db.execSql("getUIFormFields for update", getMenu, params);
            String trfStkidn ="";
            while (rs.next()) {
                int    lIdn  = rs.getInt(1);
                params = new ArrayList();
                String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn));
                if (isChecked.length() > 0) {
                   
                    params = new ArrayList();
                    params.add(String.valueOf(lIdn));
                    ArrayList out = new ArrayList();
                    out.add("I");
                    out.add("V");
                    CallableStatement cst = db.execCall("TrfToStk","PUR_PKG.TRF_TO_STK(pPurDtlIdn => ?, pStkIdn => ?,  pMsg => ?)", params ,out);
                    //                        stkidn = cst.getInt(2);
                    String newVnm= cst.getString(params.size()+1); 
                    String msg= cst.getString(params.size()+2); 
                    trfStkidn=trfStkidn+","+newVnm;
                  cst.close();
                  cst=null;
                 req.setAttribute("msg", "Transfer with new Ref ID : "+trfStkidn);
                }
            }
            rs.close();
        }
        req.setAttribute("errors", errorList);
            util.updAccessLog(req,res,"Purchase Form", "save end");
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
            util.updAccessLog(req,res,"Purchase Form", "delete start");
        RoughPurchaseDtlForm udf = (RoughPurchaseDtlForm) af;
        ArrayList ary = new ArrayList();
        String purDtlIdn = req.getParameter("purDtlIdn");
        String deleteCity = "delete pur_prp where pur_dtl_idn= ?";
        ary = new ArrayList();
        ary.add(purDtlIdn);
        int ct = db.execUpd("deleteCity", deleteCity, ary);
        deleteCity = "delete pur_dtl where pur_dtl_idn= ?";
        ary = new ArrayList();
        ary.add(purDtlIdn);
        ct = db.execUpd("deleteCity", deleteCity, ary);
        util.updAccessLog(req,res,"Purchase Form", "delete end");
        return load(am, af, req, res);
        }
        
    }
    public ActionForward loadPrp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Purchase Form", "loadprp start");
        RoughPurchaseDtlForm udf = (RoughPurchaseDtlForm) af;
            GenericInterface genericInt = new GenericImpl();
            genericInt.genericPrprVw(req, res, "purPrpLst", "PUR_PRP_UPD");
        String purIdn = util.nvl(req.getParameter("purIdn"));
        String purDtlIdn = util.nvl(req.getParameter("purDtlIdn"));
        if(purIdn.equals("")){
            purIdn = util.nvl((String)udf.getValue("purIdn"));
            purDtlIdn =util.nvl((String)udf.getValue("purDtlIdn"));
        }
        String sql = " select a.mprp , decode(b.dta_typ, 'C', a.val, 'N', to_char(a.num), 'D', to_char(a.dte, 'dd-Mon-rrrr'), a.txt)  val "+ 
                     " from pur_prp a , mprp b , rep_prp c "+
                     " where pur_idn = ? and pur_dtl_idn=?  and a.mprp = b.prp  and b.prp = c.prp and c.prp = a.mprp and c.mdl='PUR_PRP_UPD' "+ 
                     " and c.flg='Y' order by rnk ";

        ArrayList ary = new ArrayList();
        ary.add(purIdn);
        ary.add(purDtlIdn);
        ResultSet rs = db.execSql("sql", sql, ary);
        while(rs.next()){
            udf.setValue(util.nvl(rs.getString("mprp")), util.nvl(rs.getString("val")));
        }
        rs.close();            
        udf.setValue("purIdn", purIdn);
        udf.setValue("purDtlIdn", purDtlIdn);
            util.updAccessLog(req,res,"Purchase Form", "loadprp end");
        return am.findForward("loadPrp");
        }
    }
    
    public ActionForward addPrp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Purchase Form", "addprp start");
        RoughPurchaseDtlForm udf = (RoughPurchaseDtlForm) af;
        ArrayList purPrpList = (ArrayList)session.getAttribute("purPrpLst");
        String purIdn = util.nvl((String)udf.getValue("purIdn"));
        String purDtlIdn =util.nvl((String)udf.getValue("purDtlIdn"));
        for(int i=0; i < purPrpList.size() ; i++){
            String lprp = (String)purPrpList.get(i);
            String prpVal = util.nvl((String)udf.getValue(lprp));
            if(!prpVal.equals("")){
                ArrayList ary = new ArrayList();
                ary.add(purIdn);
                ary.add(purDtlIdn);
                ary.add(lprp);
                ary.add(prpVal);
               int ct = db.execCall("updatePrp","PUR_PKG.PKT_PRP_UPD(pPurIdn => ?, pPurDtlIdn => ?, pPRP => ?, pVal => ?)", ary);
            if(ct>0)
                req.setAttribute("msg", "Propeties Get update successfully...");
            }
        }
            util.updAccessLog(req,res,"Purchase Form", "addprp end");
        return loadPrp(am, af, req, res);
        }
    }
    
    public ActionForward loadbulkPrp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Pur Bulk Update", "loadbulkPrp Start");
        RoughPurchaseDtlForm udf = (RoughPurchaseDtlForm) af;
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("PUR_BULK_PRP_UPD");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("PUR_BULK_PRP_UPD");
        allPageDtl.put("PUR_BULK_PRP_UPD",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Pur Bulk Update", "loadbulkPrp end");
        return am.findForward("loadbulkPrp");
        }
    }
    
    public ActionForward updatePrp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        RoughPurchaseDtlForm udf = (RoughPurchaseDtlForm) af;
        GenericInterface genericInt = new GenericImpl();
        util.updAccessLog(req,res,"Pur Bulk Update", "updatePrp start");
        int ct = db.execCall("delete gt", "delete from Gt_Gia_File_Trf", new ArrayList());
        ct = db.execCall("delete gt_pkt_scan", "delete from gt_pkt_scan", new ArrayList());
        ArrayList ary = new ArrayList();
        String lprp = util.nvl((String)udf.getValue("lprp"));
        String vnm = util.nvl((String)udf.getValue("vnmLst"));
        String val = util.nvl((String)udf.getValue("prpVal"));
        String deletepkt = util.nvl((String)udf.getValue("deletepkt"));
        HashMap dbinfo = info.getDmbsInfoLst();
        int cnt = 0;
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "ascallSru", "CALL_SRU_VW");
        HashMap mprp = info.getMprp();
        if(deletepkt.equals("")){
        String msg = "";
        ArrayList RtnMsg = new ArrayList();
        ArrayList RtnMsgList = new ArrayList();
        String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
        if(!vnm.equals("") && !val.equals("") ){
        vnm = util.getVnm(vnm);
        if(prpTyp.equals("T"))
        val = util.getValNline(val);
        else
        val = util.getVal(val);
    
       ArrayList vnmList = new ArrayList();
       vnm = vnm.substring(1, vnm.length()-1);
       String[] vnmStr = vnm.split("','");
      
       
        ArrayList valList = new ArrayList();
        val = val.substring(1, val.length()-1);
        String[] valStr =null;
        if(prpTyp.equals("T"))
         valStr = val.split("'#'");
        else
         valStr = val.split("','");
        if(vnmStr.length==valStr.length){
        
            for(int i=0;i<vnmStr.length;i++){
                vnm = vnmStr[i];
               vnm = vnm.replaceAll(",", "");
               vnm = vnm.replaceAll("'", "");
              
             vnmList.add(vnm);
            }
        
            for(int i=0;i<valStr.length;i++){
                val = valStr[i];
                if(!prpTyp.equals("T")){
              val=val.replaceAll(",", "");
              val=val.replaceAll("'", "");
                }
              val = val.trim();
               valList.add(val);
            }
        
        for(int k=0 ;k<vnmList.size();k++){
          String insertGt = "Insert Into Gt_Gia_File_Trf(Cert_No,Lab,Mprp,Val)"+
              "Select ?,'GIA',?,? From Dual";
                   ary = new ArrayList();
                   ary.add(vnmList.get(k));
                   ary.add(lprp);
                   ary.add(valList.get(k));
                   ct = db.execDirUpd("insert Gt_Gia_File_Trf", insertGt, ary);
        }
        if(ct>0){
            ary = new ArrayList();
            ary.add("PUR");
            ArrayList out = new ArrayList();
            out.add("I");
            out.add("V");
            CallableStatement cst = null;
            cst = db.execCall(
                "PUR_PKG ",
                "PUR_PKG.BLK_UPD(pTyp => ?, pCnt => ?, pMsg => ?)", ary, out);
            cnt = cst.getInt(ary.size()+1);
            msg = cst.getString(ary.size()+2);
          cst.close();
          cst=null;
            RtnMsg.add(cnt);
            RtnMsg.add(msg);
            RtnMsgList.add(RtnMsg); 
            req.setAttribute("msgList",RtnMsgList);
            udf.reset();
        }else{
            req.setAttribute("msg","Update Process failed..");
        }
        }else{
            req.setAttribute("msg","Please check Packets and there corresponding  Values. ");
        }
        }else{
          req.setAttribute("msg","Please Specify Packets. ");
        }
        }else{
            ary=new ArrayList();
            vnm = util.getVnm(vnm);
            vnm = vnm.replaceAll("'", "");
            if(!vnm.equals("")){
            vnm="'"+vnm+"'";
            String insScanPkt = " insert into gt_pkt_scan select * from TABLE(PARSE_TO_TBL("+vnm+"))";
            ct = db.execDirUpd(" ins scan", insScanPkt,ary);
            System.out.println(insScanPkt);
            }
            ary=new ArrayList();
            ArrayList out = new ArrayList();
            out.add("V");
            CallableStatement cst = db.execCall("purTrf","pur_pkg.DEL_UPL_TRNS_PKT(pMsg => ?)", ary, out);
            String msg = cst.getString(ary.size()+1);
            req.setAttribute("msg", msg);
          cst.close();
          cst=null;
            
        }
            util.updAccessLog(req,res,"Pur Bulk Update", "updatePrp end");
        return am.findForward("loadbulkPrp");
        }
    } 
    public ActionForward LotIdnGen(ActionMapping mapping, ActionForm form,
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
   
       String miner = util.nvl(req.getParameter("miner"));
       ArrayList params = new ArrayList();
       params.add(miner);
       ArrayList out = new ArrayList();
       out.add("V");
       String lotNoGen = "RGH_PKG.GEN_LOT_NO(pMiner => ?, pLot => ?)";
       CallableStatement cst = db.execCall("lotNoGen",lotNoGen, params, out);
       String lotNo = cst.getString(params.size()+1);
         cst.close();
         cst=null;
       response.setContentType("text/xml"); 
       response.setHeader("Cache-Control", "no-cache"); 
       response.getWriter().write("<lotno>"+lotNo+"</lotno>");
       return null;
       }
    
}
 

