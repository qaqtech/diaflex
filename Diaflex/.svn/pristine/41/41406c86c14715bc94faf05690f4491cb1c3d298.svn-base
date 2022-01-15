package ft.com.lab;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.GtPktDtl;
import ft.com.dao.MNme;
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

public class LabComparisonImpl implements LabComparisonInterface {
    public ArrayList ReporTypList(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String    memoPrntOptn = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                                 + "b.mdl = 'MKTG' and b.nme_rule = 'STATUS' and a.til_dte is null order by a.dsc ";
        ArrayList sttList = new ArrayList();

        ArrayList outLst = db.execSqlLst("memoPrint", memoPrntOptn, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                UIForms memoOpn = new UIForms();
                memoOpn.setFORM_NME(rs.getString("chr_fr"));
                memoOpn.setFORM_TTL(rs.getString("dsc"));
                sttList.add(memoOpn);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return sttList;
    }
    public LabComparisonImpl() {
        super();
    }
    public int insertGt(HttpServletRequest req ,HttpServletResponse res, HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String vnm = util.nvl((String)params.get("vnm"));
        String seq = util.nvl((String)params.get("seq"));
        int gtCount=0;

        ArrayList outLst = db.execSqlLst("gtCount", "select count(*) from gt_srch_rslt", new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if (rs.next())
                gtCount = rs.getInt(1);
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        ArrayList ary = new ArrayList();
        int ct =0;
        if(gtCount==0){
            ary = new ArrayList();
        String delQ = " Delete from gt_srch_rslt ";
         ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn , vnm , pkt_dte, stt , flg , qty , cts , rmk  , sk1) " + 
        " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , tfl3 , sk1 "+
        "     from  mstk b "+
        " where 1=1 and b.stt='LB_RT' ";
         if(!seq.equals("")){
            srchRefQ = 
                    " Insert into gt_srch_rslt (pkt_ty, stk_idn , vnm , pkt_dte, stt , flg , qty , cts , rmk , sk1 ) " + 
                    " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , tfl3 , sk1  "+
                    "  from lab_inward_ora a , mstk b  "+
                    " where a.idn = b.idn and b.stt='LB_RT'  and a.load_seq = ? ";
            ary.add(seq);
        }
        
        if(!vnm.equals("")){
                 vnm = util.getVnm(vnm);
                srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))" ;
              
          }
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("LBCOM_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        }else{
            if(!vnm.equals("") || !seq.equals("")){
            ary = new ArrayList();
           String sql = " select  *  from  mstk b   where  c.stk_idn = b.idn ";
            if(!vnm.equals("")){
                vnm = util.getVnm(vnm);
                sql=sql+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))" ;
            }
            if(!seq.equals("")){
             sql = " select  *  from lab_inward_ora a , mstk b  "+
                " where a.idn = b.idn and a.load_seq = ? and c.stk_idn = b.idn and a.idn = c.stk_idn ";
                ary.add(seq);
            }
           String deleteSql = "delete from gt_srch_rslt c where not exists ("+sql+")";
            ct=db.execUpd("delete", deleteSql, ary);
        }}
        
        return ct;
    }
//    public ArrayList LBCOMGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("LBCOMGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp , flg  from rep_prp where mdl = 'LBCOM_SRCH' and flg <> 'N' order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("LBCOMGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
    
    public HashMap FetchResult(HttpServletRequest req ,HttpServletResponse res,String vnmLst, String reportTyp){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
       if(!vnmLst.equals(""))
           vnmLst = util.getVnm(vnmLst);
       HashMap dbinfo = info.getDmbsInfoLst();
        String colval = (String)dbinfo.get("COL");
        String clrval = (String)dbinfo.get("CLR");
        String cutval = util.nvl((String)dbinfo.get("CUT"));
       ArrayList ary = new ArrayList();
       ArrayList vwPrpLst = lbComView(req,res);
       ary.add(reportTyp);
        int ct = db.execCall("labRtn", "ISS_RTN_PKG.LAB_RTN_ISS_TYP(pTyp => ?)", ary);
       
       HashMap pktDtlList = new HashMap();
       String reportQuery = " select c.nme emp , a.stk_idn , a.vnm ,  max(decode(b.mprp, 'COL', b.iss_val, null)) iss_col , "+ 
                            " max(decode(b.mprp, '"+clrval+"', b.iss_val, null)) iss_clr , a.sk1 ,  max(decode(b.mprp, '"+cutval+"', b.iss_val, null)) iss_cut , a.rmk  ";
       String grp="";
        for (int i = 0; i < vwPrpLst.size(); i++) {
            String fld = "prp_";
            int j = i + 1;
            if (j < 10)
                fld += "00" + j;
            else if (j < 100)
                fld += "0" + j;
            else if (j > 100)
                fld += j;
           reportQuery += ", " + fld;
             grp += ", " + fld;
        }
        reportQuery = reportQuery +" from gt_srch_rslt a, iss_rtn_prp_comp_v b, nme_v c " + 
       " where a.stk_idn = b.idn and b.emp_id = c.nme_idn and a.srch_id = b.iss_id " + 
       " and a.flg = 'Z' and b.mprp in ('"+colval+"','"+clrval+"','"+cutval+"') " + 
       " group by c.nme, a.stk_idn, a.vnm   "+grp+" , a.sk1 , a.cts , a.rmk order by a.sk1 , a.cts ";
        

        ArrayList outLst = db.execSqlLst("conditionSql", reportQuery , new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        
         try {
            while (rs.next()) {
                GtPktDtl pktDtl = new GtPktDtl();
                String emp = util.nvl(rs.getString("emp"));
                String stkIdn = util.nvl(rs.getString("stk_idn"));
    
                String issCol = util.nvl(rs.getString("iss_col"));
                String issClr = util.nvl(rs.getString("iss_clr"));
                String issCut = util.nvl(rs.getString("iss_cut"));
                pktDtl.setValue("emp", emp);
          
                pktDtl.setValue("issCol", issCol);
                pktDtl.setValue("issClr",issClr);
                pktDtl.setValue("issCut", issCut);
                pktDtl.setValue("stkIdn", stkIdn);
                pktDtl.setValue("vnm", util.nvl(rs.getString("vnm")));
                String tfl3 = util.nvl(rs.getString("rmk"));
                String vnm = util.nvl(rs.getString("vnm"));
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
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                     
                        
                        pktDtl.setValue(prp, val);
                }
                pktDtlList.put(stkIdn, pktDtl);          
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
       return pktDtlList ;
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
    
    public ArrayList lbComView(HttpServletRequest req , HttpServletResponse res){
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
                rs1.close();
                session.setAttribute("LbComViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
   
}
