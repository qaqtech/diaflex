package ft.com.lab;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.assort.AssortIssueForm;
import ft.com.dao.GtPktDtl;
import ft.com.dao.UIForms;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.report.ComparisonReportForm;

import java.io.IOException;

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
import java.sql.Connection;

import java.sql.PreparedStatement;

import java.util.Set;

public class LabComparisonAction extends DispatchAction{
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
            util.updAccessLog(req,res,"Lab Comparison", "load start");
        LabComparisonForm comparisonForm = (LabComparisonForm)af;
        GenericInterface genericInt = new GenericImpl();
        comparisonForm.resetAll();

        ArrayList lbSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBCOMGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBCOMGNCSrch"); 
        info.setGncPrpLst(lbSrchList);
            util.updAccessLog(req,res,"Lab Comparison", "load end");
        return am.findForward("load");
        }
    }
    
    public ActionForward view(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

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
            util.updAccessLog(req,res,"Lab Comparison", "view start");
            LabComparisonForm comparisonForm = (LabComparisonForm)form;
            LabComparisonInterface comparisonInt=new LabComparisonImpl();
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBCOMGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBCOMGNCSrch"); 
        info.setGncPrpLst(genricSrchLst);
        //      ArrayList genricSrchLst = info.getGncPrpLst();
        String view =util.nvl(comparisonForm.getView());
        String reportTyp = util.nvl(comparisonForm.getReportTyp());
        String vnmLst = util.nvl((String)comparisonForm.getValue("vnmLst"));
        String seq = util.nvl((String)comparisonForm.getValue("seq"));
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        HashMap params = new HashMap();
        params.put("vnm", vnmLst);
        params.put("seq", seq);
        if(view.length() > 0){
           
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
                        String reqVal1 = util.nvl((String)comparisonForm.getValue(lprp + "_" + lVal),"");
                        if(!reqVal1.equals("")){
                        paramsMap.put(lprp + "_" + lVal, reqVal1);
                        }
                           
                        }
                    }else{
                    String fldVal1 = util.nvl((String)comparisonForm.getValue(lprp+"_1"));
                    String fldVal2 = util.nvl((String)comparisonForm.getValue(lprp+"_2"));
                    if(typ.equals("T")){
                        fldVal1 = util.nvl((String)comparisonForm.getValue(lprp+"_1"));
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
           
            if(paramsMap.size()>0){
                paramsMap.put("stt", "LB_RT");
                paramsMap.put("mdl", "LBCOM_VIEW");
            util.genericSrch(paramsMap);
            }
            comparisonInt.insertGt(req,res, params);
            
        }
        
        HashMap pktDtlList = comparisonInt.FetchResult(req ,res, vnmLst , reportTyp);
        ArrayList empList = comparisonInt.getEmp(req,res);
        comparisonForm.setEmpList(empList);
        comparisonForm.setView("");
        String lstNme = "LABCOM_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
        gtMgr.setValue(lstNme+"_SEL",new ArrayList());
        gtMgr.setValue(lstNme, pktDtlList);
            gtMgr.setValue("lstNmeCOM", lstNme);

        req.setAttribute("view","Y");
        req.setAttribute("rptTyp", reportTyp);
            util.updAccessLog(req,res,"Lab Comparison", "view end");
       return am.findForward("load");
        }
    }
    
    public ActionForward Issue(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

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
            util.updAccessLog(req,res,"Lab Comparison", "Issue start");
            LabComparisonForm comparisonForm = (LabComparisonForm)form;
        int prcId = 0;
        String empId = (String)comparisonForm.getValue("empId");
        

            ArrayList outLst = db.execSqlLst("mprcId", "select idn from mprc where is_stt='LB_CHK_IS' and stt='A'", new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        if(rs.next()) {
         prcId = rs.getInt(1);
        }
        rs.close(); pst.close();
        ArrayList params = null;
        int issueIdn = 0;
            String lstNme = (String)gtMgr.getValue("lstNmeCOM");
            ArrayList stockIdnLst = new ArrayList();
            HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
            Set<String> keys = stockList.keySet();
                    for(String key: keys){
                   stockIdnLst.add(key);
                    }
            for(int i=0; i < stockIdnLst.size(); i++ ){
           String stkIdn = (String)stockIdnLst.get(i);

           GtPktDtl stockPkt = (GtPktDtl)stockList.get(stkIdn);
          String isChecked = util.nvl((String)comparisonForm.getValue(stkIdn));
            if(isChecked.equals("yes")){
                if(issueIdn==0){
                    params = new ArrayList();
                    params.add(String.valueOf(prcId));
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
                params.add(stockPkt.getValue("wt"));
                params.add(stockPkt.getValue("qty"));
                params.add("IS");
                String issuePkt = "ISS_RTN_PKG.ISS_PKT(pIssId => ?, pStkIdn => ? , pCts => ?,  pQty => ? , pStt => ?)";
                int ct = db.execCall("issuePkt", issuePkt, params);
                
            }
       }
    if(issueIdn!=0)
        req.setAttribute( "msg","Requested packets get Issue with Issue Id "+issueIdn);
     comparisonForm.resetAll();
            GtMgrReset(req);
            util.updAccessLog(req,res,"Lab Comparison", "Issue end");
     return am.findForward("load");
        }
    }
    
    public void GtMgrReset(HttpServletRequest req){
          HttpSession session = req.getSession(false);
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
          String lstNme = (String)gtMgr.getValue("lstNmeCOM");
          HashMap gtMgrMap = (HashMap)gtMgr.getValues();
          gtMgrMap.remove(lstNme);
           gtMgrMap.remove("LabIssueEditPRP");
           gtMgrMap.remove(lstNme+"_SEL");
           gtMgrMap.remove(lstNme+"_TTL");
           gtMgrMap.remove("lstNmeCOM");
         }
    public LabComparisonAction() {
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
                util.updAccessLog(req,res,"Lab Comparison", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Lab Comparison", "init");
            }
            }
            return rtnPg;
            }
}
