package ft.com.assort;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.GtPktDtl;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;

import ft.com.lab.LabReturnForm;

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

public class AssortReturnImpl implements AssortReturnInterface {
    
    public AssortReturnImpl() {
        super();
    }
    public ArrayList getOptions( HttpServletRequest req ,HttpServletResponse res , String issueId ){
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
    }
    rs.close();
    pst.close();
    } catch (SQLException e) {
    
    }
    }
    return prcStt ;
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
       init(req,res,session,util);
        ArrayList ary = null;
        String grp = util.nvl(req.getParameter("grp"));
    
       
        HashMap prcSttMap = new HashMap();
        String prcSql = "select idn, prc , in_stt , ot_stt , is_stt,pri_yn,grp from mprc where grp in ("+grp+") and is_stt <> 'NA' and stt = ? order by srt";
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
    
    public ArrayList getEmp(HttpServletRequest req ,HttpServletResponse res){
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
        String issId = (String)params.get("issueId");
        String empIdn = (String)params.get("empIdn");
        String grpList = (String)params.get("grpList");
        ArrayList ary = null;
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk , rln_idn , sk1,rap_rte,rap_dis ) " + 
        " select  a.pkt_ty, a.idn, a.vnm,  a.dte, a.stt , 'Z' , a.qty , a.cts , b.iss_id , a.tfl3 , b.iss_emp_id , a.sk1,a.rap_rte,decode(a.rap_rte ,'1',null,'0',null, trunc((nvl(a.upr,a.cmp)/greatest(a.rap_rte,1)*100)-100, 2)) "+
        "   from mstk a , iss_rtn_dtl b where a.idn = b.iss_stk_idn and b.stt='IS'  "+
        " and a.stt =? and a.cts > 0   ";
        ary = new ArrayList();
        ary.add(stt);
        if(vnm.length()>2){
           vnm = util.getVnm(vnm);
            srchRefQ = srchRefQ+" and ( a.vnm in ("+vnm+") or a.tfl3 in ("+vnm+") ) " ;
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
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        String updProp= "pkgmkt.updProp(pMdl => ?)";
        ary = new ArrayList();
        ary.add("AS_VIEW");
        ct = db.execCall(" Srch Prp ", updProp, ary);
        
        stockList = SearchResult(req ,res, vnm);
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
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , srch_id ,rmk , byr.get_nm(rln_idn) emp,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis ";

        

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
                pktPrpMap.put("emp",  util.nvl(rs.getString("emp")));
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
                     
                       if (prp.toUpperCase().equals("RAP_DIS"))
                       val = util.nvl(rs.getString("r_dis")) ; 
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
    
  public HashMap FecthResultGt(HttpServletRequest req,HttpServletResponse res, HashMap params){
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
      String grpList = (String)params.get("grpList");
        String lotNo = util.nvl((String)params.get("lotNo"));
        String altLotNo = util.nvl((String)params.get("altLotNO"));
        String ctInwardNo = util.nvl((String)params.get("ctInwardNo"));
      ArrayList ary = null;
      String delQ = " Delete from gt_srch_rslt ";
      int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
      
      String srchRefQ = 
      "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk , rln_idn , sk1,rap_rte,rap_dis,rchk,pair_id ) " + 
      " select  a.pkt_ty, a.idn, a.vnm,  a.dte, a.stt , 'Z' , a.qty , a.cts , b.iss_id , a.tfl3 , b.iss_emp_id , a.sk1,a.rap_rte,decode(a.rap_rte ,'1',null,'0',null, trunc((nvl(a.upr,a.cmp)/greatest(a.rap_rte,1)*100)-100, 2)),b.lot_id, "+
      "  b.iss_rtn_dtl_id from mstk a , iss_rtn_dtl b where a.idn = b.iss_stk_idn and b.stt='IS'  "+
      " and a.stt =? and a.cts > 0   ";
      ary = new ArrayList();
      ary.add(stt);
      if(vnm.length()>2){
         vnm = util.getVnm(vnm);
          srchRefQ = srchRefQ+" and ( a.vnm in ("+vnm+") or a.tfl3 in ("+vnm+") ) " ;
      }
      if(!issId.equals("")){
          srchRefQ = srchRefQ+" and b.iss_id = ? " ;
          ary.add(issId);
      }
      if(!empIdn.equals("0")){
          
          srchRefQ = srchRefQ+" and b.iss_emp_id = ? " ;
          ary.add(empIdn);
      }
        if(!lotNo.equals("")){
            srchRefQ = srchRefQ+" and EXISTS ( select 1 from stk_dtl c where a.idn = c.mstk_idn and c.mprp='LOTNO' and c.txt = ?)";
            ary.add(lotNo);
        }
        if(!altLotNo.equals("")){
            srchRefQ = srchRefQ+" and EXISTS ( select 1 from stk_dtl d where a.idn = d.mstk_idn and d.mprp='ALT_LOTNO' and d.txt = ?)";
            ary.add(altLotNo);
        }
        if(!ctInwardNo.equals("")){
            srchRefQ = srchRefQ+" and EXISTS ( select 1 from stk_dtl d where a.idn = d.mstk_idn and d.mprp='CT_INWARD_NO' and d.txt = ?)";
            ary.add(ctInwardNo);
        }
      ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
      
      String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
      ary = new ArrayList();
      ary.add("AS_VIEW");
      ct = db.execCall(" Srch Prp ", pktPrp, ary);
      
      String updProp= "pkgmkt.updProp(pMdl => ?)";
      ary = new ArrayList();
      ary.add("AS_VIEW");
      ct = db.execCall(" Srch Prp ", updProp, ary);
      
      stockList = SearchResultGt(req ,res, vnm);
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
      String  srchQ =  " select stk_idn , pkt_ty, pair_id, vnm, pkt_dte, stt ,sk1, qty , cts , srch_id ,rmk , byr.get_nm(rln_idn) emp,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis,nvl(rchk,'N') rtnprpupdated ";

      

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
                pktPrpMap.setValue("emp", util.nvl(rs.getString("emp")));
                pktPrpMap.setValue("vnm", vnm);
                pktPrpMap.setSk1(new BigDecimal(util.nvl(rs.getString("sk1"),"0")));
                pktPrpMap.setValue("rtnprpupdated", util.nvl(rs.getString("rtnprpupdated")));
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
               pktPrpMap.setValue("issDtlIdn",util.nvl(rs.getString("pair_id")));
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
    
    public ArrayList StockUpdPrp(HttpServletRequest req, HttpServletResponse res, AssortReturnForm form , HashMap params){
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
        AssortReturnForm assortForm = (AssortReturnForm)form;
        String mstkIdn = (String)params.get("mstkIdn");
      
        ArrayList updatePrpList = new ArrayList();
        ArrayList prcPrpList = new ArrayList();
        HashMap mprp = info.getMprp();
      String stockPrp = "select a.mprp ,a.iss_stk_idn, b.flg" +
                        ",  Decode(C.Dta_Typ, 'C', A.Iss_Val, 'N', A.Iss_Num ,'D',format_to_date(a.txt), A.Txt) iss_val,\n" + 
                        " Decode(C.Dta_Typ, 'C', A.rtn_Val, 'N', A.rtn_Num ,'D',format_to_date(a.txt), A.Txt) rtn_val ,  "+
                         "  Decode(C.Dta_Typ, 'C', A.rep_Val, 'N', A.rep_Num ,'D',format_to_date(a.rep_txt), A.rep_Txt) rep_val "+
      
          //", a.iss_val, decode(b.flg, 'COMP', a.rtn_val, nvl(a.rtn_val, a.iss_val)) rtn_val " +
          //", a.iss_num , decode(b.flg, 'COMP', a.rtn_num, nvl(a.rtn_num, a.iss_num)) rtn_num , a.txt " +
          " from iss_rtn_prp a , prc_prp_alw b , mprp c where  a.mprp = b.mprp and b.mprp=c.prp and " +
                        "a.iss_id=? and a.iss_stk_idn = ?  and b.prc_idn =? order by b.srt ";

        ArrayList ary = new ArrayList();
        ary.add(params.get("issId"));
        ary.add(params.get("mstkIdn"));
        ary.add(params.get("prcId"));
        ArrayList  outLst = db.execSqlLst("stockDtl", stockPrp, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
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
           
                form.setValue(lprp, rtnVal);
                 if(!flg.equals("FTCH"))
                    updatePrpList.add(lprp);
                stockPrpList.add(stockPrpUpd);
                form.setValue("RP_"+lprp,repVal);
                prcPrpList.add(lprp);
            }
            
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        session.setAttribute("assortUpdPrp", updatePrpList);
        session.setAttribute("prcPrpList", prcPrpList);
        req.setAttribute("mstkIdn", mstkIdn);
        assortForm.setValue("issIdn", params.get("issId"));
        assortForm.setValue("prcId", params.get("prcId"));
        assortForm.setValue("mstkIdn", mstkIdn);
      }
        return stockPrpList;
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
       
        String gtTotal ="Select sum(qty) qty, sum(cts) cts from gt_srch_rslt where flg = ?";
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
    public String getLab(HttpServletRequest req , HttpServletResponse res ,String stkIdn){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String lab="";
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
      
        String getLab = "select cert_lab from mstk where idn=? ";
        ArrayList ary = new ArrayList();
        ary.add(stkIdn);
        
        ArrayList  outLst = db.execSqlLst("getLab", getLab, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                   lab = rs.getString("cert_lab");
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return  lab;
    }
    public String issPrcStt(HttpServletRequest req , HttpServletResponse res ,int prcIdn){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String issueStt="";
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
       
        String issSttPrc = "select idn, prc , in_stt , ot_stt , is_stt from mprc where idn=? ";
        ArrayList ary = new ArrayList();
        ary.add(String.valueOf(prcIdn));
        ArrayList  outLst = db.execSqlLst("issStt", issSttPrc, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                   issueStt = rs.getString("is_stt");
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return  issueStt;
    }
    public void isRepairIS(HttpServletRequest req , HttpServletResponse res ){
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
                    util.updAccessLog(req, res, "Assort ReturnImpl",
                                      "init");
                }
            }
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
        
       }
    
}
