package ft.com;

import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CsvUtil {
    public CsvUtil() {
        super();
    }

    DBMgr   db;
    InfoMgr info;
    DBUtil  util;
    HttpSession session;
    public void init(DBMgr db, DBUtil util, InfoMgr info) {
        this.db   = db;
        this.util = util;
        this.info = info;
    }
    public String dataInCsv(HttpServletRequest req,HttpServletResponse res,String fileName,ArrayList csvLst)throws Exception{
        PrintWriter out = res.getWriter();
        res.setContentType("application/ms-excel");
        res.setHeader("Content-disposition","attachment; filename="+fileName);
        int row=csvLst.size();
        if(csvLst!=null && csvLst.size()>0){
        for(int m=0; m < row; m++){
        String ln ="";
        ln=(String)csvLst.get(m);
        out.println(ln);  
        }
            
        }
        
        return null;
    }
    
}