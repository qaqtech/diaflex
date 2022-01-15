package ft.com.assort;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.dao.Mprc;
import ft.com.fileupload.FileUploadForm;
import ft.com.fileupload.FileUploadInterface;

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

public class PendingRcvAction extends DispatchAction {
    
    
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
      GenericInterface genericInt =new GenericImpl();
      AssortIssueInterface assortInt = new AssortIssueImpl();
      PendingRcvForm  assortForm = (PendingRcvForm)form;
       
        assortForm.reset();
        
        /*ArrayList mprcList = assortInt.getPrc(req);
        assortForm.setMprcList(mprcList);
        */
        String prcid = req.getParameter("prc_id");
        req.setAttribute("prcId", prcid);
        //req.setAttribute("prcid", prcid);
        
        /*ArrayList empList = assortInt.getEmp(req);
        assortForm.setEmpList(empList);
        */
        
        //load the form as per the prc id
        
        ArrayList assortSrchList =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_asGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_asGNCSrch");
        info.setGncPrpLst(assortSrchList);
      return am.findForward("load");
        }
    }
    
    public ActionForward fetch(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        AssortIssueInterface assortInt = new AssortIssueImpl();
      PendingRcvForm  assortForm = (PendingRcvForm)form;
        String mprcIdn = util.nvl((String)assortForm.getValue("mprcIdn"));
        String stoneId = util.nvl((String)assortForm.getValue("vnmLst"));
        String stktyp = util.nvl((String)assortForm.getValue("typ"));
        //String empId = util.nvl((String)assortForm.getValue("empIdn"));
        
        //fetch the status as per process id
        /*String sttquery = "select is_stt from mprc where idn = '" + mprcIdn + "' ";
        ResultSet rs = db.execSql("search Result", sttquery, new ArrayList());
        
        String stt = "";
        if(rs.next()) {
          stt = rs.getString("is_stt");
        }
        */
        //fetch the records as per selection
        String delQ = " Delete from gt_srch_rslt where flg in ('AV')";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        String srchRefQ = "";
        ArrayList ary = new ArrayList();
        String vnm = "";
        
        if(stoneId.length()>1) {
          
          vnm = util.getVnm(stoneId);
          srchRefQ = " Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk ) " + 
            " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'AV' , qty , cts , sk1 , tfl3  "+
            "     from mstk b " +
            " where b.vnm in ("+ vnm +") " +
              " or b.tfl3 in ("+ vnm +") ";
          
        }
        else
        {
          srchRefQ =
              "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk ) " + 
              " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'AV' , qty , cts , sk1 , tfl3  "+
              "     from mstk b, stk_dtl c "+
              " where b.idn = c.mstk_idn and stt =( select is_stt from mprc where idn = ? ) " +
              " and mprp = 'MFG_TYP' and val = ? " +
              " and cts > 0  ";
          //System.out.println("Insert query: "+srchRefQ);
          ary.add(mprcIdn);
          ary.add(stktyp);
        }
        
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary); 
        
        //prepare the gt_srch_rstl table
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("AS_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        HashMap stockList = new HashMap();
        
        stockList = ( (AssortIssueImpl) assortInt).SearchResult(req ,res, vnm);
        
        /*HashMap prcBeansList = (HashMap)session.getAttribute("mprcBean");
        Mprc mprcDto = (Mprc)prcBeansList.get(mprcIdn);
        String prcStt = mprcDto.getIn_stt();
        
        String prcStt = "MF_FL";
        HashMap params = new HashMap();
        params.put("stt", prcStt);
        params.put("vnm",stoneId);
        params.put("empIdn", empId);
        params.put("mprcIdn", mprcIdn);
        ArrayList stockList = assortInt.FecthResult(req, params);
        if(stockList.size()>0){
        HashMap totals = assortInt.GetTotal(req);
        req.setAttribute("totalMap", totals);
        }
        req.setAttribute("view", "Y");
        assortForm.setValue("prcId", mprcIdn);
        assortForm.setValue("empId", empId);*/
        
        HashMap totals = assortInt.GetTotal(req,res);
        ((AssortIssueImpl) assortInt).ASPrprViw(req,res);
        req.setAttribute("totalMap", totals);
        
        assortForm.setValue("prcId", mprcIdn);
        req.setAttribute("prcId", mprcIdn);
        req.setAttribute("view", "Y");
        session.setAttribute("StockList", stockList);
        req.setAttribute("stktyp", stktyp);
        
     
        return am.findForward("load");
        }
    }
    public ActionForward Issue(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        PendingRcvForm  assortForm = (PendingRcvForm)form;
        String prcId = (String)assortForm.getValue("prcId");
        //String empId = (String)assortForm.getValue("empId");
        
        String stktyp = (String)assortForm.getValue("stktyp");
        
        ArrayList params = null;
        int issueIdn = 0;
        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
        
        for(int i=0;i< stockList.size();i++)
        {
          HashMap stockPkt = (HashMap)stockList.get(i);
          String stkIdn = (String)stockPkt.get("stk_idn");
          String isChecked = util.nvl((String)assortForm.getValue(stkIdn));
          StringBuffer idns = new StringBuffer();
          if(isChecked.equals("yes"))
          {
              /*if(issueIdn==0){
                  params = new ArrayList();
                  params.add(prcId);
                  params.add(empId);
                  ArrayList out = new ArrayList();
                  out.add("I");
                  CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", params ,out);
                  issueIdn = cst.getInt(3);
              }
              params = new ArrayList();
              params.add(String.valueOf(issueIdn));
              params.add(stkIdn);
              params.add(stockPkt.get("cts"));
              params.add(stockPkt.get("qty"));
              params.add("IS");
              String issuePkt = "ISS_RTN_PKG.ISS_PKT(pIssId => ?, pStkIdn => ? , pCts => ?,  pQty => ? , pStt => ?)";
              int ct = db.execCall("issuePkt", issuePkt, params);*/
              
              //idns.append(stkIdn + " ,");
              //int delIndex = idns.length();
              //idns.replace(delIndex-1, delIndex-1, " ");
              
              //now update the status as per type 
              
              
          }
        }
        /*if(issueIdn!=0)
            req.setAttribute( "msg","Requested packets get Issue with Issue Id "+issueIdn);
        */
        req.setAttribute("msg", "Selected packets are Received.");
        assortForm.reset();
        return am.findForward("loadPending");
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
              util.updAccessLog(req,res,"PendingRcvAction", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"PendingRcvAction", "init");
          }
          }
          return rtnPg;
          }
    public PendingRcvAction() {
        super();
    }
}
