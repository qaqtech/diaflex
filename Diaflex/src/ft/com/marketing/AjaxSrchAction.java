package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GetPktPrice;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.dao.GtPktDtl;
import ft.com.dao.TrmsDao;

import java.io.IOException;

import java.net.URLEncoder;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AjaxSrchAction extends DispatchAction {
    
    
    
    public ActionForward loadFav(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      int fav = 0;
      int dmd = 0 ;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      ArrayList ary = new ArrayList();
      StringBuffer sb = new StringBuffer();
      String rlnId = req.getParameter("rlnId");
      String favSrch = "select fav_id, dsc from mfavsrch where rln_id=? and mdl='WWW' and tdt is null";
      ary = new ArrayList();
      ary.add(rlnId);
     ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          fav=1;
          sb.append("<favsrch>");
          sb.append("<favid>"+util.nvl(rs.getString(1).toLowerCase()) +"</favid>");
          sb.append("<favdsc>"+util.nvl(rs.getString(2)) +"</favdsc>");
          sb.append("</favsrch>");
      }
       if(fav==1)
        res.getWriter().write("<favsrchs>" +sb.toString()+ "</favsrchs>");
       
        rs.close();
        pst.close();
        return null;
    }
    public ActionForward loadXrt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      int fav = 0;
      int dmd = 0 ;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      ArrayList ary = new ArrayList();
      StringBuffer sb = new StringBuffer();
      String rlnId = req.getParameter("rlnId");
      String favSrch = "select cur rln, get_xrt(cur) xrt from nmerel where nmerel_idn = ? ";
      ary = new ArrayList();
      ary.add(rlnId);
      ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          fav=1;
          sb.append("<xrts>");
          sb.append("<cur>"+util.nvl(rs.getString(1),"0").toLowerCase() +"</cur>");
          sb.append("<xrt>"+util.nvl(rs.getString(2),"0") +"</xrt>");
          sb.append("</xrts>");
      }
       if(fav==1)
        res.getWriter().write("<xrtLst>" +sb.toString()+ "</xrtLst>");
       
        
        
        
        rs.close();
        pst.close();
        return null;
    }
  public ActionForward getRefIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    
      return null;
  }
    public ActionForward loadTrm(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      int fav = 0;
      int dmd = 0 ;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      ArrayList ary = new ArrayList();
      StringBuffer sb = new StringBuffer();
      String rlnId = util.nvl(req.getParameter("bryId"));
        ary = new ArrayList();
        ary.add(rlnId);
      String str = "where nme_idn=? order by 1 desc";
      if(rlnId.equals("0")){
          str = "";
          ary = new ArrayList();
          System.out.println("nmerel is 0 happen with :"+info.getUsr());
      }
      String favSrch = " select nvl(dflt_yn,'N') , form_url_encode(dtls) nme  , nmerel_idn from nme_rel_v  "+str ;
      ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          fav=1;
          sb.append("<trm>");
          sb.append("<trmDtl>"+util.nvl(rs.getString("nme").toLowerCase()) +"</trmDtl>");
          sb.append("<relId>"+util.nvl(rs.getString("nmerel_idn")) +"</relId>");
          sb.append("</trm>");
      }
       if(fav==1)
        res.getWriter().write("<trms>" +sb.toString()+ "</trms>");
        rs.close();
        pst.close();
           return null;
    }
    public ActionForward loadSLTrm(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      int fav = 0;
      int dmd = 0 ;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      ArrayList ary = new ArrayList();

      StringBuffer sb = new StringBuffer();
      ArrayList trmsLst = new ArrayList();
      String rlnId = util.nvl(req.getParameter("bryId"));
        ary = new ArrayList();
        ary.add(rlnId);
      String str = " and nme_idn=?";
      if(rlnId.equals("0")){
          str = "";
          ary = new ArrayList();
      }
      String favSrch = "select b.term||' '||cur||' : '||form_url_encode(byr.get_nm(AADAT_IDN))||'/'||form_url_encode(byr.get_nm(mbrk2_idn)) dtls, nmerel_idn, nme_idn from nmerel a, mtrms b where a.trms_idn = b.idn and a.vld_dte is null  and exists (select 1 from msal c where c.stt = 'IS' and (c.nmerel_idn = a.nmerel_idn or a.nmerel_idn = c.fnl_trms_idn)) "+str ;
      ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          fav=1;
     
          TrmsDao trms = new TrmsDao();
          trms.setRelId(rs.getInt(2));
          trms.setTrmDtl(rs.getString(1));
          sb.append("<trm>");
          sb.append("<trmDtl>"+util.nvl(rs.getString(1).toLowerCase()) +"</trmDtl>");
          sb.append("<relId>"+util.nvl(rs.getString(2)) +"</relId>");
          sb.append("</trm>");
          trmsLst.add(trms);
      }
       if(fav==1)
        res.getWriter().write("<trms>" +sb.toString()+ "</trms>");
       
    
        
        
        rs.close();
        pst.close();
        return null;
    }
    
    
    public ActionForward loadDmd(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      int fav = 0;
      int dmd = 0 ;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      ArrayList ary = new ArrayList();
     
      StringBuffer sb = new StringBuffer();
      ArrayList trmsLst = new ArrayList();
      String byrId = req.getParameter("bryId");
      String rlnId = req.getParameter("term");
        //      String dmdSrch = " select dmd_id , form_url_encode(nvl(dsc,'Not Given'))||' : '||to_Char(mdl) dsc from mdmd where name_id = ? and rln_Id=? and todte is null  order by  dmd_id desc ";
              String dmdSrch = " select dmd_id , form_url_encode(nvl(dsc,'Not Given'))||' : '||to_Char(mdl) dsc from mdmd where name_id = ? and todte is null  order by  dmd_id desc ";
              ary = new ArrayList();
              ary.add(byrId);
        //      ary.add(rlnId);
      ArrayList outLst = db.execSqlLst("dmd",dmdSrch, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                fav = 1;

                sb.append("<dmd>");
                sb.append("<dmdId>" + util.nvl(rs.getString(1).toLowerCase(),"Dscnone") +
                          "</dmdId>");
                sb.append("<dmdDsc>" + util.nvl(rs.getString(2),"Dscnone") +
                          "</dmdDsc>");
                sb.append("</dmd>");

            }
            rs.close();
            pst.close();
                res.getWriter().write("<dmds>" + sb.toString() + "</dmds>");
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
        
       return null;
    }
    
    public ActionForward loadWR(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
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
    ArrayList ary = new ArrayList();

    StringBuffer sb = new StringBuffer();

    String byrId = req.getParameter("bryId");
    String rlnId = req.getParameter("rlnId");
    String dmdSrch = "select distinct a.idn , to_char(a.dte, 'DD-MON HH24:mi') dte,form_url_encode(nvl(a.flg3,'NA')) flg3 from mjan a,jandtl b where a.idn=b.idn and a.typ='WR' and a.nmerel_idn=? and trunc(a.dte)>= trunc(sysdate)-7 order by a.idn desc" ;

    ary = new ArrayList();

    ary.add(rlnId);
      ArrayList outLst = db.execSqlLst("dmd",dmdSrch, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while (rs.next()) {
    sb.append("<memo>");
    sb.append("<memoId>" + util.nvl(rs.getString("idn").toLowerCase())+"</memoId>");
    sb.append("<memoDte>" + util.nvl(rs.getString("dte"))+"</memoDte>");
    sb.append("<memoFlg>" + util.nvl(rs.getString("flg3"))+"</memoFlg>");
    sb.append("</memo>");
    }
    rs.close();
        pst.close();
    res.getWriter().write("<memos>" + sb.toString() + "</memos>");
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    } catch (IOException ioe) {
    // TODO: Add catch code
    ioe.printStackTrace();
    }
    return null;
    }
    
    public ActionForward loadHR(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
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
    ArrayList ary = new ArrayList();

    StringBuffer sb = new StringBuffer();

    String byrId = req.getParameter("bryId");
    String rlnId = req.getParameter("rlnId");
    String dmdSrch = "select distinct a.idn , to_char(a.dte, 'DD-MON HH24:mi') dte,form_url_encode(nvl(a.flg3,'NA')) flg3 from mjan a,jandtl b where a.idn=b.idn and a.typ='HR' and a.nmerel_idn=? and trunc(a.dte)>= trunc(sysdate)-10 order by a.idn desc" ;

    ary = new ArrayList();

    ary.add(rlnId);
      ArrayList outLst = db.execSqlLst("dmd",dmdSrch, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while (rs.next()) {
    sb.append("<memo>");
    sb.append("<memoId>" + util.nvl(rs.getString("idn").toLowerCase())+"</memoId>");
    sb.append("<memoDte>" + util.nvl(rs.getString("dte"))+"</memoDte>");
    sb.append("<memoFlg>" + util.nvl(rs.getString("flg3"))+"</memoFlg>");
    sb.append("</memo>");
    }
    rs.close();
        pst.close();
    res.getWriter().write("<memos>" + sb.toString() + "</memos>");
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    } catch (IOException ioe) {
    // TODO: Add catch code
    ioe.printStackTrace();
    }
    return null;
    }
    public ActionForward getLstMemo(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    int srno=0;
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    ArrayList ary = new ArrayList();

    StringBuffer sb = new StringBuffer();
        ary = new ArrayList();
    String byrId = util.nvl(req.getParameter("bryId"),"0");
    String rlnId = util.nvl(req.getParameter("rlnId"),"0");
    String dmdSrch = "    select a.idn ,a.typ, to_char(a.dte, 'DD-MON HH24:mi') dte,sum(b.qty) qty,to_char(sum(b.cts),'999999990.09') cts\n" + 
    "    from mjan a,jandtl b\n" + 
    "    where \n" + 
    "    a.idn=b.idn and\n" + 
    "    a.typ not in ('NG')  and trunc(b.dte)>= trunc(sysdate)-10";
    if(!rlnId.equals("") && !rlnId.equals("0")){
    dmdSrch=dmdSrch+"    and a.nmerel_idn=? ";
        ary.add(rlnId);
    }
    if(!byrId.equals("") && !byrId.equals("0")){
        dmdSrch=dmdSrch+"    and a.nme_idn=? ";
            ary.add(byrId);
    }
            
    dmdSrch=dmdSrch+"   group by a.idn,a.typ,a.dte\n" + 
    "    order by a.idn desc " ;

  

   
      ArrayList outLst = db.execSqlLst("dmd",dmdSrch, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while (rs.next()) {
        srno = srno+1;
    sb.append("<memo>");
    sb.append("<memoId>" + util.nvl(rs.getString("idn").toLowerCase())+"</memoId>");
    sb.append("<qty>" + util.nvl(rs.getString("qty"))+"</qty>");
    sb.append("<cts>" + util.nvl(rs.getString("cts"))+"</cts>");
    sb.append("<dte>" + util.nvl(rs.getString("dte"))+"</dte>");
    sb.append("<typ>" + util.nvl(rs.getString("typ"))+"</typ>");
    sb.append("</memo>");
    }
    rs.close();
        pst.close();
    res.getWriter().write("<memos>" + sb.toString() + "</memos>");
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    } catch (IOException ioe) {
    // TODO: Add catch code
    ioe.printStackTrace();
    }
    return null;
    }
    
    public ActionForward getLstMemoNG(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    int srno=0;
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    ArrayList ary = new ArrayList();

    StringBuffer sb = new StringBuffer();

    String byrId = req.getParameter("bryId");
    String rlnId = req.getParameter("rlnId");
    String dmdSrch = "    select a.idn , to_char(a.dte, 'DD-MON HH24:mi') dte,sum(b.qty) qty,to_char(sum(b.cts),'999999990.09') cts\n" + 
    "    from mjan a,jandtl b\n" + 
    "    where \n" + 
    "    a.idn=b.idn and\n" + 
    "    a.typ in ('NG')\n" + 
    "    and a.nmerel_idn=? \n" + 
    "    group by a.idn,a.dte\n" + 
    "    order by a.idn desc " ;

    ary = new ArrayList();

    ary.add(rlnId);
      ArrayList outLst = db.execSqlLst("dmd",dmdSrch, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while (rs.next()) {
        srno = srno+1;
    sb.append("<memo>");
    sb.append("<memoId>" + util.nvl(rs.getString("idn").toLowerCase())+"</memoId>");
    sb.append("<qty>" + util.nvl(rs.getString("qty"))+"</qty>");
    sb.append("<cts>" + util.nvl(rs.getString("cts"))+"</cts>");
    sb.append("<dte>" + util.nvl(rs.getString("dte"))+"</dte>");
    sb.append("</memo>");
        if(srno==5)
        break;
    }
    rs.close();
        pst.close();
    res.getWriter().write("<memos>" + sb.toString() + "</memos>");
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    } catch (IOException ioe) {
    // TODO: Add catch code
    ioe.printStackTrace();
    }
    return null;
    }
    public ActionForward loadEmp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      int fav = 0;
      int dmd = 0 ;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      boolean isSaleExc = info.getIsSalesExec();
      ArrayList ary = new ArrayList();
      SearchForm srchForm = (SearchForm)af;
      StringBuffer sb = new StringBuffer();
      ArrayList trmsLst = new ArrayList();
      String nmeId = util.nvl(req.getParameter("bryId"));
      String str = " and nvl(emp_idn,0) = ? ";
        ary = new ArrayList();
        ary.add(nmeId);
      if(nmeId.equals("0")){
          str="";
          ary = new ArrayList();
      }
      if(isSaleExc){
          str = " and nvl(emp_idn,0) = ? ";
          ary = new ArrayList();
          ary.add(info.getDfNmeIdn());
      }
      String favSrch = "select form_url_encode(a.nme) nme, a.nme_idn from nme_v a where 1 = 1 "+str+
                        " and exists (select 1 from nmerel b where a.nme_idn = b.nme_idn and b.vld_dte is null and b.flg = 'CNF') "+
                        "order by 1 ";
   
      ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          fav=1;
          String nme = util.nvl(rs.getString(1));
         
          sb.append("<emp>");
          sb.append("<nme>" + nme +"</nme>");
          sb.append("<nmeid>"+util.nvl(rs.getString(2),"0") +"</nmeid>");
          sb.append("</emp>");
     }
        rs.close();
        pst.close();
       if(fav==1)
        res.getWriter().write("<emps>" +sb.toString()+ "</emps>");
       
        int ct = db.execUpd("delete gt","Delete from gt_srch_rslt", new ArrayList());
        
        
    
        return null;
    }
    
    public ActionForward loadEmpDmd(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      int fav = 0;
      int dmd = 0 ;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      boolean isSaleExc = info.getIsSalesExec();
      ArrayList ary = new ArrayList();
      SearchForm srchForm = (SearchForm)af;
      StringBuffer sb = new StringBuffer();
      ArrayList trmsLst = new ArrayList();
      String nmeId = util.nvl(req.getParameter("bryId"));
      String str = " and nvl(a.emp_idn,0) = ? ";
        ary = new ArrayList();
        ary.add(nmeId);
      if(nmeId.equals("0")){
          str="";
          ary = new ArrayList();
      }
      if(isSaleExc){
          str = " and nvl(a.emp_idn,0) = ? ";
          ary = new ArrayList();
          ary.add(info.getDfNmeIdn());
      }
      String favSrch = "select nme_idn, form_url_encode(initcap(trim(nme))) nme from nme_v a where 1 = 1 "+str+
      " and exists (select distinct b.name_id from mdmd b where a.nme_idn = b.name_id and b.toDte is null) and a.ss_flg='Y' order by initcap(trim(nme))";
    
      ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          fav=1;         
          sb.append("<emp>");
          sb.append("<nme>" + util.nvl(rs.getString("nme")) +"</nme>");
          sb.append("<nmeid>"+util.nvl(rs.getString("nme_idn")) +"</nmeid>");
          sb.append("</emp>");
     }
        rs.close();
        pst.close();
       if(fav==1)
        res.getWriter().write("<emps>" +sb.toString()+ "</emps>");
       
        int ct = db.execUpd("delete gt","Delete from gt_srch_rslt", new ArrayList());
        
        
    
        return null;
    }
    public ActionForward selectList(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        SearchQuery srchQuery = new SearchQuery();
        String checktype= req.getParameter("Lst");
        String status = req.getParameter("stt");
        String stkid = req.getParameter("stkIdn");
        String memoTyp = req.getParameter("memoTyp");
        String gtFlg = req.getParameter("flg");
        ArrayList memoList = info.getMemoLst();
        ArrayList params = null;
        String viewForm = info.getViewForm();
        
        String chngFlg = "M";
        String rejflg="";
        if(viewForm.equals("MP"))
            chngFlg = "MT";
        String all = req.getParameter("ALL");
        if(all!=null){
             gtFlg = "Z";
            if(viewForm.equals("MP"))
                gtFlg = "P";
            rejflg=gtFlg;
            String updGT="Update gt_srch_rslt set flg = ? where stt in (select  b.chr_fr from mrule a , rule_dtl b where a.rule_idn = b.rule_idn and a.mdl='MEMO_ALW'  and b.dsc =?) and flg not in('R') ";
           
            params = new ArrayList();
            if(status.equals("true")){
            params.add(chngFlg);
            params.add(memoTyp);
            }else{
            updGT="Update gt_srch_rslt set flg = ? where flg not in('R')";
            params.add(gtFlg); 
            }
            int ct = db.execUpd("Upd Usr", updGT, params);
            memoList.add("YES");
        }else{
        gtFlg = "Z";
        if(viewForm.equals("MP"))
        gtFlg = "P";
        rejflg=gtFlg;
        String updGT="Update gt_srch_rslt set flg = ? where stk_idn = ? and flg= ?  and flg not in('R')";
        String nwFlg = gtFlg;
        if(status.equals("true")){
            if(checktype.equals("MEMO")) {
                nwFlg = chngFlg ;        
                memoList.add(stkid);
               
            } 
            
           
            
        } else {
            memoList.remove(stkid);
            gtFlg=chngFlg;
        }
        
        info.setMemoLst(memoList);
        
        
        params = new ArrayList();
        params.add(nwFlg);
        params.add(stkid);
        params.add(gtFlg);
        int ct = db.execUpd("Upd Usr", updGT, params);
        }
        HashMap ttls = util.getTtls(req); 
        StringBuffer sb = new StringBuffer();
        sb.append("<value>");
        sb.append("<qty>"+util.nvl((String)ttls.get(chngFlg+"Q"),"0") +"</qty>");
        sb.append("<cts>"+util.nvl((String)ttls.get(chngFlg+"W"),"0") +"</cts>");
        sb.append("<vlu>"+util.nvl((String)ttls.get(chngFlg+"V"),"0") +"</vlu>");
        sb.append("<avg>"+util.nvl((String)ttls.get(chngFlg+"A"),"0") +"</avg>");
        sb.append("<dis>"+util.nvl((String)ttls.get(chngFlg+"D"),"0") +"</dis>");
        sb.append("<base>"+util.nvl((String)ttls.get(chngFlg+"B"),"0") +"</base>");
        sb.append("<rapvlu>"+util.nvl((String)ttls.get(chngFlg+"R"),"0") +"</rapvlu>");
        sb.append("<cvlu>"+util.nvl((String)ttls.get(chngFlg+"CV"),"0") +"</cvlu>");
        sb.append("<cavg>"+util.nvl((String)ttls.get(chngFlg+"CA"),"0") +"</cavg>");
        sb.append("<cdis>"+util.nvl((String)ttls.get(chngFlg+"CD"),"0") +"</cdis>");
        
        sb.append("<regqty>"+util.nvl((String)ttls.get(rejflg+"Q"),"0") +"</regqty>");
        sb.append("<regcts>"+util.nvl((String)ttls.get(rejflg+"W"),"0") +"</regcts>");
        sb.append("<regvlu>"+util.nvl((String)ttls.get(rejflg+"V"),"0") +"</regvlu>");
        sb.append("<regavg>"+util.nvl((String)ttls.get(rejflg+"A"),"0") +"</regavg>");
        sb.append("<regdis>"+util.nvl((String)ttls.get(rejflg+"D"),"0") +"</regdis>");
        sb.append("<regbase>"+util.nvl((String)ttls.get(rejflg+"B"),"0") +"</regbase>");
        sb.append("<regrapvlu>"+util.nvl((String)ttls.get(rejflg+"R"),"0") +"</regrapvlu>");
        sb.append("<regcvlu>"+util.nvl((String)ttls.get(rejflg+"CV"),"0") +"</regcvlu>");
        sb.append("<regcavg>"+util.nvl((String)ttls.get(rejflg+"CA"),"0") +"</regcavg>");
        sb.append("<regcdis>"+util.nvl((String)ttls.get(rejflg+"CD"),"0") +"</regcdis>");
        sb.append("<netprc>"+util.nvl((String)ttls.get("NETPRC"),"0") +"</netprc>");
        sb.append("<netdis>"+util.nvl((String)ttls.get("NETDIS"),"0") +"</netdis>");
        sb.append("<netvlu>"+util.nvl((String)ttls.get("NETVAL"),"0") +"</netvlu>");
        sb.append("<diff>"+util.nvl((String)ttls.get("DIFF"),"0") +"</diff>");
        sb.append("<ctg>"+util.nvl((String)ttls.get("CTG"),"0") +"</ctg>");
        sb.append("<loyvlu>"+util.nvl((String)ttls.get("LOYVLU"),"0") +"</loyvlu>");
        sb.append("<memvlu>"+util.nvl((String)ttls.get("MEMVLU"),"0") +"</memvlu>");
        sb.append("<loyPct>"+util.nvl((String)ttls.get("LOYPCT"),"0") +"</loyPct>");
        sb.append("</value>");
        
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<values>"+sb.toString()+"</values>");
        
        return null;
    }
    
        public ActionForward selChBox(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String typ = util.nvl(req.getParameter("typ"));
        String gtRlt = "select stk_idn , stt from gt_srch_rslt where stt not in (select  b.chr_fr from mrule a , rule_dtl b where a.rule_idn = b.rule_idn and a.mdl='MEMO_ALW'  and b.dsc =?)";
        ArrayList ary = new ArrayList();
        ary.add(typ);
      ArrayList outLst = db.execSqlLst("gtRlt", gtRlt, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            sb.append("<pkt>");
            sb.append("<Idn>"+util.nvl(rs.getString("stk_idn"))+"</Idn>");
            sb.append("<stt>"+util.nvl(rs.getString("stt"))+"</stt>");
            sb.append("</pkt>");
        }
        rs.close();
        pst.close();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<pkts>"+sb.toString()+"</pkts>");
        return null;
    }
    
    public ActionForward memo(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String nmeId = req.getParameter("nameIdn");
        String typ = util.nvl(req.getParameter("typ"));
        String pkttyp = util.nvl(req.getParameter("pkttyp"));
        String sql = "select distinct a.idn, a.memo_typ typ, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte , b.nme byr,mj.rmk,\n" + 
        "c.term, a.qty,to_char(trunc(a.cts,2),'9999990.99') cts, to_char(trunc(a.vlu,2),'999999990.00') vlu \n" + 
        "from jan_smry_v a, nme_v b, mtrms c,jandtl d,mjan mj\n" + 
        "where a.trms_idn = c.idn and a.nme_idn = b.nme_idn and a.idn=mj.idn and \n" + 
        "a.idn=d.idn \n" + 
        "and b.nme_idn =?\n" + 
        "and a.memo_typ =? and a.pkt_ty=? order by a.idn desc";
        ArrayList ary = new ArrayList();
        ary.add(nmeId);
        ary.add(typ);
        ary.add(pkttyp);
      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            sb.append("<memo>");
            sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
            sb.append("<trm>"+util.nvl(rs.getString("term"),"0") +"</trm>");
            sb.append("<qty>"+util.nvl(rs.getString("qty"),"0") +"</qty>");
            sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
            sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
            sb.append("<dte>"+util.nvl(rs.getString("dte"),"0") +"</dte>");
            sb.append("<memormk>"+util.nvl(rs.getString("rmk"),"NA") +"</memormk>");
            sb.append("<typ>"+util.nvl(rs.getString("typ"),"0") +"</typ>");
            sb.append("</memo>");
        }
        rs.close();
        pst.close();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<memos>"+sb.toString()+"</memos>");
        return null;
    }
    
    public ActionForward memoMerge(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    StringBuffer sb = new StringBuffer();
    String typCheck = "";
    String nmeId = req.getParameter("nameIdn");
    String nmeRln = req.getParameter("nmeRln");
    String typ = util.nvl(req.getParameter("typ"));

    String type = util.nvl(req.getParameter("type"));
        String PKT_TY = util.nvl(req.getParameter("PKT_TY"));

    if(!type.equals("0"))
    typCheck = " and a.memo_typ = '"+type+"' ";
    
    if(PKT_TY.equals(""))
        typCheck =  typCheck + " and a.pkt_ty in ('NR','SMX') ";
    else
        typCheck =  typCheck + " and a.pkt_ty in ('MIX','MX') ";

    ArrayList ary = new ArrayList();
    String sql = " select a.idn, a.memo_typ typ, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte , b.nme byr, c.term, a.qty,to_char(trunc(a.cts,2),'9999990.99') cts, to_char(trunc(a.vlu,2),'999999990.00') vlu from " +
    "jan_smry_v a, nme_v b, mtrms c " +
    "where " +
    "a.trms_idn = c.idn and a.nme_idn = b.nme_idn and b.nme_idn =? and a.memo_typ =?  and a.nmerel_idn =? " +typCheck ;
    ary.add(nmeId);
    ary.add(typ);
    ary.add(nmeRln);

      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<memo>");
    sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
    sb.append("<term>"+util.nvl(rs.getString("term"),"0") +"</term>");
    sb.append("<typ>"+util.nvl(rs.getString("typ"),"0") +"</typ>");
    sb.append("<qty>"+util.nvl(rs.getString("qty"),"0") +"</qty>");
    sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
    sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
    sb.append("<dte>"+util.nvl(rs.getString("dte"),"0") +"</dte>");
    sb.append("</memo>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>"+sb.toString()+"</memos>");
    return null;
    }

    
   
    
  public ActionForward memoIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr(); 
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
          StringBuffer sb = new StringBuffer();
          String nmeId = req.getParameter("nameIdn");
          String nmeRleId = req.getParameter("nmeRln");
         
        String typ = util.nvl(req.getParameter("typ"));
        String str =" and a.typ like 'BAP' and b.stt in ('AP','WFR')   ";
        if(typ.equals("SALE"))
         str = " and a.typ like '%AP' and a.typ <> 'BAP' and b.stt ='AP'  ";
        if(typ.equals("BB"))
          str =" and a.typ in ('BB','WB') and b.stt in ('IS','PR','WFP') ";
         ArrayList ary = new ArrayList();
         String sql =  "select a.idn, to_char(a.dte, 'dd-Mon HH24:mi') dte , sum(b.qty) qty, sum(b.cts) cts, trunc(sum(b.cts*nvl(b.fnl_sal, b.quot)), 2) vlu , " + 
         "decode(a.typ,'OAP','PBB Approve','IAP','Internal Approve','EAP','External Approve','WA','Web Approve','WAP','Web Approve','BAP','Buy Back(Offline)' ,a.typ) typ from mjan a , jandtl b " + 
         "where a.stt = 'IS'  "+str+"   " + 
         "and a.idn = b.idn " + 
         "and a.nme_idn=? and a.nmerel_idn=? and nvl(a.qty,0) > 0 group by a.idn, to_char(a.dte, 'dd-Mon HH24:mi'), b.qty, decode(a.typ,'OAP','PBB Approve','IAP','Internal Approve','EAP','External Approve','WA','Web Approve','WAP','Web Approve','BAP','Buy Back(Offline)' , a.typ) order by a.idn desc ";
          ary.add(nmeId);
          ary.add(nmeRleId);
        ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
          while(rs.next()){
              sb.append("<memo>");
              sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
              sb.append("<dte>"+util.nvl(rs.getString("dte"),"") +"</dte>");
              sb.append("<pcs>"+util.nvl(rs.getString("qty"),"0") +"</pcs>");
              sb.append("<cts>"+util.nvl(rs.getString("cts"),"") +"</cts>");
              sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
              sb.append("<typ>"+util.nvl(rs.getString("typ"),"") +"</typ>");
              sb.append("</memo>");
          }
          rs.close();
          pst.close();
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          res.getWriter().write("<memos>"+sb.toString()+"</memos>");
          return null;
      }
    
    public ActionForward consignmentIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String nmeId = req.getParameter("nameIdn");
        String nmeRleId = req.getParameter("nmeRln");
       
        
        
        ArrayList ary = new ArrayList();
        String sql =  "Select Distinct A.idn , to_char(A.dte, 'dd-Mon HH24:mi') dte From Mjan A , Jandtl B , Mstk C Where A.Idn = B.Idn And B.Mstk_Idn = C.Idn \n" + 
        "And b.stt in('IS','AV') And Nme_Idn=? and a.typ like '%CS'\n" + 
        "And Nmerel_Idn=? Order By Idn";
            
        ary.add(nmeId);
        ary.add(nmeRleId);
      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            sb.append("<memo>");
            sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
            sb.append("<dte>"+util.nvl(rs.getString("dte"),"") +"</dte>");
            sb.append("</memo>");
        }
        rs.close();
        pst.close();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<memos>"+sb.toString()+"</memos>");
        return null;
    }
    
    
    public ActionForward saleIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
         String cnt = (String)dbinfo.get("CNT");
         StringBuffer sb = new StringBuffer();
         String nmeId = util.nvl(req.getParameter("nameIdn"));
         String typ = util.nvl(req.getParameter("typ"));
         String rlnId = util.nvl(req.getParameter("rlnId"));
         String pktTy= util.nvl(req.getParameter("pktTy"));
         String strTyp="";
         if(!pktTy.equals(""))
             strTyp=" and c.pkt_ty in ('"+pktTy+"')";
        
             
         String restrict_sale_rtn_days = util.nvl((String)info.getDmbsInfoLst().get("RESTRICT_SALE_RTN_DAYS"),"30");
         ArrayList ary = new ArrayList();
         ary.add(nmeId);
         String  sql = "select distinct a.idn , to_char(a.dte, 'dd-Mon HH24:mi') dte ,a.qty,  sum(b.cts) cts, trunc(sum(b.cts*nvl(b.fnl_sal, b.quot)), 2) vlu  from msal a , jansal b , mstk c "+
            " where a.stt ='IS' and a.typ in ('"+typ+"') "+
            " and a.idn = b.idn and b.mstk_idn=c.idn "+strTyp+" and b.stt = 'SL' and a.flg1='CNF' "+
            " and nvl(a.inv_nme_idn, a.nme_idn) = ? ";
         if(typ.equals("LS")){
             sql = "select distinct a.idn , to_char(a.dte, 'dd-Mon HH24:mi') dte ,a.qty,  sum(b.cts) cts, trunc(sum(b.cts*nvl(b.fnl_sal, b.quot)), 2) vlu  from msal a , jansal b , mstk c "+
                        " where a.typ in ('"+typ+"') "+
                        " and a.idn = b.idn and b.stt in( 'SL','ADJ') and a.flg1='CNF' and b.mstk_idn=c.idn "+strTyp+
                        " and nvl(a.inv_nme_idn, a.nme_idn) = ? "; 
             sql+="  and trunc(a.dte)>=trunc(sysdate)-"+restrict_sale_rtn_days+"\n";
         }
            if(!rlnId.equals("")){
             if(typ.equals("SL"))
             sql = sql+" and nvl(fnl_trms_idn,nmerel_idn) = ?";
             else
             sql = sql+" and nmerel_idn = ? ";
             ary.add(rlnId);
         }
            sql = sql+ " group by a.idn, to_char(a.dte, 'dd-Mon HH24:mi') , a.qty order by a.idn \n ";

       ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
         while(rs.next()){
             sb.append("<memo>");
             sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
             sb.append("<dte>"+util.nvl(rs.getString("dte"),"") +"</dte>");
           sb.append("<pcs>"+util.nvl(rs.getString("qty"),"0") +"</pcs>");
           sb.append("<cts>"+util.nvl(rs.getString("cts"),"") +"</cts>");
           sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
             sb.append("</memo>");
         }
         rs.close();
         pst.close();
         res.setContentType("text/xml"); 
         res.setHeader("Cache-Control", "no-cache"); 
         res.getWriter().write("<memos>"+sb.toString()+"</memos>");
         return null;
     }
    
    public ActionForward Addr(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String nmeId = util.nvl(req.getParameter("nameIdn"));
      
       
       
        
        ArrayList ary = new ArrayList();
        String sql =  "select a.addr_idn , unt_num, bldg ||''|| street ||''|| landmark ||''|| area ||''|| b.city_nm||''|| c.country_nm addr "+
                      " from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn  and a.nme_idn = ? order by a.dflt_yn desc ";
    
        ary.add(nmeId);
      
       ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            sb.append("<addr>");
            sb.append("<id>"+util.nvl(rs.getString("addr_idn"),"0") +"</id>");
            sb.append("<add>"+util.nvl(rs.getString("addr"),"0").replaceAll("&", "&amp;") +"</add>");
            sb.append("</addr>");
        }
        rs.close();
        pst.close();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<addrs>"+sb.toString()+"</addrs>");
        return null;
    }
    
    public ActionForward Banknme(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String nmeId = util.nvl(req.getParameter("nameIdn"));
        boolean vld=true;
       
        ArrayList ary = new ArrayList();
        String sql =  "select b.nme_dtl_idn , b.txt from mnme a , nme_dtl b " +
"where a.nme_idn = b.nme_idn and b.mprp like 'THRU_BANK%' and a.nme_idn=? and b.vld_dte is null";


    
        ary.add(nmeId);
      
      ArrayList outLst = db.execSqlLst("BANK NAME", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            sb.append("<bank>");
            sb.append("<id>"+util.nvl(rs.getString("nme_dtl_idn"),"0") +"</id>");
            sb.append("<nme>"+util.nvl(rs.getString("txt"),"0").replaceAll("\\#"," ") +"</nme>");
            sb.append("</bank>");
            vld=false;
        }
        
        if(vld){
            sb.append("<bank>");
            sb.append("<id>0</id>");
            sb.append("<nme>NA</nme>");
            sb.append("</bank>");
        }
        rs.close();
        pst.close();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<banks>"+sb.toString()+"</banks>");
        return null;
    }
    
    
    public ActionForward setDfltGrpBank(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String nmeId = util.nvl(req.getParameter("nameIdn"));
      
       
        ArrayList ary = new ArrayList();
        String sql =  "select 'A' orderby,to_number(b.txt) bnkidn\n" + 
        "from nme_dtl b , mnme c\n" + 
        "where 1 = 1 \n" + 
        "and b.nme_idn = c.nme_idn  \n" + 
        "and b.mprp='GRP_BANK_DFLT' and b.txt is not null and c.nme_idn=?\n" + 
        "and b.vld_dte is null\n" + 
        "union\n" + 
        "select 'B',c.nme_idn bnkidn\n" + 
        "from nme_dtl b , mnme c\n" + 
        "where 1 = 1 \n" + 
        "and b.nme_idn = c.nme_idn  and c.typ in('GROUP','BANK') \n" + 
        "and b.mprp='PERFORMABANK' and b.txt='Y'\n" + 
        "and b.vld_dte is null\n" + 
        "order by 1";


    
        ary.add(nmeId);
      
      ArrayList outLst = db.execSqlLst("BANK NAME", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        if(rs.next()){
            sb.append("<bank>");
            sb.append("<id>"+util.nvl(rs.getString("bnkidn"),"0") +"</id>");
            sb.append("<nme>Y</nme>");
            sb.append("</bank>");
        }else{
            sb.append("<bank>");
            sb.append("<id>0</id>");
            sb.append("<nme>N</nme>");
            sb.append("</bank>"); 
        }
        rs.close();
        pst.close();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<dfltbanks>"+sb.toString()+"</dfltbanks>");
        return null;
    }
    public ActionForward cts(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String memoId = req.getParameter("memoIdn");
        ArrayList ary = new ArrayList();
        String sqlVal = "Select count(*) qty, sum(a.cts) cts , decode(c.pkt_ty,'SMX', trunc((sum(a.cts*nvl(a.fnl_sal, a.quot)) / sum(a.cts))*trunc(sum(a.cts),2),2) , trunc(sum(a.cts*nvl(a.fnl_sal, a.quot)), 2))  vlu , b.nmerel_idn , b.vw_mdl ,  b.typ,form_url_encode(byr.get_nm(b.nme_idn)) byr " +
                "from jandtl a , mjan b ,mstk c where a.idn = b.idn and a.mstk_idn=c.idn and a.idn= ? GROUP BY b.nmerel_idn ,  b.vw_mdl ,  b.typ,byr.get_nm(b.nme_idn),c.pkt_ty ";

         ary = new ArrayList();
         ary.add(memoId);
      ArrayList outLst = db.execSqlLst("sqlVlu", sqlVal, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
          if(rs.next()){
            sb.append("<memo>");
            sb.append("<qty>"+util.nvl(rs.getString("qty"),"0") +"</qty>");
            sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
            sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
            sb.append("<rln>"+util.nvl(rs.getString("nmerel_idn"),"0") +"</rln>");
            sb.append("<typ>"+util.nvl(rs.getString("typ"),"0") +"</typ>");
            sb.append("<mdl>"+util.nvl(rs.getString("vw_mdl"),"0").trim().replaceAll("&", "&amp;") +"</mdl>");
            sb.append("<byr>"+util.nvl(rs.getString("byr"),"") +"</byr>"); 
            sb.append("</memo>");
            }else{
              
                String isValid ="select * from mjan where idn=? ";
                rs = db.execSql("sqlVlu", isValid, ary);
              if(rs.next()){
                sb.append("<memo>");
                sb.append("<qty>ALLRT</qty>");
                sb.append("</memo>");
              }else{
                  sb.append("<memo>");
                  sb.append("<qty>invalid</qty>");
                  sb.append("</memo>");
              }
            }
        rs.close();
        pst.close();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<memos>"+sb.toString()+"</memos>");
        return null;
    }
    
    public ActionForward memoAllCurcts(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
           HttpSession session = req.getSession(false);
           InfoMgr info = (InfoMgr)session.getAttribute("info");
           DBUtil util = new DBUtil();
           DBMgr db = new DBMgr(); 
           db.setCon(info.getCon());
           util.setDb(db);
           util.setInfo(info);
           db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
           util.setLogApplNm(info.getLoApplNm());
           StringBuffer sb = new StringBuffer();
           String memoId = req.getParameter("memoIdn");
           String memoTyp = req.getParameter("memoTyp");
           String buttonTyp = req.getParameter("buttonTyp");
           String sqlQ = "";
           
           if(buttonTyp.equals("CRT") && memoTyp.indexOf("AP")>-1) {
                  sqlQ = " and a.typ like '%AP' and a.stt = 'AP' ";
           
           } else if(buttonTyp.equals("CRT")){
                  sqlQ = " and a.stt = 'IS' ";
           } 
           
           String sqlVal = "Select count(*) qty, sum(trunc(a.cts,2)) cts , to_char(sum(trunc(a.cts,2)*a.quot)) vlu,b.idn " +
                   "from jandtl a , mjan b where a.idn = b.idn  "+sqlQ+" and a.idn= ? GROUP BY b.idn ";
                   
           ArrayList ary = new ArrayList();
           

            ary = new ArrayList();
            ary.add(memoId);
         ArrayList outLst = db.execSqlLst("sqlVlu", sqlVal, ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
             if(rs.next()){
               sb.append("<memo>");
               sb.append("<qty>"+util.nvl(rs.getString("qty"),"0") +"</qty>");
               sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
               sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
               sb.append("</memo>");
             } else {
                 sb.append("<memo>");
                 sb.append("<qty>0</qty>");
                 sb.append("<cts>0</cts>");
                 sb.append("<vlu>0</vlu>");
                 sb.append("</memo>");
               }
           rs.close();
           pst.close();
           res.setContentType("text/xml"); 
           res.setHeader("Cache-Control", "no-cache"); 
           res.getWriter().write("<memos>"+sb.toString()+"</memos>");
           return null;
       }
    public ActionForward SaleTotal(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String stkIdn = req.getParameter("stkIdn");
        String typ = req.getParameter("typ");
        String memoIdn = req.getParameter("memoId");
        ArrayList slPktList = info.getSlPktList();
        if(typ.equals("SL")){
            slPktList.add(stkIdn);
        }else{
          if(slPktList.indexOf(stkIdn)>-1)
            slPktList.remove(slPktList.indexOf(stkIdn));
            
        }
        if(slPktList.size()>0){
            String stkIdns = slPktList.toString();
            stkIdns = stkIdns.replace('[','(');
            stkIdns =stkIdns.replace(']',')');
           
        String sqlVal = "select sum(a.qty) pcs, trunc(sum(trunc(a.cts,2)),2) cts "+
           " , trunc(((sum(nvl(a.fnl_sal, a.quot)*trunc(a.cts,2))*100/sum(nvl(b.rap_rte, 0)*trunc(a.cts,2))) - 100) ,2) avg_dis "+
           " , trunc(sum(nvl(a.fnl_sal,a.quot)*trunc(a.cts,2)),2) vlu "+
           " from jandtl a, mstk b  where a.mstk_idn = b.idn and a.mstk_idn in "+stkIdns+" and a.idn = ?";
        ArrayList ary = new ArrayList();
        ary.add(memoIdn);
          ArrayList outLst = db.execSqlLst("sqlVlu", sqlVal, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            sb.append("<memo>");
            sb.append("<qty>"+util.nvl(rs.getString("pcs"),"0") +"</qty>");
            sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
            sb.append("<dis>"+util.nvl(rs.getString("avg_dis"),"0") +"</dis>");
            sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
            sb.append("</memo>");
        }
          rs.close();
          pst.close();
        }else{
            sb.append("<memo>");
            sb.append("<qty>0</qty>");
            sb.append("<cts>0</cts>");
            sb.append("<dis>0</dis>");
            sb.append("<vlu>0</vlu>");
            sb.append("</memo>");
        }
        
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<memos>"+sb.toString()+"</memos>");
        return null;
    }
    
    
    public ActionForward DlvTotal(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String stkIdn = req.getParameter("stkIdn");
        String typ = req.getParameter("typ");
       String isMix = util.nvl(req.getParameter("isMix"),"N");
        ArrayList dlvPktList = info.getDlvPktList();
        if(typ.equals("DLV") && !dlvPktList.contains(stkIdn)){
        
            dlvPktList.add(stkIdn); 
        }else{
          if(dlvPktList.indexOf(stkIdn)>-1)
            dlvPktList.remove(dlvPktList.indexOf(stkIdn));
            
        }
        if(dlvPktList.size()>0 || typ.equals("ALL")){
            String stkIdns = dlvPktList.toString();
            stkIdns = stkIdns.replace('[','(');
            stkIdns =stkIdns.replace(']',')');
            if(typ.equals("ALL")){
                dlvPktList=new ArrayList();
                stkIdns = stkIdn.replaceFirst(",", "");
                String[] stkIdnsArr=stkIdns.split(",");
                stkIdns = "("+stkIdns+")";
                if(stkIdnsArr.length>=1){
                for (int i = 0; i < stkIdnsArr.length; i++){
                dlvPktList.add(stkIdnsArr[i]);
                }
                }
            }
           
        String sqlVal = "select sum(a.qty) pcs, trunc(sum(trunc(a.cts,2)),2) cts "+
           " , trunc(((sum(nvl(a.fnl_sal, a.quot)*trunc(a.cts,2))*100/sum(nvl(b.rap_rte, 0)*trunc(a.cts,2))) - 100) ,2) avg_dis "+
           " , decode(b.pkt_ty,'SMX', trunc((sum(a.cts*nvl(a.fnl_sal, a.quot)) / sum(a.cts))*trunc(sum(a.cts),2),2) , trunc(sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)), 2))  vlu "+
           " from jansal a, mstk b  where a.mstk_idn = b.idn and  a.mstk_idn in "+stkIdns+" and  a.stt='SL' GROUP BY b.pkt_ty ";
        if(isMix.equals("Y")){
            String saleIdn =  util.nvl(req.getParameter("saleIdn"));
          sqlVal = "select sum(a.qty) pcs, trunc(sum(trunc(a.cts,2)),2) cts "+
                     " , 0 avg_dis "+
                     " , trunc(sum(nvl(a.fnl_sal,a.quot)*trunc(a.cts,2)),2) vlu "+
                     " from jansal a, mstk b  where a.mstk_idn = b.idn and  a.mstk_idn in "+stkIdns+" and a.idn in ("+saleIdn+") and a.stt='SL'";
                
        }
       
        ArrayList ary = new ArrayList();
       
          ArrayList outLst = db.execSqlLst("sqlVlu", sqlVal, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            sb.append("<memo>");
            sb.append("<qty>"+util.nvl(rs.getString("pcs"),"0") +"</qty>");
            sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
            sb.append("<dis>"+util.nvl(rs.getString("avg_dis"),"0") +"</dis>");
            sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
            sb.append("</memo>");
        }
            rs.close();
            pst.close();
        }else{
            sb.append("<memo>");
            sb.append("<qty>0</qty>");
            sb.append("<cts>0</cts>");
            sb.append("<dis>0</dis>");
            sb.append("<vlu>0</vlu>");
            sb.append("</memo>");
        }
        
        if(dlvPktList==null)
        dlvPktList=new ArrayList();
        info.setDlvPktList(dlvPktList);
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<memos>"+sb.toString()+"</memos>");
        return null;
    }
    
  public ActionForward BuyTotal(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      StringBuffer sb = new StringBuffer();
      String stkIdn = util.nvl(req.getParameter("stkIdn"));
      String typ = util.nvl(req.getParameter("typ"));
      String memoId = util.nvl(req.getParameter("memoId"));
      stkIdn = "("+stkIdn.substring(1, stkIdn.length())+")";
         
      String sqlVal = "select sum(a.qty) pcs, trunc(sum(trunc(a.cts,2)),2) cts "+
         " , trunc(((sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts,2))))) avg_Rte , trunc(((sum(nvl(a.fnl_sal, a.quot)*trunc(a.cts,2))*100/sum(nvl(b.rap_rte, 0)*trunc(a.cts,2))) - 100) ,2) avg_dis "+
         " , trunc(sum(nvl(a.fnl_sal,a.quot)*trunc(a.cts,2)),2) vlu "+
         " from jandtl a, mstk b , mjan c where a.mstk_idn = b.idn and a.idn = c.idn and c.stt='IS' and a.mstk_idn in "+stkIdn+" and  a.idn in ("+memoId+")";
      ArrayList ary = new ArrayList();
     
    ArrayList outLst = db.execSqlLst("sqlVlu", sqlVal, ary);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
       boolean isVal = false;
      while(rs.next()){
          sb.append("<memo>");
            sb.append("<typ>"+typ+"</typ>");
          sb.append("<qty>"+util.nvl(rs.getString("pcs"),"0") +"</qty>");
          sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
          sb.append("<dis>"+util.nvl(rs.getString("avg_dis"),"0") +"</dis>");
          sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
            sb.append("<prc>"+util.nvl(rs.getString("avg_Rte"),"0") +"</prc>");
          sb.append("</memo>");
          isVal=true;
          }
    rs.close();
    pst.close();
      if(!isVal){
        sb.append("<memo>");
        sb.append("<typ>"+typ+"</typ>");
        sb.append("<qty>0</qty>");
        sb.append("<cts>0</cts>");
        sb.append("<dis>0</dis>");
        sb.append("<vlu>0</vlu>");
        sb.append("</memo>");
        
      }
          
      
      
   
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      res.getWriter().write("<memos>"+sb.toString()+"</memos>");
      return null;
  }
  
  
    public ActionForward rebateCalculation(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String stkIdn = util.nvl(req.getParameter("stkIdn"));
        String typ = util.nvl(req.getParameter("typ"));
        String memoId = util.nvl(req.getParameter("memoId"));
        String vlu = util.nvl(req.getParameter("vlu"));
        float calDis=0;
        stkIdn=stkIdn.replaceFirst("\\,", "");
        String[] stkIdnsArr=stkIdn.split(",");
        String[] memoIdsArr=memoId.split(",");
        for (int i = 0; i < stkIdnsArr.length; i++){
        String mstkidn=stkIdnsArr[i];
        String memoidn=memoIdsArr[i];
        if(!mstkidn.equals("") && !memoidn.equals("")){
        String getDis = "select MEMO_PKG.BB_REBATE(?,?) dis from dual ";
        ArrayList params = new ArrayList();
        params.add(memoidn);
        params.add(mstkidn);
          ArrayList outLst = db.execSqlLst("getDis", getDis, params);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        if(rs.next()){
        String dis=util.nvl(rs.getString("dis"),"0");
        calDis=calDis+Float.parseFloat(dis);
        }
        rs.close();
        pst.close();
        }
        }
        
        sb.append("<charge>");
        sb.append("<chargedis>"+calDis+"</chargedis>");
        sb.append("</charge>");
     
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<charges>"+sb.toString()+"</charges>");
        return null;
    }
    public ActionForward DlvTotalXL(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String stkIdn = util.nvl(req.getParameter("stkIdn"));
        String typ = util.nvl(req.getParameter("typ"));
        stkIdn = "("+stkIdn.substring(1, stkIdn.length())+")";
           
        String sqlVal = "select sum(a.qty) pcs, trunc(sum(trunc(a.cts,2)),2) cts \n" + 
        "            , trunc(((sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts,2))))) avg_Rte , \n" + 
        "            trunc(((sum(nvl(a.fnl_sal, a.quot)*trunc(a.cts,2))*100/sum(nvl(b.rap_rte, 0)*trunc(a.cts,2))) - 100) ,2) avg_dis \n" + 
        "            , trunc(sum(nvl(a.fnl_sal,a.quot)*trunc(a.cts,2)),2) vlu \n" + 
        "            from jansal a, mstk b , msal c where a.mstk_idn = b.idn \n" + 
        "            and a.idn = c.idn and c.stt='IS' and a.stt in ('SL','PR') and c.typ in ('SL') and a.mstk_idn in "+stkIdn;
        ArrayList ary = new ArrayList();
       
      ArrayList outLst = db.execSqlLst("sqlVlu", sqlVal, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
         boolean isVal = false;
        while(rs.next()){
            sb.append("<memo>");
              sb.append("<typ>"+typ+"</typ>");
            sb.append("<qty>"+util.nvl(rs.getString("pcs"),"0") +"</qty>");
            sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
            sb.append("<dis>"+util.nvl(rs.getString("avg_dis"),"0") +"</dis>");
            sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
              sb.append("<prc>"+util.nvl(rs.getString("avg_Rte"),"0") +"</prc>");
            sb.append("</memo>");
            isVal=true;
            }
      rs.close();
      pst.close();
      
        if(!isVal){
          sb.append("<memo>");
          sb.append("<typ>"+typ+"</typ>");
          sb.append("<qty>0</qty>");
          sb.append("<cts>0</cts>");
          sb.append("<dis>0</dis>");
          sb.append("<vlu>0</vlu>");
          sb.append("</memo>");
          
        }
            
        
     
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<memos>"+sb.toString()+"</memos>");
        return null;
    }
    public ActionForward memoPrice(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        
        String memoIdn = req.getParameter("memoIdn");
        
           
        String sqlVal = "select sum(a.qty) pcs, trunc(sum(trunc(a.cts,2)),2) cts "+
           " , trunc(((sum(nvl(a.fnl_sal, a.quot)*trunc(a.cts,2))*100/sum(nvl(b.rap_rte, 0)*trunc(a.cts,2))) - 100) ,2) avg_dis "+
          " , trunc(sum(nvl(a.fnl_sal,a.quot)*trunc(a.cts,2)),2) vlu ,  trunc(sum(nvl(a.fnl_sal,a.quot)*trunc(a.cts,2))/sum(trunc(a.cts,2)),2) avg "+
          " from jandtl a, mstk b  where a.mstk_idn = b.idn and  a.idn = ? ";
                
       
        ArrayList ary = new ArrayList();
         ary.add(memoIdn);
      ArrayList outLst = db.execSqlLst("sqlVlu", sqlVal, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
         while(rs.next()){
            sb.append("<memo>");
            sb.append("<qty>"+util.nvl(rs.getString("pcs"),"0") +"</qty>");
            sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
            sb.append("<avgdis>"+util.nvl(rs.getString("avg_dis"),"0") +"</avgdis>");
            sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
            sb.append("<avgvlu>"+util.nvl(rs.getString("avg"),"0") +"</avgvlu>");
            sb.append("</memo>");
         }
        rs.close();
        pst.close();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<memos>"+sb.toString()+"</memos>");
        return null;
    }
    
    public ActionForward FinalByr(ActionMapping mp , ActionForm fm , HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String byrId = req.getParameter("bryIdn");
        String typ = util.nvl(req.getParameter("typ"),"SL");
        String finalByr = "select  distinct form_url_encode(fnl_byr) fnl_byr, fnl_nme_idn  from sal_pndg_v where nme_idn=? and typ = ? order by fnl_byr";
        ArrayList ary = new ArrayList();
        ary.add(byrId);
        ary.add(typ);

        try {
            
          ArrayList outLst = db.execSqlLst("sqlVlu", finalByr, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String nme =
                    util.nvl(rs.getString(1), "0").replaceAll("&", "&amp;");


                sb.append("<nmes>");
                sb.append("<nmeid>" + util.nvl(rs.getString(2)) + "</nmeid>");
                sb.append("<nme>" + nme + "</nme>");
                sb.append("</nmes>");

            }

            rs.close();
            pst.close();

            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
        
        return null;
    }
    
    
    public ActionForward FinalByrBrcDlv(ActionMapping mp , ActionForm fm , HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String byrId = req.getParameter("bryIdn");
        String finalByr = "select distinct form_url_encode(a.nme),a.nme_idn from nme_v a , brc_mdlv b , brc_dlv_dtl c\n" + 
        "where a.nme_idn = b.nme_idn  and b.idn = c.idn and b.inv_nme_idn=? and c.stt not in ('DLV','AV','RT','CL','IS') order by 1";
        ArrayList ary = new ArrayList();
        ary.add(byrId);

        try {
            
          ArrayList outLst = db.execSqlLst("sqlVlu", finalByr, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String nme =
                    util.nvl(rs.getString(1), "0").replaceAll("&", "&amp;");


                sb.append("<nmes>");
                sb.append("<nmeid>" + util.nvl(rs.getString(2)) + "</nmeid>");
                sb.append("<nme>" + nme + "</nme>");
                sb.append("</nmes>");

            }

            rs.close();
            pst.close();

            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
        
        return null;
    }
    public ActionForward FinalByrNR(ActionMapping mp , ActionForm fm , HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String byrId = req.getParameter("bryIdn");
        String typ = util.nvl(req.getParameter("typ"),"SL");
        String finalByr = "select  distinct form_url_encode(fnl_byr) fnl_byr, fnl_nme_idn  from sal_pndg_v where nme_idn=? and typ = ?  and pkt_ty in ('NR','SMX') order by fnl_byr";
        if(typ.equals("LS")){
            finalByr = "select  distinct form_url_encode(fnl_byr) fnl_byr, fnl_nme_idn  from sal_pndg_v where nme_idn=? and typ = ? order by fnl_byr";    
        }
        ArrayList ary = new ArrayList();
        ary.add(byrId);
        ary.add(typ);

        try {
            
          ArrayList outLst = db.execSqlLst("sqlVlu", finalByr, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String nme =
                    util.nvl(rs.getString(1), "0").replaceAll("&", "&amp;");


                sb.append("<nmes>");
                sb.append("<nmeid>" + util.nvl(rs.getString(2)) + "</nmeid>");
                sb.append("<nme>" + nme + "</nme>");
                sb.append("</nmes>");

            }

            rs.close();
           pst.close();
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
        
        return null;
    }
    public ActionForward ByrFromBill(ActionMapping mp , ActionForm fm , HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String byrId = req.getParameter("bryIdn");
        String typ = util.nvl(req.getParameter("typ"),"SL");
        String PKTTYP = util.nvl(req.getParameter("PKTTYP"),"NR");
        String finalByr = "select distinct form_url_encode(a.nme) byr,a.nme_idn\n" + 
        "from nme_v a ,nme_grp b,nme_grp_dtl c, sal_pndg_v d\n" + 
        "where a.nme_idn = b.nme_idn and b.nme_grp_idn = c.nme_grp_idn and c.nme_idn=?\n" + 
        "and a.nme_idn=d.nme_idn and d.typ=? and d.pkt_ty = ?  and c.flg is null\n" + 
        " and b.typ = 'BUYER' and c.flg is null  order by 1";
        ArrayList ary = new ArrayList();
        ary.add(byrId);
        ary.add(typ);
        ary.add(PKTTYP);
        try {
            
          ArrayList outLst = db.execSqlLst("sqlVlu", finalByr, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String nme =
                    util.nvl(rs.getString(1), "0").replaceAll("&", "&amp;");


                sb.append("<nmes>");
                sb.append("<nmeid>" + util.nvl(rs.getString(2)) + "</nmeid>");
                sb.append("<nme>" + nme + "</nme>");
                sb.append("</nmes>");

            }

            rs.close();
           pst.close();
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
        
        return null;
    }
    
    public ActionForward SaleByrFromBill(ActionMapping mp , ActionForm fm , HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String byrId = req.getParameter("bryIdn");
        String finalByr = "select distinct form_url_encode(a.nme) byr,a.nme_idn \n" + 
        "            from nme_v a ,nme_grp b,nme_grp_dtl c, mjan d \n" + 
        "            where a.nme_idn = b.nme_idn and b.nme_grp_idn = c.nme_grp_idn and c.nme_idn=?\n" + 
        "            and a.nme_idn=d.nme_idn and d.stt='IS' and d.typ in('EAP','OAP', 'IAP', 'WAP', 'LAP','MAP','SAP','HAP','BAP','KAP','BIDAP') and c.flg is null\n" + 
        "            and b.typ = 'BUYER' and c.flg is null  order by 1";
        ArrayList ary = new ArrayList();
        ary.add(byrId);

        try {
            
          ArrayList outLst = db.execSqlLst("sqlVlu", finalByr, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String nme =
                    util.nvl(rs.getString(1), "0").replaceAll("&", "&amp;");


                sb.append("<nmes>");
                sb.append("<nmeid>" + util.nvl(rs.getString(2)) + "</nmeid>");
                sb.append("<nme>" + nme + "</nme>");
                sb.append("</nmes>");

            }

            rs.close();
            pst.close();
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
        
        return null;
    }
    public ActionForward FinalConsignmentByr(ActionMapping mp , ActionForm fm , HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String byrId = req.getParameter("bryIdn");
    
        String finalByr = "select  distinct form_url_encode(fnl_byr) fnl_byr, fnl_nme_idn  from cs_pndg_v where nme_idn=? order by fnl_byr";
        ArrayList ary = new ArrayList();
        ary.add(byrId);

        
        try {
            
          ArrayList outLst = db.execSqlLst("sqlVlu", finalByr, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String nme =
                    util.nvl(rs.getString(1), "0").replaceAll("&", "&amp;");


                sb.append("<nmes>");
                sb.append("<nmeid>" + util.nvl(rs.getString(2)) + "</nmeid>");
                sb.append("<nme>" + nme + "</nme>");
                sb.append("</nmes>");

            }
            
            rs.close();
            pst.close();
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
        
        return null;
    }
    
    public ActionForward ConsignmentIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String nmeId = req.getParameter("nameIdn");
        String typ = req.getParameter("typ");
        String rlnId = util.nvl(req.getParameter("rlnId"));
        ArrayList ary = new ArrayList();
        ary.add(nmeId);
        String  sql = " select distinct a.idn , to_char(a.dte, 'dd-Mon HH24:mi') dte from msal a , jansal b "+
           " where b.stt in('IS','AV') and a.typ in ('"+typ+"') "+
           " and a.idn = b.idn and b.stt = 'CS' "+
           " and nvl(a.inv_nme_idn, a.nme_idn) = ? ";
        if(!rlnId.equals("")){
            sql = sql+" and nmerel_idn = ? ";
            ary.add(rlnId);
        }
           sql = sql+ " order by a.idn ";

      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            sb.append("<memo>");
            sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
            sb.append("<dte>"+util.nvl(rs.getString("dte"),"") +"</dte>");
            sb.append("</memo>");
        }
        rs.close();
        pst.close();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<memos>"+sb.toString()+"</memos>");
        return null;
    }
    
    public ActionForward loadProp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
          HttpSession session = req.getSession(false);
                  InfoMgr info = (InfoMgr)session.getAttribute("info");
                  DBUtil util = new DBUtil();
                  DBMgr db = new DBMgr(); 
                  db.setCon(info.getCon());
                  util.setDb(db);
                  util.setInfo(info);
                  db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
                  util.setLogApplNm(info.getLoApplNm());
              ResultSet rs = null;
              res.setContentType("text/xml");
              res.setHeader("Cache-Control", "no-cache");
              StringBuffer sb = new StringBuffer();
              HashMap mprp = info.getMprp();
              HashMap prp = info.getPrp();
              String lprp = util.nvl(req.getParameter("prp"));
              String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
              if(prpTyp.equals("C")){
              ArrayList prpValLst =(ArrayList)prp.get(lprp+"V");
              sb.append("<prp>");
              sb.append("<prpVal>C</prpVal>");
              sb.append("<prpValD>C</prpValD>");
              sb.append("</prp>");
              for(int p=0;p<prpValLst.size();p++){
              String prpVal = (String)prpValLst.get(p);
              sb.append("<prp>");
              sb.append("<prpVal>"+util.nvl(prpVal) +"</prpVal>");
              sb.append("<prpValD>"+util.nvl(prpVal) +"</prpValD>");
              sb.append("</prp>");
              }}else{
              sb.append("<prp>");
              sb.append("<prpVal>NT</prpVal>");
              sb.append("<prpValD>NT</prpValD>");
              sb.append("</prp>");
              }
              res.getWriter().write("<prps>" +sb.toString()+ "</prps>");
              return null;
      }
      
      /* This will check description and type ,if its already existing in the table */
      public ActionForward descType(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr(); 
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
       
        StringBuffer sb = new StringBuffer();
        int cnt=0;
        String type=req.getParameter("type");
        String dmddsc=req.getParameter("desc");
        ArrayList ary=new ArrayList();
        String msg="";
        ary.add(type);
        ary.add(dmddsc);
        String cmpQry=" select count(*) cnt from stk_crt where  upper(typ) = upper(?) and upper(dsc)=upper(?) ";
        ArrayList outLst = db.execSqlLst("cmpQry", cmpQry, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
           cnt=rs.getInt("cnt");
        }
        if(cnt==0) {
         msg="valid";
        }
        else {
          msg="not valid"; 
        }
        sb.append("<msg>");
        sb.append("<msgDesc>"+msg+"</msgDesc>");
        sb.append("</msg>");
        rs.close();
        pst.close();
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<msgTag>" +sb.toString()+ "</msgTag>");
        return null;
      }
      public ActionForward getType(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr(); 
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String crtId=(String)req.getParameter("crtId");
          HashMap mprpLst = info.getMprp();
        String getVal="select b.chr_fr typkey, b.dsc typdsc,a.dsc descr,a.mprp,a.val,a.num,a.nme_idn , byr.get_nm(a.nme_idn) nme from stk_crt a,rule_dtl b where a.crt_idn=? and a.stt='A' and trim(a.typ)=trim(b.chr_fr) and a.vld_dte is null ";
        ArrayList ary=new ArrayList();
        String typKey="";
        String typVal="";
        String descr="";
        String mprp="";
        String val="";
        String nmeId="";
        String nme ="";
        String prpTyp="";
        ary.add(crtId);
        ArrayList outLst = db.execSqlLst(" ExistingCriteria ",getVal,ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try
        {
        while(rs.next()) {
            typKey=rs.getString("typKey");
            typVal=rs.getString("typdsc");
            descr=rs.getString("descr");
            mprp=rs.getString("mprp");
            prpTyp = util.nvl((String)mprpLst.get(mprp+"T"));
            if(prpTyp.equals("C"))
            val=rs.getString("val");
            if(prpTyp.equals("N"))
            val=rs.getString("num");
            nmeId=rs.getString("nme_idn");
            nme=rs.getString("nme");
           }
        }catch(Exception e) {
          e.printStackTrace();
        }
        rs.close();
        pst.close();
        sb.append("<typeDtl>");
        sb.append("<typkey>"+typKey+"</typkey>");
        sb.append("<typval>"+typVal+"</typval>");
        sb.append("<dsc>"+descr+"</dsc>");
        sb.append("<mprp>"+mprp+"</mprp>");
        sb.append("<val>"+val+"</val>");
        sb.append("<prpTyp>"+prpTyp+"</prpTyp>");
        sb.append("<nmeId>"+nmeId+"</nmeId>");
          sb.append("<nme>"+nme+"</nme>");
        sb.append("</typeDtl>");
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<typeTag>" +sb.toString()+ "</typeTag>");
        return null;
      
      }
      /**
       * To check type and dscr in the table
       */
      public ActionForward chkTypeDesc(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr(); 
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        int cnt=0;
        String type=(String)req.getParameter("type");
        String desc=(String)req.getParameter("desc");
        ArrayList ary=new ArrayList();
        ary.add(type);
        ary.add(desc);
        String chkqry="select count(*) cnt from stk_crt where typ=? and dsc=? and stt='A' and vld_dte is null";
        String msg="";
        ArrayList outLst = db.execSqlLst(" ChkType Descr ",chkqry,ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try
        {
        while(rs.next()) {
            cnt=rs.getInt("cnt");
              }
        }catch(Exception e) {
          e.printStackTrace();
        }
        if(cnt==1) {
         msg="notValid"; 
        }
        else if(cnt==0) {
        msg="valid";
        }
        sb.append("<msgDtl>");
        sb.append("<msg>"+msg+"</msg>");
        sb.append("</msgDtl>"); 
        rs.close();
        pst.close();
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<msgTag>" +sb.toString()+ "</msgTag>");
        return null;
      }
      
      public ActionForward loadType(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr(); 
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
        /*
         * type dropdown
         */
        ArrayList typeList=new ArrayList();
        StringBuffer sb = new StringBuffer();
        String typeqry="select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and\n" + 
        "b.mdl = 'JFLEX' and b.nme_rule = 'STK_CRT_TYP' order by a.srt_fr";
        ArrayList outLst = db.execSqlLst(" loadType ",typeqry,new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
         try
         {
        while(rs.next()) {
          ArrayList type= new ArrayList();  
          type.add(rs.getString("chr_fr"));
          type.add(rs.getString("dsc")); 
          typeList.add(type);  

          sb.append("<type>");
          sb.append("<typeKey>" + util.nvl(rs.getString("chr_fr").replaceAll("&", "&amp;")) + "</typeKey>");
          sb.append("<typeVal>" +util.nvl(rs.getString("dsc").replaceAll("&", "&amp;")) + "</typeVal>");
          sb.append("</type>");
        }
        rs.close();
        pst.close();
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<typeTag>" +sb.toString()+ "</typeTag>");
        }catch(Exception e) {
          e.printStackTrace();
        }
        return null;
      }
    public ActionForward ptkPrc(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
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
        String cnt = (String)dbinfo.get("CNT");
        String stkIdn = req.getParameter("stkIdn");
        ArrayList ary = new ArrayList();
        ary.add(stkIdn);
        StringBuffer sb = new StringBuffer();
        String adv_pri_dtl = util.nvl((String)dbinfo.get("ADV_PRI_DTL"),"N");
        
        try {
            if(!adv_pri_dtl.equals("Y")){
                String prcDtl = "select grp, pct from itm_prm_dis_v where mstk_idn = ? order by grp_srt, sub_grp_srt";
              ArrayList outLst = db.execSqlLst("prcDtl", prcDtl, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                    sb.append("<prc>");
                    sb.append("<grp>" + util.nvl(rs.getString("grp"), "0") +
                              "</grp>");
                    sb.append("<pct>" + util.nvl(rs.getString("pct"), "0") +
                              "</pct>");
                    sb.append("</prc>");
                    }
                rs.close();
                pst.close();
                prcDtl = "select b.num pur_pri, decode(a.rap_rte, 1, null, trunc((b.num/a.rap_rte*100) - 100, 2)) pur_dis" + 
                ", decode(a.rap_rte, 1, null, trunc((nvl(upr,cmp)/a.rap_rte*100) - 100, 2)) rap_off " + 
                "from mstk a, stk_dtl b where a.idn = b.mstk_idn and b.grp = 1 and b.mprp = 'FA_PUR' and a.idn =?";
                
              outLst = db.execSqlLst("prcDtles", prcDtl, ary);
               pst = (PreparedStatement)outLst.get(0);
              rs = (ResultSet)outLst.get(1);
                if (rs.next()) {
                        sb.append("<prc>");
                        sb.append("<grp>Purchase Price</grp>");
                        sb.append("<pct>" + util.nvl(rs.getString("pur_pri"), "0") +
                                  "</pct>");
                        sb.append("</prc>");
                    
                        sb.append("<prc>");
                        sb.append("<grp>Purchase Discount</grp>");
                        sb.append("<pct>" + util.nvl(rs.getString("pur_dis"), "0") +
                                  "</pct>");
                        sb.append("</prc>");
                        
                        sb.append("<prc>");
                        sb.append("<grp>RAP OFF %</grp>");
                        sb.append("<pct>" + util.nvl(rs.getString("rap_off"), "0") +
                                  "</pct>");
                        sb.append("</prc>");
                    }
                rs.close();
                pst.close();
                prcDtl = "select sum(nvl(pct,0)) dys_dis from itm_prm_dis b where b.grp = 'AGE DISCOUNT' and b.mstk_idn =?";
                
              outLst = db.execSqlLst("prcDtles", prcDtl, ary);
               pst = (PreparedStatement)outLst.get(0);
              rs = (ResultSet)outLst.get(1);
                if (rs.next()) {
                        sb.append("<prc>");
                        sb.append("<grp>Days Disc Group</grp>");
                        sb.append("<pct>" + util.nvl(rs.getString("dys_dis"), "0") +
                                  "</pct>");
                        sb.append("</prc>");
                    }
                rs.close();
                pst.close();
            }else{
                GetPktPrice getpkt=new GetPktPrice(req);
                ArrayList stkIdnLst = new ArrayList();
                stkIdnLst.add(stkIdn);
                HashMap<String, HashMap> calcPriceDetails=getpkt.calcPrice(stkIdnLst,"SL");
                if(calcPriceDetails!=null && calcPriceDetails.size()>0){
                HashMap pktPriceDetails=calcPriceDetails.get(stkIdn);
                ArrayList sheetAppliedLst = (pktPriceDetails.get("PRI_DTL") == null)?new ArrayList():(ArrayList)pktPriceDetails.get("PRI_DTL");
                    for(int s=0;s<sheetAppliedLst.size();s++){
                        HashMap priDtlMap=(HashMap)sheetAppliedLst.get(s);
                        sb.append("<prc>");
                        sb.append("<grp>"+priDtlMap.get("NME")+"</grp>");
                        sb.append("<pct>"+priDtlMap.get("DIS")+"</pct>");
                        sb.append("</prc>");
                    }
                }
            }
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            res.getWriter().write("<prcs>" + sb.toString() + "</prcs>");

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
      return null;
    }
    
    public ActionForward ptkGrpPrc(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      String stkIdn = req.getParameter("stkIdn");
      String grp = req.getParameter("grp");
      ArrayList ary = new ArrayList();
      ary.add(grp);
      ary.add(stkIdn);
        StringBuffer sb = new StringBuffer();
      String prcDtl = "select grp, pct from ITM_PRM_DIS_GRP_V1 where stkgrp=? and mstk_idn = ?  order by grp_srt, sub_grp_srt";
      ArrayList outLst = db.execSqlLst("prcDtl", prcDtl, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);

        try {
            while (rs.next()) {
                sb.append("<prc>");
                sb.append("<grp>" + util.nvl(rs.getString("grp"), "0") +
                          "</grp>");
                sb.append("<pct>" + util.nvl(rs.getString("pct"), "0") +
                          "</pct>");
                sb.append("</prc>");
                }
            rs.close();
            pst.close();
            prcDtl = "select b.num pur_pri, decode(a.rap_rte, 1, null, trunc((b.num/a.rap_rte*100) - 100, 2)) pur_dis" + 
            ", decode(a.rap_rte, 1, null, trunc((nvl(upr,cmp)/a.rap_rte*100) - 100, 2)) rap_off " + 
            "from mstk a, stk_dtl b where a.idn = b.mstk_idn and b.grp = ? and b.mprp = 'FA_PUR' and a.idn =?";
            
           outLst = db.execSqlLst("prcDtles", prcDtl, ary);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            if (rs.next()) {
                    sb.append("<prc>");
                    sb.append("<grp>Purchase Price</grp>");
                    sb.append("<pct>" + util.nvl(rs.getString("pur_pri"), "0") +
                              "</pct>");
                    sb.append("</prc>");
                
                    sb.append("<prc>");
                    sb.append("<grp>Purchase Discount</grp>");
                    sb.append("<pct>" + util.nvl(rs.getString("pur_dis"), "0") +
                              "</pct>");
                    sb.append("</prc>");
                    
                    sb.append("<prc>");
                    sb.append("<grp>RAP OFF %</grp>");
                    sb.append("<pct>" + util.nvl(rs.getString("rap_off"), "0") +
                              "</pct>");
                    sb.append("</prc>");
                }
            rs.close();
            pst.close();
            prcDtl = "select sum(nvl(pct,0)) dys_dis from itm_prm_dis b where b.grp = 'AGE DISCOUNT' and b.mstk_idn =?";
            ary = new ArrayList();
            ary.add(stkIdn);
          outLst = db.execSqlLst("prcDtles", prcDtl, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            if (rs.next()) {
                    sb.append("<prc>");
                    sb.append("<grp>Days Disc Group</grp>");
                    sb.append("<pct>" + util.nvl(rs.getString("dys_dis"), "0") +
                              "</pct>");
                    sb.append("</prc>");
                }
            rs.close();
            pst.close();
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            res.getWriter().write("<prcs>" + sb.toString() + "</prcs>");

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
      return null;
    }
    public ActionForward getHoldByr(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    String byr="";
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    boolean isPnt = true;
    boolean isvender = true;
    try {
    String stkIdn = req.getParameter("stkIdn");
    String vnm = req.getParameter("vnm");
    String typ = req.getParameter("typ");
    ArrayList ary = new ArrayList();
    StringBuffer sb = new StringBuffer();
    ArrayList prpDspBlocked = info.getPageblockList();
    ary = new ArrayList();
    ary.add(stkIdn);
    ary.add(typ);
    ArrayList out = new ArrayList();
    out.add("I");
    CallableStatement cst =
    db.execCall("findMemo", "memo_pkg.find_ref_idn(pStkIdn => ?, pTyp =>? , pIdn => ?)",
    ary, out);
    int memoIdn = cst.getInt(3);

    //Start -- changes for Memo typ display
    cst.close();
    cst=null;
    String sqlByr = " select form_url_encode(byr) byr , a.idn , to_char(dte,'dd-Mon-rrrr') dte ,a.rmk,a.note_person, "+
    " b.dsc , byr_cabin ,  a.thru from jan_v a , memo_typ b where a.idn=? and a.typ = b.typ ";
    //End -- changes for Memo typ display
    ary = new ArrayList();
    ary.add(String.valueOf(memoIdn));
      ArrayList outLst = db.execSqlLst("sqlByr", sqlByr, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    sb.append("<memo>");
    while (rs.next()) {
    byr = rs.getString(1);

    sb.append("<byr>" + util.nvl(rs.getString("byr"), "0") +
    "</byr>");
    sb.append("<dte>" + util.nvl(rs.getString("dte"), "0") +
    "</dte>");
    sb.append("<idn>" + util.nvl(rs.getString("idn"), "0") +
    "</idn>");
    //Start -- changes for Memo typ display
    sb.append("<typ>" + util.nvl(rs.getString("dsc"), "0") +
    "</typ>");
    sb.append("<cabin>" + util.nvl(rs.getString("byr_cabin"), "0") +
    "</cabin>");
      sb.append("<thru>" + util.nvl(rs.getString("thru"), "o") +
      "</thru>");
    sb.append("<memormk>" + util.nvl(rs.getString("rmk"), "NA") +
        "</memormk>");
    sb.append("<noteperson>" + util.nvl(rs.getString("note_person"), "NA") +
          "</noteperson>");
    //End -- changes for Memo typ display
    isPnt = false;
    }
    rs.close();
        pst.close();
    if(isPnt){

    sb.append("<byr>0</byr>");
    sb.append("<dte>0</dte>");
    sb.append("<idn>0</idn>");
    sb.append("<typ>0</typ>");
    sb.append("<cabin>0</cabin>");
    sb.append("<thru>0</thru>");
        sb.append("<memormk>0</memormk>");
        sb.append("<noteperson>0</noteperson>");
    }
//    ary = new ArrayList();
//    ary.add(stkIdn);
//    String maxOffer =" select max(round(a.ofr_rte)) ofrRte , a.ofr_dis , byr.get_nm(a.byr_idn) byr,to_char(a.to_dt,'dd-Mon-rrrr') to_dt,a.rmk " +
//    " from web_bid_wl a, mstk b where a.mstk_idn = b.idn " +
//    " and a.typ in ('PP','LB','BID') and trunc(nvl(a.to_dt, sysdate)) >= trunc(sysdate) " +
//    " and a.stt='A' and a.mstk_idn=? group by a.ofr_dis, byr.get_nm(a.byr_idn),to_char(a.to_dt,'dd-Mon-rrrr'),a.rmk ";
//    rs = db.execSql("maxOffer", maxOffer, ary);
//    if(rs.next()){
//    sb.append("<offer>"+util.nvl(rs.getString("ofrRte"),"0")+"</offer>");
//    sb.append("<offerDis>"+util.nvl(rs.getString("ofr_dis"),"0")+"</offerDis>");
//    sb.append("<offerbyr>"+util.nvl(rs.getString("byr"),"0")+"</offerbyr>");
//    sb.append("<offertill>"+util.nvl(rs.getString("to_dt"),"-")+"</offertill>");
//    sb.append("<offercmmt>"+util.nvl(rs.getString("rmk"),"-")+"</offercmmt>");
//
//    }else{
//    sb.append("<offer>0</offer>");
//    sb.append("<offerDis>0</offerDis>");
//    sb.append("<offerbyr>0</offerbyr>");
//    sb.append("<offertill>-</offertill>");
//    sb.append("<offercmmt>-</offercmmt>");
//    }
//    rs.close();
        if(!prpDspBlocked.contains("VENDOR")){
    String reviewQ="Select Byr.Get_Nm(Vndr_Idn) vender From Mpur A, Pur_Dtl B Where A.Pur_Idn = B.Pur_Idn And A.Typ = 'R' And B.Ref_Idn = '"+vnm+"'";
           outLst = db.execSqlLst("review", reviewQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
    sb.append("<review>" + util.nvl(rs.getString("vender"), "0")+"</review>");
    isvender=false;
    }
        rs.close();
        pst.close();
        }
    if(isvender)
    sb.append("<review>0</review>");
    sb.append("</memo>");
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>" + sb.toString() + "</memos>");
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    } catch (IOException ioe) {
    // TODO: Add catch code
    ioe.printStackTrace();
    }



    return null;
    }
    public ActionForward offerhistory(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
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
        boolean isPnt = true;
        try {
        String stkIdn = req.getParameter("stkIdn");
        ArrayList ary = new ArrayList();
        StringBuffer sb = new StringBuffer();
        ary = new ArrayList();
        ary.add(stkIdn);
        String maxOffer =" Select A.Ofr_Rte Ofrrte ,decode(a.stt,'A','Active','InActive') stt, A.Ofr_Dis , form_url_encode(Byr.Get_Nm(A.Byr_Idn)) Byr,To_Char(A.To_Dt,'dd-Mon-rrrr') To_Dt,A.Rmk,a.lmt_rte,\n" + 
        "decode(least(trunc(nvl(a.to_dt, sysdate)),trunc(sysdate)), trunc(sysdate), 'Blue','red') color\n" + 
        "         from web_bid_wl a, mstk b where a.mstk_idn = b.idn \n" + 
        "         and a.typ in ('PP','LB','BID','KS','KO') and trunc(nvl(a.to_dt, sysdate)) >= trunc(sysdate-30)\n" + 
        "         And A.Mstk_Idn=?\n" + 
        "         order by A.To_Dt desc,color ";
          ArrayList outLst = db.execSqlLst("maxOffer", maxOffer, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        isPnt=false;
        sb.append("<offerby>");
        sb.append("<offer>"+util.nvl(rs.getString("ofrRte"),"0")+"</offer>");
        sb.append("<offercolor>"+util.nvl(rs.getString("color"),"")+"</offercolor>");
        sb.append("<offerDis>"+util.nvl(rs.getString("ofr_dis"),"0")+"</offerDis>");
        sb.append("<offerbyr>"+util.nvl(rs.getString("byr"),"0")+"</offerbyr>");
        sb.append("<offertill>"+util.nvl(rs.getString("to_dt"),"-")+"</offertill>");
        sb.append("<offercmmt>"+util.nvl(rs.getString("rmk"),"-")+"</offercmmt>");
        sb.append("<offerlmt>"+util.nvl(rs.getString("lmt_rte"),"-")+"</offerlmt>");
        sb.append("<offerstt>"+util.nvl(rs.getString("stt"),"-")+"</offerstt>");
        sb.append("</offerby>");
        }
        rs.close();
            pst.close();
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<offers>" + sb.toString() + "</offers>");
        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        } catch (IOException ioe) {
        // TODO: Add catch code
        ioe.printStackTrace();
        }



        return null;
        }


    public ActionForward bankAddr(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      String idn = req.getParameter("bankIdn");
      ArrayList ary = new ArrayList();
      ary.add(idn);
     StringBuffer sb = new StringBuffer();
      String prcDtl = "select addr_idn ,  bldg ||''|| street ||''|| landmark ||''|| area addr  from maddr where nme_idn= ?";
      ArrayList outLst = db.execSqlLst("prcDtl", prcDtl, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);

        try {
            while (rs.next()) {
                sb.append("<bnkAddr>");
                sb.append("<addrIdn>" + util.nvl(rs.getString("addr_idn"), "0") +
                          "</addrIdn>");
                sb.append("<addr>" + util.nvl(rs.getString("addr"), "0").replaceAll("&", "&amp;") +
                          "</addr>");
                sb.append("</bnkAddr>");
                }
            rs.close();
            pst.close();
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            res.getWriter().write("<bank>" + sb.toString() + "</bank>");

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
      return null;
    }
    
   
    public ActionForward refine(ActionMapping am, ActionForm a, HttpServletRequest req, HttpServletResponse res) throws Exception {  
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
        String fld = util.nvl(req.getParameter("fld")).trim();
        String fldVal = util.nvl(req.getParameter("fldVal")).trim();
        String stt = util.nvl(req.getParameter("stt"));
        String sql = "update gt_srch_rslt set flg = ? where "+fld+"= ? and flg in ('Z','N','M')" ;
        ArrayList ary = new ArrayList();
        ary.add("R");
        ary.add(fldVal);
        if(stt.equals("true")){
         sql = "update gt_srch_rslt set flg = ? where "+fld+"= ? and flg in ('N', 'R','M')" ;
            ary = new ArrayList();
            ary.add("Z");
            ary.add(fldVal);
        }
     
        
        int ct = db.execUpd("sql", sql, ary);
       
        
        return null;
    
    }
    
    public ActionForward FinalDlvByr(ActionMapping mp , ActionForm fm , HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String byrId = req.getParameter("bryIdn");
    
        String finalByr = "select  form_url_encode(fnl_byr) fnl_byr , fnl_byr_idn  from dlv_pndg_v where byr_idn=? order by fnl_byr";
        ArrayList ary = new ArrayList();
        ary.add(byrId);
         try {
          ArrayList outLst = db.execSqlLst("sqlVlu", finalByr, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String nme =
                    util.nvl(rs.getString(1), "0").replaceAll("&", "&amp;");
              
                sb.append("<nmes>");
                sb.append("<nmeid>" + util.nvl(rs.getString(2)) + "</nmeid>");
                sb.append("<nme>" + nme + "</nme>");
                sb.append("</nmes>");

            }

            rs.close();
             pst.close();

            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
        
        return null;
    }
    
    public ActionForward dlvIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String nmeId = req.getParameter("nameIdn");
        String typ = req.getParameter("typ");
        String restrict_sale_rtn_days = util.nvl((String)info.getDmbsInfoLst().get("RESTRICT_SALE_RTN_DAYS"),"30");
        String sql =  "            select distinct a.idn , to_char(a.dte, 'dd-Mon HH24:mi') dte from mdlv a , dlv_dtl b \n" + 
        "            where a.stt ='IS' and a.typ in ('DLV') \n" + 
        "            and a.idn = b.idn and b.stt in ('DLV','PD')\n" + 
        "            and nvl(a.inv_nme_idn,a.nme_idn)=?";
        sql+=" and trunc(a.dte)>=trunc(sysdate)-"+restrict_sale_rtn_days+"\n"; 
        ArrayList ary = new ArrayList();
        ary.add(nmeId);
      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            sb.append("<memo>");
            sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
            sb.append("<dte>"+util.nvl(rs.getString("dte"),"") +"</dte>");
            sb.append("</memo>");
        }
        rs.close();
        pst.close();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<memos>"+sb.toString()+"</memos>");
        return null;
    }
    
    public ActionForward loadBox(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());

    StringBuffer sb = new StringBuffer();

    String vnmId = util.nvl(req.getParameter("vnmId"));
    String favSrch = "select VNM,nvl(qty, 0) - nvl(qty_iss,0) QTY,nvl(cts, 0) - nvl(cts_iss, 0) CTS, UPR from mstk where idn= "+vnmId;
    ArrayList ary = new ArrayList();
      ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    if(rs.next()){
    sb.append("<boxdata>");
    sb.append("<vnm>"+util.nvl(rs.getString(1)) +"</vnm>");
    sb.append("<qty>"+util.nvl(rs.getString(2)) +"</qty>");
    sb.append("<cts>"+util.nvl(rs.getString(3)) +"</cts>");
    sb.append("<rate>"+util.nvl(rs.getString(4)) +"</rate>");
    sb.append("</boxdata>");
    }else{
        sb.append("<boxdata>");
        sb.append("<vnm>0</vnm>");
        sb.append("<qty>0</qty>");
        sb.append("<cts>0</cts>");
        sb.append("<rate>0</rate>");
        sb.append("</boxdata>");   
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<boxdt>" + sb.toString() + "</boxdt>");


    return null;

    }
    
    public ActionForward loadBoxselection(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    StringBuffer sb = new StringBuffer();

    String vnmId = util.nvl(req.getParameter("vnmId"));
    String favSrch = "select VNM,nvl(qty, 0) - nvl(qty_iss,0) QTY,nvl(cts, 0) - nvl(cts_iss, 0) CTS, UPR,qty avlQTY,cts avlCTS from mstk where idn= "+vnmId;
    ArrayList ary = new ArrayList();
      ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<boxdata>");
    sb.append("<vnm>"+util.nvl(rs.getString(1)) +"</vnm>");
    sb.append("<qty>"+util.nvl(rs.getString(2)) +"</qty>");
    sb.append("<cts>"+util.nvl(rs.getString(3)) +"</cts>");
    sb.append("<rate>"+util.nvl(rs.getString(4)) +"</rate>");
    sb.append("<avlqty>"+util.nvl(rs.getString(5)) +"</avlqty>");
    sb.append("<avlcts>"+util.nvl(rs.getString(6)) +"</avlcts>");
    sb.append("</boxdata>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<boxdt>" + sb.toString() + "</boxdt>");


    return null;

    }
    
    public ActionForward loadBoxSplit(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());

    StringBuffer sb = new StringBuffer();

    String vnmId = util.nvl(req.getParameter("vnmId"));
    String favSrch = "select nvl(qty, 0) - nvl(qty_iss,0) QTY,nvl(cts, 0) - nvl(cts_iss, 0) CTS, UPR,qty avlQTY,cts avlCTS from mstk where idn= "+vnmId;
    ArrayList ary = new ArrayList();
      ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<boxdata>");
    sb.append("<qty>"+util.nvl(rs.getString(1)) +"</qty>");
    sb.append("<cts>"+util.nvl(rs.getString(2)) +"</cts>");
    sb.append("<rate>"+util.nvl(rs.getString(3)) +"</rate>");
    sb.append("<avlqty>"+util.nvl(rs.getString(4)) +"</avlqty>");
    sb.append("<avlcts>"+util.nvl(rs.getString(5)) +"</avlcts>");
    sb.append("</boxdata>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<boxdt>" + sb.toString() + "</boxdt>");


    return null;

    }
    
    
    public ActionForward loadSplit(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());

    StringBuffer sb = new StringBuffer();

    String vnmId = util.nvl(req.getParameter("vnmId"));
    String favSrch = "select nvl(qty, 0) - nvl(qty_iss,0) QTY,nvl(cts, 0) - nvl(cts_iss, 0) CTS, UPR from mstk where idn= "+vnmId;
    ArrayList ary = new ArrayList();
      ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<boxdata>");
    sb.append("<qty>"+util.nvl(rs.getString(1)) +"</qty>");
    sb.append("<cts>"+util.nvl(rs.getString(2)) +"</cts>");
    sb.append("<rate>"+util.nvl(rs.getString(3)) +"</rate>");
    sb.append("</boxdata>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<boxdt>" + sb.toString() + "</boxdt>");
    return null;
    }
    
    
    public ActionForward loadboxtypvnm(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());

    StringBuffer sb = new StringBuffer();

    String boxtyp = util.nvl(req.getParameter("boxtyp"));
    String status = util.nvl(req.getParameter("stt"));
    status=util.getVnm(status);
    String             sqlQ="    Select a.vnm,a.idn,a.sk1\n" + 
            "    From Mstk A,Stk_Dtl B\n" + 
            "    Where\n" + 
            "    a.idn=b.mstk_idn and b.grp=1 and b.mprp='BOX_TYP' and a.pkt_ty not in('NR') and a.stt in("+status+") and b.val in('"+boxtyp+"') order by a.sk1";
    ArrayList ary = new ArrayList();
      ArrayList outLst = db.execSqlLst("favSrch",sqlQ, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<boxdata>");
    sb.append("<vnm>"+util.nvl(rs.getString(1)) +"</vnm>");
    sb.append("<idn>"+util.nvl(rs.getString(2)) +"</idn>");
    sb.append("</boxdata>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<boxdt>" + sb.toString() + "</boxdt>");
    return null;
    }
    
    public ActionForward verifyMDL(ActionMapping mapping, ActionForm form,
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
    StringBuffer sb = new StringBuffer();
    String formatTxt = req.getParameter("formatTxt");
    ArrayList ary = new ArrayList();
    String sql = "select mdl from rep_prp where mdl=? " ;
    formatTxt = formatTxt.replaceAll("_MEMOXL","");
    formatTxt = formatTxt.trim().toUpperCase();
    formatTxt = formatTxt.replaceAll(" ", "");
    formatTxt=formatTxt+"_MEMOXL";
    ary.add(formatTxt);
      ArrayList outLst = db.execSqlLst("Verify MDL", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    if(rs.next()){
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    response.getWriter().write("<message>yes</message>");
    }
    else{
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    response.getWriter().write("<message>No</message>");
    }

    rs.close();
    pst.close();
    return null;
    }
    public ActionForward generatesrno(ActionMapping mapping, ActionForm form,
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
    StringBuffer sb = new StringBuffer();
    String nmeIdn = util.nvl(req.getParameter("nmeIdn"),"0");
    ArrayList ary = new ArrayList();
    ary.add(nmeIdn);
    int updCt = db.execCall("dp_generate_srno", " dp_generate_srno(pnme_idn => ?) ", ary);
    if(updCt>0){
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    response.getWriter().write("<message>S</message>");
    }
    else{
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    response.getWriter().write("<message>F</message>");
    }
    return null;
    }
   
    public ActionForward memof(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
           HttpSession session = req.getSession(false);
           InfoMgr info = (InfoMgr)session.getAttribute("info");
           DBUtil util = new DBUtil();
           DBMgr db = new DBMgr(); 
           db.setCon(info.getCon());
           util.setDb(db);
           util.setInfo(info);
           db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
           util.setLogApplNm(info.getLoApplNm());
       StringBuffer sb = new StringBuffer();
       String nmeId = req.getParameter("nameIdn");
       String typ = util.nvl(req.getParameter("typ"));
       String pktTy = util.nvl(req.getParameter("pktTy"));
       String pktTyCon=" a.pkt_ty in('"+pktTy+"')";
       if(pktTy.equals("") || pktTy.equals("ALL"))
            pktTyCon = " a.pkt_ty in('NR','SMX','RGH') ";


       String sql = "select a.idn, a.memo_typ typ, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte , b.nme byr, c.term, a.qty,trunc(a.cts,2) cts, to_char(decode(pkt_ty,'SMX',(vlu/cts)*trunc(cts,2),vlu) ,'999999990.00') val,form_url_encode(nvl(a.rmk,'NA')) rmk,a.note_person from " +
       "memo_smry_v a, nme_v b, mtrms c " +
       "where " +
       "a.trms_idn = c.idn and a.nme_idn = b.nme_idn and b.nme_idn =? and a.memo_typ =? and "+pktTyCon+" order by a.idn desc";
       ArrayList ary = new ArrayList();
       ary.add(nmeId);
       ary.add(typ);
      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
       while(rs.next()){
       sb.append("<memo>");
       sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
       sb.append("<trm>"+util.nvl(rs.getString("term"),"0") +"</trm>");
       sb.append("<qty>"+util.nvl(rs.getString("qty"),"0") +"</qty>");
       sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
       sb.append("<vlu>"+util.nvl(rs.getString("val"),"0") +"</vlu>");
       sb.append("<dte>"+util.nvl(rs.getString("dte"),"0") +"</dte>");
       sb.append("<typ>"+util.nvl(rs.getString("typ"),"0") +"</typ>");
       sb.append("<memormk>"+util.nvl(rs.getString("rmk"),"NA") +"</memormk>");
       sb.append("<noteperson>"+util.nvl(rs.getString("note_person"),"NA") +"</noteperson>");
       sb.append("</memo>");
       }
       rs.close();
       pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>"+sb.toString()+"</memos>");
    return null;
    }
    
    public ActionForward salef(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
           HttpSession session = req.getSession(false);
           InfoMgr info = (InfoMgr)session.getAttribute("info");
           DBUtil util = new DBUtil();
           DBMgr db = new DBMgr(); 
           db.setCon(info.getCon());
           util.setDb(db);
           util.setInfo(info);
           db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
           util.setLogApplNm(info.getLoApplNm());
       StringBuffer sb = new StringBuffer();
       String nmeId = req.getParameter("nameIdn");
       String typ = util.nvl(req.getParameter("typ"));
       String pktTy = util.nvl(req.getParameter("pktTy"));
       String pktTyCon=" a.pkt_ty in('"+pktTy+"')";
       if(pktTy.equals("") || pktTy.equals("ALL"))
            pktTyCon = " a.pkt_ty in('NR','SMX','RGH') ";


       String sql = "select a.idn, a.sale_typ typ, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte , b.nme byr, c.term, a.qty,trunc(a.cts,2) cts, to_char(decode(pkt_ty,'SMX',(vlu/cts)*trunc(cts,2),vlu) ,'999999990.00') val,form_url_encode(nvl(a.rmk,'NA')) rmk,a.note_person from " +
       "sale_smry_v a, nme_v b, mtrms c " +
       "where " +
       "a.trms_idn = c.idn and a.nme_idn = b.nme_idn and b.nme_idn =? and a.sale_typ =? and "+pktTyCon+" order by a.idn desc";
       ArrayList ary = new ArrayList();
       ary.add(nmeId);
       ary.add(typ);
      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
       while(rs.next()){
       sb.append("<memo>");
       sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
       sb.append("<trm>"+util.nvl(rs.getString("term"),"0") +"</trm>");
       sb.append("<qty>"+util.nvl(rs.getString("qty"),"0") +"</qty>");
       sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
       sb.append("<vlu>"+util.nvl(rs.getString("val"),"0") +"</vlu>");
       sb.append("<dte>"+util.nvl(rs.getString("dte"),"0") +"</dte>");
       sb.append("<typ>"+util.nvl(rs.getString("typ"),"0") +"</typ>");
       sb.append("<memormk>"+util.nvl(rs.getString("rmk"),"NA") +"</memormk>");
       sb.append("<noteperson>"+util.nvl(rs.getString("note_person"),"NA") +"</noteperson>");
       sb.append("</memo>");
       }
       rs.close();
       pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>"+sb.toString()+"</memos>");
    return null;
    }
    
    public ActionForward brcdlvf(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
           HttpSession session = req.getSession(false);
           InfoMgr info = (InfoMgr)session.getAttribute("info");
           DBUtil util = new DBUtil();
           DBMgr db = new DBMgr(); 
           db.setCon(info.getCon());
           util.setDb(db);
           util.setInfo(info);
           db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
           util.setLogApplNm(info.getLoApplNm());
       StringBuffer sb = new StringBuffer();
       String nmeId = req.getParameter("nameIdn");
       String typ = util.nvl(req.getParameter("typ"));
       String pktTy = util.nvl(req.getParameter("pktTy"));
       String pktTyCon=" a.pkt_ty in('"+pktTy+"')";
       if(pktTy.equals("") || pktTy.equals("ALL"))
            pktTyCon = " a.pkt_ty in('NR','SMX','RGH') ";


       String sql = "select a.idn, a.sale_typ typ, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte , b.nme byr, c.term, a.qty,trunc(a.cts,2) cts, to_char(decode(pkt_ty,'SMX',(vlu/cts)*trunc(cts,2),vlu) ,'999999990.00') val,form_url_encode(nvl(a.rmk,'NA')) rmk,a.note_person from " +
       "brc_smry_v a, nme_v b, mtrms c " +
       "where " +
       "a.trms_idn = c.idn and a.nme_idn = b.nme_idn and b.nme_idn =? and a.sale_typ =? and "+pktTyCon+" order by a.idn desc";
       ArrayList ary = new ArrayList();
       ary.add(nmeId);
       ary.add(typ);
      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
       while(rs.next()){
       sb.append("<memo>");
       sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
       sb.append("<trm>"+util.nvl(rs.getString("term"),"0") +"</trm>");
       sb.append("<qty>"+util.nvl(rs.getString("qty"),"0") +"</qty>");
       sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
       sb.append("<vlu>"+util.nvl(rs.getString("val"),"0") +"</vlu>");
       sb.append("<dte>"+util.nvl(rs.getString("dte"),"0") +"</dte>");
       sb.append("<typ>"+util.nvl(rs.getString("typ"),"0") +"</typ>");
       sb.append("<memormk>"+util.nvl(rs.getString("rmk"),"NA") +"</memormk>");
       sb.append("<noteperson>"+util.nvl(rs.getString("note_person"),"NA") +"</noteperson>");
       sb.append("</memo>");
       }
       rs.close();
       pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>"+sb.toString()+"</memos>");
    return null;
    }
    public ActionForward memosameterms(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    StringBuffer sb = new StringBuffer();
    String nmeId = req.getParameter("nameIdn");
    String sql = "select distinct c.idn,c.term from \n" + 
    "    memo_smry_v a, nme_v b, mtrms c\n" + 
    "    where \n" + 
    "    A.Trms_Idn = C.Idn And A.Nme_Idn = B.Nme_Idn And B.Nme_Idn =? \n" + 
    "    order by c.idn";
    ArrayList ary = new ArrayList();
    ary.add(nmeId);
      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<memo>");
    sb.append("<nmeId>"+nmeId+"</nmeId>");
    sb.append("<trm>"+util.nvl(rs.getString("term"),"0") +"</trm>");
    sb.append("<trmidn>"+util.nvl(rs.getString("idn"),"0") +"</trmidn>");
    sb.append("</memo>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>"+sb.toString()+"</memos>");
    return null;
    }
    
    public ActionForward salesameterms(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    StringBuffer sb = new StringBuffer();
    String nmeId = req.getParameter("nameIdn");
    String sql = "select distinct c.idn,c.term from \n" + 
    "   sale_smry_v a, nme_v b, mtrms c\n" + 
    "    where \n" + 
    "    A.Trms_Idn = C.Idn And A.Nme_Idn = B.Nme_Idn And B.Nme_Idn =? \n" + 
    "    order by c.idn";
    ArrayList ary = new ArrayList();
    ary.add(nmeId);
      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<memo>");
    sb.append("<nmeId>"+nmeId+"</nmeId>");
    sb.append("<trm>"+util.nvl(rs.getString("term"),"0") +"</trm>");
    sb.append("<trmidn>"+util.nvl(rs.getString("idn"),"0") +"</trmidn>");
    sb.append("</memo>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>"+sb.toString()+"</memos>");
    return null;
    }
    
    public ActionForward brcdlvsameterms(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    StringBuffer sb = new StringBuffer();
    String nmeId = req.getParameter("nameIdn");
    String sql = "select distinct c.idn,c.term from \n" + 
    "   brc_smry_v a, nme_v b, mtrms c\n" + 
    "    where \n" + 
    "    A.Trms_Idn = C.Idn And A.Nme_Idn = B.Nme_Idn And B.Nme_Idn =? \n" + 
    "    order by c.idn";
    ArrayList ary = new ArrayList();
    ary.add(nmeId);
      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<memo>");
    sb.append("<nmeId>"+nmeId+"</nmeId>");
    sb.append("<trm>"+util.nvl(rs.getString("term"),"0") +"</trm>");
    sb.append("<trmidn>"+util.nvl(rs.getString("idn"),"0") +"</trmidn>");
    sb.append("</memo>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>"+sb.toString()+"</memos>");
    return null;
    }
    public ActionForward boxSaleIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    StringBuffer sb = new StringBuffer();
    String nmeId = req.getParameter("nameIdn");
    String typ = req.getParameter("typ");
    String rlnId = util.nvl(req.getParameter("rlnId"));
    ArrayList ary = new ArrayList();
    ary.add(nmeId);
    String sql ="Select Distinct A.Idn,to_char(a.dte, 'dd-Mon HH24:mi') dte From Msal A , Jansal B , Mstk C Where A.Idn = B.Idn And B.Mstk_Idn = C.Idn " +
    "and b.stt='SL' and c.pkt_ty in ('MX','MIX') and a.typ in ('"+typ+"') and a.stt <> 'RT' and nvl(a.inv_nme_idn, a.nme_idn) = ? ";
    if(!rlnId.equals("")){
        if(typ.equals("SL"))
        sql = sql+" and nvl(fnl_trms_idn,nmerel_idn) = ?";
        else
        sql = sql+" and nmerel_idn = ? ";
    ary.add(rlnId);
    }
    sql = sql+ " order by a.idn ";

      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<memo>");
    sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
    sb.append("<dte>"+util.nvl(rs.getString("dte"),"") +"</dte>");
    sb.append("</memo>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>"+sb.toString()+"</memos>");
    return null;
    }
    
    public ActionForward memotypIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    StringBuffer sb = new StringBuffer();
    String nmeId = req.getParameter("nameIdn");
    String nmeRleId = req.getParameter("nmeRln");
    String typ = req.getParameter("typ");



    ArrayList ary = new ArrayList();
    String sql = "Select Distinct A.idn , to_char(A.dte, 'dd-Mon HH24:mi') dte From Mjan A , Jandtl B , Mstk C Where A.Idn = B.Idn And B.Mstk_Idn = C.Idn " +
    "And B.Stt = 'IS' and a.stt='IS' And A.Typ=? And Nme_Idn=? " +
    "And Nmerel_Idn=? and c.pkt_ty in ('MX','MIX','RGH') Order By Idn";
    ary.add(typ);
    ary.add(nmeId);
    ary.add(nmeRleId);
      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<memo>");
    sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
    sb.append("<dte>"+util.nvl(rs.getString("dte"),"") +"</dte>");
    sb.append("</memo>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>"+sb.toString()+"</memos>");
    return null;
    }
    
    public ActionForward pricechangememotypIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    StringBuffer sb = new StringBuffer();
    String nmeId = req.getParameter("nameIdn");
    String nmeRleId = req.getParameter("nmeRln");
    String typ = util.nvl(req.getParameter("typ"));
    String PKTTY = util.nvl(req.getParameter("PKTTY"));
    String typStr="";
    if(!typ.equals("ALL"))
         typStr=" and typ = '"+typ+"'" ;
    if(!PKTTY.equals(""))
        typStr=typStr+" and pkt_ty in ('"+PKTTY+"')";
    ArrayList ary = new ArrayList();
    String sql = "select idn,to_char(dte, 'dd-Mon HH24:mi') dte , typ , qty , cts,vlu from memo_smry_v where  Nme_Idn=? " +
    "And Nmerel_Idn=? "+typStr+" Order By Idn";
    ary.add(nmeId);
    ary.add(nmeRleId);
      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<memo>");
    sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
    sb.append("<dte>"+util.nvl(rs.getString("dte"),"0") +"</dte>");
      sb.append("<typ>"+util.nvl(rs.getString("typ"),"0") +"</typ>");
      sb.append("<qty>"+util.nvl(rs.getString("qty"),"0") +"</qty>");
      sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
      sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
      
    sb.append("</memo>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>"+sb.toString()+"</memos>");
    return null;
    }
    
    public ActionForward verifyrelCur(ActionMapping mapping, ActionForm form,
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
       StringBuffer sb = new StringBuffer();
       String rlnIdn = util.nvl(req.getParameter("rlnIdn"));
       String trfrlnId = util.nvl(req.getParameter("trfrlnId"));
       String currency = util.nvl(req.getParameter("currency"));
       ArrayList ary = new ArrayList();
       boolean exists=false;
       String sql = "select 1\n" + 
       "from nmerel f,nmerel t\n" + 
       "where \n" + 
       "f.nmerel_idn=? and \n" + 
       "t.nmerel_idn=? and f.cur=t.cur" ;
       ary = new ArrayList();
       ary.add(rlnIdn);
       ary.add(trfrlnId);
         ArrayList outLst = db.execSqlLst("Verify CUR", sql, ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
       if(rs.next()){
               exists=true;
       }
       rs.close();
       pst.close();
       if(currency.equals("N")){
           exists=true;
       }
       if(exists){
         response.setContentType("text/xml"); 
         response.setHeader("Cache-Control", "no-cache"); 
         response.getWriter().write("<message>yes</message>");
       }
       else{
         response.setContentType("text/xml"); 
         response.setHeader("Cache-Control", "no-cache"); 
         response.getWriter().write("<message>No</message>");
       }

       rs.close();
       return null;
       }
    
    public ActionForward memoReturntypIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    StringBuffer sb = new StringBuffer();
    String nmeId = req.getParameter("nameIdn");
    String nmeRleId = req.getParameter("nmeRln");
    String typ = req.getParameter("typ");



    ArrayList ary = new ArrayList();
    String sql = "Select Distinct A.idn , to_char(A.dte, 'dd-Mon HH24:mi') dte From Mjan A , Jandtl B , Mstk C Where A.Idn = B.Idn And B.Mstk_Idn = C.Idn " +
    "And B.Stt ='IS' and a.stt='IS' And A.Typ=? And Nme_Idn=? " +
    "And Nmerel_Idn=? and c.pkt_ty='NR' Order By Idn";
    ary.add(typ);
    ary.add(nmeId);
    ary.add(nmeRleId);
      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    
    while(rs.next()){
    sb.append("<memo>");
    sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
    sb.append("<dte>"+util.nvl(rs.getString("dte"),"") +"</dte>");
    sb.append("</memo>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>"+sb.toString()+"</memos>");
    return null;
    }
    public ActionForward MemoReturnIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    StringBuffer sb = new StringBuffer();
    String nmeId = util.nvl(req.getParameter("nameIdn"));
    String nmeRleId = util.nvl(req.getParameter("nmeRln"));
    String PKTTYP = util.nvl(req.getParameter("PKTTYP"));
    if(!PKTTYP.equals(""))
        PKTTYP="('"+PKTTYP+"')";
    else
        PKTTYP="('NR','SMX')";
    
    ArrayList ary = new ArrayList();
    String sql = "Select Distinct A.idn , to_char(A.dte, 'dd-Mon HH24:mi') dte From Mjan A , Jandtl B , Mstk C Where A.Idn = B.Idn And B.Mstk_Idn = C.Idn " +
    "And B.Stt ='IS' and a.stt='IS' and a.typ in ('I', 'E','O','WH','WA','WM','ZP','LB','K','H','BID')  and c.pkt_ty in "+PKTTYP+" " ;
    if(!nmeId.equals("0") && !nmeId.equals("")){
    sql=sql+ " And Nme_Idn=?  ";
        ary.add(nmeId);
    }
    if(!nmeRleId.equals("0") && !nmeRleId.equals("")){    
    sql=sql+" And Nmerel_Idn=? ";
        ary.add(nmeRleId);
    }
    sql=sql+" Order By Idn";
  
      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<memo>");
    sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
    sb.append("<dte>"+util.nvl(rs.getString("dte"),"") +"</dte>");
    sb.append("</memo>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>"+sb.toString()+"</memos>");
    return null;
    }
    
    public ActionForward MemoSMX(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    StringBuffer sb = new StringBuffer();
    String nmeId = req.getParameter("nameIdn");
    String nmeRleId = req.getParameter("nmeRln");
    ArrayList ary = new ArrayList();
    String sql = "Select Distinct A.idn , to_char(A.dte, 'dd-Mon HH24:mi') dte From Mjan A , Jandtl B , Mstk C Where A.Idn = B.Idn And B.Mstk_Idn = C.Idn " +
    "And B.Stt ='SL' And Nme_Idn=? and a.typ like '%AP' " +
    "And Nmerel_Idn=? and c.pkt_ty in ('SMX') Order By Idn";
    ary.add(nmeId);
    ary.add(nmeRleId);
      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<memo>");
    sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
    sb.append("<dte>"+util.nvl(rs.getString("dte"),"") +"</dte>");
    sb.append("</memo>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>"+sb.toString()+"</memos>");
    return null;
    }
    public ActionForward gtQuot(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String valfld = util.nvl(req.getParameter("quot"));
        String stkIdn = util.nvl(req.getParameter("stkIdn"));
        String colmn = util.nvl(req.getParameter("colmn"),"QUOT");
        String lstNme = (String)gtMgr.getValue("lstNme");
        HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
        GtPktDtl pktDtl = (GtPktDtl)stockList.get(stkIdn);
        if(colmn.equals("CTS")){
        pktDtl.setValue("CRTWT", valfld);
            pktDtl.setValue("cts", valfld);
        }else if(colmn.equals("QTY"))
         pktDtl.setValue("qty", valfld);
        else{
        
        String curcts=util.nvl(pktDtl.getValue("cts"),"0").trim();
         String curquot=util.nvl(pktDtl.getValue("quot"),"0").trim();  
          float value = Float.parseFloat(curcts)*Float.parseFloat(curquot);
         pktDtl.setValue("USDVAL",String.valueOf(value));
         pktDtl.setValue("AMT", String.valueOf(value));
         pktDtl.setValue("quot", valfld);
        }
        stockList.put(stkIdn, pktDtl);
        gtMgr.setValue(lstNme, stockList);
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>DONE</memos>");
        return null;
    }
    public ActionForward updateGtCTSQt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    String valfld = util.nvl(req.getParameter("val"));
    String stkIdn = util.nvl(req.getParameter("stkIdn"));
    String colmn = util.nvl(req.getParameter("colmn"));
        String lstNme = (String)gtMgr.getValue("lstNme");
        HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
        GtPktDtl pktDtl = (GtPktDtl)stockList.get(stkIdn);
        if(colmn.equals("CTS")){
      pktDtl.setValue("CRTWT", valfld);
            pktDtl.setValue("cts", valfld);
        }else if(colmn.equals("QTY"))
         pktDtl.setValue("qty", valfld);
        else if(colmn.equals("memo_wtdiff"))
         pktDtl.setValue("memo_wtdiff", valfld);
     else{
         pktDtl.setValue("quot", valfld);
        String curcts=util.nvl(pktDtl.getValue("cts"),"0").trim();
         String curquot=util.nvl(pktDtl.getValue("quot"),"0").trim();  
          float value = Float.parseFloat(curcts)*Float.parseFloat(curquot);
         pktDtl.setValue("USDVAL",String.valueOf(value));
         pktDtl.setValue("AMT", String.valueOf(value));
     }
        stockList.put(stkIdn, pktDtl);
        gtMgr.setValue(lstNme, stockList);
           return null;
    }
    public ActionForward dfgetpcs(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    String valcts = util.nvl(req.getParameter("cts"));
    String stkIdn = util.nvl(req.getParameter("stkIdn"));
        StringBuffer sb = new StringBuffer();
        String qty="";
        String getloyaltyDis = "select DF_GET_PCS(?, ?) qty from dual ";
        ArrayList params = new ArrayList();
        params.add(stkIdn);
        params.add(valcts);
      ArrayList outLst = db.execSqlLst("DfGetPcs", getloyaltyDis, params);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
        if(rs.next()){
            qty=util.nvl(rs.getString("qty"),"0");   
        }
        } catch (SQLException e) {
        }
        rs.close();
        pst.close();
        
    String upGt = "update gt_srch_rslt set qty = ? where stk_idn = ? ";
    params = new ArrayList();
    params.add(qty);
    params.add(stkIdn);
    int ct = db.execUpd("updatGt", upGt, params);
        sb.append("<qty>");
        sb.append("<qtyval>"+qty+"</qtyval>");
        sb.append("</qty>");
    res.setContentType("text/xml"); 
    res.setHeader("Cache-Control", "no-cache"); 
    res.getWriter().write("<qtys><qty>"+sb.toString()+"</qty></qtys>");
    return null;
    }
    
    public ActionForward loyaltyDis(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb = new StringBuffer();
        String nmeId = util.nvl(req.getParameter("nameIdn"));
        String vlu = util.nvl(req.getParameter("vlu"));
        
        String getloyaltyDis = "select LOYALTY_PKG.GET_SALES_DIS(?, ?) dis from dual ";
        ArrayList params = new ArrayList();
        params.add(nmeId);
        params.add(vlu);
      ArrayList outLst = db.execSqlLst("loyaltyDis", getloyaltyDis, params);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
        if(rs.next()){
            sb.append("<loyalty>");
            sb.append("<loyaltydis>"+util.nvl(rs.getString("dis"),"0") +"</loyaltydis>");
            sb.append("</loyalty>");
        }
        } catch (SQLException e) {
        }
        rs.close();
        pst.close();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<loyaltys>"+sb.toString()+"</loyaltys>");
        return null;
    }
    
  public ActionForward ContDtl(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    StringBuffer sb = new StringBuffer();
    String bryId = req.getParameter("bryId");
    String contactSrch = " select form_url_encode(eml) eml,mbl,ofc, form_url_encode(byr.get_nm(emp_idn)) emp from nme_cntct_v where nme_id= ? ";
      ArrayList ary = new ArrayList();
      ary.add(bryId);
    ArrayList outLst = db.execSqlLst("dmdSrch",contactSrch, ary);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
    
            while(rs.next()){
            sb.append("<ContDtl>");
            sb.append("<eml>"+util.nvl(rs.getString("eml"),"NA") +"</eml>");
            sb.append("<mbl>"+util.nvl(rs.getString("mbl"),"NA") +"</mbl>");
            sb.append("<ofc>"+util.nvl(rs.getString("ofc"),"NA") +"</ofc>");
            sb.append("<emp>"+util.nvl(rs.getString("emp"),"NA") +"</emp>");
            sb.append("</ContDtl>");
            }
            rs.close();
            pst.close();
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            res.getWriter().write("<ContDtls>"+sb.toString()+"</ContDtls>");
     
    return null;
      
  }
    public ActionForward locationDlvIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    StringBuffer sb = new StringBuffer();
    String nmeId = req.getParameter("nameIdn");
    String loc = req.getParameter("loc");
    ArrayList ary = new ArrayList();
    ary.add(nmeId);
    ary.add(loc);
    String sql = "Select distinct idn,dte From DLV_LOC_V where stt='IS' and nme_idn=? and loc=? order by dte";
      ArrayList outLst = db.execSqlLst("location Dlv memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<memo>");
    sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
    sb.append("<dte>"+util.nvl(rs.getString("dte"),"") +"</dte>");
    sb.append("</memo>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>"+sb.toString()+"</memos>");
    return null;
    }
    
    public ActionForward invTermsDtls(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    StringBuffer sb = new StringBuffer();
    String nmeRelId = util.nvl(req.getParameter("relIdn"));
    ArrayList ary = new ArrayList();
    ary.add(nmeRelId);
    String sql = "select   form_url_encode(byr.get_nm(mbrk1_idn)) brk1  , mbrk1_comm , mbrk1_paid , form_url_encode(byr.get_nm(mbrk2_idn)) brk2  ,mbrk2_comm , mbrk2_paid, form_url_encode(byr.get_nm(mbrk3_idn)) brk3  ,\n" + 
    "mbrk3_comm , mbrk3_paid, form_url_encode(byr.get_nm(mbrk4_idn)) brk4  ,mbrk4_comm , mbrk4_paid ,\n" + 
    "byr.get_nm(aadat_idn) aaDat  , aadat_paid , aadat_comm  from nmerel where nmerel_idn = ?";
      ArrayList outLst = db.execSqlLst("termsDtls", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<dtl>");
        sb.append("<brk1>"+util.nvl(rs.getString("brk1"))+"</brk1>");
        sb.append("<brk2>"+util.nvl(rs.getString("brk2"))+"</brk2>");
        sb.append("<brk3>"+util.nvl(rs.getString("brk3"))+"</brk3>");
        sb.append("<brk4>"+util.nvl(rs.getString("brk4"))+"</brk4>");
        sb.append("<brk1comm>"+util.nvl(rs.getString("mbrk1_comm"),"N")+"</brk1comm>");
        sb.append("<brk2comm>"+util.nvl(rs.getString("mbrk2_comm"),"N")+"</brk2comm>");
        sb.append("<brk3comm>"+util.nvl(rs.getString("mbrk3_comm"),"N")+"</brk3comm>");
        sb.append("<brk4comm>"+util.nvl(rs.getString("mbrk4_comm"),"N")+"</brk4comm>");
        sb.append("<brk1paid>"+util.nvl(rs.getString("mbrk1_paid"))+"</brk1paid>");
        sb.append("<brk2paid>"+util.nvl(rs.getString("mbrk2_paid"))+"</brk2paid>");
        sb.append("<brk3paid>"+util.nvl(rs.getString("mbrk3_paid"))+"</brk3paid>");
        sb.append("<brk4paid>"+util.nvl(rs.getString("mbrk4_paid"))+"</brk4paid>");
        sb.append("<aaDat>"+util.nvl(rs.getString("aaDat"))+"</aaDat>");
        sb.append("<aadatpaid>"+util.nvl(rs.getString("aadat_paid"))+"</aadatpaid>");
        sb.append("<aadatcomm>"+util.nvl(rs.getString("aadat_comm"),"N")+"</aadatcomm>");
    sb.append("</dtl>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<dtls>"+sb.toString()+"</dtls>");
    return null;
    }
    
    public ActionForward DlvTotalLoc(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    StringBuffer sb = new StringBuffer();
    String stkIdn = req.getParameter("stkIdn");
    String typ = req.getParameter("typ");
    String select = req.getParameter("select");

    ArrayList dlvPktList = info.getDlvPktList();
    ArrayList recPktList = info.getRecPktList();
    ArrayList dummy = new ArrayList();

    if(typ.equals("RECIVE")){
    recPktList.add(stkIdn);
    }else{
    if(recPktList.indexOf(stkIdn)>-1)
    recPktList.remove(recPktList.indexOf(stkIdn));

    }

    if(typ.equals("DLV")){
    dlvPktList.add(stkIdn);
    }else{
    if(dlvPktList.indexOf(stkIdn)>-1)
    dlvPktList.remove(dlvPktList.indexOf(stkIdn));

    }
    if(select.equals("ALL")){
    String stkIdns = stkIdn.replaceFirst(",", "");
    String[] stkIdnsArr=stkIdns.split(",");
    stkIdns = "("+stkIdns+")";
    if(stkIdnsArr.length>=1){
    for (int i = 0; i < stkIdnsArr.length; i++){
    dummy.add(stkIdnsArr[i]);
    }
    if(dummy.size()>0){
    dlvPktList=new ArrayList();
    recPktList=new ArrayList();
    if(typ.equals("DLV")){
    dlvPktList.addAll(dummy);
    }else{
    recPktList.addAll(dummy);
    }
    }
    }
    }
    if(dlvPktList.size()>0){
    String stkIdns = dlvPktList.toString();
    stkIdns = stkIdns.replace('[','(');
    stkIdns =stkIdns.replace(']',')');
    String sqlVal = "select sum(a.qty) pcs, trunc(sum(trunc(a.cts,2)),2) cts "+
    " , trunc(((sum(nvl(a.fnl_sal, a.quot)*trunc(a.cts,2))*100/sum(nvl(b.rap_rte, 0)*trunc(a.cts,2))) - 100) ,2) avg_dis "+
    " , trunc(sum(nvl(a.fnl_sal,a.quot)*trunc(a.cts,2)),2) vlu "+
    " from dlv_dtl a, mstk b,mdlv c where a.mstk_idn = b.idn and c.idn = a.idn and a.mstk_idn in "+stkIdns+" and a.stt='DP'";
    ArrayList ary = new ArrayList();

      ArrayList outLst = db.execSqlLst("sqlVlu", sqlVal, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<memo>");
    sb.append("<typ>DLV</typ>");
    sb.append("<qty>"+util.nvl(rs.getString("pcs"),"0") +"</qty>");
    sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
    sb.append("<dis>"+util.nvl(rs.getString("avg_dis"),"0") +"</dis>");
    sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
    sb.append("</memo>");
    }
      rs.close();
      pst.close();
    }else{
    sb.append("<memo>");
    sb.append("<typ>DLV</typ>");
    sb.append("<qty>0</qty>");
    sb.append("<cts>0</cts>");
    sb.append("<dis>0</dis>");
    sb.append("<vlu>0</vlu>");
    sb.append("</memo>");
    }
   
    if(recPktList.size()>0){
    String stkIdns = recPktList.toString();
    stkIdns = stkIdns.replace('[','(');
    stkIdns =stkIdns.replace(']',')');
    String sqlVal = "select sum(a.qty) pcs, trunc(sum(trunc(a.cts,2)),2) cts "+
    "  , trunc(((sum(nvl(a.fnl_sal, a.quot)*trunc(a.cts,2))*100/sum(nvl(b.rap_rte, 0)*trunc(a.cts,2))) - 100) ,2) avg_dis "+
    " , trunc(sum(nvl(a.fnl_sal,a.quot)*trunc(a.cts,2)),2) vlu "+
    " from dlv_dtl a, mstk b,mdlv c where a.mstk_idn = b.idn and c.idn = a.idn and a.mstk_idn in "+stkIdns+" and a.stt='DP'";
    ArrayList ary = new ArrayList();

   ArrayList outLst = db.execSqlLst("sqlVlu", sqlVal, ary);
    PreparedStatement  pst = (PreparedStatement)outLst.get(0);
  ResultSet  rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<memo>");
    sb.append("<typ>RECIVE</typ>");
    sb.append("<qty>"+util.nvl(rs.getString("pcs"),"0") +"</qty>");
    sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
    sb.append("<dis>"+util.nvl(rs.getString("avg_dis"),"0") +"</dis>");
    sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
  
    sb.append("</memo>");
    }
      rs.close();
      pst.close();
    }else{
    sb.append("<memo>");
    sb.append("<typ>RECIVE</typ>");
    sb.append("<qty>0</qty>");
    sb.append("<cts>0</cts>");
    sb.append("<dis>0</dis>");
    sb.append("<vlu>0</vlu>");
    sb.append("</memo>");
    }
    if(dlvPktList==null)
    dlvPktList=new ArrayList();
    if(recPktList==null)
    recPktList=new ArrayList();
    info.setDlvPktList(dlvPktList);
    info.setRecPktList(recPktList);
    
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>"+sb.toString()+"</memos>");
    return null;
    }


    public ActionForward charges(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
    StringBuffer sb = new StringBuffer();
    String vlu = util.nvl(req.getParameter("vlu"));
    String charge = util.nvl(req.getParameter("charge")).trim();
    String loop = util.nvl(req.getParameter("loop")).trim();
    String nmeId = util.nvl(req.getParameter("nmeIdn")).trim();
    if(chargesLst!=null){
    HashMap dtl=new HashMap();
    dtl=(HashMap)chargesLst.get(Integer.parseInt(loop));
    String dsc=(String)dtl.get("dsc");
    String flg=(String)dtl.get("flg");
    String typ=(String)dtl.get("typ");
    String fctr=(String)dtl.get("fctr");
    String fun=(String)dtl.get("fun");
    String field = "value("+typ+")";
    if(flg.equals("AUTO")){
    if(typ.equals("LOY")){
    String getloyaltyDis = "select LOYALTY_PKG.GET_SALES_DIS(?, ?) dis from dual ";
    ArrayList params = new ArrayList();
    params.add(nmeId);
    params.add(vlu);
      ArrayList outLst = db.execSqlLst("loyaltyDis", getloyaltyDis, params);
       PreparedStatement  pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
    try {
    if(rs.next()){
    sb.append("<charge>");
    String dis=util.nvl(rs.getString("dis"),"0");
    sb.append("<chargedis>"+(Float.parseFloat(dis)*Float.parseFloat(fctr)) +"</chargedis>");
    sb.append("</charge>");
    }
     rs.close();
        pst.close();
    } catch (SQLException e) {
    }
    }
    }else{
    if(typ.equals("MGMT")){
    sb.append("<charge>");
    sb.append("<chargedis>"+((Float.parseFloat(vlu)*Float.parseFloat(charge))/100)*Float.parseFloat(fctr) +"</chargedis>");
    sb.append("</charge>");
    }else{
    sb.append("<charge>");
    sb.append("<chargedis>"+(Float.parseFloat(charge)*Float.parseFloat(fctr)) +"</chargedis>");
    sb.append("</charge>");
    }
    }
    }
    
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<charges>"+sb.toString()+"</charges>");
    return null;
    }
    
    
    public ActionForward chargesSH(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
    ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
    StringBuffer sb = new StringBuffer();
    String vlu = util.nvl(req.getParameter("vlu"));
    String charge = util.nvl(req.getParameter("charge")).trim();
    String loop = util.nvl(req.getParameter("loop")).trim();
    String nmeId = util.nvl(req.getParameter("nmeIdn")).trim();
    String memoIdn = util.nvl(req.getParameter("memoIdn"));
    ArrayList params =new ArrayList();
    int l_Rap_Diff=0;
    float l_Diff=1;
    if(chargesLst!=null){
    ArrayList chargesLstSH= ((ArrayList)session.getAttribute("chargesLstSH") == null)?new ArrayList():(ArrayList)session.getAttribute("chargesLstSH"); 
    HashMap chargesLstDtlSH= ((HashMap)session.getAttribute("chargesLstDtlSH") == null)?new HashMap():(HashMap)session.getAttribute("chargesLstDtlSH"); 
    HashMap dtl=new HashMap();
    dtl=(HashMap)chargesLst.get(Integer.parseInt(loop));
    String dsc=(String)dtl.get("dsc");
    String flg=(String)dtl.get("flg");
    String typ=(String)dtl.get("typ");
    String fctr=(String)dtl.get("fctr");
    String fun=(String)dtl.get("fun");
    String field = "value("+typ+")";
    
    if(chargesLstSH.contains(typ)){
        if(charge.equals("0") || charge.equals("")){
        chargesLstSH.remove(typ);       
        }else{
        chargesLstDtlSH.put(typ, charge);
        }
    }else{
        if(!charge.equals("0") && !charge.equals("")){
        chargesLstSH.add(typ);    
        chargesLstDtlSH.put(typ, charge);
        }else{
        chargesLstSH.remove(typ);         
        }
    }
        session.setAttribute("chargesLstSH", chargesLstSH);
        session.setAttribute("chargesLstDtlSH", chargesLstDtlSH);
        
        

        if(chargesLstSH.size()>0) {
        if(chargesLstSH.contains("MGMT"))
        l_Rap_Diff=Integer.parseInt(String.valueOf(chargesLstDtlSH.get("MGMT"))); 
        
        for(int i=0;i<chargesLstSH.size();i++){
            String chargetyp=(String)chargesLstSH.get(i);
            if(!chargetyp.equals("MGMT"))
            l_Diff+=(Float.parseFloat(String.valueOf(chargesLstDtlSH.get(chargetyp)))/100);
        }
        String getSum = "select count(*) qty, sum(trunc(a.cts,2)) cts , trunc(sum(trunc(a.cts,2)*trunc((B.Rap_Rte*(100 - (100 - (Nvl(a.fnl_sal, A.Quot)/B.Rap_Rte)*100)- "+l_Rap_Diff+")/100)/"+l_Diff+"*1,2)), 2) vlu, trunc(((sum(trunc(a.cts,2)*(trunc((B.Rap_Rte*(100 - (100 - (Nvl(a.fnl_sal, A.Quot)/B.Rap_Rte)*100)- "+l_Rap_Diff+")/100)/"+l_Diff+"*1,2))/c.exh_rte) / sum(trunc(a.cts,2)*b.rap_rte))*100) - 100, 2) avg_dis ," +
        " trunc(((sum(trunc(a.cts,2)*trunc((B.Rap_Rte*(100 - (100 - (Nvl(a.fnl_sal, A.Quot)/B.Rap_Rte)*100)- "+l_Rap_Diff+")/100)/"+l_Diff+"*1,2)) / sum(trunc(a.cts,2))))) avg_Rte from jandtl a, mstk b , mjan c " +
        " where a.mstk_idn = b.idn and a.stt = 'AP' and c.idn = a.idn  and c.stt='IS' and b.stt not in('LB_PRI_AP')  " +
        " and a.idn in (" + memoIdn + ") and exists (select 1 from gt_pkt d where d.flg='SL' and b.idn=d.mstk_idn) ";


        params = new ArrayList();

          ArrayList outLst = db.execSqlLst(" Memo Info", getSum , params);
           PreparedStatement  pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);

        while(rs.next()){
        sb.append("<price>");
        sb.append("<avgDis>"+util.nvl(rs.getString("avg_dis"),"0")+"</avgDis>");
        sb.append("<avgRte>"+util.nvl(rs.getString("avg_Rte"),"0")+"</avgRte>");
        sb.append("<Qty>"+util.nvl(rs.getString("qty"),"0")+"</Qty>");
        sb.append("<Cts>"+util.nvl(rs.getString("cts"),"0")+"</Cts>");
        sb.append("<Vlu>"+util.nvl(rs.getString("vlu"),"0")+"</Vlu>");
        sb.append("</price>");
        }
            rs.close();
            pst.close();
        }
        }else {

                sb.append("<price>");
                sb.append("<avgDis>0</avgDis>");
                sb.append("<avgRte>0</avgRte>");
                sb.append("<Qty>0</Qty>");
                sb.append("<Cts>0</Cts>");
                sb.append("<Vlu>0</Vlu>");
                sb.append("</price>");

        }

        res.getWriter().write("<prices>"+sb.toString()+"</prices>");
    return null;
    }
    
    
    public ActionForward verifyvnm(ActionMapping mapping, ActionForm form,
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
    StringBuffer sb = new StringBuffer();
    String vnm = util.nvl(req.getParameter("vnm"));
    String sql = "select vnm from mstk where vnm='"+vnm+"'" ;
    ResultSet rs = db.execSql("Verify vnm", sql, new ArrayList());
    if(rs.next()){
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    response.getWriter().write("<message>yes</message>");
    }
    else{
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    response.getWriter().write("<message>No</message>");
    }

    rs.close();
    return null;
    }
    
  
    public ActionForward loadbulkprp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    ResultSet rs = null;
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    StringBuffer sb = new StringBuffer();
    HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
    String lprp = util.nvl(req.getParameter("lprp"));
    String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
    if(prpTyp.equals("C")){
    ArrayList prpValLst =(ArrayList)prp.get(lprp+"V");
    sb.append("<prp>");
    sb.append("<prpVal>C</prpVal>");
    sb.append("<prpValD>C</prpValD>");
    sb.append("</prp>");
    for(int p=0;p<prpValLst.size();p++){
    String prpVal = (String)prpValLst.get(p);
    sb.append("<prp>");
    sb.append("<prpVal>"+util.nvl(prpVal) +"</prpVal>");
    sb.append("<prpValD>"+util.nvl(prpVal) +"</prpValD>");
    sb.append("</prp>");
    }}else{
    sb.append("<prp>");
    sb.append("<prpVal>NT</prpVal>");
    sb.append("<prpValD>NT</prpValD>");
    sb.append("</prp>");
    }
    res.getWriter().write("<prps>" +sb.toString()+ "</prps>");
    return null;
    }
    
    public ActionForward loadprpmatch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    ResultSet rs = null;
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    StringBuffer sb = new StringBuffer();
        HashMap mprp = info.getSrchMprp();
        HashMap prp = info.getSrchPrp();
    String lprp = util.nvl(req.getParameter("lprp"));
    String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
    if(prpTyp.equals("C")){
    ArrayList prpValLst =(ArrayList)prp.get(lprp+"V");
    sb.append("<prp>");
    sb.append("<prpVal>C</prpVal>");
    sb.append("<prpValD>C</prpValD>");
    sb.append("</prp>");
    for(int p=0;p<prpValLst.size();p++){
    String prpVal = (String)prpValLst.get(p);
    sb.append("<prp>");
    sb.append("<prpVal>"+util.nvl(prpVal) +"</prpVal>");
    sb.append("<prpValD>"+util.nvl(prpVal) +"</prpValD>");
    sb.append("</prp>");
    }}else{
    sb.append("<prp>");
    sb.append("<prpVal>NT</prpVal>");
    sb.append("<prpValD>NT</prpValD>");
    sb.append("</prp>");
    }
    res.getWriter().write("<prps>" +sb.toString()+ "</prps>");
    return null;
    }
    public ActionForward getOffer(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    int fav = 0;
    int dmd = 0 ;
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    ArrayList ary = new ArrayList();

    StringBuffer sb = new StringBuffer();
    ArrayList trmsLst = new ArrayList();
    int byrId = info.getByrId();
    String stkIdn = req.getParameter("stkIdn");
    String dmdSrch = " select ofr_rte,ofr_dis,to_char(To_Dt,'DD-MM-YYYY') to_dte,rmk from web_bid_wl where byr_idn=? and mstk_idn=? and typ='BID' and stt='A'";
    ary = new ArrayList();
    ary.add(String.valueOf(byrId));
    ary.add(stkIdn);
      ArrayList outLst = db.execSqlLst("offer",dmdSrch, ary);
       PreparedStatement  pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
    try {
    if (rs.next()) {
    sb.append("<offer>");
    sb.append("<ofrrte>" + util.nvl(rs.getString("ofr_rte"),"0") +
    "</ofrrte>");
    sb.append("<ofrdis>" + util.nvl(rs.getString("ofr_dis"),"0") +
    "</ofrdis>");
    sb.append("<todte>" + util.nvl(rs.getString("to_dte"),"-") +
    "</todte>");
    sb.append("<comm>" + util.nvl(rs.getString("rmk"),"-") +
    "</comm>");
    sb.append("</offer>");
    }
    rs.close();
        pst.close();
    res.getWriter().write("<offers>" + sb.toString() + "</offers>");
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    } catch (IOException ioe) {
    // TODO: Add catch code
    ioe.printStackTrace();
    }

    return null;
    }
    
    public ActionForward GtList(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {

       HttpSession session = req.getSession(false);

       InfoMgr info = (InfoMgr)session.getAttribute("info");

       GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

    

       DBUtil util = new DBUtil();

       DBMgr db = new DBMgr();

       if(info!=null){

       db.setCon(info.getCon());

       util.setDb(db);

       util.setInfo(info);

       db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));

       util.setLogApplNm(info.getLoApplNm());

         String stkIdn = req.getParameter("stkIdn");

         String isChecked =  req.getParameter("stt");

         String memoTyp = req.getParameter("memoTyp");

         String lstNme =  req.getParameter("lstNme");
           String view =  util.nvl(req.getParameter("view"));
           String crdigit="2";
           if(view.equals("MIX") || view.equals("SMX"))
               crdigit="3";
         info.setMemoTypSel(memoTyp);
         if(lstNme==null)

             lstNme = (String)gtMgr.getValue("lstNme");

         HashMap dtlMap = new HashMap();

         String updSql = "";

         ArrayList ary = new ArrayList();

        

         ArrayList selectstkIdnLst = (ArrayList)gtMgr.getValue(lstNme+"_SEL");

         if(selectstkIdnLst==null)

           selectstkIdnLst = new ArrayList();

         HashMap stockList = (HashMap)gtMgr.getValue(lstNme);

           ArrayList stockIdnLst =new ArrayList();

            Set<String> keys = stockList.keySet();

                   for(String key: keys){

                  stockIdnLst.add(key);

                  }

        

         ArrayList sttList = new ArrayList();

         if(stkIdn.equals("ALL")){

         String memoStt = "select  b.chr_fr from mrule a , rule_dtl b where a.rule_idn = b.rule_idn and a.mdl='MEMO_ALW'  and b.dsc =?";

         ary.add(memoTyp);

            ArrayList outLst = db.execSqlLst("memoStt",memoStt, ary);
             PreparedStatement  pst = (PreparedStatement)outLst.get(0);
            ResultSet  rs = (ResultSet)outLst.get(1);

          while(rs.next()){

            sttList.add(rs.getString("chr_fr"));

          }
            rs.close();
            pst.close();
             selectstkIdnLst = new ArrayList();

             if(isChecked.equals("true")){

       

             for(int i=0;i<stockIdnLst.size();i++){

               String stkIdn1 = (String)stockIdnLst.get(i);

                      GtPktDtl pktDtl = (GtPktDtl)stockList.get(stkIdn1);

                          String stt = pktDtl.getValue("stt1");

                 if(sttList.contains(stt)){
                     if(!selectstkIdnLst.contains(stkIdn1))
                            selectstkIdnLst.add(stkIdn1); 
                            
                 }

             }   

          

          

          }

          }else{
           String[] stkIdnLst = stkIdn.split("~");
             for(int i=0;i<stkIdnLst.length;i++){
                 String mstkIdn = util.nvl((String)stkIdnLst[i],"");
                 if(!mstkIdn.equals("")){
              if(isChecked.equals("true")){
            if(!selectstkIdnLst.contains(mstkIdn))
              selectstkIdnLst.add(mstkIdn);

              }else{

              selectstkIdnLst.remove(mstkIdn);

              }
             }
             }

          }

         dtlMap.put("selIdnLst",selectstkIdnLst);

         dtlMap.put("pktDtl", stockList);

         dtlMap.put("flg", "M");
         

         HashMap ttlMap = util.getTTL(dtlMap);

        stockIdnLst.removeAll(selectstkIdnLst);

         dtlMap.put("selIdnLst",stockIdnLst);

         dtlMap.put("flg", "R");

         dtlMap.put("TTLMAP",ttlMap);

         ttlMap = util.getTTL(dtlMap);

         gtMgr.setValue(lstNme+"_SEL",selectstkIdnLst);

        

        

         StringBuffer sb = new StringBuffer();

         sb.append("<value>");
           sb.append("<cnt>"+util.nvl((String)ttlMap.get("MC"),"0") +"</cnt>");

         sb.append("<qty>"+util.nvl((String)ttlMap.get("MQ"),"0") +"</qty>");

         sb.append("<cts>"+util.nvl((String)ttlMap.get("MW"),"0") +"</cts>");

         sb.append("<vlu>"+util.nvl((String)ttlMap.get("MV"),"0") +"</vlu>");

         sb.append("<avg>"+util.nvl((String)ttlMap.get("MA"),"0") +"</avg>");

         sb.append("<dis>"+util.nvl((String)ttlMap.get("MD"),"0") +"</dis>");

         sb.append("<base>"+util.nvl((String)ttlMap.get("MB"),"0") +"</base>");

         sb.append("<rapvlu>"+util.nvl((String)ttlMap.get("MR"),"0") +"</rapvlu>");

         sb.append("<cvlu>"+util.nvl((String)ttlMap.get("MCV"),"0") +"</cvlu>");

         sb.append("<cavg>"+util.nvl((String)ttlMap.get("MCA"),"0") +"</cavg>");

         sb.append("<cdis>"+util.nvl((String)ttlMap.get("MCD"),"0") +"</cdis>");

         sb.append("<avgrap>"+util.nvl((String)ttlMap.get("MRA"),"0") +"</avgrap>");
           sb.append("<regcnt>"+util.nvl((String)ttlMap.get("RC"),"0") +"</regcnt>");

         sb.append("<regqty>"+util.nvl((String)ttlMap.get("RQ"),"0") +"</regqty>");

         sb.append("<regcts>"+util.nvl((String)ttlMap.get("RW"),"0") +"</regcts>");

         sb.append("<regvlu>"+util.nvl((String)ttlMap.get("RV"),"0") +"</regvlu>");

         sb.append("<regavg>"+util.nvl((String)ttlMap.get("RA"),"0") +"</regavg>");

         sb.append("<regdis>"+util.nvl((String)ttlMap.get("RD"),"0") +"</regdis>");

         sb.append("<regbase>"+util.nvl((String)ttlMap.get("RB"),"0") +"</regbase>");

         sb.append("<regrapvlu>"+util.nvl((String)ttlMap.get("RR"),"0") +"</regrapvlu>");

         sb.append("<regcvlu>"+util.nvl((String)ttlMap.get("RCV"),"0") +"</regcvlu>");

         sb.append("<regcavg>"+util.nvl((String)ttlMap.get("RCA"),"0") +"</regcavg>");

         sb.append("<regcdis>"+util.nvl((String)ttlMap.get("RCD"),"0") +"</regcdis>");
         
         sb.append("<regavgrap>"+util.nvl((String)ttlMap.get("RRA"),"0") +"</regavgrap>");

         sb.append("<netprc>"+util.nvl((String)ttlMap.get("NETPRCM"),"0") +"</netprc>");

         sb.append("<netdis>"+util.nvl((String)ttlMap.get("NETDISM"),"0") +"</netdis>");

         sb.append("<netvlu>"+util.nvl((String)ttlMap.get("NETVALM"),"0") +"</netvlu>");

         sb.append("<diff>"+util.nvl((String)ttlMap.get("DIFFM"),"0") +"</diff>");

         sb.append("<ctg>"+util.nvl((String)ttlMap.get("CTGM"),"0") +"</ctg>");

         sb.append("<loyvlu>"+util.nvl((String)ttlMap.get("LOYVLUM"),"0") +"</loyvlu>");

         sb.append("<memvlu>"+util.nvl((String)ttlMap.get("MEMVLUM"),"0") +"</memvlu>");

         sb.append("<loyPct>"+util.nvl((String)ttlMap.get("LOYPCTM"),"0") +"</loyPct>");
         sb.append("<webPct>"+util.nvl((String)ttlMap.get("WEBPCTM"),"0") +"</webPct>");
         sb.append("<webVlu>"+util.nvl((String)ttlMap.get("WEBVLUM"),"0") +"</webVlu>");

         sb.append("</value>");

         

         res.setContentType("text/xml");

         res.setHeader("Cache-Control", "no-cache");

         res.getWriter().write("<values>"+sb.toString()+"</values>");

        

       }

         return null;

     }
    
  public ActionForward refScrh(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String lprp = util.nvl(req.getParameter("lprp"));
        String valFm = util.nvl(req.getParameter("valFm"));
        String valTo = util.nvl(req.getParameter("valTo"));
        String typ= util.nvl(req.getParameter("typ"));
        String isChecked = util.nvl(req.getParameter("isChecked"));
        String lstNme = (String)gtMgr.getValue("lstNme");
            
        ArrayList prpValList = new ArrayList();
        HashMap prp = info.getPrp();
        
         HashMap refPktDtl = (HashMap)gtMgr.getValue(lstNme+"_REF");
           if(typ.equals("C")){
            prpValList = (ArrayList)refPktDtl.get(lprp);
            if(isChecked.equals("true")){
            if(valFm.equals("ALL")){
              HashMap srchPktDtl =  (HashMap)gtMgr.getValue(lstNme+"_SRCHLST");
              prpValList = (ArrayList)srchPktDtl.get(lprp);
            }else
               prpValList.add(valFm);
            }else{
              if(valFm.equals("ALL"))
                prpValList = new ArrayList();
            else
               prpValList.remove(valFm);
            }
             
         }else{
            //  valList = new ArrayList();
             prpValList.add(valFm);
              prpValList.add(valTo);


         }
        refPktDtl.put(lprp,prpValList);
        gtMgr.setValue(lstNme+"_REF", refPktDtl);
        return null;
    }
  public ActionForward loadWL(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
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
     ArrayList ary = new ArrayList();
    
     StringBuffer sb = new StringBuffer();
   

     String rlnId = req.getParameter("term");
     
     String grpNme = "select distinct a.grp_nme from web_bid_wl a, mstk b , web_usrs c " + 
     "        where a.mstk_idn = b.idn " + 
     "        and a.usr_idn = c.usr_id and c.rel_idn = ? " + 
     "        and trunc(nvl(a.to_dt, sysdate)) >= trunc(sysdate) " + 
     "        and a.typ = 'WL' and a.stt = 'A' " + 
     "        and b.stt in ('MKAV' ,'MKIS') " + 
     "        and b.pkt_ty = 'NR' and grp_nme is not null order by a.grp_nme";
     
     ary = new ArrayList();
     
     ary.add(rlnId);
     ArrayList outLst = db.execSqlLst("dmd", grpNme, ary);
      PreparedStatement  pst = (PreparedStatement)outLst.get(0);
     ResultSet  rs = (ResultSet)outLst.get(1);
       try {
           while (rs.next()) {
            
              
               sb.append("<grpNme><grp>" + util.nvl(rs.getString(1).toUpperCase()) +
                         "</grp></grpNme>");
              

           }
               rs.close();
           pst.close();
               res.getWriter().write("<grpNmes>" + sb.toString() + "</grpNmes>");
       } catch (SQLException sqle) {
           // TODO: Add catch code
           sqle.printStackTrace();
       } catch (IOException ioe) {
           // TODO: Add catch code
           ioe.printStackTrace();
       }
      return null;
   }
    
  public ActionForward usrLoc(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
  StringBuffer sb = new StringBuffer();

  String loc = util.nvl(req.getParameter("Loc"));
  String typ = util.nvl(req.getParameter("typ"));
 
  ArrayList ary = new ArrayList();
  ary.add(loc);
  String sql = "select distinct d.nme_idn , form_url_encode(d.nme) nme  from df_users a , nme_dtl b ,  mjan c  , jandtl e, nme_v d\n" + 
  "where a.nme_idn = b.nme_idn and b.mprp='OFFLOC' and b.txt=? and c.idn = e.idn and e.stt ='AP'\n" + 
  "and c.aud_created_by=a.usr and c.stt='IS' and c.typ like '%AP' and c.typ <> 'BAP' and c.nme_idn = d.nme_idn order by form_url_encode(d.nme) " ;
  if(typ.equals("DLV")){
    sql = "select distinct d.nme_idn , form_url_encode(d.nme) nme  from df_users a , nme_dtl b ,  msal c  , jansal e, nme_v d\n" + 
      "where a.nme_idn = b.nme_idn and b.mprp='OFFLOC' and b.txt=? and c.idn = e.idn and e.stt ='SL'\n" + 
      "and c.aud_created_by=a.usr and c.stt='IS' and c.typ='SL'  and c.nme_idn = d.nme_idn order by form_url_encode(d.nme) " ;

  }

    ArrayList outLst = db.execSqlLst("location", sql, ary);
     PreparedStatement  pst = (PreparedStatement)outLst.get(0);
    ResultSet  rs = (ResultSet)outLst.get(1);
  while(rs.next()){
  sb.append("<nme>");
  sb.append("<id>"+util.nvl(rs.getString("nme_idn"),"0") +"</id>");
  sb.append("<buyNme>"+util.nvl(rs.getString("nme"),"") +"</buyNme>");
  sb.append("</nme>");
  }
  rs.close();
  pst.close();
  res.setContentType("text/xml");
  res.setHeader("Cache-Control", "no-cache");
  res.getWriter().write("<nmes>"+sb.toString()+"</nmes>");
  return null;
  }
  
  public ActionForward loadBrcTrm(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
    ArrayList ary = new ArrayList();
    StringBuffer sb = new StringBuffer();
    String nmeIdn = util.nvl(req.getParameter("bryId"));
    ary.add(nmeIdn);
    String brcTermsSrch = "select distinct a.nmerel_idn , form_url_encode(b.dtls) dtls from brc_mdlv a ,  nme_rel_all_v b where a.nmerel_idn = b.nmerel_idn  and a.nme_idn=?" ;
    ArrayList outLst = db.execSqlLst("favSrch",brcTermsSrch, ary);
     PreparedStatement  pst = (PreparedStatement)outLst.get(0);
    ResultSet  rs = (ResultSet)outLst.get(1);
    while(rs.next()){
     
        sb.append("<trm>");
        sb.append("<trmDtl>"+util.nvl(rs.getString("dtls").toLowerCase()) +"</trmDtl>");
        sb.append("<relId>"+util.nvl(rs.getString("nmerel_idn")) +"</relId>");
        sb.append("</trm>");
    }
     rs.close();
     pst.close();
      res.getWriter().write("<trms>" +sb.toString()+ "</trms>");
         return null;
  }
  
  public ActionForward brnchIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
    ArrayList ary = new ArrayList();
    StringBuffer sb = new StringBuffer();
    String nmeIdn = util.nvl(req.getParameter("nameIdn"));
    String rlnId = util.nvl(req.getParameter("rlnId"));
    ary.add(nmeIdn);
    ary.add(rlnId);
    
    String brcTermsSrch = "select distinct a.dlv_idn , to_char(a.dte, 'dd-Mon HH24:mi') dte ,b.qty,  sum(b.cts) cts, trunc(sum(b.cts*nvl(b.fnl_sal, b.quot)), 2) vlu " + 
    "from brc_mdlv a , brc_dlv_dtl b where a.nme_idn= ?  and a.nmerel_idn = ? and a.idn = b.idn and b.stt not in ('RT','DLV','AV','IS','CL') group by a.dlv_idn, to_char(a.dte, 'dd-Mon HH24:mi'), b.qty";
    ArrayList outLst = db.execSqlLst("favSrch",brcTermsSrch, ary);
     PreparedStatement  pst = (PreparedStatement)outLst.get(0);
    ResultSet  rs = (ResultSet)outLst.get(1);
      while(rs.next()){
      sb.append("<memo>");
      sb.append("<id>"+util.nvl(rs.getString("dlv_idn"),"0") +"</id>");
      sb.append("<dte>"+util.nvl(rs.getString("dte"),"") +"</dte>");
        sb.append("<qty>"+util.nvl(rs.getString("qty"),"0") +"</qty>");
        sb.append("<cts>"+util.nvl(rs.getString("cts"),"") +"</cts>");
        sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"") +"</vlu>");
      sb.append("</memo>");
      }
      rs.close();
      pst.close();
      res.setContentType("text/xml");
      res.setHeader("Cache-Control", "no-cache");
      res.getWriter().write("<memos>"+sb.toString()+"</memos>");
      return null;
  }
  
  public ActionForward BrcTotal(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      StringBuffer sb = new StringBuffer();
      String stkIdn = util.nvl(req.getParameter("stkIdn"));
      String typ = util.nvl(req.getParameter("typ"));
      String memoId = util.nvl(req.getParameter("memoId"));
     stkIdn = "("+stkIdn.substring(1, stkIdn.length())+")";
         
      String sqlVal = "select sum(a.qty) pcs, trunc(sum(trunc(a.cts,2)),2) cts "+
         " , trunc(((sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts,2))))) avg_Rte , trunc(((sum(nvl(a.fnl_sal, a.quot)*trunc(a.cts,2))*100/sum(nvl(b.rap_rte, 0)*trunc(a.cts,2))) - 100) ,2) avg_dis "+
         " , trunc(sum(nvl(a.fnl_sal,a.quot)*trunc(a.cts,2)),2) vlu "+
         " from brc_dlv_dtl a, mstk b , brc_mdlv c where a.mstk_idn = b.idn and a.idn = c.idn  and a.mstk_idn in "+stkIdn+" and  a.dlv_idn in ("+memoId+") and a.stt not in  ('DLV','RT','AV','IS','CL')";
      ArrayList ary = new ArrayList();
     
    ArrayList outLst = db.execSqlLst("sqlVlu", sqlVal, ary);
     PreparedStatement  pst = (PreparedStatement)outLst.get(0);
    ResultSet  rs = (ResultSet)outLst.get(1);
       boolean isVal = false;
      while(rs.next()){
          sb.append("<memo>");
            sb.append("<typ>"+typ+"</typ>");
          sb.append("<qty>"+util.nvl(rs.getString("pcs"),"0") +"</qty>");
          sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
          sb.append("<dis>"+util.nvl(rs.getString("avg_dis"),"0") +"</dis>");
          sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
            sb.append("<prc>"+util.nvl(rs.getString("avg_Rte"),"0") +"</prc>");
          sb.append("</memo>");
          isVal=true;
          }
    rs.close();
    pst.close();
    
      if(!isVal){
        sb.append("<memo>");
        sb.append("<typ>"+typ+"</typ>");
        sb.append("<qty>0</qty>");
        sb.append("<cts>0</cts>");
        sb.append("<dis>0</dis>");
        sb.append("<vlu>0</vlu>");
        sb.append("</memo>");
        
      }
         
      
      
   
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      res.getWriter().write("<memos>"+sb.toString()+"</memos>");
      return null;
  }
  public ActionForward BranchIdnLst(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
   
    StringBuffer sb = new StringBuffer();
    String stkIdn = util.nvl(req.getParameter("stkIdn"));
    String idn = util.nvl(req.getParameter("Idn"));
    String isChecked = util.nvl(req.getParameter("isChecked"));
    ArrayList branchIdnLst = info.getBrnchDlvList();
    ArrayList branchStkIdnLst = info.getBrnchStkIdnLst();
    if(isChecked.equals("true")){
    if(!branchIdnLst.contains(idn))
        branchIdnLst.add(idn);
    if(!branchStkIdnLst.contains(stkIdn))
          branchStkIdnLst.add(stkIdn);
    }else{
      branchIdnLst.remove(idn);
      branchStkIdnLst.remove(stkIdn);
    }
    info.setBrnchDlvList(branchIdnLst);
    info.setBrnchStkIdnLst(branchStkIdnLst);
    
    return null;
  }
  
  public ActionForward BranchTtl(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    ArrayList branchIdnLst = info.getBrnchDlvList();
    ArrayList branchStkIdnLst = info.getBrnchStkIdnLst();
    String stkIdns = branchStkIdnLst.toString();
    stkIdns = stkIdns.replace('[','(');
    stkIdns =stkIdns.replace(']',')');
    StringBuffer sb = new StringBuffer();
    String Idns = branchIdnLst.toString();
    Idns = Idns.replace('[','(');
    Idns = Idns.replace(']',')');
    boolean isVal = false;
    if(branchIdnLst.size() > 0 && branchStkIdnLst.size() >0){
    String sqlVal = "select sum(a.qty) pcs, trunc(sum(trunc(a.cts,2)),2) cts "+
       " , trunc(((sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts,2))))) avg_Rte , trunc(((sum(nvl(a.fnl_sal, a.quot)*trunc(a.cts,2))*100/sum(nvl(b.rap_rte, 0)*trunc(a.cts,2))) - 100) ,2) avg_dis "+
       " , trunc(sum(nvl(a.fnl_sal,a.quot)*trunc(a.cts,2)),2) vlu "+
       " from brc_dlv_dtl a, mstk b , brc_mdlv c where a.mstk_idn = b.idn and a.idn = c.idn  and a.mstk_idn in "+stkIdns+" and  a.idn in "+Idns;
    ArrayList ary = new ArrayList();
    
      ArrayList outLst = db.execSqlLst("sqlVlu", sqlVal, ary);
       PreparedStatement  pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
    
    while(rs.next()){
        sb.append("<ttl>");
        sb.append("<qty>"+util.nvl(rs.getString("pcs"),"0") +"</qty>");
        sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
        sb.append("<dis>"+util.nvl(rs.getString("avg_dis"),"0") +"</dis>");
        sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
          sb.append("<prc>"+util.nvl(rs.getString("avg_Rte"),"0") +"</prc>");
        sb.append("</ttl>");
        isVal=true;
        }
      rs.close();
      pst.close();
      if(!isVal){
        sb.append("<ttl>");
        sb.append("<qty>0</qty>");
        sb.append("<cts>0</cts>");
        sb.append("<dis>0</dis>");
        sb.append("<vlu>0</vlu>");
        sb.append("<prc>0</prc>");
        sb.append("</ttl>");
        
      }
    }
        
    
   
    if(!isVal){
      sb.append("<ttl>");
      sb.append("<qty>0</qty>");
      sb.append("<cts>0</cts>");
      sb.append("<dis>0</dis>");
      sb.append("<vlu>0</vlu>");
      sb.append("<prc>0</prc>");
      sb.append("</ttl>");
      
    }
    res.setContentType("text/xml"); 
    res.setHeader("Cache-Control", "no-cache"); 
    res.getWriter().write("<ttls>"+sb.toString()+"</ttls>");
    return null;
    
  }
    public ActionForward selectPrint(ActionMapping mapping, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse response) throws Exception {
        
          HttpSession session = req.getSession(false);
              InfoMgr info = (InfoMgr)session.getAttribute("info");
              GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

              DBUtil util = new DBUtil();
              DBMgr db = new DBMgr();
              db.setCon(info.getCon());
              util.setDb(db);
              util.setInfo(info);
              db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
              util.setLogApplNm(info.getLoApplNm());
          String lstNme = (String)gtMgr.getValue("lstNme");
          ArrayList selectstkIdnLst= ((ArrayList)gtMgr.getValue(lstNme+"_SEL") == null)?new ArrayList():(ArrayList)gtMgr.getValue(lstNme+"_SEL");
          int selectstkIdnLstsz=selectstkIdnLst.size();
          
          if(selectstkIdnLstsz==0)
          selectstkIdnLst= ((ArrayList)gtMgr.getValue(lstNme+"_ALLLST") == null)?new ArrayList():(ArrayList)gtMgr.getValue(lstNme+"_ALLLST");
          
          HashMap pktDtls = (HashMap)gtMgr.getValue(lstNme);
          ArrayList ary = new ArrayList();
          boolean selectionExist = false;
          int memoId  = 0;
          
          if(selectstkIdnLst.size()> 0){
          if(selectstkIdnLst!=null && selectstkIdnLst.size()>0){
              int ct = db.execUpd("delete gt","Delete from gt_srch_rslt", new ArrayList());
            for(int i=0;i<selectstkIdnLst.size();i++){
              String mstkIdn = (String)selectstkIdnLst.get(i);
              GtPktDtl pktDtl = (GtPktDtl)pktDtls.get(mstkIdn);
              String gtInsrt="Insert into gt_srch_rslt ( stk_Idn, qty, cts,rap_rte, cmp, quot, rap_dis,flg) select ? ,?,?,?,? ,?,?,? from dual";
              ary = new ArrayList();
              ary.add(mstkIdn);
              ary.add(pktDtl.getValue("qty"));
              ary.add(pktDtl.getValue("cts"));
              ary.add(pktDtl.getValue("rap_rte"));
              ary.add(pktDtl.getValue("cmp"));
              ary.add(pktDtl.getValue("quot"));
              ary.add(pktDtl.getValue("r_dis"));
              ary.add("M");
              ct =db.execUpd("gtInsert", gtInsrt, ary);
            }
          }  
          
          ary = new ArrayList();
          ary.add(String.valueOf(info.getByrId()));
          ary.add(String.valueOf(info.getRlnId()));
          ary.add("RT");
          ary.add("Z");
          ary.add("NN");
          ary.add(String.valueOf(info.getXrt()));
          ArrayList out = new ArrayList();
          out.add("I");
          try {
              CallableStatement cst = null;
              cst = db.execCall("MKE_HDR ", "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ?  , pTyp => ? ,pflg => ? , pXrt => ? , pMemoIdn => ?)",
              ary,out);
              memoId = cst.getInt(ary.size()+1);
            cst.close();
            cst=null;
              if(memoId > 0)
              selectionExist = true;    
              ary = new ArrayList();
              ary.add(Integer.toString(memoId));
              ary.add("RT");
              ary.add("M");
              int ct1 = db.execCall("pop Memo from gt", "MEMO_PKG.POP_MEMO_FRM_GT(pMemoIdn => ? , pStt => ? , pFlg => ?)", ary);

          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
     //   }
        }
        if(selectionExist){  
        response.setContentType("text/xml"); 
        response.setHeader("Cache-Control", "no-cache"); 
        response.getWriter().write("<message>"+memoId+"</message>");
        } else {
        response.setContentType("text/xml"); 
        response.setHeader("Cache-Control", "no-cache"); 
        response.getWriter().write("<message>NOSELECTION</message>");      
        }  
      return null;
      }
    public ActionForward populateSalePerson(ActionMapping am, ActionForm a, HttpServletRequest req,
                                                      HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
          StringBuffer sb = new StringBuffer();
          String grpNameIdn = req.getParameter("grpNameIdn");
          ArrayList ary = new ArrayList();
          int ctr = 0;
          String sql = "select nme_idn, nme from nme_v a where grp_nme_idn = ? and typ = 'EMPLOYEE'  order by nme";
          ary.add(grpNameIdn);
      ArrayList outLst = db.execSqlLst("column nme", sql, ary);
       PreparedStatement  pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
          while (rs.next()) {
              ++ctr;
              String nme = util.nvl(rs.getString(2), "0").replaceAll("&", "&amp;");
              sb.append("<nmes>");
              sb.append("<nmeid>" + util.nvl(rs.getString(1)) + "</nmeid>");
              sb.append("<nme>" + nme + "</nme>");
              sb.append("</nmes>");
          }
          rs.close();
          pst.close();
          res.setContentType("text/xml");
          res.setHeader("Cache-Control", "no-cache");
          res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");

          return null;
    }
    
    public ActionForward currentTm(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
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
        boolean isPnt = true;
        try {
        StringBuffer sb = new StringBuffer();
        String maxOffer =" Select to_char(sysdate, 'dd-Mon HH24:mi') displaytm,to_char(sysdate, 'dd-MON-rrrr HH24:mi') inserttm\n" + 
        "         from dual";
          ArrayList outLst = db.execSqlLst("maxOffer", maxOffer, new ArrayList());
           PreparedStatement  pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        isPnt=false;
        sb.append("<timesby>");
        sb.append("<displaytm>"+util.nvl(rs.getString("displaytm"),"0")+"</displaytm>");
        sb.append("<inserttm>"+util.nvl(rs.getString("inserttm"),"")+"</inserttm>");
        sb.append("</timesby>");
        }
        rs.close();
            pst.close();
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<times>" + sb.toString() + "</times>");
        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        } catch (IOException ioe) {
        // TODO: Add catch code
        ioe.printStackTrace();
        }
        return null;
        }
    
    public ActionForward stockUpd(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());


        try {
            
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
           ArrayList rapprpList = new ArrayList();
            rapprpList.add("SHAPE");
            rapprpList.add("SH");
            rapprpList.add("COL");
            rapprpList.add("CO");
            rapprpList.add("PU");
            rapprpList.add("CLR");
            rapprpList.add("CRTWT");



            String issueId =  util.nvl(req.getParameter("issueId"));
            String lprp = req.getParameter("lprp");
            String lprpVal = req.getParameter("lprpVal");
            String lab = req.getParameter("lab");
            String mstkIdn = req.getParameter("stkIdn");
            ArrayList ary = new ArrayList();
            ary.add(mstkIdn);
            ary.add(lprp);
            ary.add(lprpVal);
            ary.add(lab);

            String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?)";
            int ct = db.execCall("stockUpd", stockUpd, ary);
            String msg="";
            if(ct>0){
                msg=lprp+" Update To :"+lprpVal;
                if(rapprpList.contains(lprp)){
                 ArrayList ary1 = new ArrayList();
                 ary1.add(mstkIdn);
                 ct =  db.execCall("stockUpd","STK_RAP_UPD(pIdn => ?)", ary1);
                 if(lprp.equals("CRTWT"))
                 ct = db.execCall("SZ_CHG", "SZ_CHG(pktId => ?)", ary1);
                 ct = db.execCall("STK_SRT", "STK_SRT(Pid => ?)", ary1);
                }
                if(!issueId.equals("")){
                    ary = new ArrayList();
                    ary.add(issueId);
                    ary.add(mstkIdn);
                    ary.add(lprp);
                    ary.add(lprpVal);
                   ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
             }
                
             }else{
                msg=lprp+" Updatetion Failed.";
            }
            
            
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            res.getWriter().write("<msg>" + msg + "</msg>");


        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
          
        return null;
    }
    
    public ActionForward saveStockCriteria(ActionMapping am, ActionForm a, HttpServletRequest req, HttpServletResponse res) throws Exception {  
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
        String stt = util.nvl(req.getParameter("stt")).trim();
        String crt_idn = util.nvl(req.getParameter("crt_idn")).trim();
        String sql = "update stk_crt set stt = ? where crt_idn=?" ;
        ArrayList ary = new ArrayList();
        ary.add(stt);
        ary.add(crt_idn);
        int ct = db.execUpd("sql", sql, ary);
       return null;
    }
    
    
    public ActionForward getmemolimit(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      int fav = 0;
      int dmd = 0 ;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      ArrayList ary = new ArrayList();
      StringBuffer sb = new StringBuffer();
      String typ = util.nvl(req.getParameter("typ"));
      String favSrch = "select GET_MEMO_LMT(?,?,?) lmt,GET_TTL_MEMO_VLU(?,?,?) vlu from dual ";
      ary = new ArrayList();
      ary.add(typ);
      ary.add(String.valueOf(info.getByrId()));
      ary.add(String.valueOf(info.getRlnId()));
        ary.add(typ);
        ary.add(String.valueOf(info.getByrId()));
        ary.add(String.valueOf(info.getRlnId()));
      ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
       PreparedStatement  pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          fav=1;
          sb.append("<pkt>");
          sb.append("<lmt>"+util.nvl(rs.getString(1),"0").toLowerCase() +"</lmt>");
          sb.append("<vlu>"+util.nvl(rs.getString(2),"0").toLowerCase() +"</vlu>");
          sb.append("</pkt>");
      }
       if(fav==1)
       res.getWriter().write("<pkts>" +sb.toString()+ "</pkts>");
       rs.close();
       pst.close();
       return null;
    }
    
    public ActionForward getcreditLmt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      int fav = 0;
      int dmd = 0 ;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      ArrayList ary = new ArrayList();
      StringBuffer sb = new StringBuffer();
      String typ = util.nvl(req.getParameter("typ"));
      String favSrch = "select GET_CREDIT_LMT(?,?,?) lmt,GET_TTL_MEMO_VLU(?,?,?) vlu from dual ";
      ary = new ArrayList();
      ary.add(typ);
      ary.add(String.valueOf(info.getByrId()));
      ary.add(String.valueOf(info.getRlnId()));
        ary.add(typ);
        ary.add(String.valueOf(info.getByrId()));
        ary.add(String.valueOf(info.getRlnId()));
      ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
       PreparedStatement  pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          fav=1;
          sb.append("<pkt>");
          sb.append("<lmt>"+util.nvl(rs.getString(1),"0").toLowerCase() +"</lmt>");
          sb.append("<vlu>"+util.nvl(rs.getString(2),"0").toLowerCase() +"</vlu>");
          sb.append("</pkt>");
      }
       if(fav==1)
       res.getWriter().write("<pkts>" +sb.toString()+ "</pkts>");
       rs.close();
       pst.close();
       return null;
    }
    public ActionForward getgrpcompanyLink(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
        try {
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            String byrId =  util.nvl(req.getParameter("byrId"));
            ArrayList ary = new ArrayList();
            String msg="None";
            ary.add(byrId);
            String grp_co_nme ="select form_url_encode(grp_co_nme) grp_co_nme from nme_v where nme_idn=?";
              ArrayList outLst = db.execSqlLst("grp_co_nme", grp_co_nme, ary);
               PreparedStatement  pst = (PreparedStatement)outLst.get(0);
              ResultSet  rs = (ResultSet)outLst.get(1);
            while(rs.next()){
            msg=util.nvl(rs.getString("grp_co_nme"),"0");
            }
            rs.close();
            pst.close();
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            res.getWriter().write("<msg>" + msg + "</msg>");
            } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
            } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
            }
          
        return null;
    }
    
    public ActionForward activeSearch(ActionMapping am, ActionForm a, HttpServletRequest req, HttpServletResponse res)
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
        StringBuffer sb      = new StringBuffer();
       String lprp = util.nvl(req.getParameter("lprp"));
       HashMap prp = info.getPrp();
       ArrayList prpKeyLst = (ArrayList)prp.get(lprp+"S");
        ArrayList prpValLst = (ArrayList)prp.get(lprp+"V");
        ArrayList prpPrtLst = (ArrayList)prp.get(lprp+"P");
        if(prpKeyLst!=null && prpKeyLst.size() > 0){
            for(int i =0;i<prpKeyLst.size();i++){
                    String key = util.nvl((String)prpKeyLst.get(i));
                    String value  = util.nvl((String)prpValLst.get(i));
                    String print  = util.nvl((String)prpPrtLst.get(i));
                    sb.append("<column><key>"+key+"</key>"); 
                    sb.append("<value>"+value+"</value>");
                    sb.append("<print>"+(print).replaceAll("&", "%26")+"</print></column>");
                }
            
        }else{
                sb.append("<column><key>0</key>"); 
                sb.append("<value>No Result Found</value><print>No Result Found</print></column>");
            }
        String str= "<columns>" + sb.toString() + "</columns>";
     
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write(str);

        return null;
    }
    
    public AjaxSrchAction() {
        super();
    }
}
