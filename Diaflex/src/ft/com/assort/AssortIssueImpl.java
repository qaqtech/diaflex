package ft.com.assort;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;


import ft.com.dao.GtPktDtl;
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

public class AssortIssueImpl implements AssortIssueInterface {
   
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
        
        
          String grp = util.nvl(req.getParameter("grp"));
          
          String prcSql = "select idn, prc , in_stt,is_stt,grp from mprc where  is_stt <> 'NA' and stt = ? " ;
              if(!grp.equals("")){
              prcSql+= " and grp in ("+grp+") ";
              }
              prcSql+=" order by srt";
          ary = new ArrayList();
          ary.add("A");
        ArrayList  outLst = db.execSqlLst("prcSql", prcSql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
          try {
              while (rs.next()) {
                  Mprc mprc = new Mprc();
                  String mprcId = rs.getString("idn");
                  mprc.setMprcId(rs.getString("idn"));
                  mprc.setPrc(rs.getString("prc"));
                  mprc.setIn_stt(rs.getString("in_stt"));
                  mprc.setIs_stt(rs.getString("is_stt"));
                  mprc.setGrp(rs.getString("grp"));
                  prcList.add(mprc);
                  prcSttMap.put(mprcId, mprc);
              }
              rs.close();
              pst.close();
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
        ArrayList  outLst = db.execSqlLst("empSql", empSql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                MNme emp = new MNme();
                emp.setEmp_idn(rs.getString("nme_idn"));
                emp.setEmp_nme(rs.getString("nme"));
                empList.add(emp);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return empList;
    }
    
    public HashMap FecthResult(HttpServletRequest req,HttpServletResponse res, HashMap params){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap stockList = new HashMap();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
          HashMap dbinfo = info.getDmbsInfoLst();
          String cnt = (String)dbinfo.get("CNT");
          int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        String stt = (String)params.get("stt");
        String vnm = (String)params.get("vnm");
        String mprcIdn = (String)params.get("mprcIdn");
        String empIdn = (String)params.get("empIdn");
        String issueIdn = (String)params.get("issueIdn");
        ArrayList ary = null;
        String flg = "ADD";
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        
        ary = new ArrayList();
        ary.add(stt);
        String srchRefQ = "";
        if(!issueIdn.equals("")){
            srchRefQ = 
                   "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk,quot,rap_rte,rap_dis ) " + 
                   "  select  pkt_ty, b.idn, b.vnm,  b.dte, b.stt , 'AV' , b.qty , b.cts , b.sk1 , b.tfl3,nvl(b.upr,b.cmp) rte,b.rap_rte,decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))  "+
                    " from mstk b , iss_rtn_dtl a where b.idn = a.iss_stk_idn "+
                    " and b.stt =? and a.iss_id = ? and b.cts > 0  ";
            ary.add(issueIdn);
        
        
        
        if(vnm.length()> 2 || !issueIdn.equals("")){
            if(vnm.length()> 2){
           vnm = util.getVnm(vnm);
           srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+") ) " ;
           }
          
          ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
//          System.out.println("query: "+srchRefQ+" @ary: "+ary);
          flg = "VNM";
            }
        }else if(!vnm.equals("")){
          vnm = util.getVnm(vnm);
          String[] vnmLst = vnm.split(",");
          int loopCnt = 1 ;
          float loops = ((float)vnmLst.length)/stepCnt;
          loopCnt = Math.round(loops);
             if(vnmLst.length <= stepCnt || new Float(loopCnt)>=loops) {
              
          } else
              loopCnt += 1 ;
          if(loopCnt==0)
              loopCnt += 1 ;
          int fromLoc = 0 ;
          int toLoc = 0 ;
          for(int i=1; i <= loopCnt; i++) {
              
            int aryLoc = Math.min(i*stepCnt, vnmLst.length) ;
            
            String lookupVnm = vnmLst[aryLoc-1];
                 if(i == 1)
                     fromLoc = 0 ;
                 else
                     fromLoc = toLoc+1;
                 
                 toLoc = Math.min(vnm.lastIndexOf(lookupVnm) + lookupVnm.length(), vnm.length());
                 String vnmSub = vnm.substring(fromLoc, toLoc);
              
              vnmSub=vnmSub.toUpperCase();
              vnmSub= vnmSub.replaceAll (" ", "");
              srchRefQ = 
               "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk,quot,rap_rte,rap_dis ) " + 
               " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'AV' , qty , cts , sk1 , tfl3,nvl(b.upr,b.cmp) rte,b.rap_rte,decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))  "+
               "     from mstk b "+
               " where stt =? and cts > 0  ";
              srchRefQ = srchRefQ+" and ( b.vnm in ("+vnmSub+") or b.tfl3 in ("+vnmSub+") ) " ;
              ct = db.execUpd(" ins gt_srch_rslt", srchRefQ,ary); 
          }
            flg = "VNM";
        }
        String prc_iss = "ISS_RTN_PKG.PRC_ISS_PKTS(pPrcId => ? , pEmpId => ?, pFlg => ?,pMdl=> ?)";
        ary = new ArrayList();
        ary.add(mprcIdn);
        ary.add(empIdn);
        ary.add(flg);
        ary.add("AS_VIEW");
        ct = db.execCall(" prc issue ", prc_iss, ary);
//        System.out.println("query: "+prc_iss+" @ary: "+ary);
        
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add("AS_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        stockList = SearchResult(req ,res, vnm);
      }
        return stockList;
    }
    
    public HashMap SearchResult(HttpServletRequest req ,HttpServletResponse res, String vnmLst ){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap pktList = new HashMap();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
      
        ArrayList vwPrpLst = ASPrprViw(req,res);
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty ,rap_rte, to_char(cts,'99990.00') cts ,quot, rmk,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis ";

        

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
        ary.add("AV");
        ArrayList  outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                 String stk_idn = util.nvl(rs.getString("stk_idn"));
                GtPktDtl pktPrpMap = new GtPktDtl();
                pktPrpMap.setStt(util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.setVnm(vnm);
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
                pktPrpMap.setPktIdn(rs.getLong("stk_idn"));
                pktPrpMap.setQty(util.nvl(rs.getString("qty")));
                pktPrpMap.setCts(util.nvl(rs.getString("cts")));
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                      if (prp.toUpperCase().equals("RAP_RTE"))
                      val = util.nvl(rs.getString("rap_rte")) ;  
                      if (prp.toUpperCase().equals("RAP_DIS"))
                      val = util.nvl(rs.getString("r_dis")) ;  
                      if (prp.toUpperCase().equals("RTE"))
                      val = util.nvl(rs.getString("quot")) ; 
                    if (prp.toUpperCase().equals("CRTWT"))
                     val = util.nvl(rs.getString("cts")) ; 
                        pktPrpMap.setValue(prp, val);
                         }
                              
                    pktList.put(stk_idn,pktPrpMap);
                }
            rs.close();
            pst.close();
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
    
    public void IssueEdit(HttpServletRequest req,HttpServletResponse res,String issStt){
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
        ArrayList prpList = new ArrayList();
        String labIssueEdit ="select b.mprp from mprc a , prc_prp_alw b " + 
                             " where a.idn = b.prc_idn and a.is_stt=? and b.flg='ISSEDIT'  order by b.srt ";
        ArrayList ary = new ArrayList();
        ary.add(issStt);
      ArrayList  outLst = db.execSqlLst("labIssue", labIssueEdit, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
          try {
              while (rs.next()) {
               prpList.add(rs.getString("mprp"));
              }
              rs.close();
              pst.close();
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
          session.setAttribute("AssortIssueEditPRP", prpList);
          gtMgr.setValue("AssortIssueEditPRP", prpList);
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
        ary.add("AV");
        ArrayList  outLst = db.execSqlLst("getTotal", gtTotal, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if (rs.next()) {
             gtTotalMap.put("qty", util.nvl(rs.getString("qty")));
             gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
            }
            rs.close();
            pst.close();
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
               
              ArrayList  outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'AS_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                pst.close();
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
    
    public AssortIssueImpl() {
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
                    util.updAccessLog(req, res, "Assort ISSUE Impl",
                                      "init");
                }
            }
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
        
       }
}
