package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.UIForms;
import ft.com.website.BulkRoleForm;

import ft.com.website.UserLoginInfoForm;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

public class MixSizeClrAction extends DispatchAction{   
    
  
    public MixSizeClrAction() {
        super();
    }
    public ActionForward loadSizeMix(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"MixSizeClr", "loadSZMx");
       MixSizeClrForm mixSizeClrForm = (MixSizeClrForm)af;
        ArrayList    ary          = null;
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        ArrayList prpPrtSize=null;
        ArrayList prpValSize=null;
        ArrayList prpSrtSize = null;
        ArrayList prpPrtClr=null;
        ArrayList prpValClr=null;
        ArrayList prpSrtClr = null;
        prpPrtSize = (ArrayList)prp.get("MIX_SIZE"+"P");
        prpSrtSize = (ArrayList)prp.get("MIX_SIZE"+"S");
        prpValSize = (ArrayList)prp.get("MIX_SIZE"+"V");
        
        prpPrtClr=new ArrayList();
        prpValClr=new ArrayList();
        prpSrtClr =new ArrayList();
        prpPrtClr = (ArrayList)prp.get("MIX_CLARITY"+"P");
        prpSrtClr = (ArrayList)prp.get("MIX_CLARITY"+"S");
        prpValClr = (ArrayList)prp.get("MIX_CLARITY"+"V");
        String sql = " select mix_Size,mix_clarity from mix_size_clarity where stt='A' ";
          ArrayList  rsLst = db.execSqlLst("Sale Person", sql, new ArrayList());
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while (rs.next()) {
                String mix_Size=util.nvl(rs.getString("mix_Size"));
                String mix_clarity=util.nvl(rs.getString("mix_clarity"));
                if(prpValSize.contains(mix_Size) && prpValClr.contains(mix_clarity)){
                String mixSize=(String)prpSrtSize.get(prpValSize.indexOf(mix_Size));
                String mixClr=(String)prpSrtClr.get(prpValClr.indexOf(mix_clarity)); 
                String fldNme = mixClr+"_SZCLR_"+mixSize;
                mixSizeClrForm.setValue(fldNme,fldNme);
                }
              
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
          util.updAccessLog(req,res,"MixSizeClr", "loadSZMxEnd");
        return am.findForward("load");
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
          util.updAccessLog(req,res,"MixSizeClr", "save");
        String stt = req.getParameter("stt");
        String mixSizeSrt = req.getParameter("mixSize");
        String mixClrSrt = req.getParameter("mixClr");
        HashMap prp = info.getPrp();
      
        ArrayList prpPrtSize=null;
        ArrayList prpValSize=null;
        ArrayList prpSrtSize = null;
        ArrayList prpPrtClr=null;
        ArrayList prpValClr=null;
        ArrayList prpSrtClr = null;
        prpPrtSize = (ArrayList)prp.get("MIX_SIZE"+"P");
        prpSrtSize = (ArrayList)prp.get("MIX_SIZE"+"S");
        prpValSize = (ArrayList)prp.get("MIX_SIZE"+"V");
        String mixSize=(String)prpValSize.get(prpSrtSize.indexOf(mixSizeSrt));
        
        prpPrtClr=new ArrayList();
        prpValClr=new ArrayList();
        prpSrtClr =new ArrayList();
        prpPrtClr = (ArrayList)prp.get("MIX_CLARITY"+"P");
        prpSrtClr = (ArrayList)prp.get("MIX_CLARITY"+"S");
        prpValClr = (ArrayList)prp.get("MIX_CLARITY"+"V");
        String mixClr=(String)prpValClr.get(prpSrtClr.indexOf(mixClrSrt));    
        
                      
        ArrayList params  = new ArrayList();
        String sql="";
             
            if(stt.equals("true"))
            stt="A";
            else
            stt="IA";
            
            sql="update mix_size_clarity set mix_Size=?,mix_clarity=?,stt=? where mix_Size=? and mix_clarity=?";
      params  = new ArrayList();
      params.add(mixSize);
      params.add(mixClr);
      params.add(stt);
      params.add(mixSize);
      params.add(mixClr);
      int ct = db.execDirUpd(" update mix_size_clarity ", sql, params);
      if(ct<1){
        params.clear();
        params.add(mixSize);
        params.add(mixClr);
        params.add(stt);
        sql="insert into mix_size_clarity(mix_Size,mix_clarity,stt) values(?,?,?)"; 
        ct = db.execDirUpd(" insert mix_size_clarity ", sql, params);
      }
          util.updAccessLog(req,res,"MixSizeClr", "save end");
        
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
          util.updAccessLog(req,res,"Mix Size Clr Action", "init");
          }
          }
          return rtnPg;
          }
}
