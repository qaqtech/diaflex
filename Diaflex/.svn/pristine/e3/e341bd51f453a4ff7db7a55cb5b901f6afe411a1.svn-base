package ft.com.pricing;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;

//~--- JDK imports ------------------------------------------------------------

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

public class LoadFile extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    DBMgr                       db           = null;
    HashMap                     fieldN       = null;
    InfoMgr                     info         = null;
    PrintWriter                 out          = null;
    HttpSession                 session      = null;
    DBUtil                      util         = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>LoadFile</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println("</body></html>");
        out.close();
    }
    public void init(HttpServletRequest req , HttpServletResponse res){
        session = req.getSession(false);
        info    = (InfoMgr) session.getAttribute("info");
        util = ((DBUtil)session.getAttribute("util") == null)?new DBUtil():(DBUtil)session.getAttribute("util");
        db      = (DBMgr) session.getAttribute("db");
        String connExists=util.nvl(util.getConnExists());   
        if(session.isNew() || db==null || connExists.equals("N")){
        try {
            res.sendRedirect("../error_page.jsp?connExists="+connExists);
            } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
            }
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        out     = response.getWriter();
        init(request, response);
        fieldN  = new HashMap();

        String         grpNme = "",
                       matIdn = "";
        DiskFileUpload upload = new DiskFileUpload();
        List           items;
        HashMap      matDtl = new HashMap();

        try {
            items = upload.parseRequest(request);

            Iterator iter = items.iterator();

            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (item.isFormField()) {
                    fieldN.put(item.getFieldName(), util.nvl(item.getString()));
                } else {
                    String fileN = util.nvl(item.getName());

                    if (!fileN.equals("")) {
                        boolean isFile = true;
                        String  path   = getServletContext().getRealPath("/") + item.getName();

                        // "D:/websites/faunatechnologies/www/LiveSupportDoc/" + item.getName();
                        File tosave = new File(path);

                        item.write(tosave);

                        File             readFile   = new File(path);
                        FileReader       fileReader = new FileReader(readFile);
                        LineNumberReader lnr        = new LineNumberReader(fileReader);
                        String           line       = "";

                        while ((line = lnr.readLine()) != null) {
                            int             ln     = lnr.getLineNumber();
                            StringTokenizer vals   = new StringTokenizer(line, ",");
                            ArrayList          aryLst = new ArrayList();

                            while (vals.hasMoreTokens()) {
                                aryLst.add(vals.nextToken());
                            }

                            for (int i = 0; i < aryLst.size(); i++) {
                                matDtl.put(ln - 1 + "_" + i, (String) aryLst.get(i));
                            }

                            // util.SOP(ln + " : "+ matDtl.toString());
                        }

                        fileReader.close();
                    }
                    session.setAttribute("FileName",fileN);
                }
            }
        } catch (Exception e) {
            util.SOP(" File Loading Error " + e);
        }

        util.SOP(fieldN.toString());
        grpNme = (String) fieldN.get("grpNme");
        matIdn = (String) fieldN.get("matIdn");

        String showAllCO = util.nvl((String) fieldN.get("allCO"));
        String showAllPU = util.nvl((String) fieldN.get("allPU"));

        session.setAttribute("MATDTL", matDtl);
        session.setAttribute("RNG",new ArrayList());
        // loadFile(request, response);
        String lGrpNme    = grpNme;
        String linkGrpNme = lGrpNme;

        if (lGrpNme.indexOf("&") > -1) {
            linkGrpNme = linkGrpNme.replaceFirst("&", "%26");
        }
        request.setAttribute("FILEUPLOAD", "Y");
        response.sendRedirect(info.getReqUrl() + "/pricing/genPriceGrid.jsp?load=Y&id=" + matIdn + "&grpNme="
                              + linkGrpNme + "&allCO=" + showAllCO + "&allPU=" + showAllPU);
    }

    public void loadFile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String    contentType = request.getContentType();
        HashMap matDtl      = new HashMap();

        if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
            DataInputStream inputStream = null;

            inputStream = new DataInputStream(request.getInputStream());

            int  formDataLength = request.getContentLength();
            byte dataBytes[]    = new byte[formDataLength];
            int  byteRead       = 0;
            int  totalBytesRead = 0;

            // this loop converting the uploaded file into byte code
            while (totalBytesRead < formDataLength) {
                byteRead       = inputStream.read(dataBytes, totalBytesRead, formDataLength);
                totalBytesRead += byteRead;
            }

            String file = new String(dataBytes);

            // for saving the file name
            String saveFile = file.substring(file.indexOf("filename=\"") + 10);

            saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
            saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1, saveFile.indexOf("\""));
            saveFile = "D:\\da_upload\\" + saveFile;

            int    lastIndex = contentType.lastIndexOf("=");
            String boundary  = contentType.substring(lastIndex + 1, contentType.length());

            // util.SOP(" Upload File : " + saveFile);
            int pos;

            // extracting the index of file
            pos = file.indexOf("filename=\"");
            pos = file.indexOf("\n", pos) + 1;
            pos = file.indexOf("\n", pos) + 1;
            pos = file.indexOf("\n", pos) + 1;

            int boundaryLocation = file.indexOf(boundary, pos) - 4;
            int startPos         = ((file.substring(0, pos)).getBytes()).length;
            int endPos           = ((file.substring(0, boundaryLocation)).getBytes()).length;

            // creating a new file with the same name and writing the content in new file
            FileOutputStream fileOut = new FileOutputStream(saveFile);

            fileOut.write(dataBytes, startPos, (endPos - startPos));
            fileOut.flush();
            fileOut.close();

            File             readFile   = new File(saveFile);
            FileReader       fileReader = new FileReader(readFile);
            LineNumberReader lnr        = new LineNumberReader(fileReader);
            String           line       = "";

            while ((line = lnr.readLine()) != null) {
                int             ln     = lnr.getLineNumber();
                StringTokenizer vals   = new StringTokenizer(line, ",");
                ArrayList          aryLst = new ArrayList();

                while (vals.hasMoreTokens()) {
                    aryLst.add(vals.nextToken());
                }

                for (int i = 0; i < aryLst.size(); i++) {
                    matDtl.put(ln - 1 + "_" + i, (String) aryLst.get(i));
                }

                util.SOP(ln + " : " + matDtl.toString());
            }
        }
       
        session.setAttribute("MATDTL", matDtl);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
