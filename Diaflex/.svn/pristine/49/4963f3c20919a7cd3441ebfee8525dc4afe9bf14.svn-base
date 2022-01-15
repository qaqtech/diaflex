package ft.com.assorthk;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;



import ft.com.dao.MNme;

import ft.com.dao.Mprc;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AssortIssueImpl implements AssortIssueInterface {
    public ArrayList getPrc(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg="sucess";
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList prcList = new ArrayList();
        ArrayList ary = new ArrayList();
        HashMap prcSttMap = new HashMap();
        
        if(req.getParameter("grp") != null) 
        {
          String grp = util.nvl(req.getParameter("grp"));
          
          String prcSql = "select idn, prc , in_stt from mprc where grp in ("+grp+") and is_stt <> 'NA' and stt = ? order by srt";
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
          
        }
        
        if(req.getParameter("instt") != null) 
        {
          String instt = (String) req.getParameter("instt");
          String prcSql = "select idn, prc , in_stt from mprc where in_stt IN ("+instt+") and is_stt <> 'NA' and stt = 'A' order by srt";
          
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
        }
        
        session.setAttribute("mprcBean", prcSttMap);
        return prcList;
    }
    
    public ArrayList getEmp(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg="sucess";
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = new ArrayList();
        ArrayList empList = new ArrayList();
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
        
        return empList;
    }
    
    public ArrayList FecthResult(HttpServletRequest req, HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg="sucess";
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList stockList = new ArrayList();
        String stt = (String)params.get("stt");
        String vnm = (String)params.get("vnm");
        String mprcIdn = (String)params.get("mprcIdn");
        String empIdn = (String)params.get("empIdn");
        String mdl = (String)params.get("mdl");
        //String instt = (String)params.get("instt");
      //System.out.println("Fetch impl instt:"+instt);
        
        ArrayList ary = null;
        String flg = "ADD";
        String delQ = " Delete from gt_srch_rslt where flg in ('AV')";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk ) " + 
        " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'AV' , qty , cts , sk1 , tfl3  "+
        "     from mstk b "+
        " where stt =? and cts > 0  ";
    
        if(vnm.length()> 2){
          
          vnm = util.getVnm(vnm);
           
          srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+") ) " ;
          ary = new ArrayList();
          ary.add(stt);
          ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
          System.out.println("query: "+srchRefQ+" @ary: "+ary);
          flg = "VNM";
        }
        
        //this is required for magnus available 
        //(new stock and magnus after final pricing)
        /*if(instt.length()>0) {
          srchRefQ = 
          "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk ) " + 
          " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'AV' , qty , cts , sk1 , tfl3  "+
          "     from mstk b "+
          " where stt IN (" + instt + ") and cts > 0  ";
          
          ct = db.execUpd(" Srch Prp ", srchRefQ, new ArrayList());
          System.out.println("query: "+srchRefQ);
          flg = "VNM";
        }*/
        
        String prc_iss = "ISS_RTN_PKG.PRC_ISS_PKTS(pPrcId => ? , pEmpId => ?, pFlg => ?,pMdl=> ?)";
        ary = new ArrayList();
        ary.add(mprcIdn);
        ary.add(empIdn);
        ary.add(flg);
        //ary.add("AS_VIEW");
        //views will be dynamic based on the form
        ary.add(mdl);
        ct = db.execCall(" prc issue ", prc_iss, ary);
      System.out.println("query: "+prc_iss+" @ary: "+ary);
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("AS_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
      System.out.println("query: "+pktPrp+" @ary: "+ary);
        
        stockList = SearchResult(req , vnm);
       
        return stockList;
    }
    
    public ArrayList SearchResult(HttpServletRequest req , String vnmLst ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg="sucess";
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList pktList = new ArrayList();
        ArrayList vwPrpLst = ASPrprViw(req);
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

        
        String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by sk1";
        
        ArrayList ary = new ArrayList();
        ary.add("AV");
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
        vnmLst = vnmLst.replaceAll(",", "");
        req.setAttribute("vnmNotFnd", vnmLst+" : Packets Not Found");
        }

        return pktList;
    }
    
    public HashMap GetTotal(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg="sucess";
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap gtTotalMap = new HashMap();
        String gtTotal ="Select count(*) qty, sum(cts) cts from gt_srch_rslt where flg = ?";
        ArrayList ary = new ArrayList();
        ary.add("AV");
        ResultSet rs = db.execSql("getTotal", gtTotal, ary);
        try {
            if (rs.next()) {
             gtTotalMap.put("qty", rs.getString("qty"));
             gtTotalMap.put("cts", rs.getString("cts"));
            }
            rs.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return gtTotalMap ;
    }
    public ArrayList ASPrprViw(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg="sucess";
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("asViewLst");
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
        
        return asViewPrp;
    }
    
    public ArrayList ASGenricSrch(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg="sucess";
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("asGNCSrch");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
                ResultSet rs1 =
                    db.execSql(" Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'AS_SRCH' and flg in ('Y','S') order by rnk ",
                               new ArrayList());
                while (rs1.next()) {
                    ArrayList asViewdtl=new ArrayList();
                    asViewdtl.add(rs1.getString("prp"));
                    asViewdtl.add(rs1.getString("flg"));
                    asViewPrp.add(asViewdtl);
                }
                rs1.close();
                session.setAttribute("asGNCSrch", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
    
    public AssortIssueImpl() {
        super();
    }
}
