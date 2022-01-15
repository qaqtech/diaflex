package ft.com;

import ft.com.dao.GenDAO;
import ft.com.dao.GtPktDtl;
import ft.com.dao.UIForms;
import ft.com.marketing.SearchQuery;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import java.net.InetSocketAddress;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.ArrayList;

import java.util.Hashtable;

import java.util.LinkedHashMap;
import java.util.Set;

import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import net.spy.memcached.MemcachedClient;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

//import org.jfree.data.time.Day;

public class ExcelUtil {
    DBMgr   db;
    InfoMgr info;
    DBUtil  util;
    HttpSession session ;
    GtMgr  gtMgr;
    int sheetCtr = 0 ;
    int rowCt = 0;
    int cellCt = -1;
    int ttlCols = 10; 
    int pSrt = 0 ;
    
    String tblNm = "" ;
    HSSFFont 
        fontHdr = null,
        fontHdrWh = null,
        fontData = null,
        fontDataBig=null,
        fontDataBold = null,
        fontDataBoldWh = null,hlink_font=null,fontData_O=null,fontData_G=null,fontData_R=null,fontData_M=null,fontData_B=null,fontData_P=null,fontData_blk=null;
      
    HSSFCellStyle 
        styleTopBrdr = null,
        styleDataBrdr = null,
        styleHdr = null,
        styleHdrBk = null,
        styleCntr = null,
        styleRght = null,
        styleCentBold = null,
        styleData = null,
        styleRghtBold = null,
        styleBig=null,
        styleBold =null,
        numStyleDataBold = null,
        styleDataRed =null,styleDataGN=null, hlink_style =null,numStyle=null,styleData_O=null,styleData_G=null,styleData_R=null,styleData_M=null,styleData_B=null,styleData_P=null,styleData_blk=null,
        numStyle_O=null,numStyle_G=null,numStyle_R=null,numStyle_M=null,numStyle_B=null,numStyle_P=null,numStyle_blk=null;
    HSSFWorkbook hwb = null;
    HSSFSheet sheet = null;
    HSSFRow row = null ;
    HSSFCell cell = null;
    TreeSet autoColums = null;
    public ExcelUtil() {
        super();
    }
    
    public void init(DBMgr db, DBUtil util, InfoMgr info) {
        this.db   = db;
        this.util = util;
        this.info = info;
    }
    public HSSFWorkbook ConatctExcel(HttpServletRequest req){
          hwb = new HSSFWorkbook();
        autoColums = new TreeSet();
        session = req.getSession(false);
        String fontNm = "Cambria";
        fontHdr = hwb.createFont();
        fontHdr.setFontHeightInPoints((short)11);
        fontHdr.setFontName(fontNm);
        fontHdr.setColor(HSSFColor.BLACK.index);
        fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
        
        styleHdr = hwb.createCellStyle();
        styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleHdr.setFont(fontHdr);
      
        
        fontData = hwb.createFont();
        fontData.setFontHeightInPoints((short)10);
        fontData.setFontName(fontNm);
        
        styleData = hwb.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setFont(fontData);
        
        ArrayList repForms = (ArrayList)req.getAttribute("selectRepForms");
        HashMap headerMap = (HashMap)req.getAttribute("headerMap");
        HashMap pktDtlMap = (HashMap)req.getAttribute("pktDtlMap");
        ArrayList nmeIdnList = (ArrayList)req.getAttribute("nmeIdnList");
        sheet = addSheet();
        row = newRow();
     
        if(repForms!=null && repForms.size()>0){
            for(int i=0; i < repForms.size(); i++){
             String formNme = (String)repForms.get(i);
             String formDsc = util.nvl((String)headerMap.get(formNme+"_TTL"));
             formDsc = formDsc.replaceAll("~nme", "");
             ArrayList formVals = (ArrayList)headerMap.get(formNme+"_VAL");
             int fldCnt = formVals.size()-1;
             int currCell = ++cellCt;
            CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell+fldCnt);
            addMergeCell(currCell, formDsc, styleHdr, rang);
            cellCt = currCell+fldCnt;
            }
            row = newRow();
            for(int i=0; i < repForms.size(); i++){
             
            String formNme = (String)repForms.get(i);
             ArrayList formDsc = (ArrayList)headerMap.get(formNme+"_DSC");
                for(int j=0;j<formDsc.size();j++){
                    String fldDsc = util.nvl((String)formDsc.get(j));
                    addCell(++cellCt, fldDsc, styleHdr);
                }
            }
            sheet.createFreezePane(0, rowCt+1);
            for(int i=0;i < nmeIdnList.size();i++){
                String nmeIdn =util.nvl((String)nmeIdnList.get(i));
                if(nmeIdn.equals("1312")){
                    String abc="";
                }
                int maxSize = 0;
                for(int j=0; j < repForms.size(); j++){
                 String formNme = (String)repForms.get(j);
                 ArrayList pktList = (ArrayList)pktDtlMap.get(formNme+"_"+nmeIdn);
                    if(pktList!=null && pktList.size()>0){
                        if(maxSize < pktList.size())
                            maxSize = pktList.size();
                    }
                    
                }
                
              for(int r=0; r < maxSize; r++){
                   row = newRow();
                   for(int j=0; j < repForms.size(); j++){
                    String formNme = (String)repForms.get(j);
                    ArrayList formVals = (ArrayList)headerMap.get(formNme+"_VAL");
                    ArrayList pktList = (ArrayList)pktDtlMap.get(formNme+"_"+nmeIdn);
                       if(pktList!=null && pktList.size()>0 ){
                                                       if(!formNme.equals("countryfrm")){

                                             if(pktList.size() > r){
                                                           GenDAO lDao = (GenDAO)pktList.get(r);
                                                           for(int m=0 ; m < formVals.size();m++){
                                                               String fldKey = util.nvl((String)formVals.get(m));
                                                               String fldVal = util.nvl((String)lDao.getValue(fldKey));
                                                               addCell(++cellCt,fldVal , styleData);
                                                           }
                                                       }else{
                                                            for(int m=0 ; m < formVals.size();m++){
                                                           addCell(++cellCt,"", styleData);
                                                       }
                                                       }
                                                       }else{
                                                           for(int m=0 ; m < formVals.size();m++){
                                                           addCell(++cellCt,(String)pktList.get(m),
                           styleData);
                                                           }
                                                       }
                           }else{
                           for(int m=0 ; m < formVals.size();m++){
                               addCell(++cellCt,"", styleData);
                           }
                       }
                   }
                
               }
            }
            Iterator it=autoColums.iterator();
            while(it.hasNext()) {
            String value=(String)it.next();
            int colId = Integer.parseInt(value);
            sheet.autoSizeColumn((short)colId, true);
            }
        }
        
     
        return hwb;
    }
   
    public HSSFWorkbook GetLabComparisionExcel(HttpServletRequest req){
        String sqlstr="";
        ArrayList labRSList =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LabRSLst") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LabRSLst"); 
        int lotIndx = labRSList.indexOf("LOT_NO");
            if(lotIndx!=-1){
            lotIndx++;
            sqlstr = " , prp_00"+lotIndx;
            }
            int fctId = labRSList.indexOf("FACTORY_ID");
            if(fctId!=-1){
                fctId++;
            sqlstr = sqlstr+ " , prp_00"+fctId;
            }
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        ArrayList  stockIdnList = new ArrayList();
        ArrayList  stockPtkList = new ArrayList();
        HashMap stockMap = new HashMap();
        HashMap stockPrpUpd = new HashMap();
        String labcomSql = "select a.mprp , a.iss_val , a.rtn_val , a.iss_num , a.rtn_num ,a.txt , a.iss_stk_idn ,c.srch_id issueID, c.vnm "+sqlstr+" from iss_rtn_prp a , rep_prp b , gt_srch_rslt c where b.mdl='LAB_RS' and a.mprp = b.prp " + 
        "and a.iss_id= c.srch_id and a.iss_stk_idn = c.stk_idn and b.flg='Y'  and c.flg='M'  " + 
        "order by a.iss_stk_idn ";

        ArrayList outLst = db.execSqlLst("labCom", labcomSql, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        String pstkIdn = "";
        try {
            while (rs.next()) {
                String lstkIdn =util.nvl(rs.getString("iss_stk_idn"));
                if(pstkIdn=="")
                pstkIdn = lstkIdn;
                
                if(!pstkIdn.equals(lstkIdn)){
                    stockMap.put(pstkIdn, stockPrpUpd);
                    stockIdnList.add(pstkIdn);
                    stockPrpUpd = new HashMap();
                    pstkIdn = lstkIdn;
                }
                
               
                String lprp = util.nvl(rs.getString("mprp"));
                String issVal = util.nvl(rs.getString("iss_val"));
                String rtnVal = util.nvl(rs.getString("rtn_val"));
                String dataTyp = util.nvl((String)mprp.get(lprp + "T"));
                if (dataTyp.equals("N")) {
                    issVal = util.nvl(rs.getString("iss_num"));
                    rtnVal = util.nvl(rs.getString("rtn_num"));
                }
                if (dataTyp.equals("T")) {
                    issVal = util.nvl(rs.getString("txt"));
                    rtnVal = util.nvl(rs.getString("txt"));
                }
                stockPrpUpd.put(lprp+"_IS", issVal);
                stockPrpUpd.put(lprp+"_RT", rtnVal);
                stockPrpUpd.put("issueId", util.nvl(rs.getString("issueID")));
                stockPrpUpd.put("vnm", util.nvl(rs.getString("vnm")));
                if(lotIndx!=-1)
                    stockPrpUpd.put("LOT_NO_IS", util.nvl(rs.getString("prp_00"+lotIndx)));
                 if(fctId!=-1)
                     stockPrpUpd.put("FACTORY_ID_IS", util.nvl(rs.getString("prp_00"+fctId)));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        if(!pstkIdn.equals("")){
            stockMap.put(pstkIdn, stockPrpUpd);
            stockIdnList.add(pstkIdn);
           }
        if(stockIdnList.size()>0){
            
      
            hwb = new HSSFWorkbook();
            String fontNm = "Cambria";
            fontHdr = hwb.createFont();
            fontHdr.setFontHeightInPoints((short)10);
            fontHdr.setFontName(fontNm);
            fontHdr.setColor(HSSFColor.BLACK.index);
            fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
            
             fontDataBoldWh = hwb.createFont();
             fontDataBoldWh.setFontHeightInPoints((short)10);
             fontDataBoldWh.setFontName(fontNm);
             fontDataBoldWh.setColor(HSSFColor.WHITE.index);
             fontDataBoldWh.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
            
            styleHdr = hwb.createCellStyle();
            styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
          
            styleHdr.setFont(fontHdr);
            
            styleHdr.setBorderBottom((short)1);
            styleHdr.setBorderTop((short)1);
            styleHdr.setBorderLeft((short)1);
            styleHdr.setBorderRight((short)1);
             
            fontData = hwb.createFont();
            fontData.setFontHeightInPoints((short)10);
            fontData.setFontName(fontNm);
            
            
            styleData = hwb.createCellStyle();
            styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleData.setFont(fontData);
           
             styleData.setBorderBottom((short)1);
             styleData.setBorderTop((short)1);
             styleData.setBorderLeft((short)1);
             styleData.setBorderRight((short)1);
             
             
             styleDataRed = hwb.createCellStyle();
             styleDataRed.setAlignment(HSSFCellStyle.ALIGN_CENTER);
             styleDataRed.setFillForegroundColor(HSSFColor.RED.index);
             styleDataRed.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             styleDataRed.setFont(fontData);
              styleDataRed.setBorderBottom((short)1);
              styleDataRed.setBorderTop((short)1);
              styleDataRed.setBorderLeft((short)1);
              styleDataRed.setBorderRight((short)1);
              
              
              styleDataGN = hwb.createCellStyle();
              styleDataGN.setAlignment(HSSFCellStyle.ALIGN_CENTER);
              styleDataGN.setFillForegroundColor(HSSFColor.LIME.index);
              styleDataGN.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
              styleDataGN.setFont(fontDataBoldWh);
              styleDataGN.setBorderBottom((short)1);
              styleDataGN.setBorderTop((short)1);
              styleDataGN.setBorderLeft((short)1);
              styleDataGN.setBorderRight((short)1);
            sheet = addSheet();
            row = newRow();
             autoColums = new TreeSet();
            addCell(++cellCt, "sr", styleHdr);
            addCell(++cellCt, "paket code", styleHdr);
            for (int j = 0; j < labRSList.size(); j++) {
                String lprp = (String)labRSList.get(j);
                String lprpDsc = util.nvl((String)mprp.get(lprp+"D"));
                String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
                if(lprpTyp.equals("T")){
                    addCell(++cellCt, lprpDsc, styleHdr);
                }else{
                int currCell = ++cellCt;
               
                CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
                addMergeCell(currCell, lprpDsc, styleHdr, rang);
                ++cellCt;
                }
            }
            
             row = newRow();
             addCell(++cellCt, " ", styleData);
             addCell(++cellCt, " ", styleData);
             for (int j = 0; j < labRSList.size(); j++) {
                 String lprp = (String)labRSList.get(j);
                 String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
                 if(lprpTyp.equals("T")){
                  addCell(++cellCt," ", styleData);
                 }else{
                 addCell(++cellCt,"Issue Value : Assort", styleData);
                 addCell(++cellCt,"Return Value : Lab", styleData);
                 }
             }
             
            for(int i=0; i< stockIdnList.size();i++){
                String stkIdn =(String)stockIdnList.get(i);
                
                HashMap stockPkt = (HashMap)stockMap.get(stkIdn);
                row = newRow();
                addCell(++cellCt, String.valueOf(i+1), styleData);
                addCell(++cellCt, (String)stockPkt.get("vnm"), styleData);
                for (int j = 0; j < labRSList.size(); j++) {
                    HSSFCellStyle cellStyle = styleData;
                    String lprp = (String)labRSList.get(j);
                    String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
                    String issVal = util.nvl((String)stockPkt.get(lprp+"_IS"));
                    String rtnVal = util.nvl((String)stockPkt.get(lprp+"_RT"));
                    int issSrt =0;
                    int rtnSrt =0;
                    if(!(issVal.equals("")) && !(rtnVal.equals(""))){
                        
                      if(lprpTyp.equals("C")){
                          String issValCmp = issVal;
                          String rtnValCmp = rtnVal;
                          issValCmp = issValCmp.replace('+',' ');
                          issValCmp = issValCmp.replace('-',' ');
                          issValCmp = issValCmp.trim();
                              
                         rtnValCmp = rtnValCmp.replace('+',' ');
                         rtnValCmp = rtnValCmp.replace('-',' ');
                         rtnValCmp = rtnValCmp.trim();
                       ArrayList prpSrtLst = (ArrayList)prp.get(lprp+"S");
                       ArrayList prpValLst =(ArrayList)prp.get(lprp+"V");
                       if(prpValLst.indexOf(issValCmp)!=-1)
                        issSrt  = Integer.parseInt((String)prpSrtLst.get(prpValLst.indexOf(issValCmp)));
                      if(prpValLst.indexOf(rtnValCmp)!=-1)
                        rtnSrt = Integer.parseInt((String)prpSrtLst.get(prpValLst.indexOf(rtnValCmp)));
                      if(issSrt < rtnSrt){
                         cellStyle = styleDataRed;
                        
                         }
                      if(issSrt > rtnSrt )
                          cellStyle = styleDataGN;
                        
                      }}
                    if(lprpTyp.equals("T")){
                        addCell(++cellCt, issVal, styleData);
                    }else{
                    addCell(++cellCt,issVal , styleData);
                    addCell(++cellCt, rtnVal, cellStyle);
                    }
                }
                
            }
           
         }
        return hwb;
    }
   
                     public HSSFWorkbook getDataAllInXl(String typ,HttpServletRequest req ,String mdl) {
                                 HashMap pageDtl=util.pagedefXL("GENERAL_FMT","EXCEL");
                                 session = req.getSession(false);
                                     String flg = "M";
                                     if(nvl(typ).equals("SRCH"))
                                      flg = "Z";
                                     if(nvl(typ).equals("LAB"))
                                     flg = "LC";
                                     if(nvl(typ).equals("TMKT"))
                                            flg="S";
                                 ArrayList imagelistDtl =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ImaageDtls") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ImaageDtls");
                                 HashMap dtls=new HashMap();
                                 int rowCt = 0;
                                 short cellCt = -1;
                                 ArrayList ary = new ArrayList();
                                 String byr = util.nvl((String)req.getAttribute("BYRNME"));
                                 String Terms = util.nvl((String)req.getAttribute("TERMS"));
                                 String brk = util.nvl((String)req.getAttribute("BRK"));
                    
                                 String memodate = util.nvl((String)req.getAttribute("MEMODATE"));
                                 String SHEETHED_SMRY =util.nvl((String)pageDtl.get("SHEETHED_SMRY"),"N");
                                 String sumSheet =util.nvl((String)pageDtl.get("SHEET_SMRY"));
                                 String grand =util.nvl((String)pageDtl.get("GRAND"));
                                 String hdrfilter =util.nvl((String)pageDtl.get("HDRFILTER"));
                                 String memoid=util.nvl((String)req.getAttribute("Memoid"));
                                 String ByrNme =util.nvl((String)pageDtl.get("BYRNME"));
                                 String BrkNme =util.nvl((String)pageDtl.get("BRKNME"));
                                 String TermDtl =util.nvl((String)pageDtl.get("TERMDTL"));
                                 String MemoDate =util.nvl((String)pageDtl.get("MEMODATE"));
                                 String SZ_GRP =util.nvl((String)pageDtl.get("SZ_GRP"));
                                 String fontNm = util.nvl((String)pageDtl.get("FONT_NAME"));
                                 String bgcolor = util.nvl((String)pageDtl.get("HEADER_BG"));
                                 String PACKETLOOKSTT = util.nvl((String)req.getAttribute("PACKETLOOKSTT"));
                                 int ADDWIDTH = Integer.parseInt(util.nvl((String)pageDtl.get("ADDWIDTH")));
                                 int fontAddSz = Integer.parseInt(util.nvl((String)pageDtl.get("FONT_SIZE_ADD"),"16"));
                                 int fontSz = Integer.parseInt((String)util.nvl((String)pageDtl.get("FONT_SIZE")));
                                 int fontHDSz = Integer.parseInt((String)util.nvl((String)pageDtl.get("FONT_SIZE_HEADER")));
                                 int fontCOLDSz = Integer.parseInt((String)util.nvl((String)pageDtl.get("FONT_SIZE_COL_HED")));
                                 HSSFWorkbook hwb = new HSSFWorkbook();
                                 HSSFSheet sheet = hwb.createSheet(typ);
//                                 sheet.setColumnWidth(0, 30000);
                                 //CreationHelper createHelper = hwb.getCreationHelper();
                                 HSSFCellStyle hlink_style = hwb.createCellStyle();
                                 HSSFFont hlink_font = hwb.createFont();
                                 hlink_font.setUnderline(HSSFFont.U_SINGLE);
                                 hlink_font.setFontName(fontNm);
                                 hlink_font.setColor(HSSFColor.BLUE.index);
                                 hlink_font.setFontHeightInPoints((short)fontSz);
                                 hlink_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                 hlink_style.setFont(hlink_font);
                                 HSSFFont fontHdr = hwb.createFont();
                                 
                                 if(bgcolor.equals("RED") || bgcolor.equals("BLACK")){
                                 fontHdr.setColor(HSSFColor.WHITE.index);
                                 }else{
                                 fontHdr.setColor(HSSFColor.BLACK.index);
                                 }
                                 
                                 fontHdr.setFontHeightInPoints((short)fontCOLDSz);
                                 fontHdr.setFontName(fontNm);
                                 String COL_HED_BOLD = util.nvl((String)pageDtl.get("COL_HED_BOLD"));
                                 if(COL_HED_BOLD.equals("YES"))
                                 fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
                                     
                                 HSSFCellStyle styleHdr = hwb.createCellStyle();
                                 styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                 if(bgcolor.equals("RED")){
                                     styleHdr.setFillForegroundColor(HSSFColor.RED.index);   
                                     }
                                   if(bgcolor.equals("BLACK")){
                                       styleHdr.setFillForegroundColor(HSSFColor.BLACK.index);   
                                   }
                                 if(bgcolor.equals("SKY_BLUE")){
                                     styleHdr.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
                                     HSSFPalette palette1 = hwb.getCustomPalette();
                                     palette1.setColorAtIndex(HSSFColor.SKY_BLUE.index, (byte)153,(byte)204, (byte)255);
                                 }
                                 styleHdr.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                                 styleHdr.setFont(fontHdr);
                                 styleHdr.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                 styleHdr.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                 styleHdr.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                 styleHdr.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                 styleHdr.setTopBorderColor(HSSFColor.BLACK.index);
                                 styleHdr.setBottomBorderColor(HSSFColor.BLACK.index);
                                 styleHdr.setLeftBorderColor(HSSFColor.BLACK.index);
                                 styleHdr.setRightBorderColor(HSSFColor.BLACK.index);
                                 styleHdr.setWrapText(true);
                                 HSSFFont fontData = hwb.createFont();
                                 fontData.setFontHeightInPoints((short)fontSz);
                                 fontData.setFontName(fontNm);
                                 String DATA_BOLD = util.nvl((String)pageDtl.get("DATA_BOLD"));
                                 if(DATA_BOLD.equals("YES"))
                                 fontData.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                                 
                                     HSSFFont fontDataNormal = hwb.createFont();
                                     fontDataNormal.setFontHeightInPoints((short)fontSz);
                                     fontDataNormal.setFontName(fontNm);
                                     if(DATA_BOLD.equals("YES"))
                                     fontDataNormal.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                                     
                                     HSSFFont fontDataRed = hwb.createFont();
                                     fontDataRed.setFontHeightInPoints((short)fontSz);
                                     fontDataRed.setFontName(fontNm);
                                     fontDataRed.setColor(HSSFColor.RED.index);
                                     if(DATA_BOLD.equals("YES"))
                                     fontDataRed.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                                     
                                     HSSFFont fontDataBlue = hwb.createFont();
                                     fontDataBlue.setFontHeightInPoints((short)fontSz);
                                     fontDataBlue.setFontName(fontNm);
                                     fontDataBlue.setColor(HSSFColor.BLUE.index);
                                     if(DATA_BOLD.equals("YES"))
                                     fontDataBlue.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                                     
                                     HSSFFont fontDataOlive = hwb.createFont();
                                     fontDataOlive.setFontHeightInPoints((short)fontSz);
                                     fontDataOlive.setFontName(fontNm);
                                     fontDataOlive.setColor(HSSFColor.OLIVE_GREEN.index);
                                     if(DATA_BOLD.equals("YES"))
                                     fontDataOlive.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                                     
                                     HSSFFont fontDataGreen = hwb.createFont();
                                     fontDataGreen.setFontHeightInPoints((short)fontSz);
                                     fontDataGreen.setFontName(fontNm);
                                     fontDataGreen.setColor(HSSFColor.GREEN.index);
                                     if(DATA_BOLD.equals("YES"))
                                     fontDataGreen.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                                     
                                     HSSFFont fontDataMaroon= hwb.createFont();
                                     fontDataMaroon.setFontHeightInPoints((short)fontSz);
                                     fontDataMaroon.setFontName(fontNm);
                                     fontDataMaroon.setColor(HSSFColor.MAROON.index);
                                     if(DATA_BOLD.equals("YES"))
                                     fontDataMaroon.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                                     
                                     HSSFFont fontDataviolet= hwb.createFont();
                                     fontDataviolet.setFontHeightInPoints((short)fontSz);
                                     fontDataviolet.setFontName(fontNm);
                                     fontDataviolet.setColor(HSSFColor.VIOLET.index);
                                     if(DATA_BOLD.equals("YES"))
                                     fontDataviolet.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                                     
                                 HSSFCellStyle styleData = hwb.createCellStyle();
                                 styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                 styleData.setFont(fontData);
                                 styleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                 styleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                 styleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                 styleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                 styleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                 styleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                 styleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                 styleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     HSSFCellStyle styleDataNormal = hwb.createCellStyle();
                                     styleDataNormal.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     styleDataNormal.setFont(fontDataNormal);
                                     styleDataNormal.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataNormal.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataNormal.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataNormal.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataNormal.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataNormal.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataNormal.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataNormal.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index); 
                                     HSSFCellStyle styleDataRed = hwb.createCellStyle();
                                     styleDataRed.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     styleDataRed.setFont(fontDataRed);
                                     styleDataRed.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataRed.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataRed.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataRed.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataRed.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataRed.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataRed.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataRed.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index); 
                                     HSSFCellStyle styleDataBlue = hwb.createCellStyle();
                                     styleDataBlue.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     styleDataBlue.setFont(fontDataBlue);
                                     styleDataBlue.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataBlue.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataBlue.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataBlue.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataBlue.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataBlue.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataBlue.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataBlue.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     HSSFCellStyle styleDataOlive = hwb.createCellStyle();
                                     styleDataOlive.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     styleDataOlive.setFont(fontDataOlive);
                                     styleDataOlive.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataOlive.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataOlive.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataOlive.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataOlive.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataOlive.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataOlive.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataOlive.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     HSSFCellStyle styleDataGreen = hwb.createCellStyle();
                                     styleDataGreen.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     styleDataGreen.setFont(fontDataGreen);
                                     styleDataGreen.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataGreen.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataGreen.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataGreen.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataGreen.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataGreen.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataGreen.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataGreen.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index); 
                                     HSSFCellStyle styleDataMaroon = hwb.createCellStyle();
                                     styleDataMaroon.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     styleDataMaroon.setFont(fontDataMaroon);
                                     styleDataMaroon.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataMaroon.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataMaroon.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataMaroon.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataMaroon.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataMaroon.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataMaroon.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataMaroon.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index); 
                                     HSSFCellStyle styleDataviolet = hwb.createCellStyle();
                                     styleDataviolet.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     styleDataviolet.setFont(fontDataviolet);
                                     styleDataviolet.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataviolet.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataviolet.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataviolet.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     styleDataviolet.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataviolet.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataviolet.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleDataviolet.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                 HSSFCellStyle numStyleData = hwb.createCellStyle();
                                 numStyleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                 numStyleData.setFont(fontData);
                                 numStyleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                 numStyleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                 numStyleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                 numStyleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                 numStyleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                 numStyleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                 numStyleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                 numStyleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                 HSSFDataFormat df = hwb.createDataFormat();
                                 if(mdl.equals("MIX_LKUP_XL"))
                                 numStyleData.setDataFormat(df.getFormat("#0.000#"));
                                 else
                                 numStyleData.setDataFormat(df.getFormat("#0.00#"));
                                     HSSFCellStyle numStyleDataNormal = hwb.createCellStyle();
                                     numStyleDataNormal.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     numStyleDataNormal.setFont(fontDataNormal);
                                     numStyleDataNormal.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataNormal.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataNormal.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataNormal.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataNormal.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataNormal.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataNormal.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataNormal.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataNormal.setDataFormat(df.getFormat("#0.00#"));
                                     HSSFCellStyle numStyleDataRed = hwb.createCellStyle();
                                     numStyleDataRed.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     numStyleDataRed.setFont(fontDataRed);
                                     numStyleDataRed.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataRed.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataRed.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataRed.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataRed.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataRed.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataRed.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataRed.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataRed.setDataFormat(df.getFormat("#0.00#"));
                                     HSSFCellStyle numStyleDataBlue = hwb.createCellStyle();
                                     numStyleDataBlue.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     numStyleDataBlue.setFont(fontDataBlue);
                                     numStyleDataBlue.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataBlue.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataBlue.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataBlue.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataBlue.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataBlue.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataBlue.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataBlue.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataBlue.setDataFormat(df.getFormat("#0.00#"));
                                     HSSFCellStyle numStyleDataOlive = hwb.createCellStyle();
                                     numStyleDataOlive.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     numStyleDataOlive.setFont(fontDataOlive);
                                     numStyleDataOlive.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataOlive.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataOlive.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataOlive.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataOlive.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataOlive.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataOlive.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataOlive.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataOlive.setDataFormat(df.getFormat("#0.00#"));   
                                     HSSFCellStyle numStyleDataGreen = hwb.createCellStyle();
                                     numStyleDataGreen.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     numStyleDataGreen.setFont(fontDataGreen);
                                     numStyleDataGreen.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataGreen.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataGreen.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataGreen.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataGreen.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataGreen.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataGreen.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataGreen.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataGreen.setDataFormat(df.getFormat("#0.00#"));            
                                     HSSFCellStyle numStyleDataMaroon = hwb.createCellStyle();
                                     numStyleDataMaroon.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     numStyleDataMaroon.setFont(fontDataMaroon);
                                     numStyleDataMaroon.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataMaroon.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataMaroon.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataMaroon.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataMaroon.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataMaroon.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataMaroon.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataMaroon.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataMaroon.setDataFormat(df.getFormat("#0.00#"));                  
                                     HSSFCellStyle numStyleDataviolet = hwb.createCellStyle();
                                     numStyleDataviolet.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     numStyleDataviolet.setFont(fontDataviolet);
                                     numStyleDataviolet.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataviolet.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataviolet.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataviolet.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataviolet.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataviolet.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataviolet.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataviolet.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataviolet.setDataFormat(df.getFormat("#0.00#")); 
                                 HSSFFont fontDataBig = hwb.createFont();
                                 fontDataBig.setFontHeightInPoints((short)fontSz);
                                 fontDataBig.setFontName(fontNm);
                                 fontDataBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                                 
                                 HSSFCellStyle styleBig = hwb.createCellStyle();
                                 styleBig.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                 styleBig.setFont(fontDataBig);
                                 styleBig.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                 styleBig.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                 styleBig.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                 styleBig.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                 styleBig.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                 styleBig.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                 styleBig.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                 styleBig.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     HSSFCellStyle numStyleDataBoldBig = hwb.createCellStyle();
                                     numStyleDataBoldBig.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     numStyleDataBoldBig.setFont(fontDataBig);
                                     numStyleDataBoldBig.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataBoldBig.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataBoldBig.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataBoldBig.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataBoldBig.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataBoldBig.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataBoldBig.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataBoldBig.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index); 
                                       HSSFDataFormat dfnumbig = hwb.createDataFormat();
                                       numStyleDataBoldBig.setDataFormat(dfnumbig.getFormat("#0.00#"));
                                     HSSFFont fontHeaderDataBig = hwb.createFont();
                                     fontHeaderDataBig.setFontHeightInPoints((short)fontHDSz);
                                     fontHeaderDataBig.setFontName(fontNm);
                                     String FONT_BOLD_HEADER = util.nvl((String)pageDtl.get("FONT_BOLD_HEADER"));
                                     if(FONT_BOLD_HEADER.equals("YES"))
                                     fontHeaderDataBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                                     
                                     HSSFCellStyle styleHeaderBig = hwb.createCellStyle();
                                     styleHeaderBig.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     styleHeaderBig.setFont(fontHeaderDataBig);
                                     styleHeaderBig.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     styleHeaderBig.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     styleHeaderBig.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     styleHeaderBig.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     styleHeaderBig.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleHeaderBig.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleHeaderBig.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     styleHeaderBig.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                 
                                 
                                 HSSFFont fontDataBold = hwb.createFont();
                                 fontDataBold.setFontHeightInPoints((short)fontSz);
                                 fontDataBold.setFontName(fontNm);
                                 String FOOTER_BOLD = util.nvl((String)pageDtl.get("FOOTER_BOLD"));
                                 if(FOOTER_BOLD.equals("YES"))
                                 fontDataBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
                                 HSSFCellStyle styleBold = hwb.createCellStyle();
                                 styleBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                 styleBold.setFont(fontDataBold);
                                 styleBold.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                 styleBold.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                 styleBold.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                 styleBold.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                 styleBold.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                 styleBold.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                 styleBold.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                 styleBold.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     HSSFCellStyle numStyleDataBold = hwb.createCellStyle();
                                     numStyleDataBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     numStyleDataBold.setFont(fontDataBold);
                                     numStyleDataBold.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataBold.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataBold.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataBold.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataBold.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataBold.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataBold.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataBold.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                       HSSFDataFormat dfnum = hwb.createDataFormat();
                                       numStyleDataBold.setDataFormat(dfnum.getFormat("#0.00#"));
                                     HSSFCellStyle numStyleDataBoldleft = hwb.createCellStyle();
                                     numStyleDataBoldleft.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                                     numStyleDataBoldleft.setFont(fontDataBold);
                                     numStyleDataBoldleft.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataBoldleft.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataBoldleft.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataBoldleft.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                                     numStyleDataBoldleft.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataBoldleft.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataBoldleft.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                                     numStyleDataBoldleft.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index); 
                                     fontHdrWh = hwb.createFont();
                                     fontHdrWh.setFontHeightInPoints((short)16);
                                     fontHdrWh.setFontName(fontNm);
                                     fontHdrWh.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
                                     styleHdrBk = hwb.createCellStyle();
                                     styleHdrBk.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     styleHdrBk.setFont(fontHdrWh);
                                     
                                     HSSFFont fontDataBold1 = hwb.createFont();
                                     fontDataBold1.setFontHeightInPoints((short)fontAddSz);
                                     fontDataBold1.setFontName(fontNm);
                                     fontDataBold1.setColor(HSSFColor.MAROON.index);
                                     fontDataBold1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                                     HSSFCellStyle styleBold1 = hwb.createCellStyle();
                                     styleBold1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                     styleBold1.setFont(fontDataBold1);
                                 SortedSet autoCols = null;
                                 HSSFRow row = null ;
                                 HSSFCell cell = null ;
                                 int colNum = 0;
                                 int rowNm = 0;
                                 int raprteInx =0;
                                 int totalCell=0;
                                 int endrow=0;
                    int cz1row=0;
                    double exh_rte=0;
                    String endCellAlp="";
                                 int stCol = ++cellCt, stRow = rowCt;
                                 boolean isSrch = false;
                                 String logoNme =util.nvl((String)pageDtl.get("LOGO"));
                                 String NAME = util.nvl((String)pageDtl.get("NAME"));   
                                 if(!logoNme.equals("")){
                                      try {
                                          String servPath = req.getRealPath("/images/clientsLogo");
                                 
                                           if(servPath.indexOf("/") > -1)
                                           servPath += "/" ;
                                           else {
                                                                 //servPath = servPath.replace('\', '\\');
                                                                 servPath += "\\" ;
                                             }
                                          FileInputStream fis=new FileInputStream(servPath+logoNme);
                                          ByteArrayOutputStream img_bytes = new ByteArrayOutputStream();
                                          int b;
                                          while ((b = fis.read()) != -1)
                                              img_bytes.write(b);
                                          fis.close();
                                          int imgWdt =Integer.parseInt(util.nvl((String)pageDtl.get("IMGWT"),"800"));
                                          int imgHt = Integer.parseInt(util.nvl((String)pageDtl.get("IMGHT"),"185")); 
                                          int imgstRow =Integer.parseInt(util.nvl((String)pageDtl.get("IMGSTROW"),"3"));
                                          int imgstCOl = Integer.parseInt(util.nvl((String)pageDtl.get("IMGSTCOL"),"8")); 
                                          int imgshort = Integer.parseInt(util.nvl((String)pageDtl.get("IMGST"),"5")); 
                                          int rowcount = Integer.parseInt(util.nvl((String)pageDtl.get("IMGSTART"),"1")); 
                                          row = sheet.createRow(rowcount);
                                 
                                          HSSFClientAnchor anchor =
                                              new HSSFClientAnchor(1, 1, imgWdt, imgHt, (short)imgshort, rowcount,
                                                                   (short)(stCol+imgstCOl), (stRow +imgstRow));
                                 
                                          rowCt = stRow + 2;
                                          cellCt = (short)(stCol + 5);
                                          int index =
                                              hwb.addPicture(img_bytes.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG);
                                          HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                                          HSSFPicture logo = patriarch.createPicture(anchor, index);
                                         
                                          anchor.setAnchorType(2);
                                 
                                      } catch (FileNotFoundException fnfe) {
                                          // TODO: Add catch code
                                          fnfe.printStackTrace();
                                      } catch (IOException ioe) {
                                          // TODO: Add catch code
                                          ioe.printStackTrace();
                                      }
                                 
                                 }else if(!NAME.equals("")){
                                 row = sheet.createRow((short)2);
                                 cell = row.createCell(1);
                                 cell.setCellValue(getStr(NAME,fontHdrWh));
                                 cell.setCellStyle(styleHdrBk);
                                 sheet.addMergedRegion(merge(2, 2, 1, 8));
                             }
                                 int addstart = Integer.parseInt(util.nvl((String)pageDtl.get("ADDSTART"),"10")); 
                                 String add1 =util.nvl((String)pageDtl.get("ADD1"));
                                 int addstartrow = Integer.parseInt(util.nvl((String)pageDtl.get("ADDSTARTROW"),"2"));
                                 if(!add1.equals("")){
                                     rowCt++;
                                     if(NAME.equals("")){
                                     row = sheet.createRow(addstartrow);
                                     }
                                           cell = row.createCell(addstart);
                                           cell.setCellValue(add1);
                                           cell.setCellStyle(styleBold1);
                                           sheet.addMergedRegion(merge(addstartrow, addstartrow, addstart, ADDWIDTH));
                                       
                                 }
                                 String add2 =util.nvl((String)pageDtl.get("ADD2"));
                                 if(!add2.equals("")){
                                      if(add1.equals(""))
                                      rowCt++;
                                           addstartrow++;
                                           row = sheet.createRow(addstartrow);
                                           cell = row.createCell(addstart);
                                           cell.setCellValue(add2);
                                           cell.setCellStyle(styleHeaderBig);
                                           sheet.addMergedRegion(merge(addstartrow, addstartrow, addstart, ADDWIDTH));
                                      rowCt++;
                                  }
                                     HashMap mprp = info.getMprp();
                                     HashMap vwDtl = null;
                                     ArrayList colHdr = new ArrayList();
                                     colHdr.add("Sr No.");
//                                     util.StockViewLyt();
                                     HashMap stockViewMap = info.getStockViewMap();
                                     vwDtl = (HashMap)stockViewMap.get("VWIMG"); 
                                     if(vwDtl!=null && !mdl.equals("MIX_VW"))
                                     colHdr.add("PKT");
                                     colHdr.add("PKT");
                                     colHdr.add("Status");
                                     ArrayList vwPrpLst = util.getMemoXl(mdl);
                                     ArrayList prpDspBlocked = info.getPageblockList();
                                         
                                     if(mdl.equals("MIX_VW")){
                                     colHdr.add("Carat");
                                     }
                                         
                                     for (int i = 0; i < vwPrpLst.size(); i++) {
                                     String prp = (String)vwPrpLst.get(i);
                                     String prpDsc = util.nvl((String)mprp.get(prp));
                                     if(prpDsc.equals(""))
                                     prpDsc =prp;
                                     if(prpDspBlocked.contains(prp)){
                                     }else{
                                     colHdr.add(prp);
                                     if (prp.equals("RTE")){
                                     colHdr.add("AMT");
                                     vwDtl = (HashMap)stockViewMap.get("ISCMP");
                                     if(vwDtl!=null){
                                     String byrIdn = util.nvl((String)vwDtl.get("url"));
                                     if(byrIdn.equals(String.valueOf(info.getByrId()))){
                                     colHdr.add("CMP");
                                     }}
                                     
                                     }
                                     if(prp.equals("RAP_DIS")){
                                     //rapDisInx=rapDisInx+addindex;
                                     vwDtl = (HashMap)stockViewMap.get("ISCMPDIS");
                                     if(vwDtl!=null){
                                     //rapDisInx=rapDisInx+1;
                                     //rapDisInx=rapDisInx+addindex+1;
                                     String byrIdn = util.nvl((String)vwDtl.get("url"));
                                     if(byrIdn.equals(String.valueOf(info.getByrId()))){
                                     colHdr.add("CMP_DIS");
                                     }}
                                     }
                                     if(prp.equals("RAP_RTE")){
                                     colHdr.add("RAPVAL");
                                     }
                                     }
                                     }
                                     
                                                     //sheet.autoSizeColumn((short)colNum, true);
                                     int rapRteInx =vwPrpLst.indexOf("RAP_RTE");
                                     int crtWtInx = vwPrpLst.indexOf("CRTWT");
                                     int rteInx = vwPrpLst.indexOf("RTE");
                                     int rapDisInx = vwPrpLst.indexOf("RAP_DIS");
                                     if(rapRteInx<=0 && mdl.indexOf("MIX_VW")!=-1){
                                     colHdr.add("RAP_RTE");                
                                     }
                                     if(rapRteInx<=0 &&  mdl.indexOf("MIX_VW")!=-1){
                                     colHdr.add("RAPVAL");
                                     }
                                     int ctwtCol=colHdr.indexOf("CRTWT");
                                     int rapVluCol=colHdr.indexOf("RAPVAL");
                                     int amtCol=colHdr.indexOf("AMT");
                                     int rteCol=colHdr.indexOf("RTE");
                                     int raprteCol=colHdr.indexOf("RAP_RTE");
                                     int rapDisCol = colHdr.indexOf("RAP_DIS");
                                     int fnTtlRow = 0;
                                     int fnTtlCell=0;
                                     try {
                                     
                                         
                               
                                     ary = new ArrayList();
                                     ary.add(flg);
                                     int pcrowcnt=0;
                                     
                                     String gtView = "select count(*) cnt from gt_srch_rslt where flg=? ";
                    ArrayList outLst = db.execSqlLst("gtView", gtView,ary);
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs1 = (ResultSet)outLst.get(1);
                                     while (rs1.next()) {
                                         pcrowcnt=rs1.getInt("cnt");
                                     }
                                        
                    rs1.close();
                    pst.close();
                                     if(!add1.equals(""))
                                     pcrowcnt++;
                                     if(!add2.equals(""))
                                     pcrowcnt++;
                                     pcrowcnt+=(rowCt+1);
                                     if(ByrNme.equals("YES") && !byr.equals(""))
                                         pcrowcnt+=2;
                                     if(!memoid.equals(""))
                                         pcrowcnt++;
                                    if(!brk.equals(""))
                                      pcrowcnt++;
                                   if(!Terms.equals(""))
                                       pcrowcnt++;
                                     if(grand.equals("Y") && !mdl.equals("MIX_VW"))
                                         pcrowcnt++;
                                     if(sumSheet.equals("YES"))
                                         pcrowcnt+=4;
                                         pcrowcnt++;
                                        rowCt++;
                                     row = sheet.createRow((short)++rowCt);
                                     cellCt = (short)2;
                                     cell = row.createCell(cellCt);
                                     cell.setCellValue("");
                                     cell.setCellStyle(styleHdr);
                                     
                                     cell = row.createCell(++cellCt);
                                     cell.setCellValue("Pcs");
                                     cell.setCellStyle(styleHdr);
                                     
                                     if(crtWtInx!=-1){
                                     cell = row.createCell(++cellCt);
                                     cell.setCellValue("Carat");
                                     cell.setCellStyle(styleHdr);
                                     }
                                     if(colHdr.indexOf("RAPVAL")>-1  && !mdl.equals("MIX_VW")) {
                                     cell = row.createCell(++cellCt);
                                     cell.setCellValue("Rap Vlu($)");
                                     cell.setCellStyle(styleHdr);
                                     }
                                     if(rapDisInx>0){
                                     cell = row.createCell(++cellCt);
                                     cell.setCellValue("Rap%");
                                     cell.setCellStyle(styleHdr);
                                     }
                                     if(rteInx>0){ 
                                     cell = row.createCell(++cellCt);
                                     cell.setCellValue("PR/ CT($)");
                                     cell.setCellStyle(styleHdr);
                                     }
                                     if(rteInx>0){
                                     cell = row.createCell(++cellCt);
                                     cell.setCellValue("Amount($)");
                                     cell.setCellStyle(styleHdr);
                                     }
                                         fnTtlRow = rowCt+1;
                                         
                                         row = sheet.createRow((short)++rowCt);
                                          cellCt = (short)2;
                                          cell = row.createCell(cellCt);
                                          cell.setCellValue("Total");
                                          cell.setCellStyle(styleHdr);

                                          
                                          cell = row.createCell(++cellCt);
                                          
                                          cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                                          cell.setCellValue("");
                                          cell.setCellStyle(styleBig);
                                         fnTtlCell = cellCt+1;
                                          if(crtWtInx!=-1){
                                          cell = row.createCell(++cellCt);
                                              cell.setCellValue("");
                                          cell.setCellStyle(numStyleDataBoldBig);
                                        
                                          }
                                          if(colHdr.indexOf("RAPVAL")>-1  && !mdl.equals("MIX_VW")) {
                                          cell = row.createCell(++cellCt);
                                              cell.setCellValue("");
                                          cell.setCellStyle(numStyleDataBoldBig);
                                         
                                          }
                                          if(rapDisInx>0){
                                          cell = row.createCell(++cellCt);
                                              cell.setCellValue("");
                                          cell.setCellStyle(numStyleDataBoldBig);
                                         
                                          }
                                          if(rteInx>0){ 
                                          cell = row.createCell(++cellCt);
                                              cell.setCellValue("");
                                          cell.setCellStyle(numStyleDataBoldBig);
                                          
                                          }
                                          if(rteInx>0){
                                          cell = row.createCell(++cellCt);
                                              cell.setCellValue("");
                                          cell.setCellStyle(numStyleDataBoldBig);
                                        
                                          cell = row.createCell(++cellCt);
                                          }
                                     
                                         row = sheet.createRow((short)++rowCt);
                                          cellCt = (short)2;
                                          cell = row.createCell(cellCt);
                                          cell.setCellValue("Sub Total");
                                          cell.setCellStyle(styleHdr);

                                          
                                          cell = row.createCell(++cellCt);
                                          
                                        
                                          cell.setCellValue("");
                                          cell.setCellStyle(styleBig);
                                          
                                          if(crtWtInx!=-1){
                                          cell = row.createCell(++cellCt);
                                              cell.setCellValue("");
                                          cell.setCellStyle(numStyleDataBoldBig);
                                        
                                          }
                                          if(colHdr.indexOf("RAPVAL")>-1  && !mdl.equals("MIX_VW")) {
                                          cell = row.createCell(++cellCt);
                                              cell.setCellValue("");
                                          cell.setCellStyle(numStyleDataBoldBig);
                                        
                                          }
                                          if(rapDisInx>0){
                                          cell = row.createCell(++cellCt);
                                              cell.setCellValue("");
                                          cell.setCellStyle(numStyleDataBoldBig);
                                         
                                          }
                                          if(rteInx>0){ 
                                          cell = row.createCell(++cellCt);
                                              cell.setCellValue("");
                                          cell.setCellStyle(numStyleDataBoldBig);
                                       
                                          }
                                          if(rteInx>0){
                                          cell = row.createCell(++cellCt);
                                              cell.setCellValue("");
                                          cell.setCellStyle(numStyleDataBoldBig);   
                                          cell = row.createCell(++cellCt);
                                          }
                                      
                                     
                                   
                                   if(ByrNme.equals("YES")){
                                     if(!byr.equals("")){
                                     row = sheet.createRow((short)++rowCt);

                                     cell = row.createCell(0);
                                     cell.setCellValue("Buyer");
                                     cell.setCellStyle(styleHeaderBig);
                                     colNum = cell.getColumnIndex();
                                     sheet.addMergedRegion(merge(rowCt, rowCt, 12, 13));

                                     cell = row.createCell(1);
                                     cell.setCellValue(byr);
                                     cell.setCellStyle(styleHeaderBig);
                                     colNum = cell.getColumnIndex();
                                     sheet.addMergedRegion(merge(rowCt, rowCt, 14, 15));
                                     
                                
                                     }
                                }
                    if(BrkNme.equals("YES") && !brk.equals("")){
                    row = sheet.createRow((short)++rowCt);

                    cell = row.createCell(0);
                    cell.setCellValue("Broker");
                    cell.setCellStyle(styleHeaderBig);
                    colNum = cell.getColumnIndex();
                    sheet.addMergedRegion(merge(rowCt, rowCt, 12, 13));

                    cell = row.createCell(1);
                    cell.setCellValue(brk);
                    cell.setCellStyle(styleHeaderBig);
                    colNum = cell.getColumnIndex();
                    sheet.addMergedRegion(merge(rowCt, rowCt, 14, 19));
                    
                    
                    }
                                         
                    if(TermDtl.equals("YES") &&!Terms.equals("")){
                    row = sheet.createRow((short)++rowCt);

                    cell = row.createCell(0);
                    cell.setCellValue("Terms");
                    cell.setCellStyle(styleHeaderBig);
                    colNum = cell.getColumnIndex();
                    sheet.addMergedRegion(merge(rowCt, rowCt, 12, 13));

                    cell = row.createCell(1);
                    cell.setCellValue(Terms);
                    cell.setCellStyle(styleHeaderBig);
                    colNum = cell.getColumnIndex();
                    sheet.addMergedRegion(merge(rowCt, rowCt, 14, 17));
                   
                    
                    }
                                   if(!memoid.equals("")){
                                   //                     int rct=rowCt++;
                                   row = sheet.createRow((short)++rowCt);

                                   cell = row.createCell(0);
                                   cell.setCellValue("Memo Id");
                                   cell.setCellStyle(styleHeaderBig);
                                   colNum = cell.getColumnIndex();
                                   sheet.addMergedRegion(merge(rowCt, rowCt, 12, 13));

                                   cell = row.createCell(1);
                                   cell.setCellValue(memoid +"|"+ memodate);
                                   cell.setCellStyle(styleHeaderBig);
                                   colNum = cell.getColumnIndex();
                                   sheet.addMergedRegion(merge(rowCt, rowCt, 14, 15));
                                   }
                                         
                   
                                         
                   
                                 autoCols = new TreeSet();
                                 if(vwPrpLst==null)
                                     util.initSrch();
                                   String cmpdisfromprp = util.nvl((String)pageDtl.get("CMPDIS"));

                                     int shInx = vwPrpLst.indexOf("SH");
                                     int ct =0;
                                 
                                     ct = db.execCall("ud_dsp_stt", "pkgmkt.upd_dsp_stt", new ArrayList());
                                 
                                     String updGrpSzQ = " Update gt_srch_rslt set srt_070 = get_grp_sz(cts) where cts is not null and get_grp_sz(cts) <> 'NA' ";
                                      ct = db.execUpd(" Upd GT Grp sz", updGrpSzQ, new ArrayList());

                                 
                                 
                                 
                                  String exhRte = util.nvl((String)req.getAttribute("exhRte"));
                                 if(exhRte.equals(""))
                                     exhRte="1";
                                         
                 
                                     String imageDir =util.nvl((String)pageDtl.get("IMG_DIR"));
                     //                     String imageDir = "E:/websites/kapugemsonline/";
                                            //  String imageDir = "E:\\";

                                String cpPrp = "prte";
                                if(vwPrpLst.contains("CP"))
                                cpPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("CP")+1);
                                String prirapPrp = "prte";
                                if(vwPrpLst.contains("PRI_RAP_RTE"))
                                prirapPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("PRI_RAP_RTE")+1);
                                String netpurPrp = "prte";
                                if(vwPrpLst.contains("NET_PUR_RTE"))
                                netpurPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("NET_PUR_RTE")+1);
                                String showRtePrp = "prte";
                                if(vwPrpLst.contains("SHOW_RTE"))
                                showRtePrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("SHOW_RTE")+1);       
                                
                                 String getQuot = "quot rte";
                                 String grpOrder = "";
                                   String srchQ =
                                     " select sk1, cts crtwt, "+
                                     //decode(quot, 0, 0, decode(nvl(rap_dis,0), 0, 101, trunc(((quot*100)/greatest(rap_rte,100)) - 100,2))) rr_dis, "+
                                     " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis " +
                                     " , stk_idn, rap_rte , vnm, kts_vw kts, cert_lab cert, cert_no, rmk, dsp_stt , img,img2,  cmp , trunc(((cmp*100)/greatest(rap_rte,1)) - 100,2) cmp_dis , to_char(trunc(cts,2),'99999990.00') cts, "+ getQuot
                                     +", rap_rte, trunc(cts,2)*rap_Rte rap_vlu "
                                     +", rmk ,exh_rte, cmnt cmnt ,to_char(trunc(cts,2) * quot, '99999990.00') amt, stt stt1, decode(stt, 'MKAV', 'Available','MKKS_IS','MEMO','MKOS_IS','MEMO', 'MKIS', 'MEMO', 'MKTL',' ', 'SOLD') stt,decode(stt,'MKAP','O','MKPP','G','MKWA','G','MKWH','R','MKLB','M','MKAV',decode(instr(upper(dsp_stt), 'NEW'), 0, ' ', 'B'),'MKIS','R','MKEI','R','LB_PRI','P','LB_IS','P','') color,diamondImg,jewImg,srayImg,agsImg,mrayImg,ringImg,lightImg,refImg,videos,certImg,video_3d, nvl("+cpPrp+",prte) prte , nvl(nvl("+cpPrp+",prte),0)*nvl(cts,0) cptotal ,trunc(((greatest(nvl("+cpPrp+",prte),1)*100)/greatest(rap_rte,1)) - 100,2) cpdis, nvl("+prirapPrp+",prte) priraprte ,trunc(((greatest(nvl("+prirapPrp+",prte),1)*100)/greatest(rap_rte,1)) - 100,2) prirapdis,nvl(nvl("+netpurPrp+",prte),0)*nvl(cts,0) netpurtotal,nvl(nvl("+showRtePrp+",prte),0)*nvl(cts,0)showtotal,trunc(((greatest(nvl("+netpurPrp+",prte),1)*100)/greatest(rap_rte,1)) - 100,2) netpurdis " ;
                                 if(shInx!=-1 && SZ_GRP.equals("Y")){
                                   grpOrder = util.nvl((String)pageDtl.get("ORDER"));
                                     shInx = shInx+1;
                                     String prpSh = "srt_00"+shInx;
                                     if(shInx > 9)
                                         prpSh = "srt_0"+shInx;
                                   grpOrder = grpOrder.replace("LAB", "cert_lab");
                                   grpOrder = grpOrder.replace("SH", prpSh);
                                 
                                   grpOrder = grpOrder.replace("SIZE", "srt_070");
                                     srchQ =" select sk1, cts crtwt, "+
                                                        //decode(quot, 0, 0, decode(nvl(rap_dis,0), 0, 101, trunc(((quot*100)/greatest(rap_rte,100)) - 100,2))) rr_dis, "+
                                        " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis " +
                                        " , stk_idn, rap_rte , vnm, kts_vw kts, cert_lab cert, cert_no, rmk, dsp_stt , img,img2,  cmp , trunc(((cmp*100)/greatest(rap_rte,1)) - 100,2) cmp_dis , decode(PKT_TY,'NR',trunc(cts,2),trunc(cts,3))  cts, "+ getQuot
                                       +", rap_rte, trunc(cts,2)*rap_Rte rap_vlu "
                                       +", rmk ,exh_rte, cmnt cmnt ,to_char(trunc(cts,2) * quot, '99999990.00') amt, stt stt1, decode(stt, 'MKAV', 'Available','MKKS_IS','MEMO','MKOS_IS','MEMO', 'MKIS', 'MEMO', 'MKTL',' ', 'SOLD') stt,decode(stt,'MKAP','O','MKPP','G','MKWA','G','MKWH','R','MKLB','M','MKAV',decode(instr(upper(dsp_stt), 'NEW'), 0, ' ', 'B'),'MKIS','R','MKEI','R','LB_PRI','P','LB_IS','P','') color,diamondImg,jewImg,srayImg,agsImg,mrayImg,ringImg,lightImg,refImg,videos,certImg,video_3d  "
                                     +", sum(trunc(cts,2)) over(partition by "+grpOrder+" ) grp_cts "
                                      +", sum(trunc(cts,2)*quot) over(partition by "+grpOrder+" ) grp_vlu "
                                     +", trunc( (sum(trunc(cts,2)*quot) over(partition by "+grpOrder+" ))/ sum(trunc(cts, 2)) over(partition by "+grpOrder+"),2) grp_avg_vlu "
                                       +", trunc( (sum(trunc(cts,2)*rap_rte ) over(partition by "+grpOrder+"))/sum(trunc(cts, 2)) over(partition by "+grpOrder+"),2) grp_rap_vlu "
                                       +", trunc((sum(trunc(cts,2)*(quot)) over(partition by "+grpOrder+")*100/sum(trunc(cts,2)*greatest(rap_rte,1) ) over(partition by "+grpOrder+")) - 100,2) grp_avg_dis ";
                                                                         
                                               
                                 }
                                 
                     //                rowCt=rowCt++;
                     cz1row = rowCt;
                                 row = sheet.createRow((short)++rowCt);
                                 row.setHeightInPoints(15f);
                                 cellCt = -1;
                                  cell = row.createCell(++cellCt);
                                  cell.setCellValue("Sr No");
                                  cell.setCellStyle(styleHdr);
                                  colNum = cell.getColumnIndex(); 
                                 autoCols.add(Integer.toString(colNum));
                                     //sheet.autoSizeColumn((short)colNum, true);
                                     vwDtl = (HashMap)stockViewMap.get("VWIMG"); 
                                         if(vwDtl!=null && !mdl.equals("MIX_VW")){
                                     
                                  cell = row.createCell(++cellCt);
                                     cell.setCellValue("View");
                                     cell.setCellStyle(styleHdr);
                                     colNum = cell.getColumnIndex();
                                     autoCols.add(Integer.toString(colNum));
                                     //sheet.autoSizeColumn((short)colNum, true);
                                       
                                     }
                                 
                                 cell = row.createCell(++cellCt);
                                 cell.setCellValue("Packet Id");
                                 cell.setCellStyle(styleHdr);
                                 colNum = cell.getColumnIndex(); 
                                 autoCols.add(Integer.toString(colNum));
                                 //sheet.autoSizeColumn((short)colNum, true);
                                 
                                  cell = row.createCell(++cellCt);
                                  cell.setCellValue("Status");
                                  cell.setCellStyle(styleHdr);
                                  colNum = cell.getColumnIndex(); 
                                  autoCols.add(Integer.toString(colNum));
                                     
                                     if(mdl.equals("MIX_VW")){
                                     cell = row.createCell(++cellCt);
                                     cell.setCellValue("Carat");
                                     colHdr.add("Carat");
                                     cell.setCellStyle(styleHdr);
                                     colNum = cell.getColumnIndex(); 
                                     autoCols.add(Integer.toString(colNum));
                                     //sheet.autoSizeColumn((short)colNum, true);
                                     }
                                 
                                 //sheet.autoSizeColumn((short)colNum, true);

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
                                 
                                 String prp = (String)vwPrpLst.get(i);
                                 String prpDsc = util.nvl((String)mprp.get(prp));
                                 if(prpDsc.equals(""))
                                     prpDsc =prp;
                                     if(prpDspBlocked.contains(prp)){
                                     }else{
                                         cell = row.createCell(++cellCt);
                                         cell.setCellValue(prpDsc);
                                         cell.setCellStyle(styleHdr);
                                         colNum = cell.getColumnIndex();
                                         autoCols.add(Integer.toString(colNum));
                                 
                                 if (prp.equals("RTE")){
                                     cell = row.createCell(++cellCt);
                                     cell.setCellValue("Amount");
                                     cell.setCellStyle(styleHdr);
                                     colNum = cell.getColumnIndex();
                                     autoCols.add(Integer.toString(colNum));
                                     
                                     vwDtl = (HashMap)stockViewMap.get("ISCMP");
                                      if(vwDtl!=null){
                                       
                                          
                                       String byrIdn = util.nvl((String)vwDtl.get("url"));
                                          if(byrIdn.equals(String.valueOf(info.getByrId()))){
                                          cell = row.createCell(++cellCt);
                                          cell.setCellValue("CMP");
                                          cell.setCellStyle(styleHdr);
                                          colNum = cell.getColumnIndex();
                                          autoCols.add(Integer.toString(colNum));
                                      }}
                                     
                                 }
                                 if(prp.equals("RAP_DIS")){
                                     //rapDisInx=rapDisInx+addindex;
                                     vwDtl = (HashMap)stockViewMap.get("ISCMPDIS");
                                      if(vwDtl!=null){
                                          //rapDisInx=rapDisInx+1;
                                          //rapDisInx=rapDisInx+addindex+1;
                                       String byrIdn = util.nvl((String)vwDtl.get("url"));
                                          if(byrIdn.equals(String.valueOf(info.getByrId()))){
                                          cell = row.createCell(++cellCt);
                                          cell.setCellValue("CMP_DIS");
                                          cell.setCellStyle(styleHdr);
                                          colNum = cell.getColumnIndex();
                                          autoCols.add(Integer.toString(colNum));
                                      }}
                                     }
                                       if(prp.equals("RAP_RTE")){
                                          cell = row.createCell(++cellCt);
                                          cell.setCellValue("Rap Vlu");
                                          cell.setCellStyle(styleHdr);
                                          colNum = cell.getColumnIndex();
                                          autoCols.add(Integer.toString(colNum));
                                      }
                                   }
                                     endCellAlp=GetAlpha(cellCt+1);
                                 }
                                         
                                      if(colHdr.indexOf("RAPVAL")==-1  && !mdl.equals("MIX_VW")) {
                                     cell = row.createCell(++cellCt);
                                     cell.setCellValue("Rap Rte");                   
                                     cell.setCellStyle(styleHdr);
                                     colNum = cell.getColumnIndex(); 
                                     autoCols.add(Integer.toString(colNum));
                                     
                                     }
                                       if(colHdr.indexOf("RAPVAL")==-1  && !mdl.equals("MIX_VW")) {
                                     cell = row.createCell(++cellCt);
                                     cell.setCellValue("Rap Vlu");
                                     cell.setCellStyle(styleHdr);
                                     colNum = cell.getColumnIndex(); 
                                     autoCols.add(Integer.toString(colNum));
                                         endCellAlp=GetAlpha(cellCt+1);
                                     }
                                     //Freezws are
                                     String srchQ1=srchQ;
                                 sheet.createFreezePane(0, rowCt+1);
                                         if(!grpOrder.equals("")){
                                           grpOrder = ","+grpOrder;
                                         }
                                     String rsltQ =
                                         srchQ +" "+grpOrder+" from gt_srch_rslt  where flg=? union "+srchQ1+"  "+grpOrder+" from " +
                                         "  gt_srch_multi a  where exists (select 1 from gt_srch_rslt b1 where b1.stk_idn = a.stk_idn and a.flg = ? ) " ;
                                        
                                     String orderBy= "  order by sk1, cts desc ";
                                   if(shInx!=-1 && SZ_GRP.equals("Y")){
                                     grpOrder = grpOrder.replace("CTS", "cts");
                                     shInx = shInx+1;
                                     String prpSh = "srt_00"+shInx;
                                     if(shInx > 9)
                                         prpSh = "srt_0"+shInx;
                                     grpOrder = grpOrder.replaceFirst(",", "");
                                     orderBy= "  order by "+grpOrder+" ,sk1 ";
                                   }
                                   rsltQ = rsltQ+orderBy;
                                     ArrayList numParam = new ArrayList();
                                         numParam.add("CRTWT");
                                         numParam.add("RTE");
                                         numParam.add("RAP_DIS");
                                         numParam.add("RAP_RTE");
                    String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
                                 ary = new ArrayList();
                                 ary.add(flg);
                                 ary.add(flg);
                    outLst = db.execSqlLst("stock", rsltQ, ary);
                    pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs = (ResultSet)outLst.get(1);
                                 String grpCts ="";
                                 String grpVlu = "";
                                 String grpAvgDis ="";
                                 String grpRapVlu = "";
                                 String grpRteVlu = "";
                                 String grpAvgVlue = "";
                                 String grpQty = "";
                                        
                                     stRow = rowCt+1;
                                 int srno=0;
                                 String l_grpCts="";
                                     String l_grpVlu="";
                                     String l_grpAvgVlu ="";
                                     String l_grpRapVlu ="";
                                     String l_avgDis ="";
                                     int rowcount =rowCt+1;
                                     int startRow=rowcount+1;
                                     ArrayList grpArray=new ArrayList();
                                     int cz1=0;
                           
                                 while (rs.next()) {
                                 srno++;  
                                  if(shInx!=-1 && SZ_GRP.equals("Y")){
                                  l_grpCts = rs.getString("grp_cts");
                                 l_grpVlu = rs.getString("grp_vlu");
                                  l_grpAvgVlu = rs.getString("grp_avg_vlu");
                                 l_grpRapVlu = rs.getString("grp_rap_vlu");
                                  l_avgDis = rs.getString("grp_avg_dis");
                                  if(grpCts.equals("")) {
                                      grpCts = l_grpCts ;
                                      grpVlu = l_grpVlu ;
                                      grpRapVlu = l_grpRapVlu ;
                                      grpAvgDis = l_avgDis ;
                                      grpAvgVlue = l_grpAvgVlu;
                                  }

                                  if(!(l_grpCts.equals(grpCts))) {
                                       int grpStrRow=startRow;
                                       int grpEndRow = rowcount;
                                         
                                      rowcount++;
                                      
                                      row = sheet.createRow((short)++rowCt);
                                      grpArray.add(rowCt+1);
                                       if(ctwtCol>-1) {          //   AMOUNT
                                              String crtwtcolName=GetAlpha(ctwtCol+1);
                                              cell = row.createCell(ctwtCol);
                                            String strFormula = "SUBTOTAL(109,"+crtwtcolName+grpStrRow+":"+crtwtcolName+grpEndRow+")";
                                               cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                                               cell.setCellFormula(strFormula);
                                              cell.setCellStyle(numStyleDataBoldBig);
                                            colNum = cell.getColumnIndex(); 
                                            autoCols.add(Integer.toString(colNum));
                                          }
                                      if(rapRteInx!=-1){
                                          String rapVlucolName=GetAlpha(rapVluCol+1);
                                          cell = row.createCell(rapVluCol);
                                          String strFormula = "SUBTOTAL(109,"+rapVlucolName+grpStrRow+":"+rapVlucolName+grpEndRow+")";
                                          cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                                          cell.setCellFormula(strFormula);
                                            cell.setCellStyle(numStyleDataBoldBig);
                                            colNum = cell.getColumnIndex();
                                            autoCols.add(Integer.toString(colNum));
                                      }
                                      if(rapDisInx!=-1){
                                            String amtColName=GetAlpha(amtCol+1);
                                            String rapVluColName=GetAlpha(rapVluCol+1);
                                          cell = row.createCell(rapDisCol);
                                          //String  strFormula= "("+(amtColName+(endrow+2))+"/"+((rapVlucolName+(endrow+2))+"/"+"CZ"+cz1row)+"*100)-100";  
                                          String strFormula="SUM((("+amtColName+(grpEndRow+1)+")/(("+rapVluColName+(grpEndRow+1)+"/"+"CZ"+cz1row+")))*100)-100";
                                             cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                                             cell.setCellFormula(strFormula);
                                          cell.setCellStyle(styleBig);
                                          
                                          colNum = cell.getColumnIndex(); 
                                          autoCols.add(Integer.toString(colNum));
                                      }
                                 
                                      if(rteInx>0){  
                                                                               cell = row.createCell(rteCol);
                                                                                String crtwtColName=GetAlpha(ctwtCol+1);
                                                                                String totalamtcolName=GetAlpha(amtCol+1);
                                                                                
                                                                                        
                                                                                String strFormula = "SUBTOTAL(109,"+totalamtcolName+grpStrRow+":"+totalamtcolName+grpEndRow+")/SUBTOTAL(109,"+crtwtColName+grpStrRow+":"+crtwtColName+grpEndRow+")";
                                                         
                                                                                cell.setCellStyle(numStyleDataBoldBig);
                                                                                colNum = cell.getColumnIndex();
                                                                                cell.setCellFormula(strFormula);
                                                                               
                                                                                cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                                                                                autoCols.add(Integer.toString(colNum));
                                                                                    }
                                                          if(rteInx>0){  
                                                                                     
                                                                                    
                                                                                    cell = row.createCell(rteCol+1);
                                                                                    
                                                                                     String colName=GetAlpha(amtCol+1);
                                                                                     String strFormula = "SUBTOTAL(109,"+colName+grpStrRow+":"+colName+grpEndRow+")";
                                                                                    cell.setCellFormula(strFormula);
                                                                                    cell.setCellStyle(numStyleDataBoldBig);
                                                                                    colNum = cell.getColumnIndex();
                                                                                   cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                                                                                    autoCols.add(Integer.toString(colNum));
                                                                                 }
                                      
                                      
                                      
                                      
                                      
                                     if(rapRteInx<=0 && rapVluCol>-1 && !mdl.equals("MIX_VW")) {          //   RAPVLU
                                     
                                     String rapVlucolName=GetAlpha(rapVluCol+1);
                                     cell = row.createCell(rapVluCol);
                                     String strFormula = "SUBTOTAL(109,"+rapVlucolName+grpStrRow+":"+rapVlucolName+grpEndRow+")";
                                     cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                                     cell.setCellFormula(strFormula);
                                       cell.setCellStyle(numStyleDataBoldBig);
                                       colNum = cell.getColumnIndex();
                                       autoCols.add(Integer.toString(colNum));
                                     }
                                      
                                  grpCts = l_grpCts ;
                                  grpVlu = l_grpVlu ;
                                  grpRapVlu = l_grpRapVlu ;
                                  grpAvgDis = l_avgDis ;
                                  startRow = rowcount+1;
                                  }}
                                     
                                 rowcount++;  
                                     
                                     
                                     
                                     
                                 isSrch = true;

                                 String cts = util.nvl(rs.getString("cts"));
                                 if(mdl.equals("MIX_LKUP_XL"))
                                     cts = util.nvl(rs.getString("crtwt"));
                                 String certNo = util.nvl(rs.getString("cert_no"));
                                 String lab =  util.nvl(rs.getString("cert"));
                                 String vnm =  util.nvl(rs.getString("vnm"));
                                 String color =  util.nvl(rs.getString("color"));
                                 styleData=styleDataNormal;
                                 numStyleData=numStyleDataNormal;
                                 if(color.equals("O")){
                                     styleData = styleDataOlive;
                                     numStyleData=numStyleDataOlive;
                                 }else if(color.equals("G")){
                                     styleData = styleDataGreen;
                                     numStyleData=numStyleDataGreen;
                                 }else if(color.equals("R")){
                                     styleData = styleDataRed;
                                     numStyleData=numStyleDataRed; 
                                 }else if(color.equals("M")){
                                     styleData = styleDataMaroon;
                                     numStyleData=numStyleDataMaroon; 
                                 }else if(color.equals("B")){
                                     styleData = styleDataBlue;
                                     numStyleData=numStyleDataBlue; 
                                 }else if(color.equals("P")){
                                     styleData = styleDataviolet;
                                     numStyleData=numStyleDataviolet; 
                                 }
                
                totalCell = cellCt;
                row = sheet.createRow((short)++rowCt);
                cellCt = -1;
                 cell = row.createCell(++cellCt);
                 cell.setCellValue(Double.parseDouble(String.valueOf(srno)));
                 cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                 cell.setCellStyle(styleData);
                 //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                 colNum = cell.getColumnIndex();
                 autoCols.add(Integer.toString(colNum));
                 vwDtl = (HashMap)stockViewMap.get("VWIMG"); 
                     if(vwDtl!=null && !mdl.equals("MIX_VW")){
                     cell = row.createCell(++cellCt);
                     boolean imageadded=true;
                     if(imagelistDtl!=null && imagelistDtl.size() >0){
                     for(int j=0;j<imagelistDtl.size();j++){
                     dtls=new HashMap();
                     dtls=(HashMap)imagelistDtl.get(j);
                     String typimg=util.nvl((String)dtls.get("TYP"));
                     String allowon=util.nvl((String)dtls.get("ALLOWON"));
                     if((allowon.indexOf("SRCH") > -1) && (typimg.equals("I") || typimg.equals("S"))){
                     String path=util.nvl((String)dtls.get("PATH"));
                     String gtCol=util.nvl((String)dtls.get("GTCOL"));
                     String dna=util.nvl((String)dtls.get("DNA"));
                     String val=util.nvl(rs.getString(gtCol),"N");
                     if(!val.equals("N") || !dna.equals("")){
                           if(path.indexOf("segoma") > -1 || path.indexOf("sarineplatform") > -1)
                                path=path+val;
                            else{
                                path=path+val;
                             }
                           if(!dna.equals("")){
                             String stkIdn=util.nvl(rs.getString("stk_idn"));
                             path=dna.replaceAll("STKIDN",stkIdn);
                           }
                         
                         if(imageadded){
                         HSSFHyperlink link1 =
                             new HSSFHyperlink(HSSFHyperlink.LINK_URL);
                         link1.setAddress(path);
                         cell.setHyperlink(link1);
                         cell.setCellStyle(hlink_style);
                         cell.setCellValue("View Image");
                         imageadded=false;
                         }
                     }
                     }}
                     }
                     if(imageadded){
                         cell.setCellStyle(styleData);
                         cell.setCellValue("-");
                     }
                      colNum = cell.getColumnIndex();
                     autoCols.add(Integer.toString(colNum));
                 }

                    vwDtl = (HashMap)stockViewMap.get("VNMIMG"); 
                        if(vwDtl!=null && !mdl.equals("MIX_VW")){
                        cell = row.createCell(++cellCt);
                        boolean imageadded=true;
                          String url = util.nvl((String)vwDtl.get("url"));
                          String gtxCol=util.nvl((String)vwDtl.get("dir"));
                        if(imagelistDtl!=null && imagelistDtl.size() >0){
                        for(int j=0;j<imagelistDtl.size();j++){
                        dtls=new HashMap();
                        dtls=(HashMap)imagelistDtl.get(j);
                        String typimg=util.nvl((String)dtls.get("TYP"));
                        String allowon=util.nvl((String)dtls.get("ALLOWON"));
                          String gtCol=util.nvl((String)dtls.get("GTCOL"));
                        if(gtxCol.equals(gtCol)){
                        String path=util.nvl((String)dtls.get("PATH"));

                        String dna=util.nvl((String)dtls.get("DNA"));
                        String val=util.nvl(rs.getString(gtCol),"N");
                        if(!val.equals("N")){
                            if(dna.equals(""))
                            path=path+val;
                            else{
                                String stkIdn=util.nvl(rs.getString("stk_idn"));
                                path=dna.replaceAll("STKIDN",stkIdn);
                            }
                            if(imageadded){
                            HSSFHyperlink link1 =
                                new HSSFHyperlink(HSSFHyperlink.LINK_URL);
                            link1.setAddress(path);
                            cell.setHyperlink(link1);
                            cell.setCellStyle(hlink_style);
                            cell.setCellValue(vnm); 
                            }
                            imageadded=false;
                        }
                        }}
                        }
                        if(imageadded){
                            cell.setCellStyle(styleData);
                            cell.setCellValue(vnm);
                        }
                         colNum = cell.getColumnIndex();
                        autoCols.add(Integer.toString(colNum));
                        }else{
                 cell = row.createCell(++cellCt);
                 cell.setCellValue(vnm);
                 cell.setCellStyle(styleData);
                 //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                 colNum = cell.getColumnIndex();
                 autoCols.add(Integer.toString(colNum));
                        }
                    
                 cell = row.createCell(++cellCt);
                 if(PACKETLOOKSTT.equals(""))
                 cell.setCellValue(rs.getString("dsp_stt"));
                 else
                 cell.setCellValue(rs.getString("stt1"));
                 cell.setCellStyle(styleData);
                 //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                 colNum = cell.getColumnIndex();
                 autoCols.add(Integer.toString(colNum));
                 if(mdl.equals("MIX_VW")){
                        cell = row.createCell(++cellCt);
                        cell.setCellValue(nvl(rs.getString("cts")));
                        cell.setCellStyle(styleData);
                        //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        colNum = cell.getColumnIndex();
                        autoCols.add(Integer.toString(colNum));
                 }
                for (int i = 0; i < vwPrpLst.size(); i++) {
                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                fld += "00" + j;
                else if (j < 100)
                fld += "0" + j;
                else if (j > 100)
                fld += j;
                String prp = (String)vwPrpLst.get(i);
                if(prpDspBlocked.contains(prp)){
                }else{
                String prptyp = util.nvl((String)mprp.get(prp+"T"));
                if (prp.equals("RTE"))
                fld = "rte";
                String dbVal = rs.getString(fld);    
                String fldVal = nvl(rs.getString(fld));
                if (prp.equals("RAP_DIS"))
                fldVal = nvl(rs.getString("r_dis"));
                if (prp.equals("CRTWT"))
                    fldVal = nvl(rs.getString("cts"));
                if(mdl.equals("MIX_LKUP_XL") && prp.equals("CRTWT"))
                    fldVal = util.nvl(rs.getString("crtwt"));
                if (prp.equals("RAP_RTE"))
                fldVal = nvl(rs.getString("rap_rte"));
                if(prp.equals("KTSVIEW"))
                  fldVal = nvl(rs.getString("kts"));
                if(prp.equals("COMMENT"))
                 fldVal = nvl(rs.getString("cmnt"));
                if(prp.equals("CMP_RTE"))
                    fldVal = nvl(rs.getString("cmp"));
                if(prp.equals("MEM_COMMENT"))
                    fldVal = nvl(rs.getString("img2"));
                if(prp.equals("RFIDCD"))
                    fldVal = nvl(rs.getString("rmk"));
                    if(prp.equals("NET_PUR_DIS"))
                        fldVal = nvl(rs.getString("netpurdis"));
                    if(prp.equals("NET_PUR_VLU"))
                        fldVal = nvl(rs.getString("netpurtotal"));
                    if(prp.equals("SHOW_AMT"))
                        fldVal = nvl(rs.getString("showtotal"));
                if(prp.equals("CMP_DIS") && cmpdisfromprp.equals(""))
                     fldVal = nvl(rs.getString("cmp_dis"));
                fldVal = fldVal.trim();
                if(fldVal.equals("NA"))
                    fldVal="";
                if(prp.equals("RCHK")){
                  fldVal = util.nvl(rs.getString("img"));
               }
                    if(prp.equals("CP")){
                        fldVal = util.nvl(rs.getString("prte"));
                    }
                    if(prp.equals("CP_DIS")){
                      fldVal = util.nvl(rs.getString("cpdis"));
                    }
                    if(prp.equals("PRI_RAP_DIS")){
                      fldVal = util.nvl(rs.getString("prirapdis"));
                    }
                    if(prp.equals("CP_VLU")){
                      fldVal = util.nvl(rs.getString("cptotal"));
                    }
                if(prp.equals("CRTWT") ||  prp.equals("RTE")){
                       cell = row.createCell(++cellCt);
                   if(!fldVal.equals("") && fldVal.indexOf("#")==-1){
                      cell.setCellValue(Double.parseDouble(fldVal));
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                   }else{
                       cell.setCellValue(fldVal); 
                   }
                        cell.setCellStyle(numStyleData);
                        colNum = cell.getColumnIndex();
                        autoCols.add(Integer.toString(colNum));
                   if (prp.equals("RTE")){
                       cell = row.createCell(++cellCt);
                       fldVal= nvl(rs.getString("amt"));
                       if(!fldVal.equals("") && fldVal.indexOf("#")==-1){
                       cell.setCellValue(Double.parseDouble(fldVal));
                       cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                       }else {
                       cell.setCellValue(fldVal);
                       }


                       cell.setCellStyle(numStyleData);
                       colNum = cell.getColumnIndex();
                       autoCols.add(Integer.toString(colNum));
                       vwDtl = (HashMap)stockViewMap.get("ISCMP");
                        if(vwDtl!=null){
                         String byrIdn = util.nvl((String)vwDtl.get("url"));
                         if(byrIdn.equals(String.valueOf(info.getByrId()))){
                            cell = row.createCell(++cellCt);
                            fldVal= nvl(rs.getString("cmp"));
                            cell.setCellValue(fldVal);
                            cell.setCellStyle(numStyleData);
                            colNum = cell.getColumnIndex();
                            autoCols.add(Integer.toString(colNum));
                        }}
                   }
                        
                }else if(prp.equals("RAP_DIS")){
                  
                   cell = row.createCell(++cellCt);
                    if(!fldVal.equals("") && fldVal.indexOf("#")==-1){
                    cell.setCellValue(Double.parseDouble(fldVal));
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    }else {
                    cell.setCellValue(fldVal);
                    }
                    cell.setCellStyle(numStyleData);
                    colNum = cell.getColumnIndex();
                    autoCols.add(Integer.toString(colNum));
                   vwDtl = (HashMap)stockViewMap.get("ISCMPDIS");
                    if(vwDtl!=null){
                     String byrIdn = util.nvl((String)vwDtl.get("url"));
                 if(byrIdn.equals(String.valueOf(info.getByrId()))){
                       
                   cell = row.createCell(++cellCt);
                   fldVal= nvl(rs.getString("cmp_dis"));
                   cell.setCellValue(fldVal);
                   cell.setCellStyle(numStyleData);
                   colNum = cell.getColumnIndex();
                   autoCols.add(Integer.toString(colNum));
                    }}
                }else if(prp.equals("RAP_RTE")){
                       cell = row.createCell(++cellCt);
                   if(!fldVal.equals("") && fldVal.indexOf("#")==-1){
                      cell.setCellValue(Double.parseDouble(fldVal));
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                   }else{
                       cell.setCellValue(fldVal); 
                   }
                        cell.setCellStyle(numStyleData);
                        colNum = cell.getColumnIndex();
                        autoCols.add(Integer.toString(colNum));
						
                       cell = row.createCell(++cellCt);
                       fldVal= nvl(rs.getString("rap_vlu"));
                       if(!fldVal.equals("") && fldVal.indexOf("#")==-1){
                       cell.setCellValue(Double.parseDouble(fldVal));
                       cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                       }else {
                       cell.setCellValue(fldVal);
                       }
                       cell.setCellStyle(numStyleData);
                       colNum = cell.getColumnIndex();
                       autoCols.add(Integer.toString(colNum));
		}else{
                vwDtl = (HashMap)stockViewMap.get(prp);
                String url = "";
                
                    if(vwDtl!=null){
                     cell = row.createCell(++cellCt);
                     String  pNme ="";
                     String dir = "";
                     url = util.nvl((String)vwDtl.get("url"));
                     typ = util.nvl((String)vwDtl.get("typ"));
                     dir = util.nvl((String)vwDtl.get("dir"),"certImg");
                     String viewPRP = util.nvl((String)vwDtl.get("view"));
                     if(prp.equals("CERT NO.") || prp.equals("CERT_NO")){
                     if(lab.equals("IGI"))
                      url="http://www.igiworldwide.com/search_report.aspx?PrintNo=REPNO&Wght=WT";
                     if(lab.equals("HRD"))
                       url="https://my.hrdantwerp.com/?record_number=REPNO&weight=WT";
                      if(!fldVal.equals("") && (lab.equals("IGI")||lab.equals("GIA") || lab.equals("HRD"))){
                       url = url.replace("WT",cts);
                       url = url.replace("REPNO", fldVal);
                       url.replaceAll(" ", "");
                       if(viewPRP.equals("1"))
                       pNme = fldVal ;
                       else
                       pNme = fldVal;
                       HSSFHyperlink link1 =
                       new HSSFHyperlink(HSSFHyperlink.LINK_URL);
                       link1.setAddress(url);
                       cell.setHyperlink(link1);
                       cell.setCellStyle(hlink_style);
                       cell.setCellValue(pNme);
                       }else{
                           cell.setCellStyle(styleData);
                           cell.setCellValue(fldVal);
                       }
                       }else{
                    if(imagelistDtl!=null && imagelistDtl.size() >0){
                    for(int k=0;k<imagelistDtl.size();k++){
                    dtls=new HashMap();
                    dtls=(HashMap)imagelistDtl.get(k);
                    String looptyp=util.nvl((String)dtls.get("TYP"));
                    String allowon=util.nvl((String)dtls.get("ALLOWON"));
                    String gtCol=util.nvl((String)dtls.get("GTCOL"));
                    if((looptyp.equals("C") || looptyp.equals("V")) && (allowon.indexOf("SRCH") > -1) && gtCol.equals(dir)){
                    String path=util.nvl((String)dtls.get("PATH"));
                    String val=util.nvl(rs.getString(gtCol),"N");
                    if(!val.equals("N")){
                        path=path+val;
                        HSSFHyperlink link1 =
                            new HSSFHyperlink(HSSFHyperlink.LINK_URL);
                        link1.setAddress(path);
                        cell.setHyperlink(link1);
                        cell.setCellStyle(hlink_style);
                        cell.setCellValue(fldVal); 
                    }else{
                           cell.setCellStyle(styleData);
                           cell.setCellValue(fldVal);
                    }}}
                    }else{
                           cell.setCellStyle(styleData);
                           cell.setCellValue(fldVal);
                    }
                    }
                    }else{
                cell = row.createCell(++cellCt);
                   
                if(!fldVal.equals("") && (numParam.contains(prp) || prptyp.equals("N")) && fldVal.indexOf("#")==-1 ){
                cell.setCellValue(Double.parseDouble(fldVal));
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellStyle(numStyleData);
                }else {
                cell.setCellValue(fldVal);
                cell.setCellStyle(styleData);
                }
                colNum = cell.getColumnIndex();
                autoCols.add(Integer.toString(colNum));
                    
                  
                }}
                }}
                    if(colHdr.indexOf("RAPVAL")==-1  && !mdl.equals("MIX_VW")) { 
                        cell = row.createCell(++cellCt);
                        double rap_rt=rs.getDouble("rap_rte");
                        cell.setCellValue(rs.getDouble("rap_rte"));
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellStyle(numStyleData);
                        colNum = cell.getColumnIndex(); 
                        autoCols.add(Integer.toString(colNum)); 
                    }   
                    
                 String rapVal = nvl(rs.getString("rap_vlu"));
                    if(colHdr.indexOf("RAPVAL")==-1  && !rapVal.equals("") && !mdl.equals("MIX_VW")) {
                 cell = row.createCell(++cellCt);
                 
                 cell.setCellValue(rs.getDouble("rap_vlu"));
                 cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                 cell.setCellStyle(numStyleData);
                 colNum = cell.getColumnIndex(); 
                 autoCols.add(Integer.toString(colNum));  
                 }
                    
                    
                      
                        exh_rte=rs.getDouble("exh_rte");
                        if(exh_rte<=0){
                        exh_rte=1;
                        }
                       

                }
                    rs.close();
                    pst.close();
                    
            //Last sub Total
          
                if(isSrch && SZ_GRP.equals("Y")){                    
                    int grpStrRow=startRow;
                    int grpEndRow = rowcount;
                    row = sheet.createRow((short)++rowCt);
                    grpArray.add(rowCt+1);
                 
                    if(colHdr.indexOf("CRTWT")>-1) {          //  Last subTotal   CaretWeight
                        String crtwtcolName=GetAlpha(ctwtCol+1);
                        cell = row.createCell(ctwtCol);
                        String strFormula = "SUBTOTAL(109,"+crtwtcolName+grpStrRow+":"+crtwtcolName+grpEndRow+")";
                        
                         cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                         cell.setCellFormula(strFormula);
                        cell.setCellStyle(numStyleDataBoldBig);
                        colNum = cell.getColumnIndex();
                        autoCols.add(Integer.toString(colNum));
                        
                       
                    }
                    if(rapRteInx!=-1) {
                    
                    String rapVlucolName=GetAlpha(rapVluCol+1);
                    cell = row.createCell(rapVluCol);
                    String strFormula = "SUBTOTAL(109,"+rapVlucolName+grpStrRow+":"+rapVlucolName+grpEndRow+")";
                    cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                    cell.setCellFormula(strFormula);
                      cell.setCellStyle(numStyleDataBoldBig);
                      colNum = cell.getColumnIndex();
                      autoCols.add(Integer.toString(colNum));
                    }
                    if(rapDisInx!=-1){
                    
                    String amtColName=GetAlpha(amtCol+1);
                    
                    String rapVluColName=GetAlpha(rapVluCol+1);
                    cell = row.createCell(rapDisCol);
                    //String strFormula="SUM((("+amtColName+(grpEndRow+1)+")/("+rapVluColName+(grpEndRow+1)+"))*100)-100";
                        String strFormula="SUM((("+amtColName+(grpEndRow+1)+")/(("+rapVluColName+(grpEndRow+1)+"/"+"CZ"+cz1row+1+")))*100)-100";
                     cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                     cell.setCellFormula(strFormula);
                     cell.setCellStyle(styleBig);
                    
                    colNum = cell.getColumnIndex();
                    autoCols.add(Integer.toString(colNum));
                    }
                    
                    if(rteInx>0){  
                                                              
                                                              cell = row.createCell(rteCol);
                                                              
                                                              String crtwtColName=GetAlpha(ctwtCol+1);
                                                              
                                                              String totalamtcolName=GetAlpha(amtCol+1);
                                                             
                                                                      
                                                              String strFormula = "SUBTOTAL(109,"+totalamtcolName+grpStrRow+":"+totalamtcolName+grpEndRow+")/SUBTOTAL(109,"+crtwtColName+grpStrRow+":"+crtwtColName+grpEndRow+")";
                                       
                                                              cell.setCellStyle(numStyleData);
                                                              colNum = cell.getColumnIndex();
                                                              cell.setCellFormula(strFormula);
                                                             
                                                              cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                                                              autoCols.add(Integer.toString(colNum));
                                                                  }
                                        if(rteInx>0){  
                                                                   
                                                                  
                                                                  cell = row.createCell(rteCol+1);
                                                                   String colName=GetAlpha(amtCol+1);
                                                                   String strFormula = "SUBTOTAL(109,"+colName+grpStrRow+":"+colName+grpEndRow+")";
                                                                  cell.setCellFormula(strFormula);
                                                                  cell.setCellStyle(numStyleData);
                                                                  colNum = cell.getColumnIndex();
                                                                 cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                                                                  autoCols.add(Integer.toString(colNum));
                                                               }
                    
                    
                    
                    
                    
                        if(rapRteInx<=0 && colHdr.indexOf("RAPVAL")>-1  && !mdl.equals("MIX_VW")) {          //   RAPVLU
                        
                        String rapVlucolName=GetAlpha(rapVluCol+1);
                        cell = row.createCell(rapVluCol);
                        String strFormula = "SUBTOTAL(109,"+rapVlucolName+grpStrRow+":"+rapVlucolName+grpEndRow+")";
                        cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                        cell.setCellFormula(strFormula);
                          cell.setCellStyle(numStyleDataBoldBig);
                          colNum = cell.getColumnIndex();
                          autoCols.add(Integer.toString(colNum));
                        }
                }else
                    rowCt = rowCt+1;
                    
             //For Grand Total///  
                if(isSrch){
                   
                  endrow=rowCt;
                    
                    row = sheet.createRow((short)++rowCt);
                        HSSFRow smryRow =  sheet.getRow(fnTtlRow);
                        String ttlFormula="";
                        if(grand.equals("Y") && !mdl.equals("MIX_VW")){
                                        cell = row.createCell(0);
                                        cell.setCellValue("Grand Total");
                                        cell.setCellStyle(styleBold);
                                        colNum = cell.getColumnIndex();
                                        autoCols.add(Integer.toString(colNum));
                                        
                            String strFormula = "SUBTOTAL(102,A"+stRow+":A"+endrow+")";
                             ttlFormula = "COUNT(A"+stRow+":A"+endrow+")";
                            if(smryRow!=null){
                                 smryRow.getCell(fnTtlCell-1).setCellFormula(ttlFormula);
                                   HSSFRow subSummRow =  sheet.getRow(fnTtlRow+1);
                                   subSummRow.getCell(fnTtlCell-1).setCellFormula(strFormula);
                            }

                                        
                        }
                                        if(crtWtInx!=-1){
                                                              cell = row.createCell(ctwtCol);
                                                               int crtwtCol=colHdr.indexOf("CRTWT");
                                                               String crtwtcolName=GetAlpha(crtwtCol+1);
                                                                 
                                                        String strFormula ="";
                                                        if(SZ_GRP.equals("Y")){
                                                     for(int i=0;i<grpArray.size();i++){
                                                         int arrRow =(Integer)grpArray.get(i);
                                                         if(strFormula.equals(""))
                                                             strFormula = (crtwtcolName+arrRow);
                                                            else
                                                         strFormula=strFormula+"+"+(crtwtcolName+arrRow);
                                                         }}else{
                                                             strFormula = "round(SUBTOTAL(109,"+crtwtcolName+stRow+":"+crtwtcolName+endrow+"),2)";
                                                            ttlFormula= "round(SUM("+crtwtcolName+stRow+":"+crtwtcolName+endrow+"),2)";
                                                         }
                                                            //  String strFormula = "SUM("+crtwtcolName+stRow+":"+crtwtcolName+endrow+")";
                                                              cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                                                              cell.setCellFormula(strFormula);

                                                              cell.setCellStyle(numStyleDataBold);
                                                              colNum = cell.getColumnIndex();
                                                              autoCols.add(Integer.toString(colNum));
                                                              if(smryRow!=null){
                                                                   smryRow.getCell(fnTtlCell).setCellFormula(ttlFormula);
                                                                     HSSFRow subSummRow =  sheet.getRow(fnTtlRow+1);
                                                                     subSummRow.getCell(fnTtlCell).setCellFormula(strFormula);
                                                              }
                                                               }  
                        if(rapRteInx!=-1) {
                            fnTtlCell =fnTtlCell+1;
                            cell = row.createCell(rapVluCol);
                            String rapVlucolName=GetAlpha(rapVluCol+1);
                            String strFormula ="";
                            if(SZ_GRP.equals("Y")){
                            for(int i=0;i<grpArray.size();i++){
                            int arrRow =(Integer)grpArray.get(i);
                            if(strFormula.equals(""))
                            strFormula = (rapVlucolName+arrRow);
                            else
                            strFormula=strFormula+"+"+(rapVlucolName+arrRow);
                            
                            }
                            }else{
                            strFormula = "round(SUBTOTAL(109,"+rapVlucolName+stRow+":"+rapVlucolName+endrow+"),2)";
                                ttlFormula = "round(SUM("+rapVlucolName+stRow+":"+rapVlucolName+endrow+"),2)";
                            }
                            
                            //String strFormula1 = "SUM("+rapVlucolName+stRow+":"+rapVlucolName+endrow+")";
                            cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                            cell.setCellFormula(strFormula);
                            cell.setCellStyle(numStyleDataBold);
                            colNum = cell.getColumnIndex();
                            autoCols.add(Integer.toString(colNum));
                            if(smryRow!=null){
                            smryRow.getCell(fnTtlCell).setCellFormula(ttlFormula);
                              HSSFRow subSummRow =  sheet.getRow(fnTtlRow+1);
                             subSummRow.getCell(fnTtlCell).setCellFormula(strFormula);
                            }
                        }
                                            if(rapDisInx!=-1){
                                                                   fnTtlCell =fnTtlCell+1;  
                                                                   String amtColName=GetAlpha(amtCol+1);
                                                                   String rapVlucolName=GetAlpha(rapVluCol+1);
                                                                  String strFormula="SUM(((SUBTOTAL(109,"+amtColName+stRow+":"+amtColName+endrow+"))/(SUBTOTAL(109,"+rapVlucolName+stRow+":"+rapVlucolName+endrow+"))/("+"CZ"+cz1row+"))*100)-100";
                                                                     ttlFormula = "SUM(((round(SUM("+amtColName+stRow+":"+amtColName+endrow+"),2))/(round(SUM("+rapVlucolName+stRow+":"+rapVlucolName+endrow+"),2))/("+"CZ"+cz1row+"))*100)-100";

                                                                          
                                                                                                                          //    String strFormula = "SUM("+colName+stRow+":"+colName+endrow+")";
                                            //String strFormula="SUM(("+amtColName+(endrow+2)+")/("+rapVlucolName+(endrow+2)+")*100)-100";
                                                              cell = row.createCell(rapDisCol);
                                                             
                                                               cell.setCellFormula(strFormula);
                                                              cell.setCellStyle(numStyleDataBold);
                                                              colNum = cell.getColumnIndex();
                                                             cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                                                              autoCols.add(Integer.toString(colNum)); 
                                                                   if(smryRow!=null){
                                                                   smryRow.getCell(fnTtlCell).setCellFormula(ttlFormula);
                                                                     HSSFRow subSummRow =  sheet.getRow(fnTtlRow+1);
                                                                     subSummRow.getCell(fnTtlCell).setCellFormula(strFormula);
                                                                   }
                                                               }
                                
                                        if(rteInx>0){  
                                                                      fnTtlCell =fnTtlCell+1;
                                                              cell = row.createCell(rteCol);
                                                            
                                                                      String amtcolName=GetAlpha(amtCol+1);
                                                                      
                                                                      String crtwtcolName=GetAlpha(ctwtCol+1);
                                                                      String  strFormula= "round("+(amtcolName+(endrow+2))+"/"+(crtwtcolName+(endrow+2))+",2)";       
                                                                     ttlFormula= "round("+(amtcolName+(endrow+2))+"/"+(crtwtcolName+(endrow+2))+",2)";       
                                                                                                     
                                                                      
                                                                      
                                                            //String strFormula = "SUM("+totalamtcolName+stRow+":"+totalamtcolName+endrow+")/SUM("+crtwtColName+stRow+":"+crtwtColName+endrow+")";
                                       
                                                              cell.setCellStyle(numStyleDataBold);
                                                              colNum = cell.getColumnIndex();
                                                              cell.setCellFormula(strFormula);
                                                             
                                                              cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                                                              autoCols.add(Integer.toString(colNum));
                                                                      if(smryRow!=null){
                                                                      smryRow.getCell(fnTtlCell).setCellFormula(ttlFormula);
                                                                        HSSFRow subSummRow =  sheet.getRow(fnTtlRow+1);
                                                                       subSummRow.getCell(fnTtlCell).setCellFormula(strFormula);
                                                                      }
                                                                  }
                                        if(rteInx>0){  
                                                                   fnTtlCell =fnTtlCell+1;         
                                                                  
                                                                  cell = row.createCell(rteCol+1);
                                                                
                                                                  
                                                                   String totalamtcolName=GetAlpha(amtCol+1);
                                                                           String strFormula ="";
                                                                   if(SZ_GRP.equals("Y")){
                                                                           for(int i=0;i<grpArray.size();i++){
                                                                               int arrRow =(Integer)grpArray.get(i);
                                                                               if(strFormula.equals(""))
                                                                                   strFormula = (totalamtcolName+arrRow);
                                                                                  else
                                                                               strFormula=strFormula+"+"+(totalamtcolName+arrRow);
                                                                             }
                                                                   }else{
                                                                       strFormula = "round(SUBTOTAL(109,"+totalamtcolName+stRow+":"+totalamtcolName+endrow+"),2)";
                                                                       ttlFormula = "round(SUM("+totalamtcolName+stRow+":"+totalamtcolName+endrow+"),2)";
                                                                   }
                                                                          
                                                                      cell.setCellFormula(strFormula);
                                                                cell.setCellStyle(numStyleDataBold);
                                                                  colNum = cell.getColumnIndex();
                                                                 cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                                                                  autoCols.add(Integer.toString(colNum));
                                                                   if(smryRow!=null){
                                                                   smryRow.getCell(fnTtlCell).setCellFormula(ttlFormula);
                                                                     HSSFRow subSummRow =  sheet.getRow(fnTtlRow+1);
                                                              subSummRow.getCell(fnTtlCell).setCellFormula(strFormula);
                                                                   }
                                                               }
//                                        if(rapRteInx>=0 && rapVluCol>0){
                                            if(rapRteInx<=0 && rapVluCol>0){
                                            fnTtlCell =fnTtlCell+1;
                                            cell = row.createCell(rapVluCol);
                                            String rapVlucolName=GetAlpha(rapVluCol+1);
                                            String strFormula ="";
                                            if(SZ_GRP.equals("Y")){
                                            for(int i=0;i<grpArray.size();i++){
                                                int arrRow =(Integer)grpArray.get(i);
                                                if(strFormula.equals(""))
                                                    strFormula = (rapVlucolName+arrRow);
                                                   else
                                                strFormula=strFormula+"+"+(rapVlucolName+arrRow);
                                                
                                            } 
                                            }else{
                                                strFormula = "round(SUBTOTAL(109,"+rapVlucolName+stRow+":"+rapVlucolName+endrow+"),2)";
                                                ttlFormula = "round(SUM("+rapVlucolName+stRow+":"+rapVlucolName+endrow+"),2)";
                                            }
                                           
                                                                  //String strFormula1 = "SUM("+rapVlucolName+stRow+":"+rapVlucolName+endrow+")";
                                                                  cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                                                                  cell.setCellFormula(strFormula);
                                                                  cell.setCellStyle(numStyleDataBold);
                                                                  colNum = cell.getColumnIndex();
                                                                  autoCols.add(Integer.toString(colNum));
                                            if(smryRow!=null){                    
                                            smryRow.getCell(fnTtlCell).setCellFormula(ttlFormula);
                                              HSSFRow subSummRow =  sheet.getRow(fnTtlRow+1);
                                              subSummRow.getCell(fnTtlCell).setCellFormula(strFormula);
                                            }
                                        }
                          
                 
                    String addNoteData =util.nvl((String)pageDtl.get("ADDNOTE"));
                    if(!addNoteData.equals("")){
                        rowCt= rowCt+2;

                        row = sheet.createRow((short)++rowCt);
                        cell = row.createCell(0);
                        cell.setCellValue("Note");
                        cell.setCellStyle(numStyleDataBoldleft);
                        sheet.addMergedRegion(merge(rowCt, rowCt, 0, 6));   
                        String[] actaddNote = addNoteData.split(",");
                        for(int i=0 ; i <actaddNote.length; i++)
                         {
                             row = sheet.createRow((short)++rowCt);
                             cell = row.createCell(0);
                             cell.setCellValue(String.valueOf(i+1)+")"+actaddNote[i]);
                             cell.setCellStyle(numStyleDataBoldleft);
                             sheet.addMergedRegion(merge(rowCt, rowCt, 0, 6)); 
                         }
                    }
                        String extraString =util.nvl((String)pageDtl.get("EXTRASTRING"));
                        if(!extraString.equals("")){
                            rowCt= rowCt+2;

                            row = sheet.createRow((short)++rowCt);
                            cell = row.createCell(0);
                            cell.setCellValue("Note");
                            cell.setCellStyle(numStyleDataBoldleft);
                            sheet.addMergedRegion(merge(rowCt, rowCt, 0, 3));   
                            String[] actaddNote = extraString.split(",");
                            for(int i=0 ; i <actaddNote.length; i++)
                             {
                                 String actaddNo=util.nvl((String)actaddNote[i]);
                                 actaddNo = actaddNo.replaceFirst("~DTE~", util.getToDteMarker());
                                 row = sheet.createRow((short)++rowCt);
                                 cell = row.createCell(0);
                                 cell.setCellValue(actaddNo);
                                 cell.setCellStyle(numStyleDataBoldleft);
                                 sheet.addMergedRegion(merge(rowCt, rowCt, 0, 3)); 
                             }
                        }
                    }
                }

                 catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
                }

                    row = sheet.getRow(cz1row-1);
                    cell = row.createCell(103);
                    cell.setCellValue(exh_rte);
                    cell.setCellStyle(styleData);
                    if(hdrfilter.equals("") || hdrfilter.equals("YES"))
                       sheet.setAutoFilter(CellRangeAddress.valueOf("A"+stRow+":"+endCellAlp+endrow));
                    
                    Iterator it=autoCols.iterator();
                        while(it.hasNext()) {
                                  String value=(String)it.next();
                                  int colId = Integer.parseInt(value);
                                  sheet.autoSizeColumn((short)colId, true);

                        }

                return hwb ;
                }





       
        public void ExcelHeader(String typ,HttpServletRequest req){
          
          HashMap pageDtl=util.pagedefXL("GENERAL_FMT","EXCEL");
              int rowCt = 0;
              short cellCt = -1;
              String fontNm = util.nvl((String)pageDtl.get("FONT_NAME"));
              String bgcolor = util.nvl((String)pageDtl.get("HEADER_BG"));
              int ADDWIDTH = Integer.parseInt(util.nvl((String)pageDtl.get("ADDWIDTH")));
              int fontSz = Integer.parseInt((String)util.nvl((String)pageDtl.get("FONT_SIZE")));
              int fontHDSz = Integer.parseInt((String)util.nvl((String)pageDtl.get("FONT_SIZE_HEADER")));
              int fontCOLDSz = Integer.parseInt((String)util.nvl((String)pageDtl.get("FONT_SIZE_COL_HED")));
             hwb = new HSSFWorkbook();
             sheet = hwb.createSheet(typ);
              //CreationHelper createHelper = hwb.getCreationHelper();
          
              HSSFCellStyle hlink_style = hwb.createCellStyle();
              HSSFFont hlink_font = hwb.createFont();
              hlink_font.setUnderline(HSSFFont.U_SINGLE);
              hlink_font.setFontName(fontNm);
              hlink_font.setColor(HSSFColor.BLUE.index);
              hlink_font.setFontHeightInPoints((short)fontSz);
              hlink_style.setFont(hlink_font);
              HSSFFont fontHdr = hwb.createFont();
          
              if(bgcolor.equals("RED") || bgcolor.equals("SKY_BLUE")){
              fontHdr.setColor(HSSFColor.WHITE.index);
              }else{
              fontHdr.setColor(HSSFColor.BLACK.index);
              }
          
              fontHdr.setFontHeightInPoints((short)fontCOLDSz);
              fontHdr.setFontName(fontNm);
              String COL_HED_BOLD = util.nvl((String)pageDtl.get("COL_HED_BOLD"));
              if(COL_HED_BOLD.equals("YES"))
              fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
          
              HSSFCellStyle styleHdr = hwb.createCellStyle();
              styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
              if(bgcolor.equals("RED")){
              styleHdr.setFillForegroundColor(HSSFColor.RED.index);
              }
          
              if(bgcolor.equals("SKY_BLUE")){
              styleHdr.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
              }
              styleHdr.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
              styleHdr.setFont(fontHdr);
          
              styleHdr.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
              styleHdr.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
              styleHdr.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
              styleHdr.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
              styleHdr.setTopBorderColor(HSSFColor.BLACK.index);
              styleHdr.setBottomBorderColor(HSSFColor.BLACK.index);
              styleHdr.setLeftBorderColor(HSSFColor.BLACK.index);
              styleHdr.setRightBorderColor(HSSFColor.BLACK.index);
          
              HSSFFont fontData = hwb.createFont();
              fontData.setFontHeightInPoints((short)fontSz);
              fontData.setFontName(fontNm);
              String DATA_BOLD = util.nvl((String)pageDtl.get("DATA_BOLD"));
              if(DATA_BOLD.equals("YES"))
              fontData.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
          
          
          
              HSSFCellStyle styleData = hwb.createCellStyle();
              styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
              styleData.setFont(fontData);
              styleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
              styleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
              styleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
              styleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
              styleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
              styleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
              styleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
              styleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
          
                  HSSFCellStyle numStyleData = hwb.createCellStyle();
                  numStyleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                  numStyleData.setFont(fontData);
                  numStyleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                  numStyleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                  numStyleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                  numStyleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                  numStyleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                  numStyleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                  numStyleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                  numStyleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                    HSSFDataFormat df = hwb.createDataFormat();
                    numStyleData.setDataFormat(df.getFormat("#0.00#"));
          
              HSSFFont fontDataBig = hwb.createFont();
              fontDataBig.setFontHeightInPoints((short)fontSz);
              fontDataBig.setFontName(fontNm);
              fontDataBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
          
              HSSFCellStyle styleBig = hwb.createCellStyle();
              styleBig.setAlignment(HSSFCellStyle.ALIGN_CENTER);
              styleBig.setFont(fontDataBig);
              styleBig.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
              styleBig.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
              styleBig.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
              styleBig.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
              styleBig.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
              styleBig.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
              styleBig.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
              styleBig.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
          
          
              HSSFFont fontHeaderDataBig = hwb.createFont();
              fontHeaderDataBig.setFontHeightInPoints((short)fontHDSz);
              fontHeaderDataBig.setFontName(fontNm);
              String FONT_BOLD_HEADER = util.nvl((String)pageDtl.get("FONT_BOLD_HEADER"));
              if(FONT_BOLD_HEADER.equals("YES"))
              fontHeaderDataBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
          
              HSSFCellStyle styleHeaderBig = hwb.createCellStyle();
              styleHeaderBig.setAlignment(HSSFCellStyle.ALIGN_CENTER);
              styleHeaderBig.setFont(fontHeaderDataBig);
              styleHeaderBig.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
              styleHeaderBig.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
              styleHeaderBig.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
              styleHeaderBig.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
              styleHeaderBig.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
              styleHeaderBig.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
              styleHeaderBig.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
              styleHeaderBig.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
          
          
          
              HSSFFont fontDataBold = hwb.createFont();
              fontDataBold.setFontHeightInPoints((short)fontSz);
              fontDataBold.setFontName(fontNm);
              String FOOTER_BOLD = util.nvl((String)pageDtl.get("FOOTER_BOLD"));
              if(FOOTER_BOLD.equals("YES"))
              fontDataBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
          
              HSSFCellStyle styleBold = hwb.createCellStyle();
              styleBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
              styleBold.setFont(fontDataBold);
              styleBold.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
              styleBold.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
              styleBold.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
              styleBold.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
              styleBold.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
              styleBold.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
              styleBold.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
              styleBold.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
          
              fontHdrWh = hwb.createFont();
              fontHdrWh.setFontHeightInPoints((short)16);
              fontHdrWh.setFontName(fontNm);
              fontHdrWh.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
              styleHdrBk = hwb.createCellStyle();
              styleHdrBk.setAlignment(HSSFCellStyle.ALIGN_CENTER);
              styleHdrBk.setFont(fontHdrWh);
              SortedSet autoCols = null;
              HSSFRow row = null ;
              HSSFCell cell = null ;
          
              int colNum = 0;
              int rowNm = 0;
              int rapValInx =0;
              int totalCell=0;
              int stCol = ++cellCt, stRow = rowCt;
              boolean isSrch = false;
              String logoNme =util.nvl((String)pageDtl.get("LOGO"));
              String NAME = util.nvl((String)pageDtl.get("NAME"));
              if(!logoNme.equals("")){
              try {
              String servPath = req.getRealPath("/images/clientsLogo");
          
              if(servPath.indexOf("/") > -1)
              servPath += "/" ;
              else {
              //servPath = servPath.replace('\', '\\');
              servPath += "\\" ;
              }
              FileInputStream fis=new FileInputStream(servPath+logoNme);
              ByteArrayOutputStream img_bytes = new ByteArrayOutputStream();
              int b;
              while ((b = fis.read()) != -1)
              img_bytes.write(b);
              fis.close();
              int imgWdt = 800, imgHt = 185;
          
              row = sheet.createRow((short)++rowCt);
          
              HSSFClientAnchor anchor =
              new HSSFClientAnchor(1, 1, imgWdt, imgHt, (short)5, rowCt,
              (short)(stCol+8), (stRow + 3));
          
              rowCt = stRow + 2;
              cellCt = (short)(stCol + 5);
              int index =
              hwb.addPicture(img_bytes.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG);
              HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
              HSSFPicture logo = patriarch.createPicture(anchor, index);
          
              anchor.setAnchorType(2);
          
              } catch (FileNotFoundException fnfe) {
              // TODO: Add catch code
              fnfe.printStackTrace();
              } catch (IOException ioe) {
              // TODO: Add catch code
              ioe.printStackTrace();
              }
          
              }else if(!NAME.equals("")){
              row = sheet.createRow((short)2);
              cell = row.createCell(1);
              cell.setCellValue(getStr(NAME,fontHdrWh));
              cell.setCellStyle(styleHdrBk);
              sheet.addMergedRegion(merge(2, 2, 1, 8));
              }
          
              String add1 =util.nvl((String)pageDtl.get("ADD1"));
              if(!add1.equals("")){
              rowCt++;
              if(NAME.equals("")){
              row = sheet.createRow((short)2);
              }
              cell = row.createCell(10);
              cell.setCellValue(add1);
              cell.setCellStyle(styleHeaderBig);
              sheet.addMergedRegion(merge(2, 2, 10, ADDWIDTH));
          
              }
              String add2 =util.nvl((String)pageDtl.get("ADD2"));
              if(!add2.equals("")){
          
              row = sheet.createRow((short)3);
              cell = row.createCell(10);
              cell.setCellValue(add2);
              cell.setCellStyle(styleHeaderBig);
              sheet.addMergedRegion(merge(3, 3, 10, ADDWIDTH));
              rowCt++;
              }
          
            
        }

    public HSSFWorkbook getInXl(String typ,HttpServletRequest req ,String mdl) {
    HashMap pageDtl=util.pagedefXL("GENERAL_FMT","EXCEL");
    int rowCt = 0;
    short cellCt = -1;
    HashMap dbinfo = info.getDmbsInfoLst();
    String cnt=util.nvl((String)dbinfo.get("CNT"));
    String fontNm = util.nvl((String)pageDtl.get("FONT_NAME"));
    String bgcolor = util.nvl((String)pageDtl.get("HEADER_BG"));
    int ADDWIDTH = Integer.parseInt(util.nvl((String)pageDtl.get("ADDWIDTH")));
    int fontSz = Integer.parseInt((String)util.nvl((String)pageDtl.get("FONT_SIZE")));
    int fontHDSz = Integer.parseInt((String)util.nvl((String)pageDtl.get("FONT_SIZE_HEADER")));
    int fontCOLDSz = Integer.parseInt((String)util.nvl((String)pageDtl.get("FONT_SIZE_COL_HED")));
    HSSFWorkbook hwb = new HSSFWorkbook();
    HSSFSheet sheet = hwb.createSheet(typ);
    //CreationHelper createHelper = hwb.getCreationHelper();

    HSSFCellStyle hlink_style = hwb.createCellStyle();
    HSSFFont hlink_font = hwb.createFont();
    hlink_font.setUnderline(HSSFFont.U_SINGLE);
    hlink_font.setFontName(fontNm);
    hlink_font.setColor(HSSFColor.BLUE.index);
    hlink_font.setFontHeightInPoints((short)fontSz);
    hlink_style.setFont(hlink_font);
    HSSFFont fontHdr = hwb.createFont();

    if(bgcolor.equals("RED") || bgcolor.equals("SKY_BLUE")){
    fontHdr.setColor(HSSFColor.WHITE.index);
    }else{
    fontHdr.setColor(HSSFColor.BLACK.index);
    }

    fontHdr.setFontHeightInPoints((short)fontCOLDSz);
    fontHdr.setFontName(fontNm);
    String COL_HED_BOLD = util.nvl((String)pageDtl.get("COL_HED_BOLD"));
    if(COL_HED_BOLD.equals("YES"))
    fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

    HSSFCellStyle styleHdr = hwb.createCellStyle();
    styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    if(bgcolor.equals("RED")){
    styleHdr.setFillForegroundColor(HSSFColor.RED.index);
    }

    if(bgcolor.equals("SKY_BLUE")){
    styleHdr.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
    }
    styleHdr.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    styleHdr.setFont(fontHdr);

    styleHdr.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
    styleHdr.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
    styleHdr.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
    styleHdr.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
    styleHdr.setTopBorderColor(HSSFColor.BLACK.index);
    styleHdr.setBottomBorderColor(HSSFColor.BLACK.index);
    styleHdr.setLeftBorderColor(HSSFColor.BLACK.index);
    styleHdr.setRightBorderColor(HSSFColor.BLACK.index);

    HSSFFont fontData = hwb.createFont();
    fontData.setFontHeightInPoints((short)fontSz);
    fontData.setFontName(fontNm);
    String DATA_BOLD = util.nvl((String)pageDtl.get("DATA_BOLD"));
    if(DATA_BOLD.equals("YES"))
    fontData.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);



    HSSFCellStyle styleData = hwb.createCellStyle();
    styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleData.setFont(fontData);
    styleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
    styleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
    styleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
    styleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
    styleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);

        HSSFCellStyle numStyleData = hwb.createCellStyle();
        numStyleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        numStyleData.setFont(fontData);
        numStyleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
          HSSFDataFormat df = hwb.createDataFormat();
          numStyleData.setDataFormat(df.getFormat("#0.00#"));

    HSSFFont fontDataBig = hwb.createFont();
    fontDataBig.setFontHeightInPoints((short)fontSz);
    fontDataBig.setFontName(fontNm);
    fontDataBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

    HSSFCellStyle styleBig = hwb.createCellStyle();
    styleBig.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleBig.setFont(fontDataBig);
    styleBig.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
    styleBig.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
    styleBig.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
    styleBig.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
    styleBig.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleBig.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleBig.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleBig.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);


    HSSFFont fontHeaderDataBig = hwb.createFont();
    fontHeaderDataBig.setFontHeightInPoints((short)fontHDSz);
    fontHeaderDataBig.setFontName(fontNm);
    String FONT_BOLD_HEADER = util.nvl((String)pageDtl.get("FONT_BOLD_HEADER"));
    if(FONT_BOLD_HEADER.equals("YES"))
    fontHeaderDataBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

    HSSFCellStyle styleHeaderBig = hwb.createCellStyle();
    styleHeaderBig.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleHeaderBig.setFont(fontHeaderDataBig);
    styleHeaderBig.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
    styleHeaderBig.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
    styleHeaderBig.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
    styleHeaderBig.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
    styleHeaderBig.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleHeaderBig.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleHeaderBig.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleHeaderBig.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);



    HSSFFont fontDataBold = hwb.createFont();
    fontDataBold.setFontHeightInPoints((short)fontSz);
    fontDataBold.setFontName(fontNm);
    String FOOTER_BOLD = util.nvl((String)pageDtl.get("FOOTER_BOLD"));
    if(FOOTER_BOLD.equals("YES"))
    fontDataBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

    HSSFCellStyle styleBold = hwb.createCellStyle();
    styleBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleBold.setFont(fontDataBold);
    styleBold.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
    styleBold.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
    styleBold.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
    styleBold.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
    styleBold.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleBold.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleBold.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleBold.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);

    fontHdrWh = hwb.createFont();
    fontHdrWh.setFontHeightInPoints((short)16);
    fontHdrWh.setFontName(fontNm);
    fontHdrWh.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
    styleHdrBk = hwb.createCellStyle();
    styleHdrBk.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleHdrBk.setFont(fontHdrWh);
    SortedSet autoCols = null;
    HSSFRow row = null ;
    HSSFCell cell = null ;

    int colNum = 0;
    int rowNm = 0;
    int rapValInx =0;
    int totalCell=0;
    int stCol = ++cellCt, stRow = rowCt;
    boolean isSrch = false;
    String logoNme =util.nvl((String)pageDtl.get("LOGO"));
    String NAME = util.nvl((String)pageDtl.get("NAME"));
    if(!logoNme.equals("")){
    try {
    String servPath = req.getRealPath("/images/clientsLogo");

    if(servPath.indexOf("/") > -1)
    servPath += "/" ;
    else {
    //servPath = servPath.replace('\', '\\');
    servPath += "\\" ;
    }
    FileInputStream fis=new FileInputStream(servPath+logoNme);
    ByteArrayOutputStream img_bytes = new ByteArrayOutputStream();
    int b;
    while ((b = fis.read()) != -1)
    img_bytes.write(b);
    fis.close();
    int imgWdt = 800, imgHt = 185;

    row = sheet.createRow((short)++rowCt);

    HSSFClientAnchor anchor =
    new HSSFClientAnchor(1, 1, imgWdt, imgHt, (short)5, rowCt,
    (short)(stCol+8), (stRow + 3));

    rowCt = stRow + 2;
    cellCt = (short)(stCol + 5);
    int index =
    hwb.addPicture(img_bytes.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG);
    HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
    HSSFPicture logo = patriarch.createPicture(anchor, index);

    anchor.setAnchorType(2);

    } catch (FileNotFoundException fnfe) {
    // TODO: Add catch code
    fnfe.printStackTrace();
    } catch (IOException ioe) {
    // TODO: Add catch code
    ioe.printStackTrace();
    }

    }else if(!NAME.equals("")){
    row = sheet.createRow((short)2);
    cell = row.createCell(1);
    cell.setCellValue(getStr(NAME,fontHdrWh));
    cell.setCellStyle(styleHdrBk);
    sheet.addMergedRegion(merge(2, 2, 1, 8));
    }

    String add1 =util.nvl((String)pageDtl.get("ADD1"));
    if(!add1.equals("")){
    rowCt++;
    if(NAME.equals("")){
    row = sheet.createRow((short)2);
    }
    cell = row.createCell(10);
    cell.setCellValue(add1);
    cell.setCellStyle(styleHeaderBig);
    sheet.addMergedRegion(merge(2, 2, 10, ADDWIDTH));

    }
    String add2 =util.nvl((String)pageDtl.get("ADD2"));
    if(!add2.equals("")){

    row = sheet.createRow((short)3);
    cell = row.createCell(10);
    cell.setCellValue(add2);
    cell.setCellStyle(styleHeaderBig);
    sheet.addMergedRegion(merge(3, 3, 10, ADDWIDTH));
    rowCt++;
    }

    autoCols = new TreeSet();
    ArrayList colHdr = new ArrayList();
    ArrayList vwPrpLst = util.getMemoXl(mdl);
    ArrayList prpDspBlocked = info.getPageblockList();
    if(vwPrpLst==null)
    util.initSrch();
    int shInx = vwPrpLst.indexOf("SH");
    int crtWtInx = vwPrpLst.indexOf("CRTWT");
    int rteInx = vwPrpLst.indexOf("RTE");
    int rapDisInx = vwPrpLst.indexOf("RAP_DIS");
    int addindex=0;
    rowCt=rowCt+2;
    row = sheet.createRow((short)++rowCt);
    cellCt = -1;
    cell = row.createCell(++cellCt);
    cell.setCellValue("Sr No");
    colHdr.add("Sr No.");
    addindex++;
    colNum = cell.getColumnIndex();
    autoCols.add(Integer.toString(colNum));
    //sheet.autoSizeColumn((short)colNum, true);

    cell.setCellStyle(styleHdr);
    cell = row.createCell(++cellCt);
    cell.setCellValue("Packet Id");
    colHdr.add("PKT");
    addindex++;
    colNum = cell.getColumnIndex();
    autoCols.add(Integer.toString(colNum));
    //sheet.autoSizeColumn((short)colNum, true);

    cell.setCellStyle(styleHdr);

    cell = row.createCell(++cellCt);
    cell.setCellValue("Status");
    colHdr.add("Status");
    colNum = cell.getColumnIndex();
    autoCols.add(Integer.toString(colNum));
    //sheet.autoSizeColumn((short)colNum, true);
    cell.setCellStyle(styleHdr);

    try {
        String cpPrp = "prte";
            if(vwPrpLst.contains("CP"))
            cpPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("CP")+1);
    HashMap mprp = info.getMprp();
    String getQuot = "quot rte";

    String srchQ =
    " select sk1, cts crtwt, "+
    //decode(quot, 0, 0, decode(nvl(rap_dis,0), 0, 101, trunc(((quot*100)/greatest(rap_rte,100)) - 100,2))) rr_dis, "+
    " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis)) r_dis " +
    " , stk_idn, rap_rte , vnm, kts_vw kts, cert_lab cert, cert_no, rmk, img,img2, to_char(trunc(cts,2),'99999990.00') cts, "+ getQuot
    +", rap_rte,srt_090, trunc(cts,2)*rap_Rte rap_vlu "
    +", rmk , cmnt cmnt ,to_char(trunc(cts,2) * quot, '99999990.00') amt, stt, nvl("+cpPrp+",prte) prte , nvl(nvl("+cpPrp+",prte),0)*nvl(cts,0) cptotal ,trunc(((greatest(nvl("+cpPrp+",prte),1)*100)/greatest(rap_rte,1)) - 100,2) cpdis  " ;

    //sheet.autoSizeColumn((short)colNum, true);

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

    String prp = (String)vwPrpLst.get(i);
    String prpDsc = prp;
    if(prpDspBlocked.contains(prp)){
    }else{
    if(prp.equals("RCHK")){


    cell = row.createCell(++cellCt);
    cell.setCellValue("Recheck Comments");
    colHdr.add(prp);
    cell.setCellStyle(styleHdr);
    colNum = cell.getColumnIndex();
    autoCols.add(Integer.toString(colNum));


    }else{
    cell = row.createCell(++cellCt);
    cell.setCellValue(prpDsc);
    colHdr.add(prp);
    cell.setCellStyle(styleHdr);
    colNum = cell.getColumnIndex();
    autoCols.add(Integer.toString(colNum));

    }
    }}
    //sheet.autoSizeColumn((short)colNum, true);


    //Freezws are
    String flg = "M";
    if(nvl(typ).equals("SRCH"))
    flg = "Z";
    if(nvl(typ).equals("LAB"))
    flg = "LC";
    if(nvl(typ).equals("TMKT"))
    flg="S";
    sheet.createFreezePane(0, rowCt+1);
    String srchQ1 = srchQ;
    String rsltQ ="";
        if(mdl.equals("LBCOM_VIEW") && (cnt.equals("pm") || cnt.equals("asha"))){
            rsltQ=srchQ + " from gt_srch_rslt where flg=? union "+srchQ1+" from " +
            " gt_srch_multi a where exists (select 1 from gt_srch_rslt b1 where b1.stk_idn = a.stk_idn and a.flg = ? ) " +
            " order by srt_090, vnm ";
        }else{
            rsltQ=srchQ + " from gt_srch_rslt where flg=? union "+srchQ1+" from " +
            " gt_srch_multi a where exists (select 1 from gt_srch_rslt b1 where b1.stk_idn = a.stk_idn and a.flg = ? ) " +
            " order by sk1 , vnm ";
        }

    ArrayList ary = new ArrayList();
    ary.add(flg);
    ary.add(flg);
        ArrayList outLst = db.execSqlLst("stock", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    stRow = rowCt+1;
    int srno=0;

    if((crtWtInx > rteInx) && rteInx!=-1)
    crtWtInx++;
    if((rapDisInx > rteInx) && rteInx!=-1)
    rapDisInx++;
    int rapRteInx =vwPrpLst.indexOf("RAP_RTE");
    if((rapRteInx > rteInx) && rteInx!=-1)
    rapRteInx++;
    int sr=0;
    while (rs.next()) {
    sr++;
    isSrch = true;

    String stt = util.nvl(rs.getString("stt"));
    totalCell = cellCt;
    row = sheet.createRow((short)++rowCt);
    cellCt = -1;
    cell = row.createCell(++cellCt);
        cell.setCellValue(Double.parseDouble(String.valueOf(sr)));
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cell.setCellStyle(styleData);
    //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
    colNum = cell.getColumnIndex();
    autoCols.add(Integer.toString(colNum));

    cell = row.createCell(++cellCt);
    cell.setCellValue(util.nvl(rs.getString("vnm")));
    cell.setCellStyle(styleData);
    //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
    colNum = cell.getColumnIndex();
    autoCols.add(Integer.toString(colNum));

    cell = row.createCell(++cellCt);
    cell.setCellValue(stt);
    cell.setCellStyle(styleData);
    //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
    colNum = cell.getColumnIndex();
    autoCols.add(Integer.toString(colNum));
    for (int i = 0; i < vwPrpLst.size(); i++) {
    String fld = "prp_";
    int j = i + 1;
    if (j < 10)
    fld += "00" + j;
    else if (j < 100)
    fld += "0" + j;
    else if (j > 100)
    fld += j;
    String prp = (String)vwPrpLst.get(i);
    String prptyp = util.nvl((String)mprp.get(prp+"T"));
    if (prp.equals("VNM"))
    fld = "vnm";
    if(prpDspBlocked.contains(prp)){
    }else{
    String fldVal = nvl(rs.getString(fld));
    if(prp.equals("KTSVIEW"))
    fldVal = nvl(rs.getString("kts"));
    if(prp.equals("COMMENT"))
    fldVal = nvl(rs.getString("cmnt"));
    if(prp.equals("RAP_RTE"))
    fldVal = nvl(rs.getString("rap_rte"));
    if(prp.equals("RTE"))
    fldVal = nvl(rs.getString("rte"));
    if(prp.equals("RAP_DIS"))
    fldVal = nvl(rs.getString("r_dis"));
    if(prp.equals("CP"))
    fldVal = util.nvl(rs.getString("prte"));
    if(prp.equals("CP_DIS"))
    fldVal = util.nvl(rs.getString("cpdis"));
    if(prp.equals("MEM_COMMENT"))
    fldVal = nvl(rs.getString("img2"));
    fldVal = fldVal.trim();
    if(fldVal.equals("NA"))
    fldVal="";
    if(prp.equals("RCHK")){



    fldVal = nvl(rs.getString("img"));
    if(stt.equals("LB_CF"))
    fldVal = "PRINT";
    cell = row.createCell(++cellCt);
    cell.setCellValue(fldVal);
    cell.setCellStyle(styleData);
    colNum = cell.getColumnIndex();
    autoCols.add(Integer.toString(colNum));



    }else if(prp.equals("RTE") || prp.equals("CRTWT") || prptyp.equals("N")){
        
    cell = row.createCell(++cellCt);
    if(!fldVal.equals("") && fldVal.indexOf("#")==-1 && fldVal.indexOf("?")==-1 && fldVal.indexOf("%")==-1 && fldVal.indexOf("*")==-1){
        try{  
              cell.setCellValue(Double.parseDouble(fldVal));
              cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
              cell.setCellStyle(numStyleData);
          }catch(NumberFormatException nfe){  
              cell.setCellValue(fldVal);
              cell.setCellStyle(styleData); 
          }  
    }else {
    cell.setCellValue(fldVal);
    cell.setCellStyle(styleData);
    }
    colNum = cell.getColumnIndex();
    autoCols.add(Integer.toString(colNum));

    } else{

    cell = row.createCell(++cellCt);
    cell.setCellValue(fldVal);
    cell.setCellStyle(styleData);
    colNum = cell.getColumnIndex();
    autoCols.add(Integer.toString(colNum));

    }
    }}

    }
        rs.close();
        pst.close();

    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }



    return hwb ;
    }
    
    public HSSFWorkbook getDataGridInXl(HttpServletRequest req,String colSpan,String qty,String cts,String avg,String rap,String vlu,String fapri,String mfgpri,String agedis,String report) {
    hwb = new HSSFWorkbook();
    autoColums = new TreeSet();
    session = req.getSession(false);
    String fontNm = "Geneva";
    fontHdr = hwb.createFont();
    fontHdr.setFontHeightInPoints((short)11);
    fontHdr.setFontName(fontNm);
    fontHdr.setColor(HSSFColor.BLACK.index);
    fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

    styleHdr = hwb.createCellStyle();
    styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleHdr.setBorderBottom((short)1);
    styleHdr.setBorderTop((short)1);
    styleHdr.setBorderLeft((short)1);
    styleHdr.setBorderRight((short)1);
    styleHdr.setFont(fontHdr);


    fontData = hwb.createFont();
    fontData.setFontHeightInPoints((short)10);
    fontData.setFontName(fontNm);

    styleData = hwb.createCellStyle();
    styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleData.setFont(fontData);
    ArrayList hdrLst= new ArrayList();
    ArrayList contentList= new ArrayList();
    HashMap dataTbl= new HashMap();
    HashMap reportDtl= (HashMap)session.getAttribute("reportDtl");
    if(reportDtl!=null && reportDtl.size()>0){
    hdrLst=(ArrayList)reportDtl.get(report+"_HDR");
    contentList=(ArrayList)reportDtl.get(report+"_CONTENT");
    dataTbl=(HashMap)reportDtl.get(report+"_DATA");
    }
    String TITLE=util.nvl((String)dataTbl.get("TITLE"));
    HashMap data=new HashMap();
    String Qty="";
    String Cts="";
    String Avg="";
    String Dis="";
    String Vlu="";
    String Fapri="";
    String Mfgpri="";
    String age="";
    int span=Integer.parseInt(colSpan);
    sheet = addSheet();
    row = newRow();
    int currCell = ++cellCt;
    // CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    addCell(currCell++, TITLE, styleHdr);

    for(int i=0;i<hdrLst.size();i++)
    {
    currCell = ++cellCt;
    // rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    rang = mergeCell(rowCt, rowCt, currCell, currCell+span);
    addMergeCell(currCell, (String)hdrLst.get(i), styleHdr, rang);
    cellCt = currCell+span;
    }

    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell+span);
    addMergeCell(currCell++, "Total", styleHdr, rang);

    cellCt = currCell+1;
    row = newRow();
    addCell(++cellCt, "", styleHdr);
    for(int i=0;i<=hdrLst.size();i++)
    {
    if(qty.equals("Y"))
    addCell(++cellCt, "QTY", styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, "CTS", styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, "AVG", styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, "RAP", styleHdr);
    if(agedis.equals("Y"))
    addCell(++cellCt, "AGE", styleHdr);
    if(vlu.equals("Y"))
    addCell(++cellCt, "VLU", styleHdr);
    if(fapri.equals("Y"))
    addCell(++cellCt, "FAPRI", styleHdr);
    if(mfgpri.equals("Y"))
    addCell(++cellCt, "MFGPRI", styleHdr);
     }
    sheet.createFreezePane(0, rowCt+1);

    for(int j=0;j<contentList.size();j++){
    String contnt=(String)contentList.get(j);
    String dsc=util.nvl2((String)dataTbl.get(contnt),contnt);
    row = newRow();
    addCell(++cellCt,dsc, styleHdr);
    for(int k=0;k<hdrLst.size();k++){
    String hdr=(String)hdrLst.get(k);
    data=new HashMap();
    Qty="";
    Cts="";
    Avg="";
    Dis="";
    Vlu="";
    Fapri="";
    Mfgpri="";
    age="";
    data=(HashMap)dataTbl.get(contnt+"_"+hdr);
    if(data!=null && data.size()>0){
    Qty=util.nvl((String)data.get("QTY"));
    Cts=util.nvl((String)data.get("CTS"));
    Avg=util.nvl((String)data.get("AVG"));
    Dis=util.nvl((String)data.get("RAP"));
    Vlu=util.nvl((String)data.get("VLU"));
    Fapri=util.nvl((String)data.get("FAPRI"));
    Mfgpri=util.nvl((String)data.get("MFGPRI"));
        age=util.nvl((String)data.get("AGE"));
    }
    if(qty.equals("Y"))
    addCell(++cellCt, Qty, styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, Cts, styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, Avg, styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, Dis, styleHdr);
        if(agedis.equals("Y"))
        addCell(++cellCt, age, styleHdr);
    if(vlu.equals("Y"))
    addCell(++cellCt, Vlu, styleHdr);
    if(fapri.equals("Y"))
    addCell(++cellCt, Fapri, styleHdr);
    if(mfgpri.equals("Y"))
    addCell(++cellCt, Mfgpri, styleHdr);
  
    }
    Qty="";
    Cts="";
    Avg="";
    Dis="";
    Vlu="";
    Fapri="";
    Mfgpri="";
        age="";
    data=new HashMap();
    data=(HashMap)dataTbl.get(contnt+"_TTL");
    if(data!=null && data.size()>0){
    Qty=util.nvl((String)data.get("QTY"));
    Cts=util.nvl((String)data.get("CTS"));
    Avg=util.nvl((String)data.get("AVG"));
    Dis=util.nvl((String)data.get("RAP"));
    Vlu=util.nvl((String)data.get("VLU"));
    Fapri=util.nvl((String)data.get("FAPRI"));
    Mfgpri=util.nvl((String)data.get("MFGPRI"));
        age=util.nvl((String)data.get("AGE"));
    }
    if(qty.equals("Y"))
    addCell(++cellCt, Qty, styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, Cts, styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, Avg, styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, Dis, styleHdr);
        if(agedis.equals("Y"))
        addCell(++cellCt, age, styleHdr);
    if(vlu.equals("Y"))
    addCell(++cellCt, Vlu, styleHdr);
    if(fapri.equals("Y"))
    addCell(++cellCt, Fapri, styleHdr);
    if(mfgpri.equals("Y"))
    addCell(++cellCt, Mfgpri, styleHdr);
    }
    row = newRow();
    addCell(++cellCt, "Total", styleHdr);
    for(int i=0;i<hdrLst.size();i++){
    String lab=(String)hdrLst.get(i);
    Qty="";
    Cts="";
    Avg="";
    Dis="";
    Vlu="";
    Fapri="";
    Mfgpri="";
        age="";
    data=new HashMap();
    data=(HashMap)dataTbl.get(lab+"_TTL");
    if(data!=null && data.size()>0){
    Qty=util.nvl((String)data.get("QTY"));
    Cts=util.nvl((String)data.get("CTS"));
    Avg=util.nvl((String)data.get("AVG"));
    Dis=util.nvl((String)data.get("RAP"));
    Vlu=util.nvl((String)data.get("VLU"));
    Fapri=util.nvl((String)data.get("FAPRI"));
    Mfgpri=util.nvl((String)data.get("MFGPRI"));
        age=util.nvl((String)data.get("AGE"));
    }
    if(qty.equals("Y"))
    addCell(++cellCt, Qty, styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, Cts, styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, Avg, styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, Dis, styleHdr);
        if(agedis.equals("Y"))
        addCell(++cellCt, age, styleHdr);
    if(vlu.equals("Y"))
    addCell(++cellCt, Vlu, styleHdr);    
    if(fapri.equals("Y"))
    addCell(++cellCt, Fapri, styleHdr);
    if(mfgpri.equals("Y"))
    addCell(++cellCt, Mfgpri, styleHdr);
    }
    Qty="";
    Cts="";
    Avg="";
    Dis="";
    Vlu="";
    Fapri="";
    Mfgpri="";
        age="";
    data=new HashMap();
    data=(HashMap)dataTbl.get("TTL");
    if(data!=null && data.size()>0){
    Qty=util.nvl((String)data.get("QTY"));
    Cts=util.nvl((String)data.get("CTS"));
    Avg=util.nvl((String)data.get("AVG"));
    Dis=util.nvl((String)data.get("RAP"));
    Vlu=util.nvl((String)data.get("VLU"));
    Fapri=util.nvl((String)data.get("FAPRI"));
    Mfgpri=util.nvl((String)data.get("MFGPRI"));
        age=util.nvl((String)data.get("AGE"));
    }
    if(qty.equals("Y"))
    addCell(++cellCt, Qty, styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, Cts, styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, Avg, styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, Dis, styleHdr);
        if(agedis.equals("Y"))
        addCell(++cellCt, age, styleHdr);
    if(vlu.equals("Y"))
    addCell(++cellCt, Vlu, styleHdr);
    if(fapri.equals("Y"))
    addCell(++cellCt, Fapri, styleHdr);
    if(mfgpri.equals("Y"))
    addCell(++cellCt, Mfgpri, styleHdr);   
    Iterator it=autoColums.iterator();
    while(it.hasNext()) {
    String value=(String)it.next();
    int colId = Integer.parseInt(value);
    sheet.autoSizeColumn((short)colId, true);
    }
    return hwb;
    }

    public HSSFWorkbook getDataAkhaniXlSH(HttpServletRequest req ,ArrayList memoList, ArrayList shapeList) {
       hwb = new HSSFWorkbook();
       autoColums = new TreeSet();
       session = req.getSession(false);
       String fontNm = "Geneva";
       fontHdr = hwb.createFont();
       fontHdr.setFontHeightInPoints((short)8);
       fontHdr.setFontName(fontNm);
       fontHdr.setColor(HSSFColor.BLACK.index);
       fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

       styleHdr = hwb.createCellStyle();
       styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
       styleHdr.setBorderBottom((short)1);
       styleHdr.setBorderTop((short)1);
       styleHdr.setBorderLeft((short)1);
       styleHdr.setBorderRight((short)1);
       styleHdr.setFont(fontHdr);


       fontData = hwb.createFont();
       fontData.setFontHeightInPoints((short)8);
       fontData.setFontName(fontNm);

       styleData = hwb.createCellStyle();
       styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
       styleData.setBorderBottom((short)1);
       styleData.setBorderTop((short)1);
       styleData.setBorderLeft((short)1);
       styleData.setBorderRight((short)1);
       styleData.setFont(fontData);
       
         HSSFFont fontDataNormal = hwb.createFont();
         fontDataNormal.setFontHeightInPoints((short)8);
         fontDataNormal.setFontName(fontNm);

         HSSFFont fontDataRed = hwb.createFont();
         fontDataRed.setFontHeightInPoints((short)8);
         fontDataRed.setFontName(fontNm);
         fontDataRed.setColor(HSSFColor.WHITE.index);

         HSSFFont fontDataGreen = hwb.createFont();
         fontDataGreen.setFontHeightInPoints((short)8);
         fontDataGreen.setFontName(fontNm);
         fontDataGreen.setColor(HSSFColor.WHITE.index);
         
         HSSFCellStyle styleDataNormal = hwb.createCellStyle();
         styleDataNormal.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         styleDataNormal.setFont(fontDataNormal);
         styleDataNormal.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
         styleDataNormal.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
         styleDataNormal.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
         styleDataNormal.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
         styleDataNormal.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
         styleDataNormal.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
         styleDataNormal.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
         styleDataNormal.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);

         HSSFCellStyle styleDataRed = hwb.createCellStyle();
         styleDataRed.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         styleDataRed.setFillForegroundColor(HSSFColor.RED.index);
         styleDataRed.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
         styleDataRed.setFont(fontDataGreen);
         styleDataRed.setBorderBottom((short)1);
         styleDataRed.setBorderTop((short)1);
         styleDataRed.setBorderLeft((short)1);
         styleDataRed.setBorderRight((short)1);

          HSSFCellStyle styleDataGreen = hwb.createCellStyle();        
          styleDataGreen.setAlignment(HSSFCellStyle.ALIGN_CENTER);
          styleDataGreen.setFillForegroundColor(HSSFColor.GREEN.index);
          styleDataGreen.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
          styleDataGreen.setFont(fontDataGreen);
          styleDataGreen.setBorderBottom((short)1);
          styleDataGreen.setBorderTop((short)1);
          styleDataGreen.setBorderLeft((short)1);
          styleDataGreen.setBorderRight((short)1);
      
         HashMap dataDtl= (HashMap)session.getAttribute("dataDtlSH");
    
       if(dataDtl!=null && dataDtl.size()>0){
       double getttlcts=0,getaakhaold=0,getaakhasrt=0,getmanojnew=0,getmanojold=0,getaakhanewvlu=0,getaakhaoldvlu=0,getmnjnewvlu=0,getmnjoldvlu=0;
       double vlujama=0,vlunew=0,vlunewavg=0;
       String percentgia="",percentnew="",percentper="",diffaakhanew="",percentsrt="";
       double aakhanew=0;
      String diffmnj="0",olddiff="0",srtdiff="0",gdavgdiff="0",diffmnjcolor="",olddiffcolor="",srtdiffcolor="",gdavgdiffcolor="";

       sheet = addSheet();
       row = newRow();
       row = newRow();
       int currCell = ++cellCt;
       CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Memo", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Shape", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Carat", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "96-UP-GIA CTS", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Aakha ni K.OLD", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Aakha ni GD SRT", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Aakha New", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "OLD", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Old Avg SRT", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "New Avg", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Manoj Old", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Manoj New", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, " Diff ", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Manoj Old", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Manoj New", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Diff Manoj", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "K.Old Diff %", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "GD SRT Diff%", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "GD AVG DIFF", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "%", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "96 up GIA", styleHdr);
       cellCt = currCell;
       
    for(int i=0;i<memoList.size();i++){
    String memoVal=util.nvl((String)memoList.get(i));
    percentsrt=util.nvl2((String)dataDtl.get(memoVal+"_SRTPER"),"0");
    for(int j=0;j<shapeList.size();j++){    
    String shapeVal=util.nvl((String)shapeList.get(j));    
    String ttlcts=util.nvl2((String)dataDtl.get(memoVal+"_"+shapeVal+"_CTS"),"0").trim();
    String deptcts=util.nvl2((String)dataDtl.get(memoVal+"_"+shapeVal+"_96-UP-GIA_CTS"),"0").trim();
    String aakhaold=util.nvl2((String)dataDtl.get(memoVal+"_"+shapeVal+"_AAKHAOLD"),"0");
    String aakhasrt=util.nvl2((String)dataDtl.get(memoVal+"_"+shapeVal+"_AAKHASRT"),"0");
    aakhasrt=String.valueOf(Math.round(util.roundToDecimals((Double.parseDouble(aakhaold) - ((Double.parseDouble(aakhaold)*Double.parseDouble(percentsrt))/100)),1)));
    String aakhaoldvlu=util.nvl2((String)dataDtl.get(memoVal+"_"+shapeVal+"_AAKHAOLDVLU"),"0");
    String aakhasrtvlu=util.nvl2((String)dataDtl.get(memoVal+"_"+shapeVal+"_AAKHASRTVLU"),"0");
    String mnjold=util.nvl2((String)dataDtl.get(memoVal+"_"+shapeVal+"_96-UP-GIA_MNJOLD"),"0");
    String mnjnew=util.nvl2((String)dataDtl.get(memoVal+"_"+shapeVal+"_96-UP-GIA_MNJNEW"),"0");
    String mnjoldvlu=util.nvl2((String)dataDtl.get(memoVal+"_"+shapeVal+"_96-UP-GIA_MNJOLDVLU"),"0");
    String mnjnewvlu=util.nvl2((String)dataDtl.get(memoVal+"_"+shapeVal+"_96-UP-GIA_MNJNEWVLU"),"0");
    percentper=util.nvl2((String)dataDtl.get(memoVal+"_"+shapeVal+"_BKPER"),"0");
    aakhanew=0;
    diffmnj="0";olddiff="0";srtdiff="0";gdavgdiff="0";diffmnjcolor="";olddiffcolor="";srtdiffcolor="";gdavgdiffcolor="";
    getmnjnewvlu=Double.parseDouble(mnjnewvlu);
    getmnjoldvlu=Double.parseDouble(mnjoldvlu);
    getttlcts=Double.parseDouble(ttlcts);
    getaakhaold=Double.parseDouble(aakhaold);
    getaakhasrt=Double.parseDouble(aakhasrt);
    getmanojnew=Double.parseDouble(mnjnew);
    getmanojold=Double.parseDouble(mnjold);
    getaakhaoldvlu=Double.parseDouble(aakhaoldvlu);
    if(!mnjnew.equals("0") && !mnjold.equals("0")){
    diffmnj=String.valueOf(util.roundToDecimals(Math.round((((getmanojnew-getmanojold)*100)/getmanojold)),1));
    }
    if(!mnjoldvlu.equals("0") && !mnjnewvlu.equals("0")){
    diffaakhanew=String.valueOf(Math.round(util.roundToDecimals(((getmnjnewvlu-getmnjoldvlu)/getttlcts),1)));
    aakhanew=Double.parseDouble(aakhaold)+Double.parseDouble(diffaakhanew);
    vlunewavg=util.roundToDecimals(aakhanew*getttlcts,1);
    }
    if(!aakhaold.equals("0")){
    olddiff=String.valueOf(Math.round(util.roundToDecimals((((aakhanew-getaakhaold)*100)/getaakhaold),1)));
    srtdiff=String.valueOf(Math.round(util.roundToDecimals((((aakhanew-getaakhasrt)*100)/getaakhasrt),1)));
    gdavgdiff=String.valueOf(Math.round(util.roundToDecimals((((getaakhasrt-getaakhaold)*100)/getaakhaold),1)));
    percentgia=String.valueOf(Math.round(util.roundToDecimals(((getmnjoldvlu/getaakhaoldvlu)*100),1)));
    }

    if(Double.parseDouble(diffmnj)>=3)
    diffmnjcolor="green";

    if(Double.parseDouble(diffmnj)<=-3)
    diffmnjcolor="red";

    if(Double.parseDouble(olddiff)>=3)
    olddiffcolor="green";

    if(Double.parseDouble(olddiff)<=-3)
    olddiffcolor="red";

    if(Double.parseDouble(srtdiff)>=3)
    srtdiffcolor="green";

    if(Double.parseDouble(srtdiff)<=-3)
    srtdiffcolor="red";

    if(Double.parseDouble(gdavgdiff)>=3)
    gdavgdiffcolor="green";

    if(Integer.parseInt(gdavgdiff)<=-3)
    gdavgdiffcolor="red";

    if(!diffmnj.equals("") && !diffmnj.equals("0"))
    diffmnj=diffmnj+"%";

    if(!percentper.equals("") && !percentper.equals("0"))
    percentper=percentper+"%";

    if(!percentgia.equals("") && !percentgia.equals("0"))
    percentgia=percentgia+"%";

    if(!olddiff.equals("") && !olddiff.equals("0"))
    olddiff=olddiff+"%";

    if(!srtdiff.equals("") && !srtdiff.equals("0"))
    srtdiff=srtdiff+"%";

    if(!gdavgdiff.equals("") && !gdavgdiff.equals("0"))
    gdavgdiff=gdavgdiff+"%";

       row = newRow();
       addCell(++cellCt,memoVal, styleData);
       addCell(++cellCt,shapeVal, styleData); 
       addCell(++cellCt,ttlcts, styleData,"");
       addCell(++cellCt, deptcts, styleData,"");
       addCell(++cellCt, aakhaold, styleData,"");
       addCell(++cellCt, aakhasrt, styleData,"");
       addCell(++cellCt, String.valueOf(aakhanew), styleData,"");
       addCell(++cellCt, aakhaoldvlu, styleData,"");
       addCell(++cellCt, aakhasrtvlu, styleData,"");
       addCell(++cellCt,String.valueOf(vlunewavg), styleData,"");
       addCell(++cellCt,mnjold, styleData,"");
       addCell(++cellCt, mnjnew, styleData,"");
       addCell(++cellCt, diffaakhanew, styleData,"");
       addCell(++cellCt, mnjoldvlu, styleData,"");
       addCell(++cellCt, mnjnewvlu, styleData,"");
       if(diffmnjcolor.equals("red"))
       styleData=styleDataRed;
       if(diffmnjcolor.equals("green"))
       styleData=styleDataGreen;
       addCell(++cellCt, diffmnj, styleData);
       styleData=styleDataNormal;
       if(olddiffcolor.equals("red"))
       styleData=styleDataRed;
       if(olddiffcolor.equals("green"))
       styleData=styleDataGreen;
       addCell(++cellCt, olddiff, styleData);
       styleData=styleDataNormal;
       if(srtdiffcolor.equals("red"))
       styleData=styleDataRed;
       if(srtdiffcolor.equals("green"))
       styleData=styleDataGreen;
       addCell(++cellCt, srtdiff, styleData);
       styleData=styleDataNormal; 
       if(gdavgdiffcolor.equals("red"))
       styleData=styleDataRed;
       if(gdavgdiffcolor.equals("green"))
       styleData=styleDataGreen;
       addCell(++cellCt, gdavgdiff, styleData);
       styleData=styleDataNormal;
       addCell(++cellCt, percentper, styleData);
       addCell(++cellCt, percentgia, styleData);
    }
    }
    String ttlcts=util.nvl2((String)dataDtl.get("TOTAL_TOTAL_CTS"),"0").trim();
    String deptcts=util.nvl2((String)dataDtl.get("TOTAL_TOTAL_96-UP-GIA_CTS"),"0").trim();
    String aakhaold=util.nvl2((String)dataDtl.get("TOTAL_TOTAL_AAKHAOLD"),"0");
    String aakhasrt=util.nvl2((String)dataDtl.get("TOTAL_TOTAL_AAKHASRT"),"0");
    String aakhaoldvlu=util.nvl2((String)dataDtl.get("TOTAL_TOTAL_AAKHAOLDVLU"),"0");
    String aakhasrtvlu=util.nvl2((String)dataDtl.get("TOTAL_TOTAL_AAKHASRTVLU"),"0");
    String mnjold=util.nvl2((String)dataDtl.get("TOTAL_TOTAL_96-UP-GIA_MNJOLD"),"0");
    String mnjnew=util.nvl2((String)dataDtl.get("TOTAL_TOTAL_96-UP-GIA_MNJNEW"),"0");
    String mnjoldvlu=util.nvl2((String)dataDtl.get("TOTAL_TOTAL_96-UP-GIA_MNJOLDVLU"),"0");
    String mnjnewvlu=util.nvl2((String)dataDtl.get("TOTAL_TOTAL_96-UP-GIA_MNJNEWVLU"),"0");
    percentper=util.nvl2((String)dataDtl.get("TOTAL_TOTAL_BKPER"),"0");
    aakhanew=0;
    diffmnj="0";olddiff="0";srtdiff="0";gdavgdiff="0";diffmnjcolor="";olddiffcolor="";srtdiffcolor="";gdavgdiffcolor="";
    getmnjnewvlu=Double.parseDouble(mnjnewvlu);
    getmnjoldvlu=Double.parseDouble(mnjoldvlu);
    getttlcts=Double.parseDouble(ttlcts);
    getaakhaold=Double.parseDouble(aakhaold);
    getaakhasrt=Double.parseDouble(aakhasrt);
    getmanojnew=Double.parseDouble(mnjnew);
    getmanojold=Double.parseDouble(mnjold);
    getaakhaoldvlu=Double.parseDouble(aakhaoldvlu);
    if(!mnjnew.equals("0") && !mnjold.equals("0")){
    diffmnj=String.valueOf(util.roundToDecimals(Math.round((((getmanojnew-getmanojold)*100)/getmanojold)),1));
    }
    if(!mnjoldvlu.equals("0") && !mnjnewvlu.equals("0")){
    diffaakhanew=String.valueOf(Math.round(util.roundToDecimals(((getmnjnewvlu-getmnjoldvlu)/getttlcts),1)));
    aakhanew=Double.parseDouble(aakhaold)+Double.parseDouble(diffaakhanew);
    vlunewavg=util.roundToDecimals(aakhanew*getttlcts,1);
    }
    if(!aakhaold.equals("0")){
    olddiff=String.valueOf(Math.round(util.roundToDecimals((((aakhanew-getaakhaold)*100)/getaakhaold),1)));
    srtdiff=String.valueOf(Math.round(util.roundToDecimals((((aakhanew-getaakhasrt)*100)/getaakhasrt),1)));
    gdavgdiff=String.valueOf(Math.round(util.roundToDecimals((((getaakhasrt-getaakhaold)*100)/getaakhaold),1)));
    percentgia=String.valueOf(Math.round(util.roundToDecimals(((getmnjoldvlu/getaakhaoldvlu)*100),1)));
    }

    if(Double.parseDouble(diffmnj)>=3)
    diffmnjcolor="green";

    if(Double.parseDouble(diffmnj)<=-3)
    diffmnjcolor="red";

    if(Double.parseDouble(olddiff)>=3)
    olddiffcolor="green";

    if(Double.parseDouble(olddiff)<=-3)
    olddiffcolor="red";

    if(Double.parseDouble(srtdiff)>=3)
    srtdiffcolor="green";

    if(Double.parseDouble(srtdiff)<=-3)
    srtdiffcolor="red";

    if(Double.parseDouble(gdavgdiff)>=3)
    gdavgdiffcolor="green";

    if(Double.parseDouble(gdavgdiff)<=-3)
    gdavgdiffcolor="red";

    if(!diffmnj.equals("") && !diffmnj.equals("0"))
    diffmnj=diffmnj+"%";

    if(!percentper.equals("") && !percentper.equals("0"))
    percentper=percentper+"%";

    if(!percentgia.equals("") && !percentgia.equals("0"))
    percentgia=percentgia+"%";

    if(!olddiff.equals("") && !olddiff.equals("0"))
    olddiff=olddiff+"%";


    if(!srtdiff.equals("") && !srtdiff.equals("0"))
    srtdiff=srtdiff+"%";

    if(!gdavgdiff.equals("") && !gdavgdiff.equals("0"))
    gdavgdiff=gdavgdiff+"%";
       
       row = newRow();
       addCell(++cellCt, "TOTAL", styleData);
       addCell(++cellCt, "", styleData);
       addCell(++cellCt, ttlcts, styleData,"");
       addCell(++cellCt, deptcts, styleData,"");
       addCell(++cellCt, aakhaold, styleData,"");
       addCell(++cellCt, aakhasrt, styleData,"");
       addCell(++cellCt, String.valueOf(aakhanew), styleData,"");
       addCell(++cellCt, aakhaoldvlu, styleData,"");
       addCell(++cellCt, aakhasrtvlu, styleData,"");
       addCell(++cellCt,String.valueOf(vlunewavg), styleData,"");
       addCell(++cellCt,  mnjold, styleData,"");
       addCell(++cellCt, mnjnew, styleData,"");
       addCell(++cellCt, diffaakhanew, styleData,"");
       addCell(++cellCt, mnjoldvlu, styleData,"");
       addCell(++cellCt, mnjnewvlu, styleData,"");
          if(diffmnjcolor.equals("red"))
          styleData=styleDataRed;
          if(diffmnjcolor.equals("green"))
          styleData=styleDataGreen;
          addCell(++cellCt, diffmnj, styleData);
          styleData=styleDataNormal;
          if(olddiffcolor.equals("red"))
          styleData=styleDataRed;
          if(olddiffcolor.equals("green"))
          styleData=styleDataGreen;
          addCell(++cellCt, olddiff, styleData);
          styleData=styleDataNormal;
          if(srtdiffcolor.equals("red"))
          styleData=styleDataRed;
          if(srtdiffcolor.equals("green"))
          styleData=styleDataGreen;
          addCell(++cellCt, srtdiff, styleData);
          styleData=styleDataNormal;
          if(gdavgdiffcolor.equals("red"))
          styleData=styleDataRed;
          if(gdavgdiffcolor.equals("green"))
          styleData=styleDataGreen;
          addCell(++cellCt, gdavgdiff, styleData);
          styleData=styleDataNormal;
       addCell(++cellCt, percentper, styleData);
       addCell(++cellCt, percentgia, styleData);
      }
       
     return hwb;
     }
    public HSSFWorkbook getDataInXl(String typ,HttpServletRequest req ,String mdl) {
                
                  int rowCt = 0;
                  short cellCt = -1;
                  String fontNm = "Cambria";
                  HSSFWorkbook hwb = new HSSFWorkbook();
                  HSSFSheet sheet = hwb.createSheet(typ);
                  //CreationHelper createHelper = hwb.getCreationHelper();
              
                  HSSFCellStyle hlink_style = hwb.createCellStyle();
                  HSSFFont hlink_font = hwb.createFont();
                  hlink_font.setUnderline(HSSFFont.U_SINGLE);
                  hlink_font.setFontName(fontNm);
                  hlink_font.setColor(HSSFColor.BLUE.index);
                  hlink_style.setFont(hlink_font);
                  //hlink_style.setFillPattern(HSSFCellStyle.SPARSE_DOTS);
                  //hlink_style.setFillForegroundColor(HSSFColor.BLUE.index);
                  
                  HSSFFont fontHdr = hwb.createFont();
                  fontHdr.setFontHeightInPoints((short)10);
                  fontHdr.setFontName(fontNm);
                  fontHdr.setColor(HSSFColor.BLACK.index);
                  fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
              
                  HSSFCellStyle styleHdr = hwb.createCellStyle();
                  styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                  styleHdr.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
                  styleHdr.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                  styleHdr.setFont(fontHdr);
                  styleHdr.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                  styleHdr.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                  styleHdr.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                  styleHdr.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                  styleHdr.setTopBorderColor(HSSFColor.BLACK.index);
                  styleHdr.setBottomBorderColor(HSSFColor.BLACK.index);
                  styleHdr.setLeftBorderColor(HSSFColor.BLACK.index);
                  styleHdr.setRightBorderColor(HSSFColor.BLACK.index);
                 
                  //styleHdr.BORDER_DOTTED;
                  //styleHdr.set
              
                  HSSFFont fontData = hwb.createFont();
                  fontData.setFontHeightInPoints((short)10);
                  fontData.setFontName(fontNm);
                  fontData.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
                  
                  HSSFFont fontStt = hwb.createFont();
                  fontStt.setFontHeightInPoints((short)10);
                  fontStt.setFontName(fontNm);
                  
                  
                  HSSFCellStyle styleDIS = hwb.createCellStyle();
                  styleDIS.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                  styleDIS.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
                  styleDIS.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                  styleDIS.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                  styleDIS.setFont(fontStt);
                  styleDIS.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                  styleDIS.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                  styleDIS.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                  styleDIS.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                  styleDIS.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                  styleDIS.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                  styleDIS.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                  styleDIS.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                  HSSFPalette palette1 = hwb.getCustomPalette();
                  palette1.setColorAtIndex(HSSFColor.SKY_BLUE.index, (byte)153,(byte)204, (byte)255);
                  
                  HSSFCellStyle styleData = hwb.createCellStyle();
                  styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                  styleData.setFont(fontData);
                  styleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                  styleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                  styleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                  styleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                  styleData.setTopBorderColor(HSSFColor.BLACK.index);
                  styleData.setBottomBorderColor(HSSFColor.BLACK.index);
                  styleData.setLeftBorderColor(HSSFColor.BLACK.index);
                  styleData.setRightBorderColor(HSSFColor.BLACK.index);
                 
                  //styleData.setDataFormat(HSSFDataFormat);
              
                  HSSFFont fontDataBig = hwb.createFont();
                  fontDataBig.setFontHeightInPoints((short)10);
                  fontDataBig.setFontName(fontNm);
                  fontDataBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
              
                  HSSFCellStyle styleBig = hwb.createCellStyle();
                  styleBig.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                  styleBig.setFont(fontDataBig);
                  styleBig.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                  styleBig.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                  styleBig.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                  styleBig.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                  styleBig.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                  styleBig.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                  styleBig.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                  styleBig.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                  styleBig.setFillForegroundColor(HSSFColor.BLUE.index);
                 
                  
                  
                  HSSFFont fontDataBold = hwb.createFont();
                  fontDataBold.setFontHeightInPoints((short)10);
                  fontDataBold.setFontName(fontNm);
                  fontDataBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
              
                  HSSFCellStyle styleBold = hwb.createCellStyle();
                  styleBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                  styleBold.setFont(fontDataBold);
                  styleBold.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                  styleBold.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                  styleBold.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                  styleBold.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                  styleBold.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                  styleBold.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                  styleBold.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                  styleBold.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                 
                  SortedSet autoCols = null;
                  HashMap exlFormat = util.EXLFormat();
                  HSSFRow row = null ;
                  HSSFCell cell = null ;
                
                  int colNum = 0;
                  int rowNm = 0;
                  int rapValInx =0;
                  int totalCell=0;
                  int stCol = ++cellCt, stRow = rowCt;
                  boolean isSrch = false;
                  String logoNme = (String)exlFormat.get("LOGO");
                  if(!logoNme.equals("")){
                       try {
                           String servPath = req.getRealPath("/images/clientsLogo");
       
                            if(servPath.indexOf("/") > -1)
                            servPath += "/" ;
                            else {
                                                  //servPath = servPath.replace('\', '\\');
                                                  servPath += "\\" ;
                              }
                           FileInputStream fis=new FileInputStream(servPath+logoNme);
                           ByteArrayOutputStream img_bytes = new ByteArrayOutputStream();
                           int b;
                           while ((b = fis.read()) != -1)
                               img_bytes.write(b);
                           fis.close();
                           int imgWdt = 108, imgHt = 96;
                          
                           row = sheet.createRow((short)++rowCt);
       
                           HSSFClientAnchor anchor =
                               new HSSFClientAnchor(1, 1, imgWdt, imgHt, (short)9, rowCt,
                                                    (short)(stCol+4), (stRow + 5));
       
                           rowCt = stRow + 5;
                           cellCt = (short)(stCol + 5);
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
       
                  }
                  rowCt++;
                  String add1 = (String)exlFormat.get("ADD1");
                  if(!add1.equals("")){
                    
                            row = sheet.createRow((short)2);
                            cell = row.createCell(11);
                            cell.setCellValue(add1);
                            cell.setCellStyle(styleBig);
                            sheet.addMergedRegion(merge(2, 2, 11, 26));
                        
                  }
                  String add2 = (String)exlFormat.get("ADD2");
                  if(!add2.equals("")){
                    
                            row = sheet.createRow((short)3);
                            cell = row.createCell(11);
                            cell.setCellValue(add2);
                            cell.setCellStyle(styleBig);
                            sheet.addMergedRegion(merge(3, 3, 11, 26));
                        
                   }
                  
                  autoCols = new TreeSet();
                  rowCt=rowCt+3;
                  ArrayList colHdr = new ArrayList();
                  ArrayList vwPrpLst = util.getMemoXl(mdl);
                  if(vwPrpLst==null)
                      util.initSrch();

                  
        
              try {
                
              
              
                 
                
                
                  HashMap mprp = info.getMprp();
       

                  String getQuot = "quot rte";
                 
                    String srchQ =
                      " select sk1, cts crtwt, "+
                      //decode(quot, 0, 0, decode(nvl(rap_dis,0), 0, 101, trunc(((quot*100)/greatest(rap_rte,100)) - 100,2))) rr_dis, "+
                      " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis " +
                      " , stk_idn, rap_rte , vnm, kts_vw kts, cert_lab cert, cert_no, rmk, img,img2, to_char(trunc(cts,2),'90.99') cts, "+ getQuot
                      +", rap_rte, trunc(cts,2)*rap_Rte rap_vlu "
                      +", rmk , cmnt cmnt ,to_char(trunc(cts,2) * quot, '99999990.00') amt, decode(stt, 'MKAV', 'Available', 'MKIS', 'MEMO', 'MKTL',' ', stt) stt " ;

                   String srchQ1="";
                  
                  row = sheet.createRow((short)++rowCt);
                  cellCt = -1;
                  cell = row.createCell(++cellCt);
                  cell.setCellValue("Packet Id");
                  colHdr.add("PKT");
                  colNum = cell.getColumnIndex(); 
                  autoCols.add(Integer.toString(colNum));
                  //sheet.autoSizeColumn((short)colNum, true);
                          
                  cell.setCellStyle(styleHdr);
              
              
                 
                  //sheet.autoSizeColumn((short)colNum, true);

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
                  
                  String prp = (String)vwPrpLst.get(i);
                  String prpDsc = (String)mprp.get(prp);
                   
                  if(prp.equals("RCHK")){
                      cell = row.createCell(++cellCt);
                      cell.setCellValue("Memo No");
                      colHdr.add(prp);
                      cell.setCellStyle(styleHdr);
                      colNum = cell.getColumnIndex();
                      autoCols.add(Integer.toString(colNum));
                      
                      cell = row.createCell(++cellCt);
                      cell.setCellValue("Report Comments");
                      colHdr.add(prp);
                      cell.setCellStyle(styleHdr);
                      colNum = cell.getColumnIndex();
                      autoCols.add(Integer.toString(colNum));
                      
                      
                  }else{
                          cell = row.createCell(++cellCt);
                          cell.setCellValue(prpDsc);
                          colHdr.add(prp);
                          cell.setCellStyle(styleHdr);
                          colNum = cell.getColumnIndex();
                          autoCols.add(Integer.toString(colNum));
                  if (prp.equals("RTE")){
                      cell = row.createCell(++cellCt);
                      cell.setCellValue("Amount");
                      colHdr.add("AMT");
                      cell.setCellStyle(styleHdr);
                      colNum = cell.getColumnIndex();
                      autoCols.add(Integer.toString(colNum));
                  }
                  }
              }                   
                      //sheet.autoSizeColumn((short)colNum, true);
           
                      cell = row.createCell(++cellCt);
                      cell.setCellValue("Rap Vlu");
                      colHdr.add("RAPVAL");
                     
                      cell.setCellStyle(styleHdr);
                      colNum = cell.getColumnIndex(); 
                      autoCols.add(Integer.toString(colNum));
                      //Freezws are
                  String flg = "M";
               if(nvl(typ).equals("SRCH"))
                   flg = "Z";
              if(nvl(typ).equals("LAB"))
                   flg = "LC";
                  sheet.createFreezePane(0, rowCt+1);
                     srchQ1 = srchQ;
                      String rsltQ =
                          srchQ + " from gt_srch_rslt  where flg=? union "+srchQ1+" from " +
                          "  gt_srch_multi a  where exists (select 1 from gt_srch_rslt b1 where b1.stk_idn = a.stk_idn and a.flg = ? ) " +
                          " order by sk1 ";
                      
              ArrayList numParam = new ArrayList();
                  numParam.add("CRTWT");
                  numParam.add("RTE");
                  numParam.add("RAP_DIS");
                  numParam.add("RAP_RTE");
              ArrayList ary = new ArrayList();
              ary.add(flg);
              ary.add(flg);

                      ArrayList outLst = db.execSqlLst("stock", rsltQ, ary);
                      PreparedStatement pst = (PreparedStatement)outLst.get(0);
                      ResultSet rs = (ResultSet)outLst.get(1);
              String grpCts ="";
              String grpVlu = "";
              String grpAvgDis ="";
              String grpRapVlu = "";
              String grpRteVlu = "";
              String grpAvgVlue = "";
                  String grpQty = "";
                      stRow = rowCt+1;
                  while (rs.next()) {

                  isSrch = true;

                  String cts =util.nvl(rs.getString("cts"));
                  String certNo = util.nvl(rs.getString("cert_no"));
                  totalCell = cellCt;
                  row = sheet.createRow((short)++rowCt);
                  cellCt = -1;

                  String vnm = rs.getString("vnm");





                  cell = row.createCell(++cellCt);
                  cell.setCellValue(vnm);
                  cell.setCellStyle(styleData);
                  //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                  colNum = cell.getColumnIndex();
                  autoCols.add(Integer.toString(colNum));




                  for (int i = 0; i < vwPrpLst.size(); i++) {
                  String fld = "prp_";
                  int j = i + 1;
                  if (j < 10)
                  fld += "00" + j;
                  else if (j < 100)
                  fld += "0" + j;
                  else if (j > 100)
                  fld += j;
                  String prp = (String)vwPrpLst.get(i);
                  String prptyp = util.nvl((String)mprp.get(prp+"T"));
                  if (prp.equals("RTE"))
                  fld = "rte";
                  String fldVal = nvl(rs.getString(fld));
                  if (prp.equals("RAP_DIS"))
                  fldVal = nvl(rs.getString("r_dis"));
                  if (prp.equals("RAP_RTE"))
                  fldVal = nvl(rs.getString("rap_rte"));
                  if(prp.equals("KTSVIEW"))
                      fldVal = nvl(rs.getString("kts"));
                  if(prp.equals("COMMENT"))
                      fldVal = nvl(rs.getString("cmnt"));
                   if(prp.equals("MEM_COMMENT"))
                      fldVal = nvl(rs.getString("img2"));

                  fldVal = fldVal.trim();
                  if(fldVal.equals("NA"))
                      fldVal="";
                  if(prp.equals("LAB") && fldVal.equals("GIA")){
                  cell = row.createCell(++cellCt);
                  cell.setCellValue(fldVal);
                  String link =(String)exlFormat.get("CERTLINK");
                  if(!link.equals("")){
                  link = link.replace("CERTNME",certNo);
                  link = link.replace("WT", cts);
                  HSSFHyperlink link1 =
                  new HSSFHyperlink(HSSFHyperlink.LINK_URL);
                  link1.setAddress(link);
                  cell.setHyperlink(link1);
                  cell.setCellStyle(hlink_style);
                  }else{
                  cell.setCellStyle(styleData);
                  }
                  colNum = cell.getColumnIndex();
                  autoCols.add(Integer.toString(colNum));
                  }else if(prp.equals("RCHK")){
                  
                      fldVal = "NA";
                      cell = row.createCell(++cellCt);
                      cell.setCellValue(fldVal);
                      cell.setCellStyle(styleData);
                      colNum = cell.getColumnIndex();
                      autoCols.add(Integer.toString(colNum));
                      
                      fldVal = nvl(rs.getString("cmnt"));
                      cell = row.createCell(++cellCt);
                      cell.setCellValue(fldVal);
                      cell.setCellStyle(styleData);
                      colNum = cell.getColumnIndex();
                      autoCols.add(Integer.toString(colNum));
                      
                      
                      
                  }else{
                      
                  cell = row.createCell(++cellCt);
                  if(!fldVal.equals("") && numParam.contains(prp) ){
                  cell.setCellValue(Double.parseDouble(fldVal));
                  cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                  }else {
                  cell.setCellValue(fldVal);
                  }


                  cell.setCellStyle(styleData);
                  colNum = cell.getColumnIndex();
                  autoCols.add(Integer.toString(colNum));
                      if (prp.equals("RTE")){
                          cell = row.createCell(++cellCt);
                          fldVal= nvl(rs.getString("amt"));
                          if(!fldVal.equals("") && fldVal.indexOf("#")==-1){
                          cell.setCellValue(Double.parseDouble(fldVal));
                          cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                          }else {
                          cell.setCellValue(fldVal);
                          }


                          cell.setCellStyle(styleData);
                          colNum = cell.getColumnIndex();
                          autoCols.add(Integer.toString(colNum));
                      }
                  }
                  }
                   String rapVal = nvl(rs.getString("rap_vlu"));
                      if(!rapVal.equals("")){
                   cell = row.createCell(++cellCt);
                   cell.setCellValue(rs.getDouble("rap_vlu"));
                   cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                   cell.setCellStyle(styleData);
                   colNum = cell.getColumnIndex(); 
                   autoCols.add(Integer.toString(colNum));  
                      }
                      
               }
                      rs.close();
                      pst.close();
                  if(isSrch){
                  stRow++;
                  row = sheet.createRow((short)++rowCt);
                  HashMap ttls = util.getTtls(req);
                      int endrow=rowCt;
                if(ttls.size() > 0){
                      grpCts = (String)ttls.get(flg+"W");
                      grpVlu = (String)ttls.get(flg+"V");
                      grpAvgDis=(String)ttls.get(flg+"D");
                      grpRapVlu=(String)ttls.get(flg+"R");
                      grpQty =(String)ttls.get(flg+"Q");
                      grpAvgVlue=(String)ttls.get(flg+"A");
                    
                    
                      int cwtInx = vwPrpLst.indexOf("CRTWT");
                      int rap_dis = vwPrpLst.indexOf("RAP_DIS");
                      int rteInx = vwPrpLst.indexOf("RTE");
                      int rapRteInx = vwPrpLst.indexOf("RAP_RTE");
                      cell = row.createCell(0);
                      cell.setCellValue("Grand Total");
                      cell.setCellStyle(styleBold);
                      colNum = cell.getColumnIndex();
                      autoCols.add(Integer.toString(colNum));
                        if(cwtInx>0){
                      cell = row.createCell(cwtInx+1);
                       int crtwtCol=colHdr.indexOf("CRTWT");
                       String crtwtcolName=GetAlpha(crtwtCol+1);
                     
                      String strFormula = "SUM("+crtwtcolName+stRow+":"+crtwtcolName+endrow+")";
                      cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                      cell.setCellFormula(strFormula);

                      cell.setCellStyle(styleBold);
                      colNum = cell.getColumnIndex();
                      autoCols.add(Integer.toString(colNum));
                       }
                     if(rap_dis>0){
                           int amtCol=colHdr.indexOf("AMT");
                           String amtColName=GetAlpha(amtCol+1);
                          
                           int rapVluCol=colHdr.indexOf("RAPVAL");
                           String rapVluColName=GetAlpha(rapVluCol+1);
                           
                         
                           //    String strFormula = "SUM("+colName+stRow+":"+colName+endrow+")";
                           String strFormula="(( ("+amtColName+(endrow+1)+")/("+rapVluColName+(endrow+1)+"))*100)-100";
                      cell = row.createCell(rap_dis+1);
                     
                       cell.setCellFormula(strFormula);
                      cell.setCellStyle(styleBold);
                      colNum = cell.getColumnIndex();
                     cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                      autoCols.add(Integer.toString(colNum));
                       }
                       if(rteInx>0){  
                      cell = row.createCell(rteInx+1);
                      int crtwtCol=colHdr.indexOf("CRTWT");
                      String crtwtColName=GetAlpha(crtwtCol+1);
                      int rteCol=colHdr.indexOf("RTE");
                      String rtecolName=GetAlpha(rteCol+1);
                     
                              String strFormula="";
                                for(int i=stRow ;i<=endrow;i++) {
                                    if((i==endrow) && (stRow==endrow)) {
                                   strFormula+="(("+crtwtColName+i+"*"+rtecolName+i+"))";    
                                    }else
                                    {
                                 if(i==stRow) {
                                   strFormula+="(("+crtwtColName+i+"*"+rtecolName+i+")+";
                                 }else if(i==endrow) {
                                  strFormula+="("+crtwtColName+i+"*"+rtecolName+i+"))";   
                                    }
                                    else
                                    {
                                  strFormula+="("+crtwtColName+i+"*"+rtecolName+i+")+";
                                    }
                                    }
                                }
                              strFormula = strFormula+"/("+crtwtColName+(endrow+1)+")";
                      cell.setCellStyle(styleBold);
                      colNum = cell.getColumnIndex();
                      cell.setCellFormula(strFormula);
                     
                      cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                      autoCols.add(Integer.toString(colNum));
                          }
       //                       if(rapRteInx>1){
       //                   cell = row.createCell(rapRteInx+1);
       //                   if(!grpRapVlu.equals(""))
       //                   {
       //                   cell.setCellValue(Double.parseDouble(grpRapVlu));
       //                   cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
       //                   }
       //                   else {
       //                   cell.setCellValue(grpRapVlu);
       //                   }
       //                   cell.setCellStyle(styleBold);
       //                   colNum = cell.getColumnIndex();
       //                   autoCols.add(Integer.toString(colNum));
       //                    }
                    if(rteInx>0){  
                           int amtCol=colHdr.indexOf("AMT");
                          
                          cell = row.createCell(rteInx+2);
                        
                           String colName=GetAlpha(amtCol+1);
                           String strFormula = "SUM("+colName+stRow+":"+colName+endrow+")";
                          cell.setCellFormula(strFormula);
                          cell.setCellStyle(styleBold);
                          colNum = cell.getColumnIndex();
                         cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                          autoCols.add(Integer.toString(colNum));
                       }
                    
                          rapValInx=colHdr.indexOf("RAPVAL");
                          cell = row.createCell(rapValInx);
                          String rapVlucolName=GetAlpha(rapValInx+1);
                          String strFormula1 = "SUM("+rapVlucolName+stRow+":"+rapVlucolName+endrow+")";
                          cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                          cell.setCellFormula(strFormula1);
                          cell.setCellStyle(styleBold);
                          colNum = cell.getColumnIndex();
                          autoCols.add(Integer.toString(colNum));
                    
                      Iterator it=autoCols.iterator();
                      while(it.hasNext()) {
                      String value=(String)it.next();
                      int colId = Integer.parseInt(value);
                      sheet.autoSizeColumn((short)colId, true);

                      }

                      String sumSheet = (String)exlFormat.get("SHEET_SMRY");
                      if(sumSheet.equals("YES")){
                      rowCt= rowCt+2;

                      row = sheet.createRow((short)++rowCt);
                      cell = row.createCell(0);
                      cell.setCellValue("Details Description of Diamonds with given Prices");
                      cell.setCellStyle(styleBig);
                      sheet.addMergedRegion(merge(rowCt, rowCt, 0, 6));
                  
                      row = sheet.createRow((short)++rowCt);
                      cell = row.createCell(0);
                      cell.setCellValue("Total Pcs");
                      cell.setCellStyle(styleBig);

                      sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
                         
                      cell = row.createCell(3);
                      
                      if(!grpQty.equals("") && grpQty.indexOf("#")==-1)
                      {
                      cell.setCellValue(Double.parseDouble(grpQty));
                      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                      }else {
                      cell.setCellValue(grpQty);
                      }
                      cell.setCellStyle(styleBig);

                      sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
                     
       //                       if(cwtInx>0){
       //                   row = sheet.createRow((short)++rowCt);
       //                   cell = row.createCell(0);
       //                   cell.setCellValue("Total Crts");
       //                   cell.setCellStyle(styleBig);
       //
       //                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
       //                   cell = row.createCell(3);
       //                   if(!grpCts.equals(""))
       //                   {
       //                   cell.setCellValue(Double.parseDouble(grpCts));
       //                   cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
       //                   }else {
       //                   cell.setCellValue(grpCts);
       //                   }
       //                   cell.setCellStyle(styleBig);
       //
       //                   sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
       //                       }
                          if(rap_dis>0){
                          row = sheet.createRow((short)++rowCt);
                          cell = row.createCell(0);
                          cell.setCellValue("Total Avg.Dis");
                          cell.setCellStyle(styleBig);

                          sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
                          cell = row.createCell(3);
                           int amtCol=colHdr.indexOf("AMT");
                           String amtColName=GetAlpha(amtCol+1);
                           int rapVluCol=colHdr.indexOf("RAPVAL");
                           String rapVluColName=GetAlpha(rapVluCol+1);
                           String strFormula="(( ("+amtColName+(endrow+1)+")/("+rapVluColName+(endrow+1)+"))*100)-100";
                           cell.setCellFormula(strFormula);
                           cell.setCellStyle(styleBig);
                           cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                          sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
                          }
       //                       if(rapRteInx>1){
       //                   row = sheet.createRow((short)++rowCt);
       //                   cell = row.createCell(0);
       //                   cell.setCellValue("Total Avg.Rap%");
       //                   cell.setCellStyle(styleBig);
       //
       //                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
       //                   cell = row.createCell(3);
       //                   if(!grpRapVlu.equals(""))
       //                   {
       //                   cell.setCellValue(Double.parseDouble(grpRapVlu));
       //                   cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
       //                   }else {
       //                   cell.setCellValue(grpRapVlu);
       //                   }
       //                   cell.setCellStyle(styleBig);
       //
       //                   sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
       //                       }
                          if(rteInx>0){    
                      row = sheet.createRow((short)++rowCt);
                      cell = row.createCell(0);
                      cell.setCellValue("Total Avg. Price ($)");
                      cell.setCellStyle(styleBig);

                      sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));

                      cell = row.createCell(3);
                         
                              int crtwtCol=colHdr.indexOf("CRTWT");
                              String crtwtColName=GetAlpha(crtwtCol+1);
                              int rteCol=colHdr.indexOf("RTE");
                              String rtecolName=GetAlpha(rteCol+1);
                            
                                      String strFormula="";
                                        for(int i=stRow ;i<=endrow;i++) {
                                            if((i==endrow) && (stRow==endrow)) {
                                           strFormula+="(("+crtwtColName+i+"*"+rtecolName+i+"))";    
                                            }else
                                            {
                                         if(i==stRow) {
                                           strFormula+="(("+crtwtColName+i+"*"+rtecolName+i+")+";
                                         }else if(i==endrow) {
                                          strFormula+="("+crtwtColName+i+"*"+rtecolName+i+"))";   
                                            }
                                            else
                                            {
                                          strFormula+="("+crtwtColName+i+"*"+rtecolName+i+")+";
                                            }
                                            }
                                        }
                              strFormula = strFormula+"/("+crtwtColName+(endrow+1)+")";
                              cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                              cell.setCellStyle(styleBig);
                              colNum = cell.getColumnIndex();
                              cell.setCellFormula(strFormula);
                            
                      sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));

                          }
                          if(rteInx>0){  
                      row = sheet.createRow((short)++rowCt);
                      cell = row.createCell(0);
                      cell.setCellValue("Total Amt. ($)");
                      cell.setCellStyle(styleBig);

                      sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
                     int amtCol=colHdr.indexOf("AMT");
                      cell = row.createCell(3);
                       String colName=GetAlpha(amtCol+1);
                       String strFormula = "SUM("+colName+stRow+":"+colName+endrow+")";
                       cell.setCellFormula(strFormula);
                       cell.setCellStyle(styleBold);
                       colNum = cell.getColumnIndex();
                       autoCols.add(Integer.toString(colNum));
                      cell.setCellStyle(styleBig);
                       cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                      sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
                          }
                      }
                      }
                  }

                  } catch (SQLException sqle) {
                  // TODO: Add catch code
                  sqle.printStackTrace();
                  }



                  return hwb ;
                  }
    
  
    
    
    
    public HSSFWorkbook getDataKapuInXl(String typ,HttpServletRequest req ,String mdl) {
             
               int rowCt = 0;
               short cellCt = -1;
                String fontNm = "Arial";
               HSSFWorkbook hwb = new HSSFWorkbook();
               HSSFSheet sheet = hwb.createSheet(typ);
               //CreationHelper createHelper = hwb.getCreationHelper();
           
               HSSFCellStyle hlink_style = hwb.createCellStyle();
               HSSFFont hlink_font = hwb.createFont();
               hlink_font.setUnderline(HSSFFont.U_SINGLE);
               hlink_font.setFontName(fontNm);
               hlink_font.setColor(HSSFColor.BLUE.index);
                hlink_font.setFontHeightInPoints((short)9);
               hlink_style.setFont(hlink_font);
               //hlink_style.setFillPattern(HSSFCellStyle.SPARSE_DOTS);
               //hlink_style.setFillForegroundColor(HSSFColor.BLUE.index);
               
               HSSFFont fontHdr = hwb.createFont();
               fontHdr.setFontHeightInPoints((short)10);
               fontHdr.setFontName(fontNm);
               fontHdr.setColor(HSSFColor.WHITE.index);
               fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
               
               HSSFCellStyle styleHdr = hwb.createCellStyle();
               styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
               styleHdr.setFillForegroundColor(HSSFColor.RED.index);
               styleHdr.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
               styleHdr.setFont(fontHdr);
               styleHdr.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
               styleHdr.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
               styleHdr.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
               styleHdr.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
               styleHdr.setTopBorderColor(HSSFColor.BLACK.index);
               styleHdr.setBottomBorderColor(HSSFColor.BLACK.index);
               styleHdr.setLeftBorderColor(HSSFColor.BLACK.index);
               styleHdr.setRightBorderColor(HSSFColor.BLACK.index);
               HSSFPalette palette = hwb.getCustomPalette();
               palette.setColorAtIndex(HSSFColor.SKY_BLUE.index, (byte)153,(byte)204, (byte)255);
               //styleHdr.BORDER_DOTTED;
               //styleHdr.set
               
               HSSFFont fontData = hwb.createFont();
               fontData.setFontHeightInPoints((short)9);
               fontData.setFontName(fontNm);
               
               HSSFFont fontStt = hwb.createFont();
               fontStt.setFontHeightInPoints((short)10);
               fontStt.setFontName(fontNm);
               
               HSSFCellStyle styleGreen = hwb.createCellStyle();
               styleGreen.setAlignment(HSSFCellStyle.ALIGN_CENTER);
               styleGreen.setFillForegroundColor(HSSFColor.RED.index);
               styleGreen.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
               styleGreen.setFont(fontData);
               styleGreen.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
               styleGreen.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
               styleGreen.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
               styleGreen.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
               styleGreen.setTopBorderColor(HSSFColor.BLACK.index);
               styleGreen.setBottomBorderColor(HSSFColor.BLACK.index);
               styleGreen.setLeftBorderColor(HSSFColor.BLACK.index);
               styleGreen.setRightBorderColor(HSSFColor.BLACK.index);
               HSSFPalette paletteGrenn = hwb.getCustomPalette();
               paletteGrenn.setColorAtIndex(HSSFColor.SKY_BLUE.index, (byte)156,(byte)255, (byte)0);
               
               HSSFCellStyle styleOrange = hwb.createCellStyle();
               styleOrange.setAlignment(HSSFCellStyle.ALIGN_CENTER);
               styleOrange.setFillForegroundColor(HSSFColor.RED.index);
               styleOrange.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
               styleOrange.setFont(fontData);
               styleOrange.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
               styleOrange.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
               styleOrange.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
               styleOrange.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
               styleOrange.setTopBorderColor(HSSFColor.BLACK.index);
               styleOrange.setBottomBorderColor(HSSFColor.BLACK.index);
               styleOrange.setLeftBorderColor(HSSFColor.BLACK.index);
               styleOrange.setRightBorderColor(HSSFColor.BLACK.index);
               HSSFPalette paletteOrange = hwb.getCustomPalette();
               paletteOrange.setColorAtIndex(HSSFColor.SKY_BLUE.index, (byte)255,(byte)214, (byte)165);
               
               
               
               
               HSSFCellStyle styleStt = hwb.createCellStyle();
               styleStt.setAlignment(HSSFCellStyle.ALIGN_CENTER);
               //styleStt.setFillForegroundColor(HSSFColor.BLUE.index);
               styleStt.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
               styleStt.setFont(fontStt);
               styleStt.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
               styleStt.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
               styleStt.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
               styleStt.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
               styleStt.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
               styleStt.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
               styleStt.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
               styleStt.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
               
               
               HSSFCellStyle styleData = hwb.createCellStyle();
               styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
               styleData.setFont(fontData);
               styleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
               styleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
               styleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
               styleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
               styleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
               styleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
               styleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
               styleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);

               HSSFCellStyle numStyleData = hwb.createCellStyle();
               numStyleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
               numStyleData.setFont(fontData);
               numStyleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
               numStyleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
               numStyleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
               numStyleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
               numStyleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
              numStyleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
               numStyleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
               numStyleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                 HSSFDataFormat df = hwb.createDataFormat();
                 numStyleData.setDataFormat(df.getFormat("#0.00#"));
               //styleData.setDataFormat(HSSFDataFormat);
               
               HSSFFont fontDataBig = hwb.createFont();
               fontDataBig.setFontHeightInPoints((short)10);
               fontDataBig.setFontName(fontNm);
               fontDataBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
               
               HSSFCellStyle styleBig = hwb.createCellStyle();
               styleBig.setAlignment(HSSFCellStyle.ALIGN_CENTER);
               styleBig.setFont(fontDataBig);
               styleBig.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
               styleBig.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
               styleBig.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
               styleBig.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
               styleBig.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
               styleBig.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
               styleBig.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
               styleBig.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
               styleBig.setFillForegroundColor(HSSFColor.BLUE.index);
               
               
               
               HSSFFont fontDataBold = hwb.createFont();
               fontDataBold.setFontHeightInPoints((short)10);
               fontDataBold.setFontName(fontNm);
               fontDataBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
               
               HSSFCellStyle styleBold = hwb.createCellStyle();
               styleBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
               styleBold.setFont(fontDataBold);
               styleBold.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
               styleBold.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
               styleBold.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
               styleBold.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
               styleBold.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
               styleBold.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
               styleBold.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
               styleBold.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
               
              
               SortedSet autoCols = null;
               HashMap exlFormat = util.EXLFormat();
               HSSFRow row = null ;
               HSSFCell cell = null ;
             
               int colNum = 0;
               int rowNm = 0;
               int rapValInx =0;
               int totalCell=0;
               int stCol = ++cellCt, stRow = rowCt;
               boolean isSrch = false;
               String logoNme = (String)exlFormat.get("LOGO");
                  
               if(!logoNme.equals("")){
                    try {
                        String servPath = req.getRealPath("/images/clientsLogo");
    
                         if(servPath.indexOf("/") > -1)
                         servPath += "/" ;
                         else {
                                               //servPath = servPath.replace('\', '\\');
                                               servPath += "\\" ;
                           }
                        FileInputStream fis=new FileInputStream(servPath+logoNme);
                        ByteArrayOutputStream img_bytes = new ByteArrayOutputStream();
                        int b;
                        while ((b = fis.read()) != -1)
                            img_bytes.write(b);
                        fis.close();
                        int imgWdt = 241, imgHt = 85;
                       
                        row = sheet.createRow((short)++rowCt);
    
                        HSSFClientAnchor anchor =
                            new HSSFClientAnchor(1, 1, imgWdt, imgHt, (short)9, rowCt,
                                                 (short)(stCol+7), (stRow + 5));
    
                        rowCt = stRow + 5;
                        cellCt = (short)(stCol + 5);
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
    
               }
               
               String add1 = (String)exlFormat.get("ADD1");
               if(!add1.equals("")){
                   rowCt++;
                         row = sheet.createRow((short)2);
                         cell = row.createCell(11);
                         cell.setCellValue(add1);
                         cell.setCellStyle(styleBig);
                         sheet.addMergedRegion(merge(2, 2, 11, 26));
                     
               }
               String add2 = (String)exlFormat.get("ADD2");
               if(!add2.equals("")){
                 
                         row = sheet.createRow((short)3);
                         cell = row.createCell(11);
                         cell.setCellValue(add2);
                         cell.setCellStyle(styleBig);
                         sheet.addMergedRegion(merge(3, 3, 11, 26));
                     
                }
               
               autoCols = new TreeSet();
                   rowCt=rowCt+1;
               ArrayList colHdr = new ArrayList();
               ArrayList vwPrpLst = util.getMemoXl(mdl);
               if(vwPrpLst==null)
                   util.initSrch();
                int shInx = vwPrpLst.indexOf("SH");
                   int crtWtInx = vwPrpLst.indexOf("CRTWT");
                   
                   int rteInx = vwPrpLst.indexOf("RTE");
                   int rapDisInx = vwPrpLst.indexOf("RAP_DIS");
                 String SZ_GRP =util.nvl((String)exlFormat.get("SZ_GRP"));
                
           try {
             
                   int ct = db.execCall("ud_dsp_stt", "pkgmkt.upd_dsp_stt", new ArrayList());
           
                   String updGrpSzQ = " Update gt_srch_rslt set prp_050 = get_grp_sz(cts) ";
                    ct = db.execUpd(" Upd GT Grp sz", updGrpSzQ, new ArrayList());

             
             
               HashMap mprp = info.getMprp();
//                   util.StockViewLyt();
                   HashMap stockViewMap = info.getStockViewMap();
                String exhRte = util.nvl((String)req.getAttribute("exhRte"));
               if(exhRte.equals(""))
                   exhRte="1";
                   
                        String imageDir = "E:/websites/kapugemsonline/";
                          //  String imageDir = "E:\\";
                    HashMap vwDtl = null;
               String getQuot = "quot rte";
              
                 String srchQ =
                   " select sk1, cts crtwt, "+
                   //decode(quot, 0, 0, decode(nvl(rap_dis,0), 0, 101, trunc(((quot*100)/greatest(rap_rte,100)) - 100,2))) rr_dis, "+
                   " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis " +
                   " , stk_idn, rap_rte , vnm, kts_vw kts, cert_lab cert, cert_no, rmk, dsp_stt , img,  cmp , trunc(((cmp*100)/greatest(rap_rte,1)) - 100,2) cmp_dis , to_char(trunc(cts,2),'90.999') cts, "+ getQuot
                   +", rap_rte, trunc(cts,2)*rap_Rte rap_vlu "
                   +", rmk , cmnt cmnt ,to_char(trunc(cts,2) * quot, '99999990.00') amt, decode(stt, 'MKAV', 'Available', 'MKIS', 'MEMO', 'MKTL',' ', 'SOLD') stt " ;
               if(shInx!=-1 && SZ_GRP.equals("Y")){
                   shInx = shInx+1;
                   String prpSh = "prp_00"+shInx;
                   if(shInx > 9)
                       prpSh = "prp_0"+shInx;
                   srchQ =" select sk1, cts crtwt, "+
                                      //decode(quot, 0, 0, decode(nvl(rap_dis,0), 0, 101, trunc(((quot*100)/greatest(rap_rte,100)) - 100,2))) rr_dis, "+
                      " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis " +
                      " , stk_idn, rap_rte , vnm, kts_vw kts, cert_lab cert, cert_no, rmk, dsp_stt , img,  cmp , trunc(((cmp*100)/greatest(rap_rte,1)) - 100,2) cmp_dis , to_char(trunc(cts,2),'90.999') cts, "+ getQuot
                     +", rap_rte, trunc(cts,2)*rap_Rte rap_vlu "
                     +", rmk , cmnt cmnt ,to_char(trunc(cts,2) * quot, '99999990.00') amt, decode(stt, 'MKAV', 'Available', 'MKIS', 'MEMO', 'MKTL',' ', 'SOLD') stt  "
                   +", sum(trunc(cts,2)) over(partition by cert_lab,"+prpSh+", prp_050) grp_cts "
                    +", sum(trunc(cts,2)*quot) over(partition by cert_lab, "+prpSh+", prp_050) grp_vlu "
                   +", trunc( (sum(trunc(cts,2)*quot) over(partition by cert_lab, "+prpSh+", prp_050))/ sum(trunc(cts, 2)) over(partition by cert_lab, "+prpSh+", prp_050),2) grp_avg_vlu "
                     +", trunc( (sum(trunc(cts,2)*rap_rte ) over(partition by cert_lab, "+prpSh+", prp_050))/sum(trunc(cts, 2)) over(partition by cert_lab, "+prpSh+", prp_050),2) grp_rap_vlu "
                     +", trunc((sum(trunc(cts,2)*(quot/"+exhRte+")) over(partition by cert_lab, "+prpSh+", prp_050)*100/sum(trunc(cts,2)*rap_rte ) over(partition by cert_lab, "+prpSh+", prp_050)) - 100,2) grp_avg_dis ";
                                                       
                             
               }
              
               
               row = sheet.createRow((short)++rowCt);
               cellCt = -1;
                cell = row.createCell(++cellCt);
                cell.setCellValue("Sr No");
                colHdr.add("Sr No.");
                colNum = cell.getColumnIndex(); 
                   cell.setCellStyle(styleHdr);
               autoCols.add(Integer.toString(colNum));
                   //sheet.autoSizeColumn((short)colNum, true);
                   vwDtl = (HashMap)stockViewMap.get("VWIMG"); 
                   if(vwDtl!=null){
                cell = row.createCell(++cellCt);
                   cell.setCellValue("Image");
                   colHdr.add("PKT");
                   colNum = cell.getColumnIndex(); 
                   autoCols.add(Integer.toString(colNum));
                   //sheet.autoSizeColumn((short)colNum, true);
                     cell.setCellStyle(styleHdr);
                   }
               
               cell = row.createCell(++cellCt);
               cell.setCellValue("Packet Id");
               colHdr.add("PKT");
               colNum = cell.getColumnIndex(); 
               autoCols.add(Integer.toString(colNum));
               //sheet.autoSizeColumn((short)colNum, true);
                 cell.setCellStyle(styleHdr);
           
                cell = row.createCell(++cellCt);
                cell.setCellValue("Status");
                colHdr.add("Status");
                colNum = cell.getColumnIndex(); 
                autoCols.add(Integer.toString(colNum));
                   //sheet.autoSizeColumn((short)colNum, true);
                cell.setCellStyle(styleHdr);
           
              
               //sheet.autoSizeColumn((short)colNum, true);

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
               
               String prp = (String)vwPrpLst.get(i);
               String prpDsc = (String)mprp.get(prp);

                
                       cell = row.createCell(++cellCt);
                       cell.setCellValue(prpDsc);
                       colHdr.add(prp);
                       cell.setCellStyle(styleHdr);
                       colNum = cell.getColumnIndex();
                       autoCols.add(Integer.toString(colNum));
             
               if (prp.equals("RTE")){
                   cell = row.createCell(++cellCt);
                   cell.setCellValue("Amount");
                   colHdr.add("AMT");
                   cell.setCellStyle(styleHdr);
                   colNum = cell.getColumnIndex();
                   autoCols.add(Integer.toString(colNum));
                   
                   vwDtl = (HashMap)stockViewMap.get("ISCMP");
                    if(vwDtl!=null){
                         String byrIdn = util.nvl((String)vwDtl.get("url"));
                        if(byrIdn.equals(String.valueOf(info.getByrId()))){
                        cell = row.createCell(++cellCt);
                        cell.setCellValue("CMP");
                        colHdr.add("CMP");
                        cell.setCellStyle(styleHdr);
                        colNum = cell.getColumnIndex();
                        autoCols.add(Integer.toString(colNum));
                    }}
                   
               }
               if(prp.equals("RAP_DIS")){
                   rapDisInx=rapDisInx+4;
                   vwDtl = (HashMap)stockViewMap.get("ISCMPDIS");
                    if(vwDtl!=null){
                        rapDisInx=rapDisInx+5;
                     String byrIdn = util.nvl((String)vwDtl.get("url"));
                        if(byrIdn.equals(String.valueOf(info.getByrId()))){
                        cell = row.createCell(++cellCt);
                        cell.setCellValue("CMP_DIS");
                        colHdr.add("CMP_DIS");
                        cell.setCellStyle(styleHdr);
                        colNum = cell.getColumnIndex();
                        autoCols.add(Integer.toString(colNum));
                    }}
                   
               }
           }                   
                   //sheet.autoSizeColumn((short)colNum, true);
        
                   cell = row.createCell(++cellCt);
                   cell.setCellValue("Rap Vlu");
                   colHdr.add("RAPVAL");
                  
                   cell.setCellStyle(styleHdr);
                   colNum = cell.getColumnIndex(); 
                   autoCols.add(Integer.toString(colNum));
                   //Freezws are
                   String srchQ1=srchQ;
               String flg = "M";
            if(nvl(typ).equals("SRCH"))
                flg = "Z";
           if(nvl(typ).equals("LAB"))
               flg = "LC";
               sheet.createFreezePane(0, rowCt+1);
                   String rsltQ =
                       srchQ + " from gt_srch_rslt  where flg=? union "+srchQ1+" from " +
                       "  gt_srch_multi a  where exists (select 1 from gt_srch_rslt b1 where b1.stk_idn = a.stk_idn and a.flg = ? ) " +
                       "  order by sk1, cts desc ";
               
                   ArrayList numParam = new ArrayList();
                       numParam.add("CRTWT");
                       numParam.add("RTE");
                       numParam.add("RAP_DIS");
                       numParam.add("RAP_RTE");

           ArrayList ary = new ArrayList();
           ary.add(flg);
           ary.add(flg);

                   ArrayList outLst = db.execSqlLst("stock", rsltQ, ary);
                   PreparedStatement pst = (PreparedStatement)outLst.get(0);
                   ResultSet rs = (ResultSet)outLst.get(1);
           String grpCts ="";
           String grpVlu = "";
           String grpAvgDis ="";
           String grpRapVlu = "";
           String grpRteVlu = "";
           String grpAvgVlue = "";
               String grpQty = "";
                   stRow = rowCt+1;
               int srno=0;
             
              if((crtWtInx > rteInx) && rteInx!=-1)
                   crtWtInx++;
             if((rapDisInx > rteInx) && rteInx!=-1)
                   rapDisInx++;
              int rapRteInx =vwPrpLst.indexOf("RAP_RTE");
              if((rapRteInx > rteInx) && rteInx!=-1)
                    rapRteInx++;
            
               while (rs.next()) {
               srno++;
                   
                if(shInx!=-1 && SZ_GRP.equals("Y")){
                String l_grpCts = rs.getString("grp_cts");
                String l_grpVlu = rs.getString("grp_vlu");
                String l_grpAvgVlu = rs.getString("grp_avg_vlu");
                String l_grpRapVlu = rs.getString("grp_rap_vlu");
                String l_avgDis = rs.getString("grp_avg_dis");
                if(grpCts.equals("")) {
                    grpCts = l_grpCts ;
                    grpVlu = l_grpVlu ;
                    grpRapVlu = l_grpRapVlu ;
                    grpAvgDis = l_avgDis ;
                    grpAvgVlue = l_grpAvgVlu;
                }

                if(!(l_grpCts.equals(grpCts))) {
                    row = sheet.createRow((short)++rowCt);
                    if(crtWtInx!=-1){
                    cell = row.createCell(crtWtInx+4);
                    cell.setCellValue(grpCts);
                    cell.setCellStyle(styleBig);
                    colNum = cell.getColumnIndex(); 
                    autoCols.add(Integer.toString(colNum));
                    }
                       
                    if(rapDisInx!=-1){
                    cell = row.createCell(rapDisInx);
                    cell.setCellValue(grpAvgDis);
                    cell.setCellStyle(styleBig);
                    colNum = cell.getColumnIndex(); 
                    autoCols.add(Integer.toString(colNum));
                    }
//                    if(rapRteInx!=-1){
//                     
//                        
//                    cell = row.createCell(rapRteInx+5);
//                    cell.setCellValue(grpRapVlu);
//                    cell.setCellStyle(styleBig);
//                    colNum = cell.getColumnIndex(); 
//                    autoCols.add(Integer.toString(colNum));
//                    }
                    
                    if(rteInx!=-1){
                        cell = row.createCell(rteInx+4);
                        cell.setCellValue(grpAvgVlue);
                        cell.setCellStyle(styleBig);
                        colNum = cell.getColumnIndex(); 
                        autoCols.add(Integer.toString(colNum));
                        
                        cell = row.createCell(rteInx+5);
                        cell.setCellValue(grpVlu);
                        cell.setCellStyle(styleBig);
                        colNum = cell.getColumnIndex(); 
                        autoCols.add(Integer.toString(colNum));
                    }
                grpCts = l_grpCts ;
                grpVlu = l_grpVlu ;
                grpRapVlu = l_grpRapVlu ;
                grpAvgDis = l_avgDis ;
                }}
               isSrch = true;

               String cts = util.nvl(rs.getString("cts"));
               String certNo = util.nvl(rs.getString("cert_no"));
                String lab =  util.nvl(rs.getString("cert"));
                String vnm =  util.nvl(rs.getString("vnm"));
               totalCell = cellCt;
               row = sheet.createRow((short)++rowCt);
               cellCt = -1;
                cell = row.createCell(++cellCt);
                cell.setCellValue(String.valueOf(srno));
                cell.setCellStyle(styleData);
                //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                colNum = cell.getColumnIndex();
                autoCols.add(Integer.toString(colNum));
                vwDtl = (HashMap)stockViewMap.get("VWIMG"); 
                if(vwDtl!=null){
                    cell = row.createCell(++cellCt);
                  
                   
                    String imgPic = imageDir+vnm+"_pic.jpg";
                    String imgPlot = imageDir+vnm+"_plot.jpg";
                    File filePic = new File(imgPic);
                    File filePlot = new File(imgPlot);
                    if( filePic.exists() || filePlot.exists()){
                     
                       String link = "http://www.kapugems.com/kgweb/zoomEffect/viewMultiImg.jsp?CertName="+vnm ;
                        HSSFHyperlink link1 =
                        new HSSFHyperlink(HSSFHyperlink.LINK_URL);
                        link1.setAddress(link);
                        cell.setHyperlink(link1);
                        cell.setCellStyle(hlink_style);
                    cell.setCellValue("View Image"); 
                    }else{
                        cell.setCellValue("-");
                        cell.setCellStyle(numStyleData);
                       
                    }
                    colNum = cell.getColumnIndex();
                    autoCols.add(Integer.toString(colNum));
                }

              
                cell = row.createCell(++cellCt);
                cell.setCellValue(vnm);
                cell.setCellStyle(styleData);
                //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                colNum = cell.getColumnIndex();
                autoCols.add(Integer.toString(colNum));
                   
                   
                cell = row.createCell(++cellCt);
                cell.setCellValue(rs.getString("dsp_stt"));
                cell.setCellStyle(styleData);
                //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                colNum = cell.getColumnIndex();
                autoCols.add(Integer.toString(colNum));

               for (int i = 0; i < vwPrpLst.size(); i++) {
               String fld = "prp_";
               int j = i + 1;
               if (j < 10)
               fld += "00" + j;
               else if (j < 100)
               fld += "0" + j;
               else if (j > 100)
               fld += j;
               String prp = (String)vwPrpLst.get(i);
               String prptyp = util.nvl((String)mprp.get(prp+"T"));
               if (prp.equals("RTE"))
               fld = "rte";
               String fldVal = nvl(rs.getString(fld));
               if (prp.equals("RAP_DIS"))
               fldVal = nvl(rs.getString("r_dis"));
             if (prp.equals("CRTWT"))
                   fldVal = nvl(rs.getString("cts"));
               if (prp.equals("RAP_RTE"))
               fldVal = nvl(rs.getString("rap_rte"));
               if(prp.equals("KTSVIEW"))
                 fldVal = nvl(rs.getString("kts"));
               if(prp.equals("COMMENT"))
                fldVal = nvl(rs.getString("cmnt"));

               fldVal = fldVal.trim();
               if(fldVal.equals("NA"))
                   fldVal="";
              if(prp.equals("CRTWT") ||  prp.equals("RTE")){
                      cell = row.createCell(++cellCt);
                  if(!fldVal.equals("") && fldVal.indexOf("#")==-1){
                     cell.setCellValue(Double.parseDouble(fldVal));
                       cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                  }else{
                      cell.setCellValue(fldVal); 
                  }
                       cell.setCellStyle(numStyleData);
                       colNum = cell.getColumnIndex();
                       autoCols.add(Integer.toString(colNum));
                  if (prp.equals("RTE")){
                      cell = row.createCell(++cellCt);
                      fldVal= nvl(rs.getString("amt"));
                      if(!fldVal.equals("") && fldVal.indexOf("#")==-1){
                      cell.setCellValue(Double.parseDouble(fldVal));
                      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                      }else {
                      cell.setCellValue(fldVal);
                      }


                      cell.setCellStyle(numStyleData);
                      colNum = cell.getColumnIndex();
                      autoCols.add(Integer.toString(colNum));
                      vwDtl = (HashMap)stockViewMap.get("ISCMP");
                       if(vwDtl!=null){
                        String byrIdn = util.nvl((String)vwDtl.get("url"));
                        if(byrIdn.equals(String.valueOf(info.getByrId()))){
                           cell = row.createCell(++cellCt);
                           fldVal= nvl(rs.getString("cmp"));
                           cell.setCellValue(fldVal);
                           cell.setCellStyle(numStyleData);
                           colNum = cell.getColumnIndex();
                           autoCols.add(Integer.toString(colNum));
                       }}
                  }
                       
              }else if(prp.equals("RAP_DIS")){
                 
                  cell = row.createCell(++cellCt);
                  cell.setCellValue(fldVal); 
                  cell.setCellStyle(numStyleData);
                   colNum = cell.getColumnIndex();
                   autoCols.add(Integer.toString(colNum));
                  vwDtl = (HashMap)stockViewMap.get("ISCMPDIS");
                   if(vwDtl!=null){
                    String byrIdn = util.nvl((String)vwDtl.get("url"));
                if(byrIdn.equals(String.valueOf(info.getByrId()))){
                      
                  cell = row.createCell(++cellCt);
                  fldVal= nvl(rs.getString("cmp_dis"));
                  cell.setCellValue(fldVal);
                  cell.setCellStyle(numStyleData);
                  colNum = cell.getColumnIndex();
                  autoCols.add(Integer.toString(colNum));
                   }}
              }else{
              vwDtl = (HashMap)stockViewMap.get(prp);
              String url = "";
              if(vwDtl!=null){
                   url = util.nvl((String)vwDtl.get("url"));
                   cell = row.createCell(++cellCt);
                   if(prp.equals("CERT NO.")){
                  if(lab.equals("GIA") && !fldVal.equals("")){
                       url = url.replace("WT",cts);
                        url = url.replace("REPNO", fldVal);
                        url = url.replace("PRP", fldVal);
                         HSSFHyperlink link1 =
                         new HSSFHyperlink(HSSFHyperlink.LINK_URL);
                         link1.setAddress(url);
                         cell.setHyperlink(link1);
                         cell.setCellStyle(hlink_style);
                         cell.setCellValue(fldVal);
                  }else{
                    cell.setCellStyle(styleData);
                    cell.setCellValue(fldVal);
                  }
                   }else{
                 
                   String imgNmeLst = util.nvl((String)vwDtl.get("nme"));
                      boolean isImg = false;
                       String imgFmt = "";
                         //    imgNmeLst = "vnm.jpg,REPNO.jpg";
                     String[] subImg = imgNmeLst.split(",");
                     for(int k=0 ; k < subImg.length ; k++){
                         // imageDir = "E:\\";
                        String imgNme = imageDir+""+subImg[k];
                          isImg = false;
                          imgNme = imgNme.replace("vnm", vnm);
                          imgNme = imgNme.replace("REPNO", certNo);
                          File imgFile = new File(imgNme);
                           if(imgFile.exists()){
                            isImg = true;
                            imgFmt = subImg[k];
                            break;
                      }}
                       
                        if(isImg){
                          if(imgFmt.indexOf("vnm")!=-1)
                             url = url.replace("REPNO",vnm);
                         else
                              url = url.replace("REPNO",certNo);
                          
                              url = url.replace("PRP", fldVal);
                                 HSSFHyperlink link1 =
                                 new HSSFHyperlink(HSSFHyperlink.LINK_URL);
                                 link1.setAddress(url);
                                 cell.setHyperlink(link1);
                                 cell.setCellStyle(hlink_style);
                                 cell.setCellValue(fldVal);
                              
                             }else{
                                 cell.setCellStyle(styleData);
                                 cell.setCellValue(fldVal);  
                             }
                         
                         }
                   colNum = cell.getColumnIndex();
                   autoCols.add(Integer.toString(colNum));
               }else{
               cell = row.createCell(++cellCt);
                  
               if(!fldVal.equals("") && numParam.contains(prp) && fldVal.indexOf("#")==-1 ){
               cell.setCellValue(Double.parseDouble(fldVal));
               cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
               }else {
               cell.setCellValue(fldVal);
               }


               cell.setCellStyle(styleData);
               colNum = cell.getColumnIndex();
               autoCols.add(Integer.toString(colNum));
                   
                 
               }}
               }
                String rapVal = nvl(rs.getString("rap_vlu"));
                   if(!rapVal.equals("")){
                cell = row.createCell(++cellCt);
                
                cell.setCellValue(rs.getDouble("rap_vlu"));
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                cell.setCellStyle(styleData);
                colNum = cell.getColumnIndex(); 
                autoCols.add(Integer.toString(colNum));  
                   }
                   
            }
                   rs.close();
                   pst.close();
               if(isSrch){
               stRow++;
               row = sheet.createRow((short)++rowCt);
               HashMap ttls = util.getTtls(req);
                   int endrow=rowCt;
             if(ttls.size() > 0){
                   grpCts = util.nvl((String)ttls.get(flg+"W"));
                   grpVlu = util.nvl((String)ttls.get(flg+"V"));
                   grpAvgDis=util.nvl((String)ttls.get(flg+"D"));
                   grpRapVlu=util.nvl((String)ttls.get(flg+"R"));
                   grpQty =util.nvl((String)ttls.get(flg+"Q"));
                   grpAvgVlue=util.nvl((String)ttls.get(flg+"A"));
                 
                 
                       row = sheet.createRow((short)++rowCt);
                       if(crtWtInx!=-1){
                       cell = row.createCell(crtWtInx+4);
                       cell.setCellValue(grpCts);
                       cell.setCellStyle(styleBig);
                       colNum = cell.getColumnIndex(); 
                       autoCols.add(Integer.toString(colNum));
                       }
                          
                       if(rapDisInx!=-1){
                       cell = row.createCell(rapDisInx);
                       cell.setCellValue(grpAvgDis);
                       cell.setCellStyle(styleBig);
                       colNum = cell.getColumnIndex(); 
                       autoCols.add(Integer.toString(colNum));
                       }
//                       if(rapRteInx!=-1){
//                       cell = row.createCell(rapRteInx+3);
//                       cell.setCellValue(grpRapVlu);
//                       cell.setCellStyle(styleBig);
//                       colNum = cell.getColumnIndex(); 
//                       autoCols.add(Integer.toString(colNum));
//                       }
                       if(rteInx!=-1){
                           cell = row.createCell(rteInx+4);
                           cell.setCellValue(grpAvgVlue);
                           cell.setCellStyle(styleBig);
                           colNum = cell.getColumnIndex(); 
                           autoCols.add(Integer.toString(colNum));
                           
                           cell = row.createCell(rteInx+5);
                           cell.setCellValue(grpVlu);
                           cell.setCellStyle(styleBig);
                           colNum = cell.getColumnIndex(); 
                           autoCols.add(Integer.toString(colNum));
                           
                           
                       }
                 
                      
                 
                   Iterator it=autoCols.iterator();
                   while(it.hasNext()) {
                   String value=(String)it.next();
                   int colId = Integer.parseInt(value);
                   sheet.autoSizeColumn((short)colId, true);

                   }

                   String sumSheet = (String)exlFormat.get("SHEET_SMRY");
                   if(sumSheet.equals("YES")){
                   rowCt= rowCt+2;

                   row = sheet.createRow((short)++rowCt);
                   cell = row.createCell(0);
                   cell.setCellValue("Details Description of Diamonds with given Prices");
                   cell.setCellStyle(styleBig);
                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 6));
               
                   row = sheet.createRow((short)++rowCt);
                   cell = row.createCell(0);
                   cell.setCellValue("Total Pcs");
                   cell.setCellStyle(styleBig);

                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
                      
                   cell = row.createCell(3);
                   
                   if(!grpQty.equals("") && grpQty.indexOf("#")==-1)
                   {
                   cell.setCellValue(grpQty);
                   
                   }else {
                   cell.setCellValue(grpQty);
                   }
                   cell.setCellStyle(styleBig);

                   sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
                  
    //                       if(cwtInx>0){
    //                   row = sheet.createRow((short)++rowCt);
    //                   cell = row.createCell(0);
    //                   cell.setCellValue("Total Crts");
    //                   cell.setCellStyle(styleBig);
    //
    //                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
    //                   cell = row.createCell(3);
    //                   if(!grpCts.equals(""))
    //                   {
    //                   cell.setCellValue(Double.parseDouble(grpCts));
    //                   cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
    //                   }else {
    //                   cell.setCellValue(grpCts);
    //                   }
    //                   cell.setCellStyle(styleBig);
    //
    //                   sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
    //                       }
                       if(rapDisInx>0){
                       row = sheet.createRow((short)++rowCt);
                       cell = row.createCell(0);
                       cell.setCellValue("Total Avg.Dis");
                       cell.setCellStyle(styleBig);

                       sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
                       cell = row.createCell(3);
                         
                        cell.setCellValue(grpAvgDis);
                           
                        cell.setCellStyle(styleBig);
                      
                       sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
                       }
    //                       if(rapRteInx>1){
    //                   row = sheet.createRow((short)++rowCt);
    //                   cell = row.createCell(0);
    //                   cell.setCellValue("Total Avg.Rap%");
    //                   cell.setCellStyle(styleBig);
    //
    //                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
    //                   cell = row.createCell(3);
    //                   if(!grpRapVlu.equals(""))
    //                   {
    //                   cell.setCellValue(Double.parseDouble(grpRapVlu));
    //                   cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
    //                   }else {
    //                   cell.setCellValue(grpRapVlu);
    //                   }
    //                   cell.setCellStyle(styleBig);
    //
    //                   sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
    //                       }
                       if(rteInx>0){    
                   row = sheet.createRow((short)++rowCt);
                   cell = row.createCell(0);
                   cell.setCellValue("Total Avg. Price ($)");
                   cell.setCellStyle(styleBig);

                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));

                   cell = row.createCell(3);
                    cell.setCellValue(grpAvgVlue);
                   cell.setCellStyle(styleBig);
                    sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));

                       }
                       if(rteInx>0){  
                   row = sheet.createRow((short)++rowCt);
                   cell = row.createCell(0);
                   cell.setCellValue("Total Amt. ($)");
                   cell.setCellStyle(styleBig);

                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
                  int amtCol=colHdr.indexOf("AMT");
                   cell = row.createCell(3);
                   cell.setCellValue(grpVlu);
                   cell.setCellStyle(styleBig);
                   
                   sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
                       }
                   }
                   }
               }

               } catch (SQLException sqle) {
               // TODO: Add catch code
               sqle.printStackTrace();
               }



               return hwb ;
               }
    
    
    public HSSFWorkbook getDataSHInXl(String typ,HttpServletRequest req ,String mdl) {
             
                   int rowCt = 0;
                   short cellCt = -1;
                   String fontNm = "Cambria";
                   HSSFWorkbook hwb = new HSSFWorkbook();
                   HSSFSheet sheet = hwb.createSheet(typ);
                   //CreationHelper createHelper = hwb.getCreationHelper();
                   
                   HSSFCellStyle hlink_style = hwb.createCellStyle();
                   HSSFFont hlink_font = hwb.createFont();
                   hlink_font.setUnderline(HSSFFont.U_SINGLE);
                   hlink_font.setFontName(fontNm);
                   hlink_font.setColor(HSSFColor.BLUE.index);
                   hlink_style.setFont(hlink_font);
                   //hlink_style.setFillPattern(HSSFCellStyle.SPARSE_DOTS);
                   //hlink_style.setFillForegroundColor(HSSFColor.BLUE.index);
                   
                   HSSFFont fontHdr = hwb.createFont();
                   fontHdr.setFontHeightInPoints((short)10);
                   fontHdr.setFontName(fontNm);
                   fontHdr.setColor(HSSFColor.BLACK.index);
                   fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
                   
                   HSSFCellStyle styleHdr = hwb.createCellStyle();
                   styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                   styleHdr.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
                   styleHdr.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                   styleHdr.setFont(fontHdr);
                   styleHdr.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                   styleHdr.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                   styleHdr.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                   styleHdr.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                   styleHdr.setTopBorderColor(HSSFColor.BLACK.index);
                   styleHdr.setBottomBorderColor(HSSFColor.BLACK.index);
                   styleHdr.setLeftBorderColor(HSSFColor.BLACK.index);
                   styleHdr.setRightBorderColor(HSSFColor.BLACK.index);
                   
                   //styleHdr.BORDER_DOTTED;
                   //styleHdr.set
                   
                   HSSFFont fontData = hwb.createFont();
                   fontData.setFontHeightInPoints((short)10);
                   fontData.setFontName(fontNm);
                   fontData.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
                   
                   HSSFFont fontStt = hwb.createFont();
                   fontStt.setFontHeightInPoints((short)10);
                   fontStt.setFontName(fontNm);
                   
                   
                   HSSFCellStyle styleDIS = hwb.createCellStyle();
                   styleDIS.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                   styleDIS.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
                   styleDIS.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                   styleDIS.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                   styleDIS.setFont(fontStt);
                   styleDIS.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                   styleDIS.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                   styleDIS.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                   styleDIS.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                   styleDIS.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                   styleDIS.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                   styleDIS.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                   styleDIS.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                   HSSFPalette palette1 = hwb.getCustomPalette();
                   palette1.setColorAtIndex(HSSFColor.SKY_BLUE.index, (byte)153,(byte)204, (byte)255);
                   
                   HSSFCellStyle styleData = hwb.createCellStyle();
                   styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                   styleData.setFont(fontData);
                   styleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                   styleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                   styleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                   styleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                   styleData.setTopBorderColor(HSSFColor.BLACK.index);
                   styleData.setBottomBorderColor(HSSFColor.BLACK.index);
                   styleData.setLeftBorderColor(HSSFColor.BLACK.index);
                   styleData.setRightBorderColor(HSSFColor.BLACK.index);
                   
                   //styleData.setDataFormat(HSSFDataFormat);
                   
                   HSSFFont fontDataBig = hwb.createFont();
                   fontDataBig.setFontHeightInPoints((short)10);
                   fontDataBig.setFontName(fontNm);
                   fontDataBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                   
                   HSSFCellStyle styleBig = hwb.createCellStyle();
                   styleBig.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                   styleBig.setFont(fontDataBig);
                   styleBig.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                   styleBig.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                   styleBig.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                   styleBig.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                   styleBig.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                   styleBig.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                   styleBig.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                   styleBig.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
                   styleBig.setFillForegroundColor(HSSFColor.BLUE.index);
                   
                   
                   
                   HSSFFont fontDataBold = hwb.createFont();
                   fontDataBold.setFontHeightInPoints((short)10);
                   fontDataBold.setFontName(fontNm);
                   fontDataBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                   
                   HSSFCellStyle styleBold = hwb.createCellStyle();
                   styleBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                   styleBold.setFont(fontDataBold);
                   styleBold.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
                   styleBold.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
                   styleBold.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
                   styleBold.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
                   styleBold.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
                   styleBold.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
                   styleBold.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
                   styleBold.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
              
               SortedSet autoCols = null;
               HashMap exlFormat = util.EXLFormat();
               HSSFRow row = null ;
               HSSFCell cell = null ;
             
               int colNum = 0;
               int rowNm = 0;
               int rapValInx =0;
               int totalCell=0;
               int stCol = ++cellCt, stRow = rowCt;
               boolean isSrch = false;
               String logoNme = (String)exlFormat.get("LOGO");
                   logoNme = "shital_logo.jpg";
               if(!logoNme.equals("")){
                    try {
                        String servPath = req.getRealPath("/images/clientsLogo");
    
                         if(servPath.indexOf("/") > -1)
                         servPath += "/" ;
                         else {
                                               //servPath = servPath.replace('\', '\\');
                                               servPath += "\\" ;
                           }
                        FileInputStream fis=new FileInputStream(servPath+logoNme);
                        ByteArrayOutputStream img_bytes = new ByteArrayOutputStream();
                        int b;
                        while ((b = fis.read()) != -1)
                            img_bytes.write(b);
                        fis.close();
                        
                       
                        row = sheet.createRow((short)++rowCt);
    
                        int imgWdt = 60, imgHt = 35;
                        HSSFClientAnchor anchor =
                        new HSSFClientAnchor(1, 1, imgWdt, imgHt, (short)6, rowCt,
                        (short)(stCol + 10), (stRow + 5));

                        rowCt = stRow + 5;
                        cellCt = (short)(stCol + 5);
                        int index =
                            hwb.addPicture(img_bytes.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG);
                        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                        HSSFPicture logo = patriarch.createPicture(anchor, index);
                     //  logo.resize();
                       
                        anchor.setAnchorType(2);
    
                    } catch (FileNotFoundException fnfe) {
                        // TODO: Add catch code
                        fnfe.printStackTrace();
                    } catch (IOException ioe) {
                        // TODO: Add catch code
                        ioe.printStackTrace();
                    }
    
               }
               rowCt++;
               String add1 = (String)exlFormat.get("ADD1");
               if(!add1.equals("")){
                 
                         row = sheet.createRow((short)2);
                         cell = row.createCell(11);
                         cell.setCellValue(add1);
                         cell.setCellStyle(styleBig);
                         sheet.addMergedRegion(merge(2, 2, 11, 26));
                     
               }
               String add2 = (String)exlFormat.get("ADD2");
               if(!add2.equals("")){
                 
                         row = sheet.createRow((short)3);
                         cell = row.createCell(11);
                         cell.setCellValue(add2);
                         cell.setCellStyle(styleBig);
                         sheet.addMergedRegion(merge(3, 3, 11, 26));
                     
                }
               
               autoCols = new TreeSet();
                   rowCt=rowCt+3;
               ArrayList colHdr = new ArrayList();
               ArrayList vwPrpLst = util.getMemoXl(mdl);
               if(vwPrpLst==null)
                   util.initSrch();

               
     
           try {
             
           
           
              
             
             
               HashMap mprp = info.getMprp();
    

               String getQuot = "quot rte";
              
                 String srchQ =
                   " select sk1, cts crtwt, "+
                   //decode(quot, 0, 0, decode(nvl(rap_dis,0), 0, 101, trunc(((quot*100)/greatest(rap_rte,100)) - 100,2))) rr_dis, "+
                   " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis " +
                   " , stk_idn, rap_rte , vnm, kts_vw kts, cert_lab cert, cert_no, rmk, img, to_char(trunc(cts,2),'90.99') cts, "+ getQuot
                   +", rap_rte, trunc(cts,2)*rap_Rte rap_vlu "
                   +", rmk , cmnt cmnt ,to_char(trunc(cts,2) * quot, '99999990.00') amt, decode(stt, 'MKAV', 'Available', 'MKIS', 'MEMO', 'MKTL',' ', 'SOLD') stt " ;

            
               
               row = sheet.createRow((short)++rowCt);
               cellCt = -1;
               cell = row.createCell(++cellCt);
               cell.setCellValue("Packet Id");
               colHdr.add("PKT");
               colNum = cell.getColumnIndex(); 
               autoCols.add(Integer.toString(colNum));
               //sheet.autoSizeColumn((short)colNum, true);
                       
               cell.setCellStyle(styleHdr);
           
           
              
               //sheet.autoSizeColumn((short)colNum, true);

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
               
               String prp = (String)vwPrpLst.get(i);
               String prpDsc = (String)mprp.get(prp);

                
                       cell = row.createCell(++cellCt);
                       cell.setCellValue(prpDsc);
                       colHdr.add(prp);
                       cell.setCellStyle(styleHdr);
                       colNum = cell.getColumnIndex();
                       autoCols.add(Integer.toString(colNum));
               if (prp.equals("RTE")){
                   cell = row.createCell(++cellCt);
                   cell.setCellValue("Amount");
                   colHdr.add("AMT");
                   cell.setCellStyle(styleHdr);
                   colNum = cell.getColumnIndex();
                   autoCols.add(Integer.toString(colNum));
               }
           }                   
                   //sheet.autoSizeColumn((short)colNum, true);
        
                   cell = row.createCell(++cellCt);
                   cell.setCellValue("Rap Vlu");
                   colHdr.add("RAPVAL");
                  
                   cell.setCellStyle(styleHdr);
                   colNum = cell.getColumnIndex(); 
                   autoCols.add(Integer.toString(colNum));
                   //Freezws are
               String flg = "M";
            if(nvl(typ).equals("SRCH"))
                flg = "Z";
                   if(nvl(typ).equals("LAB"))
                       flg = "LC";
               sheet.createFreezePane(0, rowCt+1);
               String srchQ1 = srchQ;
                   String rsltQ =
                       srchQ + " from gt_srch_rslt  where flg=? union "+srchQ1+" from " +
                       "  gt_srch_multi a  where exists (select 1 from gt_srch_rslt b1 where b1.stk_idn = a.stk_idn and a.flg = ? ) " +
                       " order by sk1 ";
                   
                   ArrayList numParam = new ArrayList();
                       numParam.add("CRTWT");
                       numParam.add("RTE");
                       numParam.add("RAP_DIS");
                       numParam.add("RAP_RTE");
           ArrayList ary = new ArrayList();
           ary.add(flg);
          ary.add(flg);
                   ArrayList outLst = db.execSqlLst("stock", rsltQ, ary);
                   PreparedStatement pst = (PreparedStatement)outLst.get(0);
                   ResultSet rs = (ResultSet)outLst.get(1);
           String grpCts ="";
           String grpVlu = "";
           String grpAvgDis ="";
           String grpRapVlu = "";
           String grpRteVlu = "";
           String grpAvgVlue = "";
               String grpQty = "";
                   stRow = rowCt+1;
               while (rs.next()) {

               isSrch = true;

               String cts = util.nvl(rs.getString("cts"));
               String certNo = util.nvl(rs.getString("cert_no"));
               String cert = util.nvl(rs.getString("cert"));
               totalCell = cellCt;
               row = sheet.createRow((short)++rowCt);
               cellCt = -1;

               String vnm = rs.getString("vnm");





               cell = row.createCell(++cellCt);
               cell.setCellValue(vnm);
               cell.setCellStyle(styleData);
               //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
               colNum = cell.getColumnIndex();
               autoCols.add(Integer.toString(colNum));




               for (int i = 0; i < vwPrpLst.size(); i++) {
               String fld = "prp_";
               int j = i + 1;
               if (j < 10)
               fld += "00" + j;
               else if (j < 100)
               fld += "0" + j;
               else if (j > 100)
               fld += j;
               String prp = (String)vwPrpLst.get(i);
               String prptyp = util.nvl((String)mprp.get(prp+"T"));
               if (prp.equals("RTE"))
               fld = "rte";
               String fldVal = nvl(rs.getString(fld));
               if (prp.equals("RAP_DIS"))
               fldVal = nvl(rs.getString("r_dis"));
               if (prp.equals("RAP_RTE"))
               fldVal = nvl(rs.getString("rap_rte"));
               if(prp.equals("KTSVIEW"))
                 fldVal = nvl(rs.getString("kts"));
               if(prp.equals("COMMENT"))
                 fldVal = nvl(rs.getString("cmnt"));

               fldVal = fldVal.trim();
               if(fldVal.equals("NA"))
                   fldVal="";
               if(prp.equals("CERT_NO") && cert.equals("GIA")){
               cell = row.createCell(++cellCt);
               cell.setCellValue(fldVal);
               String link =(String)exlFormat.get("CERTLINK");
               if(!link.equals("")){
               link = link.replace("CERTNME",certNo);
               link = link.replace("WT", cts);
               HSSFHyperlink link1 =
               new HSSFHyperlink(HSSFHyperlink.LINK_URL);
               link1.setAddress(link);
               cell.setHyperlink(link1);
               cell.setCellStyle(hlink_style);
               }else{
               cell.setCellStyle(styleData);
               }
               colNum = cell.getColumnIndex();
               autoCols.add(Integer.toString(colNum));
               }else if(prp.equals("RAP_DIS")){
                    fldVal = fldVal+"%";
                   cell = row.createCell(++cellCt);
                  cell.setCellValue(fldVal);
                   cell.setCellStyle(styleData);
                   colNum = cell.getColumnIndex();
                   autoCols.add(Integer.toString(colNum));
               } else{
                   
               cell = row.createCell(++cellCt);
               if(!fldVal.equals("") && numParam.contains(prp) && fldVal.indexOf("#")==-1 ){
               cell.setCellValue(Double.parseDouble(fldVal));
               cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
               }else {
               cell.setCellValue(fldVal);
               }


               cell.setCellStyle(styleData);
               colNum = cell.getColumnIndex();
               autoCols.add(Integer.toString(colNum));
                   if (prp.equals("RTE")){
                       cell = row.createCell(++cellCt);
                       fldVal= nvl(rs.getString("amt"));
                       if(!fldVal.equals("") && fldVal.indexOf("#")==-1){
                       cell.setCellValue(Double.parseDouble(fldVal));
                       cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                       }else {
                       cell.setCellValue(fldVal);
                       }


                       cell.setCellStyle(styleData);
                       colNum = cell.getColumnIndex();
                       autoCols.add(Integer.toString(colNum));
                   }
               }
               }
                String rapVal = nvl(rs.getString("rap_vlu"));
                   if(!rapVal.equals("")){
                cell = row.createCell(++cellCt);
                cell.setCellValue(rs.getDouble("rap_vlu"));
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                cell.setCellStyle(styleData);
                colNum = cell.getColumnIndex(); 
                autoCols.add(Integer.toString(colNum));  
                   }
                   
            }
                   rs.close();
                   pst.close();
               if(isSrch){
               stRow++;
               row = sheet.createRow((short)++rowCt);
               HashMap ttls = util.getTtls(req);
                   int endrow=rowCt;
             if(ttls.size() > 0){
                   grpCts = (String)ttls.get(flg+"W");
                   grpVlu = (String)ttls.get(flg+"V");
                   grpAvgDis=(String)ttls.get(flg+"D");
                   grpRapVlu=(String)ttls.get(flg+"R");
                   grpQty =(String)ttls.get(flg+"Q");
                   grpAvgVlue=(String)ttls.get(flg+"A");
                 
                 
                   int cwtInx = vwPrpLst.indexOf("CRTWT");
                   int rap_dis = vwPrpLst.indexOf("RAP_DIS");
                   int rteInx = vwPrpLst.indexOf("RTE");
                   int rapRteInx = vwPrpLst.indexOf("RAP_RTE");
                   cell = row.createCell(0);
                   cell.setCellValue("Grand Total");
                   cell.setCellStyle(styleBold);
                   colNum = cell.getColumnIndex();
                   autoCols.add(Integer.toString(colNum));
                     if(cwtInx>0){
                   cell = row.createCell(cwtInx+1);
                    int crtwtCol=colHdr.indexOf("CRTWT");
                    String crtwtcolName=GetAlpha(crtwtCol+1);
                  
                   String strFormula = "SUM("+crtwtcolName+stRow+":"+crtwtcolName+endrow+")";
                   cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                   cell.setCellFormula(strFormula);

                   cell.setCellStyle(styleBold);
                   colNum = cell.getColumnIndex();
                   autoCols.add(Integer.toString(colNum));
                    }
                  if(rap_dis>0){
                        int amtCol=colHdr.indexOf("AMT");
                        String amtColName=GetAlpha(amtCol+1);
                       
                        int rapVluCol=colHdr.indexOf("RAPVAL");
                        String rapVluColName=GetAlpha(rapVluCol+1);
                        
                      
                        //    String strFormula = "SUM("+colName+stRow+":"+colName+endrow+")";
                        String strFormula="(( ("+amtColName+(endrow+1)+")/("+rapVluColName+(endrow+1)+"))*100)-100";
                   cell = row.createCell(rap_dis+1);
                  
                    cell.setCellFormula(strFormula);
                   cell.setCellStyle(styleBold);
                   colNum = cell.getColumnIndex();
                  cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                   autoCols.add(Integer.toString(colNum));
                    }
                    if(rteInx>0){  
                   cell = row.createCell(rteInx+1);
                   int crtwtCol=colHdr.indexOf("CRTWT");
                   String crtwtColName=GetAlpha(crtwtCol+1);
                   int rteCol=colHdr.indexOf("RTE");
                   String rtecolName=GetAlpha(rteCol+1);
                  
                           String strFormula="";
                             for(int i=stRow ;i<=endrow;i++) {
                                 if((i==endrow) && (stRow==endrow)) {
                                strFormula+="(("+crtwtColName+i+"*"+rtecolName+i+"))";    
                                 }else
                                 {
                              if(i==stRow) {
                                strFormula+="(("+crtwtColName+i+"*"+rtecolName+i+")+";
                              }else if(i==endrow) {
                               strFormula+="("+crtwtColName+i+"*"+rtecolName+i+"))";   
                                 }
                                 else
                                 {
                               strFormula+="("+crtwtColName+i+"*"+rtecolName+i+")+";
                                 }
                                 }
                             }
                           strFormula = strFormula+"/("+crtwtColName+(endrow+1)+")";
                   cell.setCellStyle(styleBold);
                   colNum = cell.getColumnIndex();
                   cell.setCellFormula(strFormula);
                   cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                   autoCols.add(Integer.toString(colNum));
                       }
    //                       if(rapRteInx>1){
    //                   cell = row.createCell(rapRteInx+1);
    //                   if(!grpRapVlu.equals(""))
    //                   {
    //                   cell.setCellValue(Double.parseDouble(grpRapVlu));
    //                   cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
    //                   }
    //                   else {
    //                   cell.setCellValue(grpRapVlu);
    //                   }
    //                   cell.setCellStyle(styleBold);
    //                   colNum = cell.getColumnIndex();
    //                   autoCols.add(Integer.toString(colNum));
    //                    }
                 if(rteInx>0){  
                        int amtCol=colHdr.indexOf("AMT");
                       
                       cell = row.createCell(rteInx+2);
                     
                        String colName=GetAlpha(amtCol+1);
                        String strFormula = "SUM("+colName+stRow+":"+colName+endrow+")";
                       cell.setCellFormula(strFormula);
                       cell.setCellStyle(styleBold);
                       colNum = cell.getColumnIndex();
                      cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                       autoCols.add(Integer.toString(colNum));
                    }
                 
                       rapValInx=colHdr.indexOf("RAPVAL");
                       cell = row.createCell(rapValInx);
                       String rapVlucolName=GetAlpha(rapValInx+1);
                       String strFormula1 = "SUM("+rapVlucolName+stRow+":"+rapVlucolName+endrow+")";
                       cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                       cell.setCellFormula(strFormula1);
                       cell.setCellStyle(styleBold);
                       colNum = cell.getColumnIndex();
                       autoCols.add(Integer.toString(colNum));
                 
                   Iterator it=autoCols.iterator();
                   while(it.hasNext()) {
                   String value=(String)it.next();
                   int colId = Integer.parseInt(value);
                   sheet.autoSizeColumn((short)colId, true);

                   }

                   String sumSheet = (String)exlFormat.get("SHEET_SMRY");
                   if(sumSheet.equals("YES")){
                   rowCt= rowCt+2;

                   row = sheet.createRow((short)++rowCt);
                   cell = row.createCell(0);
                   cell.setCellValue("Details Description of Diamonds with given Prices");
                   cell.setCellStyle(styleBig);
                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 6));
               
                   row = sheet.createRow((short)++rowCt);
                   cell = row.createCell(0);
                   cell.setCellValue("Total Pcs");
                   cell.setCellStyle(styleBig);

                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
                      
                   cell = row.createCell(3);
                   
                   if(!grpQty.equals("") && grpQty.indexOf("#")==-1)
                   {
                   cell.setCellValue(Double.parseDouble(grpQty));
                   cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                   }else {
                   cell.setCellValue(grpQty);
                   }
                   cell.setCellStyle(styleBig);

                   sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
                  
    //                       if(cwtInx>0){
    //                   row = sheet.createRow((short)++rowCt);
    //                   cell = row.createCell(0);
    //                   cell.setCellValue("Total Crts");
    //                   cell.setCellStyle(styleBig);
    //
    //                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
    //                   cell = row.createCell(3);
    //                   if(!grpCts.equals(""))
    //                   {
    //                   cell.setCellValue(Double.parseDouble(grpCts));
    //                   cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
    //                   }else {
    //                   cell.setCellValue(grpCts);
    //                   }
    //                   cell.setCellStyle(styleBig);
    //
    //                   sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
    //                       }
                       if(rap_dis>0){
                       row = sheet.createRow((short)++rowCt);
                       cell = row.createCell(0);
                       cell.setCellValue("Total Avg.Dis");
                       cell.setCellStyle(styleBig);

                       sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
                       cell = row.createCell(3);
                        int amtCol=colHdr.indexOf("AMT");
                        String amtColName=GetAlpha(amtCol+1);
                        int rapVluCol=colHdr.indexOf("RAPVAL");
                        String rapVluColName=GetAlpha(rapVluCol+1);
                        String strFormula="(( ("+amtColName+(endrow+1)+")/("+rapVluColName+(endrow+1)+"))*100)-100";
                        cell.setCellFormula(strFormula);
                        cell.setCellStyle(styleBig);
                        cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                       sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
                       }
    //                       if(rapRteInx>1){
    //                   row = sheet.createRow((short)++rowCt);
    //                   cell = row.createCell(0);
    //                   cell.setCellValue("Total Avg.Rap%");
    //                   cell.setCellStyle(styleBig);
    //
    //                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
    //                   cell = row.createCell(3);
    //                   if(!grpRapVlu.equals(""))
    //                   {
    //                   cell.setCellValue(Double.parseDouble(grpRapVlu));
    //                   cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
    //                   }else {
    //                   cell.setCellValue(grpRapVlu);
    //                   }
    //                   cell.setCellStyle(styleBig);
    //
    //                   sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
    //                       }
                       if(rteInx>0){    
                   row = sheet.createRow((short)++rowCt);
                   cell = row.createCell(0);
                   cell.setCellValue("Total Avg. Price ($)");
                   cell.setCellStyle(styleBig);

                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));

                   cell = row.createCell(3);
                      
                           int crtwtCol=colHdr.indexOf("CRTWT");
                           String crtwtColName=GetAlpha(crtwtCol+1);
                           int rteCol=colHdr.indexOf("RTE");
                           String rtecolName=GetAlpha(rteCol+1);
                         
                                   String strFormula="";
                                     for(int i=stRow ;i<=endrow;i++) {
                                         if((i==endrow) && (stRow==endrow)) {
                                        strFormula+="(("+crtwtColName+i+"*"+rtecolName+i+"))";    
                                         }else
                                         {
                                      if(i==stRow) {
                                        strFormula+="(("+crtwtColName+i+"*"+rtecolName+i+")+";
                                      }else if(i==endrow) {
                                       strFormula+="("+crtwtColName+i+"*"+rtecolName+i+"))";   
                                         }
                                         else
                                         {
                                       strFormula+="("+crtwtColName+i+"*"+rtecolName+i+")+";
                                         }
                                         }
                                     }
                           strFormula = strFormula+"/("+crtwtColName+(endrow+1)+")";
                           cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                           cell.setCellStyle(styleBig);
                           colNum = cell.getColumnIndex();
                           cell.setCellFormula(strFormula);
                         
                   sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));

                       }
                       if(rteInx>0){  
                   row = sheet.createRow((short)++rowCt);
                   cell = row.createCell(0);
                   cell.setCellValue("Total Amt. ($)");
                   cell.setCellStyle(styleBig);

                   sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
                  int amtCol=colHdr.indexOf("AMT");
                   cell = row.createCell(3);
                    String colName=GetAlpha(amtCol+1);
                    String strFormula = "SUM("+colName+stRow+":"+colName+endrow+")";
                    cell.setCellFormula(strFormula);
                    cell.setCellStyle(styleBold);
                    colNum = cell.getColumnIndex();
                    autoCols.add(Integer.toString(colNum));
                   cell.setCellStyle(styleBig);
                    cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                   sheet.addMergedRegion(merge(rowCt, rowCt, 3, 6));
                       }
                   }
                   }
               }

               } catch (SQLException sqle) {
               // TODO: Add catch code
               sqle.printStackTrace();
               }



               return hwb ;
               }
    
    public HSSFWorkbook getGenXl(ArrayList itemHdr , ArrayList pktDtlList) {
        
        hwb = new HSSFWorkbook();
        autoColums = new TreeSet();
        String fontNm = "Cambria";
        fontHdr = hwb.createFont();
        fontHdr.setFontHeightInPoints((short)10);
        fontHdr.setFontName(fontNm);
        fontHdr.setColor(HSSFColor.BLACK.index);
        fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
        styleHdr = hwb.createCellStyle();
        styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleHdr.setFont(fontHdr);
        
       
        fontData = hwb.createFont();
        fontData.setFontHeightInPoints((short)10);
        fontData.setFontName(fontNm);
        styleData = hwb.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setFont(fontData);

        hlink_style = hwb.createCellStyle();

        hlink_font = hwb.createFont();

        hlink_font.setUnderline(HSSFFont.U_SINGLE);

        hlink_font.setFontName(fontNm);

        hlink_font.setColor(HSSFColor.BLUE.index);

        hlink_font.setFontHeightInPoints((short)10);

        hlink_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        hlink_style.setFont(hlink_font);
        
        HSSFCellStyle numStyleData = hwb.createCellStyle();
        numStyleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        numStyleData.setFont(fontData);
        numStyleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
          HSSFDataFormat df = hwb.createDataFormat();
          numStyleData.setDataFormat(df.getFormat("#0.00#"));

        sheet = addSheet();
        String  genexcel = util.nvl((String)info.getDmbsInfoLst().get("GENEXCELDSC"));
        ArrayList imagelistDtl =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ImaageDtls") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ImaageDtls");
        HashMap mprp = info.getMprp();
        row = newRow();
        HashMap prpDsc=new HashMap();
        HashMap dtls=new HashMap();
        int pktDtlListsz=pktDtlList.size();
        prpDsc.put("SALEDTE", "Sale Date");
        prpDsc.put("APPDTE", "Approve Date");
        prpDsc.put("ACTSTT", "Actual Status");
        prpDsc.put("STT", "STATUS");
        prpDsc.put("VNM", "PACKET ID");
        prpDsc.put("memoIdn", "Memo ID");
        prpDsc.put("currentstt", "Live Status");
        prpDsc.put("iss_stt", "Opening Status");
        prpDsc.put("STK_IDN", "STOCK ID");
        prpDsc.put("QTY", "QUANTITY");
        prpDsc.put("BYR", "BUYER");
        prpDsc.put("SalAmt", "Sale Amount");
        prpDsc.put("AADATNME", "Aadat Name");
        prpDsc.put("AADATCOMM", "Aadat Commission");
        prpDsc.put("curr", "Price Revised from MktTrns to Curr");
        prpDsc.put("DATEAMT", "Date selection To Curr Amt %");
        prpDsc.put("QUOT", "Sale Rte");
        prpDsc.put("SLBACK", "Sale Back");
        prpDsc.put("AMT", "Amount");
        prpDsc.put("EMP", "Sale Ex");
        prpDsc.put("plper", "Profit%");
        prpDsc.put("STK_IDN", "N");
        prpDsc.put("QTYT", "N");
        prpDsc.put("QuotT", "N");
        prpDsc.put("SLBACKT", "N");
        prpDsc.put("AMTT", "N");
        prpDsc.put("slbackT", "N");
        prpDsc.put("amtT", "N");
        prpDsc.put("diffT", "N");
        prpDsc.put("plT", "N");
        prpDsc.put("RateT", "N");
        prpDsc.put("RateAmtT", "N");
        prpDsc.put("RateDiscT", "N");
        prpDsc.put("RapRteT", "N");
        prpDsc.put("DisT", "N");
        prpDsc.put("Prc / CrtT", "N");
        prpDsc.put("ByrDisT", "N");
        prpDsc.put("SALE RTET", "N");
        prpDsc.put("AMOUNTT", "N");
        prpDsc.put("RAPVLUT", "N");
        prpDsc.put("DATEAMT", "Date selection To Curr Amt %");
            if(imagelistDtl!=null && imagelistDtl.size() >0){
            for(int j=0;j<imagelistDtl.size();j++){
            dtls=new HashMap();
            dtls=(HashMap)imagelistDtl.get(j);
            String gtCol=util.nvl((String)dtls.get("GTCOL")).toUpperCase();
            String hdr=util.nvl((String)dtls.get("HDR"));
             String path=util.nvl((String)dtls.get("DNA"));
            prpDsc.put(gtCol, hdr.toUpperCase());
            prpDsc.put(gtCol+"DNA", path);
            }
            }
    String imgLink = util.nvl((String)prpDsc.get("DIAMONDIMGDNA"));
      HashMap isCDRPT = new HashMap();
      if(pktDtlListsz>0)
      isCDRPT=(HashMap)pktDtlList.get(0);
      String isCD = util.nvl((String)isCDRPT.get("CDRPT"));
      int n=0;
      if(isCD.equals("Y")){
      String jp = (String)pktDtlList.get(1);
      String js= (String)pktDtlList.get(2);

      HashMap b2cMap= (HashMap)pktDtlList.get(3);
      ArrayList NONB2CMAP = (ArrayList)pktDtlList.get(4);
      HashMap NC = (HashMap)pktDtlList.get(5);
      HashMap CD = (HashMap)pktDtlList.get(6);
      row = newRow();
             addCell(++cellCt, "Jangad Par:", styleHdr);
             addCell(++cellCt, jp, styleHdr);
                           row = newRow();
             addCell(++cellCt, "Jangad selection :", styleHdr);
             addCell(++cellCt, js, styleHdr);
              row = newRow();
             addCell(++cellCt, "JB2:", styleHdr);
             addCell(++cellCt,util.nvl((String)b2cMap.get("ttlVlu")), styleHdr);
           for(int i=0;i<NONB2CMAP.size();i++){
            HashMap nonB2cDtl = (HashMap)NONB2CMAP.get(i);
             row = newRow();
             addCell(++cellCt,util.nvl((String)nonB2cDtl.get("brcNme")), styleHdr);
             addCell(++cellCt,util.nvl((String)nonB2cDtl.get("ttlVlu")), styleHdr);
          
             }
              row = newRow();
             addCell(++cellCt, "JMF ST :", styleHdr);
             addCell(++cellCt, util.nvl((String)NC.get("ttlVlu")), styleHdr);
              row = newRow();
             addCell(++cellCt, "JDS ST :", styleHdr);
             addCell(++cellCt, util.nvl((String)CD.get("ttlVlu")), styleHdr);
                      n=7;
          row = newRow();
          row = newRow();


        }

        for (int j = 0; j < itemHdr.size(); j++) {
             String hdr = String.valueOf(itemHdr.get(j)).toUpperCase();
             if(prpDsc.containsKey(hdr)){
                 hdr=(String)prpDsc.get(hdr);
                 }
             if(hdr.indexOf("$") > -1){
                 hdr=hdr.replaceAll("\\$"," ");
                }
            if(genexcel.equals("Y")){
             String pDsc = util.nvl((String)mprp.get(hdr+"D"));
            if(!pDsc.equals("")){
                hdr=pDsc;   
            }
            }
             addCell(++cellCt, hdr, styleHdr);
         }
        
        for (int j = n; j < pktDtlListsz; j++) {
        row = newRow();
        HashMap pktDtl = (HashMap)pktDtlList.get(j);
        String stkIdn = (String)pktDtl.get("STKIDN");
        String kapanId =util.nvl((String)pktDtl.get("ISKAPAN"));
        String ttlCts =util.nvl((String)pktDtl.get("ISTOTAL"));
        System.out.println(j);
        if(kapanId.equals("Y")){
        addCell(++cellCt,util.nvl((String)pktDtl.get("KAPAN")) , styleData);
        }else if(ttlCts.equals("Y")){
            int inxAmt = 0;
            int inxCts = 0;
            int inxVnm = 0;
            inxVnm = itemHdr.indexOf("PACKETCODE");
            inxCts = itemHdr.indexOf("CTS");
            inxAmt = itemHdr.indexOf("AMOUNT");
            for(int k=0; k < itemHdr.size(); k++ ){
            String prp = (String)itemHdr.get(k);
            int inxPrp = itemHdr.indexOf(prp);
            if (inxPrp == inxVnm) {
            addCell(++cellCt, util.nvl((String)pktDtl.get("ttlqty")), styleHdr);
            } else if (inxPrp == inxCts) {
            addCell(++cellCt, util.nvl((String)pktDtl.get("ttlCts")), styleHdr);
            } else if (inxPrp == inxAmt) {
            addCell(++cellCt, util.nvl((String)pktDtl.get("ttlAmt")), styleHdr);
            } else {
            addCell(++cellCt, "", styleData);
            }
            }

        }else{
            
        for (int i = 0; i < itemHdr.size(); i++) {
        String hdr = (String)itemHdr.get(i);
        String mprptyp = util.nvl((String)mprp.get(hdr+"T"));
        String fldVal = util.nvl((String)pktDtl.get(hdr));
            stkIdn = util.nvl((String)pktDtl.get("STKIDN"));
          fldVal = fldVal.replace(",","");
            fldVal=fldVal.trim();
        if(mprptyp.equals(""))
        mprptyp = util.nvl((String)prpDsc.get(hdr+"T"));    
        if((mprptyp.equals("N") && !fldVal.equals("")) && fldVal.indexOf("#")==-1) {
                if(fldVal.equals("NA"))
                fldVal="0";
                cell = row.createCell(++cellCt);
                cell.setCellStyle(numStyleData);
                int colNum = cell.getColumnIndex();
                try {
                            cell.setCellValue(Double.parseDouble(fldVal));
                            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        } catch (NumberFormatException nfe) {
                            cell.setCellValue(fldVal);
                        }
                autoColums.add(Integer.toString(colNum));
        }else if(hdr.equalsIgnoreCase("VIEW") && !stkIdn.equals("")){
            fldVal=imgLink.replaceAll("STKIDN",stkIdn);
            cell = row.createCell(++cellCt);
            HSSFHyperlink link1 =
                new HSSFHyperlink(HSSFHyperlink.LINK_URL);
            link1.setAddress(fldVal);
            cell.setHyperlink(link1);
            cell.setCellStyle(hlink_style);
            cell.setCellValue("View Image"); 
        
        }else{   
        addCell(++cellCt, fldVal, styleData);
        }
        }
        }
        }

         
        Iterator it= autoColums.iterator();
        while(it.hasNext()) {
        String value=(String)it.next();
        int colId = Integer.parseInt(value);
        sheet.autoSizeColumn((short)colId, true);

        }
        
        
        return hwb;
       
    }


    public HSSFWorkbook getGenVenderFileXl(ArrayList itemHdr , ArrayList pktDtlList,HashMap prpDsc) {
        
        hwb = new HSSFWorkbook();
        autoColums = new TreeSet();
        String fontNm = "Arial";
        fontHdr = hwb.createFont();
        fontHdr.setFontHeightInPoints((short)11);
        fontHdr.setFontName(fontNm);
        fontHdr.setColor(HSSFColor.BLACK.index);
        fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
        styleHdr = hwb.createCellStyle();
        styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleHdr.setFont(fontHdr);
        
       
        fontData = hwb.createFont();
        fontData.setFontHeightInPoints((short)11);
        fontData.setFontName(fontNm);
        styleData = hwb.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setFont(fontData);
        
        HSSFCellStyle numStyleData = hwb.createCellStyle();
        numStyleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        numStyleData.setFont(fontData);
        numStyleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
          HSSFDataFormat df = hwb.createDataFormat();
          numStyleData.setDataFormat(df.getFormat("#0.00#"));
          
        HSSFCellStyle numStyleDatanodesc = hwb.createCellStyle();
        numStyleDatanodesc.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        numStyleDatanodesc.setFont(fontData);
        numStyleDatanodesc.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
        numStyleDatanodesc.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
        numStyleDatanodesc.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
        numStyleDatanodesc.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
        numStyleDatanodesc.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleDatanodesc.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleDatanodesc.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleDatanodesc.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
          HSSFDataFormat nodecdf = hwb.createDataFormat();
          numStyleDatanodesc.setDataFormat(nodecdf.getFormat("##"));
        sheet = addSheet();
        String  genexcel = util.nvl((String)info.getDmbsInfoLst().get("GENEXCELDSC"));
        HashMap mprp = info.getMprp();
        rowCt=-1;
        row = newRow();
        int pktDtlListsz=pktDtlList.size();
        prpDsc.put("AMT", "Amount");
        prpDsc.put("DIAMONDIMG", "Diamond Image");
        prpDsc.put("CERTIMG", "Certificate Filename");
        prpDsc.put("EMP", "Sale Ex");
        prpDsc.put("STK_IDN", "N");
        prpDsc.put("QTYT", "N");
        prpDsc.put("QuotT", "N");
        prpDsc.put("SLBACKT", "N");
        prpDsc.put("AMTT", "N");
        prpDsc.put("slbackT", "N");
        prpDsc.put("amtT", "N");
        prpDsc.put("diffT", "N");
        prpDsc.put("plT", "N");
        prpDsc.put("RateT", "N");
        prpDsc.put("RateAmtT", "N");
        prpDsc.put("RateDiscT", "N");
        prpDsc.put("RapRteT", "N");
        prpDsc.put("DisT", "N");
        prpDsc.put("Prc / CrtT", "N");
        prpDsc.put("ByrDisT", "N");
        prpDsc.put("SALE RTET", "N");
        prpDsc.put("AMOUNTT", "N");
        prpDsc.put("RAPVLUT", "N");
        prpDsc.put("Cash Price Discount %T", "N");
        prpDsc.put("RapNet PriceT", "N");
        
        for (int j = 0; j < itemHdr.size(); j++) {
             String hdr = String.valueOf(itemHdr.get(j));
             if(prpDsc.containsKey(hdr)){
                 hdr=util.nvl((String)prpDsc.get(hdr));
                 }
             if(hdr.indexOf("$") > -1){
                 hdr=hdr.replaceAll("\\$"," ");
                }
             addCell(++cellCt, hdr, styleHdr);
         }
        
        for (int j = 0; j < pktDtlListsz; j++) {
        row = newRow();
        HashMap pktDtl = (HashMap)pktDtlList.get(j);
        String kapanId =util.nvl((String)pktDtl.get("ISKAPAN"));
        String ttlCts =util.nvl((String)pktDtl.get("ISTOTAL"));
        if(kapanId.equals("Y")){
        addCell(++cellCt,util.nvl((String)pktDtl.get("KAPAN")) , styleData);
        }else if(ttlCts.equals("Y")){
            int inxAmt = 0;
            int inxCts = 0;
            int inxVnm = 0;
            inxVnm = itemHdr.indexOf("PACKETCODE");
            inxCts = itemHdr.indexOf("CTS");
            inxAmt = itemHdr.indexOf("AMOUNT");
            for(int k=0; k < itemHdr.size(); k++ ){
            String prp = (String)itemHdr.get(k);
            int inxPrp = itemHdr.indexOf(prp);
            if (inxPrp == inxVnm) {
            addCell(++cellCt, util.nvl((String)pktDtl.get("ttlqty")), styleHdr);
            } else if (inxPrp == inxCts) {
            addCell(++cellCt, util.nvl((String)pktDtl.get("ttlCts")), styleHdr);
            } else if (inxPrp == inxAmt) {
            addCell(++cellCt, util.nvl((String)pktDtl.get("ttlAmt")), styleHdr);
            } else {
            addCell(++cellCt, "", styleData);
            }
            }

        }else{
        for (int i = 0; i < itemHdr.size(); i++) {
        String hdr = (String)itemHdr.get(i);
        String mprptyp = util.nvl((String)mprp.get(hdr+"T"));
        String fldVal = util.nvl((String)pktDtl.get(hdr));
        fldVal = fldVal.replace(",","");
        if(mprptyp.equals(""))
        mprptyp = util.nvl((String)prpDsc.get(hdr+"T"));    
        if((mprptyp.equals("N") && !fldVal.equals("")) && fldVal.indexOf("#")==-1) {
                cell = row.createCell(++cellCt);
//                if(hdr.equals("RTE") || hdr.equals("RAP_DIS") || hdr.equals("RapNet Price") || hdr.equals("Cash Price Discount %"))
                if(hdr.equals("RTE") || hdr.equals("RAP_RTE") || hdr.equals("RapNet Price"))
                cell.setCellStyle(numStyleDatanodesc);
                else
                cell.setCellStyle(numStyleData);
                int colNum = cell.getColumnIndex();
                cell.setCellValue(Double.parseDouble(fldVal));
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                autoColums.add(Integer.toString(colNum));
        }
        else{   
        addCell(++cellCt, fldVal, styleData);
        }
        }
        }
        }

         
        Iterator it= autoColums.iterator();
        while(it.hasNext()) {
        String value=(String)it.next();
        int colId = Integer.parseInt(value);
        sheet.autoSizeColumn((short)colId, true);

        }
        
        
        return hwb;
       
    }
    public HSSFWorkbook getDataStatusLabInXl(HttpServletRequest req) {
    hwb = new HSSFWorkbook();
    autoColums = new TreeSet();
    session = req.getSession(false);
    String fontNm = "Geneva";
    fontHdr = hwb.createFont();
    fontHdr.setFontHeightInPoints((short)11);
    fontHdr.setFontName(fontNm);
    fontHdr.setColor(HSSFColor.BLACK.index);
    fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

    styleHdr = hwb.createCellStyle();
    styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleHdr.setBorderBottom((short)1);
    styleHdr.setBorderTop((short)1);
    styleHdr.setBorderLeft((short)1);
    styleHdr.setBorderRight((short)1);
    styleHdr.setFont(fontHdr);


    fontData = hwb.createFont();
    fontData.setFontHeightInPoints((short)10);
    fontData.setFontName(fontNm);

    styleData = hwb.createCellStyle();
    styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleData.setFont(fontData);
    ArrayList labList= info.getLabList();
    HashMap lab_qtyctstable=info.getLab_qtyctstable();
    HashMap stt_qtyctstable=info.getStt_qtyctstable();
    String gndCtsQty=(String)session.getAttribute("gndCtsQty");
    sheet = addSheet();
    row = newRow();
    int currCell = ++cellCt;
    CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    addCell(currCell++, "STATUS/LAB", styleHdr);
    for(int i=0;i<labList.size();i++)
    {
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    addMergeCell(currCell++, (String)labList.get(i), styleHdr, rang);
    cellCt = currCell;
    }
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    addMergeCell(currCell++, "Total", styleHdr, rang);

    cellCt = currCell+1;
    row = newRow();
    addCell(++cellCt, "", styleHdr);
    for(int i=0;i<=labList.size();i++)
    {

    addCell(++cellCt, "Quantity", styleHdr);
    addCell(++cellCt, "Cts", styleHdr);
    }
    sheet.createFreezePane(0, rowCt+1);
    ArrayList dfStkStt=info.getDfStkStt();
    ArrayList sttlist=info.getSttList();
    ArrayList dfStt=null;
    for(int j=0;j<dfStkStt.size();j++)
    {
    dfStt=new ArrayList();
    dfStt=(ArrayList)dfStkStt.get(j);
    String stt=util.nvl((String)dfStt.get(0));
    String dsc=util.nvl((String)dfStt.get(1));
    int cls=j % 2;
    row = newRow();
    addCell(++cellCt,dsc, styleHdr);
    int totcolm=labList.size()*2;
    for(int k=0;k<labList.size();k++)
    {
    String lab=(String)labList.get(k);
    if(lab.equals("NA") && stt.equals("MKAV"))
    {
//    System.out.println("NA");
    }
    HashMap qty_ctstbl=info.getQty_ctstbl();
    String qty_cts=util.nvl((String)qty_ctstbl.get(lab+"_"+stt));
    String qty="";
    String cts="";
    if(!qty_cts.equals(""))
    {
    int indx=qty_cts.indexOf("|");
    qty=util.nvl(qty_cts.substring(0,indx));
    if(qty.contains("null"))
    {
    qty="0";
    }
    cts=util.nvl(qty_cts.substring(indx+1, qty_cts.length()));
    if(cts.contains("null"))
    {
    cts="0";
    }
    String idval="id"+cts;
    addCell(++cellCt, qty, styleHdr);
    addCell(++cellCt, cts, styleHdr);
    }else{
    addCell(++cellCt, "", styleHdr);
    addCell(++cellCt, "", styleHdr);
    }
    }



    if(stt_qtyctstable.containsKey(stt))
    {
    if(!(stt_qtyctstable.get(stt)).equals("")){
    String qty_cts=(String)stt_qtyctstable.get(stt);
    int indx=qty_cts.indexOf("|");
    String qty=util.nvl(qty_cts.substring(0,indx));
    String cts=util.nvl(qty_cts.substring(indx+1, qty_cts.length()));
    if(qty.contains("null"))
    {
    qty="0";
    }
    if(cts.contains("null"))
    {
    cts="0";
    }
    addCell(++cellCt, qty, styleHdr);
    addCell(++cellCt, cts, styleHdr);
    }else{
    addCell(++cellCt, "", styleHdr);
    addCell(++cellCt, "", styleHdr);
    }
    }
    else{
    addCell(++cellCt, "", styleHdr);
    addCell(++cellCt, "", styleHdr);
    }
    }
    row = newRow();

    addCell(++cellCt, "Total", styleHdr);

    if(lab_qtyctstable.size()>0)
    {
    for(int i=0;i<labList.size();i++)
    {
    String lab=util.nvl((String)labList.get(i));
    String qtycts=util.nvl((String)lab_qtyctstable.get(lab));
    if(!qtycts.equals(""))
    {
    int indx=qtycts.indexOf("|");
    String qty=util.nvl(qtycts.substring(0,indx));
    String cts=util.nvl(qtycts.substring(indx+1, qtycts.length()));
    if(qty.contains("null"))
    {
    qty="0";
    }
    if(cts.contains("null"))
    {
    cts="0";
    }
    addCell(++cellCt, qty, styleHdr);
    addCell(++cellCt, cts, styleHdr);
    }else{
    addCell(++cellCt, "", styleHdr);
    addCell(++cellCt, "", styleHdr);
    }
    }
    }
    if(!gndCtsQty.equals(""))
    {
    int indx=gndCtsQty.indexOf("|");
    String qty=util.nvl(gndCtsQty.substring(0,indx));
    String cts=util.nvl(gndCtsQty.substring(indx+1, gndCtsQty.length()));
    if(qty.contains("null"))
    {
    qty="0";
    }
    if(cts.contains("null"))
    {
    cts="0";
    }
    addCell(++cellCt, qty, styleHdr);
    addCell(++cellCt, cts, styleHdr);
    }else{
    addCell(++cellCt, "", styleHdr);
    addCell(++cellCt, "", styleHdr);
    }


    Iterator it=autoColums.iterator();
    while(it.hasNext()) {
    String value=(String)it.next();
    int colId = Integer.parseInt(value);
    sheet.autoSizeColumn((short)colId, true);
    }



    return hwb;
    }
    public HSSFWorkbook getDataShapeSzInXl(HttpServletRequest req) {
    hwb = new HSSFWorkbook();
    autoColums = new TreeSet();
    session = req.getSession(false);
    String fontNm = "Geneva";
    fontHdr = hwb.createFont();
    fontHdr.setFontHeightInPoints((short)11);
    fontHdr.setFontName(fontNm);
    fontHdr.setColor(HSSFColor.BLACK.index);
    fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

    styleHdr = hwb.createCellStyle();
    styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleHdr.setBorderBottom((short)1);
    styleHdr.setBorderTop((short)1);
    styleHdr.setBorderLeft((short)1);
    styleHdr.setBorderRight((short)1);
    styleHdr.setFont(fontHdr);


    fontData = hwb.createFont();
    fontData.setFontHeightInPoints((short)10);
    fontData.setFontName(fontNm);

    styleData = hwb.createCellStyle();
    styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleData.setFont(fontData);
    ArrayList shapeList= info.getShapeList();
    ArrayList sizeList=info.getSizeList();
    HashMap sh_QtyCtstbl= info.getSh_QtyCtstbl();
    HashMap sz_Qtyctstbl= info.getSz_Qtyctstbl();
    String grdshSzctsqty= (String)session.getAttribute("grdshSzctsqty");
    sheet = addSheet();
    row = newRow();
    int currCell = ++cellCt;
    CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    addCell(currCell++, "SHAPE/SIZE", styleHdr);

    for(int i=0;i<sizeList.size();i++)
    {
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    addMergeCell(currCell++, (String)sizeList.get(i), styleHdr, rang);
    cellCt = currCell;
    }

    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    addMergeCell(currCell++, "Total", styleHdr, rang);

    cellCt = currCell+1;
    row = newRow();
    addCell(++cellCt, "", styleHdr);
    for(int i=0;i<=sizeList.size();i++)
    {

    addCell(++cellCt, "Quantity", styleHdr);
    addCell(++cellCt, "Cts", styleHdr);
    }
    sheet.createFreezePane(0, rowCt+1);

    for(int j=0;j<shapeList.size();j++)
    {
    String shape=(String)shapeList.get(j);
    String styleClass=null;
    int cls=j % 2;
    row = newRow();
    addCell(++cellCt,shape, styleHdr);
    for(int k=0;k<sizeList.size();k++)
    {
    String size=(String)sizeList.get(k);
    HashMap Qtyctsshsztbl= info.getQtyctsshsztbl();
    String qty_cts=util.nvl((String)Qtyctsshsztbl.get(shape+"_"+size));
    String qty="";
    String cts="";
    if(!qty_cts.equals(""))
    {
    int indx=qty_cts.indexOf("|");
    qty=util.nvl(qty_cts.substring(0,indx));
    cts=util.nvl(qty_cts.substring(indx+1, qty_cts.length()));
    if(qty.contains("null"))
    {
    qty="0";
    }
    if(cts.contains("null"))
    {
    cts="0";
    }
    addCell(++cellCt, qty, styleHdr);
    addCell(++cellCt, cts, styleHdr);

    }else
    {

    addCell(++cellCt, "", styleHdr);
    addCell(++cellCt, "", styleHdr);
    }
//    System.out.println("size"+size);
    }

    if(!(util.nvl((String)sh_QtyCtstbl.get(shape))).equals(""))
    {
    String qty_cts=(String)sh_QtyCtstbl.get(shape);
    int indx=qty_cts.indexOf("|");
    String qty=util.nvl(qty_cts.substring(0,indx));
    String cts=util.nvl(qty_cts.substring(indx+1, qty_cts.length()));
//    System.out.println("qty_cts"+qty_cts);
    if(qty.contains("null"))
    {
    qty="0";
    }
    if(cts.contains("null"))
    {
    cts="0";
    }
    addCell(++cellCt, qty, styleHdr);
    addCell(++cellCt, cts, styleHdr);
    }else
    {

    addCell(++cellCt, "", styleHdr);
    addCell(++cellCt, "", styleHdr);
    }

//    System.out.println("shape"+shape);

    }
    row = newRow();
    addCell(++cellCt, "Total", styleHdr);
    for(int i=0;i<sizeList.size();i++)
    {
    String sz=util.nvl((String)sizeList.get(i));
    String qtycts=util.nvl((String)sz_Qtyctstbl.get(sz));
    if(!qtycts.equals(""))
    {
    int indx=qtycts.indexOf("|");
    String qty=util.nvl(qtycts.substring(0,indx));
    String cts=util.nvl(qtycts.substring(indx+1, qtycts.length()));
    if(qty.contains("null"))
    {
    qty="0";
    }
    if(cts.contains("null"))
    {
    cts="0";
    }
    addCell(++cellCt, qty, styleHdr);
    addCell(++cellCt, cts, styleHdr);

    }
    else
    {
    addCell(++cellCt, "", styleHdr);
    addCell(++cellCt, "", styleHdr);
    }
    }

    if(!grdshSzctsqty.equals(""))
    {
    int indx=grdshSzctsqty.indexOf("|");
    String qty=util.nvl(grdshSzctsqty.substring(0,indx));
    String cts=util.nvl(grdshSzctsqty.substring(indx+1, grdshSzctsqty.length()));
    if(qty.contains("null"))
    {
    qty="0";
    }
    if(cts.contains("null"))
    {
    cts="0";
    }
    addCell(++cellCt, qty, styleHdr);
    addCell(++cellCt, cts, styleHdr);

    }
    else
    {
    addCell(++cellCt, "", styleHdr);
    addCell(++cellCt, "", styleHdr);
    }
    Iterator it=autoColums.iterator();
    while(it.hasNext()) {
    String value=(String)it.next();
    int colId = Integer.parseInt(value);
    sheet.autoSizeColumn((short)colId, true);
    }



    return hwb;
    }

    public HSSFWorkbook getDataColorPurityInXl(HttpServletRequest req) {
    hwb = new HSSFWorkbook();
    autoColums = new TreeSet();
    session = req.getSession(false);
    String fontNm = "Geneva";
    fontHdr = hwb.createFont();
    fontHdr.setFontHeightInPoints((short)11);
    fontHdr.setFontName(fontNm);
    fontHdr.setColor(HSSFColor.BLACK.index);
    fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

    styleHdr = hwb.createCellStyle();
    styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleHdr.setBorderBottom((short)1);
    styleHdr.setBorderTop((short)1);
    styleHdr.setBorderLeft((short)1);
    styleHdr.setBorderRight((short)1);
    styleHdr.setFont(fontHdr);


    fontData = hwb.createFont();
    fontData.setFontHeightInPoints((short)10);
    fontData.setFontName(fontNm);

    styleData = hwb.createCellStyle();
    styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleData.setFont(fontData);
    ArrayList purityList= info.getPurityList();
    ArrayList colorList=info.getColorList();
    HashMap col_ctsqty=info.getCol_ctsqty();
    HashMap clry_ctsqty=info.getClry_ctsqty();
    String grdqty_cts=(String) session.getAttribute("grndcolClr");
    sheet = addSheet();
    row = newRow();
    int currCell = ++cellCt;
    CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    addCell(currCell++, "COLOR/PURITY", styleHdr);

    for(int i=0;i<purityList.size();i++)
    {
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    addMergeCell(currCell++, (String)purityList.get(i), styleHdr, rang);
    cellCt = currCell;
    }

    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    addMergeCell(currCell++, "Total", styleHdr, rang);

    cellCt = currCell+1;
    row = newRow();
    addCell(++cellCt, "", styleHdr);
    for(int i=0;i<=purityList.size();i++)
    {

    addCell(++cellCt, "Quantity", styleHdr);
    addCell(++cellCt, "Cts", styleHdr);
    }
    sheet.createFreezePane(0, rowCt+1);

    for(int j=0;j<colorList.size();j++)
    {
    String color=(String)colorList.get(j);

    String styleClass=null;
    int cls=j % 2;
    row = newRow();
    addCell(++cellCt,color, styleHdr);

    for(int k=0;k<purityList.size();k++)
    {
    String purity=(String)purityList.get(k);
    HashMap QtyctsClrPurtbl= info.getQtyctsClrPurtbl();
    String qty_cts=util.nvl((String)QtyctsClrPurtbl.get(color+"_"+purity));
    String qty="";
    String cts="";
    if(!qty_cts.equals(""))
    {
    int indx=qty_cts.indexOf("|");
    qty=util.nvl(qty_cts.substring(0,indx));
    cts=util.nvl(qty_cts.substring(indx+1, qty_cts.length()));
    String idval="id"+cts;
    if(qty.contains("null"))
    {
    qty="0";
    }
    if(cts.contains("null"))
    {
    cts="0";
    }
    addCell(++cellCt, qty, styleHdr);
    addCell(++cellCt, cts, styleHdr);
    }else{
    addCell(++cellCt, "", styleHdr);
    addCell(++cellCt, "", styleHdr);
    }

    }

    if(!(util.nvl((String)col_ctsqty.get(color))).equals(""))
    {
    String qty_cts=(String)col_ctsqty.get(color);
    int indx=qty_cts.indexOf("|");
    String qty=util.nvl(qty_cts.substring(0,indx));
    String cts=util.nvl(qty_cts.substring(indx+1, qty_cts.length()));
//    System.out.println("qty_cts"+qty_cts);
    if(qty.contains("null"))
    {
    qty="0";
    }
    if(cts.contains("null"))
    {
    cts="0";
    }
    addCell(++cellCt, qty, styleHdr);
    addCell(++cellCt, cts, styleHdr);
    }else{
    addCell(++cellCt, "", styleHdr);
    addCell(++cellCt, "", styleHdr);
    }

//    System.out.println("shape"+color);
    }
    row = newRow();
    addCell(++cellCt, "Total", styleHdr);
    for(int i=0;i<purityList.size();i++)
    {
    String purity=util.nvl((String)purityList.get(i));
    String qtycts=util.nvl((String)clry_ctsqty.get(purity));
    if(!qtycts.equals(""))
    {
    int indx=qtycts.indexOf("|");
    String qty=util.nvl(qtycts.substring(0,indx));
    String cts=util.nvl(qtycts.substring(indx+1, qtycts.length()));
    if(qty.contains("null"))
    {
    qty="0";
    }
    if(cts.contains("null"))
    {
    cts="0";
    }
    addCell(++cellCt, qty, styleHdr);
    addCell(++cellCt, cts, styleHdr);
    }else{
    addCell(++cellCt, "", styleHdr);
    addCell(++cellCt, "", styleHdr);
    }
    }

    if(!grdqty_cts.equals(""))
    {
    int indx=grdqty_cts.indexOf("|");
    String qty=util.nvl(grdqty_cts.substring(0,indx));
    String cts=util.nvl(grdqty_cts.substring(indx+1, grdqty_cts.length()));
    if(qty.contains("null"))
    {
    qty="0";
    }
    if(cts.contains("null"))
    {
    cts="0";
    }
    addCell(++cellCt, qty, styleHdr);
    addCell(++cellCt, cts, styleHdr);
    }else{
    addCell(++cellCt, "", styleHdr);
    addCell(++cellCt, "", styleHdr);
    }

    Iterator it=autoColums.iterator();
    while(it.hasNext()) {
    String value=(String)it.next();
    int colId = Integer.parseInt(value);
    sheet.autoSizeColumn((short)colId, true);
    }
    return hwb;
    }
    
    public HSSFWorkbook getDataGridInXl(HttpServletRequest req,String colSpan,String qty,String cts,String avg,String rap,String report) {
    hwb = new HSSFWorkbook();
    autoColums = new TreeSet();
    session = req.getSession(false);
    String fontNm = "Geneva";
    fontHdr = hwb.createFont();
    fontHdr.setFontHeightInPoints((short)11);
    fontHdr.setFontName(fontNm);
    fontHdr.setColor(HSSFColor.BLACK.index);
    fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

    styleHdr = hwb.createCellStyle();
    styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleHdr.setBorderBottom((short)1);
    styleHdr.setBorderTop((short)1);
    styleHdr.setBorderLeft((short)1);
    styleHdr.setBorderRight((short)1);
    styleHdr.setFont(fontHdr);


    fontData = hwb.createFont();
    fontData.setFontHeightInPoints((short)10);
    fontData.setFontName(fontNm);

    styleData = hwb.createCellStyle();
    styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleData.setFont(fontData);
    ArrayList hdrLst= new ArrayList();
    ArrayList contentList= new ArrayList();
    HashMap dataTbl= new HashMap();
    HashMap reportDtl= (HashMap)session.getAttribute("reportDtl");
    if(reportDtl!=null && reportDtl.size()>0){
    hdrLst=(ArrayList)reportDtl.get(report+"_HDR");
    contentList=(ArrayList)reportDtl.get(report+"_CONTENT");
    dataTbl=(HashMap)reportDtl.get(report+"_DATA");
    }
    String TITLE=util.nvl((String)dataTbl.get("TITLE"));
    HashMap data=new HashMap();
    String Qty="";
    String Cts="";
    String Avg="";
    String Dis="";
    int span=Integer.parseInt(colSpan);
    sheet = addSheet();
    row = newRow();
    int currCell = ++cellCt;
    // CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    addCell(currCell++, TITLE, styleHdr);

    for(int i=0;i<hdrLst.size();i++)
    {
    currCell = ++cellCt;
    // rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    rang = mergeCell(rowCt, rowCt, currCell, currCell+span);
    addMergeCell(currCell, (String)hdrLst.get(i), styleHdr, rang);
    cellCt = currCell+span;
    }

    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell+span);
    addMergeCell(currCell++, "Total", styleHdr, rang);

    cellCt = currCell+1;
    row = newRow();
    addCell(++cellCt, "", styleHdr);
    for(int i=0;i<=hdrLst.size();i++)
    {
    if(qty.equals("Y"))
    addCell(++cellCt, "QTY", styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, "CTS", styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, "AVG", styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, "RAP", styleHdr);
    }
    sheet.createFreezePane(0, rowCt+1);

    for(int j=0;j<contentList.size();j++){
    String contnt=(String)contentList.get(j);
    String dsc=util.nvl2((String)dataTbl.get(contnt),contnt);
    row = newRow();
    addCell(++cellCt,dsc, styleHdr);
    for(int k=0;k<hdrLst.size();k++){
    String hdr=(String)hdrLst.get(k);
    data=new HashMap();
    Qty="";
    Cts="";
    Avg="";
    Dis="";
    data=(HashMap)dataTbl.get(contnt+"_"+hdr);
    if(data!=null && data.size()>0){
    Qty=util.nvl((String)data.get("QTY"));
    Cts=util.nvl((String)data.get("CTS"));
    Avg=util.nvl((String)data.get("AVG"));
    Dis=util.nvl((String)data.get("RAP"));
    }
    if(qty.equals("Y"))
    addCell(++cellCt, Qty, styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, Cts, styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, Avg, styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, Dis, styleHdr);
    }
    Qty="";
    Cts="";
    Avg="";
    Dis="";
    data=new HashMap();
    data=(HashMap)dataTbl.get(contnt+"_TTL");
    if(data!=null && data.size()>0){
    Qty=util.nvl((String)data.get("QTY"));
    Cts=util.nvl((String)data.get("CTS"));
    Avg=util.nvl((String)data.get("AVG"));
    Dis=util.nvl((String)data.get("RAP"));
    }
    if(qty.equals("Y"))
    addCell(++cellCt, Qty, styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, Cts, styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, Avg, styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, Dis, styleHdr);
    }
    row = newRow();
    addCell(++cellCt, "Total", styleHdr);
    for(int i=0;i<hdrLst.size();i++){
    String lab=(String)hdrLst.get(i);
    Qty="";
    Cts="";
    Avg="";
    Dis="";
    data=new HashMap();
    data=(HashMap)dataTbl.get(lab+"_TTL");
    if(data!=null && data.size()>0){
    Qty=util.nvl((String)data.get("QTY"));
    Cts=util.nvl((String)data.get("CTS"));
    Avg=util.nvl((String)data.get("AVG"));
    Dis=util.nvl((String)data.get("RAP"));
    }
    if(qty.equals("Y"))
    addCell(++cellCt, Qty, styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, Cts, styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, Avg, styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, Dis, styleHdr);
    }
    Qty="";
    Cts="";
    Avg="";
    Dis="";
    data=new HashMap();
    data=(HashMap)dataTbl.get("TTL");
    if(data!=null && data.size()>0){
    Qty=util.nvl((String)data.get("QTY"));
    Cts=util.nvl((String)data.get("CTS"));
    Avg=util.nvl((String)data.get("AVG"));
    Dis=util.nvl((String)data.get("RAP"));
    }
    if(qty.equals("Y"))
    addCell(++cellCt, Qty, styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, Cts, styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, Avg, styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, Dis, styleHdr);
    Iterator it=autoColums.iterator();
    while(it.hasNext()) {
    String value=(String)it.next();
    int colId = Integer.parseInt(value);
    sheet.autoSizeColumn((short)colId, true);
    }
    return hwb;
    }
    
    public HSSFWorkbook getMonthlyCmpInXl(HttpServletRequest req) {
        hwb = new HSSFWorkbook();
        autoColums = new TreeSet();
        session = req.getSession(false);
        String fontNm = "Geneva";
        fontHdr = hwb.createFont();
        fontHdr.setFontHeightInPoints((short)11);
        fontHdr.setFontName(fontNm);
        fontHdr.setColor(HSSFColor.BLACK.index);
        fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

        styleHdr = hwb.createCellStyle();
        styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleHdr.setBorderBottom((short)1);
        styleHdr.setBorderTop((short)1);
        styleHdr.setBorderLeft((short)1);
        styleHdr.setBorderRight((short)1);
        styleHdr.setFont(fontHdr);


        fontData = hwb.createFont();
        fontData.setFontHeightInPoints((short)10);
        fontData.setFontName(fontNm);

        styleData = hwb.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setFont(fontData);
        HashMap datatable= (HashMap)session.getAttribute("datatable");
        ArrayList rowLst= (ArrayList)session.getAttribute("rowLst");
        ArrayList colLst= (ArrayList)session.getAttribute("colLst");
            int headSpan=0; 
            if(datatable!=null && datatable.size()>0){
            HashMap data=new HashMap();
            HashMap dataGnt=new HashMap();
            ArrayList gridLst= (ArrayList)session.getAttribute("gridLst");
            ArrayList vWPrpList = (ArrayList)session.getAttribute("memocomp_vw");
            HashMap ttlmap= (HashMap)session.getAttribute("ttlmap");
            String key=util.nvl((String)datatable.get("ROW")); 
            String headTTl=util.nvl((String)datatable.get("HEAD"));
            String rpttyp=util.nvl((String)datatable.get("RPTTYP"));
            headSpan=colLst.size();
            ArrayList dscLst=new ArrayList();
            dscLst.add("+");dscLst.add("=");dscLst.add("-"); 
            HashMap mprp = info.getMprp();
            String CNT="";
            String CNTGNT="";
            sheet = addSheet();
            
            
        for(int l=0;l<gridLst.size();l++){
        String grid=(String)gridLst.get(l);
            row = newRow();
            int currCell = ++cellCt;
            CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell+(1+(headSpan*3)+headSpan+3)-1);
            addCell(currCell++, util.nvl((String)mprp.get(grid+"D"))+" - Monthly Comparision", styleHdr);
            row = newRow();
            addCell(++cellCt, headTTl,styleHdr);
            currCell = ++cellCt;
            if(rpttyp.equals("D")){
            rang = mergeCell(rowCt, rowCt, currCell, headSpan*3);
            addMergeCell(currCell, util.nvl((String)mprp.get(grid+"D")), styleHdr, rang);
            cellCt = currCell+(headSpan*3)-1;
            currCell = ++cellCt;
            }
            rang = mergeCell(rowCt, rowCt, currCell, currCell+headSpan-1);
            addMergeCell(currCell, "Total", styleHdr, rang);
            cellCt = currCell+(headSpan)-1;
            currCell = ++cellCt;
            rang = mergeCell(rowCt, rowCt, currCell, (currCell+3)-1);
            addMergeCell(currCell, "Grand Total", styleHdr, rang);
            row = newRow();
            addCell(++cellCt, "", styleHdr);
            currCell = ++cellCt;
            if(rpttyp.equals("D")){
            rang = mergeCell(rowCt, rowCt, currCell, currCell+headSpan-1);
            addMergeCell(currCell, "+", styleHdr, rang);
            cellCt = currCell+headSpan-1;
            currCell = ++cellCt;
            rang = mergeCell(rowCt, rowCt, currCell, currCell+headSpan-1);
            addMergeCell(currCell, "=", styleHdr, rang);
            cellCt = currCell+headSpan-1;
            currCell = ++cellCt;
            rang = mergeCell(rowCt, rowCt, currCell, currCell+headSpan-1);
            addMergeCell(currCell, "-", styleHdr, rang);
            cellCt = currCell+headSpan-1;
            currCell = ++cellCt;
            }
            rang = mergeCell(rowCt, rowCt, currCell, currCell+headSpan-1);
            addMergeCell(currCell, "", styleHdr, rang);
            cellCt = currCell+headSpan-1;
            addCell(++cellCt, "+", styleHdr);
            addCell(++cellCt, "=", styleHdr);
            addCell(++cellCt, "-", styleHdr);
            row = newRow();
            addCell(++cellCt, "", styleHdr);
        if(rpttyp.equals("D")){
        for(int i=0;i<dscLst.size();i++){
        for(int j=0;j<colLst.size();j++){
                String col =(String)colLst.get(j);
        addCell(++cellCt, util.nvl((String)datatable.get(col),col), styleHdr);
        }}
        }                     
        for(int j=0;j<colLst.size();j++){
                String col =(String)colLst.get(j);
            addCell(++cellCt, util.nvl((String)datatable.get(col),col), styleHdr);
        }
            addCell(++cellCt, "", styleHdr);
            currCell = ++cellCt;
            rang = mergeCell(rowCt, rowCt, currCell, (currCell+3)-1);
            addMergeCell(currCell, "", styleHdr, rang);
            for(int i=0;i<rowLst.size();i++){
            String rowkey =(String)rowLst.get(i);
        row = newRow();
        addCell(++cellCt, util.nvl((String)datatable.get(rowkey),rowkey), styleHdr);
        if(rpttyp.equals("D")){
        for(int j=0;j<dscLst.size();j++){
        String dsc=(String)dscLst.get(j); 
        int ttl=0;
        for(int k=0;k<colLst.size();k++){
        CNT="0";CNTGNT="0";
        double per=0.0;
        String colkey=(String)colLst.get(k);
        data=new HashMap();
        dataGnt=new HashMap();
        data=(HashMap)datatable.get(rowkey+"_"+colkey+"_"+dsc+"_"+grid);
        dataGnt=(HashMap)datatable.get(rowkey+"_"+colkey+"_Total"+"_"+grid);
         if(data!=null && data.size()>0){
        CNT=util.nvl((String)data.get("CNT"));
        CNTGNT=util.nvl((String)dataGnt.get("CNT"));
        per=(Double.parseDouble(CNT)/Double.parseDouble(CNTGNT))*100;
         }
        ttl=ttl+Integer.parseInt(CNT);
        addCell(++cellCt,CNT+ " | "+String.valueOf(Math.round(per))+"%", styleHdr);
        }
        }
        }
        for(int j=0;j<colLst.size();j++){
        CNT="0";
        String colkey=(String)colLst.get(j);
        data=new HashMap();
        data=(HashMap)datatable.get(rowkey+"_"+colkey+"_Total"+"_"+grid);
        if(data!=null && data.size()>0)
        CNT=util.nvl((String)data.get("CNT"));
        addCell(++cellCt,CNT, styleHdr);
        }
            for(int j=0;j<dscLst.size();j++){
            String dsc=(String)dscLst.get(j);
            CNT=util.nvl((String)ttlmap.get(rowkey+"_"+dsc+"_"+grid),"0");
                String curval=util.nvl((String)ttlmap.get(rowkey+"_"+dsc+"_"+grid+"_CURVAL"),"0");
                String asrtval=util.nvl((String)ttlmap.get(rowkey+"_"+dsc+"_"+grid+"_ASRTVAL"),"0");
                String GRANDCNT=util.nvl((String)String.valueOf(ttlmap.get(rowkey+"_Total_"+grid)));
                String grandcurval=util.nvl((String)String.valueOf(ttlmap.get(rowkey+"_Total_"+grid+"_CURVAL")));
                String grandasrtval=util.nvl((String)String.valueOf(ttlmap.get(rowkey+"_Total_"+grid+"_ASRTVAL")));
                double per=0.0;
                per=(Double.parseDouble(CNT)/Double.parseDouble(GRANDCNT))*100;
                double curper=0.0;
                curper=(Double.parseDouble(curval)/Double.parseDouble(grandcurval))*100;
                double asrtper=0.0;
                asrtper=(Double.parseDouble(asrtval)/Double.parseDouble(grandasrtval))*100;
            addCell(++cellCt,CNT+ " | "+String.valueOf(Math.round(per))+"%"+ " | "+String.valueOf(Math.round(curper))+"%"+ " | "+String.valueOf(Math.round(asrtper))+"%", styleHdr);
            }
        }
        row = newRow();
        }
        Iterator it=autoColums.iterator();
        while(it.hasNext()) {
        String value=(String)it.next();
        int colId = Integer.parseInt(value);
        sheet.autoSizeColumn((short)colId, true);
        }
            }
        return hwb;    
    }
    public HSSFWorkbook getDataKacchaInXl(HttpServletRequest req) {
    hwb = new HSSFWorkbook();
    autoColums = new TreeSet();
    session = req.getSession(false);
    String fontNm = "Geneva";
    fontHdr = hwb.createFont();
    fontHdr.setFontHeightInPoints((short)11);
    fontHdr.setFontName(fontNm);
    fontHdr.setColor(HSSFColor.BLACK.index);
    fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

    styleHdr = hwb.createCellStyle();
    styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleHdr.setBorderBottom((short)1);
    styleHdr.setBorderTop((short)1);
    styleHdr.setBorderLeft((short)1);
    styleHdr.setBorderRight((short)1);
    styleHdr.setFont(fontHdr);


    fontData = hwb.createFont();
    fontData.setFontHeightInPoints((short)9);
    fontData.setFontName(fontNm);

    styleData = hwb.createCellStyle();
    styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleData.setBorderBottom((short)1);
    styleData.setBorderTop((short)1);
    styleData.setBorderLeft((short)1);
    styleData.setBorderRight((short)1);
    styleData.setFont(fontData);
    HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
    ArrayList memoLst= (ArrayList)session.getAttribute("memoLst");
    ArrayList sttLst= (ArrayList)session.getAttribute("sttLst");
    HashMap data=new HashMap();
    HashMap datastt=new HashMap();
    ArrayList keystt=new ArrayList();
    String stt="";
    sheet = addSheet();
    for(int i=0;i<memoLst.size();i++){
    String memo=(String)memoLst.get(i);
    data=new HashMap();
    datastt=new HashMap();
    keystt=new ArrayList();
    row = newRow();
    row = newRow();
    int currCell = ++cellCt;
    CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "Status", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "Pics", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "GiaCrt", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "GrdCrt", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "Ka Amt", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "Ka Avg", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "MnAmt", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "MnjAvg", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    addCell(currCell, "Memo ID", styleHdr);
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "Dept", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "TotCrt", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "TotIss-Rec Amt", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "TotIss-Rec Crt", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "TotK Amt", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "Akhi Avg", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "Send Vlu", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "Send Crt", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "Send %", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "Added Per", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "Baki Avg", styleHdr, rang);
    cellCt = currCell;
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell);
    addMergeCell(currCell, "Baki Amt", styleHdr, rang);
    cellCt = currCell;
    for(int j=0;j<sttLst.size();j++){
    stt=(String)sttLst.get(j);
    data=new HashMap();
    data=(HashMap)dataDtl.get(memo+"_"+stt);
    if(data!=null && data.size()>0){
    datastt.put(stt,data);
    keystt.add(stt);
    }}

    for(int k=0;k<datastt.size();k++){
    data=new HashMap();
    stt=(String)sttLst.get(k);
    data=(HashMap)datastt.get(stt);
    row = newRow();
    addCell(++cellCt,stt, styleHdr);
    addCell(++cellCt, (String)data.get("QTY"), styleData);
    addCell(++cellCt, (String)data.get("GIACTS"), styleData);
    addCell(++cellCt, (String)data.get("GRDCTS"), styleData);
    addCell(++cellCt, (String)data.get("VLU"), styleData);
    addCell(++cellCt, (String)data.get("AVG"), styleData);
    addCell(++cellCt, (String)data.get("MNJAMT"), styleData);
    addCell(++cellCt, (String)data.get("MNJAVG"), styleData);
    if(k==(datastt.size()-1)){
    data=new HashMap();
    data=(HashMap)dataDtl.get(memo+"_TTL");
    if(data==null)
    data=new HashMap();
    addCell(++cellCt,memo, styleHdr);
    addCell(++cellCt, (String)data.get("DEPT"), styleData);
    addCell(++cellCt, (String)data.get("TCTS"), styleData);
    addCell(++cellCt, (String)data.get("TISS"), styleData);
    addCell(++cellCt, (String)data.get("TISSCTS"), styleData);
    addCell(++cellCt, (String)data.get("TKAMT"), styleData);
    addCell(++cellCt, (String)data.get("TAAVG"), styleData);
    addCell(++cellCt, (String)data.get("SVLU"), styleData);
    addCell(++cellCt, (String)data.get("SCTS"), styleData);
    addCell(++cellCt, (String)data.get("TBAKI"), styleData);
    addCell(++cellCt, (String)data.get("TADD"), styleData);
    addCell(++cellCt, (String)data.get("TBAKIAVG"), styleData);
    addCell(++cellCt, (String)data.get("TBAKIAMT"), styleData);
    }else{
    addCell(++cellCt,"", styleHdr);
    addCell(++cellCt, "", styleData);
    addCell(++cellCt, "", styleData);
    addCell(++cellCt, "", styleData);
    addCell(++cellCt,"", styleData);
    addCell(++cellCt, "", styleData);
    addCell(++cellCt, "", styleData);
    addCell(++cellCt, "", styleData);
    addCell(++cellCt, "", styleData);
    addCell(++cellCt,"", styleData);
    addCell(++cellCt,"", styleData);
    addCell(++cellCt,"", styleData);
    addCell(++cellCt,"", styleData);
    }
    }
    }

    return hwb;
    }
    
    public HSSFWorkbook getMatrixXl(String loopindex,ArrayList rheadList,ArrayList cheadList,HashMap dataDtl,ArrayList shSzList) {

        hwb = new HSSFWorkbook();
        autoColums = new TreeSet();
        String fontNm = "Cambria";
        fontHdr = hwb.createFont();
        fontHdr.setFontHeightInPoints((short)10);
        fontHdr.setFontName(fontNm);
        fontHdr.setColor(HSSFColor.BLACK.index);
        fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
        styleHdr = hwb.createCellStyle();
        styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleHdr.setFont(fontHdr);
        fontData = hwb.createFont();
        fontData.setFontHeightInPoints((short)10);
        fontData.setFontName(fontNm);
        styleData = hwb.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setFont(fontData);
        sheet = addSheet();
        String key="";
        for(int l=0;l<shSzList.size();l++){
        if(loopindex.equals(""))
        key=(String)shSzList.get(l);
        else{
        key=(String)shSzList.get(Integer.parseInt(loopindex));
        l=shSzList.size()+1;
        }
        row = newRow();
        String keyLable = key.replaceAll("_", " SIZE :");
        keyLable = "Shape : "+keyLable;
        int currCell = ++cellCt;
        CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell+(cheadList.size()));
        addCell(currCell++, keyLable, styleHdr);
        row = newRow();
        addCell(++cellCt, util.nvl((String)dataDtl.get("title")), styleHdr);
        for (int j = 0; j < cheadList.size(); j++) {
        String columnV = (String)cheadList.get(j);
        addCell(++cellCt, columnV, styleHdr);
        }

        for(int m=0;m< rheadList.size();m++){
        String rowV = (String)rheadList.get(m);
        row = newRow();
        addCell(++cellCt, rowV, styleData);
        for(int n=0;n< cheadList.size();n++){
        String columnV = (String)cheadList.get(n);
        String keymfg_fnl = key+"_"+rowV+"_"+columnV;
        String valmfg_fnl = util.nvl((String)dataDtl.get(keymfg_fnl));
        addCell(++cellCt, valmfg_fnl, styleData);
        }
        }
        row = newRow();
        }
        Iterator it= autoColums.iterator();
        while(it.hasNext()) {
        String value=(String)it.next();
        int colId = Integer.parseInt(value);
        sheet.autoSizeColumn((short)colId, true);

        }


        return hwb;

        }
    public HSSFWorkbook getmixtrfcreateXL(HttpServletRequest req,String loopindex) {

       hwb = new HSSFWorkbook();
       autoColums = new TreeSet();
       session = req.getSession(false);
        String fontNm = "Cambria";
        fontHdr = hwb.createFont();
        fontHdr.setFontHeightInPoints((short)10);
        fontHdr.setFontName(fontNm);
        fontHdr.setColor(HSSFColor.BLACK.index);
        fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
        styleHdr = hwb.createCellStyle();
        styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleHdr.setFont(fontHdr);
        fontData = hwb.createFont();
        fontData.setFontHeightInPoints((short)10);
        fontData.setFontName(fontNm);
        styleData = hwb.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setFont(fontData);
        sheet = addSheet();
        String key="";
        HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
        ArrayList clrlist= (ArrayList)session.getAttribute("clrlist");
        ArrayList keylist= (ArrayList)session.getAttribute("keylist");
        HashMap prp = info.getPrp();
        ArrayList mixclrList = (ArrayList)prp.get("MIX_CLARITYV");
        int mixclrListsz=mixclrList.size();
        for(int lo=0;lo<keylist.size();lo++){
        if(loopindex.equals(""))
        key=(String)keylist.get(lo);
        else{
        key=(String)keylist.get(Integer.parseInt(loopindex));
        lo=keylist.size()+1;
        }
            row = newRow();
                    String keyLable = key.replaceAll("_", " SHAPE :");
                      ArrayList mixszList=new ArrayList();
                      mixszList=(ArrayList)dataDtl.get(key);
                      int mixszListsz=mixszList.size();
                    int currCell = ++cellCt;
                    CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell+(mixszListsz));
                    addCell(currCell++, keyLable, styleHdr);
                    row = newRow();
                            addCell(++cellCt, "Mix Clarity/Mix Size", styleHdr);
                for(int j=0;j<mixszListsz;j++){
                String mixsz=util.nvl((String)mixszList.get(j));
                currCell = ++cellCt;
                rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
                addMergeCell(currCell, mixsz, styleHdr, rang);
                cellCt = currCell+1;
                }  
                currCell = ++cellCt;
                rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
                addMergeCell(currCell,  "Total", styleHdr, rang);
                cellCt = currCell+1;
                  row = newRow();
                  addCell(++cellCt, "", styleHdr);
                  for(int j=0;j<mixszListsz;j++){
                  String mixsz=util.nvl((String)mixszList.get(j));
                  addCell(++cellCt, "CTS", styleHdr);
                  addCell(++cellCt, "AVG", styleHdr);
                  }  
                  addCell(++cellCt, "CTS", styleHdr);
                  addCell(++cellCt, "AVG", styleHdr);
              for(int k=0;k<mixclrListsz;k++){
              String mixclarity=util.nvl((String)mixclrList.get(k));
              if(clrlist.contains(mixclarity)){
              row = newRow();
              addCell(++cellCt, mixclarity, styleHdr);
              for(int l=0;l<mixszListsz;l++){
              String mixsize=util.nvl((String)mixszList.get(l));
              addCell(++cellCt, util.nvl((String)dataDtl.get(key+"_"+mixsize+"_"+mixclarity+"_CTS")), styleHdr);
              addCell(++cellCt, util.nvl((String)dataDtl.get(key+"_"+mixsize+"_"+mixclarity+"_AVG")), styleHdr);
              }
              addCell(++cellCt, util.nvl((String)dataDtl.get(key+"_"+mixclarity+"_CTS")), styleHdr);
              addCell(++cellCt, util.nvl((String)dataDtl.get(key+"_"+mixclarity+"_AVG")), styleHdr);
              }
              }
              row = newRow();
              addCell(++cellCt, "Total", styleHdr);
              for(int j=0;j<mixszListsz;j++){
              String mixsize=util.nvl((String)mixszList.get(j));
              addCell(++cellCt, util.nvl((String)dataDtl.get(key+"_"+mixsize+"_CTS")), styleHdr);
              addCell(++cellCt, util.nvl((String)dataDtl.get(key+"_"+mixsize+"_AVG")), styleHdr);
              }
              addCell(++cellCt, util.nvl((String)dataDtl.get(key+"_CTS")), styleHdr);
              addCell(++cellCt, util.nvl((String)dataDtl.get(key+"_AVG")), styleHdr);
              }   
        Iterator it= autoColums.iterator();
        while(it.hasNext()) {
        String value=(String)it.next();
        int colId = Integer.parseInt(value);
        sheet.autoSizeColumn((short)colId, true);

        }


        return hwb;

        }
    public HSSFWorkbook getMixDataGridInXl(HttpServletRequest req,String colSpan,String qty,String cts,String avg,String rap,String fapri,String mfgpri,String report) {
    hwb = new HSSFWorkbook();
    autoColums = new TreeSet();
    session = req.getSession(false);
    String fontNm = "Geneva";
    fontHdr = hwb.createFont();
    fontHdr.setFontHeightInPoints((short)11);
    fontHdr.setFontName(fontNm);
    fontHdr.setColor(HSSFColor.BLACK.index);
    fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

    styleHdr = hwb.createCellStyle();
    styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleHdr.setBorderBottom((short)1);
    styleHdr.setBorderTop((short)1);
    styleHdr.setBorderLeft((short)1);
    styleHdr.setBorderRight((short)1);
    styleHdr.setFont(fontHdr);


    fontData = hwb.createFont();
    fontData.setFontHeightInPoints((short)10);
    fontData.setFontName(fontNm);

    styleData = hwb.createCellStyle();
    styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleData.setFont(fontData);
    ArrayList Lst= new ArrayList();
    ArrayList hdrLst= new ArrayList();
    ArrayList contentList= new ArrayList();
    HashMap dataTbl= new HashMap();
    HashMap reportDtl= (HashMap)session.getAttribute("reportDtl");
    if(reportDtl!=null && reportDtl.size()>0){
    contentList=(ArrayList)reportDtl.get(report+"_CONTENT");
    hdrLst=(ArrayList)reportDtl.get(report+"_HDR");
    dataTbl=(HashMap)reportDtl.get(report+"_DATA");
    Lst=(ArrayList)reportDtl.get("Lst");
    }
    String TITLE=util.nvl((String)dataTbl.get("TITLE"));
    HashMap data=new HashMap();
    String Qty="";
    String Cts="";
    String Avg="";
    String Dis="";
    String Fapri="";
    String Mfgpri="";
    int span=Integer.parseInt(colSpan);
    sheet = addSheet();
    row = newRow();
    for(int n=0;n<Lst.size();n++){
    String shLst=(String)Lst.get(n);
    int currCell = ++cellCt;
    CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    addCell(currCell++, "Mix -"+shLst, styleHdr);
    row = newRow();
    currCell = ++cellCt;
    // CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    addCell(currCell++, TITLE, styleHdr);

    for(int i=0;i<hdrLst.size();i++)
    {
    currCell = ++cellCt;
    // rang = mergeCell(rowCt, rowCt, currCell, currCell+1);
    rang = mergeCell(rowCt, rowCt, currCell, currCell+span);
    addMergeCell(currCell, (String)hdrLst.get(i), styleHdr, rang);
    cellCt = currCell+span;
    }

    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell+span);
    addMergeCell(currCell++, "Total", styleHdr, rang);

    cellCt = currCell+1;
    row = newRow();
    addCell(++cellCt, "", styleHdr);
    for(int i=0;i<=hdrLst.size();i++)
    {
    if(qty.equals("Y"))
    addCell(++cellCt, "QTY", styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, "CTS", styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, "AVG", styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, "RAP", styleHdr);
        if(fapri.equals("Y"))
        addCell(++cellCt, "FAPRI", styleHdr);
        if(mfgpri.equals("Y"))
        addCell(++cellCt, "MFGPRI", styleHdr);
    }
    // sheet.createFreezePane(0, rowCt+1);

    for(int j=0;j<contentList.size();j++){
    String contnt=(String)contentList.get(j);
    String dsc=util.nvl2((String)dataTbl.get(contnt),contnt);
    row = newRow();
    addCell(++cellCt,dsc, styleHdr);
    for(int k=0;k<hdrLst.size();k++){
    String hdr=(String)hdrLst.get(k);
    data=new HashMap();
    Qty="";
    Cts="";
    Avg="";
    Dis="";
    Fapri="";
    Mfgpri="";
    data=(HashMap)dataTbl.get(shLst+"_"+hdr+"_"+contnt);
    if(data!=null && data.size()>0){
    Qty=util.nvl((String)data.get("QTY"));
    Cts=util.nvl((String)data.get("CTS"));
    Avg=util.nvl((String)data.get("AVG"));
    Dis=util.nvl((String)data.get("RAP"));
    Fapri=util.nvl((String)data.get("FAPRI"));
    Mfgpri=util.nvl((String)data.get("MFGPRI"));
    }
    if(qty.equals("Y"))
    addCell(++cellCt, Qty, styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, Cts, styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, Avg, styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, Dis, styleHdr);
        if(fapri.equals("Y"))
        addCell(++cellCt, Fapri, styleHdr);
        if(mfgpri.equals("Y"))
        addCell(++cellCt, Mfgpri, styleHdr);
    }
    Qty="";
    Cts="";
    Avg="";
    Dis="";
    Fapri="";
    Mfgpri="";
    data=new HashMap();
    data=(HashMap)dataTbl.get(shLst+"_"+contnt+"_TTL");
    if(data!=null && data.size()>0){
    Qty=util.nvl((String)data.get("QTY"));
    Cts=util.nvl((String)data.get("CTS"));
    Avg=util.nvl((String)data.get("AVG"));
    Dis=util.nvl((String)data.get("RAP"));
    Fapri=util.nvl((String)data.get("FAPRI"));
    Mfgpri=util.nvl((String)data.get("MFGPRI"));
    }
    if(qty.equals("Y"))
    addCell(++cellCt, Qty, styleHdr);  
    if(cts.equals("Y"))
    addCell(++cellCt, Cts, styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, Avg, styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, Dis, styleHdr);
        if(fapri.equals("Y"))
        addCell(++cellCt, Fapri, styleHdr);
        if(mfgpri.equals("Y"))
        addCell(++cellCt, Mfgpri, styleHdr);  
    }
    row = newRow();
    addCell(++cellCt, "Total", styleHdr);
    for(int i=0;i<hdrLst.size();i++){
    String lab=(String)hdrLst.get(i);
    Qty="";
    Cts="";
    Avg="";
    Dis="";
    Fapri="";
    Mfgpri="";
    data=new HashMap();
    data=(HashMap)dataTbl.get(shLst+"_"+lab+"_TTL");
    if(data!=null && data.size()>0){
    Qty=util.nvl((String)data.get("QTY"));
    Cts=util.nvl((String)data.get("CTS"));
    Avg=util.nvl((String)data.get("AVG"));
    Dis=util.nvl((String)data.get("RAP"));
    Fapri=util.nvl((String)data.get("FAPRI"));
    Mfgpri=util.nvl((String)data.get("MFGPRI"));
    }
    if(qty.equals("Y"))
    addCell(++cellCt, Qty, styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, Cts, styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, Avg, styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, Dis, styleHdr);
        if(fapri.equals("Y"))
        addCell(++cellCt, Fapri, styleHdr);
        if(mfgpri.equals("Y"))
        addCell(++cellCt, Mfgpri, styleHdr);
    }
    Qty="";
    Cts="";
    Avg="";
    Dis="";
    Fapri="";
    Mfgpri="";
    data=new HashMap();
    data=(HashMap)dataTbl.get(shLst+"_"+"TTL");
    if(data!=null && data.size()>0){
    Qty=util.nvl((String)data.get("QTY"));
    Cts=util.nvl((String)data.get("CTS"));
    Avg=util.nvl((String)data.get("AVG"));
    Dis=util.nvl((String)data.get("RAP"));
    Fapri=util.nvl((String)data.get("FAPRI"));
    Mfgpri=util.nvl((String)data.get("MFGPRI"));
    }
    if(qty.equals("Y"))
    addCell(++cellCt, Qty, styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, Cts, styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, Avg, styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, Dis, styleHdr);
        if(fapri.equals("Y"))
        addCell(++cellCt, Fapri, styleHdr);
        if(mfgpri.equals("Y"))
        addCell(++cellCt, Mfgpri, styleHdr);
    row = newRow();
    row = newRow();
    }
    Iterator it=autoColums.iterator();
    while(it.hasNext()) {
    String value=(String)it.next();
    int colId = Integer.parseInt(value);
    sheet.autoSizeColumn((short)colId, true);
    }



    return hwb;
    }
    
    public HSSFWorkbook getDatagradingInXl(HttpServletRequest req) {
        hwb = new HSSFWorkbook();
        autoColums = new TreeSet();
        session = req.getSession(false);
        String fontNm = "Geneva";
        fontHdr = hwb.createFont();
        fontHdr.setFontHeightInPoints((short)11);
        fontHdr.setFontName(fontNm);
        fontHdr.setColor(HSSFColor.BLACK.index);
        fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

        styleHdr = hwb.createCellStyle();
        styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleHdr.setBorderBottom((short)1);
        styleHdr.setBorderTop((short)1);
        styleHdr.setBorderLeft((short)1);
        styleHdr.setBorderRight((short)1);
        styleHdr.setFont(fontHdr);


        fontData = hwb.createFont();
        fontData.setFontHeightInPoints((short)9);
        fontData.setFontName(fontNm);

        styleData = hwb.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleData.setBorderBottom((short)1);
        styleData.setBorderTop((short)1);
        styleData.setBorderLeft((short)1);
        styleData.setBorderRight((short)1);
        styleData.setFont(fontData);
        ArrayList stockList = (ArrayList)session.getAttribute("stockList");
              ArrayList asViewPrp = (ArrayList)session.getAttribute("mfgViewLst");
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("GRIDING_DTATYP");        

            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
            ArrayList rptTypList = new ArrayList();
            if(pageDtl==null)
            pageDtl = new HashMap();
            pageList=(ArrayList)pageDtl.get("DTA_TYP");
            
            if(pageList!=null && pageList.size() >0){
             for(int j=0;j<pageList.size();j++){
                  pageDtlMap = (HashMap)pageList.get(j) ;
                   dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
                String[] split_attrNme = dflt_val.split(",");
                for(int i=0;i< split_attrNme.length;i++) {
                        rptTypList.add(split_attrNme[i]);
                    }
                  
            }
            } else{  
             
              rptTypList.add("MFG");
              rptTypList.add("FA");
              rptTypList.add("LAB");
            }
        sheet = addSheet();
        row = newRow();
        row = newRow();
        int rowCt=0;
        int cellCt=0;
            if(stockList!=null && stockList.size()>0){
            row = sheet.createRow((short)++rowCt);
            cell = row.createCell(cellCt);
            cell.setCellValue("Memo Id");
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt+1, cellCt, cellCt));
            cellCt++;
            cell = row.createCell(cellCt);
            cell.setCellValue("Carat");
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt+1, cellCt,cellCt));
            cellCt++;
            cell = row.createCell(cellCt);
            cell.setCellValue("Shape");
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt+1, cellCt, cellCt));
            cellCt++;
            cell = row.createCell(cellCt);
                cell.setCellValue("PKTDATE");
                cell.setCellStyle(styleData);
                sheet.addMergedRegion(merge(rowCt, rowCt+1, cellCt, cellCt));
            cellCt++;
                if(rptTypList.contains("MFG")) {
            cell = row.createCell(cellCt);
            cell.setCellValue("SURAT GRADING");
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, cellCt, (cellCt+(asViewPrp.size()-5))));
            cellCt=cellCt+(asViewPrp.size()-4);
                }
                if(rptTypList.contains("FA")) {
            cell = row.createCell(cellCt);
            cell.setCellValue("MUMBAI GRADING");
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, cellCt, (cellCt+(asViewPrp.size()-5))));
            cellCt=cellCt+(asViewPrp.size()-4);
                }
                if(rptTypList.contains("LAB")) {
            cell = row.createCell(cellCt);
            cell.setCellValue("GIA/IGI GRADING");
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, cellCt, (cellCt+(asViewPrp.size()-5))));
            cellCt=cellCt+(asViewPrp.size()-4);
                }
            cell = row.createCell(cellCt);
            cell.setCellValue("");
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, cellCt, cellCt++));
                cell = row.createCell(cellCt);
                cell.setCellValue("");
                cell.setCellStyle(styleData);
                sheet.addMergedRegion(merge(rowCt, rowCt, cellCt, cellCt));

            cellCt=4;
            row = sheet.createRow((short)++rowCt);
            for(int i=0;i<rptTypList.size();i++){
               String lrpt = (String)rptTypList.get(i);
              for(int j=0;j<asViewPrp.size();j++){
              String lprp = (String)asViewPrp.get(j);
               if(!lprp.equals("SHAPE") && !lprp.equals("MEMO") && !lprp.equals("PKTDATE")){
               if((!lrpt.equals("MFG") || !lprp.equals("LAB")) && ((!lrpt.equals("FA") || !lprp.equals("LAB"))) && ((!lrpt.equals("LAB") || !lprp.equals("RTE")))){
                  cell = row.createCell(cellCt);
                  cell.setCellValue(lprp);
                  cell.setCellStyle(styleData);
                  sheet.addMergedRegion(merge(rowCt, rowCt, cellCt, cellCt++));
                  
            }}}}
            cell = row.createCell(cellCt++);
            cell.setCellValue("Status");
            cell.setCellStyle(styleData);
            sheet.addMergedRegion(merge(rowCt, rowCt, cellCt, cellCt));
            
                cell = row.createCell(cellCt);
                cell.setCellValue("Employee");
                cell.setCellStyle(styleData);
                sheet.addMergedRegion(merge(rowCt, rowCt, cellCt, cellCt));
            
            SortedSet autoCols = new TreeSet();
            int colNum=0;
            for(int i=0;i<stockList.size();i++){
               cellCt = -1;
               row = sheet.createRow((short)++rowCt);
               HashMap dataDtl=new HashMap();
               dataDtl=(HashMap)stockList.get(i);
                   cell = row.createCell(++cellCt);
                   cell.setCellValue(util.nvl((String)dataDtl.get("vnm")));
                   cell.setCellStyle(styleData);
                   colNum = cell.getColumnIndex();
                   autoCols.add(Integer.toString(colNum));
                
                   cell = row.createCell(++cellCt);
                   cell.setCellValue(util.nvl((String)dataDtl.get("cts")));
                   cell.setCellStyle(styleData);
                   colNum = cell.getColumnIndex();
                   autoCols.add(Integer.toString(colNum));

                   cell = row.createCell(++cellCt);
                   cell.setCellValue(util.nvl((String)dataDtl.get("shape")));
                   cell.setCellStyle(styleData);
                   colNum = cell.getColumnIndex();
                   autoCols.add(Integer.toString(colNum));
                
                   cell = row.createCell(++cellCt);
                   cell.setCellValue(util.nvl((String)dataDtl.get("pktdate")));
                   cell.setCellStyle(styleData);
                   colNum = cell.getColumnIndex();
                   autoCols.add(Integer.toString(colNum));

                for(int x=0;x<rptTypList.size();x++){
                String lrpt = (String)rptTypList.get(x);
               for(int y=0;y<asViewPrp.size();y++){
               String lprp = (String)asViewPrp.get(y);
                   if(!lprp.equals("SHAPE") && !lprp.equals("MEMO") && !lprp.equals("PKTDATE")){
                      if((!lrpt.equals("MFG") || !lprp.equals("LAB")) && ((!lrpt.equals("FA") || !lprp.equals("LAB"))) && ((!lrpt.equals("LAB") || !lprp.equals("RTE")))){
                   if(lprp.indexOf("&A") > -1)
                   lprp=lprp.replaceAll("\\&A","A");
                   if(lprp.indexOf("-S") > -1)
                       lprp=lprp.replaceAll("\\-S","S");
               String val = util.nvl((String)dataDtl.get(lrpt+"_"+lprp));
                   cell = row.createCell(++cellCt);
                                  cell.setCellValue(val);
                                  cell.setCellStyle(styleData);
                                  colNum = cell.getColumnIndex();
                                  autoCols.add(Integer.toString(colNum));
               }}}}
                   cell = row.createCell(++cellCt);
                                  cell.setCellValue(util.nvl((String)dataDtl.get("status")));
                                  cell.setCellStyle(styleData);
                                  colNum = cell.getColumnIndex();
                                  autoCols.add(Integer.toString(colNum));
                   cell = row.createCell(++cellCt);
                                  cell.setCellValue(util.nvl((String)dataDtl.get("emp")));
                                  cell.setCellStyle(styleData);
                                  colNum = cell.getColumnIndex();
                                  autoCols.add(Integer.toString(colNum));
               }
            
            
            }
        return hwb;
        }
    
    public HSSFWorkbook getGenXlRepetCust(HttpServletRequest req,ArrayList byrLst,String count) {
        
        hwb = new HSSFWorkbook();
        autoColums = new TreeSet();
        String fontNm = "Cambria";        
    sheet = addSheet();
    HSSFFont fontData = hwb.createFont();
    fontData.setFontHeightInPoints((short)(short)10);
    fontData.setFontName(fontNm);

    HSSFFont fontDataNormal = hwb.createFont();
    fontDataNormal.setFontHeightInPoints((short)10);
    fontDataNormal.setFontName(fontNm);

    HSSFFont fontDataRed = hwb.createFont();
    fontDataRed.setFontHeightInPoints((short)10);
    fontDataRed.setFontName(fontNm);
    fontDataRed.setColor(HSSFColor.RED.index);

    HSSFFont fontDataGreen = hwb.createFont();
    fontDataGreen.setFontHeightInPoints((short)10);
    fontDataGreen.setFontName(fontNm);
    fontDataGreen.setColor(HSSFColor.GREEN.index);

    HSSFCellStyle styleData = hwb.createCellStyle();
    styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleData.setFont(fontData);
    styleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
    styleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
    styleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
    styleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
    styleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);


    HSSFCellStyle styleDataNormal = hwb.createCellStyle();
    styleDataNormal.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataNormal.setFont(fontDataNormal);
    styleDataNormal.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
    styleDataNormal.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
    styleDataNormal.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
    styleDataNormal.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
    styleDataNormal.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleDataNormal.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleDataNormal.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleDataNormal.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);

    HSSFCellStyle styleDataRed = hwb.createCellStyle();
    styleDataRed.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataRed.setFont(fontDataRed);
    styleDataRed.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
    styleDataRed.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
    styleDataRed.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
    styleDataRed.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
    styleDataRed.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleDataRed.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleDataRed.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleDataRed.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);

    HSSFCellStyle styleDataGreen = hwb.createCellStyle();
    styleDataGreen.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataGreen.setFont(fontDataGreen);
    styleDataGreen.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
    styleDataGreen.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
    styleDataGreen.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
    styleDataGreen.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
    styleDataGreen.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleDataGreen.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleDataGreen.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleDataGreen.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);

    SortedSet autoCols = new TreeSet();
    HashMap data=new HashMap();
    if(byrLst!=null && byrLst.size()>0){
    int cnt=0;
    int sr=0;
    int boxcnt=(byrLst.size()/3)+1;
    int follow=byrLst.size()-Integer.parseInt(count);
    int size=byrLst.size();
    int colNum=0;
    int rownum = 0;
        row = sheet.createRow((short)0);
        String ttl="";
        ttl="Total - "+size;
        ttl+=" Repeat Customer :- "+count;
        ttl+=" | "+util.Round((((Double.parseDouble(count))/Double.parseDouble(String.valueOf(size)))*100),2);
        ttl+=" Follow Up :- "+follow;
        ttl+=" | "+util.Round((((Double.parseDouble(String.valueOf(follow)))/Double.parseDouble(String.valueOf(size)))*100),2);
        cell = row.createCell(++cellCt);
        cell.setCellValue(ttl);
        cell.setCellStyle(styleData);
        sheet.addMergedRegion(merge(0, 0, 0, 6));
       
        row = newRow();
        cellCt = -1;
        cell = row.createCell(++cellCt);
        cell.setCellValue("Sr No");
        cell.setCellStyle(styleData);
        colNum = cell.getColumnIndex();
        autoCols.add(Integer.toString(colNum));
        
        cell = row.createCell(++cellCt);
        cell.setCellValue("Party");
        cell.setCellStyle(styleData);
        colNum = cell.getColumnIndex();
        autoCols.add(Integer.toString(colNum));
        
        cell = row.createCell(++cellCt);
        cell.setCellValue("Sr No");
        cell.setCellStyle(styleData);
        colNum = cell.getColumnIndex();
        autoCols.add(Integer.toString(colNum));

        cell = row.createCell(++cellCt);
        cell.setCellValue("Party");
        cell.setCellStyle(styleData);
        colNum = cell.getColumnIndex();
        autoCols.add(Integer.toString(colNum));

        cell = row.createCell(++cellCt);
        cell.setCellValue("Sr No");
        cell.setCellStyle(styleData);
        colNum = cell.getColumnIndex();
        autoCols.add(Integer.toString(colNum));

        cell = row.createCell(++cellCt);
        cell.setCellValue("Party");
        cell.setCellStyle(styleData);
        colNum = cell.getColumnIndex();
        autoCols.add(Integer.toString(colNum));

    for(int i=0;i<boxcnt;i++){
    row = newRow();
    cellCt = -1;
    styleData=styleDataNormal;
    sr=i+1;
    data=new HashMap();
    data=(HashMap)byrLst.get(i);
    String party=util.nvl((String)data.get("BYR"));
    String color=util.nvl((String)data.get("COLOR"));
    if(color.equals("red"))
    styleData=styleDataRed;
    if(color.equals("green"))
    styleData=styleDataGreen;
        cell = row.createCell(++cellCt);
        cell.setCellValue(String.valueOf(sr));
        cell.setCellStyle(styleData);
        colNum = cell.getColumnIndex();
        autoCols.add(Integer.toString(colNum));
        
        cell = row.createCell(++cellCt);
        cell.setCellValue(party);
        cell.setCellStyle(styleData);
        colNum = cell.getColumnIndex();
        autoCols.add(Integer.toString(colNum));
    data=new HashMap();
    sr=sr+boxcnt;
    data=(HashMap)byrLst.get(sr-1);
    party=util.nvl((String)data.get("BYR"));
    color=util.nvl((String)data.get("COLOR"));
    if(color.equals("red"))
    styleData=styleDataRed;
    if(color.equals("green"))
    styleData=styleDataGreen;
        cell = row.createCell(++cellCt);
        cell.setCellValue(String.valueOf(sr));
        cell.setCellStyle(styleData);
        colNum = cell.getColumnIndex();
        autoCols.add(Integer.toString(colNum));

        cell = row.createCell(++cellCt);
        cell.setCellValue(party);
        cell.setCellStyle(styleData);
        colNum = cell.getColumnIndex();
        autoCols.add(Integer.toString(colNum));

    data=new HashMap();
    sr=sr+boxcnt;
    if(sr<=size){
    data=(HashMap)byrLst.get(sr-1);
    party=util.nvl((String)data.get("BYR"));
    color=util.nvl((String)data.get("COLOR"));
    if(color.equals("red"))
    styleData=styleDataRed;
    if(color.equals("green"))
    styleData=styleDataGreen;
        cell = row.createCell(++cellCt);
        cell.setCellValue(String.valueOf(sr));
        cell.setCellStyle(styleData);
        colNum = cell.getColumnIndex();
        autoCols.add(Integer.toString(colNum));

        cell = row.createCell(++cellCt);
        cell.setCellValue(party);
        cell.setCellStyle(styleData);
        colNum = cell.getColumnIndex();
        autoCols.add(Integer.toString(colNum));
    }
    }
    }
    
    return hwb;
    }


    public  HSSFWorkbook WeeklyStockReportXL(HttpServletRequest req,String shapefetch){
       hwb = new HSSFWorkbook();
      autoColums = new TreeSet();
      session = req.getSession(false);
       String fontNm = "Calibri";
       fontHdr = hwb.createFont();
       fontHdr.setFontHeightInPoints((short)11);
       fontHdr.setFontName(fontNm);
        fontHdr.setColor(HSSFColor.BLACK.index);
        fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
        styleHdr = hwb.createCellStyle();
       styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
       styleHdr.setFont(fontHdr);
      styleHdr.setBorderBottom((short)1);
      styleHdr.setBorderTop((short)1);
      styleHdr.setBorderLeft((short)1);
      styleHdr.setBorderRight((short)1);
      styleHdr.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
      styleHdr.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
       styleDataGN =  hwb.createCellStyle();
       styleDataGN.setAlignment(HSSFCellStyle.ALIGN_LEFT);
       styleDataGN.setFont(fontHdr);
       
       fontData = hwb.createFont();
       fontData.setFontHeightInPoints((short)10);
       fontData.setFontName(fontNm);
       styleData = hwb.createCellStyle();
       styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
       styleData.setFont(fontData);
      styleData.setBorderBottom((short)1);
      styleData.setBorderTop((short)1);
      styleData.setBorderLeft((short)1);
      styleData.setBorderRight((short)1);
        sheet = addSheet();
      // Master Grid
      ArrayList shapeList = new ArrayList();
        if(shapefetch.equals("ALL")){
        shapeList.add("ROUND");
        shapeList.add("FANCY");
        }else
        shapeList.add(shapefetch);
         HashMap dayGrpSh = (HashMap)session.getAttribute("DayShMap");
         HashMap ttShMap = (HashMap)session.getAttribute("ttlShMap");
         ArrayList shList = (ArrayList)session.getAttribute("shList");
      ArrayList dayGrpList = (ArrayList)session.getAttribute("dayGrpList");
      int ttlPcs = (Integer)session.getAttribute("ttlPcs");
         if(dayGrpSh!=null && dayGrpSh.size()>0){
           row = newRow();
           addCell(++cellCt,"Day",styleHdr);
           for(int i=0 ; i < shList.size() ; i++){
             String sh = util.nvl((String)shList.get(i));
             addCell(++cellCt,sh,styleHdr);
           }
           addCell(++cellCt,"Pcs",styleHdr);
           addCell(++cellCt,"%",styleHdr);
           for(int j=0 ; j < dayGrpList.size() ; j++){
             row = newRow();
             String day = util.nvl((String)dayGrpList.get(j));
             addCell(++cellCt,day,styleData);
          
             int totalCntDy = 0;
              for(int i=0 ; i < shList.size() ; i++){
             String sh = util.nvl((String)shList.get(i));
             String cnt  = util.nvl((String)dayGrpSh.get(day+"_"+sh));
             if(!cnt.equals(""))
             totalCntDy = totalCntDy + Integer.parseInt(cnt);
              addCell(++cellCt,cnt,styleData,"");
             }
             addCell(++cellCt,String.valueOf(totalCntDy),styleHdr,"");
             double ttlDyPer = (Double.parseDouble(String.valueOf(totalCntDy))/Double.parseDouble(String.valueOf(ttlPcs)))*100 ;
             addCell(++cellCt,String.valueOf(Math.round(ttlDyPer)),styleHdr,"");
               
           }
           row = newRow();
             addCell(++cellCt,"",styleHdr);
           for(int i=0 ; i < shList.size() ; i++){
             String sh = util.nvl((String)shList.get(i));
             String cnt = util.nvl((String)ttShMap.get(sh));
               addCell(++cellCt,cnt,styleHdr,"");
            }
             addCell(++cellCt,String.valueOf(ttlPcs),styleHdr,"");
             addCell(++cellCt,"100%",styleHdr);
           }
           
        //FANCY SHAPEWISE GRID
         rowCt=rowCt+2;
          HashMap fncyShMap= (HashMap)session.getAttribute("FncyShMap"); 
          ArrayList fncyShList =(ArrayList)session.getAttribute("FncyShList");
           if(fncyShList!=null && fncyShList.size() > 0){
        
           row = newRow();
           addCell(++cellCt,"Fancy Shape",styleHdr);
           addCell(++cellCt,"Amt",styleHdr);
           addCell(++cellCt,"D.Damt",styleHdr);
           addCell(++cellCt,"%",styleHdr);
           addCell(++cellCt,"Days",styleHdr);
          
           for(int i=0 ; i< fncyShList.size();i++){
         String lprpVal = (String)fncyShList.get(i);
           row = newRow();
           addCell(++cellCt,lprpVal,styleData);
           addCell(++cellCt,util.nvl((String)fncyShMap.get("FANCY_"+lprpVal+"_VAL")),styleData , "");
           addCell(++cellCt,util.nvl((String)fncyShMap.get("FANCY_"+lprpVal+"_DYSVAL")),styleData , "");
           addCell(++cellCt,util.nvl((String)fncyShMap.get("FANCY_"+lprpVal+"_PCT")),styleData , "");
           addCell(++cellCt,util.nvl((String)fncyShMap.get("FANCY_"+lprpVal+"_DYS")),styleData , "");
           }
           row = newRow();
            addCell(++cellCt,"",styleHdr);
           addCell(++cellCt,util.nvl((String)fncyShMap.get("FANCY_TTLVAL")),styleHdr, "");
           addCell(++cellCt,util.nvl((String)fncyShMap.get("FANCY_TTLDAYVAL")),styleHdr, "");
           addCell(++cellCt,"100%",styleHdr);
           addCell(++cellCt,util.nvl((String)fncyShMap.get("FANCY_TTLDYS")),styleHdr, "");
           }
           
           //<!--LAB Grid-->
           HashMap  labWsDataMap = (HashMap)session.getAttribute("LabWsDataMap");
          ArrayList daysLabList = (ArrayList)session.getAttribute("DaysLabList");
      rowCt=rowCt+2;
      row = newRow();
      addCell(++cellCt,"ROUND",styleDataGN);
      cellCt=cellCt+7;
      addCell(++cellCt,"FANCY",styleDataGN);
      row = newRow();
      addCell(++cellCt,"Days",styleHdr);
      addCell(++cellCt,"GIA",styleHdr);
      addCell(++cellCt,"IGI",styleHdr);
      addCell(++cellCt,"Pcs",styleHdr);
      addCell(++cellCt,"Amt",styleHdr);
      addCell(++cellCt,"%",styleHdr);
        cellCt=cellCt+2;
      addCell(++cellCt,"Days",styleHdr);
      addCell(++cellCt,"GIA",styleHdr);
      addCell(++cellCt,"IGI",styleHdr);
      addCell(++cellCt,"Pcs",styleHdr);
      addCell(++cellCt,"Amt",styleHdr);
      addCell(++cellCt,"%",styleHdr);
      int ttlValFn=0;
      int ttlValRd=0;
      if(daysLabList!=null && daysLabList.size()>0){
          for(int i=0 ; i < daysLabList.size() ;i++){
         String day = (String)daysLabList.get(i);
          row = newRow();
        for(int k=0;k<shapeList.size();k++){
         String shape = (String)shapeList.get(k);
            if(shape.equals("FANCY"))
                ttlValFn=ttlValFn+Integer.parseInt(util.nvl((String)labWsDataMap.get(day+"_"+shape+"_VAL"),"0"));
            else
              ttlValRd=ttlValRd+Integer.parseInt(util.nvl((String)labWsDataMap.get(day+"_"+shape+"_VAL"),"0")); 
          addCell(++cellCt,day,styleData);
           addCell(++cellCt,util.nvl((String)labWsDataMap.get(day+"_"+shape+"_GIA_CNT")),styleData , "");
           addCell(++cellCt,util.nvl((String)labWsDataMap.get(day+"_"+shape+"_IGI_CNT")),styleData , "");
          addCell(++cellCt,util.nvl((String)labWsDataMap.get(day+"_"+shape+"_TTLCNT")),styleData , "");
          addCell(++cellCt,util.nvl((String)labWsDataMap.get(day+"_"+shape+"_VAL")),styleData , "");
          addCell(++cellCt,util.nvl((String)labWsDataMap.get(day+"_"+shape+"_PCT")),styleData , "");
            cellCt=cellCt+2;
        }}
        row = newRow();
        for(int k=0;k<shapeList.size();k++){
         String shape = (String)shapeList.get(k);
          int giaCnt = Integer.parseInt(util.nvl((String)labWsDataMap.get(shape+"_GIA_CNT"),"0"));
          int igicnt = Integer.parseInt(util.nvl((String)labWsDataMap.get(shape+"_IGI_CNT"),"0"));
          int labCnt = giaCnt + igicnt;
            int ttlVlu = ttlValRd;
          if(shape.equals("FANCY"))
            ttlVlu = ttlValFn;
          addCell(++cellCt,"",styleHdr);
          addCell(++cellCt,util.nvl((String)labWsDataMap.get(shape+"_GIA_CNT")),styleHdr, "");
          addCell(++cellCt,util.nvl((String)labWsDataMap.get(shape+"_GIA_CNT")),styleHdr, "");
          addCell(++cellCt,util.nvl(String.valueOf(labCnt)),styleHdr, "");
          addCell(++cellCt,util.nvl(String.valueOf(ttlVlu)),styleHdr, "");
           addCell(++cellCt,"100%",styleHdr);
            cellCt=cellCt+2;
         
        }
        
      }
      
      ArrayList prpList = new ArrayList();
        prpList.add("SIZE");
        prpList.add("COL");
        prpList.add("CLR");
      HashMap mprp = info.getMprp();
      HashMap  PrpWiseGd = (HashMap)session.getAttribute("PrpWiseGd");
      HashMap prpShValMap = (HashMap)session.getAttribute("PrpShMap");
      for(int i=0;i<prpList.size();i++){
          String lprp = (String)prpList.get(i);
        rowCt=rowCt+2;
        row = newRow();
        addCell(++cellCt,"ROUND",styleDataGN);
        cellCt=cellCt+7;
        addCell(++cellCt,"FANCY",styleDataGN);
        row = newRow();
        addCell(++cellCt,(String)mprp.get(lprp+"D"),styleDataGN);
        sheet.addMergedRegion(merge(rowCt, rowCt, 0, 5));
        cellCt=cellCt+7;
       addCell(++cellCt,(String)mprp.get(lprp+"D"),styleDataGN);
        sheet.addMergedRegion(merge(rowCt, rowCt,8, 13));
          
        row = newRow();
        addCell(++cellCt,"",styleHdr);
        addCell(++cellCt,"Pcs",styleHdr);
        addCell(++cellCt,"Amt",styleHdr);
        addCell(++cellCt,"D.Damt",styleHdr);
        addCell(++cellCt,"%",styleHdr);
        addCell(++cellCt,"Days",styleHdr);
            cellCt=cellCt+2;
        addCell(++cellCt,"",styleHdr);
        addCell(++cellCt,"Pcs",styleHdr);
        addCell(++cellCt,"Amt",styleHdr);
        addCell(++cellCt,"D.Damt",styleHdr);
        addCell(++cellCt,"%",styleHdr);
        addCell(++cellCt,"Days",styleHdr);
        ArrayList lprpValRDLst =((ArrayList)prpShValMap.get("ROUND_"+lprp) == null)?new ArrayList():(ArrayList)prpShValMap.get("ROUND_"+lprp); 
        ArrayList lprpValFNLst =((ArrayList)prpShValMap.get("FANCY_"+lprp) == null)?new ArrayList():(ArrayList)prpShValMap.get("FANCY_"+lprp);  
        int rdLprpLnt = lprpValRDLst.size();
        int fnLprpLnt = lprpValFNLst.size();
        int maxLnt = Math.max(rdLprpLnt, fnLprpLnt);
        for(int j=0;j<maxLnt;j++){
              row = newRow();
              for(int k=0;k<shapeList.size();k++){
               String shape = (String)shapeList.get(k);
                int listLnt = rdLprpLnt;
                  ArrayList lprpLst = lprpValRDLst;
                  if(shape.equals("FANCY")){
                    listLnt = fnLprpLnt;
                   lprpLst = lprpValFNLst;
                  }
         
            if(j < listLnt){
          String lprpVal = (String)lprpLst.get(j); 
             
              addCell(++cellCt,lprpVal,styleData);
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprpVal+"_CNT")),styleData , "");
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprpVal+"_VAL")),styleData , "");
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprpVal+"_DYSVAL")),styleData , "");
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprpVal+"_PCT")),styleData , "");
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprpVal+"_DYS")),styleData , "");
                cellCt=cellCt+2;
            }else{
              for(int a=0;a<8;a++){
                addCell(++cellCt,"",styleData);
                  if(a==5){
                      cellCt=cellCt+2;
                      a=a+2;
                  }
              }
                
            }
            }}
            row = newRow();
            for(int x=0;x<shapeList.size();x++){
             String shape = (String)shapeList.get(x);
            addCell(++cellCt,"",styleHdr);
            addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprp+"_TTLCNT")),styleHdr, ""); 
            addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprp+"_TTLVAL")),styleHdr, "");
            addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprp+"_TTLDAYVAL")),styleHdr, "");
            addCell(++cellCt,"100%",styleHdr);
            addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprp+"_TTLDYS")),styleHdr, ""); 
                cellCt=cellCt+2;
            }
           
          }
           
      rowCt=rowCt+2;
    
      HashMap allStockMap= (HashMap)session.getAttribute("AllStockMap"); 
         ArrayList allStkPrpList =(ArrayList)session.getAttribute("AllStkPrpList");
         if(allStkPrpList!=null && allStkPrpList.size() > 0){
          row = newRow();
          addCell(++cellCt,"ROUND",styleDataGN);
          cellCt=cellCt+6;
          addCell(++cellCt,"FANCY",styleDataGN);
          row = newRow();
          addCell(++cellCt,"All Stock (Live)",styleDataGN);
          sheet.addMergedRegion(merge(rowCt, rowCt, 0, 4));
          cellCt=cellCt+6;
          addCell(++cellCt,"All Stock (Live)",styleDataGN);
          sheet.addMergedRegion(merge(rowCt, rowCt,7, 11));
            
        row = newRow();
      addCell(++cellCt,"",styleHdr);
      addCell(++cellCt,"Amt",styleHdr);
      addCell(++cellCt,"D.DAmt",styleHdr);
      addCell(++cellCt,"%",styleHdr);
      addCell(++cellCt,"Days",styleHdr);
          cellCt=cellCt+2;
      addCell(++cellCt,"",styleHdr);
      addCell(++cellCt,"Amt",styleHdr);
      addCell(++cellCt,"D.DAmt",styleHdr);
      addCell(++cellCt,"%",styleHdr);
      addCell(++cellCt,"Days",styleHdr);
      
      
      for(int i=0 ; i < allStkPrpList.size() ;i++){
      String lprp= (String)allStkPrpList.get(i);
          
      row = newRow();
      for(int k=0;k<shapeList.size();k++){
      String shape = (String)shapeList.get(k);
        String   lprpVal = lprp.replace("SH", shape);
        addCell(++cellCt,lprpVal,styleData);
        addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_"+lprpVal+"_VAL")),styleData , "");
        addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_"+lprpVal+"_DYSVAL")),styleData , "");
        addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_"+lprpVal+"_PCT")),styleData , ""); 
        addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_"+lprpVal+"_DYS")),styleData , "");
          cellCt=cellCt+2;
       
      }}
     
          row = newRow();
      for(int k=0;k<shapeList.size();k++){
      String shape = (String)shapeList.get(k);
      
      addCell(++cellCt,"",styleHdr);
      addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_TTLVAL")),styleHdr, "");
      addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_TTLDAYVAL")),styleHdr, "");
      addCell(++cellCt,"100%",styleHdr);
      addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_TTLDYS")),styleHdr, "");
          cellCt=cellCt+2;
      }
      
      }
      rowCt=rowCt+2;
      HashMap dys_grpMap= (HashMap)session.getAttribute("Dys_grpMap"); 
        ArrayList dys_grpList =(ArrayList)session.getAttribute("Dys_grpList");
        if(dys_grpList!=null && dys_grpList.size() > 0){
          row = newRow();
          addCell(++cellCt,"ROUND",styleDataGN);
          cellCt=cellCt+4;
          addCell(++cellCt,"FANCY",styleDataGN);
          row = newRow();
          addCell(++cellCt,"Price Diff",styleDataGN);
          sheet.addMergedRegion(merge(rowCt, rowCt, 0, 2));
          cellCt=cellCt+4;
          addCell(++cellCt,"Price Diff",styleDataGN);
          sheet.addMergedRegion(merge(rowCt, rowCt,5, 7));
            
        row = newRow();
      addCell(++cellCt,"Days",styleHdr);
      addCell(++cellCt,"%",styleHdr);
      addCell(++cellCt,"FANCY",styleHdr);
          cellCt=cellCt+2;
      addCell(++cellCt,"Days",styleHdr);
      addCell(++cellCt,"%",styleHdr);
      addCell(++cellCt,"FANCY",styleHdr);
      
      
      for(int i=0 ; i < dys_grpList.size() ;i++){
      String lprpVal= (String)dys_grpList.get(i);
      row = newRow();
      for(int k=0;k<shapeList.size();k++){
      String shape = (String)shapeList.get(k);
          
        addCell(++cellCt,lprpVal,styleData);
        addCell(++cellCt,util.nvl((String)dys_grpMap.get(""+shape+"_"+lprpVal)),styleData , "");
        addCell(++cellCt,"",styleData);
          cellCt=cellCt+2;
       
      }}
      row = newRow();
      for(int k=0;k<shapeList.size();k++){
      String shape = (String)shapeList.get(k);
      
      addCell(++cellCt,"",styleHdr);
      addCell(++cellCt,util.nvl((String)dys_grpMap.get(""+shape+"_AVGPCT")),styleHdr, "");
      addCell(++cellCt,"0",styleHdr, "");
          cellCt=cellCt+2;
      }
      
      }
      return hwb;
    }
    
  public  HSSFWorkbook WeeklySaleReportXL(HttpServletRequest req,String shapefetch){
     hwb = new HSSFWorkbook();
    autoColums = new TreeSet();
    session = req.getSession(false);
     String fontNm = "Calibri";
     fontHdr = hwb.createFont();
     fontHdr.setFontHeightInPoints((short)11);
     fontHdr.setFontName(fontNm);
      fontHdr.setColor(HSSFColor.BLACK.index);
      fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
      styleHdr = hwb.createCellStyle();
     styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
     styleHdr.setFont(fontHdr);
    styleHdr.setBorderBottom((short)1);
    styleHdr.setBorderTop((short)1);
    styleHdr.setBorderLeft((short)1);
    styleHdr.setBorderRight((short)1);
    styleHdr.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
    styleHdr.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      
     styleDataGN =  hwb.createCellStyle();
     styleDataGN.setAlignment(HSSFCellStyle.ALIGN_LEFT);
     styleDataGN.setFont(fontHdr);
     
     fontData = hwb.createFont();
     fontData.setFontHeightInPoints((short)10);
     fontData.setFontName(fontNm);
     styleData = hwb.createCellStyle();
     styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
     styleData.setFont(fontData);
    styleData.setBorderBottom((short)1);
    styleData.setBorderTop((short)1);
    styleData.setBorderLeft((short)1);
    styleData.setBorderRight((short)1);
      sheet = addSheet();
    // Master Grid
    ArrayList shapeList = new ArrayList();
      if(shapefetch.equals("ALL")){
      shapeList.add("ROUND");
      shapeList.add("FANCY");
      }else
      shapeList.add(shapefetch);
     
         
         //<!--LAB Grid-->
         HashMap  labWsDataMap = (HashMap)session.getAttribute("LabWsDataMap");
        ArrayList daysLabList = (ArrayList)session.getAttribute("DaysLabList");
    rowCt=rowCt+2;
    row = newRow();
    addCell(++cellCt,"ROUND",styleDataGN);
    cellCt=cellCt+8;
    addCell(++cellCt,"FANCY",styleDataGN);
    row = newRow();
    addCell(++cellCt,"Days",styleHdr);
    addCell(++cellCt,"GIA",styleHdr);
    addCell(++cellCt,"IGI",styleHdr);
    addCell(++cellCt,"Pcs",styleHdr);
    addCell(++cellCt,"Amt",styleHdr);
    addCell(++cellCt,"%",styleHdr);
    addCell(++cellCt,"D-Rev",styleHdr);
      cellCt=cellCt+2;
    addCell(++cellCt,"Days",styleHdr);
    addCell(++cellCt,"GIA",styleHdr);
    addCell(++cellCt,"IGI",styleHdr);
    addCell(++cellCt,"Pcs",styleHdr);
    addCell(++cellCt,"Amt",styleHdr);
    addCell(++cellCt,"%",styleHdr);
    addCell(++cellCt,"D-Rev",styleHdr);
    int ttlValFn=0;
    int ttlValRd=0;
    if(daysLabList!=null && daysLabList.size()>0){
        for(int i=0 ; i < daysLabList.size() ;i++){
       String day = (String)daysLabList.get(i);
        row = newRow();
      for(int k=0;k<shapeList.size();k++){
       String shape = (String)shapeList.get(k);
          if(shape.equals("FANCY"))
              ttlValFn=ttlValFn+Integer.parseInt(util.nvl((String)labWsDataMap.get(day+"_"+shape+"_VAL"),"0"));
          else
            ttlValRd=ttlValRd+Integer.parseInt(util.nvl((String)labWsDataMap.get(day+"_"+shape+"_VAL"),"0")); 
        addCell(++cellCt,day,styleData);
         addCell(++cellCt,util.nvl((String)labWsDataMap.get(day+"_"+shape+"_GIA_CNT")),styleData , "");
         addCell(++cellCt,util.nvl((String)labWsDataMap.get(day+"_"+shape+"_IGI_CNT")),styleData , "");
        addCell(++cellCt,util.nvl((String)labWsDataMap.get(day+"_"+shape+"_TTLCNT")),styleData , "");
        addCell(++cellCt,util.nvl((String)labWsDataMap.get(day+"_"+shape+"_VAL")),styleData , "");
        addCell(++cellCt,util.nvl((String)labWsDataMap.get(day+"_"+shape+"_PCT")),styleData , "");
        addCell(++cellCt,util.nvl((String)labWsDataMap.get(day+"_"+shape+"_PRI")),styleData , "");
          cellCt=cellCt+2;
      }}
      row = newRow();
      for(int k=0;k<shapeList.size();k++){
       String shape = (String)shapeList.get(k);
        int giaCnt = Integer.parseInt(util.nvl((String)labWsDataMap.get(shape+"_GIA_CNT"),"0"));
        int igicnt = Integer.parseInt(util.nvl((String)labWsDataMap.get(shape+"_IGI_CNT"),"0"));
        int labCnt = giaCnt + igicnt;
          int ttlVlu = ttlValRd;
        if(shape.equals("FANCY"))
          ttlVlu = ttlValFn;
        addCell(++cellCt,"",styleHdr);
        addCell(++cellCt,util.nvl((String)labWsDataMap.get(shape+"_GIA_CNT")),styleHdr, "");
        addCell(++cellCt,util.nvl((String)labWsDataMap.get(shape+"_IGI_CNT")),styleHdr, "");
        addCell(++cellCt,util.nvl(String.valueOf(labCnt)),styleHdr, "");
        addCell(++cellCt,util.nvl(String.valueOf(ttlVlu)),styleHdr, "");
         addCell(++cellCt,"100%",styleHdr);
          addCell(++cellCt,util.nvl((String)labWsDataMap.get(shape+"_AVG_PRI")),styleHdr);
          cellCt=cellCt+2;
       
      }
      
    }
    
    ArrayList prpList = new ArrayList();
      prpList.add("SIZE");
      prpList.add("COL");
      prpList.add("CLR");
    HashMap mprp = info.getMprp();
    HashMap  PrpWiseGd = (HashMap)session.getAttribute("PrpWiseGd");
    HashMap prpShValMap = (HashMap)session.getAttribute("PrpShMap");
    for(int i=0;i<prpList.size();i++){
        String lprp = (String)prpList.get(i);
      rowCt=rowCt+2;
      row = newRow();
      addCell(++cellCt,"ROUND",styleDataGN);
      cellCt=cellCt+11;
      addCell(++cellCt,"FANCY",styleDataGN);
      row = newRow();
      addCell(++cellCt,"ADD",styleDataGN);
      addCell(++cellCt,(String)mprp.get(lprp+"D"),styleDataGN);
      sheet.addMergedRegion(merge(rowCt, rowCt, 1, 4));
          cellCt=cellCt+3;
     addCell(++cellCt,"W-S",styleDataGN);
     sheet.addMergedRegion(merge(rowCt, rowCt, 5, 6));
          cellCt=cellCt+1;
     addCell(++cellCt,"4-S",styleDataGN);
     sheet.addMergedRegion(merge(rowCt, rowCt, 7, 8));
       addCell(++cellCt,"GIA-S",styleHdr);
      cellCt=cellCt+3;
    addCell(++cellCt,"ADD",styleDataGN);
            addCell(++cellCt,(String)mprp.get(lprp+"D"),styleDataGN);
            sheet.addMergedRegion(merge(rowCt, rowCt, 12, 15));
          cellCt=cellCt+3;
            addCell(++cellCt,"W-S",styleDataGN);
            sheet.addMergedRegion(merge(rowCt, rowCt, 16, 17));
          cellCt=cellCt+1;
            addCell(++cellCt,"4-S",styleDataGN);
            sheet.addMergedRegion(merge(rowCt, rowCt, 18, 19));
          addCell(++cellCt,"GIA-S",styleHdr);
            row = newRow();
            addCell(++cellCt,"",styleHdr);
            addCell(++cellCt,"",styleHdr);
            addCell(++cellCt,"Pcs",styleHdr);
            addCell(++cellCt,"%",styleHdr);
            addCell(++cellCt,"",styleHdr);
            addCell(++cellCt,"%",styleHdr);
            addCell(++cellCt,"",styleHdr);
            addCell(++cellCt,"%",styleHdr);
            addCell(++cellCt,"",styleHdr);
          addCell(++cellCt,"",styleHdr);
            cellCt=cellCt+2;
            addCell(++cellCt,"",styleHdr);
            addCell(++cellCt,"",styleHdr);
            addCell(++cellCt,"Pcs",styleHdr);
            addCell(++cellCt,"%",styleHdr);
            addCell(++cellCt,"",styleHdr);
            addCell(++cellCt,"%",styleHdr);
            addCell(++cellCt,"",styleHdr);
            addCell(++cellCt,"%",styleHdr);
            addCell(++cellCt,"",styleHdr);  
          addCell(++cellCt,"",styleHdr);
      ArrayList lprpValRDLst =((ArrayList)prpShValMap.get("ROUND_"+lprp) == null)?new ArrayList():(ArrayList)prpShValMap.get("ROUND_"+lprp); 
      ArrayList lprpValFNLst =((ArrayList)prpShValMap.get("FANCY_"+lprp) == null)?new ArrayList():(ArrayList)prpShValMap.get("FANCY_"+lprp);  
      int rdLprpLnt = lprpValRDLst.size();
      int fnLprpLnt = lprpValFNLst.size();
      int maxLnt = Math.max(rdLprpLnt, fnLprpLnt);
      for(int j=0;j<maxLnt;j++){
            row = newRow();
            for(int k=0;k<shapeList.size();k++){
             String shape = (String)shapeList.get(k);
              int listLnt = rdLprpLnt;
                ArrayList lprpLst = lprpValRDLst;
                if(shape.equals("FANCY")){
                  listLnt = fnLprpLnt;
                 lprpLst = lprpValFNLst;
                }
       
          if(j < listLnt){
        String lprpVal = (String)lprpLst.get(j); 
           
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_Stock_"+lprpVal+"_ADD_PCT")),styleData);             
              addCell(++cellCt,lprpVal,styleData);
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_Stock_"+lprpVal+"_CNT")),styleData , "");
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_Stock_"+lprpVal+"_PCT")),styleData , "");
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_Stock_"+lprpVal+"_DYS")),styleData , "");
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_P1_"+lprpVal+"_PCT")),styleData , "");
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_P1_"+lprpVal+"_DYS")),styleData , "");
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_P2_"+lprpVal+"_PCT")),styleData , "");
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_P2_"+lprpVal+"_DYS")),styleData , "");
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprpVal+"_ASRTPCT")),styleData , "");
              cellCt=cellCt+2;
          }else{
            for(int a=0;a<13;a++){
              addCell(++cellCt,"",styleData);
                if(a==9){
                    cellCt=cellCt+2;
                    a=a+2;
                }
            }
              
          }
          }}
          row = newRow();
          for(int x=0;x<shapeList.size();x++){
           String shape = (String)shapeList.get(x);
              addCell(++cellCt,"100%",styleHdr);
              addCell(++cellCt,"Total",styleHdr);
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_Stock_"+lprp+"_TTLPCS")),styleHdr, ""); 
            
              addCell(++cellCt,"100%",styleHdr);
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_Stock_"+lprp+"_TTLAVGDAY")),styleHdr, "");
              addCell(++cellCt,"100%",styleHdr);
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_P1_"+lprp+"_TTLAVGDAY")),styleHdr, "");
              addCell(++cellCt,"100%",styleHdr);
              addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_P2_"+lprp+"_TTLAVGDAY")),styleHdr, ""); 
            addCell(++cellCt,"100%",styleHdr); 
                    cellCt=cellCt+2;
          }
         
        }
         
    rowCt=rowCt+2;
  
    HashMap allStockMap= (HashMap)session.getAttribute("AllStockMap"); 
       ArrayList allStkPrpList =(ArrayList)session.getAttribute("AllStkPrpList");
       if(allStkPrpList!=null && allStkPrpList.size() > 0){
      row = newRow();
      addCell(++cellCt,"ROUND",styleDataGN);
      cellCt=cellCt+10;
      addCell(++cellCt,"FANCY",styleDataGN);
      row = newRow();
      addCell(++cellCt,"ADD",styleDataGN);
      addCell(++cellCt,"All Stock (Live)",styleDataGN);
      sheet.addMergedRegion(merge(rowCt, rowCt, 1, 4));
      cellCt=cellCt+3;
     addCell(++cellCt,"W-S",styleDataGN);
     sheet.addMergedRegion(merge(rowCt, rowCt, 5, 6));
      cellCt=cellCt+1;
     addCell(++cellCt,"4-S",styleDataGN);
     sheet.addMergedRegion(merge(rowCt, rowCt, 7, 8));
      cellCt=cellCt+3;
            addCell(++cellCt,"ADD",styleDataGN);
            addCell(++cellCt,"All Stock (Live)",styleDataGN);
            sheet.addMergedRegion(merge(rowCt, rowCt, 12, 15));
      cellCt=cellCt+3;
            addCell(++cellCt,"W-S",styleDataGN);
            sheet.addMergedRegion(merge(rowCt, rowCt, 16, 17));
      cellCt=cellCt+1;
            addCell(++cellCt,"4-S",styleDataGN);
            sheet.addMergedRegion(merge(rowCt, rowCt, 18, 19));  
        row = newRow();
        addCell(++cellCt,"",styleHdr);
        addCell(++cellCt,"",styleHdr);
        addCell(++cellCt,"Pcs",styleHdr);
        addCell(++cellCt,"%",styleHdr);
        addCell(++cellCt,"",styleHdr);
        addCell(++cellCt,"%",styleHdr);
        addCell(++cellCt,"",styleHdr);
        addCell(++cellCt,"%",styleHdr);
        addCell(++cellCt,"",styleHdr);
        cellCt=cellCt+2;
        addCell(++cellCt,"",styleHdr);
        addCell(++cellCt,"",styleHdr);
        addCell(++cellCt,"Pcs",styleHdr);
        addCell(++cellCt,"%",styleHdr);
        addCell(++cellCt,"",styleHdr);
        addCell(++cellCt,"%",styleHdr);
        addCell(++cellCt,"",styleHdr);
        addCell(++cellCt,"%",styleHdr);
        addCell(++cellCt,"",styleHdr);  
    
    
    for(int i=0 ; i < allStkPrpList.size() ;i++){
    String lprp= (String)allStkPrpList.get(i);
        
    row = newRow();
    for(int k=0;k<shapeList.size();k++){
    String shape = (String)shapeList.get(k);
      String   lprpVal = lprp.replace("SH", shape);
        addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_Stock_"+lprpVal+"_ADD_PCT")),styleData);   
        addCell(++cellCt,lprpVal,styleData);
        addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_Stock_"+lprpVal+"_CNT")),styleData , "");
        addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_Stock_"+lprpVal+"_PCT")),styleData , "");
        addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_Stock_"+lprpVal+"_DYS")),styleData , ""); 
        addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_P1_"+lprpVal+"_PCT")),styleData , "");
        addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_P1_"+lprpVal+"_DYS")),styleData , ""); 
        addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_P2_"+lprpVal+"_PCT")),styleData , "");
        addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_P2_"+lprpVal+"_DYS")),styleData , ""); 
        cellCt=cellCt+2;
     
    }}
   
        row = newRow();
    for(int k=0;k<shapeList.size();k++){
    String shape = (String)shapeList.get(k);
    
        addCell(++cellCt,"100%",styleHdr);
        addCell(++cellCt,"TOTAL",styleHdr); 
        addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_Stock_TTLPCS")),styleHdr);
        addCell(++cellCt,"100%",styleHdr);
        addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_Stock_TTLAVGDAY")),styleHdr);
        addCell(++cellCt,"100%",styleHdr);
        addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_P1_TTLAVGDAY")),styleHdr);
        addCell(++cellCt,"100%",styleHdr);
        addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_P2_TTLAVGDAY")),styleHdr);
        
        cellCt=cellCt+2;
    }
    
    }
   
    return hwb;
  }
  
  public  HSSFWorkbook WeeklyReportXL(HttpServletRequest req,String shapefetch){
    hwb = new HSSFWorkbook();
    autoColums = new TreeSet();
    session = req.getSession(false);
    String fontNm = "Calibri";
    fontHdr = hwb.createFont();
    fontHdr.setFontHeightInPoints((short)11);
    fontHdr.setFontName(fontNm);
     fontHdr.setColor(HSSFColor.BLACK.index);
     fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
     styleHdr = hwb.createCellStyle();
    styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleHdr.setFont(fontHdr);
    styleHdr.setBorderBottom((short)1);
    styleHdr.setBorderTop((short)1);
    styleHdr.setBorderLeft((short)1);
    styleHdr.setBorderRight((short)1);
    styleHdr.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
    styleHdr.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
     
    styleDataGN =  hwb.createCellStyle();
    styleDataGN.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    styleDataGN.setFont(fontHdr);
    
    fontData = hwb.createFont();
    fontData.setFontHeightInPoints((short)10);
    fontData.setFontName(fontNm);
    styleData = hwb.createCellStyle();
    styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleData.setFont(fontData);
    styleData.setBorderBottom((short)1);
    styleData.setBorderTop((short)1);
    styleData.setBorderLeft((short)1);
    styleData.setBorderRight((short)1);
     sheet = addSheet();
    // Master Grid
    ArrayList shapeList = new ArrayList();
     if(shapefetch.equals("ALL")){
     shapeList.add("ROUND");
     shapeList.add("FANCY");
     }else
     shapeList.add(shapefetch);
    //<!-- FAncy grid-->
    row = newRow();
    addCell(++cellCt,"DepartMent :",styleDataGN);
    addCell(++cellCt,util.nvl((String)req.getParameter("dept")),styleDataGN);
    row = newRow();
    addCell(++cellCt,"Weekly Sale (P1) :",styleDataGN);
    addCell(++cellCt,util.nvl((String)req.getParameter("salep1")),styleDataGN);
    row = newRow();
    addCell(++cellCt,"4 Week Sale (P2) :",styleDataGN);
    addCell(++cellCt,util.nvl((String)req.getParameter("salep2")),styleDataGN);
    row = newRow();
    addCell(++cellCt,"Transfer Date :",styleDataGN);
    addCell(++cellCt,util.nvl((String)req.getParameter("trnDte")),styleDataGN);
     
    HashMap shapeDateMap= (HashMap)session.getAttribute("ShapeDateMap"); 
       ArrayList fancyShapeList =(ArrayList)session.getAttribute("ShapeList");
       if(fancyShapeList!=null && fancyShapeList.size() > 0){
    rowCt=rowCt+2;
      row = newRow();
     
      addCell(++cellCt,"FANCY",styleDataGN);
      row = newRow();
      addCell(++cellCt,"ADD",styleHdr);
      addCell(++cellCt,"SAHPE",styleHdr);
      sheet.addMergedRegion(merge(rowCt, rowCt, 1, 4));
       cellCt=cellCt+3;
      addCell(++cellCt,"W-S",styleHdr);
      sheet.addMergedRegion(merge(rowCt, rowCt, 5, 6));
      cellCt=cellCt+1;
      addCell(++cellCt,"4-S",styleHdr);
      
      
    
     
    
    for(int i=0 ; i< fancyShapeList.size();i++){
     String lprpVal = util.nvl((String)fancyShapeList.get(i));
    
    row = newRow();
   
     
       addCell(++cellCt,util.nvl((String)shapeDateMap.get("FANCY_"+lprpVal+"_ADDPCT")),styleData);   
       addCell(++cellCt,lprpVal,styleData);
       addCell(++cellCt,util.nvl((String)shapeDateMap.get("FANCY_"+lprpVal+"_Z_VAL")),styleData , "");
       addCell(++cellCt,util.nvl((String)shapeDateMap.get("FANCY_"+lprpVal+"_Z_PCT")),styleData );
       addCell(++cellCt,util.nvl((String)shapeDateMap.get("FANCY_"+lprpVal+"_Z_DYS")),styleData , ""); 
       addCell(++cellCt,util.nvl((String)shapeDateMap.get("FANCY_"+lprpVal+"_W_PCT")),styleData );
       addCell(++cellCt,util.nvl((String)shapeDateMap.get("FANCY_"+lprpVal+"_W_DYS")),styleData , ""); 
       addCell(++cellCt,util.nvl((String)shapeDateMap.get("FANCY_"+lprpVal+"_S_PCT")),styleData );
        cellCt=cellCt+2;
    
    }
    
       row = newRow();
    addCell(++cellCt,"100%",styleHdr);
       addCell(++cellCt,"TOTAL",styleHdr); 
       addCell(++cellCt,util.nvl((String)shapeDateMap.get("FANCY_Z_TTLVAL")),styleHdr);
       addCell(++cellCt,"100%",styleHdr);
       addCell(++cellCt,util.nvl((String)shapeDateMap.get("FANCY_Z_AVGDYS")),styleHdr);
       addCell(++cellCt,"100%",styleHdr);
       addCell(++cellCt,util.nvl((String)shapeDateMap.get("FANCY_W_AVGDYS")),styleHdr);
       addCell(++cellCt,"100%",styleHdr);
        
       cellCt=cellCt+2;
    
       }
        //<!--LAB Grid-->
        HashMap  labWsDataMap = (HashMap)session.getAttribute("LabWsDataMap");
       ArrayList daysLabList = (ArrayList)session.getAttribute("DaysLabList");
    rowCt=rowCt+2;
    row = newRow();
    addCell(++cellCt,"ROUND",styleDataGN);
    cellCt=cellCt+7;
    addCell(++cellCt,"FANCY",styleDataGN);
    row = newRow();
    addCell(++cellCt,"Days",styleHdr);
    addCell(++cellCt,"GIA",styleHdr);
    addCell(++cellCt,"IGI",styleHdr);
    addCell(++cellCt,"Pcs",styleHdr);
    addCell(++cellCt,"%",styleHdr);
    addCell(++cellCt,"D-Rev",styleHdr);
     cellCt=cellCt+2;
    addCell(++cellCt,"Days",styleHdr);
    addCell(++cellCt,"GIA",styleHdr);
    addCell(++cellCt,"IGI",styleHdr);
    addCell(++cellCt,"Pcs",styleHdr);
    addCell(++cellCt,"%",styleHdr);
    addCell(++cellCt,"D-Rev",styleHdr);
    int ttlValFn=0;
    int ttlValRd=0;
    if(daysLabList!=null && daysLabList.size()>0){
       for(int i=0 ; i < daysLabList.size() ;i++){
      String day = (String)daysLabList.get(i);
       row = newRow();
     for(int k=0;k<shapeList.size();k++){
      String shape = (String)shapeList.get(k);
        addCell(++cellCt,day,styleData);
        addCell(++cellCt,util.nvl((String)labWsDataMap.get(day+"_"+shape+"_GIA_LABCNT")),styleData , "");
        addCell(++cellCt,util.nvl((String)labWsDataMap.get(day+"_"+shape+"_IGI_LABCNT")),styleData , "");
       addCell(++cellCt,util.nvl((String)labWsDataMap.get(day+"_"+shape+"_QTY")),styleData , "");
      addCell(++cellCt,util.nvl((String)labWsDataMap.get(day+"_"+shape+"_PCT")),styleData );
       addCell(++cellCt,util.nvl((String)labWsDataMap.get(day+"_"+shape+"_REVPCT")),styleData , "");
         cellCt=cellCt+2;
     }}
     row = newRow();
     for(int k=0;k<shapeList.size();k++){
      String shape = (String)shapeList.get(k);
      
       addCell(++cellCt,"Total",styleHdr);
       addCell(++cellCt,util.nvl((String)labWsDataMap.get(shape+"_GIA_TTLCNT")),styleHdr, "");
       addCell(++cellCt,util.nvl((String)labWsDataMap.get(shape+"_IGI_TTLCNT")),styleHdr, "");
       addCell(++cellCt,util.nvl((String)labWsDataMap.get(shape+"_TTLCNT")),styleHdr, "");
       addCell(++cellCt,"100%",styleHdr);
       addCell(++cellCt,util.nvl((String)labWsDataMap.get(shape+"_TTLREVPCT")),styleHdr);
         cellCt=cellCt+2;
      
     }
     
    }
    
    ArrayList prpList = new ArrayList();
     prpList.add("SIZE");
     prpList.add("COL");
     prpList.add("CLR");
    HashMap mprp = info.getMprp();
    HashMap  PrpWiseGd = (HashMap)session.getAttribute("PrpWiseGd");
    HashMap prpShValMap = (HashMap)session.getAttribute("PrpShMap");
    for(int i=0;i<prpList.size();i++){
       String lprp = (String)prpList.get(i);
     rowCt=rowCt+2;
     row = newRow();
     addCell(++cellCt,"ROUND",styleDataGN);
     cellCt=cellCt+9;
     addCell(++cellCt,"FANCY",styleDataGN);
     row = newRow();
     addCell(++cellCt,"ADD",styleHdr);
     addCell(++cellCt,(String)mprp.get(lprp+"D"),styleHdr);
     sheet.addMergedRegion(merge(rowCt, rowCt, 1, 4));
      cellCt=cellCt+3;
    addCell(++cellCt,"W-S",styleHdr);
    sheet.addMergedRegion(merge(rowCt, rowCt, 5, 6));
    cellCt=cellCt+1;
    addCell(++cellCt,"4-S",styleHdr);
  
     
     cellCt=cellCt+2;
    addCell(++cellCt,"ADD",styleHdr);
    addCell(++cellCt,(String)mprp.get(lprp+"D"),styleHdr);
    sheet.addMergedRegion(merge(rowCt, rowCt, 11, 14));
    cellCt=cellCt+3;
    addCell(++cellCt,"W-S",styleHdr);
    sheet.addMergedRegion(merge(rowCt, rowCt, 15, 16));
     cellCt=cellCt+1;
     addCell(++cellCt,"4-S",styleHdr);
        
       
     ArrayList lprpValRDLst =((ArrayList)prpShValMap.get("ROUND_"+lprp) == null)?new ArrayList():(ArrayList)prpShValMap.get("ROUND_"+lprp); 
     ArrayList lprpValFNLst =((ArrayList)prpShValMap.get("FANCY_"+lprp) == null)?new ArrayList():(ArrayList)prpShValMap.get("FANCY_"+lprp);  
     int rdLprpLnt = lprpValRDLst.size();
     int fnLprpLnt = lprpValFNLst.size();
     int maxLnt = Math.max(rdLprpLnt, fnLprpLnt);
     for(int j=0;j<maxLnt;j++){
           row = newRow();
           for(int k=0;k<shapeList.size();k++){
            String shape = (String)shapeList.get(k);
             int listLnt = rdLprpLnt;
               ArrayList lprpLst = lprpValRDLst;
               if(shape.equals("FANCY")){
                 listLnt = fnLprpLnt;
                lprpLst = lprpValFNLst;
               }
      
         if(j < listLnt){
       String lprpVal = (String)lprpLst.get(j); 
          
             addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprpVal+"_ADD_PCT")),styleData);             
             addCell(++cellCt,lprpVal,styleData);
             addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_Z_"+lprpVal+"_QTY")),styleData , "");
             addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_Z_"+lprpVal+"_PCT")),styleData);
             addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_Z_"+lprpVal+"_DYS")),styleData , "");
             addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_W_"+lprpVal+"_PCT")),styleData);
             addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_W_"+lprpVal+"_DYS")),styleData , "");
             addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_S_"+lprpVal+"_PCT")),styleData );
               cellCt=cellCt+2;
         }else{
           for(int a=0;a<10;a++){
             addCell(++cellCt,"",styleData);
               if(a==7){
                   cellCt=cellCt+2;
                   a=a+2;
               }
           }
             
         }
         }}
         row = newRow();
         for(int x=0;x<shapeList.size();x++){
          String shape = (String)shapeList.get(x);
             addCell(++cellCt,"100%",styleHdr);
             addCell(++cellCt,"Total",styleHdr);
             addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_Z_"+lprp+"_TTLQTY")),styleHdr, ""); 
           
             addCell(++cellCt,"100%",styleHdr);
             addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_Z_"+lprp+"_AVGDYS")),styleHdr, "");
             addCell(++cellCt,"100%",styleHdr);
             addCell(++cellCt,util.nvl((String)PrpWiseGd.get(""+shape+"_W_"+lprp+"_AVGDYS")),styleHdr, "");
             addCell(++cellCt,"100%",styleHdr);
                   cellCt=cellCt+2;
         }
        
       }
        
    rowCt=rowCt+2;
    
    HashMap allStockMap= (HashMap)session.getAttribute("AllStockMap");
      ArrayList allStkPrpList =(ArrayList)session.getAttribute("AllStkPrpList");
      if(allStkPrpList!=null && allStkPrpList.size() > 0){
      row = newRow();
      addCell(++cellCt,"ROUND",styleDataGN);
      cellCt=cellCt+9;
      addCell(++cellCt,"FANCY",styleDataGN);
      row = newRow();
      addCell(++cellCt,"ADD",styleHdr);
      addCell(++cellCt,"All Stock (Live)",styleHdr);
      sheet.addMergedRegion(merge(rowCt, rowCt, 1, 4));
       cellCt=cellCt+3;
      addCell(++cellCt,"W-S",styleHdr);
      sheet.addMergedRegion(merge(rowCt, rowCt, 5, 6));
      cellCt=cellCt+1;
      addCell(++cellCt,"4-S",styleHdr);
      
      
      cellCt=cellCt+2;
      addCell(++cellCt,"ADD",styleHdr);
      addCell(++cellCt,"All Stock (Live)",styleHdr);
      sheet.addMergedRegion(merge(rowCt, rowCt, 11, 14));
      cellCt=cellCt+3;
      addCell(++cellCt,"W-S",styleHdr);
      sheet.addMergedRegion(merge(rowCt, rowCt, 15, 16));
      cellCt=cellCt+1;
      addCell(++cellCt,"4-S",styleHdr);
         
     
    
    for(int i=0 ; i < allStkPrpList.size() ;i++){
    String lprp= (String)allStkPrpList.get(i);
       
    row = newRow();
    for(int k=0;k<shapeList.size();k++){
    String shape = (String)shapeList.get(k);
     String   lprpVal = lprp;
      String lprpdsc="";
        if(lprpVal.indexOf("F-COL")!=-1){
        lprpdsc = lprpVal.replace("SH", "");
        lprpVal = lprpVal.replace("SH", shape);
       } else{
        lprpVal = lprpVal.replace("SH", shape);
         lprpdsc = lprpVal;
        }
       addCell(++cellCt,util.nvl((String)allStockMap.get(lprpVal+"_ADDPCT")),styleData);   
       addCell(++cellCt,lprpdsc,styleData);
       addCell(++cellCt,util.nvl((String)allStockMap.get(lprpVal+"_Z_VAL")),styleData , "");
       addCell(++cellCt,util.nvl((String)allStockMap.get(lprpVal+"_Z_PCT")),styleData );
       addCell(++cellCt,util.nvl((String)allStockMap.get(lprpVal+"_Z_DYS")),styleData , ""); 
       addCell(++cellCt,util.nvl((String)allStockMap.get(lprpVal+"_W_PCT")),styleData );
       addCell(++cellCt,util.nvl((String)allStockMap.get(lprpVal+"_W_DYS")),styleData , ""); 
       addCell(++cellCt,util.nvl((String)allStockMap.get(lprpVal+"_S_PCT")),styleData );
        cellCt=cellCt+2;
    
    }}
    
       row = newRow();
    for(int k=0;k<shapeList.size();k++){
    String shape = (String)shapeList.get(k);
    
       addCell(++cellCt,"100%",styleHdr);
       addCell(++cellCt,"TOTAL",styleHdr); 
       addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_Z_TTLVAL")),styleHdr);
       addCell(++cellCt,"100%",styleHdr);
       addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_Z_AVGDYS")),styleHdr);
       addCell(++cellCt,"100%",styleHdr);
       addCell(++cellCt,util.nvl((String)allStockMap.get(""+shape+"_W_AVGDYS")),styleHdr);
       addCell(++cellCt,"100%",styleHdr);
        
       cellCt=cellCt+2;
    }
    
    }
    
    return hwb;
  }
    public HSSFWorkbook getDataAkhaniXl(HttpServletRequest req ,ArrayList memoList) {
       hwb = new HSSFWorkbook();
       autoColums = new TreeSet();
       session = req.getSession(false);
       String fontNm = "Geneva";
       fontHdr = hwb.createFont();
       fontHdr.setFontHeightInPoints((short)8);
       fontHdr.setFontName(fontNm);
       fontHdr.setColor(HSSFColor.BLACK.index);
       fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

       styleHdr = hwb.createCellStyle();
       styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
       styleHdr.setBorderBottom((short)1);
       styleHdr.setBorderTop((short)1);
       styleHdr.setBorderLeft((short)1);
       styleHdr.setBorderRight((short)1);
       styleHdr.setFont(fontHdr);


       fontData = hwb.createFont();
       fontData.setFontHeightInPoints((short)8);
       fontData.setFontName(fontNm);

       styleData = hwb.createCellStyle();
       styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
       styleData.setBorderBottom((short)1);
       styleData.setBorderTop((short)1);
       styleData.setBorderLeft((short)1);
       styleData.setBorderRight((short)1);
       styleData.setFont(fontData);
       
         HSSFFont fontDataNormal = hwb.createFont();
         fontDataNormal.setFontHeightInPoints((short)8);
         fontDataNormal.setFontName(fontNm);

         HSSFFont fontDataRed = hwb.createFont();
         fontDataRed.setFontHeightInPoints((short)8);
         fontDataRed.setFontName(fontNm);
         fontDataRed.setColor(HSSFColor.WHITE.index);

         HSSFFont fontDataGreen = hwb.createFont();
         fontDataGreen.setFontHeightInPoints((short)8);
         fontDataGreen.setFontName(fontNm);
         fontDataGreen.setColor(HSSFColor.WHITE.index);
         
         HSSFCellStyle styleDataNormal = hwb.createCellStyle();
         styleDataNormal.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         styleDataNormal.setFont(fontDataNormal);
         styleDataNormal.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
         styleDataNormal.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
         styleDataNormal.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
         styleDataNormal.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
         styleDataNormal.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
         styleDataNormal.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
         styleDataNormal.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
         styleDataNormal.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);

         HSSFCellStyle styleDataRed = hwb.createCellStyle();
         styleDataRed.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         styleDataRed.setFillForegroundColor(HSSFColor.RED.index);
         styleDataRed.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
         styleDataRed.setFont(fontDataGreen);
         styleDataRed.setBorderBottom((short)1);
         styleDataRed.setBorderTop((short)1);
         styleDataRed.setBorderLeft((short)1);
         styleDataRed.setBorderRight((short)1);

          HSSFCellStyle styleDataGreen = hwb.createCellStyle();        
          styleDataGreen.setAlignment(HSSFCellStyle.ALIGN_CENTER);
          styleDataGreen.setFillForegroundColor(HSSFColor.GREEN.index);
          styleDataGreen.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
          styleDataGreen.setFont(fontDataGreen);
          styleDataGreen.setBorderBottom((short)1);
          styleDataGreen.setBorderTop((short)1);
          styleDataGreen.setBorderLeft((short)1);
          styleDataGreen.setBorderRight((short)1);
      
         HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
    
       if(dataDtl!=null && dataDtl.size()>0){
       double getttlcts=0,getaakhaold=0,getaakhasrt=0,getmanojnew=0,getmanojold=0,getaakhanewvlu=0,getaakhaoldvlu=0,getmnjnewvlu=0,getmnjoldvlu=0;
       double vlujama=0,vlunew=0,vlunewavg=0;
       String percentgia="",percentnew="",percentper="",diffaakhanew="";
       double aakhanew=0;
      String diffmnj="0",olddiff="0",srtdiff="0",gdavgdiff="0",diffmnjcolor="",olddiffcolor="",srtdiffcolor="",gdavgdiffcolor="";

       sheet = addSheet();
       row = newRow();
       row = newRow();
       int currCell = ++cellCt;
       CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Memo", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Carat", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "96-UP-GIA CTS", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Aakha ni K.OLD", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Aakha ni GD SRT", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Aakha New", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "OLD", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Old Avg SRT", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "New Avg", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Manoj Old", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Manoj New", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, " ", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Manoj Old", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Manoj New", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "Diff Manoj", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "K.Old Diff %", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "GD SRT Diff%", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "GD AVG DIFF", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "%", styleHdr);
       cellCt = currCell;
       currCell = ++cellCt;
       rang = mergeCell(rowCt, rowCt, currCell, currCell);
       addCell(currCell, "96 up GIA", styleHdr);
       cellCt = currCell;
    for(int i=0;i<memoList.size();i++){
    String memoVal=util.nvl((String)memoList.get(i));
    String ttlcts=util.nvl2((String)dataDtl.get(memoVal+"_CTS"),"0").trim();
    String deptcts=util.nvl2((String)dataDtl.get(memoVal+"_96-UP-GIA_CTS"),"0").trim();
    String aakhaold=util.nvl2((String)dataDtl.get(memoVal+"_AAKHAOLD"),"0");
    String aakhasrt=util.nvl2((String)dataDtl.get(memoVal+"_AAKHASRT"),"0");
    String aakhaoldvlu=util.nvl2((String)dataDtl.get(memoVal+"_AAKHAOLDVLU"),"0");
    String aakhasrtvlu=util.nvl2((String)dataDtl.get(memoVal+"_AAKHASRTVLU"),"0");
    String mnjold=util.nvl2((String)dataDtl.get(memoVal+"_96-UP-GIA_MNJOLD"),"0");
    String mnjnew=util.nvl2((String)dataDtl.get(memoVal+"_96-UP-GIA_MNJNEW"),"0");
    String mnjoldvlu=util.nvl2((String)dataDtl.get(memoVal+"_96-UP-GIA_MNJOLDVLU"),"0");
    String mnjnewvlu=util.nvl2((String)dataDtl.get(memoVal+"_96-UP-GIA_MNJNEWVLU"),"0");
    percentper=util.nvl2((String)dataDtl.get(memoVal+"_BKPER"),"0");
    aakhanew=0;
    diffmnj="0";olddiff="0";srtdiff="0";gdavgdiff="0";diffmnjcolor="";olddiffcolor="";srtdiffcolor="";gdavgdiffcolor="";
    getmnjnewvlu=Double.parseDouble(mnjnewvlu);
    getmnjoldvlu=Double.parseDouble(mnjoldvlu);
    getttlcts=Double.parseDouble(ttlcts);
    getaakhaold=Double.parseDouble(aakhaold);
    getaakhasrt=Double.parseDouble(aakhasrt);
    getmanojnew=Double.parseDouble(mnjnew);
    getmanojold=Double.parseDouble(mnjold);
    getaakhaoldvlu=Double.parseDouble(aakhaoldvlu);
    if(!mnjnew.equals("0") && !mnjold.equals("0")){
    diffmnj=String.valueOf(util.roundToDecimals(Math.round((((getmanojnew-getmanojold)*100)/getmanojold)),1));
    }
    if(!mnjoldvlu.equals("0") && !mnjnewvlu.equals("0")){
    diffaakhanew=String.valueOf(Math.round(util.roundToDecimals(((getmnjnewvlu-getmnjoldvlu)/getttlcts),1)));
    aakhanew=Double.parseDouble(aakhaold)+Double.parseDouble(diffaakhanew);
    vlunewavg=util.roundToDecimals(aakhanew*getttlcts,1);
    }
    if(!aakhaold.equals("0")){
    olddiff=String.valueOf(Math.round(util.roundToDecimals((((aakhanew-getaakhaold)*100)/getaakhaold),1)));
    srtdiff=String.valueOf(Math.round(util.roundToDecimals((((aakhanew-getaakhasrt)*100)/getaakhasrt),1)));
    gdavgdiff=String.valueOf(Math.round(util.roundToDecimals((((getaakhasrt-getaakhaold)*100)/getaakhaold),1)));
    percentgia=String.valueOf(Math.round(util.roundToDecimals(((getmnjoldvlu/getaakhaoldvlu)*100),1)));
    }

    if(Double.parseDouble(diffmnj)>=3)
    diffmnjcolor="green";

    if(Double.parseDouble(diffmnj)<=-3)
    diffmnjcolor="red";

    if(Double.parseDouble(olddiff)>=3)
    olddiffcolor="green";

    if(Double.parseDouble(olddiff)<=-3)
    olddiffcolor="red";

    if(Double.parseDouble(srtdiff)>=3)
    srtdiffcolor="green";

    if(Double.parseDouble(srtdiff)<=-3)
    srtdiffcolor="red";

    if(Double.parseDouble(gdavgdiff)>=3)
    gdavgdiffcolor="green";

    if(Integer.parseInt(gdavgdiff)<=-3)
    gdavgdiffcolor="red";

    if(!diffmnj.equals("") && !diffmnj.equals("0"))
    diffmnj=diffmnj+"%";

    if(!percentper.equals("") && !percentper.equals("0"))
    percentper=percentper+"%";

    if(!percentgia.equals("") && !percentgia.equals("0"))
    percentgia=percentgia+"%";

    if(!olddiff.equals("") && !olddiff.equals("0"))
    olddiff=olddiff+"%";

    if(!srtdiff.equals("") && !srtdiff.equals("0"))
    srtdiff=srtdiff+"%";

    if(!gdavgdiff.equals("") && !gdavgdiff.equals("0"))
    gdavgdiff=gdavgdiff+"%";

       row = newRow();
       addCell(++cellCt,memoVal, styleData);
       addCell(++cellCt,ttlcts, styleData,"");
       addCell(++cellCt, deptcts, styleData,"");
       addCell(++cellCt, aakhaold, styleData,"");
       addCell(++cellCt, aakhasrt, styleData,"");
       addCell(++cellCt, String.valueOf(aakhanew), styleData,"");
       addCell(++cellCt, aakhaoldvlu, styleData,"");
       addCell(++cellCt, aakhasrtvlu, styleData,"");
       addCell(++cellCt,String.valueOf(vlunewavg), styleData,"");
       addCell(++cellCt,mnjold, styleData,"");
       addCell(++cellCt, mnjnew, styleData,"");
       addCell(++cellCt, diffaakhanew, styleData,"");
       addCell(++cellCt, mnjoldvlu, styleData,"");
       addCell(++cellCt, mnjnewvlu, styleData,"");
       if(diffmnjcolor.equals("red"))
       styleData=styleDataRed;
       if(diffmnjcolor.equals("green"))
       styleData=styleDataGreen;
       addCell(++cellCt, diffmnj, styleData);
       styleData=styleDataNormal;
       if(olddiffcolor.equals("red"))
       styleData=styleDataRed;
       if(olddiffcolor.equals("green"))
       styleData=styleDataGreen;
       addCell(++cellCt, olddiff, styleData);
       styleData=styleDataNormal;
       if(srtdiffcolor.equals("red"))
       styleData=styleDataRed;
       if(srtdiffcolor.equals("green"))
       styleData=styleDataGreen;
       addCell(++cellCt, srtdiff, styleData);
       styleData=styleDataNormal; 
       if(gdavgdiffcolor.equals("red"))
       styleData=styleDataRed;
       if(gdavgdiffcolor.equals("green"))
       styleData=styleDataGreen;
       addCell(++cellCt, gdavgdiff, styleData);
       styleData=styleDataNormal;
       addCell(++cellCt, percentper, styleData);
       addCell(++cellCt, percentgia, styleData);
}  
    String ttlcts=util.nvl2((String)dataDtl.get("TOTAL_CTS"),"0").trim();
    String deptcts=util.nvl2((String)dataDtl.get("TOTAL_96-UP-GIA_CTS"),"0").trim();
    String aakhaold=util.nvl2((String)dataDtl.get("TOTAL_AAKHAOLD"),"0");
    String aakhasrt=util.nvl2((String)dataDtl.get("TOTAL_AAKHASRT"),"0");
    String aakhaoldvlu=util.nvl2((String)dataDtl.get("TOTAL_AAKHAOLDVLU"),"0");
    String aakhasrtvlu=util.nvl2((String)dataDtl.get("TOTAL_AAKHASRTVLU"),"0");
    String mnjold=util.nvl2((String)dataDtl.get("TOTAL_96-UP-GIA_MNJOLD"),"0");
    String mnjnew=util.nvl2((String)dataDtl.get("TOTAL_96-UP-GIA_MNJNEW"),"0");
    String mnjoldvlu=util.nvl2((String)dataDtl.get("TOTAL_96-UP-GIA_MNJOLDVLU"),"0");
    String mnjnewvlu=util.nvl2((String)dataDtl.get("TOTAL_96-UP-GIA_MNJNEWVLU"),"0");
    percentper=util.nvl2((String)dataDtl.get("TOTAL_BKPER"),"0");
    aakhanew=0;
    diffmnj="0";olddiff="0";srtdiff="0";gdavgdiff="0";diffmnjcolor="";olddiffcolor="";srtdiffcolor="";gdavgdiffcolor="";
    getmnjnewvlu=Double.parseDouble(mnjnewvlu);
    getmnjoldvlu=Double.parseDouble(mnjoldvlu);
    getttlcts=Double.parseDouble(ttlcts);
    getaakhaold=Double.parseDouble(aakhaold);
    getaakhasrt=Double.parseDouble(aakhasrt);
    getmanojnew=Double.parseDouble(mnjnew);
    getmanojold=Double.parseDouble(mnjold);
    getaakhaoldvlu=Double.parseDouble(aakhaoldvlu);
    if(!mnjnew.equals("0") && !mnjold.equals("0")){
    diffmnj=String.valueOf(util.roundToDecimals(Math.round((((getmanojnew-getmanojold)*100)/getmanojold)),1));
    }
    if(!mnjoldvlu.equals("0") && !mnjnewvlu.equals("0")){
    diffaakhanew=String.valueOf(Math.round(util.roundToDecimals(((getmnjnewvlu-getmnjoldvlu)/getttlcts),1)));
    aakhanew=Double.parseDouble(aakhaold)+Double.parseDouble(diffaakhanew);
    vlunewavg=util.roundToDecimals(aakhanew*getttlcts,1);
    }
    if(!aakhaold.equals("0")){
    olddiff=String.valueOf(Math.round(util.roundToDecimals((((aakhanew-getaakhaold)*100)/getaakhaold),1)));
    srtdiff=String.valueOf(Math.round(util.roundToDecimals((((aakhanew-getaakhasrt)*100)/getaakhasrt),1)));
    gdavgdiff=String.valueOf(Math.round(util.roundToDecimals((((getaakhasrt-getaakhaold)*100)/getaakhaold),1)));
    percentgia=String.valueOf(Math.round(util.roundToDecimals(((getmnjoldvlu/getaakhaoldvlu)*100),1)));
    }

    if(Double.parseDouble(diffmnj)>=3)
    diffmnjcolor="green";

    if(Double.parseDouble(diffmnj)<=-3)
    diffmnjcolor="red";

    if(Double.parseDouble(olddiff)>=3)
    olddiffcolor="green";

    if(Double.parseDouble(olddiff)<=-3)
    olddiffcolor="red";

    if(Double.parseDouble(srtdiff)>=3)
    srtdiffcolor="green";

    if(Double.parseDouble(srtdiff)<=-3)
    srtdiffcolor="red";

    if(Double.parseDouble(gdavgdiff)>=3)
    gdavgdiffcolor="green";

    if(Double.parseDouble(gdavgdiff)<=-3)
    gdavgdiffcolor="red";

    if(!diffmnj.equals("") && !diffmnj.equals("0"))
    diffmnj=diffmnj+"%";

    if(!percentper.equals("") && !percentper.equals("0"))
    percentper=percentper+"%";

    if(!percentgia.equals("") && !percentgia.equals("0"))
    percentgia=percentgia+"%";

    if(!olddiff.equals("") && !olddiff.equals("0"))
    olddiff=olddiff+"%";


    if(!srtdiff.equals("") && !srtdiff.equals("0"))
    srtdiff=srtdiff+"%";

    if(!gdavgdiff.equals("") && !gdavgdiff.equals("0"))
    gdavgdiff=gdavgdiff+"%";
       
       row = newRow();
       addCell(++cellCt, "TOTAL", styleData);
       addCell(++cellCt, ttlcts, styleData,"");
       addCell(++cellCt, deptcts, styleData,"");
       addCell(++cellCt, aakhaold, styleData,"");
       addCell(++cellCt, aakhasrt, styleData,"");
       addCell(++cellCt, String.valueOf(aakhanew), styleData,"");
       addCell(++cellCt, aakhaoldvlu, styleData,"");
       addCell(++cellCt, aakhasrtvlu, styleData,"");
       addCell(++cellCt,String.valueOf(vlunewavg), styleData,"");
       addCell(++cellCt,  mnjold, styleData,"");
       addCell(++cellCt, mnjnew, styleData,"");
       addCell(++cellCt, diffaakhanew, styleData,"");
       addCell(++cellCt, mnjoldvlu, styleData,"");
       addCell(++cellCt, mnjnewvlu, styleData,"");
          if(diffmnjcolor.equals("red"))
          styleData=styleDataRed;
          if(diffmnjcolor.equals("green"))
          styleData=styleDataGreen;
          addCell(++cellCt, diffmnj, styleData);
          styleData=styleDataNormal;
          if(olddiffcolor.equals("red"))
          styleData=styleDataRed;
          if(olddiffcolor.equals("green"))
          styleData=styleDataGreen;
          addCell(++cellCt, olddiff, styleData);
          styleData=styleDataNormal;
          if(srtdiffcolor.equals("red"))
          styleData=styleDataRed;
          if(srtdiffcolor.equals("green"))
          styleData=styleDataGreen;
          addCell(++cellCt, srtdiff, styleData);
          styleData=styleDataNormal;
          if(gdavgdiffcolor.equals("red"))
          styleData=styleDataRed;
          if(gdavgdiffcolor.equals("green"))
          styleData=styleDataGreen;
          addCell(++cellCt, gdavgdiff, styleData);
          styleData=styleDataNormal;
       addCell(++cellCt, percentper, styleData);
       addCell(++cellCt, percentgia, styleData);
      }
       
     return hwb;
     }
    
    public HSSFWorkbook getDataGenericXlQuick(HttpServletRequest req,String colSpan,String loop,String qty,String cts,String avg,String rap,String age,String hit,String vlu,String net_dis,String trf_cmp) {
    hwb = new HSSFWorkbook();
    autoColums = new TreeSet();
    session = req.getSession(false);
    String fontNm = "Geneva";
    fontHdr = hwb.createFont();
    fontHdr.setFontHeightInPoints((short)8);
    fontHdr.setFontName(fontNm);
    fontHdr.setColor(HSSFColor.BLACK.index);
    fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

    styleHdr = hwb.createCellStyle();
    styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleHdr.setBorderBottom((short)1);
    styleHdr.setBorderTop((short)1);
    styleHdr.setBorderLeft((short)1);
    styleHdr.setBorderRight((short)1);
    styleHdr.setFont(fontHdr);


    fontData = hwb.createFont();
    fontData.setFontHeightInPoints((short)8);
    fontData.setFontName(fontNm);

    styleData = hwb.createCellStyle();
    styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleData.setBorderBottom((short)1);
    styleData.setBorderTop((short)1);
    styleData.setBorderLeft((short)1);
    styleData.setBorderRight((short)1);
    styleData.setFont(fontData);

    HSSFFont fontDataNormal = hwb.createFont();
    fontDataNormal.setFontHeightInPoints((short)8);
    fontDataNormal.setFontName(fontNm);

    HSSFFont fontDataRed = hwb.createFont();
    fontDataRed.setFontHeightInPoints((short)8);
    fontDataRed.setFontName(fontNm);
    fontDataRed.setColor(HSSFColor.RED.index);

    HSSFFont fontDataGreen = hwb.createFont();
    fontDataGreen.setFontHeightInPoints((short)8);
    fontDataGreen.setFontName(fontNm);
    fontDataGreen.setColor(HSSFColor.GREEN.index);

    HSSFFont fontDataMaroon = hwb.createFont();
    fontDataMaroon.setFontHeightInPoints((short)8);
    fontDataMaroon.setFontName(fontNm);
    fontDataMaroon.setColor(HSSFColor.MAROON.index);

    HSSFCellStyle styleDataNormal = hwb.createCellStyle();
    styleDataNormal.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataNormal.setBorderBottom((short)1);
    styleDataNormal.setBorderTop((short)1);
    styleDataNormal.setBorderLeft((short)1);
    styleDataNormal.setBorderRight((short)1);
    styleDataNormal.setFont(fontDataNormal);

    HSSFCellStyle styleDataRed = hwb.createCellStyle();
    styleDataRed.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataRed.setBorderBottom((short)1);
    styleDataRed.setBorderTop((short)1);
    styleDataRed.setBorderLeft((short)1);
    styleDataRed.setBorderRight((short)1);
    styleDataRed.setFont(fontDataRed);

    HSSFCellStyle styleDataMaroon = hwb.createCellStyle();
    styleDataMaroon.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataMaroon.setBorderBottom((short)1);
    styleDataMaroon.setBorderTop((short)1);
    styleDataMaroon.setBorderLeft((short)1);
    styleDataMaroon.setBorderRight((short)1);
    styleDataMaroon.setFont(fontDataMaroon);

    HSSFCellStyle styleDataGreen = hwb.createCellStyle();
    styleDataGreen.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataGreen.setBorderBottom((short)1);
    styleDataGreen.setBorderTop((short)1);
    styleDataGreen.setBorderLeft((short)1);
    styleDataGreen.setBorderRight((short)1);
    styleDataGreen.setFont(fontDataGreen);

    HSSFCellStyle styleDataRedbg = hwb.createCellStyle();
    styleDataRedbg.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataRedbg.setFillForegroundColor(HSSFColor.RED.index);
    styleDataRedbg.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    styleDataRedbg.setFont(fontDataNormal);
    styleDataRedbg.setBorderBottom((short)1);
    styleDataRedbg.setBorderTop((short)1);
    styleDataRedbg.setBorderLeft((short)1);
    styleDataRedbg.setBorderRight((short)1);

    HSSFCellStyle styleDataGreenbg = hwb.createCellStyle();
    styleDataGreenbg.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataGreenbg.setFillForegroundColor(HSSFColor.LIME.index);
    styleDataGreenbg.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    styleDataGreenbg.setFont(fontDataNormal);
    styleDataGreenbg.setBorderBottom((short)1);
    styleDataGreenbg.setBorderTop((short)1);
    styleDataGreenbg.setBorderLeft((short)1);
    styleDataGreenbg.setBorderRight((short)1);

    HSSFCellStyle styleDataYellowbg = hwb.createCellStyle();
    styleDataYellowbg.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataYellowbg.setFillForegroundColor(HSSFColor.YELLOW.index);
    styleDataYellowbg.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    styleDataYellowbg.setFont(fontDataNormal);
    styleDataYellowbg.setBorderBottom((short)1);
    styleDataYellowbg.setBorderTop((short)1);
    styleDataYellowbg.setBorderLeft((short)1);
    styleDataYellowbg.setBorderRight((short)1);

    HashMap dbSysInfo=info.getDmbsInfoLst();

    String docPath = (String)dbSysInfo.get("GRAPH_PATH");

    double MIN_INVENTORY_DAYS = Double.parseDouble((String)dbSysInfo.get("MIN_INVENTORY_DAYS"));
    double TOLERANCE_MAX_PCT = Double.parseDouble((String)dbSysInfo.get("TOLERANCE_MAX_PCT"));
    double TOLERANCE_MIN_PCT = Double.parseDouble((String)dbSysInfo.get("TOLERANCE_MIN_PCT"));
    double days = Double.parseDouble((String)session.getAttribute("days"));

    String docDwnPath = (String)dbSysInfo.get("GRAPH_DWN");
    ArrayList commkeyList = (ArrayList)session.getAttribute("commkeyList");
    HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
    HashMap getGrandTotalList = (HashMap)session.getAttribute("getGrandTotalList");
    ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
    HashMap sttColorCodeMap =((HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_sttColorCodeMap") == null)?new HashMap():(HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_sttColorCodeMap"); 
    ArrayList rowListGt = (ArrayList)session.getAttribute("rowList");
    ArrayList colListGt = (ArrayList)session.getAttribute("colList");
    // String age = (String)request.getAttribute("Age");
    // String hit = (String)request.getAttribute("Hit");
    HashMap dbinfo = info.getDmbsInfoLst();
    String sh = (String)dbinfo.get("SHAPE");
    String szval = (String)dbinfo.get("SIZE");
    String rowVal = (String)dbinfo.get("COL");
    String colVal = (String)dbinfo.get("CLR");
    HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
    ArrayList gridcommLst=new ArrayList();
    ArrayList gridrowLst=new ArrayList();
    ArrayList gridcolLst=new ArrayList();
    gridcommLst=(ArrayList)gridstructure.get("COMM");
    gridrowLst=(ArrayList)gridstructure.get("ROW");
    gridcolLst=(ArrayList)gridstructure.get("COL");
    HashMap mprp = info.getSrchMprp();
    HashMap prp = info.getSrchPrp();
    String checkClr="";
    String checkCol="";
    String checkrowVal="";
    String checkcolVal="",imageMap="";
    int loopclr=0;
    int loopcol=0;
    int tcount=0;
    String style="";
    String tdstyle="";
    String hasrc="";
    String imgid="";
        HashMap dscttlLst= ((HashMap)session.getAttribute("dscttlLst") == null)?new HashMap():(HashMap)session.getAttribute("dscttlLst");
        ArrayList defaultstatusLst= ((ArrayList)session.getAttribute("defaultstatusLst") == null)?new ArrayList():(ArrayList)session.getAttribute("defaultstatusLst");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
          ArrayList pageList=new ArrayList();
          HashMap pageDtlMap=new HashMap();
          String fld_nme="",fld_ttl="",val_cond="",dflt_val="",lov_qry="",fld_typ="",form_nme="",flg1="";
        double perdfvalAvg=0,perdfvalQty=0;
        int defaultstatusLstsz=defaultstatusLst.size();
        String perQTY="",perAVG="";
        JspUtil jsputil=new JspUtil();
        pageList=(ArrayList)pageDtl.get("PERDIFFQTY");
        if(pageList!=null && pageList.size() >0){
        for(int j=0;j<pageList.size();j++){
        pageDtlMap=(HashMap)pageList.get(j);
        perQTY=(String)pageDtlMap.get("dflt_val");
        }
        }
        pageList=(ArrayList)pageDtl.get("PERDIFFAVG");
        if(pageList!=null && pageList.size() >0){
        for(int j=0;j<pageList.size();j++){
        pageDtlMap=(HashMap)pageList.get(j);
        perAVG=(String)pageDtlMap.get("dflt_val");
        }
        }
    if(prp==null){
    //    util.initPrpSrch();
    prp = info.getSrchPrp();
    mprp = info.getSrchMprp();
    }
    String TITLE="";
    ArrayList rowList = (ArrayList)prp.get(gridrowLst.get(0)+"V");
    ArrayList colList = (ArrayList)prp.get(gridcolLst.get(0)+"V");
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
    String GenStt=(String)session.getAttribute("GenStt");
    // String processtm=util.nvl((String)request.getAttribute("processtm"));
    // ArrayList srchids= (ArrayList)request.getAttribute("srchids");
    int sttLstSize=statusLst.size();
    int rownum=0;
    int colnum=0;
    int colnumstt=0;
    double perCentSold,perCentSoldQty,perCentMinTol,perCentMaxTol;
    String inventoryDis = util.nvl((String)sttColorCodeMap.get("INVDIS"));
    String isShowgraph = util.nvl((String)sttColorCodeMap.get("GRAPH"));
    // int loopno=Integer.parseInt(loop);
    int loopno=0;
    int loopto=commkeyList.size()-1;
    if(commkeyList!=null && commkeyList.size()>0){
    if(!loop.equals("All")){
    loopno =Integer.parseInt(loop);
    loopto = loopno;
    }
    sheet = addSheet();
    for(int i=loopno ;i<=loopto;i++ ){
    String key = (String)commkeyList.get(i);
    String keyLable = key;
    keyLable = (String)mprp.get(gridcommLst.get(0))+" : "+keyLable;

    for(int g=1 ;g <gridcommLst.size();g++ ){
    keyLable=keyLable.replaceFirst("@", " "+(String)mprp.get(gridcommLst.get(g))+" :");
    }
    loopclr=0;
    loopcol=0;
    String replaceplus="";
    if(key.indexOf("+") > -1)
    replaceplus=key.replaceAll("\\+","~");
    else
    replaceplus=key;
    int span=Integer.parseInt(colSpan);
    // sheet = addSheet();
    int currCell = ++cellCt;
    row = newRow();
    row = newRow();
    CellRangeAddress rang = mergeCell(rowCt, rowCt, 2, 10);
    addMergeCell(2, keyLable , styleHdr, rang);
    // addCell(++cellCt,keyLable, styleHdr);
    row = newRow();
    row = newRow();
    addCell(++cellCt, " ", styleHdr);
    for(int j=0;j< colList.size();j++){
    String colV = (String)colList.get(j);
    if(colListGt.contains(colV)){
    currCell = ++cellCt;

    rang = mergeCell(rowCt, rowCt, currCell, currCell+(span+1)*sttLstSize-1);
    addMergeCell(currCell, (String)colList.get(j), styleHdr, rang);

    cellCt = currCell+(span+1)*sttLstSize-1;
    }}
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell+(span+1)*sttLstSize-1);
    addMergeCell(currCell, "Total", styleHdr, rang);

    cellCt = currCell+1;
    row = newRow();
    addCell(++cellCt, " ", styleHdr);
    for(int k=0;k< colList.size();k++){
    String colV = (String)colList.get(k);
    if(colListGt.contains(colV)){
    colnumstt++;
    for(int st=0;st<statusLst.size();st++){
    String stt=(String)statusLst.get(st);
    String bocolor=(String)sttColorCodeMap.get(stt);
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell+span);
    addMergeCell(currCell,util.nvl((String)dscttlLst.get(stt),stt), styleHdr, rang);
    cellCt = currCell+span;

    }}}
    colnumstt=0;
    for(int st=0;st<statusLst.size();st++){
    colnumstt++;
    String stt=(String)statusLst.get(st);
    String bocolor=(String)sttColorCodeMap.get(stt);
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell+span);
    addMergeCell(currCell,util.nvl((String)dscttlLst.get(stt),stt), styleHdr, rang);
    cellCt = currCell+span;
    }
    cellCt = currCell+1;
    row = newRow();
    addCell(++cellCt, "", styleHdr);
    for (int k=0;k< colList.size();k++){
    String colV = (String)colList.get(k);
    if(colListGt.contains(colV)){
    for(int st=0;st<statusLst.size();st++){
    colnum++;
    String stt=(String)statusLst.get(st);
    String bocolor=(String)sttColorCodeMap.get(stt);
    if(qty.equals("Y"))
    addCell(++cellCt, "QTY", styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, "CTS", styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, "AVG", styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, "RAP", styleHdr);
    if(age.equals("Y"))
    addCell(++cellCt, "AGE", styleHdr);
    if(hit.equals("Y"))
    addCell(++cellCt, "HIT", styleHdr);
    if(vlu.equals("Y"))
    addCell(++cellCt, "VLU", styleHdr);
    if(net_dis.equals("Y"))
    addCell(++cellCt, "NET_DIS", styleHdr);
    if(trf_cmp.equals("Y"))
    addCell(++cellCt, "TRF_CMP", styleHdr);
    }}}
    for(int st=0;st<statusLst.size();st++){
    String stt=(String)statusLst.get(st);
    String bocolor=(String)sttColorCodeMap.get(stt);
    if(qty.equals("Y"))
    addCell(++cellCt, "QTY", styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, "CTS", styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, "AVG", styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, "RAP", styleHdr);
    if(age.equals("Y"))
    addCell(++cellCt, "AGE", styleHdr);
    if(hit.equals("Y"))
    addCell(++cellCt, "HIT", styleHdr);
    if(vlu.equals("Y"))
    addCell(++cellCt, "VLU", styleHdr);
    if(net_dis.equals("Y"))
    addCell(++cellCt, "NET_DIS", styleHdr);
    if(trf_cmp.equals("Y"))
    addCell(++cellCt, "TRF_CMP", styleHdr);
    }
    // sheet.createFreezePane(0, rowCt+1);

    for(int m=0;m< rowList.size();m++){
    String rowV = (String)rowList.get(m);
    boolean isDis = true;
    int totalQtyCol=0;
    int sumtotalQtyCol=0;
    int totalQtySold=0;
    int sumtotalQtySold=0;

    if(rowV.indexOf("+")!=-1 && !rowlprpTyp.equals("T") && !rowlprpTyp.equals("N"))
    isDis = false;
    if(rowV.indexOf("-")!=-1 && !rowlprpTyp.equals("T") && !rowlprpTyp.equals("N"))
    isDis = false;
    if(isDis){
    if(rowListGt.contains(rowV)){
    checkCol=key+"_row_"+loopcol;
    loopcol++;
    colnum=0;
    rownum++;
    row = newRow();
    addCell(++cellCt,rowV, styleHdr);
    for(int n=0;n< colList.size();n++){
    String colV = (String)colList.get(n);
    boolean isDis1 = true;

    if(colV.indexOf("+")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))
    isDis1 = false;
    if(colV.indexOf("-")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))
    isDis1 = false;
    if(isDis1){
    if(colListGt.contains(colV)){
    for(int st=0;st<statusLst.size();st++){
    colnum++;
    styleData = styleDataNormal;
    String stt=(String)statusLst.get(st);
    if(stt.equals("SOLD")){
    styleData =styleDataRed;
    }
    if(stt.equals("LAB")){
    styleData =styleDataGreen;
    }
    if(stt.equals("ASRT")){
    styleData =styleDataMaroon;
    }

    String keyQty = key+"@"+rowV+"@"+colV+"@"+stt+"@QTY";
    String keyCts = key+"@"+rowV+"@"+colV+"@"+stt+"@CTS";
    String keyAvg = key+"@"+rowV+"@"+colV+"@"+stt+"@AVG";
    String keyRap = key+"@"+rowV+"@"+colV+"@"+stt+"@RAP";
    String keyAge = key+"@"+rowV+"@"+colV+"@"+stt+"@AGE";
    String keyHit = key+"@"+rowV+"@"+colV+"@"+stt+"@HIT";
    String keyVlu = key+"@"+rowV+"@"+colV+"@"+stt+"@VLU";
    String keynet_dis = key+"@"+rowV+"@"+colV+"@"+stt+"@Net_Dis";
    String keytrf_cmp = key+"@"+rowV+"@"+colV+"@"+stt+"@TRF_CMP";
    String diffavgkey=key+"@"+rowV+"@"+colV;
    String diffqtykey=key+"@"+rowV+"@"+colV;
    String ctsId=key.trim()+"@CTS@"+rownum+"@"+colnum;
    String avgId=key.trim()+"@AVG@"+rownum+"@"+colnum;
    String rapId=key.trim()+"@RAP@"+rownum+"@"+colnum;
    String ageId=key.trim()+"@AGE@"+rownum+"@"+colnum;
    String hitId=key.trim()+"@HIT@"+rownum+"@"+colnum;
    String vluId=key.trim()+"@VLU@"+rownum+"@"+colnum;
    String valQty = util.nvl((String)dataDtl.get(keyQty.trim()));
    String valCts = util.nvl((String)dataDtl.get(keyCts.trim()));
    String valAvg = util.nvl((String)dataDtl.get(keyAvg.trim()));
    String valRap = util.nvl((String)dataDtl.get(keyRap.trim()));
    String valAge = util.nvl((String)dataDtl.get(keyAge.trim()));
    String valHit = util.nvl((String)dataDtl.get(keyHit.trim()));
    String valVlu = util.nvl((String)dataDtl.get(keyVlu.trim()));
        String valNet_dis = util.nvl((String)dataDtl.get(keynet_dis.trim()));
        String valTrf_cmp = util.nvl((String)dataDtl.get(keytrf_cmp.trim()));

    if(!valQty.equals("")){
    totalQtyCol=Integer.parseInt(valQty);
    sumtotalQtyCol+=totalQtyCol;
    String bocolor=(String)sttColorCodeMap.get(stt);
    style = "color: "+bocolor+";";
    tdstyle="";
    if(statusLst.contains("SOLD") && stt.equals("MKT") && inventoryDis.equals("Y")) {
    double mktQty=Double.parseDouble(valQty);
    String soldKeyQty = key+"@"+rowV+"@"+colV+"@SOLD@QTY";
    String soldQty = util.nvl2((String)dataDtl.get(soldKeyQty),"0");

    if(!soldQty.equals("")){
    perCentSold=Math.round((Double.parseDouble(soldQty)/days)*100);
    perCentSoldQty = Math.round((MIN_INVENTORY_DAYS*perCentSold)/100);
    perCentMinTol=perCentSoldQty-Math.round((perCentSoldQty/100)*TOLERANCE_MIN_PCT);
    perCentMaxTol=perCentSoldQty+Math.round((perCentSoldQty/100)*TOLERANCE_MAX_PCT);
    if(mktQty>=perCentMinTol && mktQty<=perCentMaxTol && mktQty!=0)
    styleData = styleDataYellowbg;
    if(mktQty<perCentMinTol && mktQty!=0)
    styleData =styleDataGreenbg;
    if(mktQty > perCentMaxTol && mktQty!=0)
    styleData =styleDataRedbg ;
    }
    }
        boolean displayper=false;
        if(defaultstatusLstsz>1){
        String firststt=util.nvl((String)defaultstatusLst.get(0));
        perdfvalAvg=0;perdfvalQty=0;
        if(stt.indexOf(firststt)<0){
        for(int df=1;df<defaultstatusLst.size();df++){
        String dfstt=util.nvl((String)defaultstatusLst.get(df));
           if(stt.indexOf(dfstt)>=0){
               diffavgkey=diffavgkey+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG";
               diffqtykey=diffqtykey+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY";
               String dfvalAvg = util.nvl((String)dataDtl.get(diffavgkey.trim()));
               String dfvalQty = util.nvl((String)dataDtl.get(diffqtykey.trim()));
               if(!valAvg.equals("") && !dfvalAvg.equals("")){
               perdfvalAvg=jsputil.roundTopercentage(Double.parseDouble(valAvg),Double.parseDouble(dfvalAvg));
               displayper=true;
        //                                  if(perdfvalAvg>0)
        //                                  styleavg = "color: green";
               }
               if(!valQty.equals("") && !dfvalQty.equals("")){
               perdfvalQty=jsputil.roundTopercentage(Double.parseDouble(valQty),Double.parseDouble(dfvalQty));
        //                                  if(perdfvalQty>0)
        //                                  styleqty = "color: green";
               }
               break;
           }
        }
        }
        }

        if(qty.equals("Y")){
        if(!perQTY.equals("") && displayper){
        valQty+=" | "+perdfvalQty;
        if(perdfvalQty>0)
        addCell(++cellCt, valQty , styleDataGreen);
        else
        addCell(++cellCt, valQty , styleDataRed); 
        }else{
        addCell(++cellCt, valQty , styleData);
        }
        }
    if(cts.equals("Y"))
    addCell(++cellCt, valCts, styleData,"");

        if(avg.equals("Y")){
        if(!perAVG.equals("") && displayper){
        valAvg+=" | "+perdfvalAvg;
        if(perdfvalAvg>0)
        addCell(++cellCt, valAvg , styleDataGreen);
        else
        addCell(++cellCt, valAvg , styleDataRed); 
        }else{
        addCell(++cellCt, valAvg , styleData);
        }
        }
    
    if(rap.equals("Y"))
    addCell(++cellCt,valRap, styleData,"");
    if(age.equals("Y"))
    addCell(++cellCt, valAge, styleData,"");
    if(hit.equals("Y"))
    addCell(++cellCt, valHit , styleData,"");
    if(vlu.equals("Y"))
    addCell(++cellCt, valVlu , styleData,"");
        if(net_dis.equals("Y"))
        addCell(++cellCt, valNet_dis , styleData,"");
        if(trf_cmp.equals("Y"))
        addCell(++cellCt, valTrf_cmp , styleData,"");
    }else{
    if(qty.equals("Y"))
    addCell(++cellCt, "" , styleHdr,"");
    if(cts.equals("Y"))
    addCell(++cellCt, "", styleHdr,"");
    if(avg.equals("Y"))
    addCell(++cellCt,"", styleHdr,"");
    if(rap.equals("Y"))
    addCell(++cellCt,"", styleHdr,"");
    if(age.equals("Y"))
    addCell(++cellCt, "", styleHdr,"");
    if(hit.equals("Y"))
    addCell(++cellCt, "" , styleHdr,"");
    if(vlu.equals("Y"))
    addCell(++cellCt, "" , styleHdr,"");
        if(net_dis.equals("Y"))
        addCell(++cellCt, "" , styleHdr,"");
        if(trf_cmp.equals("Y"))
        addCell(++cellCt, "" , styleHdr,"");
    }
    }}}}
    for(int sti=0;sti<statusLst.size();sti++){
    styleData = styleDataNormal;
    String stt=(String)statusLst.get(sti);
    if(stt.equals("SOLD")){
    styleData =styleDataRed;
    }
    if(stt.equals("LAB")){
    styleData =styleDataGreen;
    }
    if(stt.equals("ASRT")){
    styleData =styleDataMaroon;
    }
    String getcolttlqty=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@"+stt+"@QTY"),"0");
    String getcolttlcts=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@"+stt+"@CTS"),"0");
    String getcolttlavg=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@"+stt+"@AVG"),"0");
    String getcolttlrap=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@"+stt+"@RAP"),"0");
    String getcolttlage=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@"+stt+"@AGE"),"0");
    String getcolttlhit=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@"+stt+"@HIT"),"0");
    String getcolttlvlu=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@"+stt+"@VLU"),"0");
        String getcolttlnet_dis=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@"+stt+"@Net_Dis"),"0");
        String getcolttltrf_cmp=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@"+stt+"@TRF_CMP"),"0");
    if(!getcolttlqty.equals("")){
    tdstyle="";
    String bocolor=(String)sttColorCodeMap.get(stt);
    // piePrpDtl.put(key+"_"+stt+"_"+rowV+"_"+"COL",getcolttlqty);
    style = "color: "+bocolor+";";
    if(statusLst.contains("SOLD") && stt.equals("MKT") && inventoryDis.equals("Y")) {
    double mktQty=Double.parseDouble(getcolttlqty);
    String soldKeyQty = key+"@"+rowV+"@"+"SOLD@QTY";
    String soldQty = util.nvl2((String)dataDtl.get(soldKeyQty),"0");
    if(!soldQty.equals("")){
    perCentSold=Math.round((Double.parseDouble(soldQty)/days)*100);
    perCentSoldQty = Math.round((MIN_INVENTORY_DAYS*perCentSold)/100);
    perCentMinTol=perCentSoldQty-Math.round((perCentSoldQty/100)*TOLERANCE_MIN_PCT);
    perCentMaxTol=perCentSoldQty+Math.round((perCentSoldQty/100)*TOLERANCE_MAX_PCT);
    if(mktQty>=perCentMinTol && mktQty<=perCentMaxTol && mktQty!=0)
    styleData = styleDataYellowbg;
    if(mktQty<perCentMinTol && mktQty!=0)
    styleData =styleDataGreenbg;
    if(mktQty > perCentMaxTol && mktQty!=0)
    styleData =styleDataRedbg ;

    }
    }
        boolean displayper=false;
             String styleqty = "color: red";
             String styleavg = "color: red";
             if(defaultstatusLstsz>1){
             String firststt=util.nvl((String)defaultstatusLst.get(0));
             perdfvalAvg=0;perdfvalQty=0;
            if(stt.indexOf(firststt)<0){
            for(int df=1;df<defaultstatusLst.size();df++){
            String dfstt=util.nvl((String)defaultstatusLst.get(df));
                if(stt.indexOf(dfstt)>=0){
                    String dfgetcolttlqty=util.nvl((String)dataDtl.get(key+"@"+rowV+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY"));
                    String dfgetcolttlavg=util.nvl((String)dataDtl.get(key+"@"+rowV+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG"));
                    if(!getcolttlavg.equals("") && !dfgetcolttlavg.equals("")){
                    perdfvalAvg=jsputil.roundTopercentage(Double.parseDouble(getcolttlavg),Double.parseDouble(dfgetcolttlavg));
                    displayper=true;
                    if(perdfvalAvg>0)
                    styleavg = "color: green";
                    }
                    if(!getcolttlqty.equals("") && !dfgetcolttlqty.equals("")){
                    perdfvalQty=jsputil.roundTopercentage(Double.parseDouble(getcolttlqty),Double.parseDouble(dfgetcolttlqty));
                    if(perdfvalQty>0)
                    styleqty = "color: green";
                    }
                    break;
                }
            }
            }
            }
        if(qty.equals("Y")){
        if(!perQTY.equals("") && displayper){
        getcolttlqty+=" | "+perdfvalQty;
        if(perdfvalQty>0)
        addCell(++cellCt, getcolttlqty , styleDataGreen);
        else
        addCell(++cellCt, getcolttlqty , styleDataRed); 
        }else{
        addCell(++cellCt, getcolttlqty , styleData);
        }
        }
    if(cts.equals("Y"))
    addCell(++cellCt, getcolttlcts, styleData,"");
    //    if(avg.equals("Y")){
    //        if(!perAVG.equals("") && displayper){
    //         getcolttlavg+=" | "+perdfvalAvg;
    //        }
    //    addCell(++cellCt,getcolttlavg, styleData);
    //    }
        if(avg.equals("Y")){
        if(!perAVG.equals("") && displayper){
        getcolttlavg+=" | "+perdfvalAvg;
        if(perdfvalAvg>0)
        addCell(++cellCt, getcolttlavg , styleDataGreen);
        else
        addCell(++cellCt, getcolttlavg , styleDataRed); 
        }else{
        addCell(++cellCt, getcolttlavg , styleData);
        }
        }
    if(rap.equals("Y"))
    addCell(++cellCt,getcolttlrap, styleData,"");
    if(age.equals("Y"))
    addCell(++cellCt, getcolttlage, styleData,"");
    if(hit.equals("Y"))
    addCell(++cellCt, getcolttlhit , styleData,"");
    if(vlu.equals("Y"))
    addCell(++cellCt, getcolttlvlu , styleData,"");
        if(net_dis.equals("Y"))
        addCell(++cellCt, getcolttlnet_dis , styleData,"");
        if(trf_cmp.equals("Y"))
        addCell(++cellCt, getcolttltrf_cmp , styleData,"");
    }else{
    }
    }
    }}
    }
    row = newRow();
    addCell(++cellCt, "Total" , styleHdr);
    colnum=0;
    for(int j=0;j< colList.size();j++){
    String colV = (String)colList.get(j);
    if(colListGt.contains(colV)){
    for(int st=0;st<statusLst.size();st++){
    colnum++;
    styleData = styleDataNormal;
    String stt=(String)statusLst.get(st);
    if(stt.equals("SOLD")){
    styleData =styleDataRed;
    }
    if(stt.equals("LAB")){
    styleData =styleDataGreen;
    }
    if(stt.equals("ASRT")){
    styleData =styleDataMaroon;
    }
    String getclrttlqty=util.nvl2((String)dataDtl.get(key+"@"+colV+"@"+stt+"@QTY"),"0");
    String getclrttlcts=util.nvl2((String)dataDtl.get(key+"@"+colV+"@"+stt+"@CTS"),"0");
    String getclrttlavg=util.nvl2((String)dataDtl.get(key+"@"+colV+"@"+stt+"@AVG"),"0");
    String getclrttlrap=util.nvl2((String)dataDtl.get(key+"@"+colV+"@"+stt+"@RAP"),"0");
    String getclrttlage=util.nvl2((String)dataDtl.get(key+"@"+colV+"@"+stt+"@AGE"),"0");
    String getclrttlhit=util.nvl2((String)dataDtl.get(key+"@"+colV+"@"+stt+"@HIT"),"0");
    String getclrttlvlu=util.nvl2((String)dataDtl.get(key+"@"+colV+"@"+stt+"@VLU"),"0");
        String getclrttlnet_dis=util.nvl2((String)dataDtl.get(key+"@"+colV+"@"+stt+"@Net_Dis"),"0");
        String getclrttltrf_cmp=util.nvl2((String)dataDtl.get(key+"@"+colV+"@"+stt+"@TRF_CMP"),"0");
    String GCLRCTSID="GCLRIDCTS@"+colnum;
    String GCLRAVGID="GCLRIDAVG@"+colnum;
    String GCLRRAPID="GCLRIDRAP@"+colnum;
    String GCLRAGEID="GCLRIDAGE@"+colnum;
    String GCLRHITID="GCLRIDHIT@"+colnum;
    String GCLRVLUID="GCLRIDVLU@"+colnum;
    // if(!getclrttlqty.equals("")){
    // piePrpDtl.put(key+"_"+stt+"_"+colV+"_"+"CLR",getclrttlqty);
    // }
    if(!getclrttlqty.equals("")){
    String bocolor=(String)sttColorCodeMap.get(stt);
    style = "color: "+bocolor+";";
    tdstyle="";
    if(statusLst.contains("SOLD") && stt.equals("MKT") && inventoryDis.equals("Y")) {
    double mktQty=Double.parseDouble(getclrttlqty);
    String soldKeyQty = key+"@"+colV+"@"+"SOLD@QTY";
    String soldQty = util.nvl2((String)dataDtl.get(soldKeyQty),"0");
    if(!soldQty.equals("")){
    perCentSold=Math.round((Double.parseDouble(soldQty)/days)*100);
    perCentSoldQty = Math.round((MIN_INVENTORY_DAYS*perCentSold)/100);
    perCentMinTol=perCentSoldQty-Math.round((perCentSoldQty/100)*TOLERANCE_MIN_PCT);
    perCentMaxTol=perCentSoldQty+Math.round((perCentSoldQty/100)*TOLERANCE_MAX_PCT);
    if(mktQty>=perCentMinTol && mktQty<=perCentMaxTol && mktQty!=0)
    styleData = styleDataYellowbg;
    if(mktQty<perCentMinTol && mktQty!=0)
    styleData =styleDataGreenbg;
    if(mktQty > perCentMaxTol && mktQty!=0)
    styleData =styleDataRedbg ;

    }
    }
    }
        boolean displayper=false;
             String styleqty = "color: red";
             String styleavg = "color: red";
             if(defaultstatusLstsz>1){
             String firststt=util.nvl((String)defaultstatusLst.get(0));
             perdfvalAvg=0;perdfvalQty=0;
            if(stt.indexOf(firststt)<0){
            for(int df=1;df<defaultstatusLst.size();df++){
            String dfstt=util.nvl((String)defaultstatusLst.get(df));
                if(stt.indexOf(dfstt)>=0){
                    String dfgetclrttlqty=util.nvl((String)dataDtl.get(key+"@"+colV+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY"));
                    String dfgetclrttlqtyavg=util.nvl((String)dataDtl.get(key+"@"+colV+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG"));
                    if(!getclrttlavg.equals("") && !dfgetclrttlqtyavg.equals("")){
                    perdfvalAvg=jsputil.roundTopercentage(Double.parseDouble(getclrttlavg),Double.parseDouble(dfgetclrttlqtyavg));

                    displayper=true;
                    if(perdfvalAvg>0)
                    styleavg = "color: green";
                    }
                    if(!getclrttlqty.equals("") && !dfgetclrttlqty.equals("")){
                    perdfvalQty=jsputil.roundTopercentage(Double.parseDouble(getclrttlqty),Double.parseDouble(dfgetclrttlqty));
                    if(perdfvalQty>0)
                    styleqty = "color: green";
                    }
                    break;
                }
            }
            }
             }
    //        if(!perQTY.equals("") && displayper){
    //        getclrttlqty+=" | "+perdfvalQty;
    //        }
    //    if(qty.equals("Y"))
    //    addCell(++cellCt, getclrttlqty , styleData);
        if(qty.equals("Y")){
        if(!perQTY.equals("") && displayper){
        getclrttlqty+=" | "+perdfvalQty;
        if(perdfvalQty>0)
        addCell(++cellCt, getclrttlqty , styleDataGreen);
        else
        addCell(++cellCt, getclrttlqty , styleDataRed); 
        }else{
        addCell(++cellCt, getclrttlqty , styleData);
        }
        }
    if(cts.equals("Y"))
    addCell(++cellCt, getclrttlcts, styleData,"");
    //        if(avg.equals("Y")){
    //            if(!perAVG.equals("") && displayper){
    //             getclrttlavg+=" | "+perdfvalAvg;
    //            }
    //    addCell(++cellCt,getclrttlavg, styleData);
    //        }
        if(avg.equals("Y")){
        if(!perAVG.equals("") && displayper){
        getclrttlavg+=" | "+perdfvalAvg;
        if(perdfvalAvg>0)
        addCell(++cellCt, getclrttlavg , styleDataGreen);
        else
        addCell(++cellCt, getclrttlavg , styleDataRed); 
        }else{
        addCell(++cellCt, getclrttlavg , styleData);
        }
        }
    if(rap.equals("Y"))
    addCell(++cellCt,getclrttlrap, styleData,"");
    if(age.equals("Y"))
    addCell(++cellCt, getclrttlage, styleData,"");
    if(hit.equals("Y"))
    addCell(++cellCt, getclrttlhit , styleData,"");
    if(vlu.equals("Y"))
    addCell(++cellCt, getclrttlvlu , styleData,"");
        if(net_dis.equals("Y"))
        addCell(++cellCt, getclrttlnet_dis , styleData,"");
        if(trf_cmp.equals("Y"))
        addCell(++cellCt, getclrttltrf_cmp , styleData,"");
    }
    }}
    for(int st=0;st<statusLst.size();st++){
    styleData = styleDataNormal;
    String stt=(String)statusLst.get(st);
    if(stt.equals("SOLD")){
    styleData =styleDataRed;
    }
    if(stt.equals("LAB")){
    styleData =styleDataGreen;
    }
    if(stt.equals("ASRT")){
    styleData =styleDataMaroon;
    }
    String getttlqty=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@QTY"),"0");
    String getttlcts=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@CTS"),"0");
    String getttlavg=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@AVG"),"0");
    String getttlrap=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@RAP"),"0");
    String getttlage=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@AGE"),"0");
    String getttlhit=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@HIT"),"0");
    String getttlvlu=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@VLU"),"0");
        String getttlnet_dis=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@Net_Dis"),"0");
        String getttltrf_cmp=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@TRF_CMP"),"0");
    String GCTSID="GIDCTS@"+colnum+"@"+st;
    String GAVGID="GIDAVG@"+colnum+"@"+st;
    String GRAPID="GIDRAP@"+colnum+"@"+st;
    String GAGEID="GIDAGE@"+colnum+"@"+st;
    String GHITID="GIDHIT@"+colnum+"@"+st;
    String bocolor=(String)sttColorCodeMap.get(stt);
    style = "color: "+bocolor+";";

    if(!getttlqty.equals("")){
    tdstyle="";
    if(statusLst.contains("SOLD") && stt.equals("MKT") && inventoryDis.equals("Y")) {
    double mktQty=Double.parseDouble(getttlqty);
    String soldKeyQty = key+"@SOLD@QTY";
    String soldQty = util.nvl2((String)getGrandTotalList.get(soldKeyQty),"0");
    if(!soldQty.equals("")){
    perCentSold=Math.round((Double.parseDouble(soldQty)/days)*100);
    perCentSoldQty = Math.round((MIN_INVENTORY_DAYS*perCentSold)/100);
    perCentMinTol=perCentSoldQty-Math.round((perCentSoldQty/100)*TOLERANCE_MIN_PCT);
    perCentMaxTol=perCentSoldQty+Math.round((perCentSoldQty/100)*TOLERANCE_MAX_PCT);
    if(mktQty>=perCentMinTol && mktQty<=perCentMaxTol && mktQty!=0)
    styleData = styleDataYellowbg;
    if(mktQty<perCentMinTol && mktQty!=0)
    styleData =styleDataGreenbg;
    if(mktQty > perCentMaxTol && mktQty!=0)
    styleData =styleDataRedbg ;
    }
    }
    }
        boolean displayper=false;
             String styleqty = "color: red";
             String styleavg = "color: red";
             if(defaultstatusLstsz>1){
             String firststt=util.nvl((String)defaultstatusLst.get(0));
             perdfvalAvg=0;perdfvalQty=0;
            if(stt.indexOf(firststt)<0){
            for(int df=1;df<defaultstatusLst.size();df++){
            String dfstt=util.nvl((String)defaultstatusLst.get(df));
                if(stt.indexOf(dfstt)>=0){
                    String dfgetttlqty=util.nvl((String)getGrandTotalList.get(key+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY"));
                    String dfgetttlavg=util.nvl((String)getGrandTotalList.get(key+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG"));
                    if(!getttlavg.equals("") && !dfgetttlavg.equals("")){
                    perdfvalAvg=jsputil.roundTopercentage(Double.parseDouble(getttlavg),Double.parseDouble(dfgetttlavg));
                    displayper=true;
                    if(perdfvalAvg>0)
                    styleavg = "color: green";
                    }
                    if(!getttlqty.equals("") && !dfgetttlqty.equals("")){
                    perdfvalQty=jsputil.roundTopercentage(Double.parseDouble(getttlqty),Double.parseDouble(dfgetttlqty));
                    if(perdfvalQty>0)
                    styleqty = "color: green";
                    }
                    break;
                }
            }
            }
            }
    //        if(!perQTY.equals("") && displayper){
    //        getttlqty+=" | "+perdfvalQty;
    //        }
    //    if(qty.equals("Y"))
    //    addCell(++cellCt, getttlqty , styleData);
        if(qty.equals("Y")){
        if(!perQTY.equals("") && displayper){
        getttlqty+=" | "+perdfvalQty;
        if(perdfvalQty>0)
        addCell(++cellCt, getttlqty , styleDataGreen);
        else
        addCell(++cellCt, getttlqty , styleDataRed); 
        }else{
        addCell(++cellCt, getttlqty , styleData);
        }
        }
    if(cts.equals("Y"))
    addCell(++cellCt, getttlcts, styleData,"");
    //        if(avg.equals("Y")){
    //            if(!perAVG.equals("") && displayper){
    //             getttlavg+=" | "+perdfvalAvg;
    //            }
    //    addCell(++cellCt,getttlavg, styleData);
    //        }
        if(avg.equals("Y")){
        if(!perAVG.equals("") && displayper){
        getttlavg+=" | "+perdfvalAvg;
        if(perdfvalAvg>0)
        addCell(++cellCt, getttlavg , styleDataGreen);
        else
        addCell(++cellCt, getttlavg , styleDataRed); 
        }else{
        addCell(++cellCt, getttlavg , styleData);
        }
        }
    if(rap.equals("Y"))
    addCell(++cellCt,getttlrap, styleData,"");
    if(age.equals("Y"))
    addCell(++cellCt, getttlage, styleData,"");
    if(hit.equals("Y"))
    addCell(++cellCt, getttlhit , styleData,"");
    if(vlu.equals("Y"))
    addCell(++cellCt, getttlvlu , styleData,"");
        if(net_dis.equals("Y"))
        addCell(++cellCt, getttlnet_dis , styleData,"");
        if(trf_cmp.equals("Y"))
        addCell(++cellCt, getttltrf_cmp , styleData,"");
    }
    }
    Iterator it=autoColums.iterator();
    while(it.hasNext()) {
    String value=(String)it.next();
    int colId = Integer.parseInt(value);
    sheet.autoSizeColumn((short)colId, true);
    }
    }

    return hwb;
    }
    public HSSFWorkbook getDataGenericXl(HttpServletRequest req,String colSpan,String loop,String qty,String cts,String avg,String rap,String age,String hit,String vlu,String net_dis,String trf_cmp) {
    hwb = new HSSFWorkbook();
    autoColums = new TreeSet();
    session = req.getSession(false);
    String fontNm = "Geneva";
    fontHdr = hwb.createFont();
    fontHdr.setFontHeightInPoints((short)8);
    fontHdr.setFontName(fontNm);
    fontHdr.setColor(HSSFColor.BLACK.index);
    fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

    styleHdr = hwb.createCellStyle();
    styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleHdr.setBorderBottom((short)1);
    styleHdr.setBorderTop((short)1);
    styleHdr.setBorderLeft((short)1);
    styleHdr.setBorderRight((short)1);
    styleHdr.setFont(fontHdr);


    fontData = hwb.createFont();
    fontData.setFontHeightInPoints((short)8);
    fontData.setFontName(fontNm);

    styleData = hwb.createCellStyle();
    styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleData.setBorderBottom((short)1);
    styleData.setBorderTop((short)1);
    styleData.setBorderLeft((short)1);
    styleData.setBorderRight((short)1);
    styleData.setFont(fontData);

    HSSFFont fontDataNormal = hwb.createFont();
    fontDataNormal.setFontHeightInPoints((short)8);
    fontDataNormal.setFontName(fontNm);

    HSSFFont fontDataRed = hwb.createFont();
    fontDataRed.setFontHeightInPoints((short)8);
    fontDataRed.setFontName(fontNm);
    fontDataRed.setColor(HSSFColor.RED.index);

    HSSFFont fontDataGreen = hwb.createFont();
    fontDataGreen.setFontHeightInPoints((short)8);
    fontDataGreen.setFontName(fontNm);
    fontDataGreen.setColor(HSSFColor.GREEN.index);

    HSSFFont fontDataMaroon = hwb.createFont();
    fontDataMaroon.setFontHeightInPoints((short)8);
    fontDataMaroon.setFontName(fontNm);
    fontDataMaroon.setColor(HSSFColor.MAROON.index);

    HSSFCellStyle styleDataNormal = hwb.createCellStyle();
    styleDataNormal.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataNormal.setBorderBottom((short)1);
    styleDataNormal.setBorderTop((short)1);
    styleDataNormal.setBorderLeft((short)1);
    styleDataNormal.setBorderRight((short)1);
    styleDataNormal.setFont(fontDataNormal);

    HSSFCellStyle styleDataRed = hwb.createCellStyle();
    styleDataRed.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataRed.setBorderBottom((short)1);
    styleDataRed.setBorderTop((short)1);
    styleDataRed.setBorderLeft((short)1);
    styleDataRed.setBorderRight((short)1);
    styleDataRed.setFont(fontDataRed);

    HSSFCellStyle styleDataMaroon = hwb.createCellStyle();
    styleDataMaroon.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataMaroon.setBorderBottom((short)1);
    styleDataMaroon.setBorderTop((short)1);
    styleDataMaroon.setBorderLeft((short)1);
    styleDataMaroon.setBorderRight((short)1);
    styleDataMaroon.setFont(fontDataMaroon);

    HSSFCellStyle styleDataGreen = hwb.createCellStyle();
    styleDataGreen.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataGreen.setBorderBottom((short)1);
    styleDataGreen.setBorderTop((short)1);
    styleDataGreen.setBorderLeft((short)1);
    styleDataGreen.setBorderRight((short)1);
    styleDataGreen.setFont(fontDataGreen);

    HSSFCellStyle styleDataRedbg = hwb.createCellStyle();
    styleDataRedbg.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataRedbg.setFillForegroundColor(HSSFColor.RED.index);
    styleDataRedbg.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    styleDataRedbg.setFont(fontDataNormal);
    styleDataRedbg.setBorderBottom((short)1);
    styleDataRedbg.setBorderTop((short)1);
    styleDataRedbg.setBorderLeft((short)1);
    styleDataRedbg.setBorderRight((short)1);

    HSSFCellStyle styleDataGreenbg = hwb.createCellStyle();
    styleDataGreenbg.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataGreenbg.setFillForegroundColor(HSSFColor.LIME.index);
    styleDataGreenbg.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    styleDataGreenbg.setFont(fontDataNormal);
    styleDataGreenbg.setBorderBottom((short)1);
    styleDataGreenbg.setBorderTop((short)1);
    styleDataGreenbg.setBorderLeft((short)1);
    styleDataGreenbg.setBorderRight((short)1);

    HSSFCellStyle styleDataYellowbg = hwb.createCellStyle();
    styleDataYellowbg.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleDataYellowbg.setFillForegroundColor(HSSFColor.YELLOW.index);
    styleDataYellowbg.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    styleDataYellowbg.setFont(fontDataNormal);
    styleDataYellowbg.setBorderBottom((short)1);
    styleDataYellowbg.setBorderTop((short)1);
    styleDataYellowbg.setBorderLeft((short)1);
    styleDataYellowbg.setBorderRight((short)1);

    HashMap dbSysInfo=info.getDmbsInfoLst();

    String docPath = (String)dbSysInfo.get("GRAPH_PATH");

    double MIN_INVENTORY_DAYS = Double.parseDouble((String)dbSysInfo.get("MIN_INVENTORY_DAYS"));
    double TOLERANCE_MAX_PCT = Double.parseDouble((String)dbSysInfo.get("TOLERANCE_MAX_PCT"));
    double TOLERANCE_MIN_PCT = Double.parseDouble((String)dbSysInfo.get("TOLERANCE_MIN_PCT"));
    double days = Double.parseDouble((String)session.getAttribute("days"));

    String docDwnPath = (String)dbSysInfo.get("GRAPH_DWN");
    ArrayList commkeyList = (ArrayList)session.getAttribute("commkeyList");
    HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
    HashMap getGrandTotalList = (HashMap)session.getAttribute("getGrandTotalList");
    ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
    HashMap sttColorCodeMap =((HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_sttColorCodeMap") == null)?new HashMap():(HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_sttColorCodeMap"); 
    ArrayList rowListGt = (ArrayList)session.getAttribute("rowList");
    ArrayList colListGt = (ArrayList)session.getAttribute("colList");
    // String age = (String)request.getAttribute("Age");
    // String hit = (String)request.getAttribute("Hit");
    HashMap dbinfo = info.getDmbsInfoLst();
    String sh = (String)dbinfo.get("SHAPE");
    String szval = (String)dbinfo.get("SIZE");
    String rowVal = (String)dbinfo.get("COL");
    String colVal = (String)dbinfo.get("CLR");
    HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
    ArrayList gridcommLst=new ArrayList();
    ArrayList gridrowLst=new ArrayList();
    ArrayList gridcolLst=new ArrayList();
    gridcommLst=(ArrayList)gridstructure.get("COMM");
    gridrowLst=(ArrayList)gridstructure.get("ROW");
    gridcolLst=(ArrayList)gridstructure.get("COL");
    HashMap mprp = info.getSrchMprp();
    HashMap prp = info.getSrchPrp();
    String checkClr="";
    String checkCol="";
    String checkrowVal="";
    String checkcolVal="",imageMap="";
    int loopclr=0;
    int loopcol=0;
    int tcount=0;
    String style="";
    String tdstyle="";
    String hasrc="";
    String imgid="";
        HashMap dscttlLst= ((HashMap)session.getAttribute("dscttlLst") == null)?new HashMap():(HashMap)session.getAttribute("dscttlLst");
        ArrayList defaultstatusLst= ((ArrayList)session.getAttribute("defaultstatusLst") == null)?new ArrayList():(ArrayList)session.getAttribute("defaultstatusLst");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
          ArrayList pageList=new ArrayList();
          HashMap pageDtlMap=new HashMap();
          String fld_nme="",fld_ttl="",val_cond="",dflt_val="",lov_qry="",fld_typ="",form_nme="",flg1="";
        double perdfvalAvg=0,perdfvalQty=0;
        int defaultstatusLstsz=defaultstatusLst.size();
        String perQTY="",perAVG="";
        JspUtil jsputil=new JspUtil();
        pageList=(ArrayList)pageDtl.get("PERDIFFQTY");
        if(pageList!=null && pageList.size() >0){
        for(int j=0;j<pageList.size();j++){
        pageDtlMap=(HashMap)pageList.get(j);
        perQTY=(String)pageDtlMap.get("dflt_val");
        }
        }
        pageList=(ArrayList)pageDtl.get("PERDIFFAVG");
        if(pageList!=null && pageList.size() >0){
        for(int j=0;j<pageList.size();j++){
        pageDtlMap=(HashMap)pageList.get(j);
        perAVG=(String)pageDtlMap.get("dflt_val");
        }
        }
    if(prp==null){
//    util.initPrpSrch();
    prp = info.getSrchPrp();
    mprp = info.getSrchMprp();
    }
    String TITLE="";
    ArrayList rowList = (ArrayList)prp.get(gridrowLst.get(0)+"V");
    ArrayList colList = (ArrayList)prp.get(gridcolLst.get(0)+"V");
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
    String GenStt=(String)session.getAttribute("GenStt");
    // String processtm=util.nvl((String)request.getAttribute("processtm"));
    // ArrayList srchids= (ArrayList)request.getAttribute("srchids");
    int sttLstSize=statusLst.size();
    int rownum=0;
    int colnum=0;
    int colnumstt=0;
    double perCentSold,perCentSoldQty,perCentMinTol,perCentMaxTol;
    String inventoryDis = util.nvl((String)sttColorCodeMap.get("INVDIS"));
    String isShowgraph = util.nvl((String)sttColorCodeMap.get("GRAPH"));
    // int loopno=Integer.parseInt(loop);
    int loopno=0;
    int loopto=commkeyList.size()-1;
    if(commkeyList!=null && commkeyList.size()>0){
    if(!loop.equals("All")){
    loopno =Integer.parseInt(loop);
    loopto = loopno;
    }
    sheet = addSheet();
    for(int i=loopno ;i<=loopto;i++ ){
    String key = (String)commkeyList.get(i);
    String keyLable = key;
    keyLable = (String)mprp.get(gridcommLst.get(0))+" : "+keyLable;

    for(int g=1 ;g <gridcommLst.size();g++ ){
    keyLable=keyLable.replaceFirst("@", " "+(String)mprp.get(gridcommLst.get(g))+" :");
    }
    loopclr=0;
    loopcol=0;
    String replaceplus="";
    if(key.indexOf("+") > -1)
    replaceplus=key.replaceAll("\\+","~");
    else
    replaceplus=key;
    int span=Integer.parseInt(colSpan);
    // sheet = addSheet();
    int currCell = ++cellCt;
    row = newRow();
    row = newRow();
    CellRangeAddress rang = mergeCell(rowCt, rowCt, 2, 10);
    addMergeCell(2, keyLable , styleHdr, rang);
    // addCell(++cellCt,keyLable, styleHdr);
    row = newRow();
    row = newRow();
    addCell(++cellCt, " ", styleHdr);
    for(int j=0;j< colList.size();j++){
    String colV = (String)colList.get(j);
    if(colListGt.contains(colV)){
    currCell = ++cellCt;

    rang = mergeCell(rowCt, rowCt, currCell, currCell+(span+1)*sttLstSize-1);
    addMergeCell(currCell, (String)colList.get(j), styleHdr, rang);

    cellCt = currCell+(span+1)*sttLstSize-1;
    }}
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell+(span+1)*sttLstSize-1);
    addMergeCell(currCell, "Total", styleHdr, rang);

    cellCt = currCell+1;
    row = newRow();
    addCell(++cellCt, " ", styleHdr);
    for(int k=0;k< colList.size();k++){
    String colV = (String)colList.get(k);
    if(colListGt.contains(colV)){
    colnumstt++;
    for(int st=0;st<statusLst.size();st++){
    String stt=(String)statusLst.get(st);
    String bocolor=(String)sttColorCodeMap.get(stt);
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell+span);
    addMergeCell(currCell,util.nvl((String)dscttlLst.get(stt),stt), styleHdr, rang);
    cellCt = currCell+span;

    }}}
    colnumstt=0;
    for(int st=0;st<statusLst.size();st++){
    colnumstt++;
    String stt=(String)statusLst.get(st);
    String bocolor=(String)sttColorCodeMap.get(stt);
    currCell = ++cellCt;
    rang = mergeCell(rowCt, rowCt, currCell, currCell+span);
    addMergeCell(currCell,util.nvl((String)dscttlLst.get(stt),stt), styleHdr, rang);
    cellCt = currCell+span;
    }
    cellCt = currCell+1;
    row = newRow();
    addCell(++cellCt, "", styleHdr);
    for (int k=0;k< colList.size();k++){
    String colV = (String)colList.get(k);
    if(colListGt.contains(colV)){
    for(int st=0;st<statusLst.size();st++){
    colnum++;
    String stt=(String)statusLst.get(st);
    String bocolor=(String)sttColorCodeMap.get(stt);
    if(qty.equals("Y"))
    addCell(++cellCt, "QTY", styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, "CTS", styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, "AVG", styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, "RAP", styleHdr);
    if(age.equals("Y"))
    addCell(++cellCt, "AGE", styleHdr);
    if(hit.equals("Y"))
    addCell(++cellCt, "HIT", styleHdr);
    if(vlu.equals("Y"))
    addCell(++cellCt, "VLU", styleHdr);
    if(net_dis.equals("Y"))
    addCell(++cellCt, "NET_DIS", styleHdr);
    if(trf_cmp.equals("Y"))
    addCell(++cellCt, "TRF_CMP", styleHdr);
    }}}
    for(int st=0;st<statusLst.size();st++){
    String stt=(String)statusLst.get(st);
    String bocolor=(String)sttColorCodeMap.get(stt);
    if(qty.equals("Y"))
    addCell(++cellCt, "QTY", styleHdr);
    if(cts.equals("Y"))
    addCell(++cellCt, "CTS", styleHdr);
    if(avg.equals("Y"))
    addCell(++cellCt, "AVG", styleHdr);
    if(rap.equals("Y"))
    addCell(++cellCt, "RAP", styleHdr);
    if(age.equals("Y"))
    addCell(++cellCt, "AGE", styleHdr);
    if(hit.equals("Y"))
    addCell(++cellCt, "HIT", styleHdr);
    if(vlu.equals("Y"))
    addCell(++cellCt, "VLU", styleHdr);
    if(net_dis.equals("Y"))
    addCell(++cellCt, "NET_DIS", styleHdr);
    if(trf_cmp.equals("Y"))
    addCell(++cellCt, "TRF_CMP", styleHdr);
    }
    // sheet.createFreezePane(0, rowCt+1);

    for(int m=0;m< rowList.size();m++){
    String rowV = (String)rowList.get(m);
    boolean isDis = true;
    int totalQtyCol=0;
    int sumtotalQtyCol=0;
    int totalQtySold=0;
    int sumtotalQtySold=0;

    if(rowV.indexOf("+")!=-1 && !rowlprpTyp.equals("T") && !rowlprpTyp.equals("N"))
    isDis = false;
    if(rowV.indexOf("-")!=-1 && !rowlprpTyp.equals("T") && !rowlprpTyp.equals("N"))
    isDis = false;
    if(isDis){
    if(rowListGt.contains(rowV)){
    checkCol=key+"_row_"+loopcol;
    loopcol++;
    colnum=0;
    rownum++;
    row = newRow();
    addCell(++cellCt,rowV, styleHdr);
    for(int n=0;n< colList.size();n++){
    String colV = (String)colList.get(n);
    boolean isDis1 = true;

    if(colV.indexOf("+")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))
    isDis1 = false;
    if(colV.indexOf("-")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))
    isDis1 = false;
    if(isDis1){
    if(colListGt.contains(colV)){
    for(int st=0;st<statusLst.size();st++){
    colnum++;
    styleData = styleDataNormal;
    String stt=(String)statusLst.get(st);
    if(stt.equals("SOLD")){
    styleData =styleDataRed;
    }
    if(stt.equals("LAB")){
    styleData =styleDataGreen;
    }
    if(stt.equals("ASRT")){
    styleData =styleDataMaroon;
    }

    String keyQty = key+"@"+rowV+"@"+colV+"@"+stt+"@QTY";
    String keyCts = key+"@"+rowV+"@"+colV+"@"+stt+"@CTS";
    String keyAvg = key+"@"+rowV+"@"+colV+"@"+stt+"@AVG";
    String keyRap = key+"@"+rowV+"@"+colV+"@"+stt+"@RAP";
    String keyAge = key+"@"+rowV+"@"+colV+"@"+stt+"@AGE";
    String keyHit = key+"@"+rowV+"@"+colV+"@"+stt+"@HIT";
    String keyVlu = key+"@"+rowV+"@"+colV+"@"+stt+"@VLU";
    String keynet_dis = key+"@"+rowV+"@"+colV+"@"+stt+"@Net_Dis";
    String keytrf_cmp = key+"@"+rowV+"@"+colV+"@"+stt+"@TRF_CMP";
    String diffavgkey=key+"@"+rowV+"@"+colV;
    String diffqtykey=key+"@"+rowV+"@"+colV;
    String ctsId=key.trim()+"@CTS@"+rownum+"@"+colnum;
    String avgId=key.trim()+"@AVG@"+rownum+"@"+colnum;
    String rapId=key.trim()+"@RAP@"+rownum+"@"+colnum;
    String ageId=key.trim()+"@AGE@"+rownum+"@"+colnum;
    String hitId=key.trim()+"@HIT@"+rownum+"@"+colnum;
    String vluId=key.trim()+"@VLU@"+rownum+"@"+colnum;
    String valQty = util.nvl((String)dataDtl.get(keyQty.trim()));
    String valCts = util.nvl((String)dataDtl.get(keyCts.trim()));
    String valAvg = util.nvl((String)dataDtl.get(keyAvg.trim()));
    String valRap = util.nvl((String)dataDtl.get(keyRap.trim()));
    String valAge = util.nvl((String)dataDtl.get(keyAge.trim()));
    String valHit = util.nvl((String)dataDtl.get(keyHit.trim()));
    String valVlu = util.nvl((String)dataDtl.get(keyVlu.trim()));
        String valNet_dis = util.nvl((String)dataDtl.get(keynet_dis.trim()));
        String valTrf_cmp = util.nvl((String)dataDtl.get(keytrf_cmp.trim()));

    if(!valQty.equals("")){
    totalQtyCol=Integer.parseInt(valQty);
    sumtotalQtyCol+=totalQtyCol;
    String bocolor=(String)sttColorCodeMap.get(stt);
    style = "color: "+bocolor+";";
    tdstyle="";
    if(statusLst.contains("SOLD") && stt.equals("MKT") && inventoryDis.equals("Y")) {
    double mktQty=Double.parseDouble(valQty);
    String soldKeyQty = key+"@"+rowV+"@"+colV+"@SOLD@QTY";
    String soldQty = util.nvl2((String)dataDtl.get(soldKeyQty),"0");

    if(!soldQty.equals("")){
    perCentSold=Math.round((Double.parseDouble(soldQty)/days)*100);
    perCentSoldQty = Math.round((MIN_INVENTORY_DAYS*perCentSold)/100);
    perCentMinTol=perCentSoldQty-Math.round((perCentSoldQty/100)*TOLERANCE_MIN_PCT);
    perCentMaxTol=perCentSoldQty+Math.round((perCentSoldQty/100)*TOLERANCE_MAX_PCT);
    if(mktQty>=perCentMinTol && mktQty<=perCentMaxTol && mktQty!=0)
    styleData = styleDataYellowbg;
    if(mktQty<perCentMinTol && mktQty!=0)
    styleData =styleDataGreenbg;
    if(mktQty > perCentMaxTol && mktQty!=0)
    styleData =styleDataRedbg ;
    }
    }
        boolean displayper=false;
        if(defaultstatusLstsz>1){
        String firststt=util.nvl((String)defaultstatusLst.get(0));
        perdfvalAvg=0;perdfvalQty=0;
        if(stt.indexOf(firststt)<0){
        for(int df=1;df<defaultstatusLst.size();df++){
        String dfstt=util.nvl((String)defaultstatusLst.get(df));
           if(stt.indexOf(dfstt)>=0){
               diffavgkey=diffavgkey+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG";
               diffqtykey=diffqtykey+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY";
               String dfvalAvg = util.nvl((String)dataDtl.get(diffavgkey.trim()));
               String dfvalQty = util.nvl((String)dataDtl.get(diffqtykey.trim()));
               if(!valAvg.equals("") && !dfvalAvg.equals("")){
               perdfvalAvg=jsputil.roundTopercentage(Double.parseDouble(valAvg),Double.parseDouble(dfvalAvg));
               displayper=true;
        //                                  if(perdfvalAvg>0)
        //                                  styleavg = "color: green";
               }
               if(!valQty.equals("") && !dfvalQty.equals("")){
               perdfvalQty=jsputil.roundTopercentage(Double.parseDouble(valQty),Double.parseDouble(dfvalQty));
        //                                  if(perdfvalQty>0)
        //                                  styleqty = "color: green";
               }
               break;
           }
        }
        }
        }

        if(qty.equals("Y")){
        if(!perQTY.equals("") && displayper){
        valQty+=" | "+perdfvalQty;
        if(perdfvalQty>0)
        addCell(++cellCt, valQty , styleDataGreen);
        else
        addCell(++cellCt, valQty , styleDataRed); 
        }else{
        addCell(++cellCt, valQty , styleData);
        }
        }
    if(cts.equals("Y"))
    addCell(++cellCt, valCts, styleData,"");

        if(avg.equals("Y")){
        if(!perAVG.equals("") && displayper){
        valAvg+=" | "+perdfvalAvg;
        if(perdfvalAvg>0)
        addCell(++cellCt, valAvg , styleDataGreen);
        else
        addCell(++cellCt, valAvg , styleDataRed); 
        }else{
        addCell(++cellCt, valAvg , styleData);
        }
        }
    
    if(rap.equals("Y"))
    addCell(++cellCt,valRap, styleData,"");
    if(age.equals("Y"))
    addCell(++cellCt, valAge, styleData,"");
    if(hit.equals("Y"))
    addCell(++cellCt, valHit , styleData,"");
    if(vlu.equals("Y"))
    addCell(++cellCt, valVlu , styleData,"");
        if(net_dis.equals("Y"))
        addCell(++cellCt, valNet_dis , styleData,"");
        if(trf_cmp.equals("Y"))
        addCell(++cellCt, valTrf_cmp , styleData,"");
    }else{
    if(qty.equals("Y"))
    addCell(++cellCt, "" , styleHdr,"");
    if(cts.equals("Y"))
    addCell(++cellCt, "", styleHdr,"");
    if(avg.equals("Y"))
    addCell(++cellCt,"", styleHdr,"");
    if(rap.equals("Y"))
    addCell(++cellCt,"", styleHdr,"");
    if(age.equals("Y"))
    addCell(++cellCt, "", styleHdr,"");
    if(hit.equals("Y"))
    addCell(++cellCt, "" , styleHdr,"");
    if(vlu.equals("Y"))
    addCell(++cellCt, "" , styleHdr,"");
        if(net_dis.equals("Y"))
        addCell(++cellCt, "" , styleHdr,"");
        if(trf_cmp.equals("Y"))
        addCell(++cellCt, "" , styleHdr,"");
    }
    }}}}
    for(int sti=0;sti<statusLst.size();sti++){
    styleData = styleDataNormal;
    String stt=(String)statusLst.get(sti);
    if(stt.equals("SOLD")){
    styleData =styleDataRed;
    }
    if(stt.equals("LAB")){
    styleData =styleDataGreen;
    }
    if(stt.equals("ASRT")){
    styleData =styleDataMaroon;
    }
    String getcolttlqty=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@QTY"),"0");
    String getcolttlcts=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@CTS"),"0");
    String getcolttlavg=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@AVG"),"0");
    String getcolttlrap=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@RAP"),"0");
    String getcolttlage=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@AGE"),"0");
    String getcolttlhit=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@HIT"),"0");
    String getcolttlvlu=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@VLU"),"0");
        String getcolttlnet_dis=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@Net_Dis"),"0");
        String getcolttltrf_cmp=util.nvl2((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@TRF_CMP"),"0");
    if(!getcolttlqty.equals("")){
    tdstyle="";
    String bocolor=(String)sttColorCodeMap.get(stt);
    // piePrpDtl.put(key+"_"+stt+"_"+rowV+"_"+"COL",getcolttlqty);
    style = "color: "+bocolor+";";
    if(statusLst.contains("SOLD") && stt.equals("MKT") && inventoryDis.equals("Y")) {
    double mktQty=Double.parseDouble(getcolttlqty);
    String soldKeyQty = key+"@"+rowV+"@ALL@"+"SOLD@QTY";
    String soldQty = util.nvl2((String)dataDtl.get(soldKeyQty),"0");
    if(!soldQty.equals("")){
    perCentSold=Math.round((Double.parseDouble(soldQty)/days)*100);
    perCentSoldQty = Math.round((MIN_INVENTORY_DAYS*perCentSold)/100);
    perCentMinTol=perCentSoldQty-Math.round((perCentSoldQty/100)*TOLERANCE_MIN_PCT);
    perCentMaxTol=perCentSoldQty+Math.round((perCentSoldQty/100)*TOLERANCE_MAX_PCT);
    if(mktQty>=perCentMinTol && mktQty<=perCentMaxTol && mktQty!=0)
    styleData = styleDataYellowbg;
    if(mktQty<perCentMinTol && mktQty!=0)
    styleData =styleDataGreenbg;
    if(mktQty > perCentMaxTol && mktQty!=0)
    styleData =styleDataRedbg ;

    }
    }
        boolean displayper=false;
             String styleqty = "color: red";
             String styleavg = "color: red";
             if(defaultstatusLstsz>1){
             String firststt=util.nvl((String)defaultstatusLst.get(0));
             perdfvalAvg=0;perdfvalQty=0;
            if(stt.indexOf(firststt)<0){
            for(int df=1;df<defaultstatusLst.size();df++){
            String dfstt=util.nvl((String)defaultstatusLst.get(df));
                if(stt.indexOf(dfstt)>=0){
                    String dfgetcolttlqty=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY"));
                    String dfgetcolttlavg=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG"));
                    if(!getcolttlavg.equals("") && !dfgetcolttlavg.equals("")){
                    perdfvalAvg=jsputil.roundTopercentage(Double.parseDouble(getcolttlavg),Double.parseDouble(dfgetcolttlavg));
                    displayper=true;
                    if(perdfvalAvg>0)
                    styleavg = "color: green";
                    }
                    if(!getcolttlqty.equals("") && !dfgetcolttlqty.equals("")){
                    perdfvalQty=jsputil.roundTopercentage(Double.parseDouble(getcolttlqty),Double.parseDouble(dfgetcolttlqty));
                    if(perdfvalQty>0)
                    styleqty = "color: green";
                    }
                    break;
                }
            }
            }
            }
        if(qty.equals("Y")){
        if(!perQTY.equals("") && displayper){
        getcolttlqty+=" | "+perdfvalQty;
        if(perdfvalQty>0)
        addCell(++cellCt, getcolttlqty , styleDataGreen);
        else
        addCell(++cellCt, getcolttlqty , styleDataRed); 
        }else{
        addCell(++cellCt, getcolttlqty , styleData);
        }
        }
    if(cts.equals("Y"))
    addCell(++cellCt, getcolttlcts, styleData,"");
//    if(avg.equals("Y")){
//        if(!perAVG.equals("") && displayper){
//         getcolttlavg+=" | "+perdfvalAvg;
//        }
//    addCell(++cellCt,getcolttlavg, styleData);
//    }
        if(avg.equals("Y")){
        if(!perAVG.equals("") && displayper){
        getcolttlavg+=" | "+perdfvalAvg;
        if(perdfvalAvg>0)
        addCell(++cellCt, getcolttlavg , styleDataGreen);
        else
        addCell(++cellCt, getcolttlavg , styleDataRed); 
        }else{
        addCell(++cellCt, getcolttlavg , styleData);
        }
        }
    if(rap.equals("Y"))
    addCell(++cellCt,getcolttlrap, styleData,"");
    if(age.equals("Y"))
    addCell(++cellCt, getcolttlage, styleData,"");
    if(hit.equals("Y"))
    addCell(++cellCt, getcolttlhit , styleData,"");
    if(vlu.equals("Y"))
    addCell(++cellCt, getcolttlvlu , styleData,"");
        if(net_dis.equals("Y"))
        addCell(++cellCt, getcolttlnet_dis , styleData,"");
        if(trf_cmp.equals("Y"))
        addCell(++cellCt, getcolttltrf_cmp , styleData,"");
    }else{
    }
    }
    }}
    }
    row = newRow();
    addCell(++cellCt, "Total" , styleHdr);
    colnum=0;
    for(int j=0;j< colList.size();j++){
    String colV = (String)colList.get(j);
    if(colListGt.contains(colV)){
    for(int st=0;st<statusLst.size();st++){
    colnum++;
    styleData = styleDataNormal;
    String stt=(String)statusLst.get(st);
    if(stt.equals("SOLD")){
    styleData =styleDataRed;
    }
    if(stt.equals("LAB")){
    styleData =styleDataGreen;
    }
    if(stt.equals("ASRT")){
    styleData =styleDataMaroon;
    }
    String getclrttlqty=util.nvl2((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@QTY"),"0");
    String getclrttlcts=util.nvl2((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@CTS"),"0");
    String getclrttlavg=util.nvl2((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@AVG"),"0");
    String getclrttlrap=util.nvl2((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@RAP"),"0");
    String getclrttlage=util.nvl2((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@AGE"),"0");
    String getclrttlhit=util.nvl2((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@HIT"),"0");
    String getclrttlvlu=util.nvl2((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@VLU"),"0");
        String getclrttlnet_dis=util.nvl2((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@Net_Dis"),"0");
        String getclrttltrf_cmp=util.nvl2((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@TRF_CMP"),"0");
    String GCLRCTSID="GCLRIDCTS@"+colnum;
    String GCLRAVGID="GCLRIDAVG@"+colnum;
    String GCLRRAPID="GCLRIDRAP@"+colnum;
    String GCLRAGEID="GCLRIDAGE@"+colnum;
    String GCLRHITID="GCLRIDHIT@"+colnum;
    String GCLRVLUID="GCLRIDVLU@"+colnum;
    // if(!getclrttlqty.equals("")){
    // piePrpDtl.put(key+"_"+stt+"_"+colV+"_"+"CLR",getclrttlqty);
    // }
    if(!getclrttlqty.equals("")){
    String bocolor=(String)sttColorCodeMap.get(stt);
    style = "color: "+bocolor+";";
    tdstyle="";
    if(statusLst.contains("SOLD") && stt.equals("MKT") && inventoryDis.equals("Y")) {
    double mktQty=Double.parseDouble(getclrttlqty);
    String soldKeyQty = key+"@ALL@"+colV+"@"+"SOLD@QTY";
    String soldQty = util.nvl2((String)dataDtl.get(soldKeyQty),"0");
    if(!soldQty.equals("")){
    perCentSold=Math.round((Double.parseDouble(soldQty)/days)*100);
    perCentSoldQty = Math.round((MIN_INVENTORY_DAYS*perCentSold)/100);
    perCentMinTol=perCentSoldQty-Math.round((perCentSoldQty/100)*TOLERANCE_MIN_PCT);
    perCentMaxTol=perCentSoldQty+Math.round((perCentSoldQty/100)*TOLERANCE_MAX_PCT);
    if(mktQty>=perCentMinTol && mktQty<=perCentMaxTol && mktQty!=0)
    styleData = styleDataYellowbg;
    if(mktQty<perCentMinTol && mktQty!=0)
    styleData =styleDataGreenbg;
    if(mktQty > perCentMaxTol && mktQty!=0)
    styleData =styleDataRedbg ;

    }
    }
    }
        boolean displayper=false;
             String styleqty = "color: red";
             String styleavg = "color: red";
             if(defaultstatusLstsz>1){
             String firststt=util.nvl((String)defaultstatusLst.get(0));
             perdfvalAvg=0;perdfvalQty=0;
            if(stt.indexOf(firststt)<0){
            for(int df=1;df<defaultstatusLst.size();df++){
            String dfstt=util.nvl((String)defaultstatusLst.get(df));
                if(stt.indexOf(dfstt)>=0){
                    String dfgetclrttlqty=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY"));
                    String dfgetclrttlqtyavg=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG"));
                    if(!getclrttlavg.equals("") && !dfgetclrttlqtyavg.equals("")){
                    perdfvalAvg=jsputil.roundTopercentage(Double.parseDouble(getclrttlavg),Double.parseDouble(dfgetclrttlqtyavg));

                    displayper=true;
                    if(perdfvalAvg>0)
                    styleavg = "color: green";
                    }
                    if(!getclrttlqty.equals("") && !dfgetclrttlqty.equals("")){
                    perdfvalQty=jsputil.roundTopercentage(Double.parseDouble(getclrttlqty),Double.parseDouble(dfgetclrttlqty));
                    if(perdfvalQty>0)
                    styleqty = "color: green";
                    }
                    break;
                }
            }
            }
             }
//        if(!perQTY.equals("") && displayper){
//        getclrttlqty+=" | "+perdfvalQty;
//        }
//    if(qty.equals("Y"))
//    addCell(++cellCt, getclrttlqty , styleData);
        if(qty.equals("Y")){
        if(!perQTY.equals("") && displayper){
        getclrttlqty+=" | "+perdfvalQty;
        if(perdfvalQty>0)
        addCell(++cellCt, getclrttlqty , styleDataGreen);
        else
        addCell(++cellCt, getclrttlqty , styleDataRed); 
        }else{
        addCell(++cellCt, getclrttlqty , styleData);
        }
        }
    if(cts.equals("Y"))
    addCell(++cellCt, getclrttlcts, styleData,"");
//        if(avg.equals("Y")){
//            if(!perAVG.equals("") && displayper){
//             getclrttlavg+=" | "+perdfvalAvg;
//            }
//    addCell(++cellCt,getclrttlavg, styleData);
//        }
        if(avg.equals("Y")){
        if(!perAVG.equals("") && displayper){
        getclrttlavg+=" | "+perdfvalAvg;
        if(perdfvalAvg>0)
        addCell(++cellCt, getclrttlavg , styleDataGreen);
        else
        addCell(++cellCt, getclrttlavg , styleDataRed); 
        }else{
        addCell(++cellCt, getclrttlavg , styleData);
        }
        }
    if(rap.equals("Y"))
    addCell(++cellCt,getclrttlrap, styleData,"");
    if(age.equals("Y"))
    addCell(++cellCt, getclrttlage, styleData,"");
    if(hit.equals("Y"))
    addCell(++cellCt, getclrttlhit , styleData,"");
    if(vlu.equals("Y"))
    addCell(++cellCt, getclrttlvlu , styleData,"");
        if(net_dis.equals("Y"))
        addCell(++cellCt, getclrttlnet_dis , styleData,"");
        if(trf_cmp.equals("Y"))
        addCell(++cellCt, getclrttltrf_cmp , styleData,"");
    }
    }}
    for(int st=0;st<statusLst.size();st++){
    styleData = styleDataNormal;
    String stt=(String)statusLst.get(st);
    if(stt.equals("SOLD")){
    styleData =styleDataRed;
    }
    if(stt.equals("LAB")){
    styleData =styleDataGreen;
    }
    if(stt.equals("ASRT")){
    styleData =styleDataMaroon;
    }
    String getttlqty=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@QTY"),"0");
    String getttlcts=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@CTS"),"0");
    String getttlavg=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@AVG"),"0");
    String getttlrap=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@RAP"),"0");
    String getttlage=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@AGE"),"0");
    String getttlhit=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@HIT"),"0");
    String getttlvlu=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@VLU"),"0");
        String getttlnet_dis=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@Net_Dis"),"0");
        String getttltrf_cmp=util.nvl2((String)getGrandTotalList.get(key+"@"+stt+"@TRF_CMP"),"0");
    String GCTSID="GIDCTS@"+colnum+"@"+st;
    String GAVGID="GIDAVG@"+colnum+"@"+st;
    String GRAPID="GIDRAP@"+colnum+"@"+st;
    String GAGEID="GIDAGE@"+colnum+"@"+st;
    String GHITID="GIDHIT@"+colnum+"@"+st;
    String bocolor=(String)sttColorCodeMap.get(stt);
    style = "color: "+bocolor+";";

    if(!getttlqty.equals("")){
    tdstyle="";
    if(statusLst.contains("SOLD") && stt.equals("MKT") && inventoryDis.equals("Y")) {
    double mktQty=Double.parseDouble(getttlqty);
    String soldKeyQty = key+"@SOLD@QTY";
    String soldQty = util.nvl2((String)getGrandTotalList.get(soldKeyQty),"0");
    if(!soldQty.equals("")){
    perCentSold=Math.round((Double.parseDouble(soldQty)/days)*100);
    perCentSoldQty = Math.round((MIN_INVENTORY_DAYS*perCentSold)/100);
    perCentMinTol=perCentSoldQty-Math.round((perCentSoldQty/100)*TOLERANCE_MIN_PCT);
    perCentMaxTol=perCentSoldQty+Math.round((perCentSoldQty/100)*TOLERANCE_MAX_PCT);
    if(mktQty>=perCentMinTol && mktQty<=perCentMaxTol && mktQty!=0)
    styleData = styleDataYellowbg;
    if(mktQty<perCentMinTol && mktQty!=0)
    styleData =styleDataGreenbg;
    if(mktQty > perCentMaxTol && mktQty!=0)
    styleData =styleDataRedbg ;
    }
    }
    }
        boolean displayper=false;
             String styleqty = "color: red";
             String styleavg = "color: red";
             if(defaultstatusLstsz>1){
             String firststt=util.nvl((String)defaultstatusLst.get(0));
             perdfvalAvg=0;perdfvalQty=0;
            if(stt.indexOf(firststt)<0){
            for(int df=1;df<defaultstatusLst.size();df++){
            String dfstt=util.nvl((String)defaultstatusLst.get(df));
                if(stt.indexOf(dfstt)>=0){
                    String dfgetttlqty=util.nvl((String)getGrandTotalList.get(key+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY"));
                    String dfgetttlavg=util.nvl((String)getGrandTotalList.get(key+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG"));
                    if(!getttlavg.equals("") && !dfgetttlavg.equals("")){
                    perdfvalAvg=jsputil.roundTopercentage(Double.parseDouble(getttlavg),Double.parseDouble(dfgetttlavg));
                    displayper=true;
                    if(perdfvalAvg>0)
                    styleavg = "color: green";
                    }
                    if(!getttlqty.equals("") && !dfgetttlqty.equals("")){
                    perdfvalQty=jsputil.roundTopercentage(Double.parseDouble(getttlqty),Double.parseDouble(dfgetttlqty));
                    if(perdfvalQty>0)
                    styleqty = "color: green";
                    }
                    break;
                }
            }
            }
            }
//        if(!perQTY.equals("") && displayper){
//        getttlqty+=" | "+perdfvalQty;
//        }
//    if(qty.equals("Y"))
//    addCell(++cellCt, getttlqty , styleData);
        if(qty.equals("Y")){
        if(!perQTY.equals("") && displayper){
        getttlqty+=" | "+perdfvalQty;
        if(perdfvalQty>0)
        addCell(++cellCt, getttlqty , styleDataGreen);
        else
        addCell(++cellCt, getttlqty , styleDataRed); 
        }else{
        addCell(++cellCt, getttlqty , styleData);
        }
        }
    if(cts.equals("Y"))
    addCell(++cellCt, getttlcts, styleData,"");
//        if(avg.equals("Y")){
//            if(!perAVG.equals("") && displayper){
//             getttlavg+=" | "+perdfvalAvg;
//            }
//    addCell(++cellCt,getttlavg, styleData);
//        }
        if(avg.equals("Y")){
        if(!perAVG.equals("") && displayper){
        getttlavg+=" | "+perdfvalAvg;
        if(perdfvalAvg>0)
        addCell(++cellCt, getttlavg , styleDataGreen);
        else
        addCell(++cellCt, getttlavg , styleDataRed); 
        }else{
        addCell(++cellCt, getttlavg , styleData);
        }
        }
    if(rap.equals("Y"))
    addCell(++cellCt,getttlrap, styleData,"");
    if(age.equals("Y"))
    addCell(++cellCt, getttlage, styleData,"");
    if(hit.equals("Y"))
    addCell(++cellCt, getttlhit , styleData,"");
    if(vlu.equals("Y"))
    addCell(++cellCt, getttlvlu , styleData,"");
        if(net_dis.equals("Y"))
        addCell(++cellCt, getttlnet_dis , styleData,"");
        if(trf_cmp.equals("Y"))
        addCell(++cellCt, getttltrf_cmp , styleData,"");
    }
    }
    Iterator it=autoColums.iterator();
    while(it.hasNext()) {
    String value=(String)it.next();
    int colId = Integer.parseInt(value);
    sheet.autoSizeColumn((short)colId, true);
    }
    }

    return hwb;
    }
    
    
    
       public HSSFWorkbook getGenXlcreateXLSearchHistory(ArrayList srchIds, HashMap srchList, HashMap srchVal,ArrayList srchPrp) {
        
        hwb = new HSSFWorkbook();
        autoColums = new TreeSet();
        String fontNm = "Cambria";
        fontHdr = hwb.createFont();
        fontHdr.setFontHeightInPoints((short)10);
        fontHdr.setFontName(fontNm);
        fontHdr.setColor(HSSFColor.BLACK.index);
        fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
        styleHdr = hwb.createCellStyle();
        styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleHdr.setFont(fontHdr);
        
       
        fontData = hwb.createFont();
        fontData.setFontHeightInPoints((short)10);
        fontData.setFontName(fontNm);
        styleData = hwb.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setFont(fontData);
        
        HSSFCellStyle numStyleData = hwb.createCellStyle();
        numStyleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        numStyleData.setFont(fontData);
        numStyleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
        HSSFDataFormat df = hwb.createDataFormat();
        numStyleData.setDataFormat(df.getFormat("#0.00#"));
        sheet = addSheet();
        HashMap mprp = info.getMprp();
        
        if(srchIds!=null && srchIds.size()>0 ){
        
            row = newRow();
            
            addCell(++cellCt, "User", styleHdr);
            addCell(++cellCt, "Buyer name", styleHdr);
            addCell(++cellCt, "Date", styleHdr);
            addCell(++cellCt, "Id", styleHdr);
            
            for(int i=0; i < srchPrp.size(); i++) {
                    ArrayList prpVec = (ArrayList)srchPrp.get(i);
                    String lprp = (String)prpVec.get(0);
                    String flag = (String)prpVec.get(1);
                    String prpDsc = (String)mprp.get(lprp);
                   if(flag.equals("M"))
                    {
                        addCell(++cellCt, prpDsc, styleHdr);
                    }
                    else
                    {   
                        int span = 1;
                        int currCell = ++cellCt;
                        CellRangeAddress rang = mergeCell(rowCt, rowCt, currCell, currCell+span);
                        addMergeCell(currCell,prpDsc, styleHdr, rang);
                        cellCt = currCell+span;
                    }
            }
            
            row = newRow();
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "", styleHdr);
            for(int i=0; i < srchPrp.size(); i++) {
                    ArrayList prpVec = (ArrayList)srchPrp.get(i);
                    String lprp = (String)prpVec.get(0);
                    String flag = (String)prpVec.get(1);
                   if(flag.equals("M"))
                    {
                        addCell(++cellCt, "", styleHdr);
                    }
                    else
                    {   
                        addCell(++cellCt, "From", styleHdr);
                        addCell(++cellCt, " To ", styleHdr);
                    }
            }
            
            
            for(int i=0; i < srchIds.size(); i++)    {
                    String l_srchId = (String)srchIds.get(i);
                    HashMap msrcList =(HashMap)srchList.get(l_srchId);
                    String dte = util.nvl((String)srchVal.get(l_srchId+"_DTE"));
                    String usr = util.nvl((String)srchVal.get(l_srchId+"_USR"));
                    String byr = util.nvl((String)srchVal.get(l_srchId+"_BYR"));
                    if(msrcList!=null)
                    {
                    row = newRow();
                    addCell(++cellCt,usr, styleData);
                    addCell(++cellCt, byr, styleData);
                    addCell(++cellCt, dte, styleData);
                    addCell(++cellCt, l_srchId, styleData);
                        for(int j=0; j < srchPrp.size(); j++) {
                            
                                ArrayList prpVec = (ArrayList)srchPrp.get(j);
                                String lprp = (String)prpVec.get(0);
                                String flag = (String)prpVec.get(1);
                            if(flag.equals("M")){
                                addCell(++cellCt,util.nvl2((String)msrcList.get(util.nvl(lprp)),""), styleData);        
                            } else if(flag.equals("S")) {
                                String vfr = util.nvl2((String)srchVal.get(l_srchId+"_"+lprp+"_fr"), ""); 
                                String vto = util.nvl2((String)srchVal.get(l_srchId+"_"+lprp+"_to"), ""); 
                                addCell(++cellCt,vfr, styleData);
                                addCell(++cellCt,vto, styleData);
                            }
                                
                            }
                    
            
                    }
                
            }
            
            
        }    
         
        Iterator it = autoColums.iterator();
        while(it.hasNext()) {
        String value=(String)it.next();
        int colId = Integer.parseInt(value);
        sheet.autoSizeColumn((short)colId, true);

        }
        
        
        return hwb;
       
    }
       
  public HSSFWorkbook getGenXlObj(HashMap excelDtl, HttpServletRequest req) {
     session = req.getSession(false);
     gtMgr = (GtMgr)session.getAttribute("gtMgr");

  hwb = new HSSFWorkbook();
  autoColums = new TreeSet();
   HashMap mprp = info.getMprp();
   sheet = addSheet();
   row = newRow();
    String fontNm = "Cambria";
    fontHdr = hwb.createFont();
    fontHdr.setFontHeightInPoints((short)10);
    fontHdr.setFontName(fontNm);
    fontHdr.setColor(HSSFColor.BLACK.index);
    fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
    styleHdr = hwb.createCellStyle();
    styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleHdr.setFont(fontHdr);
    
    
    fontData = hwb.createFont();
    fontData.setFontHeightInPoints((short)10);
    fontData.setFontName(fontNm);
    styleData = hwb.createCellStyle();
    styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleData.setFont(fontData);
    
    fontDataBig = hwb.createFont();
    fontDataBig.setFontHeightInPoints((short)10);
    fontDataBig.setFontName(fontNm);
    fontDataBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    
    styleBig = hwb.createCellStyle();
    styleBig.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    styleBig.setFont(fontDataBig);
    styleBig.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
    styleBig.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
    styleBig.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
    styleBig.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
    styleBig.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleBig.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleBig.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleBig.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
    styleBig.setFillForegroundColor(HSSFColor.BLUE.index);
    String logo = util.nvl((String)excelDtl.get("LOGO"));
    if(logo.equals("Y")){
    addheader(req);
    }
    row = newRow();

  HashMap itemHdrDsc = new HashMap();
    itemHdrDsc.put("emp","Employee Name");
    itemHdrDsc.put("issIdn","Issue Id");
    itemHdrDsc.put("vnm","Packet No");
    ArrayList itemHdr = (ArrayList)excelDtl.get("HDR");
    for(int i=0;i<itemHdr.size();i++){
    HashMap hdrDtl = (HashMap)itemHdr.get(i);
   String hdr = (String)hdrDtl.get("hdr");
    String typ = (String)hdrDtl.get("typ");
  String hdrDsc= "";
    if(typ.equals("P")){
     
    String dp = (String)hdrDtl.get("dp");
     hdrDsc = (String)mprp.get(hdr+""+dp);
       if(hdrDsc==null)
         hdrDsc = hdr;
         
     }else{
     hdrDsc = (String)itemHdrDsc.get(hdr);
      if(hdrDsc==null)
        hdrDsc = hdr;
     }


    addCell(++cellCt, hdrDsc, styleHdr);
    }
     String lstNme = (String)excelDtl.get("lstNme");
     HashMap pktDtlMap = (HashMap)gtMgr.getValue(lstNme);
    LinkedHashMap pktDtl = new LinkedHashMap(pktDtlMap); 

     ArrayList stkIdnList = (ArrayList)gtMgr.getValue(lstNme+"_ALLLST");
     if(stkIdnList==null || stkIdnList.size()==0){
          stkIdnList  = new ArrayList();
        Set<String> keys = pktDtl.keySet();
         for(String key: keys){
            stkIdnList.add(key);
         }

      }

     for(int i=0; i < stkIdnList.size(); i++ ){
        String stkIdn = (String)stkIdnList.get(i);
        GtPktDtl stockPkt = (GtPktDtl)pktDtl.get(stkIdn);
         row= newRow();
          for(int j=0;j<itemHdr.size();j++){
         HashMap hdrDtl = (HashMap)itemHdr.get(j);
         String hdr = (String)hdrDtl.get("hdr");
         String val =   stockPkt.getValue(hdr);
         
         addCell(++cellCt, val, styleData);
    
         }


      }
    Iterator it = autoColums.iterator();
    while(it.hasNext()) {
    String value=(String)it.next();
    int colId = Integer.parseInt(value);
    sheet.autoSizeColumn((short)colId, true);

    }
    
   return hwb;
  }
  
  public void addheader(HttpServletRequest req){
                    
                      HashMap exlFormat = util.EXLFormat();

                  
                    String logoNme = (String)exlFormat.get("LOGO");
                    
                 if(!logoNme.equals("")){
                      try {
                          String servPath = req.getRealPath("/images/clientsLogo");
      
                           if(servPath.indexOf("/") > -1)
                           servPath += "/" ;
                           else {
                                                 //servPath = servPath.replace('\', '\\');
                                                 servPath += "\\" ;
                             }
                          FileInputStream fis=new FileInputStream(servPath+logoNme);
                          ByteArrayOutputStream img_bytes = new ByteArrayOutputStream();
                          int b;
                          while ((b = fis.read()) != -1)
                              img_bytes.write(b);
                          fis.close();
                          int imgWdt = 241, imgHt = 85;
                         
                          row = sheet.createRow((short)++rowCt);
      
                          HSSFClientAnchor anchor =
                              new HSSFClientAnchor(1, 1, imgWdt, imgHt, (short)9, rowCt,
                                                   (short)(7), (5));
      
                          rowCt =5;
                          cellCt = 5;
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
      
                 }
                 
                 String add1 = (String)exlFormat.get("ADD1");
                 if(!add1.equals("")){
                     rowCt++;
                           row = sheet.createRow((short)2);
                           cell = row.createCell(11);
                           cell.setCellValue(add1);
                           cell.setCellStyle(styleBig);
                           sheet.addMergedRegion(merge(2, 2, 11, 26));
                       
                 }
                 String add2 = (String)exlFormat.get("ADD2");
                 if(!add2.equals("")){
                   
                           row = sheet.createRow((short)3);
                           cell = row.createCell(11);
                           cell.setCellValue(add2);
                           cell.setCellStyle(styleBig);
                           sheet.addMergedRegion(merge(3, 3, 11, 26));
                       
                  }
                 
                 

  }

    public HSSFWorkbook getGenXlRough(ArrayList itemHdr , HashMap itemDtls, ArrayList itemHdrPkt , HashMap itemDtlsPkt ,ArrayList stockIdnLst ) {
            
            hwb = new HSSFWorkbook();
            autoColums = new TreeSet();
            String fontNm = "Cambria";
            fontHdr = hwb.createFont();
            fontHdr.setFontHeightInPoints((short)10);
            fontHdr.setFontName(fontNm);
            fontHdr.setColor(HSSFColor.BLACK.index);
            fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
            styleHdr = hwb.createCellStyle();
            styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleHdr.setFont(fontHdr);
            
           
            fontData = hwb.createFont();
            fontData.setFontHeightInPoints((short)10);
            fontData.setFontName(fontNm);
            styleData = hwb.createCellStyle();
            styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleData.setFont(fontData);
            
            HSSFCellStyle numStyleData = hwb.createCellStyle();
            numStyleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            numStyleData.setFont(fontData);
            numStyleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
            numStyleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
            numStyleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
            numStyleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
            numStyleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
            numStyleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
            numStyleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
            numStyleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
              HSSFDataFormat df = hwb.createDataFormat();
              numStyleData.setDataFormat(df.getFormat("#0.00#"));
            sheet = addSheet();
            String  genexcel = util.nvl((String)info.getDmbsInfoLst().get("GENEXCELDSC"));
            ArrayList imagelistDtl= (info.getImagelistDtl() == null)?new ArrayList():(ArrayList)info.getImagelistDtl();
            HashMap mprp = info.getMprp();
            HashMap prpDsc=new HashMap();
            int itemDtlsSize=itemDtls.size();
            int itemDtlsPktSize=itemDtlsPkt.size();
            prpDsc.put("SALEDTE", "Sale Date");
            prpDsc.put("APPDTE", "Approve Date");
            prpDsc.put("ACTSTT", "Actual Status");
            prpDsc.put("STT", "STATUS");
            prpDsc.put("VNM", "PACKET ID");
            prpDsc.put("memoIdn", "Memo ID");
            prpDsc.put("currentstt", "Live Status");
            prpDsc.put("iss_stt", "Opening Status");
            prpDsc.put("STK_IDN", "STOCK ID");
            prpDsc.put("QTY", "QUANTITY");
            prpDsc.put("BYR", "BUYER");
            prpDsc.put("SalAmt", "Sale Amount");
            prpDsc.put("AADATNME", "Aadat Name");
            prpDsc.put("AADATCOMM", "Aadat Commission");
            prpDsc.put("curr", "Price Revised from MktTrns to Curr");
            prpDsc.put("DATEAMT", "Date selection To Curr Amt %");
            prpDsc.put("QUOT", "Sale Rte");
            prpDsc.put("SLBACK", "Sale Back");
            prpDsc.put("AMT", "Amount");
            prpDsc.put("EMP", "Sale Ex");
            prpDsc.put("STK_IDN", "N");
            prpDsc.put("QTYT", "N");
            prpDsc.put("QuotT", "N");
            prpDsc.put("SLBACKT", "N");
            prpDsc.put("AMTT", "N");
            prpDsc.put("slbackT", "N");
            prpDsc.put("amtT", "N");
            prpDsc.put("diffT", "N");
            prpDsc.put("plT", "N");
            prpDsc.put("RateT", "N");
            prpDsc.put("RateAmtT", "N");
            prpDsc.put("RateDiscT", "N");
            prpDsc.put("RapRteT", "N");
            prpDsc.put("DisT", "N");
            prpDsc.put("Prc / CrtT", "N");
            prpDsc.put("ByrDisT", "N");
            prpDsc.put("SALE RTET", "N");
            prpDsc.put("AMOUNTT", "N");
            prpDsc.put("RAPVLUT", "N");
            prpDsc.put("DATEAMT", "Date selection To Curr Amt %");
            prpDsc.put("VLU", "VLU");
            prpDsc.put("RAP VLU", "RAP VLU");
            prpDsc.put("VluT", "N");
            prpDsc.put("Rap VluT", "N");
                
            int n=0;
            
            
            
            if(stockIdnLst.size()>0){
            for (int p = 0; p < stockIdnLst.size(); p++) {
            String stkIdn = (String)stockIdnLst.get(p);
            row = newRow();    
            HashMap pktDtl = (HashMap)itemDtls.get(stkIdn);
            if(pktDtl != null && pktDtl.size()>0){    
            row = newRow();
            for (int j = 0; j < itemHdr.size(); j++) {
                 String hdr = String.valueOf(itemHdr.get(j)).toUpperCase();
                 if(prpDsc.containsKey(hdr)){
                     hdr=(String)prpDsc.get(hdr);
                     }
                 if(hdr.indexOf("$") > -1){
                     hdr=hdr.replaceAll("\\$"," ");
                    }
                if(genexcel.equals("Y")){
                 String pDsc = util.nvl((String)mprp.get(hdr+"D"));
                if(!pDsc.equals("")){
                    hdr=pDsc;   
                }
                    }
                 addCell(++cellCt, hdr, styleHdr);
             }
            
            
            row = newRow();
            for (int i = 0; i < itemHdr.size(); i++) {
            String hdr = (String)itemHdr.get(i);
            String mprptyp = util.nvl((String)mprp.get(hdr+"T"));
            String fldVal = util.nvl((String)pktDtl.get(hdr));
              fldVal = fldVal.replace(",","");
            if(mprptyp.equals(""))
            mprptyp = util.nvl((String)prpDsc.get(hdr+"T"));    
            if((mprptyp.equals("N") && !fldVal.equals("")) && fldVal.indexOf("#")==-1) {
                    if(fldVal.equals("NA"))
                    fldVal="0";
                    cell = row.createCell(++cellCt);
                    cell.setCellStyle(numStyleData);
                    int colNum = cell.getColumnIndex();
                    cell.setCellValue(Double.parseDouble(fldVal));
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    autoColums.add(Integer.toString(colNum));
            }
            else{   
            addCell(++cellCt, fldVal, styleData);
            }
            }
            }
            
            
            
            pktDtl = (HashMap)itemDtlsPkt.get(stkIdn);
            if(pktDtl != null && pktDtl.size()>0){    
            row = newRow();
            for (int j = 0; j < itemHdrPkt.size(); j++) {
                 String hdr = String.valueOf(itemHdrPkt.get(j)).toUpperCase();
                 if(prpDsc.containsKey(hdr)){
                     hdr=(String)prpDsc.get(hdr);
                     }
                 if(hdr.indexOf("$") > -1){
                     hdr=hdr.replaceAll("\\$"," ");
                    }
                if(genexcel.equals("Y")){
                 String pDsc = util.nvl((String)mprp.get(hdr+"D"));
                if(!pDsc.equals("")){
                    hdr=pDsc;   
                }
                    }
                 addCell(++cellCt, hdr, styleHdr);
             }
            
            
            row = newRow();
            for (int i = 0; i < itemHdrPkt.size(); i++) {
            String hdr = (String)itemHdrPkt.get(i);
            String mprptyp = util.nvl((String)mprp.get(hdr+"T"));
            String fldVal = util.nvl((String)pktDtl.get(hdr));
              fldVal = fldVal.replace(",","");
            if(mprptyp.equals(""))
            mprptyp = util.nvl((String)prpDsc.get(hdr+"T"));    
            if((mprptyp.equals("N") && !fldVal.equals("")) && fldVal.indexOf("#")==-1) {
                    if(fldVal.equals("NA"))
                    fldVal="0";
                    cell = row.createCell(++cellCt);
                    cell.setCellStyle(numStyleData);
                    int colNum = cell.getColumnIndex();
                    cell.setCellValue(Double.parseDouble(fldVal));
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    autoColums.add(Integer.toString(colNum));
            }
            else{   
            addCell(++cellCt, fldVal, styleData);
            }
            }
            }
            
            }
            }
             
            Iterator it= autoColums.iterator();
            while(it.hasNext()) {
            String value=(String)it.next();
            int colId = Integer.parseInt(value);
            sheet.autoSizeColumn((short)colId, true);

            }
            
            
            return hwb;
           
        }


 public CellRangeAddress merge(int fRow, int lRow, int fCol, int lCol) {
            CellRangeAddress cra = new CellRangeAddress(fRow, lRow, fCol, lCol);
            return cra;
     }
    public String nvl(String pVal) {
        return nvl(pVal, "");
    }
    
    public String nvl(String pVal, String rVal) {
        String val = pVal ;
        if(pVal == null)
            val = rVal;
        return val;
    }
    private String GetAlpha(int num)
       {
            int A = 65;    //ASCII value for capital A
         String sCol = "";
         int iRemain = 0;
         // THIS ALGORITHM ONLY WORKS UP TO ZZ. It fails on AAA
         if (num > 701) 
           {                
               return null;
           }
         if (num <= 26) 
           {
               if (num == 0)
               {
                   sCol = (char)((A + 26) - 1)+sCol; 
               }
               else
               {
                   sCol =(char)((A + num) - 1)+sCol;
               }
           }
         else
           {
               iRemain = ((num / 26))-1;
               if ((num % 26) == 0)
               {
                   sCol = GetAlpha(iRemain) + GetAlpha(num % 26);
               }
               else
               {                    
                   sCol = (char)((A + iRemain)) + GetAlpha(num % 26);
               }
         }
           return sCol;

       }

    
    public void addCell(String val) {
        addCell(val, styleDataBrdr);    
    }

    public void addCell(String val, HSSFCellStyle styl) {
        addCell(++cellCt, val, styleDataBrdr);    
    }
    
    public void addCell(int cellNum, String val, HSSFCellStyle styl, String typ ) {
        HSSFCell cell = row.createCell(cellNum);
        cell.setCellStyle(styl);
        if(val.equals("")){
            cell.setCellValue(val);
        }else{
        cell.setCellValue(Double.parseDouble(val));
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        }
    }
    public void addCell(int cellNum, String val, HSSFCellStyle styl) {
        HSSFCell cell = row.createCell(cellNum);
            
        cell.setCellStyle(styl);
        cell.setCellValue(getStr(val)); 
        int colNum = cell.getColumnIndex();
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        autoColums.add(Integer.toString(colNum));
        
    }
    public void addMergeCell(int cellNum, String val, HSSFCellStyle styl ,CellRangeAddress rang ) {
        HSSFCell cell = row.createCell(cellNum);
        cell.setCellValue(getStr(val));      
        cell.setCellStyle(styl);
        sheet.addMergedRegion(rang);
    }
    public HSSFSheet addSheet() {
        HSSFSheet lsheet = hwb.createSheet("Sheet"+ ++sheetCtr);
        lsheet.autoSizeColumn((short)0);
        rowCt = 0;
        cellCt = -1;
       return lsheet ;
      
    }
    public HSSFRow newRow() {
        return newRow(++rowCt);        
    }
    
    public HSSFRow newRow(int rownum) {
        HSSFRow lrow = sheet.createRow(rownum);
        cellCt = -1 ;
        return lrow ;
    }
    public CellRangeAddress mergeCell(int fRow, int lRow, int fCol, int lCol) {
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
    
//    public ArrayList LabPrpRs(HttpServletRequest req){
//        session = req.getSession(false);
//        ArrayList viewPrp = (ArrayList)session.getAttribute("LabRSLst");
//        try {
//            if (viewPrp == null) {
//                HashMap dbinfo = info.getDmbsInfoLst();
//                String cnt=util.nvl((String)dbinfo.get("CNT"));
//                String mem_ip=util.nvl((String)dbinfo.get("MEM_IP"),"52.74.209.117");
//                int mem_port=Integer.parseInt(util.nvl((String)dbinfo.get("MEM_PORT"),"80"));
//                MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//                viewPrp=(ArrayList)mcc.get(cnt+"_LabRSLst");
//                if(viewPrp==null){
//                viewPrp=new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp  from rep_prp where mdl = 'LAB_RS' and flg='Y' order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//
//                    viewPrp.add(rs1.getString("prp"));
//                }
//                rs1.close();
//                }
//                    Future fo = mcc.delete(cnt+"_LabRSLst");
//                    System.out.println("add status:_LabRSLst" + fo.get());
//                    fo = mcc.set(cnt+"_LabRSLst", 24*60*60, viewPrp);
//                    System.out.println("add status:_LabRSLst" + fo.get());
//                    mcc.shutdown();
//                }
//            session.setAttribute("LabRSLst", viewPrp);
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//            }catch(Exception ex){
//             System.out.println( ex.getMessage() );
//            }
//        
//        return viewPrp;
//    }
    
    public HSSFWorkbook getSapphireXl(ArrayList vwPrpLst,ArrayList pktList,HashMap prpDsc) {          
          hwb = new HSSFWorkbook();
          autoColums = new TreeSet();
          fontHdr = hwb.createFont();
          fontHdr.setFontHeightInPoints((short)11);
          fontHdr.setFontName("Calibri");
          fontHdr.setColor(HSSFColor.BLACK.index);
          fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
          styleHdr = hwb.createCellStyle();
          styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
          styleHdr.setFont(fontHdr);
          fontData = hwb.createFont();
          fontData.setFontHeightInPoints((short)10);
          fontData.setFontName("Calibri");
          styleData = hwb.createCellStyle();
          styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
          styleData.setFont(fontData);
          
          HSSFCellStyle numStyleData = hwb.createCellStyle();
          numStyleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
          numStyleData.setFont(fontData);
          numStyleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
          numStyleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
          numStyleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
          numStyleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
          numStyleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
          numStyleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
          numStyleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
          numStyleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
          ArrayList sheet1Lst=new ArrayList();
          ArrayList sheet2Lst=new ArrayList();
          ArrayList sheet3Lst=new ArrayList();
          ArrayList sheet4Lst=new ArrayList();
          sheet1Lst=new ArrayList();
          sheet1Lst.add("VNM");sheet1Lst.add("PKTCODE");sheet1Lst.add("EX_INV_NO");
          sheet1Lst.add("EX_INV_DATE");sheet1Lst.add("SINGLE_GROUP_CODE");sheet1Lst.add("CRTWT");
          sheet1Lst.add("SHAPE");sheet1Lst.add("COL");sheet1Lst.add("CLR");
          sheet1Lst.add("COL-SHADE");sheet1Lst.add("CUT");sheet1Lst.add("CUT_DISC");
          sheet1Lst.add("FLOURESENCE");
          sheet1Lst.add("FLUO_DISC");sheet1Lst.add("MILKY");sheet1Lst.add("MILKY_DISC");
          sheet1Lst.add("COST");sheet1Lst.add("RAP_RTE");sheet1Lst.add("RAP_DIS");
          sheet1Lst.add("RTE");sheet1Lst.add("STOCK");sheet1Lst.add("LOC");
          sheet1Lst.add("CREATED_DATE");sheet1Lst.add("MODIFIED_DATE");sheet1Lst.add("USER_CODE");

          
          sheet2Lst=new ArrayList();
          sheet2Lst.add("VNM");sheet2Lst.add("EX_INV_NO");sheet2Lst.add("EX_INV_DATE");
          sheet2Lst.add("ENTRY_DATE");sheet2Lst.add("ISSUE_READY_DATE");sheet2Lst.add("FIRST_ISSUE_DATE");
          sheet2Lst.add("SALE_DATE");sheet2Lst.add("ISSUE_COUNT");sheet2Lst.add("LAB");
          sheet2Lst.add("PLOTTING_PATH");sheet2Lst.add("ROUGH_TYPE_CODE");sheet2Lst.add("LOTNO");
          sheet2Lst.add("PKT_ID");sheet2Lst.add("BOX_NO");sheet2Lst.add("SAFE_CODE");
          sheet2Lst.add("REGION");sheet2Lst.add("LOCKED");sheet2Lst.add("LOCKED_DATE");
          sheet2Lst.add("LOCKED_BY");sheet2Lst.add("LOCKED_REASON");sheet2Lst.add("REMARKS1");
          sheet2Lst.add("REMARKS2");sheet2Lst.add("REMARKS3");sheet2Lst.add("LOC");
          sheet2Lst.add("CREATED_DATE");sheet2Lst.add("MODIFIED_DATE");sheet2Lst.add("USER_CODE");
                 
          sheet3Lst=new ArrayList();                                   
          sheet3Lst.add("VNM");sheet3Lst.add("EX_INV_NO");sheet3Lst.add("EX_INV_DATE");
          sheet3Lst.add("SYM");sheet3Lst.add("SYMMETRY_DISC");sheet3Lst.add("CULET");
          sheet3Lst.add("CULET_CODE");sheet3Lst.add("LUS");sheet3Lst.add("LUSTER_DISC");
          sheet3Lst.add("POL");sheet3Lst.add("POLISH_QUALITY_DISC");sheet3Lst.add("GIRDLE");
          sheet3Lst.add("GIRDLE_DISC");sheet3Lst.add("NATTS");sheet3Lst.add("NATTS_DISC");
          sheet3Lst.add("TI");sheet3Lst.add("TABLE_INCLUSION_DISC");sheet3Lst.add("INCL");
          sheet3Lst.add("OPEN_INCLUSION_DISC");sheet3Lst.add("PROP");sheet3Lst.add("PROPORTION_DISC");
          sheet3Lst.add("SPREADED");sheet3Lst.add("SPREADED_CODE");sheet3Lst.add("FEATHER");
          sheet3Lst.add("FEATHER_DISC");sheet3Lst.add("EXTRA FACETES");sheet3Lst.add("FACETS_COUNT");sheet3Lst.add("DIA_MN");
          sheet3Lst.add("DIA_MX");sheet3Lst.add("LN");sheet3Lst.add("WD");
          sheet3Lst.add("PA");sheet3Lst.add("PD");sheet3Lst.add("CA");
          sheet3Lst.add("CH");sheet3Lst.add("GIRDLEMIN_PER");sheet3Lst.add("GIRDLEMAX-PER");
          sheet3Lst.add("HT");sheet3Lst.add("DPL");sheet3Lst.add("TBL");
          sheet3Lst.add("ST_P");sheet3Lst.add("LH");sheet3Lst.add("H&A");
          sheet3Lst.add("CULET_MM");sheet3Lst.add("LOC");sheet3Lst.add("CREATED_DATE");
          sheet3Lst.add("MODIFIED_DATE");sheet3Lst.add("USER_CODE");
        
          sheet4Lst=new ArrayList();
          sheet4Lst.add("VNM");sheet4Lst.add("EX_INV_NO");sheet4Lst.add("EX_INV_DATE");
          sheet4Lst.add("COLOR_VARIATION");sheet4Lst.add("PURITY_VARIATION");sheet4Lst.add("CUT_VARIATION");
          sheet4Lst.add("RATE_VARIATION");sheet4Lst.add("ESTIMATER_RATE");sheet4Lst.add("ESTIMATER_DISCOUNT");
          sheet4Lst.add("KTSVIEW");sheet4Lst.add("COMMENT");sheet4Lst.add("REMARKS");
          sheet4Lst.add("LOC");sheet4Lst.add("MODIFIED_DATE");sheet4Lst.add("CREATED_DATE");
          sheet4Lst.add("USER_CODE");
          
          addsheetall("mFinish",sheet1Lst,pktList,prpDsc,numStyleData);            
          addsheetall("mFinish_Misc",sheet2Lst,pktList,prpDsc,numStyleData);
          addsheetall("mFinish_Parameter",sheet3Lst,pktList,prpDsc,numStyleData);
          addsheetall("mFinish_Variation",sheet4Lst,pktList,prpDsc,numStyleData);
          
          Iterator it = autoColums.iterator();
          while(it.hasNext()) {
          String value=(String)it.next();
          int colId = Integer.parseInt(value);
          sheet.autoSizeColumn((short)colId, true);
          }
       return hwb;
      }
    public void addsheetall(String sheetnm,ArrayList sheet1Lst,ArrayList pktList,HashMap prpDsc,HSSFCellStyle numStyleData){
    sheet = addSheet(sheetnm);
    Hashtable mprp = new Hashtable();
    int pktDtlListsz=pktList.size();
    row = newRow();
    for (int j = 0; j < sheet1Lst.size(); j++) {
         String hdr = String.valueOf(sheet1Lst.get(j));
         if(prpDsc.containsKey(hdr)){
             hdr=util.nvl((String)prpDsc.get(hdr));
             }
         if(hdr.indexOf("$") > -1){
             hdr=hdr.replaceAll("\\$"," ");
            }
         addCell(++cellCt, hdr, styleHdr);
    }
     
    for (int j = 0; j < pktDtlListsz; j++) {
    row = newRow();
    HashMap pktDtl = (HashMap)pktList.get(j);
    for (int i = 0; i < sheet1Lst.size(); i++) {
    String hdr = (String)sheet1Lst.get(i);
    String mprptyp = util.nvl((String)mprp.get(hdr+"T"));
    String fldVal = util.nvl((String)pktDtl.get(hdr));
    fldVal = fldVal.replace(",","");
    if(mprptyp.equals(""))
    mprptyp = util.nvl((String)prpDsc.get(hdr+"T"));    
        try{  
              cell = row.createCell(++cellCt);
              cell.setCellValue(Double.parseDouble(fldVal));
              cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
              cell.setCellStyle(numStyleData);
              int colNum = cell.getColumnIndex();
              autoColums.add(Integer.toString(colNum));
          }catch(NumberFormatException nfe){  
              cell.setCellValue(fldVal);
              cell.setCellStyle(styleData); 
          }
    }
    }
    }
    public HSSFSheet addSheet(String SheetNme) {
       HSSFSheet lsheet = hwb.createSheet(SheetNme);
       lsheet.autoSizeColumn((short)0);
       rowCt = -1;
       cellCt = -1;
      return lsheet ;
     
    }
    
    public HSSFWorkbook VIGAT(ArrayList itemHdr , ArrayList pktDtlList) {
        hwb = new HSSFWorkbook();
        autoColums = new TreeSet();
        String fontNm = "Cambria";
        fontHdr = hwb.createFont();
        fontHdr.setFontHeightInPoints((short)10);
        fontHdr.setFontName(fontNm);
        fontHdr.setColor(HSSFColor.BLACK.index);
        fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
        styleHdr = hwb.createCellStyle();
        styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleHdr.setFont(fontHdr);
        fontData = hwb.createFont();
        fontData.setFontHeightInPoints((short)10);
        fontData.setFontName(fontNm);
        styleData = hwb.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setFont(fontData);

        hlink_style = hwb.createCellStyle();

        hlink_font = hwb.createFont();

        hlink_font.setUnderline(HSSFFont.U_SINGLE);

        hlink_font.setFontName(fontNm);

        hlink_font.setColor(HSSFColor.BLUE.index);

        hlink_font.setFontHeightInPoints((short)10);

        hlink_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        hlink_style.setFont(hlink_font);
        
        HSSFCellStyle numStyleData = hwb.createCellStyle();
        numStyleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        numStyleData.setFont(fontData);
        numStyleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
        numStyleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
        numStyleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
        HSSFDataFormat df = hwb.createDataFormat();
        numStyleData.setDataFormat(df.getFormat("#0.00#"));

        sheet = addSheet();
        HashMap mprp = info.getMprp();
        HashMap prpDsc=new HashMap();
        int pktDtlListsz=pktDtlList.size();
        int n=0;
        HashMap pktDtl1 = (HashMap)pktDtlList.get(0);
        prpDsc.put("SHAPE_CLR_COL","DESCRIPTION");
        prpDsc.put("CERT_NO","CERTNO");
        prpDsc.put("MEASUREMENT","DIAMETER");
        row = newRow();
        addMergeCell(++cellCt, "TO", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+2));
        cellCt= cellCt+6;
        addMergeCell(++cellCt, "TERMS", styleHdr, merge(rowCt,rowCt,cellCt,cellCt++));
        addMergeCell(++cellCt, util.nvl((String)pktDtl1.get("TRMS")), styleHdr, merge(rowCt,rowCt,cellCt,cellCt++));
        row = newRow();
        addMergeCell(++cellCt, util.nvl((String)pktDtl1.get("BYR")), styleHdr, merge(rowCt,rowCt,cellCt,cellCt+2));
        cellCt= cellCt+6;
        addMergeCell(++cellCt, "DATE", styleHdr, merge(rowCt,rowCt,cellCt,cellCt++));
        addMergeCell(++cellCt, util.nvl((String)pktDtl1.get("DTE")), styleHdr, merge(rowCt,rowCt,cellCt,cellCt++));
        
        row = newRow();
        row = newRow();
        for (int j = 0; j < itemHdr.size(); j++) {
             String hdr = String.valueOf(itemHdr.get(j)).toUpperCase();
             if(prpDsc.containsKey(hdr)){
                 hdr=(String)prpDsc.get(hdr);
                 }
             if(hdr.indexOf("$") > -1){
                 hdr=hdr.replaceAll("\\$"," ");
                }
             addCell(++cellCt, hdr, styleHdr);
         }
        
        for (int j = n; j < pktDtlListsz; j++) {
        row = newRow();
        HashMap pktDtl = (HashMap)pktDtlList.get(j);   
        for (int i = 0; i < itemHdr.size(); i++) {
        String hdr = (String)itemHdr.get(i);
        String mprptyp = util.nvl((String)mprp.get(hdr+"T"));
        String fldVal = util.nvl((String)pktDtl.get(hdr));
          fldVal = fldVal.replace(",","");
            fldVal=fldVal.trim();
        if(mprptyp.equals(""))
        mprptyp = util.nvl((String)prpDsc.get(hdr+"T"));    
        if((mprptyp.equals("N") && !fldVal.equals("")) && fldVal.indexOf("#")==-1) {
                if(fldVal.equals("NA"))
                fldVal="0";
                cell = row.createCell(++cellCt);
                cell.setCellStyle(numStyleData);
                int colNum = cell.getColumnIndex();
                try {
                            cell.setCellValue(Double.parseDouble(fldVal));
                            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        } catch (NumberFormatException nfe) {
                            cell.setCellValue(fldVal);
                        }
                autoColums.add(Integer.toString(colNum));
        }else{   
        addCell(++cellCt, fldVal, styleData);
        }
        }
        }

         
        Iterator it= autoColums.iterator();
        while(it.hasNext()) {
        String value=(String)it.next();
        int colId = Integer.parseInt(value);
        sheet.autoSizeColumn((short)colId, true);

        }
        
        
        return hwb;
       
    }
    
    public HSSFWorkbook getPurOrdGenXl(HashMap ExcelDtl,ArrayList itemHdr,ArrayList pktDtlList) {
            String cmyName =  util.nvl((String)ExcelDtl.get("cmyName"));
            String byr = util.nvl((String)ExcelDtl.get("byr"));
            String dte =  util.nvl((String)ExcelDtl.get("dte"));
            String unt_num =  util.nvl((String)ExcelDtl.get("unt_num"));
            String bldg =  util.nvl((String)ExcelDtl.get("bldg"));
            String addr =  util.nvl((String)ExcelDtl.get("addr"));
            String landmk =  util.nvl((String)ExcelDtl.get("landmk"));
            String term =  util.nvl((String)ExcelDtl.get("term"));
            String brk =  util.nvl((String)ExcelDtl.get("brk"));
            String pur =  util.nvl((String)ExcelDtl.get("pur"));
            String purDisInr =  util.nvl((String)ExcelDtl.get("purDisINR"));
            String purIntInr =  util.nvl((String)ExcelDtl.get("paymentIntINR"));
            HashMap mprp = info.getMprp();
            HashMap prpDsc=new HashMap();
            hwb = new HSSFWorkbook();
            sheet = addSheet();
            autoColums = new TreeSet();
              fontHdr = hwb.createFont();

              fontHdr.setColor(HSSFColor.BLACK.index);

              fontHdr.setFontHeightInPoints((short)12);

              fontHdr.setFontName("Calibri");
              
              fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

              styleHdr = hwb.createCellStyle();

              styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);

              styleHdr.setFont(fontHdr);

              styleHdr.setWrapText(true);
              
              fontData = hwb.createFont();

              fontData.setFontHeightInPoints((short)10);

              fontData.setFontName("Calibri");

              fontData.setColor(HSSFColor.BLACK.index);

              styleData = hwb.createCellStyle();

              styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);

              styleData.setFont(fontData);
              
            HSSFCellStyle numStyleData = hwb.createCellStyle();
            numStyleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            numStyleData.setFont(fontData);
            numStyleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
            numStyleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
            numStyleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
            numStyleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
            numStyleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
            numStyleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
            numStyleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
            numStyleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
            HSSFDataFormat df = hwb.createDataFormat();
            numStyleData.setDataFormat(df.getFormat("#0.00#"));
            row = newRow();
            addMergeCell(++cellCt,cmyName, styleHdr, merge(rowCt,rowCt,cellCt,cellCt+7));
            
            row = newRow();
            row = newRow();
            addMergeCell(++cellCt, "Supplier:", styleHdr, merge(rowCt,rowCt,cellCt,cellCt));
            addMergeCell(++cellCt, byr, styleHdr, merge(rowCt,rowCt,cellCt,cellCt+3));
            cellCt=cellCt+3;
            addMergeCell(++cellCt, "Date", styleHdr, merge(rowCt,rowCt,cellCt,cellCt)); 
            addMergeCell(++cellCt,dte, styleHdr, merge(rowCt,rowCt,cellCt,cellCt+1));


            row = newRow();
            row = newRow();
            addMergeCell(++cellCt, "Address:", styleHdr, merge(rowCt,rowCt,cellCt,cellCt));
            addMergeCell(++cellCt, unt_num+","+bldg+","+landmk+","+addr, styleHdr, merge(rowCt,rowCt,cellCt,cellCt+3));
            cellCt=cellCt+3;
            addMergeCell(++cellCt, "Payment Terms", styleHdr, merge(rowCt,rowCt,cellCt,cellCt)); 
            addMergeCell(++cellCt,term, styleHdr, merge(rowCt,rowCt,cellCt,cellCt+1));
            
            row = newRow();
            row = newRow();
            addMergeCell(++cellCt,"Broker:", styleHdr, merge(rowCt,rowCt,cellCt,cellCt));
            addMergeCell(++cellCt, brk, styleHdr, merge(rowCt,rowCt,cellCt,cellCt+3));
            cellCt=cellCt+3;
            addMergeCell(++cellCt, "Purchase Order No.", styleHdr, merge(rowCt,rowCt,cellCt,cellCt)); 
            addMergeCell(++cellCt,pur, styleHdr, merge(rowCt,rowCt,cellCt,cellCt+1));
            
            row = newRow();
            row = newRow();
            int pktDtlListsz=pktDtlList.size();
            for (int j = 0; j < itemHdr.size(); j++) {
                 String hdr = String.valueOf(itemHdr.get(j)).toUpperCase();
                 if(prpDsc.containsKey(hdr)){
                     hdr=(String)prpDsc.get(hdr);
                     }
                 if(hdr.indexOf("$") > -1){
                     hdr=hdr.replaceAll("\\$"," ");
                    }
                 addCell(++cellCt, hdr, styleHdr);
             }
            
            for (int j = 0; j < pktDtlListsz; j++) {
            row = newRow();
            HashMap pktDtl = (HashMap)pktDtlList.get(j);   
            for (int i = 0; i < itemHdr.size(); i++) {
            String hdr = (String)itemHdr.get(i);
            String mprptyp = util.nvl((String)mprp.get(hdr+"T"));
            String fldVal = util.nvl((String)pktDtl.get(hdr));
              fldVal = fldVal.replace(",","");
                fldVal=fldVal.trim();
            if(mprptyp.equals(""))
            mprptyp = util.nvl((String)prpDsc.get(hdr+"T"));    
            if((mprptyp.equals("N") && !fldVal.equals("")) && fldVal.indexOf("#")==-1) {
                    if(fldVal.equals("NA"))
                    fldVal="0";
                    cell = row.createCell(++cellCt);
                    cell.setCellStyle(numStyleData);
                    int colNum = cell.getColumnIndex();
                    try {
                                cell.setCellValue(Double.parseDouble(fldVal));
                                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                            } catch (NumberFormatException nfe) {
                                cell.setCellValue(fldVal);
                            }
                    autoColums.add(Integer.toString(colNum));
            }else{   
            addCell(++cellCt, fldVal, styleData);
            }
            }
            }

            row = newRow();
            row = newRow();
            addMergeCell(++cellCt, "For "+cmyName, styleHdr, merge(rowCt,rowCt,cellCt,cellCt+1));
            cellCt=cellCt+4;
            addMergeCell(++cellCt, "For "+cmyName, styleHdr, merge(rowCt,rowCt,cellCt,cellCt+2));
            row = newRow();
            row = newRow();
            row = newRow();
            row = newRow();
            addMergeCell(++cellCt, "Order By", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+1));
            cellCt=cellCt+4;
            addMergeCell(++cellCt, "Approved By", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+2));

            row = newRow();
            row = newRow();
            row = newRow();
            addMergeCell(++cellCt,"HEAD OFFICE: DW-2332,BHARAT DIAMOND BOURSE,B.K.C.,BANDRA(E) , MUMBAI 400 051.Tele No. 022-43000200,FAX:43000210/48                                                                 ", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+7));
            row = newRow();
            addMergeCell(++cellCt,"VAT NO.: 27350604308V DT. 04/04/2007", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+7));
            row = newRow();
            addMergeCell(++cellCt,"CST NO.: 27350604308C DT. 04/04/2007", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+7));
            row = newRow();
            addMergeCell(++cellCt,"PAN NO.: AADCC0451B", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+7));
            row = newRow();
            row = newRow();
            addMergeCell(++cellCt,"BRANCH OFFICE: 5/897, KALPVRUKSHA, MAHIDHARPURA, GHIYASHERI, SURAT-395003 TEL:2433818 FAX :2455600                                                                                         ", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+7));            
            row = newRow();
            addMergeCell(++cellCt,"VAT NO.: 24220901568 DT. 04/07/2007", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+7));            
            row = newRow();
            addMergeCell(++cellCt,"CST NO.: 24720901568 DT. 04/07/2007", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+7));            
            row = newRow();
            addMergeCell(++cellCt,"PAN NO.: AADCC0451B", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+7));            
            
            Iterator it= autoColums.iterator();
            while(it.hasNext()) {
            String value=(String)it.next();
            int colId = Integer.parseInt(value);
            sheet.autoSizeColumn((short)colId, true);
            }
            
            
              return hwb;
        }
    
    
    public HSSFWorkbook getGenOfferXl(ArrayList itemHdr , ArrayList pktDtlList) {
          
          hwb = new HSSFWorkbook();
          autoColums = new TreeSet();
          String fontNm = "Cambria";
          fontHdr = hwb.createFont();
          fontHdr.setFontHeightInPoints((short)10);
          fontHdr.setFontName(fontNm);
          fontHdr.setColor(HSSFColor.BLACK.index);
          fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
          styleHdr = hwb.createCellStyle();
          styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
          styleHdr.setFont(fontHdr);
          
         
          fontData = hwb.createFont();
          fontData.setFontHeightInPoints((short)10);
          fontData.setFontName(fontNm);
          styleData = hwb.createCellStyle();
          styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
          styleData.setFont(fontData);

          hlink_style = hwb.createCellStyle();

          hlink_font = hwb.createFont();

          hlink_font.setUnderline(HSSFFont.U_SINGLE);

          hlink_font.setFontName(fontNm);

          hlink_font.setColor(HSSFColor.BLUE.index);

          hlink_font.setFontHeightInPoints((short)10);

          hlink_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

          hlink_style.setFont(hlink_font);
          
          HSSFCellStyle numStyleData = hwb.createCellStyle();
          numStyleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
          numStyleData.setFont(fontData);
          numStyleData.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
          numStyleData.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
          numStyleData.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
          numStyleData.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
          numStyleData.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
          numStyleData.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
          numStyleData.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
          numStyleData.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
            HSSFDataFormat df = hwb.createDataFormat();
            numStyleData.setDataFormat(df.getFormat("#0.00#"));

          sheet = addSheet();
          String  genexcel = util.nvl((String)info.getDmbsInfoLst().get("GENEXCELDSC"));
          ArrayList imagelistDtl =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ImaageDtls") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ImaageDtls");
          HashMap mprp = info.getMprp();
          row = newRow();
          HashMap prpDsc=new HashMap();
          HashMap dtls=new HashMap();
          int pktDtlListsz=pktDtlList.size();
          prpDsc.put("RNK", "Rank");
          prpDsc.put("BYRNM", "Buyer Name");
          prpDsc.put("TERM", "Term");
          prpDsc.put("EMP", "Employee");
          prpDsc.put("VNM", "Packet No.");
          prpDsc.put("OFFER_RTE", "Offer Price");
          prpDsc.put("CMP", "Mem Price");
          prpDsc.put("NET_RTE", "Net Price");
          prpDsc.put("PLPER", "Pl %");
          prpDsc.put("OFFER_DIS", "Offer Discout");
          prpDsc.put("MR_DIS", "Mem Dis     ");
          prpDsc.put("NET_DIS", "Net dis");
          prpDsc.put("TYP", "Type");
          prpDsc.put("FRMDTE", "Offer Date");
          prpDsc.put("TODTE", "valid till Date     ");
          prpDsc.put("DIS", "Dis");
          prpDsc.put("QUOT", "Quot");
          prpDsc.put("STK_IDN", "N");
          prpDsc.put("QTYT", "N");
          prpDsc.put("QuotT", "N");
          prpDsc.put("SLBACKT", "N");
          prpDsc.put("AMTT", "N");
          prpDsc.put("slbackT", "N");
          prpDsc.put("amtT", "N");
          prpDsc.put("diffT", "N");
          prpDsc.put("plT", "N");
          prpDsc.put("RateT", "N");
          prpDsc.put("RateAmtT", "N");
          prpDsc.put("RateDiscT", "N");
          prpDsc.put("RapRteT", "N");
          prpDsc.put("DisT", "N");
          prpDsc.put("Prc / CrtT", "N");
          prpDsc.put("ByrDisT", "N");
          prpDsc.put("SALE RTET", "N");
          prpDsc.put("AMOUNTT", "N");
          prpDsc.put("RAPVLUT", "N");
          prpDsc.put("DATEAMT", "Date selection To Curr Amt %");
              if(imagelistDtl!=null && imagelistDtl.size() >0){
              for(int j=0;j<imagelistDtl.size();j++){
              dtls=new HashMap();
              dtls=(HashMap)imagelistDtl.get(j);
              String gtCol=util.nvl((String)dtls.get("GTCOL")).toUpperCase();
              String hdr=util.nvl((String)dtls.get("HDR"));
               String path=util.nvl((String)dtls.get("DNA"));
              prpDsc.put(gtCol, hdr.toUpperCase());
              prpDsc.put(gtCol+"DNA", path);
              }
              }
      String imgLink = util.nvl((String)prpDsc.get("DIAMONDIMGDNA"));
        HashMap isCDRPT = new HashMap();
        if(pktDtlListsz>0)
        isCDRPT=(HashMap)pktDtlList.get(0);
        String isCD = util.nvl((String)isCDRPT.get("CDRPT"));
        int n=0;
        if(isCD.equals("Y")){
        String jp = (String)pktDtlList.get(1);
        String js= (String)pktDtlList.get(2);

        HashMap b2cMap= (HashMap)pktDtlList.get(3);
        ArrayList NONB2CMAP = (ArrayList)pktDtlList.get(4);
        HashMap NC = (HashMap)pktDtlList.get(5);
        HashMap CD = (HashMap)pktDtlList.get(6);
        row = newRow();
               addCell(++cellCt, "Jangad Par:", styleHdr);
               addCell(++cellCt, jp, styleHdr);
                             row = newRow();
               addCell(++cellCt, "Jangad selection :", styleHdr);
               addCell(++cellCt, js, styleHdr);
                row = newRow();
               addCell(++cellCt, "JB2:", styleHdr);
               addCell(++cellCt,util.nvl((String)b2cMap.get("ttlVlu")), styleHdr);
             for(int i=0;i<NONB2CMAP.size();i++){
              HashMap nonB2cDtl = (HashMap)NONB2CMAP.get(i);
               row = newRow();
               addCell(++cellCt,util.nvl((String)nonB2cDtl.get("brcNme")), styleHdr);
               addCell(++cellCt,util.nvl((String)nonB2cDtl.get("ttlVlu")), styleHdr);
            
               }
                row = newRow();
               addCell(++cellCt, "JMF ST :", styleHdr);
               addCell(++cellCt, util.nvl((String)NC.get("ttlVlu")), styleHdr);
                row = newRow();
               addCell(++cellCt, "JDS ST :", styleHdr);
               addCell(++cellCt, util.nvl((String)CD.get("ttlVlu")), styleHdr);
                        n=7;
            row = newRow();
            row = newRow();


          }

          for (int j = 0; j < itemHdr.size(); j++) {
               String hdr = String.valueOf(itemHdr.get(j)).toUpperCase();
               if(prpDsc.containsKey(hdr)){
                   hdr=(String)prpDsc.get(hdr);
                   }
               if(hdr.indexOf("$") > -1){
                   hdr=hdr.replaceAll("\\$"," ");
                  }
              if(genexcel.equals("Y")){
               String pDsc = util.nvl((String)mprp.get(hdr+"D"));
              if(!pDsc.equals("")){
                  hdr=pDsc;   
              }
              }
               addCell(++cellCt, hdr, styleHdr);
           }
          
          for (int j = n; j < pktDtlListsz; j++) {
          row = newRow();
          HashMap pktDtl = (HashMap)pktDtlList.get(j);
          String stkIdn = (String)pktDtl.get("STKIDN");
          String kapanId =util.nvl((String)pktDtl.get("ISKAPAN"));
          String ttlCts =util.nvl((String)pktDtl.get("ISTOTAL"));
          System.out.println(j);
          if(kapanId.equals("Y")){
          addCell(++cellCt,util.nvl((String)pktDtl.get("KAPAN")) , styleData);
          }else if(ttlCts.equals("Y")){
              int inxAmt = 0;
              int inxCts = 0;
              int inxVnm = 0;
              inxVnm = itemHdr.indexOf("PACKETCODE");
              inxCts = itemHdr.indexOf("CTS");
              inxAmt = itemHdr.indexOf("AMOUNT");
              for(int k=0; k < itemHdr.size(); k++ ){
              String prp = (String)itemHdr.get(k);
              int inxPrp = itemHdr.indexOf(prp);
              if (inxPrp == inxVnm) {
              addCell(++cellCt, util.nvl((String)pktDtl.get("ttlqty")), styleHdr);
              } else if (inxPrp == inxCts) {
              addCell(++cellCt, util.nvl((String)pktDtl.get("ttlCts")), styleHdr);
              } else if (inxPrp == inxAmt) {
              addCell(++cellCt, util.nvl((String)pktDtl.get("ttlAmt")), styleHdr);
              } else {
              addCell(++cellCt, "", styleData);
              }
              }

          }else{
              
          for (int i = 0; i < itemHdr.size(); i++) {
          String hdr = (String)itemHdr.get(i);
          String mprptyp = util.nvl((String)mprp.get(hdr+"T"));
          String fldVal = util.nvl((String)pktDtl.get(hdr));
              stkIdn = util.nvl((String)pktDtl.get("STKIDN"));
            fldVal = fldVal.replace(",","");
              fldVal=fldVal.trim();
          if(mprptyp.equals(""))
          mprptyp = util.nvl((String)prpDsc.get(hdr+"T"));    
          if((mprptyp.equals("N") && !fldVal.equals("")) && fldVal.indexOf("#")==-1) {
                  if(fldVal.equals("NA"))
                  fldVal="0";
                  cell = row.createCell(++cellCt);
                  cell.setCellStyle(numStyleData);
                  int colNum = cell.getColumnIndex();
                  try {
                              cell.setCellValue(Double.parseDouble(fldVal));
                              cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                          } catch (NumberFormatException nfe) {
                              cell.setCellValue(fldVal);
                          }
                  autoColums.add(Integer.toString(colNum));
          }else if(hdr.equalsIgnoreCase("VIEW") && !stkIdn.equals("")){
              fldVal=imgLink.replaceAll("STKIDN",stkIdn);
              cell = row.createCell(++cellCt);
              HSSFHyperlink link1 =
                  new HSSFHyperlink(HSSFHyperlink.LINK_URL);
              link1.setAddress(fldVal);
              cell.setHyperlink(link1);
              cell.setCellStyle(hlink_style);
              cell.setCellValue("View Image"); 
          
          }else{   
          addCell(++cellCt, fldVal, styleData);
          }
          }
          }
          }

           
          Iterator it= autoColums.iterator();
          while(it.hasNext()) {
          String value=(String)it.next();
          int colId = Integer.parseInt(value);
          sheet.autoSizeColumn((short)colId, true);

          }
          
          
          return hwb;
         
      }
}
