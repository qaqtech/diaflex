package ft.com;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

public class LabComparisionExcel extends HttpServlet {
    private static final String CONTENT_TYPE = "getServletContext()/vnd-excel";
    DBMgr db = null;
    DBUtil util = null;
    InfoMgr info = null;
    LogMgr log = null;
    HttpSession session;

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
        fontDataBold = null,
        fontDataBoldWh = null;
    
    HSSFCellStyle 
        styleTopBrdr = null,
        styleDataBrdr = null,
        styleHdr = null,
        styleHdrBk = null,
        styleCntr = null,
        styleRght = null,
        styleCentBold = null,
        styleData = null,
        styleRghtBold = null;
        
    HSSFWorkbook hwb = null;
    HSSFSheet sheet = null;
    HSSFRow row = null ;
    HSSFCell cell = null;
    ArrayList vwPrpLst = new ArrayList();
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db = new DBMgr();
        util = new DBUtil();
        info = new InfoMgr();
        log = new LogMgr();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
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
        cell.setCellValue(getStr(val));      
        cell.setCellStyle(styl);
        
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
    
}
