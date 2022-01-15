package ft.com.Repair;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.assort.AssortReturnForm;
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

public class RepairingReturnImpl implements RepairingReturnInterface {
    public ArrayList getOptions(HttpServletRequest req ,HttpServletResponse res, int prcId){
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

    String getPrcToPrc =  " select a1.in_stt from mprc a1, mprc a, prc_to_prc b "+
      " where a.idn = b.prc_id and a1.idn = b.prc_to_id  and a.idn = ? "+
       " order by b.srt ";

    ArrayList ary = new ArrayList();
    ary.add(String.valueOf(prcId));


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
        String empSql = "select nme_idn, nme from nme_v where typ in ('GROUP','MFG') order by nme";

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
        String issId = (String)params.get("issueId");
        String empIdn = (String)params.get("empIdn");
        HashMap dbinfo = info.getDmbsInfoLst();
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        String client = util.nvl((String)dbinfo.get("CNT"));
        ArrayList ary = null;
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_pkt_scan", new ArrayList());
        
        if(vnm.length()>2){
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
                
            vnmSub = vnmSub.replaceAll(",", "");
            vnmSub = vnmSub.replaceAll("''", ",");
            vnmSub = vnmSub.replaceAll("'", "");
              if(!vnmSub.equals("")){
            vnmSub="'"+vnmSub+"'";
            String insScanPkt = " insert into gt_pkt_scan select * from TABLE(PARSE_TO_TBL("+vnmSub+"))";
              ct = db.execDirUpd(" ins scan", insScanPkt,new ArrayList());
              System.out.println(insScanPkt);  
            }
            }
        }
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk) " + 
        " select  a.pkt_ty, a.idn, a.vnm,  a.dte, a.stt , 'AV' , a.qty , a.cts , b.iss_id , a.tfl3 "+
        "   from mstk a , iss_rtn_dtl b where a.idn = b.iss_stk_idn and b.stt='IS'  "+
        " and a.stt in ("+stt+") and a.cts > 0  ";
        ary = new ArrayList();
        
        if(vnm.length()>2){
            srchRefQ+=" and exists(select 1 from gt_pkt_scan c where ( a.vnm= c.vnm or a.tfl3 = a.vnm )) ";
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
        ary.add("REP_VIEW");
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
        ArrayList vwPrpLst = ASPrprViw(req,res);
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

        ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
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
    
    public ArrayList ASPrprViw(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("repViewLst");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();

                ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'REP_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                session.setAttribute("repViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
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
        ary.add("AV");
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
    public RepairingReturnImpl() {
        super();
    }
    
    public ArrayList StockUpdPrp(HttpServletRequest req,HttpServletResponse res, RepairingReturnForm form , HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList stockPrpList = new ArrayList();
        ArrayList updatePrpList = new ArrayList();
        HashMap mprp = info.getMprp();
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("REPAIR_RETURN");
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        String dflt_LabLoc="";
        
        if(pageDtl!=null && pageDtl.size() >0){  
             for(int i=0;i<pageDtl.size();i++){
            pageList= ((ArrayList)pageDtl.get("LOC") == null)?new ArrayList():(ArrayList)pageDtl.get("LOC");
            for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_LabLoc=(String)pageDtlMap.get("dflt_val");
         }
        }
        }
        String stockPrp = "select a.mprp ,a.iss_stk_idn,b.flg , Decode(C.Dta_Typ, 'C', A.Iss_Val, 'N', A.Iss_Num ,'D',format_to_date(a.txt), A.Txt) iss_val,\n" + 
        " Decode(C.Dta_Typ, 'C', A.rtn_Val, 'N', A.rtn_Num , 'D',format_to_date(a.txt), A.Txt) rtn_val\n" + 
        " from iss_rtn_prp a , prc_prp_alw b , mprp c where  a.mprp = b.mprp and  b.mprp=c.prp and\n" + 
        "                           a.iss_id=? and a.iss_stk_idn =?  and b.prc_idn=? order by b.srt ";

        ArrayList ary = new ArrayList();
        ary.add(params.get("issId"));
        ary.add(params.get("mstkIdn"));
        ary.add(params.get("prcId"));

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
                if(!flg.equals("FTCH"))
                    form.setValue(lprp,rtnVal);
                stockPrpUpd.put("mprp",lprp);
                stockPrpUpd.put("issVal",issVal);
                stockPrpUpd.put("rtnVal",rtnVal) ;
                stockPrpUpd.put("flg",flg);
                stockPrpUpd.put("mstkIdn",util.nvl(rs.getString("iss_stk_idn")));
               
                if(!flg.equals("FTCH"))
                    updatePrpList.add(lprp);
                stockPrpList.add(stockPrpUpd);
                if(!dflt_LabLoc.equals("") && lprp.equals("LOC"))
                form.setValue(lprp,dflt_LabLoc);
            }
            rs.close(); pst.close();
          
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        session.setAttribute("assortUpdPrp", updatePrpList);
        return stockPrpList;
    }
    
    public int mprcIdn(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        int prcIdn =0;
         String getMprcIdn = "select idn from mprc where is_stt='REP_IS'";

        ArrayList outLst = db.execSqlLst("getMprc", getMprcIdn, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
            prcIdn = rs.getInt(1);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return prcIdn;
    }
}
