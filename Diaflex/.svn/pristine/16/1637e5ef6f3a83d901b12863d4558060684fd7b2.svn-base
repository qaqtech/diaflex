package ft.com.rough;

import ft.com.DBMgr;
import ft.com.DBUtil;

import ft.com.InfoMgr;
import ft.com.dao.GtPktDtl;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import ft.com.marketing.SearchQuery;

import java.math.BigDecimal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class RoughPolishPlanComparReport extends DispatchAction {
    public RoughPolishPlanComparReport() {
        super();
    }
    
    
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String rtnPg="sucess";
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg=init(req,res,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
          RoughGroupFrom udf = (RoughGroupFrom)af;
          udf.resetAll();
          GenericInterface genericInt = new GenericImpl();
          ArrayList assortSrchList = genericInt.genricSrch(req,res,"RGH_PLAN_SRCH","RGH_PLAN_SRCH");
          info.setGncPrpLst(assortSrchList);
          
          return am.findForward("load");
        }
    }
    
    
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String rtnPg="sucess";
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg=init(req,res,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
          RoughGroupFrom udf = (RoughGroupFrom)af;
          GenericInterface genericInt = new GenericImpl();
          ArrayList genricSrchLst = genericInt.genricSrch(req,res,"RGH_PLAN_SRCH","RGH_PLAN_SRCH");
          info.setGncPrpLst(genricSrchLst);
          HashMap mprp = info.getMprp();
          HashMap prp = info.getPrp();
            HashMap paramsMap = new HashMap();
            for(int i=0;i<genricSrchLst.size();i++){
                ArrayList prplist =(ArrayList)genricSrchLst.get(i);
                String lprp = (String)prplist.get(0);
                String flg= (String)prplist.get(1);
                String typ = util.nvl((String)mprp.get(lprp+"T"));
                String prpSrt = lprp ;  
                if(flg.equals("M")) {
                
                    ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                    ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                    for(int j=0; j < lprpS.size(); j++) {
                    String lSrt = (String)lprpS.get(j);
                    String lVal = (String)lprpV.get(j);    
                    String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                    if(!reqVal1.equals("")){
                    paramsMap.put(lprp + "_" + lVal, reqVal1);
                    }
                       
                    }
                }else if(flg.equals("SM")){
                    String reqVal1 = util.nvl((String)udf.getValue(lprp));
                        if(!reqVal1.equals("")){
                        String[] srtLst = reqVal1.split(",");
                        if(srtLst.length>0) {  
                        paramsMap.put(lprp, srtLst);
                        }}
                }else{
                String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                if(typ.equals("T")){
                    fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                    fldVal2 = fldVal1;
                }
                if(fldVal2.equals(""))
                fldVal2=fldVal1;
                if(!fldVal1.equals("") && !fldVal2.equals("")){
                    paramsMap.put(lprp+"_1", fldVal1);
                    paramsMap.put(lprp+"_2", fldVal2);
                }
                }
            }
            if(paramsMap.size()>0){
            paramsMap.put("stt", "RGH_MRG");
            paramsMap.put("mdl", "RGH_VIEW");
            paramsMap.put("PRCD", "ROUGH");
            util.genericSrch(paramsMap);
             ArrayList vwPrpLst = ASPrprViw(req, res, "RGH_VIEW");
             req.setAttribute("RGH_VIEW", vwPrpLst);
             ArrayList pktList = SearchResult(req, res, "", "RGH_VIEW");
             req.setAttribute("ROOTPKTLIST", pktList);
             HashMap polishPktList = PolishDataList(req, res, "", "PLAN_ENTRY");
            req.setAttribute("POLISHPKTLIST", polishPktList);
            HashMap planDataList = PlanDataList(req, res, "", "PLAN_ENTRY");
             ASPrprViw(req, res, "PLAN_ENTRY");
            req.setAttribute("PALNLIST", planDataList);
            }
          req.setAttribute("view", "Y");
          return am.findForward("load");
        }
    }
    
    public ArrayList SearchResult(HttpServletRequest req,
                                HttpServletResponse res, String vnmLst,
                                String mdl) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        ArrayList pktList = new ArrayList();
      
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            init(req, res, session, util);

            ArrayList vwPrpLst = ASPrprViw(req, res, mdl);
            req.setAttribute("RGH_VIEW", vwPrpLst);
            String srchQ =
                " select stk_idn , pkt_ty,stt, sk1, vnm, pkt_dte, stt , qty , cts , srch_id ,rmk , byr.get_nm(rln_idn) emp , quot ";


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


            String rsltQ =
                srchQ + " from gt_srch_rslt where flg =? order by sk1 , cts ";

            ArrayList ary = new ArrayList();
            ary.add("Z");
          ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            try {
                while (rs.next()) {
                    String cts = util.nvl(rs.getString("cts"), "0");
                   
                    String qty = util.nvl(rs.getString("qty"), "0");
                   
                    String stkIdn = util.nvl(rs.getString("stk_idn"));
                    GtPktDtl pktPrpMap = new GtPktDtl();
                    pktPrpMap.setValue("stt", util.nvl(rs.getString("stt")));
                    pktPrpMap.setValue("emp", util.nvl(rs.getString("emp")));
                    String vnm = util.nvl(rs.getString("vnm"));
                    pktPrpMap.setValue("vnm", vnm);
                  
                    pktPrpMap.setValue("stk_idn", stkIdn);
                    pktPrpMap.setValue("qty", util.nvl(rs.getString("qty")));
                    pktPrpMap.setValue("cts", cts);
                  
                    pktPrpMap.setValue("stt", util.nvl(rs.getString("stt")));
                    
                    pktPrpMap.setValue("upr", util.nvl(rs.getString("quot")));
                    for (int j = 0; j < vwPrpLst.size(); j++) {
                        String prp = (String)vwPrpLst.get(j);

                        String fld = "prp_";
                        if (j < 9)
                            fld = "prp_00" + (j + 1);
                        else
                            fld = "prp_0" + (j + 1);
                        if (prp.equals("CRTWT"))
                            fld = "cts";
                        String val = util.nvl(rs.getString(fld));


                        pktPrpMap.setValue(prp, val);
                    }

                    pktList.add(pktPrpMap);
                }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {

                // TODO: Add catch code
                sqle.printStackTrace();
            }
        }
        return pktList;
    }
    
    public HashMap PolishDataList(HttpServletRequest req,
                                HttpServletResponse res, String vnmLst,
                                String mdl) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        HashMap pktListMap = new HashMap();
      
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            init(req, res, session, util);
            String polishSql = "select rh.stk_idn rtPkt, m.idn,m.vnm, m.cts, m.stt,m.upr,m.qty from gt_srch_rslt rh , stk_dtl rt , mstk m\n" + 
            "where rh.stk_idn=TO_NUMBER(rt.txt) and rt.mprp=? and rt.grp=? \n" + 
            "and rt.mstk_idn=m.idn order by m.idn,m.cts ";
            ArrayList ary = new ArrayList();
            ary.add("RT_PKT");
            ary.add("1");
            String prt_pkt=null;
            ArrayList pktList = new ArrayList();
            ArrayList rsList = db.execSqlLst("polishSql", polishSql,ary);
            try {
                PreparedStatement pst = (PreparedStatement)rsList.get(0);
                ResultSet rs = (ResultSet)rsList.get(1);
                while (rs.next()) {
                    String rt_pkt = util.nvl(rs.getString("rtPkt"));
                    if(prt_pkt==null)
                        prt_pkt=rt_pkt;
                    if(!prt_pkt.equals(rt_pkt)){
                        pktListMap.put(prt_pkt, pktList);
                        pktList=new ArrayList();
                        prt_pkt=rt_pkt;
                    }
                    HashMap pktDtl = new HashMap();
                    String idn = util.nvl(rs.getString("idn"));
                    String vnm = util.nvl(rs.getString("vnm"));
                    String cts = util.nvl(rs.getString("cts"));
                    String stt = util.nvl(rs.getString("stt"));
                    String qty = util.nvl(rs.getString("qty"));
                    String upr = util.nvl(rs.getString("upr"));
                    String rtPkt = util.nvl(rs.getString("rtPkt"));
                    pktDtl.put("IDN", idn);
                    pktDtl.put("VNM", vnm);/*  */
                    pktDtl.put("CTS", cts);
                    pktDtl.put("STT", stt);
                    pktDtl.put("QTY", qty);
                    pktDtl.put("UPR", upr);
                    String getPktPrp = " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                                       + " from stk_dtl a, mprp b, rep_prp c "
                                       + " where a.mprp = b.prp and a.grp=1 and b.prp = c.prp and c.mdl = ? and a.mstk_idn = ? "
                                       + " order by c.rnk, c.srt ";

                    ary = new ArrayList();
                    ary.add(mdl);
                    ary.add(idn);
                    ArrayList rsList1 = db.execSqlLst("getPktPrp", getPktPrp,ary);
                    PreparedStatement pst1 = (PreparedStatement)rsList1.get(0);
                    ResultSet rs1 = (ResultSet)rsList1.get(1);
                    while (rs1.next()) {
                        String lPrp = rs1.getString("mprp");
                        String lVal = rs1.getString("val");
                        pktDtl.put(lPrp, lVal);
                        
                    }
                    rs1.close();
                    pst1.close();
                    pktList.add(pktDtl);

                }
                if(prt_pkt!=null){
                    pktListMap.put(prt_pkt, pktList);
                    
                }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            } finally {

            }


        }
        return pktListMap;
    }
    
    
    public HashMap PlanDataList(HttpServletRequest req,
                                HttpServletResponse res, String vnmLst,
                                String mdl) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
       HashMap rootPktMap = new HashMap();
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            init(req, res, session, util);
            String polishSql = "select  a.stk_idn , c.cts,c.rap_rte,c.rte, c.sub_plan_seq, c.idn  from gt_srch_rslt a , plan_m b , plan_pkt c\n" + 
            "where a.stk_idn=b.ref_idn and b.dflt_yn=? \n" + 
            "and b.idn=c.plan_idn\n" + 
            "order by a.stk_idn, c.cts, b.idn , c.idn ";
            ArrayList ary = new ArrayList();
            ary.add("Y");
            ArrayList rsList = db.execSqlLst("polishSql", polishSql,ary);
            String prt_pkt=null;
            ArrayList pktList = new ArrayList();
            try {
                PreparedStatement pst = (PreparedStatement)rsList.get(0);
                ResultSet rs = (ResultSet)rsList.get(1);
                while (rs.next()) {
                    HashMap pktDtl = new HashMap();
                    String rt_pkt = rs.getString("stk_idn");
                    if(prt_pkt==null)
                        prt_pkt = rt_pkt;
                    if(!prt_pkt.equals(rt_pkt)){
                        rootPktMap.put(prt_pkt, pktList);
                        pktList = new ArrayList();
                        prt_pkt=rt_pkt;
                    }
                    String cts = rs.getString("cts");
                    String plan = rs.getString("sub_plan_seq");
                    String idn = rs.getString("idn");
                    pktDtl.put("IDN", idn);
                    pktDtl.put("CTS", cts);
                    pktDtl.put("PLAN", plan);
                    String getPktPrp ="  select a.mprp, decode(b.dta_typ, 'C', a.val, 'N', a.num, 'D', a.dte, a.txt) val \n" + 
                    "   from plan_pkt_dtl a, mprp b, rep_prp c \n" + 
                    "    where a.mprp = b.prp  and b.prp = c.prp and c.mdl = 'MEMO_RTRN' \n" + 
                    "     and a.plan_pkt_idn = ? \n" +
                    "       order by c.rnk, c.srt";

                    ary = new ArrayList();
                    ary.add(idn);
                    ArrayList rsList1 = db.execSqlLst("getPktPrp", getPktPrp,ary);
                    PreparedStatement pst1 = (PreparedStatement)rsList1.get(0);
                    ResultSet rs1 = (ResultSet)rsList1.get(1);
                    while (rs1.next()) {
                        String lPrp = rs1.getString("mprp");
                        String lVal = rs1.getString("val");
                        pktDtl.put(lPrp, lVal);
                        
                    }
                    pktList.add(pktDtl);
                    rs1.close();
                    pst1.close();

                }
                if(prt_pkt!=null){
                    rootPktMap.put(prt_pkt, pktList);
                 
                }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            } finally {

            }


        }
        return rootPktMap;
    }
    public ArrayList ASPrprViw(HttpServletRequest req, HttpServletResponse res,
                               String mdl) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        ArrayList asViewPrp = (ArrayList)session.getAttribute(mdl);
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            init(req, res, session, util);

            try {
                if (asViewPrp == null) {

                    asViewPrp = new ArrayList();
                    ArrayList ary = new ArrayList();
                    ary.add(mdl);
           
                  ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = ? and flg='Y' order by rnk ",
                                   ary);
                  PreparedStatement pst = (PreparedStatement)outLst.get(0);
                  ResultSet rs1 = (ResultSet)outLst.get(1);
                    while (rs1.next()) {

                        asViewPrp.add(rs1.getString("prp"));
                    }
                    rs1.close();
                    pst.close();
                    session.setAttribute(mdl, asViewPrp);
                }

            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        }
        return asViewPrp;
    }
    public String init(HttpServletRequest req , HttpServletResponse res,HttpSession session,DBUtil util) {
            String rtnPg="sucess";
            String invalide="";
            String connExists=util.nvl(util.getConnExists());  
            if(!connExists.equals("N"))
            invalide=util.nvl(util.chkTimeOut(),"N");
            if(session.isNew())
            rtnPg="sessionTO";    
            if(connExists.equals("N"))
            rtnPg="connExists";     
            if(invalide.equals("Y"))
            rtnPg="chktimeout";
            if(rtnPg.equals("sucess")){
            boolean sout=util.getLoginsession(req,res,session.getId());
            if(!sout){
            rtnPg="sessionTO";
            System.out.print("New Session Id :="+session.getId());
            }else{
                rtnPg=util.checkUserPageRights("",util.getFullURL(req));
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Split Return Action", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Split Return Action", "init");
            }
            }
            return rtnPg;
            }
}
