package ft.com.assort;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.Mprc;

import ft.com.generic.GenericImpl;

import ft.com.generic.GenericInterface;

import java.sql.CallableStatement;

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

public class MagnusReturnAction extends DispatchAction {
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
        AssortReturnForm  assortForm = (AssortReturnForm)form;
         AssortReturnInterface assortInt = new   AssortReturnImpl();
        
        assortForm.reset();
        ArrayList mprcList = assortInt.getPrc(req,res);
        assortForm.setMprcList(mprcList);
        
        ArrayList empList = assortInt.getEmp(req,res);
        assortForm.setEmpList(empList);
        
        
        
      return am.findForward("load");
        }
    }
    public ActionForward fecth(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        AssortReturnForm  assortForm = (AssortReturnForm)form;
         AssortReturnInterface assortInt = new   AssortReturnImpl();
        
        String mprcIdn = util.nvl((String)assortForm.getValue("mprcIdn"));
        String stoneId = util.nvl((String)assortForm.getValue("vnmLst"));
        String empId = util.nvl((String)assortForm.getValue("empIdn"));
        String issueId = util.nvl((String)assortForm.getValue("issueId"));
        HashMap prcBeansList = (HashMap)session.getAttribute("mprcBean");
        session.setAttribute("AssortStockList", new ArrayList());
        String prcStt = "";
        if(!issueId.equals("") && mprcIdn.equals("0")){
            ArrayList ary = new ArrayList();
            String issuStt = " select b.is_stt from iss_rtn a , mprc b where a.prc_id = b.idn and a.iss_id= ? ";
            ary.add(issueId);
            ResultSet rs = db.execSql("issueStt", issuStt, ary);
            while(rs.next()){
              prcStt = util.nvl(rs.getString("is_stt"));
            }
            rs.close();
        }else{
         Mprc mprcDto = (Mprc)prcBeansList.get(mprcIdn);
         prcStt = mprcDto.getIs_stt();
        }
        HashMap params = new HashMap();
        params.put("stt", prcStt);
        params.put("vnm",stoneId);
        params.put("empIdn", empId);
        params.put("issueId", issueId);
        ArrayList stockList = assortInt.FecthResult(req,res, params);
        if(stockList.size()>0){
        HashMap totals = assortInt.GetTotal(req,res);
        req.setAttribute("totalMap", totals);
            ArrayList options = assortInt.getOptions(req ,res, issueId);
          
            req.setAttribute("OPTIONS", options);


        }
        req.setAttribute("view", "Y");
        assortForm.setValue("prcId", mprcIdn);
        assortForm.setValue("empId", empId);
        session.setAttribute("AssortStockList", stockList);
       
        
        return am.findForward("load");
        }
    }
    
    public ActionForward stockUpd(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
         AssortReturnForm  assortForm = (AssortReturnForm)form;
          AssortReturnInterface assortInt = new   AssortReturnImpl();
        
        String prcId= (String)assortForm.getValue("prcId");
        String issId = req.getParameter("issIdn");
        String mstkIdn = req.getParameter("mstkIdn");
        HashMap params = new HashMap();
        params.put("prcId", prcId);
        params.put("issId", issId);
        params.put("mstkIdn", mstkIdn);
        
        ArrayList stockPrpList = assortInt.StockUpdPrp(req,res, assortForm , params );
        req.setAttribute("StockList", stockPrpList);
        assortForm.setValue("issIdn", issId);
        assortForm.setValue("mstkIdn", mstkIdn);
        return am.findForward("loadStock");
         }
     }
    
    public ActionForward savePrpUpd(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        AssortReturnForm  assortForm = (AssortReturnForm)form;
         AssortReturnInterface assortInt = new   AssortReturnImpl();
        
       
        String prcId= (String)assortForm.getValue("prcId");
        String issId = (String)assortForm.getValue("issIdn");
        String mstkIdn = (String)assortForm.getValue("mstkIdn");
       
        ArrayList assortPrpUpd = (ArrayList)session.getAttribute("assortUpdPrp");
        for(int i=0 ; i <assortPrpUpd.size();i++){
            
            String lprp = (String)assortPrpUpd.get(i);
            String fldVal =(String)assortForm.getValue(lprp);
            if(!fldVal.equals("") && !fldVal.equals("0")){
            ArrayList ary = new ArrayList();
            ary.add(issId);
            ary.add(mstkIdn);
            ary.add(lprp);
            ary.add(fldVal);
            int ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
            } 
            
        }
        
        HashMap params = new HashMap();
        params.put("prcId", prcId);
        params.put("issId", issId);
        params.put("mstkIdn", mstkIdn);
        
        ArrayList stockPrpList = assortInt.StockUpdPrp(req,res, assortForm , params );
        req.setAttribute( "msg","Propeties Get update successfully");
        req.setAttribute("StockList", stockPrpList);
        return am.findForward("loadStock");
        }
    }
    
    public ActionForward Return(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        AssortReturnForm  assortForm = (AssortReturnForm)form;
        ArrayList RtnMsgList = new ArrayList();
        String msg = "";
        int cnt = 0;
        ArrayList params = null;
        ArrayList returnPkt = new ArrayList();
        ArrayList stockList = (ArrayList)session.getAttribute("AssortStockList");
        for(int i=0;i< stockList.size();i++){
            HashMap stockPkt = (HashMap)stockList.get(i);
             String stkIdn = (String)stockPkt.get("stk_idn");
             String isChecked = util.nvl((String)assortForm.getValue(stkIdn));
            if(isChecked.equals("yes")){
                String lStkStt = util.nvl((String)assortForm.getValue("STT_"+stkIdn), "NA");
                ArrayList RtnMsg = new ArrayList();
                returnPkt.add(stkIdn);
                params = new ArrayList();
                params.add(stockPkt.get("issIdn"));
                params.add(stkIdn);
                params.add("RT");
                params.add(lStkStt);
                ArrayList out = new ArrayList();
                out.add("I");
                out.add("V");
                String issuePkt = "ISS_RTN_PKG.RTN_PKT(pIssId => ?, pStkIdn => ? , pStt => ? , pStkStt => ? , pCnt=>? , pMsg => ? )";
                CallableStatement ct = db.execCall("issue Rtn", issuePkt, params, out);
                cnt = ct.getInt(5);
                msg = ct.getString(6);
                RtnMsg.add(cnt);
                RtnMsg.add(msg);
                RtnMsgList.add(RtnMsg);
                ct.close();
            }
       }
    if(returnPkt.size()>0)
        req.setAttribute("msgList",RtnMsgList);
     
     return fecth(am, form, req, res);
        }
    }
    public MagnusReturnAction() {
        super();
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
}
