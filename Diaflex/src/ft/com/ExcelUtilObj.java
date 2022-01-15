package ft.com;

import ft.com.dao.GtPktDtl;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

public class ExcelUtilObj {
    
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
      
      fontUndHdr=null,
      fontHdrWh = null,
      fontData = null,
      fontDataBig=null,
      fontDataBold = null,
      fontDataBoldWh = null,hlink_font=null,fontData_O=null,fontData_G=null,fontData_R=null,fontData_M=null,fontData_B=null,fontData_P=null,fontData_blk=null;
    
  HSSFCellStyle 
      styleTopBrdr = null,
      styleDataBrdr = null,
      styleHdr = null,
      styleUndHdr=null,
      styleHdrBk = null,
      styleCntr = null,
      styleRght = null,
      styleCentBold = null,
      styleData = null,
      styleRghtBold = null,
      styleBig=null,
      styleBold =null,
      numStyleDataBold = null,
      styleDataRed =null,styleDataGN=null, hlink_style =null,numStyle=null,styleData_O=null,styleData_RLeft=null,styleData_G=null,styleData_R=null,styleData_M=null,styleData_B=null,styleData_P=null,styleData_blk=null,
      numStyle_O=null,numStyle_G=null,numStyle_R=null,numStyle_M=null,numStyle_B=null,numStyle_P=null,numStyle_blk=null;
  HSSFWorkbook hwb = null;
  HSSFSheet sheet = null;
  HSSFRow row = null ;
  HSSFCell cell = null;
  TreeSet autoColums = null;
  
  public ExcelUtilObj() {
      super();
  }
  
  
  public void init(DBMgr db, DBUtil util, InfoMgr info,GtMgr gtMgr) {
      this.db   = db;
      this.util = util;
      this.info = info;
      this.gtMgr = gtMgr;
  }

    public HSSFWorkbook getGenXlObj(HashMap excelDtl, HttpServletRequest req) {

       session = req.getSession(false);

       gtMgr = (GtMgr)session.getAttribute("gtMgr");
         GenericInterface genericInt = new GenericImpl();
       HashMap pageDtl=util.pagedefXL("GENERAL_FMT","EXCEL");
         HashMap dbinfo = info.getDmbsInfoLst();
         String cnt = (String)dbinfo.get("CNT");
         String imgSegoma = nvl((String)dbinfo.get("SEGOMA_IMG_LINK"));
         String imgUrl = nvl((String)dbinfo.get("WEB_IMG_URL"));
         String imgDiaUrl = nvl((String)dbinfo.get("WEB_DIA_IMG_URL"));
       String SHEETHED_SMRY =util.nvl((String)pageDtl.get("SHEETHED_SMRY"),"N");

       String sumSheet =util.nvl((String)pageDtl.get("SHEET_SMRY"));

       String grand =util.nvl((String)pageDtl.get("GRAND"));

       String ByrNme =util.nvl((String)pageDtl.get("BYRNME"));

       String MemoDate =util.nvl((String)pageDtl.get("MEMODATE"));

       String SZ_GRP =util.nvl((String)pageDtl.get("SZ_GRP"));

       String fontNm = util.nvl((String)pageDtl.get("FONT_NAME"));

       String bgcolor = util.nvl((String)pageDtl.get("HEADER_BG"));
       
       String kgHdr = util.nvl((String)pageDtl.get("KGHDR"),"N");
       String highlignt = util.nvl((String)pageDtl.get("HIGHLIGHT"),"N");

       int ADDWIDTH = Integer.parseInt(util.nvl((String)pageDtl.get("ADDWIDTH")));

       int fontAddSz = Integer.parseInt(util.nvl((String)pageDtl.get("FONT_SIZE_ADD"),"16"));

       int fontSz = Integer.parseInt((String)util.nvl((String)pageDtl.get("FONT_SIZE")));

       int fontHDSz = Integer.parseInt((String)util.nvl((String)pageDtl.get("FONT_SIZE_HEADER")));

       int fontCOLDSz = Integer.parseInt((String)util.nvl((String)pageDtl.get("FONT_SIZE_COL_HED")));

                                    

       hwb = new HSSFWorkbook();

       autoColums = new TreeSet();

       HashMap mprp = info.getMprp();

       sheet = addSheet();

       row = newRow();

       fontHdr = hwb.createFont();

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

       styleHdr = hwb.createCellStyle();

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

       

       HSSFDataFormat df = hwb.createDataFormat();

       fontData = hwb.createFont();
       fontData.setFontHeightInPoints((short)10);
       fontData.setFontName(fontNm);
       styleData = hwb.createCellStyle();
       styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
       styleData.setFont(fontData);
       
         numStyle = hwb.createCellStyle();
         numStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         numStyle.setFont(fontData);
         numStyle.setDataFormat(df.getFormat("#0.00#"));
         

         fontData_O = hwb.createFont();
         fontData_O.setFontHeightInPoints((short)10);
         fontData_O.setFontName(fontNm);
         fontData_O.setColor(HSSFColor.OLIVE_GREEN.index);
         styleData_O = hwb.createCellStyle();
         styleData_O.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         styleData_O.setFont(fontData_O);
         numStyle_O = hwb.createCellStyle();
         numStyle_O.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         numStyle_O.setFont(fontData_O);
         numStyle_O.setDataFormat(df.getFormat("#0.00#"));
         
         fontData_G = hwb.createFont();
         fontData_G.setFontHeightInPoints((short)10);
         fontData_G.setFontName(fontNm);
         fontData_G.setColor(HSSFColor.GREEN.index);
         styleData_G = hwb.createCellStyle();
         styleData_G.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         styleData_G.setFont(fontData_G);
         numStyle_G = hwb.createCellStyle();
         numStyle_G.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         numStyle_G.setFont(fontData_G);
         numStyle_G.setDataFormat(df.getFormat("#0.00#"));
         
         fontData_R = hwb.createFont();
         fontData_R.setFontHeightInPoints((short)10);
         fontData_R.setFontName(fontNm);
         fontData_R.setColor(HSSFColor.RED.index);
         styleData_R = hwb.createCellStyle();
         styleData_R.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         styleData_R.setFont(fontData_R);
         numStyle_R = hwb.createCellStyle();
         numStyle_R.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         numStyle_R.setFont(fontData_R);
         numStyle_R.setDataFormat(df.getFormat("#0.00#"));
         
         styleData_RLeft = hwb.createCellStyle();
         styleData_RLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);
         styleData_RLeft.setFont(fontData_R);
         
         fontData_M = hwb.createFont();
         fontData_M.setFontHeightInPoints((short)10);
         fontData_M.setFontName(fontNm);
         fontData_M.setColor(HSSFColor.MAROON.index);
         styleData_M = hwb.createCellStyle();
         styleData_M.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         styleData_M.setFont(fontData_M);
         numStyle_M = hwb.createCellStyle();
         numStyle_M.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         numStyle_M.setFont(fontData_M);
         numStyle_M.setDataFormat(df.getFormat("#0.00#"));
         
         fontData_B = hwb.createFont();
         fontData_B.setFontHeightInPoints((short)10);
         fontData_B.setFontName(fontNm);
         fontData_B.setColor(HSSFColor.BLUE.index);
         styleData_B = hwb.createCellStyle();
         styleData_B.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         styleData_B.setFont(fontData_B);
         numStyle_B = hwb.createCellStyle();
         numStyle_B.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         numStyle_B.setFont(fontData_B);
         numStyle_B.setDataFormat(df.getFormat("#0.00#"));
         
         fontData_P = hwb.createFont();
         fontData_P.setFontHeightInPoints((short)10);
         fontData_P.setFontName(fontNm);
         fontData_P.setColor(HSSFColor.VIOLET.index);
         styleData_P = hwb.createCellStyle();
         styleData_P.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         styleData_P.setFont(fontData_P);
         numStyle_P = hwb.createCellStyle();
         numStyle_P.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         numStyle_P.setFont(fontData_P);
         numStyle_P.setDataFormat(df.getFormat("#0.00#"));
         
         fontData_blk = hwb.createFont();
         fontData_blk.setFontHeightInPoints((short)10);
         fontData_blk.setFontName(fontNm);
         fontData_blk.setColor(HSSFColor.BLACK.index);
         styleData_blk = hwb.createCellStyle();
         styleData_blk.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         styleData_blk.setFont(fontData_blk);
         numStyle_blk = hwb.createCellStyle();
         numStyle_blk.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         numStyle_blk.setFont(fontData_blk);
         numStyle_blk.setDataFormat(df.getFormat("#0.00#"));

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

      

       fontDataBold = hwb.createFont();

       fontDataBold.setFontHeightInPoints((short)fontSz);

       fontDataBold.setFontName(fontNm);

       fontDataBold.setColor(HSSFColor.BLACK.index);

       fontDataBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

       styleBold = hwb.createCellStyle();

       styleBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);

       styleBold.setFont(fontDataBold);

        



       numStyleDataBold = hwb.createCellStyle();

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

         numStyleDataBold.setDataFormat(df.getFormat("#0.00#"));

        

       

    

       hlink_style = hwb.createCellStyle();

       hlink_font = hwb.createFont();

       hlink_font.setUnderline(HSSFFont.U_SINGLE);

       hlink_font.setFontName(fontNm);

       hlink_font.setColor(HSSFColor.BLUE.index);

       hlink_font.setFontHeightInPoints((short)fontSz);

       hlink_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

       hlink_style.setFont(hlink_font);

         HSSFFont fontHdrkg = hwb.createFont();
         fontHdrkg.setFontHeightInPoints((short)12);
         fontHdrkg.setFontName("Times New Roman");
         fontHdrkg.setColor(HSSFColor.WHITE.index);
         fontHdrkg.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
         HSSFCellStyle styleHdrkg = hwb.createCellStyle();
         styleHdrkg.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         styleHdrkg.setFillForegroundColor(HSSFColor.DARK_BLUE.index);
         styleHdrkg.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
         styleHdrkg.setFont(fontHdrkg);
         styleHdrkg.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
         styleHdrkg.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
         styleHdrkg.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
         styleHdrkg.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
         styleHdrkg.setTopBorderColor(HSSFColor.BLACK.index);
         styleHdrkg.setBottomBorderColor(HSSFColor.BLACK.index);
         styleHdrkg.setLeftBorderColor(HSSFColor.BLACK.index);
         styleHdrkg.setRightBorderColor(HSSFColor.BLACK.index);
         
         HSSFFont fontHdrMrgDtl = hwb.createFont();
         fontHdrMrgDtl.setFontHeightInPoints((short)14);
         fontHdrMrgDtl.setFontName("Times New Roman");
         fontHdrMrgDtl.setColor(HSSFColor.BLACK.index);
         fontHdrMrgDtl.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
         HSSFCellStyle styleHdrMrgDtl = hwb.createCellStyle();
         styleHdrMrgDtl.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         styleHdrMrgDtl.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
         styleHdrMrgDtl.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
         styleHdrMrgDtl.setFont(fontHdrMrgDtl);
         styleHdrMrgDtl.setDataFormat(df.getFormat("#0.00#"));
         styleHdrMrgDtl.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
         styleHdrMrgDtl.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
         styleHdrMrgDtl.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
         styleHdrMrgDtl.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
         styleHdrMrgDtl.setTopBorderColor(HSSFColor.BLACK.index);
         styleHdrMrgDtl.setBottomBorderColor(HSSFColor.BLACK.index);
         styleHdrMrgDtl.setLeftBorderColor(HSSFColor.BLACK.index);
         styleHdrMrgDtl.setRightBorderColor(HSSFColor.BLACK.index);

         ArrayList imagelistDtl =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ImaageDtls") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ImaageDtls");

    

       String logo = util.nvl((String)excelDtl.get("LOGO"));

      String mdl = util.nvl((String)excelDtl.get("MDL"));

       HashMap stockViewMap = info.getStockViewMap();

       if(stockViewMap==null)

           stockViewMap= new HashMap();

       if(logo.equals("Y")){
       addheader(req,pageDtl,excelDtl);
       if(cnt.equals("kj"))
       styleHdr=styleHdrkg;
       }

      

       String lstNme = (String)excelDtl.get("lstNme");

       HashMap pktDtlMap = (HashMap)gtMgr.getValue(lstNme);

       LinkedHashMap pktDtl = new LinkedHashMap(pktDtlMap);

    

       ArrayList stkIdnList = (ArrayList)gtMgr.getValue(lstNme+"_SEL");

       if(stkIdnList==null || stkIdnList.size()==0){

            stkIdnList  = new ArrayList();

          Set<String> keys = pktDtl.keySet();

           for(String key: keys){

              stkIdnList.add(key);

           }

    

        }

       int pcs=stkIdnList.size();

       int smryRomnum=0;

       int startCellCnt=0;
       
       ArrayList vwPrpLst = (ArrayList)excelDtl.get("VWLST");
       if(vwPrpLst==null)
         vwPrpLst = info.getVwPrpLst();
       
         String grandTtl = util.nvl((String)excelDtl.get("GRNTTL"));

        

         if(grandTtl.equals("Y")){

         row = newRow();

         cellCt=1;

        addCell(++cellCt, "", styleHdr);

        addCell(++cellCt, "Pcs", styleHdr);

        if(vwPrpLst.indexOf("CRTWT")!=-1)

        addCell(++cellCt, "Carat", styleHdr);

        if(vwPrpLst.indexOf("RAP_RTE")!=-1)

        addCell(++cellCt, "Rap Vlu($)", styleHdr);

       if(vwPrpLst.indexOf("RTE")!=-1)

        addCell(++cellCt, "Rap%", styleHdr);

       if(vwPrpLst.indexOf("RTE")!=-1)

        addCell(++cellCt, "PR/ CT($)", styleHdr);

       if(vwPrpLst.indexOf("RTE")!=-1 && vwPrpLst.indexOf("CRTWT")!=-1)

        addCell(++cellCt, "Amount($)", styleHdr);

         row = newRow();

          cellCt=1;

         smryRomnum=rowCt;

         addCell(++cellCt, "Grand Total", styleHdr);

         startCellCnt=cellCt+1;

         addCell(++cellCt,"", styleHdr);

         if(vwPrpLst.indexOf("CRTWT")!=-1)

         addCell(++cellCt, "", styleHdr);

           

         if(vwPrpLst.indexOf("RAP_RTE")!=-1)

         addCell(++cellCt, "", styleHdr);

           

         if(vwPrpLst.indexOf("RTE")!=-1)

         addCell(++cellCt, "", styleHdr);

        

         if(vwPrpLst.indexOf("RTE")!=-1)

         addCell(++cellCt, "", styleHdr);

            

         if(vwPrpLst.indexOf("RTE")!=-1 && vwPrpLst.indexOf("CRTWT")!=-1)

         addCell(++cellCt, "", styleHdr);
         
             row = newRow();

              cellCt=1;
             addCell(++cellCt, "Sub Total", styleHdr);

            
             addCell(++cellCt,"", styleHdr);

             if(vwPrpLst.indexOf("CRTWT")!=-1)

             addCell(++cellCt, "", styleHdr);

               

             if(vwPrpLst.indexOf("RAP_RTE")!=-1)

             addCell(++cellCt, "", styleHdr);

               

             if(vwPrpLst.indexOf("RTE")!=-1)

             addCell(++cellCt, "", styleHdr);

             

             if(vwPrpLst.indexOf("RTE")!=-1)

             addCell(++cellCt, "", styleHdr);

                

             if(vwPrpLst.indexOf("RTE")!=-1 && vwPrpLst.indexOf("CRTWT")!=-1)

             addCell(++cellCt, "", styleHdr);


         }

       

       row = newRow();

    
//
       row = newRow();

    

       HashMap itemHdrDsc = new HashMap();

       itemHdrDsc.put("emp","Employee Name");

       itemHdrDsc.put("issIdn","Issue Id");

       itemHdrDsc.put("vnm","Packet Id");

       itemHdrDsc.put("AMT","Amount");

       itemHdrDsc.put("RAPVAL","Rap Value");

       itemHdrDsc.put("amt","Amount");

       itemHdrDsc.put("rapval","Rap Value");

         itemHdrDsc.put("cpDis","CP Dis");

         itemHdrDsc.put("cpTotal","CP Vlu");

         itemHdrDsc.put("cts","Carat");

         itemHdrDsc.put("qty","Qty");

         itemHdrDsc.put("stt","Status");

    

    
      int filStart = rowCt-1;
       ArrayList itemHdr = (ArrayList)excelDtl.get("HDR");

       ArrayList colHdr = new ArrayList();

       addCell(++cellCt, "SR.", styleHdr);

       colHdr.add("SR");

       HashMap vwDtl = null;

       vwDtl = (HashMap)stockViewMap.get("VWIMG");

       if(vwDtl!=null && !mdl.equals("MIX_VW")  && !cnt.equals("kj")){

     

       addCell(++cellCt, "View", styleHdr);

       colHdr.add("VIEW");

       }

       for(int i=0;i<itemHdr.size();i++){

       HashMap hdrDtl = (HashMap)itemHdr.get(i);

       String hdr = (String)hdrDtl.get("hdr");

       String typ = (String)hdrDtl.get("typ");
       String dp = util.nvl((String)hdrDtl.get("dp"));
       String hdrDsc= "";
           colHdr.add(hdr);

          

           if(typ.equals("P")){
        if(cnt.equals("kj"))
            dp="";
        hdrDsc = (String)mprp.get(hdr+""+dp);

          if(hdrDsc==null)

            hdrDsc = hdr;

           

        }else{

        hdrDsc = (String)itemHdrDsc.get(hdr);

         if(hdrDsc==null)

           hdrDsc = hdr;

        }
        if(cnt.equals("kj") && hdr.equals("LAB")){
            addCell(++cellCt, "View Cert", styleHdr);
            addCell(++cellCt, "View Image", styleHdr);
            addCell(++cellCt, "View DNA", styleHdr);
        }else{

       addCell(++cellCt, hdrDsc, styleHdr);
        }
       }

       sheet.createFreezePane(0, rowCt+1);

         int startRowCnt=0;

         int endrowCnt=0;

         double exh_rte=Double.parseDouble(util.nvl((String)excelDtl.get("EXHRTE"),"1"));

       startRowCnt=rowCt+2;


        for(int i=0; i < stkIdnList.size(); i++ ){
           String stkIdn = (String)stkIdnList.get(i);

           GtPktDtl stockPkt = (GtPktDtl)pktDtl.get(stkIdn);

            String color = util.nvl(stockPkt.getValue("color"));
             String diamondimg = nvl(stockPkt.getValue("diamondImg"));
             String lightimg = nvl(stockPkt.getValue("lightimg"));
           if(color.equals("O")){
               styleData = styleData_O;
               numStyle=numStyle_O;
           }else if(color.equals("G")){
               styleData = styleData_G;
               numStyle=numStyle_G;
           }else if(color.equals("R")){
               styleData = styleData_R;
               numStyle=numStyle_R;
           }else if(color.equals("M")){
               styleData = styleData_M;
               numStyle=numStyle_M;
           }else if(color.equals("B")){
               styleData = styleData_B;
               numStyle=numStyle_B;
           }else if(color.equals("P")){
               styleData = styleData_P;
               numStyle=numStyle_P;
           }else{
               styleData = styleData_blk;
               numStyle=numStyle_blk;
           }
           

            String lab = stockPkt.getValue("cert_lab");

           String cts = stockPkt.getValue("cts");

            row= newRow();

            int sr = i+1;

           addCell(++cellCt,String.valueOf(sr) , styleData,"N");

            vwDtl = (HashMap)stockViewMap.get("VWIMG");

             if(vwDtl!=null && !mdl.equals("MIX_VW") && !cnt.equals("kj")){
             cell = row.createCell(++cellCt);
             boolean imageadded=true;
             if(imagelistDtl!=null && imagelistDtl.size() >0){
             for(int j=0;j<imagelistDtl.size();j++){
             HashMap dtls=new HashMap();
             dtls=(HashMap)imagelistDtl.get(j);
             String typimg=util.nvl((String)dtls.get("TYP"));
             String allowon=util.nvl((String)dtls.get("ALLOWON"));
             if((allowon.indexOf("SRCH") > -1) && typimg.equals("I")){
             String path=util.nvl((String)dtls.get("PATH"));
             String gtCol=util.nvl((String)dtls.get("GTCOL"));
             String dna=util.nvl((String)dtls.get("DNA"));
             String val=util.nvl(stockPkt.getValue(gtCol),"N");
             if(!val.equals("N") || !dna.equals("")){
                 
                 if(path.indexOf("segoma") > -1 || path.indexOf("sarineplatform") > -1)
                      path=path+val;
                  else{
                       path=path+val;
                   }
                 if(!dna.equals("")){
                  
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
             int colNum = cell.getColumnIndex();
             autoColums.add(Integer.toString(colNum));
             }

             for(int j=0;j<itemHdr.size();j++){

            HashMap hdrDtl = (HashMap)itemHdr.get(j);

            String hdr = (String)hdrDtl.get("hdr");

            String prpTyp = util.nvl((String)hdrDtl.get("htyp"));

              vwDtl = (HashMap)stockViewMap.get(hdr);

    

            String fldVal =  util.nvl(stockPkt.getValue(hdr)).trim();

          

                  if(vwDtl!=null){

                   cell = row.createCell(++cellCt);

                   String  pNme ="";

                   String dir = "";

                  String url = util.nvl((String)vwDtl.get("url"));

                  String  typ = util.nvl((String)vwDtl.get("typ"));

                   dir = util.nvl((String)vwDtl.get("dir"),"certImg");

                   String viewPRP = util.nvl((String)vwDtl.get("view"));

                   if(hdr.equals("CERT NO.") || hdr.equals("CERT_NO")){

                   if(lab.equals("IGI"))
                    url="http://www.igiworldwide.com/search_report.aspx?PrintNo=REPNO&Wght=WT";

                   if(lab.equals("HRD"))
                    url="https://my.hrdantwerp.com/?record_number=REPNO&weight=WT";
                   
                     if(lab.equals("GCAL"))
                        url="http://gemfacts.com/Cert/CertSearch.aspx?certNo=REPNO";

                    if(!fldVal.equals("") && (lab.equals("IGI")||lab.equals("GIA") || lab.equals("HRD") || lab.equals("GCAL"))){

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

                   HashMap dtls=new HashMap();

                  dtls=(HashMap)imagelistDtl.get(k);

                  String looptyp=util.nvl((String)dtls.get("TYP"));

                  String allowon=util.nvl((String)dtls.get("ALLOWON"));

                  String gtCol=util.nvl((String)dtls.get("GTCOL"));

                  if((looptyp.equals("C") || looptyp.equals("V")) && (allowon.indexOf("SRCH") > -1) && gtCol.equals(dir)){

                  String path=util.nvl((String)dtls.get("PATH"));

                  String val=util.nvl(stockPkt.getValue(gtCol),"N");

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
                      if(cnt.equals("kj") && hdr.equals("LAB")){
                          String imgLink = "";
                          if(!diamondimg.equals("N") && !diamondimg.equals("")) {
                          imgLink = imgDiaUrl+""+diamondimg;
                          cell = row.createCell(++cellCt);
                          cell.setCellValue("Image");
                          HSSFHyperlink link =new HSSFHyperlink(HSSFHyperlink.LINK_URL);
                          link.setAddress(imgLink);
                          cell.setHyperlink(link);
                          cell.setCellStyle(hlink_style);
                          } else if(!lightimg.equals("N") && !lightimg.equals("")) {
                          imgLink = imgSegoma+""+lightimg;
                          cell = row.createCell(++cellCt);
                          cell.setCellValue("Image");
                          HSSFHyperlink link =
                          new HSSFHyperlink(HSSFHyperlink.LINK_URL);
                          link.setAddress(imgLink);
                          cell.setHyperlink(link);
                          cell.setCellStyle(hlink_style);
                          }  else {
                          cell = row.createCell(++cellCt);
                          cell.setCellValue(imgLink);
                          cell.setCellStyle(styleData);
                          }
                          String dnaLink = "http://www.kapugems.com/kapuutilnew/search.do?method=load&stoneId="+stkIdn+"&by=DF";
                          cell = row.createCell(++cellCt);
                          cell.setCellValue("DNA");
                          HSSFHyperlink link =
                          new HSSFHyperlink(HSSFHyperlink.LINK_URL);
                          link.setAddress(dnaLink);
                          cell.setHyperlink(link);
                          cell.setCellStyle(hlink_style);
                      }
                  }else if(prpTyp.equals("N")){
                      if(highlignt.equals("Y") && hdr.equals("RAP_DIS")){
                          addCell(++cellCt, fldVal, styleHdrMrgDtl, prpTyp); 
                      }else{
                        addCell(++cellCt, fldVal, numStyle, prpTyp); 
                      }

                  }else{
                      
             if(hdr.equals("vnm")){
                 vwDtl = (HashMap)stockViewMap.get("VNMIMG");
                 if(vwDtl!=null && !mdl.equals("MIX_VW")){
                 
                 String url = util.nvl((String)vwDtl.get("url"));
                 String gtCol=util.nvl((String)vwDtl.get("dir"));
                     String val=util.nvl(stockPkt.getValue(gtCol),"N");
                     if(!val.equals("N")){
                     cell = row.createCell(++cellCt);
                     
                         url=url+val;
                         HSSFHyperlink link1 =
                             new HSSFHyperlink(HSSFHyperlink.LINK_URL);
                         link1.setAddress(url);
                         cell.setHyperlink(link1);
                         cell.setCellStyle(hlink_style);
                         cell.setCellValue(fldVal); 
                         int colNum = cell.getColumnIndex();
                         autoColums.add(Integer.toString(colNum));
                     }else{
                         addCell(++cellCt, fldVal, styleData);
                     }
                 }else{
                     addCell(++cellCt, fldVal, styleData);
                     
                 }
                 
                 

              
             }else if(hdr.equals("stt")){
                 vwDtl = (HashMap)stockViewMap.get("STTIMG");
                 if(vwDtl!=null && !mdl.equals("MIX_VW")){
                 
                 String url = util.nvl((String)vwDtl.get("url"));
                 String gtCol=util.nvl((String)vwDtl.get("dir"));
                     String val=util.nvl(stockPkt.getValue(gtCol),"N");
                     if(!val.equals("N")){
                     cell = row.createCell(++cellCt);
                     
                         url=url+val;
                         HSSFHyperlink link1 =
                             new HSSFHyperlink(HSSFHyperlink.LINK_URL);
                         link1.setAddress(url);
                         cell.setHyperlink(link1);
                         cell.setCellStyle(hlink_style);
                         cell.setCellValue(fldVal); 
                         int colNum = cell.getColumnIndex();
                         autoColums.add(Integer.toString(colNum));
                     }else{
                         addCell(++cellCt, fldVal, styleData);
                     }
                 }else{
                     addCell(++cellCt, fldVal, styleData);
                     
                 }
                 
                 

              
             }else{
                 addCell(++cellCt, fldVal, styleData);
             }
                      
            
                  }
      

            }

    

    

         }
        int filEndRow =rowCt-1;
       
        endrowCnt=rowCt+1;

       
        int cz = endrowCnt+2;
    

       if(grandTtl.equals("Y")){

        

         row = newRow();

          int grandTtlRow =rowCt;

            int ctwtCol=colHdr.indexOf("CRTWT");

           if(ctwtCol==-1)

                ctwtCol = colHdr.indexOf("cts");

          int rapVluCol=colHdr.indexOf("RAPVAL");

           int amtColVal=colHdr.indexOf("AMT");

           int rteCol=colHdr.indexOf("RTE");

           int raprteCol=colHdr.indexOf("RAP_RTE");

           int rapDisCol = colHdr.indexOf("RAP_DIS");

                 

         if(grand.equals("Y") && !mdl.equals("MIX_VW")){

              styleBold.setDataFormat(df.getFormat("#0.00#"));

            HSSFRow smryRow =  sheet.getRow(smryRomnum);

            String strFormula = "SUBTOTAL(102,A"+startRowCnt+":A"+endrowCnt+")";
              String ttlFormula = "COUNT(A"+startRowCnt+":A"+endrowCnt+")";

                cell = row.createCell(0);

            cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);

            cell.setCellFormula(strFormula);

                cell.setCellStyle(styleBold);

                 int  colNum = cell.getColumnIndex();

                autoColums.add(Integer.toString(colNum));

            smryRow.getCell(startCellCnt).setCellFormula(ttlFormula);
              HSSFRow subSummRow =  sheet.getRow(smryRomnum+1);
              subSummRow.getCell(startCellCnt).setCellFormula(strFormula);

         if(ctwtCol!=-1){

           startCellCnt = startCellCnt+1;

           String crtwtcolName=GetAlpha(ctwtCol+1);

            strFormula = "round(SUBTOTAL(109,"+crtwtcolName+startRowCnt+":"+crtwtcolName+endrowCnt+"),2)";
             ttlFormula = "round(SUM("+crtwtcolName+startRowCnt+":"+crtwtcolName+endrowCnt+"),2)";
           cell = row.createCell(ctwtCol);

           cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);

           cell.setCellFormula(strFormula);

           cell.setCellStyle(styleBold);

          

            

            colNum = cell.getColumnIndex();

           autoColums.add(Integer.toString(colNum));

            smryRow.getCell(startCellCnt).setCellFormula(ttlFormula);      
             subSummRow =  sheet.getRow(smryRomnum+1);
             subSummRow.getCell(startCellCnt).setCellFormula(strFormula);

          

         }

          if(rapVluCol!=-1 && rteCol!=-1){

            startCellCnt = startCellCnt+1;

              String rapVlucolName=GetAlpha(rapVluCol+1);

              cell = row.createCell(rapVluCol);

              strFormula = "SUBTOTAL(109,"+rapVlucolName+startRowCnt+":"+rapVlucolName+endrowCnt+")";
              ttlFormula = "SUM("+rapVlucolName+startRowCnt+":"+rapVlucolName+endrowCnt+")";

              cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);

              cell.setCellFormula(strFormula);

              cell.setCellStyle(styleBold);

                colNum = cell.getColumnIndex();

              autoColums.add(Integer.toString(colNum));

            smryRow.getCell(startCellCnt).setCellFormula(ttlFormula);      
              subSummRow =  sheet.getRow(smryRomnum+1);
              subSummRow.getCell(startCellCnt).setCellFormula(strFormula);

    

          }

          if(rapDisCol!=-1 && rapVluCol!=-1 && amtColVal!=-1){

            startCellCnt = startCellCnt+1;

            String amtColName=GetAlpha(amtColVal+1);

            String rapVluColName=GetAlpha(rapVluCol+1);

            cell = row.createCell(rapDisCol);

           

            strFormula="SUM(((SUBTOTAL(109,"+amtColName+startRowCnt+":"+amtColName+endrowCnt+"))/(SUBTOTAL(109,"+rapVluColName+startRowCnt+":"+rapVluColName+endrowCnt+"))/("+"CZ"+cz+"))*100)-100";
              ttlFormula = "SUM(((SUM("+amtColName+startRowCnt+":"+amtColName+endrowCnt+"))/(SUM("+rapVluColName+startRowCnt+":"+rapVluColName+endrowCnt+"))/("+"CZ"+cz+"))*100)-100";

             cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);

             cell.setCellFormula(strFormula);

            cell.setCellStyle(styleBold);

             colNum = cell.getColumnIndex();

            autoColums.add(Integer.toString(colNum));

            smryRow.getCell(startCellCnt).setCellFormula(ttlFormula);      
              subSummRow =  sheet.getRow(smryRomnum+1);
              subSummRow.getCell(startCellCnt).setCellFormula(strFormula);

    

           

          }

          

          if(rteCol!=-1 && ctwtCol !=-1 && amtColVal!=-1){ 

              startCellCnt = startCellCnt+1;                                  

               cell = row.createCell(rteCol);

               String crtwtColName=GetAlpha(ctwtCol+1);

               String totalamtcolName=GetAlpha(amtColVal+1);

                strFormula = "SUBTOTAL(109,"+totalamtcolName+startRowCnt+":"+totalamtcolName+endrowCnt+")/SUBTOTAL(109,"+crtwtColName+startRowCnt+":"+crtwtColName+endrowCnt+")";
                ttlFormula = "SUM("+totalamtcolName+startRowCnt+":"+totalamtcolName+endrowCnt+")/SUM("+crtwtColName+startRowCnt+":"+crtwtColName+endrowCnt+")";


               cell.setCellStyle(styleBold);

               colNum = cell.getColumnIndex();

               cell.setCellFormula(strFormula);

               cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);

              autoColums.add(Integer.toString(colNum));

              smryRow.getCell(startCellCnt).setCellFormula(ttlFormula);      
                subSummRow =  sheet.getRow(smryRomnum+1);
                subSummRow.getCell(startCellCnt).setCellFormula(strFormula);

    

            }

         

       

          if(raprteCol!=-1){

            startCellCnt = startCellCnt+1;

            String rapVlucolName=GetAlpha(rapVluCol+1);

            cell = row.createCell(rapVluCol);

            strFormula = "SUBTOTAL(109,"+rapVlucolName+startRowCnt+":"+rapVlucolName+endrowCnt+")";
              ttlFormula =  "SUM("+rapVlucolName+startRowCnt+":"+rapVlucolName+endrowCnt+")";

            cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);

            cell.setCellFormula(strFormula);

            cell.setCellStyle(styleBold);

            colNum = cell.getColumnIndex();

            autoColums.add(Integer.toString(colNum));

            smryRow.getCell(startCellCnt).setCellFormula(ttlFormula);      
              subSummRow =  sheet.getRow(smryRomnum+1);
              subSummRow.getCell(startCellCnt).setCellFormula(strFormula);

    

           

          }

         

            if(amtColVal!=-1){

            

              cell = row.createCell(amtColVal);

               String colName=GetAlpha(amtColVal+1);

               strFormula = "SUBTOTAL(109,"+colName+startRowCnt+":"+colName+endrowCnt+")";
                ttlFormula ="SUM("+colName+startRowCnt+":"+colName+endrowCnt+")";

              cell.setCellFormula(strFormula);

              cell.setCellStyle(styleBold);

              colNum = cell.getColumnIndex();

              cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);

              autoColums.add(Integer.toString(colNum));

              smryRow.getCell(startCellCnt).setCellFormula(ttlFormula);
                subSummRow =  sheet.getRow(smryRomnum+1);
                subSummRow.getCell(startCellCnt).setCellFormula(strFormula);


            }

         

          }

        }
         
         row = newRow();
         cell = row.createCell(103);

       

         

         if(exh_rte<=0){

         exh_rte=1;

         }

         cell.setCellValue(exh_rte);

         cell.setCellStyle(styleData);

         int colNum = cell.getColumnIndex();

         autoColums.add(Integer.toString(colNum));
         int rapVluCol=colHdr.indexOf("RAPVAL");
         String rapVluColName=GetAlpha(rapVluCol+1);
         startRowCnt= startRowCnt-1;
         sheet.setAutoFilter(CellRangeAddress.valueOf("A"+startRowCnt+":"+rapVluColName+endrowCnt));
       

       Iterator it = autoColums.iterator();

       while(it.hasNext()) {

       String value=(String)it.next();

       int colId = Integer.parseInt(value);

       sheet.autoSizeColumn((short)colId, true);

    

       }

      

      return hwb;

     }

    public void addheader(HttpServletRequest req,HashMap pageDtl,HashMap excelDtl){
                    
                      HashMap exlFormat = util.EXLFormat();

                 int stCol = ++cellCt, stRow = rowCt;

                   String logoNme =util.nvl((String)pageDtl.get("LOGO"));
                   String NAME = util.nvl((String)pageDtl.get("NAME"));   
                   String kgHdr =util.nvl((String)pageDtl.get("KGHDR"));
                    if(kgHdr.equals("Y")){
                        logoNme =util.nvl((String)pageDtl.get("LOGORO"));
                    }
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
                       //row = sheet.createRow(rowcount);
                       HSSFClientAnchor anchor=null;
                       if(kgHdr.equals("Y")){
                       imgWdt=0;
                       imgHt=0;
                       imgshort=4;
                       stRow=0;
                       rowcount=4;
                       anchor =new HSSFClientAnchor(0, 0, imgWdt, imgHt, (short)imgshort, rowcount,
                                                    (short)0, 0);
                       rowCt = stRow + 1;
                       cellCt = (short)(stCol + 5);
                       anchor.setAnchorType(2);
                       }else{
                           row = sheet.createRow(rowcount);
                       anchor =new HSSFClientAnchor(1, 1, imgWdt, imgHt, (short)imgshort, rowcount,
                                                    (short)(stCol+imgstCOl), (stRow +imgstRow));
                       rowCt = stRow + 2;
                       cellCt = (short)(stCol + 5);
                       anchor.setAnchorType(2);
                       }
                       int index =
                           hwb.addPicture(img_bytes.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG);
                       HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                       HSSFPicture logo = patriarch.createPicture(anchor, index);
                   
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
      int ADDWIDTH = Integer.parseInt(util.nvl((String)pageDtl.get("ADDWIDTH")));
      int addstartrow = Integer.parseInt(util.nvl((String)pageDtl.get("ADDSTARTROW"),"2"));
      
                 
                 if(kgHdr.equals("Y")){
                          HashMap byrLoyDtl=util.getDataForExl(info.getByrId(),info.getRlnId());
                          String companyNme = util.nvl((String)byrLoyDtl.get("COMPANYNME"),"");
                          String loyaltyLvl = util.nvl((String)byrLoyDtl.get("LOYALTYLVL"),"");
                          String emailid = util.nvl((String)byrLoyDtl.get("EMAILID"),"");
                          String yrVlu = util.nvl((String)byrLoyDtl.get("365"),"");
                          String terms = util.nvl((String)byrLoyDtl.get("TERMS"),"");
                          rowCt++;
                          addstartrow=0;
                          row = sheet.createRow(addstartrow);
                          cell = row.createCell(addstart);
                          cell.setCellValue("Company Name");
                          cell.setCellStyle(styleHdr);
                          sheet.addMergedRegion(merge(addstartrow, addstartrow, addstart, 6));
                          
                          cell = row.createCell(addstart+3);
                          cell.setCellValue(companyNme);
                          cell.setCellStyle(styleBold);
                          sheet.addMergedRegion(merge(addstartrow, addstartrow, addstart+3, 11));
                          
                          cell = row.createCell(addstart+9);
                          cell.setCellValue("Loyalty Level");
                          cell.setCellStyle(styleHdr);
                          sheet.addMergedRegion(merge(addstartrow, addstartrow, addstart+9, 16));
                          
                          cell = row.createCell(addstart+13);
                          cell.setCellValue(loyaltyLvl);
                          cell.setCellStyle(styleBold);
                          sheet.addMergedRegion(merge(addstartrow, addstartrow, addstart+13, 17));
                          
                          addstartrow+=1;
                          rowCt++;
                          row = sheet.createRow(addstartrow);
                          cell = row.createCell(addstart);
                          cell.setCellValue("Email Id");
                          cell.setCellStyle(styleHdr);
                      sheet.addMergedRegion(merge(addstartrow, addstartrow, addstart, 6));
                          
                          cell = row.createCell(addstart+3);
                          cell.setCellValue(emailid);
                          cell.setCellStyle(styleBold);
                      sheet.addMergedRegion(merge(addstartrow, addstartrow, addstart+3, 11));
                          
                          cell = row.createCell(addstart+9);
                          cell.setCellValue("365 Days Purchase");
                          cell.setCellStyle(styleHdr);
                      sheet.addMergedRegion(merge(addstartrow, addstartrow, addstart+9, 16));
                          
                          cell = row.createCell(addstart+13);
                          cell.setCellValue(yrVlu);
                          cell.setCellStyle(styleBold);
                      sheet.addMergedRegion(merge(addstartrow, addstartrow, addstart+13, 17));
                          
                          addstartrow+=1;
                          rowCt++;
                          row = sheet.createRow(addstartrow);
                          cell = row.createCell(addstart);
                          cell.setCellValue("Terms");
                          cell.setCellStyle(styleHdr);
                      sheet.addMergedRegion(merge(addstartrow, addstartrow, addstart, 6));
                          
                          cell = row.createCell(addstart+3);
                          cell.setCellValue(terms);
                          cell.setCellStyle(styleBold);
                      sheet.addMergedRegion(merge(addstartrow, addstartrow, addstart+3, 11));
                          
                          cell = row.createCell(addstart+9);
                          cell.setCellValue("Min Buy To Loyalty Upgrade");
                          cell.setCellStyle(styleHdr);
                      sheet.addMergedRegion(merge(addstartrow, addstartrow, addstart+9, 16));
                          
                          cell = row.createCell(addstart+13);
                          cell.setCellValue("300001");
                          cell.setCellStyle(styleBold);
                      sheet.addMergedRegion(merge(addstartrow, addstartrow, addstart+13, 17));
                      rowCt=2;
                     
                  }else{
                     
                 
                                   if(!add1.equals("")){
                                     rowCt++;
                                     if(NAME.equals("")){
                                     row = sheet.createRow(addstartrow);
                                     }
                                           cell = row.createCell(addstart);
                                           cell.setCellValue(add1);
                                           cell.setCellStyle(styleBold);
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
                                           cell.setCellStyle(styleBold);
                                           sheet.addMergedRegion(merge(addstartrow, addstartrow, addstart, ADDWIDTH));
                                      rowCt++;
                                  }
                     String note =util.nvl((String)pageDtl.get("NOTE"));
                     if(!note.equals("")){
                          if(note.equals(""))
                               addstartrow++;
                               row = sheet.createRow(addstartrow);
                               cell = row.createCell(addstart);
                               cell.setCellValue(note);
                               cell.setCellStyle(styleData_RLeft);
                               sheet.addMergedRegion(merge(addstartrow, addstartrow, addstart, ADDWIDTH));
                      }
                 }
                                  
                 
                
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

    public HSSFWorkbook WeeklyExcel(HashMap excelDtl,ArrayList mixSzLst,ArrayList mixSz1Lst,ArrayList mixClrLst,HashMap mixSizeMap, HttpServletRequest req) {

      session = req.getSession(false);

      gtMgr = (GtMgr)session.getAttribute("gtMgr");

       HashMap prp = info.getPrp();

        hwb = new HSSFWorkbook();

        autoColums = new TreeSet();

        HashMap mprp = info.getMprp();

        sheet = addSheet();

       

       HSSFFont fontttlHdr = hwb.createFont();

        fontttlHdr.setColor(HSSFColor.BLACK.index);

        fontttlHdr.setFontHeightInPoints((short)14);

        fontttlHdr.setFontName("Calibri");

        fontttlHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));

       HSSFCellStyle   stylettlHdr = hwb.createCellStyle();

        stylettlHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        stylettlHdr.setFont(fontttlHdr);

        stylettlHdr.setWrapText(true);

      

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

       

      

        numStyleDataBold = hwb.createCellStyle();

        numStyleDataBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        numStyleDataBold.setFont(fontHdr);

        numStyleDataBold.setBorderTop(HSSFCellStyle.BORDER_THIN);

        numStyleDataBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        numStyleDataBold.setBorderLeft(HSSFCellStyle.BORDER_THIN);

        numStyleDataBold.setBorderRight(HSSFCellStyle.BORDER_THIN);

        numStyleDataBold.setTopBorderColor(HSSFColor.BLACK.index);

        numStyleDataBold.setBottomBorderColor(HSSFColor.BLACK.index);

        numStyleDataBold.setLeftBorderColor(HSSFColor.BLACK.index);

        numStyleDataBold.setRightBorderColor(HSSFColor.BLACK.index);

       

         HSSFCellStyle  redStyleDataBold = hwb.createCellStyle();

        redStyleDataBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        redStyleDataBold.setFont(fontHdr);

        redStyleDataBold.setBorderTop(HSSFCellStyle.BORDER_THIN);

        redStyleDataBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        redStyleDataBold.setBorderLeft(HSSFCellStyle.BORDER_THIN);

        redStyleDataBold.setBorderRight(HSSFCellStyle.BORDER_THIN);

        redStyleDataBold.setTopBorderColor(HSSFColor.BLACK.index);

        redStyleDataBold.setBottomBorderColor(HSSFColor.BLACK.index);

        redStyleDataBold.setLeftBorderColor(HSSFColor.BLACK.index);

        redStyleDataBold.setRightBorderColor(HSSFColor.BLACK.index);

        redStyleDataBold.setFillForegroundColor(HSSFColor.RED.index);  

        redStyleDataBold.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

       

         HSSFCellStyle  genStyleDataBold = hwb.createCellStyle();

        genStyleDataBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        genStyleDataBold.setFont(fontHdr);

        genStyleDataBold.setBorderTop(HSSFCellStyle.BORDER_THIN);

        genStyleDataBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        genStyleDataBold.setBorderLeft(HSSFCellStyle.BORDER_THIN);

        genStyleDataBold.setBorderRight(HSSFCellStyle.BORDER_THIN);

        genStyleDataBold.setTopBorderColor(HSSFColor.BLACK.index);

        genStyleDataBold.setBottomBorderColor(HSSFColor.BLACK.index);

        genStyleDataBold.setLeftBorderColor(HSSFColor.BLACK.index);

        genStyleDataBold.setRightBorderColor(HSSFColor.BLACK.index);

        genStyleDataBold.setFillForegroundColor(HSSFColor.GREEN.index);  

        genStyleDataBold.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

    

          HSSFCellStyle purStyleDataBold = hwb.createCellStyle();

        purStyleDataBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        purStyleDataBold.setFont(fontHdr);

        purStyleDataBold.setBorderTop(HSSFCellStyle.BORDER_THIN);

        purStyleDataBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        purStyleDataBold.setBorderLeft(HSSFCellStyle.BORDER_THIN);

        purStyleDataBold.setBorderRight(HSSFCellStyle.BORDER_THIN);

        purStyleDataBold.setTopBorderColor(HSSFColor.BLACK.index);

        purStyleDataBold.setBottomBorderColor(HSSFColor.BLACK.index);

        purStyleDataBold.setLeftBorderColor(HSSFColor.BLACK.index);

        purStyleDataBold.setRightBorderColor(HSSFColor.BLACK.index);

        purStyleDataBold.setFillForegroundColor(HSSFColor.BLUE.index);

        purStyleDataBold.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

    

       

        row = newRow();

        addMergeCell(++cellCt, "0.18-0.96mix WEEKLY PRICE ANALYS (ESTIMATE)", stylettlHdr, merge(rowCt,rowCt,cellCt,20));

        addMergeCell(21, util.getDtTm(), stylettlHdr, merge(rowCt,rowCt,21,25));

        row =  newRow();

     
       int srtRowCnt = rowCt;

       int srtCellCnt = -1;

       int gridCnt =0;

       int maxRowCnt=0;

       int rowCnt =0;
        ArrayList mixSizLst=new ArrayList();

        for(int i=0;i<mixSzLst.size();i++){

        String mixSz = (String)mixSzLst.get(i);
               if(gridCnt!=0){

                    srtCellCnt=srtCellCnt+10;

                    cellCt =srtCellCnt;

                    rowCt=srtRowCnt;

                    row =  sheet.getRow(++rowCt);

                       addMergeCell(++cellCt, mixSz, numStyleDataBold, merge(rowCt,rowCt,cellCt,cellCt+8));

                    row =  sheet.getRow(++rowCt);

                    cellCt =srtCellCnt;

                }else{

                    row =  newRow();

                       addMergeCell(++cellCt, mixSz, numStyleDataBold, merge(rowCt,rowCt,cellCt,cellCt+8));

                    row =  newRow();

                   

                }

                  

                addCell(++cellCt, "NUM", numStyleDataBold);

                addCell(++cellCt, "LIST", numStyleDataBold);

                addCell(++cellCt, "22-08", numStyleDataBold);

                addCell(++cellCt, "Days", numStyleDataBold);

                addCell(++cellCt, "SRT", numStyleDataBold);

                addCell(++cellCt, "1-11-24", numStyleDataBold);

                addCell(++cellCt, "L.P%", numStyleDataBold);

                addCell(++cellCt, "F.%", numStyleDataBold);

                addCell(++cellCt, "CON", numStyleDataBold);

           ArrayList mixClrPrp =(ArrayList)mixSizeMap.get(mixSz);
                
                for(int j=0;j<mixClrPrp.size();j++){

                    String lClr = (String)mixClrPrp.get(j);

                    String cts = util.nvl((String)excelDtl.get("ROUND_"+mixSz+"_"+lClr+"_CTS"),"0");

//                    if(!cts.equals("")){
                        if(gridCnt!=0){

                      row =  sheet.getRow(++rowCt);

                      if(row==null){

                          rowCt=rowCt-1;

                        row =   newRow();

                      }

                      cellCt =srtCellCnt;

                        }else{

                        row =   newRow();

                        }

                       int days =Integer.parseInt(util.nvl((String)excelDtl.get("ROUND_"+mixSz+"_"+lClr+"_DAYS"),"0"));

                      if(days>=0 && days <=30)

                        addCell(++cellCt, lClr, genStyleDataBold);

                       if(days>30 && days <=60)

                        addCell(++cellCt, lClr, purStyleDataBold);

                       if(days>60)

                        addCell(++cellCt, lClr, redStyleDataBold);

                           

                        addCell(++cellCt, cts, styleData ,"N");

                        addCell(++cellCt, util.nvl((String)excelDtl.get("ROUND_"+mixSz+"_"+lClr+"_AVG"),"0"), styleData,"N");

                        addCell(++cellCt, util.nvl((String)excelDtl.get("ROUND_"+mixSz+"_"+lClr+"_DAYS"),"0"), styleData,"N");

                        addCell(++cellCt, util.nvl((String)excelDtl.get("ROUND_"+mixSz+"_"+lClr+"_SRT"),"0"), styleData,"N");

                        addCell(++cellCt, util.nvl((String)excelDtl.get("ROUND_"+mixSz+"_"+lClr+"_FA_PRI"),"0"), styleData,"N");

                        addCell(++cellCt, util.nvl((String)excelDtl.get("ROUND_"+mixSz+"_"+lClr+"_LP"),"0"), styleData,"N");

                        addCell(++cellCt, "", styleData);

                        addCell(++cellCt, "", styleData);

                        

                    }

                    if(gridCnt!=0){

                    row =  sheet.getRow(++rowCt);

                    if(row==null){

                        rowCt=rowCt-1;

                    row =   newRow();

                   

                    }

                    cellCt =srtCellCnt;

                    }else{

                    row =   newRow();

                    }

                   

                    addCell(++cellCt, "TTL", numStyleDataBold);

                    addCell(++cellCt, util.nvl((String)excelDtl.get("ROUND_"+mixSz+"_TTL_CTS")), numStyleDataBold);

                    addCell(++cellCt, util.nvl((String)excelDtl.get("ROUND_"+mixSz+"_TTL_AVG")), numStyleDataBold);

                    addCell(++cellCt, util.nvl((String)excelDtl.get("ROUND_"+mixSz+"_TTL_DAY")), numStyleDataBold);

                    addCell(++cellCt, util.nvl((String)excelDtl.get("ROUND_"+mixSz+"_TTL_SRT")), numStyleDataBold);

                    addCell(++cellCt,  util.nvl((String)excelDtl.get("ROUND_"+mixSz+"_TTL_FA_PRI")), numStyleDataBold);

                    addCell(++cellCt, "", numStyleDataBold);

                    addCell(++cellCt,"", numStyleDataBold);

                    addCell(++cellCt, "", numStyleDataBold);

                    

                   if(maxRowCnt < rowCt)

                       maxRowCnt = rowCt;

                    gridCnt++;

                    if(gridCnt==4){

                        gridCnt=0;

                        maxRowCnt=maxRowCnt+1;

                        rowCt=maxRowCnt;

                        srtRowCnt=maxRowCnt;

                        srtCellCnt = -1;

                    }

               

            }
        int szRowCnt =0;
        if(mixSz1Lst.size()>0){

        row =   newRow();

        row =   newRow();
       szRowCnt = rowCt;
        addMergeCell(++cellCt, "SIZE", numStyleDataBold, merge(rowCt,rowCt,cellCt,cellCt+10));

        row =  newRow();

        addCell(++cellCt, "SIZE", numStyleDataBold);

        addCell(++cellCt, "ADD%", numStyleDataBold);

        addCell(++cellCt, "AMT", numStyleDataBold);

        addCell(++cellCt, "S.A", numStyleDataBold);

        addCell(++cellCt, "Days", numStyleDataBold);

        addCell(++cellCt, "4WS%", numStyleDataBold);

        addCell(++cellCt, "8WS%", numStyleDataBold);

        addCell(++cellCt, "ADD4", numStyleDataBold);

        addCell(++cellCt, "4WSA", numStyleDataBold);

        addCell(++cellCt, "TRY", numStyleDataBold);

        addCell(++cellCt, "Day-0", numStyleDataBold);

        addCell(++cellCt, "4WS+/-", numStyleDataBold);

        for(int i=0;i<mixSz1Lst.size();i++){

              row =  newRow();

            String mixSz = (String)mixSz1Lst.get(i);

            addCell(++cellCt,mixSz, numStyleDataBold);

            addCell(++cellCt, util.nvl((String)excelDtl.get(mixSz+"_ADDPCT")), styleData,"N");

            addCell(++cellCt, util.nvl((String)excelDtl.get(mixSz+"_ADDVLU")), styleData,"N");

            addCell(++cellCt, util.nvl((String)excelDtl.get(mixSz+"_SLPCT")), styleData,"N");

            addCell(++cellCt, util.nvl((String)excelDtl.get(mixSz+"_DYS")), styleData,"N");

            addCell(++cellCt, util.nvl((String)excelDtl.get(mixSz+"_SLWPCT")), styleData,"N");

            addCell(++cellCt, util.nvl((String)excelDtl.get(mixSz+"_SLEWPCT")), styleData,"N");

              addCell(++cellCt,"", styleData);

            addCell(++cellCt, util.nvl((String)excelDtl.get(mixSz+"_VLUFW")), styleData,"N");

              addCell(++cellCt,"", styleData);

              addCell(++cellCt,"", styleData);

              addCell(++cellCt,"", styleData);

          }}
          
       
        if(mixClrLst.size()>0){
        int szCellCnt = cellCt+1;
        row =   sheet.getRow(szRowCnt-1);

        row =   sheet.getRow(szRowCnt++);
              cellCt = szCellCnt;
        addMergeCell(++cellCt, "CLARITY", numStyleDataBold, merge(rowCt,rowCt,cellCt,cellCt+10));

        row =  sheet.getRow(szRowCnt++);
              cellCt = szCellCnt;
        addCell(++cellCt, "CLARITY", numStyleDataBold);

        addCell(++cellCt, "ADD%", numStyleDataBold);

        addCell(++cellCt, "AMT", numStyleDataBold);

        addCell(++cellCt, "S.A", numStyleDataBold);

        addCell(++cellCt, "Days", numStyleDataBold);

        addCell(++cellCt, "4WS%", numStyleDataBold);

        addCell(++cellCt, "8WS%", numStyleDataBold);

        addCell(++cellCt, "ADD4", numStyleDataBold);

        addCell(++cellCt, "4WSA", numStyleDataBold);

        addCell(++cellCt, "TRY", numStyleDataBold);

        addCell(++cellCt, "Day-0", numStyleDataBold);

        addCell(++cellCt, "4WS+/-", numStyleDataBold);

        for(int i=0;i<mixClrLst.size();i++){

              row =   sheet.getRow(szRowCnt++);
              if(row==null){

                  rowCt=rowCt-1;

              row =   newRow();

              

              }
              cellCt = szCellCnt;
            String mixclr = (String)mixClrLst.get(i);

            addCell(++cellCt,mixclr, numStyleDataBold);

            addCell(++cellCt, util.nvl((String)excelDtl.get(mixclr+"_ADDPCT")), styleData,"N");

            addCell(++cellCt, util.nvl((String)excelDtl.get(mixclr+"_ADDVLU")), styleData,"N");

            addCell(++cellCt, util.nvl((String)excelDtl.get(mixclr+"_SLPCT")), styleData,"N");

            addCell(++cellCt, util.nvl((String)excelDtl.get(mixclr+"_DYS")), styleData,"N");

            addCell(++cellCt, util.nvl((String)excelDtl.get(mixclr+"_SLWPCT")), styleData,"N");

            addCell(++cellCt, util.nvl((String)excelDtl.get(mixclr+"_SLEWPCT")), styleData,"N");

              addCell(++cellCt,"", styleData);

            addCell(++cellCt, util.nvl((String)excelDtl.get(mixclr+"_VLUFW")), styleData,"N");

              addCell(++cellCt,"", styleData);

              addCell(++cellCt,"", styleData);

              addCell(++cellCt,"", styleData);

          }}


    return hwb;

    

    }
    
    
    
    public HSSFWorkbook GenExcelFormat(HashMap excelDtl, HttpServletRequest req) {

        session = req.getSession(false);
        hwb = new HSSFWorkbook();
        autoColums = new TreeSet();

        HashMap mprp = info.getMprp();

        sheet = addSheet();

        fontHdr = hwb.createFont();

        fontHdr.setColor(HSSFColor.BLACK.index);

        fontHdr.setFontHeightInPoints((short)12);

        fontHdr.setFontName("Arial");
      
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
        
        ArrayList keyList = (ArrayList)excelDtl.get("KEYLIST");
        HashMap hdrList = (HashMap)excelDtl.get("HDRLIST");
        ArrayList pktList = (ArrayList)excelDtl.get("PKTLIST");
        rowCt=-1;
        row = newRow();
        for(int i=0;i<keyList.size();i++){
            String lkey = (String)keyList.get(i);
            String hdrDsc = (String)hdrList.get(lkey);
           addCell(++cellCt, hdrDsc, styleHdr);
        }
        for(int j=0;j<pktList.size();j++){
            row =   newRow();

            GtPktDtl pktDtl = (GtPktDtl)pktList.get(j);
            for(int i=0;i<keyList.size();i++){
                String lkey = (String)keyList.get(i);
                String dflVal = util.nvl((String)hdrList.get(lkey+"_DFL"));
                String lkeyVal = util.nvl(pktDtl.getValue(lkey));
                if(!dflVal.equals(""))
                    lkeyVal=dflVal;
                addCell(++cellCt, lkeyVal, styleData);
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
    
    public HSSFWorkbook PriceSheetExcel(HttpServletRequest req,HashMap ExcelDtl) {

      session = req.getSession(false);

        
       
        hwb = new HSSFWorkbook();
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
          
       
       ArrayList subSheetLst =(ArrayList)ExcelDtl.get("SUBSHEETLST");
       HashMap sheetData = (HashMap)ExcelDtl.get("SUBSHEETDTL");
      if(subSheetLst!=null && subSheetLst.size()>0)
       for(int i=0;i<subSheetLst.size();i++){
           String lsheetNme = (String)subSheetLst.get(i);
           sheet = addSheet(lsheetNme);
           ArrayList hdrLst = (ArrayList)sheetData.get("HDRLST_"+lsheetNme);
           HashMap  gridDtlMap = (HashMap)sheetData.get("GRIDDTL_"+lsheetNme);
           if(hdrLst!=null && hdrLst.size()>0)
           for(int j=0;j<hdrLst.size();j++){
               String hdr_idn = (String)hdrLst.get(j);
               ArrayList colLst = (ArrayList)gridDtlMap.get(hdr_idn+"_COLLST");
               ArrayList rowLst = (ArrayList)gridDtlMap.get(hdr_idn+"_ROWLST");
               String subSize = (String)gridDtlMap.get(hdr_idn+"_SUBSIZE");
               HashMap gridData = (HashMap)gridDtlMap.get(hdr_idn+"_GRID");
               row = newRow();
               addCell(++cellCt, subSize, styleHdr);
               for(int k=0;k<rowLst.size();k++){
                   String lrow = util.nvl((String)rowLst.get(k));
                   addCell(++cellCt, lrow, styleHdr);
               }
               for(int x=0;x<colLst.size();x++){
                   row = newRow();
                   String lcol = util.nvl((String)colLst.get(x));
                   addCell(++cellCt, lcol, styleHdr);
                   for(int y=0;y<rowLst.size();y++){
                     String lrow = util.nvl((String)rowLst.get(y));
                     String lval = util.nvl((String)gridData.get(lcol+"#"+lrow)) ; 
                    addCell(++cellCt, lval, styleData);
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
    
    public HSSFWorkbook ConsolidatedSheetExcel(HttpServletRequest req,HashMap ExcelDtl) {

          session = req.getSession(false);
          hwb = new HSSFWorkbook();
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
          sheet = addSheet("ConsolidateSheet");
          HashMap prp = info.getPrp();
          ArrayList colPrpList = (ArrayList)prp.get("COLV");
          ArrayList clrPrpList = (ArrayList)prp.get("CLRV");
          ArrayList colColList = (ArrayList)ExcelDtl.get("COLLIST");
          ArrayList clrColList = (ArrayList)ExcelDtl.get("CLRLIST");
          ArrayList colList = new ArrayList();
          ArrayList clrList = new ArrayList();
          for(int i=0;i<colPrpList.size();i++){
              String col = (String)colPrpList.get(i);
              if(colColList.contains(col))
                  colList.add(col);
          }
        for(int i=0;i<clrPrpList.size();i++){
            String clr = (String)clrPrpList.get(i);
            if(clrColList.contains(clr))
                clrList.add(clr);
        }
          ArrayList LVL1LIST = (ArrayList)ExcelDtl.get("LVL1LIST");  
          HashMap lvl2Map = (HashMap)ExcelDtl.get("LVL2MAP");
          HashMap lvl3Map = (HashMap)ExcelDtl.get("LVL3MAP");
          HashMap sheetHdrMap = (HashMap)ExcelDtl.get("SHEETHDRMAP");
          HashMap SheetGridDtl = (HashMap)ExcelDtl.get("SHEETGRIDDTL");
          if(LVL1LIST!=null && LVL1LIST.size()>0){
              for(int i=0;i<LVL1LIST.size();i++){
                  String shape = (String)LVL1LIST.get(i);
                  ArrayList sizeLst =(ArrayList)lvl2Map.get(shape+"_SIZE");
                  if(sizeLst!=null && sizeLst.size()>0){
                  for(int j=0;j<sizeLst.size();j++){
                      String sizeStr = (String)sizeLst.get(j);
                      ArrayList lvl3List = (ArrayList)lvl3Map.get(shape+"_"+sizeStr+"_PRP") ;
                      if(lvl3List!=null && lvl3List.size()>0){
                     String bseKey = shape+"_"+sizeStr;
                     ArrayList baseHdrIdnLst = (ArrayList)sheetHdrMap.get(bseKey);
                    if(baseHdrIdnLst!=null && baseHdrIdnLst.size()>0){
                        String baseHdrIdn =(String)baseHdrIdnLst.get(0);
                        HashMap calGridDtlMap = new HashMap();
                    HashMap gridDtl = (HashMap)SheetGridDtl.get(baseHdrIdn);
                    if(gridDtl!=null && gridDtl.size()>0){
                      row = newRow();
                      int clrSize = clrList.size();
                      int colSize = colList.size();
                      addMergeCell(++cellCt,shape+"("+sizeStr+")", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+clrSize));
                      cellCt=cellCt+clrSize;
                      int lvl3Cell = cellCt;
                      int lvl3Size = lvl3List.size();
                        addCell(++cellCt,"", styleHdr);
                      for(int x=0;x<lvl3Size;x++){
                      String lvl3Prp = (String)lvl3List.get(x);
                      addMergeCell(++cellCt,lvl3Prp+"("+sizeStr+")", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+clrSize)); 
                      cellCt=cellCt+clrSize;
                      addCell(++cellCt,"", styleHdr);
                       }
                      
                     //Header Code
                     row = newRow();
                     addCell(++cellCt,"", styleHdr);
                     for(int k=0;k<clrSize;k++){
                        String clr = (String)clrList.get(k);
                         addCell(++cellCt,clr, styleHdr);
                         
                      }
                     addCell(++cellCt,"", styleHdr);
                     for(int x=0;x<lvl3Size;x++){
                         addCell(++cellCt,"", styleHdr);
                     for(int y=0;y<clrSize;y++){
                        String clr = (String)clrList.get(y);
                        addCell(++cellCt,clr, styleHdr);
                      }
                        addCell(++cellCt,"", styleHdr);
                     }
                  
                    
                    for(int b=0;b<colSize;b++){
                     String col = (String)colList.get(b);
                        row = newRow();
                     addCell(++cellCt,col, styleHdr);      
                     for(int a=0;a<clrSize;a++){
                     String clr = (String)clrList.get(a);  
                     String val = util.nvl((String)gridDtl.get(col+"_"+clr),"0");
                     addCell(++cellCt,val, styleData);   
                    }
                    addCell(++cellCt,"", styleHdr);  
                       
                   for(int c=0;c<lvl3Size;c++){
                   String prpVal = (String)lvl3List.get(c);         
                   String  prpKey =bseKey+"_"+prpVal;
                    HashMap calGridDtl = (HashMap)calGridDtlMap.get(prpKey);
                       if(calGridDtl==null)
                           calGridDtl = new HashMap();
                   String HdrIdn = (String)((ArrayList)sheetHdrMap.get(prpKey)).get(0);       
                    HashMap prpgridDtl = (HashMap)SheetGridDtl.get(HdrIdn);
                    addCell(++cellCt,col, styleHdr);      
                    for(int a=0;a<clrSize;a++){
                     String clr = (String)clrList.get(a);  
                     String val = util.nvl((String)prpgridDtl.get(col+"_"+clr),"0");
                     String bseval = util.nvl((String)gridDtl.get(col+"_"+clr),"0");
                     String calVal = String.valueOf(Integer.parseInt(val)+Integer.parseInt(bseval));
                     calGridDtl.put(col+"_"+clr, calVal);
                     addCell(++cellCt,val, styleData);   
                     }
                    calGridDtlMap.put(prpKey, calGridDtl);
                    addCell(++cellCt,"", styleHdr);    
                    } 
                   
                        
                    }
                    
                    row = newRow();
                    row = newRow();
                       
                        addCell(++cellCt,"", styleHdr);
                        for(int k=0;k<clrSize;k++){
                        
                             addCell(++cellCt,"", styleHdr);
                            
                         }
                        addCell(++cellCt,"", styleHdr);
                        for(int x=0;x<lvl3Size;x++){
                            addCell(++cellCt,"", styleHdr);
                        for(int y=0;y<clrSize;y++){
                           String clr = (String)clrList.get(y);
                           addCell(++cellCt,clr, styleHdr);
                         }
                           addCell(++cellCt,"", styleHdr);
                        }
                        
                        
                        for(int b=0;b<colSize;b++){
                        String col = (String)colList.get(b);
                           row = newRow();
                            addCell(++cellCt,"", styleHdr);
                            for(int k=0;k<clrSize;k++){
                            addCell(++cellCt,"", styleHdr);
                          }
                        addCell(++cellCt,"", styleHdr);
                        for(int x=0;x<lvl3Size;x++){
                         String prpVal = (String)lvl3List.get(x);         
                         String  prpKey =bseKey+"_"+prpVal;
                         HashMap calGridDtl = (HashMap)calGridDtlMap.get(prpKey);
                        addCell(++cellCt,col, styleHdr);      
                        for(int a=0;a<clrSize;a++){
                        String clr = (String)clrList.get(a);  
                        String val = util.nvl((String)calGridDtl.get(col+"_"+clr),"0");
                        addCell(++cellCt,val, styleData);   
                         }
                        addCell(++cellCt,"", styleHdr);
                       }
                    
                    
                        }
                    
                    
                    
                    
                    }
                   
                    }
                         
                    }
                      row = newRow();
                  }
              }
                      row = newRow();   
                  }}
          
        return hwb; 
          
    }
    
    
    public HSSFWorkbook PriceHistoryExcel(HttpServletRequest req,HashMap ExcelDtl) {

          session = req.getSession(false);
          hwb = new HSSFWorkbook();
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
          sheet = addSheet("Price History");
         
          HashMap prp = info.getPrp();
          ArrayList mathIdnList = (ArrayList)ExcelDtl.get("HDRIDNLIST");
          ArrayList rowList = (ArrayList)ExcelDtl.get("ROWLIST");
          ArrayList colList = (ArrayList)ExcelDtl.get("COLLIST");
          HashMap hdrDtlMap = (HashMap)ExcelDtl.get("HDRDTLMAP");
          String colval = (String)ExcelDtl.get("COL");
          String rowVal = (String)ExcelDtl.get("ROW");
          ArrayList colLst = (ArrayList)prp.get(colval+"V");
          ArrayList rowLst = (ArrayList)prp.get(rowVal+"V");
          ArrayList colValLst = new ArrayList();
          ArrayList rowValLst = new ArrayList();
          if(colLst!=null)
          for(int i=0;i<colLst.size();i++){
              String lprp = (String)colLst.get(i);
              if(colList.contains(lprp))
                  colValLst.add(lprp);
          }
          
        if(rowLst!=null)
        for(int i=0;i<rowLst.size();i++){
            String lprp = (String)rowLst.get(i);
            if(rowList.contains(lprp))
                rowValLst.add(lprp);
        }
          if(mathIdnList!=null){
          for(int i=0 ; i < mathIdnList.size();i++){
              
              String hdrIdn = (String)mathIdnList.get(i);
              String sheetNme = (String)hdrDtlMap.get(hdrIdn);
              String sheetNmeDte = (String)hdrDtlMap.get(hdrIdn+"_DTE");
              HashMap gridMap = (HashMap)ExcelDtl.get(hdrIdn);
              
              if(gridMap!=null && gridMap.size()>0){
                  
                  row = newRow();
                  addMergeCell(++cellCt, sheetNme+"("+sheetNmeDte+")", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+4));
                  row = newRow();
                  addCell(++cellCt,"", styleHdr);
                  for(int x=0;x<rowValLst.size();x++){
                      String rowPrpVal = (String)rowValLst.get(x);
                      addCell(++cellCt,rowPrpVal, styleHdr);
                  }
                  
                  for(int j=0;j<colValLst.size();j++){
                      String colVal = (String)colValLst.get(j);
                     
                      row = newRow();
                      addCell(++cellCt,colVal, styleHdr);
                      
                      for(int x=0;x<rowValLst.size();x++){
                          String rowPrpVal = (String)rowValLst.get(x);
                          String fldVal = (String)gridMap.get(colVal+"_"+rowPrpVal);
                          addCell(++cellCt,fldVal, styleData);
                          
                      }
                  }
                 
                  row = newRow();
              }
              
          }
          }
          return hwb;
    }
    public HSSFWorkbook OFFERXLBYR(HttpServletRequest req,HashMap ExcelDtl,String cnt) {

      session = req.getSession(false);

        
       
        hwb = new HSSFWorkbook();
          sheet = addSheet();
        autoColums = new TreeSet();
          fontHdr = hwb.createFont();

          fontHdr.setColor(HSSFColor.BLACK.index);

          fontHdr.setFontHeightInPoints((short)12);

          fontHdr.setFontName("Arial");
          
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
          ArrayList vwPrpList = (ArrayList)ExcelDtl.get("VWPRPLST");
          ArrayList pktDtlList = (ArrayList)ExcelDtl.get("OFFERPKTLIST");
       
          if(pktDtlList!=null && pktDtlList.size()>0){
              String pbyr = "";
              for(int i=0;i<pktDtlList.size();i++){
                  HashMap pktDtl = (HashMap)pktDtlList.get(i);
                  String lbyr = (String)pktDtl.get("byrNm");
                
                  if(!lbyr.equals(pbyr)){
                      if(i!=0)
                       row = newRow();
                      row = newRow();
                      addMergeCell(++cellCt, "Buyer Nme", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+2));
                     cellCt= cellCt+2;
                      addMergeCell(++cellCt, "Employee Nme", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+2));
                      cellCt= cellCt+2;  
                      addMergeCell(++cellCt, "Term", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+2));
                      cellCt= cellCt+2;  
                      row = newRow();
                      addMergeCell(++cellCt, lbyr, styleHdr, merge(rowCt,rowCt,cellCt,cellCt+2));
                      cellCt= cellCt+2;
                      addMergeCell(++cellCt, util.nvl((String)pktDtl.get("emp")), styleHdr, merge(rowCt,rowCt,cellCt,cellCt+2));
                      cellCt= cellCt+2;  
                      addMergeCell(++cellCt, util.nvl((String)pktDtl.get("term")), styleHdr, merge(rowCt,rowCt,cellCt,cellCt+2));
                      cellCt= cellCt+2;  
                      
                          row = newRow();
                          addCell(++cellCt,"Ordered", styleHdr);
                          addCell(++cellCt,"Party", styleHdr);
                          if(!cnt.equals("dj"))
                          addCell(++cellCt,"Deleted", styleHdr);
                          addCell(++cellCt,"Offer Date", styleHdr);
                          addCell(++cellCt,"LastMod", styleHdr);
                          addCell(++cellCt,"Vnm", styleHdr);
                          addCell(++cellCt,"Dis", styleHdr);
                          addCell(++cellCt,"Offer Discout", styleHdr);
                          if(!cnt.equals("dj"))
                          addCell(++cellCt,"Mem Dis", styleHdr);
                          if(!cnt.equals("ag") && !cnt.equals("dj"))
                          addCell(++cellCt,"Net dis", styleHdr);
                          addCell(++cellCt,"Quot", styleHdr);
                          addCell(++cellCt,"Offer Price", styleHdr);
                          if(!cnt.equals("dj"))
                          addCell(++cellCt,"Mem Price", styleHdr);
                          if(!cnt.equals("ag") && !cnt.equals("dj"))
                          addCell(++cellCt,"Net Price", styleHdr);
                          for(int j=0;j<vwPrpList.size();j++){
                              String lprp = (String)vwPrpList.get(j);
                              addCell(++cellCt,lprp, styleHdr);
//                              if(lprp.equals("RAP_RTE")){
//                                  addCell(++cellCt,"Off%", styleHdr);
//                                  addCell(++cellCt,"Bid Off%", styleHdr);
//                                  addCell(++cellCt,"Price$", styleHdr);
//                                  addCell(++cellCt,"Bid Price$", styleHdr);
//                              }
                          }
                        pbyr=lbyr;
                      }
                  row = newRow();

                  addCell(++cellCt,util.nvl((String)pktDtl.get("orStt")), styleData);

                  addCell(++cellCt, "-", styleData);
                  if(!cnt.equals("dj"))
                  addCell(++cellCt, "No", styleData);

                  addCell(++cellCt,util.nvl((String)pktDtl.get("frmDte")), styleData);

                  addCell(++cellCt,util.nvl((String)pktDtl.get("toDte")), styleData);

                  addCell(++cellCt,util.nvl((String)pktDtl.get("vnm")), styleData);
                  addCell(++cellCt,util.nvl((String)pktDtl.get("dis")), styleData);
                  addCell(++cellCt,util.nvl((String)pktDtl.get("offer_dis")), styleData);
                  if(!cnt.equals("dj"))
                  addCell(++cellCt,util.nvl((String)pktDtl.get("mr_dis")), styleData);
                  if(!cnt.equals("ag") && !cnt.equals("dj"))
                  addCell(++cellCt,util.nvl((String)pktDtl.get("net_dis")), styleData);
                  addCell(++cellCt,util.nvl((String)pktDtl.get("quot")), styleData);
                  addCell(++cellCt,util.nvl((String)pktDtl.get("offer_rte")), styleData);
                  if(!cnt.equals("dj"))
                  addCell(++cellCt,util.nvl((String)pktDtl.get("cmp")), styleData);
                  if(!cnt.equals("ag") && !cnt.equals("dj"))
                  addCell(++cellCt,util.nvl((String)pktDtl.get("net_rte")), styleData);
                  for(int j=0;j<vwPrpList.size();j++){
                      String lprp = (String)vwPrpList.get(j);
                      addCell(++cellCt,util.nvl((String)pktDtl.get(lprp)), styleData);
//                      if(lprp.equals("RAP_RTE")){
//                          addCell(++cellCt,util.nvl((String)pktDtl.get("dis")), styleData);
//                          addCell(++cellCt,util.nvl((String)pktDtl.get("offer_dis")), styleData);
//                          addCell(++cellCt,util.nvl((String)pktDtl.get("quot")), styleData);
//                          addCell(++cellCt,util.nvl((String)pktDtl.get("offer_rte")), styleData);
//                      }
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
    
    public HSSFWorkbook OFFERXLStone(HttpServletRequest req,HashMap ExcelDtl,String cnt) {

      session = req.getSession(false);

        
       
        hwb = new HSSFWorkbook();
          sheet = addSheet();
        autoColums = new TreeSet();
          fontHdr = hwb.createFont();

          fontHdr.setColor(HSSFColor.BLACK.index);

          fontHdr.setFontHeightInPoints((short)12);

          fontHdr.setFontName("Arial");
          
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
          ArrayList vwPrpList = (ArrayList)ExcelDtl.get("VWPRPLST");
          ArrayList pktDtlList = (ArrayList)ExcelDtl.get("OFFERPKTLIST");
       
          if(pktDtlList!=null && pktDtlList.size()>0){
              String pVnm = "";
              for(int i=0;i<pktDtlList.size();i++){
                  HashMap pktDtl = (HashMap)pktDtlList.get(i);
                  String lVnm = (String)pktDtl.get("vnm");
                
                  if(!lVnm.equals(pVnm)){
                      if(i!=0)
                       row = newRow();
                      row = newRow();
                        
                          addCell(++cellCt,"Ordered", styleHdr);
                          addCell(++cellCt,"Vnm", styleHdr);
                          addCell(++cellCt,"Dis", styleHdr);
                          addCell(++cellCt,"Offer Discout", styleHdr);
                          addCell(++cellCt,"Mem Dis", styleHdr);
                          if(!cnt.equals("ag"))
                          addCell(++cellCt,"Net dis", styleHdr);
                          addCell(++cellCt,"Quot", styleHdr);
                          addCell(++cellCt,"Offer Price", styleHdr);
                          addCell(++cellCt,"Mem Price", styleHdr);
                          if(!cnt.equals("ag"))
                          addCell(++cellCt,"Net Price", styleHdr);
                          for(int j=0;j<vwPrpList.size();j++){
                              String lprp = (String)vwPrpList.get(j);
                              addCell(++cellCt,lprp, styleHdr);
//                              if(lprp.equals("RAP_RTE")){
//                                  addCell(++cellCt,"Off%", styleHdr);
//                                  addCell(++cellCt,"Price$", styleHdr);
//                              }
                          }
                          
                          row = newRow();

                          addCell(++cellCt,util.nvl((String)pktDtl.get("orStt")), styleData);
                         addCell(++cellCt,util.nvl((String)pktDtl.get("vnm")), styleData);
                          addCell(++cellCt,util.nvl((String)pktDtl.get("dis")), styleData);
                          addCell(++cellCt,util.nvl((String)pktDtl.get("offer_dis")), styleData);
                          addCell(++cellCt,util.nvl((String)pktDtl.get("mr_dis")), styleData);
                          if(!cnt.equals("ag"))
                          addCell(++cellCt,util.nvl((String)pktDtl.get("net_dis")), styleData);
                          addCell(++cellCt,util.nvl((String)pktDtl.get("quot")), styleData);
                          addCell(++cellCt,util.nvl((String)pktDtl.get("offer_rte")), styleData);
                          addCell(++cellCt,util.nvl((String)pktDtl.get("cmp")), styleData);
                          if(!cnt.equals("ag"))
                          addCell(++cellCt,util.nvl((String)pktDtl.get("net_rte")), styleData);
                          for(int j=0;j<vwPrpList.size();j++){
                              String lprp = (String)vwPrpList.get(j);
                              addCell(++cellCt,util.nvl((String)pktDtl.get(lprp)), styleData);
//                              if(lprp.equals("RAP_RTE")){
//                                  addCell(++cellCt,util.nvl((String)pktDtl.get("dis")), styleData);
//                                  addCell(++cellCt,util.nvl((String)pktDtl.get("quot")), styleData);
//                              }
                          }
                         
                      row = newRow();
                      addMergeCell(++cellCt, "Buyer Nme", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+2));
                     cellCt= cellCt+2;
                      addMergeCell(++cellCt, "Employee Nme", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+2));
                      cellCt= cellCt+2;  
                      addMergeCell(++cellCt, "Term", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+2));
                      cellCt= cellCt+2;  
                      addCell(++cellCt,"Offer Date", styleHdr);
                      addCell(++cellCt,"LastMod", styleHdr);
                      addCell(++cellCt,"Offer Price$", styleHdr);
                      addCell(++cellCt,"Offer Off%", styleHdr);
                          pVnm=lVnm;
                      }
                  row = newRow();
                  addMergeCell(++cellCt, util.nvl((String)pktDtl.get("byrNm")), styleHdr, merge(rowCt,rowCt,cellCt,cellCt+2));
                  cellCt= cellCt+2;
                  addMergeCell(++cellCt, util.nvl((String)pktDtl.get("emp")), styleHdr, merge(rowCt,rowCt,cellCt,cellCt+2));
                  cellCt= cellCt+2;  
                  addMergeCell(++cellCt, util.nvl((String)pktDtl.get("term")), styleHdr, merge(rowCt,rowCt,cellCt,cellCt+2));
                  cellCt= cellCt+2;  
                  addCell(++cellCt,util.nvl((String)pktDtl.get("frmDte")), styleData);
                  addCell(++cellCt,util.nvl((String)pktDtl.get("toDte")), styleData);
                  addCell(++cellCt,util.nvl((String)pktDtl.get("offer_rte")), styleData);
                  addCell(++cellCt,util.nvl((String)pktDtl.get("offer_dis")), styleData);
                  

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
    
    public HSSFWorkbook EstimatedDuesExcel(HttpServletRequest req,HashMap ExcelDtl) {

      session = req.getSession(false);
        
         ArrayList recLst = (ArrayList)ExcelDtl.get("RECLST");
         ArrayList payLst = (ArrayList)ExcelDtl.get("PAYLST");
        if(recLst==null)
            recLst = new ArrayList();
        if(payLst==null)
            payLst = new ArrayList();
        int recSize = recLst.size();
        int paySize = payLst.size();
        int loopSize = recSize;
        if(paySize>recSize)
            loopSize=paySize;
        loopSize++;
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
          row = newRow();
        addCell(++cellCt,"Buyer Name", styleHdr);
        addCell(++cellCt,"Ref Date", styleHdr);
        addCell(++cellCt,"Ref Idn", styleHdr);
        addCell(++cellCt,"Cnt Idn", styleHdr);
        addCell(++cellCt,"Dys", styleHdr);
        addCell(++cellCt,"Due Date", styleHdr);
        addCell(++cellCt,"End Dys", styleHdr);
        addCell(++cellCt,"Amount($)", styleHdr);
        addCell(++cellCt,"Amount", styleHdr);
        addCell(++cellCt,"", styleHdr);
        addCell(++cellCt,"", styleHdr);
        addCell(++cellCt,"Buyer Name", styleHdr);
        addCell(++cellCt,"Ref Date", styleHdr);
        addCell(++cellCt,"Ref Idn", styleHdr);
        addCell(++cellCt,"Cnt Idn", styleHdr);
        addCell(++cellCt,"Dys", styleHdr);
        addCell(++cellCt,"Due Date", styleHdr);
        addCell(++cellCt,"End Dys", styleHdr);
        addCell(++cellCt,"Amount($)", styleHdr);
        addCell(++cellCt,"Amount", styleHdr);
        for(int i=0;i<loopSize;i++){
            row = newRow();
            if(i < recSize ){
                HashMap recDtl = (HashMap)recLst.get(i);
                addCell(++cellCt,util.nvl((String)recDtl.get("NME")), styleData);
                addCell(++cellCt,util.nvl((String)recDtl.get("REF_DTE")), styleData);
                addCell(++cellCt,util.nvl((String)recDtl.get("REF_IDN")), styleData);
                addCell(++cellCt,util.nvl((String)recDtl.get("CNTIDN")), styleData);
                addCell(++cellCt,util.nvl((String)recDtl.get("DYS")), styleData);
                addCell(++cellCt,util.nvl((String)recDtl.get("DUE_DTE")), styleData);
                addCell(++cellCt,util.nvl((String)recDtl.get("ENDDYS")), styleData);
                addCell(++cellCt,util.nvl((String)recDtl.get("CUR")), styleData);
                addCell(++cellCt,util.nvl((String)recDtl.get("AMT")), styleData);
                
            }else{
                String total ="";
                String ttlCur ="";
                String ttlAmt="";
                if(i==recSize){
                    total ="Total";
                    ttlCur = util.priceFormatter((String)ExcelDtl.get("RECUTTL"));
                    ttlAmt = util.priceFormatter((String)ExcelDtl.get("RECTTL"));
                }
                addCell(++cellCt,total, styleHdr);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,ttlCur, styleHdr);
                addCell(++cellCt,ttlAmt, styleHdr);
            }
            addCell(++cellCt,"", styleData);
            addCell(++cellCt,"", styleData);
            if(i < paySize){
                HashMap payDtl = (HashMap)payLst.get(i);
                addCell(++cellCt,util.nvl((String)payDtl.get("NME")), styleData);
                addCell(++cellCt,util.nvl((String)payDtl.get("REF_DTE")), styleData);
                addCell(++cellCt,util.nvl((String)payDtl.get("REF_IDN")), styleData);
                addCell(++cellCt,util.nvl((String)payDtl.get("CNTIDN")), styleData);
                addCell(++cellCt,util.nvl((String)payDtl.get("DYS")), styleData);
                addCell(++cellCt,util.nvl((String)payDtl.get("DUE_DTE")), styleData);
                addCell(++cellCt,util.nvl((String)payDtl.get("ENDDYS")), styleData);
                addCell(++cellCt,util.nvl((String)payDtl.get("CUR")), styleData);
                addCell(++cellCt,util.nvl((String)payDtl.get("AMT")), styleData);
                
            }else{
                String total ="";
                String ttlCur ="";
                String ttlAmt="";
                if(i==paySize){
                    total ="Total";
                    ttlCur = util.priceFormatter((String)ExcelDtl.get("PAYUTTL"));
                    ttlAmt = util.priceFormatter((String)ExcelDtl.get("PAYTTL"));
                }
                addCell(++cellCt,total, styleHdr);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,ttlCur, styleHdr);
                addCell(++cellCt,ttlAmt, styleHdr);
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
    
    public HSSFWorkbook OutstandingExcel(HttpServletRequest req,HashMap ExcelDtl) {

      session = req.getSession(false);
        
        ArrayList recLst = (ArrayList)ExcelDtl.get("RECLST");
        ArrayList payLst = (ArrayList)ExcelDtl.get("PAYLST");
        ArrayList dfltLst = (ArrayList)ExcelDtl.get("DFLTLST");
        if(recLst==null)
            recLst = new ArrayList();
        if(payLst==null)
            payLst = new ArrayList();
        if(dfltLst==null)
            dfltLst= new ArrayList();
        int recSize = recLst.size();
        int paySize = payLst.size();
        int dfltSize = dfltLst.size();
        int loopSize = recSize;
        if(paySize>recSize){
            loopSize=paySize;
            if(dfltSize > paySize)
            loopSize=dfltSize;
        }
         
        loopSize++;
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
        row = newRow();
        addMergeCell(++cellCt, "Received:", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+1));
        cellCt=cellCt+3;
        addMergeCell(++cellCt, "Pay:", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+1));
        cellCt=cellCt+3;
        addMergeCell(++cellCt, "Primary Accounts:", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+1));
        row = newRow();
        addCell(++cellCt,"Buyer Name", styleHdr);
        addCell(++cellCt,"Amount($)", styleHdr);
        addCell(++cellCt,"Amount", styleHdr);
        addCell(++cellCt,"", styleHdr);
        addCell(++cellCt,"Buyer Name", styleHdr);
        addCell(++cellCt,"Amount($)", styleHdr);
        addCell(++cellCt,"Amount", styleHdr);
        addCell(++cellCt,"", styleHdr);
        addCell(++cellCt,"Buyer Name", styleHdr);
        addCell(++cellCt,"Amount($)", styleHdr);
        addCell(++cellCt,"Amount", styleHdr);
        for(int i=0;i<loopSize;i++){
            row = newRow();
            if(i < recSize ){
                HashMap recDtl = (HashMap)recLst.get(i);
                addCell(++cellCt,util.nvl((String)recDtl.get("NME")), styleData);
                addCell(++cellCt,util.nvl((String)recDtl.get("CUR")), styleData,"N");
                addCell(++cellCt,util.nvl((String)recDtl.get("AMT")), styleData,"N");
                
            }else{
                String total ="";
                String ttlCur ="";
                String ttlAmt="";
                if(i==recSize){
                    total ="Total";
                    ttlCur = (String)ExcelDtl.get("RECUTTL");
                    ttlAmt = util.priceFormatter((String)ExcelDtl.get("RECTTL"));
                }
                addCell(++cellCt,total, styleHdr);
                addCell(++cellCt,ttlCur, styleHdr);
                addCell(++cellCt,ttlAmt, styleHdr);
            }
            addCell(++cellCt,"", styleData);
            if(i < paySize){
                HashMap payDtl = (HashMap)payLst.get(i);
                addCell(++cellCt,util.nvl((String)payDtl.get("NME")), styleData);
                addCell(++cellCt,util.nvl((String)payDtl.get("CUR")), styleData,"N");
                addCell(++cellCt,util.nvl((String)payDtl.get("AMT")), styleData,"N");
                
            }else{
                String total ="";
                String ttlCur ="";
                String ttlAmt="";
                if(i==paySize){
                    total ="Total";
                    ttlCur = (String)ExcelDtl.get("PAYUTTL");
                    ttlAmt = util.priceFormatter((String)ExcelDtl.get("PAYTTL"));
                }
                addCell(++cellCt,total, styleHdr);
                addCell(++cellCt,ttlCur, styleHdr);
                addCell(++cellCt,ttlAmt, styleHdr);
            }
            
            addCell(++cellCt,"", styleData);
            if(i < dfltSize){
                HashMap dftlDtl = (HashMap)dfltLst.get(i);
                addCell(++cellCt,util.nvl((String)dftlDtl.get("NME")), styleData);
                addCell(++cellCt,util.nvl((String)dftlDtl.get("CUR")), styleData,"N");
                addCell(++cellCt,util.nvl((String)dftlDtl.get("AMT")), styleData,"N");
                
            }else{
                String total ="";
                String ttlCur ="";
                String ttlAmt="";
                if(i==dfltSize){
                    total ="Total";
                    ttlCur = (String)ExcelDtl.get("DFLTUTTL");
                    ttlAmt = util.priceFormatter((String)ExcelDtl.get("DFLTTTL"));
                }
                addCell(++cellCt,total, styleHdr);
                addCell(++cellCt,ttlCur, styleHdr);
                addCell(++cellCt,ttlAmt, styleHdr);
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
    
    public HSSFWorkbook OutstandingTransExcel(HttpServletRequest req,HashMap ExcelDtl) {

      session = req.getSession(false);
        
        ArrayList purLst = (ArrayList)ExcelDtl.get("PURLST");
          ArrayList soldLst = (ArrayList)ExcelDtl.get("SOLDLST");
        if(purLst==null)
            purLst = new ArrayList();
        if(soldLst==null)
            soldLst = new ArrayList();
        int purSize = purLst.size();
        int soldSize = soldLst.size();
        int loopSize = purSize;
        if(soldSize>purSize)
            loopSize=soldSize;
        loopSize++;
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
        row = newRow();
        addMergeCell(++cellCt,"Purchase:", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+7));
        cellCt=cellCt+9;
        addMergeCell(++cellCt, "Sold:", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+1));
     
        
          row = newRow();
        addCell(++cellCt,"Buyer Name", styleHdr);
        addCell(++cellCt,"Ref Date", styleHdr);
        addCell(++cellCt,"Ref Idn", styleHdr);
        addCell(++cellCt,"Cnt Idn", styleHdr);
        addCell(++cellCt,"Dys", styleHdr);
        addCell(++cellCt,"Due Date", styleHdr);
        addCell(++cellCt,"End Dys", styleHdr);
        addCell(++cellCt,"Amount($)", styleHdr);
        addCell(++cellCt,"Amount", styleHdr);
        addCell(++cellCt,"", styleHdr);
      
        addCell(++cellCt,"Buyer Name", styleHdr);
        addCell(++cellCt,"Ref Date", styleHdr);
        addCell(++cellCt,"Ref Idn", styleHdr);
        addCell(++cellCt,"Cnt Idn", styleHdr);
        addCell(++cellCt,"Dys", styleHdr);
        addCell(++cellCt,"Due Date", styleHdr);
        addCell(++cellCt,"End Dys", styleHdr);
        addCell(++cellCt,"Amount($)", styleHdr);
        addCell(++cellCt,"Amount", styleHdr);
        for(int i=0;i<loopSize;i++){
            row = newRow();
            if(i < purSize ){
                HashMap purDtl = (HashMap)purLst.get(i);
                addCell(++cellCt,util.nvl((String)purDtl.get("NME")), styleData);
                addCell(++cellCt,util.nvl((String)purDtl.get("REF_DTE")), styleData);
                addCell(++cellCt,util.nvl((String)purDtl.get("REF_IDN")), styleData);
                addCell(++cellCt,util.nvl((String)purDtl.get("CNTIDN")), styleData);
                addCell(++cellCt,util.nvl((String)purDtl.get("DYS")), styleData);
                addCell(++cellCt,util.nvl((String)purDtl.get("DUE_DTE")), styleData);
                addCell(++cellCt,util.nvl((String)purDtl.get("ENDDYS")), styleData);
                addCell(++cellCt,util.nvl((String)purDtl.get("CUR")), styleData);
                addCell(++cellCt,util.nvl((String)purDtl.get("AMT")), styleData);
                
            }else{
                String total ="";
                String ttlCur ="";
                String ttlAmt="";
                if(i==purSize){
                    total ="Total";
                    ttlCur = (String)ExcelDtl.get("PURUTTL");
                    ttlAmt = util.priceFormatter((String)ExcelDtl.get("PURTTL"));
                }
                addCell(++cellCt,total, styleHdr);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,ttlCur, styleHdr);
                addCell(++cellCt,ttlAmt, styleHdr);
            }
            addCell(++cellCt,"", styleData);
          
            if(i < soldSize){
                HashMap soldDtl = (HashMap)soldLst.get(i);
                addCell(++cellCt,util.nvl((String)soldDtl.get("NME")), styleData);
                addCell(++cellCt,util.nvl((String)soldDtl.get("REF_DTE")), styleData);
                addCell(++cellCt,util.nvl((String)soldDtl.get("REF_IDN")), styleData);
                addCell(++cellCt,util.nvl((String)soldDtl.get("CNTIDN")), styleData);
                addCell(++cellCt,util.nvl((String)soldDtl.get("DYS")), styleData);
                addCell(++cellCt,util.nvl((String)soldDtl.get("DUE_DTE")), styleData);
                addCell(++cellCt,util.nvl((String)soldDtl.get("ENDDYS")), styleData);
                addCell(++cellCt,util.nvl((String)soldDtl.get("CUR")), styleData);
                addCell(++cellCt,util.nvl((String)soldDtl.get("AMT")), styleData);
                
            }else{
                String total ="";
                String ttlCur ="";
                String ttlAmt="";
                if(i==soldSize){
                    total ="Total";
                    ttlCur = (String)ExcelDtl.get("SOLDUTTL");
                    ttlAmt = util.priceFormatter((String)ExcelDtl.get("SOLDTTL"));
                }
                addCell(++cellCt,total, styleHdr);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,ttlCur, styleHdr);
                addCell(++cellCt,ttlAmt, styleHdr);
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
    
    public HSSFWorkbook TransDtlExcel(HttpServletRequest req) {

      session = req.getSession(false);
        
        
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
        row = newRow();
        ArrayList maintransDtlLst = (ArrayList)session.getAttribute("MAINTRANSLIST");
        ArrayList transDtlLst = (ArrayList)session.getAttribute("TRANSLIST");
        String ttlUCR= util.nvl((String)session.getAttribute("TTLUCR"));
        String ttlUCB= util.priceFormatter((String)session.getAttribute("TTLUDB"));
        String TTLCR=util.priceFormatter((String)session.getAttribute("TTLCR"));
        String ttlDB= util.priceFormatter((String)session.getAttribute("TTLDB"));
        for(int i=0;i<maintransDtlLst.size();i++){
           HashMap transMap = (HashMap)maintransDtlLst.get(i); 
           String ent_seq = util.nvl((String)transMap.get("ENT_SEQ"));
            String xrt = util.nvl((String)transMap.get("XRT"));
            String typ = util.nvl((String)transMap.get("TYP"));
            String idn =  util.nvl((String)transMap.get("IDN"));
            row = newRow();
            
            
        }
      
    
        Iterator it = autoColums.iterator();

        while(it.hasNext()) {

        String value=(String)it.next();

        int colId = Integer.parseInt(value);

        sheet.autoSizeColumn((short)colId, true);
        }
          return hwb;
    
    }
    
    public HSSFWorkbook TransDtlDTEExcel(HttpServletRequest req) {

      session = req.getSession(false);
        
        
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
        row = newRow();
        ArrayList transDtlLst = (ArrayList)session.getAttribute("TRANSLISTDTE");
        String ttlUCR= util.nvl((String)session.getAttribute("TTLUCRD"));
        String ttlUCB= (String)session.getAttribute("TTLUDBD");
        String TTLCR=util.priceFormatter((String)session.getAttribute("TTLCRD"));
        String ttlDB= util.priceFormatter((String)session.getAttribute("TTLDBD"));
        if(transDtlLst!=null && transDtlLst.size()>0){
            row = newRow();
            addCell(++cellCt,"Party Name", styleHdr);
            addCell(++cellCt,"Trans Dte", styleHdr);
            addCell(++cellCt,"Type", styleHdr);
            addCell(++cellCt,"Sub Type", styleHdr);
            addCell(++cellCt,"Ref Type", styleHdr);
            addCell(++cellCt,"Ref Idn", styleHdr);
            addCell(++cellCt,"Due Date", styleHdr);
            addCell(++cellCt,"Credit($)", styleHdr);
            addCell(++cellCt,"Debit($)", styleHdr);
            addCell(++cellCt,"Credit", styleHdr);
            addCell(++cellCt,"Debit", styleHdr);
            addCell(++cellCt,"Xrt", styleHdr);
            for(int i=0;i<transDtlLst.size();i++){
                row = newRow();
                HashMap transMap = (HashMap)transDtlLst.get(i); 
                addCell(++cellCt,util.nvl((String)transMap.get("NME")), styleData);
                addCell(++cellCt,util.nvl((String)transMap.get("T_DTE")), styleData);
                addCell(++cellCt,util.nvl((String)transMap.get("TYP")), styleData);
                addCell(++cellCt,util.nvl((String)transMap.get("SUBTYP")), styleData);
                addCell(++cellCt,util.nvl((String)transMap.get("REF_TYP")), styleData);
                addCell(++cellCt,util.nvl((String)transMap.get("REF_IDN")), styleData);
                addCell(++cellCt,util.nvl((String)transMap.get("DUE_DTE")), styleData);
                addCell(++cellCt,util.nvl((String)transMap.get("CRUAMT")), styleData,"N");
                addCell(++cellCt,util.nvl((String)transMap.get("DBUAMT")), styleData,"N");
                addCell(++cellCt,util.nvl((String)transMap.get("CRAMT")), styleData,"N");
                addCell(++cellCt,util.nvl((String)transMap.get("DBAMT")), styleData,"N");
                addCell(++cellCt,util.nvl((String)transMap.get("XRT")), styleData);
            }
            row = newRow();
            addCell(++cellCt,"", styleData);
            addCell(++cellCt,"", styleData);
            addCell(++cellCt,"", styleData);
            addCell(++cellCt,"", styleData);
            addCell(++cellCt,"", styleData);
            addCell(++cellCt,"", styleData);
            addCell(++cellCt,"Total", styleHdr);
            addCell(++cellCt,ttlUCR, styleHdr);
            addCell(++cellCt,ttlUCB, styleHdr);
            addCell(++cellCt,TTLCR, styleHdr);
            addCell(++cellCt,ttlDB, styleHdr);
            addCell(++cellCt,"", styleData);
        }
        Iterator it = autoColums.iterator();

        while(it.hasNext()) {

        String value=(String)it.next();

        int colId = Integer.parseInt(value);

        sheet.autoSizeColumn((short)colId, true);
        }
          return hwb;
    
    }
    
    
    public HSSFWorkbook getBranchReceivedXls(ArrayList itemHdr , ArrayList pktDtlList,HashMap BrchDlvDataMap) {
        
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
        
       sheet = addSheet();
        rowCt=-1;
        row = newRow();
        int pktDtlListsz=pktDtlList.size();       
        for (int j = 0; j < itemHdr.size(); j++) {
             String hdr = String.valueOf(itemHdr.get(j));
             if(hdr.indexOf("$") > -1){
                 hdr=hdr.replaceAll("\\$"," ");
                }
             addCell(++cellCt, hdr, styleHdr);
         }
        
        for (int j = 0; j < pktDtlListsz; j++) {
            row = newRow();
            String dlvIdn = (String)pktDtlList.get(j);
            HashMap hdrDtl = (HashMap)BrchDlvDataMap.get(dlvIdn+"_HDR");
            ArrayList dataDtl = (ArrayList)BrchDlvDataMap.get(dlvIdn+"_DTL");
            String dlvIdnDtl ="Dlv Idn :"+dlvIdn +"  BranchName :"+hdrDtl.get("brnNme")+" Date :"+hdrDtl.get("Dte");
            String idn = (String)hdrDtl.get("IDN");
            addMergeCell(++cellCt, dlvIdnDtl, styleHdr, merge(rowCt,rowCt,cellCt,cellCt+10));         
            for(int i=0; i<dataDtl.size();i++){
            row = newRow();
            HashMap pktDtl = (HashMap)dataDtl.get(i);
               addCell(++cellCt,  util.nvl((String)pktDtl.get("byr")), styleData);
                addCell(++cellCt, util.nvl((String)pktDtl.get("sal_idn")), styleData);
                addCell(++cellCt,  util.nvl((String)pktDtl.get("vnm")), styleData);
                addCell(++cellCt,  util.nvl((String)pktDtl.get("qty")), styleData);
                addCell(++cellCt, util.nvl((String)pktDtl.get("SH")), styleData);
                addCell(++cellCt, util.nvl((String)pktDtl.get("cts")), styleData);
                addCell(++cellCt, util.nvl((String)pktDtl.get("COL")), styleData);
                addCell(++cellCt, util.nvl((String)pktDtl.get("CLR")), styleData);
                addCell(++cellCt, util.nvl((String)pktDtl.get("FNLSAL")), styleData);
                addCell(++cellCt,  util.nvl((String)pktDtl.get("FNLSALDIS")), styleData);
                addCell(++cellCt, util.nvl((String)pktDtl.get("amt")), styleData);

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
    
    public HSSFWorkbook getConRtnGenXl(HashMap ExcelDtl,ArrayList itemHdr,ArrayList pktDtlList) {
            HashMap mprp = info.getMprp();
            HashMap prpDsc=new HashMap();
            hwb = new HSSFWorkbook();
            sheet = addSheet();
            autoColums = new TreeSet();
              fontHdr = hwb.createFont();
              fontUndHdr = hwb.createFont();
              
              fontHdr.setColor(HSSFColor.BLACK.index);
              fontHdr.setFontHeightInPoints((short)12);
              fontHdr.setFontName("Calibri");
              fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
              
            fontUndHdr.setColor(HSSFColor.BLACK.index);
            fontUndHdr.setFontHeightInPoints((short)12);
            fontUndHdr.setFontName("Calibri");
            fontUndHdr.setUnderline(HSSFFont.U_SINGLE);
            fontUndHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
            
              styleHdr = hwb.createCellStyle();
              styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
              styleHdr.setFont(fontHdr);
            styleHdr.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleHdr.setBorderTop(HSSFCellStyle.BORDER_THIN);
            styleHdr.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            styleHdr.setBorderRight(HSSFCellStyle.BORDER_THIN);
              styleHdr.setWrapText(true);
              
            styleUndHdr = hwb.createCellStyle();
            styleUndHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleUndHdr.setFont(fontUndHdr);
            styleUndHdr.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleUndHdr.setBorderTop(HSSFCellStyle.BORDER_THIN);
            styleUndHdr.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            styleUndHdr.setBorderRight(HSSFCellStyle.BORDER_THIN);
            styleUndHdr.setWrapText(true);
              
             HSSFCellStyle styleHdrLeft = hwb.createCellStyle();

            styleHdrLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);

            styleHdrLeft.setFont(fontHdr);
            styleHdrLeft.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleHdrLeft.setBorderTop(HSSFCellStyle.BORDER_THIN);
            styleHdrLeft.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            styleHdrLeft.setBorderRight(HSSFCellStyle.BORDER_THIN);

            styleHdrLeft.setWrapText(true);
            
            HSSFCellStyle styleUndHdrLeft = hwb.createCellStyle();

            styleUndHdrLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);

            styleUndHdrLeft.setFont(fontUndHdr);
            styleUndHdrLeft.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleUndHdrLeft.setBorderTop(HSSFCellStyle.BORDER_THIN);
            styleUndHdrLeft.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            styleUndHdrLeft.setBorderRight(HSSFCellStyle.BORDER_THIN);
            styleUndHdrLeft.setWrapText(true);
              
              fontData = hwb.createFont();

              fontData.setFontHeightInPoints((short)10);

              fontData.setFontName("Calibri");

              fontData.setColor(HSSFColor.BLACK.index);
            

              styleData = hwb.createCellStyle();

              styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleData.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleData.setBorderTop(HSSFCellStyle.BORDER_THIN);
            styleData.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            styleData.setBorderRight(HSSFCellStyle.BORDER_THIN);
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
            
            addMergeCell(++cellCt,"INVOICE", styleUndHdr, merge(rowCt,rowCt,cellCt,cellCt+11));
          
            row = newRow();
            addMergeCell(++cellCt,"CONSIGNMENT GOODS RETURN", styleUndHdr, merge(rowCt,rowCt,cellCt,cellCt+11));
            
            row = newRow();
            addMergeCell(++cellCt,"Exporter.:ANKIT GEMS (HONG KONG) LTD", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+11));
            row = newRow();
            addMergeCell(++cellCt,"Importers & Exporters of Polished Diamonds", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+11));
            row = newRow();
            addMergeCell(++cellCt,"403 Chevalier House, 45-51 Chatham Road South, T.S.T., Kowloon, Hong Kong", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+11));
            row = newRow();
            addMergeCell(++cellCt,"Tel.: 00852-27212642  Fax.: 00852-27213642", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+11));
            row = newRow();                                                                              
            row = newRow();
            addMergeCell(++cellCt, "Invoice No.", styleHdrLeft, merge(rowCt,rowCt,cellCt,cellCt));
            addMergeCell(++cellCt, "", styleHdrLeft, merge(rowCt,rowCt,cellCt,cellCt+3));
            //cellCt=cellCt+7;
            addMergeCell(++cellCt, "Date", styleHdr, merge(rowCt,rowCt,cellCt,cellCt)); 
            addMergeCell(++cellCt,util.nvl(util.getToDte()), styleHdr, merge(rowCt,rowCt,cellCt,cellCt+1));
            
            row = newRow();
            
            addMergeCell(++cellCt, "Buyer:", styleUndHdrLeft, merge(rowCt,rowCt,cellCt,cellCt));
            addMergeCell(++cellCt, "", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+3));
            cellCt=cellCt+7;
          
            addMergeCell(++cellCt, "", styleHdr, merge(rowCt,rowCt,cellCt,cellCt)); 
            addMergeCell(++cellCt,"CIF: MUMBAI", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+1));
            
            row = newRow();
            addMergeCell(++cellCt,"ANKIT GEMS PVT. LTD", styleHdrLeft, merge(rowCt,rowCt,cellCt,cellCt+11));
            row = newRow();
            addMergeCell(++cellCt,"DW 6251.52, D TOWER, BHARAT DIAMOND BOURSE, BANDRA KURLA COMPLEX,G BLOCK", styleHdrLeft, merge(rowCt,rowCt,cellCt,cellCt+11));
            row = newRow();
            addMergeCell(++cellCt,"BANDRA (EAST) MUMBAI - 400051", styleHdrLeft, merge(rowCt,rowCt,cellCt,cellCt+11));
            row = newRow();
            addMergeCell(++cellCt,"Tel.: 91-22-43548800", styleHdrLeft, merge(rowCt,rowCt,cellCt,cellCt+11));
            row = newRow();
            addMergeCell(++cellCt,"POLISHED DIAMONDS", styleHdr, merge(rowCt,rowCt,cellCt,cellCt+11));
            
            row = newRow();
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "", styleHdr);
            int pktDtlListsz=pktDtlList.size();
            for (int j = 0; j < itemHdr.size(); j++) {
                 String hdr = String.valueOf(itemHdr.get(j)).toUpperCase();
                 if(prpDsc.containsKey(hdr)){
                     hdr=(String)prpDsc.get(hdr);
                     }
                 if(hdr.indexOf("$") > -1){
                     hdr=hdr.replaceAll("\\$"," ");
                    }
                 addCell(++cellCt, "", styleHdr);
             }
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "USD", styleHdr);
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "", styleHdr);
            
            row = newRow();
            addCell(++cellCt, "SR NO", styleHdr);
            addCell(++cellCt, "EXP NO", styleHdr);
            pktDtlListsz=pktDtlList.size();
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
            addCell(++cellCt, "REF_NO", styleHdr);
            addCell(++cellCt, "CERTI_NO", styleHdr);
            addCell(++cellCt, "PCS", styleHdr);
            addCell(++cellCt, "CARATS", styleHdr);
            addCell(++cellCt, "RATE", styleHdr);
            addCell(++cellCt, "AMT", styleHdr);
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "", styleHdr);
            
            for (int j = 0; j < pktDtlListsz; j++) {
            row = newRow();
            addCell(++cellCt, String.valueOf(j+1), styleData);
            addCell(++cellCt, "", styleData);
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
                addCell(++cellCt, util.nvl((String)pktDtl.get("vnm")), styleData);
                addCell(++cellCt, util.nvl((String)pktDtl.get("cert_no")), styleData);
                addCell(++cellCt, util.nvl((String)pktDtl.get("qty")), styleData);
                addCell(++cellCt, util.nvl((String)pktDtl.get("cts")), styleData);
                addCell(++cellCt, util.nvl((String)pktDtl.get("rte")), styleData);
                addCell(++cellCt, util.nvl((String)pktDtl.get("vlu")), styleData);
            }

            row = newRow();
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "", styleHdr);
            pktDtlListsz=pktDtlList.size();
            for (int j = 0; j < itemHdr.size(); j++) {
                 String hdr = String.valueOf(itemHdr.get(j)).toUpperCase();
                 if(prpDsc.containsKey(hdr)){
                     hdr=(String)prpDsc.get(hdr);
                     }
                 if(hdr.indexOf("$") > -1){
                     hdr=hdr.replaceAll("\\$"," ");
                    }
                 addCell(++cellCt, "", styleHdr);
             }
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "TOTAL-", styleHdr);
            addCell(++cellCt, util.nvl((String)ExcelDtl.get("qty")), styleHdr);
            addCell(++cellCt, util.nvl((String)ExcelDtl.get("cts")), styleHdr);
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, util.nvl((String)ExcelDtl.get("vlu")), styleHdr);
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "", styleHdr);
            addCell(++cellCt, "", styleHdr);
            row = newRow();
            addMergeCell(++cellCt,"The diamond herein invoiced have been purchased from legitimate sources not involved in funding conflict and in 							", styleHdrLeft, merge(rowCt,rowCt,cellCt,cellCt+11));
            row = newRow();
            addMergeCell(++cellCt,"compliance with United Nations Resolutions. The seller hereby gurantees that these diamonds are conflict free based											", styleHdrLeft, merge(rowCt,rowCt,cellCt,cellCt+11));
            row = newRow();
            addMergeCell(++cellCt,"on personal knowledge and/ or written guarantes provided by supplier of these diamonds.											", styleHdrLeft, merge(rowCt,rowCt,cellCt,cellCt+11));
               
            
            Iterator it= autoColums.iterator();
            while(it.hasNext()) {
            String value=(String)it.next();
            int colId = Integer.parseInt(value);
            sheet.autoSizeColumn((short)colId, true);
            }
            
            
              return hwb;
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
            try {
                cell.setCellValue(Double.parseDouble(val));
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            } catch (NumberFormatException nfe) {
                // TODO: Add catch code
                cell.setCellValue(val);
            }
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
         String sheetNme = "Sheet"+ ++sheetCtr;
        return addSheet(sheetNme);
     }
    public HSSFSheet addSheet(String SheetNme) {
        HSSFSheet lsheet = hwb.createSheet(SheetNme);
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

  
}
