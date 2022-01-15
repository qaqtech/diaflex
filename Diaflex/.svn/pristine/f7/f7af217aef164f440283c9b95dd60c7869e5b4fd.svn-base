package ft.com;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

public class PriSheetExcel {
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
        fontDataBoldWh = null,hlink_font=null;
      
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
        styleDataRed =null,styleDataGN=null, hlink_style =null,numStyle=null;
    HSSFWorkbook hwb = null;
    HSSFSheet sheet = null;
    HSSFRow row = null ;
    HSSFCell cell = null;
    TreeSet autoColums = null;
    
    public void init(DBMgr db, DBUtil util, InfoMgr info,GtMgr gtMgr) {
        this.db   = db;
        this.util = util;
        this.info = info;
        this.gtMgr = gtMgr;
    }
    
      
    public HSSFWorkbook getGenXlObj(HashMap excelDtl,ArrayList mpriIdnLst, HttpServletRequest req,DBMgr db) {
      session = req.getSession(false);
      gtMgr = (GtMgr)session.getAttribute("gtMgr");
      hwb = new HSSFWorkbook();
      autoColums = new TreeSet();
      sheet = addSheet();
       row = newRow();
        fontHdr = hwb.createFont();
        fontHdr.setColor(HSSFColor.BLACK.index);
        fontHdr.setFontHeightInPoints((short)10);
        fontHdr.setFontName("Arial");
        fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
        
        styleHdr = hwb.createCellStyle();
        styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleHdr.setFont(fontHdr);
        
        fontData = hwb.createFont();
        fontData.setFontHeightInPoints((short)10);
        fontData.setFontName("Arial");
        styleData = hwb.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setFont(fontData);
        for(int i=0;i<mpriIdnLst.size();i++){
         String mpriIdn = (String)mpriIdnLst.get(i);
         HashMap sheetDtl = (HashMap)excelDtl.get(mpriIdn);
         String shtNme = (String)sheetDtl.get("SHEETNME");
         String commPrpstr = (String)sheetDtl.get("COMMPRP");
          row = newRow();
          row = newRow();
        String shape=util.nvl((String)sheetDtl.get("SHAPE"));
        String wt_fr=util.nvl((String)sheetDtl.get("WT_FR"));
        String wt_to=util.nvl((String)sheetDtl.get("WT_TO"));
          cellCt=1;
          addMergeCell(++cellCt, "Sheet Name :", styleHdr, new CellRangeAddress(rowCt, rowCt,cellCt, cellCt++));
          addMergeCell(++cellCt, shtNme, styleHdr, new CellRangeAddress(rowCt, rowCt,cellCt, cellCt+=2));
          String[] commPrp = commPrpstr.split("@");
            if(commPrp.length>0){
                row = newRow();
                cellCt=1;
           for(int j=0;j<commPrp.length;j++){
                String lprpLst = commPrp[j];
               String[] lprp = lprpLst.split("#");
                addCell(++cellCt, lprp[0], styleHdr);
                addMergeCell(++cellCt, lprp[1], styleHdr, new CellRangeAddress(rowCt,rowCt,cellCt,cellCt+=2));
            }}
            
            
            
            HashMap gridDtl = (HashMap)sheetDtl.get("GridDtl");
            ArrayList keyIdnLst = (ArrayList)sheetDtl.get("GridKeyList");
            if(keyIdnLst!=null && keyIdnLst.size()>0){
        
                
                row = newRow();
                cellCt=1;
                ArrayList rowLst = new ArrayList();
                ArrayList colList = new ArrayList();
                addCell(++cellCt,"", styleData);
                for(int k=0;k<keyIdnLst.size();k++){
                   
                    String key = (String)keyIdnLst.get(k);
                    String[] keyLst = key.split("#");
                    if(keyLst.length>1){
                    String colVal = keyLst[0];
                    String rowVal = keyLst[1];
                    
                    if(!colList.contains(colVal))
                      colList.add(colVal);
                    if(!rowLst.contains(rowVal)){
                    rowLst.add(rowVal);
                     addCell(++cellCt,rowVal, styleData);
                    }
                   
                    }
                }
                if(!shape.equals("")){
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                addCell(++cellCt,"", styleData);
                for(int x=0;x<rowLst.size();x++) {
                    String rowVal = (String)rowLst.get(x);
                     addCell(++cellCt,rowVal, styleData);
                 }
                }
                    
                
                for(int y=0;y<colList.size();y++) {
                    String colVal = (String)colList.get(y);
                    row = newRow();
                    cellCt=1;
                    addCell(++cellCt,colVal, styleData);
                for(int x=0;x<rowLst.size();x++) {
                    String rowVal = (String)rowLst.get(x);
                    String fldVal = util.nvl((String)gridDtl.get(colVal+"#"+rowVal));
                   
                    addCell(++cellCt, fldVal, styleData);
                    
                 } 
                 if(!shape.equals("")){
                        addCell(++cellCt, "", styleData);
                        addCell(++cellCt, "", styleData);
                        addCell(++cellCt,colVal, styleData);
                     for(int x=0;x<rowLst.size();x++) {
                       String rowVal = (String)rowLst.get(x);
                       String fldVal =  util.nvl((String)gridDtl.get(colVal+"#"+rowVal));
                        String rapPrice = "select RAP_PRC(r_sh => ? ,r_wt => ?,r_pu => ? ,r_co => ?,rt => ?) price from dual";
                        ArrayList ary=new ArrayList();
                        ary.add(shape);
                        ary.add(wt_fr);
                        ary.add(rowVal);
                        ary.add(colVal);
                        ary.add("0");
                     try {
                       String price="";
                        ArrayList rsLst = db.execSqlLst("rapPrice", rapPrice, ary);
                        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
                        ResultSet rs = (ResultSet)rsLst.get(1);
                       if (rs.next()) {
                        price =util.nvl(rs.getString("price"));
                       }
                        rs.close();
                        pst.close();
                        if(!fldVal.equals("") && !price.equals("")){
                            double val = Double.parseDouble(fldVal);
                            double rapRte = Double.parseDouble(price);
                            double calVal = rapRte*(100 - val)/100;
                            addCell(++cellCt, String.valueOf(calVal), styleData);
                        }
                        rs.close();
                        pst.close();
                        } catch (SQLException sqle) {
                                   // TODO: Add catch code
                                   sqle.printStackTrace();
                        }
                         
                      
                       
                     }
                 }
                    
                    }
                
                
          
            }
        }
        
        
        return hwb;
    }
    public PriSheetExcel() {
        super();
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

}
