package ft.com.dashboard;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.inward.AssortLabReportForm;
import ft.com.marketing.MemoReportForm;

import ft.com.marketing.SearchQuery;

import ft.com.report.ReportForm;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import java.math.BigDecimal;

import java.math.RoundingMode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import java.sql.Connection;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import java.util.Map;

import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScreenAction extends DispatchAction {
    
    public ActionForward loadwebaccesssmmyApi(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
      String url=util.getUrl(req);
            String hdrnme=(String)req.getParameter("hdrnme");
            String dtl=util.nvl((String)req.getParameter("dtl"));
            String webrfsh=util.nvl((String)req.getParameter("webapirfsh"));
            ArrayList dtlList = new ArrayList();
        String loginDtlSql = "select e.nme byr, d.usr usr, count(*) cnt from web_login_log a , nmerel c , web_usrs d , nme_v e\n" + 
        "Where C.Nmerel_Idn = D.Rel_Idn And D.Usr_Id = A.Usr_Id And C.Nme_Idn=E.Nme_Idn\n" + 
        "and trunc(a.dt_tm) = trunc(sysdate) and a.flg in('API') " +
        "GROUP BY e.nme, d.usr\n" + 
        "order by cnt desc\n";
        
        ArrayList rsLst = db.execSqlLst("loginDtl", loginDtlSql, new ArrayList());
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        int srno=0;
        while(rs.next()){
          srno = srno+1;
          HashMap dtls = new HashMap();
          dtls.put("SR No", String.valueOf(srno));
          dtls.put("Buyer", util.nvl(rs.getString("byr")));
          dtls.put("User", util.nvl(rs.getString("usr")));
          dtls.put("Count", util.nvl(rs.getString("cnt")));
          dtlList.add(dtls);
          if(dtl.length()==0 && srno==30){
          break;}    
        }
        rs.close();
        pst.close();
        ArrayList itemHdr = new ArrayList();
        itemHdr.add("SR No");
        itemHdr.add("Buyer");
        itemHdr.add("User");
        itemHdr.add("Count");
        req.setAttribute("itemHdrwebapi",itemHdr);
        req.setAttribute("dtlListwebapi", dtlList);
        req.setAttribute("webapirfsh", webrfsh);
        udf.setPktList(dtlList);
        req.setAttribute("hdrnmewebapi", hdrnme);
        if(dtl.equals(""))
            util.updAccessLog(req,res,hdrnme, hdrnme);
        else
            util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
        return am.findForward("loadwebaccesssmmyApi");
            }
        }
  public ActionForward loadwebaccesssmmy(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
    UserRightsForm udf = (UserRightsForm)af;
    String url=util.getUrl(req);
          String hdrnme=(String)req.getParameter("hdrnme");
          String dtl=util.nvl((String)req.getParameter("dtl"));
          String webrfsh=util.nvl((String)req.getParameter("webrfsh"));
          ArrayList dtlList = new ArrayList();
      String loginDtlSql = "select e.nme byr, d.usr usr, count(*) cnt from web_login_log a , nmerel c , web_usrs d , nme_v e\n" + 
      "Where C.Nmerel_Idn = D.Rel_Idn And D.Usr_Id = A.Usr_Id And C.Nme_Idn=E.Nme_Idn\n" + 
      "and trunc(a.dt_tm) = trunc(sysdate) and a.flg not in('API') " +
      "GROUP BY e.nme, d.usr\n" + 
      "order by cnt desc\n";
      
      ArrayList rsLst = db.execSqlLst("loginDtl", loginDtlSql, new ArrayList());
      PreparedStatement pst = (PreparedStatement)rsLst.get(0);
      ResultSet rs = (ResultSet)rsLst.get(1);
      int srno=0;
      while(rs.next()){
        srno = srno+1;
        HashMap dtls = new HashMap();
        dtls.put("SR No", String.valueOf(srno));
        dtls.put("Buyer", util.nvl(rs.getString("byr")));
        dtls.put("User", util.nvl(rs.getString("usr")));
        dtls.put("Count", util.nvl(rs.getString("cnt")));
        dtlList.add(dtls);
        if(dtl.length()==0 && srno==30){
        break;}    
      }
      rs.close();
      pst.close();
      ArrayList itemHdr = new ArrayList();
      itemHdr.add("SR No");
      itemHdr.add("Buyer");
      itemHdr.add("User");
      itemHdr.add("Count");
      req.setAttribute("itemHdrweb",itemHdr);
      req.setAttribute("dtlListweb", dtlList);
      req.setAttribute("webrfsh", webrfsh);
      udf.setPktList(dtlList);
      req.setAttribute("hdrnmeweb", hdrnme);
      if(dtl.equals(""))
          util.updAccessLog(req,res,hdrnme, hdrnme);
      else
          util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
      return am.findForward("load");
          }
      }
    public ActionForward loadinternal(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
        String typ=util.nvl((String)req.getParameter("internal"));
        String dtl=util.nvl((String)req.getParameter("dtl"));
        String hdrnme=(String)req.getParameter("hdrI");
        String internalrfsh=(String)req.getParameter("internalrfsh");
        String internalpktty=util.nvl((String)req.getParameter("internalpktty"));
        ArrayList dtlList=loadmemo(typ,internalpktty,dtl,hdrnme,req,res);
        udf.setPktIList(dtlList);
        req.setAttribute("internalrfsh", internalrfsh);
        return am.findForward("loadinternal");
        }
    }
    public ActionForward loadconsignmentpnd(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
        String typ=util.nvl((String)req.getParameter("consignmentpnd"));
        String dtl=util.nvl((String)req.getParameter("dtl"));
        String hdrnme=(String)req.getParameter("hdrCSPND");
        String consignmentpndrfsh=(String)req.getParameter("consignmentpndrfsh");
        String consignmentpndpktty=util.nvl((String)req.getParameter("consignmentpndpktty"));
        ArrayList dtlList=loadmemo(typ,consignmentpndpktty,dtl,hdrnme,req,res);
        udf.setPktCSList(dtlList);
        req.setAttribute("consignmentpndrfsh", consignmentpndrfsh);
        return am.findForward("loadconsignmentpnd");
        }
    }
    
    public ActionForward loadConsignment(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
        String typ=util.nvl((String)req.getParameter("consignment"));
        String dtl=util.nvl((String)req.getParameter("dtl"));
        String hdrnme=(String)req.getParameter("hdrC");
        String consignmentrfsh=(String)req.getParameter("consignmentrfsh");
        loaddaily(typ,dtl,hdrnme,req,res);
        req.setAttribute("consignmentrfsh", consignmentrfsh);
        return am.findForward("loadconsignment");
        }
    }
    public ActionForward loadexternal(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
        String typ=util.nvl((String)req.getParameter("external"));
        String dtl=util.nvl((String)req.getParameter("dtl"));
        String hdrnme=(String)req.getParameter("hdrE");
        String externalrfsh=(String)req.getParameter("externalrfsh");
        String externalpktty=util.nvl((String)req.getParameter("externalpktty"));
        ArrayList dtlList=loadmemo(typ,externalpktty,dtl,hdrnme,req,res);
        udf.setPktEList(dtlList);
        req.setAttribute("externalrfsh", externalrfsh);
        return am.findForward("loadexternal");
        }
    }
    public ActionForward loadhold(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
        String typ=util.nvl((String)req.getParameter("hold"));
        String dtl=util.nvl((String)req.getParameter("dtl"));
        String hdrnme=(String)req.getParameter("hdrWH");
        String holdrfsh=(String)req.getParameter("holdrfsh");
        String holdpktty=util.nvl((String)req.getParameter("holdpktty"));
        ArrayList dtlList=loadmemo(typ,holdpktty,dtl,hdrnme,req,res);
        udf.setPktWHList(dtlList);
        req.setAttribute("holdrfsh", holdrfsh);
        return am.findForward("loadhold");
        }
    }
    public ActionForward loadcabinreq(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
        String typ=util.nvl((String)req.getParameter("cabinreq"));
        String dtl=util.nvl((String)req.getParameter("dtl"));
        String hdrnme=(String)req.getParameter("hdrCR");
        String cabinreqrfsh=(String)req.getParameter("cabinreqrfsh");
        String cabinreqpktty=util.nvl((String)req.getParameter("cabinreqpktty"));
        ArrayList dtlList=loadmemo(typ,cabinreqpktty,dtl,hdrnme,req,res);
        udf.setPktCRList(dtlList);
        req.setAttribute("cabinreqrfsh", cabinreqrfsh);
        return am.findForward("loadcabinreq");
        }
    }
    public ActionForward loadwebapp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
        String typ=util.nvl((String)req.getParameter("webapp"));
        String dtl=util.nvl((String)req.getParameter("dtl"));
        String hdrnme=(String)req.getParameter("hdrWAP");
        String webapprfsh=(String)req.getParameter("webapprfsh");
        String webapppktty=util.nvl((String)req.getParameter("webapppktty"));
        ArrayList dtlList=loadmemo(typ,webapppktty,dtl,hdrnme,req,res);
        udf.setPktWAPList(dtlList);
        req.setAttribute("webapprfsh", webapprfsh);
        return am.findForward("loadwebapp");
        }
    }
    public ActionForward loadextapp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
        String typ=util.nvl((String)req.getParameter("extapp"));
        String dtl=util.nvl((String)req.getParameter("dtl"));
        String hdrnme=(String)req.getParameter("hdrEAP");
        String extapprfsh=(String)req.getParameter("extapprfsh");
        String extapppktty=util.nvl((String)req.getParameter("extapppktty"));
        ArrayList dtlList=loadmemo(typ,extapppktty,dtl,hdrnme,req,res);
        udf.setPktEAPList(dtlList);
        req.setAttribute("extapprfsh", extapprfsh);
        return am.findForward("loadextapp");
        }
    }
    public ActionForward loadintapp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
        String typ=util.nvl((String)req.getParameter("intapp"));
        String dtl=util.nvl((String)req.getParameter("dtl"));
        String hdrnme=(String)req.getParameter("hdrIAP");
        String intapprfsh=(String)req.getParameter("intapprfsh");
        String intapppktty=util.nvl((String)req.getParameter("intapppktty"));
        ArrayList dtlList=loadmemo(typ,intapppktty,dtl,hdrnme,req,res);
        udf.setPktIAPList(dtlList);
        req.setAttribute("intapprfsh", intapprfsh);
        return am.findForward("loadintapp");
        }
    }
    public ArrayList loadmemo(String typ,String pktty,String dtl,String hdrnme,HttpServletRequest req, HttpServletResponse res)
            throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
        String conQ=" and pkt_ty in ('MIX','MX','NR') ";
        if(!pktty.equals(""))
            conQ=" and pkt_ty in ('"+pktty+"') ";  
        ArrayList dtlList = new ArrayList();
        String loadqry = "Select Byr , Cnt , Qty ,To_Char(Trunc(Cts,2),'9990.99') Cts,To_Char(Trunc(Val,2),'99999990.99')  Vlu,Typ \n" + 
        "    From Memo_Pndg_V Where Typ In('"+typ+"')  " +conQ+
        "    order by Vlu desc";
        if(typ.equals("CSPND")){
        loadqry = "Select Byr , Cnt , Qty ,To_Char(Trunc(Cts,2),'9990.99') Cts,To_Char(Trunc(Val,2),'99999990.99')  Vlu,Typ \n" + 
        "   From cs_pndg_v Where Typ In('CS') \n" + conQ+
        "   order by Vlu desc";    
        }
        ArrayList rsLst = db.execSqlLst("Memo", loadqry, new ArrayList());
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        int srno=0;
        while(rs.next()){
          srno = srno+1;
          HashMap dtls = new HashMap();
          dtls.put("SR No", String.valueOf(srno));
          dtls.put("Buyer", util.nvl(rs.getString("Byr")));
          dtls.put("Cnt", util.nvl(rs.getString("Cnt")));
            dtls.put("Qty", util.nvl(rs.getString("Qty")));
            dtls.put("Cts", util.nvl(rs.getString("Cts")));
            dtls.put("Vlu", util.nvl(rs.getString("Vlu")));
          dtlList.add(dtls);
          if(dtl.length()==0 && srno==30){
          break;} 
        }
        rs.close();
        pst.close();
            ArrayList itemHdr = new ArrayList();
            itemHdr.add("SR No");
            itemHdr.add("Buyer");
            itemHdr.add("Cnt");
            itemHdr.add("Qty");
            itemHdr.add("Cts");
            itemHdr.add("Vlu");
            req.setAttribute("itemHdr_"+typ,itemHdr);
            req.setAttribute("dtlList_"+typ, dtlList);
            req.setAttribute("hdrnme_"+typ, hdrnme);
            if(dtl.equals(""))
                util.updAccessLog(req,res,hdrnme, hdrnme);
            else
                util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
        return dtlList;
        }
    
    public ActionForward loadapprove(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("approve"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            String hdrnme=util.nvl((String)req.getParameter("hdrapprove"));
            String dapproverfsh=(String)req.getParameter("dapproverfsh");
            loaddaily(typ,dtl,hdrnme,req,res);
            req.setAttribute("hdrnme_"+typ, hdrnme);
            req.setAttribute("dapproverfsh", dapproverfsh);
        return am.findForward("loadapprove");
            }
        }
    public ActionForward loadlaballocation(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("allocation"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            String hdrnme=util.nvl((String)req.getParameter("hdrallocation"));
            String allocationrfsh=(String)req.getParameter("allocationrfsh");
            ArrayList    dtlList=new ArrayList(); 
                String loadqry="select a.idn,c.vnm,c.sk1,to_char(b.cts,'99999999990.99') cts,\n" + 
                "byr.get_nm(nvl(d.emp_idn,0)) sale_name, d.nme byr,to_char(b.cts*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1)),'999,9999,999,990.00') amt\n" + 
                "from mjan a,jandtl b, mstk c,nme_v d\n" + 
                "where\n" + 
                "a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn\n" + 
                "and b.typ in ('IAP') And a.flg='LAB' and trunc(a.dte)=trunc(sysdate)\n" + 
                "order by a.idn,d.nme,c.sk1";  
                ArrayList rsLst = db.execSqlLst(typ, loadqry, new ArrayList());
                PreparedStatement pst = (PreparedStatement)rsLst.get(0);
                ResultSet rs = (ResultSet)rsLst.get(1);
                int srno=0;
                while(rs.next()){
                            HashMap byrdata = new HashMap();
                            byrdata.put("SPERSON",util.nvl(rs.getString("sale_name")));
                            byrdata.put("BYR",util.nvl(rs.getString("byr")));
                            byrdata.put("VNM",util.nvl(rs.getString("vnm")));
                            byrdata.put("CTS",util.nvl(rs.getString("cts")));
                            byrdata.put("VLU",util.nvl(rs.getString("amt")));
                            dtlList.add(byrdata);
                }
                rs.close();
                pst.close();
                req.setAttribute("dtlList_"+typ, dtlList);
                req.setAttribute("hdrnme_"+typ, hdrnme);
                req.setAttribute("view", "Y");
                    if(dtl.equals(""))
                        util.updAccessLog(req,res,hdrnme, hdrnme);
                    else
                        util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
                req.setAttribute("allocationrfsh", allocationrfsh);
             
        return am.findForward("loadlaballocation");
            }
        }
    public ActionForward loadLivestockandsale(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("livestock"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            String hdrnme=util.nvl((String)req.getParameter("hdrlivestock"));
            String livestockrfsh=(String)req.getParameter("livestockrfsh");
            ArrayList    deptList=new ArrayList(); 
                String loadqry="WITH \n" + 
                "STK_TTL as (\n" + 
                "  select 'TTL' flg, count(*) pkts, trunc(sum(cts),2) cts\n" + 
                "    , round(sum(cts*nvl(upr, cmp))/1000) vlu\n" + 
                "    , round(sum(cts*(nvl(upr, cmp)))/sum(cts)) avg\n" + 
                "    , round(sum(cts*(nvl(upr, cmp))*cert_dys)/sum(cts*(nvl(upr, cmp)))) dys_avg\n" + 
                "  from mstk m, STK_CERT_DYS_MV dys\n" + 
                "  where 1 = 1 \n" + 
                "  and m.idn = dys.idn\n" + 
                "  and stt in ('MKAV', 'MKIS','SHIS', 'MKEI', 'MKWA', 'MKAP','MKKS_IS','MKOS_IS')\n" + 
                ")\n" + 
                ", STK_DEPT as (\n" + 
                "  select dept.val dpt, count(*) pkts, trunc(sum(cts),2) cts\n" + 
                "  , round(sum(cts*nvl(upr, cmp))/1000) vlu\n" + 
                "  , round(sum(cts*(nvl(upr, cmp)))/sum(cts)) avg\n" + 
                "  , round(sum(cts*(nvl(upr, cmp))*cert_dys)/sum(cts*(nvl(upr, cmp)))) dys_avg\n" + 
                "  , dept.srt srt\n" + 
                "  from mstk m, stk_dtl dept, STK_CERT_DYS_MV dys \n" + 
                "  where 1 = 1 \n" + 
                "  and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT'\n" + 
                "  and m.idn = dys.idn\n" + 
                "  and stt in ('MKAV', 'MKIS','SHIS', 'MKEI', 'MKWA', 'MKAP') and m.cts > 0 \n" + 
                "  group by dept.val, dept.srt\n" + 
                "  order by dept.srt\n" + 
                ")\n" + 
                "select stkd.dpt dept, stkd.cts, stkd.avg, stkd.dys_avg, stkd.vlu, to_char(trunc(stkd.vlu*100/stkt.vlu, 3),90.99) deptStkPct \n" + 
                ", stkt.cts tcts, stkt.avg tavg, stkt.dys_avg tdys, stkt.vlu tvlu\n" + 
                "from stk_ttl stkt, stk_dept stkd\n" + 
                "where 1 = 1 \n" + 
                "order by stkd.srt ";  
                ArrayList rsLst = db.execSqlLst(typ, loadqry, new ArrayList());
                PreparedStatement pst = (PreparedStatement)rsLst.get(0);
                ResultSet rs = (ResultSet)rsLst.get(1);
                int srno=0;
                HashMap livedata = new HashMap();
                try
                {
                while(rs.next()){
                            String dept =util.nvl(rs.getString("dept"));
                            livedata.put(dept+"CTS",util.nvl(rs.getString("cts")));
                            livedata.put(dept+"AVG",util.nvl(rs.getString("avg")));
                            livedata.put(dept+"DYS_AVG",util.nvl(rs.getString("dys_avg")));
                            livedata.put(dept+"VLU",util.nvl(rs.getString("vlu")));
                            livedata.put(dept+"DEPTSTKPCT",util.nvl(rs.getString("deptStkPct")));
                            livedata.put("TCTS",util.nvl(rs.getString("tcts")));
                            livedata.put("TAVG",util.nvl(rs.getString("tavg")));
                            livedata.put("TDYS",util.nvl(rs.getString("tdys")));
                            livedata.put("TVLU",util.nvl(rs.getString("tvlu")));


                            deptList.add(dept);
                }
                rs.close();
                pst.close();
                } catch (SQLException sqle) {
                    // TODO: Add catch code
                    sqle.printStackTrace();
                }
                loadqry="WITH STK_TTL as ( \n" + 
                "  select 'TTL' flg, count(*) pkts, trunc(sum(cts),2) cts\n" + 
                "    , round(sum(cts*nvl(upr, cmp))/1000) vlu\n" + 
                "    , round(sum(cts*(nvl(upr, cmp)))/sum(cts)) avg\n" + 
                "    , round(sum(cts*(nvl(upr, cmp))*cert_dys)/sum(cts*(nvl(upr, cmp)))) dys_avg\n" + 
                "  from mstk m, STK_CERT_DYS_MV dys\n" + 
                "  where 1 = 1 \n" + 
                "  and m.idn = dys.idn\n" + 
                "  and stt in ('MKAV', 'MKIS','SHIS', 'MKEI', 'MKWA', 'MKAP','MKKS_IS','MKOS_IS')\n" + 
                ")\n" + 
                ", SAL_TTL as (\n" + 
                "  select 'TTL' flg, round(sum(js.cts*nvl(js.fnl_sal, js.quot))/1000) vlu \n" + 
                "  from msal s, jansal js\n" + 
                "  where s.idn = js.idn and js.stt not in ('CL', 'RT') and trunc(js.dte) = trunc(sysdate) \n" + 
                ")\n" + 
                ", SAL_DEPT as (\n" + 
                "  select dept.val dpt\n" + 
                "    , round(sum(js.cts*nvl(js.fnl_sal, js.quot))/1000) vlu \n" + 
                "    , dept.srt\n" + 
                "  from msal s, jansal js, stk_dtl dept\n" + 
                "  where s.idn = js.idn and js.stt not in ('CL', 'RT') and trunc(js.dte) = trunc(sysdate) \n" + 
                "  and js.mstk_idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT'\n" + 
                "  group by dept.val, dept.srt\n" + 
                "  order by dept.srt\n" + 
                ")\n" + 
                "select sald.dpt dept, sald.vlu, to_char(trunc(sald.vlu*100/stkt.vlu, 3),90.99) salStkPct \n" + 
                ", salt.vlu stvlu, to_char(trunc(salt.vlu*100/stkt.vlu, 3),90.99) salPct \n" + 
                "from stk_ttl stkt, sal_ttl salt, sal_dept sald\n" + 
                "where 1 = 1 \n" + 
                "and stkt.flg = salt.flg\n" + 
                "order by sald.srt ";  
               rsLst = db.execSqlLst(typ, loadqry, new ArrayList());
               pst = (PreparedStatement)rsLst.get(0);
                rs = (ResultSet)rsLst.get(1);          
                try
                {
                while(rs.next()){
                    String dept =util.nvl(rs.getString("dept"));
                  livedata.put(dept+"SALVLU",util.nvl(rs.getString("vlu")));
                  livedata.put(dept+"SALPCT",util.nvl(rs.getString("salStkPct")));
                  livedata.put("STVLU",util.nvl(rs.getString("stvlu")));
                  livedata.put("SALTPCT",util.nvl(rs.getString("salPct")));
             if(!deptList.contains(dept)) {     
                deptList.add(dept);
             }
            }
                rs.close();
                pst.close();
                } catch (SQLException sqle) {
                    // TODO: Add catch code
                    sqle.printStackTrace();
                }
                req.setAttribute("deptList_"+typ, deptList);
                req.setAttribute("livedata_"+typ, livedata);
                req.setAttribute("hdrnme_"+typ, hdrnme);
                req.setAttribute("view", "Y");
                    if(dtl.equals(""))
                        util.updAccessLog(req,res,hdrnme, hdrnme);
                    else
                        util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
                req.setAttribute("livestockrfsh", livestockrfsh);
             
        return am.findForward("loadLivestockandsale");
            }
        }
    public ActionForward loadsale(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("sale"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            String hdrnme=util.nvl((String)req.getParameter("hdrsale"));
            String dsalerfsh=(String)req.getParameter("dsalerfsh");
            loaddaily(typ,dtl,hdrnme,req,res);
            req.setAttribute("hdrnme_"+typ, hdrnme);
            req.setAttribute("dsalerfsh", dsalerfsh);
        return am.findForward("loadsale");
            }
        }
    
    public ActionForward loadbrc(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("brc"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            String hdrnme=util.nvl((String)req.getParameter("hdrbrc"));
            String dsalerfsh=(String)req.getParameter("dbrcrfsh");
            loaddaily(typ,dtl,hdrnme,req,res);
            req.setAttribute("hdrnme_"+typ, hdrnme);
            req.setAttribute("dbrcrfsh", dsalerfsh);
        return am.findForward("loadbrc");
            }
        }
    public ActionForward loaddlv(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("dlv"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            String hdrnme=util.nvl((String)req.getParameter("hdrdlv"));
            String ddlvrfsh=(String)req.getParameter("ddlvrfsh");
            loaddaily(typ,dtl,hdrnme,req,res);
            req.setAttribute("ddlvrfsh", ddlvrfsh);
        return am.findForward("loaddlv");
            }
        }
    
    public void loaddaily(String typ,String dtl,String hdrnme,HttpServletRequest req, HttpServletResponse res)
            throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
        ArrayList dtlList = new ArrayList();
        String loadqry = " select to_char(sum(trunc(b.cts,2)*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))),'999,9999,999,990.00') vlu\n" + 
        ", d.emp_idn, byr.get_nm(nvl(d.emp_idn,0)) sale_name\n" + 
        ", d.co_nme byr, d.nme_idn byrid,b.typ typ\n" + 
        ", to_char(b.dte,'dd-mm-yyyy') dte\n" + 
        ", sum(b.qty) qty\n" + 
        ", to_char(sum(trunc(b.cts,2)),'999,990.99') cts\n" + 
        "from mjan a,jandtl b, mstk c,nme_v d where\n" + 
        "a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn\n" + 
        "and b.stt not in ('APRT','RT','CS','ALC','MRG') and b.typ in ('IAP', 'EAP','WAP','LAP','MAP','SAP','HAP','BAP','KAP','OAP','BIDAP')\n" + 
        "and trunc(b.dte) between trunc(sysdate) and trunc(sysdate)\n" + 
        "group by d.emp_idn, d.nme_idn, d.co_nme, to_char(b.dte,'dd-mm-yyyy') , b.typ\n" + 
        "Order By 1 Desc, 3, 4";
        
        if(typ.equals("sale")){
            loadqry="Select To_Char(Sum(Trunc(B.Cts,2)*(Nvl(B.Fnl_Sal, B.Quot)/Nvl(A.Exh_Rte,1))),'999,9999,999,990.00') Vlu,\n" + 
            "d.emp_idn, byr.get_nm(nvl(d.emp_idn,0)) sale_name, d.co_nme byr, d.nme_idn byrid,b.typ typ \n" + 
            "                      , to_char(b.dte,'dd-mm-yyyy') dte , sum(c.qty) qty , to_char(sum(trunc(c.cts,2)),'999,990.99') cts \n" + 
            "                      , to_char(sum(trunc(c.cts,2)*(nvl(b.fnl_usd, b.quot))),'999,9999,999,990.00') vlu  \n" + 
            "                     from msal a,Sal_Pkt_Dtl_V b, mstk c,nme_v d where\n" + 
            "                     a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn\n" + 
            "                   and b.stt not in ('RT','CS') and b.typ in ('SL','DLV') and c.stt in('MKSL', 'MKSD', 'BRC_MKSD') \n" + 
            "                     And Trunc(B.Dte)  Between Trunc(Sysdate) And Trunc(Sysdate)\n" + 
            "                     Group by d.emp_idn, d.nme_idn, d.co_nme, to_char(b.dte,'dd-mm-yyyy'),b.typ\n" + 
            "                    Order By 1 Desc, 3, 4";
        }
        if(typ.equals("dlv")){
            loadqry="select to_char(sum(trunc(vlu,2)),'999,9999,999,990.00') vlu ,emp_idn, sale_name, byr, byrid , typ, dte \n" + 
            "         , Sum(Qty) Qty, To_Char(Sum(Trunc(Cts,2)),'999,990.99') Cts \n" + 
            "        From ( Select Sum(Trunc(C.Cts,2)*(Nvl(B.Fnl_Usd, B.Quot))) Vlu,\n" + 
            "        d.emp_idn emp_idn , byr.get_nm(nvl(d.emp_idn,0)) sale_name \n" + 
            "        , d.co_nme byr , d.nme_idn byrid , b.typ typ \n" + 
            "        , to_char(b.dte,'dd-mm-yyyy') dte \n" + 
            "        , sum(c.qty) qty , sum(trunc(c.cts,2)) cts  \n" + 
            "        from mdlv a,Dlv_pkt_dtl_v b, mstk c,nme_v d\n" + 
            "        where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.stt in('MKSD', 'BRC_MKSD')\n" + 
            "        and b.stt not in ('RT','CS','DP','PS','CL') and b.typ in ('DLV') \n" + 
            "        and trunc(b.dte) between trunc(sysdate) and trunc(sysdate)\n" + 
            "        group by d.emp_idn, d.nme_idn, d.co_nme, to_char(b.dte,'dd-mm-yyyy'), b.typ\n" + 
            "         Union \n" + 
            "        select sum(trunc(c.cts,2)*(nvl(b.fnl_usd, b.quot))) vlu ,d.emp_idn emp_idn \n" + 
            "        , byr.get_nm(nvl(d.emp_idn,0)) sale_name  , 'MIX' byr , d.nme_idn byrid,b.typ typ \n" + 
            "        , to_char(b.dte,'dd-mm-yyyy') dte , sum(c.qty) qty , sum(trunc(c.cts,2)) cts\n" + 
            "        from msal a, Sal_pkt_dtl_v b, mstk c,nme_v d\n" + 
            "        where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.stt in('MKSD', 'BRC_MKSD')\n" + 
            "        and b.stt not in ('RT','CS') and a.typ in ('LS') \n" + 
            "        and trunc(b.dte) between trunc(sysdate) and trunc(sysdate)\n" + 
            "        group by d.emp_idn, d.nme_idn, d.co_nme, to_char(b.dte,'dd-mm-yyyy'), b.typ )\n" + 
            "        group by emp_idn, sale_name, byr, byrid, typ, dte   Order By 1 Desc, 3, 4";
                
        }
        if(typ.equals("CS")){
            loadqry="select to_char(sum(trunc(b.cts,2)*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))),'999,9999,999,990.00') vlu\n" + 
            "        , d.emp_idn, byr.get_nm(nvl(d.emp_idn,0)) sale_name \n" + 
            "        , d.co_nme byr, d.nme_idn byrid,b.typ typ\n" + 
            "        , to_char(b.dte,'dd-mm-yyyy') dte\n" + 
            "        , sum(b.qty) qty\n" + 
            "        , to_char(sum(trunc(b.cts,2)),'999,990.99') cts \n" + 
            "        from mjan a,jandtl b, mstk c,nme_v d where\n" + 
            "        A.Idn=B.Idn And B.Mstk_Idn=C.Idn And D.Nme_Idn=A.Nme_Idn\n" + 
            "        and b.stt='IS' and b.typ in ('CS')\n" + 
            "        and trunc(b.dte) between trunc(sysdate) and trunc(sysdate)\n" + 
            "        group by d.emp_idn, d.nme_idn, d.co_nme, to_char(b.dte,'dd-mm-yyyy') , b.typ\n" + 
            "        Order By 1 Desc, 3, 4";  
        }
            if(typ.equals("BRC")){
                loadqry="select '' sale_name, d.nme byr , d.nme_idn byrid ,\n" + 
                "to_char(b.dte_dlv,'dd-mm-yyyy') dte, sum (b.qty) qty ,\n" + 
                "to_char(sum(trunc(b.cts,2)),'999,990.99') cts ,     \n" + 
                "to_char(sum(trunc(nvl(b.fnl_sal,b.quot)*b.cts,2)),'999,9999,999,990.00') vlu\n" + 
                "from brc_mdlv a , brc_dlv_dtl b ,msal ms, mstk c , nme_v d , nme_v e\n" + 
                "where a.idn = b.idn and b.sal_idn=ms.idn and b.mstk_idn = c.idn \n" + 
                "and ms.nme_idn = d.nme_idn   and b.stt='DLV' and a.inv_nme_idn = e.nme_idn \n" + 
                "and  trunc(b.dte_dlv) between trunc(sysdate) and trunc(sysdate)      \n" + 
                "group by  d.nme, d.nme_idn, to_char(b.dte_dlv,'dd-mm-yyyy')\n" + 
                "order by d.nme";  
            }
        ArrayList rsLst = db.execSqlLst(typ, loadqry, new ArrayList());
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        int srno=0;
        while(rs.next()){
                    HashMap byrdata = new HashMap();
                    byrdata.put("SPERSON",util.nvl(rs.getString("sale_name")));
                    byrdata.put("BYR",util.nvl(rs.getString("byr")));
                    byrdata.put("BYRIDN",util.nvl(rs.getString("byrid")));
                    byrdata.put("DTE",util.nvl(rs.getString("dte")));
                    byrdata.put("QTY",util.nvl(rs.getString("qty")));
                    byrdata.put("CTS",util.nvl(rs.getString("cts")));
                    byrdata.put("VLU",util.nvl(rs.getString("vlu")));
                    dtlList.add(byrdata);
        }
        rs.close();
        pst.close();
        req.setAttribute("dtlList_"+typ, dtlList);
        req.setAttribute("hdrnme_"+typ, hdrnme);
        req.setAttribute("typ", typ);
        req.setAttribute("view", "Y");
            if(dtl.equals(""))
                util.updAccessLog(req,res,hdrnme, hdrnme);
            else
                util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
        }
    public ActionForward loadConsignmentSale(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
        String typ=util.nvl((String)req.getParameter("consignmentsale"));
        String dtl=util.nvl((String)req.getParameter("dtl"));
        String hdrnme=(String)req.getParameter("hdrconsignmentsale");
        String consignmentsalerfsh=(String)req.getParameter("consignmentsalerfsh");
        ArrayList dtlList = new ArrayList();
        String loadqry="select to_char(sum(trunc(b.cts,2)*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))),'999,9999,999,990.00') vlu\n" + 
            "        , d.emp_idn, byr.get_nm(nvl(d.emp_idn,0)) sale_name \n" + 
            "        , byr.get_nm(nvl(d.nme_idn,0)) byr, d.nme_idn byrid,b.typ typ\n" + 
            "        , to_char(b.dte,'dd-mm-yyyy') dte\n" + 
            "        , sum(b.qty) qty\n" + 
            "        , to_char(sum(trunc(b.cts,2)),'999,990.99') cts \n" + 
            "        from mjan a,jandtl b, mstk c,mnme d where\n" + 
            "        A.Idn=B.Idn And B.Mstk_Idn=C.Idn And D.Nme_Idn=A.Nme_Idn\n" + 
            "        and b.stt='CS' and b.typ in ('CS')\n" + 
            "        and trunc(b.dte_sal) between trunc(sysdate) and trunc(sysdate)\n" + 
            "        group by d.emp_idn, d.nme_idn, d.fnme, to_char(b.dte,'dd-mm-yyyy') , b.typ\n" + 
            "        Order By 1 Desc, 3, 4";  
        ArrayList rsLst = db.execSqlLst(typ, loadqry, new ArrayList());
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        int srno=0;
        while(rs.next()){
                    HashMap byrdata = new HashMap();
                    byrdata.put("SPERSON",util.nvl(rs.getString("sale_name")));
                    byrdata.put("BYR",util.nvl(rs.getString("byr")));
                    byrdata.put("BYRIDN",util.nvl(rs.getString("byrid")));
                    byrdata.put("DTE",util.nvl(rs.getString("dte")));
                    byrdata.put("QTY",util.nvl(rs.getString("qty")));
                    byrdata.put("CTS",util.nvl(rs.getString("cts")));
                    byrdata.put("VLU",util.nvl(rs.getString("vlu")));
                    dtlList.add(byrdata);
        }
        rs.close();
        pst.close();
        req.setAttribute("dtlList_"+typ, dtlList);
        req.setAttribute("hdrnme_"+typ, hdrnme);
        req.setAttribute("view", "Y");
            if(dtl.equals(""))
                util.updAccessLog(req,res,hdrnme, hdrnme);
            else
                util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
        req.setAttribute("consignmentsalerfsh", consignmentsalerfsh);
        return am.findForward("loadconsignmentsale");
        }
    }
    public ActionForward loadoffdf(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("df"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            String hdrnme=util.nvl((String)req.getParameter("hdrdf"));
            String offerdfrfsh=util.nvl((String)req.getParameter("offerdfrfsh"));
            loadoffer(typ,dtl,hdrnme,req,res);
            req.setAttribute("offerdfrfsh", offerdfrfsh);
        return am.findForward("loaddf");
            }
        }
    public ActionForward loadoffweb(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("web"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            String hdrnme=util.nvl((String)req.getParameter("hdrweb"));
            String offerwebrfsh=util.nvl((String)req.getParameter("offerwebrfsh"));
            loadoffer(typ,dtl,hdrnme,req,res);
         req.setAttribute("offerwebrfsh", offerwebrfsh);
        return am.findForward("loadweb");
         }
     }
    
    public void loadoffer(String typ,String dtl,String hdrnme,HttpServletRequest req, HttpServletResponse res)
            throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            ArrayList pktList = new  ArrayList();
            ArrayList byridnList = new  ArrayList();
            HashMap dataDtl=new HashMap();
            HashMap byrDtl=new HashMap();
        String loadqry = "Select Sk1, Cts Crtwt, Round(B.Ofr_Rte) Ofr_Rte , Round(B.Net_Rte) Net_Rte ,  B.Chg_Typ ,\n" + 
        "B.Diff, c.nme_idn byr_idn  , C.Nme byr ,  \n" + 
        "To_Char(Decode(B.Quot, 0, 0, Decode(Nvl(A.Rap_Dis,0), 0, 0,\n" + 
        "Trunc(((B.Quot*100/Decode(D.Cur, 'USD', 1, Get_Xrt))/Greatest(A.Rap_Rte,100)) - 100,2))),'90.00') R_Dis ,\n" + 
        "To_Char(Decode(A.Cmp, 0, 0, Decode(Nvl(A.Rap_Dis,0), 0, 0, Trunc(((A.Cmp*100)/Greatest(A.Rap_Rte,100)) - 100,2))),'90.00') Mr_Dis ,\n" + 
        "To_Char(Trunc((((B.Ofr_Rte)*100/(Decode(D.Cur, 'USD', 1, Get_Xrt)))/Greatest(A.Rap_Rte,100)) - 100,2),'90.00') Ofr_Dis ,\n" + 
        "To_Char(Trunc(((Round(B.Net_Rte)*100)/Greatest(A.Rap_Rte,100)) - 100,2),'90.00') Net_Dis ,  A.idn, B.Quot,\n" + 
        "A.Vnm, Cmp , Cert_Lab Cert, Cert_No,B.Idn , A.Rap_Rte , To_Char(Trunc(Cts,2),'90.99') Cts,\n" + 
        "B.Idn Bididn ,  A.Stt , B.Ofr_Rte , To_Char(B.To_Dt,'dd-Mon-rrrr') Todte \n" + 
        "From mstk A ,\n" + 
        "Web_Bid_Wl B , Nme_V C , Nmerel D Where A.Idn = B.Mstk_Idn \n" + 
        "And B.Byr_Idn = C.Nme_Idn And Trunc(Nvl(B.To_Dt, Sysdate)) >= Trunc(Sysdate) \n" + 
        "And  D.Nme_Idn = C.Nme_Idn And D.Nmerel_Idn = B.Rel_Idn  And B.Stt='A' And Trunc(B.Frm_Dt) Between Trunc(Sysdate) And Trunc(Sysdate)\n" + 
        "And B.Typ='BID' and b.flg='"+typ+"'  Order By C.Nme,b.ofr_rte";
       
        ArrayList rsLst = db.execSqlLst(typ, loadqry, new ArrayList());
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        int srno=0;
        while(rs.next()){
            String byr_idn=util.nvl(rs.getString("byr_idn"));
            if(!byridnList.contains(byr_idn)){
             byridnList.add(byr_idn);
             byrDtl.put(byr_idn, util.nvl(rs.getString("byr")));
             pktList = new  ArrayList();
            }
            HashMap pktPrpMap = new HashMap();
            pktPrpMap.put("cmp", util.nvl(rs.getString("cmp")));
            pktPrpMap.put("vnm",util.nvl(rs.getString("vnm")));
            pktPrpMap.put("stk_idn",util.nvl(rs.getString("idn")));
            pktPrpMap.put("offer_rte",util.nvl(rs.getString("ofr_rte")));
            pktPrpMap.put("offer_dis", util.nvl(rs.getString("ofr_dis")));
            pktPrpMap.put("net_rte",util.nvl(rs.getString("net_rte")));
            pktPrpMap.put("net_dis", util.nvl(rs.getString("net_dis")));
            pktPrpMap.put("dis", util.nvl(rs.getString("r_dis")));
            pktPrpMap.put("typ", util.nvl(rs.getString("chg_typ")));
            pktPrpMap.put("diff", util.nvl(rs.getString("diff")));
            pktPrpMap.put("mr_dis", util.nvl(rs.getString("mr_dis")));
            pktPrpMap.put("toDte", util.nvl(rs.getString("toDte")));
            pktPrpMap.put("cts",util.nvl(rs.getString("Crtwt")));
            pktPrpMap.put("quot", util.nvl(rs.getString("quot")));
            pktPrpMap.put("cmp", util.nvl(rs.getString("cmp")));
            pktList.add(pktPrpMap);
            dataDtl.put(byr_idn, pktList);
        }
        rs.close();
        pst.close();
        req.setAttribute("dataDtl_"+typ, dataDtl);
        req.setAttribute("byrdtl_"+typ, byrDtl);
        req.setAttribute("byridnLst_"+typ, byridnList);
        req.setAttribute("hdrnme_"+typ, hdrnme);
        req.setAttribute("view", "Y");
            if(dtl.equals(""))
                util.updAccessLog(req,res,hdrnme, hdrnme);
            else
                util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
        }
    
    public ActionForward loadwebaccess(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("webaccess"));
            String hdrnme=util.nvl((String)req.getParameter("hdrwebaccess"));
            String webaccessrfsh=util.nvl((String)req.getParameter("webaccessrfsh"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            ArrayList byrList = new  ArrayList();
            ArrayList pgList = new  ArrayList();
            HashMap dataDtl=new HashMap();
            String loadqry = "    select count(*) cnt,a.pg,byr.get_nm(d.nme_idn) byr,b.usr_id from\n" + 
            "web_access_log a,web_login_log b,web_usrs c,nmerel d,mnme e\n" + 
            "where a.log_id=b.log_id\n" + 
            "and B.USR_ID=c.usr_id\n" + 
            "and c.rel_idn=d.nmerel_idn\n" + 
            "and d.nme_idn=e.nme_idn\n" + 
            "and d.vld_dte is null\n" + 
            "and e.vld_dte is null\n" + 
            "and trunc(b.dt_tm)=trunc(sysdate)\n" + 
            "Group By A.Pg,Byr.Get_Nm(D.Nme_Idn),B.Usr_Id\n" + 
            "Order By a.pg,Cnt Desc";
            
            ArrayList rsLst = db.execSqlLst(typ, loadqry, new ArrayList());
            PreparedStatement pst = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while(rs.next()){
            String pg=util.nvl(rs.getString("pg"));
            if(!pgList.contains(pg)){
             pgList.add(pg);
             byrList = new  ArrayList();
            }
            HashMap pktPrpMap = new HashMap();
            pktPrpMap.put("byr", util.nvl(rs.getString("byr")));
            pktPrpMap.put("cnt",util.nvl(rs.getString("cnt")));
            byrList.add(pktPrpMap);
            dataDtl.put(pg, byrList);
            }
            rs.close();
            pst.close();
            req.setAttribute("dataDtl_"+typ, dataDtl);
            req.setAttribute("pgList_"+typ, pgList);
            req.setAttribute("hdrnme_"+typ, hdrnme);
            req.setAttribute("view", "Y");  
            req.setAttribute("webaccessrfsh", webaccessrfsh);
            if(dtl.equals(""))
                util.updAccessLog(req,res,hdrnme, hdrnme);
            else
                util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
        return am.findForward("loadwebaccess");
            }
        }
    
    public ActionForward loadstkaccess(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("stkaccess"));
            String hdrnme=util.nvl((String)req.getParameter("hdrstkaccess"));
            String webaccessrfsh=util.nvl((String)req.getParameter("stkaccessrfsh"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            int srno=0;
            ArrayList dtlList = new ArrayList();
            String loadqry = "select to_char(a.dt_tm, 'DD-MON HH24:mi') dte,byr.get_nm(d.nme_idn) byr,c.usr,a.pg \n" + 
            "from\n" + 
            "stk_access_log a,STK_LOGIN_LOG b,web_usrs c,nmerel d,mnme e\n" + 
            "where a.log_id=b.log_id\n" + 
            "and B.USR_ID=c.usr_id\n" + 
            "and c.rel_idn=d.nmerel_idn\n" + 
            "and d.nme_idn=e.nme_idn\n" + 
            "and d.vld_dte is null\n" + 
            "and e.vld_dte is null\n" + 
            "and trunc(a.dt_tm)=trunc(sysdate) and a.pg like 'WS:PacketList%' and a.flg like '%WS Start%'\n" + 
            "Order By 1,2 Desc";
            
            ArrayList rsLst = db.execSqlLst(typ, loadqry, new ArrayList());
            PreparedStatement pst = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while(rs.next()){
                srno = srno+1;
                HashMap dtls = new HashMap();
                dtls.put("SR No", String.valueOf(srno));
                dtls.put("Buyer", util.nvl(rs.getString("byr")));
                dtls.put("User", util.nvl(rs.getString("usr")));
                dtls.put("Dte", util.nvl(rs.getString("dte")));
                dtls.put("Pg", util.nvl(rs.getString("pg")));
                dtlList.add(dtls);
            }
            rs.close();
            pst.close();
                ArrayList itemHdr = new ArrayList();
                itemHdr.add("SR No");
                itemHdr.add("Buyer");
                itemHdr.add("User");
                itemHdr.add("Dte");
                itemHdr.add("Pg");
                req.setAttribute("itemHdrstkaccess",itemHdr);
                req.setAttribute("dtlListstkaccess", dtlList);
                req.setAttribute("hdrnmestkaccess", hdrnme);
                udf.setPktList(dtlList);
            req.setAttribute("stkaccessrfsh", webaccessrfsh);
            if(dtl.equals(""))
                util.updAccessLog(req,res,hdrnme, hdrnme);
            else
                util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
        return am.findForward("loadstkaccess");
            }
        }
    public ActionForward loaddfaccess(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
         String typ=util.nvl((String)req.getParameter("dfaccess"));
         String hdrnme=util.nvl((String)req.getParameter("hdrdfaccess"));
         String dtl=util.nvl((String)req.getParameter("dtl"));
         String dfaccessrfsh=util.nvl((String)req.getParameter("dfaccessrfsh"));
         ArrayList usrList = new  ArrayList();
         ArrayList pgList = new  ArrayList();
         HashMap dataDtl=new HashMap();
         String loadqry = "Select Count(*) cnt\n" + 
         ", lower(c.usr) usr\n" + 
         ",A.Pg pg\n" + 
         "From\n" + 
         "Df_Access_Log A,Df_Login_Log B,Df_Users C\n" + 
         "Where A.Log_Idn=B.Log_Idn\n" + 
         "And upper(trim(B.Df_Usr))=upper(trim(C.Usr))\n" + 
         "and trunc(b.log_dte)=trunc(sysdate)\n" + 
         "--and rownum<10\n" + 
         "Group By A.Pg,Byr.Get_Nm(C.Nme_Idn),C.Usr\n" + 
         "Order By a.pg,Cnt Desc";
         
         ArrayList rsLst = db.execSqlLst(typ, loadqry, new ArrayList());
         PreparedStatement pst = (PreparedStatement)rsLst.get(0);
         ResultSet rs = (ResultSet)rsLst.get(1);
         while(rs.next()){
         String pg=util.nvl(rs.getString("pg"));
         if(!pgList.contains(pg)){
          pgList.add(pg);
          usrList = new  ArrayList();
         }
         HashMap pktPrpMap = new HashMap();
         pktPrpMap.put("usr", util.nvl(rs.getString("usr")));
         pktPrpMap.put("cnt",util.nvl(rs.getString("cnt")));
         usrList.add(pktPrpMap);
         dataDtl.put(pg, usrList);
         }
         rs.close();
         pst.close();
         req.setAttribute("dataDtl_"+typ, dataDtl);
         req.setAttribute("pgList_"+typ, pgList);
         req.setAttribute("hdrnme_"+typ, hdrnme);
         req.setAttribute("dfaccessrfsh", dfaccessrfsh);   
         req.setAttribute("view", "Y");
         if(dtl.equals(""))
             util.updAccessLog(req,res,hdrnme, hdrnme);
         else
             util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
        return am.findForward("loaddfaccess");
         }
     }
    public ActionForward loadwebpending(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("webpending"));
            String hdrnme=util.nvl((String)req.getParameter("hdrwebpending"));
            String webpendingrfsh=util.nvl((String)req.getParameter("webpendingrfsh"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            ArrayList pktList = new  ArrayList();
            ArrayList ary=new ArrayList();
            HashMap dbinfo = info.getDmbsInfoLst();
            String sh = (String)dbinfo.get("SHAPE");
            String loadqry = "select byr.get_nm(rel_idn, 'R') byr, a.dte, c.vnm, c.stt ct_stt\n" + 
            " , sh.val, c.cts\n" + 
            " from web_minv a, web_inv_dtl b, mstk c, stk_dtl sh\n" + 
            " where a.inv_id = b.inv_id and alc_memo is null and b.mstk_idn = c.idn\n" + 
            " and c.idn = sh.mstk_idn and sh.grp = 1 and sh.mprp = ?\n" + 
            " and a.inv_typ = 'WA' and trunc(a.dte) >= trunc(sysdate) - 3\n" + 
            " and c.stt in ('MKAV', 'MKIS', 'MKEI','SHIS','MKKS_IS','MKOS_IS')\n" + 
            " order by a.dte, c.sk1";
            ary.add(sh);
            ArrayList rsLst = db.execSqlLst(typ, loadqry,ary);
            PreparedStatement pst = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while(rs.next()){
            HashMap pktPrpMap = new HashMap();
            pktPrpMap.put("byr", util.nvl(rs.getString("byr")));
            pktPrpMap.put("dte",util.nvl(rs.getString("dte")));
            pktPrpMap.put("vnm",util.nvl(rs.getString("vnm")));
            pktPrpMap.put("ct_stt",util.nvl(rs.getString("ct_stt")));
            pktPrpMap.put("sh",util.nvl(rs.getString("val")));
            pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
            pktList.add(pktPrpMap);
            }
            rs.close();
            pst.close();
            req.setAttribute("dataDtl_"+typ, pktList);
            req.setAttribute("hdrnme_"+typ, hdrnme);
            req.setAttribute("view", "Y");  
            req.setAttribute("webpendingrfsh", webpendingrfsh);
            if(dtl.equals(""))
                util.updAccessLog(req,res,hdrnme, hdrnme);
            else
                util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
        return am.findForward("loadwebpending");
            }
        }
    
    public ActionForward dfwebdtl(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("dfwebdtl"));
            String hdrnme=util.nvl((String)req.getParameter("hdrdfwebdtl"));
            String dfwebdtlrfsh=util.nvl((String)req.getParameter("dfwebdtlrfsh"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            ArrayList pktList = new  ArrayList();
            ResultSet rs           = null;
            String loadqry = "select count(*) cnt from web_login_Log where trunc(dt_tm)=trunc(sysdate)";
            rs = db.execSql("web_login_Log Details", loadqry, new ArrayList());
            while(rs.next()){
            HashMap pktPrpMap = new HashMap();
            pktPrpMap.put("CNT", util.nvl(rs.getString("cnt")));
            pktPrpMap.put("TYP", "WebSite Login");
            pktList.add(pktPrpMap);
            }
            rs.close();
            loadqry = "select count(*) cnt from web_Access_Log where trunc(dt_tm)=trunc(sysdate)";
            rs = db.execSql("web_Access_Log Details", loadqry, new ArrayList());
            while(rs.next()){
            HashMap pktPrpMap = new HashMap();
            pktPrpMap.put("CNT", util.nvl(rs.getString("cnt")));
            pktPrpMap.put("TYP", "WebSite Access");
            pktList.add(pktPrpMap);
            }
            rs.close();
            loadqry = "select count(*) cnt from df_login_Log where trunc(log_dte)=trunc(sysdate)";
            rs = db.execSql("df_login_Log Details", loadqry, new ArrayList());
            while(rs.next()){
            HashMap pktPrpMap = new HashMap();
            pktPrpMap.put("CNT", util.nvl(rs.getString("cnt")));
            pktPrpMap.put("TYP", "Diaflex Login");
            pktList.add(pktPrpMap);
            }
            rs.close();
            loadqry = "select count(*) cnt from Df_Access_Log where trunc(log_dte)=trunc(sysdate)";
            rs = db.execSql("Df_Access_Log Details", loadqry, new ArrayList());
            while(rs.next()){
            HashMap pktPrpMap = new HashMap();
            pktPrpMap.put("CNT", util.nvl(rs.getString("cnt")));
            pktPrpMap.put("TYP", "Diaflex Access");
            pktList.add(pktPrpMap);
            }
            rs.close();
            req.setAttribute("dataDtl_"+typ, pktList);
            req.setAttribute("hdrnme_"+typ, hdrnme);
            req.setAttribute("view", "Y");  
            req.setAttribute("dfwebdtlrfsh",dfwebdtlrfsh);
            if(dtl.equals(""))
                util.updAccessLog(req,res,hdrnme, hdrnme);
            else
                util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
        return am.findForward("loaddfwebdtl");
            }
        }
    public ActionForward loaddob(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
         String hdrnme=(String)req.getParameter("hdrdob");    
         String rfshdob=(String)req.getParameter("rfshdob");
         String dobreq=(String)req.getParameter("dobreq");
         String dtl=util.nvl((String)req.getParameter("dtl"));
         ArrayList dtlList=loaddobannidte(dobreq,dtl,hdrnme,req,res);
         udf.setPktDOBList(dtlList);
         req.setAttribute("rfshdob", rfshdob);
        return am.findForward("loaddob");
         }
     }
    public ActionForward loadannidte(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
         String hdrnme=(String)req.getParameter("hdradte");    
         String rfshadte=(String)req.getParameter("rfshadte");
         String adtereq=(String)req.getParameter("adtereq");
         String dtl=util.nvl((String)req.getParameter("dtl"));
         ArrayList dtlList=loaddobannidte(adtereq,dtl,hdrnme,req,res);
         udf.setPktADTEList(dtlList);
         req.setAttribute("rfshadte", rfshadte);
        return am.findForward("loadannidte");
         }
     }
    public ArrayList loaddobannidte(String typ,String dtl,String hdrnme,HttpServletRequest req, HttpServletResponse res)
            throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
            String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
            String usrFlg=util.nvl((String)info.getUsrFlg());
            ArrayList rolenmLst=(ArrayList)info.getRoleLst();
            String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
            ArrayList ary=new ArrayList();
        ArrayList dtlList = new ArrayList();
        String conQdte=" and to_char(to_date(br.anni_dte,'dd/mm/yyy'),'dd-mm')=to_char(sysdate,'dd-mm') ";
        if(typ.equals("DOB"))
            conQdte=" and to_char(to_date(br.dob,'dd/mm/yyy'),'dd-mm')=to_char(sysdate,'dd-mm') ";
        
        if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
            }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
            conQdte +=" and nvl(br.grp_nme_idn,0) =? "; 
            ary.add(dfgrpnmeidn);
        } 
        
        if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                conQdte += " and (br.emp_idn= ? or br.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
                ary.add(dfNmeIdn);
                ary.add(dfNmeIdn);
        }
        
        String loadqry = "select br.nme byr,em.nme sperson,br.typ,byr.get_AttrData('EMAIL',br.nme_idn) EMAIL,byr.get_AttrData('MOBILE',br.nme_idn) MOBILE,byr.get_AttrData('TEL_NO',br.nme_idn) TEL_NO \n" + 
        "from nme_v br,emp_v em\n" + 
        "where br.emp_idn=em.nme_idn\n" + conQdte+
        " order by br.typ desc,br.nme";
        
        ArrayList rsLst = db.execSqlLst("Dtl", loadqry, ary);
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        int srno=0;
        while(rs.next()){
          srno = srno+1;
          HashMap dtls = new HashMap();
          dtls.put("SR No", String.valueOf(srno));
          dtls.put("Person", util.nvl(rs.getString("byr")));
          dtls.put("Sale Person", util.nvl(rs.getString("sperson")));
          dtls.put("Type", util.nvl(rs.getString("typ")));
          dtls.put("Email", util.nvl(rs.getString("EMAIL")));
          dtls.put("Mobile", util.nvl(rs.getString("MOBILE")));
          dtls.put("Tel No", util.nvl(rs.getString("TEL_NO")));
          dtlList.add(dtls);
          if(dtl.length()==0 && srno==30){
          break;} 
        }
        rs.close();
        pst.close();
            ArrayList itemHdr = new ArrayList();
            itemHdr.add("SR No");
            itemHdr.add("Person");
            if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
            }else{
                itemHdr.add("Sale Person");
            }
            itemHdr.add("Type");
            itemHdr.add("Email");
            itemHdr.add("Mobile");
            itemHdr.add("Tel No");
            
            req.setAttribute("itemHdr_"+typ,itemHdr);
            req.setAttribute("dtlList_"+typ, dtlList);
            req.setAttribute("hdrnme_"+typ, hdrnme);
            if(dtl.equals(""))
                util.updAccessLog(req,res,hdrnme, hdrnme);
            else
                util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
        return dtlList;
        }
    public ActionForward loadpurbuy(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("purbuy"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            String hdrnme=util.nvl((String)req.getParameter("hdrpurbuy"));
            String purbuyrfsh=(String)req.getParameter("purbuyrfsh");
            loadpur(typ,dtl,hdrnme,req,res);
            req.setAttribute("purbuyrfsh", purbuyrfsh);
        return am.findForward("loadpurbuy");
            }
        }
    public ActionForward loadpurreview(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("purreview"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            String hdrnme=util.nvl((String)req.getParameter("hdrpurreview"));
            String purreviewrfsh=(String)req.getParameter("purreviewrfsh");
            loadpur(typ,dtl,hdrnme,req,res);
            req.setAttribute("purreviewrfsh", purreviewrfsh);
        return am.findForward("loadpurreview");
            }
        }
    public ActionForward loadpuroffer(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("puroffer"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            String hdrnme=util.nvl((String)req.getParameter("hdrpuroffer"));
            String purofferrfsh=(String)req.getParameter("purofferrfsh");
            loadpur(typ,dtl,hdrnme,req,res);
            req.setAttribute("purofferrfsh", purofferrfsh);
        return am.findForward("loadpuroffer");
            }
        }
    public void loadpur(String typ,String dtl,String hdrnme,HttpServletRequest req, HttpServletResponse res)
            throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
        ArrayList dtlList = new ArrayList();
        ArrayList ary = new ArrayList();
        ary.add(typ);
       String loadqry="select c.nme,count(*) qty, Trunc(sum(b.cts),2) cts\n" + 
       ",Trunc(Sum(b.rte*b.cts)/Sum(b.cts),2) avg,Trunc(Sum(b.rte*b.cts),2) vlu\n" + 
       "From Mpur a, Pur_Dtl b,nme_v c\n" + 
       "Where\n" + 
       "a.vndr_idn=c.nme_idn and b.Pur_Idn = a.Pur_Idn\n" + 
       "and a.typ=?\n" + 
       "and trunc(a.pur_dte) = trunc(sysdate)\n" + 
       "group by c.nme\n" + 
       "order by c.nme"  ;
        ArrayList rsLst = db.execSqlLst("Purchase Typ-"+typ, loadqry, ary);
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
            int srno=0;
        while(rs.next()){
            srno = srno+1;
                    HashMap purdata = new HashMap();
                    purdata.put("NME",util.nvl(rs.getString("nme")));
                    purdata.put("QTY",util.nvl(rs.getString("qty")));
                    purdata.put("CTS",util.nvl(rs.getString("cts")));
                    purdata.put("AVG",util.nvl(rs.getString("avg")));
                    purdata.put("VLU",util.nvl(rs.getString("vlu")));
                
                    dtlList.add(purdata);
            if(dtl.length()==0 && srno==30){
            break;} 
        }
        rs.close();
        pst.close();
        req.setAttribute("dtlList_"+typ, dtlList);
        req.setAttribute("hdrnme_"+typ, hdrnme);
        req.setAttribute("view", "Y");
            if(dtl.equals(""))
                util.updAccessLog(req,res,hdrnme, hdrnme);
            else
                util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
        }
    public ActionForward loadDailyMemoTypI(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("memotypI"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            String hdrnme=util.nvl((String)req.getParameter("hdrmemotypI"));
            String memotypIrfsh=(String)req.getParameter("memotypIrfsh");
            loadDailyMemoR(typ,dtl,hdrnme,req,res);
            req.setAttribute("memotypIrfsh", memotypIrfsh);
        return am.findForward("loadDailyMemoTypI");
            }
        }
    public ActionForward loadDailyMemoTypE(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("memotypE"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            String hdrnme=util.nvl((String)req.getParameter("hdrmemotypE"));
            String memotypErfsh=(String)req.getParameter("memotypErfsh");
            loadDailyMemoR(typ,dtl,hdrnme,req,res);
            req.setAttribute("memotypErfsh", memotypErfsh);
        return am.findForward("loadDailyMemoTypE");
            }
        }
    public ActionForward loadDailyMemoTypWH(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("memotypWH"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            String hdrnme=util.nvl((String)req.getParameter("hdrmemotypWH"));
            String memotypWHrfsh=(String)req.getParameter("memotypWHrfsh");
            loadDailyMemoR(typ,dtl,hdrnme,req,res);
            req.setAttribute("memotypWHrfsh", memotypWHrfsh);
        return am.findForward("loadDailyMemoTypWH");
            }
        }
    public ActionForward loadDailyMemoTypZ(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("memotypZ"));
            String dtl=util.nvl((String)req.getParameter("dtl"));
            String hdrnme=util.nvl((String)req.getParameter("hdrmemotypZ"));
            String memotypZrfsh=(String)req.getParameter("memotypZrfsh");
            loadDailyMemoR(typ,dtl,hdrnme,req,res);
            req.setAttribute("memotypZrfsh", memotypZrfsh);
        return am.findForward("loadDailyMemoTypZ");
            }
        }
    
    public void loadDailyMemoR( String typ,String dtl ,String hdrnme, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
        ArrayList dtlList = new ArrayList();
        ArrayList ary = new ArrayList();
       ary.add(typ);
        
       String loadqry="select trunc(sum(trunc(b.cts,2)*nvl(fnl_sal, quot)/a.exh_rte),2) vlu,a.nme_idn,a.emp, a.byr, count(distinct a.idn) cnt, sum(b.qty) qty, trunc(sum(trunc(b.cts,2)),2) cts from jan_v a, jandtl b,Nme_V c\n" + 
       "where a.idn = b.idn and c.Nme_Idn=A.Nme_Idn and trunc(a.dte) = trunc(sysdate)  \n" + 
       "and  a.typ=?\n" + 
       "group by a.nme_idn,a.emp, a.byr, a.typ order by 1 desc,a.emp, a.byr"  ;
        ArrayList rsLst = db.execSqlLst("Daily Memo Report"+typ, loadqry, ary);
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
            int srno=0;
        while(rs.next()){
            srno = srno+1;
                    HashMap purdata = new HashMap();
                        
                    purdata.put("EMP",util.nvl(rs.getString("emp")));
                    purdata.put("BYR",util.nvl(rs.getString("byr")));   
                    purdata.put("CNT",util.nvl(rs.getString("cnt")));
                    purdata.put("QTY",util.nvl(rs.getString("qty")));
                    purdata.put("CTS",util.nvl(rs.getString("cts")));
                    purdata.put("VLU",util.nvl(rs.getString("vlu")));
                    dtlList.add(purdata);
            if(dtl.length()==0 && srno==30){
            break;} 
        }
        rs.close();
        pst.close();
            req.setAttribute("dailyMemoR_"+typ, dtlList);
            req.setAttribute("hdrnme_"+typ, hdrnme);
            req.setAttribute("view", "Y");
            if(dtl.equals(""))
                util.updAccessLog(req,res,hdrnme, hdrnme);
            else
                util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
            
        }
    public ActionForward loaddailyinward(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                UserRightsForm udf = (UserRightsForm)af;
                HashMap dbinfo = info.getDmbsInfoLst();
                String shlprp = (String)dbinfo.get("SHAPE");
                String lotlprp = util.nvl((String)(String)dbinfo.get("LOTNO"),"LOTNO");
                String inwarddatelprp = util.nvl((String)(String)dbinfo.get("INWARDDATE"),"INWARD_DATE");
                String typ=util.nvl((String)req.getParameter("inward"));
                String dtl=util.nvl((String)req.getParameter("dtl"));
                String hdrnme=util.nvl((String)req.getParameter("hdrinward"));
                String dsalerfsh=(String)req.getParameter("dinwardrfsh");
                ArrayList dtlList = new ArrayList();
                String prvlotno="";
                String loadqry = "select nvl(lot.txt,'NA') lotno,DECODE (a1.pkt_ty, 'NR', decode(sh.val,'RO', 'ROUND', 'RD', 'ROUND','ROUND','ROUND', 'FANCY'), 'MIX') sh,\n" + 
                "ROUND(SUM (nvl(a1.qty,1)),2) qty,TO_CHAR(ROUND(SUM (decode(a1.pkt_ty, 'NR', trunc(a1.cts,2), a1.cts)),2),'999999990.99') cts\n" + 
                "FROM mstk a1, stk_dtl sh,stk_dtl ide,stk_dtl lot\n" + 
                "where \n" + 
                "a1.idn=sh.mstk_idn and sh.mprp='"+shlprp+"' and sh.grp=1 and\n" + 
                "a1.idn=ide.mstk_idn and ide.mprp='"+inwarddatelprp+"' and ide.grp=1 and trunc(ide.dte)=trunc(sysdate) and\n" + 
                "a1.idn=lot.mstk_idn and lot.mprp='"+lotlprp+"' and lot.grp=1 \n" + 
                "GROUP BY nvl(lot.txt,'NA'), DECODE (a1.pkt_ty, 'NR', decode(sh.val,'RO', 'ROUND', 'RD', 'ROUND','ROUND','ROUND', 'FANCY'), 'MIX')\n" + 
                "order by 1,2 desc";
                ArrayList rsLst = db.execSqlLst(typ, loadqry, new ArrayList());
                PreparedStatement pst = (PreparedStatement)rsLst.get(0);
                ResultSet rs = (ResultSet)rsLst.get(1);
                int srno=0;
                while(rs.next()){
                HashMap data = new HashMap();
                String lotno=util.nvl(rs.getString("lotno"));
                if(prvlotno.equals("") || !prvlotno.equals(lotno)){
                data.put("LOTNO",lotno);
                prvlotno=lotno;
                }else
                data.put("LOTNO","");   
                data.put("SHAPE",util.nvl(rs.getString("sh")));
                data.put("QTY",util.nvl(rs.getString("qty")));
                data.put("CRTWT",util.nvl(rs.getString("cts")));
                dtlList.add(data);
                }
                rs.close();
                pst.close();
                req.setAttribute("dtlList_"+typ, dtlList);
                req.setAttribute("hdrnme_"+typ, hdrnme);
                req.setAttribute("dsalerfsh", dsalerfsh);
                req.setAttribute("view", "Y");
                    if(dtl.equals(""))
                        util.updAccessLog(req,res,hdrnme, hdrnme);
                    else
                        util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
        return am.findForward("loaddailyinward");
            }
        }
    
    
    public ActionForward loadrapsyncpnd(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                UserRightsForm udf = (UserRightsForm)af;
                String typ=util.nvl((String)req.getParameter("rapsync"));
                String dtl=util.nvl((String)req.getParameter("dtl"));
                String hdrnme=util.nvl((String)req.getParameter("hdrrapsync"));
                String dsalerfsh=(String)req.getParameter("rapsyncrfsh");
                ArrayList countitemHdr=new ArrayList();
                HashMap rapsyncdata=new HashMap();
                ArrayList rapsyncdataLst=new ArrayList();
                ArrayList rapsyncdatadtl=new ArrayList();
                String countQ="select count(*) count, count(distinct a.vnm) vnm, decode(b.rapnet,'C','COMPETED','E','ERROR',b.rapnet) rapnetstt\n" + 
                "from rap_sync_log a, rap_sync_stt b\n" + 
                "Where A.Idn = B.Log_Idn And Trunc(A.Log_Tm) = Trunc(Sysdate)\n" + 
                "group by decode(b.rapnet,'C','COMPETED','E','ERROR',b.rapnet)";
                ResultSet rs = db.execSql("search Result", countQ, new ArrayList());
                while(rs.next()){
                    String rapnetstt=util.nvl(rs.getString("rapnetstt"));
                    rapsyncdata.put(rapnetstt+"_COUNT", util.nvl(rs.getString("count")));
                    rapsyncdata.put(rapnetstt+"_VNM", util.nvl(rs.getString("vnm")));
                    countitemHdr.add(rapnetstt);
                }
                rs.close();
                
                countQ=" select distinct vnm,to_char(s.log_tm, 'DD-MON HH24:mi') log_tm,decode(s.rapnet,'P','PENDING','E','ERROR',s.rapnet) rapnetstt \n" + 
                " From Rap_Sync_Log L, Rap_Sync_Stt S\n" + 
                "  where l.idn = s.log_idn and s.rapnet in('E','P')\n" + 
                "  And L.Act_Typ = 'UPD' And Trunc(L.Log_Tm) = Trunc(Sysdate)\n" + 
                "  order by 3";
                rs = db.execSql("search Result", countQ, new ArrayList());
                while(rs.next()){
                      rapsyncdatadtl=new ArrayList();
                      rapsyncdatadtl.add(util.nvl(rs.getString("rapnetstt")));
                      rapsyncdatadtl.add(util.nvl(rs.getString("vnm")));
                      rapsyncdatadtl.add(util.nvl(rs.getString("log_tm")));
                      rapsyncdataLst.add(rapsyncdatadtl);
                }
                rs.close();
                req.setAttribute("countitemHdr", countitemHdr);
                req.setAttribute("rapsyncdata", rapsyncdata);
                req.setAttribute("rapsyncdataLst", rapsyncdataLst);
                
                req.setAttribute("hdrnme_"+typ, hdrnme);
                req.setAttribute(typ+"rfsh", dsalerfsh);
                req.setAttribute("view", "Y");
                if(dtl.equals(""))
                        util.updAccessLog(req,res,hdrnme, hdrnme);
                else
                        util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
        return am.findForward("rapsync");
            }
        }
    
    public ActionForward loadhitratio(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                UserRightsForm udf = (UserRightsForm)af;
                SearchQuery query=new SearchQuery();
                String typ=util.nvl((String)req.getParameter("hitratio"));
                String dtl=util.nvl((String)req.getParameter("dtl"));
                String hdrnme=util.nvl((String)req.getParameter("hdrhitratio"));
                String dsalerfsh=(String)req.getParameter("hitratiorfsh");
                String hitratioprp=util.nvl((String)req.getParameter("hitratioprp"),"SHAPE");
                String hitratioClientTyp=util.nvl((String)req.getParameter("hitratioClientTyp"),"I");
                ArrayList countitemHdr=new ArrayList();
                HashMap hitratiodata=new HashMap();
                ArrayList hitratioprpLst=new ArrayList();
                ArrayList hitratiomemoTyp=new ArrayList();
                String countQ="WITH MEMO_TTL as (\n" + 
                "select count(*) cnt, jd.typ\n" + 
                "from mstk m, jandtl jd\n" + 
                "where 1 = 1\n" + 
                "and m.idn = jd.mstk_idn\n" + 
                "and m.pkt_ty = 'NR'\n" + 
                "and trunc(jd.dte) = trunc(sysdate)\n" + 
                "and jd.typ in ("+hitratioClientTyp+")\n" + 
                "and jd.stt not in ('CL', 'MRG')\n" + 
                "group by jd.typ\n" + 
                ")\n" + 
                ", MEMO_RT_SMRY as (\n" + 
                "select count(*) cnt, jd.typ, sd.val "+hitratioprp+"\n" + 
                "from mstk m, jandtl jd, stk_dtl sd\n" + 
                "where 1 = 1\n" + 
                "and m.idn = jd.mstk_idn\n" + 
                "and m.pkt_ty = 'NR'\n" + 
                "and m.idn = sd.mstk_idn\n" + 
                "AND SD.GRP = 1\n" + 
                "and sd.mprp = '"+hitratioprp+"'\n" + 
                "and trunc(jd.dte) = trunc(sysdate)\n" + 
                "and jd.typ in ("+hitratioClientTyp+")\n" + 
                "and jd.stt in ('APRT', 'RT')\n" + 
                "group by jd.typ, sd.val\n" + 
                ")\n" + 
                "select round(100 - (s.cnt*100/t.cnt)) CNF_PCT, t.typ, s."+hitratioprp+"\n" + 
                "from MEMO_TTL t, MEMO_RT_SMRY s\n" + 
                "where t.typ = s.typ\n" + 
                "order by s."+hitratioprp+", t.typ, 1";
                ResultSet rs = db.execSql("search Result", countQ, new ArrayList());
                while(rs.next()){
                    String prpval=util.nvl(rs.getString(hitratioprp));
                    String memotyp=util.nvl(rs.getString("typ"));
                    if(!hitratioprpLst.contains(prpval))
                    hitratioprpLst.add(prpval); 
                    if(!hitratiomemoTyp.contains(memotyp))
                    hitratiomemoTyp.add(memotyp); 
                    hitratiodata.put(memotyp+"_"+prpval,util.nvl(rs.getString("CNF_PCT")));
                }
                rs.close();
                req.setAttribute("hitratiodata", hitratiodata);
                req.setAttribute("hitratiomemoTyp", hitratiomemoTyp);
                req.setAttribute("hitratioprpLst", hitratioprpLst);
                req.setAttribute("hitratioprp", hitratioprp);
                req.setAttribute("hitratioClientTyp", hitratioClientTyp);
                req.setAttribute("hdrnme_"+typ, hdrnme);
                req.setAttribute(typ+"rfsh", dsalerfsh);
                req.setAttribute("view", "Y");
                query.getHitMemoType(req,res);
                if(dtl.equals(""))
                util.updAccessLog(req,res,hdrnme, hdrnme);
                else
                util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
        return am.findForward("hitratio");
            }
        }
    
    public ActionForward loadgrpcomp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            UserRightsForm udf = (UserRightsForm)af;
            GenericInterface genericInt = new GenericImpl();
            ArrayList vWPrpList=genericInt.genericPrprVw(req,res,"DASH_GRP_COMP","DASH_GRP_COMP"); 
            String lprp=util.nvl((String)req.getParameter("lprp"));
            String lprpval=util.nvl((String)req.getParameter("lprpval"));   
            udf.reset();
                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("DASH_GRP_COMP");
                if(pageDtl==null || pageDtl.size()==0){
                pageDtl=new HashMap();
                pageDtl=util.pagedef("DASH_GRP_COMP");
                allPageDtl.put("DASH_GRP_COMP",pageDtl);
                }
                info.setPageDetails(allPageDtl);
//                if(info.getSrchPrp()==null)
//                util.initPrpSrch();
            dayscomp(req,res,"0",lprp,lprpval);  
                
            ArrayList dashboardgrpList=new ArrayList();
            dashboardgrpList.add("MKT");
            dashboardgrpList.add("SOLD");
            dashboardgrpList.add("NEWMKT");
            dashboardgrpList.add("NEWSOLD");
                
            session.setAttribute("dashboardgrpList", dashboardgrpList);
            session.setAttribute("filterlprpLst", util.useDifferentArrayList(vWPrpList));
            graphdataDtl(req,res,new ArrayList(),"");
            udf.setValue("lprp",lprp);
            udf.setValue("lprpval",lprpval);
            udf.setValue("BTN_DAYS","0");
            return am.findForward("loadgrpcomp");
            }
        }
    
    public ActionForward fetchgrpcomp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            UserRightsForm udf = (UserRightsForm)af;
                ArrayList  grpList = (ArrayList)session.getAttribute("grpList");
                String days = util.nvl((String)udf.getValue("BTN_DAYS"),"3");
                String lprp=util.nvl((String)udf.getValue("lprp"));
                String lprpval=util.nvl((String)udf.getValue("lprpval")); 
                GenericInterface genericInt = new GenericImpl();
                ArrayList vWPrpList=genericInt.genericPrprVw(req,res,"DASH_GRP_COMP","DASH_GRP_COMP"); 
                dayscomp(req,res,days,lprp,lprpval);    
                req.setAttribute("days", days);
                udf.setValue("lprp",lprp);
                udf.setValue("lprpval",lprpval);
                session.setAttribute("filterlprpLst", util.useDifferentArrayList(vWPrpList));
                graphdataDtl(req,res,new ArrayList(),"");
                udf.setValue("BTN_DAYS",days);
            return am.findForward("loadgrpcomp");
            }
        }
    
    public ActionForward filtergrpcomp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            UserRightsForm udf = (UserRightsForm)af;
            String filterlprp = util.nvl((String)udf.getValue("filterlprp"));
                String days = util.nvl((String)udf.getValue("BTN_DAYS"),"3");
                String  filterDsc = util.nvl((String)session.getAttribute("filterDsc"));
                String reset = util.nvl((String)udf.getValue("Reset"));
                ArrayList selectLst=new ArrayList();
                HashMap prp = info.getSrchPrp();
                HashMap mprp = info.getMprp();
                if(!reset.equals("")){
                    
                    ArrayList  grpList = (ArrayList)session.getAttribute("grpList");
                    String lprp=util.nvl((String)udf.getValue("lprp"));
                    String lprpval=util.nvl((String)udf.getValue("lprpval")); 
                    GenericInterface genericInt = new GenericImpl();
                    udf.reset();
                    ArrayList vWPrpList=genericInt.genericPrprVw(req,res,"DASH_GRP_COMP","DASH_GRP_COMP"); 
                    ArrayList quickpktStkIdnList=(ArrayList)session.getAttribute("quickpktStkIdnList");
                    session.setAttribute("fltquickpktStkIdnList", util.useDifferentArrayList(quickpktStkIdnList));
                    session.setAttribute("filterDsc", ""); 
                    req.setAttribute("days", days);
                    udf.setValue("lprp",lprp);
                    udf.setValue("lprpval",lprpval);
                    udf.setValue("filterlprp",vWPrpList.get(0));
                    session.setAttribute("filterlprpLst", util.useDifferentArrayList(vWPrpList));
                    graphdataDtl(req,res,new ArrayList(),"");
                    udf.setValue("BTN_DAYS",days);
                    return am.findForward("loadgrpcomp");
                }
                String lprp = filterlprp;
                String prpSrt = lprp ;  
                ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                for(int j=0; j < lprpS.size(); j++) {
                  String lSrt = (String)lprpS.get(j);
                  String lVal = (String)lprpV.get(j);
                  String lFld =  lprp + "_" + lVal; 
                  String reqVal = util.nvl((String)udf.getValue(lFld));
                    if(reqVal.length() > 0 && !reqVal.equals("0")) {  
                      selectLst.add(reqVal);
                  }
                }   
                graphdataDtl(req,res,selectLst,filterlprp);
                if(!filterlprp.equals("")){
                    String lprpDsc = util.nvl((String)mprp.get(filterlprp+"D"));
                    if(selectLst.size()==0 && filterDsc.equals("")){
                    filterDsc+=" "+lprpDsc+ " : ALL";
                    }else if(selectLst.size()!=0 && !filterDsc.equals("")){
                        String selGrp=selectLst.toString();
                        selGrp = selGrp.replaceAll("\\[", "");
                        selGrp = selGrp.replaceAll("\\]", "");
                        selGrp = selGrp.replaceAll(" ", "");
                        filterDsc+=" "+lprpDsc+ " : "+selGrp;
                    }else{
                        String selGrp=selectLst.toString();
                        selGrp = selGrp.replaceAll("\\[", "");
                        selGrp = selGrp.replaceAll("\\]", "");
                        selGrp = selGrp.replaceAll(" ", "");
                        filterDsc+=" "+lprpDsc+ " : "+selGrp;
                    }
                }
                session.setAttribute("filterDsc", filterDsc);
                req.setAttribute("days", days);
                udf.setValue("BTN_DAYS",days);
            return am.findForward("loadgrpcomp");
            }
        }
    
    public void graphdataDtl(HttpServletRequest req,HttpServletResponse res,ArrayList selectLst,String filterlprp)throws Exception{
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        GenericInterface genericInt = new GenericImpl();
        ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "DASH_GRP_COMP_ALL","DASH_GRP_COMP_ALL");
        ArrayList dataDtlGrpDashboard=(ArrayList)session.getAttribute("dashboardgrpList");
        HashMap quickpktListMap=(HashMap)session.getAttribute("quickpktListMap");
        ArrayList quickpktStkIdnList=(ArrayList)session.getAttribute("quickpktStkIdnList");
        ArrayList filterlprpLst=(ArrayList)session.getAttribute("filterlprpLst");
        ArrayList fltquickpktStkIdnList=(ArrayList)session.getAttribute("fltquickpktStkIdnList");
        ArrayList lstfltquickpktStkIdnList=new ArrayList();
        HashMap pktPrpMap = new HashMap();
        HashMap graphdataDtl = new HashMap();
        int vWPrpListsz=vWPrpList.size();
        int fltquickpktStkIdnListsz=fltquickpktStkIdnList.size();
        if(!filterlprp.equals("") && selectLst.size()!=0)
        filterlprpLst.remove(filterlprpLst.indexOf(filterlprp));
        double qty = 0 ;
        double vlu = 0 ;
        double cts = 0 ;
        HashMap mprp = info.getMprp();
        for(int i=0;i<fltquickpktStkIdnListsz;i++){
            String stkIdn=(String)fltquickpktStkIdnList.get(i);
            pktPrpMap = new HashMap();
            pktPrpMap=(HashMap)quickpktListMap.get(stkIdn) ;
            String lprpval=util.nvl((String)pktPrpMap.get(filterlprp));
            if(selectLst.contains(lprpval) || selectLst.size()==0){
            lstfltquickpktStkIdnList.add(stkIdn);
            String dsp_stt=util.nvl((String)pktPrpMap.get("dsp_stt"));
                String age_grp=util.nvl((String)pktPrpMap.get("dys"));
                String curlprpqty = util.nvl((String)pktPrpMap.get("qty"),"0").trim();
                String curcts = util.nvl((String)pktPrpMap.get("cts"),"0").trim();
                for(int j=0;j<vWPrpListsz;j++){
                    String vwlprp=(String)vWPrpList.get(j);
                    String lprpTyp = util.nvl((String)mprp.get(vwlprp+"T"));
                    if(lprpTyp.equals("N")){
                        BigDecimal currBigCts = new BigDecimal(curcts);
                        String curlprprte = util.nvl((String)pktPrpMap.get(vwlprp),"0").trim();
                        if(curlprprte.equals("NA"))
                        curlprprte="0";
                        BigDecimal currBigLprpVlu = new BigDecimal(curlprprte);
                        BigDecimal lprpBigvlu = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+vwlprp+"_VLU");
                        if(lprpBigvlu==null)
                        lprpBigvlu=new BigDecimal(vlu);
                        if(!curlprprte.equals("0")){
                        currBigLprpVlu=currBigLprpVlu.multiply(currBigCts);
                        lprpBigvlu = lprpBigvlu.add(currBigLprpVlu);
                        }
                        graphdataDtl.put(dsp_stt+"_"+vwlprp+"_VLU", lprpBigvlu);
                       }
                }
                BigDecimal currBigLprpQty = new BigDecimal(curlprpqty);
                BigDecimal lprpBigqty = (BigDecimal)graphdataDtl.get(dsp_stt+"_QTY");
                if(lprpBigqty==null)
                lprpBigqty=new BigDecimal(qty);
                if(!curlprpqty.equals("0"))
                lprpBigqty = lprpBigqty.add(currBigLprpQty);
                graphdataDtl.put(dsp_stt+"_QTY", lprpBigqty);
                
                BigDecimal currBigLprpCts = new BigDecimal(curcts);
                BigDecimal lprpBigcts = (BigDecimal)graphdataDtl.get(dsp_stt+"_CTS");
                if(lprpBigcts==null)
                lprpBigcts=new BigDecimal(cts);
                if(!curcts.equals("0"))
                lprpBigcts = lprpBigcts.add(currBigLprpCts);
                graphdataDtl.put(dsp_stt+"_CTS", lprpBigcts);
            }
        }
        session.setAttribute("filterlprpLst", filterlprpLst);
        session.setAttribute("fltquickpktStkIdnList", lstfltquickpktStkIdnList);
        session.setAttribute("graphdataDtl", graphdataDtl);
    } 
    
    public void dayscomp(HttpServletRequest req,HttpServletResponse res,String days,String lprp,String lprpval)throws Exception{
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList params=new ArrayList();
        db.execUpd("gt delete","delete from gt_srch_rslt", new ArrayList());
        GenericInterface genericInt = new GenericImpl();
        ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "DASH_GRP_COMP_ALL","DASH_GRP_COMP_ALL");
        ArrayList pktStkIdnList = new ArrayList();
        HashMap pktListMap = new HashMap();
        HashMap pktPrpMap = new HashMap();
        ArrayList grpList=new ArrayList();
        int vWPrpListsz=vWPrpList.size();
        ArrayList agegrplst=new ArrayList();
        grpList.add("MKT");
        grpList.add("SOLD");

        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("DASH_GRP_COMP");
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="",removeplusminus="N";
        pageList= ((ArrayList)pageDtl.get("PLUSMINUS") == null)?new ArrayList():(ArrayList)pageDtl.get("PLUSMINUS");
        if(pageList!=null && pageList.size() >0){
        for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
            if(dflt_val.equals("Y"))
                removeplusminus="Y";
        }}
        long lStartTime = new Date().getTime();
        String proc="DP_REP_DASHBOARD(pStkGrp => ? \n" + 
        "    , pDteFrm => to_date(trunc(sysdate-?), 'dd-mm-rrrr') , pDteTo => to_date(trunc(sysdate), 'dd-mm-rrrr'))";
        if(!lprp.equals("")){
        proc="DP_REP_DASHBOARD(pStkGrp => ? \n" + 
                    "    , pDteFrm => to_date(trunc(sysdate-?), 'dd-mm-rrrr') , pDteTo => to_date(trunc(sysdate), 'dd-mm-rrrr'),pLprp => ?,pLprpVal => ?)";  
        }
        
        for(int i=0;i<grpList.size();i++) {
        params=new ArrayList();
        params.add(grpList.get(i));
        params.add(days);
        if(!lprp.equals("")){
        params.add(lprp);
        params.add(lprpval);
        }
        int ct = db.execCall("DP_REP_DASHBOARD", proc, params);  
        }
        
        ArrayList param = new ArrayList();
        String mprpStr = "";
        String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||Upper(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1')) str From Rep_Prp Rp Where rp.MDL = ? and flg ='Y' order by srt " ;
        param = new ArrayList();
        param.add("DASH_GRP_COMP_ALL");
        ResultSet rs = db.execSql("mprp ", mdlPrpsQ , param);
        while(rs.next()) {
        String val = util.nvl((String)rs.getString("str"));
        mprpStr = mprpStr +" "+val;
        }
        rs.close();
        
        String rsltQ = " with \n" + 
        "         STKDTL as \n" + 
        "         ( select c.dsp_stt,c.stt,b.sk1, nvl(nvl(txt,num),val) atr , mprp,c.stk_idn, b.vnm ,b.cts,b.qty,c.mkt_dys,nvl(c.sl_rte,c.cmp) rteQ, b.rap_rte from stk_dtl a, mstk b , gt_srch_rslt c\n" + 
        "         Where 1=1 \n" + 
        "         and a.mstk_idn = b.idn and b.idn = c.stk_idn \n" + 
        "         and exists (select 1 from rep_prp rp where rp.MDL = ? and a.mprp = rp.prp and rp.flg='Y') \n" + 
        "         And a.Grp = 1) \n" + 
        "         Select * from stkDtl PIVOT \n" + 
        "         ( max(atr) " +
        " for mprp in ( "+mprpStr+" ) ) order by 1,3 " ;
        
        param = new ArrayList();
        param.add("DASH_GRP_COMP_ALL");
        rs = db.execSql("search Result", rsltQ, param);
        long colllEndTime = new Date().getTime();
        while (rs.next()) {
            pktPrpMap = new HashMap();
            String stkIdn = util.nvl(rs.getString("stk_idn"));
            String dys = "AGE-GRP-"+util.nvl(rs.getString("mkt_dys"));
            pktPrpMap.put("vnm",util.nvl(rs.getString("vnm")));
            pktPrpMap.put("dsp_stt",util.nvl(rs.getString("dsp_stt")));
            pktPrpMap.put("stt",util.nvl(rs.getString("stt")));
            pktPrpMap.put("sk1",util.nvl(rs.getString("sk1")));
            pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
            pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
            pktPrpMap.put("rte",util.nvl(rs.getString("rteQ")));
            pktPrpMap.put("rap_rte",util.nvl(rs.getString("rap_rte")));
            pktPrpMap.put("dys",dys);
            if(!agegrplst.contains(dys))
            agegrplst.add(dys);
            for (int i = 0; i < vWPrpListsz; i++) {
            String vwPrp = (String)vWPrpList.get(i);
            String vwPrp1=vwPrp;
            if(vwPrp.toUpperCase().equals("SIZE"))
            vwPrp1 = "SIZE1";
            if(vwPrp.toUpperCase().equals("RTE"))
            vwPrp1 = "rteQ";
            String fldVal = util.nvl((String)rs.getString(vwPrp1));     
            if(removeplusminus.equals("Y") && !vwPrp.equals("SZ") && !vwPrp.equals("SIZE1")){
                fldVal=fldVal.replaceAll("\\+", "");
                fldVal=fldVal.replaceAll("\\-", "");
            }
            pktPrpMap.put(vwPrp, fldVal);
            }
            pktListMap.put(stkIdn, pktPrpMap);
            pktStkIdnList.add(stkIdn);
        }
        rs.close();
        long lEndTime = new Date().getTime();
        long difference = lEndTime - lStartTime;
        long difference1 = lEndTime - colllEndTime;
        System.out.println("Collection using piovet Time =>"+util.convertMillis(difference1));
        System.out.println("Total Time =>"+util.convertMillis(difference));
        session.setAttribute("quickpktStkIdnList", pktStkIdnList);
        session.setAttribute("agegrplst", agegrplst);
        session.setAttribute("fltquickpktStkIdnList", util.useDifferentArrayList(pktStkIdnList));
        session.setAttribute("quickpktListMap", pktListMap);
        session.setAttribute("filterDsc", "");
    } 
    
    public ActionForward grpcomparasionBarChart(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            res.setContentType("text/xml"); 
            res.setHeader("Cache-Control", "no-cache"); 
            StringBuffer sb = new StringBuffer();
            String graphby=util.nvl((String)req.getParameter("graphby"),"VLU");
            ArrayList dataDtlGrpDashboard=(ArrayList)session.getAttribute("dashboardgrpList");
            HashMap graphdataDtl=(HashMap)session.getAttribute("graphdataDtl");
            GenericInterface genericInt = new GenericImpl();
            ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "DASH_GRP_COMP_ALL","DASH_GRP_COMP_ALL");
            int vWPrpListsz=vWPrpList.size();
            HashMap mprp = info.getMprp();
            int dataDtlGrpDashboardsz=dataDtlGrpDashboard.size();
            double qty = 0 ;
            double vlu = 0 ;
            double divide = 1000 ;
            BigDecimal divideBig=new BigDecimal(divide);
            
            for (int i=0;i<dataDtlGrpDashboardsz;i++){
            String dsp_stt=(String)dataDtlGrpDashboard.get(i);
                    sb.append("<subTag>");
                    sb.append("<attrNme>"+dsp_stt+"</attrNme>");
                    for (int j=0;j<vWPrpListsz;j++){
                    String vwlprp=(String)vWPrpList.get(j);
                    String lprpTyp = util.nvl((String)mprp.get(vwlprp+"T"));
                    if(lprpTyp.equals("N")){
                    BigDecimal sumdsp_sttBigvlu = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+vwlprp+"_"+graphby);
                    if(dsp_stt.equals("MKT") || dsp_stt.equals("SOLD")){
                        BigDecimal sumdsp_sttBigvlu_add = (BigDecimal)graphdataDtl.get("NEW"+dsp_stt+"_"+vwlprp+"_"+graphby);
                        if(sumdsp_sttBigvlu!=null && sumdsp_sttBigvlu_add!=null)
                        sumdsp_sttBigvlu = sumdsp_sttBigvlu.add(sumdsp_sttBigvlu_add);
                        if(sumdsp_sttBigvlu==null && sumdsp_sttBigvlu_add!=null){
                        sumdsp_sttBigvlu=new BigDecimal(vlu);
                        sumdsp_sttBigvlu = sumdsp_sttBigvlu.add(sumdsp_sttBigvlu_add);
                        }
                    }
                    vwlprp=vwlprp.replaceAll("_", "");
                    if(sumdsp_sttBigvlu!=null){
                    sb.append("<"+vwlprp+">"+(sumdsp_sttBigvlu.divide(divideBig,0,RoundingMode.HALF_EVEN))+"</"+vwlprp+">");
                    }else{
                    sb.append("<"+vwlprp+">0.0</"+vwlprp+">");  
                    }
                    }
                    }
             sb.append("</subTag>"); 
            }
            
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
            return null;
        }
    
    public ActionForward grpcomparasionDataGrid(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            res.setContentType("text/xml"); 
            res.setHeader("Cache-Control", "no-cache"); 
            StringBuffer sb = new StringBuffer();
            String graphby=util.nvl((String)req.getParameter("graphby"),"VLU");
            ArrayList dataDtlGrpDashboard=(ArrayList)session.getAttribute("dashboardgrpList");
            HashMap graphdataDtl=(HashMap)session.getAttribute("graphdataDtl");
            GenericInterface genericInt = new GenericImpl();
            ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "DASH_GRP_COMP_ALL","DASH_GRP_COMP_ALL");
            int vWPrpListsz=vWPrpList.size();
            HashMap mprp = info.getMprp();
            int dataDtlGrpDashboardsz=dataDtlGrpDashboard.size();
            double qty = 0 ;
            double vlu = 0 ;
            double divide = 1000 ;
            BigDecimal divideBig=new BigDecimal(divide);
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("DASH_GRP_COMP");
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            
            String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="",diffprp="";
            pageList= ((ArrayList)pageDtl.get("DIFF_PRP") == null)?new ArrayList():(ArrayList)pageDtl.get("DIFF_PRP");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                diffprp=util.nvl((String)pageDtlMap.get("dflt_val"));
            }}
            for (int i=0;i<dataDtlGrpDashboardsz;i++){
            String dsp_stt=(String)dataDtlGrpDashboard.get(i);
                    BigDecimal dsp_sttBigcts = (BigDecimal)graphdataDtl.get(dsp_stt+"_CTS");
                    BigDecimal dsp_sttBigqty = (BigDecimal)graphdataDtl.get(dsp_stt+"_QTY");
                    sb.append("<subTag>");
                    sb.append("<attrNme>"+dsp_stt+"</attrNme>");
                    if(dsp_sttBigcts!=null)
                    sb.append("<CTS>"+dsp_sttBigcts+"</CTS>");
                    else
                    sb.append("<CTS>0</CTS>");
                    if(dsp_sttBigqty!=null)
                    sb.append("<QTY>"+dsp_sttBigqty+"</QTY>");
                    else
                    sb.append("<QTY>0</QTY>");
                
                    BigDecimal sumdsp_sttBigvludiffprp = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+diffprp+"_"+graphby);
                    for (int j=0;j<vWPrpListsz;j++){
                    String vwlprp=(String)vWPrpList.get(j);
                    String lprpTyp = util.nvl((String)mprp.get(vwlprp+"T"));
                    if(lprpTyp.equals("N")){
                    BigDecimal sumdsp_sttBigvlu = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+vwlprp+"_"+graphby);
                    vwlprp=vwlprp.replaceAll("_", "");
                    if(sumdsp_sttBigvludiffprp!=null){
                    if(sumdsp_sttBigvlu!=null){
                    sb.append("<"+vwlprp+"_AVG>"+(sumdsp_sttBigvlu.divide(dsp_sttBigcts,0,RoundingMode.HALF_EVEN))+"</"+vwlprp+"_AVG>");
                    sb.append("<"+vwlprp+"_VLU>"+(sumdsp_sttBigvlu.divide(divideBig,0,RoundingMode.HALF_EVEN))+"</"+vwlprp+"_VLU>");
                    sb.append("<"+vwlprp+"_DIFF>"+((sumdsp_sttBigvlu.subtract(sumdsp_sttBigvludiffprp).divide(sumdsp_sttBigvludiffprp,4,RoundingMode.HALF_EVEN)).multiply(new BigDecimal(100))).divide(new BigDecimal(1), 2, RoundingMode.HALF_EVEN)+"</"+vwlprp+"_DIFF>");
                    }else{
                    sb.append("<"+vwlprp+"_AVG>0</"+vwlprp+"_AVG>"); 
                    sb.append("<"+vwlprp+"_VLU>0</"+vwlprp+"_VLU>");  
                    sb.append("<"+vwlprp+"_DIFF>0</"+vwlprp+"_DIFF>");  
                    }
                    }else{
                        if(sumdsp_sttBigvlu!=null){
                        sb.append("<"+vwlprp+"_AVG>"+(sumdsp_sttBigvlu.divide(dsp_sttBigcts,0,RoundingMode.HALF_EVEN))+"</"+vwlprp+"_AVG>");
                        sb.append("<"+vwlprp+"_VLU>"+(sumdsp_sttBigvlu.divide(divideBig,0,RoundingMode.HALF_EVEN))+"</"+vwlprp+"_VLU>");
                        sb.append("<"+vwlprp+"_DIFF>0</"+vwlprp+"_DIFF>");
                        }else{
                        sb.append("<"+vwlprp+"_AVG>0</"+vwlprp+"_AVG>");  
                        sb.append("<"+vwlprp+"_VLU>0</"+vwlprp+"_VLU>");  
                        sb.append("<"+vwlprp+"_DIFF>0</"+vwlprp+"_DIFF>");  
                        }
                    }
                    }
                    }
             sb.append("</subTag>"); 
            }
            
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
            return null;
        }
    
    public ActionForward grpcomparasionBarChartPrpWise(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            res.setContentType("text/xml"); 
            res.setHeader("Cache-Control", "no-cache"); 
            StringBuffer sb = new StringBuffer();
            String lprp=util.nvl((String)req.getParameter("filterlprp"));
            String graphby=util.nvl((String)req.getParameter("graphby"),"VLU");
            String pietyp=util.nvl((String)req.getParameter("pietyp"),"PRP");
            ArrayList dataDtlGrpDashboard=(ArrayList)session.getAttribute("dashboardgrpList");
            ArrayList fltquickpktStkIdnList=(ArrayList)session.getAttribute("fltquickpktStkIdnList");
            int fltquickpktStkIdnListsz=fltquickpktStkIdnList.size();
            HashMap quickpktListMap=(HashMap)session.getAttribute("quickpktListMap");
            HashMap prp = info.getSrchPrp();
            HashMap graphdataDtl=new HashMap();
            ArrayList diffDisplayLst=new ArrayList();
                double qty = 0 ;
                double vlu = 0 ;
            double divide = 1000 ;
            BigDecimal divideBig=new BigDecimal(divide);
            HashMap pktPrpMap = new HashMap();
             ArrayList  prpList = (ArrayList)prp.get(lprp+"V");
             ArrayList  prpGrpList = (ArrayList)prp.get(lprp+"G");
             int dataDtlGrpDashboardsz=dataDtlGrpDashboard.size();
            for(int i=0;i<fltquickpktStkIdnListsz;i++){
                String stkIdn=(String)fltquickpktStkIdnList.get(i);
                pktPrpMap = new HashMap();
                pktPrpMap=(HashMap)quickpktListMap.get(stkIdn) ;
                String lprpval=util.nvl((String)pktPrpMap.get(lprp));
                
                if(pietyp.equals("GRP")){
                int indexGrp=prpList.indexOf(lprpval);
                if(indexGrp>-1)
                lprpval=util.nvl((String)prpGrpList.get(indexGrp));
                else
                lprpval="NN";
                }
                
                String dsp_stt=util.nvl((String)pktPrpMap.get("dsp_stt"));
                String qtyval=util.nvl((String)pktPrpMap.get("qty"),"0").trim();
                String cts = util.nvl((String)pktPrpMap.get("cts"),"0").trim();
                String rteval = util.nvl((String)pktPrpMap.get("rte"),"0").trim(); 
                if(rteval.equals("NA"))
                rteval="0";
                            BigDecimal currBigCts = new BigDecimal(cts);
                            BigDecimal currBigLprpVlu = new BigDecimal(rteval);
                            BigDecimal lprpBigvlu = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+lprpval+"_VLU");
                            if(lprpBigvlu==null)
                            lprpBigvlu=new BigDecimal(vlu);
                            if(!rteval.equals("0")){
                            currBigLprpVlu=currBigLprpVlu.multiply(currBigCts);
                            lprpBigvlu = lprpBigvlu.add(currBigLprpVlu);
                            }
                            graphdataDtl.put(dsp_stt+"_"+lprpval+"_VLU", lprpBigvlu);
                            
                            BigDecimal currBigLprpQty = new BigDecimal(qtyval);
                            BigDecimal lprpBigqty = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+lprpval+"_QTY");
                            if(lprpBigqty==null)
                            lprpBigqty=new BigDecimal(qty);
                            if(!qtyval.equals("0"))
                            lprpBigqty = lprpBigqty.add(currBigLprpQty);
                            graphdataDtl.put(dsp_stt+"_"+lprpval+"_QTY", lprpBigqty);
            }
            
             if(pietyp.equals("GRP"))
             diffDisplayLst=util.useDifferentArrayListUnique(prpGrpList);
             else
             diffDisplayLst=util.useDifferentArrayList(prpList);
             int diffDisplayLstsz=diffDisplayLst.size();
            
             for(int i=0; i<diffDisplayLstsz; i++){              
               String  prpV = util.nvl((String)diffDisplayLst.get(i));                 
                 BigDecimal mktqty = (BigDecimal)graphdataDtl.get("MKT_"+prpV+"_QTY");
                 BigDecimal soldqty = (BigDecimal)graphdataDtl.get("SOLD_"+prpV+"_QTY");
                 BigDecimal newmktqty = (BigDecimal)graphdataDtl.get("NEWMKT_"+prpV+"_QTY");
                 BigDecimal newsoldqty = (BigDecimal)graphdataDtl.get("NEWSOLD_"+prpV+"_QTY");
                 if(mktqty!=null || soldqty!=null  || newmktqty!=null  || newsoldqty!=null ){
                     BigDecimal mktvlu = (BigDecimal)graphdataDtl.get("MKT_"+prpV+"_VLU");
                     BigDecimal soldvlu = (BigDecimal)graphdataDtl.get("SOLD_"+prpV+"_VLU");
                     BigDecimal newmktvlu = (BigDecimal)graphdataDtl.get("NEWMKT_"+prpV+"_VLU");
                     BigDecimal newsoldvlu= (BigDecimal)graphdataDtl.get("NEWSOLD_"+prpV+"_VLU");
                 if(mktqty==null)
                 mktqty=new BigDecimal(qty);  
                 if(soldqty==null)
                 soldqty=new BigDecimal(qty);
                 if(newmktqty==null)
                 newmktqty=new BigDecimal(qty);
                 if(newsoldqty==null)
                 newsoldqty=new BigDecimal(qty);
                 
                     if(mktvlu==null)
                     mktvlu=new BigDecimal(vlu);  
                     if(soldvlu==null)
                     soldvlu=new BigDecimal(vlu);
                     if(newmktvlu==null)
                     newmktvlu=new BigDecimal(vlu);
                     if(newsoldvlu==null)
                     newsoldvlu=new BigDecimal(vlu);
                     
                     if (prpV.indexOf("&") > -1)
                     prpV = prpV.replaceAll("&", "%26");
                     if (prpV.indexOf("\"") > -1)
                     prpV = prpV.replaceAll("\"", "%22");
                     if (prpV.indexOf("(") > -1)
                     prpV = prpV.replaceAll("\\(", "%28");
                     if (prpV.indexOf(")") > -1)
                     prpV = prpV.replaceAll("\\)", "%29");
                     if (prpV.indexOf("/") > -1)
                     prpV = prpV.replaceAll("\\/", "%2F");
                     if (prpV.indexOf("*") > -1)
                     prpV = prpV.replaceAll("\\*", "%2A");
                     if (prpV.indexOf("!") > -1)
                     prpV = prpV.replaceAll("\\!", "%21");
                     if (prpV.indexOf("$") > -1)
                     prpV = prpV.replaceAll("\\$", "%24");
                     if (prpV.indexOf("%") > -1)
                     prpV = prpV.replaceAll("\\%", "%25");
                     if (prpV.indexOf("'") > -1)
                     prpV = prpV.replaceAll("\\'", "%27");
                     if (prpV.indexOf("-") > -1)
                     prpV = prpV.replaceAll("\\-", "%2D");
                     if (prpV.indexOf(".") > -1)
                     prpV = prpV.replaceAll("\\.", "%2E");
                     if (prpV.indexOf("?") > -1)
                     prpV = prpV.replaceAll("\\?", "%3F");
                     if (prpV.indexOf(" ") > -1)
                     prpV = prpV.replaceAll(" ", "%20");
                     
                 sb.append("<subTag>");
                 sb.append("<attrNme>"+prpV+"</attrNme>");
                 sb.append("<MKTQty>"+mktqty+"</MKTQty>");
                 sb.append("<MKTVlu>"+(mktvlu.divide(divideBig,0,RoundingMode.HALF_EVEN))+"</MKTVlu>");
                 sb.append("<SOLDQty>"+soldqty+"</SOLDQty>");
                 sb.append("<SOLDVlu>"+(soldvlu.divide(divideBig,0,RoundingMode.HALF_EVEN))+"</SOLDVlu>");
                 
                 sb.append("<NEWMKTQty>"+newmktqty+"</NEWMKTQty>");
                 sb.append("<NEWMKTVlu>"+(newmktvlu.divide(divideBig,0,RoundingMode.HALF_EVEN))+"</NEWMKTVlu>");
                 sb.append("<NEWSOLDQty>"+newsoldqty+"</NEWSOLDQty>");
                 sb.append("<NEWSOLDVlu>"+(newsoldvlu.divide(divideBig,0,RoundingMode.HALF_EVEN))+"</NEWSOLDVlu>");
                 sb.append("</subTag>");
                 }
             }
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
            return null;
        }
    
    public ActionForward grpcomparasionPiePrpWise(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            res.setContentType("text/xml"); 
            res.setHeader("Cache-Control", "no-cache"); 
            StringBuffer sb = new StringBuffer();
            String lprp=util.nvl((String)req.getParameter("filterlprp"));
            String dspstt = util.nvl((String)req.getParameter("dsp_stt"));
            String graphby=util.nvl((String)req.getParameter("graphby"),"VLU");
            String pietyp=util.nvl((String)req.getParameter("pietyp"),"PRP");
            ArrayList dataDtlGrpDashboard=(ArrayList)session.getAttribute("dashboardgrpList");
            ArrayList fltquickpktStkIdnList=(ArrayList)session.getAttribute("fltquickpktStkIdnList");
            int fltquickpktStkIdnListsz=fltquickpktStkIdnList.size();
            HashMap quickpktListMap=(HashMap)session.getAttribute("quickpktListMap");
            HashMap prp = info.getSrchPrp();
            HashMap graphdataDtl=new HashMap();
            ArrayList diffDisplayLst=new ArrayList();
            if(dspstt.equals(""))
            dspstt=util.nvl((String)req.getParameter("stt"));
                double qty = 0 ;
                double vlu = 0 ;
            double divide = 1000 ;
            BigDecimal divideBig=new BigDecimal(divide);
            HashMap pktPrpMap = new HashMap();
            ArrayList  prpList = (ArrayList)prp.get(lprp+"V");
            ArrayList  prpGrpList = (ArrayList)prp.get(lprp+"G");
             int prpListsz=prpList.size();
             int dataDtlGrpDashboardsz=dataDtlGrpDashboard.size();
            for(int i=0;i<fltquickpktStkIdnListsz;i++){
                String stkIdn=(String)fltquickpktStkIdnList.get(i);
                pktPrpMap = new HashMap();
                pktPrpMap=(HashMap)quickpktListMap.get(stkIdn) ;
                String lprpval=util.nvl((String)pktPrpMap.get(lprp));
                if(pietyp.equals("GRP")){
                int indexGrp=prpList.indexOf(lprpval);
                    if(indexGrp>-1)
                lprpval=util.nvl((String)prpGrpList.get(indexGrp));
                else
                lprpval="NN";
                }
                String dsp_stt=util.nvl((String)pktPrpMap.get("dsp_stt"));
                if(dsp_stt.equalsIgnoreCase(dspstt)){
                String qtyval=util.nvl((String)pktPrpMap.get("qty"),"0").trim();
                String cts = util.nvl((String)pktPrpMap.get("cts"),"0").trim();
                String rteval = util.nvl((String)pktPrpMap.get("rte"),"0").trim(); 
                if(rteval.equals("NA"))
                rteval="0";
                            BigDecimal currBigQty = new BigDecimal(qtyval);
                            BigDecimal currBigCts = new BigDecimal(cts);
                            BigDecimal currBigLprpVlu = new BigDecimal(rteval);
                            BigDecimal lprpBigvlu = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+lprpval+"_VLU");
                            if(lprpBigvlu==null)
                            lprpBigvlu=new BigDecimal(vlu);
                            if(!rteval.equals("0")){
                            currBigLprpVlu=currBigLprpVlu.multiply(currBigCts);
                            lprpBigvlu = lprpBigvlu.add(currBigLprpVlu);
                            }
                            graphdataDtl.put(dsp_stt+"_"+lprpval+"_VLU", lprpBigvlu);
                            
                            BigDecimal currBigLprpQty = new BigDecimal(qtyval);
                            BigDecimal lprpBigqty = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+lprpval+"_QTY");
                            if(lprpBigqty==null)
                            lprpBigqty=new BigDecimal(qty);
                            if(!qtyval.equals("0"))
                            lprpBigqty = lprpBigqty.add(currBigLprpQty);
                            graphdataDtl.put(dsp_stt+"_"+lprpval+"_QTY", lprpBigqty);
                }
            }
            
            if(pietyp.equals("GRP"))
            diffDisplayLst=util.useDifferentArrayListUnique(prpGrpList);
            else
            diffDisplayLst=util.useDifferentArrayList(prpList);
            int diffDisplayLstsz=diffDisplayLst.size();
            
             for(int i=0; i<diffDisplayLstsz; i++){              
               String  prpV = util.nvl((String)diffDisplayLst.get(i));
               BigDecimal prpValue  = (BigDecimal)graphdataDtl.get(dspstt+"_"+prpV+"_"+graphby);
                 if(prpValue!=null){
                     
                     if (prpV.indexOf("&") > -1)
                                          prpV = prpV.replaceAll("&", "%26");
                                          if (prpV.indexOf("\"") > -1)
                                          prpV = prpV.replaceAll("\"", "%22");
                                          if (prpV.indexOf("(") > -1)
                                          prpV = prpV.replaceAll("\\(", "%28");
                                          if (prpV.indexOf(")") > -1)
                                          prpV = prpV.replaceAll("\\)", "%29");
                                          if (prpV.indexOf("/") > -1)
                                          prpV = prpV.replaceAll("\\/", "%2F");
                                          if (prpV.indexOf("*") > -1)
                                          prpV = prpV.replaceAll("\\*", "%2A");
                                          if (prpV.indexOf("!") > -1)
                                          prpV = prpV.replaceAll("\\!", "%21");
                                          if (prpV.indexOf("$") > -1)
                                          prpV = prpV.replaceAll("\\$", "%24");
                                          if (prpV.indexOf("%") > -1)
                                          prpV = prpV.replaceAll("\\%", "%25");
                                          if (prpV.indexOf("'") > -1)
                                          prpV = prpV.replaceAll("\\'", "%27");
                                          if (prpV.indexOf("-") > -1)
                                          prpV = prpV.replaceAll("\\-", "%2D");
                                          if (prpV.indexOf(".") > -1)
                                          prpV = prpV.replaceAll("\\.", "%2E");
                                          if (prpV.indexOf("?") > -1)
                                          prpV = prpV.replaceAll("\\?", "%3F");
                                          if (prpV.indexOf(" ") > -1)
                                          prpV = prpV.replaceAll(" ", "%20");
                     sb.append("<subTag>");
                     sb.append("<attrNme>"+prpV+"</attrNme>");
                     if(graphby.equals("VLU"))
                     sb.append("<attrVal>"+(prpValue.divide(divideBig,0,RoundingMode.HALF_EVEN))+"</attrVal>");
                     else
                     sb.append("<attrVal>"+prpValue+"</attrVal>");
                     sb.append("</subTag>");
                 }
              }
             
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
            return null;
        }
    
    public ActionForward grpcomparasionagegrpBarChart(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            res.setContentType("text/xml"); 
            res.setHeader("Cache-Control", "no-cache"); 
            StringBuffer sb = new StringBuffer();
            String graphby=util.nvl((String)req.getParameter("graphby"),"VLU");
            ArrayList dataDtlGrpDashboard=(ArrayList)session.getAttribute("dashboardgrpList");
            ArrayList fltquickpktStkIdnList=(ArrayList)session.getAttribute("fltquickpktStkIdnList");
            ArrayList agegrplst=(ArrayList)session.getAttribute("agegrplst");
            int fltquickpktStkIdnListsz=fltquickpktStkIdnList.size();
            HashMap quickpktListMap=(HashMap)session.getAttribute("quickpktListMap");
            HashMap prp = info.getSrchPrp();
            HashMap graphdataDtl=new HashMap();
                double qty = 0 ;
                double vlu = 0 ;
            double divide = 1000 ;
            BigDecimal divideBig=new BigDecimal(divide);
            HashMap pktPrpMap = new HashMap();
             int dataDtlGrpDashboardsz=dataDtlGrpDashboard.size();
            for(int i=0;i<fltquickpktStkIdnListsz;i++){
                String stkIdn=(String)fltquickpktStkIdnList.get(i);
                pktPrpMap = new HashMap();
                pktPrpMap=(HashMap)quickpktListMap.get(stkIdn) ;
                String lprpval=util.nvl((String)pktPrpMap.get("dys"));
                String dsp_stt=util.nvl((String)pktPrpMap.get("dsp_stt"));
                String qtyval=util.nvl((String)pktPrpMap.get("qty"),"0").trim();
                String cts = util.nvl((String)pktPrpMap.get("cts"),"0").trim();
                String rteval = util.nvl((String)pktPrpMap.get("rte"),"0").trim(); 
                if(dsp_stt.equals("NEWMKT"))
                dsp_stt="MKT";
                if(dsp_stt.equals("NEWSOLD"))
                dsp_stt="SOLD";
                        if(graphby.equals("VLU")){
                            if(rteval.equals("NA"))
                            rteval="0";
                            BigDecimal currBigCts = new BigDecimal(cts);
                            BigDecimal currBigLprpVlu = new BigDecimal(rteval);
                            BigDecimal lprpBigvlu = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+lprpval+"_VLU");
                            if(lprpBigvlu==null)
                            lprpBigvlu=new BigDecimal(vlu);
                            if(!rteval.equals("0")){
                            currBigLprpVlu=currBigLprpVlu.multiply(currBigCts);
                            lprpBigvlu = lprpBigvlu.add(currBigLprpVlu);
                            }
                            graphdataDtl.put(dsp_stt+"_"+lprpval+"_VLU", lprpBigvlu);
                        }else{
                            BigDecimal currBigLprpQty = new BigDecimal(qtyval);
                            BigDecimal lprpBigqty = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+lprpval+"_QTY");
                            if(lprpBigqty==null)
                            lprpBigqty=new BigDecimal(qty);
                            if(!qtyval.equals("0"))
                            lprpBigqty = lprpBigqty.add(currBigLprpQty);
                            graphdataDtl.put(dsp_stt+"_"+lprpval+"_QTY", lprpBigqty);
                        }
            }
             
                 int agegrplstsz=agegrplst.size();
                 for (int i=0;i<dataDtlGrpDashboardsz;i++){
                             String dsp_stt=(String)dataDtlGrpDashboard.get(i);
                     if(!dsp_stt.equals("NEWMKT") && !dsp_stt.equals("NEWSOLD")){
                                     sb.append("<subTag>");
                                     sb.append("<attrNme>"+dsp_stt+"</attrNme>");
                                     for (int j=0;j<agegrplstsz;j++){
                                     String vwlprp=(String)agegrplst.get(j);
                                     BigDecimal sumdsp_sttBigvlu = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+vwlprp+"_"+graphby);
                                     vwlprp=vwlprp.replaceAll("_", "");
                                     if(sumdsp_sttBigvlu!=null){
                                     if(graphby.equals("VLU")){
                                     sb.append("<"+vwlprp+">"+(sumdsp_sttBigvlu.divide(divideBig,0,RoundingMode.HALF_EVEN))+"</"+vwlprp+">");
                                     }else{
                                     sb.append("<"+vwlprp+">"+sumdsp_sttBigvlu+"</"+vwlprp+">");  
                                     }
                                     }else{
                                     sb.append("<"+vwlprp+">0.0</"+vwlprp+">");  
                                     }
                                     }
                              sb.append("</subTag>"); 
                     }
                 }
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
            return null;
        }
    public ActionForward grpcomparasionpie(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            res.setContentType("text/xml"); 
            res.setHeader("Cache-Control", "no-cache"); 
            StringBuffer sb = new StringBuffer();
            String lprp=util.nvl((String)req.getParameter("lprp"));
            String pietyp=util.nvl((String)req.getParameter("pietyp"),"VLU");
            String pieprp=util.nvl((String)req.getParameter("pieprp"),"PRP");
            String dsp_stt=util.nvl((String)req.getParameter("stt"));
            HashMap dataDtlGrpDashboard=(HashMap)session.getAttribute("dataDtlGrpDashboard");
            HashMap dataDtl=(HashMap)dataDtlGrpDashboard.get(lprp);
            ArrayList prpList=new ArrayList();
            prpList = (ArrayList)dataDtlGrpDashboard.get(lprp+"_"+pieprp);
            int prpListsz=prpList.size();
            if(prpList!=null){
            for(int i=0;i<prpListsz;i++){
            String prpVal=(String)prpList.get(i);
            String pietypvlu=util.nvl((String)dataDtl.get(pieprp+"_"+dsp_stt+"_"+prpVal+"_"+pietyp));
            if(!pietypvlu.equals("")){
            sb.append("<subTag>");
            sb.append("<attrNme>"+util.nvl(prpVal,"NF")+"</attrNme>");
            sb.append("<attrVal>"+pietypvlu+"</attrVal>");
            sb.append("</subTag>");
            }
            }
            }
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
            return null;
        }
    
    public ActionForward stackGrpChart(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            res.setContentType("text/xml"); 
            res.setHeader("Cache-Control", "no-cache"); 
            StringBuffer sb = new StringBuffer();
            String lprp=util.nvl((String)req.getParameter("lprp"));
            String pieprp=util.nvl((String)req.getParameter("pieprp"),"PRP");
            HashMap dataDtlGrpDashboard=(HashMap)session.getAttribute("dataDtlGrpDashboard");
            HashMap dataDtl=(HashMap)dataDtlGrpDashboard.get(lprp);
            ArrayList prpList=new ArrayList();
            prpList = (ArrayList)dataDtlGrpDashboard.get(lprp+"_"+pieprp);
            int prpListsz=prpList.size();
            if(prpList!=null){
            for(int i=0;i<prpListsz;i++){
            String prpVal=(String)prpList.get(i);
            String totalinward=util.nvl((String)dataDtl.get(pieprp+"_TOTALINWARD_"+prpVal+"_QTY"));
            if(!totalinward.equals("")){
                String sold=util.nvl((String)dataDtl.get(pieprp+"_SOLD_"+prpVal+"_QTY"),"0");
                String newsold=util.nvl((String)dataDtl.get(pieprp+"_NEWSOLD_"+prpVal+"_QTY"),"0");
                sb.append("<subTag>");
                sb.append("<attrNme>"+prpVal+"</attrNme>");   
                sb.append("<attrVal>0XML"+totalinward+"XML"+newsold+"XML"+sold+"XML</attrVal>");
                sb.append("</subTag>");
            }
            }
            }
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
            return null;
        }
    
    public ActionForward netloadgrpcomp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                UserRightsForm udf = (UserRightsForm)af;
                ArrayList grpList=new ArrayList();
                grpList.add("MKT");
                grpList.add("SOLD");
                netdayscomp(req,res,grpList,"0");    
                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("DASH_GRP_COMP");
                if(pageDtl==null || pageDtl.size()==0){
                pageDtl=new HashMap();
                pageDtl=util.pagedef("DASH_GRP_COMP");
                allPageDtl.put("DASH_GRP_COMP",pageDtl);
                }
                info.setPageDetails(allPageDtl);
                session.setAttribute("grpList", grpList);
                return am.findForward("netloadgrpcomp");
                }
            }
    
    public void netdayscomp(HttpServletRequest req,HttpServletResponse res,ArrayList grpList,String days)throws Exception{
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList params=new ArrayList();
    //        String trfdtefr="";
    //        String trfdteto="";
        db.execUpd("gt delete","delete from gt_srch_rslt", new ArrayList());
        
    //        String dtePrpQ="select to_char(trunc(sysdate-?), 'dd-mm-rrrr') frmdte,to_char(trunc(sysdate), 'dd-mm-rrrr') todte from dual";
    //        params=new ArrayList();
    //        params.add(days);
    //        ResultSet rs = db.execDirSql("Date calc", dtePrpQ,params);
    //        while (rs.next()) {
    //        trfdtefr=util.nvl(rs.getString("frmdte"));
    //        trfdteto=util.nvl(rs.getString("todte"));
    //        }
    //        rs.close();
        
        String proc="DP_REP_DASHBOARD(pStkGrp => ? \n" + 
        "    , pDteFrm => to_date(trunc(sysdate-?), 'dd-mm-rrrr') , pDteTo => to_date(trunc(sysdate), 'dd-mm-rrrr'))";
        for(int i=0;i<grpList.size();i++) {
        params=new ArrayList();
        params.add(grpList.get(i));
        params.add(days);
        int ct = db.execCall("DP_REP_DASHBOARD", proc, params);  
        }
        
        String pktPrp = "DP_GT_PRP_UPD(pMdl =>?, pTyp =>?, pRPM => 'Y')";
        ArrayList ary = new ArrayList();
        ary.add("DASH_GRP_COMP");
        ary.add("VAL");
        int ct = db.execCall(" Srch Prp ", pktPrp, ary);
        if(ct>0){
        GenericInterface genericInt = new GenericImpl();
        ArrayList vWPrpList = genericInt.genericPrprVw(req,res,"DASH_GRP_COMP","DASH_GRP_COMP"); 
        ArrayList  pktdtlList = new ArrayList(); 
        HashMap    prpwiseLst =new  HashMap();
            for(int i=0; i<vWPrpList.size(); i++)  { 
            String lprp=(String)vWPrpList.get(i);
            HashMap pktdtl =new HashMap();
            int indexLB = vWPrpList.indexOf(lprp)+1;
            String lbPrp = util.prpsrtcolumn("prp",indexLB);
            String lbSrt = util.prpsrtcolumn("srt",indexLB);
            pktdtlList = new ArrayList();
            String sqlQ="select "+lbPrp+" prp,"+lbSrt+" srt,sum(qty) qty,To_Char(sum(nvl(sl_rte,cmp)*cts),'99999990.99') vlu ,trunc(sum(nvl(sl_rte,cmp)*trunc(cts,2))/sum(trunc(cts, 2)),0) avg, dsp_stt \n" + 
            "    from gt_srch_rslt where 1=1 and dsp_stt in ('MKT','SOLD')\n" + 
            "    group by "+lbPrp+","+lbSrt+" ,dsp_stt\n" + 
            "    order by 2";  
            ResultSet rs = db.execSql("stkStt", sqlQ , new ArrayList());
             while (rs.next()) { 
                 String dsp_stt=util.nvl((String)rs.getString("dsp_stt"));
                 String prp=util.nvl((String)rs.getString("prp"));
                 pktdtl.put(dsp_stt+"_"+prp+"_QTY", util.nvl((String)rs.getString("qty")));
                 pktdtl.put(dsp_stt+"_"+prp+"_VLU", util.nvl((String)rs.getString("vlu")));
                 pktdtl.put(dsp_stt+"_"+prp+"_AVG", util.nvl((String)rs.getString("avg")));
             }
             rs.close();
             prpwiseLst.put(lprp,pktdtl);              
            }
        session.setAttribute("dspktdtlList", prpwiseLst);
        }
    } 
    
    public ActionForward netfetchgrpcomp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            UserRightsForm udf = (UserRightsForm)af;
                ArrayList  grpList = (ArrayList)session.getAttribute("grpList");
                String days = util.nvl((String)udf.getValue("BTN_DAYS"),"3");
                netdayscomp(req,res,grpList,days);
                req.setAttribute("days", days);
            return am.findForward("netloadgrpcomp");
            }
        }
    
    public ActionForward netgrpcomparasionpie(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
                HttpSession session = req.getSession(false);
                InfoMgr info = (InfoMgr)session.getAttribute("info");
                DBUtil util = new DBUtil();
                DBMgr db = new DBMgr(); 
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                res.setContentType("text/xml"); 
                res.setHeader("Cache-Control", "no-cache"); 
                StringBuffer sb = new StringBuffer();
                String lprp=util.nvl((String)req.getParameter("lprp"));
                String pietyp=util.nvl((String)req.getParameter("pietyp"),"VLU");
                String dsp_stt=util.nvl((String)req.getParameter("stt"));
                 HashMap pktList =(HashMap)session.getAttribute("dspktdtlList");
                 HashMap prpwiseLst =(HashMap)pktList.get(lprp);
                 HashMap prp = info.getPrp();
                 ArrayList  prpList = (ArrayList)prp.get(lprp+"V");
                 for(int i=0; i<prpList.size(); i++){              
                   String  prpV = util.nvl((String)prpList.get(i));
                   String qty = util.nvl((String)prpwiseLst.get(dsp_stt+"_"+prpV+"_QTY"));
                     if(!qty.equals("")){
                     sb.append("<subTag>");
                     sb.append("<attrNme>"+prpV+"</attrNme>");
                     if(pietyp.equals("QTY"))
                     sb.append("<attrVal>"+qty+"</attrVal>");
                     else
                     sb.append("<attrVal>"+util.nvl((String)prpwiseLst.get(dsp_stt+"_"+prpV+"_VLU"))+"</attrVal>");
                     sb.append("</subTag>");
                     }
                 }
                res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
                return null;
            }
        
        public ActionForward netgrpcomparasionbargraph(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
                HttpSession session = req.getSession(false);
                InfoMgr info = (InfoMgr)session.getAttribute("info");
                DBUtil util = new DBUtil();
                DBMgr db = new DBMgr(); 
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                res.setContentType("text/xml"); 
                res.setHeader("Cache-Control", "no-cache"); 
                StringBuffer sb = new StringBuffer();
                String lprp=util.nvl((String)req.getParameter("lprp"));
                String pietyp=util.nvl((String)req.getParameter("pietyp"),"VLU");
                HashMap pktList =(HashMap)session.getAttribute("dspktdtlList");
                ArrayList  grpList = (ArrayList)session.getAttribute("grpList");
                 HashMap prpwiseLst =(HashMap)pktList.get(lprp);
                 HashMap prp = info.getPrp();
                 ArrayList  prpList = (ArrayList)prp.get(lprp+"V");
                 for(int i=0; i<prpList.size(); i++){              
                   String  prpV = util.nvl((String)prpList.get(i));
                     String mktqty = util.nvl((String)prpwiseLst.get("MKT_"+prpV+"_QTY"),"0");
                     String soldqty = util.nvl((String)prpwiseLst.get("SOLD_"+prpV+"_QTY"),"0");
                     if(!mktqty.equals("0") || !soldqty.equals("0")){
                     sb.append("<subTag>");
                     sb.append("<attrNme>"+prpV+"</attrNme>");
                     sb.append("<MKTQty>"+mktqty+"</MKTQty>");
                     sb.append("<MKTAvg>"+util.nvl((String)prpwiseLst.get("MKT_"+prpV+"_AVG"),"0")+"</MKTAvg>");
                     sb.append("<SOLDQty>"+soldqty+"</SOLDQty>");
                     sb.append("<SOLDAvg>"+util.nvl((String)prpwiseLst.get("SOLD_"+prpV+"_AVG"),"0")+"</SOLDAvg>");
                     sb.append("</subTag>");
                     }
                 }
                res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
                return null;
            }
    public ActionForward loadsalertn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("salertn"));
            String hdrnme=util.nvl((String)req.getParameter("hdrsalertn"));
            String salertnrfsh=(String)req.getParameter("salertnrfsh");
            loadrtn(typ,hdrnme,req,res);
            req.setAttribute("salertnrfsh", salertnrfsh);
        return am.findForward("loadsalertn");
            }
        }
    public ActionForward loaddlvrtn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            UserRightsForm udf = (UserRightsForm)af;
            String typ=util.nvl((String)req.getParameter("dlvrtn"));
            String hdrnme=util.nvl((String)req.getParameter("hdrdlvrtn"));
            String dlvrtnrfsh=(String)req.getParameter("dlvrtnrfsh");
            loadrtn(typ,hdrnme,req,res);
            req.setAttribute("dlvrtnrfsh", dlvrtnrfsh);
        return am.findForward("loaddlvrtn");
            }
        }
    
    public ActionForward loadopenclose(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            UserRightsForm udf = (UserRightsForm)af;
            String openclose=util.nvl((String)req.getParameter("openclose"));
                db.execUpd("gt delete","delete from gt_srch_rslt", new ArrayList()); 
                HashMap openCloseDtl = new HashMap();
                ArrayList grpPrpList=new ArrayList();
                ArrayList params=new ArrayList();
                String proc="DP_DASH_OPEN_CLOSE(pLprp => ?)";
                params.add(openclose);
                int ct = db.execCall("DP_DASH_OPEN_CLOSE", proc, params); 
                
                String sql="select prp_002,stt,count(*) cnt,to_char(trunc(sum(trunc(cts, 2)),2),'9999999999999990.00') cts,trunc(sum(cmp*trunc(cts,2))/sum(trunc(cts, 2)),2) avg,round(trunc(sum(cmp*trunc(cts,2)),2)) vlu\n" + 
                "from gt_srch_rslt\n" + 
                "group by prp_002,stt\n" + 
                "union\n" + 
                "select prp_002,dsp_stt,count(*),to_char(trunc(sum(trunc(cts, 2)),2),'9999999999999990.00') cts,trunc(sum(cmp*trunc(cts,2))/sum(trunc(cts, 2)),2) avg,round(trunc(sum(cmp*trunc(cts,2)),2)) vlu\n" + 
                "from gt_srch_rslt\n" + 
                "group by prp_002,dsp_stt\n" + 
                "union\n" + 
                "select 'TTL',stt,count(*),to_char(trunc(sum(trunc(cts, 2)),2),'9999999999999990.00') cts,trunc(sum(cmp*trunc(cts,2))/sum(trunc(cts, 2)),2) avg,round(trunc(sum(cmp*trunc(cts,2)),2)) vlu\n" + 
                "from gt_srch_rslt\n" + 
                "group by stt\n" + 
                "union\n" + 
                "select 'TTL',dsp_stt,count(*),to_char(trunc(sum(trunc(cts, 2)),2),'9999999999999990.00') cts,trunc(sum(cmp*trunc(cts,2))/sum(trunc(cts, 2)),2) avg,round(trunc(sum(cmp*trunc(cts,2)),2)) vlu\n" + 
                "from gt_srch_rslt\n" + 
                "group by dsp_stt\n";
                ArrayList rsList = db.execSqlLst("sql", sql, new ArrayList());
                PreparedStatement pst=(PreparedStatement)rsList.get(0);
                ResultSet rs = (ResultSet)rsList.get(1);
                while(rs.next()) {
                    String prp_002=util.nvl(rs.getString("prp_002"));
                    String stt=util.nvl(rs.getString("stt"));
                    String key=prp_002+"_"+stt;
                    openCloseDtl.put(key+"_CNT", util.nvl(rs.getString("cnt")));
                    openCloseDtl.put(key+"_CTS", util.nvl(rs.getString("cts")));
                    openCloseDtl.put(key+"_VLU", util.nvl(rs.getString("vlu")));
                    if(!grpPrpList.contains(prp_002) && !prp_002.equals("TTL")){
                        grpPrpList.add(prp_002);
                    }
                }
                rs.close();
                pst.close();   
            req.setAttribute("openCloseDtl", openCloseDtl);
            req.setAttribute("grpPrpList", grpPrpList);
            req.setAttribute("openclose", openclose);
        return am.findForward("loadopenclose");
            }
        }
    public void loadrtn(String typ,String hdrnme,HttpServletRequest req, HttpServletResponse res)
            throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            ArrayList dtlList = new ArrayList();
            HashMap salExDtl = new HashMap();
        String loadqry = " select To_Char(Trunc(sum(b.Cts),2),'9990.99') cts,To_Char(Sum(Trunc(B.Cts,2)*(Nvl(B.Fnl_Sal, B.Quot)/Nvl(A.Exh_Rte,1))),'999,9999,999,990.00') vlu,e.nme sale_name,d.nme byr\n" + 
        " from msal a , jansal b  , mstk c,nme_v d,emp_v e\n" + 
        " where a.idn = b.idn  and b.mstk_idn = c.idn and a.nme_idn=d.nme_idn And nvl(D.Emp_Idn,0)=nvl(E.Nme_Idn,0)\n" + 
        " and b.stt in('RT') and trunc(b.dte_rtn)=trunc(sysdate) \n" + 
        " group by e.nme,d.nme\n" + 
        " order by 3,4  ";
        if(typ.equals("DLVRTN")){
          loadqry ="select To_Char(Trunc(sum(b.Cts),2),'9990.99') cts,To_Char(Sum(Trunc(B.Cts,2)*(Nvl(B.Fnl_Sal, B.Quot)/Nvl(A.Exh_Rte,1))),'999,9999,999,990.00') vlu, e.nme sale_name,d.nme byr\n" + 
          "from mdlv a , dlv_dtl b  , mstk c,nme_v d,emp_v e\n" + 
          "where a.idn = b.idn  and b.mstk_idn = c.idn and a.nme_idn=d.nme_idn And nvl(D.Emp_Idn,0)=nvl(E.Nme_Idn,0) \n" + 
          "and b.stt in('RT') and trunc(b.dte_rtn)=trunc(sysdate) \n" + 
          "group by e.nme,d.nme\n" + 
          "order by 3,4  ";
            }
        ArrayList rsLst = db.execSqlLst(typ, loadqry, new ArrayList());
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        int srno=0;
        while(rs.next()){
                    HashMap byrdata = new HashMap();
                    byrdata.put("SPERSON",util.nvl(rs.getString("sale_name")));
                    byrdata.put("BYR",util.nvl(rs.getString("byr")));
                    byrdata.put("CTS",util.nvl(rs.getString("cts")));
                    byrdata.put("VLU",util.nvl(rs.getString("vlu")));
                    dtlList.add(byrdata);
        }
        rs.close();
        pst.close();
        loadqry = " select To_Char(Trunc(sum(b.Cts),2),'9990.99') cts,To_Char(Sum(Trunc(B.Cts,2)*(Nvl(B.Fnl_Sal, B.Quot)/Nvl(A.Exh_Rte,1))),'999,9999,999,990.00') vlu,e.nme sale_name\n" + 
        " from msal a , jansal b  , mstk c,nme_v d,emp_v e\n" + 
        " where a.idn = b.idn  and b.mstk_idn = c.idn and a.nme_idn=d.nme_idn And nvl(D.Emp_Idn,0)=nvl(E.Nme_Idn,0)\n" + 
        " and b.stt in('RT') and trunc(b.dte_rtn)=trunc(sysdate) \n" + 
        " group by e.nme\n" + 
        " order by 3 ";
        if(typ.equals("DLVRTN")){
        loadqry ="select To_Char(Trunc(sum(b.Cts),2),'9990.99') cts,To_Char(Sum(Trunc(B.Cts,2)*(Nvl(B.Fnl_Sal, B.Quot)/Nvl(A.Exh_Rte,1))),'999,9999,999,990.00') vlu,e.nme sale_name\n" + 
            "from mdlv a , dlv_dtl b  , mstk c,nme_v d,emp_v e\n" + 
            "where a.idn = b.idn  and b.mstk_idn = c.idn and a.nme_idn=d.nme_idn And nvl(D.Emp_Idn,0)=nvl(E.Nme_Idn,0) \n" + 
            "and b.stt in('RT') and trunc(b.dte_rtn)=trunc(sysdate) \n" + 
            "group by e.nme\n" + 
            "order by 3";
        }
             rsLst = db.execSqlLst(typ, loadqry, new ArrayList());
             pst = (PreparedStatement)rsLst.get(0);
             rs = (ResultSet)rsLst.get(1); 
             while(rs.next()){
                        salExDtl.put(util.nvl(rs.getString("sale_name"))+"_CTS",util.nvl(rs.getString("cts")));
                        salExDtl.put(util.nvl(rs.getString("sale_name"))+"_VLU",util.nvl(rs.getString("vlu")));
            }
            rs.close();
            pst.close();
        req.setAttribute("dtlList_"+typ, dtlList);
        req.setAttribute("salExList_"+typ, salExDtl);
        req.setAttribute("hdrnme_"+typ, hdrnme);
        req.setAttribute("typ", typ);
        req.setAttribute("view", "Y");
                util.updAccessLog(req,res,hdrnme+" Detail", hdrnme+" Detail");
        }
    
    
    public ActionForward grpcomparasionbargraph(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            res.setContentType("text/xml"); 
            res.setHeader("Cache-Control", "no-cache"); 
            StringBuffer sb = new StringBuffer();
            String lprp=util.nvl((String)req.getParameter("lprp"));
            GenericInterface genericInt = new GenericImpl();
            ArrayList param=new ArrayList();
            ArrayList  grpList = (ArrayList)session.getAttribute("grpList");
            ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "DASH_GRP_COMP","DASH_GRP_COMP");
            int indexLB = vWPrpList.indexOf(lprp)+1;
            String lbPrp = util.prpsrtcolumn("prp",indexLB);
            String lbSrt = util.prpsrtcolumn("srt",indexLB);
            HashMap grpcompDtl=new HashMap();
            ArrayList prpdtlList=new ArrayList();
            String sqlQ="select dsp_stt,"+lbPrp+" prp,"+lbSrt+" srt,sum(qty) qty,trunc(sum(nvl(sl_rte,cmp)*trunc(cts,2))/sum(trunc(cts, 2)),0) avg\n" + 
                                "    from gt_srch_rslt\n" + 
                                "    group by dsp_stt,"+lbPrp+","+lbSrt+"\n" +  
                                "    order by 3";
            ResultSet rs = db.execSql("stkStt", sqlQ, new ArrayList());
            while(rs.next()){
            String dsp_stt=util.nvl((String)rs.getString("dsp_stt"));
            String prp=util.nvl((String)rs.getString("prp"),"NF");
            grpcompDtl.put(dsp_stt+"_"+prp+"_QTY", util.nvl((String)rs.getString("qty")));
            grpcompDtl.put(dsp_stt+"_"+prp+"_AVG", util.nvl((String)rs.getString("avg")));
            if(!prpdtlList.contains(prp) && !prp.equals("TOTAL"))
            prpdtlList.add(prp);
            }
            rs.close();
            
                            for(int z=0;z<prpdtlList.size();z++){
                            String prp=util.nvl((String)prpdtlList.get(z));
                            sb.append("<subTag>");
                            sb.append("<attrNme>"+prp+"</attrNme>");
                            for(int i=0;i< grpList.size();i++){
                            String grp=util.nvl((String)grpList.get(i));
                            String valAvg=util.nvl((String)grpcompDtl.get(grp+"_"+prp+"_AVG"),"0");
                            String valQty=util.nvl((String)grpcompDtl.get(grp+"_"+prp+"_QTY"),"0");
                            sb.append("<"+grp+"Qty>"+valQty+"</"+grp+"Qty>");
                            sb.append("<"+grp+"Avg>"+valAvg+"</"+grp+"Avg>"); 
                            }
                                sb.append("</subTag>");
                            }
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
            return null;
        }
    
    
    public ActionForward loadgrpcompmix(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            UserRightsForm udf = (UserRightsForm)af;
            GenericInterface genericInt = new GenericImpl();
            ArrayList vWPrpList=genericInt.genericPrprVw(req,res,"DASH_GRP_COMP_MIX","DASH_GRP_COMP_MIX"); 
            String lprp=util.nvl((String)req.getParameter("lprp"));
            String lprpval=util.nvl((String)req.getParameter("lprpval"));   
            udf.reset();
                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("DASH_GRP_COMP_MIX");
                if(pageDtl==null || pageDtl.size()==0){
                pageDtl=new HashMap();
                pageDtl=util.pagedef("DASH_GRP_COMP_MIX");
                allPageDtl.put("DASH_GRP_COMP_MIX",pageDtl);
                }
                info.setPageDetails(allPageDtl);
//                if(info.getSrchPrp()==null)
//                util.initPrpSrch();
            dayscompmix(req,res,"0",lprp,lprpval);  
                
            ArrayList dashboardgrpList=new ArrayList();
            dashboardgrpList.add("MKT");
            dashboardgrpList.add("SOLD");
            dashboardgrpList.add("ADD");
                
            session.setAttribute("dashboardgrpList_mix", dashboardgrpList);
            session.setAttribute("filterlprpLst_mix", util.useDifferentArrayList(vWPrpList));
            graphdataDtlmix(req,res,new ArrayList(),"");
            udf.setValue("lprp",lprp);
            udf.setValue("lprpval",lprpval);
            udf.setValue("BTN_DAYS","0");
            return am.findForward("loadgrpcompmix");
            }
        }
    
    public ActionForward fetchgrpcompmix(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            UserRightsForm udf = (UserRightsForm)af;
                String days = util.nvl((String)udf.getValue("BTN_DAYS"),"3");
                String lprp=util.nvl((String)udf.getValue("lprp"));
                String lprpval=util.nvl((String)udf.getValue("lprpval")); 
                GenericInterface genericInt = new GenericImpl();
                ArrayList vWPrpList=genericInt.genericPrprVw(req,res,"DASH_GRP_COMP_MIX","DASH_GRP_COMP_MIX"); 
                dayscompmix(req,res,days,lprp,lprpval);    
                req.setAttribute("days", days);
                udf.setValue("lprp",lprp);
                udf.setValue("lprpval",lprpval);
                session.setAttribute("filterlprpLst_mix", util.useDifferentArrayList(vWPrpList));
                graphdataDtlmix(req,res,new ArrayList(),"");
                udf.setValue("BTN_DAYS",days);
            return am.findForward("loadgrpcompmix");
            }
        }
    
    public ActionForward filtergrpcompmix(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            UserRightsForm udf = (UserRightsForm)af;
            String filterlprp = util.nvl((String)udf.getValue("filterlprp"));
                String days = util.nvl((String)udf.getValue("BTN_DAYS"),"3");
                String  filterDsc = util.nvl((String)session.getAttribute("filterDsc"));
                String reset = util.nvl((String)udf.getValue("Reset"));
                ArrayList selectLst=new ArrayList();
                HashMap prp = info.getSrchPrp();
                HashMap mprp = info.getMprp();
                if(!reset.equals("")){
                    

                    String lprp=util.nvl((String)udf.getValue("lprp"));
                    String lprpval=util.nvl((String)udf.getValue("lprpval")); 
                    GenericInterface genericInt = new GenericImpl();
                    udf.reset();
                    ArrayList vWPrpList=genericInt.genericPrprVw(req,res,"DASH_GRP_COMP_MIX","DASH_GRP_COMP_MIX"); 
                    ArrayList quickpktStkIdnList=(ArrayList)session.getAttribute("quickpktStkIdnList_mix");
                    session.setAttribute("quickpktStkIdnList_mix", util.useDifferentArrayList(quickpktStkIdnList));
                    session.setAttribute("filterDsc", ""); 
                    req.setAttribute("days", days);
                    udf.setValue("lprp",lprp);
                    udf.setValue("lprpval",lprpval);
                    udf.setValue("filterlprp",vWPrpList.get(0));
                    session.setAttribute("filterlprpLst_mix", util.useDifferentArrayList(vWPrpList));
                    graphdataDtlmix(req,res,new ArrayList(),"");
                    udf.setValue("BTN_DAYS",days);
                    return am.findForward("loadgrpcompmix");
                }
                String lprp = filterlprp;
                String prpSrt = lprp ;  
                ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                for(int j=0; j < lprpS.size(); j++) {
                  String lSrt = (String)lprpS.get(j);
                  String lVal = (String)lprpV.get(j);
                  String lFld =  lprp + "_" + lVal; 
                  String reqVal = util.nvl((String)udf.getValue(lFld));
                    if(reqVal.length() > 0 && !reqVal.equals("0")) {  
                      selectLst.add(reqVal);
                  }
                }   
                graphdataDtlmix(req,res,selectLst,filterlprp);
                if(!filterlprp.equals("")){
                    String lprpDsc = util.nvl((String)mprp.get(filterlprp+"D"));
                    if(selectLst.size()==0 && filterDsc.equals("")){
                    filterDsc+=" "+lprpDsc+ " : ALL";
                    }else if(selectLst.size()!=0 && !filterDsc.equals("")){
                        String selGrp=selectLst.toString();
                        selGrp = selGrp.replaceAll("\\[", "");
                        selGrp = selGrp.replaceAll("\\]", "");
                        selGrp = selGrp.replaceAll(" ", "");
                        filterDsc+=" "+lprpDsc+ " : "+selGrp;
                    }else{
                        String selGrp=selectLst.toString();
                        selGrp = selGrp.replaceAll("\\[", "");
                        selGrp = selGrp.replaceAll("\\]", "");
                        selGrp = selGrp.replaceAll(" ", "");
                        filterDsc+=" "+lprpDsc+ " : "+selGrp;
                    }
                }
                session.setAttribute("filterDsc", filterDsc);
                req.setAttribute("days", days);
                udf.setValue("BTN_DAYS",days);
            return am.findForward("loadgrpcompmix");
            }
        }
    
    public void graphdataDtlmix(HttpServletRequest req,HttpServletResponse res,ArrayList selectLst,String filterlprp)throws Exception{
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        GenericInterface genericInt = new GenericImpl();
        ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "DASH_GRP_COMP_MIX_ALL","DASH_GRP_COMP_MIX_ALL");
        ArrayList dataDtlGrpDashboard=(ArrayList)session.getAttribute("dashboardgrpList_mix");
        HashMap quickpktListMap=(HashMap)session.getAttribute("quickpktListMap_mix");
        ArrayList quickpktStkIdnList=(ArrayList)session.getAttribute("quickpktStkIdnList_mix");
        ArrayList filterlprpLst=(ArrayList)session.getAttribute("filterlprpLst_mix");
        ArrayList fltquickpktStkIdnList=(ArrayList)session.getAttribute("fltquickpktStkIdnList_mix");
        ArrayList lstfltquickpktStkIdnList=new ArrayList();
        HashMap pktPrpMap = new HashMap();
        HashMap graphdataDtl = new HashMap();
        int vWPrpListsz=vWPrpList.size();
        int fltquickpktStkIdnListsz=fltquickpktStkIdnList.size();
        if(!filterlprp.equals("") && selectLst.size()!=0)
        filterlprpLst.remove(filterlprpLst.indexOf(filterlprp));
        double qty = 0 ;
        double vlu = 0 ;
        HashMap mprp = info.getMprp();
        for(int i=0;i<fltquickpktStkIdnListsz;i++){
            String stkIdn=(String)fltquickpktStkIdnList.get(i);
            pktPrpMap = new HashMap();
            pktPrpMap=(HashMap)quickpktListMap.get(stkIdn) ;
            String lprpval=util.nvl((String)pktPrpMap.get(filterlprp));
            if(selectLst.contains(lprpval) || selectLst.size()==0){
            lstfltquickpktStkIdnList.add(stkIdn);
            String dsp_stt=util.nvl((String)pktPrpMap.get("dsp_stt"));
                String age_grp=util.nvl((String)pktPrpMap.get("dys"));
                String curlprpqty = util.nvl((String)pktPrpMap.get("qty"),"0").trim();
                for(int j=0;j<vWPrpListsz;j++){
                    String vwlprp=(String)vWPrpList.get(j);
                    String lprpTyp = util.nvl((String)mprp.get(vwlprp+"T"));
                    if(lprpTyp.equals("N")){
                        String curcts = util.nvl((String)pktPrpMap.get("cts"),"0").trim();
                        BigDecimal currBigCts = new BigDecimal(curcts);
                        String curlprprte = util.nvl((String)pktPrpMap.get(vwlprp),"0").trim();
                        if(curlprprte.equals("NA"))
                        curlprprte="0";
                        BigDecimal currBigLprpVlu = new BigDecimal(curlprprte);
                        BigDecimal lprpBigvlu = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+vwlprp+"_VLU");
                        if(lprpBigvlu==null)
                        lprpBigvlu=new BigDecimal(vlu);
                        if(!curlprprte.equals("0")){
                        currBigLprpVlu=currBigLprpVlu.multiply(currBigCts);
                        lprpBigvlu = lprpBigvlu.add(currBigLprpVlu);
                        }
                        graphdataDtl.put(dsp_stt+"_"+vwlprp+"_VLU", lprpBigvlu);
    //                        BigDecimal currBigLprpQty = new BigDecimal(curlprpqty);
    //                        BigDecimal lprpBigqty = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+vwlprp+"_QTY");
    //                        if(lprpBigqty==null)
    //                        lprpBigqty=new BigDecimal(qty);
    //                        if(!curlprpqty.equals("0"))
    //                        lprpBigqty = lprpBigqty.add(currBigLprpQty);
    //
    //                        graphdataDtl.put(dsp_stt+"_"+vwlprp+"_QTY", lprpBigqty);
                    }
                }
            }
        }
        session.setAttribute("filterlprpLst_mix", filterlprpLst);
        session.setAttribute("fltquickpktStkIdnList_mix", lstfltquickpktStkIdnList);
        session.setAttribute("graphdataDtl_mix", graphdataDtl);
    } 
    
    public void dayscompmix(HttpServletRequest req,HttpServletResponse res,String days,String lprp,String lprpval)throws Exception{
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList params=new ArrayList();
        db.execDirUpd("gt delete","delete from gt_srch_multi", new ArrayList());
        GenericInterface genericInt = new GenericImpl();
        ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "DASH_GRP_COMP_MIX_ALL","DASH_GRP_COMP_MIX_ALL");
        ArrayList pktStkIdnList = new ArrayList();
        HashMap pktListMap = new HashMap();
        HashMap pktPrpMap = new HashMap();
        ArrayList grpList=new ArrayList();
        int vWPrpListsz=vWPrpList.size();
        grpList.add("MKT");
        grpList.add("SOLD");

        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("DASH_GRP_COMP_MIX");
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="",removeplusminus="N";
        pageList= ((ArrayList)pageDtl.get("PLUSMINUS") == null)?new ArrayList():(ArrayList)pageDtl.get("PLUSMINUS");
        if(pageList!=null && pageList.size() >0){
        for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
            if(dflt_val.equals("Y"))
                removeplusminus="Y";
        }}
        long lStartTime = new Date().getTime();
        String proc="DP_REP_DASHBOARD_MIX(pStkGrp => ? \n" + 
        "    , pDteFrm => to_date(trunc(sysdate-?), 'dd-mm-rrrr') , pDteTo => to_date(trunc(sysdate), 'dd-mm-rrrr'))";
        if(!lprp.equals("")){
        proc="DP_REP_DASHBOARD_MIX(pStkGrp => ? \n" + 
                    "    , pDteFrm => to_date(trunc(sysdate-?), 'dd-mm-rrrr') , pDteTo => to_date(trunc(sysdate), 'dd-mm-rrrr'),pLprp => ?,pLprpVal => ?)";  
        }
        
        for(int i=0;i<grpList.size();i++) {
        params=new ArrayList();
        params.add(grpList.get(i));
        params.add(days);
        if(!lprp.equals("")){
        params.add(lprp);
        params.add(lprpval);
        }
        int ct = db.execCall("DP_REP_DASHBOARD", proc, params);  
        }
        
        ArrayList param = new ArrayList();
        String mprpStr = "";
        String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||Upper(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1')) str From Rep_Prp Rp Where rp.MDL = ? and flg ='Y' order by srt " ;
        param = new ArrayList();
        param.add("DASH_GRP_COMP_MIX_ALL");
        ResultSet rs = db.execSql("mprp ", mdlPrpsQ , param);
        while(rs.next()) {
        String val = util.nvl((String)rs.getString("str"));
        mprpStr = mprpStr +" "+val;
        }
        rs.close();
        
        String rsltQ = " with \n" + 
        "         STKDTL as \n" + 
        "         ( select c.dsp_stt,c.stt,b.sk1,c.rowid duppktIdn, nvl(nvl(txt,num),val) atr , mprp,c.stk_idn, b.vnm ,c.cts,b.qty,c.mkt_dys,nvl(c.sl_rte,c.cmp) rteQ, b.rap_rte from stk_dtl a, mstk b , gt_srch_multi c\n" + 
        "         Where 1=1 \n" + 
        "         and a.mstk_idn = b.idn and b.idn = c.stk_idn \n" + 
        "         and exists (select 1 from rep_prp rp where rp.MDL = ? and a.mprp = rp.prp and rp.flg='Y') \n" + 
        "         And a.Grp = 1) \n" + 
        "         Select * from stkDtl PIVOT \n" + 
        "         ( max(atr) " +
        " for mprp in ( "+mprpStr+" ) ) order by 1,3 " ;
        
        param = new ArrayList();
        param.add("DASH_GRP_COMP_MIX_ALL");
        rs = db.execSql("search Result", rsltQ, param);
        long colllEndTime = new Date().getTime();
        while (rs.next()) {
            pktPrpMap = new HashMap();
            String duppktIdn = util.nvl(rs.getString("duppktIdn"));
            String dys = "AGE-GRP-"+util.nvl(rs.getString("mkt_dys"));
            pktPrpMap.put("vnm",util.nvl(rs.getString("vnm")));
            pktPrpMap.put("dsp_stt",util.nvl(rs.getString("dsp_stt")));
            pktPrpMap.put("stt",util.nvl(rs.getString("stt")));
            pktPrpMap.put("sk1",util.nvl(rs.getString("sk1")));
            pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
            pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
            pktPrpMap.put("rte",util.nvl(rs.getString("rteQ")));
            pktPrpMap.put("rap_rte",util.nvl(rs.getString("rap_rte")));
            for (int i = 0; i < vWPrpListsz; i++) {
            String vwPrp = (String)vWPrpList.get(i);
            String vwPrp1=vwPrp;
            if(vwPrp.toUpperCase().equals("SIZE"))
            vwPrp1 = "SIZE1";
            if(vwPrp.toUpperCase().equals("MIX_SIZE"))
            vwPrp1 = "MIX_SIZE1";
            if(vwPrp.toUpperCase().equals("RTE"))
            vwPrp1 = "rteQ";
            String fldVal = util.nvl((String)rs.getString(vwPrp1));     
            if(removeplusminus.equals("Y") && !vwPrp.equals("SZ") && !vwPrp.equals("SIZE") && !vwPrp.equals("MIX_SIZE")){
                fldVal=fldVal.replaceAll("\\+", "");
                fldVal=fldVal.replaceAll("\\-", "");
            }
            pktPrpMap.put(vwPrp, fldVal);
            }
            pktListMap.put(duppktIdn, pktPrpMap);
            pktStkIdnList.add(duppktIdn);
        }
        rs.close();
        long lEndTime = new Date().getTime();
        long difference = lEndTime - lStartTime;
        long difference1 = lEndTime - colllEndTime;
        System.out.println("Collection using piovet Time =>"+util.convertMillis(difference1));
        System.out.println("Total Time =>"+util.convertMillis(difference));
        session.setAttribute("quickpktStkIdnList_mix", pktStkIdnList);
        session.setAttribute("fltquickpktStkIdnList_mix", util.useDifferentArrayList(pktStkIdnList));
        session.setAttribute("quickpktListMap_mix", pktListMap);
        session.setAttribute("filterDsc", "");
    }
    
    
    public ActionForward grpcomparasionPiePrpWiseMix(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            res.setContentType("text/xml"); 
            res.setHeader("Cache-Control", "no-cache"); 
            StringBuffer sb = new StringBuffer();
            String lprp=util.nvl((String)req.getParameter("filterlprp"));
            String dspstt = util.nvl((String)req.getParameter("dsp_stt"));
            String graphby=util.nvl((String)req.getParameter("graphby"),"VLU");
            String pietyp=util.nvl((String)req.getParameter("pietyp"),"PRP");
            ArrayList dataDtlGrpDashboard=(ArrayList)session.getAttribute("dashboardgrpList_mix");
            ArrayList fltquickpktStkIdnList=(ArrayList)session.getAttribute("fltquickpktStkIdnList_mix");
            int fltquickpktStkIdnListsz=fltquickpktStkIdnList.size();
            HashMap quickpktListMap=(HashMap)session.getAttribute("quickpktListMap_mix");
            HashMap prp = info.getSrchPrp();
            HashMap graphdataDtl=new HashMap();
            ArrayList diffDisplayLst=new ArrayList();
            if(dspstt.equals(""))
            dspstt=util.nvl((String)req.getParameter("stt"));
                double qty = 0 ;
                double vlu = 0 ;
            double divide = 1000 ;
            BigDecimal divideBig=new BigDecimal(divide);
            HashMap pktPrpMap = new HashMap();
            ArrayList  prpList = (ArrayList)prp.get(lprp+"V");
            ArrayList  prpGrpList = (ArrayList)prp.get(lprp+"G");
             int prpListsz=prpList.size();
             int dataDtlGrpDashboardsz=dataDtlGrpDashboard.size();
            for(int i=0;i<fltquickpktStkIdnListsz;i++){
                String stkIdn=(String)fltquickpktStkIdnList.get(i);
                pktPrpMap = new HashMap();
                pktPrpMap=(HashMap)quickpktListMap.get(stkIdn) ;
                String lprpval=util.nvl((String)pktPrpMap.get(lprp));
                if(pietyp.equals("GRP")){
                int indexGrp=prpList.indexOf(lprpval);
                if(indexGrp>-1)
                lprpval=util.nvl((String)prpGrpList.get(indexGrp));
                else
                lprpval="NN";
                }
                String dsp_stt=util.nvl((String)pktPrpMap.get("dsp_stt"));
                if(dsp_stt.equalsIgnoreCase(dspstt)){
                String cts = util.nvl((String)pktPrpMap.get("cts"),"0").trim();
                String rteval = util.nvl((String)pktPrpMap.get("rte"),"0").trim(); 
                if(rteval.equals("NA"))
                rteval="0";
                            BigDecimal currBigCts = new BigDecimal(cts);
                            BigDecimal currBigLprpVlu = new BigDecimal(rteval);
                            BigDecimal lprpBigvlu = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+lprpval+"_VLU");
                            if(lprpBigvlu==null)
                            lprpBigvlu=new BigDecimal(vlu);
                            if(!rteval.equals("0")){
                            currBigLprpVlu=currBigLprpVlu.multiply(currBigCts);
                            lprpBigvlu = lprpBigvlu.add(currBigLprpVlu);
                            }
                            graphdataDtl.put(dsp_stt+"_"+lprpval+"_VLU", lprpBigvlu);
                            
                            BigDecimal currBigLprpCts= new BigDecimal(cts);
                            BigDecimal lprpBigcts = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+lprpval+"_CTS");
                            if(lprpBigcts==null)
                            lprpBigcts=new BigDecimal(qty);
                            if(!cts.equals("0"))
                            lprpBigcts = lprpBigcts.add(currBigLprpCts);
                            graphdataDtl.put(dsp_stt+"_"+lprpval+"_CTS", lprpBigcts);
                }
            }
            
            if(pietyp.equals("GRP"))
            diffDisplayLst=util.useDifferentArrayListUnique(prpGrpList);
            else
            diffDisplayLst=util.useDifferentArrayList(prpList);
            int diffDisplayLstsz=diffDisplayLst.size();
            
             for(int i=0; i<diffDisplayLstsz; i++){              
               String  prpV = util.nvl((String)diffDisplayLst.get(i));
               BigDecimal prpValue  = (BigDecimal)graphdataDtl.get(dspstt+"_"+prpV+"_"+graphby);
                 if(prpValue!=null){
                     sb.append("<subTag>");
                     if (prpV.indexOf("&") > -1)
                     prpV = prpV.replaceAll("&", "%26");
                     if (prpV.indexOf("\"") > -1)
                     prpV = prpV.replaceAll("\"", "%22");
                     if (prpV.indexOf("(") > -1)
                     prpV = prpV.replaceAll("\\(", "%28");
                     if (prpV.indexOf(")") > -1)
                     prpV = prpV.replaceAll("\\)", "%29");
                     if (prpV.indexOf("/") > -1)
                     prpV = prpV.replaceAll("\\/", "%2F");
                     if (prpV.indexOf("*") > -1)
                     prpV = prpV.replaceAll("\\*", "%2A");
                     if (prpV.indexOf("!") > -1)
                     prpV = prpV.replaceAll("\\!", "%21");
                     if (prpV.indexOf("$") > -1)
                     prpV = prpV.replaceAll("\\$", "%24");
                     if (prpV.indexOf("%") > -1)
                     prpV = prpV.replaceAll("\\%", "%25");
                     if (prpV.indexOf("'") > -1)
                     prpV = prpV.replaceAll("\\'", "%27");
                     if (prpV.indexOf("-") > -1)
                     prpV = prpV.replaceAll("\\-", "%2D");
                     if (prpV.indexOf(".") > -1)
                     prpV = prpV.replaceAll("\\.", "%2E");
                     if (prpV.indexOf("?") > -1)
                     prpV = prpV.replaceAll("\\?", "%3F");
                     if (prpV.indexOf(" ") > -1)
                     prpV = prpV.replaceAll(" ", "%20");
                     
                     sb.append("<attrNme>"+prpV+"</attrNme>");
                     if(graphby.equals("VLU"))
                     sb.append("<attrVal>"+(prpValue.divide(divideBig,0,RoundingMode.HALF_EVEN))+"</attrVal>");
                     else
                     sb.append("<attrVal>"+prpValue.setScale(0, RoundingMode.UP)+"</attrVal>");
                     sb.append("</subTag>");
                 }
              }
            System.out.println(sb); 
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
            return null;
        }
    
    public ActionForward grpcomparasionBarChartPrpWiseMix(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            res.setContentType("text/xml"); 
            res.setHeader("Cache-Control", "no-cache"); 
            StringBuffer sb = new StringBuffer();
            String lprp=util.nvl((String)req.getParameter("filterlprp"));
            String graphby=util.nvl((String)req.getParameter("graphby"),"VLU");
            String pietyp=util.nvl((String)req.getParameter("pietyp"),"PRP");
            ArrayList dataDtlGrpDashboard=(ArrayList)session.getAttribute("dashboardgrpList_mix");
            ArrayList fltquickpktStkIdnList=(ArrayList)session.getAttribute("fltquickpktStkIdnList_mix");
            int fltquickpktStkIdnListsz=fltquickpktStkIdnList.size();
            HashMap quickpktListMap=(HashMap)session.getAttribute("quickpktListMap_mix");
            HashMap prp = info.getSrchPrp();
            HashMap graphdataDtl=new HashMap();
            ArrayList diffDisplayLst=new ArrayList();
            double ctsval = 0 ;
            double vlu = 0 ;
            double divide = 1000 ;
            BigDecimal divideBig=new BigDecimal(divide);
            HashMap pktPrpMap = new HashMap();
            ArrayList  prpList = (ArrayList)prp.get(lprp+"V");
            ArrayList  prpGrpList = (ArrayList)prp.get(lprp+"G");
             int prpListsz=prpList.size();
             int dataDtlGrpDashboardsz=dataDtlGrpDashboard.size();
            for(int i=0;i<fltquickpktStkIdnListsz;i++){
                String stkIdn=(String)fltquickpktStkIdnList.get(i);
                pktPrpMap = new HashMap();
                pktPrpMap=(HashMap)quickpktListMap.get(stkIdn) ;
                String lprpval=util.nvl((String)pktPrpMap.get(lprp));
                if(pietyp.equals("GRP")){
                int indexGrp=prpList.indexOf(lprpval);
                if(indexGrp>-1)
                lprpval=util.nvl((String)prpGrpList.get(indexGrp));
                else
                lprpval="NN";
                }
                String dsp_stt=util.nvl((String)pktPrpMap.get("dsp_stt"));
                String cts = util.nvl((String)pktPrpMap.get("cts"),"0").trim();
                String rteval = util.nvl((String)pktPrpMap.get("rte"),"0").trim(); 
                if(rteval.equals("NA"))
                rteval="0";
                            BigDecimal currBigCts = new BigDecimal(cts);
                            BigDecimal currBigLprpVlu = new BigDecimal(rteval);
                            BigDecimal lprpBigvlu = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+lprpval+"_VLU");
                            if(lprpBigvlu==null)
                            lprpBigvlu=new BigDecimal(vlu);
                            if(!rteval.equals("0")){
                            currBigLprpVlu=currBigLprpVlu.multiply(currBigCts);
                            lprpBigvlu = lprpBigvlu.add(currBigLprpVlu);
                            }
                            graphdataDtl.put(dsp_stt+"_"+lprpval+"_VLU", lprpBigvlu);
                            
                            BigDecimal currBigLprpCts = new BigDecimal(cts);
                            BigDecimal lprpBigcts = (BigDecimal)graphdataDtl.get(dsp_stt+"_"+lprpval+"_CTS");
                            if(lprpBigcts==null)
                            lprpBigcts=new BigDecimal(ctsval);
                            if(!cts.equals("0"))
                            lprpBigcts = lprpBigcts.add(currBigLprpCts);
                            graphdataDtl.put(dsp_stt+"_"+lprpval+"_CTS", lprpBigcts);
            }
            
            if(pietyp.equals("GRP"))
            diffDisplayLst=util.useDifferentArrayListUnique(prpGrpList);
            else
            diffDisplayLst=util.useDifferentArrayList(prpList);
            int diffDisplayLstsz=diffDisplayLst.size();
             
             for(int i=0; i<diffDisplayLstsz; i++){              
               String  prpV = util.nvl((String)diffDisplayLst.get(i));
                 BigDecimal mktcts = (BigDecimal)graphdataDtl.get("MKT_"+prpV+"_CTS");
                 BigDecimal soldcts= (BigDecimal)graphdataDtl.get("SOLD_"+prpV+"_CTS");
                 BigDecimal addcts = (BigDecimal)graphdataDtl.get("ADD_"+prpV+"_CTS");
                 if(mktcts!=null || soldcts!=null  || addcts!=null){
                     BigDecimal mktvlu = (BigDecimal)graphdataDtl.get("MKT_"+prpV+"_VLU");
                     BigDecimal soldvlu = (BigDecimal)graphdataDtl.get("SOLD_"+prpV+"_VLU");
                     BigDecimal addvlu = (BigDecimal)graphdataDtl.get("ADD_"+prpV+"_VLU");
                 if(mktcts==null)
                 mktcts=new BigDecimal(ctsval);  
                 if(soldcts==null)
                 soldcts=new BigDecimal(ctsval);
                 if(addcts==null)
                 addcts=new BigDecimal(ctsval);
                 
                     if(mktvlu==null)
                     mktvlu=new BigDecimal(vlu);  
                     if(soldvlu==null)
                     soldvlu=new BigDecimal(vlu);
                     if(addvlu==null)
                     addvlu=new BigDecimal(vlu);
                     if (prpV.indexOf("&") > -1)
                     prpV = prpV.replaceAll("&", "%26");
                     if (prpV.indexOf("\"") > -1)
                     prpV = prpV.replaceAll("\"", "%22");
                     if (prpV.indexOf("(") > -1)
                     prpV = prpV.replaceAll("\\(", "%28");
                     if (prpV.indexOf(")") > -1)
                     prpV = prpV.replaceAll("\\)", "%29");
                     if (prpV.indexOf("/") > -1)
                     prpV = prpV.replaceAll("\\/", "%2F");
                     if (prpV.indexOf("*") > -1)
                     prpV = prpV.replaceAll("\\*", "%2A");
                     if (prpV.indexOf("!") > -1)
                     prpV = prpV.replaceAll("\\!", "%21");
                     if (prpV.indexOf("$") > -1)
                     prpV = prpV.replaceAll("\\$", "%24");
                     if (prpV.indexOf("%") > -1)
                     prpV = prpV.replaceAll("\\%", "%25");
                     if (prpV.indexOf("'") > -1)
                     prpV = prpV.replaceAll("\\'", "%27");
                     if (prpV.indexOf("-") > -1)
                     prpV = prpV.replaceAll("\\-", "%2D");
                     if (prpV.indexOf(".") > -1)
                     prpV = prpV.replaceAll("\\.", "%2E");
                     if (prpV.indexOf("?") > -1)
                     prpV = prpV.replaceAll("\\?", "%3F");
                     if (prpV.indexOf(" ") > -1)
                     prpV = prpV.replaceAll(" ", "%20");
                 sb.append("<subTag>");
                 sb.append("<attrNme>"+prpV+"</attrNme>");
                 
                 sb.append("<MKTCts>"+mktcts.setScale(0, RoundingMode.UP)+"</MKTCts>");
                 if(BigDecimal.ZERO.equals(mktcts))
                 sb.append("<MKTAvg>0</MKTAvg>");
                 else
                 sb.append("<MKTAvg>"+(mktvlu.divide(mktcts, 0,RoundingMode.HALF_EVEN))+"</MKTAvg>");
                 
                 sb.append("<SOLDCts>"+soldcts.setScale(0, RoundingMode.UP)+"</SOLDCts>");
                 if(BigDecimal.ZERO.equals(soldcts))
                 sb.append("<SOLDAvg>0</SOLDAvg>");
                 else
                 sb.append("<SOLDAvg>"+(soldvlu.divide(soldcts, 0,RoundingMode.HALF_EVEN))+"</SOLDAvg>");
                 
                 sb.append("<ADDCts>"+addcts.setScale(0, RoundingMode.UP)+"</ADDCts>");
                 if(BigDecimal.ZERO.equals(addcts))
                 sb.append("<ADDAvg>0</ADDAvg>");
                 else
                 sb.append("<ADDAvg>"+(addvlu.divide(addcts, 0,RoundingMode.HALF_EVEN))+"</ADDAvg>");
                 sb.append("</subTag>");
                 }
             }
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
            return null;
        }
        public ScreenAction() {
        super();
    }
    
    public ActionForward loadT20(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            UserRightsForm udf = (UserRightsForm)af;
            GenericInterface genericInt = new GenericImpl();
            HashMap dtls=new HashMap();
            String tblNme="STK_ASRT,STK_MKTG,STK_SOLD";
            String mdl="T20";
            udf.reset();
            String lprpStr="";
            String lprpVal="";
            String lprpTyp="";
            String sttStr="";
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            String requestLprpAll=util.nvl((String)req.getParameter("lprp"));
            String requestLprpValAll=util.nvl((String)req.getParameter("lprpval"));   
            String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="",grpListStr="MKT";
            HashMap dfstksttDtl  =((HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_df_stk_stt") == null)?new HashMap():(HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_df_stk_stt"); 
            ArrayList findTbls=new ArrayList();
            ArrayList findstt=new ArrayList(); 
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("T20");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("T20");
            allPageDtl.put("T20",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            
            pageList= ((ArrayList)pageDtl.get("GRPLIST") == null)?new ArrayList():(ArrayList)pageDtl.get("GRPLIST");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
            grpListStr=dflt_val;
            }}
                
            
            String [] grpListStrKey=grpListStr.split("@"); 
            List sttList = Arrays.asList(grpListStrKey); 
            
            t20Rule(req);
                
            ArrayList t20SalePeriodList = (session.getAttribute("t20SalePeriodList") == null)?new ArrayList():(ArrayList)session.getAttribute("t20SalePeriodList");
            int t20SalePeriodListsz=t20SalePeriodList.size();
            dfstksttDtl  =((HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_df_stk_stt") == null)?new HashMap():(HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_df_stk_stt"); 
            findTbls=new ArrayList();
            findstt=new ArrayList();
                for(int n=0;n<sttList.size();n++){
                          String stt = (String)sttList.get(n);
                          if(!stt.equals("")) {
                              HashMap grp1Dtl=(HashMap)dfstksttDtl.get(stt);
                              ArrayList sttLst=(ArrayList)grp1Dtl.get("STT");
                              ArrayList mdlLst=(ArrayList)grp1Dtl.get("MDL");
                                  for(int j=0;j<mdlLst.size();j++){
                                      String mdldf=(String)mdlLst.get(j);
                                      if(!findTbls.contains(mdldf))
                                      findTbls.add(mdldf);
                                  }
                                  findstt.addAll(sttLst);
                          }
                 } 
                
                if(findTbls.indexOf("PMKT") >  -1 && findTbls.indexOf("MKT") >  -1)
                    tblNme="STK_ASRT,STK_MKTG";
                else if(findTbls.indexOf("PMKT") >  -1)
                    tblNme="STK_ASRT";
                else if(findTbls.indexOf("MKT") >  -1)
                    tblNme="STK_MKTG";

            if(findstt.size()>0){
                sttStr= findstt.toString();
                sttStr = sttStr.replaceAll("\\[", "");
                sttStr = sttStr.replaceAll("\\]", "").replaceAll(" ", "");
            }
            
            if(!requestLprpAll.equals("")){
                HashMap prp = info.getPrp();
                String [] requestLprpKey=requestLprpAll.split("~");
                String [] requestLprpValKey=requestLprpValAll.split("~");
                for (int r = 0; r < requestLprpKey.length; r++) {
                String requestLprp=requestLprpKey[r];
                String requestLprpVal=requestLprpValKey[r];
                ArrayList lprpS = (ArrayList)prp.get(requestLprp+"S");
                ArrayList lprpV = (ArrayList)prp.get(requestLprp+"V");
                if(lprpStr.equals(""))
                lprpStr=requestLprp;
                else
                lprpStr+="#"+requestLprp;  
                if(lprpTyp.equals(""))
                lprpTyp="M";
                else
                lprpTyp+="#M";  
                if((requestLprp.equals("SH") || requestLprp.equals("SHAPE")) && requestLprpVal.equals("FANCY")){
                    if(!lprpVal.equals(""))
                    lprpVal+="#";
                    for(int m=0; m < lprpV.size(); m++) {
                    String lVal = (String)lprpV.get(m);
                        if(!lVal.equals("RD") && !lVal.equals("ROUND") && !lVal.equals("RND")){
                        String srtVal=(String)lprpS.get(lprpV.indexOf(lVal));
                            if(lprpVal.equals(""))
                            lprpVal=srtVal;
                            else
                            lprpVal+="@"+srtVal; 
                        }
                    }
                }else{
                    String srtVal=(String)lprpS.get(lprpV.indexOf(requestLprpVal));
                    if(lprpVal.equals(""))
                    lprpVal=srtVal;
                    else
                    lprpVal+="#"+srtVal; 
                }
            }
            }
            dtls=new HashMap();
            lprpVal = "1001133177,1001108326,1453166";
            lprpStr = "S";
            lprpTyp = "M";
            sttStr="";
            dtls.put("prpVal",lprpVal);
            dtls.put("prp",lprpStr);
            dtls.put("prpTyp",lprpTyp);
            dtls.put("stt",sttStr);
            dtls.put("mdl",mdl);
            dtls.put("tblNme", tblNme);
            dtls.put("hardcode", "MKT");
            HashMap pktListMapMkt=getPacketsFromMongo(req,res,dtls);
//            HashMap pktListMapMkt=new HashMap();
            if(pktListMapMkt.size()>0)
            session.setAttribute("t20PacketLstMapMkt", (HashMap)sortByComparatorMap(pktListMapMkt,"sk1"));
                
            
           sttList=new ArrayList();
           sttList.add("SOLD");
           findTbls=new ArrayList();
           findstt=new ArrayList();
           for(int n=0;n<sttList.size();n++){
                     String stt = (String)sttList.get(n);
                     if(!stt.equals("")) {
                         HashMap grp1Dtl=(HashMap)dfstksttDtl.get(stt);
                         ArrayList sttLst=(ArrayList)grp1Dtl.get("STT");
                         ArrayList mdlLst=(ArrayList)grp1Dtl.get("MDL");
                             for(int j=0;j<mdlLst.size();j++){
                                 String mdldf=(String)mdlLst.get(j);
                                 if(!findTbls.contains(mdldf))
                                 findTbls.add(mdldf);
                             }
                             findstt.addAll(sttLst);
                     }
            }
            if(findstt.size()>0){
                    sttStr= findstt.toString();
                    sttStr = sttStr.replaceAll("\\[", "");
                    sttStr = sttStr.replaceAll("\\]", "").replaceAll(" ", "");
            }
            tblNme="STK_SOLD";
            if(lprpTyp.equals(""))
            lprpTyp="Y";
            else
            lprpTyp+="#Y"; 
            if(lprpStr.equals(""))
            lprpStr="SAL_DTE";
            else
            lprpStr+="#SAL_DTE";    

            if(!lprpVal.equals(""))
            lprpVal+="#";    
            lprpVal+=util.getBackDatedDate("yyyyMMdd", Integer.parseInt((String)t20SalePeriodList.get(t20SalePeriodListsz-1)))+"@"+util.getBackDatedDate("yyyyMMdd",0);
            
            lprpVal = "1001125958,1001136277";
            lprpStr = "S";
            lprpTyp = "M";
            sttStr="";
                
            dtls=new HashMap();
            dtls.put("prpVal",lprpVal);
            dtls.put("prp",lprpStr);
            dtls.put("prpTyp",lprpTyp);
            dtls.put("stt",sttStr);
            dtls.put("mdl",mdl);
            dtls.put("tblNme", tblNme);
            HashMap pktListMapSold=getPacketsFromMongo(req,res,dtls);
//            HashMap pktListMapSold=new HashMap();
            if(pktListMapSold.size()>0)
            session.setAttribute("t20PacketLstMapSold", (HashMap)sortByComparatorMap(pktListMapSold,"sk1"));

            return loadT20Grid(am, af, req, res);
            }
    }
    
    public ActionForward loadT20Grid(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                UserRightsForm udf = (UserRightsForm)af;
                HashMap allpktListMapSold = (session.getAttribute("t20PacketLstMapSold") == null)?new HashMap():(HashMap)session.getAttribute("t20PacketLstMapSold");
                ArrayList genericPacketLstSold=util.getKeyInArrayList(allpktListMapSold);
                ArrayList pktLstSold=util.convertArrayList(allpktListMapSold,genericPacketLstSold);
                
                HashMap allpktListMapMkt = (session.getAttribute("t20PacketLstMapMkt") == null)?new HashMap():(HashMap)session.getAttribute("t20PacketLstMapMkt");
                ArrayList genericPacketLstMkt=util.getKeyInArrayList(allpktListMapMkt);
                ArrayList pktLstMkt=util.convertArrayList(allpktListMapMkt,genericPacketLstMkt);
                HashMap dbSysInfo=info.getDmbsInfoLst();  
                String dbmsvluLimit = util.nvl((String)dbSysInfo.get("T20_VLU_LIMIT"),"100000");
                String dbmsAvgPct = util.nvl((String)dbSysInfo.get("T20_AVG_PCT"),"1");
                String dbmsdflyDays = util.nvl((String)dbSysInfo.get("DFLT_T20DAYS"),"60");
                double MIN_INVENTORY_DAYS = Double.parseDouble((String)dbSysInfo.get("MIN_INVENTORY_DAYS"));
                double TOLERANCE_MAX_PCT = Double.parseDouble((String)dbSysInfo.get("TOLERANCE_MAX_PCT"));
                double TOLERANCE_MIN_PCT = Double.parseDouble((String)dbSysInfo.get("TOLERANCE_MIN_PCT"));
                ArrayList t20SalePeriodListOrg = (session.getAttribute("t20SalePeriodList") == null)?new ArrayList():(ArrayList)session.getAttribute("t20SalePeriodList");
                ArrayList t20SalePeriodList=util.useDifferentArrayListUnique(t20SalePeriodListOrg);
                int t20SalePeriodListsz=t20SalePeriodList.size();
                ArrayList keyList = new ArrayList();
                String hardCodeGrpKey="hardCodeGrp";
                String hardCodeGrpVal="MKT";
                double ratio;
                String displayCOlumn=util.nvl((String)req.getParameter("displayColumn"));
                if(displayCOlumn.equals(""))
                displayCOlumn=util.nvl((String)udf.getValue("displayColumn"),"QTY");
                String vluLimitString=util.nvl((String)req.getParameter("vluLimit"));
                if(vluLimitString.equals(""))
                vluLimitString=util.nvl((String)udf.getValue("vluLimit"));
                
                if(vluLimitString.equals(""))
                vluLimitString=dbmsvluLimit;
                
                double vluLimit=Double.parseDouble(vluLimitString);
                
                String dbmsAvgPctString=util.nvl((String)req.getParameter("avgPct"));
                if(dbmsAvgPctString.equals(""))
                dbmsAvgPctString=util.nvl((String)udf.getValue("avgPct"));
                
                if(dbmsAvgPctString.equals(""))
                dbmsAvgPctString=dbmsAvgPct;
                
                String gridby=util.nvl((String)req.getParameter("gridby"));
                if(gridby.equals(""))
                gridby=util.nvl((String)udf.getValue("gridby"),"");
                
                String fmt=util.nvl((String)req.getParameter("fmt"));
                String[] fmtLst=udf.getFmtLst();
                if(fmt.equals("") && fmtLst.length==0)
                fmt=(String)dbSysInfo.get("SHAPE")+"_"+(String)dbSysInfo.get("SIZE")+"_"+(String)dbSysInfo.get("COL")+"_"+(String)dbSysInfo.get("CLR");
                
                if(fmtLst.length!=0)
                fmt=util.convertStringArrayToString("_",fmtLst);
                                    
                String topListByPeriod=util.nvl((String)req.getParameter("topListByPeriod"));
                
                int topcount=Integer.parseInt(util.nvl((String)req.getParameter("topcount"),"0"));
                if(topcount==0)
                topcount=Integer.parseInt(util.nvl((String)udf.getValue("topcount"),"20"));
                
                int day=Integer.parseInt(util.nvl((String)req.getParameter("day"),"0"));
                if(day==0)
                day=Integer.parseInt(util.nvl((String)udf.getValue("day"),dbmsdflyDays));
                
                String crtwtfor=util.nvl((String)req.getParameter("crtwtfor"));
                if(crtwtfor.equals(""))
                crtwtfor=util.nvl((String)udf.getValue("crtwtfor"),"");
                
                String ratioby=util.nvl((String)req.getParameter("ratioby"));
                if(ratioby.equals(""))
                ratioby=util.nvl((String)udf.getValue("ratioby"),"RNK");
                
                String [] fmtKey=fmt.split("_");
                List fmtList = Arrays.asList(fmtKey);
                String someFmtgridby=gridby;
                if(!someFmtgridby.equals(""))
                someFmtgridby="_"+someFmtgridby;
                
                HashMap redSortedPeriodMap=new HashMap();
                HashMap greenSortedPeriodMap=new HashMap();
                ArrayList dataValidGreenLst=new ArrayList();
                ArrayList dataValidRedLst=new ArrayList();
                ArrayList<String> gridParamsLst=new ArrayList();
                HashMap<String, String> dataDtl=new HashMap<String, String>();
                if(!ratioby.equals("MKTVSNEWMKT") && !ratioby.equals("OLDVSNEW")){
                gridParamsLst.addAll(fmtList);
                gridParamsLst.add(hardCodeGrpKey);
                String [] gridParams = gridParamsLst.toArray(new String[gridParamsLst.size()]);
                gridParams = gridParamsLst.toArray(new String[gridParamsLst.size()]);
                dataDtl = util.getKV(req,res,pktLstMkt, gridParams,"Y","SHOWPRP","@",someFmtgridby,"T20","T20",crtwtfor);
                
                for(int s=0;s<t20SalePeriodListsz;s++){
                gridParamsLst=new ArrayList();
                gridParamsLst.addAll(fmtList);
                gridParamsLst.add((String)t20SalePeriodList.get(s));
                gridParams = gridParamsLst.toArray(new String[gridParamsLst.size()]);
                gridParams = gridParamsLst.toArray(new String[gridParamsLst.size()]);
                HashMap<String, String> rowDtl= util.getKV(req,res,pktLstSold, gridParams,"Y","SHOWPRP","@",someFmtgridby,"T20","T20",crtwtfor);
                dataDtl.putAll(rowDtl);
                }
                }else if(ratioby.equals("MKTVSNEWMKT")){
                    HashMap allpktListMapMktALtr =util.applymktVsNewMkt(allpktListMapMkt,genericPacketLstMkt,day);
                    pktLstMkt=util.convertArrayList(allpktListMapMkt,genericPacketLstMkt);
                    gridParamsLst.addAll(fmtList);
                    gridParamsLst.add("mktvsnewmkt");
                    String [] gridParams = gridParamsLst.toArray(new String[gridParamsLst.size()]);
                    gridParams = gridParamsLst.toArray(new String[gridParamsLst.size()]);
                    dataDtl = util.getKV(req,res,pktLstMkt, gridParams,"Y","SHOWPRP","@",someFmtgridby,"T20","T20",crtwtfor);  
                    t20SalePeriodList=new ArrayList();
                    t20SalePeriodList.add("NEWMKT");
                    session.setAttribute("t20PacketLstMapMkt", (HashMap)sortByComparatorMap(allpktListMapMktALtr,"sk1"));
                    t20SalePeriodListsz=t20SalePeriodList.size();
                    hardCodeGrpKey="mktvsnewmkt";
                    hardCodeGrpVal="OLDMKT";
                }else if(ratioby.equals("OLDVSNEW")){
                    HashMap allpktListMapMktALtr =util.applyOldVsNewFromMkt(allpktListMapMkt,genericPacketLstMkt,day);
                    genericPacketLstMkt=new ArrayList();
                    pktLstMkt=new ArrayList();
                    genericPacketLstMkt=util.getKeyInArrayList(allpktListMapMktALtr);
                    pktLstMkt=util.convertArrayList(allpktListMapMktALtr,genericPacketLstMkt);
                    HashMap allpktListMapSoldALtr =util.applyOldVsNewFromSold(allpktListMapSold,genericPacketLstSold,day);
                    genericPacketLstSold=new ArrayList();
                    pktLstSold=new ArrayList();
                    genericPacketLstSold=util.getKeyInArrayList(allpktListMapSoldALtr);
                    pktLstSold=util.convertArrayList(allpktListMapSoldALtr,genericPacketLstSold);
                    HashMap dtlMap=new HashMap();
                    dtlMap.putAll(allpktListMapMktALtr);
                    dtlMap.putAll(allpktListMapSoldALtr);
                    ArrayList pktLst=new ArrayList();
                    pktLst.addAll(pktLstMkt);
                    pktLst.addAll(pktLstSold);
                    
                    t20SalePeriodList=new ArrayList();
                    t20SalePeriodList.add("NEW");
                    session.setAttribute("t20PacketLstMapAll", (HashMap)sortByComparatorMap(dtlMap,"sk1"));
                    t20SalePeriodListsz=t20SalePeriodList.size();
                    hardCodeGrpKey="oldgrp";
                    hardCodeGrpVal="OLD";
                    
                    gridParamsLst.addAll(fmtList);
                    gridParamsLst.add(hardCodeGrpKey);
                    String [] gridParams = gridParamsLst.toArray(new String[gridParamsLst.size()]);
                    gridParams = gridParamsLst.toArray(new String[gridParamsLst.size()]);
                    dataDtl = util.getKV1(req,res,pktLstMkt, gridParams,"Y","SHOWPRP","@",someFmtgridby,"T20","T20",crtwtfor,hardCodeGrpKey);
                    
                    gridParamsLst=new ArrayList();
                    gridParamsLst.addAll(fmtList);
                    gridParamsLst.add("newgrp");
                    String [] gridParamsN = gridParamsLst.toArray(new String[gridParamsLst.size()]);
                    gridParamsN = gridParamsLst.toArray(new String[gridParamsLst.size()]);
                    HashMap<String, String> rowDtl = util.getKV1(req,res,pktLstMkt, gridParamsN,"Y","SHOWPRP","@",someFmtgridby,"T20","T20",crtwtfor,"newgrp");
                    dataDtl.putAll(rowDtl);
                }
                
                if(topListByPeriod.equals(""))
                topListByPeriod=(String)t20SalePeriodList.get(0);
                session.setAttribute("t20GroupDtlMap", dataDtl);
                session.setAttribute("t20List", t20SalePeriodList);
                List<String> gridkeyList = new ArrayList<String>(dataDtl.keySet());
                for(String gridKeys : gridkeyList) {
                String [] splitGridKey=gridKeys.split("@");
                String key="";
                for(int i=0;i<fmtList.size();i++) {
                    key = key + splitGridKey[i] + "@";  
                }
                key = key.substring(0, key.length() - 1);
                if(!keyList.contains(key))
                keyList.add(key);
                }
                
                double dflt=0;
                BigDecimal MIN_INVENTORY_DAYS_BIG = new BigDecimal(MIN_INVENTORY_DAYS);
                for(int s=0;s<t20SalePeriodListsz;s++){
                String soldDays=(String)t20SalePeriodList.get(s);
                String soldP="P"+(String)t20SalePeriodList.get(s);
                HashMap map=new HashMap();
                HashMap greenSortedMap=new HashMap();
                HashMap redSortedMap=new HashMap();
                for(int k=0;k< keyList.size();k++){
                    String commonkey = (String)keyList.get(k);
                    String mktkeyQty = commonkey+"@"+hardCodeGrpVal+"@QTY";
                    String mktkeyVlu = commonkey+"@"+hardCodeGrpVal+"@VLU";
                    String mktDisplaykeyVlu = commonkey+"@"+hardCodeGrpVal+"@"+displayCOlumn;
                    String mktQty = util.nvl((String)dataDtl.get(mktkeyQty),"0");
                    String mktVlu = util.nvl((String)dataDtl.get(mktkeyVlu),"0");
                    String mktDisplayVlu = util.nvl((String)dataDtl.get(mktDisplaykeyVlu),"0");
                    BigDecimal mktDisplayVluBig = new BigDecimal(mktDisplayVlu);
                    String soldkeyVlu = commonkey+"@"+soldP+"@VLU";
                    String soldkeyQty = commonkey+"@"+soldP+"QTY";
                    String soldDisplaykeyVlu = commonkey+"@"+soldP+"@"+displayCOlumn;
                    String soldDisplaykeyrnk = commonkey+"@"+soldP+"@RATIO";
                    String soldQty = util.nvl((String)dataDtl.get(soldkeyQty),"0");
                    String soldVlu = util.nvl((String)dataDtl.get(soldkeyVlu),"0");
                    String soldDisplayVlu = util.nvl((String)dataDtl.get(soldDisplaykeyVlu),"0");
                    String svpd="";
                    String msd="";
                    ratio=0;
                    if(!soldDisplayVlu.equals("0") && !mktDisplayVlu.equals("0") && (Double.parseDouble(soldVlu) > vluLimit || Double.parseDouble(mktVlu) > vluLimit)){
                        if(ratioby.equals("RNK")){
                        BigDecimal days = new BigDecimal(Double.parseDouble(soldDays));
                        ratio=Double.parseDouble(String.valueOf((((new BigDecimal(soldDisplayVlu)).divide(days,4,RoundingMode.HALF_EVEN)).divide(mktDisplayVluBig,4,RoundingMode.HALF_EVEN)).setScale(2, RoundingMode.HALF_EVEN))); 
                        }else if(ratioby.equals("STOCK"))
                        ratio=Double.parseDouble(mktDisplayVlu);
                        else if(ratioby.equals("SOLD"))
                        ratio=Double.parseDouble(soldDisplayVlu);
                        else if(ratioby.equals("MKTVSNEWMKT") || ratioby.equals("OLDVSNEW")){
                        ratio=Double.parseDouble(String.valueOf(((new BigDecimal(mktDisplayVlu)).subtract(new BigDecimal(soldDisplayVlu)).setScale(2, RoundingMode.HALF_EVEN)))); 
                        //ratio=Double.parseDouble(String.valueOf((new BigDecimal(soldDisplayVlu).multiply(new BigDecimal(100)).divide(new BigDecimal(mktDisplayVlu),4,RoundingMode.HALF_EVEN)))); 
                        }
                        map.put(soldDisplaykeyVlu, ratio);
                        dataDtl.put(soldDisplaykeyrnk,String.valueOf(ratio));
                        if(!soldVlu.equals("") && !soldVlu.equals("0") && !ratioby.equals("MKTVSNEWMKT") && !ratioby.equals("OLDVSNEW")) {
                            svpd=util.nvl(String.valueOf(Math.round(Double.parseDouble(soldVlu)/Double.parseDouble(soldDays))),"0");
                            if(!mktVlu.equals("") && !mktVlu.equals("0") && !svpd.equals("0"))
                            msd=util.nvl(String.valueOf(Math.round(Double.parseDouble(mktVlu)/Double.parseDouble(svpd))),"0");
                            if(svpd.equals("0"))
                            svpd="";
                            dataDtl.put(commonkey+"@"+soldP+"@SVPD",svpd);
                            dataDtl.put(commonkey+"@"+soldP+"@MSD",msd);
                        }
                    }
//                    else{
//                        if(!soldQty.equals("0") || !mktQty.equals("0"))
//                        map.put(mktDisplaykeyVlu, Double.parseDouble(mktVlu)*-1);
//                    }
                }  
                
                int mapCnt=0;                    
                if(map!=null && map.size()!=0){
                    greenSortedMap=(HashMap) sortByComparator(map,"DSC");
                    Set set = greenSortedMap.keySet();
                    Iterator iter = set.iterator();
                    ArrayList greenkeyList=new ArrayList();
                    while (iter.hasNext()) {
                    greenkeyList.add((String)iter.next());
                    }
                    int greenkeyListsz= greenkeyList.size();
                    int subArrayListCount=topcount;
                    if(topcount > greenkeyListsz){
                        if ((greenkeyListsz % 2) == 0){
                            float loops = ((float)greenkeyListsz)/2;
                            subArrayListCount = Math.round(loops);
                            if(greenkeyListsz <= 2 || new Float(subArrayListCount)>=loops) {
                            } else
                             subArrayListCount += 1 ;
                            if(subArrayListCount==0)
                             subArrayListCount += 1 ; 
                        }else{
                        float loops = ((float)greenkeyListsz)/2;
                        subArrayListCount = Math.round(loops);
                        if(greenkeyListsz <= 2 || new Float(subArrayListCount)>=loops) {
                        } else
                         subArrayListCount += 1 ;
                        if(subArrayListCount==0)
                         subArrayListCount += 1 ; 
                        }
                        System.out.println(subArrayListCount);
                    }
                    ArrayList<String> validgreenkeyList = new ArrayList<String>(greenkeyList.subList(0, subArrayListCount));
                    HashMap regeneratedgreenMap=new HashMap();
                    for(int vg=0;vg<validgreenkeyList.size();vg++){
                        String keyVg=(String)validgreenkeyList.get(vg);
                        regeneratedgreenMap.put(keyVg, map.get(keyVg));
                    }
                    
                    HashMap finalgreenSortedMap=(HashMap) sortByComparator(regeneratedgreenMap,"DSC");
                    int rnk=1;
                    Set set1 = finalgreenSortedMap.keySet();
                    Iterator iter1 = set1.iterator();
                    greenkeyList=new ArrayList();
                    while (iter1.hasNext()) {
                    String key=(String)iter1.next();
                    greenkeyList.add(key);
                    dataDtl.put(key+"_GREEN_RNK",String.valueOf(rnk++));
                    }
                    dataValidGreenLst.addAll(greenkeyList);
                    greenSortedPeriodMap.put(soldP,finalgreenSortedMap);
                }
                                        
                    if(map!=null && map.size()!=0){
                        redSortedMap=(HashMap) sortByComparator(map,"ASC");
                        Set set = redSortedMap.keySet();
                        Iterator iter = set.iterator();
                        ArrayList redkeyList=new ArrayList();
                        while (iter.hasNext()) {
                        redkeyList.add((String)iter.next());
                        }
                        int redkeyListsz= redkeyList.size();
                        int subArrayListCount=topcount;
                        if(topcount > redkeyListsz){
                            if ((redkeyListsz % 2) == 0){
                                float loops = ((float)redkeyListsz)/2;
                                subArrayListCount = Math.round(loops);
                                if(redkeyListsz <= 2 || new Float(subArrayListCount)>=loops) {
                                } else
                                 subArrayListCount += 1 ;
                                if(subArrayListCount==0)
                                 subArrayListCount += 1 ; 
                            }else{
                                float loopsbottom = ((float)redkeyListsz)/2;
                                subArrayListCount = Math.round(loopsbottom);
                                if(redkeyListsz <= 2 || new Float(subArrayListCount)>=loopsbottom) {
                                    subArrayListCount -= 1;
                                } else
                                 subArrayListCount += 1 ;
                                if(subArrayListCount==0)
                                 subArrayListCount += 1 ; 
                            }
                            System.out.println(subArrayListCount);
                        }
                        
                        ArrayList<String> validredkeyList = new ArrayList<String>(redkeyList.subList(0, subArrayListCount));
                        HashMap regeneratedredMap=new HashMap();
                        for(int vg=0;vg<validredkeyList.size();vg++){
                            String keyVg=(String)validredkeyList.get(vg);
                            regeneratedredMap.put(keyVg, map.get(keyVg));
                        }
                        
                        HashMap finalredSortedMap=(HashMap) sortByComparator(regeneratedredMap,"ASC");
                        int rnk=1;
                        Set set1 = finalredSortedMap.keySet();
                        Iterator iter1 = set1.iterator();
                        redkeyList=new ArrayList();
                        while (iter1.hasNext()) {
                        String key=(String)iter1.next();
                        redkeyList.add(key);
                        dataDtl.put(key+"_RED_RNK",String.valueOf(rnk++));
                        }
                        dataValidRedLst.addAll(redkeyList);
                        redSortedPeriodMap.put(soldP,finalredSortedMap);
                    }
                }
                 
                 req.setAttribute("redSortedPeriodMap", redSortedPeriodMap);
                 req.setAttribute("greenSortedPeriodMap", greenSortedPeriodMap);
                 req.setAttribute("topListByPeriod", topListByPeriod);
                 req.setAttribute("dataValidGreenLst", dataValidGreenLst);
                 req.setAttribute("dataValidRedLst", dataValidRedLst);
                 req.setAttribute("dataDtl", dataDtl); 
                 req.setAttribute("displayColumn", displayCOlumn);
                 req.setAttribute("gridby", gridby);
                 req.setAttribute("crtwtfor", crtwtfor); 
                 req.setAttribute("fmt", fmt);
                 req.setAttribute("ratioby", ratioby);
                 req.setAttribute("topcount", topcount);
                 req.setAttribute("day", String.valueOf(day));
                 req.setAttribute("vluLimit", vluLimitString);
                 req.setAttribute("avgPct", dbmsAvgPctString);
                 req.setAttribute("hardCodeGrpKey", hardCodeGrpKey);
                 req.setAttribute("hardCodeGrpVal", hardCodeGrpVal);
                 udf.setFmtLst(fmtKey);
                 udf.setValue("topcount", topcount);
                 udf.setValue("day", String.valueOf(day));
                 udf.setValue("gridby", gridby);
                 udf.setValue("ratioby", ratioby);
                 udf.setValue("crtwtfor", crtwtfor);
                 udf.setValue("displayColumn", displayCOlumn);
                 udf.setValue("vluLimit", vluLimitString);
                 udf.setValue("avgPct", dbmsAvgPctString);
            return am.findForward("loadT20Grid");
            }
    }
    public HashMap getPacketsFromMongo(HttpServletRequest req, HttpServletResponse res,HashMap dtls) throws Exception{
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap dbinfo = info.getDmbsInfoLst();
        String mongodb = util.nvl((String)dbinfo.get("MONGODB"));
        String rep_path = util.nvl((String)dbinfo.get("REP_PATHNEW"),util.nvl((String)dbinfo.get("REP_PATH")));
        GenericInterface genericInt = new GenericImpl();
        String lprpTyp=util.nvl((String)dtls.get("prpTyp"));
        String lprpStr=util.nvl((String)dtls.get("prp"));
        String lprpVal=util.nvl((String)dtls.get("prpVal"));
        String sttStr=util.nvl((String)dtls.get("stt"));
        String mdl=util.nvl((String)dtls.get("mdl"));
        String hardcode=util.nvl((String)dtls.get("hardcode"));
        String tblNme=util.nvl((String)dtls.get("tblNme"));
        long startm = new Date().getTime();
        System.out.println(lprpTyp);
        System.out.println(lprpStr);
        System.out.println(lprpVal);
        ArrayList t20SalePeriodList = (session.getAttribute("t20SalePeriodList") == null)?new ArrayList():(ArrayList)session.getAttribute("t20SalePeriodList");
        int t20SalePeriodListsz=t20SalePeriodList.size();
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, mdl,mdl);
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        int todaysdate=util.getdoubleDateInyyyyMMdd();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(rep_path+"/diaflexWebService/REST/WebService/searchValues");
        postRequest.setHeader("Accept", "application/json");
        postRequest.setHeader("Content-type", "application/json");
        JSONObject jObj = new JSONObject();
        try{
                 jObj.put("prpTyp", lprpTyp);
                 jObj.put("prp", lprpStr);
                 jObj.put("prpVal", lprpVal);
                 jObj.put("stt", sttStr);
                 jObj.put("mdl", mdl);
                 jObj.put("pkt_ty", "NR");
                 jObj.put("tblNme", tblNme);
                 jObj.put("clientName", mongodb);                                     
        } catch (JSONException jsone) {
        jsone.printStackTrace();
        }
                     
        StringEntity insetValue = new StringEntity(jObj.toString());
        insetValue.setContentType(MediaType.APPLICATION_JSON);
        postRequest.setEntity(insetValue);
                                            
        HttpResponse responsejson = httpClient.execute(postRequest);
                                            
        if (responsejson.getStatusLine().getStatusCode() !=200) {
        throw new RuntimeException("Failed : HTTP error code : "
                               + responsejson.getStatusLine().getStatusCode());
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
        HashMap pktListMap=new HashMap();
        
        int count = 0;
        if(!jsonStr.equals("")){
        JSONObject  jObject = new JSONObject(jsonStr);
        JSONArray  dataObject = (JSONArray)jObject.get("STKDTL");
        if(dataObject!=null){
            for (int i = 0; i < dataObject.length(); i++) {
                count++;
                JSONObject pktDtl = dataObject.getJSONObject(i);
                HashMap pktPrpMap = new HashMap();
                String stt=util.nvl((String)pktDtl.get("STATUS"));
                String grp1=util.nvl((String)pktDtl.get("GRP1"));
                String rap_rte=util.nvl((String)pktDtl.get("RAP_RTE"));
                String cts=util.nvl((String)pktDtl.get("CRTWT"),"0.01");
                String stk_idn=util.nvl((String)pktDtl.get("STK_IDN"),"0.01");
                pktPrpMap.put("stk_idn", stk_idn);  
                pktPrpMap.put("pkt_ty", util.nvl((String)pktDtl.get("PKT_TY")));
                pktPrpMap.put("vnm", util.nvl((String)pktDtl.get("PACKETCODE")));
                pktPrpMap.put("qty", util.nvl((String)pktDtl.get("QTY")));
                pktPrpMap.put("cts", cts);
                pktPrpMap.put("cmp", util.nvl((String)pktDtl.get("RTE")));
                pktPrpMap.put("rap_rte", rap_rte);
                pktPrpMap.put("cert_lab", util.nvl((String)pktDtl.get("LAB")));
                pktPrpMap.put("sk1", new BigDecimal(util.nvl((String)pktDtl.get("SK1"),"0")));
                pktPrpMap.put("flg", "Z");
                if(stt.equals("MKSL") || stt.equals("MKSD") || stt.equals("BRC_MKSD")){
                pktPrpMap.put("quot", util.nvl((String)pktDtl.get("SAL_RTE")));
                pktPrpMap.put("fnl_usd", util.nvl((String)pktDtl.get("SAL_FNLBSE")));
                }else{
                pktPrpMap.put("quot", util.nvl((String)pktDtl.get("RTE")));
                pktPrpMap.put("fnl_usd", util.nvl((String)pktDtl.get("RTE")));
                }
                if(!hardcode.equals("")){
                pktPrpMap.put("hardCodeGrp", hardcode);
                pktPrpMap.put("hardCodeGrp_GRP", hardcode);
                }else{
                pktPrpMap.put("hardCodeGrp", "");
                pktPrpMap.put("hardCodeGrp_GRP", hardcode);
                }
                pktPrpMap.put("dsp_stt", stt);
                pktPrpMap.put("stt", grp1);
                pktPrpMap.put("org_dsp_stt", stt);
                pktPrpMap.put("org_stt", grp1);
                pktPrpMap.put("actstt", stt);
                pktPrpMap.put("rap_dis", util.nvl((String)pktDtl.get("RAP_DIS")));
//                pktPrpMap.put("CERTIMG", util.nvl((String)pktDtl.get("CERTIMG")));
//                pktPrpMap.put("DIAMONDIMG", util.nvl((String)pktDtl.get("DIAMONDIMG")));
//                pktPrpMap.put("JEWIMG", util.nvl((String)pktDtl.get("JEWIMG")));
//                pktPrpMap.put("SRAYIMG", util.nvl((String)pktDtl.get("SRAYIMG")));
//                pktPrpMap.put("AGSIMG", util.nvl((String)pktDtl.get("AGSIMG")));
//                pktPrpMap.put("MRAYIMG", util.nvl((String)pktDtl.get("MRAYIMG")));
//                pktPrpMap.put("RINGIMG", util.nvl((String)pktDtl.get("RINGIMG")));
//                pktPrpMap.put("LIGHTIMG", util.nvl((String)pktDtl.get("LIGHTIMG")));
//                pktPrpMap.put("REFIMG", util.nvl((String)pktDtl.get("REFIMG")));
//                pktPrpMap.put("VIDEOS", util.nvl((String)pktDtl.get("VIDEOS")));
//                pktPrpMap.put("VIDEO_3D", util.nvl((String)pktDtl.get("VIDEO_3D")));
                pktPrpMap.put("byr", util.nvl((String)pktDtl.get("SAL_BYR")));
                pktPrpMap.put("emp", util.nvl((String)pktDtl.get("SAL_EMP")));
                pktPrpMap.put("byr_cntry", util.nvl((String)pktDtl.get("BYR_COUNTRY")));
                pktPrpMap.put("rmk", util.nvl((String)pktDtl.get("SAL_RMK")));
                pktPrpMap.put("QTY", "1");
                String pkt_dte="";
                String sal_dte="";
                try{
                String recpt_dt=util.nvl((String)pktDtl.get("RECPT_DT"));
                String trf_dte=util.nvl((String)pktDtl.get("TRF_DTE"));
                if(grp1.equals("MKT") || grp1.equals("MKAP") || grp1.equals("MKIS") || grp1.equals("MKTOUT") || grp1.equals("SOLD")){
                    pkt_dte=trf_dte;
                }else{
                    pkt_dte=recpt_dt;
                }
                sal_dte=util.nvl((String)pktDtl.get("SAL_DTE"));
                pktPrpMap.put("sl_dte", sal_dte);
                pktPrpMap.put("pkt_dte", pkt_dte);
                if(!pkt_dte.equals("") && !pkt_dte.equals("0")){
                    pktPrpMap.put("pkt_dte_SRT", Integer.parseInt(util.toconvertStringtoDate(pkt_dte)));
                }
                if(!sal_dte.equals("") && !sal_dte.equals("0")){
                    int pktdte = Integer.parseInt(util.toconvertStringtoDate(sal_dte));
                    pktPrpMap.put("sl_dte_SRT", pktdte);
                    int days=util.diffInDaysBetweendate("yyyyMMdd",pktdte,todaysdate);
                    for(int s=0;s<t20SalePeriodListsz;s++){
                        if(days <= Integer.parseInt((String)t20SalePeriodList.get(s))){
                            pktPrpMap.put((String)t20SalePeriodList.get(s),"P"+(String)t20SalePeriodList.get(s));
                            pktPrpMap.put((String)t20SalePeriodList.get(s)+"_GRP","P"+(String)t20SalePeriodList.get(s));
                        }
                    }
                }
                }catch(JSONException js){
                    pktPrpMap.put("sl_dte", sal_dte);
                    pktPrpMap.put("pkt_dte", pkt_dte);
                    pktPrpMap.put("sl_dte_SRT", 0);
                }
                for (int j = 0; j < vwPrpLst.size(); j++) {
                  try{
                        String lprp = (String)vwPrpLst.get(j);
                        String typ = util.nvl((String)mprp.get(lprp+"T"));
                        String prpVal="";
                        String prepSrtVal="";
                        String prepGrpVal="";
                        String fldVal = util.nvl((String)pktDtl.get(lprp));
                        if(typ.equals("C")){
                           
                            ArrayList lprpS = (ArrayList)prp.get(lprp + "S");
                            ArrayList lprpV = (ArrayList)prp.get(lprp + "V");
                            ArrayList lprpG = (ArrayList)prp.get(lprp + "G");
                           
                      if(fldVal!=""){
                                      Double obj = new Double(fldVal);
                                      int prpSrt = obj.intValue();
                                      prepSrtVal = String.valueOf(prpSrt);
                                      try{
                                      prpVal = (String)lprpV.get(lprpS.indexOf(prepSrtVal));
                                      prepGrpVal = (String)lprpG.get(lprpS.indexOf(prepSrtVal));
                                      if(lprp.equals("CO") || lprp.equals("COL") || lprp.equals("PU") || lprp.equals("CLR")){
                                              prpVal=prpVal.replaceAll("\\+", "");
                                              prpVal=prpVal.replaceAll("\\-", "");
                                              prepSrtVal = (String)lprpS.get(lprpV.indexOf(prpVal));
                                              prepGrpVal = (String)lprpG.get(lprpS.indexOf(prepSrtVal));
                                      }
                                      }catch(ArrayIndexOutOfBoundsException a){
                                          prpVal=""; 
                                          prepSrtVal="";
                                          prepGrpVal="";
                                      }
                      }
                      }else{
                            prpVal = fldVal;
                            prepSrtVal=prpVal;
                            prepGrpVal=prpVal;
                            if(typ.equals("T"))
                            prepSrtVal="10";
                            if(typ.equals("D"))
                            prepSrtVal=util.toconvertStringtoDate(fldVal);
                      }
                    
                            pktPrpMap.put(lprp, prpVal);
                            pktPrpMap.put(lprp+"_SRT", prepSrtVal);
                            pktPrpMap.put(lprp+"_GRP", prepGrpVal);
                      }catch(JSONException js){
                          
                      }
                    }
                    pktListMap.put(stk_idn,pktPrpMap);
            }
        }
        }
        long endm = new Date().getTime();
        System.out.println("@ Collection on Table "+tblNme+" Total Time = "+ ((endm-startm)/1000));
        return pktListMap;
    }
    public ActionForward pktDtlt20(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
           util.updAccessLog(req,res,"T20 Report", "pktDtlt20 start");
        UserRightsForm udf = (UserRightsForm)af;
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "T20","T20");
        ArrayList pktList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        HashMap dbinfo = info.getDmbsInfoLst();
           
        String gridby=util.nvl((String)req.getParameter("gridby")).trim();
        String fmt=util.nvl((String)req.getParameter("fmt")).trim();
        String soldP = util.nvl((String)req.getParameter("soldP")).trim();
        String key=util.nvl((String)req.getParameter("key")).trim();
        String hardCodeGrpval = util.nvl((String)req.getParameter("hardCodeGrp")).trim();
        String hardCodeGrpKey = util.nvl((String)req.getParameter("hardCodeGrpKey")).trim();
        String crtwtfor = util.nvl((String)req.getParameter("crtwtfor")).trim();
        String groupMatchCOlumn="";
        String groupMatchCOlumnVlu="P"+soldP;
        if(!gridby.equals(""))
        gridby="_"+gridby;
           
        if(!hardCodeGrpval.equals(""))   
        groupMatchCOlumn=hardCodeGrpKey+gridby;
        else
        groupMatchCOlumn=soldP+gridby;
        
        if(!hardCodeGrpval.equals(""))   
        groupMatchCOlumnVlu=hardCodeGrpval;

        
        HashMap allpktListMap=new HashMap();
        if(hardCodeGrpval.equals("") && !soldP.equals("NEWMKT"))
        allpktListMap = (session.getAttribute("t20PacketLstMapSold") == null)?new HashMap():(HashMap)session.getAttribute("t20PacketLstMapSold");
        else
        allpktListMap = (session.getAttribute("t20PacketLstMapMkt") == null)?new HashMap():(HashMap)session.getAttribute("t20PacketLstMapMkt");
        ArrayList genericPacketLst=util.getKeyInArrayList(allpktListMap); 
        int genericPacketLstsz=genericPacketLst.size();
        String [] fmtKey=fmt.split("_");
        List fmtList = Arrays.asList(fmtKey);
        int fmtListsz=fmtList.size();
           
        String [] datakey=key.split("@");
        List dataValList = Arrays.asList(datakey);
        itemHdr.add("Sr No");
        itemHdr.add("stt");
        itemHdr.add("vnm");
        int sr=1;
        for (int i = 0; i < vwPrpLst.size(); i++) {
             String prp=util.nvl((String)vwPrpLst.get(i));
             itemHdr.add(prp);
        }
           
           for(int i=0;i<genericPacketLstsz;i++){
               HashMap pktPrpMap = new HashMap();
               pktPrpMap= (HashMap)allpktListMap.get(genericPacketLst.get(i));
               double ctsCmp=Double.parseDouble(util.nvl((String)pktPrpMap.get("CRTWT"),"0"));
               boolean vldpkt=true;
               if(crtwtfor.equals("DOWN")){
                   if(ctsCmp>=1.0000)
                   vldpkt=false; 
               }else if(crtwtfor.equals("UP")){
                   if(ctsCmp<=0.99999)
                   vldpkt=false; 
               }
               String val=util.nvl((String)pktPrpMap.get(groupMatchCOlumn));
               if(!val.equals(groupMatchCOlumnVlu)){
                   vldpkt=false;
               }
               if(vldpkt){
                   for (int p = 0; p < fmtListsz; p++) {
                   String pktfldval=util.nvl((String)pktPrpMap.get(fmtList.get(p)+gridby)).trim();
                   String comparefldval=util.nvl((String)dataValList.get(p)).trim();
                   if(!comparefldval.equals(pktfldval)){
                   vldpkt=false;
                   break;
                   }
                   }
               }
               if(vldpkt){
                   pktPrpMap.put("Sr No", String.valueOf(sr++));
                   pktList.add(pktPrpMap);
               }
           }
           
           session.setAttribute("pktList", pktList);
           session.setAttribute("itemHdr",itemHdr);
       util.updAccessLog(req,res,"T20 Report", "pktDtlt20 end");
        return am.findForward("pktDtl");
       }
    }
    public void t20Rule(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap t20Sale = (session.getAttribute("t20Sale") == null)?new HashMap():(HashMap)session.getAttribute("t20Sale");
        ArrayList t20SalePeriodList=new ArrayList();
        try {
        if(t20Sale.size() == 0) {
        String gtView = "select chr_fr, chr_to , dsc , dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
        " b.mdl = 'JFLEX' and b.nme_rule = 'T20_SALE' and a.til_dte is null order by a.srt_fr ";
        ResultSet rs = db.execSql("gtView", gtView, new ArrayList());
        while (rs.next()) {
        t20Sale.put(util.nvl(rs.getString("chr_fr")), util.nvl(rs.getString("dsc")));
        t20SalePeriodList.add(util.nvl(rs.getString("chr_fr")));
        }
        rs.close();
        session.setAttribute("t20Sale", t20Sale);
        session.setAttribute("t20SalePeriodList", t20SalePeriodList);
        }
        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        }
        
        ArrayList t20TopList = (session.getAttribute("t20TopList") == null)?new ArrayList():(ArrayList)session.getAttribute("t20TopList");
        try {
        if(t20TopList.size() == 0) {
        String gtView = "select chr_fr, chr_to , dsc , dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
        " b.mdl = 'JFLEX' and b.nme_rule = 'T20_TOP' and a.til_dte is null order by a.srt_fr ";
        ResultSet rs = db.execSql("gtView", gtView, new ArrayList());
        while (rs.next()) {
        t20TopList.add(util.nvl(rs.getString("chr_fr")));
        }
        rs.close();
        session.setAttribute("t20TopList", t20TopList);
        }
        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        }
    }
    
    public  Map<String, Double> sortByComparator(Map<String, Double> unsortMap,final String orderBy) {
      
         // Convert Map to List
         List<Map.Entry<String, Double>> list = 
           new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());
      
         // Sort list with comparator, to compare the Map values
         Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
           public int compare(Map.Entry<String, Double> o1,
                                                Map.Entry<String, Double> o2) {
           if(orderBy.equals("ASC"))
           return ((Comparable) ((Map.Entry<String, Double>) (o1)).getValue()).compareTo(((Map.Entry<String, Double>) (o2)).getValue());
           else
           return ((Comparable) ((Map.Entry<String, Double>) (o2)).getValue()).compareTo(((Map.Entry<String, Double>) (o1)).getValue());  
           }
         });
      
         // Convert sorted map back to a Map
         Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
             for (Iterator<Map.Entry<String, Double>> it = list.iterator(); it.hasNext();) {
               Map.Entry<String, Double> entry = it.next();
               sortedMap.put(entry.getKey(), entry.getValue());
             }
             return sortedMap;
        
       }
    public  Map<String, HashMap> sortByComparatorMap(Map<String, HashMap> unsortMap,final String sortby) {
     
        // Convert Map to List
        List<Map.Entry<String, HashMap>> list = 
          new LinkedList<Map.Entry<String, HashMap>>(unsortMap.entrySet());
     
        // Sort list with comparator, to compare the Map values
        Collections.sort(list, new Comparator<Map.Entry<String, HashMap>>() {
          public int compare(Map.Entry<String, HashMap> o1,
                                               Map.Entry<String, HashMap> o2) {
            return ((Comparable) ((Map.Entry<String, HashMap>) (o1)).getValue().get(sortby) )
                              .compareTo(((Map.Entry<String, HashMap>) (o2)).getValue().get(sortby));
          }
        });
     
        // Convert sorted map back to a Map
        Map<String, HashMap> sortedMap = new LinkedHashMap<String, HashMap>();
            for (Iterator<Map.Entry<String, HashMap>> it = list.iterator(); it.hasNext();) {
              Map.Entry<String, HashMap> entry = it.next();
              sortedMap.put(entry.getKey(), entry.getValue());
            }
            return sortedMap;
       
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
            util.updAccessLog(req,res,"Screen Action", "init");
            }
            }
            return rtnPg;
            }
}
