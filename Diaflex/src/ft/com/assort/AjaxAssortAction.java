package ft.com.assort;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AjaxAssortAction extends DispatchAction {
    
   
    public ActionForward prpUpd(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String msg="FAIL";
     if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
         String isRepair= util.nvl(req.getParameter("isRepair"));
         int ct=0;
        if(isRepair.equals("YES")){
            ArrayList ary = new ArrayList();
            ary.add(req.getParameter("issIdn"));
            ary.add(req.getParameter("mstkIdn"));
            ary.add(req.getParameter("prp"));
            ary.add(req.getParameter("prpVal"));
           ct = db.execCall("updateRepPrp", "ISS_RTN_PKG.REP_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pRepVal => ? )", ary);
            
        }else{
            ArrayList ary = new ArrayList();
            ary.add(req.getParameter("issIdn"));
            ary.add(req.getParameter("mstkIdn"));
            ary.add(req.getParameter("prp"));
            ary.add(req.getParameter("prpVal"));
           ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
            
        }
        if(ct>0)
            msg="SUCCESS";
        
      }
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<msg>" + msg + "</msg>");
      return null;
    }
    
    public ActionForward updGt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
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
        String updSql = "";
        ArrayList ary = new ArrayList();
        if(stkIdn.equals("ALL")){
            if(isChecked.equals("true")){
            updSql =" Update gt_srch_rslt set flg = ? ";
            ary.add("Y");
            }else{
            updSql =" Update gt_srch_rslt set flg = ? ";
            ary.add("Z"); 
            }
         }else{
             if(isChecked.equals("true")){
             updSql ="Update gt_srch_rslt set flg = ? where stk_idn = ?";
             ary.add("Y"); 
             ary.add(stkIdn);
             }else{
                 updSql ="Update gt_srch_rslt set flg = ? where stk_idn = ?";
                 ary.add("Z"); 
                 ary.add(stkIdn); 
             }
         }
        int ct = db.execUpd("update gt", updSql, ary);
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
      String updSql = "";
      ArrayList ary = new ArrayList();
      String lstNme = req.getParameter("lstNme");
      ArrayList selectstkIdnLst = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
      if(selectstkIdnLst==null)
          selectstkIdnLst = new ArrayList();
      if(stkIdn.equals("ALL")){
          if(isChecked.equals("true")){
         HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
           ArrayList stockIdnLst =new ArrayList();
            Set<String> keys = stockList.keySet();
                   for(String key: keys){
                  stockIdnLst.add(key);
                  }
         selectstkIdnLst = stockIdnLst;

       }else{
           selectstkIdnLst = new ArrayList();
          }
       }else{
           if(isChecked.equals("true")){
           if(!selectstkIdnLst.contains(stkIdn))
           selectstkIdnLst.add(stkIdn);
           }else{
           selectstkIdnLst.remove(stkIdn); 
           }
       }
      gtMgr.setValue(lstNme+"_SEL",selectstkIdnLst);
    }
      return null;
  } 
  
  
  
    public ActionForward bulkRtnUpdate(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
        String lprp =  util.nvl(req.getParameter("lprp"));
        String lprpVal =  req.getParameter("lprpVal");
        String lstNme = util.nvl(req.getParameter("lstNme"));
        ArrayList selectstkIdnLst = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
        String prcId= util.nvl(req.getParameter("prcId"));
          int ct=0;
          int nopkt=0;
        if(!lprp.equals("") && !lstNme.equals("") && !prcId.equals("")){
        String grp = "select grp from mprc where idn=?";
        ArrayList ary = new ArrayList();
        ary.add(prcId);
          ArrayList outLst = db.execSqlLst("grp", grp, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
          String grpVal="";
           if(rs.next())
           grpVal = rs.getString("grp");
         rs.close();
          pst.close();
       
        
        try{
        if(selectstkIdnLst!=null && selectstkIdnLst.size()>0) {
            nopkt = selectstkIdnLst.size();
            for(int i=0;i<selectstkIdnLst.size();i++){
                
            String lstkIdn = (String)selectstkIdnLst.get(i);
            ary = new ArrayList();
            ary.add(lstkIdn);
            ary.add(grpVal);
            int issIdn =0;
            String getIssIdn = "select ISS_RTN_PKG.LST_ISS_ID(?,?) issIdn from dual";
              outLst =  db.execSqlLst("issIdn", getIssIdn, ary);
              pst = (PreparedStatement)outLst.get(0);
              ResultSet rs1 = (ResultSet)outLst.get(1);
             if(rs1.next())
             issIdn = rs1.getInt(1);
                rs1.close();
              pst.close();
          if(lprpVal.equals("") || lprpVal.equals("0"))
              lprpVal="NA";
          if(!lprpVal.equals("") && !lprpVal.equals("0")){
             ary = new ArrayList();
             ary.add(String.valueOf(issIdn));
             ary.add(lstkIdn);
             ary.add(lprp);
             ary.add(lprpVal);
             ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
           
            }
            
            }
        }
            rs.close();
        }catch (SQLException sqle) {
                 // TODO: Add catch code
                 sqle.printStackTrace();
             } 
        }
          res.setContentType("text/xml");
          res.setHeader("Cache-Control", "no-cache");
        if(ct>0){
        res.getWriter().write("<message>"+lprp+" update successfully for "+nopkt+" </message>");
        }else{
            res.getWriter().write("<message>Updation Failed </message>");
        }
        
      }
        return null;
    } 
    
    
    public ActionForward updateGTExcel(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        String flg =  util.nvl(req.getParameter("flg"));
        String isChecked =  util.nvl(req.getParameter("stt"));
        String stkIdn =  util.nvl(req.getParameter("stkIdn"));
        String updSql = "";
        ArrayList ary = new ArrayList();
        if(flg.equals("ALL")){
            if(isChecked.equals("true")){
            updSql =" Update gt_srch_rslt set flg = ? ";
            ary.add("M");
            }else{
            updSql =" Update gt_srch_rslt set flg = ? ";
            ary.add("Z"); 
            }
         }else{
             if(isChecked.equals("true")){
             updSql ="Update gt_srch_rslt set flg = ? where stk_idn = ?";
             ary.add("M"); 
             ary.add(stkIdn);
             }else{
                 updSql ="Update gt_srch_rslt set flg = ? where stk_idn = ?";
                 ary.add("Z"); 
                 ary.add(stkIdn); 
             }
         }
        int ct = db.execUpd("update gt", updSql, ary);
      }
        return null;
    } 
    
    public ActionForward getvalidPrcEmp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
      ResultSet rs = null;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      ArrayList ary = new ArrayList();
      StringBuffer sb = new StringBuffer();
      String prcIdn = util.nvl(req.getParameter("prcIdn"));
        ary = new ArrayList();
        ary.add(prcIdn);
      String str = "where trunc(nvl(prc_emp_valid.dte_to, sysdate)) >= trunc(sysdate) and prc_idn=?";
      if(prcIdn.equals("0")){
          str = "";
          ary = new ArrayList();
      }
      String favSrch = " select emp_idn,form_url_encode(byr.get_nm(emp_idn)) emp from prc_emp_valid   "+str  +" order by 2";
      ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
       rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          fav=1;
          sb.append("<prc>");
          sb.append("<emp>"+util.nvl(rs.getString("emp").toLowerCase()) +"</emp>");
          sb.append("<emp_idn>"+util.nvl(rs.getString("emp_idn")) +"</emp_idn>");
          sb.append("</prc>");
      }
       if(fav==1)
        res.getWriter().write("<prcs>" +sb.toString()+ "</prcs>");
        rs.close();
        pst.close();
           return null;
    }
    
    public ActionForward fetchStatus(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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

       String prcId = util.nvl(req.getParameter("prcId"));
       String prcStr="select in_stt,grp from mprc where idn=?";
       ArrayList ary = new ArrayList();
       ary.add(prcId);
       ArrayList rsLst = db.execSqlLst("prcStr", prcStr, ary);
       PreparedStatement pst = (PreparedStatement)rsLst.get(0);
       ResultSet rs = (ResultSet)rsLst.get(1);
       if(rs.next()){
           String in_stt = util.nvl(rs.getString("in_stt"));
           if(in_stt.equals("NA")){
             String sqlStt="select inpc.dsc ||'('|| inpc.in_stt ||')' sttTtl, inpc.in_stt val from mprc a , prc_to_prc b , mprc inpc\n" + 
             "where a.idn=b.prc_id and a.idn=? and flg1=? and b.prc_to_id=inpc.idn and inpc.stt=?";
               ary = new ArrayList();
                ary.add(prcId);
               ary.add("IN");
               ary.add("A");
               ArrayList rsLst1 = db.execSqlLst("sqlStt", sqlStt, ary);
               PreparedStatement pst1 = (PreparedStatement)rsLst1.get(0);
               ResultSet rs1 = (ResultSet)rsLst1.get(1);
               while(rs1.next()){
                   
                  sb.append("<stt>");
                  sb.append("<nme>"+util.nvl(rs1.getString("val"))+"</nme>");
                  sb.append("<ttl>"+util.nvl(rs1.getString("sttTtl"))+"</ttl>");
                  sb.append("</stt>");
               }
               rs1.close();
               pst1.close();
           }
       }
       rs.close();
       pst.close();
        res.getWriter().write("<stts>"+sb.toString()+"</stts>");
        return null;
    
    }
    
    public AjaxAssortAction() {
        super();
    }
    
   
    
}
