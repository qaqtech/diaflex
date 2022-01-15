package ft.com.assort;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.lab.LabSelectionForm;

import java.io.IOException;

import java.io.OutputStream;

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

public class AssortSelectionAction extends DispatchAction {
    
  
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
          AssortSelectionForm  assortForm = (AssortSelectionForm)form;
         GenericInterface genericInt = new GenericImpl();
        util.updAccessLog(req,res,"Assort Selection", "load");
       String stt=util.nvl(req.getParameter("STT"),"MF_FL");
          if(stt.equals(""))
              stt="MF_FL";
        assortForm.resetALL();
          assortForm.setValue("stt", stt);
        ArrayList assortSrchList =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_asGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_asGNCSrch");
        info.setGncPrpLst(assortSrchList);
        util.updAccessLog(req,res,"Assort Selection", "AS_SRCH "+assortSrchList.size());
        util.updAccessLog(req,res,"Assort Selection", "End");
        return am.findForward("load");
        }
    }
    public ActionForward save(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          AssortSelectionForm  assortForm = (AssortSelectionForm)form;
        
          util.updAccessLog(req,res,"Assort Selection", "load");
       
        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
       HashMap dbinfo = info.getDmbsInfoLst();
       String cnt = (String)dbinfo.get("CNT");
        int ct=0;
        int count =0;
        String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => 'RECPT_DT', pVal => to_char(sysdate, 'dd-MON-rrrr'), pGrp => 1, pPrpTyp => 'D')";
        ArrayList ary = new ArrayList();
        for(int i=0;i< stockList.size();i++){
            HashMap stockPkt = (HashMap)stockList.get(i);
             String stkIdn = (String)stockPkt.get("stk_idn");
             String isChecked   = util.nvl((String)assortForm.getValue(stkIdn));
             String stt   = util.nvl((String)assortForm.getValue("stt_"+stkIdn));
            if(isChecked.length() > 0){
                String updateMstk = "update mstk set stt=? where idn= ? ";
                ary = new ArrayList();
                ary.add(stt);
                ary.add(stkIdn);
                ct = db.execUpd("update", updateMstk, ary);
                if(ct==1){
                    count++;
                if(cnt.equals("kj")){
                ary = new ArrayList();
                ary.add(stkIdn);
                ct = db.execCall("stockUpd",stockUpd, ary);
                }
                if(cnt.equals("pm") || cnt.equals("asha")){
                    ary = new ArrayList();
                    ary.add(stkIdn);
                    ct = db.execUpd("update", "update mstk a set a.stt='SUADV' where idn=? and exists (select 1 from stk_dtl b where a.idn=b.mstk_idn and b.grp=1 and b.mprp='STT_TY' and b.val='ADVISE')", ary);
                }
                }
            }
    
        }
        if(count > 0)
            req.setAttribute("msg", "Verification Done For "+count+" Stones.");
            util.updAccessLog(req,res,"Assort Selection", "stockList "+stockList.size());
            util.updAccessLog(req,res,"Assort Selection", "End");
       return  load(am, form, req, res);
        }
    }
    public ActionForward view(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          AssortSelectionForm  assortForm = (AssortSelectionForm)form;
          AssortSelectionInterface assortInt = new AssortSelectionImpl();
            util.updAccessLog(req,res,"Assort Selection", "load");
        db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
    
        boolean isGencSrch = false;
      
        ArrayList stockList = new ArrayList();
        String view =util.nvl(assortForm.getView());
        ArrayList genricSrchLst =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_asGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_asGNCSrch");
        info.setGncPrpLst(genricSrchLst);
//      ArrayList genricSrchLst = info.getGncPrpLst();
        HashMap params = new HashMap();
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        if(view.length() > 0){
        String vnmLst = util.nvl((String)assortForm.getValue("vnmLst"));
        if(!vnmLst.equals("")){
            vnmLst = util.getVnm(vnmLst);
            params.put("vnm", vnmLst);
        }else{
            String stt = util.nvl((String)assortForm.getValue("stt"));
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
              paramsMap.put("stt", stt);
              paramsMap.put("mdl", "AS_VIEW");
            isGencSrch = true; 
            util.genericSrch(paramsMap);
            stockList = assortInt.SearchResult(req,res, "",assortForm);
            
        }
          
        }
        if(!isGencSrch)
        stockList = assortInt.FetchResult(req ,res , params,assortForm);
        if(stockList.size()>0){
        HashMap totals = assortInt.GetTotal(req , res,"Z");
        req.setAttribute("totalMap", totals);
        }
        req.setAttribute("view", "Y");
        session.setAttribute("StockList", stockList);
        ArrayList vwPrpList = assortInt.ASPrprViw(req,res);
        assortForm.setView("");
        assortForm.setViewAll("");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("ASSORT_VERTION");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("ASSORT_VERTION");
        allPageDtl.put("ASSORT_VERTION",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Assort Selection", "End");
        return am.findForward("load");
        }
    }
    public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        
            util.updAccessLog(req,res,"Assort Selection", "load");
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "AssortVerification"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        HSSFWorkbook hwb = null;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        hwb = xlUtil.getInXl("Assort", req, "AS_VIEW");
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Assort Selection", "End");
        return null;
        }
    }
    public AssortSelectionAction() {
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
