package ft.com.inward;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.TrmsDao;

import java.io.IOException;

import java.net.URLEncoder;

import java.sql.CallableStatement;
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
public class AjaxIssueAction extends DispatchAction {
    public AjaxIssueAction() {
        super();
    }
    
  public ActionForward issueDetail(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
      String issueIdn = req.getParameter("issueIdn");
      ResultSet rs = null;
      ArrayList ary = new ArrayList();
      String sqlVal =  "SELECT iss_id,  "+ 
        "    COUNT(*) qty, " + 
        "    SUM(a.cts) cts " + 
        "  FROM mstk a, " + 
        "    iss_rtn_dtl b " + 
        "  WHERE a.idn = b.iss_stk_idn " + 
        "  and iss_id=? " + 
        "  GROUP BY iss_id ";

       ary = new ArrayList();
       ary.add(issueIdn);
       rs = db.execSql("Issue details", sqlVal, ary);
        if(rs.next()){
          sb.append("<issue>");
          sb.append("<qty>"+util.nvl(rs.getString("qty"),"0") +"</qty>");
          sb.append("<cts>"+util.nvl(rs.getString("cts").trim(),"0") +"</cts>");
          sb.append("</issue>");
          }else{
            
              String isValid ="select * from iss_rtn_dtl where iss_id=? ";
              rs = db.execSql("sqlVlu", isValid, ary);
            if(rs.next()){
              sb.append("<issue>");
              sb.append("<qty>ALL</qty>");
              sb.append("</issue>");
            }else{
                sb.append("<issue>");
                sb.append("<qty>invalid</qty>");
                sb.append("</issue>");
            }
          }
      rs.close();
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      res.getWriter().write("<issue>"+sb.toString()+"</issue>");
      return null;
  }
    public ActionForward ttlBuyer(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
         ArrayList ary = new ArrayList();
         ary.add(stkIdn);
         
         
        String sql ="select a.nme nme , b.dte dte from nme_v a , msal b , jansal c , nmerel d " + 
        "where a.nme_idn = b.nme_idn and b.nmerel_idn = d.nmerel_idn " + 
        "and b.idn = c.idn and c.mstk_idn = ? and c.stt in ('SL','DLV') and rownum <=3 " + 
        "order by b.idn desc ";
        
        ResultSet rs = db.execSql("Trf", sql, ary);
        
        StringBuffer sb = new StringBuffer();
        while(rs.next()){
        sb.append("<value>");
        sb.append("<nme>"+util.nvl(rs.getString("nme"),"0") +"</nme>");
        sb.append("<dte>"+util.nvl(rs.getString("dte"),"0") +"</dte>");
        sb.append("</value>");
        }
        
        rs.close();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<values>"+sb.toString()+"</values>");
        return null;
    }
    
    public ActionForward salAvg(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    db.execUpd("delete","delete from gt_srch_multi",new ArrayList());
    String stkIdn = req.getParameter("stkIdn");
    ArrayList ary = new ArrayList();
    ary.add(stkIdn);
    ArrayList out = new ArrayList();
    out.add("I");
    out.add("I");
    CallableStatement ct = db.execCall("getSalAvg", "PRI_PKG.GET_SL_AVG(pStkIdn => ?, pRte => ?, pDis => ?)", ary, out);
    StringBuffer sb = new StringBuffer();
    sb.append("<value>");
    sb.append("<nme>Avg Rate ~ "+util.nvl(ct.getString(ary.size()+1),"0") +"</nme>");
    sb.append("<dte>Rap Dis ~ "+util.nvl(ct.getString(ary.size()+2),"0") +"</dte>");
    sb.append("<rte>0</rte>");
    sb.append("<dis>0</dis>");
    sb.append("<vnm>0</vnm>");
    sb.append("<raprte>0</raprte>");
      sb.append("<mstkidn>0</mstkidn>");
    sb.append("</value>");
    ct.close();
    String sql ="select form_url_encode(a.byr)  nme , to_char(a.dte, 'DD-MON-YYYY HH24:mi') dte " +
    " , trunc(((b.fnl_bse/greatest(c.rap_rte,1))*100)-100,2) mDis " +
    ", b.fnl_bse memoQuot , c.vnm , c.rap_rte,c.idn mstk_idn  " +
    ", dense_rank() over (order by b.idn desc) rank "+
    " from sal_v a , sal_pkt_dtl_v b, mstk c" +
    " where a.idn = b.idn and b.mstk_idn = c.idn " +
    // " and b.stt in ('SL','DLV') " +
    " and exists (select 1 from gt_srch_multi g where b.mstk_idn = g.stk_idn) "+
    // " and rownum <=3 " +
    "order by b.idn desc ";

    ResultSet rs = db.execSql("Trf", sql, new ArrayList());


    while(rs.next()){
    if(rs.getInt("rank") <= 5) {
    sb.append("<value>");
    sb.append("<nme>"+util.nvl(rs.getString("nme"),"0") +"</nme>");
    sb.append("<dte>"+util.nvl(rs.getString("dte"),"0") +"</dte>");
    sb.append("<rte>"+util.nvl(rs.getString("memoQuot"),"0") +"</rte>");
    sb.append("<dis>"+util.nvl(rs.getString("mDis"),"0") +"</dis>");
    sb.append("<vnm>"+util.nvl(rs.getString("vnm"),"0") +"</vnm>");
    sb.append("<raprte>"+util.nvl(rs.getString("rap_rte"),"0") +"</raprte>");
    sb.append("<mstkidn>"+util.nvl(rs.getString("mstk_idn"),"0") +"</mstkidn>");
    sb.append("</value>");
    } else
    break;
    }
      rs.close(); 
    String stkSql = "select  stk_idn, vnm,stt,quot,rap_rte,to_char(trunc(cts,2) * quot, '99999990.00') amt, "+
                 " decode(quot, 0, 0, decode(nvl(rap_dis,0), 0, 101, trunc(((quot*100)/greatest(rap_rte,100)) - 100,2))) rapDis "+
                 " from gt_srch_multi where stt not in ('MKSL','MKSD')";
 
       rs = db.execSql("Trf", stkSql, new ArrayList());
    while(rs.next()){
        sb.append("<value>");
        sb.append("<nme>"+util.nvl(rs.getString("stt"),"0") +"</nme>");
        sb.append("<dte>"+util.nvl(rs.getString("amt"),"0") +"</dte>");
        sb.append("<rte>"+util.nvl(rs.getString("quot"),"0") +"</rte>");
        sb.append("<dis>"+util.nvl(rs.getString("rapDis"),"0") +"</dis>");
        sb.append("<vnm>"+util.nvl(rs.getString("vnm"),"0") +"</vnm>");
        sb.append("<raprte>"+util.nvl(rs.getString("rap_rte"),"0") +"</raprte>");
        sb.append("<mstkidn>INSTOCK</mstkidn>");
        sb.append("</value>");
      }
      rs.close();

      
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<values>"+sb.toString()+"</values>");
    return null;
    }
    
    public ActionForward stkUpd(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String lab = req.getParameter("lab");
        String mstkIdn = req.getParameter("mstkIdn");
        String lprp = req.getParameter("lprp");
        String lprpVal = req.getParameter("lprpVal");
        HashMap mprp = info.getMprp();
        String lprpTyp = (String)mprp.get(lprp+"T");
        ArrayList ary = new ArrayList();
        ary.add(mstkIdn);
        ary.add(lprp);
        ary.add(lprpVal);
        ary.add(lab);
        ary.add(lprpTyp);
        String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?, pPrpTyp => ? )";
        int ct = db.execCall("stockUpd",stockUpd, ary);
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<values>yes</values>");
     return null;
    }
}
