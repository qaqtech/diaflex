package ft.com.receipt;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtilObj;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.dao.ByrDao;

import ft.com.dao.ObjBean;

import java.io.OutputStream;

import java.math.BigDecimal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ReceiptTransReport  extends  DispatchAction {
    public ReceiptTransReport() {
        super();
    }
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        HashMap dtlMap = new HashMap();
        ArrayList recevieLst  = new ArrayList();
        ArrayList payLst  = new ArrayList(); 
            BigDecimal amtP =  new BigDecimal(0);
            BigDecimal amtR =  new BigDecimal(0);
            BigDecimal amtD =  new BigDecimal(0);
            BigDecimal amtUP =  new BigDecimal(0);
            BigDecimal amtUR =  new BigDecimal(0);
            BigDecimal amtUD =  new BigDecimal(0);
         String reportdata=" select nm , flg, to_char(sum(trunc(amt,2)),'99,99,99,99,99,990.00') amt, to_char(sum(trunc(cur,2)),'999999999990.00') usdAmt from gl_os  \n" + 
                           " where entry_point <> 'DFLT'  and amt > 1 group by nm,flg order by flg,nm";
            ArrayList rsLst = db.execSqlLst("data sql", reportdata, new ArrayList());
            PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
                HashMap dtl = new HashMap();
                String amt = rs.getString("amt");
                 String cur = rs.getString("usdAmt");
                dtl.put("NME", rs.getString("nm"));
                dtl.put("AMT", amt);
                 dtl.put("CUR", cur);
                amt=amt.replaceAll(",", "").trim();
                 cur=cur.trim();
                BigDecimal amtB=  new BigDecimal(amt);
                 BigDecimal amtUB=  new BigDecimal(cur);
                String flg = rs.getString("flg");
                if(flg.equals("R")){
                    recevieLst.add(dtl);
                   amtR= amtR.add(amtB);
                    amtUR= amtUR.add(amtUB);
                }else{
                    payLst.add(dtl);
                   amtP = amtP.add(amtB);
                    amtUP = amtUP.add(amtUB);
                }
             }
            dtlMap.put("RECLST", recevieLst);
            dtlMap.put("PAYLST", payLst);
            dtlMap.put("RECTTL", String.valueOf(amtR));
            dtlMap.put("PAYTTL", String.valueOf(amtP));
            dtlMap.put("RECUTTL", String.valueOf(amtUR));
            dtlMap.put("PAYUTTL", String.valueOf(amtUP));
            rs.close();
            psmt.close();
            ArrayList dfltActList = new ArrayList();
            String dfltdata="select nm,to_char(trunc(amt,2),'99,99,99,99,99,990.00') amt , to_char(trunc(cur,2),'9999999999990.00') usdAmt from gl_os\n" + 
                             "where entry_point = 'DFLT' order by amt";
                rsLst = db.execSqlLst("data sql", dfltdata, new ArrayList());
                psmt = (PreparedStatement)rsLst.get(0);
                rs = (ResultSet)rsLst.get(1);
               while (rs.next()) {
                   HashMap dtl = new HashMap();
                   String amt = rs.getString("amt");
                    String cur = rs.getString("usdAmt");
                   dtl.put("NME", rs.getString("nm"));
                   dtl.put("AMT", amt);
                    dtl.put("CUR", cur);
                   amt=amt.replaceAll(",", "").trim();
                    cur=cur.trim();
                   BigDecimal amtB=  new BigDecimal(amt);
                    BigDecimal amtUB=  new BigDecimal(cur);
                   amtD = amtD.add(amtB);
                    amtUD = amtUD.add(amtUB);
                    dfltActList.add(dtl);
                }
              rs.close();
              psmt.close();
            dtlMap.put("DFLTLST", dfltActList);
            dtlMap.put("DFLTTTL", String.valueOf(amtD));
            dtlMap.put("DFLTUTTL", String.valueOf(amtUD));
            session.setAttribute("OUTDTLMAP", dtlMap);
            return am.findForward("load");   
        }
    }
    
    
    public ActionForward ReceiptTrans(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        HashMap dtlMap = new HashMap();
        ArrayList purLst  = new ArrayList();
        ArrayList soldLst  = new ArrayList(); 
            BigDecimal amtP =  new BigDecimal(0);
            BigDecimal amtS =  new BigDecimal(0);
            BigDecimal amtUP =  new BigDecimal(0);
            BigDecimal amtUS =  new BigDecimal(0);
         String reportdata="select decode(os.ref_typ,'PUR','PURCHASE','SOLD') typ\n" + 
         ", os.nm, to_char(sm.ref_dte,'dd-MON-yyyy') ref_dte, sm.ref_idn,sm.cntidn, sm.dys, to_char(sm.due_dte,'dd-MON-yyyy') due_dte\n" + 
         ", trunc(sm.due_dte) - trunc(sysdate) EDys\n" + 
         ", to_char(sum(trunc(os.amt,2)),'99,99,99,99,99,990.00') amt,to_char(sum(trunc(os.cur,2)),'9999999999990.00') amtUSD\n" + 
         "from gl_trns_os os,gl_trns_smry sm\n" + 
         "where os.ref_idn=sm.ref_idn and os.amt>1 and os.entry_point <> 'DFLT'\n" + 
         "group by decode(os.ref_typ,'PUR','PURCHASE','SOLD'),os.nm,sm.ref_dte,sm.ref_idn,sm.dys,sm.due_dte,sm.cntidn\n" + 
         "order by 1, sm.due_dte";
            ArrayList rsLst = db.execSqlLst("data sql", reportdata, new ArrayList());
            PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
                HashMap dtl = new HashMap();
                String typ = rs.getString("typ");
                 String amt = rs.getString("amt");
                 String cur = rs.getString("amtUSD");
                dtl.put("NME", rs.getString("nm"));
                dtl.put("REF_DTE", rs.getString("ref_dte"));
                dtl.put("REF_IDN", rs.getString("ref_idn"));
                 dtl.put("CNTIDN", rs.getString("cntidn"));
                dtl.put("DYS", rs.getString("DYS"));
                dtl.put("DUE_DTE", rs.getString("DUE_DTE"));
                dtl.put("ENDDYS", rs.getString("EDys"));
                dtl.put("AMT", amt);
                 dtl.put("CUR", cur);
                 amt=amt.replaceAll(",", "").trim();
                 cur=cur.replaceAll(",", "").trim();
                BigDecimal amtB=  new BigDecimal(amt);
                 BigDecimal amtUB=  new BigDecimal(cur);
                if(typ.equals("PURCHASE")){
                    purLst.add(dtl);
                   amtP = amtP.add(amtB);
                   amtUP = amtUP.add(amtUB);
                }else{
                    soldLst.add(dtl);
                    amtS = amtS.add(amtB);
                    amtUS = amtUS.add(amtUB);
                }
             }
            dtlMap.put("PURLST", purLst);
            dtlMap.put("SOLDLST", soldLst);
            dtlMap.put("PURTTL", String.valueOf(amtP));
            dtlMap.put("SOLDTTL", String.valueOf(amtS));
            dtlMap.put("PURUTTL", String.valueOf(amtUP));
            dtlMap.put("SOLDUTTL", String.valueOf(amtUS));
            rs.close();
            psmt.close();
            session.setAttribute("OUTTANDTLMAP", dtlMap);
            return am.findForward("ReceiptTrans");   
        }
    }
    
    public ActionForward EstimatedDues(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        HashMap dtlMap = new HashMap();
        ArrayList RecLst  = new ArrayList();
        ArrayList payLst  = new ArrayList(); 
            BigDecimal amtP =  new BigDecimal(0);
            BigDecimal amtR =  new BigDecimal(0);
            BigDecimal amtUP =  new BigDecimal(0);
            BigDecimal amtUR =  new BigDecimal(0);
            ReceiptTransReportForm udf = (ReceiptTransReportForm)form;
            String dfr = util.nvl((String)udf.getValue("dtefr"));
            String dto = util.nvl((String)udf.getValue("dteto"));
            
            String dtefrom = " trunc(sysdate) ";
            String dteto = " trunc(sysdate) ";
            if(!dfr.equals(""))
              dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
            
            if(!dto.equals(""))
              dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
            
           
            ArrayList ary  = new ArrayList(); 
         String reportdata="select os.flg\n" + 
         ", os.nm, to_char(sm.ref_dte,'dd-MON-yyyy') ref_dte, sm.ref_idn,sm.cntidn, sm.dys, to_char(sm.due_dte,'dd-MON-yyyy') due_dte\n" + 
         ", trunc(sm.due_dte) - trunc(sysdate) EDys\n" + 
         ", to_char(sum(trunc(os.amt,2)),'99,99,99,99,99,990.00') amt,to_char(sum(trunc(os.cur,2)),'9999999999990.00') amtUSD\n" + 
         "from gl_trns_os os,gl_trns_smry sm\n" + 
         "where os.ref_idn=sm.ref_idn and os.amt>0 and os.entry_point <> 'DFLT'\n" + 
         " and trunc(sm.due_dte) between "+dtefrom+" and "+dteto+
         "group by os.flg,os.nm,sm.ref_dte,sm.ref_idn,sm.dys,sm.due_dte,sm.cntidn\n" + 
         "order by 1, sm.due_dte";
            
            ArrayList rsLst = db.execSqlLst("data sql", reportdata,ary);
            PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
                HashMap dtl = new HashMap();
                String typ = rs.getString("flg");
                 String amt = rs.getString("amt");
                 String amtUSD = rs.getString("amtUSD");
                dtl.put("NME", rs.getString("nm"));
                dtl.put("REF_DTE", rs.getString("ref_dte"));
                dtl.put("REF_IDN", rs.getString("ref_idn"));
                 dtl.put("CNTIDN", rs.getString("cntidn"));
                dtl.put("DYS", rs.getString("DYS"));
                dtl.put("DUE_DTE", rs.getString("DUE_DTE"));
                dtl.put("ENDDYS", rs.getString("EDys"));
                dtl.put("AMT", amt);
                 dtl.put("CUR", amtUSD);
                 amt=amt.replaceAll(",", "").trim();
                 amtUSD=amtUSD.trim();
                BigDecimal amtB=  new BigDecimal(amt);
                 BigDecimal amtUB=  new BigDecimal(amtUSD);
                if(typ.equals("P")){
                    payLst.add(dtl);
                   amtP = amtP.add(amtB);
                    amtUP = amtUP.add(amtUB);
                }else{
                    RecLst.add(dtl);
                    amtR = amtR.add(amtB);
                    amtUR = amtUR.add(amtUB);
                }
             }
            dtlMap.put("PAYLST", payLst);
            dtlMap.put("RECLST", RecLst);
            dtlMap.put("PAYTTL", String.valueOf(amtP));
            dtlMap.put("RECTTL", String.valueOf(amtR));
            dtlMap.put("PAYUTTL", String.valueOf(amtUP));
            dtlMap.put("RECUTTL", String.valueOf(amtUR));
            rs.close();
            psmt.close();
            udf.setValue("dtefr", dfr);
            udf.setValue("dteto", dto);
            session.setAttribute("ESDTLMAP", dtlMap);
            return am.findForward("Estimated");   
        }
    }
    
    public ActionForward EstimatedDuesExcel(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            
            HashMap dtlMap = (HashMap)session.getAttribute("ESDTLMAP");
            HSSFWorkbook hwb = null;
            ExcelUtilObj xlUtil=new ExcelUtilObj();
            xlUtil.init(db, util, info, gtMgr);
            hwb =  xlUtil.EstimatedDuesExcel(req, dtlMap);
            OutputStream out = res.getOutputStream();
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "EstimatedDues"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
            hwb.write(out);
            out.flush();
            out.close();
            
          
        return null;
        }
    }
    public ActionForward TransDtlExcel(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            
             HSSFWorkbook hwb = null;
            ExcelUtilObj xlUtil=new ExcelUtilObj();
            xlUtil.init(db, util, info, gtMgr);
            hwb =  xlUtil.TransDtlExcel(req);
            OutputStream out = res.getOutputStream();
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "EstimatedDues"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
            hwb.write(out);
            out.flush();
            out.close();
            
          
        return null;
        }
    }
    
    public ActionForward TransDtlDteExcel(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            
            HSSFWorkbook hwb = null;
            ExcelUtilObj xlUtil=new ExcelUtilObj();
            xlUtil.init(db, util, info, gtMgr);
            hwb =  xlUtil.TransDtlDTEExcel(req);
            OutputStream out = res.getOutputStream();
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "TransDtlDteExcel"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
            hwb.write(out);
            out.flush();
            out.close();
            
          
        return null;
        }
    }
    
    public ActionForward OutstandingExcel(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            
            HashMap dtlMap = (HashMap)session.getAttribute("OUTDTLMAP");
            HSSFWorkbook hwb = null;
            ExcelUtilObj xlUtil=new ExcelUtilObj();
            xlUtil.init(db, util, info, gtMgr);
            hwb =  xlUtil.OutstandingExcel(req, dtlMap);
            OutputStream out = res.getOutputStream();
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "OutstandingExcel"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
            hwb.write(out);
            out.flush();
            out.close();
            
          
        return null;
        }
    }
    
    public ActionForward OutstandingTransExcel(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            
            HashMap dtlMap = (HashMap)session.getAttribute("OUTTANDTLMAP");
            HSSFWorkbook hwb = null;
            ExcelUtilObj xlUtil=new ExcelUtilObj();
            xlUtil.init(db, util, info, gtMgr);
            hwb =  xlUtil.OutstandingTransExcel(req, dtlMap);
            OutputStream out = res.getOutputStream();
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "OutstandingExcel"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
            hwb.write(out);
            out.flush();
            out.close();
            
          
        return null;
        }
    }
    public ActionForward TransDtlLoad(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            ReceiptTransReportForm udf = (ReceiptTransReportForm)form;
            udf.resetAll();
            ArrayList byrList = new ArrayList();
            String gMst ="select * from gl_mst where entry_point <> 'DFLT' order by nm";
            ArrayList rsLst = db.execSqlLst("data sql", gMst, new ArrayList());
            PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
               ByrDao byr = new ByrDao();
                byr.setByrIdn(rs.getInt("idn"));
                byr.setByrNme(rs.getString("nm"));
                byrList.add(byr);
            }
            udf.setPartyList(byrList);
            rs.close();
            psmt.close();
             
            return am.findForward("TransLoad");   
        }
    }
    public ActionForward TransDtlDteLoad(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            ReceiptTransReportForm udf = (ReceiptTransReportForm)form;
            udf.resetAll();
            ArrayList byrList = new ArrayList();
            String gMst ="select * from gl_mst where entry_point <> 'DFLT' order by nm";
            ArrayList rsLst = db.execSqlLst("data sql", gMst, new ArrayList());
            PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
               ByrDao byr = new ByrDao();
                byr.setByrIdn(rs.getInt("idn"));
                byr.setByrNme(rs.getString("nm"));
                byrList.add(byr);
            }
            udf.setPartyList(byrList);
            rs.close();
            psmt.close();
             
            return am.findForward("TransDtlDteLoad");   
        }
    }
    public ActionForward GlMstLoad(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            ReceiptTransReportForm udf = (ReceiptTransReportForm)form;
            udf.resetAll();
            ArrayList GlMstList = new ArrayList();
            String glMstSql="select idn,nm,cd , mbl,eml from gl_mst where entry_point <> 'DFLT' and stt='A' and vld_to is null order by nm";
            ArrayList rsLst = db.execSqlLst("data sql", glMstSql,new ArrayList());
            PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
                HashMap glMst = new HashMap();
                glMst.put("IDN", rs.getString("idn"));
                glMst.put("NME", rs.getString("nm"));
                glMst.put("CD", rs.getString("cd"));
                glMst.put("MBL", rs.getString("mbl"));
                glMst.put("EML", rs.getString("eml"));
                GlMstList.add(glMst);
            }
            rs.close();
            psmt.close();
            ArrayList TransFullList = new ArrayList();
            String tranFullSql="select gl_idn from GL_TRNS_SMRY where full_yn='Y'";
             rsLst = db.execSqlLst("data sql", tranFullSql,new ArrayList());
             psmt = (PreparedStatement)rsLst.get(0);
             rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
                TransFullList.add(rs.getString("gl_idn"));
            }
            rs.close();
            psmt.close();
            
            req.setAttribute("GlMstList", GlMstList);
            req.setAttribute("TransFullList", TransFullList);
        }
        return am.findForward("GlMstLoad");   
    }
    public ActionForward TransDtl(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            ReceiptTransReportForm udf = (ReceiptTransReportForm)form;
            String update = util.nvl((String)udf.getValue("update"));
            String delete = util.nvl((String)udf.getValue("delete"));
            Enumeration reqNme = req.getParameterNames();
            if(!update.equals("") || !delete.equals("")){
             while (reqNme.hasMoreElements()) {
                    String paramNm = (String) reqNme.nextElement();
                    if(paramNm.indexOf("cb_trns_") > -1) {
                        String idn = req.getParameter(paramNm);
                        String xrt = req.getParameter("XRT_"+idn);
                        String updateENTXrt=" update gl_entries set xrt = ? where ref_idn=?";
                        ArrayList ary = new ArrayList();
                        ary.add(xrt);
                        ary.add(idn);
                        int ct = db.execUpd("updateXrt", updateENTXrt, ary);
                        
                        String updatesmryXrt=" update gl_trns_smry set xrt = ? where ref_idn=?";
                         ary = new ArrayList();
                        ary.add(xrt);
                       ary.add(idn);
                      ct = db.execUpd("updateXrt", updatesmryXrt, ary);
                  }
                 
                     if(paramNm.indexOf("cb_del_") > -1) {
                         String idn = req.getParameter(paramNm);
                     
                         String updateENTXrt=" delete from gl_entries  where ent_seq=?";
                         ArrayList ary = new ArrayList();
                       
                         ary.add(idn);
                         int ct = db.execUpd("updateXrt", updateENTXrt, ary);
                         
                         }
                 
                 
                 
                 }}
            udf.setValue("update", "");
            udf.setValue("delete", "");
            BigDecimal amtC =  new BigDecimal(0);
            BigDecimal amtD =  new BigDecimal(0);
            BigDecimal amtUC =  new BigDecimal(0);
            BigDecimal amtUD =  new BigDecimal(0);
            String partyIdn = (String)udf.getValue("byrIdn");
            String frmDte = (String)udf.getValue("dtefr");
            String toDte = (String)udf.getValue("dteto");
            String dteStr="";
            String dteStr1="";  
            if(!frmDte.equals("") && !toDte.equals("")){
                dteStr=" and trunc(g1.t_dte) between to_date('"+frmDte+"' , 'dd-mm-yyyy') and to_date('"+toDte+"' , 'dd-mm-yyyy') ";
                dteStr1=" and trunc(e.t_dte) between to_date('"+frmDte+"' , 'dd-mm-yyyy') and to_date('"+toDte+"' , 'dd-mm-yyyy')  ";  
            }
            ArrayList transList = new ArrayList();
            ArrayList mainTransList = new ArrayList();
            String transSql=
            "select e.ent_seq,to_char(trunc(abs(e.amt),2),'99,99,99,99,99,990.00')  amt,to_char(trunc(abs(e.amt/e.xrt),2),'99999999990.00')  amtUSD\n" + 
            ", m.nm, to_char(e.t_dte,'dd-MON-yyyy') t_dte, e.typ, e.sub_typ, e.ref_typ, e.ref_idn,to_char(e.due_dte,'dd-MON-yyyy') due_dte \n" + 
            ", e.fctr, e.xrt,e.idn ,'M' flg ,e.cntidn  \n" + 
            "from gl_entries e, gl_mst m \n" + 
            "where 1 = 1 and exists (select 1 from gl_entries g1 where 1 = 1 \n" + 
            " and g1.ref_typ = e.ref_typ and g1.ref_idn = e.ref_idn   "+dteStr+") \n" + 
            "and e.gl_idn = m.idn and  (m.entry_point <> 'DFLT' or m.typ in ('A','B','C'))\n" + 
            "and e.gl_idn = ?\n" + 
            "and e.typ in ('PURCHASE','SOLD')\n" + 
            "UNION\n" + 
            "select e.ent_seq,to_char(trunc(abs(e.amt),2),'99,99,99,99,99,990.00')  amt,to_char(trunc(abs(e.amt/e.xrt),2),'99999999990.00')  amtUSD\n" + 
            ", m.nm||decode(greatest(m.idn, 1000001), 1000001, '', '(TP)') nm\n" + 
            ", to_char(e.t_dte,'dd-MON-yyyy') t_dte, e.typ, e.sub_typ\n" + 
            ", e.ref_typ, e.ref_idn,to_char(e.due_dte,'dd-MON-yyyy') due_dte \n" + 
            ", decode(greatest(m.idn, 1000001), 1000001, e.fctr, e.fctr*-1) fctr ,e.xrt,e.idn,'S' flg,e.cntidn\n" + 
            "from gl_entries e, gl_mst m \n" + 
            "where exists (select 1 from gl_entries g where g.gl_idn= ? and e.ent_seq = g.ent_seq)\n" + 
            "and e.gl_idn = m.idn and  (m.entry_point <> 'DFLT' or m.typ in ('A','B','C'))\n" + 
            "and e.gl_idn <> ? "+dteStr1+" \n" + 
            "order by 1,2 desc ";
            ArrayList ary = new ArrayList();
            ary.add(partyIdn);
            ary.add(partyIdn);
            ary.add(partyIdn);
            ArrayList rsLst = db.execSqlLst("data sql", transSql,ary);
            PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
                String flg = rs.getString("flg");
                HashMap dtlMap = new HashMap();
                dtlMap.put("NME", rs.getString("nm"));
                dtlMap.put("T_DTE", rs.getString("t_dte"));
                dtlMap.put("TYP", rs.getString("typ"));
                dtlMap.put("SUBTYP", rs.getString("sub_typ"));
                dtlMap.put("REF_TYP", rs.getString("ref_typ"));
                dtlMap.put("REF_IDN", rs.getString("ref_idn"));
                dtlMap.put("DUE_DTE", rs.getString("due_dte"));
                dtlMap.put("ENT_SEQ", rs.getString("ent_seq"));
                dtlMap.put("XRT", rs.getString("xrt"));
                dtlMap.put("IDN", rs.getString("idn"));
                dtlMap.put("IDN", rs.getString("idn"));
                dtlMap.put("CNTIDN", rs.getString("cntidn"));
                   dtlMap.put("FLG", flg);
                String fctr = rs.getString("FCTR");
                String amt =util.nvl(rs.getString("amt"),"0");
                String amtUSD =util.nvl(rs.getString("amtUSD"),"0");
                BigDecimal amtB=  new BigDecimal(amt.replaceAll(",", "").trim());
                BigDecimal amtUB=  new BigDecimal(amtUSD.replaceAll(",", "").trim());
                if(fctr.equals("1")){
                dtlMap.put("CRAMT",amt);
                dtlMap.put("CRUAMT",amtUSD);

                if(!flg.equals("M")){
                amtC = amtC.add(amtB);
                amtUC = amtUC.add(amtUB);
                }
                }else{
                dtlMap.put("DBAMT",amt);
                dtlMap.put("DBUAMT",amtUSD);
                 if(!flg.equals("M")){
                amtD = amtD.add(amtB);
                amtUD = amtUD.add(amtUB);
                }
                }
                if(flg.equals("M"))
                 mainTransList.add(dtlMap);  
                else    
                transList.add(dtlMap);
            }
            session.setAttribute("TRANSLIST", transList);
            session.setAttribute("MAINTRANSLIST", mainTransList);
            session.setAttribute("TTLCR", amtC.toString());
            session.setAttribute("TTLDB", amtD.toString());
            session.setAttribute("TTLUCR", amtUC.toString());
            session.setAttribute("TTLUDB", amtUD.toString());
            req.setAttribute("view","yes");
            rs.close();
            psmt.close();
            
            return am.findForward("TransDtl");  
        }
    }
    
    public ActionForward TransDtlDteWise(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            ReceiptTransReportForm udf = (ReceiptTransReportForm)form;
          
            BigDecimal amtC =  new BigDecimal(0);
            BigDecimal amtD =  new BigDecimal(0);
            BigDecimal amtUC =  new BigDecimal(0);
            BigDecimal amtUD =  new BigDecimal(0);
            String partyIdn = (String)udf.getValue("byrIdn");
            String frmDte = (String)udf.getValue("dtefr");
            String toDte = (String)udf.getValue("dteto");
            String dteStr="";
            String dteStr1="";  
            if(!frmDte.equals("") && !toDte.equals("")){
                dteStr=" and trunc(g1.t_dte) between to_date('"+frmDte+"' , 'dd-mm-yyyy') and to_date('"+toDte+"' , 'dd-mm-yyyy') ";
                dteStr1=" and trunc(e.t_dte) between to_date('"+frmDte+"' , 'dd-mm-yyyy') and to_date('"+toDte+"' , 'dd-mm-yyyy')  ";  
            }
            ArrayList transList = new ArrayList();
            String transSql=
            "select e.ent_seq,to_char(trunc(abs(e.amt),2),'99,99,99,99,99,990.00')  amt,to_char(trunc(abs(e.amt/e.xrt),2),'99999999990.00')  amtUSD\n" + 
            ", m.nm, to_char(e.t_dte,'dd-MON-yyyy HH24:mm:ss') t_dte, e.typ, e.sub_typ, e.ref_typ, e.ref_idn,to_char(e.due_dte,'dd-MON-yyyy') due_dte \n" + 
            ", e.fctr, e.xrt,e.idn,e.cntidn ,'M' flg,e.flg1 , e.rmk \n" + 
            "from gl_entries e, gl_mst m \n" + 
            "where 1 = 1 and exists (select 1 from gl_entries g1 where 1 = 1 \n" + 
            " and g1.ref_typ = e.ref_typ and g1.ref_idn = e.ref_idn   "+dteStr+") \n" + 
            "and e.gl_idn = m.idn and  (m.entry_point <> 'DFLT' or m.typ in ('A','B','C'))\n" + 
            "and e.gl_idn = ?\n" + 
            "and e.typ in ('PURCHASE','SOLD')\n" + 
            "UNION\n" + 
            "select e.ent_seq,to_char(trunc(abs(e.amt),2),'99,99,99,99,99,990.00')  amt,to_char(trunc(abs(e.amt/e.xrt),2),'99999999990.00')  amtUSD\n" + 
            ", m.nm||decode(greatest(m.idn, 1000001), 1000001, '', '(TP)') nm\n" + 
            ", to_char(e.t_dte,'dd-MON-yyyy') t_dte, e.typ, e.sub_typ\n" + 
            ", e.ref_typ, e.ref_idn,to_char(e.due_dte,'dd-MON-yyyy') due_dte \n" + 
            ", decode(greatest(m.idn, 1000001), 1000001, e.fctr, e.fctr*-1) fctr ,e.xrt,e.idn,e.cntidn ,'S' flg,e.flg1 , e.rmk\n" + 
            "from gl_entries e, gl_mst m \n" + 
            "where exists (select 1 from gl_entries g where g.gl_idn= ? and e.ent_seq = g.ent_seq)\n" + 
            "and e.gl_idn = m.idn and  (m.entry_point <> 'DFLT' or m.typ in ('A','B','C'))\n" + 
            "and e.gl_idn <> ? "+dteStr1+" \n" + 
            "order by 1,2 desc ";
            ArrayList ary = new ArrayList();
            ary.add(partyIdn);
            ary.add(partyIdn);
            ary.add(partyIdn);
            ArrayList rsLst = db.execSqlLst("data sql", transSql,ary);
            PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
                HashMap dtlMap = new HashMap();
                dtlMap.put("NME", rs.getString("nm"));
                dtlMap.put("T_DTE", rs.getString("t_dte"));
                dtlMap.put("TYP", rs.getString("typ"));
                dtlMap.put("SUBTYP", rs.getString("sub_typ"));
                dtlMap.put("REF_TYP", rs.getString("ref_typ"));
                dtlMap.put("REF_IDN", rs.getString("ref_idn"));
                dtlMap.put("CNTIDN", rs.getString("cntidn"));
                dtlMap.put("DUE_DTE", rs.getString("due_dte"));
                dtlMap.put("ENT_SEQ", rs.getString("ent_seq"));
                dtlMap.put("XRT", rs.getString("xrt"));
                dtlMap.put("IDN", rs.getString("idn"));
                dtlMap.put("CFM", rs.getString("flg1"));
                dtlMap.put("RMK", rs.getString("rmk"));
                
                String fctr = rs.getString("FCTR");
                String amt =util.nvl(rs.getString("amt"),"0");
                String amtUSD =util.nvl(rs.getString("amtUSD"),"0");
                BigDecimal amtB=  new BigDecimal(amt.replaceAll(",", "").trim());
                BigDecimal amtUB=  new BigDecimal(amtUSD.replaceAll(",", "").trim());
                if(fctr.equals("1")){
                dtlMap.put("CRAMT",amt);
                dtlMap.put("CRUAMT",amtUSD);
                amtC = amtC.add(amtB);
                amtUC = amtUC.add(amtUB);
                
                }else{
                dtlMap.put("DBAMT",amt);
                dtlMap.put("DBUAMT",amtUSD);
                amtD = amtD.add(amtB);
                amtUD = amtUD.add(amtUB);
                
                }
                
                transList.add(dtlMap);
            }
            session.setAttribute("TRANSLISTDTE", transList);
            session.setAttribute("TTLCRD", amtC.toString());
            session.setAttribute("TTLDBD", amtD.toString());
            session.setAttribute("TTLUCRD", amtUC.toString());
            session.setAttribute("TTLUDBD", amtUD.toString());
            req.setAttribute("view","yes");
            rs.close();
            psmt.close();
            
            return am.findForward("TransDtlDteWise");  
        }
    }
    public ActionForward RefWiseLoad(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            ReceiptTransReportForm udf = (ReceiptTransReportForm)form;
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                 HashMap pageDtl=(HashMap)allPageDtl.get("PAY_RECEIPT");
                 if(pageDtl==null || pageDtl.size()==0){
                 pageDtl=new HashMap();
                 pageDtl=util.pagedef("PAY_RECEIPT");
                 allPageDtl.put("PAY_RECEIPT",pageDtl);
                 }
                 
                 
                 udf.resetAll();
                 info.setPageDetails(allPageDtl);  
                 ArrayList typList = new ArrayList();
                 ArrayList pageList=(ArrayList)pageDtl.get("REC_TYP");
                 if(pageList!=null && pageList.size() >0){
                 for(int j=0;j<pageList.size();j++){
                           HashMap  pageDtlMap=(HashMap)pageList.get(j);
                            String fld_nme=(String)pageDtlMap.get("fld_nme");
                            String  fld_ttl=(String)pageDtlMap.get("fld_ttl");
                            String fld_typ=(String)pageDtlMap.get("fld_typ");
                            String  lov_qry=(String)pageDtlMap.get("lov_qry");
                            String dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
                     
                            ObjBean objBn = new ObjBean();
                            objBn.setDsc(fld_ttl);
                            objBn.setNme(fld_nme);
                            typList.add(objBn);
                      }}
                 udf.setInvTypLst(typList);
            return am.findForward("RefWiseLoad");  
        }
    }
    public ActionForward RefWise(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            ReceiptTransReportForm udf = (ReceiptTransReportForm)form;
            String refIdn = (String)udf.getValue("refIdn");
            String refTyp = (String)udf.getValue("refTyp");
            ArrayList transList = new ArrayList();
            ArrayList mainTransList = new ArrayList();
            int cnt=0;
           BigDecimal amtC =  new BigDecimal(0);
           BigDecimal amtD =  new BigDecimal(0);
           
            String refWise="select e.ent_seq,to_char(trunc(abs(e.amt),2),'99,99,99,99,99,990.00')  amt,to_char(trunc(abs(e.amt/e.xrt),2),'99999999990.00')  amtUSD\n" + 
            ", m.nm, to_char(e.t_dte,'dd-MON-yyyy') t_dte, e.typ, e.sub_typ, e.ref_typ, e.ref_idn,to_char(e.due_dte,'dd-MON-yyyy') due_dte \n" + 
            ", e.fctr, e.xrt,e.idn\n" + 
            "from gl_entries e, gl_mst m \n" + 
            "where 1 = 1\n" + 
            "and exists (select 1 from gl_entries g1 where g1.ent_seq = e.ent_seq and g1.ref_typ = ? and g1.ref_idn = ?) \n" + 
            "and e.gl_idn = m.idn and  (m.entry_point <> 'DFLT' or m.typ in ('A','B','C'))\n" + 
            "order by 1,2 desc ";
            ArrayList ary = new ArrayList();
            ary.add(refTyp);
            ary.add(refIdn);
            ArrayList rsLst = db.execSqlLst("data sql", refWise,ary);
            PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
               
                HashMap dtlMap = new HashMap();
                dtlMap.put("NME", rs.getString("nm"));
                dtlMap.put("T_DTE", rs.getString("t_dte"));
                dtlMap.put("TYP", rs.getString("typ"));
                dtlMap.put("SUBTYP", rs.getString("sub_typ"));
                dtlMap.put("REF_TYP", rs.getString("ref_typ"));
                dtlMap.put("REF_IDN", rs.getString("ref_idn"));
                dtlMap.put("DUE_DTE", rs.getString("due_dte"));
                dtlMap.put("ENT_SEQ", rs.getString("ent_seq"));
                dtlMap.put("XRT", rs.getString("xrt"));
                dtlMap.put("IDN", rs.getString("idn"));
                dtlMap.put("AMTUSD", rs.getString("amtUSD"));
                String fctr = rs.getString("FCTR");
                String amt =util.nvl(rs.getString("amt"),"0");
                BigDecimal amtB = new BigDecimal(amt.replaceAll(",", "").trim());
                if(fctr.equals("1")){
                dtlMap.put("CRAMT",amt);
                    if(cnt!=0)
                amtC = amtC.add(amtB);
                }else{
                dtlMap.put("DBAMT",amt);
                    if(cnt!=0)
                amtD = amtD.add(amtB);
                }
                if(cnt==0)
                 mainTransList.add(dtlMap);  
                else    
                transList.add(dtlMap);
                cnt++;
            }
            req.setAttribute("TRANSLIST", transList);
            req.setAttribute("MAINTRANSLIST", mainTransList);
            req.setAttribute("TTLCR",amtC.toString());
            req.setAttribute("TTLDB",amtD.toString());
            req.setAttribute("view","yes");
            rs.close();
            psmt.close();
            return am.findForward("RefWiseLoad");  
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
                util.updAccessLog(req,res,"Receipt Trns", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Receipt Trns", "init");
            }
            }
            return rtnPg;
            }
    
}
