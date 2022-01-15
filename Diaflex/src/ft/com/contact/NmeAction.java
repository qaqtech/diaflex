package ft.com.contact;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.*;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.*;

import ft.com.masters.CountryFrm;

import java.io.IOException;

import java.net.InetSocketAddress;

import java.sql.PreparedStatement;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

import java.util.concurrent.Future;

import net.spy.memcached.MemcachedClient;

public class NmeAction extends DispatchAction {
    private final String formName   = "contact";
   
    public NmeAction() {
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
            util.updAccessLog(req,res,"Contact", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeFrm udf = (NmeFrm) af;
        String    lNmeIdn = util.nvl(udf.getNmeIdn(), "0");
        String    srch    = util.nvl((String) udf.getSearch());
        String    modify  = util.nvl((String) udf.getModify());
        String    addnew  = util.nvl((String) udf.getValue("btn"));
        String    view    = util.nvl((String) udf.getView());
        
      

        if (srch.length() > 0) {
            return search(am, af, req, res);
        } else if (view.length() > 0) {
            return load(am, af, req, res);
        } else {
            if (addnew.equals("add") ) {

                ArrayList outLst = db.execSqlLst("nmeSeq","select nme_seq.nextval from dual", new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                rs.next();
                lNmeIdn = rs.getString(1);
                rs.close(); pst.close();
                ArrayList params   = new ArrayList();
                String insIntoQ = " insert into mnme (nme_idn ";
                String insValQ  = "values(? ";
                params.add(lNmeIdn);
                for (int j = 0; j < daos.size(); j++) {
                    UIFormsFields dao   = (UIFormsFields) daos.get(j);
                    String        lFld  = dao.getFORM_FLD();
                    String        fldNm = lFld;
                    String        lVal  = (String) udf.getValue(fldNm);
                    String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));

                    String        lDsc = dao.getDSP_TTL();
                    if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                        String errorMsg = lDsc + " is  Required";

                        errors.add(errorMsg);
                        params.clear();

                        
                       
                    } else {
                        if(!lFld.equals("frm_dte") && !lFld.equals("nme_idn")){
                        String lTblFld = dao.getTBL_FLD().toLowerCase();    // util.nvl((String)formFields.get(lFld+"TF"));

                        insIntoQ += ", " + lTblFld;

                        if (dao.getFLD_TYP().equals("DT")) {
                            insValQ += ",to_date(?,'dd-mm-rrrr')";
                        } else {
                            insValQ += ",?";
                        }

                        params.add(lVal);
                        }
                    }
                }

                if (params.size() > 0 && errors.size()==0) {
                    String sql = insIntoQ + ") " + insValQ + ")";
                    int    ct  = db.execUpd(" Ins Menu", sql, params);
                }
            } else {
             

                String srchFields = getSrchFields(daos);
                String getMenu    = "select " + srchFields + " from mnme where nme_idn = ? ";
                ArrayList params     = new ArrayList();

                params.add(lNmeIdn);


                ArrayList outLst = db.execSqlLst(formName + " for update", getMenu, params);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                    int    lIdn  = rs.getInt(1);
                    String updQ  = " update mnme set nme_idn = nme_idn ";
                    String condQ = " where nme_idn = ? ";

                    params = new ArrayList();

                    String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn));

                    isChecked = "yes";

                    if (isChecked.length() > 0) {

                        // params.add(Integer.toString(menuIdn));
                        for (int j = 0; j < daos.size(); j++) {
                            UIFormsFields dao   = (UIFormsFields) daos.get(j);
                            String        lFld  = dao.getFORM_FLD();    // (String)flds.get(j);
                            String        fldNm = lFld;
                            String        lVal  = (String) udf.getValue(fldNm);
                            String        lReqd = dao.getREQ_YN();    // util.nvl((String)formFields.get(lFld+"REQ"));
                            String        lDsc = dao.getDSP_TTL();
                            if ((lReqd.equalsIgnoreCase("Y")) && (lVal.length() == 0) ) {
                                String errorMsg = lDsc + " is  Required";

                                errors.add(errorMsg);
                                params.clear();

                             
                            } else {
                                if(!lFld.equals("frm_dte") && !lFld.equals("nme_idn")){
                                String lTblFld = dao.getTBL_FLD().toLowerCase();    // util.nvl((String)formFields.get(lFld+"TF"));

                                if (dao.getFLD_TYP().equals("DT")) {
                                    updQ += ", " + lTblFld + " = to_date(?,'dd-mm-rrrr') ";
                                } else {
                                    updQ += ", " + lTblFld + " = ? ";
                                }

                                params.add(lVal);
                                }
                            }
                        }
                    }

                    if (params.size() > 0 && errors.size()==0) { 
                        params.add(Integer.toString(lIdn));
                       

                        int ct = db.execUpd(" upd menu " + lIdn, updQ + condQ, params);
                    }
                }
                rs.close(); pst.close();
            }
        }
       if(errors.size()==0)
        udf.setNmeIdn(lNmeIdn);
        util.SOP(errors.toString());
        req.setAttribute("errors", errors);
//       addParty(req,res);
                        util.updAccessLog(req,res,"Contact", "save end");
            HashMap dbmsSysInfo = info.getDmbsInfoLst();
            String isSalForce =util.nvl((String)dbmsSysInfo.get("SLFORCE"));
            try {
                if (isSalForce.equals("YES")) {
                    SaleForce saleForce = new SaleForce();
                    saleForce.init(db, util, info);
                    saleForce.UpdateContact(req,lNmeIdn);
                }
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
            }
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
            util.updAccessLog(req,res,"Contact", "delete start");
        NmeFrm udf = (NmeFrm) af;

        String delIdn = req.getParameter("nmeIdn");
        String delQ   = " update mnme set vld_dte = sysdate where nme_idn = ? ";
        ArrayList params = new ArrayList();
        params.add(delIdn);
        int cnt = db.execUpd(" Del " + delIdn, delQ, params);
         String delNmeRel = "update nmerel set vld_dte = sysdate where nme_idn = ? ";
        cnt = db.execUpd("delNmeRel", delNmeRel, params);
        String delWebUsr = "update web_usrs a set to_dt = sysdate where exists (select 1 from nmerel b where a.rln_idn = b.nmerel_idn and b.nme_idn =?) " +
                           " and to_dt is null ";
        cnt = db.execUpd("delWebUsr", delWebUsr, params);
            util.updAccessLog(req,res,"Contact", "delete end");
        return search(am, af, req, res);
        }
    }

    public ActionForward search(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Contact", "search start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeFrm udf = (NmeFrm) af;

        HashMap tbls       = new HashMap();
        ArrayList    params     = new ArrayList();
        String    srchQ      = "",
                  srchFields = "";

        for (int i = 0; i < daos.size(); i++) {
            UIFormsFields dao    = (UIFormsFields) daos.get(i);
            String        lFld   = dao.getFORM_FLD();
            String        fldTyp = dao.getFLD_TYP();
            String        tblNm  = dao.getTBL_NME();

            // tbls.put(tblNm, tbls.size());
            // String tblFld = tblNm + "." + dao.getTBL_FLD().toLowerCase();
            String tblFld = dao.getTBL_FLD().toLowerCase();
            String usrVal = util.nvl((String) udf.getValue(lFld), "NA");

            // util.SOP(tblFld + " : " + usrVal);
            if ((usrVal.equals("0")) || (usrVal.equals(""))) {
                usrVal = "NA";
            }

            if (!(usrVal.equals("NA"))) {
                srchQ += " and upper(" + tblFld + ")  like upper(?) ";
                params.add("%" + usrVal + "%");
            }

            // srchFields += ", " + tblFld   ;
        }

        srchFields = getSrchFields(daos);
        srchQ      += " and vld_dte is null ";

        ArrayList list = getSrchList(srchFields, srchQ, params, daos, "SRCH",uiForms,req,udf);

        info.setNmeSrchList(list);
        
        ArrayList nonUsrList = new ArrayList();
        ArrayList outLst = db.execSqlLst("webusr", "select nme_idn from gt_nme_srch where flg='N'", new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            nonUsrList.add(rs.getString("nme_idn"));
        }
        req.setAttribute("NonUsrList", nonUsrList);
        rs.close(); pst.close();

//            HashMap contViewMap = (HashMap)session.getAttribute("ContViewMap");
//            if(contViewMap==null){
//                try {
//                HashMap dbinfo = info.getDmbsInfoLst();
//                String cnt=util.nvl((String)dbinfo.get("CNT"));
//                String mem_ip=util.nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//                int mem_port=Integer.parseInt(util.nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//                MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//                contViewMap=(HashMap)mcc.get(cnt+"_contViewMap");
//                if(contViewMap==null){
//                contViewMap=new HashMap();
//            String contVw =  " select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + 
//                             " b.mdl = 'JFLEX' and b.nme_rule = 'CONT_VW' and a.til_dte is null order by a.srt_fr ";
//            outLst = db.execSqlLst("contView", contVw, new ArrayList());
//             pst = (PreparedStatement)outLst.get(0);
//             rs = (ResultSet)outLst.get(1);
//            while(rs.next()){
//                contViewMap.put(util.nvl(rs.getString("dsc")), util.nvl(rs.getString("chr_fr")));
//            }
//                rs.close(); pst.close();
//                pst.close();
//                rs.close(); pst.close();
//                Future fo = mcc.delete(cnt+"_contViewMap");
//                System.out.println("add status:_contViewMap" + fo.get());
//                fo = mcc.set(cnt+"_contViewMap", 24*60*60, contViewMap);
//                System.out.println("add status:_contViewMap" + fo.get());
//                }
//                mcc.shutdown();
//                session.setAttribute("ContViewMap", contViewMap);
//                }catch(Exception ex){
//                 System.out.println( ex.getMessage() );
//                }
//            }
            util.updAccessLog(req,res,"Contact", "search end");
        return am.findForward("searchlst");
        }
    }

    // loadSearch
    public ActionForward loadSearch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Contact", "loadSearch start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeFrm udf = (NmeFrm) af;

        HashMap tbls       = new HashMap();
        ArrayList    params     = new ArrayList();
        String    srchQ      = " and exists (select 1 from gt_nme_srch where mnme.nme_idn = gt_nme_srch.nme_idn) ",
                  srchFields = "";

        srchFields = getSrchFields(daos);
        srchQ      += " and vld_dte is null ";

        ArrayList list = getSrchList(srchFields, srchQ, params, daos, "LOADSRCH",uiForms,req,udf);

        info.setNmeSrchList(list);
      
        ArrayList nonUsrList = new ArrayList();
        ArrayList outLst = db.execSqlLst("webusr", "select nme_idn from gt_nme_srch where flg='N'", new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            nonUsrList.add(rs.getString("nme_idn"));
        }
        req.setAttribute("NonUsrList", nonUsrList);
        rs.close(); pst.close();

//            HashMap contViewMap = (HashMap)session.getAttribute("ContViewMap");
//            if(contViewMap==null){
//                try {
//                HashMap dbinfo = info.getDmbsInfoLst();
//                String cnt=util.nvl((String)dbinfo.get("CNT"));
//                String mem_ip=util.nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//                int mem_port=Integer.parseInt(util.nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//                MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//                contViewMap=(HashMap)mcc.get(cnt+"_contViewMap");
//                if(contViewMap==null){
//                contViewMap=new HashMap();
//            String contVw =  " select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + 
//                             " b.mdl = 'JFLEX' and b.nme_rule = 'CONT_VW' and a.til_dte is null order by a.srt_fr ";
//            outLst = db.execSqlLst("contView", contVw, new ArrayList());
//             pst = (PreparedStatement)outLst.get(0);
//             rs = (ResultSet)outLst.get(1);
//            while(rs.next()){
//                contViewMap.put(util.nvl(rs.getString("dsc")), util.nvl(rs.getString("chr_fr")));
//            }
//                rs.close(); pst.close();
//                pst.close();
//                rs.close(); pst.close();
//                Future fo = mcc.delete(cnt+"_contViewMap");
//                System.out.println("add status:_contViewMap" + fo.get());
//                fo = mcc.set(cnt+"_contViewMap", 24*60*60, contViewMap);
//                System.out.println("add status:_contViewMap" + fo.get());
//                }
//                mcc.shutdown();
//                session.setAttribute("ContViewMap", contViewMap);
//                }catch(Exception ex){
//                 System.out.println( ex.getMessage() );
//                }
//            }
        info.setNmemassmaillist(new ArrayList());

        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_SRCH");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("CONTACT_SRCH");
        allPageDtl.put("CONTACT_SRCH",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Contact", "loadSearch end");
        return am.findForward("searchlst");
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
            util.updAccessLog(req,res,"Contact", "load start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
        NmeFrm udf = (NmeFrm) af;
        String srchQ      = "";
        ArrayList params     = new ArrayList();
        String lNmeIdn = util.nvl(req.getParameter("nmeIdn"));
        String frompa = util.nvl(req.getParameter("frompa"));
        if(frompa.equals(""))
        frompa=util.nvl((String)udf.getValue("frompa"));
        req.setAttribute("frompa", frompa);
        udf.setValue("frompa", frompa);
        if(!lNmeIdn.equals("")){
                String fromFeedback = util.nvl(req.getParameter("fromFeedback"));
                if(fromFeedback.equals("")){
                String rtnPggt=util.checkNmeIdngt_nme_srch(lNmeIdn);
                if(rtnPggt.equals("N"))
                return am.findForward("unauthorized");
                }
        }
        if(lNmeIdn.equals(""))
        lNmeIdn    = util.nvl(udf.getNmeIdn());
        String srchFields = getSrchFields(daos);
        if(!lNmeIdn.equals("")){
         srchQ      = " and nme_idn = ? ";
         params.add(lNmeIdn);
         udf.setValue("nmeIdn",lNmeIdn);
         req.setAttribute("upd","update");
         req.setAttribute("nmeIdn", lNmeIdn);
        
        
        String sqlTyp = "select typ from mnme where 1=1 "+srchQ;
            ArrayList outLst = db.execSqlLst("sqlTyp", sqlTyp, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            req.setAttribute("typ",rs.getString("typ"));
        }
            rs.close(); pst.close();
        

        

        ArrayList list = helper.getSrchList(uiForms, "", srchFields, srchQ, params, daos, "VIEW");
        if(!lNmeIdn.equals("")){
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

                  

                    String fldAlias = util.nvl(dao.getALIAS());
                    String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);

                    udf.setValue(lFrmFld, util.nvl((String) gen.getValue(lFld)));
                    if(aliasFld.length() > 0){
                        udf.setValue(aliasFld,util.nvl((String) gen.getValue(aliasFld)));
                    }
                }
            }
        }}
            req.setAttribute(formName, list);
        }
        // udf.setList(list);
        // info.setNmeSrchList(list);
    
        
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_FORM");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("CONTACT_FORM");
        allPageDtl.put("CONTACT_FORM",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Contact", "load end");
        return am.findForward("load");
        }
    }

    public ActionForward reset(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Contact", "reset start");
        NmeFrm udf = (NmeFrm) af;
        udf.reset();
            util.updAccessLog(req,res,"Contact", "reset end");
        return am.findForward("reset");
        }
    }

    public ArrayList getSrchList(String srchFields, String srchQ, ArrayList params, ArrayList daos, String typ,UIForms  uiForms,HttpServletRequest req,NmeFrm udf) {
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
        
            String getDataQ = " select nme_idn " + srchFields + " from mnme where 1 =1 " + srchQ
                              + " order by fnme, mnme, lnme ";

            ArrayList outLst = db.execSqlLst(" get Search data", getDataQ, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                MNme   nme    = new MNme();
                String nmeIdn = rs.getString(1);

                nme.setIdn(nmeIdn);

                // nme.setValue("idn", nmeIdn);
                for (int i = 0; i < daos.size(); i++) {
                    UIFormsFields dao    = (UIFormsFields) daos.get(i);
                    String        tblFld = dao.getTBL_FLD().toLowerCase();
                    String        lFld   = dao.getFORM_FLD();
                    String        dbVal  = util.nvl(rs.getString(tblFld));
                    String fldAlias = util.nvl(dao.getALIAS());
                    String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);
                    if(aliasFld.length() > 0){
                        udf.setValue(aliasFld, util.nvl(rs.getString(aliasFld)));
                        nme.setValue(aliasFld, util.nvl(rs.getString(aliasFld)));                     

                    }
                                
                    if (typ.equalsIgnoreCase("VIEW")) {
                        udf.setValue(lFld, dbVal);
                    }

                    nme.setValue(lFld, dbVal);
                }

                list.add(nme);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }

        return list;
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
                util.updAccessLog(req,res,"Contact", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Contact", "init");
            }
            }
            return rtnPg;
         }

    public String getSrchFields(ArrayList daos) {
        String srchFields = " nme_idn ";
        JspUtil util=new JspUtil();
        ArrayList ukFld = new ArrayList();

        for (int j = 0; j < daos.size(); j++) {
            UIFormsFields dao     = (UIFormsFields) daos.get(j);
            String        lTblFld = dao.getTBL_FLD().toLowerCase();
            String        fldTyp  = dao.getFLD_TYP();

            if (fldTyp.equalsIgnoreCase("DT")) {
                lTblFld = "to_char(" + lTblFld + ", 'dd-mm-rrrr') " + lTblFld;
            }

            if (util.nvl(dao.getFLG()).equals("UK")) {
                ukFld.add(lTblFld);
            }

            String delim = ", ";

            /*
             * if (j==0) {
             *   delim = "";
             * }
             */
            
            srchFields += delim + lTblFld;
            if (util.nvl(dao.getALIAS()).length() > 0) {
                srchFields += delim + dao.getALIAS();
            }
        }

        return srchFields;
    }
    
    
    public void addParty(HttpServletRequest req , HttpServletResponse res){
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
        String party = "select nme_idn, nme from nme_v a " +
            " where exists (select 1 from nmerel b where a.nme_idn = b.nme_idn and b.vld_dte is null and flg = 'CNF') order by nme";
        ArrayList partyList = new ArrayList();

            ArrayList outLst = db.execSqlLst("byr", party, new ArrayList());
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
            session.setAttribute("partyList", partyList);
        }
      
    
    
}


//~ Formatted by Jindent --- http://www.jindent.com
