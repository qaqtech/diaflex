package ft.com.assort;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.Mprc;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.IOException;

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

public class OddPathEditIssueAction extends DispatchAction {
    
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
     OddPathEditIssueForm  oddPathForm = (OddPathEditIssueForm)form;
          OddPathEditIssueInterface oddPathInt = new OddPathEditIssueImpl();
       oddPathForm.reset();
        ArrayList mprcList = oddPathInt.getPrc(req,res);
       oddPathForm.setMprcList(mprcList);
        
        ArrayList empList = oddPathInt.getEmp(req,res);
       oddPathForm.setEmpList(empList);

        ArrayList assortSrchList =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_asGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_asGNCSrch");
        info.setGncPrpLst(assortSrchList);
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
      
      OddPathEditIssueForm  oddPathForm = (OddPathEditIssueForm)form;
      OddPathEditIssueInterface oddPathInt = new OddPathEditIssueImpl();
        String mprcIdn = util.nvl((String)oddPathForm.getValue("mprcIdn"));
        String stoneId = util.nvl((String)oddPathForm.getValue("vnmLst"));
        String empId = util.nvl((String)oddPathForm.getValue("empIdn"));
        String issueIdn = util.nvl((String)oddPathForm.getValue("issueIdn"));
        ArrayList genricSrchLst =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_asGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_asGNCSrch");
        info.setGncPrpLst(genricSrchLst);
//        ArrayList genricSrchLst = info.getGncPrpLst();
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        HashMap prcBeansList = (HashMap)session.getAttribute("mprcBean");
        Mprc mprcDto = (Mprc)prcBeansList.get(mprcIdn);
        String prcStt = mprcDto.getIn_stt();
        HashMap params = new HashMap();
        params.put("stt", prcStt);
        params.put("vnm",stoneId);
        params.put("empIdn", empId);
        params.put("mprcIdn", mprcIdn);
        params.put("issueIdn", issueIdn);
        HashMap paramsMap = new HashMap();
            for(int i=0;i<genricSrchLst.size();i++){
                ArrayList prplist =(ArrayList)genricSrchLst.get(i);
                String lprp = (String)prplist.get(0);
                String flg= (String)prplist.get(1);
                String typ = util.nvl((String)mprp.get(lprp+"T"));
                String prpSrt = lprp ;  
                if(flg.equals("M")) {
                
                    ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                    ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                    for(int j=0; j < lprpS.size(); j++) {
                    String lSrt = (String)lprpS.get(j);
                    String lVal = (String)lprpV.get(j);    
                    String reqVal1 = util.nvl((String)oddPathForm.getValue(lprp + "_" + lVal),"");
                    if(!reqVal1.equals("")){
                    paramsMap.put(lprp + "_" + lVal, reqVal1);
                    }
                       
                    }
                }else{
                String fldVal1 = util.nvl((String)oddPathForm.getValue(lprp+"_1"));
                String fldVal2 = util.nvl((String)oddPathForm.getValue(lprp+"_2"));
                if(typ.equals("T")){
                    fldVal1 = util.nvl((String)oddPathForm.getValue(lprp+"_1"));
                    fldVal2 = fldVal1;
                }
                if(fldVal2.equals(""))
                fldVal2=fldVal1;
                if(!fldVal1.equals("") && !fldVal2.equals("")){
                    paramsMap.put(lprp+"_1", fldVal1);
                    paramsMap.put(lprp+"_2", fldVal2);
                }
                }
            }
        ArrayList stockList = null;
        if(paramsMap.size()>0){
        paramsMap.put("stt", prcStt);
        paramsMap.put("mdl", "AS_VIEW");
        util.genericSrch(paramsMap);
        stockList = oddPathInt.SearchResult(req,res, "");
        }else{
       stockList = oddPathInt.FecthResult(req,res, params);
        }
        if(stockList.size()>0){
        HashMap totals = oddPathInt.GetTotal(req,res);
        ArrayList prpList = oddPathInt.prcPrpList(req, res , mprcIdn);
        session.setAttribute("labSVCPrpList", prpList);
        req.setAttribute("totalMap", totals);
        }
        
        req.setAttribute("view", "Y");
        oddPathForm.setValue("prcId", mprcIdn);
        oddPathForm.setValue("empId", empId);
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
     
      OddPathEditIssueForm  oddPathForm = (OddPathEditIssueForm)form;
        String prcId = (String)oddPathForm.getValue("prcId");
        String empId = (String)oddPathForm.getValue("empId");
        ArrayList params = null;
        int issueIdn = 0;
        ArrayList prpList = (ArrayList)session.getAttribute("labSVCPrpList");
        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
        for(int i=0;i< stockList.size();i++){
            HashMap stockPkt = (HashMap)stockList.get(i);
             String stkIdn = (String)stockPkt.get("stk_idn");
             String isChecked = util.nvl((String)oddPathForm.getValue(stkIdn));
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
                
                
                for(int j=0 ; j< prpList.size() ; j++){
                String lprp = (String)prpList.get(j);
                String fldVal =util.nvl((String)oddPathForm.getValue(lprp+"_"+stkIdn));
                if(!fldVal.equals("") && !fldVal.equals("0")){
                 params = new ArrayList();
                 params.add(String.valueOf(issueIdn));
                 params.add(stkIdn);
                 params.add(lprp);
                 params.add(fldVal);
                ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", params);
                oddPathForm.setValue(lprp, "");
                }}
                
            }
       }
        if(issueIdn!=0){
        req.setAttribute( "msg","Requested packets get Issue with Issue Id "+issueIdn);
        req.setAttribute( "issueidn",String.valueOf(issueIdn));
        }
    
     oddPathForm.reset();
     return am.findForward("load");
        }
    }
    
    
    public OddPathEditIssueAction() {
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
              util.updAccessLog(req,res,"odd Path Issue", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"odd Path Issue", "init");
          }
          }
          return rtnPg;
          }
    
}
