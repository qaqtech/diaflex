package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.PdfforReport;
import ft.com.assort.AssortIssueImpl;
import ft.com.assort.AssortIssueInterface;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;
import ft.com.dao.UIForms;
import ft.com.marketing.PacketPrintForm;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MixToMixAction extends DispatchAction {
  
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
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
           util.updAccessLog(req,res,"MixToMix", "load");
        MixToMixForm udf = (MixToMixForm)af;
        udf.resetAll();
        String status=util.nvl(req.getParameter("STT"));
        if(status.equals("")){
            ArrayList mixsttList=mixsttList(db);
            req.setAttribute("mixtomixsttList", mixsttList); 
        } 
           util.updAccessLog(req,res,"MixToMix", "load end");
        return am.findForward("load");
         }
     }
    
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
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
           util.updAccessLog(req,res,"MixToMix", "save");
        MixToMixForm udf = (MixToMixForm)af;
         ArrayList ary = new ArrayList();
         String   stt = util.nvl((String)udf.getValue("stt"));
         if(stt.equals(""))
             stt="MKAV"; 
         for(int i=1; i <=5; i++) {
             String trf = util.nvl((String)udf.getValue("TRFF_"+i),"0");  
             if(!trf.equals("0") && !trf.equals("")){
                 String idnf = util.nvl((String)udf.getValue("IDNF_"+i),"0"); 
                 String idnt = util.nvl((String)udf.getValue("IDNT_"+i),"0"); 
                 String rnk = util.nvl((String)udf.getValue("nmeIDt_"+i),"0");  
                 String memo = util.nvl((String)udf.getValue("MEMOF_"+i),"0");
                 String rtet = util.nvl((String)udf.getValue("RTET_"+i),"0");
                 ary = new ArrayList();
                 ary.add(idnf);
                 ary.add(idnt);
                 ary.add(trf);
                 ary.add(rnk);
                 ary.add(stt);
                 ary.add(memo);
                 ary.add(rtet);
                 String mix ="MIX_PKG.MIX_TO_MIX_TRF(pFrmPkt =>?, pToPkt =>?, pTrfCts =>?, pRnk =>?,pSTT=>?,pMemo=>?,pRte => ?)";
                 int ct = db.execCall("mix To mix", mix, ary);
           }
         }
         udf.resetAll();
         if(!stt.equals("MKAV")){
             ArrayList mixsttList=mixsttList(db);
             req.setAttribute("mixtomixsttList", mixsttList); 
         }
           util.updAccessLog(req,res,"MixToMix", "save end");
         return am.findForward("load");
         }
     }
   
    public ActionForward mixShapeszClr(ActionMapping am, ActionForm af,  HttpServletRequest req, HttpServletResponse res) throws Exception {
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
          
       MixToMixForm udf = (MixToMixForm)af;
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        ArrayList ary=new ArrayList();
        StringBuffer sb      = new StringBuffer();
        String       initial = req.getParameter("param");
        String lFld = util.nvl(req.getParameter("lFld"));
        String       match   = "" + initial + "%";
        match = match.toLowerCase();
        String sql = "select rnk, form_url_encode(dsc) nme  from mix_sh_sz_clr_v where lower(dsc) like lower(?)  " + 
        "and dsc is not null and rownum < 50 order by rnk " ;
        
        ary.add(match);
          ArrayList  rsLst = db.execSqlLst("column nme", sql, ary);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
        while (rs.next()) {
            String nme = util.nvl(rs.getString(2), "0");
            sb.append("<nmes>");
            sb.append("<nmeid>" + util.nvl(rs.getString(1), "0") + "</nmeid>");
            sb.append("<nme>" + nme + "</nme>");
            sb.append("</nmes>");

            /*
             * if(ctr == 20)
             *   break;
             */
        }} catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }

        rs.close();
          stmt.close();
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");

        return null;
        }
    }
    public ActionForward getMixIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
       MixToMixForm udf = (MixToMixForm)af;
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    ArrayList ary=new ArrayList();
    StringBuffer sb = new StringBuffer();
    String rnk = util.nvl(req.getParameter("rnk")).trim();
    String stt = util.nvl(req.getParameter("stt")).trim();
    String memo = util.nvl(req.getParameter("memo")).trim();
    String conQ="";
    if(stt.equals(""))
    stt="MKAV";
    if(!rnk.equals("")){
    if(!memo.equals("")){
    conQ=conQ+", STK_DTL MEMO\n";
    }
    String sql = "Select B.Idn,B.Cts\n" + 
    "From Mix_Sh_Sz_Clr_V A, MSTK B\n" + 
    ", STK_DTL SH\n" + 
    ", STK_DTL SZ\n" + 
    ", STK_DTL CLR\n" + 
    ", STK_DTL DP,STK_DTL DEP\n" + conQ+
    " Where A.Rnk=? And B.STT = ? and b.pkt_ty <> 'NR' \n" + 
    "and b.idn = sh.mstk_idn and sh.grp = 1 and sh.mprp = 'SHAPE' and a.shape = sh.val\n" + 
    "and b.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_SIZE' and a.mix_size = sz.val\n" + 
    "and b.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY' and a.mix_clarity = clr.val\n" +
    "and b.idn = dep.mstk_idn and dep.grp = 1 and dep.mprp = 'DEPT' and dep.val not in('S3')\n" +
    "And B.Idn = Dp.Mstk_Idn And Dp.Grp = 1 And Dp.Mprp = 'DP' And A.Dp = Dp.Num and b.cts > 0 ";
    ary.add(rnk);
    ary.add(stt);
    if(!memo.equals("")){
        sql = sql+" And B.Idn = Memo.Mstk_Idn And Memo.Grp = 1 And Memo.Mprp = 'MEMO' And MEMO.TXT = '"+memo+"'";
     }
      ArrayList  rsLst = db.execSqlLst("Mix Idn", sql, ary);
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
    try {
    if (rs.next()) {
    sb.append("<nmes>");
    sb.append("<idn>" + util.nvl(rs.getString("idn"), "0") + "</idn>");
    sb.append("<cts>" + util.nvl(rs.getString("cts"), "0") + "</cts>");
    sb.append("</nmes>");
    }else{
    sb.append("<nmes>");
    sb.append("<idn>0</idn>");
    sb.append("<cts>0</cts>");
    sb.append("</nmes>");
    }
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    rs.close();
    stmt.close();
    }


    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");

    return null;
        }
    }
    
    public ActionForward getMixIdnRTE(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
      
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    ArrayList ary=new ArrayList();
    StringBuffer sb = new StringBuffer();
    String mstkIdn = util.nvl(req.getParameter("mstkIdn")).trim();
    String sql = "select num from stk_dtl where mprp='RTE' and grp=1 and mstk_idn=? ";
    ary.add(mstkIdn);
      ArrayList  rsLst = db.execSqlLst("Mix Idn", sql, ary);
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
    try {
    if (rs.next()) {
    sb.append("<nmes>");
    sb.append("<rte>" + util.nvl(rs.getString("num"), "1") + "</rte>");
    sb.append("</nmes>");
    }else{
    sb.append("<nmes>");
    sb.append("<rte>1</rte>");
    sb.append("</nmes>");
    }
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    rs.close();
  stmt.close();

    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");

    return null;
    }
    }
    public ArrayList mixsttList(DBMgr db){
        JspUtil jspUtil = new JspUtil();
        ArrayList mixsttList = new ArrayList();
        String memoPrntOptn = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
        + "b.mdl = 'JFLEX' and b.nme_rule = 'MIXTOMIX' and a.til_dte is null order by a.srt_fr ";
      ArrayList  rsLst = db.execSqlLst("memoPrint", memoPrntOptn, new ArrayList());
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
        while (rs.next()) {
            ArrayList data = new ArrayList();
            data.add(jspUtil.nvl(rs.getString("chr_fr")));
            data.add(jspUtil.nvl(rs.getString("dsc")));
            mixsttList.add(data);
        }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        }

        return mixsttList;
    }
    
    public ActionForward IsMemo(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
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
        StringBuffer sb = new StringBuffer();
        String stt = util.nvl(req.getParameter("stt"));
        String sql = "select a.dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + 
                     " b.mdl = 'JFLEX' and b.nme_rule = 'MIXTOMIX' and a.chr_fr=?";
        ArrayList ary = new ArrayList();
        ary.add(stt);
           ArrayList  rsLst = db.execSqlLst("memoPrint", sql , ary);
           PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
           ResultSet  rs =(ResultSet)rsLst.get(1);
         try {
        if(rs.next()) {
           sb.append("<flg>"+util.nvl(rs.getString("dta_typ"))+"</flg>");
         }
             rs.close();
             stmt.close();
         } catch (SQLException sqle) {
         // TODO: Add catch code
         sqle.printStackTrace();
         }
         res.setContentType("text/xml");
         res.setHeader("Cache-Control", "no-cache");
         res.getWriter().write(sb.toString());
     return null;
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
              util.updAccessLog(req,res,"Mix to MIX Action", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Mix to MIX Action", "init");
          }
          }
          return rtnPg;
          }
    
    
    public MixToMixAction() {
        super();
    }
}
