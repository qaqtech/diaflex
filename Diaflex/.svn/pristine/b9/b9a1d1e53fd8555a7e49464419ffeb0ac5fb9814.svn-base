package ft.com.mpur;

import ft.com.DBMgr;
import ft.com.DBUtil;

import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.dao.GenDAO;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PurchaseConfrimAction extends DispatchAction {
    public PurchaseConfrimAction() {
        super();
    }
    
    
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
       HttpSession session = req.getSession(false);
       InfoMgr info = (InfoMgr)session.getAttribute("info");
       DBUtil util = new DBUtil();
       DBMgr db = new DBMgr();
       String rtnPg="sucess";
       if(info!=null){
       Connection conn=info.getCon();
       if(conn!=null){
       db.setCon(info.getCon());
       util.setDb(db);
       util.setInfo(info);
       db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
       util.setLogApplNm(info.getLoApplNm());
       rtnPg=init(req,res,session,util);
       }else{
       rtnPg="connExists";   
       }
       }else
       rtnPg="sessionTO";
       if(!rtnPg.equals("sucess")){
           return am.findForward(rtnPg);   
       }else{
        util.updAccessLog(req,res,"Purchase confrim", "load start");
        PurchaseConfrimFrom udf = (PurchaseConfrimFrom) af;
           udf.reset();
        GenericInterface genericInt = new GenericImpl();
        String prcStt = req.getParameter("prcStt");
        udf.setPrcStt(prcStt);
        String inStt="";
           ArrayList outPrcList = new ArrayList();
        String mprcSql="select a.in_stt inStt, c.IN_STT outStt , c.prc outPrc from mprc a , PRC_TO_PRC b , mprc c\n" + 
        "where a.idn = b.PRC_ID and a.IS_STT=? \n" + 
        "and b.PRC_TO_ID=c.idn ";
           ArrayList ary = new ArrayList();
           ary.add(prcStt);
           ArrayList outLst = db.execSqlLst("mprcSql", mprcSql, ary);
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet rs = (ResultSet)outLst.get(1);
           while(rs.next()){
               inStt =  rs.getString("inStt");
               GenDAO genDao = new GenDAO();
               genDao.setDsc(rs.getString("outPrc"));
               genDao.setSrt(rs.getString("outStt"));
               outPrcList.add(genDao);
           }
           rs.close();
           pst.close();
           udf.setInStt(inStt);
           udf.setOutPrcList(outPrcList);
           ArrayList purIdnList =new ArrayList();
       String pursql=  "select distinct a.pur_idn , n.fnme  vndr from mpur a , pur_dtl b  , pur_prp c , stk_dtl s , mstk m ,mnme n\n" + 
       "where\n" + 
       "a.pur_idn=b.pur_idn and a.typ='A' and b.pur_dtl_idn = c.pur_dtl_idn\n" + 
       "and c.mprp=? and b.stt=? \n" + 
       "and s.mprp=? and s.txt=c.txt and s.grp=1 and  a.VNDR_IDN=n.NME_IDN and \n" + 
       "s.mstk_idn=m.idn and m.stt=? ";
           ary = new ArrayList();
           ary.add("ALT_LOTNO");
           ary.add("IS");
           ary.add("ALT_LOTNO");
           ary.add(inStt);
           outLst = db.execSqlLst("pursql", pursql, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
           while(rs.next()){
               String purIdn = rs.getString("pur_idn");
               GenDAO genDao = new GenDAO();
               genDao.setDsc(rs.getString("vndr")+"("+purIdn+")");
               genDao.setSrt(purIdn);
               purIdnList.add(genDao);
               
               
           }
           rs.close();
           pst.close();
           
          udf.setVenderList(purIdnList);
           
           ArrayList searchList = genericInt.genricSrch(req,res,"PUR_CNF_SRCH","PUR_CNF_SRCH");
           info.setGncPrpLst(searchList);

           util.updAccessLog(req,res,"Purchase confrim", "load end");
        return am.findForward("load");
       }
    }
    
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
       HttpSession session = req.getSession(false);
       InfoMgr info = (InfoMgr)session.getAttribute("info");
       DBUtil util = new DBUtil();
       DBMgr db = new DBMgr();
       String rtnPg="sucess";
       if(info!=null){
       Connection conn=info.getCon();
       if(conn!=null){
       db.setCon(info.getCon());
       util.setDb(db);
       util.setInfo(info);
       db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
       util.setLogApplNm(info.getLoApplNm());
       rtnPg=init(req,res,session,util);
       }else{
       rtnPg="connExists";   
       }
       }else
       rtnPg="sessionTO";
       if(!rtnPg.equals("sucess")){
           return am.findForward(rtnPg);   
       }else{
        util.updAccessLog(req,res,"Purchase confrim", "fetch start");
        PurchaseConfrimFrom udf = (PurchaseConfrimFrom) af;
           GenericInterface genericInt = new GenericImpl();
           ArrayList genricSrchLst = genericInt.genricSrch(req,res,"PUR_CNF_SRCH","PUR_CNF_SRCH");
           info.setGncPrpLst(genricSrchLst);
         String inStt = (String)udf.getValue("inStt");
         String pur_idn = (String)udf.getValue("pur_idn");
           HashMap prp = info.getPrp();
           HashMap mprp = info.getMprp();
           HashMap paramsMap = new HashMap();
               for (int i = 0; i < genricSrchLst.size(); i++) {
               ArrayList srchPrp = (ArrayList)genricSrchLst.get(i);
               String lprp = (String)srchPrp.get(0);
               String flg= (String)srchPrp.get(1);
               String prpSrt = lprp ;  
               String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
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
                   }else{
                       String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                      String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                      if(fldVal2.equals(""))
                          fldVal2=fldVal1;
                        if(!fldVal1.equals("") && !fldVal2.equals("")){
                                       paramsMap.put(lprp+"_1", fldVal1);
                                       paramsMap.put(lprp+"_2", fldVal2);
                       }   
                   }            
           }
           if(paramsMap.size()>0){ 
           int lSrchId=util.genericSrchEntries(paramsMap);
           String pur_pkg = "PUR_PKG.SRCH(? ,?)";
           ArrayList ary = new ArrayList();
           ary.add(String.valueOf(lSrchId));
           ary.add("PUR_CNF_VW");
           int  ct = db.execCall("Purchase_srch", pur_pkg, ary);
           }else{
               paramsMap.put("pur_idn", pur_idn);
               paramsMap.put("mdl", "PUR_CNF_VW");
              FecthResult(req,res,paramsMap);
           }
          ArrayList purPktList = SearchResult(req, res, "Z");
        
        HashMap assortDtlList = AssortDtlResult(req, res, udf.getInStt());
           session.setAttribute("assortDtlList", assortDtlList);
         req.setAttribute("purPktDtlList", purPktList);
         req.setAttribute("view", "y");
        util.updAccessLog(req,res,"Purchase confrim", "fecth end");
        return am.findForward("load");
       }
    
    }
    
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
       HttpSession session = req.getSession(false);
       InfoMgr info = (InfoMgr)session.getAttribute("info");
       DBUtil util = new DBUtil();
       DBMgr db = new DBMgr();
       String rtnPg="sucess";
       if(info!=null){
       Connection conn=info.getCon();
       if(conn!=null){
       db.setCon(info.getCon());
       util.setDb(db);
       util.setInfo(info);
       db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
       util.setLogApplNm(info.getLoApplNm());
       rtnPg=init(req,res,session,util);
       }else{
       rtnPg="connExists";   
       }
       }else
       rtnPg="sessionTO";
       if(!rtnPg.equals("sucess")){
           return am.findForward(rtnPg);   
       }else{
        util.updAccessLog(req,res,"Purchase confrim", "fetch start");
        PurchaseConfrimFrom udf = (PurchaseConfrimFrom) af;
           HashMap assortDtlList = (HashMap)session.getAttribute("assortDtlList");
           String msg = "";
           String succVnm="";
            try {
                if (assortDtlList != null && assortDtlList.size() > 0) {
                    Enumeration reqNme = req.getParameterNames();
                    db.setAutoCommit(false);
                    while (reqNme.hasMoreElements()) {
                        String paramNm = (String)reqNme.nextElement();
                        if (paramNm.indexOf("cb_pur_") > -1) {
                            String purStkidn = req.getParameter(paramNm);
                            String purFnlRte =
                                util.nvl(req.getParameter("FNLRTE_" +
                                                          purStkidn), "0");
                            String assortFnlRte =
                                req.getParameter("AFNLRTE_" + purStkidn);
                            String purVnm =
                                req.getParameter("VNM@" + purStkidn);
                            String rejVlu =
                                util.nvl(req.getParameter("REJVLU_" +
                                                          purStkidn), "0");
                            String rejCts =
                                util.nvl(req.getParameter("REJCTS_" +
                                                          purStkidn), "0");
                            double percnt =
                                Double.parseDouble(purFnlRte) / Double.parseDouble(assortFnlRte);
                            ArrayList pktListDtl =
                                (ArrayList)assortDtlList.get(purStkidn);
                            if (pktListDtl != null && pktListDtl.size() > 0) {

                                try {
                                    for (int y = 0; y < pktListDtl.size();
                                         y++) {
                                        HashMap pktDtlMap =
                                            (HashMap)pktListDtl.get(y);
                                        String stk_idn =
                                            (String)pktDtlMap.get("stk_idn");
                                        String rte =
                                            util.nvl((String)pktDtlMap.get("rte"),
                                                     "0");
                                        double cpval =
                                            Double.parseDouble(rte) * percnt;
                                        String selRejRd =
                                            req.getParameter("RD@" +
                                                             purStkidn + "@" +
                                                             stk_idn);
                                        if (selRejRd != null) {
                                            String[] splitSel =
                                                selRejRd.split("_");
                                            String stt = splitSel[0];
                                            if (stt.equals("SEL")) {
                                                String stnStt =
                                                    (String)udf.getValue("outprc_" +
                                                                         stk_idn);
                                                String updateRej =
                                                    "update mstk set stt=? where idn =? ";
                                                ArrayList ary =
                                                    new ArrayList();
                                                ary.add(stnStt);
                                                ary.add(stk_idn);
                                                int ct =
                                                    db.execDirUpd("update mstk",
                                                                  updateRej,
                                                                  ary);

                                                ary = new ArrayList();
                                                ary.add(stk_idn);
                                                ary.add("CP");
                                                ary.add(String.valueOf(cpval));
                                                String stockUpd =
                                                    "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                                                ct =
 db.execCallDir("update stk Dtl", stockUpd, ary);

                                            } else {
                                                String updateRej =
                                                    "update mstk set stt=? where idn =? ";
                                                ArrayList ary =
                                                    new ArrayList();
                                                ary.add("PUR_REJ");
                                                ary.add(stk_idn);
                                                int ct =
                                                    db.execDirUpd("update mstk",
                                                                  updateRej,
                                                                  ary);
                                            }

                                        }
                                    }

                                    double rejRte =
                                        util.roundToDecimals(Double.parseDouble(rejVlu) /
                                                             Double.parseDouble(rejCts),
                                                             0);
                                    String purDtl =
                                        "update pur_dtl set stt= ? , rej_cts = ? , rej_rte =? , ofr_rte=? where mstk_idn = ? ";
                                    ArrayList ary = new ArrayList();
                                    ary.add("CF");
                                    ary.add(String.valueOf(rejCts));
                                    ary.add(String.valueOf(rejRte));
                                    ary.add(String.valueOf(purFnlRte));
                                    ary.add(purStkidn);
                                    int ct =
                                        db.execDirUpd("update purDtl", purDtl,
                                                      ary);
                                    if (ct > 0){
                                        succVnm = succVnm + "," + purVnm;
                                        db.doCommit();
                                    }else{
                                        db.doRollBack();
                                    }
                                } catch (NumberFormatException nfe) {
                                    // TODO: Add catch code
                                    nfe.printStackTrace();
                                    db.doRollBack();
                                } catch (Exception nfe) {
                                    // TODO: Add catch code
                                    nfe.printStackTrace();
                                    db.doRollBack();
                                }


                            }
                        }
                    }
                    msg = "Purchase confirmation done for :" + succVnm;
                } else {
                    msg = "Error in process";
                }
            } catch (NumberFormatException nfe) {
                // TODO: Add catch code
                nfe.printStackTrace();
                db.doRollBack();
            } finally {
                db.setAutoCommit(true);   
            }
           udf.reset();
           req.setAttribute("msg", msg);
           return am.findForward("load");
       }
    }
  
    public HashMap AssortDtlResult(HttpServletRequest req,HttpServletResponse res, String inStt){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap assortDtlMap = new HashMap();
        GenericInterface genericInt = new GenericImpl();
        String delQ = " Delete from gt_srch_multi ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        String gtInsert=" insert into gt_srch_multi(stk_idn,vnm,qty,cts,quot,pair_id, flg)\n" + 
        "           select a.idn ,a.vnm,a.qty, a.cts, a.upr , a.pkt_rt, ? from mstk a , gt_srch_rslt b\n" + 
        "           where a.pkt_rt=b.pair_id  and a.stt= ? ";
        ArrayList ary = new ArrayList();
        ary.add("Z");
        ary.add(inStt);
         ct = db.execCall("insert gt", gtInsert, ary);
         if(ct>0){
             String pktPrp = "SRCH_PKG.POP_PKT_PRP(pMdl=>?,pTbl=>?)";
             ary = new ArrayList();
             ary.add("AS_CNF_VW");
             ary.add("gt_srch_multi");
             ct = db.execCall(" Srch Prp ", pktPrp, ary);
             ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "AS_CNF_VW", "AS_CNF_VW");
             
             String  srchQ =  " select stk_idn ,pair_id pkt_rt, vnm, stt , qty ,to_char(trunc(cts,2),'999990.99') cts ,  quot , Trunc(quot*Cts,2) vlu   ";
                
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

             String rsltQ = srchQ + " from gt_srch_multi where flg =? order by pair_id ";
             ary = new ArrayList();
             ary.add("Z");

            try {
                ArrayList outLst = db.execSqlLst("rsltQ", rsltQ, ary);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                String PpktRt=null;
              
                ArrayList pktList = new ArrayList();
                while(rs.next()) {
                    String lpktRt = rs.getString("pkt_rt");
                    if(PpktRt==null)
                        PpktRt=lpktRt;
                    if(!PpktRt.equals(lpktRt)){
                        assortDtlMap.put(PpktRt, pktList);
                        pktList = new ArrayList();
                        PpktRt=lpktRt;
                    }
                    HashMap pktDtl = new HashMap();
                    pktDtl.put("vnm", rs.getString("vnm"));
                    pktDtl.put("qty", rs.getString("qty"));
                    pktDtl.put("cts", rs.getString("cts"));
                    pktDtl.put("rte", rs.getString("quot"));
                    pktDtl.put("vlu", rs.getString("vlu"));
                    pktDtl.put("stk_idn", rs.getString("stk_idn"));
                    for(int j=0; j < vwPrpLst.size(); j++){
                         String prp = (String)vwPrpLst.get(j);
                          
                          String fld="prp_";
                          if(j < 9)
                                  fld="prp_00"+(j+1);
                          else    
                                  fld="prp_0"+(j+1);
                          if(prp.equals("RTE"))
                              fld="quot";
                          String val = util.nvl(rs.getString(fld)) ;
                        pktDtl.put(prp, val);
                    }
                     pktList.add(pktDtl);

                }
                if(PpktRt!=null){
                    assortDtlMap.put(PpktRt, pktList);
                }
                rs.close();
                pst.close();
                HashMap totalsMap = new HashMap();
                String ttlSummary ="    select pair_id, sum(qty) qty,sum(cts) cts, trunc(((sum(trunc(cts,2)* quot) / sum(trunc(cts,2))))) avg_Rte , trunc(sum(trunc(cts,2)*quot), 2) vlu\n" + 
                "           from gt_srch_multi\n" + 
                "           group by pair_id ";
                outLst = db.execSqlLst("ttlSummary", ttlSummary, new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
                while(rs.next()) {
                    String pktIdn = rs.getString("pair_id") ;
                    String qty = rs.getString("qty") ;
                    String cts = rs.getString("cts") ;
                    String avg = rs.getString("avg_Rte");
                    String vlu = rs.getString("vlu") ;
                    HashMap ttlMap = new HashMap();
                    ttlMap.put("QTY", qty);
                    ttlMap.put("CTS", cts);
                    ttlMap.put("AVG", avg);
                    ttlMap.put("VLU", vlu);
                    totalsMap.put(pktIdn, ttlMap);
                }
                rs.close();
                pst.close();
              req.setAttribute("ttlMapDtl", totalsMap);
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
         }
      return assortDtlMap;
 
    }
    
    public void FecthResult(HttpServletRequest req,HttpServletResponse res, HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
       ArrayList pktList  = new ArrayList();
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ArrayList ary = new ArrayList();
        String typ = (String)params.get("stt");
        String refno = (String)params.get("refno");
        String venderId = (String)params.get("rlnId");
        String purIdn = (String)params.get("pur_idn");
        String mdl = (String)params.get("mdl");
        String srchRefQ = "Insert into gt_srch_rslt ( pair_id ,sl_idn, stk_idn, vnm, qty, cts, pkt_dte, stt, dsp_stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis,fquot,exh_rte) \n" + 
        "          select a.mstk_idn, A.Pur_Idn, a.pur_dtl_idn, a.ref_idn, 1, a.cts, a1.dte\n" + 
        "            , a1.typ stt, decode(a1.typ, 'R', 'Review', 'O', 'Offer', 'B', 'Buy', a1.typ)\n" + 
        "            , a.cmp, a.rap, 'NA', null, 'Z' flg, a.pur_dtl_idn, a.rte, a.dis,nvl(a.ofr_rte, a.rte),nvl(a1.exh_rte,get_xrt(a1.cur))\n" + 
        "          From Mpur A1, Pur_Dtl A Where A.Pur_Idn = A1.Pur_Idn and a.stt='IS'  and nvl(a1.flg2, 'NA') <> 'DEL' and nvl(a.flg2, 'NA') <> 'DEL'\n" + 
      
        "           And A1.Pur_Idn  = ? " ;
       
        
      
        ary = new ArrayList();
        
        ary.add(purIdn);
       
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "PUR_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add(mdl);
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
    }
    
    public ArrayList SearchResult(HttpServletRequest req,HttpServletResponse res,String flg){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
       
        GenericInterface genericInt = new GenericImpl();
        ArrayList pktList = new ArrayList();
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "PUR_CNF_VW", "PUR_CNF_VW");
       
        String  srchQ =  " select stk_idn ,pair_id,sl_idn pur_idn, pkt_ty,  vnm,to_char(trunc(pkt_dte), 'dd-mm-rrrr') dte  ,rap_rte, stt , qty ,to_char(trunc(cts,2),'999990.99') cts , cmp , quot , Trunc(quot*Cts,2) vlu , decode(rap_rte, 1, '', trunc(((fquot/greatest(rap_rte,1))*100)-100,2)) dis   ";
           
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
        ary.add(flg);
        
        ArrayList outLst = db.execSqlLst("rsltQ", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try{
            while(rs.next()) {

                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.put("vnm",vnm);
                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                    pktPrpMap.put("dte",util.nvl(rs.getString("dte")));
                    pktPrpMap.put("pur_idn",util.nvl(rs.getString("pur_idn")));
                    pktPrpMap.put("mstk_idn",util.nvl(rs.getString("pair_id")));
                    pktPrpMap.put("dte",util.nvl(rs.getString("dte")));
                    pktPrpMap.put("quot",util.nvl(rs.getString("quot")));
                   
                    pktPrpMap.put("vlu",util.nvl(rs.getString("vlu")));
                   
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                             if (prp.toUpperCase().equals("CRTWT"))
                                 val = util.nvl(rs.getString("cts"));
                             if (prp.toUpperCase().equals("RAP_DIS"))
                                 val = util.nvl(rs.getString("dis"));
                            
                             if (prp.toUpperCase().equals("RAP_RTE"))
                                 val = util.nvl(rs.getString("rap_rte"));
                           
                             if(prp.equals("RTE"))
                                 val = util.nvl(rs.getString("quot"));
                             
                             
                                 pktPrpMap.put(prp, val);
                        
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
                util.updAccessLog(req,res,"Repairing Return", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Purchase Action", "init");
            }
            }
            return rtnPg;
            }
}
