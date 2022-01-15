package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.assort.AssortIssueInterface;
import ft.com.dao.MNme;

import ft.com.dao.Mprc;

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

public class MixActionImpl  implements MixInterface {
   
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
    public MixActionImpl() {
        super();
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
        String vnm = (String)params.get("vnm");
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
        if(vnm.length()> 2){
          
          vnm = util.getVnm(vnm);
           
          srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+") ) " ;
         
        }
        if(!issueId.equals("")){
            srchRefQ = srchRefQ+" and a.iss_id = ? " ;
            ary.add(issueId);
        }
        if(!empIdn.equals("0")){
            
            srchRefQ = srchRefQ+" and a.iss_emp_id = ? " ;
            ary.add(empIdn);
        }
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("MIX_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        
        stockList = SearchResult(req ,res, vnm);
      }
        return stockList;
    }
    
    public ArrayList SearchResult(HttpServletRequest req , HttpServletResponse res, String vnmLst ){
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
     
        ArrayList vwPrpLst =  MixPrprViw(req,res);
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , rmk , srch_id , cert_lab  ";

        

        for (int i = 0; i < vwPrpLst.size(); i++) {
            String fld = "prp_";
            int j = i + 1;
            if (j < 10)
                fld += "00" + j;
            else if (j < 100)
                fld += "0" + j;
            else if (j > 100)
                fld += j;

           

            srchQ += ", " + fld;
           
         }

        
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
                    pktPrpMap.put("cert_lab", util.nvl(rs.getString("cert_lab")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.put("vnm",vnm);
                    String tfl3 = util.nvl(rs.getString("rmk"));
                    if(vnmLst.indexOf(tfl3)!=-1 && !tfl3.equals("")){
                        if(vnmLst.indexOf("'")==-1)
                            vnmLst =  vnmLst.replaceAll(tfl3,"");
                        else
                            vnmLst =  vnmLst.replaceAll("'"+tfl3+"'", "");
                    } else if(vnmLst.indexOf(vnm)!=-1 && !vnm.equals("")){
                        if(vnmLst.indexOf("'")==-1)
                            vnmLst =  vnmLst.replaceAll(vnm,"");
                        else
                            vnmLst =  vnmLst.replaceAll("'"+vnm+"'", "");
                    }
                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                pktPrpMap.put("issIdn",util.nvl(rs.getString("srch_id")));
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                     
                        
                        pktPrpMap.put(prp, val);
                         }
                              
                    pktList.add(pktPrpMap);
                }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        if(!vnmLst.equals("")){
            vnmLst = util.pktNotFound(vnmLst);
            req.setAttribute("vnmNotFnd", vnmLst);
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

                session.setAttribute("MixViewLst", asViewPrp);
                rs1.close();
                stmt.close();
            }
            
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return asViewPrp;
    }
  public void init(HttpServletRequest req , HttpServletResponse res,HttpSession session,DBUtil util) {
          String rtnPg="sucess";
          String invalide="";
          String connExists=util.nvl(util.getConnExists());
        try {
            if (!connExists.equals("N"))
                invalide = util.nvl(util.chkTimeOut(), "N");
            if (session.isNew())
                res.sendRedirect("../error_page.jsp");
            if (connExists.equals("N"))
                res.sendRedirect("../error_page.jsp?connExists=N");
            if (invalide.equals("Y"))
                res.sendRedirect("../loginInvalid.jsp");
            if (rtnPg.equals("sucess")) {
                boolean sout = util.getLoginsession(req, res, session.getId());
                if (!sout) {
                  res.sendRedirect("../error_page.jsp");
                    System.out.print("New Session Id :=" + session.getId());
                } else {
                    util.updAccessLog(req, res, "Mix Action Impl",
                                      "init");
                }
            }
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
         
       }
}
