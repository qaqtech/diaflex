package ft.com.lab;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.assort.AssortReturnForm;

import ft.com.dao.GtPktDtl;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

public class LabReturnImpl implements LabReturnInterface{
  
    public HashMap FecthResult(HttpServletRequest req, HttpServletResponse res, HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap stockList = new HashMap();
        String stt = (String)params.get("stt");
        String vnm = (String)params.get("vnm");
        String lab = util.nvl((String)params.get("lab"),"0");
        if(!lab.equals("0")){
        ArrayList ary = null;
      
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk , quot,rap_rte,rap_dis) " + 
       " select  a.pkt_ty, a.idn, a.vnm,  a.dte, a.stt , 'Z' , a.qty , a.cts , a.sk1 , a.tfl3 ,nvl(upr,cmp),rap_rte, decode(rap_rte ,'1',null,'0',null, trunc((nvl(upr,cmp)/greatest(rap_rte,1)*100)-100, 2))   "+   
        " from mstk a , stk_dtl b where  a.idn = b.mstk_idn and b.mprp='LAB_PRC' and b.grp=1 and "+
        " a.stt in ("+stt+") and b.val=? and "+
         " a.cts > 0  ";
      
        ary = new ArrayList();
       
        ary.add(lab);
        if(vnm.length()> 2){
          
          vnm = util.getVnm(vnm);
           
          srchRefQ = srchRefQ+" and ( a.vnm in ("+vnm+") or a.tfl3 in ("+vnm+") ) " ;
         
        }
      
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("LAB_VIEW");
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
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap pktList = new HashMap();
        ArrayList vwPrpLst =  LabPrprViw(req,res);
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , rmk , srch_id ,to_char(cts * quot, '99999990.00') amt,to_char(cts * rap_rte, '99999990.00') rapvlu    ";

        

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
            String stkIdn = util.nvl(rs.getString("stk_idn"));

              GtPktDtl pktPrpMap = new GtPktDtl();
                pktPrpMap.setValue("stt", util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.setValue("vnm",vnm);
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
                pktPrpMap.setValue("stk_idn",util.nvl(rs.getString("stk_idn")));
                pktPrpMap.setValue("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.setValue("cts",util.nvl(rs.getString("cts")));
                pktPrpMap.setValue("AMT",util.nvl(rs.getString("amt")));
                pktPrpMap.setValue("RAPVAL",util.nvl(rs.getString("rapvlu")));
                
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                     
                        
                        pktPrpMap.setValue(prp, val);
                         }
                       pktList.put(stkIdn, pktPrpMap);      
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
    public ArrayList StockUpdPrp(HttpServletRequest req, HttpServletResponse res, LabReturnForm form , HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        LabReturnForm labRtnForm = (LabReturnForm)form;
        String stkIdn = (String)params.get("mstkIdn");
        String issuId = (String)params.get("issueId");
        String prcId = (String)params.get("prcId");
        ArrayList stockPrpList = new ArrayList();
        ArrayList updatePrpList = new ArrayList();
        HashMap mprp = info.getMprp();
      String stockPrp = "select a.mprp ,a.iss_stk_idn, b.flg" +
                        ",  Decode(C.Dta_Typ, 'C', A.Iss_Val, 'N', A.Iss_Num ,'D',format_to_date(a.txt),  A.Txt) iss_val,\n" + 
                        " Decode(C.Dta_Typ, 'C', A.rtn_Val, 'N', A.rtn_Num ,'D',format_to_date(a.txt), A.Txt) rtn_val   "+
                        " from iss_rtn_prp a , prc_prp_alw b , mprp c where  a.mprp = b.mprp and b.mprp=c.prp and " +
                        "a.iss_id=? and a.iss_stk_idn = ?  and b.prc_idn =? order by b.srt ";

        ArrayList ary = new ArrayList();
        ary.add(issuId);
        ary.add(String.valueOf(stkIdn));
        ary.add(prcId);

        ArrayList outLst = db.execSqlLst("stockDtl", stockPrp, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                HashMap stockPrpUpd = new HashMap();
                String lprp = util.nvl(rs.getString("mprp"));
                String issVal = util.nvl(rs.getString("iss_val"));
                String rtnVal = util.nvl(rs.getString("rtn_val"));
              
                String flg = util.nvl(rs.getString("flg"));
                form.setValue(lprp, rtnVal);
                stockPrpUpd.put("mprp",lprp);
                stockPrpUpd.put("issVal",issVal);
                stockPrpUpd.put("rtnVal",rtnVal) ;
                stockPrpUpd.put("flg",flg);
                stockPrpUpd.put("mstkIdn",util.nvl(rs.getString("iss_stk_idn")));
               
                if(!flg.equals("FTCH"))
                    updatePrpList.add(lprp);
                stockPrpList.add(stockPrpUpd);
               
            }
            
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        session.setAttribute("assortUpdPrp", updatePrpList);
        labRtnForm.setValue("issIdn", issuId);
        labRtnForm.setValue("mstkIdn", stkIdn);
        labRtnForm.setValue("prcId", prcId);
        return stockPrpList;
    }
    
  
    public int lstIssueId(HttpServletRequest req , HttpServletResponse res, String stkIdn){
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
        ary.add(stkIdn);
        int issIdn =0;
        String getIssIdn = "select ISS_RTN_PKG.LST_ISS_ID(?) issIdn from dual";

        ArrayList outLst = db.execSqlLst("issIdn", getIssIdn, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if (rs.next()) {
                issIdn = rs.getInt(1);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return issIdn;
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
             gtTotalMap.put("qty",util.nvl(rs.getString("qty")));
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
        ArrayList viewPrp = (ArrayList)session.getAttribute("LabViewLst");
        try {
            if (viewPrp == null) {

                viewPrp = new ArrayList();
                ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'LAB_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    viewPrp.add(rs1.getString("prp"));
                }
                rs1.close(); pst.close();
                session.setAttribute("LabViewLst", viewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return viewPrp;
    }
    public LabReturnImpl() {
        super();
    }
}
