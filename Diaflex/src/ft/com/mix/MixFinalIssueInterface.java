package ft.com.mix;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MixFinalIssueInterface {
    public ArrayList getEmp(HttpServletRequest req , HttpServletResponse res);
//    public ArrayList MIXGenricSrch(HttpServletRequest req , HttpServletResponse res);
    public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res, String vnmLst );
    public ArrayList FecthResult(HttpServletRequest req,HttpServletResponse res, HashMap params);
    public HashMap GetTotal(HttpServletRequest req , HttpServletResponse res);
    public ArrayList getPrc(HttpServletRequest req ,HttpServletResponse res);
}
