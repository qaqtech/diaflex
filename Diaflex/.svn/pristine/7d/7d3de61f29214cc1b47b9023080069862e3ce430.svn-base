package ft.com.lab;

import com.itextpdf.text.Font;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.GtPktDtl;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.pricing.ExcelUtilityForm;

import java.io.IOException;

import java.io.OutputStream;

import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import java.sql.Connection;

import java.sql.PreparedStatement;

import java.util.Set;

public class LabSelectionAction extends DispatchAction{

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
        LabSelectionForm labSelectForm = (LabSelectionForm)form;
        GenericInterface genericInt = new GenericImpl();
        labSelectForm.resetALL();
        ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch"); 
        info.setGncPrpLst(assortSrchList);
        ArrayList labList = new ArrayList();
        String labSql = "select chr_fr from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + 
                          " b.mdl = 'JFLEX_LAB' and b.nme_rule =  'DFLTLAB' and a.til_dte is null order by a.srt_fr ";

            ArrayList outLst = db.execSqlLst("labVal", labSql, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            labList.add(rs.getString("chr_fr"));
        }
        rs.close(); pst.close();
        info.setValue("labList", labList);
        
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("LAB_SELECTION");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("LAB_SELECTION");
        allPageDtl.put("LAB_SELECTION",pageDtl);
        }
        info.setPageDetails(allPageDtl);
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
            LabSelectionForm labSelectForm = (LabSelectionForm)form;
            LabSelectionInterface labSelectInt=new LabSelectionImpl();
        boolean isGencSrch = false;
        HashMap stockList = new HashMap();
        ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch"); 
        info.setGncPrpLst(genricSrchLst);
        //      ArrayList genricSrchLst = info.getGncPrpLst();
        String view =util.nvl(labSelectForm.getView());
        String vnm = util.nvl((String)labSelectForm.getValue("vnmLst"));
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        if(view.length() > 0){
            if(vnm.equals("")){
               
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
                        String reqVal1 = util.nvl((String)labSelectForm.getValue(lprp + "_" + lVal),"");
                        if(!reqVal1.equals("")){
                        paramsMap.put(lprp + "_" + lVal, reqVal1);
                        }
                           
                        }
                    }else{
                    String fldVal1 = util.nvl((String)labSelectForm.getValue(lprp+"_1"));
                    String fldVal2 = util.nvl((String)labSelectForm.getValue(lprp+"_2"));
                    if(typ.equals("T")){
                        fldVal1 = util.nvl((String)labSelectForm.getValue(lprp+"_1"));
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
                paramsMap.put("stt", "LAB_SEL");
                paramsMap.put("mdl", "LAB_SEL_VW");
                isGencSrch = true; 
                util.genericSrch(paramsMap);
                stockList = labSelectInt.SearchResultGT(req,res, labSelectForm);
            }
        }
        if(!isGencSrch)
        stockList = labSelectInt.FetchResultGT(req ,res, labSelectForm );
        req.setAttribute("view", "Y");
            String lstNme = "LABSEL_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
            gtMgr.setValue(lstNme+"_SEL",new ArrayList());
            gtMgr.setValue(lstNme, stockList);
            gtMgr.setValue("lstNmeSEL", lstNme);
            ArrayList labList = labSelectInt.getLab(req,res);
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
      
      
        labSelectForm.setLabList(labList);
        labSelectForm.setView("");
        labSelectForm.setViewAll("");
        return am.findForward("load");
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
    HashMap dbinfo = info.getDmbsInfoLst();
    String clnt = (String)dbinfo.get("CNT");
    ExcelUtil xlUtil = new ExcelUtil();
    xlUtil.init(db, util, info);
          String delQ = " Delete from gt_srch_rslt ";
          int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
          
          String lstNme = (String)gtMgr.getValue("lstNmeSEL");
          ArrayList selectstkIdnLst = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
         String stkStr = selectstkIdnLst.toString();
          stkStr = stkStr.replace("[", "(");
          stkStr = stkStr.replace("]", ")");

          String srchRefQ = 
          "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1  ,  cert_lab , rmk , quot,rap_rte,rap_dis) " + 
          " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'M' , qty , cts , sk1 ,  cert_lab , tfl3 , nvl(upr,cmp),rap_rte, decode(rap_rte ,'1',null,'0',null, trunc((nvl(upr,cmp)/greatest(rap_rte,1)*100)-100, 2))  "+
          "     from mstk b where idn  in "+stkStr;
      ct = db.execUpd(" Srch Prp ", srchRefQ, new ArrayList());

    String pktPrp = "pkgmkt.pktPrp(0,?)";
    ArrayList ary = new ArrayList();
    ary.add("LAB_VIEW_XL");
    int ct1 = db.execCall(" Srch Prp ", pktPrp, ary);
    HSSFWorkbook hwb = null;   
    
    hwb =  xlUtil.getInXl("memo", req, "LAB_VIEW_XL");
    
    OutputStream out = res.getOutputStream();
    String CONTENT_TYPE = "getServletContext()/vnd-excel";
    String fileNm = "LabSelectionExcel"+util.getToDteTime()+".xls";
    res.setContentType(CONTENT_TYPE);
    res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
    hwb.write(out);
    out.flush();
    out.close();
      return null;
      }
  }
    public ActionForward save(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            LabSelectionForm labSelectForm = (LabSelectionForm)form;
            String lstNme = (String)gtMgr.getValue("lstNmeSEL");
            ArrayList selectstkIdnLst = new ArrayList();
            HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
            HashMap values = (HashMap)labSelectForm.getValues();
            Set<String> keys = values.keySet();
                for(String key: keys){
                    if(key.indexOf("CHK_")!=-1){
                        String val = (String)values.get(key);
                       selectstkIdnLst.add(val);
                        
                    }
                }
        ArrayList labList = (ArrayList)info.getValue("labList");
        HashMap dbmsSysInfo = info.getDmbsInfoLst();
        int ct=0;
        ArrayList ary = new ArrayList();
        for(int i=0;i< selectstkIdnLst.size();i++){
            String stkIdn = (String)selectstkIdnLst.get(i);
           String lStt  = util.nvl((String)labSelectForm.getValue("stt_" + stkIdn));
         
            String newStt = lStt+"_AV";
            if(lStt.equals("LB")){
                String lab= util.nvl((String) labSelectForm.getValue("lab_" + stkIdn));
                if(labList.contains(lab))
                    newStt = "LB_CF";
                ary = new ArrayList();
                ary.add(stkIdn);
                ary.add(lab);
                ct = db.execCall("lab_sel","ISS_RTN_PKG.LAB_SEL(pStkIdn => ?, pLab => ?)", ary);
                
//                 ary = new ArrayList();
//                 ary.add(newStt);
//                 ary.add(lab);
//                 ary.add(stkIdn);
//                 String updGt ="update mstk set stt=? , cert_lab=? where idn=? ";
//                ct = db.execUpd("update Gt", updGt, ary);
            }else if(lStt.equals("REP") || lStt.equals("MX")){
            
                ary = new ArrayList();
                ary.add(newStt);
                ary.add(stkIdn);
                String updGt ="update mstk set stt=? where idn=? ";
                ct = db.execUpd("update Gt", updGt, ary);
            
            }else if(lStt.equals("PRO")){
                ary = new ArrayList();
                ary.add(stkIdn);
                 ct = db.execCall("DP_BLK_UPD_PROP", "DP_BLK_UPD_PROP(pIdn => ? )", ary);
            }else if(!lStt.equals("None")){
                ary = new ArrayList();
                ary.add(lStt);
                ary.add("MIX");
                ary.add(stkIdn);
                String updGt ="update mstk set stt=? , pkt_ty=? where idn=? ";
                ct = db.execUpd("update Gt", updGt, ary);
                if(ct>0){
                    String sql="select qty,cts ,upr from mstk where idn=?";
                    ary = new ArrayList();
                    ary.add(stkIdn);
                    ResultSet rs = db.execSql("mstkSql", sql, ary);
                    if(rs.next()){
                 String addQty=util.nvl(rs.getString("qty"));
                 String addCts=util.nvl(rs.getString("cts"));
                 String addUpr=util.nvl(rs.getString("upr"));
                 String insertSql= "INSERT INTO MIX_TRF_LOG(LOG_DTE, FRM_IDN, TO_IDN, QTY, CTS,UPR,TYP,RMK,UNM)"+
                                   "VALUES(SYSDATE, ? ,?, ?, ?,?,?,?, PACK_VAR.GET_USR)";
                
                 ary = new ArrayList();
                 ary.add(stkIdn);
                 ary.add(stkIdn);
                 ary.add(String.valueOf(addQty));
                 ary.add(String.valueOf(addCts));
                 ary.add(String.valueOf(addUpr));
                 ary.add("LAB TRNS");
                 ary.add("LABBOX Transfer");
                 ct = db.execUpd("log insert", insertSql, ary);
                    }
                }
            }
              req.setAttribute("msg", "Process done successfully..");
                        
        }
       String cnt = util.nvl((String)dbmsSysInfo.get("CNT"));
      if(cnt.equalsIgnoreCase("HK")){
          ct = db.execCall("MERGE_PKG", "MERGE_PKG.SNGL_TO_MIX", new ArrayList());
      }
        labSelectForm.setView("");
        labSelectForm.setViewAll("");
         GtMgrReset(req);
      
        return am.findForward("load");
        }
    }
    public LabSelectionAction() {
        super();
    }
    
    
  public ActionForward checkALL(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
      HttpSession session = request.getSession(false);
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
      rtnPg=init(request,response,session,util);
      }else{
      rtnPg="connExists";   
      }
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return mapping.findForward(rtnPg);   
      }else{
     
      String stt = util.nvl(request.getParameter("stt"));    
      ArrayList ary = new ArrayList();
     String upexcelLabSecect = "update gt_srch_rslt set flg = ? ";
      String flg="Z";
      if(stt.equals("true"))
        flg="M";
      ary.add(flg);
   
     int ct=db.execUpd("update gt_srch_rslt", upexcelLabSecect, ary);
      response.setContentType("text/xml");
      response.setHeader("Cache-Control", "no-cache");
      response.getWriter().write("<msg>yes</msg>");
      return null;
      }
  }
    public ActionForward checkSingle(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
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
        rtnPg=init(request,response,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return mapping.findForward(rtnPg);   
        }else{
  String stt = util.nvl(request.getParameter("stt"));    
  String stkIdn = util.nvl(request.getParameter("stkIdn"));
  ArrayList ary = new ArrayList();
  String upexcelLabSecect = "update gt_srch_rslt set flg = ? where stk_idn=? ";
  String flg="Z";
  if(stt.equals("true"))
    flg="M";
  ary.add(flg);
  ary.add(stkIdn);
  
    int ct=db.execUpd("update gt_srch_rslt", upexcelLabSecect, ary);
  response.setContentType("text/xml");
  response.setHeader("Cache-Control", "no-cache");
  response.getWriter().write("<msg>yes</msg>");
  return null;
        }
    }   
    public void GtMgrReset(HttpServletRequest req){
          HttpSession session = req.getSession(false);
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
          String lstNme = (String)gtMgr.getValue("lstNmeSEL");
          HashMap gtMgrMap = (HashMap)gtMgr.getValues();
          gtMgrMap.remove(lstNme);
           gtMgrMap.remove(lstNme+"_SEL");
           gtMgrMap.remove(lstNme+"_TTL");
           gtMgrMap.remove("lstNmeSEL");
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
                util.updAccessLog(req,res,"Lab Selection", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Lab Selection", "init");
            }
            }
            return rtnPg;
            }
}
