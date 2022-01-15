package ft.com.assorthk;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public interface AssortIssueInterface {
    public ArrayList getPrc(HttpServletRequest req);
    public ArrayList getEmp(HttpServletRequest req);
    public ArrayList FecthResult(HttpServletRequest req, HashMap params);
    public HashMap GetTotal(HttpServletRequest req);
    public ArrayList ASGenricSrch(HttpServletRequest req);
}
