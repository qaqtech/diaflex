package ft.com.inward;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;

import ft.com.marketing.MemoReportForm;

import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AssortLabReport extends DispatchAction {
   
    public AssortLabReport() {
        super();
    }
  public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
          throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String rtnPg="sucess";
      if(info!=null){
      Connection conn=info.getCon();
      if(conn!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg=init(req,res,session,util);
      }else{
      rtnPg="connExists";   
      }
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
      util.updAccessLog(req,res,"Assort Lab Report", "load start");
    AssortLabReportForm udf = (AssortLabReportForm)af;

    ResultSet rs           = null;
    ArrayList    ary          = null;
    ArrayList issuePrt      = new ArrayList();
    String    memoPrntOptn = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                             + "b.mdl = 'ASSORT' and b.nme_rule =  'CUST SLIP' and a.til_dte is null order by a.srt_fr ";

    rs = db.execSql("memoPrint", memoPrntOptn, new ArrayList());

    while (rs.next()) {
        UIForms issueOpn = new UIForms();

        issueOpn.setFORM_NME(rs.getString("chr_fr"));
        issueOpn.setFORM_TTL(rs.getString("dsc"));
        issuePrt.add(issueOpn);
    }
      rs.close();
    String p_iss_id = util.nvl((String)req.getAttribute("issueId"));
    if(!p_iss_id.equals("")){
        String sqlVal = "SELECT iss_id,  "+ 
        "    COUNT(*) qty, " + 
        "    SUM(a.cts) cts " + 
        "  FROM mstk a, " + 
        "    iss_rtn_dtl b " + 
        "  WHERE a.idn = b.iss_stk_idn " + 
        "  and iss_id=? " + 
        "  GROUP BY iss_id ";
        ary = new ArrayList();
        ary.add(p_iss_id);
        rs = db.execSql("Issue details", sqlVal, ary);
         if(rs.next()){
             udf.setValue("qty", rs.getString("qty"));
             udf.setValue("cts", rs.getString("cts"));
             udf.setValue("p_iss_id", p_iss_id);
         }
        rs.close();
        
    }
    udf.setValue("all","ALL");
//    udf.setValue("issuePrintList", issuePrt);
    udf.setIssuePrintList(issuePrt);
      int accessidn=util.updAccessLog(req,res,"Assort Lab Report", "load end");
          req.setAttribute("accessidn", String.valueOf(accessidn));;
      return am.findForward("load");
      }
  } 
  
    public String init(HttpServletRequest req , HttpServletResponse res,HttpSession session,DBUtil util) {
    String rtnPg="sucess";
    String invalide="";
    String connExists=util.nvl(util.getConnExists());  
    if(!connExists.equals("N"))
    invalide=util.nvl(util.chkTimeOut(),"N");
    if(session.isNew())
    rtnPg="sessionTO";    
    if(connExists.equals("N"))
    rtnPg="connExists";     
    if(invalide.equals("Y"))
    rtnPg="chktimeout";
    if(rtnPg.equals("sucess")){
    boolean sout=util.getLoginsession(req,res,session.getId());
    if(!sout){
    rtnPg="sessionTO";
    System.out.print("New Session Id :="+session.getId());
    }else{
        rtnPg=util.checkUserPageRights("",util.getFullURL(req));
        if(rtnPg.equals("unauthorized"))
        util.updAccessLog(req,res,"Assort Lab Report", "Unauthorized Access");
        else
    util.updAccessLog(req,res,"Assort Lab Report", "init");
    }
    }
    return rtnPg;
    }
}
