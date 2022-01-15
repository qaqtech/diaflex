package ft.com.lab;

import ft.com.CsvUtil;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.assort.AssortReturnForm;

import ft.com.dao.GtPktDtl;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;

import ft.com.report.DailyApproveReportForm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.io.PrintWriter;

import java.net.InetSocketAddress;

import java.sql.CallableStatement;
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
import java.sql.Connection;

import java.sql.PreparedStatement;

import java.util.Set;

import java.util.concurrent.Future;

import net.spy.memcached.MemcachedClient;

public class LabResultAction extends DispatchAction{

    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.getOpenCursorConnection(db,util,info);
        util.updAccessLog(req,res,"Lab Result", "load start");
        LabResultForm labResultForm = (LabResultForm)form;
        LabResultInterface labResultInt=new LabResultImpl();
        GenericInterface genericInt = new GenericImpl();
        labResultForm.resetAll();
        ArrayList labList = labResultInt.getLab(req,res);
        labResultForm.setLabList(labList);
        labResultForm.setValue("labIdn", "GIA");
            ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch"); 
            info.setGncPrpLst(assortSrchList);        
        String sysdate = util.getToDteGiveFrmt("dd-MM-yyyy");
        labResultForm.setValue("frmdte", sysdate);
        labResultForm.setValue("todte", sysdate);
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
              HashMap pageDtl=(HashMap)allPageDtl.get("LAB_RESULT");
              if(pageDtl==null || pageDtl.size()==0){
              pageDtl=new HashMap();
              pageDtl=util.pagedef("LAB_RESULT");
              allPageDtl.put("LAB_RESULT",pageDtl);
              }
              info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Lab Result", "load end");
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
            util.updAccessLog(req,res,"Lab Result", "fetch start");
            LabResultForm labResultForm = (LabResultForm)form;
            LabResultInterface labResultInt=new LabResultImpl();
            GenericInterface genericInt = new GenericImpl();
            ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch"); 
            info.setGncPrpLst(assortSrchList);
        String vnm = util.nvl((String)labResultForm.getValue("vnmLst"));
        String seq = util.nvl((String)labResultForm.getValue("seq"));
        String lab = util.nvl((String)labResultForm.getValue("labIdn"));
        String frmdte = util.nvl((String)labResultForm.getValue("frmdte"));
        String todte = util.nvl((String)labResultForm.getValue("todte"));
        String labStt = util.nvl((String)labResultForm.getValue("labStt"));
        String issueId = util.nvl((String)labResultForm.getValue("issue"));
        String reptTyp = util.nvl((String)labResultForm.getValue("reportTyp"));
        String srv = util.nvl((String)labResultForm.getValue("SVR"));
        
        HashMap params = new HashMap();
        params.put("vnm", vnm);
        params.put("seq", seq);
        params.put("lab", lab);
        params.put("issue", issueId);
        params.put("frmdte", frmdte);
        params.put("todte", todte);
        params.put("labStt",labStt);
        params.put("RPTTYP", reptTyp);
        params.put("SRV", srv);
       HashMap stockList = labResultInt.FetchResult(req,res, params, labResultForm); 
        
      
        labResultForm.setValue("vnmLst", "");
        labResultForm.setValue("seq", seq);
        labResultForm.setValue("lab", lab);
        req.setAttribute("view", "Y");
        req.setAttribute("lab", lab);
        String lstNme = "LABRLT_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
        gtMgr.setValue(lstNme+"_SEL",new ArrayList());
        gtMgr.setValue(lstNme, stockList);
        gtMgr.setValue("lstNmeRLT", lstNme);
//        labresultVaration(req);
        if(stockList.size()>0){          
               HashMap totals = labResultInt.GetTotal(req,res);        
             gtMgr.setValue(lstNme+"_TTL",totals);
         }     
       util.updAccessLog(req,res,"Lab Result", "fetch end");
    return am.findForward("load");
        }
    }
    
    public ActionForward viewRS(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
             util.updAccessLog(req,res,"Lab Result", "viewRS start");
             LabResultForm labResultForm = (LabResultForm)form;
             LabResultInterface labResultInt=new LabResultImpl();
        String mstkIdn = req.getParameter("mstkIdn");
        String lab = util.nvl(req.getParameter("lab"),"GIA");
        HashMap params = new HashMap();
        params.put("mstkIdn", mstkIdn);
        ArrayList stockPrpList = labResultInt.StockView(req,res, params);
        req.setAttribute("StockViewList", stockPrpList);
         req.setAttribute("mstkIdn", mstkIdn);
         req.setAttribute("lab", lab);
             util.updAccessLog(req,res,"Lab Result", "viewRS end");
        return am.findForward("loadView");
         }
     }
    
    public ActionForward assortHis(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
             util.updAccessLog(req,res,"Lab Result", "assortHis start");
             LabResultForm labResultForm = (LabResultForm)form;
             LabResultInterface labResultInt=new LabResultImpl();
        String mstkIdn = req.getParameter("mstkIdn");
        HashMap params = new HashMap();
        params.put("mstkIdn", mstkIdn);
        HashMap assortHisMap = labResultInt.AssortHistory(req, res, params);
        req.setAttribute("assortHisMap", assortHisMap);
        req.setAttribute("mstkIdn", mstkIdn);
             util.updAccessLog(req,res,"Lab Result", "assortHis end");
        return am.findForward("loadHIS");
         }
     }
    
    public ActionForward ProcessHis(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
             util.updAccessLog(req,res,"Lab Result", "assortHis start");
             LabResultForm labResultForm = (LabResultForm)form;
             LabResultInterface labResultInt=new LabResultImpl();
        String mstkIdn = req.getParameter("mstkIdn");
        HashMap params = new HashMap();
        params.put("mstkIdn", mstkIdn);
        HashMap assortHisMap = labResultInt.ProcessHistory(req, res, params);
        req.setAttribute("ProcessHisMap", assortHisMap);
        req.setAttribute("mstkIdn", mstkIdn);
             util.updAccessLog(req,res,"Lab Result", "assortHis end");
        return am.findForward("loadPRC");
         }
     }
    public ActionForward Excel(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Lab Result", "Excel start");
        LabResultForm labResultForm = (LabResultForm)form;
        LabResultInterface labResultInt=new LabResultImpl();
        String btn = util.nvl(req.getParameter("btn"));
        String lab = util.nvl(req.getParameter("lab"),"GIA");
        int ct =0;
            GenericInterface genericInt = new GenericImpl();
           genericInt.genericPrprVw(req,res,"LabRSLst","LAB_RS");

        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        ArrayList ary = null;
            String lstNme = (String)gtMgr.getValue("lstNmeRLT");

            HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_rslt", new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        ct = db.execUpd("gtUpdate","update gt_srch_rslt set prp_001 = null", new ArrayList());
        ct = db.execCall("gt refresh","pkgmkt.gt_refresh",new ArrayList());
            ArrayList stkIdnLst = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
            for(int i=0;i<stkIdnLst.size();i++){
                       String stkIdn = (String)stkIdnLst.get(i);
                       GtPktDtl pktDtl = (GtPktDtl)stockList.get(stkIdn);
                         String gtInsert = "Insert into gt_srch_rslt(srch_id, pkt_ty, stk_idn, vnm, qty, cts,  stt, cmp,quot, rap_rte,pkt_dte, cert_lab, cert_no, flg, sk1,kts_vw,cmnt)"+
                                           "select ?,?,?,?,?,?,?,?,?,?,trunc(to_date(?, 'dd-mm-rrrr')),?,?,?,?,?,? from dual";
                           ary = new ArrayList();
                           ary.add(pktDtl.getValue("iss_idn"));
                           ary.add("NR");
                           ary.add(stkIdn);
                           ary.add(pktDtl.getValue("vnm"));
                           ary.add(pktDtl.getValue("qty"));
                           ary.add(pktDtl.getValue("cts"));
                           ary.add(pktDtl.getValue("stt"));
                           ary.add(pktDtl.getValue("cmp"));
                           ary.add(pktDtl.getValue("quot"));
                           ary.add(pktDtl.getValue("rap_rte"));
                           ary.add(pktDtl.getValue("pkt_dte"));
                           ary.add(pktDtl.getValue("cert_lab"));
                           ary.add(pktDtl.getValue("cert_no"));
                           ary.add("M");
                           ary.add(pktDtl.getValue("sk1"));
                           ary.add(util.nvl((String)pktDtl.getValue("KTSVIEW")));
                           ary.add(util.nvl((String)pktDtl.getValue("COMMENT")));
                         
                                  
                          ct = db.execUpd("gtInsert", gtInsert, ary);
                       }
            
        
        if(btn.equals("Crexcel") || btn.equals("Crcsv") || btn.equals("CrcsvIGI")){
                String updateGt = "update gt_srch_rslt a  set a.img = (select b.txt from iss_rtn_prp b where  a.srch_id=b.iss_id and a.stk_idn=b.iss_stk_idn and b.mprp ='RCHK') where a.flg='M'";
                ary = new ArrayList();
                ct = db.execUpd("update gtSrch", updateGt, ary);
                if( btn.equals("Crexcel")){
                HSSFWorkbook hwb = null;
                String labXl = "LAB_PKG.GEN_LAB_XL(plab => ?)";
                ary = new ArrayList();
                ary.add(lab);
                ct = db.execCall(" Gen lab xl ", labXl, ary);
                hwb =  xlUtil.getInXl("memo", req, "LAB_"+lab);
                
                OutputStream out = res.getOutputStream();
                String CONTENT_TYPE = "getServletContext()/vnd-excel";
                String fileNm = "LabResultExcel"+util.getToDteTime()+".xls";
                res.setContentType(CONTENT_TYPE);
                res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
                hwb.write(out);
                out.flush();
                out.close();
                }else if( btn.equals("Crcsv")){
                    String pktPrp = "pkgmkt.pktPrp(0,?)";
                    ary = new ArrayList();
                    ary.add("LAB_GIA_CSV");
                    int ct1 = db.execCall(" Srch Prp ", pktPrp, ary);
                    String fileName = "LabResultCSV_"+util.getToDteTime()+".csv";    
                    ArrayList csvLst=csvDataLst(req,res);
                    PrintWriter out = res.getWriter();
                    res.setContentType("application/ms-excel");
                    res.setHeader("Content-disposition","attachment;filename="+fileName);
                    int row=csvLst.size();
                    if(csvLst!=null && csvLst.size()>0){
                    try{    
                    for(int m=0; m < row; m++){
                    String ln ="";
                    ln=(String)csvLst.get(m);
                    out.println(ln);  
                    }
                 
                    out.flush();
                    out.close();    
                    }catch (Exception e) {
                    }
                    }
                }else{
                    String pktPrp = "pkgmkt.pktPrp(0,?)";
                    ary = new ArrayList();
                    ary.add("LAB_IGI_CSV");
                    int ct1 = db.execCall(" Srch Prp ", pktPrp, ary);
                    String fileName = "LabResultCSV_"+util.getToDteTime()+".csv";    
                    ArrayList csvLst=csvDataLstIGI(req,res);
                    PrintWriter out = res.getWriter();
                    res.setContentType("application/ms-excel");
                    res.setHeader("Content-disposition","attachment;filename="+fileName);
                    int row=csvLst.size();
                    if(csvLst!=null && csvLst.size()>0){
                    try{    
                    for(int m=0; m < row; m++){
                    String ln ="";
                    ln=(String)csvLst.get(m);
                    out.println(ln);  
                    }
                 
                    out.flush();
                    out.close();    
                    }catch (Exception e) {
                    }
                    }
                }
        }
        if(btn.equals("createXL")){

        String creExcelPRp ="Lab_pkg.GEN_LABDTLXL(MDL=>?)";
        ary = new ArrayList();
        ary.add("LAB_DTL_XL");
        // ary.add("-1");
        ct = db.execCall("create Excel", creExcelPRp, ary);
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "Excel"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        HSSFWorkbook hwb = null;
        
        hwb = xlUtil.getInXl("memo", req, "LAB_DTL_XL");
        
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
        }

        if(btn.equals("labExcel")){
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("LAB_RS");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        HSSFWorkbook hwb = xlUtil.GetLabComparisionExcel(req);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "LabResultComparision"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        hwb.write(out);
        out.flush();
        out.close();
        }
        if(btn.equals("multiCrexcel")){
            String mutiPRp ="Lab_pkg.Multi_Comp( pMdl=> ? ,  pGrpLmt=> ?)";
            ary = new ArrayList();
            ary.add("LAB_COM_XL");
            ary.add("-1");
            ct = db.execCall("Multi Comp", mutiPRp, ary);
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "LabMutipleCompExcel"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
            HSSFWorkbook hwb = null;
           
            hwb =  xlUtil.getInXl("memo", req, "LAB_COM_XL");
        
            OutputStream out = res.getOutputStream();
            hwb.write(out);
            out.flush();
            out.close();
        }
        if(btn.equals("assortLabCom")){
            
            ArrayList pktDtl = labResultInt.pktList(req, res,"M");
            
            
            HashMap dbinfo = info.getDmbsInfoLst();
            String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N");
            int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
            HSSFWorkbook hwb = null;
            ArrayList itemHdr = (ArrayList)req.getAttribute("ItemHdr");
            hwb =  xlUtil.getGenXl(itemHdr, pktDtl);
            int pktListsz=pktDtl.size();
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
            String contentTypezip = "application/zip";
            String fileNmzip = "AssotLabCompExcel"+util.getToDteTime();
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
                String CONTENT_TYPE = "getServletContext()/vnd-excel";
                String fileNm = "AssotLabCompExcel"+util.getToDteTime()+".xls";
                res.setContentType(CONTENT_TYPE);
                res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
                OutputStream out = res.getOutputStream();
                hwb.write(out);
                out.flush();
                out.close();
            }
        }
            util.updAccessLog(req,res,"Lab Result", "Excel end");
       return null;
        }
    }
    public ActionForward save(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
             util.updAccessLog(req,res,"Lab Result", "save start");
             LabResultForm labResultForm = (LabResultForm)form;
        ResultSet rs =null;
        ArrayList ary  = null;
        ArrayList errorList = new ArrayList();
         HashMap dbinfo = info.getDmbsInfoLst();
         String clnt = (String)dbinfo.get("CNT");
        int ct=0;
         boolean isError = false;
      
         String labIdn = util.nvl((String)labResultForm.getValue("lab"));
       
             String lstNme = (String)gtMgr.getValue("lstNmeRLT");
               HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme);
             ArrayList stockIdnLst = new ArrayList();
             Set<String> keys = stockListMap.keySet();
                     for(String key: keys){
                    stockIdnLst.add(key);
                     }
            HashMap srchRcObList = (HashMap)gtMgr.getValue("srchRIMap");
            if(srchRcObList==null)
                srchRcObList = new HashMap();
            int rcPrcIdn = util.getProcess("LB_RI");
            int empIdn = 0;
            ary=new ArrayList();
             String labNme="LAB-"+labIdn;
            String empIdqry="select nme_idn from nme_v where upper(nme) = upper('"+labNme+"')";
             ArrayList outLst = db.execSqlLst("empId", empIdqry, ary);
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
            if(rs.next()) {
             empIdn=rs.getInt(1);
            }
             rs.close(); pst.close();
           ary = new ArrayList();
           ary.add(Integer.toString(rcPrcIdn));
           ary.add(Integer.toString(empIdn));
           ArrayList out = new ArrayList();
           out.add("I");
           CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", ary ,out);
            int rcIssueIdn = cst.getInt(3);
           cst.close();
           cst=null;
            String msg="";
            for(int i=0 ; i < stockIdnLst.size();i++){
               
                 String stkIdn = (String)stockIdnLst.get(i);
                GtPktDtl stockPkt = (GtPktDtl)stockListMap.get(stkIdn);
                String isChecked = util.nvl((String)labResultForm.getValue(stkIdn));
                boolean isChange = false;
               if(isChecked.equals("yes")){
                 String vnm = stockPkt.getValue("vnm");
                 String stt = util.nvl(stockPkt.getValue("stt"));
                 String isCheckedStt = util.nvl((String)labResultForm.getValue("stt_"+stkIdn));
                 ary = new ArrayList();
                 ary.add(stkIdn);
                 int issIdn =0;
                 String getIssIdn = "select ISS_RTN_PKG.LST_ISS_ID(?) issIdn from dual";

             outLst = db.execSqlLst("issIdn", getIssIdn, ary);
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
                 if(rs.next())
                 issIdn = rs.getInt(1);
                rs.close(); pst.close();
               if(isCheckedStt.equals("LB_CF") || isCheckedStt.equals("LB_CFRS")){
                   int cnt =0;
                   String lprp = "SERVICE";
                   String str =  util.nvl((String)labResultForm.getValue("SV_"+stkIdn));
                   if(!str.equals("")){
                   String updiss_rtn_prp = "update iss_rtn_prp set rtn_val = ? where iss_stk_idn=? and iss_id=? and mprp= ?";
                    ary = new ArrayList();
                    ary.add(str);
                    ary.add(stkIdn);
                    ary.add(String.valueOf(issIdn));
                    ary.add(lprp);
                   cnt = db.execUpd("updateIssrtndtl", updiss_rtn_prp ,ary);
                      ary = new ArrayList();
                      ary.add(stkIdn);
                      ary.add(lprp);
                      ary.add(str);
                      ary.add(labIdn);
                      ary.add("T");
                      ary.add("1");
                      String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?, pPrpTyp => ?, pgrp => ? )";
                      ct = db.execCall("stockUpd",stockUpd, ary);
                      
                   isChange = true;
                    }else{
                    errorList.add("Please Select Service Of Packet "+vnm);  
                   }
                }
                if(isCheckedStt.equals("LB_RI")){
                    int cnt = 0;
                    String str = util.nvl((String)srchRcObList.get(stkIdn));
                    if(!str.equals("")){
                    ary = new ArrayList();
                    String  updiss_rtn_prp = "update iss_rtn_prp set txt='"+str+"' where iss_stk_idn=? and iss_id=? and mprp= ?";
                    String lprp="RCHK";
                    String insertiss_rtn_prp = "insert into iss_rtn_prp(iss_rtn_prp_id, txt,iss_stk_idn , iss_id, mprp )" + 
                    "values(seq_iss_rtn_prp.nextval, '"+str+"' ,? ,? , ? )";
                    ary.add(stkIdn);
                    ary.add(String.valueOf(issIdn));
                    ary.add(lprp);
                    cnt = db.execUpd("updateIssrtndtl", updiss_rtn_prp ,ary);
                    if(cnt==0)
                        cnt = db.execUpd("insert Issrtndtl", insertiss_rtn_prp ,ary);
                    
                       String service =  util.nvl((String)labResultForm.getValue("SV_"+stkIdn));
                       ary = new ArrayList();
                       updiss_rtn_prp = "update iss_rtn_prp set rtn_val='"+service+"' where iss_stk_idn=? and iss_id=? and mprp= ?";
                       lprp="SERVICE";
                       insertiss_rtn_prp = "insert into iss_rtn_prp(iss_rtn_prp_id, rtn_val,iss_stk_idn , iss_id, mprp )" + 
                                           " values(seq_iss_rtn_prp.nextval, '"+service+"' ,? ,? , ? )";
                        ary.add(stkIdn);
                        ary.add(String.valueOf(issIdn));
                        ary.add(lprp);
                        cnt = db.execUpd("updateIssrtndtl", updiss_rtn_prp ,ary);
                        if(cnt==0)
                            cnt = db.execUpd("insert Issrtndtl", insertiss_rtn_prp ,ary);
                        ary = new ArrayList();
                      ary.add(stkIdn);
                      ary.add(lprp);
                      ary.add(service);
                      ary.add(labIdn);
                      ary.add("T");
                      ary.add("1");
                      String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?, pPrpTyp => ?, pgrp => ? )";
                      ct = db.execCall("stockUpd",stockUpd, ary);
                    
                    ary = new ArrayList();
                    ary.add(String.valueOf(rcIssueIdn));
                    ary.add(stkIdn);
                    ary.add(String.valueOf(issIdn));
                    ary.add("RI");
                    cnt = db.execCall("lab_rc_ob", "ISS_RTN_PKG.LAB_RC_OB_ISS(pIssId => ?, pStkIdn => ? , pFrmIssId => ? , pStt => ? )", ary);
                    isChange = true;  
                    }else{
                        errorList.add("Please select properties for Recheck of pakets "+vnm);  
                    }
                }
           if(isChange){
            String issStt = isCheckedStt.replace("LB_", "");
            String updteissrtndtl = "update iss_rtn_dtl set stt=? where iss_stk_idn=? and iss_id=?";
            ary = new ArrayList();
            ary.add(issStt);
            ary.add(stkIdn);
            ary.add(String.valueOf(issIdn));
            ct = db.execUpd("updateIssrtndtl", updteissrtndtl,ary);
             
            String updtemstk = "update mstk set stt=? where idn=? ";
            ary = new ArrayList();
            ary.add(isCheckedStt);
            ary.add(stkIdn);
            ct = db.execUpd("updateMstk", updtemstk,ary);
            if(isCheckedStt.equals("LB_CF") || isCheckedStt.equals("LB_CFRS")){
                ary = new ArrayList();
                ary.add(String.valueOf(issIdn));
                ary.add(stkIdn);
               //ct = db.execCall("APPLY_RTN_PRP", "iss_rtn_pkg.APPLY_RTN_PRP(pIssId => ?, pStkIdn => ?, pPrpLst => 'ALL')",ary);
            }
            if(isCheckedStt.equals("LB_CFRS")){
                String updFlgmstk = "update mstk set flg = ? where idn=? ";
                ary = new ArrayList();
                ary.add("LRS");
                ary.add(stkIdn);
                ct = db.execUpd("updMstk", updFlgmstk, ary);
            }
            if(isCheckedStt.equals("LB_RI"))
             msg= vnm+": Stone Reissue to Lab.";
            if(isCheckedStt.equals("LB_CF"))
             msg= vnm+": Stone get confirm.";
            if(isCheckedStt.equals("LB_CFRS"))
             msg= vnm+": Stone get confirm and resend";
            errorList.add(msg);
        
         }}}
                    
         gtMgr.setValue("srchRIMap", new HashMap());
         labResultForm.reset();
         req.setAttribute("errorList", errorList);          
             util.updAccessLog(req,res,"Lab Result", "save end");  
             GtMgrReset(req);
         return load(am,form,req,res);
         }
     }
    
    public ActionForward saveOld(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
             util.updAccessLog(req,res,"Lab Result", "saveOld start");
             LabResultForm labResultForm = (LabResultForm)form;
        ResultSet rs =null;
        ArrayList ary  = null;
        ArrayList errorList = new ArrayList();
        int ct=0;
         boolean isError = false;
         String labIdn = util.nvl((String)labResultForm.getValue("lab"));
        String labExcel = util.nvl((String)labResultForm.getValue("excel"));
         String Crexcel = util.nvl((String)labResultForm.getValue("Crexcel"));
         String comexcel = util.nvl((String)labResultForm.getValue("comexcel"));
         ArrayList stockList = (ArrayList)session.getAttribute("StockList");
        if(!labExcel.equals("") || !Crexcel.equals("") || !comexcel.equals("")){
           ExcelUtil xlUtil = new ExcelUtil();
           xlUtil.init(db, util, info);
            String delQ = " Delete from gt_srch_rslt ";
             ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
            boolean isSelected=false;
            for(int i=0 ; i < stockList.size();i++){
                HashMap stockPkt = (HashMap)stockList.get(i);
                 String stkIdn = (String)stockPkt.get("stk_idn");
                 String vnm = (String)stockPkt.get("vnm");
                 String isChecked = util.nvl((String)labResultForm.getValue(stkIdn));
                if(isChecked.equals("yes")){
                    isSelected = true;
                    ary = new ArrayList();
                    ary.add(stkIdn);
                    int issIdn =0;
                    String getIssIdn = "select ISS_RTN_PKG.LST_ISS_ID(?) issIdn from dual";

                    ArrayList outLst = db.execSqlLst("issIdn", getIssIdn, ary);
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                     if(rs.next())
                     issIdn = rs.getInt(1);
                     rs.close(); pst.close();
                     ary = new ArrayList();
                     ary.add(String.valueOf(issIdn));
                     ary.add(stkIdn);
                     ary.add(vnm);
                     ary.add(util.nvl((String)stockPkt.get("stt")));
                    ary.add(util.nvl((String)stockPkt.get("qty")));
                    ary.add(util.nvl((String)stockPkt.get("cts")));
                    ary.add(util.nvl((String)stockPkt.get("cert_lab")));
                     ct = db.execUpd("insertGt", "Insert into gt_srch_rslt (srch_id , stk_idn , flg , vnm , stt , qty , cts , cert_lab) select ? , ? , 'LC',?,? , ? ,? ,?  from dual ",ary);
                    if(!Crexcel.equals("")){
                        String updateGt = "update gt_srch_rslt  set cmnt = (select txt from iss_rtn_prp where  iss_id=? and iss_stk_idn = ? and mprp ='RCHK') where stk_idn=? ";
                        ary = new ArrayList();
                        ary.add(String.valueOf(issIdn));
                        ary.add(stkIdn);
                        ary.add(stkIdn);
                        ct = db.execUpd("update gtSrch", updateGt, ary);
                    }
                }
            }
            if(!Crexcel.equals("")){
                String pktPrp = "pkgmkt.pktPrp(0,?)";
                ary = new ArrayList();
                ary.add("LAB_GIA");
                ct = db.execCall(" Srch Prp ", pktPrp, ary);
                String labXl = "LAB_PKG.GEN_LAB_XL";
                ary = new ArrayList();
                ct = db.execCall(" Gen lab xl ", labXl, ary);
                HSSFWorkbook hwb = xlUtil.getInXl("LAB", req, "LAB_GIA");
                OutputStream out = res.getOutputStream();
                String CONTENT_TYPE = "getServletContext()/vnd-excel";
                String fileNm = "Excel"+util.getToDteTime()+".xls";
                res.setContentType(CONTENT_TYPE);
                res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
                hwb.write(out);
                out.flush();
                out.close();
            }
            if(!labExcel.equals("")){
            if(isSelected){
            HSSFWorkbook hwb = xlUtil.GetLabComparisionExcel(req);
            OutputStream out = res.getOutputStream();
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "LabComparision"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "inline;attachment;filename=" + fileNm);
            hwb.write(out);
            out.flush();
            out.close();
            }}
            
        }else{
           HashMap srchRcObList = (HashMap)session.getAttribute("srchRIMap");
            int rcPrcIdn = util.getProcess("LB_RC");
            int obPrcIdn = util.getProcess("LB_OB");
            int empIdn = 0;
           ary=new ArrayList();
           ary.add("LAB-"+labIdn);
           String empIdqry="select nme_idn from nme_v where nme = ?";
           ArrayList outLst = db.execSqlLst("empId", empIdqry, ary);
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            if(rs.next()) {
             empIdn=rs.getInt(1);
            }
           rs.close(); pst.close();
               ary = new ArrayList();
               ary.add(Integer.toString(rcPrcIdn));
               ary.add(Integer.toString(empIdn));
               ArrayList out = new ArrayList();
               out.add("I");
               CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", ary ,out);
               int rcIssueIdn = cst.getInt(3);
           
         cst.close();
         cst=null;
               ary = new ArrayList();
               ary.add(Integer.toString(obPrcIdn));
               ary.add(Integer.toString(empIdn));
                out = new ArrayList();
                out.add("I");
                cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", ary ,out);
                int obIssueIdn = cst.getInt(3);
                String msg="";
         cst.close();
         cst=null;
            for(int i=0 ; i < stockList.size();i++){
                 HashMap stockPkt = (HashMap)stockList.get(i);
                 String stkIdn = (String)stockPkt.get("stk_idn");
                 String vnm = (String)stockPkt.get("vnm");
                 String stt = util.nvl((String)stockPkt.get("stt"));
                 String isCheckedStt = util.nvl((String)labResultForm.getValue("stt_"+stkIdn));
                 boolean isRCOBCF = false;
                
                if(!isCheckedStt.equals(stt)){
                ary = new ArrayList();
                ary.add(stkIdn);
                int issIdn =0;
                String getIssIdn = "select ISS_RTN_PKG.LST_ISS_ID(?) issIdn from dual";
                rs = db.execSql("issIdn", getIssIdn, ary);
                if(rs.next())
                issIdn = rs.getInt(1);
                rs.close(); pst.close();
                String str = util.nvl((String)srchRcObList.get(stkIdn));
                ary = new ArrayList();
                String  updiss_rtn_prp = "update iss_rtn_prp set txt='"+str+"' where iss_stk_idn=? and iss_id=? and mprp= ?";

                String lprp="RCHK";
                String insertiss_rtn_prp = "insert into iss_rtn_prp(iss_rtn_prp_id, txt,iss_stk_idn , iss_id, mprp )" + 
                "values(seq_iss_rtn_prp.nextval, '"+str+"' ,? ,? , ? )";
                if(isCheckedStt.equals("LB_CF") || isCheckedStt.equals("LB_CFRS")){
                    lprp = "SERVICE";
                    str =  util.nvl((String)srchRcObList.get("SV_"+stkIdn));
                    updiss_rtn_prp = "update iss_rtn_prp set rtn_val = ? where iss_stk_idn=? and iss_id=? and mprp= ?";
                    isRCOBCF  = true;
                }
                if(!str.equals("")){
                    ary = new ArrayList();
                    ary.add(stkIdn);
                    ary.add(String.valueOf(issIdn));
                    ary.add(lprp);
                    ct = db.execUpd("updateIssrtndtl", updiss_rtn_prp ,ary);
                    if(ct < 1)
                        ct = db.execUpd("insertIssrtndtl", insertiss_rtn_prp ,ary);
                    if(ct > 0)
                        isRCOBCF  = true;
                }
                
                if(isRCOBCF){
                String issStt = isCheckedStt.replace("LB_", "");
                if(stt.equals("LB_RC") || stt.equals("LB_OB")){
                ary = new ArrayList();
                if(isCheckedStt.equals("LB_RC"))
                ary.add(rcIssueIdn) ;
                if(isCheckedStt.equals("LB_OB"))
                 ary.add(String.valueOf(obIssueIdn));
                 ary.add(String.valueOf(issIdn));
                 ary.add(issStt);
                 ct = db.execCall("lab_rc_ob", "ISS_RTN_PKG.LAB_RC_OB_ISS(pIssId => ?, pStkIdn => ? , pFrmIssId => ? , pStt => ? )", ary);
                }
                String updteissrtndtl = "update iss_rtn_dtl set stt=? where iss_stk_idn=? and iss_id=?";
                ary = new ArrayList();
                ary.add(issStt);
                ary.add(stkIdn);
                ary.add(String.valueOf(issIdn));
                ct = db.execUpd("updateIssrtndtl", updteissrtndtl,ary);
                 
                String updtemstk = "update mstk set stt=? where idn=? ";
                ary = new ArrayList();
                ary.add(isCheckedStt);
                ary.add(stkIdn);
                ct = db.execUpd("updateMstk", updtemstk,ary);
                if(isCheckedStt.equals("LB_CF") || isCheckedStt.equals("LB_CFRS")){
                    ary = new ArrayList();
                    ary.add(String.valueOf(issIdn));
                    ary.add(stkIdn);
                    ct = db.execCall("APPLY_RTN_PRP", "iss_rtn_pkg.APPLY_RTN_PRP(pIssId => ?, pStkIdn => ?, pPrpLst => 'ALL')",ary);
                }
                if(isCheckedStt.equals("LB_CFRS")){
                    String updFlgmstk = "update mstk set flg = ? where idn=? ";
                    ary = new ArrayList();
                    ary.add("RHT");
                    ary.add(stkIdn);
                    ct = db.execUpd("updMstk", updFlgmstk, ary);
                }
                
                if(isCheckedStt.equals("LB_RC"))
                    msg= vnm+": Stone issue for recheck.";
                if(isCheckedStt.equals("LB_OB"))
                    msg= vnm+": Stone issue for observation.";
                if(isCheckedStt.equals("LB_CF"))
                    msg= vnm+": Stone get confirm.";
                if(isCheckedStt.equals("LB_CFRS"))
                    msg= vnm+": Stone get confirm and resend";
                 errorList.add(msg);
                }else{
                   isError = true;
                    if(isCheckedStt.equals("LB_RC")){
                        msg = "Please select properties for Recheck of pakets : "+vnm;
                    }
                    if(isCheckedStt.equals("LB_OB")){
                        msg = "Please select properties for observation of pakets : "+vnm;
                    }   
                    errorList.add(msg);
                }
            }
           }
       }
        if(isError){
            req.setAttribute("view", "Y");
       }else{
          session.setAttribute("srchRIMap", new HashMap());
            labResultForm.reset();
       }
         req.setAttribute("errorList", errorList);
             util.updAccessLog(req,res,"Lab Result", "saveOld end");
         return am.findForward("load");
         }
     }
    public ActionForward fetchservice(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Lab Result", "fetchservice start");
            LabResultForm labResultForm = (LabResultForm)af;
            LabResultInterface labResultInt=new LabResultImpl();
        labResultInt.packetData(req, res , labResultForm);
        ArrayList vwPrpLst = labResultInt.LabPrprViw(req,res);
        HashMap pktList =labResultInt.SearchResult(req,res,"'Z'",vwPrpLst);
        req.setAttribute("pktList", pktList);
        req.setAttribute("view","Y");
        HashMap totals = labResultInt.GetTotal(req,res);
        req.setAttribute("totalMap", totals);
            util.updAccessLog(req,res,"Lab Result", "fetchservice end");
    return am.findForward("service");
        }
    }
    public ActionForward saveservice(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Lab Result", "saveservice start");
            LabResultForm labResultForm = (LabResultForm)form;
        ArrayList pktStkIdnList = (ArrayList)session.getAttribute("pktStkIdnList");
        ArrayList ary = new ArrayList();
        boolean msg=false;
        for(int m=0;m< pktStkIdnList.size();m++){ 
        String stkIdn = (String)pktStkIdnList.get(m);
            String chk=util.nvl((String)labResultForm.getValue("check_"+stkIdn));
            if(chk.equals("Y")){
                int issIdn =0;
                String getIssIdn = "select ISS_RTN_PKG.LST_ISS_ID(?) issIdn from dual";
                ary = new ArrayList();
                ary.add(stkIdn);

                ArrayList outLst = db.execSqlLst("issIdn", getIssIdn, ary);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                if(rs.next())
                issIdn = rs.getInt(1);
                rs.close(); pst.close();
                int cnt =0;
                String str =  util.nvl((String)labResultForm.getValue("SV_"+stkIdn));
                if(!str.equals("")){
                String updiss_rtn_prp = "update iss_rtn_prp set rtn_val = ? where iss_stk_idn=? and iss_id=? and mprp= 'SERVICE'";
                ary = new ArrayList();
                ary.add(str);
                ary.add(stkIdn);
                ary.add(String.valueOf(issIdn));
                cnt = db.execUpd("updateIssrtndtl", updiss_rtn_prp ,ary);
            }
                msg=true;
            }
            }
            if(msg)
            req.setAttribute("msg", "Service change Sucessfully");
            labResultForm.resetAll();
            util.updAccessLog(req,res,"Lab Result", "saveservice end");
            return am.findForward("service"); 
        }
    }
    
    public ActionForward pktDtl(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Lab Result", "pktDtl start");
            
            HashMap prpUpDwMap = (HashMap)gtMgr.getValue("PRPUPDWMAP");
             ArrayList pktList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        String sign=util.nvl(req.getParameter("sign"), "+").trim();
        String lprp=util.nvl(req.getParameter("lprp")).trim();
        ArrayList vwPrpLst = (ArrayList)session.getAttribute("LabViewLst");
          int count=1;
          itemHdr.add("SR");
        itemHdr.add("VNM");
        itemHdr.add("STT");
            String dsc="=";
        if(sign.equals("P"))
                dsc="+";
        if(sign.equals("M"))
                dsc="-";
        String lstNme = (String)gtMgr.getValue("lstNmeRLT");
         HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
             itemHdr.addAll(vwPrpLst);       
          ArrayList stkIdnList = (ArrayList)prpUpDwMap.get(lprp+"_"+dsc);
            if(stkIdnList!=null && stkIdnList.size()>0){
                for(int i=0;i<stkIdnList.size();i++){
                    String stkIdn = (String)stkIdnList.get(i);
                    GtPktDtl pktdtl = (GtPktDtl)stockList.get(stkIdn);
                    String stt = util.nvl(pktdtl.getValue("stt"));
                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap.put("STT", stt);  
                    pktPrpMap.put("SR", String.valueOf(count++));  
                    String vnm = util.nvl(pktdtl.getValue("vnm"));   
                    pktPrpMap.put("VNM",vnm);
                    for(int j=0; j < vwPrpLst.size(); j++){
                         String prp = (String)vwPrpLst.get(j);
                          
                         
                        String val = util.nvl(pktdtl.getValue(prp)) ;
                         if (prp.toUpperCase().equals("CRTWT"))
                             val = util.nvl(pktdtl.getValue("cts"));
                         if (prp.toUpperCase().equals("RAP_RTE"))
                             val = util.nvl(pktdtl.getValue("rap_rte"));
                         if(prp.equals("KTSVIEW"))
                             val = util.nvl(pktdtl.getValue("kts"));
                         if(prp.equals("COMMENT"))
                             val = util.nvl(pktdtl.getValue("cmnt"));
                         if(prp.equals("LAB"))
                             val = util.nvl(pktdtl.getValue("cert_lab"));
                        pktPrpMap.put(prp, val);
                     
                     }
                                  
                        pktList.add(pktPrpMap);
                }
            }
            
          
        session.setAttribute("pktList", pktList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Lab Result", "pktDtl end");
        return am.findForward("pktDtl"); 
        }
    }
    
    public ArrayList csvDataLstIGI(HttpServletRequest req,HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap mprp = info.getMprp();
        GenericInterface genericInt = new GenericImpl();
        ArrayList csvLst=new ArrayList();
        ArrayList vwCsvPrpLst = genericInt.genericPrprVw(req, res, "LABCSViewLstIGI", "LAB_IGI_CSV");
        String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
        int vwCsvPrpLstsz=vwCsvPrpLst.size();        
        String jbPrp = (String)vwCsvPrpLst.get(0);
        String cnPrp = (String)vwCsvPrpLst.get(1);
        String svPrp = (String)vwCsvPrpLst.get(2);
        String rnPrp = (String)vwCsvPrpLst.get(3);
        String jbPrpDsc = util.nvl((String)mprp.get(jbPrp+"D"));
        String cnPrpDsc = util.nvl((String)mprp.get(cnPrp+"D"));
        String svPrpDsc = util.nvl((String)mprp.get(svPrp+"D"));
        String rnPrpDsc = util.nvl((String)mprp.get(rnPrp+"D"));
        jbPrp = util.prpsrtcolumn("prp",vwCsvPrpLst.indexOf(jbPrp)+1);
        cnPrp = util.prpsrtcolumn("prp",vwCsvPrpLst.indexOf(cnPrp)+1);
        svPrp = util.prpsrtcolumn("prp",vwCsvPrpLst.indexOf(svPrp)+1);
        rnPrp = util.prpsrtcolumn("prp",vwCsvPrpLst.indexOf(rnPrp)+1);
        
        String ln="";
        ln = jbPrpDsc+ ","+cnPrpDsc+ ",Report Action,"+svPrpDsc+ " Code,Service Comment,Inscription Text,"+rnPrpDsc+",";
        if(vwCsvPrpLstsz==5)
        ln +=   "Packet No,";
        
        csvLst.add(ln);
        String updSr="update gt_srch_rslt set img="+svPrp+" where img is null";
        int upcnt = db.execUpd("gt_srch_rslt", updSr ,new ArrayList());
        upcnt = db.execUpd("gt_srch_rslt", "update gt_srch_rslt gt set "+svPrp+" = (select ser.val from stk_dtl ser where gt.stk_idn = ser.mstk_idn and ser.grp = 1 and ser.mprp = 'SERVICE') where gt.stt in('LB_CF')" ,new ArrayList());
        updSr="update gt_srch_rslt gt set gt.img ='I' where stt in('LB_CFRS')";
        upcnt = db.execUpd("gt_srch_rslt", updSr ,new ArrayList());
        updSr="update gt_srch_rslt gt set gt.img = (select nvl(p.prt2,p.prt1) from prp p where  p.mprp = 'SERVICE' and gt."+svPrp+"= p.val)  where stt in('LB_CF')";
        upcnt = db.execUpd("gt_srch_rslt", updSr ,new ArrayList());
        String sqlQ="select "+jbPrp+" job,"+cnPrp+" control,decode(img, 'Print','Print','PRINT','Print','HOLD','','Recheck') action,"
        +"decode(img, 'Print','','PRINT','','HOLD','',img) service,"+rnPrp+" reportNo , stt,vnm from gt_srch_rslt where flg='M'";

        ArrayList outLst = db.execSqlLst(" Csv Data", sqlQ,new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
        while (rs.next()) {
            ln="";
            String job = util.nvl(rs.getString("job"));
            String stt = util.nvl(rs.getString("stt"));
            String control = util.nvl(rs.getString("control"));
            String action = util.nvl(rs.getString("action"));
            String service =util.nvl(rs.getString("service"));
           service = service.replaceAll(",", " ").trim();
            if(stt.equals("LB_RI") && !action.equals("")){
                action = action.replaceAll(",", " ");
            }
            if(stt.equals("LB_CF") && (cnt.equals("kj") || cnt.equals("sg"))){
              action = "Print";
              service="";
            }
          service = service.replaceAll("Recheck", "Re-");
          service = service.replaceAll("Observer", "FO-");
            String reportNo = util.nvl(rs.getString("reportNo"));
            ln += job+ ",";
            ln += control+ ",";
            ln += action+ ",";
            ln += service+ ",";
            ln += " ,";
            if(service.indexOf("Recheck") > -1 || service.indexOf("Observer") > -1)
            ln += " ,";
            else
            ln += "LASER IT,";
            ln += reportNo+ ",";
            if(vwCsvPrpLstsz==5)
            ln += util.nvl(rs.getString("vnm"))+ ",";
            csvLst.add(ln);
        }
            rs.close(); pst.close();
        }catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return csvLst;
    }
    public ArrayList csvDataLst(HttpServletRequest req,HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap mprp = info.getMprp();
        GenericInterface genericInt = new GenericImpl();
        ArrayList csvLst=new ArrayList();
        ArrayList vwCsvPrpLst = genericInt.genericPrprVw(req, res, "LABCSViewLst", "LAB_GIA_CSV");
        String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
        int vwCsvPrpLstsz=vwCsvPrpLst.size();
//        String jbPrp = util.prpsrtcolumn("prp",vwCsvPrpLst.indexOf("JOB")+1);
//        String cnPrp = util.prpsrtcolumn("prp",vwCsvPrpLst.indexOf("CONTROL_NO")+1);
//        String svPrp = util.prpsrtcolumn("prp",vwCsvPrpLst.indexOf("SERVICE")+1);
//        String rnPrp = util.prpsrtcolumn("prp",vwCsvPrpLst.indexOf("CERT_NO")+1);
//        String jbPrpDsc = util.nvl((String)mprp.get("JOBD"));
//        String cnPrpDsc = util.nvl((String)mprp.get("CONTROL_NOD"));
//        String svPrpDsc = util.nvl((String)mprp.get("SERVICED"));
//        String rnPrpDsc = util.nvl((String)mprp.get("CERT_NOD"));
        
        String jbPrp = (String)vwCsvPrpLst.get(0);
        String cnPrp = (String)vwCsvPrpLst.get(1);
        String svPrp = (String)vwCsvPrpLst.get(2);
        String rnPrp = (String)vwCsvPrpLst.get(3);
        String jbPrpDsc = util.nvl((String)mprp.get(jbPrp+"D"));
        String cnPrpDsc = util.nvl((String)mprp.get(cnPrp+"D"));
        String svPrpDsc = util.nvl((String)mprp.get(svPrp+"D"));
        String rnPrpDsc = util.nvl((String)mprp.get(rnPrp+"D"));
        jbPrp = util.prpsrtcolumn("prp",vwCsvPrpLst.indexOf(jbPrp)+1);
        cnPrp = util.prpsrtcolumn("prp",vwCsvPrpLst.indexOf(cnPrp)+1);
        svPrp = util.prpsrtcolumn("prp",vwCsvPrpLst.indexOf(svPrp)+1);
        rnPrp = util.prpsrtcolumn("prp",vwCsvPrpLst.indexOf(rnPrp)+1);
        
        String ln="";
        ln = jbPrpDsc+ ","+cnPrpDsc+ ",Report Action,"+svPrpDsc+ " Code,Service Comment,Inscription Text,"+rnPrpDsc+",";
        if(vwCsvPrpLstsz==5)
        ln +=   "Packet No,";
        
        csvLst.add(ln);
        String updSr="update gt_srch_rslt set img="+svPrp+" where img is null";
        int upcnt = db.execUpd("gt_srch_rslt", updSr ,new ArrayList());
        upcnt = db.execUpd("gt_srch_rslt", "update gt_srch_rslt gt set "+svPrp+" = (select ser.val from stk_dtl ser where gt.stk_idn = ser.mstk_idn and ser.grp = 1 and ser.mprp = 'SERVICE') where gt.stt in('LB_CF')" ,new ArrayList());
        updSr="update gt_srch_rslt gt set gt.img ='I' where stt in('LB_CFRS')";
        upcnt = db.execUpd("gt_srch_rslt", updSr ,new ArrayList());
        updSr="update gt_srch_rslt gt set gt.img = (select nvl(p.prt2,p.prt1) from prp p where  p.mprp = 'SERVICE' and gt."+svPrp+"= p.val)  where stt in('LB_CF')";
        upcnt = db.execUpd("gt_srch_rslt", updSr ,new ArrayList());
        String sqlQ="select "+jbPrp+" job,"+cnPrp+" control,decode(img, 'Print','Print','PRINT','Print','HOLD','','Recheck') action,"
        +"decode(img, 'Print','','PRINT','','HOLD','',img) service,"+rnPrp+" reportNo , stt,vnm from gt_srch_rslt where flg='M'";

        ArrayList outLst = db.execSqlLst(" Csv Data", sqlQ,new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
        while (rs.next()) {
            ln="";
            String job = util.nvl(rs.getString("job"));
            String stt = util.nvl(rs.getString("stt"));
            String control = util.nvl(rs.getString("control"));
            String action = util.nvl(rs.getString("action"));
            String service =util.nvl(rs.getString("service"));
           service = service.replaceAll(",", " ").trim();
            if(stt.equals("LB_RI") && !action.equals("") && !cnt.equals("dj")){
                action = action.replaceAll(",", " ");
            }
            if(stt.equals("LB_CF") && (cnt.equals("kj") || cnt.equals("sg") || cnt.equals("sbs"))){
              action = "Print";
              service="";
            }
//            if(stt.equals("LB_CF") && cnt.equals("ag")){
//              if(service.equals("DD"))
//              action = "Print";
//              else if(service.equals("DG"))
//              action = "Inscription";
//              else
//              action = util.nvl(rs.getString("service"));
//              service="";
//            }
            service = service.replaceAll("Recheck", "Re-");
            service = service.replaceAll("Observer", "FO-");
            if(service.indexOf("Re- COL") == -1)
            service = service.replaceAll("Re- CO", "Re-COL ");
            if(service.indexOf("Re- CUT") == -1)
            service = service.replaceAll("Re- CT", "Re-CUT ");
            if(service.indexOf("Re- POL") == -1)
            service = service.replaceAll("Re- PO", "Re-POL ");
            if(service.indexOf("Re- SYM") == -1)
            service = service.replaceAll("Re- SY", "Re-SYM ");
            if(service.indexOf("Re- CLA") == -1)
            service = service.replaceAll("Re- PU", "Re-CLA ");
            if(service.indexOf("FO- COL") == -1)
            service = service.replaceAll("FO- CO", "FO-COL ");
            if(service.indexOf("FO- CUT") == -1)
            service = service.replaceAll("FO- CT", "FO-CUT ");
            if(service.indexOf("FO- POL") == -1)
            service = service.replaceAll("FO- PO", "FO-POL ");
            if(service.indexOf("FO- SYM") == -1)
            service = service.replaceAll("FO- SY", "FO-SYM ");
            if(service.indexOf("FO- CLA") == -1)
            service = service.replaceAll("FO- PU", "FO-CLA ");
            String reportNo = util.nvl(rs.getString("reportNo"));
            ln += job+ ",";
            ln += control+ ",";
            ln += action+ ",";
            ln += service+ ",";
            ln += " ,";
            ln += " ,";
            ln += reportNo+ ",";
            if(vwCsvPrpLstsz==5)
            ln += util.nvl(rs.getString("vnm"))+ ",";
            csvLst.add(ln);
        }
            rs.close(); pst.close();
        }catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return csvLst;
    }
    public LabResultAction() {
        super();
    }
    
    public void GtMgrReset(HttpServletRequest req){
          HttpSession session = req.getSession(false);
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
          String lstNme = (String)gtMgr.getValue("lstNmeRLT");
          HashMap gtMgrMap = (HashMap)gtMgr.getValues();
          gtMgrMap.remove(lstNme+"_SEL");
          gtMgrMap.remove(lstNme);
          gtMgrMap.remove("PRPUPDWMAP");
          gtMgrMap.remove("srchRIMap");
          gtMgrMap.remove("lstNmeRLT");
          gtMgrMap.remove("IssueIdnLst");
          gtMgrMap.remove("srchReckObsMap");
          
        }
    
//    public void labresultVaration(HttpServletRequest req){
//        HttpSession session = req.getSession(false);
//        InfoMgr info = (InfoMgr)session.getAttribute("info");
//        DBUtil util = new DBUtil();
//        DBMgr db = new DBMgr();
//        db.setCon(info.getCon());
//        util.setDb(db);
//        util.setInfo(info);
//        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//        util.setLogApplNm(info.getLoApplNm());
//    HashMap labresultVaration = (session.getAttribute("labresultVaration") == null)?new HashMap():(HashMap)session.getAttribute("labresultVaration");
//
//    try {
//    if(labresultVaration.size() == 0) {
//        HashMap dbinfo = info.getDmbsInfoLst();
//        String cnt=util.nvl((String)dbinfo.get("CNT"));
//        String mem_ip=util.nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//        int mem_port=Integer.parseInt(util.nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//        MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//        labresultVaration=(HashMap)mcc.get(cnt+"_rfiddevice");
//        if(labresultVaration==null){
//        labresultVaration=new HashMap();
//    String gtView = "select chr_fr, chr_to , dsc , dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
//    " b.mdl = 'JFLEX' and b.nme_rule = 'LAB_RESULT' and a.til_dte is null order by a.srt_fr ";
//    ResultSet rs = db.execSql("gtView", gtView, new ArrayList());
//    while (rs.next()) {
//    labresultVaration.put(util.nvl(rs.getString("dsc")), util.nvl(rs.getString("chr_fr")));
//    }
//        rs.close(); pst.close();
//        rs.close(); pst.close();
//        Future fo = mcc.delete(cnt+"_labresultVaration");
//        System.out.println("add status:_labresultVaration" + fo.get());
//        fo = mcc.set(cnt+"_labresultVaration", 24*60*60, labresultVaration);
//        System.out.println("add status:_labresultVaration" + fo.get());
//        }
//        mcc.shutdown();
//    session.setAttribute("labresultVaration", labresultVaration);
//    }
//    } catch (SQLException sqle) {
//    // TODO: Add catch code
//    sqle.printStackTrace();
//        }catch(Exception ex){
//         System.out.println( ex.getMessage() );
//        }
//    }
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
                util.updAccessLog(req,res,"Lab Result", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Lab Result", "init");
            }
            }
            return rtnPg;
            }
}
