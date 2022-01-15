package ft.com.pricing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

//import ft.com.SyncOnRap;
import ft.com.dao.GtPktDtl;
import ft.com.lab.FinalLabSelectionForm;
import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.LinkedHashMap;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PriceChangesAction extends DispatchAction {
   
    public PriceChangesAction() {
        super();
    }
    public ActionForward load(ActionMapping mapping,
                                 ActionForm af,
                                 HttpServletRequest req,
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
          return mapping.findForward(rtnPg);   
      }else{
          util.updAccessLog(req,res,"PriceChanges", "load");
            PriceChangesForm udf = (PriceChangesForm)af;
            udf.reset();
            String premiumLnk = util.nvl(req.getParameter("premiumLnk")).trim();
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("MOD_PRICE_CHANGE");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("MOD_PRICE_CHANGE");
        allPageDtl.put("MOD_PRICE_CHANGE",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            udf.setValue("premiumLnk", premiumLnk);
          util.updAccessLog(req,res,"PriceChanges", "load end");
     return mapping.findForward("load");
        }
    }
    public ActionForward loadApp(ActionMapping mapping,
                                 ActionForm af,
                                 HttpServletRequest req,
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
         return mapping.findForward(rtnPg);   
     }else{
    
     return mapping.findForward("loadAPP");
       }
   }
    
    public ActionForward fetchPC(ActionMapping mapping,
                                 ActionForm af,
                                 HttpServletRequest req,
                                 HttpServletResponse res) throws Exception {
     HttpSession session = req.getSession(false);
     InfoMgr info = (InfoMgr)session.getAttribute("info");
     GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
         return mapping.findForward(rtnPg);   
     }else{
         util.updAccessLog(req,res,"PriceChanges", "fetch");
     SearchQuery srchQuery = new SearchQuery();
     int ct1 = db.execUpd("delete gt","delete from gt_srch_rslt", new ArrayList());
     PriceChangesForm udf = (PriceChangesForm)af;
     String accept = util.nvl((String)udf.getValue("acptAll"));
     String reject =  util.nvl((String)udf.getValue("rjctAll"));
     if(!accept.equals("")){
         ct1 = db.execCall("CNF_ALL", "PRI_PKG.CNF_ALL", new ArrayList());
         req.setAttribute("msg", "Price update successfully...");
     }else if(!reject.equals("")){
         ct1 = db.execCall("REJ_ALL", "PRI_PKG.REJ_ALL", new ArrayList());
         req.setAttribute("msg", "Reject price successfully...");
     }else{
     String frmDte = util.nvl((String)udf.getValue("FrmDte"));
     String toDte = util.nvl((String)udf.getValue("toDte"));
     String frmDiff = util.nvl((String)udf.getValue("frmDiff"));
     String toDiff = util.nvl((String)udf.getValue("toDiff"));
     String vnm =  util.nvl((String)udf.getValue("vnm"));
      String seq =  util.nvl((String)udf.getValue("seq"));
         if(seq.equals("")){
             seq = util.nvl(req.getParameter("lseq"));
             udf.setValue("seq",seq);
         }
     String tbl = "   vlu_tab a, mstk b   ";
     if(!seq.equals(""))
        tbl = "   vlu_tab a, mstk b  , pri_batch c ";
       String sql =
       "    Insert into gt_srch_rslt ( srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt,  rap_rte, cert_lab, cert_no, flg, sk1, fquot, quot , cmp ) " +
       "     select  1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt,  rap_rte" +
       "     , cert_lab, cert_no, 'Z' flg, sk1, upr,a.rte, decode(a.prev_rte, a.rte, cmp , a.prev_rte) " +
       "    from     " +tbl+
       "     where  pkt_ty = 'NR' and a.mstk_idn = b.idn and a.chg_stt='P'  " ;
         if(!seq.equals(""))
      sql=sql+" and c.idn = a.mstk_idn and c.idn = b.idn and c.pri_seq = '"+seq+"' ";
     if(!frmDte.equals("")){
         if(toDte.equals(""))
             toDte = "sysdate";
         sql= sql+" and trunc(a.dte) between to_date('"+frmDte+"', 'dd-mm-rrrr') and to_date('"+toDte+"', 'dd-mm-rrrr') ";
     }
     if(!frmDiff.equals("") && !toDiff.equals("")){
         sql = sql+ " and ((a.rev_rte - a.rte)*100/rte) between "+frmDiff+" and "+toDiff ;
     }
     if(!vnm.equals("")){
         vnm = util.getVnm(vnm);
         sql = sql +" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+") )";
     }
         System.out.println(sql);
     int ct = db.execUpd("gtInsert", sql, new ArrayList());
       String pktPrp = "pkgmkt.pktPrp(0,?)";
       ArrayList ary = new ArrayList();
       ary.add("PRC_PRP");
      ct = db.execCall(" Srch Prp ", pktPrp, ary);
       ArrayList prcPrpLst = srchQuery.getPRCPRPMdl(req,res);
       HashMap pktList = srchQuery.SearchResult(req,res,"'Z'",prcPrpLst);
       
         String lstNme = "PRICHNG_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
         gtMgr.setValue(lstNme+"_SEL",new ArrayList());
         gtMgr.setValue(lstNme, pktList);
         gtMgr.setValue("lstNmePRICHG", lstNme);
      
       req.setAttribute("view", "Y");
     }
       udf.setValue("acptAll", "");
       udf.setValue("rjctAll", "");
       udf.setValue("fetch", "");
         util.updAccessLog(req,res,"PriceChanges", "fetch end");
       return mapping.findForward("loadAPP");
       }
     
   }
    public ActionForward priceDtl(ActionMapping mapping,
                                 ActionForm af,
                                 HttpServletRequest req,
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
           return mapping.findForward(rtnPg);   
       }else{
           util.updAccessLog(req,res,"PriceChanges", "price dtl");
      String stkIdn = req.getParameter("mstkIdn");
     String sql= "select grp, nw_val, old_val, ref_nme from ( select idn, prmtyp, grp_srt , "+
                 " decode(prmtyp, 'REF', 0, 'DP', vlu, nvl(vlu,0)+nvl(pct,0)) nw_val , "+
                 " decode(prmtyp, 'REF', 0, 'DP', old_vlu, nvl(old_vlu,0)+nvl(old_pct,0)) old_val , grp, ref_nme "+
                 " from itm_prm_dis where mstk_idn = ? and prmtyp <> 'REF') a where (prmtyp = 'DP') or (old_val <> nw_val) "+
                 " order by grp_srt ";

     ArrayList ary = new ArrayList();
     ary.add(stkIdn);
           ArrayList outLst = db.execSqlLst("sql", sql, ary);
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet rs = (ResultSet)outLst.get(1);
     ArrayList pktList = new ArrayList();
     while(rs.next()){
         HashMap pktDtl = new HashMap();
         pktDtl.put("grp", util.nvl(rs.getString("grp")));
         pktDtl.put("nw_val", util.nvl(rs.getString("nw_val")));
         pktDtl.put("old_val", util.nvl(rs.getString("old_val")));
         pktDtl.put("ref_nme", util.nvl(rs.getString("ref_nme")));
         pktList.add(pktDtl);
     }
         rs.close();
           pst.close();
     req.setAttribute("pktList", pktList);
           util.updAccessLog(req,res,"PriceChanges", "price dtl");
     return mapping.findForward("priceDtl");
         }
     }
    public ActionForward savePC(ActionMapping mapping,
                                 ActionForm af,
                                 HttpServletRequest req,
                                 HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
          return mapping.findForward(rtnPg);   
      }else{
          util.updAccessLog(req,res,"PriceChanges", "save");
        PriceChangesForm udf = (PriceChangesForm)af;
        String accept = util.nvl((String)udf.getValue("acptAll"));
        String reject =  util.nvl((String)udf.getValue("rjctAll"));
        if(!accept.equals("")){
           int ct1 = db.execCall("CNF_ALL", "PRI_PKG.CNF_ALL(pGT=>'Y')", new ArrayList());
            if(ct1 > 0)
            req.setAttribute("msg", "Price update successfully...");
            else
            req.setAttribute("msg", "Error in process");
        }else if(!reject.equals("")){
          int  ct1 = db.execCall("REJ_ALL", "PRI_PKG.REJ_ALL(pGT=>'Y')", new ArrayList());
            if(ct1 > 0)       
            req.setAttribute("msg", "Reject price successfully...");
            else
            req.setAttribute("msg", "Error in process");
        }else{
        String lstNme = (String)gtMgr.getValue("lstNmePRICHG");
        HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
        ArrayList stockIdnLst =new ArrayList();
      Set<String> keys = stockList.keySet();
         for(String key: keys){
            stockIdnLst.add(key);
            }
        int count=0;
        for(int i=0;i< stockIdnLst.size();i++){
        String stkIdn = (String)stockIdnLst.get(i);
            String updflg = util.nvl((String)udf.getValue("PC_"+stkIdn));
            if(!updflg.equals("NN") && !updflg.equals("")){
                String updvlu_tab = "PRI_PKG.CNF_PRI_CHNG(pStkIdn => ?, pStt => ?, pCnt => ?)";
                ArrayList ary = new ArrayList();
                ary.add(stkIdn);
                ary.add(updflg);
                ArrayList out =new ArrayList();
                out.add("I");
              CallableStatement ct = db.execCall("update", updvlu_tab, ary, out);
              int cnt = ct.getInt(ary.size()+1);
              count = count+cnt;
              ct.close();
          }
        }
               
        if(count > 0)
        req.setAttribute("msg", "Number of Packet updated =>"+count);
        else
         req.setAttribute("msg", "Error in process");
                            
            }
        udf.setValue("acptAll", "");
        udf.setValue("rjctAll", "");
        udf.setValue("submit", "");
          util.updAccessLog(req,res,"PriceChanges", "save end");
        return mapping.findForward("loadAPP");
        }
    }
    
    public ActionForward view(ActionMapping mapping,
                                 ActionForm af,
                                 HttpServletRequest req,
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
          return mapping.findForward(rtnPg);   
      }else{
          util.updAccessLog(req,res,"PriceChanges", "view");
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
      db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
        db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
     PriceChangesForm udf = (PriceChangesForm)af;
            String    premiumLnk = util.nvl((String) udf.getValue("premiumLnk"));
        ArrayList params = new ArrayList();
      String memoId = util.nvl((String)udf.getValue("memoIdn"));
      String vnmLst = util.nvl((String)udf.getValue("vnmLst"));
      String priceSql = "";
      if(!memoId.equals("")){
         priceSql = "Insert into gt_srch_rslt ( stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , cmp , rap_dis , rap_rte,quot)  " +
              "select  b.mstk_idn ,c.vnm , c.dte , c.stt , 'Z' , c.qty , c.cts , c.sk1 , nvl(c.upr, c.cmp) ,  decode(c.rap_rte ,'1',null, trunc((nvl(c.upr,c.cmp)/c.rap_rte*100)-100, 2)) , c.rap_rte, nvl(c.upr, c.cmp) from mjan a , jandtl b , mstk c " + 
          "where a.idn = b.idn and b.mstk_idn = c.idn and c.stt in ('MKAV','MKIS','MKEI','SHIS','BRAV','MKKS_IS','MKOS_IS') " + 
          "and a.idn = ? ";
          if(!vnmLst.equals("")){
              vnmLst = util.getVnm(vnmLst);
              priceSql = priceSql+" and  ( c.vnm in ("+vnmLst+") or c.tfl3 in ("+vnmLst+") )";
          }
          if(cnt.equals("asha")){
              priceSql = "Insert into gt_srch_rslt ( stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , cmp , rap_dis , rap_rte,quot)  " +
                   "select  b.mstk_idn ,c.vnm , c.dte , c.stt , 'Z' , c.qty , c.cts , c.sk1 , nvl(c.upr, c.cmp) ,  decode(c.rap_rte ,'1',null, trunc((nvl(c.upr,c.cmp)/c.rap_rte*100)-100, 2)) , c.rap_rte, nvl(c.upr, c.cmp) from mjan a , jandtl b , mstk c,df_stk_stt d " + 
               "where a.idn = b.idn and b.mstk_idn = c.idn and c.stt=d.stt and d.grp2='MKT' " + 
               "and a.idn = ? ";
               if(!vnmLst.equals("")){
                   vnmLst = util.getVnm(vnmLst);
                   priceSql = priceSql+" and  ( c.vnm in ("+vnmLst+") or c.tfl3 in ("+vnmLst+") )";
               }
          }
      params.add(memoId);
      }else{
          if(!vnmLst.equals("")){
              vnmLst = util.getVnm(vnmLst);
              priceSql = "Insert into gt_srch_rslt ( stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 ,cmp , rap_dis , rap_rte ,quot)  " +
                " select  c.idn ,c.vnm , c.dte , c.stt , 'Z' , c.qty , c.cts , c.sk1 ,nvl(c.upr, c.cmp) ,  decode(c.rap_rte ,'1',null, trunc((nvl(c.upr,c.cmp)/c.rap_rte*100)-100, 2)) ,  c.rap_rte, nvl(c.upr, c.cmp) from  mstk c " + 
                " where  c.stt in ('MKAV','MKIS','MKEI','SHIS','BRAV') " + 
                " and ( c.vnm in ("+vnmLst+") or c.tfl3 in ("+vnmLst+") )";
              if(cnt.equals("asha")){
                  priceSql = "Insert into gt_srch_rslt ( stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 ,cmp , rap_dis , rap_rte ,quot)  " +
                    " select  c.idn ,c.vnm , c.dte , c.stt , 'Z' , c.qty , c.cts , c.sk1 ,nvl(c.upr, c.cmp) ,  decode(c.rap_rte ,'1',null, trunc((nvl(c.upr,c.cmp)/c.rap_rte*100)-100, 2)) ,  c.rap_rte, nvl(c.upr, c.cmp) from  mstk c,df_stk_stt d " + 
                    " where  c.stt=d.stt and d.grp2='MKT' " + 
                    " and ( c.vnm in ("+vnmLst+") or c.tfl3 in ("+vnmLst+") )";
              }
          }
      }
      int ct = db.execUpd("insert into gt", priceSql, params);
      if(ct > 0){
          String pktPrp = "pkgmkt.pktPrp(0,?)";
           params = new ArrayList();
          params.add("MEMO_RTRN");
          ct = db.execCall(" Srch Prp ", pktPrp, params);
          insertGtmemo(req,res);
          ArrayList pktList = SearchResult(req,res, vnmLst,udf);
          
          String getMemoInfo = "Select Trunc(Sum(Quot*Cts),2) vlu,Trunc(Sum(Quot*Cts)/Sum(Cts),2) avg,\n" + 
          "trunc(((sum(trunc(cts,2)*Quot) / sum(trunc(cts,2)* greatest(rap_rte,1) ))*100) - 100, 2) avg_dis,\n" + 
          "         Trunc(Sum(Cmp*Cts),2) cmpvlu,Trunc(Sum(Cmp*Cts)/Sum(Cts),2) cmpavg,\n" + 
          "         trunc(((sum(trunc(cts,2)*cmp) / sum(trunc(cts,2)* greatest(rap_rte,1) ))*100) - 100, 2) cmpavg_dis\n" + 
          "         from gt_srch_rslt  ";
        ArrayList outLst = db.execSqlLst(" Memo Info", getMemoInfo, new ArrayList());      
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
          if (rs.next()) {
              udf.setAvgDis(rs.getString("avg_dis"));
              udf.setVlu(rs.getString("vlu"));
              udf.setAvg(rs.getString("avg"));
          }
          rs.close();
          pst.close();
          session.setAttribute("pktList", pktList);
          req.setAttribute("view", "Y");
      }
            udf.setValue("premiumLnk", premiumLnk);
          util.updAccessLog(req,res,"PriceChanges", "view end");
     return mapping.findForward("load");
        }
    }
    public ActionForward save(ActionMapping mapping,
                                 ActionForm af,
                                 HttpServletRequest req,
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
          return mapping.findForward(rtnPg);   
      }else{
          util.updAccessLog(req,res,"PriceChanges", "save");
     PriceChangesForm udf = (PriceChangesForm)af;
     String    premiumLnk = util.nvl((String) udf.getValue("premiumLnk"));
     ArrayList pktList = (ArrayList)session.getAttribute("pktList");
     ArrayList ary = new ArrayList();
     HashMap dbinfo = info.getDmbsInfoLst();
     String rapsync = util.nvl((String)dbinfo.get("RAPSYNC"));
     String cnt = util.nvl((String)dbinfo.get("CNT"));
     for(int i=0; i < pktList.size();i++){
         HashMap pktDtl = (HashMap)pktList.get(i);
         String stkIdn = (String)pktDtl.get("stk_idn");
         String priceFld = "nwprice_"+stkIdn;
         String uprPrc = (String)udf.getValue(priceFld);
         String uprDis = (String)udf.getValue("nwdis_"+stkIdn);
         if(!(uprDis.equals("")) && !(uprPrc.equals("") && !(uprPrc.equals("0")))){
          String updateMstk = "update mstk set upr = ? , upr_dis = ? where idn=? ";
          ary = new ArrayList();
          ary.add(uprPrc);
          ary.add(uprDis);
          ary.add(stkIdn);
          int ct = db.execUpd("updateMstk", updateMstk, ary);
         }
     }
//        if(rapsync.equals("Y"))
//        new SyncOnRap(cnt).start();
//        String[] arguments = new String[] {cnt};
//        SyncOnRap.main(arguments);
          udf.setValue("premiumLnk", premiumLnk);
            util.updAccessLog(req,res,"PriceChanges", "save end");
     req.setAttribute("msg", "Price update successfully...");
     return mapping.findForward("load");
        }
    }
    public void insertGtmemo(HttpServletRequest req,HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        String delQ = " Delete from gt_memo_pri_chng ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        String srchRefQ = "Insert Into Gt_Memo_Pri_Chng(Memo_Id, Exh_Rte, Mstk_Idn, Cts, Rap_Rte, Rte, Quot) " + 
        "        Select 0, Nvl(A.Exh_Rte, 1), A.Stk_Idn, Trunc(A.Cts, 2) Cts, A.Rap_Rte,A.Cmp,A.Quot " + 
        "        from gt_srch_rslt A";
        

        ct = db.execUpd(" Insert gt_memo_pri_chng", srchRefQ, new ArrayList());
    }
    public ArrayList SearchResult(HttpServletRequest req , HttpServletResponse res, String vnmLst,PriceChangesForm udf ){
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
        ArrayList vwPrpLst = getMemoRtn(req,res);
        String cpPrp = "prte";
        if(vwPrpLst.contains("CP"))
        cpPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("CP")+1);
        String  srchQ =  " select a.mstk_idn,b.stk_idn , b.pkt_ty,  b.vnm, b.pkt_dte, b.stt , b.qty , b.cts , b.rmk , b.cmp , b.rap_dis ,  b.rap_rte,trunc(((greatest(nvl(b."+cpPrp+",b.prte),1)*100)/greatest(b.rap_rte,1)) - 100,2) cpdis " +
            ", b.quot,a.byr_dis , a.rap_vlu , a.quot_vlu";

        

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

        
        String rsltQ = srchQ + " from gt_memo_pri_chng a , gt_srch_rslt b " +
            " where a.mstk_idn = b.stk_idn and b.flg =? order by b.sk1";
        
        ArrayList ary = new ArrayList();
        ary.add("Z");
      ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {

                HashMap pktPrpMap = new HashMap();
                long   pktIdn = rs.getLong("mstk_idn");
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                    pktPrpMap.put("vnm",vnm);
                    pktPrpMap.put("upr",util.nvl(rs.getString("cmp")));
                    pktPrpMap.put("uprDis",util.nvl(rs.getString("rap_dis")));
                    pktPrpMap.put("rap_rte",util.nvl(rs.getString("rap_rte")));
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
                    udf.setValue("nwprice_"+pktIdn, util.nvl(rs.getString("quot")));
                    udf.setValue("nwdis_"+pktIdn, util.nvl(rs.getString("byr_dis")));
                    udf.setValue("rapVal_"+pktIdn, util.nvl(rs.getString("rap_vlu")));
                    udf.setValue("val_"+pktIdn, util.nvl(rs.getString("quot_vlu")));
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                     
                             if(prp.equals("CP_DIS"))
                                 val = util.nvl(rs.getString("cpdis"));
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
        if(!vnmLst.equals("")){
            vnmLst = util.pktNotFound(vnmLst);
            req.setAttribute("vnmNotFnd", vnmLst);
        }

        return pktList;
    }
    
    public HashMap SearchResultGT(HttpServletRequest req ,HttpServletResponse res, ArrayList vwPrpLst ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
     
    HashMap pktList = new HashMap();
    
    String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts ,cmp,quot, cert_lab , rmk ";

    

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
      ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    try {
        while(rs.next()) {
                GtPktDtl pktPrpMap = new GtPktDtl();
            String cert_lab = util.nvl(rs.getString("cert_lab"));
            String stkIdn = util.nvl(rs.getString("stk_idn"));
          
            pktPrpMap.setValue("stk_idn",stkIdn);
            pktPrpMap.setValue("qty",util.nvl(rs.getString("qty")));
            pktPrpMap.setValue("cts",util.nvl(rs.getString("cts")));
            pktPrpMap.setValue("cmp",util.nvl(rs.getString("cmp")));
            pktPrpMap.setValue("quot",util.nvl(rs.getString("quot")));
                
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
                          
                pktList.put(stkIdn,pktPrpMap);
            }
        rs.close();
        pst.close();
    } catch (SQLException sqle) {

        // TODO: Add catch code
        sqle.printStackTrace();
    }
    
    return pktList;
    }
    
    public ArrayList  getMemoRtn(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList  repMemoLst = new ArrayList();
        repMemoLst =(ArrayList)session.getAttribute("memoPrpList");
        if(repMemoLst==null){
            repMemoLst = new ArrayList();
        String rep_prpVw = "select prp from rep_prp where mdl='MEMO_RTRN' and flg='Y' order by srt, rnk";
          ArrayList outLst = db.execSqlLst("rep_prp",rep_prpVw, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs1 = (ResultSet)outLst.get(1);

        try {
            while (rs1.next()) {
                repMemoLst.add(rs1.getString("prp"));
            }
            rs1.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        }
        session.setAttribute("memoPrpList", repMemoLst);
        
       return repMemoLst;
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
          util.updAccessLog(req,res,"Assort Issue Action", "init");
          }
          }
          return rtnPg;
          }
}
