package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

//import ft.com.SyncOnRap;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class StockPrpUpdAction extends DispatchAction {
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
        StockPrpUpdForm udf = (StockPrpUpdForm)af;
        SearchQuery srchQuery=new SearchQuery();
        util.updAccessLog(req,res,"stock properties update", "stock properties update load");
        ArrayList  prpUpdList = srchQuery.getPRPUPGrp(req , res,"PRP_UPD");
          info.setValue("prpUpdLst", prpUpdList);
        
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("BULK_PRP_UPD");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("BULK_PRP_UPD");
        allPageDtl.put("BULK_PRP_UPD",pageDtl);
        }
        info.setPageDetails(allPageDtl);
          util.updAccessLog(req,res,"stock properties update", "stock properties update load done");
        return am.findForward("load");
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
        StockPrpUpdForm udf = (StockPrpUpdForm)af;
        SearchQuery srchQuery=new SearchQuery();
        util.updAccessLog(req,res,"stock properties update", "stock properties update view");
        String stoneId =(String)udf.getValue("vnmLst");
        String memoNo = (String)udf.getValue("memoIdn");
        
      if(stoneId.equals(""))
          stoneId = "0";
     
      if(memoNo.equals(""))
          memoNo = "0" ;
      stoneId = util.getVnm(stoneId);
      
      
     
      memoNo = util.getVnm(memoNo);
      
      
      
      String delQ = " Delete from gt_srch_rslt";
      int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
      
      String srchRefQ = 
      "    Insert into gt_srch_rslt ( srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis ) " + 
      "     select  1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt, nvl(upr, cmp) rte, rap_rte" +
      "     , cert_lab, cert_no, 'Z' flg, sk1, nvl(upr, cmp),  decode(rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2)) " + 
      "    from mstk b " +
      "     where stt <> 'MKSL' " + 
      "    and pkt_ty in('NR','SMX')" + 
      "    and ( vnm in ("+stoneId+") or tfl3 in ("+stoneId+") )  ";
      
      if(memoNo.length() > 3) {
          srchRefQ = 
          "    Insert into gt_srch_rslt (srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis ) " + 
          "                select 1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt" +
              " , nvl(upr, cmp) rte, rap_rte, cert_lab, cert_no, 'Z' flg, sk1, nvl(upr, cmp),  decode(rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2)) " + 
          "                from mstk b where stt <> 'MKSL' " + 
          "                and pkt_ty in('NR','SMX') " + 
          " and exists (select 1 from jandtl a where a.mstk_idn = b.idn and a.idn in ("+ memoNo + "))";
                  
          
      }
      ArrayList params = new ArrayList();
     
    
      //params.add(stoneId);
      //params.add(reportNo);
      System.out.println(srchRefQ + " : " + params.toString());
      
      ct = db.execUpd(" Srch Ref ", srchRefQ, params);
//      String calQuot = "pkgmkt.Cal_Quot(?)";
//      params = new ArrayList();
//      params.add(Integer.toString(info.getRlnId()));
//      ct = db.execCall(" Srch calQ ", calQuot, params);
      
      String pktPrp = "pkgmkt.pktPrp(0,?)";
      params = new ArrayList();
      params.add("PRP_UPD_VIEW");
      ct = db.execCall(" Srch Prp ", pktPrp, params);
      
        String updProp= "pkgmkt.updProp(pMdl => ?)";
        params = new ArrayList();
        params.add("PRP_UPD_VIEW");
        ct = db.execCall(" Srch Prp ", updProp, params);
        
      ArrayList prpUpdList = (ArrayList)info.getValue("prpUpdViewLst");
      if(prpUpdList==null){
        prpUpdList = srchQuery.getPRPUPViewGrp(req,res);
        info.setValue("prpUpdViewLst", prpUpdList);
      }
      req.setAttribute("vnmLst", stoneId);
      HashMap pktList = srchQuery.SearchResult(req,res,"'Z'",prpUpdList);
      req.setAttribute("pktList", pktList);
          util.updAccessLog(req,res,"stock properties update", "stock properties update view done pkt size "+pktList.size());
        return am.findForward("view");
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
         StockPrpUpdForm udf = (StockPrpUpdForm)af;
         SearchQuery srchQuery=new SearchQuery();
         util.updAccessLog(req,res,"stock properties update", "stock properties update save");
         HashMap mprp = info.getMprp();
         String mstkIdn = (String)udf.getValue("mstkIdn");
         String issueId = util.nvl((String)udf.getValue("issueId"));
        ArrayList prpUpdList = (ArrayList)info.getValue("prpUpdLst");
        ArrayList grpList = (ArrayList)session.getAttribute("grpList");
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        String rapsync = util.nvl((String)dbinfo.get("RAPSYNC"));
        if(grpList!=null && grpList.size() >0){
        for(int g=0 ; g<grpList.size() ;g++){
            String grp=(String)grpList.get(g);
        if(grp.equals("1") || cnt.equals("hk")){  
        if(prpUpdList!=null && prpUpdList.size() >0){
            for(int i=0 ; i<prpUpdList.size() ;i++){
            String lmprp = (String)prpUpdList.get(i);
            String mprpTyp = util.nvl((String)mprp.get(lmprp+"T"));
            String updVal = util.nvl((String)udf.getValue(lmprp+"_"+grp));
           if(!updVal.equals("") && !updVal.equals("0")){
            
            String lab = (String)udf.getValue("lab_"+grp);
            ArrayList ary = new ArrayList();
            ary.add(mstkIdn);
            ary.add(lmprp);
            ary.add(updVal);
            ary.add(lab);
            ary.add(mprpTyp);
            ary.add(grp);
            String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?, pPrpTyp => ?, pgrp => ? )";
            int ct = db.execCall("stockUpd",stockUpd, ary);
            if(lmprp.equals("LAB")){
                ary = new ArrayList();
                ary.add(updVal);
                ary.add(mstkIdn);
                ary.add(grp);
                ary.add(updVal);
             String sql= "update stk_dtl set lab = ? where mstk_idn = ? and grp = ? and lab <> ?";
             db.execUpd("update stkDtl", sql, ary);
            }
            if(!issueId.equals("")){
                ary = new ArrayList();
                ary.add(issueId);
                ary.add(mstkIdn);
                ary.add(lmprp);
                ary.add(updVal);
                 ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
                
            }
            }}
        }}  
        }
        }
        ArrayList ary1 = new ArrayList();
         ary1.add(mstkIdn);
        int ct =  db.execCall("stockUpd","STK_RAP_UPD(pIdn => ?)", ary1);
        
//        if(rapsync.equals("Y"))
//         new SyncOnRap(cnt).start();
        
        udf.setMsg("Propeties Get update successfully");
           util.updAccessLog(req,res,"stock properties update", "stock properties update save Done");
        stockUpd(am, af , req, res);
         return am.findForward("update");
         }
     }
    
    
    public ActionForward bulksave(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
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
         StockPrpUpdForm udf = (StockPrpUpdForm)af;
         SearchQuery srchQuery=new SearchQuery();
         HashMap mprp = info.getMprp();
             boolean isUp=false;
         util.updAccessLog(req,res,"stock properties update", "stock properties bulk Save");
//         String mstkIdn = (String)udf.getValue("mstkIdn");
         String issueId = util.nvl((String)udf.getValue("issueId"));
         String mstkIdn="";
         String selectidn = "select stk_idn , cert_lab from gt_srch_rslt where flg='S'";//--Mayur
           ArrayList outLst = db.execSqlLst("Selectidn", selectidn, new ArrayList());
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet rs = (ResultSet)outLst.get(1);
         try{
         while(rs.next()){
             isUp=true;
            mstkIdn=util.nvl(rs.getString("stk_idn"));
           String lab = util.nvl(rs.getString("cert_lab"));
            ArrayList prpUpdList = (ArrayList)info.getValue("prpUpdLst");
            if(prpUpdList!=null && prpUpdList.size() >0){
                for(int i=0 ; i<prpUpdList.size() ;i++){
                String lmprp = (String)prpUpdList.get(i);
                String mprpTyp = util.nvl((String)mprp.get(lmprp+"T"));
                String updVal = util.nvl((String)udf.getValue(lmprp));
               if(!updVal.equals("") && !updVal.equals("0")){
                ArrayList ary = new ArrayList();
                ary.add(mstkIdn);
                ary.add(lmprp);
                ary.add(updVal);
                ary.add(lab);
                ary.add(mprpTyp);
                String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?, pPrpTyp => ? )";
                int ct = db.execCall("stockUpd",stockUpd, ary);
               
                }}
                }
                ArrayList ary1 = new ArrayList();
                ary1.add(mstkIdn);
                int ct =  db.execCall("stockUpd","STK_RAP_UPD(pIdn => ?)", ary1);
             if(ct>=1)
                 req.setAttribute("msg","Propeties Get update successfully");
             }
             rs.close();
             pst.close();
             if(!isUp){
                 
                 String lstNme = (String)gtMgr.getValue("lstNmeSEL");
                 ArrayList selectstkIdnLst = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
                 if(selectstkIdnLst!=null && selectstkIdnLst.size()>0){
                     for(int i=0;i<selectstkIdnLst.size();i++){
                     String stkIdn=(String)selectstkIdnLst.get(i);
                         
                         ArrayList prpUpdList = (ArrayList)info.getValue("prpUpdLst");
                         if(prpUpdList!=null && prpUpdList.size() >0){
                             for(int j=0 ; j<prpUpdList.size() ;j++){
                             String lmprp = (String)prpUpdList.get(j);
                             String mprpTyp = util.nvl((String)mprp.get(lmprp+"T"));
                             String updVal = util.nvl((String)udf.getValue(lmprp));
                            if(!updVal.equals("") && !updVal.equals("0")){
                             ArrayList ary = new ArrayList();
                             ary.add(stkIdn);
                             ary.add(lmprp);
                             ary.add(updVal);
                             ary.add(mprpTyp);
                             String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? ,  pPrpTyp => ? )";
                             int ct = db.execCall("stockUpd",stockUpd, ary);
                            
                             }}
                             }
                             ArrayList ary1 = new ArrayList();
                             ary1.add(stkIdn);
                             int ct =  db.execCall("stockUpd","STK_RAP_UPD(pIdn => ?)", ary1);
                     }
                 }
                 
             }
         }catch (SQLException sqle) {
                 // TODO: Add catch code
                 sqle.printStackTrace();
             } 

        
           util.updAccessLog(req,res,"stock properties update", "stock properties bulk Save done");
       
        return am.findForward("loadBulkPrp");
         }
     }
    
    public ActionForward stockUpd(ActionMapping am , ActionForm fm , HttpServletRequest req , HttpServletResponse res){
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
        
        StockPrpUpdForm udf = (StockPrpUpdForm)fm;
        SearchQuery srchQuery=new SearchQuery();
        String stkIdn = req.getParameter("mstkIdn");
        String mdl = req.getParameter("mdl");
        String viewMdl = "PRP_UPD_DFLT";
        String issueId = util.nvl(req.getParameter("issueID"));
        if(stkIdn==null){
            stkIdn = (String)udf.getValue("mstkIdn");
            mdl = (String)udf.getValue("mdl");
        }
        String reqviewMdl = util.nvl(req.getParameter("viewMdl"));
        if(!reqviewMdl.equals(""))
            viewMdl = reqviewMdl;
        ArrayList  prpUpdList = srchQuery.getPRPUPGrp(req,res,mdl);
        ArrayList grpList = new ArrayList();
        HashMap stockPrpUpd = new HashMap();
        String stockPrp = "select mstk_idn, lab,b.dta_typ, mprp, a.srt, grp , decode(b.dta_typ, 'C', a.val,'N', to_char(a.num), 'D', format_to_date(dte), nvl(txt,'')) val " + 
        "from stk_dtl a, mprp b , rep_prp c " + 
        "where a.mprp = b.prp and mstk_idn =?  and b.dta_typ in ('C','N','T','D') and c.prp = b.prp and c.flg = 'Y' and mdl='"+viewMdl+"' " + 
        "order by grp, psrt, mprp,lab ";
        ArrayList ary = new ArrayList();
        ary.add(stkIdn);
        String pgrp ="";
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
                udf.setValue(mprp+"_"+grp, util.nvl(rs.getString("val")));
                udf.setValue("lab_"+grp, util.nvl(rs.getString("lab")));
            }
            rs.close();
            pst.close();
                String sql="select nvl(upr,cmp) upr,to_char(decode(rap_rte, 1, '', trunc(((nvl(upr, cmp)/greatest(rap_rte,1))*100)-100,2)),9990.99) dis \n" + 
                "from mstk where idn=?";
                ary = new ArrayList();
                ary.add(stkIdn);
           outLst = db.execSqlLst("sql", sql, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
                    while(rs.next()) {
                        stockPrpUpd.put(stkIdn+"_RTE_1", util.nvl(rs.getString("upr")));
                        udf.setValue("RTE_1", util.nvl(rs.getString("upr")));
                        stockPrpUpd.put(stkIdn+"_RAP_DIS_1", util.nvl(rs.getString("dis")));
                        udf.setValue("RAP_DIS_1", util.nvl(rs.getString("dis")));
                    }
                    rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
           

        udf.setValue("mstkIdn", stkIdn);
         udf.setValue("mdl", mdl);
         udf.setValue("issueId", issueId);
           
           
           //Price History start
           ArrayList items = new ArrayList();
           HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
           HashMap pageDtl=(HashMap)allPageDtl.get("BULK_PRP_UPD");
            if(pageDtl==null || pageDtl.size()==0){
           pageDtl=new HashMap();
           pageDtl=util.pagedef("BULK_PRP_UPD");
           allPageDtl.put("BULK_PRP_UPD",pageDtl);
           }
           info.setPageDetails(allPageDtl);
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
           String fld_typ;
           pageList=(ArrayList)pageDtl.get("LINK");
                 if(pageList!=null && pageList.size() >0){
           for(int j=0;j<pageList.size();j++){
                   pageDtlMap=(HashMap)pageList.get(j);
                   fld_typ=(String)pageDtlMap.get("fld_typ");
                   if(fld_typ.equals("PRI_LOG")) {
                       
                           String  lookUpQ = " select a.prc , n.nme , p.rtn_num , b.iss_dt from mprc a , iss_rtn b ,nme_v n, iss_rtn_dtl c,iss_rtn_prp p\n" + 
                               "where a.idn=b.prc_id and a.grp='PRICING'  and b.iss_id=c.iss_id\n" + 
                               "and b.emp_id=n.nme_idn and b.iss_id=p.iss_id and c.iss_stk_idn=p.iss_stk_idn  and p.mprp='RTE'\n" + 
                               "and c.iss_stk_idn= ? order by b.iss_id desc ";

                               ArrayList params = new ArrayList();
                            
                               params.add(stkIdn);
                               ArrayList execLst = db.execSqlLst(" pkt lookup ", lookUpQ, params);
                               pst = (PreparedStatement)execLst.get(0);
                                rs = (ResultSet)execLst.get(1);
                            try {
                                while (rs.next()) {
                                    HashMap dtls = new HashMap();
                                    dtls.put("employee",util.nvl(rs.getString("nme")));

                                    dtls.put("rate",util.nvl(rs.getString("rtn_num")));
                                    items.add(dtls);
                                }
                                rs.close();
                                pst.close();
                                
                                
                                String mstkrmk = "select rmk from mstk where idn=?";
                                 params = new ArrayList();
                                
                                params.add(stkIdn);
                                 execLst = db.execSqlLst(" pkt lookup ", mstkrmk, params);
                                 pst = (PreparedStatement)execLst.get(0);
                                 rs = (ResultSet)execLst.get(1);
                                
                                 while (rs.next()) {
                                   udf.setValue("mstkRmk", util.nvl(rs.getString("rmk")));
                                 }
                                 rs.close();
                                 pst.close();
                                
                            } catch (SQLException sqle) {
                                // TODO: Add catch code
                                sqle.printStackTrace();
                            } finally {

                            }

                       }
               } }   
           
           
           //Price History end
           
         
        PRPUPDDFLTList(req);
        req.setAttribute("stockList",stockPrpUpd);
           req.setAttribute("ITEMS", items);   
        session.setAttribute("grpList", grpList);
        req.setAttribute("mstkIdn", stkIdn);
        info.setValue("prpUpdLst", prpUpdList);
        String grp = util.labGroup();
        req.setAttribute("grp", grp);
        String cutClrGrp = util.CUTCRLGroup();
        req.setAttribute("cutClr", cutClrGrp);
       req.setAttribute("viewMdl", viewMdl);
       req.setAttribute("editmdl", mdl);
      return am.findForward("update");
       }
   }
    
    public ArrayList PRPUPDDFLTList(HttpServletRequest req){
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
        String viewMdl = "PRP_UPD_DFLT";
        String reqviewMdl = util.nvl(req.getParameter("viewMdl"));
        if(!reqviewMdl.equals(""))
            viewMdl = reqviewMdl;
            ArrayList viewPrp = null;
            try {
                if (viewPrp == null) {

                    viewPrp = new ArrayList();
              
                  ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = '"+viewMdl+"' and flg='Y' order by rnk ",
                                   new ArrayList());
                  PreparedStatement pst = (PreparedStatement)outLst.get(0);
                  ResultSet rs1 = (ResultSet)outLst.get(1);
                    while (rs1.next()) {

                       viewPrp.add(rs1.getString("prp"));
                    }
                    rs1.close();
                    pst.close();
                    session.setAttribute("PRPUPDDFLTList", viewPrp);
                }

            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        
        return viewPrp;
    }
    public ActionForward loadPrp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        StockPrpUpdForm udf = (StockPrpUpdForm)af;
        String isMix = util.nvl(req.getParameter("ISMIX"),"N");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("BULK_PRP_UPD");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("BULK_PRP_UPD");
        allPageDtl.put("BULK_PRP_UPD",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        udf.setValue("ISMIX", isMix);
        udf.setValue("pktTyp", isMix);
        return am.findForward("loadPrp");
        }
    }
    public ActionForward Openpop(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        StockPrpUpdForm udf = (StockPrpUpdForm)af;
        SearchQuery srchQuery=new SearchQuery();
        String mdl = util.nvl(req.getParameter("mdl"));
        if(mdl.equals(""))
            mdl="AS_PRC_EDIT";
        ArrayList  prpUpdList = srchQuery.getPRPUPGrp(req , res, mdl);
          info.setValue("prpUpdLst", prpUpdList);
        return am.findForward("loadBulkPrp");
        }
    }
    public ActionForward updatePrp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            StockPrpUpdForm udf = (StockPrpUpdForm)af;
            SearchQuery srchQuery=new SearchQuery();
            GenericInterface genericInt = new GenericImpl();
          util.updAccessLog(req,res,"stock properties update", "stock properties update");
        int ct = db.execCall("delete gt", "delete from gt_file_trf", new ArrayList());
        ArrayList ary = new ArrayList();
        String pktTyp = util.nvl((String)udf.getValue("pktTyp"),"N");
        String lprp = util.nvl((String)udf.getValue("lprp"));
        String vnm = util.nvl((String)udf.getValue("vnmLst"));
        String val = util.nvl((String)udf.getValue("prpVal"));
        String memo = util.nvl((String)udf.getValue("memo"));
        String memoVal = util.nvl((String)udf.getValue("memoValTN"));
        String prpUp = util.nvl((String)udf.getValue("prpUp"));
        String sttUp = util.nvl((String)udf.getValue("sttUp"));
        String rfIdUp = util.nvl((String)udf.getValue("rfIdUp"));
        String appDirPrice = util.nvl((String)udf.getValue("appDirPrice"));
        String view = util.nvl((String)udf.getValue("view"));
        HashMap dbinfo = info.getDmbsInfoLst();
        String rapsync = util.nvl((String)dbinfo.get("RAPSYNC"));
        String cnt = util.nvl((String)dbinfo.get("CNT"));
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "ascallSru", "CALL_SRU_VW");
        HashMap mprp = info.getMprp();
        String prpTyp =util.nvl((String)mprp.get(lprp+"T"));
        String conQ=" and pkt_ty in('NR','SMX') ";
        if(pktTyp.equals("Y"))
            conQ=" and pkt_ty <> 'NR' ";
        if(!vnm.equals("") || !memo.equals("")){
        if(memoVal.equals("") && prpTyp.equals("C"))
        memoVal = util.nvl((String)udf.getValue("memoValC"));
        if(!prpUp.equals("")){
      if(!vnm.equals("") && !val.equals("") ){
        vnm = util.getVnm(vnm);
        if(prpTyp.equals("T"))
          val = util.getValNline(val);
        else
        val = util.getVal(val);
  
       ArrayList vnmList = new ArrayList();
       vnm = vnm.substring(1, vnm.length()-1);
       String[] vnmStr = vnm.split("','");
      
       
        ArrayList valList = new ArrayList();
        val = val.substring(1, val.length()-1);
        String[] valStr =null;
        if(prpTyp.equals("T"))
         valStr = val.split("'#'");
        else
         valStr = val.split("','");
        if(vnmStr.length==valStr.length){
        
            for(int i=0;i<vnmStr.length;i++){
                vnm = vnmStr[i];
               vnm = vnm.replaceAll(",", "");
               vnm = vnm.replaceAll("'", "");
              
             vnmList.add(vnm);
            }
        
            for(int i=0;i<valStr.length;i++){
                val = valStr[i];
                if(!prpTyp.equals("T")){
              val=val.replaceAll(",", "");
              val=val.replaceAll("'", "");
                }
              val = val.trim();
               valList.add(val);
            }
        
        for(int k=0 ;k<vnmList.size();k++){
          String insertGt = "insert into gt_file_trf(stk_idn , lab , mprp , val ,flg) select idn , cert_lab , ? , ? , ? from mstk where vnm=?  "+conQ;
                   insertGt+=" and not exists(select 1 from gt_file_trf b where mstk.idn=b.stk_idn)";
                   ary = new ArrayList();
                   ary.add(lprp);
                   ary.add(valList.get(k));
                   ary.add("N");
                   ary.add(vnmList.get(k));
                   ct = db.execDirUpd("insert gt_file_trf", insertGt, ary);
          }
        if(ct>0){
        ArrayList out = new ArrayList();
        out.add("I");
        out.add("V");

        CallableStatement ct1 = db.execCall("update pkt", "PRP_PKG.BLK_UPD(pCnt => ? , pMsg=> ?)", new ArrayList(), out);
        int count = ct1.getInt(1);
        String msg = ct1.getString(2);
        req.setAttribute("msg","Number of rows updated : "+count);
        req.setAttribute("msg1",msg);
        udf.reset();
        }else{
            req.setAttribute("msg","Update Process failed..");
        }
        }else{
            req.setAttribute("msg","Please check Packets and there corresponding  Values. ");
        }
      }else if(!memo.equals("") && !memoVal.equals("")){
         memo = util.getVnm(memo);
         memoVal = memoVal.trim();
         memo = memo.replaceAll("'", "");
          String insertGt = "insert into gt_file_trf(stk_idn , lab , mprp , val ,flg) " +
              " select b.idn , b.cert_lab , ? ,? , ?  from jandtl a , mstk b where a.mstk_idn = b.idn and a.idn in("+memo+") and b.stt <> 'MKSL' "+conQ;
          ary = new ArrayList();
          ary.add(lprp);
          ary.add(memoVal);
          ary.add("N");
          ct = db.execDirUpd("insert gt_file_trf", insertGt, ary); 
        if(ct>0){
            ArrayList out = new ArrayList();
            out.add("I");
            out.add("V");
            CallableStatement ct1 = db.execCall("update pkt", "PRP_PKG.BLK_UPD(pCnt => ? , pMsg=> ?)", new ArrayList(), out);
            int count = ct1.getInt(1);
            String msg = ct1.getString(2);
            req.setAttribute("msg","Number of rows updated : "+count);
            req.setAttribute("msg1",msg);
            udf.reset();
           }else{
                req.setAttribute("msg","Update Process failed..");
            }
        }else{
          req.setAttribute("msg","Please Specify Packets. ");
        }
         if(vwPrpLst.contains(lprp)){
          String sruQ="select stk_idn from gt_file_trf";
          ResultSet rs = db.execSql("sruQ", sruQ, new ArrayList());
          while(rs.next()){
              ary = new ArrayList();
              ary.add(util.nvl(rs.getString("stk_idn")));
              ct = db.execCall("STK_RAP_UPD", "STK_RAP_UPD(pIdn => ?)", ary);
              if(lprp.equals("CRTWT"))
              ct = db.execCall("SZ_CHG", "SZ_CHG(pktId => ?)", ary);
              ct = db.execCall("STK_SRT", "STK_SRT(Pid => ?)", ary);
          }
          rs.close();
        }
        }else if(!sttUp.equals("")){
         
            vnm = util.getVnm(vnm);
            String stt = util.nvl((String)udf.getValue("stt"));
            if(stt.equals("MKAV")){
              String mkppLst = "select idn , cert_lab from mstk where ( vnm in ("+vnm+") or tfl3 in ("+vnm+") ) and stt='MKPP' ";
              ArrayList outLst = db.execSqlLst("mkppLst", mkppLst, new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
              while(rs.next()){
                  String stk_idn = rs.getString("idn");
                  String lab = rs.getString("cert_lab");
                String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => "+stk_idn+", pPrp => 'TRF_DTE', pVal => to_char(sysdate, 'dd-MON-rrrr'), pGrp => 1, pPrpTyp => 'D', pLab => '"+lab+"')";
                ct = db.execCall("stockUpd",stockUpd, ary);
                
              }
              rs.close();
              pst.close();
            }
            String flg = util.nvl((String)udf.getValue("flg"));
            if(!stt.equals("") && !flg.equals("")){
            String updateMstk = "update mstk set stt=? , flg = ? where ( vnm in ("+vnm+") or tfl3 in ("+vnm+") )"+conQ;
            ary = new ArrayList();
            ary.add(stt);
            ary.add(flg);
            
             ct = db.execUpd("mstk update", updateMstk, ary);
             if(ct>0){
                 req.setAttribute("msg","status get updated...");
                 udf.reset();
             }
            }
        }else if(!rfIdUp.equals("")){
            String delScanPkt = " delete from gt_pkt_scan ";
            ct = db.execUpd(" del scan", delScanPkt, new ArrayList());
            String insertQ="";
            if(!vnm.equals("")){
            vnm = util.getVnm(vnm);
            }else if(!memo.equals("")){
            vnm = util.getVnm(memo);
            insertQ="M";
            }
            
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
            String insScanPkt = " insert  into gt_pkt_scan select idn from mstk where ( vnm in ("+vnmSub+") or tfl3 in ("+vnmSub+") ) "+conQ;
            if(insertQ.equals("M")){
            insScanPkt ="insert  into gt_pkt_scan select b.idn from jandtl a , mstk b where a.mstk_idn = b.idn and a.idn in("+vnmSub+") and b.stt <> 'MKSL'"+conQ;        
            }
            ct = db.execUpd(" ins scan", insScanPkt, new ArrayList());
            }
            
            String updateMstk = "update mstk a set a.tfl3=null where exists (select 1 from gt_pkt_scan b where b.vnm=a.idn)";
            ct = db.execUpd("mstk update", updateMstk, new ArrayList());
            if(ct>0){
            req.setAttribute("msg","Rfid get deleted...");
            udf.reset();
            }
            
        }else if(!appDirPrice.equals("")){ 
            String updateMstk = "";
             if(!memo.equals("")){
                memo = util.getVnm(memo);
                memoVal = memoVal.trim();
                memo = memo.replaceAll("'", "");
                updateMstk = " update mstk set upr = null , upr_dis=trunc(((cmp/rap_rte)*100)-100,2)  " +
                    " where cmp is not null "+conQ+" and idn in (select mstk_idn from jandtl where idn in ("+memo+")) ";
            }else{
                vnm = util.getVnm(vnm);
                
              updateMstk = " update mstk set upr = null , upr_dis=trunc(((cmp/rap_rte)*100)-100,2)  " +
                  " where cmp is not null "+conQ+" and ( vnm in ("+vnm+") or tfl3 in ("+vnm+") ) ";
                
            }
             ct = db.execUpd("update mstk", updateMstk, new ArrayList());
             if(ct>0){
                req.setAttribute("msg","Apply Direct Price Done.");
                 udf.reset();
             }
        } else{
            viewBulk(am, af, req, res);
        }
//            if(rapsync.equals("Y"))
//            new SyncOnRap(cnt).start();
        }else{
            req.setAttribute("msg","Please Specify Packets. ");
        }
          util.updAccessLog(req,res,"stock properties update", "stock properties update done");
          udf.setValue("ISMIX", pktTyp);
          udf.setValue("pktTyp", pktTyp);
        return am.findForward("loadPrp");
        }
    } 
    
 
    public ActionForward viewBulk(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          StockPrpUpdForm udf = (StockPrpUpdForm)af;
          SearchQuery srchQuery=new SearchQuery();
        HashMap pktList= new HashMap();
        util.updAccessLog(req,res,"stock properties update", "stock properties view Bulk update");
        String stoneId =(String)udf.getValue("vnmLst");
        String memoNo = (String)udf.getValue("memo");
        String viewSelPrp = util.nvl((String)udf.getValue("viewSelPrp")); 
          if(stoneId.equals(""))
              stoneId = "0";
          if(memoNo.equals(""))
                memoNo = "0" ;  
          
            stoneId = util.getVnm(stoneId);
            memoNo = util.getVnm(memoNo);
        if(!viewSelPrp.equals("")){
            String lprp = util.nvl((String)udf.getValue("lprp"));
            pktList=SearchResultDirectStkStl(req,res,lprp,stoneId,memoNo);    
        }else{
       
      String delQ = " Delete from gt_srch_rslt where flg in ('Z')";
      int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
      
      String srchRefQ = 
      "    Insert into gt_srch_rslt ( srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis ) " + 
      "     select  1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt, nvl(upr, cmp) rte, rap_rte" +
      "     , cert_lab, cert_no, 'Z' flg, sk1, nvl(upr, cmp), decode(rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2)) " + 
      "    from mstk b " +
      "     where stt <> 'MKSL' " + 
      "    and pkt_ty = 'NR'" + 
      "    and ( b.vnm in ("+stoneId+") or b.tfl3 in ("+stoneId+") )  ";
      
        if(memoNo.length() > 3) {
            srchRefQ = 
            "    Insert into gt_srch_rslt (srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis ) " + 
            "                select 1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt" +
                " , nvl(upr, cmp) rte, rap_rte, cert_lab, cert_no, 'Z' flg, sk1, nvl(upr, cmp),  decode(rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2)) " + 
            "                from mstk b where stt <> 'MKSL' " + 
            "                and pkt_ty = 'NR'" + 
            " and exists (select 1 from jandtl a where a.mstk_idn = b.idn and a.idn in ("+ memoNo + "))";
                    
            
        }
     
      ArrayList params = new ArrayList();
      ct = db.execUpd(" Srch Ref ", srchRefQ, params);
   
      String pktPrp = "pkgmkt.pktPrp(0,?)";
      params = new ArrayList();
      params.add("PRP_UPD_VIEW");
      ct = db.execCall(" Srch Prp ", pktPrp, params);
      ArrayList prpUpdList = (ArrayList)info.getValue("prpUpdViewLst");
      req.setAttribute("srchRef", "vnm");
      req.setAttribute("srchRefVal", stoneId);
      if(prpUpdList==null){
        prpUpdList = srchQuery.getPRPUPViewGrp(req,res);
        info.setValue("prpUpdViewLst", prpUpdList);
      }
      
      pktList = srchQuery.SearchResult(req,res,"'Z'",prpUpdList);
      
      }
      req.setAttribute("pktList", pktList);
      req.setAttribute("view", "Y");
      return am.findForward("loadPrp");
      }
    }
    public HashMap SearchResultDirectStkStl(HttpServletRequest req,HttpServletResponse res, String lprp,String stoneId,String memoNo) {
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
        HashMap pktPrpMap = new HashMap();
        HashMap pktListMap = new HashMap();
        ArrayList pktStkIdnList = new ArrayList();
        ArrayList params = new ArrayList();
        ArrayList viewSelPrp=new ArrayList();
        viewSelPrp.add(lprp);
        
        String srchRefQ = 
        " select pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt, nvl(upr, cmp) rte, rap_rte" +
        "     , cert_lab, cert_no, 'Z' flg, sk1, nvl(upr, cmp), decode(rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2)) " + 
        "    from mstk b " +
        "     where 1=1 " + 
        "    and pkt_ty = 'NR'" + 
        "    and ( b.vnm in ("+stoneId+") or b.tfl3 in ("+stoneId+") )  ";
        
          if(memoNo.length() > 3) {
              srchRefQ = 
              "                select pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt" +
                  " , nvl(upr, cmp) rte, rap_rte, cert_lab, cert_no, 'Z' flg, sk1, nvl(upr, cmp),  decode(rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2)) " + 
              "                from mstk b where stt <> 'MKSL' " + 
              "                and pkt_ty = 'NR'" + 
              " and exists (select 1 from jandtl a where a.mstk_idn = b.idn and a.idn in ("+ memoNo + "))";
                      
              
          }
      ArrayList outLst = db.execSqlLst("search Result", srchRefQ, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                pktPrpMap = new HashMap();
                String   stkIdn = rs.getString("idn");
                String vnm =util.nvl(rs.getString("vnm"));
                pktPrpMap.put("vnm", vnm);
                String getPktPrp =
                    " select a.mprp, decode(b.dta_typ, 'C', a.val, 'N', a.num, 'D', a.dte, a.txt) val \n" + 
                    "From Stk_Dtl A, Mprp B\n" + 
                    "where a.mprp = b.prp and b.prp = ? and a.mstk_idn = ? and a.grp=1  ";
                params = new ArrayList();
                params.add(lprp);
                params.add(stkIdn);
              ArrayList outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, params);
              PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
              ResultSet rs1 = (ResultSet)outLst1.get(1);
                while (rs1.next()) {
                    String lPrp = rs1.getString("mprp");
                    String lVal = rs1.getString("val");
                    pktPrpMap.put(lPrp, lVal);
                }
                rs1.close();
                pst1.close();
                pktListMap.put(stkIdn , pktPrpMap);
                pktStkIdnList.add(stkIdn);
            }
        rs.close();
            pst.close();
        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        }
        req.setAttribute("viewSelPrp", viewSelPrp);
        req.setAttribute("selPrp", "Y");
        req.setAttribute("pktStkIdnList", pktStkIdnList);
        return pktListMap;
    }
    
    public ActionForward saveEmpRemark(ActionMapping am, ActionForm a, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb      = new StringBuffer();
        String remarkVal = util.nvl(req.getParameter("remarkVal"));
        String mstkIdn = util.nvl(req.getParameter("mstkIdn"));
       ArrayList ary =new ArrayList();
        String sql= "update mstk set rmk = ? where idn = ? ";
        ary.add(remarkVal);
        ary.add(mstkIdn);
        int cnt=db.execDirUpd("update stkDtl", sql, ary);  
        String msg="Remark Updation Fail";
        if(cnt>0)
            msg="Remark Updation Successfully";
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<msg>"+msg+"</msg>");
        return null;
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
                util.updAccessLog(req,res,"stock properties update", "init");
            }
            }
            return rtnPg;
            }
    public StockPrpUpdAction() {
        super();
    }
}
