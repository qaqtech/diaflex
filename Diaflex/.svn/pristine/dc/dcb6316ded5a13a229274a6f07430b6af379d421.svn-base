package ft.com.Repair;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.assort.AssortIssueForm;
import ft.com.assort.AssortIssueImpl;
import ft.com.assort.AssortIssueInterface;

import ft.com.dao.Mprc;

import ft.com.generic.GenericImpl;
import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.sql.CallableStatement;

import java.sql.Connection;
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

public class RepairingIssueAction extends DispatchAction {
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.getOpenCursorConnection(db,util,info);
        util.updAccessLog(req,res,"Repairing Issue", "load start");
        RepairingIssueForm repairForm = (RepairingIssueForm)form;
        RepairingIssueInterface repairInt=new RepairingIssueImpl();
        repairForm.resetALL();
            
        ArrayList mprcList = repairInt.getPrc(req,res);
         repairForm.setMprcList(mprcList);
            
        ArrayList empList = repairInt.getEmp(req,res);
        repairForm.setEmpList(empList);
        repairForm.setTrmsLst(new ArrayList());
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("REPAIR_ISSUE");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("REPAIR_ISSUE");
        allPageDtl.put("REPAIR_ISSUE",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Repairing Issue", "load end");
     return am.findForward("load");
        }
    }
    public ActionForward view(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Repairing Issue", "view start");
            RepairingIssueForm repairForm = (RepairingIssueForm)form;
            RepairingIssueInterface repairInt=new RepairingIssueImpl();
        String view = util.nvl(repairForm.getView());
        String viewAll = util.nvl(repairForm.getViewAll());
        String repView = util.nvl(repairForm.getRepView());
        String stoneId = util.nvl((String)repairForm.getValue("vnmLst"));
        String empIdn = util.nvl((String)repairForm.getValue("empIdn"));
        String rlnId = util.nvl((String)repairForm.getValue("byrRln"));
        String stt = util.nvl((String)repairForm.getValue("stt"));
        HashMap params = new HashMap();
        if(!view.equals("")&& !(stoneId.equals(""))){
        stoneId = util.getVnm(stoneId);
        params.put("vnm",stoneId);
        }
        
        if(!repView.equals("")){
            params.put("RepView",repView);
            if(!stoneId.equals("")){
            stoneId = util.getVnm(stoneId);
            params.put("vnm",stoneId);
            }
        }        
        
        params.put("fact", empIdn);
        params.put("stt", stt);
        ArrayList stockList = repairInt.FecthResult(req,res, params,repairForm);
        
        if(stockList.size()>0){
        HashMap totals = repairInt.GetTotal(req,res);
        req.setAttribute("totalMap", totals);
        }
        
        ArrayList  trmList = repairInt.getTerm(req,res, empIdn);
        repairForm.setTrmsLst(trmList);
        repairForm.setValue("byrRln", rlnId);
        req.setAttribute("view", "Y");
        repairForm.setView("");
        repairForm.setViewAll("");
        repairForm.setValue("emp", empIdn);
        repairForm.setValue("rln", rlnId);
        repairForm.setValue("stt", stt);
        session.setAttribute("StockList", stockList);
        repairInt.IssueEdit(req, res,"REP_IS");
            util.updAccessLog(req,res,"Repairing Issue", "view end");
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
            util.updAccessLog(req,res,"Repairing Issue", "Issue start");
            RepairingIssueForm repairForm = (RepairingIssueForm)form;
            RepairingIssueInterface repairInt=new RepairingIssueImpl();
        int issueIdn =0;
        ArrayList ary =null;
        String empIdn = (String)repairForm.getValue("emp");
        String mprcIdn = (String)repairForm.getValue("mprcIdn");
            int prcIdn =0;
            if(mprcIdn.equals("")){
            prcIdn = repairInt.mprcIdn(req,res);
            } else{
            prcIdn=Integer.parseInt(mprcIdn);
            }
            
        String byrRln = (String)repairForm.getValue("rln");
        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
            ArrayList issEditPrp = (session.getAttribute("RepairIssueEditPRP") == null)?new ArrayList():(ArrayList)session.getAttribute("RepairIssueEditPRP");
        String selectPkt = "";
        for(int i=0;i< stockList.size();i++){
            HashMap stockPkt = (HashMap)stockList.get(i);
             String stkIdn = (String)stockPkt.get("stk_idn");
             String isChecked = util.nvl((String)repairForm.getValue(stkIdn));
            if(isChecked.equals("yes")){
                
                if(issueIdn==0){
                    ary = new ArrayList();
                    ary.add(String.valueOf(prcIdn));
                    ary.add(empIdn);
                    ArrayList out = new ArrayList();
                    out.add("I");
                    CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", ary ,out);
                    issueIdn = cst.getInt(3);
                  cst.close();
                  cst=null;
                }
                
                ary = new ArrayList();
                ary.add(String.valueOf(issueIdn));
                ary.add(stkIdn);
                ary.add(stockPkt.get("cts"));
                ary.add(stockPkt.get("qty"));
                ary.add("IS");
                String issuePkt = "ISS_RTN_PKG.ISS_PKT(pIssId => ?, pStkIdn => ? , pCts => ?,  pQty => ? , pStt => ?)";
                int ct = db.execCall("issuePkt", issuePkt, ary);
                selectPkt = selectPkt + stkIdn+",";
            for(int j=0 ; j < issEditPrp.size() ;j++){  
                String lprp = (String)issEditPrp.get(j);
                String lprpVal = util.nvl((String)repairForm.getValue(lprp+"_"+stkIdn));
             if(!lprpVal.equals("0") && !lprpVal.equals("")){
                 ary = new ArrayList();
                 ary.add(String.valueOf(issueIdn));
                 ary.add(stkIdn);
                 ary.add(lprp);
                 ary.add(lprpVal);
                 ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
                 
               ary = new ArrayList();
               ary.add(stkIdn);
               ary.add(lprp);
               ary.add(lprpVal);
               String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
               ct = db.execCall("stockUpd",stockUpd, ary);
               
             }}
        }}
        int memoIdn =0;
        selectPkt = util.getVnm(selectPkt);
        int ct = db.execUpd("delete gt","Delete from gt_srch_rslt where stk_idn not in ("+selectPkt+")", new ArrayList());
       
        ary = new ArrayList();
        ary.add(empIdn);
        ary.add(byrRln);
        ary.add("IS");
        ary.add("REP");
        ary.add("NN");
         
        ArrayList out = new ArrayList();
        out.add("I");
        CallableStatement cst = null;
        cst = db.execCall("MKE_HDR ", "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ?  , pTyp => ? , pflg => ? , pMemoIdn => ?)",ary, out);

        memoIdn = cst.getInt(6);
          cst.close();
          cst=null;
        if(memoIdn!=0){
        ary = new ArrayList();
        ary.add(Integer.toString(memoIdn));
        ary.add("IS");
        int ct1 = db.execCall("pop Memo from gt", "MEMO_PKG.POP_MEMO_FRM_GT(?,?)",ary);
        ary = new ArrayList();
        ary.add(Integer.toString(memoIdn));
        int ct2 = db.execCall("jan_qty", "jan_qty(?)", ary);
        if(ct1 > 0){
//        String updateMstk = "update mstk set stt='REP_IS' where idn in ("+selectPkt+")";
//        ct2 = db.execUpd("updateMstk",updateMstk, new ArrayList());
        req.setAttribute("msg","Requested packets get Issue with Issue Id:"+issueIdn+" And Memo Id :"+memoIdn);
        req.setAttribute("repMemoId", Integer.toString(memoIdn));
        req.setAttribute("issueId", Integer.toString(issueIdn));
        }else{
        req.setAttribute( "msg","Error during issue packets");           
        }
        }
       repairForm.reset();
       int accessidn=util.updAccessLog(req,res,"Repairing Issue", "view end");
       req.setAttribute("accessidn", String.valueOf(accessidn));
     return am.findForward("load");
        }
    
    }
    public RepairingIssueAction() {
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
                util.updAccessLog(req,res,"Repairing Issue", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Repairing Issue", "init");
            }
            }
            return rtnPg;
            }
}
