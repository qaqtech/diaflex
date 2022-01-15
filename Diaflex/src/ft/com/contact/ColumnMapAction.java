package ft.com.contact;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;
import ft.com.fileupload.FileUploadForm;
import ft.com.fileupload.FileUploadInterface;

import ft.com.masters.MprpForm;

import ft.com.masters.PrpForm;

import java.io.IOException;

import java.io.PrintWriter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ColumnMapAction extends DispatchAction {
    private final String formName   = "prpwebmap";


    
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
            util.updAccessLog(req,res,"Column Mapping", "load start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
         ColumnMapForm udf = (ColumnMapForm) form;
       
          udf.reset();
        
        String srchFields = helper.getSrchFields(daos, "mprp");
        String nmeIdn = util.nvl(req.getParameter("nmeIdn"));
        String srchQ      = " and mprp=? and name_id=?";
        ArrayList ary = new ArrayList();
        ary.add("DFCOL");
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
                    req.setAttribute(lFrmFld, util.nvl((String) gen.getValue(lFld)));
                    String fldAlias = util.nvl(dao.getALIAS());
                    String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);

                    udf.setValue(lFrmFld, util.nvl((String) gen.getValue(lFld)));
                    udf.setValue(lFrmFld + "_dsp", util.nvl((String) gen.getValue(aliasFld)));
                }
            }
        }

        // udf = (CountryFrm)helper.getForm(uiForms, "", srchFields, srchQ, new ArrayList(), daos, "VIEW") ;
        req.setAttribute(formName, list);
        req.setAttribute("namIdn", nmeIdn);
        udf.setValue("namIdn", nmeIdn);
        // udf.setList(list);
        // info.setNmeSrchList(list);
        util.SOP(" NmeSearch Count : " + list.size());
            util.updAccessLog(req,res,"Column Mapping", "load end");
        return am.findForward("view");
        }
    }
    
    public ColumnMapAction() {
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
            util.updAccessLog(req,res,"Column Mapping", "save start");
            HashMap  formFields = info.getFormFields(formName);

            if ((formFields == null) || (formFields.size() == 0)) {
                formFields = util.getFormFields(formName);
            }

            UIForms  uiForms = (UIForms) formFields.get("DAOS");
            ArrayList  daos    = uiForms.getFields();
            FormsUtil helper  = new FormsUtil();
            ArrayList errors  = new ArrayList();
            helper.init(db, util, info);
         ColumnMapForm udf = (ColumnMapForm) af;
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        ArrayList dfPrpVal = (ArrayList)prp.get("DFCOLV");
        ArrayList dfPrpSrt = (ArrayList)prp.get("DFCOLS");
        ArrayList errorList = new ArrayList();
        String nmeIdn = util.nvl(req.getParameter("nmeIdn"));
        int lmt = Integer.parseInt(util.nvl(uiForms.getREC(), "5"));
        udf.setValue("namIdn", nmeIdn);
        req.setAttribute("namIdn", nmeIdn);
        if (udf.getAddnew() != null) {
         

            for (int i = 1; i <= lmt; i++) {
                int    lIdn     = i;
                boolean isValid = false;
                String lsrt = "";
             
                ArrayList params   = new ArrayList();
                String insIntoQ = " insert into prp_map_web ( mprp , name_id , ";
                String insValQ  = " values(?, ? ,";
                params.add("DFCOL");
                params.add(nmeIdn);
                for (int j = 0; j < daos.size(); j++) {
                    UIFormsFields dao   = (UIFormsFields) daos.get(j);
                    String        lFld  = dao.getFORM_FLD();
                    String        fldNm = lFld + "_" + lIdn;
                    String        lVal  = util.nvl((String) udf.getValue(fldNm));
                    String        lReqd = dao.getREQ_YN();  
                    String        lDsc = dao.getDSP_TTL();
                    // util.nvl((String)formFields.get(lFld+"REQ"));
                    if(j==0 && !lVal.equals("")){
                      
                        lsrt = (String)dfPrpSrt.get(dfPrpVal.indexOf(lVal));
                    }
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
            params.add("DFCOL");
            params.add(nmeIdn);
            ArrayList outLst = db.execSqlLst("getUIFormFields for update", getMenu, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            int count = 0;
            while (rs.next()) {
                String    lIdn  = rs.getString(1);
                String colP = rs.getString(2);
                String lprp = rs.getString(3);
                String updQ  = " update prp_map_web set ";
                String condQ = " where mprp=? and name_id=? and val=? and idx=?";
                boolean isValid = false;
                
                String cnt = String.valueOf(count+1);
                params = new ArrayList();
                
                String lsrt = "";
                String isChecked = util.nvl((String) udf.getValue("upd_" + lIdn+"_"+cnt));

                if (isChecked.length() > 0) {

                    // params.add(Integer.toString(menuIdn));
                    for (int j = 0; j < daos.size(); j++) {
                        UIFormsFields dao   = (UIFormsFields) daos.get(j);
                        String        lFld  = dao.getFORM_FLD();    // (String)flds.get(j);
                        String        fldNm = lFld + "_" + lIdn+"_"+cnt;
                        String        lVal  = util.nvl((String) udf.getValue(fldNm));
                        String        lReqd = dao.getREQ_YN();  
                        String        lDsc = dao.getDSP_TTL();
                        // util.nvl((String)formFields.get(lFld+"REQ"));
                        if(j==0 && !lVal.equals("")){
                          
                            lsrt = (String)dfPrpSrt.get(dfPrpVal.indexOf(lVal));
                        }
                        if ((lReqd.equalsIgnoreCase("Y")) && ((lVal.length() == 0) || (lVal.equals("0")))) {
                            String errorMsg = lIdn + "-" + lDsc + ", Required.";

                            errors.add(errorMsg);
                            params.clear();
                        } else {
                            String lTblFld = dao.getTBL_FLD().toLowerCase();    // util.nvl((String)formFields.get(lFld+"TF"));
                            if(j==0)
                                updQ += lTblFld + " = ? ";
                            else
                                updQ += ", " + lTblFld + " = ? ";
                            params.add(lVal);
                            if(lReqd.equalsIgnoreCase("Y"))
                            isValid = true;
                        }
                    }
                }

                if (params.size() > 0 && errors.size()==0) {
                    params.add(lsrt);
                    params.add("DFCOL");
                    params.add(nmeIdn);
                    params.add(colP);
                    params.add(lprp);
                    int ct = db.execDirUpd(" upd menu " + lIdn, updQ+" , srt =? " + condQ, params);
                }
                if(params.size() > 0){
                    errorList.add(errors);
                    errors = new ArrayList();
                }
                count++;
            }
            rs.close();
            pst.close();
        }

        req.setAttribute("errors", errorList);
            util.updAccessLog(req,res,"Column Mapping", "save end");
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
             util.updAccessLog(req,res,"Column Mapping", "delete start");
         ArrayList ary = new ArrayList();
         String mprp = util.nvl(req.getParameter("mprp"));
         String nmeIdn = util.nvl(req.getParameter("nmeIdn"));
         String col = util.nvl(req.getParameter("col"));
         String deleteprpMap="delete from prp_map_web where mprp=? and name_id=? ";
         ary = new ArrayList();
         ary.add(mprp);
         ary.add(nmeIdn);
         int ct = db.execUpd("delete Prp", deleteprpMap, ary);
         
         String deleteMap = "delete from prp_map_web where idx=? and name_id=? and val=? ";
         ary = new ArrayList();
         ary.add(mprp);
         ary.add(nmeIdn);
         ary.add(col);
         ct = db.execUpd("delete", deleteMap, ary);
             util.updAccessLog(req,res,"Column Mapping", "delete end");
         return load(am, af, req, res);
         }
     }
    public ActionForward loadcopymap(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception  {
        HttpSession session = request.getSession(false);
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
        rtnPg=init(request,response,session,util);
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(request,response,"Column Mapping", "loadcopymap start");
        ColumnMapForm  udf = (ColumnMapForm)af;
        String currentnmeIdn = util.nvl(request.getParameter("nmeIdn"));
        udf.setValue("currentnmeIdn", currentnmeIdn);
            util.updAccessLog(request,response,"Column Mapping", "loadcopymap end");
       return am.findForward("copymap");
        }
    }
    public ActionForward copymap(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception  {
        HttpSession session = request.getSession(false);
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
        rtnPg=init(request,response,session,util);
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(request,response,"Column Mapping", "copymap start");
        ColumnMapForm udf = (ColumnMapForm)af;
        ArrayList ary=new ArrayList();
        String currentnmeIdn = util.nvl((String)udf.getValue("currentnmeIdn"));
        String copynmeidn = util.nvl((String)udf.getNmeID());
        if(!copynmeidn.equals("")){
        int ct=0;
        String delQ = " Delete from Prp_Map_Web where name_id=?";
        ary.add(currentnmeIdn);
        ct =db.execUpd(" Del Old Pkts ", delQ,ary);
        if(ct>0)
        request.setAttribute("del", "Delete Exists Rows -"+ct);
        
        String srchRefQ = 
        " Insert Into Prp_Map_Web (mprp,psrt,val,srt,rap,pol,idx,crt_lne,w1,w2,w3,name_id,frm_name_id) \n" + 
        "Select mprp,psrt,val,srt,rap,pol,idx,crt_lne,w1,w2,w3,?,? \n" + 
        "From Prp_Map_Web\n" + 
        "WHERE name_id=?";
        ary=new ArrayList();
        ary.add(currentnmeIdn);
        ary.add(copynmeidn);
        ary.add(copynmeidn);
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);        
        if(ct>0)
        request.setAttribute("copy", "Copy Rows -"+ct);
        }
        udf.setValue("currentnmeIdn", currentnmeIdn);
            util.updAccessLog(request,response,"Column Mapping", "copymap end");
       return am.findForward("copymap");
        }
    }
    
    public ActionForward dwmapping(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse res) throws Exception  {
        HttpSession session = request.getSession(false);
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
        rtnPg=init(request,res,session,util);
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(request,res,"Column Mapping", "dwmapping start");
        ColumnMapForm  udf = (ColumnMapForm)af;
        String nmeIdn = util.nvl(request.getParameter("nmeIdn"));
            String fileName = nmeIdn+"_"+util.getToDteTime()+".csv";
            String lnhdr="";
            PrintWriter out = res.getWriter();
            res.setContentType("application/ms-excel");
            res.setHeader("Content-disposition","attachment;filename="+fileName);
            String prpqry="select idx mprp,nvl(w3,idx) dsc, rank() over (order by srt) rnk from prp_map_web  where name_id = ? and mprp = 'DFCOL'";
            ArrayList ary=new ArrayList();
                ary.add(util.nvl(nmeIdn));

            ArrayList outLst = db.execSqlLst(" Vw Lst ",prpqry , ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs1 = (ResultSet)outLst.get(1);
            while (rs1.next()) {
                lnhdr+=util.nvl(rs1.getString("mprp"))+" ("+util.nvl(rs1.getString("dsc"))+"),";
            }
            rs1.close();
            pst.close();
            out.println(lnhdr);  
            out.flush();
            out.close();
            util.updAccessLog(request,res,"Column Mapping", "dwmapping end");
            return null;
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
                util.updAccessLog(req,res,"Column Mapping", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Column Mapping", "init");
            }
            }
            return rtnPg;
         }
}
