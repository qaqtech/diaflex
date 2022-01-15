package ft.com.Repair;



import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public interface RepairingIssueInterface {
    public ArrayList FecthResult(HttpServletRequest req, HttpServletResponse res, HashMap params,RepairingIssueForm form);
    public HashMap GetTotal(HttpServletRequest req ,HttpServletResponse res);
    public ArrayList getEmp(HttpServletRequest req , HttpServletResponse res);
    public int mprcIdn(HttpServletRequest req , HttpServletResponse res);
    public ArrayList getTerm(HttpServletRequest req ,HttpServletResponse res, String ptyId);
    public void IssueEdit(HttpServletRequest req,HttpServletResponse res,String issStt);
    public ArrayList getPrc(HttpServletRequest req ,HttpServletResponse res);
}
