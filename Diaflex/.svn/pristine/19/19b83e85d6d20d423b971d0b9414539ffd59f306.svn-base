package ft.com.lab;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;
import ft.com.assort.AssortFinalRtnForm;
import ft.com.assort.AssortReturnForm;
import ft.com.dao.GtPktDtl;
import ft.com.dao.labDet;

import ft.com.generic.GenericImpl;

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

public class LabReturnAction extends DispatchAction{
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
              util.updAccessLog(req,res,"Manual Lab Return", "load start");
          LabReturnForm labRtnForm = (LabReturnForm)form;
          LabIssueInterface labIssueInt=new LabIssueImpl();
          labRtnForm.resetAll();
          ArrayList labList = labIssueInt.getLab(req,res);
          labRtnForm.setLabList(labList);
              util.updAccessLog(req,res,"Manual Lab Return", "load end");
          return am.findForward("load");
          }
      }
      public ActionForward fecth(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
              throws Exception {
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
               util.updAccessLog(req,res,"Manual Lab Return", "load start");
               LabReturnForm labRtnForm = (LabReturnForm)af;
               LabReturnInterface labRtnInt=new LabReturnImpl();
          String stoneId = util.nvl((String)labRtnForm.getValue("vnmLst"));
          String lab =  util.nvl((String)labRtnForm.getValue("lab"));
         
            HashMap params = new HashMap();
               params.put("stt", "'LB_IS','LB_RS','LB_CF','LB_RI'");
            params.put("vnm",stoneId);
            params.put("lab", lab);
           HashMap stockList = labRtnInt.FecthResult(req,res, params);
           
          String lstNme = "LABRTN_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
          gtMgr.setValue(lstNme+"_SEL",new ArrayList());
          gtMgr.setValue(lstNme, stockList);
          gtMgr.setValue("lstNmeLABRTN", lstNme);
         if(stockList.size()>0){
                HashMap dtlMap = new HashMap();
                ArrayList selectstkIdnLst = new ArrayList();
                Set<String> keys = stockList.keySet();
                       for(String key: keys){
                      selectstkIdnLst.add(key);
                       }
                dtlMap.put("selIdnLst",selectstkIdnLst);
                dtlMap.put("pktDtl", stockList);
                dtlMap.put("flg", "Z");
                HashMap ttlMap = util.getTTL(dtlMap);
                          
                gtMgr.setValue(lstNme+"_TTL", ttlMap);
            }
           req.setAttribute("view", "Y");
           
           int prcId = 0;
               ArrayList outLst = db.execSqlLst("mprcId", "select idn from mprc where is_stt='LB_IS' and stt='A'", new ArrayList());
               PreparedStatement pst = (PreparedStatement)outLst.get(0);
               ResultSet rs = (ResultSet)outLst.get(1);
           if(rs.next()) {
            prcId = rs.getInt(1);
           }
           rs.close(); pst.close();
           req.setAttribute("prcIdn", String.valueOf(prcId));
          
               util.updAccessLog(req,res,"Manual Lab Return", "load end");
           return am.findForward("load");
           }
       }
    public ActionForward stockUpd(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
             util.updAccessLog(req,res,"Manual Lab Return", "stockUpd start");
             LabReturnForm labRtnForm = (LabReturnForm)form;
             LabReturnInterface labRtnInt=new LabReturnImpl();
      
        String mstkIdn = util.nvl(req.getParameter("mstkIdn"));
        String prcIdn = util.nvl(req.getParameter("prcId"));
         String lastpage = util.nvl(req.getParameter("lastpage"));
         String currentpage = util.nvl(req.getParameter("currentpage"));
         String vnm="";
         if(!currentpage.equals("")){
                String lstNme = (String)gtMgr.getValue("lstNmeLABRTN");
              HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
             ArrayList stkIdnList = (ArrayList)gtMgr.getValue(lstNme+"_ALLLST"); 
             mstkIdn = (String)stkIdnList.get(Integer.parseInt(currentpage));
             GtPktDtl pktDtl = (GtPktDtl)stockList.get(mstkIdn);
             vnm = util.nvl(pktDtl.getVnm());
            }
        int issuId = labRtnInt.lstIssueId(req,res, mstkIdn);
        HashMap params = new HashMap();
         params.put("mstkIdn", mstkIdn);
         params.put("issueId", String.valueOf(issuId));
         params.put("prcId", prcIdn);
        ArrayList stockPrpList = labRtnInt.StockUpdPrp(req,res, labRtnForm , params);
        req.setAttribute("StockList", stockPrpList);
        labRtnForm.setValue("prcId", prcIdn);
        labRtnForm.setValue("issIdn", issuId);
        labRtnForm.setValue("mstkIdn", mstkIdn);
        labRtnForm.setValue("vnm", vnm);
        labRtnForm.setValue("lastpage", lastpage);
        labRtnForm.setValue("currentpage", currentpage);
        labRtnForm.setValue("url", "labReturn.do?method=stockUpd&prcId="+prcIdn);
        req.setAttribute("PopUpidn", mstkIdn);
             util.updAccessLog(req,res,"Manual Lab Return", "stockUpd end");
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
            util.updAccessLog(req,res,"Manual Lab Return", "savePrpUpd start");
            LabReturnForm labRtnForm = (LabReturnForm)form;
            LabReturnInterface labRtnInt=new LabReturnImpl();
        String prcId= (String)labRtnForm.getValue("prcId");
        String issId = (String)labRtnForm.getValue("issIdn");
        String mstkIdn = (String)labRtnForm.getValue("mstkIdn");
        String vnm="";
        String lastpage = util.nvl(req.getParameter("lastpage"));
        String currentpage = util.nvl(req.getParameter("currentpage"));
        if(lastpage.equals("") && currentpage.equals("")){
            lastpage = util.nvl((String)labRtnForm.getValue("lastpage"));
            currentpage = util.nvl((String)labRtnForm.getValue("currentpage"));
            vnm=util.nvl((String)labRtnForm.getValue("vnm"));
        }
        ArrayList assortPrpUpd = (ArrayList)session.getAttribute("assortUpdPrp");
        for(int i=0 ; i <assortPrpUpd.size();i++){
            
            String lprp = (String)assortPrpUpd.get(i);
            String fldVal =util.nvl((String)labRtnForm.getValue(lprp));
             if(!fldVal.equals("") && !fldVal.equals("0")){
            ArrayList ary = new ArrayList();
            ary.add(issId);
            ary.add(mstkIdn);
            ary.add(lprp);
            ary.add(fldVal);
            int ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
            System.out.println("pktUpd:---"+ct);
            }
            
        }
        
        HashMap params = new HashMap();
        params.put("prcId", prcId);
        params.put("issueId", issId);
        params.put("mstkIdn", mstkIdn);
        
        ArrayList stockPrpList = labRtnInt.StockUpdPrp(req,res, labRtnForm , params );
        req.setAttribute( "msg","Propeties Get update successfully");
        req.setAttribute("StockList", stockPrpList);
        req.setAttribute("PopUpidn", mstkIdn);
        labRtnForm.setValue("prcId", prcId);
        labRtnForm.setValue("vnm", vnm);
        labRtnForm.setValue("lastpage", lastpage);
        labRtnForm.setValue("currentpage", currentpage);
        labRtnForm.setValue("url", "labReturn.do?method=stockUpd&prcId="+prcId);
            util.updAccessLog(req,res,"Manual Lab Return", "savePrpUpd end");
        return am.findForward("loadStock");
        }
    }
    
    public ActionForward Return(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Manual Lab Return", "Return start");
            LabReturnForm labRtnForm = (LabReturnForm)form;
            LabReturnInterface labRtnInt=new LabReturnImpl();
            String lstNme = (String)gtMgr.getValue("lstNmeLABRTN");

        ArrayList RtnMsgList = new ArrayList();
        String msg = "";
        int cnt = 0;
       
            ArrayList selectList = (ArrayList)gtMgr.getValue(lstNme+"_SEL");

            if(selectList!=null && selectList.size()>0) {
                for(int i=0;i<selectList.size();i++){
              String stkIdn = (String)selectList.get(i);
           
                int issuId = labRtnInt.lstIssueId(req,res, stkIdn);
               ArrayList  params = new ArrayList();
                params.add(String.valueOf(issuId));
                params.add(stkIdn);
                
                ArrayList out = new ArrayList();
                out.add("I");
                out.add("V");
                String issuePkt = "ISS_RTN_PKG.MNL_LAB_RTN(pIssId => ?, pStkIdn => ? , pCnt=>? , pMsg => ? )";
                CallableStatement ct = db.execCall("issue Rtn", issuePkt, params, out);
                 cnt = ct.getInt(params.size()+1);
                 msg = ct.getString(params.size()+2);
               ArrayList RtnMsg = new ArrayList();
                RtnMsg.add(cnt);
                RtnMsg.add(msg);
                RtnMsgList.add(RtnMsg);
                ct.close();
            }
       }
    if(RtnMsgList.size()>0)
        req.setAttribute("msgList",RtnMsgList);
            util.updAccessLog(req,res,"Manual Lab Return", "Return end");
     return fecth(am, form, req, res);
        }
    }
    public LabReturnAction() {
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
                util.updAccessLog(req,res,"Manual Lab Return", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Manual Lab Return", "init");
            }
            }
            return rtnPg;
            }
}
