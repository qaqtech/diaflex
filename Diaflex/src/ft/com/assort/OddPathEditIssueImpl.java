package ft.com.assort;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;

import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OddPathEditIssueImpl implements OddPathEditIssueInterface {
  
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
       init(req,res,session,util);
      
        ArrayList ary = new ArrayList();
        HashMap prcSttMap = new HashMap();
         String prcSql = "select idn, prc , in_stt from mprc where  grp ='ISEDIT' and stt = ? " ;
            
          ary = new ArrayList();
          ary.add("A");
          ResultSet rs = db.execSql("prcSql", prcSql, ary);
          try {
              while (rs.next()) {
                  Mprc mprc = new Mprc();
                  String mprcId = rs.getString("idn");
                  mprc.setMprcId(rs.getString("idn"));
                  mprc.setPrc(rs.getString("prc"));
                  mprc.setIn_stt(rs.getString("in_stt"));
                  prcList.add(mprc);
                  prcSttMap.put(mprcId, mprc);
              }
              rs.close();
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
          
        session.setAttribute("mprcBean", prcSttMap);
      }
         return prcList;
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
       init(req,res,session,util);
        ArrayList ary = new ArrayList();
       
        String empSql = "select nme_idn, nme from nme_v where typ = 'EMPLOYEE' order by nme";
        ResultSet rs = db.execSql("empSql", empSql, ary);
        try {
            while (rs.next()) {
                MNme emp = new MNme();
                emp.setEmp_idn(rs.getString("nme_idn"));
                emp.setEmp_nme(rs.getString("nme"));
                empList.add(emp);
            }
            rs.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return empList;
    }
    
    public ArrayList FecthResult(HttpServletRequest req,HttpServletResponse res, HashMap params){
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
       init(req,res,session,util);
       
        String stt = (String)params.get("stt");
        String vnm = (String)params.get("vnm");
        String mprcIdn = (String)params.get("mprcIdn");
        String empIdn = (String)params.get("empIdn");
        String issueIdn = (String)params.get("issueIdn");
        ArrayList ary = null;
        String flg = "ADD";
        String delQ = " Delete from gt_srch_rslt";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ary = new ArrayList();
        ary.add(stt);
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk ) " + 
        " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , sk1 , tfl3  "+
        "     from mstk b "+
        " where stt =? and cts > 0  ";
        if(!issueIdn.equals("")){
            srchRefQ = 
                   "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk ) " + 
                   "  select  pkt_ty, b.idn, b.vnm,  b.dte, b.stt , 'Z' , b.qty , b.cts , b.sk1 , b.tfl3  "+
                    " from mstk b , iss_rtn_dtl a where b.idn = a.iss_stk_idn "+
                    " and b.stt =? and a.iss_id = ? and b.cts > 0  ";
            ary.add(issueIdn);
        }
        
        
        if(vnm.length()> 2 ){
            vnm = util.getVnm(vnm);
           srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+") ) " ;
        }
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        
        String pktPrp = "DP_GT_PRP_UPD(pMdl => ?, pTyp => 'PRT')";
        ary = new ArrayList();
        ary.add("AS_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        stockList = SearchResult(req ,res, vnm);
      }
        return stockList;
    }
    
    public ArrayList prcPrpList(HttpServletRequest req ,HttpServletResponse res ,String prcIdn){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList prcPrpList = new ArrayList();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
       
        String prcPrpSql = "select mprp from prc_prp_alw where  prc_idn=? ";
        ArrayList ary = new ArrayList();
        ary.add(prcIdn);
        ResultSet rs = db.execSql("prpPrcSql", prcPrpSql ,ary);
        try {
            while (rs.next()) {
               prcPrpList.add(util.nvl(rs.getString("mprp")));
            }
            rs.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return prcPrpList;
    }
    public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res, String vnmLst ){
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
       init(req,res,session,util);
        
        ArrayList vwPrpLst = ASPrprViw(req,res);
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , rmk ";

        

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
        ResultSet rs = db.execSql("search Result", rsltQ, ary);
        try {
            while(rs.next()) {

                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
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
    
    public HashMap GetTotal(HttpServletRequest req , HttpServletResponse res){
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
       init(req,res,session,util);
       
        String gtTotal ="Select count(*) qty, sum(cts) cts from gt_srch_rslt where flg = ?";
        ArrayList ary = new ArrayList();
        ary.add("Z");
        ResultSet rs = db.execSql("getTotal", gtTotal, ary);
        try {
            if (rs.next()) {
             gtTotalMap.put("qty", util.nvl(rs.getString("qty")));
             gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
            }
            rs.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return gtTotalMap ;
    }
    public ArrayList ASPrprViw(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute("asViewLst");
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
        
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
                ResultSet rs1 =
                    db.execSql(" Vw Lst ", "Select prp  from rep_prp where mdl = 'AS_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                session.setAttribute("asViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return asViewPrp;
    }
    
   
    
//    public ArrayList ASGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("asGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'AS_SRCH' and flg <> 'N' order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("asGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
    
    public OddPathEditIssueImpl() {
        super();
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
                  util.updAccessLog(req, res, "Odd Path Edit issue Impl",
                                    "init");
              }
          }
      } catch (IOException ioe) {
          // TODO: Add catch code
          ioe.printStackTrace();
      }
      
     }
   
}
