package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.PdfforReport;
import ft.com.dao.ByrDao;
import ft.com.dao.DFMenu;

import ft.com.dao.DFMenuItm;

import ft.com.dao.ObjBean;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.PacketLookupForm;
import ft.com.marketing.SearchQuery;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.URLEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Collections;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ReportAction extends DispatchAction {

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
        util.updAccessLog(req,res,"Report Action", "load start");
        ReportForm form = (ReportForm)af;
        form.reset();
        ArrayList reportLst = new ArrayList();
        String reportSql = "select nme_rule , grp  from mrule  where mdl = 'JFLEX_REPORT'  " +
                           " and til_dte is null order by nme_rule";

            ArrayList outLst = db.execSqlLst("reportSql", reportSql , new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            DFMenu report = new DFMenu();
            report.setDsc(rs.getString("nme_rule"));
            report.setFlg(rs.getString("grp"));
            reportLst.add(report);
        }
        rs.close(); pst.close();
        form.setMemoList(reportLst);
        session.setAttribute("paramsList", new ArrayList());
            util.updAccessLog(req,res,"Report Action", "load end");
        return am.findForward("load");
        }
    }
    public ActionForward fecthParam(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Report Action", "fecthParam start");
        ArrayList paramsList = new ArrayList();
        ReportForm form = (ReportForm)af;
        String pageName = util.nvl(req.getParameter("reportPag"));
        String pagedsc="";
        if(!pageName.equals("0") &&  !pageName.equals("ALL")){
        String reportParams = "select a.nme_rule , b.dsc , b.dta_typ , b.chr_fr , b.chr_to from mrule a , rule_dtl  b " +
                              "where b.rule_idn = a.rule_idn and a.grp= ? order by b.srt_fr ";
       ArrayList params = new ArrayList();
       params.add(pageName);

            ArrayList outLst = db.execSqlLst("reportParams",reportParams, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
       while(rs.next()){
           pagedsc = util.nvl(rs.getString("nme_rule"));
           HashMap paramsMap = new HashMap();
           paramsMap.put("dsc", rs.getString("dsc"));
           paramsMap.put("dataTyp",rs.getString("dta_typ"));
           paramsMap.put("pNme", rs.getString("chr_fr"));
           paramsMap.put("list", util.nvl(rs.getString("chr_to")));
           paramsList.add(paramsMap);
       }
            rs.close(); pst.close();
        }
        
       req.setAttribute("pageDsc", pagedsc);
       session.setAttribute("paramsList", paramsList);
       form.reset();
       form.setValue("reportPag", pageName);
            util.updAccessLog(req,res,"Report Action", "fecthParam end");
        return am.findForward("load");
        }
    }
    public ActionForward reset(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             util.updAccessLog(req,res,"Report Action", "reset start");
        ReportForm form = (ReportForm)af;
             util.updAccessLog(req,res,"Report Action", "reset end");
      return am.findForward("load");
         }
     }
    public ActionForward viewRT(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Report Action", "viewRT start");
       String url = "";
        ReportForm form = (ReportForm)af;
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        String accessidn = util.nvl(req.getParameter("accessidn"));
        String webUrl = (String)dbinfo.get("REP_URL");
        String reqUrl = (String)dbinfo.get("HOME_DIR");
        String repPath = (String)dbinfo.get("REP_PATH");
        String pageName = util.nvl((String)form.getValue("reportPag"));
          String shmfg = util.nvl(req.getParameter("shmfg"));
           
        url = repPath+"/reports/rwservlet?"+cnt+"&report="+reqUrl+"\\reports\\"+pageName+"&p_access="+accessidn ;
          if(shmfg.equals("Y")){
          reqUrl = (String)dbinfo.get("MFGHOME_DIR");
          cnt = (String)dbinfo.get("MFGKEY");
            url = repPath+"/reports/rwservlet?"+cnt+"&report="+reqUrl+"\\reports\\"+pageName ;
          }
        ArrayList paramsList = (ArrayList)session.getAttribute("paramsList");
          if(paramsList!=null && paramsList.size() >0){
          for(int i=0 ; i< paramsList.size() ; i++){
          HashMap params = (HashMap)paramsList.get(i);
          String dataTyp = (String)params.get("dataTyp");
          String dsc =(String)params.get("dsc");
          String pNme =(String)params.get("pNme");
              if(dataTyp.equals("M")){
             String[] valLst = req.getParameterValues(pNme);
            if(valLst!=null && valLst.length > 0){
                boolean isFt=true;
                 for(int j=0 ; j < valLst.length ; j++){
                     String pVal = util.nvl(valLst[j]);
                     if(!pVal.equals("") && !pVal.equals("0")){
                         if(isFt){
                         isFt=false;
                         url = url+"&"+pNme+"="+URLEncoder.encode(pVal , "UTF-8")+"";
                         } else{
                         url = url+","+URLEncoder.encode(pVal , "UTF-8")+"";
                         }
                     }
                         
                 }
                 }
                  
              }else{
             String pVal = util.nvl((String)form.getValue(pNme));
             if(!pVal.equals("") && !pVal.equals("ALL") && !pVal.equals("0") ){
                 if(dataTyp.equals("TA")){
                    pVal = util.getVnm(pVal);
                    pVal = pVal.replaceAll("'", "");
                    
                 }
                    
                 url = url+"&"+pNme+"="+URLEncoder.encode(pVal , "UTF-8")+"";
            }}
          }}
            util.updAccessLog(req,res,"Report Action", "viewRT end");
        res.sendRedirect(url); 
        return null;
        }
    }
    public ActionForward salePnd(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                util.updAccessLog(req,res,"Report Action", "salePnd start");
            ReportForm udf = (ReportForm)af;
            GenericInterface genericInt = new GenericImpl();
            udf.resetALL();
            String pkt_typ=util.nvl(req.getParameter("PKT_TYP"));
            String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
            String dfNmeIdn=util.nvl((String)info.getDfNmeIdn()); 
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            ArrayList rolenmLst=(ArrayList)info.getRoleLst();
            String usrFlg=util.nvl((String)info.getUsrFlg());
            String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
            SearchQuery srchQuery = new SearchQuery();
            ArrayList itemHdr = new ArrayList();    
            ArrayList itemHdrXL = new ArrayList();   
            db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
            ArrayList vwPrpLst = genericInt.genericPrprVw(req,res,"SAL_PND_VW","SAL_PND_VW");
            String conQ="";
            String allowData="Y";
            ArrayList pktList = new  ArrayList();
                ArrayList prpDspBlocked = info.getPageblockList();
            ArrayList ary = new ArrayList();
                ArrayList pageList=new ArrayList();
                HashMap pageDtlMap=new HashMap();
                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("SALEPENDING_REPORT");
                if(pageDtl==null || pageDtl.size()==0){
                pageDtl=new HashMap();
                pageDtl=util.pagedef("SALEPENDING_REPORT");
                allPageDtl.put("SALEPENDING_REPORT",pageDtl);
                }
                info.setPageDetails(allPageDtl);
                pageList= ((ArrayList)pageDtl.get("DFLT_DISPLAY") == null)?new ArrayList():(ArrayList)pageDtl.get("DFLT_DISPLAY");
                if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                allowData=(String)pageDtlMap.get("dflt_val");
                }
                }
            if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
            }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
            conQ =conQ+" and d.grp_nme_idn=?"; 
            ary.add(dfgrpnmeidn);
            }
              if(!cnt.equals("xljf")){
            if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                conQ += " and (d.emp_idn= ? or d.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
                ary.add(dfNmeIdn);
                ary.add(dfNmeIdn);
            }}
                pageList= ((ArrayList)pageDtl.get("DFLT_COM") == null)?new ArrayList():(ArrayList)pageDtl.get("DFLT_COM");               
              if(pageList!=null && pageList.size() >0){
              for(int j=0;j<pageList.size();j++){
              pageDtlMap=(HashMap)pageList.get(j);
              String dfltCon=(String)pageDtlMap.get("fld_nme");
                conQ = conQ+ " "+dfltCon ;
               
              }
              } 
           String srchRef = " Insert into gt_srch_multi (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts ,rap_rte,rap_dis,cert_no, sk1,quot,cmp,pair_id,rmk ) " + 
                            " select c.pkt_ty , c.idn mstkIdn, c.vnm , c.dte , c.stt , 'Z' ,  b.qty , b.cts ,c.rap_rte,decode(c.rap_rte ,'1',null,'0',null, trunc((nvl(c.upr,c.cmp)/greatest(c.rap_rte,1)*100)-100, 2)),c.cert_no, c.sk1,nvl(c.upr,c.cmp),trunc(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte, 1), 2),d.emp_idn,a.rmk from mjan a , jandtl b , mstk c,mnme d " + 
                            " where a.idn = b.idn  and b.mstk_idn = c.idn and a.nme_idn=d.nme_idn " + 
                            "  and a.stt='IS' and b.stt = 'AP' and a.typ in ('EAP', 'IAP', 'WAP','OAP','LAP','MAP','BCAP','SAP','HAP','BAP','KAP','BIDAP') "+conQ ;
           if(!pkt_typ.equals(""))
               srchRef+=" and c.stt in('MKAV') and c.pkt_ty in('MX','MIX') "; 
           else
               srchRef+="  and c.stt in('MKAP','MKWA','LB_PRI_AP','MKSA') and c.pkt_ty in('NR','SMX') ";
           int ct = db.execUpd("insert gt",srchRef, ary);
            String pktPrp = "srch_pkg.POP_PKT_PRP_TEST(pMdl=>?,pTbl=>'GT_SRCH_MULTI')";
            ary = new ArrayList();
            ary.add("SAL_PND_VW");
            ct = db.execCall(" Srch Prp ", pktPrp, ary);
            
            ArrayList byrList = new ArrayList();
            ResultSet rs1      = null;
            String    getByr  = "select  distinct d.fnme byr,d.nme_idn from gt_srch_multi a , mjan b , jandtl c , mnme d " + 
                    " where a.stk_idn = c.mstk_idn and b.stt='IS' and c.stt='AP' and b.typ in ('EAP', 'IAP', 'WAP', 'LAP','MAP','BCAP','SAP','HAP','BAP','KAP') and c.idn = b.idn and d.nme_idn = b.nme_idn and a.flg= ? " + 
                    " order by byr ";
            ary = new ArrayList();
            ary.add("Z");

                ArrayList outLst1 = db.execSqlLst("byr", getByr, ary);
                PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
                rs1 = (ResultSet)outLst1.get(1);
            while (rs1.next()) {
                ByrDao byr = new ByrDao();

                byr.setByrIdn(rs1.getInt("nme_idn"));
                byr.setByrNme(rs1.getString("byr"));
                byrList.add(byr);
            }
            rs1.close(); pst1.close();
            udf.setByrList(byrList);
            
            if(allowData.equals("Y")){
                if(cnt.equals("ri"))
                itemHdr.add("Customer ID");    
                itemHdr.add("Sale Person");    
                itemHdr.add("Buyer");
                itemHdr.add("Id");    
                itemHdr.add("Packet No");    
                itemHdr.add("Date");    
                itemHdr.add("Status");    
                itemHdr.add("Memo Type");    
                itemHdr.add("Trm");    
                itemHdr.add("Days");    
                itemHdr.add("Qty");    
                itemHdr.add("Prc / Crt");    
                itemHdr.add("Raprte");    
                itemHdr.add("RapVlu");    
                itemHdr.add("Rapdis");    
                itemHdr.add("Quot");    
                itemHdr.add("Amount");
                
                itemHdrXL.add("Sale Person");    
                itemHdrXL.add("Buyer");
                itemHdrXL.add("Date");    
                itemHdrXL.add("Days");
                itemHdrXL.add("Packet No"); 
            String xlconQ="";
            if(cnt.equals("xljf")){
                xlconQ=" ,get_net (c.idn, c.mstk_idn,'M') STONEWISE_DIS,ROUND ( (NVL (c.FNL_SAL, c.quot) * c.cts) * get_net (c.idn, c.mstk_idn,'M') / 100, 2 ) NET_DISC_AMT,to_char(trunc(a.cts*nvl(c.fnl_sal, c.quot)/nvl(b.exh_rte,1), 2),'9999999999990.00')-ROUND ( (NVL (c.FNL_SAL, c.quot) * c.cts) * get_net (c.idn, c.mstk_idn,'M') / 100, 2 ) grandttl ";
            }
            String rsltQ = " select d.nme_idn,d.fnme byr , byr.get_nm(nvl(d.emp_idn,0)) sal , b.idn , to_char(b.dte, 'dd-Mon-rrrr') dte,round(trunc(sysdate)-trunc(b.dte)) days ,a.stt, a.vnm ,a.qty, to_char(trunc(a.cts ,2),'999999990.00') cts,trunc(nvl(c.fnl_sal, c.quot)/nvl(b.exh_rte, 1), 2) memoQuot, a.quot rte,to_char(trunc(a.cts*nvl(c.fnl_sal, c.quot)/nvl(b.exh_rte,1), 2),'9999999999990.00') amt,decode(nvl(b.exh_rte,1),1,'','#00CC66') color,to_char(trunc(a.cts,2) * a.rap_rte, '99999999990.00') rapVlu,a.rmk "+
                " ,decode(a.rap_rte, 1, '', decode(least(a.rap_dis, 0),0, '+'||a.rap_dis, a.rap_dis))  r_dis,a.rap_rte,a.cert_no,b.typ,byr.get_trms(b.nmerel_idn) trm"+xlconQ;
            for (int i = 0; i < vwPrpLst.size(); i++) {
                String prpNme=util.nvl((String)vwPrpLst.get(i));
                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                    fld += "00" + j;
                else if (j < 100)
                    fld += "0" + j;
                else if (j > 100)
                    fld += j;

               

                rsltQ += ", " + fld;
                 if(prpDspBlocked.contains(prpNme)){
                 }else{
                 itemHdr.add(prpNme);
                 itemHdrXL.add(prpNme);
                 if(prpNme.equals("COL-SHADE")){
                 itemHdrXL.add("Raprte");
                 itemHdrXL.add("Rapdis");
                 itemHdrXL.add("Prc / Crt");
                 itemHdrXL.add("Amount");
                 }
                 }
             }
            rsltQ = rsltQ +" from gt_srch_multi a , mjan b , jandtl c , mnme d  " + 
                    " where a.stk_idn = c.mstk_idn and b.stt='IS' and c.stt='AP' and b.typ in ('EAP', 'IAP','OAP', 'WAP', 'LAP','MAP','BCAP','SAP','HAP','BAP','KAP','BIDAP') and c.idn = b.idn and d.nme_idn = b.nme_idn and a.flg= ? " + 
                    " order by sal , byr ";
            ary = new ArrayList();
            ary.add("Z");

                ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                 while(rs.next()) {
                        HashMap pktPrpMap = new HashMap();
                        pktPrpMap.put("Customer ID", util.nvl(rs.getString("nme_idn")));
                        pktPrpMap.put("Buyer", util.nvl(rs.getString("byr")));
                        pktPrpMap.put("Sale Person",util.nvl(rs.getString("sal")));
                        pktPrpMap.put("Id",util.nvl(rs.getString("idn")));
                        pktPrpMap.put("Date",util.nvl(rs.getString("dte")));
                        pktPrpMap.put("Days",util.nvl(rs.getString("days")));
                        pktPrpMap.put("Qty",util.nvl(rs.getString("qty")));
                        pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                        pktPrpMap.put("Status",util.nvl(rs.getString("stt")));
                        pktPrpMap.put("Memo Type",util.nvl(rs.getString("typ")));
                        pktPrpMap.put("Quot",util.nvl(rs.getString("memoQuot")));
                        pktPrpMap.put("Prc / Crt",util.nvl(rs.getString("rte")));
                        pktPrpMap.put("Amount",util.nvl(rs.getString("amt")));
                        pktPrpMap.put("amtex",util.nvl(rs.getString("amt")));
                        pktPrpMap.put("Packet No",util.nvl(rs.getString("vnm")));
                        pktPrpMap.put("color",util.nvl(rs.getString("color")));
                        pktPrpMap.put("Rapdis",util.nvl(rs.getString("r_dis")));
                        pktPrpMap.put("Raprte",util.nvl(rs.getString("rap_rte")));
                        pktPrpMap.put("RapVlu", util.nvl(rs.getString("rapVlu")));
                        pktPrpMap.put("Trm",util.nvl(rs.getString("trm")));
                        pktPrpMap.put("Remark",util.nvl(rs.getString("rmk")));
                     if(cnt.equals("xljf")){
                     pktPrpMap.put("Net Dis",util.nvl(rs.getString("STONEWISE_DIS")));
                     pktPrpMap.put("Net Amt",util.nvl(rs.getString("NET_DISC_AMT")));
                     pktPrpMap.put("Grand Total",util.nvl(rs.getString("grandttl")));
                     }
                        for(int j=0; j < vwPrpLst.size(); j++){
                             String lprp = (String)vwPrpLst.get(j);
                              
                              String fld="prp_";
                              if(j < 9)
                                      fld="prp_00"+(j+1);
                              else    
                                      fld="prp_0"+(j+1);
                              
                            String val = util.nvl(rs.getString(fld)) ;
                            if (lprp.toUpperCase().equals("CRTWT"))
                                val = util.nvl(rs.getString("cts"));
                            if (lprp.toUpperCase().equals("RAP_DIS"))
                                val = util.nvl(rs.getString("r_dis"));
                            if (lprp.toUpperCase().equals("RAP_RTE"))
                                val = util.nvl(rs.getString("rap_rte"));
                            if(lprp.toUpperCase().equals("RTE"))
                                val = util.nvl(rs.getString("rte"));
                            if (lprp.toUpperCase().equals("CERT NO.") || lprp.toUpperCase().equals("CERT_NO")){
                            val = util.nvl(rs.getString("cert_no"));
                              lprp="CERT_NO";
                            }
                              
                              pktPrpMap.put(lprp, val);
                        }
                                      
                     pktList.add(pktPrpMap);
                 }
            rs.close(); pst.close();
                req.setAttribute("view", "Y");
            }
            udf.setPktList(pktList);
            udf.setReportNme("Sale Pending Report");
            ArrayList partyLst = srchQuery.getEmpList(req,res);
            udf.setEmpList(partyLst);
            req.setAttribute("allowData", allowData);
            session.setAttribute("pktList", pktList);
            session.setAttribute("itemHdr",itemHdr);
            session.setAttribute("itemHdrXL",itemHdrXL);
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_PND_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_PND_SRCH");info.setGncPrpLst(genricSrchLst);
            info.setGncPrpLst(genricSrchLst);  
            if(cnt.equals("hk"))
            util.groupcompany();    
                util.updAccessLog(req,res,"Report Action", "salePnd end");
           return am.findForward("loadSL");
            }
        }
        public ActionForward fetchsalePndOld(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                util.updAccessLog(req,res,"Report Action", "fetchsalePnd start");
            ReportForm udf = (ReportForm)af;
             GenericInterface genericInt = new GenericImpl();
            String byr = util.nvl((String)udf.getByrIdn());
            String group = util.nvl((String)udf.getValue("group"));
            String appfrmDte = util.nvl((String)udf.getValue("appfrmDte"));
            String apptoDte = util.nvl((String)udf.getValue("apptoDte"));
            String empIdn = util.nvl((String)udf.getValue("empIdn"));
            ArrayList vwPrpLst = genericInt.genericPrprVw(req,res,"SAL_PND_VW","SAL_PND_VW");
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            ArrayList rolenmLst=(ArrayList)info.getRoleLst();
            String usrFlg=util.nvl((String)info.getUsrFlg());
            String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_PND_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_PND_SRCH");info.setGncPrpLst(genricSrchLst);
            info.setGncPrpLst(genricSrchLst);
            SearchQuery srchQuery = new SearchQuery();
            String conQ="",filterconQ="";
            HashMap prp = info.getPrp();
            HashMap mprp = info.getMprp();
            ArrayList ary = new ArrayList(),selectCollection= new ArrayList();
                for(int i=0;i<genricSrchLst.size();i++){
                    ArrayList prplist =(ArrayList)genricSrchLst.get(i);
                    String lprp = (String)prplist.get(0);
                    String flg= (String)prplist.get(1);
                    String typ = util.nvl((String)mprp.get(lprp+"T"));
                    String prpSrt = lprp ;  
                    ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                    ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                    int indexprp = vwPrpLst.indexOf(lprp)+1;
                    if(flg.equals("M")) {
                        selectCollection=new ArrayList();
                        for(int j=0; j < lprpS.size(); j++) {
                        String lSrt = (String)lprpS.get(j);
                        String lVal = (String)lprpV.get(j);    
                        String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                        if(!reqVal1.equals("")){
                        selectCollection.add(reqVal1);
                        }
                        }
                        if(selectCollection.size()>0){
                            String selprp = selectCollection.toString();
                            selprp = selprp.replaceAll("\\[", "");
                            selprp = selprp.replaceAll("\\]", "").replaceAll(" ", "");
                            selprp=util.getVal(selprp);
                            String colPrp =  util.prpsrtcolumn("prp",indexprp); 
                            filterconQ += " and a."+colPrp+" in("+selprp+") ";
                        }
                    }else{
                    String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                    String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                    if(typ.equals("T")){
                        fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                        fldVal2 = fldVal1;
                    }
                    if(fldVal2.equals(""))
                    fldVal2=fldVal1;
                    if(!fldVal1.equals("") && !fldVal2.equals("")){
                    String colSrt = util.prpsrtcolumn("srt",indexprp);
                    filterconQ += " and a."+colSrt+" between '"+util.nvl((String)lprpS.get(lprpV.indexOf(fldVal1)))+"' and '"+util.nvl((String)lprpS.get(lprpV.indexOf(fldVal2)))+"' ";
                    }
                    }
                }    
                
            if(!group.equals("")){
            conQ =conQ+ " and d.grp_nme_idn= ? ";
            ary.add(group);
            }else{
            if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
            }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
            conQ =conQ+" and d.grp_nme_idn=?"; 
            ary.add(dfgrpnmeidn);
            }  
            }
            if (!empIdn.equals("0") && !empIdn.equals("")) {
                    conQ += " and a.pair_id = ? ";
                    ary.add(empIdn);
            }
                String xlconQ="";
                if(cnt.equals("xljf")){
                    xlconQ=" ,get_net (c.idn, c.mstk_idn,'M') STONEWISE_DIS,ROUND ( (NVL (c.FNL_SAL, c.quot) * c.cts) * get_net (c.idn, c.mstk_idn,'M') / 100, 2 ) NET_DISC_AMT,to_char(trunc(a.cts*nvl(c.fnl_sal, c.quot)/nvl(b.exh_rte,1), 2),'9999999999990.00')-ROUND ( (NVL (c.FNL_SAL, c.quot) * c.cts) * get_net (c.idn, c.mstk_idn,'M') / 100, 2 ) grandttl ";
                }
            String rsltQ = " select d.fnme byr , byr.get_nm(nvl(d.emp_idn,0)) sal , b.idn , to_char(b.dte, 'dd-Mon-rrrr') dte ,round(trunc(sysdate)-trunc(b.dte)) days , a.vnm ,a.stt,a.qty, to_char(trunc(a.cts ,2),'999999990.00') cts,nvl(c.fnl_sal, c.quot) memoQuot, a.quot rte,to_char(trunc(a.cts,2) * Nvl(c.Fnl_Sal, c.Quot), '9999999990.00') amt,decode(nvl(b.exh_rte,1),1,'','#00CC66') color,to_char(trunc(a.cts,2) * a.rap_rte, '99999999990.00') rapVlu,a.rmk "+
            " ,decode(a.rap_rte, 1, '', decode(least(a.rap_dis, 0),0, '+'||a.rap_dis, a.rap_dis))  r_dis,a.rap_rte,a.cert_no,b.typ,byr.get_trms(b.nmerel_idn) trm"+xlconQ;
                for (int i = 0; i < vwPrpLst.size(); i++) {
                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                    fld += "00" + j;
                else if (j < 100)
                    fld += "0" + j;
                else if (j > 100)
                    fld += j;

               

                rsltQ += ", " + fld;
               
             }
            rsltQ = rsltQ +" from gt_srch_multi a , mjan b , jandtl c , mnme d " + 
                    " where a.stk_idn = c.mstk_idn and b.stt='IS' and c.stt='AP' and b.typ in ('EAP','OAP', 'IAP', 'WAP', 'LAP','MAP','BCAP','SAP','HAP','BAP','KAP','BIDAP') and c.idn = b.idn and d.nme_idn = b.nme_idn "+conQ+" and a.flg= ? ";
            ary.add("Z");
            if(!byr.equals("") && !byr.equals("0")){
            rsltQ = rsltQ +" and d.nme_idn=? ";
            ary.add(byr);
            }
            if(!appfrmDte.equals("") && !apptoDte.equals("")){
            rsltQ = rsltQ + " and trunc(c.dte) between trunc(to_date('"+appfrmDte+"','dd-mm-rrrr')) and trunc(to_date('"+apptoDte+"','dd-mm-rrrr')) ";
            }
            rsltQ = rsltQ +filterconQ+" order by sal , byr ";

                ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
            ArrayList pktList = new  ArrayList();
             while(rs.next()) {
                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap.put("byr", util.nvl(rs.getString("byr")));
                    pktPrpMap.put("sal",util.nvl(rs.getString("sal")));
                    pktPrpMap.put("memoIdn",util.nvl(rs.getString("idn")));
                    pktPrpMap.put("dte",util.nvl(rs.getString("dte")));
                    pktPrpMap.put("days",util.nvl(rs.getString("days")));
                 pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                 pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                 pktPrpMap.put("stt",util.nvl(rs.getString("stt")));
                 pktPrpMap.put("typ",util.nvl(rs.getString("typ")));
                 pktPrpMap.put("memoQuot",util.nvl(rs.getString("memoQuot")));
                 pktPrpMap.put("rte",util.nvl(rs.getString("rte")));
                    pktPrpMap.put("amt",util.nvl(rs.getString("amt")));
                    pktPrpMap.put("amtex",util.nvl(rs.getString("amt")));
                    pktPrpMap.put("vnm",util.nvl(rs.getString("vnm")));
                    pktPrpMap.put("color",util.nvl(rs.getString("color")));
                 pktPrpMap.put("r_dis",util.nvl(rs.getString("r_dis")));
                 pktPrpMap.put("rap_rte",util.nvl(rs.getString("rap_rte")));
                 pktPrpMap.put("rapVlu", util.nvl(rs.getString("rapVlu")));
                 pktPrpMap.put("trm",util.nvl(rs.getString("trm")));
                 pktPrpMap.put("rmk",util.nvl(rs.getString("rmk")));
                 if(cnt.equals("xljf")){
                 pktPrpMap.put("STONEWISE_DIS",util.nvl(rs.getString("STONEWISE_DIS")));
                 pktPrpMap.put("NET_DISC_AMT",util.nvl(rs.getString("NET_DISC_AMT")));
                 pktPrpMap.put("grandttl",util.nvl(rs.getString("grandttl")));
                 }
                    for(int j=0; j < vwPrpLst.size(); j++){
                         String lprp = (String)vwPrpLst.get(j);
                          
                          String fld="prp_";
                          if(j < 9)
                                  fld="prp_00"+(j+1);
                          else    
                                  fld="prp_0"+(j+1);
                          
                          String val = util.nvl(rs.getString(fld)) ;
                        if (lprp.toUpperCase().equals("CRTWT"))
                            val = util.nvl(rs.getString("cts"));
                        if (lprp.toUpperCase().equals("RAP_DIS"))
                            val = util.nvl(rs.getString("r_dis"));
                        if (lprp.toUpperCase().equals("RAP_RTE"))
                            val = util.nvl(rs.getString("rap_rte"));
                        if(lprp.toUpperCase().equals("RTE"))
                            val = util.nvl(rs.getString("rte"));
                        if (lprp.toUpperCase().equals("CERT NO.") || lprp.toUpperCase().equals("CERT_NO")){
                        val = util.nvl(rs.getString("cert_no"));
                          lprp="CERT_NO";
                        }
                          pktPrpMap.put(lprp, val);
                    }
                                  
                 pktList.add(pktPrpMap);
             }
            rs.close(); pst.close();
            ArrayList partyLst = srchQuery.getEmpList(req,res);
            udf.setEmpList(partyLst);
            udf.setPktList(pktList);
            udf.setReportNme("Sale Pending Report");
                util.updAccessLog(req,res,"Report Action", "fetchsalePnd end");
        return am.findForward("loadSL");
            }
        }
    public ActionForward fetchsalePnd(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Report Action", "fetchsalePnd start");
        ReportForm udf = (ReportForm)af;
         GenericInterface genericInt = new GenericImpl();
        String byr = util.nvl((String)udf.getByrIdn());
        String group = util.nvl((String)udf.getValue("group"));
        String appfrmDte = util.nvl((String)udf.getValue("appfrmDte"));
        String apptoDte = util.nvl((String)udf.getValue("apptoDte"));
        String empIdn = util.nvl((String)udf.getValue("empIdn"));
        String type = util.nvl((String)udf.getValue("typ"));
        ArrayList vwPrpLst = genericInt.genericPrprVw(req,res,"SAL_PND_VW","SAL_PND_VW");
        ArrayList pktList = new  ArrayList();
        ArrayList itemHdr = new ArrayList();    
        ArrayList itemHdrXL = new ArrayList();    
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        ArrayList rolenmLst=(ArrayList)info.getRoleLst();
        String usrFlg=util.nvl((String)info.getUsrFlg());
        String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
        ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_PND_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_PND_SRCH");info.setGncPrpLst(genricSrchLst);
        ArrayList prpDspBlocked = info.getPageblockList();
        SearchQuery srchQuery = new SearchQuery();
        String conQ="",filterconQ="";
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        ArrayList ary = new ArrayList(),selectCollection= new ArrayList();
            for(int i=0;i<genricSrchLst.size();i++){
                ArrayList prplist =(ArrayList)genricSrchLst.get(i);
                String lprp = (String)prplist.get(0);
                String flg= (String)prplist.get(1);
                String typ = util.nvl((String)mprp.get(lprp+"T"));
                String prpSrt = lprp ;  
                ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                int indexprp = vwPrpLst.indexOf(lprp)+1;
                if(flg.equals("M")) {
                    selectCollection=new ArrayList();
                    for(int j=0; j < lprpS.size(); j++) {
                    String lSrt = (String)lprpS.get(j);
                    String lVal = (String)lprpV.get(j);    
                    String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                    if(!reqVal1.equals("")){
                    selectCollection.add(reqVal1);
                    }
                    }
                    if(selectCollection.size()>0){
                        String selprp = selectCollection.toString();
                        selprp = selprp.replaceAll("\\[", "");
                        selprp = selprp.replaceAll("\\]", "").replaceAll(" ", "");
                        selprp=util.getVal(selprp);
                        String colPrp =  util.prpsrtcolumn("prp",indexprp); 
                        filterconQ += " and a."+colPrp+" in("+selprp+") ";
                    }
                }else{
                String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                if(typ.equals("T")){
                    fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                    fldVal2 = fldVal1;
                }
                if(fldVal2.equals(""))
                fldVal2=fldVal1;
                if(!fldVal1.equals("") && !fldVal2.equals("")){
                String colSrt = util.prpsrtcolumn("srt",indexprp);
                filterconQ += " and a."+colSrt+" between '"+util.nvl((String)lprpS.get(lprpV.indexOf(fldVal1)))+"' and '"+util.nvl((String)lprpS.get(lprpV.indexOf(fldVal2)))+"' ";
                }
                }
            }    
            
        if(!group.equals("")){
        conQ =conQ+ " and d.grp_nme_idn= ? ";
        ary.add(group);
        }else{
        if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
        }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
        conQ =conQ+" and d.grp_nme_idn=?"; 
        ary.add(dfgrpnmeidn);
        }  
        }
        if (!empIdn.equals("0") && !empIdn.equals("")) {
                conQ += " and a.pair_id = ? ";
                ary.add(empIdn);
        }
            if(!type.equals("") && !type.equals("0")){
              conQ += " and b.typ= ? ";
              ary.add(type);
            }
            String xlconQ="";
            if(cnt.equals("xljf")){
                xlconQ=" ,get_net (c.idn, c.mstk_idn,'M') STONEWISE_DIS,ROUND ( (NVL (c.FNL_SAL, c.quot) * c.cts) * get_net (c.idn, c.mstk_idn,'M') / 100, 2 ) NET_DISC_AMT,to_char(trunc(a.cts*nvl(c.fnl_sal, c.quot)/nvl(b.exh_rte,1), 2),'9999999999990.00')-ROUND ( (NVL (c.FNL_SAL, c.quot) * c.cts) * get_net (c.idn, c.mstk_idn,'M') / 100, 2 ) grandttl ";
            }
            
            
            if(cnt.equals("ri"))
            itemHdr.add("Customer ID");  
            itemHdr.add("Sale Person");    
            itemHdr.add("Buyer");
            itemHdr.add("Id");    
            itemHdr.add("Packet No");    
            itemHdr.add("Date");    
            itemHdr.add("Status");    
            itemHdr.add("Memo Type");    
            itemHdr.add("Trm");    
            itemHdr.add("Days");    
            itemHdr.add("Qty");    
            itemHdr.add("Prc / Crt");    
            itemHdr.add("Raprte");    
            itemHdr.add("RapVlu");    
            itemHdr.add("Rapdis");    
            itemHdr.add("Quot");    
            itemHdr.add("Amount");
            
            
            itemHdrXL.add("Sale Person");    
            itemHdrXL.add("Buyer");
            itemHdrXL.add("Date");    
            itemHdrXL.add("Days");
            itemHdrXL.add("Packet No"); 
            

            
        String rsltQ = " select d.nme_idn,d.fnme byr , byr.get_nm(nvl(d.emp_idn,0)) sal , b.idn , to_char(b.dte, 'dd-Mon-rrrr') dte ,round(trunc(sysdate)-trunc(b.dte)) days , a.vnm ,a.stt,a.qty, to_char(trunc(a.cts ,2),'999999990.00') cts,nvl(c.fnl_sal, c.quot) memoQuot, a.quot rte,to_char(trunc(a.cts,2) * Nvl(c.Fnl_Sal, c.Quot), '9999999990.00') amt,decode(nvl(b.exh_rte,1),1,'','#00CC66') color,to_char(trunc(a.cts,2) * a.rap_rte, '99999999990.00') rapVlu,a.rmk "+
        " ,decode(a.rap_rte ,'1',null,'0',null, trunc((nvl(c.fnl_sal, c.quot)/greatest(a.rap_rte,1)*100)-100, 2))  r_dis,a.rap_rte,a.cert_no,b.typ,byr.get_trms(b.nmerel_idn) trm"+xlconQ;
            for (int i = 0; i < vwPrpLst.size(); i++) {
            String prpNme = (String)vwPrpLst.get(i);
            String fld = "prp_";
            int j = i + 1;
            if (j < 10)
                fld += "00" + j;
            else if (j < 100)
                fld += "0" + j;
            else if (j > 100)
                fld += j;

            if(prpDspBlocked.contains(prp)){
            }else{
            itemHdr.add(prpNme);
            itemHdrXL.add(prpNme);
            if(prpNme.equals("COL-SHADE")){
            itemHdrXL.add("Raprte");    
            itemHdrXL.add("Rapdis");   
            itemHdrXL.add("Prc / Crt");    
            itemHdrXL.add("Amount");    
            }
            }    
            rsltQ += ", " + fld;
           
         }
            
        itemHdr.add("Remark");        
        if(cnt.equals("xljf")){
        itemHdr.add("Net Dis");            
        itemHdr.add("Net Amt");            
        itemHdr.add("Grand Total");            
        }    
        itemHdrXL.add("Id");    
        itemHdrXL.add("Remark");    
        itemHdrXL.add("Status");    
        itemHdrXL.add("Memo Type");    
        itemHdrXL.add("Trm");    
        itemHdrXL.add("Qty");        
            
        rsltQ = rsltQ +" from gt_srch_multi a , mjan b , jandtl c , mnme d " + 
                " where a.stk_idn = c.mstk_idn and b.stt='IS' and c.stt='AP' and b.typ in ('EAP', 'IAP', 'WAP', 'LAP','MAP','BCAP','OAP','SAP','HAP','BAP','KAP','BIDAP') and c.idn = b.idn and d.nme_idn = b.nme_idn "+conQ+" and a.flg= ? ";
        ary.add("Z");
        if(!byr.equals("") && !byr.equals("0")){
        rsltQ = rsltQ +" and d.nme_idn=? ";
        ary.add(byr);
        }
        if(!appfrmDte.equals("") && !apptoDte.equals("")){
        rsltQ = rsltQ + " and trunc(c.dte) between trunc(to_date('"+appfrmDte+"','dd-mm-rrrr')) and trunc(to_date('"+apptoDte+"','dd-mm-rrrr')) ";
        }
        rsltQ = rsltQ +filterconQ+" order by sal , byr ";

            ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
         while(rs.next()) {
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("Customer ID", util.nvl(rs.getString("nme_idn")));
                pktPrpMap.put("Buyer", util.nvl(rs.getString("byr")));
                pktPrpMap.put("Sale Person",util.nvl(rs.getString("sal")));
                pktPrpMap.put("Id",util.nvl(rs.getString("idn")));
                pktPrpMap.put("Date",util.nvl(rs.getString("dte")));
                pktPrpMap.put("Days",util.nvl(rs.getString("days")));
                pktPrpMap.put("Qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                pktPrpMap.put("Status",util.nvl(rs.getString("stt")));
                pktPrpMap.put("Memo Type",util.nvl(rs.getString("typ")));
                pktPrpMap.put("Quot",util.nvl(rs.getString("memoQuot")));
                pktPrpMap.put("Prc / Crt",util.nvl(rs.getString("rte")));
                pktPrpMap.put("Amount",util.nvl(rs.getString("amt")));
                pktPrpMap.put("amtex",util.nvl(rs.getString("amt")));
                pktPrpMap.put("Packet No",util.nvl(rs.getString("vnm")));
                pktPrpMap.put("color",util.nvl(rs.getString("color")));
                pktPrpMap.put("Rapdis",util.nvl(rs.getString("r_dis")));
                pktPrpMap.put("Raprte",util.nvl(rs.getString("rap_rte")));
                pktPrpMap.put("RapVlu", util.nvl(rs.getString("rapVlu")));
                pktPrpMap.put("Trm",util.nvl(rs.getString("trm")));
                pktPrpMap.put("Remark",util.nvl(rs.getString("rmk")));
             if(cnt.equals("xljf")){
             pktPrpMap.put("Net Dis",util.nvl(rs.getString("STONEWISE_DIS")));
             pktPrpMap.put("Net Amt",util.nvl(rs.getString("NET_DISC_AMT")));
             pktPrpMap.put("Grand Total",util.nvl(rs.getString("grandttl")));
             }
                for(int j=0; j < vwPrpLst.size(); j++){
                     String lprp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                    String val = util.nvl(rs.getString(fld)) ;
                    if (lprp.toUpperCase().equals("CRTWT"))
                        val = util.nvl(rs.getString("cts"));
                    if (lprp.toUpperCase().equals("RAP_DIS"))
                        val = util.nvl(rs.getString("r_dis"));
                    if (lprp.toUpperCase().equals("RAP_RTE"))
                        val = util.nvl(rs.getString("rap_rte"));
                    if(lprp.toUpperCase().equals("RTE"))
                        val = util.nvl(rs.getString("rte"));
                    if (lprp.toUpperCase().equals("CERT NO.") || lprp.toUpperCase().equals("CERT_NO")){
                    val = util.nvl(rs.getString("cert_no"));
                      lprp="CERT_NO";
                    }
                      
                      pktPrpMap.put(lprp, val);
                }
                              
             pktList.add(pktPrpMap);
         }
        rs.close(); pst.close();
        ArrayList partyLst = srchQuery.getEmpList(req,res);
        udf.setEmpList(partyLst);
        req.setAttribute("view","Y");
        session.setAttribute("pktList", pktList);
        session.setAttribute("itemHdr",itemHdr);
        session.setAttribute("itemHdrXL",itemHdrXL);
        udf.setReportNme("Sale Pending Report");
            util.updAccessLog(req,res,"Report Action", "fetchsalePnd end");
    return am.findForward("loadSL");
        }
    }
        
    public ActionForward csPnd(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                util.updAccessLog(req,res,"Report Action", "csPnd start");
            ReportForm udf = (ReportForm)af;
            GenericInterface genericInt = new GenericImpl();
            udf.resetALL();
            String pkt_typ=util.nvl(req.getParameter("PKT_TYP"));
            String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
            String dfNmeIdn=util.nvl((String)info.getDfNmeIdn()); 
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            ArrayList rolenmLst=(ArrayList)info.getRoleLst();
            String usrFlg=util.nvl((String)info.getUsrFlg());
            String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
            SearchQuery srchQuery = new SearchQuery();
            db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
            ArrayList vwPrpLst = genericInt.genericPrprVw(req,res,"SAL_PND_VW","SAL_PND_VW");
            String conQ="";
            String allowData="Y";
            ArrayList pktList = new  ArrayList();
            ArrayList ary = new ArrayList();
                ArrayList pageList=new ArrayList();
                HashMap pageDtlMap=new HashMap();
                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("CSPENDING_REPORT");
                if(pageDtl==null || pageDtl.size()==0){
                pageDtl=new HashMap();
                pageDtl=util.pagedef("CSPENDING_REPORT");
                allPageDtl.put("CSPENDING_REPORT",pageDtl);
                }
                info.setPageDetails(allPageDtl);
                pageList= ((ArrayList)pageDtl.get("DFLT_DISPLAY") == null)?new ArrayList():(ArrayList)pageDtl.get("DFLT_DISPLAY"); 
                if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                allowData=(String)pageDtlMap.get("dflt_val");
                }
                }
            if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
            }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
            conQ =conQ+" and d.grp_nme_idn=?"; 
            ary.add(dfgrpnmeidn);
            }
            
            if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                conQ += " and (d.emp_idn= ? or d.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
                ary.add(dfNmeIdn);
                ary.add(dfNmeIdn);
            }
                
           String srchRef = " Insert into gt_srch_multi (pkt_ty, stk_idn, vnm,cert_no, pkt_dte, stt , flg , qty , cts ,rap_rte,rap_dis, sk1,quot,cmp,pair_id ) " + 
                            " select c.pkt_ty , c.idn mstkIdn, c.vnm ,c.cert_no, c.dte , c.stt , 'Z' ,  b.qty , b.cts ,c.rap_rte,decode(c.rap_rte ,'1',null,'0',null, trunc((nvl(c.upr,c.cmp)/greatest(c.rap_rte,1)*100)-100, 2)), c.sk1,nvl(c.upr,c.cmp),trunc(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte, 1), 2),d.emp_idn from mjan a , jandtl b , mstk c,mnme d " + 
                            " where a.idn = b.idn  and b.mstk_idn = c.idn and a.nme_idn=d.nme_idn " + 
                            "  and b.stt = 'IS' and a.typ in ('CS') "+conQ ;
           if(!pkt_typ.equals(""))
               srchRef+=" and c.stt in('MKAV') and c.pkt_ty in('MX','MIX') "; 
           else
               srchRef+="  and c.stt in('MKCS') and c.pkt_ty in('NR','SMX') ";
           int ct = db.execUpd("insert gt",srchRef, ary);
            String pktPrp = "srch_pkg.POP_PKT_PRP_TEST(pMdl=>?,pTbl=>'GT_SRCH_MULTI')";
            ary = new ArrayList();
            ary.add("SAL_PND_VW");
            ct = db.execCall(" Srch Prp ", pktPrp, ary);
            
            ArrayList byrList = new ArrayList();
            ResultSet rs1      = null;
            String    getByr  = "select  distinct d.fnme byr,d.nme_idn from gt_srch_multi a , mjan b , jandtl c , mnme d " + 
                    " where a.stk_idn = c.mstk_idn and c.stt='IS' and b.typ in ('CS') and c.idn = b.idn and d.nme_idn = b.nme_idn and a.flg= ? " + 
                    " order by byr ";
            ary = new ArrayList();
            ary.add("Z");

                ArrayList outLst1 = db.execSqlLst("byr", getByr, ary);
                PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
                rs1 = (ResultSet)outLst1.get(1);
            while (rs1.next()) {
                ByrDao byr = new ByrDao();

                byr.setByrIdn(rs1.getInt("nme_idn"));
                byr.setByrNme(rs1.getString("byr"));
                byrList.add(byr);
            }
            rs1.close(); pst1.close();
            udf.setByrList(byrList);
            
            if(allowData.equals("Y")){
            String rsltQ = " select d.fnme byr , byr.get_nm(nvl(d.emp_idn,0)) sal , b.idn , to_char(b.dte, 'dd-Mon-rrrr') dte,round(trunc(sysdate)-trunc(b.dte)) days , a.vnm , to_char(trunc(a.cts ,2),'999999990.00') cts,trunc(nvl(c.fnl_sal, c.quot)/nvl(b.exh_rte, 1), 2) memoQuot, a.cmp rte,to_char(trunc(a.cts*nvl(c.fnl_sal, c.quot)/nvl(b.exh_rte,1), 2),'9999999999990.00') amt,decode(nvl(b.exh_rte,1),1,'','#00CC66') color,to_char(trunc(a.cts,2) * a.rap_rte, '99999999990.00') rapVlu "+
            ",decode(a.rap_rte, 1, '', decode(least(a.rap_dis, 0),0, '+'||a.rap_dis, a.rap_dis))  r_dis,a.rap_rte,a.cert_no ";
            for (int i = 0; i < vwPrpLst.size(); i++) {
                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                    fld += "00" + j;
                else if (j < 100)
                    fld += "0" + j;
                else if (j > 100)
                    fld += j;

               

                rsltQ += ", " + fld;
               
             }
            rsltQ = rsltQ +" from gt_srch_multi a , mjan b , jandtl c , mnme d  " + 
                    " where a.stk_idn = c.mstk_idn and c.stt='IS' and b.typ in ('CS') and c.idn = b.idn and d.nme_idn = b.nme_idn and a.flg= ? " + 
                    " order by sal , byr ";
            ary = new ArrayList();
            ary.add("Z");

                ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap.put("byr", util.nvl(rs.getString("byr")));
                    pktPrpMap.put("sal",util.nvl(rs.getString("sal")));
                    pktPrpMap.put("memoIdn",util.nvl(rs.getString("idn")));
                    pktPrpMap.put("dte",util.nvl(rs.getString("dte")));
                 pktPrpMap.put("days",util.nvl(rs.getString("days")));
                 pktPrpMap.put("memoQuot",util.nvl(rs.getString("memoQuot")));
                 pktPrpMap.put("rte",util.nvl(rs.getString("rte")));
                 pktPrpMap.put("amt",util.nvl(rs.getString("amt")));
                 pktPrpMap.put("amtex",util.nvl(rs.getString("amt")));
                 pktPrpMap.put("color",util.nvl(rs.getString("color")));
                  
                    pktPrpMap.put("vnm",util.nvl(rs.getString("vnm")));
                 pktPrpMap.put("rapVlu", util.nvl(rs.getString("rapVlu")));
                    for(int j=0; j < vwPrpLst.size(); j++){
                         String prp = (String)vwPrpLst.get(j);
                          
                          String fld="prp_";
                          if(j < 9)
                                  fld="prp_00"+(j+1);
                          else    
                                  fld="prp_0"+(j+1);
                          
                          String val = util.nvl(rs.getString(fld)) ;
                        if (prp.toUpperCase().equals("CRTWT"))
                            val = util.nvl(rs.getString("cts"));
                        if (prp.toUpperCase().equals("RAP_DIS"))
                            val = util.nvl(rs.getString("r_dis"));
                        if (prp.toUpperCase().equals("RAP_RTE"))
                            val = util.nvl(rs.getString("rap_rte"));
                        if(prp.toUpperCase().equals("RTE"))
                            val = util.nvl(rs.getString("rte"));
                        if (prp.toUpperCase().equals("CERT NO.") || prp.toUpperCase().equals("CERT_NO")){
                        val = util.nvl(rs.getString("cert_no"));
                          prp="CERT_NO";
                        }
                          pktPrpMap.put(prp, val);
                    }
                                  
                 pktList.add(pktPrpMap);
             }
            rs.close(); pst.close();
            }
            udf.setPktList(pktList);
            udf.setReportNme("Consignment Pending Report");
            ArrayList partyLst = srchQuery.getEmpList(req,res);
            udf.setEmpList(partyLst);
            req.setAttribute("allowData", allowData);
            ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_PND_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_PND_SRCH");
            info.setGncPrpLst(assortSrchList);  
                if(cnt.equals("hk"))
                util.groupcompany();
                util.updAccessLog(req,res,"Report Action", "csPnd end");
           return am.findForward("loadCS");
            }
        }
        public ActionForward fetchcsPnd(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                util.updAccessLog(req,res,"Report Action", "fetchcsPnd start");
            ReportForm udf = (ReportForm)af;
            GenericInterface genericInt = new GenericImpl();
            String byr = util.nvl((String)udf.getByrIdn());
            String group = util.nvl((String)udf.getValue("group"));
            String appfrmDte = util.nvl((String)udf.getValue("appfrmDte"));
            String apptoDte = util.nvl((String)udf.getValue("apptoDte"));
            String empIdn = util.nvl((String)udf.getValue("empIdn"));
           
            ArrayList vwPrpLst = genericInt.genericPrprVw(req,res,"SAL_PND_VW","SAL_PND_VW");
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            ArrayList rolenmLst=(ArrayList)info.getRoleLst();
            String usrFlg=util.nvl((String)info.getUsrFlg());
            String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_PND_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_PND_SRCH");
            info.setGncPrpLst(genricSrchLst);
            SearchQuery srchQuery = new SearchQuery();
            String conQ="",filterconQ="";
            HashMap prp = info.getPrp();
            HashMap mprp = info.getMprp();
            ArrayList ary = new ArrayList(),selectCollection= new ArrayList();
                for(int i=0;i<genricSrchLst.size();i++){
                    ArrayList prplist =(ArrayList)genricSrchLst.get(i);
                    String lprp = (String)prplist.get(0);
                    String flg= (String)prplist.get(1);
                    String typ = util.nvl((String)mprp.get(lprp+"T"));
                    String prpSrt = lprp ;  
                    ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                    ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                    int indexprp = vwPrpLst.indexOf(lprp)+1;
                    if(flg.equals("M")) {
                        selectCollection=new ArrayList();
                        for(int j=0; j < lprpS.size(); j++) {
                        String lSrt = (String)lprpS.get(j);
                        String lVal = (String)lprpV.get(j);    
                        String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                        if(!reqVal1.equals("")){
                        selectCollection.add(reqVal1);
                        }
                        }
                        if(selectCollection.size()>0){
                            String selprp = selectCollection.toString();
                            selprp = selprp.replaceAll("\\[", "");
                            selprp = selprp.replaceAll("\\]", "").replaceAll(" ", "");
                            selprp=util.getVal(selprp);
                            String colPrp =  util.prpsrtcolumn("prp",indexprp); 
                            filterconQ += " and a."+colPrp+" in("+selprp+") ";
                        }
                    }else{
                    String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                    String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                    if(typ.equals("T")){
                        fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                        fldVal2 = fldVal1;
                    }
                    if(fldVal2.equals(""))
                    fldVal2=fldVal1;
                    if(!fldVal1.equals("") && !fldVal2.equals("")){
                    String colSrt = util.prpsrtcolumn("srt",indexprp);
                    filterconQ += " and a."+colSrt+" between '"+util.nvl((String)lprpS.get(lprpV.indexOf(fldVal1)))+"' and '"+util.nvl((String)lprpS.get(lprpV.indexOf(fldVal2)))+"' ";
                    }
                    }
                }    
                
            if(!group.equals("")){
            conQ =conQ+ " and d.grp_nme_idn= ? ";
            ary.add(group);
            }else{
            if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
            }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
            conQ =conQ+" and d.grp_nme_idn=?"; 
            ary.add(dfgrpnmeidn);
            }  
            }
            if (!empIdn.equals("0") && !empIdn.equals("")) {
                    conQ += " and a.pair_id = ? ";
                    ary.add(empIdn);
            }
            String rsltQ = " select d.fnme byr , byr.get_nm(nvl(d.emp_idn,0)) sal , b.idn , to_char(b.dte, 'dd-Mon-rrrr') dte,round(trunc(sysdate)-trunc(b.dte)) days , a.vnm , to_char(trunc(a.cts ,2),'999999990.00') cts,trunc(nvl(c.fnl_sal, c.quot)/nvl(b.exh_rte, 1), 2) memoQuot, a.cmp rte,to_char(trunc(a.cts*nvl(c.fnl_sal, c.quot)/nvl(b.exh_rte,1), 2),'9999999999990.00') amt,decode(nvl(b.exh_rte,1),1,'','#00CC66') color,to_char(trunc(a.cts,2) * a.rap_rte, '99999999990.00') rapVlu "+
            ",decode(a.rap_rte, 1, '', decode(least(a.rap_dis, 0),0, '+'||a.rap_dis, a.rap_dis))  r_dis,a.rap_rte,a.cert_no ";
            for (int i = 0; i < vwPrpLst.size(); i++) {
                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                    fld += "00" + j;
                else if (j < 100)
                    fld += "0" + j;
                else if (j > 100)
                    fld += j;

               

                rsltQ += ", " + fld;
               
             }
            rsltQ = rsltQ +" from gt_srch_multi a , mjan b , jandtl c , mnme d " + 
                    " where a.stk_idn = c.mstk_idn and c.stt='IS' and b.typ in ('CS') and c.idn = b.idn and d.nme_idn = b.nme_idn "+conQ+" and a.flg= ? ";
            ary.add("Z");
            if(!byr.equals("") && !byr.equals("0")){
            rsltQ = rsltQ +" and d.nme_idn=? ";
            ary.add(byr);
            }
            if(!appfrmDte.equals("") && !apptoDte.equals("")){
            rsltQ = rsltQ + " and trunc(c.dte) between trunc(to_date('"+appfrmDte+"','dd-mm-rrrr')) and trunc(to_date('"+apptoDte+"','dd-mm-rrrr')) ";
            }
            rsltQ = rsltQ +filterconQ+" order by sal , byr ";

                ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
            ArrayList pktList = new  ArrayList();
             while(rs.next()) {
                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap.put("byr", util.nvl(rs.getString("byr")));
                    pktPrpMap.put("sal",util.nvl(rs.getString("sal")));
                    pktPrpMap.put("memoIdn",util.nvl(rs.getString("idn")));
                    pktPrpMap.put("dte",util.nvl(rs.getString("dte")));
                    pktPrpMap.put("days",util.nvl(rs.getString("days")));
                    pktPrpMap.put("memoQuot",util.nvl(rs.getString("memoQuot")));
                    pktPrpMap.put("rte",util.nvl(rs.getString("rte")));
                    pktPrpMap.put("amt",util.nvl(rs.getString("amt")));
                    pktPrpMap.put("amtex",util.nvl(rs.getString("amt")));
                    pktPrpMap.put("vnm",util.nvl(rs.getString("vnm")));
                    pktPrpMap.put("color",util.nvl(rs.getString("color")));
                    pktPrpMap.put("rapVlu", util.nvl(rs.getString("rapVlu")));
                    for(int j=0; j < vwPrpLst.size(); j++){
                         String lprp = (String)vwPrpLst.get(j);
                          
                          String fld="prp_";
                          if(j < 9)
                                  fld="prp_00"+(j+1);
                          else    
                                  fld="prp_0"+(j+1);
                          
                          String val = util.nvl(rs.getString(fld)) ;
                        if (lprp.toUpperCase().equals("CRTWT"))
                            val = util.nvl(rs.getString("cts"));
                        if (lprp.toUpperCase().equals("RAP_DIS"))
                            val = util.nvl(rs.getString("r_dis"));
                        if (lprp.toUpperCase().equals("RAP_RTE"))
                            val = util.nvl(rs.getString("rap_rte"));
                        if(lprp.toUpperCase().equals("RTE"))
                            val = util.nvl(rs.getString("rte"));
                        if (lprp.toUpperCase().equals("CERT NO.") || lprp.toUpperCase().equals("CERT_NO")){
                        val = util.nvl(rs.getString("cert_no"));
                          lprp="CERT_NO";
                        }
                          pktPrpMap.put(lprp, val);
                    }
                                  
                 pktList.add(pktPrpMap);
             }
            rs.close(); pst.close();
            ArrayList partyLst = srchQuery.getEmpList(req,res);
            udf.setEmpList(partyLst);
            udf.setPktList(pktList);
            udf.setReportNme("Consignment Pending Report");
                util.updAccessLog(req,res,"Report Action", "fetchcsPnd end");
        return am.findForward("loadCS");
            }
        }
    public ActionForward DlvPnd(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                util.updAccessLog(req,res,"Report Action", "DlvPnd start");
            ReportForm udf = (ReportForm)af;
            udf.resetALL();
            String pkt_typ=util.nvl(req.getParameter("PKT_TYP"));
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            ArrayList rolenmLst=(ArrayList)info.getRoleLst();
            String usrFlg=util.nvl((String)info.getUsrFlg());
            String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
            String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
            String dfNmeIdn=util.nvl((String)info.getDfNmeIdn()); 
            String conQ="";
            String allowData="Y";
            ArrayList byrList = new ArrayList();
            ArrayList params = new ArrayList();
            ResultSet rs1      = null;
                ArrayList pageList=new ArrayList();
                HashMap pageDtlMap=new HashMap();
                ArrayList pktList = new  ArrayList();
            SearchQuery srchQuery = new SearchQuery();
            GenericInterface genericInt = new GenericImpl();
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("PENDING_REPORT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("PENDING_REPORT");
            allPageDtl.put("PENDING_REPORT",pageDtl);
            }
            info.setPageDetails(allPageDtl);
                pageList= ((ArrayList)pageDtl.get("DFLT_DISPLAY") == null)?new ArrayList():(ArrayList)pageDtl.get("DFLT_DISPLAY"); 
                if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                allowData=(String)pageDtlMap.get("dflt_val");
                }
                }
            if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
            }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
            conQ =conQ+" and d.grp_nme_idn=?"; 
            params.add(dfgrpnmeidn);
            }  

            if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                conQ += " and (d.emp_idn= ? or d.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
                params.add(dfNmeIdn);
                params.add(dfNmeIdn);
            }
                
            String    getByr  = "select  distinct a.byr , a.nme_idn from sal_pndg_v a,mnme d where a.nme_idn=d.nme_idn and a.typ='SL' "+conQ;
            if(pkt_typ.equals("Y"))
                getByr = getByr+ " and a.pkt_ty <> 'NR'";
            else
             getByr = getByr+ "  and a.pkt_ty in('NR','SMX') ";
            getByr = getByr+ " order by a.byr";

                ArrayList outLst1 = db.execSqlLst("byr", getByr, params);
                PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
                rs1 = (ResultSet)outLst1.get(1);
            while (rs1.next()) {
                ByrDao byr = new ByrDao();

                byr.setByrIdn(rs1.getInt("nme_idn"));
                byr.setByrNme(rs1.getString("byr"));
                byrList.add(byr);
            }
            rs1.close(); pst1.close();
            udf.setByrList(byrList);
            db.execUpd("delete", "delete from gt_srch_multi", new ArrayList());
            ArrayList vwPrpLst = genericInt.genericPrprVw(req,res,"DLV_PND_VW","DLV_PND_VW");
           String srchRef = " Insert into gt_srch_multi (pkt_ty, srch_id, stk_idn, vnm, pkt_dte, stt , flg , qty , cts ,rap_rte,rap_dis,cert_no,sl_dte , sk1 , quot,exh_rte,pair_id,rmk) " + 
                            " select c.pkt_ty , a.idn, c.idn mstkIdn, c.vnm , a.dte , c.stt , 'Z' ,  b.qty , b.cts,c.rap_rte,decode(c.rap_rte ,'1',null,'0',null, trunc((nvl(c.upr,c.cmp)/greatest(c.rap_rte,1)*100)-100, 2)),c.cert_no,trunc(to_date(b.dte, 'dd-mm-rrrr')), sk1 , nvl(b.fnl_usd, b.quot),nvl(a.exh_rte,1),d.emp_idn, " +
                           " a.rmk from msal a , Sal_Pkt_Dtl_V b  , mstk c,mnme d " + 
                            " where a.idn = b.idn  and b.mstk_idn = c.idn and a.nme_idn=d.nme_idn " + 
                            " and a.stt='IS'  and b.stt = 'SL' and a.typ='SL'  and a.flg1='CNF'"+conQ ;
           if(!pkt_typ.equals(""))
           srchRef+=" and c.stt = 'MKAV' and c.pkt_ty in('MX','MIX') "; 
           else
           srchRef+=" and c.stt = 'MKSL' and  c.pkt_ty in('NR','SMX')  ";
            
           int ct = db.execUpd("insert gt",srchRef, params);
            String pktPrp = "srch_pkg.POP_PKT_PRP_TEST(pMdl=>?,pTbl=>'GT_SRCH_MULTI')";
            ArrayList ary = new ArrayList();
            ary.add("DLV_PND_VW");
            ct = db.execCall(" Srch Prp ", pktPrp, ary);
            if(allowData.equals("Y")){
            String rsltQ = " select b.nme_idn,b.byr byr,a.rmk, b.emp sal, b.idn, b.iss_dte dte,b.app_dte app_dte,round(trunc(sysdate)-trunc(a.pkt_dte)) days , a.vnm , to_char(trunc(a.cts ,2),'999999990.00') cts,a.sl_dte,nvl(b.fnl_usd, b.quot) memoQuot, b.rte rte,to_char(trunc(a.cts,2) * nvl(b.fnl_usd, b.quot), '9999999990.00') amt,decode(nvl(a.exh_rte,1),1,'','#00CC66') color,to_char(trunc(a.cts,2) * a.rap_rte, '99999999990.00') rapVlu"+
                " ,decode(a.rap_rte, 1, '', decode(least(a.rap_dis, 0),0, '+'||a.rap_dis, a.rap_dis))  r_dis,a.rap_rte,a.cert_no";
                for (int i = 0; i < vwPrpLst.size(); i++) {
                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                    fld += "00" + j;
                else if (j < 100)
                    fld += "0" + j;
                else if (j > 100)
                    fld += j;

               

                rsltQ += ", " + fld;
               
             }
            rsltQ = rsltQ +" from gt_srch_multi a , sal_pkt_dtl_v b  where a.stk_idn = b.mstk_idn and a.srch_id = b.idn and a.flg= ?  order by b.emp, b.byr , b.idn , sk1";
            ary = new ArrayList();
            ary.add("Z");

                ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap.put("Customer ID", util.nvl(rs.getString("nme_idn")));
                    pktPrpMap.put("byrnme", util.nvl(rs.getString("byr")));
                 pktPrpMap.put("rmk", util.nvl(rs.getString("rmk")));
                    pktPrpMap.put("sal",util.nvl(rs.getString("sal")));
                    pktPrpMap.put("memoIdn",util.nvl(rs.getString("idn")));
                    pktPrpMap.put("dte",util.nvl(rs.getString("dte")));
                 pktPrpMap.put("days",util.nvl(rs.getString("days")));
                 pktPrpMap.put("memoQuot",util.nvl(rs.getString("memoQuot")));
                 pktPrpMap.put("app_dte",util.nvl(rs.getString("app_dte")));
                 pktPrpMap.put("rte",util.nvl(rs.getString("rte")));
                 pktPrpMap.put("amt",util.nvl(rs.getString("amt")));
                 pktPrpMap.put("vlu",util.nvl(rs.getString("amt")));
                 pktPrpMap.put("color",util.nvl(rs.getString("color")));  
                    pktPrpMap.put("vnm",util.nvl(rs.getString("vnm")));
                    pktPrpMap.put("rapVlu", util.nvl(rs.getString("rapVlu")));
                    for(int j=0; j < vwPrpLst.size(); j++){
                         String prp = (String)vwPrpLst.get(j);
                          
                          String fld="prp_";
                          if(j < 9)
                                  fld="prp_00"+(j+1);
                          else    
                                  fld="prp_0"+(j+1);
                          
                          String val = util.nvl(rs.getString(fld)) ;
                        if (prp.toUpperCase().equals("CRTWT"))
                            val = util.nvl(rs.getString("cts"));
                        if (prp.toUpperCase().equals("RAP_DIS"))
                            val = util.nvl(rs.getString("r_dis"));
                        if (prp.toUpperCase().equals("RAP_RTE"))
                            val = util.nvl(rs.getString("rap_rte"));
                        if(prp.toUpperCase().equals("RTE"))
                            val = util.nvl(rs.getString("rte"));
                        if (prp.toUpperCase().equals("CERT NO.") || prp.toUpperCase().equals("CERT_NO")){
                        val = util.nvl(rs.getString("cert_no"));
                          prp="CERT_NO";
                    }
                          pktPrpMap.put(prp, val);
                    }
                                  
                 pktList.add(pktPrpMap);
             }
            rs.close(); pst.close();
            
            String ttltotal = "select  sum(qty) qty,to_char(sum(cts),'999999990.00') cts,to_char(trunc(sum(cts*quot), 2),'999999999990.00') vlu  from gt_srch_multi ";

                outLst = db.execSqlLst("ttltotal", ttltotal, new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
            while(rs.next()){
              String qty = util.nvl(rs.getString("qty"));
              String cts = util.nvl(rs.getString("cts"));
              String vlu = util.nvl(rs.getString("vlu"));
                req.setAttribute("qty", qty);
              req.setAttribute("cts", cts);
              req.setAttribute("vlu", vlu);
            }
                rs.close(); pst.close();
            }
            udf.setPktList(pktList);
            udf.setReportNme("Delivery Pending Report");
            ArrayList partyLst = srchQuery.getEmpList(req,res);
            req.setAttribute("allowData", allowData);
            udf.setEmpList(partyLst);
            ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DLV_PND_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DLV_PND_SRCH");
            info.setGncPrpLst(assortSrchList);  
            if(cnt.equals("hk"))
            util.groupcompany();
                util.updAccessLog(req,res,"Report Action", "DlvPnd end");
           return am.findForward("loadDLV");
            }
        }
    public ActionForward fetchDlvPnd(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Report Action", "fetchDlvPnd start");
        ReportForm udf = (ReportForm)af;
        GenericInterface genericInt = new GenericImpl();
        String byr = util.nvl((String)udf.getByrIdn());
        String saleDte = util.nvl((String)udf.getValue("saleDte"));
        String saletoDte = util.nvl((String)udf.getValue("saletoDte"));
        String dlvDte = util.nvl((String)udf.getValue("dlvDte"));
        String dlvtoDte = util.nvl((String)udf.getValue("dlvtoDte"));
        String group = util.nvl((String)udf.getValue("group"));
        String empIdn = util.nvl((String)udf.getValue("empIdn"));
        ArrayList vwPrpLst = genericInt.genericPrprVw(req,res,"DLV_PND_VW","DLV_PND_VW");
        ArrayList ary = new ArrayList(),selectCollection= new ArrayList();
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        ArrayList rolenmLst=(ArrayList)info.getRoleLst();
        String usrFlg=util.nvl((String)info.getUsrFlg());
        String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
        String conQ="",filterconQ="";
        ary = new ArrayList();
        
        ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DLV_PND_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DLV_PND_SRCH");
        info.setGncPrpLst(genricSrchLst);
        SearchQuery srchQuery = new SearchQuery();
            
            for(int i=0;i<genricSrchLst.size();i++){
                                ArrayList prplist =(ArrayList)genricSrchLst.get(i);
                                String lprp = (String)prplist.get(0);
                                String flg= (String)prplist.get(1);
                                String typ = util.nvl((String)mprp.get(lprp+"T"));
                                String prpSrt = lprp ;  
                                ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                                ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                                int indexprp = vwPrpLst.indexOf(lprp)+1;
                                if(flg.equals("M")) {
                                    selectCollection=new ArrayList();
                                    for(int j=0; j < lprpS.size(); j++) {
                                    String lSrt = (String)lprpS.get(j);
                                    String lVal = (String)lprpV.get(j);    
                                    String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                                    if(!reqVal1.equals("")){
                                    selectCollection.add(util.nvl((String)lprpS.get(lprpV.indexOf(reqVal1))));
                                    }
                                    }
                                  String colSrt = util.prpsrtcolumn("srt",indexprp);
                                    if(selectCollection.size()>0){
                                        String selprp = selectCollection.toString();
                                        selprp = selprp.replaceAll("\\[", "");
                                        selprp = selprp.replaceAll("\\]", "").replaceAll(" ", "");
                                        selprp=util.getVal(selprp);
                                        String colPrp =  util.prpsrtcolumn("prp",indexprp); 
                                        filterconQ += " and a."+colSrt+" in("+selprp+") ";
                                    }
                                }else{
                                String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                                String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                                if(typ.equals("T")){
                                    fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                                    fldVal2 = fldVal1;
                                }
                                if(fldVal2.equals(""))
                                fldVal2=fldVal1;
                                if(!fldVal1.equals("") && !fldVal2.equals("")){
                                String colSrt = util.prpsrtcolumn("srt",indexprp);
                                filterconQ += " and a."+colSrt+" between '"+util.nvl((String)lprpS.get(lprpV.indexOf(fldVal1)))+"' and '"+util.nvl((String)lprpS.get(lprpV.indexOf(fldVal2)))+"' ";
                                }
                                }
                            }    
        if(!group.equals("")){
          conQ =conQ+ " and c.grp_nme_idn= ? ";
          ary.add(group);
        }else{
        if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
        }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
        conQ =conQ+" and c.grp_nme_idn=?"; 
        ary.add(dfgrpnmeidn);
        }  
        } 
        if (!empIdn.equals("0") && !empIdn.equals("")) {
                conQ += " and a.pair_id = ? ";
                ary.add(empIdn);
        }
            if (!empIdn.equals("0") && !empIdn.equals("")) {
                    conQ += " and a.pair_id = ? ";
                    ary.add(empIdn);
            }
        String rsltQ = " select b.nme_idn,b.byr byr, b.emp sal, b.idn, b.iss_dte dte,b.app_dte app_dte,round(trunc(sysdate)-trunc(a.pkt_dte)) days , a.vnm , to_char(trunc(a.cts ,2),'999999990.00') cts,a.sl_dte,nvl(b.fnl_sal, b.quot) memoQuot, b.rte rte,to_char(trunc(a.cts,2) * nvl(b.fnl_sal, b.quot), '9999999990.00') amt,decode(nvl(a.exh_rte,1),1,'','#00CC66') color,to_char(trunc(a.cts,2) * a.rap_rte, '99999999990.00') rapVlu"+
            " ,decode(a.rap_rte, 1, '', decode(least(a.rap_dis, 0),0, '+'||a.rap_dis, a.rap_dis))  r_dis,a.rap_rte,a.cert_no";
            
        for (int i = 0; i < vwPrpLst.size(); i++) {
            String fld = "prp_";
            int j = i + 1;
            if (j < 10)
                fld += "00" + j;
            else if (j < 100)
                fld += "0" + j;
            else if (j > 100)
                fld += j;

           

            rsltQ += ", " + fld;
           
         }
        String rsltQ1= " from gt_srch_multi a , sal_pkt_dtl_v b,mnme c  where a.stk_idn = b.mstk_idn and a.srch_id = b.idn and b.nme_idn=c.nme_idn "+conQ+" and a.flg= ? ";        
        ary.add("Z");
        if(!byr.equals("0")){
            rsltQ1 = rsltQ1 + " and b.nme_idn = ? ";
            ary.add(byr);
        }
        if(!saleDte.equals("") && !saletoDte.equals("")){
            rsltQ1 = rsltQ1 + " and trunc(b.dte) between trunc(to_date('"+saleDte+"','dd-mm-rrrr')) and trunc(to_date('"+saletoDte+"','dd-mm-rrrr')) ";
        }
        if(!dlvDte.equals("") && !dlvtoDte.equals("")){
            rsltQ1 = rsltQ1 + " and trunc(a.sl_dte) between trunc(to_date('"+dlvDte+"','dd-mm-rrrr')) and trunc(to_date('"+dlvtoDte+"','dd-mm-rrrr')) ";
        }
        
        rsltQ = rsltQ +rsltQ1+filterconQ+" order by b.emp, b.byr , b.idn , sk1";

        ArrayList pktList = new  ArrayList();
            ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
         while(rs.next()) {
                HashMap pktPrpMap = new HashMap();
             pktPrpMap.put("Customer ID", util.nvl(rs.getString("nme_idn")));
                pktPrpMap.put("byrnme", util.nvl(rs.getString("byr")));
                pktPrpMap.put("sal",util.nvl(rs.getString("sal")));
                pktPrpMap.put("memoIdn",util.nvl(rs.getString("idn")));
                pktPrpMap.put("dte",util.nvl(rs.getString("dte")));
             pktPrpMap.put("app_dte",util.nvl(rs.getString("app_dte")));
             pktPrpMap.put("days",util.nvl(rs.getString("days")));
             pktPrpMap.put("memoQuot",util.nvl(rs.getString("memoQuot")));
             pktPrpMap.put("rte",util.nvl(rs.getString("rte")));
             pktPrpMap.put("amt",util.nvl(rs.getString("amt")));
             pktPrpMap.put("vlu",util.nvl(rs.getString("amt")));
             pktPrpMap.put("color",util.nvl(rs.getString("color")));
                pktPrpMap.put("vnm",util.nvl(rs.getString("vnm")));
             pktPrpMap.put("rapVlu", util.nvl(rs.getString("rapVlu")));
                for(int j=0; j < vwPrpLst.size(); j++){
                     String lprp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                    if (lprp.toUpperCase().equals("CRTWT"))
                        val = util.nvl(rs.getString("cts"));
                    if (lprp.toUpperCase().equals("RAP_DIS"))
                        val = util.nvl(rs.getString("r_dis"));
                    if (lprp.toUpperCase().equals("RAP_RTE"))
                        val = util.nvl(rs.getString("rap_rte"));
                    if(lprp.toUpperCase().equals("RTE"))
                        val = util.nvl(rs.getString("rte"));
                    if (lprp.toUpperCase().equals("CERT NO.") || lprp.toUpperCase().equals("CERT_NO")){
                    val = util.nvl(rs.getString("cert_no"));
                      lprp="CERT_NO";
                    }
                      pktPrpMap.put(lprp, val);
                }
                              
             pktList.add(pktPrpMap);
         }
        rs.close(); pst.close();
        udf.setPktList(pktList);
            
            
            String ttltotal = "select  sum(a.qty) qty,to_char(sum(a.cts),'999999990.00') cts,to_char(trunc(sum(a.cts*a.quot), 2),'999999999990.00') vlu  "+rsltQ1+filterconQ;

            outLst = db.execSqlLst("ttltotal", ttltotal, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while(rs.next()){
              String qty = util.nvl(rs.getString("qty"));
              String cts = util.nvl(rs.getString("cts"));
              String vlu = util.nvl(rs.getString("vlu"));
                req.setAttribute("qty", qty);
              req.setAttribute("cts", cts);
              req.setAttribute("vlu", vlu);
            }
                rs.close(); pst.close();
        udf.setReportNme("Delivery Pending Report");
        ArrayList partyLst = srchQuery.getEmpList(req,res);
        udf.setEmpList(partyLst);
            util.updAccessLog(req,res,"Report Action", "fetchDlvPnd end");
    return am.findForward("loadDLV");
        }
    }
    
    
    public ActionForward assLab(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Report Action", "assLab start");
    ReportForm udf = (ReportForm)af;
            GenericInterface genericInt = new GenericImpl();
    ArrayList itemHdr = new ArrayList();
    itemHdr.add("STK_IDN");
    itemHdr.add("Memo Id");
    itemHdr.add("BYR");

    ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "ASS_LAB_PRI","ASS_LAB_PRI");
    for(int m=0;m<vwPrpLst.size();m++){
    itemHdr.add(vwPrpLst.get(m));
    }
    HashMap pktPrpMap = new HashMap();
    ArrayList pktList = new ArrayList();
    String srchQ = "select mstk_idn,memo_id,byr from LP_PNDG_V order by sk1 ";

            ArrayList outLst = db.execSqlLst("search Result", srchQ, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while (rs.next()) {
    pktPrpMap = new HashMap();
    String StkIdn = util.nvl(rs.getString("mstk_idn"));
    pktPrpMap.put("STK_IDN", util.nvl(rs.getString("mstk_idn")));
    pktPrpMap.put("Memo Id", util.nvl(rs.getString("memo_id")));
    pktPrpMap.put("BYR", util.nvl(rs.getString("byr")));
    String getPktPrp =
    " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', nvl(to_char(trunc(a.num,2), '999990.09'),''), 'D', to_char(a.dte, 'dd-Mon-rrrr'), a.txt) val , grp "
    + " from stk_dtl a, mprp b, rep_prp c "
    + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'ASS_LAB_PRI' and c.flg='Y' and a.mstk_idn = ? "
    + " and grp = 1 order by a.grp desc ";

    ArrayList params = new ArrayList();
    params.add(StkIdn);

        ArrayList outLst1 = db.execSqlLst("stkDtl", getPktPrp, params);
        PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
        ResultSet rs1 = (ResultSet)outLst1.get(1);
    while(rs1.next()){
    String lprp = rs1.getString("mprp");
    String val = util.nvl(rs1.getString("val"));
    pktPrpMap.put(lprp, val);
    }
        rs1.close(); pst1.close();
    pktList.add(pktPrpMap);
    }
        rs.close(); pst.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    session.setAttribute("itemHdr", itemHdr);
    session.setAttribute("pktList", pktList);
            util.updAccessLog(req,res,"Report Action", "assLab end");
    return am.findForward("assLab");
        }
    }
    public ActionForward loadKaccha(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Report Action", "loadKaccha start");
        ReportForm udf = (ReportForm)form;
        GenericInterface genericInt = new GenericImpl();
        udf.resetALL();
        ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_kacchaGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_kacchaGNCSrch");
        info.setGncPrpLst(assortSrchList);
            util.updAccessLog(req,res,"Report Action", "loadKaccha end");
        return am.findForward("loadKaccha");
        }
    }
    public ActionForward fetchKaccha(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Report Action", "fetchKaccha start");
    ReportForm udf = (ReportForm)form;
            GenericInterface genericInt = new GenericImpl();
    String memobtn = util.nvl((String)udf.getValue("memo"));
    ArrayList memoLst=new ArrayList();
    ArrayList sttLst=new ArrayList();
    HashMap dataDtl=new HashMap();
    HashMap data=new HashMap();
    ArrayList ary = null;
    ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_kacchaGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_kacchaGNCSrch");
    info.setGncPrpLst(genricSrchLst);
    ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "kacchaViewLst","STK_CORE");
    HashMap prp = info.getPrp();
    HashMap mprp = info.getMprp();
    HashMap dbinfo = info.getDmbsInfoLst();
    String memoval = (String)dbinfo.get("MEMO");
    String colval = (String)dbinfo.get("COL");
    String clrval = (String)dbinfo.get("CLR");
    String prival = "FA_PRI";
    String crtval = "CRTWT";
    int indexMemo = vWPrpList.indexOf(memoval)+1;
    int indexCol = vWPrpList.indexOf(colval)+1;
    int indexClr = vWPrpList.indexOf(clrval)+1;
    int indexPri = vWPrpList.indexOf(prival)+1;
    int indexCrtwt = vWPrpList.indexOf(crtval)+1;
    String memoPrp = util.prpsrtcolumn("prp",indexMemo);
    String colPrp = util.prpsrtcolumn("prp",indexCol);
    String clrPrp = util.prpsrtcolumn("prp",indexClr);
    String priPrp = util.prpsrtcolumn("prp",indexPri);
    String crtwtPrp = util.prpsrtcolumn("prp",indexCrtwt);
    String memoSrt = util.prpsrtcolumn("srt",indexMemo);
    String colSrt = util.prpsrtcolumn("srt",indexCol);
    String clrSrt = util.prpsrtcolumn("srt",indexClr);
    String priSrt = util.prpsrtcolumn("srt",indexPri);
    String crtwtSrt = util.prpsrtcolumn("srt",indexCrtwt);
    String delQ = " Delete from gt_srch_rslt ";
    int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
    HashMap paramsMap = new HashMap();
    for (int i = 0; i < genricSrchLst.size(); i++) {
    ArrayList srchPrp = (ArrayList)genricSrchLst.get(i);
    String lprp = (String)srchPrp.get(0);
    String flg= (String)srchPrp.get(1);
    String prpSrt = lprp ;
    String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
    if(flg.equals("M")) {
    ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
    ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
    for(int j=0; j < lprpS.size(); j++) {
    String lSrt = (String)lprpS.get(j);
    String lVal = (String)lprpV.get(j);
    String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
    if(!reqVal1.equals("")){
    paramsMap.put(lprp + "_" + lVal, reqVal1);
    }
    }
    }else{
    String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
    String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
    if(fldVal2.equals(""))
    fldVal2=fldVal1;
    if(!fldVal1.equals("") && !fldVal2.equals("")){
    paramsMap.put(lprp+"_1", fldVal1);
    paramsMap.put(lprp+"_2", fldVal2);
    }
    }
    }
    if(paramsMap.size()>0){
    paramsMap.put("stt", "ALL");
    paramsMap.put("mdl", "STK_CORE");
    int lSrchId=util.genericSrchEntries(paramsMap);
    System.out.println(lSrchId);
    ary = new ArrayList();
    ary.add(String.valueOf(lSrchId));
    ct = db.execCall(" Gt ", "report_pkg.Kaccha_Avg(Psrchid => ?)", ary);

    if(memobtn.length()==0){
    String sqlQ="select prp_001 memo, prp_002 dept, stt " +
    " , count(*) qty, Sum(Trunc(Prp_004,2)) giaCts, trunc(sum(prp_005),3) grdCts" +
    " , round(sum(nvl(prp_005, 0)*nvl(prp_010, 0))) kacchaAmt" +
    " , round(sum(nvl(prp_005, 0)*nvl(prp_010, 0))/sum(prp_005)) kacchaAvg" +
    " , round(sum(nvl(Trunc(Prp_004,2), 0)*nvl(prp_011, 0))) mnjAmt" +
    " , round(sum(nvl(Trunc(Prp_004,2), 0)*nvl(prp_011, 0))/sum(Trunc(Prp_004,2))) mnjAvg" +
    " from gt_srch_rslt" +
    " group by prp_001, prp_002, stt";

        ArrayList outLst = db.execSqlLst("stkDtl", sqlQ, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    data=new HashMap();
    String memo=util.nvl(rs.getString("memo"));
    String stt=util.nvl(rs.getString("stt"));
    data.put("QTY",util.nvl(rs.getString("qty")));
    data.put("GIACTS",util.nvl(rs.getString("giaCts")));
    data.put("GRDCTS",util.nvl(rs.getString("grdCts")));
    data.put("VLU",util.nvl(rs.getString("kacchaAmt")));
    data.put("AVG",util.nvl(rs.getString("kacchaAvg")));
    data.put("MNJAMT",util.nvl(rs.getString("Mnjamt")));
    data.put("MNJAVG",util.nvl(rs.getString("Mnjavg")));
    // data.put("AVG_DAYS",util.nvl(rs.getString("age")));
    if(!memoLst.contains(memo) && !memo.equals((""))){
    memoLst.add(memo);
    }
    if(!sttLst.contains(stt) && !stt.equals((""))){
    sttLst.add(stt);
    }
    dataDtl.put(memo+"_"+stt,data);
    }
    rs.close(); pst.close();
    String ttlQ="WITH \n" + 
    "SEND_SMRY as (\n" + 
    "select prp_001 memo, prp_002 dept, stt\n" + 
    ", ((round(sum(nvl(Trunc(Prp_004,2), 0)*nvl(prp_011, 0))/sum(Trunc(Prp_004,2))) - round(sum(nvl(prp_005, 0)*nvl(prp_010, 0))/sum(prp_005)))/round(sum(nvl(prp_005, 0)*nvl(prp_010, 0))/sum(prp_005)))+1 diff_avg -- mnjAvg - kacchaAvg\n" + 
    ", (((round(sum(nvl(Trunc(Prp_004,2), 0)*nvl(prp_011, 0))/sum(Trunc(Prp_004,2))) - round(sum(nvl(prp_005, 0)*nvl(prp_010, 0))/sum(prp_005)))/round(sum(nvl(prp_005, 0)*nvl(prp_010, 0))/sum(prp_005))))*100 Addper\n" + 
    "from gt_srch_rslt \n" + 
    "where stt = 'SEND'\n" + 
    "GROUP BY prp_001, prp_002, stt\n" + 
    "), \n" + 
    "SMRY as (\n" + 
    "select a.prp_001 memo, a.prp_002 dept, trunc(sum(a.prp_005), 3) ttl_cts ,\n" + 
    "sum(nvl(prp_005, 0)*nvl(prp_010, 0)) asrt_vlu, sum(nvl(prp_005, 0)*nvl(prp_010, 0))/sum(prp_005) asrt_avg ,\n" + 
    "sum(decode(a.stt, 'SEND', nvl(prp_005, 0)*nvl(prp_010, 0), 0)) TRF_VLU , sum(decode(a.stt, 'SEND', prp_005, 0)) TRF_CTS ,\n" + 
    "sum(decode(a.stt, 'SEND', 0, nvl(prp_005, 0)*nvl(prp_010, 0))) IR_VLU , sum(decode(a.stt, 'SEND', 0, prp_005)) IR_CTS\n" + 
    "--(Sum(greatest(Trunc(Prp_004,2), 1)*greatest(Prp_011, 1))*100/Sum(greatest(Prp_005, 1)*greatest(Prp_010, 1)))-100 Addper\n" + 
    "from gt_srch_rslt a group by a.prp_001, a.prp_002\n" + 
    ")\n" + 
    "select a.memo, a.dept , trunc(ttl_cts, 3) ttl_cts , round(asrt_vlu) TotKAmt , round(asrt_avg) AkhiAvg ,\n" + 
    "round(trf_vlu) sendVlu , trunc(trf_cts, 2) sendCts ,\n" + 
    "round(ir_vlu) ir_vlu , trunc(ir_cts, 2) ir_cts , trunc(trf_vlu/asrt_vlu*100, 2) bakiPct , Trunc(Addper,2) addPct ,\n" + 
    "round(decode(greatest(round(100 - (greatest(ttl_cts,1)/greatest(ttl_cts,1)*100),2), 50) , 50, asrt_avg*diff_avg,\n" + 
    "(asrt_avg)*(1+(((greatest(trf_vlu,1)/greatest(ttl_cts,1))/asrt_avg)-1)))) bakiAvg ,\n" + 
    "round(decode(greatest(round(100 - (greatest(ttl_cts,1)/greatest(ttl_cts,1)*100),2), 50) , 50, ir_cts*(asrt_avg*diff_avg),\n" + 
    "ir_cts*asrt_avg*(1+(((greatest(trf_vlu,1)/greatest(ttl_cts,1))/asrt_avg)-1)))) bakiAmt\n" + 
    "from smry a, send_smry b \n" + 
    "WHERE A.MEMO = B.MEMO AND A.DEPT = B.DEPT\n" + 
    "order by 1,2";

        outLst = db.execSqlLst("TTL", ttlQ, new ArrayList());
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    data=new HashMap();
    String memo=util.nvl(rs.getString("memo"));
    data.put("DEPT",util.nvl(rs.getString("dept")));
    data.put("TCTS",util.nvl(rs.getString("ttl_cts")));
    data.put("TISS",util.nvl(rs.getString("ir_vlu")));
    data.put("TISSCTS",util.nvl(rs.getString("ir_cts")));
    data.put("TKAMT",util.nvl(rs.getString("TotKAmt")));
    data.put("TAAVG",util.nvl(rs.getString("AkhiAvg")));
    data.put("SVLU",util.nvl(rs.getString("sendVlu")));
    data.put("SCTS",util.nvl(rs.getString("sendCts")));
    data.put("TBAKI",util.nvl(rs.getString("bakiPct")));
    data.put("TADD",util.nvl(rs.getString("addPct")));
    data.put("TBAKIAVG",util.nvl(rs.getString("bakiAvg")));
    data.put("TBAKIAMT",util.nvl(rs.getString("bakiAmt")));
    dataDtl.put(memo+"_TTL",data);
    }
    rs.close(); pst.close();
    udf.reset();
    session.setAttribute("memoLst", memoLst);
    session.setAttribute("dataDtl", dataDtl);
    session.setAttribute("sttLst", sttLst);
    req.setAttribute("View", "Y");
    }else{
        ArrayList colList = new ArrayList();
        ArrayList clrList = new ArrayList();
        ArrayList memoList = new ArrayList();
        String sqlQ="Select "+memoPrp+","+colPrp+","+clrPrp+",Trunc(Sum(Nvl("+priPrp+", 0)*"+crtwtPrp+"),0) avg\n" + 
        "From Gt_Srch_Rslt where "+clrPrp+" is not null and "+colPrp+" is not null " +  
        "Group by "+memoPrp+","+colPrp+","+clrPrp+"\n" + 
        "order by "+memoPrp;

        ArrayList outLst = db.execSqlLst("Dtl", sqlQ, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String colVal = util.nvl(rs.getString(colPrp));
            String clrVal = util.nvl(rs.getString(clrPrp)); 
            String memoVal = util.nvl(rs.getString(memoPrp));
            String key = memoVal+"_"+colVal+"_"+clrVal+"_AVG";
            dataDtl.put(key, util.nvl(rs.getString("avg")));
            if(!memoList.contains(memoVal))
                memoList.add(memoVal);
        }
        rs.close(); pst.close();
        String colttlQ="select "+memoPrp+","+colPrp+","+colSrt+",Trunc(Sum(Nvl("+priPrp+", 0)*"+crtwtPrp+"),0) avgsum\n" + 
        "From Gt_Srch_Rslt where "+clrPrp+" is not null and "+colPrp+" is not null " +   
        "Group By "+memoPrp+","+colPrp+","+colSrt+"\n" + 
        "order by "+memoPrp+","+colSrt;

        outLst = db.execSqlLst("Col ttl", colttlQ, new ArrayList());
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String colVal = util.nvl(rs.getString(colPrp));
            String memoVal = util.nvl(rs.getString(memoPrp));
            String key = memoVal+"_"+colVal+"_COLTTL";
            dataDtl.put(key, util.nvl(rs.getString("avgsum")));
            if(!colList.contains(colVal))
                colList.add(colVal);
        }
        rs.close(); pst.close();
        String clrttlQ="select "+memoPrp+","+clrPrp+","+clrSrt+",Trunc(Sum(Nvl("+priPrp+", 0)*"+crtwtPrp+"),0) avgsum\n" + 
        "From Gt_Srch_Rslt where "+clrPrp+" is not null and "+colPrp+" is not null " +   
        "Group By "+memoPrp+","+clrPrp+","+clrSrt+"\n" + 
        "order by "+memoPrp+","+clrSrt;
        rs = db.execSql("Clr ttl", clrttlQ, new ArrayList());
        outLst = db.execSqlLst("Clr ttl", clrttlQ, new ArrayList());
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String clrVal = util.nvl(rs.getString(clrPrp));
            String memoVal = util.nvl(rs.getString(memoPrp));
            String key = memoVal+"_"+clrVal+"_CLRTTL";
            dataDtl.put(key, util.nvl(rs.getString("avgsum")));
            if(!clrList.contains(clrVal))
                clrList.add(clrVal);
        }
        rs.close(); pst.close();
        String ttlQ="select "+memoPrp+",Trunc(Sum(Nvl("+priPrp+", 0)*"+crtwtPrp+"),0) avgsum\n" + 
        "From Gt_Srch_Rslt where "+clrPrp+" is not null and "+colPrp+" is not null " +  
        "Group By "+memoPrp;

        outLst = db.execSqlLst("Grand ttl", ttlQ, new ArrayList());
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String memoVal = util.nvl(rs.getString(memoPrp));
            String key = memoVal+"_TTL";
            dataDtl.put(key, util.nvl(rs.getString("avgsum")));
        }
        rs.close(); pst.close();
        session.setAttribute("dataDtl", dataDtl);
        req.setAttribute("colList", colList);
        req.setAttribute("clrList", clrList);
        req.setAttribute("memoList", memoList);
        req.setAttribute("View", "N"); 
        
    }
    }
    udf.resetALL();
            util.updAccessLog(req,res,"Report Action", "fetchKaccha end");
    return am.findForward("loadKaccha");
        }
    } 
     
    
//    public ArrayList kacchaGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("kacchaGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'KACCHA_SRCH' and flg <> 'N' order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close(); pst1.close();
//                session.setAttribute("kacchaGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
//    public ActionForward loadKaccha(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
//      init(req,res);
//        udf = (ReportForm)form;
//        ArrayList deptList = new ArrayList();
//        String deptQ = "select dsc,val from prp where mprp= 'DEPT' and flg is null order by srt";
//        ResultSet rs = db.execSql("stkDtl", deptQ, new ArrayList());
//        while(rs.next()){ 
//            ObjBean prp = new ObjBean();
//                prp.setNme(util.nvl(rs.getString("val")));
//                prp.setDsc(util.nvl(rs.getString("dsc")));
//                deptList.add(prp);
//        }
//        rs.close(); pst.close();
//        udf.setDeptList(deptList);
//        return am.findForward("loadKaccha");
//    }
//    public ActionForward fetchKaccha(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
//      init(req,res);
//        udf = (ReportForm)form;
//      ArrayList memoLst=new ArrayList();
//      ArrayList sttLst=new ArrayList();
//      HashMap dataDtl=new HashMap();
//      HashMap data=new HashMap();
//      String memoIdn = util.nvl((String)udf.getValue("memoLst"));
//      String[] deptLst=udf.getDeptLst();
//      String dept="";
//        for(int i=0;i<deptLst.length;i++){
//            dept=dept+","+deptLst[i];
//        }
//        if(!dept.equals("")){
//        dept.replaceFirst(",", "");
//        dept = util.getVnm(dept);
//        dept=" and a.dept in("+dept+") ";
//        } 
//        memoIdn = util.getVnm(memoIdn);
//        String sqlQ="Select A.memo, A.Dept, B.Grp2 dsp_stt, Count(*) qty, Sum(Cts) cts\n" +
//        ", To_Char(Sum(Fa_Pri_Vlu),'999999990.00') vlu\n" +
//        ", Trunc(Avg(A.Age),2) age\n" +
//        ", Decode(B.Grp2, 'SEND', to_char(sum(trf_vlu),'999999990.00'),0) mnjAmt\n" +
//        " , to_char(Sum(Fa_Pri_Vlu)/nvl(sum(cts),0),'99999990.00') kacch_avg\n" +
//        " From Stk_Avg_V A, Df_Stk_Stt B\n" +
//        " where a.stt = b.stt and nvl(b.grp2, 'NA') <> 'NA'" + dept+
//        " and a.memo in ("+memoIdn+") \n" +
//        " group by a.memo, a.dept, b.grp2 order by a.memo, a.dept, b.grp2";
//        ResultSet rs = db.execSql("stkDtl", sqlQ, new ArrayList());
//        while(rs.next()){ 
//            data=new HashMap();
//            String memo=util.nvl(rs.getString("memo"));
//            String stt=util.nvl(rs.getString("dsp_stt"));
//            
//            data.put("QTY",util.nvl(rs.getString("qty")));
//            data.put("CTS",util.nvl(rs.getString("cts")));
//            data.put("VLU",util.nvl(rs.getString("vlu")));
//            data.put("AVG",util.nvl(rs.getString("kacch_avg")));
//            data.put("MNJAMT",util.nvl(rs.getString("mnjAmt")));
//            data.put("AVG_DAYS",util.nvl(rs.getString("age")));
//            if(!memoLst.contains(memo) && !memo.equals((""))){
//                memoLst.add(memo);
//            }  
//            if(!sttLst.contains(stt) && !stt.equals((""))){
//                sttLst.add(stt);
//            } 
//            dataDtl.put(memo+"_"+stt,data);  
//        }
//        rs.close(); pst.close();
//        String ttlQ="select memo, dept, ttl_cts, asrt_vlu TotKAmt, asrt_avg AkhiAvg, trf_vlu sendVlu, trf_cts sendCts, ir_vlu, ir_cts\n" + 
//        ", round(100 - (trf_cts/ttl_cts*100),2) bakiPct\n" + 
//        ", decode(greatest(round(100 - (greatest(ttl_cts,1)/greatest(ttl_cts,1)*100),2), 50)\n" + 
//        ", 50, 0, Round((((greatest(trf_vlu,1)/greatest(ttl_cts,1))/asrt_avg)-1)*100,2)) addPct\n" + 
//        ", decode(greatest(round(100 - (greatest(ttl_cts,1)/greatest(ttl_cts,1)*100),2), 50)\n" + 
//        ", 50, asrt_avg, asrt_avg*(1+(((greatest(trf_vlu,1)/greatest(ttl_cts,1))/asrt_avg)-1))) bakiAvg\n" + 
//        ", decode(greatest(round(100 - (greatest(ttl_cts,1)/greatest(ttl_cts,1)*100),2), 50)\n" + 
//        ", 50, ir_cts*asrt_avg, ir_cts*asrt_avg*(1+(((greatest(trf_vlu,1)/greatest(ttl_cts,1))/asrt_avg)-1))) bakiAmt\n" + 
//        "from (\n" + 
//        "select a.memo, a.dept, trunc(sum(a.cts), 3) ttl_cts, sum(a.asrt_vlu) asrt_vlu\n" + 
//        ", round(sum(a.asrt_vlu)/sum(cts), 2) asrt_avg\n" + 
//        ", sum(decode(a.grp2, 'SEND', a.trf_vlu, 0)) TRF_VLU\n" + 
//        ", sum(decode(a.grp2, 'SEND', a.cts, 0)) TRF_CTS\n" + 
//        ", sum(decode(a.grp2, 'SEND', 0, a.asrt_vlu)) IR_VLU\n" + 
//        ", sum(decode(a.grp2, 'SEND', 0, a.cts)) IR_CTS\n" + 
//        "from memo_dept_avg_v a\n" + 
//        "where a.memo in ("+memoIdn+") "+dept+ 
//        " group by a.memo, a.dept)\n" + 
//        "order by 1,2\n";
//        rs = db.execSql("TTL", ttlQ, new ArrayList());
//                while(rs.next()){ 
//                    data=new HashMap();
//                    String memo=util.nvl(rs.getString("memo"));
//                    data.put("DEPT",util.nvl(rs.getString("dept")));
//                    data.put("TCTS",util.nvl(rs.getString("ttl_cts")));
//                    data.put("TISS",util.nvl(rs.getString("ir_vlu")));
//                    data.put("TKAMT",util.nvl(rs.getString("TotKAmt")));
//                    data.put("TAAVG",util.nvl(rs.getString("AkhiAvg")));
//                    data.put("TBAKI",util.nvl(rs.getString("bakiPct")));
//                    data.put("TADD",util.nvl(rs.getString("addPct"))); 
//                    data.put("TBAKIAVG",util.nvl(rs.getString("bakiAvg"))); 
//                    data.put("TBAKIAMT",util.nvl(rs.getString("bakiAmt"))); 
//                    dataDtl.put(memo+"_TTL",data);  
//                }
//        rs.close(); pst.close();
//        session.setAttribute("memoLst", memoLst);
//        session.setAttribute("dataDtl", dataDtl);
//        session.setAttribute("sttLst", sttLst);
//        req.setAttribute("View", "Y");
//        return am.findForward("loadKaccha");
//    }
    public ActionForward loadWeekBuyer(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Report Action", "loadWeekBuyer start");
        ReportForm udf = (ReportForm)form;
        SearchQuery srchQuery = new SearchQuery();
        udf.reset();
        ArrayList yrList =new ArrayList();
        ArrayList empList= srchQuery.getByrList(req,res);
        String gtView = "select chr_fr, chr_to , dsc , dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
        " b.mdl = 'JFLEX' and b.nme_rule = 'YEARS' and a.til_dte is null order by a.srt_fr ";

            ArrayList outLst = db.execSqlLst("gtView", gtView, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            ObjBean prp = new ObjBean();
                prp.setNme(util.nvl(rs.getString("chr_fr")));
                prp.setDsc(util.nvl(rs.getString("dsc")));
                yrList.add(prp);
        }
        rs.close(); pst.close();
        udf.setEmpList(empList); 
        udf.setYrList(yrList); 
            util.updAccessLog(req,res,"Report Action", "loadWeekBuyer end");
        return am.findForward("loadWeekBuyer");
        }
    }
    public ActionForward fetchWeekBuyer(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Report Action", "fetchWeekBuyer start");
        ReportForm udf = (ReportForm)form;
        String[] empLst=udf.getEmpLst();
        String[] yrLst=udf.getYrLst();
        String empidn="";
        String empQ="";
        String years="";
        String yrQ="";
        if(empLst!=null){
          for(int i=0;i<empLst.length;i++){
              empidn=empidn+","+empLst[i];
          }
        }
        if(yrLst!=null){
        for(int i=0;i<yrLst.length;i++){
            years=years+","+yrLst[i];
        }
        }
        if(!empidn.equals("")){
          empidn=empidn.replaceFirst(",", "");
          empQ=" and emp_idn in("+empidn+") ";
        }
        if(!years.equals("")){
        years=years.replaceFirst(",", "");
        yrQ=" and Yr In ("+years+")";
        }
        HashMap dataDtl=new HashMap();
        HashMap buyerDisplay=new HashMap();
        ArrayList empidnLst=new ArrayList();
        ArrayList nmeidnLst=new ArrayList();
        String weekQ="Select Yr, Mm, Week_Mm, Dsp_Mon\n" + 
        " , byr, emp,nvl(emp_idn,0) emp_idn,nme_idn, sum(qty) qty, sum(cts) cts, sum(cts*fnl_usd) vlu\n" + 
        " from sal_pkt_dtl_v\n" + 
        " Where 1=1  " +empQ+yrQ+ 
        " group by yr, mm, week_mm, dsp_mon, byr, emp,emp_idn,nme_idn\n" + 
        " Order By Emp, Byr, 1, 2, 3";

            ArrayList outLst = db.execSqlLst("weekQ", weekQ, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){ 
         HashMap data=new HashMap();   
         String emp_idn=util.nvl(rs.getString("emp_idn"));
         String nme_idn=util.nvl(rs.getString("nme_idn"));
         String month=util.nvl(rs.getString("dsp_mon"));
         String week=util.nvl(rs.getString("week_mm"));
         String Yr=util.nvl(rs.getString("Yr"));
         String key=nme_idn+"_"+emp_idn+"_"+Yr+"_"+month+"_"+week;
            if(!empidnLst.contains(emp_idn)){
                empidnLst.add(emp_idn);
                nmeidnLst=new ArrayList();
            }
            if(!nmeidnLst.contains(nme_idn)){
                nmeidnLst.add(nme_idn);
            }
            data.put("QTY",util.nvl(rs.getString("qty")));
            data.put("CTS",util.nvl(rs.getString("cts")));
            data.put("VLU",util.nvl(rs.getString("vlu")));
            dataDtl.put(nme_idn,util.nvl(rs.getString("byr")));
            dataDtl.put(emp_idn,util.nvl(rs.getString("emp")));
            dataDtl.put(key,data); 
            buyerDisplay.put(Yr+"_"+emp_idn,nmeidnLst);
        }
        rs.close(); pst.close();
        ArrayList yrList=new ArrayList();
        ArrayList monthList=new ArrayList();
        ArrayList keymonthList=new ArrayList();
        ArrayList Lst=new ArrayList();
        HashMap monthDtl=new HashMap();
        String sqlQ="select yr, mm, week_mm, dsp_mon\n" + 
        " , byr, emp, sum(qty) qty, sum(cts) cts, sum(cts*fnl_usd) vlu\n" + 
        " from sal_pkt_dtl_v\n" + 
        " where 1=1 "+empQ+yrQ+ 
        " Group By Yr, Mm, Week_Mm, Dsp_Mon, Byr, Emp\n" + 
        " order by yr,mm";

            outLst = db.execSqlLst("sqlQ", sqlQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while(rs.next()){ 
         String yr=util.nvl(rs.getString("yr"));
         String month=util.nvl(rs.getString("dsp_mon"));
         String week=util.nvl(rs.getString("week_mm"));
         String key =yr+"_"+month;
             if(!yrList.contains(yr)){
                 yrList.add(yr);
             }  
            if(!monthList.contains(month)){
                monthList.add(month);
            }
             if(!keymonthList.contains(key)){ 
                 keymonthList.add(key);
                 Lst=new ArrayList();
             }
             if(!Lst.contains(week)){ 
                 Lst.add(week);
             }
            monthDtl.put(key, Lst);  
         }
        rs.close(); pst.close();
        req.setAttribute("yrList", yrList);
        req.setAttribute("monthList", monthList);
        req.setAttribute("monthDtl", monthDtl);
        req.setAttribute("dataDtl", dataDtl);
        req.setAttribute("empidnLst", empidnLst);
        req.setAttribute("buyerDisplay", buyerDisplay);
        req.setAttribute("view", "Y");
            util.updAccessLog(req,res,"Report Action", "fetchWeekBuyer end");
        return am.findForward("loadWeekBuyer");
        }
    }
    
    public ActionForward fetchrepetcust(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Report Action", "fetchrepetcust start");
        ReportForm udf = (ReportForm)form;
        String dept=util.nvl((String)udf.getValue("dept"));
        String frmdte=util.nvl((String)udf.getValue("frmdte")); 
        String todte=util.nvl((String)udf.getValue("todte"));
        ArrayList ary=new ArrayList();
        if(!dept.equals("")){
            ArrayList byrDtl=new ArrayList();
            String conQ="and trunc(a.dte) between trunc(sysdate) - 30 and trunc(sysdate)";
            if(!frmdte.equals("") && !todte.equals("")){
                conQ =" and trunc(a.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
            }
        ary.add(dept);
        String srchQ="select byr, cnt, to_char(trunc(vlu/1000,2),'99999990.00') vlu\n" + 
        " , cts from (\n" + 
        "  select count(distinct b.idn) cnt\n" + 
        "  , a.byr\n" + 
        "  , round(sum(fnl_usd*trunc(b.cts,2))) vlu\n" + 
        "  , trunc(sum(trunc(b.cts,2)),2) cts\n" + 
        "  from sal_v a, sal_pkt_dtl_v b, mstk c, stk_dtl d\n" + 
        "  where a.idn = b.idn and b.mstk_idn = c.idn and c.idn = d.mstk_idn\n" + 
        "  and c.pkt_ty = 'NR' and d.grp = 1 and d.mprp = 'DEPT'\n" + 
        "  and b.stt <> 'RT'\n" + 
        conQ + 
        "  and d.val = ?\n" + 
        "  group by a.byr)\n" + 
        "  where cnt > 1 and vlu > 10000\n" + 
        "  order by vlu desc, 1\n";

            ArrayList outLst = db.execSqlLst("Repeat Customer", srchQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap data=new HashMap();
            data.put("BYR", util.nvl((String)rs.getString("byr")));
            data.put("CNT",util.nvl((String)rs.getString("cnt")));
            data.put("VLU", util.nvl((String)rs.getString("vlu")));
            data.put("CTS", util.nvl((String)rs.getString("cts")));
            byrDtl.add(data);
        }
            rs.close(); pst.close();
        req.setAttribute("byrDtl", byrDtl);
        session.setAttribute("title", dept+"  REPEATED CUSTOMER LIST "+frmdte+"  "+todte);
        req.setAttribute("view", "Y");
        }
            util.updAccessLog(req,res,"Report Action", "fetchrepetcust end");
        return am.findForward("repetcust");
        }
    }
    public ActionForward createrepetcustXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Report Action", "createrepetcustXL start");
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "Repeat_Customer"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList byrLst = (ArrayList)session.getAttribute("byrLstRpt");
        String count = (String)session.getAttribute("countrpt");
        HSSFWorkbook hwb = xlUtil.getGenXlRepetCust(req, byrLst,count);
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Report Action", "createrepetcustXL end");
        return null;
        }
    }
    
    public ActionForward loadmemohk(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Report Action", "memohk start");
        ReportForm udf = (ReportForm)form;
            udf.reset();
            String ketan= util.nvl((String)req.getParameter("KET"));
            if(!ketan.equals(""))
            req.setAttribute("ketan", "Y");
            String ifrsBtn = util.nvl(req.getParameter("service"));
            if(!ifrsBtn.equals(""))
            req.setAttribute("ifrsBtn", "Y");
            util.updAccessLog(req,res,"Report Action", "memohk end");
        return am.findForward("memohk");
        }
    }
    public ActionForward memohk(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
               util.updAccessLog(req,res,"Report Action", "memohk start");
           ReportForm udf = (ReportForm)form;
           GenericInterface genericInt = new GenericImpl();
           String memoIdn=util.nvl((String)udf.getValue("memoIdn")).trim();
           String ketan=util.nvl((String)udf.getValue("ketan")).trim();
           String ifrsBtn=util.nvl((String)udf.getValue("ifrsBtn")).trim();
           String pDteFrm = util.nvl((String)udf.getValue("dteFrm"));
           String pDteTo = util.nvl((String)udf.getValue("dteTo"));
           String deptsummary=util.nvl((String)udf.getValue("deptsummary"));
           String memolotifrs=util.nvl((String)udf.getValue("memolotifrs"));
           HashMap dataDtl=new HashMap();
           HashMap datattl=new HashMap();
           HashMap data=new HashMap();
           ArrayList keytable=new ArrayList();
           ArrayList deptLst=new ArrayList();
           ArrayList conatinsdata=new ArrayList();
           HashMap dbinfo = info.getDmbsInfoLst();
           ArrayList memoList=new ArrayList();
           String mlotidn="";
           if(!memoIdn.equals("") && memoIdn.indexOf(",")==-1){
               udf.reset();
           if(deptsummary.equals("")){
               String memoPRCQ="Pop_Memo_Rep_Data(p_memo => ?,p_mdl => ?)";
               ArrayList ary=new ArrayList();
               ary.add(memoIdn);
               ary.add("MEMO_LOT_VW");
               if(!memolotifrs.equals("")){
                   memoPRCQ="Pop_Memo_Rep_Data(p_memo => ?,p_mdl => ?,pTyp => ?)";
                   ary.add("IFRS");
               }
               String sh = (String)dbinfo.get("SHAPE");
               String szval = (String)dbinfo.get("SIZE");
               String memo = util.nvl((String)dbinfo.get("MEMO"));
               ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "MEMO_HKREPORT","MEMO_LOT_VW");
               int indexSH = vWPrpList.indexOf(sh)+1;
               int indexSZ = vWPrpList.indexOf(szval)+1;
               int indexdept = vWPrpList.indexOf("DEPT")+1;
               int indexMIXSZ = vWPrpList.indexOf("MIX_SIZE")+1;
               int indexfapri = vWPrpList.indexOf("FA_PRI")+1;
               int indexMemo = vWPrpList.indexOf(memo)+1;
               int indexFactory = vWPrpList.indexOf("FACTORY")+1;
               int indexAvg = vWPrpList.indexOf("AVG")+1;
               int indexmixclr = vWPrpList.indexOf("MIX_CLARITY")+1;
               int indexcol = vWPrpList.indexOf("COL")+1;
               int indexmfgpri= vWPrpList.indexOf("MFG_PRI")+1;
               int indexcp= vWPrpList.indexOf("CP")+1;
               String shPrp = util.prpsrtcolumn("prp",indexSH);
               String szPrp = util.prpsrtcolumn("prp",indexSZ);
               String deptPrp = util.prpsrtcolumn("prp",indexdept);
               String mixszPrp = util.prpsrtcolumn("prp",indexMIXSZ);
               String fapriPrp = util.prpsrtcolumn("prp",indexfapri);
               String memoPrp = util.prpsrtcolumn("prp",indexMemo);
               String facPrp = util.prpsrtcolumn("prp",indexFactory);
               String avgPrp = util.prpsrtcolumn("prp",indexAvg);
               String mixclrPrp = util.prpsrtcolumn("prp",indexmixclr);
               String colPrp = util.prpsrtcolumn("prp",indexcol);
               String colmfgpri = util.prpsrtcolumn("prp",indexmfgpri);
               String colcp = util.prpsrtcolumn("srt",indexcp);
               String shSrt = util.prpsrtcolumn("srt",indexSH);
               String szSrt = util.prpsrtcolumn("srt",indexSZ);
               String deptSrt = util.prpsrtcolumn("srt",indexdept);  
               String mixszSrt = util.prpsrtcolumn("srt",indexMIXSZ);
               String fapriSrt = util.prpsrtcolumn("srt",indexfapri);
               String facSrt= util.prpsrtcolumn("srt",indexFactory);
               String avgSrt= util.prpsrtcolumn("srt",indexAvg);
               String colSrt= util.prpsrtcolumn("srt",indexcol);
               String mixclrSrt= util.prpsrtcolumn("srt",indexmixclr);
               HashMap prp = info.getPrp();
               HashMap mprp = info.getMprp();
               ArrayList prpPrtDept=null;
               ArrayList prpValDept=null;
               ArrayList prpSrtDept = null;
               prpPrtDept = (ArrayList)prp.get("DEPT"+"P");
               prpSrtDept = (ArrayList)prp.get("DEPT"+"S");
               prpValDept= (ArrayList)prp.get("DEPT"+"V");
           if(!pDteFrm.equals("") && !pDteTo.equals("")){
               memoPRCQ="report_pkg.MEMO_PRI_COMP(pMemo =>?, pDteFrm=>?, pDteTo=>?)";
               ary=new ArrayList();
               ary.add(memoIdn);
               ary.add(pDteFrm);
               ary.add(pDteTo);
           }
           int ct = db.execCall("memoPRCQ", memoPRCQ,ary);     
               
           String memoQ="select "+memoPrp+" memo,"+deptPrp+" dept,"+deptSrt+" dept_so,"+shPrp+" sh,"+shSrt+" sh_so,\n" + 
           "decode(pkt_ty,'NR',"+szPrp+","+mixszPrp+") sz, decode(pkt_ty,'NR',"+szSrt+","+mixszSrt+") sz_so,\n" + 
           "count(*) qty, to_char(sum(cts),'99999990.00') cts," + 
           "to_char(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts),1)/sum(cts),2),'99999990.00') mfg_avg,"+
           "trunc(sum(nvl("+colcp+", 0)*cts)/sum(cts), 2) cp_avg,"+
           "decode(min(to_number(nvl("+fapriPrp+",0))), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2),'99999990.00')) fa_avg,to_char(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2),'99999990.00') avg,trunc(greatest(sum(cts*to_number("+fapriPrp+")*fctr),1)*100/greatest(sum(cts*to_number("+fapriPrp+")),1), 2) colVlu_PCT \n" + 
           ",to_char(trunc(greatest(sum(to_number(prte)*cts),1)/sum(cts),2),'99999990.00') prte,\n" + 
           "to_char(trunc(greatest(sum(to_number(mrte)*cts),1)/sum(cts),2),'99999990.00') mrte,decode(min(to_number("+fapriPrp+")), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1),2),'99999990.00')) fa_amt\n"+
           "from gt_srch_rslt gt,(select stk_idn,\n" + 
           "CASE pkt_ty\n" + 
           "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
           "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
           "END fctr\n" + 
           "from gt_srch_rslt) gtCol\n" + 
           "where gt.stk_idn = gtCol.stk_idn and nvl(cts,0) > 0 \n" + 
           "group by "+memoPrp+" ,"+deptPrp+" ,"+deptSrt+" ,"+shPrp+" ,"+shSrt+" ,\n" + 
           "Decode(Pkt_Ty,'NR',"+szPrp+","+mixszPrp+") , Decode(Pkt_Ty,'NR',"+szSrt+","+mixszSrt+")\n" + 
           "Order By Dept_So,Sh_So,Sz_So";

               ArrayList outLst = db.execSqlLst("memo by Shape", memoQ, new ArrayList());
               PreparedStatement pst = (PreparedStatement)outLst.get(0);
               ResultSet rs = (ResultSet)outLst.get(1);
           while(rs.next()){
               data=new HashMap();   
               String deptsrt=util.nvl((String)rs.getString("dept_so"));
               String shapesrt=util.nvl((String)rs.getString("sh_so"));
               String key=deptsrt+"_"+shapesrt;
               if(!keytable.contains(key)){
                   keytable.add(key);
                   conatinsdata=new ArrayList();       
               }
               if(!deptLst.contains(deptsrt)){
                   deptLst.add(deptsrt);      
               }
               data.put("DEPT",util.nvl((String)rs.getString("dept")));
               data.put("SH",util.nvl((String)rs.getString("sh")));
               data.put("SZ",util.nvl((String)rs.getString("sz")));
               data.put("QTY",util.nvl((String)rs.getString("qty")));
               data.put("CTS",util.nvl((String)rs.getString("cts")));
               data.put("AVG",util.nvl((String)rs.getString("avg")));
               data.put("P1",util.nvl((String)rs.getString("prte")));
               data.put("P2",util.nvl((String)rs.getString("mrte")));
               data.put("FAAMT",util.nvl((String)rs.getString("fa_amt")));
               data.put("FAAVG",util.nvl((String)rs.getString("fa_avg")));
               data.put("CPAVG",util.nvl((String)rs.getString("cp_avg")));
               data.put("MFGAVG",util.nvl((String)rs.getString("mfg_avg")));
               data.put("COLPCT",util.nvl((String)rs.getString("colVlu_PCT")));
               conatinsdata.add(data);
               dataDtl.put(key,conatinsdata);       
           }
               rs.close(); pst.close();
           String shapeQ="select "+deptPrp+" dept,"+deptSrt+" dept_so,"+shPrp+" sh,"+shSrt+" sh_so,\n" + 
           "count(*) qty, to_char(sum(cts),'99999990.00') cts,\n" +
           "to_char(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts),1)/sum(cts),2),'99999990.00') mfg_avg,"+
           "trunc(sum(nvl("+colcp+", 0)*cts)/sum(cts), 2) cp_avg,"+
           "decode(min(to_number(nvl("+fapriPrp+",0))), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2),'99999990.00')) fa_avg,to_char(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2),'99999990.00') avg,trunc(greatest(sum(cts*to_number("+fapriPrp+")*fctr),1)*100/greatest(sum(cts*to_number("+fapriPrp+")),1), 2) colVlu_PCT \n" + 
           ",to_char(trunc(greatest(sum(to_number(prte)*cts),1)/sum(cts),2),'99999990.00') prte,\n" + 
           "to_char(trunc(greatest(sum(to_number(mrte)*cts),1)/sum(cts),2),'99999990.00') mrte,decode(min(to_number("+fapriPrp+")), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1),2),'99999990.00')) fa_amt\n"+
           "from gt_srch_rslt gt,(select stk_idn,\n" + 
           "CASE pkt_ty\n" + 
           "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
           "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
           "END fctr\n" + 
           "from gt_srch_rslt) gtCol\n" + 
           "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 \n" + 
           "group by "+deptPrp+" ,"+deptSrt+" ,"+shPrp+" ,"+shSrt+" \n" + 
           "Order By Dept_So,Sh_So";

               outLst = db.execSqlLst("Sum by shape", shapeQ, new ArrayList());
               pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
           while(rs.next()){ 
               data=new HashMap();   
               String deptsrt=util.nvl((String)rs.getString("dept_so"));
               String shapesrt=util.nvl((String)rs.getString("sh_so"));
               String key=deptsrt+"_"+shapesrt;
               data.put("DEPT",util.nvl((String)rs.getString("dept")));
               data.put("SH",util.nvl((String)rs.getString("sh")));
               data.put("QTY",util.nvl((String)rs.getString("qty")));
               data.put("CTS",util.nvl((String)rs.getString("cts")));
               data.put("AVG",util.nvl((String)rs.getString("avg")));
               data.put("P1",util.nvl((String)rs.getString("prte")));
               data.put("P2",util.nvl((String)rs.getString("mrte")));
               data.put("FAAMT",util.nvl((String)rs.getString("fa_amt")));
               data.put("FAAVG",util.nvl((String)rs.getString("fa_avg")));
               data.put("CPAVG",util.nvl((String)rs.getString("cp_avg")));
               data.put("COLPCT",util.nvl((String)rs.getString("colVlu_PCT")));
               data.put("MFGAVG",util.nvl((String)rs.getString("mfg_avg")));
               datattl.put(key,data);  
           }
               rs.close(); pst.close();
           String deptQ=" select "+deptPrp+" dept,"+deptSrt+" dept_so,\n" + 
           "count(*) qty,to_char(sum(cts),'99999990.00') cts,\n" + 
           "to_char(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts),1)/sum(cts),2),'99999990.00') mfg_avg,\n"+
           "trunc(sum(nvl("+colcp+", 0)*cts)/sum(cts), 2) cp_avg,"+
           "decode(min(to_number(nvl("+fapriPrp+",0))), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2),'99999990.00')) fa_avg,to_char(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2),'99999990.00') avg,trunc(greatest(sum(cts*to_number("+fapriPrp+")*fctr),1)*100/greatest(sum(cts*to_number("+fapriPrp+")),1), 2) colVlu_PCT \n" + 
               ",to_char(trunc(greatest(sum(to_number(prte)*cts),1)/sum(cts),2),'99999990.00') prte,\n" + 
               "to_char(trunc(greatest(sum(to_number(mrte)*cts),1)/sum(cts),2),'99999990.00') mrte,decode(min(to_number("+fapriPrp+")), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1),2),'99999990.00')) fa_amt\n"+        
           "From Gt_Srch_Rslt gt,(select stk_idn,\n" + 
           "CASE pkt_ty\n" + 
           "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
           "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
           "END fctr\n" + 
           "from gt_srch_rslt) gtCol\n" + 
           "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 \n" + 
           "GROUP BY "+deptPrp+", "+deptSrt+"\n" + 
           "Order By Dept_So";

               outLst = db.execSqlLst("Sum by Dept", deptQ, new ArrayList());
               pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
           while(rs.next()){
               data=new HashMap();
               String deptsrt=util.nvl((String)rs.getString("dept_so"));
               data.put("DEPT",util.nvl((String)rs.getString("dept")));
               data.put("QTY",util.nvl((String)rs.getString("qty")));
               data.put("CTS",util.nvl((String)rs.getString("cts")));
               data.put("AVG",util.nvl((String)rs.getString("avg")));
               data.put("P1",util.nvl((String)rs.getString("prte")));
               data.put("P2",util.nvl((String)rs.getString("mrte")));
               data.put("FAAMT",util.nvl((String)rs.getString("fa_amt")));
               data.put("FAAVG",util.nvl((String)rs.getString("fa_avg")));
               data.put("CPAVG",util.nvl((String)rs.getString("cp_avg")));
               data.put("COLPCT",util.nvl((String)rs.getString("colVlu_PCT")));
               data.put("MFGAVG",util.nvl((String)rs.getString("mfg_avg")));
               dataDtl.put(deptsrt,data);    
           }
               rs.close(); pst.close();
               
               String shpdeptttQ=" select "+deptPrp+" dept,"+deptSrt+" dept_so,\n" + 
               "count(*) qty,to_char(sum(cts),'99999990.00') cts,\n" + 
               "to_char(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts),1)/sum(cts),2),'99999990.00') mfg_avg,\n" + 
               "trunc(sum(nvl("+colcp+", 0)*cts)/sum(cts), 2) cp_avg,"+
               "decode(min(to_number(nvl("+fapriPrp+",0))), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2),'99999990.00')) fa_avg,to_char(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2),'99999990.00') avg,trunc(greatest(sum(cts*to_number("+fapriPrp+")*fctr),1)*100/greatest(sum(cts*to_number("+fapriPrp+")),1), 2) colVlu_PCT \n" + 
               ",to_char(trunc(greatest(sum(to_number(prte)*cts),1)/sum(cts),2),'99999990.00') prte,\n" + 
               "to_char(trunc(greatest(sum(to_number(mrte)*cts),1)/sum(cts),2),'99999990.00') mrte,decode(min(to_number("+fapriPrp+")), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1),2),'99999990.00')) fa_amt\n"+
               "From Gt_Srch_Rslt gt,(select stk_idn,\n" + 
               "CASE pkt_ty\n" + 
               "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
               "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
               "END fctr\n" + 
               "from gt_srch_rslt) gtCol\n" + 
               "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 and "+shPrp+" not  in('ROUND','RD')\n" + 
               "GROUP BY "+deptPrp+", "+deptSrt+"\n" + 
               "Order By Dept_So ";

               outLst = db.execSqlLst("Sum by Shape Dept", shpdeptttQ, new ArrayList());
               pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
               while(rs.next()){
                   data=new HashMap();
                   String deptsrt=util.nvl((String)rs.getString("dept_so"));
                   data.put("DEPT",util.nvl((String)rs.getString("dept")));
                   data.put("QTY",util.nvl((String)rs.getString("qty")));
                   data.put("CTS",util.nvl((String)rs.getString("cts")));
                   data.put("AVG",util.nvl((String)rs.getString("avg")));
                   data.put("P1",util.nvl((String)rs.getString("prte")));
                   data.put("P2",util.nvl((String)rs.getString("mrte")));
                   data.put("FAAMT",util.nvl((String)rs.getString("fa_amt")));
                   data.put("FAAVG",util.nvl((String)rs.getString("fa_avg")));
                   data.put("CPAVG",util.nvl((String)rs.getString("cp_avg")));
                   data.put("COLPCT",util.nvl((String)rs.getString("colVlu_PCT")));
                   data.put("MFGAVG",util.nvl((String)rs.getString("mfg_avg")));
                   dataDtl.put(deptsrt+"_TTL",data);    
               }
                   rs.close(); pst.close();
           String grandQ="select \n" + 
           "count(*) qty, to_char(sum(cts),'99999990.00') cts,\n" + 
           "to_char(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts),1)/sum(cts),2),'99999990.00') mfg_avg,\n"+
           "trunc(sum(nvl("+colcp+", 0)*cts)/sum(cts), 2) cp_avg,"+
           "decode(min(to_number(nvl("+fapriPrp+",0))), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2),'99999990.00')) fa_avg,to_char(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2),'99999990.00') avg,trunc(greatest(sum(cts*to_number("+fapriPrp+")*fctr),1)*100/greatest(sum(cts*to_number("+fapriPrp+")),1), 2) colVlu_PCT \n" + 
               ",to_char(trunc(greatest(sum(to_number(prte)*cts),1)/sum(cts),2),'99999990.00') prte,\n" + 
               "to_char(trunc(greatest(sum(to_number(mrte)*cts),1)/sum(cts),2),'99999990.00') mrte,decode(min(to_number("+fapriPrp+")), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1),2),'99999990.00')) fa_amt\n"+        
           "From Gt_Srch_Rslt gt,(select stk_idn,\n" + 
           "CASE pkt_ty\n" + 
           "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
           "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
           "END fctr\n" + 
           "from gt_srch_rslt) gtCol\n" + 
           "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 ";

               outLst = db.execSqlLst("Grand Totals", grandQ, new ArrayList());
               pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
           while(rs.next()){ 
               data=new HashMap();
               data.put("QTY",util.nvl((String)rs.getString("qty")));
               data.put("CTS",util.nvl((String)rs.getString("cts")));
               data.put("AVG",util.nvl((String)rs.getString("avg")));
               data.put("P1",util.nvl((String)rs.getString("prte")));
               data.put("P2",util.nvl((String)rs.getString("mrte")));
               data.put("FAAMT",util.nvl((String)rs.getString("fa_amt")));
               data.put("FAAVG",util.nvl((String)rs.getString("fa_avg")));
               data.put("CPAVG",util.nvl((String)rs.getString("cp_avg")));
               data.put("COLPCT",util.nvl((String)rs.getString("colVlu_PCT")));
               data.put("MFGAVG",util.nvl((String)rs.getString("mfg_avg")));
               String compDteQ="select comp_dte,acc_av,avg_50,idn,ketan_av from mlot where dsc='"+memoIdn+"'";

               ArrayList outLst1 = db.execSqlLst("Comp Dte", compDteQ,  new ArrayList());
               PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
               ResultSet rs1 = (ResultSet)outLst1.get(1);
               if(rs1.next()){
                   mlotidn=util.nvl((String)rs1.getString("idn"));
                   data.put("IDN",mlotidn);
                   data.put("CMPDTE",util.nvl((String)rs1.getString("comp_dte"))); 
                   udf.setValue("ACC_"+mlotidn, util.nvl((String)rs1.getString("acc_av")));  
                   udf.setValue("AVG_"+mlotidn, util.nvl((String)rs1.getString("avg_50")));  
                   udf.setValue("KET_"+mlotidn, util.nvl((String)rs1.getString("ketan_av")));  
               }
               rs1.close(); pst1.close();
               String facQ="select distinct "+facPrp+" factory from gt_srch_rslt where "+facPrp+" is not null";

               ArrayList outLst2 = db.execSqlLst("Factory", facQ,  new ArrayList());
               PreparedStatement pst2 = (PreparedStatement)outLst2.get(0);
               ResultSet rs2 = (ResultSet)outLst2.get(1);
               if(rs2.next()){
                   data.put("FAC",util.nvl((String)rs2.getString("factory")));    
               }
               rs2.close(); pst2.close();
               data.put("MEMO",memoIdn);   
               dataDtl.put("GRANDTTL",data);  
           }
               rs.close(); pst.close();
           String mlotdeptQ="select acc_av,avg_50,dept,ketan_av from mlot_dept where idn='"+mlotidn+"'";

               outLst = db.execSqlLst("mlotdeptQ", mlotdeptQ, new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
           while(rs.next()){
             String dept=util.nvl((String)rs.getString("dept"));
             String deptsrt=(String)prpSrtDept.get(prpValDept.indexOf(dept));
             udf.setValue("ACC_"+mlotidn+"_"+deptsrt, util.nvl((String)rs.getString("acc_av"))); 
             udf.setValue("AVG_"+mlotidn+"_"+deptsrt, util.nvl((String)rs.getString("avg_50")));  
             udf.setValue("KET_"+mlotidn+"_"+deptsrt, util.nvl((String)rs.getString("ketan_av")));  
           }
               rs.close(); pst.close();
               
           //new
           shapeQ="select "+shPrp+" sh,"+shSrt+" sh_so,\n" + 
           "count(*) qty, to_char(sum(cts),'99999990.00') cts,\n" +
           "to_char(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts),1)/sum(cts),2),'99999990.00') mfg_avg,"+
           "trunc(sum(nvl("+colcp+", 0)*cts)/sum(cts), 2) cp_avg,"+
           "decode(min(to_number(nvl("+fapriPrp+",0))), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2),'99999990.00')) fa_avg,to_char(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2),'99999990.00') avg,trunc(greatest(sum(cts*to_number("+fapriPrp+")*fctr),1)*100/greatest(sum(cts*to_number("+fapriPrp+")),1), 2) colVlu_PCT \n" + 
           ",to_char(trunc(greatest(sum(to_number(prte)*cts),1)/sum(cts),2),'99999990.00') prte,\n" + 
           "to_char(trunc(greatest(sum(to_number(mrte)*cts),1)/sum(cts),2),'99999990.00') mrte,decode(min(to_number("+fapriPrp+")), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1),2),'99999990.00')) fa_amt\n"+
           "from gt_srch_rslt gt,(select stk_idn,\n" + 
           "CASE pkt_ty\n" + 
           "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
           "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
           "END fctr\n" + 
           "from gt_srch_rslt) gtCol\n" + 
           "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 \n" + 
           "group by "+shPrp+" ,"+shSrt+" \n" + 
           "Order By Sh_So";

               outLst = db.execSqlLst("Sum by shape", shapeQ, new ArrayList());
               pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
           while(rs.next()){ 
               data=new HashMap();   
               String shapesrt=util.nvl((String)rs.getString("sh_so"));
               String key=shapesrt;
               data.put("SH",util.nvl((String)rs.getString("sh")));
               data.put("QTY",util.nvl((String)rs.getString("qty")));
               data.put("CTS",util.nvl((String)rs.getString("cts")));
               data.put("AVG",util.nvl((String)rs.getString("avg")));
               data.put("P1",util.nvl((String)rs.getString("prte")));
               data.put("P2",util.nvl((String)rs.getString("mrte")));
               data.put("FAAMT",util.nvl((String)rs.getString("fa_amt")));
               data.put("FAAVG",util.nvl((String)rs.getString("fa_avg")));
               data.put("CPAVG",util.nvl((String)rs.getString("cp_avg")));
               data.put("COLPCT",util.nvl((String)rs.getString("colVlu_PCT")));
               data.put("MFGAVG",util.nvl((String)rs.getString("mfg_avg")));
               datattl.put(key,data);  
           }
               rs.close(); pst.close();   
           session.setAttribute("dataDtl", dataDtl);
           session.setAttribute("datattl", datattl);
           session.setAttribute("keytable", keytable);
           session.setAttribute("deptLst", deptLst);
           req.setAttribute("NORMAL", "Y");
           piememodata(req,res,udf);
           }else{
                   String memoPRCQ="pop_memo_rep_data_dept_smry(?,?)";
                   ArrayList ary=new ArrayList();
                   ary.add(memoIdn);
                   ary.add("MEMO_LOT_DEPT");
                   int ct = db.execCall("memoPRCQ", memoPRCQ,ary);  
                   String sh = (String)dbinfo.get("SHAPE");
                   String szval = (String)dbinfo.get("SIZE");
                   String memo = util.nvl((String)dbinfo.get("MEMO"));
                   ArrayList vWPrpList = genericInt.genericPrprVw(req,res,"MEMO_LOT_DEPT","MEMO_LOT_DEPT");
                   int indexSH = vWPrpList.indexOf(sh)+1;
                   int indexSZ = vWPrpList.indexOf(szval)+1;
                   int indexdept = vWPrpList.indexOf("DEPT")+1;
                   int indexMIXSZ = vWPrpList.indexOf("MIX_SIZE")+1;
                   int indexfapri = vWPrpList.indexOf("FA_PRI")+1;
                   int indexMemo = vWPrpList.indexOf(memo)+1;
                   int indexFactory = vWPrpList.indexOf("FACTORY")+1;
                   int indexAvg = vWPrpList.indexOf("AVG")+1;
                   int indexmixclr = vWPrpList.indexOf("MIX_CLARITY")+1;
                   int indexcol = vWPrpList.indexOf("COL")+1;
                   int indexmfgpri= vWPrpList.indexOf("MFG_PRI")+1;
                   int indexmfgcts= vWPrpList.indexOf("MFG_CTS")+1;
                   int indexLabCharges= vWPrpList.indexOf("LAB_CHARGES")+1;
                   String shPrp = util.prpsrtcolumn("prp",indexSH);
                   String szPrp = util.prpsrtcolumn("prp",indexSZ);
                   String deptPrp = util.prpsrtcolumn("prp",indexdept);
                   String mixszPrp = util.prpsrtcolumn("prp",indexMIXSZ);
                   String fapriPrp = util.prpsrtcolumn("prp",indexfapri);
                   String memoPrp = util.prpsrtcolumn("prp",indexMemo);
                   String facPrp = util.prpsrtcolumn("prp",indexFactory);
                   String avgPrp = util.prpsrtcolumn("prp",indexAvg);
                   String mixclrPrp = util.prpsrtcolumn("prp",indexmixclr);
                   String colPrp = util.prpsrtcolumn("prp",indexcol);
                   String colmfgpri = util.prpsrtcolumn("prp",indexmfgpri);
                   String colmfgcts = util.prpsrtcolumn("prp",indexmfgcts);
                   String colLabCharges = util.prpsrtcolumn("prp",indexLabCharges);
                   String shSrt = util.prpsrtcolumn("srt",indexSH);
                   String szSrt = util.prpsrtcolumn("srt",indexSZ);
                   String deptSrt = util.prpsrtcolumn("srt",indexdept);  
                   String mixszSrt = util.prpsrtcolumn("srt",indexMIXSZ);
                   String fapriSrt = util.prpsrtcolumn("srt",indexfapri);
                   String facSrt= util.prpsrtcolumn("srt",indexFactory);
                   String avgSrt= util.prpsrtcolumn("srt",indexAvg);
                   String colSrt= util.prpsrtcolumn("srt",indexcol);
                   String mixclrSrt= util.prpsrtcolumn("srt",indexmixclr);
                   String mlotQ=" select "+memoPrp+" memo,"+deptPrp+" dept,\n" + 
                   "to_char(sum(nvl("+colmfgcts+",cts)),'99999990.00') actcts,\n" + 
                   "round(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*nvl("+colmfgcts+",cts))- sum(nvl("+colLabCharges+", 0)),1)/sum(nvl("+colmfgcts+",cts)),2)) actmfgavg,\n"+      
                   "decode(min(to_number("+fapriPrp+")), 0, 0, round(trunc(greatest(sum(to_number("+fapriPrp+")*nvl("+colmfgcts+",cts)),1)/sum(nvl("+colmfgcts+",cts)),2))) Actfa_Pri_Avg,round(trunc(greatest(sum(to_number("+avgPrp+")*nvl("+colmfgcts+",cts)),1)/sum(nvl("+colmfgcts+",cts)),2)) Actavg \n" +  
                   ",Round(Trunc(Greatest(Sum(To_Number(Nvl(Fnl_Usd,"+fapriPrp+"))*Cts),1)/Sum(Cts),2)) Salrte "+
                   "From Gt_Srch_Rslt gt,(select stk_idn,\n" + 
                   "CASE pkt_ty\n" + 
                   "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
                   "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
                   "END fctr\n" + 
                   "from gt_srch_rslt) gtCol\n" + 
                   "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 \n" + 
                   "GROUP BY "+memoPrp+","+deptPrp+" \n"+
                   "union"+
                   " select "+memoPrp+" memo,'TOTAL' dept,\n" + 
                   "to_char(sum(nvl("+colmfgcts+",cts)),'99999990.00') actcts,\n" + 
                   "round(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*nvl("+colmfgcts+",cts))- sum(nvl("+colLabCharges+", 0)),1)/sum(nvl("+colmfgcts+",cts)),2)) actmfgavg,\n"+      
                   "decode(min(to_number("+fapriPrp+")), 0, 0, round(trunc(greatest(sum(to_number("+fapriPrp+")*nvl("+colmfgcts+",cts)),1)/sum(nvl("+colmfgcts+",cts)),2))) Actfa_Pri_Avg,round(trunc(greatest(sum(to_number("+avgPrp+")*nvl("+colmfgcts+",cts)),1)/sum("+colmfgcts+"),2)) Actavg \n" +  
                   ",Round(Trunc(Greatest(Sum(To_Number(Nvl(Fnl_Usd,"+fapriPrp+"))*Cts),1)/Sum(Cts),2)) Salrte "+
                   "From Gt_Srch_Rslt gt,(select stk_idn,\n" + 
                   "CASE pkt_ty\n" + 
                   "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
                   "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
                   "END fctr\n" + 
                   "from gt_srch_rslt) gtCol\n" + 
                   "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 \n" + 
                   "GROUP BY "+memoPrp;            

                   ArrayList outLst = db.execSqlLst("Fetch Mlot", mlotQ,  new ArrayList());
                   PreparedStatement pst = (PreparedStatement)outLst.get(0);
                   ResultSet rs = (ResultSet)outLst.get(1);
                   while(rs.next()){
                   String memoidn=util.nvl((String)rs.getString("Memo"));
                   String dept=util.nvl((String)rs.getString("dept"));
                   String key=memoidn+"_"+dept;
                   dataDtl.put(key+"_ACT_CTS", util.nvl((String)rs.getString("actcts")));
                   dataDtl.put(key+"_ACT_FAAVG", util.nvl((String)rs.getString("Actfa_Pri_Avg")));
                   dataDtl.put(key+"_ACT_AVG", util.nvl((String)rs.getString("Actavg")));
                   dataDtl.put(key+"_ACT_MFGAVG", util.nvl((String)rs.getString("actmfgavg")));
                   dataDtl.put(key+"_ACT_SLRTE", util.nvl((String)rs.getString("Salrte")));
                   if(!memoList.contains(memoidn))
                       memoList.add(memoidn);
                   }
                   rs.close(); pst.close();
                   mlotQ=" select "+memoPrp+" memo,"+deptPrp+" dept,\n" + 
                   "to_char(sum(cts),'99999990.00') wmksdcts,\n" + 
                   "round(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts)- sum(nvl("+colLabCharges+", 0)),1)/sum(cts),2)) wmksdmfgavg,\n"+
                   "decode(min(to_number("+fapriPrp+")), 0, 0, round(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2))) wmksdfa_Pri_Avg,round(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2)) wmksdavg \n" +       
                   ",Round(Trunc(Greatest(Sum(To_Number(Nvl(Fnl_Usd,"+fapriPrp+"))*Cts),1)/Sum(Cts),2)) Salrte "+
                   "From Gt_Srch_Rslt gt,(select stk_idn,\n" + 
                   "CASE pkt_ty\n" + 
                   "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
                   "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
                   "END fctr\n" + 
                   "from gt_srch_rslt) gtCol\n" + 
                   "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 and stt not in ('MKSD','BRC_MKSD') and pkt_ty='NR' \n" + 
                   "GROUP BY "+memoPrp+","+deptPrp+" \n"+
                   "union"+
                   " select "+memoPrp+" memo,'TOTAL' dept,\n" + 
                   "to_char(sum(cts),'99999990.00') wmksdcts,\n" + 
                   "round(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts)- sum(nvl("+colLabCharges+", 0)),1)/sum(cts),2)) wmksdmfgavg,\n"+
                   "decode(min(to_number("+fapriPrp+")), 0, 0, round(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2))) wmksdfa_Pri_Avg,round(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2)) wmksdavg \n" +       
                   ",Round(Trunc(Greatest(Sum(To_Number(Nvl(Fnl_Usd,"+fapriPrp+"))*Cts),1)/Sum(Cts),2)) Salrte "+
                   "From Gt_Srch_Rslt gt,(select stk_idn,\n" + 
                   "CASE pkt_ty\n" + 
                   "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
                   "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
                   "END fctr\n" + 
                   "from gt_srch_rslt) gtCol\n" + 
                   "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 and stt not in ('MKSD','BRC_MKSD') and pkt_ty='NR' \n" + 
                   "GROUP BY "+memoPrp;     

                   outLst = db.execSqlLst("Fetch Mlot", mlotQ,  new ArrayList());
                   pst = (PreparedStatement)outLst.get(0);
                   rs = (ResultSet)outLst.get(1);
                   while(rs.next()){
                       String memoidn=util.nvl((String)rs.getString("Memo"));
                       String dept=util.nvl((String)rs.getString("dept"));
                       String key=memoidn+"_"+dept;
                       dataDtl.put(key+"_WMKSD_CTS", util.nvl((String)rs.getString("wmksdcts")));
                       dataDtl.put(key+"_WMKSD_FAAVG", util.nvl((String)rs.getString("wmksdfa_Pri_Avg")));
                       dataDtl.put(key+"_WMKSD_AVG", util.nvl((String)rs.getString("wmksdavg")));
                       dataDtl.put(key+"_WMKSD_MFGAVG", util.nvl((String)rs.getString("wmksdmfgavg")));                 
                       dataDtl.put(key+"_WMKSD_SLRTE", util.nvl((String)rs.getString("Salrte")));
                   }
                   rs.close(); pst.close();
                   mlotQ=" select "+memoPrp+" memo,"+deptPrp+" dept,\n" + 
                   "to_char(sum(cts),'99999990.00') mksdcts,\n" + 
                   "round(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts)- sum(nvl("+colLabCharges+", 0)),1)/sum(cts),2)) mksdmfgavg,\n"+
                   "decode(min(to_number("+fapriPrp+")), 0, 0, round(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2))) mksdfa_Pri_Avg,round(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2)) mksdavg \n" +       
                   ",Round(Trunc(Greatest(Sum(To_Number(Nvl(Fnl_Usd,"+fapriPrp+"))*Cts),1)/Sum(Cts),2)) Salrte "+
                   "From Gt_Srch_Rslt gt,(select stk_idn,\n" + 
                   "CASE pkt_ty\n" + 
                   "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
                   "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
                   "END fctr\n" + 
                   "from gt_srch_rslt) gtCol\n" + 
                   "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 and stt in ('MKSD','MEMO_MRG','AS_FN_MRG','DEPT_MRG') \n" + 
                   "GROUP BY "+memoPrp+","+deptPrp+" \n"+
                   "union"+
                   " select "+memoPrp+" memo,'TOTAL' dept,\n" + 
                   "to_char(sum(cts),'99999990.00') mksdcts,\n" + 
                   "round(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts)- sum(nvl("+colLabCharges+", 0)),1)/sum(cts),2)) mksdmfgavg,\n"+
                   "decode(min(to_number("+fapriPrp+")), 0, 0, round(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2))) mksdfa_Pri_Avg,round(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2)) mksdavg \n" +       
                   ",Round(Trunc(Greatest(Sum(To_Number(Nvl(Fnl_Usd,"+fapriPrp+"))*Cts),1)/Sum(Cts),2)) Salrte "+
                   "From Gt_Srch_Rslt gt,(select stk_idn,\n" + 
                   "CASE pkt_ty\n" + 
                   "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
                   "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
                   "END fctr\n" + 
                   "from gt_srch_rslt) gtCol\n" + 
                   "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 and stt in ('MKSD','MEMO_MRG','AS_FN_MRG','DEPT_MRG') \n" + 
                   "GROUP BY "+memoPrp;    

                   outLst = db.execSqlLst("Fetch Mlot", mlotQ,  new ArrayList());
                   pst = (PreparedStatement)outLst.get(0);
                   rs = (ResultSet)outLst.get(1);
                   while(rs.next()){
                       String memoidn=util.nvl((String)rs.getString("Memo"));
                       String dept=util.nvl((String)rs.getString("dept"));
                       String key=memoidn+"_"+dept;
                       dataDtl.put(key+"_MKSD_CTS", util.nvl((String)rs.getString("mksdcts")));
                       dataDtl.put(key+"_MKSD_FAAVG", util.nvl((String)rs.getString("mksdfa_Pri_Avg")));
                       dataDtl.put(key+"_MKSD_AVG", util.nvl((String)rs.getString("mksdavg")));
                       dataDtl.put(key+"_MKSD_MFGAVG", util.nvl((String)rs.getString("mksdmfgavg")));       
                       dataDtl.put(key+"_MKSD_SLRTE", util.nvl((String)rs.getString("Salrte")));                 
                   }
                   rs.close(); pst.close();
                   Collections.sort(memoList);
                   req.setAttribute("NORMAL", "DEPTSUMMY");
                   session.setAttribute("dataDtl", dataDtl);
                   session.setAttribute("memoList", memoList);
               }
           }else{
               ArrayList pktList=new ArrayList();
               String startfrmdte=util.nvl((String)udf.getValue("startfrmdte")); 
               String starttodte=util.nvl((String)udf.getValue("starttodte"));
               String compfrdte=util.nvl((String)udf.getValue("compfrdte"));
               String comptodte=util.nvl((String)udf.getValue("comptodte"));
               String stt=util.nvl((String)udf.getValue("stt"));
               String mgmt=util.nvl((String)udf.getValue("mgmt"));
               udf.reset();
               String mlotQ="    Select a.dsc memo,Count(*) Qty,To_Char(Sum(B.Cts),'99999990.00') Cts,\n" + 
               "    To_Char(Trunc(Sum(b.cts*greatest(fa.num,1))/Sum(b.cts), 2),'99999990.00') Fa_Pri_Avg\n" + 
               "    From Mlot A, Mstk B \n" + 
               "    , STK_DTL MO\n" + 
               "    , STK_DTL FA\n" + 
               "     Where  \n" + 
               "     A.Dsc = Mo.Txt  And B.stt not in ('AS_MRG', 'POST_MRG')\n" + 
               "     And B.Idn = Mo.Mstk_Idn And Mo.Grp = 1 And Mo.Mprp = 'MEMO'\n" + 
               "     And B.Idn = Fa.Mstk_Idn And Fa.Grp = 1 And Fa.Mprp = 'FA_PRI' ";
               if(!startfrmdte.equals("") && !starttodte.equals("")){
               mlotQ = mlotQ+" and trunc(a.dte) between to_date('"+startfrmdte+"' , 'dd-mm-yyyy') and to_date('"+starttodte+"' , 'dd-mm-yyyy') ";
               }  
               if(!compfrdte.equals("") && !comptodte.equals("")){
               mlotQ = mlotQ+" and trunc(a.comp_dte) between to_date('"+compfrdte+"' , 'dd-mm-yyyy') and to_date('"+comptodte+"' , 'dd-mm-yyyy') ";
               }
               if(!stt.equals(""))
               mlotQ = mlotQ+" and a.memo_status='"+stt+"'";  
               if(!mgmt.equals("")){
               if(mgmt.equals("Y"))
               mlotQ = mlotQ+" and a.acc_av > 0";
               if(mgmt.equals("N"))
               mlotQ = mlotQ+" and a.acc_av =0"; 
               }
               if(!memoIdn.equals("")){
               memoIdn=memoIdn.replaceAll(",", "','");
               mlotQ = mlotQ+" and MO.txt in('"+memoIdn+"')"; 
               }
               mlotQ = mlotQ+" group by a.dsc Order by 1";

               ArrayList outLst = db.execSqlLst("Fetch Mlot", mlotQ,  new ArrayList());
               PreparedStatement pst = (PreparedStatement)outLst.get(0);
               ResultSet rs = (ResultSet)outLst.get(1);
               while(rs.next()){
                data=new HashMap();
                   data.put("DSC",util.nvl((String)rs.getString("memo")));
                   data.put("QTY",util.nvl((String)rs.getString("qty")));
                   data.put("CTS",util.nvl((String)rs.getString("cts")));
                   data.put("FAAVG",util.nvl((String)rs.getString("fa_pri_avg")));
                pktList.add(data);
                memoList.add(util.nvl((String)rs.getString("memo")));
               }
               rs.close(); pst.close();
               req.setAttribute("pktList", pktList);
               session.setAttribute("memoList", memoList);
           }
           req.setAttribute("view", "Y");
           req.setAttribute("ketan", ketan);
           req.setAttribute("ifrsBtn", ifrsBtn);
           req.setAttribute("pDteFrm", pDteFrm);
           req.setAttribute("pDteTo", pDteTo);
               util.updAccessLog(req,res,"Report Action", "memohk end");
           return am.findForward("memohk");
           }
       }
    
    public void piememodata(HttpServletRequest req,HttpServletResponse res,ReportForm udf) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        GenericInterface genericInt = new GenericImpl();
        HashMap dbinfo = info.getDmbsInfoLst();
        String colval = (String)dbinfo.get("COL");
        String clrval = (String)dbinfo.get("CLR");
        String deptval = (String)dbinfo.get("DEPT");
        String mixclrval = "MIX_CLARITY";
        ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "MEMO_HKREPORT","MEMO_LOT_VW");
        int indexCO = vWPrpList.indexOf(colval)+1;
        int indexCLR = vWPrpList.indexOf(clrval)+1;
        int indexDEPT = vWPrpList.indexOf(deptval)+1;
        int indexMIXCLR = vWPrpList.indexOf(mixclrval)+1;
        String coPrp = util.prpsrtcolumn("prp",indexCO);
        String clrPrp = util.prpsrtcolumn("prp",indexCLR);
        String deptPrp = util.prpsrtcolumn("prp",indexDEPT);
        String mixclrPrp = util.prpsrtcolumn("prp",indexMIXCLR);
        String coSrt = util.prpsrtcolumn("srt",indexCO);
        String clrSrt = util.prpsrtcolumn("srt",indexCLR);
        String deptSrt = util.prpsrtcolumn("srt",indexDEPT);
        String mixclrSrt = util.prpsrtcolumn("srt",indexMIXCLR);
        String colQ="select b.grp grp,'18-96-GIA' dept,trunc(sum(a.quot*trunc(a.cts,2)),2) vlu\n" + 
        "From Gt_Srch_Rslt A,Prp B\n" + 
        "where a."+coPrp+"=b.val and a.quot > 0 and a."+coSrt+"=b.srt and b.mprp='"+colval+"' and a."+deptPrp+"='18-96-GIA'\n" + 
        "Group By b.grp\n" + 
        "Union\n" + 
        "select b.grp grp,'96-UP-GIA' dept,trunc(sum(a.quot*trunc(a.cts,2)),2) vlu\n" + 
        "From Gt_Srch_Rslt A,Prp B\n" + 
        "where a."+coPrp+"=b.val and a.quot > 0 and  a."+coSrt+"=b.srt and b.mprp='"+colval+"' and a."+deptPrp+"='96-UP-GIA'\n" + 
        "Group By b.grp\n" + 
        "Union\n" + 
        "select b.grp grp,'OverAll GIA' dept,trunc(sum(a.quot*trunc(a.cts,2)),2) vlu\n" + 
        "From Gt_Srch_Rslt A,Prp B\n" + 
        "where a."+coPrp+"=b.val and a.quot > 0 and  a."+coSrt+"=b.srt and b.mprp='"+colval+"' and a."+deptPrp+" in('18-96-GIA','96-UP-GIA')\n" + 
        "Group By b.grp\n" + 
        "Order By 2,1";

        ArrayList outLst = db.execSqlLst("colQ", colQ, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        HashMap dataDtl = new HashMap();
        ArrayList grpLst=new ArrayList();
        ArrayList deptLst=new ArrayList();
        while(rs.next()){
            String grp = util.nvl(rs.getString("grp")); 
            String dept = util.nvl(rs.getString("dept"));
            dataDtl.put("COL_"+grp+"_"+dept,util.nvl(rs.getString("vlu")));
            if(!grpLst.contains(grp))
            grpLst.add(grp);  
            if(!deptLst.contains(dept))
            deptLst.add(dept);  
        }
        rs.close(); pst.close();
        if(grpLst.size()>0){
        dataDtl.put("COL_GRP",grpLst);
        dataDtl.put("COL_DEPT",deptLst);
        }
        grpLst=new ArrayList();
        deptLst=new ArrayList();
        String clrQ="select b.grp grp,'18-96-GIA' dept,trunc(sum(a.quot*trunc(a.cts,2)),2) vlu\n" + 
        "From Gt_Srch_Rslt A,Prp B\n" + 
        "where a."+clrPrp+"=b.val and a.quot > 0 and a."+clrSrt+"=b.srt and b.mprp='"+clrval+"' and a."+deptPrp+"='18-96-GIA'\n" + 
        "Group By b.grp\n" + 
        "Union\n" + 
        "select b.grp grp,'96-UP-GIA' dept,trunc(sum(a.quot*trunc(a.cts,2)),2) vlu\n" + 
        "From Gt_Srch_Rslt A,Prp B\n" + 
        "where a."+clrPrp+"=b.val and a.quot > 0 and  a."+clrSrt+"=b.srt and b.mprp='"+clrval+"' and a."+deptPrp+"='96-UP-GIA'\n" + 
        "Group By b.grp\n" + 
        "Union\n" + 
        "select a1.grp, 'OverAll GIA' dept, sum(vlu) vlu\n" + 
        "from (select b.grp grp, trunc(sum(a.quot*trunc(a.cts,2)),2) vlu\n" + 
        "From Gt_Srch_Rslt A,Prp B\n" + 
        "where a."+clrPrp+"=b.val and a.quot > 0\n" + 
        "and a."+clrSrt+"=b.srt and b.mprp='"+clrval+"'\n" + 
        "and a."+deptPrp+" in('18-96-GIA','96-UP-GIA')\n" + 
        "Group By b.grp\n" + 
        "UNION\n" + 
        "select b.grp grp, trunc(sum(a.quot*trunc(a.cts,2)),2) vlu\n" + 
        "From Gt_Srch_Rslt A,Prp B\n" + 
        "where a."+mixclrPrp+" = b.val and a.quot > 0\n" + 
        "and a."+mixclrSrt+"=b.srt and b.mprp='"+mixclrval+"'\n" + 
        "and a."+deptPrp+" not in('18-96-GIA','96-UP-GIA')\n" + 
        "Group By b.grp) a1\n" + 
        "group by a1.grp\n" + 
        "Order By 2,1";

        outLst = db.execSqlLst("clrQ", clrQ, new ArrayList());
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String grp = util.nvl(rs.getString("grp")); 
            String dept = util.nvl(rs.getString("dept"));
            dataDtl.put("CLR_"+grp+"_"+dept,util.nvl(rs.getString("vlu")));
            if(!grpLst.contains(grp))
            grpLst.add(grp);
            if(!deptLst.contains(dept))
            deptLst.add(dept); 
        }
        if(grpLst.size()>0){
        dataDtl.put("CLR_GRP",grpLst);
        dataDtl.put("CLR_DEPT",deptLst);
        }
        if(dataDtl.size()>0 && dataDtl!=null){
        grpLst=new ArrayList();  
            grpLst.add(colval);
            grpLst.add(clrval);
            dataDtl.put("PRO",grpLst);
            session.setAttribute("PIEdataDtl", dataDtl);
        }
    }
    public ActionForward piememo(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Report Action", "piememo start");
        ReportForm udf = (ReportForm)af;
            piememodata(req,res,udf);
            util.updAccessLog(req,res,"Report Action", "piememo end");
        return am.findForward("piememo");
        }
    }
    public ActionForward memohkPop(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
                util.updAccessLog(req,res,"Report Action", "memohkPop start");
            ReportForm udf = (ReportForm)form;
            GenericInterface genericInt = new GenericImpl();
            String memoIdn=util.nvl((String)req.getParameter("memo")).trim();
            String ketan=util.nvl((String)req.getParameter("ketan")).trim();
            HashMap dataDtl=new HashMap();
            HashMap datattl=new HashMap();
            HashMap data=new HashMap();
            ArrayList keytable=new ArrayList();
            ArrayList deptLst=new ArrayList();
            ArrayList conatinsdata=new ArrayList();
            String mlotidn="";
            if(!memoIdn.equals("")){
                HashMap dbinfo = info.getDmbsInfoLst();
                String sh = (String)dbinfo.get("SHAPE");
                String szval = (String)dbinfo.get("SIZE");
                String memo = util.nvl((String)dbinfo.get("MEMO"));
                ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "MEMO_HKREPORT","MEMO_LOT_VW");
                int indexSH = vWPrpList.indexOf(sh)+1;
                int indexSZ = vWPrpList.indexOf(szval)+1;
                int indexdept = vWPrpList.indexOf("DEPT")+1;
                int indexMIXSZ = vWPrpList.indexOf("MIX_SIZE")+1;
                int indexfapri = vWPrpList.indexOf("FA_PRI")+1;
                int indexMemo = vWPrpList.indexOf(memo)+1;
                int indexFactory = vWPrpList.indexOf("FACTORY")+1;
                int indexAvg = vWPrpList.indexOf("AVG")+1;
                int indexmixclr = vWPrpList.indexOf("MIX_CLARITY")+1;
                int indexcol = vWPrpList.indexOf("COL")+1;
                int indexmfgpri= vWPrpList.indexOf("MFG_PRI")+1;
                String shPrp = util.prpsrtcolumn("prp",indexSH);
                String szPrp = util.prpsrtcolumn("prp",indexSZ);
                String deptPrp = util.prpsrtcolumn("prp",indexdept);
                String mixszPrp = util.prpsrtcolumn("prp",indexMIXSZ);
                String fapriPrp = util.prpsrtcolumn("prp",indexfapri);
                String memoPrp = util.prpsrtcolumn("prp",indexMemo);
                String facPrp = util.prpsrtcolumn("prp",indexFactory);
                String avgPrp = util.prpsrtcolumn("prp",indexAvg);
                String colmfgpri = util.prpsrtcolumn("prp",indexmfgpri);
                String shSrt = util.prpsrtcolumn("srt",indexSH);
                String szSrt = util.prpsrtcolumn("srt",indexSZ);
                String deptSrt = util.prpsrtcolumn("srt",indexdept);  
                String mixszSrt = util.prpsrtcolumn("srt",indexMIXSZ);
                String fapriSrt = util.prpsrtcolumn("srt",indexfapri);
                String facSrt= util.prpsrtcolumn("srt",indexFactory);
                String avgSrt= util.prpsrtcolumn("srt",indexAvg);
                String colSrt= util.prpsrtcolumn("srt",indexcol);
                String mixclrSrt= util.prpsrtcolumn("srt",indexmixclr);
                HashMap prp = info.getPrp();
                HashMap mprp = info.getMprp();
                ArrayList prpPrtDept=null;
                ArrayList prpValDept=null;
                ArrayList prpSrtDept = null;
                prpPrtDept = (ArrayList)prp.get("DEPT"+"P");
                prpSrtDept = (ArrayList)prp.get("DEPT"+"S");
                prpValDept= (ArrayList)prp.get("DEPT"+"V");
            String memoPRCQ="Pop_Memo_Rep_Data(?,?)";
            ArrayList ary=new ArrayList();
            ary.add(memoIdn);
            ary.add("MEMO_LOT_VW");
            int ct = db.execCall("memoPRCQ", memoPRCQ,ary);     
                
            String memoQ="select "+memoPrp+" memo,"+deptPrp+" dept,"+deptSrt+" dept_so,"+shPrp+" sh,"+shSrt+" sh_so,\n" + 
            "decode(pkt_ty,'NR',"+szPrp+","+mixszPrp+") sz, decode(pkt_ty,'NR',"+szSrt+","+mixszSrt+") sz_so,\n" + 
            "count(*) qty, to_char(sum(cts),'99999990.00') cts," + 
            "to_char(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts),1)/sum(cts),2),'99999990.00') mfg_avg,\n"+
            "decode(min(to_number(nvl("+fapriPrp+",0))), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2),'99999990.00')) fa_avg,to_char(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2),'99999990.00') avg,trunc(greatest(sum(cts*to_number("+fapriPrp+")*fctr),1)*100/greatest(sum(cts*to_number("+fapriPrp+")),1), 2) colVlu_PCT \n" + 
            "from gt_srch_rslt gt,(select stk_idn,\n" + 
            "CASE pkt_ty\n" + 
            "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
            "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
            "END fctr\n" + 
            "from gt_srch_rslt) gtCol\n" + 
            "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 \n" + 
            "group by "+memoPrp+" ,"+deptPrp+" ,"+deptSrt+" ,"+shPrp+" ,"+shSrt+" ,\n" + 
            "Decode(Pkt_Ty,'NR',"+szPrp+","+mixszPrp+") , Decode(Pkt_Ty,'NR',"+szSrt+","+mixszSrt+")\n" + 
            "Order By Dept_So,Sh_So,Sz_So";

                ArrayList outLst = db.execSqlLst("memo by Shape", memoQ, new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                data=new HashMap();   
                String deptsrt=util.nvl((String)rs.getString("dept_so"));
                String shapesrt=util.nvl((String)rs.getString("sh_so"));
                String key=deptsrt+"_"+shapesrt;
                if(!keytable.contains(key)){
                    keytable.add(key);
                    conatinsdata=new ArrayList();       
                }
                if(!deptLst.contains(deptsrt)){
                    deptLst.add(deptsrt);      
                }
                data.put("DEPT",util.nvl((String)rs.getString("dept")));
                data.put("SH",util.nvl((String)rs.getString("sh")));
                data.put("SZ",util.nvl((String)rs.getString("sz")));
                data.put("QTY",util.nvl((String)rs.getString("qty")));
                data.put("CTS",util.nvl((String)rs.getString("cts")));
                data.put("AVG",util.nvl((String)rs.getString("avg")));
                data.put("FAAVG",util.nvl((String)rs.getString("fa_avg")));
                data.put("COLPCT",util.nvl((String)rs.getString("colVlu_PCT")));
                data.put("MFGAVG",util.nvl((String)rs.getString("mfg_avg")));
                conatinsdata.add(data);
                dataDtl.put(key,conatinsdata);       
            }
                rs.close(); pst.close();
            String shapeQ="select "+deptPrp+" dept,"+deptSrt+" dept_so,"+shPrp+" sh,"+shSrt+" sh_so,\n" + 
            "count(*) qty, to_char(sum(cts),'99999990.00') cts,\n" + 
            "to_char(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts),1)/sum(cts),2),'99999990.00') mfg_avg,\n"+
            "decode(min(to_number(nvl("+fapriPrp+",0))), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2),'99999990.00')) fa_avg,to_char(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2),'99999990.00') avg,trunc(greatest(sum(cts*to_number("+fapriPrp+")*fctr),1)*100/greatest(sum(cts*to_number("+fapriPrp+")),1), 2) colVlu_PCT \n" + 
            "from gt_srch_rslt gt,(select stk_idn,\n" + 
            "CASE pkt_ty\n" + 
            "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
            "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
            "END fctr\n" + 
            "from gt_srch_rslt) gtCol\n" + 
            "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 \n" + 
            "group by "+deptPrp+" ,"+deptSrt+" ,"+shPrp+" ,"+shSrt+" \n" + 
            "Order By Dept_So,Sh_So";

                outLst = db.execSqlLst("Sum by shape", shapeQ, new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
            while(rs.next()){ 
                data=new HashMap();   
                String deptsrt=util.nvl((String)rs.getString("dept_so"));
                String shapesrt=util.nvl((String)rs.getString("sh_so"));
                String key=deptsrt+"_"+shapesrt;
                data.put("DEPT",util.nvl((String)rs.getString("dept")));
                data.put("SH",util.nvl((String)rs.getString("sh")));
                data.put("QTY",util.nvl((String)rs.getString("qty")));
                data.put("CTS",util.nvl((String)rs.getString("cts")));
                data.put("AVG",util.nvl((String)rs.getString("avg")));
                data.put("FAAVG",util.nvl((String)rs.getString("fa_avg")));
                data.put("COLPCT",util.nvl((String)rs.getString("colVlu_PCT")));
                data.put("MFGAVG",util.nvl((String)rs.getString("mfg_avg")));
                datattl.put(key,data);  
            }
                rs.close(); pst.close();
            String deptQ=" select "+deptPrp+" dept,"+deptSrt+" dept_so,\n" + 
            "count(*) qty,to_char(sum(cts),'99999990.00') cts,\n" +
            "to_char(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts),1)/sum(cts),2),'99999990.00') mfg_avg,\n"+
            "decode(min(to_number(nvl("+fapriPrp+",0))), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2),'99999990.00')) fa_avg,to_char(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2),'99999990.00') avg,trunc(greatest(sum(cts*to_number("+fapriPrp+")*fctr),1)*100/greatest(sum(cts*to_number("+fapriPrp+")),1), 2) colVlu_PCT \n" + 
            "From Gt_Srch_Rslt gt,(select stk_idn,\n" + 
            "CASE pkt_ty\n" + 
            "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
            "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
            "END fctr\n" + 
            "from gt_srch_rslt) gtCol\n" + 
            "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 \n" + 
            "GROUP BY "+deptPrp+", "+deptSrt+"\n" + 
            "Order By Dept_So";

                outLst = db.execSqlLst("Sum by Dept", deptQ, new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                data=new HashMap();
                String deptsrt=util.nvl((String)rs.getString("dept_so"));
                data.put("DEPT",util.nvl((String)rs.getString("dept")));
                data.put("QTY",util.nvl((String)rs.getString("qty")));
                data.put("CTS",util.nvl((String)rs.getString("cts")));
                data.put("AVG",util.nvl((String)rs.getString("avg")));
                data.put("FAAVG",util.nvl((String)rs.getString("fa_avg")));
                data.put("COLPCT",util.nvl((String)rs.getString("colVlu_PCT")));
                data.put("MFGAVG",util.nvl((String)rs.getString("mfg_avg")));
                dataDtl.put(deptsrt,data);    
            }
                rs.close(); pst.close();
                
                String shpdeptttQ=" select "+deptPrp+" dept,"+deptSrt+" dept_so,\n" + 
                "count(*) qty,to_char(sum(cts),'99999990.00') cts,\n" + 
                "to_char(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts),1)/sum(cts),2),'99999990.00') mfg_avg,\n" + 
                "decode(min(to_number(nvl("+fapriPrp+",0))), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2),'99999990.00')) fa_avg,to_char(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2),'99999990.00') avg,trunc(greatest(sum(cts*to_number("+fapriPrp+")*fctr),1)*100/greatest(sum(cts*to_number("+fapriPrp+")),1), 2) colVlu_PCT \n" + 
                "From Gt_Srch_Rslt gt,(select stk_idn,\n" + 
                "CASE pkt_ty\n" + 
                "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
                "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
                "END fctr\n" + 
                "from gt_srch_rslt) gtCol\n" + 
                "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 and "+shPrp+" not  in('ROUND','RD')\n" + 
                "GROUP BY "+deptPrp+", "+deptSrt+"\n" + 
                "Order By Dept_So ";

                outLst = db.execSqlLst("Sum by Shape Dept", shpdeptttQ, new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                    data=new HashMap();
                    String deptsrt=util.nvl((String)rs.getString("dept_so"));
                    data.put("DEPT",util.nvl((String)rs.getString("dept")));
                    data.put("QTY",util.nvl((String)rs.getString("qty")));
                    data.put("CTS",util.nvl((String)rs.getString("cts")));
                    data.put("AVG",util.nvl((String)rs.getString("avg")));
                    data.put("FAAVG",util.nvl((String)rs.getString("fa_avg")));
                    data.put("COLPCT",util.nvl((String)rs.getString("colVlu_PCT")));
                    data.put("MFGAVG",util.nvl((String)rs.getString("mfg_avg")));
                    dataDtl.put(deptsrt+"_TTL",data);    
                }
                    rs.close(); pst.close();
            String grandQ="select \n" + 
            "count(*) qty, to_char(sum(cts),'99999990.00') cts,\n" +
            "to_char(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts),1)/sum(cts),2),'99999990.00') mfg_avg,\n"+
            "decode(min(to_number(nvl("+fapriPrp+",0))), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2),'99999990.00')) fa_avg,to_char(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2),'99999990.00') avg,trunc(greatest(sum(cts*to_number("+fapriPrp+")*fctr),1)*100/greatest(sum(cts*to_number("+fapriPrp+")),1), 2) colVlu_PCT \n" + 
            "From Gt_Srch_Rslt gt,(select stk_idn,\n" + 
            "CASE pkt_ty\n" + 
            "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
            "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
            "END fctr\n" + 
            "from gt_srch_rslt) gtCol\n" + 
            "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 ";

                outLst = db.execSqlLst("Grand Totals", grandQ, new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
            while(rs.next()){ 
                data=new HashMap();
                data.put("QTY",util.nvl((String)rs.getString("qty")));
                data.put("CTS",util.nvl((String)rs.getString("cts")));
                data.put("AVG",util.nvl((String)rs.getString("avg")));
                data.put("FAAVG",util.nvl((String)rs.getString("fa_avg")));
                data.put("COLPCT",util.nvl((String)rs.getString("colVlu_PCT")));
                data.put("MFGAVG",util.nvl((String)rs.getString("mfg_avg")));
                String compDteQ="select comp_dte,acc_av,avg_50,idn,ketan_av from mlot where dsc='"+memoIdn+"'";

                ArrayList outLst1 = db.execSqlLst("Comp Dte", compDteQ,  new ArrayList());
                PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
                ResultSet rs1 = (ResultSet)outLst1.get(1);
                if(rs1.next()){
                    mlotidn=util.nvl((String)rs1.getString("idn"));
                    data.put("IDN",mlotidn);
                    data.put("CMPDTE",util.nvl((String)rs1.getString("comp_dte")));  
                    udf.setValue("ACC_"+mlotidn, util.nvl((String)rs1.getString("acc_av")));  
                    udf.setValue("AVG_"+mlotidn, util.nvl((String)rs1.getString("avg_50")));  
                    udf.setValue("KET_"+mlotidn, util.nvl((String)rs1.getString("ketan_av")));  
                }
                rs1.close(); pst1.close();
                String facQ="select distinct "+facPrp+" factory from gt_srch_rslt where "+facPrp+" is not null";

                ArrayList outLst2 = db.execSqlLst("Factory", facQ,  new ArrayList());
                PreparedStatement pst2 = (PreparedStatement)outLst2.get(0);
                ResultSet rs2 = (ResultSet)outLst2.get(1);
                if(rs2.next()){
                    data.put("FAC",util.nvl((String)rs2.getString("factory")));    
                }
                rs2.close(); pst2.close();
                data.put("MEMO",memoIdn);   
                dataDtl.put("GRANDTTL",data);  
            }
                rs.close(); pst.close();
                String mlotdeptQ="select acc_av,avg_50,dept,ketan_av from mlot_dept where idn='"+mlotidn+"'";
                
                outLst = db.execSqlLst("mlotdeptQ", mlotdeptQ, new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                  String dept=util.nvl((String)rs.getString("dept"));
                  String deptsrt=(String)prpSrtDept.get(prpValDept.indexOf(dept));
                  udf.setValue("ACC_"+mlotidn+"_"+deptsrt, util.nvl((String)rs.getString("acc_av")));  
                  udf.setValue("AVG_"+mlotidn+"_"+deptsrt, util.nvl((String)rs.getString("avg_50"))); 
                  udf.setValue("KET_"+mlotidn+"_"+deptsrt, util.nvl((String)rs.getString("ketan_av")));  
                }
                rs.close(); pst.close();
            session.setAttribute("dataDtl", dataDtl);
            session.setAttribute("datattl", datattl);
            session.setAttribute("keytable", keytable);
            session.setAttribute("deptLst", deptLst);
            req.setAttribute("view", "Y");
            req.setAttribute("ketan", ketan);
            }
                util.updAccessLog(req,res,"Report Action", "memohkPop end");
            return am.findForward("memohkPop");
            }
        }
    public ActionForward mlotrpt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Report Action", "mlotrpt start");
        ReportForm udf = (ReportForm)af;
        GenericInterface genericInt = new GenericImpl();
        String pkttyp=util.nvl(req.getParameter("PKT_TYP"));
        int ct = db.execUpd("gtUpdate","update gt_srch_rslt set prp_001 = null", new ArrayList());
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ArrayList ary = new ArrayList();
        ary.add("MLOT_RPT");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        HashMap dbinfo = info.getDmbsInfoLst();
        String colorval = (String)dbinfo.get("COL");
        String clrval = (String)dbinfo.get("CLR");
        String mixclr = "MIX_CLARITY";
        String mixsz = "MIX_SIZE";
        ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "MLOT_RPTREPORT","MLOT_RPT");
        String rowval=mixclr;
        String colval=mixsz;
        String conQ=" where PKT_TY in('MIX','MX') and nvl(cts,0) > 0 ";
        String reportNme="Mix Size Clarity Report";
        String hdr="Mix Clarity/Mix Size";
        if(pkttyp.equals("NR")){
            rowval=colorval; 
            colval=clrval;
            conQ=" where PKT_TY in('NR') and nvl(cts,0) > 0 ";
            reportNme="Color Clarity Report";
            hdr="Color/Clarity";
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_SUMMARY");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("STOCK_SUMMARY");
            allPageDtl.put("STOCK_SUMMARY",pageDtl);
            }
            info.setPageDetails(allPageDtl);
        }else{
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("MIX_STOCK_SUMMARY");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("MIX_STOCK_SUMMARY");
            allPageDtl.put("MIX_STOCK_SUMMARY",pageDtl);
            }
            info.setPageDetails(allPageDtl);
        }
        int indexrow = vWPrpList.indexOf(rowval)+1;
        int indexcol = vWPrpList.indexOf(colval)+1;
        String rowPrp =  util.prpsrtcolumn("prp",indexrow);
        String colPrp = util.prpsrtcolumn("prp",indexcol);
        String rowSrt = util.prpsrtcolumn("srt",indexrow);
        String colSrt = util.prpsrtcolumn("srt",indexcol);
        ResultSet rs = null;
        HashMap dataTbl=new HashMap();
        ArrayList rowLst= new ArrayList();
        ArrayList colList= new ArrayList();
        String dataQ="SELECT SUM(QTY) QTY,TO_CHAR(TRUNC(SUM(TRUNC(CTS, 2)),2),'99999990.00') CTS,TRUNC(SUM(QUOT*TRUNC(CTS,2)),0) VLU,\n" + 
        "trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),0) AVG,trunc(((sum(quot*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2) avg_dis,"+rowPrp+","+rowSrt+","+colPrp+","+colSrt+"\n" + 
        "FROM GT_SRCH_RSLT \n" + conQ+
        "GROUP BY "+rowPrp+","+rowSrt+","+colPrp+","+colSrt;

            ArrayList outLst = db.execSqlLst("Data dtl", dataQ, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String row=util.nvl(rs.getString(rowPrp));
            String col=util.nvl(rs.getString(colPrp));
            String key=row+"_"+col;
            dataTbl.put(key+"_QTY",util.nvl(rs.getString("QTY")));
            dataTbl.put(key+"_CTS",util.nvl(rs.getString("CTS")));
            dataTbl.put(key+"_VLU",util.nvl(rs.getString("VLU")));
            dataTbl.put(key+"_AVG",util.nvl(rs.getString("AVG")));
            dataTbl.put(key+"_RAP",util.nvl(rs.getString("avg_dis")));
        }
            rs.close(); pst.close();
        String rowQ="SELECT SUM(QTY) QTY,TO_CHAR(TRUNC(SUM(TRUNC(CTS, 2)),2),'99999990.00') CTS,TRUNC(SUM(QUOT*TRUNC(CTS,2)),0) VLU,\n" + 
        "trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),0) AVG,trunc(((sum(quot*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2) avg_dis,"+rowPrp+","+rowSrt+"\n" + 
        "FROM GT_SRCH_RSLT \n" + conQ+
        "GROUP BY "+rowPrp+","+rowSrt+" order by "+rowSrt;

            outLst = db.execSqlLst("Row Dtl", rowQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String row=util.nvl(rs.getString(rowPrp));
            rowLst.add(row);
            dataTbl.put(row+"_QTY",util.nvl(rs.getString("QTY")));
            dataTbl.put(row+"_CTS",util.nvl(rs.getString("CTS")));
            dataTbl.put(row+"_VLU",util.nvl(rs.getString("VLU")));
            dataTbl.put(row+"_AVG",util.nvl(rs.getString("AVG")));
            dataTbl.put(row+"_RAP",util.nvl(rs.getString("avg_dis")));
        }
            rs.close(); pst.close();
        String colQ="SELECT SUM(QTY) QTY,TO_CHAR(TRUNC(SUM(TRUNC(CTS, 2)),2),'99999990.00') CTS,TRUNC(SUM(QUOT*TRUNC(CTS,2)),0) VLU,\n" + 
        "trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),0) AVG,trunc(((sum(quot*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2) avg_dis,"+colPrp+","+colSrt+"\n" + 
        "FROM GT_SRCH_RSLT \n" + conQ+
        "GROUP BY "+colPrp+","+colSrt+" order by "+colSrt;

            outLst = db.execSqlLst("Col Dtl", colQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String col=util.nvl(rs.getString(colPrp));
            colList.add(col);
            dataTbl.put(col+"_QTY",util.nvl(rs.getString("QTY")));
            dataTbl.put(col+"_CTS",util.nvl(rs.getString("CTS")));
            dataTbl.put(col+"_VLU",util.nvl(rs.getString("VLU")));
            dataTbl.put(col+"_AVG",util.nvl(rs.getString("AVG")));
            dataTbl.put(col+"_RAP",util.nvl(rs.getString("avg_dis")));
        }
            rs.close(); pst.close();
        String ttlQ="SELECT SUM(QTY) QTY,TO_CHAR(TRUNC(SUM(TRUNC(CTS, 2)),2),'99999990.00') CTS,TRUNC(SUM(QUOT*TRUNC(CTS,2)),0) VLU,\n" + 
        "trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),0) AVG,trunc(((sum(quot*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2) avg_dis " + 
        "FROM GT_SRCH_RSLT \n" + conQ;

            outLst = db.execSqlLst("ttl Dtl", ttlQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            dataTbl.put("TTL_QTY",util.nvl(rs.getString("QTY")));
            dataTbl.put("TTL_CTS",util.nvl(rs.getString("CTS")));
            dataTbl.put("TTL_VLU",util.nvl(rs.getString("VLU")));
            dataTbl.put("TTL_AVG",util.nvl(rs.getString("AVG")));
            dataTbl.put("TTL_RAP",util.nvl(rs.getString("avg_dis")));
        }
            rs.close(); pst.close();
        if(dataTbl!=null && dataTbl.size()>0){
            req.setAttribute("dataDtl", dataTbl);
            req.setAttribute("rowList", rowLst);
            req.setAttribute("colList", colList);
            req.setAttribute("hdr", hdr);
            req.setAttribute("pkttyp", pkttyp);
            udf.setReportNme(reportNme);
        }
        req.setAttribute("view", "Y");
            util.updAccessLog(req,res,"Report Action", "mlotrpt end");
        return am.findForward("loadrpt");
        }
    }

    
    public ActionForward memohkPKTDTL(ActionMapping am, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Report Action", "memohkPKTDTL start");
            GenericInterface genericInt = new GenericImpl();
        ArrayList pktList = new ArrayList();
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "MEMOLOT_HKREPORT","MEMO_LOTPKT_VW");
        ArrayList itemHdr = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        itemHdr.add("Sr");
        itemHdr.add("VNM");
        int ct = db.execUpd("gtUpdate","update gt_srch_rslt set prp_001 = null", new ArrayList());
        int indexDEPT = vwPrpLst.indexOf("DEPT")+1;
        String deptPrp = util.prpsrtcolumn("prp",indexDEPT);
        String deptSrt = util.prpsrtcolumn("srt",indexDEPT);
            
        String pktPrp = "srch_pkg.pop_pkt_prp(pMdl => ?)";
        ArrayList ary = new ArrayList();
        ary.add("MEMO_LOTPKT_VW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        String updatedeptQ="    update gt_srch_rslt gt set gt."+deptPrp+"='96-UP-GIA',gt."+deptSrt+"=25 where gt.pkt_ty='SMX' and gt.cts>=0.96 \n" + 
        "    and exists (select 1\n" + 
        "    from mstk a,iss_rtn_dtl b,iss_rtn c,mprc d\n" + 
        "    where \n" + 
        "    gt.stk_idn=a.idn and a.idn=b.iss_stk_idn and b.iss_id=c.iss_id and c.prc_id=d.idn and d.idn=644)";
        ct = db.execUpd("Update", updatedeptQ, new ArrayList());
            
        updatedeptQ="    update gt_srch_rslt gt set gt."+deptPrp+"='18-96-GIA',gt."+deptSrt+"=15 where gt.pkt_ty='SMX' and gt.cts<0.96 \n" + 
        "    and exists (select 1\n" + 
        "    from mstk a,iss_rtn_dtl b,iss_rtn c,mprc d\n" + 
        "    where \n" + 
        "    gt.stk_idn=a.idn and a.idn=b.iss_stk_idn and b.iss_id=c.iss_id and c.prc_id=d.idn and d.idn=644)";
        ct = db.execUpd("Update", updatedeptQ, new ArrayList());
        
        String cpPrp = "prte";
        if(vwPrpLst.contains("CP"))
        cpPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("CP")+1);
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt ,dsp_stt, qty , cts , rmk,prte,mrte, to_char(cts*nvl(nvl("+cpPrp+",prte),0),999999999990.99) cptotal ";
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

            srchQ += ", " + fld;
            if(prpDspBlocked.contains(lprp)){
            }else{
            itemHdr.add(lprp);
                if(lprp.equals("SAL_RTE")){
                    itemHdr.add("P1");
                    itemHdr.add("P2");
                }
            if(lprp.equals("CP")){
                itemHdr.add("CPVLU");
            }
        }}
        itemHdr.add("STATUS");
        
        int sr=1;
        String rsltQ = srchQ + " from gt_srch_rslt where cts <> 0 order by sk1 , cts ";
        

            ArrayList outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {

                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("Sr", String.valueOf(sr++));
                pktPrpMap.put("STATUS", util.nvl(rs.getString("dsp_stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.put("VNM",vnm);
                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("P1",util.nvl(rs.getString("prte")));
                pktPrpMap.put("P2",util.nvl(rs.getString("mrte")));
                pktPrpMap.put("CPVLU",util.nvl(rs.getString("cptotal")));
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
                              
                    pktList.add(pktPrpMap);
                }
            rs.close(); pst.close();
            rsltQ = "select to_char(sum(cts),999990.99) cts, to_char(sum(cts*nvl(nvl("+cpPrp+",prte),0)),999999999990.99) cpVlu from gt_srch_rslt";

            outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while(rs.next()) {
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("CRTWT", util.nvl(rs.getString("cts")));
                pktPrpMap.put("CPVLU", util.nvl(rs.getString("cpVlu")));
                pktList.add(pktPrpMap);
            }
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        
        session.setAttribute("pktList", pktList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Report Action", "memohkPKTDTL end");
        return am.findForward("pktDtl");
        }
    }
    
    public ActionForward createPDFMEMOHK(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Report Action", "createPDFMEMOHK start");
    PdfforReport pdf=new PdfforReport();
    HashMap dbSysInfo = info.getDmbsInfoLst();
    String docPath = (String)dbSysInfo.get("DOC_PATH");
    String FILE = "MEMO_LOT_"+util.getToDteTime()+".pdf";
    //String FILEPATH="c:/pdfana/"+FILE;
    String path = getServlet().getServletContext().getRealPath("/") + FILE;
    String FILEPATH= path+""+FILE;
    pdf.createPDFMEMOHK(req,FILEPATH);
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
            util.updAccessLog(req,res,"Report Action", "createPDFMEMOHK end");
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
    public ActionForward savemlotDept(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Report Action", "savemlotDept start");
      String mlotidn=util.nvl((String)req.getParameter("mlotidn"));
      String srtDept=util.nvl((String)req.getParameter("srtDept"));
      String val=util.nvl((String)req.getParameter("val"));
      String coulmn=util.nvl((String)req.getParameter("coulmn"));
        ArrayList ary = new ArrayList();
      if(!srtDept.equals("")){
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        ArrayList prpPrtDept=null;
        ArrayList prpValDept=null;
        ArrayList prpSrtDept = null;
        prpPrtDept = (ArrayList)prp.get("DEPT"+"P");
        prpSrtDept = (ArrayList)prp.get("DEPT"+"S");
        prpValDept= (ArrayList)prp.get("DEPT"+"V");
        String dept=(String)prpValDept.get(prpSrtDept.indexOf(srtDept));
        String updQ="update mlot_dept set "+coulmn+"=?,dte=sysdate where idn=? and dept=?";
        String insQ="INSERT INTO mlot_dept ("+coulmn+",idn,dept) VALUES (?,?,?)";
        ary = new ArrayList();
        ary.add(val);
        ary.add(mlotidn);
        ary.add(dept);
        int ct = db.execUpd("Update", updQ, ary);
        System.out.println(ct);
        if(ct<1){
        ct = db.execUpd("Insert", insQ, ary);
        System.out.println(ct);
        }
      }else{
          String updQ="update mlot set "+coulmn+"=? where idn=?"; 
          ary = new ArrayList();
          ary.add(val);
          ary.add(mlotidn);
          int ct = db.execDirUpd("Update", updQ, ary);
          
      }
            util.updAccessLog(req,res,"Report Action", "savemlotDept end");
      return null;
        }
    }
    public ActionForward loadmemohkcomp(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
                util.updAccessLog(req,res,"Report Action", "loadmemohkcomp start");
            ReportForm udf = (ReportForm)form;
            udf.reset();
                util.updAccessLog(req,res,"Report Action", "loadmemohkcomp end");
            return am.findForward("memohkcomp");
            }
        }
    
    public ActionForward memohkcomp(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
                util.updAccessLog(req,res,"Report Action", "memohkcomp start");
            ReportForm udf = (ReportForm)form;
            GenericInterface genericInt = new GenericImpl();
            String memoIdn1=util.nvl((String)udf.getValue("memoIdn1")).trim();
            String memoIdn2=util.nvl((String)udf.getValue("memoIdn2")).trim();
            HashMap dataDtl=new HashMap();
            HashMap datattl=new HashMap();
            HashMap data=new HashMap();
            ArrayList keytable=new ArrayList();
            ArrayList memoidLst=new ArrayList();
            String pkeyval="";
            ArrayList shLst=new ArrayList();   
            ArrayList szLst=new ArrayList();  
            if(!memoIdn1.equals(""))
                memoidLst.add(memoIdn1);
            if(!memoIdn2.equals(""))
                memoidLst.add(memoIdn2);
            if(memoidLst!=null && memoidLst.size()>0){
                HashMap dbinfo = info.getDmbsInfoLst();
                String sh = (String)dbinfo.get("SHAPE");
                String szval = (String)dbinfo.get("SIZE");
                String memo = util.nvl((String)dbinfo.get("MEMO"));
                ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "MEMO_HKREPORT","MEMO_LOT_VW");
                int indexSH = vWPrpList.indexOf(sh)+1;
                int indexSZ = vWPrpList.indexOf(szval)+1;
                int indexdept = vWPrpList.indexOf("DEPT")+1;
                int indexMIXSZ = vWPrpList.indexOf("MIX_SIZE")+1;
                int indexfapri = vWPrpList.indexOf("FA_PRI")+1;
                int indexMemo = vWPrpList.indexOf(memo)+1;
                int indexFactory = vWPrpList.indexOf("FACTORY")+1;
                int indexAvg = vWPrpList.indexOf("AVG")+1;
                int indexmixclr = vWPrpList.indexOf("MIX_CLARITY")+1;
                int indexcol = vWPrpList.indexOf("COL")+1;
                int indexmfgpri= vWPrpList.indexOf("MFG_PRI")+1;
                String shPrp = util.prpsrtcolumn("prp",indexSH);
                String szPrp = util.prpsrtcolumn("prp",indexSZ);
                String deptPrp = util.prpsrtcolumn("prp",indexdept);
                String mixszPrp = util.prpsrtcolumn("prp",indexMIXSZ);
                String fapriPrp = util.prpsrtcolumn("prp",indexfapri);
                String memoPrp = util.prpsrtcolumn("prp",indexMemo);
                String facPrp = util.prpsrtcolumn("prp",indexFactory);
                String avgPrp = util.prpsrtcolumn("prp",indexAvg);
                String colmfgpri = util.prpsrtcolumn("prp",indexmfgpri);
                String shSrt = util.prpsrtcolumn("srt",indexSH);
                String szSrt = util.prpsrtcolumn("srt",indexSZ);
                String deptSrt = util.prpsrtcolumn("srt",indexdept);  
                String mixszSrt = util.prpsrtcolumn("srt",indexMIXSZ);
                String fapriSrt = util.prpsrtcolumn("srt",indexfapri);
                String facSrt= util.prpsrtcolumn("srt",indexFactory);
                String avgSrt= util.prpsrtcolumn("srt",indexAvg);
                String colSrt= util.prpsrtcolumn("srt",indexcol);
                String mixclrSrt= util.prpsrtcolumn("srt",indexmixclr);
                HashMap prp = info.getPrp();
                HashMap mprp = info.getMprp();
                ArrayList prpPrtDept=null;
                ArrayList prpValDept=null;
                ArrayList prpSrtDept = null;
                prpPrtDept = (ArrayList)prp.get("DEPT"+"P");
                prpSrtDept = (ArrayList)prp.get("DEPT"+"S");
                prpValDept= (ArrayList)prp.get("DEPT"+"V");
            ArrayList deptLst=new ArrayList(); 
                for(int i=0;i<memoidLst.size();i++){
                String memoIdn=(String) memoidLst.get(i);   
            String memoPRCQ="Pop_Memo_Rep_Data(?,?)";
            ArrayList ary=new ArrayList();
            ary.add(memoIdn);
            ary.add("MEMO_LOT_VW");
            int ct = db.execCall("memoPRCQ", memoPRCQ,ary);        
            String memoQ="select gt."+memoPrp+" memo,gt."+deptPrp+" dept,gt."+deptSrt+" dept_so,gt."+shPrp+" sh,gt."+shSrt+" sh_so,\n" + 
            "decode(gt.pkt_ty,'NR',upper(b.prt2),upper(gt."+mixszPrp+")) sz,\n" + 
            "to_char(trunc(greatest(sum(to_number(decode(gt.pkt_ty,'NR',gt."+colmfgpri+",gt.quot))*gt.cts),1)/sum(gt.cts),2),'99999990.00') mfg_avg,\n"+
            "count(distinct gt.stk_idn) qty, to_char(sum(gt.cts),'99999990.00') cts," + 
            "decode(min(to_number(gt."+fapriPrp+")), 0, 0, to_char(trunc(greatest(sum(to_number(gt."+fapriPrp+")*gt.cts),1)/sum(gt.cts),2),'99999990.00')) fa_avg,to_char(trunc(greatest(sum(to_number(gt."+avgPrp+")*gt.cts),1)/sum(gt.cts),2),'99999990.00') avg,trunc(greatest(sum(gt.cts*to_number(gt."+fapriPrp+")*fctr),1)*100/greatest(sum(gt.cts*to_number(gt."+fapriPrp+")),1), 2) colVlu_PCT \n" + 
            "from gt_srch_rslt gt,prp b,(select stk_idn,\n" + 
            "CASE pkt_ty\n" + 
            "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
            "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
            "END fctr\n" + 
            "from gt_srch_rslt) gtCol\n" + 
            "where gt.stk_idn = gtCol.stk_idn  and  nvl(gt.cts,0) > 0  and b.val in (upper(gt."+szPrp+"),upper(gt."+mixszPrp+")) and gt."+shPrp+"='ROUND' and b.mprp in('SIZE','MIX_SIZE') \n" + 
            "group by gt."+memoPrp+" ,gt."+deptPrp+" ,gt."+deptSrt+" ,gt."+shPrp+" ,gt."+shSrt+" ,\n" + 
            "Decode(gt.Pkt_Ty,'NR',upper(b.prt2),upper(gt."+mixszPrp+"))\n" + 
            "Order By Dept_So,Sh_So";
 
                ArrayList outLst = db.execSqlLst("memo by Shape", memoQ, new ArrayList()); 
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                data=new HashMap();   
                String deptsrt=util.nvl((String)rs.getString("dept_so"));
                String shapesrt=util.nvl((String)rs.getString("sh_so"));
                String sz=util.nvl((String)rs.getString("sz"));
                String key=memoIdn+"_"+deptsrt+"_"+shapesrt+"_"+sz;
                dataDtl.put(key+"_SH",util.nvl((String)rs.getString("sh")));
                dataDtl.put(key+"_SZ",util.nvl((String)rs.getString("sz")));
                dataDtl.put(key+"_QTY",util.nvl((String)rs.getString("qty")));
                dataDtl.put(key+"_CTS",util.nvl((String)rs.getString("cts")));
                dataDtl.put(key+"_AVG",util.nvl((String)rs.getString("avg")));
                dataDtl.put(key+"_FAAVG",util.nvl((String)rs.getString("fa_avg")));    
                dataDtl.put(key+"_COLPCT",util.nvl((String)rs.getString("colVlu_PCT")));
                dataDtl.put(key+"_MFGAVG",util.nvl((String)rs.getString("mfg_avg")));
            }
                rs.close(); pst.close();  
            String shapeQ="select "+deptPrp+" dept,"+deptSrt+" dept_so,"+shPrp+" sh,"+shSrt+" sh_so,\n" + 
            "count(*) qty, to_char(sum(cts),'99999990.00') cts,\n" + 
            "to_char(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts),1)/sum(cts),2),'99999990.00') mfg_avg,\n"+
            "decode(min(to_number("+fapriPrp+")), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2),'99999990.00')) fa_avg,to_char(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2),'99999990.00') avg,trunc(greatest(sum(cts*to_number("+fapriPrp+")*fctr),1)*100/greatest(sum(cts*to_number("+fapriPrp+")),1), 2) colVlu_PCT \n" + 
            "from gt_srch_rslt gt,(select stk_idn,\n" + 
            "CASE pkt_ty\n" + 
            "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
            "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
            "END fctr\n" + 
            "from gt_srch_rslt) gtCol\n" + 
            "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 \n" + 
            "group by "+deptPrp+" ,"+deptSrt+" ,"+shPrp+" ,"+shSrt+" \n" + 
            "Order By Dept_So,Sh_So";

                outLst = db.execSqlLst("Sum by shape", shapeQ, new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
            while(rs.next()){   
                String deptsrt=util.nvl((String)rs.getString("dept_so"));
                String shapesrt=util.nvl((String)rs.getString("sh_so"));
                String key=memoIdn+"_"+deptsrt+"_"+shapesrt;
                datattl.put(key+"_DEPT",util.nvl((String)rs.getString("dept")));
                datattl.put(key+"_SH",util.nvl((String)rs.getString("sh")));
                datattl.put(key+"_QTY",util.nvl((String)rs.getString("qty")));
                datattl.put(key+"_CTS",util.nvl((String)rs.getString("cts")));
                datattl.put(key+"_AVG",util.nvl((String)rs.getString("avg")));
                datattl.put(key+"_FAAVG",util.nvl((String)rs.getString("fa_avg")));
                datattl.put(key+"_COLPCT",util.nvl((String)rs.getString("colVlu_PCT")));
                dataDtl.put(key+"_MFGAVG",util.nvl((String)rs.getString("mfg_avg")));
                if(!keytable.contains(key)){
                    keytable.add(key);     
                }
                if(!deptLst.contains(deptsrt)){
                    deptLst.add(deptsrt);      
                }
            }
                rs.close(); pst.close();         
            String deptQ=" select "+deptPrp+" dept,"+deptSrt+" dept_so,\n" + 
            "count(*) qty,to_char(sum(cts),'99999990.00') cts,\n" +
            "to_char(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts),1)/sum(cts),2),'99999990.00') mfg_avg,\n"+
            "decode(min(to_number("+fapriPrp+")), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2),'99999990.00')) fa_avg,to_char(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2),'99999990.00') avg,trunc(greatest(sum(cts*to_number("+fapriPrp+")*fctr),1)*100/greatest(sum(cts*to_number("+fapriPrp+")),1), 2) colVlu_PCT \n" + 
            "From Gt_Srch_Rslt gt,(select stk_idn,\n" + 
            "CASE pkt_ty\n" + 
            "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
            "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
            "END fctr\n" + 
            "from gt_srch_rslt) gtCol\n" + 
            "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 \n" + 
            "GROUP BY "+deptPrp+", "+deptSrt+"\n" + 
            "Order By Dept_So";

                outLst = db.execSqlLst("Sum by Dept", deptQ, new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                data=new HashMap();
                String deptsrt=memoIdn+"_"+util.nvl((String)rs.getString("dept_so"));
                data.put("DEPT",util.nvl((String)rs.getString("dept")));
                data.put("QTY",util.nvl((String)rs.getString("qty")));
                data.put("CTS",util.nvl((String)rs.getString("cts")));
                data.put("AVG",util.nvl((String)rs.getString("avg")));
                data.put("FAAVG",util.nvl((String)rs.getString("fa_avg")));
                data.put("COLPCT",util.nvl((String)rs.getString("colVlu_PCT")));
                data.put("MFGAVG",util.nvl((String)rs.getString("mfg_avg")));
                dataDtl.put(deptsrt,data);    
            }
                rs.close(); pst.close();
            String grandQ="select \n" + 
            "count(*) qty, to_char(sum(cts),'99999990.00') cts,\n" + 
            "to_char(trunc(greatest(sum(to_number(decode(pkt_ty,'NR',"+colmfgpri+",quot))*cts),1)/sum(cts),2),'99999990.00') mfg_avg,\n"+
            "decode(min(to_number("+fapriPrp+")), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2),'99999990.00')) fa_avg,to_char(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2),'99999990.00') avg,trunc(greatest(sum(cts*to_number("+fapriPrp+")*fctr),1)*100/greatest(sum(cts*to_number("+fapriPrp+")),1), 2) colVlu_PCT \n" + 
            "From Gt_Srch_Rslt gt,(select stk_idn,\n" + 
            "CASE pkt_ty\n" + 
            "WHEN 'NR' Then CASE WHEN "+colSrt+" >= 95 THEN 1 ELSE 0 END\n" + 
            "ELSE CASE WHEN "+mixclrSrt+" Between 520 and 770 Then 1 ELSE 0 END\n" + 
            "END fctr\n" + 
            "from gt_srch_rslt) gtCol\n" + 
            "where gt.stk_idn = gtCol.stk_idn  and nvl(cts,0) > 0 ";

                outLst = db.execSqlLst("Grand Totals", grandQ, new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
            while(rs.next()){ 
                data=new HashMap();
                data.put("QTY",util.nvl((String)rs.getString("qty")));
                data.put("CTS",util.nvl((String)rs.getString("cts")));
                data.put("AVG",util.nvl((String)rs.getString("avg")));
                data.put("FAAVG",util.nvl((String)rs.getString("fa_avg")));
                data.put("COLPCT",util.nvl((String)rs.getString("colVlu_PCT")));
                data.put("MFGAVG",util.nvl((String)rs.getString("mfg_avg")));
                String compDteQ="select comp_dte,acc_av,avg_50,idn from mlot where dsc='"+memoIdn+"'";

                ArrayList outLst1 = db.execSqlLst("Comp Dte", compDteQ,  new ArrayList());
                PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
                ResultSet rs1 = (ResultSet)outLst1.get(1);
                if(rs1.next()){
                    String mlotidn=util.nvl((String)rs1.getString("idn"));
                    data.put("IDN",mlotidn);
                    data.put("CMPDTE",util.nvl((String)rs1.getString("comp_dte")));  
                    udf.setValue("ACC_"+mlotidn, util.nvl((String)rs1.getString("acc_av")));  
                    udf.setValue("AVG_"+mlotidn, util.nvl((String)rs1.getString("avg_50")));  
                }
                rs1.close(); pst1.close();
                String facQ="select distinct "+facPrp+" factory from gt_srch_rslt where "+facPrp+" is not null";

                ArrayList outLst2 = db.execSqlLst("Factory", facQ,  new ArrayList());
                PreparedStatement pst2 = (PreparedStatement)outLst2.get(0);
                ResultSet rs2 = (ResultSet)outLst2.get(1);
                if(rs2.next()){
                    data.put("FAC",util.nvl((String)rs2.getString("factory")));    
                }
                rs2.close(); pst2.close();
                data.put("MEMO",memoIdn);   
                dataDtl.put(memoIdn+"_"+"GRANDTTL",data);  
            }
                rs.close(); pst.close();
                String mlotdeptQ="select a.acc_av,a.avg_50,a.dept,b.idn from mlot_dept a,mlot b where a.idn=b.idn and b.dsc='"+memoIdn+"'";

                outLst = db.execSqlLst("mlotdeptQ", mlotdeptQ, new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                  String mlotidn=util.nvl((String)rs.getString("idn"));
                  data.put("IDN",mlotidn);  
                  String dept=util.nvl((String)rs.getString("dept"));
                  String deptsrt=(String)prpSrtDept.get(prpValDept.indexOf(dept));
                  udf.setValue("ACC_"+mlotidn+"_"+deptsrt, util.nvl((String)rs.getString("acc_av")));  
                  udf.setValue("AVG_"+mlotidn+"_"+deptsrt, util.nvl((String)rs.getString("avg_50")));  
                }
                rs.close(); pst.close();
                                shLst = new ArrayList();
                                szLst = new ArrayList();
                                String orderQ="select "+memoPrp+" memo,"+deptPrp+" dept,"+deptSrt+" dept_so,"+shPrp+" sh,"+shSrt+" sh_so,\n" + 
                                "decode(pkt_ty,'NR',upper(b.prt2),upper("+mixszPrp+")) sz,\n" + 
                                "count(distinct stk_idn) qty, to_char(sum(cts),'99999990.00') cts," + 
                                "decode(min(to_number("+fapriPrp+")), 0, 0, to_char(trunc(greatest(sum(to_number("+fapriPrp+")*cts),1)/sum(cts),2),'99999990.00')) fa_avg,to_char(trunc(greatest(sum(to_number("+avgPrp+")*cts),1)/sum(cts),2),'99999990.00') avg \n" + 
                                "from gt_srch_rslt,prp b  where nvl(cts,0) > 0  and b.val in (upper("+szPrp+"),upper("+mixszPrp+")) and b.mprp in('SIZE','MIX_SIZE') \n" + 
                                "group by "+memoPrp+" ,"+deptPrp+" ,"+deptSrt+" ,"+shPrp+" ,"+shSrt+" ,\n" + 
                                "Decode(Pkt_Ty,'NR',upper(b.prt2),upper("+mixszPrp+"))\n" + 
                                "Order By Dept_So,Sh_So";

                outLst = db.execSqlLst("orderQ", orderQ, new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
                                while(rs.next()){
                                    String memoval=util.nvl(rs.getString("memo")); 
                                    String deptsrtval=util.nvl(rs.getString("dept_so"));
                                    String shsrt=util.nvl((String)rs.getString("sh_so"));
                                    String sz=util.nvl((String)rs.getString("sz"));
                                    String key=memoval+"_"+deptsrtval;
                                        if(pkeyval.equals(""))
                                        pkeyval = key;
                                        if(!pkeyval.equals(key)){
                                        dataDtl.put(pkeyval+"_SH", shLst);
                                        dataDtl.put(pkeyval+"_SZ", szLst);
                                        shLst = new ArrayList();
                                        szLst = new ArrayList();
                                        pkeyval = key;
                                        }
                                        if(!shLst.contains(shsrt))
                                            shLst.add(shsrt);  
                                        if(!szLst.contains(sz))
                                            szLst.add(sz);   
                                            
                                    }
                                    rs.close(); pst.close();
                                    if(!pkeyval.equals("")){
                                        dataDtl.put(pkeyval+"_SH", shLst);
                                        dataDtl.put(pkeyval+"_SZ", szLst);
                                    }
                    
            }
            dataDtl.put("DEPT",deptLst);
            session.setAttribute("dataDtl", dataDtl);
            session.setAttribute("datattl", datattl);
            session.setAttribute("keytable", keytable);
            session.setAttribute("memoidLst", memoidLst); 
            req.setAttribute("view", "Y");
    }
                util.updAccessLog(req,res,"Report Action", "memohkcomp end");
            return am.findForward("memohkcomp");
            }
        }
    
    
    

    public ActionForward createKacchaXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Report Action", "createKacchaXL start");
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "KacchaReport"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        HSSFWorkbook hwb = null;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        hwb = xlUtil.getDataKacchaInXl(req);
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Report Action", "createKacchaXL end");
        return null;
        }
    }
    
    public ActionForward ketanXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Report Action", "ketanXL start");
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "ketanXL"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList params = new ArrayList();
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        HashMap pktDtl = new HashMap();
        HashMap prp = info.getPrp();
        ArrayList prpValDept=null;
        prpValDept= (ArrayList)prp.get("DEPT"+"V");
        ArrayList memoList = (ArrayList)session.getAttribute("memoList");
        String idnPcs = memoList.toString();
        idnPcs = idnPcs.replaceAll("\\[", "");
        idnPcs = idnPcs.replaceAll("\\]", "").replaceAll(" ", "");
        String pmemoIdn = "";
        String ttlcts="0";
        int ct =db.execUpd(" Del Old Pkts ", "Delete from gt_pkt_scan", new ArrayList());
        params.add(idnPcs);
        String insScanPkt = " insert into gt_pkt_scan select * from TABLE(PARSE_TO_TBL(?))";
        ct = db.execUpd(" ins scan", insScanPkt,params);
        
        String sqlQ="select gt.vnm memo, dept.val dept\n" + 
        "--, sum(decode(greatest(instr(dept.val, 'GIA'),0), 0, m.cts, sd.num)) cts\n" + 
        ", to_char(sum(sd.num),'99999990.00') cts, lot.acc_av acc_av, lot.ketan_av lot_avg, ldept.ketan_av dept_avg\n" + 
        "from gt_pkt_scan gt, mstk m, stk_dtl memo, stk_dtl sd, stk_dtl dept, mlot lot, mlot_dept ldept\n" + 
        "where 1 = 1 and m.stt not like '%SUS%' and m.stt not like '%MRG%' and m.stt <> 'PCHK'\n" + 
        "and m.idn = memo.mstk_idn and memo.grp = 1 and memo.mprp = 'MEMO' and memo.txt = gt.vnm\n" + 
        "and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val in ('18-96-GIA', '96-UP-GIA')\n" + 
        "and m.idn = sd.mstk_idn and sd.grp = 1 and sd.mprp = 'MFG_CTS'\n" + 
        "and lot.dsc = gt.vnm and lot.idn = ldept.idn and ldept.dept = dept.val\n" + 
        "and not exists (select 1 from stk_dtl ms where m.idn = ms.mstk_idn and ms.grp = 1 and ms.mprp = 'MIX_SNGL' and ms.val = 'Y')\n"+
        "group by gt.vnm, dept.val, lot.acc_av, lot.ketan_av, ldept.ketan_av\n" + 
        "UNION\n" + 
        "select gt.vnm memo, dept.val dept\n" + 
        "--, sum(decode(greatest(instr(dept.val, 'GIA'),0), 0, m.cts, sd.num)) cts\n" + 
        ", to_char(sum(m.cts),'99999990.00') cts, lot.acc_av acc_av, lot.ketan_av lot_avg, ldept.ketan_av dept_avg\n" + 
        "from gt_pkt_scan gt, mstk m, stk_dtl memo, stk_dtl dept, mlot lot, mlot_dept ldept\n" + 
        "where 1 = 1  and m.stt not like '%SUS%' and m.stt not in ('AS_MRG', 'POST_MRG') and m.stt <> 'PCHK'\n" + 
        "and m.idn = memo.mstk_idn and memo.grp = 1 and memo.mprp = 'MEMO' and memo.txt = gt.vnm\n" + 
        "and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val not in ('18-96-GIA', '96-UP-GIA')\n" + 
        "and lot.dsc = gt.vnm and lot.idn = ldept.idn and ldept.dept = dept.val\n" + 
        "and not exists (select 1 from stk_dtl ms where m.idn = ms.mstk_idn and ms.grp = 1 and ms.mprp = 'MIX_SNGL' and ms.val = 'Y')\n"+
        "group by gt.vnm, dept.val, lot.acc_av, lot.ketan_av, ldept.ketan_av order by 1";

            ArrayList outLst = db.execSqlLst("sqlQ", sqlQ, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String memoIdn = util.nvl(rs.getString("memo"));
            String dept = util.nvl(rs.getString("dept")); 
            String cts = util.nvl2(rs.getString("cts"),"0");
            if(pmemoIdn.equals(""))
                pmemoIdn = memoIdn;
            if(!pmemoIdn.equals(memoIdn)){
                pktDtlList.add(pktDtl);
                pktDtl = new HashMap();
                ttlcts="0";
                pmemoIdn = memoIdn;
            }
            ttlcts=String.valueOf(Double.parseDouble(ttlcts)+Double.parseDouble(cts));
            pktDtl.put("MEMO", pmemoIdn);
            pktDtl.put(dept+"Crts", cts);
            pktDtl.put(dept+"Avg", util.nvl(rs.getString("dept_avg")));
            pktDtl.put("FRemarks", "F="+util.nvl(rs.getString("acc_av")));
            pktDtl.put("TotAvg", util.nvl(rs.getString("lot_avg")));
            pktDtl.put("GTotCrts", ttlcts);
        }
            rs.close(); pst.close();
        if(!pmemoIdn.equals("")){
            pktDtlList.add(pktDtl);
        }
        itemHdr.add("MEMO");
        for(int i=1;i<prpValDept.size();i++){
        itemHdr.add(prpValDept.get(i)+"Crts");  
        itemHdr.add(prpValDept.get(i)+"Avg"); 
        }
        itemHdr.add("GTotCrts");
        itemHdr.add("TotAvg");
        itemHdr.add("FRemarks");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktDtlList);
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Report Action", "ketanXL end");
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
            util.updAccessLog(req,res,"Report Action", "createXL start");
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "StockSummeryReport"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Report Action", "createXL end");
        return null;
        }
    }
    
    
    
    
    public ActionForward loadmfgGrading(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Report Action", "loadmfgGrading start");
        ReportForm udf = (ReportForm)form;
        GenericInterface genericInt = new GenericImpl();
        udf.resetALL();
            ResultSet rs =null;
            ArrayList sttList = new ArrayList();
            String stkStt = "select distinct grp1 , decode(grp1, 'MKT','Marketing','LAB','Lab','ASRT','Assort','SOLD','Sold', grp1) dsc " +
                            " from df_stk_stt where flg='A' and vld_dte is null and grp1 is not null order by dsc ";
            

            ArrayList outLst = db.execSqlLst("stkStt", stkStt, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                ArrayList sttDtl = new ArrayList();
                sttDtl.add(rs.getString("grp1"));
                sttDtl.add(rs.getString("dsc"));
                sttList.add(sttDtl);
            }
            rs.close(); pst.close();
            session.setAttribute("sttLst",sttList);
            ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_mfgGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_mfgGNCSrch");
        info.setGncPrpLst(assortSrchList);
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("GRIDING_DTATYP");  
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("GRIDING_DTATYP");
            allPageDtl.put("GRIDING_DTATYP",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Report Action", "loadmfgGrading end");
      return am.findForward("loadmfgGrading");
        }
    }
    
    public ActionForward mlotAsstDtl(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            HashMap assortDtl = new HashMap();
            util.updAccessLog(req,res,"Report Action", "memohk start");
            String dept = util.nvl(req.getParameter("dept"));
            String memo = util.nvl(req.getParameter("memo"));
            double ttlCts = 0;
            double ttlCpvlu=0;
            double ttlCp=0;
            String asMrgSql="select sh.val sh, sz.val sz, to_char(sum(m.cts),999990.99) cts, to_char(sum(m.cts*cp.num),999999999990.99) cpVlu, to_char(sum(m.cts*cp.num)/sum(m.cts),999999990.99) cp\n" + 
            "  from mstk m, stk_dtl dept, stk_dtl memo, stk_dtl cp, stk_dtl sh, stk_dtl sz\n" + 
            "where 1 = 1 and m.pkt_ty = 'MIX' and m.stt = 'AS_MRG'\n" + 
            "  and m.idn = cp.mstk_idn and cp.grp = 1 and cp.mprp = 'CP' \n" + 
            "  and m.idn = sh.mstk_idn and sh.grp = 1 and sh.mprp = 'SHAPE' \n" + 
            "  and m.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_SIZE' \n" + 
            "  and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '"+dept+"' \n" + 
            "  and m.idn = memo.mstk_idn and memo.grp = 1 and memo.mprp = 'MEMO' and memo.txt = '"+memo+"' \n" + 
            "group by sh.val, sz.val, sh.srt, sz.srt\n" + 
            "order by sh.srt, sz.srt";
            ArrayList ary = new ArrayList();
           
            ArrayList pktList = new ArrayList();
            ArrayList outLst = db.execSqlLst("asMrgSql", asMrgSql, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                HashMap pktDtl = new HashMap();
                pktDtl.put("sh", util.nvl(rs.getString("sh")));
                pktDtl.put("sz", util.nvl(rs.getString("sz")));
                pktDtl.put("cts", util.nvl(rs.getString("cts")));
                pktDtl.put("cpVlu", util.nvl(rs.getString("cpVlu")));
                pktDtl.put("cp", util.nvl(rs.getString("cp")));
                pktList.add(pktDtl);
                double Cts= rs.getDouble("cts");
                double Cpvlu= rs.getDouble("cpVlu");
                ttlCts=ttlCts+Cts;
                ttlCpvlu=ttlCpvlu+Cpvlu;
                
            }
            ttlCp=ttlCpvlu/ttlCts;
            
            rs.close(); pst.close();

            assortDtl.put("MFGLIST", pktList);
            assortDtl.put("TTLCTS",util.roundToDecimals(ttlCts,2));
            assortDtl.put("TTLCP", util.roundToDecimals(ttlCp,2));
            assortDtl.put("TTLCPVLU", util.roundToDecimals(ttlCpvlu,2));
            
            double mettlCts = 0;
            double mettlCpvlu=0;
            double mettlCp=0;
            String memoMrgSql="select sh.val sh, sz.val sz, clr.val clr,  to_char(sum(m.cts),999990.99) cts,  to_char(sum(m.cts*cp.num),999999990.99) cpVlu,  decode(sum(m.cts), 0, 0,to_char( sum(m.cts*cp.num)/sum(m.cts),9999999990.99)) cp\n" + 
            "  from mstk m, stk_dtl dept, stk_dtl memo, stk_dtl cp, stk_dtl sh, stk_dtl sz, stk_dtl clr\n" + 
            "where 1 = 1 and m.pkt_ty = 'MIX' and m.stt = 'MEMO_MRG'\n" + 
            "  and m.idn = cp.mstk_idn and cp.grp = 1 and cp.mprp = 'CP' \n" + 
            "  and m.idn = sh.mstk_idn and sh.grp = 1 and sh.mprp = 'SHAPE' \n" + 
            "  and m.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_SIZE' \n" + 
            "  and m.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY' \n" + 
            "  and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '"+dept+"' \n" + 
            "  and m.idn = memo.mstk_idn and memo.grp = 1 and memo.mprp = 'MEMO' and memo.txt = '"+memo+"' \n" + 
            "  and m.cts > 0\n" + 
            "group by sh.val, sz.val, sh.srt, sz.srt, clr.val, clr.srt\n" + 
            "order by sh.srt, sz.srt, clr.srt";
            
            pktList = new ArrayList();
            outLst = db.execSqlLst("memoMrgSql", memoMrgSql, ary);
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                HashMap pktDtl = new HashMap();
                pktDtl.put("sh", util.nvl(rs.getString("sh")));
                pktDtl.put("sz", util.nvl(rs.getString("sz")));
                pktDtl.put("cts", util.nvl(rs.getString("cts")));
                pktDtl.put("clr", util.nvl(rs.getString("clr")));
                pktDtl.put("cpVlu", util.nvl(rs.getString("cpVlu")));
                pktDtl.put("cp", util.nvl(rs.getString("cp")));
                pktList.add(pktDtl);
                double Cts= rs.getDouble("cts");
                double Cpvlu= rs.getDouble("cpVlu");
                mettlCts=mettlCts+Cts;
                mettlCpvlu=mettlCpvlu+Cpvlu;
                
            }
            mettlCp=mettlCpvlu/mettlCts;
            
            rs.close(); pst.close();

            
            assortDtl.put("FNLLIST", pktList);
            assortDtl.put("FNLTTLCTS", util.roundToDecimals(mettlCts,2));
            assortDtl.put("FNLTTLCP", util.roundToDecimals(mettlCp,2));
            assortDtl.put("FNLTTLCPVLU",util.roundToDecimals( mettlCpvlu,2));
            
            req.setAttribute("ASSORTDTL", assortDtl);
            return am.findForward("assortDtl");   
        }
    }
    
    
    public ActionForward fetchmfgGrading(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Report Action", "fetchmfgGrading start");
        ReportForm udf = (ReportForm)form;
        GenericInterface genericInt = new GenericImpl();
        String vnm = util.nvl((String)udf.getValue("vnm"));
        String srchTyp =util.nvl((String)udf.getValue("srchRef"));
        HashMap dataDtl=new HashMap();
        ArrayList stockList=new ArrayList();
        
        packetData(req, res , vnm , udf, srchTyp);
        
        String prc = "report_pkg.MFG_IN_DATA(pMdl => ?)";
                ArrayList ary = new ArrayList();
                ary.add("GRAD_MFG_VW");
                int ct = db.execCall("Grading Mfg", prc, ary);
                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("GRIDING_DTATYP");        

               ArrayList pageList=new ArrayList();
               HashMap pageDtlMap=new HashMap();
               String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
              ArrayList rptTypList = new ArrayList();
               if(pageDtl==null)
               pageDtl = new HashMap();
               pageList=(ArrayList)pageDtl.get("DTA_TYP");
            
                if(pageList!=null && pageList.size() >0){
                 for(int j=0;j<pageList.size();j++){
                      pageDtlMap = (HashMap)pageList.get(j) ;
                       dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
                    String[] split_attrNme = dflt_val.split(",");
                    for(int i=0;i< split_attrNme.length;i++) {
                            rptTypList.add(split_attrNme[i]);
                        }
                      
                }
                } else{  
                   
                 
                  rptTypList.add("MFG");
                  rptTypList.add("FA");
                  rptTypList.add("LAB");
                }
            
                  ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "mfgViewLst","GRAD_MFG_VW");
                  HashMap dbinfo = info.getDmbsInfoLst();
                  String sh = (String)dbinfo.get("SHAPE");
                  int indexSH = vWPrpList.indexOf(sh)+1;
                  String shPrp = util.prpsrtcolumn("prp", indexSH);
                  int indexPDTE = vWPrpList.indexOf("PKTDATE")+1;
                  String pdatePrp = util.prpsrtcolumn("prp",indexPDTE);
                  String sqlQry = " select sk1,cert_lab,vnm , max(decode(stt,'LAB',cts,null)) cts , max(decode(stt,'LAB',status,null)) status , max(decode(stt,'LAB',shape,null)) shape,max(decode(stt,'FA',emp,null)) emp,max(decode(stt,'MFG',pktdate,null)) pktdate , max(decode(stt,'LAB',status,null)) status ";
                  
                   for(int i=0;i<rptTypList.size();i++){
                          String ltyp = (String)rptTypList.get(i);
                        for(int j=0;j<vWPrpList.size();j++){
                            String lprp = (String)vWPrpList.get(j);
                            if(lprp.indexOf("&A") > -1)
                                lprp=lprp.replaceAll("\\&A","A");
                            if(lprp.indexOf("-S") > -1)
                                lprp=lprp.replaceAll("\\-S","S");
                            String lprpNme = ltyp+"_"+lprp;
                            sqlQry = sqlQry+" , max(decode(stt,'"+ltyp+"',"+lprpNme+",null)) "+lprpNme ;
                        }
                    }
                   
                   String labSql = " select a.sk1,a.cert_lab,a.vnm,a.cts,a."+shPrp+" shape,null emp,null pktdate, 'LAB' stt, a.stt status ";
                    for (int i = 0; i < vWPrpList.size(); i++) {
                        String lprp = (String)vWPrpList.get(i);
                        String fld = "a.prp_";
                        int j = i + 1;
                        if (j < 10)
                            fld += "00" + j;
                        else if (j < 100)
                            fld += "0" + j;
                        else if (j > 100)
                            fld += j;

                         if(lprp.indexOf("&A") > -1)
                             lprp=lprp.replaceAll("\\&A","A");
                         if(lprp.indexOf("-S") > -1)
                             lprp=lprp.replaceAll("\\-S","S");
                        labSql+= " , " + fld+" LAB_"+lprp;
                       
                     }
                     ArrayList crtTypList = new ArrayList();
                     crtTypList.add("MFG");
                     crtTypList.add("FA");
                     labSql = labSql+" "+queryStr(vWPrpList,  crtTypList)+" from  gt_srch_rslt a ";
                     
                    String mfgSql = " select a.sk1,a.cert_lab,a.vnm, null cts, null shape,null emp,b."+pdatePrp+" pktdate, b.stt, null status ";
                    crtTypList = new ArrayList();
                    crtTypList.add("LAB");
                    mfgSql = mfgSql+" "+queryStr(vWPrpList,  crtTypList);
                     for (int i = 0; i < vWPrpList.size(); i++) {
                         String lprp = (String)vWPrpList.get(i);
                         String fld = "b.prp_";
                         int j = i + 1;
                         if (j < 10)
                             fld += "00" + j;
                         else if (j < 100)
                             fld += "0" + j;
                         else if (j > 100)
                             fld += j;

                          if(lprp.indexOf("&A") > -1)
                              lprp=lprp.replaceAll("\\&A","A");
                          if(lprp.indexOf("-S") > -1)
                              lprp=lprp.replaceAll("\\-S","S");
                         mfgSql+= " , " +fld+" MFG_"+lprp;
                        
                      }
                    crtTypList = new ArrayList();
                     crtTypList.add("FA");
                    mfgSql = mfgSql+" "+queryStr(vWPrpList,  crtTypList)+" from  gt_srch_rslt a , gt_srch_multi b " + 
                    "  where a.stk_idn = b.stk_idn and b.stt='MFG' ";
                      
                      
                    String faSql = " select a.sk1,a.cert_lab,a.vnm, null cts, null shape,byr.get_nm(nvl(b.rln_idn,0)) emp,null pktdate, b.stt, null status ";
                    
                    crtTypList = new ArrayList();
                    crtTypList.add("LAB");
                    crtTypList.add("MFG");
                    faSql = faSql+" "+queryStr(vWPrpList,  crtTypList);
                    
                     for (int i = 0; i < vWPrpList.size(); i++) {
                         String lprp = (String)vWPrpList.get(i);
                         String fld = "b.prp_";
                         int j = i + 1;
                         if (j < 10)
                             fld += "00" + j;
                         else if (j < 100)
                             fld += "0" + j;
                         else if (j > 100)
                             fld += j;

                          if(lprp.indexOf("&A") > -1)
                              lprp=lprp.replaceAll("\\&A","A");
                          if(lprp.indexOf("-S") > -1)
                              lprp=lprp.replaceAll("\\-S","S");
                         faSql+= " , " + fld+" FA_"+lprp;
                        
                      }
                      
                      faSql = faSql+" from  gt_srch_rslt a, gt_srch_multi b " +
                               " where a.stk_idn = b.stk_idn  and b.stt='FA'";
                      
                    sqlQry = sqlQry+" from ("+labSql+" union "+mfgSql+" union "+faSql+") Group By Vnm,cert_lab,sk1 order by sk1";
                    
                    
        //            String sql="select vnm,max(decode(stt,'LAB',cts,null)) cts, max(decode(stt,'LAB',shape,null)) shape,        \n" + 
        //            "       max(decode(stt,'MFG',mfg_col,null)) mfg_col, max(decode(stt,'MFG',mfg_clr,null)) mfg_clr ,max(decode(stt,'MFG',mfg_cut,null)) mfg_cut ,\n" + 
        //            "       max(decode(stt,'MFG',mfg_pol,null)) mfg_pol ,max(decode(stt,'MFG',mfg_sym,null)) mfg_sym ,max(decode(stt,'MFG',mfg_flo,null)) mfg_flo ,\n" + 
        //            "       max(decode(stt,'MFG',mfg_rte,null)) mfg_rte,\n" + 
        //            "       max(decode(stt,'FA',fa_col,null)) fa_col, max(decode(stt,'FA',fa_clr,null)) fa_clr ,max(decode(stt,'FA',fa_cut,null)) fa_cut ,\n" + 
        //            "       max(decode(stt,'FA',fa_pol,null)) fa_pol ,max(decode(stt,'FA',fa_sym,null)) fa_sym ,max(decode(stt,'FA',fa_flo,null)) fa_flo ,\n" + 
        //            "       max(decode(stt,'FA',fa_rte,null)) fa_rte,\n" + 
        //            "       max(decode(stt,'LAB',lab_col,null)) lab_col, max(decode(stt,'LAB',lab_clr,null)) lab_clr ,max(decode(stt,'LAB',lab_cut,null)) lab_cut ,\n" + 
        //            "       max(decode(stt,'LAB',lab_pol,null)) lab_pol ,max(decode(stt,'LAB',lab_sym,null)) lab_sym ,max(decode(stt,'LAB',lab_flo,null)) lab_flo ,\n" + 
        //            "       max(decode(stt,'LAB',lab_rte,null)) lab_rte, max(decode(stt,'LAB',lab,null)) lab, max(decode(stt,'LAB',status,null)) status\n" + 
        //            "from(\n" + 
        //            "select a.vnm,a.cts,a."+shPrp+" shape, 'LAB' stt, a.stt status,\n" + 
        //            "       a.prp_005 lab_col,a.prp_006 lab_clr,a.prp_007 lab_cut,a.prp_007 lab_pol,a.prp_009 lab_sym,a.prp_010 lab_flo,a.prp_012 lab_rte,a.prp_011 lab,\n" + 
        //            "       null mfg_col , null mfg_clr,null mfg_cut,null mfg_pol,null mfg_sym,null mfg_flo,null mfg_rte,\n" + 
        //            "       null fa_col , null fa_clr,null fa_cut,null fa_pol,null fa_sym,null fa_flo,null fa_rte        \n" + 
        //            "from  gt_srch_rslt a\n" + 
        //            "union\n" + 
        //            "select a.vnm,null cts, null shape, b.stt, null status,\n" + 
        //            "null lab_col , null lab_clr,null lab_cut,null lab_pol,null lab_sym,null lab_flo,null lab_rte,null lab,\n" + 
        //            "b.prp_005 mfg_col,b.prp_006 mfg_clr,b.prp_007 mfg_cut,b.prp_007 mfg_pol,b.prp_009 mfg_sym,b.prp_010 mfg_flo,b.prp_013 mfg_rte\n" + 
        //            ",null fa_col , null fa_clr,null fa_cut,null fa_pol,null fa_sym,null fa_flo,null fa_rte        \n" + 
        //            "from  gt_srch_rslt a, gt_srch_multi b\n" + 
        //            "where a.stk_idn = b.stk_idn\n" + 
        //            "and b.stt = 'MFG'\n" + 
        //            "union\n" + 
        //            "select a.vnm,null cts, null shape, b.stt, null status,\n" + 
        //            "null lab_col , null lab_clr,null lab_cut,null lab_pol,null lab_sym,null lab_flo,null lab_rte, null lab,\n" + 
        //            "null mfg_col , null mfg_clr,null mfg_cut,null mfg_pol,null mfg_sym,null mfg_flo,null mfg_rte,\n" + 
        //            "b.prp_005 fa_col,b.prp_006 fa_clr,b.prp_007 fa_cut,b.prp_008 fa_pol,b.prp_009 fa_sym,b.prp_010 fa_flo,b.prp_012 fa_rte        \n" + 
        //            "from  gt_srch_rslt a, gt_srch_multi b\n" + 
        //            "where a.stk_idn = b.stk_idn\n" + 
        //            "and b.stt = 'FA')\n" + 
        //            "Group By Vnm\n";

            ArrayList outLst = db.execSqlLst("sql", sqlQry, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
                    while(rs.next()){
                        dataDtl=new HashMap();
                        dataDtl.put("vnm",util.nvl(rs.getString("vnm")));
                        dataDtl.put("cts",util.nvl(rs.getString("cts")));
                        dataDtl.put("shape",util.nvl(rs.getString("shape")));
                        dataDtl.put("pktdate",util.nvl(rs.getString("pktdate")));
                        dataDtl.put("emp",util.nvl(rs.getString("emp")));
                        dataDtl.put("status",util.nvl(rs.getString("status")));
                        dataDtl.put("cert_lab",util.nvl(rs.getString("cert_lab")));
                        for(int i=0;i<rptTypList.size();i++){
                               String ltyp = (String)rptTypList.get(i);
                             for(int j=0;j<vWPrpList.size();j++){
                                 String lprp = (String)vWPrpList.get(j);
                                 if(lprp.indexOf("&A") > -1)
                                 lprp=lprp.replaceAll("\\&A","A");
                                 if(lprp.indexOf("-S") > -1)
                                 lprp=lprp.replaceAll("\\-S","S");
                                 String lprpNme = ltyp+"_"+lprp;
                                 dataDtl.put(lprpNme,util.nvl(rs.getString(lprpNme)));
                                 }
                        }
                        stockList.add(dataDtl);
                    }
        rs.close(); pst.close();
        req.setAttribute("view", "Y");
        req.setAttribute("rptTypList", rptTypList);
        session.setAttribute("stockList", stockList);
            util.updAccessLog(req,res,"Report Action", "fetchmfgGrading end");
      return am.findForward("loadmfgGrading");
        }
    }
    
    public void packetData(HttpServletRequest req, HttpServletResponse res, String vnm , ReportForm form, String srchTyp) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        int ct=0;
        HashMap dbinfo = info.getDmbsInfoLst();
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        String delQ = " Delete from gt_srch_rslt ";
        ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        ArrayList sttList = (session.getAttribute("sttLst") == null)?new ArrayList():(ArrayList)session.getAttribute("sttLst");
       ArrayList selectedSttList = new ArrayList();
        for(int n=0;n<sttList.size();n++){
        ArrayList sttDtl = (ArrayList)sttList.get(n);
         String  stt = util.nvl((String)form.getValue((String)sttDtl.get(0)));
            if(!stt.equals("")) {
                selectedSttList.add(stt); 
            }
        }
      if(!vnm.equals(""))
      vnm = util.getVnm(vnm);
     
         if(!vnm.equals("")){
        String[] vnmLst = vnm.split(",");
        int loopCnt = 1 ;
        System.out.println(vnmLst.length);
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
               
               toLoc = Math.min(vnm.lastIndexOf(lookupVnm) + lookupVnm.length(), vnm.length());
               String vnmSub = vnm.substring(fromLoc, toLoc);
      
          String srchStr = "";
            
          if(srchTyp.equals("vnm"))
              srchStr = " and ( b.vnm in ("+vnmSub+") or b.tfl3 in ("+vnmSub+") ) ";
          else if(srchTyp.equals("cert_no"))
              srchStr = " and b.cert_no in ("+vnmSub+")";
      
      if(!srchStr.equals("")){
      String srchRefQ = 
      "    Insert into gt_srch_rslt (stk_idn , vnm  , flg , qty , cts , stt ,  cmp, rap_rte , quot , rap_dis ,cert_lab, sk1 , pkt_ty ) " + 
      " select  b.idn, b.vnm,  'Z' , qty , cts , stt ,nvl(cmp,upr) , rap_rte , nvl(upr,cmp) , decode(rap_rte ,'1',null,'0',null, trunc((nvl(upr,cmp)/greatest(rap_rte,1)*100)-100, 2)) ,cert_lab, sk1  "+
      "  ,pkt_ty   from mstk b where  1=1 and b.pkt_ty='NR' "+srchStr;
      
         
          // ary.add(vnm);
          
          ct = db.execUpd(" Srch Prp ", srchRefQ, new ArrayList());
        }
      }
      ArrayList ary = new ArrayList();
      String pktPrp = "pkgmkt.pktPrp(0,?)";
      ary = new ArrayList();
      ary.add("GRAD_MFG_VW");
      ct = db.execCall(" Srch Prp ", pktPrp, ary);
      }else{
          HashMap mprp = info.getMprp();
          HashMap prp = info.getPrp();
          ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_mfgGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_mfgGNCSrch");
          info.setGncPrpLst(genricSrchLst); 
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
                      String reqVal1 = util.nvl((String)form.getValue(lprp + "_" + lVal),"");
                      if(!reqVal1.equals("")){
                      paramsMap.put(lprp + "_" + lVal, reqVal1);
                      }
                         
                      }
                  }else{
                  String fldVal1 = util.nvl((String)form.getValue(lprp+"_1"));
                  String fldVal2 = util.nvl((String)form.getValue(lprp+"_2"));
                  if(typ.equals("T")){
                      fldVal1 = util.nvl((String)form.getValue(lprp+"_1"));
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
          paramsMap.put("stt", "ALL");
          paramsMap.put("mdl", "GRAD_MFG_VW");
          paramsMap.put("grpLst",selectedSttList);
          util.genericSrch(paramsMap);
      }
    }
    
    public void insertSrchAddon(int srchId,String grp,DBMgr db){
        String sttQry = "select stt from df_stk_stt where grp1=? and stt not like 'SUS%'  and flg='A' order by srt";
         ArrayList ary = new ArrayList();
         ary.add(grp);
        try {
            ArrayList outLst = db.execSqlLst("sttQry", sttQry, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String stt = rs.getString("stt");
                String insrtAddon = " insert into srch_addon( srch_id , cprp , cval) "+
                "select ? , ? , ?  from dual ";
                ary = new ArrayList();
                ary.add(String.valueOf(srchId));
                ary.add("STT");
                ary.add(stt);
                int cnt = db.execUpd("", insrtAddon, ary);
            }
            rs.close(); pst.close();

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    }
    
    public String queryStr(ArrayList vwPrpList , ArrayList typList){
        String str ="";
        for(int i=0 ; i < typList.size() ; i++){
            String lprpTyp = (String)typList.get(i);
            for(int j=0 ; j < vwPrpList.size() ; j++){
                String lprp = (String)vwPrpList.get(j);
                if(lprp.indexOf("&A") > -1)
                    lprp=lprp.replaceAll("\\&A","A");
                if(lprp.indexOf("-S") > -1)
                lprp=lprp.replaceAll("\\-S","S");
                str = str+" , null "+lprpTyp+"_"+lprp;
            }
        }
        return str;
    }
    
//    public ArrayList MFGGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("mfgGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'GRAD_MFG_SRCH' and flg <> 'N' order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close(); pst1.close();
//                session.setAttribute("mfgGNCSrch", asViewPrp);
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
    

    public ActionForward createGradingXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
                util.updAccessLog(req,res,"Report Action", "createGradingXL start");
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "GradingReport"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
            HSSFWorkbook hwb = null;
            ExcelUtil xlUtil = new ExcelUtil();
            xlUtil.init(db, util, info);
            hwb = xlUtil.getDatagradingInXl(req);
            OutputStream out = res.getOutputStream();
            hwb.write(out);
            out.flush();
            out.close();
                util.updAccessLog(req,res,"Report Action", "createGradingXL end");
            return null;
            }
        }
    public ActionForward createXLSP(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Report Action", "createXL start");
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "StockSummeryReport"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdrXL = (ArrayList)session.getAttribute("itemHdrXL");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdrXL, pktList);
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Report Action", "createXL end");
        return null;
        }
    }
    
    public ActionForward memoPnd(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Report Action", "memoPnd start");
            ReportForm udf = (ReportForm)af;
            GenericInterface genericInt = new GenericImpl();
            udf.resetALL();
            String pkt_typ=util.nvl(req.getParameter("PKT_TYP"));
            String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
            String dfNmeIdn=util.nvl((String)info.getDfNmeIdn()); 
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            ArrayList rolenmLst=(ArrayList)info.getRoleLst();
            String usrFlg=util.nvl((String)info.getUsrFlg());
            String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
            SearchQuery srchQuery = new SearchQuery();
            ArrayList itemHdr = new ArrayList();    
            ArrayList itemHdrXL = new ArrayList();   
            db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
            ArrayList vwPrpLst = genericInt.genericPrprVw(req,res,"SAL_PND_VW","SAL_PND_VW");
            String conQ="";
            String allowData="Y";
            ArrayList pktList = new  ArrayList();
                ArrayList prpDspBlocked = info.getPageblockList();
            ArrayList ary = new ArrayList();
                ArrayList pageList=new ArrayList();
                HashMap pageDtlMap=new HashMap();
                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("MEMOPENDING_REPORT");
                if(pageDtl==null || pageDtl.size()==0){
                pageDtl=new HashMap();
                pageDtl=util.pagedef("MEMOPENDING_REPORT");
                allPageDtl.put("MEMOPENDING_REPORT",pageDtl);
                }
                info.setPageDetails(allPageDtl);
                pageList= ((ArrayList)pageDtl.get("DFLT_DISPLAY") == null)?new ArrayList():(ArrayList)pageDtl.get("DFLT_DISPLAY");
                if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                allowData=(String)pageDtlMap.get("dflt_val");
                }
                }
            if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
            }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
            conQ =conQ+" and d.grp_nme_idn=?"; 
            ary.add(dfgrpnmeidn);
            }
              if(!cnt.equals("xljf")){
            if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                conQ += " and (d.emp_idn= ? or d.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
                ary.add(dfNmeIdn);
                ary.add(dfNmeIdn);
            }}
                pageList= ((ArrayList)pageDtl.get("DFLT_COM") == null)?new ArrayList():(ArrayList)pageDtl.get("DFLT_COM");               
              if(pageList!=null && pageList.size() >0){
              for(int j=0;j<pageList.size();j++){
              pageDtlMap=(HashMap)pageList.get(j);
              String dfltCon=(String)pageDtlMap.get("fld_nme");
                conQ = conQ+ " "+dfltCon ;
               
              }
              } 
                String srchRef = " Insert into gt_srch_multi (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts ,rap_rte,rap_dis,cert_no, sk1,quot,cmp,pair_id,rmk ) " + 
                                            " select c.pkt_ty , c.idn mstkIdn, c.vnm , c.dte , c.stt , 'Z' ,  b.qty , b.cts ,c.rap_rte,decode(c.rap_rte ,'1',null,'0',null, trunc((nvl(c.upr,c.cmp)/greatest(c.rap_rte,1)*100)-100, 2)),c.cert_no, c.sk1,nvl(c.upr,c.cmp),trunc(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte, 1), 2),d.emp_idn,a.rmk from mjan a , jandtl b , mstk c,mnme d " + 
                                            " where a.idn = b.idn  and b.mstk_idn = c.idn and a.nme_idn=d.nme_idn " + 
                                            "  and a.stt='IS' and b.stt = 'IS' and a.typ in ('E', 'I', 'WH','H') "+conQ ;
                           if(!pkt_typ.equals(""))
                               srchRef+=" and c.stt in('MKAV') and c.pkt_ty in('MX','MIX') "; 
                           else
                               srchRef+="  and c.stt in('MKIS','MKEI','MKWH') and c.pkt_ty in('NR','SMX') ";
           int ct = db.execUpd("insert gt",srchRef, ary);
            String pktPrp = "srch_pkg.POP_PKT_PRP_TEST(pMdl=>?,pTbl=>'GT_SRCH_MULTI')";
            ary = new ArrayList();
            ary.add("SAL_PND_VW");
            ct = db.execCall(" Srch Prp ", pktPrp, ary);
            
            ArrayList byrList = new ArrayList();
            ResultSet rs1      = null;
            String    getByr  = "select  distinct d.fnme byr,d.nme_idn from gt_srch_multi a , mjan b , jandtl c , mnme d " + 
                    " where a.stk_idn = c.mstk_idn and b.stt='IS' and c.stt='IS' and b.typ in ('E', 'I', 'WH','K','H','BID','O') and c.idn = b.idn and d.nme_idn = b.nme_idn and a.flg= ? " + 
                    " order by byr ";
            ary = new ArrayList();
            ary.add("Z");

                ArrayList outLst1 = db.execSqlLst("byr", getByr, ary);
                PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
                rs1 = (ResultSet)outLst1.get(1);
            while (rs1.next()) {
                ByrDao byr = new ByrDao();

                byr.setByrIdn(rs1.getInt("nme_idn"));
                byr.setByrNme(rs1.getString("byr"));
                byrList.add(byr);
            }
            rs1.close(); pst1.close();
            udf.setByrList(byrList);
            
            if(allowData.equals("Y")){
                itemHdr.add("Sale Person");    
                itemHdr.add("Buyer");
                itemHdr.add("Id");    
                itemHdr.add("Packet No");    
                itemHdr.add("Date");    
                itemHdr.add("Status");    
                itemHdr.add("Memo Type");    
                itemHdr.add("Trm");    
                itemHdr.add("Days");    
                itemHdr.add("Qty");    
                itemHdr.add("Prc / Crt");    
                itemHdr.add("Raprte");    
                itemHdr.add("RapVlu");    
                itemHdr.add("Rapdis");    
                itemHdr.add("Quot");    
                itemHdr.add("Amount");
                
                itemHdrXL.add("Sale Person");    
                itemHdrXL.add("Buyer");
                itemHdrXL.add("Date");    
                itemHdrXL.add("Days");
                itemHdrXL.add("Packet No"); 
            String xlconQ="";
            if(cnt.equals("xljf")){
                xlconQ=" ,get_net (c.idn, c.mstk_idn,'M') STONEWISE_DIS,ROUND ( (NVL (c.FNL_SAL, c.quot) * c.cts) * get_net (c.idn, c.mstk_idn,'M') / 100, 2 ) NET_DISC_AMT,to_char(trunc(a.cts*nvl(c.fnl_sal, c.quot)/nvl(b.exh_rte,1), 2),'9999999999990.00')-ROUND ( (NVL (c.FNL_SAL, c.quot) * c.cts) * get_net (c.idn, c.mstk_idn,'M') / 100, 2 ) grandttl ";
            }
            String rsltQ = " select d.fnme byr , byr.get_nm(nvl(d.emp_idn,0)) sal , b.idn , to_char(b.dte, 'dd-Mon-rrrr') dte,round(trunc(sysdate)-trunc(b.dte)) days ,a.stt, a.vnm ,a.qty, to_char(trunc(a.cts ,2),'999999990.00') cts,trunc(nvl(c.fnl_sal, c.quot)/nvl(b.exh_rte, 1), 2) memoQuot, a.quot rte,to_char(trunc(a.cts*nvl(c.fnl_sal, c.quot)/nvl(b.exh_rte,1), 2),'9999999999990.00') amt,decode(nvl(b.exh_rte,1),1,'','#00CC66') color,to_char(trunc(a.cts,2) * a.rap_rte, '99999999990.00') rapVlu,a.rmk "+
                " ,decode(a.rap_rte, 1, '', decode(least(a.rap_dis, 0),0, '+'||a.rap_dis, a.rap_dis))  r_dis,a.rap_rte,a.cert_no,b.typ,byr.get_trms(b.nmerel_idn) trm"+xlconQ;
            for (int i = 0; i < vwPrpLst.size(); i++) {
                String prpNme=util.nvl((String)vwPrpLst.get(i));
                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                    fld += "00" + j;
                else if (j < 100)
                    fld += "0" + j;
                else if (j > 100)
                    fld += j;

               

                rsltQ += ", " + fld;
                 if(prpDspBlocked.contains(prpNme)){
                 }else{
                 itemHdr.add(prpNme);
                 itemHdrXL.add(prpNme);
                 if(prpNme.equals("COL-SHADE")){
                 itemHdrXL.add("Raprte");
                 itemHdrXL.add("Rapdis");
                 itemHdrXL.add("Prc / Crt");
                 itemHdrXL.add("Amount");
                 }
                 }
             }
            rsltQ = rsltQ +" from gt_srch_multi a , mjan b , jandtl c , mnme d  " + 
                    " where a.stk_idn = c.mstk_idn and b.stt='IS' and c.stt='IS' and b.typ in ('E', 'I', 'WH','K','H','BID','O') and c.idn = b.idn and d.nme_idn = b.nme_idn and a.flg= ? " + 
                    " order by sal , byr ";
            ary = new ArrayList();
            ary.add("Z");

                ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                 while(rs.next()) {
                        HashMap pktPrpMap = new HashMap();
                        pktPrpMap.put("Buyer", util.nvl(rs.getString("byr")));
                        pktPrpMap.put("Sale Person",util.nvl(rs.getString("sal")));
                        pktPrpMap.put("Id",util.nvl(rs.getString("idn")));
                        pktPrpMap.put("Date",util.nvl(rs.getString("dte")));
                        pktPrpMap.put("Days",util.nvl(rs.getString("days")));
                        pktPrpMap.put("Qty",util.nvl(rs.getString("qty")));
                        pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                        pktPrpMap.put("Status",util.nvl(rs.getString("stt")));
                        pktPrpMap.put("Memo Type",util.nvl(rs.getString("typ")));
                        pktPrpMap.put("Quot",util.nvl(rs.getString("memoQuot")));
                        pktPrpMap.put("Prc / Crt",util.nvl(rs.getString("rte")));
                        pktPrpMap.put("Amount",util.nvl(rs.getString("amt")));
                        pktPrpMap.put("amtex",util.nvl(rs.getString("amt")));
                        pktPrpMap.put("Packet No",util.nvl(rs.getString("vnm")));
                        pktPrpMap.put("color",util.nvl(rs.getString("color")));
                        pktPrpMap.put("Rapdis",util.nvl(rs.getString("r_dis")));
                        pktPrpMap.put("Raprte",util.nvl(rs.getString("rap_rte")));
                        pktPrpMap.put("RapVlu", util.nvl(rs.getString("rapVlu")));
                        pktPrpMap.put("Trm",util.nvl(rs.getString("trm")));
                        pktPrpMap.put("Remark",util.nvl(rs.getString("rmk")));
                     if(cnt.equals("xljf")){
                     pktPrpMap.put("Net Dis",util.nvl(rs.getString("STONEWISE_DIS")));
                     pktPrpMap.put("Net Amt",util.nvl(rs.getString("NET_DISC_AMT")));
                     pktPrpMap.put("Grand Total",util.nvl(rs.getString("grandttl")));
                     }
                        for(int j=0; j < vwPrpLst.size(); j++){
                             String lprp = (String)vwPrpLst.get(j);
                              
                              String fld="prp_";
                              if(j < 9)
                                      fld="prp_00"+(j+1);
                              else    
                                      fld="prp_0"+(j+1);
                              
                            String val = util.nvl(rs.getString(fld)) ;
                            if (lprp.toUpperCase().equals("CRTWT"))
                                val = util.nvl(rs.getString("cts"));
                            if (lprp.toUpperCase().equals("RAP_DIS"))
                                val = util.nvl(rs.getString("r_dis"));
                            if (lprp.toUpperCase().equals("RAP_RTE"))
                                val = util.nvl(rs.getString("rap_rte"));
                            if(lprp.toUpperCase().equals("RTE"))
                                val = util.nvl(rs.getString("rte"));
                            if (lprp.toUpperCase().equals("CERT NO.") || lprp.toUpperCase().equals("CERT_NO")){
                            val = util.nvl(rs.getString("cert_no"));
                              lprp="CERT_NO";
                            }
                              
                              pktPrpMap.put(lprp, val);
                        }
                                      
                     pktList.add(pktPrpMap);
                 }
            rs.close(); pst.close();
                req.setAttribute("view", "Y");
            }
            udf.setPktList(pktList);
            udf.setReportNme("Memo Pending Report");
            ArrayList partyLst = srchQuery.getEmpList(req,res);
            udf.setEmpList(partyLst);
            req.setAttribute("allowData", allowData);
            session.setAttribute("pktList", pktList);
            session.setAttribute("itemHdr",itemHdr);
            session.setAttribute("itemHdrXL",itemHdrXL);
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_PND_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_PND_SRCH");info.setGncPrpLst(genricSrchLst);
            info.setGncPrpLst(genricSrchLst);  
            if(cnt.equals("hk"))
            util.groupcompany();    
                util.updAccessLog(req,res,"Report Action", "memoPnd end");
           return am.findForward("loadMemo");
            }
        }
    
    public ActionForward fetchmemoPnd(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        util.updAccessLog(req,res,"Report Action", "fetchmemoPnd start");
        ReportForm udf = (ReportForm)af;
        GenericInterface genericInt = new GenericImpl();
        String byr = util.nvl((String)udf.getByrIdn());
        String group = util.nvl((String)udf.getValue("group"));
        String appfrmDte = util.nvl((String)udf.getValue("ifrmDte"));
        String apptoDte = util.nvl((String)udf.getValue("itoDte"));
        String empIdn = util.nvl((String)udf.getValue("empIdn"));
        String type = util.nvl((String)udf.getValue("typ"));
        ArrayList vwPrpLst = genericInt.genericPrprVw(req,res,"SAL_PND_VW","SAL_PND_VW");
        ArrayList pktList = new  ArrayList();
        ArrayList itemHdr = new ArrayList();    
        ArrayList itemHdrXL = new ArrayList();    
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        ArrayList rolenmLst=(ArrayList)info.getRoleLst();
        String usrFlg=util.nvl((String)info.getUsrFlg());
        String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
        ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_PND_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_PND_SRCH");info.setGncPrpLst(genricSrchLst);
        ArrayList prpDspBlocked = info.getPageblockList();
        SearchQuery srchQuery = new SearchQuery();
        String conQ="",filterconQ="";
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        ArrayList ary = new ArrayList(),selectCollection= new ArrayList();
            for(int i=0;i<genricSrchLst.size();i++){
                ArrayList prplist =(ArrayList)genricSrchLst.get(i);
                String lprp = (String)prplist.get(0);
                String flg= (String)prplist.get(1);
                String typ = util.nvl((String)mprp.get(lprp+"T"));
                String prpSrt = lprp ;  
                ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                int indexprp = vwPrpLst.indexOf(lprp)+1;
                if(flg.equals("M")) {
                    selectCollection=new ArrayList();
                    for(int j=0; j < lprpS.size(); j++) {
                    String lSrt = (String)lprpS.get(j);
                    String lVal = (String)lprpV.get(j);    
                    String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                    if(!reqVal1.equals("")){
                    selectCollection.add(reqVal1);
                    }
                    }
                    if(selectCollection.size()>0){
                        String selprp = selectCollection.toString();
                        selprp = selprp.replaceAll("\\[", "");
                        selprp = selprp.replaceAll("\\]", "").replaceAll(" ", "");
                        selprp=util.getVal(selprp);
                        String colPrp =  util.prpsrtcolumn("prp",indexprp); 
                        filterconQ += " and a."+colPrp+" in("+selprp+") ";
                    }
                }else{
                String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                if(typ.equals("T")){
                    fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                    fldVal2 = fldVal1;
                }
                if(fldVal2.equals(""))
                fldVal2=fldVal1;
                if(!fldVal1.equals("") && !fldVal2.equals("")){
                String colSrt = util.prpsrtcolumn("srt",indexprp);
                filterconQ += " and a."+colSrt+" between '"+util.nvl((String)lprpS.get(lprpV.indexOf(fldVal1)))+"' and '"+util.nvl((String)lprpS.get(lprpV.indexOf(fldVal2)))+"' ";
                }
                }
            }    
            
        if(!group.equals("")){
        conQ =conQ+ " and d.grp_nme_idn= ? ";
        ary.add(group);
        }else{
        if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
        }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
        conQ =conQ+" and d.grp_nme_idn=?"; 
        ary.add(dfgrpnmeidn);
        }  
        }
        if (!empIdn.equals("0") && !empIdn.equals("")) {
                conQ += " and a.pair_id = ? ";
                ary.add(empIdn);
        }
            if(!type.equals("") && !type.equals("0")){
              conQ += " and b.typ= ? ";
              ary.add(type);
            }
            String xlconQ="";
            if(cnt.equals("xljf")){
                xlconQ=" ,get_net (c.idn, c.mstk_idn,'M') STONEWISE_DIS,ROUND ( (NVL (c.FNL_SAL, c.quot) * c.cts) * get_net (c.idn, c.mstk_idn,'M') / 100, 2 ) NET_DISC_AMT,to_char(trunc(a.cts*nvl(c.fnl_sal, c.quot)/nvl(b.exh_rte,1), 2),'9999999999990.00')-ROUND ( (NVL (c.FNL_SAL, c.quot) * c.cts) * get_net (c.idn, c.mstk_idn,'M') / 100, 2 ) grandttl ";
            }
            
            
                
            itemHdr.add("Sale Person");    
            itemHdr.add("Buyer");
            itemHdr.add("Id");    
            itemHdr.add("Packet No");    
            itemHdr.add("Date");    
            itemHdr.add("Status");    
            itemHdr.add("Memo Type");    
            itemHdr.add("Trm");    
            itemHdr.add("Days");    
            itemHdr.add("Qty");    
            itemHdr.add("Prc / Crt");    
            itemHdr.add("Raprte");    
            itemHdr.add("RapVlu");    
            itemHdr.add("Rapdis");    
            itemHdr.add("Quot");    
            itemHdr.add("Amount");
            
            
            itemHdrXL.add("Sale Person");    
            itemHdrXL.add("Buyer");
            itemHdrXL.add("Date");    
            itemHdrXL.add("Days");
            itemHdrXL.add("Packet No"); 
            

            
        String rsltQ = " select d.fnme byr , byr.get_nm(nvl(d.emp_idn,0)) sal , b.idn , to_char(b.dte, 'dd-Mon-rrrr') dte ,round(trunc(sysdate)-trunc(b.dte)) days , a.vnm ,a.stt,a.qty, to_char(trunc(a.cts ,2),'999999990.00') cts,nvl(c.fnl_sal, c.quot) memoQuot, a.quot rte,to_char(trunc(a.cts,2) * Nvl(c.Fnl_Sal, c.Quot), '9999999990.00') amt,decode(nvl(b.exh_rte,1),1,'','#00CC66') color,to_char(trunc(a.cts,2) * a.rap_rte, '99999999990.00') rapVlu,a.rmk "+
        " ,decode(a.rap_rte ,'1',null,'0',null, trunc((nvl(c.fnl_sal, c.quot)/greatest(a.rap_rte,1)*100)-100, 2))  r_dis,a.rap_rte,a.cert_no,b.typ,byr.get_trms(b.nmerel_idn) trm"+xlconQ;
            for (int i = 0; i < vwPrpLst.size(); i++) {
            String prpNme = (String)vwPrpLst.get(i);
            String fld = "prp_";
            int j = i + 1;
            if (j < 10)
                fld += "00" + j;
            else if (j < 100)
                fld += "0" + j;
            else if (j > 100)
                fld += j;

            if(prpDspBlocked.contains(prp)){
            }else{
            itemHdr.add(prpNme);
            itemHdrXL.add(prpNme);
            if(prpNme.equals("COL-SHADE")){
            itemHdrXL.add("Raprte");    
            itemHdrXL.add("Rapdis");   
            itemHdrXL.add("Prc / Crt");    
            itemHdrXL.add("Amount");    
            }
            }    
            rsltQ += ", " + fld;
           
         }
            
        itemHdr.add("Remark");        
        if(cnt.equals("xljf")){
        itemHdr.add("Net Dis");            
        itemHdr.add("Net Amt");            
        itemHdr.add("Grand Total");            
        }    
        itemHdrXL.add("Id");    
        itemHdrXL.add("Remark");    
        itemHdrXL.add("Status");    
        itemHdrXL.add("Memo Type");    
        itemHdrXL.add("Trm");    
        itemHdrXL.add("Qty");        
            
        rsltQ = rsltQ +" from gt_srch_multi a , mjan b , jandtl c , mnme d " + 
                " where a.stk_idn = c.mstk_idn and b.stt='IS' and c.stt='IS' and b.typ in ('E', 'I', 'WH','K','H','BID','O') and c.idn = b.idn and d.nme_idn = b.nme_idn "+conQ+" and a.flg= ? ";
        ary.add("Z");
        if(!byr.equals("") && !byr.equals("0")){
        rsltQ = rsltQ +" and d.nme_idn=? ";
        ary.add(byr);
        }
        if(!appfrmDte.equals("") && !apptoDte.equals("")){
        rsltQ = rsltQ + " and trunc(c.dte) between trunc(to_date('"+appfrmDte+"','dd-mm-rrrr')) and trunc(to_date('"+apptoDte+"','dd-mm-rrrr')) ";
        }
        rsltQ = rsltQ +filterconQ+" order by sal , byr ";

            ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
         while(rs.next()) {
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("Buyer", util.nvl(rs.getString("byr")));
                pktPrpMap.put("Sale Person",util.nvl(rs.getString("sal")));
                pktPrpMap.put("Id",util.nvl(rs.getString("idn")));
                pktPrpMap.put("Date",util.nvl(rs.getString("dte")));
                pktPrpMap.put("Days",util.nvl(rs.getString("days")));
                pktPrpMap.put("Qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                pktPrpMap.put("Status",util.nvl(rs.getString("stt")));
                pktPrpMap.put("Memo Type",util.nvl(rs.getString("typ")));
                pktPrpMap.put("Quot",util.nvl(rs.getString("memoQuot")));
                pktPrpMap.put("Prc / Crt",util.nvl(rs.getString("rte")));
                pktPrpMap.put("Amount",util.nvl(rs.getString("amt")));
                pktPrpMap.put("amtex",util.nvl(rs.getString("amt")));
                pktPrpMap.put("Packet No",util.nvl(rs.getString("vnm")));
                pktPrpMap.put("color",util.nvl(rs.getString("color")));
                pktPrpMap.put("Rapdis",util.nvl(rs.getString("r_dis")));
                pktPrpMap.put("Raprte",util.nvl(rs.getString("rap_rte")));
                pktPrpMap.put("RapVlu", util.nvl(rs.getString("rapVlu")));
                pktPrpMap.put("Trm",util.nvl(rs.getString("trm")));
                pktPrpMap.put("Remark",util.nvl(rs.getString("rmk")));
             if(cnt.equals("xljf")){
             pktPrpMap.put("Net Dis",util.nvl(rs.getString("STONEWISE_DIS")));
             pktPrpMap.put("Net Amt",util.nvl(rs.getString("NET_DISC_AMT")));
             pktPrpMap.put("Grand Total",util.nvl(rs.getString("grandttl")));
             }
                for(int j=0; j < vwPrpLst.size(); j++){
                     String lprp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                    String val = util.nvl(rs.getString(fld)) ;
                    if (lprp.toUpperCase().equals("CRTWT"))
                        val = util.nvl(rs.getString("cts"));
                    if (lprp.toUpperCase().equals("RAP_DIS"))
                        val = util.nvl(rs.getString("r_dis"));
                    if (lprp.toUpperCase().equals("RAP_RTE"))
                        val = util.nvl(rs.getString("rap_rte"));
                    if(lprp.toUpperCase().equals("RTE"))
                        val = util.nvl(rs.getString("rte"));
                    if (lprp.toUpperCase().equals("CERT NO.") || lprp.toUpperCase().equals("CERT_NO")){
                    val = util.nvl(rs.getString("cert_no"));
                      lprp="CERT_NO";
                    }
                      
                      pktPrpMap.put(lprp, val);
                }
                              
             pktList.add(pktPrpMap);
         }
        rs.close(); pst.close();
        ArrayList partyLst = srchQuery.getEmpList(req,res);
        udf.setEmpList(partyLst);
        req.setAttribute("view","Y");
        session.setAttribute("pktList", pktList);
        session.setAttribute("itemHdr",itemHdr);
        session.setAttribute("itemHdrXL",itemHdrXL);
        udf.setReportNme("Memo Pending Report");
            util.updAccessLog(req,res,"Report Action", "fetchmemoPnd end");
    return am.findForward("loadMemo");
        }
    }

    public ReportAction() {
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
                util.updAccessLog(req,res,"Report Action", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Report Action", "init");
            }
            }
            return rtnPg;
            }
   
}

