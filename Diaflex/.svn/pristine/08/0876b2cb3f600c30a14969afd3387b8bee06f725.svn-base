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

public class IssuetoMagnusAction extends DispatchAction {
    
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
       IssuetoMagnusForm assortForm = (IssuetoMagnusForm)form;
          AssortIssueInterface assortInt = new AssortIssueImpl();
          GenericInterface genericInt =new GenericImpl();
        assortForm.reset();
        
        ArrayList mprcList = assortInt.getPrc(req,res);
        assortForm.setMprcList(mprcList);
        
        ArrayList empList = assortInt.getEmp(req,res);
        assortForm.setEmpList(empList);
        
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
       IssuetoMagnusForm assortForm = (IssuetoMagnusForm)form;
          AssortIssueInterface assortInt = new AssortIssueImpl();
        
        String mprcIdn = util.nvl((String)assortForm.getValue("mprcIdn"));
        String stoneId = util.nvl((String)assortForm.getValue("vnmLst"));
        String empId = util.nvl((String)assortForm.getValue("empIdn"));
       
        HashMap prcBeansList = (HashMap)session.getAttribute("mprcBean");
        Mprc mprcDto = (Mprc)prcBeansList.get(mprcIdn);
        String prcStt = mprcDto.getIn_stt();
        HashMap params = new HashMap();
        params.put("stt", prcStt);
        params.put("vnm",stoneId);
        params.put("empIdn", empId);
        params.put("mprcIdn", mprcIdn);
        HashMap stockList = assortInt.FecthResult(req, res,params);
        if(stockList.size()>0){
        HashMap totals = assortInt.GetTotal(req,res);
        req.setAttribute("totalMap", totals);
        }
        req.setAttribute("view", "Y");
        assortForm.setValue("prcId", mprcIdn);
        assortForm.setValue("empId", empId);
        session.setAttribute("StockList", stockList);
        
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
       IssuetoMagnusForm assortForm = (IssuetoMagnusForm)form;
        
        String prcId = (String)assortForm.getValue("prcId");
        String empId = (String)assortForm.getValue("empId");
        ArrayList params = null;
        int issueIdn = 0;
        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
        for(int i=0;i< stockList.size();i++){
            HashMap stockPkt = (HashMap)stockList.get(i);
             String stkIdn = (String)stockPkt.get("stk_idn");
             String isChecked = util.nvl((String)assortForm.getValue(stkIdn));
            if(isChecked.equals("yes")){
                if(issueIdn==0){
                    params = new ArrayList();
                    params.add(prcId);
                    params.add(empId);
                    ArrayList out = new ArrayList();
                    out.add("I");
                    CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", params ,out);
                    issueIdn = cst.getInt(3);
                  cst.close();
                  cst=null;
                }
                params = new ArrayList();
                params.add(String.valueOf(issueIdn));
                params.add(stkIdn);
                params.add(stockPkt.get("cts"));
                params.add(stockPkt.get("qty"));
                params.add("IS");
                String issuePkt = "ISS_RTN_PKG.ISS_PKT(pIssId => ?, pStkIdn => ? , pCts => ?,  pQty => ? , pStt => ?)";
                int ct = db.execCall("issuePkt", issuePkt, params);
                
            }
       }
    if(issueIdn!=0)
        req.setAttribute( "msg","Requested packets get Issue with Issue Id "+issueIdn);
     assortForm.reset();
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
    public IssuetoMagnusAction() {
        super();
    }
}
