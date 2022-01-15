
package ft.com.Repair;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;


import ft.com.dao.MNme;

import ft.com.dao.Mprc;
import ft.com.dao.TrmsDao;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RepairingIssueImpl implements RepairingIssueInterface {
    public ArrayList FecthResult(HttpServletRequest req, HttpServletResponse res, HashMap params,RepairingIssueForm form){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = null;
        ArrayList pktDtlList = new ArrayList();
        String strSql = " ";
        String vnm = util.nvl((String)params.get("vnm"));
        String fact = util.nvl((String)params.get("fact"));
        String stt = util.nvl((String)params.get("stt"));
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        String repView ="";
        ary = new ArrayList();
        
        if(vnm.length() > 1)
            strSql += " and  ( vnm in ("+vnm+") or tfl3 in ("+vnm+")) ";
        
        if(params.containsKey("RepView")){
            repView =util.nvl((String)params.get("RepView"));  
            if(!repView.equals("")){
            stt="REP_AV";
            }
        }
        
        if(!stt.equals("")){
            strSql += " and  stt = ?  ";
            ary.add(stt);
        }else if(stt.equals("ALL")){
        }else{
            strSql += " and stt in ('REP_AV','AS_FN','LB_RT','AS_PRC','MKAV','LB_FN','AS_AV','LB_AV','MX_AV') ";
        }
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk ) " + 
        " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , sk1 , tfl3  "+
        "     from mstk b "+
        " where cts > 0 "+strSql ;
        
        
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        String empNme = "";
        if(!fact.equals("NA"))
         empNme = empIdn(req,fact);
        ary = new ArrayList();
        ary.add(empNme);
        ct = db.execCall(" Srch Prp ", "ISS_RTN_PKG.UPD_REP_ISS(pNme =>?)", ary);   
      
        
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add("REP_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        pktDtlList = SearchResult(req ,res, vnm,form);
        
        return pktDtlList;
    }
    public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res, String vnmLst ,RepairingIssueForm form){
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
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("REPAIR_ISSUE");
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
        ary.add("Z");

        ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                String stk_idn= util.nvl(rs.getString("stk_idn"));
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
                pktPrpMap.put("stk_idn",stk_idn);
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
                    if(!dflt_LabLoc.equals(""))
                    form.setValue("LOC_"+stk_idn,dflt_LabLoc);
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
    
    public String empIdn(HttpServletRequest req,String nme_idn){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String empIdn = "";
        String empSql = "select nme_idn, nme from nme_v where nme_idn= ?";
        ArrayList ary = new ArrayList();
        ary.add(nme_idn);

        ArrayList outLst = db.execSqlLst("empSql", empSql,ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if (rs.next()) {
                empIdn = util.nvl(rs.getString("nme"));
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }    
                
        return empIdn;
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
        String empSql = "select nme_idn, nme from nme_v where typ in ('MFG') order by nme";

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
    public HashMap GetTotal(HttpServletRequest req ,HttpServletResponse res){
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
    
    public int issuePkt(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList ary =null;
        int memoId =0;
        int ct = db.execUpd("delete gt","Delete from gt_srch_rslt ", new ArrayList());
        // if(ct > 0){
        try {
            ary = new ArrayList();
            ary.add(String.valueOf(info.getByrId()));
            ary.add(String.valueOf(info.getRlnId()));
            ary.add("IS");
            ary.add("REP");
            ary.add("NN");

            ArrayList out = new ArrayList();
            out.add("I");
            CallableStatement cst = null;
            cst = db.execCall("MKE_HDR ", "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ?  , pTyp => ? , pflg => ? , pMemoIdn => ?)",
            ary, out);

            memoId = cst.getInt(8);
            ary = new ArrayList();
            ary.add(Integer.toString(memoId));
            ary.add("IS");
          cst.close();
          cst=null;
            int ct1 = db.execCall("pop Memo from gt", "MEMO_PKG.POP_MEMO_FRM_GT(?,?)",
                            ary);
            ary = new ArrayList();
            ary.add(Integer.toString(memoId));
            int ct2 = db.execCall("jan_qty", "jan_qty(?)", ary);
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return memoId;
    }
    public ArrayList getTerm(HttpServletRequest req ,HttpServletResponse res, String ptyId){
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
        String favSrch = " select  dtls , nmerel_idn from  nme_rel_v where nme_idn=?  ";
        ArrayList ary = new ArrayList();
        ary.add(ptyId);

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
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        session.setAttribute("trmsLst", trmsLst);
        return trmsLst;
    }
    
    public RepairingIssueImpl() {
        super();
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
                rs1.close(); pst.close();
                session.setAttribute("repViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
    
    public ArrayList getPrc(HttpServletRequest req ,HttpServletResponse res){
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
        HashMap prcSttMap = new HashMap();
        ArrayList prcList=new ArrayList();
        
          String grp = util.nvl(req.getParameter("grp"));
          if(grp.equals(""))
              grp="'REP'";
          String prcSql = "select idn, prc , in_stt,is_stt,grp from mprc where  is_stt <> 'NA' and stt = ? " ;
              if(!grp.equals("")){
              prcSql+= " and grp in ("+grp+") ";
              }
              prcSql+=" order by srt";
          ary = new ArrayList();
          ary.add("A");

        ArrayList outLst = db.execSqlLst("prcSql", prcSql, ary);
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
              rs.close(); pst.close();
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
          
    
        return prcList;
    }
    
    public void IssueEdit(HttpServletRequest req,HttpServletResponse res,String issStt){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
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

        ArrayList outLst = db.execSqlLst("labIssue", labIssueEdit, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
          try {
              while (rs.next()) {
               prpList.add(rs.getString("mprp"));
              }
              rs.close(); pst.close();
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
          session.setAttribute("RepairIssueEditPRP", prpList);
    }
}
