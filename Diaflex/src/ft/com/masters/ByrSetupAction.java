package ft.com.masters;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.MailAction;
import ft.com.dao.ByrDao;

import ft.com.dao.MemoType;

import ft.com.dao.PktDtl;

import ft.com.dao.UIForms;
import ft.com.generic.GenericImpl;
import ft.com.marketing.SearchForm;
import ft.com.marketing.SearchQuery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;

import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.ArrayList;

import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

public class ByrSetupAction extends DispatchAction {
    
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"BuyerSetup", "load start");
        ByrSetupForm byrForm = (ByrSetupForm)form;
        SearchQuery query = new SearchQuery();
//        byrForm.setIsEx("");
        ArrayList byrLst= query.getEmpList(req,res);
        byrForm.setByrList(byrLst);
        ArrayList partyLst = query.getPartyList(req,res);
        byrForm.setPartyList(partyLst);
        ArrayList termsList = query.getTerm(req,res, info.getDf_nme_id());
        byrForm.setTrmList(termsList);
          util.updAccessLog(req,res,"BuyerSetup", "load end");
     return am.findForward("load");
      }
  } 
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse response) throws Exception {
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
        rtnPg=init(req,response,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return mapping.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,response,"BuyerSetup", "save start");
            ByrSetupForm byrForm = (ByrSetupForm)form;
    ResultSet rs=null;
    ArrayList ary = new ArrayList();
    int rlnid=Integer.parseInt(byrForm.getByrRln());
    int byrId=Integer.parseInt(byrForm.getByrId());
    int party= Integer.parseInt(byrForm.getParty());
    String newXrt=util.nvl((String)byrForm.getValue("xrt"));
    
    String byrsetUpQ ="select nme_idn, nme from nme_v where nme_idn=?";
    ary.add(String.valueOf(byrId));
    rs = db.execSql("Buyer SetUp", byrsetUpQ, ary);
    while(rs.next()) {
    info.setSetUpbyrId(Integer.parseInt(rs.getString("nme_idn")));
    info.setSetUpbyrNm(util.nvl((rs.getString("nme"))));
    }
        rs.close();
    byrsetUpQ ="select nme_idn, nme from nme_v where nme_idn=?";
    ary = new ArrayList();
    ary.add(String.valueOf(party));
    rs = db.execSql("Buyer SetUp", byrsetUpQ, ary);
    while(rs.next()) {
    info.setSetUppartyId(Integer.parseInt(rs.getString("nme_idn")));
    info.setSetUppartyNm(util.nvl((rs.getString("nme"))));
    }
        rs.close();
    byrsetUpQ ="select  dtls , nmerel_idn from  nme_rel_v where nmerel_idn=?";
    ary = new ArrayList();
    ary.add(String.valueOf(rlnid));
    rs = db.execSql("Buyer SetUp", byrsetUpQ, ary);
    while(rs.next()) {
    info.setSetUpTermId(Integer.parseInt(rs.getString("nmerel_idn")));
    info.setSetUpTermNm(util.nvl((rs.getString("dtls"))));
    }
        rs.close();
    info.setSetUpEX(util.nvl((newXrt)));

    req.setAttribute("msg", "SetUp Done for Buyer : "+info.getSetUppartyNm());
            util.updAccessLog(req,response,"BuyerSetup", "save end");
    return mapping.findForward("load");
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
                util.updAccessLog(req,res,"BuyerSetup", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"BuyerSetup", "init");
            }
            }
            return rtnPg;
         }
    public ByrSetupAction() {
        super();
    }
}

