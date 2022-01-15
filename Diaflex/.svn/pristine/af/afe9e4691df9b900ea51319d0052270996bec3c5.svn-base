package ft.com.lab;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.GtPktDtl;
import ft.com.dao.Mprc;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.lab.LabRecheckForm;
import ft.com.lab.LabRecheckImpl;
import ft.com.lab.LabRecheckInterface;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LabRecheckAction extends DispatchAction{
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
            util.updAccessLog(req,res,"Lab Recheck", "load start");
    LabRecheckForm labForm = (LabRecheckForm)form;
    GenericInterface genericInt = new GenericImpl();
        labForm.resetAll();

    ArrayList checkSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_lbRIGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_lbRIGNCSrch"); 
    info.setGncPrpLst(checkSrchList);
        HashMap prp = info.getPrp();
        ArrayList prpSrt = (ArrayList)prp.get("LABS");
        ArrayList prpVal = (ArrayList)prp.get("LABV");
        String srt = (String)prpSrt.get(prpVal.indexOf("GIA"));
        labForm.setValue("LAB_1", srt);
            util.updAccessLog(req,res,"Lab Recheck", "load end");
    return am.findForward("load");
        }
    }

    public ActionForward fetch(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Lab Recheck", "fetch start");
            LabRecheckForm labForm = (LabRecheckForm)form;
            LabRecheckInterface labInt=new LabRecheckImpl();
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        String stoneId = util.nvl((String)labForm.getValue("vnmLst"));
        String lab = util.nvl((String)labForm.getValue("LAB_1"));
        ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_lbRIGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_lbRIGNCSrch"); 
        info.setGncPrpLst(genricSrchLst);
        HashMap stockList = null;
        //      ArrayList genricSrchLst = info.getGncPrpLst();
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        if(stoneId.equals("")){
        HashMap params = new HashMap();
        params.put("vnm",stoneId);
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
                    String reqVal1 = util.nvl((String)labForm.getValue(lprp + "_" + lVal),"");
                    if(!reqVal1.equals("")){
                    paramsMap.put(lprp + "_" + lVal, reqVal1);
                    }
                       
                    }
                }else{
                String fldVal1 = util.nvl((String)labForm.getValue(lprp+"_1"));
                String fldVal2 = util.nvl((String)labForm.getValue(lprp+"_2"));
                if(typ.equals("T")){
                    fldVal1 = util.nvl((String)labForm.getValue(lprp+"_1"));
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
        paramsMap.put("stt", "ALL");
        paramsMap.put("mdl", "LAB_VIEW");
        util.genericSrch(paramsMap);
        }else{
            labInt.insertgt(req,res,labForm);
        }
//        if(!stoneId.equals(""))
//            labInt.delGt(req,res,stoneId);
       stockList = labInt.SearchResult(req,res, "");
      String lstNme = "LABRCK_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
      gtMgr.setValue(lstNme+"_SEL",new ArrayList());
      gtMgr.setValue(lstNme, stockList);
      gtMgr.setValue("lstNmeRCK", lstNme);
      if(stockList.size()>0){
          HashMap totals = labInt.GetTotal(req,res);
            gtMgr.setValue(lstNme+"_TTL", totals);
          labInt.LabIssueEdit(req, res);
        }
        req.setAttribute("view", "Y");
        labForm.setValue("lab", lab);
        gtMgr.setValue("srchReckObsMap",new HashMap());
        util.updAccessLog(req,res,"Lab Recheck", "fetch end");
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
            util.updAccessLog(req,res,"Lab Recheck", "Issue start");
            LabRecheckForm labForm = (LabRecheckForm)form;
        ArrayList params = null;
        ArrayList ary = null;
        int issueIdn = 0;
        ResultSet rs =null;
        String lab="";
        String labIdn = util.nvl((String)labForm.getValue("lab"));
            String lstNme = (String)gtMgr.getValue("lstNmeRCK");
            HashMap srchRcObList = (HashMap)gtMgr.getValue("srchReckObsMap");
            ArrayList selectstkIdnLst = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
            HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
            ArrayList issEditPrp = (ArrayList)gtMgr.getValue("LabIssueEditPRP");
            if(selectstkIdnLst!=null && selectstkIdnLst.size()>0){
                 for(int i=0;i< selectstkIdnLst.size();i++){
             String stkIdn = (String)selectstkIdnLst.get(i);
             GtPktDtl pktDtl = (GtPktDtl)stockList.get(stkIdn);
             String isChecked = util.nvl((String)labForm.getValue(stkIdn));
            if(isChecked.equals("yes")){
                if(issueIdn==0){
                    int rcPrcIdn = util.getProcess("LB_RI");
                    ary=new ArrayList();
                    int empIdn = 0;
                   
                    String labQ="select val from prp where srt="+labIdn+" and mprp='LAB'";

                    ArrayList outLst = db.execSqlLst("lab", labQ, ary);
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                    if(rs.next()) {
                     lab=rs.getString("val");
                    }
                    rs.close(); pst.close();
                    ary=new ArrayList();
                    
                     String labNme="LAB-"+lab;
                    String empIdqry="select nme_idn from nme_v where upper(nme) = upper('"+labNme+"')";

                    outLst = db.execSqlLst("empId", empIdqry, ary);
                    pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                    if(rs.next()) {
                     empIdn=rs.getInt(1);
                    }
                    rs.close(); pst.close();
                    params = new ArrayList();
                    params.add(Integer.toString(rcPrcIdn));
                    params.add(Integer.toString(empIdn));
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
                params.add(pktDtl.getValue("cts"));
                params.add(pktDtl.getValue("qty"));
                params.add("IS");
                String issuePkt = "ISS_RTN_PKG.ISS_PKT(pIssId => ?, pStkIdn => ? , pCts => ?,  pQty => ? , pStt => ?)";
                int ct = db.execCall("issuePkt", issuePkt, params);
                
                //Mayur
                int cnt = 0;
                String str = util.nvl((String)srchRcObList.get(stkIdn));
                if(!str.equals("")){
                ary = new ArrayList();
                String  updiss_rtn_prp = "update iss_rtn_prp set txt='"+str+"' where iss_stk_idn=? and iss_id=? and mprp= ?";
                String lprp="RCHK";
                ary.add(stkIdn);
                ary.add(String.valueOf(issueIdn));
                ary.add(lprp);
                cnt = db.execUpd("updateIssrtndtl", updiss_rtn_prp ,ary);
                    
                }
                
                  for(int j=0 ; j < issEditPrp.size() ;j++){  
                      String lprp = (String)issEditPrp.get(j);
                      String lprpVal = util.nvl((String)labForm.getValue(lprp+"_"+stkIdn));
                   if(!lprpVal.equals("0") && !lprpVal.equals("")){
                       params = new ArrayList();
                       params.add(String.valueOf(issueIdn));
                       params.add(stkIdn);
                       params.add(lprp);
                       params.add(lprpVal);
                       ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", params);
                       
                     params = new ArrayList();
                     params.add(stkIdn);
                     params.add(lprp);
                     params.add(lprpVal);
                     params.add(labIdn);
                     
                     String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?)";
                      ct = db.execCall("stockUpd",stockUpd, params);
                     
                   }}
                }
            
       }}
        if(issueIdn!=0){
        req.setAttribute( "msg","Requested packets get Issue with Issue Id "+issueIdn);
        req.setAttribute("url", info.getReqUrl()+"/excel/labxl?issueIdn="+issueIdn+"&lab="+lab);
        req.setAttribute("issueidn",String.valueOf(issueIdn));
        }
            GtMgrReset(req);
        labForm.resetAll();
            util.updAccessLog(req,res,"Lab Recheck", "Issue end");
     return am.findForward("load");
        }
    }
    
    public ActionForward viewRS(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
             util.updAccessLog(req,res,"Lab Recheck", "viewRS start");
             LabRecheckForm labForm = (LabRecheckForm)form;
             LabRecheckInterface labInt=new LabRecheckImpl();
        String mstkIdn = req.getParameter("mstkIdn");
        HashMap params = new HashMap();
        params.put("mstkIdn", mstkIdn);
        ArrayList stockPrpList = labInt.StockView(req,res, params);
        req.setAttribute("StockViewList", stockPrpList);
        req.setAttribute("mstkIdn", mstkIdn);
             util.updAccessLog(req,res,"Lab Recheck", "viewRS end");
        return am.findForward("loadView");
         }
     }
    
    public void GtMgrReset(HttpServletRequest req){
          HttpSession session = req.getSession(false);
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
          String lstNme = (String)gtMgr.getValue("lstNmeRCK");
          HashMap gtMgrMap = (HashMap)gtMgr.getValues();
          gtMgrMap.remove(lstNme);
           gtMgrMap.remove("LabIssueEditPRP");
           gtMgrMap.remove(lstNme+"_SEL");
           gtMgrMap.remove(lstNme+"_TTL");
           gtMgrMap.remove("lstNmeRCK");
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
                util.updAccessLog(req,res,"Screen Action", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Screen Action", "init");
            }
            }
            return rtnPg;
            }
     public LabRecheckAction() {
        super();
    }
}
