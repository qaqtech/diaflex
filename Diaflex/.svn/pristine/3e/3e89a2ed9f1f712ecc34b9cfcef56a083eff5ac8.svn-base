package ft.com.marketing;

import ft.com.DBMgr;

import ft.com.DBUtil;
import ft.com.GenMail;
import ft.com.InfoMgr;

import ft.com.dao.ByrDao;

import ft.com.dao.GtPktDtl;
import ft.com.dao.MAddr;
import ft.com.dao.MemoType;
import ft.com.dao.RepPrp;
import ft.com.dao.TrmsDao;

import ft.com.dao.UIForms;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.mpur.PurchaseDmdForm;
import ft.com.report.CombinationReportForm;

import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;

import java.math.BigDecimal;

import java.net.InetSocketAddress;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.spy.memcached.MemcachedClient;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.upload.FormFile;

public class SearchQuery {
    
   
//    public HashMap StockViewLyt(HttpServletRequest req , HttpServletResponse res){
//        HttpSession session = req.getSession(false);
//        InfoMgr info = (InfoMgr)session.getAttribute("info");
//        DBUtil util = new DBUtil();
//        DBMgr db = new DBMgr(); 
//        db.setCon(info.getCon());
//        util.setDb(db);
//        util.setInfo(info);
//        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//        util.setLogApplNm(info.getLoApplNm());
//        HashMap stockViewMap = (session.getAttribute("stockViewMap") == null)?new HashMap():(HashMap)session.getAttribute("stockViewMap");
//        try {
//            if(stockViewMap.size() == 0) {
//            HashMap dbinfo = info.getDmbsInfoLst();
//            String cnt=util.nvl((String)dbinfo.get("CNT"));
//            String mem_ip=util.nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//            int mem_port=Integer.parseInt(util.nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//            MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//            stockViewMap=(HashMap)mcc.get(cnt+"_stockViewMap");
//            if(stockViewMap==null){
//            stockViewMap=new HashMap();
//            String gtView = "select chr_fr, chr_to , dsc , num_fr, dta_typ , rem , lvl from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + 
//                            " b.mdl = 'JFLEX' and b.nme_rule = 'STOCK_VIEW' and a.til_dte is null order by a.srt_fr ";
//            ResultSet rs = db.execSql("gtView", gtView, new ArrayList());
//            while (rs.next()) {
//                HashMap vwMap = new HashMap();
//               String prp = util.nvl(rs.getString("dsc"));
//                vwMap.put("nme", util.nvl(rs.getString("chr_to")));
//                vwMap.put("url",util.nvl(rs.getString("chr_fr")));
//                vwMap.put("typ",util.nvl(rs.getString("dta_typ")));
//                vwMap.put("view",util.nvl(rs.getString("num_fr")));
//              vwMap.put("dir",util.nvl(rs.getString("rem")));
//              vwMap.put("Hed", util.nvl(rs.getString("lvl")));
//                stockViewMap.put(prp, vwMap);
//            }
//            rs.close();
//            Future fo = mcc.delete(cnt+"_stockViewMap");
//            System.out.println("add status:_stockViewMap" + fo.get());
//            fo = mcc.set(cnt+"_stockViewMap", 24*60*60, stockViewMap);
//            System.out.println("add status:_stockViewMap" + fo.get());
//            }
//            mcc.shutdown();
//            session.setAttribute("stockViewMap", stockViewMap);
//        }
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//            }catch(Exception ex){
//             System.out.println( ex.getMessage() );
//            }
//        info.setStockViewMap(stockViewMap);
//        return stockViewMap;
//    }
  public ArrayList getEmpList(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      ResultSet rs = null;
    ArrayList byrList = (ArrayList)session.getAttribute("empList");
    if(byrList==null){
    byrList = new ArrayList();
        
      String sql = "  select nme_idn,initcap(trim(nme)) nme from nme_v a where typ = 'EMPLOYEE' " + 
        "              and exists (select 1 from mnme a1, nmerel b where b.nme_idn = a1.nme_idn and a.nme_idn = a1.emp_idn and b.vld_dte is null and b.flg = 'CNF') " + 
        "             order by initcap(trim(nme))";
    
         
      rs = db.execSql("byr", sql, new ArrayList());
      ByrDao byr = new ByrDao();
      byr.setByrIdn(0);
      byr.setByrNme("None");
      byrList.add(byr);

      try {
          while (rs.next()) {
              byr = new ByrDao();
              byr.setByrIdn(rs.getInt("nme_idn"));
              byr.setByrNme(util.nvl(rs.getString("nme")));
              byrList.add(byr);
          }
          rs.close();
      } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
      }
      session.setAttribute("empList", byrList);
    }
      return byrList;
  }
    
    public ArrayList getByrList(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
       
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        
        ArrayList byrList = (ArrayList)session.getAttribute("byrList");
        if(byrList==null){
            String getByrCnt = " select count(*) cnt from mnme where emp_idn = ? " ;
            ArrayList ary = new ArrayList();
            ary.add(info.getDfNmeIdn());
            int byrCnt = 0 ;
            
          ArrayList  outLst = db.execSqlLst(" get cnt", getByrCnt, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            
            try {
                if (rs.next()) {
                    byrCnt = rs.getInt(1);
                }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            
            byrList = new ArrayList();
            ary = new ArrayList();
        String sql = "  select nme_idn,initcap(trim(nme)) nme from nme_v a where typ = 'EMPLOYEE' " + 
          "              and exists (select 1 from mnme a1, nmerel b where b.nme_idn = a1.nme_idn and a.nme_idn = a1.emp_idn and b.vld_dte is null and b.flg = 'CNF') ";
        if(cnt.equals("kj")){
            sql+=" and not exists (select 1 from mdept a,dept_emp_valid b where a.idn=b.dept_idn and stt='IA' and a.dept='Sales' and a.nme_idn=b.emp_idn) ";
        }
         sql+= "             order by initcap(trim(nme))";
        if(byrCnt > 0) {
            info.setIsSalesExec(true);
            sql = "  select nme_idn,initcap(trim(nme)) nme from nme_v a where typ = 'EMPLOYEE' " + 
          "              and exists (select 1 from mnme a1, nmerel b where b.nme_idn = a1.nme_idn and a.nme_idn = a1.emp_idn and b.vld_dte is null and b.flg = 'CNF') " + 
                  " and (a.nme_idn= ? or a.nme_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) "+
          "             order by initcap(trim(nme))";
            
           ary.add(info.getDfNmeIdn());
            ary.add(info.getDfNmeIdn());
        }
           
            outLst = db.execSqlLst("byr", sql, ary);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        ByrDao byr = new ByrDao();
        byr.setByrIdn(0);
        byr.setByrNme("None");
        byrList.add(byr);

        try {
            while (rs.next()) {
                byr = new ByrDao();
                byr.setByrIdn(rs.getInt("nme_idn"));
                byr.setByrNme(util.nvl(rs.getString("nme")));
                byrList.add(byr);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }}
        session.setAttribute("byrList", byrList);
        return byrList;
    }
    
   
    public ArrayList getByrListDmd(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList byrList = new ArrayList();
        ArrayList ary = new ArrayList();
        String sql = "  select nme_idn,initcap(trim(nme)) nme from nme_v a where typ = 'EMPLOYEE'\n" + 
        "and exists (select distinct b.nme_idn from \n" + 
        "mdmd a1,mnme b where b.nme_idn = a1.name_id and a.nme_idn = b.emp_idn and a1.toDte is null) \n" + 
        "order by initcap(trim(nme))";
      ArrayList  outLst = db.execSqlLst("byr", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        ByrDao byr = new ByrDao();
        byr.setByrIdn(0);
        byr.setByrNme("None");
        byrList.add(byr);

        try {
            while (rs.next()) {
                byr = new ByrDao();
                byr.setByrIdn(rs.getInt("nme_idn"));
                byr.setByrNme(util.nvl(rs.getString("nme")));
                byrList.add(byr);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("byrListDmd", byrList);
        return byrList;
    }
    
    public void iememov(HttpServletRequest req,int nmeIdn){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList iememov = new ArrayList();
        ArrayList ary = new ArrayList();
        ary.add(String.valueOf(nmeIdn));
        String sql = "select mstk_idn from ie_memo_v where nme_idn=?";
      ArrayList  outLst = db.execSqlLst("byr", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                ary.add(util.nvl(rs.getString("mstk_idn")));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        info.setIememov(iememov);
    }
    
    public ArrayList getBrokerList (HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList brokerList = (session.getAttribute("brokerList") == null)?new ArrayList():(ArrayList)session.getAttribute("brokerList");
        try {
            if(brokerList.size() == 0) {
            String sql = " select nme_idn , initcap(trim(nme)) nme from nme_v  where typ='BROKER'" + 
                         " order by initcap(trim(nme)) ";
          ArrayList  outLst = db.execSqlLst("sql", sql, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                ByrDao byr = new ByrDao();
                byr.setByrIdn(rs.getInt("nme_idn"));
                byr.setByrNme(util.nvl(rs.getString("nme")));
                brokerList.add(byr);
            }
            rs.close();
            pst.close();
            session.setAttribute("brokerList", brokerList);
        }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return brokerList;
    }
    public ArrayList getPartyList (HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList partyList = (ArrayList)session.getAttribute("srchpartyList");
        ArrayList ary = new ArrayList();
        if(partyList==null){
        String party = "select nme_idn, initcap(trim(nme)) nme from nme_v a " +
            " where exists (select 1 from nmerel b where a.nme_idn = b.nme_idn and " +
            " b.vld_dte is null and flg = 'CNF') and a.ss_flg='Y' order by initcap(trim(nme))";
        if(info.getIsSalesExec()) {
            party = "select nme_idn, initcap(trim(nme)) nme from nme_v a " +
                        " where a.emp_idn = ? " +
                "  and exists (select 1 from nmerel b where a.nme_idn = b.nme_idn and " +
                        " b.vld_dte is null and flg = 'CNF') and ss_flg='Y' order by initcap(trim(nme))";
            ary.add(info.getDfNmeIdn());
        }
         partyList = new ArrayList();
          ArrayList  outLst = db.execSqlLst("byr", party, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                ByrDao byr = new ByrDao();
                byr.setByrIdn(rs.getInt("nme_idn"));
                byr.setByrNme(util.nvl(rs.getString("nme")));
                partyList.add(byr);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        }
        session.setAttribute("srchpartyList", partyList);
        return partyList;
    }
    public ArrayList getDmdPartyList (HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String party = "select nme_idn, nme from nme_v a , mdmd b" +
            " where exists (select 1 from nmerel b where a.nme_idn = b.nme_idn and b.vld_dte is null and flg = 'CNF') and  a.nme_idn = b.name_id order by nme";
        ArrayList partyList = new ArrayList();
      ArrayList  outLst = db.execSqlLst("byr", party, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                ByrDao byr = new ByrDao();
                byr.setByrIdn(rs.getInt("nme_idn"));
                byr.setByrNme(util.nvl(rs.getString("nme")));
                partyList.add(byr);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return partyList;
    }
    
    public ArrayList getPartyListDmd (HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList partyList = new ArrayList();
        ArrayList ary = new ArrayList();
        String party = "select nme_idn, initcap(trim(nme)) nme from nme_v a \n" + 
        "where exists (select distinct b.name_id from mdmd b where a.nme_idn = b.name_id and b.toDte is null) and a.ss_flg='Y' order by initcap(trim(nme))\n";
         partyList = new ArrayList();
      ArrayList  outLst = db.execSqlLst("byr", party, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                ByrDao byr = new ByrDao();
                byr.setByrIdn(rs.getInt("nme_idn"));
                byr.setByrNme(util.nvl(rs.getString("nme")));
                partyList.add(byr);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("srchpartyListDmd", partyList);
        return partyList;
    }
    public void popWL(HttpServletRequest req,String rlnId, String grpNme) {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
        String usrId="0";
      ArrayList ary = new ArrayList();
      ary.add(rlnId);
       String webUsr = "select usr_id from web_usrs where rel_idn=? ";
       if(cnt.equals("hk")){
           webUsr = "select distinct c.usr_id from web_bid_wl a, web_usrs c where a.usr_idn = c.usr_id and c.rel_idn = ? and trunc(nvl(a.to_dt, sysdate)) >= trunc(sysdate) and a.typ = 'WL' and a.stt = 'A' and grp_nme = ? ";
           ary.add(grpNme);
       }
    ArrayList  outLst = db.execSqlLst("webUsr", webUsr,ary);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                usrId = rs.getString("usr_id");
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    String updWl = "pkgmkt.popWl(pUsrId => ? ,pRelIdn => ?,pMdl=> ? , pTyp=> ?, pGrpNme => ?) ";
    ary = new ArrayList();
    ary.add(usrId);
    ary.add(rlnId);
    ary.add(info.getVwMdl());    
    ary.add("WL");
    ary.add(grpNme);
    int ct = db.execCall(" Pop WL", updWl, ary);
    if(ct>0){
        ct = db.execUpd("update gt_srch ", "update gt_srch_rslt set flg='Z' where flg='W' ", new ArrayList());
    }
        
      
    
  }
    public int saveCombSearch(HttpServletRequest req,HttpServletResponse res , CombinationReportForm form) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap prp;
        HashMap mprp;
    ArrayList params = new ArrayList();
    ArrayList srchPrpLst=null;
    ArrayList prpCombvw= info.getPrpCombvw();
    String stt=req.getParameter("stt");
    String dltgt="Delete from gt_srch_rslt";
    int ctl = db.execCall("delete gt", dltgt, new ArrayList());

    int lSrchId=0;
    String getSrchId =
    "srch_pkg.get_srch_id(pRlnId => ?, pTyp => ?, pTrmVal => ?, pMdl => ?, pFlg => ?, pSrchId => ?)";
    params = new ArrayList();
    params.add(String.valueOf(info.getRlnId()));
    params.add(stt);
    params.add(Double.toString(info.getCmpTrm()));
    params.add("WEB");
    params.add("");

    ArrayList outParams = new ArrayList();
    outParams.add("I");

    CallableStatement cst =
    db.execCall(" Add Srch Hdr ", getSrchId, params, outParams);
    try {
    lSrchId = cst.getInt(params.size() + 1);
      cst.close();
      cst=null;
    } catch (SQLException e) {
    e.printStackTrace();
    }
    if (lSrchId > 0) {
    prp = info.getPrp();
    mprp = info.getMprp();
    String addSrchDtl =
    "srch_pkg.add_srch_dtl(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)"; // CRTWT
    String addSrchDtlVal =
    "srch_pkg.add_srch_dtl_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
    // String addSrchDtlSub =
    // "srch_pkg.add_srch_dtl_sub(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
    String addSrchDtlSubVal =
    "srch_pkg.add_srch_dtl_sub_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
    // String addSrchDtlDte =
    // "srch_pkg.add_srch_dtl_dte(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
    for (int i = 0; i < prpCombvw.size(); i++) {
    boolean dtlAddedOnce = false;
    // ArrayList srchPrp = (ArrayList)srchPrpLst.get(i);
    /* String lprp = (String)srchPrp.get(0);
    String flg = (String)srchPrp.get(1);
    String prpSrt = lprp;
    String lprpTyp = util.nvl((String)mprp.get(lprp + "T")); */
        ArrayList prpVw = (ArrayList)prpCombvw.get(i);
    String lprp=(String)prpVw.get(0);
    String prpSrt = lprp;
    int cnt=0;

    ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
    ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");

    /* if ((buttonPressed.equalsIgnoreCase("refine")) &&
    (lprpS != null))
    flg = "M"; */

    // if (flg.equals("M")) {
    if (lprp.equalsIgnoreCase("SIZE")) {
    String wtFr =
    util.nvl((String)form.getValue("wt_fr_c"));
    String wtTo =
    util.nvl((String)form.getValue("wt_to_c"));
    if ((wtFr.length() > 0) && (wtTo.length() > 0)) {
    params = new ArrayList();
    params.add(String.valueOf(lSrchId));
    params.add(lprp);
    params.add(wtFr);
    params.add(wtTo);

    cnt =
    db.execCall(" SrchDtl ", addSrchDtl, params);
    }
    } else {
    //All Other Multiple Properties

    for (int j = 0; j < lprpS.size(); j++) {
    String lSrt = (String)lprpS.get(j);
    String lVal = (String)lprpV.get(j);
    String lFld = lprp + "_" + lSrt;
    String reqVal =
    util.nvl((String)form.getValue(lFld));
    //util.SOP(lprp + " : " + lFld + " : " + reqVal);
    if (reqVal.length() > 0) {
    if (!dtlAddedOnce) {
    params = new ArrayList();
    params.add(String.valueOf(lSrchId));
    params.add(lprp);
    params.add(reqVal);
    params.add(reqVal);
    cnt =
    db.execCall(" SrchDtl ", addSrchDtlVal, params);
    dtlAddedOnce = true;
    }
    params = new ArrayList();
    params.add(String.valueOf(lSrchId));
    params.add(lprp);
    params.add(reqVal);
    params.add(reqVal);
    cnt =
    db.execCall(" SrchDtl ", addSrchDtlSubVal, params);
    }
    }
    }



    }

    String dbSrch = " srch_pkg.STK_SRCH(? ,? )";
    params = new ArrayList();
    params.add(String.valueOf(lSrchId));
    params.add("COMB_VIEW");
    int cnt = db.execCall(" Srch : " + lSrchId, dbSrch, params);
    System.out.println("count"+cnt);

    }
    return lSrchId;
    }

  public String srchDscription(HttpServletRequest req, String lSrchId) {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      String dsc = "";
      String getSrchDscr = "Select web_pkg.get_srch_dscr(?) dscr from dual ";
      ArrayList ary = new ArrayList();
      ary.add(lSrchId);
      
    ArrayList  outLst = db.execSqlLst(" srch dscr", getSrchDscr, ary);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
      try {
        if(rs.next()) {
          dsc = rs.getString(1);
        }
          rs.close();
          pst.close();
      } catch (SQLException e) {
      }
      return dsc ;
  }
  
  public int saveSearch(HttpServletRequest req, SearchForm form, String buttonPressed, String soldDay) {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      HashMap prp;
      HashMap mprp;
    ArrayList srchPrpLst = new ArrayList();
    ArrayList advPrpLst = info.getAdvPrpLst();
    ArrayList includeexcludeList = (session.getAttribute("INCLUDE_EXEC_VW") == null)?new ArrayList():(ArrayList)session.getAttribute("INCLUDE_EXEC_VW");
    srchPrpLst.addAll(info.getSrchPrpLst());
      String isMix = util.nvl(info.getIsMix(),"NR");
    if(advPrpLst!=null && advPrpLst.size()>0)
      srchPrpLst.addAll(advPrpLst);
    if(buttonPressed.equalsIgnoreCase("refine"))
      srchPrpLst = info.getRefPrpLst();
    //util.SOP(form.toString());
    if(buttonPressed.equals("mixsrch"))
        srchPrpLst = info.getMixScrhLst();
    if(buttonPressed.equals("rghSrch"))
          srchPrpLst = info.getRghScrhLst();
    if(isMix.equals("SMX"))
        srchPrpLst = info.getSmxScrhLst();
    String isEx = util.nvl((String)form.getValue("is2Ex"));
    if(isEx.equals(""))
        isEx = util.nvl((String)form.getValue("is3Ex"));
    if(isEx.equals(""))
        isEx = util.nvl((String)form.getValue("is3VG"));
    if(isEx.equals(""))
        isEx = util.nvl((String)form.getValue("is3VG_UP"));
    
    String oldSrchId = util.nvl((String)form.getValue("oldsrchid"));
    ArrayList params = null;
    int cnt =0;
    ArrayList isExParams = new ArrayList();
      HashMap dbinfo = info.getDmbsInfoLst();
      String cutlprp = (String)dbinfo.get("CUT");
      String pollprp = (String)dbinfo.get("POL");
      String symlprp = (String)dbinfo.get("SYM");
    int lSrchId = 0 ;
      String srchTyp = util.nvl(info.getIsFancy());
      String mdl="WEB";
      if(srchTyp.equals("FCY"))
      mdl="FNCY";
      String pair=util.nvl((String)form.getValue("pairs"));
      if(!pair.equals(""))
          pair="MATCHPAIR";
      
    if(oldSrchId.length() > 0) {
      lSrchId = Integer.parseInt(oldSrchId);
    } else {
      String pri_chng_typ = util.nvl((String)form.getValue("PRI_CHNG_TYP"));
      String pri_chng_val = util.nvl((String)form.getValue("PRI_CHNG_VAL"));
      String getSrchId = "srch_pkg.get_srch_id(pRlnId => ?, pTyp => ?,pSrchMdl=>? , pTrmVal => ?, pMdl => ?, pFlg => ?, pXrt => ? , pSrchId => ?)";
      params = new ArrayList();
      params.add(String.valueOf(info.getRlnId()));
      params.add("DF");  
      params.add("MEMO_SRCH");
      params.add(Double.toString(info.getCmpTrm()));
      params.add(mdl);
      if(pair.equals(""))  
      params.add(isEx);  
      else 
      params.add(pair); 
      params.add(form.getValue("xrt")); 
      if(!pri_chng_val.equals("")){
          getSrchId = "srch_pkg.get_srch_id(pRlnId => ?, pTyp => ?,pSrchMdl=>? , pTrmVal => ?, pMdl => ?, pFlg => ?, pXrt => ? ,pPriChngTyp => ? ,pPriChngVal => ?, pSrchId => ?)"; 
          params.add(pri_chng_typ); 
          params.add(pri_chng_val); 
      }
      ArrayList outParams = new ArrayList();
      outParams.add("I");
        
       CallableStatement cst = db.execCall(" Add Srch Hdr ", getSrchId, params, outParams);
        try {
          lSrchId = cst.getInt(params.size()+1);
          cst.close();
          cst=null;
        } catch (SQLException e) {
        }
      }
    
      if(info.getSearchView().equals("LIST") && !isEx.equals("") && info.getDmbsInfoLst().get("CNT").equals("ag")){
          info.setIsEx(isEx);
          isEx="";
      }
      if(isEx.length() > 0) {
      isExParams.add(cutlprp);
      isExParams.add(pollprp);
      isExParams.add(symlprp);
      }
    
    if(lSrchId > 0) {
      ArrayList srchTypList = (session.getAttribute("srchTypList_"+isMix) == null)?new ArrayList():(ArrayList)session.getAttribute("srchTypList_"+isMix);
        ArrayList srchTypListDtl=new ArrayList();
        String insrtAddon = " insert into srch_addon( srch_id , cprp , cval) "+
                   "select ? , ? , ?  from dual ";
        boolean srchaddonInt=false;
        for(int i=0;i<srchTypList.size();i++){
            srchTypListDtl=new ArrayList();
            srchTypListDtl=(ArrayList)srchTypList.get(i);
            String srchstt=util.nvl((String)form.getValue("SRCHSTT_"+util.nvl((String)srchTypListDtl.get(1))));
            if(!srchstt.equals("")){
           String[] srSttStr = srchstt.split(",");
            for(int j=0;j<srSttStr.length;j++){
                String srStt = srSttStr[j];
                params = new ArrayList();
                params.add(String.valueOf(lSrchId));
                params.add("STT");
                params.add(srStt);
                int ct = db.execUpd("", insrtAddon, params);
                srchaddonInt=true;
            }}
        }
        if(!srchaddonInt){
            params = new ArrayList();
            params.add(String.valueOf(lSrchId));
            params.add("STT");
            params.add("MKAV");
            int ct = db.execUpd("", insrtAddon, params);
        }
      prp = info.getPrp();
      mprp = info.getMprp();
      String addSrchDtl = "srch_pkg.add_srch_dtl(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
      String addSrchDtlVal = "srch_pkg.add_srch_dtl_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
      String addSrchDtlSub = "srch_pkg.add_srch_dtl_sub(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
      String addSrchDtlSubVal = "srch_pkg.add_srch_dtl_sub_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
      String addSrchDtlDte = "srch_pkg.add_srch_dtl_dte(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
      String addSrchDtlDteinclude = "srch_pkg.add_srch_dtl_dte(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?,pIncExc => ?)";
      String addSrchDtlValinclude = "srch_pkg.add_srch_dtl_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?,pIncExc => ?)";
      for (int i = 0; i < srchPrpLst.size(); i++) {
        boolean dtlAddedOnce = false ;
        ArrayList srchPrp = (ArrayList)srchPrpLst.get(i);
        String lprp = (String)srchPrp.get(0);
        String flg= (String)srchPrp.get(1);
        String prpSrt = lprp ;  
        String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
        if(isExParams.contains(lprp)) {
          // 2EX clause tp be ignored          
        } else {
          if(lprp.equals("CRTWT"))
              prpSrt = "SIZE";
          ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
            if(lprpS==null){
                prpSrt = "SZ";
                lprpS = (ArrayList)prp.get(prpSrt + "S");
            }
          ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
          ArrayList lprpP = (ArrayList)prp.get(prpSrt + "P");

          if((buttonPressed.equalsIgnoreCase("refine")) && (lprpS!=null))
              flg = "M";
          
          if((flg.equals("M") && (lprpTyp.equals("C"))) || lprp.equals("CRTWT") || (flg.equals("CTA") && (lprpTyp.equals("C"))) || flg.equals("KT")) {
              if(lprp.equalsIgnoreCase("CRTWT")) {
                String wtFr = util.nvl((String)form.getValue("wt_fr_c"));
                String wtTo = util.nvl((String)form.getValue("wt_to_c"));
                if((wtFr.length() > 0) && (wtTo.length() > 0)) {
                  params = new ArrayList();
                  params.add(String.valueOf(lSrchId));
                  params.add(lprp);
                  params.add(wtFr);
                  params.add(wtTo);
                  
                  cnt = db.execCall(" SrchDtl ", addSrchDtl, params);
                } else {
                    for(int j=0; j < lprpS.size(); j++) {
                      String lSrt = (String)lprpS.get(j);
                      String reqVal1 = util.nvl((String)form.getValue(lprp + "_1_" + lSrt),"");
                      String reqVal2 = util.nvl((String)form.getValue(lprp + "_2_" + lSrt),"");
                        if((reqVal1.length() == 0) || (reqVal2.length() == 0)) {
                          //ignore no value selected;
                        } else {
                            if(!dtlAddedOnce) {
                              params = new ArrayList();
                              params.add(String.valueOf(lSrchId));
                              params.add(lprp);
                              params.add(reqVal1);
                              params.add(reqVal2);
                              cnt = db.execCallDir(" SrchDtl ", addSrchDtl, params);
                              dtlAddedOnce = true;
                            }
                            params = new ArrayList();
                            params.add(String.valueOf(lSrchId));
                            params.add(lprp);
                            params.add(reqVal1);
                            params.add(reqVal2);
                            cnt = db.execCallDir(" SrchDtl ", addSrchDtlSub, params);
                        }
                    }
                }    
              } else {
                //All Other Multiple Properties
                if(flg.equals("M")){
                for(int j=0; j < lprpS.size(); j++) {
                  String lSrt = (String)lprpS.get(j);
                  String lVal = (String)lprpV.get(j);
                  String lFld =  lprp + "_" + lVal; 
                  String reqVal = util.nvl((String)form.getValue(lFld));
                  //util.SOP(lprp + " : " + lFld + " : " + reqVal);  
                    if(reqVal.length() > 0 && !reqVal.equals("0")) {  
                      if(!dtlAddedOnce) {
                        params = new ArrayList();
                        params.add(String.valueOf(lSrchId));
                        params.add(lprp);
                        params.add(reqVal);
                        params.add(reqVal);
                        cnt = db.execCallDir(" SrchDtl ", addSrchDtlVal, params);
                        dtlAddedOnce = true;
                      }
                      params = new ArrayList();
                      params.add(String.valueOf(lSrchId));
                      params.add(lprp);
                      params.add(reqVal);
                      params.add(reqVal);
                      cnt = db.execCallDir(" SrchDtl ", addSrchDtlSubVal, params);
                  }
                }
                
                    
                }else if(flg.equals("KT")){
                String ktIncludeExclude=util.nvl((String)req.getParameter("INC_"+lprp),"I");
                
                if(ktIncludeExclude.equals("I"))
                    ktIncludeExclude="Y";
                else
                    ktIncludeExclude="N";
                HashMap ktsViewPrpDtls = (info.getKtsViewPrpDtls() == null)?new HashMap():(HashMap)info.getKtsViewPrpDtls();
                lprpS = (ArrayList)ktsViewPrpDtls.get("KTSCUSTOMS");
                lprpV = (ArrayList)ktsViewPrpDtls.get("KTSCUSTOMV");
                for(int j=0; j < lprpS.size(); j++) {
                  String lSrt = (String)lprpS.get(j);
                  String lVal = (String)lprpV.get(j);
                  String lFld =  lprp + "_" + lVal; 
                  String reqVal = util.nvl((String)form.getValue(lFld));
                  //util.SOP(lprp + " : " + lFld + " : " + reqVal);  
                    if(reqVal.length() > 0 && !reqVal.equals("0")) {  

                     params = new ArrayList();
                     params.add(String.valueOf(lSrchId));
                     params.add(lVal);
                     params.add(ktIncludeExclude);
                     params.add(ktIncludeExclude);
                     cnt = db.execCallDir(" SrchDtl ", addSrchDtlVal, params);

                      params = new ArrayList();
                      params.add(String.valueOf(lSrchId));
                      params.add(lVal);
                      params.add(ktIncludeExclude);
                      params.add(ktIncludeExclude);
                      cnt = db.execCallDir(" SrchDtl ", addSrchDtlSubVal, params);
                      if(ktIncludeExclude.equals("N")){
                      params = new ArrayList();
                      params.add(String.valueOf(lSrchId));
                      params.add(lVal);
                      params.add("NA");
                      params.add("NA");
                      cnt = db.execCallDir(" SrchDtl ", addSrchDtlSubVal, params);    
                      }
                  }
                }
                }else if(flg.equals("CTA")){
                    String reqVal1 = util.nvl((String)form.getValue(lprp + "_1"),"");
                    if(!reqVal1.equals("")){
                        ArrayList fmtVal = util.getStrToArr(reqVal1);
                        for(int j=0; j < lprpS.size(); j++) {
                          String lSrt = (String)lprpS.get(j);
                          String lVal = (String)lprpV.get(j);
                          String lprt = (String)lprpP.get(j);
                            
                          if(fmtVal.contains(lVal) || fmtVal.contains(lprt)){
                          String reqVal = lVal;
                            if(reqVal.length() > 0 && !reqVal.equals("0")) {  
                              if(!dtlAddedOnce) {
                                params = new ArrayList();
                                params.add(String.valueOf(lSrchId));
                                params.add(lprp);
                                params.add(reqVal);
                                params.add(reqVal);
                                cnt = db.execCallDir(" SrchDtl ", addSrchDtlVal, params);
                                dtlAddedOnce = true;
                              }
                              params = new ArrayList();
                              params.add(String.valueOf(lSrchId));
                              params.add(lprp);
                              params.add(reqVal);
                              params.add(reqVal);
                              cnt = db.execCallDir(" SrchDtl ", addSrchDtlSubVal, params);
                          }
                          }
                        }
                    }
                }
              }
          } else if(flg.equals("SM")){
                    
                    
                      String lFld =  lprp;
                      String reqVal = util.nvl((String)form.getValue(lFld));
                        if(!reqVal.equals("")){
                      //util.SOP(lprp + " : " + lFld + " : " + reqVal);  
                            String[] srtLst = reqVal.split(",");
                        if(srtLst.length>0) {  
                            for(int s=0;s<srtLst.length;s++){
                                reqVal= srtLst[s];
                          if(!dtlAddedOnce) {
                            params = new ArrayList();
                            params.add(String.valueOf(lSrchId));
                            params.add(lprp);
                            params.add(reqVal);
                            params.add(reqVal);
                            cnt = db.execCallDir(" SrchDtl ", addSrchDtlVal, params);
                            dtlAddedOnce = true;
                          }
                          params = new ArrayList();
                          params.add(String.valueOf(lSrchId));
                          params.add(lprp);
                          params.add(reqVal);
                          params.add(reqVal);
                          cnt = db.execCallDir(" SrchDtl ", addSrchDtlSubVal, params);
                            }
                      }
                    }
          
          } else {
              
              
            String reqVal1 = util.nvl((String)form.getValue(lprp + "_1"),"");
            String reqVal2 = util.nvl((String)form.getValue(lprp + "_2"),"");
              if(lprpTyp.equals("T"))
                  reqVal2 = reqVal1;
            //util.SOP(lprp + " : "+ reqVal1 + " : " + reqVal2);  
            
            if(((reqVal1.length() == 0) || (reqVal2.length() == 0))
              || ((reqVal1.equals("0")) && (reqVal2.equals("0")) )) {
                //ignore no selection;
            } else {
                if(lprpTyp.equals("T")){
                   ArrayList fmtVal = util.getStrToArr(reqVal1);
                   if(fmtVal!=null && fmtVal.size()>0){
                   for(int k=0;k<fmtVal.size();k++){
                       String txtVal = (String)fmtVal.get(k);
                       if(!dtlAddedOnce) {
                       params = new ArrayList();
                       params.add(String.valueOf(lSrchId));
                       params.add(lprp);
                       params.add(txtVal);
                       params.add(txtVal);
                       cnt = db.execCallDir(" SrchDtl ", addSrchDtlVal, params);
                       dtlAddedOnce = true;
                       }
                       params = new ArrayList();
                       params.add(String.valueOf(lSrchId));
                       params.add(lprp);
                       params.add(txtVal);
                       params.add(txtVal);
                       cnt = db.execCallDir(" SrchDtl ", addSrchDtlSubVal, params);
                   }
                   }
                   }else{
              params = new ArrayList();
              params.add(String.valueOf(lSrchId));
              params.add(lprp);
              params.add(reqVal1);
              params.add(reqVal2);
              if(lprpTyp.equals("T")){
                  if(includeexcludeList.contains(lprp)){
                     params.add(util.nvl((String)req.getParameter("INC_"+lprp),"I"));
                     cnt = db.execCall(" SrchDtl ", addSrchDtlValinclude, params);
                  }else
                   cnt = db.execCall(" SrchDtl ", addSrchDtlVal, params);
              }else if(lprpTyp.equals("D")){
                 if(includeexcludeList.contains(lprp)){
                     params.add(util.nvl((String)req.getParameter("INC_"+lprp),"I"));
                     cnt = db.execCall(" SrchDtl ", addSrchDtlDteinclude, params);
                 }else
                     cnt = db.execCall(" SrchDtl ", addSrchDtlDte, params);
              }else
                cnt = db.execCall(" SrchDtl ", addSrchDtl, params);  
              
            }}
          }
        }    
      }    
    }
    
   
    ArrayList multiSrchLst = info.getMultiSrchLst();
    if(multiSrchLst == null)
      multiSrchLst = new ArrayList();
    multiSrchLst.add(String.valueOf(lSrchId));
    
    HashMap multiSrchMap = info.getMultiSrchMap();
    
    multiSrchMap.put(String.valueOf(lSrchId), util.nvl(util.srchDscription(String.valueOf(lSrchId))));

   
    if(isEx.equals("is2Ex")){
      int isEx_srchId = add2ExSrch(req,lSrchId);
      multiSrchLst.add(String.valueOf(isEx_srchId));
      multiSrchMap.put(String.valueOf(isEx_srchId), util.nvl(util.srchDscription(String.valueOf(isEx_srchId))));
      info.setIsEx(isEx);
    }else if(isEx.equals("is3Ex")){
        int isEx_srchId = add3ExSrch(req,lSrchId);
//        multiSrchLst.add(String.valueOf(isEx_srchId));
        multiSrchMap.put(String.valueOf(isEx_srchId), util.nvl(util.srchDscription(String.valueOf(isEx_srchId))));
        info.setIsEx(isEx);
    }else if(isEx.equals("is3VG")){
        int isEx_srchId = add3VGSrch(req,lSrchId);
//        multiSrchLst.add(String.valueOf(isEx_srchId));
        multiSrchMap.put(String.valueOf(isEx_srchId), util.nvl(util.srchDscription(String.valueOf(isEx_srchId))));
        info.setIsEx(isEx);
    }else if(isEx.equals("is3VG_UP")){
          int isEx_srchId = add3VGSrchUP(req,lSrchId);
          multiSrchLst.add(String.valueOf(isEx_srchId));
          multiSrchMap.put(String.valueOf(isEx_srchId), util.nvl(util.srchDscription(String.valueOf(isEx_srchId))));
          info.setIsEx(isEx);
      }else
     info.setIsEx("");
    
      if(!soldDay.equals("0")){
          String to_dte = util.getToDte();
          String frmDte = util.getBackDate("dd-MM-yyyy",Integer.parseInt(soldDay));
          int soldSrchId=0;
          String getfinalSrchid = "srch_pkg.COPY_SRCH(pFrmId => ?, pTyp => ?, pSrchid => ?)";
          params = new ArrayList();
          params.add(String.valueOf(lSrchId));
          params.add("SOLD");  
          ArrayList out = new ArrayList();
          out.add("I");
          CallableStatement  cst = db.execCall(" Add Srch Hdr ", getfinalSrchid, params, out);
          try {
              soldSrchId = cst.getInt(params.size()+1);
                multiSrchLst.add(String.valueOf(soldSrchId));
                cst.close();
            } catch (SQLException e) {
            }
          
          String sttQry = "select stt from df_stk_stt where grp1=? and stt not like 'SUS%'  and flg='A' order by srt";
           ArrayList ary = new ArrayList();
           ary.add("SOLD");
          try {
              ArrayList outLst = db.execSqlLst("sttQry", sttQry, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
              while (rs.next()) {
                  String stt = rs.getString("stt");
                  String insrtAddon = " insert into srch_addon( srch_id , cprp , cval) "+
                  "select ? , ? , ?  from dual ";
                  ary = new ArrayList();
                  ary.add(String.valueOf(soldSrchId));
                  ary.add("STT");
                  ary.add(stt);
                   cnt = db.execUpd("", insrtAddon, ary);
              }
              rs.close();
              pst.close();
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
          params = new ArrayList();
          params.add(String.valueOf(soldSrchId));
          params.add("SAL_DTE");
          params.add(frmDte);
          params.add(to_dte);
          String addSrchDtlDte = "srch_pkg.add_srch_dtl_dte(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
           cnt = db.execCall(" SrchDtl ", addSrchDtlDte, params);
         
      }
    info.setMultiSrchLst(multiSrchLst);
    info.setMultiSrchMap(multiSrchMap);
    return lSrchId ;
    //srchForm.setValue("oldsrchId", srchIDN);
  }
  
  
    public int saveSearchpur(HttpServletRequest req, PurchaseDmdForm form, String buttonPressed) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap prp;
        HashMap mprp;
      ArrayList srchPrpLst = new ArrayList();
      ArrayList basicsrchPrp = (session.getAttribute("purDmdSrch") == null)?new ArrayList():(ArrayList)session.getAttribute("purDmdSrch");
      String dtls="",localdtls="";
      srchPrpLst.addAll(basicsrchPrp);
      String isEx = util.nvl((String)form.getValue("is2Ex"));
      if(isEx.equals(""))
          isEx = util.nvl((String)form.getValue("is3Ex"));
      if(isEx.equals(""))
          isEx = util.nvl((String)form.getValue("is3VG"));
      String oldSrchId = util.nvl((String)form.getValue("oldsrchid"));
      ArrayList params = null;
      int cnt =0;
        ArrayList isExParams = new ArrayList();
          HashMap dbinfo = info.getDmbsInfoLst();
          String cutlprp = (String)dbinfo.get("CUT");
          String pollprp = (String)dbinfo.get("POL");
          String symlprp = (String)dbinfo.get("SYM");
        if(isEx.length() > 0) {
          isExParams.add(cutlprp);
          isExParams.add(pollprp);
          isExParams.add(symlprp);
      }
      int lSrchId = 0 ;
        String srchTyp = util.nvl(info.getIsFancy());
        String mdl="WEB";
        if(srchTyp.equals("FCY"))
        mdl="FNCY";
        String pair=util.nvl((String)form.getValue("pairs"));
        if(!pair.equals(""))
            pair="MATCHPAIR";
        
      if(oldSrchId.length() > 0) {
        lSrchId = Integer.parseInt(oldSrchId);
      } else {
        String getSrchId = "srch_pkg.get_srch_id(pRlnId => ?, pTyp => ?,pSrchMdl=>? , pTrmVal => ?, pMdl => ?, pFlg => ?, pXrt => ? , pSrchId => ?)";
        params = new ArrayList();
        params.add(String.valueOf(info.getRlnId()));
        params.add(util.nvl((String)form.getValue("typ"), "WEB"));  
        params.add("MEMO_SRCH");
        params.add(Double.toString(info.getCmpTrm()));
        params.add(mdl);
        if(pair.equals(""))  
        params.add(isEx);  
        else 
        params.add(pair); 
        params.add(form.getValue("xrt")); 
        ArrayList outParams = new ArrayList();
        outParams.add("I");
          
         CallableStatement cst = db.execCall(" Add Srch Hdr ", getSrchId, params, outParams);
          try {
            lSrchId = cst.getInt(params.size()+1);
            cst.close();
            cst=null;
          } catch (SQLException e) {
          }
        }
      
      if(lSrchId > 0) {
        prp = info.getPrp();
        mprp = info.getMprp();
        String addSrchDtl = "srch_pkg.add_srch_dtl(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
        String addSrchDtlVal = "srch_pkg.add_srch_dtl_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
        String addSrchDtlSub = "srch_pkg.add_srch_dtl_sub(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
        String addSrchDtlSubVal = "srch_pkg.add_srch_dtl_sub_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
        String addSrchDtlDte = "srch_pkg.add_srch_dtl_dte(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
        for (int i = 0; i < srchPrpLst.size(); i++) {
          boolean dtlAddedOnce = false ;
          ArrayList srchPrp = (ArrayList)srchPrpLst.get(i);
          String lprp = (String)srchPrp.get(0);
          String flg= (String)srchPrp.get(1);
          String prpSrt = lprp ;  
          String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
          String prpDsc = (String)mprp.get(lprp+"D");
          localdtls="";
          if(isExParams.contains(lprp)) {
            // 2EX clause tp be ignored          
          } else {
            if(lprp.equals("CRTWT"))
                prpSrt = "SIZE";
            ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
              if(lprpS==null){
                  prpSrt = "SZ";
                  lprpS = (ArrayList)prp.get(prpSrt + "S");
              }
            ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
              
            
            if((buttonPressed.equalsIgnoreCase("refine")) && (lprpS!=null))
                flg = "M";
            
            if(flg.equals("M")) {
                if(lprp.equalsIgnoreCase("CRTWT")) {
                  String wtFr = util.nvl((String)form.getValue("wt_fr_c"));
                  String wtTo = util.nvl((String)form.getValue("wt_to_c"));
                  if((wtFr.length() > 0) && (wtTo.length() > 0)) {
                    params = new ArrayList();
                    params.add(String.valueOf(lSrchId));
                    params.add(lprp);
                    params.add(wtFr);
                    params.add(wtTo);
                    
                    cnt = db.execCall(" SrchDtl ", addSrchDtl, params);
                    dtls = dtls+"<tr><td>"+ prpDsc + "</td><td> "+wtFr+" To "+wtTo+"</td></tr>"; 
                  } else {
                      for(int j=0; j < lprpS.size(); j++) {
                        String lSrt = (String)lprpS.get(j);
                        String reqVal1 = util.nvl((String)form.getValue(lprp + "_1_" + lSrt),"");
                        String reqVal2 = util.nvl((String)form.getValue(lprp + "_2_" + lSrt),"");
                          if((reqVal1.length() == 0) || (reqVal2.length() == 0)) {
                            //ignore no value selected;
                          } else {
                              if(!dtlAddedOnce) {
                                params = new ArrayList();
                                params.add(String.valueOf(lSrchId));
                                params.add(lprp);
                                params.add(reqVal1);
                                params.add(reqVal2);
                                cnt = db.execCall(" SrchDtl ", addSrchDtl, params);
                                dtlAddedOnce = true;
                              }
                              params = new ArrayList();
                              params.add(String.valueOf(lSrchId));
                              params.add(lprp);
                              params.add(reqVal1);
                              params.add(reqVal2);
                              cnt = db.execCall(" SrchDtl ", addSrchDtlSub, params);
                              localdtls+=","+reqVal1+" To "+reqVal2;
                          }
                      }
                      if(!localdtls.equals("")){
                          localdtls=localdtls.replaceFirst("\\,", "");
                          dtls = dtls+"<tr><td>"+ prpDsc + "</td><td> "+localdtls+"</td></tr>"; 
                      }
                  }    
                } else {
                  //All Other Multiple Properties
                    
                  for(int j=0; j < lprpS.size(); j++) {
                    String lSrt = (String)lprpS.get(j);
                    String lVal = (String)lprpV.get(j);
                    String lFld =  lprp + "_" + lVal; 
                    String reqVal = util.nvl((String)form.getValue(lFld));
                    //util.SOP(lprp + " : " + lFld + " : " + reqVal);  
                      if(reqVal.length() > 0 && !reqVal.equals("0")) {  
                        if(!dtlAddedOnce) {
                          params = new ArrayList();
                          params.add(String.valueOf(lSrchId));
                          params.add(lprp);
                          params.add(reqVal);
                          params.add(reqVal);
                          cnt = db.execCall(" SrchDtl ", addSrchDtlVal, params);
                          dtlAddedOnce = true;
                        }
                        params = new ArrayList();
                        params.add(String.valueOf(lSrchId));
                        params.add(lprp);
                        params.add(reqVal);
                        params.add(reqVal);
                        cnt = db.execCall(" SrchDtl ", addSrchDtlSubVal, params);
                        localdtls+=","+reqVal;
                    }
                  }
                    if(!localdtls.equals("")){
                        localdtls=localdtls.replaceFirst("\\,", "");
                        dtls = dtls+"<tr><td>"+ prpDsc + "</td><td> "+localdtls+"</td></tr>"; 
                    }
                }     
            } else {
                
                
              String reqVal1 = util.nvl((String)form.getValue(lprp + "_1"),"");
              String reqVal2 = util.nvl((String)form.getValue(lprp + "_2"),"");
                if(lprpTyp.equals("T"))
                    reqVal2 = reqVal1;
              //util.SOP(lprp + " : "+ reqVal1 + " : " + reqVal2);  
              
              if(((reqVal1.length() == 0) || (reqVal2.length() == 0))
                || ((reqVal1.equals("0")) && (reqVal2.equals("0")) )) {
                  //ignore no selection;
              } else {
                  if(lprpTyp.equals("T")){
                     ArrayList fmtVal = util.getStrToArr(reqVal1);
                     if(fmtVal!=null && fmtVal.size()>0){
                     for(int k=0;k<fmtVal.size();k++){
                         String txtVal = (String)fmtVal.get(k);
                         if(!dtlAddedOnce) {
                         params = new ArrayList();
                         params.add(String.valueOf(lSrchId));
                         params.add(lprp);
                         params.add(txtVal);
                         params.add(txtVal);
                         cnt = db.execCallDir(" SrchDtl ", addSrchDtlVal, params);
                         dtlAddedOnce = true;
                         }
                         params = new ArrayList();
                         params.add(String.valueOf(lSrchId));
                         params.add(lprp);
                         params.add(txtVal);
                         params.add(txtVal);
                         cnt = db.execCallDir(" SrchDtl ", addSrchDtlSubVal, params);
                         localdtls+=","+txtVal;
                     }
                     if(!localdtls.equals("")){
                         localdtls=localdtls.replaceFirst("\\,", "");
                         dtls = dtls+"<tr><td>"+ prpDsc + "</td><td> "+localdtls+"</td></tr>"; 
                     }
                     }
                     }else{
                params = new ArrayList();
                params.add(String.valueOf(lSrchId));
                params.add(lprp);
                params.add(reqVal1);
                params.add(reqVal2);
              if(lprpTyp.equals("T"))
                cnt = db.execCall(" SrchDtl ", addSrchDtlVal, params);
              else if(lprpTyp.equals("D"))
                cnt = db.execCall(" SrchDtl ", addSrchDtlDte, params);
              else
                  cnt = db.execCall(" SrchDtl ", addSrchDtl, params);
                  if(lprpTyp.equals("C"))
                  dtls = dtls+"<tr><td>"+ prpDsc + "</td><td> "+(String)lprpV.get(lprpS.indexOf(reqVal1))+" To "+(String)lprpV.get(lprpS.indexOf(reqVal2))+"</td></tr>"; 
                  else
                  dtls = dtls+"<tr><td>"+ prpDsc + "</td><td> "+reqVal1+" To "+reqVal2+"</td></tr>"; 
              }
             }
            }
          }    
        }    
      }
      ArrayList multiSrchLst = info.getMultiSrchLst();
      if(multiSrchLst == null)
        multiSrchLst = new ArrayList();
      multiSrchLst.add(String.valueOf(lSrchId));
      
      HashMap multiSrchMap = info.getMultiSrchMap();
      
      multiSrchMap.put(String.valueOf(lSrchId), util.nvl(util.srchDscription(String.valueOf(lSrchId))));

     
      if(isEx.equals("is2Ex")){
        int isEx_srchId = add2ExSrch(req,lSrchId);
        multiSrchLst.add(String.valueOf(isEx_srchId));
        multiSrchMap.put(String.valueOf(isEx_srchId), util.nvl(util.srchDscription(String.valueOf(isEx_srchId))));
        info.setIsEx(isEx);
      }else if(isEx.equals("is3Ex")){
          int isEx_srchId = add3ExSrch(req,lSrchId);
          multiSrchLst.add(String.valueOf(isEx_srchId));
          multiSrchMap.put(String.valueOf(isEx_srchId), util.nvl(util.srchDscription(String.valueOf(isEx_srchId))));
          info.setIsEx(isEx);
      }else if(isEx.equals("is3VG")){
          int isEx_srchId = add3VGSrch(req,lSrchId);
          multiSrchLst.add(String.valueOf(isEx_srchId));
          multiSrchMap.put(String.valueOf(isEx_srchId), util.nvl(util.srchDscription(String.valueOf(isEx_srchId))));
          info.setIsEx(isEx);
      }else
       info.setIsEx("");
      info.setMultiSrchLst(multiSrchLst);
      info.setMultiSrchMap(multiSrchMap);
      req.setAttribute("dtls", dtls);
      return lSrchId ;
      //srchForm.setValue("oldsrchId", srchIDN);
    }
//  public String getSrchDscr(int lSrchId) {
//    String dsc = "";
//    ArrayList ary = new ArrayList();
//    ary.add(String.valueOf(lSrchId));
//    
//    String getDscr = "select web_pkg.get_srch_dscr(?) dscr from dual";
//    ResultSet rs = db.execSql(" Srch Dscr ", getDscr, ary);
//    try {
//        if(rs.next()) {
//          dsc = rs.getString(1);
//    }
//        rs.close();
//    } catch (SQLException e) {
//    }
//    return dsc ;
//  }
  
  public int addDmd(HttpServletRequest req,HttpServletResponse res, int srchId , String dsc){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      int dmdId=0;
      String getSrchId = "dmd_pkg.Add_Dmd_Frm_Srch(pSrchId => ?, pDsc => ?,  pDmdId =>?) ";
      ArrayList params = new ArrayList();
      params.add(String.valueOf(srchId));
      params.add(dsc);
      ArrayList outParams = new ArrayList();
      outParams.add("I");

      CallableStatement cst = db.execCall(" Add Srch Hdr ", getSrchId, params, outParams);
       try {
         dmdId = cst.getInt(params.size()+1);
         cst.close();
         cst=null;
       } catch (SQLException e) {
       }
      return dmdId; 
  }
    public int addDmdPur(HttpServletRequest req,HttpServletResponse res,String nmeIdn ,int srchId , String dsc){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        int dmdId=0;
        String getSrchId = "dmd_pkg.Add_Dmd_Pur_Frm_Srch(pSrchId => ?,pNmeIdn => ?, pDsc => ?,  pDmdId =>?) ";
        ArrayList params = new ArrayList();
        params.add(String.valueOf(srchId));
        params.add(nmeIdn);
        params.add(dsc);
        ArrayList outParams = new ArrayList();
        outParams.add("I");

        CallableStatement cst = db.execCall(" Add Srch Hdr ", getSrchId, params, outParams);
         try {
           dmdId = cst.getInt(params.size()+1);
           cst.close();
           cst=null;
         } catch (SQLException e) {
         }
        return dmdId; 
    }
    public int loadDmd(HttpServletRequest req, HttpServletResponse res, int dmdId ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        int ct = 0;
        int srchId = 0;
      try {
        ResultSet rs = db.execSql("srch Id", "select srch_id_seq.nextval from dual", new ArrayList());
        if(rs.next())
            srchId = rs.getInt(1);
             rs.close();
        String getSrchId = "dmd_pkg.Add_Srch_Frm_Dmd(pDmdId => ? , pSrchId => ? , pMdl => 'WEB') ";
        ArrayList params = new ArrayList();
        params.add(String.valueOf(dmdId));
        params.add(String.valueOf(srchId));
         ct = db.execCall(" Add Srch Hdr ", getSrchId, params);
       
        
         } catch (SQLException e) {
         }
        return srchId; 
    }
    
    public int loadDmdPur(HttpServletRequest req, HttpServletResponse res, int dmdId ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        int ct = 0;
        int srchId = 0;
      try {
        ResultSet rs = db.execSql("srch Id", "select srch_id_seq.nextval from dual", new ArrayList());
        if(rs.next())
            srchId = rs.getInt(1);
             rs.close();
        String getSrchId = "dmd_pkg.Add_Srch_Pur_Frm_Dmd(pDmdId => ? , pSrchId => ?) ";
        ArrayList params = new ArrayList();
        params.add(String.valueOf(dmdId));
        params.add(String.valueOf(srchId));
         ct = db.execCall(" Add Srch Hdr ", getSrchId, params);
       
        
         } catch (SQLException e) {
         }
        return srchId; 
    }
    public ArrayList MemoSttAlw(HttpServletRequest req, HttpServletResponse res,String memoTyp){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    ArrayList sttList = (session.getAttribute("memoSttAlw") == null)?new ArrayList():(ArrayList)session.getAttribute("memoSttAlw");
    try {
    if(sttList.size() == 0) {
    String sttFtch =
        " select  b.chr_fr from mrule a , rule_dtl b where a.rule_idn = b.rule_idn " +
        " and a.mdl='MEMO_ALW'  and b.dsc =? ";
    ArrayList ary = new ArrayList();
    ary.add(memoTyp);
      ArrayList  outLst = db.execSqlLst("stt Ftch", sttFtch, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
        sttList.add(rs.getString("chr_fr"));
    }
    rs.close();
    pst.close();
        session.setAttribute("memoSttAlw", sttList);
    }
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    
    return sttList;
    }
  public int add2ExSrch(HttpServletRequest req,int srchId) {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    int lSrchId = 0 ;
    
    String getSrchId = "srch_pkg.srch_2ex(pFrmId => ?, pSrchId => ?)";
    ArrayList params = new ArrayList();
    params.add(String.valueOf(srchId));
    
    ArrayList outParams = new ArrayList();
    outParams.add("I");

    CallableStatement cst = db.execCall(" Add Srch Hdr ", getSrchId, params, outParams);
     try {
       lSrchId = cst.getInt(params.size()+1);
       cst.close();
       cst=null;
     } catch (SQLException e) {
     }
    return lSrchId; 
  }
  
    public int add3ExSrch(HttpServletRequest req,int srchId) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      int lSrchId = 0 ;
      
      String getSrchId = "srch_pkg.srch_3ex(pSrchId => ?)";
      ArrayList params = new ArrayList();
      params.add(String.valueOf(srchId));
      
    

     int cst = db.execCall(" Add Srch Hdr ", getSrchId, params);
       
      return srchId; 
    }
    
    public int add3VGSrch(HttpServletRequest req,int srchId) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      int lSrchId = 0 ;
      
      String getSrchId = "srch_pkg.srch_3VG(pSrchId => ?)";
      ArrayList params = new ArrayList();
      params.add(String.valueOf(srchId));
      
    

     int cst = db.execCall(" Add Srch Hdr ", getSrchId, params);
       
      return srchId; 
    }
    
    public int add3VGSrchUP(HttpServletRequest req,int srchId) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      int lSrchId = 0 ;
      
      String getSrchId = "srch_pkg.SRCH_3VG_UP(pSrchId => ?)";
      ArrayList params = new ArrayList();
      params.add(String.valueOf(srchId));
      
    

     int cst = db.execCall(" Add Srch Hdr ", getSrchId, params);
       
      return srchId; 
    }
  public void delSearch(HttpServletRequest req,String srchId) {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    String delQ = "srch_pkg.del_srch(?) ";
    ArrayList ary = new ArrayList();
    ary.add(srchId);
    
    int indexSrch = info.getMultiSrchLst().indexOf(srchId);
     info.getMultiSrchLst().remove(indexSrch);
    db.execCall(" Del / Upd Srch ", delQ, ary);
  }
 
  public ArrayList getGrpList(HttpServletRequest req , HttpServletResponse res, int rlnId){
       HttpSession session = req.getSession(false);
       InfoMgr info = (InfoMgr)session.getAttribute("info");
       DBUtil util = new DBUtil();
       DBMgr db = new DBMgr(); 
       db.setCon(info.getCon());
       util.setDb(db);
       util.setInfo(info);
       db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
       util.setLogApplNm(info.getLoApplNm());
       ArrayList grpNmeList = new ArrayList();
     String grpNme = "select distinct a.grp_nme from web_bid_wl a, mstk b , web_usrs c " + 
     "        where a.mstk_idn = b.idn " + 
     "        and a.usr_idn = c.usr_id and c.rel_idn = ? " + 
     "        and trunc(nvl(a.to_dt, sysdate)) >= trunc(sysdate) " + 
     "        and a.typ = 'WL' and a.stt = 'A' " + 
     "        and b.stt in ('MKAV' ,'MKIS') " + 
     "        and b.pkt_ty = 'NR' and grp_nme is not null order by a.grp_nme";
     
       ArrayList ary = new ArrayList();
       ary.add(String.valueOf(rlnId));
       try {
           ArrayList  outLst = db.execSqlLst("grpNme", grpNme, ary);
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet rs = (ResultSet)outLst.get(1);
             while (rs.next()) {
                 grpNmeList.add(rs.getString(1).toUpperCase());
             }
             rs.close();
           pst.close();
         } catch (SQLException sqle) {
             // TODO: Add catch code
             sqle.printStackTrace();
             }finally{
                 System.out.println("exception");
             }
       
       
       return grpNmeList;
   }
  
    public void srchRef(String srchTyp , String srchVal , HashMap fileDataMap , SearchForm form,HttpServletRequest req, HttpServletResponse res ,String srchSold){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());   
        try{
        util.updAccessLog(req,res,"Search Page", "srchRef Start");
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    HashMap dbinfo = info.getDmbsInfoLst();
    String cnt = (String)dbinfo.get("CNT");
    int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
    String offerLmt = (String)dbinfo.get("OFFER_LMT_DFLT");
    String srchStr = "";
    String mdl=info.getVwMdl();    
        srchVal = util.getVnm(srchVal);
    String isMix = util.nvl(info.getIsMix(),"NR");
        String usrFlg=util.nvl((String)info.getUsrFlg());
        ArrayList rolenmLst=(ArrayList)info.getRoleLst();
    String srchsttLst="";
    ArrayList srchTypList = (session.getAttribute("srchTypList_"+isMix) == null)?new ArrayList():(ArrayList)session.getAttribute("srchTypList_"+isMix);
    ArrayList srchTypListDtl=new ArrayList();
    ArrayList params = new ArrayList();
    ArrayList srchsttLstArr = new ArrayList();
    int ct=0;
    for(int i=0;i<srchTypList.size();i++){
    srchTypListDtl=new ArrayList();
    srchTypListDtl=(ArrayList)srchTypList.get(i);
    String srchstt=util.nvl((String)form.getValue("SRCHSTT_"+util.nvl((String)srchTypListDtl.get(1))));
    if(!srchstt.equals("")){
    srchsttLst+=","+srchstt;  
    srchsttLstArr.add(srchstt);
    }
    }
    srchsttLst = srchsttLst.replaceFirst(",", "");
    if(srchsttLst.equals("")){
    srchsttLst="MKAV";
    srchsttLstArr.add(srchsttLst);
    }
    
    srchsttLst=util.getVnm(srchsttLst);
    String qtyQ=", decode(b.pkt_ty, 'NR', b.qty, b.qty - nvl(qty_iss,0)) qty, ";
    String[] vnmLst = srchVal.split(",");
    int loopCnt = 1 ;
    System.out.println(vnmLst.length);
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



    toLoc = Math.min(srchVal.lastIndexOf(lookupVnm) + lookupVnm.length(), srchVal.length());

    String vnmSub = srchVal.substring(fromLoc, toLoc);
    vnmSub=vnmSub.toUpperCase();
    vnmSub= vnmSub.replaceAll (" ", "");
    if(srchTyp.equals("vnm")){
        if(vnmSub.equals("")){
            vnmSub="1";
        }
    srchStr = " ( vnm in ("+vnmSub+") or tfl3 in ("+vnmSub+") ) ";
    }else if(srchTyp.equals("cert_no"))
    srchStr = " cert_no in ("+vnmSub+")";
    else if(srchTyp.equals("show_id"))
    srchStr = " b.tfl1 in ("+vnmSub+")";
    String pktTy=" and b.pkt_ty = 'NR'";
    if(form.getValue("mixsrch")!=null || form.getValue("mixsrchpkt")!=null){
    pktTy = " and b.pkt_ty in ('MX', 'MIX') ";
    mdl="MIX_VW";
    if(cnt.equals("hk")){
    qtyQ=", DF_GET_PCS(b.idn, nvl(b.cts, 0) - nvl(b.cts_iss, 0)) qty, ";
    }
    }
    if(isMix.equals("SMX")){
        pktTy=" and b.pkt_ty = 'SMX'";  
        mdl="SMX_VW";
    }
        
        if(isMix.equals("RGH")){
            pktTy=" and b.pkt_ty = 'RGH'";  
            mdl="RGHMKT_VW";
        }
        
    srchStr = srchStr +pktTy;
        
    String sttQ=" and b.stt in("+srchsttLst+") ";
    if(srchSold.equals("YES")){
    mdl="SOLD_VW";
    sttQ="";
    }

    String srchRefQ =
    " Insert into gt_srch_rslt (rln_idn, srch_id, pkt_ty, stk_idn, vnm, rmk , qty, cts,sl_rte, pkt_dte, stt,prte, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis,diamondImg,jewImg,srayImg,agsImg,mrayImg,ringImg,lightImg,refImg,videos,certImg,video_3d ) " +
    " select ?, 1 srchId , pkt_ty, b.idn, b.vnm, b.tfl3 " +qtyQ+
    " decode(b.pkt_ty, 'NR', b.cts, b.cts - nvl(cts_iss, 0)) cts , b.cts ctsalt " +
    ", b.dte, stt,b.fcpr, nvl(upr, cmp) rte, rap_rte" +
    " , cert_lab, cert_no, 'Z' flg, sk1, nvl(upr, cmp), decode(rap_rte, 1, null, trunc(100 - (nvl(upr, cmp)/greatest(rap_rte,1)*100), 2)),diamondImg,jewImg,srayImg,agsImg,mrayImg,ringImg,lightImg,refImg,videos,certImg,video_3d " +
    " from mstk b "+
    " where 1=1 " +sttQ+
    " and "+srchStr ;

    if(srchTyp.equals("memoId")) {
    srchRefQ =
    " Insert into gt_srch_rslt (rln_idn, srch_id, pkt_ty, stk_idn, vnm, qty, cts,sl_rte, pkt_dte, stt,prte, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis,diamondImg,jewImg,srayImg,agsImg,mrayImg,ringImg,lightImg,refImg,videos,certImg,video_3d ) " +
    " select ?, 1 srchId , pkt_ty, b.idn, b.vnm "+qtyQ+
    " decode(b.pkt_ty, 'NR', b.cts, b.cts - nvl(cts_iss, 0)) cts ,b.cts ctsalt, " +
    " b.dte , stt ,b.fcpr, " +
    " nvl(upr, cmp) rte, rap_rte, cert_lab, cert_no, 'Z' flg, sk1, nvl(upr, cmp), decode(rap_rte, 1, null, trunc(100 - (nvl(upr, cmp)/rap_rte*100), 2)),diamondImg,jewImg,srayImg,agsImg,mrayImg,ringImg,lightImg,refImg,videos,certImg,video_3d " +
    " from mstk b " +
    " where 1=1 " +sttQ+
    " " +pktTy+
    " and exists (select 1 from jandtl a where a.mstk_idn = b.idn and a.idn in ("+vnmSub+ ") and nvl(a.flg,'NA') <> 'DEAL')";


    }
    
    params = new ArrayList();
    params.add(Integer.toString(info.getRlnId()));
    //params.add(Integer.toString(info.getRlnId()));
    //params.add(stoneId);
    //params.add(reportNo);
    if(cnt.equals("ag")){
    if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
    }else{
        srchRefQ += " and exists (select 1 from stk_dtl loc where b.idn= loc.mstk_idn  " +
        " and loc.grp = 1 and loc.mprp='LOC' and loc.val in (select nd.txt  from nme_dtl nd where mprp = 'LOC' and exists (select 1 from mnme n where n.grp_nme_idn = nd.nme_idn and n.nme_idn = ?)))";
        params.add(Integer.toString(info.getSrchPryID()));
    }
    }
    System.out.println(srchRefQ + " : " + params.toString());

    ct = db.execUpd(" Srch Ref ", srchRefQ, params);
    }
    
    if(mdl.equals("SOLD_VW")){
    int cnt1 = db.execCall(" sort : ", "DP_CUSTOM_SRT", new ArrayList());
    }
    
    String pri_chng_typ = util.nvl((String)form.getValue("PRI_CHNG_TYP"));
    String pri_chng_val = util.nvl((String)form.getValue("PRI_CHNG_VAL"));

    if(!pri_chng_val.equals("")){
        params = new ArrayList();
        params.add(pri_chng_typ);
        params.add(pri_chng_val);
        ct = db.execCall(" PKGMKT.Pri_Chng ", "PKGMKT.Pri_Chng(pTyp => ?, pVal => ?)", params);
        info.setPri_chng_typ(pri_chng_typ);
        info.setPri_chng_val(pri_chng_val);
    }
    String calQuot = "pkgmkt.Cal_Quot( pRlnId=> ?, pXrt=> ?)";
    params = new ArrayList();
    params.add(Integer.toString(info.getRlnId()));
    params.add(form.getValue("xrt"));
    ct = db.execCall(" Srch calQ ", calQuot, params);
    
    if(fileDataMap!=null && fileDataMap.size()>0)
    setOfferUpload(req,fileDataMap,offerLmt);

    String pktPrp = "pkgmkt.pktPrp(0,?)";
    params = new ArrayList();
    params.add(mdl);
    ct = db.execCall(" Srch Prp ", pktPrp, params);
    info.setSrchBaseOn(srchTyp);
    info.setSrchBaseOnLst(srchVal);
    info.setSrchsttLst(srchsttLstArr);
        try{
        util.updAccessLog(req,res,"Search Page", "srchRef End");
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }
    
    
    public void srchRefFindNotSeen(String srchTyp , String srchVal , SearchForm form,HttpServletRequest req, HttpServletResponse res ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());   
    HashMap dbinfo = info.getDmbsInfoLst();
    String cnt = (String)dbinfo.get("CNT");
    int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
    String srchStr = "";
    String mdl=info.getVwMdl();    
        srchVal = util.getVnm(srchVal);
    ArrayList params = new ArrayList();
    int ct=0;
    String[] vnmLst = srchVal.split(",");
    int loopCnt = 1 ;
    System.out.println(vnmLst.length);
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
    toLoc = Math.min(srchVal.lastIndexOf(lookupVnm) + lookupVnm.length(), srchVal.length());
    String vnmSub = srchVal.substring(fromLoc, toLoc);
    vnmSub=vnmSub.toUpperCase();
    vnmSub= vnmSub.replaceAll (" ", "");
    if(srchTyp.equals("vnm"))
    srchStr = " ( vnm in ("+vnmSub+")) ";
    else if(srchTyp.equals("cert_no"))
    srchStr = " cert_no in ("+vnmSub+")";       

    String srchRefQ =
    " delete gt_srch_rslt b where 1=1 "+
    " and "+srchStr ;

    if(srchTyp.equals("memoId")) {
        vnmSub= vnmSub.replaceAll ("'", "");
    srchRefQ =
    " delete gt_srch_rslt b where 1=1 "+
    " and exists (select 1 from jandtl a where a.mstk_idn = b.stk_idn and a.idn in ("+vnmSub+ "))";
    }
    
    params = new ArrayList();
    ct = db.execUpd(" Srch Ref ", srchRefQ, params);
    }
    }
    public void setOfferUpload(HttpServletRequest req,HashMap fileData,String offerLmt) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm()); 
        if(fileData.size() > 0) {
            String getGT = "select stk_idn idn, vnm, rap_rte,  quot, nvl(rap_dis, '') rap_dis from gt_srch_rslt" ;
            ArrayList rsLst1 = db.execSqlLst(" gt vnm", getGT, new ArrayList());
            PreparedStatement pst= (PreparedStatement)rsLst1.get(0);
            ResultSet rs = (ResultSet)rsLst1.get(1);
              try {
                  while(rs.next()) {
                      String idn = rs.getString("idn");
                      String vnm = rs.getString("vnm");
                      int rapRte = rs.getInt("rap_rte");
                      double quot = rs.getDouble("quot");
                      //double rapDis = rs.getDouble("rap_dis");
                      String rapDis = rs.getString("rap_dis");
                      
                      if(fileData.get(vnm) != null) {
                          HashMap reqData = (HashMap)fileData.get(vnm);
                          String dis = (String)reqData.get("dis");
                          String prc = (String)reqData.get("prc");
                          String lmt = util.nvl((String)reqData.get("lmt"));
                          double fQuot = quot ;
                          String fDis = rapDis ;
                          
                          if(prc.length() > 0) {
                              fQuot = Double.parseDouble(prc) ;    
                              if(rapRte > 1)
                              fDis = String.valueOf(((fQuot/rapRte*100)- 100));
                          }
                          if(dis.length() > 0) {
                              fQuot = get2Decimal(rapRte * (100 + Double.parseDouble(dis))/100);
                              fDis = String.valueOf(dis);
                          }
                          String color="";
                          double percent = ((quot * Integer.parseInt(offerLmt))/100);
                          double diff = quot - fQuot;
                          double minPrc = fQuot - percent;
                          if(diff > percent){
                              color="red";   
                          }
                          String upGTq = "update gt_srch_rslt set ofr_rte = ?, ofr_dis = ?,cmnt2=?,lmt=? where stk_idn = ? ";
                          ArrayList ary = new ArrayList();
                          ary.add(String.valueOf(fQuot));
                          ary.add(fDis);
                          ary.add(color);
                          ary.add(lmt);
                          ary.add(idn);
                          int ct=db.execDirUpd(" upd file dis", upGTq, ary);
                          System.out.println(ct);
                      }
                      
                  }
                  rs.close();
                  pst.close();
              } catch (SQLException e) {
              }
          }
    }
    
//    public void setFinalQuot(HashMap fileData) {
//        if(fileData.size() > 0) {
//            String getGT = "select stk_idn, vnm, rap_rte,  quot, nvl(rap_dis, '') rap_dis from gt_srch_rslt" ;
//            ResultSet rs = db.execSql(" gt vnm", getGT, new ArrayList());
//              try {
//                  while(rs.next()) {
//                      String idn = rs.getString("idn");
//                      String vnm = rs.getString("vnm");
//                      int rapRte = rs.getInt("rap_rte");
//                      double quot = rs.getDouble("quot");
//                      //double rapDis = rs.getDouble("rap_dis");
//                      String rapDis = rs.getString("rap_dis");
//                      
//                      if(fileData.get(vnm) != null) {
//                          HashMap reqData = (HashMap)fileData.get(vnm);
//                          String dis = (String)reqData.get("dis");
//                          String prc = (String)reqData.get("prc");
//                          double fQuot = quot ;
//                          String fDis = rapDis ;
//                          if(prc.length() > 0) {
//                              fQuot = Double.parseDouble(prc) ;    
//                              if(rapRte > 1)
//                                  fDis = String.valueOf(((fQuot/rapRte*100)- 100));
//                          }
//                          if(dis.length() > 0) {
//                              fQuot = get2Decimal(rapRte * (100 + Double.parseDouble(dis))/100);
//                              fDis = String.valueOf(dis);
//                          }
//                          String upGTq = "update gt_srch_rslt set quot = ?, rap_dis = ? where stk_idn = ? ";
//                          ArrayList ary = new ArrayList();
//                          ary.add(String.valueOf(fQuot));
//                          ary.add(fDis);
//                          ary.add(idn);
//                          db.execUpd(" upd file dis", upGTq, ary);
//                      }
//                      
//                  }
//                  rs.close();
//              } catch (SQLException e) {
//              }
//          }
//    }
    
    public double get2Decimal(double val) {
      DecimalFormat df = new  DecimalFormat ("0.##");
      String d = df.format (val);
      System.out.println ("\tformatted: " + d);
      d = d.replaceAll (",", ".");
      Double dbl = new Double (d);
      return dbl.doubleValue ();
    }
    
    public Boolean searchStock(HttpServletRequest req, HttpServletResponse res, String flg , String buttonPressed, String soldDay ) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      Boolean search = Boolean.valueOf(false);  
      String mdl =info.getVwMdl();    
        if(!soldDay.equals("0"))
            mdl="SOLD_VW";
        String isMix = util.nvl(info.getIsMix(),"NR");
        String dbSrch = "DP_STK_STT_SRCH(pSrchId => ?, pMdl => ?, pPktTy => ?)";
        if(buttonPressed.equals("mixsrch")){
          mdl="MIX_VW";
        }
        if(buttonPressed.equals("rghSrch")){
            mdl="RGHMKT_VW";
            isMix="RGH";
          
        }
        if(isMix.equals("SMX"))
        mdl="SMX_VW";
      ArrayList ary ;
      int ct = 0 ;
        try {
            ArrayList multiSrchLst = info.getMultiSrchLst();
            int multiSrchLstsz=multiSrchLst.size();
             
              
            for (int i = 0; i < multiSrchLstsz; i++) {
                String srchId = (String)multiSrchLst.get(i);
                
                ary = new ArrayList();
                ary.add(srchId);
                ary.add(mdl);
             
                ary.add(isMix);
                int cnt = db.execCall(" Srch : " + srchId, dbSrch, ary);
            }
            search = Boolean.valueOf(true);
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
            search = Boolean.valueOf(false);

        }
        if(!soldDay.equals("0")){
        int cnt = db.execCall(" sort : ", "DP_CUSTOM_SRT", new ArrayList());
        }
      return search;
    }
    public HashMap SearchResult(HttpServletRequest req,HttpServletResponse res, String flg , ArrayList vwPrpLst) {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            try{
            util.updAccessLog(req,res,"Search Query", "SearchResult Start");
            } catch (Exception sqle) {
                sqle.printStackTrace();
            }
            String vnmLst = util.nvl((String)req.getAttribute("vnmLst"));
            int ct = db.execCall("ud_dsp_stt", "pkgmkt.upd_dsp_stt", new ArrayList());
            int vwPrpLstsz=vwPrpLst.size();
            HashMap pktListMap = new HashMap();
            HashMap mprp = null;
            ArrayList pktStkIdnList = new ArrayList();
            boolean isSrchRef = true;
            String srchRef = util.nvl((String)req.getAttribute("srchRef"));
            if(srchRef.equals(""))
                isSrchRef=false;
            String srchQ = "";
            HashMap pktPrpMap = new HashMap();
             String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
          String rapDisFmt = util.nvl((String)info.getDmbsInfoLst().get("RAPDISFMT"));
            if(rapDisFmt.equals(""))
                 rapDisFmt="990.00";
            String cpPrp = "prte";
            if(vwPrpLst.contains("CP"))
            cpPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("CP")+1);
            String netPurRtePrp = "prte";
            if(vwPrpLst.contains("NET_PUR_RTE"))
            netPurRtePrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("NET_PUR_RTE")+1);
            String prirapPrp = "prte";
            if(vwPrpLst.contains("PRI_RAP_RTE"))
            prirapPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("PRI_RAP_RTE")+1);
            String shfld = "prp_";
            mprp = info.getMprp();
            String srchQ1 = " select sk1, 0 dsp , a.stk_idn , cert_lab , dsp_stt , decode(stt " +
            ", 'MKAP',' color:Olive'" + 
            ", 'MKPP','color:Green'" + 
            ", 'MKWH','color:Red'" + 
            " , 'MKLB','color:Maroon'" + 
            " , 'MKAV', decode(instr(upper(dsp_stt), 'NEW'), 0, ' ', 'color:Blue')" + 
            " , 'MKIS', 'color:Red'" + 
            " , 'MKEI', 'color:Red'" + 
            " , 'MKKS_IS', 'color:Red'" + 
            " , 'MKOS_IS', 'color:Red'" + 
            " , 'LB_PRI', 'color:#C80BCF'" +
            " , 'LB_IS', 'color:#C80BCF'" +
            " , 'SOLD') class ,  ";
            String srchQ2 = " select sk1, 1 dsp , a.stk_idn , cert_lab , '' dsp_stt , '' class , ";
            String dspStk = "stkDspFF";
            String getQuot = "quot rte";
           srchQ += "  cts crtwt, " +
                    //decode(quot, 0, 0, decode(nvl(rap_dis,0), 0, 101, trunc(((quot*100)/greatest(rap_rte,100)) - 100,2))) rr_dis, "+
                    " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, to_char(rap_dis, '"+rapDisFmt+"')))  r_dis " +
                    " , stk_idn  , vnm, kts_vw kts, cert_lab cert, cert_no, rmk, img,img2, cmp , trunc(((cmp*100)/greatest(rap_rte,1)) - 100,2) cmp_dis ,  to_char(trunc(cts,2),'999999990.99') cts, qty , rap_rte , " +
                    getQuot +
                    ", cmnt,stt stt1,  nvl(fquot,0) fquot ,ofr_rte,ofr_dis,CMNT2 CMNT2,lmt lmtRte, nvl("+cpPrp+",prte) prte , nvl(nvl("+cpPrp+",prte),0)*nvl(cts,0) cptotal,nvl(nvl("+netPurRtePrp+",prte),0)*nvl(cts,0) netPurRteTotal ,trunc(((greatest(nvl("+netPurRtePrp+",prte),1)*100)/greatest(rap_rte,1)) - 100,2) netPurRteDis,trunc(((greatest(nvl("+cpPrp+",prte),1)*100)/greatest(rap_rte,1)) - 100,2) cpdis, nvl("+prirapPrp+",prte) priraprte ,trunc(((greatest(nvl("+prirapPrp+",prte),1)*100)/greatest(rap_rte,1)) - 100,2) prirapdis, flg , to_char(trunc(cts,2) * quot, '99999990.00') amt , " +
                    " decode(nvl(fquot,0),0, 'NA', decode(fquot, quot, 'NA', decode(greatest(fquot, quot),quot, 'UP','DN'))) cmp_flg,diamondImg,jewImg,srayImg,agsImg,mrayImg,ringImg,lightImg,refImg,videos,certImg,video_3d ";

         

            for (int i = 0; i < vwPrpLstsz; i++) {
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

         
            String rsltQ =
               srchQ1+" "+srchQ + " from gt_srch_rslt a where flg in ("+flg+") union " +srchQ2+ " "+srchQ +"   from gt_srch_multi a " ;
              String isFlgOD = util.nvl((String)req.getAttribute("flgORDER"));
              if(isFlgOD.equals("Y"))
                  rsltQ = rsltQ+  " order by flg , 1, 2,3,4";
            else
                rsltQ = rsltQ+  " order by 1, 2,3,4";
            
            ArrayList ary = new ArrayList();
            
          ArrayList rsLst1 = db.execSqlLst("search Result", rsltQ, ary);
          PreparedStatement pst = (PreparedStatement)rsLst1.get(0);
          ResultSet rs = (ResultSet)rsLst1.get(1);

            String  srchRefVal = util.nvl((String)req.getAttribute("srchRefVal"));
            String srchMsg = "";
            pktListMap = new HashMap();
            try {
                while (rs.next()) {
                    String vnm =util.nvl(rs.getString("vnm"));
                    String tfl3 =util.nvl(rs.getString("rmk"));
                    String cert_No = util.nvl(rs.getString("cert_no"));
                    String prte = util.nvl(rs.getString("prte"));
                    if(isSrchRef){
                        
                        if(srchRef.equals("vnm")){
                            if(srchRefVal.indexOf(vnm)!=-1 ){
                              srchRefVal = srchRefVal.replaceAll("'"+vnm+"'","");
                            }else  if(srchRefVal.indexOf(tfl3)!=-1 ){
                                srchRefVal = srchRefVal.replaceAll("'"+tfl3+"'","");
                            }
                         }
                        if(srchRef.equals("cert_no")){
                            if(srchRefVal.indexOf(cert_No)!=-1)
                                srchRefVal = srchRefVal.replaceAll("'"+cert_No+"'","");
                                
                     }
                    }
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
                    pktPrpMap = new HashMap();
                    String stkIdn = util.nvl(rs.getString("stk_idn"));
                    pktPrpMap.put("flg", util.nvl(rs.getString("flg")));
                    pktPrpMap.put("stt", util.nvl(rs.getString("dsp_stt")));
                    pktPrpMap.put("stk_idn",stkIdn);
                    pktPrpMap.put("vnm",vnm);
                    pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                    pktPrpMap.put("class",util.nvl(rs.getString("class")));
                    pktPrpMap.put("cts", util.nvl(rs.getString("cts")));
                    pktPrpMap.put("cert_no",cert_No);
                    pktPrpMap.put("stt1",util.nvl(rs.getString("stt1")));
                    pktPrpMap.put("quot",util.nvl(rs.getString("rte")));
                    pktPrpMap.put("fquot",util.nvl(rs.getString("fquot")));
                    pktPrpMap.put("cmp",util.nvl(rs.getString("cmp")));
                    pktPrpMap.put("cmp_dis",util.nvl(rs.getString("cmp_dis")));
                    pktPrpMap.put("rap_rte",util.nvl(rs.getString("rap_rte")));
                    pktPrpMap.put("r_dis",util.nvl(rs.getString("r_dis")));
                    pktPrpMap.put("cert_lab", util.nvl(rs.getString("cert_lab")));
                    pktPrpMap.put("amt", util.nvl(rs.getString("amt")));
//                    pktPrpMap.put("cpRte", util.nvl(rs.getString("prte")));
                    pktPrpMap.put("cpTotal", util.nvl(rs.getString("cptotal")));
                    pktPrpMap.put("cpDis", util.nvl(rs.getString("cpdis")));
                    pktPrpMap.put("netPurRteTotal", util.nvl(rs.getString("netPurRteTotal")));
                    pktPrpMap.put("netPurRteDis", util.nvl(rs.getString("netPurRteDis")));
                    pktPrpMap.put("ofr_rte", util.nvl(rs.getString("ofr_rte")));
                    pktPrpMap.put("ofr_dis", util.nvl(rs.getString("ofr_dis")));
                    pktPrpMap.put("offerCol", util.nvl(rs.getString("CMNT2")));
                    pktPrpMap.put("offerlmtRte", util.nvl(rs.getString("lmtRte")));
                    pktPrpMap.put("diamondImg",util.nvl(rs.getString("diamondImg"),"N"));
                    pktPrpMap.put("jewImg",util.nvl(rs.getString("jewImg"),"N"));
                    pktPrpMap.put("srayImg",util.nvl(rs.getString("srayImg"),"N"));
                    pktPrpMap.put("agsImg",util.nvl(rs.getString("agsImg"),"N"));
                    pktPrpMap.put("mrayImg",util.nvl(rs.getString("mrayImg"),"N"));
                    pktPrpMap.put("ringImg",util.nvl(rs.getString("ringImg"),"N"));
                    pktPrpMap.put("lightImg",util.nvl(rs.getString("lightImg"),"N"));
                    pktPrpMap.put("refImg",util.nvl(rs.getString("refImg"),"N"));
                    pktPrpMap.put("videos",util.nvl(rs.getString("videos"),"N"));
                    pktPrpMap.put("certImg",util.nvl(rs.getString("certImg"),"N"));
                    pktPrpMap.put("video_3d",util.nvl(rs.getString("video_3d"),"N"));
                    for (int j = 0; j < vwPrpLstsz; j++) {
                        String prp = (String)vwPrpLst.get(j);

                        String fld = "prp_";
                        if (j < 9)
                            fld = "prp_00" + (j + 1);
                        else
                            fld = "prp_0" + (j + 1);

                        String val = util.nvl(rs.getString(fld));
                        if (prp.toUpperCase().equals("CRTWT"))
                            val = util.nvl(rs.getString("cts"));
                        if (prp.toUpperCase().equals("CERT_NO"))
                            val = util.nvl(rs.getString("cert_no"));
                        if (prp.toUpperCase().equals("RAP_DIS"))
                            val = util.nvl(rs.getString("r_dis"));
                        if (prp.toUpperCase().equals("UPR_DIS"))
                            val = util.nvl(rs.getString("r_dis"));
                        if (prp.toUpperCase().equals("CMP_DIS")){
                            if(val.equals(""))
                            val = util.nvl(rs.getString("cmp_dis")); 
                        }
                        if (prp.toUpperCase().equals("RAP_RTE"))
                            val = util.nvl(rs.getString("rap_rte"));
                        if(prp.equalsIgnoreCase("MEM_COMMENT"))
                            val = util.nvl(rs.getString("img2"));
                        if(prp.equals("RTE"))
                            val = util.nvl(rs.getString("rte"));
                        if(prp.equals("CMP_RTE")){
                          if(val.equals(""))
                              val = util.nvl(rs.getString("cmp"));
                        }
                        if(prp.equals("RFIDCD"))
                            val = util.nvl(rs.getString("rmk"));
                      
                        if(prp.equals("CP")){
                            pktPrpMap.put("cpRte", val);
                            val = util.nvl(rs.getString("prte"));
                        }
                        
                        if(prp.equals("CP_DIS"))
                            val = util.nvl(rs.getString("cpdis"));
                        if(prp.equals("PRI_RAP_DIS"))
                        val = util.nvl(rs.getString("prirapdis"));
                        
                        if(prp.equals("KTSVIEW"))
                            val = util.nvl(rs.getString("kts"));
                        if(prp.equals("COMMENT"))
                            val = util.nvl(rs.getString("cmnt"));
                        if(prp.equals("CP_VLU"))
                            val = util.nvl(rs.getString("cptotal"));
                        if(prp.equals("NET_PUR_DIS"))
                        val = util.nvl(rs.getString("netPurRteDis"));
                        if(prp.equals("NET_PUR_VLU"))
                        val = util.nvl(rs.getString("netPurRteTotal"));
                            pktPrpMap.put(prp, val);
                        }

                    pktListMap.put(stkIdn , pktPrpMap);
                    pktStkIdnList.add(stkIdn);
                }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            if(isSrchRef){
                srchRefVal = srchRefVal.replaceAll(",", "");       
            if(!srchRefVal.equals("")){
         
                if(srchRef.equals("vnm")){
                    if(!srchRefVal.equals("")){
                       srchRefVal = util.pktNotFound(srchRefVal);
                        req.setAttribute("vnmNotFnd", srchRefVal);
                    }
                }
                if(srchRef.equals("cert_no")){
                 srchRefVal += " :-certificate numbers not found.";
                                    
            req.setAttribute("vnmNotFnd", srchRefVal);
                }
            }
            }
           
           req.setAttribute("pktStkIdnList", pktStkIdnList);
            try{
            util.updAccessLog(req,res,"Search Query", "SearchResult End");
            } catch (Exception sqle) {
                sqle.printStackTrace();
            }
            return pktListMap;
        }
    
  public HashMap SearchResultGT(HttpServletRequest req,HttpServletResponse res, String flg , ArrayList vwPrpLst) {
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr(); 
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
          try{
          util.updAccessLog(req,res,"Search Query", "SearchResult Start");
          } catch (Exception sqle) {
              sqle.printStackTrace();
          }
          String vnmLst = util.nvl((String)req.getAttribute("vnmLst"));
          int ct = db.execCall("ud_dsp_stt", "pkgmkt.upd_dsp_stt", new ArrayList());
          int vwPrpLstsz=vwPrpLst.size();
          HashMap pktListMap = new HashMap();
          HashMap mprp = null;
          ArrayList pktStkIdnList = new ArrayList();
          boolean isSrchRef = true;
          String srchRef = util.nvl((String)req.getAttribute("srchRef"));
          ArrayList soldPacketList = new ArrayList();
          double xrt= info.getXrt();
          String isMix = info.getIsMix();
          if(isMix.equals("MIX"))
              xrt=1;
          if(srchRef.equals(""))
              isSrchRef=false;
          String srchQ = "";
           String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
        String rapDisFmt = util.nvl((String)info.getDmbsInfoLst().get("RAPDISFMT"));
          if(rapDisFmt.equals(""))
               rapDisFmt="90.00";
          String cpPrp = "prte";
          if(vwPrpLst.contains("CP"))
          cpPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("CP")+1);
          String prirapPrp = "prte";
          if(vwPrpLst.contains("PRI_RAP_RTE"))
          prirapPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("PRI_RAP_RTE")+1);
          String netSaleRte = "";
          String netSql="  0 netDis  ";
          if(vwPrpLst.contains("NET_SALES_RTE")){
          netSaleRte =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("NET_SALES_RTE")+1);
              netSql=" trunc(((nvl("+netSaleRte+",1)*100)/greatest(rap_rte,1)) - 100,2) netDis ";
          }
          
          String SaleRte = "";
          String salSql="  0 salDis  ";
          if(vwPrpLst.contains("SAL_RTE")){
          SaleRte =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("SAL_RTE")+1);
          salSql=" trunc(((nvl("+SaleRte+",1)*100)/greatest(rap_rte,1)) - 100,2) salDis ";
          }
          
          String mfgPri = "";
          String mfgSql=" 0 mfgDis ";
          if(vwPrpLst.contains("MFG_PRI")){
          mfgPri =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("MFG_PRI")+1);
              mfgSql="  trunc(((nvl("+mfgPri+",1)*100)/greatest(rap_rte,1)) - 100,2) mfgDis  ";
          }
 
          String shfld = "prp_";
          mprp = info.getMprp();
          String srchQ1 = " select sk1, 0 dsp , a.stk_idn , cert_lab ,pair_id, dsp_stt ,byr,emp, decode(stt " +
          ", 'MKAP',' color:Olive'" + 
          ", 'MKPP','color:Green'" + 
          ", 'MKWH','color:Red'" + 
          " , 'MKLB','color:Maroon'" + 
          " , 'MKBD','color:Maroon'" + 
          " , 'MKAV', decode(instr(upper(dsp_stt), 'NEW'), 0, decode(instr(upper(dsp_stt), 'REV'),0,' ','color:Green'), 'color:Blue')" + 
          " , 'MKIS', 'color:Red'" + 
          " , 'MKEI', 'color:Red'" + 
          " , 'LB_PRI', 'color:#C80BCF'" +
          " , 'LB_IS', 'color:#C80BCF'" +
          " , 'MKSL', ' color:Green'" +
          " , 'MKSD', ' color:Green'" +
           " , 'SOLD') class ,  ";
          String srchQ2 = " select sk1, 1 dsp , a.stk_idn , cert_lab ,pair_id, '' dsp_stt ,byr,emp, '' class , ";
          String dspStk = "stkDspFF";
          String getQuot = "quot rte";
         srchQ += "  decode(PKT_TY,'NR',to_char(trunc(cts,2),'999999990.99'),trunc(cts,3)) crtwt, trunc(nvl(sl_rte,0),3) ttlCts , " +
                  //decode(quot, 0, 0, decode(nvl(rap_dis,0), 0, 101, trunc(((quot*100)/greatest(rap_rte,100)) - 100,2))) rr_dis, "+
                  " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, to_char(rap_dis, '"+rapDisFmt+"')))  r_dis " +
                  " , stk_idn  ,pair_id , stt,decode(stt,'MKAP','O','MKPP','G','MKWA','G','MKWH','R','MKLB','M','MKBD','M','MKAV',decode(instr(upper(dsp_stt), 'NEW'), 0, decode(instr(upper(dsp_stt), 'REV'),0,' ','G'), 'B'),'MKOS_IS','R','MKKS_IS','R','MKIS','R','MKEI','R','LB_PRI','P','LB_IS','P','') color, vnm, kts_vw kts, cert_lab cert, cert_no, rmk, img,img2, cmp , trunc(((cmp*100)/greatest(rap_rte,1)) - 100,2) cmp_dis ,to_char(cts * cmp, '9999999999990.00') basval, decode(PKT_TY,'NR',to_char(trunc(cts,2),'999999990.99'),to_char(trunc(cts,3),'999999990.999'))  cts, qty , rap_rte , to_char(cts * (quot/"+xrt+") , '99999999990.00') usdVal , to_char(cts * (vlu_inr/"+xrt+") , '99999999990.00') usdValLoy,"+
                  getQuot +","+netSql+","+mfgSql+","+salSql+
                  ",to_char(trunc(((quot-nvl(vlu_usd,0))/greatest(to_number(vlu_usd),1))*100, 2),'90.99') diffRev, cmnt,stt stt1,PKT_TY,  nvl(fquot,0) fquot ,ofr_rte,ofr_dis,CMNT2 CMNT2,lmt lmtRte, nvl("+cpPrp+",prte) prte , to_char(nvl(nvl("+cpPrp+",prte),0)*nvl(cts,0), '99999999999999990.00') cptotal ,trunc(((greatest(nvl("+cpPrp+",prte),1)*100)/greatest(rap_rte,1)) - 100,2) cpdis, nvl("+prirapPrp+",prte) priraprte ,trunc(((greatest(nvl("+prirapPrp+",prte),1)*100)/greatest(rap_rte,1)) - 100,2) prirapdis, flg , decode(PKT_TY,'NR',to_char(cts * rap_rte, '99999999990.00'),to_char(cts * rap_rte, '99999999990.000') ) rapval ,to_char(cts * quot, '99999990.00') amt , " +
                  " decode(nvl(fquot,0),0, 'NA', decode(fquot, quot, 'NA', decode(greatest(fquot, quot),quot, 'UP','DN'))) cmp_flg,diamondImg,jewImg,srayImg,agsImg,mrayImg,ringImg,lightImg,refImg,videos,certImg,video_3d ";

       

          for (int i = 0; i < vwPrpLstsz; i++) {
              String fld = "prp_";
              String fldsrt = "srt_";
              int j = i + 1;
              if (j < 10){
                  fld += "00" + j;
                  fldsrt += "00" + j;
              }else if (j < 100){
                  fld += "0" + j;
                  fldsrt += "0" + j;
              }else if (j > 100){
                  fld += j;
                  fldsrt += j;
              }

              
              srchQ += ", " + fld;
              srchQ += ", " + fldsrt;
             
           }

       
          String rsltQ =
             srchQ1+" "+srchQ + " from gt_srch_rslt a where flg in ("+flg+") and a.cts > 0 union " +srchQ2+ " "+srchQ +"   from gt_srch_multi a  where a.cts > 0 " ;
            String isFlgOD = util.nvl((String)req.getAttribute("flgORDER"));
            if(isFlgOD.equals("Y"))
                rsltQ = rsltQ+  " order by flg , 1, 2,3,4";
          else
              rsltQ = rsltQ+  " order by 1, 2,3,4";
          
          ArrayList ary = new ArrayList();
        ArrayList rsLst1 = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)rsLst1.get(0);
        ResultSet rs = (ResultSet)rsLst1.get(1);
          String  srchRefVal = util.nvl((String)req.getAttribute("srchRefVal"));
          String srchMsg = "";
          pktListMap = new HashMap();
          try {
              while (rs.next()) {
                GtPktDtl pktPrpMap = new GtPktDtl();

                  String vnm =util.nvl(rs.getString("vnm"));
                  String tfl3 =util.nvl(rs.getString("rmk"));
                  String cert_No = util.nvl(rs.getString("cert_no"));
                  String pair_id = util.nvl(rs.getString("pair_id"));
                  String prte = util.nvl(rs.getString("prte"));
                  if(isSrchRef){
                      
                      if(srchRef.equals("vnm")){
                          if(srchRefVal.indexOf(vnm)!=-1 ){
                            srchRefVal = srchRefVal.replaceAll("'"+vnm+"'","");
                          }else  if(srchRefVal.indexOf(tfl3)!=-1 ){
                              srchRefVal = srchRefVal.replaceAll("'"+tfl3+"'","");
                          }
                       }
                      if(srchRef.equals("cert_no")){
                          if(srchRefVal.indexOf(cert_No)!=-1)
                              srchRefVal = srchRefVal.replaceAll("'"+cert_No+"'","");
                              
                   }
                  }
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
                  String stkIdn = util.nvl(rs.getString("stk_idn"));
                  String stt = util.nvl(rs.getString("stt"));
                  String dspStt = util.nvl(rs.getString("dsp_stt"));
                  if(stt.equals("MKSL") || stt.equals("MKSD") || stt.equals("BRC_MKSD"))
                      soldPacketList.add(stkIdn);
                  pktPrpMap.setSk1(new BigDecimal(util.nvl(rs.getString("sk1"),"0")));
                  pktPrpMap.setValue("flg", util.nvl(rs.getString("flg")));
                  pktPrpMap.setValue("stt", dspStt);
                  pktPrpMap.setValue("pair_id", pair_id);
                  pktPrpMap.setValue("stk_idn",stkIdn);
                  pktPrpMap.setValue("vnm",vnm);
                  pktPrpMap.setValue("qty",util.nvl(rs.getString("qty")));
                  pktPrpMap.setValue("class",util.nvl(rs.getString("class")));
                  pktPrpMap.setValue("color",util.nvl(rs.getString("color")));
                  pktPrpMap.setValue("cts", util.nvl(rs.getString("cts")));
                  pktPrpMap.setValue("cert_no",cert_No);
                  pktPrpMap.setValue("stt1",util.nvl(rs.getString("stt1")));
                  pktPrpMap.setValue("quot",util.nvl(rs.getString("rte")));
                  pktPrpMap.setValue("fquot",util.nvl(rs.getString("fquot")));
                  pktPrpMap.setValue("cmp",util.nvl(rs.getString("cmp")));
                  pktPrpMap.setValue("pairId",util.nvl(rs.getString("pair_id")));
                  pktPrpMap.setValue("cmp_dis",util.nvl(rs.getString("cmp_dis")));
                  pktPrpMap.setValue("rap_rte",util.nvl(rs.getString("rap_rte")));
                  pktPrpMap.setValue("r_dis",util.nvl(rs.getString("r_dis")));
                  pktPrpMap.setValue("cert_lab", util.nvl(rs.getString("cert_lab")));
                  pktPrpMap.setValue("AMT", util.nvl(rs.getString("amt")));
                  pktPrpMap.setValue("RAPVAL", util.nvl(rs.getString("rapval")));
                  pktPrpMap.setValue("basval", util.nvl(rs.getString("basval")));
                  pktPrpMap.setValue("RAPVAL", util.nvl(rs.getString("rapval")));
                  pktPrpMap.setValue("USDVAL", util.nvl(rs.getString("usdVal")));
                  pktPrpMap.setValue("USDVALLOY", util.nvl(rs.getString("usdValLoy")));
                  pktPrpMap.setValue("PKT_TY", util.nvl(rs.getString("PKT_TY")));
                    pktPrpMap.setValue("ttlCts", util.nvl(rs.getString("ttlCts")));
                  pktPrpMap.setValue("cpTotal", util.nvl(rs.getString("cptotal")));
                  pktPrpMap.setValue("cpDis", util.nvl(rs.getString("cpdis")));
                  pktPrpMap.setValue("ofr_rte", util.nvl(rs.getString("ofr_rte")));
                  pktPrpMap.setValue("ofr_dis", util.nvl(rs.getString("ofr_dis")));
                  pktPrpMap.setValue("offerCol", util.nvl(rs.getString("CMNT2")));
                  pktPrpMap.setValue("offerlmtRte", util.nvl(rs.getString("lmtRte")));
                  pktPrpMap.setValue("diamondImg",util.nvl(rs.getString("diamondImg"),"N"));
                  pktPrpMap.setValue("jewImg",util.nvl(rs.getString("jewImg"),"N"));
                  pktPrpMap.setValue("srayImg",util.nvl(rs.getString("srayImg"),"N"));
                  pktPrpMap.setValue("agsImg",util.nvl(rs.getString("agsImg"),"N"));
                  pktPrpMap.setValue("mrayImg",util.nvl(rs.getString("mrayImg"),"N"));
                  pktPrpMap.setValue("ringImg",util.nvl(rs.getString("ringImg"),"N"));
                  pktPrpMap.setValue("lightImg",util.nvl(rs.getString("lightImg"),"N"));
                  pktPrpMap.setValue("refImg",util.nvl(rs.getString("refImg"),"N"));
                  pktPrpMap.setValue("videos",util.nvl(rs.getString("videos"),"N"));
                  pktPrpMap.setValue("certImg",util.nvl(rs.getString("certImg"),"N"));
                  pktPrpMap.setValue("video_3d",util.nvl(rs.getString("video_3d"),"N"));
                  for (int j = 0; j < vwPrpLstsz; j++) {
                      String prp = (String)vwPrpLst.get(j);

                      String fld = "prp_";
                      String fldsrt = "srt_";
                      if (j < 9){
                          fld = "prp_00" + (j + 1);
                          fldsrt = "srt_00" + (j + 1);
                      }else{
                          fld = "prp_0" + (j + 1);
                          fldsrt = "srt_0" + (j + 1);
                      }
                      String val = util.nvl(rs.getString(fld));
                      String valsrt=util.nvl(rs.getString(fldsrt));
                      if (prp.toUpperCase().equals("CRTWT"))
                          val = util.nvl(rs.getString("cts"));
                      if (prp.toUpperCase().equals("CERT_NO"))
                          val = util.nvl(rs.getString("cert_no"));
                      if (prp.toUpperCase().equals("RAP_DIS"))
                          val = util.nvl(rs.getString("r_dis"));
                      if (prp.toUpperCase().equals("UPR_DIS"))
                          val = util.nvl(rs.getString("r_dis"));
                      if (prp.toUpperCase().equals("CMP_DIS")){
                          if(val.equals(""))
                          val = util.nvl(rs.getString("cmp_dis")); 
                      }
                      if (prp.toUpperCase().equals("RAP_RTE"))
                          val = util.nvl(rs.getString("rap_rte"));
                     if (prp.toUpperCase().equals("NET_SALES_DIS"))
                              val = util.nvl(rs.getString("netDis"));
                          if (prp.toUpperCase().equals("SAL_DIS"))
                                   val = util.nvl(rs.getString("salDis"));
                          if (prp.toUpperCase().equals("MFG_DIS"))
                                   val = util.nvl(rs.getString("mfgDis"));
                     if (prp.toUpperCase().equals("RAP_RTE"))
                              val = util.nvl(rs.getString("rap_rte"));
                      if(prp.equalsIgnoreCase("MEM_COMMENT"))
                          val = util.nvl(rs.getString("img2"));
                      if(prp.equals("RTE"))
                          val = util.nvl(rs.getString("rte"));
                      if(prp.equals("CMP_RTE")){
                        if(val.equals(""))
                            val = util.nvl(rs.getString("cmp"));
                      }
                      if(prp.equals("RFIDCD"))
                          val = util.nvl(rs.getString("rmk"));
                    
                      if(prp.equals("CP")){
                          pktPrpMap.setValue("cpRte", val);
                          val = util.nvl(rs.getString("prte"));
                      }
                      
                      if(prp.equals("CP_DIS"))
                          val = util.nvl(rs.getString("cpdis"));
                      if(prp.equals("PRI_RAP_DIS"))
                      val = util.nvl(rs.getString("prirapdis"));
                      
                      if(prp.equals("KTSVIEW"))
                          val = util.nvl(rs.getString("kts"));
                      if(prp.equals("DIFF"))
                          val = util.nvl(rs.getString("diffRev"));
                      if(prp.equals("COMMENT"))
                          val = util.nvl(rs.getString("cmnt"));
                     
                       pktPrpMap.setValue(prp, val);
                       pktPrpMap.setValue(prp+"_SRT", valsrt);
                      }

                  pktListMap.put(stkIdn , pktPrpMap);
              }
              rs.close();
              pst.close();
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
          if(isSrchRef){
              srchRefVal = srchRefVal.replaceAll(",", "");       
          if(!srchRefVal.equals("")){
       
              if(srchRef.equals("vnm")){
                  if(!srchRefVal.equals("")){
                     srchRefVal = util.pktNotFound(srchRefVal);
                      req.setAttribute("vnmNotFnd", srchRefVal);
                  }
              }
              if(srchRef.equals("cert_no")){
               srchRefVal += " :-certificate numbers not found.";
                                  
          req.setAttribute("vnmNotFnd", srchRefVal);
              }
          }
          }
          req.setAttribute("SOLDSTONELIST", soldPacketList);
          pktListMap =(HashMap)sortByComparator(pktListMap);
          return pktListMap;
      }
  

    public  Map<String, HashMap> sortByComparatorMap(Map<String, HashMap> unsortMap) {
     
        // Convert Map to List
        List<Map.Entry<String, HashMap>> list = 
          new LinkedList<Map.Entry<String, HashMap>>(unsortMap.entrySet());
     
        // Sort list with comparator, to compare the Map values
        Collections.sort(list, new Comparator<Map.Entry<String, HashMap>>() {
          public int compare(Map.Entry<String, HashMap> o1,
                                               Map.Entry<String, HashMap> o2) {
            return ((Comparable) ((Map.Entry<String, HashMap>) (o1)).getValue().get("SK1") )
                              .compareTo(((Map.Entry<String, HashMap>) (o2)).getValue().get("SK1"));
          }
        });
     
        // Convert sorted map back to a Map
        Map<String, HashMap> sortedMap = new LinkedHashMap<String, HashMap>();
            for (Iterator<Map.Entry<String, HashMap>> it = list.iterator(); it.hasNext();) {
              Map.Entry<String, HashMap> entry = it.next();
              sortedMap.put(entry.getKey(), entry.getValue());
            }
            return sortedMap;
       
      }
  
  public  Map<String, GtPktDtl> sortByComparator(Map<String, GtPktDtl> unsortMap) {
   
      // Convert Map to List
      List<Map.Entry<String, GtPktDtl>> list = 
        new LinkedList<Map.Entry<String, GtPktDtl>>(unsortMap.entrySet());
   
      // Sort list with comparator, to compare the Map values
      Collections.sort(list, new Comparator<Map.Entry<String, GtPktDtl>>() {
        public int compare(Map.Entry<String, GtPktDtl> o1,
                                             Map.Entry<String, GtPktDtl> o2) {
          return ((Comparable) ((Map.Entry<String, GtPktDtl>) (o1)).getValue().getSk1())
                            .compareTo(((Map.Entry<String, GtPktDtl>) (o2)).getValue().getSk1());
        }
      });
   
      // Convert sorted map back to a Map
      Map<String, GtPktDtl> sortedMap = new LinkedHashMap<String, GtPktDtl>();
          for (Iterator<Map.Entry<String, GtPktDtl>> it = list.iterator(); it.hasNext();) {
            Map.Entry<String, GtPktDtl> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
          }
          return sortedMap;
     
    }
   
    public  Map<String, GtPktDtl> sortByComparator(Map<String, GtPktDtl> unsortMap, final String orderBy, final String orderPrp) {

            // Convert Map to List
            List<Map.Entry<String, GtPktDtl>> list =
              new LinkedList<Map.Entry<String, GtPktDtl>>(unsortMap.entrySet());
            // Sort list with comparator, to compare the Map values
            Collections.sort(list, new Comparator<Map.Entry<String, GtPktDtl>>() {
              public int compare(Map.Entry<String, GtPktDtl> o1,Map.Entry<String, GtPktDtl> o2) {
              if(orderPrp.equals("AMT")){
                if(orderBy.equals("ASC")) 
                return ((Comparable) ((Map.Entry<String, GtPktDtl>) (o1)).getValue().getValue("AMT")).compareTo(((Map.Entry<String, GtPktDtl>) (o2)).getValue().getValue("AMT"));
                else
                return ((Comparable) ((Map.Entry<String, GtPktDtl>) (o2)).getValue().getValue("AMT")).compareTo(((Map.Entry<String, GtPktDtl>) (o1)).getValue().getValue("AMT"));
                } else if(orderPrp.equals("VNM")){
                if(orderBy.equals("ASC")) 
                return ((Comparable) ((Map.Entry<String, GtPktDtl>) (o1)).getValue().getValue("vnm")).compareTo(((Map.Entry<String, GtPktDtl>) (o2)).getValue().getValue("vnm"));
                else
                return ((Comparable) ((Map.Entry<String, GtPktDtl>) (o2)).getValue().getValue("vnm")).compareTo(((Map.Entry<String, GtPktDtl>) (o1)).getValue().getValue("vnm"));
                }else if(orderPrp.equals("DSP_STT")){
                if(orderBy.equals("ASC")) 
                return ((Comparable) ((Map.Entry<String, GtPktDtl>) (o1)).getValue().getValue("stt")).compareTo(((Map.Entry<String, GtPktDtl>) (o2)).getValue().getValue("stt"));
                else
                return ((Comparable) ((Map.Entry<String, GtPktDtl>) (o2)).getValue().getValue("stt")).compareTo(((Map.Entry<String, GtPktDtl>) (o1)).getValue().getValue("stt"));
                } else if(orderPrp.equals("CRTWT") || orderPrp.equals("CERT_NO") || orderPrp.equals("RAP_DIS") || orderPrp.equals("UPR_DIS") || orderPrp.equals("CMP_DIS") || orderPrp.equals("RAP_RTE") || orderPrp.equals("MEM_COMMENT") || orderPrp.equals("RTE") || orderPrp.equals("CMP_RTE") || orderPrp.equals("RFIDCD") || orderPrp.equals("CP") || orderPrp.equals("CP_DIS") || orderPrp.equals("PRI_RAP_DIS") || orderPrp.equals("KTSVIEW") || orderPrp.equals("COMMENT") || orderPrp.equals("SAL_BYR") || orderPrp.equals("SAL_EMP")){
                if(orderBy.equals("ASC")) 
                return ((Comparable) ((Map.Entry<String, GtPktDtl>) (o1)).getValue().getValue(orderPrp)).compareTo(((Map.Entry<String, GtPktDtl>) (o2)).getValue().getValue(orderPrp));
                else
                return ((Comparable) ((Map.Entry<String, GtPktDtl>) (o2)).getValue().getValue(orderPrp)).compareTo(((Map.Entry<String, GtPktDtl>) (o1)).getValue().getValue(orderPrp));
                }else {
                if(orderBy.equals("ASC")) 
                return ((Comparable) ((Map.Entry<String, GtPktDtl>) (o1)).getValue().getValue(orderPrp+"_SRT")).compareTo(((Map.Entry<String, GtPktDtl>) (o2)).getValue().getValue(orderPrp+"_SRT"));
                else
                return ((Comparable) ((Map.Entry<String, GtPktDtl>) (o2)).getValue().getValue(orderPrp+"_SRT")).compareTo(((Map.Entry<String, GtPktDtl>) (o1)).getValue().getValue(orderPrp+"_SRT"));
                }
              }
            });
            // Convert sorted map back to a Map
            Map<String, GtPktDtl> sortedMap = new LinkedHashMap<String, GtPktDtl>();
                for (Iterator<Map.Entry<String, GtPktDtl>> it = list.iterator(); it.hasNext();) {
                  Map.Entry<String, GtPktDtl> entry = it.next();
                  sortedMap.put(entry.getKey(), entry.getValue());
                }
                return sortedMap;
        }
  
  public HashMap refineObj(HttpServletRequest req ,HashMap pktDtl,ArrayList prpVwList){
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
    
       HashMap refinPktDtl = new HashMap();
       HashMap mprp = info.getMprp();
    ArrayList stockIdnLst =new ArrayList();
    Set<String> keys = pktDtl.keySet();
          for(String key: keys){
         stockIdnLst.add(key);
          }
     for(int j=0;j<stockIdnLst.size();j++){
       String stkIdn = (String)stockIdnLst.get(j);
         GtPktDtl pkts =(GtPktDtl)pktDtl.get(stkIdn);
        for(int i=0;i<prpVwList.size();i++){
         String lprp = (String)prpVwList.get(i);
          String lprpTyp = (String)mprp.get(lprp+"T");
          ArrayList valLst = (ArrayList)refinPktDtl.get(lprp);
         String lprpVal = util.nvl(pkts.getValue(lprp));

           if(valLst==null)
               valLst=new ArrayList();
            if(lprpTyp.equals("C")){
              if(!valLst.contains(lprpVal))
                  valLst.add(lprpVal);

            }else{
                lprpVal = util.nvl(lprpVal,"0");
                String min="";
                String max="";
                if(valLst.size()==0){
                  min=lprpVal;
                  max=lprpVal;
                }else{
                  min =(String)valLst.get(0);
                  max=(String)valLst.get(1);
                }
              double lprpValnum = Double.parseDouble(lprpVal);
              double minnum=Double.parseDouble(min);
              double maxnum=Double.parseDouble(max);
              valLst=new ArrayList();

                if(lprpValnum < minnum)
                    minnum = lprpValnum;
                if(lprpValnum >= maxnum)
                    maxnum = lprpValnum;
                valLst.add(String.valueOf(minnum));
                valLst.add(String.valueOf(maxnum));
            }
         refinPktDtl.put(lprp, valLst);

           
       }}
       return refinPktDtl;
  }
  
    public ArrayList getSrchList(HttpServletRequest req , HttpServletResponse res,HashMap paramMap){

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
    HashMap mprp = null;
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("prpList");
    String srchQ = null;
    HashMap pktPrpMap = new HashMap();
    int prpSHseq = vwPrpLst.indexOf("SH");
    String shfld = "prp_";
    String nmeIdn = util.nvl((String)paramMap.get("nmeIdn"));
    String empIdn = util.nvl((String)paramMap.get("empIdn"));
    String frmDte = util.nvl((String)paramMap.get("frmDte"));
    String todte = util.nvl((String)paramMap.get("toDte"));
    String web = util.nvl((String)paramMap.get("web"));
    String diaflex = util.nvl((String)paramMap.get("diaflex"));
    String history = util.nvl((String)paramMap.get("history"));
    String pepTyp = util.nvl((String)paramMap.get("pepTyp"));
    mprp = info.getMprp();
    String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
    ArrayList ary = new ArrayList();
    String byrQ=" c.nme byrNm,";
    if(cnt.equals("kj"))
        byrQ=" GET_ANA_BYR_NM(c.nme_idn) byrNm,";
    srchQ = "select sk1, d.cur||' -'||d.aadat_comm||' '||byr.get_trms(d.nmerel_idn) term,byr.get_nm(c.emp_idn) emp,cts crtwt, round(b.ofr_rte) ofr_rte , round(b.net_rte) net_rte ,  b.chg_typ , b.diff, b.rel_idn ,decode(b.stt,'ALC','YES','NO') orStt  , "+byrQ+
    " to_char(decode(b.quot, 0, 0, decode(nvl(a.rap_dis,0), 0, 0, trunc(((b.quot*100/decode(d.cur, 'USD', 1, decode(b.exh_rte,1,GET_XRT,b.exh_rte)))/greatest(a.rap_rte,100)) - 100,2))),'999999990.00') r_dis , "+
    " to_char(decode(a.cmp, 0, 0, decode(nvl(a.rap_dis,0), 0, 0, trunc(((a.cmp*100)/greatest(a.rap_rte,100)) - 100,2))),'999999990.00') mr_dis , "+
    " to_char(trunc((((b.ofr_rte)*100/(decode(d.cur, 'USD', 1, decode(b.exh_rte,1,GET_XRT,b.exh_rte))))/greatest(a.rap_rte,100)) - 100,2),'90.00') ofr_dis , b.lmt_rte ,  "+
    " to_char(trunc(((round(b.net_rte)*100)/greatest(a.rap_rte,100)) - 100,2),'999999990.00') net_dis , "+
    " stk_idn, b.quot, a.vnm, kts_vw kts, cmp , cert_lab cert, cert_no,b.idn , a.rap_rte , to_char(trunc(cts,2),'999999990.99') cts, b.idn bidIdn , "+
    " a.stt, nvl(fquot,0) fquot , b.ofr_rte , to_char(b.to_dt,'dd-Mon-rrrr') toDte ,to_char( b.frm_dt , 'dd-Mon-rrrr') frmDte,b.NOW_RNK,trunc(((b.net_rte-nvl(a.cmp,0))/greatest(to_number(a.cmp),1))*100, 2) plper,trunc(((b.ofr_rte-nvl(b.quot,0))/greatest(to_number(b.quot),1))*100, 2) plperByr ";
    for (int i = 0; i < vwPrpLst.size(); i++) {
    String fld = "prp_";
    int j = i + 1;

    if (j < 10) {
    fld += "00" + j;
    } else if (j < 100) {
    fld += "0" + j;
    } else if (j > 100) {
    fld += j;
    }

    if (i == prpSHseq) {
    shfld = fld;
    }

    srchQ += ", " + fld;
    }
    ary = new ArrayList();
    ary.add("Z");
    String rsltQ = srchQ + " from gt_srch_rslt a , web_bid_wl b , nme_v c , nmerel d , nme_rel_all_v r where a.flg = ? and " +
    " a.stk_idn = b.mstk_idn and a.srch_id=b.idn and b.byr_idn = c.nme_idn and " +
    " d.nme_idn = c.nme_idn and d.nmerel_idn = b.rel_idn    and d.nmerel_idn = r.nmerel_idn" ;
    
    if(history.equals("")){
        rsltQ+= "  and trunc(nvl(b.to_dt, sysdate)) >= trunc(sysdate)   and b.stt='A' ";
    }

    if(!nmeIdn.equals("0") && !nmeIdn.equals("")){
    rsltQ+= " and b.byr_idn = ? ";
    ary.add(nmeIdn);
    }
    if(!empIdn.equals("0") && !empIdn.equals("")){
    rsltQ+= " and c.emp_idn = ? ";
    ary.add(empIdn);
    }
    if(!frmDte.equals("") && !todte.equals("")){
    rsltQ+= " and trunc(b.frm_dt) between to_date('"+frmDte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";

    }
    if(!web.equals("") && diaflex.equals(""))
    rsltQ+=" and b.flg='WEB' ";
    if(!diaflex.equals("") && web.equals(""))
    rsltQ+=" and b.flg='DF' ";
    
    if(!pepTyp.equals("")){
    rsltQ+=" and b.typ=? ";
    ary.add(pepTyp);
    }

    if(!cnt.equals("ag") && !cnt.equals("kj"))
    rsltQ+=" order by c.nme,b.frm_dt desc ";
    else if(cnt.equals("kj"))
    rsltQ+=" order by a.sk1 ";
    else
    rsltQ+=" order by a.vnm ";
    

      ArrayList rsLst1 = db.execSqlLst("search Result", rsltQ, ary);
      PreparedStatement pst = (PreparedStatement)rsLst1.get(0);
      ResultSet rs = (ResultSet)rsLst1.get(1);

    pktList = new ArrayList();

    try {
    while(rs.next()) {

    pktPrpMap = new HashMap();





    pktPrpMap.put("byrNm", util.nvl(rs.getString("byrNm")));
    pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
    pktPrpMap.put("cmp", util.nvl(rs.getString("cmp")));
    String vnm = util.nvl(rs.getString("vnm"));
    pktPrpMap.put("vnm",vnm);
    pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
    pktPrpMap.put("fquot", util.nvl(rs.getString("fquot")));
    pktPrpMap.put("term",util.nvl(rs.getString("term")));
        pktPrpMap.put("orStt",util.nvl(rs.getString("orStt")));
    pktPrpMap.put("emp", util.nvl(rs.getString("emp")));
    pktPrpMap.put("offer_rte",util.nvl(rs.getString("ofr_rte")));
    pktPrpMap.put("offer_dis", util.nvl(rs.getString("ofr_dis")));
    pktPrpMap.put("net_rte",util.nvl(rs.getString("net_rte")));
    pktPrpMap.put("net_dis", util.nvl(rs.getString("net_dis")));
    pktPrpMap.put("dis", util.nvl(rs.getString("r_dis")));
    pktPrpMap.put("typ", util.nvl(rs.getString("chg_typ")));
    pktPrpMap.put("diff", util.nvl(rs.getString("diff")));
        pktPrpMap.put("plper", util.nvl(rs.getString("plper")));
        pktPrpMap.put("plperByr", util.nvl(rs.getString("plperByr")));
    pktPrpMap.put("relIdn", util.nvl(rs.getString("rel_idn")));
    pktPrpMap.put("mr_dis", util.nvl(rs.getString("mr_dis")));
    pktPrpMap.put("toDte", util.nvl(rs.getString("toDte")));
    pktPrpMap.put("frmDte", util.nvl(rs.getString("frmDte")));
    pktPrpMap.put("bidIdn", util.nvl(rs.getString("bidIdn")));
    pktPrpMap.put("ofr_lmt", util.nvl(rs.getString("lmt_rte")));
    pktPrpMap.put("rnk", util.nvl(rs.getString("NOW_RNK")));
    /*
    String lab = info.getVal(rs.getString("cert"));
    pktPrpMap.put("cert",lab);
    String certNo = info.getVal(rs.getString("cert_no"));
    pktPrpMap.put("cert_no",certNo);
    */
    String wt = util.nvl(rs.getString("cts"));

    pktPrpMap.put("cts",wt);

    pktPrpMap.put("quot", util.nvl(rs.getString("quot")));
    pktPrpMap.put("cmp", util.nvl(rs.getString("cmp")));

    //String certImgLink = "<a href=\"http://cert.kapugems.com/view-cert.aspx?ID="+certNo+ ".jpg\">"+cert+"</a>";
    ArrayList dspBlocked = info.getPrpDspBlocked();
    pktPrpMap.put("Ref No", vnm);


    for(int j=0; j < vwPrpLst.size(); j++){
    String prp = (String)vwPrpLst.get(j);

    String fld="prp_";
    if(j < 9)
    fld="prp_00"+(j+1);
    else
    fld="prp_0"+(j+1);

    String val = util.nvl(rs.getString(fld)) ;
    if(prp.toUpperCase().equals("RAP_DIS"))
    val = util.nvl(rs.getString("r_dis"));

    if(prp.toUpperCase().equals("RAP_RTE"))
    val = util.nvl(rs.getString("rap_rte"));

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

    return pktList;
    }

//    public String srchDscription(String srchID) {
//           String dsc = "";
//           ResultSet rs = null;
//           
//           try {
//               String getDscr =
//                   " select a.dsc prp, " + " decode(lag(a.dsc) over (order by e.srt, d.sfr), a.dsc, ',',''||a.dsc) lag_prp," +
//                   "decode(a.dta_typ, 'C', b.prt1, to_char(trunc(d.sfr,2), '90.00')||'-'||to_char(trunc(d.sto,2), '90.00')) valFr, decode(a.dta_typ, 'C', c.prt1, d.sto) valTo, e.srt " +
//                   "from mprp a, prp b, prp c, srch_Dtl_sub d, rep_prp e " +
//                   "where a.prp = d.mprp and c.mprp = d.mprp and b.mprp = d.mprp and b.val = d.vfr and c.val = d.vto " +
//                   "and d.mprp = e.prp and e.mdl = ? and d.srch_id = ? order by e.srt, d.sfr ";
//               ArrayList ary = new ArrayList();
//               ary.add("MEMO_VW");
//               ary.add(srchID);
//               rs = db.execSql("Get DSC", getDscr, ary);
//               while (rs.next()) {
//                   String prp = rs.getString("lag_prp");
//                   String val = rs.getString("valfr");
//                   String styl = "nrm";
//                   if (prp.indexOf(",") > -1) {
//                       dsc = dsc + " " + prp;
//                   } else {
//                       dsc = dsc + " " + prp;
//                   }
//                   dsc = dsc + " " + val;
//               }
//               rs.close();
//               String getDsS =
//                   "select a.dsc prp, decode(a.dta_typ, 'C', b.prt1, d.sfr) valFr, decode(a.dta_typ, 'C', c.prt1, d.sto) valTo, e.srt " +
//                   "from mprp a, prp b, prp c, srch_Dtl d, rep_prp e " +
//                   "where a.prp = d.mprp and c.mprp = d.mprp and b.mprp = d.mprp and b.val = d.vfr and c.val = d.vto " +
//                   " and d.mprp = e.prp and e.mdl = ? and d.flg = 'R' " +
//                   "and not exists (select 1 from srch_dtl_sub a1 where a1.srch_id = d.srch_id and a1.mprp = d.mprp) " +
//                   " and d.srch_id = ? order by 4 ";
//               ary = new ArrayList();
//               ary.add("MEMO_VW");
//               ary.add(srchID);
//               rs = db.execSql("Get DSC", getDsS, ary);
//               while (rs.next()) {
//                   String prp = rs.getString("prp");
//                   String valfr = rs.getString("valfr");
//                   String valto = rs.getString("valto");
//                   String styl = "nrm";
//                   if (!prp.equals("Certificate")) {
//                       if (prp.indexOf(",") > -1) {
//                           dsc = dsc + " " + prp;
//                       } else {
//                           dsc = dsc + " " + prp;
//                       }
//                       dsc = dsc + " " + valfr + "-" + valto;
//                   }
//               }
//               rs.close();
//           } catch (SQLException sqle) {
//               // TODO: Add catch code
//               sqle.printStackTrace();
//           }
//
//           return dsc;
//       }

   
    
    public void MailExcel(HSSFWorkbook hwb,HashMap params , HttpServletRequest req , HttpServletResponse res )throws Exception  {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        int byrIdn = info.getByrId();
        String salEml = "";
        String salExl = "select lower(byr.get_eml(emp_idn,'N')) salEml from mnme where nme_idn=? ";
        ArrayList ary = new ArrayList();
        ary.add(String.valueOf(byrIdn));
      ArrayList outLst = db.execSqlLst("salExl", salExl, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            salEml = util.nvl(rs.getString("salEml"));
        }
        rs.close();
        pst.close();
        ArrayList attAttachFilNme = new ArrayList();
        ArrayList attAttachTyp = new ArrayList();
        ArrayList attAttachFile = new ArrayList();
       HashMap dbmsInfo = info.getDmbsInfoLst();
       String fileNme = util.nvl((String)params.get("fileName"));
        fileNme = util.getToDteTime()+"_"+fileNme;
        String filePath = session.getServletContext().getRealPath("/") + fileNme;
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        hwb.write(fileOutputStream);
        attAttachFilNme.add(fileNme);
        attAttachTyp.add("application/vnd.ms-excel");
        attAttachFile.add(filePath);
         
        FormFile formFile = (FormFile)params.get("formFile");
        fileNme  = formFile.getFileName();
        filePath =  session.getServletContext().getRealPath("/")+ fileNme;
        File readFile = new File(filePath);
        if(!readFile.exists()){
           fileOutputStream = new FileOutputStream(filePath);
           fileOutputStream.write(formFile.getFileData());
           fileOutputStream.flush();
           fileOutputStream.close();
           attAttachFilNme.add(fileNme);
           attAttachTyp.add(formFile.getContentType());
           attAttachFile.add(filePath);
        }
         GenMail mail = new GenMail();
           
        info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));    
        info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));    
        info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));    
        info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));  
         mail.setInfo(info);    
         mail.init();
         String senderID =(String)dbmsInfo.get("SENDERID");
         String senderNm =(String)dbmsInfo.get("SENDERNM");
         String mailTo = (String)dbmsInfo.get("MAILTO");
         String eml=util.nvl(req.getParameter("eml"));
        String subject = util.nvl((String)params.get("sub"));
        mail.setSender(senderID, senderNm);
        mail.setSubject(subject+""+ new Date());
//        mail.setBCC("jignesh.jethwa@faunatechnologies.com");
//        mail.setBCC("surekha.kandhare@faunatechnologies.com");
//         mail.setBCC("techpurav@gmail.com");
         mail.setAttachments(new ArrayList());
        
         String isChecked =util.nvl(req.getParameter("byrEml"));

         
         String[] emlLst = eml.split(",");
         if(emlLst!=null){
         for(int i=0 ; i <emlLst.length; i++)
          {
            mail.setBCC(emlLst[i]);
          }}
          ArrayList emlIdList = (ArrayList)params.get("mailList");
          if(emlIdList!=null && emlIdList.size()>0){
              for(int i=0 ; i < emlIdList.size();i++)
                  mail.setTO((String)emlIdList.get(i));
          }
          if(!salEml.equals("")){
             mail.setTO(salEml);
              mail.setReplyTo(salEml);
          }
            mail.setMsgText(util.nvl((String)params.get("msg")));
            mail.setFileName(attAttachFilNme);
            mail.setAttachmentType(attAttachTyp);
            mail.setAttachments(attAttachFile);
            
        try {
            mail.send("");
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        
         fileOutputStream.close();
    }
    
    public int addExData(int oldsrchId, String typ,HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      ArrayList outLst = db.execSqlLst(" Srch Id", " select srch_id_seq.nextval from dual", new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
       ArrayList ary = null;
        ArrayList lprpS = null;
        ArrayList lprpV =null;
            
       HashMap prp  = info.getPrp(); 
        int srchId =0;
       int ct=0;
        try {
            if (rs.next()) {
             srchId  = rs.getInt(1);

                info.setSrchId(srchId);


                String msrchQ =
                    " insert into msrch (srch_id, rln_id, mdl, typ, dte, trm_val) " +
                    " select ?,?, 'WWW', ? , sysdate, ? from dual ";
                ary = new ArrayList();
                ary.add(Integer.toString(srchId));
                ary.add(Integer.toString(info.getRlnId()));
                ary.add(typ);
                ary.add(Double.toString(info.getCmpTrm()));

                ct = db.execUpd(" Insert Srch ", msrchQ, ary);

                if (ct > 0) {
                     lprpS = (ArrayList)prp.get("POLS");
                     lprpV = (ArrayList)prp.get("POLV");
                    int indexOfEx = lprpV.indexOf("EX");
                    String  insSrchPOL =
                              " insert into srch_dtl (srch_id ,mprp,vfr,vto,sfr,sto,dta_typ)" +
                              "select ?,?,?,?,?,?,? from dual ";
                      ary = new ArrayList();
                      ary.add(Integer.toString(srchId));
                      ary.add("POL");
                      ary.add("EX");
                      ary.add("EX");
                      ary.add(lprpS.get(indexOfEx));
                      ary.add(lprpS.get(indexOfEx));
                      
                      ary.add("C");
                      db.execUpd(" insert pol " , insSrchPOL, ary);
                     
                    lprpS = (ArrayList)prp.get("SYMS");
                    lprpV = (ArrayList)prp.get("SYMV");
                    int indexOfSym = lprpV.indexOf("EX")+1;
                    String  insSrchSYS =
                              " insert into srch_dtl (srch_id ,mprp,vfr,vto,sfr,sto,dta_typ)" +
                              "select ?,?,?,?,?,?,? from dual ";
                      ary = new ArrayList();
                      ary.add(Integer.toString(srchId));
                      ary.add("SYM");
                      ary.add(lprpV.get(indexOfSym));
                      ary.add(lprpV.get(lprpS.size()-1));
                      ary.add(lprpS.get(indexOfSym));
                      ary.add(lprpS.get(lprpS.size()-1));
                      
                      ary.add("C");
                      db.execUpd(" insert pol " , insSrchPOL, ary);
                    
                 String srchDtl = " insert into srch_dtl (srch_id ,mprp,vfr,vto,sfr,sto,dta_typ)" +
                                      "select  ?,a.mprp,a.vfr,a.vto,a.sfr,a.sto,a.dta_typ from  srch_dtl a where a.srch_id=? and a.mprp not in ('POL','SYM') ";
                 ary = new ArrayList();
                    ary.add(Integer.toString(srchId));
                    ary.add(Integer.toString(oldsrchId));
                    ct = db.execUpd("Insert Srch ", srchDtl, ary);
                 
                    String srchSubDtl =  "insert into srch_dtl_sub (srch_id, mprp, vfr, vto, nfr, nto, sfr, sto, dta_typ) " +
                                         "select  ?, a.mprp , a.vfr, a.vto, a.nfr, a.nto, a.sfr, a.sto, a.dta_typ from  srch_dtl a where a.srch_id=? and a.mprp not in ('POL','SYM') ";
                 
                 ct = db.execUpd("Insert Srch ", srchSubDtl, ary);
                }
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return srchId;
    }
    
    public ArrayList getExpDay( HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList expDayList = (session.getAttribute("expDayList") == null)?new ArrayList():(ArrayList)session.getAttribute("expDayList");
        try {
        if(expDayList.size() == 0) {
                String    memoPrntOptn = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                                 + "b.mdl = 'JFLEX' and b.nme_rule = 'EXP_DAY' and a.til_dte is null order by a.srt_fr ";
              ArrayList outLst = db.execSqlLst("memoPrint", memoPrntOptn, new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                UIForms memoOpn = new UIForms();
                memoOpn.setFORM_NME(rs.getString("chr_fr"));
                memoOpn.setFORM_TTL(rs.getString("dsc"));
                expDayList.add(memoOpn);
            }
            rs.close();
            pst.close();
                session.setAttribute("expDayList", expDayList);
            }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return expDayList;
    }
    
   
    public ArrayList getBuyerCabin( HttpServletRequest req , HttpServletResponse res){
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            ArrayList burCbList = (session.getAttribute("burCbList") == null)?new ArrayList():(ArrayList)session.getAttribute("burCbList");
           if(burCbList.size() == 0) {
                    HashMap prp = info.getPrp();
                    ArrayList prpPrt = (ArrayList)prp.get("BUYER_CABINP");
                    ArrayList prpVal = (ArrayList)prp.get("BUYER_CABINV");
                    if(prpVal!=null){
                    for(int i=0;i<prpVal.size();i++){
                        UIForms memoOpn = new UIForms();
                        memoOpn.setFORM_NME((String)prpVal.get(i));
                        memoOpn.setFORM_TTL((String)prpPrt.get(i));
                        burCbList.add(memoOpn);
                    }
                    }
           session.setAttribute("burCbList", burCbList);
           }
            return burCbList;
        }
    
    public ArrayList getTerm(HttpServletRequest req ,HttpServletResponse res, int ptyId){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      
        ArrayList trmsLst = new ArrayList();
        String favSrch = " select nvl(dflt_yn,'N') , dtls, nmerel_idn from nme_rel_v where nme_idn=? order by 1 desc ";
        ArrayList ary = new ArrayList();
        ary.add(String.valueOf(ptyId));
      ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {

                TrmsDao trms = new TrmsDao();
                trms.setRelId(rs.getInt("nmerel_idn"));
                trms.setTrmDtl(rs.getString("dtls"));

                trmsLst.add(trms);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        session.setAttribute("trmsLst", trmsLst);
        return trmsLst;
    }
    
    public ArrayList getdayTerm(HttpServletRequest req ,HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList trmsLst= (session.getAttribute("daytrmsLst") == null)?new ArrayList():(ArrayList)session.getAttribute("daytrmsLst");
        if(trmsLst.size()==0){
        String favSrch = "select idn,term from mtrms \n" + 
        "where trunc(nvl(till_dt, sysdate)) = trunc(sysdate) \n" + 
        "order by dys, pct";
        ArrayList ary = new ArrayList();
          ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {

                TrmsDao trms = new TrmsDao();
                trms.setRelId(rs.getInt("idn"));
                trms.setTrmDtl(rs.getString("term"));

                trmsLst.add(trms);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        }
        session.setAttribute("daytrmsLst", trmsLst);
        return trmsLst;
    }
    public ArrayList getTermALL(HttpServletRequest req ,HttpServletResponse res, int ptyId){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      
        ArrayList trmsLst = new ArrayList();
        String favSrch = " select  dtls , nmerel_idn from  nme_rel_all_v where nme_idn=?  ";
        ArrayList ary = new ArrayList();
        ary.add(String.valueOf(ptyId));
      ArrayList outLst = db.execSqlLst("favSrch",favSrch, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {

                TrmsDao trms = new TrmsDao();
                trms.setRelId(rs.getInt(2));
                trms.setTrmDtl(rs.getString(1));

                trmsLst.add(trms);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        session.setAttribute("trmsLst", trmsLst);
        return trmsLst;
    }
    public ArrayList getDmdList(HttpServletRequest req , HttpServletResponse res, int ptyId){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      
        ArrayList dmdLst = new ArrayList();
        String dmdSrch = " select dmd_id , nvl(dsc,'Not Given')||' : '||to_Char(mdl) dsc from mdmd where rln_id = ? and todte is null   order by  dmd_id desc  ";
        ArrayList ary = new ArrayList();
        ary.add(String.valueOf(ptyId));
      ArrayList outLst = db.execSqlLst("dmdSrch",dmdSrch, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                ArrayList dmd = new ArrayList();
                dmd.add(rs.getString(1));
                dmd.add(rs.getString(2));
                dmdLst.add(dmd);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        
        return dmdLst;
    }
    
  public ArrayList getContactList(HttpServletRequest req , HttpServletResponse res, int ptyId){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    
      ArrayList contactLst = new ArrayList();
      String contactSrch = " select eml,mbl,ofc, byr.get_nm(emp_idn) emp from nme_cntct_v where nme_id= ? ";
      ArrayList ary = new ArrayList();
      ary.add(String.valueOf(ptyId));
    ArrayList outLst = db.execSqlLst("dmdSrch",contactSrch, ary);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
      try {
          while (rs.next()) {
             HashMap contDtl = new HashMap();
             contDtl.put("eml", util.nvl(rs.getString("eml")));
             contDtl.put("mbl", util.nvl(rs.getString("mbl")));
             contDtl.put("ofc", util.nvl(rs.getString("ofc")));
             contDtl.put("emp", util.nvl(rs.getString("emp")));
             contactLst.add(contDtl);
          }
          rs.close();
          pst.close();
      } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
      }
      
      
      return contactLst;
  }
    public ArrayList getWebRequest(HttpServletRequest req , HttpServletResponse res , int nmeIdn){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    ArrayList webReqList = new ArrayList();
    HashMap webList = new HashMap();
    ArrayList ary = new ArrayList();
    ary.add(String.valueOf(nmeIdn));
    String webReq = "select idn, to_char(dte, 'dd-Mon HH24:mi') dte,(nvl(flg3,'NA')) flg3 from mjan where typ='WR' and nmerel_idn=? and trunc(dte)>= trunc(sysdate)-7 order by idn desc ";
      ArrayList outLst = db.execSqlLst("webReq", webReq, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while(rs.next()){
    webList = new HashMap();
    webList.put("idn",util.nvl(rs.getString("idn")));
    webList.put("dte",util.nvl(rs.getString("dte")));//shiv
    webList.put("flg3",util.nvl(rs.getString("flg3")));//shiv
    webReqList.add(webList);
    }

    rs.close();
        pst.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    return webReqList;
    }
    
    public ArrayList getHrRequest(HttpServletRequest req , HttpServletResponse res , int nmeIdn){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    ArrayList webReqList = new ArrayList();
    HashMap webList = new HashMap();
    ArrayList ary = new ArrayList();
    ary.add(String.valueOf(nmeIdn));
    String webReq = "select idn, to_char(dte, 'dd-Mon HH24:mi') dte,(nvl(flg3,'NA')) flg3 from mjan where typ='HR' and nmerel_idn=? and trunc(dte)>= trunc(sysdate)-10 order by idn desc ";
      ArrayList outLst = db.execSqlLst("webReq", webReq, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while(rs.next()){
    webList = new HashMap();
    webList.put("idn",util.nvl(rs.getString("idn")));
    webList.put("dte",util.nvl(rs.getString("dte")));//shiv
    webList.put("flg3",util.nvl(rs.getString("flg3")));//shiv
    webReqList.add(webList);
    }

    rs.close();
        pst.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    return webReqList;
    }
    public ArrayList getLstMemo(HttpServletRequest req , HttpServletResponse res , int nmerelIdn){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        int srno=0;
    ArrayList webReqList = new ArrayList();
    HashMap webList = new HashMap();
    ArrayList ary = new ArrayList();
    ary.add(String.valueOf(nmerelIdn));
    String webReq = "    select a.idn ,a.typ, to_char(a.dte, 'DD-MON HH24:mi') dte,sum(b.qty) qty,to_char(sum(b.cts),'999999990.09') cts\n" + 
    "    from mjan a,jandtl b\n" + 
    "    where \n" + 
    "    a.idn=b.idn and\n" + 
    "    a.typ not in ('NG','OG')\n" + 
    "    and a.nmerel_idn=? and trunc(b.dte)>= trunc(sysdate)-10\n" + 
    "    group by a.idn,a.typ,a.dte\n" + 
    "    order by a.idn desc";
      ArrayList outLst = db.execSqlLst("webReq", webReq, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while(rs.next()){
        srno = srno+1;
    webList = new HashMap();
    webList.put("idn",util.nvl(rs.getString("idn")));
    webList.put("dte",util.nvl(rs.getString("dte")));//shiv
    webList.put("qty",util.nvl(rs.getString("qty")));//shiv
    webList.put("cts",util.nvl(rs.getString("cts")));//shiv
    webList.put("typ",util.nvl(rs.getString("typ")));//shiv
    webReqList.add(webList);
    }

    rs.close();
        pst.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    return webReqList;
    }
    public ArrayList getLstMemoNG(HttpServletRequest req , HttpServletResponse res , int nmerelIdn){
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            int srno=0;
        ArrayList webReqList = new ArrayList();
        HashMap webList = new HashMap();
        ArrayList ary = new ArrayList();
        ary.add(String.valueOf(nmerelIdn));
        String webReq = "    select a.idn , to_char(a.dte, 'DD-MON HH24:mi') dte,sum(b.qty) qty,to_char(sum(b.cts),'999999990.09') cts\n" + 
        "    from mjan a,jandtl b\n" + 
        "    where \n" + 
        "    a.idn=b.idn and\n" + 
        "    a.typ in ('NG')\n" + 
        "    and a.nmerel_idn=? \n" + 
        "    group by a.idn,a.dte\n" + 
        "    order by a.idn desc";
          ArrayList outLst = db.execSqlLst("webReq", webReq, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        try {
        while(rs.next()){
            srno = srno+1;
        webList = new HashMap();
        webList.put("idn",util.nvl(rs.getString("idn")));
        webList.put("dte",util.nvl(rs.getString("dte")));//shiv
        webList.put("qty",util.nvl(rs.getString("qty")));//shiv
        webList.put("cts",util.nvl(rs.getString("cts")));//shiv
        webReqList.add(webList);
            if(srno==5)
            break;
        }

        rs.close();
        pst.close();
        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        }
        return webReqList;
        }
    public  ArrayList getSrchType(HttpServletRequest req , HttpServletResponse res,SearchForm srchForm,String dfltSet){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String isMix = util.nvl(info.getIsMix(),"NR");
        ArrayList memoList = (session.getAttribute("srchTypList_"+isMix) == null)?new ArrayList():(ArrayList)session.getAttribute("srchTypList_"+isMix);
        ArrayList memoListDtl=new ArrayList();
        try {
            if(memoList.size() == 0) {
            String memoTypSql = "select chr_fr,nvl(chr_to,'Y') chr_to, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                            + "b.mdl = 'JFLEX' and b.nme_rule = 'SRCH_TYP_"+isMix+"' and a.til_dte is null order by a.srt_fr ";
            ResultSet rs = db.execSql("memoType", memoTypSql , new ArrayList());
            while (rs.next()) {
             memoListDtl=new ArrayList();
             String chr_fr=rs.getString("chr_fr").trim();
             memoListDtl.add(rs.getString("dsc"));
             memoListDtl.add(chr_fr);
             memoListDtl.add(util.nvl((String)rs.getString("chr_to").trim(),"Y"));
             memoList.add(memoListDtl);
            }
            rs.close();
            session.setAttribute("srchTypList_"+isMix, memoList);
            }
            if(dfltSet.equals("Y")){
                for(int i=0;i<memoList.size();i++){
                memoListDtl=new ArrayList();
                memoListDtl=(ArrayList)memoList.get(i);
                String srchstt=util.nvl((String)memoListDtl.get(1));
                String chk=util.nvl((String)memoListDtl.get(2));
                if(chk.equals("Y"))
                srchForm.setValue("SRCHSTT_"+srchstt, srchstt);
                }
            }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
           
        return memoList;
    }
    public  ArrayList getSrchType(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList memoList = (session.getAttribute("srchTypList") == null)?new ArrayList():(ArrayList)session.getAttribute("srchTypList");
        
        try {
            if(memoList.size() == 0) {
            HashMap memoTypMap = new HashMap();
            String memoTypSql = "select distinct dsc , typ , memo_typ , srt from srch_typ where flg='A'  order by srt";
            ResultSet rs = db.execSql("memoType", memoTypSql , new ArrayList());
            while (rs.next()) {
             MemoType memoTyp = new MemoType();
             memoTyp.setDsc(rs.getString("dsc"));
             memoTyp.setSrch_typ(rs.getString("typ"));
            
             memoList.add(memoTyp);
             memoTypMap.put(rs.getString("typ"),rs.getString("memo_typ"));
            }
            rs.close();
            session.setAttribute("srchTypList", memoList);
            session.setAttribute("memoTypMap", memoTypMap);
            }} catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
           
        return memoList;
    }

    public  MemoType getMemoTypeDtl(HttpServletRequest req , HttpServletResponse res ,String typ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        MemoType memoTyp = new MemoType();
        String memoTypSql = "select distinct dsc , typ , srch_typ , dtl_stt , hdr_stt, srt from memo_typ where flg=? and memo_flg=? and typ=? order by srt";
        ArrayList ary = new ArrayList();
        ary.add("A");
        ary.add("Y");
        ary.add(typ);
      ArrayList outLst = db.execSqlLst("memoType", memoTypSql , ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
             memoTyp.setDsc(util.nvl(rs.getString("dsc")));
             memoTyp.setMemo_typ(util.nvl(rs.getString("typ")));
             memoTyp.setSrch_typ(util.nvl(rs.getString("srch_typ")));
             memoTyp.setDtl_stt(util.nvl(rs.getString("dtl_stt")));
             memoTyp.setHdr_stt(util.nvl(rs.getString("hdr_stt")));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
       return memoTyp;
    }
    public ArrayList getMemoType(HttpServletRequest req, HttpServletResponse res){
         HttpSession session = req.getSession(false);
         InfoMgr info = (InfoMgr)session.getAttribute("info");
         DBUtil util = new DBUtil();
         DBMgr db = new DBMgr(); 
         db.setCon(info.getCon());
         util.setDb(db);
         util.setInfo(info);
         db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
         util.setLogApplNm(info.getLoApplNm());
         ArrayList memoList=new ArrayList();
         try {
         if(info.getIsMix().equals("") || info.getIsMix().equals("SMX")){
         memoList = (session.getAttribute("memoTypeList") == null)?new ArrayList():(ArrayList)session.getAttribute("memoTypeList");
             if(memoList.size() == 0) {
                 String memoTypSql = "select distinct dsc ,typ , srt from memo_typ where flg='A'  order by srt";
               ArrayList outLst = db.execSqlLst("memoType", memoTypSql , new ArrayList());
               PreparedStatement pst = (PreparedStatement)outLst.get(0);
               ResultSet rs = (ResultSet)outLst.get(1);
                 while(rs.next()){
                     MemoType memoTyp = new MemoType();
                     memoTyp.setDsc(rs.getString("dsc"));
                     memoTyp.setMemo_typ(rs.getString("typ"));
                     memoList.add(memoTyp);
                 }
                 rs.close();
                 pst.close();
                 session.setAttribute("memoTypeList", memoList);
             }
         }else if(info.getIsMix().equals("RGH")){
             memoList = (session.getAttribute("memoTypeListRGH") == null)?new ArrayList():(ArrayList)session.getAttribute("memoTypeListRGH");
                 if(memoList.size() == 0) {
                     String memoTypSql = "select distinct dsc ,typ , srt from memo_typ where flg='A' and typ like 'RG%'  order by srt";
                   ArrayList outLst = db.execSqlLst("memoType", memoTypSql , new ArrayList());
                   PreparedStatement pst = (PreparedStatement)outLst.get(0);
                   ResultSet rs = (ResultSet)outLst.get(1);
                     while(rs.next()){
                         MemoType memoTyp = new MemoType();
                         memoTyp.setDsc(rs.getString("dsc"));
                         memoTyp.setMemo_typ(rs.getString("typ"));
                         memoList.add(memoTyp);
                     }
                     rs.close();
                     pst.close();
                     session.setAttribute("memoTypeListRGH", memoList);
                 }
         }else{
             memoList = (session.getAttribute("memoTypeListMix") == null)?new ArrayList():(ArrayList)session.getAttribute("memoTypeListMix");
                 if(memoList.size() == 0) {
                     String memoTypSql = "select distinct dsc ,typ , srt from memo_typ where flg='A' and dtl_stt not in('AP')  order by srt";
                   ArrayList outLst = db.execSqlLst("memoType", memoTypSql , new ArrayList());
                   PreparedStatement pst = (PreparedStatement)outLst.get(0);
                   ResultSet rs = (ResultSet)outLst.get(1);
                     while(rs.next()){
                         MemoType memoTyp = new MemoType();
                         memoTyp.setDsc(rs.getString("dsc"));
                         memoTyp.setMemo_typ(rs.getString("typ"));
                         memoList.add(memoTyp);
                     }
                     rs.close();
                     pst.close();
                     session.setAttribute("memoTypeListMix", memoList);
                 }
         }
        } catch (SQLException sqle) {
             // TODO: Add catch code
             sqle.printStackTrace();
         }
         return memoList;
     }

    public ArrayList getHitMemoType(HttpServletRequest req, HttpServletResponse res){
         HttpSession session = req.getSession(false);
         InfoMgr info = (InfoMgr)session.getAttribute("info");
         DBUtil util = new DBUtil();
         DBMgr db = new DBMgr(); 
         db.setCon(info.getCon());
         util.setDb(db);
         util.setInfo(info);
         db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
         util.setLogApplNm(info.getLoApplNm());
         ArrayList memoList=new ArrayList();
         try {
         memoList = (session.getAttribute("HitmemoTypeList") == null)?new ArrayList():(ArrayList)session.getAttribute("HitmemoTypeList");
             if(memoList.size() == 0) {
                 String memoTypSql = "select distinct dsc ,typ , srt from memo_typ where flg='A'  order by srt";
               ArrayList outLst = db.execSqlLst("memoType", memoTypSql , new ArrayList());
               PreparedStatement pst = (PreparedStatement)outLst.get(0);
               ResultSet rs = (ResultSet)outLst.get(1);
                 while(rs.next()){
                     MemoType memoTyp = new MemoType();
                     memoTyp.setDsc(rs.getString("dsc"));
                     memoTyp.setMemo_typ(rs.getString("typ"));
                     memoList.add(memoTyp);
                 }
                 rs.close();
                 pst.close();
                 MemoType memoTyp = new MemoType();
                 memoTyp.setDsc("Consignment");
                 memoTyp.setMemo_typ("CS");
                 memoList.add(memoTyp);
                 session.setAttribute("HitmemoTypeList", memoList);
             }
        } catch (SQLException sqle) {
             // TODO: Add catch code
             sqle.printStackTrace();
         }
         return memoList;
     }
    public ArrayList getTerms(HttpServletRequest req, HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList trmsLst = (ArrayList)session.getAttribute("trmsList");
        if(trmsLst==null){
            trmsLst = new ArrayList();
          
          String trmsSql="select  dtls , nmerel_idn from  nme_rel_v ";
            try {
          ArrayList outLst = db.execSqlLst("trms", trmsSql, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                TrmsDao trms = new TrmsDao();
                trms.setRelId(rs.getInt(2));
                trms.setTrmDtl(rs.getString(1));
                trmsLst.add(trms);
            }
            rs.close();
                pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        }
        session.setAttribute("trmsList", trmsLst);
            return trmsLst;
    }
    public ArrayList getMemoXlGrp(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String xlGrp = "select distinct mdl from rep_prp where mdl like '%_MEMOXL' or mdl='MEMO_VW'";
      ArrayList outLst = db.execSqlLst("xlGrp",xlGrp,new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        ArrayList repPrpLst = new ArrayList();
        try {
            while (rs.next()) {
                RepPrp repPrp = new RepPrp();
               
                repPrp.setMdl(rs.getString("mdl").trim());
                
                repPrpLst.add(repPrp);
            }
            rs.close();
            pst.close();

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return repPrpLst;
    }
    
    public ArrayList getMemoXlGrpFrm(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String xlGrp = "select distinct mdl from rep_prp where mdl like '%_MEMOXL' or mdl='MEMO_VW_XL'";
      ArrayList outLst = db.execSqlLst("xlGrp",xlGrp,new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        ArrayList repPrpLst = new ArrayList();
        try {
            while (rs.next()) {
                RepPrp repPrp = new RepPrp();
               
                repPrp.setMdl(rs.getString("mdl").trim());
                
                repPrpLst.add(repPrp);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return repPrpLst;
    }
    public ArrayList getPRPUPViewGrp(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String xlGrp = "select prp from rep_prp where mdl ='PRP_UPD_VIEW' and flg='Y' order by rnk";
      ArrayList outLst = db.execSqlLst("xlGrp",xlGrp,new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        ArrayList repPrpLst = new ArrayList();
        try {
            while (rs.next()) {
               repPrpLst.add(rs.getString(1));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return repPrpLst;
    }
    
    public ArrayList getPRPUPGrp(HttpServletRequest req ,HttpServletResponse res, String mdl){
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
        String xlGrp = "select prp from rep_prp where mdl =? and flg='Y' order by rnk";
        ary.add(mdl);
      ArrayList outLst = db.execSqlLst("xlGrp",xlGrp,ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        ArrayList repPrpLst = new ArrayList();
        try {
            while (rs.next()) {
               repPrpLst.add(rs.getString(1));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return repPrpLst;
    }
    public ArrayList getPRCPRPMdl(HttpServletRequest req, HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList prpPrpMdl = (ArrayList)session.getAttribute("prpPrpMdl");
        if(prpPrpMdl==null){
        String xlGrp = "select prp from rep_prp where mdl ='PRC_PRP' and flg='Y' order by rnk";
          ArrayList outLst = db.execSqlLst("xlGrp",xlGrp,new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        prpPrpMdl = new ArrayList();
        try {
            while (rs.next()) {
               prpPrpMdl.add(rs.getString(1));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        }
        session.setAttribute("prpPrpMdl",prpPrpMdl);
        return prpPrpMdl;
    }
    public ArrayList getDmd(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String getDmd = "select dmd_id  , dsc from mdmd where todte  is null";
      ArrayList outLst = db.execSqlLst("get mdmd",getDmd,new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        ArrayList dmdPrpLst = new ArrayList();
        try {
            while (rs.next()) {
                ArrayList dmdLst = new ArrayList();
                dmdLst.add(rs.getString("dmd_id"));
                dmdLst.add(util.nvl(rs.getString("dsc")));
                dmdPrpLst.add(dmdLst);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      return dmdPrpLst;
    }
    
        
    public HashMap bidPkt(HttpServletRequest req , HttpServletResponse res , int rlnId ){
         HttpSession session = req.getSession(false);
         InfoMgr info = (InfoMgr)session.getAttribute("info");
         DBUtil util = new DBUtil();
         DBMgr db = new DBMgr(); 
         db.setCon(info.getCon());
         util.setDb(db);
         util.setInfo(info);
         db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
         util.setLogApplNm(info.getLoApplNm());
        HashMap bidinfo = new HashMap();
        HashMap bidmap = new HashMap();
        ArrayList ary  = new ArrayList();
       
       
        String getOff= 
       " select a.mstk_idn,a.rmk,a.lmt_rte, b.rap_rte,  round(a.ofr_rte) ofr_rte, to_char((a.ofr_rte*100/greatest(b.rap_rte,1)) - 100, '90.00') ofr_dis, "+
       " to_char(a.to_dt, 'dd-mm-rrrr') to_dt , to_char(a.frm_dt, 'dd-mm-rrrr') frm_dt "+
       " , Decode(a.flg, null, decode(least(trunc(sysdate), trunc(a.frm_dt)),trunc(sysdate), 'P', 'O'), a.flg) flg "+
       " from web_bid_wl a, mstk b , gt_srch_rslt c where a.mstk_idn = b.idn  "+
       " and a.typ in ('PP','LB','BID','KS','KO')  and trunc(nvl(a.to_dt, sysdate)) >= trunc(sysdate) "+
       " and a.stt='A' and a.mstk_idn = c.stk_idn and b.idn = c.stk_idn "+
       " and b.stt in ('MKAV' ,'MKIS','MKTL','MKLB','MKPP','SHIS','MKCS','MKOS','MKOS_IS')"+
       " and b.pkt_ty = 'NR'";
         ary = new ArrayList();
        if(rlnId!=0){
           getOff = getOff+ " and a.rel_idn = ? ";
          ary.add(Integer.toString(info.getRlnId()));
        }
       ArrayList outLst = db.execSqlLst("GET OFF",getOff,ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                bidinfo = new HashMap();
                String flg = util.nvl(rs.getString("flg"));
                String stkIdn = util.nvl(rs.getString("mstk_idn"));
                String bidDt = util.nvl(rs.getString("to_dt"));
                String bidRte = util.nvl(rs.getString("ofr_rte"));
                String frm_dt = util.nvl(rs.getString("frm_dt"));
                bidinfo.put("toDte",bidDt);
                bidinfo.put("rte",bidRte);
                bidinfo.put("dis",util.nvl(rs.getString("ofr_dis")));
                bidinfo.put("rmk",util.nvl(rs.getString("rmk")));
                bidinfo.put("frmDte",util.nvl(rs.getString("frm_dt")));
                bidinfo.put("lmtRte",util.nvl(rs.getString("lmt_rte")));
               
                bidmap.put(stkIdn, bidinfo);
               

            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      
      return  bidmap;
        
     }
    public HashMap GetTotal(HttpServletRequest req ,HttpServletResponse res, String flg){
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
        ary.add(flg);
      ArrayList outLst = db.execSqlLst("getTotal", gtTotal, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if (rs.next()) {
             gtTotalMap.put("qty", rs.getString("qty"));
             gtTotalMap.put("cts", rs.getString("cts"));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return gtTotalMap ;
    }
    public void IsXRT(HttpServletRequest req,HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String isXrt="0";
        String  xrtSql = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                                 + "b.mdl = 'JFLEX' and b.nme_rule = 'XRT' and a.til_dte is null order by a.srt_fr ";

        try {
          ArrayList outLst = db.execSqlLst("memoPrint", xrtSql, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
            isXrt = rs.getString("chr_fr");
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
       info.setIsXrt(isXrt);
    }
    public HashMap getMatchpairType(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());

    HashMap matchpairTypMap = new HashMap();
    String matchpairTypSql = "select rule_idn, nme_rule from mrule where mdl='MATCHPAIR' and vld_dte is null order by 1";
      ArrayList outLst = db.execSqlLst("matchpairType", matchpairTypSql , new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while (rs.next()) {
    matchpairTypMap.put(rs.getString("rule_idn"),rs.getString("nme_rule"));
    }
        rs.close();
        pst.close();
    } catch (SQLException sqle) {

    sqle.printStackTrace();
    }
    session.setAttribute("matchpairTypeMap", matchpairTypMap);

    return matchpairTypMap;
    }
    
  public String TotalMemoVal(HttpServletRequest req,int nme_idn,int nmerel_idn,String cnt,String typ){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    String ttlMemoValSql = "\n" + 
    " select to_char(trunc(sum(val),2),'99999990') ttlVal from memo_pndg_v where nme_idn=?";
      ArrayList ary = new ArrayList();
      ary.add(String.valueOf(nme_idn));
    if(cnt.equals("kj")){
        ttlMemoValSql="with appVlu as (\n" + 
        "select to_char(trunc(sum(val),2),'99999999990') vlu from memo_pndg_v where nme_idn=? ";
        if(typ.equals("AP")){
        ttlMemoValSql+=" and typ like '%AP'\n";
        }
        ttlMemoValSql+="), salVlu as (\n" + 
        "select to_char(sum(b.cts*nvl(b.fnl_usd, b.quot))  ,'9999999999990.00') vlu\n" + 
        "from msal a , Sal_Pkt_Dtl_V b  , mstk c,mnme d \n" + 
        "where a.idn = b.idn  and b.mstk_idn = c.idn and a.nme_idn=d.nme_idn  \n" + 
        "and a.stt='IS'  and b.stt = 'SL' and a.typ='SL'  and a.flg1='CNF' and c.stt = 'MKSL' and a.nme_idn=? \n" + 
        "), osVlu as (\n" + 
        "select GET_OS_AMT(nme_idn,cur) vlu  from nmerel where nmerel_idn = ? and nme_idn=?\n" + 
        ")\n" + 
        "select to_char(nvl(ap.vlu,0)+nvl(sl.vlu,0)+nvl(os.vlu,0),'99999999999999999990') ttlVal\n" + 
        "from \n" + 
        "appVlu ap,salVlu sl,osVlu os";
        ary = new ArrayList();
        ary.add(String.valueOf(nme_idn));
        ary.add(String.valueOf(nme_idn));
        ary.add(String.valueOf(nmerel_idn));
        ary.add(String.valueOf(nme_idn));
    }
    ArrayList outLst = db.execSqlLst("ttlMemoVal", ttlMemoValSql, ary);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
    String ttlMemoVal = "0";
      try {
          while (rs.next()) {
              ttlMemoVal = util.nvl(rs.getString("ttlVal"),"0").trim();
          }
          rs.close();
          pst.close();
      } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
      }
      
      return ttlMemoVal;
  }
    public void MailExcelmass(HSSFWorkbook hwb,String fileNme,HttpServletRequest req, HttpServletResponse res )throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    String isPdf = util.nvl((String)req.getAttribute("PDF"));
    int byrIdn = info.getByrId();
    ArrayList eid=new ArrayList();
    String salEml = "";
    
    info.setNmemaillist(eid);
    ArrayList attAttachFilNme = new ArrayList();
    ArrayList attAttachTyp = new ArrayList();
    ArrayList attAttachFile = new ArrayList();
    HashMap dbmsInfo = info.getDmbsInfoLst();
    fileNme = util.getToDteTime()+"_"+fileNme;
    String filePath = session.getServletContext().getRealPath("/") + fileNme;
   

    FileOutputStream fileOutputStream = new FileOutputStream(filePath);
    hwb.write(fileOutputStream);
    attAttachFilNme.add(fileNme);
    attAttachTyp.add("application/vnd.ms-excel");
    attAttachFile.add(filePath);
    if(isPdf.equals("Y")){
        String pdfUrl = (String)req.getAttribute("PDFURL");
        if(pdfUrl!=null){
        String pdfFileNme = fileNme.replace(".xls",".pdf");
        String pdfFilePath = filePath.replace(".xls",".pdf");
        GenericInterface genInt = new GenericImpl();
        genInt.CeatePdf(req, res,pdfUrl,pdfFilePath);
        attAttachFilNme.add(pdfFileNme);
        attAttachTyp.add("application/pdf");
        attAttachFile.add(pdfFilePath);
        }
    }

    session.setAttribute("attAttachFilNme", attAttachFilNme);
    session.setAttribute("attAttachTyp", attAttachTyp);
    session.setAttribute("attAttachFile", attAttachFile);
    try {
    // mail.send("");
    } catch (Exception e) {
    // TODO: Add catch code
    e.printStackTrace();
    }

    fileOutputStream.close();
    }
    
    public ArrayList getBankList(HttpServletRequest req, HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList bankList = (session.getAttribute("bankList") == null)?new ArrayList():(ArrayList)session.getAttribute("bankList");
        
        try {
            if(bankList.size() == 0) {
                HashMap dbmsInfo = info.getDmbsInfoLst();
                String types =util.nvl((String)dbmsInfo.get("BANK"));
                types=types.replaceAll(",", "','");
                String conQ =" and typ in('GROUP','BANK') ";
                if(!types.equals(""))
                conQ =" and typ in('"+types+"') ";
                String bankSql = "select nme_idn, byr.get_nm(nvl(nme_idn,0)) fnme  from mnme  where 1 = 1 and vld_dte is null "+conQ;
              ArrayList outLst = db.execSqlLst("bank Sql", bankSql, new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                    MAddr addr = new MAddr();
                    addr.setIdn(rs.getString("nme_idn"));
                    addr.setAddr(rs.getString("fnme"));
                    bankList.add(addr);
                }
                rs.close();
                pst.close();
                session.setAttribute("bankList", bankList);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return bankList;
    }

    public ArrayList getcourierList(HttpServletRequest req, HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList courierList = (session.getAttribute("courierList") == null)?new ArrayList():(ArrayList)session.getAttribute("courierList");
        
        try {
            if(courierList.size() == 0) {
                String courierQ="select chr_fr,dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and\n" + 
                "             b.mdl = 'JFLEX' and b.nme_rule = 'COURIER' and a.til_dte is null order by a.srt_fr";
              ArrayList outLst = db.execSqlLst("courierQ", courierQ, new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                    MAddr addr = new MAddr();
                    addr.setIdn(util.nvl(rs.getString("chr_fr")));
                    addr.setAddr(util.nvl(rs.getString("dsc")));
                    courierList.add(addr);
                }
                rs.close();
                pst.close();
                session.setAttribute("courierList", courierList);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return courierList;
    }
    public ArrayList getgroupList(HttpServletRequest req, HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList groupList = (session.getAttribute("groupList") == null)?new ArrayList():(ArrayList)session.getAttribute("groupList");
        
        try {
            if(groupList.size() == 0) {
                String companyQ="select nme_idn,fnme from  CO_BRANCH_V";
              ArrayList outLst = db.execSqlLst("Group Sql", companyQ, new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                    MAddr addr = new MAddr();
                    addr.setIdn(rs.getString("nme_idn"));
                    addr.setAddr(rs.getString("fnme"));
                    groupList.add(addr);
                }
                rs.close();
                pst.close();
                session.setAttribute("groupList", groupList);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return groupList;
    }
    public SearchQuery() {
        super();
    }
}
