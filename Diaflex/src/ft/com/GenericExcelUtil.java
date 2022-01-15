package ft.com;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
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
import org.apache.poi.hssf.util.HSSFColor;

public class GenericExcelUtil extends HttpServlet {
    private static final String CONTENT_TYPE = "getServletContext()/vnd-excel";
    DBMgr db = null;
    DBUtil util = null;
    InfoMgr info = null;
    LogMgr log = null;
    HttpSession session;
    SortedSet autoCols = null;
    int sheetCtr = 0 ;
    int rowCt = 0;
    int cellCt = -1;
    int ttlCols = 10; 
    int pSrt = 0 ;
    int  colNum=0;
    String tblNm = "" ;
    HSSFFont 
         fontHdr = null,
         fontData = null;
    HSSFCellStyle 
        styleHdr = null,
        styleData = null;
    HSSFWorkbook hwb = null;
    HSSFSheet sheet = null;
    HSSFRow row = null ;
    HSSFCell cell = null;
    ArrayList vwPrpLst = new ArrayList();
    HashMap mprp = null;
    HashMap prp = null;
    HashMap exlFormat = null;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db = new DBMgr();
        util = new DBUtil();
        info = new InfoMgr();
        log = new LogMgr();
    }


    public void doPost(HttpServletRequest request,
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
        String fileNme =util.nvl(request.getParameter("filename"));
        fileNme = fileNme+"_"+util.getToDteTime()+".xls";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Content-Disposition","attachment;filename="+fileNme);
        
        mprp = info.getMprp();
        prp = info.getPrp();
         exlFormat =  util.EXLFormat();
        String fontNme = util.nvl(request.getParameter("fontName"));
        String fontSz = util.nvl(request.getParameter("size"));
        String mdl = util.nvl(request.getParameter("mdl"));
        if(fontSz.equals(""))
            fontSz = "10";
        int fontSize = Integer.parseInt(fontSz);
        hwb = new HSSFWorkbook();
        fontHdr = hwb.createFont();
        fontHdr.setFontHeightInPoints((short)10);
        fontHdr.setFontName(fontNme);
        fontHdr.setBoldweight((HSSFFont.BOLDWEIGHT_BOLD));
        
        fontData = hwb.createFont();
        fontData.setFontHeightInPoints((short)fontSize);
        fontData.setFontName(fontNme);
        
        styleHdr = hwb.createCellStyle();
        styleHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleHdr.setFont(fontHdr);
        
        styleData = hwb.createCellStyle();
        styleData.setFont(fontData);
        styleData.setWrapText(true);
        vwPrpLst = getVwList(mdl);
        autoCols = new TreeSet();
        sheet = addSheet();
        String add = util.nvl(request.getParameter("add"));
        if(!add.equals(""))
          addAddress(request);
        addTableHed(request);
        ArrayList pktList = popFrmGT();
        for(int i=0 ; i < pktList.size() ; i++){
            HashMap pktDtl = (HashMap)pktList.get(i);
            row = newRow();
            addCell((String)pktDtl.get("sr") , styleData);
            addCell((String)pktDtl.get("vnm"),styleData);
            for (int j = 0; j < vwPrpLst.size(); j++) {
                String lprp = (String)vwPrpLst.get(j);
                addCell((String)pktDtl.get(lprp),styleData);
            }
            String SUTVAL = util.nvl(request.getParameter("SUTVAL"));
            if(!SUTVAL.equals("")){
                addCell((String)pktDtl.get("sutVal"), styleData);
            }
        }
        
        Iterator it=autoCols.iterator();
        while(it.hasNext()) {
        String value=(String)it.next();
        int colId = Integer.parseInt(value);
        sheet.autoSizeColumn((short)colId, true);

        }
        String QC = util.nvl(request.getParameter("QC"));
        String PRC = util.nvl(request.getParameter("PRC"));
        if(!QC.equals("") || !PRC.equals(""))
         addTotals(request);
        OutputStream out = response.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
    }
    public ArrayList popFrmGT(){
        String srchQ =
          " select sk1, stk_idn, cts crtwt, quot , "+
          " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis " +
          " , stk_idn, rap_rte , vnm, kts_vw kts, cert_lab cert, cert_no, rmk, img, to_char(trunc(cts,2),'9990.00') cts, to_char(trunc(cts,2)*prte ,'99999990.00') sutval , "+
          " rap_rte, trunc(cts,2)*rap_Rte rap_vlu  , qty  "+
          ", rmk , cmnt cmnt ,to_char(trunc(cts,2) * quot, '99999990.00') amt, decode(stt, 'MKAV', 'Available', 'MKIS', 'MEMO', 'MKTL',' ', stt) stt " ;
        
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
        ArrayList  ary = new ArrayList();
        ary.add("Z");
        ResultSet rs = db.execSql("gtFech", srchQ, ary);
        int count=0;
        ArrayList pktList = new ArrayList();
            try {
                while(rs.next()) {
                    count++;
                    String stkIdn = util.nvl(rs.getString("stk_idn"));
                    String stt = util.nvl(rs.getString("stt"));
                
                   
                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap.put("stt", stt);
                    pktPrpMap.put("sr", String.valueOf(count));
                    String vnm = util.nvl(rs.getString("vnm"));
                    pktPrpMap.put("vnm",vnm);
                    pktPrpMap.put("stk_idn", stkIdn);
                    pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                    pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                    pktPrpMap.put("quot",util.nvl(rs.getString("quot")));
                    pktPrpMap.put("sutVal",util.nvl(rs.getString("sutval")));
                  
                    for(int j=0; j < vwPrpLst.size(); j++){
                         String prp = (String)vwPrpLst.get(j);
                          
                          String fld="prp_";
                          if(j < 9)
                                  fld="prp_00"+(j+1);
                          else    
                                  fld="prp_0"+(j+1);
                          
                          String val = util.nvl(rs.getString(fld)) ;
                         
                            
                            pktPrpMap.put(prp, val);
                             }
                                  
                        pktList.add(pktPrpMap);
                    }
                rs.close();
            } catch (SQLException sqle) {

                // TODO: Add catch code
                sqle.printStackTrace();
            }
            
            return pktList;
    }
    
    public void addTableHed(HttpServletRequest req){
        row = newRow();
        addCell("Sr.No", styleHdr);
        addCell("Packet", styleHdr);
        for(int i=0;i<vwPrpLst.size();i++){
            String lprp = (String)vwPrpLst.get(i);
            String lprpD = (String)mprp.get(lprp+"D");
            addCell(lprpD, styleHdr);
        }
        String SUTVAL = util.nvl(req.getParameter("SUTVAL"));
        if(!SUTVAL.equals("")){
            addCell("Value", styleHdr);
        }
    }
    public void addAddress(HttpServletRequest req){
        
        String add1 = util.nvl((String)exlFormat.get("ADD1"));
     
         if(!add1.equals("")){
        row = newRow();
        
        cell = row.createCell(++cellCt);
        cell.setCellValue(getStr(add1 , fontData));
        cell.setCellStyle(styleHdr);
        sheet.addMergedRegion(merge(rowCt, rowCt, 0, vwPrpLst.size()));
        
         }
         
        String add2 = util.nvl((String)exlFormat.get("ADD2"));
       
        if(!add2.equals("")){
        row = newRow();
        
        cell = row.createCell(++cellCt);
        cell.setCellValue(getStr(add2, fontData));
        cell.setCellStyle(styleHdr);
        sheet.addMergedRegion(merge(rowCt, rowCt, 0, vwPrpLst.size()));
        //To    
     }
        row = newRow();
    }
    public void addTotals(HttpServletRequest req) {
         String QC = util.nvl(req.getParameter("QC"));
         String PRC = util.nvl(req.getParameter("PRC"));
        row = newRow();     
        ResultSet rs1 = null;
        String ttlQty="";
        String ttlCts ="";
        String ttlVlu = "";
        String getTtls = " select to_char(trunc(sum(trunc(cts,2)),2),'9990.00') cts, trunc(sum(qty)) qty ,  to_char(sum((trunc(cts,2) * nvl(prte,1))),'9999999990.99') vlu  from gt_srch_rslt where flg = 'Z' ";
        ArrayList ary = new ArrayList();
         rs1 = db.execSql(" Ttls ", getTtls, ary);
           try {
                if (rs1.next()) {
               ttlQty = util.nvl(rs1.getString("qty"));
               ttlCts = util.nvl(rs1.getString("cts"));
               ttlVlu = util.nvl(rs1.getString("vlu"));
                }
                rs1.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        
         addCell("TOTAL", styleData);
         if(!QC.equals("")){
         cellCt = vwPrpLst.indexOf("CRTWT")-2;
         sheet.addMergedRegion(merge(rowCt, rowCt, 0, cellCt));
         addCell("QTY", styleData);
         addCell(ttlQty, styleData);
         addCell("Total Cts", styleData);
         addCell(ttlCts, styleData);
         }
         if(!PRC.equals("")){
             String SUTVAL = util.nvl(req.getParameter("SUTVAL"));
             if(!SUTVAL.equals("")){
             addCell("Total Value", styleData);
             addCell(ttlVlu, styleData); 
             }
         }
     }
    public ArrayList getVwList(String mdl){
        ArrayList vwPrpLst = new ArrayList();
        String vwList = "select * from rep_prp where mdl=? and flg='Y' order by rnk ";
        ArrayList ary = new ArrayList();
        ary.add(mdl);
        try {
            ResultSet rs = db.execSql("lbXl", vwList, ary);
            while (rs.next()) {
                vwPrpLst.add(rs.getString("prp"));
            }
            rs.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return vwPrpLst;
    }
    
    public HSSFRow newRow() {
        return newRow(++rowCt);        
    }
    
    public HSSFRow newRow(int rownum) {
        HSSFRow lrow = sheet.createRow(rownum);
        cellCt = -1 ;
        return lrow ;
    }
   

    public void addCell(String val, HSSFCellStyle styl) {
        addCell(++cellCt, val, styl);    
    }
    
    public void addCell(int cellNum, String val, HSSFCellStyle styl, String typ ) {
        HSSFCell cell = row.createCell(cellNum);
        val = util.nvl(val);
        cell.setCellStyle(styl);
        if(val.equals("")){
            cell.setCellValue(val);
        }else{
        cell.setCellValue(Double.parseDouble(val));
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        }
        colNum = cell.getColumnIndex();
        autoCols.add(Integer.toString(colNum));
    }
    public void addCell(int cellNum, String val, HSSFCellStyle styl) {
        val = util.nvl(val);
        HSSFCell cell = row.createCell(cellNum);
        cell.setCellValue(getStr(val));      
        cell.setCellStyle(styl);
        colNum = cell.getColumnIndex();
        autoCols.add(Integer.toString(colNum));
    }
    
    public HSSFSheet addSheet() {
        HSSFSheet lsheet = hwb.createSheet("Sheet"+ ++sheetCtr);
        lsheet.autoSizeColumn((short)0);
        rowCt = 0;
        cellCt = -1;
       return lsheet ;
      
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
