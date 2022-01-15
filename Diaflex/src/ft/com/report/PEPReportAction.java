package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.dao.ByrDao;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SaleDeliveryForm;
import ft.com.marketing.SearchQuery;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

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

public class PEPReportAction extends DispatchAction {

    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception 
    {  
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
        util.updAccessLog(req,res,"PEP Report", "load start");
        GenericInterface genericInt = new GenericImpl();
        WebReportForm udf = (WebReportForm) af;
        udf.resetALL();
        int del = db.execUpd("delete gt", "delete from gt_srch_rslt",new ArrayList());
        
        String insertIntoGt = "Insert into gt_srch_rslt (srch_id, stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, fquot, rap_dis , lmt) "+	  	
                             " select distinct a.idn , b.idn, b.vnm, qty, cts, dte, b.stt,  decode(nvl(upr,0), 0, cmp, upr), b.rap_rte, cert_lab, cert_no   "+	  			
                             " ,'Z', sk1, nvl(upr, cmp), nvl(upr, cmp),  decode(b.rap_rte ,'1',null, trunc((nvl(b.upr,b.cmp)/b.rap_rte*100)-100, 2)) , a.lmt_rte "+	
                            " from web_bid_wl a, mstk b "+	  	
                            " where a.mstk_idn = b.idn  and trunc(nvl(a.to_dt, sysdate)) >= trunc(sysdate) "+  	
                             " and a.typ in('BID','LB','BD','KO') and a.stt = 'A' and b.stt in ('MKAV','MKPP','MKEI','MKIS','SHIS','MKTL','MKLB','MKBD','MKBDIS','MKOS','MKOS_IS') and b.pkt_ty = 'NR'" ;
        int ct = db.execUpd("insert Gt", insertIntoGt, new ArrayList());
            
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ArrayList params = new ArrayList();
        params.add("MEMO_VW");
        ct = db.execCall(" Srch Prp ", pktPrp, params);
        
        ArrayList repMemoList = genericInt.genericPrprVw(req, res, "memoVwList","MEMO_VW");
        ArrayList pktList = getSrchList(req , res,udf);
        req.setAttribute("pktList", pktList);
        req.setAttribute("view", "Y");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("OFFER_REPORT");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("OFFER_REPORT");
        allPageDtl.put("OFFER_REPORT",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"PEP Report", "load end");
        return am.findForward("load");

        }
    }
    
    public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"PEP Report", "createXL start");
        HashMap dbinfo = info.getDmbsInfoLst();
        String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
        int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        ArrayList pktList = (ArrayList)session.getAttribute("peppktDtlList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        int pktListsz=pktList.size();
     
            HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
            String contentTypezip = "application/zip";
            String fileNmzip = "PepReport"+util.getToDteTime();
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
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "PriceReport"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        hwb.write(out);
        out.flush();
        out.close();
        }
            util.updAccessLog(req,res,"PEP Report", "createCXL end");
        return null;
        }
    }
    
    public ArrayList getSrchList(HttpServletRequest req , HttpServletResponse res,WebReportForm udf){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList pktList   = new ArrayList();
        GenericInterface genericInt = new GenericImpl();
        HashMap mprp      = null;
        ArrayList    vwPrpLst  = genericInt.genericPrprVw(req, res, "memoVwList","MEMO_VW");
        String    srchQ     = null;
        HashMap pktPrpMap = new HashMap();
        int       prpSHseq  = vwPrpLst.indexOf("SH");
        String    shfld     = "prp_";
        String conQ="";
        mprp = info.getMprp();
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        if(cnt.equals("kj")){
            conQ=" and b.flg = 'WEB' "; 
            udf.setValue("web", "WEB");
        }

      
        srchQ = "select sk1, cts crtwt, round(b.ofr_rte) ofr_rte , b.rnk , b.net_rte, b.byr_idn , initcap(byr.get_nm(b.byr_idn)) byrNm  ," + 
                " to_char(trunc(((round(b.net_rte)*100)/greatest(a.rap_rte,100)) - 100,2),'90.00') net_dis ,"+
                "to_char(decode(nvl(b.ofr_dis,0),0, trunc(((round(b.ofr_rte)*100)/greatest(a.rap_rte,100)) - 100,2),b.ofr_dis),'90.00') ofr_dis,  to_char(decode(a.quot, 0, 0, decode(nvl(a.rap_dis,0), 0, 0, trunc(((a.quot*100)/greatest(a.rap_rte,100)) - 100,2))),'90.00') r_dis  , "+
                " stk_idn, a.quot, a.vnm, kts_vw kts, cert_lab cert, cert_no,b.idn ,  to_char(trunc(cts,2),'90.99') cts, a.quot rte , a.rap_rte , "+
                " a.stt, nvl(fquot,0) fquot , a.lmt , b.frm_dt offerDte  ";
       for (int i = 0; i < vwPrpLst.size(); i++) {
            String fld = "prp_";
            int    j   = i + 1;

            if (j < 10) {
                fld += "00" + j;
            } else if (j < 100) {
                fld += "0" + j;
            } else if (j > 100) {
                fld += j;
            }

            if (i == prpSHseq) {
                shfld = fld;
            }

            srchQ += ", " + fld;
        }

        String rsltQ = srchQ + " from gt_srch_rslt a , WEB_BID_WL_V b where a.flg = ? and  b.stt='A' and  a.srch_id=b.idn and " +
            "trunc(nvl(b.to_dt, sysdate)) >= trunc(sysdate) and a.stk_idn = b.mstk_idn and b.typ in ('BD','BID','LB','KS','KO')"+conQ+" order by a.sk1, a.vnm, b.rnk";
        ArrayList ary   = new ArrayList();

        ary.add("Z");

        ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        pktList = new ArrayList();

        try {
            while(rs.next()) {

                    pktPrpMap = new HashMap();
                    pktPrpMap.put("offer_dte", util.nvl(rs.getString("offerDte")));
                    pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                    pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                    pktPrpMap.put("fquot", util.nvl(rs.getString("fquot")));
                    pktPrpMap.put("offer_rte",util.nvl(rs.getString("ofr_rte")));
                    pktPrpMap.put("offer_dis", util.nvl(rs.getString("ofr_dis")));
                    pktPrpMap.put("rnk",util.nvl(rs.getString("rnk")));
                    pktPrpMap.put("net_rte",util.nvl(rs.getString("net_rte")));
                    pktPrpMap.put("net_dis",util.nvl(rs.getString("net_dis")));
                    pktPrpMap.put("byrNm",util.nvl(rs.getString("byrNm")));
                    pktPrpMap.put("bidIdn", util.nvl(rs.getString("idn")));
                    pktPrpMap.put("vnm", util.nvl(rs.getString("vnm")));
                    pktPrpMap.put("byrIdn", util.nvl(rs.getString("byr_idn")));
                    pktPrpMap.put("ofr_lmt", util.nvl(rs.getString("lmt")));
                  
              
                    for (int j = 0; j < vwPrpLst.size(); j++) {
                        String prp = (String)vwPrpLst.get(j);

                        String fld = "prp_";
                        if (j < 9)
                            fld = "prp_00" + (j + 1);
                        else
                            fld = "prp_0" + (j + 1);

                        String val = util.nvl(rs.getString(fld));
                        if (prp.toUpperCase().equals("RAP_DIS"))
                            val = util.nvl(rs.getString("r_dis"));
                            
                        if (prp.toUpperCase().equals("RAP_RTE"))
                            val = util.nvl(rs.getString("rap_rte"));
                             

                    

                        if(prp.equals("RTE"))
                            val = util.nvl(rs.getString("rte"));
                     
                           

                            pktPrpMap.put(prp, val);
                        }
                    pktList.add(pktPrpMap);
                }
            rs.close(); pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }

        return pktList;
    }
    
    public ActionForward Allocate(ActionMapping am, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception 
    {  
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
             util.updAccessLog(req,res,"PEP Report", "Allocate start");
        int ct = db.execCall("AutoAlcPkg", "AUTO_ALC_PKG.PEP_MNL", new ArrayList());
        if(ct>0)
            req.setAttribute("msg", "Allocation Done Successfully");
             util.updAccessLog(req,res,"PEP Report", "Allocate end");
       return load(am,form, req, res);
         }
     }
    
    public ActionForward loadbid(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"PEP Report", "loadbid start");
        GenericInterface genericInt = new GenericImpl();
        WebReportForm udf = (WebReportForm) af;
        String    web      = util.nvl((String)udf.getValue("web"));
        String    diaflex      = util.nvl((String)udf.getValue("diaflex"));
            String frmDte = util.nvl((String)udf.getValue("frmdte"));
            String todte = util.nvl((String)udf.getValue("todte"));
        ArrayList pktList   = new ArrayList();
        HashMap mprp      = null;
        ArrayList    vwPrpLst  = genericInt.genericPrprVw(req, res, "memoVwList","MEMO_VW");
        String    srchQ     = null;
        HashMap pktPrpMap = new HashMap();
        int       prpSHseq  = vwPrpLst.indexOf("SH");
        String    shfld     = "prp_";
        String conQ="";
        mprp = info.getMprp();
            if(!web.equals("") && diaflex.equals(""))
                conQ=" and b.flg='WEB' "; 
            if(!diaflex.equals("") && web.equals(""))
                conQ=" and b.flg='DF' ";
            
            if(!frmDte.equals("") && !todte.equals("")){
            conQ+= " and trunc(b.frm_dt) between to_date('"+frmDte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";

            }
            
        //Sir Told me web_bid_wl flg '0' nahe aayega but existing logic comming
        srchQ = "select sk1, cts crtwt, round(b.ofr_rte) ofr_rte , b.rnk , b.net_rte, b.byr_idn , initcap(byr.get_nm(b.byr_idn)) byrNm  ," + 
                " to_char(trunc(((round(b.net_rte)*100)/greatest(a.rap_rte,100)) - 100,2),'90.00') net_dis ,"+
                "to_char(decode(nvl(b.ofr_dis,0),0, trunc(((round(b.ofr_rte)*100)/greatest(a.rap_rte,100)) - 100,2),b.ofr_dis),'90.00') ofr_dis,  to_char(decode(a.quot, 0, 0, decode(nvl(a.rap_dis,0), 0, 0, trunc(((a.quot*100)/greatest(a.rap_rte,100)) - 100,2))),'90.00') r_dis  , "+
                " stk_idn, a.quot, a.vnm, kts_vw kts, cert_lab cert, cert_no,b.idn ,  to_char(trunc(cts,2),'90.99') cts, a.quot rte , a.rap_rte , "+
                " a.stt, nvl(fquot,0) fquot  ";
        for (int i = 0; i < vwPrpLst.size(); i++) {
            String fld = "prp_";
            int    j   = i + 1;

            if (j < 10) {
                fld += "00" + j;
            } else if (j < 100) {
                fld += "0" + j;
            } else if (j > 100) {
                fld += j;
            }

            if (i == prpSHseq) {
                shfld = fld;
            }

            srchQ += ", " + fld;
        }

        String rsltQ = srchQ + " from gt_srch_rslt a , WEB_BID_WL_V b where a.flg = ?  and  b.stt='A' and  a.srch_id=b.idn and   " +
            "trunc(nvl(b.to_dt, sysdate)) >= trunc(sysdate) and a.stk_idn = b.mstk_idn and b.typ in('BID','LB') "+conQ+" order by a.sk1, a.vnm, b.rnk";
        ArrayList ary   = new ArrayList();

        ary.add("Z");


            ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        pktList = new ArrayList();

        try {
            while(rs.next()) {

                    pktPrpMap = new HashMap();
                    pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                    pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                    pktPrpMap.put("fquot", util.nvl(rs.getString("fquot")));
                    pktPrpMap.put("offer_rte",util.nvl(rs.getString("ofr_rte")));
                    pktPrpMap.put("offer_dis", util.nvl(rs.getString("ofr_dis")));
                    pktPrpMap.put("rnk",util.nvl(rs.getString("rnk")));
                    pktPrpMap.put("net_rte",util.nvl(rs.getString("net_rte")));
                    pktPrpMap.put("net_dis",util.nvl(rs.getString("net_dis")));
                    pktPrpMap.put("byrNm",util.nvl(rs.getString("byrNm")));
                    pktPrpMap.put("bidIdn", util.nvl(rs.getString("idn")));
                    pktPrpMap.put("vnm", util.nvl(rs.getString("vnm")));
                    pktPrpMap.put("byrIdn", util.nvl(rs.getString("byr_idn")));
                    
              
                    for (int j = 0; j < vwPrpLst.size(); j++) {
                        String prp = (String)vwPrpLst.get(j);

                        String fld = "prp_";
                        if (j < 9)
                            fld = "prp_00" + (j + 1);
                        else
                            fld = "prp_0" + (j + 1);

                        String val = util.nvl(rs.getString(fld));
                        if (prp.toUpperCase().equals("RAP_DIS"))
                            val = util.nvl(rs.getString("r_dis"));
                            
                        if (prp.toUpperCase().equals("RAP_RTE"))
                            val = util.nvl(rs.getString("rap_rte"));
                             

                    

                        if(prp.equals("RTE"))
                            val = util.nvl(rs.getString("rte"));
                     
                           

                            pktPrpMap.put(prp, val);
                        }
                    pktList.add(pktPrpMap);
                }
            rs.close(); pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        udf.setValue("web", web);
        udf.setValue("diaflex", diaflex);
        req.setAttribute("pktList", pktList);
        req.setAttribute("view", "Y");
            util.updAccessLog(req,res,"PEP Report", "loadbid end");
        return am.findForward("load");
        }
    }

    public PEPReportAction() {
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
                util.updAccessLog(req,res,"PEP Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"PEP Report", "init");
            }
            }
            return rtnPg;
            }
}
