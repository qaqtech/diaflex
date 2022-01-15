package ft.com.lab;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface LabIssueInterface {
  public ArrayList getLab(HttpServletRequest req , HttpServletResponse res);
  public ArrayList getService(HttpServletRequest req , HttpServletResponse res);
  public HashMap FetchResult(HttpServletRequest req, HttpServletResponse res, HashMap params,LabIssueForm form,String LabForm);
  public ArrayList FetchResult(HttpServletRequest req ,HttpServletResponse res, LabIssueForm form);
  public HashMap insertgt(HttpServletRequest req ,HttpServletResponse res, LabIssueForm form,String LabForm);  
  public ArrayList insert(HttpServletRequest req ,HttpServletResponse res, LabIssueForm form);
    public ArrayList getPrc(HttpServletRequest req ,HttpServletResponse res);
  public HashMap GetTotal(HttpServletRequest req , HttpServletResponse res);
  public HashMap GetTotalNew(HttpServletRequest req , HttpServletResponse res);
  public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res,String vnm, LabIssueForm form);
//  public ArrayList LBGenricSrch(HttpServletRequest req , HttpServletResponse res);
  public void delGt(HttpServletRequest req, HttpServletResponse res,String vnm);
  public HashMap SearchResultGT(HttpServletRequest req ,HttpServletResponse res,String vnm, LabIssueForm form,String LabForm);
  public void LabIssueEdit(HttpServletRequest req,HttpServletResponse res,String issStt);
  public ArrayList validateMapping(HttpServletRequest req , HttpServletResponse res);
}
