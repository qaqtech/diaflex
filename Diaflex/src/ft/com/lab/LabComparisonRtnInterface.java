package ft.com.lab;

import ft.com.assort.AssortFinalRtnForm;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LabComparisonRtnInterface {
    public ArrayList getEmp(HttpServletRequest req , HttpServletResponse res);
    public ArrayList FecthResult(HttpServletRequest req, HttpServletResponse res, HashMap params);
    public HashMap GetTotal(HttpServletRequest req , HttpServletResponse res);
    public ArrayList getOptions(HttpServletRequest req ,HttpServletResponse res, String issueId);
    public ArrayList MultiUpdPRP(HttpServletRequest req , HttpServletResponse res);
    public ArrayList StockUpdPrp(HttpServletRequest req, HttpServletResponse res, LabComparisonRtnForm form ,HashMap params);
    public ArrayList pktList(HttpServletRequest req , HttpServletResponse res);    
    public ArrayList getOptionsPrc(HttpServletRequest req ,HttpServletResponse res, String prcIdn);
}
