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

public class AssortReturnImpl implements AssortReturnInterface {
    
    public AssortReturnImpl() {
        super();
    }
    public ArrayList getOptions( HttpServletRequest req ,String issueId ){
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
    ArrayList prcStt = new ArrayList();

    String getPrcToPrc = " select a1.in_stt from mprc a1, mprc a, prc_to_prc b, iss_rtn c "+
    " where a.idn = b.prc_id and a1.idn = b.prc_to_id and a.idn = c.prc_id and c.iss_id = ? "+
    " order by b.srt";

    ArrayList ary = new ArrayList();
    ary.add(issueId);

    ResultSet rs = db.execSql(" get options", getPrcToPrc, ary);
    try {
    while(rs.next()){
    prcStt.add(rs.getString("in_stt"));
    //prcStt.put(rs.getString("in_stt"), rs.getString("in_stt"));
    }
        rs.close();
    } catch (SQLException e) {
  
    }
    return prcStt ;
    }

    
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
        ArrayList ary = new ArrayList();
        ArrayList prcList = new ArrayList();
        HashMap prcSttMap = new HashMap();
        
        if(req.getParameter("grp") != null) 
        {
          String grp = util.nvl(req.getParameter("grp"));
      
          String prcSql = "select idn, prc , in_stt , ot_stt , is_stt from mprc where grp in ("+grp+") and is_stt <> 'NA' and stt = ? order by srt";
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
                  mprc.setOut_stt(rs.getString("ot_stt"));
                  mprc.setIs_stt(rs.getString("is_stt"));
                  prcList.add(mprc);
                  prcSttMap.put(mprcId, mprc);
              }
              rs.close();
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
        } 
        if(req.getParameter("isstt") != null) 
        {
          String isstt = (String) req.getParameter("isstt");
          String prcSql = "select idn, prc , in_stt, ot_stt , is_stt from mprc where is_stt IN ("+isstt+") and is_stt <> 'NA' and stt = 'A' order by srt";
          
          ResultSet rs = db.execSql("prcSql", prcSql, ary);
          try {
              while (rs.next()) {
                  Mprc mprc = new Mprc();
                  String mprcId = rs.getString("idn");
                  mprc.setMprcId(rs.getString("idn"));
                  mprc.setPrc(rs.getString("prc"));
                  mprc.setIn_stt(rs.getString("in_stt"));
                  mprc.setOut_stt(rs.getString("ot_stt"));
                  mprc.setIs_stt(rs.getString("is_stt"));
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
        String issId = (String)params.get("issueId");
        String empIdn = (String)params.get("empIdn");
        
        //get the checker prp
       
        ArrayList ary = null;
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        String srchRefQ = "";
        
       
          srchRefQ = 
          "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk) " + 
          " select  a.pkt_ty, a.idn, a.vnm,  a.dte, a.stt , 'AV' , a.qty , a.cts , b.iss_id , a.tfl3 "+
          "   from mstk a , MG_PNDG_V b where a.idn = b.iss_stk_idn   ";
        
          /*" select  a.pkt_ty, a.idn, a.vnm,  a.dte, a.stt , 'AV' , a.qty , a.cts , b.iss_id , a.tfl3 "+
          "   from mstk a , iss_rtn_dtl b, stk_dtl c " +
          " where a.idn = b.iss_stk_idn and b.stt='IS'  " +
          " and a.idn = c.mstk_idn "+
          " and a.stt =? and a.cts > 0  " +
          " and ((c.mprp = '"+chk+"' and c.val = 'CHK_OK')  " +
          " or  (c.mprp = '"+chk+"' and c.val = 'CHK_FAIL')) ";*/
          
       
       ary = new ArrayList();
        if(vnm.length()>2){
           vnm = util.getVnm(vnm);
            srchRefQ = srchRefQ+" and ( a.vnm in ("+vnm+") or a.tfl3 in ("+vnm+") ) " ;
        }
        if(!issId.equals("")){
            srchRefQ = srchRefQ+" and b.iss_id = ? " ;
            ary.add(issId);
        }
        if(!empIdn.equals("0")){
            
            srchRefQ = srchRefQ+" and b.emp_id = ? " ;
            ary.add(empIdn);
        }
       
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("AS_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
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
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , srch_id ,rmk  ";

        

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
                        if(vnmLst.indexOf("'")==-1 && !tfl3.equals(""))
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
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        if(!vnmLst.equals("")){
            vnmLst = util.pktNotFound(vnmLst);
            req.setAttribute("vnmNotFnd", vnmLst);
        }

        return pktList;
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
    
    public ArrayList StockUpdPrp(HttpServletRequest req, AssortReturnForm form , HashMap params){
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
        ArrayList stockPrpList = new ArrayList();
        ArrayList updatePrpList = new ArrayList();
        HashMap mprp = info.getMprp();
        String stockPrp = "select a.mprp ,a.iss_stk_idn, a.iss_val , b.flg, a.rtn_val ,a.iss_num , a.rtn_num , a.txt  from iss_rtn_prp a , prc_prp_alw b where  a.mprp = b.mprp and " +
                          "  a.iss_id=? and a.iss_stk_idn = ?  and b.prc_idn= ? order by b.srt ";

        ArrayList ary = new ArrayList();
        ary.add(params.get("issId"));
        ary.add(params.get("mstkIdn"));
        ary.add(params.get("prcId"));
        ResultSet rs = db.execSql("stockDtl", stockPrp, ary);
        try {
            while(rs.next()) {
                HashMap stockPrpUpd = new HashMap();
                String lprp = util.nvl(rs.getString("mprp"));
                String issVal = util.nvl(rs.getString("iss_val"));
                String rtnVal = util.nvl(rs.getString("rtn_val"));
                String dataTyp = util.nvl((String)mprp.get(lprp+"T"));
                if(dataTyp.equals("N")){
                    issVal = util.nvl(rs.getString("iss_num"));
                    rtnVal = util.nvl(rs.getString("rtn_num"));
                }
                if(dataTyp.equals("T") || dataTyp.equals("D")){
                    issVal = util.nvl(rs.getString("txt"));
                    rtnVal = util.nvl(rs.getString("txt"));
                 }
                String flg = util.nvl(rs.getString("flg"));
                if(flg.equals("EDIT"))
                    form.setValue(lprp, issVal);
                stockPrpUpd.put("mprp",lprp);
                stockPrpUpd.put("issVal",issVal);
                stockPrpUpd.put("rtnVal",rtnVal) ;
                stockPrpUpd.put("flg",flg);
                stockPrpUpd.put("mstkIdn",util.nvl(rs.getString("iss_stk_idn")));
                if(!flg.equals("EDIT"))
                form.setValue(lprp, rtnVal);
                if(!flg.equals("FTCH"))
                    updatePrpList.add(lprp);
                stockPrpList.add(stockPrpUpd);
               
            }
            
            rs.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        session.setAttribute("assortUpdPrp", updatePrpList);
        return stockPrpList;
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
    
}
