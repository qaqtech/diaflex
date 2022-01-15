package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.sql.PreparedStatement;
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

public class Merge96UpMixAction  extends DispatchAction {
  
    public Merge96UpMixAction() {
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
           util.getOpenCursorConnection(db,util,info);
         util.updAccessLog(req,res,"Merge96UpMix", "load");
       Merge96UpMixForm  udf = (Merge96UpMixForm)af;   
        ArrayList pktList = new ArrayList();
        HashMap ttl= new HashMap();
        String mergeSql = " select memo , to_char(sum(cts),'9990.99') cts, trunc(((sum(cts* nvl(cmp, upr)) / sum(cts)))) avgPrc "+
                          " , trunc(sum(cts * nvl(upr, cmp)), 2) val "+
                          " from mix_stk_v where stt='AS_FN_SMX'  and cts >0 group by memo ";
         ArrayList  rsLst = db.execSqlLst("mergeSql",mergeSql, new ArrayList());
         PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
         ResultSet  rs =(ResultSet)rsLst.get(1);
        while(rs.next()){
            HashMap pktMap = new HashMap();
            pktMap.put("MEMO", util.nvl(rs.getString("memo")));
            pktMap.put("CTS", util.nvl(rs.getString("cts")));
            pktMap.put("AVGPRC", util.nvl(rs.getString("avgPrc")));
            pktMap.put("VAL", util.nvl(rs.getString("val")));
            pktList.add(pktMap);
        }
        rs.close();
         stmt.close();
       String gtttl = "select to_char(sum(cts),'9990.99') cts, trunc(((sum(cts* nvl(cmp, upr)) / sum(cts)))) avg \n" + 
       "                           , Trunc(Sum(Cts * Nvl(upr, cmp)), 2) val \n" + 
       "                           from mix_stk_v where stt='AS_FN_SMX' and cts >0" ;
          rsLst = db.execSqlLst("gtttl", gtttl, new ArrayList());
          stmt =(PreparedStatement)rsLst.get(0);
          rs =(ResultSet)rsLst.get(1);
       while(rs.next()){
           ttl=new HashMap();
           ttl.put("CTS",util.nvl(rs.getString("cts")));
           ttl.put("AVGPRC",util.nvl(rs.getString("avg")));
           ttl.put("VAL",util.nvl(rs.getString("val")));
       }  
       rs.close();
         stmt.close();
        req.setAttribute("pktList", pktList);
        req.setAttribute("TTL", ttl);
         util.updAccessLog(req,res,"Merge96UpMix", "load end");
        return am.findForward("load");
       }
   }
    public ActionForward merge(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
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
          util.updAccessLog(req,res,"Merge96UpMix", "merge");
        Merge96UpMixForm  udf = (Merge96UpMixForm)af;   
        ArrayList memoList = new ArrayList();
        Enumeration reqNme = req.getParameterNames();
        
        while(reqNme.hasMoreElements()) 
        {  
            String paramNm = (String)reqNme.nextElement();
             if(paramNm.indexOf("cb_memo_") > -1){
                String memo = req.getParameter(paramNm);
                memoList.add(memo);
            }
        }
        for(int i=0;i<memoList.size();i++){
            String memo = (String)memoList.get(i);
            ArrayList ary = new ArrayList();
            ary.add(memo);
            String merge = "MERGE_SMX_MEMO(pMemo => ?)";
           int ct = db.execCallDir("merge",merge,ary);
           if(ct>0)
               req.setAttribute("msg", "process done successfully...");
        }
          util.updAccessLog(req,res,"Merge96UpMix", "merge end");
       return load(am, af, req, res);
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
              util.updAccessLog(req,res,"Merge96UpMix", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Merge96UpMix", "init");
          }
          }
          return rtnPg;
       }

   
}
