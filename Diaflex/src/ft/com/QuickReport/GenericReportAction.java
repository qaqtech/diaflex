package ft.com.QuickReport;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.InsertGtThread;
import ft.com.JspUtil;
import ft.com.PdfforReport;
import ft.com.assort.AssortIssueImpl;
import ft.com.dao.DFMenu;
import ft.com.dao.GtPktDtl;
import ft.com.dao.Mprc;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.QuickReport.GenericReportForm;
import ft.com.marketing.PacketLookupForm;
import ft.com.marketing.SearchForm;
import ft.com.marketing.SearchQuery;

import java.awt.Color;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;
import java.io.OutputStream;

import java.math.BigDecimal;

import java.math.RoundingMode;

import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.ws.rs.core.MediaType;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.collections.ListUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GenericReportAction extends DispatchAction {
    public GenericReportAction() {
        super();
    }
    final static int size=1024;
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
        GenericInterface genericInt ;
        util.updAccessLog(req,res,"Analysis Report", "load start");
        GenericReportForm udf = (GenericReportForm)af;
        udf.reset();
        HashMap mprp = info.getSrchMprp();
        HashMap prp = info.getSrchPrp();
        String rpt = util.nvl(req.getParameter("rpt"));
        String asUp = req.getParameter("btn");
        HashMap prpMap = new HashMap();
        ArrayList sttList = new ArrayList();
        String stkStt = "select distinct grp1 , decode(grp1, 'MKT','Marketing','LAB','Lab','ASRT','Assort','SOLD','Sold', grp1) dsc " +
                        " from df_stk_stt where flg='A' and vld_dte is null and grp1 is not null order by dsc ";
        if(asUp!=null)
            stkStt = stkStt+" and grp1 <> 'SOLD'";
          ArrayList outLst = db.execSqlLst("stkStt", stkStt, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            ArrayList sttDtl = new ArrayList();
            sttDtl.add(rs.getString("grp1"));
            sttDtl.add(rs.getString("dsc"));
            sttList.add(sttDtl);
        }
        rs.close();
        pst.close();
        session.setAttribute("sttLst",sttList);
        String getSrchPrp="";
        ArrayList basicPrpLst = (ArrayList)session.getAttribute("basicPrpLst");
        if(basicPrpLst==null){
            basicPrpLst = new ArrayList();
         getSrchPrp = "Select prp , flg from rep_prp where mdl='ANLS_BSC_PRP' and flg in('M','S') order by rnk ";
           outLst = db.execSqlLst(" Srch Prp ", getSrchPrp, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
             ArrayList srchMdl = new ArrayList();
             srchMdl.add(util.nvl(rs.getString("prp")));
             srchMdl.add(util.nvl(rs.getString("flg")));
             basicPrpLst.add(srchMdl);
        }
            rs.close();
            pst.close();
        session.setAttribute("basicPrpLst", basicPrpLst);
      
        }
        prpMap.put("BSC", "basicPrpLst");
        prpMap.put("BSCTTL", "Basic Properties");
        ArrayList measCutPrpLst = (ArrayList)session.getAttribute("measCutPrpLst");
        if(measCutPrpLst==null){
            measCutPrpLst = new ArrayList();
        getSrchPrp = "Select prp , flg from rep_prp where mdl='ANLS_MEAS_PRP' and flg in('M','S') order by rnk ";
          outLst = db.execSqlLst(" Srch Prp ", getSrchPrp, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
             ArrayList srchMdl = new ArrayList();
             srchMdl.add(util.nvl(rs.getString("prp")));
             srchMdl.add(util.nvl(rs.getString("flg")));
             measCutPrpLst.add(srchMdl);
        }
            rs.close();
            pst.close();
        session.setAttribute("measCutPrpLst", measCutPrpLst);
       
        }
        prpMap.put("MEAS","measCutPrpLst");
        prpMap.put("MEASTTL", "Measurement Properties");
        ArrayList customPrpLst = (ArrayList)session.getAttribute("customPrpLst");
        if(customPrpLst==null){
            customPrpLst = new ArrayList();
        getSrchPrp = "Select prp , flg from rep_prp where mdl='ANLS_CST_PRP' and flg in('M','S') order by rnk ";
          outLst = db.execSqlLst(" Srch Prp ", getSrchPrp, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
             ArrayList srchMdl = new ArrayList();
             srchMdl.add(util.nvl(rs.getString("prp")));
             srchMdl.add(util.nvl(rs.getString("flg")));
             customPrpLst.add(srchMdl);
        }
            rs.close();
            pst.close();
        session.setAttribute("customPrpLst", customPrpLst);
        
        }
        prpMap.put("CUST","customPrpLst");
        prpMap.put("CUSTTTL", "Customize Properties");
        ArrayList mfgPrpLst = (ArrayList)session.getAttribute("mfgPrpLst");
        if(mfgPrpLst==null){
            mfgPrpLst = new ArrayList();
        getSrchPrp = "Select prp , flg from rep_prp where mdl='ANLS_MFG_PRP' and flg in('M','S') order by rnk ";
          outLst = db.execSqlLst(" Srch Prp ", getSrchPrp, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
             ArrayList srchMdl = new ArrayList();
             srchMdl.add(util.nvl(rs.getString("prp")));
             srchMdl.add(util.nvl(rs.getString("flg")));
             mfgPrpLst.add(srchMdl);
        }
            rs.close();
            pst.close();
        session.setAttribute("mfgPrpLst", mfgPrpLst);
      
        }
        prpMap.put("MFG","mfgPrpLst");
        prpMap.put("MFGTTL", "Manufacturing Properties");
        ArrayList txtPrpLst = (ArrayList)session.getAttribute("txtPrpLst");
        if(txtPrpLst==null){
            txtPrpLst = new ArrayList();
        getSrchPrp = "Select prp , flg from rep_prp where mdl='ANLS_TXT_PRP' and flg in('M','S') order by rnk ";
          outLst = db.execSqlLst(" Srch Prp ", getSrchPrp, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
             ArrayList srchMdl = new ArrayList();
             srchMdl.add(util.nvl(rs.getString("prp")));
             srchMdl.add(util.nvl(rs.getString("flg")));
             txtPrpLst.add(srchMdl);
        }
            rs.close();
            pst.close();
        session.setAttribute("txtPrpLst", txtPrpLst);
       
        }
        
        prpMap.put("TXT","txtPrpLst");
        prpMap.put("TXTTTL","Text Properties");
        session.setAttribute("prpMap", prpMap);
//        udf.setValue("SOLD", "SOLD");
        
        String btnKey = req.getParameter("btn");
        if(btnKey!=null)
            udf.setValue("upgrade", "COL");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("GENERIC_REPORT");
        allPageDtl.put("GENERIC_REPORT",pageDtl);
        }
        info.setPageDetails(allPageDtl); 
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        ArrayList prpList = new ArrayList();
            pageList= ((ArrayList)pageDtl.get("TABLINK") == null)?new ArrayList():(ArrayList)pageDtl.get("TABLINK");
        if(pageList!=null && pageList.size() >0){
              for(int j=0;j<pageList.size();j++){
              pageDtlMap=(HashMap)pageList.get(j);
              prpList.add(util.nvl((String)pageDtlMap.get("fld_nme")));
        }}
        session.setAttribute("prpList",prpList);
            pageList= ((ArrayList)pageDtl.get("DAYS") == null)?new ArrayList():(ArrayList)pageDtl.get("DAYS");
        if(pageList!=null && pageList.size() >0){
        for(int j=0;j<pageList.size();j++){
        pageDtlMap=(HashMap)pageList.get(j);
        String dflt_val=(String)pageDtlMap.get("dflt_val");
        ArrayList params= new ArrayList();
        String dtePrpQ="select to_char(trunc(sysdate-?), 'dd-mm-rrrr') frmdte,to_char(trunc(sysdate), 'dd-mm-rrrr') todte from dual";
        params.add(dflt_val);
           outLst = db.execSqlLst("Date calc", dtePrpQ,params);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while (rs.next()) { 
        udf.setValue("frmDte",(util.nvl(rs.getString("frmdte"))));
        udf.setValue("toDte",(util.nvl(rs.getString("todte"))));
        udf.setValue("frmDtestkoenclose",(util.nvl(rs.getString("frmdte"))));
        udf.setValue("toDtestkoenclose",(util.nvl(rs.getString("todte"))));
        udf.setValue("frmdtestkoenclose",(util.nvl(rs.getString("frmdte"))));
        udf.setValue("todtestkoenclose",(util.nvl(rs.getString("todte"))));
        }    
            rs.close();
            pst.close();
        }
        }
        gridfmtLst(req);
//        genericInt.graphPath(req,res);
        udf.setValue("srchRef","vnm");
        udf.setValue("srchBy","C");
        udf.setValue("periodcomp","Y");
            
//        util.imageDtls();
            if(!rpt.equals("")){
                genericInt = new GenericImpl();
                ArrayList mprcIdnAll=(ArrayList)session.getAttribute("mprcIdnAll");
                if(mprcIdnAll==null)
                getPrc(req,res);
                genericInt.genericPrprVw(req,res,"ANLS_GROUP","ANLS_GROUP");
                req.setAttribute("rpt",rpt);
            }
            util.updAccessLog(req,res,"Analysis Report", "load end");
        return loadSrch(am, af, req, res);
        }
    }
    
    public ActionForward loadSrch(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
            return mapping.findForward(rtnPg);   
        }else{ 
            GenericReportForm udf = (GenericReportForm)form;
        String srchIDN = util.nvl((String)info.getDmbsInfoLst().get("ANYSIS_DFTLSRCHID"));
            if(!srchIDN.equals("")){   
        HashMap vals = new HashMap();
        ArrayList ary = new ArrayList();
        String getDtls = "select a.mprp , a.sfr ,a.sto from srch_dtl a where a.srch_id = ? ";
        ary = new ArrayList();
        ary.add(srchIDN);
    
              ArrayList outLst = db.execSqlLst(" Dmd Id :"+ srchIDN, getDtls, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
          String mprp =util.nvl(rs.getString("mprp")) ;
          String sfr = util.nvl( rs.getString("sfr"));
          String sto = util.nvl(rs.getString("sto"));
          udf.setValue(mprp+"_1", sfr);
          udf.setValue(mprp+"_2", sto);
        }
        rs.close();
        pst.close();
        String getSubDtls = "select a.mprp,a.vfr, a.sfr ,a.sto from srch_dtl_sub a where a.srch_id = ? ";
        ary = new ArrayList();
        ary.add(srchIDN);
      
       outLst = db.execSqlLst(" SubSrch :"+ srchIDN, getSubDtls, ary);
        pst = (PreparedStatement)outLst.get(0);
       rs = (ResultSet)outLst.get(1);
        while(rs.next()){
          String mprp =util.nvl(rs.getString("mprp")) ;
          String sfr = util.nvl( rs.getString("sfr"));
            String vfr = util.nvl( rs.getString("vfr"));
          udf.setValue(mprp+"_"+vfr, vfr);
        }
        rs.close();
        pst.close();
        
           
            }
        return mapping.findForward("load");
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
                util.updAccessLog(req,res,"Analysis Report", "save start");
            GenericReportForm udf = (GenericReportForm)af;
            long lStartTime = new Date().getTime();
            HashMap dbinfo = info.getDmbsInfoLst();
            int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
            String mongoanalysis = util.nvl((String)dbinfo.get("MONGO_ANALYSIS"),"N");
            db.execUpd("gt delete","delete from gt_srch_rslt", new ArrayList());
            db.execUpd(" Del Old Pkts ", "Delete from gt_pkt_scan", new ArrayList());
            String btn = util.nvl((String)udf.getValue("colClrUpd"));
            String saldelation = util.nvl((String)udf.getValue("saledelation"));
            String vnm = util.nvl((String)udf.getValue("vnm"));
            String srchRef = util.nvl((String)udf.getValue("srchRef"));
            String srchTyp = util.nvl((String)udf.getValue("srchTyp"));
            String srchBy = util.nvl((String)udf.getValue("srchBy"));
            String giasummary = util.nvl((String)udf.getValue("giasummary"));
            String fiftydownpt = util.nvl((String)udf.getValue("fiftydownpt"));
            String sizepurity = util.nvl((String)udf.getValue("sizepurity"));
            String memoshape = util.nvl((String)udf.getValue("memoshape"));
            String shapeclr = util.nvl((String)udf.getValue("shapeclr"));
            String pricegraph = util.nvl((String)udf.getValue("pricegraph"));
            String pktdtl = util.nvl((String)udf.getValue("pktdtl"));
            String imageDtl = util.nvl((String)udf.getValue("imageDtl"));
            String hitmap = util.nvl((String)udf.getValue("hitmap"));
            String compareWith = util.nvl((String)udf.getValue("compareWith"),"quot");
            ArrayList sttList = (session.getAttribute("sttLst") == null)?new ArrayList():(ArrayList)session.getAttribute("sttLst");
            String frmDte = util.nvl((String)udf.getValue("frmDte"));
            String toDte = util.nvl((String)udf.getValue("toDte"));
            String gridfmt = util.nvl((String)udf.getValue("gridfmt"));
            String p1frm = util.nvl((String)udf.getValue("p1frm"));
            String p1to = util.nvl((String)udf.getValue("p1to"));
            String p2frm = util.nvl((String)udf.getValue("p2frm"));
            String p2to = util.nvl((String)udf.getValue("p2to"));
            String p3frm = util.nvl((String)udf.getValue("p3frm"));
            String p3to = util.nvl((String)udf.getValue("p3to"));
            String companalysis = util.nvl((String)udf.getValue("companalysis"));
            String stkoencloseBtn = util.nvl((String)udf.getValue("stkoencloseBtn"));
            String reports = util.nvl((String)udf.getValue("reports"));
            info.setCompareWith(compareWith);
            ResultSet rs=null;
            String psearch="N";
            String stt="";
            String mdl="ANLS_VW";
            String srchR="srchR";
                int lSrchId=0;
                int pSrchId=0;   
            int cnt=0;
            ArrayList ary = null;
            ArrayList status=new ArrayList();
            if(btn.equals("Assort Upgrade"))
            mdl="CO_CLR_UPG_VW";
            if(!reports.equals(""))
            mdl="NONE";
            if(!giasummary.equals(""))
            mdl="GRP_SMRY";  
            if(!fiftydownpt.equals(""))
            mdl="FIFTYDWNPT"; 
            if(!sizepurity.equals(""))
            mdl="MERGEDATA_VW"; 
            if(!memoshape.equals(""))
            mdl="MEMOSHAPEDATA_VW";
            if(!shapeclr.equals(""))
            mdl="SHAPECLRANA_VW"; 
            if(!pricegraph.equals(""))
            mdl="SHAPECLRANA_VW"; 
            if(!imageDtl.equals(""))
            mdl="IMAGEDTL_VW"; 
            if(!stkoencloseBtn.equals("")){
            mdl="ANLS_VW_OPEN_CLOSE";
            frmDte = util.nvl((String)udf.getValue("frmDtestkoenclose"));
            toDte = util.nvl((String)udf.getValue("toDtestkoenclose"));
            info.setLevel1(util.nvl((String)udf.getValue("level1openclose")));
            }
             for(int n=0;n<sttList.size();n++){
             ArrayList sttDtl = (ArrayList)sttList.get(n);
                stt = util.nvl((String)udf.getValue((String)sttDtl.get(0)));
                 if(!stt.equals("")) {
                     status.add(stt); 
                 }
            }  
            String pMdl = "Z";
                
                session.setAttribute("SLFRMDTE", frmDte);
                session.setAttribute("SLTODTE", toDte);
                if(!companalysis.equals("")){
                                pMdl = "P1";  
                                psearch="Y";   
                }
             if(btn.equals("Assort Upgrade"))
                pMdl="AS_UP";
             
                     status=new ArrayList();
                     long startm = new Date().getTime();
                     mongoAnalysisSearch(am, af, req, res,mdl,frmDte,toDte,psearch);
                     HashMap allpktListMap = (HashMap)session.getAttribute("genericPacketLstMap");
                     ArrayList genericPacketLst=util.getKeyInArrayList(allpktListMap);
                     int pktssz=genericPacketLst.size();
                     if(psearch.equals("Y")){
                         status=new ArrayList();
                         if(!p1frm.equals("")  && !p1to.equals(""))
                         status.add("P1");
                         if(!p2frm.equals("")  && !p2to.equals(""))
                         status.add("P2");
                         if(!p3frm.equals("")  && !p3to.equals(""))
                         status.add("P3");
                         
                         int statussz=status.size();
                         if(!companalysis.equals("")){
                         HashMap dscttlLst=new HashMap();
                         String period = util.nvl((String)udf.getValue("period"));
                         String periodcomp = util.nvl((String)udf.getValue("periodcomp"));  
                             for (int i=0;i<pktssz;i++) {
                                String stk_idn=(String)genericPacketLst.get(i);
                                HashMap pktPrpMap = (HashMap)allpktListMap.get(stk_idn);
                                int sal_dte_srt=(Integer)pktPrpMap.get("sl_dte_srt");
                                for (int j=0;j<statussz;j++) {
                                String pStt=(String)status.get(j);
                                int d1=0,d2=0;
                                if(pStt.equals("P1")){
                                d1=Integer.parseInt(util.convertyyyymmddFmt(p1frm));
                                d2=Integer.parseInt(util.convertyyyymmddFmt(p1to));
                                }else if(pStt.equals("P2")){
                                d1=Integer.parseInt(util.convertyyyymmddFmt(p2frm));
                                d2=Integer.parseInt(util.convertyyyymmddFmt(p2to));
                                }else if(pStt.equals("P3")){
                                d1=Integer.parseInt(util.convertyyyymmddFmt(p3frm));
                                d2=Integer.parseInt(util.convertyyyymmddFmt(p3to));
                                }    
                                if(sal_dte_srt >= d1 && sal_dte_srt <= d2){
                                String finalstt=pStt;
                                String finalsttDsp=pStt;
                                String lFmt="";
                                String lDspFmt="";
                                    if(!period.equals("ALL")){
                                        if(period.equals("M")){
                                            if(statussz==1)
                                            lFmt="yyMM";
                                            else
                                            lFmt="MM";
                                            lDspFmt="MMM yy";
                                        }else if(period.equals("W")){
                                            if(statussz==1)
                                            lFmt="yyw";
                                            else
                                            lFmt="w";
                                            lDspFmt="w yy";
                                        }else{
                                            lFmt="yy";
                                        }
                                        
                                        if(statussz==1){
                                            if(period.equals("Q")){
                                                int thisMonth=Integer.parseInt(util.formattedDate(String.valueOf(sal_dte_srt), "MM"));
                                                String quarter=thisMonth/3 <= 1 ? "Q1" : thisMonth/3 <= 2 ? "Q2" : thisMonth/3 <= 3 ? "Q3" : "Q4";
                                                finalstt=util.formattedDate(String.valueOf(sal_dte_srt), lFmt)+quarter;
                                                finalsttDsp=quarter+" "+util.formattedDate(String.valueOf(sal_dte_srt), lFmt);
                                            }else{
                                                finalstt=period+util.formattedDate(String.valueOf(sal_dte_srt), lFmt);
                                                finalsttDsp=period+util.formattedDate(String.valueOf(sal_dte_srt), lDspFmt); 
                                            }
                                        }else{
                                            if(periodcomp.equals("Y")){
                                                if(period.equals("Q")){
                                                        int thisMonth=Integer.parseInt(util.formattedDate(String.valueOf(sal_dte_srt), "MM"));
                                                        String quarter=thisMonth/3 <= 1 ? "Q1" : thisMonth/3 <= 2 ? "Q2" : thisMonth/3 <= 3 ? "Q3" : "Q4";
                                                        finalstt=pStt+quarter+util.formattedDate(String.valueOf(sal_dte_srt), lFmt);
                                                        finalsttDsp=pStt+quarter+" "+util.formattedDate(String.valueOf(sal_dte_srt), lFmt);
                                                }else{
                                                finalstt=pStt+period+util.formattedDate(String.valueOf(sal_dte_srt), lFmt);
                                                finalsttDsp=pStt+period+util.formattedDate(String.valueOf(sal_dte_srt), lDspFmt);
                                                }
                                            }else{
                                            if(period.equals("Q")){
                                                        int thisMonth=Integer.parseInt(util.formattedDate(String.valueOf(sal_dte_srt), "MM"));
                                                        String quarter=thisMonth/3 <= 1 ? "Q1" : thisMonth/3 <= 2 ? "Q2" : thisMonth/3 <= 3 ? "Q3" : "Q4";
                                                        finalstt=quarter+util.formattedDate(String.valueOf(sal_dte_srt), lFmt);
                                                        finalsttDsp=quarter+" "+util.formattedDate(String.valueOf(sal_dte_srt), lFmt);
                                            }else{
                                            finalstt=period+util.formattedDate(String.valueOf(sal_dte_srt), lFmt);
                                            finalsttDsp=period+util.formattedDate(String.valueOf(sal_dte_srt), lDspFmt);
                                            }
                                            }
                                        }
                                    }
                                pktPrpMap.put("stt", finalstt);
                                dscttlLst.put(finalstt, finalsttDsp);
                                }
                             }
                             allpktListMap.put(stk_idn,pktPrpMap);
                         }
                         session.setAttribute("dscttlLst", dscttlLst);
                         session.setAttribute("genericPacketLstMap",allpktListMap);
                     }
                     }
                     allpktListMap=new HashMap();
                     allpktListMap = (HashMap)session.getAttribute("genericPacketLstMap");
                     status=new ArrayList();
                     for (int i=0;i<pktssz;i++) {
                        String stt1 = util.nvl((String)((HashMap)allpktListMap.get(genericPacketLst.get(i))).get("stt"));
                        if(!status.contains(stt1))
                            status.add(stt1);
                     }
                     if(status.size()==0)
                     status.add("MKT");
                     else
                     Collections.sort(status);
                         
                     long endm = new Date().getTime();
                     System.out.println("@ Total Time = "+ ((endm-startm)/1000));

             
             
             int days=0;
             if(!frmDte.equals("") && !toDte.equals(""))
             days=util.daysbetweendate(frmDte,toDte);
//             if(!companalysis.equals("")){
//                 HashMap dscttlLst=new HashMap();
//                 String period = util.nvl((String)udf.getValue("period"));
//                 String periodcomp = util.nvl((String)udf.getValue("periodcomp"));
//                 if(!period.equals("ALL")){
//                 ary = new ArrayList();
//                 ary.add(period);
//                 ary.add(periodcomp);
//                 int ct = db.execCall("stk_srch", "DP_GT_ANALYSIS(pTyp => ?, pCompare => ?)", ary);  
//                 if(periodcomp.equals("N")){
//                 status=new ArrayList();
//                 String getSrchPrp = "select  distinct stt from gt_srch_rslt order by stt";
//                 rs = db.execSql(" Srch Prp ", getSrchPrp, new ArrayList());
//                 while (rs.next()) {
//                  status.add(util.nvl(rs.getString("stt")));    
//                 }
//                 rs.close();
//                     session.setAttribute("defaultstatusLst", status);
//                 }else{
//                     if(period.equals("W") && periodcomp.equals("N"))
//                     session.setAttribute("defaultstatusLst", status);
//                     
//                     if(status.size()>1){
//                     ArrayList adjstatus=new ArrayList();
//                     String firstperiodstt=util.nvl((String)status.get(0));
//                     String getSrchPrp = "select  distinct stt from gt_srch_rslt where stt like '"+firstperiodstt.trim()+"%' order by stt";
//                     rs = db.execSql(" Srch Prp ", getSrchPrp, new ArrayList());
//                     while (rs.next()) {
//                      String adjstt=util.nvl(rs.getString("stt"));
//                      adjstatus.add(adjstt); 
//                      if(status.size()>1){
//                      for(int st=1;st<status.size();st++){
//                      adjstatus.add(adjstt.replaceAll(firstperiodstt, (String)status.get(st)));
//                      }
//                      }
//                     }
//                     rs.close();  
//                     status=new ArrayList();
//                     status=adjstatus;
//                     }else{
//                         status=new ArrayList();
//                         String getSrchPrp = "select  distinct stt from gt_srch_rslt order by stt";
//                         rs = db.execSql(" Srch Prp ", getSrchPrp, new ArrayList());
//                         while (rs.next()) {
//                          status.add(util.nvl(rs.getString("stt")));    
//                         }
//                         rs.close();   
//                     }
//                     if(!period.equals("W") || periodcomp.equals("Y"))
//                     session.setAttribute("defaultstatusLst", status);
//                 }
//                 }else{
//                     session.setAttribute("defaultstatusLst", status);
//                 }
//                 String getSrchPrp = "select  distinct stt,RCHK from gt_srch_rslt order by stt";
//                 rs = db.execSql(" Srch Prp ", getSrchPrp, new ArrayList());
//                 while (rs.next()) {
//                  dscttlLst.put(util.nvl(rs.getString("stt")).trim(),util.nvl(rs.getString("RCHK")));    
//                 }
//                 rs.close();
//                 session.setAttribute("dscttlLst", dscttlLst);
//             }
                
                
             session.setAttribute("days", String.valueOf(days)); 
             session.setAttribute("GenStt", stt);
             session.setAttribute("statusLst", status);
             session.setAttribute("psearch", psearch);
                

             gridstructure(req,gridfmt);
           if(!stkoencloseBtn.equals("")){
                     return loadstkopenclose(am, af, req, res);
           }
           if(btn.equals("Assort Upgrade")){
                return loadColClrUp(am, af, req, res);
            }
           if(!giasummary.equals("")){
                 return giaSummary(am, af, req, res);
           }
            if(!reports.equals("")){
                req.setAttribute("SRCH_ID", String.valueOf(lSrchId));
                      return reports(am, af, req, res);
            }
            if(!fiftydownpt.equals("")){
                  return fiftydownpt(am, af, req, res);
            }
            if(!sizepurity.equals("")){
                  return sizepurity(am, af, req, res);
            }
            if(!memoshape.equals("")){
                  return memoshape(am, af, req, res);
            }
            if(!shapeclr.equals("")){
                  return shapeclarity(am, af, req, res);
            }
            if(!pricegraph.equals("")){
                  return pricegraphrpt(am, af, req, res);
            }
           if(!saldelation.equals("")){
               String srch_pkg = "srch_pkg.SAL_PRI_CHNG";
               int ct = db.execCall("stk_srch", srch_pkg, new ArrayList());
           }
    //        if(!srchTyp.equals("0") && !srchTyp.equals("") && psearch.equals("N")){
    //           return loadDtl(am, af, req, res);
    //        }
            if(!saldelation.equals("")){
                return loadDtl(am, af, req, res);
            }
            if(!pktdtl.equals("")){
                return loadDtl(am, af, req, res);
            }
            if(!imageDtl.equals("")){
                return pktDtl(am, af, req, res);
            }
            if(!hitmap.equals("")){
                    srchR="srchRHitMap";
                    SearchRslthitmap(req, res);
            }else{
            SearchRslt(req, res);
            }
            long lEndTime = new Date().getTime();
            long difference = lEndTime - lStartTime;
            req.setAttribute("processtm", util.convertMillis(difference));
            util.updAccessLog(req,res,"Analysis Report", "save end");
            return am.findForward(srchR);
            }
        }
    
    public void insertSrchAddon(int srchId,String grp,DBMgr db){
        String sttQry = "select stt from df_stk_stt where grp1=? and stt not like 'SUS%'  and flg='A' order by srt";
         ArrayList ary = new ArrayList();
         ary.add(grp);
        try {
            ArrayList outLst = db.execSqlLst("sttQry", sttQry, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String stt = rs.getString("stt");
                String insrtAddon = " insert into srch_addon( srch_id , cprp , cval) "+
                "select ? , ? , ?  from dual ";
                ary = new ArrayList();
                ary.add(String.valueOf(srchId));
                ary.add("STT");
                ary.add(stt);
                int cnt = db.execUpd("", insrtAddon, ary);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    }
    
    public void SearchRslthitmap( HttpServletRequest req, HttpServletResponse res)
    throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    HashMap dbinfo = info.getDmbsInfoLst();
    String ageval = (String)dbinfo.get("AGE");
    String hitval = (String)dbinfo.get("HIT");
    GenericInterface genericInt = new GenericImpl();
    ArrayList ANLSVWList=genericInt.genericPrprVw(req, res, "ANLS_VW","ANLS_VW");
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
        HashMap dbSysInfo=info.getDmbsInfoLst();  
        double MIN_INVENTORY_DAYS = Double.parseDouble((String)dbSysInfo.get("MIN_INVENTORY_DAYS"));
          double TOLERANCE_MAX_PCT = Double.parseDouble((String)dbSysInfo.get("TOLERANCE_MAX_PCT"));
          double TOLERANCE_MIN_PCT = Double.parseDouble((String)dbSysInfo.get("TOLERANCE_MIN_PCT"));
    ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList showprpLst=new ArrayList();
    int indexAge = ANLSVWList.indexOf(ageval)+1;
    int indexHit = ANLSVWList.indexOf(hitval)+1;
    String agePrp = util.prpsrtcolumn("prp",indexAge);
    String hitPrp = util.prpsrtcolumn("prp",indexHit);
    HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
    double days = Double.parseDouble((String)session.getAttribute("days"));
    ArrayList gridcommLst=new ArrayList();
    ArrayList gridrowLst=new ArrayList();
    ArrayList gridcolLst=new ArrayList();
    gridcommLst=(ArrayList)gridstructure.get("COMM");
    gridrowLst=(ArrayList)gridstructure.get("ROW");
    gridcolLst=(ArrayList)gridstructure.get("COL");
    String colPrp = util.prpsrtcolumn("prp",ANLSVWList.indexOf(gridcolLst.get(0))+1);
    String rowPrp = util.prpsrtcolumn("prp",ANLSVWList.indexOf(gridrowLst.get(0))+1);
    String conQ="";
    double perCentSold,perCentSoldQty,ratio;
    String commprpQ=getconQ("prp",gridcommLst,ANLSVWList);
    String commsrtQ=getconQ("srt",gridcommLst,ANLSVWList);
    if(indexAge!=0){
    conQ="round(sum((trunc(cts,2)*quot)*nvl("+agePrp+",0))/sum(trunc(cts,2)*quot)) age,";
    }
    if(indexHit!=0){
    conQ=conQ+"round(sum("+hitPrp+")/sum(qty)) hit,";
    }
    
    pageList=(ArrayList)pageDtl.get("SHOWPRP");
    if(pageList!=null && pageList.size() >0){
    for(int j=0;j<pageList.size();j++){
    pageDtlMap=(HashMap)pageList.get(j);
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    dflt_val=(String)pageDtlMap.get("dflt_val");
    fld_typ=(String)pageDtlMap.get("fld_typ");
    lov_qry=(String)pageDtlMap.get("lov_qry");
    int indexprp=0;
    String showgtPrp="";
    if(lov_qry.equals("PRP")){
    indexprp = ANLSVWList.indexOf(fld_ttl)+1;   
    if(!prpDspBlocked.contains(fld_ttl)){
    if(indexprp!=0){
    showgtPrp = util.prpsrtcolumn("prp",indexprp);
    if(dflt_val.equals("AVG")){
    conQ+="trunc(sum(nvl("+showgtPrp+",quot)*trunc(cts,2))/sum(trunc(cts, 2)),0) "+fld_ttl+",";  
    showprpLst.add(fld_ttl);  
    }else{
    if(!prpDspBlocked.contains(fld_typ)){
    indexprp = ANLSVWList.indexOf(fld_typ)+1; 
    if(indexprp!=0){
    showgtPrp = util.prpsrtcolumn("prp",indexprp);
    conQ+="trunc(((sum(nvl("+showgtPrp+",quot)*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2)  "+fld_ttl+",";  
    showprpLst.add(fld_ttl);  
    }
    }
    }
    }
    }
    }else if(lov_qry.equals("VLU")){
        if(!prpDspBlocked.contains(fld_typ)){
        indexprp = ANLSVWList.indexOf(fld_typ)+1; 
        if(indexprp!=0){
        showgtPrp = util.prpsrtcolumn("prp",indexprp);
        conQ+="trunc(sum(trunc(cts,2)*nvl("+showgtPrp+",quot)), 0) "+fld_ttl+",";
        showprpLst.add(fld_ttl);  
        }else{
            conQ+="trunc(sum(trunc(cts,2)*nvl("+fld_typ+",quot)), 0) "+fld_ttl+",";
            showprpLst.add(fld_ttl);   
        }
        }
    }
    }
    }
    int showprpLstsz=showprpLst.size();
//    String shSzSql = "select distinct "+commprpQ+","+commsrtQ+" from gt_srch_rslt order by "+commsrtQ;
//    ResultSet rs = db.execSql("shSzSql", shSzSql, new ArrayList());
//    ArrayList keyList = new ArrayList();
//    while(rs.next()){
//    String key="";
//    for (int i = 0; i < gridcommLst.size(); i++) {
//            int index = ANLSVWList.indexOf(gridcommLst.get(i))+1;    
//            String fld=util.prpsrtcolumn("prp",index);
//            key += "_" + rs.getString(fld);
//    }
//    key=key.replaceFirst("\\_", "");    
//    keyList.add(key);
//    }
//        rs.close();

    String getTotal = "select stt, sum(qty) qty, trunc(sum(trunc(cts, 2)),2) cts " +
    ", trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),0) avg " +
    " , trunc(sum(rap_rte*trunc(cts,2))/sum(trunc(cts, 2)),2) rap_avg "+
    ", trunc(((sum(quot*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2) avg_dis,trunc(sum(trunc(cts,2)*quot), 0) vlu " +
    " ,"+conQ+rowPrp+", "+colPrp+" " +
    " from gt_srch_rslt where 1 = 1 " + //+ dysQ
    " group by "+rowPrp+", "+colPrp+", stt    " ;
      ArrayList outLst = db.execSqlLst("getTotal", getTotal, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    HashMap dataDtl = new HashMap();
        ArrayList colList = new ArrayList();
        ArrayList rowList = new ArrayList();
    while(rs.next()){
        String rowVal = util.nvl(rs.getString(rowPrp));
        String colVal = util.nvl(rs.getString(colPrp));
        String status = rs.getString("stt");
        String key="";
        key = rowVal+"@"+colVal;
    if(!rowList.contains(rowVal) && !rowVal.equals(""))
        rowList.add(rowVal);
    if(!colList.contains(colVal) && !colVal.equals(""))
        colList.add(colVal);
    dataDtl.put(key+"@"+status+"@QTY", util.nvl(rs.getString("qty")));
    dataDtl.put(key+"@"+status+"@CTS", util.nvl(rs.getString("cts")));
    dataDtl.put(key+"@"+status+"@AVG", util.nvl(rs.getString("avg")));
    dataDtl.put(key+"@"+status+"@VLU", util.nvl(rs.getString("vlu")));
    dataDtl.put(key+"@"+status+"@RAP", util.nvl(rs.getString("avg_dis")));
    if(indexAge!=0)
    dataDtl.put(key+"@"+status+"@AGE", util.nvl(rs.getString("age")));
    if(indexHit!=0)    
    dataDtl.put(key+"@"+status+"@HIT", util.nvl(rs.getString("hit")));
    for(int s=0;s<showprpLstsz;s++){
    String showprp=(String)showprpLst.get(s);
    dataDtl.put(key+"@"+status+"@"+showprp, util.nvl(rs.getString(showprp)));        
    }
    }
    rs.close();
    pst.close();
    session.setAttribute("rowList", rowList);
    session.setAttribute("colList", colList);
    session.setAttribute("showprpLst", showprpLst);
    
    HashMap redMap=new HashMap();
    HashMap greenMap=new HashMap();
    HashMap yellowMap=new HashMap();
        HashMap redSortedMap=new HashMap();
        HashMap greenSortedMap=new HashMap();
        HashMap yelloSortedwMap=new HashMap();
                for(int r=0;r< rowList.size();r++){
                    String rowV = (String)rowList.get(r);
                        for(int c=0;c< colList.size();c++){
                            String colV = (String)colList.get(c);
                            String commonkey=rowV+"@"+colV;
                            String mktkeyQty = commonkey+"@MKT@QTY";
                            String soldkeyQty = commonkey+"@SOLD@QTY";
                            String mktkeyVlu = commonkey+"@MKT@VLU";
                            String soldkeyVlu = commonkey+"@SOLD@VLU";
                            String mktQty = util.nvl((String)dataDtl.get(mktkeyQty),"0");
                            String soldQty = util.nvl((String)dataDtl.get(soldkeyQty),"0");
                            String mktVlu = util.nvl((String)dataDtl.get(mktkeyVlu),"0");
                            String soldVlu = util.nvl((String)dataDtl.get(soldkeyVlu),"0");
                            String svpd="";
                            String msd="";
                            ratio=0;
                            if(!soldQty.equals("0")){
                                 ratio=util.roundToDecimals(((Double.parseDouble(soldQty)*MIN_INVENTORY_DAYS/days)/Double.parseDouble(mktQty)),2); 
                                 if(ratio > (1 + (TOLERANCE_MAX_PCT/100)))
                                 greenMap.put(commonkey, ratio);
                                else if(ratio < (1 - (TOLERANCE_MAX_PCT/100)))
                                 redMap.put(commonkey, ratio);
                                 else
                                 yellowMap.put(commonkey, ratio);
                                if(!soldVlu.equals("") && !soldVlu.equals("0")) {
                                     svpd=util.nvl(String.valueOf(Math.round(Double.parseDouble(soldVlu)/days)),"0");
                                     if(!mktVlu.equals("") && !mktVlu.equals("0") && !svpd.equals("0"))
                                     msd=util.nvl(String.valueOf(Math.round(Double.parseDouble(mktVlu)/Double.parseDouble(svpd))),"0");
                                     if(svpd.equals("0"))
                                     svpd="";
                                    dataDtl.put(commonkey+"@SOLD@SVPD",svpd);
                                    dataDtl.put(commonkey+"@MKT@MSD",msd);
                                }
                            }else{
                                if(!soldQty.equals("0") || !mktQty.equals("0"))
                                redMap.put(commonkey, Double.parseDouble(mktVlu)*-1);
                            }
                        }
                }

    
    if(yellowMap!=null && yellowMap.size()!=0)
        yelloSortedwMap=(HashMap) sortByComparator(yellowMap);
    
    if(greenMap!=null && greenMap.size()!=0)
            greenSortedMap=(HashMap) sortByComparator(greenMap);
        
     if(redMap!=null && redMap.size()!=0)
            redSortedMap=(HashMap) sortByComparator(redMap);
    
        session.setAttribute("redSortedMap", redSortedMap);
        session.setAttribute("greenSortedMap", greenSortedMap);
        session.setAttribute("yelloSortedwMap", yelloSortedwMap);
        session.setAttribute("dataDtl", dataDtl);
    }
    
    public  Map<String, Double> sortByComparator(Map<String, Double> unsortMap) {
      
         // Convert Map to List
         List<Map.Entry<String, Double>> list = 
           new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());
      
         // Sort list with comparator, to compare the Map values
         Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
           public int compare(Map.Entry<String, Double> o1,
                                                Map.Entry<String, Double> o2) {
             return ((Comparable) ((Map.Entry<String, Double>) (o1)).getValue())
                               .compareTo(((Map.Entry<String, Double>) (o2)).getValue());
           }
         });
      
         // Convert sorted map back to a Map
         Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
             for (Iterator<Map.Entry<String, Double>> it = list.iterator(); it.hasNext();) {
               Map.Entry<String, Double> entry = it.next();
               sortedMap.put(entry.getKey(), entry.getValue());
             }
             return sortedMap;
        
       }
    
    public  Map<String, HashMap> sortByComparatorMap(Map<String, HashMap> unsortMap,final String sortby) {
     
        // Convert Map to List
        List<Map.Entry<String, HashMap>> list = 
          new LinkedList<Map.Entry<String, HashMap>>(unsortMap.entrySet());
     
        // Sort list with comparator, to compare the Map values
        Collections.sort(list, new Comparator<Map.Entry<String, HashMap>>() {
          public int compare(Map.Entry<String, HashMap> o1,
                                               Map.Entry<String, HashMap> o2) {
            return ((Comparable) ((Map.Entry<String, HashMap>) (o1)).getValue().get(sortby) )
                              .compareTo(((Map.Entry<String, HashMap>) (o2)).getValue().get(sortby));
          }
        });
     
        // Convert sorted map back to a Map
        Map<String, HashMap> sortedMap = new LinkedHashMap<String, HashMap>();
            for (Iterator<Map.Entry<String, HashMap>> it = list.iterator(); it.hasNext();) {
              Map.Entry<String, HashMap> entry = it.next();
              sortedMap.put(entry.getKey(), entry.getValue());
            }
            return sortedMap;
       
      }
        public ActionForward pktDtl(ActionMapping am, ActionForm form,
                                  HttpServletRequest req,
                                  HttpServletResponse res) throws Exception {
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
                util.updAccessLog(req,res,"Analysis Report", "pktDtl start");
                GenericInterface genericInt = new GenericImpl();
            ArrayList pktList = new ArrayList();
            ArrayList vwPrpLst =genericInt.genericPrprVw(req,res,"IMAGEDTL_VW","IMAGEDTL_VW");
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
            ArrayList itemHdr = new ArrayList();
            ArrayList prpDspBlocked = info.getPageblockList();
            itemHdr.add("Sr");
            itemHdr.add("VNM");
            itemHdr.add("status");
            int counter=1;
            String  srchQ =  " select stk_idn , pkt_ty,  vnm,to_char(pkt_dte,'dd-mm-yyyy') pkt_dte, stt , qty , to_char(cts,'9999999999990.99') cts , srch_id , cmp , rmk, kts_vw kts , byr , emp ,to_char(sl_dte,'dd-mm-yyyy') sl_dte,"+
                  "  decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis ,rap_rte, quot , cmnt , trunc(cts*quot,2) amt,to_char(((quot/rap_rte*100)-100),'999999990.00') slback,"+
                  " nvl(fnl_usd, quot) FixRate,trunc(cts*nvl(fnl_usd, quot),2) FixRateAmt,  decode(rap_rte, 1, '', trunc(((nvl(fnl_usd, quot)/greatest(rap_rte,1))*100)-100,2)) FixRateDisc, "+
                "decode(CERTIMG,'','N','N','N',CERTIMG) CERTIMG,"+
                "decode(DIAMONDIMG,'','N','N','N',DIAMONDIMG) DIAMONDIMG,"+
            "decode(JEWIMG,'','N','N','N',JEWIMG) JEWIMG,"+
            "decode(SRAYIMG,'','N','N','N',SRAYIMG) SRAYIMG,"+
            "decode(AGSIMG,'','N','N','N',AGSIMG) AGSIMG,"+
            "decode(MRAYIMG,'','N','N','N',MRAYIMG) MRAYIMG,"+
            "decode(RINGIMG,'','N','N','N',RINGIMG) RINGIMG,"+
            "decode(LIGHTIMG,'','N','N','N',LIGHTIMG) LIGHTIMG,"+
            "decode(REFIMG,'','N','N','N',REFIMG) REFIMG,"+
            "decode(VIDEOS,'','N','N','N','Y') VIDEOS,"+
            "decode(VIDEO_3D,'','N','N','N','Y') VIDEO_3D";
            for (int i = 0; i < vwPrpLst.size(); i++) {
                 String lprp=(String)vwPrpLst.get(i);
                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                    fld += "00" + j;
                else if (j < 100)
                    fld += "0" + j;
                else if (j > 100)
                    fld += j;

               srchQ += ", " + fld;
                 if(prpDspBlocked.contains(lprp)){
                 }else{
                 itemHdr.add(lprp);
                 }
             }

            String rsltQ = srchQ + " from gt_srch_rslt where 1=1 order by sk1,stk_idn, cts";
            
            ArrayList ary = new ArrayList();
            
              ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
            try {
                while(rs.next()) {
                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap.put("status", util.nvl(rs.getString("stt")));
                    String vnm = util.nvl(rs.getString("vnm"));
                    pktPrpMap.put("VNM",vnm);
                    pktPrpMap.put("Sr",String.valueOf(counter++));
                    pktPrpMap.put("byr",util.nvl(rs.getString("byr"))); 
                    pktPrpMap.put("emp",util.nvl(rs.getString("emp"))); 
                    pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                    pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                    pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                    pktPrpMap.put("rap_rte",util.nvl(rs.getString("rap_rte")));
                    pktPrpMap.put("amt",util.nvl(rs.getString("amt")));
                    pktPrpMap.put("pkt_dte",util.nvl(rs.getString("pkt_dte")));
                    pktPrpMap.put("sl_dte",util.nvl(rs.getString("sl_dte")));
                    pktPrpMap.put("issIdn",util.nvl(rs.getString("srch_id")));
                    pktPrpMap.put("cmp",util.nvl(rs.getString("cmp")));
                    pktPrpMap.put("quot",util.nvl(rs.getString("quot")));
                    pktPrpMap.put("slback",util.nvl(rs.getString("slback")));
                    pktPrpMap.put("Rate",util.nvl(rs.getString("FixRate")));
                    pktPrpMap.put("RateAmt",util.nvl(rs.getString("FixRateAmt")));
                    pktPrpMap.put("RateDisc",util.nvl(rs.getString("FixRateDisc")));
                    pktPrpMap.put("Remark",util.nvl(rs.getString("rmk")));
                    pktPrpMap.put("CERTIMG",util.nvl(rs.getString("CERTIMG")));
                    pktPrpMap.put("DIAMONDIMG",util.nvl(rs.getString("DIAMONDIMG")));
                    pktPrpMap.put("JEWIMG",util.nvl(rs.getString("JEWIMG")));
                    pktPrpMap.put("SRAYIMG",util.nvl(rs.getString("SRAYIMG")));
                    pktPrpMap.put("AGSIMG",util.nvl(rs.getString("AGSIMG")));
                    pktPrpMap.put("MRAYIMG",util.nvl(rs.getString("MRAYIMG")));
                    pktPrpMap.put("RINGIMG",util.nvl(rs.getString("RINGIMG")));
                    pktPrpMap.put("LIGHTIMG",util.nvl(rs.getString("LIGHTIMG")));
                    pktPrpMap.put("REFIMG",util.nvl(rs.getString("REFIMG")));
                    pktPrpMap.put("VIDEOS",util.nvl(rs.getString("VIDEOS")));
                    pktPrpMap.put("VIDEO_3D",util.nvl(rs.getString("VIDEO_3D")));
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
                                     val = util.nvl(rs.getString("r_dis"));
                                 if (prp.toUpperCase().equals("RAP_RTE"))
                                     val = util.nvl(rs.getString("rap_rte"));
                                 if(prp.equals("RTE"))
                                     val = util.nvl(rs.getString("cmp"));
                                 if(prp.equals("KTSVIEW"))
                                     val = util.nvl(rs.getString("kts"));
                                 if(prp.equals("COMMENT"))
                                     val = util.nvl(rs.getString("cmnt"));
                            
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
//            itemHdr.add("CERTIMG");
//            itemHdr.add("DIAMONDIMG");
//            itemHdr.add("JEWIMG");
//            itemHdr.add("SRAYIMG");
//            itemHdr.add("AGSIMG");
//            itemHdr.add("MRAYIMG");
//            itemHdr.add("RINGIMG");
//            itemHdr.add("LIGHTIMG");
//            itemHdr.add("REFIMG");
//            itemHdr.add("VIDEOS");
//            itemHdr.add("VIDEO_3D");
            session.setAttribute("pktList", pktList);
            session.setAttribute("itemHdr",itemHdr);
                util.updAccessLog(req,res,"Analysis Report", "pktDtl end");
            return am.findForward("imageDtl");
            }
        }
        
    public ActionForward bulkDowenload(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
    GenericReportForm udf = (GenericReportForm)form;
    ArrayList imagelistDtl= (info.getImagelistDtl() == null)?new ArrayList():(ArrayList)info.getImagelistDtl();
    ArrayList pktDtlList = (ArrayList)session.getAttribute("pktList");
    HashMap dtls=new HashMap();
        HashMap dbmsInfo = info.getDmbsInfoLst();
        String bucketName     = util.nvl((String)dbmsInfo.get("BKTNME")).trim();
        String s3key     = util.nvl((String)dbmsInfo.get("S3KEY")).trim();
        String s3val     = util.nvl((String)dbmsInfo.get("S3VAL")).trim();
            
        InputStream is = null;
        String fileNmzip = "BlkImages_"+util.getToDteTime()+".zip";
        AWSCredentials myCredentials = new BasicAWSCredentials(s3key, s3val); 
        AmazonS3Client s3Client = new AmazonS3Client(myCredentials); 
        ArrayList<File> filesToAdd = new ArrayList<File>();
        String contextpath=session.getServletContext().getRealPath("/");
        for(int j=0 ; j <pktDtlList.size() ;j++){
        HashMap pktDtl = (HashMap)pktDtlList.get(j);
        String vnm=util.nvl((String)pktDtl.get("VNM"));
        String filePathrootDw = contextpath+vnm;
        new File(filePathrootDw).mkdir();
        if(imagelistDtl!=null && imagelistDtl.size() >0){
        for(int k=0;k<imagelistDtl.size();k++){
        dtls=new HashMap();
        dtls=(HashMap)imagelistDtl.get(k);
        String path=util.nvl((String)dtls.get("PATH"));
        String gtCol=util.nvl((String)dtls.get("GTCOL")).toUpperCase();
        String val=util.nvl((String)pktDtl.get(gtCol));
        if(!val.equals("N")){
        if (val.indexOf("&") > -1) {
        val = val.replaceAll("&", "%26");
        }
        if(path.indexOf("segoma") > -1 || path.indexOf("sarineplatform") > -1 || path.indexOf(".html") > -1){
        }else{
            String typ="";
            if(val.indexOf("jpeg") > -1) {
                typ="image/jpeg";
            }
            if(val.indexOf("jpg") > -1) {
                typ="image/jpg";
            }
            if(val.indexOf("JPG") > -1) {
                typ="image/JPG";
            }
            if(val.indexOf("png") > -1) {
                typ="image/png";
            }
            if(val.indexOf("gif") > -1) {
                typ="image/gif";
            }
            if(val.indexOf("mov") > -1) {
                typ="video/quicktime";
            }
            if(val.indexOf("wmv") > -1) {
                typ="video/x-ms-wmv";
            }
            if(val.indexOf("mp4") > -1) {
                typ="video/mp4";
            }
            if(val.indexOf("txt") > -1) {
                typ="text/plain";
            } 
            if(val.indexOf("doc") > -1) {
                typ="application/msword";
            } 
            if(val.indexOf("pdf") > -1) {
                typ="application/pdf";
            } 
            if(val.indexOf("gif") > -1) {
                typ="image/gif";
            }
            if(val.indexOf("html") > -1) {
                typ="text/html";
            }
            if(!typ.equals("")){
            path=path+val;
            String filept =path.substring(path.indexOf("com/")+4, path.length());
            String filenm=filept.substring(filept.lastIndexOf("/")+1, filept.length());
            s3Client.getObject(new GetObjectRequest(bucketName, filept),new File(filePathrootDw+"/"+k+filenm));
            filesToAdd.add(new File(filePathrootDw+"/"+k+filenm));
        }
        }
        }
        }
        }
        }
        try {
        ZipFile zipFile = new ZipFile(contextpath+"/"+fileNmzip);
        zipFile.addFiles(filesToAdd, new ZipParameters());
        } 
        catch (ZipException e) 
        {
          e.printStackTrace();
        }
            try{
                ServletOutputStream out = res.getOutputStream();
                res.setContentType("application/zip");
                res.setHeader("Content-Disposition", "attachment; filename=" + fileNmzip);
                FileInputStream fis = new FileInputStream(contextpath+"/"+fileNmzip);
                int             i   = 0;

                while ((i = fis.read()) != -1) {
                    out.write(i);
                }

                fis.close();
                out.flush();
                out.close();

            }catch(IOException ex){
               ex.printStackTrace();
            }
            
    }
    return null;
    }
    
    public static void copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
            byte[] buf = new byte[bufferSize];
            int n = input.read(buf);
            while (n >= 0) {
                output.write(buf, 0, n);
                n = input.read(buf);
            }
            output.flush();
        }
    public ActionForward loadColClrUp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
           util.updAccessLog(req,res,"Analysis Report", "loadColClrUp start");
       GenericReportForm udf = (GenericReportForm)af;
       ArrayList ary = new ArrayList();
       String upgrade = util.nvl((String)udf.getValue("upgrade"));
       String  numjob = util.nvl((String)info.getDmbsInfoLst().get("COLCLCNT"));
       int ct = db.execCall("delete", "delete from gt_srch_rapup", new ArrayList());
       int numjobCnt = Integer.parseInt(numjob);
       String gt_srch_rapupIns =  "insert into gt_srch_rapup(stk_idn,lab, typ) select stk_idn, cert_lab, ? from gt_srch_rslt ";
       ary = new ArrayList();
       ary.add(upgrade);
        ct = db.execUpd("gt_srch_rapup", gt_srch_rapupIns, ary);
       int gtCount = ct;
       String delsrch_rapup = "delete from srch_rapup a where exists (select 1 from gt_srch_rapup b where a.stk_idn = b.stk_idn)";
       ct = db.execUpd("delsrch_rapup", delsrch_rapup, new ArrayList());
       
       String srch_rapup = "insert into srch_rapup(stk_idn, typ) select a.stk_idn, ? from gt_srch_rapup a ";
       ary = new ArrayList();
       ary.add(upgrade);
        ct = db.execUpd("srch_rapup", srch_rapup, ary);
        
//        ResultSet rs1 = db.execSql("numJob", "select least(ceil(("+gtCount+"*5/60)/5),10) from dual", new ArrayList());
//        if(rs1.next());
//        numjobCnt = rs1.getInt(1);
        
       String reprc = "reprc(num_job => ?, lstt1 => ?, lstt2 => ?,lSeq=> ?)";
       ary = new ArrayList();
       ary.add(String.valueOf(numjobCnt));
       ary.add("REP");
       ary.add("REP");
       ArrayList out = new ArrayList();
       out.add("I");
       CallableStatement cnt = db.execCall(" reprc : ",reprc, ary,out );
       int  lseq = cnt.getInt(ary.size()+1);
       cnt.close();
       int outVal=-1;
       Thread th = new Thread();
       while(outVal!=0){
       String df_pricing_status = "select count(*) from pri_batch where pri_seq = ? and flg ='-1'";
       ary = new ArrayList();
       ary.add(String.valueOf(lseq));
       ArrayList outLst = db.execSqlLst("price Status", df_pricing_status, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
        if (rs.next()){
           outVal = rs.getInt(1);
           if(outVal!=0)
          th.sleep(15000);
       

        }
           rs.close();
           pst.close();
       }
       HashMap params = new HashMap();
       params.put("upgrade", upgrade);
       ArrayList pktList = PktsDtlList(req ,res, params);
       session.setAttribute("pktList", pktList);
           util.updAccessLog(req,res,"Analysis Report", "loadColClrUp end");
       return am.findForward("colClrUp");
       }
   }
    public ActionForward ColClrUpFlt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             util.updAccessLog(req,res,"Analysis Report", "ColClrUpFlt start");
       GenericReportForm udf = (GenericReportForm)af;
         String upgrade = util.nvl((String)udf.getValue("upgrade"));
         String colfrmval = util.nvl((String)udf.getValue("colFrmVal"));
         String coltoval = util.nvl((String)udf.getValue("colToVal"));
         String clrfrmval = util.nvl((String)udf.getValue("clrFrmVal"));
         String clrtoval = util.nvl((String)udf.getValue("clrToVal"));
         String bothfrmval = util.nvl((String)udf.getValue("bothFrmVal"));
         String bothtoval = util.nvl((String)udf.getValue("bothToVal"));
         HashMap params = new HashMap();
         params.put("upgrade", upgrade);
         params.put("colFrmVal", colfrmval);
         params.put("colToVal", coltoval);
         params.put("clrFrmVal", clrfrmval);
         params.put("clrToVal", clrtoval);
         params.put("bothFrmVal", bothfrmval);
         params.put("bothToVal", bothtoval);
         ArrayList pktList = PktsDtlList(req ,res, params);
         session.setAttribute("pktDtlList", pktList);
             util.updAccessLog(req,res,"Analysis Report", "ColClrUpFlt end");
         return am.findForward("colClrUp");
         }
     }
    public ArrayList PktsDtlList(HttpServletRequest req ,HttpServletResponse res, HashMap params){
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
        ArrayList pktDtlList = new ArrayList();
        String upgrade = util.nvl((String)params.get("upgrade"));
        String colfrmVal = util.nvl((String)params.get("colFrmVal"));
        String coltoVal = util.nvl((String)params.get("colToVal"));
        String clrfrmVal = util.nvl((String)params.get("clrFrmVal"));
        String clrtoVal = util.nvl((String)params.get("clrToVal"));
        String bothfrmVal = util.nvl((String)params.get("bothFrmVal"));
        String bothtoVal = util.nvl((String)params.get("bothToVal"));
        ArrayList colclrList = genericInt.genericPrprVw(req, res, "CO_CLR_UPG_VW","CO_CLR_UPG_VW");
        ArrayList itemsHdr = new ArrayList();
        itemsHdr.add("SRNO");
        itemsHdr.add("PacketCode");
        for(int j=0;j < colclrList.size() ; j++){
            String lprp = (String)colclrList.get(j);
            itemsHdr.add(lprp);
            if(lprp.equals("RAP_DIS")){
                if(upgrade.equals("COL") || upgrade.equals("BOTH")){
                itemsHdr.add("ByColProp");
                itemsHdr.add("ByColPrice");
                itemsHdr.add("ByColOff");
                itemsHdr.add("ByColDiff%");
                }
                 if(upgrade.equals("CLR") || upgrade.equals("BOTH")){
                itemsHdr.add("ByPurProp");
                itemsHdr.add("ByPurPrice");
                itemsHdr.add("ByPurOff");
                itemsHdr.add("ByPurDiff%");
                 }
                 if(upgrade.equals("BOTH")){
                itemsHdr.add("ByBothProp");
                itemsHdr.add("ByBothPrice");
                itemsHdr.add("ByBothOff");
                itemsHdr.add("ByBothDiff%");
                 }
                
             }
        }
        session.setAttribute("itemHdr", itemsHdr);
        int colIndex = colclrList.indexOf("COL")+1;
        int clrIndex = colclrList.indexOf("CLR")+1;
        String color_col= util.prpsrtcolumn("prp",colIndex);
        String clr_col= util.prpsrtcolumn("prp",clrIndex);
        String   srchQ = " select rank() over(partition by a.vnm order by a.sk1, a.vnm) sr " + 
         " , a.vnm, a.cts, a.rap_rte, a.quot ,col_rap , clr_rap , col_clr_rap , decode(a.rap_rte, 1, '', decode(least(a.rap_dis, 0),0, '+'||a.rap_dis, a.rap_dis))  r_dis , a."+color_col+" , a."+clr_col+" , a.cmp, a.rap_dis " + 
        ", b.colup||'/'||a."+clr_col+" ByCol, col_rte, trunc((col_rte/col_rap*100)-100, 2) ByColOff,\n" + 
        " trunc((col_rte/a.cmp*100) - 100, 2) ByColDiff\n" + 
        ", a."+color_col+"||'/'||b.clrup ByClr, clr_rte, trunc((clr_rte/clr_rap*100)-100, 2) ByClrOff, \n" + 
        "trunc((clr_rte/a.cmp*100) - 100, 2) ByClrDiff\n" + 
        ", b.colup||'/'||b.clrup ByBoth, col_clr_rte, trunc((col_clr_rte/col_clr_rap*100)-100, 2) ByBothOff, \n" + 
        "trunc((col_clr_rte/a.cmp*100) - 100, 2) ByBothDiff ";

        for (int i = 0; i < colclrList.size(); i++) {
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
       srchQ+= " from gt_srch_rslt a , srch_rapup b " + 
       " where a.stk_idn = b.stk_idn " ;
   
          if(!colfrmVal.equals("") && !coltoVal.equals(""))
               srchQ+= " and ( trunc((col_rte/col_rap*100) - 100, 2) between "+colfrmVal+" and "+coltoVal+" ) ";  
          if(!clrfrmVal.equals("") && !clrtoVal.equals(""))
               srchQ+= " and (trunc((clr_rte/clr_rap*100) - 100, 2) between "+clrfrmVal+" and "+clrtoVal+") ";
          if(!bothfrmVal.equals("") && !bothtoVal.equals(""))
               srchQ+= " and  (trunc((col_clr_rte/col_clr_rap*100) - 100, 2) between "+bothfrmVal+" and "+bothtoVal+") ";
       
         srchQ+=   " order by a.sk1, a.vnm ";
      int cnt=0;
      ArrayList outLst = db.execSqlLst("srchQ", srchQ, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                cnt++;
                HashMap pktDtl = new HashMap();
                pktDtl.put("SRNO", String.valueOf(cnt));
                pktDtl.put("PacketCode",util.nvl(rs.getString("vnm")));
                pktDtl.put("ByColProp", util.nvl(rs.getString("ByCol")));
                pktDtl.put("ByColPrice",  util.nvl(rs.getString("col_rte")));
                pktDtl.put("ByColOff",  util.nvl(rs.getString("ByColOff")));
                pktDtl.put("ByColDiff%",  util.nvl(rs.getString("ByColDiff")));
                pktDtl.put("ByPurProp",  util.nvl(rs.getString("ByClr")));
                pktDtl.put("ByPurPrice",  util.nvl(rs.getString("clr_rte")));
                pktDtl.put("ByPurOff",  util.nvl(rs.getString("ByClrOff")));
                pktDtl.put("ByPurDiff%",  util.nvl(rs.getString("ByClrDiff")));
                pktDtl.put("ByBothProp",  util.nvl(rs.getString("ByBoth")));
                pktDtl.put("ByBothPrice",  util.nvl(rs.getString("col_clr_rte")));
                pktDtl.put("ByBothOff",  util.nvl(rs.getString("ByBothOff")));
                pktDtl.put("ByBothDiff%",  util.nvl(rs.getString("ByBothDiff")));
                String clrRap  = util.nvl(rs.getString("clr_rap"));
                String colRap  = util.nvl(rs.getString("col_rap"));
                String colclrRap  = util.nvl(rs.getString("col_clr_rap"));
                for(int i=0; i < colclrList.size(); i++){
                     String prp = (String)colclrList.get(i);
                      
                      String fld="prp_";
                    int j = i + 1;
                    if (j < 10)
                        fld += "00" + j;
                    else if (j < 100)
                        fld += "0" + j;
                    else if (j > 100)
                        fld += j;
                    if(prp.equals("RAP_DIS"))
                        fld="r_dis";
                    if(prp.equals("RTE"))
                        fld="quot";
                    if(prp.equals("RAP_RTE"))
                        fld="rap_rte";
                    
                     String val = util.nvl(rs.getString(fld)) ;
                    pktDtl.put(prp, val);
                }
                pktDtlList.add(pktDtl);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return pktDtlList;
    }
   
    public ActionForward inOutSummarymonth(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
               util.updAccessLog(req,res,"Analysis Report", "inOutSummarymonth start");
           GenericInterface genericInt = new GenericImpl();
           String typ = req.getParameter("typ");
           String row="",col="";
           ArrayList gridcommLst=new ArrayList();
           ArrayList gridrowLst=new ArrayList();
           ArrayList gridcolLst=new ArrayList();
           HashMap openingValLst=new HashMap();
           HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
           gridcommLst=(ArrayList)gridstructure.get("COMM");
           gridrowLst=(ArrayList)gridstructure.get("ROW");
           gridcolLst=(ArrayList)gridstructure.get("COL");
           int gridcommLstsz=gridcommLst.size();
           int gridrowLstsz=gridrowLst.size();
           int gridcolLstsz=gridcolLst.size();
           ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "ANLS_VW","ANLS_VW");
           int autPurRte = vwPrpLst.indexOf("ACT_NET_PUR_RTE")+1;
               String autPurPrp = "";
              
               String autPurVlu="";
               if(autPurRte > 0){
                   if(autPurRte < 10)
                       autPurPrp="prp_00"+autPurRte;
                   else
                   autPurPrp="prp_0"+autPurRte;   
                   autPurVlu=" , trunc(sum("+autPurPrp+"*trunc(cts,2))/sum(trunc(cts, 2)),2) aurPurAvg , trunc(sum(trunc(cts,2)*nvl("+autPurPrp+", quot)), 0) aurPurVlu ";
               }
           String key = req.getParameter("key");
           ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
                 String statusALL= statusLst.toString();
                 statusALL = statusALL.replace("[", "");
                 statusALL = statusALL.replace("]", "");
                 statusALL = statusALL.replaceAll(",", "','");
                 String[] param = key.split("@");
               if(key.length() > 0){
                     String Check = req.getParameter("row");
                       if(Check!=null && !Check.equals("")){
                       if(param.length > 0) {
                           row = Check;
                           col = req.getParameter("col");
                       }    
                     }else{
                         row = param[gridcommLstsz];
                         col = param[gridcommLstsz+gridrowLstsz];
                     }
                     }  
                       String condQ="";
                       if(!key.equals("")){
                               for (int i = 0; i < gridcommLstsz; i++) {
                               int index = vwPrpLst.indexOf(gridcommLst.get(i))+1;    
                               String fldprp=util.prpsrtcolumn("prp",index);
                               condQ += " and ( "+fldprp+"= '+"+param[i].trim()+"' or "+fldprp+"= '"+param[i]+"') ";
                               }    
                               if(!row.equals("ALL")){
                               int index = vwPrpLst.indexOf(gridrowLst.get(0))+1;    
                               String fldprp=util.prpsrtcolumn("prp",index);
                               condQ +=" and "+fldprp+" in ('"+row+"') ";
                               }
                               if(!col.equals("ALL")){
                               int index = vwPrpLst.indexOf(gridcolLst.get(0))+1;    
                               String fldprp=util.prpsrtcolumn("prp",index);
                               condQ +=" and "+fldprp+" in ('"+col+"') ";
                               }
                               } 
             String openingSql = "select count(qty) qty,to_char(trunc(sum(trunc(cts, 2)),2),'9999999999999990.00') cts,trunc(sum(trunc(cts,2)*quot), 0) vlu,\n" + 
             "trunc(sum(trunc(cts,2)*nvl(fnl_usd, quot)), 0) fnl_usdvlu "+autPurVlu+" from gt_srch_rslt where 1 = 1 "+ condQ +  
                                 " and to_number(to_char(trunc(pkt_dte), 'rrrrmm')) < (select to_number(to_char(trunc(min(pkt_dte)), 'rrrrmm')) " +
                                 " from gt_srch_rslt where 1 = 1 "+ condQ +")";
             ArrayList outLst = db.execSqlLst("openingSql", openingSql, new ArrayList());
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet rs1 = (ResultSet)outLst.get(1);
               if(rs1.next()){
                   openingValLst.put("OPENSTR", rs1.getInt("qty"));
                   openingValLst.put("OPENSTR_CTS", rs1.getDouble("cts"));
                   openingValLst.put("OPENSTR_VLU", rs1.getDouble("vlu"));
                   openingValLst.put("OPENSTR_NETVLU", rs1.getDouble("fnl_usdvlu"));
                   if(!autPurVlu.equals("")){
                       openingValLst.put("OPENSTR_PURAVG", rs1.getDouble("aurPurAvg"));
                       openingValLst.put("OPENSTR_PURVLU", rs1.getDouble("aurPurVlu"));
                   }
               }
               rs1.close();
               pst.close();
           String summary = "select 'NEW' DSP_STT, count(qty) NW_GDS,to_char(trunc(sum(trunc(cts, 2)),2),'9999999999999990.00') cts,trunc(sum(trunc(cts,2)*quot), 0) vlu,\n" + 
           "trunc(sum(trunc(cts,2)*nvl(fnl_usd, quot)), 0) fnl_usdvlu, " + 
                            "  to_number(to_char(trunc(nvl(pkt_dte,sysdate)), 'rrrrmm')) srt_mon " + 
                            " , to_char(trunc(nvl(pkt_dte,sysdate)), 'MON rrrr') DSP_MON,trunc(sum(fnl_usd*trunc(cts,2))/sum(trunc(cts, 2)),2) usdAvg " +autPurVlu+
                              " from GT_SRCH_RSLT Where stt = stt " + condQ + 
     //    " and "+shPrp+" = '"+sh+"' and ( "+szPrp+"= '+"+sz+"' or "+szPrp+" = '"+sz+"')"+Color+" "+Clarity+
           " group by to_char(trunc(nvl(pkt_dte,sysdate)), 'MON rrrr'), to_number(to_char(trunc(nvl(pkt_dte,sysdate)), 'rrrrmm'))\n" + 
           " UNION " + 
          " select 'NEW_SOLD' DSP_STT, count(qty) NW_GDS,to_char(trunc(sum(trunc(cts, 2)),2),'9999999999999990.00') cts,trunc(sum(trunc(cts,2)*quot), 0) vlu,\n" + 
          "trunc(sum(trunc(cts,2)*nvl(fnl_usd, quot)), 0) fnl_usdvlu, "+
           "  to_number(to_char(trunc(nvl(sl_dte,sysdate)), 'rrrrmm')) srt_mon "+
            " , to_char(trunc(nvl(sl_dte,sysdate)), 'MON rrrr') DSP_MON,trunc(sum(fnl_usd*trunc(cts,2))/sum(trunc(cts, 2)),2) usdAvg "+autPurVlu+
           " from GT_SRCH_RSLT where stt = 'SOLD' "+
           condQ +
           " and to_number(to_char(trunc(nvl(pkt_dte,sysdate)), 'rrrrmm')) = to_number(to_char(trunc(nvl(sl_dte, sysdate)), 'rrrrmm')) "+
           " group by to_char(trunc(nvl(sl_dte,sysdate)), 'MON rrrr'), to_number(to_char(trunc(nvl(sl_dte,sysdate)), 'rrrrmm')) "+
           " UNION " +              
           " select 'SOLD' DSP_STT, count(qty) NW_GDS,to_char(trunc(sum(trunc(cts, 2)),2),'9999999999999990.00') cts,trunc(sum(trunc(cts,2)*quot), 0) vlu,\n" + 
           "trunc(sum(trunc(cts,2)*nvl(fnl_usd, quot)), 0) fnl_usdvlu, " + 
           "  to_number(to_char(trunc(nvl(sl_dte,sysdate)), 'rrrrmm')) srt_mon " + 
           " , to_char(trunc(nvl(sl_dte,sysdate)), 'MON rrrr') DSP_MON ,trunc(sum(fnl_usd*trunc(cts,2))/sum(trunc(cts, 2)),2) usdAvg " +autPurVlu+
           " from GT_SRCH_RSLT where stt = 'SOLD'  "+ condQ +
           " and to_number(to_char(trunc(nvl(pkt_dte,sysdate)), 'rrrrmm')) < to_number(to_char(trunc(nvl(sl_dte,sysdate)), 'rrrrmm')) "+
           " group by to_char(trunc(nvl(sl_dte,sysdate)), 'MON rrrr'), to_number(to_char(trunc(nvl(sl_dte,sysdate)), 'rrrrmm')) " + 
           " order by 6, 1";
              outLst = db.execSqlLst("summary", summary,new ArrayList());
              pst = (PreparedStatement)outLst.get(0);
             ResultSet rs = (ResultSet)outLst.get(1);
         HashMap iosummDtl = new HashMap();
         ArrayList colList=new ArrayList();
         while(rs.next()){
            String DSP_STT=util.nvl(rs.getString("DSP_STT"));
            String DSP_MON=util.nvl(rs.getString("DSP_MON"));
            String NW_GDS=util.nvl(rs.getString("NW_GDS"));
            String cts=util.nvl(rs.getString("cts")).trim();
            String vlu=util.nvl(rs.getString("vlu"));
            String fnl_usdvlu=util.nvl(rs.getString("fnl_usdvlu"));
            String fnl_usdAvg=util.nvl(rs.getString("usdAvg"));
             if(!colList.contains(DSP_MON)){
             colList.add(DSP_MON);
             }
            iosummDtl.put(DSP_STT+"_"+DSP_MON+"_QTY",NW_GDS);
            iosummDtl.put(DSP_STT+"_"+DSP_MON+"_CTS",cts);
            iosummDtl.put(DSP_STT+"_"+DSP_MON+"_VLU",vlu);
            iosummDtl.put(DSP_STT+"_"+DSP_MON+"_NETVLU",fnl_usdvlu);
            iosummDtl.put(DSP_STT+"_"+DSP_MON+"_NETAVG",fnl_usdAvg);
            if(!autPurVlu.equals("")){
                    iosummDtl.put(DSP_STT+"_"+DSP_MON+"_PURAVG", rs.getString("aurPurAvg"));
                    iosummDtl.put(DSP_STT+"_"+DSP_MON+"_PURVLU", rs.getString("aurPurVlu"));
            }
            }
           rs.close();
               pst.close();
         req.setAttribute("colList", colList);
         req.setAttribute("iosummDtl", iosummDtl);
        req.setAttribute("OPENSTR_LST",openingValLst);   
        
         if(iosummDtl.size() >0){
           req.setAttribute("iosummary","Y");
         }
         
           req.setAttribute("autPurRte", autPurRte);
           if(!autPurVlu.equals(""))
           req.setAttribute("NET_PUR", "Y");
           session.setAttribute("summaryList",new ArrayList());
           util.updAccessLog(req,res,"Analysis Report", "inOutSummarymonth end");
           return am.findForward("summary");
           }
       }
    public ActionForward inOutSummaryweek(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Analysis Report", "inOutSummaryweek start");
            GenericInterface genericInt = new GenericImpl();
        String row="",col="";
              ArrayList gridcommLst=new ArrayList();
              ArrayList gridrowLst=new ArrayList();
              ArrayList gridcolLst=new ArrayList();
              HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
              gridcommLst=(ArrayList)gridstructure.get("COMM");
              gridrowLst=(ArrayList)gridstructure.get("ROW");
              gridcolLst=(ArrayList)gridstructure.get("COL");
              int gridcommLstsz=gridcommLst.size();
              int gridrowLstsz=gridrowLst.size();
              int gridcolLstsz=gridcolLst.size();
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "ANLS_VW","ANLS_VW");
        String key = req.getParameter("key");
        ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
              String statusALL= statusLst.toString();
              statusALL = statusALL.replace("[", "");
              statusALL = statusALL.replace("]", "");
              statusALL = statusALL.replaceAll(",", "','");
            String[] param = key.split("@");
                 if(key.length() > 0){
                 String Check = req.getParameter("row");
                   if(Check!=null && !Check.equals("")){
                   if(param.length > 0) {
                       row = Check;
                       col = req.getParameter("col");
                   }    
                 }else{
                     row = param[gridcommLstsz];
                     col = param[gridcommLstsz+gridrowLstsz];
                 }
                 }  
                   String condQ="";
                   if(!key.equals("")){
                           for (int i = 0; i < gridcommLstsz; i++) {
                           int index = vwPrpLst.indexOf(gridcommLst.get(i))+1;    
                           String fldprp=util.prpsrtcolumn("prp",index);
                           condQ += " and ( "+fldprp+"= '+"+param[i].trim()+"' or "+fldprp+"= '"+param[i]+"') ";
                           }    
                           if(!row.equals("ALL")){
                           int index = vwPrpLst.indexOf(gridrowLst.get(0))+1;    
                           String fldprp=util.prpsrtcolumn("prp",index);
                           condQ +=" and "+fldprp+" in ('"+row+"') ";
                           }
                           if(!col.equals("ALL")){
                           int index = vwPrpLst.indexOf(gridcolLst.get(0))+1;    
                           String fldprp=util.prpsrtcolumn("prp",index);
                           condQ +=" and "+fldprp+" in ('"+col+"') ";
                           }
                           } 
        String reqWeeks = util.nvl((String)req.getParameter("weeks"),"0");
        int weeks = 0;
        if(reqWeeks.length() > 0)
            weeks = Integer.parseInt(reqWeeks);
     int openingValWeek =0;
     String openingSqlWeek = "select count(*) from gt_srch_rslt where 1 = 1 "+ condQ +  
                         " and to_number(to_char(trunc(pkt_dte), 'rrrrWW')) < to_number(to_char(to_date(to_char(week_start(to_char(sysdate, 'rrrr')," +
                         "to_number(to_char(sysdate, 'WW'))  - "+weeks+"), 'dd-mm-rrrr'), 'dd-mm-rrrr'), 'rrrrWW')) ";
          ArrayList outLst = db.execSqlLst("openingSqlWeek", openingSqlWeek, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs1 = (ResultSet)outLst.get(1);
       if(rs1.next())
           openingValWeek = rs1.getInt(1);
            rs1.close();
            pst.close();
     String summaryWeek = "select 'NEW' DSP_STT, count(*) NW_GDS " + 
     " , PKT_SRT_WEEK SRT_WEEK, PKT_DSP_WEEK DSP_WEEK " + 
     " from GT_SRCH_RSLT_ANA_V " + 
     " Where stt = stt " + 
      condQ +
     " and PKT_SRT_WEEK between " + 
     "     to_number(to_char(to_date(to_char(week_start(to_char(sysdate, 'rrrr'), to_number(to_char(sysdate, 'WW'))  - "+weeks+"), 'dd-mm-rrrr'), 'dd-mm-rrrr'), 'rrrrWW')) " + 
     "     and to_number(to_char(sysdate, 'rrrrWW')) " + 
     " group by PKT_SRT_WEEK, PKT_DSP_WEEK " + 
     " UNION " + 
     " select 'NEW_SOLD' DSP_STT, count(*) NW_GDS " + 
     " , SOLD_SRT_WEEK SRT_WEEK, SOLD_DSP_WEEK DSP_WEEK " + 
     " from GT_SRCH_RSLT_ANA_V " + 
     " where stt = 'SOLD' " + 
        condQ + 
     " and PKT_SRT_WEEK = SOLD_SRT_WEEK " + 
     " and SOLD_SRT_WEEK between " + 
     "     to_number(to_char(to_date(to_char(week_start(to_char(sysdate, 'rrrr'), to_number(to_char(sysdate, 'WW'))  - "+weeks+"), 'dd-mm-rrrr'), 'dd-mm-rrrr'), 'rrrrWW')) " + 
     "       and to_number(to_char(sysdate, 'rrrrWW')) " + 
     " group by SOLD_SRT_WEEK, SOLD_DSP_WEEK " + 
     " UNION " + 
     " select 'SOLD' DSP_STT, count(*) NW_GDS " + 
     " , SOLD_SRT_WEEK SRT_WEEK, SOLD_DSP_WEEK DSP_WEEK " + 
     " from GT_SRCH_RSLT_ANA_V " + 
     " where stt = 'SOLD' " + 
        condQ +
     " and PKT_SRT_WEEK <> SOLD_SRT_WEEK " + 
     " and SOLD_SRT_WEEK between " + 
     "     to_number(to_char(to_date( to_char(week_start(to_char(sysdate, 'rrrr'), to_number(to_char(sysdate, 'WW'))  - "+weeks+"), 'dd-mm-rrrr') , 'dd-mm-rrrr'), 'rrrrWW')) " + 
     "       and to_number(to_char(sysdate, 'rrrrWW')) " + 
     " group by SOLD_SRT_WEEK, SOLD_DSP_WEEK " + 
     "order by 3, 1";
           outLst = db.execSqlLst("summaryWeek", summaryWeek,new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
             HashMap iosummWeekDtl = new HashMap();
             ArrayList colWeekList=new ArrayList();
             while(rs.next()){
              String wk=util.nvl(rs.getString("DSP_WEEK"));
              String DSP_STT=util.nvl(rs.getString("DSP_STT"));
              String NW_GDS=util.nvl(rs.getString("NW_GDS"));
               if(!colWeekList.contains(wk)){
               colWeekList.add(wk);
               }
              iosummWeekDtl.put(DSP_STT+"_"+wk,NW_GDS);
              }
        rs.close();
            pst.close();
             session.setAttribute("OPENSTRWEEK",openingValWeek);
             session.setAttribute("colWeekList", colWeekList);
             session.setAttribute("iosummWeekDtl", iosummWeekDtl);
          
     //Mayur
     
      if(iosummWeekDtl.size() >0){
        req.setAttribute("iosummary","Y");
      }
      
        
            util.updAccessLog(req,res,"Analysis Report", "inOutSummaryweek end");
        return am.findForward("summaryioWeek");
        }
    }
    public ActionForward summary(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Analysis Report", "summary start");
            GenericInterface genericInt = new GenericImpl();
      String typ = req.getParameter("typ");
      String row="",col="";
      ArrayList gridcommLst=new ArrayList();
      ArrayList gridrowLst=new ArrayList();
      ArrayList gridcolLst=new ArrayList();
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
      HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
      String comparewith=util.nvl(info.getCompareWith(),"cmp");
      HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
      gridcommLst=(ArrayList)gridstructure.get("COMM");
      gridrowLst=(ArrayList)gridstructure.get("ROW");
      gridcolLst=(ArrayList)gridstructure.get("COL");
      int gridcommLstsz=gridcommLst.size();
      int gridrowLstsz=gridrowLst.size();
      int gridcolLstsz=gridcolLst.size();
      ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "ANLS_VW","ANLS_VW");
      String key = req.getParameter("key");
        ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
        String psearch = util.nvl((String)session.getAttribute("psearch"));
        String statusALL= statusLst.toString();
        statusALL = statusALL.replace("[", "");
        statusALL = statusALL.replace("]", "");
        statusALL = statusALL.replaceAll(",", "','");
        String[] param = key.split("@");
        if(!comparewith.equals("cmp")){
        int indexpl = vwPrpLst.indexOf(comparewith)+1;
        comparewith = util.prpsrtcolumn("prp",indexpl);
            if(vwPrpLst.contains("LAB_CHARGES") && vwPrpLst.contains("FA_PRI")){
            int indexlabchr = vwPrpLst.indexOf("LAB_CHARGES")+1;
            String labchr = util.prpsrtcolumn("prp",indexlabchr);
            comparewith=" nvl("+comparewith+",0)+nvl("+labchr+",0)";
            }
        }
      if(key.length() > 0){
      String Check = req.getParameter("row");
        if(Check!=null && !Check.equals("")){
        if(param.length > 0) {
            row = Check;
            col = req.getParameter("col");
        }    
      }else{
          row = param[gridcommLstsz];
          col = param[gridcommLstsz+gridrowLstsz];
      }
      }  
        String condQ=" where stt = 'SOLD' ";
        if(psearch.equals("Y"))
        condQ=" where 1=1 ";    
        if(!key.equals("")){
                for (int i = 0; i < gridcommLstsz; i++) {
                int index = vwPrpLst.indexOf(gridcommLst.get(i))+1;    
                String fldprp=util.prpsrtcolumn("prp",index);
                condQ += " and ( "+fldprp+"= '+"+param[i].trim()+"' or "+fldprp+"= '"+param[i]+"') ";
                }    
                if(!row.equals("ALL")){
                int index = vwPrpLst.indexOf(gridrowLst.get(0))+1;    
                String fldprp=util.prpsrtcolumn("prp",index);
                condQ +=" and "+fldprp+" in ('"+row+"') ";
                }
                if(!col.equals("ALL")){
                int index = vwPrpLst.indexOf(gridcolLst.get(0))+1;    
                String fldprp=util.prpsrtcolumn("prp",index);
                condQ +=" and "+fldprp+" in ('"+col+"') ";
                }
                } 
  
        String summSql = "select emp, byr, sum(qty) qty, to_char(trunc(sum(trunc(cts, 2)),2),'9999999999999990.00') cts " + 
        ", trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),2) avg , round(trunc(sum(quot*trunc(cts,2)),2)) vlu,round(trunc(sum((quot-nvl("+comparewith+",cmp))*cts), 2)) pl  " + 
        ", trunc(sum(rap_rte*trunc(cts,2))/sum(trunc(cts, 2)),2) rap_avg " + 
        ", to_char(trunc(((sum(quot*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2),'9999999999999990.00') avg_dis,trunc(sum(trunc(cts,2)*quot), 0) vlu " + 
        " from gt_srch_rslt " + condQ +
            //" where stt = 'SOLD' " +
            //"and "+shPrp+"= ? and ( "+szPrp+"= '+"+sz+"' or "+szPrp+"= '"+sz+"')"+
        //shpCond + szCond + Color+" "+Clarity+
        " group by emp, byr " + 
        " order by 6 desc, 5 desc, 4 desc ";
        if(typ.equals("EMP")){
            summSql = "select emp, sum(qty) qty, to_char(trunc(sum(trunc(cts, 2)),2),'9999999999999990.00') cts " + 
                    ", trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),2) avg , round(trunc(sum(quot*trunc(cts,2)),2)) vlu,round(trunc(sum((quot-nvl("+comparewith+",cmp))*cts), 2)) pl  " + 
                    ", trunc(sum(rap_rte*trunc(cts,2))/sum(trunc(cts, 2)),2) rap_avg " + 
                    ", to_char(trunc(((sum(quot*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2),'9999999999999990.00') avg_dis,trunc(sum(trunc(cts,2)*quot), 0) vlu " + 
                    " from gt_srch_rslt " + condQ +
                        //" where stt = 'SOLD' " +
                        //"and "+shPrp+"= ? and ( "+szPrp+"= '+"+sz+"' or "+szPrp+"= '"+sz+"')"+
                    //shpCond + szCond + Color+" "+Clarity+
                    " group by emp " + 
                    " order by 5 desc, 4 desc, 3 desc ";
        }
        if(typ.equals("MONTH")){
        summSql = " select to_number(to_char(nvl(sl_dte,sysdate), 'rrrrmm')) srt_mm "+
                  " , to_char(nvl(sl_dte,sysdate), 'MON rrrr') dsp_mm "+
                  " , sum(qty) qty, to_char(trunc(sum(trunc(cts, 2)),2),'9999999999999990.00') cts "+
                  " , trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),2) avg "+
                  " , trunc(sum(rap_rte*trunc(cts,2))/sum(trunc(cts, 2)),2) rap_avg "+
                  " , to_char(trunc(((sum(quot*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2),'9999999999999990.00') avg_dis,trunc(sum(trunc(cts,2)*quot), 0) vlu "+
                  " from gt_srch_rslt " + condQ + 
            //"where stt = 'SOLD' and "+shPrp+"= ? and ( "+szPrp+"= '+"+sz+"' or "+szPrp+"= '"+sz+"')"+Color+" "+Clarity+
                  " group by to_number(to_char(nvl(sl_dte,sysdate), 'rrrrmm')), to_char(nvl(sl_dte,sysdate), 'MON rrrr') "+
                  " order by 1, 5 desc, 4 desc ";

        }
        if(typ.equals("WEEK")){
            summSql = "select  to_char(to_date(nvl(sl_dte,sysdate)), 'WW' ) wk, " + 
            "sum(qty) qty, to_char(trunc(sum(trunc(cts, 2)),2),'9999999999999990.00') cts  , trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),2) avg  , " + 
            "trunc(sum(rap_rte*trunc(cts,2))/sum(trunc(cts, 2)),2) rap_avg  , " + 
            "to_char(trunc(((sum(quot*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2),'9999999999999990.00') avg_dis,trunc(sum(trunc(cts,2)*quot), 0) vlu  " + 
            " from gt_srch_rslt " + condQ + 
            " group by to_char(to_date(nvl(sl_dte,sysdate)), 'WW' ) " + 
            "order by 1";
            
        }
          ArrayList outLst = db.execSqlLst("summary", summSql, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        ArrayList summList = new ArrayList();
        while(rs.next()){
            HashMap summDtl = new HashMap();
            if(typ.equals("BYR") || typ.equals("EMP")){
                summDtl.put("emp", util.nvl(rs.getString("emp")));
                if(!typ.equals("EMP"))
                summDtl.put("byr", util.nvl(rs.getString("byr")));
                summDtl.put("vlu",util.nvl( rs.getString("vlu")));
                summDtl.put("pl",util.nvl( rs.getString("pl")));
            }else if(typ.equals("WEEK")){
                summDtl.put("wk", util.nvl(rs.getString("wk")));
            }else{
                summDtl.put("srtMM",util.nvl( rs.getString("srt_mm")));
                summDtl.put("dspMM", util.nvl(rs.getString("dsp_mm")));
                  
            }
            summDtl.put("qty",util.nvl(rs.getString("qty")));
            summDtl.put("cts",util.nvl( rs.getString("cts")));
            summDtl.put("avg", util.nvl(rs.getString("avg")));
            summDtl.put("rapavg", util.nvl(rs.getString("rap_avg")));
             summDtl.put("vlu", util.nvl(rs.getString("vlu")));
            summDtl.put("avgDis",util.nvl( rs.getString("avg_dis")));
            summList.add(summDtl);
         }
        rs.close();
            pst.close();
        session.setAttribute("summaryList",summList);
            util.updAccessLog(req,res,"Analysis Report", "summary end");
        return am.findForward("summary");
        }
    }
    public ActionForward Weeksummary(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Analysis Report", "Weeksummary start");
    GenericReportForm udf = (GenericReportForm)af;
            GenericInterface genericInt = new GenericImpl();
    String row="",col="";
    ArrayList gridcommLst=new ArrayList();
    ArrayList gridrowLst=new ArrayList();
    ArrayList gridcolLst=new ArrayList();
    HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
    gridcommLst=(ArrayList)gridstructure.get("COMM");
    gridrowLst=(ArrayList)gridstructure.get("ROW");
    gridcolLst=(ArrayList)gridstructure.get("COL");
    int gridcommLstsz=gridcommLst.size();
    int gridrowLstsz=gridrowLst.size();
    int gridcolLstsz=gridcolLst.size();
    ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "ANLS_VW","ANLS_VW");
    String reqWeeks = util.nvl((String)req.getParameter("weeks"),"0");
    int weeks = 0;
    if(reqWeeks.length() > 0)
    weeks = Integer.parseInt(reqWeeks);
    String key = req.getParameter("key");
    ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
    String statusALL= statusLst.toString();
    statusALL = statusALL.replace("[", "");
    statusALL = statusALL.replace("]", "");
    statusALL = statusALL.replaceAll(",", "','");
    String[] param = key.split("@");
        if(key.length() > 0){
              String Check = req.getParameter("row");
                if(Check!=null && !Check.equals("")){
                if(param.length > 0) {
                    row = Check;
                    col = req.getParameter("col");
                }    
              }else{
                  row = param[gridcommLstsz];
                  col = param[gridcommLstsz+gridrowLstsz];
              }
              }  
                String condQ=" where 1=1 ";
                if(!key.equals("")){
                        for (int i = 0; i < gridcommLstsz; i++) {
                        int index = vwPrpLst.indexOf(gridcommLst.get(i))+1;    
                        String fldprp=util.prpsrtcolumn("prp",index);
                        condQ += " and ( "+fldprp+"= '+"+param[i].trim()+"' or "+fldprp+"= '"+param[i]+"') ";
                        }    
                        if(!row.equals("ALL")){
                        int index = vwPrpLst.indexOf(gridrowLst.get(0))+1;    
                        String fldprp=util.prpsrtcolumn("prp",index);
                        condQ +=" and "+fldprp+" in ('"+row+"') ";
                        }
                        if(!col.equals("ALL")){
                        int index = vwPrpLst.indexOf(gridcolLst.get(0))+1;    
                        String fldprp=util.prpsrtcolumn("prp",index);
                        condQ +=" and "+fldprp+" in ('"+col+"') ";
                        }
                        } 

//    String WeeksummSql = "select stt, to_number(to_char(nvl(sl_dte, pkt_dte), 'WW')) wk, " + 
//    "to_char(trunc(nvl(sl_dte, pkt_dte), 'WW')+1,'dd-mm-yy') FROM_DATE, " + 
//    "to_char(next_day(trunc(nvl(sl_dte, pkt_dte),'WW'),'SUNDAY'),'dd-mm-yy') TO_DATE , " + 
//    "sum(qty) qty, trunc(sum(trunc(cts, 2)),2) cts , round(sum(quot*trunc(cts,2))/sum(trunc(cts, 2))) avg , " + 
//    "trunc(sum(rap_rte*trunc(cts,2))/sum(trunc(cts, 2)),2) rap_avg , " + 
//    "trunc(((sum(quot*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2) avg_dis " + 
//    "from gt_srch_rslt  " + condQ + 
//    " and trunc(nvl(sl_dte, pkt_dte))\n" + 
//    "between week_start(to_number(to_char(sysdate-"+weeks+"*7, 'yyyy')),to_number(to_char(sysdate-"+weeks+"*7, 'ww'))) " + 
//    " and sysdate " + 
//    "group by stt, to_number(to_char(nvl(sl_dte, pkt_dte), 'WW')) , " + 
//    "trunc(nvl(sl_dte, pkt_dte), 'WW')+1 , next_day(trunc(nvl(sl_dte, pkt_dte),'WW'),'SUNDAY')  order by 2,1";
    String WeeksummSql="select stt\n" + 
    ", decode(stt, 'SOLD', SOLD_SRT_WEEK, PKT_SRT_WEEK) SRT_WEEK\n" + 
    ", decode(stt, 'SOLD', SOLD_DSP_WEEK, PKT_DSP_WEEK) dsp_week\n" + 
    ", Substr(Decode(Stt, 'SOLD', Sold_Dsp_Week, Pkt_Dsp_Week), 0, Instr(Decode(Stt, 'SOLD', Sold_Dsp_Week, Pkt_Dsp_Week), ':')-1) wk\n" + 
    ", sum(qty) qty, sum(cts) cts\n" + 
    ", round(sum(quot*trunc(cts,2))/sum(trunc(cts, 2))) avg\n" + 
    ", trunc(sum(rap_rte*trunc(cts,2))/sum(trunc(cts, 2)),2) rap_avg\n" + 
    ", trunc(((sum(quot*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2) avg_dis,trunc(sum(trunc(cts,2)*quot), 0) vlu\n" + 
    "from gt_srch_rslt_ana_v "+ condQ+
     " and trunc(pkt_dte) between week_start(to_char(sysdate, 'rrrr'), to_number(to_char(sysdate, 'IW')) - 6) and sysdate\n" + 
     "group by stt, decode(stt, 'SOLD', SOLD_SRT_WEEK, PKT_SRT_WEEK), decode(stt, 'SOLD', SOLD_DSP_WEEK, PKT_DSP_WEEK)\n" + 
     "Order By 2,1"   ;
          ArrayList outLst = db.execSqlLst("Week summary", WeeksummSql, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
    HashMap summList = new HashMap();
    HashMap summListdate = new HashMap();
    ArrayList weekList = new ArrayList();
    while(rs.next()){
    HashMap summDtl = new HashMap();
    String stt=util.nvl(rs.getString("stt"));
    String wk=util.nvl(rs.getString("wk"));
    summDtl.put("wk", util.nvl(rs.getString("wk")));
    summDtl.put("qty",util.nvl(rs.getString("qty")));
    summDtl.put("cts",util.nvl( rs.getString("cts")));
    summDtl.put("avg", util.nvl(rs.getString("avg")));
    summDtl.put("rapavg", util.nvl(rs.getString("rap_avg")));
    summDtl.put("avgDis",util.nvl( rs.getString("avg_dis")));
    summDtl.put("vlu",util.nvl( rs.getString("vlu")));
    if(!weekList.contains(wk) && !wk.equals((""))){
    weekList.add(wk);
    summListdate.put(wk,rs.getString("dsp_week"));
    }
    summList.put(stt+"_"+wk,summDtl);
    }
        rs.close();
            pst.close();
    session.setAttribute("summaryList",summList);
    session.setAttribute("summListdate",summListdate);
    session.setAttribute("weekList",weekList);
            util.updAccessLog(req,res,"Analysis Report", "Weeksummary end");
    return am.findForward("Weeksummary");
        }
    }
    public void SearchRslt( HttpServletRequest req, HttpServletResponse res)
    throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    HashMap dbinfo = info.getDmbsInfoLst();
    HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
    HashMap allpktListMap = (HashMap)session.getAttribute("genericPacketLstMap");
    ArrayList genericPacketLst=util.getKeyInArrayList(allpktListMap);
    ArrayList pktLst=util.convertArrayList(allpktListMap,genericPacketLst);
    ArrayList gridcommLst=new ArrayList();
    ArrayList gridrowLst=new ArrayList();
    ArrayList gridcolLst=new ArrayList();
    ArrayList colList = new ArrayList();
    ArrayList rowList = new ArrayList();
    ArrayList keyList = new ArrayList();
    HashMap getGrandTotalList=new HashMap();
    gridcommLst=(ArrayList)gridstructure.get("COMM");
    gridrowLst=(ArrayList)gridstructure.get("ROW");
    gridcolLst=(ArrayList)gridstructure.get("COL");
    int gridcommLstsz=gridcommLst.size();
    int gridrowLstsz=gridrowLst.size();
    int gridcolLstsz=gridcolLst.size();
    ArrayList<String> gridParamsLst=new ArrayList();
    gridParamsLst.addAll(gridcommLst);
    gridParamsLst.addAll(gridrowLst);
    gridParamsLst.addAll(gridcolLst);
    gridParamsLst.add("stt");
    
    String [] gridParams = gridParamsLst.toArray(new String[gridParamsLst.size()]);
    HashMap<String, String> dataDtl = util.getKV(req,res,pktLst, gridParams,"Y","SHOWPRP","@","","ANLS_VW","GENERIC_REPORT","");
    List<String> gridkeyList = new ArrayList<String>(dataDtl.keySet());
    for(String gridKeys : gridkeyList) {
    String [] splitGridKey=gridKeys.split("@");
    String key="";
    for(int i=0;i<gridcommLstsz;i++) {
        key = key + splitGridKey[i] + "@";  
    }
    key = key.substring(0, key.length() - 1);
    if(!keyList.contains(key))
    keyList.add(key);
    String rowPrp=splitGridKey[gridcommLstsz];
    String colPrp=splitGridKey[gridcommLstsz+gridrowLstsz];
    if(!rowList.contains(rowPrp))
    rowList.add(rowPrp);
    if(!colList.contains(colPrp))
    colList.add(colPrp);
    }
    
    gridParamsLst=new ArrayList();
    gridParamsLst.addAll(gridcommLst);
    gridParamsLst.addAll(gridrowLst);
    gridParamsLst.add("stt");
    gridParams = gridParamsLst.toArray(new String[gridParamsLst.size()]);
    HashMap<String, String> rowDtl = util.getKV(req,res,pktLst, gridParams,"N","SHOWPRP","@","","ANLS_VW","GENERIC_REPORT","");
    
    gridParamsLst=new ArrayList();
    gridParamsLst.addAll(gridcommLst);
    gridParamsLst.addAll(gridcolLst);
    gridParamsLst.add("stt");
    gridParams = gridParamsLst.toArray(new String[gridParamsLst.size()]);
    HashMap<String, String> colDtl = util.getKV(req,res,pktLst, gridParams,"N","SHOWPRP","@","","ANLS_VW","GENERIC_REPORT","");
    
    gridParamsLst=new ArrayList();
    gridParamsLst.addAll(gridcommLst);
    gridParamsLst.add("stt");
    gridParams = gridParamsLst.toArray(new String[gridParamsLst.size()]);
    getGrandTotalList = util.getKV(req,res,pktLst, gridParams,"N","SHOWPRP","@","","ANLS_VW","GENERIC_REPORT","");
    
    dataDtl.putAll(rowDtl);
    dataDtl.putAll(colDtl);
    Collections.sort(keyList);
    session.setAttribute("rowList", rowList);
    session.setAttribute("colList", colList);
    session.setAttribute("commkeyList", keyList);
    session.setAttribute("dataDtl", dataDtl);
    session.setAttribute("getGrandTotalList", getGrandTotalList);
    }
    
    @SuppressWarnings("unchecked")
    public HashMap<String, String> getKV(HttpServletRequest req, HttpServletResponse res,ArrayList<HashMap> pkts, String[] gridParams,String setshowpprp,String itm_typ,String keyDelim) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        HashMap<String, String> kv = new HashMap<String, String>();
        int pktssz=pkts.size();
        HashMap dbinfo = info.getDmbsInfoLst();
        String ageval = (String)dbinfo.get("AGE");
        String hitval = (String)dbinfo.get("HIT");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
        GenericInterface genericInt = new GenericImpl();
        ArrayList ANLSVWList=genericInt.genericPrprVw(req, res, "ANLS_VW","ANLS_VW");
        ArrayList prpDspBlocked = info.getPageblockList();
        ArrayList showprpLst=new ArrayList();
        DBUtil util = new DBUtil();
        int indexAge = ANLSVWList.indexOf(ageval)+1;
        int indexHit = ANLSVWList.indexOf(hitval)+1;
        
        for (int i=0;i<pktssz;i++) {
            HashMap<String, String> dtls;
            dtls = pkts.get(i);
            String key = "";
            for(String params : gridParams) {
                key = key + dtls.get(params) + keyDelim;                
            }
            if(!key.equals("") && !keyDelim.equals("")){
            key = key.substring(0, key.length() - 1);
            }
            int keyQty = 0 ;
            double keyCts = 0 ;
            double keyRte = 0 ;
            double keyRapRte = 0 ;
            double keyFnlUsd = 0 ;
            keyQty = Integer.parseInt(util.nvl((String)kv.get(key+keyDelim+"QTY"),"0"));
            keyCts = Double.parseDouble(util.nvl((String)kv.get(key+keyDelim+"CTS"),"0"));
            keyRte = Double.parseDouble(util.nvl((String)kv.get(key+keyDelim+"AVG"),"0"));
            keyRapRte = Double.parseDouble(util.nvl((String)kv.get(key+keyDelim+"RAPVLU"),"0"));
            keyFnlUsd = Double.parseDouble(util.nvl((String)kv.get(key+keyDelim+"FNLUSDVLU"),"0"));

            BigDecimal qty = new BigDecimal(util.nvl(dtls.get("QTY"),"0"));
            BigDecimal cts = new BigDecimal(util.nvl(dtls.get("CRTWT"),"0"));
            BigDecimal rte = new BigDecimal(util.nvl(dtls.get("quot"),"0"));
            BigDecimal rap_rte = new BigDecimal(util.nvl(dtls.get("RAP_RTE"),"0"));
            BigDecimal fnl_usd = new BigDecimal(util.nvl(dtls.get("fnl_usd"),"0"));

            double keyVlu = (keyCts*keyRte)+(cts.doubleValue()*rte.doubleValue());
            double keyrapVlu = (keyRapRte)+(cts.doubleValue()*rap_rte.doubleValue());
            double keyfnlusdVlu = (keyFnlUsd)+(cts.doubleValue()*fnl_usd.doubleValue());
            keyCts += cts.doubleValue();
            keyQty += qty.intValue();
            BigDecimal lAvg = new BigDecimal(1);
            BigDecimal lRapAvg = new BigDecimal(1);
            try {
                lAvg = new BigDecimal(keyVlu/keyCts);
                lRapAvg = new BigDecimal(keyrapVlu/keyCts);
            } catch (Exception e) {   
            }
            
            pageList=(ArrayList)pageDtl.get(itm_typ);
            if(pageList!=null && pageList.size() >0 && (setshowpprp.equals("Y") || setshowpprp.equals("N"))){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            if(lov_qry.equals("PRP")){
            int indexprp = ANLSVWList.indexOf(fld_ttl)+1;   
            if(!prpDspBlocked.contains(fld_ttl)){
            if(indexprp!=0){
            if(dflt_val.equals("AVG")){
                BigDecimal lShowPrpval = new BigDecimal(1);
                double keyshowprp = 0 ;
                keyshowprp = Double.parseDouble(util.nvl((String)kv.get(key+keyDelim+fld_ttl),"0"));
                String tempval=util.nvl(dtls.get(fld_ttl),"0");
                if(tempval.equals("0"))
                tempval=util.nvl(dtls.get("quot"),"0");
                BigDecimal prpval = new BigDecimal(tempval);
                double keyshowVlu = (keyshowprp)+(cts.doubleValue()*prpval.doubleValue());
                try {
                    lShowPrpval = new BigDecimal(keyshowVlu/keyCts);
                } catch (Exception e) {   
                }
                if(!showprpLst.contains(fld_ttl))
                showprpLst.add(fld_ttl);
                kv.put(key+keyDelim+fld_ttl, lShowPrpval.setScale(0, RoundingMode.HALF_EVEN).toString());
            }else{
                double keyshowprp = 0 ;
                keyshowprp = Double.parseDouble(util.nvl((String)kv.get(key+keyDelim+fld_ttl+"VALUE"),"0"));
                String tempval=util.nvl(dtls.get(fld_typ),"0");
                if(tempval.equals("0"))
                tempval=util.nvl(dtls.get("quot"),"0");
                BigDecimal prpval = new BigDecimal(tempval);
                double keyshowVlu = (keyshowprp)+(cts.doubleValue()*prpval.doubleValue());
                if(!prpDspBlocked.contains(fld_typ)){
                indexprp = ANLSVWList.indexOf(fld_typ)+1; 
                if(indexprp!=0){
                    if(!showprpLst.contains(fld_ttl))
                    showprpLst.add(fld_ttl);
                    kv.put(key+keyDelim+fld_ttl, String.valueOf(util.roundToDecimals(((keyshowVlu/keyrapVlu)*100)-100,2)));
                    kv.put(key+keyDelim+fld_ttl+"VALUE", String.valueOf(util.roundToDecimals(keyshowVlu,0)));
                }
                }
            }
            }else if(fld_typ.equals("FNL_USD")){
                if(dflt_val.equals("AVG")){
                    BigDecimal lShowPrpval = new BigDecimal(1);
                    double keyshowprp = 0 ;
                    keyshowprp = Double.parseDouble(util.nvl((String)kv.get(key+keyDelim+fld_ttl),"0"));
                    String tempval=util.nvl(dtls.get(fld_typ.toLowerCase()),"0");
                    BigDecimal prpval = new BigDecimal(tempval);
                    double keyshowVlu = (keyshowprp)+(cts.doubleValue()*prpval.doubleValue());
                    try {
                        lShowPrpval = new BigDecimal(keyshowVlu/keyCts);
                    } catch (Exception e) {   
                    }
                    if(!showprpLst.contains(fld_ttl))
                    showprpLst.add(fld_ttl);
                    kv.put(key+keyDelim+fld_ttl, lShowPrpval.setScale(0, RoundingMode.HALF_EVEN).toString());
                }else{
                    BigDecimal lShowPrpval = new BigDecimal(1);
                    double keyshowprp = 0 ;
                    keyshowprp = Double.parseDouble(util.nvl((String)kv.get(key+keyDelim+fld_ttl+"VALUE"),"0"));
                    String tempval=util.nvl(dtls.get(fld_typ.toLowerCase()),"0");
                    BigDecimal prpval = new BigDecimal(tempval);
                    double keyshowVlu = (keyshowprp)+(cts.doubleValue()*prpval.doubleValue());
                    lShowPrpval = new BigDecimal(String.valueOf(util.roundToDecimals(((keyshowVlu/keyrapVlu)*100)-100,2)));
                    if(!showprpLst.contains(fld_ttl))
                    showprpLst.add(fld_ttl);
                    kv.put(key+keyDelim+fld_ttl, String.valueOf(util.roundToDecimals(((keyshowVlu/keyrapVlu)*100)-100,2)));
                    kv.put(key+keyDelim+fld_ttl+"VALUE", String.valueOf(util.roundToDecimals(keyshowVlu,0)));
                }
            }
            }
            }else if(lov_qry.equals("VLU")){
                double keyshowprp = 0 ;
                keyshowprp = Double.parseDouble(util.nvl((String)kv.get(key+keyDelim+fld_ttl),"0"));
                String tempval=util.nvl(dtls.get(fld_typ),"0");
                if(tempval.equals("0"))
                tempval=util.nvl(dtls.get("quot"),"0");
                BigDecimal prpval = new BigDecimal(tempval);
                double keyshowVlu = 0;
                if(!prpDspBlocked.contains(fld_typ)){
                int indexprp = ANLSVWList.indexOf(fld_typ)+1; 
                if(indexprp!=0){
                keyshowVlu = (keyshowprp)+(cts.doubleValue()*prpval.doubleValue());
                }else{
                    prpval = new BigDecimal(util.nvl(dtls.get(fld_typ.toLowerCase()),"0"));
                    keyshowVlu = (keyshowprp)+(cts.doubleValue()*prpval.doubleValue());
                }
                }
                if(!showprpLst.contains(fld_ttl))
                showprpLst.add(fld_ttl);
                kv.put(key+keyDelim+fld_ttl, String.valueOf(util.roundToDecimals(keyshowVlu,0)));
            }
            }
            }
            
            if(indexAge!=0){
            }
            if(indexHit!=0){
            int keyHit = 0 ;
            keyHit = Integer.parseInt(util.nvl((String)kv.get(key+keyDelim+"HITTOTAL"),"0"));
            BigDecimal hit = new BigDecimal(util.nvl(dtls.get("HIT"),"0"));
            keyHit += hit.intValue();
            kv.put(key+keyDelim+"HIT",String.valueOf(keyHit/keyQty));
            kv.put(key+keyDelim+"HITTOTAL",String.valueOf(keyHit));
            }
            kv.put(key+keyDelim+"QTY", String.valueOf(keyQty));
            kv.put(key+keyDelim+"CTS", String.valueOf(util.Round(keyCts,2)));
            kv.put(key+keyDelim+"AVG", lAvg.setScale(0, RoundingMode.HALF_EVEN).toString());
            kv.put(key+keyDelim+"VLU", String.valueOf(util.roundToDecimals(keyVlu,0)));
            kv.put(key+keyDelim+"RAPVLU",String.valueOf(util.roundToDecimals(keyrapVlu,0)));
            kv.put(key+keyDelim+"FNLUSDVLU",String.valueOf(util.roundToDecimals(keyfnlusdVlu,0)));
            kv.put(key+keyDelim+"SD",String.valueOf(util.roundToDecimals(((keyfnlusdVlu/keyrapVlu)*100)-100,2)));
            kv.put(key+keyDelim+"RAP",String.valueOf(util.roundToDecimals(((keyVlu/keyrapVlu)*100)-100,2)));
        }
        if(setshowpprp.equals("Y"))
        session.setAttribute("showprpLst", showprpLst);
        return kv;        
    }
    public ActionForward loadstkopenclose(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        util.updAccessLog(req,res,"Analysis Report", "loadstkoenclose start");
        GenericReportForm udf = (GenericReportForm)af;
            ArrayList ary=new ArrayList();
            String frmdtestkoenclose = util.nvl((String)udf.getValue("frmdtestkoenclose"));
            String todtestkoenclose = util.nvl((String)udf.getValue("todtestkoenclose"));   
            String frmDteSold = util.nvl((String)udf.getValue("frmDtestkoenclose"));
            String toDteSold = util.nvl((String)udf.getValue("toDtestkoenclose"));
            GenericInterface genericInt = new GenericImpl();
            HashMap pktListMap = new HashMap();
            ArrayList pktStkIdnList=new ArrayList();
            ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "ANLS_VW_OPEN_CLOSE","ANLS_VW_OPEN_CLOSE");
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
            ArrayList filterlprpLst=new ArrayList();
            String perioddtlmsg="Sold Date : "+frmDteSold+" To "+toDteSold+"  , Date : "+frmdtestkoenclose+" To "+todtestkoenclose+" ";
            
            ary=new ArrayList();
            ary.add(frmdtestkoenclose);
            ary.add(todtestkoenclose);
            int ct = db.execCall("DP_ANALYSIS_OPEN_CLOSE", "DP_ANALYSIS_OPEN_CLOSE(pDteFrm => to_date(?, 'dd-mm-rrrr') , pDteTo => to_date(?, 'dd-mm-rrrr'))", ary);
            
            String cpPrp = "prte";
            if(vwPrpLst.contains("CP"))
            cpPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("CP")+1);
                       
            String netpurPrp = "prte";
            if(vwPrpLst.contains("NET_PUR_RTE"))
            netpurPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("NET_PUR_RTE")+1);
                       
            String mfgpriPrp = "prte";
            if(vwPrpLst.contains("MFG_PRI"))
            mfgpriPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("MFG_PRI")+1);
            String cmpPrp = "prte";
            if(vwPrpLst.contains("CMP_RTE"))
            cmpPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("CMP_RTE")+1); 
            
            String  srchQ =  " select to_number(to_char(sl_dte, 'rrrrmmdd')) sldteorderby,to_number(to_char(pkt_dte, 'rrrrmmdd')) pktdteorderby,stk_idn , pkt_ty,  vnm,to_char(pkt_dte,'dd-mm-yyyy') pkt_dte, stt,dsp_stt,img2, qty , to_char(cts,'9999999999990.99') cts , srch_id , cmp , rmk, kts_vw kts , byr , emp ,to_char(sl_dte,'dd-mm-yyyy') sl_dte,trunc(((greatest(nvl("+netpurPrp+",prte),1)*100)/greatest(rap_rte,1)) - 100,2) netpurdis,nvl(nvl("+netpurPrp+",prte),0)*nvl(cts,0) netpurtotal,trunc(((greatest(nvl("+cpPrp+",prte),1)*100)/greatest(rap_rte,1)) - 100,2) cpdis,nvl(nvl("+cpPrp+",prte),0)*nvl(cts,0) cptotal,trunc(((greatest(nvl("+mfgpriPrp+",prte),1)*100)/greatest(rap_rte,1)) - 100,2) mfgpridis,nvl(nvl("+mfgpriPrp+",prte),0)*nvl(cts,0) mfgpritotal,trunc(((greatest(nvl("+cmpPrp+",prte),1)*100)/greatest(rap_rte,1)) - 100,2) cmpdis,nvl(nvl("+cmpPrp+",prte),0)*nvl(cts,0) cmpvlutotal,"+
                             "  decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis ,rap_rte, quot , cmnt , trunc(cts*quot,2) amt,trunc(cts*cmp,2) rteamt,to_char(decode(rap_rte, 1, null, trunc((quot/rap_rte*100) - 100,2)),'999999990.00') slback,byr_cntry,"+
                             " nvl(fnl_usd, quot) FixRate,trunc(cts*nvl(fnl_usd, quot),2) FixRateAmt,  decode(rap_rte, 1, '', trunc(((nvl(fnl_usd, quot)/greatest(rap_rte,1))*100)-100,2)) FixRateDisc";
                       for (int i = 0; i < vwPrpLst.size(); i++) {
                            String prp=util.nvl((String)vwPrpLst.get(i));
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
                       String rsltQ = srchQ + " from gt_srch_rslt where 1=1 and dsp_stt is not null ";
               
                       rsltQ+= " order by sk1, cts ";
          ArrayList outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
                           while(rs.next()) {
                               HashMap pktPrpMap = new HashMap();
                               String stk_idn = util.nvl(rs.getString("stk_idn"));
                               pktPrpMap.put("Status", util.nvl(rs.getString("dsp_stt")));
                               pktPrpMap.put("dsp_stt", util.nvl(rs.getString("dsp_stt")));
                               pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                               pktPrpMap.put("Packet Code",util.nvl(rs.getString("vnm")));
                               pktPrpMap.put("Byr",util.nvl(rs.getString("byr"))); 
                               pktPrpMap.put("Sale Ex",util.nvl(rs.getString("emp"))); 
                               pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                               pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                               pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                               pktPrpMap.put("rap_rte",util.nvl(rs.getString("rap_rte")));
                               pktPrpMap.put("amt",util.nvl(rs.getString("amt")));
                               pktPrpMap.put("rteamt",util.nvl(rs.getString("rteamt")));
                               pktPrpMap.put("Packet Date",util.nvl(rs.getString("pkt_dte")));
                               pktPrpMap.put("Sale Date",util.nvl(rs.getString("sl_dte")));
                               pktPrpMap.put("issIdn",util.nvl(rs.getString("srch_id")));
                               pktPrpMap.put("cmp",util.nvl(rs.getString("cmp")));
                               pktPrpMap.put("quot",util.nvl(rs.getString("quot")));
                               pktPrpMap.put("slback",util.nvl(rs.getString("slback")));
                               pktPrpMap.put("Rate",util.nvl(rs.getString("FixRate")));
                               pktPrpMap.put("RateAmt",util.nvl(rs.getString("FixRateAmt")));
                               pktPrpMap.put("RateDisc",util.nvl(rs.getString("FixRateDisc")));
                               pktPrpMap.put("Remark",util.nvl(rs.getString("rmk")));
                               pktPrpMap.put("Byr Country",util.nvl(rs.getString("byr_cntry")));
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
                                                val = util.nvl(rs.getString("r_dis"));
                                            if (prp.toUpperCase().equals("RAP_RTE"))
                                                val = util.nvl(rs.getString("rap_rte"));
                                            if(prp.equals("RTE"))
                                                val = util.nvl(rs.getString("cmp"));
                                            if(prp.equals("KTSVIEW"))
                                                val = util.nvl(rs.getString("kts"));
                                            if(prp.equals("CP_DIS"))
                                                val = util.nvl(rs.getString("cpdis"));
                                            if(prp.equals("CP_VLU"))
                                                val = util.nvl(rs.getString("cptotal"));
                                            if(prp.equals("NET_PUR_DIS"))
                                                val = util.nvl(rs.getString("netpurdis"));
                                            if(prp.equals("NET_PUR_VLU"))
                                                val = util.nvl(rs.getString("netpurtotal"));
                                            if(prp.equals("MFG_DIS"))
                                                val = util.nvl(rs.getString("mfgpridis"));
                                            if(prp.equals("MFG_VLU"))
                                                val = util.nvl(rs.getString("mfgpritotal"));
                                            if(prp.equals("CMP_DIS"))
                                                val = util.nvl(rs.getString("cmpdis"));
                                            if(prp.equals("CMP_VLU"))
                                                val = util.nvl(rs.getString("cmpvlutotal"));
                                            if(prp.equals("COMMENT"))
                                                val = util.nvl(rs.getString("cmnt"));
                                            if(prp.equalsIgnoreCase("MEM_COMMENT"))
                                                val = util.nvl(rs.getString("img2"));
                                       pktPrpMap.put(prp, val);
                                  } 
                                   pktListMap.put(stk_idn,pktPrpMap);
                                   pktStkIdnList.add(stk_idn);
                           }
                           rs.close();
            pst.close();
            session.setAttribute("pktStkIdnListopenclose", pktStkIdnList);
            session.setAttribute("filterStkIdnListopenclose", util.useDifferentArrayList(pktStkIdnList));
            session.setAttribute("pktListMapopenclose", pktListMap);
            session.setAttribute("opencloseCriteria", "");
            pageList=(ArrayList)pageDtl.get("LEVEL1LPRP");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_nme=(String)pageDtlMap.get("fld_nme");
            filterlprpLst.add(fld_nme);
            }}
            
            session.setAttribute("perioddtlmsg",perioddtlmsg);
            session.setAttribute("openclosefilterlprpLst", filterlprpLst);
            util.updAccessLog(req,res,"Analysis Report", "loadstkoenclose end");
            return loadstkopencloseData(am, af, req, res);
        }
    }  
    
    
    public ActionForward loadstkopencloseData(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        util.updAccessLog(req,res,"Analysis Report", "loadstkoencloseData start");
        GenericInterface genericInt = new GenericImpl();
        GenericReportForm udf = (GenericReportForm)af;
            HashMap dbinfo = info.getDmbsInfoLst();
                String gridbylprp = util.nvl((String)req.getParameter("gridbylprp"),info.getLevel1());
                String filterlprp = util.nvl((String)req.getParameter("filterlprp"),info.getLevel1());
                String filterlprpval = util.nvl((String)req.getParameter("filterlprpval"),"ALL");
                String opencloseCriteria = util.nvl((String)session.getAttribute("opencloseCriteria"),"");
                ArrayList dspgrpList=new ArrayList();
                HashMap pktListMapopenclose=(HashMap)session.getAttribute("pktListMapopenclose");
                ArrayList filterStkIdnListopenclose=(ArrayList)session.getAttribute("filterStkIdnListopenclose");
                ArrayList openclosefilterlprpLst=(ArrayList)session.getAttribute("openclosefilterlprpLst");
                int filterStkIdnListopenclosesz=filterStkIdnListopenclose.size();
                HashMap pktPrpMap = new HashMap();
                HashMap dataDtl = new HashMap();
                HashMap mprp = info.getSrchMprp();
                ArrayList gtList=new ArrayList();
                ArrayList lstfltquickpktStkIdnList=new ArrayList();
                double qty = 0 ;
                double vlu = 0 ;
                double cts = 0 ;
            
            opencloseCriteria+=" "+util.nvl((String)mprp.get(filterlprp+"D"))+" : "+filterlprpval;
            openclosefilterlprpLst.remove((openclosefilterlprpLst.indexOf(gridbylprp)));
            
            for(int i=0;i<filterStkIdnListopenclosesz;i++){
                String stkIdn=(String)filterStkIdnListopenclose.get(i);
                pktPrpMap = new HashMap();
                pktPrpMap=(HashMap)pktListMapopenclose.get(stkIdn) ;
                String lprpval=util.nvl((String)pktPrpMap.get(filterlprp));
                if(filterlprpval.equals("ALL") || filterlprpval.equals(lprpval)){
                String gridbylprpval=util.nvl((String)pktPrpMap.get(gridbylprp));
                lstfltquickpktStkIdnList.add(stkIdn);
                String dsp_stt=util.nvl((String)pktPrpMap.get("dsp_stt"));
                String stt=util.nvl((String)pktPrpMap.get("stt"));
                    String curlprpqty = util.nvl((String)pktPrpMap.get("qty"),"0").trim();
                    String curcts = util.nvl((String)pktPrpMap.get("cts"),"0").trim();
                    String currte = util.nvl((String)pktPrpMap.get("quot"),"0").trim();
                    String currap_rte = util.nvl((String)pktPrpMap.get("rap_rte"),"0").trim();
                    for(int l=0;l<2;l++){
                    String sttgrp=stt;    
                    if(l==1)
                    sttgrp=dsp_stt;
                    if(!gtList.contains(gridbylprpval))
                    gtList.add(gridbylprpval);
                    BigDecimal currBigLprpQty = new BigDecimal(curlprpqty);
                    BigDecimal lprpBigqty = (BigDecimal)dataDtl.get(gridbylprpval+"_"+sttgrp+"_QTY");
                    if(lprpBigqty==null)
                    lprpBigqty=new BigDecimal(qty);
                    if(!curlprpqty.equals("0"))
                    lprpBigqty = lprpBigqty.add(currBigLprpQty);
                    dataDtl.put(gridbylprpval+"_"+sttgrp+"_QTY", lprpBigqty);
                    
                    BigDecimal currBigLprpCts = new BigDecimal(curcts);
                    BigDecimal lprpBigcts = (BigDecimal)dataDtl.get(gridbylprpval+"_"+sttgrp+"_CTS");
                    if(lprpBigcts==null)
                    lprpBigcts=new BigDecimal(cts);
                    if(!curcts.equals("0"))
                    lprpBigcts = lprpBigcts.add(currBigLprpCts);
                    dataDtl.put(gridbylprpval+"_"+sttgrp+"_CTS", lprpBigcts);
                
                    BigDecimal currBigCts = new BigDecimal(curcts);
                    BigDecimal currBigLprpVlu = new BigDecimal(currte);
                    BigDecimal lprpBigvlu = (BigDecimal)dataDtl.get(gridbylprpval+"_"+sttgrp+"_VLU");
                    if(lprpBigvlu==null)
                    lprpBigvlu=new BigDecimal(vlu);
                    if(!currte.equals("0")){
                    currBigLprpVlu=currBigLprpVlu.multiply(currBigCts);
                    lprpBigvlu = lprpBigvlu.add(currBigLprpVlu);
                    }
                    lprpBigvlu = lprpBigvlu.setScale(0, RoundingMode.HALF_EVEN);
                    dataDtl.put(gridbylprpval+"_"+sttgrp+"_VLU", lprpBigvlu);
                
                    BigDecimal currBigLprpRapVlu = new BigDecimal(currap_rte);
                    BigDecimal lprpBigrapvlu = (BigDecimal)dataDtl.get(gridbylprpval+"_"+sttgrp+"_RAPVLU");
                    if(lprpBigrapvlu==null)
                    lprpBigrapvlu=new BigDecimal(vlu);
                    if(!currte.equals("0")){
                    currBigLprpRapVlu=currBigLprpRapVlu.multiply(currBigCts);
                    lprpBigrapvlu = lprpBigrapvlu.add(currBigLprpRapVlu);
                    }
                    lprpBigrapvlu = lprpBigrapvlu.setScale(0, RoundingMode.HALF_EVEN);
                    dataDtl.put(gridbylprpval+"_"+sttgrp+"_RAPVLU", lprpBigrapvlu);
                    
                        BigDecimal grandlprpBigqty = (BigDecimal)dataDtl.get(sttgrp+"_QTY");
                        if(grandlprpBigqty==null)
                        grandlprpBigqty=new BigDecimal(qty);
                        if(!curcts.equals("0"))
                        grandlprpBigqty = grandlprpBigqty.add(currBigLprpQty);
                        dataDtl.put(sttgrp+"_QTY", grandlprpBigqty);
                        
                        BigDecimal grandlprpBigcts = (BigDecimal)dataDtl.get(sttgrp+"_CTS");
                        if(grandlprpBigcts==null)
                        grandlprpBigcts=new BigDecimal(cts);
                        if(!curcts.equals("0"))
                        grandlprpBigcts = grandlprpBigcts.add(currBigLprpCts);
                        dataDtl.put(sttgrp+"_CTS", grandlprpBigcts);
                        
                        BigDecimal grandlprpBigvlu = (BigDecimal)dataDtl.get(sttgrp+"_VLU");
                        if(grandlprpBigvlu==null)
                        grandlprpBigvlu=new BigDecimal(vlu);
                        if(!currte.equals("0"))
                        grandlprpBigvlu = grandlprpBigvlu.add(currBigLprpVlu);
                        grandlprpBigvlu = grandlprpBigvlu.setScale(0, RoundingMode.HALF_EVEN);
                        dataDtl.put(sttgrp+"_VLU", grandlprpBigvlu);
                        

                        BigDecimal grandlprpBigrapvlu = (BigDecimal)dataDtl.get(sttgrp+"_RAPVLU");
                        if(grandlprpBigrapvlu==null)
                        grandlprpBigrapvlu=new BigDecimal(vlu);
                        if(!currte.equals("0"))
                        grandlprpBigrapvlu = grandlprpBigrapvlu.add(currBigLprpRapVlu);
                        grandlprpBigrapvlu = grandlprpBigrapvlu.setScale(0, RoundingMode.HALF_EVEN);
                        dataDtl.put(sttgrp+"_RAPVLU", grandlprpBigrapvlu);
                    }
            }
            }
            
            dspgrpList.add("OPEN");
            dspgrpList.add("NEW");
            dspgrpList.add("SOLD");
            dspgrpList.add("CLOSE");
            req.setAttribute("filterlprp", gridbylprp);
            udf.setValue("criteria",opencloseCriteria);  
            req.setAttribute("gridbylprp", gridbylprp);
            session.setAttribute("openclosefilterlprpLst", openclosefilterlprpLst);
            session.setAttribute("opencloseCriteria", opencloseCriteria);
            session.setAttribute("filterStkIdnListopenclose", lstfltquickpktStkIdnList);
            session.setAttribute("stkoenclosedataDtl", dataDtl);
            session.setAttribute("stkoenclosegtList", gtList);
            session.setAttribute("dspgrpList", dspgrpList);
            util.updAccessLog(req,res,"Analysis Report", "loadstkoencloseData end");
    return am.findForward("loadstkoenclose");
        }
    }  
    public ActionForward loadstkopenclosereset(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        util.updAccessLog(req,res,"Analysis Report", "loadstkoenclose start");
        GenericReportForm udf = (GenericReportForm)af;
            ArrayList ary=new ArrayList();
            String frmdtestkoenclose = util.nvl((String)udf.getValue("frmdtestkoenclose"));
            String todtestkoenclose = util.nvl((String)udf.getValue("todtestkoenclose"));   
            GenericInterface genericInt = new GenericImpl();
            HashMap pktListMap = new HashMap();
            ArrayList pktStkIdnList=new ArrayList();
            ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "ANLS_VW_OPEN_CLOSE","ANLS_VW_OPEN_CLOSE");
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
            ArrayList filterlprpLst=new ArrayList();
            ArrayList pktStkIdnListopenclose=(ArrayList)session.getAttribute("pktStkIdnListopenclose");
            
            session.setAttribute("opencloseCriteria", "");
            session.setAttribute("filterStkIdnListopenclose", util.useDifferentArrayList(pktStkIdnListopenclose));
            session.setAttribute("opencloseCriteria", "");
            pageList=(ArrayList)pageDtl.get("LEVEL1LPRP");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_nme=(String)pageDtlMap.get("fld_nme");
            filterlprpLst.add(fld_nme);
            }}
            session.setAttribute("openclosefilterlprpLst", filterlprpLst);
            util.updAccessLog(req,res,"Analysis Report", "loadstkoenclose end");
            return loadstkopencloseData(am, af, req, res);
        }
    } 
    
    public ActionForward giaSummary(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Analysis Report", "giaSummary start");
            GenericInterface genericInt = new GenericImpl();
        GenericReportForm udf = (GenericReportForm)af;
        String ignoreememo = util.nvl((String)udf.getValue("ignoreememo"));
        String mfgprimemo = util.nvl((String)udf.getValue("mfgprimemo"));
        HashMap dbinfo = info.getDmbsInfoLst();
        String sh = (String)dbinfo.get("SHAPE");
        String szval = (String)dbinfo.get("SIZE");
        String colval = (String)dbinfo.get("COL");
        String clrval = (String)dbinfo.get("CLR");
        String ageval = (String)dbinfo.get("AGE");
        String hitval = (String)dbinfo.get("HIT");
        ArrayList ANLSVWList = genericInt.genericPrprVw(req, res, "GRP_SMRY","GRP_SMRY");
        ArrayList srchids=(ArrayList)session.getAttribute("srchids");
        int indexSH = ANLSVWList.indexOf(sh)+1;
        int indexSZ = ANLSVWList.indexOf(szval)+1;
        int indexCol = ANLSVWList.indexOf(colval)+1;
        int indexClr = ANLSVWList.indexOf(clrval)+1;
        int indexAge = ANLSVWList.indexOf(ageval)+1;
        int indexHit = ANLSVWList.indexOf(hitval)+1;
        String shPrp = util.prpsrtcolumn("prp",indexSH);
        String szPrp = util.prpsrtcolumn("prp",indexSZ);
        String shSrt = util.prpsrtcolumn("srt",indexSH);
        String szSrt = util.prpsrtcolumn("srt",indexSZ);
        String colPrp = util.prpsrtcolumn("prp",indexCol);
        String colsrt = util.prpsrtcolumn("srt",indexCol);
        String clrPrp = util.prpsrtcolumn("prp",indexClr);
        String agePrp = util.prpsrtcolumn("prp",indexAge);
        String ageSrt = util.prpsrtcolumn("srt",indexAge);
        String hitPrp = util.prpsrtcolumn("prp",indexHit);
        String hitSrt = util.prpsrtcolumn("srt",indexHit);
        HashMap dataDtl = new HashMap();
        ArrayList colLst=new ArrayList();
        ArrayList clrLst=new ArrayList();
        ArrayList szLst=new ArrayList();
        ArrayList shList=new ArrayList();
        ArrayList ary=new ArrayList();
        String key="";
        String conQ="";
        int ct=0;
        if(!ignoreememo.equals("")){
            ignoreememo = util.getVnm(ignoreememo);
            ignoreememo = ignoreememo.replaceAll("'", "");
            String[] ignoreememoLst = ignoreememo.split(",");
            String insrtAddon = " insert into srch_addon( srch_id , cprp , cval,cstt) "+
            "select ? , ? , ? ,? from dual ";
            for(int s=0;s<srchids.size();s++){
            for(int i=0;i<ignoreememoLst.length;i++){
            ary = new ArrayList();
            ary.add(srchids.get(s));
            ary.add("MEMO");
            ary.add(ignoreememoLst[i]);
            ary.add("IGNORE");
            ct = db.execUpd("", insrtAddon, ary); 
            }
            }
        }
        
            if(!mfgprimemo.equals("")){
                mfgprimemo = util.getVnm(mfgprimemo);
                mfgprimemo = mfgprimemo.replaceAll("'", "");
                String[] mfgprimemoLst = mfgprimemo.split(",");
                String insrtAddon = " insert into srch_addon( srch_id , cprp , cval,cstt) "+
                "select ? , ? , ?,?  from dual ";
                for(int s=0;s<srchids.size();s++){
                for(int i=0;i<mfgprimemoLst.length;i++){
                ary = new ArrayList();
                ary.add(srchids.get(s));
                ary.add("MEMO");
                ary.add(mfgprimemoLst[i]);
                ary.add("MFG_PRI");
                ct = db.execUpd("", insrtAddon, ary); 
                }
                }
            }
        if(indexAge!=0){
        conQ="to_char(sum("+agePrp+")/30,'99999990.0') age,";
        }
        if(indexHit!=0){
        conQ=conQ+"round(sum("+hitPrp+")/sum(qty)) hit,";
        }
        
        for(int i=0;i<srchids.size();i++){
        ary=new ArrayList();
        ary.add(srchids.get(i));
        ct = db.execCall("GIA_SMRY_NOMEMO", "report_pkg.GIA_SMRY_NOMEMO(?,'GRP_SMRY')", ary);
//        int ct = db.execCall("Upd_Gt_Col_Clr_Grp", "Srch_Pkg.Upd_Gt_Col_Clr_Grp('GRP_SMRY')", params);
        }
        String dataQ = "Select " +conQ+ 
        "to_char(sum(cts),'99999990.00') Cts,Trunc(Sum(Quot*Trunc(Cts,2))/Sum(Trunc(Cts, 2)),0) Avg,\n" + 
        ""+szPrp+","+clrPrp+","+colPrp+",stt\n" + 
        "From Gt_Srch_Rslt where cert_lab='GIA' and "+shPrp+"='ROUND' and "+colsrt+" <= 201\n" + 
        "Group By "+szPrp+","+clrPrp+","+colPrp+",stt\n" + 
        "Order by "+szPrp+","+clrPrp+","+colPrp ;
          ArrayList outLst = db.execSqlLst("giadataQ", dataQ, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        ArrayList shSzList = new ArrayList();
        while(rs.next()){
            String szVal = util.nvl(rs.getString(szPrp));
            String colVal = util.nvl(rs.getString(colPrp));
            String clrVal = util.nvl(rs.getString(clrPrp));
            String status = util.nvl(rs.getString("stt"));
            key=szVal+"@"+status+"@"+clrVal+"@"+colVal;
            dataDtl.put(key+"@CTS",util.nvl(rs.getString("cts")));
            dataDtl.put(key+"@AVG",util.nvl(rs.getString("avg")));
            dataDtl.put(key+"@AGE",util.nvl(rs.getString("age")));
            if(!colLst.contains(colVal))
                colLst.add(colVal);
            if(!clrLst.contains(clrVal))
                clrLst.add(clrVal);
            if(!szLst.contains(szVal))
                szLst.add(szVal);
        }
        rs.close();
            pst.close();
        String getTotal = "Select " +conQ+  
        "to_char(sum(cts),'99999990.00') Cts,Trunc(Sum(Quot*Trunc(Cts,2))/Sum(Trunc(Cts, 2)),0) Avg,\n" + 
        ""+szPrp+",stt\n" + 
        "From Gt_Srch_Rslt where cert_lab='GIA' and "+shPrp+"='ROUND' and "+colsrt+" <= 201\n" + 
        "Group By "+szPrp+",Stt\n" + 
        "Order by "+szPrp ;

           outLst = db.execSqlLst("getTotal", getTotal, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        String szVal = rs.getString(szPrp);
        String status = rs.getString("stt");
        key = szVal+"@"+status;
        dataDtl.put(key+"@CTS", util.nvl(rs.getString("cts")));
        dataDtl.put(key+"@AVG", util.nvl(rs.getString("avg")));
        dataDtl.put(key+"@AGE", util.nvl(rs.getString("age")));
        }
        rs.close();
        pst.close();
        String shSql="Select " +conQ+  
        "to_char(sum(cts),'99999990.00') Cts,Trunc(Sum(Quot*Trunc(Cts,2))/Sum(Trunc(Cts, 2)),0) Avg,\n" + 
        ""+shPrp+",Stt,"+shSrt+"\n" + 
        "From Gt_Srch_Rslt where "+shPrp+" <> 'ROUND'\n" +
        "GROUP BY "+shPrp+", Stt, "+shSrt+" \n" + 
        "Order By "+shSrt;
          outLst = db.execSqlLst("shSql", shSql, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        String shval = util.nvl(rs.getString(shPrp));
        String status = rs.getString("stt");
        key = shval+"@"+status;
        dataDtl.put(key+"@CTS", util.nvl(rs.getString("cts")));
        dataDtl.put(key+"@AVG", util.nvl(rs.getString("avg")));
        dataDtl.put(key+"@AGE", util.nvl(rs.getString("age")));
        if(!shList.contains(shval))
        shList.add(shval);
        }
        rs.close();
            pst.close();
            String labSql="Select " +conQ+  
            "to_char(sum(cts),'99999990.00') Cts,Trunc(Sum(Quot*Trunc(Cts,2))/Sum(Trunc(Cts, 2)),0) Avg,cert_lab,stt\n" + 
            "From Gt_Srch_Rslt where cert_lab <> 'GIA' and "+shPrp+"='ROUND'\n" + 
            "group by cert_lab, stt \n" +
            "union\n" + 
            "Select " +conQ+  
            "to_char(sum(cts),'99999990.00') Cts,Trunc(Sum(Quot*Trunc(Cts,2))/Sum(Trunc(Cts, 2)),0) Avg,'TOTAL',stt\n" + 
            "From Gt_Srch_Rslt\n" + 
            "group by stt \n" +
            "union\n" + 
            "Select " +conQ+  
            "to_char(sum(cts),'99999990.00') Cts,Trunc(Sum(Quot*Trunc(Cts,2))/Sum(Trunc(Cts, 2)),0) Avg,'ROUND',stt\n" + 
            "from gt_srch_rslt where cert_lab='GIA' and "+shPrp+"='ROUND' and "+colsrt+" <= 201\n" + 
            "group by stt \n" +
            "union\n"+
            "Select " +conQ+  
            "to_char(sum(cts),'99999990.00') Cts,Trunc(Sum(Quot*Trunc(Cts,2))/Sum(Trunc(Cts, 2)),0) Avg,'FANCY',stt\n" + 
            "From Gt_Srch_Rslt where cert_lab = 'GIA' and "+shPrp+"='ROUND' and "+colsrt+" > 201\n" +  
            "group by stt \n" +
            "Order By 4";
          outLst = db.execSqlLst("labSql", labSql, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while(rs.next()){
            String cert_lab = util.nvl(rs.getString("cert_lab"));
            String status = rs.getString("stt");
            key = cert_lab+"@"+status;
            dataDtl.put(key+"@CTS", util.nvl(rs.getString("cts")));
            dataDtl.put(key+"@AVG", util.nvl(rs.getString("avg")));
            dataDtl.put(key+"@AGE", util.nvl(rs.getString("age")));
            if(!shList.contains(cert_lab) && !cert_lab.equals("TOTAL"))
            shList.add(cert_lab);
            }
            rs.close();  
            pst.close();
        Collections.sort(colLst);
        Collections.sort(clrLst);
        session.setAttribute("dataDtl", dataDtl);
        session.setAttribute("colList", colLst);
        session.setAttribute("clrList", clrLst);
        session.setAttribute("shList", shList);
        session.setAttribute("szLst", szLst);
            util.updAccessLog(req,res,"Analysis Report", "giaSummary end");
    return am.findForward("giasummary");
        }
    }  
    
    public ActionForward reports(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Analysis Report", "giaSummary start");
            GenericReportForm udf = (GenericReportForm)af;
            ArrayList ary = new ArrayList();
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            String webUrl = (String)dbinfo.get("REP_URL");
            String reqUrl = (String)dbinfo.get("HOME_DIR");
            String rep_path = util.nvl((String)dbinfo.get("REP_PATH"));
            String prcstt=util.nvl((String)udf.getValue("prcstt"));
            String rpttype=util.nvl((String)udf.getValue("rpttype"));
            String repNme=util.nvl((String)udf.getValue("reportNme"));
            ArrayList mprcIdnAll=(ArrayList)session.getAttribute("mprcIdnAll");
            String SRCH_ID =util.nvl((String)req.getAttribute("SRCH_ID"));
            int srch_idn=util.getSeqVal("srch_id_seq");
            int mkt_prc=util.getSeqVal("seq_mkt_prc");
            String insrtAddon = " insert into srch_addon( srch_id , cprp , cval,cstt) "+
            "select ? , ? , ?,?  from dual ";
            int ct;
            for(int p=0;p<mprcIdnAll.size();p++){
            String mprcId= (String)mprcIdnAll.get(p);
            String chk=util.nvl((String)udf.getValue("PRC_"+mprcId));
            if(!chk.equals("")){
                ary = new ArrayList();
                ary.add(String.valueOf(srch_idn));
                ary.add("PRC");
                ary.add(mprcId);
                ary.add("PRC_IDN");
                ct = db.execUpd("", insrtAddon, ary); 
            }
            }
            if(!prcstt.equals("")){
            ary = new ArrayList();
            ary.add(String.valueOf(srch_idn));
            ary.add("PRCSTT");
            ary.add(prcstt);
            ary.add("ALL");
            ct = db.execUpd("", insrtAddon, ary); 
            }
            if(!SRCH_ID.equals("")){
            ary = new ArrayList();
            ary.add(String.valueOf(srch_idn));
            ary.add("SRCH_ID");
            ary.add(SRCH_ID);
            ary.add("ALL");
            ct = db.execUpd("", insrtAddon, ary); 
            }
            
            if(!rpttype.equals("")){
            ary = new ArrayList();
            ary.add(String.valueOf(srch_idn));
            ary.add("RPTTYP");
            ary.add(rpttype);
            ary.add("ALL");
            ct = db.execUpd("", insrtAddon, ary); 
            }
            
            for(int p=1;p<3;p++){
            String chklprp=util.nvl((String)udf.getValue("GRP_"+p));
            if(!chklprp.equals("")){
                ary = new ArrayList();
                ary.add(String.valueOf(srch_idn));
                ary.add("GRP"+p);
                ary.add(chklprp);
                ary.add(util.nvl((String)udf.getValue("GRPBY_"+p)));
                ct = db.execUpd("", insrtAddon, ary); 
            }
            }
            String insertPrc = " insert into mkt_prc(prc_idn ,mstk_idn ,rep_dte) select ?, stk_idn, sysdate from" +
                " gt_srch_rslt where flg = ? ";
            ary = new ArrayList();
            ary.add(String.valueOf(mkt_prc));
            ary.add("Z");
            ct = db.execUpd("insert mkt_prc", insertPrc, ary);
            
            String url = rep_path+"/reports/rwservlet?"+cnt+"&report="+reqUrl+"\\reports\\"+repNme+"&srch_idn="+srch_idn+"&mkt_prc="+mkt_prc ;
            System.out.println(url);
            res.sendRedirect(url);
            util.updAccessLog(req,res,"Analysis Report", "giaSummary end");
    return null;
        }
    }  
    
    public ActionForward fiftydownpt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Analysis Report", "fiftydownpt start");
            GenericInterface genericInt = new GenericImpl();
        GenericReportForm udf = (GenericReportForm)af;
        HashMap dbinfo = info.getDmbsInfoLst();
        String sh = (String)dbinfo.get("SHAPE");
        String szval = (String)dbinfo.get("SIZE");
        String colval = (String)dbinfo.get("COL");
        String cutval = (String)dbinfo.get("CUT");
        String polval = (String)dbinfo.get("POL");
        String symval = (String)dbinfo.get("SYM");
        ArrayList ANLSVWList = genericInt.genericPrprVw(req, res, "fiftydown","FIFTYDWNPT");;
        int indexSH = ANLSVWList.indexOf(sh)+1;
        int indexSZ = ANLSVWList.indexOf(szval)+1;
        int indexCol = ANLSVWList.indexOf(colval)+1;
        int indexCut = ANLSVWList.indexOf(cutval)+1;
        int indexPol = ANLSVWList.indexOf(polval)+1;
        int indexSym = ANLSVWList.indexOf(symval)+1;
        int indexsubsize = ANLSVWList.indexOf("SUBSIZE")+1;
        String shPrp = util.prpsrtcolumn("prp",indexSH);
        String szPrp = util.prpsrtcolumn("prp",indexSZ);
        String colPrp = util.prpsrtcolumn("prp",indexCol);
        String cutPrp = util.prpsrtcolumn("prp",indexCut);
        String polPrp = util.prpsrtcolumn("prp",indexPol);
        String symPrp = util.prpsrtcolumn("prp",indexSym);
        String shSrt = util.prpsrtcolumn("srt",indexSH);
        String szSrt = util.prpsrtcolumn("srt",indexSZ);
        String cutSrt = util.prpsrtcolumn("srt",indexCut);
        String polSrt = util.prpsrtcolumn("srt",indexPol);
        String symSrt = util.prpsrtcolumn("srt",indexSym);
        HashMap dataDtl = new HashMap();
        ArrayList monthLst=new ArrayList();
        ArrayList cutgrpLst=new ArrayList();
        ArrayList polgrpLst=new ArrayList();
        ArrayList symgrpLst=new ArrayList();
        ArrayList szLst=new ArrayList();
        String key="";
        String dataQ="Select Sum(Qty) Qty,b.grp grp,\n" + 
        "To_Number(To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'rrrrmm')) Srt_Mon,\n" + 
        "To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'MON rrrr') Dsp_Mon,'"+cutval+"' dsc,Stt\n" + 
        "From Gt_Srch_Rslt A,Prp B where a."+cutPrp+"=b.val and "+cutSrt+"=b.srt and b.mprp='"+cutval+"' \n" + 
        "Group By b.grp,To_Number(To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'rrrrmm')),\n" + 
        "To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'MON rrrr'),Stt\n" + 
        "Union\n" + 
        "Select Sum(Qty) Qty,b.grp grp,\n" + 
        "To_Number(To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'rrrrmm')) Srt_Mon,\n" + 
        "To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'MON rrrr') Dsp_Mon,'"+polval+"' Dsc,Stt\n" + 
        "From Gt_Srch_Rslt A,Prp B where a."+polPrp+"=b.val and "+polSrt+"=b.srt and b.mprp='"+polval+"' \n" + 
        "Group By b.grp,To_Number(To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'rrrrmm')),\n" + 
        "To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'MON rrrr'),Stt\n" + 
        "Union\n" + 
        "Select Sum(Qty) Qty,b.grp grp,\n" + 
        "To_Number(To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'rrrrmm')) Srt_Mon,\n" + 
        "To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'MON rrrr') Dsp_Mon,'"+symval+"' dsc,Stt\n" + 
        "From Gt_Srch_Rslt A,Prp B where a."+symPrp+"=b.val and "+symSrt+"=b.srt and b.mprp='"+symval+"' \n" + 
        "Group By b.grp,To_Number(To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'rrrrmm')),\n" + 
        "To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'MON rrrr'),Stt\n" + 
        "Order By 3,2,5";
          ArrayList outLst = db.execSqlLst("dataQ", dataQ, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String grp = util.nvl(rs.getString("grp"));
            String stt = util.nvl(rs.getString("Stt"));
            String qty = util.nvl(rs.getString("Qty"));
            String dsc = util.nvl(rs.getString("dsc"));
            String month = util.nvl(rs.getString("Dsp_Mon"));
            key=stt+"@"+dsc+"@"+month+"@"+grp;
            dataDtl.put(key,qty);
            if(!monthLst.contains(month))
                monthLst.add(month); 
            if(dsc.equals("CUT") && !cutgrpLst.contains(grp)){
               cutgrpLst.add(grp);                
            }
            if(dsc.equals("POL") && !polgrpLst.contains(grp)){
                polgrpLst.add(grp); 
            }
            if(dsc.equals("SYM") && !symgrpLst.contains(grp)){
                symgrpLst.add(grp);     
            }
        }
            rs.close();
            pst.close();
        String totalQ="Select Sum(Qty) Qty,\n" + 
        "To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'MON rrrr') Dsp_Mon,Stt\n" + 
        "From Gt_Srch_Rslt \n" + 
        "group by To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'MON rrrr'), Stt";
           outLst = db.execSqlLst("totalQ", totalQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String stt = util.nvl(rs.getString("Stt"));
            String qty = util.nvl(rs.getString("Qty"));
            String month = util.nvl(rs.getString("Dsp_Mon"));
        key=stt+"@"+month;  
        dataDtl.put(key,qty);
        }
            rs.close();
            pst.close();
        String szQ="select distinct "+szPrp+","+szSrt+" from Gt_Srch_Rslt order by "+szSrt;
          outLst = db.execSqlLst("szQ", szQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        String szVal = rs.getString(szPrp);
            szLst.add(szVal);    
        }
            rs.close();
            pst.close();
        dataDtl.put("CUT",cutgrpLst);
        dataDtl.put("POL",polgrpLst);
        dataDtl.put("SYM",symgrpLst);
        session.setAttribute("dataDtl", dataDtl);
        session.setAttribute("monthLst", monthLst);
        session.setAttribute("szLst", szLst);
            util.updAccessLog(req,res,"Analysis Report", "fiftydownpt end");
    return am.findForward("fiftydownpt");
        }
    }  
    public ActionForward sizepurity(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                util.updAccessLog(req,res,"Analysis Report", "sizepurity start");
            GenericReportForm udf = (GenericReportForm)af;
                GenericInterface genericInt = new GenericImpl();
            HashMap dbinfo = info.getDmbsInfoLst();
            String sh = (String)dbinfo.get("SHAPE");
            String szval = (String)dbinfo.get("SIZE");
            String colval = (String)dbinfo.get("COL");
            String clrval = (String)dbinfo.get("CLR");
            ArrayList ANLSVWList = genericInt.genericPrprVw(req, res, "sizepurity","MERGEDATA_VW");
            int indexSH = ANLSVWList.indexOf(sh)+1;
            int indexSZ = ANLSVWList.indexOf(szval)+1;
            int indexCol = ANLSVWList.indexOf(colval)+1;
            int indexClr = ANLSVWList.indexOf(clrval)+1;
            String shPrp = util.prpsrtcolumn("prp",indexSH);
            String szPrp = util.prpsrtcolumn("prp",indexSZ);
            String colPrp = util.prpsrtcolumn("prp",indexCol);
            String clrPrp = util.prpsrtcolumn("prp",indexClr);
            String shSrt = util.prpsrtcolumn("srt",indexSH);
            String szSrt = util.prpsrtcolumn("srt",indexSZ);
            String clrSrt = util.prpsrtcolumn("srt",indexClr);
            HashMap dataDtl = new HashMap();
            ArrayList monthLst=new ArrayList();
            ArrayList grpLst=new ArrayList();
            ArrayList szLst=new ArrayList();
            String key="";
            String dataQ="Select Sum(Qty) qty,trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),0) avg,b.grp grp,"+szPrp+",\n" + 
            "To_Number(To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'rrrrmm')) Srt_Mon,\n" + 
            "To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'MON rrrr') Dsp_Mon,Stt\n" + 
            "From Gt_Srch_Rslt A,Prp B \n" + 
            "Where A."+clrPrp+"=B.Val And a."+clrSrt+"=B.Srt And B.Mprp='"+clrval+"' \n" + 
            "Group By B.Grp,"+szPrp+",To_Number(To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'rrrrmm')),\n" + 
            "To_Char(Trunc(Nvl(Pkt_Dte,Sysdate)), 'MON rrrr'),Stt\n" + 
            "order by 5,4";
              ArrayList outLst = db.execSqlLst("dataQ", dataQ, new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                String szVal = util.nvl(rs.getString(szPrp));
                String grp = util.nvl(rs.getString("grp"));
                String stt = util.nvl(rs.getString("Stt"));
                String month = util.nvl(rs.getString("Dsp_Mon"));
                key=stt+"@"+szVal+"@"+month+"@"+grp;
                dataDtl.put(key+"@AVG",util.nvl(rs.getString("avg")));
                dataDtl.put(key+"@QTY",util.nvl(rs.getString("qty")));
                if(!monthLst.contains(month))
                    monthLst.add(month); 
                if(!szLst.contains(szVal))
                    szLst.add(szVal); 
            }
                rs.close();
                pst.close();
            String prpQ="Select Distinct B.Grp grp \n" + 
            "From Gt_Srch_Rslt A,Prp B\n" + 
            "Where A."+clrPrp+"=B.Val And a."+clrSrt+"=B.Srt And B.Mprp='"+clrval+"'\n" + 
            "order by B.Grp desc";
               outLst = db.execSqlLst("prpQ", prpQ, new ArrayList());
               pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                grpLst.add(util.nvl(rs.getString("grp")));
            }
                rs.close();
                pst.close();
            dataDtl.put("GRP",grpLst);
            session.setAttribute("dataDtl", dataDtl);
            session.setAttribute("monthLst", monthLst);
            session.setAttribute("szLst", szLst);
                util.updAccessLog(req,res,"Analysis Report", "sizepurity end");
        return am.findForward("sizepurity");
            }
        }  
    public ActionForward memoshape(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Analysis Report", "memoshape start");
        GenericReportForm udf = (GenericReportForm)af;
            GenericInterface genericInt = new GenericImpl();
        HashMap dbinfo = info.getDmbsInfoLst();
        String sh = (String)dbinfo.get("SHAPE");
        String szval = (String)dbinfo.get("SIZE");
        String colval = (String)dbinfo.get("COL");
        String memoval = (String)dbinfo.get("MEMO");
        String ctsval = "MFG_CTS";
        String faprival = "FA_PRI";
        ArrayList ANLSVWList = genericInt.genericPrprVw(req, res, "memoshape_vw","MEMOSHAPEDATA_VW");;
        int indexSH = ANLSVWList.indexOf(sh)+1;
        int indexSZ = ANLSVWList.indexOf(szval)+1;
        int indexCol = ANLSVWList.indexOf(colval)+1;
        int indexMemo = ANLSVWList.indexOf(memoval)+1;
        int indexCts = ANLSVWList.indexOf(ctsval)+1;
        int indexfapri = ANLSVWList.indexOf(faprival)+1;
        String shPrp = util.prpsrtcolumn("prp",indexSH);
        String szPrp = util.prpsrtcolumn("prp",indexSZ);
        String colPrp = util.prpsrtcolumn("prp",indexCol);
        String memoPrp = util.prpsrtcolumn("prp",indexMemo);
        String ctsPrp = util.prpsrtcolumn("prp",indexCts);
        String fapriPrp = util.prpsrtcolumn("prp",indexfapri);
        String shSrt = util.prpsrtcolumn("srt",indexSH);
        String szSrt = util.prpsrtcolumn("srt",indexSZ);
        String memoSrt = util.prpsrtcolumn("srt",indexMemo);
        String ctsSrt = util.prpsrtcolumn("srt",indexCts);
        String fapriSrt = util.prpsrtcolumn("srt",indexfapri);
        HashMap dataDtl = new HashMap();
        ArrayList memoLst=new ArrayList();
        ArrayList shapeLst=new ArrayList();
        ArrayList szLst=new ArrayList();
        String key="";
        String dataQ="Select "+memoPrp+","+shPrp+",Trunc(Sum(Nvl("+fapriPrp+", 0)*trunc("+ctsPrp+",2)),0) vlu\n" + 
        ",trunc(((sum(trunc("+ctsPrp+",2)*nvl("+fapriPrp+",0)) / sum(trunc("+ctsPrp+",2)* greatest(rap_rte,1) ))*100) - 100, 2) vludis\n"+
        "From Gt_Srch_Rslt Group By "+memoPrp+","+shPrp+"\n" + 
        "order by 1";
          ArrayList outLst = db.execSqlLst("dataQ", dataQ, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String memoVal = util.nvl(rs.getString(memoPrp));
            String shVal = util.nvl(rs.getString(shPrp));
            key=memoVal+"@"+shVal;
            dataDtl.put(key+"@VLU",util.nvl(rs.getString("vlu")));
            dataDtl.put(key+"@DIS",util.nvl(rs.getString("vludis")));
            if(!memoLst.contains(memoVal))
                memoLst.add(memoVal); 
        }
            rs.close();
            pst.close();
        String memochk="";
        String szQ="Select "+memoPrp+","+shPrp+","+shSrt+","+szPrp+",Trunc(Sum(Nvl("+fapriPrp+", 0)*trunc("+ctsPrp+",2)),0) vlu\n" + 
        ",trunc(((sum(trunc("+ctsPrp+",2)*nvl("+fapriPrp+",0)) / sum(trunc("+ctsPrp+",2)* greatest(rap_rte,1) ))*100) - 100, 2) vludis\n"+
        "From Gt_Srch_Rslt Group By "+memoPrp+","+shPrp+","+shSrt+","+szPrp+"\n" + 
        "order by "+memoPrp+","+szPrp+","+shSrt;
          outLst = db.execSqlLst("szQ", szQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        String memoVal = util.nvl(rs.getString(memoPrp));
        String shVal = util.nvl(rs.getString(shPrp));
        String szVal = util.nvl(rs.getString(szPrp));
        key=memoVal+"@"+shVal+"@"+szVal;
        dataDtl.put(key+"@VLU",util.nvl(rs.getString("vlu")));
        dataDtl.put(key+"@DIS",util.nvl(rs.getString("vludis")));
        if(memochk.equals("") || !memochk.equals(memoVal)){   
        dataDtl.put(memochk+"@SZ",szLst);
        szLst=new ArrayList();
        memochk=memoVal; 
        }
        if(!szLst.contains(szVal))
        szLst.add(szVal); 
        }
            rs.close();
            pst.close();
        dataDtl.put(memochk+"@SZ",szLst);
        String szttlQ="Select "+memoPrp+","+szPrp+",Trunc(Sum(Nvl("+fapriPrp+", 0)*trunc("+ctsPrp+",2)),0) vlu\n" + 
        ",trunc(((sum(trunc("+ctsPrp+",2)*nvl("+fapriPrp+",0)) / sum(trunc("+ctsPrp+",2)* greatest(rap_rte,1) ))*100) - 100, 2) vludis\n"+
        "From Gt_Srch_Rslt Group By "+memoPrp+","+szPrp+"\n" + 
        "order by "+memoPrp+","+szPrp;
          outLst = db.execSqlLst("szttlQ", szttlQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        String memoVal = util.nvl(rs.getString(memoPrp));
        String szVal = util.nvl(rs.getString(szPrp));
        key=memoVal+"@"+szVal;
        dataDtl.put(key+"@VLU",util.nvl(rs.getString("vlu")));
        dataDtl.put(key+"@DIS",util.nvl(rs.getString("vludis")));
        }
            rs.close();
            pst.close();
        String memottlQ="Select "+memoPrp+",Trunc(Sum(Nvl("+fapriPrp+", 0)*trunc("+ctsPrp+",2)),0) vlu\n" + 
        ",trunc(((sum(trunc("+ctsPrp+",2)*nvl("+fapriPrp+",0)) / sum(trunc("+ctsPrp+",2)* greatest(rap_rte,1) ))*100) - 100, 2) vludis\n"+
        "From Gt_Srch_Rslt Group By "+memoPrp+"\n" + 
        "order by 1";
          outLst = db.execSqlLst("memottlQ", memottlQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String memoVal = util.nvl(rs.getString(memoPrp));
            dataDtl.put(memoVal+"@VLU",util.nvl(rs.getString("vlu")));
            dataDtl.put(memoVal+"@DIS",util.nvl(rs.getString("vludis")));
        }
            rs.close();
            pst.close();
        String shapettlQ="Select "+shPrp+","+shSrt+",Trunc(Sum(Nvl("+fapriPrp+", 0)*trunc("+ctsPrp+",2)),0) vlu\n" + 
        ",trunc(((sum(trunc("+ctsPrp+",2)*nvl("+fapriPrp+",0)) / sum(trunc(trunc("+ctsPrp+",2),2)* greatest(rap_rte,1) ))*100) - 100, 2) vludis\n"+
        "From Gt_Srch_Rslt Group By "+shPrp+","+shSrt+"\n" + 
        "order by 2";
          outLst = db.execSqlLst("shapettlQ", shapettlQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String shVal = util.nvl(rs.getString(shPrp));
            dataDtl.put(shVal+"@VLU",util.nvl(rs.getString("vlu")));
            dataDtl.put(shVal+"@DIS",util.nvl(rs.getString("vludis")));
            if(!shapeLst.contains(shVal))
                shapeLst.add(shVal);
        }
            rs.close();
            pst.close();
        String ttlQ="Select Trunc(Sum(Nvl("+fapriPrp+", 0)*trunc("+ctsPrp+",2)),0) vlu\n" + 
        ",trunc(((sum(trunc("+ctsPrp+",2)*nvl("+fapriPrp+",0)) / sum(trunc("+ctsPrp+",2)* greatest(rap_rte,1) ))*100) - 100, 2) vludis\n"+
        "From Gt_Srch_Rslt";
          outLst = db.execSqlLst("ttlQ", ttlQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            dataDtl.put("TTLVLU",util.nvl(rs.getString("vlu")));
            dataDtl.put("TTLDIS",util.nvl(rs.getString("vludis")));
        }
            rs.close();
            pst.close();
        dataDtl.put("SHAPE",shapeLst);
        session.setAttribute("dataDtl", dataDtl);
        session.setAttribute("memoLst", memoLst);
            util.updAccessLog(req,res,"Analysis Report", "memoshape end");
    return am.findForward("memoshape");
        }
    } 
    public ActionForward shapeclarity(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Analysis Report", "shapeclarity start");
        GenericReportForm udf = (GenericReportForm)af;
        GenericInterface genericInt = new GenericImpl();
        HashMap dbinfo = info.getDmbsInfoLst();
        String sh = (String)dbinfo.get("SHAPE");
        String szval = (String)dbinfo.get("SIZE");
        String colval = (String)dbinfo.get("COL");
        String clrval = (String)dbinfo.get("CLR");
        ArrayList ANLSVWList = genericInt.genericPrprVw(req, res, "shapeclarity_vw","SHAPECLRANA_VW");
        int indexSH = ANLSVWList.indexOf(sh)+1;
        int indexSZ = ANLSVWList.indexOf(szval)+1;
        int indexCol = ANLSVWList.indexOf(colval)+1;
        int indexClr = ANLSVWList.indexOf(clrval)+1;
        String shPrp = util.prpsrtcolumn("prp",indexSH);
        String szPrp = util.prpsrtcolumn("prp",indexSZ);
        String colPrp = util.prpsrtcolumn("prp",indexCol);
        String clrPrp = util.prpsrtcolumn("prp",indexClr);
        String shSrt = util.prpsrtcolumn("srt",indexSH);
        String szSrt = util.prpsrtcolumn("srt",indexSZ);
        String clrSrt = util.prpsrtcolumn("srt",indexClr);
        HashMap dataDtl = new HashMap();
        ArrayList grpLst=new ArrayList();
        ArrayList shapeLst=new ArrayList();
        ArrayList szLst=new ArrayList();
        String key="";
        String dataQ="Select "+shPrp+","+shSrt+","+szPrp+",b.grp grp,Stt\n" + 
        ",trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),0) avg,\n" + 
        "Trunc(((Sum(Quot*Trunc(Cts,2))/Sum(Trunc(Cts, 2)))/(Sum(Rap_Rte*Trunc(Cts,2))/Sum(Trunc(Cts, 2)))*100) - 100, 2) avg_dis\n" + 
        "From Gt_Srch_Rslt A,Prp B\n" + 
        "Where A."+clrPrp+"=B.Val And A."+clrSrt+"=B.Srt And B.Mprp='"+clrval+"' \n"+
        "Group By "+shPrp+","+shSrt+","+szPrp+", B.Grp,Stt\n" + 
        "Order by 2,3,4,5";
          ArrayList outLst = db.execSqlLst("dataQ", dataQ, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        String szVal = util.nvl(rs.getString(szPrp));
        String shVal = util.nvl(rs.getString(shPrp));
        String grp = util.nvl(rs.getString("grp"));
        String stt = util.nvl(rs.getString("Stt"));
        key=stt+"@"+szVal+"@"+grp+"@"+shVal;
        dataDtl.put(key+"@AVG",util.nvl(rs.getString("avg")));
        dataDtl.put(key+"@DIS",util.nvl(rs.getString("avg_dis")));
        if(!szLst.contains(szVal))
            szLst.add(szVal);
        if(!shapeLst.contains(shVal))
        shapeLst.add(shVal);
        }
            rs.close();
            pst.close();
        String grpQ="Select Distinct B.Grp grp \n" + 
        "From Gt_Srch_Rslt A,Prp B\n" + 
        "Where A."+clrPrp+"=B.Val And a."+clrSrt+"=B.Srt And B.Mprp='"+clrval+"'\n" + 
        "order by B.Grp desc";
          outLst = db.execSqlLst("grpQ", grpQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        grpLst.add(util.nvl(rs.getString("grp")));
        }
            rs.close();
            pst.close();
        String szQ="Select Distinct b.val,b.dsc \n" + 
        "From Gt_Srch_Rslt A,Prp B\n" + 
        "Where A."+szPrp+"=B.Val And a."+szSrt+"=B.Srt And B.Mprp='"+szval+"'\n";
          outLst = db.execSqlLst("szQ", szQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        String val=util.nvl(rs.getString("val"));
        dataDtl.put(val,(util.nvl(rs.getString("dsc"))));
        }
            rs.close();
            pst.close();
        dataDtl.put("GRP",grpLst);
        dataDtl.put("SHAPE",shapeLst);
        dataDtl.put("SIZE",szLst);
        session.setAttribute("dataDtl", dataDtl);
            util.updAccessLog(req,res,"Analysis Report", "shapeclarity end");
    return am.findForward("shapeclarity");
        }
    } 
    
    public ActionForward pricegraphrpt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Analysis Report", "pricegraphrpt start");
        GenericReportForm udf = (GenericReportForm)af;
        GenericInterface genericInt = new GenericImpl();
        HashMap dbinfo = info.getDmbsInfoLst();
        String sh = (String)dbinfo.get("SHAPE");
        String szval = (String)dbinfo.get("SIZE");
        String colval = (String)dbinfo.get("COL");
        String clrval = (String)dbinfo.get("CLR");
        ArrayList ANLSVWList = genericInt.genericPrprVw(req, res, "shapeclarity_vw","SHAPECLRANA_VW");
        int indexSH = ANLSVWList.indexOf(sh)+1;
        int indexSZ = ANLSVWList.indexOf(szval)+1;
        int indexCol = ANLSVWList.indexOf(colval)+1;
        int indexClr = ANLSVWList.indexOf(clrval)+1;
        String shPrp = util.prpsrtcolumn("prp",indexSH);
        String szPrp = util.prpsrtcolumn("prp",indexSZ);
        String colPrp = util.prpsrtcolumn("prp",indexCol);
        String clrPrp = util.prpsrtcolumn("prp",indexClr);
        String shSrt = util.prpsrtcolumn("srt",indexSH);
        String szSrt = util.prpsrtcolumn("srt",indexSZ);
        String clrSrt = util.prpsrtcolumn("srt",indexClr);
        HashMap dataDtl = new HashMap();
        ArrayList grpLst=new ArrayList();
        ArrayList shapeLst=new ArrayList();
        ArrayList monthLst=new ArrayList();
        String key="";
        String dataQ="Select "+shPrp+","+shSrt+",B.Grp Grp,To_Number(To_Char(Trunc(Nvl(sl_dte,Sysdate)), 'rrrrmm')) Srt_Mon,\n" + 
        "To_Char(Trunc(Nvl(sl_dte,Sysdate)), 'MON rrrr') Dsp_Mon,Stt\n" + 
        ",trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),0) avg\n" + 
        "From Gt_Srch_Rslt A,Prp B\n" + 
        "Where A."+clrPrp+"=B.Val And A."+clrSrt+"=B.Srt And B.Mprp='CLR' \n" + 
        "Group By "+shPrp+","+shSrt+", B.Grp, To_Number(To_Char(Trunc(Nvl(sl_dte,Sysdate)), 'rrrrmm'))\n" + 
        ",To_Char(Trunc(Nvl(sl_dte,Sysdate)), 'MON rrrr'), Stt\n" + 
        "Order by 4";
         ArrayList outLst = db.execSqlLst("dataQ", dataQ, new ArrayList());
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        String shVal = util.nvl(rs.getString(shPrp));
        String grp = util.nvl(rs.getString("grp"));
        String stt = util.nvl(rs.getString("Stt"));
        String month = util.nvl(rs.getString("Dsp_Mon"));
        key=stt+"@"+shVal+"@"+grp+"@"+month;
        dataDtl.put(key+"@AVG",util.nvl(rs.getString("avg")));
        if(!monthLst.contains(month))
            monthLst.add(month);
        }
            rs.close();
            pst.close();
        String grpQ="Select Distinct B.Grp grp \n" + 
        "From Gt_Srch_Rslt A,Prp B\n" + 
        "Where A."+clrPrp+"=B.Val And a."+clrSrt+"=B.Srt And B.Mprp='"+clrval+"'\n" + 
        "order by B.Grp desc";
           outLst = db.execSqlLst("grpQ", grpQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        grpLst.add(util.nvl(rs.getString("grp")));
        }
            rs.close();
            pst.close();
        String shQ="Select distinct "+shPrp+","+shSrt+" from Gt_Srch_Rslt order by "+shSrt;
          outLst = db.execSqlLst("shQ", shQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        shapeLst.add(util.nvl(rs.getString(shPrp)));
        }
            rs.close();
            pst.close();
        dataDtl.put("GRP",grpLst);
        dataDtl.put("SHAPE",shapeLst);
        dataDtl.put("MONTH",monthLst);
        session.setAttribute("dataDtl", dataDtl);
            util.updAccessLog(req,res,"Analysis Report", "pricegraphrpt end");
    return am.findForward("pricegraphrpt");
        }
    } 
    public ActionForward createPDF(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Analysis Report", "createPDF start");
    SearchRslt(req, res);
    PdfforReport pdf=new PdfforReport();
    HashMap dbSysInfo = info.getDmbsInfoLst();
    String docPath = (String)dbSysInfo.get("DOC_PATH");
    String loop = util.nvl(req.getParameter("loopno"));
    String Span = util.nvl(req.getParameter("colSpan"));
    int spanno=0;
    if(!Span.equals(""))
    spanno=Integer.parseInt(Span);
    if(spanno==0)
        spanno=1;  
    int loopno=Integer.parseInt(loop);
        String qty=util.nvl(req.getParameter("qty"));
        String cts=util.nvl(req.getParameter("cts"));
        String avg=util.nvl(req.getParameter("avg"));
        String rap=util.nvl(req.getParameter("rap"));
        String age=util.nvl(req.getParameter("age"));
        String hit=util.nvl(req.getParameter("hit"));
   String FILE = "Analysis_Report_"+util.getToDteTime()+".pdf";
   //String FILEPATH="c:/pdfana/"+FILE;
   String path = getServlet().getServletContext().getRealPath("/") + FILE;
    String FILEPATH= path+""+FILE;
   ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
    pdf.getDataInMultiplePDFQuick(statusLst,qty,cts,avg,rap,age,hit,spanno,loopno,req,FILEPATH);
    byte[] byteArray=null;
    InputStream inputStream = new FileInputStream(FILEPATH);
    byteArray = readFully(inputStream);

    inputStream.close();
    res.setHeader("Pragma", "no-cache");
    res.setHeader("Cache-control", "private");
    res.setDateHeader("Expires", 0);
    res.setContentType("application/pdf");
    res.setHeader("Content-Disposition", "attachment; filename="+ FILE);

    if (byteArray != null) {
    res.setContentLength(byteArray.length);
    ServletOutputStream out = res.getOutputStream();
    out.write(byteArray);
    out.flush();
    out.close();
    }
            util.updAccessLog(req,res,"Analysis Report", "createPDF start");
    return null;
        }
    }
    
    public void mongoAnalysisSearch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res,String mdl,String frmDte,String toDte,String psearch)
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

       }else{
        util.updAccessLog(req,res,"Analysis Report", "loadDtl start");
        GenericReportForm udf = (GenericReportForm)af;
        String vnmStr = util.nvl((String)udf.getValue("vnm"));
        String srchRef = util.nvl((String)udf.getValue("srchRef"));
        String srchTyp = util.nvl((String)udf.getValue("srchTyp"));
        String srchBy = util.nvl((String)udf.getValue("srchBy"));
        String idn = util.nvl((String)udf.getValue("idn"));
        String byr = util.nvl((String)udf.getValue("byr"));
        String lprpStr="";
        String lprpVal="";
        String lprpStrDsc="";
        String lprpValDsc="";
        String lprpTyp="";
        String sttStr="";
        String tblNme="STK_ASRT,STK_MKTG,STK_SOLD";
        if(idn.equals(""))
        idn = byr;       
        HashMap dtls=new HashMap();
        int maxThreads = 100 ;
        int threadCount = 100;
        int minIndex=0;
        int maxIndex=0;
        String srchDsc="";
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, mdl,mdl);
        HashMap dfstksttDtl  =((HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_df_stk_stt") == null)?new HashMap():(HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_df_stk_stt"); 
        ArrayList findTbls=new ArrayList();
        ArrayList findstt=new ArrayList();
        HashMap allpktListMap = new HashMap();
           ArrayList sttList = (session.getAttribute("sttLst") == null)?new ArrayList():(ArrayList)session.getAttribute("sttLst");
           if(!srchBy.equals("C") && !vnmStr.equals("")){
               if(srchRef.equals("vnm"))
               srchRef="S";
               else
               srchRef="C";
               vnmStr = util.getVnm(vnmStr);
               vnmStr = vnmStr.replaceAll("'", "");
               lprpVal = vnmStr;
               lprpStr = srchRef;
               lprpTyp = "M";
               sttStr="";
               findTbls.add("PMKT");
               findTbls.add("MKT");
               findTbls.add("SOLD");
           }else{
               ArrayList prpNameList = (ArrayList)session.getAttribute("prpList");
               HashMap prpMap = (HashMap)session.getAttribute("prpMap");
               HashMap mprp = info.getMprp();
               HashMap prp = info.getPrp();
               for(int i=0;i< prpNameList.size();i++){
                 String grpNme = (String)prpNameList.get(i);
                 String listName = (String)prpMap.get(grpNme);
                 ArrayList prpList = (ArrayList)session.getAttribute(listName);
                  if(prpList!=null && prpList.size()>0){
                     for(int j=0;j<prpList.size();j++){
                       boolean dtlAddedOnce = false ;
                       ArrayList prpDtl = (ArrayList)prpList.get(j);
                       String lprp= (String)prpDtl.get(0);
                       ArrayList lprpS = (ArrayList)prp.get(lprp+"S");
                       ArrayList lprpV = (ArrayList)prp.get(lprp+"V");
                       String dtaTyp = util.nvl((String)mprp.get(lprp+"T"));
                       String dsc = util.nvl((String)mprp.get(lprp+"D"));
                       String flg="M";
                         if(dtaTyp.equals("T") || dtaTyp.equals("D"))
                         flg="Y";
                         String prpStr="";
                         String prpValStr="";
                         String prpStrDsc="";
                         String prpValStrDsc="";
                         if(lprpS!=null){
                         for(int m=0; m < lprpV.size(); m++) {
                             String lVal = (String)lprpV.get(m);
                             String lFld =  lprp + "_" + lVal; 
                             String reqVal = util.nvl((String)udf.getValue(lFld));
                               if(reqVal.length() > 0 && !reqVal.equals("0")) {  
                                     String srtVal=(String)lprpS.get(lprpV.indexOf(reqVal));
                                     if(prpValStr.equals("")){
                                             if(lprp.equals("CO") || lprp.equals("PU") || lprp.equals("COL") || lprp.equals("CLR") || lprp.equals("CUT") || lprp.equals("POL") || lprp.equals("SYM") || lprp.equals("CT") || lprp.equals("PO") || lprp.equals("SY")){
                                           for(int s=0; s < lprpV.size(); s++) {
                                           String startwith=util.nvl((String)lprpV.get(s));
                                               if (startwith.startsWith(reqVal) && (startwith.endsWith("+") || startwith.endsWith("-") || startwith.endsWith("1") || startwith.endsWith("2") || startwith.endsWith("3") || startwith.endsWith("4") || startwith.equals(reqVal))){
                                               if(prpValStr.equals("")){
                                                   prpValStr= (String)lprpS.get(lprpV.indexOf(startwith));
                                               }else{
                                                   prpValStr=prpValStr+"@"+(String)lprpS.get(lprpV.indexOf(startwith));
                                               }
                                           }
                                         }
                                         }else{
                                             prpValStr= srtVal;
                                         }
                                         prpValStrDsc=reqVal;
                                     }else{
                                         if(lprp.equals("CO") || lprp.equals("PU") || lprp.equals("COL") || lprp.equals("CLR") || lprp.equals("CUT") || lprp.equals("POL") || lprp.equals("SYM") || lprp.equals("CT") || lprp.equals("PO") || lprp.equals("SY")){
                                           for(int s=0; s < lprpV.size(); s++) {
                                           String startwith=util.nvl((String)lprpV.get(s));
                                               if (startwith.startsWith(reqVal) && (startwith.endsWith("+") || startwith.endsWith("-") || startwith.endsWith("1") || startwith.endsWith("2") || startwith.endsWith("3") || startwith.endsWith("4") || startwith.equals(reqVal))){
                                               if(prpValStr.equals("")){
                                                   prpValStr= (String)lprpS.get(lprpV.indexOf(startwith));
                                               }else{
                                                   prpValStr=prpValStr+"@"+(String)lprpS.get(lprpV.indexOf(startwith));
                                               }
                                           }
                                         }
                                         }else{
                                           prpValStr= prpValStr+"@"+srtVal;
                                         }
                                         prpValStrDsc= prpValStrDsc+"@"+reqVal;
                                     }
                                     if(prpStr.equals("")){
                                         prpStr=lprp;
                                         prpStrDsc=dsc;
                                     }
                             }
                         }
                         }else{
                         String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                         String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                         String fldVal1Dsc=fldVal1;
                         String fldVal2Dsc=fldVal2;
                         if(dtaTyp.equals("T")){
                         fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                         fldVal2 = fldVal1;
                         fldVal1Dsc = fldVal1;
                         fldVal2Dsc = fldVal1;
                         }
                         if(dtaTyp.equals("D")){
                           fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                           fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                           if(!fldVal1.equals("") && !fldVal2.equals("")){
                                 fldVal1Dsc = fldVal1;
                                 fldVal2Dsc = fldVal2;
                                 String arr[] = fldVal1.split("-");
                                 fldVal1 = arr[2]+""+arr[1]+""+arr[0];
                                 String arr1[] = fldVal2.split("-");
                                 fldVal2 = arr1[2]+""+arr1[1]+""+arr1[0];
                                 System.out.println("fldValue"+fldVal1);
                           }
                         }
                               
                         if(fldVal2.equals("")){
                         fldVal2=fldVal1;
                         fldVal2Dsc = fldVal1;
                         }
                             
                         
                         if(!fldVal1.equals("") && !fldVal2.equals("")){
                                 prpValStr=fldVal1+"@"+fldVal2;
                                 prpValStrDsc=fldVal1Dsc+"@"+fldVal2Dsc;
                                 if(prpStr.equals("")){
                                     prpStr=lprp;
                                     prpStrDsc=dsc;
                                 }
                         }
                     }
                     if(!prpStr.equals("")){
                     if(lprpVal.equals("")){
                         lprpVal=prpValStr;
                         lprpStr=prpStr;
                         lprpStrDsc=prpStrDsc;
                         lprpValDsc=prpValStrDsc;
                         if(dtaTyp.equals("T"))
                         lprpTyp="M";
                         else
                         lprpTyp=flg;
                     }else{
                         lprpVal=lprpVal+"#"+prpValStr;
                         lprpStr=lprpStr+"#"+prpStr;
                         lprpStrDsc=lprpStrDsc+"#"+prpStrDsc;
                         lprpValDsc=lprpValDsc+"#"+prpValStrDsc;
                         if(dtaTyp.equals("T"))
                         lprpTyp=lprpTyp=lprpTyp+"#M";
                         else
                         lprpTyp=lprpTyp+"#"+flg;
                     }}
                 }
               }}
           }
           System.out.println(lprpTyp);
           System.out.println(lprpStr);
           System.out.println(lprpVal);
           System.out.println(lprpStrDsc);
           System.out.println(lprpValDsc);
           
           
           for(int n=0;n<sttList.size();n++){
            ArrayList sttDtl = (ArrayList)sttList.get(n);
            String stt = util.nvl((String)udf.getValue((String)sttDtl.get(0)));
            if((!stt.equals("") && psearch.equals("N")) || (stt.equals("SOLD") && psearch.equals("Y"))) {
                HashMap grp1Dtl=(HashMap)dfstksttDtl.get(stt);
                ArrayList sttLst=(ArrayList)grp1Dtl.get("STT");
                ArrayList mdlLst=(ArrayList)grp1Dtl.get("MDL");
                    for(int j=0;j<mdlLst.size();j++){
                        String mdldf=(String)mdlLst.get(j);
                        if(!findTbls.contains(mdldf))
                        findTbls.add(mdldf);
                    }
                    findstt.addAll(sttLst);
            }
           }  
           
           if(findTbls.indexOf("PMKT") >  -1 && findTbls.indexOf("MKT") >  -1)
               tblNme="STK_ASRT,STK_MKTG";
           else if(findTbls.indexOf("PMKT") >  -1)
               tblNme="STK_ASRT";
           else if(findTbls.indexOf("MKT") >  -1)
               tblNme="STK_MKTG";

           if(findstt.size()>0){
           sttStr= findstt.toString();
           sttStr = sttStr.replaceAll("\\[", "");
           sttStr = sttStr.replaceAll("\\]", "").replaceAll(" ", "");
           }
           System.out.println(sttStr);
           if(findTbls.indexOf("PMKT") >  -1 || findTbls.indexOf("MKT") >  -1){
           dtls=new HashMap();
           dtls.put("prpVal",lprpVal);
           dtls.put("prp",lprpStr);
           dtls.put("prpTyp",lprpTyp);
           dtls.put("stt",sttStr);
           dtls.put("mdl",mdl);
           dtls.put("tblNme", tblNme);
           HashMap pktListMap=getPacketsFromMongo(req,res,dtls);
           if(pktListMap.size()>0)
           allpktListMap.putAll(pktListMap);
           srchDsc=util.srchDscriptionMongo(lprpTyp,lprpStrDsc,lprpValDsc,sttStr,tblNme,"");
           }

           
           if(findTbls.indexOf("SOLD") >  -1){
               tblNme="STK_SOLD";
               if(srchBy.equals("C")){
               if(!frmDte.equals("") && !toDte.equals("") && !psearch.equals("Y")){
                   String f1=util.convertyyyymmddFmt(frmDte);
                   String f2=util.convertyyyymmddFmt(toDte);
                   if(lprpTyp.equals("")){
                       lprpTyp="Y";
                       lprpStr="SAL_DTE";
                       lprpVal=f1+"@"+f2;
                       lprpStrDsc="SAL_DTE";
                       lprpValDsc=frmDte+"@"+toDte;
                   }else{
                       lprpTyp+="#Y";
                       lprpStr+="#SAL_DTE";
                       lprpVal+="#"+f1+"@"+f2;
                       lprpStrDsc+="#SAL_DTE";
                       lprpValDsc+="#"+frmDte+"@"+toDte;
                   }
               }
               if(!srchTyp.equals("0") && !srchTyp.equals("")){
                   String byrNme="";
                   if(srchTyp.equals("SALE_ID")){
                       byr= util.getVnm(idn);
                       byr = byr.replaceAll("'", "");
                   }
                    if(srchTyp.equals("VENDOR") || srchTyp.equals("VENDOR") || srchTyp.equals("EMPLOYEE")){
                    String byrNmeQry = "select GET_ANA_BYR_NM(?) from dual";
                    ArrayList ary = new ArrayList();
                    ary.add(byr);
                    ResultSet rs = db.execSql(" Srch Prp ", byrNmeQry, ary);
                    while (rs.next()) {
                           byrNme = rs.getString(1);  
                    }
                    rs.close();
                    }else{
                       byrNme=byr;
                    }
                   if(srchTyp.equals("BUYER"))
                       srchTyp="SAL_BYR_IDN";
                   if(srchTyp.equals("VENDOR"))
                       srchTyp="VENDOR";
                   if(srchTyp.equals("VENDER"))
                       srchTyp="VENDOR";
                   if(srchTyp.equals("EMPLOYEE"))
                         srchTyp="SAL_EMP";
                   if(srchTyp.equals("COUNTRY"))
                         srchTyp="BYR_COUNTRY";
                   if(srchTyp.equals("SALE_ID"))
                         srchTyp="SAL_IDN";
                   if(lprpTyp.equals("")){
                       lprpTyp="M";
                       lprpStr=srchTyp;
                       lprpVal=byrNme;
                       lprpStrDsc=srchTyp;
                       lprpValDsc=byrNme;
                   }else{
                       lprpTyp+="#M";
                       lprpStr+="#"+srchTyp;
                       lprpVal+="#"+byrNme;
                       lprpStrDsc+="#"+srchTyp;
                       lprpValDsc+="#"+byrNme;
                   }
               }
               }
               dtls=new HashMap();
               dtls.put("prpVal",lprpVal);
               dtls.put("prp",lprpStr);
               dtls.put("prpTyp",lprpTyp);
               dtls.put("stt",sttStr);
               dtls.put("mdl",mdl);
               dtls.put("tblNme", tblNme);
               if(psearch.equals("N")){
                   HashMap pktListMap=getPacketsFromMongo(req,res,dtls);
                   if(pktListMap.size()>0)
                   allpktListMap.putAll(pktListMap);
               srchDsc+=util.srchDscriptionMongo(lprpTyp,lprpStrDsc,lprpValDsc,sttStr,tblNme,"");
               }else{
                          String p1frm = util.nvl((String)udf.getValue("p1frm"));
                          String p1to = util.nvl((String)udf.getValue("p1to"));
                          String p2frm = util.nvl((String)udf.getValue("p2frm"));
                          String p2to = util.nvl((String)udf.getValue("p2to"));
                          String p3frm = util.nvl((String)udf.getValue("p3frm"));
                          String p3to = util.nvl((String)udf.getValue("p3to"));
                          ArrayList status=new ArrayList();
                          if(!p1frm.equals("")  && !p1to.equals(""))
                          status.add("P1");
                          if(!p2frm.equals("")  && !p2to.equals(""))
                          status.add("P2");
                          if(!p3frm.equals("")  && !p3to.equals(""))
                          status.add("P3");
                          for(int st=0;st<status.size();st++){
                              String Stt=(String)status.get(st);
                              String lprpTypPeriod=lprpTyp;
                              String lprpStrPeriod=lprpStr;
                              String lprpValPeriod=lprpVal;
                              String lprpStrDscPeriod=lprpStrDsc;
                              String lprpValDscPeriod=lprpValDsc;
                              String f1="";
                              String f2="";
                              if(Stt.equals("P1")){
                                  f1=util.convertyyyymmddFmt(p1frm);
                                  f2=util.convertyyyymmddFmt(p1to);
                                  frmDte=p1frm;
                                  toDte=p1to;
                              }
                              if(Stt.equals("P2")){
                                  f1=util.convertyyyymmddFmt(p2frm);
                                  f2=util.convertyyyymmddFmt(p2to);
                                  frmDte=p2frm;
                                  toDte=p2to;
                              }
                              if(Stt.equals("P3")){
                                  f1=util.convertyyyymmddFmt(p3frm);
                                  f2=util.convertyyyymmddFmt(p3to);
                                  frmDte=p3frm;
                                  toDte=p3to;
                              }
                              if(lprpTypPeriod.equals("")){
                                  lprpTypPeriod="Y";
                                  lprpStrPeriod="SAL_DTE";
                                  lprpValPeriod=f1+"@"+f2;
                                  lprpStrDscPeriod="SAL_DTE";
                                  lprpValDscPeriod=frmDte+"@"+toDte;
                              }else{
                                  lprpTypPeriod+="#Y";
                                  lprpStrPeriod+="#SAL_DTE";
                                  lprpValPeriod+="#"+f1+"@"+f2;
                                  lprpStrDscPeriod+="#SAL_DTE";
                                  lprpValDscPeriod+="#"+frmDte+"@"+toDte;
                              }
                              dtls=new HashMap();
                              dtls.put("prpVal",lprpValPeriod);
                              dtls.put("prp",lprpStrPeriod);
                              dtls.put("prpTyp",lprpTypPeriod);
                              dtls.put("stt",sttStr);
                              dtls.put("mdl",mdl);
                              dtls.put("tblNme", tblNme);
                              HashMap pktListMap=getPacketsFromMongo(req,res,dtls);
                              if(pktListMap.size()>0)
                              allpktListMap.putAll(pktListMap);
                              srchDsc+=util.srchDscriptionMongo(lprpTypPeriod,lprpStrDscPeriod,lprpValDscPeriod,sttStr,tblNme,Stt);
                          }
              }
           }
           
            session.setAttribute("mongosrchDsc", srchDsc);
            int pktCnt=allpktListMap.size();
            if(pktCnt>0)
            maxIndex=pktCnt;
           session.setAttribute("genericPacketLstMap", allpktListMap);
            session.setAttribute("genericPacketLstMap", (HashMap)sortByComparatorMap(allpktListMap,"sk1"));
           
            System.out.println("@total Packet Count :"+pktCnt);
            
       }
    }
    
    public String generateInsertSql(ArrayList vwPrpLst,DBUtil util){
        int vwPrpLstsz=vwPrpLst.size();
        String insertsqlQ=" Insert into gt_srch_rslt (rln_idn, srch_id, pkt_ty, stk_idn, vnm, qty,sl_rte, cts, pkt_dte,\n"+
        "stt,dsp_stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis\n" + 
        ", CERTIMG, DIAMONDIMG, JEWIMG, SRAYIMG, AGSIMG, MRAYIMG, RINGIMG, LIGHTIMG, REFIMG, VIDEOS, VIDEO_3D,fnl_usd,byr,byr_cntry,emp,sl_dte,rmk,srt_090";
        String valuessqlQ=" values(?,?,?,?,?,?,?,?,to_date(?,'dd-Mon-YYYY'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd-Mon-YYYY'),?,?";
        
        for (int j = 0; j <vwPrpLstsz; j++) {
            String prp = (String)vwPrpLst.get(j);
            int rnk=j+1;
            String lbPrp = util.prpsrtcolumn("prp",rnk);
            String lbsrt = util.prpsrtcolumn("srt",rnk);
            insertsqlQ+=","+lbPrp+","+lbsrt;
            if(!prp.equals("KTSVIEW") && !prp.equals("COMMENT") && !prp.equals("MEM_COMMENT"))
            valuessqlQ+=",?,?";
            else
            valuessqlQ+=",substr(?,0,89),?";
            if(prp.equals("KTSVIEW")){
                insertsqlQ+=",kts_vw";
                valuessqlQ+=",?";
            }else if(prp.equals("COMMENT")){
                insertsqlQ+=",cmnt";
                valuessqlQ+=",?";
            }else if(prp.equals("MEM_COMMENT")){
                insertsqlQ+=",img2";
                valuessqlQ+=",?";
            }
        }
        String finalSqlQ=insertsqlQ+") "+valuessqlQ+")";
        return finalSqlQ;
    }
    public HashMap getPacketsFromMongo(HttpServletRequest req, HttpServletResponse res,HashMap dtls) throws Exception{
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap dbinfo = info.getDmbsInfoLst();
        String mongodb = util.nvl((String)dbinfo.get("MONGODB"));
        String rep_path = util.nvl((String)dbinfo.get("REP_PATHNEW"),util.nvl((String)dbinfo.get("REP_PATH")));
        GenericInterface genericInt = new GenericImpl();
        String lprpTyp=util.nvl((String)dtls.get("prpTyp"));
        String lprpStr=util.nvl((String)dtls.get("prp"));
        String lprpVal=util.nvl((String)dtls.get("prpVal"));
        String sttStr=util.nvl((String)dtls.get("stt"));
        String mdl=util.nvl((String)dtls.get("mdl"));
        String tblNme=util.nvl((String)dtls.get("tblNme"));
        long startm = new Date().getTime();
        System.out.println(lprpTyp);
        System.out.println(lprpStr);
        System.out.println(lprpVal);
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, mdl,mdl);
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(rep_path+"/diaflexWebService/REST/WebService/searchValues");
        postRequest.setHeader("Accept", "application/json");
        postRequest.setHeader("Content-type", "application/json");
        JSONObject jObj = new JSONObject();
        try{
                 jObj.put("prpTyp", lprpTyp);
                 jObj.put("prp", lprpStr);
                 jObj.put("prpVal", lprpVal);
                 jObj.put("stt", sttStr);
                 jObj.put("mdl", mdl);
                 jObj.put("pkt_ty", "NR,NR_MX");
                 jObj.put("tblNme", tblNme);
                 jObj.put("clientName", mongodb);                                     
        } catch (JSONException jsone) {
        jsone.printStackTrace();
        }
                     
        StringEntity insetValue = new StringEntity(jObj.toString());
        insetValue.setContentType(MediaType.APPLICATION_JSON);
        postRequest.setEntity(insetValue);
                                            
        HttpResponse responsejson = httpClient.execute(postRequest);
                                            
        if (responsejson.getStatusLine().getStatusCode() !=200) {
        throw new RuntimeException("Failed : HTTP error code : "
                               + responsejson.getStatusLine().getStatusCode());
        }
                                            
                                                   
        BufferedReader br = new BufferedReader(new InputStreamReader((responsejson.getEntity().getContent())));
        String outsetValue="";
        String jsonStr="";
        //System.out.println("OutsetValue from Server .... \n"+br.readLine());
        while ((outsetValue = br.readLine()) != null) {
                                           //    System.out.println(outsetValue);
           jsonStr =jsonStr+outsetValue ;
        }
                                   //    System.out.println(jsonStr);
        httpClient.getConnectionManager().shutdown();
        HashMap pktListMap=new HashMap();
        
        int count = 0;
        if(!jsonStr.equals("")){
        JSONObject  jObject = new JSONObject(jsonStr);
        JSONArray  dataObject = (JSONArray)jObject.get("STKDTL");
        if(dataObject!=null){
            for (int i = 0; i < dataObject.length(); i++) {
                count++;
                JSONObject pktDtl = dataObject.getJSONObject(i);
                HashMap pktPrpMap = new HashMap();
                String stt=util.nvl((String)pktDtl.get("STATUS"));
                String grp1=util.nvl((String)pktDtl.get("GRP1"));
                String rap_rte=util.nvl((String)pktDtl.get("RAP_RTE"));
                String cts=util.nvl((String)pktDtl.get("CRTWT"),"0.01");
                String stk_idn=util.nvl((String)pktDtl.get("STK_IDN"),"0.01");
                pktPrpMap.put("stk_idn", stk_idn);  
                pktPrpMap.put("pkt_ty", util.nvl((String)pktDtl.get("PKT_TY")));
                pktPrpMap.put("vnm", util.nvl((String)pktDtl.get("PACKETCODE")));
                pktPrpMap.put("qty", util.nvl((String)pktDtl.get("QTY")));
                pktPrpMap.put("cts", cts);
                pktPrpMap.put("cmp", util.nvl((String)pktDtl.get("RTE")));
                pktPrpMap.put("rap_rte", rap_rte);
                pktPrpMap.put("cert_lab", util.nvl((String)pktDtl.get("LAB")));
                pktPrpMap.put("sk1", new BigDecimal(util.nvl((String)pktDtl.get("SK1"),"0")));
                pktPrpMap.put("flg", "Z");
                if(stt.equals("MKSL") || stt.equals("MKSD") || stt.equals("BRC_MKSD")){
                pktPrpMap.put("quot", util.nvl((String)pktDtl.get("SAL_RTE")));
                pktPrpMap.put("fnl_usd", util.nvl((String)pktDtl.get("SAL_FNLBSE")));
                }else{
                pktPrpMap.put("quot", util.nvl((String)pktDtl.get("RTE")));
                pktPrpMap.put("fnl_usd", util.nvl((String)pktDtl.get("RTE")));
                }
                pktPrpMap.put("dsp_stt", stt);
                pktPrpMap.put("stt", grp1);
                pktPrpMap.put("org_dsp_stt", stt);
                pktPrpMap.put("org_stt", grp1);
                pktPrpMap.put("actstt", stt);
                pktPrpMap.put("rap_dis", util.nvl((String)pktDtl.get("RAP_DIS")));
                pktPrpMap.put("CERTIMG", util.nvl((String)pktDtl.get("CERTIMG")));
                pktPrpMap.put("DIAMONDIMG", util.nvl((String)pktDtl.get("DIAMONDIMG")));
                pktPrpMap.put("JEWIMG", util.nvl((String)pktDtl.get("JEWIMG")));
                pktPrpMap.put("SRAYIMG", util.nvl((String)pktDtl.get("SRAYIMG")));
                pktPrpMap.put("AGSIMG", util.nvl((String)pktDtl.get("AGSIMG")));
                pktPrpMap.put("MRAYIMG", util.nvl((String)pktDtl.get("MRAYIMG")));
                pktPrpMap.put("RINGIMG", util.nvl((String)pktDtl.get("RINGIMG")));
                pktPrpMap.put("LIGHTIMG", util.nvl((String)pktDtl.get("LIGHTIMG")));
                pktPrpMap.put("REFIMG", util.nvl((String)pktDtl.get("REFIMG")));
                pktPrpMap.put("VIDEOS", util.nvl((String)pktDtl.get("VIDEOS")));
                pktPrpMap.put("VIDEO_3D", util.nvl((String)pktDtl.get("VIDEO_3D")));
                pktPrpMap.put("byr", util.nvl((String)pktDtl.get("SAL_BYR")));
                pktPrpMap.put("emp", util.nvl((String)pktDtl.get("SAL_EMP")));
                pktPrpMap.put("byr_cntry", util.nvl((String)pktDtl.get("BYR_COUNTRY")));
                pktPrpMap.put("rmk", util.nvl((String)pktDtl.get("SAL_RMK")));
                pktPrpMap.put("QTY", "1");
                String pkt_dte="";
                String sal_dte="";
                try{
                String recpt_dt=util.nvl((String)pktDtl.get("RECPT_DT"));
                String trf_dte=util.nvl((String)pktDtl.get("TRF_DTE"));
                if(grp1.equals("MKT") || grp1.equals("MKAP") || grp1.equals("MKIS") || grp1.equals("MKTOUT") || grp1.equals("SOLD")){
                    pkt_dte=trf_dte;
                }else{
                    pkt_dte=recpt_dt;
                }
                sal_dte=util.nvl((String)pktDtl.get("SAL_DTE"));
                pktPrpMap.put("sl_dte", sal_dte);
                pktPrpMap.put("pkt_dte", pkt_dte);
                if(!sal_dte.equals("") && !sal_dte.equals("0")){
                    int pktdte = Integer.parseInt(util.toconvertStringtoDate(sal_dte));
                    pktPrpMap.put("sl_dte_srt", pktdte);
                }
                }catch(JSONException js){
                    pktPrpMap.put("sl_dte", sal_dte);
                    pktPrpMap.put("pkt_dte", pkt_dte);
                    pktPrpMap.put("sl_dte_srt", "");
                }
                for (int j = 0; j < vwPrpLst.size(); j++) {
                  try{
                        String lprp = (String)vwPrpLst.get(j);
                        String typ = util.nvl((String)mprp.get(lprp+"T"));
                        String prpVal="";
                        String prepSrtVal="";
                        String fldVal = util.nvl((String)pktDtl.get(lprp));
                        if(typ.equals("C")){
                           
                            ArrayList lprpS = (ArrayList)prp.get(lprp + "S");
                            ArrayList lprpV = (ArrayList)prp.get(lprp + "V");
                           
                      if(fldVal!=""){
                                      Double obj = new Double(fldVal);
                                      int prpSrt = obj.intValue();
                                      prepSrtVal = String.valueOf(prpSrt);
                                      try{
                                      prpVal = (String)lprpV.get(lprpS.indexOf(prepSrtVal));
                                      if(lprp.equals("CO") || lprp.equals("COL") || lprp.equals("PU") || lprp.equals("CLR")){
                                              prpVal=prpVal.replaceAll("\\+", "");
                                              prpVal=prpVal.replaceAll("\\-", "");
                                      }
                                      }catch(ArrayIndexOutOfBoundsException a){
                                          prpVal=""; 
                                          prepSrtVal="";
                                      }
                      }
                      }else{
                            prpVal = fldVal;
                            prepSrtVal=prpVal;
                            if(typ.equals("T"))
                            prepSrtVal="10";
                            if(typ.equals("D"))
                            prepSrtVal=util.toconvertStringtoDate(fldVal);
                      }
                    
                            pktPrpMap.put(lprp, prpVal);
                            pktPrpMap.put(lprp+"_SRT", prepSrtVal);
                      }catch(JSONException js){
                          
                      }
                    }
                    pktListMap.put(stk_idn,pktPrpMap);
            }
        }
        }
        long endm = new Date().getTime();
        System.out.println("@ Collection on Table "+tblNme+" Total Time = "+ ((endm-startm)/1000));
        return pktListMap;
    }
    
    public static byte[] readFully(InputStream stream) throws IOException
    {
    byte[] buffer = new byte[8192];
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    int bytesRead;
    while ((bytesRead = stream.read(buffer)) != -1)
    {
    baos.write(buffer, 0, bytesRead);
    }
    return baos.toByteArray();
    }
    public ActionForward loadDtl(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
           util.updAccessLog(req,res,"Analysis Report", "loadDtl start");
        GenericReportForm udf = (GenericReportForm)af;
        SearchResult(req,res);
     String Check = req.getParameter("col");
     if(Check!=null){
       udf.setValue("col", util.nvl(Check));
       udf.setValue("clr", util.nvl(req.getParameter("clr")));
     }
//       udf.setValue("WEEKS", "6");
                      util.updAccessLog(req,res,"Analysis Report", "loadDtl end");
        return am.findForward("loadDtl");
       }
   }
    
        public void SearchResult(HttpServletRequest req,HttpServletResponse res ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      HashMap dbinfo = info.getDmbsInfoLst();
      ArrayList prpDspBlocked = info.getPageblockList();
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
      HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
      HashMap allpktListMap = (HashMap)session.getAttribute("genericPacketLstMap");
      ArrayList genericPacketLst=util.getKeyInArrayList(allpktListMap);
      String comparewith=util.nvl(info.getCompareWith(),"cmp");
      String row="",col="",status="",statusIn="",conQ="";
      String fnlPriQ=" ,'' fnlPri";
      ArrayList gridcommLst=new ArrayList();
      ArrayList gridrowLst=new ArrayList();
      ArrayList gridcolLst=new ArrayList();
      ArrayList grpOrderList = new ArrayList();
      ArrayList grpbypktList = new ArrayList();
      ArrayList allpktList = new ArrayList();
      HashMap dataDtl = new HashMap();
        GenericInterface genericInt = new GenericImpl();
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
      ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "ANLS_VW","ANLS_VW");
          
        String key = util.nvl(req.getParameter("key"));
        String orderby = util.nvl(req.getParameter("orderby"));
        String orderbyconQ=orderby;
        if(orderby.equals(""))
            orderbyconQ="sk1"; 
        else{
            String order = util.nvl((String)dbinfo.get(orderby));
            if(!order.equals(""))
                orderby=order;    
            int indexgp = vwPrpLst.indexOf(orderby)+1;
            if(indexgp<=0)
            orderbyconQ=orderby;
            else{
            orderbyconQ=util.prpsrtcolumn("srt",indexgp);  
            }
        }
        String onlysrtby = util.nvl(req.getParameter("onlysrtby"));
        if(!onlysrtby.equals(""))
        orderbyconQ="sk1"; 
        String Check = util.nvl(req.getParameter("row"));
        String orderbyrow = util.nvl(req.getParameter("orderbyrow"));
        HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
        gridcommLst=(ArrayList)gridstructure.get("COMM");
        gridrowLst=(ArrayList)gridstructure.get("ROW");
        gridcolLst=(ArrayList)gridstructure.get("COL");
        int gridcommLstsz=gridcommLst.size();
        int gridrowLstsz=gridrowLst.size();
        int gridcolLstsz=gridcolLst.size();
      ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
      String statusALL= statusLst.toString();
      statusALL = statusALL.replace("[", "");
      statusALL = statusALL.replace("]", "");
      statusALL = statusALL.replaceAll(",", "','");
      String param[] = key.split("@");
      if(key.equals("")){
        status= "ALL";
        }else if(!Check.equals("")){
        row = Check;
        col = req.getParameter("col");
        status = util.nvl(req.getParameter("status"));
        req.setAttribute("row", row);
        req.setAttribute("col", col);
        req.setAttribute("status", status);  
        if(status.equals("ALL"))
                statusIn=statusALL;
          else
            statusIn=status; 
      }else if(!orderbyrow.equals("")){
        if(orderbyrow.indexOf(",") > -1){
        orderbyrow = util.getVnm(orderbyrow);
        orderbyrow=orderbyrow.replaceFirst("\\'", "");
        orderbyrow=orderbyrow.substring(0, orderbyrow.length()-1);
        }
        row = orderbyrow;
        String orderbycol = req.getParameter("orderbycol");
          if(orderbycol.indexOf(",") > -1){
          orderbycol = util.getVnm(orderbycol);
          orderbycol=orderbycol.replaceFirst("\\'", "");
          orderbycol=orderbycol.substring(0, orderbycol.length()-1);
          }
        col = orderbycol;
        status = util.nvl(req.getParameter("status"));
        req.setAttribute("row", row);
        req.setAttribute("col", col);
        req.setAttribute("status", status);  
        if(status.equals("ALL"))
                statusIn=statusALL;
          else
            statusIn=status; 
      }else{
        row = param[gridcommLstsz];
        col = param[gridcommLstsz+gridrowLstsz];
        status = param[gridcommLstsz+gridcolLstsz+gridrowLstsz]; 
        req.setAttribute("row", row);
        req.setAttribute("col", col);
        req.setAttribute("status", status);  
        if(status.equals("ALL"))
              statusIn=statusALL;
        else
          statusIn=status; 
          if(statusLst.contains("SOLD") && !status.equals("SOLD")){
              statusIn+="','SOLD";   
          }
      }    

        HashMap sttMap = new HashMap();
        int pktssz=genericPacketLst.size();
        orderbyconQ=orderbyconQ.equals("sk1") ? "" : orderbyconQ;
        String prvGrpby="";
        
        if(!orderbyconQ.equals("sk1") && !orderbyconQ.equals("")){
        allpktListMap=(HashMap)sortByComparatorMap(allpktListMap,orderbyconQ);
        genericPacketLst=util.getKeyInArrayList(allpktListMap);
        }
        for (int i=0;i<pktssz;i++) {
            HashMap pktPrpMap= (HashMap)allpktListMap.get(genericPacketLst.get(i));
                boolean validpkt=true;
                if(!key.equals("")){
                for (int p = 0; p < gridcommLstsz; p++) {
                String fldval=util.nvl((String)pktPrpMap.get(gridcommLst.get(p)));
                if(Arrays.asList(param).indexOf(fldval) <=-1){
                validpkt=false;
                break;
                }
                }    
                if(!row.equals("ALL") && validpkt){ 
                    String fldval=util.nvl((String)pktPrpMap.get(gridrowLst.get(0)));
                    if(!row.equals(fldval))
                    validpkt=false;
                }
                if(!col.equals("ALL") && validpkt){ 
                    String fldval=util.nvl((String)pktPrpMap.get(gridcolLst.get(0)));
                    if(!col.equals(fldval))
                    validpkt=false;
                }
                }
            if(validpkt){
                String grpby = util.nvl((String)pktPrpMap.get(orderbyconQ));
                if(prvGrpby.equals(""))
                    prvGrpby=grpby;
                if(!prvGrpby.equals(grpby)){
                grpOrderList.add(prvGrpby);
                dataDtl.put(prvGrpby, grpbypktList);
                grpbypktList = new ArrayList();
                prvGrpby=grpby;
                }
                String fnl_usd=util.nvl((String)pktPrpMap.get("fnl_usd"));
                String quot=util.nvl((String)pktPrpMap.get("quot"));
                String rap_rte=util.nvl((String)pktPrpMap.get("rap_rte"));
                String cts=util.nvl((String)pktPrpMap.get("cts"));
                String cmp=util.nvl((String)pktPrpMap.get("cmp"));
                pktPrpMap.put("Rate", fnl_usd);
                pktPrpMap.put("amt", util.calculateDisVlu(rap_rte,cts,quot,"VLU"));
                pktPrpMap.put("rteamt", util.calculateDisVlu(rap_rte,cts,cmp,"VLU"));
                pktPrpMap.put("RateAmt", util.calculateDisVlu(rap_rte,cts,fnl_usd,"VLU"));
                pktPrpMap.put("RateDisc", util.calculateDisVlu(rap_rte,cts,fnl_usd,"DIS"));
                pktPrpMap.put("slback", util.calculateDisVlu(rap_rte,cts,quot,"DIS"));
                pktPrpMap.put("rap_dis", util.calculateDisVlu(rap_rte,cts,cmp,"DIS"));
                pktPrpMap.put("RAP_DIS", util.calculateDisVlu(rap_rte,cts,cmp,"DIS"));
                String compPri="";
                if(!comparewith.equals("cmp")){
                compPri=util.nvl((String)pktPrpMap.get(comparewith),"0");
                if(vwPrpLst.contains("LAB_CHARGES") && vwPrpLst.contains("FA_PRI")){
                compPri=String.valueOf(Double.parseDouble(compPri)+Double.parseDouble(util.nvl((String)pktPrpMap.get("LAB_CHARGES"),"0")));
                pktPrpMap.put("Fnlpri", compPri);
                }
                }else
                compPri=util.nvl((String)pktPrpMap.get("cmp"),"0");
                pktPrpMap.put("diff", util.calculatePlDiffVlu(rap_rte,cts,quot,fnl_usd,compPri,"DIFF"));
                pktPrpMap.put("pl", util.calculatePlDiffVlu(rap_rte,cts,quot,fnl_usd,compPri,"PL"));
                pktPrpMap.put("plper", util.calculatePlDiffVlu(rap_rte,cts,quot,fnl_usd,compPri,"PLPER"));
                pktPrpMap.put("plSalrte", util.calculatePlDiffVlu(rap_rte,cts,quot,fnl_usd,compPri,"PLSALRTE"));
                pktPrpMap.put("plSalrteper", util.calculatePlDiffVlu(rap_rte,cts,quot,fnl_usd,compPri,"PLSALRTEPER"));
                if(vwPrpLst.indexOf("CP_DIS") > -1) 
                pktPrpMap.put("CP_DIS",util.calculateDisVlu(rap_rte,cts,util.nvl((String)pktPrpMap.get("CP")),"DIS"));
                if(vwPrpLst.indexOf("CP_VLU") > -1)
                pktPrpMap.put("CP_VLU",util.calculateDisVlu(rap_rte,cts,util.nvl((String)pktPrpMap.get("CP")),"VLU"));
                if(vwPrpLst.indexOf("NET_PUR_DIS") > -1)
                pktPrpMap.put("NET_PUR_DIS",util.calculateDisVlu(rap_rte,cts,util.nvl((String)pktPrpMap.get("NET_PUR_RTE")),"DIS"));
                if(vwPrpLst.indexOf("NET_PUR_VLU") > -1)
                pktPrpMap.put("NET_PUR_VLU",util.calculateDisVlu(rap_rte,cts,util.nvl((String)pktPrpMap.get("NET_PUR_RTE")),"VLU"));
                if(vwPrpLst.indexOf("MFG_DIS") > -1)
                pktPrpMap.put("MFG_DIS",util.calculateDisVlu(rap_rte,cts,util.nvl((String)pktPrpMap.get("MFG_PRI")),"DIS"));
                if(vwPrpLst.indexOf("MFG_VLU") > -1)
                pktPrpMap.put("MFG_VLU",util.calculateDisVlu(rap_rte,cts,util.nvl((String)pktPrpMap.get("MFG_PRI")),"VLU"));
                if(vwPrpLst.indexOf("CMP_DIS") > -1)
                pktPrpMap.put("CMP_DIS",util.calculateDisVlu(rap_rte,cts,util.nvl((String)pktPrpMap.get("CMP_RTE")),"DIS"));
                if(vwPrpLst.indexOf("CMP_VLU") > -1)
                pktPrpMap.put("CMP_VLU",util.calculateDisVlu(rap_rte,cts,util.nvl((String)pktPrpMap.get("CMP_RTE")),"VLU"));
                grpbypktList.add(pktPrpMap);
                allpktList.add(pktPrpMap);
            }
        }

        grpOrderList.add(prvGrpby);
        dataDtl.put(prvGrpby, grpbypktList);
        
//        for(int i;i<grpOrderList.size();i++){
//            String loopgrp=(String)grpOrderList.get(i);
//            ArrayList localLst=(ArrayList)dataDtl.get(loopgrp);
//            String[] gridParams = {"STT", "SH", "CO", "PU"};
//            HashMap<String, String> colDtl = getKV(req,res,localLst, gridParams,"N","SELECTED","");
//        }

            ArrayList<String> gridParamsLst=new ArrayList();
            String [] gridParams = gridParamsLst.toArray(new String[gridParamsLst.size()]);
            HashMap<String, String> colDtl = util.getKV(req,res,allpktList, gridParams,"N","SELECTED","","","ANLS_VW","GENERIC_REPORT","");
            sttMap.putAll(colDtl);
            
            gridParamsLst=new ArrayList();
            gridParamsLst.add("stt");
            gridParams = gridParamsLst.toArray(new String[gridParamsLst.size()]);
            colDtl = util.getKV(req,res,allpktList, gridParams,"N","SELECTED","","","ANLS_VW","GENERIC_REPORT","");
            sttMap.putAll(colDtl);
            req.setAttribute("sttMap", sttMap); 
        
        
       req.setAttribute("key", key);
       session.setAttribute("pktdataDtl", dataDtl);
       session.setAttribute("grpOrderList", grpOrderList);
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
        rtnPg=initnormal(req,res,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,res,"Analysis Report", "createXL start");
        HashMap dbinfo = info.getDmbsInfoLst();
        String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
        int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        String   imgCol= util.nvl(req.getParameter("imgCol"));
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
         ArrayList imagelistDtl= (info.getImagelistDtl() == null)?new ArrayList():(ArrayList)info.getImagelistDtl();
         HashMap dtls=new HashMap();
        int pktListsz=pktList.size();
            if(imgCol.equals("Y")){
//                        itemHdr.add("DIAMONDIMG");
//                        itemHdr.add("JEWIMG");
//                        itemHdr.add("SRAYIMG");
//                        itemHdr.add("AGSIMG");
//                        itemHdr.add("MRAYIMG");
//                        itemHdr.add("RINGIMG");
//                        itemHdr.add("LIGHTIMG");
//                        itemHdr.add("REFIMG");
//                        itemHdr.add("VIDEOS");
//                        itemHdr.add("CERTIMG");
//                       // itemHdr.add("VIDEO_3D");
                       if(imagelistDtl!=null && imagelistDtl.size() >0){
                       for(int k=0;k<imagelistDtl.size();k++){
                       dtls=new HashMap();
                       dtls=(HashMap)imagelistDtl.get(k);
                       String gtCol=util.nvl((String)dtls.get("GTCOL")).toUpperCase();
                       itemHdr.add(gtCol);
                       }}
            }
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
            String contentTypezip = "application/zip";
            String fileNmzip = "ResultExcel"+util.getToDteTime();
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
        String fileNm = "ResultExcel"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        hwb.write(out);
        out.flush();
        out.close();
        }
            util.updAccessLog(req,res,"Analysis Report", "createXL end");
        return null;
        }
    }
    
    public ActionForward createXLDIFFFMT(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Analysis Report", "createXLDIFFFMT start");
            HashMap dbinfo = info.getDmbsInfoLst();
            String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
                int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        ArrayList pktList = new ArrayList();
        ArrayList ary = new ArrayList();
        ArrayList itemHdr= new ArrayList();
        int count=1;
        String getPktPrp =
                " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val " + " from stk_dtl a, mprp b, rep_prp c " +
                " where a.mprp = b.prp and b.prp = c.prp and c.mdl = ? and grp=1 and a.mstk_idn = ? " +
                " order by c.rnk, c.srt ";
        String  srchQ =  " select stk_idn , pkt_ty,  vnm,to_char(pkt_dte,'dd-mm-yyyy') pkt_dte, stt , qty , cts , srch_id , cmp , rmk , kts_vw kts , byr , emp ,to_char(sl_dte,'dd-mm-yyyy') sl_dte,"+
              "  trunc(decode(rap_rte, 1, null, (quot-cmp)/rap_rte*100), 2)  diff, decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis ,rap_rte, quot , cmnt , (cts*quot) amt,trunc(rap_rte*cts,2) rap_val ";
        String rsltQ = srchQ + " from gt_srch_rslt where 1=1 ";     
        rsltQ+= " order by sk1 , cts ";
          ArrayList outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()) {
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("SrNo", String.valueOf(count++));
                pktPrpMap.put("STT", util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                String mstkIdn = util.nvl(rs.getString("stk_idn"));
                pktPrpMap.put("VNM",vnm);
                pktPrpMap.put("byr",util.nvl(rs.getString("byr"))); 
                pktPrpMap.put("emp",util.nvl(rs.getString("emp"))); 
                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                pktPrpMap.put("RAP_RTE",util.nvl(rs.getString("rap_rte")));
                pktPrpMap.put("Amount",util.nvl(rs.getString("amt")));
                pktPrpMap.put("pkt_dte",util.nvl(rs.getString("pkt_dte")));
                pktPrpMap.put("sl_dte",util.nvl(rs.getString("sl_dte")));
                pktPrpMap.put("issIdn",util.nvl(rs.getString("srch_id")));
                pktPrpMap.put("cmp",util.nvl(rs.getString("cmp")));
                pktPrpMap.put("salerte",util.nvl(rs.getString("quot")));
                pktPrpMap.put("diff",util.nvl(rs.getString("diff")));
                pktPrpMap.put("Remark",util.nvl(rs.getString("rmk")));
                pktPrpMap.put("RapVlu",util.nvl(rs.getString("rap_val")));
                    ary = new ArrayList();
                    ary.add("GRNERIC_FMT_VW");
                    ary.add(mstkIdn);
                       ResultSet rs1 = db.execSql("MEMO_VW", getPktPrp, ary);
                       while (rs1.next()) {
                       String lPrp = util.nvl(rs1.getString("mprp"));
                           String val = util.nvl(rs1.getString("val")) ;
                             if (lPrp.equals("RAP_DIS"))
                                 val = util.nvl(rs.getString("r_dis"));
                             if (lPrp.equals("RAP_RTE"))
                                 val = util.nvl(rs.getString("rap_rte"));
                             if(lPrp.equals("RTE"))
                                 val = util.nvl(rs.getString("cmp"));
                             if(lPrp.equals("KTSVIEW"))
                                 val = util.nvl(rs.getString("kts"));
                             if(lPrp.equals("COMMENT"))
                                 val = util.nvl(rs.getString("cmnt"));
                        
                        pktPrpMap.put(lPrp, val);
                         }
            rs1.close();  
                    pktList.add(pktPrpMap);
        }
        rs.close();
            pst.close();
        ArrayList vwPrpLst =util.getMemoXl("GRNERIC_FMT_VW");
        itemHdr.add("SrNo");
        itemHdr.add("byr");
        itemHdr.add("sl_dte");
        itemHdr.add("VNM");
        itemHdr.add("STT");
        for(int i=0;i<vwPrpLst.size();i++){
         String prp= (String)vwPrpLst.get(i);
         itemHdr.add(prp);
         if(prp.equals("PU")){
         itemHdr.add("salerte");  
         itemHdr.add("Amount");  
         }
         if(prp.equals("RAP_RTE"))
         itemHdr.add("RapVlu"); 
        }
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
        int pktListsz=pktList.size();
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
            String contentTypezip = "application/zip";
            String fileNmzip = "ResultExcel"+util.getToDteTime();
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
        String fileNm = "ResultExcel"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        hwb.write(out);
        out.flush();
        out.close();
        }
            util.updAccessLog(req,res,"Analysis Report", "createXLDIFFFMT end");
        return null;
        }
    }

    

    
    public void gridfmtLst(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList gridfmtLst = new ArrayList();
        try {
       
          ArrayList outLst = db.execSqlLst(" Vw Lst ", "select distinct a.nme,a.idn from  ANLS_GRP a,ANLS_GRP_PRP b\n" + 
                    "where a.nme=b.nme and a.stt='A' and b.stt='A' and a.vld_dte is null and b.vld_dte is null order by a.idn",
                               new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {
                    gridfmtLst.add(rs1.getString("nme"));
                }
                rs1.close();
            pst.close();
                session.setAttribute("gridfmtLst", gridfmtLst);

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    }
    
    

    
    
    public String insertANLS_GRP(HttpServletRequest req, HttpServletResponse res,GenericReportForm udf)throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    String key="";
    ArrayList gridfmttyp=new ArrayList();
    ArrayList commLst=new ArrayList();
    ArrayList rowLst=new ArrayList();
    ArrayList colLst=new ArrayList();
    ArrayList params=new ArrayList();
    gridfmttyp.add("COMM");
    gridfmttyp.add("ROW");
    gridfmttyp.add("COL");
        int ct=0;
    ArrayList gridfmtPrp = (ArrayList)session.getAttribute("gridfmtPrp");
    HashMap dbinfo = info.getDmbsInfoLst();
    for(int i=0;i<gridfmttyp.size();i++){
        String gridtyp=(String)gridfmttyp.get(i);
        for(int j=0;j<gridfmtPrp.size();j++){
            String lprp=(String)gridfmtPrp.get(j);
            String val=util.nvl((String)udf.getValue(gridtyp+"_"+lprp));
        if(!val.equals("")){
        if(gridtyp.equals("COMM"))
            commLst.add(lprp);   
        if(gridtyp.equals("ROW"))
            rowLst.add(lprp);   
        if(gridtyp.equals("COL"))
            colLst.add(lprp);   
        }
    }  
    }
    if(commLst.size()==0){
        commLst.add((String)dbinfo.get("SHAPE")); 
        commLst.add((String)dbinfo.get("SIZE")); 
    }
    if(rowLst.size()==0)
        rowLst.add((String)dbinfo.get("COL")); 
    if(colLst.size()==0)
        colLst.add((String)dbinfo.get("CLR")); 
        
    String comm=commLst.toString();
    String row=rowLst.toString();
    String col=colLst.toString();
    comm=comm.replaceAll("\\[","");
    comm=comm.replaceAll("\\]","");
    comm=comm.replaceAll(",","_").trim();
    comm=comm.replaceAll(" ","").trim();
    row=row.replaceAll("\\[","");
    row=row.replaceAll("\\]","");
    row=row.replaceAll(",","_").trim();
    col=col.replaceAll("\\[","");
    col=col.replaceAll("\\]","");
    col=col.replaceAll(",","_").trim();
    key=comm+"_"+row+"_"+col;
    String insertmainQ="Insert into ANLS_GRP (idn, nme,srt, cmn_cnt, row_cnt , col_cnt , stt , frm_dte) \n" + 
    " select ANLS_GRP_SEQ.nextval ,?,?,?,?,?,?,sysdate from dual";
    params.add(key);
    params.add(String.valueOf(key.length()));
    params.add(String.valueOf(commLst.size()));
    params.add(String.valueOf(rowLst.size()));
    params.add(String.valueOf(colLst.size()));
    params.add("A");
    ct = db.execDirUpd("insert gt",insertmainQ, params);
    if(ct>0){
    String insertsubQ=" Insert into ANLS_GRP_PRP (idn, nme, mprp,srt, typ , stt , frm_dte) \n" + 
    " select BULK_TRANS_IDN_SEQ.nextval ,?,?,?,?,?,sysdate from dual";
        for(int i=0;i<gridfmttyp.size();i++){
            String gridtyp=(String)gridfmttyp.get(i);
            for(int j=0;j<gridfmtPrp.size();j++){
                String lprp=(String)gridfmtPrp.get(j);
            String val=util.nvl((String)udf.getValue(gridtyp+"_"+lprp));
            if(!val.equals("")){
            params=new ArrayList();
            params.add(key);
            params.add(lprp);  
            params.add(String.valueOf(j));
            params.add(gridtyp);  
            params.add("A"); 
            ct = db.execDirUpd("insertsubQ",insertsubQ, params);  
            }
        }  
        }
    }
    return key;
    }
//    public void sttColorCode(HttpServletRequest req){
//        HttpSession session = req.getSession(false);
//        InfoMgr info = (InfoMgr)session.getAttribute("info");
//        DBUtil util = new DBUtil();
//        DBMgr db = new DBMgr();
//        db.setCon(info.getCon());
//        util.setDb(db);
//        util.setInfo(info);
//        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//        util.setLogApplNm(info.getLoApplNm());
//    HashMap sttColorCodeMap = (session.getAttribute("sttColorCodeMap") == null)?new HashMap():(HashMap)session.getAttribute("sttColorCodeMap");
//
//    try {
//    if(sttColorCodeMap.size() == 0) {
//        HashMap dbinfo = info.getDmbsInfoLst();
//        String cnt=util.nvl((String)dbinfo.get("CNT"));
//        String mem_ip=util.nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//        int mem_port=Integer.parseInt(util.nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//        MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//        sttColorCodeMap=(HashMap)mcc.get(cnt+"_sttColorCodeMap");
//        if(sttColorCodeMap==null){
//        sttColorCodeMap=new HashMap();
//    String gtView = "select chr_fr, chr_to , dsc , dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
//    " b.mdl = 'JFLEX' and b.nme_rule = 'GENERIC_REPORT' and a.til_dte is null order by a.srt_fr ";
//    ResultSet rs = db.execSql("gtView", gtView, new ArrayList());
//    while (rs.next()) {
//    sttColorCodeMap.put(util.nvl(rs.getString("dsc")), util.nvl(rs.getString("chr_fr")));
//    }
//        rs.close();
//        Future fo = mcc.delete(cnt+"_sttColorCodeMap");
//        System.out.println("add status:_sttColorCodeMap" + fo.get());
//        fo = mcc.set(cnt+"_sttColorCodeMap", 24*60*60, sttColorCodeMap);
//        System.out.println("add status:_sttColorCodeMap" + fo.get());
//        }
//        mcc.shutdown();
//    session.setAttribute("sttColorCodeMap", sttColorCodeMap);
//    }
//    } catch (SQLException sqle) {
//    // TODO: Add catch code
//    sqle.printStackTrace();
//        }catch(Exception ex){
//         System.out.println( ex.getMessage() );
//        }
//    }
    public void gridstructure(HttpServletRequest req,String gridKey){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    HashMap gridstructure = new HashMap();
    String typold="";
    ArrayList Lst=new ArrayList();
    ArrayList ary=new ArrayList();
    try {
    String gtView = "select b.mprp,b.typ from  ANLS_GRP a,ANLS_GRP_PRP b\n" + 
    "where a.nme=b.nme and a.nme=? and a.stt='A' and b.stt='A' and a.vld_dte is null and b.vld_dte is null order by b.srt";
    ary.add(gridKey);
      ArrayList outLst = db.execSqlLst("gtView", gtView, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
        String typ=util.nvl(rs.getString("typ"));
        if(!typold.equals(typ) || typold.equals("")){
            typold=typ ; 
            Lst=new ArrayList();
        }
        Lst.add(util.nvl(rs.getString("mprp")));
        gridstructure.put(typ,Lst);
    }
    rs.close();
        pst.close();
    session.setAttribute("gridstructure", gridstructure);
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    }
    public ActionForward ageRpt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Analysis Report", "ageRpt start");
            GenericInterface genericInt = new GenericImpl();
        String row="",col="";
              ArrayList gridcommLst=new ArrayList();
              ArrayList gridrowLst=new ArrayList();
              ArrayList gridcolLst=new ArrayList();
              HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
              gridcommLst=(ArrayList)gridstructure.get("COMM");
              gridrowLst=(ArrayList)gridstructure.get("ROW");
              gridcolLst=(ArrayList)gridstructure.get("COL");
              int gridcommLstsz=gridcommLst.size();
              int gridrowLstsz=gridrowLst.size();
              int gridcolLstsz=gridcolLst.size();
    ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "ANLS_VW","ANLS_VW");
    String key = req.getParameter("key");
    ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
    String statusALL= statusLst.toString();
    statusALL = statusALL.replace("[", "");
    statusALL = statusALL.replace("]", "");
    statusALL = statusALL.replaceAll(",", "','");
        String[] param = key.split("@");
              if(key.length() > 0){
              String Check = req.getParameter("row");
                if(Check!=null && !Check.equals("")){
                if(param.length > 0) {
                    row = Check;
                    col = req.getParameter("col");
                }    
              }else{
                  row = param[gridcommLstsz];
                  col = param[gridcommLstsz+gridrowLstsz];
              }
              }  
                String condQ="";
                if(!key.equals("")){
                        for (int i = 0; i < gridcommLstsz; i++) {
                        int index = vwPrpLst.indexOf(gridcommLst.get(i))+1;    
                        String fldprp=util.prpsrtcolumn("prp",index);
                        condQ += " and ( "+fldprp+"= '+"+param[i].trim()+"' or "+fldprp+"= '"+param[i]+"') ";
                        }    
                        if(!row.equals("ALL")){
                        int index = vwPrpLst.indexOf(gridrowLst.get(0))+1;    
                        String fldprp=util.prpsrtcolumn("prp",index);
                        condQ +=" and "+fldprp+" in ('"+row+"') ";
                        }
                        if(!col.equals("ALL")){
                        int index = vwPrpLst.indexOf(gridcolLst.get(0))+1;    
                        String fldprp=util.prpsrtcolumn("prp",index);
                        condQ +=" and "+fldprp+" in ('"+col+"') ";
                        }
                        } 
    String ageQ="select count(*) qty, to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts " +
    ", Round(sum(trunc(cts,2)*cmp)) vlu, PKT_SRT_MON, PKT_DSP_MON, to_char(to_date(pkt_srt_mon, 'rrrrmm'), 'Mon/rr') DSP_MON2 " +
    "from gt_srch_rslt_ana_v where stt = 'MKT' " +
    condQ +
    " group by PKT_SRT_MON, PKT_DSP_MON, to_char(to_date(pkt_srt_mon, 'rrrrmm'), 'Mon/rr') " +
    "order by pkt_srt_mon, pkt_dsp_mon";
    ArrayList ageDtl=new ArrayList();
          ArrayList outLst = db.execSqlLst("Ageing", ageQ,new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    HashMap ageSubDtl=new HashMap();
    ageSubDtl.put("qty", util.nvl(rs.getString("qty")));
    ageSubDtl.put("cts", util.nvl(rs.getString("cts")));
    ageSubDtl.put("vlu", util.nvl(rs.getString("vlu")));
    ageSubDtl.put("PKT_DSP_MON", util.nvl(rs.getString("PKT_DSP_MON")));
    ageSubDtl.put("DSP_MON2", util.nvl(rs.getString("DSP_MON2")));
    ageDtl.add(ageSubDtl);
    }
        rs.close();
            pst.close();
    session.setAttribute("AgeDtl", ageDtl);
            util.updAccessLog(req,res,"Analysis Report", "ageRpt end");
    return am.findForward("ageGen");
        }
    }
    
    
    public ActionForward redirectsave(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        util.updAccessLog(req,res,"Analysis Report", "redirectsave start");
            GenericReportForm udf = (GenericReportForm)af;            
            long lStartTime = new Date().getTime();
            String gridfmt=util.nvl((String)req.getParameter("gridfmt"));
                if(!gridfmt.equals("")){
                    gridstructure(req,gridfmt);
                    SearchRslt(req, res);
                }
            long lEndTime = new Date().getTime();
            long difference = lEndTime - lStartTime;
            req.setAttribute("processtm", util.convertMillis(difference));
        util.updAccessLog(req,res,"Analysis Report", "redirectsave end");
    return am.findForward("srchR");
    }
    }
    public ActionForward createGenXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Analysis Report", "createGenXL start");
    HashMap dbinfo = info.getDmbsInfoLst();
    String cnt = (String)dbinfo.get("CNT");
    ExcelUtil xlUtil = new ExcelUtil();
    xlUtil.init(db, util, info);
    OutputStream out = res.getOutputStream();
    String CONTENT_TYPE = "getServletContext()/vnd-excel";
    String fileNm = "ResultExcel"+util.getToDteTime()+".xls";
    res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
    res.setContentType(CONTENT_TYPE);

    String loop = util.nvl(req.getParameter("loopno"));
    String Span = util.nvl(req.getParameter("colSpan"));
    int spanno=0;
    if(!Span.equals(""))
    spanno=Integer.parseInt(Span);
    if(spanno==0)
    spanno=1;
    // int loopno=Integer.parseInt(loop);
    String qty=util.nvl(req.getParameter("qty"));
    String cts=util.nvl(req.getParameter("cts"));
    String avg=util.nvl(req.getParameter("avg"));
    String rap=util.nvl(req.getParameter("rap"));
    String age=util.nvl(req.getParameter("age"));
    String hit=util.nvl(req.getParameter("hit"));
    String vlu=util.nvl(req.getParameter("vlu"));
    String net_dis="";
    String trf_cmp="";
    if(cnt.equals("hk")){
        net_dis="Y";
        trf_cmp="Y";
        if(!Span.equals("")){
        int spannohk=Integer.parseInt(Span);
        spannohk++;spannohk++;
        Span=String.valueOf(spannohk);
        }
    }
    HSSFWorkbook hwb = xlUtil.getDataGenericXlQuick(req,Span,loop,qty,cts,avg,rap, age, hit,vlu,net_dis,trf_cmp);
    hwb.write(out);
    out.flush();
    out.close();
        util.updAccessLog(req,res,"Analysis Report", "createGenXL end");
    return null;
    }
    }
    public String getconQ(String column,ArrayList Lst,ArrayList vwList){
        String rtnQ="";
        for (int i = 0; i < Lst.size(); i++) {
        int index = vwList.indexOf(Lst.get(i))+1;    
        String fld = column+"_";
        if (index < 10)
        fld += "00" + index;
        else if (index < 100)
        fld += "0" + index;
        rtnQ += ", " + fld;
        }
        rtnQ=rtnQ.replaceFirst("\\,", "");
        return rtnQ;
    }
    
    public ActionForward hitData(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
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
    boolean exists=true;
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    ArrayList ary = new ArrayList();
    StringBuffer sb = new StringBuffer();
    String stkIdn = req.getParameter("stkIdn");
    String dmdSrch = " select mj.idn,form_url_encode(byr.get_nm(mj.nme_idn)) byr,jd.aud_created_by createdby,jd.aud_modified_by modifiedby,form_url_encode(jd.rmk) rmk,To_Char(jd.dte_rtn,'dd-Mon-rrrr') dte_rtn from \n" + 
    "      mjan mj,jandtl jd\n" + 
    "      where \n" + 
    "      mj.idn=jd.idn\n" + 
    "      and mj.typ in('I', 'E', 'WH','K','H','BID','O')\n" + 
    "      and jd.mstk_idn=? order by mj.idn desc";
    ary = new ArrayList();
    ary.add(stkIdn);
      ArrayList outLst = db.execSqlLst("Hit",dmdSrch, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while (rs.next()) {
        exists=false;
    sb.append("<hit>");
    sb.append("<idn>" + util.nvl(rs.getString("idn"),"0") +
    "</idn>");
    sb.append("<byr>" + util.nvl(rs.getString("byr"),"0") +
    "</byr>");
    sb.append("<createdby>" + util.nvl(rs.getString("createdby"),"-") +
    "</createdby>");
    sb.append("<modifiedby>" + util.nvl(rs.getString("modifiedby"),"-") +
    "</modifiedby>");
    sb.append("<rmk>" + util.nvl(rs.getString("rmk"),"-") +
    "</rmk>");
        sb.append("<dtertn>" + util.nvl(rs.getString("dte_rtn"),"-") +
        "</dtertn>");
    sb.append("</hit>");
    }
    rs.close();
        pst.close();
//        if(exists){
//            sb.append("<hit>");
//            sb.append("<idn>0</idn>");
//            sb.append("<byr>-</byr>");
//            sb.append("<createdby>-</createdby>");
//            sb.append("<modifiedby>-</modifiedby>");
//            sb.append("<rmk>-</rmk>");
//            sb.append("</hit>");    
//        }
    res.getWriter().write("<hitss>" + sb.toString() + "</hitss>");
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    } catch (IOException ioe) {
    // TODO: Add catch code
    ioe.printStackTrace();
    }

    return null;
    }
    
    public ActionForward pktDtlStockOpenClose(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
           util.updAccessLog(req,res,"Analysis Report", "loadDtl start");
        GenericReportForm udf = (GenericReportForm)af;
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "ANLS_VW_OPEN_CLOSE","ANLS_VW_OPEN_CLOSE");
        ArrayList pktList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        HashMap dbinfo = info.getDmbsInfoLst();
           
        String dsp_stt=util.nvl((String)req.getParameter("dsp_stt"));
        String filterlprpval=util.nvl((String)req.getParameter("filterlprpval"));
        String filterlprp = util.nvl((String)req.getParameter("filterlprp"));
        ArrayList filterStkIdnListopenclose=(ArrayList)session.getAttribute("filterStkIdnListopenclose");
        HashMap pktListMapopenclose=(HashMap)session.getAttribute("pktListMapopenclose");
        int filterStkIdnListopenclosesz=filterStkIdnListopenclose.size();
        HashMap pktPrpMap = new HashMap();
        itemHdr.add("Sr No");
        itemHdr.add("Status");
        itemHdr.add("Byr");
        itemHdr.add("Byr Country");
        itemHdr.add("Sale Ex");
        itemHdr.add("Packet Code");
        itemHdr.add("Packet Date");
        itemHdr.add("Sale Date");
        int sr=1;
        for (int i = 0; i < vwPrpLst.size(); i++) {
             String prp=util.nvl((String)vwPrpLst.get(i));
             itemHdr.add(prp);
         }
           
           for(int i=0;i<filterStkIdnListopenclosesz;i++){
               String stkIdn=(String)filterStkIdnListopenclose.get(i);
               boolean vldpkt=false;
               pktPrpMap = new HashMap();
               pktPrpMap=(HashMap)pktListMapopenclose.get(stkIdn) ;
               String lprpval=util.nvl((String)pktPrpMap.get(filterlprp));
               String dsp_sttMap=util.nvl((String)pktPrpMap.get("dsp_stt"));
               String sttMap=util.nvl((String)pktPrpMap.get("stt"));
               if((filterlprpval.equals("ALL") || filterlprpval.equals(lprpval)) && (dsp_sttMap.equals(dsp_stt) || sttMap.equals(dsp_stt))){
                   vldpkt=true;
               }
               if(vldpkt){
                   pktPrpMap.put("Sr No", String.valueOf(sr++));
                   pktPrpMap.put("Status", dsp_stt);
                   pktList.add(pktPrpMap);
               }
           }
           
           session.setAttribute("pktList", pktList);
           session.setAttribute("itemHdr",itemHdr);
       util.updAccessLog(req,res,"Analysis Report", "loadDtl end");
        return am.findForward("pktDtl");
       }
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
                rtnPg=util.checkUserPageRights("report/genericReport.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Analysis Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Analysis Report", "init");
            }
            }
            return rtnPg;
            }
    
    public ArrayList getPrc(HttpServletRequest req ,HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = null;
        ArrayList prcList = new ArrayList();
        HashMap prcSttMap = new HashMap();
        String prcSql = "select idn, prc , in_stt , ot_stt , is_stt from mprc where is_stt <> 'NA' and stt = ? order by srt";
        ary = new ArrayList();
        ary.add("A");
      ArrayList outLst = db.execSqlLst("prcSql", prcSql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                String mprcId = rs.getString("idn");
                prcList.add(mprcId);
                prcSttMap.put(mprcId, rs.getString("prc"));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("mprcIdnAll", prcList);
        session.setAttribute("mprcBeanAll", prcSttMap);
        return prcList;
    }
    
    public ActionForward commonBarChart(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          StringBuffer sb = new StringBuffer();
            HashMap mprp = info.getSrchMprp();
            String key = util.nvl((String)req.getParameter("key"));
            String row = util.nvl((String)req.getParameter("row"));
            String col = util.nvl((String)req.getParameter("col"));
            HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
            ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
            HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
            ArrayList gridcommLst=new ArrayList();
            ArrayList gridrowLst=new ArrayList();
            ArrayList gridcolLst=new ArrayList();
            gridcommLst=(ArrayList)gridstructure.get("COMM");
            gridrowLst=(ArrayList)gridstructure.get("ROW");
            gridcolLst=(ArrayList)gridstructure.get("COL");
            HashMap prp = info.getSrchPrp();
            ArrayList rowList = (ArrayList)prp.get(gridrowLst.get(0)+"V");
            ArrayList colList = (ArrayList)prp.get(gridcolLst.get(0)+"V");
            ArrayList rowListGt = (ArrayList)session.getAttribute("rowList");
            ArrayList colListGt  = (ArrayList)session.getAttribute("colList");
            String rowlprpTyp = util.nvl((String)mprp.get(gridrowLst.get(0)+"T"));
            String collprpTyp = util.nvl((String)mprp.get(gridcolLst.get(0)+"T"));
            if(rowlprpTyp.equals("T") || rowlprpTyp.equals("N")){
            rowList=new ArrayList();
            rowList=rowListGt;
            }
            if(collprpTyp.equals("T") || collprpTyp.equals("N")){
            colList=new ArrayList();
            colList=colListGt;
            }
            if(key.indexOf("~") > -1)
            key=key.replaceAll("\\~","+");
            if(!row.equals("")) { 
                String rowV=row ;  
            for(int n=0;n< colList.size();n++){
              String colV = (String)colList.get(n);
              if(colListGt.contains(colV)){
              sb.append("<subTag>");
              sb.append("<attrNme>"+colV+"</attrNme>");
              for(int st=0;st<statusLst.size();st++){
              String stt=(String)statusLst.get(st);   
                String keydflt="@"+rowV+"@"+colV;;
                if(rowV.equals("ALL") && !colV.equals("ALL")){
                keydflt="@"+colV;
                }else if(!rowV.equals("ALL") && colV.equals("ALL")){
                keydflt="@"+rowV;
                }else if(rowV.equals("ALL") && colV.equals("ALL")){
                keydflt="";
                }
              
              String keyQty = key+keydflt+"@"+stt+"@QTY";
              String keyAVG = key+keydflt+"@"+stt+"@AVG";
              String valQty = util.nvl((String)dataDtl.get(keyQty),"0");
              String valAVG = util.nvl((String)dataDtl.get(keyAVG),"0");  
              sb.append("<"+stt+"Qty>"+valQty+"</"+stt+"Qty>");
              sb.append("<"+stt+"Avg>"+valAVG+"</"+stt+"Avg>");   
            }
            sb.append("</subTag>");
            }
            }
            }else{
                for(int m=0;m< rowList.size();m++){
                  String rowV = (String)rowList.get(m);
                  if(rowListGt.contains(rowV)){
                  sb.append("<subTag>");
                  sb.append("<attrNme>"+rowV+"</attrNme>");
                  String colV = col; 
                  for(int st=0;st<statusLst.size();st++){
                  String stt=(String)statusLst.get(st);    
                      String keydflt="@"+rowV+"@"+colV;;
                      if(rowV.equals("ALL") && !colV.equals("ALL")){
                      keydflt="@"+colV;
                      }else if(!rowV.equals("ALL") && colV.equals("ALL")){
                      keydflt="@"+rowV;
                      }else if(rowV.equals("ALL") && colV.equals("ALL")){
                      keydflt="";
                      }
                      
                      String keyQty = key+keydflt+"@"+stt+"@QTY";
                      String keyAVG = key+keydflt+"@"+stt+"@AVG";
                  String valQty = util.nvl((String)dataDtl.get(keyQty),"0");
                  String valAVG = util.nvl((String)dataDtl.get(keyAVG),"0");    
                  sb.append("<"+stt+"Qty>"+valQty+"</"+stt+"Qty>");
                  sb.append("<"+stt+"Avg>"+valAVG+"</"+stt+"Avg>");   
                  }
                  sb.append("</subTag>");
                }
                }
                } 
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
         return null;
        }
    
    public ActionForward commonpieChart(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          String  key = (String)req.getParameter(("key"));
          if(key.indexOf("~") > -1)
          key=key.replaceAll("\\~","+"); 
          String row = util.nvl((String)req.getParameter("row"));
          String col = util.nvl((String)req.getParameter("col"));
          String stt = util.nvl((String)req.getParameter("stt"));
          HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
          HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
          HashMap prp = info.getSrchPrp();
    //          if(prp==null){
    //          util.initPrpSrch();
    //          prp = info.getSrchPrp();
    //          }
            HashMap mprp = info.getSrchMprp();
          ArrayList gridcommLst=new ArrayList();
          ArrayList gridrowLst=new ArrayList();
          ArrayList gridcolLst=new ArrayList();
          gridcommLst=(ArrayList)gridstructure.get("COMM");
          gridrowLst=(ArrayList)gridstructure.get("ROW");
          gridcolLst=(ArrayList)gridstructure.get("COL");
          ArrayList rowList = (ArrayList)prp.get(gridrowLst.get(0)+"V");
          ArrayList colList = (ArrayList)prp.get(gridcolLst.get(0)+"V");
            ArrayList rowListGt = (ArrayList)session.getAttribute("rowList");
            ArrayList colListGt  = (ArrayList)session.getAttribute("colList");
            String rowlprpTyp = util.nvl((String)mprp.get(gridrowLst.get(0)+"T"));
            String collprpTyp = util.nvl((String)mprp.get(gridcolLst.get(0)+"T"));
            if(rowlprpTyp.equals("T") || rowlprpTyp.equals("N")){
            rowList=new ArrayList();
            rowList=rowListGt;
            }
            if(collprpTyp.equals("T") || collprpTyp.equals("N")){
            colList=new ArrayList();
            colList=colListGt;
            }
          StringBuffer sb = new StringBuffer();
          HashMap finalDtl=new HashMap();
          ArrayList finalList=new ArrayList();
            if(!row.equals("")) { 
                String rowV=row ;  
            for(int n=0;n< colList.size();n++){
              String colV = (String)colList.get(n); 
                String keydflt="@"+rowV+"@"+colV;;
                if(rowV.equals("ALL") && !colV.equals("ALL")){
                keydflt="@"+colV;
                }else if(!rowV.equals("ALL") && colV.equals("ALL")){
                keydflt="@"+rowV;
                }else if(rowV.equals("ALL") && colV.equals("ALL")){
                keydflt="";
                }
                
              String keyQty = key+keydflt+"@"+stt+"@QTY";
              String valQty = util.nvl((String)dataDtl.get(keyQty));
              if(!valQty.equals("")) { 
              finalDtl.put(colV,valQty);
              finalList.add(colV);
              } 
            }
            }else{
                for(int m=0;m< rowList.size();m++){
                  String rowV = (String)rowList.get(m);
                  String colV = col;  
                    String keydflt="@"+rowV+"@"+colV;;
                    if(rowV.equals("ALL") && !colV.equals("ALL")){
                    keydflt="@"+colV;
                    }else if(!rowV.equals("ALL") && colV.equals("ALL")){
                    keydflt="@"+rowV;
                    }else if(rowV.equals("ALL") && colV.equals("ALL")){
                    keydflt="";
                    }
                    
                    String keyQty = key+keydflt+"@"+stt+"@QTY";
                  String valQty = util.nvl((String)dataDtl.get(keyQty));
                 
                  if(!valQty.equals("")) { 
                  finalDtl.put(rowV,valQty);
                  finalList.add(rowV);
                  }
                } 
            }
            
            for(int z=0;z<finalList.size();z++){
            String finalkey=util.nvl((String)finalList.get(z));
            String vlu=util.nvl((String)finalDtl.get(finalkey));
            if(!vlu.equals("")){
                sb.append("<subTag>");
                sb.append("<attrNme>"+finalkey+"</attrNme>");
                sb.append("<attrVal>"+vlu+"</attrVal>");
                sb.append("</subTag>");
            }
            }
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");

            return null;
        }
    
    public ActionForward commonBarChartSelection(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          StringBuffer sb = new StringBuffer();
            String key = util.nvl((String)req.getParameter("key"));
            String row = util.nvl((String)req.getParameter("row"));
            String col = util.nvl((String)req.getParameter("col"));
            String typ = util.nvl((String)req.getParameter("typ"));
            HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
            HashMap prp = info.getSrchPrp();
            ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
            HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
            ArrayList vwPrpLst = (ArrayList)session.getAttribute("ANLS_VW");
            ArrayList gridcommLst=new ArrayList();
            ArrayList gridrowLst=new ArrayList();
            ArrayList gridcolLst=new ArrayList();
            HashMap mprp = info.getSrchMprp();
            gridcommLst=(ArrayList)gridstructure.get("COMM");
            gridrowLst=(ArrayList)gridstructure.get("ROW");
            gridcolLst=(ArrayList)gridstructure.get("COL");
            ArrayList rowList = (ArrayList)prp.get(gridrowLst.get(0)+"V");
            ArrayList colList = (ArrayList)prp.get(gridcolLst.get(0)+"V");
            ArrayList rowListGt = (ArrayList)session.getAttribute("rowList");
            ArrayList colListGt  = (ArrayList)session.getAttribute("colList");
            String colPrp = util.prpsrtcolumn("prp",vwPrpLst.indexOf(gridcolLst.get(0))+1);
            String rowPrp = util.prpsrtcolumn("prp",vwPrpLst.indexOf(gridrowLst.get(0))+1);
            String rowlprpTyp = util.nvl((String)mprp.get(gridrowLst.get(0)+"T"));
            String collprpTyp = util.nvl((String)mprp.get(gridcolLst.get(0)+"T"));
            if(rowlprpTyp.equals("T") || rowlprpTyp.equals("N")){
            rowList=new ArrayList();
            rowList=rowListGt;
            }
            if(collprpTyp.equals("T") || collprpTyp.equals("N")){
            colList=new ArrayList();
            colList=colListGt;
            }
            int gridcommLstsz=gridcommLst.size();
            String condQ="";
            if(key.indexOf("~") > -1)
            key=key.replaceAll("\\~","+");
            String[] param = key.split("@");
            for (int i = 0; i < gridcommLstsz; i++) {
            int index = vwPrpLst.indexOf(gridcommLst.get(i))+1;    
            String fldprp=util.prpsrtcolumn("prp",index);
            condQ += " and ( "+fldprp+"= '+"+param[i].trim()+"' or "+fldprp+"= '"+param[i]+"') ";
            }
    //            if(!row.equals("ALL")){
    //            int index = vwPrpLst.indexOf(gridrowLst.get(0))+1;
    //            String fldprp=util.prpsrtcolumn("prp",index);
    //            condQ +=" and "+fldprp+" in ('"+row+"') ";
    //            }
    //            if(!col.equals("ALL")){
    //            int index = vwPrpLst.indexOf(gridcolLst.get(0))+1;
    //            String fldprp=util.prpsrtcolumn("prp",index);
    //            condQ +=" and "+fldprp+" in ('"+col+"') ";
    //            }
            
            String commprpQ=getconQ("prp",gridcommLst,vwPrpLst);
            String[] rowparam=null;
               String[] colparam=null;
               ArrayList rowListSelected = new ArrayList();
               ArrayList colListSelected  = new ArrayList();
               if(row.equals("ALL")){
               rowListSelected.addAll(rowListGt);
               rowListSelected.remove("ALL");
               rowListSelected.remove("ALL");
               }
               if(col.equals("ALL")){
               colListSelected.addAll(colListGt);
               colListSelected.remove("ALL");
               colListSelected.remove("ALL");
               }
               if(!row.equals("") && !row.equals("ALL")){
              rowparam = row.split(",");
              for(int k=0;k< rowparam.length;k++){
              String rowp=rowparam[k];
              rowListSelected.add(rowp);
              }
              }
               if(!col.equals("") && !col.equals("ALL")){
              colparam = col.split(",");
              for(int k=0;k< colparam.length;k++){
              String colp=colparam[k];
              colListSelected.add(colp);
              }}
              if(typ.equals("COL")) { 
              String rowListItm = rowListSelected.toString();
              rowListItm = rowListItm.replaceAll("\\[","");
              rowListItm = rowListItm.replaceAll("\\]","");
              rowListItm=util.getVnm(rowListItm);
            for(int n=0;n< colList.size();n++){
                String colV = (String)colList.get(n);
                if(colListSelected.contains(colV)){
                int index = vwPrpLst.indexOf(gridcolLst.get(0))+1;    
                String fldprp=util.prpsrtcolumn("prp",index);
                String subcondQ =" and "+fldprp+" in ('"+colV+"') ";
                int indexcol = vwPrpLst.indexOf(gridrowLst.get(0))+1;    
                String fldprpcol=util.prpsrtcolumn("prp",indexcol);
                subcondQ +=" and "+fldprpcol+" in ("+rowListItm+") ";
                sb.append("<subTag>");
                sb.append("<attrNme>"+colV+"</attrNme>");
                for(int st=0;st<statusLst.size();st++){
                String stt=(String)statusLst.get(st);
                String getColTotal="select sum(qty) qty ,trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),0) avg,"+
                "stt,"+commprpQ+
                " from gt_srch_rslt where 1 = 1 " +condQ+ " and stt = '"+stt+"'"+subcondQ+" group by "+commprpQ+",stt" ;
                  ResultSet rs = db.execSql("getColTotal", getColTotal, new ArrayList());
                  if(rs.next()){
                  sb.append("<"+stt+"Qty>"+rs.getString("qty")+"</"+stt+"Qty>");
                  sb.append("<"+stt+"Avg>"+rs.getString("avg")+"</"+stt+"Avg>");  
                  }else{
                  sb.append("<"+stt+"Qty>0.0</"+stt+"Qty>");
                  sb.append("<"+stt+"Avg>0.0</"+stt+"Avg>");    
                  }
                  rs.close();
              }
                  sb.append("</subTag>");
              }
              }
              }else{
                  String colListItm = colListSelected.toString();
                  colListItm = colListItm.replaceAll("\\[","");
                  colListItm = colListItm.replaceAll("\\]","");
                  colListItm=util.getVnm(colListItm);
                  for(int n=0;n< rowList.size();n++){
                  String rowV = (String)rowList.get(n);
                  if(rowListSelected.contains(rowV)){
                  int index = vwPrpLst.indexOf(gridcolLst.get(0))+1;    
                  String fldprp=util.prpsrtcolumn("prp",index);
                  String subcondQ =" and "+fldprp+" in ("+colListItm+") ";
                  int indexcol = vwPrpLst.indexOf(gridrowLst.get(0))+1;    
                  String fldprpcol=util.prpsrtcolumn("prp",indexcol);
                  subcondQ +=" and "+fldprpcol+" in ('"+rowV+"') ";
                  sb.append("<subTag>");
                  sb.append("<attrNme>"+rowV+"</attrNme>");
                  for(int st=0;st<statusLst.size();st++){
                  String stt=(String)statusLst.get(st);     
                 String getrowTotal="select sum(qty) qty ,trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),0) avg,"+
                 "stt,"+commprpQ+
                 " from gt_srch_rslt where 1 = 1 " +condQ+ " and stt = '"+stt+"'"+subcondQ+" group by "+commprpQ+",stt" ;
                  ResultSet rs = db.execSql("getrowTotal", getrowTotal, new ArrayList());
                      if(rs.next()){
                      sb.append("<"+stt+"Qty>"+rs.getString("qty")+"</"+stt+"Qty>");
                      sb.append("<"+stt+"Avg>"+rs.getString("avg")+"</"+stt+"Avg>");  
                      }else{
                      sb.append("<"+stt+"Qty>0.0</"+stt+"Qty>");
                      sb.append("<"+stt+"Avg>0.0</"+stt+"Avg>");    
                      }
                          rs.close();
                      }
                      sb.append("</subTag>"); 
                }
               } 
              }
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");
         return null;
        }
    
    public ActionForward commonpieselection(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
          String  key = (String)req.getParameter(("key"));
          if(key.indexOf("~") > -1)
          key=key.replaceAll("\\~","+"); 
          String row = util.nvl((String)req.getParameter("row"));
          String col = util.nvl((String)req.getParameter("col"));
          String stt = util.nvl((String)req.getParameter("stt"));
            String  typ = util.nvl((String)req.getParameter(("typ")));
            HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
          ArrayList rowList = (ArrayList)session.getAttribute("rowList");
          ArrayList colList  = (ArrayList)session.getAttribute("colList");
          HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
          String[] rowparam=null;
          String[] colparam=null;
          ArrayList rowListSelected = new ArrayList();
          ArrayList colListSelected  = new ArrayList();
          HashMap prp = info.getSrchPrp();
          HashMap finalDtl=new HashMap();
    //          if(prp==null){
    //          util.initPrpSrch();
    //          prp = info.getSrchPrp();
    //          }
          ArrayList gridcommLst=new ArrayList();
          ArrayList gridrowLst=new ArrayList();
          ArrayList gridcolLst=new ArrayList();
            HashMap mprp = info.getSrchMprp();
          gridcommLst=(ArrayList)gridstructure.get("COMM");
          gridrowLst=(ArrayList)gridstructure.get("ROW");
          gridcolLst=(ArrayList)gridstructure.get("COL");
          ArrayList rowListPrp = (ArrayList)prp.get(gridrowLst.get(0)+"V");
          ArrayList colListPrp = (ArrayList)prp.get(gridcolLst.get(0)+"V");
            String rowlprpTyp = util.nvl((String)mprp.get(gridrowLst.get(0)+"T"));
            String collprpTyp = util.nvl((String)mprp.get(gridcolLst.get(0)+"T"));
            if(rowlprpTyp.equals("T") || rowlprpTyp.equals("N")){
            rowListPrp=new ArrayList();
            rowListPrp=rowList;
            }
            if(collprpTyp.equals("T") || collprpTyp.equals("N")){
            colListPrp=new ArrayList();
            colListPrp=colList;
            }
          StringBuffer sb = new StringBuffer();
            if(row.equals("ALL")){
            rowListSelected=rowList;
            rowListSelected.remove("ALL");
            rowListSelected.remove("ALL");
            }
            if(col.equals("ALL")){
            colListSelected=colList;
            colListSelected.remove("ALL");
            colListSelected.remove("ALL");
            }
            if(!row.equals("") && !row.equals("ALL")){
            rowparam = row.split(",");
            for(int k=0;k< rowparam.length;k++){
            String rowp=rowparam[k];
            rowListSelected.add(rowp);
            }
            }
            if(!col.equals("") && !col.equals("ALL")){
            colparam = col.split(",");
            for(int k=0;k< colparam.length;k++){
            String colp=colparam[k];
            colListSelected.add(colp);
            }}
            if(typ.equals("COL")) { 
            for(int m=0;m< rowListSelected.size();m++){
            String rowV = (String)rowListSelected.get(m);
            for(int n=0;n< colListSelected.size();n++){
              String colV = (String)colListSelected.get(n);  
                String keydflt="@"+rowV+"@"+colV;;
                if(rowV.equals("ALL") && !colV.equals("ALL")){
                keydflt="@"+colV;
                }else if(!rowV.equals("ALL") && colV.equals("ALL")){
                keydflt="@"+rowV;
                }else if(rowV.equals("ALL") && colV.equals("ALL")){
                keydflt="";
                }
                
                String keyQty = key+keydflt+"@"+stt+"@QTY";
              String valQty = util.nvl((String)dataDtl.get(keyQty));
                  if(!valQty.equals("")) { 
                  finalDtl.put(rowV+"@"+colV,valQty);
                  } 
            }}
            }else{
                for(int m=0;m< rowListSelected.size();m++){
                  String rowV = (String)rowListSelected.get(m);                
                  for(int n=0;n< colListSelected.size();n++){
                  String colV = (String)colListSelected.get(n);   
                      String keydflt="@"+rowV+"@"+colV;;
                      if(rowV.equals("ALL") && !colV.equals("ALL")){
                      keydflt="@"+colV;
                      }else if(!rowV.equals("ALL") && colV.equals("ALL")){
                      keydflt="@"+rowV;
                      }else if(rowV.equals("ALL") && colV.equals("ALL")){
                      keydflt="";
                      }
                      
                      String keyQty = key+keydflt+"@"+stt+"@QTY";
                  String valQty = util.nvl((String)dataDtl.get(keyQty));
                 
                  if(!valQty.equals("")) { 
                  finalDtl.put(rowV+"@"+colV,valQty);
                  }}
                } 
            }
                if(colListSelected.size()>0 && colListSelected!=null){
                if(typ.equals("CLR")){
                for(int i=0;i<rowListPrp.size();i++){
                String rowkey=util.nvl((String)rowListPrp.get(i));
                if(rowListSelected.contains(rowkey)){
                String Valuettl="0";
                for(int j=0;j<colListSelected.size();j++){    
                String colkey=util.nvl((String)colListSelected.get(j));
                String Value=util.nvl((String)finalDtl.get(rowkey+"@"+colkey));
                if(!Value.equals("")){
                    Valuettl=String.valueOf(Integer.parseInt(Valuettl)+Integer.parseInt(Value));
                }
                }
                    if(!Valuettl.equals("0") && !Valuettl.equals("")){
                    sb.append("<subTag>");
                    sb.append("<attrNme>"+rowkey+"</attrNme>");
                    sb.append("<attrVal>"+Valuettl+"</attrVal>");
                    sb.append("</subTag>");
                    }
                }
                }
                }else{
                 for(int i=0;i<colListPrp.size();i++){
                 String colkey=util.nvl((String)colListPrp.get(i));
                 if(colListSelected.contains(colkey)){
                 String Valuettl="0";
                 for(int j=0;j<rowListSelected.size();j++){    
                 String rowkey=util.nvl((String)rowListSelected.get(j));
                 String Value=util.nvl((String)finalDtl.get(rowkey+"@"+colkey));
                 if(!Value.equals("")){
                     Valuettl=String.valueOf(Integer.parseInt(Valuettl)+Integer.parseInt(Value));
                 }
                 }
                 if(!Valuettl.equals("0") && !Valuettl.equals("")){
                 sb.append("<subTag>");
                 sb.append("<attrNme>"+colkey+"</attrNme>");
                 sb.append("<attrVal>"+Valuettl+"</attrVal>");
                 sb.append("</subTag>");
                 }   
                 }
                }
                }
                }
            res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");

            return null;
        }
    public String initnormal(HttpServletRequest req , HttpServletResponse res,HttpSession session,DBUtil util) {
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
                
            }
            }
            return rtnPg;
            }
}
