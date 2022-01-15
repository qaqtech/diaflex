package ft.com.generic;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface GenericInterface {
    public ArrayList genricSrch(HttpServletRequest req , HttpServletResponse res,String sname,String mdl);
    public ArrayList genericPrprVw(HttpServletRequest req , HttpServletResponse res,String sname,String mdl);
    public HashMap SearchResult(HttpServletRequest req,HttpServletResponse res,String display,String mdl, String flg , ArrayList vwPrpLst);
    public HashMap NoGtPop(HttpServletRequest req,HttpServletResponse res,String display, String mdl,String client);
    public HashMap graphPath(HttpServletRequest req,HttpServletResponse res);
    public ArrayList DataColloction(HttpServletRequest req,HttpServletResponse res,HashMap dtlMap);
    public void CeatePdf(HttpServletRequest req,HttpServletResponse res,String URL,String filePath);
}
