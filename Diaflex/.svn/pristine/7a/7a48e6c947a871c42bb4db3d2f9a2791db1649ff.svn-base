package ft.com.masters;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.DynamicPropertyForm;
import ft.com.ExcelUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;

import ft.com.JSONParser;
import ft.com.JspUtil;
import ft.com.PdfforReport;
import ft.com.assort.AssortIssueImpl;
import ft.com.assort.AssortIssueInterface;
import ft.com.contact.MailAction;
import ft.com.dao.JsonDao;
import ft.com.dao.JsonResultDao;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;
import ft.com.dao.ObjBean;
import ft.com.dao.UIForms;
import ft.com.fileupload.FileUploadForm;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.PacketPrintForm;

import ft.com.mpur.PurchaseDtlForm;
import ft.com.pri.PriceDtlMatrixForm;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.io.PrintWriter;

import java.net.MalformedURLException;
import java.net.URL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Map;
import java.util.Vector;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import org.json.JSONException;
import org.json.JSONObject;

public class UtilityActionNew extends DispatchAction {
    public ActionForward load(ActionMapping am, ActionForm af,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = init(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            util.updAccessLog(req, res, "Utility", "load start");
            AtrDtlForm udf = (AtrDtlForm)af;

            HashMap allPageDtl =
                (info.getPageDetails() == null) ? new HashMap() :
                (HashMap)info.getPageDetails();
            HashMap pageDtl = (HashMap)allPageDtl.get("UTILITY");
            if (pageDtl == null || pageDtl.size() == 0) {
                pageDtl = new HashMap();
                pageDtl = util.pagedef("UTILITY");
                allPageDtl.put("UTILITY", pageDtl);
            }
            info.setPageDetails(allPageDtl);
            util.updAccessLog(req, res, "Utility", "load end");
            return am.findForward("load");
        }
    }

    public ActionForward fetch(ActionMapping am, ActionForm af,
                               HttpServletRequest req,
                               HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = init(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            util.updAccessLog(req, res, "Utility", "fetch start");
            AtrDtlForm udf = (AtrDtlForm)af;
            String dpmrgmixpkts =
                util.nvl((String)udf.getValue("dpmrgmixpkts"));
            String applyrap = util.nvl((String)udf.getValue("applyrap"));
            String updaterap = util.nvl((String)udf.getValue("updaterap"));
            String reapplysort = util.nvl((String)udf.getValue("reapplysort"));
            String genmatch = util.nvl((String)udf.getValue("genmatch"));
            String upsrtprt = util.nvl((String)udf.getValue("upsrtprt"));
            String fileblue = util.nvl((String)udf.getValue("fileblue"));
            String genfilerap = util.nvl((String)udf.getValue("genfilerap"));
            String fileCdex = util.nvl((String)udf.getValue("fileCdex"));
            String fileBRNT = util.nvl((String)udf.getValue("filebrillianct"));
            String rapnet = util.nvl((String)udf.getValue("rapnet"));
            String rapnetusa = util.nvl((String)udf.getValue("rapnetusa"));
            String diamondworld =
                util.nvl((String)udf.getValue("diamondworld"));
            String idex = util.nvl((String)udf.getValue("idex"));
            String certdays = util.nvl((String)udf.getValue("certdays"));
            String deliverydata =
                util.nvl((String)udf.getValue("deliverydata"));
            String saledata = util.nvl((String)udf.getValue("saledata"));
            String pricereduction =
                util.nvl((String)udf.getValue("pricereduction"));
            String pricereductionag =
                util.nvl((String)udf.getValue("pricereductionag"));
            String cddpapplycutgrp =
                util.nvl((String)udf.getValue("cddpapplycutgrp"));
            String cddpapplydept =
                util.nvl((String)udf.getValue("cddpapplydept"));
            String cdctg = util.nvl((String)udf.getValue("cdctg"));
            String polygon = util.nvl((String)udf.getValue("polygon"));
            String gemfind = util.nvl((String)udf.getValue("gemfind"));
            String verichannel = util.nvl((String)udf.getValue("verichannel"));
            String hra = util.nvl((String)udf.getValue("hra"));
            String priceAnalies =
                util.nvl((String)udf.getValue("priceAnalies"));
            String fullrapsync = util.nvl((String)udf.getValue("fullrapsync"));
            String sdilinc = util.nvl((String)udf.getValue("sdilinc"));
            String forevermark = util.nvl((String)udf.getValue("forevermark"));
            String genpridtlmatric =
                util.nvl((String)udf.getValue("genpridtlmatric"));
            String fileblueBFLG =
                util.nvl((String)udf.getValue("filebluebflg"));
            String fileritani = util.nvl((String)udf.getValue("fileritani"));
            String fileritanixls =
                util.nvl((String)udf.getValue("fileritanixls"));
            String filebluexls = util.nvl((String)udf.getValue("filebluexls"));
            String fileblueNY = util.nvl((String)udf.getValue("fileblueNY"));
            String dpgenngmemo = util.nvl((String)udf.getValue("dpgenngmemo"));
            String dppktrappri = util.nvl((String)udf.getValue("dppktrappri"));
            String dailyclosingag =
                util.nvl((String)udf.getValue("dailyclosingag"));
            String allwebusersmail =
                util.nvl((String)udf.getValue("allwebusersmail"));
            String dpapplymixpri =
                util.nvl((String)udf.getValue("dpapplymixpri")); /*  */
            String dpcalcMcd = util.nvl((String)udf.getValue("dpcalcMcd"));
            String lotnolistdwn =
                util.nvl((String)udf.getValue("lotnolistdwn"));
            String bpfallocation =
                util.nvl((String)udf.getValue("bpfallocation"));
            String regenerateallhk =
                util.nvl((String)udf.getValue("regenerateallhk"));
            String fileforgemsdiamond =
                util.nvl((String)udf.getValue("fileforgemsdiamond"));
            String nmeIdn = "";
            String fileSrchId = "";
            String days = "0";
            HashMap allPageDtl =
                (info.getPageDetails() == null) ? new HashMap() :
                (HashMap)info.getPageDetails();
            HashMap pageDtl = (HashMap)allPageDtl.get("UTILITY");
            ArrayList pageList = new ArrayList();
            HashMap pageDtlMap = new HashMap();
            String fld_nme = "", fld_ttl = "", val_cond = "", dflt_val =
                "", fld_typ = "", form_nme = "";
            pageList = (ArrayList)pageDtl.get("SUBMIT");
            if (pageList != null && pageList.size() > 0) {
                for (int j = 0; j < pageList.size(); j++) {
                    pageDtlMap = (HashMap)pageList.get(j);
                    fld_nme = (String)pageDtlMap.get("fld_nme");
                    fld_ttl = (String)pageDtlMap.get("fld_ttl");
                    val_cond = (String)pageDtlMap.get("val_cond");
                    dflt_val = (String)pageDtlMap.get("dflt_val");
                    fld_typ = (String)pageDtlMap.get("fld_typ");
                    if (!rapnet.equals("") && fld_nme.equals("rapnet"))
                        nmeIdn = dflt_val;
                    if (!rapnetusa.equals("") && fld_nme.equals("rapnetusa"))
                        nmeIdn = dflt_val;
                    if (!diamondworld.equals("") &&
                        fld_nme.equals("diamondworld"))
                        nmeIdn = dflt_val;
                    if (!idex.equals("") && fld_nme.equals("idex"))
                        nmeIdn = dflt_val;
                    if (!gemfind.equals("") && fld_nme.equals("gemfind"))
                        nmeIdn = dflt_val;
                    if (!polygon.equals("") && fld_nme.equals("polygon"))
                        nmeIdn = dflt_val;
                    if (!verichannel.equals("") &&
                        fld_nme.equals("verichannel"))
                        nmeIdn = dflt_val;
                    if (!forevermark.equals("") &&
                        fld_nme.equals("forevermark"))
                        nmeIdn = dflt_val;
                    if (!hra.equals("") && fld_nme.equals("hra"))
                        nmeIdn = dflt_val;
                    if (!priceAnalies.equals("") &&
                        fld_nme.equals("priceAnalies"))
                        nmeIdn = dflt_val;
                    if (!sdilinc.equals("") && fld_nme.equals("sdilinc"))
                        nmeIdn = dflt_val;
                    if (!fileritanixls.equals("") &&
                        fld_nme.equals("fileritanixls"))
                        nmeIdn = dflt_val;
                    if (!filebluexls.equals("") &&
                        fld_nme.equals("filebluexls"))
                        nmeIdn = dflt_val;
                    if (!dppktrappri.equals("") &&
                        fld_nme.equals("dppktrappri"))
                        days = dflt_val;
                    if (!fileBRNT.equals("") &&
                        fld_nme.equals("filebrillianct"))
                        nmeIdn = dflt_val;
                    if (!fileforgemsdiamond.equals("") &&
                        fld_nme.equals("fileforgemsdiamond"))
                        nmeIdn = dflt_val;
                    if (!nmeIdn.equals("")) {
                        fileSrchId = fld_typ;
                        break;
                    }
                }
            }
            int ct;
            if (!dpcalcMcd.equals("")) {
                ct =
 db.execCallDir("DP_CALC_MSD", "DP_CALC_MSD", new ArrayList());
                req.setAttribute("msg", "MSD Updated");
            }
            if (!applyrap.equals("")) {
                ct =
 db.execCallDir("DP_UPD_RAPNET", "dp_job('DP_UPD_RAPNET', sysdate, null)",
                new ArrayList());
                req.setAttribute("msg", "Apply RAP Change Done");
            }
            if (!updaterap.equals("")) {
                ct =
 db.execCallDir("STK_RAP_UPD", "dp_job('STK_RAP_UPD', sysdate, null)",
                new ArrayList());
                req.setAttribute("msg", "Update RAP on STOCK Done");
            }
            if (!certdays.equals("")) {
                ct =
 db.execCall("refresh vw", "dp_job('DBMS_MVIEW.REFRESH(''STK_CERT_DYS_MV'')', sysdate, null)",
             new ArrayList());
                req.setAttribute("msg", "Update Cert Days");
            }
            if (!fullrapsync.equals("")) {
                ct =
 db.execCall("refresh vw", "dp_job('DP_POP_FULL_RAPSYNC', sysdate, null)",
             new ArrayList());
                req.setAttribute("msg", "Full rapsync started");
            }
            if (!dpmrgmixpkts.equals("")) {
                ct =
 db.execCall("refresh vw", "DP_MERGE_MIX_PKTS", new ArrayList());
                req.setAttribute("msg", "Merge started");
            }
            if (!deliverydata.equals("")) {
                ct =
 db.execCall("refresh vw", "DBMS_MVIEW.REFRESH('DLV_MAX_IDN_PKT_MV')",
             new ArrayList());
                req.setAttribute("msg", "Updated Delivery Data");
            }
            if (!saledata.equals("")) {
                ct =
 db.execCall("refresh vw", "DBMS_MVIEW.REFRESH('SAL_MAX_IDN_PKT_MV')",
             new ArrayList());
                req.setAttribute("msg", "Updated Sale Data");
            }
            if (!reapplysort.equals("")) {
                ct =
 db.execCallDir("STK_SRT", "dp_job('STK_SRT', sysdate, null)",
                new ArrayList());
                req.setAttribute("msg", "Reapply Sort on Stock Done");
            }
            if (!dpgenngmemo.equals("")) {
                ct =
 db.execCallDir("DP_JOB_GEN_NG_MEMO", "dp_job('DP_JOB_GEN_NG_MEMO', sysdate, null)",
                new ArrayList());
                req.setAttribute("msg", "Execution Done Sucessfully");
            }
            if (!genmatch.equals("")) {
                ct =
 db.execCallDir("DP_GEN_ALL_MATCH_PAIRS", "dp_job('DP_GEN_ALL_MATCH_PAIRS', sysdate, null)",
                new ArrayList());
                req.setAttribute("msg", "Generate Match Pairs Done");
            }
            if (!upsrtprt.equals("")) {
                ct =
 db.execCallDir("STK_PRT_SRT_UPD", "dp_job('STK_PRT_SRT_UPD', sysdate, null)",
                new ArrayList());
                req.setAttribute("msg", "Update Sort And Print Done");
            }
            if (!genpridtlmatric.equals("")) {
                ct =
 db.execCallDir("POP_ITM_BSE_PRI_SHAPES", "dp_job('POP_ITM_BSE_PRI_SHAPES', sysdate, null)",
                new ArrayList());
                req.setAttribute("msg", "Generate Price Detail Matrix Done");
            }
            if (!pricereduction.equals("")) {
                ct =
 db.execCall("DP_PRICE_REDUCTION", "dp_job('DP_PRICE_REDUCTION', sysdate, null)",
             new ArrayList());
                req.setAttribute("msg", "Price Reduction Done");
            }

            if (!dailyclosingag.equals("")) {
                ct =
 db.execCall("DP_GEN_ADJ_MEMO", "dp_job('DP_GEN_ADJ_MEMO', sysdate, null)",
             new ArrayList());
                req.setAttribute("msg", "Daily Closeing Done");
            }
            if (!pricereductionag.equals("")) {
                ct =
 db.execCall("DLY_PRICE_REDUCTION", "dp_job('DLY_PRICE_REDUCTION', sysdate, null)",
             new ArrayList());
                req.setAttribute("msg", "Price Reduction Done");
            }
            if (!cddpapplycutgrp.equals("")) {
                ct =
 db.execCallDir("DP_APPLY_CUT_GRP", "dp_job('DP_APPLY_CUT_GRP', sysdate, null)",
                new ArrayList());
                req.setAttribute("msg", "Applied Cut Grp");
            }
            if (!cddpapplydept.equals("")) {
                ct =
 db.execCallDir("DP_APPLY_DEPT", "dp_job('DP_APPLY_DEPT', sysdate, null)",
                new ArrayList());
                req.setAttribute("msg", "Applied Dept");
            }
            if (!bpfallocation.equals("")) {
                ct =
 db.execCallDir("AUTO_ALC_PKG", "dp_job('AUTO_ALC_PKG.ALC_PRC(pTyp => ''PP'')', sysdate, null)",
                new ArrayList());
                req.setAttribute("msg", "Applied Applocation");
            }
            if (!regenerateallhk.equals("")) {
                ct =
 db.execCallDir("POP_ITM_BSE_PRI", "dp_job('POP_ITM_BSE_PRI')', sysdate, null)",
                new ArrayList());
                req.setAttribute("msg", "Applied Applocation");
            }
            if (!cdctg.equals("")) {
                ct =
 db.execCallDir("DP_APPLY_CTG", "dp_job('DP_APPLY_CTG', sysdate, null)",
                new ArrayList());
                req.setAttribute("msg", "Applied Ctg");
            }
            if (!dpapplymixpri.equals("")) {
                ct =
 db.execCallDir("DP_APPLY_MIX_PRI", "dp_job('DP_APPLY_MIX_PRI', sysdate, null)",
                new ArrayList());
                req.setAttribute("msg",
                                 "Applied Mix Price Sheet on live Packets");
            }

            if (!allwebusersmail.equals("")) {
                allwebusersmail(am, af, req, res);
                req.setAttribute("msg", "Mail Send Sucessfully");
            }
            if (!dppktrappri.equals("")) {
                String procQ =
                    "DP_PKT_RAP_PRI(Pdys => " + util.nvl(days, "0") + ")";
                ct =
 db.execCallDir("DP_PKT_RAP_PRI", "dp_job('" + procQ + "', sysdate, null)",
                new ArrayList());
                req.setAttribute("msg", "Generate Rap Price Sucessfully");
            }
            if (!fileBRNT.equals("") || !fileforgemsdiamond.equals("")) {
                ArrayList ary = new ArrayList();
                ary.add(fileSrchId);
                ary.add(nmeIdn);
                ary.add("Y");
                ct =
 db.execCall("GEN_FILE", "GEN_FILE(pSrchId => ? , pNmeIdn => ? , pDp =>?)",
             ary);
                req.setAttribute("nmeIDN", nmeIdn);
                genfileALBBNT(am, af, req, res);

                return null;
            }
            if (!fileblue.equals("")) {
                ct =
 db.execCall("GEN_FILE_BN", "GEN_FILE_BN", new ArrayList());
                genfileBN(am, af, req, res);

                return null;
            }
            if (!fileblueBFLG.equals("")) {
                ct =
 db.execCall("GEN_FILE_BN", "GEN_FILE_BN('N')", new ArrayList());
                genfileRNBN(am, af, req, res, "B");

                return null;
            }
            if (!fileritani.equals("")) {
                ct =
 db.execCall("GEN_FILE_BN", "GEN_FILE_BN('N')", new ArrayList());
                genfileRNBN(am, af, req, res, "R");

                return null;
            }
            if (!fileblueNY.equals("")) {
                ct =
 db.execCall("GEN_FILE_BN", "GEN_FILE_BN('Y')", new ArrayList());
                genfileRNBN(am, af, req, res, "B");

                return null;
            }
            if (!lotnolistdwn.equals("")) {
                return genfilelotnolistdwn(am, af, req, res);
            }
            if (!fileCdex.equals("")) {
                ct =
 db.execCall("GEN_FILE_BN", "GEN_FILE_CDEX", new ArrayList());
                genfileCdex(am, af, req, res);

                return null;
            }
            if (!genfilerap.equals("")) {
                ct =
 db.execCall("GEN_FILE_RAP", "GEN_FILE_RAP", new ArrayList());
                req.setAttribute("msg", "Download Rap File");
            }
            if (!rapnet.equals("") || !rapnetusa.equals("") ||
                !diamondworld.equals("") || !idex.equals("") ||
                !gemfind.equals("") || !polygon.equals("") ||
                !verichannel.equals("") || !forevermark.equals("") ||
                !priceAnalies.equals("") || !hra.equals("") ||
                !sdilinc.equals("") || !fileritanixls.equals("") ||
                !filebluexls.equals("")) {
                ArrayList ary = new ArrayList();
                db.execUpd("gt delete", "delete from gt_srch_rslt",
                           new ArrayList());
                String genProc = "GEN_FILE_BN";
                ary = new ArrayList();
                if (!fileSrchId.equals("")) {
                    genProc = "GEN_FILE_BN(pSrchId =>?)";
                    ary.add(fileSrchId);
                }
                ct = db.execCall("GEN_FILE_RAP", genProc, ary);
                String pktPrp = "DP_POP_GT_MAPPING(?)";
                ary = new ArrayList();
                ary.add(util.nvl(nmeIdn));
                ct = db.execCall(" Srch Prp ", pktPrp, ary);
                if (!verichannel.equals("") || !forevermark.equals("") ||
                    !gemfind.equals(""))
                    return genfileComman(am, af, req, res, nmeIdn);
                else
                    return createXLComman(am, af, req, res, nmeIdn);
            }
            util.updAccessLog(req, res, "Utility", "fetch end");
            return am.findForward("load");
        }
    }

    public ActionForward genfilelotnolistdwn(ActionMapping am, ActionForm form,
                                             HttpServletRequest req,
                                             HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = init(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            util.updAccessLog(req, res, "Analysis Report",
                              "createXLDIFFFMT start");
            HashMap dbinfo = info.getDmbsInfoLst();
            String zipallowyn =
                util.nvl((String)dbinfo.get("ZIP_DWD_YN"), "N");
            int zipdwnsz =
                Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),
                                          "100"));
            ExcelUtil xlUtil = new ExcelUtil();
            xlUtil.init(db, util, info);
            ArrayList pktList = new ArrayList();
            ArrayList itemHdr = new ArrayList();
            int count = 1;
            String rsltQ = "select distinct dsc from mlot order by 1";
            ResultSet rs = db.execSql("search Result", rsltQ, new ArrayList());
            while (rs.next()) {
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("SrNo", String.valueOf(count++));
                pktPrpMap.put("LOTNO", util.nvl(rs.getString("dsc")));
                pktList.add(pktPrpMap);
            }
            rs.close();
            itemHdr.add("SrNo");
            itemHdr.add("LOTNO");
            HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            int pktListsz = pktList.size();
            if (zipallowyn.equals("Y") && pktListsz > zipdwnsz) {
                String contentTypezip = "application/zip";
                String fileNmzip = "LotNOResultExcel" + util.getToDteTime();
                OutputStream outstm = res.getOutputStream();
                ZipOutputStream zipOut = new ZipOutputStream(outstm);
                ZipEntry entry = new ZipEntry(fileNmzip + ".xls");
                zipOut.putNextEntry(entry);
                res.setHeader("Content-Disposition",
                              "attachment; filename=" + fileNmzip + ".zip");
                res.setContentType(contentTypezip);
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
               hwb.write(bos);
               bos.writeTo(zipOut);      
              zipOut.closeEntry();
               zipOut.flush();
               zipOut.close();
               outstm.flush();
               outstm.close(); 
            } else {
                OutputStream out = res.getOutputStream();
                String CONTENT_TYPE = "getServletContext()/vnd-excel";
                String fileNm = "ResultExcel" + util.getToDteTime() + ".xls";
                res.setContentType(CONTENT_TYPE);
                res.setHeader("Content-Disposition",
                              "attachment;filename=" + fileNm);
                hwb.write(out);
                out.flush();
                out.close();
            }
            util.updAccessLog(req, res, "Analysis Report",
                              "createXLDIFFFMT end");
            return null;
        }
    }

    public ActionForward createXLComman(ActionMapping am, ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        String nmeIdn) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = init(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            HashMap dbinfo = info.getDmbsInfoLst();
            String zipallowyn =
                util.nvl((String)dbinfo.get("ZIP_DWD_YN"), "N");
            int zipdwnsz =
                Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),
                                          "100"));
            ExcelUtil xlUtil = new ExcelUtil();
            xlUtil.init(db, util, info);
            OutputStream out = res.getOutputStream();
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String contentTypezip = "application/zip";
            res.setContentType(CONTENT_TYPE);
            String fileNm = "";
            HashMap allPageDtl =
                (info.getPageDetails() == null) ? new HashMap() :
                (HashMap)info.getPageDetails();
            HashMap pageDtl = (HashMap)allPageDtl.get("UTILITY");
            ArrayList pageList = new ArrayList();
            HashMap pageDtlMap = new HashMap();
            String fld_nme = "", fld_ttl = "", val_cond = "", dflt_val =
                "", lov_qry = "", fld_typ = "", form_nme = "";
            pageList = (ArrayList)pageDtl.get("SUBMIT");
            if (pageList != null && pageList.size() > 0) {
                for (int j = 0; j < pageList.size(); j++) {
                    pageDtlMap = (HashMap)pageList.get(j);
                    fld_nme = (String)pageDtlMap.get("fld_nme");
                    fld_ttl = (String)pageDtlMap.get("fld_ttl");
                    val_cond = (String)pageDtlMap.get("val_cond");
                    lov_qry = (String)pageDtlMap.get("lov_qry");
                    dflt_val = (String)pageDtlMap.get("dflt_val");
                    if (dflt_val.equals(nmeIdn)) {
                        fileNm = lov_qry;
                        break;
                    }
                }
            }

            fileNm = fileNm + util.getToDteTime() + ".xls";
            res.setHeader("Content-Disposition",
                          "attachment;filename=" + fileNm);
            stockList(req, nmeIdn);
            ArrayList pktList = (ArrayList)session.getAttribute("pktList");
            ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
            HashMap prpDsc = new HashMap();
            try {

                ArrayList ary = new ArrayList();
                ary.add(util.nvl(nmeIdn));
                String prpqry =
                    "select idx mprp,nvl(w3,idx) dsc, rank() over (order by srt) rnk from prp_map_web  where name_id = ? and mprp = 'DFCOL'";
                ResultSet rs1 = db.execSql(" Vw Lst ", prpqry, ary);
                while (rs1.next()) {
                    prpDsc.put(util.nvl(rs1.getString("mprp")),
                               util.nvl(rs1.getString("dsc")));
                }
                rs1.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
            HSSFWorkbook hwb =
                xlUtil.getGenVenderFileXl(itemHdr, pktList, prpDsc);
            int pktListsz = pktList.size();
            if (zipallowyn.equals("Y") && pktListsz > zipdwnsz) {
                String fileNmzip = fileNm.substring(0, fileNm.indexOf(".xls"));
                OutputStream outstm = res.getOutputStream();
                ZipOutputStream zipOut = new ZipOutputStream(outstm);
                ZipEntry entry = new ZipEntry(fileNmzip + ".xls");
                zipOut.putNextEntry(entry);
                res.setHeader("Content-Disposition",
                              "attachment; filename=" + fileNmzip + ".zip");
                res.setContentType(contentTypezip);
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
               hwb.write(bos);
               bos.writeTo(zipOut);      
              zipOut.closeEntry();
               zipOut.flush();
               zipOut.close();
               outstm.flush();
               outstm.close(); 
            } else {
                hwb.write(out);
                out.flush();
                out.close();
            }
            return null;
        }
    }

    public void stockList(HttpServletRequest req, String nmeIdn) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());
        db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap allPageDtl =
            (info.getPageDetails() == null) ? new HashMap() : (HashMap)info.getPageDetails();
        HashMap pageDtl = (HashMap)allPageDtl.get("UTILITY");
        ArrayList pageList = new ArrayList();
        HashMap pageDtlMap = new HashMap();
        String fld_nme = "", fld_ttl = "", val_cond = "", dflt_val =
            "", lov_qry = "", fld_typ = "", form_nme = "";
        ArrayList pktList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        ArrayList vwPrpLst = EXPORTCommanFmtvw(req, nmeIdn);
        String diamongImgpath = "";
        String certImgpath = "";
        int sr = 1;
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = util.nvl((String)dbinfo.get("CNT"));
        ArrayList imagelistDtl =
            ((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),
                                                 info.getMem_port(),
                                                 info.getCnt() +
                                                 "_ImaageDtls") == null) ?
            new ArrayList() :
            (ArrayList)new JspUtil().getFromMem(info.getMem_ip(),
                                                info.getMem_port(),
                                                info.getCnt() + "_ImaageDtls");
        if (imagelistDtl != null && imagelistDtl.size() > 0) {
            for (int j = 0; j < imagelistDtl.size(); j++) {
                HashMap dtls = (HashMap)imagelistDtl.get(j);
                String path = util.nvl((String)dtls.get("PATH"));
                String gtCol = util.nvl((String)dtls.get("GTCOL"));
                if (gtCol.equals("diamondImg"))
                    diamongImgpath = path;
                if (gtCol.equals("certImg"))
                    certImgpath = path;
            }
        }
        String srchQ =
            " select stk_idn , pkt_ty,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  rap_dis  , rap_rte,quot , cert_no,cert_lab , vnm, pkt_dte, stt ,dsp_stt, qty ,to_char(trunc(cts,2) * quot, '9999999990.00') amt,to_char(trunc(cts,2) * rap_rte, '9999999990.00') rapamt, to_char(trunc(cts,2),'9990.99') cts ,cmnt, rmk ,CERTIMG, DIAMONDIMG ";
        for (int i = 0; i < vwPrpLst.size(); i++) {
            String lprp = (String)vwPrpLst.get(i);
            String fld = "prp_";
            int j = i + 1;
            if (j < 10)
                fld += "00" + j;
            else if (j < 100)
                fld += "0" + j;
            else if (j > 100)
                fld += j;


            srchQ += ", " + fld;

            pageList = (ArrayList)pageDtl.get(nmeIdn + "_" + lprp + "_PRV");
            if (pageList != null) {
                for (int p = 0; p < pageList.size(); p++) {
                    pageDtlMap = (HashMap)pageList.get(p);
                    fld_ttl = (String)pageDtlMap.get("fld_ttl");
                    itemHdr.add(fld_ttl);
                }
            }

            itemHdr.add(lprp);

            pageList = (ArrayList)pageDtl.get(nmeIdn + "_" + lprp);
            if (pageList != null) {
                for (int p = 0; p < pageList.size(); p++) {
                    pageDtlMap = (HashMap)pageList.get(p);
                    fld_ttl = (String)pageDtlMap.get("fld_ttl");
                    itemHdr.add(fld_ttl);
                }
            }
        }

        String rsltQ =
            srchQ + " from gt_srch_rslt where cts <> 0 order by sk1 , cts ";

        ResultSet rs = db.execSql("search Result", rsltQ, new ArrayList());
        try {
            while (rs.next()) {

                String stkIdn = util.nvl(rs.getString("stk_idn"));

                HashMap pktPrpMap = new HashMap();
                String vnm = util.nvl(rs.getString("vnm"));
                String cert_no = util.nvl(rs.getString("cert_no"));
                String cert_lab = util.nvl(rs.getString("cert_lab"));
                String cts = util.nvl(rs.getString("cts"));

                pktPrpMap.put("stk_idn", stkIdn);

                for (int j = 0; j < itemHdr.size(); j++) {
                    String prp = (String)itemHdr.get(j);
                    String fld = "";
                    String val = "";
                    if (vwPrpLst.indexOf(prp) > -1) {
                        int indexof = vwPrpLst.indexOf(prp);
                        fld = "prp_";
                        if (indexof < 9)
                            fld = "prp_00" + (indexof + 1);
                        else
                            fld = "prp_0" + (indexof + 1);
                        if (prp.toUpperCase().equals("RAP_DIS"))
                            fld = "rap_dis";
                        if (prp.toUpperCase().equals("RAP_RTE"))
                            fld = "rap_rte";
                        if (prp.toUpperCase().equals("RTE"))
                            fld = "quot";
                        if (prp.toUpperCase().equals("VNM"))
                            fld = "vnm";
                        if (prp.toUpperCase().equals("CRTWT"))
                            fld = "cts";
                        if (prp.toUpperCase().equals("COMMENT"))
                            fld = "cmnt";

                        val = util.nvl(rs.getString(fld));
                        if (prp.equals("PKTCODE"))
                            val = util.nvl(rs.getString("vnm"));

                        if (prp.equals("CRTWT"))
                            val = util.nvl(rs.getString("cts"));
                        pageList = (ArrayList)pageDtl.get(nmeIdn + "_" + prp);
                        if (pageList != null) {
                            for (int p = 0; p < pageList.size(); p++) {
                                pageDtlMap = (HashMap)pageList.get(p);
                                fld_ttl = (String)pageDtlMap.get("fld_ttl");
                                dflt_val = (String)pageDtlMap.get("dflt_val");
                                fld_typ = (String)pageDtlMap.get("fld_typ");
                                if (fld_typ.equals("CPD")) {
                                    String fldval =
                                        util.nvl(rs.getString("rap_dis"), "1");
                                    dflt_val =
                                            String.valueOf(util.Round((Double.parseDouble(fldval) /
                                                                       100),
                                                                      2));
                                }
                                if (fld_typ.equals("CPDDIRECT")) {
                                    dflt_val =
                                            util.nvl(rs.getString("rap_dis"),
                                                     "1");
                                }
                                if (fld_typ.equals("SAMERTE")) {
                                    dflt_val =
                                            util.nvl(rs.getString("quot"), "1");
                                }
                                if (fld_typ.equals("AMT")) {
                                    dflt_val =
                                            util.nvl(rs.getString("amt"), "1");
                                }
                                if (fld_typ.equals("RAPAMT")) {
                                    dflt_val =
                                            util.nvl(rs.getString("rapamt"), "1");
                                }
                                if (fld_typ.equals("STT")) {
                                    dflt_val =
                                            util.nvl(rs.getString("dsp_stt"),
                                                     "1");
                                }
                                if (fld_typ.equals("ONLINERPT")) {
                                    if (cert_lab.equals("IGI")) {
                                        dflt_val =
                                                util.nvl((String)dbinfo.get("IGI_LINK"));
                                    } else if (cert_lab.equals("GIA")) {
                                        dflt_val =
                                                util.nvl((String)dbinfo.get("GIA_LINK"));
                                    } else if (cert_lab.equals("HRD")) {
                                        dflt_val =
                                                util.nvl((String)dbinfo.get("HRD_LINK"));
                                    }
                                    dflt_val =
                                            dflt_val.replaceAll("~CERTNO~", cert_no);
                                    dflt_val =
                                            dflt_val.replaceAll("~CRTWT~", cts);
                                }
                                if (fld_typ.equals("DIRECTDNA")) {
                                    dflt_val =
                                            (String)pageDtlMap.get("dflt_val");
                                    dflt_val = dflt_val.replaceAll("VNM", vnm);
                                    dflt_val =
                                            dflt_val.replaceAll("STKIDN", stkIdn);
                                }
                                j++;
                                pktPrpMap.put(fld_ttl, dflt_val);
                            }
                        }
                    }
                    pktPrpMap.put(prp, val);
                }
                pktPrpMap.put("Sr No", String.valueOf(sr++));
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                String DIAMONDIMG = util.nvl(rs.getString("DIAMONDIMG"));
                if (!DIAMONDIMG.equals("N"))
                    pktPrpMap.put("DIAMONDIMG", diamongImgpath + DIAMONDIMG);
                pageList = (ArrayList)pageDtl.get(nmeIdn + "_DNAIMGPAGE");
                if (pageList != null) {
                    for (int p = 0; p < pageList.size(); p++) {
                        pageDtlMap = (HashMap)pageList.get(p);
                        fld_ttl = (String)pageDtlMap.get("fld_ttl");
                        dflt_val = (String)pageDtlMap.get("dflt_val");
                        dflt_val = dflt_val.replaceAll("VNM", vnm);
                        dflt_val = dflt_val.replaceAll("STKIDN", stkIdn);
                        pktPrpMap.put("DIAMONDIMG", dflt_val);
                    }
                }

                String CERTIMG = util.nvl(rs.getString("CERTIMG"));
                if (!CERTIMG.equals("N"))
                    pktPrpMap.put("CERTIMG", certImgpath + CERTIMG);
                pageList = (ArrayList)pageDtl.get(nmeIdn + "_DNACERTPAGE");
                if (pageList != null) {
                    for (int p = 0; p < pageList.size(); p++) {
                        pageDtlMap = (HashMap)pageList.get(p);
                        fld_ttl = (String)pageDtlMap.get("fld_ttl");
                        dflt_val = (String)pageDtlMap.get("dflt_val");
                        dflt_val = dflt_val.replaceAll("VNM", vnm);
                        dflt_val = dflt_val.replaceAll("STKIDN", stkIdn);
                        pktPrpMap.put("CERTIMG", dflt_val);
                    }
                }
                pktList.add(pktPrpMap);
            }
            rs.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        if (!itemHdr.contains("DIAMONDIMG"))
            itemHdr.add("DIAMONDIMG");
        if (!itemHdr.contains("CERTIMG"))
            itemHdr.add("CERTIMG");

        pageList = (ArrayList)pageDtl.get(nmeIdn + "_REMOVE");
        if (pageList != null) {
            for (int p = 0; p < pageList.size(); p++) {
                pageDtlMap = (HashMap)pageList.get(p);
                fld_ttl = (String)pageDtlMap.get("fld_ttl");
                itemHdr.remove(fld_ttl);
            }
        }
        session.setAttribute("pktList", pktList);
        session.setAttribute("itemHdr", itemHdr);
    }

    public ArrayList EXPORTCommanFmtvw(HttpServletRequest req, String nmeIdn) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());
        db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = new ArrayList();
        try {

            ArrayList ary = new ArrayList();
            ary.add(util.nvl(nmeIdn));
            String prpqry =
                "select idx mprp, rank() over (order by srt) rnk from prp_map_web  where name_id = ? and mprp = 'DFCOL' ";
            ResultSet rs1 = db.execSql(" Vw Lst ", prpqry, ary);
            while (rs1.next()) {
                asViewPrp.add(rs1.getString("mprp"));
            }
            rs1.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }

        return asViewPrp;
    }

    public ActionForward genfileComman(ActionMapping am, ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       String nmeIdn) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = init(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            String diamongImgpath = "";
            String certImgpath = "";
            ArrayList vwPrpLst = EXPORTCommanFmtvw(req, nmeIdn);
            String fileName = "";
            HashMap allPageDtl =
                (info.getPageDetails() == null) ? new HashMap() :
                (HashMap)info.getPageDetails();
            HashMap pageDtl = (HashMap)allPageDtl.get("UTILITY");
            ArrayList pageList = new ArrayList();
            HashMap pageDtlMap = new HashMap();
            String fld_nme = "", fld_ttl = "", val_cond = "", dflt_val =
                "", lov_qry = "", fld_typ = "", form_nme = "";
            pageList = (ArrayList)pageDtl.get("SUBMIT");
            if (pageList != null && pageList.size() > 0) {
                for (int j = 0; j < pageList.size(); j++) {
                    pageDtlMap = (HashMap)pageList.get(j);
                    fld_nme = (String)pageDtlMap.get("fld_nme");
                    fld_ttl = (String)pageDtlMap.get("fld_ttl");
                    val_cond = (String)pageDtlMap.get("val_cond");
                    lov_qry = (String)pageDtlMap.get("lov_qry");
                    dflt_val = (String)pageDtlMap.get("dflt_val");
                    if (dflt_val.equals(nmeIdn)) {
                        fileName = lov_qry;
                        break;
                    }
                }
            }
            fileName = fileName + util.getToDteTime() + ".csv";
            ArrayList imagelistDtl =
                ((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),
                                                     info.getMem_port(),
                                                     info.getCnt() +
                                                     "_ImaageDtls") == null) ?
                new ArrayList() :
                (ArrayList)new JspUtil().getFromMem(info.getMem_ip(),
                                                    info.getMem_port(),
                                                    info.getCnt() +
                                                    "_ImaageDtls");
            if (imagelistDtl != null && imagelistDtl.size() > 0) {
                for (int j = 0; j < imagelistDtl.size(); j++) {
                    HashMap dtls = (HashMap)imagelistDtl.get(j);
                    String path = util.nvl((String)dtls.get("PATH"));
                    String gtCol = util.nvl((String)dtls.get("GTCOL"));
                    if (gtCol.equals("diamondImg"))
                        diamongImgpath = path;
                    if (gtCol.equals("certImg"))
                        certImgpath = path;
                }
            }
            HashMap prpDsc = new HashMap();
            ArrayList ary = new ArrayList();
            ary.add(util.nvl(nmeIdn));
            String prpqry =
                "select idx mprp,nvl(w3,idx) dsc, rank() over (order by srt) rnk from prp_map_web  where name_id = ? and mprp = 'DFCOL'";
            ResultSet rs1 = db.execSql(" Vw Lst ", prpqry, ary);
            while (rs1.next()) {
                prpDsc.put(util.nvl(rs1.getString("mprp")),
                           util.nvl(rs1.getString("dsc")));
            }
            rs1.close();
            PrintWriter out = res.getWriter();
            res.setContentType("application/ms-excel");
            res.setHeader("Content-disposition",
                          "attachment;filename=" + fileName);
            String ln = "";
            String lnhdr = "";
            String srchQ =
                " select stk_idn , pkt_ty,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  rap_dis  , rap_rte,quot , cert_no , vnm, pkt_dte, stt ,dsp_stt, qty , to_char(trunc(cts,2),'9990.99') cts ,cmnt, rmk ,CERTIMG, DIAMONDIMG ";
            for (int i = 0; i < vwPrpLst.size(); i++) {
                String lprp = (String)vwPrpLst.get(i);

                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                    fld += "00" + j;
                else if (j < 100)
                    fld += "0" + j;
                else if (j > 100)
                    fld += j;

                srchQ += ", " + fld;
                String dsc = "";

                pageList =
                        (ArrayList)pageDtl.get(nmeIdn + "_" + lprp + "_PRV");
                if (pageList != null) {
                    for (int p = 0; p < pageList.size(); p++) {
                        pageDtlMap = (HashMap)pageList.get(p);
                        fld_ttl = (String)pageDtlMap.get("fld_ttl");
                        if (lnhdr.equals(""))
                            lnhdr = fld_ttl;
                        else
                            lnhdr = lnhdr + "," + fld_ttl;
                    }
                }

                if (prpDsc.containsKey(lprp))
                    dsc = util.nvl((String)prpDsc.get(lprp));
                else
                    dsc = lprp;

                if (lnhdr.equals(""))
                    lnhdr = dsc;
                else
                    lnhdr = lnhdr + "," + dsc;
                pageList = (ArrayList)pageDtl.get(nmeIdn + "_" + lprp);
                if (pageList != null) {
                    for (int p = 0; p < pageList.size(); p++) {
                        pageDtlMap = (HashMap)pageList.get(p);
                        fld_ttl = (String)pageDtlMap.get("fld_ttl");
                        if (lnhdr.equals(""))
                            lnhdr = fld_ttl;
                        else
                            lnhdr = lnhdr + "," + fld_ttl;
                    }
                }
            }
            if (lnhdr.indexOf("DIAMONDIMG") <= 0)
                lnhdr = lnhdr + ",DiamondImage";
            if (lnhdr.indexOf("CERTIMG") <= 0)
                lnhdr = lnhdr + ",CertificateImage";

            out.println(lnhdr);
            int sr = 1;
            String rsltQ =
                srchQ + " from gt_srch_rslt where cts <> 0 order by sk1 , cts ";

            ResultSet rs = db.execSql("search Result", rsltQ, new ArrayList());
            try {
                while (rs.next()) {
                    ln = "";
                    if (lnhdr.indexOf("Sr No") >= 0) {
                        if (ln.equals(""))
                            ln = String.valueOf(sr++);
                    }
                    String vnm = util.nvl(rs.getString("vnm"));
                    String cert_no = util.nvl(rs.getString("cert_no"));
                    String DIAMONDIMG = util.nvl(rs.getString("DIAMONDIMG"));
                    String CERTIMG = util.nvl(rs.getString("CERTIMG"));
                    for (int j = 0; j < vwPrpLst.size(); j++) {
                        String prp = (String)vwPrpLst.get(j);
                        String fld = "prp_";
                        if (j < 9)
                            fld = "prp_00" + (j + 1);
                        else
                            fld = "prp_0" + (j + 1);

                        if (prp.toUpperCase().equals("RAP_DIS"))
                            fld = "rap_dis";
                        if (prp.toUpperCase().equals("RAP_RTE"))
                            fld = "rap_rte";
                        if (prp.toUpperCase().equals("RTE"))
                            fld = "quot";
                        if (prp.toUpperCase().equals("VNM"))
                            fld = "vnm";
                        if (prp.toUpperCase().equals("CRTWT"))
                            fld = "cts";
                        if (prp.toUpperCase().equals("COMMENT"))
                            fld = "cmnt";
                        if (prp.equals("PKTCODE"))
                            fld = "vnm";
                        String val = util.nvl(rs.getString(fld));
                        val = val.replaceAll(",", " ");
                        val = val.replaceAll("\n", " ");
                        val = val.replaceAll("\r", " ");
                        val = val.replaceAll("\t", " ");
                        val = val.trim();
                        if (ln.equals(""))
                            ln = val;
                        else
                            ln = ln + "," + val;
                        pageList = (ArrayList)pageDtl.get(nmeIdn + "_" + prp);
                        if (pageList != null) {
                            for (int p = 0; p < pageList.size(); p++) {
                                pageDtlMap = (HashMap)pageList.get(p);
                                fld_ttl = (String)pageDtlMap.get("fld_ttl");
                                dflt_val = (String)pageDtlMap.get("dflt_val");
                                fld_typ = (String)pageDtlMap.get("fld_typ");
                                if (fld_typ.equals("CPD")) {
                                    String fldval =
                                        util.nvl(rs.getString("rap_dis"), "1");
                                    dflt_val =
                                            String.valueOf(util.Round((Double.parseDouble(fldval) /
                                                                       100),
                                                                      2));
                                }
                                if (fld_ttl.equals("DIAMONDIMG")) {
                                    if (!DIAMONDIMG.equals("N")) {
                                        ln =
 ln + "," + diamongImgpath + DIAMONDIMG;
                                    } else {
                                        ln = ln + ",";
                                    }
                                } else if (fld_ttl.equals("CERTIMG")) {
                                    if (!CERTIMG.equals("N")) {
                                        ln = ln + "," + certImgpath + CERTIMG;
                                    } else {
                                        ln = ln + ",";
                                    }
                                } else {
                                    if (ln.equals(""))
                                        ln = dflt_val;
                                    else
                                        ln = ln + "," + dflt_val;
                                }
                            }
                        }
                    }

                    if (lnhdr.indexOf("DiamondImage") <= 0 &&
                        lnhdr.indexOf("DIAMONDIMG") <= 0) {
                        if (!DIAMONDIMG.equals("N")) {
                            ln = ln + "," + diamongImgpath + DIAMONDIMG;
                        } else {
                            ln = ln + ",";
                        }
                    }
                    if (lnhdr.indexOf("CertificateImage") <= 0 &&
                        lnhdr.indexOf("CERTIMG") <= 0) {
                        if (!CERTIMG.equals("N")) {
                            ln = ln + "," + certImgpath + CERTIMG;
                        } else {
                            ln = ln + ",";
                        }
                    }
                    out.println(ln);
                }
                rs.close();
            } catch (SQLException sqle) {

                // TODO: Add catch code
                sqle.printStackTrace();
            }

            out.flush();
            out.close();
            return null;
        }
    }

    public ActionForward genfileCdex(ActionMapping am, ActionForm form,
                                     HttpServletRequest req,
                                     HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = init(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            ArrayList pktList = new ArrayList();
            GenericInterface genericInt = new GenericImpl();
            ArrayList vwPrpLst =
                genericInt.genericPrprVw(req, res, "CDEX_FMT", "CDEX_FMT");
            ArrayList itemHdr = new ArrayList();
            String fileName = "GEN_FILE_CDEX_" + util.getToDteTime() + ".csv";

            PrintWriter out = res.getWriter();
            res.setContentType("application/ms-excel");
            res.setHeader("Content-disposition",
                          "attachment;filename=" + fileName);
            String diamongImgpath = "";
            String certImgpath = "";
            ArrayList imagelistDtl =
                ((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),
                                                     info.getMem_port(),
                                                     info.getCnt() +
                                                     "_ImaageDtls") == null) ?
                new ArrayList() :
                (ArrayList)new JspUtil().getFromMem(info.getMem_ip(),
                                                    info.getMem_port(),
                                                    info.getCnt() +
                                                    "_ImaageDtls");
            if (imagelistDtl != null && imagelistDtl.size() > 0) {
                for (int j = 0; j < imagelistDtl.size(); j++) {
                    HashMap dtls = (HashMap)imagelistDtl.get(j);
                    String path = util.nvl((String)dtls.get("PATH"));
                    String gtCol = util.nvl((String)dtls.get("GTCOL"));
                    if (gtCol.equals("diamondImg"))
                        diamongImgpath = path;
                    if (gtCol.equals("certImg"))
                        certImgpath = path;
                }
            }
            String ln = "Supplier";
            // int ct = db.execUpd("gtUpdate","update gt_srch_rslt set prp_001 = null", new ArrayList());
            String pktPrp = "pkgmkt.pktPrp(0,?)";
            ArrayList ary = new ArrayList();
            ary.add("CDEX_FMT");
            int ct = db.execCall(" Srch Prp ", pktPrp, ary);

            String srchQ =
                " select stk_idn , pkt_ty,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  rap_dis , rap_rte,quot ,cert_no ,cmnt , vnm, pkt_dte,decode(stt,'MKAV','Yes','') stt ,dsp_stt, qty , cts , rmk,trunc(quot*trunc(cts,2),0) vlu ";
            for (int i = 0; i < vwPrpLst.size(); i++) {
                String lprp = (String)vwPrpLst.get(i);

                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                    fld += "00" + j;
                else if (j < 100)
                    fld += "0" + j;
                else if (j > 100)
                    fld += j;
                if (lprp.equals("EC"))
                    fld =
" decode(" + fld + ",'EY','Yes','EC1','Yes','EC2', 'Yes', 'E1', 'Yes', 'E2', 'Yes', 'E3', 'Yes', 'No') " +
  fld;
                if (lprp.equals("MILK"))
                    fld =
" decode(" + fld + ",'M0', 'No','NV', 'NV','Yes') " + fld;

                srchQ += ", " + fld;
                ln = ln + "," + lprp;
                if (lprp.toUpperCase().equals("RTE"))
                    ln = ln + ",Value,Picture Link,Cert Link,Video Link";

            }
            ln = ln + " , Guaranteed Avilability";
            out.println(ln);
            int sr = 1;
            String rsltQ =
                srchQ + " from gt_srch_rslt where cts <> 0 order by sk1 , cts ";

            ResultSet rs = db.execSql("search Result", rsltQ, new ArrayList());

            while (rs.next()) {
                ln = util.nvl(rs.getString("vnm"));
                String vnm = util.nvl(rs.getString("vnm"));
                String cert_no = util.nvl(rs.getString("cert_no"));
                for (int j = 0; j < vwPrpLst.size(); j++) {
                    String prp = (String)vwPrpLst.get(j);

                    String fld = "prp_";
                    if (j < 9)
                        fld = "prp_00" + (j + 1);
                    else
                        fld = "prp_0" + (j + 1);
                    if (prp.toUpperCase().equals("VNM"))
                        fld = "vnm";
                    if (prp.toUpperCase().equals("CRTWT"))
                        fld = "cts";
                    if (prp.toUpperCase().equals("RAP_DIS"))
                        fld = "rap_dis";
                    if (prp.toUpperCase().equals("RAP_RTE"))
                        fld = "rap_rte";
                    if (prp.toUpperCase().equals("RTE"))
                        fld = "quot";
                    if (prp.toUpperCase().equals("COMMENT"))
                        fld = "cmnt";

                    String val = util.nvl(rs.getString(fld));
                    val = val.replaceAll(",", " ");
                    val = val.replaceAll("\n", " ");
                    val = val.replaceAll("\r", " ");
                    val = val.replaceAll("\t", " ");
                    val = val.trim();
                    ln = ln + "," + val;
                    if (prp.toUpperCase().equals("RTE")) {
                        ln = ln + "," + util.nvl(rs.getString("vlu"));
                        ln =
 ln + ",http://s3-ap-southeast-1.amazonaws.com/hkfauna/pics/diamondImg/" +
    vnm + ".jpg";
                        ln =
 ln + ",http://s3-ap-southeast-1.amazonaws.com/hkfauna/certificates/" +
    cert_no + ".jpg";
                        ln = ln + ",";
                    }
                }
                ln = ln + "," + util.nvl(rs.getString("stt"));
                out.println(ln);
            }
            rs.close();

            out.flush();
            out.close();

            return null;
        }
    }


    public ActionForward genfileALBBNT(ActionMapping am, ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = init(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            HashMap prpDsc = new HashMap();
            String nmeId = (String)req.getAttribute("nmeIDN");
            ArrayList ary = new ArrayList();
            ary.add(nmeId);
            String prpqry =
                "select idx mprp,nvl(w3,idx) dsc, rank() over (order by srt) rnk from prp_map_web  where name_id = ? and mprp = 'DFCOL'";
            ResultSet rs1 = db.execSql(" Vw Lst ", prpqry, ary);
            while (rs1.next()) {
                prpDsc.put(util.nvl(rs1.getString("mprp")),
                           util.nvl(rs1.getString("dsc")));
            }
            rs1.close();


            ArrayList vwPrpLst = EXPORTFmtvw(nmeId, db);

            String fileName = "brl-348.csv";

            PrintWriter out = res.getWriter();
            res.setContentType("application/ms-excel");
            res.setHeader("Content-disposition",
                          "attachment;filename=" + fileName);
            String ln = "";
            int vwPrpLstsz = vwPrpLst.size();
            System.out.print("processing....");
            String srchQ =
                " select stk_idn ,IMG4, pkt_ty,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  rap_dis , rap_rte,quot ,cert_no , vnm, pkt_dte, stt ,dsp_stt, qty , cts , rmk, CERTIMG, nvl(DIAMONDIMG,'N') DIAMONDIMG,nvl(mrayImg,'N') mrayImg,cmnt,img2,kts_vw kts,cmnt2,cmnt3 ";
            for (int i = 0; i < vwPrpLstsz; i++) {
                String lprp = (String)vwPrpLst.get(i);

                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                    fld += "00" + j;
                else if (j < 100)
                    fld += "0" + j;
                else if (j > 100)
                    fld += j;

                srchQ += ", " + fld;
                if (ln.equals(""))
                    ln = util.nvl((String)prpDsc.get(lprp));
                else
                    ln = ln + "," + util.nvl((String)prpDsc.get(lprp));
                if(lprp.equals("VNM")){
                    ln = ln + ",Availability";
                }

            }
            out.println(ln);

            int sr = 1;
            String rsltQ =
                srchQ + " from gt_srch_rslt where cts <> 0 and flg=? order by sk1 , cts ";
            ary = new ArrayList();
            ary.add("Z");
            ResultSet rs = db.execSql("search Result", rsltQ, ary);

            while (rs.next()) {
                String mrayImg = util.nvl(rs.getString("mrayImg"));
                if(!mrayImg.equals("N"))
                    mrayImg=util.nvl(rs.getString("IMG4"))+mrayImg;
                else
                    mrayImg=""  ;
                ln = "";
                for (int j = 0; j < vwPrpLstsz; j++) {
                    String prp = (String)vwPrpLst.get(j);

                    String fld = "prp_";
                    if (j < 9)
                        fld = "prp_00" + (j + 1);
                    else
                        fld = "prp_0" + (j + 1);

                    if (prp.equals("RTE"))
                        fld = "quot";
                    if (prp.equals("VNM"))
                        fld = "vnm";
                    if (prp.equals("RAP_DIS"))
                        fld = "rap_dis";


                    String val = util.nvl(rs.getString(fld));
                    val = val.replaceAll(",", " ");
                    if(prp.equals("IMG_URL")){
                        val=mrayImg;
                    }
                    if (ln.equals(""))
                        ln = val;
                    else
                        ln = ln + "," + val;
                    if(prp.equals("VNM"))
                        ln = ln + "," + util.nvl(rs.getString("stt"));;
                }
                out.println(ln);
            }
            rs.close();

            out.flush();
            out.close();


            return null;
        }
    }

    public ActionForward genfileBN(ActionMapping am, ActionForm form,
                                   HttpServletRequest req,
                                   HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = init(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            ArrayList pktList = new ArrayList();
            GenericInterface genericInt = new GenericImpl();
            ArrayList vwPrpLst =
                genericInt.genericPrprVw(req, res, "EXPORT_FMTVW",
                                         "EXPORT_FMT_VW");
            ArrayList itemHdr = new ArrayList();
            String fileName = "GEN_FILE_RAP_" + util.getToDteTime() + ".csv";

            PrintWriter out = res.getWriter();
            res.setContentType("application/ms-excel");
            res.setHeader("Content-disposition",
                          "attachment;filename=" + fileName);
            String diamongImgpath = "";
            String certImgpath = "";
            ArrayList imagelistDtl =
                ((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),
                                                     info.getMem_port(),
                                                     info.getCnt() +
                                                     "_ImaageDtls") == null) ?
                new ArrayList() :
                (ArrayList)new JspUtil().getFromMem(info.getMem_ip(),
                                                    info.getMem_port(),
                                                    info.getCnt() +
                                                    "_ImaageDtls");
            if (imagelistDtl != null && imagelistDtl.size() > 0) {
                for (int j = 0; j < imagelistDtl.size(); j++) {
                    HashMap dtls = (HashMap)imagelistDtl.get(j);
                    String path = util.nvl((String)dtls.get("PATH"));
                    String gtCol = util.nvl((String)dtls.get("GTCOL"));
                    if (gtCol.equals("diamondImg"))
                        diamongImgpath = path;
                    if (gtCol.equals("certImg"))
                        certImgpath = path;
                }
            }
            String ln = "LOTNO";
            // int ct = db.execUpd("gtUpdate","update gt_srch_rslt set prp_001 = null", new ArrayList());
            String pktPrp = "pkgmkt.pktPrp(0,?)";
            ArrayList ary = new ArrayList();
            ary.add("EXPORT_FMT_VW");
            int ct = db.execCall(" Srch Prp ", pktPrp, ary);

            String srchQ =
                " select stk_idn , pkt_ty,rap_dis , rap_rte,quot , cert_no , vnm, pkt_dte, stt ,dsp_stt, qty , cts , rmk ,CERTIMG, DIAMONDIMG ";
            for (int i = 0; i < vwPrpLst.size(); i++) {
                String lprp = (String)vwPrpLst.get(i);
                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                    fld += "00" + j;
                else if (j < 100)
                    fld += "0" + j;
                else if (j > 100)
                    fld += j;

                srchQ += ", " + fld;
                ln = ln + "," + lprp;
            }
            ln = ln + ",DiamondImage,CertificateImage";
            out.println(ln);
            int sr = 1;
            String rsltQ =
                srchQ + " from gt_srch_rslt where cts <> 0 order by sk1 , cts ";

            ResultSet rs = db.execSql("search Result", rsltQ, new ArrayList());
            try {
                while (rs.next()) {
                    ln = util.nvl(rs.getString("vnm"));
                    String vnm = util.nvl(rs.getString("vnm"));
                    String cert_no = util.nvl(rs.getString("cert_no"));
                    for (int j = 0; j < vwPrpLst.size(); j++) {
                        String prp = (String)vwPrpLst.get(j);

                        String fld = "prp_";
                        if (j < 9)
                            fld = "prp_00" + (j + 1);
                        else
                            fld = "prp_0" + (j + 1);

                        if (prp.toUpperCase().equals("RAP_DIS"))
                            fld = "rap_dis";
                        if (prp.toUpperCase().equals("RAP_RTE"))
                            fld = "rap_rte";
                        if (prp.toUpperCase().equals("RTE"))
                            fld = "quot";

                        String val = util.nvl(rs.getString(fld));

                        ln = ln + "," + val;
                    }
                    String DIAMONDIMG = util.nvl(rs.getString("DIAMONDIMG"));
                    if (!DIAMONDIMG.equals("N")) {
                        ln = ln + "," + diamongImgpath + DIAMONDIMG;
                    } else {
                        ln = ln + ",";
                    }

                    String CERTIMG = util.nvl(rs.getString("CERTIMG"));
                    if (!CERTIMG.equals("N")) {
                        ln = ln + "," + certImgpath + CERTIMG;
                    } else {
                        ln = ln + ",";
                    }

                    out.println(ln);
                }
                rs.close();
            } catch (SQLException sqle) {

                // TODO: Add catch code
                sqle.printStackTrace();
            }

            out.flush();
            out.close();


            return null;
        }
    }

    public ActionForward genfileRNBN(ActionMapping am, ActionForm form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     String flg) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = init(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            GenericInterface genericInt = new GenericImpl();
            ArrayList vwPrpLst =
                genericInt.genericPrprVw(req, res, "EXPORT_FMTVW",
                                         "EXPORT_FMT_VW");
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = util.nvl((String)dbinfo.get("CNT"));
            String fileName = cnt + "_ritani.csv";
            if (flg.equals("B"))
                fileName = cnt + "_bluenile.csv";
            String diamongImgpath = "";
            String certImgpath = "";
            ArrayList imagelistDtl =
                ((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),
                                                     info.getMem_port(),
                                                     info.getCnt() +
                                                     "_ImaageDtls") == null) ?
                new ArrayList() :
                (ArrayList)new JspUtil().getFromMem(info.getMem_ip(),
                                                    info.getMem_port(),
                                                    info.getCnt() +
                                                    "_ImaageDtls");
            if (imagelistDtl != null && imagelistDtl.size() > 0) {
                for (int j = 0; j < imagelistDtl.size(); j++) {
                    HashMap dtls = (HashMap)imagelistDtl.get(j);
                    String path = util.nvl((String)dtls.get("PATH"));
                    String gtCol = util.nvl((String)dtls.get("GTCOL"));
                    if (gtCol.equals("diamondImg"))
                        diamongImgpath = path;
                    if (gtCol.equals("certImg"))
                        certImgpath = path;
                }
            }
            PrintWriter out = res.getWriter();
            res.setContentType("application/ms-excel");
            res.setHeader("Content-disposition",
                          "attachment;filename=" + fileName);

            String ln = "LOTNO";
            String pktPrp = "pkgmkt.pktPrp(0,?)";
            ArrayList ary = new ArrayList();
            ary.add("EXPORT_FMT_VW");
            int ct = db.execCall(" Srch Prp ", pktPrp, ary);


            String srchQ =
                " select stk_idn , pkt_ty,rap_dis , rap_rte,quot ,cert_no , vnm, pkt_dte, stt ,dsp_stt, qty , cts , rmk,DIAMONDIMG,CERTIMG ";
            for (int i = 0; i < vwPrpLst.size(); i++) {
                String lprp = (String)vwPrpLst.get(i);

                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                    fld += "00" + j;
                else if (j < 100)
                    fld += "0" + j;
                else if (j > 100)
                    fld += j;
                if (lprp.equals("EC"))
                    fld =
" decode(" + fld + ",'EY','Yes','EC2', 'Yes', 'E2', 'Yes', 'E3', 'Yes', 'No') " +
  fld;
                if (lprp.equals("MILK"))
                    fld =
" decode(" + fld + ",'M0', 'No','NV', 'No','Yes') " + fld;
                srchQ += ", " + fld;
                ln = ln + "," + lprp;
            }
            ln = ln + ",DiamondImage,CertificateImage";
            out.println(ln);
            int sr = 1;
            String rsltQ =
                srchQ + " from gt_srch_rslt where cts <> 0 and flg=? order by sk1 , cts ";
            ArrayList aryQ = new ArrayList();
            aryQ.add(flg);
            ResultSet rs = db.execSql("search Result", rsltQ, aryQ);
            try {
                while (rs.next()) {
                    ln = util.nvl(rs.getString("vnm"));
                    String vnm = util.nvl(rs.getString("vnm"));
                    String cert_no = util.nvl(rs.getString("cert_no"));
                    for (int j = 0; j < vwPrpLst.size(); j++) {
                        String prp = (String)vwPrpLst.get(j);

                        String fld = "prp_";
                        if (j < 9)
                            fld = "prp_00" + (j + 1);
                        else
                            fld = "prp_0" + (j + 1);

                        if (prp.toUpperCase().equals("RAP_DIS"))
                            fld = "rap_dis";
                        if (prp.toUpperCase().equals("RAP_RTE"))
                            fld = "rap_rte";
                        if (prp.toUpperCase().equals("RTE"))
                            fld = "quot";

                        String val = util.nvl(rs.getString(fld));

                        ln = ln + "," + val;
                    }
                    String DIAMONDIMG = util.nvl(rs.getString("DIAMONDIMG"));
                    if (!DIAMONDIMG.equals("N")) {
                        ln = ln + "," + diamongImgpath + DIAMONDIMG;
                    } else {
                        ln = ln + ",";
                    }

                    String CERTIMG = util.nvl(rs.getString("CERTIMG"));
                    if (!CERTIMG.equals("N")) {
                        ln = ln + "," + certImgpath + CERTIMG;
                    } else {
                        ln = ln + ",";
                    }

                    out.println(ln);
                }
                rs.close();
            } catch (SQLException sqle) {

                // TODO: Add catch code
                sqle.printStackTrace();
            }

            out.flush();
            out.close();


            return null;
        }
    }


    public ActionForward loadrpt(ActionMapping am, ActionForm af,
                                 HttpServletRequest req,
                                 HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = initNormal(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            util.updAccessLog(req, res, "Utility", "loadrpt start");
            AtrDtlForm udf = (AtrDtlForm)af;
            HashMap allPageDtl =
                (info.getPageDetails() == null) ? new HashMap() :
                (HashMap)info.getPageDetails();
            HashMap pageDtl = (HashMap)allPageDtl.get("UTILITY");
            if (pageDtl == null || pageDtl.size() == 0) {
                pageDtl = new HashMap();
                pageDtl = util.pagedef("UTILITY");
                allPageDtl.put("UTILITY", pageDtl);
            }
            info.setPageDetails(allPageDtl);
            util.updAccessLog(req, res, "Utility", "loadrpt end");
            return am.findForward("loadrpt");
        }
    }

    //    public ActionForward fetchrpt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
    //            throws Exception {
    //        init(req,res);
    //         init(req,res);
    //          udf = (AtrDtlForm)af;
    //          String srchRef = util.nvl((String)udf.getValue("srchRef"));
    //          String reportno = util.nvl((String)udf.getValue("reportno"));
    //         String crtwt = util.nvl((String)udf.getValue("cts"));
    //         String[] reportnoLst=null;
    //         String[] ctsLst=null;
    //         if(!reportno.equals(""))
    //         reportno = util.getVnm(reportno);
    //         if(!crtwt.equals(""))
    //         crtwt = util.getVnm(crtwt);
    //         if(!reportno.equals("")){
    //         reportno=reportno.replaceAll("\\'", "");
    //         reportnoLst = reportno.split(",");
    //         }
    //         if(!crtwt.equals("")){
    //         crtwt=crtwt.replaceAll("\\'", "");
    //         ctsLst = crtwt.split(",");
    //         }
    //         if(srchRef.equals("GIA")){
    //         retriveData(reportnoLst,ctsLst,req);
    //         }
    //
    //        return am.findForward("loadrpt");
    //     }
    //    public void retriveData(String[] reportnoLst,String[] ctsLst, HttpServletRequest req) {
    //        URL u;
    //        InputStream is = null;
    //        DataInputStream dis;
    //        String s;
    //        ArrayList vwPrpLst = ASPrprViw(req);
    //        ArrayList rptList = rptListViw(req);
    //        HashMap pktDtl=new HashMap();
    //        ArrayList pktlst=new  ArrayList();
    //        for(int i=0; i < reportnoLst.length; i++) {
    //        String certno=reportnoLst[i];
    //        String cts=ctsLst[i];
    //        try {
    //           HashMap pkt=new HashMap();
    //           u = new URL("https://myapps.gia.edu/ReportCheckPortal/getReportData.do?&reportno="+certno+"&weight="+cts);
    //           is = u.openStream();
    //           dis = new DataInputStream(new BufferedInputStream(is));
    //            boolean start=false;
    //            boolean valid=false;
    //            String reportno="";
    //            int index=0;
    //            while ((s = dis.readLine()) != null) {
    //                if(s.indexOf("<h2>GIA Report Number:") > -1 && !valid){
    //                s=s.substring(s.indexOf(":")+1, s.indexOf("</h2>"));
    //                reportno=s.trim();
    //                valid=true;
    //                }
    //               if(s.indexOf("<h2 class=\"h2black\">") > -1 && !start && valid){
    //                   s=s.substring(s.indexOf(">")+1, s.indexOf("</h2>"));
    //                   System.out.println(s);
    //                   pkt.put(vwPrpLst.get(0),s);
    //                   start=true;
    //               }
    //               if(rptList.contains(s.trim()) && start  && valid && s.indexOf("<td") > -1){
    //                   index=rptList.indexOf(s.trim());
    //                   s = dis.readLine();
    //                   s=s.substring(s.indexOf(">")+1, s.indexOf("</td>"));
    //                   s=s.replaceAll("\\%", "");
    //                   s=s.replaceAll("\\&nbsp;carat", "");
    //                   if(s.indexOf("?") > -1){
    //                   s=s.substring(0,s.indexOf("?")-1);
    //                   }
    //                   if(s.indexOf(" mm") > -1){
    //                   s=s.substring(0,s.indexOf(" mm"));
    //                   }
    //                   if(s.indexOf(",") > -1){
    //                   s=s.substring(0,s.indexOf(","));
    //                   }
    //                   if(s.indexOf("(") > -1){
    //                   s=s.substring(s.indexOf("(")+1,s.indexOf(")"));
    //                   }
    //                   System.out.println(s);
    //                   pkt.put(vwPrpLst.get(index),s);
    //               }
    //                   if(rptList.contains(s.trim()) && start && valid && s.indexOf("<label") > -1){
    //                       index=rptList.indexOf(s.trim());
    //                       while ((s = dis.readLine()) != null) {
    //                       s=s.substring(s.indexOf(">")+1, s.indexOf("</label>"));
    //                       System.out.println(s);
    //                       pkt.put(vwPrpLst.get(index),s);
    //                       break;
    //                       }
    //                   }
    //               if(rptList.contains(s.trim()) && start && valid && s.indexOf("<h2") > -1){
    //                   index=rptList.indexOf(s.trim());
    //                   while ((s = dis.readLine()) != null) {
    //                   if(s.indexOf("<td width=\"70%\">") > -1) {
    //                   s = dis.readLine() ;
    //                   s = dis.readLine() ;
    //                   s=s.replaceAll("\\.", "");
    //                   System.out.println(s.trim());
    //                   pkt.put(vwPrpLst.get(index),s.trim());
    //                   break;
    //                   }
    //               }
    //               }
    //           }
    //            if(!reportno.equals("")){
    //                pktlst.add(reportno);
    //                pktDtl.put(reportno,pkt);
    //            }
    //
    //        } catch (MalformedURLException mue) {
    //
    //           System.out.println("Ouch - a MalformedURLException happened.");
    //           mue.printStackTrace();
    //           System.exit(1);
    //
    //        } catch (IOException ioe) {
    //
    //           System.out.println("Oops- an IOException happened.");
    //           ioe.printStackTrace();
    //           System.exit(1);
    //
    //        } finally {
    //           try {
    //              is.close();
    //           } catch (IOException ioe) {
    //           }
    //
    //        }
    //        }
    //        session.setAttribute("pktlst", pktlst);
    //        session.setAttribute("pktDtl", pktDtl);
    //        req.setAttribute("view", "Y");
    //    }

    public ActionForward loadlab(ActionMapping am, ActionForm af,
                                 HttpServletRequest req,
                                 HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = initNormal(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            util.updAccessLog(req, res, "Utility", "loadlab start");
            AtrDtlForm udf = (AtrDtlForm)af;
            String purIdn = util.nvl(req.getParameter("purIdn"));
            String typ = util.nvl(req.getParameter("typ"));
            String refidn = util.nvl(req.getParameter("refidn"));
            ArrayList ary = new ArrayList();
            ResultSet rs = null;
            ArrayList certnoLst = new ArrayList();
            ArrayList certLabLst = new ArrayList();
            ArrayList ctsLst = new ArrayList();
            HashMap syspktdtl = new HashMap();
            HashMap pktdtl = new HashMap();
            String sqlQ = "";
            if (typ.equals("PUR")) {
                if (!refidn.equals("")) {
                    refidn = util.getVnm(refidn);
                }
                sqlQ = "select b.pur_idn,b.ref_idn,b.cert_no,b.cts\n" +
                        ", b.lab,b.rte,b.dis,b.rap,b.cmp,to_char(trunc(cts,2) * b.rte, '99999990.00') amt \n" +
                        "from pur_dtl b\n" +
                        "where b.pur_idn=? and b.ref_idn in(" + refidn +
                        ")  and nvl(b.flg2, 'NA') <> 'DEL' ";
                ary = new ArrayList();
                ary.add(purIdn);
                rs = db.execSql("sqlQ", sqlQ, ary);
                while (rs.next()) {
                    String certno = util.nvl(rs.getString("cert_no"));
                    String crtwt = util.nvl(rs.getString("cts"));
                    if (!certno.equals("") && !crtwt.equals("")) {
                        pktdtl = new HashMap();
                        certnoLst.add(certno);
                        ctsLst.add(crtwt);
                        certLabLst.add(util.nvl(rs.getString("Lab")));
                        pktdtl.put("vnm", util.nvl(rs.getString("ref_idn")));
                        pktdtl.put("lab", util.nvl(rs.getString("Lab")));
                        pktdtl.put("rte", util.nvl(rs.getString("Rte")));
                        pktdtl.put("dis", util.nvl(rs.getString("Dis")));
                        pktdtl.put("rap", util.nvl(rs.getString("Rap")));
                        pktdtl.put("cmp", util.nvl(rs.getString("Cmp")));
                        pktdtl.put("amt", util.nvl(rs.getString("amt")));
                        syspktdtl.put(certno, pktdtl);
                    }
                }
                rs.close();
            }
            if (typ.equals("TRF") || typ.equals("STK")) {
                sqlQ =
"select cert_no,cts crtwt,vnm,cert_lab,rap_rte,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis,\n" +
                        "quot rte,cmp, trunc(((cmp*100)/greatest(rap_rte,1)) - 100,2) cmp_dis, \n" +
                        "to_char(trunc(cts,2) * quot, '99999990.00') amt from gt_srch_rslt where flg='S'";
                rs = db.execSql("sqlQ", sqlQ, ary);
                while (rs.next()) {
                    String certno = util.nvl(rs.getString("cert_no"));
                    String crtwt = util.nvl(rs.getString("crtwt"));
                    if (!certno.equals("") && !crtwt.equals("")) {
                        pktdtl = new HashMap();
                        certnoLst.add(certno);
                        ctsLst.add(crtwt);
                        certLabLst.add(util.nvl(rs.getString("cert_lab")));
                        pktdtl.put("vnm", util.nvl(rs.getString("vnm")));
                        pktdtl.put("lab", util.nvl(rs.getString("cert_lab")));
                        pktdtl.put("rte", util.nvl(rs.getString("rte")));
                        pktdtl.put("dis", util.nvl(rs.getString("r_dis")));
                        pktdtl.put("rap", util.nvl(rs.getString("rap_rte")));
                        pktdtl.put("cmp", util.nvl(rs.getString("cmp")));
                        pktdtl.put("amt", util.nvl(rs.getString("amt")));
                        syspktdtl.put(certno, pktdtl);
                    }
                }
                rs.close();
            }
            //remove this
            //        certnoLst=new ArrayList();
            //        ctsLst=new ArrayList();
            //        certnoLst.add("7131581093");
            //        ctsLst.add("2.00");
            retriveData(certnoLst, ctsLst, certLabLst, req, res);
            udf.setValue("typ", typ);
            session.setAttribute("syscertnoLst", certnoLst);
            session.setAttribute("syspktdtl", syspktdtl);
            util.updAccessLog(req, res, "Utility", "loadlab end");
            return am.findForward("loadrpt");
        }
    }

    public ActionForward saverpt(ActionMapping am, ActionForm form,
                                 HttpServletRequest req,
                                 HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg = "sucess";
        if (info != null) {
            Connection conn = info.getCon();
            if (conn != null) {
                db.setCon(info.getCon());
                util.setDb(db);
                util.setInfo(info);
                db.setLogApplNm(info.getLoApplNm());
                db.setLogid(String.valueOf(info.getLogId()));
                util.setLogApplNm(info.getLoApplNm());
                rtnPg = initNormal(req, res, session, util);
            } else {
                rtnPg = "connExists";
            }
        } else
            rtnPg = "sessionTO";
        if (!rtnPg.equals("sucess")) {
            return am.findForward(rtnPg);
        } else {
            util.updAccessLog(req, res, "Utility", "saverpt start");
            AtrDtlForm udf = (AtrDtlForm)form;
            HashMap allPageDtl =
                (info.getPageDetails() == null) ? new HashMap() :
                (HashMap)info.getPageDetails();
            HashMap pageDtl = (HashMap)allPageDtl.get("UTILITY");
            if (pageDtl == null || pageDtl.size() == 0) {
                pageDtl = new HashMap();
                pageDtl = util.pagedef("UTILITY");
                allPageDtl.put("UTILITY", pageDtl);
            }
            String isFcFmt = "";
            ArrayList pageList = (ArrayList)pageDtl.get("FANCYCOL");
            if (pageList != null && pageList.size() > 0) {
                isFcFmt = ((String)((HashMap)pageList.get(0)).get("dflt_val"));

            }

            HashMap pktDtl =
                (session.getAttribute("pktDtl") == null) ? new HashMap() :
                (HashMap)session.getAttribute("pktDtl");
            ArrayList pktList =
                (session.getAttribute("pktlst") == null) ? new ArrayList() :
                (ArrayList)session.getAttribute("pktlst");
            ArrayList syscertnoLst =
                (session.getAttribute("syscertnoLst") == null) ?
                new ArrayList() :
                (ArrayList)session.getAttribute("syscertnoLst");
            ArrayList vwPrpLst = (ArrayList)session.getAttribute("RPT_PKT_VW");
            ArrayList mappNotFnd =
                (session.getAttribute("mappNotFnd") == null) ?
                new ArrayList() :
                (ArrayList)session.getAttribute("mappNotFnd");
            String typ = util.nvl2((String)udf.getValue("typ"), "MKT");
            HashMap dbinfo = info.getDmbsInfoLst();
            String szval = (String)dbinfo.get("SIZE");
            String sh = (String)dbinfo.get("SHAPE");
            ArrayList ary = new ArrayList();
            ArrayList updlst = new ArrayList();
            String msg = "";
            HashMap pktPrpMap = new HashMap();
            ArrayList RtnMsg = new ArrayList();
            ArrayList RtnMsgList = new ArrayList();
            HashMap mprp = info.getMprp();
            HashMap prp = info.getPrp();
            int vwPrpLstsz = vwPrpLst.size();
            int cnt = 0;
            boolean execute = false;
            String dltgt = "Delete from Gt_Gia_File_Trf";
            int ctl = db.execCall("delete gt", dltgt, new ArrayList());
            String insertQ =
                " Insert Into Gt_Gia_File_Trf(Cert_No,Lab,Mprp,Val)\n" +
                "Select ?,?,?,? From Dual";
            int ct = 0;
            for (int i = 0; i < pktList.size(); i++) {
                String overtone = "";
                String intensity = "";
                String fancyColor = "";
                String fancyColorTxt = "";
                String reportno = util.nvl((String)pktList.get(i));
                pktPrpMap = new HashMap();
                pktPrpMap = (HashMap)pktDtl.get(reportno);
                String cts = util.nvl((String)pktPrpMap.get("CRTWT"));
                String isChecked = util.nvl((String)udf.getValue(reportno));
                if (isChecked.equals("yes")) {
                    execute = true;
                    for (int j = 0; j < vwPrpLstsz; j++) {
                        String lprp = (String)vwPrpLst.get(j);
                        String val = util.nvl((String)pktPrpMap.get(lprp));
                        val = val.replace("�", "");
                        if (mappNotFnd.contains(reportno + "-" + lprp)) {
                            String prpTyp =
                                util.nvl((String)mprp.get(lprp + "T"));
                            String fldName = lprp + "_" + reportno;
                            if (prpTyp.equals("C")) {
                                val = util.nvl((String)udf.getValue(fldName));
                            }
                        }
                        if (lprp.equals("OVERTONE"))
                            overtone = val;
                        if (lprp.equals("INTENSITY"))
                            intensity = val;
                        if (lprp.equals("FANCY COLOR"))
                            fancyColor = val;
                        ary = new ArrayList();
                        ary.add(reportno);
                        ary.add(util.nvl((String)pktPrpMap.get("LAB")));
                        ary.add(lprp);
                        ary.add(val);
                        if (!val.equals("")) {

                            ct =
 db.execDirUpd(" insert Gt_Gia_File_Trf ", insertQ, ary);
                            System.out.println(ct);
                            if (lprp.equals("MEASUREMENT") ||
                                lprp.equals("MEAS")) {
                                String m1 = "LN", m2 = "WD";
                                String shapeval =
                                    util.nvl((String)pktPrpMap.get(sh));
                                if (shapeval.equals("RO") ||
                                    shapeval.equals("RD") ||
                                    shapeval.equals("RBC") ||
                                    shapeval.equals("ROUND") ||
                                    shapeval.equals("Round")) {
                                    m1 = "DIA_MN";
                                    m2 = "DIA_MX";
                                }
                                val = val.replaceAll("[X]", "-");
                                val = val.replaceAll("x", "-");
                                String[] splitval = val.split("-");
                                ary = new ArrayList();
                                ary.add(reportno);
                                ary.add(util.nvl((String)pktPrpMap.get("LAB")));
                                ary.add(m1);
                                ary.add(splitval[0].trim());
                                ct =
 db.execDirUpd(" insert Gt_Gia_File_Trf ", insertQ, ary);

                                ary = new ArrayList();
                                ary.add(reportno);
                                ary.add(util.nvl((String)pktPrpMap.get("LAB")));
                                ary.add(m2);
                                ary.add(splitval[1].trim());
                                ct =
 db.execDirUpd(" insert Gt_Gia_File_Trf ", insertQ, ary);

                                ary = new ArrayList();
                                ary.add(reportno);
                                ary.add(util.nvl((String)pktPrpMap.get("LAB")));
                                ary.add("HT");
                                ary.add(splitval[2].trim());
                                ct =
 db.execDirUpd(" insert Gt_Gia_File_Trf ", insertQ, ary);
                            }
                        }

                    }
                    if (!vwPrpLst.contains("CERT_NO")) {
                        ary = new ArrayList();
                        ary.add(reportno);
                        ary.add(util.nvl((String)pktPrpMap.get("LAB")));
                        ary.add("CERT_NO");
                        ary.add(reportno);
                        ct =
 db.execDirUpd(" insert Gt_Gia_File_Trf ", insertQ, ary);
                    }
                    if (!vwPrpLst.contains("LAB")) {
                        ary = new ArrayList();
                        ary.add(reportno);
                        ary.add(util.nvl((String)pktPrpMap.get("LAB")));
                        ary.add("LAB");
                        ary.add(util.nvl((String)pktPrpMap.get("LAB")));
                        ct =
 db.execDirUpd(" insert Gt_Gia_File_Trf ", insertQ, ary);
                    }
                    String sz = "";
                    String szQ = "select GET_SZ(?) sz from dual";
                    ary = new ArrayList();
                    ary.add(cts);
                    ResultSet rs = db.execSql("sql", szQ, ary);
                    while (rs.next()) {
                        sz = util.nvl(rs.getString("sz"));
                    }
                    rs.close();
                    ary = new ArrayList();
                    ary.add(reportno);
                    ary.add(util.nvl((String)pktPrpMap.get("LAB")));
                    ary.add(szval);
                    ary.add(sz);
                    ct =
 db.execDirUpd(" insert Gt_Gia_File_Trf ", insertQ, ary);
                    if (!isFcFmt.equals("")) {
                        fancyColorTxt = isFcFmt;
                        fancyColorTxt =
                                fancyColorTxt.replaceAll("INT", intensity);
                        fancyColorTxt =
                                fancyColorTxt.replaceAll("OVR", overtone);
                        fancyColorTxt =
                                fancyColorTxt.replaceAll("FC", fancyColor);
                        fancyColorTxt = fancyColorTxt.replaceAll("@", " ");
                        fancyColorTxt = fancyColorTxt.replaceAll("  ", " ");
                        ary = new ArrayList();
                        ary.add(fancyColorTxt);
                        ary.add(reportno);
                        ary.add(util.nvl((String)pktPrpMap.get("LAB")));
                        ary.add("FANCYCOLOR");
                        ct =
 db.execDirUpd("update Gt_Gia_File_Trf ", "update Gt_Gia_File_Trf set val=? where Cert_No=? and Lab=? and Mprp=?",
               ary);
                    }
                    updlst.add(reportno);
                    syscertnoLst.remove(reportno);
                }
            }
            if (execute) {
                ary = new ArrayList();
                ary.add(typ);
                ArrayList out = new ArrayList();
                out.add("I");
                out.add("V");
                CallableStatement cst = null;
                cst =
db.execCall("PRP_PKG ", "PRP_PKG.GIA_BLK_UPD(pTyp => ?, pCnt => ?, pMsg => ?)",
            ary, out);
                cnt = cst.getInt(ary.size() + 1);
                msg = cst.getString(ary.size() + 2);
              cst.close();
              cst=null;
                RtnMsg.add(cnt);
                RtnMsg.add(msg);
                RtnMsgList.add(RtnMsg);
                req.setAttribute("msgList", RtnMsgList);
                req.setAttribute("view", "Y");
                pktList.removeAll(updlst);
                session.setAttribute("pktlst", pktList);
                session.setAttribute("syscertnoLst", syscertnoLst);
            }
            udf.setValue("typ", typ);
            util.updAccessLog(req, res, "Utility", "saverpt end");
            return am.findForward("loadrpt");
        }
    }

    public void retriveData(ArrayList certnoLst, ArrayList ctsLst,
                            ArrayList certLabLst, HttpServletRequest req,
                            HttpServletResponse res) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());
        db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        URL u;
        InputStream is = null;
        DataInputStream dis;
        String s;
        GenericInterface genericInt = new GenericImpl();
        HashMap pktDtl = new HashMap();
        ArrayList pktlst = new ArrayList();
        ArrayList mappNotFnd = new ArrayList();
        HashMap prpLst = new HashMap();
        HashMap dbinfo = info.getDmbsInfoLst();
        HashMap mprp = info.getMprp();
        String hrdauth = util.nvl((String)dbinfo.get("HRDAUTH"));
        String cnt = util.nvl((String)dbinfo.get("CNT"));
        ArrayList RtnMsg = new ArrayList();
        ArrayList RtnMsgList = new ArrayList();
        for (int i = 0; i < certnoLst.size(); i++) {
            String certno = (String)certnoLst.get(i);
            certno=certno.trim();
            String cts = (String)ctsLst.get(i);
            String lab = (String)certLabLst.get(i);
            HashMap prpMappingLst = prpMapping(req, lab);
            ArrayList rptList = rptListViw(req, lab);
            ArrayList rptListOptional =
                (ArrayList)session.getAttribute(lab + "_rptListOptional");
            genericInt.genericPrprVw(req, res, "RPT_PKT_VW", "GIARPT_PKT");
            ArrayList vwPrpLst =
                genericInt.genericPrprVw(req, res, lab.toUpperCase() +
                                         "RPT_PKT_VW",
                                         lab.toUpperCase() + "_RPT_PKT");
            String callUrl =
                util.nvl((String)dbinfo.get(lab.toUpperCase() + "_WEBSERVICELINK"));
            callUrl = callUrl.replaceAll("~AUTH~", hrdauth);
            callUrl = callUrl.replaceAll("~CERTNO~", certno);
            callUrl = callUrl.replaceAll("~CRTWT~", cts);
            callUrl = callUrl.replaceAll("&'", "&");
            if (lab.toUpperCase().equals("GIA")) {
                callUrl = callUrl.replaceAll("'", "");
                util.updAccessLog(req, res, "Utility", callUrl);
            }
            System.out.println(callUrl);
            if (!callUrl.equals("")) {

                try {

                    HashMap pkt = new HashMap();
                    pkt.put("LAB", lab);
                    u = new URL(callUrl);
                    is = u.openStream();
                    dis = new DataInputStream(new BufferedInputStream(is));
                    boolean start = false;
                    boolean valid = false;
                    int index = 0;
                    String prp = "";
                    String ourval = "";
                    while ((s = dis.readLine()) != null) {
                        s = s.trim();
                        System.out.println(s);
                        if (lab.toUpperCase().equals("IGI")) {
                            if (rptList.contains(s.trim()) ||
                                rptListOptional.contains(s.trim())) {
                                index = rptList.indexOf(s);
                                if (index == -1)
                                    index = rptListOptional.indexOf(s);

                                prp = util.nvl((String)vwPrpLst.get(index));
                                s = dis.readLine();
                                s = dis.readLine();
                                if (s.indexOf("</span>") > -1)
                                    s =
  s.substring(s.indexOf(">") + 1, s.indexOf("</span>"));
                                else
                                    s =
  s.substring(s.indexOf(">") + 1, s.length());
                                s = s.replaceAll("\\%", "");
                                s = s.replaceAll("\\ Carats", "");
                                s = s.replaceAll("\\ Carat", "");
                                s = s.replaceAll("\\<strong>", "");
                                s = s.replaceAll("\\</strong>", "");
                                s = s.replaceAll("\\°", "");
                                s = s.replaceAll("<BR>", "");
                                if (s.indexOf("?") > -1) {
                                    s = s.substring(0, s.indexOf("?") - 1);
                                }
                                if (s.indexOf(" mm") > -1) {
                                    s = s.substring(0, s.indexOf(" mm"));
                                }
                                if (s.indexOf(",") > -1) {
                                    s = s.substring(0, s.indexOf(","));
                                }
                                if (s.indexOf("(") > -1 &&
                                    prp.equals("GRDL")) {
                                    s = s.substring(0, s.indexOf("(")).trim();
                                }

                                if (s.indexOf("(") > -1) {
                                    s =
  s.substring(s.indexOf("(") + 1, s.indexOf(")"));
                                }
                                System.out.println(s);
                                if (prp.equals("CH")) {
                                    String[] valArr = s.split("-");
                                    String valCH = valArr[0].trim();
                                    valCH = valCH.toUpperCase();
                                    prpLst = (HashMap)prpMappingLst.get(prp);
                                    if (prpLst == null)
                                        ourval = valCH;
                                    else {
                                        ourval =
                                                util.nvl((String)prpLst.get(valCH));
                                        if (ourval.equals("")) {
                                            mappNotFnd.add(certno + "-" + prp);
                                            ourval = valCH;
                                        }
                                    }
                                    pkt.put(prp, ourval);
                                    s = valArr[1].trim();
                                    prp = "CA";
                                }
                                if (prp.equals("PD")) {
                                    String[] valArr = s.split("-");
                                    String valPD = valArr[0].trim();
                                    valPD = valPD.toUpperCase();
                                    prpLst = (HashMap)prpMappingLst.get(prp);
                                    if (prpLst == null)
                                        ourval = valPD;
                                    else {
                                        ourval =
                                                util.nvl((String)prpLst.get(valPD));
                                        if (ourval.equals("")) {
                                            mappNotFnd.add(certno + "-" + prp);
                                            ourval = valPD;
                                        }
                                    }
                                    pkt.put(prp, ourval);
                                    s = valArr[1].trim();
                                    prp = "PA";
                                }
                                s = s.toUpperCase();
                                prpLst = (HashMap)prpMappingLst.get(prp);
                                if (prpLst == null)
                                    ourval = s;
                                else {
                                    ourval = util.nvl((String)prpLst.get(s));
                                    if (ourval.equals("")) {
                                        mappNotFnd.add(certno + "-" + prp);
                                        ourval = s;
                                    }
                                }
                                pkt.put(prp, ourval);
                            }
                        }
                        if (lab.equalsIgnoreCase("GCAL")) {
                          

                        } else {
                            if (s.indexOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?>") >
                                -1) {
                                if (lab.toUpperCase().equals("HRD"))
                                    s = dis.readLine();
                                s = s.replaceAll("\\%", "");
                                s = s.replaceAll("\\?", "");
                                s = s.replaceAll("\\ mm", "");
                                s = s.replaceAll("LINEBREAK", "");
                                s = s.replaceAll("\\°", "");
                                s = s.replaceAll("<BR>", "");
                                System.out.println(s);
                                String fncyCol = "";
                                for (int r = 0; r < rptList.size(); r++) {
                                    String rptSting =
                                        util.nvl((String)rptList.get(r));
                                    prp = util.nvl((String)vwPrpLst.get(r));
                                    String lprpTyp =
                                        util.nvl((String)mprp.get(prp + "T"));
                                    String str =
                                        s.substring(s.indexOf(rptSting) + 1,
                                                    s.length());
                                    str =
str.substring(str.indexOf(">") + 1, str.indexOf("<"));
                                    if (prp.equals("COL") ||
                                        prp.equals("CO") &&
                                        str.indexOf("~") > -1) {
                                        fncyCol = str.toUpperCase();
                                    }
                                    if (prp.equals("SHAPE") ||
                                        prp.equals("SH") ||
                                        prp.equals("MEASUREMENT") ||
                                        prp.equals("CRTWT")) {

                                        if (str.equals("")) {


                                            str =
s.substring(s.indexOf(rptSting) + 1, s.length());
                                            str =
str.substring(str.indexOf(">") + 1, str.indexOf("<"));
                                            str = str.replace("carat", "");
                                        }
                                    }

                                    if (str.indexOf("~") > -1)
                                        str =
str.substring(str.indexOf("~") + 1, str.length());
                                    str = str.toUpperCase();
                                    prpLst = (HashMap)prpMappingLst.get(prp);
                                    if (prpLst == null && !lprpTyp.equals("C"))
                                        ourval = str;
                                    else {
                                        if (prpLst != null)
                                            ourval =
                                                    util.nvl((String)prpLst.get(str));
                                        else
                                            ourval = "";

                                        if (ourval.equals("")) {
                                            mappNotFnd.add(certno + "-" + prp);
                                            ourval = str;

                                        }
                                    }
                                    ourval = ourval.trim();
                                    pkt.put(prp, ourval);
                                    System.out.println(prp + " : " + ourval);
                                }
                                if (!fncyCol.equals("")) {
                                    pkt.put("FANCY_COLOR", fncyCol);
                                    pkt.put("FANCYCOLOR", fncyCol);
                                    mappNotFnd.add(certno + "-FANCY COLOR");
                                    mappNotFnd.add(certno + "-FANCY_CO");
                                    mappNotFnd.add(certno + "-INTENSITY");
                                    mappNotFnd.add(certno + "-OVERTONE");
                                    mappNotFnd.add(certno + "-FL_CO");
                                    mappNotFnd.add(certno + "-FL-COL");
                                }
                            }
                        }
                        //            if(s.indexOf("<h2>GIA Report Number:") > -1 && !valid){
                        //            s=s.substring(s.indexOf(":")+1, s.indexOf("</h2>"));
                        //            reportno=s.trim();
                        //            valid=true;
                        //            }
                        //           if(s.indexOf("<h2 class=\"h2black\">") > -1 && !start && valid){
                        //               s=s.substring(s.indexOf(">")+1, s.indexOf("</h2>")).toUpperCase();
                        //               System.out.println(s);
                        //               prp=util.nvl((String)vwPrpLst.get(0));
                        //               prpLst =(HashMap)prpMappingLst.get(prp);
                        //               ourval=util.nvl((String)prpLst.get(s));
                        //               if(ourval.equals("")){
                        //               mappNotFnd.add(reportno+"-"+prp);
                        //               ourval=s;
                        //               }
                        //               pkt.put(prp,ourval);
                        //               start=true;
                        //           }
                        //           if(rptList.contains(s.trim()) && start  && valid && s.indexOf("<td") > -1){
                        //               index=rptList.indexOf(s.trim());
                        //               prp=util.nvl((String)vwPrpLst.get(index));
                        //               s = dis.readLine();
                        //               s=s.substring(s.indexOf(">")+1, s.indexOf("</td>"));
                        //               s=s.replaceAll("\\%", "");
                        //               s=s.replaceAll("\\&nbsp;carat", "");
                        //               if(s.indexOf("?") > -1){
                        //               s=s.substring(0,s.indexOf("?")-1);
                        //               }
                        //               if(s.indexOf(" mm") > -1){
                        //               s=s.substring(0,s.indexOf(" mm"));
                        //               }
                        //               if(s.indexOf(",") > -1){
                        //               s=s.substring(0,s.indexOf(","));
                        //               }
                        //               if(s.indexOf("(") > -1 && prp.equals("GRDL")){
                        //               s=s.substring(0,s.indexOf("(")).trim();
                        //               }
                        //
                        //               if(s.indexOf("(") > -1){
                        //               s=s.substring(s.indexOf("(")+1,s.indexOf(")"));
                        //               }
                        //               System.out.println(s);
                        //               s=s.toUpperCase();
                        //               prpLst =(HashMap)prpMappingLst.get(prp);
                        //               if(prpLst==null)
                        //               ourval=s;
                        //               else{
                        //               ourval=util.nvl((String)prpLst.get(s));
                        //               if(ourval.equals("")){
                        //               mappNotFnd.add(reportno+"-"+prp);
                        //               ourval=s;
                        //               }}
                        //               pkt.put(prp,ourval);
                        //           }
                        //               if(rptList.contains(s.trim()) && start && valid && s.indexOf("<label") > -1){
                        //                   index=rptList.indexOf(s.trim());
                        //                   while ((s = dis.readLine()) != null) {
                        //                   s=s.substring(s.indexOf(">")+1, s.indexOf("</label>"));
                        //                   s=s.replaceAll("&amp;","&");
                        //                   System.out.println(s);
                        //                   pkt.put(vwPrpLst.get(index),s);
                        //                   break;
                        //                   }
                        //               }
                        //           if(rptList.contains(s.trim()) && start && valid && s.indexOf("<h2") > -1){
                        //               index=rptList.indexOf(s.trim());
                        //               prp=util.nvl((String)vwPrpLst.get(index));
                        //               while ((s = dis.readLine()) != null) {
                        //               if(s.indexOf("<td width=\"70%\">") > -1 && prp.equals("COMMENT")) {
                        //               s = dis.readLine() ;
                        //               s = dis.readLine() ;
                        //               System.out.println(s.trim());
                        //               String cmt=s.trim();
                        //               s = dis.readLine() ;
                        //               cmt=cmt+" "+s.trim();
                        //               cmt=cmt.replaceAll("</td>","");
                        //               pkt.put(prp,cmt.trim());
                        //               break;
                        //               }else if(s.indexOf("<td width=\"70%\">") > -1){
                        //                s=s.substring(s.indexOf(">")+1, s.indexOf("</td>"));
                        //                pkt.put(prp,s.trim());
                        //                break;
                        //               }
                        //           }
                        //           }
                    }

                    if (!certno.equals("")) {
                        pktlst.add(certno);
                        pktDtl.put(certno, pkt);
                        System.out.println(" Cert_No: " + certno);
                        System.out.println(" Cert_No: " + pkt.size());
                    }

                    if (lab.toUpperCase().equals("IGI") && pkt.size() < 5) {
                        getCOlumnMapDetails(req, res, lab.toUpperCase());
                        ArrayList vwPrpLstDfCol =
                            (ArrayList)session.getAttribute("DFCOLMPRP-" +
                                                            lab.toUpperCase());
                        HashMap prpDsc =
                            (HashMap)session.getAttribute("DFCOLMPRPDSC-" +
                                                          lab.toUpperCase());
                        callUrl =
                                util.nvl((String)dbinfo.get(lab.toUpperCase() +
                                                            "NY_WEBSERVICELINK"));
                        callUrl = callUrl.replaceAll("~AUTH~", hrdauth);
                        callUrl = callUrl.replaceAll("~CERTNO~", certno);
                        callUrl = callUrl.replaceAll("~CRTWT~", cts);
                        callUrl = callUrl.replaceAll("&'", "&");
                        if (lab.toUpperCase().equals("GIA")) {
                            callUrl = callUrl.replaceAll("'", "");
                            util.updAccessLog(req, res, "Utility", callUrl);
                        }
                        System.out.println(callUrl);
                        if (!callUrl.equals("")) {
                            pkt = new HashMap();
                            pkt.put("LAB", lab);
                            u = new URL(callUrl);
                            is = u.openStream();
                            dis = new DataInputStream(new BufferedInputStream(is));
                            start = false;
                            valid = false;
                            index = 0;
                            prp = "";
                            ourval = "";
                            while ((s = dis.readLine()) != null) {
                                s = s.trim();
                                System.out.println(s);
                                
                                    try {
                                        ObjectMapper mapper =
                                            new ObjectMapper();
                                        String json =
                                            s.substring(1, s.length() - 1);
                                        Map<String, Object> map =
                                            new HashMap<String, Object>();
                                        // convert JSON string to Map
                                        for (int r = 0;
                                             r < vwPrpLstDfCol.size(); r++) {
                                            String lprp =
                                                util.nvl((String)vwPrpLstDfCol.get(r));
                                            String lprpTyp =
                                                util.nvl((String)mprp.get(lprp +
                                                                          "T"));
                                            String dsc =
                                                util.nvl((String)prpDsc.get(lprp));
                                            String val =
                                                util.nvl((String)map.get(dsc));
                                            if (lprpTyp.equals("C")) {
                                                prpLst =
                                                        (HashMap)prpMappingLst.get(lprp);
                                                if (prpLst == null) {
                                                    mappNotFnd.add(certno +
                                                                   "-" + lprp);
                                                    ourval = val;
                                                } else {
                                                    ourval =
                                                            util.nvl((String)prpLst.get(val));
                                                    if (ourval.equals("")) {
                                                        mappNotFnd.add(certno +
                                                                       "-" +
                                                                       lprp);
                                                        ourval = val;
                                                    }
                                                }
                                            } else {
                                                val =val.replaceAll("CARAT", "");
                                                val =val.replaceAll("MM.", "");
                                                val = val.replaceAll("%", "");
                                                if (lprpTyp.equals("D")) {
                                                    val = val.trim();
                                                    String[] valArr =
                                                        s.split("/");
                                                    val =valArr[1] + "-" + valArr[0] + "-" + valArr[2];
                                                }
                                                val = val.trim();
                                                ourval = val;
                                            }
                                            pkt.put(lprp, ourval);
                                        }
                                    } catch (Exception e) {
                                        // TODO: Add catch code
                                        e.printStackTrace();
                                    } finally {

                                    }
                                
                            }
                        }
                        if (!certno.equals("")) {
                            pktDtl.put(certno, pkt);
                            System.out.println(" Cert_No: " + certno);
                            System.out.println(" Cert_No: " + pkt.size());
                        }
                    }

                } catch (MalformedURLException mue) {

                    System.out.println("Ouch - a MalformedURLException happened.");
                    mue.printStackTrace();

                } catch (IOException ioe) {

                    System.out.println("Oops- an IOException happened.");
                    ioe.printStackTrace();

                } finally {
                    try {
                        is.close();
                    } catch (IOException ioe) {
                        
                    }catch (NullPointerException ioe) {
                        RtnMsg.add("");
                        RtnMsg.add("Service Not Available");
                        RtnMsgList.add(RtnMsg);
                        req.setAttribute("msgList", RtnMsgList);
                    }

                }
            }

        }
        System.out.println(mappNotFnd);
        session.setAttribute("pktlst", pktlst);
        session.setAttribute("pktDtl", pktDtl);
        session.setAttribute("mappNotFnd", mappNotFnd);
        req.setAttribute("view", "Y");
    }


    public void allwebusersmail(ActionMapping am, ActionForm form,
                                HttpServletRequest req,
                                HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());
        db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList usrIdnlst = new ArrayList();
        MailAction mailFt = new MailAction();
        String regSql = "select a.usr_id\n" +
            "from web_usrs a , nmerel b, mnme c  \n" +
            "where a.rel_idn = b.nmerel_idn and c.nme_idn = b.nme_idn\n" +
            "and b.vld_dte is null and c.vld_dte is null and a.to_dt is null";

        ResultSet rs = db.execSql("regSql", regSql, new ArrayList());
        try {
            while (rs.next()) {
                usrIdnlst.add(util.nvl(rs.getString("usr_id")));
            }
            rs.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        for (int i = 0; i < usrIdnlst.size(); i++) {
            mailFt.sendmail(req, res, util.nvl((String)usrIdnlst.get(i)),
                            "WEB_INFO", "");
        }

    }
    //        public void retriveData(ArrayList certnoLst,ArrayList ctsLst, HttpServletRequest req, HttpServletResponse res) {
    //        HttpSession session = req.getSession(false);
    //        InfoMgr info = (InfoMgr)session.getAttribute("info");
    //        DBUtil util = new DBUtil();
    //        DBMgr db = new DBMgr();
    //        db.setCon(info.getCon());
    //        util.setDb(db);
    //        util.setInfo(info);
    //        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    //        util.setLogApplNm(info.getLoApplNm());
    //        URL u;
    //        InputStream is = null;
    //        DataInputStream dis;
    //        String s;
    //        HashMap prpMappingLst = prpMapping(req);
    //        GenericInterface genericInt = new GenericImpl();
    //        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "GIARPT_PKT_VW", "GIARPT_PKT");
    //        ArrayList rptList = rptListViw(req);
    //        HashMap pktDtl=new HashMap();
    //        ArrayList pktlst=new  ArrayList();
    //        ArrayList mappNotFnd=new  ArrayList();
    //        HashMap prpLst = new HashMap();
    //        for(int i=0; i < certnoLst.size(); i++) {
    //        String certno=(String)certnoLst.get(i);
    //        String cts=(String)ctsLst.get(i);
    //        try {
    //           HashMap pkt=new HashMap();
    //           u = new URL("https://myapps.gia.edu/ReportCheckPortal/getReportData.do?&reportno="+certno+"&weight="+cts);
    //           is = u.openStream();
    //           dis = new DataInputStream(new BufferedInputStream(is));
    //            boolean start=false;
    //            boolean valid=false;
    //            String reportno="";
    //            int index=0;
    //            String prp="";
    //            String ourval="";
    //            while ((s = dis.readLine()) != null) {
    //                if(s.indexOf("<h2>GIA Report Number:") > -1 && !valid){
    //                s=s.substring(s.indexOf(":")+1, s.indexOf("</h2>"));
    //                reportno=s.trim();
    //                valid=true;
    //                }
    //               if(s.indexOf("<h2 class=\"h2black\">") > -1 && !start && valid){
    //                   s=s.substring(s.indexOf(">")+1, s.indexOf("</h2>")).toUpperCase();
    //                   System.out.println(s);
    //                   prp=util.nvl((String)vwPrpLst.get(0));
    //                   prpLst =(HashMap)prpMappingLst.get(prp);
    //                   ourval=util.nvl((String)prpLst.get(s));
    //                   if(ourval.equals("")){
    //                   mappNotFnd.add(reportno+"-"+prp);
    //                   ourval=s;
    //                   }
    //                   pkt.put(prp,ourval);
    //                   start=true;
    //               }
    //               if(rptList.contains(s.trim()) && start  && valid && s.indexOf("<td") > -1){
    //                   index=rptList.indexOf(s.trim());
    //                   prp=util.nvl((String)vwPrpLst.get(index));
    //                   s = dis.readLine();
    //                   s=s.substring(s.indexOf(">")+1, s.indexOf("</td>"));
    //                   s=s.replaceAll("\\%", "");
    //                   s=s.replaceAll("\\&nbsp;carat", "");
    //                   if(s.indexOf("?") > -1){
    //                   s=s.substring(0,s.indexOf("?")-1);
    //                   }
    //                   if(s.indexOf(" mm") > -1){
    //                   s=s.substring(0,s.indexOf(" mm"));
    //                   }
    //                   if(s.indexOf(",") > -1){
    //                   s=s.substring(0,s.indexOf(","));
    //                   }
    //                   if(s.indexOf("(") > -1 && prp.equals("GRDL")){
    //                   s=s.substring(0,s.indexOf("(")).trim();
    //                   }
    //
    //                   if(s.indexOf("(") > -1){
    //                   s=s.substring(s.indexOf("(")+1,s.indexOf(")"));
    //                   }
    //                   System.out.println(s);
    //                   s=s.toUpperCase();
    //                   prpLst =(HashMap)prpMappingLst.get(prp);
    //                   if(prpLst==null)
    //                   ourval=s;
    //                   else{
    //                   ourval=util.nvl((String)prpLst.get(s));
    //                   if(ourval.equals("")){
    //                   mappNotFnd.add(reportno+"-"+prp);
    //                   ourval=s;
    //                   }}
    //                   pkt.put(prp,ourval);
    //               }
    //                   if(rptList.contains(s.trim()) && start && valid && s.indexOf("<label") > -1){
    //                       index=rptList.indexOf(s.trim());
    //                       while ((s = dis.readLine()) != null) {
    //                       s=s.substring(s.indexOf(">")+1, s.indexOf("</label>"));
    //                       s=s.replaceAll("&amp;","&");
    //                       System.out.println(s);
    //                       pkt.put(vwPrpLst.get(index),s);
    //                       break;
    //                       }
    //                   }
    //               if(rptList.contains(s.trim()) && start && valid && s.indexOf("<h2") > -1){
    //                   index=rptList.indexOf(s.trim());
    //                   prp=util.nvl((String)vwPrpLst.get(index));
    //                   while ((s = dis.readLine()) != null) {
    //                   if(s.indexOf("<td width=\"70%\">") > -1 && prp.equals("COMMENT")) {
    //                   s = dis.readLine() ;
    //                   s = dis.readLine() ;
    //                   System.out.println(s.trim());
    //                   String cmt=s.trim();
    //                   s = dis.readLine() ;
    //                   cmt=cmt+" "+s.trim();
    //                   cmt=cmt.replaceAll("</td>","");
    //                   pkt.put(prp,cmt.trim());
    //                   break;
    //                   }else if(s.indexOf("<td width=\"70%\">") > -1){
    //                    s=s.substring(s.indexOf(">")+1, s.indexOf("</td>"));
    //                    pkt.put(prp,s.trim());
    //                    break;
    //                   }
    //               }
    //               }
    //           }
    //            if(!reportno.equals("")){
    //                pktlst.add(reportno);
    //                pktDtl.put(reportno,pkt);
    //            }
    //
    //        } catch (MalformedURLException mue) {
    //
    //           System.out.println("Ouch - a MalformedURLException happened.");
    //           mue.printStackTrace();
    //           System.exit(1);
    //
    //        } catch (IOException ioe) {
    //
    //           System.out.println("Oops- an IOException happened.");
    //           ioe.printStackTrace();
    //           System.exit(1);
    //
    //        } finally {
    //           try {
    //              is.close();
    //           } catch (IOException ioe) {
    //           }
    //
    //        }
    //        }
    //        System.out.println(mappNotFnd);
    //        session.setAttribute("pktlst", pktlst);
    //        session.setAttribute("pktDtl", pktDtl);
    //        session.setAttribute("mappNotFnd", mappNotFnd);
    //        req.setAttribute("view", "Y");
    //    }

    public HashMap prpMapping(HttpServletRequest req, String lab) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());
        db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        lab = lab.toUpperCase();
        HashMap prpMappingLst =
            (HashMap)session.getAttribute(lab + "_prpMappingLst");
        try {
            if (prpMappingLst == null) {
                ArrayList ary = new ArrayList();
                String Sql =
                    "Select mprp,val,upper(w2) w2,Srt From Prp_Map_Web Where 1 = 1\n" +
                    "And Name_Id=(select nme_idn from nme_v where upper(nme) = upper(?)) And Mprp!='DFCOL'\n" +
                    "Order By Mprp,psrt ";
                prpMappingLst = new HashMap();
                ary.add("Lab-" + lab);
                ResultSet rs = db.execSql("prpMappingLst", Sql, ary);
                String exists = "";
                HashMap prpLst = new HashMap();
                while (rs.next()) {
                    String mprp = util.nvl(rs.getString("mprp"));
                    if (exists.equals("") || !mprp.equals(exists)) {
                        exists = mprp;
                        prpLst = new HashMap();
                    }
                    prpLst.put(util.nvl(rs.getString("w2")),
                               util.nvl(rs.getString("val")));
                    prpMappingLst.put(mprp, prpLst);
                }
                rs.close();
            }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute(lab + "_prpMappingLst", prpMappingLst);
        return prpMappingLst;
    }

    public ArrayList rptListViw(HttpServletRequest req, String lab) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());
        db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        lab = lab.toUpperCase();
        ArrayList rptList = (ArrayList)session.getAttribute(lab + "_rptList");
        try {
            if (rptList == null) {
                rptList = new ArrayList();
                ArrayList ary = new ArrayList();
                ArrayList rptListOptional = new ArrayList();
                String reportSql =
                    "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
                    "b.mdl = 'JFLEX' and b.nme_rule = ? and a.til_dte is null order by a.srt_fr ";
                ary.add(lab + "_REPORT_FMT");
                ResultSet rs = db.execSql("reportSql", reportSql, ary);
                while (rs.next()) {
                    rptList.add(rs.getString("dsc"));
                    rptListOptional.add(util.nvl((String)rs.getString("chr_fr"),
                                                 util.nvl((String)rs.getString("dsc"))));
                }
                rs.close();
                session.setAttribute(lab + "_rptListOptional",
                                     rptListOptional);
            }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute(lab + "_rptList", rptList);
        return rptList;
    }

    public String init(HttpServletRequest req, HttpServletResponse res,
                       HttpSession session, DBUtil util) {
        String rtnPg = "sucess";
        String invalide = "";
        String connExists = util.nvl(util.getConnExists());
        if (!connExists.equals("N"))
            invalide = util.nvl(util.chkTimeOut(), "N");
        if (session.isNew())
            rtnPg = "sessionTO";
        if (connExists.equals("N"))
            rtnPg = "connExists";
        if (invalide.equals("Y"))
            rtnPg = "chktimeout";
        if (rtnPg.equals("sucess")) {
            boolean sout = util.getLoginsession(req, res, session.getId());
            if (!sout) {
                rtnPg = "sessionTO";
                System.out.print("New Session Id :=" + session.getId());
            } else {
                rtnPg = util.checkUserPageRights("", util.getFullURL(req));
                if (rtnPg.equals("unauthorized"))
                    util.updAccessLog(req, res, "Utility",
                                      "Unauthorized Access");
                else
                    util.updAccessLog(req, res, "Utility", "init");
            }
        }
        return rtnPg;
    }

    public String initNormal(HttpServletRequest req, HttpServletResponse res,
                             HttpSession session, DBUtil util) {
        String rtnPg = "sucess";
        String invalide = "";
        String connExists = util.nvl(util.getConnExists());
        if (!connExists.equals("N"))
            invalide = util.nvl(util.chkTimeOut(), "N");
        if (session.isNew())
            rtnPg = "sessionTO";
        if (connExists.equals("N"))
            rtnPg = "connExists";
        if (invalide.equals("Y"))
            rtnPg = "chktimeout";
        if (rtnPg.equals("sucess")) {
            boolean sout = util.getLoginsession(req, res, session.getId());
            if (!sout) {
                rtnPg = "sessionTO";
                System.out.print("New Session Id :=" + session.getId());
            } else {
                util.updAccessLog(req, res, "Utility", "init");
            }
        }
        return rtnPg;
    }

    public ArrayList getCOlumnMapDetails(HttpServletRequest req,
                                         HttpServletResponse res, String lab) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        ArrayList asViewPrp =
            (ArrayList)session.getAttribute("DFCOLMPRP-" + lab);
        if (info != null) {
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm());
            db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            init(req, res, session, util);

            try {
                if (asViewPrp == null) {
                    ArrayList ary = new ArrayList();
                    HashMap prpDsc = new HashMap();
                    asViewPrp = new ArrayList();
                    ary.add("LAB-" + lab);
                    ResultSet rs1 =
                        db.execSql(" Vw Lst ", "select b.idx mprp,nvl(b.w3,b.idx) dsc, rank() over (order by b.srt) rnk \n" +
                            "from nme_v a,prp_map_web b  where a.nme_idn=b.name_id and upper(a.nme)=? and b.mprp = 'DFCOL'",
                            ary);
                    while (rs1.next()) {
                        asViewPrp.add(rs1.getString("mprp"));
                        prpDsc.put(util.nvl(rs1.getString("mprp")),
                                   util.nvl(rs1.getString("dsc")));
                    }
                    rs1.close();
                    session.setAttribute("DFCOLMPRP-" + lab, asViewPrp);
                    session.setAttribute("DFCOLMPRPDSC-" + lab, prpDsc);
                }

            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        }
        return asViewPrp;
    }

    public ArrayList EXPORTFmtvw(String nmeIdn, DBMgr db) {

        ArrayList ary = new ArrayList();

        String sql =
            "select idx prp, rank() over (order by srt) rnk from prp_map_web  where name_id = ? and mprp = 'DFCOL' ";
        ary.add(nmeIdn);


        ArrayList asViewPrp = new ArrayList();
        try {
            ResultSet rs1 = db.execSql(" Vw Lst ", sql, ary);
            while (rs1.next()) {
                asViewPrp.add(rs1.getString("prp"));
            }
            rs1.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return asViewPrp;
    }

    public UtilityActionNew() {
        super();
    }
}
