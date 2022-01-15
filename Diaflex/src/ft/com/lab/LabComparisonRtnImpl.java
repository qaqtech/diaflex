package ft.com.lab;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.assort.AssortFinalRtnForm;
import ft.com.dao.MNme;

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

public class LabComparisonRtnImpl implements LabComparisonRtnInterface{
    HttpSession session ;
    DBMgr db ;
    InfoMgr info ;
    DBUtil util ;
    
    public ArrayList getOptions(HttpServletRequest req ,HttpServletResponse res, String issueId){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
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


        ArrayList outLst = db.execSqlLst(" get options", getPrcToPrc, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while(rs.next()){
    prcStt.add(rs.getString("in_stt"));
    //prcStt.put(rs.getString("in_stt"), rs.getString("in_stt"));
    }
        rs.close(); pst.close();
    } catch (SQLException e) {
    
    }
    return prcStt ;
    }
   
    public ArrayList getOptionsPrc(HttpServletRequest req ,HttpServletResponse res, String prcIdn){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    ArrayList prcStt = new ArrayList();

    String getPrcToPrc = " select a1.in_stt from mprc a1, mprc a, prc_to_prc b "+
    " where a.idn = b.prc_id and a1.idn = b.prc_to_id and b.prc_id = ? "+
    " order by b.srt";

    ArrayList ary = new ArrayList();
    ary.add(prcIdn);


        ArrayList outLst = db.execSqlLst(" get options", getPrcToPrc, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while(rs.next()){
    prcStt.add(rs.getString("in_stt"));
    //prcStt.put(rs.getString("in_stt"), rs.getString("in_stt"));
    }
        rs.close(); pst.close();
    } catch (SQLException e) {
    
    }
    return prcStt ;
    }
    public ArrayList getEmp(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = new ArrayList();
        ArrayList empList = new ArrayList();
        String empSql = "select nme_idn, nme from nme_v where typ = 'EMPLOYEE' order by nme";

        ArrayList outLst = db.execSqlLst("empSql", empSql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                MNme emp = new MNme();
                emp.setEmp_idn(rs.getString("nme_idn"));
                emp.setEmp_nme(rs.getString("nme"));
                empList.add(emp);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return empList;
    }
    
    public ArrayList StockUpdPrp(HttpServletRequest req,HttpServletResponse res, LabComparisonRtnForm form , HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String mstkIdn = (String)params.get("mstkIdn");
        String getVnm = "select vnm from mstk where idn=?";
        ArrayList ary = new ArrayList();
        ary.add(params.get("mstkIdn"));

        ArrayList outLst = db.execSqlLst("getVnm",getVnm , ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
           req.setAttribute("vnm", rs.getString("vnm"));
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
       
       HashMap stockPrpUpdList = new HashMap();
        ArrayList stockPrpList = new ArrayList();
        ArrayList updatePrpList = new ArrayList();
        HashMap mprp = info.getMprp();
      String stockPrp = "select a.mprp ,a.iss_stk_idn, b.flg" +
                        ",  Decode(C.Dta_Typ, 'C', A.Iss_Val, 'N', A.Iss_Num ,'D',format_to_date(a.txt), A.Txt) iss_val,\n" + 
                        " Decode(C.Dta_Typ, 'C', A.rtn_Val, 'N', A.rtn_Num ,'D',format_to_date(a.txt),A.Txt) rtn_val   "+
                        " from iss_rtn_prp a , prc_prp_alw b, mprp c where  a.mprp = b.mprp and b.mprp=c.prp and " +
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
               
                String flg = util.nvl(rs.getString("flg"));
                stockPrpUpd.put("mprp",lprp);
                stockPrpUpd.put("issVal",issVal);
                stockPrpUpd.put("rtnVal",rtnVal) ;
                stockPrpUpd.put("flg",flg);
                stockPrpUpd.put("mstkIdn",util.nvl(rs.getString("iss_stk_idn")));
                form.setValue(mstkIdn+"_"+lprp, rtnVal);
                stockPrpUpdList.put(mstkIdn+"_"+lprp, issVal);
                if(!flg.equals("FTCH"))
                updatePrpList.add(lprp);
                stockPrpList.add(stockPrpUpd);
               
            }
            rs.close(); pst.close();
          
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        req.setAttribute("stockPrpUpdList", stockPrpUpdList);
        session.setAttribute("assortUpdPrp", updatePrpList);
        return stockPrpList;
    }
    public ArrayList FecthResult(HttpServletRequest req, HttpServletResponse res, HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
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
        String issueId = util.nvl((String)params.get("issueId"));
        ArrayList ary = null;
      
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk , srch_id , cert_lab,  cmp, rap_rte , quot , rap_dis ) " + 
        " select  b.pkt_ty, b.idn, b.vnm,  b.dte, b.stt , 'Z' , b.qty , b.cts , b.sk1 , b.tfl3  , a.iss_id , cert_lab ,nvl(b.cmp,b.upr) , b.rap_rte , nvl(b.upr,b.cmp) , decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))  "+
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
        ary.add("LBCOM_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        
        stockList = SearchResult(req ,res, vnm);
       
        return stockList;
    }
    
    public ArrayList SearchResult(HttpServletRequest req , HttpServletResponse res, String vnmLst ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList pktList = new ArrayList();
        ArrayList vwPrpLst =  LabPrprViw(req,res);
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

        ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
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
            rs.close(); pst.close();
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
    
    public HashMap GetTotal(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap gtTotalMap = new HashMap();
        String gtTotal ="Select count(*) qty, sum(cts) cts from gt_srch_rslt where flg = ?";
        ArrayList ary = new ArrayList();
        ary.add("Z");

        ArrayList outLst = db.execSqlLst("getTotal", gtTotal, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if (rs.next()) {
             gtTotalMap.put("qty", util.nvl(rs.getString("qty")));
             gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return gtTotalMap ;
    }
    
    public ArrayList LabPrprViw(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("LbComViewLst");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();

                ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'LBCOM_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close(); pst.close();
                session.setAttribute("LbComViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return asViewPrp;
    }
    
    public ArrayList MultiUpdPRP(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList viewPrp = (ArrayList)session.getAttribute("MULT_PKT_UPD");
        try {
            if (viewPrp == null) {

                viewPrp = new ArrayList();

                ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MULT_PKT_UPD' and flg='Y' order by rnk ",
                               new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    viewPrp.add(rs1.getString("prp"));
                }
                rs1.close(); pst.close();
                session.setAttribute("MULT_PKT_UPD", viewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return viewPrp;
    }
    
    public ArrayList pktList(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList pktList = new ArrayList();
        ArrayList itmHdr = new ArrayList();
        itmHdr.add("SR NO");
        itmHdr.add("VNM");
        String mstkSql ="select a.vnm , a.idn , a.rap_rte ,  decode(a.rap_rte ,'1',null, trunc((nvl(a.upr,a.cmp)/greatest(a.rap_rte,1)*100)-100, 2)) r_dis, to_char(trunc(a.cts,2) * nvl(a.upr,a.cmp), '99999990.00') amt from mstk a , gt_srch_rslt b where a.idn = b.stk_idn and b.flg='Z' order by a.sk1 , a.vnm ";

        ArrayList outLst = db.execSqlLst("mstk", mstkSql, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            int sr=0;
            while (rs.next()) {
                sr++;
               HashMap pktDtl = new HashMap();
                String r_dis = util.nvl(rs.getString("r_dis"));
                String idn = util.nvl(rs.getString("idn"));
                String vnm = util.nvl(rs.getString("vnm"));
                pktDtl.put("STK_IDN", idn);
                pktDtl.put("VNM", vnm);
                pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
                pktDtl.put("RAP Vlu", util.nvl(rs.getString("amt")));
                pktDtl.put("SR NO",Integer.toString(sr));
                String getPktPrp =
                    " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', nvl(to_char(trunc(a.num,2), '999990.09'),''), 'D', to_char(a.dte, 'dd-Mon-rrrr'), a.txt) val ,  grp "
                    + " from stk_dtl a, mprp b, rep_prp c "
                    + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'ASRT_LAB_COMP' and c.flg='Y' and a.mstk_idn = ? "
                    + " and grp in (1,2) order by a.grp desc  ";

                ArrayList params = new ArrayList();
                params.add(idn);
                ResultSet rs1 = db.execSql("stkDtl", getPktPrp, params);
                int pGrp = 0;
             
                while(rs1.next()){
                    int grp = rs1.getInt("grp");
                    if(pGrp==0)
                        pGrp=grp;
                    if(pGrp!=grp){
                        if(pGrp==2){
                            String getIssIdn = "select ISS_RTN_PKG.LST_ISS_ID(?,?) issIdn from dual";
                            params = new ArrayList();
                            params.add(idn);
                            params.add("FNL ASRT");
                            ResultSet rs2 = db.execSql("issIdn", getIssIdn, params);
                            if(rs2.next()){
                             int issIdn = rs2.getInt(1);
                             String issRtnVal = " select a.mprp , decode(b.dta_typ, 'C', c.prt1, 'N', to_char(a.rtn_num) , a.txt) rtn_val "+
                                              " from iss_rtn_prp a , mprp b, prp c where a.mprp = b.prp and b.prp = c.mprp and  "+
                                             " c.val = decode(b.dta_typ, 'C', a.rtn_Val, 'NA') and a.iss_id=? and a.iss_stk_idn = ? ";
                                params = new ArrayList();
                                params.add(String.valueOf(issIdn));
                                params.add(idn);
                                rs2 = db.execSql("issRtn", issRtnVal, params);
                                while(rs2.next()){
                                    String lprp = rs2.getString("mprp");
                                    String val = util.nvl(rs2.getString("rtn_val"));
                                    pktDtl.put(lprp, val);
                                }
                             
                            }
                            
                        }
                    pktList.add(pktDtl);
                    pktDtl = new HashMap();
                    pGrp=grp;
                    pktDtl.put("STK_IDN","");
                    pktDtl.put("VNM", "");
                    pktDtl.put("SR NO", "");
                    }
                    String lprp = rs1.getString("mprp");
                    String val = util.nvl(rs1.getString("val"));
                    if(lprp.equals("RAP_DIS"))
                        val=r_dis;
                    pktDtl.put(lprp, val);
                    
                }
                rs1.close(); pst.close();
                if(pGrp!=0){
                    
                    pktList.add(pktDtl);
                }
                
                
            }
            rs.close(); pst.close();
            
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        itmHdr = assortLabCom(itmHdr);
        itmHdr.add("RAP_RTE");
        itmHdr.add("RAP Vlu");
        req.setAttribute("ItemHdr", itmHdr);
       return pktList;
    }
    public ArrayList assortLabCom(ArrayList itemLst){


        try {

            ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp , flg  from rep_prp where mdl = 'ASRT_LAB_COMP' and flg in ('Y') order by rnk ",
                           new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs1 = (ResultSet)outLst.get(1);
            while (rs1.next()) {

            itemLst.add(rs1.getString("prp"));
               
                
            }
            rs1.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }

               
            
        return itemLst;
    }
    
    
    public LabComparisonRtnImpl() {
        super();
    }

}
