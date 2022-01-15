package ft.com.mix;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface MixIssueInterface {
    public ArrayList getPrc(HttpServletRequest req , HttpServletResponse res);
  public HashMap GetTotal(HttpServletRequest req , HttpServletResponse res);
    public ArrayList getEmp(HttpServletRequest req , HttpServletResponse res);
//  public ArrayList MIXGenricSrch(HttpServletRequest req , HttpServletResponse res);
  public void delGt(HttpServletRequest req, HttpServletResponse res,String vnm);
    public void LabIssueEdit(HttpServletRequest req,HttpServletResponse res);
    public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res, String vnmLst );
    public ArrayList FecthResult(HttpServletRequest req,HttpServletResponse res, HashMap params);
}
