package ft.com.pricing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.MailAction;
import ft.com.PdfforReport;
import ft.com.dao.UIForms;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ProposToLiveAction extends DispatchAction {
    DBMgr db = null;
    InfoMgr info = null ;
    DBUtil util = null;   
    HttpSession session = null;
    ProposToLiveForm udf = null;
   
    public ProposToLiveAction() {
        super();
    }
    public ActionForward load(ActionMapping mapping, ActionForm af,
                                 HttpServletRequest req,
                                 HttpServletResponse res) throws Exception {
        String rtnPg=init(req,res);
        if(!rtnPg.equals("sucess")){
            return mapping.findForward(rtnPg);   
        }else{
     udf = (ProposToLiveForm)af;
     String sqlRem = "select distinct rem from pri_chng_hdr_v ";
          ArrayList outLst = db.execSqlLst("remSql", sqlRem, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);     
            
     ArrayList remList = new ArrayList();
     while(rs.next()){
        ArrayList remDtl = new ArrayList();
         remDtl.add(rs.getString("rem"));
         remList.add(remDtl);
     }
        rs.close();
            pst.close();
     session.setAttribute("remList", remList);
     
     return mapping.findForward("load");
        }
    }
    public ActionForward Fetch(ActionMapping mapping, ActionForm af,
                                 HttpServletRequest req,
                                 HttpServletResponse res) throws Exception {
        String rtnPg=init(req,res);
        if(!rtnPg.equals("sucess")){
            return mapping.findForward(rtnPg);   
        }else{
     udf = (ProposToLiveForm)af;
     String rem = req.getParameter("rem");
     String sqlRem = " select a.rem, a.idn, a.lv_idn , a.nme, a.dte , a.typ from  pri_chng_hdr_v a "+
                     " where  a.rem = ?  order by a.rem, a.rem_srt";
    ArrayList ary = new ArrayList();
    ary.add(rem);
          ArrayList outLst = db.execSqlLst("remSql", sqlRem, ary );
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);     
            
     ArrayList remList = new ArrayList();
     while(rs.next()){
        ArrayList remDtl = new ArrayList();
         remDtl.add(rs.getString("idn"));
         remDtl.add(rs.getString("nme"));
         remDtl.add(rs.getString("dte"));
         remDtl.add(rs.getString("rem"));
         remDtl.add(rs.getString("lv_idn"));
         remDtl.add(rs.getString("idn"));
         remDtl.add(rs.getString("typ"));
         remList.add(remDtl);
     }
        rs.close();
            pst.close();
     req.setAttribute("remList", remList);
     req.setAttribute("rem", rem);

        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("PROPOSE_TO_LIVE");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("PROPOSE_TO_LIVE");
        allPageDtl.put("PROPOSE_TO_LIVE",pageDtl);
        }
        info.setPageDetails(allPageDtl);
     return mapping.findForward("load");
        }
    }
    public ActionForward ReverttoLive(ActionMapping mapping, ActionForm af,
                                 HttpServletRequest req,
                                 HttpServletResponse res) throws Exception {
        String rtnPg=init(req,res);
        if(!rtnPg.equals("sucess")){
            return mapping.findForward(rtnPg);   
        }else{
     String nme = util.nvl(req.getParameter("nme"));
     String rem = util.nvl(req.getParameter("rem"));
     ArrayList ary = new ArrayList();
     ary.add(rem);
     ary.add(nme);
     int ct = db.execCall("dp_trf_frm_live", "Jbutl.dp_trf_frm_live( pGrp=> ?, pNme => ?)", ary);
    if(ct>0){
         req.setAttribute("successMsg","Process done successfully..");
    }else{
        req.setAttribute("successMsg","Process failed . Please verify...");
    }
     return mapping.findForward("loadSucc");
        }
    }
    public ActionForward save(ActionMapping mapping, ActionForm af,
                                    HttpServletRequest req,
                                    HttpServletResponse res) throws Exception {
         String rtnPg=init(req,res);
         if(!rtnPg.equals("sucess")){
             return mapping.findForward(rtnPg);   
         }else{
        String verifyALL = util.nvl(req.getParameter("verifyALL"));
        String verifySelected = util.nvl(req.getParameter("verifySelected"));
        String comparison = util.nvl(req.getParameter("comparison"));
        String rem = util.nvl(req.getParameter("rem"));
        int ct = 0;
         ArrayList ary = new ArrayList();
        MailAction mailAction = new MailAction();
        ArrayList idnList = new ArrayList();
       
        if(!verifyALL.equals("")){
            String sqlRem = " select a.rem, a.idn, a.lv_idn , a.nme, a.dte from  pri_chng_hdr_v a "+
                               " where  a.rem = ?  order by a.rem, a.rem_srt";
            ary = new ArrayList();
            ary.add(rem);
          ArrayList outLst = db.execSqlLst("remSql", sqlRem, ary );
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);   
            HashMap  matDtlMap = new HashMap();
            while(rs.next()){
               String lv_idn = rs.getString("lv_idn");
                String nme = rs.getString("nme");
               idnList.add(nme);
               HashMap matDtl = util.getMatrixDtl(Integer.parseInt(lv_idn),"");
               matDtlMap.put("BF_"+nme, matDtl);
               matDtlMap.put("NME_"+nme,rs.getString("nme"));
               matDtlMap.put("GRP_"+nme,rs.getString("rem"));
            }
            rs.close();
            pst.close();
         ary = new ArrayList();
         ary.add(rem);
         ct = db.execCall("df_trf_prop", "DP_TRF_FRM_PROP(pGrp=>?)", ary);
         for(int i=0;i<idnList.size();i++){
             String nme = (String)idnList.get(i);
             String getNme = " select idn from dyn_mst_t where rem =? and nme = ?";
             ary = new ArrayList();
             ary.add(rem);
             ary.add(nme);
            outLst = db.execSqlLst("get Nme", getNme, ary);
            pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1); 
            if(rs.next()) {
             String lv_idn = rs.getString("idn");
             HashMap matDtl = util.getMatrixDtl(Integer.parseInt(lv_idn),"");
             matDtlMap.put("AF_"+nme, matDtl);
            }
             rs.close();
             pst.close();
         }
         
        mailAction.GrpVerifyPriceMail(matDtlMap,idnList, req, res);
        }else if(!verifySelected.equals("")){
            Enumeration reqNme = req.getParameterNames();
            ArrayList nmeLst = new ArrayList();
            while(reqNme.hasMoreElements()) {
                String paramNm = (String)reqNme.nextElement();
                if(paramNm.indexOf("cb_rem") > -1) {
                    String val = util.nvl(req.getParameter(paramNm));
                    if(!val.equals("")){
                        val = val.trim();
                        nmeLst.add(val);
                    }
                }
            }
            if(nmeLst!=null && nmeLst.size()>0){
                HashMap  matDtlMap = new HashMap();
                for(int i=0 ; i < nmeLst.size() ;i++){
                String nme = util.nvl((String)nmeLst.get(i));
                    String sqlRem = " select a.rem, a.idn, a.lv_idn , a.nme, a.dte from  pri_chng_hdr_v a "+
                                       " where  a.rem = ? and a.nme =?  order by a.rem, a.rem_srt";
                    ary = new ArrayList();
                    ary.add(rem);
                    ary.add(nme);
                  ArrayList outLst = db.execSqlLst("remSql", sqlRem, ary );
                  PreparedStatement pst = (PreparedStatement)outLst.get(0);
                  ResultSet rs = (ResultSet)outLst.get(1); 
                   if(rs.next()){
                       String lv_idn = rs.getString("lv_idn");
                       idnList.add(nme);
                       HashMap matDtl = util.getMatrixDtl(Integer.parseInt(lv_idn),"");
                       matDtlMap.put("BF_"+nme, matDtl);
                       matDtlMap.put("NME_"+nme,rs.getString("nme"));
                       matDtlMap.put("GRP_"+nme,rs.getString("rem"));
                    }
                    rs.close();
                    pst.close();
                ary = new ArrayList();
                 ary.add(rem);
                 ary.add(nme);
                ct = db.execCall("df_trf_prop", "DP_TRF_FRM_PROP(pGrp=>? , pNme => ?)", ary);
               
                  
                     
                        String getNme = " select idn from dyn_mst_t where rem =? and nme = ?";
                        ary = new ArrayList();
                        ary.add(rem);
                        ary.add(nme);
                   outLst = db.execSqlLst("get Nme", getNme, ary);
                   pst = (PreparedStatement)outLst.get(0);
                   rs = (ResultSet)outLst.get(1); 
                        if(rs.next()) {
                        String lv_idn = rs.getString("idn");
                        HashMap matDtl = util.getMatrixDtl(Integer.parseInt(lv_idn),"");
                        matDtlMap.put("AF_"+nme, matDtl);
                        }
                    rs.close();
                    pst.close();
                    
                }
                mailAction.GrpVerifyPriceMail(matDtlMap,idnList, req, res);
            }
        
            
        }else if(!comparison.equals("")){
          return comparison(mapping, af, req, res);
        }else{
            Enumeration reqNme = req.getParameterNames();
            ArrayList nmeLst = new ArrayList();
            while(reqNme.hasMoreElements()) {
                String paramNm = (String)reqNme.nextElement();
                if(paramNm.indexOf("cb_rem") > -1) {
                    String val = util.nvl(req.getParameter(paramNm));
                    if(!val.equals("")){
                        val = val.trim();
                        nmeLst.add(val);
                    }
                }
            }
            if(nmeLst!=null && nmeLst.size()>0){
              ArrayList proposIdnLst = new ArrayList();
              ArrayList liveIdnLst = new ArrayList();
              ArrayList grpNmeLst = new ArrayList();
                    HashMap matDtlList = new HashMap();
                    String dbStr = util.nvl((String)info.getDmbsInfoLst().get("PRI_PROP"));
                    dbStr = dbStr+".";
                for(int i=0 ; i < nmeLst.size() ;i++){
                String nme = util.nvl((String)nmeLst.get(i));
                    String sqlRem = " select a.rem, a.idn, a.lv_idn , a.nme, a.dte from  pri_chng_hdr_v a "+
                                       " where  a.rem = ? and a.nme =?  order by a.rem, a.rem_srt";
                    ary = new ArrayList();
                    ary.add(rem);
                    ary.add(nme);
                  ArrayList outLst = db.execSqlLst("remSql", sqlRem, ary );
                 PreparedStatement pst = (PreparedStatement)outLst.get(0);
                 ResultSet rs = (ResultSet)outLst.get(1); 
                    while(rs.next()){
                       String lv_idn = rs.getString("lv_idn");
                        String prp_idn = rs.getString("idn");
                        matDtlList.put("PROP_"+prp_idn,util.getMatrixDtl(Integer.parseInt(prp_idn), dbStr));
                        matDtlList.put("LIVE_"+lv_idn,util.getMatrixDtl(Integer.parseInt(lv_idn),""));
                        proposIdnLst.add(prp_idn);
                        liveIdnLst.add(lv_idn);
                        grpNmeLst.add(rem);
                    }
                    rs.close();
                    pst.close();
                }
                   session.setAttribute("matDtlMap",matDtlList);
                    PdfforReport pdf=new PdfforReport();
                    HashMap dbSysInfo = info.getDmbsInfoLst();
                    String docPath = (String)dbSysInfo.get("DOC_PATH");
                    String FILE = "PDF_ProposToLive_"+util.getToDteTime()+".pdf";
                     //String FILEPATH="E:/"+FILE;
                     String FILEPATH= docPath+""+FILE;

                    pdf.getProposDataInPDF(proposIdnLst,liveIdnLst,nmeLst,grpNmeLst,req,FILEPATH);
                    byte[] byteArray=null;
                    InputStream inputStream = new FileInputStream(FILEPATH);
                    byteArray = readFully(inputStream);

                    inputStream.close();
                    res.setHeader("Pragma", "no-cache");
                    res.setHeader("Cache-control", "private");
                    res.setDateHeader("Expires", 0);
                    res.setContentType("application/pdf");
                    res.setHeader("Content-Disposition", "attachment; filename="+ FILE);

                    if (byteArray != null) {
                    res.setContentLength(byteArray.length);
                    ServletOutputStream out = res.getOutputStream();
                    out.write(byteArray);
                    out.flush();
                    out.close();
                    }
                   return Fetch(mapping, af, req, res);
                }
        }
          if(ct > 0){
              req.setAttribute("msg", "Changes get Apply");
          }
         return mapping.findForward("load");
         }
     }
    public ActionForward comparison(ActionMapping mapping, ActionForm af,
                                 HttpServletRequest req,
                                 HttpServletResponse res) throws Exception {
         String rtnPg=init(req,res);
         if(!rtnPg.equals("sucess")){
             return mapping.findForward(rtnPg);   
         }else{
         String rem = util.nvl(req.getParameter("rem"));
         String count =util.nvl(req.getParameter("count"));
         ArrayList nmeLst = new ArrayList();
         if(!count.equals("")){
             int cnt = Integer.parseInt(count);
             for(int i=1;i<=cnt;i++){
                 String fldnme = "cb_rem_"+i;
                 String val = util.nvl(req.getParameter(fldnme));
                 if(!val.equals("")){
                     val = val.trim();
                     nmeLst.add(val);
                 }
             }
         }
//         Enumeration reqNme = req.getParameterNames();
//         List<String> reqList = Collections.list(reqNme);
//         Collections.sort(reqList);
//        
//         for(String paramNm : reqList) {
//             if(paramNm.indexOf("cb_rem") > -1) {
//                 String val = util.nvl(req.getParameter(paramNm));
//                 if(!val.equals("")){
//                     val = val.trim();
//                     nmeLst.add(val);
//                 }
//             }
//         }
        
         ArrayList idnDtlList = new ArrayList();
         
         HashMap nmeDtl = new HashMap();
         HashMap nmeSrt = new HashMap();
         
         if(nmeLst!=null && nmeLst.size()>0){
             for(int i=0;i< nmeLst.size() ; i++){
                 String nme =(String)nmeLst.get(i);
                 String sqlRem = " select a.rem, a.idn, a.lv_idn , a.nme, a.dte, a.rem_srt from  pri_chng_hdr_v a "+
                                 " where  a.rem = ?  and a.nme = ? order by a.rem, a.rem_srt";
                 ArrayList ary = new ArrayList();
                 ary.add(rem);
                 ary.add(nme);
                 ArrayList outLst = db.execSqlLst("sqlRem", sqlRem, ary);
                 PreparedStatement pst = (PreparedStatement)outLst.get(0);
                 ResultSet rs = (ResultSet)outLst.get(1);
                 while(rs.next()){
                     ArrayList idnDtl = new ArrayList();
                     idnDtl.add(util.nvl(rs.getString("idn")));
                     idnDtl.add(util.nvl(rs.getString("lv_idn")));
                     String remNme = util.nvl(rs.getString("nme"));
                     idnDtl.add(remNme);
                     String key_srt = util.nvl(rs.getString("rem_srt"));
                     //idnDtl.add(key_srt);
                     //nmeRem.add(key_srt);
                     //nmeSrt.put(remNme, key_srt);
                     //nmeDtl.put(remNme, idnDtl);
                     idnDtlList.add(idnDtl);
                 }
                 rs.close();
                 pst.close();
             }
         }
      HashMap matDtlMap =  comparisonData(idnDtlList);
      session.setAttribute("matDtlMap",matDtlMap);
      req.setAttribute("idnDtlList",idnDtlList);
      req.setAttribute("grpNme", rem);
         HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
         HashMap pageDtl=(HashMap)allPageDtl.get("PROPOSE_COMPARISION");
         if(pageDtl==null || pageDtl.size()==0){
         pageDtl=new HashMap();
         pageDtl=util.pagedef("PROPOSE_COMPARISION");
         allPageDtl.put("PROPOSE_COMPARISION",pageDtl);
         }
         info.setPageDetails(allPageDtl);
     return mapping.findForward("comparison");
         }
     }
    
    public HashMap comparisonData(ArrayList idnDtlList){
        HashMap matDtlMap = new HashMap();
        String dbStr = util.nvl((String)info.getDmbsInfoLst().get("PRI_PROP"));
        dbStr = dbStr+".";
        for(int i=0;i<idnDtlList.size();i++){
            ArrayList idnDtl = (ArrayList)idnDtlList.get(i);
            String proposIdn = (String)idnDtl.get(0);
            String liveIdn = (String)idnDtl.get(1);
            HashMap matDtl = util.getMatrixDtl(Integer.parseInt(proposIdn), dbStr);
            matDtlMap.put("PROP_"+proposIdn, matDtl);
            matDtl = util.getMatrixDtl(Integer.parseInt(liveIdn), "Jbros.");
            matDtlMap.put("LIVE_"+liveIdn, matDtl);
        }
        
        return matDtlMap;
    }
    
    public ActionForward createPDF(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
        String rtnPg=init(req,res);
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
    PdfforReport pdf=new PdfforReport();
    HashMap dbSysInfo = info.getDmbsInfoLst();
    String docPath = (String)dbSysInfo.get("DOC_PATH");
    String proposIdn = req.getParameter("proposIdn");
    String liveIdn = req.getParameter("liveIdn");
    String nme = req.getParameter("nme");
    String grpNme = req.getParameter("grpNme");
    ArrayList proposIdnLst = new ArrayList();
    ArrayList liveIdnLst = new ArrayList();
    ArrayList nmeLst = new ArrayList();
    ArrayList grpNmeLst = new ArrayList();
    proposIdnLst.add(proposIdn);
    liveIdnLst.add(liveIdn);
    nmeLst.add(nme);
    grpNmeLst.add(grpNme);
    String FILE = "PDF_ProposToLive_"+util.getToDteTime()+".pdf";
    // String FILEPATH="E:/"+FILE;
    String FILEPATH= docPath+""+FILE;

    pdf.getProposDataInPDF(proposIdnLst,liveIdnLst,nmeLst,grpNmeLst,req,FILEPATH);
    byte[] byteArray=null;
    InputStream inputStream = new FileInputStream(FILEPATH);
    byteArray = readFully(inputStream);

    inputStream.close();
    res.setHeader("Pragma", "no-cache");
    res.setHeader("Cache-control", "private");
    res.setDateHeader("Expires", 0);
    res.setContentType("application/pdf");
    res.setHeader("Content-Disposition", "attachment; filename="+ FILE);

    if (byteArray != null) {
    res.setContentLength(byteArray.length);
    ServletOutputStream out = res.getOutputStream();
    out.write(byteArray);
    out.flush();
    out.close();
    }
    return null;
        }
    }

    public static byte[] readFully(InputStream stream) throws IOException
    {
    byte[] buffer = new byte[8192];
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    int bytesRead;
    while ((bytesRead = stream.read(buffer)) != -1)
    {
    baos.write(buffer, 0, bytesRead);
    }
    return baos.toByteArray();
    }


    
    public String getDsr(String idn){
    String dsc="";
        ArrayList ary = new ArrayList();
        String sql= "select get_pri_dscr(?) dscr from dual";
        ary.add(idn);
      ArrayList outLst = db.execSqlLst("getDscr", sql, ary );
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try{
        while(rs.next()){
            dsc=util.nvl(rs.getString("dscr"));
        }
           rs.close();
            pst.close();
       } catch (SQLException sqle) {
      sqle.printStackTrace();
      
      }
        return dsc;
    }
    public String init(HttpServletRequest req , HttpServletResponse res) {
        String rtnPg="sucess";
        String invalide="";
        session = req.getSession(false);
        db = (DBMgr)session.getAttribute("db");
        info = (InfoMgr)session.getAttribute("info");
        util = ((DBUtil)session.getAttribute("util") == null)?new DBUtil():(DBUtil)session.getAttribute("util");
            String connExists=util.nvl(util.getConnExists());  
            if(!connExists.equals("N"))
            invalide=util.nvl(util.chkTimeOut(),"N");
            if(session.isNew() || db==null)
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
                }
            }    
        return rtnPg;
        }
}
