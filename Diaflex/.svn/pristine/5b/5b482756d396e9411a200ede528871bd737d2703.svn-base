package ft.com;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;

import com.itextpdf.text.Section;

import com.itextpdf.text.pdf.PdfPCell;

import com.itextpdf.text.pdf.PdfPTable;

import java.awt.Color;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PdfforReport {
  DBMgr db = null;
  DBUtil util = null;
  InfoMgr info = null;
  LogMgr log = null;
  HttpSession session;

    public PdfforReport() {
        super();
    }
  public void init(HttpServletRequest req) {
      session = req.getSession(false);
      info = (InfoMgr)session.getAttribute("info");
      util = new DBUtil();
      db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
  }
  public void getDataInSinglePDF(int loopno,HttpServletRequest req,String FILE) {
    
    try
    {
       Font catFont = new Font(Font.FontFamily.HELVETICA, 18,
                        Font.BOLD);
         Font redFont = new Font(Font.FontFamily.HELVETICA, 12,
                        Font.NORMAL, BaseColor.RED);
        Font subFont = new Font(Font.FontFamily.HELVETICA, 16,
                        Font.BOLD);
       Font smallBold = new Font(Font.FontFamily.HELVETICA, 12,
                        Font.BOLD);
  
        com.itextpdf.text.Document document = new com.itextpdf.text.Document(com.itextpdf.text.PageSize.
        A2.rotate(), 5, 5, 20, 20); 
       
        com.itextpdf.text.pdf.PdfWriter.getInstance(document, new FileOutputStream(FILE));
        document.open();
        //addContent(document);
        //createTableSingleGrid(document,loopno,req);Plz Don't delete this Function in future it may required...Mayur
        document.close();
     }catch(Exception e) {
        e.printStackTrace();
    }
  }
  
    public void getDataInMultiplePDF(ArrayList statusLst,String qty,String cts,String avg,String rap,String age,String hit,int spanno,int loopno,HttpServletRequest req,String FILE) {
      
      try
      {
         Font catFont = new Font(Font.FontFamily.HELVETICA, 18,
                          Font.BOLD);
           Font redFont = new Font(Font.FontFamily.HELVETICA, 12,
                          Font.NORMAL, BaseColor.RED);
          Font subFont = new Font(Font.FontFamily.HELVETICA, 16,
                          Font.BOLD);
         Font smallBold = new Font(Font.FontFamily.HELVETICA, 12,
                          Font.BOLD);
    
          com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A2.rotate()
          , 1, 1, 1, 1); 
         document.setMargins(1, 1, 1, 1);
          com.itextpdf.text.pdf.PdfWriter.getInstance(document, new FileOutputStream(FILE));
          document.open();
//           document.setPageSize(PageSize.A4.rotate());
          //addContent(document);
          //createTableMultipleGrid(document,loopno,req);
          //createTableSingleGrid(document,loopno,req);Plz Don't delete this Function in future it may required...Mayur
          //document.close();
           for(int i=0;i<statusLst.size();i++){
                     String Status = (String)statusLst.get(i);
                     createTableMultipleGrid(document,qty,cts,avg,rap,age,hit,spanno,loopno,Status,req);
                   document.newPage();
          }
                 document.close();
       }catch(Exception e) {
          e.printStackTrace();
      }
    }








    private void createTableMultipleGrid(com.itextpdf.text.Document document,String qty,String cts,String avg,String rap,String age,String hit,int spanno,int loopno,String status,HttpServletRequest req)
                             throws com.itextpdf.text.BadElementException{
               init(req);
               try{
               Date date = Calendar.getInstance().getTime();
               DateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy, hh:mm:ss.SSS a");
               String today = formatter.format(date); 
               com.itextpdf.text.pdf.PdfPTable forDate = new com.itextpdf.text.pdf.PdfPTable(1);
               com.itextpdf.text.pdf.PdfPCell celltdate=new com.itextpdf.text.pdf.PdfPCell(new Phrase(today,new Font(Font.FontFamily.HELVETICA, 25,
                       Font.BOLD,BaseColor.LIGHT_GRAY)));
               forDate.setSpacingBefore(20f);
               forDate.setSpacingAfter(20f);
               celltdate.setBorder(0);
               celltdate.setHorizontalAlignment(Element.ALIGN_LEFT);
               celltdate.setVerticalAlignment(Element.ALIGN_MIDDLE);
               celltdate.setFixedHeight(40f);
               forDate.addCell(celltdate);
               document.add(forDate);
               }catch(Exception e) {
                       e.printStackTrace();
                   }
               try{
                   ArrayList shSzList = (ArrayList)session.getAttribute("shSzList");
                   HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
                     ArrayList rowListGt = (ArrayList)session.getAttribute("rowList");
                     ArrayList colListGt  = (ArrayList)session.getAttribute("colList");
                     HashMap getGrandTotalList = (HashMap)session.getAttribute("getGrandTotalList");
                     ArrayList commkeyList = (ArrayList)session.getAttribute("commkeyList");
                     ArrayList defaultstatusLst= ((ArrayList)session.getAttribute("defaultstatusLst") == null)?new ArrayList():(ArrayList)session.getAttribute("defaultstatusLst");
                     HashMap dscttlLst= ((HashMap)session.getAttribute("dscttlLst") == null)?new HashMap():(HashMap)session.getAttribute("dscttlLst");
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
                     HashMap mprp = info.getSrchMprp();
                     HashMap prp = info.getSrchPrp();
                     if(prp==null){
//                       util.initPrpSrch();
                     prp = info.getSrchPrp();
                     }
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
                   int size=0;
                   //int sttLstSize=statusLst.size();
                   for(int z=0;z<colList.size();z++){
                     String colV = (String)colList.get(z);
                     boolean isDis = true;
                     if(colV.indexOf("+")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                      isDis = false;
                      if(colV.indexOf("-")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                      isDis = false;
                     if(isDis){
                         if(colListGt.contains(colV)){
                         size++;
                         }
                     }
                   }
                   size=(size*spanno)+(spanno+1);
                   com.itextpdf.text.pdf.PdfPTable newtable = new com.itextpdf.text.pdf.PdfPTable(size);
                   for(int i=loopno ;i<=loopno;i++ ){
                           String key = (String)commkeyList.get(i);
                           String keyLable = key;
                           keyLable = (String)mprp.get(gridcommLst.get(0))+" : "+keyLable;
                           
                           for(int g=1 ;g <gridcommLst.size();g++ ){
                           keyLable=keyLable.replaceFirst("@", " "+(String)mprp.get(gridcommLst.get(g))+"   :");
                           }
                         com.itextpdf.text.pdf.PdfPCell cellt=new com.itextpdf.text.pdf.PdfPCell(new Phrase("Group :"+util.nvl((String)dscttlLst.get(status),status)+" "+keyLable,new Font(Font.FontFamily.HELVETICA, 20,
                                 Font.BOLD)));
                         newtable.setSpacingBefore(5f);
                         newtable.setSpacingAfter(5f);
                         newtable.setWidthPercentage(95.00f);
                         cellt.setColspan(size);
                         cellt.setBorder(0);
                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                         cellt.setFixedHeight(40f);
                         newtable.addCell(cellt);
                         cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                         cellt.setFixedHeight(40f);
                       
                         newtable.addCell(cellt);
                   
                   for(int j=0;j< colList.size();j++){
                       String colV = (String)colList.get(j);
                       boolean isDis = true;
                       if(colV.indexOf("+")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                        isDis = false;
                        if(colV.indexOf("-")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                        isDis = false;
                       if(isDis){
                       if(colListGt.contains(colV)){
                       cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(colV,new Font(Font.FontFamily.HELVETICA, 15,
                                 Font.BOLD)));
                     cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                     cellt.setColspan(spanno);
                     cellt.setFixedHeight(40f);
                       newtable.addCell(cellt);
                       }}}
                         cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Total",new Font(Font.FontFamily.HELVETICA, 15,
                                   Font.BOLD)));
                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                         cellt.setFixedHeight(40f);
                         cellt.setColspan(spanno);
                         newtable.addCell(cellt);
                         
    //                         cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
    //                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
    //                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
    //                         cellt.setFixedHeight(40f);
    //                         newtable.addCell(cellt);
    //
    //                           for(int j=0;j< colList.size();j++){
    //                               String colV = (String)colList.get(j);
    //                               boolean isDis = true;
    //                               if(colV.indexOf("+")!=-1)
    //                                isDis = false;
    //                                if(colV.indexOf("-")!=-1)
    //                                isDis = false;
    //                               if(isDis){
    //                               for(int st=0;st<statusLst.size();st++){
    //                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase((String)statusLst.get(st),new Font(Font.FontFamily.TIMES_ROMAN, 16,
    //                                         Font.BOLD)));
    //                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
    //                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
    //                             cellt.setFixedHeight(40f);
    //                               cellt.setColspan(3);
    //                             cellt.setNoWrap(true);
    //                               newtable.addCell(cellt);
    //                           }}}
    //
    //                               for(int st=0;st<statusLst.size();st++){
    //                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase((String)statusLst.get(st),new Font(Font.FontFamily.TIMES_ROMAN, 16,
    //                                         Font.BOLD)));
    //                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
    //                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
    //                             cellt.setFixedHeight(40f);
    //                             cellt.setColspan(3);
    //                             cellt.setNoWrap(true);
    //                               newtable.addCell(cellt);
    //                           }
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           newtable.addCell(cellt); 
                       
                   for(int k=0;k< colList.size();k++){
                        String colV = (String)colList.get(k);
                      boolean isDis = true;
                       if(colV.indexOf("+")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                        isDis = false;
                        if(colV.indexOf("-")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                        isDis = false;
                        if(isDis){
                        if(colListGt.contains(colV)){
                       if(qty.equals("Y")){
                       cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("QTY"));
                       cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                       cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                       cellt.setFixedHeight(40f);
                       cellt.setPadding(2f);
                       cellt.setNoWrap(true); 
                       newtable.addCell(cellt);
                       }
                       if(cts.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("CTS"));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                           cellt.setPadding(2f);
                             cellt.setNoWrap(true); 
                             newtable.addCell(cellt);
                       }
                         if(avg.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("AVG"));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true);
                             cellt.setPadding(2f);
                             newtable.addCell(cellt);
                         }
                            if(rap.equals("Y")){
                                cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("RAP"));
                                cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cellt.setFixedHeight(40f);
                                cellt.setNoWrap(true);
                                cellt.setPadding(2f);
                                newtable.addCell(cellt);
                            }
                            if(age.equals("Y")){
                                cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("AGE"));
                                cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cellt.setFixedHeight(40f);
                                cellt.setNoWrap(true);
                                cellt.setPadding(2f);
                                newtable.addCell(cellt);
                            }
                            if(hit.equals("Y")){
                                cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("HIT"));
                                cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cellt.setFixedHeight(40f);
                                cellt.setNoWrap(true);
                                cellt.setPadding(2f);
                                newtable.addCell(cellt);
                            }
                        }}} 
                           if(qty.equals("Y")){
                         cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("QTY"));
                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                         cellt.setFixedHeight(40f);
                         cellt.setNoWrap(true); 
                           cellt.setPadding(2f);
                         newtable.addCell(cellt);
                           }
                         if(cts.equals("Y")){
                          cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("CTS"));
                          cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                          cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true); 
                             cellt.setPadding(2f);
                          newtable.addCell(cellt);
                         }
                           if(avg.equals("Y")){
                          cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("AVG"));
                          cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                          cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                          newtable.addCell(cellt);
                           }
                           if(rap.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("RAP"));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                           if(age.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("AGE"));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                           if(hit.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("HIT"));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                   for(int m=0;m< rowList.size();m++){
                       String rowV = (String)rowList.get(m);
                          boolean isDis = true;
                         int totalQtyCol=0;
                         int sumtotalQtyCol=0;
                       if(rowV.indexOf("+")!=-1 && !rowlprpTyp.equals("T") && !rowlprpTyp.equals("N"))   
                        isDis = false;
                        if(rowV.indexOf("-")!=-1 && !rowlprpTyp.equals("T") && !rowlprpTyp.equals("N"))   
                        isDis = false;
                       if(isDis){
                       if(rowListGt.contains(rowV)){
                         cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(rowV,new Font(Font.FontFamily.HELVETICA, 15,
                                 Font.BOLD)));
                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                         cellt.setExtraParagraphSpace(2f);
                         cellt.setMinimumHeight(60f);
                         cellt.setMinimumHeight(40f);
                         cellt.setPadding(3f);
                         
    //                         cellt.setNoWrap(true);
                         newtable.addCell(cellt);
                       
                     
                     for(int n=0;n< colList.size();n++){
                       String colV = (String)colList.get(n);
                        boolean isDis1 = true;
                       if(colV.indexOf("+")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                        isDis1 = false;
                        if(colV.indexOf("-")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                        isDis1 = false;
                       if(isDis1){
                       if(colListGt.contains(colV)){    
                       String keyQtySold = key+"@"+rowV+"@"+colV+"@QTY@SOLD";
                       String keyQty = key+"@"+rowV+"@"+colV+"@"+status+"@QTY";
                       String keyCts = key+"@"+rowV+"@"+colV+"@"+status+"@CTS";
                       String keyAvg = key+"@"+rowV+"@"+colV+"@"+status+"@AVG";
                       String keyRAP = key+"@"+rowV+"@"+colV+"@"+status+"@RAP";
                       String keyAGE = key+"@"+rowV+"@"+colV+"@"+status+"@AGE";
                       String keyHit = key+"@"+rowV+"@"+colV+"@"+status+"@HIT";
                       String diffavgkey=key+"@"+rowV+"@"+colV;
                       String diffqtykey=key+"@"+rowV+"@"+colV;
                       String valQty = util.nvl((String)dataDtl.get(keyQty));
                       String valCts = util.nvl((String)dataDtl.get(keyCts));
                       String valAvg = util.nvl((String)dataDtl.get(keyAvg));
                       String valRap = util.nvl((String)dataDtl.get(keyRAP));
                       String valAge = util.nvl((String)dataDtl.get(keyAGE));
                       String valHit = util.nvl((String)dataDtl.get(keyHit));
                         if(!valQty.equals("")){
                              totalQtyCol=Integer.parseInt(valQty);
                              sumtotalQtyCol+=totalQtyCol;
                           }
                           boolean displayper=false;
                           if(defaultstatusLstsz>1){
                           String firststt=util.nvl((String)defaultstatusLst.get(0));
                           perdfvalAvg=0;perdfvalQty=0;
                           if(status.indexOf(firststt)<0){
                           for(int df=1;df<defaultstatusLst.size();df++){
                           String dfstt=util.nvl((String)defaultstatusLst.get(df));
                              if(status.indexOf(dfstt)>=0){
                                  diffavgkey=diffavgkey+"@"+status.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG";
                                  diffqtykey=diffqtykey+"@"+status.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY";
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
                           valQty+="\n"+perdfvalQty;
                           }
                               if(!perQTY.equals("") && displayper){
                               valQty+="\n"+perdfvalQty;
                               if(perdfvalQty>0)
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valQty,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.NORMAL,BaseColor.GREEN)));
                               else
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valQty,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.NORMAL,BaseColor.RED)));
                               }else{
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valQty,new Font(Font.FontFamily.HELVETICA, 15)));
                               }
                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                         cellt.setFixedHeight(40f);
                         cellt.setNoWrap(false); 
                         cellt.setPadding(2f);
                         newtable.addCell(cellt);
                           }
                               if(cts.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valCts,new Font(Font.FontFamily.HELVETICA, 15)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                             newtable.addCell(cellt);
                           }
                             if(avg.equals("Y")){
                              if(!perAVG.equals("") && displayper){
                               valAvg+="\n"+perdfvalAvg;
                                  if(perdfvalAvg>0)
                                  cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valAvg,new Font(Font.FontFamily.HELVETICA, 15,
                                  Font.NORMAL,BaseColor.GREEN)));
                                  else
                                  cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valAvg,new Font(Font.FontFamily.HELVETICA, 15,
                                  Font.NORMAL,BaseColor.RED)));
                                  }else{
                                  cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valAvg,new Font(Font.FontFamily.HELVETICA, 15)));
                                  }
//                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valAvg,new Font(Font.FontFamily.HELVETICA, 15)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                               cellt.setPadding(2f);
                             newtable.addCell(cellt);
                           }
                           if(rap.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valRap,new Font(Font.FontFamily.HELVETICA, 15)));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true); 
                             cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                           if(age.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valAge,new Font(Font.FontFamily.HELVETICA, 15)));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true); 
                             cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                           if(hit.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valHit,new Font(Font.FontFamily.HELVETICA, 15)));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true); 
                             cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                       } 
                       }
                       }       
                             String getcolttlqty=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+status+"@QTY"));
                             String getcolttlcts=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+status+"@CTS"));
                             String getcolttlavg=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+status+"@AVG"));   
                           String getcolttlrap=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL_"+status+"@RAP"));   
                           String getcolttlage=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+status+"@AGE"));   
                           String getcolttlhit=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+status+"@HIT"));   
                           if(!getcolttlqty.equals("")){
                             boolean displayper=false;
                                  String styleqty = "color: red";
                                  String styleavg = "color: red";
                                  if(defaultstatusLstsz>1){
                                  String firststt=util.nvl((String)defaultstatusLst.get(0));
                                  perdfvalAvg=0;perdfvalQty=0;
                                 if(status.indexOf(firststt)<0){
                                 for(int df=1;df<defaultstatusLst.size();df++){
                                 String dfstt=util.nvl((String)defaultstatusLst.get(df));
                                     if(status.indexOf(dfstt)>=0){
                                         String dfgetcolttlqty=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+status.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY"));
                                         String dfgetcolttlavg=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+status.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG"));
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
                                 getcolttlqty+="\n"+perdfvalQty;
                                 if(perdfvalQty>0)
                                 cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlqty,new Font(Font.FontFamily.HELVETICA, 15,
                                 Font.BOLD,BaseColor.GREEN)));
                                 else
                                 cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlqty,new Font(Font.FontFamily.HELVETICA, 15,
                                 Font.BOLD,BaseColor.RED)));
                                 }else{
                                 cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlqty,new Font(Font.FontFamily.HELVETICA, 15,Font.BOLD)));
                                 }
//                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlqty,new Font(Font.FontFamily.HELVETICA, 15,
//                                 Font.BOLD)));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                             cellt.setPadding(2f);
                           newtable.addCell(cellt);
                             }
                                 if(cts.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlcts,new Font(Font.FontFamily.HELVETICA, 15,
                                   Font.BOLD)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                                 cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                                 if(avg.equals("Y")){
                                 if(!perAVG.equals("") && displayper){
                                  getcolttlavg+="\n"+perdfvalAvg;
                                     if(perdfvalAvg>0)
                                     cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlavg,new Font(Font.FontFamily.HELVETICA, 15,
                                     Font.BOLD,BaseColor.GREEN)));
                                     else
                                     cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlavg,new Font(Font.FontFamily.HELVETICA, 15,
                                     Font.BOLD,BaseColor.RED)));
                                     }else{
                                     cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlavg,new Font(Font.FontFamily.HELVETICA, 15,Font.BOLD)));
                                     }
//                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlavg,new Font(Font.FontFamily.HELVETICA, 15,
//                                   Font.BOLD)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                                 cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                             if(rap.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlrap,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.BOLD)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true);
                             cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                             if(age.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlage,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.BOLD)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true);
                             cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                             if(hit.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlhit,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.BOLD)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true);
                             cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                         }else {
                             if(qty.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true);
                             cellt.setPadding(2f);
                           newtable.addCell(cellt);
                             }
                             if(cts.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                                 cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                             if(avg.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                                 cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                             if(rap.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                                 cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                             if(age.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                                 cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                             if(hit.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                                 cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                         }
                       }}
                         }
                         cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Total",new Font(Font.FontFamily.HELVETICA, 15,
                                   Font.BOLD)));
                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                         cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true); 
                           cellt.setPadding(2f);
                         newtable.addCell(cellt);
                         int grandtotal=0;
                         for(int j=0;j< colList.size();j++){
                             String colV = (String)colList.get(j);
                             boolean isDis = true;
                             if(colV.indexOf("+")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                              isDis = false;
                              if(colV.indexOf("-")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                              isDis = false;
                             if(isDis){
                                 if(colListGt.contains(colV)){    
                               String getclrttlqty=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+status+"@QTY"));
                               String getclrttlcts=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+status+"@CTS"));
                               String getclrttlavg=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+status+"@AVG"));
                                 String getclrttlrap=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+status+"@RAP"));
                                 String getclrttlage=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+status+"@AGE"));
                                 String getclrttlhit=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+status+"@HIT"));
                           
                             if(!getclrttlqty.equals("")){
                               boolean displayper=false;
                                    String styleqty = "color: red";
                                    String styleavg = "color: red";
                                    if(defaultstatusLstsz>1){
                                    String firststt=util.nvl((String)defaultstatusLst.get(0));
                                    perdfvalAvg=0;perdfvalQty=0;
                                   if(status.indexOf(firststt)<0){
                                   for(int df=1;df<defaultstatusLst.size();df++){
                                   String dfstt=util.nvl((String)defaultstatusLst.get(df));
                                       if(status.indexOf(dfstt)>=0){
                                           String dfgetclrttlqty=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+status.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY"));
                                           String dfgetclrttlqtyavg=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+status.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG"));
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
                               if(qty.equals("Y")){
                                   if(!perQTY.equals("") && displayper){
                                   getclrttlqty+="\n"+perdfvalQty;
                                   if(perdfvalQty>0)
                                   cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlqty,new Font(Font.FontFamily.HELVETICA, 15,
                                   Font.BOLD,BaseColor.GREEN)));
                                   else
                                   cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlqty,new Font(Font.FontFamily.HELVETICA, 15,
                                   Font.BOLD,BaseColor.RED)));
                                   }else{
                                   cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlqty,new Font(Font.FontFamily.HELVETICA, 15,Font.BOLD)));
                                   }
//                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlqty,new Font(Font.FontFamily.HELVETICA, 15,
//                                 Font.BOLD)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true); 
                               cellt.setPadding(2f);
                             newtable.addCell(cellt);
                               }
                               if(cts.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlcts,new Font(Font.FontFamily.HELVETICA, 15,
                                   Font.BOLD)));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true); 
                                   cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                                   if(avg.equals("Y")){
                                   if(!perAVG.equals("") && displayper){
                                    getclrttlavg+="\n"+perdfvalAvg;
                                       if(perdfvalAvg>0)
                                       cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlavg,new Font(Font.FontFamily.HELVETICA, 15,
                                       Font.BOLD,BaseColor.GREEN)));
                                       else
                                       cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlavg,new Font(Font.FontFamily.HELVETICA, 15,
                                       Font.BOLD,BaseColor.RED)));
                                       }else{
                                       cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlavg,new Font(Font.FontFamily.HELVETICA, 15,Font.BOLD)));
                                       }
//                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlavg,new Font(Font.FontFamily.HELVETICA, 15,
//                                   Font.BOLD)));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true); 
                                   cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                               if(rap.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlrap,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.BOLD)));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                               if(age.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlage,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.BOLD)));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                               if(hit.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlhit,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.BOLD)));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                           }else{
                               if(qty.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                             newtable.addCell(cellt); 
                               }
                                   if(cts.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true); 
                                   cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                                   if(avg.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true);
                                   cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                               if(rap.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                               if(age.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                               if(hit.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                           }
                             }}}
                                String getttlqty=util.nvl((String)getGrandTotalList.get(key+"@"+status+"@QTY"));
                                 String getttlcts=util.nvl((String)getGrandTotalList.get(key+"@"+status+"@CTS"));
                                 String getttlavg=util.nvl((String)getGrandTotalList.get(key+"@"+status+"@AVG"));
                           String getttlrap=util.nvl((String)getGrandTotalList.get(key+"@"+status+"@RAP"));
                           String getttlage=util.nvl((String)getGrandTotalList.get(key+"@"+status+"@AGE"));
                           String getttlhit=util.nvl((String)getGrandTotalList.get(key+"@"+status+"@HIT"));
                           boolean displayper=false;
                                String styleqty = "color: red";
                                String styleavg = "color: red";
                                if(defaultstatusLstsz>1){
                                String firststt=util.nvl((String)defaultstatusLst.get(0));
                                perdfvalAvg=0;perdfvalQty=0;
                               if(status.indexOf(firststt)<0){
                               for(int df=1;df<defaultstatusLst.size();df++){
                               String dfstt=util.nvl((String)defaultstatusLst.get(df));
                                   if(status.indexOf(dfstt)>=0){
                                       String dfgetttlqty=util.nvl((String)getGrandTotalList.get(key+"@"+status.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY"));
                                       String dfgetttlavg=util.nvl((String)getGrandTotalList.get(key+"@"+status.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG"));
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

                           if(qty.equals("Y")){
                               if(!perQTY.equals("") && displayper){
                               getttlqty+="\n"+perdfvalQty;
                               if(perdfvalQty>0)
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlqty,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.BOLD,BaseColor.GREEN)));
                               else
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlqty,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.BOLD,BaseColor.RED)));
                               }else{
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlqty,new Font(Font.FontFamily.HELVETICA, 15,Font.BOLD)));
                               }
//                         cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlqty,new Font(Font.FontFamily.HELVETICA, 15,
//                                 Font.BOLD)));
                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                         cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true); 
                           cellt.setPadding(2f);
                         newtable.addCell(cellt);
                           }
                               if(cts.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlcts,new Font(Font.FontFamily.HELVETICA, 15,
                                     Font.BOLD)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                             newtable.addCell(cellt);
                           }
                               if(avg.equals("Y")){
                               if(!perAVG.equals("") && displayper){
                                getttlavg+="\n"+perdfvalAvg;
                                   if(perdfvalAvg>0)
                                   cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlavg,new Font(Font.FontFamily.HELVETICA, 15,
                                   Font.BOLD,BaseColor.GREEN)));
                                   else
                                   cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlavg,new Font(Font.FontFamily.HELVETICA, 15,
                                   Font.BOLD,BaseColor.RED)));
                                   }else{
                                   cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlavg,new Font(Font.FontFamily.HELVETICA, 15)));
                                   }
//                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlavg,new Font(Font.FontFamily.HELVETICA, 15,
//                                     Font.BOLD)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true); 
                               cellt.setPadding(2f);
                             newtable.addCell(cellt);
                           }
                           if(rap.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlrap,new Font(Font.FontFamily.HELVETICA, 15,
                                 Font.BOLD)));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true);
                           cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                           if(age.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlage,new Font(Font.FontFamily.HELVETICA, 15,
                                 Font.BOLD)));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true);
                           cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                           if(hit.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlhit,new Font(Font.FontFamily.HELVETICA, 15,
                                 Font.BOLD)));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true);
                           cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                       }
            document.add(newtable);
                     
                 }catch(Exception e) {
                         e.printStackTrace();
                     }
                   

             }

    public void getDataInMultiplePDFQuick(ArrayList statusLst,String qty,String cts,String avg,String rap,String age,String hit,int spanno,int loopno,HttpServletRequest req,String FILE) {
      
      try
      {
         Font catFont = new Font(Font.FontFamily.HELVETICA, 18,
                          Font.BOLD);
           Font redFont = new Font(Font.FontFamily.HELVETICA, 12,
                          Font.NORMAL, BaseColor.RED);
          Font subFont = new Font(Font.FontFamily.HELVETICA, 16,
                          Font.BOLD);
         Font smallBold = new Font(Font.FontFamily.HELVETICA, 12,
                          Font.BOLD);
    
          com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A2.rotate()
          , 1, 1, 1, 1); 
         document.setMargins(1, 1, 1, 1);
          com.itextpdf.text.pdf.PdfWriter.getInstance(document, new FileOutputStream(FILE));
          document.open();
    //           document.setPageSize(PageSize.A4.rotate());
          //addContent(document);
          //createTableMultipleGrid(document,loopno,req);
          //createTableSingleGrid(document,loopno,req);Plz Don't delete this Function in future it may required...Mayur
          //document.close();
           for(int i=0;i<statusLst.size();i++){
                     String Status = (String)statusLst.get(i);
                     createTableMultipleGridQuick(document,qty,cts,avg,rap,age,hit,spanno,loopno,Status,req);
                   document.newPage();
          }
                 document.close();
       }catch(Exception e) {
          e.printStackTrace();
      }
    }
        
            private void createTableMultipleGridQuick(com.itextpdf.text.Document document,String qty,String cts,String avg,String rap,String age,String hit,int spanno,int loopno,String status,HttpServletRequest req)
                             throws com.itextpdf.text.BadElementException{
               init(req);
               try{
               Date date = Calendar.getInstance().getTime();
               DateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy, hh:mm:ss.SSS a");
               String today = formatter.format(date); 
               com.itextpdf.text.pdf.PdfPTable forDate = new com.itextpdf.text.pdf.PdfPTable(1);
               com.itextpdf.text.pdf.PdfPCell celltdate=new com.itextpdf.text.pdf.PdfPCell(new Phrase(today,new Font(Font.FontFamily.HELVETICA, 25,
                       Font.BOLD,BaseColor.LIGHT_GRAY)));
               forDate.setSpacingBefore(20f);
               forDate.setSpacingAfter(20f);
               celltdate.setBorder(0);
               celltdate.setHorizontalAlignment(Element.ALIGN_LEFT);
               celltdate.setVerticalAlignment(Element.ALIGN_MIDDLE);
               celltdate.setFixedHeight(40f);
               forDate.addCell(celltdate);
               document.add(forDate);
               }catch(Exception e) {
                       e.printStackTrace();
                   }
               try{
                   ArrayList shSzList = (ArrayList)session.getAttribute("shSzList");
                   HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
                     ArrayList rowListGt = (ArrayList)session.getAttribute("rowList");
                     ArrayList colListGt  = (ArrayList)session.getAttribute("colList");
                     HashMap getGrandTotalList = (HashMap)session.getAttribute("getGrandTotalList");
                     ArrayList commkeyList = (ArrayList)session.getAttribute("commkeyList");
                     ArrayList defaultstatusLst= ((ArrayList)session.getAttribute("defaultstatusLst") == null)?new ArrayList():(ArrayList)session.getAttribute("defaultstatusLst");
                     HashMap dscttlLst= ((HashMap)session.getAttribute("dscttlLst") == null)?new HashMap():(HashMap)session.getAttribute("dscttlLst");
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
                     HashMap mprp = info.getSrchMprp();
                     HashMap prp = info.getSrchPrp();
                     if(prp==null){
    //                       util.initPrpSrch();
                     prp = info.getSrchPrp();
                     }
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
                   int size=0;
                   //int sttLstSize=statusLst.size();
                   for(int z=0;z<colList.size();z++){
                     String colV = (String)colList.get(z);
                     boolean isDis = true;
                     if(colV.indexOf("+")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                      isDis = false;
                      if(colV.indexOf("-")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                      isDis = false;
                     if(isDis){
                         if(colListGt.contains(colV)){
                         size++;
                         }
                     }
                   }
                   size=(size*spanno)+(spanno+1);
                   com.itextpdf.text.pdf.PdfPTable newtable = new com.itextpdf.text.pdf.PdfPTable(size);
                   for(int i=loopno ;i<=loopno;i++ ){
                           String key = (String)commkeyList.get(i);
                           String keyLable = key;
                           keyLable = (String)mprp.get(gridcommLst.get(0))+" : "+keyLable;
                           
                           for(int g=1 ;g <gridcommLst.size();g++ ){
                           keyLable=keyLable.replaceFirst("@", " "+(String)mprp.get(gridcommLst.get(g))+"   :");
                           }
                         com.itextpdf.text.pdf.PdfPCell cellt=new com.itextpdf.text.pdf.PdfPCell(new Phrase("Group :"+util.nvl((String)dscttlLst.get(status),status)+" "+keyLable,new Font(Font.FontFamily.HELVETICA, 20,
                                 Font.BOLD)));
                         newtable.setSpacingBefore(5f);
                         newtable.setSpacingAfter(5f);
                         newtable.setWidthPercentage(95.00f);
                         cellt.setColspan(size);
                         cellt.setBorder(0);
                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                         cellt.setFixedHeight(40f);
                         newtable.addCell(cellt);
                         cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                         cellt.setFixedHeight(40f);
                       
                         newtable.addCell(cellt);
                   
                   for(int j=0;j< colList.size();j++){
                       String colV = (String)colList.get(j);
                       boolean isDis = true;
                       if(colV.indexOf("+")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                        isDis = false;
                        if(colV.indexOf("-")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                        isDis = false;
                       if(isDis){
                       if(colListGt.contains(colV)){
                       cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(colV,new Font(Font.FontFamily.HELVETICA, 15,
                                 Font.BOLD)));
                     cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                     cellt.setColspan(spanno);
                     cellt.setFixedHeight(40f);
                       newtable.addCell(cellt);
                       }}}
                         cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Total",new Font(Font.FontFamily.HELVETICA, 15,
                                   Font.BOLD)));
                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                         cellt.setFixedHeight(40f);
                         cellt.setColspan(spanno);
                         newtable.addCell(cellt);
                         
    //                         cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
    //                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
    //                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
    //                         cellt.setFixedHeight(40f);
    //                         newtable.addCell(cellt);
    //
    //                           for(int j=0;j< colList.size();j++){
    //                               String colV = (String)colList.get(j);
    //                               boolean isDis = true;
    //                               if(colV.indexOf("+")!=-1)
    //                                isDis = false;
    //                                if(colV.indexOf("-")!=-1)
    //                                isDis = false;
    //                               if(isDis){
    //                               for(int st=0;st<statusLst.size();st++){
    //                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase((String)statusLst.get(st),new Font(Font.FontFamily.TIMES_ROMAN, 16,
    //                                         Font.BOLD)));
    //                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
    //                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
    //                             cellt.setFixedHeight(40f);
    //                               cellt.setColspan(3);
    //                             cellt.setNoWrap(true);
    //                               newtable.addCell(cellt);
    //                           }}}
    //
    //                               for(int st=0;st<statusLst.size();st++){
    //                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase((String)statusLst.get(st),new Font(Font.FontFamily.TIMES_ROMAN, 16,
    //                                         Font.BOLD)));
    //                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
    //                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
    //                             cellt.setFixedHeight(40f);
    //                             cellt.setColspan(3);
    //                             cellt.setNoWrap(true);
    //                               newtable.addCell(cellt);
    //                           }
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           newtable.addCell(cellt); 
                       
                   for(int k=0;k< colList.size();k++){
                        String colV = (String)colList.get(k);
                      boolean isDis = true;
                       if(colV.indexOf("+")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                        isDis = false;
                        if(colV.indexOf("-")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                        isDis = false;
                        if(isDis){
                        if(colListGt.contains(colV)){
                       if(qty.equals("Y")){
                       cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("QTY"));
                       cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                       cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                       cellt.setFixedHeight(40f);
                       cellt.setPadding(2f);
                       cellt.setNoWrap(true); 
                       newtable.addCell(cellt);
                       }
                       if(cts.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("CTS"));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                           cellt.setPadding(2f);
                             cellt.setNoWrap(true); 
                             newtable.addCell(cellt);
                       }
                         if(avg.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("AVG"));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true);
                             cellt.setPadding(2f);
                             newtable.addCell(cellt);
                         }
                            if(rap.equals("Y")){
                                cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("RAP"));
                                cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cellt.setFixedHeight(40f);
                                cellt.setNoWrap(true);
                                cellt.setPadding(2f);
                                newtable.addCell(cellt);
                            }
                            if(age.equals("Y")){
                                cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("AGE"));
                                cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cellt.setFixedHeight(40f);
                                cellt.setNoWrap(true);
                                cellt.setPadding(2f);
                                newtable.addCell(cellt);
                            }
                            if(hit.equals("Y")){
                                cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("HIT"));
                                cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cellt.setFixedHeight(40f);
                                cellt.setNoWrap(true);
                                cellt.setPadding(2f);
                                newtable.addCell(cellt);
                            }
                        }}} 
                           if(qty.equals("Y")){
                         cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("QTY"));
                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                         cellt.setFixedHeight(40f);
                         cellt.setNoWrap(true); 
                           cellt.setPadding(2f);
                         newtable.addCell(cellt);
                           }
                         if(cts.equals("Y")){
                          cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("CTS"));
                          cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                          cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true); 
                             cellt.setPadding(2f);
                          newtable.addCell(cellt);
                         }
                           if(avg.equals("Y")){
                          cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("AVG"));
                          cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                          cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                          newtable.addCell(cellt);
                           }
                           if(rap.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("RAP"));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                           if(age.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("AGE"));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                           if(hit.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("HIT"));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                   for(int m=0;m< rowList.size();m++){
                       String rowV = (String)rowList.get(m);
                          boolean isDis = true;
                         int totalQtyCol=0;
                         int sumtotalQtyCol=0;
                       if(rowV.indexOf("+")!=-1 && !rowlprpTyp.equals("T") && !rowlprpTyp.equals("N"))   
                        isDis = false;
                        if(rowV.indexOf("-")!=-1 && !rowlprpTyp.equals("T") && !rowlprpTyp.equals("N"))   
                        isDis = false;
                       if(isDis){
                       if(rowListGt.contains(rowV)){
                         cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(rowV,new Font(Font.FontFamily.HELVETICA, 15,
                                 Font.BOLD)));
                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                         cellt.setExtraParagraphSpace(2f);
                         cellt.setMinimumHeight(60f);
                         cellt.setMinimumHeight(40f);
                         cellt.setPadding(3f);
                         
    //                         cellt.setNoWrap(true);
                         newtable.addCell(cellt);
                       
                     
                     for(int n=0;n< colList.size();n++){
                       String colV = (String)colList.get(n);
                        boolean isDis1 = true;
                       if(colV.indexOf("+")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                        isDis1 = false;
                        if(colV.indexOf("-")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                        isDis1 = false;
                       if(isDis1){
                       if(colListGt.contains(colV)){    
                       String keyQtySold = key+"@"+rowV+"@"+colV+"@QTY@SOLD";
                       String keyQty = key+"@"+rowV+"@"+colV+"@"+status+"@QTY";
                       String keyCts = key+"@"+rowV+"@"+colV+"@"+status+"@CTS";
                       String keyAvg = key+"@"+rowV+"@"+colV+"@"+status+"@AVG";
                       String keyRAP = key+"@"+rowV+"@"+colV+"@"+status+"@RAP";
                       String keyAGE = key+"@"+rowV+"@"+colV+"@"+status+"@AGE";
                       String keyHit = key+"@"+rowV+"@"+colV+"@"+status+"@HIT";
                       String diffavgkey=key+"@"+rowV+"@"+colV;
                       String diffqtykey=key+"@"+rowV+"@"+colV;
                       String valQty = util.nvl((String)dataDtl.get(keyQty));
                       String valCts = util.nvl((String)dataDtl.get(keyCts));
                       String valAvg = util.nvl((String)dataDtl.get(keyAvg));
                       String valRap = util.nvl((String)dataDtl.get(keyRAP));
                       String valAge = util.nvl((String)dataDtl.get(keyAGE));
                       String valHit = util.nvl((String)dataDtl.get(keyHit));
                         if(!valQty.equals("")){
                              totalQtyCol=Integer.parseInt(valQty);
                              sumtotalQtyCol+=totalQtyCol;
                           }
                           boolean displayper=false;
                           if(defaultstatusLstsz>1){
                           String firststt=util.nvl((String)defaultstatusLst.get(0));
                           perdfvalAvg=0;perdfvalQty=0;
                           if(status.indexOf(firststt)<0){
                           for(int df=1;df<defaultstatusLst.size();df++){
                           String dfstt=util.nvl((String)defaultstatusLst.get(df));
                              if(status.indexOf(dfstt)>=0){
                                  diffavgkey=diffavgkey+"@"+status.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG";
                                  diffqtykey=diffqtykey+"@"+status.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY";
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
                           valQty+="\n"+perdfvalQty;
                           }
                               if(!perQTY.equals("") && displayper){
                               valQty+="\n"+perdfvalQty;
                               if(perdfvalQty>0)
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valQty,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.NORMAL,BaseColor.GREEN)));
                               else
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valQty,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.NORMAL,BaseColor.RED)));
                               }else{
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valQty,new Font(Font.FontFamily.HELVETICA, 15)));
                               }
                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                         cellt.setFixedHeight(40f);
                         cellt.setNoWrap(false); 
                         cellt.setPadding(2f);
                         newtable.addCell(cellt);
                           }
                               if(cts.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valCts,new Font(Font.FontFamily.HELVETICA, 15)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                             newtable.addCell(cellt);
                           }
                             if(avg.equals("Y")){
                              if(!perAVG.equals("") && displayper){
                               valAvg+="\n"+perdfvalAvg;
                                  if(perdfvalAvg>0)
                                  cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valAvg,new Font(Font.FontFamily.HELVETICA, 15,
                                  Font.NORMAL,BaseColor.GREEN)));
                                  else
                                  cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valAvg,new Font(Font.FontFamily.HELVETICA, 15,
                                  Font.NORMAL,BaseColor.RED)));
                                  }else{
                                  cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valAvg,new Font(Font.FontFamily.HELVETICA, 15)));
                                  }
    //                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valAvg,new Font(Font.FontFamily.HELVETICA, 15)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                               cellt.setPadding(2f);
                             newtable.addCell(cellt);
                           }
                           if(rap.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valRap,new Font(Font.FontFamily.HELVETICA, 15)));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true); 
                             cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                           if(age.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valAge,new Font(Font.FontFamily.HELVETICA, 15)));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true); 
                             cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                           if(hit.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valHit,new Font(Font.FontFamily.HELVETICA, 15)));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true); 
                             cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                       } 
                       }
                       }       
                             String getcolttlqty=util.nvl((String)dataDtl.get(key+"@"+rowV+"@"+status+"@QTY"));
                             String getcolttlcts=util.nvl((String)dataDtl.get(key+"@"+rowV+"@"+status+"@CTS"));
                             String getcolttlavg=util.nvl((String)dataDtl.get(key+"@"+rowV+"@"+status+"@AVG"));   
                           String getcolttlrap=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL_"+status+"@RAP"));   
                           String getcolttlage=util.nvl((String)dataDtl.get(key+"@"+rowV+"@"+status+"@AGE"));   
                           String getcolttlhit=util.nvl((String)dataDtl.get(key+"@"+rowV+"@"+status+"@HIT"));   
                           if(!getcolttlqty.equals("")){
                             boolean displayper=false;
                                  String styleqty = "color: red";
                                  String styleavg = "color: red";
                                  if(defaultstatusLstsz>1){
                                  String firststt=util.nvl((String)defaultstatusLst.get(0));
                                  perdfvalAvg=0;perdfvalQty=0;
                                 if(status.indexOf(firststt)<0){
                                 for(int df=1;df<defaultstatusLst.size();df++){
                                 String dfstt=util.nvl((String)defaultstatusLst.get(df));
                                     if(status.indexOf(dfstt)>=0){
                                         String dfgetcolttlqty=util.nvl((String)dataDtl.get(key+"@"+rowV+"@"+status.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY"));
                                         String dfgetcolttlavg=util.nvl((String)dataDtl.get(key+"@"+rowV+"@"+status.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG"));
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
                                 getcolttlqty+="\n"+perdfvalQty;
                                 if(perdfvalQty>0)
                                 cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlqty,new Font(Font.FontFamily.HELVETICA, 15,
                                 Font.BOLD,BaseColor.GREEN)));
                                 else
                                 cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlqty,new Font(Font.FontFamily.HELVETICA, 15,
                                 Font.BOLD,BaseColor.RED)));
                                 }else{
                                 cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlqty,new Font(Font.FontFamily.HELVETICA, 15,Font.BOLD)));
                                 }
    //                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlqty,new Font(Font.FontFamily.HELVETICA, 15,
    //                                 Font.BOLD)));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                             cellt.setPadding(2f);
                           newtable.addCell(cellt);
                             }
                                 if(cts.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlcts,new Font(Font.FontFamily.HELVETICA, 15,
                                   Font.BOLD)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                                 cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                                 if(avg.equals("Y")){
                                 if(!perAVG.equals("") && displayper){
                                  getcolttlavg+="\n"+perdfvalAvg;
                                     if(perdfvalAvg>0)
                                     cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlavg,new Font(Font.FontFamily.HELVETICA, 15,
                                     Font.BOLD,BaseColor.GREEN)));
                                     else
                                     cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlavg,new Font(Font.FontFamily.HELVETICA, 15,
                                     Font.BOLD,BaseColor.RED)));
                                     }else{
                                     cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlavg,new Font(Font.FontFamily.HELVETICA, 15,Font.BOLD)));
                                     }
    //                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlavg,new Font(Font.FontFamily.HELVETICA, 15,
    //                                   Font.BOLD)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                                 cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                             if(rap.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlrap,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.BOLD)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true);
                             cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                             if(age.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlage,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.BOLD)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true);
                             cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                             if(hit.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlhit,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.BOLD)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true);
                             cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                         }else {
                             if(qty.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true);
                             cellt.setPadding(2f);
                           newtable.addCell(cellt);
                             }
                             if(cts.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                                 cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                             if(avg.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                                 cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                             if(rap.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                                 cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                             if(age.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                                 cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                             if(hit.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                             cellt.setNoWrap(true); 
                                 cellt.setPadding(2f);
                             newtable.addCell(cellt);
                             }
                         }
                       }}
                         }
                         cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Total",new Font(Font.FontFamily.HELVETICA, 15,
                                   Font.BOLD)));
                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                         cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true); 
                           cellt.setPadding(2f);
                         newtable.addCell(cellt);
                         int grandtotal=0;
                         for(int j=0;j< colList.size();j++){
                             String colV = (String)colList.get(j);
                             boolean isDis = true;
                             if(colV.indexOf("+")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                              isDis = false;
                              if(colV.indexOf("-")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N"))   
                              isDis = false;
                             if(isDis){
                                 if(colListGt.contains(colV)){    
                               String getclrttlqty=util.nvl((String)dataDtl.get(key+"@"+colV+"@"+status+"@QTY"));
                               String getclrttlcts=util.nvl((String)dataDtl.get(key+"@"+colV+"@"+status+"@CTS"));
                               String getclrttlavg=util.nvl((String)dataDtl.get(key+"@"+colV+"@"+status+"@AVG"));
                                 String getclrttlrap=util.nvl((String)dataDtl.get(key+"@"+colV+"@"+status+"@RAP"));
                                 String getclrttlage=util.nvl((String)dataDtl.get(key+"@"+colV+"@"+status+"@AGE"));
                                 String getclrttlhit=util.nvl((String)dataDtl.get(key+"@"+colV+"@"+status+"@HIT"));
                           
                             if(!getclrttlqty.equals("")){
                               boolean displayper=false;
                                    String styleqty = "color: red";
                                    String styleavg = "color: red";
                                    if(defaultstatusLstsz>1){
                                    String firststt=util.nvl((String)defaultstatusLst.get(0));
                                    perdfvalAvg=0;perdfvalQty=0;
                                   if(status.indexOf(firststt)<0){
                                   for(int df=1;df<defaultstatusLst.size();df++){
                                   String dfstt=util.nvl((String)defaultstatusLst.get(df));
                                       if(status.indexOf(dfstt)>=0){
                                           String dfgetclrttlqty=util.nvl((String)dataDtl.get(key+"@"+colV+"@"+status.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY"));
                                           String dfgetclrttlqtyavg=util.nvl((String)dataDtl.get(key+"@"+colV+"@"+status.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG"));
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
                               if(qty.equals("Y")){
                                   if(!perQTY.equals("") && displayper){
                                   getclrttlqty+="\n"+perdfvalQty;
                                   if(perdfvalQty>0)
                                   cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlqty,new Font(Font.FontFamily.HELVETICA, 15,
                                   Font.BOLD,BaseColor.GREEN)));
                                   else
                                   cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlqty,new Font(Font.FontFamily.HELVETICA, 15,
                                   Font.BOLD,BaseColor.RED)));
                                   }else{
                                   cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlqty,new Font(Font.FontFamily.HELVETICA, 15,Font.BOLD)));
                                   }
    //                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlqty,new Font(Font.FontFamily.HELVETICA, 15,
    //                                 Font.BOLD)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true); 
                               cellt.setPadding(2f);
                             newtable.addCell(cellt);
                               }
                               if(cts.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlcts,new Font(Font.FontFamily.HELVETICA, 15,
                                   Font.BOLD)));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true); 
                                   cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                                   if(avg.equals("Y")){
                                   if(!perAVG.equals("") && displayper){
                                    getclrttlavg+="\n"+perdfvalAvg;
                                       if(perdfvalAvg>0)
                                       cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlavg,new Font(Font.FontFamily.HELVETICA, 15,
                                       Font.BOLD,BaseColor.GREEN)));
                                       else
                                       cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlavg,new Font(Font.FontFamily.HELVETICA, 15,
                                       Font.BOLD,BaseColor.RED)));
                                       }else{
                                       cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlavg,new Font(Font.FontFamily.HELVETICA, 15,Font.BOLD)));
                                       }
    //                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlavg,new Font(Font.FontFamily.HELVETICA, 15,
    //                                   Font.BOLD)));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true); 
                                   cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                               if(rap.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlrap,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.BOLD)));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                               if(age.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlage,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.BOLD)));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                               if(hit.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlhit,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.BOLD)));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                           }else{
                               if(qty.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                             newtable.addCell(cellt); 
                               }
                                   if(cts.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true); 
                                   cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                                   if(avg.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true);
                                   cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                               if(rap.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                               if(age.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                               if(hit.equals("Y")){
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                               cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                               cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                               cellt.setFixedHeight(40f);
                               cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                               newtable.addCell(cellt);
                               }
                           }
                             }}}
                                String getttlqty=util.nvl((String)getGrandTotalList.get(key+"@"+status+"@QTY"));
                                 String getttlcts=util.nvl((String)getGrandTotalList.get(key+"@"+status+"@CTS"));
                                 String getttlavg=util.nvl((String)getGrandTotalList.get(key+"@"+status+"@AVG"));
                           String getttlrap=util.nvl((String)getGrandTotalList.get(key+"@"+status+"@RAP"));
                           String getttlage=util.nvl((String)getGrandTotalList.get(key+"@"+status+"@AGE"));
                           String getttlhit=util.nvl((String)getGrandTotalList.get(key+"@"+status+"@HIT"));
                           boolean displayper=false;
                                String styleqty = "color: red";
                                String styleavg = "color: red";
                                if(defaultstatusLstsz>1){
                                String firststt=util.nvl((String)defaultstatusLst.get(0));
                                perdfvalAvg=0;perdfvalQty=0;
                               if(status.indexOf(firststt)<0){
                               for(int df=1;df<defaultstatusLst.size();df++){
                               String dfstt=util.nvl((String)defaultstatusLst.get(df));
                                   if(status.indexOf(dfstt)>=0){
                                       String dfgetttlqty=util.nvl((String)getGrandTotalList.get(key+"@"+status.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY"));
                                       String dfgetttlavg=util.nvl((String)getGrandTotalList.get(key+"@"+status.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG"));
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

                           if(qty.equals("Y")){
                               if(!perQTY.equals("") && displayper){
                               getttlqty+="\n"+perdfvalQty;
                               if(perdfvalQty>0)
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlqty,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.BOLD,BaseColor.GREEN)));
                               else
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlqty,new Font(Font.FontFamily.HELVETICA, 15,
                               Font.BOLD,BaseColor.RED)));
                               }else{
                               cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlqty,new Font(Font.FontFamily.HELVETICA, 15,Font.BOLD)));
                               }
    //                         cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlqty,new Font(Font.FontFamily.HELVETICA, 15,
    //                                 Font.BOLD)));
                         cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                         cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true); 
                           cellt.setPadding(2f);
                         newtable.addCell(cellt);
                           }
                               if(cts.equals("Y")){
                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlcts,new Font(Font.FontFamily.HELVETICA, 15,
                                     Font.BOLD)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true);
                               cellt.setPadding(2f);
                             newtable.addCell(cellt);
                           }
                               if(avg.equals("Y")){
                               if(!perAVG.equals("") && displayper){
                                getttlavg+="\n"+perdfvalAvg;
                                   if(perdfvalAvg>0)
                                   cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlavg,new Font(Font.FontFamily.HELVETICA, 15,
                                   Font.BOLD,BaseColor.GREEN)));
                                   else
                                   cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlavg,new Font(Font.FontFamily.HELVETICA, 15,
                                   Font.BOLD,BaseColor.RED)));
                                   }else{
                                   cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlavg,new Font(Font.FontFamily.HELVETICA, 15)));
                                   }
    //                             cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlavg,new Font(Font.FontFamily.HELVETICA, 15,
    //                                     Font.BOLD)));
                             cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                             cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true); 
                               cellt.setPadding(2f);
                             newtable.addCell(cellt);
                           }
                           if(rap.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlrap,new Font(Font.FontFamily.HELVETICA, 15,
                                 Font.BOLD)));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true);
                           cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                           if(age.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlage,new Font(Font.FontFamily.HELVETICA, 15,
                                 Font.BOLD)));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true);
                           cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                           if(hit.equals("Y")){
                           cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlhit,new Font(Font.FontFamily.HELVETICA, 15,
                                 Font.BOLD)));
                           cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cellt.setFixedHeight(40f);
                           cellt.setNoWrap(true);
                           cellt.setPadding(2f);
                           newtable.addCell(cellt);
                           }
                       }
            document.add(newtable);
                     
                 }catch(Exception e) {
                         e.printStackTrace();
                     }
                   

             }
    
 private void createTableSingleGrid(com.itextpdf.text.Document document,int loopno,HttpServletRequest req)
                          throws com.itextpdf.text.BadElementException{
            init(req);
            try{
            Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy, hh:mm:ss.SSS a");
            String today = formatter.format(date); 
            com.itextpdf.text.pdf.PdfPTable forDate = new com.itextpdf.text.pdf.PdfPTable(1);
            com.itextpdf.text.pdf.PdfPCell celltdate=new com.itextpdf.text.pdf.PdfPCell(new Phrase(today,new Font(Font.FontFamily.HELVETICA, 25,
                    Font.BOLD,BaseColor.LIGHT_GRAY)));
            forDate.setSpacingBefore(20f);
            forDate.setSpacingAfter(20f);;
            celltdate.setBorder(0);
            celltdate.setHorizontalAlignment(Element.ALIGN_LEFT);
            celltdate.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celltdate.setFixedHeight(40f);
            forDate.addCell(celltdate);
            document.add(forDate);
            }catch(Exception e) {
                    e.printStackTrace();
                }
            try{
                ArrayList shSzList = (ArrayList)session.getAttribute("shSzList");
                HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
                HashMap getClrTotalList = (HashMap)session.getAttribute("getClrTotalList");
                  HashMap getColTotalList = (HashMap)session.getAttribute("getColTotalList");
                  HashMap getGrandTotalList = (HashMap)session.getAttribute("getGrandTotalList");
                  ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
                  HashMap dbinfo = info.getDmbsInfoLst();
                  String sh = (String)dbinfo.get("SHAPE");
                  String szval = (String)dbinfo.get("SIZE");
                  String colval = (String)dbinfo.get("COL");
                  String clrval = (String)dbinfo.get("CLR");
                  HashMap prp = info.getSrchPrp();
                  if(prp==null){
//                    util.initPrpSrch();
                  prp = info.getSrchPrp();
                  }
                  ArrayList colList = (ArrayList)prp.get(colval+"V");
                  ArrayList clrList = (ArrayList)prp.get(clrval+"V");
                int size=0;
                int sttLstSize=statusLst.size();
                for(int z=0;z<clrList.size();z++){
                  String clrV = (String)clrList.get(z);
                  boolean isDis = true;
                  if(clrV.indexOf("+")!=-1)   
                   isDis = false;
                   if(clrV.indexOf("-")!=-1)   
                   isDis = false;
                  if(isDis){
                      size+=sttLstSize;
                  }
                }
                size=size+(sttLstSize+1);
                com.itextpdf.text.pdf.PdfPTable newtable = new com.itextpdf.text.pdf.PdfPTable(size);
                for(int i=loopno ;i<=loopno;i++ ){
                  String key = (String)shSzList.get(i);
                  String keyLable = key.replaceAll("_", "  SIZE :");
                  keyLable = "Shape : "+keyLable;
                      com.itextpdf.text.pdf.PdfPCell cellt=new com.itextpdf.text.pdf.PdfPCell(new Phrase(keyLable,new Font(Font.FontFamily.HELVETICA, 20,
                              Font.BOLD)));
                      newtable.setSpacingBefore(5f);
                      newtable.setSpacingAfter(5f);
                      cellt.setColspan(size);
                      cellt.setBorder(0);
                      cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cellt.setFixedHeight(40f);
                      newtable.addCell(cellt);
                      cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                      cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cellt.setFixedHeight(40f);
                    
                      newtable.addCell(cellt);
                
                for(int j=0;j< clrList.size();j++){
                    String clrV = (String)clrList.get(j);
                    boolean isDis = true;
                    if(clrV.indexOf("+")!=-1)   
                     isDis = false;
                     if(clrV.indexOf("-")!=-1)   
                     isDis = false;
                    if(isDis){
                    cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(clrV,new Font(Font.FontFamily.TIMES_ROMAN, 16,
                              Font.BOLD)));
                  cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                  cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                  cellt.setColspan(sttLstSize);
                  cellt.setFixedHeight(40f);
                    newtable.addCell(cellt);
                }}
                      cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Total",new Font(Font.FontFamily.TIMES_ROMAN, 16,
                                Font.BOLD)));
                      cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cellt.setFixedHeight(40f);
                      cellt.setColspan(sttLstSize);
                      newtable.addCell(cellt);
                      
                      cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                      cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cellt.setFixedHeight(40f);
                      newtable.addCell(cellt); 
                    
                        for(int j=0;j< clrList.size();j++){
                            String clrV = (String)clrList.get(j);
                            boolean isDis = true;
                            if(clrV.indexOf("+")!=-1)   
                             isDis = false;
                             if(clrV.indexOf("-")!=-1)   
                             isDis = false;
                            if(isDis){
                            for(int st=0;st<statusLst.size();st++){
                            cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase((String)statusLst.get(st),new Font(Font.FontFamily.TIMES_ROMAN, 16,
                                      Font.BOLD)));
                            cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                          cellt.setFixedHeight(40f);
                          cellt.setNoWrap(true);      
                            newtable.addCell(cellt);
                        }}}
                        
                            for(int st=0;st<statusLst.size();st++){
                            cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase((String)statusLst.get(st),new Font(Font.FontFamily.TIMES_ROMAN, 16,
                                      Font.BOLD)));
                            cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                          cellt.setFixedHeight(40f);
                          cellt.setNoWrap(true); 
                            newtable.addCell(cellt);
                        }
                        cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                        cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cellt.setFixedHeight(40f);
                        newtable.addCell(cellt); 
                    
                for(int k=0;k< clrList.size();k++){
                     String clrV = (String)clrList.get(k);
                   boolean isDis = true;
                    if(clrV.indexOf("+")!=-1)   
                     isDis = false;
                     if(clrV.indexOf("-")!=-1)   
                     isDis = false;
                     if(isDis){
                      for(int st=0;st<statusLst.size();st++){
                    cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("QTY"));
                    cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellt.setFixedHeight(40f);
                    cellt.setNoWrap(true); 
                    newtable.addCell(cellt);
                      }
                  }} 
                   for(int st=0;st<statusLst.size();st++){
                      cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("QTY"));
                      cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cellt.setFixedHeight(40f);
                      newtable.addCell(cellt);
                   }
                for(int m=0;m< colList.size();m++){
                    String colV = (String)colList.get(m);
                       boolean isDis = true;
                      int totalQtyCol=0;
                      int sumtotalQtyCol=0;
                    if(colV.indexOf("+")!=-1)   
                     isDis = false;
                     if(colV.indexOf("-")!=-1)   
                     isDis = false;
                    if(isDis){
                      cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(colV,new Font(Font.FontFamily.TIMES_ROMAN, 16,
                              Font.BOLD)));
                      cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cellt.setFixedHeight(40f);
                      newtable.addCell(cellt);
                    
                  
                  for(int n=0;n< clrList.size();n++){
                    String clrV = (String)clrList.get(n);
                     boolean isDis1 = true;
                    if(clrV.indexOf("+")!=-1)   
                     isDis1 = false;
                     if(clrV.indexOf("-")!=-1)   
                     isDis1 = false;
                    if(isDis1){
                    for(int st=0;st<statusLst.size();st++){
                    String stt=(String)statusLst.get(st);
                    String keyQtySold = key+"_"+colV+"_"+clrV+"_QTY_SOLD";
                    String keyQty = key+"_"+colV+"_"+clrV+"_"+stt+"_QTY";
                    String keyCts = key+"_"+colV+"_"+clrV+"_"+stt+"_CTS";
                    String keyAvg = key+"_"+colV+"_"+clrV+"_"+stt+"_AVG";
                    String valQty = util.nvl((String)dataDtl.get(keyQty));
                    String valCts = util.nvl((String)dataDtl.get(keyCts));
                    String valAvg = util.nvl((String)dataDtl.get(keyAvg));
                      if(!valQty.equals("")){
                           totalQtyCol=Integer.parseInt(valQty);
                           sumtotalQtyCol+=totalQtyCol;
                        }
                      cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(valQty));
                      cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cellt.setFixedHeight(40f);
                      newtable.addCell(cellt);
                      
                      }
                    }
                    }       
                    for(int sti=0;sti<statusLst.size();sti++){
                        String stt=(String)statusLst.get(sti);
                          String getcolttlqty=util.nvl((String)getClrTotalList.get(key+"_"+colV+"_"+stt+"_QTY"));
                          String getcolttlcts=util.nvl((String)getClrTotalList.get(key+"_"+colV+"_"+stt+"_CTS"));
                          String getcolttlavg=util.nvl((String)getClrTotalList.get(key+"_"+colV+"_"+stt+"_AVG"));   
                        if(!getcolttlqty.equals("")){
                        cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getcolttlqty,new Font(Font.FontFamily.TIMES_ROMAN, 16,
                              Font.BOLD)));
                        cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cellt.setFixedHeight(40f);
                        newtable.addCell(cellt);
                      }else {
                        cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                        cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cellt.setFixedHeight(40f);
                        newtable.addCell(cellt);
                      }
                    }
                  }
                      }
                      cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Total",new Font(Font.FontFamily.TIMES_ROMAN, 16,
                                Font.BOLD)));
                      cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cellt.setFixedHeight(40f);
                      newtable.addCell(cellt);
                      int grandtotal=0;
                      for(int j=0;j< clrList.size();j++){
                          String clrV = (String)clrList.get(j);
                          boolean isDis = true;
                          if(clrV.indexOf("+")!=-1)   
                           isDis = false;
                           if(clrV.indexOf("-")!=-1)   
                           isDis = false;
                          if(isDis){
                          for(int st=0;st<statusLst.size();st++){
                            String stt=(String)statusLst.get(st);
                            String getclrttlqty=util.nvl((String)getClrTotalList.get(key+"_"+clrV+"_"+stt+"_QTY"));
                            String getclrttlcts=util.nvl((String)getClrTotalList.get(key+"_"+clrV+"_"+stt+"_CTS"));
                            String getclrttlavg=util.nvl((String)getClrTotalList.get(key+"_"+clrV+"_"+stt+"_AVG"));
                        
                          if(!getclrttlqty.equals("")){
                          cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getclrttlqty,new Font(Font.FontFamily.TIMES_ROMAN, 16,
                              Font.BOLD)));
                          cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                          cellt.setFixedHeight(40f);
                          newtable.addCell(cellt);
                        }else{
                          cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                          cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                          cellt.setFixedHeight(40f);
                          newtable.addCell(cellt); 
                        }
                        }
                          }}
                      for(int st=0;st<statusLst.size();st++){
                          String stt=(String)statusLst.get(st);
                             String getttlqty=util.nvl((String)getGrandTotalList.get(key+"_"+stt+"_QTY"));
                              String getttlcts=util.nvl((String)getGrandTotalList.get(key+"_"+stt+"_CTS"));
                              String getttlavg=util.nvl((String)getGrandTotalList.get(key+"_"+stt+"_AVG"));
                      cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(getttlqty,new Font(Font.FontFamily.TIMES_ROMAN, 16,
                              Font.BOLD)));
                      cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cellt.setFixedHeight(40f);
                      newtable.addCell(cellt);
                      }
                    }
                  document.add(newtable);
                  
              }catch(Exception e) {
                      e.printStackTrace();
                  }
                
  }
 
    public void getProposDataInPDF(ArrayList proposIdnLst,ArrayList liveIdnLst,ArrayList nmeLst,ArrayList grpNmeLst,HttpServletRequest req,String FILE) {
      
      try
      {
         Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                          Font.BOLD);
           Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                          Font.NORMAL, BaseColor.RED);
          Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
                          Font.BOLD);
         Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                          Font.BOLD);
    
          com.itextpdf.text.Document document = new com.itextpdf.text.Document(com.itextpdf.text.PageSize.
          A2.rotate(), 5, 5, 20, 20); 
         
          com.itextpdf.text.pdf.PdfWriter.getInstance(document, new FileOutputStream(FILE));
          document.open();
          //addContent(document);
          for(int i=0;i<proposIdnLst.size();i++){
              String proposIdn = (String)proposIdnLst.get(i);
              String liveIdn =(String)liveIdnLst.get(i);
              String nme = (String)nmeLst.get(i);
              String grpNme = (String)grpNmeLst.get(i);
          createTablePropos(document,proposIdn,liveIdn,nme,grpNme,req);
            document.newPage();
          }
          document.close();
       }catch(Exception e) {
          e.printStackTrace();
      }
    }
    
    private void createTablePropos(com.itextpdf.text.Document document,String proposIdn,String liveIdn,String nme,String grpNme,HttpServletRequest req)
                            throws com.itextpdf.text.BadElementException{
              init(req);
              try{
                  com.itextpdf.text.pdf.PdfPTable headtable = new com.itextpdf.text.pdf.PdfPTable(5);
                  com.itextpdf.text.pdf.PdfPTable innerheadtable = new com.itextpdf.text.pdf.PdfPTable(2);
                  com.itextpdf.text.pdf.PdfPCell headcellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 16,
                            Font.BOLD)));
                  headcellt.setFixedHeight(5f);
                  headcellt.setBorder(PdfPCell.NO_BORDER);
                  headcellt.setBackgroundColor(BaseColor.BLUE);
                  innerheadtable.addCell(headcellt);
                  headcellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Proposed",new Font(Font.FontFamily.HELVETICA, 16,
                            Font.BOLD)));
                  headcellt.setBorder(PdfPCell.NO_BORDER);
                  headcellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                  headtable.setSpacingBefore(1f);
                  headtable.setSpacingAfter(1f);
                  innerheadtable.addCell(headcellt);
                  headtable.addCell(innerheadtable);
                  
                  innerheadtable = new com.itextpdf.text.pdf.PdfPTable(2);
                  headcellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 16,
                                              Font.BOLD)));
                  headcellt.setFixedHeight(5f);
                  headcellt.setBorder(PdfPCell.NO_BORDER);
                  headcellt.setBackgroundColor(BaseColor.RED);
                  innerheadtable.addCell(headcellt);
                  headcellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Live",new Font(Font.FontFamily.HELVETICA, 16,
                            Font.BOLD)));
                  headcellt.setBorder(PdfPCell.NO_BORDER);
                  headcellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                  headtable.setSpacingBefore(1f);
                  headtable.setSpacingAfter(1f);
                  innerheadtable.addCell(headcellt);
                  headtable.addCell(innerheadtable);
                  
                  innerheadtable = new com.itextpdf.text.pdf.PdfPTable(2);
                  headcellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 16,
                                              Font.BOLD)));
                  headcellt.setFixedHeight(5f);
                  headcellt.setBorder(PdfPCell.NO_BORDER);
                  headcellt.setBackgroundColor(BaseColor.GREEN);
                  innerheadtable.addCell(headcellt);
                  headcellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Same",new Font(Font.FontFamily.HELVETICA, 16,
                            Font.BOLD)));
                  headcellt.setBorder(PdfPCell.NO_BORDER);
                  headcellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                  headtable.setSpacingBefore(1f);
                  headtable.setSpacingAfter(1f);
                  innerheadtable.addCell(headcellt);
                  headtable.addCell(innerheadtable);
                  headcellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 16,
                            Font.BOLD)));
                  headcellt.setBorder(PdfPCell.NO_BORDER);
                  headtable.setSpacingBefore(2f);
                  headtable.setSpacingAfter(2f);
                  headtable.addCell(headcellt);
                  headcellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 16,
                            Font.BOLD)));
                  headcellt.setBorder(PdfPCell.NO_BORDER);
                  headtable.setSpacingBefore(2f);
                  headtable.setSpacingAfter(2f);
                  headtable.addCell(headcellt);
              document.add(headtable);
                  
              }catch(Exception e) {
                      e.printStackTrace();
                  }
              //head end
              //grpName
              try{
                    com.itextpdf.text.pdf.PdfPCell grpcellt=new com.itextpdf.text.pdf.PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 20,
                                Font.BOLD)));
                    com.itextpdf.text.pdf.PdfPTable grpName = new com.itextpdf.text.pdf.PdfPTable(1);
                    grpcellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Rem Name : "+grpNme,new Font(Font.FontFamily.HELVETICA, 16,Font.BOLD)));
                    grpName.setSpacingBefore(2f);
                    grpName.setSpacingAfter(2f);
                    grpcellt.setHorizontalAlignment(Element.ALIGN_LEFT);
                    grpcellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    grpcellt.setBorder(PdfPCell.NO_BORDER);
                    grpName.addCell(grpcellt);
                  
                    grpcellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Sheet Name : "+nme,new Font(Font.FontFamily.HELVETICA, 16,Font.BOLD)));
                    grpcellt.setBorder(PdfPCell.NO_BORDER);
                    grpName.setSpacingBefore(2f);
                    grpName.setSpacingAfter(2f);
                    grpName.addCell(grpcellt);
                    
                  
                    grpcellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Basic Propeties : ",new Font(Font.FontFamily.HELVETICA, 16,
                              Font.BOLD)));
                    grpName.setSpacingBefore(2f);
                    grpName.setSpacingAfter(2f);
                    grpcellt.setBorder(PdfPCell.NO_BORDER);
                    grpName.addCell(grpcellt);
                    
                    document.add(grpName);
                }catch(Exception e) {
                        e.printStackTrace();
                    }
              //grpname end
              //basic Properties Start
              HashMap matDtlList = (HashMap)session.getAttribute("matDtlMap");
              HashMap proMatDtl = (HashMap)matDtlList.get("PROP_"+proposIdn);
              HashMap liveMatDtl = (HashMap)matDtlList.get("LIVE_"+liveIdn);
              
              int size=0;
              
              
              
              String prpCO = "COL", prpPU = "CLR" ;
              HashMap mprp = info.getSrchMprp();
              if(mprp==null){
//               util.initPrpSrch();
                mprp = info.getSrchMprp();
               }
              HashMap prp = info.getSrchPrp();
              String dscCO = (String)mprp.get(prpCO+"D");
              String dscPU = (String)mprp.get(prpPU+"D");
              
              ArrayList valCO = (ArrayList)prp.get(prpCO+"P");
              ArrayList bseCO = (ArrayList)prp.get(prpCO+"B");
              ArrayList srtCO = (ArrayList)prp.get(prpCO+"V");
              
              ArrayList valPU = (ArrayList)prp.get(prpPU+"P");
              ArrayList bsePU = (ArrayList)prp.get(prpPU+"B");
              ArrayList srtPU = (ArrayList)prp.get(prpPU+"V");
              ArrayList refPrp = info.getPrcRefPrp();
              ArrayList grpPrp = util.getGrpPrp(grpNme);
              try{
                  com.itextpdf.text.pdf.PdfPTable basicprp = new com.itextpdf.text.pdf.PdfPTable(5);
                  com.itextpdf.text.pdf.PdfPCell basiccellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(" ",new Font(Font.FontFamily.HELVETICA, 16,
                            Font.BOLD)));
                  basiccellt.setHorizontalAlignment(Element.ALIGN_LEFT);
                  basiccellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                  basicprp.setSpacingBefore(2f);
                  basicprp.setSpacingAfter(2f);
                  basiccellt.setBorder(PdfPCell.NO_BORDER);
                  basicprp.addCell(basiccellt);
                  
                  
                  basiccellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Proposed ",new Font(Font.FontFamily.HELVETICA, 16,
                            Font.BOLD)));
                  basiccellt.setHorizontalAlignment(Element.ALIGN_LEFT);
                  basiccellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                  basicprp.setSpacingBefore(2f);
                  basicprp.setSpacingAfter(2f);
                  basiccellt.setBorder(PdfPCell.NO_BORDER);
                  basicprp.addCell(basiccellt);
                  
                  
                  basiccellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Live ",new Font(Font.FontFamily.HELVETICA, 16,
                            Font.BOLD)));
                  basiccellt.setHorizontalAlignment(Element.ALIGN_LEFT);
                  basiccellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                  basicprp.setSpacingBefore(2f);
                  basicprp.setSpacingAfter(2f);
                  basiccellt.setBorder(PdfPCell.NO_BORDER);
                  basicprp.addCell(basiccellt);
                  
                  basiccellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 16,
                            Font.BOLD)));
                  basiccellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                  basiccellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                  basicprp.setSpacingBefore(2f);
                  basicprp.setSpacingAfter(2f);
                  basiccellt.setBorder(PdfPCell.NO_BORDER);
                  basicprp.addCell(basiccellt);
                  
                  basiccellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 16,
                            Font.BOLD)));
                  basiccellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                  basiccellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                  basicprp.setSpacingBefore(2f);
                  basicprp.setSpacingAfter(2f);
                  basiccellt.setBorder(PdfPCell.NO_BORDER);
                  basicprp.addCell(basiccellt);
                  
                  
                  for(int j=0;j< grpPrp.size();j++){
                     String lprp = (String)grpPrp.get(j);
                      String prpDsc = (String)mprp.get(lprp+"D");
                     String valProFr = util.nvl((String)proMatDtl.get(lprp+ "_FR"));
                     String valProTo = util.nvl((String)proMatDtl.get(lprp+ "_TO"));
                     String valLiveFr = util.nvl((String)liveMatDtl.get(lprp+ "_FR"));
                     String valLiveTo = util.nvl((String)liveMatDtl.get(lprp+ "_TO"));
                      
                      basiccellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase((prpDsc),new Font(Font.FontFamily.HELVETICA, 16,
                                    Font.BOLD)));
                      basiccellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                      basiccellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      basiccellt.setBorder(PdfPCell.NO_BORDER);
                      basicprp.addCell(basiccellt);
                      
                      basiccellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(new Phrase((valProFr+" to "+valProTo),new Font(Font.FontFamily.HELVETICA, 20,
                    Font.BOLD,BaseColor.BLUE))));                     
                      basiccellt.setHorizontalAlignment(Element.ALIGN_LEFT);
                      basiccellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      basiccellt.setBorder(PdfPCell.NO_BORDER);
                      basicprp.addCell(basiccellt);
                      
                      basiccellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(new Phrase((valLiveFr+" to "+valLiveTo),new Font(Font.FontFamily.HELVETICA, 20,
                    Font.BOLD,BaseColor.RED))));
                      basiccellt.setHorizontalAlignment(Element.ALIGN_LEFT);
                      basiccellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      basiccellt.setBorder(PdfPCell.NO_BORDER);
                      basicprp.addCell(basiccellt);
                      
                      basiccellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                      basiccellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                      basiccellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      basiccellt.setBorder(PdfPCell.NO_BORDER);
                      basicprp.addCell(basiccellt);
                      basiccellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                      basiccellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                      basiccellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      basiccellt.setBorder(PdfPCell.NO_BORDER);
                      basicprp.addCell(basiccellt);
                      
                      
                  }
                  document.add(basicprp);
              }catch(Exception e) {
                      e.printStackTrace();
                  }
              //basic Properties end
              
              //main--start            
              try{
                 
                com.itextpdf.text.pdf.PdfPCell cellt=new com.itextpdf.text.pdf.PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 20,
                            Font.BOLD)));
                    for(int x=0 ; x < bsePU.size(); x++){ 
                        size++;
                    }
                    com.itextpdf.text.pdf.PdfPTable newtable = new com.itextpdf.text.pdf.PdfPTable(++size);
                    cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                    cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellt.setFixedHeight(30f);
                    newtable.addCell(cellt); 
                    for(int x=0 ; x < bsePU.size(); x++){ 
                        
                        cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase((String)bsePU.get(x),new Font(Font.FontFamily.HELVETICA, 16,
                                  Font.BOLD)));
                        cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cellt.setFixedHeight(30f);
                        newtable.addCell(cellt);
                    } 
                  
                for(int x=0 ; x < bseCO.size(); x++){
                String coVal = (String)bseCO.get(x);
                 
                    cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase((coVal),new Font(Font.FontFamily.HELVETICA, 16,
                                  Font.BOLD)));
                    cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellt.setFixedHeight(30f);
                    newtable.addCell(cellt);
            
                for(int y=0 ; y < bsePU.size() ; y++){
                String puVal = (String)bsePU.get(y);
                String proPosVal = util.nvl((String)proMatDtl.get(coVal+"_"+puVal));
                int proValInt = 0;
                if(!proPosVal.equals(""))
                proValInt = Integer.parseInt(proPosVal);
                
                String liveVal = util.nvl((String)liveMatDtl.get(coVal+"_"+puVal));
                 int liveValInt = 0;
                if(!liveVal.equals(""))
                liveValInt = Integer.parseInt(liveVal);
                
                 if(liveValInt==0 && proValInt==0){
                    cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(" "));
                    cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellt.setFixedHeight(30f);
                    newtable.addCell(cellt);
                }else if(liveValInt==proValInt){
                        cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(util.nvl((String)proMatDtl.get(coVal+"_"+puVal)),new Font(Font.FontFamily.HELVETICA, 15,
                    Font.BOLD,BaseColor.GREEN)));
                        cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cellt.setFixedHeight(30f);
                        newtable.addCell(cellt);
                 }else{
                        String diff="";
                        if(liveValInt!=0 && grpNme.equals("JB BASE PRICE")){
                        float diffResult=(((float)proValInt-(float)liveValInt)/(float)liveValInt)*100;
                        diff=String.valueOf(Math.round(diffResult*100.0)/100.0) ;   
                        }
                     com.itextpdf.text.pdf.PdfPTable innernewtable = new com.itextpdf.text.pdf.PdfPTable(1);
                     
                     com.itextpdf.text.pdf.PdfPCell innerheadbluecellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(util.nvl((String)proMatDtl.get(coVal+"_"+puVal)),new Font(Font.FontFamily.HELVETICA, 15,
                    Font.BOLD,BaseColor.BLUE)));
                     innerheadbluecellt.setBorder(PdfPCell.NO_BORDER);
                     innerheadbluecellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                     innerheadbluecellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                     
                     String liveval=util.nvl((String)liveMatDtl.get(coVal+"_"+puVal));
                     com.itextpdf.text.pdf.PdfPCell innerheadredcellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(liveval,new Font(Font.FontFamily.HELVETICA, 15,
                     Font.BOLD,BaseColor.RED)));
                     if(!liveval.equals("")){
                     innerheadredcellt.setBorder(PdfPCell.NO_BORDER);
                     innerheadredcellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                     innerheadredcellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                     }
                     
                     com.itextpdf.text.pdf.PdfPCell innerheadjbcellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(util.nvl(diff+" %"),new Font(Font.FontFamily.HELVETICA, 15,
                     Font.BOLD)));
                     if(liveValInt!=0 && grpNme.equals("JB BASE PRICE") && !diff.equals("")){
                     innerheadjbcellt.setBorder(PdfPCell.NO_BORDER);
                     innerheadjbcellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                     innerheadjbcellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                     }
                     innernewtable.addCell(innerheadbluecellt); 
                     if(!liveval.equals("")){
                     innernewtable.addCell(innerheadredcellt);
                     }
                     if(liveValInt!=0 && grpNme.equals("JB BASE PRICE") && !diff.equals("")){
                     innernewtable.addCell(innerheadjbcellt);
                     }
                     
                     newtable.addCell(innernewtable);              
                 }
                }
                }
                
                    document.add(newtable);  
                
                
                    
                }catch(Exception e) {
                        e.printStackTrace();
                    }
              //mainTable--end
              
              //Reference properties--start
              try{
                  
                  com.itextpdf.text.pdf.PdfPTable refprp = new com.itextpdf.text.pdf.PdfPTable(3);
                  com.itextpdf.text.pdf.PdfPCell refcellt= new com.itextpdf.text.pdf.PdfPCell(new Phrase("Reference Propeties :",new Font(Font.FontFamily.HELVETICA, 16,
                            Font.BOLD)));
                  refcellt.setColspan(3);
                  refcellt.setBorder(PdfPCell.NO_BORDER);
                  refprp.addCell(refcellt);
                  
                  for(int j=0;j< refPrp.size();j++){
                          String lprp = (String)refPrp.get(j);
                          String prpDsc = (String)mprp.get(lprp+"D");
                          String prpTyp = (String)mprp.get(lprp+"T");
                          ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
                          ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
                            String tblId = "ref_"+lprp ;
                  com.itextpdf.text.pdf.PdfPTable refprpDsc = new com.itextpdf.text.pdf.PdfPTable(2);    
                  com.itextpdf.text.pdf.PdfPCell lprprefcellt=new com.itextpdf.text.pdf.PdfPCell(new Phrase(prpDsc,new Font(Font.FontFamily.HELVETICA, 16,
                            Font.BOLD)));
                  lprprefcellt.setBorder(PdfPCell.NO_BORDER);
                  lprprefcellt.setFixedHeight(30f);
                  lprprefcellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                  lprprefcellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                  lprprefcellt.setColspan(2);
                  refprpDsc.addCell(lprprefcellt);
                  if(prpVal!=null && prpVal.size()>0){
                      
                  for(int k = 0 ; k < prpVal.size() ; k++){
                             String lVal = (String)prpPrt.get(k);
                             String lSrt = (String)prpVal.get(k);
                             String proPct = util.nvl((String)proMatDtl.get(tblId + "_" + lSrt));
                             String livePct = util.nvl((String)liveMatDtl.get(tblId + "_" + lSrt));
                             int proValInt = 0;
                             if(!proPct.equals(""))
                              proValInt = Integer.parseInt(proPct);
                             int liveValInt = 0;
                             if(!livePct.equals(""))
                             liveValInt = Integer.parseInt(livePct);
                      
                  com.itextpdf.text.pdf.PdfPCell lValrefcellt=new com.itextpdf.text.pdf.PdfPCell(new Phrase(lVal));
                  lValrefcellt.setFixedHeight(20f);
                  lValrefcellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                  lValrefcellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                  refprpDsc.addCell(lValrefcellt); 
                      
                  if(liveValInt==0 && proValInt==0){
                      lValrefcellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(""));
                      lValrefcellt.setFixedHeight(25f);
                      lValrefcellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                      lValrefcellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      refprpDsc.addCell(lValrefcellt); 
                  }else if(liveValInt==proValInt){
                      lValrefcellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(proPct));
                      lValrefcellt.setFixedHeight(25f);
                      lValrefcellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                      lValrefcellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      refprpDsc.addCell(lValrefcellt); 
                  }else{
                      lValrefcellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(" "+proPct+" / livePct"));
                      lValrefcellt.setFixedHeight(25f);
                      lValrefcellt.setHorizontalAlignment(Element.ALIGN_CENTER);
                      lValrefcellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      refprpDsc.addCell(lValrefcellt); 
                  }
                      
                  }}
                      refprp.addCell(refprpDsc); 
                  }
                  
                    document.add(refprp);
                }catch(Exception e) {
                      e.printStackTrace();
                  }
              //Reference properties--end
                  

            }
    
  private void addContent(Document document) throws DocumentException {
          Anchor anchor = new Anchor("First Chapter");
      anchor.setName("First Chapter");

      // Second parameter is the number of the chapter
      Chapter catPart = new Chapter(new Paragraph(anchor), 1);

      Paragraph subPara = new Paragraph("Subcategory 1");
      Section subCatPart = catPart.addSection(subPara);
      subCatPart.add(new Paragraph("Hello"));

      subPara = new Paragraph("Subcategory 2");
      subCatPart = catPart.addSection(subPara);
      subCatPart.add(new Paragraph("Paragraph 1"));
      subCatPart.add(new Paragraph("Paragraph 2"));
      subCatPart.add(new Paragraph("Paragraph 3"));

      // Add a  little list
      //createList(subCatPart);

      // Add a small table
      //createTable(subCatPart);
      // Now a small table



      // Now add all this to the document
      document.add(catPart);

      // Next section
      anchor = new Anchor("Second Chapter");
      anchor.setName("Second Chapter");

      // Second parameter is the number of the chapter
      catPart = new Chapter(new Paragraph(anchor), 1);

      subPara = new Paragraph("Subcategory");
      subCatPart = catPart.addSection(subPara);
      subCatPart.add(new Paragraph("This is a very important message"));

      // Now add all this to the document
      document.add(catPart);
      }
  
  private void createList(com.itextpdf.text.Section subCatPart) {
          com.itextpdf.text.List list = new com.itextpdf.text.List(true, false, 10);
          list.add(new com.itextpdf.text.ListItem("First point"));
          list.add(new com.itextpdf.text.ListItem("Second point"));
          list.add(new com.itextpdf.text.ListItem("Third point"));
          subCatPart.add(list);
  }
  private  void addEmptyLine(com.itextpdf.text.Paragraph paragraph, int number) {
          for (int i = 0; i < number; i++) {
                  paragraph.add(new com.itextpdf.text.Paragraph(" "));
          }
  }
  
    

   
    
    private void createTableReportGrid(com.itextpdf.text.Document document,HttpServletRequest req)
    throws com.itextpdf.text.BadElementException{
    init(req);
    try{
    Date date = Calendar.getInstance().getTime();
    DateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy, hh:mm:ss.SSS a");
    String today = formatter.format(date);
    com.itextpdf.text.pdf.PdfPTable forDate = new com.itextpdf.text.pdf.PdfPTable(1);
    com.itextpdf.text.pdf.PdfPCell celltdate=new com.itextpdf.text.pdf.PdfPCell(new Phrase(today,new Font(Font.FontFamily.HELVETICA, 25,
    Font.BOLD,BaseColor.LIGHT_GRAY)));
    forDate.setSpacingBefore(20f);
    forDate.setSpacingAfter(20f);
    celltdate.setBorder(0);
    celltdate.setHorizontalAlignment(Element.ALIGN_LEFT);
    celltdate.setVerticalAlignment(Element.ALIGN_MIDDLE);
    celltdate.setFixedHeight(40f);
    forDate.addCell(celltdate);
    document.add(forDate);
    }catch(Exception e) {
    e.printStackTrace();
    }
    try{
    String title="LAB LOCATION REPORT";
    ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
    ArrayList pktList = (ArrayList)session.getAttribute("pktList");

    com.itextpdf.text.pdf.PdfPTable newtable = new com.itextpdf.text.pdf.PdfPTable(itemHdr.size());
    if(itemHdr.contains("AVG")){
            title="LAB ISSUE/RETURN REPORT";
    }
    com.itextpdf.text.pdf.PdfPCell cellt=new com.itextpdf.text.pdf.PdfPCell
    (new Phrase(title,new Font(Font.FontFamily.HELVETICA, 20,Font.BOLD)));
    newtable.setSpacingBefore(5f);
    newtable.setSpacingAfter(5f);
    newtable.setWidthPercentage(95.00f);
    cellt.setColspan(itemHdr.size());
    cellt.setBorder(0);
    cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
    cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cellt.setFixedHeight(40f);
    newtable.addCell(cellt);

    for(int j=0;j< itemHdr.size();j++){
    String clrV = (String)itemHdr.get(j);
    boolean isDis = true;
    cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(clrV,new Font(Font.FontFamily.HELVETICA, 18,
    Font.BOLD)));
    cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
    cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cellt.setFixedHeight(40f);
    newtable.addCell(cellt);
    }
    HashMap pktPrpMap = new HashMap();


    String pstt = "";
    ArrayList checklist = new ArrayList();

    for(int j=0;j<pktList.size();j++) {
    pktPrpMap=(HashMap)pktList.get(j);

    String nstt = (String)pktPrpMap.get("STATUS");

    for(int i=0;i<itemHdr.size();i++) {

    String status = (String)itemHdr.get(i);
    String clrD = (String)pktPrpMap.get(itemHdr.get(i));

    if(!pstt.equals(clrD) && !checklist.contains(clrD) )
    {
    cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase(clrD,new Font(Font.FontFamily.HELVETICA, 18,Font.BOLD)));
    cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
    cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cellt.setFixedHeight(40f);
    newtable.addCell(cellt);

    } else {

    cellt = new com.itextpdf.text.pdf.PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 18,Font.BOLD)));
    cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
    cellt.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cellt.setFixedHeight(40f);
    newtable.addCell(cellt);
    }


    if(status.equals("STATUS") || status.equals("LAB")) {

    if(!checklist.contains(clrD)){
    checklist.add(clrD);
    }
    }

    if(pstt.equals(""))
    pstt = nstt;

    if(!pstt.equals(nstt))
    checklist = new ArrayList();
    pstt = nstt;

    }




    }

    document.add(newtable);

    }catch(Exception e) {
    e.printStackTrace();
    }


    }
    
    public void createPDFMEMOHK(HttpServletRequest req,String FILE) {
      init(req);
      try
      {
           HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
           HashMap datattl= (HashMap)session.getAttribute("datattl");
           ArrayList keytable= (ArrayList)session.getAttribute("keytable");
           ArrayList deptLst= (ArrayList)session.getAttribute("deptLst");
           HashMap data=new HashMap();
           HashMap datasum=new HashMap();
           com.itextpdf.text.Document document = new com.itextpdf.text.Document(com.itextpdf.text.PageSize.
           A2.rotate(), 5, 5, 15, 15);  
           PdfWriter wr = PdfWriter.getInstance(document, new FileOutputStream(FILE));     
           if(dataDtl!=null && dataDtl.size()>0){
           document.open();  
           ArrayList conatinsdata= new ArrayList();
           String shape="",sz="",qty="",cts="",faavg="",avg="",qtysum="",ctssum="",faavgsum="",avgsum="",grandcts="",compdte="",fac="",memo;
           HashMap prp = info.getPrp();
           ArrayList prpSrtDept = null;
           ArrayList prpValDept = null;
           ArrayList prpSrtShape = null;
           ArrayList prpValShape = null;
           prpSrtDept =new ArrayList();
           prpSrtDept = (ArrayList)prp.get("DEPT"+"S"); 
           prpValDept = (ArrayList)prp.get("DEPT"+"V");
           prpSrtShape =new ArrayList();
           prpSrtShape = (ArrayList)prp.get("SHAPE"+"S");
           prpValShape = (ArrayList)prp.get("SHAPE"+"V");
           String roundSrt=(String)prpSrtShape.get(prpValShape.indexOf("ROUND"));
           data=(HashMap)dataDtl.get("GRANDTTL");
           grandcts=util.nvl((String)data.get("CTS"));
           compdte=util.nvl((String)data.get("CMPDTE"));
           fac=util.nvl((String)data.get("FAC"));
           memo=util.nvl((String)data.get("MEMO"));
           avg=util.nvl((String)data.get("AVG"));  
           
           PdfPTable tableleft = new PdfPTable(7);
           tableleft.setTotalWidth(360f);
              
           PdfPCell cell = new PdfPCell(new Phrase("  Memo -"+memo+" Factory - "+fac+" Complete Date -"+compdte,new Font(Font.FontFamily.HELVETICA, 15)));
           cell.setColspan(7);
           cell.setHorizontalAlignment(Element.ALIGN_LEFT);
           cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           cell.setFixedHeight(36f);
           cell.setNoWrap(true);
           tableleft.addCell(cell);
           for(int l=0;l<deptLst.size();l++){
           String srtDept=(String)deptLst.get(l);
           String dept=(String)prpValDept.get(prpSrtDept.indexOf(srtDept));  
           cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase("  "+dept,new Font(Font.FontFamily.HELVETICA, 15)));
           cell.setColspan(7);
           cell.setHorizontalAlignment(Element.ALIGN_LEFT);
           cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           cell.setFixedHeight(36f);
           cell.setNoWrap(true);
           tableleft.addCell(cell);
           cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Size",new Font(Font.FontFamily.HELVETICA, 15)));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           cell.setFixedHeight(36f);
           cell.setNoWrap(true);
           tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Pcs",new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Cts",new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Avg",new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase("F.Avg",new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Crt%",new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Tot%",new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
           for(int i=0;i<prpSrtShape.size();i++){
                  String srtShape=(String)prpSrtShape.get(i);
                  String key=srtDept+"_"+srtShape;
                  conatinsdata= new ArrayList();
                  datasum=new HashMap();
                  conatinsdata=(ArrayList)dataDtl.get(key);
                  if(conatinsdata!=null && conatinsdata.size()>0){
                  datasum=(HashMap)datattl.get(key);
                  shape=util.nvl((String)datasum.get("SH"));
                  qtysum=util.nvl((String)datasum.get("QTY"));
                  ctssum=util.nvl((String)datasum.get("CTS"));
                  avgsum=util.nvl((String)datasum.get("AVG"));
                  faavgsum=util.nvl((String)datasum.get("FAAVG"));
                  String subkey=key.substring(key.indexOf("_")+1,key.length());
                  if(subkey.equals(roundSrt)){
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase("  Shape :"+shape,new Font(Font.FontFamily.HELVETICA, 15,
                                          Font.BOLD,BaseColor.BLACK)));
                      cell.setColspan(7);
                      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell);
                  for(int j=0;j<conatinsdata.size();j++){
                  data=new HashMap();
                  data=(HashMap)conatinsdata.get(j);
                  if(data!=null && data.size()>0){
                  shape=util.nvl((String)data.get("SH"));
                  sz=util.nvl((String)data.get("SZ"));
                  qty=util.nvl((String)data.get("QTY"));
                  cts=util.nvl((String)data.get("CTS"));
                  avg=util.nvl((String)data.get("AVG"));
                  faavg=util.nvl((String)data.get("FAAVG"));
                  Double crt=(Double.parseDouble(cts)/Double.parseDouble(ctssum))*100;
                  Double tot=(Double.parseDouble(cts)/Double.parseDouble(grandcts))*100;
                     cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(sz,new Font(Font.FontFamily.HELVETICA, 15)));
                     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                     cell.setFixedHeight(36f);
                     cell.setNoWrap(true);
                     tableleft.addCell(cell); 
                     cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(qty));
                     cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(sz,new Font(Font.FontFamily.HELVETICA, 15)));
                     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                     cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                     cell.setFixedHeight(36f);
                     cell.setPaddingRight(16);
                     cell.setNoWrap(true);
                     tableleft.addCell(cell); 
                     cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(cts,new Font(Font.FontFamily.HELVETICA, 15)));
                     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                     cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                     cell.setFixedHeight(36f);
                     cell.setPaddingRight(16);
                     cell.setNoWrap(true);
                     tableleft.addCell(cell); 
                     cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(avg,new Font(Font.FontFamily.HELVETICA, 15)));
                     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                     cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                     cell.setFixedHeight(36f);
                     cell.setPaddingRight(16);
                     cell.setNoWrap(true);
                     tableleft.addCell(cell); 
                     cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(faavg,new Font(Font.FontFamily.HELVETICA, 15)));
                     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                     cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                     cell.setFixedHeight(36f);
                     cell.setPaddingRight(16);
                     cell.setNoWrap(true);
                     tableleft.addCell(cell); 
                     cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(String.valueOf(util.Round(crt, 2))+"%",new Font(Font.FontFamily.HELVETICA, 15)));
                     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                     cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                     cell.setFixedHeight(36f);
                     cell.setPaddingRight(16);
                     cell.setNoWrap(true);
                     tableleft.addCell(cell); 
                     cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(String.valueOf(util.Round(tot, 2))+"%",new Font(Font.FontFamily.HELVETICA, 15)));
                     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                     cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                     cell.setFixedHeight(36f);
                     cell.setPaddingRight(16);
                     cell.setNoWrap(true);
                     tableleft.addCell(cell); 
                 }
                 }
                  Double crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
                  Double tot=(Double.parseDouble(ctssum)/Double.parseDouble(grandcts))*100;
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Total",new Font(Font.FontFamily.HELVETICA, 15,
                                          Font.BOLD,BaseColor.BLACK)));
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(qtysum,new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(ctssum,new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(avgsum,new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                                          cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(faavgsum,new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                                          cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(String.valueOf(util.Round(crt, 2))+"%",new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(String.valueOf(util.Round(tot, 2))+"%",new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                  }else{
                  Double crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
                  Double tot=(Double.parseDouble(ctssum)/Double.parseDouble(grandcts))*100;
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(shape));
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(qtysum,new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(ctssum,new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(avgsum,new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(faavgsum,new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(String.valueOf(util.Round(crt, 2))+"%",new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(String.valueOf(util.Round(tot, 2))+"%",new Font(Font.FontFamily.HELVETICA, 15)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                  }}}
                  data=new HashMap();
                  data=(HashMap)dataDtl.get(srtDept);
                  qty=util.nvl((String)data.get("QTY"));
                  cts=util.nvl((String)data.get("CTS"));
                  avg=util.nvl((String)data.get("AVG"));
                  faavg=util.nvl((String)data.get("FAAVG"));
                  Double tot=(Double.parseDouble(cts)/Double.parseDouble(grandcts))*100;
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Dept Total",new Font(Font.FontFamily.HELVETICA, 15,
                                          Font.BOLD,BaseColor.BLACK)));
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell);  
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(qty,new Font(Font.FontFamily.HELVETICA, 15,
                                          Font.BOLD,BaseColor.BLUE)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(cts,new Font(Font.FontFamily.HELVETICA, 15,
                                          Font.BOLD,BaseColor.BLUE)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell);  
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(avg,new Font(Font.FontFamily.HELVETICA, 15,
                                          Font.BOLD,BaseColor.BLUE)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(faavg,new Font(Font.FontFamily.HELVETICA, 15,
                                          Font.BOLD,BaseColor.BLUE)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 15,
                                          Font.BOLD,BaseColor.BLUE)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                      cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(String.valueOf(util.Round(tot, 2))+"%",new Font(Font.FontFamily.HELVETICA, 15,
                                          Font.BOLD,BaseColor.BLUE)));
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                      cell.setFixedHeight(36f);
                      cell.setPaddingRight(16);
                      cell.setNoWrap(true);
                      tableleft.addCell(cell); 
                  }
                  data=new HashMap();
                  data=(HashMap)dataDtl.get("GRANDTTL");
                  qty=util.nvl((String)data.get("QTY"));
                  cts=util.nvl((String)data.get("CTS"));
                  avg=util.nvl((String)data.get("AVG"));
                  faavg=util.nvl((String)data.get("FAAVG"));
              cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase("Grand Total",new Font(Font.FontFamily.HELVETICA, 15,
                                  Font.BOLD,BaseColor.BLACK)));
              cell.setHorizontalAlignment(Element.ALIGN_CENTER);
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              cell.setFixedHeight(36f);
              cell.setNoWrap(true);
              tableleft.addCell(cell); 
              cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(qty,new Font(Font.FontFamily.HELVETICA, 15,
                                  Font.BOLD,BaseColor.RED)));
              cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              cell.setFixedHeight(36f);
              cell.setNoWrap(true);
              cell.setPaddingRight(16);
              tableleft.addCell(cell); 
              cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(cts,new Font(Font.FontFamily.HELVETICA, 15,
                                  Font.BOLD,BaseColor.RED)));
              cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              cell.setFixedHeight(36f);
              cell.setNoWrap(true);
              tableleft.addCell(cell); 
              cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(avg,new Font(Font.FontFamily.HELVETICA, 15,
                                  Font.BOLD,BaseColor.RED)));
              cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              cell.setFixedHeight(36f);
              cell.setPaddingRight(16);
              cell.setNoWrap(true);
              tableleft.addCell(cell); 
              cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(faavg,new Font(Font.FontFamily.HELVETICA, 15,
                                  Font.BOLD,BaseColor.RED)));
              cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              cell.setFixedHeight(36f);
              cell.setPaddingRight(16);
              cell.setNoWrap(true);
              tableleft.addCell(cell); 
              cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 15,
                                  Font.BOLD,BaseColor.RED)));
              cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              cell.setFixedHeight(36f);
              cell.setPaddingRight(16);
              cell.setNoWrap(true);
              tableleft.addCell(cell); 
              cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase("100.0%",new Font(Font.FontFamily.HELVETICA, 15,
                                  Font.BOLD,BaseColor.RED)));
              cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              cell.setFixedHeight(36f);
              cell.setPaddingRight(16);
              cell.setNoWrap(true);
              tableleft.addCell(cell); 
              
              PdfPTable tableright = new PdfPTable(5);
              tableright.setTotalWidth(300f);
                  for(int l=0;l<deptLst.size();l++){
                                   String srtDept=(String)deptLst.get(l);
                                   String dept=(String)prpValDept.get(prpSrtDept.indexOf(srtDept));  
                                  for(int i=0;i<prpSrtShape.size();i++){
                                   String srtShape=(String)prpSrtShape.get(i);
                                   String key=srtDept+"_"+srtShape;
                                   conatinsdata= new ArrayList();
                                   datasum=new HashMap();
                                   conatinsdata=(ArrayList)dataDtl.get(key);
                                   if(conatinsdata!=null && conatinsdata.size()>0){
                                   datasum=(HashMap)datattl.get(key);
                                   shape=util.nvl((String)datasum.get("SH"));
                                   qtysum=util.nvl((String)datasum.get("QTY"));
                                   ctssum=util.nvl((String)datasum.get("CTS"));
                                   faavgsum=util.nvl((String)datasum.get("FAAVG"));
                                   String subkey=key.substring(key.indexOf("_")+1,key.length());
                                   if(!subkey.equals(roundSrt)){   
                                   PdfPCell cell1 = new PdfPCell(new Phrase(shape,new Font(Font.FontFamily.HELVETICA, 15)));
                                   cell1.setColspan(3);
                                       cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setNoWrap(true);
                                       tableright.addCell(cell1); 
                                       cell1 = new PdfPCell(new Phrase(dept,new Font(Font.FontFamily.HELVETICA, 15)));
                                   cell1.setColspan(2);
                                       cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setNoWrap(true);
                                       tableright.addCell(cell1); 
                                       cell1 = new PdfPCell(new Phrase("Size",new Font(Font.FontFamily.HELVETICA, 15)));
                                       cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setNoWrap(true);
                                       tableright.addCell(cell1); 
                                       cell1 = new PdfPCell(new Phrase("Pcs",new Font(Font.FontFamily.HELVETICA, 15)));
                                       cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setNoWrap(true);
                                       tableright.addCell(cell1); 
                                       cell1 = new PdfPCell(new Phrase("Carat",new Font(Font.FontFamily.HELVETICA, 15)));
                                       cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setNoWrap(true);
                                       tableright.addCell(cell1); 
                                       cell1 = new PdfPCell(new Phrase("Rate",new Font(Font.FontFamily.HELVETICA, 15)));
                                       cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setNoWrap(true);
                                       tableright.addCell(cell1); 
                                       cell1 = new PdfPCell(new Phrase("Per%",new Font(Font.FontFamily.HELVETICA, 15)));
                                       cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setNoWrap(true);
                                       tableright.addCell(cell1); 
                                   
                                   for(int j=0;j<conatinsdata.size();j++){
                                   data=new HashMap();
                                   data=(HashMap)conatinsdata.get(j);
                                   if(data!=null && data.size()>0){
                                   shape=util.nvl((String)data.get("SH"));
                                   sz=util.nvl((String)data.get("SZ"));
                                   qty=util.nvl((String)data.get("QTY"));
                                   cts=util.nvl((String)data.get("CTS"));
                                   faavg=util.nvl((String)data.get("FAAVG"));
                                   Double crt=(Double.parseDouble(cts)/Double.parseDouble(ctssum))*100;
                                   Double tot=(Double.parseDouble(cts)/Double.parseDouble(grandcts))*100;
                                       cell1 = new PdfPCell(new Phrase(sz,new Font(Font.FontFamily.HELVETICA, 15)));
                                       cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setNoWrap(true);
                                       tableright.addCell(cell1); 
                                       cell1 = new PdfPCell(new Phrase(qty,new Font(Font.FontFamily.HELVETICA, 15)));
                                       cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setPaddingRight(20);
                                       cell1.setNoWrap(true);
                                       tableright.addCell(cell1); 
                                       cell1 = new PdfPCell(new Phrase(cts,new Font(Font.FontFamily.HELVETICA, 15)));
                                       cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setPaddingRight(20);
                                       cell1.setNoWrap(true);
                                       tableright.addCell(cell1); 
                                       cell1 = new PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 15)));
                                       cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setPaddingRight(20);
                                       cell1.setNoWrap(true);
                                       tableright.addCell(cell1); 
                                       cell1 = new PdfPCell(new Phrase(String.valueOf(util.Round(tot, 2))+"%",new Font(Font.FontFamily.HELVETICA, 15)));
                                       cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setPaddingRight(20);
                                       cell1.setNoWrap(true);
                                       tableright.addCell(cell1); 
                                   }
                                   }
                                   Double crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
                                   Double tot=(Double.parseDouble(ctssum)/Double.parseDouble(grandcts))*100;
                                       cell1 = new PdfPCell(new Phrase("Total",new Font(Font.FontFamily.HELVETICA, 15)));
                                       cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setNoWrap(true);
                                       cell1.setExtraParagraphSpace(0f);
                                       tableright.addCell(cell1); 
                                       cell1 = new PdfPCell(new Phrase(qtysum,new Font(Font.FontFamily.HELVETICA, 15)));
                                       cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setPaddingRight(20);
                                       cell1.setExtraParagraphSpace(0f);
                                       cell1.setNoWrap(true);
                                       tableright.addCell(cell1); 
                                       cell1 = new PdfPCell(new Phrase(ctssum,new Font(Font.FontFamily.HELVETICA, 15)));
                                       cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setPaddingRight(20);
                                       cell1.setNoWrap(true);
                                       tableright.addCell(cell1); 
                                       cell1 = new PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 15)));
                                       cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setPaddingRight(20);
                                       cell1.setExtraParagraphSpace(0f);
                                       cell1.setNoWrap(true);
                                       tableright.addCell(cell1); 
                                       cell1 = new PdfPCell(new Phrase(String.valueOf(util.Round(tot, 2))+"%",new Font(Font.FontFamily.HELVETICA, 15)));
                                       cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                       cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                       cell1.setFixedHeight(36f);
                                       cell1.setPaddingRight(20);
                                       cell1.setExtraParagraphSpace(0f);
                                       cell1.setNoWrap(true);
                                       tableright.addCell(cell1); 
                                   }
                                  }
                                  }
                                        
                              
                                        
                                    }

              PdfPTable spacenoborder = new PdfPTable(7);
              PdfPCell cellinternal = new PdfPCell(new Phrase("."));
              cellinternal.setBorder(PdfPCell.NO_BORDER);   
              tableleft.addCell(cellinternal);
              cellinternal = new PdfPCell(new Phrase("."));
                            cellinternal.setBorder(PdfPCell.NO_BORDER);   
              tableleft.addCell(cellinternal);
              cellinternal = new PdfPCell(new Phrase("."));
                            cellinternal.setBorder(PdfPCell.NO_BORDER);   
              tableleft.addCell(cellinternal);
              cellinternal = new PdfPCell(new Phrase("."));
                            cellinternal.setBorder(PdfPCell.NO_BORDER);   
              tableleft.addCell(cellinternal);
              cellinternal = new PdfPCell(new Phrase("."));
                            cellinternal.setBorder(PdfPCell.NO_BORDER);   
              tableleft.addCell(cellinternal);
              cellinternal = new PdfPCell(new Phrase("."));
                            cellinternal.setBorder(PdfPCell.NO_BORDER);   
              tableleft.addCell(cellinternal);
              cellinternal = new PdfPCell(new Phrase("."));
                            cellinternal.setBorder(PdfPCell.NO_BORDER);   
              tableleft.addCell(cellinternal);
              PdfPTable maintable = new PdfPTable(2);
              PdfPCell cell2 = new PdfPCell(tableleft);
              cell2.setPaddingRight(2);
              cell2.setBorder(PdfPCell.NO_BORDER);   
              maintable.addCell(cell2);
              
              cellinternal = new PdfPCell(new Phrase("."));
              cellinternal.setBorder(PdfPCell.NO_BORDER);   
              tableright.addCell(cellinternal);
              cellinternal = new PdfPCell(new Phrase("."));
                            cellinternal.setBorder(PdfPCell.NO_BORDER);   
              tableright.addCell(cellinternal);
              cellinternal = new PdfPCell(new Phrase("."));
                            cellinternal.setBorder(PdfPCell.NO_BORDER);   
              tableright.addCell(cellinternal);
              cellinternal = new PdfPCell(new Phrase("."));
                            cellinternal.setBorder(PdfPCell.NO_BORDER);   
              tableright.addCell(cellinternal);
              cellinternal = new PdfPCell(new Phrase("."));
                            cellinternal.setBorder(PdfPCell.NO_BORDER);   
              tableright.addCell(cellinternal);
              
              PdfPCell cell3 = new PdfPCell(tableright);
              cell3.setBorder(PdfPCell.NO_BORDER);
              cell3.setPaddingLeft(2);
              maintable.addCell(cell3);
              
              document.add(maintable);
           document.close();
          }

       }catch(Exception e) {
          e.printStackTrace();
      }
    }
    
    

    public void getReportPDF(HttpServletRequest req,String FILE) {

    try
    {
    Font catFont = new Font(Font.FontFamily.HELVETICA, 18,
    Font.BOLD);
    Font redFont = new Font(Font.FontFamily.HELVETICA, 12,
    Font.NORMAL, BaseColor.RED);
    Font subFont = new Font(Font.FontFamily.HELVETICA, 16,
    Font.BOLD);
    Font smallBold = new Font(Font.FontFamily.HELVETICA, 12,
    Font.BOLD);

    com.itextpdf.text.Document document = new com.itextpdf.text.Document(com.itextpdf.text.PageSize.
    A2.rotate(), 1, 1, 1, 1);
    document.setMargins(1, 1, 1, 1);
    com.itextpdf.text.pdf.PdfWriter.getInstance(document, new FileOutputStream(FILE));
    document.open();
    createTableReportGrid(document,req);
    document.close();
    }catch(Exception e) {
    e.printStackTrace();
    }
    }

}