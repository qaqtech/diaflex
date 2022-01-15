package ft.com.lab;

import java.io.InputStream;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.ExcelUtilObj;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.GtPktDtl;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.IOException;

import java.io.OutputStream;

import java.net.URL;

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

public class AutoLabIssueAction  extends DispatchAction{
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
            util.updAccessLog(req,res,"Lab Issue", "load start");
    LabIssueForm labForm = (LabIssueForm)form;
    LabIssueInterface labInt=new LabIssueImpl();
    GenericInterface genericInt = new GenericImpl();
    labForm.resetAll();
    HashMap prp = info.getPrp();
    ArrayList labList = labInt.getLab(req,res);
    ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_lbAUISGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_lbAUISGNCSrch"); 
    info.setGncPrpLst(assortSrchList);
    labForm.setLabList(labList);
    ArrayList prpSrt = (ArrayList)prp.get("LAB_PRCS");
    ArrayList prpVal = (ArrayList)prp.get("LAB_PRCV");
    String srt = (String)prpSrt.get(prpVal.indexOf("GIA"));
    labForm.setValue("LAB_PRC_1", srt);
        util.AssortViewLyt();
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("LAB_ISSUE");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("LAB_ISSUE");
        allPageDtl.put("LAB_ISSUE",pageDtl);
        }
    ArrayList mprcList = labInt.getPrc(req, res);
          labForm.setMprcList(mprcList);  
        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Lab Issue", "load end");
    return am.findForward("load");
        }
    }

//    public void LABLinkViewLyt(){
//    ArrayList  dsc=new ArrayList();
//    String gtView = "select chr_fr,dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + 
//                    " b.mdl = 'JFLEX' and b.nme_rule = 'Assort_Lab_View' and a.til_dte is null order by a.srt_fr ";
//    ResultSet rs = db.execSql("gtView", gtView, new ArrayList());
//    try {
//       while(rs.next()) {
//            String prp = util.nvl(rs.getString("dsc"));
//            dsc.add(prp);
//        }
//        rs.close(); pst.close();
//    } catch (SQLException sqle) {
//        // TODO: Add catch code
//        sqle.printStackTrace();
//    }
//
//    info.setAssortViewMap(dsc);
//    }
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
            util.updAccessLog(req,res,"Lab Issue", "fetch start");
            LabIssueForm labForm = (LabIssueForm)form;
            LabIssueInterface labInt=new LabIssueImpl();
    boolean isGencSrch = false;
    HashMap stockList = new HashMap();
    ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_lbAUISGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_lbAUISGNCSrch"); 
    info.setGncPrpLst(genricSrchLst);
    //      ArrayList genricSrchLst = info.getGncPrpLst();
    HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
    String vnm = util.nvl((String)labForm.getValue("vnmLst"));
  
    HashMap paramsMap = new HashMap();
    if(vnm.equals("")){
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
        paramsMap.put("stt", "LB_AV");
        paramsMap.put("mdl", "LAB_VIEW");
        isGencSrch = true;
        util.genericSrch(paramsMap);
        stockList = labInt.SearchResultGT(req,res,"", labForm,"AUTO");
    }else{
        stockList = labInt.insertgt(req,res,labForm,"AUTO");
    }
    
//    if(!vnm.equals(""))
//        labInt.delGt(req,res,vnm);
//    stockList = labInt.SearchResult(req,res,"", labForm);
//    
         String lstNme = "LABRST_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
    gtMgr.setValue(lstNme+"_SEL",new ArrayList());
    gtMgr.setValue(lstNme, stockList);
    gtMgr.setValue("lstNmeLABAU", lstNme);
    if(stockList.size()>0){
    HashMap totals = labInt.GetTotalNew(req,res);
    gtMgr.setValue(lstNme+"_TTL", totals);
    ArrayList errorList = labInt.validateMapping(req,res);
    ArrayList serviceList = labInt.getService(req,res);
    labForm.setServiceList(serviceList);
    labInt.LabIssueEdit(req, res,"LB_AU_IS");
        req.setAttribute("MappingMiss", errorList);
    }
    req.setAttribute("view", "Y");
   


    ArrayList labList = labInt.getLab(req,res);
    labForm.setLabList(labList);

    labForm.setView("");
    labForm.setViewAll("");
            util.updAccessLog(req,res,"Lab Issue", "fetch end");
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
          util.updAccessLog(req,res,"Lab Issue", "Issue start");
          LabIssueForm labForm = (LabIssueForm)form;
         
        HashMap dbInfoSys = info.getDmbsInfoLst();
          String giaServc = util.nvl((String)dbInfoSys.get("GIASERVC"));
     boolean isDone = false;
      ArrayList params = null;
      int issueIdn = 0;
      int empId=0;
      int prcid=0;
      String labIdn= util.nvl((String)labForm.getValue("labIdn"));
     
          String lstNme = (String)gtMgr.getValue("lstNmeLABAU");
          ArrayList selectstkIdnLst = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
          HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
          ArrayList issEditPrp = (ArrayList)gtMgr.getValue("LabIssueEditPRP");
          
      String labNme="LAB-"+labIdn;
     HashMap prpLst = info.getPrp();
      ArrayList ary=new ArrayList();
      ary.add(labNme);
      String empIdqry="select nme_idn from nme_v where  upper(nme) = upper('"+labNme+"')";

          ArrayList outLst = db.execSqlLst("empId", empIdqry, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
      if(rs.next()) {
        empId=rs.getInt(1);
      }
      rs.close(); pst.close();
      
        prcid = Integer.parseInt((String)labForm.getValue("mprcIdn"));
      
      rs.close(); pst.close();
      int ct =0;
          for(int i=0;i< selectstkIdnLst.size();i++){
              String stkIdn = (String)selectstkIdnLst.get(i);
              GtPktDtl stockPkt = (GtPktDtl)stockList.get(stkIdn);
             
               String isChecked = util.nvl((String)labForm.getValue(stkIdn));
              if(isChecked.equals("yes")){
                  if(issueIdn==0){
                      params = new ArrayList();
                      params.add(Integer.toString(prcid));
                      params.add(Integer.toString(empId));
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
                  params.add(stockPkt.getValue("cts"));
                  params.add(stockPkt.getValue("qty"));
                  params.add("IS");
                  String issuePkt = "ISS_RTN_PKG.ISS_PKT(pIssId => ?, pStkIdn => ? , pCts => ?,  pQty => ? , pStt => ?)";
                   ct = db.execCall("issuePkt", issuePkt, params);
                  if(ct>0){
                       isDone = true;
                 for(int j=0 ; j < issEditPrp.size() ;j++){  
                     String lprp = (String)issEditPrp.get(j);
                     String lprpVal = util.nvl((String)labForm.getValue(lprp+"_"+stkIdn));
                     if(lprpVal.equals("0") || lprpVal.equals("") )
                         lprpVal="NA";
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
                     
                     if(lprp.equals("SERVICE")){
                        ArrayList serPrpList=(ArrayList)prpLst.get("SERVICEP2");
                         ArrayList serValList=(ArrayList)prpLst.get("SERVICEV");
                         String serVal = (String)serPrpList.get(serValList.indexOf(lprpVal));
                         params = new ArrayList();
                         params.add(String.valueOf(issueIdn));
                         params.add(stkIdn);
                         params.add("SERVICE_CODE");
                         params.add(serVal);
                         ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", params);
                         
                     }
                    
                  }}
                  
                  }
          }
         
      if(issueIdn!=0){
          if(isDone){
      
              if(labIdn.equals("GIA") && giaServc.equalsIgnoreCase("YES")){
            String usrNme = (String)dbInfoSys.get("GIA_USER_NAME");
            String clientId = (String)dbInfoSys.get("GIA_CLIENT_ID");
            String giaSerUrl = (String)dbInfoSys.get("GIASERURL");
            String url=giaSerUrl+"/GIAWebClient?issID="+issueIdn+"&userNme="+usrNme+"&clientId="+clientId+"&DS="+info.getDbTyp();
              System.out.println(url);
               URL u = new URL(url);
               InputStream is = u.openStream();
               
            String issRtn = "select res_stt ,res_err,res_msg from iss_rtn where iss_id=?";
            params = new ArrayList();
            params.add(String.valueOf(issueIdn));
                  outLst = db.execSqlLst("issRtn", issRtn, params);
                  pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
            if(rs.next()){ 
              String res_stt = util.nvl(rs.getString("res_stt"));
              String res_err = util.nvl(rs.getString("res_err"));
              String res_msg = util.nvl(rs.getString("res_msg"));
              if(res_stt.equals("SUCCESS")){
                req.setAttribute( "msg","Requested packets get Issued with Issue Id "+issueIdn+" ,  Job No:-"+res_msg);
                req.setAttribute("issueIdn",String.valueOf(issueIdn));
                req.setAttribute("Lab",labIdn);
               
              }else{
                params = new ArrayList();
                params.add(String.valueOf(issueIdn));
                ct = db.execCall("ISS_RTN_PKG", "ISS_RTN_PKG.ISS_CNCL(pIssId=>?)", params);
                 
                req.setAttribute("Errmsg","Issue ID"+issueIdn+":"+res_err+" "+res_msg); 
              }
              }
                  rs.close(); pst.close();
              }else{
                req.setAttribute( "msg","Requested packets get Issued with Issue Id "+issueIdn);
                req.setAttribute("issueIdn",String.valueOf(issueIdn));
                req.setAttribute("Lab",labIdn);
              }
    
          }else{
              req.setAttribute( "Errmsg","Error in process..");    
          }
      }
   labForm.reset();
          ArrayList prpSrt = (ArrayList)prpLst.get("LAB_PRCS");
          ArrayList prpVal = (ArrayList)prpLst.get("LAB_PRCV");
          String srt = (String)prpSrt.get(prpVal.indexOf("GIA"));
          labForm.setValue("LAB_PRC_1", srt);
   int accessidn=util.updAccessLog(req,res,"Lab Issue", "Issue end");
          GtMgrReset(req);
   req.setAttribute("accessidn", String.valueOf(accessidn));;
   return am.findForward("load");
      }
  }
    
    public ActionForward pktPrint(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Lab Issue", "pktPrint start");
              String issueIdn = util.nvl(req.getParameter("issueIdn"));
              String accessidn = util.nvl(req.getParameter("accessidn"));
               String delQ = " Delete from mkt_prc ";
               int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
              ArrayList ary = new ArrayList();
              HashMap dbinfo = info.getDmbsInfoLst();
              String cnt = (String)dbinfo.get("CNT");
              String webUrl = (String)dbinfo.get("REP_URL");
              String reqUrl = (String)dbinfo.get("HOME_DIR");
              String repPath = (String)dbinfo.get("REP_PATH");
              String repNme = "pktprint_prc.rdf";
        
              int mkt_prc = 0;

            ArrayList outLst = db.execSqlLst("mkt_prc", "select  seq_mkt_prc.nextval from dual ", new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
              if(rs.next())
                  mkt_prc = rs.getInt(1);
              rs.close(); pst.close();
              String insertPrc = " insert into mkt_prc(prc_idn ,mstk_idn ,rep_dte) "+
                  " select ? , iss_stk_idn , sysdate from iss_rtn_dtl where iss_id=? and stt=? ";
              ary.add(String.valueOf(mkt_prc));
              ary.add(issueIdn);
              ary.add("IS");
               ct = db.execUpd("insert mkt_prc", insertPrc, ary);
              if(ct>0){
                 String url = repPath+"/reports/rwservlet?"+cnt+"&report="+reqUrl+"\\reports\\"+repNme+"&p_access="+accessidn ;
                  res.sendRedirect(url);    
              }
            util.updAccessLog(req,res,"Lab Issue", "pktPrint end");
            return null;
        }
    }
    public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Lab Issue", "createXL start");
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "LabIssueExcel"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
            ExcelUtilObj excelUtil = new ExcelUtilObj();
            excelUtil.init(db, util, info,gtMgr);
            HSSFWorkbook hwb = new HSSFWorkbook();
            String lstNme = (String)gtMgr.getValue("lstNmeLABAU");
            ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
            HashMap excelDtl = new HashMap();
            excelDtl.put("HDR", itemHdr);
            excelDtl.put("lstNme", lstNme);
            excelDtl.put("MDL", "LB_AV");
            excelDtl.put("EXHRTE", String.valueOf(info.getXrt()));
            hwb = excelUtil.getGenXlObj(excelDtl, req);
            OutputStream out = res.getOutputStream();
            hwb.write(out);
            out.flush();
            out.close();
            util.updAccessLog(req,res,"Lab Issue", "createXL end");
            return null;
        }
    }
    public void GtMgrReset(HttpServletRequest req){
          HttpSession session = req.getSession(false);
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
          String lstNme = (String)gtMgr.getValue("lstNmeLABAU");
          HashMap gtMgrMap = (HashMap)gtMgr.getValues();
          gtMgrMap.remove(lstNme);
           gtMgrMap.remove("LabIssueEditPRP");
           gtMgrMap.remove(lstNme+"_SEL");
           gtMgrMap.remove(lstNme+"_TTL");
           gtMgrMap.remove("lstNmeLABAU");
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
                util.updAccessLog(req,res,"Lab Issue", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Lab Issue", "init");
            }
            }
            return rtnPg;
            }
  public AutoLabIssueAction() {
      super();
  }
}

   

