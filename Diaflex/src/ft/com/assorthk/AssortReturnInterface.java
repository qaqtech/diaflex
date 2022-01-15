package ft.com.assorthk;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public interface AssortReturnInterface {
    public ArrayList getPrc(HttpServletRequest req);
    public ArrayList getEmp(HttpServletRequest req);
    public ArrayList FecthResult(HttpServletRequest req, HashMap params);
    public ArrayList StockUpdPrp(HttpServletRequest req,  AssortReturnForm form ,HashMap params);
    public HashMap GetTotal(HttpServletRequest req);
    public ArrayList getOptions(HttpServletRequest req , String issueId);
}
