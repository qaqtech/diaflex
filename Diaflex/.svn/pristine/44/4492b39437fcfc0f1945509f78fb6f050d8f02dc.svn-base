package ft.com.website;

import ft.com.DBMgr;

import ft.com.DBUtil;
import ft.com.GenMail;
import ft.com.InfoMgr;

import ft.com.dao.GenDAO;
import ft.com.generic.GenericImpl;
import ft.com.marketing.SearchQuery;

import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;

import java.net.IDN;

import java.sql.PreparedStatement;
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
import org.apache.struts.upload.FormFile;



public class AdminHomepgScrollAction extends DispatchAction{
  
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception 
        {
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
              util.updAccessLog(request,response,"AdminHomepgScroll", "load");
          AdminHomepgScrollForm scrollForm = (AdminHomepgScrollForm)af;
          String sql = "select a.idn , b.idn ctgIdn , a.nme , b.dsc , b.stt , to_char(b.vld_dte ,'DD-MON-YYYY')  vld_dte ,  to_char( b.frm_dte ,'DD-MON-YYYY') frm_dte , b.img1  from ctg a , ctg_content b " + 
          " where  a.idn = b.ctg_idn and b.stt='A'  ";
          ArrayList dtlList = new ArrayList();
                ArrayList outLst = db.execSqlLst("homepgscroll", sql, new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next())
            {
                HashMap ctgList = new HashMap();
                ctgList.put("nme", util.nvl(rs.getString("nme")));
                ctgList.put("dsc", util.nvl(rs.getString("dsc")));
                ctgList.put("stt", util.nvl(rs.getString("stt")));
               ctgList.put("img", util.nvl(rs.getString("img1")));
                ctgList.put("vldDte", util.nvl(rs.getString("vld_dte")));
                ctgList.put("frmDte", util.nvl(rs.getString("frm_dte")));
               ctgList.put("idn", util.nvl(rs.getString("ctgIdn")));
                dtlList.add(ctgList);
                
           }
                rs.close();
                pst.close();
          ArrayList ctgList = new ArrayList();
          String sqlCtg = " select idn, nme from ctg where stt='A' and vld_dte is null ";
                outLst = db.execSqlLst("sqlCtg", sqlCtg, new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
          while(rs.next()){
              GenDAO dao = new GenDAO();
              dao.setIdn(rs.getString("idn"));
              dao.setDsc(rs.getString("nme"));
              ctgList.add(dao);
              
          }
                rs.close();
                pst.close();
          scrollForm.setCtgList(ctgList);
          request.setAttribute("datalist", dtlList);
              util.updAccessLog(request,response,"AdminHomepgScroll", "load end");
          return am.findForward("view");
            }
        
        }
      
  public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception 
  {
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
        util.updAccessLog(request,response,"AdminHomepgScroll", "save");
    AdminHomepgScrollForm popupform = (AdminHomepgScrollForm)af;
    String add = util.nvl(request.getParameter("add"));
    if(add.length()>0){
    String getInvIdQ = " select CTG_CONTENT_SEQ.nextval id from dual  ";

        ArrayList outLst = db.execSqlLst("Get Inv Id ", getInvIdQ, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
     if(rs.next()){
     String Idn = rs.getString(1);
        String insQ =
         "insert into CTG_CONTENT(IDN, CTG_IDN, DSC, IMG1 , frm_dte , vld_dte ) values(?,?,?,?,trunc(to_date('"+popupform.getValidfrom().trim()+"', 'dd-mm-rrrr')),trunc(to_date('"+popupform.getValidtill().trim()+"', 'dd-mm-rrrr')))";
    String filename = uploadFile(request, popupform.getHomepageimg() , Idn);
    ArrayList params = new ArrayList();
    params.add(Idn);
    params.add(popupform.getCtgIdn());
    params.add(request.getParameter("longdesc"));
     params.add(filename);
  
    int ct = db.execUpd("Ctg_content", insQ, params);
    }
        rs.close();
        pst.close();
    }else{
        String updateSql = "update CTG_CONTENT set dsc=? , frm_dte =trunc(to_date('"+popupform.getValidfrom().trim()+"', 'dd-mm-rrrr')) , vld_dte=trunc(to_date('"+popupform.getValidtill().trim()+"', 'dd-mm-rrrr'))  , ctg_idn = ? ";
        ArrayList params = new ArrayList();
        params.add(request.getParameter("longdesc"));
      //  params.add(popupform.getValidfrom().trim());
      //  params.add(popupform.getValidtill().trim());
        params.add(popupform.getCtgIdn());
        params.add(popupform.getIdn());
        String filename = uploadFile(request,popupform.getHomepageimg() , popupform.getIdn());
        if(!filename.equals("")){
            updateSql = updateSql+" , img1 = ? ";
            params.add(filename);
        }
        updateSql = updateSql+"  where idn=? ";
        int ct = db.execUpd("Ctg_content", updateSql, params);
        System.out.println("count"+ct);
    }
    popupform.reset();
        util.updAccessLog(request,response,"AdminHomepgScroll", "save end");
    return load(am, af, request, response);
      }
  }
  
  public String uploadFile(HttpServletRequest req, FormFile myFile , String ctgIdn) throws Exception{
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
      HashMap dbSysInfo = info.getDmbsInfoLst();
      String imageName  = util.nvl(myFile.getFileName());
   
      if(!imageName.equals("")){
          imageName  = ctgIdn+"_"+myFile.getFileName();
         String docPath = (String)dbSysInfo.get("DOC_PATH");
         String filePath = docPath+""+imageName;
         File fileToCreate = new File(filePath);
      if (!fileToCreate.exists()) {
         FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
         fileOutStream.write(myFile.getFileData());
         fileOutStream.flush();
          fileOutStream.close();
      }}
      return imageName;
  }
  
    public ActionForward edit(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception 
        {
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
              util.updAccessLog(request,response,"AdminHomepgScroll", "edit");
          AdminHomepgScrollForm scrollForm = (AdminHomepgScrollForm)af;
          String idn = request.getParameter("idn");
          String sql = "select a.idn idn , b.idn ctgIdn , a.nme , b.dsc , b.stt , to_char(b.vld_dte ,'DD-MM-YYYY')  vld_dte ,  to_char( b.frm_dte ,'DD-MM-YYYY') frm_dte , b.img1  from ctg a , ctg_content b " + 
          " where  a.idn = b.ctg_idn and b.idn = ?  ";
          ArrayList ary = new ArrayList();
          ary.add(idn);
                ArrayList outLst = db.execSqlLst("homepgscroll", sql, ary);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
          if(rs.next()){
                scrollForm.setCtgIdn(util.nvl(rs.getString("idn")));
                scrollForm.setValidfrom(util.nvl(rs.getString("frm_dte")));
                scrollForm.setValidtill(util.nvl(rs.getString("vld_dte")));
                request.setAttribute("dsc", util.nvl(rs.getString("dsc")));
                scrollForm.setIdn(util.nvl(rs.getString("ctgIdn")));
          }
                rs.close();
                pst.close();
              util.updAccessLog(request,response,"AdminHomepgScroll", "edit end");
          return load(am, af, request, response);
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
              rtnPg=util.checkUserPageRights("",util.getFullURL(req));
              if(rtnPg.equals("unauthorized"))
              util.updAccessLog(req,res,"AdminHomepgScroll", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"AdminHomepgScroll", "init");
          }
          }
          return rtnPg;
          }
    public AdminHomepgScrollAction() {
        super();
    }
}
