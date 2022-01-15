package ft.com.lab;

import ft.com.assort.AssortFinalRtnForm;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LabComparisonInterface {
    public ArrayList ReporTypList(HttpServletRequest req , HttpServletResponse res);
//    public ArrayList LBCOMGenricSrch(HttpServletRequest req , HttpServletResponse res);
    public HashMap FetchResult(HttpServletRequest req, HttpServletResponse res,String vnmLst, String reportTyp);
    public int insertGt(HttpServletRequest req ,HttpServletResponse res, HashMap param);
    public ArrayList getEmp(HttpServletRequest req , HttpServletResponse res);
    
}
