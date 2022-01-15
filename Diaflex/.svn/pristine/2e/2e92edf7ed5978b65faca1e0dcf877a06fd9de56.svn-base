package ft.com.assort;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class DeptSmryAction extends DispatchAction {
    
     
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String rtnPg="sucess";
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg=init(req,res,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
      String dept = "96-UP-GIA";
      
      String query ="select FIRST.idn prc_id, FIRST.dsc dsc, FIRST.stt stt, SECOND.qty qty, SECOND.cts cts \n" + 
      "from (\n" + 
      "select idn, dsc, in_stt stt, null qty, null cts from mprc\n" + 
      "where stt='A' and in_stt <> 'NA'\n" + 
      "order by srt) FIRST\n" + 
      "LEFT JOIN (\n" + 
      "select null idn, null dsc, a.stt stt, SUM (a.qty) qty, SUM (a.cts) cts \n" + 
      "from mstk a, stk_dtl b\n" + 
      "where a.idn = b.mstk_idn\n" + 
      "and b.mprp = 'DEPT' AND b.VAL = '"+ dept +"' \n" + 
      "group by stt) SECOND\n" + 
      "ON FIRST.STT = SECOND.STT";
      
      ArrayList arrprc = new ArrayList();
      HashMap htprc = new HashMap();
      
      ResultSet rs = db.execSql("Get process", query, new ArrayList());
      while(rs.next()) {
          
          htprc = new HashMap();
          
          htprc.put("dsc", rs.getString("dsc"));
          htprc.put("stt", rs.getString("stt"));
          htprc.put("prc_id", rs.getString("prc_id"));
          
          String qty = "";
          String cts = "";
          if(rs.getString("qty")!=null) {
            qty = rs.getString("qty");
          }
          htprc.put("qty", qty);
          
          if(rs.getString("cts")!=null) {
            cts = rs.getString("cts");
          }
          htprc.put("cts", cts);
          
          arrprc.add(htprc);
          System.out.println("********process: "+ htprc);
          
      }
        rs.close();
      /*String query ="select prc, dsc, in_stt from mprc\n" + 
      "where stt='A' and in_stt <> 'NA'\n" + 
      "order by srt";
      
      ArrayList arrprc = new ArrayList();
      HashMap htprc = new HashMap();
      HashMap temp = new HashMap(); 
      
      ResultSet rs = db.execSql("Get process", query, new ArrayList());
      while(rs.next()) {
          
          arrprc.add(rs.getString("prc"));
          temp = new HashMap();
          temp.put("prc", rs.getString("prc"));
          temp.put("dsc", rs.getString("dsc"));
          temp.put("in_stt", rs.getString("in_stt"));
          htprc.put(rs.getString("prc"), temp);
      }
      
      query = "select a.stt stt, SUM (a.qty) qty, SUM (a.cts) cts \n" + 
      "from mstk a, stk_dtl b\n" + 
      "where a.idn = b.mstk_idn\n" + 
      "and b.mprp = 'DEPT' AND b.VAL = '96-UP-GIA' \n" + 
      "group by stt";
      
      ArrayList arrSum = new ArrayList();
      HashMap htSum = new HashMap();
      
      rs = db.execSql("Get Summary", query, new ArrayList());
      while(rs.next()) {
          
          arrSum.add(rs.getString("stt"));
          temp = new HashMap();
          temp.put("qty", rs.getString("qty"));
          temp.put("cts", rs.getString("cts"));
          
          htSum.put(rs.getString("stt"), temp);
      }
      
      req.setAttribute("arrSum", arrSum);
      req.setAttribute("htSum", htSum);*/
      
      req.setAttribute("arrprc", arrprc);
      //req.setAttribute("htprc", htprc);
      
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
              util.updAccessLog(req,res,"Assort Issue Action", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Assort Issue Action", "init");
          }
          }
          return rtnPg;
          }
    public DeptSmryAction() {
        super();
    }
}
