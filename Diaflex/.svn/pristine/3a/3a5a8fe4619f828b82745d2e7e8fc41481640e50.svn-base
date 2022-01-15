package ft.com;

//~--- non-JDK imports --------------------------------------------------------


import ft.com.marketing.SearchQuery;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

//~--- JDK imports ------------------------------------------------------------

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.*;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

public class SaveToXL extends HttpServlet {
    private static final String CONTENT_TYPE = "getServletContext()/vnd-excel";
    DBMgr                       db           = null;
    InfoMgr                     info         = null;
    LogMgr                      log          = null;
    DBUtil                      util         = null;
   SearchQuery               qurey        = new SearchQuery();
    HttpSession                 session;
    ExcelUtil                   excelUtil = null;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db   = new DBMgr();
        util = new DBUtil();
        info = new InfoMgr();
        log  = new LogMgr();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session = request.getSession(false);

        if (session.getAttribute("db") == null) {
            db.setApplId(getServletContext().getInitParameter("ApplId"));
            db.setDbHost(getServletContext().getInitParameter("HostName"));
            db.setDbPort(getServletContext().getInitParameter("Port"));
            db.setDbSID(getServletContext().getInitParameter("SID"));
            db.setDbUsr(getServletContext().getInitParameter("UserName"));
            db.setDbPwd(getServletContext().getInitParameter("Password"));
            db.init();
            info.setId(session.getId());
            util.setInfo(info);
            util.setDb(db);
        } else {
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
        excelUtil = new ExcelUtil();
        excelUtil.init(db, util, info);
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        String  getMemoId = request.getParameter("myIdn");
        String  myKey     = util.nvl(request.getParameter("myKey"));
        Boolean excelDnl  = null,
                mailExcl  = null;

        excelDnl = Boolean.valueOf(false);
        mailExcl = Boolean.valueOf(false);
        if(request.getParameter("excel")!=null)
            excelDnl = Boolean.valueOf(true);
        String fileNm = "memoInExcel.xls";

        try {
            HSSFWorkbook hwb = new HSSFWorkbook();

           
              if (excelDnl) {
                      response.setContentType(CONTENT_TYPE);
                      response.setHeader("Content-Disposition", "inline;attachment;filename=" + fileNm);

                      OutputStream out = response.getOutputStream();
                      if(cnt.equals("kj")){
                          hwb = excelUtil.getDataKapuInXl("memo", request, "MEMO_VW");
                      }else if(cnt.equals("sd")){
                          hwb = excelUtil.getDataSHInXl("memo", request, "MEMO_VW");
                      }else{
                       hwb = excelUtil.getDataInXl("memo", request, "MEMO_VW");
                      }
                      hwb.write(out);
                      out.flush();
                      out.close();
                
              }

              if (mailExcl) {
              //    qurey.MailExcel(hwb, fileNm, request , info.getByrId());
                  response.setContentType(CONTENT_TYPE);

                  PrintWriter out = response.getWriter();

                  out.println("<html>");
                  out.println("<head><title>SaveToXL</title></head>");
                  out.println("<body>");
                  out.println("<p>Mail delivered successfully </p>");
                  out.println("</body></html>");
                  out.close();
              }

            
        } catch (Exception e) {
//            System.out.println(e);
        }
    }

    public CellRangeAddress merge(int fRow, int lRow, int fCol, int lCol) {
        CellRangeAddress cra = new CellRangeAddress(fRow, lRow, fCol, lCol);

        return cra;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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


//~ Formatted by Jindent --- http://www.jindent.com
