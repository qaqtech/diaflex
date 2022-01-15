package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.assort.AssortIssueForm;

import ft.com.dao.Mprc;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class MixIssueAction extends DispatchAction{
 
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
       util.updAccessLog(req,res,"Mix issue", "load");
    MixIssueForm mixForm = (MixIssueForm)form;
    MixIssueInterface mixInt = new MixIssueImpl();
    GenericInterface genericInt = new GenericImpl();
    mixForm.resetAll();
         ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXISGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXISGNCSrch");
    info.setGncPrpLst(assortSrchList);
    ArrayList empList = mixInt.getEmp(req,res);
    mixForm.setEmpList(empList);
    ArrayList prcList = mixInt.getPrc(req, res);
    mixForm.setMprcList(prcList);
       util.updAccessLog(req,res,"Mix issue", "load end");
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
          util.updAccessLog(req,res,"Mix issue", "fetch");
      MixIssueForm mixForm = (MixIssueForm)form;
      MixIssueInterface mixInt = new MixIssueImpl();
      GenericInterface genericInt = new GenericImpl();
        long lStartTime = new Date().getTime();
        
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
      
        String stoneId = util.nvl((String)mixForm.getValue("vnmLst"));
        String empId = util.nvl((String)mixForm.getValue("empIdn"));
        String mprcIdn = util.nvl((String)mixForm.getValue("mprcIdn"));
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXISGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXISGNCSrch");
        info.setGncPrpLst(genricSrchLst);
        HashMap mprp = info.getMprp();
        String inStt="";
        String otStt ="";
        String isStt="";
        ArrayList ary = new ArrayList();
        ary.add(mprcIdn);
          ArrayList  rsLst = db.execSqlLst("mprcId", "select idn , in_stt , ot_stt , is_stt from mprc where idn=? and stt='A'", ary);

          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
        if(rs.next()) {
         mprcIdn = util.nvl(rs.getString("idn"));
         inStt = util.nvl(rs.getString("in_stt"));
         otStt = util.nvl(rs.getString("ot_stt"));
         isStt = util.nvl(rs.getString("is_stt"));
        }
        rs.close();
          stmt.close();
        HashMap params = new HashMap();
        params.put("stt", inStt);
        params.put("vnm",stoneId);
        params.put("empIdn", empId);
        params.put("mprcIdn", mprcIdn);
        HashMap paramsMap = new HashMap();
        for(int i=0;i<genricSrchLst.size();i++){
            ArrayList prplist =(ArrayList)genricSrchLst.get(i);
            String lprp = (String)prplist.get(0);
            String typ = util.nvl((String)mprp.get(lprp+"T"));
            String fldVal1 = util.nvl((String)mixForm.getValue(lprp+"_1"));
            String fldVal2 = util.nvl((String)mixForm.getValue(lprp+"_2"));
            if(typ.equals("T")){
                fldVal1 = util.nvl((String)mixForm.getValue(lprp+"_1"));
                fldVal2 = fldVal1;
            }
            if(fldVal2.equals(""))
            fldVal2=fldVal1;
            if(!fldVal1.equals("") && !fldVal2.equals("")){
                paramsMap.put(lprp+"_1", fldVal1);
                paramsMap.put(lprp+"_2", fldVal2);
            }
        
        }
        ArrayList stockList = null;
        if(paramsMap.size()>0){
        paramsMap.put("stt", inStt);
        paramsMap.put("mdl", "MIX_VIEW");
        paramsMap.put("MIX","Y");
        util.genericSrch(paramsMap);
        stockList = mixInt.SearchResult(req,res, "");
        }else{
        stockList = mixInt.FecthResult(req,res, params);
        }
        if(stockList.size()>0){
        HashMap totals = mixInt.GetTotal(req,res);
        req.setAttribute("totalMap", totals);
        }
        req.setAttribute("view", "Y");
        mixForm.setValue("prcId", mprcIdn);
        mixForm.setValue("empId", empId);
        session.setAttribute("StockList", stockList);
            long lEndTime = new Date().getTime();
            long difference = lEndTime - lStartTime;
            System.out.println(lStartTime);
            System.out.println(lEndTime);
            System.out.println(difference);
            System.out.println(util.convertMillis(difference));
          util.updAccessLog(req,res,"Mix issue", "fetch end");
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
      MixIssueForm mixForm = (MixIssueForm)form;
          util.updAccessLog(req,res,"Mix issue", "issue");
        String empId = (String)mixForm.getValue("empId");
        String prcId = (String)mixForm.getValue("prcId");
        String inStt = "";
        ArrayList  params = new ArrayList();
        params.add(prcId);
          ArrayList  rsLst = db.execSqlLst("gt fetch", "select in_stt from mprc where idn=?" , params);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
        while(rs.next()){
              inStt = rs.getString("in_stt"); 
        }
        rs.close();  
          stmt.close();
        
        int issueIdn = 0;
        ArrayList vwPrpLst = (ArrayList)session.getAttribute("MixViewLst");
        int shInx = vwPrpLst.indexOf("SHAPE")+1;
        String fldSh = "prp_00"+shInx;
        if(shInx > 9)
         fldSh = "prp_0"+shInx;
        int memoInx = vwPrpLst.indexOf("MEMO")+1;
        String fldMemo ="prp_00"+memoInx;
        if(memoInx > 9)
            fldMemo ="prp_0"+memoInx;
        int sizeInx = vwPrpLst.indexOf("MIX_SIZE")+1;
        String fldSize ="prp_00"+sizeInx;
        if(sizeInx > 9)
            fldSize ="prp_0"+sizeInx;
        String issStr="";
        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
        for(int i=0;i< stockList.size();i++){
            HashMap stockPkt = (HashMap)stockList.get(i);
            String memo = (String)stockPkt.get("memo");
            String sh = (String)stockPkt.get("sh");
            String size = (String)stockPkt.get("size");
            String fldKey = memo+"_"+sh+"_"+size;
            String isChecked = util.nvl((String)mixForm.getValue(fldKey));
            if(isChecked.equals("yes")){
                
                 rsLst = db.execSqlLst("gt fetch", "select stk_idn , qty , cts from gt_srch_rslt where "+fldSh+"='"+sh+"' and "+fldMemo+"='"+memo+"' and "+fldSize+"='"+size+"' and stt='"+inStt+"'" , new ArrayList());
                    stmt =(PreparedStatement)rsLst.get(0);
                  rs =(ResultSet)rsLst.get(1);
                while(rs.next()){
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
                params.add(util.nvl(rs.getString("stk_idn")));
                String issuePkt = "MIX_ISS_RTN.ISS_PKT(pIssId => ?, pStkIdn => ?)";
                int ct = db.execCall("issuePkt", issuePkt, params);
               
                }
                    issStr=issStr+","+issueIdn;
                    rs.close();
                    stmt.close();
                }
         
       }
        if(!issStr.equals("")){
        req.setAttribute( "msg","Requested packets get Issue with Issue Id "+issStr);
        req.setAttribute( "issueidn",issStr);
        }
    
     mixForm.reset();
          util.updAccessLog(req,res,"Mix issue", "issue end");
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
          util.updAccessLog(req,res,"Mix Issue", "init");
          }
          }
          return rtnPg;
          }
     public MixIssueAction() {
        super();
    }
}
