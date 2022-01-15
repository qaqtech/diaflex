package ft.com;

import ft.com.DBMgr;

import ft.com.DBUtil;

import ft.com.InfoMgr;

import ft.com.LogMgr;

import ft.com.dao.PktDtl;
import ft.com.marketing.SearchQuery;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;

import java.io.*;

import java.math.BigDecimal;

import java.math.RoundingMode;

import java.util.*;
import java.util.Date;

import java.sql.*;

import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

public class LabToXL extends HttpServlet {
    private static final String CONTENT_TYPE = "getServletContext()/vnd-excel";
    DBMgr db = null;
    DBUtil util = null;
    InfoMgr info = null;
    LogMgr log = null;
    HttpSession session;

    int sheetCtr = 0;
    int rowCt = 0;
    int cellCt = -1;
    int ttlCols = 10;
    int pSrt = 0;

    String tblNm = "";
    HSSFFont fontHdr = null, fontHdrWh = null, fontData = null, fontDataBold =
        null, fontDataBoldWh = null;

    HSSFCellStyle styleTopBrdr = null, styleDataBrdr = null, styleHdr =
        null, styleHdrBk = null, styleCntr = null, styleRght =
        null, styleRghtBdr = null, styleCentBold = null, styleData =
        null, styleRghtBold = null, styleHdrBorder = null, stylebodyData =
        null, stylebodyBdrData = null, styleRghtNobdr = null;
    HSSFWorkbook hwb = null;
    HSSFSheet sheet = null;
    HSSFRow row = null;
    HSSFCell cell = null;
    ArrayList vwPrpLst = new ArrayList();
    ArrayList disHdr = new ArrayList();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db = new DBMgr();
        util = new DBUtil();
        info = new InfoMgr();
        log = new LogMgr();
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        session = request.getSession(false);
        info = (InfoMgr)session.getAttribute("info");
        util = new DBUtil();
        db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        sheetCtr = 0;
        tblNm = "";
        pSrt = 0;


        String issueIdn =
            (request.getParameter("issueIdn") == null) ? "0" : request.getParameter("issueIdn");
        String lab =
            (request.getParameter("lab") == null) ? "GIA" : request.getParameter("lab");
        String auto =
            (request.getParameter("auto") == null) ? "" : request.getParameter("auto");
        String mailExcl =
            (request.getParameter("mailExcl") == null) ? "" : request.getParameter("mailExcl");


        String fileNm = "PACKING_LIST_" + lab + ".xls";
        ResultSet rs = null;

        response.setContentType(CONTENT_TYPE);
        response.setHeader("Content-Disposition",
                           "attachment;filename=" + fileNm);

        Boolean popSucceeded = Boolean.valueOf(false);

        try {
            popSucceeded = Boolean.valueOf(true);

            if (popSucceeded.booleanValue()) {
                HashMap dbinfo = info.getDmbsInfoLst();
                String client = (String)dbinfo.get("CNT");
                if (client.equals("sd") && lab.equals("IGI")) {
                    shitalLabIGI(lab, "",client, request, response);
                } else if(lab.equals("RAPGIA") || lab.equals("GIA1")) {
                    labRapGia(lab, "", request, response);
                    }else if (!auto.equals("")) {
                        labauto(lab, "", request, response);
                    }else{
                    hwb = new HSSFWorkbook();

                    fontHdr = hwb.createFont();
                    fontHdr.setFontHeightInPoints((short)24);
                    fontHdr.setFontName("Courier New");
                    fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

                    fontHdr.setItalic(false);

                    fontHdrWh = hwb.createFont();
                    fontHdrWh.setFontHeightInPoints((short)16);
                    fontHdrWh.setFontName("Courier New");
                    fontHdrWh.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
                    //fontHdrWh.setColor(new HSSFColor.WHITE().getIndex());

                    fontDataBoldWh = hwb.createFont();
                    fontDataBoldWh.setFontHeightInPoints((short)24);
                    fontDataBoldWh.setFontName("Courier New");
                    fontDataBoldWh.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
                    //  fontDataBoldWh.setColor(new HSSFColor.WHITE().getIndex());

                    fontData = hwb.createFont();
                    fontData.setFontHeightInPoints((short)10);
                    fontData.setFontName("Arial");

                    fontDataBold = hwb.createFont();
                    fontDataBold.setFontHeightInPoints((short)10);
                    fontDataBold.setFontName("Arial");
                    fontDataBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

                    styleHdrBk = hwb.createCellStyle();
                    styleHdrBk.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    styleHdrBk.setFont(fontHdrWh);
                    // styleHdrBk.setFillBackgroundColor(new HSSFColor.BLACK().getIndex());


                    styleTopBrdr = hwb.createCellStyle();
                    styleTopBrdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    styleTopBrdr.setFont(fontHdr);
                    styleTopBrdr.setFont(fontDataBold);

                    styleHdr = hwb.createCellStyle();
                    styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    styleHdr.setFont(fontHdr);

                    styleDataBrdr = hwb.createCellStyle();
                    styleDataBrdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    styleDataBrdr.setFont(fontData);

                    stylebodyData = hwb.createCellStyle();
                    stylebodyData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    stylebodyData.setFont(fontData);

                    styleCntr = hwb.createCellStyle();
                    styleCntr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    styleCntr.setFont(fontData);

                    styleRght = hwb.createCellStyle();
                    styleRght.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                    styleRght.setFont(fontData);

                    styleRghtNobdr = hwb.createCellStyle();
                    styleRghtNobdr.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                    styleRghtNobdr.setFont(fontData);

                    styleRghtBdr = hwb.createCellStyle();
                    styleRghtBdr.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                    styleRghtBdr.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                    styleRghtBdr.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                    styleRghtBdr.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                    styleRghtBdr.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                    styleRghtBdr.setFont(fontData);
                    styleRghtBold = hwb.createCellStyle();
                    styleRghtBold.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                    styleRghtBold.setFont(fontDataBold);


                    styleCentBold = hwb.createCellStyle();
                    styleCentBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    styleCentBold.setFont(fontDataBold);

                    styleData = hwb.createCellStyle();
                    styleData.setFont(fontData);
                    styleData.setWrapText(true);

                    styleHdrBorder = hwb.createCellStyle();
                    styleHdrBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);
                    styleHdrBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                    styleHdrBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                    styleHdrBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);
                    styleHdrBorder.setAlignment(HSSFCellStyle.BORDER_THIN);
                    styleHdrBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    styleHdrBorder.setFont(fontDataBold);

                    stylebodyBdrData = hwb.createCellStyle();
                    stylebodyBdrData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                    stylebodyBdrData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                    stylebodyBdrData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                    stylebodyBdrData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                    stylebodyBdrData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    stylebodyBdrData.setFont(fontData);
                    
                    String loc = util.nvl((String)request.getParameter("LOC"));
                    if(!loc.equals(""))
                    loc="_"+loc;
                    int ct = 0;
                    ArrayList ary = new ArrayList();
                    String mdl = lab + "_PKT";
                    String pegdef = lab + "_EXCEL_FMT";
                    String delQ = " Delete from gt_srch_rslt ";
                    ct = db.execUpd(" Del Old Pkts ", delQ, new ArrayList());

                    String lbxlList =
                        "select * from rep_prp where mdl=? and flg='Y' order by rnk ";
                    ary = new ArrayList();
                    ary.add(mdl);
                    ArrayList outLst = db.execSqlLst("lbXl", lbxlList, ary);
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                    vwPrpLst = new ArrayList();
                    while (rs.next()) {
                        vwPrpLst.add(rs.getString("prp"));
                    }
                    rs.close();
                    pst.close();
                    String gtView =
                        "select itm_typ,fld_ttl,val_cond,a.flg1 from df_pg_itm a, df_pg b where a.pg_idn = b.idn" +
                        " and b.mdl = ? and a.stt=b.stt and a.stt='A' and b.stt='A' and a.vld_dte is null order by a.val_cond";
                    ary = new ArrayList();
                    ary.add(pegdef);
                    disHdr = new ArrayList();
                    outLst = db.execSqlLst("PageDef", gtView, ary);
                    pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                    while (rs.next()) {
                        disHdr.add(util.nvl(rs.getString("fld_ttl")));
                    }
                    rs.close();
                    pst.close();
                    sheet = addSheet();
                    
                    if(lab.equals("FM") || lab.equals("IIDGR") )
                    addHeaderFM(lab, "", request);
                    else
                    addHeader(lab+loc, "", request);
                    
                    String[] issueidnLst = issueIdn.split(",");
                    for (int k = 0; k < issueidnLst.length; k++) {
                        ary = new ArrayList();
                        ary.add(issueidnLst[k]);
                        ary.add(mdl);
                        ct = db.execCall(" Srch Prp ", "lab_pkg.PACKING_LST(pIssId=>? , pMdl=>?)", ary);
                        
                        ary = new ArrayList();
                        ary.add(lab);
                        ct = db.execCall(" update sk1 ", "DP_PKG_LST_SRT(pLab => ?)", ary);
                        
                        String srvc =
                            "prp_00" + (vwPrpLst.indexOf("SERVICE") + 1);
                        String updQ =
                            " update gt_srch_rslt a set " + srvc + " = (select nvl(rtn_val,iss_val) from iss_rtn_prp b where a.stk_idn = b.iss_stk_idn and b.mprp = 'SERVICE' and b.iss_id = ?) where srch_id=? ";
                        if(client.equals("alb")){
                            updQ ="update gt_srch_rslt a set " + srvc + " = (select dsc from prp where mprp='SERVICE' and val =(select nvl(rtn_val,iss_val) from iss_rtn_prp b where a.stk_idn = b.iss_stk_idn \n" + 
                            "and b.mprp = 'SERVICE' and b.iss_id = ?)) where srch_id=?";
                        }
                        ary = new ArrayList();
                        ary.add(issueidnLst[k]);
                        ary.add(issueidnLst[k]);
                        ct = db.execUpd("srch", updQ, ary);
                        
                     updQ =
                          " update gt_srch_rslt a set prte = (select nvl(rtn_num,iss_num) from iss_rtn_prp b where a.stk_idn = b.iss_stk_idn and b.mprp = 'LAB_PRCNT' and b.iss_id = ?) where srch_id=? ";
                      
                      ct = db.execUpd("srch", updQ, ary);
                    }
                    String srchQ ="";
                    if(client.equals("sje")){
                        srchQ =" select rap_rte rap,stk_idn , pkt_ty, to_char(trunc(cts,2),'999,990.00') cts,  to_char(trunc(nvl((rap_rte * (100+prte)/100),cmp),2),'9999990.90') cmp ,cert_no, vnm , to_char(trunc(((trunc(cts,2))*nvl((rap_rte * (100+prte)/100),cmp)),2), '9999990.90') amt ";
                    }else{
                        srchQ =
                         " select rap_rte rap,stk_idn , pkt_ty, to_char(trunc(cts,2),'999,990.00') cts, to_char(trunc((cmp*nvl(prte,100)/100),2),'9999990.90') cmp ,cert_no, vnm ,to_char(trunc(((trunc(cts,2)*cmp)*nvl(prte,100)/100),2), '9999990.90') amt ";
                    }
//                    String srchQ =
//                        " select stk_idn , pkt_ty, to_char(trunc(cts,2),'999,990.00') cts, to_char(trunc((cmp*nvl(prte,100)/100),2),'9999990.90') cmp ,cert_no, vnm ,to_char(trunc(((trunc(cts,2)*cmp)*nvl(prte,100)/100),2), '9999990.90') amt ";
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


                    srchQ =
                            srchQ + " from gt_srch_rslt where flg =? order by sk1";
                    ary = new ArrayList();
                    ary.add("Z");
                    outLst = db.execSqlLst("gtFech", srchQ, ary);
                    pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                    ArrayList pktDetail = new ArrayList();
                    int cnt = 0;
                    while (rs.next()) {
                        PktDtl pktDtl = new PktDtl();
                        String cts = rs.getString("CTS");
                        String amt = rs.getString("AMT");
                        String cmp = rs.getString("CMP");
                        pktDtl.setValue("VNM", util.nvl(rs.getString("VNM")));
                        pktDtl.setValue("Client #", util.nvl(rs.getString("VNM")));
                        pktDtl.setValue("AMT", amt);
                        pktDtl.setValue("Total Value", amt);
                        pktDtl.setValue("CTS", cts);
                        pktDtl.setValue("CMP", cmp);
                        pktDtl.setValue("Barcode", "");
                        pktDtl.setValue("CustomerCode",  util.nvl(rs.getString("VNM")));
                        pktDtl.setValue("Report Type",  "");
                        if(lab.equals("FM")){
                        pktDtl.setValue("Market",  "US");
                        pktDtl.setValue("Origin",  "DTC");
                        pktDtl.setValue("Brand",  "ForeverMark");
                        }else if(lab.equals("IIDGR")){
                            pktDtl.setValue("Market",  "");
                            pktDtl.setValue("Origin",  "");
                            pktDtl.setValue("Brand",  "IIDGR");
                            pktDtl.setValue("Report Type",  "GR");
                        }
                        for (int i = 0; i < vwPrpLst.size(); i++) {
                            String prp = (String)vwPrpLst.get(i);
                            String fld = "prp_";
                            if (i < 9)
                                fld = "prp_00" + (i + 1);
                            else
                                fld = "prp_0" + (i + 1);

                            String val = util.nvl(rs.getString(fld));
                            String cert_no = util.nvl(rs.getString("cert_no"));
                            if (prp.equals("SERVICE") && client.equals("hk") &&
                                !cert_no.equals("")) {
                                val = val + "-" + cert_no;
                            }
                            if(prp.equals("MEAS")){
                              String[] meas = val.split("x");
                              if(meas.length>=2){
                                val =  meas[0].trim() +"-"+meas[1].trim();
                             
                              }
                            }
                            pktDtl.setValue((String)vwPrpLst.get(i), val);
                        }
                        pktDtl.setValue("RAP_RTE", rs.getString("rap"));
                        pktDtl.setValue("CRTWT", rs.getString("cts"));
                        pktDetail.add(pktDtl);
                    }
                    rs.close();
                    pst.close();
                    for (int i = 0; i < pktDetail.size(); i++) {
                        cnt++;
                        PktDtl pktDtl = (PktDtl)pktDetail.get(i);
                        row = newRow();
                        if(!lab.equals("FM") && !lab.equals("IIDGR")){
                        addCell(Integer.toString(cnt));
                        if(client.equals("sje"))
                        addCell(pktDtl.getValue("Client #"));
                        else
                        addCell(pktDtl.getValue("VNM"));
                        }
                        for (int j = 0; j < disHdr.size(); j++) {
                            String disp=util.nvl((String)disHdr.get(j));
                            if(disp.equals("Carats"))
                                disp="CTS";
                            if(disp.equals("Colour"))
                                disp="COL";
                            if(disp.equals("Quality"))
                                disp="CLR";
                            if(disp.equals("Shape"))
                                disp="SHAPE";
                            if(disp.equals("Service"))
                                disp="SERVICE";
                            String val =util.nvl(pktDtl.getValue(disp));
                            val = val.trim();
                            if (!val.equals(""))
                                addCell(val);
                            else
                                addCell("");
                        }
                        if(!lab.equals("FM") && !lab.equals("IIDGR")){
                        addCell(pktDtl.getValue("CMP"), styleRght);
                        addCell(pktDtl.getValue("AMT"), styleRght);
                        }
                    }
                    HashMap totalDtl = getTtl(client);
                    ArrayList pkts = new ArrayList();
                    pkts.add(totalDtl.get("Tcts"));
                    pkts.add(totalDtl.get("Tamt"));
                    if(!lab.equals("FM") && !lab.equals("IIDGR")){
                    addTotals(pkts, lab+loc, request);
                    }
                    OutputStream out = response.getOutputStream();
                    if (mailExcl.equals("")) {
                        hwb.write(out);
                        out.flush();
                        out.close();
                    } else {
                        SearchQuery srchQuery = new SearchQuery();
                        srchQuery.MailExcelmass(hwb, fileNm, request,
                                                response);
                        String destination =
                            info.getReqUrl() + "/contact/massmail.do?method=mailListView";
                        response.sendRedirect(response.encodeRedirectURL(destination));

                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public HashMap getTtl(String client) {
        ResultSet rs = null;
        String sql =
            "select to_char(sum(trunc(cts,2)),'999,990.00') cts, trunc(sum(trunc(trunc(cts,2)*cmp*nvl(prte,100)/100 ,2)),2) amt from gt_srch_rslt where flg = 'Z'";
        
        if(client.equals("sje"))
        sql =" select to_char(sum(trunc(cts,2)),'999,990.00') cts, \n" + 
        " trunc(sum(((trunc(cts,2))*nvl((rap_rte * (100+prte)/100),cmp))),2) amt \n" + 
        " from gt_srch_rslt where flg = 'Z'";

        ArrayList outLst = db.execSqlLst("Total", sql, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        HashMap totalDtl = new HashMap();
        try {
            if (rs.next()) {
                totalDtl.put("Tcts", util.nvl(rs.getString("cts")));
                totalDtl.put("Tamt", util.nvl(rs.getString("amt")));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return totalDtl;
    }

    public void addTotals(ArrayList pkts, String lab, HttpServletRequest req) {

        row = newRow();
        HashMap dbInfo = info.getDmbsInfoLst();
        HashMap pageDtl = util.pagedefXL(lab + "_FMT", "FOOTER");

        row = newRow();
        addCell("TOTAL CTS", styleDataBrdr);
        cellCt = disHdr.indexOf("CRTWT") + 1;
        sheet.addMergedRegion(merge(rowCt, rowCt, 0, cellCt));

        addCell((String)pkts.get(0), styleRght);
        addCell(disHdr.size() + 2, "Total US $", styleDataBrdr);
        addCell(disHdr.size() + 3, (String)pkts.get(1), styleRght);

        String TOTALRS = util.nvl((String)pageDtl.get("TOTALRS"));
      
        String XRT = util.nvl((String)pageDtl.get("XRT"));
        if(XRT.equals(""))
            XRT="INR";
        if (TOTALRS.equals("YES")) {
            row = newRow();
            addCell("Equivelant Rupees @ Custom Rate", styleDataBrdr);
            cellCt = disHdr.indexOf("CRTWT") + 2;
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, cellCt));
            String lclXrt =
                "select cur , xrt from lcl_xrt where to_dte is null and cur=? order by cur";
           ArrayList  ary = new ArrayList();
           ary.add(XRT);
            ArrayList outLst = db.execSqlLst("xrt", lclXrt, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            String xrt = "";
            try {
                while (rs.next()) {
                    xrt = util.nvl(rs.getString("xrt"));
                }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }

            addCell(xrt, styleRght);
            addCell(disHdr.size() + 2, "TOTAL RUPEES Rs", styleDataBrdr);
            addCell(disHdr.size() + 3,
                    getAmount(util.nvl((String)pkts.get(1)), xrt), styleRght);
        }
        row = newRow();
        for (int i = 0; i < ttlCols; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(styleTopBrdr);
        }


        String Comments = util.nvl((String)pageDtl.get("COMMENTS"));
        if (!Comments.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(Comments, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String Contact = util.nvl((String)pageDtl.get("CONTACT"));
        if (!Contact.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(Contact, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOR = util.nvl((String)pageDtl.get("FOR"));
        if (!FOR.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr("For ________________________ ",
                                     fontDataBold));
            cell.setCellStyle(styleRghtNobdr);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
            String PARTNER = util.nvl((String)pageDtl.get("PARTNER"));
            if (!PARTNER.equals("")) {
                row = newRow();
                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr(PARTNER, fontDataBold));
                cell.setCellStyle(styleRghtNobdr);
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
            }
        } else {
            row = newRow();
            row = newRow();
            String SIGN = util.nvl((String)pageDtl.get("SIGN"));
            if (SIGN.equals(""))
                SIGN = "Authorized Signatory";
            String xl2 = req.getParameter("xl");
            if (xl2 != null) {
                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr("For, " + dbInfo.get("SENDERNM2") +
                                         "   ", fontDataBold));
                cell.setCellStyle(styleRght);
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
                SIGN = "DIRECTOR";
            } else {
                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr("For, " + dbInfo.get("SENDERNM") +
                                         "   ", fontDataBold));
                cell.setCellStyle(styleRght);
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
            }
            row = newRow();
            sheet.addMergedRegion(merge(rowCt++, rowCt++, disHdr.size() + 2,
                                        disHdr.size() + 3));

            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(SIGN) + "   ");
            cell.setCellStyle(styleRghtNobdr);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        sheet.autoSizeColumn((short)1);
        sheet.autoSizeColumn((short)4);
        sheet.autoSizeColumn((short)(ttlCols - 1));
        sheet.autoSizeColumn((short)ttlCols);

    }

    public void addTotalsIGI(ArrayList pkts, String lab,
                             HttpServletRequest req) {

        row = newRow();
        HashMap dbInfo = info.getDmbsInfoLst();
        HashMap pageDtl = util.pagedefXL(lab + "_FMT", "FOOTER");

        row = newRow();
        addCell("TOTAL CTS", styleDataBrdr);
        cellCt = disHdr.indexOf("CRTWT") + 1;
        sheet.addMergedRegion(merge(rowCt, rowCt, 0, cellCt));

        addCell((String)pkts.get(0), styleRght);
        addCell(disHdr.size() + 2, "Total US $", styleDataBrdr);
        addCell(disHdr.size() + 3, (String)pkts.get(1), styleRght);

        String TOTALRS = util.nvl((String)pageDtl.get("TOTALRS"));
            String XRT = util.nvl((String)pageDtl.get("XRT"));
            if(XRT.equals(""))
                XRT="INR";
            if (TOTALRS.equals("YES")) {
                row = newRow();
                addCell("Equivelant Rupees @ Custom Rate", styleDataBrdr);
                cellCt = disHdr.indexOf("CRTWT") + 2;
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, cellCt));
                String lclXrt =
                    "select cur , xrt from lcl_xrt where to_dte is null and cur=? order by cur";
               ArrayList  ary = new ArrayList();
               ary.add(XRT);
            ArrayList outLst = db.execSqlLst("xrt", lclXrt, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
              String xrt = "";
            try {
                while (rs.next()) {
                    xrt = util.nvl(rs.getString("xrt"));
                }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }

            addCell(xrt, styleRght);
            addCell(disHdr.size() + 2, "TOTAL RUPEES Rs", styleDataBrdr);
            addCell(disHdr.size() + 3,
                    getAmount(util.nvl((String)pkts.get(1)), xrt), styleRght);
        }
        row = newRow();
        for (int i = 0; i < ttlCols; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(styleTopBrdr);
        }

        cellCt = 1;
        String FOOTER_ADD1 = util.nvl((String)pageDtl.get("FOOTER_ADD1"));
        if (!FOOTER_ADD1.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD1, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOOTER_ADD2 = util.nvl((String)pageDtl.get("FOOTER_ADD2"));
        if (!FOOTER_ADD2.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD2, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOOTER_ADD3 = util.nvl((String)pageDtl.get("FOOTER_ADD3"));
        if (!FOOTER_ADD3.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD3, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOOTER_ADD4 = util.nvl((String)pageDtl.get("FOOTER_ADD4"));
        if (!FOOTER_ADD4.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD4, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOOTER_ADD5 = util.nvl((String)pageDtl.get("FOOTER_ADD5"));
        if (!FOOTER_ADD5.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD5, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOOTER_ADD6 = util.nvl((String)pageDtl.get("FOOTER_ADD6"));
        if (!FOOTER_ADD6.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD6, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }

        sheet.autoSizeColumn((short)1);
        sheet.autoSizeColumn((short)4);
        sheet.autoSizeColumn((short)(ttlCols - 1));
        sheet.autoSizeColumn((short)ttlCols);

    }

    public void addCell(String val) {
        addCell(val, stylebodyData);
    }

    public void addCell(String val, HSSFCellStyle styl) {
        addCell(++cellCt, val, styl);
    }

    public void addCell(int cellNum, String val, HSSFCellStyle styl,
                        String typ) {
        HSSFCell cell = row.createCell(cellNum);
        cell.setCellStyle(styl);
        if (val.equals("")) {
            cell.setCellValue(val);
        } else {
            cell.setCellValue(Double.parseDouble(val));
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        }
    }

    public void addCell(int cellNum, String val, HSSFCellStyle styl) {
        HSSFCell cell = row.createCell(cellNum);
        cell.setCellValue(getStr(val));
        cell.setCellStyle(styl);

    }

    public HSSFSheet addSheet() {
        HSSFSheet lsheet = hwb.createSheet("Sheet" + ++sheetCtr);
        lsheet.autoSizeColumn((short)0);
        rowCt = 0;
        cellCt = -1;
        return lsheet;

    }
    public void addHeaderFM(String lab, String memo, HttpServletRequest req) {
        row = newRow();
       for (int j = 0; j < disHdr.size(); j++) {
            String prp = (String)disHdr.get(j);
            addCell(prp, styleDataBrdr);
        }
        
    }

    public void addHeader(String lab, String memo, HttpServletRequest req) {

        HashMap dbInfo = info.getDmbsInfoLst();
        HashMap pageDtl = util.pagedefXL(lab + "_FMT", "HEADER");
        String labHed = util.nvl((String)pageDtl.get("LABHED"));
        ttlCols = disHdr.size() + 3;
        if (!labHed.equals("NO")) {
            String logoNme = util.nvl((String)pageDtl.get("LABLOGO"));
            String NAME = util.nvl((String)pageDtl.get("NAME"));
            if (!logoNme.equals("")) {
                try {
                    rowCt = 2;
                    String servPath = req.getRealPath("/images/clientsLogo");

                    if (servPath.indexOf("/") > -1)
                        servPath += "/";
                    else {
                        //servPath = servPath.replace('\', '\\');
                        servPath += "\\";
                    }
                    FileInputStream fis =
                        new FileInputStream(servPath + logoNme);
                    ByteArrayOutputStream img_bytes =
                        new ByteArrayOutputStream();
                    int b;
                    while ((b = fis.read()) != -1)
                        img_bytes.write(b);
                    fis.close();
                    int imgWdt = 192, imgHt = 67;
                    //                     HSSFClientAnchor anchor =
                    //                         new HSSFClientAnchor(1,1, imgWdt, imgHt, (short)3, 0,
                    //                                             (short) 7, (short)5);
                    HSSFClientAnchor anchor =
                        new HSSFClientAnchor(1, 1, imgWdt, imgHt, (short)2, 0,
                                             (short)20, 9);
                    int index =
                        hwb.addPicture(img_bytes.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG);
                    HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                    HSSFPicture logo = patriarch.createPicture(anchor, index);
                    logo.resize();

                    anchor.setAnchorType(2);

                } catch (FileNotFoundException fnfe) {
                    // TODO: Add catch code
                    fnfe.printStackTrace();
                } catch (IOException ioe) {
                    // TODO: Add catch code
                    ioe.printStackTrace();
                }
            } else if (!NAME.equals("")) {
                row = newRow();
                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr(NAME, fontDataBold));
                cell.setCellStyle(styleHdrBk);
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
                String FULLADDER = util.nvl((String)pageDtl.get("FULLADDER"));
                if (!FULLADDER.equals("")) {
                    row = newRow();
                    cell = row.createCell(++cellCt);
                    cell.setCellValue(getStr(FULLADDER));
                    cell.setCellStyle(styleHdrBk);
                    sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
                }
                String DIRECTADD1 = util.nvl((String)pageDtl.get("DIRECTADD1"));
                if (!DIRECTADD1.equals("")) {
                    row = newRow();
                    cell = row.createCell(++cellCt);
                    cell.setCellValue(getStr(DIRECTADD1));
                    cell.setCellStyle(styleHdrBk);
                    sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
                }
                String DIRECTADD2 = util.nvl((String)pageDtl.get("DIRECTADD2"));
                if (!DIRECTADD2.equals("")) {
                    row = newRow();
                    cell = row.createCell(++cellCt);
                    cell.setCellValue(getStr(DIRECTADD2));
                    cell.setCellStyle(styleHdrBk);
                    sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
                }
            }
            String ATTN = util.nvl((String)pageDtl.get("ATTN"));
            if (!ATTN.equals("")) {

                cell = row.createCell(ttlCols - 2);
                cell.setCellValue(getStr("ATTN: " + ATTN, fontDataBold));
                cell.setCellStyle(styleData);
                sheet.addMergedRegion(merge(rowCt, rowCt, ttlCols - 2,
                                            ttlCols));
            }
            String Visit = util.nvl((String)pageDtl.get("VISIT"));
            if (!Visit.equals("")) {
                rowCt = rowCt++;
                row = newRow();
                cell = row.createCell(ttlCols - 2);
                cell.setCellValue(getStr(Visit, fontData));
                cell.setCellStyle(styleData);
                sheet.addMergedRegion(merge(rowCt, rowCt, ttlCols - 2,
                                            ttlCols));
            }
            String Tel = util.nvl((String)pageDtl.get("TEL"));
            if (!Tel.equals("")) {
                rowCt = rowCt++;
                row = newRow();
                cell = row.createCell(ttlCols - 2);
                cell.setCellValue(getStr(Tel, fontData));
                cell.setCellStyle(styleData);
                sheet.addMergedRegion(merge(rowCt, rowCt, ttlCols - 2,
                                            ttlCols));
            }
            String add1 = util.nvl((String)pageDtl.get("LABADD1"));
            String fax = util.nvl((String)pageDtl.get("FAX"));
            if (!add1.equals("")) {
                rowCt = rowCt++;
                row = newRow();

                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr(add1, fontData));
                cell.setCellStyle(styleCntr);
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols - 3));

                cell = row.createCell(ttlCols - 2);
                cell.setCellValue(getStr(fax, fontData));
                cell.setCellStyle(styleData);
                sheet.addMergedRegion(merge(rowCt, rowCt, ttlCols - 2,
                                            ttlCols));
            }

            String add2 = util.nvl((String)pageDtl.get("LABADD2"));
            String email = util.nvl((String)pageDtl.get("EMAIL"));
            if (!add2.equals("")) {
                row = newRow();

                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr(add2, fontData));
                cell.setCellStyle(styleCntr);
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols - 3));
                //To
            }
            if (!email.equals("")) {
                cell = row.createCell(ttlCols - 2);
                cell.setCellValue(getStr(email, fontData));
                cell.setCellStyle(styleData);
                sheet.addMergedRegion(merge(rowCt, rowCt, ttlCols - 2,
                                            ttlCols));
            }

            String gstin = util.nvl((String)pageDtl.get("GSTIN"));
            if (!gstin.equals("")) {
                row = newRow();

                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr("GST NO - "+ util.nvl((String)dbInfo.get("GSTIN")),
                                         fontDataBold));
                cell.setCellStyle(styleCntr);
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols - 3));
                //To
            }
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(util.nvl((String)pageDtl.get("ADD1")),
                                     fontDataBold));
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));

            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(util.nvl((String)pageDtl.get("ADD2")),
                                     fontDataBold));
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols - 3));

            String dt = util.nvl((String)pageDtl.get("DATE"));
            if (!dt.equals("")) {
                cell = row.createCell(ttlCols - 2);
                cell.setCellValue(getStr(util.nvl((String)pageDtl.get("DATE")) +
                                         util.getToDte(), fontDataBold));
                cell.setCellStyle(styleRght);
                sheet.addMergedRegion(merge(rowCt, rowCt, ttlCols - 2,
                                            ttlCols));
            }
            String add3 = util.nvl((String)pageDtl.get("ADD3"));
            if (!add3.equals("")) {
                row = newRow();
                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr(util.nvl((String)pageDtl.get("ADD3")),
                                         fontDataBold));
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
            }
            String add4 = util.nvl((String)pageDtl.get("ADD3"));
            if (!add4.equals("")) {
                row = newRow();
                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr(util.nvl((String)pageDtl.get("ADD4")),
                                         fontDataBold));
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols - 3));
            }
            String xl2 = req.getParameter("xl");
            String clientid2 = util.nvl((String)pageDtl.get("CLIENTID2"));
            if (xl2 != null) {
                if (!clientid2.equals("")) {
                    cell = row.createCell(ttlCols - 2);
                    cell.setCellValue(getStr("Client ID :" +
                                             util.nvl((String)dbInfo.get("CLIENTID_" +
                                                                         lab)),
                                             fontDataBold));
                    cell.setCellStyle(styleRght);
                    sheet.addMergedRegion(merge(rowCt, rowCt, ttlCols - 2,
                                                ttlCols));
                }
            } else {
                String clientid = util.nvl((String)pageDtl.get("CLIENTID"));
                if (!clientid.equals("")) {
                    cell = row.createCell(ttlCols - 2);
                    cell.setCellValue(getStr("Client ID :" +
                                             util.nvl((String)dbInfo.get("CLIENTID_" +
                                                                         lab)),
                                             fontDataBold));
                    cell.setCellStyle(styleRght);
                    sheet.addMergedRegion(merge(rowCt, rowCt, ttlCols - 2,
                                                ttlCols));
                }
            }
            row = newRow();
            String title =util.nvl((String)pageDtl.get("PKTTITLE"));
            if(title.equals(""))
                title="Packing List";
            cell = row.createCell(++cellCt);
            cell.setCellValue(title);
            cell.setCellStyle(styleHdrBk);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        row = newRow();
        addCell("Sr.No", styleDataBrdr);
        addCell("Lot No", styleDataBrdr);
        for (int j = 0; j < disHdr.size(); j++) {
            String prp = (String)disHdr.get(j);
            addCell(prp, styleDataBrdr);
        }
        addCell("Rate/Crt", styleRght);
        addCell("Amount US $", styleRght);
        String inrVal =util.nvl((String)pageDtl.get("AMTINR"));
        if(inrVal.equals("YES"))
            addCell("Amount INR", styleRght);
        
    }


    public void shitalLabIGI(String lab, String memo,String client, HttpServletRequest req,
                             HttpServletResponse response) {
        hwb = new HSSFWorkbook();
        try {
            String issueIdn =
                (req.getParameter("issueIdn") == null) ? "0" : req.getParameter("issueIdn");
            String fileNm = "PACKING_LIST_" + lab + ".xls";
            String mailExcl =
                (req.getParameter("mailExcl") == null) ? "" : req.getParameter("mailExcl");
            fontHdr = hwb.createFont();
            fontHdr.setFontHeightInPoints((short)24);
            fontHdr.setFontName("Courier New");
            fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

            fontHdr.setItalic(false);

            fontHdrWh = hwb.createFont();
            fontHdrWh.setFontHeightInPoints((short)16);
            fontHdrWh.setFontName("Courier New");
            fontHdrWh.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
            //fontHdrWh.setColor(new HSSFColor.WHITE().getIndex());
            fontDataBoldWh = hwb.createFont();
            fontDataBoldWh.setFontHeightInPoints((short)24);
            fontDataBoldWh.setFontName("Courier New");
            fontDataBoldWh.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
            //  fontDataBoldWh.setColor(new HSSFColor.WHITE().getIndex());

            fontData = hwb.createFont();
            fontData.setFontHeightInPoints((short)10);
            fontData.setFontName("Arial");

            fontDataBold = hwb.createFont();
            fontDataBold.setFontHeightInPoints((short)10);
            fontDataBold.setFontName("Arial");
            fontDataBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            styleHdrBk = hwb.createCellStyle();
            styleHdrBk.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleHdrBk.setFont(fontHdrWh);
            // styleHdrBk.setFillBackgroundColor(new HSSFColor.BLACK().getIndex());


            styleTopBrdr = hwb.createCellStyle();
            styleTopBrdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleTopBrdr.setFont(fontHdr);
            styleTopBrdr.setFont(fontDataBold);

            styleHdr = hwb.createCellStyle();
            styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleHdr.setFont(fontHdr);

            styleDataBrdr = hwb.createCellStyle();
            styleDataBrdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleDataBrdr.setFont(fontData);

            stylebodyData = hwb.createCellStyle();
            stylebodyData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            stylebodyData.setFont(fontData);

            styleCntr = hwb.createCellStyle();
            styleCntr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleCntr.setFont(fontData);

            styleRght = hwb.createCellStyle();
            styleRght.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            styleRght.setFont(fontData);

            styleRghtNobdr = hwb.createCellStyle();
            styleRghtNobdr.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            styleRghtNobdr.setFont(fontData);

            styleRghtBdr = hwb.createCellStyle();
            styleRghtBdr.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            styleRghtBdr.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
            styleRghtBdr.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
            styleRghtBdr.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
            styleRghtBdr.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
            styleRghtBdr.setFont(fontData);
            styleRghtBold = hwb.createCellStyle();
            styleRghtBold.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            styleRghtBold.setFont(fontDataBold);


            styleCentBold = hwb.createCellStyle();
            styleCentBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleCentBold.setFont(fontDataBold);

            styleData = hwb.createCellStyle();
            styleData.setFont(fontData);
            styleData.setWrapText(true);

            styleHdrBorder = hwb.createCellStyle();
            styleHdrBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);
            styleHdrBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleHdrBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            styleHdrBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);
            styleHdrBorder.setAlignment(HSSFCellStyle.BORDER_THIN);
            styleHdrBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleHdrBorder.setFont(fontDataBold);

            stylebodyBdrData = hwb.createCellStyle();
            stylebodyBdrData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
            stylebodyBdrData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
            stylebodyBdrData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
            stylebodyBdrData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
            stylebodyBdrData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            stylebodyBdrData.setFont(fontData);
            int ct = 0;
            ArrayList ary = new ArrayList();
            String mdl = lab + "_PKT";
            String pegdef = lab + "_EXCEL_FMT";
            String delQ = " Delete from gt_srch_rslt ";
            ct = db.execUpd(" Del Old Pkts ", delQ, new ArrayList());

            String lbxlList =
                "select * from rep_prp where mdl=? and flg='Y' order by rnk ";
            ary = new ArrayList();
            ary.add(mdl);
            ArrayList outLst = db.execSqlLst("lbXl", lbxlList, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            vwPrpLst = new ArrayList();

            while (rs.next()) {
                vwPrpLst.add(rs.getString("prp"));
            }
            rs.close();
            pst.close();
            String gtView =
                "select itm_typ,fld_ttl,val_cond,a.flg1 from df_pg_itm a, df_pg b where a.pg_idn = b.idn" +
                " and b.mdl = ? and a.stt=b.stt and a.stt='A' and b.stt='A' and a.vld_dte is null order by a.val_cond";
            ary = new ArrayList();
            ary.add(pegdef);
            disHdr = new ArrayList();

            outLst = db.execSqlLst("PageDef", gtView, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                disHdr.add(util.nvl(rs.getString("fld_ttl")));
            }
            rs.close();
            pst.close();
            sheet = addSheet();
            addHeaderIGI(lab, "", req);

            ary = new ArrayList();
            ary.add(issueIdn);
            ary.add(mdl);
            ct =
 db.execCall(" Srch Prp ", "lab_pkg.PACKING_LST(pIssId=>? , pMdl=>?)", ary);
            ary = new ArrayList();
            ary.add(lab);
            ct = db.execCall(" update sk1 ", "DP_PKG_LST_SRT(pLab => ?)", ary);

            String srvc = "prp_00" + (vwPrpLst.indexOf("SERVICE") + 1);
            String updQ =
                " update gt_srch_rslt a set " + srvc + " = (select nvl(rtn_val,iss_val) from iss_rtn_prp b where a.stk_idn = b.iss_stk_idn and b.mprp = 'SERVICE' and b.iss_id = ?) ";
            ary = new ArrayList();
            ary.add(issueIdn);
            ct = db.execUpd("srch", updQ, ary);
            String srchQ =
                " select stk_idn , pkt_ty, cts, cmp , vnm ,to_char(trunc(trunc(cts,2)*cmp,2), '9999990.90') amt ";
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


            srchQ = srchQ + " from gt_srch_rslt where flg =? order by sk1";
            ary = new ArrayList();
            ary.add("Z");
            outLst = db.execSqlLst("gtFech", srchQ, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            ArrayList pktDetail = new ArrayList();
            int cnt = 0;
            while (rs.next()) {
                PktDtl pktDtl = new PktDtl();
                String cts = rs.getString("CTS");
                String amt = rs.getString("AMT");
                String cmp = rs.getString("CMP");
                pktDtl.setValue("VNM", util.nvl(rs.getString("VNM")));
                pktDtl.setValue("AMT", amt);
                pktDtl.setValue("CTS", cts);
                pktDtl.setValue("CMP", cmp);
                for (int i = 0; i < vwPrpLst.size(); i++) {
                    String fld = "prp_";
                    if (i < 9)
                        fld = "prp_00" + (i + 1);
                    else
                        fld = "prp_0" + (i + 1);

                    String val = util.nvl(rs.getString(fld));
                    pktDtl.setValue((String)vwPrpLst.get(i), val);
                }
                pktDetail.add(pktDtl);
            }
            rs.close();
            pst.close();
            for (int i = 0; i < pktDetail.size(); i++) {
                cnt++;
                PktDtl pktDtl = (PktDtl)pktDetail.get(i);
                row = newRow();
                addCell(Integer.toString(cnt));
                addCell(pktDtl.getValue("VNM"));
                for (int j = 0; j < disHdr.size(); j++) {
                    String val =
                        util.nvl(pktDtl.getValue((String)disHdr.get(j)));
                    if (!val.equals(""))
                        addCell(val);
                    else
                        addCell("");
                }
                addCell(pktDtl.getValue("CMP"), styleRght);
                addCell(pktDtl.getValue("AMT"), styleRght);
            }
            HashMap totalDtl = getTtl(client);
            ArrayList pkts = new ArrayList();
            pkts.add(totalDtl.get("Tcts"));
            pkts.add(totalDtl.get("Tamt"));
            addTotalsIGI(pkts, lab, req);
            OutputStream out = response.getOutputStream();
            if (mailExcl.equals("")) {
                hwb.write(out);
                out.flush();
                out.close();
            } else {
                SearchQuery srchQuery = new SearchQuery();
                srchQuery.MailExcelmass(hwb, fileNm, req, response);
                String destination =
                    info.getReqUrl() + "/contact/massmail.do?method=mailListView";
                response.sendRedirect(response.encodeRedirectURL(destination));

            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void addHeaderIGI(String lab, String memo, HttpServletRequest req) {

        HashMap dbInfo = info.getDmbsInfoLst();
        HashMap pageDtl = util.pagedefXL(lab + "_FMT", "HEADER");
        ttlCols = disHdr.size() + 3;
        String CMP_NME = util.nvl((String)pageDtl.get("CMP_NME"));
        String CMP_ADD1 = util.nvl((String)pageDtl.get("CMP_ADD1"));
        String CMP_ADD2 = util.nvl((String)pageDtl.get("CMP_ADD2"));
        String CMP_ADD3 = util.nvl((String)pageDtl.get("CMP_ADD3"));
        String CMP_ADD4 = util.nvl((String)pageDtl.get("CMP_ADD4"));

        if (!CMP_NME.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(CMP_NME, fontDataBold));
            cell.setCellStyle(styleHdrBk);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols - 1));
            if (!CMP_ADD1.equals("")) {
                row = newRow();
                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr(CMP_ADD1, fontDataBold));
                cell.setCellStyle(styleHdrBk);
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols - 1));
            }
            if (!CMP_ADD2.equals("")) {
                row = newRow();
                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr(CMP_ADD2, fontDataBold));
                cell.setCellStyle(styleHdrBk);
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols - 1));
            }
            if (!CMP_ADD3.equals("")) {
                row = newRow();
                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr(CMP_ADD3, fontDataBold));
                cell.setCellStyle(styleHdrBk);
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols - 1));
            }
            if (!CMP_ADD4.equals("")) {
                row = newRow();
                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr(CMP_ADD4, fontDataBold));
                cell.setCellStyle(styleHdrBk);
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols - 1));
            }
        }

        String dt = util.nvl((String)pageDtl.get("DATE"));
        if (!dt.equals("")) {
            cell = row.createCell(ttlCols);
            cell.setCellValue(getStr(util.nvl((String)pageDtl.get("DATE")) +
                                     util.getToDte(), fontDataBold));
            cell.setCellStyle(styleRght);
            sheet.addMergedRegion(merge(rowCt, rowCt, ttlCols, ttlCols));
        }

        row = newRow();
        addCell("SR.NO", styleDataBrdr);
        addCell("CLIENT ID/STYLE NO", styleDataBrdr);
        for (int j = 0; j < disHdr.size(); j++) {
            String prp = (String)disHdr.get(j);
            addCell(prp, styleDataBrdr);
        }
        addCell("Rate/Crt", styleRght);
        addCell("Amount US $", styleRght);
    }

    public void labauto(String lab, String memo, HttpServletRequest req,
                             HttpServletResponse response) {
        hwb = new HSSFWorkbook();
        try {
            HashMap dbInfo = info.getDmbsInfoLst();
            String issueIdn =
                (req.getParameter("issueIdn") == null) ? "0" : req.getParameter("issueIdn");
            String fileNm = "PACKING_LIST_" + lab + ".xls";
            String mailExcl =
                (req.getParameter("mailExcl") == null) ? "" : req.getParameter("mailExcl");
            fontHdr = hwb.createFont();
            fontHdr.setFontHeightInPoints((short)24);
            fontHdr.setFontName("Courier New");
            fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

            fontHdr.setItalic(false);

            fontHdrWh = hwb.createFont();
            fontHdrWh.setFontHeightInPoints((short)16);
            fontHdrWh.setFontName("Courier New");
            fontHdrWh.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
            //fontHdrWh.setColor(new HSSFColor.WHITE().getIndex());
            fontDataBoldWh = hwb.createFont();
            fontDataBoldWh.setFontHeightInPoints((short)24);
            fontDataBoldWh.setFontName("Courier New");
            fontDataBoldWh.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
            //  fontDataBoldWh.setColor(new HSSFColor.WHITE().getIndex());

            fontData = hwb.createFont();
            fontData.setFontHeightInPoints((short)10);
            fontData.setFontName("Arial");

            fontDataBold = hwb.createFont();
            fontDataBold.setFontHeightInPoints((short)10);
            fontDataBold.setFontName("Arial");
            fontDataBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            styleHdrBk = hwb.createCellStyle();
            styleHdrBk.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleHdrBk.setFont(fontHdrWh);
            // styleHdrBk.setFillBackgroundColor(new HSSFColor.BLACK().getIndex());


            styleTopBrdr = hwb.createCellStyle();
            styleTopBrdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleTopBrdr.setFont(fontHdr);
            styleTopBrdr.setFont(fontDataBold);

            styleHdr = hwb.createCellStyle();
            styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleHdr.setFont(fontHdr);

            styleDataBrdr = hwb.createCellStyle();
            styleDataBrdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleDataBrdr.setFont(fontData);

            stylebodyData = hwb.createCellStyle();
            stylebodyData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            stylebodyData.setFont(fontData);

            styleCntr = hwb.createCellStyle();
            styleCntr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleCntr.setFont(fontData);

            styleRght = hwb.createCellStyle();
            styleRght.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            styleRght.setFont(fontData);

            styleRghtNobdr = hwb.createCellStyle();
            styleRghtNobdr.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            styleRghtNobdr.setFont(fontData);

            styleRghtBdr = hwb.createCellStyle();
            styleRghtBdr.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            styleRghtBdr.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
            styleRghtBdr.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
            styleRghtBdr.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
            styleRghtBdr.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
            styleRghtBdr.setFont(fontData);
            styleRghtBold = hwb.createCellStyle();
            styleRghtBold.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            styleRghtBold.setFont(fontDataBold);


            styleCentBold = hwb.createCellStyle();
            styleCentBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleCentBold.setFont(fontDataBold);

            styleData = hwb.createCellStyle();
            styleData.setFont(fontData);
            styleData.setWrapText(true);

            styleHdrBorder = hwb.createCellStyle();
            styleHdrBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);
            styleHdrBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleHdrBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            styleHdrBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);
            styleHdrBorder.setAlignment(HSSFCellStyle.BORDER_THIN);
            styleHdrBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleHdrBorder.setFont(fontDataBold);

            stylebodyBdrData = hwb.createCellStyle();
            stylebodyBdrData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
            stylebodyBdrData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
            stylebodyBdrData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
            stylebodyBdrData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
            stylebodyBdrData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            stylebodyBdrData.setFont(fontData);
            int ct = 0;
            ArrayList ary = new ArrayList();
            String mdl = lab + "_PKTAUTO";
            String pegdef = lab + "_EXCEL_FMTAUTO";
            String delQ = " Delete from gt_srch_rslt ";
            ct = db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            String client=util.nvl((String)dbInfo.get("CNT"));

            String lbxlList =
                "select * from rep_prp where mdl=? and flg='Y' order by rnk ";
            ary = new ArrayList();
            ary.add(mdl);
            ArrayList outLst = db.execSqlLst("lbXl", lbxlList, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            vwPrpLst = new ArrayList();

            while (rs.next()) {
                vwPrpLst.add(rs.getString("prp"));
            }
            rs.close();
            pst.close();
            
            sheet = addSheet();
            row = newRow();
            
            String gtView =
                "select itm_typ,dflt_val,val_cond,a.flg1 from df_pg_itm a, df_pg b where a.pg_idn = b.idn" +
                " and b.mdl = ? and a.stt=b.stt and a.stt='A' and b.stt='A' and a.vld_dte is null order by a.srt";
            ary = new ArrayList();
            ary.add(pegdef);
            disHdr = new ArrayList();
            outLst = db.execSqlLst("PageDef", gtView, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                disHdr.add(util.nvl(rs.getString("itm_typ")));
                addCell(util.nvl(rs.getString("dflt_val")), styleDataBrdr);
            }
            rs.close();
            pst.close();

            ary = new ArrayList();
            ary.add(issueIdn);
            ary.add(mdl);
            ct =
            db.execCall(" Srch Prp ", "lab_pkg.PACKING_LST(pIssId=>? , pMdl=>?)", ary);
            
            ary = new ArrayList();
            ary.add(lab);
            ct = db.execCall(" update sk1 ", "DP_PKG_LST_SRT(pLab => ?)", ary);

            String srvc = "prp_00" + (vwPrpLst.indexOf("SERVICE") + 1);
            
            int sr=1;
            String updQ =
                " update gt_srch_rslt a set " + srvc + " = (select nvl(rtn_val,iss_val) from iss_rtn_prp b where a.stk_idn = b.iss_stk_idn and b.mprp = 'SERVICE' and b.iss_id = ?) ";
            ary = new ArrayList();
            ary.add(issueIdn);
            ct = db.execUpd("srch", updQ, ary);
//            if(client.equals("ag")){
//                updQ ="update gt_srch_rslt gt set "+srvc+" = (select prt2 from prp \n" + 
//                "            where mprp = 'SERVICE' and gt."+srvc+" = prp.val) where 1=1";
//                 ct = db.execUpd("srch", updQ, new ArrayList());
//            }
            updQ =
                 " update gt_srch_rslt a set prte = (select nvl(rtn_num,iss_num) from iss_rtn_prp b where a.stk_idn = b.iss_stk_idn and b.mprp = 'LAB_PRCNT' and b.iss_id = ?) where srch_id=? ";
            ary = new ArrayList();
            ary.add(issueIdn);
            ary.add(issueIdn);
             ct = db.execUpd("srch", updQ, ary);
            
            String srchQ =
            " select stk_idn , pkt_ty, to_char(trunc(cts,2),'999,990.00') cts, to_char(trunc((cmp*nvl(prte,100)/100),2),'9999990.90') cmp ,cert_no, vnm ,to_char(trunc(((trunc(cts,2)*cmp)*nvl(prte,100)/100),2), '9999990.90') amt ";
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


            srchQ = srchQ + " from gt_srch_rslt where flg =? order by sk1";
            ary = new ArrayList();
            ary.add("Z");
            outLst = db.execSqlLst("gtFech", srchQ, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            ArrayList pktDetail = new ArrayList();
            int cnt = 0;
            while (rs.next()) {
                PktDtl pktDtl = new PktDtl();
                String cts = rs.getString("CTS");
                String amt = rs.getString("AMT");
                String cmp = rs.getString("CMP");
                pktDtl.setValue("AMT", amt);
                pktDtl.setValue("CTS", cts);
                pktDtl.setValue("CMP", cmp);
                pktDtl.setValue("SRNO", String.valueOf(sr++));
                pktDtl.setValue("CLIENTID",util.nvl((String)dbInfo.get("CLIENTID_"+lab)));
                pktDtl.setValue("CNTROL", "");
                pktDtl.setValue("CNMT", "");
                pktDtl.setValue("INC_DFLT", "I");
                pktDtl.setValue("INC_TXT", "");
                pktDtl.setValue("FOCO", "");
                pktDtl.setValue("AUTO_RES", "Y");
                for (int i = 0; i < vwPrpLst.size(); i++) {
                    String fld = "prp_";
                    if (i < 9)
                        fld = "prp_00" + (i + 1);
                    else
                        fld = "prp_0" + (i + 1);

                    String val = util.nvl(rs.getString(fld));
                    pktDtl.setValue((String)vwPrpLst.get(i), val);
                }
                pktDtl.setValue("VNM", util.nvl(rs.getString("VNM")));
                pktDtl.setValue("CRTWT", cts);
                pktDetail.add(pktDtl);
            }
            rs.close();
            pst.close();
            
            String sql =
                "select to_char(sum(trunc(cts,2)),'999,990.00') CTS, trunc(sum(trunc(trunc(cts,2)*cmp*nvl(prte,100)/100 ,2)),2) AMT from gt_srch_rslt where flg = ?";
            ary = new ArrayList();
            ary.add("Z");
            outLst = db.execSqlLst("gtFech", sql, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                PktDtl pktDtl = new PktDtl();
                String cts = rs.getString("CTS");
                String amt = rs.getString("AMT");
                pktDtl.setValue("AMT", amt);
                pktDtl.setValue("CRTWT", cts);
                pktDetail.add(pktDtl);
            }
            rs.close();
            pst.close();
            
            
            for (int i = 0; i < pktDetail.size(); i++) {
                PktDtl pktDtl = (PktDtl)pktDetail.get(i);
                row = newRow();
                for (int j = 0; j < disHdr.size(); j++) {
                    String val =
                        util.nvl(pktDtl.getValue((String)disHdr.get(j)));
                    if (!val.equals(""))
                        addCell(val);
                    else
                        addCell("");
                }
            }


            OutputStream out = response.getOutputStream();
            if (mailExcl.equals("")) {
                hwb.write(out);
                out.flush();
                out.close();
            } else {
                SearchQuery srchQuery = new SearchQuery();
                srchQuery.MailExcelmass(hwb, fileNm, req, response);
                String destination =
                    info.getReqUrl() + "/contact/massmail.do?method=mailListView";
                response.sendRedirect(response.encodeRedirectURL(destination));

            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public void labRapGia(String lab, String memo, HttpServletRequest req,
                             HttpServletResponse response) {
        hwb = new HSSFWorkbook();
        try {
            String issueIdn =
                (req.getParameter("issueIdn") == null) ? "0" : req.getParameter("issueIdn");
            String fileNm = "PACKING_LIST_" + lab + ".xls";
            String mailExcl =
                (req.getParameter("mailExcl") == null) ? "" : req.getParameter("mailExcl");
            fontHdr = hwb.createFont();
            fontHdr.setFontHeightInPoints((short)24);
            fontHdr.setFontName("Courier New");
            fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

            fontHdr.setItalic(false);

            fontHdrWh = hwb.createFont();
            fontHdrWh.setFontHeightInPoints((short)16);
            fontHdrWh.setFontName("Courier New");
            fontHdrWh.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
            //fontHdrWh.setColor(new HSSFColor.WHITE().getIndex());
            fontDataBoldWh = hwb.createFont();
            fontDataBoldWh.setFontHeightInPoints((short)24);
            fontDataBoldWh.setFontName("Courier New");
            fontDataBoldWh.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
            //  fontDataBoldWh.setColor(new HSSFColor.WHITE().getIndex());

            fontData = hwb.createFont();
            fontData.setFontHeightInPoints((short)10);
            fontData.setFontName("Arial");

            fontDataBold = hwb.createFont();
            fontDataBold.setFontHeightInPoints((short)10);
            fontDataBold.setFontName("Arial");
            fontDataBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            styleHdrBk = hwb.createCellStyle();
            styleHdrBk.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleHdrBk.setFont(fontHdrWh);
            // styleHdrBk.setFillBackgroundColor(new HSSFColor.BLACK().getIndex());


            styleTopBrdr = hwb.createCellStyle();
            styleTopBrdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleTopBrdr.setFont(fontHdr);
            styleTopBrdr.setFont(fontDataBold);

            styleHdr = hwb.createCellStyle();
            styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleHdr.setFont(fontHdr);

            styleDataBrdr = hwb.createCellStyle();
            styleDataBrdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleDataBrdr.setFont(fontData);

            stylebodyData = hwb.createCellStyle();
            stylebodyData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            stylebodyData.setFont(fontData);

            styleCntr = hwb.createCellStyle();
            styleCntr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleCntr.setFont(fontData);

            styleRght = hwb.createCellStyle();
            styleRght.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            styleRght.setFont(fontData);

            styleRghtNobdr = hwb.createCellStyle();
            styleRghtNobdr.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            styleRghtNobdr.setFont(fontData);

            styleRghtBdr = hwb.createCellStyle();
            styleRghtBdr.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            styleRghtBdr.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
            styleRghtBdr.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
            styleRghtBdr.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
            styleRghtBdr.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
            styleRghtBdr.setFont(fontData);
            styleRghtBold = hwb.createCellStyle();
            styleRghtBold.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            styleRghtBold.setFont(fontDataBold);


            styleCentBold = hwb.createCellStyle();
            styleCentBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleCentBold.setFont(fontDataBold);

            styleData = hwb.createCellStyle();
            styleData.setFont(fontData);
            styleData.setWrapText(true);

            styleHdrBorder = hwb.createCellStyle();
            styleHdrBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);
            styleHdrBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleHdrBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            styleHdrBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);
            styleHdrBorder.setAlignment(HSSFCellStyle.BORDER_THIN);
            styleHdrBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleHdrBorder.setFont(fontDataBold);

            stylebodyBdrData = hwb.createCellStyle();
            stylebodyBdrData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
            stylebodyBdrData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
            stylebodyBdrData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
            stylebodyBdrData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
            stylebodyBdrData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            stylebodyBdrData.setFont(fontData);
            int ct = 0;
            ArrayList ary = new ArrayList();
            String mdl = lab + "_PKT";
            String pegdef = lab + "_EXCEL_FMT";
            String delQ = " Delete from gt_srch_rslt ";
            ct = db.execUpd(" Del Old Pkts ", delQ, new ArrayList());

            String lbxlList =
                "select * from rep_prp where mdl=? and flg='Y' order by rnk ";
            ary = new ArrayList();
            ary.add(mdl);
            vwPrpLst = new ArrayList();
            ArrayList outLst = db.execSqlLst("lbXl", lbxlList, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                vwPrpLst.add(rs.getString("prp"));
            }
            rs.close();
            pst.close();
            String gtView =
                "select itm_typ,fld_ttl,val_cond,a.flg1 from df_pg_itm a, df_pg b where a.pg_idn = b.idn" +
                " and b.mdl = ? and a.stt=b.stt and a.stt='A' and b.stt='A' and a.vld_dte is null order by a.val_cond";
            ary = new ArrayList();
            ary.add(pegdef);
            disHdr = new ArrayList();
            outLst = db.execSqlLst("PageDef", gtView, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                disHdr.add(util.nvl(rs.getString("fld_ttl")));
            }
            rs.close();
            pst.close();
            sheet = addSheet();
            addHeaderlabRapGia(lab, "", req);

            ary = new ArrayList();
            ary.add(issueIdn);
            ary.add(mdl);
            ct =
    db.execCall(" Srch Prp ", "lab_pkg.PACKING_LST(pIssId=>? , pMdl=>?)", ary);
            
            ary = new ArrayList();
            ary.add(lab);
            ct = db.execCall(" update sk1 ", "DP_PKG_LST_SRT(pLab => ?)", ary);
            
            int serviceindex = vwPrpLst.indexOf("SERVICE")+1;
           String srvc = "prp_00"+serviceindex;
           if(serviceindex >=10)
             srvc = "prp_0"+serviceindex;

            String updQ =
                " update gt_srch_rslt a set " + srvc + " = (select nvl(rtn_val,iss_val) from iss_rtn_prp b where a.stk_idn = b.iss_stk_idn and b.mprp = 'SERVICE' and b.iss_id = ?) ";
            ary = new ArrayList();
            ary.add(issueIdn);
            ct = db.execUpd("srch", updQ, ary);
            String srchQ =
                " select stk_idn , pkt_ty, cts, cmp , vnm ,to_char(trunc(trunc(cts,2)*cmp,2), '9999990.90') amt ";
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


            srchQ = srchQ + " from gt_srch_rslt where flg =? order by sk1";
            ary = new ArrayList();
            ary.add("Z");
            outLst = db.execSqlLst("gtFech", srchQ, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            ArrayList pktDetail = new ArrayList();
            int cnt = 0;
            while (rs.next()) {
                PktDtl pktDtl = new PktDtl();
                String cts = rs.getString("CTS");
                String amt = rs.getString("AMT");
                String cmp = rs.getString("CMP");
                pktDtl.setValue("VNM", util.nvl(rs.getString("VNM")));
                pktDtl.setValue("AMT", amt);
                pktDtl.setValue("CTS", cts);
                pktDtl.setValue("CMP", cmp);
                for (int i = 0; i < vwPrpLst.size(); i++) {
                    String lprp = (String)vwPrpLst.get(i);
                    String fld = "prp_";
                    if (i < 9)
                        fld = "prp_00" + (i + 1);
                    else
                        fld = "prp_0" + (i + 1);

                    String val = util.nvl(rs.getString(fld));
                  if(lprp.equals("MEAS")){
                    String[] meas = val.split("x");
                    if(meas.length>=2){
                      val =  meas[0].trim() +"-"+meas[1].trim();
                    }
                  }
                    pktDtl.setValue((String)vwPrpLst.get(i), val);
                }
                pktDetail.add(pktDtl);
            }
            rs.close();
            pst.close();
            for (int i = 0; i < pktDetail.size(); i++) {
                cnt++;
                PktDtl pktDtl = (PktDtl)pktDetail.get(i);
                row = newRow();
                addCell(Integer.toString(cnt));
                for (int j = 0; j < disHdr.size(); j++) {
                    String val =
                        util.nvl(pktDtl.getValue((String)disHdr.get(j)));
                    if (!val.equals(""))
                        addCell(val);
                    else
                        addCell("");
                }
            }
//            HashMap totalDtl = getTtl();
//            ArrayList pkts = new ArrayList();
//            pkts.add(totalDtl.get("Tcts"));
//            pkts.add(totalDtl.get("Tamt"));
            addTotalsRapgia(lab, req);
            OutputStream out = response.getOutputStream();
            if (mailExcl.equals("")) {
                hwb.write(out);
                out.flush();
                out.close();
            } else {
                SearchQuery srchQuery = new SearchQuery();
                srchQuery.MailExcelmass(hwb, fileNm, req, response);
                String destination =
                    info.getReqUrl() + "/contact/massmail.do?method=mailListView";
                response.sendRedirect(response.encodeRedirectURL(destination));

            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

        public void addHeaderlabRapGia(String lab, String memo, HttpServletRequest req) {
        HashMap dbInfo = info.getDmbsInfoLst();
        HashMap pageDtl = util.pagedefXL(lab + "_FMT", "HEADER");
        ttlCols = disHdr.size();
        HashMap mprp = info.getMprp();
        String LAB_NME = util.nvl((String)pageDtl.get("LAB_NME"));
        String LAB_ADD1 = util.nvl((String)pageDtl.get("LAB_ADD1"));
        String LAB_ADD2 = util.nvl((String)pageDtl.get("LAB_ADD2"));
        String LAB_ADD3 = util.nvl((String)pageDtl.get("LAB_ADD3"));
        String COMPANY = util.nvl((String)pageDtl.get("COMPANY"));
        String CONTACT = util.nvl((String)pageDtl.get("CONTACT"));
        String CMP_ADD1 = util.nvl((String)pageDtl.get("CMP_ADD1"));
        String TEL = util.nvl((String)pageDtl.get("TEL"));
        String FAX = util.nvl((String)pageDtl.get("FAX"));
        String MOBILE = util.nvl((String)pageDtl.get("MOBILE"));
        String HOME_TEL = util.nvl((String)pageDtl.get("HOME_TEL"));
        String EMAIL = util.nvl((String)pageDtl.get("EMAIL"));
        String INTERCOM_NO = util.nvl((String)pageDtl.get("INTERCOM_NO"));
        if (!LAB_NME.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(LAB_NME, fontDataBold));
            cell.setCellStyle(styleHdrBk);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
            if (!LAB_ADD1.equals("")) {
                row = newRow();
                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr(LAB_ADD1, fontDataBold));
                cell.setCellStyle(styleHdrBk);
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
            }
            if (!LAB_ADD2.equals("")) {
                row = newRow();
                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr(LAB_ADD2, fontDataBold));
                cell.setCellStyle(styleHdrBk);
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
            }
            if (!LAB_ADD3.equals("")) {
                row = newRow();
                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr(LAB_ADD3, fontDataBold));
                cell.setCellStyle(styleHdrBk);
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
            }
                String dt = util.nvl((String)pageDtl.get("DATE"));
                if (!dt.equals("")) {
                    row = newRow();
                    cell = row.createCell(++cellCt);
                    cell.setCellValue(getStr(util.nvl((String)pageDtl.get("DATE")) +
                                             util.getToDte(), fontDataBold));
                    cell.setCellStyle(styleData);
                    sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
                }
            if (!COMPANY.equals("")) {
                row = newRow();
                cell = row.createCell(++cellCt);
                cell.setCellValue(getStr(COMPANY, fontData));
                cell.setCellStyle(styleData);
                sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
            }
            if (!CONTACT.equals("") && !CMP_ADD1.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(CONTACT, fontData));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols - 6));
            
            cell = row.createCell(ttlCols - 5);
            cell.setCellValue(getStr(CMP_ADD1, fontData));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, ttlCols - 5,
                                        ttlCols));
            }
            if (!TEL.equals("") && !FAX.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(TEL, fontData));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols - 6));
            
            cell = row.createCell(ttlCols - 5);
            cell.setCellValue(getStr(FAX, fontData));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, ttlCols - 5,
                                        ttlCols));
            }
            
            if (!MOBILE.equals("") && !HOME_TEL.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(MOBILE, fontData));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols - 6));
            
            cell = row.createCell(ttlCols - 5);
            cell.setCellValue(getStr(HOME_TEL, fontData));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, ttlCols - 5,
                                        ttlCols));
            }
            
            if (!EMAIL.equals("") && !INTERCOM_NO.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(EMAIL, fontData));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols - 6));
            
            cell = row.createCell(ttlCols - 5);
            cell.setCellValue(getStr(INTERCOM_NO, fontData));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, ttlCols - 5,
                                        ttlCols));
            }
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr("ALL FIELDS MUST BE FILLED", fontDataBold));
            cell.setCellStyle(styleHdrBk);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        HashMap hdrDsc=new HashMap();
        hdrDsc.put("CMP", "$ / Ct");
        hdrDsc.put("AMT", "$ Total");
        hdrDsc.put("VNM", "Client #");
        row = newRow();
        addCell("SR.NO", styleDataBrdr);
        for (int j = 0; j < disHdr.size(); j++) {
            String prpold=(String)disHdr.get(j);
            String prp = util.nvl((String)hdrDsc.get(prpold),prpold);
            if(prp.equals(prpold))
            prp= util.nvl((String)mprp.get(prp+"D"));    
            addCell(prp, styleDataBrdr);
        }
    }
    public void addTotalsRapgia(String lab,
                             HttpServletRequest req) {

        row = newRow();
        HashMap dbInfo = info.getDmbsInfoLst();
        HashMap pageDtl = util.pagedefXL(lab + "_FMT", "FOOTER");
        String FOOTER_ADD1 = util.nvl((String)pageDtl.get("FOOTER_ADD1"));
        if (!FOOTER_ADD1.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD1, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOOTER_ADD2 = util.nvl((String)pageDtl.get("FOOTER_ADD2"));
        if (!FOOTER_ADD2.equals("")) {
            row = newRow();
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD2, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOOTER_ADD3 = util.nvl((String)pageDtl.get("FOOTER_ADD3"));
        if (!FOOTER_ADD3.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD3, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOOTER_ADD4 = util.nvl((String)pageDtl.get("FOOTER_ADD4"));
        if (!FOOTER_ADD4.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD4, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOOTER_ADD5 = util.nvl((String)pageDtl.get("FOOTER_ADD5"));
        if (!FOOTER_ADD5.equals("")) {
            row = newRow();
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD5, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOOTER_ADD6 = util.nvl((String)pageDtl.get("FOOTER_ADD6"));
        if (!FOOTER_ADD6.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD6, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOOTER_ADD7 = util.nvl((String)pageDtl.get("FOOTER_ADD7"));
        if (!FOOTER_ADD7.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD7, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOOTER_ADD71 = util.nvl((String)pageDtl.get("FOOTER_ADD71"));
        if (!FOOTER_ADD71.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD71, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        
        String FOOTER_ADD8 = util.nvl((String)pageDtl.get("FOOTER_ADD8"));
        if (!FOOTER_ADD8.equals("")) {
            row = newRow();
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD8, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOOTER_ADD9 = util.nvl((String)pageDtl.get("FOOTER_ADD9"));
        if (!FOOTER_ADD9.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD9, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOOTER_ADD10 = util.nvl((String)pageDtl.get("FOOTER_ADD10"));
        if (!FOOTER_ADD10.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD10, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOOTER_ADD11 = util.nvl((String)pageDtl.get("FOOTER_ADD11"));
        if (!FOOTER_ADD11.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD11, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        String FOOTER_ADD12 = util.nvl((String)pageDtl.get("FOOTER_ADD12"));
        if (!FOOTER_ADD12.equals("")) {
            row = newRow();
            cell = row.createCell(++cellCt);
            cell.setCellValue(getStr(FOOTER_ADD12, fontDataBold));
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols));
        }
        row = newRow();
        row = newRow();
        row = newRow();
        cell = row.createCell(++cellCt);
        cell.setCellValue(getStr("Submitted By:____________________________",
                                 fontDataBold));
        cell.setCellStyle(styleCntr);
        sheet.addMergedRegion(merge(rowCt, rowCt, 0, ttlCols - 6));
        
        cell = row.createCell(ttlCols - 5);
        cell.setCellValue(getStr("Accepted  By:______________________________",
                                 fontDataBold));
        cell.setCellStyle(styleRght);
        sheet.addMergedRegion(merge(rowCt, rowCt, ttlCols - 5,
                                    ttlCols));
        sheet.autoSizeColumn((short)1);
        sheet.autoSizeColumn((short)4);
        sheet.autoSizeColumn((short)(ttlCols - 1));
        sheet.autoSizeColumn((short)ttlCols);

    }
    public HSSFRow newRow() {
        return newRow(++rowCt);
    }

    public HSSFRow newRow(int rownum) {
        HSSFRow lrow = sheet.createRow(rownum);
        cellCt = -1;
        return lrow;
    }

    public CellRangeAddress merge(int fRow, int lRow, int fCol, int lCol) {
        CellRangeAddress cra = new CellRangeAddress(fRow, lRow, fCol, lCol);
        return cra;
    }

    public HSSFRichTextString getStr(String val, HSSFFont font) {
        HSSFRichTextString richString = new HSSFRichTextString(val);
        richString.applyFont(font);
        return richString;
    }

    public HSSFRichTextString getStr(String val) {
        return getStr(val, fontData);
    }

    public String getAmount(String ttlamt, String xrt) {
        String val = "";
        if (!ttlamt.equals("") && !xrt.equals("")) {
            val =String.valueOf(new BigDecimal(ttlamt).multiply(new BigDecimal(xrt)).setScale(2, RoundingMode.HALF_EVEN));
        }
        return val;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>SaveToXL</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a POST. This is the reply.</p>");
        out.println("</body></html>");
        out.close();
    }
}
