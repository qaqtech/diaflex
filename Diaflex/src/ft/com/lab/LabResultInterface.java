package ft.com.lab;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LabResultInterface {
    public ArrayList getLab(HttpServletRequest req , HttpServletResponse res);
    public HashMap FetchResult(HttpServletRequest req,HttpServletResponse res, HashMap params , LabResultForm form);
    public ArrayList StockView(HttpServletRequest req,HttpServletResponse res, HashMap params );
    public HashMap StockHistory(HttpServletRequest req ,HttpServletResponse res, HashMap params);
//    public ArrayList LBRSGenricSrch(HttpServletRequest req , HttpServletResponse res);
    public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res,String vnmLst, LabResultForm form );
    public ArrayList getLabFRGt(HttpServletRequest req , HttpServletResponse res);    
    public HashMap GetTotal(HttpServletRequest req, HttpServletResponse res);
    public ArrayList pktList(HttpServletRequest req , HttpServletResponse res,String flg);
    public HashMap AssortHistory(HttpServletRequest req ,HttpServletResponse res, HashMap params);
    public void packetData(HttpServletRequest req , HttpServletResponse res,LabResultForm form );
    public HashMap SearchResult(HttpServletRequest req ,HttpServletResponse res,String flg , ArrayList vwPrpLst );
    public ArrayList LabPrprViw(HttpServletRequest req , HttpServletResponse res);
    public HashMap ProcessHistory(HttpServletRequest req ,HttpServletResponse res, HashMap params);

}
