package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.PdfforReport;
import ft.com.dao.ByrDao;
import ft.com.dao.DFMenu;

import ft.com.dao.DFMenuItm;

import ft.com.dao.MNme;
import ft.com.dao.ObjBean;
import ft.com.fileupload.FileUploadForm;
import ft.com.marketing.SearchQuery;

import ft.com.report.ReportForm;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.URLEncoder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MixClrMappingNew extends DispatchAction {
    
    public ActionForward loadfetchview(ActionMapping am, ActionForm af, HttpServletRequest req,
                                     HttpServletResponse res) throws Exception {
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
         
        MixClrMappingForm udf = (MixClrMappingForm)af;
        udf.reset();
        return am.findForward("load");
        }
    }
   
    public ActionForward fetchview(ActionMapping am, ActionForm af, HttpServletRequest req,
                                     HttpServletResponse res) throws Exception {
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
         
        MixClrMappingForm udf = (MixClrMappingForm)af;
        String dept=util.nvl((String)udf.getValue("dept"));
        String wtfr=util.nvl((String)udf.getValue("wtfr"));
        String wtto=util.nvl((String)udf.getValue("wtto"));
        String srch=util.nvl((String)udf.getValue("srch"));

        if(!dept.equals("") && !wtfr.equals("") && !wtto.equals("")){
        if(!srch.equals("")){
            return load(am,af,req,res);
        }else{
            return update(am,af,req,res);
        }
        }
         
        return am.findForward("load");
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
          util.updAccessLog(req,res,"MixClrMapping", "load");
        MixClrMappingForm udf = (MixClrMappingForm)af;
        String dept=util.nvl((String)udf.getValue("dept"));
        String wtfr=util.nvl((String)udf.getValue("wtfr"));
        String wtto=util.nvl((String)udf.getValue("wtto"));
        udf = (MixClrMappingForm) af;
        udf.reset();
        udf.setValue("dept", dept);
        udf.setValue("wtfr", wtfr);
        udf.setValue("wtto", wtto);
        HashMap prp = info.getPrp();
        ArrayList ary = new ArrayList();
        ArrayList updlist = new ArrayList();
        String sqlQ="select dept,mix_clarity,wt_fr,wt_to,col_fr,col_to,clr_fr,col_to from MIX_CLARITY_MAPPING where dept=? and wt_fr=? and wt_to=? and stt='A'";
        ary.add(dept);
        ary.add(wtfr);
        ary.add(wtto);
          ArrayList  rsLst = db.execSqlLst("dtl", sqlQ, ary);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
        while (rs.next()) {
        String col=util.nvl(rs.getString("col_fr"));
        String clr=util.nvl(rs.getString("clr_fr"));
        udf.setValue(col+"_"+clr, util.nvl(rs.getString("mix_clarity"))); 
        updlist.add(col+"_"+clr);    
        }
        rs.close();
        stmt.close();
        session.setAttribute("updlist", updlist);
        req.setAttribute("view", "Y");
        req.setAttribute("criteria", "Dept-"+dept+" ,Weight From-"+wtfr+" ,Weight To-"+wtto);
          util.updAccessLog(req,res,"MixClrMapping", "load end");
    return am.findForward("load");
        }
    }
    public ActionForward update(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"MixClrMapping", "update");
        MixClrMappingForm udf = (MixClrMappingForm)af;
        String dept=util.nvl((String)udf.getValue("dept"));
        String wtfr=util.nvl((String)udf.getValue("wtfr"));
        String wtto=util.nvl((String)udf.getValue("wtto"));
        udf = (MixClrMappingForm) af;
        HashMap prp = info.getPrp();
        ArrayList ary = new ArrayList();
        ArrayList colList = (ArrayList)prp.get("COLV");
        ArrayList clrList = (ArrayList)prp.get("CLRV");
        ArrayList updlist = (session.getAttribute("updlist") == null)?new ArrayList():(ArrayList)session.getAttribute("updlist");
        String updQ="update mix_clarity_mapping set mix_clarity=?,df_usr=?,df_usr_mch=? where dept=? and wt_fr=? and wt_to=? and col_fr=? and clr_fr=?";
        String insertQ="insert into mix_clarity_mapping (idn,dept,wt_fr,wt_to,col_fr,col_to,clr_fr,clr_to,mix_clarity,df_usr,df_usr_mch) VALUES (mix_clarity_mapping_SEQ.nextval,?,?,?,?,?,?,?,?,?,?)";
        for(int i=0;i<colList.size();i++){
        String col=(String)colList.get(i);
        boolean isDis1 = true;
        if(col.indexOf("+")!=-1)   
        isDis1 = false;
        if(col.indexOf("-")!=-1)   
        isDis1 = false;
        if(isDis1){   
        for(int j=0;j<clrList.size();j++){
        String clr=(String)clrList.get(j);
        boolean isDis2 = true;
        if(clr.indexOf("+")!=-1)   
        isDis2 = false;
        if(clr.indexOf("-")!=-1)   
        isDis2 = false;
        if(isDis2){  
        String mixval=util.nvl((String)udf.getValue(col+"_"+clr));
        if(mixval.equals(""))  
         mixval="NA";
        if(updlist.contains(col+"_"+clr)){
          ary = new ArrayList();
          ary.add(mixval);
          ary.add(info.getUsr());
          ary.add(req.getRemoteUser());
          ary.add(dept);
          ary.add(wtfr);
          ary.add(wtto);
          ary.add(col);
          ary.add(clr);
          int ct = db.execUpd("Update", updQ, ary);  
        }else if(!mixval.equals("NA") && !mixval.equals("")){
              ary = new ArrayList();
              ary.add(dept);
              ary.add(wtfr);
              ary.add(wtto);
              ary.add(col);
              ary.add(col);
              ary.add(clr);
              ary.add(clr);
              ary.add(mixval);
              ary.add(info.getUsr());
              ary.add(req.getRemoteUser());
          int ct = db.execUpd("Insert", insertQ, ary);
          System.out.println(ct);
          }
        }
        }
        }
        }
        udf.reset();
        udf.setValue("dept", dept);
        udf.setValue("wtfr", wtfr);
        udf.setValue("wtto", wtto);
          util.updAccessLog(req,res,"MixClrMapping", "update end");
    return load(am,af,req,res);
        }
    }
    public MixClrMappingNew() {
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
              util.updAccessLog(req,res,"Mix Clr Mapping", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Mix Clr Mapping", "init");
          }
          }
          return rtnPg;
          }


}

