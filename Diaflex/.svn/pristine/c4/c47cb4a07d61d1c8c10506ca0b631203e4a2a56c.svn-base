package ft.com.lab;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.labDet;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

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

public class MultiLabImpl implements MultiLabInterface {
    public ArrayList FetchResult(HttpServletRequest req ,HttpServletResponse res, MultiLabForm form){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap dbInfoSys = info.getDmbsInfoLst();
        String cnt = (String)dbInfoSys.get("CNT");
        String vnm = util.nvl((String)form.getValue("vnmLst"));
        form.reset();      
        if(!vnm.equals("")){
            ArrayList ary = new ArrayList();
            ary=new ArrayList();
            String delQ = " Delete from gt_srch_rslt ";
            int ct =db.execUpd(" Del Old Pkts ", delQ, ary);
            
            String conQ=" where stt in ('MKAV') ";
            String srchRefQ = 
            "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1  ,  cert_lab , rmk ) " + 
            " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , sk1 ,  cert_lab , tfl3 "+
            "     from mstk b "+conQ;
             vnm = util.getVnm(vnm);
            srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))" ;
            ary = new ArrayList();
            ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        }
        
        ArrayList  stockList = SearchResult(req ,res, form , vnm);
       
        return stockList;
        
    }
      public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res, MultiLabForm form ,String vnmLst ){
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
          ArrayList param = new ArrayList();
          HashMap pktPrpMap = new HashMap();
          HashMap pktDtlMap = new HashMap();
          ArrayList stkIdnLst = new ArrayList();
          GenericInterface genericInt = new GenericImpl();
          ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "MULTI_LAB_VW","MULTI_LAB_VW");
          try {
          String mprpStr = "";
          String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||Upper(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1')) str From Rep_Prp Rp Where rp.MDL = ? and flg ='Y' order by srt " ;
          param = new ArrayList();
          param.add("MULTI_LAB_VW");

          ArrayList outLst = db.execSqlLst("mprp ", mdlPrpsQ , param);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
          while(rs.next()) {
          String val = util.nvl((String)rs.getString("str"));
          mprpStr = mprpStr +" "+val;
          }
          rs.close(); pst.close();
              
          String rsltQ = " with " +
          " STKDTL as " +
          " ( select b.sk1,pl.grp,pl.lab pllab,pl.stt plstt,c.stk_idn, nvl(nvl(nvl(a.txt,a.dte),a.num),a.val) atr , mprp, b.vnm,b.stt,b.qty,b.rmk from stk_dtl a,PKT_LAB_DTL pl, mstk b , gt_srch_rslt c" +
          " Where 1=1 " +
          " and a.mstk_idn=pl.stk_idn and a.grp=pl.grp and a.mstk_idn = b.idn and b.idn = c.stk_idn  and c.flg=?" +
          " and exists ( select 1 from rep_prp rp where rp.MDL = ? and rp.flg='Y' and a.mprp = rp.prp)) " +
          " Select * from stkDtl PIVOT " +
          " ( max(atr) " +
          " for mprp in ( "+mprpStr+" ) ) order by 1,2" ;
          param = new ArrayList();
          param.add("Z");
          param.add("MULTI_LAB_VW");
          outLst = db.execSqlLst("search Result", rsltQ, param);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
          String pstkIdn = "0";
          while(rs.next()) {
              String lstkIdn = util.nvl(rs.getString("stk_idn"));
              if(pstkIdn.equals("0"))
              pstkIdn = lstkIdn;
              if(!pstkIdn.equals(lstkIdn)){
                  pktDtlMap.put(pstkIdn, pktList);
                  stkIdnLst.add(pstkIdn);
                  pktList = new ArrayList();
                  pstkIdn = lstkIdn;
              }
              pktPrpMap = new HashMap();
              String plstt = util.nvl(rs.getString("plstt"));
              String lab = util.nvl(rs.getString("pllab"));
              String grp = util.nvl(rs.getString("grp"));
              pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
              pktPrpMap.put("vnm",util.nvl(rs.getString("vnm")));    
              pktPrpMap.put("stk_idn",lstkIdn);
              pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
              pktPrpMap.put("grp",grp);
              pktPrpMap.put("lab",lab);
              if(plstt.equals("A"))
              form.setValue(lab+"_"+pstkIdn+"_"+grp, "yes");
              if(grp.equals("1"))
              form.setValue("RD_"+pstkIdn, grp+"_"+lab);
              for(int j=0; j < vwPrpLst.size(); j++){
                   String prp = (String)vwPrpLst.get(j);
                   String fldName = util.pivot(prp);
                   if(prp.equals("CRTWT"))
                       fldName="CRTWT";
                   String fldVal = util.nvl((String)rs.getString(fldName));
                   pktPrpMap.put(prp, fldVal);
              }
              pktList.add(pktPrpMap);
          }
          rs.close(); pst.close();
              if(!pstkIdn.equals("0")){
                  pktDtlMap.put(pstkIdn, pktList);
                  stkIdnLst.add(pstkIdn);
              }
      } catch (SQLException sqle) {

          // TODO: Add catch code
          sqle.printStackTrace();
      }
      session.setAttribute("multilabstkIdnLst", stkIdnLst);
      session.setAttribute("multilabpktDtlMap", pktDtlMap);
      return pktList;
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
    public MultiLabImpl() {
        super();
    }
    public void labDetail(HttpServletRequest req ,HttpServletResponse res, HashMap param){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String mdl = util.nvl((String)param.get("mdl"));
        if(mdl.equals("")){
            mdl="LAB_VIEW";
        }else{
          memoVeiw(req, res);
        }
        ArrayList grpList = new ArrayList();
        HashMap stockPrpUpd = new HashMap();
        String stockPrp = "select mstk_idn, lab,b.dta_typ, mprp, a.srt, grp " +
            ", decode(b.dta_typ, 'C', a.val,'N', to_char(a.num)||prop_pkg.get_val(mstk_idn, mprp)" +
            ", 'D', to_char(dte,'dd-mm-rrrr'), nvl(txt,'')) val " + 
        "from stk_dtl a, mprp b , rep_prp c " + 
        "where a.mprp = b.prp and mstk_idn =?  and b.dta_typ in ('C','N','T','D') and c.prp = b.prp and c.flg = 'Y' and mdl=? " + 
        "order by grp, psrt, mprp,lab ";
        ArrayList ary = new ArrayList();
        ary.add(param.get("stkIdn"));
        ary.add(mdl);
        ArrayList outLst = db.execSqlLst("stockDtl", stockPrp, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                String grp = util.nvl(rs.getString("grp"));
                if(!grpList.contains(grp))
                    grpList.add(grp);
                
                String mprp =util.nvl(rs.getString("mprp"));
                String val = util.nvl(rs.getString("val"));
              
                String msktIdn = util.nvl(rs.getString("mstk_idn"));
                stockPrpUpd.put(msktIdn+"_"+mprp+"_"+grp, val);
               
            }
            
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
       
        
        req.setAttribute("labDtlList",stockPrpUpd);
        req.setAttribute("grpList", grpList);
    
    }
    
    public ArrayList memoVeiw(HttpServletRequest req ,HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("MEMO_VW");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();

                ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MEMO_VW' and flg='Y' order by rnk ",
                               new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close(); pst.close();
                session.setAttribute("MEMO_VW", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
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
                ArrayList outLst = db.execSqlLst( "Vw Lst ", "Select prp  from rep_prp where mdl = 'LAB_VIEW' and flg='Y' order by rnk ",
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
    
    public ArrayList LBGenricSrch(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("LBGNCSrch");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();

                ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp , flg  from rep_prp where mdl = 'LB_SRCH' and flg <> 'N'  order by rnk ",
                               new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    ArrayList asViewdtl=new ArrayList();
                    asViewdtl.add(rs1.getString("prp"));
                    asViewdtl.add(rs1.getString("flg"));
                    asViewPrp.add(asViewdtl);
                }
                rs1.close(); pst.close();
                session.setAttribute("LBGNCSrch", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
}
