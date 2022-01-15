package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.UIForms;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class StockTallyAction extends DispatchAction {

    public StockTallyAction() {
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
          util.updAccessLog(req,res,"stock tally", "stock tally load");
          
         HashMap pktDtl = new HashMap();
         ArrayList sttList = new ArrayList();
         String sql = "select sum(a.qty) qty, sum(a.cts) cts , a.stt mstkStt, b.stt tlyStt from mstk a , stk_tly b "+
                     " where a.idn = b.stk_idn and b.stt in ('IS','RT') and trunc(nvl(b.iss_dte, sysdate)) = trunc(sysdate) "+
                     " and trunc(nvl(b.rtn_dte, sysdate)) = trunc(sysdate)  group by a.stt, b.stt ";
          ArrayList outLst = db.execSqlLst("sql", sql, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
         while(rs.next()){
            String qty = util.nvl(rs.getString("qty"));
            String cts =  util.nvl(rs.getString("cts"));
            String mstk_stt = util.nvl(rs.getString("mstkStt"));
            String tlyStt =  util.nvl(rs.getString("tlyStt"));
            pktDtl.put(mstk_stt+"_"+tlyStt+"_Q" , qty);
            pktDtl.put(mstk_stt+"_"+tlyStt+"_C" , cts);
            if(!sttList.contains(mstk_stt))
                sttList.add(mstk_stt);
         }
        rs.close();
            pst.close();
        req.setAttribute("sttList",sttList);
        req.setAttribute("pktDtl", pktDtl);
          util.updAccessLog(req,res,"stock tally", "stock tally load done");
        return am.findForward("load");
        }
    }
    
    public ActionForward loadTtl(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"stock tally", "stock tally load ttl");
         String stt = req.getParameter("stt");
         int ct=0;
         String sql = "Insert into gt_srch_rslt (stk_idn, vnm , stt ) " + 
                     " select a.idn , a.vnm , a.stt from mstk a , stk_tly b "+
                     " where a.idn = b.stk_idn and b.stt in ("+stt+") and trunc(nvl(b.iss_dte, sysdate)) = trunc(sysdate) "+
                     " and trunc(nvl(b.rtn_dte, sysdate)) = trunc(sysdate) ";
        ct = db.execUpd("sql", sql, new ArrayList());
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ArrayList ary = new ArrayList();
        ary.add("MEMO_RTRN");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        return SearchResult(am, af, req, res);
        }
    }
    public ActionForward loadSt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"stock tally", "stock tally ");
        String stockTally = "MEMO_PKG.Stk_Tally ";
        ArrayList ary = new ArrayList();
        int ct = db.execCall("stoclTally", stockTally, ary);
        return load(am, af, req, res);
        }
    }
    public ActionForward loadRT(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        StockTallyForm udf = (StockTallyForm)af;
        udf.reset();
          util.updAccessLog(req,res,"stock tally", "stock tally return");
        int ct=0;
     //   ct =db.execUpd("update gt_pkt ", "update gt_pkt set flg='NF' where flg='IS'", new ArrayList());
       String delQMult = " Delete from  gt_srch_multi ";
       ct =db.execUpd(" Del Old Pkts ", delQMult, new ArrayList());
        
        String delQ = " Delete from gt_srch_rslt ";
         ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        session.setAttribute("totalCnt", "0");
        String delpkt = " Delete from gt_pkt ";
         ct =db.execUpd(" Del Old Pkts ", delpkt, new ArrayList());
        ArrayList sttList = util.ReporTypList(req,res);
        udf.setSttList(sttList);
        ArrayList lbSrchList = STGenricSrch(req,res);
        info.setGncPrpLst(lbSrchList);
        session.setAttribute("STT", "");
        HashMap srchVal = (HashMap)session.getAttribute("srchVal");
        if(srchVal!=null){
            String end = (String)req.getAttribute("END");
            if(end!=null){
                for(int i=0 ; i < lbSrchList.size() ; i++){
                    ArrayList prplist =(ArrayList)lbSrchList.get(i);
                    String lprp = (String)prplist.get(0);
                    String fldVal1 = util.nvl((String)srchVal.get(lprp+"_1"));
                    String fldVal2 = util.nvl((String)srchVal.get(lprp+"_2"));
                    udf.setValue(lprp+"_1", fldVal1);
                    udf.setValue(lprp+"_2", fldVal2);
                }
                udf.setValue("stt", util.nvl((String)srchVal.get("stt"))); 
            }
        }
        return am.findForward("loadRT");
        }
    }
    public ActionForward view(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        int ct=0;
        String dsc = "";
        StockTallyForm udf = (StockTallyForm)af;
          util.updAccessLog(req,res,"stock tally", "stock tally view");
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        String stt = util.nvl((String)udf.getValue("stt"));
        String stkCrt = util.nvl((String)udf.getValue("stkCrt"));
        String delQ = " Delete from gt_srch_rslt ";
         ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        String delpkt = " Delete from gt_pkt";
         ct =db.execUpd(" Del Old Pkts ", delpkt, new ArrayList());
        
        HashMap paramsMap = new HashMap();
        if(!stkCrt.equals("0")){
            String getDtls =
                " select a.mprp ,a.vfr , a.vto , a.sfr ,a.sto, b.dta_typ  from stk_crt_dtl a, mprp b where a.mprp = b.prp and a.crt_idn = ? ";
           ArrayList ary = new ArrayList();
           ary.add(stkCrt);
          ArrayList outLst = db.execSqlLst("stkCrt", getDtls, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String lprp = util.nvl(rs.getString("mprp"));
                String sfr = util.nvl(rs.getString("sfr"));
                String sto = util.nvl(rs.getString("sto"));
                paramsMap.put(lprp+"_1", sfr);
                paramsMap.put(lprp+"_2", sto);
            }
            rs.close();
            pst.close();
        }else{
        ArrayList genricSrchLst = info.getGncPrpLst();
            for(int i=0;i<genricSrchLst.size();i++){
                ArrayList prplist =(ArrayList)genricSrchLst.get(i);
                String lprp = (String)prplist.get(0);
                String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                if(fldVal2.equals(""))
                fldVal2=fldVal1;
                if(!fldVal1.equals("") && !fldVal2.equals("")){
                    paramsMap.put(lprp+"_1", fldVal1);
                    paramsMap.put(lprp+"_2", fldVal2);
                    String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
                    if(lprpTyp.equals("C")){
                     ArrayList lprpV = (ArrayList)prp.get(lprp+"V");
                     ArrayList lprpS = (ArrayList)prp.get(lprp + "S");
                     fldVal1 = (String)lprpV.get(lprpS.indexOf(fldVal1));
                     fldVal2 = (String)lprpV.get(lprpS.indexOf(fldVal2));
                        
                    }
                    dsc = dsc+" "+lprp+":"+fldVal1 +"To"+fldVal2+"  ";
                }
             
            }}
            paramsMap.put("stt", stt);
            paramsMap.put("mdl", "MEMO_RTRN");
            util.genericSrch(paramsMap);
        String insQ = " insert into gt_pkt(mstk_idn , flg) select stk_idn , 'IS' from gt_srch_rslt  ";
        ArrayList params = new ArrayList();
        ct = db.execUpd("insertGtPkt", insQ, params);
        String upQ = "update gt_pkt a set flg = 'RT' " +
            " where exists (select 1 from stk_tly b where a.mstk_idn = b.stk_idn " +
            " and trunc(rtn_dte) = trunc(sysdate) and b.stt = 'RT') ";
        ct = db.execUpd("updGtPkt", upQ, params);
        HashMap sttMap = new HashMap();
        String gtPktCount = "select count(*) cnt,  flg  from gt_pkt GROUP BY flg  ";    
          ArrayList outLst = db.execSqlLst("gtCount", gtPktCount, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
          while(rs.next()){
            sttMap.put(rs.getString("flg"), rs.getString("cnt"));
         }
            rs.close();
            pst.close();
        String gtTtlCount = "select count(*) cnt from gt_srch_rslt ";
           outLst = db.execSqlLst("gtCount", gtTtlCount, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while(rs.next()){
           session.setAttribute("totalCnt", rs.getString("cnt"));
        }
        rs.close();
        pst.close();
        req.setAttribute("sttMap", sttMap);
        session.setAttribute("STT", stt);
        udf.reset();
        udf.setTallyDsc(dsc);
        req.setAttribute("TALLY", "Y");
        
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_TALLY");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("STOCK_TALLY");
        allPageDtl.put("STOCK_TALLY",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        session.setAttribute("srchVal",paramsMap);
          util.updAccessLog(req,res,"stock tally", "stock tally view Done");
        return am.findForward("loadRT");
        }
    }
    public ActionForward Return(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        StockTallyForm udf = (StockTallyForm)af;
          util.updAccessLog(req,res,"stock tally", "stock tally return");
        ArrayList ary = null;
        int ct = 0;
        ResultSet rs = null;
        HashMap dbinfo = info.getDmbsInfoLst();
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        String vnm = util.nvl((String)udf.getValue("vnm"));
        String close =  util.nvl((String)udf.getValue("close"));
        String reset = util.nvl((String)udf.getValue("reset"));
        String stt = util.nvl((String)session.getAttribute("STT"));
        HashMap sttMap = new HashMap();
        if(close.equals("")){
        if(!vnm.equals("")){
            String vnm1 = vnm;
            vnm = util.getVnm(vnm);
            vnm1 = vnm1.replaceAll(" ", ",");
            vnm1 = vnm1.replace('\n',',');
            String CodeNF = "";
            String vnm_csv = vnm1;
            
            String delScanPkt = " delete from gt_pkt_scan ";
           
            ct = db.execUpd(" del scan", delScanPkt, new ArrayList());
            if(!vnm.equals("")){
            String[] vnmLst = vnm.split(",");
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
                  
                  toLoc = Math.min(vnm.lastIndexOf(lookupVnm) + lookupVnm.length(), vnm.length());
                  String vnmSub = vnm.substring(fromLoc, toLoc);
                    
            String insScanPkt = " insert  into gt_pkt_scan select vnm from mstk where vnm in ("+ vnmSub + ") ";
          
            ct = db.execUpd(" ins scan", insScanPkt, new ArrayList());
            
            }}
            String getRfidNF = " select vnm from gt_pkt_scan a where not exists (select 1 from mstk b where ((a.vnm = b.tfl3) or (a.vnm = b.vnm))) "; 
                
          ArrayList outLst = db.execSqlLst("Find NF", getRfidNF, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rsNf = (ResultSet)outLst.get(1);
            while(rsNf.next()) {
              CodeNF += ", " + rsNf.getString("vnm") ;
            }
            rsNf.close();
            pst.close();
            if(CodeNF.length() > 1)
              CodeNF = CodeNF.substring(1).trim();
          
            if(!stt.equals(""))
              stt = " and stt = '"+ stt + "'";
            
            stt = "" ;
            String[] vnmList = vnm1.split(",");
            req.setAttribute("vnmLnt", String.valueOf(vnmList.length));
            
            if(!vnm.equals("")){
            String[] vnmLst = vnm.split(",");
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
               
                  int aryLoc = Math.min(i*stepCnt, vnmLst.length-1) ;
                
                  String lookupVnm = vnmLst[aryLoc];
                  if(i == 1)
                      fromLoc = 0 ;
                  else
                      fromLoc = toLoc+1;
                  
                  toLoc = Math.min(vnm.lastIndexOf(lookupVnm) + lookupVnm.length(), vnm.length());
                  String vnmSub = vnm.substring(fromLoc, toLoc);
                    
                  String vnmQry = vnmSub ;
                
                String updateGTPkt = "update gt_pkt set flg='RT' where flg = 'IS' and mstk_idn in ("+
                " select idn from mstk where 1 = 1 "+ stt + " and ( vnm in ("+vnmQry+") or tfl3 in ("+vnmQry+")))";
                ct = db.execUpd("update", updateGTPkt, new ArrayList());
            
                String insExtPkt = "insert into gt_pkt(mstk_idn, flg) " +
                    "select idn, 'EX' " +
                    " from mstk where 1 = 1 "+ stt + " and ( vnm in ("+vnmQry+") or tfl3 in ("+vnmQry+")) " +
                    " and not exists (select 1 from gt_pkt where gt_pkt.mstk_idn = mstk.idn) ";
                ct = db.execUpd("update", insExtPkt, new ArrayList());
                
                if(ct>0){
                  String delGT = " delete from gt_srch_rslt where flg = 'EX' ";
                  ct = db.execUpd("update",delGT, new ArrayList());
                  String insrtExGt = "Insert into gt_srch_rslt (stk_idn, vnm , stt , flg , cert_lab ) " + 
                  "select idn, vnm ,stt , 'EX' , cert_lab " +
                  " from mstk where 1 = 1 "+ stt + " and ( vnm in ("+vnmQry+") or tfl3 in ("+vnmQry+")) " +
                  " and  exists (select 1 from gt_pkt where flg = 'EX') ";
                  ct = db.execUpd("update",insrtExGt, new ArrayList());
                  
                  String  multiCertPrp = "pkgmkt.pktPrp(pMdl => ?)";
                  ary = new ArrayList();
                  ary.add("MEMO_RTRN");
                  ct = db.execCall(" Srch Prp ",multiCertPrp, ary);
                }

                String updateMstk = "update stk_tly set crt_idn=? , rtn_dte=sysdate  , stt=?  " +
                    " where ( vnm in ("+vnmQry+") or tfl3 in ("+vnmQry+") ) " +
                    " and stk_idn in (select mstk_idn from gt_pkt b where b.flg = 'RT')  ";
                ary = new ArrayList();
                ary.add(String.valueOf(info.getSrchId()));
                ary.add("RT");
                ct = db.execUpd("update", updateMstk, ary);

            }
            }
            
            String gtPktCount = "select count(*) cnt,  flg  from gt_pkt GROUP BY flg  ";
            rs = db.execSql("gtCount", gtPktCount, new ArrayList());
          outLst = db.execSqlLst("gtCount", gtPktCount, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                sttMap.put(rs.getString("flg"), rs.getString("cnt"));
            }
            rs.close();
            pst.close();
            sttMap.put("NF", CodeNF);       
        }else if(!reset.equals("")){
            if(!vnm.equals("")){
            String[] vnmLst = vnm.split(",");
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
               
                  int aryLoc = Math.min(i*stepCnt, vnmLst.length-1) ;
                
                  String lookupVnm = vnmLst[aryLoc];
                  if(i == 1)
                      fromLoc = 0 ;
                  else
                      fromLoc = toLoc+1;
                  
                  toLoc = Math.min(vnm.lastIndexOf(lookupVnm) + lookupVnm.length(), vnm.length());
                  String vnmSub = vnm.substring(fromLoc, toLoc);
                     
            String updateMstk = "update stk_tly set crt_nme=? , rtn_dte=sysdate  , stt=?  " +
                " where ( vnm in ("+vnmSub+") or tfl3 in ("+vnmSub+") ) " +
                " and stk_idn in (select mstk_idn from gt_pkt b where b.flg = 'RT')  ";
            //if(!stt.equals(""))
                //updateMstk = updateMstk+ "and stt='"+stt+"'" ;
            ary = new ArrayList();
            ary.add(String.valueOf(info.getSrchId()));
            ary.add("RT");
            ct = db.execUpd("update", updateMstk, ary);
            }}
            String gtPktCount = "select count(*) cnt,  flg  from gt_pkt GROUP BY flg  ";
          ArrayList outLst = db.execSqlLst("gtCount", gtPktCount, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                sttMap.put(rs.getString("flg"), rs.getString("cnt"));
            }
            rs.close();
            pst.close();
            
        }
        req.setAttribute("TALLY", "Y");
        
        udf.reset();
        
        req.setAttribute("sttMap", sttMap);
        } else{
            req.setAttribute("END", "Y");
            loadRT(am, af, req, res);
        }
          util.updAccessLog(req,res,"stock tally", "stock tally return done");
        return am.findForward("loadRT");
        }
    }
    public ActionForward TallyCRT(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        StockTallyForm udf = (StockTallyForm)af;
        HashMap pktDtl = new HashMap();
        ArrayList crtList = new ArrayList();
        String stt = util.nvl(req.getParameter("stt"));
        String tallyCrt = "select b.crt_nme , count(*) Cnt, sum(trunc(a.cts, 2)) cts ,  b.stt " + 
        "from mstk a, stk_tly b where a.idn = b.stk_idn and trunc(b.iss_dte) = trunc(sysdate) and stk_stt=? group by b.crt_nme, b.stt order by 1  ";
        ArrayList ary = new ArrayList();
        ary.add(stt);
          ArrayList outLst = db.execSqlLst("tallyCrt", tallyCrt, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
               String cnt = util.nvl(rs.getString("cnt"));
               String cts =  util.nvl(rs.getString("cts"));
               String crt_nme = util.nvl(rs.getString("crt_nme"));
               String tlyStt = util.nvl(rs.getString("stt"));
               pktDtl.put(crt_nme+"_"+tlyStt+"_Q" , cnt);
               pktDtl.put(crt_nme+"_"+tlyStt+"_C" , cts);
               if(!crtList.contains(crt_nme))
                   crtList.add(crt_nme);
         }
        rs.close();
            pst.close();
            req.setAttribute("crtList",crtList);
            req.setAttribute("pktDtl", pktDtl);
        
        
        return am.findForward("TallyCRT");
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
        StockTallyForm udf = (StockTallyForm)af;
        int ct =0;
         ArrayList  stockList = (ArrayList)session.getAttribute("stockList");
         String crtNme = util.nvl((String)udf.getValue("crtNme"));
        for(int i=0;i< stockList.size();i++){
            HashMap stockPkt = (HashMap)stockList.get(i);
             String stkIdn = (String)stockPkt.get("stk_idn");
             String isChecked   = util.nvl((String)udf.getValue(stkIdn));
            if(isChecked.length() > 0){
                String updateMstk = " update stk_tly set crt_nme=? , rtn_dte=sysdate  , stt=? where stk_idn=?  ";
                ArrayList ary = new ArrayList();
                ary.add(crtNme);
                ary.add("RT");
                ary.add(stkIdn);
                ct = db.execUpd("update", updateMstk, ary);
            }
        
        }
        if(ct > 0)
            req.setAttribute("msg", "Changes save successfully...");
        return am.findForward("loadRT");
        }
    }
   
    public ActionForward SearchResult(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      GenericInterface genericInt = new GenericImpl();
      String stt = req.getParameter("stt");
      String gtFlg = "Z";
      String gtTbl =" gt_srch_rslt ";
      if(stt.equals("EX")) {
          //gtTbl =" gt_srch_multi  ";
          gtFlg = "EX";
      }    
      ArrayList pktList = new ArrayList();
      ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "memoPrpList", "MEMO_RTRN");
      String  srchQ =  " select a.stk_idn ,  a.vnm , a.stt  ";

      

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

      
      String rsltQ = srchQ + " from "+gtTbl+" a , gt_pkt b where  b.mstk_idn=a.stk_idn and a.flg =? ";
      if(!stt.equals("ALL"))
         rsltQ = rsltQ+ " and b.flg=? ";
      
      ArrayList ary = new ArrayList();
      ary.add(gtFlg);
      if(!stt.equals("ALL"))
      ary.add(stt);
        ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
      try {
          while(rs.next()) {
            
              String stkIdn = util.nvl(rs.getString("stk_idn"));
            
              HashMap pktPrpMap = new HashMap();
              pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
              String vnm = util.nvl(rs.getString("vnm"));
              pktPrpMap.put("vnm",vnm);
              pktPrpMap.put("stk_idn",stkIdn);
             
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
          pst.close();
      } catch (SQLException sqle) {

          // TODO: Add catch code
          sqle.printStackTrace();
      }

      req.setAttribute("stockList", pktList);
          return am.findForward("loadRS");
      }
      }
    
    public ArrayList STGenricSrch(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        Connection conn=info.getCon();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("STGNCSrch");
        ArrayList stkTlyCrt = (ArrayList)session.getAttribute("STK_TLY_CRT");
        try {
        if(stkTlyCrt == null) {
        stkTlyCrt = new ArrayList();
        
        String getStkTlyCrt = "select crt_idn, dsc from stk_crt where typ = 'STK_TLY' order by dsc";
          ArrayList outLst = db.execSqlLst(" stk tly crt", getStkTlyCrt, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()) {
        ArrayList crtDsc = new ArrayList();
        crtDsc.add(rs.getString("crt_idn"));
        crtDsc.add(rs.getString("dsc"));
        stkTlyCrt.add(crtDsc);
        }
            rs.close();
            pst.close();
        session.setAttribute("STK_TLY_CRT", stkTlyCrt);
        }
        if (asViewPrp == null) {

        asViewPrp = new ArrayList();
      
        
          ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp , flg from rep_prp where mdl = 'TLLY_SRCH' and flg in ('Y','S') order by rnk ",
        new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs1 = (ResultSet)outLst.get(1);
        while (rs1.next()) {
            ArrayList asViewdtl=new ArrayList();
            asViewdtl.add(rs1.getString("prp"));
            asViewdtl.add(rs1.getString("flg"));
            asViewPrp.add(asViewdtl);
        }
            rs1.close();
            pst.close();
        session.setAttribute("STGNCSrch", asViewPrp);
        }

        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
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
                util.updAccessLog(req,res,"stock tally", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"stock tally", "init");
            }
            }
            return rtnPg;
            }
    
}
