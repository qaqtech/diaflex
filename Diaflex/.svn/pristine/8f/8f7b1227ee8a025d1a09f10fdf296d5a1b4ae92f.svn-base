package ft.com.marketing;

import ft.com.DBMgr;

import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.ExcelUtilObj;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.GtPktDtl;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;
import ft.com.dao.PktDtl;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Enumeration;
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

public class OfferPriceAction extends DispatchAction {
    public OfferPriceAction() {
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
        OfferPriceFrom udf = (OfferPriceFrom)af;
          util.updAccessLog(req,res,"Offer Price", "Offer Price load");
        ArrayList repMemoLst = new ArrayList();
               String rep_prp = "select prp from rep_prp where mdl='MEMO_RTRN' and flg='Y' order by srt, rnk";
          ArrayList  outLst = db.execSqlLst("rep_prp",rep_prp, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
               while(rs.next()){
                   repMemoLst.add(rs.getString("prp"));
               }
        rs.close();
        pst.close();
               session.setAttribute("prpList", repMemoLst);
          util.updAccessLog(req,res,"Offer Price", "Offer Price load done");  
        return am.findForward("load");
        }
    }
    
    public ActionForward pktList(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        OfferPriceFrom udf = (OfferPriceFrom)af;
          util.updAccessLog(req,res,"Offer Price", "Offer Price pkt fetch");
        int memoIdn = udf.getMemoIdn();
        ArrayList pkts        = new ArrayList();
        ArrayList ary = null;
        String getMemoInfo =
        " select sum(a.qty) pcs, trunc(sum(trunc(a.cts,2)),2) cts ,c.nme_idn, c.nmerel_idn , nvl(c.exh_rte,1) exhRte , "+
        " d.byr byr,trunc(sum(nvl(fnl_sal, quot)*trunc(a.cts,2))/sum(trunc(a.cts,2)),2) avg , "+ 
        " sum(trunc(a.cts,2)*nvl(fnl_sal, quot)) ttlVlu, sum(trunc(a.cts,2)*greatest(b.rap_rte,1)) rapVlu,  "+
        " trunc((sum(trunc(a.cts,2)*nvl(fnl_sal, quot))/sum(trunc(a.cts,2)*greatest(b.rap_rte,1))*100)-100,4) avgDis "+ 
        " from jandtl a, mstk b , mjan c , cus_rel_v d  where a.mstk_idn = b.idn and  c.idn = a.idn and c.nmerel_idn=d.rel_idn  "+
        " and c.nme_idn = d.nme_idn "+
        " and b.stt  in ('MKAV','MKPP','MKEI','MKIS','MKTL','MKKS_IS','MKOS_IS','MKOS','MKCS')  and a.idn = ? "+
        " group by c.nme_idn, c.nmerel_idn, d.byr ,c.exh_rte ";
        ary = new ArrayList();
        ary.add(String.valueOf(memoIdn));

          ArrayList  outLst = db.execSqlLst(" Memo Info", getMemoInfo, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  mrs = (ResultSet)outLst.get(1);

        if (mrs.next()) {
            udf.setNmeIdn(mrs.getInt("nme_idn"));
            udf.setRelIdn(mrs.getInt("nmerel_idn"));
            udf.setByr(mrs.getString("byr"));
            udf.setQty(mrs.getString("pcs"));
            udf.setCts(mrs.getString("cts"));
            udf.setRapVlu(mrs.getString("rapVlu"));
            udf.setAvgDis(mrs.getString("avgDis"));
            udf.setVlu(mrs.getString("ttlVlu"));
            udf.setAvg(mrs.getString("avg"));
            udf.setValue("exhRte", mrs.getString("exhRte"));
            udf.setMemoIdn(memoIdn);
            String getPktData =
                    " select mstk_idn, b.vnm , nvl(fnl_sal, quot) memoQuot, quot,  nvl(fnl_sal, quot) byrQuot, fnl_sal"
                    + ", b.cts, nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte, a.stt "
                    + " , trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100 ,2) dis, trunc(((nvl(fnl_sal, quot)/greatest(b.rap_rte,1))*100)-100,2) mDis "
                    + " from jandtl a, mstk b where a.mstk_idn = b.idn and b.stt in ('MKAV','MKPP','MKEI','MKIS','SHIS','MKTL','MKKS_IS','MKOS_IS','MKOS','MKCS') and a.idn = ? order by a.srl, b.sk1 ";
                  ary = new ArrayList();
                  ary.add(String.valueOf(memoIdn));
              ArrayList  outLst1 = db.execSqlLst(" memo pkts", getPktData, ary);
              PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
              ResultSet  rs1 = (ResultSet)outLst1.get(1);
                while (rs1.next()) {
                    PktDtl pkt    = new PktDtl();
                    long   pktIdn = rs1.getLong("mstk_idn");

                    pkt.setPktIdn(pktIdn);
                    pkt.setRapRte(util.nvl(rs1.getString("rap_rte")));
                    pkt.setCts(util.nvl(rs1.getString("cts")));
                    pkt.setRte(util.nvl(rs1.getString("rte")));
                    pkt.setMemoQuot(util.nvl(rs1.getString("memoQuot")));
                    pkt.setByrRte(util.nvl(rs1.getString("quot")));
                    pkt.setFnlRte(util.nvl(rs1.getString("fnl_sal")));
                    pkt.setDis(rs1.getString("dis"));
                    pkt.setByrDis(rs1.getString("mDis"));
                    pkt.setVnm(util.nvl(rs1.getString("vnm")));
                    String lStt = rs1.getString("stt");

                    pkt.setStt(lStt);
                    udf.setValue("stt_" + pktIdn, lStt);

                    String getPktPrp =
                        " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                        + " from stk_dtl a, mprp b, rep_prp c "
                        + " where a.mprp = b.prp and a.grp=1 and  b.prp = c.prp and c.mdl = 'MEMO_RTRN' and a.mstk_idn = ? and grp=1 "
                        + " order by c.rnk, c.srt ";

                    ary = new ArrayList();
                    ary.add(Long.toString(pktIdn));

                  ArrayList  outLst2 = db.execSqlLst(" Pkt Prp", getPktPrp, ary);
                  PreparedStatement pst2 = (PreparedStatement)outLst2.get(0);
                  ResultSet  rs2 = (ResultSet)outLst2.get(1);

                    while (rs2.next()) {
                        String lPrp = rs2.getString("mprp");
                        String lVal = rs2.getString("val");

                        pkt.setValue(lPrp, lVal);
                    }
                    rs2.close();
                    pst2.close();
                    pkts.add(pkt);
                }
                rs1.close();
                pst1.close();
            }
        mrs.close();
          pst.close();
       String offRteSql = "select b.mstk_idn , a.ofr_rte , a.ofr_dis , a.chg_typ , to_char( a.to_dt ,'DD-MM-YYYY ') to_dt , a.diff " +
           "  from web_bid_wl a , jandtl b where b.idn=? and a.mstk_idn = b.mstk_idn and rel_idn = ? and  trunc(nvl(a.to_dt, sysdate)) >= trunc(sysdate) and  a.typ in('BID','LB') and a.stt='A'";
        ary = new ArrayList();
        ary.add(String.valueOf(memoIdn));
        ary.add(String.valueOf(udf.getRelIdn()));
        outLst = db.execSqlLst("offerRte", offRteSql, ary);
        pst = (PreparedStatement)outLst.get(0);
         mrs = (ResultSet)outLst.get(1);
       while(mrs.next()){
           String stk_idn = mrs.getString("mstk_idn");
           udf.setValue("nwprice_"+stk_idn, mrs.getString("ofr_rte"));
           udf.setValue("nwdis_"+stk_idn, mrs.getString("ofr_dis"));
           udf.setValue("typ_"+stk_idn, mrs.getString("chg_typ"));
           udf.setValue("chng_"+stk_idn, mrs.getString("diff"));
           udf.setValue("dte_"+stk_idn, mrs.getString("to_dt"));
       }
        mrs.close();
        pst.close();
       if(pkts.size()> 0)
           udf.setView("yes");
       else
         req.setAttribute("msg", "Sorry there is no stones in this memo");
               
           
        udf.setPkts(pkts);
        
        udf.setMemoIdn(memoIdn);
        session.setAttribute("PKTS", pkts);
       
          util.updAccessLog(req,res,"Offer Price", "Offer Price pkt Size "+pkts.size());
        
        
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
        OfferPriceFrom udf = (OfferPriceFrom)af;
       util.updAccessLog(req,res,"Offer Price", "Offer Price save");
        ArrayList msgList =new ArrayList();
        String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
         String memoIdn="";
        int count=0;
        ArrayList  pkts = (ArrayList)session.getAttribute("PKTS");
         for (int i = 0; i < pkts.size(); i++) {
             PktDtl pkt     = (PktDtl) pkts.get(i);
             long   lPktIdn = pkt.getPktIdn();
             String pktIdn = String.valueOf(lPktIdn);
              memoIdn =String.valueOf(udf.getMemoIdn());
             String prcVal = (String)udf.getValue("nwprice_"+pktIdn);
             String prcDis = (String)udf.getValue("nwdis_"+pktIdn);
             String typVal = (String)udf.getValue("typ_"+pktIdn);
             String chngVal = (String)udf.getValue("chng_"+pktIdn);
             String dateVal = (String)udf.getValue("dte_"+pktIdn);
             int relIdn = udf.getRelIdn();
             String quot = util.nvl(pkt.getByrRte());
             String check = util.nvl((String)udf.getValue(pktIdn));
             if(check.equals("Yes")){
                 count++;
             ArrayList ary = new ArrayList();
             ary.add(pktIdn);
             ary.add(quot);
             ary.add(prcVal);
             ary.add(typVal);
             ary.add(chngVal);
             ary.add(typVal+"_"+chngVal);
             ary.add(dateVal);
             ary.add(String.valueOf(relIdn));
             ary.add("DF");
             ArrayList out = new ArrayList();
             out.add("I");
             out.add("V");
          
            String addOffer = "web_pkg.bid_add( pStkIdn =>? , pQuot => ? , pOfrRte => ? ,  pChgTyp => ? , pDiff => ?, pRmk => ? , pToDte=> ? , pRlnId => ? ,pFlg => ? , pCnt=> ? , pMsg=>? )";
            CallableStatement ct = db.execCall("addOffer", addOffer, ary, out);
             msgList.add(ct.getString(ary.size()+2));
             ct.close();
             }
             
         }
         req.setAttribute("msgList", msgList);
         session.setAttribute("PKTS", new ArrayList());
       util.updAccessLog(req,res,"Offer Price", "Offer Price save done");
             if(cnt.equalsIgnoreCase("PM")  || cnt.equalsIgnoreCase("ASHA")){
                 session.setAttribute("memoId",memoIdn);
            return am.findForward("memo");
             } else {    
       return am.findForward("load");
             }
     }
            }
    
    public ActionForward loadRPT(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Offer Price", "Offer Price loadRPT");
        GenericInterface genericInt = new GenericImpl();
        ArrayList repRPTLst = new ArrayList();
        ResultSet rs=null;
        ArrayList offerSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_OfferSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_OfferSrch"); 
        info.setGncPrpLst(offerSrchList);         
        ArrayList repMemoLst = new ArrayList();
         HashMap dbinfo = info.getDmbsInfoLst();
         String cnt = (String)dbinfo.get("CNT");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("OFFER_HISTORY");
        if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("OFFER_HISTORY");
            allPageDtl.put("OFFER_HISTORY",pageDtl);
        }
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
        String mdl="MEMO_RTRN";
        pageList= ((ArrayList)pageDtl.get("CUST_MEMO_RTRN") == null)?new ArrayList():(ArrayList)pageDtl.get("CUST_MEMO_RTRN");  
                    if(pageList!=null && pageList.size() >0){
                    for(int j=0;j<pageList.size();j++){
                    pageDtlMap=(HashMap)pageList.get(j);
                        mdl=(String)pageDtlMap.get("dflt_val");
                    }
         }
         ArrayList ary = new ArrayList();
         ary.add(mdl);
         String rep_prpVw = "select prp from rep_prp where mdl=? and flg='Y' order by srt, rnk";
          ArrayList  outLst = db.execSqlLst("rep_prp",rep_prpVw, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs1 = (ResultSet)outLst.get(1);
         while(rs1.next()){
             repMemoLst.add(rs1.getString("prp"));
         }
        rs1.close();
        pst.close();
        session.setAttribute("prpList", repMemoLst);
        ArrayList cusLst = new ArrayList();
         String cusLstSql = "select distinct a.byr, a.nme_idn " + 
         "from cus_rel_v a, web_bid_wl b, mstk c " + 
         "where a.nme_idn = b.byr_idn and b.mstk_idn = c.idn " + 
         " and trunc(nvl(b.to_dt, sysdate)) >= trunc(sysdate)  and b.stt='A' and  b.typ in('BD','BID','LB','KS','KO') and c.stt in ('MKAV','MKKS','MKKS_IS','MKPP','MKEI','MKIS','SHIS','MKTL','MKLB','MKBD','MKBDIS','MKKS_IS','MKOS_IS','MKOS','MKCS')" + 
         "order by 1";
         outLst = db.execSqlLst("rep_prp",cusLstSql, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs1 = (ResultSet)outLst.get(1);
         while(rs1.next()){
             ArrayList cus = new ArrayList();
             cus.add(rs1.getString(2));
             cus.add(rs1.getString(1));
             cusLst.add(cus);
         }
        rs1.close();
        pst.close();
         session.setAttribute("cusList", cusLst);
         
        ArrayList empList = new ArrayList();
        String empSql = "select nme_idn, nme from nme_v where typ = 'EMPLOYEE' order by nme";
           outLst = db.execSqlLst("empSql", empSql, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs1 = (ResultSet)outLst.get(1);
        try {
            while (rs1.next()) {
                ArrayList emp = new ArrayList();
                emp.add(rs1.getString("nme_idn"));
                emp.add(rs1.getString("nme"));
                empList.add(emp);
            }
            rs1.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        session.setAttribute("empList", empList);
             

        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Offer Price", "Offer Price loadRPT end");
        return am.findForward("loadRpt");
        }
    }
    
    public ActionForward srch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        OfferPriceFrom udf = (OfferPriceFrom)af;
        SearchQuery     srchQury=new SearchQuery();
            util.updAccessLog(req,res,"Offer Price", "Offer Price srch");
        ArrayList prpRPTList = (ArrayList)session.getAttribute("prpRPTList");
        ResultSet rs = null;
        int ct =0;
        String vnm = util.nvl((String)udf.getValue("vnm"));
        String history = util.nvl((String)udf.getValue("history"));
        String nmeIdn = util.nvl((String)udf.getValue("nmeIdn"));
        String empIdn = util.nvl((String)udf.getValue("empIdn"));
        String frmDte = util.nvl((String)udf.getValue("frmdte"));
        String todte = util.nvl((String)udf.getValue("todte"));
        String  web  = util.nvl((String)udf.getValue("web"));
        String  diaflex= util.nvl((String)udf.getValue("diaflex"));
        String  pepTyp= util.nvl((String)udf.getValue("pepTyp"));
        String delQ = " Delete from gt_srch_rslt";
        ct = db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            String mdl="MEMO_RTRN";
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("OFFER_HISTORY");
            if(pageDtl==null || pageDtl.size()==0){
                pageDtl=new HashMap();
                pageDtl=util.pagedef("OFFER_HISTORY");
                allPageDtl.put("OFFER_HISTORY",pageDtl);
            }
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
            pageList= ((ArrayList)pageDtl.get("CUST_MEMO_RTRN") == null)?new ArrayList():(ArrayList)pageDtl.get("CUST_MEMO_RTRN");  
                        if(pageList!=null && pageList.size() >0){
                        for(int j=0;j<pageList.size();j++){
                        pageDtlMap=(HashMap)pageList.get(j);
                            mdl=(String)pageDtlMap.get("dflt_val");
                        }
             }
        ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_OfferSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_OfferSrch"); 
        info.setGncPrpLst(genricSrchLst);
        //      ArrayList genricSrchLst = info.getGncPrpLst();
        HashMap paramsMap = new HashMap();
        if(!vnm.equals("")){
            srchRef(req,udf);
            if(!history.equals(""))
            paramsMap.put("history", "Y");
        }else{
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
          paramsMap.put("stt", "MKT");
          paramsMap.put("mdl",mdl);
         util.genericSrchGRP_SRCH(paramsMap);
            paramsMap = new HashMap();
            if(!history.equals("")){
                String addonSrch = "DP_OFR_HIS(ptyp =>?, pMdl => ? , pByr=> ? ,pEmpIdn=> ? ,pDteFr => ? , pDteTo=> ?)";
                ArrayList ary = new ArrayList();
                ary.add(pepTyp);
                ary.add(mdl);
                ary.add(nmeIdn);
                ary.add(empIdn);
                ary.add(frmDte);
                ary.add(todte);
                 ct = db.execCall("addonSrch", addonSrch, ary);
                paramsMap.put("history", "Y");
            }else{
        String addonSrch = "DP_OFR_SRCH(ptyp =>?, pMdl => ? , pByr=> ? ,pEmpIdn=> ? ,pDteFr => ? , pDteTo=> ?)";
        ArrayList ary = new ArrayList();
        ary.add(pepTyp);
        ary.add(mdl);
        ary.add(nmeIdn);
        ary.add(empIdn);
        ary.add(frmDte);
        ary.add(todte);
         ct = db.execCall("addonSrch", addonSrch, ary);
            }
        }
      
        paramsMap.put("nmeIdn", nmeIdn);
        paramsMap.put("empIdn", empIdn);
        paramsMap.put("frmDte", frmDte);
        paramsMap.put("toDte", todte);
       
        paramsMap.put("pepTyp", pepTyp);
        if(!web.equals(""))
        paramsMap.put("web","WEB");
        if(!diaflex.equals(""))
        paramsMap.put("diaflex","DF");
         ArrayList pktList = srchQury.getSrchList(req, res, paramsMap);
         if(pktList.size()>0)
             udf.setView("yes");
         session.setAttribute("OFFERPKTLIST", pktList);
         req.setAttribute("pepTyp", pepTyp);
            util.updAccessLog(req,res,"Offer Price", "Offer Price end");
        return am.findForward("loadRpt");
        }
    }
    public ActionForward sort(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            OfferPriceFrom udf = (OfferPriceFrom)af;
           final String lprp = req.getParameter("prp");
           final String order = req.getParameter("order");
            ArrayList OFFERPKTLIST = (ArrayList)session.getAttribute("OFFERPKTLIST");
            if(OFFERPKTLIST.size()>0)
                udf.setView("yes");
            ArrayList pktList = sortByComparator(OFFERPKTLIST,lprp,order);
            session.setAttribute("OFFERPKTLIST", pktList);
            return am.findForward("loadRpt"); 
        }
    }
    public  ArrayList<HashMap> sortByComparator( ArrayList<HashMap> unsortList,final String lprp ,final String order) {
     
       
     
        // Sort list with comparator, to compare the Map values
        Collections.sort(unsortList, new Comparator<HashMap>() {
          public int compare(HashMap o1,HashMap o2) {
              if(order.equals("asc"))
            return ((Comparable) ((HashMap) (o1)).get(lprp)).compareTo(((HashMap) (o2)).get(lprp));
              else
            return ((Comparable) ((HashMap) (o2)).get(lprp)).compareTo(((HashMap) (o1)).get(lprp));
   
          }
        });
     
        // Convert sorted map back to a Map
      
            return unsortList;
       
      }
    public ActionForward ExcelByrWise(ActionMapping am, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
          
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"MEMO_RTRN","MEMO_RTRN");
        ArrayList offerPktList= (ArrayList)session.getAttribute("OFFERPKTLIST");
            offerPktList = sortByComparator(offerPktList, "byrNm", "asc");
            if(offerPktList!=null && offerPktList.size()>0){
                HashMap dbinfo = info.getDmbsInfoLst();
                String cnt = (String)dbinfo.get("CNT");
                HashMap ExcelDtl = new HashMap();
                ExcelDtl.put("VWPRPLST", vwprpLst);
                ExcelDtl.put("OFFERPKTLIST", offerPktList);
                HSSFWorkbook hwb = null;
                ExcelUtilObj xlUtil=new ExcelUtilObj();
                xlUtil.init(db, util, info, gtMgr);
                hwb =  xlUtil.OFFERXLBYR(req,ExcelDtl,cnt);
                OutputStream out = res.getOutputStream();
                String CONTENT_TYPE = "getServletContext()/vnd-excel";
                String fileNm = "OFFerByrWise"+util.getToDteTime()+".xls";
                res.setContentType(CONTENT_TYPE);
                res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
                hwb.write(out);
                out.flush();
                out.close();
            }
       return null;
        }
      }
     
    public ActionForward ExcelStoneWise(ActionMapping am, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
          
            GenericInterface genericInt = new GenericImpl();
            ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"MEMO_RTRN","MEMO_RTRN");
            ArrayList offerPktList= (ArrayList)session.getAttribute("OFFERPKTLIST");
                offerPktList = sortByComparator(offerPktList, "vnm", "asc");
                if(offerPktList!=null && offerPktList.size()>0){
                    HashMap dbinfo = info.getDmbsInfoLst();
                    String cnt = (String)dbinfo.get("CNT");
                    HashMap ExcelDtl = new HashMap();
                    ExcelDtl.put("VWPRPLST", vwprpLst);
                    ExcelDtl.put("OFFERPKTLIST", offerPktList);
                    HSSFWorkbook hwb = null;
                    ExcelUtilObj xlUtil=new ExcelUtilObj();
                    xlUtil.init(db, util, info, gtMgr);
                    hwb =  xlUtil.OFFERXLStone(req,ExcelDtl,cnt);
                    OutputStream out = res.getOutputStream();
                    String CONTENT_TYPE = "getServletContext()/vnd-excel";
                    String fileNm = "OFFerStoneWise"+util.getToDteTime()+".xls";
                    res.setContentType(CONTENT_TYPE);
                    res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
                    hwb.write(out);
                    out.flush();
                    out.close();
                }
            return null;
        }
      }
    
    public ActionForward excel(ActionMapping am, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
            HashMap dbinfo = info.getDmbsInfoLst();
            String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
            int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
            ExcelUtil xlUtil = new ExcelUtil();
            xlUtil.init(db, util, info);
            ArrayList pktList = (ArrayList)session.getAttribute("OFFERPKTLIST");
            ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
            HSSFWorkbook hwb = xlUtil.getGenOfferXl(itemHdr, pktList);
            int pktListsz=pktList.size();
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
            String contentTypezip = "application/zip";
            String fileNmzip = "offerHistory"+util.getToDteTime();
            OutputStream outstm = res.getOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(outstm);
            ZipEntry entry = new ZipEntry(fileNmzip+".xls");
            zipOut.putNextEntry(entry);
            res.setHeader("Content-Disposition","attachment; filename="+fileNmzip+".zip");
            res.setContentType(contentTypezip);
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
               hwb.write(bos);
               bos.writeTo(zipOut);      
              zipOut.closeEntry();
               zipOut.flush();
               zipOut.close();
               outstm.flush();
               outstm.close(); 
            }else{
                String fileNm = "OfferHistory"+util.getToDteTime()+".xls";
                OutputStream out = res.getOutputStream();
                String CONTENT_TYPE = "getServletContext()/vnd-excel";
                res.setContentType(CONTENT_TYPE);
                res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
            hwb.write(out);
            out.flush();
            out.close();
            }          
            return null;
        }
      }
    
    public ActionForward mailexcel(ActionMapping am, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
            ArrayList attAttachFilNme = new ArrayList();
            ArrayList attAttachTyp = new ArrayList();
            ArrayList attAttachFile = new ArrayList();
            HashMap dbinfo = info.getDmbsInfoLst();
            String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
            int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
            ExcelUtil xlUtil = new ExcelUtil();
            xlUtil.init(db, util, info);
            ArrayList pktList = (ArrayList)session.getAttribute("OFFERPKTLIST");
            ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
            HSSFWorkbook hwb = xlUtil.getGenOfferXl(itemHdr, pktList);
            String fileNme = "OfferHistory"+util.getToDteTime()+".xls";
            String filePath = session.getServletContext().getRealPath("/") + fileNme;
            

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            hwb.write(fileOutputStream);
            attAttachFilNme.add(fileNme);
            attAttachTyp.add("application/vnd.ms-excel");
            attAttachFile.add(filePath);
            
            session.setAttribute("attAttachFilNme", attAttachFilNme);
            session.setAttribute("attAttachTyp", attAttachTyp);
            session.setAttribute("attAttachFile", attAttachFile);
            return am.findForward("view");
        }
      }
//    public void srchBuy( HttpServletRequest req) throws Exception {
//        HttpSession session = req.getSession(false);
//        InfoMgr info = (InfoMgr)session.getAttribute("info");
//        DBUtil util = new DBUtil();
//        DBMgr db = new DBMgr();
//        Connection conn=info.getCon();
//        db.setCon(info.getCon());
//        util.setDb(db);
//        util.setInfo(info);
//        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//        util.setLogApplNm(info.getLoApplNm());
//        String buy = util.nvl((String)udf.getValue("nmeIdn"));
//        String delQ = " Delete from gt_srch_rslt where flg in ('Z')";
//        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
//        
//       String buySql = "insert into gt_srch_rslt(srch_id, stk_idn, stt, flg, rap_rte,  quot , vnm) " + 
//       "select  distinct 1 , a.idn, a.stt, 'Z', a.rap_rte,  b.quot , a.vnm  from mstk a, web_bid_wl b  where a.idn = b.mstk_idn " + 
//       "and a.stt in ('MKAV', 'MKIS', 'MKTL') and trunc(nvl(b.to_dt, sysdate)) >= trunc(sysdate) and b.stt='A' and b.typ='BID' and b.byr_idn = ?";
//        String stoneId= util.nvl((String)udf.getValue("vnm"));
//        if(!stoneId.equals(""))
//            buySql = buySql+"   and  ( vnm in ("+stoneId+") or tfl3 in ("+stoneId+") )";
//            
//        ArrayList ary = new ArrayList();
//        ary.add(buy);
//         ct = db.execUpd("buy", buySql, ary);
//        
//        String pktPrp = "pkgmkt.pktPrp(0,?)";
//        ary = new ArrayList();
//        
//        ary.add("MEMO_RTRN");
//        ct = db.execCall(" Srch Prp ", pktPrp, ary);
//        req.setAttribute("nmeIdn", buy);
//        
//    }
    
    public void srchRef( HttpServletRequest req,OfferPriceFrom udf) throws Exception {
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
      
        ArrayList params = new ArrayList();
        String stoneId= util.nvl((String)udf.getValue("vnm"));
        String  web  = util.nvl((String)udf.getValue("web"));
        String  diaflex= util.nvl((String)udf.getValue("diaflex"));
        String  pepTyp= util.nvl((String)udf.getValue("pepTyp"));
        String history = util.nvl((String)udf.getValue("history"));
        stoneId = util.getVnm(stoneId);
       String delQ = " Delete from gt_srch_rslt";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        String conQ="";
        if(!web.equals("") && diaflex.equals(""))
            conQ+=" and b.flg='WEB' ";
        if(!diaflex.equals("") && web.equals(""))
            conQ=" and b.flg='DF' "; 
        
        params = new ArrayList();
        
        conQ+=" and b.typ =? ";
        params.add(pepTyp);
        
       String srchRefQ = 
            "insert into gt_srch_rslt(srch_id, stk_idn, stt, flg, rap_rte,  quot , vnm) " + 
             "select  distinct b.idn , a.idn, a.stt, 'Z', a.rap_rte,  b.quot , a.vnm  from mstk a, web_bid_wl b  where a.idn = b.mstk_idn " + 
             "and a.stt in ('MKAV','MKPP','MKEI','MKIS','SHIS','MKTL','MKLB','MKSL','MKKS_IS','MKKS','MKOS_IS','MKOS','MKCS') and trunc(nvl(b.to_dt, sysdate)) >= trunc(sysdate) and b.stt='A' and   ( vnm in ("+stoneId+") or tfl3 in ("+stoneId+") ) "+conQ;
       if(!history.equals("")){
           srchRefQ = 
                       "insert into gt_srch_rslt(srch_id, stk_idn, stt, flg, rap_rte,  quot , vnm) " + 
                        "select  distinct b.idn , a.idn, a.stt, 'Z', a.rap_rte,  b.quot , a.vnm  from mstk a, web_bid_wl b  where a.idn = b.mstk_idn " + 
                        "and   ( vnm in ("+stoneId+") or tfl3 in ("+stoneId+") ) "+conQ; 
       }
        ct = db.execUpd(" Srch Ref ", srchRefQ, params);
      
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        params = new ArrayList();
        params.add("MEMO_RTRN");
        ct = db.execCall(" Srch Prp ", pktPrp, params);
    }
    
    public ActionForward delete(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        OfferPriceFrom udf = (OfferPriceFrom)af;
         util.updAccessLog(req,res,"Offer Price", "Offer Price delete");
        String allocate = util.nvl((String)udf.getValue("allocate"));
        String allocateKS = util.nvl((String)udf.getValue("allocateKS"));
         String allocateKO = util.nvl((String)udf.getValue("allocateKO"));
        if(!allocate.equals("")){
              int ct = db.execCall("AutoAlcPkg", "AUTO_ALC_PKG.PEP_MNL", new ArrayList());
            if(ct>0)
                req.setAttribute("msg", "Allocation Done Successfully");
        }else if(!allocateKS.equals("")){
            int ct = db.execCall("AutoAlcPkg", "AUTO_ALC_PKG.PEP_MNL_KS(pTyp=>'KS')", new ArrayList());
            if(ct>0)
              req.setAttribute("msg", "Allocation Done Successfully");
        }else if(!allocateKO.equals("")){
            int ct = db.execCall("AutoAlcPkg", "AUTO_ALC_PKG.PEP_MNL_KO(pTyp=>'KO')", new ArrayList());
            if(ct>0)
              req.setAttribute("msg", "Allocation Done Successfully");
        }else{
         Enumeration reqNme = req.getParameterNames();
         while(reqNme.hasMoreElements()) {
             String paramNm = (String)reqNme.nextElement();
           
             if(paramNm.indexOf("cb_off_") > -1) {
                String val = util.nvl(req.getParameter(paramNm));
                if(!val.equals("")){
                String[] params = val.split("_");
                if(params.length==2){
                String msktIdn = params[0];
                String relIdn = params[1];
                 
                String deleteOffer = "web_pkg.bid_rmv(pStkIdn=> ?,pRelIdn=>?)" ;
                ArrayList ary = new ArrayList();
                ary.add(msktIdn);
                 ary.add(relIdn);
                int ct = db.execCall("deleteOffer", deleteOffer, ary);
                }}
                 
            }}
        
         req.setAttribute("msg", "Delete Successfully..");
        }
         util.updAccessLog(req,res,"Offer Price", "Offer Price srch end");
         return am.findForward("loadRpt");
     }
            }
//    public ArrayList GenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("OfferSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'OFFER_RPT' and flg <> 'N' order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("OfferSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
    
        public ActionForward loadKapuOction(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        OfferPriceFrom udf = (OfferPriceFrom)af;
        util.updAccessLog(req,res,"Offer Price", "Offer Price loadKapuOction");
        ArrayList ary = new ArrayList();   
        
        String delQ = " Delete from gt_srch_rslt";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        String frm_dt=util.nvl((String)udf.getValue("issfrmdte"));
        String to_dt=util.nvl((String)udf.getValue("isstodte"));
        String conQ="";
        if(!frm_dt.equals("") && to_dt.equals("")){
            to_dt=frm_dt;
        }
        if(frm_dt.equals("") && !to_dt.equals("")){
                frm_dt=to_dt;
        }
        if(!frm_dt.equals("") && !to_dt.equals("")){
                conQ+=" and trunc(b.frm_dt) between to_date('"+frm_dt+"' , 'dd-mm-yyyy') and to_date('"+to_dt+"' , 'dd-mm-yyyy') ";
        }else{
                conQ+=" and trunc(nvl(b.to_dt, sysdate)) >= trunc(sysdate) ";
        }
            
        String srchRefQ="insert into gt_srch_rslt(srch_id,PKT_DTE, stk_idn, stt,cts, flg, rap_rte,  quot ,rap_dis, vnm,byr,emp) \n" + 
        "select  b.idn ,b.frm_dt, a.idn, a.stt,a.cts, 'Z', a.rap_rte,  b.quot ,decode(a.rap_rte ,'1',null,'0',null, trunc((b.quot/greatest(a.rap_rte,1)*100)-100, 2)), a.vnm,\n" + 
        "GET_ANA_BYR_NM(b.byr_idn),byr.get_nm(d.emp_idn)\n" + 
        "from \n" + 
        "mstk a, web_bid_wl b,nmerel c,nme_v d\n" + 
        "where a.idn = b.mstk_idn \n" + 
        "and a.pkt_ty='NR' and b.typ='KOR' and b.rel_idn=c.nmerel_idn and c.nme_idn=d.nme_idn\n" + 
        "and b.stt='A' ";
            srchRefQ+=conQ;
        ct = db.execUpd(" ins gt_srch_rslt", srchRefQ,new ArrayList());   
        if(ct > 0){
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add("OCTION_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        HashMap stockList = SearchResult(req,res);
            String lstNme = "OCTION_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
            gtMgr.setValue(lstNme+"_SEL",new ArrayList());
            gtMgr.setValue(lstNme, stockList);
            gtMgr.setValue("lstNmeOCTION", lstNme);
            if(stockList.size()>0){
            HashMap dtlMap = new HashMap();
            ArrayList selectstkIdnLst = new ArrayList();
            Set<String> keys = stockList.keySet();
            for(String key: keys){
            selectstkIdnLst.add(key);
            }
            dtlMap.put("selIdnLst",selectstkIdnLst);
            dtlMap.put("pktDtl", stockList);
            dtlMap.put("flg", "M");
            HashMap ttlMap = util.getTTL(dtlMap);
            gtMgr.setValue(lstNme+"_TTL", ttlMap);
            }
            req.setAttribute("view", "Y");
        }

        util.updAccessLog(req,res,"Offer Price", "Offer Price load loadKapuOction");  
        return am.findForward("loadKapuOction");
        }
    }
     
    public ActionForward Issue(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          return am.findForward(rtnPg);   
      }else{
        util.updAccessLog(req,res,"Offer Price", "Issue");
          OfferPriceFrom udf = (OfferPriceFrom)form;
          Enumeration reqNme = req.getParameterNames();
          ArrayList stkIdnLst = new ArrayList();
          String stkIdnstr="";
           while (reqNme.hasMoreElements()) {
              String paramNm = (String) reqNme.nextElement();
              if (paramNm.indexOf("cb_stk") > -1) {
                  String stkIdn = req.getParameter(paramNm);
                  if(!stkIdnLst.contains(stkIdn)){
                  stkIdnstr = stkIdnstr+","+stkIdn;
                  stkIdnLst.add(stkIdn);
                  }
              }
            }
          if(!stkIdnstr.equals(""))
              stkIdnstr = stkIdnstr.replaceFirst(",", "");
          int ct=0;
        String delQ = " Delete from gt_srch_rslt ";
        ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
    
        String prcId = "";
        String empId = "13568";
          
        String prcSql = "select idn, prc , in_stt,is_stt,grp from mprc where  is_stt ='MKOS'" ;
      ArrayList  outLst = db.execSqlLst("prcSql", prcSql, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
        prcId = rs.getString("idn");
        }
        rs.close();
        pst.close();
        
        if(!prcId.equals("")){
        ArrayList params = null;
        int issueIdn = 0;
          
        String insScanPkt = " insert into gt_srch_rslt(stk_idn,flg) select vnm ,'S' from TABLE(PARSE_TO_TBL('"+stkIdnstr+"'))";
        ct = db.execDirUpd(" ins scan", insScanPkt,new ArrayList());
          
        if(ct>0){
        params = new ArrayList();
        params.add(prcId);
        params.add(empId);
        ArrayList out = new ArrayList();
        out.add("I");
        CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", params ,out);
        issueIdn = cst.getInt(3);
          cst.close();
          cst=null;
            util.updAccessLog(req,res,"Assort Issue", "issueIdn" +issueIdn);
         params = new ArrayList();
         params.add(String.valueOf(issueIdn));
         params.add("1");
         params.add("IS");
         String issuePkt = "ISS_RTN_PKG.ALL_ISS_PKT(pIssId =>?, pGrp => ?, pStt => ?)";
         ct = db.execCall("issuePkt", issuePkt, params);
        }
    if(ct > 0){
    req.setAttribute( "msg","Requested packets get Issue with Issue Id "+issueIdn);
    req.setAttribute( "issueidn",String.valueOf(issueIdn));
    }else{
        req.setAttribute( "msg","Error in Issue process"); 
    }
        }else{
            req.setAttribute( "msg","Error in Issue process To Find Process"); 
        }
    GtMgrReset(req);
    udf.reset();
    int accessidn=util.updAccessLog(req,res,"Offer Price", "End");
    req.setAttribute("accessidn", String.valueOf(accessidn));;
    return loadKapuOction(am,form,req, res);
    }
    }   
        
    public void GtMgrReset(HttpServletRequest req){
          HttpSession session = req.getSession(false);
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
          String lstNme = (String)gtMgr.getValue("lstNmeOCTION");
          HashMap gtMgrMap = (HashMap)gtMgr.getValues();
          gtMgrMap.remove(lstNme);
    
           gtMgrMap.remove(lstNme+"_SEL");
           gtMgrMap.remove(lstNme+"_TTL");
           gtMgrMap.remove("lstNmeOCTION");
         }
    public HashMap SearchResult(HttpServletRequest req ,HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap pktList = new HashMap();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
      
        ArrayList vwPrpLst = mktPrprViw(req,res);
        String  srchQ =  " select srch_id,to_char(PKT_DTE, 'DD-MON HH24:mi:ss') dte,sk1,byr,emp,quot,stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , rmk,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis ";

        

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

        
        String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by sk1 ";
        
        ArrayList ary = new ArrayList();
        ary.add("Z");
        ArrayList  outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet  rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                 String stk_idn = util.nvl(rs.getString("srch_id"));
                GtPktDtl pktPrpMap = new GtPktDtl();
                pktPrpMap.setStt(util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.setVnm(vnm);
                pktPrpMap.setPktIdn(rs.getLong("stk_idn"));
                pktPrpMap.setValue("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.setValue("cts",util.nvl(rs.getString("cts")));
                pktPrpMap.setValue("byr",util.nvl(rs.getString("byr")));
                pktPrpMap.setValue("emp",util.nvl(rs.getString("emp")));
                    pktPrpMap.setValue("stt",util.nvl(rs.getString("stt")));
                    pktPrpMap.setValue("dte",util.nvl(rs.getString("dte")));
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                      if (prp.toUpperCase().equals("RAP_DIS"))
                      val = util.nvl(rs.getString("r_dis")) ;  
                      if (prp.toUpperCase().equals("RTE"))
                      val = util.nvl(rs.getString("quot")) ;  
                        pktPrpMap.setValue(prp, val);
                         }
                              
                    pktList.put(stk_idn,pktPrpMap);
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
    
    public ArrayList mktPrprViw(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute("kapuOctionViewLst");
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
      
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
           
              ArrayList  outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'OCTION_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet  rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                pst.close();
                session.setAttribute("kapuOctionViewLst", asViewPrp);
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
                util.updAccessLog(req,res,"Offer Price", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Offer Price", "init");
            }
            }
            return rtnPg;
            }
       
}
   

