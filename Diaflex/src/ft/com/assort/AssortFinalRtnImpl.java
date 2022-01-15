package ft.com.assort;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.GtPktDtl;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;

import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.math.BigDecimal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AssortFinalRtnImpl implements AssortFinalRtnInterface {
    
 
    public AssortFinalRtnImpl() {
        super();
    }
    public ArrayList getOptions(HttpServletRequest req ,HttpServletResponse res, String issueId){
    //HashMap prcStt = new HashMap();
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    ArrayList prcStt = new ArrayList();
    if(info!=null){  
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
     init(req,res,session,util);
     String getPrcToPrc =  " select a1.in_stt from mprc a1, mprc a, prc_to_prc b "+
      " where a.idn = b.prc_id and a1.idn = b.prc_to_id  and a.idn = ? "+
       " order by b.srt ";

    ArrayList ary = new ArrayList();
    ary.add(issueId);

      ArrayList  outLst = db.execSqlLst(" get options", getPrcToPrc, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while(rs.next()){
    prcStt.add(rs.getString("in_stt"));
    //prcStt.put(rs.getString("in_stt"), rs.getString("in_stt"));
    }
    rs.close(); 
        pst.close();
    } catch (SQLException e) {
   
    }}
    return prcStt ;
    }
    
    public ArrayList getPrc(HttpServletRequest req , HttpServletResponse res){
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
        ArrayList ary = null;
     
        HashMap prcSttMap = new HashMap();
        String prcSql = "select idn, prc , in_stt , ot_stt , is_stt,pri_yn from mprc where grp in ('FNL ASRT') and is_stt <> 'NA' and stt = ? order by srt";
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
                mprc.setOut_stt(rs.getString("ot_stt"));
                mprc.setIs_stt(rs.getString("is_stt"));
                mprc.setPri_yn(rs.getString("pri_yn"));
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
       init(req,res,session,util);
       
        String stt = (String)params.get("stt");
        String vnm = (String)params.get("vnm");
        String issId = (String)params.get("issueId");
        String empIdn = (String)params.get("empIdn");
        ArrayList ary = null;
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk , rln_idn , quot , sk1 ) " + 
        " select  a.pkt_ty, a.idn, a.vnm,  a.dte, a.stt , 'Z' , a.qty , a.cts , b.iss_id , a.tfl3 , b.iss_emp_id , nvl(upr,cmp) , a.sk1  "+
        "   from mstk a , iss_rtn_dtl b where a.idn = b.iss_stk_idn and b.stt='IS'  "+
        " and a.stt =? and a.cts > 0   ";
        ary = new ArrayList();
        ary.add(stt);
        if(vnm.length() >2){
            vnm = util.getVnm(vnm);
            srchRefQ = srchRefQ+" and ( a.vnm in ("+vnm+") or a.tfl3 in ("+vnm+") ) ";
        }
        if(!issId.equals("")){
            srchRefQ = srchRefQ+" and b.iss_id = ? " ;
            ary.add(issId);
        }
        if(!empIdn.equals("0")){
            
            srchRefQ = srchRefQ+" and b.iss_emp_id = ? " ;
            ary.add(empIdn);
        }
       
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add("AS_VIEW");
        ct = db.execCall("Srch Prp", pktPrp, ary);
        
        String updProp= "pkgmkt.updProp(pMdl => ?)";
        ary = new ArrayList();
        ary.add("AS_VIEW");
        ct = db.execCall(" Srch Prp ", updProp, ary);
         stockList = SearchResult(req ,res , vnm);
      }
        return stockList;
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
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , srch_id , rmk , byr.get_nm(rln_idn) emp  , quot ";

        

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
        ArrayList  outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {

                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.put("vnm",vnm);
                    pktPrpMap.put("emp",util.nvl(rs.getString("emp")));
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
                    
                    if(prp.equals("RTE"))
                        fld="quot";
                      
                      String val = util.nvl(rs.getString(fld)) ;
                     
                        
                        pktPrpMap.put(prp, val);
                         }
                              
                    pktList.add(pktPrpMap);
                }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        ArrayList list = (ArrayList)req.getAttribute("msgList");
        if(list!=null && list.size()>0){
        }else{
        if(!vnmLst.equals("")){
            vnmLst = util.pktNotFound(vnmLst);
            req.setAttribute("vnmNotFnd", vnmLst);
        }
        }
      }
        return pktList;
    }
    
  public HashMap FecthResultGt(HttpServletRequest req, HttpServletResponse res, HashMap params){
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
     
      String stt = (String)params.get("stt");
      String vnm = (String)params.get("vnm");
      String issId = (String)params.get("issueId");
      String empIdn = (String)params.get("empIdn");
      ArrayList ary = null;
      String delQ = " Delete from gt_srch_rslt ";
      int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
      
      String srchRefQ = 
      "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk , rln_idn , quot , sk1,rap_rte,rap_dis,rchk ) " + 
      " select  a.pkt_ty, a.idn, a.vnm,  a.dte, a.stt , 'Z' , a.qty , a.cts , b.iss_id , a.tfl3 , b.iss_emp_id , nvl(upr,cmp) , a.sk1,a.rap_rte,decode(a.rap_rte ,'1',null,'0',null, trunc((nvl(a.upr,a.cmp)/greatest(a.rap_rte,1)*100)-100, 2)) ,b.lot_id "+
      "   from mstk a , iss_rtn_dtl b where a.idn = b.iss_stk_idn and b.stt='IS'  "+
      " and a.stt =? and a.cts > 0   ";
      ary = new ArrayList();
      ary.add(stt);
      if(vnm.length() >2){
          vnm = util.getVnm(vnm);
          srchRefQ = srchRefQ+" and ( a.vnm in ("+vnm+") or a.tfl3 in ("+vnm+") ) ";
      }
      if(!issId.equals("")){
          srchRefQ = srchRefQ+" and b.iss_id = ? " ;
          ary.add(issId);
      }
      if(!empIdn.equals("0")){
          
          srchRefQ = srchRefQ+" and b.iss_emp_id = ? " ;
          ary.add(empIdn);
      }
     
      ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
      
      String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
      ary = new ArrayList();
      ary.add("AS_VIEW");
      ct = db.execCall("Srch Prp", pktPrp, ary);
      
      String updProp= "pkgmkt.updProp(pMdl => ?)";
      ary = new ArrayList();
      ary.add("AS_VIEW");
      ct = db.execCall(" Srch Prp ", updProp, ary);
       stockList = SearchResultGt(req ,res , vnm);
    }
      return stockList;
  }
  
  public HashMap SearchResultGt(HttpServletRequest req ,HttpServletResponse res, String vnmLst ){
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
      
      SearchQuery query=new SearchQuery();
      ArrayList vwPrpLst = ASPrprViw(req,res);
      String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt ,sk1, qty , cts , srch_id , rmk , byr.get_nm(rln_idn) emp  ,rap_rte, quot,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis,nvl(rchk,'N') rtnprpupdated ";

      

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
                pktPrpMap.setValue("vnm", vnm);
                pktPrpMap.setSk1(new BigDecimal(util.nvl(rs.getString("sk1"),"0")));
                pktPrpMap.setValue("emp", util.nvl(rs.getString("emp")));
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
                pktPrpMap.setPktIdn(rs.getLong("stk_idn"));
                pktPrpMap.setQty(util.nvl(rs.getString("qty")));
                pktPrpMap.setCts(util.nvl(rs.getString("cts")));
                pktPrpMap.setValue("issIdn",util.nvl(rs.getString("srch_id")));
                pktPrpMap.setValue("rap_rte",util.nvl(rs.getString("rap_rte")));
                pktPrpMap.setValue("rtnprpupdated", util.nvl(rs.getString("rtnprpupdated")));
                for(int j=0; j < vwPrpLst.size(); j++){
                   String prp = (String)vwPrpLst.get(j);
                    
                    String fld="prp_";
                    if(j < 9)
                            fld="prp_00"+(j+1);
                    else    
                            fld="prp_0"+(j+1);
                    
                         String val = util.nvl(rs.getString(fld)) ;
                          if (prp.toUpperCase().equals("RAP_DIS"))
                          val = util.nvl(rs.getString("r_dis")) ;  
                          if (prp.toUpperCase().equals("RAP_RTE"))
                          val = util.nvl(rs.getString("rap_rte")) ;
                          if (prp.toUpperCase().equals("RTE"))
                          val = util.nvl(rs.getString("quot")) ;
                           pktPrpMap.setValue(prp, val);
                  }
                            
                pktList.put(stk_idn,pktPrpMap);
                }
                rs.close();
             pst.close();
                pktList =(HashMap)query.sortByComparator(pktList);
      } catch (SQLException sqle) {

          // TODO: Add catch code
          sqle.printStackTrace();
      }
      ArrayList list = (ArrayList)req.getAttribute("msgList");
      if(list!=null && list.size()>0){
      }else{
      if(!vnmLst.equals("")){
          vnmLst = util.pktNotFound(vnmLst);
          req.setAttribute("vnmNotFnd", vnmLst);
      }
      }
    }
      return pktList;
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
              ArrayList  outLst = db.execSqlLst("search Result", "Select prp  from rep_prp where mdl = 'AS_VIEW' and flg='Y' order by rnk ", new ArrayList());
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
    
    public ArrayList StockUpdPrp(HttpServletRequest req,HttpServletResponse res, AssortFinalRtnForm form , HashMap params){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList stockPrpList = new ArrayList();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
       
        String mstkIdn = (String)params.get("mstkIdn");
        String getVnm = "select vnm from mstk where idn=?";
        ArrayList ary = new ArrayList();
        ary.add(params.get("mstkIdn"));
        ArrayList  outLst = db.execSqlLst("getVnm",getVnm , ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
           req.setAttribute("vnm", rs.getString("vnm"));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        ArrayList finlPrcIdn = new ArrayList();
        String prcIdn = "select  Idn from mprc where stt = 'A' and in_stt in ('AS_AV') and idn != ?";
        ary = new ArrayList();
        ary.add(params.get("prcId"));
        outLst = db.execSqlLst("prpUpdSql", prcIdn, ary);
         pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
            finlPrcIdn.add(util.nvl(rs.getString("Idn")));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        String finalPrcId = finlPrcIdn.toString();
        finalPrcId = finalPrcId.replace('[','(');
        finalPrcId = finalPrcId.replace(']',')');
       
        ArrayList updatePrpList = new ArrayList();
        ArrayList prcPRPList = new ArrayList();
        HashMap mprp = info.getMprp();
     String stockPrp = "select a.mprp ,a.iss_stk_idn, b.flg" +
                          ",  Decode(C.Dta_Typ, 'C', A.Iss_Val, 'N', A.Iss_Num ,'D',format_to_date(a.txt), A.Txt) iss_val,\n" + 
                          " Decode(C.Dta_Typ, 'C', A.rtn_Val, 'N', A.rtn_Num ,'D',format_to_date(a.txt), A.Txt) rtn_val ,  "+
                           "  Decode(C.Dta_Typ, 'C', A.rep_Val, 'N', A.rep_Num ,'D',format_to_date(a.rep_txt), A.rep_Txt) rep_val "+
        
            //", a.iss_val, decode(b.flg, 'COMP', a.rtn_val, nvl(a.rtn_val, a.iss_val)) rtn_val " +
            //", a.iss_num , decode(b.flg, 'COMP', a.rtn_num, nvl(a.rtn_num, a.iss_num)) rtn_num , a.txt " +
            " from iss_rtn_prp a , prc_prp_alw b , mprp c where  a.mprp = b.mprp and b.mprp=c.prp and " +
                          "a.iss_id=? and a.iss_stk_idn = ?  and b.prc_idn =? order by b.srt ";


         ary = new ArrayList();
        ary.add(params.get("issId"));
        ary.add(params.get("mstkIdn"));
        ary.add(params.get("prcId"));
         outLst = db.execSqlLst("stockDtl", stockPrp, ary);
        pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                HashMap stockPrpUpd = new HashMap();
                 String lprp = util.nvl(rs.getString("mprp"));
                String issVal = util.nvl(rs.getString("iss_val"));
                String rtnVal = util.nvl(rs.getString("rtn_val"));
                String repVal = util.nvl(rs.getString("rep_val"));
               
                String flg = util.nvl(rs.getString("flg"));
                stockPrpUpd.put("mprp",lprp);
                stockPrpUpd.put("issVal",issVal);
                stockPrpUpd.put("rtnVal",rtnVal) ;
                stockPrpUpd.put("flg",flg);
                stockPrpUpd.put("mstkIdn",util.nvl(rs.getString("iss_stk_idn")));
                form.setValue(mstkIdn+"_"+lprp, rtnVal);
                if(!flg.equals("FTCH"))
                    updatePrpList.add(lprp);
                stockPrpList.add(stockPrpUpd);
                 prcPRPList.add(lprp);
                form.setValue("RP_"+mstkIdn+"_"+lprp, repVal);
                
            }
            rs.close();
            pst.close();
          
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        session.setAttribute("assortUpdPrp", updatePrpList);
        session.setAttribute("prcPrpList", prcPRPList);
        String prpUpdSql ="select 0 iss_id, 0 emp_id, 'A - MFG' nme, a.idn iss_stk_idn, a.mprp, a.val " + 
        "from STK_DTL_MFG_V a, prc_prp_alw c " + 
        "where a.mprp = c.mprp " + 
        "and a.idn = ? " + 
        "and c.prc_idn in "+finalPrcId+  
        "and c.flg <> 'FTCH' " + 
        "UNION " + 
       " select a.iss_id, a.emp_id, d.nme, b.iss_stk_idn, b.mprp, decode(e.dta_typ, 'C', b.rtn_val,'N', to_char(b.rtn_num),  nvl(b.txt,'')) val  "+
       " from iss_rtn a, iss_rtn_prp b,iss_rtn_dtl ir, prc_prp_alw c, nme_v d , mprp e where a.iss_id = b.iss_id and a.prc_id = c.prc_idn " +
        " and b.mprp = c.mprp and b.mprp = e.prp "+
       " and c.mprp = e.prp "+
        "and a.emp_id = d.nme_idn and a.iss_id = ir.iss_id and ir.iss_stk_idn = b.iss_stk_idn and ir.stt <> 'CL' " + 
        "and a.prc_id in "+finalPrcId + 
        "and b.iss_stk_idn = ? " + 
        "and c.flg <> 'FTCH'" + 
        "and b.rtn_val is not null " + 
        "order by 1, 3";
        
        
          ary = new ArrayList();
        
        ary.add(params.get("mstkIdn"));
        ary.add(params.get("mstkIdn"));
         int pIssueIdn = -1;
         String pEmpName ="";
         HashMap prpValMap = new HashMap();
         ArrayList diffPrpList = new ArrayList();
         HashMap empPrpMainMap = new HashMap();
         HashMap empPrpSubMap = new HashMap();
         HashMap empNameMap = new HashMap();
         ArrayList empIdnList = new ArrayList();
        outLst = db.execSqlLst("prpUpdSql", prpUpdSql, ary);
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
         try {
            while (rs.next()) {
             int lissueIdn = rs.getInt("iss_id");
             String lEmpName = rs.getString("nme");
                if(pIssueIdn==-1){
                pIssueIdn = lissueIdn;
                pEmpName = lEmpName;
                }
                
                if(pIssueIdn!=lissueIdn){
                    empPrpMainMap.put(String.valueOf(pIssueIdn), empPrpSubMap);
                    empPrpSubMap = new HashMap();
                   
                    empIdnList.add(String.valueOf(pIssueIdn));
                    empNameMap.put(String.valueOf(pIssueIdn), pEmpName);
                    pIssueIdn = lissueIdn;
                    pEmpName = lEmpName;
                }
               
                String mprpFld = util.nvl(rs.getString("mprp")).trim();
                String prpTyp = util.nvl((String)mprp.get(mprpFld+"T"));
                String mprpVal = util.nvl(rs.getString("val")).trim();
                empPrpSubMap.put(mprpFld,mprpVal);
                if(!diffPrpList.contains(mprpFld) && prpTyp.equals("C") ){
                String mapPrpVal = util.nvl((String)prpValMap.get(mprpFld)).trim();
                if(mapPrpVal.equals(""))
                prpValMap.put(mprpFld,mprpVal);
                else{
                    if(!mprpVal.equals(mapPrpVal))
                        diffPrpList.add(mprpFld);
                }
                }
                
            }
            rs.close();
             pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
         if(pIssueIdn!=-1){
             empPrpMainMap.put(String.valueOf(pIssueIdn), empPrpSubMap);
             empIdnList.add(String.valueOf(pIssueIdn));
             empNameMap.put(String.valueOf(pIssueIdn), pEmpName);
         }
        req.setAttribute("empIdnList", empIdnList);
        req.setAttribute("empPrpMainMap", empPrpMainMap);
        req.setAttribute("empNameMap", empNameMap);
        req.setAttribute("diffPrpList", diffPrpList);
        req.setAttribute("prpValMap", prpValMap);
        
        ArrayList assortUpdPrp = (ArrayList)session.getAttribute("assortUpdPrp");
        for(int n=0;n<assortUpdPrp.size();n++){
          String editPrp = util.nvl((String)assortUpdPrp.get(n));
            if(!diffPrpList.contains(editPrp)){
              String  issVal = (String)prpValMap.get(editPrp);
               String setVal = util.nvl((String)form.getValue(mstkIdn+"_"+editPrp));
               if(setVal.equals("")) {
                form.setValue(mstkIdn+"_"+editPrp, issVal);
               } 
            }
        }
        
        req.setAttribute("mstkIdn", mstkIdn);
        form.setValue("issIdn", params.get("issId"));
        form.setValue("prcId", params.get("prcId"));
        form.setValue("mstkIdn", mstkIdn);
      }
        return stockPrpList;
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
       init(req,res,session,util);
    
        String gtTotal ="Select count(*) qty, sum(cts) cts from gt_srch_rslt where flg = ?";
        ArrayList ary = new ArrayList();
        ary.add("Z");
        
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
    
    public void isRepairIS(HttpServletRequest req , HttpServletResponse res ){
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
        String isRepair = util.nvl((String)session.getAttribute("ISREPAIR"));
        if(isRepair.equals("")){
        String    memoPrntOptn = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                                 + "b.mdl = 'JFLEX' and b.nme_rule ='RPAIRISS' and a.til_dte is null order by a.srt_fr ";
       String val = "";
          ArrayList  outLst = db.execSqlLst("memoPrint", memoPrntOptn, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if (rs.next()) {
                 val = rs.getString("chr_fr");
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
            session.setAttribute("ISREPAIR", val);

        }
      }
       
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
                    util.updAccessLog(req, res, "Assort Final ReturnImpl",
                                      "init");
                }
            }
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
         
       }
  
}

   
