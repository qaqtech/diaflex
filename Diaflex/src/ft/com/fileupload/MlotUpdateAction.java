package ft.com.fileupload;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.dao.ObjBean;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MlotUpdateAction extends DispatchAction {
    
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
               MlotUpdateFrom udf = (MlotUpdateFrom)form;
            util.updAccessLog(req,res,"Mfg update", "Mfg update");
              udf.resetAll();
                HashMap mlotLst = mlotCollection(db);
             String lstNme = "MLOT_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
             gtMgr.setValue("lstNmeMLOT", lstNme);
             gtMgr.setValue(lstNme, mlotLst);
                HashMap prp = info.getPrp();
                ArrayList prpROValLst = (ArrayList)prp.get("ROV");
                ArrayList prpLst = new ArrayList();
                for(int i=0;i<prpROValLst.size();i++){
                    String prpVal = (String)prpROValLst.get(i);
                    ObjBean prpObj = new ObjBean();
                    prpObj.setDsc(prpVal);
                    prpLst.add(prpObj);
                }
                udf.setPrpRoLst(prpLst);
                 ArrayList prpMINLst = (ArrayList)prp.get("MINING_COV");
                 prpLst = new ArrayList();
                for(int i=0;i<prpMINLst.size();i++){
                    String prpVal = (String)prpMINLst.get(i);
                    ObjBean prpObj = new ObjBean();
                    prpObj.setDsc(prpVal);
                    prpLst.add(prpObj);
                }
             
                udf.setPrpMinLst(prpLst);
            return am.findForward("load"); 
            }
        
        }
    
    public ActionForward save(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
               MlotUpdateFrom udf = (MlotUpdateFrom)form;
              ArrayList mlotIdnLst = new ArrayList();
                Enumeration reqNme = req.getParameterNames();
                
                while(reqNme.hasMoreElements()) 
                {
                    String paramNm = (String)reqNme.nextElement();
                  
                    if(paramNm.indexOf("cb_") > -1) 
                    {
                        String mlotIdn = req.getParameter(paramNm);
                     
                        mlotIdnLst.add(mlotIdn);
                    }
                }
                for(int i=0;i< mlotIdnLst.size();i++){
                    String mlotIdn = (String)mlotIdnLst.get(i);
                    String roVal = (String)udf.getValue("RO_"+mlotIdn);
                    String miniVal = (String)udf.getValue("MIN_"+mlotIdn);
                    String updatemlot = "update mlot set ro= ? , mining=? , mod_usr= ? , mod_dte=sysdate where idn=? ";
                    ArrayList ary = new ArrayList();
                    ary.add(roVal);
                    ary.add(miniVal);
                    ary.add(info.getUsr());
                    ary.add(mlotIdn);
                    int ct = db.execCall("updatemlot", updatemlot, ary);
                    
                }
                udf.reset();
                String lstNme = (String)gtMgr.getValue("lstNmeMLOT");
                HashMap mlotLst = mlotCollection(db);
                gtMgr.setValue(lstNme, mlotLst);
                return am.findForward("load"); 
            }
    }
    
    public HashMap mlotCollection(DBMgr db){
        HashMap mlotLst = new HashMap();
        try {
           
            ArrayList dspLst = new ArrayList();
            String modMlot =
                "select idn,dsc,ro,mining,mod_usr,to_char(mod_dte,'dd-mm-yyyy') mod_dte,to_char(dte,'dd-mm-yyyy') dte from mlot where trunc(nvl(mod_dte,sysdate)) > trunc(sysdate-30) and ro is not null and mining is not null order by dsc";
            ArrayList rsLst = db.execSqlLst("modMlot", modMlot, new ArrayList());
            PreparedStatement ps = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
                ObjBean mlot = new ObjBean();
                mlot.setValue("dsc", rs.getString("dsc"));
                mlot.setValue("ro", rs.getString("ro"));
                mlot.setValue("mining", rs.getString("mining"));
                mlot.setValue("mod_usr", rs.getString("mod_usr"));
                mlot.setValue("mod_dte", rs.getString("mod_dte"));
                mlot.setValue("dte", rs.getString("dte"));
                mlot.setValue("idn", rs.getString("idn"));
                dspLst.add(mlot);
            }
            rs.close();
            ps.close();
            mlotLst.put("DSP", dspLst);
            ArrayList modLst = new ArrayList();
            String Mlot =
                "select idn, dsc,ro,MINING,mod_usr,to_char(mod_dte,'dd-mm-yyyy') mod_dte,to_char(dte,'dd-mm-yyyy') dte from mlot where ro is  null and mining is null order by dsc";
            rsLst = db.execSqlLst("modMlot", Mlot, new ArrayList());
            ps = (PreparedStatement)rsLst.get(0);
            rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
                ObjBean mlot = new ObjBean();
                mlot.setValue("dsc", rs.getString("dsc"));
                mlot.setValue("ro", rs.getString("ro"));
                mlot.setValue("mining", rs.getString("mining"));
                mlot.setValue("mod_usr", rs.getString("mod_usr"));
                mlot.setValue("mod_dte", rs.getString("mod_dte"));
                mlot.setValue("dte", rs.getString("dte"));
                mlot.setValue("idn", rs.getString("idn"));
                modLst.add(mlot);
            }
            rs.close();
            ps.close();
            mlotLst.put("MOD", modLst);

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return mlotLst;
    }
    public MlotUpdateAction() {
        super();
    }
    public void GtMgrReset(HttpServletRequest req){
          HttpSession session = req.getSession(false);
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
          String lstNme = (String)gtMgr.getValue("lstNmeMLOT");
          HashMap gtMgrMap = (HashMap)gtMgr.getValues();
          gtMgrMap.remove(lstNme);
          gtMgrMap.remove("lstNmeMLOT");
          
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
                util.updAccessLog(req,res,"Mfg Recive", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Mfg Recive", "init");
            }
            }
            return rtnPg;
            }
}
