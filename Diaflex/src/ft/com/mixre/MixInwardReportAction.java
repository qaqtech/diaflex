package ft.com.mixre;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import ft.com.mixakt.MixInwardReportForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MixInwardReportAction  extends DispatchAction {
    
    public MixInwardReportAction() {
        super();
    }
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
          MixInwardReportForm udf = (MixInwardReportForm)af;
          udf.reset();
          return am.findForward("load"); 
      }
   }
    
    
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
          MixInwardReportForm udf = (MixInwardReportForm)af;
          ArrayList pktList = new ArrayList();
          String frmdte = util.nvl((String)udf.getValue("frmdte"));
          String todte = util.nvl((String)udf.getValue("todte"));
          String conQ="";
          if(!frmdte.equals("") && !todte.equals("")){
              conQ = conQ+" and trunc(a.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
          }
          String inwardSql="select 'MFG' typ, b.srt, b.txt val , m.dsc,to_char(a.dte,'dd-MON-yyyy') dte, to_char(sum(a.cts),'999990.00') cts , sum(a.qty) qty , \n" + 
          "trunc(((sum(trunc(a.cts,2)*nvl(a.upr, a.cmp)) / sum(trunc(a.cts,2))))) avg_Rte\n" + 
          "from mstk a , stk_dtl b , stk_dtl c,prp m\n" + 
          "where a.idn=b.mstk_idn and a.pkt_ty='MIX' and b.mprp='STK_CTG' and b.grp=1 and\n" + 
          "b.mstk_idn=c.mstk_idn and c.mprp='RECPT_DT' and c.grp=1\n" + 
          "and m.mprp='STK_CTG' and m.val=b.val \n" +conQ+ 
          "  and a.cts > 0 \n" + 
          "group by b.txt , b.srt ,m.dsc,a.dte";
        
          
          ArrayList rsLst = db.execSqlLst("inwardSql", inwardSql, new ArrayList());
          PreparedStatement pst = (PreparedStatement)rsLst.get(0);
          ResultSet rs = (ResultSet)rsLst.get(1);
          while(rs.next()){
              HashMap pktDtlMap = new HashMap();
              pktDtlMap.put("TYP",util.nvl(rs.getString("typ")));
              pktDtlMap.put("BOXTYP",util.nvl(rs.getString("val")));
              pktDtlMap.put("CTS",util.nvl(rs.getString("cts")));
              pktDtlMap.put("QTY",util.nvl(rs.getString("qty")));
              pktDtlMap.put("AVGRTE",util.nvl(rs.getString("avg_Rte")));
              pktDtlMap.put("DSC",util.nvl(rs.getString("dsc")));
              pktDtlMap.put("DTE",util.nvl(rs.getString("dte")));
              pktList.add(pktDtlMap);
             }
          rs.close();
          pst.close();
         udf.setPKTLIST(pktList);
          req.setAttribute("view", "yes");
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
                util.updAccessLog(req,res,"Mix Assort Rtn", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Mix Assort Rtn", "init");
            }
            }
            return rtnPg;
            }
    
}
