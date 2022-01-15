package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.assort.AssortIssueInterface;
import ft.com.dao.MNme;

import ft.com.dao.Mprc;

import ft.com.dao.ObjBean;

import ft.com.dao.UIForms;

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

public class MixRtnMatrixImpl  implements MixRtnMatrixInterface {
   
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
    public MixRtnMatrixImpl() {
        super();
    }
    
    public ArrayList dpMruleDtl(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList mixDpLst = new ArrayList();
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        String    memoPrntOptn = "select chr_fr, dsc ,chr_to from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                                 + "b.mdl = 'MIXDP' and b.nme_rule =  'MIXDP' and a.til_dte is null order by a.srt_fr ";
      
        ArrayList  rsLst = db.execSqlLst("memoPrint", memoPrntOptn, new ArrayList());
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while (rs.next()) {
                ArrayList mixDp = new ArrayList();
                mixDp.add(rs.getString("dsc"));
                mixDp.add(rs.getString("chr_fr"));
                mixDp.add(rs.getString("chr_to"));
                mixDpLst.add(mixDp);
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return mixDpLst;
    }
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
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
      }
    return mixSizeList;
    }
//    public ArrayList MIXGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("MIXRtNMatrixGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp,flg  from rep_prp where mdl = 'MIX_RTNMAX_SRCH' and flg in ('Y','S','M') order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                 rs1.close();
//                session.setAttribute("MIXRtNMatrixGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
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
        String prcSql = "select idn, prc , in_stt from mprc where  grp ='MEMO_MIX' " ;
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
    public HashMap FecthResult(HttpServletRequest req, HttpServletResponse res, HashMap params){
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
     
        String stt = (String)params.get("stt");
        String issueId = util.nvl((String)params.get("issueId"));
      
        ArrayList ary = null;
      
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", " Delete from gt_srch_asrt ", new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
          ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_rslt", new ArrayList());
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk , srch_id , cert_lab ) " + 
        " select  b.pkt_ty, b.idn, b.vnm,  b.dte, b.stt , 'Z' , b.qty , b.cts , b.sk1 , b.tfl3  , a.iss_id , cert_lab   "+
        "     from mstk b , "+
         " iss_rtn_dtl a where b.idn = a.iss_stk_idn and a.stt='IS'  "+
         " and b.stt =? and  b.cts is not null  ";
      
        ary = new ArrayList();
        ary.add(stt);
        if(!issueId.equals("")){
            srchRefQ = srchRefQ+" and a.iss_id = ? " ;
            ary.add(issueId);
        }
      
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("MIX_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        ArrayList vwPrpLst =  MixPrprViw(req,res);
        int inxMemo = vwPrpLst.indexOf("MEMO")+1;
        int inxSH = vwPrpLst.indexOf("SHAPE")+1;
        String prpMemo = "prp_00"+inxMemo;
        if(inxMemo>9)
        prpMemo = "prp_0"+inxMemo;
        
        String prpSH = "prp_00"+inxSH;
        if(inxSH>9)
        prpSH = "prp_0"+inxSH;
        boolean isVaild = true;
      
        try {
            String distinctPRP ="select distinct "+prpMemo+" from gt_srch_rslt";
          ArrayList  rsLst = db.execSqlLst("dis",distinctPRP, new ArrayList());
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
            int cnt =0;
            String memoIds="";
            while (rs.next()) {
                String memo = util.nvl(rs.getString(1));
                if(!memo.equals("")){
                  cnt++;
                memoIds = memoIds +" , "+util.nvl(rs.getString(1));
                }
            }
            rs.close();
            stmt.close();
            if(cnt > 1){
                isVaild = false;
                req.setAttribute("msg1","Given Issue Id Having multiple memo Id ("+memoIds+") please specify memoID.");
            }else{
                 distinctPRP ="select distinct "+prpSH+" from gt_srch_rslt";
               rsLst = db.execSqlLst("dis",distinctPRP, new ArrayList());
               stmt =(PreparedStatement)rsLst.get(0);
             rs =(ResultSet)rsLst.get(1);
               cnt =0;
                String shVal = "";
                while (rs.next()) {
                    String sh = util.nvl(rs.getString(1));
                    if(!sh.equals("")){
                      cnt++;
                    shVal = shVal+" , "+util.nvl(rs.getString(1));
                    }
                }
                rs.close();
                stmt.close();
                if(cnt > 1){
                    isVaild = false;
                    req.setAttribute("msg1","Given Issue Id Having multiple Shape ("+shVal+") please specify Shape.");
                    
                }
            }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        HashMap prpLst = info.getPrp();
        ArrayList mixSizeV = (ArrayList)prpLst.get("MIX_SIZEV");
        ArrayList mixSizeS = (ArrayList)prpLst.get("MIX_SIZES");
        
        if(isVaild){
        String srchQ = " select stk_idn ";
        for (int i = 0; i < vwPrpLst.size(); i++) {
            String fld = "prp_";
             String srt = "srt_";
            int j = i + 1;
            if (j < 10){
                fld += "00" + j;
                srt += "00" + j;
            } else if (j < 100){
                fld += "0" + j;
                srt += "0" + j;
            }else if (j > 100){
                fld += j;
                srt += j;
            }

            srchQ += ", "+srt+" , "+ fld;
         }
 
        
        String rsltQ = srchQ + " from gt_srch_rslt " ;
        ary = new ArrayList();
     
         ArrayList rsLst = db.execSqlLst("gt fetch", rsltQ, ary);
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
         ResultSet rs =(ResultSet)rsLst.get(1);
        ArrayList sizeLst = new ArrayList();
        String szLst = "";
        try {
            while(rs.next()) {
            
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      String srt ="srt_";
                    if(j < 9){
                              fld="prp_00"+(j+1);
                               srt="srt_00"+(j+1);
                    } else {   
                              fld="prp_0"+(j+1);
                              srt="srt_0"+(j+1);
                    }
                      
                      String val = util.nvl(rs.getString(fld)).trim() ;
                    String srtVal = util.nvl(rs.getString(srt)) ;
                    if(!val.equals("")){
                    if(prp.equals("SHAPE"))
                        stockList.put(prp, srtVal);
                    if(prp.equals("MEMO"))
                        stockList.put(prp, val);
                    }
                    if(prp.equals("MIX_SIZE")){
                        val = (String)mixSizeV.get(mixSizeS.indexOf(srtVal));
                        if(!sizeLst.contains(val) && !val.equals((""))){
                            sizeLst.add(val);
                            szLst = szLst+"','"+val;
                        }
                        
                    }
                    
                }
             
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        stockList.put("sizeStr",szLst);
        stockList.put("sizeLst",sizeLst);
        }
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
  
}
