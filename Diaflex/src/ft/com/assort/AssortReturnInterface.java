package ft.com.assort;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AssortReturnInterface {
    public ArrayList getPrc(HttpServletRequest req, HttpServletResponse res);
    public ArrayList getEmp(HttpServletRequest req, HttpServletResponse res);
    public ArrayList FecthResult(HttpServletRequest req, HttpServletResponse res, HashMap params);
    public ArrayList StockUpdPrp(HttpServletRequest req, HttpServletResponse res,  AssortReturnForm form ,HashMap params);
    public HashMap GetTotal(HttpServletRequest req, HttpServletResponse res);
    public ArrayList getOptions(HttpServletRequest req , HttpServletResponse res, String issueId);
    public String issPrcStt(HttpServletRequest req , HttpServletResponse res ,int prcIdn);
    public String getLab(HttpServletRequest req , HttpServletResponse res ,String stkIdn);
    public void isRepairIS(HttpServletRequest req , HttpServletResponse res );
    public HashMap SearchResultGt(HttpServletRequest req ,HttpServletResponse res, String vnmLst );
    public HashMap FecthResultGt(HttpServletRequest req,HttpServletResponse res, HashMap params);
}
