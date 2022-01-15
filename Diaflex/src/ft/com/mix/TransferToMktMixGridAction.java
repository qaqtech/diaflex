package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.dao.GtPktDtl;
import ft.com.dao.boxDet;
import ft.com.dao.labDet;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.inward.TransferToMktSHForm;
import ft.com.marketing.SearchQuery;

import ft.com.mixakt.MixTransferKapuForm;

import ft.com.receipt.ReceviceInvoiceForm;

import java.io.OutputStream;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class TransferToMktMixGridAction extends DispatchAction {
    public TransferToMktMixGridAction() {
        super();
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
        util.updAccessLog(req,res,"Transfer to Marketing", "Unauthorized Access");
        else
        util.updAccessLog(req,res,"Transfer to Marketing", "init");
    }
    }
    return rtnPg;
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
            util.updAccessLog(req,res,"Transfer to Marketing", "load start");
        TransferToMKTMIXFrom form = (TransferToMKTMIXFrom)af;
            form.resetAll();
        GenericInterface genericInt=new GenericImpl(); 
        ArrayList srchPktList = genericInt.genricSrch(req, res, "TRF_MIX_SRCH", "TRF_MIX_SRCH");
        info.setGncPrpLst(srchPktList);
       
       
            util.updAccessLog(req,res,"Transfer to Marketing", "load end");
         return am.findForward("load");
        }
    }
    
    
    public ActionForward fetch(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
            util.updAccessLog(req,res,"Lab Issue", "fetch start");
            TransferToMKTMIXFrom udf = (TransferToMKTMIXFrom)form;
          String trntyp = util.nvl((String)udf.getValue("typ"));
    boolean isGencSrch = false;
    HashMap stockList = new HashMap();
            GenericInterface genericInt=new GenericImpl(); 
            ArrayList genricSrchLst = genericInt.genricSrch(req, res, "TRF_MIX_SRCH", "TRF_MIX_SRCH");
            info.setGncPrpLst(genricSrchLst);
    //      ArrayList genricSrchLst = info.getGncPrpLst();
    HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
    String vnm = util.nvl((String)udf.getValue("vnmLst"));
    
    HashMap paramsMap = new HashMap();
    if(vnm.equals("")){
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
        paramsMap.put("stt", "MX_FN");
        paramsMap.put("MIX", "Y");
        paramsMap.put("mdl", "TRF_MIX_VIEW");
        isGencSrch = true;
        util.genericSrch(paramsMap);
        stockList = SearchResultGT(req,res,vnm);
    }else{
        stockList =FetchResult(req,res,vnm);
    }
    String lstNme = "MIXTRAN_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
    gtMgr.setValue(lstNme+"_SEL",new ArrayList());
    gtMgr.setValue(lstNme, stockList);
    gtMgr.setValue("lstNmeMIXTRN", lstNme);
    if(stockList.size()>0){
        HashMap dtlMap = new HashMap();
        ArrayList selectstkIdnLst = new ArrayList();
        Set<String> keys = stockList.keySet();
               for(String key: keys){
              selectstkIdnLst.add(key);
               }
        dtlMap.put("selIdnLst",selectstkIdnLst);
        dtlMap.put("pktDtl", stockList);
        dtlMap.put("flg", "Z");
        dtlMap.put("CTSDIGIT", "3");
        HashMap ttlMap = GetTotal(req, res) ;               
        gtMgr.setValue(lstNme+"_TTL", ttlMap);
     
       
    }
    req.setAttribute("view", "Y");
    udf.reset();
    return am.findForward("load");
        }
    }
    
    
    public HashMap FetchResult(HttpServletRequest req,HttpServletResponse res, String  vnm ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        
     
      
        String stt="MX_FN";
        ArrayList ary = null;
        ArrayList delAry=new ArrayList();
        delAry.add("Z");
        String delQ = " Delete from gt_srch_rslt where flg in (?)";
        int ct =db.execUpd(" Del Old Pkts ", delQ, delAry);
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn , vnm , pkt_dte, stt , flg , qty , cts , rmk,sk1,quot ) " + 
        " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , tfl3 ,sk1,nvl(upr,cmp)  "+
        "     from mstk b "+
        " where stt =? and cts > 0  ";
        
        if(!vnm.equals("")){
                 vnm = util.getVnm(vnm);
                srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))" ;
                
          }
        ary = new ArrayList();
        ary.add(stt);
      
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add("TRF_MIX_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        HashMap stockList = SearchResultGT(req ,res , vnm);
        req.setAttribute("pktList", stockList);
        return stockList;
    }
    
    
    public HashMap SearchResultGT(HttpServletRequest req ,HttpServletResponse res, String vnmLst  ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        GenericInterface genericInt=new GenericImpl();
        HashMap pktList = new HashMap();
        ArrayList vwPrpLst =genericInt.genericPrprVw(req, res, "TRF_MIX_VIEW", "TRF_MIX_VIEW");
        String  srchQ =  " select sk1,stk_idn , pkt_ty, vnm,rap_rte, pkt_dte, stt , qty ,  to_char(cts,'999,990.000') cts , rmk,quot rte,to_char(cts * quot, '99999990.00') amt,to_char(cts * rap_rte, '99999990.00') rapvlu  ";

        

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

        
        String rsltQ = srchQ + " from gt_srch_rslt where flg =? " ;
       
         rsltQ  = rsltQ+ "order by sk1 , cts ";
        
        ArrayList ary = new ArrayList();
        ary.add("Z");
      ArrayList  rsLst = db.execSqlLst("search Result", rsltQ, ary);
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while(rs.next()) {
                  GtPktDtl pktPrpMap = new GtPktDtl();
                float cts = rs.getFloat("cts");
                String stk_idn = util.nvl(rs.getString("stk_idn"));
               pktPrpMap.setSk1(new BigDecimal(util.nvl(rs.getString("sk1"),"0")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.setValue("vnm",vnm);
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
                pktPrpMap.setValue("stk_idn", stk_idn);
                pktPrpMap.setValue("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.setValue("cts",util.nvl(rs.getString("cts")));
                    pktPrpMap.setValue("USDVAL",util.nvl(rs.getString("rte")));
                    pktPrpMap.setValue("AMT",util.nvl(rs.getString("amt")));
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
                   
                    pktList.put(stk_idn, pktPrpMap);
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
      pktList =(HashMap)util.sortByComparator(pktList);
        return pktList;
    }
    public HashMap GetTotal(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        HashMap gtTotalMap = new HashMap();
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            init(req, res, session, util);

            String gtTotal =
                "Select sum(qty) qty, to_char(sum(cts),'999990.000') cts from gt_srch_rslt where flg = ?";
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
        return gtTotalMap;
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
            TransferToMKTMIXFrom udf = (TransferToMKTMIXFrom)af;
            Enumeration reqNme = req.getParameterNames(); 
            ArrayList ary = null;
            int ct=0;
            db.setAutoCommit(false);
            boolean isCommit = true;
            String msg="No of Stone transfer successfully :- ";
            int cnt=0;
            try {
                while (reqNme.hasMoreElements()) {
                    String paramNm = (String)reqNme.nextElement();
                    if (paramNm.indexOf("cb_stk_") > -1) {
                        String mstkIdn = req.getParameter(paramNm);

                        String updMstk = "update mstk set stt=? where idn=?";
                        ary = new ArrayList();
                        ary.add("MKAV");
                        ary.add(mstkIdn);

                        ct = db.execCall("update mstk", updMstk, ary);
                        
                        String rteval = util.nvl(req.getParameter("RTE_"+mstkIdn));
                        if(!rteval.equals("") && !rteval.equals("0")){
                        ary = new ArrayList();
                        ary.add(mstkIdn);
                        ary.add("RTE");
                        ary.add(rteval);
                        String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? )";
                        ct = db.execCall("stockUpd",stockUpd, ary);
                        }
                       cnt=cnt+ct;

                    }
                }

                if (ct > 0) {
                    try {
                        ary = new ArrayList();
                        ary.add("MKAV");
                        ary.add("MKT");
                        ct = db.execCall("dpMrge", "DP_MERGE_MIX_PKTS(pStt=> ?,pTyp => ?)", ary);
                        if(ct<0)
                            isCommit=false;
                    } catch (Exception e) {
                        // TODO: Add catch code
                        isCommit=false;
                        e.printStackTrace();
                    }
                }else{
                    isCommit=false;
                }

            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
                isCommit=false;
            } finally {
                db.setAutoCommit(true);
            }
            if(isCommit){
                db.doCommit();
                msg=msg+" "+cnt;
            }else{
                db.doRollBack();
                msg="Error in process...";
            }
            
            req.setAttribute("msg", msg);
                
            
            return am.findForward("load");   
        }
        
        
        
        
              
            }
    
    
    
}
