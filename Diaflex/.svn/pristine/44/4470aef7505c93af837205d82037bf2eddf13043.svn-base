package ft.com.report;

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
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.PacketPrintForm;

import ft.com.marketing.SearchQuery;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AssortLabPending extends DispatchAction {
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
         util.updAccessLog(req,res,"Assort Lab Pending", "load start");
         AssortLabPendingForm udf = (AssortLabPendingForm)af;
        udf.resetAll();
        HashMap params = new HashMap();
        AssortIssueInterface assortInt = new AssortIssueImpl();
        ArrayList mprcList = assortInt.getPrc(req,res);
        udf.setMprcList(mprcList);
        String grp = util.nvl(req.getParameter("grp"));
//        if(grp.equals(""))
//        grp="'ASRT','LAB'";
//        ArrayList empList = getEmp(req,res);
//        udf.setEmpList(empList);
             params.put("grp", grp);
         ArrayList pktDtlList = pktDtl(req,params);
        //udf.setPktDtlList(pktDtlList);
         req.setAttribute("PktDtlList", pktDtlList);
         HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
         HashMap pageDtl=(HashMap)allPageDtl.get("ASSORT_LAB");
         if(pageDtl==null || pageDtl.size()==0){
         pageDtl=new HashMap();
         pageDtl=util.pagedef("ASSORT_LAB");
         allPageDtl.put("ASSORT_LAB",pageDtl);
         }
        udf.setValue("grp", grp); 
         info.setPageDetails(allPageDtl);
             util.updAccessLog(req,res,"Assort Lab Pending", "load end");
        return am.findForward("load");
         }
     }
    public ActionForward mailexcel(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             util.updAccessLog(req,res,"Assort Lab Pending", "mailexcel start");
             AssortLabPendingForm udf = (AssortLabPendingForm)af;
             SearchQuery srchQuery = new SearchQuery();
             HSSFWorkbook hwb = (HSSFWorkbook)session.getAttribute("hwb");
             String fileNm = (String)session.getAttribute("fileNm");
             srchQuery.MailExcelmass(hwb, fileNm, req,res);
             req.setAttribute("load","memoreport");
                 util.updAccessLog(req,res,"Assort Lab Pending", "mailexcel end");
             return am.findForward("view");
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
             util.updAccessLog(req,res,"Assort Lab Pending", "save start");
         AssortLabPendingForm udf = (AssortLabPendingForm)af;
         String empIdn = (String)udf.getValue("empIdn");
         String prcIdn = (String)udf.getValue("prcIdn");
         String grp = (String)udf.getValue("grp");
         HashMap params = new HashMap();
         params.put("empIdn", empIdn);
         params.put("prcIdn",prcIdn);
         params.put("grp",grp);
         params.put("frmdte", util.nvl((String)udf.getValue("frmdte")));
         params.put("todte", util.nvl((String)udf.getValue("todte")));
         ArrayList pktDtlList = pktDtl(req,params);
         udf.setPktDtlList(pktDtlList);
             util.updAccessLog(req,res,"Assort Lab Pending", "save end");
         return am.findForward("load");
         }
     }
    public ArrayList pktDtl(HttpServletRequest req,HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap dbSysInfo = info.getDmbsInfoLst();
        String empId = util.nvl((String)params.get("empIdn"));
        String prcId = util.nvl((String)params.get("prcIdn"),"0");
        String Frmdte  = util.nvl((String)params.get("frmdte"));
        String todte  = util.nvl((String)params.get("todte"));
        String grp= util.nvl((String)params.get("grp"));
        String cnt=(String)dbSysInfo.get("CNT");
        ArrayList ary = new ArrayList();
        int count=0;
        double ttlcts = 0 ;
        BigDecimal ttlBigqty = new BigDecimal(ttlcts);
        BigDecimal ttlBigcts = new BigDecimal(ttlcts);
        BigDecimal ttlBigvlu = new BigDecimal(ttlcts);
        HashMap gtTotalMap = new HashMap();
        String sql = "select a.iss_id ,  to_char(a.iss_dt, 'dd-Mon-rrrr HH24:mi:ss') iss_dt , a.prc , a.emp , a.qty , a.cts , a.vlu from ISS_PNDG_V a , mprc b where 1=1 and a.prc_id = b.idn " ;
            if(!empId.equals("0") && !empId.equals("")){
               sql+= " and a.emp_id = ? ";
               ary.add(empId);
            }
            if(!prcId.equals("0") && !prcId.equals("")){
                sql+= " and a.prc_id = ? ";
                ary.add(prcId);
            }
            if(cnt.equals("ag") && !prcId.equals("0")){
                sql+= " and a.prc_id <> ? ";
                ary.add("984");
            }
            if(!Frmdte.equals("") && !todte.equals("")){
                sql+= " and trunc(a.iss_dt) between to_date('"+Frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy')   ";
                
            }
            if(!grp.equals("")){
                sql+= " and b.grp in ("+grp+")";
          }
           sql+= " order by a.prc_id , a.iss_id ";

        ArrayList outLst = db.execSqlLst("sql", sql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        ArrayList pktDtlList = new ArrayList();
        try {
            while (rs.next()) {
                count++;
                HashMap pktDtl = new HashMap();
                String cts =util.nvl(rs.getString("cts"),"0").trim();
                String vlu =util.nvl(rs.getString("vlu"),"0").trim();
                String qty=util.nvl(rs.getString("qty"),"0").trim();
                pktDtl.put("Count", String.valueOf(count));
                pktDtl.put("issIdn", util.nvl(rs.getString("iss_id")));
                pktDtl.put("issDte", util.nvl(rs.getString("iss_dt")));
                pktDtl.put("prc", util.nvl(rs.getString("prc")));
                pktDtl.put("emp", util.nvl(rs.getString("emp")));
                pktDtl.put("qty", util.nvl(rs.getString("qty")));
                pktDtl.put("cts", cts);
                pktDtl.put("vlu", vlu);
                pktDtl.put("requrl", info.getReqUrl());
                pktDtl.put("homeDir", dbSysInfo.get("HOME_DIR"));
                pktDtl.put("cnt", dbSysInfo.get("CNT"));
                pktDtl.put("webDir", dbSysInfo.get("REP_URL"));
                pktDtl.put("info",info);
                pktDtlList.add(pktDtl);
                ttlBigqty =  ttlBigqty.add(new BigDecimal(qty));
                ttlBigcts =  ttlBigcts.add(new BigDecimal(cts));
                ttlBigvlu =  ttlBigvlu.add(new BigDecimal(vlu));
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        gtTotalMap.put("qty", util.nvl(String.valueOf(ttlBigqty)));
        gtTotalMap.put("cts", util.nvl(String.valueOf(ttlBigcts)));
        gtTotalMap.put("vlu", util.nvl(String.valueOf(ttlBigvlu)));
        req.setAttribute("totalMap", gtTotalMap);
        
        return pktDtlList;
    }
    public ActionForward loadPkt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Assort Lab Pending", "loadPkt start");
        AssortLabPendingForm udf = (AssortLabPendingForm)af;
        GenericInterface genericInt = new GenericImpl();
        db.execUpd("delete from gt", "delete from gt_srch_rslt", new ArrayList());
        ArrayList  ary = new ArrayList();
        String issIdn = req.getParameter("issueIdn");
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        ArrayList prpDspBlocked = info.getPageblockList();
        String srchRefQ = 
        " Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk ) " + 
       " select  a.pkt_ty, a.idn, a.vnm,  a.dte, a.stt , 'AV' , a.qty , a.cts , b.iss_id , a.tfl3 ";
        if(!cnt.equals("svk")){
        srchRefQ=srchRefQ+" from mstk a , iss_rtn_dtl b where a.idn = b.iss_stk_idn and b.iss_id = ? and b.stt='IS' ";
        }else{
        srchRefQ=srchRefQ+" from mstk a , iss_rtn_dtl b where a.idn = b.iss_stk_idn and b.iss_id = ? and b.stt <> 'CL' "+
        " and a.stt in ('LB_RS','LB_CF','LB_CFRS','LB_PRI','LB_IS') ";    
        }
                
        ary = new ArrayList();
        ary.add(issIdn);
        int ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("AS_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
         
        ArrayList stockList = SearchResult(req,res);
        ArrayList itemHdr = new ArrayList();
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "asViewLst", "AS_VIEW");
        itemHdr.add("vnm");
        for(int m=0;m<vwPrpLst.size();m++){
        String lprp = (String)vwPrpLst.get(m);
        if(prpDspBlocked.contains(lprp)){
        }else{
        itemHdr.add(lprp);
        }}
        session.setAttribute("itemHdr", itemHdr);
        session.setAttribute("pktList", stockList);
            req.setAttribute("grp", udf.getValue("grp"));
            util.updAccessLog(req,res,"Assort Lab Pending", "loadPkt end");
        return am.findForward("loadPkt");
        }
    }
    
    
    public ActionForward loadPktFnlAssortdata(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Assort Lab Pending", "loadPkt start");
            AssortLabPendingForm udf = (AssortLabPendingForm)af;
            GenericInterface genericInt = new GenericImpl();
            ArrayList pktList = new ArrayList();
            ArrayList itemHdr = new ArrayList();
            ArrayList ary = new ArrayList();
            String issIdn = req.getParameter("issueIdn");
            String lab = util.nvl(req.getParameter("lab"));
            HashMap dbinfo = info.getDmbsInfoLst();
            String fnxl = util.nvl((String)dbinfo.get("FN_XL"),"Y");
            ArrayList prpDspBlocked = info.getPageblockList();
            ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "ISSUEFNLASSORT_VIEW","ISSUEFNLASSORT_VIEW");
            ArrayList assrtVwPrpLst = genericInt.genericPrprVw(req, res, "FNL_ASSORTVW","FNL_ASSORTVW");
            String grp="";
            String prcGrp = "select b.grp from iss_rtn a , mprc b where a.prc_id=b.idn and a.iss_id= ?";
             ary =  new ArrayList();
            ary.add(issIdn);
            ResultSet rsset = db.execSql("prcGrp", prcGrp, ary);
            while(rsset.next()){
                grp=util.nvl(rsset.getString("grp"));
            }
            rsset.close();
            int sr=1;
            ExcelUtil xlUtil = new ExcelUtil();
            xlUtil.init(db, util, info);
            OutputStream out1 = res.getOutputStream();
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "PacketDtlReportExcel"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
            String colval = (String)dbinfo.get("COL");
            String clrval = (String)dbinfo.get("CLR");
            String cutval = (String)dbinfo.get("CUT");
            String polval = (String)dbinfo.get("POL");
            String symval = (String)dbinfo.get("SYM");
            String flval = (String)dbinfo.get("FL");
            
            itemHdr.add("Sr No");
            itemHdr.add("View");
            itemHdr.add("CRTWT");
           if(assrtVwPrpLst.contains("RTE"))
               itemHdr.add("F RTE");
            itemHdr.add("RATE");
            if(assrtVwPrpLst.contains("RAP_RTE"))
                itemHdr.add("F RAP_RTE");
            itemHdr.add("RAP_RTE");
            if(assrtVwPrpLst.contains("RAP_DIS"))
                itemHdr.add("F RAP_DIS");
            itemHdr.add("RAP_DIS");
            itemHdr.add("AMOUNT");
            itemHdr.add("RAP_VLU");
            itemHdr.add("STATUS");
            itemHdr.add("VNM");
            for (int i = 0; i < vwPrpLst.size(); i++) {
            String lprp=(String)vwPrpLst.get(i);
            String fld = "prp_";
            int j = i + 1;
            if (j < 10)
            fld += "00" + j;
            else if (j < 100)
            fld += "0" + j;
            else if (j > 100)
            fld += j;
            if(prpDspBlocked.contains(lprp)){
            }else{
                if(assrtVwPrpLst.contains(lprp))
                    itemHdr.add("F "+lprp);
            itemHdr.add(lprp);
            }}            
            ArrayList param = new ArrayList();
            String mprpStr = "";
            String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||Upper(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1')) str From Rep_Prp Rp Where rp.MDL = ? and flg ='Y' order by srt " ;
            param = new ArrayList();
            param.add("ISSUEFNLASSORT_VIEW");

            ArrayList outLst = db.execSqlLst("mprp ", mdlPrpsQ , param);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()) {
            String val = util.nvl((String)rs.getString("str"));
            mprpStr = mprpStr +" "+val;
            }
            rs.close(); pst.close();
            
            int shapeIndx = vwPrpLst.indexOf("SHAPE")+1;
            int clrIndx = vwPrpLst.indexOf("CLR")+1;
            String shPrp = "prp_00"+shapeIndx;
            String clrPrp = "srt_00"+clrIndx;
            ArrayList fancyList = new ArrayList();
            fancyList.add("OVAL");
            fancyList.add("PEAR");
            fancyList.add("HEART");
            fancyList.add("MARQUISE");
            fancyList.add("EMERALD");
            ArrayList certLabLst = new ArrayList();
            ArrayList newfancyList = new ArrayList();
            ArrayList newfancyListSrt = new ArrayList();
            String delQ = " Delete from gt_srch_rslt ";
            int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            
            String query ="insert into gt_srch_rslt (sk1,stt,vnm,quot,rap_rte,stk_idn,flg,cts,cert_lab)" +
                          " select b.sk1,b.stt,b.vnm ,nvl(b.upr, b.cmp) ,b.rap_rte,b.idn,'Z',b.cts,b.cert_lab from  mstk b ,iss_rtn_dtl ir\n" + 
                          " where  b.pkt_ty in ('NR','SMX') and b.idn=ir.iss_stk_idn and ir.stt='IS' and ir.iss_id=?";
            ary = new ArrayList();
            ary.add(issIdn);
        
             ct = db.execUpd(" Srch Prp ", query, ary);
            
            String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
            ary = new ArrayList();
            ary.add("ISSUEFNLASSORT_VIEW");
            ct = db.execCall(" Srch Prp ", pktPrp, ary);
            
            
            String srchQ = "select b.stk_idn,b.cert_lab, b.quot rate, b.rap_rte , to_char(trunc(b.cts,2) * b.rap_rte, '99999999990.00') rapVlu , to_char(trunc(b.cts,2) *  b.quot, '99999999990.00') vlu ," + 
                           " decode(b.rap_rte, 1, '', trunc((( b.quot/greatest(b.rap_rte,1))*100)-100,2)) rapdis , b.cts , b.vnm , b.stt  ";
                          
            for (int i = 0; i < vwPrpLst.size(); i++) {
                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                    fld += "00" + j;
                else if (j < 100)
                    fld += "0" + j;
                else if (j > 100)
                    fld += j;

               

                srchQ += ", " + fld;
               
             }

            
            String rsltQ = srchQ + " from gt_srch_rslt b where flg ='Z' order by sk1 , cts " ;
                

             outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
            String stk_idn=util.nvl((String)rs.getString("stk_idn"));
                String rte = util.nvl((String)rs.getString("rate"));
                String rap_rte = util.nvl((String)rs.getString("rap_rte"));
            HashMap pktPrpMap = new HashMap();
            pktPrpMap.put("Sr No", String.valueOf(sr++));
                pktPrpMap.put("STKIDN", stk_idn);
            pktPrpMap.put("VNM", util.nvl((String)rs.getString("vnm")));
            pktPrpMap.put("STATUS", util.nvl((String)rs.getString("stt")));
            pktPrpMap.put("CRTWT", util.nvl((String)rs.getString("cts")));
            pktPrpMap.put("RATE", rte);
            pktPrpMap.put("AMOUNT", util.nvl((String)rs.getString("vlu")));
            pktPrpMap.put("RAP_RTE", util.nvl((String)rs.getString("rap_rte")));
            pktPrpMap.put("RAP_DIS", util.nvl((String)rs.getString("rapdis")));
            pktPrpMap.put("RAP_VLU", util.nvl((String)rs.getString("rapVlu")));
            String shape = util.nvl((String)rs.getString(shPrp));
            String certLab = util.nvl((String)rs.getString("cert_lab"));
                if(!certLabLst.contains(certLab))
                    certLabLst.add(certLab);
                if(fancyList.indexOf(shape)!=-1){
                    if(newfancyList.indexOf(shape)==-1)
                        newfancyList.add(shape);
                }
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                    
                String val = util.nvl(rs.getString(fld)) ;
         
            pktPrpMap.put(prp, val);
            }
                ary = new ArrayList();
                ary.add(stk_idn);
                ArrayList out = new ArrayList();
                out.add("V");
                out.add("V");
                CallableStatement cst = null;
                cst = db.execCall("DP_GET_FA_PKT_PRP ","DP_GET_FA_PKT_PRP(pStkIdn => ?, pPrp => ?, pVal => ?)", ary, out);
                String lprpLst = util.nvl(cst.getString(ary.size()+1));
                if(!lprpLst.equals("")){
                String lprpVal = util.nvl(cst.getString(ary.size()+2));
                  cst.close();
                  cst=null;
                String[] lprpLstsplit = lprpLst.split("#");
                String[] lprpValsplit = lprpVal.split("#");
                if(lprpLstsplit!=null){
                for(int i=0 ; i <lprpLstsplit.length; i++){
                    String lprp= util.nvl(lprpLstsplit[i]).trim();
                    String lval = util.nvl(lprpValsplit[i]).trim();
                    if(lprp.equals("RTE"))
                        rte=lval;
                    if(lprp.equals("RAP_RTE"))
                        rap_rte=lval;
                pktPrpMap.put("F "+lprp, lval);
                }
                }
                    double rapDis = 0;
                if(rap_rte.equals("1"))
                    rapDis = 0;
                else if(!rte.equals("") && !rap_rte.equals("")){
                    double frte = Float.parseFloat(rte);
                    double frapRte = Float.parseFloat(rap_rte);
                    rapDis = (((frte/frapRte)*100)-100);
                    rapDis = util.roundToDecimals(rapDis, 2);
                }
                    pktPrpMap.put("F RAP_DIS", String.valueOf(rapDis)); 
                }
            pktList.add(pktPrpMap);
            }
            rs.close(); pst.close();
            pktList.add(new HashMap());
           
            
         if(grp.equals("PRICING") && fnxl.equals("Y")){
               
                param = new ArrayList();
                
                String labStr = certLabLst.toString();
                labStr = labStr.replaceAll(",", "','");
                labStr = labStr.replace("[", "('");
                labStr = labStr.replace("]", "')");
                
                HashMap prp = info.getPrp();
                ArrayList prpSrt = (ArrayList)prp.get("SHAPES");
                ArrayList prpVal = (ArrayList)prp.get("SHAPEV");
                for(int x=0;x<newfancyList.size();x++){
                    String lprpVal=(String)newfancyList.get(x);
                    newfancyListSrt.add(prpSrt.get(prpVal.indexOf(lprpVal)));
                }
                
                String shapeStr = newfancyListSrt.toString();
                shapeStr = shapeStr.replace("[", "(");
                shapeStr = shapeStr.replace("]", ")");
                
            if(newfancyList!=null && newfancyList.size()>0){
               
            rsltQ = "with STKDTL as  ( Select b.sk1,b.pkt_ty,b.stt,b.cert_no certno,b.qty,b.cert_lab,b.idn,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , st.mprp, \n" + 
            "DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), b.vnm) vnm , b.tfl3 , b.cts, nvl(b.upr, b.cmp) rate, \n" + 
            "b.rap_rte raprte,to_char(trunc(b.cts,2) * b.rap_rte, '99999999990.00') rapVlu ,to_char(trunc(b.cts,2) * nvl(b.upr, b.cmp), '99999999990.00') vlu, \n" + 
            "decode(b.rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)) rapdis\n" + 
            "from stk_dtl st, mstk b , stk_dtl st1\n" + 
            "where st.mstk_idn=b.idn  and st.mstk_idn=st1.mstk_idn  and b.pkt_ty in ('NR','SMX') and st.grp=1  and st1.mprp='SHAPE' \n" + 
            " and st1.srt in "+shapeStr+" and st1.grp=1 and b.cert_lab in "+labStr+" \n" + 
            "and stt in ('MKAV','MKEI','MKIS','SHIS','MKKS_IS','MKOS_IS') and exists (select 1 from rep_prp rp where rp.MDL = ? and st.mprp = rp.prp))  \n" + 
            " Select * from stkDtl PIVOT  ( max(atr)  for mprp in ("+mprpStr+") ) order by 1" ;
                       
                       param = new ArrayList();
                       param.add("ISSUEFNLASSORT_VIEW");

                 outLst = db.execSqlLst("search Result", rsltQ, param);
                pst = (PreparedStatement)outLst.get(0);
                 rs = (ResultSet)outLst.get(1);
                       while (rs.next()) {
                       String stk_idn=util.nvl((String)rs.getString("idn"));
                       HashMap pktPrpMap = new HashMap();
                       pktPrpMap.put("Sr No", String.valueOf(sr++));
                        pktPrpMap.put("STKIDN", stk_idn);
                       pktPrpMap.put("VNM", util.nvl((String)rs.getString("vnm")));
                       pktPrpMap.put("STATUS", util.nvl((String)rs.getString("stt")));
                       pktPrpMap.put("CRTWT", util.nvl((String)rs.getString("cts")));
                       pktPrpMap.put("RATE", util.nvl((String)rs.getString("rate")));
                       pktPrpMap.put("AMOUNT", util.nvl((String)rs.getString("vlu")));
                       pktPrpMap.put("RAP_RTE", util.nvl((String)rs.getString("raprte")));
                       pktPrpMap.put("RAP_DIS", util.nvl((String)rs.getString("rapdis")));
                       pktPrpMap.put("RAP_VLU", util.nvl((String)rs.getString("rapVlu")));
                       for (int i = 0; i < vwPrpLst.size(); i++) {
                       String vwPrp = (String)vwPrpLst.get(i);
                       String fldName = util.pivot(vwPrp);
                       String fldVal = util.nvl((String)rs.getString(fldName));
                       pktPrpMap.put(vwPrp, fldVal);
                       }
                      pktList.add(pktPrpMap);
                       }
            rs.close(); pst.close();
            }
            String gt_rslt = "select count(*) from gt_srch_rslt where "+shPrp+"='ROUND' and "+clrPrp+" between 20 and 60 and cts between 0.50 and 100 ";
            ResultSet rs1 = db.execSql("gt fetch", gt_rslt, new ArrayList());
            while(rs1.next()){
            rsltQ = "with STKDTL as  ( Select b.sk1,b.pkt_ty,b.stt,b.cert_no certno,b.qty,b.cert_lab,b.idn,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , st.mprp, \n" + 
            "DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), b.vnm) vnm , b.tfl3 , b.cts, nvl(b.upr, b.cmp) rate, \n" + 
            "b.rap_rte raprte,to_char(trunc(b.cts,2) * b.rap_rte, '99999999990.00') rapVlu ,to_char(trunc(b.cts,2) * nvl(b.upr, b.cmp), '99999999990.00') vlu, \n" + 
            "decode(b.rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)) rapdis\n" + 
            "from stk_dtl st, mstk b , stk_dtl st1 , stk_dtl st2 , stk_dtl st3\n" + 
            "where st.mstk_idn=b.idn  and st.mstk_idn=st1.mstk_idn and st1.mstk_idn=st2.mstk_idn and st2.mstk_idn=st3.mstk_idn and b.pkt_ty in ('NR','SMX') and st.grp=1  \n" + 
            "  and b.cert_lab in "+labStr+"  and st1.mprp='SHAPE' and  st1.val in ('ROUND') and st1.grp=1\n" + 
            "and st2.mprp='CLR' and  st2.srt between 20 and 60 and st2.grp=1\n" + 
            "and st3.mprp='CRTWT' and  st3.num between 0.50 and 100 and st3.grp=1\n" + 
            "and stt in ('MKAV','MKEI','MKIS','SHIS','MKKS_IS','MKOS_IS') and exists (select 1 from rep_prp rp where rp.MDL = ? and st.mprp = rp.prp)) " +
            " Select * from stkDtl PIVOT  ( max(atr)  for mprp in ("+mprpStr+") ) order by 1" ;
          
                param = new ArrayList();
                param.add("ISSUEFNLASSORT_VIEW");
            rs = db.execSql("search Result", rsltQ, param);
                 outLst = db.execSqlLst("search Result", rsltQ, param);
                 pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                       String stk_idn=util.nvl((String)rs.getString("idn"));
                       HashMap pktPrpMap = new HashMap();
                       pktPrpMap.put("Sr No", String.valueOf(sr++));
                       pktPrpMap.put("STKIDN", stk_idn);
                       pktPrpMap.put("VNM", util.nvl((String)rs.getString("vnm")));
                       pktPrpMap.put("STATUS", util.nvl((String)rs.getString("stt")));
                       pktPrpMap.put("CRTWT", util.nvl((String)rs.getString("cts")));
                       pktPrpMap.put("RATE", util.nvl((String)rs.getString("rate")));
                       pktPrpMap.put("AMOUNT", util.nvl((String)rs.getString("vlu")));
                       pktPrpMap.put("RAP_RTE", util.nvl((String)rs.getString("raprte")));
                       pktPrpMap.put("RAP_DIS", util.nvl((String)rs.getString("rapdis")));
                       pktPrpMap.put("RAP_VLU", util.nvl((String)rs.getString("rapVlu")));
                       for (int i = 0; i < vwPrpLst.size(); i++) {
                       String vwPrp = (String)vwPrpLst.get(i);
                       String fldName = util.pivot(vwPrp);
                       String fldVal = util.nvl((String)rs.getString(fldName));
                       pktPrpMap.put(vwPrp, fldVal);
                       }
                      pktList.add(pktPrpMap);
            }
            rs.close(); pst.close();
            }
            rs1.close();
         }
            util.updAccessLog(req,res,"Assort Lab Pending", "loadPkt end");
            HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            hwb.write(out1);
            out1.flush();
            out1.close();
            return null;
        }
    }
    
    public ArrayList SearchResult(HttpServletRequest req , HttpServletResponse res  ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList pktList = new ArrayList();
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "asViewLst", "AS_VIEW");
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        String sumRte="quot";
        if(cnt.equals("kj")){
            int indexPri = vwPrpLst.indexOf("MFG_PRI")+1;
            sumRte = util.prpsrtcolumn("prp", indexPri);
        }
        HashMap gtTotalMap = new HashMap();
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, to_char(pkt_dte, 'dd-Mon-rrrr') issdte,trunc(sysdate)-trunc(nvl(pkt_dte,sysdate)) pdays, stt , qty , cts ,img , rmk , srch_id , quot , to_char(trunc(cts,2) * quot, '99999990.00') amt,to_char(trunc(cts,2) * "+sumRte+", '99999990.00') mfgpritotal  ";

        for (int i = 0; i < vwPrpLst.size(); i++) {
            String fld = "prp_";
            int j = i + 1;
            if (j < 10)
                fld += "00" + j;
            else if (j < 100)
                fld += "0" + j;
            else if (j > 100)
                fld += j;           

            srchQ += ", " + fld;
           
         }

        
        String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by img , sk1";
        
        ArrayList ary = new ArrayList();
        ary.add("AV");

        ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {

                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.put("vnm",vnm);
                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                pktPrpMap.put("emp",util.nvl(rs.getString("img")));
                pktPrpMap.put("prc",util.nvl(rs.getString("rmk")));
                pktPrpMap.put("issIdn",util.nvl(rs.getString("srch_id")));
                pktPrpMap.put("rte", util.nvl(rs.getString("quot")));
                pktPrpMap.put("amt", util.nvl(rs.getString("amt")));
                pktPrpMap.put("issdte", util.nvl(rs.getString("issdte")));
                pktPrpMap.put("pnddays", util.nvl(rs.getString("pdays")));
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                       if(prp.equals("MFG_VLU"))
                           val = util.nvl(rs.getString("mfgpritotal"));
                        
                        pktPrpMap.put(prp, val);
                         }
                              
                    pktList.add(pktPrpMap);
                }
            rs.close(); pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
       

        return pktList;
    }
    public ActionForward labPktPrint(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Assort Lab Pending", "labPktPrint start");
              String delQ = " Delete from mkt_prc ";
              int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
              String issuId = req.getParameter("issueIdn");
              ArrayList ary = new ArrayList();
              HashMap dbinfo = info.getDmbsInfoLst();
              String cnt = (String)dbinfo.get("CNT");
              String webUrl = (String)dbinfo.get("REP_URL");
              String reqUrl = (String)dbinfo.get("HOME_DIR");
              String repPath = (String)dbinfo.get("REP_PATH");
              String repNme = "pktprint_lbiss.rdf";
             
              int mkt_prc = 0;

            ArrayList outLst = db.execSqlLst("mkt_prc", "select  seq_mkt_prc.nextval from dual ", new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
              if(rs.next())
                  mkt_prc = rs.getInt(1);
            rs.close(); pst.close();
              String insertPrc = " insert into mkt_prc(prc_idn ,mstk_idn ,rep_dte) select ? , iss_stk_idn , sysdate from iss_rtn_dtl where iss_id=? and stt='IS'" ;
                ary.add(String.valueOf(mkt_prc)); 
                ary.add(issuId);
             
               ct = db.execUpd("insert mkt_prc", insertPrc, ary);
             
              if(ct>0){
                 String url = repPath+"/reports/rwservlet?"+cnt+"&report="+reqUrl+"\\reports\\"+repNme ;
                  res.sendRedirect(url);    
              }
            util.updAccessLog(req,res,"Assort Lab Pending", "labPktPrint end");
            return null;
        }
    }
    public ActionForward fecth(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Assort Lab Pending", "fetch start");
        AssortLabPendingForm udf = (AssortLabPendingForm)af;
        int ct = db.execUpd("delete gt", "delete from gt_srch_rslt", new ArrayList());
        String mprcIdn = util.nvl((String)udf.getValue("mprcIdn"));
        String empIdn = util.nvl((String)udf.getValue("empIdn"));
        String grp = util.nvl((String)udf.getValue("stt"));
        String frmdte =util.nvl((String)udf.getValue("frmdte"));
        String todte =util.nvl((String)udf.getValue("todte"));
        ArrayList ary = null;
        ary = new ArrayList();
        String srchRefQ = 
        " Insert into gt_srch_rslt (stk_idn, vnm,  stt , flg , qty , cts , srch_id , rmk ,  img , quot,sk1,pkt_dte ) " + 
       " select  b.iss_stk_idn , c.vnm , c.stt , 'AV' , c.qty , c.cts , a.iss_id ,  a.prc , a.emp , nvl(upr, cmp) rte,c.sk1,b.iss_dt  " + 
       "from iss_rtn_v a, iss_rtn_dtl b , mstk c " + 
       "where b.stt = 'IS'  and a.iss_id = b.iss_id " + 
       "and b.iss_stk_idn = c.idn " ;
        if(!mprcIdn.equals("0")){
            srchRefQ = srchRefQ+" and a.prc_id = ? ";
            ary.add(mprcIdn);
        }
        if(!empIdn.equals("0")){
          srchRefQ = srchRefQ+  " and a.emp_id=? ";
            ary.add(empIdn);
        }
        if(!grp.equals("")){
            ArrayList params = new ArrayList();
            params.add(grp);

            ArrayList outLst = db.execSqlLst("dfStkStt", "select stt from df_stk_stt where grp1=? and flg='A'", params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            String sttStr = "";
            while(rs.next()){
              String stt =  rs.getString("stt");
                if(sttStr.equals(""))
                    sttStr = "'"+stt+"'" ;
                else
                   sttStr = sttStr+",'"+stt+"'";
            }
            rs.close(); pst.close();
           sttStr = "("+sttStr+")";
          srchRefQ = srchRefQ+" and c.stt in "+sttStr ;
           
        }
        if (!frmdte.equals("") && !todte.equals("")){
        srchRefQ = srchRefQ+" and trunc(a.iss_dt) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
        }
        srchRefQ = srchRefQ+ " order by a.emp ";
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("AS_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        ArrayList pktDtlList = SearchResult(req, res);
        session.setAttribute("pktList", pktDtlList);
        HashMap ttlMap = GetTotal(req, res);
        req.setAttribute("totalMap", ttlMap);
        req.setAttribute("view", "Y");
            util.updAccessLog(req,res,"Assort Lab Pending", "fetch end");
      return am.findForward("loadREP");
        }
    }
  
    public ActionForward loadREP(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             util.updAccessLog(req,res,"Assort Lab Pending", "loadREP start");
        AssortLabPendingForm udf = (AssortLabPendingForm)af;
        udf.resetAll();
        String issueEmp = "select distinct a.emp_id, a.emp from iss_rtn_v a order by 2 ";

        ArrayList empList = new ArrayList();
             ArrayList outLst = db.execSqlLst("issueEmp", issueEmp, new ArrayList());
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            MNme mnme = new MNme();
            mnme.setEmp_nme(rs.getString("emp"));
            mnme.setEmp_idn(rs.getString("emp_id"));
            empList.add(mnme);
        }
         rs.close(); pst.close();
        ArrayList mprcList = new ArrayList();
        String mprcSql = "select idn , prc from mprc where stt='A' and vld_till is null order by srt ";

             outLst = db.execSqlLst("mprcSql", mprcSql, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            Mprc prc = new Mprc();
            prc.setMprcId(rs.getString("idn"));
            prc.setPrc(rs.getString("prc"));
            mprcList.add(prc);
        }
         rs.close(); pst.close();
        udf.setEmpList(empList);
        udf.setMprcList(mprcList);
        ArrayList sttList = new ArrayList();
         String stkStt = "select distinct grp1 , decode(grp1, 'MKT','Marketing','LAB','Lab','ASRT','Assort','SOLD','Sold', grp1) dsc " +
                         " from df_stk_stt where flg='A' and vld_dte is null and grp1 is not null ";
         rs = db.execSql("stkStt", stkStt, new ArrayList());
         while(rs.next()){
             ArrayList sttDtl = new ArrayList();
             sttDtl.add(rs.getString("grp1"));
             sttDtl.add(rs.getString("dsc"));
             sttList.add(sttDtl);
         }
         rs.close(); pst.close();
         session.setAttribute("sttLst",sttList);
             util.updAccessLog(req,res,"Assort Lab Pending", "loadREP end");
        return am.findForward("loadREP");
         }
     }
    
    public HashMap GetTotal(HttpServletRequest req ,HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "asViewLst", "AS_VIEW");
        String sumRte="quot";
        if(cnt.equals("kj")){
            int indexPri = vwPrpLst.indexOf("MFG_PRI")+1;
            sumRte = util.prpsrtcolumn("prp", indexPri);
        }
        HashMap gtTotalMap = new HashMap();
        String gtTotal ="Select count(*) qty, sum(cts) cts ,  to_char(sum((trunc(cts,2) * nvl("+sumRte+",1))),'9999999990.99') vlu  from gt_srch_rslt where flg = ?";
        ArrayList ary = new ArrayList();
        ary.add("AV");

        ArrayList outLst = db.execSqlLst("getTotal", gtTotal, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if (rs.next()) {
             gtTotalMap.put("qty", util.nvl(rs.getString("qty")));
             gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
             gtTotalMap.put("vlu", util.nvl(rs.getString("vlu")));
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        gtTotal =" Select count(*) qty, sum(cts) cts  , img emp , to_char(sum((trunc(cts,2) * nvl("+sumRte+",1))),'9999999990.99') vlu  from gt_srch_rslt GROUP BY img ";
        ary = new ArrayList();

        outLst = db.execSqlLst("getTotal", gtTotal, ary);
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
             String emp = rs.getString("emp");
             gtTotalMap.put(emp+"_Q", util.nvl(rs.getString("qty")));
             gtTotalMap.put(emp+"_C", util.nvl(rs.getString("cts")));
             gtTotalMap.put(emp+"_A", util.nvl(rs.getString("vlu")));
            }
            rs.close(); pst.close();

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return gtTotalMap ;
    }
    public ActionForward createXLpkt(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Assort Lab Pending", "createXLpkt start");
        HashMap dbinfo = info.getDmbsInfoLst();
        String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N");
        int zipallowsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"50"));
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "PacketDtls"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            int pktListsz=pktList.size();
            if(zipallowyn.equals("Y") && pktListsz>zipallowsz){
            String contentTypezip = "application/zip";
            String fileNmzip = "PacketDtls"+util.getToDteTime();
            OutputStream outstm = res.getOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(outstm);
            ZipEntry entry = new ZipEntry(fileNmzip+".xls");
            zipOut.putNextEntry(entry);
            res.setHeader("Content-Disposition","attachment; filename="+fileNmzip+".zip");
            res.setContentType(contentTypezip);
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
               hwb.write(bos);
               bos.writeTo(zipOut);      
              zipOut.closeEntry();
               zipOut.flush();
               zipOut.close();
               outstm.flush();
               outstm.close(); 
            }else{
        hwb.write(out);
        out.flush();
        out.close();
            }
            util.updAccessLog(req,res,"Assort Lab Pending", "createXLpkt end");
        return null;
        }
    }
    public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Assort Lab Pending", "createXL start");
            HashMap dbinfo = info.getDmbsInfoLst();
            String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
            int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            int pktListsz=pktList.size();
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
            String contentTypezip = "application/zip";
            String fileNmzip = "PendingIssueDtl"+util.getToDteTime();
            OutputStream outstm = res.getOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(outstm);
            ZipEntry entry = new ZipEntry(fileNmzip+".xls");
            zipOut.putNextEntry(entry);
            res.setHeader("Content-Disposition","attachment; filename="+fileNmzip+".zip");
            res.setContentType(contentTypezip);
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
               hwb.write(bos);
               bos.writeTo(zipOut);      
              zipOut.closeEntry();
               zipOut.flush();
               zipOut.close();
               outstm.flush();
               outstm.close(); 
            }else{
                String fileNm = "PendingIssueDtl"+util.getToDteTime()+".xls";
                OutputStream out = res.getOutputStream();
                String CONTENT_TYPE = "getServletContext()/vnd-excel";
                res.setContentType(CONTENT_TYPE);
                res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        hwb.write(out);
        out.flush();
        out.close();
            }
            util.updAccessLog(req,res,"Assort Lab Pending", "createXL end");
        return null;
        }
    }

  
    public ActionForward labLabReport(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Assort Lab Pending", "labLabReport start");
    AssortLabPendingForm udf = (AssortLabPendingForm)af;
    GenericInterface genericInt = new GenericImpl();
    udf.resetAll();
     ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LABLOCGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LABLOCGNCSrch");
    info.setGncPrpLst(assortSrchList);
     labReport(am, af, req, res);
            util.updAccessLog(req,res,"Assort Lab Pending", "labLabReport end");
     return am.findForward("labReport");
        }
    }
    public ActionForward labReport(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Assort Lab Pending", "labReport start");
    AssortLabPendingForm udf = (AssortLabPendingForm)af;
    ArrayList pktList = new ArrayList();
    String loc =util.nvl((String)udf.getValue("loc"));
    String daily = util.nvl((String)udf.getValue("daily"));
    String dtefr = util.nvl((String)udf.getValue("dtefr"));
    String dteto = util.nvl((String)udf.getValue("dteto"));
    HashMap mprp = info.getMprp();  
    ArrayList itemHdr = new ArrayList();
    HashMap paramsMap = new HashMap();
    String conQ="";
    itemHdr.add("STATUS");
    itemHdr.add("LAB");
    if(daily.equals(""))  {
    itemHdr.add("LAB_LOC");
    }
    itemHdr.add("COUNT");
    itemHdr.add("CTS");
    if(!daily.equals(""))  {
        itemHdr.add("AVG");
        itemHdr.add("AMT");
    }
    itemHdr.add("ASRT_VLU");
    itemHdr.add("ASRT_AVG");   
        HashMap pktPrpMap = new HashMap();
        HashMap prp = info.getPrp();
        ArrayList assortSrchList = (ArrayList)session.getAttribute("LABLOCGNCSrch");
        for(int i=0;i<assortSrchList.size();i++){
            ArrayList prplist =(ArrayList)assortSrchList.get(i);
            String lprp = (String)prplist.get(0);
            String typ = util.nvl((String)mprp.get(lprp+"T"));
            String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
            String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
            if(fldVal2.equals(""))
            fldVal2=fldVal1;
//            if(typ.equals("C")){
//            ArrayList prpSrtSize = (ArrayList)prp.get(lprp+"S");
//            ArrayList prpValSize = (ArrayList)prp.get(lprp+"V");  
//            if(!fldVal1.equals("")){
//            fldVal1=(String)prpValSize.get(prpSrtSize.indexOf(fldVal1));
//            fldVal2=(String)prpValSize.get(prpSrtSize.indexOf(fldVal2));
//            }
//            }
            if(typ.equals("T")){
                fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                fldVal2 = fldVal1;
            }
           
            if(!fldVal1.equals("") && !fldVal2.equals("")){
                paramsMap.put(lprp+"_1", fldVal1);
                paramsMap.put(lprp+"_2", fldVal2);               
            }             
        }
        if(paramsMap!=null && paramsMap.size()>0){
        for(int i=0;i<assortSrchList.size();i++){
        ArrayList prplist =(ArrayList)assortSrchList.get(i);
        String lprp = (String)prplist.get(0);
          
            String val1 = util.nvl((String)paramsMap.get(lprp+"_1"));          
            String val2 = util.nvl((String)paramsMap.get(lprp+"_2"));
         
             if(!val1.equals("") && !val2.equals("")){
//                String srtval=util.prpsrtcolumn("srt",i+1);
                conQ = conQ + " and "+lprp+".srt between "+val1+" and "+val2+" ";
             }
        }
        }
            String delQ = " Delete from gt_srch_rslt ";
            int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            String srchRefQ="insert into gt_srch_rslt (stk_idn, stt, qty, cts,quot, cmp, prp_001,srt_001, prp_002,srt_002,prp_003,srt_003)\n" + 
            "select m.idn, decode(stt,'LB_RT','Receive','Issue') Status\n" + 
            ", 1 qty\n" + 
            ", trunc(cts,2) cts\n" + 
            ",nvl(upr, cmp)\n" + 
            ", nvl(nvl(FA_PRI.num, nvl(upr, cmp)), 0)\n" + 
            ", nvl(LAB_PRC.val, '-') lab,LAB_PRC.srt\n" + 
            ", nvl(LAB_LOC.val, 'NA') lab_loc,LAB_LOC.srt\n" + 
            ", nvl(DEPT.val, 'NA') dt,DEPT.srt\n" + 
            "from mstk m, stk_dtl LAB_PRC, stk_dtl LAB_LOC, stk_dtl FA_PRI,stk_dtl DEPT\n" + 
            "where 1 = 1\n" + 
            "and pkt_ty = 'NR' and m.stt in ('LB_IS', 'LB_RI', 'LB_RS', 'LB_RT','LB_CF','LB_CFRS')\n" + 
            "and m.idn = FA_PRI.mstk_idn and FA_PRI.grp = 1 and FA_PRI.mprp = 'FA_PRI'\n" + 
            "and m.idn = LAB_PRC.mstk_idn and LAB_PRC.grp = 1 and LAB_PRC.mprp = 'LAB_PRC'\n" + 
            "and m.idn = LAB_LOC.mstk_idn and LAB_LOC.grp = 1 and LAB_LOC.mprp = 'LAB_LOC'\n" + 
            "and m.idn = DEPT.mstk_idn and DEPT.grp = 1 and DEPT.mprp = 'DEPT' "+conQ;
            ct = db.execUpd(" insert Lab loc ", srchRefQ, new ArrayList());
    if(daily.equals(""))  { 
        String srchQ = "select stt status, prp_001 lab, prp_002 lab_loc, sum(qty) cnt, to_char(sum(cts), 9999990.00) cts\n" + 
        ", to_char(sum(cts*cmp), 999999999990.00) asrt_vlu\n" + 
        ", to_char(sum(cts*cmp)/sum(cts), 99999990.00) asrt_avg\n" + 
        "from GT_SRCH_RSLT \n"+
        " group by stt, prp_001, prp_002\n" + 
        " order by 1,2,3";

        ArrayList outLst = db.execSqlLst("Lab Loc Result", srchQ, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while (rs.next()) {
    pktPrpMap = new HashMap();
    pktPrpMap.put("STATUS", util.nvl(rs.getString("status")));
    pktPrpMap.put("LAB", util.nvl(rs.getString("lab")));
    pktPrpMap.put("LAB_LOC", util.nvl(rs.getString("lab_loc")));
    pktPrpMap.put("COUNT", util.nvl(rs.getString("cnt")));
    pktPrpMap.put("CTS", util.nvl(rs.getString("cts")));
    pktPrpMap.put("ASRT_VLU", util.nvl(rs.getString("asrt_vlu")));
    pktPrpMap.put("ASRT_AVG", util.nvl(rs.getString("asrt_avg")));         
    pktList.add(pktPrpMap);
    }   
        rs.close(); pst.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    req.setAttribute("view", "Y");
    }else{
        String srchQ = "select stt status, prp_001 lab, sum(qty) cnt, to_char(sum(cts), 9999990.00) cts,Trunc(Sum(quot*Trunc(Cts,2))/Sum(Trunc(Cts, 2)),2) avg,To_Char(Sum(Trunc(Cts,2)*Nvl(quot,0)),999999999990.00) amt \n" + 
        ", to_char(sum(cts*cmp), 999999999990.00) asrt_vlu\n" + 
        ", to_char(sum(cts*cmp)/sum(cts), 99999990.00) asrt_avg\n" + 
        "from GT_SRCH_RSLT\n"+ 
        " group by stt, prp_001\n" + 
        " order by 1,2";

        ArrayList outLst = db.execSqlLst("Lab Loc Result", srchQ, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
        while (rs.next()) {
        pktPrpMap = new HashMap();
        pktPrpMap.put("STATUS", util.nvl(rs.getString("status")));
        pktPrpMap.put("LAB", util.nvl(rs.getString("lab")));
        pktPrpMap.put("COUNT", util.nvl(rs.getString("cnt")));
        pktPrpMap.put("CTS", util.nvl(rs.getString("cts")));
        pktPrpMap.put("AVG", util.nvl(rs.getString("avg")));
        pktPrpMap.put("AMT", util.nvl(rs.getString("amt")));    
        pktPrpMap.put("ASRT_VLU", util.nvl(rs.getString("asrt_vlu")));
        pktPrpMap.put("ASRT_AVG", util.nvl(rs.getString("asrt_avg")));         
        pktList.add(pktPrpMap);
        }   
            rs.close(); pst.close();
        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        }    
        req.setAttribute("view", "N");
    }
    udf.resetAll();
    udf.setPktList(pktList);
    session.setAttribute("itemHdr", itemHdr);
    session.setAttribute("pktList", pktList);
            util.updAccessLog(req,res,"Assort Lab Pending", "labReport end");
    return am.findForward("labReport");
        }
    }
    
//    public ArrayList GenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("LABLOCGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'LAB_LOC_SRCH' and flg in ('Y','S') order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("LABLOCGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
//    
    public ActionForward createReportPDF(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Assort Lab Pending", "createReportPDF start");
    PdfforReport pdf=new PdfforReport();
    HashMap dbSysInfo = info.getDmbsInfoLst();
    String docPath = (String)dbSysInfo.get("DOC_PATH");


        String FILE = "Location_Report_"+util.getToDteTime()+".pdf";
        // String FILEPATH="c:/pdfana/"+FILE;
        String path = getServlet().getServletContext().getRealPath("/") + FILE;
        String FILEPATH= path+""+FILE;


    pdf.getReportPDF(req,FILEPATH);
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
            util.updAccessLog(req,res,"Assort Lab Pending", "createReportPDF end");
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
    public ArrayList getEmp(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = new ArrayList();
        ArrayList empList = new ArrayList();
        String empSql = "select nme_idn, nme from nme_v where typ in('EMPLOYEE','MFG') order by nme";

        ArrayList outLst = db.execSqlLst("empSql", empSql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                MNme emp = new MNme();
                emp.setEmp_idn(rs.getString("nme_idn"));
                emp.setEmp_nme(rs.getString("nme"));
                empList.add(emp);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return empList;
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
                util.updAccessLog(req,res,"Assort Lab Pending", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Assort Lab Pending", "init");
            }
            }
            return rtnPg;
            }
    
    
    public AssortLabPending() {
        super();
    }
}
