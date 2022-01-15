package ft.com.marketing;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtilObj;
import ft.com.GtMgr;
import ft.com.InfoMgr;
import ft.com.contact.MailAction;
import ft.com.dao.ByrDao;
import ft.com.dao.UIForms;

import ft.com.masters.UtilityActionNew;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import java.io.*;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
//~--- JDK imports ------------------------------------------------------------

import java.sql.ResultSet;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Queue;
import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class MemoReportAction extends DispatchAction {
    public MemoReportAction() {
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
            util.getOpenCursorConnection(db,util,info);
        MemoReportForm udf = (MemoReportForm)af;
        SearchQuery query=new SearchQuery();
        util.updAccessLog(req,res,"Memo Report ", "Memo Report load");
        ArrayList    ary          = null;
        ArrayList memoPrt      = new ArrayList();
        String    memoPrntOptn = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                                 + "b.mdl = 'MEMO' and b.nme_rule =  'CUST SLIP' and a.til_dte is null order by a.srt_fr ";

         ArrayList outLst = db.execSqlLst("memoPrint", memoPrntOptn, new ArrayList());
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            UIForms memoOpn = new UIForms();

            memoOpn.setFORM_NME(rs.getString("chr_fr"));
            memoOpn.setFORM_TTL(rs.getString("dsc"));
            memoPrt.add(memoOpn);
        }
        rs.close();
        pst.close();
        String memoId = util.nvl((String)session.getAttribute("memoId"));
        if(memoId.equals(""))
            memoId = util.nvl(req.getParameter("memoId"));
        String typ = "";
            memoId = util.getVnm(memoId);
                 if(memoId.indexOf(",")!=-1){
                 String[] memoIdLst = memoId.split(",");
                           memoId=memoIdLst[0];
                    
                }
            memoId=memoId.replaceAll("'", "");
        if (!memoId.equals("")) {
            String sqlVal = "WITH JPndg as (\n" + 
            "select jd.idn, jd.stt \n" + 
            " , sum(decode(m.pkt_ty, 'MIX', jd.qty, 1)) qty \n" + 
            " , sum(decode(m.pkt_ty, 'NR', trunc(jd.cts, 2), jd.cts)) cts\n" + 
            " , sum(decode(m.pkt_ty, 'NR', 0, jd.cts_rtn)) cts_rtn\n" + 
            " , sum(decode(m.pkt_ty, 'NR', 0, jd.cts_sal)) cts_sal\n" + 
            " , sum(decode(m.pkt_ty, 'NR', trunc(jd.cts, 2), jd.cts)*quot) quotVlu\n" + 
            " , sum(decode(m.pkt_ty, 'NR', trunc(jd.cts, 2), jd.cts)*nvl(fnl_sal, quot)) fnlVlu\n" + 
            " , sum(decode(m.pkt_ty, 'NR', trunc(jd.cts, 2), jd.cts)*decode(m.rap_rte, 1, 0, m.rap_rte)) rapVlu\n" + 
            "from mstk m, jandtl jd\n" + 
            "where m.idn = jd.mstk_idn and jd.idn = ? and nvl(jd.cts,0) > 0  \n" + 
            "group by jd.idn, jd.stt\n" + 
            ") \n" + 
            "select j.exh_rte, p.stt, p.qty, p.cts, p.cts_rtn, p.cts_sal\n" + 
            ", p.quotVlu\n" + 
            ", trunc(decode(rapVlu, 0, p.fnlVlu/p.cts, p.fnlVlu/trunc(p.cts, 2)),2) avgRte\n" + 
            ", trunc(decode(rapVlu, 0, p.fnlVlu/p.cts*trunc(p.cts, 2), p.fnlVlu),2) fnlVlu\n" + 
            ", trunc(decode(rapVlu, 0, p.fnlVlu/p.cts*trunc(p.cts, 2)/nvl(j.exh_rte, 1), p.fnlVlu/nvl(j.exh_rte, 1)), 2) usdVlu\n" + 
            ", rapVlu\n" + 
            ", decode(rapVlu, 0, null, trunc((((fnlVlu/nvl(j.exh_rte, 1))*100)/rapVlu) - 100, 2)) avgRapDis,\n" + 
            " j.nmerel_idn , j.vw_mdl , j.typ,byr.get_nm(j.nme_idn) byr \n" + 
            "from jPndg p, mjan j\n" + 
            "where j.idn = p.idn\n" ; 
            
            ary = new ArrayList();
            ary.add(memoId);
           outLst = db.execSqlLst("sqlVlu", sqlVal, ary);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);

            while (rs.next()) {
                udf.setValue("qty", util.nvl(rs.getString("qty")));
                udf.setValue("cts", util.nvl(rs.getString("cts")));
                udf.setValue("vlu", util.nvl(rs.getString("fnlVlu")));
                udf.setValue("rlnId", util.nvl(rs.getString("nmerel_idn")));
                udf.setValue("mdl", util.nvl(rs.getString("vw_mdl")));
                typ = util.nvl(rs.getString("typ"));
                udf.setValue("typ", typ);
                udf.setValue("byr", util.nvl(rs.getString("byr")));
            }
            rs.close();
            pst.close();
            udf.setValue("memoId", memoId);
            
            req.setAttribute("showIcon", "Y");
            req.setAttribute("memoId", memoId);
        }
         
         ArrayList repPrpList = query.getMemoXlGrpFrm(req,res);
            udf.setMemoPrintList(memoPrt);
            udf.setMemoXlList(repPrpList);
//        udf.setValue("memoPrintList", memoPrt);
//        udf.setValue("memoXlList",repPrpList);
        udf.setValue("mdl","MEMO_VW_XL");
        if(typ.equals("Z")){
           udf.setValue("repTyp", "ALL");
        }else{
          udf.setValue("repTyp", "CRT");
        }
           if(info.getVwPrpLst()==null)
           util.initSrch(); 
          int accessidn=util.updAccessLog(req,res,"Memo Report ", "Memo Report load done");
          req.setAttribute("accessidn", String.valueOf(accessidn));;
        return am.findForward("load");
        }
    }
   
    public ActionForward loadfromdaily(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            MemoReportForm udf = (MemoReportForm)af;
            SearchQuery query=new SearchQuery();
    util.updAccessLog(req,res,"Memo Report ", "Memo Report loadfromdaily");
    ArrayList ary = null;
    ArrayList memoPrt = new ArrayList();
    String memoPrntOptn = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
    + "b.mdl = 'MEMO' and b.nme_rule = 'CUST SLIP' and a.til_dte is null order by a.srt_fr ";

    ArrayList outLst = db.execSqlLst("memoPrint", memoPrntOptn, new ArrayList());
     PreparedStatement pst = (PreparedStatement)outLst.get(0);
     ResultSet rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
    UIForms memoOpn = new UIForms();

    memoOpn.setFORM_NME(rs.getString("chr_fr"));
    memoOpn.setFORM_TTL(rs.getString("dsc"));
    memoPrt.add(memoOpn);
    }
        rs.close();
        pst.close();
    String memoId = util.nvl(req.getParameter("memoId"));
    String typ = "";
    String stt= "";
    if (!memoId.equals("")) {
    String sqlVal = "Select count(*) qty, sum(a.cts) cts, to_char(sum(a.cts*nvl(a.fnl_sal, a.quot)),'99999999990.00') vlu , b.nmerel_idn , b.vw_mdl , b.typ , b.stt,byr.get_nm(b.nme_idn) byr " +
    "from jandtl a , mjan b where a.idn = b.idn and a.idn= ? and (b.stt='RT' or b.typ='Z' or b.typ='CR') GROUP BY b.nmerel_idn , b.vw_mdl, b.typ , b.stt,byr.get_nm(b.nme_idn) ";

    ary = new ArrayList();
    ary.add(memoId);
       outLst = db.execSqlLst("sqlVlu", sqlVal, ary);
       pst = (PreparedStatement)outLst.get(0);
       rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
    udf.setValue("qty", util.nvl(rs.getString("qty")));
    udf.setValue("cts", util.nvl(rs.getString("cts")));
    udf.setValue("vlu", util.nvl(rs.getString("vlu")));
    udf.setValue("rlnId", util.nvl(rs.getString("nmerel_idn")));
    udf.setValue("mdl", util.nvl(rs.getString("vw_mdl")));
    typ = util.nvl(rs.getString("typ"));
    stt = util.nvl(rs.getString("stt"));
    udf.setValue("typ", typ);
    udf.setValue("byr", util.nvl(rs.getString("byr")));
    }
        rs.close();
        pst.close();
    udf.setValue("memoId", memoId);

    req.setAttribute("showIcon", "Y");
    req.setAttribute("memoId", memoId);
    }

    ArrayList repPrpList = query.getMemoXlGrp(req,res);
//    udf.setValue("memoPrintList", memoPrt);
    udf.setMemoPrintList(memoPrt);
//    udf.setValue("memoXlList",repPrpList);
    udf.setMemoXlList(repPrpList);
    if(req.getAttribute("mdlUp")!=null)
    udf.setValue("formatVersion", "New");
    else
    udf.setValue("formatVersion", "Exit");

    if(stt.equals("RT")){
    udf.setValue("repTyp", "ALL");
    udf.setValue("memoTyp", "NN");
    }else{
    udf.setValue("repTyp", "CRT");
    udf.setValue("memoTyp", "IS");
    }
    int accessidn=util.updAccessLog(req,res,"Memo Report ", "Memo Report loadfromdaily done");
    req.setAttribute("accessidn", String.valueOf(accessidn));
            finalizeObject(db, util);
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
         util.updAccessLog(req,res,"Memo Report ", "save");
         String CONTENT_TYPE = "getServletContext()/vnd-excel";
         String contentTypezip = "application/zip";
         Boolean excelDnl  = null,
                 mailExcl  = null,
              mailExclPDF   = null;
         HashMap dbinfo = info.getDmbsInfoLst();
             MemoReportForm udf = (MemoReportForm)af;
         String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N");
             int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
             String pdfUrl ="";
         excelDnl = Boolean.valueOf(false);
         mailExcl = Boolean.valueOf(false);
         mailExclPDF = Boolean.valueOf(false);
         ArrayList emailids=new ArrayList();
         if (req.getParameter("mail") != null) 
             mailExcl = Boolean.valueOf(true);
         if (req.getParameter("excel") != null) 
             excelDnl = Boolean.valueOf(true);
      if (req.getParameter("mailExclPdf") != null) {
                 mailExclPDF = Boolean.valueOf(true);
                 String repPath = req.getParameter("repPath");
                 String cnt =req.getParameter("cnt");
                 String reportUrl =req.getParameter("repUrl");
                 String accessidn = req.getParameter("accessidn");
                 String memoPg =(String)udf.getValue("memoPag");
                 String memoId =(String)udf.getValue("memoId");
                 String stt=(String)udf.getValue("repTyp");
                String certY =util.nvl(req.getParameter("P_withoutcertno"),"Y");
                 String priceY = util.nvl(req.getParameter("p_withoutprice"),"Y");
                 String quotY = util.nvl(req.getParameter("p_quot"),"Y");
                 String wiPack = util.nvl(req.getParameter("P_WIthoutPacketcode"),"Y");
                 String p_ttlb = util.nvl(req.getParameter("p_ttlb"),"Y");
                 String p_incl = util.nvl(req.getParameter("p_incl"),"Y");
                 String P_withoutdisc = util.nvl(req.getParameter("P_withoutdisc"),"Y");
                 String p_vol_dis = util.nvl(req.getParameter("p_vol_dis"),"Y");
                 String p_dtl = util.nvl(req.getParameter("p_dtl"),"Y");
                 String p_trm = util.nvl(req.getParameter("p_trm"),"Y");
                 String p_show = util.nvl(req.getParameter("p_show"),"Y");
                    
             pdfUrl = repPath+"/reports/rwservlet?"+cnt+"&report="+reportUrl+"\\reports\\"+memoPg+"&p_id="+memoId+"&P_withoutcertno="+certY+"&p_withoutprice="+priceY+"&P_quot="+quotY+"&P_WIthoutPacketcode="+wiPack+"&P_withoutdisc="+P_withoutdisc+"&p_vol_dis="+p_vol_dis+"&p_trm="+p_trm+"&p_show="+p_show+"&p_dtl="+p_dtl+"&p_incl="+p_incl+"&p_ttlb="+p_ttlb+"&p_stt="+stt+"&p_access="+accessidn; 
                 req.setAttribute("PDF", "Y");
                 req.setAttribute("PDFURL", pdfUrl);
                    
         
         
             }
             SearchQuery query=new SearchQuery();
        ArrayList ary = null;
        String xlFormat = (String)udf.getValue("formatVersion");
        String memoIdn= (String)udf.getValue("memoId");
         String rlnIdn= (String)udf.getValue("rlnId");
         String xlMdl= "";
         xlMdl= util.nvl((String)udf.getValue("mdl"));                      
            
       if (excelDnl || mailExcl ||  mailExclPDF) {
       if(!xlMdl.equals("")){
        String updateMjan = "update mjan set vw_mdl=? where idn=? ";
        ary = new ArrayList();
        ary.add(xlMdl);
        ary.add(memoIdn);
        int ct = db.execUpd("update vw_mdl ", updateMjan , ary);
        }
         String fileNm = "memoInExcel" + memoIdn +"_"+getToDteTime()+".xls";
         String fileNmzip = "memoInExcel" + memoIdn +"_"+getToDteTime();
         String typ = (String)udf.getValue("repTyp");  
         if(typ.equals("CRT")){
             typ="IS";
         }
             int byrIdn = 0;
             String email="";
             String byrIdnSql = "select a.nme_idn , byr.get_nm(a.nme_idn) byrNm,to_char(a.dte,'dd-mm-yyyy') dte,\n" + 
             "  b.cur||' -'||a.aadat_comm||' '||c.term trmsDtl ,\n" + 
             "             byr.get_nm(a.aadat_idn)||','||byr.get_nm(a.mbrk1_idn)||','||byr.get_nm(a.mbrk2_idn)||','||byr.get_nm(a.mbrk3_idn) brk\n" + 
             "             from mjan a ,nmerel  b , mtrms c \n" + 
             "             where a.nmerel_idn=b.nmerel_idn and a.idn=? and b.trms_idn=c.idn";
             ary = new ArrayList();
             ary.add(memoIdn);
          ArrayList  outLst = db.execSqlLst("byIdn", byrIdnSql ,ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet  rs = (ResultSet)outLst.get(1);
             while(rs.next()){
                 byrIdn = rs.getInt("nme_idn");
                 String brk = util.nvl(rs.getString("brk"));
                 brk=brk.replaceAll("None,", "");
              
                 req.setAttribute("BYRNME", rs.getString("byrNm"));
                 req.setAttribute("MEMODATE", rs.getString("dte"));
                 req.setAttribute("BRK", brk);
                 req.setAttribute("TERMS", rs.getString("trmsDtl"));
             }
             rs.close();
             pst.close();
             info.setByrId(byrIdn);
             info.setRlnId(Integer.parseInt(rlnIdn));
          HSSFWorkbook hwb = util.getMemoInXl(memoIdn, rlnIdn,typ,xlMdl, req,res,session);
         if (excelDnl) {
             if(zipallowyn.equals("D")){
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
                 res.setContentType(CONTENT_TYPE);
                 res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
                 OutputStream out = res.getOutputStream();
                 hwb.write(out);
                 out.flush();
                 out.close();
             }
         }

         if (mailExcl || mailExclPDF) {
           
            
             query.MailExcelmass(hwb, fileNm, req,res);
            
           String byrmailid = "select b.txt emailid from mjan a , nme_dtl b where a.nme_idn=b.nme_idn and a.idn=? and b.mprp like 'EMAIL%' and vld_dte is null";
           ary.clear();
           ary.add(memoIdn);
           outLst = db.execSqlLst("Buyer Mail Ids", byrmailid ,ary);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
           while(rs.next()){
               email=util.nvl(rs.getString("emailid"));
               if(!email.equals("NA") && !email.equals("")){
               emailids.add(email);
               }
           }
             rs.close();
             pst.close();
           req.setAttribute("ByrEmailIds",emailids);
           req.setAttribute("load","memoreport");
           HashMap mailDtl = util.getMailFMT("DFLT_TXT_MAIL");
           String dfltmailtxt=util.nvl((String)mailDtl.get("MAILBODY"));
           req.setAttribute("dfltmailtxt", dfltmailtxt);
           return am.findForward("view");
      
         }}
             int accessidn=util.updAccessLog(req,res,"Memo Report ", "end");
             req.setAttribute("accessidn", String.valueOf(accessidn));
             finalizeObject(db, util);
         return load(am, af, req, res);
         }
     }
    
   
    public ActionForward loadFmt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            MemoReportForm udf = (MemoReportForm)af;
        String mdlTyp = req.getParameter("mdl");
        String rep_prpMdl = "select prp from rep_prp where mdl=?";
        ArrayList ary = new ArrayList();
        ary.add(mdlTyp);
          ArrayList  outLst = db.execSqlLst("mdl", rep_prpMdl, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String prp = util.nvl(rs.getString("prp"));
            udf.setValue(prp , prp);
        }
        rs.close();
        pst.close();
        udf.setValue("formatNme", mdlTyp);
        udf.setValue("isUpdate", "YES");
        req.setAttribute("mdlUp", "Y");
            finalizeObject(db, util);
        return load(am, af, req, res);
        }
    }
    
    public ActionForward loadfileinfmt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                UtilityActionNew utility=new UtilityActionNew();
                String nmeIdn=util.nvl((String)req.getParameter("nmeIdn"));
                String memoId = util.nvl((String)req.getParameter("memoIdn"));
                String memoTyp = util.nvl((String)req.getParameter("memoTyp"));
                String buttonTyp = util.nvl((String)req.getParameter("buttonTyp"),"CRT");
                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("UTILITY");
                if(pageDtl==null || pageDtl.size()==0){
                pageDtl=new HashMap();
                pageDtl=util.pagedef("UTILITY");
                allPageDtl.put("UTILITY",pageDtl);
                }
                info.setPageDetails(allPageDtl);
            if(!nmeIdn.equals("")){
                ArrayList ary = new ArrayList();
                String sqlQ = "";
                
                if(buttonTyp.equals("CRT") && memoTyp.indexOf("AP")>-1) {
                       sqlQ = " and a.typ like '%AP' and a.stt = 'AP' ";
                
                } else if(buttonTyp.equals("CRT") && !memoTyp.equals("Z")){
                       sqlQ = " and a.stt = 'IS' ";
                } 
            db.execUpd("gt delete","delete from gt_srch_rslt", new ArrayList());
            String srchRef = "Insert into gt_srch_rslt (rln_idn, srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, prte, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis\n" + 
            ", CERTIMG, DIAMONDIMG, JEWIMG, SRAYIMG, AGSIMG, MRAYIMG, RINGIMG, LIGHTIMG, REFIMG, VIDEOS, VIDEO_3D)\n" + 
            "select b.nmerel_idn , b.idn, c.pkt_ty, c.idn, c.vnm, a.qty, a.cts, c.dte, c.stt, c.fcpr, nvl(a.fnl_sal, a.quot) rte, c.rap_rte, c.cert_lab, c.cert_no, 'Z' flg, c.sk1, nvl(a.fnl_sal, a.quot), decode(c.rap_rte ,'1',null,'0',null, trunc((nvl(a.fnl_sal, a.quot)/greatest(c.rap_rte,1)*100)-100, 2)) \n" + 
            ", c.CERTIMG, c.DIAMONDIMG, c.JEWIMG, c.SRAYIMG, c.AGSIMG, c.MRAYIMG, c.RINGIMG, c.LIGHTIMG, c.REFIMG, c.VIDEOS, c.VIDEO_3D\n" + 
            "from jandtl a , mjan b,mstk c\n" + 
            "where \n" + 
            "a.idn=b.idn and a.mstk_idn=c.idn and a.idn= ? "+sqlQ ;
            ary = new ArrayList();
            ary.add(memoId);  
            int ct = db.execUpd("insert gt", srchRef, ary);
            
            String pktPrp = "DP_POP_GT_MAPPING(?)";
            ary = new ArrayList();
            ary.add(util.nvl(nmeIdn));
            ct = db.execCall(" Srch Prp ", pktPrp, ary);
            return utility.createXLComman(am,af, req, res,nmeIdn);
            }
            return null;
        }
    }
    
    public String getToDteTime() {
        Date date = new Date();
        String DATE_FORMAT = "ddMMMyyyy_kk.mm.ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String toDte = sdf.format(date) ;
        return toDte;
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
                util.updAccessLog(req,res,"Memo Report ", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Memo Report ", "init");
            }
            }
            return rtnPg;
            }
    
    public void finalizeObject(DBMgr db, DBUtil util){
        try {
            db=null;
            util=null;
        } catch (Throwable t) {
            // TODO: Add catch code
            t.printStackTrace();
        }
       
    }
    
}


//~ Formatted by Jindent --- http://www.jindent.com
