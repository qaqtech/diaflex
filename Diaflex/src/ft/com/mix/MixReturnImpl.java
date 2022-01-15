package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.MNme;
import ft.com.dao.Mprc;
import ft.com.dao.ObjBean;

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

public class MixReturnImpl implements MixReturnInterface {

public ArrayList MixSizeList(HttpServletRequest req , HttpServletResponse res){
  HttpSession session = req.getSession(false);
  InfoMgr info = (InfoMgr)session.getAttribute("info");
  DBUtil util = new DBUtil();
  DBMgr db = new DBMgr();
  ArrayList mixSizeList = new ArrayList();
  if(info!=null){  
  db.setCon(info.getCon());
  util.setDb(db);
  util.setInfo(info);
  db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
  util.setLogApplNm(info.getLoApplNm());

String mixSizeSql = "select distinct a.mix_size , b.srt from mix_size_clarity a , prp b " +
"where a.mix_size = b.val and b.mprp='MIX_SIZE' order by b.srt";
ArrayList outLst = db.execSqlLst("mixSize", mixSizeSql, new ArrayList());
PreparedStatement pst = (PreparedStatement)outLst.get(0);
ResultSet rs = (ResultSet)outLst.get(1);
try {
while (rs.next()) {
ObjBean prp = new ObjBean();
prp.setNme(util.nvl(rs.getString("mix_size")));
prp.setDsc(util.nvl(rs.getString("mix_size")));
mixSizeList.add(prp);
}
    rs.close();
    pst.close();
} catch (SQLException sqle) {
// TODO: Add catch code
sqle.printStackTrace();
}
  }
return mixSizeList;
}

    public ArrayList getEmp(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList empList = new ArrayList();
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = new ArrayList();
       
        String empSql = "select nme_idn, nme from nme_v where typ = 'EMPLOYEE' order by nme";
        ArrayList  rsLst = db.execSqlLst("empSql", empSql, ary);
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while (rs.next()) {
                MNme emp = new MNme();
                emp.setEmp_idn(rs.getString("nme_idn"));
                emp.setEmp_nme(rs.getString("nme"));
                empList.add(emp);
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return empList;
    }
    
    public ArrayList getPrc(HttpServletRequest req ,HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList prcList = new ArrayList();
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
     
        ArrayList ary = new ArrayList();
        String prcSql = "select idn, prc , in_stt from mprc where  is_stt ='MX_IS' " ;
        ary = new ArrayList();
        ArrayList  rsLst = db.execSqlLst("prcSql", prcSql, ary);
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
          try {
              while (rs.next()) {
                  Mprc mprc = new Mprc();
                 
                  mprc.setMprcId(rs.getString("idn"));
                  mprc.setPrc(rs.getString("prc"));
                  mprc.setIn_stt(rs.getString("in_stt"));
                  prcList.add(mprc);
                 
              }
              rs.close();
              stmt.close();
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
      }
       return prcList;
    }
    public ArrayList FecthResult(HttpServletRequest req, HttpServletResponse res, HashMap params){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList stockList = new ArrayList();
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      
        String stt = (String)params.get("stt");
        String mprcIdn = (String)params.get("mprcIdn");
        String empIdn = (String)params.get("empIdn");
        String issueId = util.nvl((String)params.get("issueId"));
        ArrayList ary = null;
      
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk , srch_id , cert_lab ) " + 
        " select  b.pkt_ty, b.idn, b.vnm,  b.dte, b.stt , 'Z' , b.qty , b.cts , b.sk1 , b.tfl3  , a.iss_id , cert_lab   "+
        "     from mstk b , "+
         " iss_rtn_dtl a where b.idn = a.iss_stk_idn and a.stt='IS'  "+
         " and b.stt =? and b.cts > 0  ";
      
        ary = new ArrayList();
        ary.add(stt);
        if(!issueId.equals("")){
            srchRefQ = srchRefQ+" and a.iss_id = ? " ;
            ary.add(issueId);
        }
        if(!empIdn.equals("0")){
            
            srchRefQ = srchRefQ+" and a.iss_emp_id = ? " ;
            ary.add(empIdn);
        }
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        stockList = SearchResult(req ,res);
      }
        return stockList;
    }
    
    public ArrayList SearchResult(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList pktList = new ArrayList();
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , rmk , srch_id , cert_lab  ";      
        String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by sk1 , cts ";      
        ArrayList ary = new ArrayList();
        ary.add("Z");
        ArrayList  rsLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while(rs.next()) {

                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                pktPrpMap.put("issIdn",util.nvl(rs.getString("srch_id")));         
                pktList.add(pktPrpMap);
                }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return pktList;
    }
    
    public HashMap GetTotal(HttpServletRequest req ,HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap gtTotalMap = new HashMap();
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      
        String gtTotal ="Select count(*) qty, sum(cts) cts from gt_srch_rslt where flg = ?";
        ArrayList ary = new ArrayList();
        ary.add("Z");
        ArrayList  rsLst = db.execSqlLst("getTotal", gtTotal, ary);
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            if (rs.next()) {
             gtTotalMap.put("qty", util.nvl(rs.getString("qty")));
             gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
            }
           rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return gtTotalMap ;
    }
    public ArrayList MixPrprViw(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute("MixViewLst");
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
     
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
                
              ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MIX_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs1 =(ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                stmt.close();
                session.setAttribute("MixViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return asViewPrp;
    }

public MixReturnImpl() {
super();
}



}