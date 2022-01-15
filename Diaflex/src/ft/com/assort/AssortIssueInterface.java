package ft.com.assort;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AssortIssueInterface {
    public ArrayList getPrc(HttpServletRequest req , HttpServletResponse res);
    public ArrayList getEmp(HttpServletRequest req , HttpServletResponse res);
    public HashMap FecthResult(HttpServletRequest req,HttpServletResponse res, HashMap params);
    public HashMap GetTotal(HttpServletRequest req , HttpServletResponse res);
//    public ArrayList ASGenricSrch(HttpServletRequest req , HttpServletResponse res);
    public HashMap SearchResult(HttpServletRequest req ,HttpServletResponse res, String vnmLst );
    public void IssueEdit(HttpServletRequest req,HttpServletResponse res,String issStt);   

    
}
