package ft.com.assort;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.dao.Mprc;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AssortIssueAction extends DispatchAction {
    
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
          util.getOpenCursorConnection(db,util,info);
          AssortIssueForm assortForm = (AssortIssueForm)form;
          AssortIssueInterface assortInt = new AssortIssueImpl();
          GenericInterface genericInt = new GenericImpl();
        util.updAccessLog(req,res,"Assort Issue", "load");
      
        assortForm.reset();
        ArrayList mprcList = assortInt.getPrc(req,res);
        assortForm.setMprcList(mprcList);
        
        ArrayList empList = assortInt.getEmp(req,res);
        assortForm.setEmpList(empList);
        
        ArrayList assortSrchList =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_asGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_asGNCSrch"); 
        info.setGncPrpLst(assortSrchList);
        util.updAccessLog(req,res,"Assort Issue", "End");
      return am.findForward("load");
        }
    }
    
    public ActionForward fecth(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

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
          AssortIssueForm assortForm = (AssortIssueForm)form;
          AssortIssueInterface assortInt = new AssortIssueImpl();
         
            util.updAccessLog(req,res,"Assort Issue", "fecth");
       
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        String mprcIdn = util.nvl((String)assortForm.getValue("mprcIdn"));
        String stoneId = util.nvl((String)assortForm.getValue("vnmLst"));
        String empId = util.nvl((String)assortForm.getValue("empIdn"));
        String issueIdn = util.nvl((String)assortForm.getValue("issueIdn"));
        ArrayList genricSrchLst =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_asGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_asGNCSrch");
        info.setGncPrpLst(genricSrchLst);
//      ArrayList genricSrchLst = info.getGncPrpLst();
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        HashMap prcBeansList = (HashMap)session.getAttribute("mprcBean");
        if(prcBeansList!=null){
              assortInt.getPrc(req, res);
         }
        Mprc mprcDto = (Mprc)prcBeansList.get(mprcIdn);
        String prcStt = mprcDto.getIn_stt();
        String isStt = mprcDto.getIs_stt();
        String grp = mprcDto.getGrp();
        HashMap params = new HashMap();
        params.put("stt", prcStt);
        params.put("vnm",stoneId);
        params.put("empIdn", empId);
        params.put("mprcIdn", mprcIdn);
        params.put("issueIdn", issueIdn);
        params.put("grp", grp);
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
                String reqVal1 = util.nvl((String)assortForm.getValue(lprp + "_" + lVal),"");
                if(!reqVal1.equals("")){
                paramsMap.put(lprp + "_" + lVal, reqVal1);
                }
                   
                }
            }else{
            String fldVal1 = util.nvl((String)assortForm.getValue(lprp+"_1"));
            String fldVal2 = util.nvl((String)assortForm.getValue(lprp+"_2"));
            if(typ.equals("T")){
                fldVal1 = util.nvl((String)assortForm.getValue(lprp+"_1"));
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
        HashMap stockList = null;
        if(paramsMap.size()>0){
        paramsMap.put("stt", prcStt);
        paramsMap.put("mdl", "AS_VIEW");
       if(grp.equals("BPRC")) 
           paramsMap.put("MIX", "Y");
        util.genericSrch(paramsMap);
        String prc_iss = "ISS_RTN_PKG.PRC_ISS_PKTS(pPrcId => ? , pEmpId => ?, pFlg => ?,pMdl=> ?)";
        ArrayList ary = new ArrayList();
        ary.add(mprcIdn);
        ary.add(empId);
        ary.add("VNM");
        ary.add("AS_VIEW");
         ct = db.execCall(" prc issue ", prc_iss, ary);
         util.updAccessLog(req,res,"Assort Issue", prcStt);
       stockList = assortInt.SearchResult(req,res, "");
        }else{
        util.updAccessLog(req,res,"Assort Issue", "Manual");
        stockList = assortInt.FecthResult(req,res, params);
        }
        if(stockList.size()>0){
        HashMap totals = assortInt.GetTotal(req,res);
        req.setAttribute("totalMap", totals);
        assortInt.IssueEdit(req, res,isStt);
        }
        util.updAccessLog(req,res,"Assort Issue", "stockList : "+stockList.size());
        req.setAttribute("view", "Y");
        assortForm.setValue("prcId", mprcIdn);
        assortForm.setValue("empId", empId);
        String lstNme = "ASIS_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
        gtMgr.setValue(lstNme, stockList);
        req.setAttribute("lstNme", lstNme);
        
            util.updAccessLog(req,res,"Assort Issue", "End");
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
          ArrayList stkIdnList = new ArrayList();
          AssortIssueForm assortForm = (AssortIssueForm)form;
          Enumeration reqNme = req.getParameterNames();
          String stkIdnstr="";
           while (reqNme.hasMoreElements()) {
              String paramNm = (String) reqNme.nextElement();
              if (paramNm.indexOf("cb_stk") > -1) {
                  String stkIdn = req.getParameter(paramNm);
                  stkIdnstr = stkIdnstr+","+stkIdn;
                  stkIdnList.add(stkIdn);
              }
            }
          if(!stkIdnstr.equals(""))
              stkIdnstr = stkIdnstr.replaceFirst(",", "");
          int ct=0;
            String delQ = " Delete from gt_srch_rslt ";
            ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            
            util.updAccessLog(req,res,"Assort Issue", "Issue");
        assortForm = (AssortIssueForm)form;
        String prcId = util.nvl((String)assortForm.getValue("prcId"),"0");
        String empId = (String)assortForm.getValue("empId");
          String lstNme = util.nvl((String)assortForm.getValue("lstNme"));

        ArrayList params = null;
        int issueIdn = 0;
            HashMap dbinfo = info.getDmbsInfoLst();

            int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));

        
            String[] vnmLst = stkIdnstr.split(",");
            int loopCnt = 1 ;
            float loops = ((float)vnmLst.length)/stepCnt;
            loopCnt = Math.round(loops);
               if(vnmLst.length <= stepCnt || new Float(loopCnt)>=loops) {
                
            } else
                loopCnt += 1 ;
            if(loopCnt==0)
                loopCnt += 1 ;
            int fromLoc = 0 ;
            int toLoc = 0 ;
            for(int i=1; i <= loopCnt; i++) {
                
              int aryLoc = Math.min(i*stepCnt, vnmLst.length) ;
              
              String lookupVnm = vnmLst[aryLoc-1];
                   if(i == 1)
                       fromLoc = 0 ;
                   else
                       fromLoc = toLoc+1;
                   
                   toLoc = Math.min(stkIdnstr.lastIndexOf(lookupVnm) + lookupVnm.length(), stkIdnstr.length());
                   String vnmSub = stkIdnstr.substring(fromLoc, toLoc);
                
                vnmSub=vnmSub.toUpperCase();
                vnmSub= vnmSub.replaceAll(" ", "");
                String insScanPkt = " insert into gt_srch_rslt(stk_idn,flg) select vnm ,'S' from TABLE(PARSE_TO_TBL('"+vnmSub+"'))";
                  ct = db.execDirUpd(" ins scan", insScanPkt,new ArrayList());
                
            }
            
        if(ct>0 && !prcId.equals("0")){
        params = new ArrayList();
        params.add(prcId);
        params.add(empId);
        ArrayList out = new ArrayList();
        out.add("I");
        CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", params ,out);
        issueIdn = cst.getInt(3);
          cst.close();
          cst=null;
            util.updAccessLog(req,res,"Assort Issue", "issueIdn" +issueIdn);
         params = new ArrayList();
         params.add(String.valueOf(issueIdn));
         params.add("1");
         params.add("IS");
         String issuePkt = "ISS_RTN_PKG.ALL_ISS_PKT(pIssId =>?, pGrp => ?, pStt => ?)";
         ct = db.execCall("issuePkt", issuePkt, params);
        }
        
            ArrayList issEditPrp = (ArrayList)gtMgr.getValue("AssortIssueEditPRP");
          if(issEditPrp!=null && issEditPrp.size()>0 && issueIdn>0){
              for(int i=0;i<stkIdnList.size();i++){
              String stkIdn = (String)stkIdnList.get(i);
                  for(int j=0 ; j < issEditPrp.size() ;j++){  
                      String lprp = (String)issEditPrp.get(j);
                      String lprpVal = util.nvl((String)assortForm.getValue(lprp+"_"+stkIdn));
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
                    
                     
                     String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                      ct = db.execCall("stockUpd",stockUpd, params);
                     
                   }}
              }
          }
          
//        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
//        for(int i=0;i< stockList.size();i++){
//            HashMap stockPkt = (HashMap)stockList.get(i);
//             String stkIdn = (String)stockPkt.get("stk_idn");
//             String isChecked = util.nvl((String)assortForm.getValue(stkIdn));
//            if(isChecked.equals("yes")){
//                if(issueIdn==0){
//                    params = new ArrayList();
//                    params.add(prcId);
//                    params.add(empId);
//                    ArrayList out = new ArrayList();
//                    out.add("I");
//                    CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", params ,out);
//                    issueIdn = cst.getInt(3);
//                    cst.close();
//                }
//                params = new ArrayList();
//                params.add(String.valueOf(issueIdn));
//                params.add(stkIdn);
//                params.add(stockPkt.get("cts"));
//                params.add(stockPkt.get("qty"));
//                params.add("IS");
//                String issuePkt = "ISS_RTN_PKG.ISS_PKT(pIssId => ?, pStkIdn => ? , pCts => ?,  pQty => ? , pStt => ?)";
//                int ct = db.execCall("issuePkt", issuePkt, params);
//                }
//       }
        if(ct > 0){
        req.setAttribute( "msg","Requested packets get Issue with Issue Id "+issueIdn);
        req.setAttribute( "issueidn",String.valueOf(issueIdn));
        }else{
            req.setAttribute( "msg","Error in Issue process"); 
        }
          gtMgr.setValue(lstNme, new HashMap());

     assortForm.reset();
        int accessidn=util.updAccessLog(req,res,"Assort Issue", "End");
        req.setAttribute("accessidn", String.valueOf(accessidn));;
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
       
    public AssortIssueAction() {
        super();
    }
}
