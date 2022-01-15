package ft.com.report;

import ft.com.GtMgr;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;
import ft.com.ExcelUtilObj;
import ft.com.JspUtil;
import ft.com.PdfforReport;
import ft.com.dao.ByrDao;
import ft.com.dao.DFMenu;
import java.sql.PreparedStatement;
import ft.com.dao.DFMenuItm;

import ft.com.dao.MNme;
import ft.com.dao.ObjBean;
import ft.com.fileupload.FileUploadForm;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.URLEncoder;

import java.sql.Connection;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.math.BigDecimal;

import java.math.RoundingMode;

import java.net.InetSocketAddress;

import java.util.Collections;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.spy.memcached.MemcachedClient;

public class RptAction extends DispatchAction {

    public ActionForward loadlocsmmy(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "loadlocsmmy start");
        ReportForm udf = (ReportForm)af;
        udf.resetALL();
        db.execUpd("delete", "delete from gt_srch_rslt", new ArrayList());
        db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        String location = util.nvl(req.getParameter("LOC"));
        ArrayList byrList = new ArrayList();
        ArrayList pktList = new ArrayList();
        ArrayList params = new ArrayList();
        String isMix = util.nvl(req.getParameter("ISMIX"));
        String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
        String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());   
        SearchQuery srchQuery = new SearchQuery();
        String getByr =
            "select  distinct byr , byr_idn from Dlv_Pndg_Loc_pkt_V where typ='DLV' and loc='" + location + "' ";
      if(isMix.equals("Y"))
          getByr = getByr+ " and pkt_ty <> 'NR'";
      else
       getByr = getByr+ " and pkt_ty = 'NR'";
            
       if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
       getByr = getByr+ " and emp_idn= ? ";  
       params.add(dfNmeIdn);
       }
        getByr = getByr+ " order by byr";

          ArrayList outLst = db.execSqlLst("byr", getByr, params);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            ByrDao byr = new ByrDao();

            byr.setByrIdn(rs.getInt("byr_idn"));
            byr.setByrNme(rs.getString("byr"));
            byrList.add(byr);
        }
        rs.close();
            pst.close();
            
        params = new ArrayList();  
        udf.setByrList(byrList);
        String conQ = " and a.loc='" + location + "'";
        if(isMix.equals("Y"))
            conQ = " and b.pkt_ty <> 'NR'";
        else
         conQ = " and b.pkt_ty = 'NR'";
        
        if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
        conQ = conQ+ " and a.emp_idn= ? ";  
        params.add(dfNmeIdn);
        }
            
        String srchRef =
            " Insert Into gt_srch_multi(Pkt_Ty, Stk_Idn,Srch_Id,Rln_Idn,pair_id,Byr,emp, Vnm, Stt , Flg , Qty , Cts,Quot ,prte,Cmp,Rap_Rte,Rap_Dis, Sk1,pkt_dte ) " +
            "Select b.Pkt_Ty , b.Idn,a.dlv_idn,a.byr_idn,a.emp_idn,a.byr,a.emp, b.Vnm , b.Stt , 'Z' ,  a.Qty , a.Cts ,a.quot,a.val,b.cmp,b.rap_rte,b.rap_dis, b.Sk1,a.dlv_dte " +
            "From Dlv_Pndg_Loc_Pkt_V A,Mstk B " + "where a.mstk_idn=b.idn  " + conQ;
        int ct = db.execUpd("insert gt", srchRef, params);
        String pktPrp = "srch_pkg.POP_PKT_PRP_TEST(pMdl=>?,pTbl=>'GT_SRCH_MULTI')";
        ArrayList ary = new ArrayList();
        ary.add("DLV_LOC");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);

        fetchData(req,res, "","");
        ArrayList partyLst = srchQuery.getEmpList(req,res);
        udf.setEmpList(partyLst);
            util.updAccessLog(req,res,"Rpt Action", "loadlocsmmy end");
        return am.findForward("locsmmy");
        }
    }

    public ActionForward fetchloc(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "fetchloc start");
        ReportForm udf = (ReportForm)af;
        SearchQuery srchQuery = new SearchQuery();
        String byr = util.nvl((String)udf.getByrIdn());
        String empIdn = util.nvl((String)udf.getValue("empIdn"));
      
        fetchData(req,res, byr,empIdn);
        ArrayList partyLst = srchQuery.getEmpList(req,res);
        udf.setEmpList(partyLst);    
            util.updAccessLog(req,res,"Rpt Action", "fetchloc end");
        return am.findForward("locsmmy");
        }
    }
    
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "fetch start");
        ReportForm udf = (ReportForm)af;
        SearchQuery srchQuery = new SearchQuery();
        String byr = util.nvl((String)udf.getByrIdn());
        String empIdn = util.nvl((String)udf.getValue("empIdn"));
      
        fetchData(req,res, byr,empIdn);
        ArrayList partyLst = srchQuery.getEmpList(req,res);
        udf.setEmpList(partyLst);    
            util.updAccessLog(req,res,"Rpt Action", "fetch end");
        return am.findForward("pndsummy");
        }
    }
    public ActionForward salePnd(ActionMapping am, ActionForm af, HttpServletRequest req,
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
               util.updAccessLog(req,res,"Rpt Action", "salePnd start");
           ReportForm udf = (ReportForm)af;
           String pkt_typ=util.nvl(req.getParameter("PKT_TYP"));
           String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
           String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());  
           ArrayList params = new ArrayList();
           SearchQuery srchQuery = new SearchQuery();
           db.execUpd("delete", "delete from gt_srch_multi", new ArrayList());
           String srchRef =
               " Insert Into gt_srch_multi (Pkt_Ty, Stk_Idn,Srch_Id,Rln_Idn,Byr,pair_id,emp, Vnm, Pkt_Dte, Stt , Flg , Qty , Cts,Quot , Rap_Rte ,prte, Sk1,exh_rte ) \n" + 
               "select c.pkt_ty , c.idn , b.idn , a.nme_idn ,  d.fnme ,d.emp_idn,byr.get_nm(nvl(d.emp_idn,0)), c.vnm , a.dte , c.stt ,'Z',b.qty , b.cts ,trunc(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte, 1), 2),\n" + 
               "c.rap_rte , trunc(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte, 1), 2) * b.cts , c.sk1,nvl(a.exh_rte, 1)  \n" + 
               "from mjan a,jandtl b, mstk c  , mnme d\n" + 
               "where a.idn = b.idn  and b.mstk_idn = c.idn and a.nme_idn=d.nme_idn \n" + 
               "and a.stt='IS' and b.stt = 'AP' and a.typ in ('EAP', 'IAP', 'WAP','OAP' ,'LAP','BCAP','MAP','SAP','HAP','BAP','KAP','BIDAP')";
           if(!pkt_typ.equals(""))
               srchRef+="  and c.stt in('MKAV') and c.pkt_ty in('MX','MIX')"; 
           else
           srchRef+="  and c.stt in('MKAP','MKWA','LB_PRI_AP','MKSA') and c.pkt_ty in('NR','SMX') ";
           
           
           if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
           srchRef+=" and d.emp_idn= ? ";  
           params.add(dfNmeIdn);
           }
               
           int ct = db.execUpd("insert gt", srchRef, params);
           String pktPrp = "srch_pkg.POP_PKT_PRP_TEST(pMdl=>?,pTbl=>'GT_SRCH_MULTI')";
           ArrayList ary = new ArrayList();
           ary.add("DLV_LOC");
           ct = db.execCall(" Srch Prp ", pktPrp, ary);
           fetchData(req,res, "","");
           ArrayList partyLst = srchQuery.getEmpList(req,res);
           udf.setEmpList(partyLst);
           udf.setReportNme("Sale Pending Summary Report");
               util.updAccessLog(req,res,"Rpt Action", "salePnd end");
           return am.findForward("pndsummy");
           }
       }

    public ActionForward csPnd(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "csPnd start");
        ReportForm udf = (ReportForm)af;
        String pkt_typ=util.nvl(req.getParameter("PKT_TYP"));
        String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
        String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());  
        ArrayList params = new ArrayList();
        SearchQuery srchQuery = new SearchQuery();
        db.execUpd("delete", "delete from gt_srch_multi", new ArrayList());
        String srchRef =
            " Insert Into gt_srch_multi (Pkt_Ty, Stk_Idn,Srch_Id,Rln_Idn,Byr,pair_id,emp, Vnm, Pkt_Dte, Stt , Flg , Qty , Cts,Quot , Rap_Rte ,prte, Sk1,exh_rte ) \n" + 
            "select c.pkt_ty , c.idn , b.idn , a.nme_idn ,  d.fnme ,d.emp_idn,byr.get_nm(nvl(d.emp_idn,0)), c.vnm , a.dte , c.stt ,'Z',b.qty , b.cts ,trunc(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte, 1), 2),\n" + 
            "c.rap_rte , trunc(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte, 1), 2) * b.cts , c.sk1,nvl(a.exh_rte, 1)  \n" + 
            "from mjan a,jandtl b, mstk c  , mnme d\n" + 
            "where a.idn = b.idn  and b.mstk_idn = c.idn and a.nme_idn=d.nme_idn \n" + 
            "and a.stt='IS' and b.stt = 'IS' and a.typ in ('CS')";
        if(!pkt_typ.equals(""))
            srchRef+="  and c.stt in('MKAV') and c.pkt_ty in('MX','MIX')"; 
        else
        srchRef+="  and c.stt in('MKCS') and  c.pkt_ty in('NR','SMX')";
        
        
        if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
        srchRef+=" and d.emp_idn= ? ";  
        params.add(dfNmeIdn);
        }
            
        int ct = db.execUpd("insert gt", srchRef, new ArrayList());
        String pktPrp = "srch_pkg.POP_PKT_PRP_TEST(pMdl=>?,pTbl=>'GT_SRCH_MULTI')";
        ArrayList ary = new ArrayList();
        ary.add("DLV_LOC");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        fetchData(req,res, "","");
        ArrayList partyLst = srchQuery.getEmpList(req,res);
        udf.setEmpList(partyLst);
        udf.setReportNme("Consignment Pending Summary Report");
            util.updAccessLog(req,res,"Rpt Action", "csPnd end");
        return am.findForward("pndsummy");
        }
    }
    public ActionForward dlvPnd(ActionMapping am, ActionForm af, HttpServletRequest req,
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
                util.updAccessLog(req,res,"Rpt Action", "dlvPnd start");
            ReportForm udf = (ReportForm)af;
            String pkt_typ=util.nvl(req.getParameter("PKT_TYP"));
                String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
                String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());  
                ArrayList params = new ArrayList();
                SearchQuery srchQuery = new SearchQuery();
            db.execUpd("delete", "delete from gt_srch_multi", new ArrayList());
            String srchRef =
                " Insert Into gt_srch_multi (Pkt_Ty, Stk_Idn,Srch_Id,Rln_Idn,Byr,pair_id,emp, Vnm, Pkt_Dte, Stt , Flg , Qty , Cts,Quot , Rap_Rte ,prte, Sk1,exh_rte )" +
                " select c.pkt_ty , c.idn , b.idn , b.nme_idn , b.byr ,b.emp_idn,b.emp, c.vnm , b.dte , c.stt ,'Z',b.qty , b.cts , nvl(b.fnl_usd,b.quot )     ,c.rap_rte , nvl(b.fnl_usd,b.quot ) * b.cts , c.sk1,nvl(d.exh_rte,1) " +
                " from Sal_Pkt_Dtl_V b, mstk c  , msal d where " +
                " b.mstk_idn=c.idn  and c.idn=b.mstk_idn and b.idn = d.idn and d.stt='IS' " +
                " and b.stt = 'SL' and d.typ='SL'  and d.flg1='CNF'";
            if(!pkt_typ.equals(""))
                srchRef+="  and c.stt='MKAV' and c.pkt_ty in('MX','MIX')"; 
            else
            srchRef+="  and c.stt='MKSL' and  c.pkt_ty in('NR','SMX')";
                
                if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                srchRef+=" and b.emp_idn= ? ";  
                params.add(dfNmeIdn);
                }
                
            int ct = db.execUpd("insert gt", srchRef, params);
            String pktPrp = "srch_pkg.POP_PKT_PRP_TEST(pMdl=>?,pTbl=>'GT_SRCH_MULTI')";
            ArrayList ary = new ArrayList();
            ary.add("DLV_LOC");
            ct = db.execCall(" Srch Prp ", pktPrp, ary);
            fetchData(req,res, "","");
            //    ResultSet rs = null;
            //    String conQ="";
            //    ArrayList ary = new ArrayList();
            //    ArrayList byridnLst=new ArrayList();
            //    HashMap byrdtl=new HashMap();
            //    ArrayList byrdtlLst=new ArrayList();
            //    HashMap dataDtl=new HashMap();
            //    String loadqry ="select d.fnme byr, d.nme_idn byrid,b.typ typ,A.idn\n" +
            //    "         , to_char(b.dte,'dd-mm-yyyy') dte , sum(c.qty) qty , to_char(sum(trunc(c.cts,2)),'999,990.00') cts \n" +
            //    "         , to_char(sum(trunc(c.cts,2)*(nvl(b.fnl_usd, b.quot))),'999,9999,999,990.00') vlu \n" +
            //    "       from msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d where \n" +
            //    "         a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn \n" +
            //    "        and b.stt = 'SL' and b.typ='SL' and c.stt='MKSL'\n" +
            //    "       group by d.emp_idn, d.nme_idn, d.fnme, to_char(b.dte,'dd-mm-yyyy'),b.typ,A.Idn \n" +
            //    "        order by d.fnme,A.idn desc ";
            //    rs = db.execSql("Sale Pending", loadqry, ary);
            //    while(rs.next()){
            //    String byrid=util.nvl(rs.getString("byrid"));
            //        if(!byridnLst.contains(byrid)){
            //            byridnLst.add(byrid);
            //            byrdtl.put(byrid, util.nvl(rs.getString("byr")));
            //            byrdtlLst = new  ArrayList();
            //        }
            //        HashMap byrdata = new HashMap();
            //        byrdata.put("DTE",util.nvl(rs.getString("dte")));
            //        byrdata.put("QTY",util.nvl(rs.getString("qty")));
            //        byrdata.put("CTS",util.nvl(rs.getString("cts")));
            //        byrdata.put("VLU",util.nvl(rs.getString("vlu")));
            //        byrdtlLst.add(byrdata);
            //        dataDtl.put(byrid, byrdtlLst) ;
            //    }
            //        loadqry ="  select d.nme_idn byrid \n" +
            //        "         , sum(c.qty) qty  , to_char(sum(trunc(c.cts,2)),'999,990.00') cts \n" +
            //        "         , to_char(sum(trunc(c.cts,2)*(nvl(b.fnl_usd, b.quot))),'999,999,999,990.00') vlu \n" +
            //        "         from msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d \n" +
            //        "        where a.idn=b.idn and b.mstk_idn=c.idn \n" +
            //        "         and b.stt = 'SL' and b.typ='SL' and c.stt='MKSL'\n" +
            //        "        and d.nme_idn = a.nme_idn \n" +
            //        "        group by d.nme_idn";
            //            rs = db.execSql("Sale Person Total", loadqry, ary);
            //        while(rs.next()) {
            //            String byrid=util.nvl(rs.getString("byrid"));
            //            HashMap ttlDtl = new HashMap();
            //            ttlDtl.put("CTS",util.nvl(rs.getString("cts")));
            //            ttlDtl.put("VLU",util.nvl(rs.getString("vlu")));
            //            ttlDtl.put("QTY",util.nvl(rs.getString("qty")));
            //            dataDtl.put(byrid+"_TTL", ttlDtl);
            //        }
            //        loadqry ="select sum(c.qty) qty \n" +
            //        "        , to_char(sum(trunc(c.cts,2)),'999,990.00') cts \n" +
            //        "       , to_char(sum(trunc(c.cts,2)*nvl(b.fnl_usd, b.quot)),'999,9999,999,990.00') vlu \n" +
            //        "        from msal a,Sal_Pkt_Dtl_V b, mstk c,mnme d \n" +
            //        "       where a.idn=b.idn and b.mstk_idn=c.idn \n" +
            //        "       and b.stt = 'SL' and b.typ='SL' and c.stt='MKSL'\n" +
            //        "       and d.nme_idn=a.nme_idn";
            //            rs = db.execSql("Grand Total", loadqry, ary);
            //        while(rs.next()) {
            //            HashMap ttlDtl = new HashMap();
            //            ttlDtl.put("CTS",util.nvl(rs.getString("cts")));
            //            ttlDtl.put("VLU",util.nvl(rs.getString("vlu")));
            //            ttlDtl.put("QTY",util.nvl(rs.getString("qty")));
            //            dataDtl.put("GRANDTTL", ttlDtl);
            //        }
            //        req.setAttribute("dataDtl", dataDtl);
            //        req.setAttribute("byrdtl", byrdtl);
            //        req.setAttribute("byridnLst", byridnLst);
            //        req.setAttribute("view", "Y");
            ArrayList partyLst = srchQuery.getEmpList(req,res);
            udf.setEmpList(partyLst);
            udf.setReportNme("Delivery Pending Summary Report");
                util.updAccessLog(req,res,"Rpt Action", "dlvPnd end");
            return am.findForward("pndsummy");
            }
        }

    public ActionForward loadSaleDisReport(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Rpt Action", "loadSaleDisReport start");
            SearchQuery srchQuery = new SearchQuery();
            ReportForm udf = (ReportForm)af;
            udf.resetALL();
            GenericInterface genericInt = new GenericImpl();
            ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_DIS_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_DIS_SRCH");
            info.setGncPrpLst(assortSrchList);  
            ArrayList empList= srchQuery.getByrList(req,res);
            udf.setByrList(empList); 
            util.updAccessLog(req,res,"Rpt Action", "loadSaleDisReport end");
            return am.findForward("loadSaleDisReport");
            }
        }
    public ActionForward fetchSaleDisReport(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Rpt Action", "fetchSaleDisReport start");
            ArrayList itemHdr=new ArrayList();
            ResultSet rs = null;
            String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
            String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_DIS_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_DIS_SRCH");
            info.setGncPrpLst(genricSrchLst);
            HashMap pageDtl=(HashMap)allPageDtl.get("SALE_DIS_REPORT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("SALE_DIS_REPORT");
            allPageDtl.put("SALE_DIS_REPORT",pageDtl);
            }
            info.setPageDetails(allPageDtl);     
                
                
            ReportForm udf = (ReportForm)af;
            SearchQuery srchQuery = new SearchQuery();
            String frmdte=util.nvl((String)udf.getValue("frmdte")); 
            String todte=util.nvl((String)udf.getValue("todte"));
            ArrayList pktList = new  ArrayList();
            ArrayList ary = new ArrayList();
                ArrayList params = new ArrayList();
                params.add(frmdte);
                params.add(todte);                
                int ct = db.execCall("DP_GEN_SALE_DISC", "DP_GEN_SALE_DISC (pDteFrm => ?, pDteTo => ?)", params);   
                
            
            if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                String empNm="";
                String empQ="select byr.GET_NM(?) empnm from dual";
                ary = new ArrayList();  
                ary.add(dfNmeIdn);
                rs = db.execSql("empQ", empQ, ary); 
                while(rs.next()) {
                    empNm=util.nvl(rs.getString("empnm"));
                }
                rs.close();
                String upddys = "  Update gt_srch_multi set dsp_stt = 'D' where upper(emp) <> upper('"+empNm+"')" ;
                ct = db.execUpd("insert gt",upddys, new ArrayList());
            }
            
            String rsltQ = "WITH EMP_TTL as (  select emp,  sum(cts) ects, sum(sl_rte*cts) eSlVlu, sum(quot*cts) grossVlu, sum(cmp*cts) bseVlu  \n" + 
            "  , sum(srt_003*cts) aadat, sum(srt_004*cts) coor, sum(srt_005*cts) mgmt, sum(srt_006*cts) loy, sum(srt_007*cts) extra  \n" + 
            "from gt_srch_multi\n" + 
            "where dsp_stt='Z'\n" + 
            "group by emp)  , \n" + 
            "EMP_FLG_TTL as (select emp, sum(cts) ects\n" + 
            ", sum(quot*cts) grossVlu, sum(cmp*cts) bseVlu, sum(sl_rte*cts) eslVlu\n" + 
            ", sum(decode(flg, 'Y', sl_rte, 0)*cts) fNetSlVlu, sum(decode(flg, 'F', sl_rte, 0)*cts) fullNetSlVlu   \n" + 
            ", sum(decode(flg, 'N', sl_rte, 0)*cts) fOthSlVlu\n" + 
            ", sum(decode(prp_001, 'Y', decode(flg, 'N', 0, sl_rte*cts), 0)) fNetBlindSlVlu   \n" + 
            ", sum(decode(prp_001, 'Y', decode(flg, 'N', sl_rte*cts, 0), 0)) fOthBlindSlVlu   \n" + 
            "from gt_srch_multi \n" + 
            "where dsp_stt='Z'\n" + 
            "group by emp)   \n" + 
            "select t.emp \n" + 
            ", ROUND(f.grossVlu) grossVlu, ROUND(f.grossVlu - f.bseVlu) profit, ROUND(f.eSlVlu) ttlSl   \n" + 
            ", ROUND(f.fNetSlVlu) netSl, ROUND(f.fullNetSlVlu) fullnetSl   \n" + 
            ", ROUND(f.fNetBlindSlVlu) netBlind, ROUND(f.fOthSlVlu) otherSl   \n" + 
            ", ROUND(f.fOthBlindSlVlu) otherBlSl, ROUND(t.aadat) aadat\n" + 
            ", ROUND(t.coor) coor, ROUND(t.mgmt) mgmt, ROUND(t.loy) loy, ROUND(t.extra) extra   \n" + 
            ", ROUND((f.grossVlu*2/100)) est_aadat, ROUND((t.aadat)) act_aadat   \n" + 
            ", ROUND((f.eSlVlu*0.5/100)) est_coor   , ROUND((t.coor)) act_coor   \n" + 
            ", ROUND((f.grossVlu*2/100) - (t.aadat)) aadat_diff, ROUND((f.eSlVlu*0.5/100) - (t.coor)) coor_diff   \n" + 
            "from emp_ttl t, emp_flg_ttl f   \n" + 
            "where t.emp = f.emp   \n" + 
            "order by t.emp";

            
             ary = new ArrayList();  
              ArrayList outLst = db.execSqlLst("Collection", rsltQ, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
                HashMap pktPrpMap = new HashMap();
                 pktPrpMap.put("EMP", util.nvl(rs.getString("emp").toUpperCase()));
                 pktPrpMap.put("GROSSVLU",util.nvl(rs.getString("grossVlu")));
                 pktPrpMap.put("PROFIT",util.nvl(rs.getString("profit")));
                 pktPrpMap.put("TTLSL",util.nvl(rs.getString("ttlSl")));
                 pktPrpMap.put("NETSL",util.nvl(rs.getString("netSl")));
                 pktPrpMap.put("FNETSL",util.nvl(rs.getString("fullnetSl")));
                 pktPrpMap.put("NETBLIND",util.nvl(rs.getString("netBlind")));
                 pktPrpMap.put("OTHERSL",util.nvl(rs.getString("otherSl")));
                 pktPrpMap.put("OTHERBLSL",util.nvl(rs.getString("otherBlSl")));
                 pktPrpMap.put("AADAT",util.nvl(rs.getString("aadat")));
                 pktPrpMap.put("CORD",util.nvl(rs.getString("coor")));
                 pktPrpMap.put("MGMT",util.nvl(rs.getString("mgmt")));
                 pktPrpMap.put("LOY",util.nvl(rs.getString("loy")));
                 pktPrpMap.put("EXTRA",util.nvl(rs.getString("extra")));
                 pktPrpMap.put("EST_AADAT",util.nvl(rs.getString("est_aadat")));
                 pktPrpMap.put("ACT_AADAT",util.nvl(rs.getString("act_aadat")));
                 pktPrpMap.put("EST_CORD",util.nvl(rs.getString("est_coor")));
                 pktPrpMap.put("ACT_CORD",util.nvl(rs.getString("act_coor")));
                 pktPrpMap.put("AADAT_DIFF",util.nvl(rs.getString("aadat_diff")));
                 pktPrpMap.put("CORD_DIFF",util.nvl(rs.getString("coor_diff")));
                 pktList.add(pktPrpMap);
             }
            rs.close();
                pst.close();
                
                rsltQ = "WITH EMP_TTL as (  select 'TOTAL' emp,  sum(cts) ects, sum(sl_rte*cts) eSlVlu, sum(quot*cts) grossVlu, sum(cmp*cts) bseVlu  \n" + 
                            "  , sum(srt_003*cts) aadat, sum(srt_004*cts) coor, sum(srt_005*cts) mgmt, sum(srt_006*cts) loy, sum(srt_007*cts) extra  \n" + 
                            "from gt_srch_multi\n" + 
                            "where dsp_stt='Z'\n" + 
                            ")  , \n" + 
                            "EMP_FLG_TTL as (select 'TOTAL' emp, sum(cts) ects\n" + 
                            ", sum(quot*cts) grossVlu, sum(cmp*cts) bseVlu, sum(sl_rte*cts) eslVlu\n" + 
                            ", sum(decode(flg, 'Y', sl_rte, 0)*cts) fNetSlVlu, sum(decode(flg, 'F', sl_rte, 0)*cts) fullNetSlVlu   \n" + 
                            ", sum(decode(flg, 'N', sl_rte, 0)*cts) fOthSlVlu\n" + 
                            ", sum(decode(prp_001, 'Y', decode(flg, 'N', 0, sl_rte*cts), 0)) fNetBlindSlVlu   \n" + 
                            ", sum(decode(prp_001, 'Y', decode(flg, 'N', sl_rte*cts, 0), 0)) fOthBlindSlVlu   \n" + 
                            "from gt_srch_multi \n" + 
                            "where dsp_stt='Z'\n" + 
                            ")   \n" + 
                            "select 'TOTAL' emp \n" + 
                            ", ROUND(f.grossVlu) grossVlu, ROUND(f.grossVlu - f.bseVlu) profit, ROUND(f.eSlVlu) ttlSl   \n" + 
                            ", ROUND(f.fNetSlVlu) netSl, ROUND(f.fullNetSlVlu) fullnetSl   \n" + 
                            ", ROUND(f.fNetBlindSlVlu) netBlind, ROUND(f.fOthSlVlu) otherSl   \n" + 
                            ", ROUND(f.fOthBlindSlVlu) otherBlSl, ROUND(t.aadat) aadat\n" + 
                            ", ROUND(t.coor) coor, ROUND(t.mgmt) mgmt, ROUND(t.loy) loy, ROUND(t.extra) extra   \n" + 
                            ", ROUND((f.grossVlu*2/100)) est_aadat, ROUND((t.aadat)) act_aadat   \n" + 
                            ", ROUND((f.eSlVlu*0.5/100)) est_coor   , ROUND((t.coor)) act_coor   \n" + 
                            ", ROUND((f.grossVlu*2/100) - (t.aadat)) aadat_diff, ROUND((f.eSlVlu*0.5/100) - (t.coor)) coor_diff   \n" + 
                            "from emp_ttl t, emp_flg_ttl f   \n" + 
                            "where t.emp = f.emp   \n";

                            
                             ary = new ArrayList();  
                             outLst = db.execSqlLst("Collection", rsltQ, ary);
                             pst = (PreparedStatement)outLst.get(0);
                               rs = (ResultSet)outLst.get(1);
                             while(rs.next()) {
                                HashMap pktPrpMap = new HashMap();
                                 pktPrpMap.put("EMP", util.nvl(rs.getString("emp").toUpperCase()));
                                 pktPrpMap.put("GROSSVLU",util.nvl(rs.getString("grossVlu")));
                                 pktPrpMap.put("PROFIT",util.nvl(rs.getString("profit")));
                                 pktPrpMap.put("TTLSL",util.nvl(rs.getString("ttlSl")));
                                 pktPrpMap.put("NETSL",util.nvl(rs.getString("netSl")));
                                 pktPrpMap.put("FNETSL",util.nvl(rs.getString("fullnetSl")));
                                 pktPrpMap.put("NETBLIND",util.nvl(rs.getString("netBlind")));
                                 pktPrpMap.put("OTHERSL",util.nvl(rs.getString("otherSl")));
                                 pktPrpMap.put("OTHERBLSL",util.nvl(rs.getString("otherBlSl")));
                                 pktPrpMap.put("AADAT",util.nvl(rs.getString("aadat")));
                                 pktPrpMap.put("CORD",util.nvl(rs.getString("coor")));
                                 pktPrpMap.put("MGMT",util.nvl(rs.getString("mgmt")));
                                 pktPrpMap.put("LOY",util.nvl(rs.getString("loy")));
                                 pktPrpMap.put("EXTRA",util.nvl(rs.getString("extra")));
                                 pktPrpMap.put("EST_AADAT",util.nvl(rs.getString("est_aadat")));
                                 pktPrpMap.put("ACT_AADAT",util.nvl(rs.getString("act_aadat")));
                                 pktPrpMap.put("EST_CORD",util.nvl(rs.getString("est_coor")));
                                 pktPrpMap.put("ACT_CORD",util.nvl(rs.getString("act_coor")));
                                 pktPrpMap.put("AADAT_DIFF",util.nvl(rs.getString("aadat_diff")));
                                 pktPrpMap.put("CORD_DIFF",util.nvl(rs.getString("coor_diff")));
                                 pktList.add(pktPrpMap);
                             }
                            rs.close();
                           pst.close();
            
                            itemHdr.add("EMP");
                            itemHdr.add("GROSSVLU");
                            itemHdr.add("PROFIT");
                            itemHdr.add("TTLSL");
                            itemHdr.add("FNETSL");
                            itemHdr.add("NETSL");
                            itemHdr.add("OTHERSL");
                            itemHdr.add("NETBLIND");
                            itemHdr.add("OTHERBLSL");
                            itemHdr.add("LOY");
                            itemHdr.add("EXTRA");
                            itemHdr.add("MGMT");
                            itemHdr.add("ACT_AADAT");
                            itemHdr.add("ACT_CORD");
                            itemHdr.add("EST_AADAT");
                            itemHdr.add("EST_CORD");
                            itemHdr.add("AADAT_DIFF");
                            itemHdr.add("CORD_DIFF"); 
            session.setAttribute("pktListEMP", pktList);
            session.setAttribute("itemHdrEMP", itemHdr);
            req.setAttribute("view", "Y");  
            udf.reset();
            udf.setValue("frmdte",frmdte);
            udf.setValue("todte",todte);
            ArrayList empList= srchQuery.getByrList(req,res);
            udf.setByrList(empList); 
            util.updAccessLog(req,res,"Rpt Action", "fetchSaleDisReport end");
            return am.findForward("loadSaleDisReport");
            }
        }
    
   
    public ActionForward fetchSaleDisReportUsingFilter(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Rpt Action", "fetchSaleDisReport start");
            ArrayList itemHdr=new ArrayList();
            ResultSet rs = null;
    
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_DIS_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SAL_DIS_SRCH");
            info.setGncPrpLst(genricSrchLst);
            HashMap pageDtl=(HashMap)allPageDtl.get("SALE_DIS_REPORT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("SALE_DIS_REPORT");
            allPageDtl.put("SALE_DIS_REPORT",pageDtl);
            }
            info.setPageDetails(allPageDtl);     
                   
            ReportForm udf = (ReportForm)af;
            SearchQuery srchQuery = new SearchQuery();
            String reset =util.nvl((String)req.getParameter("reset"));
            String frmdte=util.nvl((String)udf.getValue("frmdte")); 
            String todte=util.nvl((String)udf.getValue("todte"));
            if(reset.equals("Y")){
                        udf.reset();
                        udf.setValue("frmdte",frmdte);
                        udf.setValue("todte",todte);
            }
            String empId =util.nvl((String)udf.getValue("empId"));
            String byrId = util.nvl((String)udf.getValue("byrId"));
            ArrayList pktList = new  ArrayList();
            ArrayList ary = new ArrayList();  
            HashMap prp = info.getPrp();
            HashMap mprp = info.getMprp();
            String empNm="",conQ="";
            String deptFrm="",deptto="";
            for(int i=0;i<genricSrchLst.size();i++){
                        ArrayList prplist =(ArrayList)genricSrchLst.get(i);
                        String lprp = (String)prplist.get(0);
                        String typ = util.nvl((String)mprp.get(lprp+"T"));
                        String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                        String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                        if(fldVal2.equals(""))
                        fldVal2=fldVal1;                   
                        if(!fldVal1.equals("") && !fldVal2.equals("")){
                            deptFrm=fldVal1;
                            deptto=fldVal2;               
                        }             
             }  
             if(empId.equals("") || empId.equals("0"))
             empId="";
             if(byrId.equals("") || byrId.equals("0"))
             byrId="";
             
             if(!empId.equals("")){
                 String empQ="select byr.GET_NM(?) empnm from dual";
                 ary = new ArrayList();  
                 ary.add(empId);
              ArrayList   outLst = db.execSqlLst("empQ", empQ, ary); 
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
                 rs = (ResultSet)outLst.get(1);
                 while(rs.next()) {
                     empNm=util.nvl(rs.getString("empnm"));
                 }
                 rs.close();
                 pst.close();
             }
            
            ary = new ArrayList(); 
            if(!empNm.equals(""))
                conQ+=" and upper(emp) = upper('"+empNm+"') \n";
            
           if(!byrId.equals("")){
                conQ+=" and rln_idn = ? \n"; 
                ary.add(byrId);
                ary.add(byrId);
           }
                if(deptFrm.equals("") || deptto.equals("")){
                    deptFrm="srt_008";
                    deptto="srt_008";
                }
                    
                conQ+=" and srt_008 between nvl("+deptFrm+",srt_008) and nvl("+deptto+",srt_008) \n" ;
                
            String rsltQ = "WITH EMP_TTL as (  select emp,  sum(cts) ects, sum(sl_rte*cts) eSlVlu, sum(quot*cts) grossVlu, sum(cmp*cts) bseVlu  \n" + 
            "  , sum(srt_003*cts) aadat, sum(srt_004*cts) coor, sum(srt_005*cts) mgmt, sum(srt_006*cts) loy, sum(srt_007*cts) extra  \n" + 
            "from gt_srch_multi\n" + 
            "where dsp_stt='Z'\n" +conQ+ 
            "group by emp)  , \n" + 
            "EMP_FLG_TTL as (select emp, sum(cts) ects\n" + 
            ", sum(quot*cts) grossVlu, sum(cmp*cts) bseVlu, sum(sl_rte*cts) eslVlu\n" + 
            ", sum(decode(flg, 'Y', sl_rte, 0)*cts) fNetSlVlu, sum(decode(flg, 'F', sl_rte, 0)*cts) fullNetSlVlu   \n" + 
            ", sum(decode(flg, 'N', sl_rte, 0)*cts) fOthSlVlu\n" + 
            ", sum(decode(prp_001, 'Y', decode(flg, 'N', 0, sl_rte*cts), 0)) fNetBlindSlVlu   \n" + 
            ", sum(decode(prp_001, 'Y', decode(flg, 'N', sl_rte*cts, 0), 0)) fOthBlindSlVlu  \n" + 
            "from gt_srch_multi \n" + 
            "where dsp_stt='Z'\n" +conQ+ 
            "group by emp)   \n" + 
            "select t.emp \n" + 
            ", ROUND(f.grossVlu) grossVlu, ROUND(f.grossVlu - f.bseVlu) profit, ROUND(f.eSlVlu) ttlSl   \n" + 
            ", ROUND(f.fNetSlVlu) netSl, ROUND(f.fullNetSlVlu) fullnetSl   \n" + 
            ", ROUND(f.fNetBlindSlVlu) netBlind, ROUND(f.fOthSlVlu) otherSl   \n" + 
            ", ROUND(f.fOthBlindSlVlu) otherBlSl, ROUND(t.aadat) aadat\n" + 
            ", ROUND(t.coor) coor, ROUND(t.mgmt) mgmt, ROUND(t.loy) loy, ROUND(t.extra) extra   \n" + 
            ", ROUND((f.grossVlu*2/100)) est_aadat, ROUND((t.aadat)) act_aadat   \n" + 
            ", ROUND((f.eSlVlu*0.5/100)) est_coor   , ROUND((t.coor)) act_coor   \n" + 
            ", ROUND((f.grossVlu*2/100) - (t.aadat)) aadat_diff, ROUND((f.eSlVlu*0.5/100) - (t.coor)) coor_diff   \n" + 
            "from emp_ttl t, emp_flg_ttl f   \n" + 
            "where t.emp = f.emp   \n" + 
            "order by t.emp"; 
              ArrayList   outLst = db.execSqlLst("Collection", rsltQ, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
                 rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
                HashMap pktPrpMap = new HashMap();
                 pktPrpMap.put("EMP", util.nvl(rs.getString("emp").toUpperCase()));
                 pktPrpMap.put("GROSSVLU",util.nvl(rs.getString("grossVlu")));
                 pktPrpMap.put("PROFIT",util.nvl(rs.getString("profit")));
                 pktPrpMap.put("TTLSL",util.nvl(rs.getString("ttlSl")));
                 pktPrpMap.put("NETSL",util.nvl(rs.getString("netSl")));
                 pktPrpMap.put("FNETSL",util.nvl(rs.getString("fullnetSl")));
                 pktPrpMap.put("NETBLIND",util.nvl(rs.getString("netBlind")));
                 pktPrpMap.put("OTHERSL",util.nvl(rs.getString("otherSl")));
                 pktPrpMap.put("OTHERBLSL",util.nvl(rs.getString("otherBlSl")));
                 pktPrpMap.put("AADAT",util.nvl(rs.getString("aadat")));
                 pktPrpMap.put("CORD",util.nvl(rs.getString("coor")));
                 pktPrpMap.put("MGMT",util.nvl(rs.getString("mgmt")));
                 pktPrpMap.put("LOY",util.nvl(rs.getString("loy")));
                 pktPrpMap.put("EXTRA",util.nvl(rs.getString("extra")));
                 pktPrpMap.put("EST_AADAT",util.nvl(rs.getString("est_aadat")));
                 pktPrpMap.put("ACT_AADAT",util.nvl(rs.getString("act_aadat")));
                 pktPrpMap.put("EST_CORD",util.nvl(rs.getString("est_coor")));
                 pktPrpMap.put("ACT_CORD",util.nvl(rs.getString("act_coor")));
                 pktPrpMap.put("AADAT_DIFF",util.nvl(rs.getString("aadat_diff")));
                 pktPrpMap.put("CORD_DIFF",util.nvl(rs.getString("coor_diff")));
                 pktList.add(pktPrpMap);
             }
            rs.close();
                pst.close();
                
                rsltQ = "WITH EMP_TTL as (  select 'TOTAL' emp,  sum(cts) ects, sum(sl_rte*cts) eSlVlu, sum(quot*cts) grossVlu, sum(cmp*cts) bseVlu  \n" + 
                            "  , sum(srt_003*cts) aadat, sum(srt_004*cts) coor, sum(srt_005*cts) mgmt, sum(srt_006*cts) loy, sum(srt_007*cts) extra  \n" + 
                            "from gt_srch_multi\n" + 
                            "where dsp_stt='Z'\n" +conQ+ 
                            ")  , \n" + 
                            "EMP_FLG_TTL as (select 'TOTAL' emp, sum(cts) ects\n" + 
                            ", sum(quot*cts) grossVlu, sum(cmp*cts) bseVlu, sum(sl_rte*cts) eslVlu\n" + 
                            ", sum(decode(flg, 'Y', sl_rte, 0)*cts) fNetSlVlu, sum(decode(flg, 'F', sl_rte, 0)*cts) fullNetSlVlu   \n" + 
                            ", sum(decode(flg, 'N', sl_rte, 0)*cts) fOthSlVlu\n" + 
                            ", sum(decode(prp_001, 'Y', decode(flg, 'N', 0, sl_rte*cts), 0)) fNetBlindSlVlu   \n" + 
                            ", sum(decode(prp_001, 'Y', decode(flg, 'N', sl_rte*cts, 0), 0)) fOthBlindSlVlu  \n" + 
                            "from gt_srch_multi \n" + 
                            "where dsp_stt='Z'\n" +conQ+ 
                            ")   \n" + 
                            "select 'TOTAL' emp \n" + 
                            ", ROUND(f.grossVlu) grossVlu, ROUND(f.grossVlu - f.bseVlu) profit, ROUND(f.eSlVlu) ttlSl   \n" + 
                            ", ROUND(f.fNetSlVlu) netSl, ROUND(f.fullNetSlVlu) fullnetSl   \n" + 
                            ", ROUND(f.fNetBlindSlVlu) netBlind, ROUND(f.fOthSlVlu) otherSl   \n" + 
                            ", ROUND(f.fOthBlindSlVlu) otherBlSl, ROUND(t.aadat) aadat\n" + 
                            ", ROUND(t.coor) coor, ROUND(t.mgmt) mgmt, ROUND(t.loy) loy, ROUND(t.extra) extra   \n" + 
                            ", ROUND((f.grossVlu*2/100)) est_aadat, ROUND((t.aadat)) act_aadat   \n" + 
                            ", ROUND((f.eSlVlu*0.5/100)) est_coor   , ROUND((t.coor)) act_coor   \n" + 
                            ", ROUND((f.grossVlu*2/100) - (t.aadat)) aadat_diff, ROUND((f.eSlVlu*0.5/100) - (t.coor)) coor_diff   \n" + 
                            "from emp_ttl t, emp_flg_ttl f   \n" + 
                            "where t.emp = f.emp   \n";
                             outLst = db.execSqlLst("Collection", rsltQ, ary);
                               pst = (PreparedStatement)outLst.get(0);
                            rs = (ResultSet)outLst.get(1);
                             while(rs.next()) {
                                HashMap pktPrpMap = new HashMap();
                                 pktPrpMap.put("EMP", util.nvl(rs.getString("emp").toUpperCase()));
                                 pktPrpMap.put("GROSSVLU",util.nvl(rs.getString("grossVlu")));
                                 pktPrpMap.put("PROFIT",util.nvl(rs.getString("profit")));
                                 pktPrpMap.put("TTLSL",util.nvl(rs.getString("ttlSl")));
                                 pktPrpMap.put("NETSL",util.nvl(rs.getString("netSl")));
                                 pktPrpMap.put("FNETSL",util.nvl(rs.getString("fullnetSl")));
                                 pktPrpMap.put("NETBLIND",util.nvl(rs.getString("netBlind")));
                                 pktPrpMap.put("OTHERSL",util.nvl(rs.getString("otherSl")));
                                 pktPrpMap.put("OTHERBLSL",util.nvl(rs.getString("otherBlSl")));
                                 pktPrpMap.put("AADAT",util.nvl(rs.getString("aadat")));
                                 pktPrpMap.put("CORD",util.nvl(rs.getString("coor")));
                                 pktPrpMap.put("MGMT",util.nvl(rs.getString("mgmt")));
                                 pktPrpMap.put("LOY",util.nvl(rs.getString("loy")));
                                 pktPrpMap.put("EXTRA",util.nvl(rs.getString("extra")));
                                 pktPrpMap.put("EST_AADAT",util.nvl(rs.getString("est_aadat")));
                                 pktPrpMap.put("ACT_AADAT",util.nvl(rs.getString("act_aadat")));
                                 pktPrpMap.put("EST_CORD",util.nvl(rs.getString("est_coor")));
                                 pktPrpMap.put("ACT_CORD",util.nvl(rs.getString("act_coor")));
                                 pktPrpMap.put("AADAT_DIFF",util.nvl(rs.getString("aadat_diff")));
                                 pktPrpMap.put("CORD_DIFF",util.nvl(rs.getString("coor_diff")));
                                 pktList.add(pktPrpMap);
                             }
                            rs.close();
                                pst.close();
            
                            itemHdr.add("EMP");
                            itemHdr.add("GROSSVLU");
                            itemHdr.add("PROFIT");
                            itemHdr.add("TTLSL");
                            itemHdr.add("FNETSL");
                            itemHdr.add("NETSL");
                            itemHdr.add("OTHERSL");
                            itemHdr.add("NETBLIND");
                            itemHdr.add("OTHERBLSL");
                            itemHdr.add("LOY");
                            itemHdr.add("EXTRA");
                            itemHdr.add("MGMT");
                            itemHdr.add("ACT_AADAT");
                            itemHdr.add("ACT_CORD");
                            itemHdr.add("EST_AADAT");
                            itemHdr.add("EST_CORD");
                            itemHdr.add("AADAT_DIFF");
                            itemHdr.add("CORD_DIFF"); 
            session.setAttribute("pktListEMP", pktList);
            session.setAttribute("itemHdrEMP", itemHdr);
            req.setAttribute("byrId",byrId);
            req.setAttribute("empId",empId); 
            req.setAttribute("deptFrm",deptFrm);
            req.setAttribute("deptto",deptto); 
            udf.setValue("empId", empId);
            udf.setValue("byrId", byrId);
            udf.setValue("DEPT_1", deptFrm);
            udf.setValue("DEPT_2", deptto);
            req.setAttribute("view", "Y");  
            req.setAttribute("viewFilter", "Y");
            ArrayList empList= srchQuery.getByrList(req,res);
            udf.setByrList(empList); 
            util.updAccessLog(req,res,"Rpt Action", "fetchSaleDisReport end");
            return am.findForward("loadSaleDisReport");
            }
        }
    public ActionForward fetchSaleDisReportByr(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Rpt Action", "fetchSaleDisReportByr start");
            String emp = util.nvl((String)req.getParameter("emp"));    
            ArrayList itemHdr=new ArrayList();
            ResultSet rs = null;
            ArrayList pktList = new  ArrayList();
            ArrayList ary = new ArrayList();
                
            String rsltQ = "WITH EMP_TTL as (select emp,byr,  sum(cts) ects, sum(sl_rte*cts) eSlVlu, sum(quot*cts) grossVlu, sum(cmp*cts) bseVlu  \n" + 
            "  , sum(srt_003*cts) aadat, sum(srt_004*cts) coor, sum(srt_005*cts) mgmt, sum(srt_006*cts) loy, sum(srt_007*cts) extra  \n" + 
            "from gt_srch_multi\n" + 
            "where dsp_stt='Z' and upper(emp) = '"+emp+"'\n" + 
            "group by emp,byr)  , \n" + 
            "EMP_FLG_TTL as (select emp,byr, sum(cts) ects\n" + 
            ", sum(quot*cts) grossVlu, sum(cmp*cts) bseVlu, sum(sl_rte*cts) eslVlu\n" + 
            ", sum(decode(flg, 'Y', sl_rte, 0)*cts) fNetSlVlu, sum(decode(flg, 'F', sl_rte, 0)*cts) fullNetSlVlu   \n" + 
            ", sum(decode(flg, 'N', sl_rte, 0)*cts) fOthSlVlu\n" + 
            ", sum(decode(prp_001, 'Y', decode(flg, 'N', 0, sl_rte*cts), 0)) fNetBlindSlVlu   \n" + 
            ", sum(decode(prp_001, 'Y', decode(flg, 'N', sl_rte*cts, 0), 0)) fOthBlindSlVlu  \n" + 
            "from gt_srch_multi \n" + 
            "where dsp_stt='Z' and  upper(emp) = '"+emp+"'\n" + 
            "group by emp,byr)   \n" + 
            "select t.emp ,t.byr\n" + 
            ", ROUND(f.grossVlu) grossVlu, ROUND(f.grossVlu - f.bseVlu) profit, ROUND(f.eSlVlu) ttlSl   \n" + 
            ", ROUND(f.fNetSlVlu) netSl, ROUND(f.fullNetSlVlu) fullnetSl   \n" + 
            ", ROUND(f.fNetBlindSlVlu) netBlind, ROUND(f.fOthSlVlu) otherSl   \n" + 
            ", ROUND(f.fOthBlindSlVlu) otherBlSl, ROUND(t.aadat) aadat\n" + 
            ", ROUND(t.coor) coor, ROUND(t.mgmt) mgmt, ROUND(t.loy) loy, ROUND(t.extra) extra   \n" + 
            ", ROUND((f.grossVlu*2/100)) est_aadat, ROUND((t.aadat)) act_aadat   \n" + 
            ", ROUND((f.eSlVlu*0.5/100)) est_coor   , ROUND((t.coor)) act_coor   \n" + 
            ", ROUND((f.grossVlu*2/100) - (t.aadat)) aadat_diff, ROUND((f.eSlVlu*0.5/100) - (t.coor)) coor_diff   \n" + 
            "from emp_ttl t, emp_flg_ttl f   \n" + 
            "where t.emp = f.emp   and t.byr = f.byr\n" + 
            "order by t.emp,byr";

            
             ary = new ArrayList();  
              ArrayList outLst = db.execSqlLst("Collection", rsltQ, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
                HashMap pktPrpMap = new HashMap();
                 pktPrpMap.put("EMP", util.nvl(rs.getString("emp").toUpperCase()));
                 pktPrpMap.put("BYR", util.nvl(rs.getString("byr").toUpperCase()));
                 pktPrpMap.put("GROSSVLU",util.nvl(rs.getString("grossVlu")));
                 pktPrpMap.put("PROFIT",util.nvl(rs.getString("profit")));
                 pktPrpMap.put("TTLSL",util.nvl(rs.getString("ttlSl")));
                 pktPrpMap.put("NETSL",util.nvl(rs.getString("netSl")));
                 pktPrpMap.put("FNETSL",util.nvl(rs.getString("fullnetSl")));
                 pktPrpMap.put("NETBLIND",util.nvl(rs.getString("netBlind")));
                 pktPrpMap.put("OTHERSL",util.nvl(rs.getString("otherSl")));
                 pktPrpMap.put("OTHERBLSL",util.nvl(rs.getString("otherBlSl")));
                 pktPrpMap.put("AADAT",util.nvl(rs.getString("aadat")));
                 pktPrpMap.put("CORD",util.nvl(rs.getString("coor")));
                 pktPrpMap.put("MGMT",util.nvl(rs.getString("mgmt")));
                 pktPrpMap.put("LOY",util.nvl(rs.getString("loy")));
                 pktPrpMap.put("EXTRA",util.nvl(rs.getString("extra")));
                 pktPrpMap.put("EST_AADAT",util.nvl(rs.getString("est_aadat")));
                 pktPrpMap.put("ACT_AADAT",util.nvl(rs.getString("act_aadat")));
                 pktPrpMap.put("EST_CORD",util.nvl(rs.getString("est_coor")));
                 pktPrpMap.put("ACT_CORD",util.nvl(rs.getString("act_coor")));
                 pktPrpMap.put("AADAT_DIFF",util.nvl(rs.getString("aadat_diff")));
                 pktPrpMap.put("CORD_DIFF",util.nvl(rs.getString("coor_diff")));
                 pktList.add(pktPrpMap);
             }
            rs.close();
                pst.close();
                
                
                rsltQ = "WITH EMP_TTL as (select emp,'TOTAL' byr,  sum(cts) ects, sum(sl_rte*cts) eSlVlu, sum(quot*cts) grossVlu, sum(cmp*cts) bseVlu  \n" + 
                            "  , sum(srt_003*cts) aadat, sum(srt_004*cts) coor, sum(srt_005*cts) mgmt, sum(srt_006*cts) loy, sum(srt_007*cts) extra  \n" + 
                            "from gt_srch_multi\n" + 
                            "where dsp_stt='Z' and upper(emp) = '"+emp+"'\n" + 
                            "group by emp)  , \n" + 
                            "EMP_FLG_TTL as (select emp,'TOTAL' byr, sum(cts) ects\n" + 
                            ", sum(quot*cts) grossVlu, sum(cmp*cts) bseVlu, sum(sl_rte*cts) eslVlu\n" + 
                            ", sum(decode(flg, 'Y', sl_rte, 0)*cts) fNetSlVlu, sum(decode(flg, 'F', sl_rte, 0)*cts) fullNetSlVlu   \n" + 
                            ", sum(decode(flg, 'N', sl_rte, 0)*cts) fOthSlVlu\n" + 
                            ", sum(decode(prp_001, 'Y', decode(flg, 'N', 0, sl_rte*cts), 0)) fNetBlindSlVlu   \n" + 
                            ", sum(decode(prp_001, 'Y', decode(flg, 'N', sl_rte*cts, 0), 0)) fOthBlindSlVlu  \n" + 
                            "from gt_srch_multi \n" + 
                            "where dsp_stt='Z' and  upper(emp) = '"+emp+"'\n" + 
                            "group by emp)   \n" + 
                            "select t.emp ,'TOTAL' byr\n" + 
                            ", ROUND(f.grossVlu) grossVlu, ROUND(f.grossVlu - f.bseVlu) profit, ROUND(f.eSlVlu) ttlSl   \n" + 
                            ", ROUND(f.fNetSlVlu) netSl, ROUND(f.fullNetSlVlu) fullnetSl   \n" + 
                            ", ROUND(f.fNetBlindSlVlu) netBlind, ROUND(f.fOthSlVlu) otherSl   \n" + 
                            ", ROUND(f.fOthBlindSlVlu) otherBlSl, ROUND(t.aadat) aadat\n" + 
                            ", ROUND(t.coor) coor, ROUND(t.mgmt) mgmt, ROUND(t.loy) loy, ROUND(t.extra) extra   \n" + 
                            ", ROUND((f.grossVlu*2/100)) est_aadat, ROUND((t.aadat)) act_aadat   \n" + 
                            ", ROUND((f.eSlVlu*0.5/100)) est_coor   , ROUND((t.coor)) act_coor   \n" + 
                            ", ROUND((f.grossVlu*2/100) - (t.aadat)) aadat_diff, ROUND((f.eSlVlu*0.5/100) - (t.coor)) coor_diff   \n" + 
                            "from emp_ttl t, emp_flg_ttl f   \n" + 
                            "where t.emp = f.emp   and t.byr = f.byr\n" + 
                            "order by t.emp,byr";

                            
                             ary = new ArrayList();  
                           outLst = db.execSqlLst("Collection", rsltQ, ary);
                            pst = (PreparedStatement)outLst.get(0);
                               rs = (ResultSet)outLst.get(1);
                             while(rs.next()) {
                                HashMap pktPrpMap = new HashMap();
                                 pktPrpMap.put("EMP", util.nvl(rs.getString("emp").toUpperCase()));
                                 pktPrpMap.put("BYR", util.nvl(rs.getString("byr").toUpperCase()));
                                 pktPrpMap.put("GROSSVLU",util.nvl(rs.getString("grossVlu")));
                                 pktPrpMap.put("PROFIT",util.nvl(rs.getString("profit")));
                                 pktPrpMap.put("TTLSL",util.nvl(rs.getString("ttlSl")));
                                 pktPrpMap.put("NETSL",util.nvl(rs.getString("netSl")));
                                 pktPrpMap.put("FNETSL",util.nvl(rs.getString("fullnetSl")));
                                 pktPrpMap.put("NETBLIND",util.nvl(rs.getString("netBlind")));
                                 pktPrpMap.put("OTHERSL",util.nvl(rs.getString("otherSl")));
                                 pktPrpMap.put("OTHERBLSL",util.nvl(rs.getString("otherBlSl")));
                                 pktPrpMap.put("AADAT",util.nvl(rs.getString("aadat")));
                                 pktPrpMap.put("CORD",util.nvl(rs.getString("coor")));
                                 pktPrpMap.put("MGMT",util.nvl(rs.getString("mgmt")));
                                 pktPrpMap.put("LOY",util.nvl(rs.getString("loy")));
                                 pktPrpMap.put("EXTRA",util.nvl(rs.getString("extra")));
                                 pktPrpMap.put("EST_AADAT",util.nvl(rs.getString("est_aadat")));
                                 pktPrpMap.put("ACT_AADAT",util.nvl(rs.getString("act_aadat")));
                                 pktPrpMap.put("EST_CORD",util.nvl(rs.getString("est_coor")));
                                 pktPrpMap.put("ACT_CORD",util.nvl(rs.getString("act_coor")));
                                 pktPrpMap.put("AADAT_DIFF",util.nvl(rs.getString("aadat_diff")));
                                 pktPrpMap.put("CORD_DIFF",util.nvl(rs.getString("coor_diff")));
                                 pktList.add(pktPrpMap);
                             }
                            rs.close();
                         pst.close();
            
                            itemHdr.add("BYR");
                            itemHdr.add("GROSSVLU");
                            itemHdr.add("PROFIT");
                            itemHdr.add("TTLSL");
                            itemHdr.add("FNETSL");
                            itemHdr.add("NETSL");
                            itemHdr.add("OTHERSL");
                            itemHdr.add("NETBLIND");
                            itemHdr.add("OTHERBLSL");
                            itemHdr.add("LOY");
                            itemHdr.add("EXTRA");
                            itemHdr.add("MGMT");
                            itemHdr.add("ACT_AADAT");
                            itemHdr.add("ACT_CORD");
                            itemHdr.add("EST_AADAT");
                            itemHdr.add("EST_CORD");
                            itemHdr.add("AADAT_DIFF");
                            itemHdr.add("CORD_DIFF");
            
            session.setAttribute("pktListBYR", pktList);
            session.setAttribute("itemHdrBYR", itemHdr);
            util.updAccessLog(req,res,"Rpt Action", "fetchSaleDisReportByr end");
            return am.findForward("fetchSaleDisReportByr");
            }
        }
    
    public ActionForward loadSaleBasePrc(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Rpt Action", "loadSaleBasePrc start");
            ReportForm udf = (ReportForm)af;
            udf.resetALL();
            util.updAccessLog(req,res,"Rpt Action", "loadSaleBasePrc end");
            return am.findForward("loadSaleBasePrc");
            }
        }
    public ActionForward fetchSaleBasePrc(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Rpt Action", "fetchSaleBasePrc start");
            
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("BASE_PRC_REPORT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("BASE_PRC_REPORT");
            allPageDtl.put("BASE_PRC_REPORT",pageDtl);
            }
            info.setPageDetails(allPageDtl); 
            
            ReportForm udf = (ReportForm)af;
            String frmdte=util.nvl((String)udf.getValue("frmdte")); 
            String todte=util.nvl((String)udf.getValue("todte"));            
            ArrayList params = new ArrayList();
            ArrayList pktList = new  ArrayList();
            String conQdte=" and trunc(j.dte) >= trunc(sysdate) - 365 ";
            if(!frmdte.equals("") && !todte.equals(""))
            conQdte = " and trunc(j.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
            if(frmdte.equals("") && !todte.equals(""))
            conQdte = " and trunc(j.dte) between to_date('"+todte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
            if(!frmdte.equals("") && todte.equals(""))
            conQdte = " and trunc(j.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+frmdte+"' , 'dd-mm-yyyy') ";

            db.execUpd("delete", "delete from gt_srch_rslt", new ArrayList());
            
            String srchQ = " insert into gt_srch_rslt(sl_idn, stk_idn, cts, sl_rte, vlu_usd, emp, byr) "+
                " select j.idn, j.mstk_idn, j.cts, round(j.quot/s.exh_rte), round(j.quot/s.exh_rte)*j.cts, s.emp_nme, s.byr "+
                " from jansal j, sal_v s, mstk m "+
                " where 1 = 1 "+
                " and j.idn = s.idn and m.idn = j.mstk_idn and m.pkt_ty = 'NR' "+conQdte+
                " and j.stt not like '%RT' and j.stt <> 'CL' and j.quot = nvl(j.fnl_sal, j.quot) ";     
                 
                 
            int ct = db.execUpd("insert gt",srchQ, params);
            if(ct>0) {
            String rsltQ = " select emp, byr, count(*) qty, sum(cts) cts, trunc(sum(vlu_usd),2) vlu from gt_srch_rslt group by emp, byr order by emp, byr, 5 desc " ;
              ArrayList outLst = db.execSqlLst("search Result", rsltQ, params);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
                HashMap pktPrpMap = new HashMap();
                 pktPrpMap.put("emp", util.nvl(rs.getString("emp")));
                 pktPrpMap.put("byr",util.nvl(rs.getString("byr")));
                 pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                 pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                 pktPrpMap.put("vlu",util.nvl(rs.getString("vlu")));
                 pktList.add(pktPrpMap);
             }
            rs.close();
            pst.close();
            }
            udf.setPktList(pktList);
            req.setAttribute("pktList", pktList); 
            req.setAttribute("view", "Y"); 
            util.updAccessLog(req,res,"Rpt Action", "fetchSaleBasePrc end");
            return am.findForward("loadSaleBasePrc");
            }
        }
    
    
    public ActionForward createXLSaleDsc(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
                util.updAccessLog(req,res,"Rpt Action", "createXLSaleDsc start");
            String typ = util.nvl((String)req.getParameter("typ"));
            ExcelUtil xlUtil = new ExcelUtil();
            xlUtil.init(db, util, info);
            OutputStream out = res.getOutputStream();
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "createXLSaleDsc"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
            ArrayList pktList=new ArrayList();
            ArrayList itemHdr=new ArrayList();
            pktList = (ArrayList)session.getAttribute("pktList"+typ);
            itemHdr = (ArrayList)session.getAttribute("itemHdr"+typ);
            HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            hwb.write(out);
            out.flush();
            out.close();
                util.updAccessLog(req,res,"Rpt Action", "createXLSaleDsc end");
            return null;
            }
        }

    public ActionForward SaleDscPktList(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            
            util.updAccessLog(req,res,"Rpt Action", "SaleDscPktList start");
            ArrayList pktList = new ArrayList();
            ArrayList itemHdr = new ArrayList();
            String empId =util.nvl((String)req.getParameter("empId"));
            String byrId = util.nvl((String)req.getParameter("byrId"));
            String deptFrm =util.nvl((String)req.getParameter("deptFrm"));
            String deptto = util.nvl((String)req.getParameter("deptto"));
            int sr = 1;
            ResultSet rs=null;
            ArrayList ary = new ArrayList();  
            String empNm="",conQ="";
            itemHdr.add("Sr");
            itemHdr.add("Status");
            itemHdr.add("EMP");
            itemHdr.add("BYR");
            itemHdr.add("PacketCode");
                
            if(empId.equals("") || empId.equals("0"))
            empId="";
            if(byrId.equals("") || byrId.equals("0"))
            byrId="";
            
            try {          
            if(!empId.equals("")){
                String empQ="select byr.GET_NM(?) empnm from dual";
                ary = new ArrayList();  
                ary.add(empId);
              ArrayList outLst = db.execSqlLst("empQ", empQ, ary); 
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
                while(rs.next()) {
                  empNm=util.nvl(rs.getString("empnm"));
                }
                rs.close();
                pst.close();
            }
                            
                 ary = new ArrayList(); 
                 if(!empNm.equals(""))
                 conQ+=" and upper(emp) = upper('"+empNm+"') \n";
                            
                 if(!byrId.equals("")){
                 conQ+=" and rln_idn = ? \n"; 
                 ary.add(byrId);
                 }
                            
           if(deptFrm.equals("") || deptto.equals("")){
                    deptFrm="srt_008";
                    deptto="srt_008";
           }    
            conQ+=" and srt_008 between nvl("+deptFrm+",srt_008) and nvl("+deptto+",srt_008) \n" ;  
                
            String srchQ="select vnm , cts ,stt , emp , byr,cmp , prte, mrte, sl_rte,sl_rte*cts slvlu ,prp_001,srt_003, srt_004, srt_005, srt_006, srt_007,prp_008 " ;
                itemHdr.add("RTE");    
                itemHdr.add("SL_RTE");
                itemHdr.add("SL_VLU");
                itemHdr.add("BLIND_YN");
                itemHdr.add("AADAT_VLU");
                itemHdr.add("CORD_VLU");
                itemHdr.add("MGMT");
                itemHdr.add("LOY");
                itemHdr.add("EXTRA");
                itemHdr.add("DEPT");
                
                String rsltQ = srchQ +" from gt_srch_multi where dsp_stt='Z' "+conQ+" order by emp , byr, srch_id, sk1 ";
              ArrayList outLst = db.execSqlLst(" Sale Info", rsltQ , ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
                while(rs.next()) {
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("Sr", String.valueOf(sr++));
                pktPrpMap.put("Status", util.nvl(rs.getString("stt")));
                pktPrpMap.put("EMP", util.nvl(rs.getString("emp")));
                pktPrpMap.put("BYR", util.nvl(rs.getString("byr")));
                pktPrpMap.put("PacketCode", util.nvl(rs.getString("vnm")));
                pktPrpMap.put("RTE", util.nvl(rs.getString("cmp")));
                pktPrpMap.put("SL_RTE", util.nvl(rs.getString("sl_rte")));
                pktPrpMap.put("SL_VLU", util.nvl(rs.getString("slvlu")));
                pktPrpMap.put("BLIND_YN", util.nvl(rs.getString("prp_001")));
                pktPrpMap.put("AADAT_VLU", util.nvl(rs.getString("srt_003")));
                pktPrpMap.put("CORD_VLU", util.nvl(rs.getString("srt_004")));
                pktPrpMap.put("MGMT", util.nvl(rs.getString("srt_005")));
                pktPrpMap.put("LOY", util.nvl(rs.getString("srt_006")));
                pktPrpMap.put("EXTRA", util.nvl(rs.getString("srt_007")));
                pktPrpMap.put("DEPT", util.nvl(rs.getString("prp_008")));
                pktList.add(pktPrpMap);
            
            }
                    rs.close();
                pst.close();
            } catch (SQLException sqle) {

                    sqle.printStackTrace();
            }
            
                
                util.updAccessLog(req,res,"Rpt Action", "SaleDscPktList end");
                session.setAttribute("pktList", pktList);
                session.setAttribute("itemHdr",itemHdr);
                return am.findForward("pktDtl");
                
            }
        }


    public ActionForward loadcust(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "loadcust start");
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
        ReportForm udf = (ReportForm)af;
        udf.resetALL();
        util.getmonthyr();
        ArrayList yrList = (info.getYrList() == null)?new ArrayList():(ArrayList)info.getYrList();
        ArrayList monthList = (info.getMonthList() == null)?new ArrayList():(ArrayList)info.getMonthList();
        udf.setYrList(yrList);
        udf.setMonthList(monthList);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("REPEAT_CUSTOMER");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("REPEAT_CUSTOMER");
        allPageDtl.put("REPEAT_CUSTOMER",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        util.saleperson();
            if(cnt.equals("hk"))
            util.groupcompany();
            util.updAccessLog(req,res,"Rpt Action", "loadcust end");
        return am.findForward("loadcust");
        }
    }

    public ActionForward fetchcust(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "fetchcust start");
        ReportForm udf = (ReportForm)af;
        ArrayList ary = new ArrayList();
        ArrayList byrLst = new ArrayList();
        String count = "";
        String dept = util.nvl((String)udf.getValue("dept"));
        String snmeIdn = util.nvl((String)udf.getValue("styp"));
        String groupCIdn = util.nvl((String)udf.getValue("group"));
        String salefrmon = util.nvl((String)udf.getValue("salefrmon"));
        String salefryr = util.nvl((String)udf.getValue("salefryr"));
        String saletomon = util.nvl((String)udf.getValue("saletomon"));
        String saletoyr = util.nvl((String)udf.getValue("saletoyr"));
        String compfrmon = util.nvl((String)udf.getValue("compfrmon"));
        String compfryr = util.nvl((String)udf.getValue("compfryr"));
        String comptomon = util.nvl((String)udf.getValue("comptomon"));
        String comptoyr = util.nvl((String)udf.getValue("comptoyr"));
        String oldsalefrdte = salefrmon + "-" + salefryr;
        String oldsaletodte = saletomon + "-" + saletoyr;
        String newcompfrdte = compfrmon + "-" + compfryr;
        String newcomptodte = comptomon + "-" + comptoyr;
        db.execUpd("delete", "delete from Gt_Pkt", new ArrayList());
        ary.add(dept);
        ary.add(snmeIdn);
        ary.add(groupCIdn);
        ary.add(oldsalefrdte);
        ary.add(oldsaletodte);
        ary.add(newcompfrdte);
        ary.add(newcomptodte);
        int ct =
            db.execCall("Repeat Customer", "report_pkg.REPEAT_CUSTOMER(pDept => ? ,pSalExIdn => ? ,pGrpIdn => ? ,pOldDteFr => ?,pOldDteTo => ?,pNewDteFr => ?,pNewDteTo => ? )", ary);
        String dataQ =
            "Select b.nme byr,decode(a.flg,'NR','red','green') color From Gt_Pkt A,NME_V B Where A.mstk_idn=b.nme_idn order by a.flg desc,b.nme";
          ArrayList outLst = db.execSqlLst("Repeat Customer", dataQ, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            HashMap data = new HashMap();
            data.put("BYR", util.nvl((String)rs.getString("byr")));
            data.put("COLOR", util.nvl((String)rs.getString("color")));
            byrLst.add(data);
        }
            rs.close();
            pst.close();
        String countQ = "select count(*) cnt from Gt_Pkt where flg='R'";
          outLst = db.execSqlLst("count", countQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            count = util.nvl((String)rs.getString("cnt"));
        }
            rs.close();
            pst.close();
        session.setAttribute("byrLstRpt", byrLst);
        req.setAttribute("view", "Y");
        session.setAttribute("countrpt", count);
            util.updAccessLog(req,res,"Rpt Action", "fetchcust end");
        return am.findForward("loadcust");
        }
    }

    public ActionForward loadfeedbackRpt(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "loadfeedbackRpt start");
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
        ReportForm udf = (ReportForm)af;
        udf.resetALL();
        util.getmonthyr();
        ArrayList yrList = (info.getYrList() == null)?new ArrayList():(ArrayList)info.getYrList();
        ArrayList monthList = (info.getMonthList() == null)?new ArrayList():(ArrayList)info.getMonthList();
        udf.setYrList(yrList);
        udf.setMonthList(monthList);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_SRCH");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("CONTACT_SRCH");
        allPageDtl.put("CONTACT_SRCH",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            ArrayList empList = new ArrayList();
            String sql = " select nme_idn, nme from nme_v a where typ = 'EMPLOYEE' " +
            " and exists (select 1 from mnme a1, nmerel b where b.nme_idn = a1.nme_idn and a.nme_idn = a1.emp_idn and b.vld_dte is null and b.flg = 'CNF') " +
            " order by nme";
          ArrayList outLst = db.execSqlLst("stkDtl", sql, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){ 
                MNme emp = new MNme();
                emp.setEmp_idn(rs.getString("nme_idn"));
                emp.setEmp_nme(rs.getString("nme"));
                empList.add(emp);
            }
            rs.close();
            pst.close();
            udf.setEmpList(empList);
        util.updAccessLog(req,res,"Rpt Action", "loadfeedbackRpt end");
        return am.findForward("loadfeedbackRpt");
        }
    }


    public ActionForward fetchfeedbackRpt(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "fetchfeedbackRpt start");
        ReportForm udf = (ReportForm)af;
        ArrayList ary = new ArrayList();
        String[] empLst=udf.getEmpLst();
        String salefrmon = util.nvl((String)udf.getValue("salefrmon"));
        String salefryr = util.nvl((String)udf.getValue("salefryr"));
        String summary = util.nvl((String)udf.getValue("summary"));
        String oldsalefrdte = salefrmon + "-" + salefryr;
        String conQ="",snmeIdn="";
        ArrayList bryLstFeedbk=new ArrayList();
        ArrayList empLstFeedbk=new ArrayList();
        HashMap dataDtlFeedbk=new HashMap();
        String prvEmp = "";
        for(int i=0;i<empLst.length;i++){
                snmeIdn+=","+empLst[i];
        }
        snmeIdn=snmeIdn.replaceFirst("\\,", "");
        if(!snmeIdn.equals("") && !snmeIdn.equals("0")){
            conQ=" and df.emp_idn in("+snmeIdn+")";
        }
        
        if(summary.equals("")){
        String dataQ =
            "select ev.nme_idn,ev.nme emp,nm.nme_idn byrnme_idn,nm.nme byr,to_char(df.dte, 'dd') frm \n" + 
            "from dly_feedback df,nme_v nm,emp_v ev\n" + 
            "where \n" + 
            "df.nme_idn=nm.nme_idn and df.emp_idn=ev.nme_idn \n" +conQ+
            " and trunc(df.dte) between to_date('01-"+oldsalefrdte+"', 'dd-MON-rrrr') \n" + 
            "and last_day(to_date('01-"+oldsalefrdte+"', 'dd-MON-rrrr'))\n" + 
            "order by ev.nme_idn,ev.nme,nm.nme_idn,nm.nme";
          ArrayList outLst = db.execSqlLst("Repeat Customer", dataQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String emp=util.nvl((String)rs.getString("emp"));
            String byr=util.nvl((String)rs.getString("byr"));
            
            if(prvEmp.equals(""))
                prvEmp= emp;
            if(!prvEmp.equals(emp)){
                dataDtlFeedbk.put(prvEmp+"_BYR", bryLstFeedbk);   
                bryLstFeedbk=new ArrayList();
                prvEmp= emp;
            }
            dataDtlFeedbk.put(emp+"_"+byr+"_"+util.nvl((String)rs.getString("frm")), "Y");
            dataDtlFeedbk.put(byr, util.nvl((String)rs.getString("byrnme_idn")));
            dataDtlFeedbk.put(emp, util.nvl((String)rs.getString("nme_idn")));
            if(!empLstFeedbk.contains(emp))
            empLstFeedbk.add(emp);
            if(!bryLstFeedbk.contains(byr))
            bryLstFeedbk.add(byr);
        }
            rs.close();
            pst.close();
            if(!prvEmp.equals(""))
            dataDtlFeedbk.put(prvEmp+"_BYR", bryLstFeedbk);   
            
        req.setAttribute("empLstFeedbk", empLstFeedbk);
        req.setAttribute("dataDtlFeedbk", dataDtlFeedbk);
        req.setAttribute("rpt", "DETAIL");
        }else{
            String dataQ =
                "select count(*) cnt,ev.nme emp,comm_mode\n" + 
                "from dly_feedback df,emp_v ev\n" + 
                "where \n" + 
                "df.emp_idn=ev.nme_idn \n" +conQ+
                " and trunc(df.dte) between to_date('01-"+oldsalefrdte+"', 'dd-MON-rrrr') \n" + 
                "and last_day(to_date('01-"+oldsalefrdte+"', 'dd-MON-rrrr'))\n" + 
                "group by ev.nme,comm_mode";
          ArrayList outLst = db.execSqlLst("Repeat Customer", dataQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String emp=util.nvl((String)rs.getString("emp"));
                dataDtlFeedbk.put(emp+"_"+util.nvl((String)rs.getString("comm_mode")), util.nvl((String)rs.getString("cnt")));
                if(!empLstFeedbk.contains(emp))
                empLstFeedbk.add(emp);
            }
            rs.close();
            pst.close();
            req.setAttribute("empLstFeedbk", empLstFeedbk);
            req.setAttribute("dataDtlFeedbk", dataDtlFeedbk);   
            req.setAttribute("rpt", "SUMMARY");
        }
        req.setAttribute("view", "Y");
        udf.setValue("summary","");
        udf.setValue("srch","");
        util.updAccessLog(req,res,"Rpt Action", "fetchfeedbackRpt end");
        return am.findForward("loadfeedbackRpt");
        }
    }
    
    
    public ActionForward loadakhi(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "loadakhi start");
        ReportForm udf = (ReportForm)af;
        udf.reset();
        util.updAccessLog(req,res,"Rpt Action", "loadakhi end");
        return am.findForward("fetchakhi");
        }
    }

    public ActionForward fetchakhi(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "fetchakhi start");
        ReportForm udf = (ReportForm)af;
        GenericInterface genericInt = new GenericImpl();
        ArrayList ary = new ArrayList();
        String memo = util.nvl((String)udf.getValue("memo"));
        String startfrmdte=util.nvl((String)udf.getValue("startfrmdte")); 
        String starttodte=util.nvl((String)udf.getValue("starttodte"));
        String compfrdte=util.nvl((String)udf.getValue("compfrdte"));
        String comptodte=util.nvl((String)udf.getValue("comptodte"));
        ArrayList memoList = new ArrayList();
        HashMap dataDtl = new HashMap();
        String delQ = " Delete from Gt_Pkt_Scan ";
        int ct = db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct = db.execUpd(" Del Old Pkts ", " Delete from Gt_Srch_Rslt ", new ArrayList());
        ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "akhiViewLst","AKHI_VW");
        HashMap dbinfo = info.getDmbsInfoLst();
        String memoval = (String)dbinfo.get("MEMO");
        String crtval = "CRTWT";
        String mfgcrtval = "MFG_CTS";
        String prival = "FA_PRI";
        String mfgval = "MFG_PRI";
        ResultSet rs=null;
        int indexMemo = vWPrpList.indexOf(memoval) + 1;
        int indexCrtwt = vWPrpList.indexOf(crtval) + 1;
        int indexMfgCrtwt = vWPrpList.indexOf(mfgcrtval) + 1;
        int indexPri = vWPrpList.indexOf(prival) + 1;
        int indexmfg = vWPrpList.indexOf(mfgval) + 1;
        String memoPrp = util.prpsrtcolumn("prp", indexMemo);
        String crtwtPrp = util.prpsrtcolumn("prp", indexCrtwt);
        String mfgcrtwtPrp = util.prpsrtcolumn("prp", indexMfgCrtwt);
        String priPrp = util.prpsrtcolumn("prp", indexPri);
        String mfgPrp = util.prpsrtcolumn("prp", indexmfg);
        String memoSrt = util.prpsrtcolumn("srt", indexMemo);
        String priSrt = util.prpsrtcolumn("srt", indexPri);
        String crtwtSrt = util.prpsrtcolumn("srt", indexCrtwt);
        String mfgcrtwtSrt = util.prpsrtcolumn("srt", indexMfgCrtwt);
        String mfgSrt = util.prpsrtcolumn("srt", indexmfg);
        if((!startfrmdte.equals("") && !starttodte.equals("")) || (!compfrdte.equals("") && !comptodte.equals(""))){
            String mlotQ=" select a.dsc memo from mlot a where 1=1 ";
            if(!startfrmdte.equals("") && !starttodte.equals("")){
            mlotQ = mlotQ+" and trunc(a.dte) between to_date('"+startfrmdte+"' , 'dd-mm-yyyy') and to_date('"+starttodte+"' , 'dd-mm-yyyy') ";
            }  
            if(!compfrdte.equals("") && !comptodte.equals("")){
            mlotQ = mlotQ+" and trunc(a.comp_dte) between to_date('"+compfrdte+"' , 'dd-mm-yyyy') and to_date('"+comptodte+"' , 'dd-mm-yyyy') ";
            }
            rs = db.execSql("Fetch Mlot", mlotQ,  new ArrayList());
            while(rs.next()){
                memo+=","+util.nvl((String)rs.getString("memo"));
            }
            rs.close();
            memo = memo.replaceFirst("\\,", "");
        }
        if (memo.length() > 0) {
            String memoLst = util.getVnm(memo);
            memoLst = memoLst.replaceAll(",", "");
            memoLst = memoLst.replaceAll("''", ",");
            if(!memoLst.equals("")){
            String insScanPkt = " insert into gt_pkt_scan select * from TABLE(PARSE_TO_TBL(" + memoLst + "))";
            ct = db.execUpd("Insert", insScanPkt, ary);
            }
            ary = new ArrayList();
            ary.add("AKHI_VW");
            ct = db.execCall(" Gt ", "Report_Pkg.AKHI_AVG(pMdl => ?)", ary);

            String sqlQ = "     Select A.prp_001,Nvl(B.acc_av,0) aakhasrt\n" + 
            "     , To_Char(Sum(A.Cts),'99999990.00') Cts\n" + 
            "     , Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts)) Aakhaold,\n" + 
            "    Round(Sum(Nvl(A.Cts, 0)*Nvl(B.Acc_Av,0))) aakhasrtvlu,\n" + 
            "    Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts))*Sum(A.Cts) Aakhaoldvlu\n" + 
            "     From Gt_Srch_Rslt A,Mlot B\n" + 
            "     Where A.prp_001=B.Dsc and a.cts > 0 \n" + 
            "     Group By A.Prp_001,B.Acc_Av\n" + 
            "     Union\n" + 
            "     Select 'TOTAL',Round(Sum(Nvl(A.Cts, 0)*Nvl(B.acc_av,0))/Sum(A.Cts)) aakhasrt\n" + 
            "     , to_char(sum(a.cts),'99999990.00') Cts\n" + 
            "     , Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts)) Aakhaold,\n" + 
            "    Round(Sum(Nvl(A.Cts, 0)*Nvl(B.Acc_Av,0))) aakhasrtvlu,\n" + 
            "    Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts))*Sum(A.Cts) Aakhaoldvlu\n" + 
            "     From Gt_Srch_Rslt A,Mlot B\n" + 
            "     Where A.prp_001=B.Dsc and Nvl(A.Cts, 0) > 0 and Nvl(A.Prp_004, 0) > 0 \n" + 
            "     order by 1";
          ArrayList outLst = db.execSqlLst("Dtl", sqlQ, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String memoVal = util.nvl(rs.getString(memoPrp));
                dataDtl.put(memoVal + "_CTS", util.nvl(rs.getString("Cts")));
                dataDtl.put(memoVal + "_AAKHAOLD", util.nvl(rs.getString("aakhaold")));
                dataDtl.put(memoVal + "_AAKHASRT", util.nvl(rs.getString("aakhasrt")));
                dataDtl.put(memoVal + "_AAKHAOLDVLU", util.nvl(rs.getString("Aakhaoldvlu")));
                dataDtl.put(memoVal + "_AAKHASRTVLU", util.nvl(rs.getString("aakhasrtvlu")));
                if (!memoList.contains(memoVal) && !memoVal.equals("TOTAL"))
                    memoList.add(memoVal);
            }
            rs.close();
            pst.close();
            
            sqlQ ="Select A.Prp_001,To_Char(Sum(A.Cts),'99999990.00') Cts\n" + 
            "   , Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts)) Mnjold\n" + 
            "   , Round((Sum(Nvl(A.Prp_002, 0)*Nvl(A.Prp_005, 0)) - sum(nvl(prp_008, 0)))/Sum(A.Prp_002))  Mnjnew,\n" + 
            "  Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts))*Sum(A.Cts) Mnjoldvlu,\n" + 
            "  Round((Sum(Nvl(A.Prp_002, 0)*Nvl(A.Prp_005, 0)) - sum(nvl(prp_008, 0)))/Sum(A.Prp_002)*Sum(A.Cts)) Mnjnewvlu\n" + 
            "--   Round(Sum(Nvl(A.Prp_002, 0)*Nvl(A.Prp_005, 0))/Sum(A.Prp_002))*Sum(A.Cts) Mnjnewvlu\n" + 
            "   From Gt_Srch_Rslt A\n" + 
            "   Where  Nvl(A.Cts, 0) > 0  and Nvl(A.Prp_004, 0) > 0 and prp_006='96-UP-GIA'\n" + 
            "   Group By A.Prp_001\n" + 
            "   Union\n" + 
            "   Select 'TOTAL',To_Char(Sum(A.Cts),'99999990.00') Cts\n" + 
            "   , Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts)) Mnjold\n" + 
            "   , Round((Sum(Nvl(A.Prp_002, 0)*Nvl(A.Prp_005, 0)) - sum(nvl(prp_008, 0)))/Sum(A.Prp_002))  Mnjnew,\n" + 
            "  Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts))*Sum(A.Cts) Mnjoldvlu,\n" + 
            "  Round((Sum(Nvl(A.Prp_002, 0)*Nvl(A.Prp_005, 0)) - sum(nvl(prp_008, 0)))/Sum(A.Prp_002)*Sum(A.Cts)) Mnjnewvlu\n" + 
            "   From Gt_Srch_Rslt A\n" + 
            "   Where Nvl(A.Cts, 0) > 0  and Nvl(A.Prp_004, 0) > 0 and prp_006='96-UP-GIA'\n" + 
            "   Order By 1";
         
           outLst = db.execSqlLst("Dtl", sqlQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
                        while (rs.next()) {
                            String memoVal = util.nvl(rs.getString(memoPrp));
                            dataDtl.put(memoVal + "_96-UP-GIA_CTS", util.nvl(rs.getString("Cts")));
                            dataDtl.put(memoVal + "_96-UP-GIA_MNJOLD", util.nvl(rs.getString("Mnjold")));
                            dataDtl.put(memoVal + "_96-UP-GIA_MNJNEW", util.nvl(rs.getString("Mnjnew")));
                            dataDtl.put(memoVal + "_96-UP-GIA_MNJOLDVLU", util.nvl(rs.getString("Mnjoldvlu")));
                            dataDtl.put(memoVal + "_96-UP-GIA_MNJNEWVLU", util.nvl(rs.getString("Mnjnewvlu")));
            }
            rs.close();
            pst.close();
            String bakiQ = "Select Memo,round(trf_vlu/asrt_vlu*100) Bakipct\n" + 
            "From (Select Prp_001 Memo,sum(decode(b.grp2, 'SEND', nvl(prp_003, 0)*nvl(prp_004, 0), 0)) TRF_VLU,\n" + 
            "sum(nvl(prp_003, 0)*nvl(prp_004, 0)) asrt_vlu\n" + 
            "From Gt_Srch_Rslt A, Df_Stk_Stt B Where A.Stt = B.Stt  and  Nvl(A.Cts, 0) > 0  and Nvl(A.Prp_004, 0) > 0  and prp_006='96-UP-GIA' Group By Prp_001)\n" + 
            "Union\n" + 
            "Select memo,round(trf_vlu/asrt_vlu*100) Bakipct\n" + 
            "From (Select 'TOTAL' Memo,sum(decode(b.grp2, 'SEND', nvl(prp_003, 0)*nvl(prp_004, 0), 0)) TRF_VLU,\n" + 
            "Sum(Nvl(Prp_003, 0)*Nvl(Prp_004, 0)) Asrt_Vlu\n" + 
            "From Gt_Srch_Rslt A, DF_STK_STT b where a.stt = b.stt  and  Nvl(A.Cts, 0) > 0  and Nvl(A.Prp_004, 0) > 0 and prp_006='96-UP-GIA')\n" + 
            "Order By 1";
          outLst = db.execSqlLst("Dtl", bakiQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String memoVal = util.nvl(rs.getString("memo"));
                dataDtl.put(memoVal + "_BKPER", util.nvl(rs.getString("Bakipct")));
            }
            rs.close();
            pst.close();
            session.setAttribute("dataDtl", dataDtl);
            req.setAttribute("memoList", memoList);
            req.setAttribute("View", "Y");
        }
            util.updAccessLog(req,res,"Rpt Action", "fetchakhi end");
        return am.findForward("fetchakhi");
        }
    }

    public ActionForward fetchakhiSH(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "fetchakhi start");
        ReportForm udf = (ReportForm)af;
        GenericInterface genericInt = new GenericImpl();
        ArrayList ary = new ArrayList();
        String memo = util.nvl((String)req.getParameter("memoIdn"));
        ArrayList shapeList = new ArrayList();
        ArrayList memoList = new ArrayList();
        HashMap dataDtl = new HashMap();
        ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "akhiViewLst","AKHI_VW");
        HashMap dbinfo = info.getDmbsInfoLst();
        String memoval = (String)dbinfo.get("MEMO");
        String crtval = "CRTWT";
        String mfgcrtval = "MFG_CTS";
        String prival = "FA_PRI";
        String mfgval = "MFG_PRI";
        String shapeval = (String)dbinfo.get("SHAPE");
        ResultSet rs=null;
        int indexMemo = vWPrpList.indexOf(memoval) + 1;
        int indexCrtwt = vWPrpList.indexOf(crtval) + 1;
        int indexMfgCrtwt = vWPrpList.indexOf(mfgcrtval) + 1;
        int indexPri = vWPrpList.indexOf(prival) + 1;
        int indexmfg = vWPrpList.indexOf(mfgval) + 1;
        int indexsh = vWPrpList.indexOf(shapeval) + 1;
        String memoPrp = util.prpsrtcolumn("prp", indexMemo);
        String crtwtPrp = util.prpsrtcolumn("prp", indexCrtwt);
        String mfgcrtwtPrp = util.prpsrtcolumn("prp", indexMfgCrtwt);
        String priPrp = util.prpsrtcolumn("prp", indexPri);
        String mfgPrp = util.prpsrtcolumn("prp", indexmfg);
        String memoSrt = util.prpsrtcolumn("srt", indexMemo);
        String priSrt = util.prpsrtcolumn("srt", indexPri);
        String crtwtSrt = util.prpsrtcolumn("srt", indexCrtwt);
        String mfgcrtwtSrt = util.prpsrtcolumn("srt", indexMfgCrtwt);
        String mfgSrt = util.prpsrtcolumn("srt", indexmfg);
        String shapePrp = util.prpsrtcolumn("prp", indexsh);    
            
            String sqlQ = "  Select A.Prp_001, decode(A.Prp_007,'ROUND','ROUND','RD','ROUND','RBC','ROUND','FANCY') Shape , Nvl(B.acc_av,0) aakhasrt\n" + 
            "     , To_Char(Sum(A.Cts),'99999990.00') Cts\n" + 
            "     , Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts)) Aakhaold,\n" + 
            "    Round(Sum(Nvl(A.Cts, 0)*Nvl(B.Acc_Av,0))) aakhasrtvlu,\n" + 
            "    Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts))*Sum(A.Cts) Aakhaoldvlu\n" + 
            "     From Gt_Srch_Rslt A,Mlot B\n "+ 
            "     Where A.prp_001=B.Dsc and a.cts > 0 \n "+
            "     Group By A.Prp_001,decode(A.Prp_007,'ROUND','ROUND','RD','ROUND','RBC','ROUND','FANCY') , A.Prp_001 , B.Acc_Av " +
            "     Union\n" + 
            "     Select 'TOTAL','TOTAL',Round(Sum(Nvl(A.Cts, 0)*Nvl(B.acc_av,0))/Sum(A.Cts)) aakhasrt\n" + 
            "     , to_char(sum(a.cts),'99999990.00') Cts\n" + 
            "     , Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts)) Aakhaold,\n" + 
            "     Round(Sum(Nvl(A.Cts, 0)*Nvl(B.Acc_Av,0))) aakhasrtvlu,\n" + 
            "     Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts))*Sum(A.Cts) Aakhaoldvlu\n" + 
            "     From Gt_Srch_Rslt A,Mlot B\n" + 
            "     Where A.prp_001=B.Dsc and Nvl(A.Cts, 0) > 0 and Nvl(A.Prp_004, 0) > 0\n "+
            "     Order By 1";
            
          ArrayList outLst = db.execSqlLst("Dtl", sqlQ, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String memoVal = util.nvl(rs.getString(memoPrp));
                String shapeVal = util.nvl(rs.getString("Shape"));
                dataDtl.put(memoVal+"_"+shapeVal + "_CTS", util.nvl(rs.getString("Cts")));
                dataDtl.put(memoVal+"_"+shapeVal + "_AAKHAOLD", util.nvl(rs.getString("aakhaold")));
                dataDtl.put(memoVal+"_"+shapeVal + "_AAKHASRT", util.nvl(rs.getString("aakhasrt")));
                dataDtl.put(memoVal+"_"+shapeVal + "_AAKHAOLDVLU", util.nvl(rs.getString("Aakhaoldvlu")));
                dataDtl.put(memoVal+"_"+shapeVal + "_AAKHASRTVLU", util.nvl(rs.getString("aakhasrtvlu")));
                if (!shapeList.contains(shapeVal) && !shapeVal.equals("TOTAL"))
                    shapeList.add(shapeVal);
                if (!memoList.contains(memoVal) && !memoVal.equals("TOTAL"))
                    memoList.add(memoVal);
            }
            rs.close();
            pst.close();
            
            sqlQ ="     Select A.Prp_001,decode(A.Prp_007,'ROUND','ROUND','RD','ROUND','RBC','ROUND','FANCY') Shape ,To_Char(Sum(A.Cts),'99999990.00') Cts\n" + 
            "     , Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts)) Mnjold\n" + 
            "     , Round(Sum(Nvl(A.Prp_002, 0)*Nvl(A.Prp_005, 0))/Sum(A.Prp_002)) Mnjnew,\n" + 
            "     Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts))*Sum(A.Cts) Mnjoldvlu,\n" + 
            "     Round(Sum(Nvl(A.Prp_002, 0)*Nvl(A.Prp_005, 0))/Sum(A.Prp_002))*Sum(A.Cts) Mnjnewvlu\n" + 
            "     From Gt_Srch_Rslt A\n" + 
            "     Where  Nvl(A.Cts, 0) > 0  and Nvl(A.Prp_004, 0) > 0 and prp_006='96-UP-GIA'\n "+
            "     Group By A.Prp_001,decode(A.Prp_007,'ROUND','ROUND','RD','ROUND','RBC','ROUND','FANCY') , A.Prp_001  " +
            "     Union\n" + 
            "     Select 'TOTAL','TOTAL',To_Char(Sum(A.Cts),'99999990.00') Cts\n" + 
            "     , Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts)) Mnjold\n" + 
            "     , Round(Sum(Nvl(A.Prp_002, 0)*Nvl(A.Prp_005, 0))/Sum(A.Prp_002)) Mnjnew,\n" + 
            "     Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts))*Sum(A.Cts) Mnjoldvlu,\n" + 
            "     Round(Sum(Nvl(A.Prp_002, 0)*Nvl(A.Prp_005, 0))/Sum(A.Prp_002))*Sum(A.Cts) Mnjnewvlu\n" + 
            "     From Gt_Srch_Rslt A\n" + 
            "     Where Nvl(A.Cts, 0) > 0 And Nvl(A.Prp_004, 0) > 0 And Prp_006='96-UP-GIA'\n "+
            "     Order By 1" ;
            
           outLst = db.execSqlLst("Dtl", sqlQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
                        while (rs.next()) {
                            String memoVal = util.nvl(rs.getString(memoPrp));
                            String shapeVal = util.nvl(rs.getString("Shape"));
                            
                            dataDtl.put(memoVal+"_"+shapeVal + "_96-UP-GIA_CTS", util.nvl(rs.getString("Cts")));
                            dataDtl.put(memoVal+"_"+shapeVal + "_96-UP-GIA_MNJOLD", util.nvl(rs.getString("Mnjold")));
                            dataDtl.put(memoVal+"_"+shapeVal + "_96-UP-GIA_MNJNEW", util.nvl(rs.getString("Mnjnew")));
                            dataDtl.put(memoVal+"_"+shapeVal + "_96-UP-GIA_MNJOLDVLU", util.nvl(rs.getString("Mnjoldvlu")));
                            dataDtl.put(memoVal+"_"+shapeVal + "_96-UP-GIA_MNJNEWVLU", util.nvl(rs.getString("Mnjnewvlu")));
            }
            rs.close();
            pst.close();
            String bakiQ = "Select Memo,Shape,round(trf_vlu/asrt_vlu*100) Bakipct\n" + 
            "From (Select Prp_001 Memo, decode(A.Prp_007,'ROUND','ROUND','RD','ROUND','RBC','ROUND','FANCY') Shape, sum(decode(b.grp2, 'SEND', nvl(prp_003, 0)*nvl(prp_004, 0), 0)) TRF_VLU,\n" + 
            "sum(nvl(prp_003, 0)*nvl(prp_004, 0)) asrt_vlu\n" + 
            "From Gt_Srch_Rslt A, Df_Stk_Stt B Where A.Stt = B.Stt  and  Nvl(A.Cts, 0) > 0  and Nvl(A.Prp_004, 0) > 0  and prp_006='96-UP-GIA'  "+
            " Group By A.Prp_001 , decode(A.Prp_007,'ROUND','ROUND','RD','ROUND','RBC','ROUND','FANCY')) " +
            "Union\n" + 
            "Select memo,Shape,round(trf_vlu/asrt_vlu*100) Bakipct\n" + 
            "From (Select 'TOTAL' Memo,'TOTAL' Shape, sum(decode(b.grp2, 'SEND', nvl(prp_003, 0)*nvl(prp_004, 0), 0)) TRF_VLU,\n" + 
            "Sum(Nvl(Prp_003, 0)*Nvl(Prp_004, 0)) Asrt_Vlu\n" + 
            "From Gt_Srch_Rslt A, Df_Stk_Stt B Where A.Stt = B.Stt And Nvl(A.Cts, 0) > 0 And Nvl(A.Prp_004, 0) > 0 And Prp_006='96-UP-GIA'  ) " + 
            "Order By 1" ; 
          outLst = db.execSqlLst("Dtl", bakiQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String memoVal = util.nvl(rs.getString("memo"));
                String shapeVal = util.nvl(rs.getString("Shape"));
                dataDtl.put(memoVal+"_"+shapeVal + "_BKPER", util.nvl(rs.getString("Bakipct")));
            }
            rs.close();
            pst.close();
            
            String srtQ = " Select A.Prp_001 memo , "+
                  " Nvl(B.Acc_Av,0) Aakhasrt "+
                  " , Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts)) Aakhaold, "+
                  " Round ( ( ( Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts)) - Nvl(B.Acc_Av,0) ) / "+ 
                  " Round(Sum(Nvl(A.Cts, 0)*Nvl(A.Prp_004, 0))/Sum(A.Cts))  ) * 100 ) srtpct "+
                  " From Gt_Srch_Rslt A,Mlot B "+
                  " Where A.prp_001=B.Dsc and a.cts > 0  "+
                  " Group By A.Prp_001,B.Acc_Av ";
            
          outLst = db.execSqlLst("Dtl", srtQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String memoVal = util.nvl(rs.getString("memo"));
                dataDtl.put(memoVal+"_SRTPER", util.nvl(rs.getString("srtpct")));
            }
            rs.close();
            pst.close();
            
            session.setAttribute("dataDtlSH", dataDtl);
            session.setAttribute("memoListSH", memoList);
            session.setAttribute("shapeListSH", shapeList);
            
        
            util.updAccessLog(req,res,"Rpt Action", "fetchakhiSH end");
        return am.findForward("fetchakhiSH");
        }
    }

    public ActionForward akhaniavgDiffXLSH(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "akhaniavgDiffXLSH start");
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "akhaniavgDiff" + util.getToDteTime() + ".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        ArrayList memoList= (ArrayList)session.getAttribute("memoListSH");
        ArrayList shapeList= (ArrayList)session.getAttribute("shapeListSH");
        HSSFWorkbook hwb = null;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        hwb = xlUtil.getDataAkhaniXlSH(req, memoList,shapeList);
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Rpt Action", "akhaniavgDiffXLSH end");
        return null;
        }
    }
    
    public ActionForward loadWeekMonth(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "loadWeekMonth start");
        ReportForm udf = (ReportForm)af;
        ArrayList empList = new ArrayList();
        udf.resetALL();
        String deptQ =
            "select a.nme_idn, trim(initcap(a.nme)) nme from nme_v a where a.typ = 'EMPLOYEE' " + "and exists (select 1 from iss_rtn b where a.nme_idn = b.emp_id) order by 2";
         ArrayList outLst = db.execSqlLst("stkDtl", deptQ, new ArrayList());
        PreparedStatement  pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            MNme emp = new MNme();
            emp.setEmp_idn(rs.getString("nme_idn"));
            emp.setEmp_nme(rs.getString("nme"));
            empList.add(emp);
        }
        rs.close();
        pst.close();
        udf.setEmpList(empList);
        ArrayList yrList = new ArrayList();
        ArrayList monthList = new ArrayList();
        String gtView =
            "select chr_fr, chr_to , dsc , dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
            " b.mdl = 'JFLEX' and b.nme_rule = 'YEARS' and a.til_dte is null order by a.srt_fr ";
           outLst = db.execSqlLst("gtView", gtView, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            ObjBean prp = new ObjBean();
            prp.setNme(util.nvl(rs.getString("chr_fr")));
            prp.setDsc(util.nvl(rs.getString("dsc")));
            yrList.add(prp);
        }
        rs.close();
            pst.close();

        gtView =
                "select chr_fr, chr_to , dsc , dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + " b.mdl = 'JFLEX' and b.nme_rule = 'MONTHS' and a.til_dte is null order by a.srt_fr ";
          outLst = db.execSqlLst("gtView", gtView, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            ObjBean prp = new ObjBean();
            prp.setNme(util.nvl(rs.getString("chr_fr")));
            prp.setDsc(util.nvl(rs.getString("dsc")));
            monthList.add(prp);
        }
        rs.close();
            pst.close();
        udf.setYrList(yrList);
        udf.setMonthList(monthList);
            util.updAccessLog(req,res,"Rpt Action", "loadWeekMonth end");
        return am.findForward("loadWeekMonth");
        }
    }

    public ActionForward fetchWeekMonth(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "fetchWeekMonth start");
        ReportForm udf = (ReportForm)af;
        String month = util.nvl((String)udf.getValue("month"));
        String year = util.nvl((String)udf.getValue("year"));
        String[] empLst = udf.getEmpLst();
        String empidn = "";
        for (int i = 0; i < empLst.length; i++) {
            empidn = empidn + "," + empLst[i];
        }
        empidn = empidn.replaceAll("\\,", "");
            util.updAccessLog(req,res,"Rpt Action", "fetchWeekMonth end");
        return am.findForward("loadWeekMonth");
        }
    }

    public ActionForward loadReports(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "loadReports start");
        ReportForm udf = (ReportForm)form;
        udf.reset();
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("JFLEX_REPORT");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("JFLEX_REPORT");
        allPageDtl.put("JFLEX_REPORT",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Rpt Action", "loadReports end");
        return am.findForward("loadReports");
        }
    }



    public void fetchData(HttpServletRequest req,HttpServletResponse res, String byr,String empIdn) {
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
        ArrayList byrList = new ArrayList();
        ArrayList pktList = new ArrayList();
        ArrayList byridnList = new ArrayList();
        HashMap byrDtl = new HashMap();
        HashMap dataDtl = new HashMap();
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "dlvlocPrpList","DLV_LOC");
        ArrayList ary = new ArrayList();
        String conQ = "";
        String rsltQ =
            " select Stk_Idn , Srch_Id dlv_idn , Rln_Idn byr_idn ,emp,byr,Vnm,Qty,Cts,prte ,to_char(quot, '9999999999990.00') salrte,to_char(decode(rap_rte, 1, '', trunc(((quot/greatest(rap_rte,1))*100)-100,2)), '99999990.00') saldis, to_char(trunc(cts,2) * quot, '99999990.00') amt,to_char(pkt_dte, 'dd-Mon-rrrr') pkt_dte,round(trunc(sysdate)-trunc(pkt_dte)) days,decode(nvl(exh_rte,1),1,'','#00CC66') color   ";
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
        rsltQ = rsltQ + " from gt_srch_multi " + " where flg= ?  ";

        ary = new ArrayList();
        ary.add("Z");
        if (!byr.equals("0") && !byr.equals("")) {
            conQ = " and Rln_Idn = ? ";
            ary.add(byr);
        }
        if (!empIdn.equals("0") && !empIdn.equals("")) {
            conQ += " and pair_id = ? ";
            ary.add(empIdn);
        }
        rsltQ = rsltQ + conQ + " order by emp,byr,sk1";
        try {
          ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
          PreparedStatement  pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String byr_idn = util.nvl(rs.getString("byr_idn"));
                if (!byridnList.contains(byr_idn)) {
                    byridnList.add(byr_idn);
                    byrDtl.put(byr_idn, util.nvl(rs.getString("byr")));
                    byrDtl.put(byr_idn+"_EMP", util.nvl(rs.getString("emp")));
                    pktList = new ArrayList();
                }
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("dlvIdn", util.nvl(rs.getString("dlv_idn")));
                pktPrpMap.put("qty", util.nvl(rs.getString("Qty")));
                pktPrpMap.put("cts", util.nvl(rs.getString("Cts")));
                pktPrpMap.put("vlu", util.nvl(rs.getString("amt")));
                pktPrpMap.put("vnm", util.nvl(rs.getString("Vnm")));
                pktPrpMap.put("dte", util.nvl(rs.getString("pkt_dte")));
                pktPrpMap.put("days",util.nvl(rs.getString("days")));
                pktPrpMap.put("color", util.nvl(rs.getString("color")));
                pktPrpMap.put("SALERTE", util.nvl(rs.getString("salrte")));
                pktPrpMap.put("SALEDIS", util.nvl(rs.getString("saldis")));
                for (int j = 0; j < vwPrpLst.size(); j++) {
                    String prp = (String)vwPrpLst.get(j);
                    String fld = "prp_";
                    if (j < 9)
                        fld = "prp_00" + (j + 1);
                    else
                        fld = "prp_0" + (j + 1);
                    String val = util.nvl(rs.getString(fld));
                    pktPrpMap.put(prp, val);
                }
                pktList.add(pktPrpMap);
                dataDtl.put(byr_idn, pktList);
            }
            rs.close();
            pst.close();
            String ttlQ =
                "select Rln_Idn byr_idn,to_char(sum(cts),'99999990.00') cts,sum(qty) qty,to_char(sum(prte),'9999999999990.00') vlu from gt_srch_multi group by Rln_Idn";
           outLst = db.execSqlLst("Totals", ttlQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String byr_idn = util.nvl(rs.getString("byr_idn"));
                HashMap ttlDtl = new HashMap();
                ttlDtl.put("cts", util.nvl(rs.getString("cts")));
                ttlDtl.put("vlu", util.nvl(rs.getString("vlu")));
                ttlDtl.put("qty", util.nvl(rs.getString("qty")));
                dataDtl.put(byr_idn + "_TTL", ttlDtl);
            }
            rs.close();
            pst.close();
            ttlQ =
"select to_char(sum(cts),'9999999990.00') cts,sum(qty) qty,to_char(sum(prte),'9999999999990.00') vlu from gt_srch_multi where flg= ?  " + conQ;
          outLst = db.execSqlLst("Grand Totals", ttlQ, ary);
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                HashMap ttlDtl = new HashMap();
                ttlDtl.put("cts", util.nvl(rs.getString("cts")));
                ttlDtl.put("vlu", util.nvl(rs.getString("vlu")));
                ttlDtl.put("qty", util.nvl(rs.getString("qty")));
                dataDtl.put("GRANDTTL", ttlDtl);
            }
            rs.close();
            pst.close();
            req.setAttribute("dataDtl", dataDtl);
            req.setAttribute("byrDtl", byrDtl);
            req.setAttribute("byridnList", byridnList);
            req.setAttribute("view", "Y");
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    }

    public ActionForward mixsalerpt(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "mixsalerpt start");
        ReportForm udf = (ReportForm)af;
        GenericInterface genericInt = new GenericImpl();
        String dfr = util.nvl((String)udf.getValue("dtefr"));
        String dto = util.nvl((String)udf.getValue("dteto"));
        String buyerId = util.nvl(req.getParameter("nmeID"));
        String dept = util.nvl((String)udf.getValue("dept")).trim();
        ArrayList ary = new ArrayList();
        ArrayList parms = new ArrayList();
        ArrayList pktList = new ArrayList();
        HashMap mixsalesizeadj =((HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_mixsalesizeadj") == null)?new HashMap():(HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_mixsalesizeadj"); 
        String deptQ=" and sd.VAL = sd.VAL ";
        String conQ = " And Trunc(C.Dte) = Trunc(Sysdate)";
        if (!dfr.equals(""))
            conQ =
        " and trunc(C.Dte) between to_date('" + dfr + "' , 'dd-mm-yyyy') and to_date('" + dto + "' , 'dd-mm-yyyy') ";
        if (!buyerId.equals("") && !buyerId.equals("0")) {
            conQ = conQ + " and e.nme_idn=?";
            ary.add(buyerId);
        }
        if (!dept.equals("") && !dept.equals("0")) 
        deptQ=" and sd.VAL = '"+dept+"' ";
        String sqlQ =
            "Select a.vnm,b.idn,c.mstk_idn ,to_char(c.cts,'99999990.00') cts, to_char(C.dte, 'dd-mm-rrrr') dte,Initcap(E.Fnme\n" + 
            "    ||Nvl2(E.Lnme, ' / '\n" + 
            "    ||e.lnme, '')) nme,e.alias partycode,Decode(B.Cur, 'USD', 'PSALE-D', 'INR', 'PSALE-RS', 'PSALE-D/RS') Cur,\n" + 
            "F.Dys Duedays,to_char(C.Dte+F.Dys, 'dd-Mon-rrrr') Durdate,\n" + 
            "decode(B.Exh_Rte,1,'',B.Exh_Rte) Exh_Rte,f.terms_code,F.Term, \n" + 
            "  Byr.get_nmcode(b.Mbrk3_Idn) brok1,\n" + 
            "  Byr.get_nmcode(b.Mbrk4_Idn) brok2\n" + 
            ",Nvl(b.Brk3_Comm,0)+nvl(b.Brk4_Comm,0) cordper,b.aadat_comm,b.Brk1_Comm com2,to_char(nvl(c.fnl_sal,c.fnl_usd),'99999999990.00') fixRate,\n" + 
            "to_char((nvl(c.fnl_usd,c.fnl_sal))*(1-((Decode(B.Cur, 'USD',0,5)+NVL (f.pct, 0)+NVL (b.aadat_comm, 0)+NVL (b.Brk1_Comm, 0)+NVL (b.Brk3_Comm, 0)+NVL (b.Brk4_Comm, 0))/100)),'99999999990.00') fnl_bse\n" + 
            ",to_char(c.BSE_PRI,'99999999990.00') srtavg ,c.rmk,b.thru,GET_PKT_EMP(a.idn,e.nme_idn) emp,b.blind_yn blind,b.net_yn net\n" + 
            "From Mstk A,Msal B,sal_pkt_dtl_v C,Nmerel D,mnme E,Mtrms F,stk_dtl sd\n" + 
            "Where\n" + 
            "A.Idn=C.Mstk_Idn And\n" + 
            "b.idn =c.idn and a.idn=sd.mstk_idn and sd.grp=1 and sd.mprp='DEPT' and b.nmerel_idn = d.nmerel_idn\n" + 
            "and d.nme_idn=e.nme_idn and d.trms_idn = f.idn\n" + 
            " and a.pkt_ty <> 'NR' and\n" + 
            "C.Stt Not In ('RT','CS') And C.Typ='SL' \n" + deptQ+conQ+
            " order by e.Fnme";
          ArrayList outLst = db.execSqlLst("sqlQ", sqlQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        int i = 0;
        while (rs.next()) {
            HashMap pktPrpMap = new HashMap();
            String mstkIdn = rs.getString("mstk_idn");
            String saleidn = rs.getString("idn");
            pktPrpMap.put("Sr", String.valueOf(++i));
            pktPrpMap.put("TransDate", util.nvl(rs.getString("dte")));
            pktPrpMap.put("PartyCode", util.nvl(rs.getString("partycode")));
            pktPrpMap.put("nme", util.nvl(rs.getString("nme")));
            pktPrpMap.put("emp", util.nvl(rs.getString("emp")));
            pktPrpMap.put("Transaction", util.nvl(rs.getString("Cur")));
            pktPrpMap.put("RndNo", "");
            pktPrpMap.put("DueDays", util.nvl(rs.getString("Duedays")));
            pktPrpMap.put("DueDate", util.nvl(rs.getString("Durdate")));
            pktPrpMap.put("PartyCnvRt", util.nvl(rs.getString("Exh_Rte")));
            pktPrpMap.put("StkCnvRt", "");
            pktPrpMap.put("termscode", util.nvl(rs.getString("terms_code")));
            pktPrpMap.put("TermRem", util.nvl(rs.getString("Term")));
            pktPrpMap.put("ExpThrough", "");
            pktPrpMap.put("Handle", util.nvl(rs.getString("thru")));
            pktPrpMap.put("Blind", util.nvl(rs.getString("blind")));
            pktPrpMap.put("Net", util.nvl(rs.getString("net")));
            pktPrpMap.put("vnm", util.nvl(rs.getString("vnm")));
            pktPrpMap.put("StkRiskFact", "");
            String brok1 = util.nvl(rs.getString("brok1"));
            String brok2 = util.nvl(rs.getString("brok2"));
            String cordcode = "";
            if (!brok1.equals("None"))
                cordcode = brok1;
            if (!brok2.equals("None"))
                cordcode = cordcode + "-" + brok2;
            cordcode = cordcode.replaceFirst("\\-", "");
            pktPrpMap.put("CordCode", cordcode);
            String cordper = util.nvl(rs.getString("cordper"),"0");
            if(cordper.equals("0"))
                cordper="";
//            if(cordper.length()>0)
//                cordper = "-"+cordper;
            pktPrpMap.put("Cord", cordper);
            pktPrpMap.put("CordCnvRt", "");
            String com1=util.nvl(rs.getString("aadat_comm"),"0");
            if(com1.equals("0"))
                com1="";
            else
                com1="-"+com1;
            pktPrpMap.put("Com1", com1);
            String com2=util.nvl(rs.getString("com2"),"0");
            if(com2.equals("0"))
                com2="";
            else
                com2="-"+com2;
            pktPrpMap.put("Com2", com2);
            pktPrpMap.put("EntSrNo", String.valueOf(i));
            String srtAvg = util.nvl(rs.getString("srtavg"));
            pktPrpMap.put("srtavg", srtAvg);
            String getPktcharges = "Select b.ref_idn,\n" +
                "Max (Decode (A.Typ, 'MGMT', B.Charges, Null)) mgmt,\n" +
                "MAX (DECODE (a.typ, 'LOY', B.Charges, NULL)) loy\n" +
                "From Charges_Typ A,Trns_Charges B\n" +
                "Where A.Idn=B.Charges_Idn And A.Stt='A' And Nvl(B.Flg,'NA') Not In('Y') And Ref_Idn In (?) And App_Typ='PP' and ref_typ='SAL' " +
                "Group By B.Ref_Idn";
            parms = new ArrayList();
            parms.add(saleidn);
          ArrayList outLst1 = db.execSqlLst("getPktcharges", getPktcharges, parms);
          PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
          ResultSet rs1 = (ResultSet)outLst1.get(1);
            while (rs1.next()) {
                String mgmt = util.nvl(rs1.getString("mgmt"));
                if(mgmt.length()>0)
                    mgmt = "-"+mgmt;
                pktPrpMap.put("Com3", mgmt);
                String  loy = util.nvl(rs1.getString("loy"));
                if(loy.length()>0)
                    loy="-"+loy;
                if(loy.equals("-0"));
                loy="";
                pktPrpMap.put("Com4", loy);
            }
            rs1.close();
            pst1.close();
            String getPktPrp =
                "             select a.prp mprp,decode(a.dta_typ, 'C', decode(a.prp,'MIX_SIZE',d.val,nvl(b.prt2,d.val)), 'N', d.num, 'D', d.dte, d.txt) val\n" + 
                "            From Mprp A, Prp B,stk_dtl D, Rep_Prp E\n" + 
                "            Where A.Prp = D.Mprp And B.Mprp = D.Mprp \n" + 
                "            And D.Mprp = E.Prp And B.Val = d.val and d.grp=1  And E.Mdl = ? and d.mstk_idn = ? order by e.rnk, e.srt  ";
            parms = new ArrayList();
            parms.add("MIXSALE_VW");
            parms.add(mstkIdn);
           outLst1 = db.execSqlLst("pktdata", getPktPrp, parms);
           pst1 = (PreparedStatement)outLst1.get(0);
           rs1 = (ResultSet)outLst1.get(1);
            while (rs1.next()) {
                String lPrp = util.nvl(rs1.getString("mprp"));
                String lVal = util.nvl(rs1.getString("val"));
                if (lPrp.equals("CRTWT"))
                    lVal = util.nvl(rs.getString("cts"));
                if(lPrp.equals("MIX_SIZE")){
                String adjMixsz=util.nvl((String)mixsalesizeadj.get(lVal),lVal);    
                lVal="'"+adjMixsz;
                }
                pktPrpMap.put(lPrp, lVal);
            }
            rs1.close();
            pst1.close();
            pktPrpMap.put("UsRate", util.nvl(rs.getString("fixRate")));
            pktPrpMap.put("FixRate", util.nvl(rs.getString("fnl_bse")));
            pktPrpMap.put("FixCnvRt", "");
            pktPrpMap.put("RemOut", util.nvl(rs.getString("rmk")));
            pktPrpMap.put("RemNoChange", "");
            pktList.add(pktPrpMap);
        }
        rs.close();
            pst.close();
        udf.setPktList(pktList);
       genericInt.genericPrprVw(req, res, "mixsaleprpLst","MIXSALE_VW");
        req.setAttribute("View", "Y");
        udf.resetALL();
            util.updAccessLog(req,res,"Rpt Action", "mixsalerpt end");
        return am.findForward("mixsalerpt");
        }
    }

//    public void mixsalesizeadj(HttpServletRequest req){
//        HttpSession session = req.getSession(false);
//        InfoMgr info = (InfoMgr)session.getAttribute("info");
//        DBUtil util = new DBUtil();
//        DBMgr db = new DBMgr();
//        db.setCon(info.getCon());
//        util.setDb(db);
//        util.setInfo(info);
//        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//        util.setLogApplNm(info.getLoApplNm());
//    HashMap mixsalesizeadj = (session.getAttribute("mixsalesizeadj") == null)?new HashMap():(HashMap)session.getAttribute("mixsalesizeadj");
//
//    try {
//    if(mixsalesizeadj.size() == 0) {
//        HashMap dbinfo = info.getDmbsInfoLst();
//        String cnt=util.nvl((String)dbinfo.get("CNT"));
//        String mem_ip=util.nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//        int mem_port=Integer.parseInt(util.nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//        MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//        mixsalesizeadj=(HashMap)mcc.get(cnt+"_mixsalesizeadj");
//        if(mixsalesizeadj==null){
//        mixsalesizeadj=new HashMap();
//    String gtView = "select chr_fr, chr_to , dsc , dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
//    " b.mdl = 'JFLEX' and b.nme_rule = 'MIX_SALE_RPT' and a.til_dte is null order by a.srt_fr ";
//    ResultSet rs = db.execSql("gtView", gtView, new ArrayList());
//    while (rs.next()) {
//    mixsalesizeadj.put(util.nvl(rs.getString("dsc")), util.nvl(rs.getString("chr_fr")));
//    }
//        rs.close();
//        Future fo = mcc.delete(cnt+"_mixsalesizeadj");
//        System.out.println("add status:_mixsalesizeadj" + fo.get());
//        fo = mcc.set(cnt+"_mixsalesizeadj", 24*60*60, mixsalesizeadj);
//        System.out.println("add status:_mixsalesizeadj" + fo.get());
//        }
//        mcc.shutdown();
//    session.setAttribute("mixsalesizeadj", mixsalesizeadj);
//    }
//    } catch (SQLException sqle) {
//    // TODO: Add catch code
//    sqle.printStackTrace();
//        }catch(Exception ex){
//         System.out.println( ex.getMessage() );
//        }
//    }
    public ActionForward singlrsalerpt(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "singlrsalerpt start");
        ReportForm udf = (ReportForm)af;
        String dfr = util.nvl((String)udf.getValue("dtefr"));
        String dto = util.nvl((String)udf.getValue("dteto"));
        String buyerId = util.nvl(req.getParameter("nmeID"));
        String dept = util.nvl((String)udf.getValue("dept")).trim();
        ArrayList ary = new ArrayList();
        ArrayList parms = new ArrayList();
        ArrayList pktList = new ArrayList();
        String conQ = " And trunc(a.DTE) = trunc(sysdate) ";
        String conQbranch = " And trunc(d.DTE) = trunc(sysdate) ";
        String conQPkt = " And trunc(PKT.sal_dte) = trunc(sysdate) ";
        String deptQ=" and SD.VAL = SD.VAL ";
            if (!dfr.equals("")){
            conQ =" and trunc(a.Dte) between to_date('" + dfr + "' , 'dd-mm-yyyy') and to_date('" + dto + "' , 'dd-mm-yyyy') ";
            conQbranch =" and trunc(d.Dte) between to_date('" + dfr + "' , 'dd-mm-yyyy') and to_date('" + dto + "' , 'dd-mm-yyyy') ";
            conQPkt =" and trunc(PKT.sal_dte) between to_date('" + dfr + "' , 'dd-mm-yyyy') and to_date('" + dto + "' , 'dd-mm-yyyy') ";
            }
        if (!buyerId.equals("") && !buyerId.equals("0")) {
            conQ = conQ + " and a.nme_idn=?";
            conQbranch = conQbranch + " and d.nme_idn=?";
            ary.add(buyerId);
        }
        if (!dept.equals("") && !dept.equals("0")) 
        deptQ=" and SD.VAL = '"+dept+"' ";
      
//      String sqlQ="WITH SAL as (\n" + 
//      " select D.idn, \n" + 
//      "  N.alias partycode,\n" + 
//      "  N.CO_NME nme,N.cp PrimaryContact,\n" + 
//      "  DECODE (d.aadat_paid, 'Y', Byr.Get_Nm(D.aadat_Idn), '') mb1,\n" + 
//      "  Byr.Get_Nm(D.Mbrk1_Idn) mb2,\n" + 
//      "  Byr.Get_Nm(D.Mbrk2_Idn) sb1,\n" + 
//      "  Byr.Get_Nm(D.Mbrk3_Idn) sb2,\n" + 
//      "  Byr.Get_Nm(D.Mbrk4_Idn) Sb3,\n" + 
//      "  Byr.Get_Nmcode(D.Aadat_Idn) Mb1code, \n" + 
//      "  Byr.Get_Nmcode(D.Mbrk1_Idn) Mb2code, \n" + 
//      "  Byr.Get_Nmcode(D.Mbrk2_Idn) Sb1code, \n" + 
//      "  Byr.Get_Nmcode(D.Mbrk3_Idn) Sb2code,\n" + 
//      "  Byr.get_nmcode(D.Mbrk4_Idn) sb3code, \n" + 
//      "  Nvl(Nvl(Nvl(D.Brk1_Comm, D.Brk2_Comm), D.Brk3_Comm), D.Brk4_Comm) cordper,\n" + 
//      "  (DECODE (d.aadat_paid, 'Y', NVL (d.aadat_comm, 0), 0) * -1) aadat_comm,\n" + 
//      "  Decode(D.Cur, 'USD', 'PSALE-D', 'INR', 'PSALE-RS', 'PSALE-D/RS') Cur\n" + 
//      "  , decode(D.EXH_RTE , '1' , '' , D.EXH_RTE) EXH_RTE\n" + 
//      "  , T.Dys DueDays, to_Char(trunc(d.dte) + t.dys,'dd/mm/rrrr') dueDte\n" + 
//      "  , T.term, T.terms_code\n" + 
//      "  , to_Char(D.dte, 'dd/mm/rrrr') dte\n" + 
//      "  ,( decode(upper(n.grp_co_nme), 'HK EMKT', 2.5, 'HK DOM', 2.5, 0)*-1) stkRiskFact\n" + 
//      " from MSAL D, NME_V N, NmeRel R, Mtrms T\n" + 
//      " where 1 = 1 \n" + 
//      " And  nvl(d.fnl_trms_idn,d.nmerel_idn) = R.nmerel_idn\n" + 
//      "  and R.trms_idn = T.idn\n" + 
//      "  and R.nme_idn = N.nme_idn \n" + 
//      "    and  d.idn in (select distinct b.sal_idn  \n" + 
//      "       from MDLV a,dlv_pkt_dtl_v b, mstk c,mnme d \n" + 
//      "       where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.stt='MKSD' \n" + 
//      "       and b.typ in ('DLV') and b.stt not in ('RT','CS','DP','PS','CL') "+ conQ+")\n" + 
//      ") \n" + 
//      "select MSAL.idn, MSAL.dte, MSAL.partycode, MSAL.nme,MSAL.PrimaryContact, MSAL.mb1, MSAL.mb2, MSAL.sb1, MSAL.sb2, MSAL.sb3,MSAL.mb1code, MSAL.mb2code, MSAL.sb1code, MSAL.sb2code, MSAL.sb3code\n" + 
//      ", MSAL.cordper, MSAL.aadat_comm, MSAL.cur, MSAL.exh_rte, MSAL.dueDays, MSAL.dueDte, MSAL.term, MSAL.terms_code\n" + 
//      ", MSAL.stkRiskFact\n" + 
//      ", decode(sd.val, '18-96-GIA', '18', '96') sz \n" + 
//      ", decode(sd.val, '18-96-GIA', '18G', '96G') Dept\n" + 
//      ",   decode(sh.val, 'ROUND', 'R', 'F')  Shape , trunc(sum(pkt.cts), 2) cts\n" + 
//      "  , to_char(trunc(((sum(trunc(pkt.cts,2)*pkt.fnl_usd) / sum(trunc(pkt.cts,2)))), 2),'999999999990.00') UsRate  ,  to_char(trunc(((sum(trunc(pkt.cts,2)*pkt.fnl_usd) * ((100 + nvl(MSAL.stkRiskFact, 0))/100) * ((100 - nvl(MSAL.cordPer, 0))/100) * ((100 + nvl(MSAL.aadat_comm, 0))/100)/ sum(trunc(pkt.cts,2)))), 2),'99999999990.00') fixRate , pkt.rmk  , sd.srt \n" + 
//      "  From SAL MSAL, SAL_PKT_DTL_V PKT, MSTK M, STK_DTL SD, STK_DTL SH\n" + 
//      "Where 1 = 1\n" + 
//      "and MSAL.idn = PKT.IDN\n" + 
//      "and PKT.MSTK_IDN = M.IDN\n" + 
//      "and M.PKT_TY = 'NR'\n" + 
//      "and M.IDN = SD.MSTK_IDN and SD.GRP = 1 and SD.MPRP = 'DEPT' "+deptQ+" and SD.VAL = SD.VAL   and SD.VAL = SD.VAL   and M.IDN = SH.MSTK_IDN and SH.GRP = 1 and SH.MPRP = 'SHAPE' \n" + 
//      "and PKT.STT in ('DLV')\n" + 
//      "group by MSAL.idn, MSAL.dte, MSAL.partycode, MSAL.nme,MSAL.PrimaryContact, MSAL.mb1, MSAL.mb2, MSAL.sb1, MSAL.sb2, MSAL.sb3, MSAL.mb1code, MSAL.mb2code, MSAL.sb1code, MSAL.sb2code, MSAL.sb3code, MSAL.cordper, MSAL.aadat_comm, MSAL.cur, MSAL.exh_rte, MSAL.dueDays, MSAL.dueDte, MSAL.term, MSAL.terms_code, MSAL.stkRiskFact, decode(sd.val, '18-96-GIA', '18', '96'), decode(sd.val, '18-96-GIA', '18G', '96G'), decode(sh.val, 'ROUND', 'R', 'F'), pkt.rmk, sd.srt Order By Shape,MSAL.partycode ";
//        
        String sqlQ="WITH SAL as (\n" + 
        " select D.idn,\n" + 
        "  N.alias partycode,\n" + 
        "  N.CO_NME nme,N.cp PrimaryContact,\n" + 
        "  DECODE (d.aadat_paid, 'Y', Byr.Get_Nm(D.aadat_Idn), '') mb1,\n" + 
        "  Byr.Get_Nm(D.Mbrk1_Idn) mb2,\n" + 
        "  Byr.Get_Nm(D.Mbrk2_Idn) sb1,\n" + 
        "  Byr.Get_Nm(D.Mbrk3_Idn) sb2,\n" + 
        "  Byr.Get_Nm(D.Mbrk4_Idn) Sb3,\n" + 
        "  Byr.Get_Nmcode(D.Aadat_Idn) Mb1code,\n" + 
        "  Byr.Get_Nmcode(D.Mbrk1_Idn) Mb2code,\n" + 
        "  Byr.Get_Nmcode(D.Mbrk2_Idn) Sb1code,\n" + 
        "  Byr.Get_Nmcode(D.Mbrk3_Idn) Sb2code,\n" + 
        "  Byr.get_nmcode(D.Mbrk4_Idn) sb3code,\n" + 
        "  Nvl(Nvl(Nvl(D.Brk1_Comm, D.Brk2_Comm), D.Brk3_Comm), D.Brk4_Comm) cordper,\n" + 
        "  (DECODE (d.aadat_paid, 'Y', DECODE (d.mbrk1_paid,'Y',NVL (d.brk1_comm, 0)+NVL (d.aadat_comm, 0),NVL (d.aadat_comm, 0)), 0) * -1) aadat_comm,\n" + 
        "  Decode(D.Cur, 'USD', 'PSALE-D', 'INR', 'PSALE-RS', 'PSALE-D/RS') Cur\n" + 
        "  , decode(D.EXH_RTE , '1' , '' , D.EXH_RTE) EXH_RTE\n" + 
        "  , T.Dys DueDays, to_Char(trunc(d.dte) + t.dys,'dd/mm/rrrr') dueDte\n" + 
        "  , T.term, T.terms_code\n" + 
        "  , to_Char(D.dte, 'dd/mm/rrrr') dte\n" + 
        "  ,( decode(upper(n.grp_co_nme), 'HK DOM', 2.5, 0)*-1) stkRiskFact\n" + 
        " from MSAL D, NME_V N, NmeRel R, Mtrms T\n" + 
        " where 1 = 1\n" + 
        " And  nvl(d.fnl_trms_idn,d.nmerel_idn) = R.nmerel_idn\n" + 
        "  and R.trms_idn = T.idn\n" + 
        "  and R.nme_idn = N.nme_idn\n" + 
   //  "  AND D.INV_NME_IDN not in (select NME_IDN from CO_BRANCH_V)\n" + 
              
        "    and  d.idn in (select distinct b.sal_idn \n" + 
        "       from MDLV a,dlv_pkt_dtl_v b, mstk c,mnme d\n" + 
        "       where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.stt in('MKSD','BRC_MKSD')\n" + 
        "       and b.typ in ('DLV') and b.stt not in ('RT','CS','DP','PS','CL')  "+ conQ+" )\n" + 
        ")\n" + 
        "select -- MSAL.idn, \n" + 
        "MSAL.dte, MSAL.partycode, MSAL.nme,MSAL.PrimaryContact,EX.txt emp, MSAL.mb1, MSAL.mb2, MSAL.sb1, MSAL.sb2, MSAL.sb3,MSAL.mb1code, MSAL.mb2code, MSAL.sb1code, MSAL.sb2code, MSAL.sb3code\n" + 
        ", MSAL.cordper, MSAL.aadat_comm, MSAL.cur, MSAL.exh_rte, MSAL.dueDays, MSAL.dueDte, MSAL.term, MSAL.terms_code\n" + 
        ", MSAL.stkRiskFact\n" + 
        ", decode(sd.val, '18-96-GIA', '18', '96') sz\n" + 
        ", decode(sd.val, '18-96-GIA', '18G', '96G') Dept\n" + 
        ",   decode(sh.val, 'ROUND', 'R', 'F')  Shape , trunc(sum(pkt.cts), 2) cts\n" + 
        "  , to_char(trunc(((sum(trunc(pkt.cts,2)*pkt.fnl_usd) / sum(trunc(pkt.cts,2)))), 2),'999999999990.00') UsRate  ,  to_char(trunc(((sum(trunc(pkt.cts,2)*pkt.fnl_usd) * ((100 + nvl(MSAL.stkRiskFact, 0))/100) * ((100 - nvl(MSAL.cordPer, 0))/100) * ((100 + nvl(MSAL.aadat_comm, 0))/100)/ sum(trunc(pkt.cts,2)))), 2),'99999999990.00') fixRate , pkt.rmk  , sd.srt\n" + 
        "  From SAL MSAL, SAL_PKT_DTL_V PKT, MSTK M, STK_DTL SD, STK_DTL SH,STK_DTL CTG,STK_DTL EX\n" + 
        "Where 1 = 1\n" + 
        "and MSAL.idn = PKT.IDN\n" + 
        "and PKT.MSTK_IDN = M.IDN\n" + 
        "and M.PKT_TY = 'NR'\n" + 
        "and M.IDN = SD.MSTK_IDN and SD.GRP = 1 and SD.MPRP = 'DEPT'   "+deptQ+"   and SD.VAL = SD.VAL   and SD.VAL = SD.VAL   and M.IDN = SH.MSTK_IDN and SH.GRP = 1 and SH.MPRP = 'SHAPE'\n" + 
        "and M.IDN = CTG.MSTK_IDN and CTG.GRP = 1 and CTG.MPRP = 'STK-CTG' and CTG.val in('A','B') and PKT.STT in ('DLV') and M.IDN = EX.MSTK_IDN and EX.GRP = 1 and EX.MPRP = 'SAL_EMP' \n" +conQPkt +
        "group by --MSAL.idn, \n" + 
        "MSAL.dte, MSAL.partycode, MSAL.nme,MSAL.PrimaryContact,EX.txt, MSAL.mb1, MSAL.mb2, MSAL.sb1, MSAL.sb2, MSAL.sb3, MSAL.mb1code, MSAL.mb2code, MSAL.sb1code, MSAL.sb2code, MSAL.sb3code, MSAL.cordper, MSAL.aadat_comm, MSAL.cur, MSAL.exh_rte, MSAL.dueDays, MSAL.dueDte, MSAL.term, MSAL.terms_code, MSAL.stkRiskFact, decode(sd.val, '18-96-GIA', '18', '96'), decode(sd.val, '18-96-GIA', '18G', '96G'), decode(sh.val, 'ROUND', 'R', 'F'), pkt.rmk, sd.srt Order By Shape,MSAL.partycode";
          ArrayList outLst = db.execSqlLst("sqlQ", sqlQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        int i = 0;
        while (rs.next()) {
            HashMap pktPrpMap = new HashMap();
            String stkRiskFact=util.nvl(rs.getString("stkRiskFact"));
            if(stkRiskFact.equals("0"))
            stkRiskFact="";
            pktPrpMap.put("Sr", String.valueOf(++i));
            pktPrpMap.put("VchYN", "");
            pktPrpMap.put("InvNo", "");
            pktPrpMap.put("cts", util.nvl(rs.getString("cts")));
            pktPrpMap.put("TransDate", util.nvl(rs.getString("dte")));
            pktPrpMap.put("PartyCode", util.nvl(rs.getString("partycode")));
            pktPrpMap.put("emp", util.nvl(rs.getString("emp")));
            pktPrpMap.put("nme", util.nvl(rs.getString("nme")));
            pktPrpMap.put("PrimaryContact", util.nvl(rs.getString("PrimaryContact")));
            pktPrpMap.put("Transaction", util.nvl(rs.getString("Cur")));
            pktPrpMap.put("RndNo", "");
            pktPrpMap.put("DueDays", util.nvl(rs.getString("Duedays")));
            pktPrpMap.put("DueDate", util.nvl(rs.getString("dueDte")));
            pktPrpMap.put("PartyCnvRt", util.nvl(rs.getString("Exh_Rte")));
            pktPrpMap.put("StkCnvRt", "");
            pktPrpMap.put("termscode", util.nvl(rs.getString("terms_code")));
            pktPrpMap.put("TermRem", util.nvl(rs.getString("Term")));    
            pktPrpMap.put("StkRiskFact",stkRiskFact);
            String brok1 = util.nvl(rs.getString("mb2"));
            String brok2 = util.nvl(rs.getString("sb1"));
            String brok3 = util.nvl(rs.getString("sb2"));
            String brok4 = util.nvl(rs.getString("sb3"));
            String brok1code = util.nvl(rs.getString("mb2code"));
            String brok2code = util.nvl(rs.getString("sb1code"));
            String brok3code = util.nvl(rs.getString("sb2code"));
            String brok4code = util.nvl(rs.getString("sb3code"));
            String cordcode = "";
            if(!brok1code.equals("None") && !brok1code.equals(""))
                cordcode = brok1code;
            else if (!brok1.equals("None"))
                cordcode = brok1;
            
            if(!brok2code.equals("None") && !brok2code.equals(""))
                cordcode = cordcode + "-"+brok2code;
            else if (!brok2.equals("None"))
                cordcode = cordcode + "-" + brok2;
            
            if(!brok3code.equals("None") && !brok3code.equals(""))
                cordcode = cordcode + "-"+brok3code;
            else if (!brok3.equals("None"))
                cordcode = cordcode + "-" + brok3;
            
            if(!brok4code.equals("None") && !brok4code.equals(""))
                cordcode = cordcode + "-"+brok4code;
            else if (!brok4.equals("None"))
                cordcode = cordcode + "-" + brok4;
            cordcode = cordcode.replaceFirst("\\-", "");
            pktPrpMap.put("CordCode", cordcode);
            pktPrpMap.put("Cord", util.nvl(rs.getString("cordper")));
            pktPrpMap.put("CordCnvRt", "");
            String com1 = util.nvl(rs.getString("aadat_comm"));
            if(com1.equals("0"))
            com1="";
            pktPrpMap.put("Com1",com1);
            pktPrpMap.put("EntSrNo", "1");
            pktPrpMap.put("size", util.nvl(rs.getString("sz")));
            pktPrpMap.put("Com2", "");
            pktPrpMap.put("Com3", "");
            pktPrpMap.put("Com4", "");
            pktPrpMap.put("MFG_PRI","");
            pktPrpMap.put("UsRate", util.nvl(rs.getString("UsRate")));
            pktPrpMap.put("FixRate", util.nvl(rs.getString("fixRate")));
            pktPrpMap.put("FixCnvRt", "");
            pktPrpMap.put("RemOut", util.nvl(rs.getString("rmk")));
            pktPrpMap.put("RemNoChange", "");
            pktPrpMap.put("Quality", "GIA");
            pktPrpMap.put("Shape", util.nvl(rs.getString("Shape")));
            pktPrpMap.put("Dept", util.nvl(rs.getString("Dept")));
            pktPrpMap.put("StkTermsPer", "");
            pktPrpMap.put("Expthru", "");
            pktPrpMap.put("ExpPer", "");
            pktList.add(pktPrpMap);
        }
        rs.close();
            pst.close();

//            sqlQ="WITH SAL as (\n" + 
//            " select D.idn,\n" + 
//            "  N.alias partycode,\n" + 
//            "  N.CO_NME nme,N.cp PrimaryContact,\n" + 
//            "  DECODE (d.aadat_paid, 'Y', Byr.Get_Nm(D.aadat_Idn), '') mb1,\n" + 
//            "  Byr.Get_Nm(D.Mbrk1_Idn) mb2,\n" + 
//            "  Byr.Get_Nm(D.Mbrk2_Idn) sb1,\n" + 
//            "  Byr.Get_Nm(D.Mbrk3_Idn) sb2,\n" + 
//            "  Byr.Get_Nm(D.Mbrk4_Idn) Sb3,\n" + 
//            "  Byr.Get_Nmcode(D.Aadat_Idn) Mb1code,\n" + 
//            "  Byr.Get_Nmcode(D.Mbrk1_Idn) Mb2code,\n" + 
//            "  Byr.Get_Nmcode(D.Mbrk2_Idn) Sb1code,\n" + 
//            "  Byr.Get_Nmcode(D.Mbrk3_Idn) Sb2code,\n" + 
//            "  Byr.get_nmcode(D.Mbrk4_Idn) sb3code,\n" + 
//            "  Nvl(Nvl(Nvl(D.Brk1_Comm, D.Brk2_Comm), D.Brk3_Comm), D.Brk4_Comm) cordper,\n" + 
//            "  (DECODE (d.aadat_paid, 'Y', DECODE (d.mbrk1_paid,'Y',NVL (d.brk1_comm, 0)+NVL (d.aadat_comm, 0),NVL (d.aadat_comm, 0)), 0) * -1) aadat_comm,\n" + 
//            "  Decode(D.Cur, 'USD', 'PSALE-D', 'INR', 'PSALE-RS', 'PSALE-D/RS') Cur\n" + 
//            "  , decode(D.EXH_RTE , '1' , '' , D.EXH_RTE) EXH_RTE\n" + 
//            "  , T.Dys DueDays, to_Char(trunc(d.dte) + t.dys,'dd/mm/rrrr') dueDte\n" + 
//            "  , T.term, T.terms_code\n" + 
//            "  , to_Char(D.dte, 'dd/mm/rrrr') dte\n" + 
//            "  ,( decode(upper(n.grp_co_nme), 'HK DOM', 2.5, 0)*-1) stkRiskFact\n" + 
//            " from MDLV D, NME_V N, NmeRel R, Mtrms T\n" + 
//            " where 1 = 1\n" + 
//            " And  nvl(d.fnl_trms_idn,d.nmerel_idn) = R.nmerel_idn\n" + 
//            "  and R.trms_idn = T.idn\n" + 
//            "  and R.nme_idn = N.nme_idn\n" +
//            "  AND NVL(D.INV_NME_IDN, D.NME_IDN) in (select NME_IDN from CO_BRANCH_V)\n" + 
//               
//             ""+ conQbranch+"  \n" + 
//            ")\n" + 
//            "select --MSAL.idn, \n" + 
//            "MSAL.dte, MSAL.partycode, MSAL.nme,MSAL.PrimaryContact,EX.txt emp, MSAL.mb1, MSAL.mb2, MSAL.sb1, MSAL.sb2, MSAL.sb3,MSAL.mb1code, MSAL.mb2code, MSAL.sb1code, MSAL.sb2code, MSAL.sb3code\n" + 
//            ", MSAL.cordper, MSAL.aadat_comm, MSAL.cur, MSAL.exh_rte, MSAL.dueDays, MSAL.dueDte, MSAL.term, MSAL.terms_code\n" + 
//            ", MSAL.stkRiskFact\n" + 
//            ", decode(sd.val, '18-96-GIA', '18', '96') sz\n" + 
//            ", decode(sd.val, '18-96-GIA', '18G', '96G') Dept\n" + 
//            ",   decode(sh.val, 'ROUND', 'R', 'F')  Shape , trunc(sum(pkt.cts), 2) cts\n" + 
//            "  , to_char(trunc(((sum(trunc(pkt.cts,2)*pkt.fnl_usd) / sum(trunc(pkt.cts,2)))), 2),'999999999990.00') UsRate  ,  to_char(trunc(((sum(trunc(pkt.cts,2)*pkt.fnl_usd) * ((100 + nvl(MSAL.stkRiskFact, 0))/100) * ((100 - nvl(MSAL.cordPer, 0))/100) * ((100 + nvl(MSAL.aadat_comm, 0))/100)/ sum(trunc(pkt.cts,2)))), 2),'99999999990.00') fixRate , pkt.rmk  , sd.srt\n" + 
//            "  From SAL MSAL, DLV_PKT_DTL_V PKT, MSTK M, STK_DTL SD, STK_DTL SH,STK_DTL CTG,STK_DTL EX\n" + 
//            "Where 1 = 1\n" + 
//            "and MSAL.idn = PKT.IDN\n" + 
//            "and PKT.MSTK_IDN = M.IDN\n" + 
//            "and M.PKT_TY = 'NR'\n" + 
//            "and M.IDN = SD.MSTK_IDN and SD.GRP = 1 and SD.MPRP = 'DEPT'  "+deptQ+"  and SD.VAL = SD.VAL   and SD.VAL = SD.VAL   and M.IDN = SH.MSTK_IDN and SH.GRP = 1 and SH.MPRP = 'SHAPE'\n" + 
//            "and M.IDN = CTG.MSTK_IDN and CTG.GRP = 1 and CTG.MPRP = 'STK-CTG' and CTG.val in('A','B') and PKT.STT in ('DLV') and M.IDN = EX.MSTK_IDN and EX.GRP = 1 and EX.MPRP = 'SAL_EMP'\n" + 
//            "group by --MSAL.idn, \n" + 
//            "MSAL.dte, MSAL.partycode, MSAL.nme,MSAL.PrimaryContact,EX.txt, MSAL.mb1, MSAL.mb2, MSAL.sb1, MSAL.sb2, MSAL.sb3, MSAL.mb1code, MSAL.mb2code, MSAL.sb1code, MSAL.sb2code, MSAL.sb3code, MSAL.cordper, MSAL.aadat_comm, MSAL.cur, MSAL.exh_rte, MSAL.dueDays, MSAL.dueDte, MSAL.term, MSAL.terms_code, MSAL.stkRiskFact, decode(sd.val, '18-96-GIA', '18', '96'), decode(sd.val, '18-96-GIA', '18G', '96G'), decode(sh.val, 'ROUND', 'R', 'F'), pkt.rmk, sd.srt Order By Shape,MSAL.partycode";
//            rs = db.execSql("sqlQ", sqlQ, ary);
//            i = 0;
//            while (rs.next()) {
//                HashMap pktPrpMap = new HashMap();
//                String stkRiskFact=util.nvl(rs.getString("stkRiskFact"));
//                if(stkRiskFact.equals("0"))
//                stkRiskFact="";
//                pktPrpMap.put("Sr", String.valueOf(++i));
//                pktPrpMap.put("VchYN", "");
//                pktPrpMap.put("InvNo", "");
//                pktPrpMap.put("cts", util.nvl(rs.getString("cts")));
//                pktPrpMap.put("TransDate", util.nvl(rs.getString("dte")));
//                pktPrpMap.put("PartyCode", util.nvl(rs.getString("partycode")));
//                pktPrpMap.put("nme", util.nvl(rs.getString("nme")));
//                pktPrpMap.put("PrimaryContact", util.nvl(rs.getString("PrimaryContact")));
//                pktPrpMap.put("emp", util.nvl(rs.getString("emp")));
//                pktPrpMap.put("Transaction", util.nvl(rs.getString("Cur")));
//                pktPrpMap.put("RndNo", "");
//                pktPrpMap.put("DueDays", util.nvl(rs.getString("Duedays")));
//                pktPrpMap.put("DueDate", util.nvl(rs.getString("dueDte")));
//                pktPrpMap.put("PartyCnvRt", util.nvl(rs.getString("Exh_Rte")));
//                pktPrpMap.put("StkCnvRt", "");
//                pktPrpMap.put("termscode", util.nvl(rs.getString("terms_code")));
//                pktPrpMap.put("TermRem", util.nvl(rs.getString("Term")));    
//                pktPrpMap.put("StkRiskFact",stkRiskFact);
//                String brok1 = util.nvl(rs.getString("mb2"));
//                String brok2 = util.nvl(rs.getString("sb1"));
//                String brok3 = util.nvl(rs.getString("sb2"));
//                String brok4 = util.nvl(rs.getString("sb3"));
//                String brok1code = util.nvl(rs.getString("mb2code"));
//                String brok2code = util.nvl(rs.getString("sb1code"));
//                String brok3code = util.nvl(rs.getString("sb2code"));
//                String brok4code = util.nvl(rs.getString("sb3code"));
//                String cordcode = "";
//                if(!brok1code.equals("None") && !brok1code.equals(""))
//                    cordcode = brok1code;
//                else if (!brok1.equals("None"))
//                    cordcode = brok1;
//                
//                if(!brok2code.equals("None") && !brok2code.equals(""))
//                    cordcode = cordcode + "-"+brok2code;
//                else if (!brok2.equals("None"))
//                    cordcode = cordcode + "-" + brok2;
//                
//                if(!brok3code.equals("None") && !brok3code.equals(""))
//                    cordcode = cordcode + "-"+brok3code;
//                else if (!brok3.equals("None"))
//                    cordcode = cordcode + "-" + brok3;
//                
//                if(!brok4code.equals("None") && !brok4code.equals(""))
//                    cordcode = cordcode + "-"+brok4code;
//                else if (!brok4.equals("None"))
//                    cordcode = cordcode + "-" + brok4;
//                cordcode = cordcode.replaceFirst("\\-", "");
//                pktPrpMap.put("CordCode", cordcode);
//                pktPrpMap.put("Cord", util.nvl(rs.getString("cordper")));
//                pktPrpMap.put("CordCnvRt", "");
//                String com1 = util.nvl(rs.getString("aadat_comm"));
//                if(com1.equals("0"))
//                com1="";
//                pktPrpMap.put("Com1",com1);
//                pktPrpMap.put("EntSrNo", "1");
//                pktPrpMap.put("size", util.nvl(rs.getString("sz")));
//                pktPrpMap.put("Com2", "");
//                pktPrpMap.put("Com3", "");
//                pktPrpMap.put("Com4", "");
//                pktPrpMap.put("MFG_PRI","");
//                pktPrpMap.put("UsRate", util.nvl(rs.getString("UsRate")));
//                pktPrpMap.put("FixRate", util.nvl(rs.getString("fixRate")));
//                pktPrpMap.put("FixCnvRt", "");
//                pktPrpMap.put("RemOut", util.nvl(rs.getString("rmk")));
//                pktPrpMap.put("RemNoChange", "");
//                pktPrpMap.put("Quality", "GIA");
//                pktPrpMap.put("Shape", util.nvl(rs.getString("Shape")));
//                pktPrpMap.put("Dept", util.nvl(rs.getString("Dept")));
//                pktPrpMap.put("StkTermsPer", "");
//                pktPrpMap.put("Expthru", "");
//                pktPrpMap.put("ExpPer", "");
//                pktList.add(pktPrpMap);
//            }
//            rs.close();
        udf.setPktList(pktList);
        req.setAttribute("View", "Y");
        udf.resetALL();
            util.updAccessLog(req,res,"Rpt Action", "singlrsalerpt end");
        return am.findForward("singlesalerpt");
        }
    }

    public ActionForward loaddailymixtrf(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "loaddailymixtrf start");
        ReportForm udf = (ReportForm)form;
        GenericInterface genericInt = new GenericImpl();
        udf.reset();
            ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DAILYRPTGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DAILYRPTGNCSrch");
        info.setGncPrpLst(assortSrchList);
            util.updAccessLog(req,res,"Rpt Action", "loaddailymixtrf end");
        return am.findForward("loaddailymixtrf");
        }
    }

    public ActionForward fetchdailymixtrf(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "fetchdailymixtrf start");
        ReportForm udf = (ReportForm)form;
        GenericInterface genericInt = new GenericImpl();
        String frmdte = util.nvl((String)udf.getValue("dtefrtrf"));
        String todte = util.nvl((String)udf.getValue("dtetotrf"));
        ResultSet rs = null;
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        String sh = (String)dbinfo.get("SHAPE");
        String shfr = (String)udf.getValue(sh+"_1");
        String shto = (String)udf.getValue(sh+"_2");
        String deptfr = (String)udf.getValue("DEPT_1");
        String deptto =(String)udf.getValue("DEPT_2");
        ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DAILYRPTGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DAILYRPTGNCSrch");
        info.setGncPrpLst(assortSrchList);
        int ct = db.execCall("delete gt", "delete from gt_pkt", new ArrayList());

        String conQdte = " and trunc(b.log_dte)=trunc(sysdate)";
        if (!frmdte.equals("") && !todte.equals(""))
            conQdte =
                    " and trunc(b.log_dte) between to_date('" + frmdte + "' , 'dd-mm-yyyy') and to_date('" + todte + "' , 'dd-mm-yyyy') ";
        if (frmdte.equals("") && !todte.equals(""))
            conQdte =
                    " and trunc(b.log_dte) between to_date('" + todte + "' , 'dd-mm-yyyy') and to_date('" + todte + "' , 'dd-mm-yyyy') ";
        if (!frmdte.equals("") && todte.equals(""))
            conQdte =
                    " and trunc(b.log_dte) between to_date('" + frmdte + "' , 'dd-mm-yyyy') and to_date('" + frmdte + "' , 'dd-mm-yyyy') ";

        String insertGt = "insert into gt_pkt(mstk_idn) \n" +
            "select a.idn from mstk a, mix_trf_log b,stk_dtl c,stk_dtl d \n" +
            " where a.idn = b.frm_idn "+
            " and a.idn = c.mstk_idn  and c.mprp='"+sh+"' and c.grp=1 and a.idn = d.mstk_idn and d.mprp='DEPT' and d.grp=1 ";
            if(shfr.equals("") || shto.equals(""))
            insertGt =insertGt+"and c.srt between nvl(null,c.srt) and nvl(null,c.srt) ";
            else
            insertGt =insertGt+" and c.srt between nvl("+shfr+",c.srt) and nvl("+shto+",c.srt) ";
            if(deptfr.equals("") || deptto.equals(""))
            insertGt =insertGt+"  and d.srt between nvl(null,d.srt) and nvl(null,d.srt) ";
            else
            insertGt =insertGt+"  and d.srt between nvl("+deptfr+",d.srt) and nvl("+deptto+",d.srt) ";
            
            insertGt =insertGt+conQdte + " and a.stt = 'TRF_MRG'  and a.cts <> 0";
        if (cnt.equals("kj")) {
            insertGt = "insert into gt_pkt(mstk_idn) \n" +
                        "select a.idn from mstk a, mix_trf_log b \n" +
                        "where a.idn = b.frm_idn " + conQdte + "and a.stt = 'TRF_MRG' and a.cts <> 0";
        }
        ct = db.execDirUpd("insert gt_pkt", insertGt, new ArrayList());
        ArrayList keylist = new ArrayList();
        ArrayList clrlist = new ArrayList();
        ArrayList mixszlist = new ArrayList();
        HashMap dataDtl = new HashMap();
        String key = "";
        String oldkey = "";
        String loadqry =
            "select count(*) cnt, to_char(sum(cts),'99999990.00') cts,trunc(sum(nvl(upr,cmp)*cts)/sum(cts),0) avg, dept.val dept, sh.val sh, sz.val sz, clr.val clr\n" +
            ", dept.srt, sh.srt, sz.srt, clr.srt\n" +
            "from mstk a, gt_pkt gt, stk_dtl dept, stk_dtl sh, stk_dtl sz, stk_dtl clr\n" +
            "where a.idn = gt.mstk_idn\n" +
            "and a.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT'\n" +
            "and a.idn = sh.mstk_idn and sh.grp = 1 and sh.mprp = '"+sh+"'\n" +
            "and a.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_SIZE'\n" +
            "and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" +
            "group by dept.val, sh.val, sz.val, clr.val, dept.srt, sh.srt, sz.srt, clr.srt\n" +
            "order by dept.srt, sh.srt, sz.srt, clr.srt";
        if (cnt.equals("kj")) {
            loadqry =
                    "select count(*) cnt, sum(cts) cts,trunc(sum(nvl(upr,cmp)*cts)/sum(cts),0) avg, '' dept, sh.val sh, sz.val sz, clr.val clr\n" +
                    ",  sh.srt, sz.srt, clr.srt\n" +
                    "from mstk a, gt_pkt gt, stk_dtl sh, stk_dtl sz, stk_dtl clr\n" +
                    "where a.idn = gt.mstk_idn\n" +
                    "and a.idn = sh.mstk_idn and sh.grp = 1 and sh.mprp = '"+sh+"'\n" +
                    "AND A.IDN = SZ.MSTK_IDN AND SZ.GRP = 1 AND SZ.MPRP = 'MIX_SIZE'\n" +
                    "and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" +
                    "group by sh.val, sz.val, clr.val, sh.srt, sz.srt, clr.srt\n" +
                    "order by sh.srt, sz.srt, clr.srt";
        }
          ArrayList outLst = db.execSqlLst("loadqry", loadqry, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String shape = util.nvl(rs.getString("sh"));
            String size = util.nvl(rs.getString("sz"));
            String clr = util.nvl(rs.getString("clr"));
            String dept = util.nvl(rs.getString("dept"));
            key = dept + "_" + shape;
            if (!clrlist.contains(clr))
                clrlist.add(clr);
            if (!keylist.contains(key))
                keylist.add(key);
            dataDtl.put(key + "_" + size + "_" + clr + "_CTS", util.nvl(rs.getString("cts")));
            dataDtl.put(key + "_" + size + "_" + clr + "_AVG", util.nvl(rs.getString("avg")));
        }
            rs.close();
            pst.close();
        String sizeQ =
            "select count(*) cnt, to_char(sum(cts),'99999990.00') cts,trunc(sum(nvl(upr,cmp)*cts)/sum(cts),0) avg, dept.val dept, sh.val sh, sz.val sz\n" +
            ", dept.srt, sh.srt, sz.srt\n" +
            "from mstk a, gt_pkt gt, stk_dtl dept, stk_dtl sh, stk_dtl sz\n" +
            "where a.idn = gt.mstk_idn\n" +
            "and a.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT'\n" +
            "and a.idn = sh.mstk_idn and sh.grp = 1 and sh.mprp = '"+sh+"'\n" +
            "and a.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_SIZE'\n" +
            "GROUP BY DEPT.VAL, SH.VAL, SZ.VAL,  DEPT.SRT, SH.SRT, SZ.SRT\n" +
            "order by dept.srt, sh.srt, sz.srt";
        if (cnt.equals("kj")) {
            sizeQ =
                    "select count(*) cnt, to_char(sum(cts),'99999990.00') cts,trunc(sum(nvl(upr,cmp)*cts)/sum(cts),0) avg,'' dept, sh.val sh, sz.val sz\n" +
                    ", sh.srt, sz.srt\n" +
                    "from mstk a, gt_pkt gt, stk_dtl sh, stk_dtl sz\n" +
                    "where a.idn = gt.mstk_idn\n" +
                    "and a.idn = sh.mstk_idn and sh.grp = 1 and sh.mprp = '"+sh+"'\n" +
                    "and a.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_SIZE'\n" +
                    "GROUP BY SH.VAL, SZ.VAL,  SH.SRT, SZ.SRT\n" +
                    "order by sh.srt, sz.srt";
        }
           outLst = db.execSqlLst("Size Wise total", sizeQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String shape = util.nvl(rs.getString("sh"));
            String size = util.nvl(rs.getString("sz"));
            String dept = util.nvl(rs.getString("dept"));
            key = dept + "_" + shape;
            if (oldkey.equals(""))
                oldkey = key;
            if (!oldkey.equals(key)) {
                dataDtl.put(oldkey, mixszlist);
                mixszlist = new ArrayList();
                oldkey = key;
            }
            dataDtl.put(key + "_" + size + "_CTS", util.nvl(rs.getString("cts")));
            dataDtl.put(key + "_" + size + "_AVG", util.nvl(rs.getString("avg")));
            mixszlist.add(size);
        }
            rs.close();
            pst.close();
        dataDtl.put(oldkey, mixszlist);
        String clrQ =
            "select count(*) cnt, to_char(sum(cts),'99999990.00') cts,trunc(sum(nvl(upr,cmp)*cts)/sum(cts),0) avg, dept.val dept, sh.val sh, clr.val clr\n" +
            ", dept.srt, sh.srt, clr.srt\n" +
            "from mstk a, gt_pkt gt, stk_dtl dept, stk_dtl sh, stk_dtl clr\n" +
            "where a.idn = gt.mstk_idn\n" +
            "and a.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT'\n" +
            "and a.idn = sh.mstk_idn and sh.grp = 1 and sh.mprp = '"+sh+"'\n" +
            "and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" +
            "GROUP BY DEPT.VAL, SH.VAL, clr.val,  DEPT.SRT, SH.SRT, CLR.SRT\n" +
            "order by dept.srt, sh.srt, clr.srt";
        if (cnt.equals("kj")) {
            clrQ =
"select count(*) cnt, to_char(sum(cts),'99999990.00') cts,trunc(sum(nvl(upr,cmp)*cts)/sum(cts),0) avg, '' dept, sh.val sh, clr.val clr\n" +
                    ", sh.srt, clr.srt\n" +
                    "from mstk a, gt_pkt gt, stk_dtl sh, stk_dtl clr\n" +
                    "where a.idn = gt.mstk_idn\n" +
                    "and a.idn = sh.mstk_idn and sh.grp = 1 and sh.mprp = '"+sh+"'\n" +
                    "and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" +
                    "GROUP BY SH.VAL, clr.val,  SH.SRT, CLR.SRT\n" +
                    "order by sh.srt, clr.srt";
        }
          outLst = db.execSqlLst("Mix Clarity Wise total", clrQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String shape = util.nvl(rs.getString("sh"));
            String clr = util.nvl(rs.getString("clr"));
            String dept = util.nvl(rs.getString("dept"));
            key = dept + "_" + shape;
            dataDtl.put(key + "_" + clr + "_CTS", util.nvl(rs.getString("cts")));
            dataDtl.put(key + "_" + clr + "_AVG", util.nvl(rs.getString("avg")));
        }
            rs.close();
            pst.close();
        String gttlQ =
            "select count(*) cnt, to_char(sum(cts),'99999990.00') cts,trunc(sum(nvl(upr,cmp)*cts)/sum(cts),0) avg, dept.val dept, sh.val sh\n" +
            ", dept.srt, sh.srt\n" +
            "from mstk a, gt_pkt gt, stk_dtl dept, stk_dtl sh\n" +
            "where a.idn = gt.mstk_idn\n" +
            "and a.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT'\n" +
            "and a.idn = sh.mstk_idn and sh.grp = 1 and sh.mprp = '"+sh+"'\n" +
            "GROUP BY DEPT.VAL, SH.VAL,  DEPT.SRT, SH.SRT\n" +
            "order by dept.srt, sh.srt";
        if (cnt.equals("kj")) {
            gttlQ =
                    "select count(*) cnt, to_char(sum(cts),'99999990.00') cts,trunc(sum(nvl(upr,cmp)*cts)/sum(cts),0) avg, '' dept, sh.val sh\n" +
                    ", sh.srt\n" +
                    "from mstk a, gt_pkt gt, stk_dtl sh, stk_dtl sz, stk_dtl clr\n" +
                    "where a.idn = gt.mstk_idn\n" +
                    "and a.idn = sh.mstk_idn and sh.grp = 1 and sh.mprp = '"+sh+"'\n" +
                    "and a.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_SIZE'\n" +
                    "and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" +
                    "GROUP BY SH.VAL,  SH.SRT\n" +
                    "order by sh.srt";
        }
          outLst = db.execSqlLst("Dept Shape total", gttlQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String shape = util.nvl(rs.getString("sh"));
            String dept = util.nvl(rs.getString("dept"));
            key = dept + "_" + shape;
            dataDtl.put(key + "_CTS", util.nvl(rs.getString("cts")));
            dataDtl.put(key + "_AVG", util.nvl(rs.getString("avg")));
        }
            rs.close();
            pst.close();
        udf.reset();
        if (dataDtl != null && dataDtl.size() > 0) {
            req.setAttribute("view", "Y");
            session.setAttribute("clrlist", clrlist);
            session.setAttribute("dataDtl", dataDtl);
            session.setAttribute("keylist", keylist);
        }
        udf.setValue("dtefrtrf", frmdte);
        udf.setValue("dtetotrf", todte);
            util.updAccessLog(req,res,"Rpt Action", "fetchdailymixtrf end");
        return am.findForward("loaddailymixtrf");
        }
    }
    
    public ActionForward loadrtn(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "loadrtn start");
        ReportForm udf = (ReportForm)form;
        udf.reset();
        SearchQuery srchQuery = new SearchQuery();
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("REPORT_RTN");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("REPORT_RTN");
        allPageDtl.put("REPORT_RTN",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        
        ArrayList empList= srchQuery.getByrList(req,res);
        udf.setByrList(empList); 
            util.updAccessLog(req,res,"Rpt Action", "loadrtn end");
        return am.findForward("loadrtn");
        }
    }
    
    public ActionForward loadmonthly(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "loadmonthly start");
        ReportForm udf = (ReportForm)form;
        udf.reset();
            util.updAccessLog(req,res,"Rpt Action", "loadmonthly end");
        return am.findForward("loadmonthly");
        }
    }
    
    public ActionForward fetchdmonthly(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "fetchdmonthly start");
        ReportForm udf = (ReportForm)form;
        ArrayList commshLst=new ArrayList();
        ArrayList shLst=new ArrayList();
        ArrayList szLst=new ArrayList();
        ArrayList clrLst=new ArrayList();
        ArrayList ary=new ArrayList(); 
        HashMap dataDtl=new HashMap();
            
        String dept=util.nvl((String) udf.getValue("dept"));
        String conQ=" in ('ROUND') ";
        String deptQ=" and DEPT.val=? ";
        if(dept.equals("18-96-FANCY"))
            conQ=" in ('PEAR','MARQUISE') ";
        ary=new ArrayList();
        ary.add(dept);  
        ary.add(dept); 
        ary.add(dept);
        ary.add(dept);
        String commshQ="Select SH.val shape,SZ.val mixsz,CLR.val mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" + 
        "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) rm\n" + 
        "    From MSTK A\n" + 
        "    , STK_DTL SH \n" + 
        "    , STK_DTL DEPT \n" + 
        "    , STK_DTL CLR \n" + 
        "    , STK_DTL SZ\n" + 
        "    , STK_DTL DYS\n" + 
        "     Where a.STT = 'MKAV' and a.pkt_ty <> 'NR'  \n" + 
        "    and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val \n" +conQ+
        "    And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" + deptQ+
        "    and a.idn = SZ.mstk_idn and SZ.grp = 1 and SZ.mprp = 'MIX_SIZE'\n" + 
        "    and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" + 
        "    and upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')) not in('NATTS','TLB','TLC','FCCUT','FCCOL','SAM','REP','RSP','MIX')\n" + 
        "    And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0 group by SH.val,SZ.val, CLR.val\n" + 
        "union\n" + 
        "Select SH.val shape,SZ.val mixsz,'TTL' mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" + 
        "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) rm\n" + 
        "    From MSTK A\n" + 
        "    , STK_DTL SH \n" + 
        "    , STK_DTL DEPT \n" + 
        "    , STK_DTL CLR \n" + 
        "    , STK_DTL SZ\n" + 
        "    , STK_DTL DYS\n" + 
        "     Where a.STT = 'MKAV' and a.pkt_ty <> 'NR'  \n" + 
        "    and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val  \n" +conQ+
        "    And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" + deptQ+
        "    and a.idn = SZ.mstk_idn and SZ.grp = 1 and SZ.mprp = 'MIX_SIZE'\n" + 
        "    and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" + 
        "        and upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')) not in('NATTS','TLB','TLC','FCCUT','FCCOL','SAM','REP','RSP','MIX')\n" + 
        "    And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0 group by SH.val,SZ.val\n"+
        "    union\n"+
        "      Select SH.val shape,'TTL' mixsz,'TTL' mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" + 
        "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) rm\n" + 
        "    From MSTK A\n" + 
        "    , STK_DTL SH \n" + 
        "    , STK_DTL DEPT \n" + 
        "    , STK_DTL CLR \n" + 
        "    , STK_DTL DYS\n" + 
        "     Where a.STT = 'MKAV' and a.pkt_ty <> 'NR'  \n" + 
        "    and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val  \n" +conQ+
        "  And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" +  deptQ+
        "    and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" + 
        "        and upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')) not in('NATTS','TLB','TLC','FCCUT','FCCOL','SAM','REP','RSP','MIX')\n" + 
        "    And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0 group by SH.val\n"+
        "    union\n" + 
        "Select 'TTL' shape,'TTL' mixsz,'TTL' mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" + 
        "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) rm\n" + 
        "    From MSTK A\n" + 
        "    , STK_DTL SH \n" + 
        "    , STK_DTL DEPT \n" + 
        "    , STK_DTL CLR \n" + 
        "    , STK_DTL SZ\n" + 
        "    , STK_DTL DYS\n" + 
        "     Where a.STT = 'MKAV' and a.pkt_ty <> 'NR'  \n" + 
        "    and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val \n" +conQ+ 
        "    And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" + deptQ+
        "    and a.idn = SZ.mstk_idn and SZ.grp = 1 and SZ.mprp = 'MIX_SIZE'\n" + 
        "    and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" + 
        "        and upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')) not in('NATTS','TLB','TLC','FCCUT','FCCOL','SAM','REP','RSP','MIX')\n" + 
        "    And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0";
            
            
          ArrayList outLst = db.execSqlLst("Common Shape", commshQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String shape=util.nvl((String)rs.getString("shape"));
            String mixsz=util.nvl((String)rs.getString("mixsz"));
            String mixclr=util.nvl((String)rs.getString("mixclr"));
            String key=shape+"_"+mixclr+"_"+mixsz;
        dataDtl.put(key+"_CTS", util.nvl((String)rs.getString("cts")));
        dataDtl.put(key+"_AVG", util.nvl((String)rs.getString("avg")));
        dataDtl.put(key+"_RM", util.nvl((String)rs.getString("rm")));
        if(!commshLst.contains(shape) && !shape.equals("TTL"))
            commshLst.add(shape);
        if(!szLst.contains(mixsz) && !mixsz.equals("TTL"))
            szLst.add(mixsz);
        if(!clrLst.contains(mixclr) && !mixclr.equals("TTL"))
            clrLst.add(mixclr);
        }
        rs.close();
            pst.close();
            ary=new ArrayList();
            ary.add(dept);  
            ary.add(dept); 
            ary.add(dept);
        String fancyshQ="Select SH.val shape,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" + 
        "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) rm\n" + 
        "    From MSTK A\n" + 
        "    , STK_DTL SH \n" + 
        "    , STK_DTL DEPT \n" + 
        "    , STK_DTL DYS\n" + 
        "     Where a.STT = 'MKAV' and a.pkt_ty <> 'NR'  \n" + 
        "    and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val not \n" +conQ+
        "    And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" + deptQ+
        "    And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0 group by SH.val\n" + 
        "union\n" + 
        "Select 'FANCYSHTTL' shape,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" + 
        "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) rm\n" + 
        "    From MSTK A\n" + 
        "    , STK_DTL SH \n" + 
        "    , STK_DTL DEPT \n" + 
        "    , STK_DTL DYS\n" + 
        "     Where a.STT = 'MKAV' and a.pkt_ty <> 'NR'  \n" + 
        "    and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val not \n" +conQ+  
        "    And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n"+ deptQ+ 
        "    And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0";  
            ary=new ArrayList();
            ary.add(dept);  
            ary.add(dept); 
           outLst = db.execSqlLst("Fancy Shape", fancyshQ, ary);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while(rs.next()){
            String shape=util.nvl((String)rs.getString("shape"));
            dataDtl.put(shape+"_CTS", util.nvl((String)rs.getString("cts")));
            dataDtl.put(shape+"_AVG", util.nvl((String)rs.getString("avg")));
            dataDtl.put(shape+"_RM", util.nvl((String)rs.getString("rm")));
            if(!shLst.contains(shape) && !shape.equals("FANCYSHTTL"))
            shLst.add(shape);
            }
            rs.close();
            pst.close();
        String allclrQ="Select CLR.val mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" + 
        "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) rm\n" + 
        "    From MSTK A\n" + 
        "    , STK_DTL DEPT \n" + 
        "    , STK_DTL CLR \n" + 
        "    , STK_DTL DYS\n" + 
        "     Where a.STT = 'MKAV' and a.pkt_ty <> 'NR'  \n" + 
        "    And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" + deptQ+ 
        "    and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY' and clr.val in('FcCut','FcCol','SAM','REP','RSP','MIX')\n" + 
        "    And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0 group by CLR.val \n" + 
        "    union\n" + 
        "       Select 'ALLCLRTTL' mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" + 
        "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) rm\n" + 
        "    From MSTK A\n" + 
        "    , STK_DTL DEPT \n" + 
        "    , STK_DTL CLR \n" + 
        "    , STK_DTL DYS\n" + 
        "     Where a.STT = 'MKAV' and a.pkt_ty <> 'NR'  \n" + 
        "    And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" + deptQ+ 
        "    and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY' and clr.val in('FcCut','FcCol','SAM','REP','RSP','MIX')\n" + 
        "    And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0";  
            ary=new ArrayList();
            ary.add(dept);  
            ary.add(dept); 
          outLst = db.execSqlLst("All Clarity", allclrQ, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while(rs.next()){
            String mixclr=util.nvl((String)rs.getString("mixclr"));
            dataDtl.put(mixclr+"_CTS", util.nvl((String)rs.getString("cts")));
            dataDtl.put(mixclr+"_AVG", util.nvl((String)rs.getString("avg")));
            dataDtl.put(mixclr+"_RM", util.nvl((String)rs.getString("rm")));
            }
                        rs.close();
            pst.close();
        String rdclrQ=" Select upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')) mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" + 
        "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) rm\n" + 
        "    From MSTK A\n" + 
        "    , STK_DTL SH \n" + 
        "    , STK_DTL DEPT \n" + 
        "    , STK_DTL CLR \n" + 
        "    , STK_DTL DYS\n" + 
        "     Where a.STT = 'MKAV' and a.pkt_ty <> 'NR'  \n" + 
        "      and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val \n" +conQ+
        "    And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" + deptQ+ 
        "    and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" + 
        "    and upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', ''))  in('NATTS','TLB','TLC')\n" + 
        "    And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0 group by upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')) \n" + 
        "    union\n" + 
        "    Select 'ROUNDTTTL' mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" + 
        "    trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) rm\n" + 
        "    From MSTK A\n" + 
        "    , STK_DTL SH \n" + 
        "    , STK_DTL DEPT \n" + 
        "    , STK_DTL CLR \n" + 
        "    , STK_DTL DYS\n" + 
        "     Where a.STT = 'MKAV' and a.pkt_ty <> 'NR'  \n" + 
        "      and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val \n" +conQ+
        "    And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" + deptQ+
        "    and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" + 
        "    and upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', ''))  in('NATTS','TLB','TLC')\n" + 
        "    And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0";
            ary=new ArrayList();
            ary.add(dept);  
            ary.add(dept); 
          outLst = db.execSqlLst("Rd Clarity", rdclrQ, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while(rs.next()){
            String mixclr=util.nvl((String)rs.getString("mixclr"));
            dataDtl.put(mixclr+"_CTS", util.nvl((String)rs.getString("cts")));
            dataDtl.put(mixclr+"_AVG", util.nvl((String)rs.getString("avg")));
            dataDtl.put(mixclr+"_RM", util.nvl((String)rs.getString("rm")));
            }
                        rs.close();
            pst.close();
        String mseriSttQ="'MX_AS_AV','MX_AS_IS','MX_AS_FN','MX_FN_IS','MX_FN','MX_LB_IS','MX_FN_AV','MX_AS_RC','MX_RC_IS'";
        if(dept.equals("96-UP-MIX"))
            mseriSttQ="'AS_FN_SMX','AS_SMX','SMX_GR_IS','FN_SMX_IS','MX_FN_AV','MX_FN_IS','MX_AS_RC','MX_RC_IS','MX_FN'";
        String mserQ="Select 'M.SERI' mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" + 
        "    trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,avg(0) rm\n" + 
        "    From MSTK A\n" + 
        "    , STK_DTL DEPT \n" + 
        "    , STK_DTL CLR \n" + 
        "     Where a.STT in("+mseriSttQ+") and a.pkt_ty <> 'NR'  \n" +  
        "    And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" + deptQ+
        "    and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" + 
        "    and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0 \n" +
            "union\n" +
            "    Select 'GTTL' mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" + 
            "    trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) rm\n" + 
            "    From MSTK A\n" + 
            "    , STK_DTL DEPT \n" + 
            "    , STK_DTL CLR \n" + 
            "    , STK_DTL DYS\n" + 
            "     Where a.STT in('MKAV') and a.pkt_ty <> 'NR'  \n" + 
            "    And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" + deptQ+ 
            "    and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" + 
            "    And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0";
            ary=new ArrayList();
            ary.add(dept);  
            ary.add(dept);  
          outLst = db.execSqlLst("M.SERI", mserQ, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while(rs.next()){
            String mixclr=util.nvl((String)rs.getString("mixclr"));
            dataDtl.put(mixclr+"_CTS", util.nvl((String)rs.getString("cts"),"0"));
            dataDtl.put(mixclr+"_AVG", util.nvl((String)rs.getString("avg"),"0"));
            dataDtl.put(mixclr+"_RM", util.nvl((String)rs.getString("rm"),"0"));
            }
            rs.close();
            pst.close();
            
            dataDtl.put("DEPT", dept);
            req.setAttribute("commshLst", commshLst);
            req.setAttribute("shLst", shLst);
            req.setAttribute("szLst", szLst);
            req.setAttribute("clrLst", clrLst);
            req.setAttribute("dataDtl", dataDtl);
            req.setAttribute("view", "Y");
            util.updAccessLog(req,res,"Rpt Action", "fetchdmonthly end");
        return am.findForward("loadmonthly");
        }
    }
    
    public ActionForward loadweekly(ActionMapping am, ActionForm form, HttpServletRequest req,
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
        util.updAccessLog(req,res,"Rpt Action", "loadweekly start");
    ReportForm udf = (ReportForm)form;
    udf.reset();
        util.updAccessLog(req,res,"Rpt Action", "loadweekly end");
    return am.findForward("loadweekly");
    }
    }

    public ActionForward fetchdweekly(ActionMapping am, ActionForm form, HttpServletRequest req,
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
        util.updAccessLog(req,res,"Rpt Action", "fetchdweekly start");
    ReportForm udf = (ReportForm)form;
    ArrayList commshLst=new ArrayList();
    ArrayList shLst=new ArrayList();
    ArrayList szLst=new ArrayList();
    ArrayList clrLst=new ArrayList();
    ArrayList ary=new ArrayList();
    HashMap dataDtl=new HashMap();

    String dept=util.nvl((String) udf.getValue("dept"));
    String conQ=" in ('ROUND') ";
    String deptQ=" and DEPT.val=? ";
    if(dept.equals("18-96-FANCY"))
    conQ=" in ('PEAR','MARQUISE') ";
    ary=new ArrayList();
    ary.add(dept);
    ary.add(dept);
    ary.add(dept);
    String commshQ="Select SH.val shape,SZ.val mixsz,CLR.val mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" +
    "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) days,trunc(sum(DYSPCT.num),0) daysPct,trunc(sum(SRT.num),0) srt\n" +
    " From MSTK A\n" +
    " , STK_DTL SH\n" +
    " , STK_DTL DEPT\n" +
    " , STK_DTL CLR\n" +
    " , STK_DTL SZ\n" +
    " , STK_DTL DYS\n" +
    " , STK_DTL DYSPCT\n" +
    " , STK_DTL SRT\n" +
    " Where a.STT = 'MKAV' and a.pkt_ty <> 'NR' \n" +
    " and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val\n" +
    " "+conQ+" And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" +
    " "+deptQ+" and a.idn = SZ.mstk_idn and SZ.grp = 1 and SZ.mprp = 'MIX_SIZE'\n" +
    " and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" +
    " and upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')) not in('NATTS','TLB','TLC','FCCUT','FCCOL','SAM','REP','RSP','MIX')\n" +
    " And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS'\n" +
    " And DYS.Mstk_Idn = DYSPCT.Mstk_Idn And DYSPCT.Grp = 1 And DYSPCT.Mprp = 'IN_STK_PCT'\n" +
    " And DYSPCT.Mstk_Idn = SRT.Mstk_Idn And SRT.Grp = 1 And SRT.Mprp = 'MIX_RTE'\n" +
    " and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0 group by SH.val,SZ.val, CLR.val\n" +
    "union\n" +
    "Select SH.val shape,SZ.val mixsz,'TTL' mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" +
    "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) days,trunc(sum(DYSPCT.num),0) daysPct,trunc(sum(SRT.num),0) srt\n" +
    " From MSTK A\n" +
    " , STK_DTL SH\n" +
    " , STK_DTL DEPT\n" +
    " , STK_DTL CLR\n" +
    " , STK_DTL SZ\n" +
    " , STK_DTL DYS\n" +
    " , STK_DTL DYSPCT\n" +
    " , STK_DTL SRT\n" +
    " Where a.STT = 'MKAV' and a.pkt_ty <> 'NR' \n" +
    " and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val \n" +
    " "+conQ+" And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" +
    " "+deptQ+" and a.idn = SZ.mstk_idn and SZ.grp = 1 and SZ.mprp = 'MIX_SIZE'\n" +
    " and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" +
    " and upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')) not in('NATTS','TLB','TLC','FCCUT','FCCOL','SAM','REP','RSP','MIX')\n" +
    " And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS'\n" +
    " And DYS.Mstk_Idn = DYSPCT.Mstk_Idn And DYSPCT.Grp = 1 And DYSPCT.Mprp = 'IN_STK_PCT'\n" +
    " And DYSPCT.Mstk_Idn = SRT.Mstk_Idn And SRT.Grp = 1 And SRT.Mprp = 'MIX_RTE'\n" +
    " and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0 group by SH.val,SZ.val\n" +
    " union\n" +
    "Select 'TTL' shape,'TTL' mixsz,'TTL' mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" +
    "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) days,trunc(sum(DYSPCT.num),0) daysPct,trunc(sum(SRT.num),0) srt\n" +
    " From MSTK A\n" +
    " , STK_DTL SH\n" +
    " , STK_DTL DEPT\n" +
    " , STK_DTL CLR\n" +
    " , STK_DTL SZ\n" +
    " , STK_DTL DYS\n" +
    " , STK_DTL DYSPCT\n" +
    " , STK_DTL SRT\n" +
    " Where a.STT = 'MKAV' and a.pkt_ty <> 'NR' \n" +
    " and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val\n" +
    " "+conQ+" And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" +
    " "+deptQ+" and a.idn = SZ.mstk_idn and SZ.grp = 1 and SZ.mprp = 'MIX_SIZE'\n" +
    " and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" +
    " and upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')) not in('NATTS','TLB','TLC','FCCUT','FCCOL','SAM','REP','RSP','MIX')\n" +
    " And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS'\n" +
    " And DYS.Mstk_Idn = DYSPCT.Mstk_Idn And DYSPCT.Grp = 1 And DYSPCT.Mprp = 'IN_STK_PCT'\n" +
    " And DYSPCT.Mstk_Idn = SRT.Mstk_Idn And SRT.Grp = 1 And SRT.Mprp = 'MIX_RTE'\n" +
    " and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0";

    ;
    ArrayList  outLst = db.execSqlLst("Common Shape", commshQ, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet  rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    String shape=util.nvl((String)rs.getString("shape"));
    String mixsz=util.nvl((String)rs.getString("mixsz"));
    String mixclr=util.nvl((String)rs.getString("mixclr"));
    String key=shape+"_"+mixclr+"_"+mixsz;
    dataDtl.put(key+"_CTS", util.nvl((String)rs.getString("cts")));
    dataDtl.put(key+"_AVG", util.nvl((String)rs.getString("avg")));
    dataDtl.put(key+"_DAYS", util.nvl((String)rs.getString("days")));
    dataDtl.put(key+"_DAYSPCT", util.nvl((String)rs.getString("daysPct")));
    dataDtl.put(key+"_SRT", util.nvl((String)rs.getString("srt")));


    if(!commshLst.contains(shape) && !shape.equals("TTL"))
    commshLst.add(shape);
    if(!szLst.contains(mixsz) && !mixsz.equals("TTL"))
    szLst.add(mixsz);
    if(!clrLst.contains(mixclr) && !mixclr.equals("TTL"))
    clrLst.add(mixclr);
    }
    rs.close();
        pst.close();
    String fancyshQ="\n" +
    "Select SH.val shape,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" +
    "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) days, trunc(sum(DYSPCT.num),0) daysPct,trunc(sum(SRT.num),0) srt\n" +
    " From MSTK A\n" +
    " , STK_DTL SH\n" +
    " , STK_DTL DEPT\n" +
    " , STK_DTL DYS\n" +
    " , STK_DTL DYSPCT\n" +
    " , STK_DTL SRT\n" +
    " Where a.STT = 'MKAV' and a.pkt_ty <> 'NR' \n" +
    " and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val not\n" +
    " "+conQ+" And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" +
    " "+deptQ+" And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS'\n" +
    " And DYS.Mstk_Idn = DYSPCT.Mstk_Idn And DYSPCT.Grp = 1 And DYSPCT.Mprp = 'IN_STK_PCT'\n" +
    " And DYSPCT.Mstk_Idn = SRT.Mstk_Idn And SRT.Grp = 1 And SRT.Mprp = 'MIX_RTE'\n" +
    " and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0 GROUP BY SH.val\n" +
    "union\n" +
    "Select 'FANCYSHTTL' shape,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" +
    "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) days, trunc(sum(DYSPCT.num),0) daysPct,trunc(sum(SRT.num),0) srt\n" +
    " From MSTK A\n" +
    " , STK_DTL SH\n" +
    " , STK_DTL DEPT\n" +
    " , STK_DTL DYS\n" +
    " , STK_DTL DYSPCT\n" +
    " , STK_DTL SRT\n" +
    " Where a.STT = 'MKAV' and a.pkt_ty <> 'NR' \n" +
    " and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val not\n" +
    " "+conQ+" And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" +
    " "+deptQ+" And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS'\n" +
    " And DYS.Mstk_Idn = DYSPCT.Mstk_Idn And DYSPCT.Grp = 1 And DYSPCT.Mprp = 'IN_STK_PCT'\n" +
    " And DYSPCT.Mstk_Idn = SRT.Mstk_Idn And SRT.Grp = 1 And SRT.Mprp = 'MIX_RTE'\n" +
    " and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0\n";
    ary=new ArrayList();
    ary.add(dept);
    ary.add(dept);
        outLst = db.execSqlLst("Fancy Shape", fancyshQ, ary);
         pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    String shape=util.nvl((String)rs.getString("shape"));
    dataDtl.put(shape+"_CTS", util.nvl((String)rs.getString("cts")));
    dataDtl.put(shape+"_AVG", util.nvl((String)rs.getString("avg")));
    dataDtl.put(shape+"_DAYS", util.nvl((String)rs.getString("days")));
    dataDtl.put(shape+"DAYSPCT", util.nvl((String)rs.getString("daysPct")));
    dataDtl.put(shape+"_SRT", util.nvl((String)rs.getString("srt")));
    if(!shLst.contains(shape) && !shape.equals("FANCYSHTTL"))
    shLst.add(shape);
    }
    rs.close();
      pst.close();
    String allclrQ="\n" +
    "Select CLR.val mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" +
    "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) days,trunc(sum(DYSPCT.num),0) daysPct ,trunc(sum(SRT.num),0) srt\n" +
    " From MSTK A\n" +
    " , STK_DTL DEPT\n" +
    " , STK_DTL CLR\n" +
    " , STK_DTL DYS\n" +
    " , STK_DTL DYSPCT\n" +
    " , STK_DTL SRT\n" +
    " Where a.STT = 'MKAV' and a.pkt_ty <> 'NR' \n" +
    " And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" +
    " "+deptQ+" and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY' and clr.val in('FcCut','FcCol','SAM','REP','RSP','MIX')\n" +
    " And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' And DYS.Mstk_Idn = DYSPCT.Mstk_Idn And DYSPCT.Grp = 1 And DYSPCT.Mprp = 'IN_STK_PCT'\n" +
    " And DYSPCT.Mstk_Idn = SRT.Mstk_Idn And SRT.Grp = 1 And SRT.Mprp = 'MIX_RTE' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0 group by CLR.val\n" +
    " union\n" +
    " Select 'ALLCLRTTL' mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" +
    "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) days,trunc(sum(DYSPCT.num),0) daysPct,trunc(sum(SRT.num),0) srt\n" +
    " From MSTK A\n" +
    " , STK_DTL DEPT\n" +
    " , STK_DTL CLR\n" +
    " , STK_DTL DYS\n" +
    " , STK_DTL DYSPCT\n" +
    " , STK_DTL SRT\n" +
    " Where a.STT = 'MKAV' and a.pkt_ty <> 'NR' \n" +
    " And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" +
    " "+deptQ+" and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY' and clr.val in('FcCut','FcCol','SAM','REP','RSP','MIX')\n" +
    " And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' And DYS.Mstk_Idn = DYSPCT.Mstk_Idn And DYSPCT.Grp = 1 And DYSPCT.Mprp = 'IN_STK_PCT'\n" +
    " And DYSPCT.Mstk_Idn = SRT.Mstk_Idn And SRT.Grp = 1 And SRT.Mprp = 'MIX_RTE' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0";
    ary=new ArrayList();
    ary.add(dept);
    ary.add(dept);
      outLst = db.execSqlLst("All Clarity", allclrQ, ary);
       pst = (PreparedStatement)outLst.get(0);
      rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    String mixclr=util.nvl((String)rs.getString("mixclr"));
    dataDtl.put(mixclr+"_CTS", util.nvl((String)rs.getString("cts")));
    dataDtl.put(mixclr+"_AVG", util.nvl((String)rs.getString("avg")));
    dataDtl.put(mixclr+"_DAYS", util.nvl((String)rs.getString("days")));
    dataDtl.put(mixclr+"DAYSPCT", util.nvl((String)rs.getString("daysPct")));
    dataDtl.put(mixclr+"_SRT", util.nvl((String)rs.getString("srt")));
    }
    rs.close();
        pst.close();
    String rdclrQ="Select upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')) mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" +
    "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) days, trunc(sum(DYSPCT.num),0) daysPct,trunc(sum(SRT.num),0) srt\n" +
    " From MSTK A\n" +
    " , STK_DTL SH\n" +
    " , STK_DTL DEPT\n" +
    " , STK_DTL CLR\n" +
    " , STK_DTL DYS\n" +
    " , STK_DTL DYSPCT\n" +
    " , STK_DTL SRT\n" +
    " Where a.STT = 'MKAV' and a.pkt_ty <> 'NR' \n" +
    " and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val\n" +
    ""+conQ+" And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" +
    " "+deptQ+" and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" +
    " and upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')) in('NATTS','TLB','TLC')\n" +
    " And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' And DYS.Mstk_Idn = DYSPCT.Mstk_Idn And DYSPCT.Grp = 1 And DYSPCT.Mprp = 'IN_STK_PCT'\n" +
    " And DYSPCT.Mstk_Idn = SRT.Mstk_Idn And SRT.Grp = 1 And SRT.Mprp = 'MIX_RTE' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0 GROUP BY upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', ''))\n" +
    " union\n" +
    " Select 'ROUNDTTTL' mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" +
    " trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) days, trunc(sum(DYSPCT.num),0) daysPct,trunc(sum(SRT.num),0) srt\n" +
    " From MSTK A\n" +
    " , STK_DTL SH\n" +
    " , STK_DTL DEPT\n" +
    " , STK_DTL CLR\n" +
    " , STK_DTL DYS\n" +
    " , STK_DTL DYSPCT\n" +
    " , STK_DTL SRT\n" +
    " Where a.STT = 'MKAV' and a.pkt_ty <> 'NR' \n" +
    " and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val\n" +
    " "+conQ+ " And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" +
    " "+deptQ+" and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" +
    " and upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')) in('NATTS','TLB','TLC')\n" +
    " And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' And DYS.Mstk_Idn = DYSPCT.Mstk_Idn And DYSPCT.Grp = 1 And DYSPCT.Mprp = 'IN_STK_PCT'\n" +
    " And DYSPCT.Mstk_Idn = SRT.Mstk_Idn And SRT.Grp = 1 And SRT.Mprp = 'MIX_RTE' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0";
    ary=new ArrayList();
    ary.add(dept);
    ary.add(dept);
      outLst = db.execSqlLst("Rd Clarity", rdclrQ, ary);
       pst = (PreparedStatement)outLst.get(0);
      rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    String mixclr=util.nvl((String)rs.getString("mixclr"));
    dataDtl.put(mixclr+"_CTS", util.nvl((String)rs.getString("cts")));
    dataDtl.put(mixclr+"_AVG", util.nvl((String)rs.getString("avg")));
    dataDtl.put(mixclr+"_DAYS", util.nvl((String)rs.getString("days")));
    dataDtl.put(mixclr+"DAYSPCT", util.nvl((String)rs.getString("daysPct")));
    dataDtl.put(mixclr+"_SRT", util.nvl((String)rs.getString("srt")));
    }
    rs.close();
        pst.close();
    String mserQ="\n" +
    "Select 'M.SERI' mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" +
    " trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) days, trunc(sum(DYSPCT.num),0) daysPct,trunc(sum(SRT.num),0) srt\n" +
    " From MSTK A\n" +
    " , STK_DTL DEPT\n" +
    " , STK_DTL CLR\n" +
    " , STK_DTL DYS\n" +
    " , STK_DTL DYSPCT\n" +
    " , STK_DTL SRT\n" +
    " Where a.STT in('MX_AS_AV','MX_AS_IS','MX_AS_FN','MX_FN_IS','MX_FN','MX_LB_IS','MX_FN_AV','MX_AS_RC','MX_RC_IS') and a.pkt_ty <> 'NR' \n" +
    " And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" +
    " "+deptQ+" and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" +
    " And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' And DYS.Mstk_Idn = DYSPCT.Mstk_Idn And DYSPCT.Grp = 1 And DYSPCT.Mprp = 'IN_STK_PCT'\n" +
    " And DYSPCT.Mstk_Idn = SRT.Mstk_Idn And SRT.Grp = 1 And SRT.Mprp = 'MIX_RTE' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0 GROUP BY 'M.SERI'\n" +
    "union\n" +
    " Select 'GTTL' mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" +
    " trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) days, trunc(sum(DYSPCT.num),0) daysPct,trunc(sum(SRT.num),0) srt\n" +
    " From MSTK A\n" +
    " , STK_DTL DEPT\n" +
    " , STK_DTL CLR\n" +
    " , STK_DTL DYS\n" +
    " , STK_DTL DYSPCT\n" +
    " , STK_DTL SRT\n" +
    " Where a.STT in('MX_AS_AV','MX_AS_IS','MX_AS_FN','MX_FN_IS','MX_FN','MX_LB_IS','MX_FN_AV','MX_AS_RC','MX_RC_IS','MKAV') and a.pkt_ty <> 'NR' \n" +
    " And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" +
    " "+deptQ+" and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_CLARITY'\n" +
    " And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS' And DYS.Mstk_Idn = DYSPCT.Mstk_Idn And DYSPCT.Grp = 1 And DYSPCT.Mprp = 'IN_STK_PCT'\n" +
    " And DYSPCT.Mstk_Idn = SRT.Mstk_Idn And SRT.Grp = 1 And SRT.Mprp = 'MIX_RTE' and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0";
    ary=new ArrayList();
    ary.add(dept);
    ary.add(dept);
      outLst = db.execSqlLst("M.SERI", mserQ, ary);
       pst = (PreparedStatement)outLst.get(0);
      rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    String mixclr=util.nvl((String)rs.getString("mixclr"));
    dataDtl.put(mixclr+"_CTS", util.nvl((String)rs.getString("cts")));
    dataDtl.put(mixclr+"_AVG", util.nvl((String)rs.getString("avg")));
    dataDtl.put(mixclr+"_DAYS", util.nvl((String)rs.getString("days")));
    dataDtl.put(mixclr+"DAYSPCT", util.nvl((String)rs.getString("daysPct")));
    dataDtl.put(mixclr+"_SRT", util.nvl((String)rs.getString("srt")));
    }
    rs.close();
        pst.close();

    dataDtl.put("DEPT", dept);
    req.setAttribute("commshLst", commshLst);
    req.setAttribute("shLst", shLst);
    req.setAttribute("szLst", szLst);
    req.setAttribute("clrLst", clrLst);
    req.setAttribute("dataDtl", dataDtl);
    req.setAttribute("view", "Y");
        util.updAccessLog(req,res,"Rpt Action", "fetchdweekly end");
    return am.findForward("loadweekly");
    }
    }

    public ActionForward fetchloadrtn(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "fetchloadrtn start");
        ReportForm udf = (ReportForm)form;
        String empId =(String)udf.getValue("empId");
        String byrId = (String)udf.getValue("byrId");
        String typ =(String)udf.getValue("typ");
        String rtnfrmdte = (String)udf.getValue("rtnfrmdte");
        String rtntodte =(String)udf.getValue("rtntodte");
        String pkttyp = (String)udf.getValue("pkttyp");
        db.execUpd("delete", "delete from gt_srch_multi", new ArrayList());
        ArrayList vwPrpLst =(info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
        int vwPrpLstsz=vwPrpLst.size();
        ArrayList params = new ArrayList();
        String srchRef=" Insert into gt_srch_multi (pkt_ty, srch_id, stk_idn, vnm, pkt_dte,sl_dte,rap_rte,rap_dis,prte, stt,dsp_stt , flg , qty , cts , sk1,byr , emp,sl_rte,quot,rmk) \n" + 
            "select c.pkt_ty , b.idn, c.idn mstkIdn, c.vnm ,b.dte,b.dte_rtn,c.rap_rte,decode(c.rap_rte ,'1',null, trunc((nvl(c.upr,c.cmp)/c.rap_rte*100)-100, 2)),c.fcpr, c.stt ,a.typ, 'Z' ,  b.qty,b.cts, sk1,d.nme byr, e.nme emp,b.rte,nvl(b.fnl_sal, b.quot),b.rmk\n" + 
            "from mjan a , jandtl b  , mstk c,nme_v d,emp_v e\n" + 
            "where a.idn = b.idn  and b.mstk_idn = c.idn and a.nme_idn=d.nme_idn And nvl(D.Emp_Idn,0)=nvl(E.Nme_Idn,0)\n" + 
            "and b.stt in('RT') and a.typ in ('I', 'E', 'WH', 'Z','NG','K','H','BID','O') ";
       if(typ.equals("CS")){
           srchRef=" Insert into gt_srch_multi (pkt_ty, srch_id, stk_idn, vnm, pkt_dte,sl_dte,rap_rte,rap_dis,prte, stt,dsp_stt , flg , qty , cts , sk1,byr , emp,sl_rte,quot,rmk) \n" + 
                       "select c.pkt_ty , b.idn, c.idn mstkIdn, c.vnm ,b.dte,b.dte_rtn,c.rap_rte,decode(c.rap_rte ,'1',null, trunc((nvl(c.upr,c.cmp)/c.rap_rte*100)-100, 2)),c.fcpr, c.stt ,a.typ, 'Z' ,  b.qty,b.cts, sk1,d.nme byr, e.nme emp,b.rte,nvl(b.fnl_sal, b.quot),b.rmk\n" + 
                       "from mjan a , jandtl b  , mstk c,nme_v d,emp_v e\n" + 
                       "where a.idn = b.idn  and b.mstk_idn = c.idn and a.nme_idn=d.nme_idn And nvl(D.Emp_Idn,0)=nvl(E.Nme_Idn,0)\n" + 
                       "and b.stt in('RT') and a.typ in ('CS') ";  
       }else  if(typ.equals("AP")){
            srchRef = " Insert into gt_srch_multi (pkt_ty, srch_id, stk_idn, vnm, pkt_dte,sl_dte,rap_rte,rap_dis,prte, stt,dsp_stt , flg , qty , cts , sk1,byr , emp,sl_rte,quot,rmk) \n" + 
            "select c.pkt_ty , b.idn, c.idn mstkIdn, c.vnm ,b.dte,b.dte_rtn,c.rap_rte,decode(c.rap_rte ,'1',null, trunc((nvl(c.upr,c.cmp)/c.rap_rte*100)-100, 2)),c.fcpr, c.stt,a.typ , 'Z' ,  b.qty,b.cts, sk1,d.nme byr, e.nme emp,b.rte,nvl(b.fnl_sal, b.quot),b.rmk\n" + 
            "from mjan a , jandtl b  , mstk c,nme_v d,emp_v e\n" + 
            "where a.idn = b.idn  and b.mstk_idn = c.idn and a.nme_idn=d.nme_idn And nvl(D.Emp_Idn,0)=nvl(E.Nme_Idn,0)\n" + 
                "and b.stt in('RT','APRT') and b.typ in ('IAP', 'EAP','WAP','OAP','LAP','BCAP','MAP','SAP','HAP','BAP','KAP','BIDAP') ";     
       }else if(typ.equals("SL")){
        srchRef = " Insert into gt_srch_multi (pkt_ty, srch_id, stk_idn, vnm, pkt_dte,sl_dte,rap_rte,rap_dis,prte, stt,dsp_stt , flg , qty , cts , sk1,byr , emp,sl_rte,quot,rmk) \n" + 
        "select c.pkt_ty , b.idn, c.idn mstkIdn, c.vnm ,b.dte,b.dte_rtn,c.rap_rte,decode(c.rap_rte ,'1',null, trunc((nvl(c.upr,c.cmp)/c.rap_rte*100)-100, 2)),c.fcpr, c.stt ,a.typ, 'Z' ,  b.qty,b.cts, sk1,d.nme byr, e.nme emp,b.rte,nvl(b.fnl_sal, b.quot),b.rmk\n" + 
        "from msal a , jansal b  , mstk c,nme_v d,emp_v e\n" + 
        "where a.idn = b.idn  and b.mstk_idn = c.idn and a.nme_idn=d.nme_idn And nvl(D.Emp_Idn,0)=nvl(E.Nme_Idn,0)\n" + 
        "and b.stt in('RT','DLV_RT')";
        }else if(typ.equals("DLV")){
            srchRef = "Insert into gt_srch_multi (pkt_ty, srch_id, stk_idn, vnm, pkt_dte,sl_dte,rap_rte,rap_dis,prte, stt,dsp_stt , flg , qty , cts , sk1,byr , emp,sl_rte,quot,rmk) \n" + 
            "select c.pkt_ty , b.idn, c.idn mstkIdn, c.vnm ,b.dte,b.dte_rtn ,c.rap_rte,decode(c.rap_rte ,'1',null, trunc((nvl(c.upr,c.cmp)/c.rap_rte*100)-100, 2)),c.fcpr, c.stt ,a.typ, 'Z' ,  b.qty,b.cts, sk1,d.nme byr, e.nme emp,b.rte,nvl(b.fnl_sal, b.quot),b.rmk\n" + 
            "from mdlv a , dlv_dtl b  , mstk c,nme_v d,emp_v e\n" + 
            "where a.idn = b.idn  and b.mstk_idn = c.idn and a.nme_idn=d.nme_idn And nvl(D.Emp_Idn,0)=nvl(E.Nme_Idn,0)\n" + 
            "and b.stt = 'RT'";  
        }
        if(!empId.equals("") && !empId.equals("0")){
            srchRef+=" and d.emp_idn=? "; 
            params.add(empId);
        }
        if(!byrId.equals("") && !byrId.equals("0")){
            srchRef+=" and a.nme_idn=? "; 
            params.add(byrId);
        }
        if(pkttyp.equals("M"))
        srchRef+=" and c.pkt_ty in('MX','MIX') "; 
        else
        srchRef+=" and c.pkt_ty='NR' ";    
        if(!rtnfrmdte.equals("") && !rtntodte.equals("")){
            srchRef+= " and trunc(b.dte_rtn) between trunc(to_date('"+rtnfrmdte+"','dd-mm-rrrr')) and trunc(to_date('"+rtntodte+"','dd-mm-rrrr')) ";
        }else{
            srchRef+= " and trunc(b.dte_rtn)=trunc(sysdate)";
        }
        int ct = db.execUpd("insert gt",srchRef, params);
        
        String pktPrp = "srch_pkg.POP_PKT_PRP_TEST(pMdl=>?,pTbl=>'GT_SRCH_MULTI')";
        params= new ArrayList();
        params.add("MEMO_RTRN");
        ct = db.execCall(" Srch Prp ", pktPrp, params);
        String cpPrp = "prte";
        if(vwPrpLst.contains("CP"))
        cpPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("CP")+1);
        String rsltQ = " select byr byr, emp sal, srch_id idn,dsp_stt, to_char(pkt_dte,'DD-MON-YYYY')  dte,to_char(sl_dte,'DD-MON-YYYY') rtn_dte, vnm ,to_char(trunc(cts,2),'9990.99') cts, quot memoQuot, sl_rte rte,to_char(trunc(cts,2) * quot, '9999999990.00') amt,rmk,rap_rte,prte,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis, "+
            "nvl("+cpPrp+",prte) prte , nvl(nvl("+cpPrp+",prte),0)*nvl(cts,0) cptotal ,trunc(((greatest(nvl("+cpPrp+",prte),1)*100)/greatest(rap_rte,1)) - 100,2) cpdis,decode(rap_rte, 1, '', trunc(((quot/greatest(rap_rte,1))*100)-100,2)) byrdis ";
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
        rsltQ = rsltQ +" from gt_srch_multi a where flg= ?  order by emp, byr , srch_id , sk1";
        params= new ArrayList();
        params.add("Z");
        ArrayList  outLst = db.execSqlLst("search Result", rsltQ, params);
        PreparedStatement   pst = (PreparedStatement)outLst.get(0);
        ResultSet  rs = (ResultSet)outLst.get(1);
        ArrayList pktList = new  ArrayList();
         while(rs.next()) {
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("byr", util.nvl(rs.getString("byr")));
                pktPrpMap.put("sal",util.nvl(rs.getString("sal")));
                pktPrpMap.put("memoIdn",util.nvl(rs.getString("idn")));
                pktPrpMap.put("dte",util.nvl(rs.getString("dte")));
             pktPrpMap.put("memoQuot",util.nvl(rs.getString("memoQuot")));
             pktPrpMap.put("byrdis",util.nvl(rs.getString("byrdis")));
             pktPrpMap.put("typ",util.nvl(rs.getString("dsp_stt")));
             pktPrpMap.put("rte",util.nvl(rs.getString("rte")));
             pktPrpMap.put("amt",util.nvl(rs.getString("amt")));
             pktPrpMap.put("vlu",util.nvl(rs.getString("amt")));
                pktPrpMap.put("vnm",util.nvl(rs.getString("vnm")));
                pktPrpMap.put("rmk",util.nvl(rs.getString("rmk")));
             pktPrpMap.put("rtn_dte",util.nvl(rs.getString("rtn_dte")));
             for (int j = 0; j < vwPrpLstsz; j++) {
                 String prp = (String)vwPrpLst.get(j);

                 String fld = "prp_";
                 if (j < 9)
                     fld = "prp_00" + (j + 1);
                 else
                     fld = "prp_0" + (j + 1);

                 String val = util.nvl(rs.getString(fld));
                 if (prp.toUpperCase().equals("CRTWT"))
                     val = util.nvl(rs.getString("cts"));
                 if (prp.toUpperCase().equals("RAP_DIS"))
                     val = util.nvl(rs.getString("r_dis"));
                 if (prp.toUpperCase().equals("RAP_RTE"))
                     val = util.nvl(rs.getString("rap_rte"));
                 if(prp.equals("RTE"))
                     val = util.nvl(rs.getString("rte"));
               
                 if(prp.equals("CP")){
                     pktPrpMap.put("cpRte", val);
                     val = util.nvl(rs.getString("prte"));
                 }
                 if(prp.equals("CP_DIS")){
                     val = util.nvl(rs.getString("cpdis"));
                 }
                 if(prp.equals("KTSVIEW"))
                     val = util.nvl(rs.getString("kts"));
                 if(prp.equals("COMMENT"))
                     val = util.nvl(rs.getString("cmnt"));
                
                     pktPrpMap.put(prp, val);
                 }
                              
             pktList.add(pktPrpMap);
         }
        rs.close();
            pst.close();
        udf.reset();
        udf.setPktList(pktList);
        if(pktList.size()>0)
        req.setAttribute("view", "Y");
            util.updAccessLog(req,res,"Rpt Action", "fetchloadrtn end");
        return am.findForward("loadrtn");
        }
    }
//    public ArrayList ASGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("DAILYRPTGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'DAILY_TRF_RPT_SRCH' and flg in ('Y','S') order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("DAILYRPTGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
        public ActionForward loadbox(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "loadbox start");
        ReportForm udf = (ReportForm)af;
        String vnm = util.nvl((String)udf.getValue("vnm"));
        String frmdte = util.nvl((String)udf.getValue("dtefr"));
        String todte = util.nvl((String)udf.getValue("dteto"));
        String condteQ="";
        String consdteQ="";  
        ArrayList pktList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        if(!frmdte.equals("") && !todte.equals("")){
        condteQ = " and trunc(a.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
        consdteQ = " and trunc(a.sdate) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
        }
        if(frmdte.equals("") && !todte.equals("")){
        condteQ = " and trunc(a.dte) between to_date('"+todte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
        consdteQ = " and trunc(a.sdate) between to_date('"+todte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
        }
        if(!frmdte.equals("") && todte.equals("")){
        condteQ = " and trunc(a.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+frmdte+"' , 'dd-mm-yyyy') ";
        consdteQ = " and trunc(a.sdate) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+frmdte+"' , 'dd-mm-yyyy') ";
        }
        if(!vnm.equals("")){
            vnm = util.getVnm(vnm);
            condteQ+=" and c.vnm in("+vnm+") ";
            consdteQ+=" and b.vnm in("+vnm+") ";
        }
        if(condteQ.equals("") && consdteQ.equals("")){
            condteQ+=" and trunc(a.dte)=trunc(sysdate) ";
            consdteQ+=" and trunc(a.sdate)=trunc(sysdate) ";
        }
        String srchQ="select cts , qty ,dte , vnm , stt , sk1 , idn , nme , vnmto , new_crtwt , new_qty , flg from (\n" + 
        "select b.cts , b.qty ,to_char( a.dte ,'dd-Mon-rrrr HH24:mi:ss') dte , c.vnm , b.stt , c.sk1 , a.idn ,byr.get_nm(a.nme_idn) nme ,\n" + 
        "'' vnmto , 0 new_crtwt , 0 new_qty , 'CS' flg from mjan a , jandtl b, mstk c\n" + 
        "where a.idn=b.idn and b.mstk_idn =c.idn and b.stt <> 'RT' and a.stt='CS' and c.pkt_ty <> 'NR' \n" +condteQ+  
        "union\n" + 
        "select b.cts, b.qty ,to_char( a.dte ,'dd-Mon-rrrr HH24:mi:ss') dte , c.vnm , b.stt , c.sk1 , a.idn , byr.get_nm(a.nme_idn) nme ,\n" + 
        "'' vnmto , 0 new_crtwt , 0 new_qty , 'SAL' flg from msal a , jansal b, mstk c\n" + 
        "where a.idn=b.idn and b.mstk_idn =c.idn and b.stt <> 'RT' and c.pkt_ty <> 'NR' \n" + condteQ+
        "union\n" + 
        "select a.old_crtwt cts ,a.old_qty qty,to_char( a.sdate ,'dd-Mon-rrrr HH24:mi:ss') dte , a.vnmfr vnm,'' stt , b.sk1 , b.idn\n" + 
        ", '' nme , a.vnmto , a.new_crtwt , a.new_qty ,(a.old_crtwt- a.new_crtwt)||' CTS TRF' flg\n" + 
        "from mix_box_log a , mstk b where a.vnmfr = b.vnm and b.pkt_ty <> 'NR' \n" +consdteQ+ 
        ")\n" + 
        "order by sk1 , vnm ,   dte";
          ArrayList outLst = db.execSqlLst("search Result", srchQ, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()) {
            HashMap pktPrpMap = new HashMap();
            pktPrpMap.put("Cts", util.nvl(rs.getString("cts")));
            pktPrpMap.put("Qty", util.nvl(rs.getString("qty")));
            pktPrpMap.put("Dte", util.nvl(rs.getString("dte")));
            pktPrpMap.put("VNM", util.nvl(rs.getString("vnm")));
            pktPrpMap.put("Status", util.nvl(rs.getString("stt")));
            pktPrpMap.put("Sk1", util.nvl(rs.getString("sk1")));
            pktPrpMap.put("Idn", util.nvl(rs.getString("idn")));
            pktPrpMap.put("BYR", util.nvl(rs.getString("nme")));
            pktPrpMap.put("VnmTo", util.nvl(rs.getString("vnmto")));
            pktPrpMap.put("NewCts", util.nvl(rs.getString("new_crtwt")));
            pktPrpMap.put("NewQty", util.nvl(rs.getString("new_qty")));
            pktPrpMap.put("Desc", util.nvl(rs.getString("flg")));
            pktList.add(pktPrpMap);
        }
            rs.close();
            pst.close();
        itemHdr.add("VNM");
        itemHdr.add("Qty");
        itemHdr.add("Cts");
        itemHdr.add("Dte");
//        itemHdr.add("Status");
//        itemHdr.add("Sk1");
//        itemHdr.add("Idn");
        itemHdr.add("BYR");
        itemHdr.add("VnmTo");
        itemHdr.add("NewCts");
        itemHdr.add("NewQty");
        itemHdr.add("Desc");
        session.setAttribute("itemHdr", itemHdr);
        session.setAttribute("pktList", pktList);
        if(pktList.size()>0)
        req.setAttribute("View", "Y");
            util.updAccessLog(req,res,"Rpt Action", "loadbox end");
        return am.findForward("loadbox");
        }
    }
        
    public ActionForward loadmakablerpt(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "loadmakablerpt start");
        ReportForm udf = (ReportForm)af;
            udf.reset();
//            HashMap mprp = info.getMprp();
//            if(mprp==null)
//            util.initPrp();
            HashMap dbinfo = info.getDmbsInfoLst();
            String mdflink = util.nvl((String)dbinfo.get("MFGKEY"));
            ArrayList ary=new ArrayList();
            ArrayList blckLst=new ArrayList();
            ArrayList docLst=new ArrayList();  
            ArrayList orderLst=new ArrayList(); 
            ArrayList lotLst=new ArrayList(); 
            HashMap mfgprpset= (session.getAttribute("MFGPRPSET") == null)?new HashMap():(HashMap)session.getAttribute("MFGPRPSET");
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("MAKABLE_RPT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("MAKABLE_RPT");
            allPageDtl.put("MAKABLE_RPT",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
            pageList= ((ArrayList)pageDtl.get("PRPFLT") == null)?new ArrayList():(ArrayList)pageDtl.get("PRPFLT");             
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=(String)pageDtlMap.get("dflt_val");
            ArrayList prpLst= ((ArrayList)mfgprpset.get(dflt_val+"_VAL") == null)?new ArrayList():(ArrayList)mfgprpset.get(dflt_val+"_VAL");
            if(prpLst==null || prpLst.size()==0){
            ArrayList prpval=new ArrayList();
            ArrayList prpsrt=new ArrayList(); 
            String sqlQ="Select A.Attr_Cd val,a.sq srt From \n" + 
            "(Select A.Attr_Cd,A.Sqnc_Nmbr Sq From "+mdflink+".Mfg_Attrbt_D A\n" + 
            "Where Attr_Typ=?) A\n" + 
            "Order By A.Sq";
                ary=new ArrayList();
                ary.add(dflt_val);
              ArrayList outLst = db.execSqlLst("sql", sqlQ, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
                    while (rs.next()) {
                        prpval.add(util.nvl((String)util.nvl(rs.getString("val"))));
                        prpsrt.add(util.nvl((String)util.nvl(rs.getString("srt"))));
                    }    
                rs.close();
                pst.close();
            mfgprpset.put(dflt_val+"_VAL", prpval);
            mfgprpset.put(dflt_val+"_SRT", prpval);
            }
            }
            }
            String sqlQ="Select Distinct Blck_Cd P_Blck_Cd From "+mdflink+".Per_Empdtl_V A\n" + 
            "Where Prcs_Cd='AS'\n" + 
            "And Fnct_Cd='PLNR'\n" + 
            "Order By Blck_Cd";
          ArrayList outLst = db.execSqlLst("sql", sqlQ, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
                    while (rs.next()) {
                        blckLst.add(util.nvl((String)util.nvl(rs.getString("P_Blck_Cd"))));
                    }    
                rs.close(); 
            pst.close();
            mfgprpset.put("blckLst", blckLst);
            sqlQ="Select A.Inv_Doc_Id P_Inv_Doc_Id From \n" + 
            "(Select A.Inv_Doc_Id,A.Inv_Doc_Id Sq From "+mdflink+".Rgh_Invmst_M A\n" + 
            "Union\n" + 
            "Select 'ALL', '1' Sq From Dual ) A\n" + 
            "Order By A.Sq";
           outLst = db.execSqlLst("sql", sqlQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
                    while (rs.next()) {
                        docLst.add(util.nvl((String)util.nvl(rs.getString("P_Inv_Doc_Id"))));
                    }    
                rs.close();
            pst.close();
            mfgprpset.put("docLst", docLst);
            sqlQ="Select A.P_Ord2 From (\n" + 
            "Select 'NONE' P_Ord2,0 Sq From Dual\n" + 
            "Union\n" + 
            "Select 'INV_ID' P_Ord2,1 Sq From Dual\n" + 
            "Union \n" + 
            "Select 'LOT' P_Ord2,2 Sq From Dual\n" + 
            "Union \n" + 
            "Select 'PCKT' P_Ord2,3 Sq From Dual\n" + 
            "Union \n" + 
            "Select 'EMP' P_Ord2,5 Sq From Dual\n" + 
            "Union \n" + 
            "Select 'BLCK' P_Ord2, 6 Sq From Dual )A\n" + 
            "Order By A.Sq";
          outLst = db.execSqlLst("sql", sqlQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
                    while (rs.next()) {
                        orderLst.add(util.nvl((String)util.nvl(rs.getString("P_Ord2"))));
                    }    
                rs.close();
            pst.close();
            mfgprpset.put("orderLst", orderLst);
            
            sqlQ="Select A.Lt_Nmbr From \n" + 
            "(Select A.Lt_Nmbr,A.Lt_Nmbr Sq From "+mdflink+".Prp_Lot_M A) A\n" + 
            "Order By A.Sq\n";
          outLst = db.execSqlLst("sql", sqlQ, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
                    while (rs.next()) {
                        lotLst.add(util.nvl((String)util.nvl(rs.getString("Lt_Nmbr"))));
                    }    
                rs.close();
            pst.close();
            mfgprpset.put("lotLst", lotLst);
            session.setAttribute("MFGPRPSET", mfgprpset);
            util.updAccessLog(req,res,"Rpt Action", "loadmakablerpt end");
        return am.findForward("makablerpt");
        }
    }
    
    
    public ActionForward fetchmakablerpt(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "fetchmakablerpt start");
        ReportForm udf = (ReportForm)af;
            HashMap dbinfo = info.getDmbsInfoLst();
            String mdflink = util.nvl((String)dbinfo.get("MFGKEY"));
            ArrayList ary=new ArrayList();
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
            HashMap mfgprpset= (session.getAttribute("MFGPRPSET") == null)?new HashMap():(HashMap)session.getAttribute("MFGPRPSET");
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("MAKABLE_RPT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("MAKABLE_RPT");
            allPageDtl.put("MAKABLE_RPT",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            pageList= ((ArrayList)pageDtl.get("PRPFLT") == null)?new ArrayList():(ArrayList)pageDtl.get("PRPFLT"); 
            String filterconQ="";
            ArrayList selectCollection= new ArrayList();
            if(pageList!=null && pageList.size() >0){
            for(int k=0;k<pageList.size();k++){
            pageDtlMap=(HashMap)pageList.get(k);
                        String lprp=(String)pageDtlMap.get("dflt_val");
                        String flg=(String)pageDtlMap.get("val_cond");
                        String typ = (String)pageDtlMap.get("fld_typ");
                        String prpSrt = lprp ; 
                         ArrayList lprpS= ((ArrayList)mfgprpset.get(lprp+"_SRT") == null)?new ArrayList():(ArrayList)mfgprpset.get(lprp+"_SRT");
                         ArrayList lprpV = ((ArrayList)mfgprpset.get(lprp+"_VAL") == null)?new ArrayList():(ArrayList)mfgprpset.get(lprp+"_VAL");
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
                                filterconQ += " and a."+lprp+" in("+selprp+") ";
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
                        filterconQ += " and a."+lprp+" between '"+util.nvl((String)lprpS.get(lprpV.indexOf(fldVal1)))+"' and '"+util.nvl((String)lprpS.get(lprpV.indexOf(fldVal2)))+"' ";
                        }
                        } 
            }
            }
            String[] P_Inv_Doc_Id =  udf.getPInvDocIdLst();
            String[] lotLst =  udf.getLotLst();
//            String P_Inv_Doc_Id = util.nvl((String)udf.getValue("P_Inv_Doc_Id"));  
//            String lot = util.nvl((String)udf.getValue("lot")); 
            String blck = util.nvl((String)udf.getValue("blck"));  
            String startfrmdte = util.nvl((String)udf.getValue("startfrmdte")); 
            String starttodte = util.nvl((String)udf.getValue("starttodte")); 
            String CRTWT_1 = util.nvl((String)udf.getValue("CRTWT_1")); 
            String CRTWT_2 = util.nvl((String)udf.getValue("CRTWT_2")); 
            String pkt_1 = util.nvl((String)udf.getValue("pkt_1")); 
            String pkt_2 = util.nvl((String)udf.getValue("pkt_2"));
            String order_1 = util.nvl((String)udf.getValue("order_1")); 
            String order_2 = util.nvl((String)udf.getValue("order_2")); 
            if(P_Inv_Doc_Id!=null){
                String pidval="";
                for(int i=0;i<P_Inv_Doc_Id.length;i++){
                    pidval+=",'"+P_Inv_Doc_Id[i]+"'";
                }
                pidval = pidval.replaceFirst("\\,", "");
                filterconQ += " and A.Inv_Doc_Id in("+pidval+")";
            }
            if(lotLst!=null){
                String lotval="";
                for(int i=0;i<lotLst.length;i++){
                    lotval+=",'"+lotLst[i]+"'";
                }
                lotval = lotval.replaceFirst("\\,", "");
                filterconQ += " and A.Lt_Nmbr in ("+lotval+")";
            }
            if(!blck.equals("")){
                filterconQ += " and Nvl(A.Blck_Cd,'xx')=?";
                ary.add(blck);
            }
            if(!startfrmdte.equals("") && !starttodte.equals(""))
                filterconQ += " and trunc(A.Mk_Dt) between trunc(to_date('"+startfrmdte+"','dd-mm-rrrr')) and trunc(to_date('"+starttodte+"','dd-mm-rrrr')) ";
            if(!startfrmdte.equals("") && starttodte.equals(""))
                filterconQ += " and trunc(A.Mk_Dt) between trunc(to_date('"+startfrmdte+"','dd-mm-rrrr')) and trunc(to_date('"+startfrmdte+"','dd-mm-rrrr')) ";
            if(startfrmdte.equals("") && !starttodte.equals(""))
                filterconQ += " and trunc(A.Mk_Dt) between trunc(to_date('"+starttodte+"','dd-mm-rrrr')) and trunc(to_date('"+starttodte+"','dd-mm-rrrr')) ";
            if(!CRTWT_1.equals("") && !CRTWT_2.equals(""))
                filterconQ += " And To_Number(A.Crt) Between Nvl("+CRTWT_1+",To_Number(A.Crt)) And Nvl("+CRTWT_2+",To_Number(A.Crt))";
            if(!CRTWT_1.equals("") && CRTWT_2.equals(""))
                filterconQ += " And To_Number(A.Crt) Between Nvl("+CRTWT_1+",To_Number(A.Crt)) And Nvl("+CRTWT_1+",To_Number(A.Crt))";
            if(CRTWT_1.equals("") && !CRTWT_2.equals(""))
                filterconQ += " And To_Number(A.Crt) Between Nvl("+CRTWT_2+",To_Number(A.Crt)) And Nvl("+CRTWT_2+",To_Number(A.Crt))";
            
            if(!pkt_1.equals("") && !pkt_2.equals(""))
                filterconQ += " And To_Number(A.Pckt_Nmbr) Between Nvl("+pkt_1+",To_Number(A.Pckt_Nmbr)) And Nvl("+pkt_2+",To_Number(A.Pckt_Nmbr))";
            if(!pkt_1.equals("") && pkt_2.equals(""))
                filterconQ += " And To_Number(A.Pckt_Nmbr) Between Nvl("+pkt_1+",To_Number(A.Pckt_Nmbr)) And Nvl("+pkt_1+",To_Number(A.Pckt_Nmbr))";
            if(pkt_1.equals("") && !pkt_2.equals(""))
                filterconQ += " And To_Number(A.Pckt_Nmbr) Between Nvl("+pkt_2+",To_Number(A.Pckt_Nmbr)) And Nvl("+pkt_2+",To_Number(A.Pckt_Nmbr))";
        
        ArrayList pktLst=new ArrayList();
        ArrayList pktsummryLst=new ArrayList();
        HashMap pktDtlMap=new HashMap();
        String rsltQ="Select A.Inv_Doc_Id,A.Lt_Nmbr, A.Pckt_Nmbr, A.Empl_Cd, A.Blck_Cd,to_char(A.Mk_Crts,'99999999990.000') Mk_Crts,to_char(A.Crt,'99999999990.000')  Exp_Plsh_Crts,\n" + 
        "A.Pln_$Vlu Vlu, A.Sha, A.Col, A.Clr, A.Cut, A.Flo, A.Lus, A.Tn, A.Mk_Dt\n" + 
        "From "+mdflink+".Bid_Mkbdtl_V A\n" + 
        "Where 1=1 ";
            rsltQ = rsltQ +filterconQ+" Order By Decode('"+order_1+"','INV_ID',A.Inv_Doc_Id,'LOT',A.Lt_Nmbr,'PCKT',A.Pckt_Nmbr,'EMP',A.Empl_Cd, 'BLCK',A.Blck_Cd,'NONE',Null,Null),\n" + 
            "        Decode('"+order_2+"','INV_ID',A.Inv_Doc_Id,'LOT',A.Lt_Nmbr,'PCKT',A.Pckt_Nmbr,'EMP',A.Empl_Cd, 'BLCK',A.Blck_Cd,'NONE',Null,Null)";
         ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet  rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
                 pktDtlMap=new HashMap();
                 pktDtlMap.put("Inv_Doc_Id",util.nvl(rs.getString("Inv_Doc_Id")));
                 pktDtlMap.put("Lt_Nmbr",util.nvl(rs.getString("Lt_Nmbr")));
                 pktDtlMap.put("Pckt_Nmbr",util.nvl(rs.getString("Pckt_Nmbr")));
                 pktDtlMap.put("Empl_Cd",util.nvl(rs.getString("Empl_Cd")));
                 pktDtlMap.put("Blck_Cd",util.nvl(rs.getString("Blck_Cd")));
                 pktDtlMap.put("Mk_Crts",util.nvl(rs.getString("Mk_Crts")));
                 pktDtlMap.put("Exp_Plsh_Crts",util.nvl(rs.getString("Exp_Plsh_Crts")));
                 pktDtlMap.put("Vlu",util.nvl(rs.getString("Vlu")));
                 pktDtlMap.put("Sha",util.nvl(rs.getString("Sha")));
                 pktDtlMap.put("Col",util.nvl(rs.getString("Col")));
                 pktDtlMap.put("Clr",util.nvl(rs.getString("Clr")));
                 pktDtlMap.put("Cut",util.nvl(rs.getString("Cut")));
                 pktDtlMap.put("Flo",util.nvl(rs.getString("Flo")));
                 pktDtlMap.put("Lus",util.nvl(rs.getString("Lus")));
                 pktDtlMap.put("Tn",util.nvl(rs.getString("Tn")));
                 pktDtlMap.put("Mk_Dt",util.nvl(rs.getString("Mk_Dt")));
                 pktLst.add(pktDtlMap);
             }
            rs.close();
            pst.close();
            String rsltsummryQ="Select A.Inv_Doc_Id,A.Lt_Nmbr,count(pckt_nmbr) pckt_nmbr,to_char(Sum(A.Mk_Crts),'99999990.000') Mk_Crts,to_char(Sum(A.Crt),'99999990.000')   Exp_Plsh_Crts,\n" + 
            "Sum(A.Pln_$Vlu) Vlu \n" + 
            "From "+mdflink+".Bid_Mkbdtl_V A\n" + 
            "Where 1=1 ";
                rsltsummryQ = rsltsummryQ +filterconQ+" Group By A.Inv_Doc_Id,A.Lt_Nmbr";
           outLst = db.execSqlLst("search Result", rsltsummryQ, ary);
           pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
                while(rs.next()) {
                    pktDtlMap=new HashMap();
                    pktDtlMap.put("Inv_Doc_Id",util.nvl(rs.getString("Inv_Doc_Id")));
                    pktDtlMap.put("pckt_nmbr",util.nvl(rs.getString("pckt_nmbr")));
                    pktDtlMap.put("Lt_Nmbr",util.nvl(rs.getString("Lt_Nmbr")));
                    pktDtlMap.put("Mk_Crts",util.nvl(rs.getString("Mk_Crts")));
                    pktDtlMap.put("Exp_Plsh_Crts",util.nvl(rs.getString("Exp_Plsh_Crts")));
                    pktDtlMap.put("Vlu",util.nvl(rs.getString("Vlu")));
                    pktsummryLst.add(pktDtlMap); 
                }
            rs.close();
            pst.close();
            req.setAttribute("pktsummryLst", pktsummryLst);
            req.setAttribute("pktLst", pktLst);
            req.setAttribute("view", "Y");
            util.updAccessLog(req,res,"Rpt Action", "fetchmakablerpt end");
        return loadmakablerpt(am,af,req,res);
        }
    }
    
    public ActionForward loadfactoryrun(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "loadfactoryrun start");
        ReportForm udf = (ReportForm)af;
            udf.reset();
//            HashMap mprp = info.getMprp();
//            if(mprp==null)
//            util.initPrp();
            HashMap dbinfo = info.getDmbsInfoLst();
            String mdflink = util.nvl((String)dbinfo.get("MFGKEY"));
            ArrayList ary=new ArrayList();
            ArrayList blckLst=new ArrayList();
            ArrayList docLst=new ArrayList();  
            ArrayList orderLst=new ArrayList(); 
            ArrayList lotLst=new ArrayList(); 
            HashMap mfgprpset= (session.getAttribute("MFGPRPFACTORYSET") == null)?new HashMap():(HashMap)session.getAttribute("MFGPRPFACTORYSET");
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("FACRUN_RPT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("FACRUN_RPT");
            allPageDtl.put("FACRUN_RPT",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
            pageList= ((ArrayList)pageDtl.get("PRPFLT") == null)?new ArrayList():(ArrayList)pageDtl.get("PRPFLT");             
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=(String)pageDtlMap.get("dflt_val");
            ArrayList prpLst= ((ArrayList)mfgprpset.get(dflt_val+"_VAL") == null)?new ArrayList():(ArrayList)mfgprpset.get(dflt_val+"_VAL");
            if(prpLst==null || prpLst.size()==0){
            ArrayList prpval=new ArrayList();
            ArrayList prpsrt=new ArrayList(); 
            String sqlQ="Select A.Attr_Cd val,a.sq srt From \n" + 
            "(Select A.Attr_Cd,A.Sqnc_Nmbr Sq From "+mdflink+".Mfg_Attrbt_D A\n" + 
            "Where Attr_Typ=?) A\n" + 
            "Order By A.Sq";
                ary=new ArrayList();
                ary.add(dflt_val);
             ArrayList outLst = db.execSqlLst("sql", sqlQ, ary);
            PreparedStatement  pst = (PreparedStatement)outLst.get(0);
            ResultSet   rs = (ResultSet)outLst.get(1);
                    while (rs.next()) {
                        prpval.add(util.nvl((String)util.nvl(rs.getString("val"))));
                        prpsrt.add(util.nvl((String)util.nvl(rs.getString("srt"))));
                    }    
                rs.close();
                pst.close();
            mfgprpset.put(dflt_val+"_VAL", prpval);
            mfgprpset.put(dflt_val+"_SRT", prpval);
            }
            }
            }
            
            String sqlQ="Select A.Lt_Nmbr From \n" + 
            "(Select A.Lt_Nmbr,A.Lt_Nmbr Sq From "+mdflink+".Prp_Lot_M A) A\n" + 
            "Order By A.Sq\n";
          ArrayList outLst = db.execSqlLst("sql", sqlQ, ary);
          PreparedStatement  pst = (PreparedStatement)outLst.get(0);
          ResultSet   rs = (ResultSet)outLst.get(1);
                    while (rs.next()) {
                        lotLst.add(util.nvl((String)util.nvl(rs.getString("Lt_Nmbr"))));
                    }    
                rs.close();
            pst.close();
            mfgprpset.put("lotLst", lotLst);
            session.setAttribute("MFGPRPFACTORYSET", mfgprpset);
            util.updAccessLog(req,res,"Rpt Action", "loadfactoryrun end");
        return am.findForward("loadfactoryrun");
        }
    }
    
    public ActionForward fetchfactoryrun(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "fetchfactoryrun start");
        ReportForm udf = (ReportForm)af;
            HashMap dbinfo = info.getDmbsInfoLst();
            String mdflink = util.nvl((String)dbinfo.get("MFGKEY"));
            ArrayList ary=new ArrayList();
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
            HashMap mfgprpset= (session.getAttribute("MFGPRPFACTORYSET") == null)?new HashMap():(HashMap)session.getAttribute("MFGPRPFACTORYSET");
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("FACRUN_RPT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("FACRUN_RPT");
            allPageDtl.put("FACRUN_RPT",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            pageList= ((ArrayList)pageDtl.get("PRPFLT") == null)?new ArrayList():(ArrayList)pageDtl.get("PRPFLT"); 
            String filterconQ="";
            ArrayList selectCollection= new ArrayList();
            if(pageList!=null && pageList.size() >0){
            for(int k=0;k<pageList.size();k++){
            pageDtlMap=(HashMap)pageList.get(k);
                        String lprp=(String)pageDtlMap.get("dflt_val");
                        String flg=(String)pageDtlMap.get("val_cond");
                        String typ = (String)pageDtlMap.get("fld_typ");
                        String prpSrt = lprp ; 
                         ArrayList lprpS= ((ArrayList)mfgprpset.get(lprp+"_SRT") == null)?new ArrayList():(ArrayList)mfgprpset.get(lprp+"_SRT");
                         ArrayList lprpV = ((ArrayList)mfgprpset.get(lprp+"_VAL") == null)?new ArrayList():(ArrayList)mfgprpset.get(lprp+"_VAL");
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
                                filterconQ += " and b."+lprp+" in("+selprp+") ";
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
                        filterconQ += " and b."+lprp+" between '"+util.nvl((String)lprpS.get(lprpV.indexOf(fldVal1)))+"' and '"+util.nvl((String)lprpS.get(lprpV.indexOf(fldVal2)))+"' ";
                        }
                        } 
            }
            }
            String[] lotLst =  udf.getLotLst();
            String CRTWT_1 = util.nvl((String)udf.getValue("CRTWT_1")); 
            String CRTWT_2 = util.nvl((String)udf.getValue("CRTWT_2")); 
            String pkt_1 = util.nvl((String)udf.getValue("pkt_1")); 
            String pkt_2 = util.nvl((String)udf.getValue("pkt_2"));
            String flg = util.nvl((String)udf.getValue("flg"));  
            if(lotLst!=null){
                String lotval="";
                for(int i=0;i<lotLst.length;i++){
                    lotval+=",'"+lotLst[i]+"'";
                }
                lotval = lotval.replaceFirst("\\,", "");
                filterconQ += " and A.Lt_Nmbr in ("+lotval+")";
            }
            if(!CRTWT_1.equals("") && !CRTWT_2.equals(""))
                filterconQ += " And To_Number(A.crts) Between Nvl("+CRTWT_1+",To_Number(A.Crt)) And Nvl("+CRTWT_2+",To_Number(A.Crt))";
            if(!CRTWT_1.equals("") && CRTWT_2.equals(""))
                filterconQ += " And To_Number(A.crts) Between Nvl("+CRTWT_1+",To_Number(A.Crt)) And Nvl("+CRTWT_1+",To_Number(A.Crt))";
            if(CRTWT_1.equals("") && !CRTWT_2.equals(""))
                filterconQ += " And To_Number(A.crts) Between Nvl("+CRTWT_2+",To_Number(A.Crt)) And Nvl("+CRTWT_2+",To_Number(A.Crt))";
            
            if(!pkt_1.equals("") && !pkt_2.equals(""))
                filterconQ += " And To_Number(A.pckt_nmbr) Between Nvl("+pkt_1+",To_Number(A.Pckt_Nmbr)) And Nvl("+pkt_2+",To_Number(A.Pckt_Nmbr))";
            if(!pkt_1.equals("") && pkt_2.equals(""))
                filterconQ += " And To_Number(A.pckt_nmbr) Between Nvl("+pkt_1+",To_Number(A.Pckt_Nmbr)) And Nvl("+pkt_1+",To_Number(A.Pckt_Nmbr))";
            if(pkt_1.equals("") && !pkt_2.equals(""))
                filterconQ += " And To_Number(A.pckt_nmbr) Between Nvl("+pkt_2+",To_Number(A.Pckt_Nmbr)) And Nvl("+pkt_2+",To_Number(A.Pckt_Nmbr))";
        
            if(!flg.equals("")){
                filterconQ += " and A.Flg =?";
                ary.add(flg);
            }
        
        ArrayList pktLst=new ArrayList();
        ArrayList pktsummryLst=new ArrayList();
        HashMap pktDtlMap=new HashMap();
        String rsltQ="Select a.dscr,a.lt_nmbr,a.pckt_nmbr,a.pcs,to_char(a.crts,'99999999990.000') mk_crt,a.cur_loc,to_char(a.fnl_$val,'99999999999990.000') vlu,a.fnl_rap_rt,to_char(a.fnl_dis_pct,'99999999999990.00') fnl_dis_pct,to_char(b.crt,'99999999999990.000') pls_crt,b.sha,b.col,b.clr,b.cut,b.dp,b.lus,b.flo,b.bis,b.dia,b.pol,b.sym \n" + 
        "From "+mdflink+".Mfg_Mktgsw_V A,"+mdflink+".pkt_atrdtl_v b\n" + 
        "Where a.pckt_id=b.pckt_id\n" + 
        "and a.fnl_attr=b.attr_id ";
            rsltQ = rsltQ +filterconQ+" order by a.flg,a.lt_nmbr,a.pckt_nmbr";
          ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
          PreparedStatement  pst = (PreparedStatement)outLst.get(0);
          ResultSet   rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
                 pktDtlMap=new HashMap();
                 pktDtlMap.put("dscr",util.nvl(rs.getString("dscr")));
                 pktDtlMap.put("lt_nmbr",util.nvl(rs.getString("lt_nmbr")));
                 pktDtlMap.put("pckt_nmbr",util.nvl(rs.getString("pckt_nmbr")));
                 pktDtlMap.put("pcs",util.nvl(rs.getString("pcs")));
                 pktDtlMap.put("mk_crt",util.nvl(rs.getString("mk_crt")));
                 pktDtlMap.put("cur_loc",util.nvl(rs.getString("cur_loc")));
                 pktDtlMap.put("vlu",util.nvl(rs.getString("vlu")));
                 pktDtlMap.put("fnl_rap_rt",util.nvl(rs.getString("fnl_rap_rt")));
                 pktDtlMap.put("fnl_dis_pct",util.nvl(rs.getString("fnl_dis_pct")));
                 pktDtlMap.put("pls_crt",util.nvl(rs.getString("pls_crt")));
                 pktDtlMap.put("sha",util.nvl(rs.getString("sha")));
                 pktDtlMap.put("col",util.nvl(rs.getString("col")));
                 pktDtlMap.put("clr",util.nvl(rs.getString("clr")));
                 pktDtlMap.put("cut",util.nvl(rs.getString("cut")));
                 pktDtlMap.put("dp",util.nvl(rs.getString("dp")));
                 pktDtlMap.put("lus",util.nvl(rs.getString("lus")));
                 pktDtlMap.put("flo",util.nvl(rs.getString("flo")));
                 pktDtlMap.put("bis",util.nvl(rs.getString("bis")));
                 pktDtlMap.put("dia",util.nvl(rs.getString("dia")));
                 pktDtlMap.put("pol",util.nvl(rs.getString("pol")));
                 pktDtlMap.put("sym",util.nvl(rs.getString("sym")));
                 pktLst.add(pktDtlMap);
             }
            rs.close();
            pst.close();
            String rsltsummryQ="Select A.Dscr,Sum(a.Pcs) Tot_Pcs ,to_char(Sum(a.Crts),'99999999999990.000') tot_crts,to_char(sum(b.crt),'99999999999990.000') tot_pls_crt,to_char(sum(FNL_$val),'999999999999999990.000') tot_fnl_vlu\n" + 
            "From "+mdflink+".Mfg_Mktgsw_V A,"+mdflink+".pkt_atrdtl_v b\n" + 
            "Where a.pckt_id=b.pckt_id\n" + 
            "and a.fnl_attr=b.attr_id ";
                rsltsummryQ = rsltsummryQ +filterconQ+" Group By Dscr";
           outLst = db.execSqlLst("search Result", rsltsummryQ, ary);
            pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
                while(rs.next()) {
                    pktDtlMap=new HashMap();
                    pktDtlMap.put("dsc",util.nvl(rs.getString("Dscr")));
                    pktDtlMap.put("tot_pcs",util.nvl(rs.getString("Tot_Pcs")));
                    pktDtlMap.put("tot_crts",util.nvl(rs.getString("tot_crts")));
                    pktDtlMap.put("tot_pls_crt",util.nvl(rs.getString("tot_pls_crt")));
                    pktDtlMap.put("tot_fnl_vlu",util.nvl(rs.getString("tot_fnl_vlu")));
                    pktsummryLst.add(pktDtlMap); 
                }
            rs.close();
            pst.close();
            req.setAttribute("pktsummryLst", pktsummryLst);
            req.setAttribute("pktLst", pktLst);
            req.setAttribute("view", "Y");
            util.updAccessLog(req,res,"Rpt Action", "fetchfactoryrun end");
        return loadfactoryrun(am,af,req,res);
        }
    }
    public ActionForward mixtrfcreateXL(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "mixtrfcreateXL start");
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String loopindex = util.nvl(req.getParameter("loopindex"));
        String fileNm = "mixtransfer" + util.getToDteTime() + ".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        HSSFWorkbook hwb = null;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        hwb = xlUtil.getmixtrfcreateXL(req, loopindex);
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Rpt Action", "mixtrfcreateXL end");
        return null;
        }
    }
    public ActionForward akhaniavgDiffXL(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "akhaniavgDiffXL start");
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "akhaniavgDiff" + util.getToDteTime() + ".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        ArrayList memoList= (ArrayList)session.getAttribute("memoList");
        HSSFWorkbook hwb = null;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        hwb = xlUtil.getDataAkhaniXl(req, memoList);
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Rpt Action", "akhaniavgDiffXL end");
        return null;
        }
    }

    public ActionForward loadwebservice(ActionMapping am, ActionForm af, HttpServletRequest req,
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
        util.updAccessLog(req,res,"Rpt Action", "loadwebservice start");
        ReportForm udf = (ReportForm)af;
        udf.reset();
        util.updAccessLog(req,res,"Rpt Action", "loadwebservice end");
        return am.findForward("loadwebservice");
        }
    }

    public ActionForward callwebservice(ActionMapping am, ActionForm af, HttpServletRequest req,
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
        util.updAccessLog(req,res,"Rpt Action", "callwebservice start");
        ReportForm udf = (ReportForm)af;
        String msg="";
        String reply;
        InputStream is = null;
        DataInputStream dis;
        HashMap dbmsInfo = info.getDmbsInfoLst();
        String webserviceurl = (String)dbmsInfo.get("WEB_SERVICE_INT_URL");
        String nmeIdn = util.nvl((String)udf.getValue("nmeID"));  
        String typ = util.nvl((String)udf.getValue("typ"));  
        String vnmLst = util.nvl((String)udf.getValue("vnm"));
        udf.reset();
        try {
        vnmLst=util.getVnm(vnmLst);
        vnmLst = vnmLst.replaceAll("\\'", "");
        webserviceurl = webserviceurl.replaceAll("~NMEIDN~", nmeIdn); 
        webserviceurl = webserviceurl.replaceAll("~TYP~", typ);
        webserviceurl = webserviceurl.replaceAll("~VNM~", vnmLst);
        System.out.println(webserviceurl);
        URL u = new URL(webserviceurl);
        is = u.openStream();
        dis = new DataInputStream(new BufferedInputStream(is)); 
        while ((reply = dis.readLine()) != null) {
        msg=reply.substring(reply.indexOf(">")+1, reply.indexOf("</memoid>"));
        System.out.println(msg);
        }
        } catch (MalformedURLException mue) {
        System.out.println("Ouch - a MalformedURLException happened.");
        mue.printStackTrace();
        msg="Web Service call failed";
        } catch (IOException ioe) {
        System.out.println("Oops- an IOException happened.");
        ioe.printStackTrace();
        msg="Web Service call failed";
        }   
        if(msg.equals(""))
        msg="Web Service called Sucessfully";  
        req.setAttribute("msg", msg);
        util.updAccessLog(req,res,"Rpt Action", "callwebservice end");
        return am.findForward("loadwebservice");
        }
    }
    public ActionForward loaddlvsummaryrpt(ActionMapping am, ActionForm form, HttpServletRequest req,
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
        util.updAccessLog(req,res,"Rpt Action", "loaddlvsummaryrpt start");
    ReportForm udf = (ReportForm)form;
    udf.reset();
    dlvsummaryData(req,res,udf);
        util.updAccessLog(req,res,"Rpt Action", "loaddlvsummaryrpt end");
    return am.findForward("loaddlvsummaryrpt");
    }
    }
    public ActionForward fetchdlvsummaryrpt(ActionMapping am, ActionForm form, HttpServletRequest req,
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
        util.updAccessLog(req,res,"Rpt Action", "fetchdlvsummaryrpt start");
    ReportForm udf = (ReportForm)form;
    dlvsummaryData(req,res,udf);
    udf.reset();
        util.updAccessLog(req,res,"Rpt Action", "fetchdlvsummaryrpt end");
    return am.findForward("loaddlvsummaryrpt");
    }
    }
    
    public ActionForward hitratio(ActionMapping am, ActionForm form, HttpServletRequest req,
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
        util.updAccessLog(req,res,"Rpt Action", "hitratio start");
        ReportForm udf = (ReportForm)form;
        udf.reset();
        GenericInterface genericInt = new GenericImpl();
        genericInt.genericPrprVw(req,res,"HIT_RATIO_VW","HIT_RATIO_VW");
        SearchQuery srchQuery = new SearchQuery();
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("HITRATIO");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("HITRATIO");
        allPageDtl.put("HITRATIO",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        
        ArrayList empList= srchQuery.getByrList(req,res);
        udf.setByrList(empList); 
        ArrayList memoList = srchQuery.getHitMemoType(req,res);
        udf.setMemoList(memoList);
        if(cnt.equals("hk"))
        util.groupcompany();
        util.updAccessLog(req,res,"Rpt Action", "hitratio end");
        return am.findForward("hitratio");
        }
    }
    
    
    public ActionForward fetchhitratio(ActionMapping am, ActionForm form, HttpServletRequest req,
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
        util.updAccessLog(req,res,"Rpt Action", "fetchhitratio start");
        ReportForm udf = (ReportForm)form;
        String empId =(String)udf.getValue("empId");
        String byrId = (String)udf.getValue("byrId");
        String typ =(String)udf.getValue("typ");
        String frmdte = (String)udf.getValue("frmdte");
        String todte =(String)udf.getValue("todte");
        String group = util.nvl((String)udf.getValue("group"));
        String row = util.nvl((String)udf.getValue("row"));
        String column = util.nvl((String)udf.getValue("column"));
            String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
            String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
            String dfNmeIdn=util.nvl((String)info.getDfNmeIdn()); 
            ArrayList rolenmLst=(ArrayList)info.getRoleLst();
            String usrFlg=util.nvl((String)info.getUsrFlg());
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            SearchQuery srchQuery = new SearchQuery();
            HashMap hitratiodata=new HashMap();
            ArrayList hitratiorowprpValLst=new ArrayList();
            ArrayList hitratiocolprpValLst=new ArrayList();
            ArrayList hitratiomemoTyp=new ArrayList();
            ArrayList ary=new ArrayList();
            String conQ=" and jd.typ in ('I', 'E','O','K','H', 'WAP','HAP','BAP','KAP','OAP', 'CS','BIDAP','BID') ";
            if(!typ.equals(""))
            conQ=" and jd.typ in ('"+typ+"')";
            if(!group.equals("")){
              conQ =conQ+ " and nm.grp_nme_idn="+group;
            }else{
            if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
            }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
            conQ =conQ+" and nm.grp_nme_idn="+dfgrpnmeidn; 
            }  
            }
            
            if(!empId.equals("") && empId.length()>1){
              conQ+= " and nm.emp_idn= "+empId;
            }
                  
            if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                conQ += " and (nm.emp_idn= "+dfNmeIdn+" or nm.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= "+dfNmeIdn+")) ";  
            }
            if(!byrId.equals("")){
              conQ+= " and nm.nme_idn= "+byrId;
            }
            
            String conQdte=" and trunc(jd.dte) >= trunc(sysdate) - 7 ";
            if(!frmdte.equals("") && !todte.equals(""))
            conQdte = " and trunc(jd.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
            if(frmdte.equals("") && !todte.equals(""))
            conQdte = " and trunc(jd.dte) between to_date('"+todte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
            if(!frmdte.equals("") && todte.equals(""))
            conQdte = " and trunc(jd.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+frmdte+"' , 'dd-mm-yyyy') ";
            
            conQ+= conQdte;
            String countQ="WITH MEMO_TTL as (\n" + 
            "  SELECT COUNT(*) CNT, JD.TYP\n" + 
            "   from mstk m, jandtl jd,mjan mj,nme_v nm\n" + 
            "   WHERE 1 = 1\n" + 
            "    and mj.idn=jd.idn\n" + 
            "    and m.idn = jd.mstk_idn\n" + 
            "    and nm.nme_idn=mj.nme_idn\n" + 
            "    and m.pkt_ty = 'NR'\n" + conQ+
            "    and jd.stt not in ('CL', 'MRG')\n" + 
            "   group by jd.typ\n" + 
            " )\n" + 
            " , MEMO_RT_SMRY AS (\n" + 
            "  SELECT COUNT(*) CNT, JD.TYP, rw.VAL rowprp, cl.VAL colprp, rw.SRT SH_SRT, cl.SRT SZ_SRT\n" + 
            "   from mstk m, jandtl jd,mjan mj,nme_v nm, stk_dtl rw, stk_dtl cl\n" + 
            "   WHERE 1 = 1\n" + 
            "    and mj.idn=jd.idn\n" + 
            "    and m.idn = jd.mstk_idn\n" + 
            "    and nm.nme_idn=mj.nme_idn\n" + 
            "    and m.pkt_ty = 'NR'\n" + 
            "    and m.idn = rw.mstk_idn and rw.grp = 1 and rw.mprp = '"+row+"'\n" + 
            "    and m.idn = cl.mstk_idn and cl.grp = 1 and cl.mprp = '"+column+"'\n" +  conQ+
            "    AND JD.STT IN ('APRT', 'RT')\n" + 
            "   GROUP BY JD.TYP, rw.VAL, cl.VAL, rw.SRT, cl.SRT\n" + 
            " )\n" + 
            " select round(100 - (s.cnt*100/t.cnt)) CNF_PCT, t.typ, s.rowprp, s.colprp\n" + 
            " from MEMO_TTL t, MEMO_RT_SMRY s\n" + 
            " where t.typ = s.typ\n" + 
            " order by s.sh_srt, s.sz_srt, t.typ, 1";
         ArrayList outLst = db.execSqlLst("search Result", countQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                String rowprpval=util.nvl(rs.getString("rowprp"));
                String colprpval=util.nvl(rs.getString("colprp"));
                String memotyp=util.nvl(rs.getString("typ"));
                if(!hitratiorowprpValLst.contains(rowprpval))
                hitratiorowprpValLst.add(rowprpval);
                if(!hitratiocolprpValLst.contains(colprpval))
                hitratiocolprpValLst.add(colprpval); 
                if(!hitratiomemoTyp.contains(memotyp))
                hitratiomemoTyp.add(memotyp); 
                hitratiodata.put(memotyp+"_"+rowprpval+"_"+colprpval,util.nvl(rs.getString("CNF_PCT")));
            }
            rs.close();
            pst.close();
        udf.reset();
        ArrayList memoList = srchQuery.getHitMemoType(req,res);
        udf.setMemoList(memoList);
        req.setAttribute("row", row); 
        req.setAttribute("column", column);
        req.setAttribute("hitratiodata", hitratiodata);
        req.setAttribute("hitratiomemoTyp", hitratiomemoTyp);
        req.setAttribute("hitratiorowprpValLst", hitratiorowprpValLst);
        req.setAttribute("hitratiocolprpValLst", hitratiocolprpValLst);
        req.setAttribute("view", "Y");
        util.updAccessLog(req,res,"Rpt Action", "fetchhitratio end");
        return am.findForward("hitratio");
        }
    }
    
    public ActionForward loadPriceDiff(ActionMapping am, ActionForm form, HttpServletRequest req,
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
             util.updAccessLog(req,res,"Rpt Action", "loadPriceDiff start");
         ReportForm udf = (ReportForm)form;
         udf.reset();
             GenericInterface genericInt = new GenericImpl();
             genericInt.genericPrprVw(req,res,"PRC_ZERO_VW","PRC_ZERO_VW");
             HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
             HashMap pageDtl=(HashMap)allPageDtl.get("PRICE_ZERO_DIFF");
             if(pageDtl==null || pageDtl.size()==0){
             pageDtl=new HashMap();
             pageDtl=util.pagedef("PRICE_ZERO_DIFF");
             allPageDtl.put("PRICE_ZERO_DIFF",pageDtl);
             }
             info.setPageDetails(allPageDtl);
             util.updAccessLog(req,res,"Rpt Action", "loadPriceDiff end");
         return am.findForward("loadPriceDiff");
         }
         }
    public ActionForward viewPriceDiff(ActionMapping am, ActionForm form, HttpServletRequest req,
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
         util.updAccessLog(req,res,"Rpt Action", "viewPriceDiff start");
     ReportForm udf = (ReportForm)form;
       
         String status =util.nvl((String)udf.getValue("status"),"MKAV");
         String pkttype = util.nvl((String)udf.getValue("pkttype"),"NR");
         String diffr = util.nvl((String)udf.getValue("diffr"),"1");
         String frmpro =util.nvl((String)udf.getValue("frmpro"));
         String topro = util.nvl((String)udf.getValue("topro"));
         String ctsfr =util.nvl((String)udf.getValue("ctsfr"));
         String ctsto = util.nvl((String)udf.getValue("ctsto"));
         String pricepro = util.nvl((String)udf.getValue("pricepro"));
         String prczero = util.nvl((String)udf.getValue("prczero"));
         String rappricediff = util.nvl((String)udf.getValue("rappricediff"));
         String vnm = util.nvl((String)udf.getValue("vnm"));
         String sqlQry="";
         String conQ="";
         ArrayList pktList =new ArrayList();
         ArrayList itemHdr =new ArrayList();
         ArrayList ary =new ArrayList();
         if(pkttype.equals("NR"))
         conQ=" and a.pkt_ty='NR'";
         else
         conQ=" and a.pkt_ty <>'NR'"  ;
         
         if(!ctsfr.equals("") && !ctsto.equals(""))
         conQ += " and a.cts between "+ctsfr+" and "+ctsto+" ";
         if(ctsfr.equals("") && !ctsto.equals(""))
         conQ +=  " and a.cts between "+ctsto+" and "+ctsto+" ";
         if(!ctsfr.equals("") && ctsto.equals(""))
         conQ +=  " and a.cts between "+ctsfr+" and "+ctsfr+" ";
         if(!vnm.equals("")){
             vnm=util.nvl(util.getVnm(vnm));
             conQ+=" and  a.vnm in ("+vnm+")";
         }
         itemHdr.add("Status");
         itemHdr.add("Vnm");
         itemHdr.add("Cts");
         if(!prczero.equals("")){
           status=util.nvl(util.getVnm(status));
           sqlQry =" select a.idn,a.sk1,a.vnm,to_char(trunc(a.cts,2),'90.99') cts ,a.qty,nvl(a.upr,a.cmp) cmp ,a.stt,b.num,b.srt from \n" + 
                   " mstk a,stk_dtl b where a.idn=b.mstk_idn and b.mprp= ?  and b.grp=1  \n" + conQ+
                   " and nvl(b.num,0)=0 and  a.stt in ("+status+") order by a.sk1,a.cts ";
            ary =new ArrayList();
            ary.add(pricepro); 
           ArrayList outLst = db.execSqlLst("Price Result", sqlQry , ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet rs = (ResultSet)outLst.get(1);
             while(rs.next()){
                         HashMap pktPrpMap = new HashMap();
                         pktPrpMap.put("Vnm", util.nvl(rs.getString("vnm")));
                         pktPrpMap.put("Cts", util.nvl(rs.getString("cts")));
                         pktPrpMap.put("Status", util.nvl(rs.getString("stt")));                         
                         pktList.add(pktPrpMap);
             }
             rs.close();
             pst.close();
         }else if(!rappricediff.equals("")){
             frmpro="RAP_RTE";
             topro="LST_RAP";
             if(!status.equals("")){
                 status=util.nvl(util.getVnm(status));
                 conQ+=" and  a.stt in ("+status+")";
             }
                 sqlQry =" select a.idn,a.sk1,a.vnm,to_char(trunc(a.cts,2),'99990.99') cts,a.qty,nvl(a.upr,a.cmp) cmp ,a.stt,a.rap_rte price1,cm2.num price2, trunc(decode(rap_rte, 1, null, ((a.rap_rte-cm2.num)/cm2.num)*100), 2) diff,to_char(trunc(cts,2) * a.rap_rte, '999999999990.00') price1Vlu,to_char(trunc(cts,2) * cm2.num, '999999999990.00') price2Vlu \n" + 
                 "from mstk a,stk_dtl cm2 where 1=1 \n" + conQ+
                 " and a.idn=cm2.mstk_idn and cm2.mprp= 'LST_RAP' and cm2.grp=1 and nvl(a.rap_rte,0) <> 0 and nvl(cm2.num,0) <> 0  \n" + 
                 " order by a.sk1,a.cts  ";
                 ary =new ArrayList();
           ArrayList outLst = db.execSqlLst("Price Result", sqlQry , ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet rs = (ResultSet)outLst.get(1);
                 while(rs.next()){
                         HashMap pktPrpMap = new HashMap();
                         pktPrpMap.put("Vnm", util.nvl(rs.getString("vnm")));
                         pktPrpMap.put("Cts", util.nvl(rs.getString("cts")));
                         pktPrpMap.put("Status", util.nvl(rs.getString("stt")));
                         pktPrpMap.put(frmpro, util.nvl(rs.getString("price1")));
                         pktPrpMap.put(topro, util.nvl(rs.getString("price2")));
                         pktPrpMap.put("Diff", util.nvl(rs.getString("diff")));     
                         pktPrpMap.put(frmpro+" VLU", util.nvl(rs.getString("price1Vlu")));
                         pktPrpMap.put(topro+" VLU", util.nvl(rs.getString("price2Vlu")));
                         pktList.add(pktPrpMap);
                 }
                 rs.close();
                 pst.close();
             sqlQry =" select trunc(((sum(a.rap_rte)-sum(cm2.num))/sum(cm2.num))*100,2) diff,\n" + 
             "sum(a.rap_rte) price1,\n" + 
             "sum(cm2.num) price2 ,\n" + 
             "round(sum(trunc(cts,2) * a.rap_rte)) price1Vlu,\n" + 
             "round(sum(trunc(cts,2) * cm2.num)) price2Vlu \n" + 
             "from mstk a,stk_dtl cm2 where 1=1 \n" + conQ+
             " and a.idn=cm2.mstk_idn and cm2.mprp= 'LST_RAP' and cm2.grp=1 and nvl(a.rap_rte,0) <> 0 and nvl(cm2.num,0) <> 0  \n" ;
             ary =new ArrayList();
            outLst = db.execSqlLst("Price Result", sqlQry , ary);
             pst = (PreparedStatement)outLst.get(0);
              rs = (ResultSet)outLst.get(1);
             while(rs.next()){
                     HashMap pktPrpMap = new HashMap();
                     pktPrpMap.put("Diff", util.nvl(rs.getString("diff")));     
                     pktPrpMap.put(frmpro+" VLU", util.nvl(rs.getString("price1Vlu")));
                     pktPrpMap.put(topro+" VLU", util.nvl(rs.getString("price2Vlu")));
                     pktList.add(pktPrpMap);
             }
               rs.close();
               
               pst.close();
                     itemHdr.add(frmpro);
                     itemHdr.add(topro);
                     itemHdr.add("Diff");
                     itemHdr.add(frmpro+" VLU");
                     itemHdr.add(topro+" VLU");
         }else{
                      status=util.nvl(util.getVnm(status));
              sqlQry =" select a.idn,a.sk1,a.vnm,to_char(trunc(a.cts,2),'99990.99') cts,a.qty,nvl(a.upr,a.cmp) cmp ,a.stt,cm1.num price1 ,cm2.num price2, trunc(decode(rap_rte, 1, null, ((cm2.num-cm1.num)/cm1.num)*100), 2) diff \n" + 
                   " from mstk a,stk_dtl cm1,stk_dtl cm2 where 1=1 \n" + conQ+
                   " and a.idn=cm1.mstk_idn and cm1.mprp= ? and cm1.grp=1 \n" + 
                   " and a.stt in ("+status+") and a.idn=cm2.mstk_idn and cm2.mprp= ? and cm2.grp=1 and nvl(cm1.num,0) <> 0 and nvl(cm2.num,0) <> 0 and \n" + 
                   "  trunc(decode(rap_rte, 1, null, ((cm2.num-cm1.num)/cm1.num)*100), 2) > ?  order by 10,a.sk1,a.cts ";
                      ary =new ArrayList();
                      ary.add(frmpro); 
                      ary.add(topro);  
                      ary.add(diffr);
                   ArrayList outLst = db.execSqlLst("Price Result", sqlQry , ary);
                   PreparedStatement  pst = (PreparedStatement)outLst.get(0);
                  ResultSet    rs = (ResultSet)outLst.get(1);
                  while(rs.next()){
                          HashMap pktPrpMap = new HashMap();
                          pktPrpMap.put("Vnm", util.nvl(rs.getString("vnm")));
                          pktPrpMap.put("Cts", util.nvl(rs.getString("cts")));
                          pktPrpMap.put("Status", util.nvl(rs.getString("stt")));
                          pktPrpMap.put(frmpro, util.nvl(rs.getString("price1")));
                          pktPrpMap.put(topro, util.nvl(rs.getString("price2")));
                          pktPrpMap.put("Diff", util.nvl(rs.getString("diff")));                        
                          pktList.add(pktPrpMap);
                  }
             
                    rs.close();
                   pst.close();
                      itemHdr.add(frmpro);
                      itemHdr.add(topro);
                      itemHdr.add("Diff");
                  }            
          session.setAttribute("PrcitemHdr", itemHdr);
          session.setAttribute("PrcpktList", pktList);
         req.setAttribute("view", "Y");
          util.updAccessLog(req,res,"Rpt Action", "viewPriceDiff end");
     return loadPriceDiff(am,form,req,res);
     }
     }
        
    public ActionForward createPRCXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"PRICE ZERO Report", "createPRCXL start");
        HashMap dbinfo = info.getDmbsInfoLst();
        String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
        int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        ArrayList pktList = (ArrayList)session.getAttribute("PrcpktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("PrcitemHdr");
        int pktListsz=pktList.size();
     
            HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
            String contentTypezip = "application/zip";
            String fileNmzip = "PriceReport"+util.getToDteTime();
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
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "PriceReport"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        hwb.write(out);
        out.flush();
        out.close();
        }
            util.updAccessLog(req,res,"PRICE ZERO Report", "createPRCXL end");
        return null;
        }
    }
    public ActionForward loadmixprofit(ActionMapping am, ActionForm form, HttpServletRequest req,
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
        util.updAccessLog(req,res,"Rpt Action", "loadmixprofit start");
        ReportForm udf = (ReportForm)form;
        SearchQuery srchQuery = new SearchQuery();
        ArrayList empList= srchQuery.getByrList(req,res);
        udf.setByrList(empList);
        util.updAccessLog(req,res,"Rpt Action", "loadmixprofit end");
        return am.findForward("loadmixprofit");
        }
    }
    
    public ActionForward loadmixprofitpkt(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "loadmixprofitpkt start");
            ReportForm udf = (ReportForm)form;
                String idn =util.nvl((String)req.getParameter("idn"));
                String trmidn = util.nvl((String)req.getParameter("trmidn"));
                String frmdte = util.nvl((String)req.getParameter("frmdte"));
                String todte =util.nvl((String)req.getParameter("todte"));
                String conQ="";
                String conQterm="";
                ArrayList pktList=new ArrayList();
                HashMap pktPrpMap=new HashMap();
                ArrayList itemHdr=new ArrayList();
                
            if(!idn.equals("")){
                    conQ+=" and m.idn in ("+idn+") ";
            }
            
            if(!trmidn.equals("")){
                        conQterm+=" and s.trms_idn in ("+trmidn+") ";
            }
                
            String conQdte=" and trunc(s.dte) >= trunc(sysdate) - 30 ";
            if(!frmdte.equals("") && !todte.equals(""))
            conQdte = " and trunc(s.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
            if(frmdte.equals("") && !todte.equals(""))
            conQdte = " and trunc(s.dte) between to_date('"+todte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
            if(!frmdte.equals("") && todte.equals(""))
            conQdte = " and trunc(s.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+frmdte+"' , 'dd-mm-yyyy') ";
            

            String dtlQ=
            "select m.sk1,m.vnm, js.idn, js.memo_idn, js.dte\n " + 
            ", nvl(aadat_comm, 0)+nvl(brk1_comm, 0) aadat\n " + 
            ", t.term||' : '||t.pct terms\n " + 
            ", nvl(brk2_comm, 0)+nvl(brk3_comm, 0)+nvl(brk4_comm, 0) brk\n " + 
            ", js.cts cts\n " + 
            ", round((js.rte*js.cts)) bseVlu\n " + 
            ", round((trunc(nvl(js.fnl_sal, js.quot)/decode(s.cur, 'INR', s.exh_rte, 1), 2)*js.cts)) slVlu \n " + 
            ", round(((trunc(nvl(js.fnl_sal, js.quot)/decode(s.cur, 'INR', s.exh_rte, 1), 2)*js.cts)\n " + 
            "            *(1 - ((nvl(aadat_comm, 0)+nvl(brk1_comm, 0))/100)))\n " + 
            "            *(1 - (t.pct/100))\n " + 
            "            *(1 - ((nvl(brk2_comm, 0)+nvl(brk3_comm, 0)+nvl(brk4_comm, 0))/100)))             \n " + 
            "netVlu \n " + 
            "from mstk m, msal s, jansal js, mtrms t   \n " + 
            "where m.idn = js.mstk_idn and s.idn = js.idn  and s.trms_idn = t.idn and m.vnm not in('131.001')\n " + 
            "and m.pkt_ty <> 'NR' \n " + conQdte+conQ+conQterm+
            " and js.stt not like '%RT'\n " + 
            "order by 1,m.vnm "; 
              ArrayList outLst = db.execSqlLst("reportSql", dtlQ , new ArrayList());
              PreparedStatement  pst = (PreparedStatement)outLst.get(0);
              ResultSet    rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                pktPrpMap=new HashMap();
                pktPrpMap.put("VNM", util.nvl((String)rs.getString("vnm")));
                pktPrpMap.put("CTS", util.nvl((String)rs.getString("cts")));
                pktPrpMap.put("SALE_ID", util.nvl((String)rs.getString("idn")));
                pktPrpMap.put("MEMO_ID", util.nvl((String)rs.getString("memo_idn")));
                pktPrpMap.put("DATE", util.nvl((String)rs.getString("dte")));
                pktPrpMap.put("TERM", util.nvl((String)rs.getString("terms")));
                pktPrpMap.put("BRK", util.nvl((String)rs.getString("brk")));
                pktPrpMap.put("BSEVLU", util.nvl((String)rs.getString("bseVlu")));
                pktPrpMap.put("NETVLU", util.nvl((String)rs.getString("netVlu")));
                pktPrpMap.put("SLVLU", util.nvl((String)rs.getString("slVlu")));
                pktList.add(pktPrpMap);
            }
            rs.close();  
                pst.close();
                itemHdr.add("VNM");
                itemHdr.add("CTS");
                itemHdr.add("SALE_ID");
                itemHdr.add("MEMO_ID");
                itemHdr.add("DATE");
                itemHdr.add("TERM");
                itemHdr.add("BRK");
                itemHdr.add("BSEVLU");
                itemHdr.add("NETVLU");
                itemHdr.add("SLVLU");
            session.setAttribute("mixprofitpktListpkt", pktList);
            session.setAttribute("mixprofititemHdrpkt", itemHdr);
            req.setAttribute("view", "Y");
            util.updAccessLog(req,res,"Rpt Action", "loadmixprofitpkt end");
            return am.findForward("loadmixprofitpkt");
            }
        }
    
    public ActionForward fetchmixprofit(ActionMapping am, ActionForm form, HttpServletRequest req,
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
        util.updAccessLog(req,res,"Rpt Action", "fetchmixprofit start");
        ReportForm udf = (ReportForm)form;
            String empId =util.nvl((String)udf.getValue("empId"));
            String byrId = util.nvl((String)udf.getValue("byrId"));
            String vnm =util.nvl((String)udf.getValue("vnm"));
            String frmdte = util.nvl((String)udf.getValue("frmdte"));
            String todte =util.nvl((String)udf.getValue("todte"));
            String group = util.nvl((String)udf.getValue("group"));
            String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
            String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
            String dfNmeIdn=util.nvl((String)info.getDfNmeIdn()); 
            ArrayList rolenmLst=(ArrayList)info.getRoleLst();
            String usrFlg=util.nvl((String)info.getUsrFlg());
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            String conQ="";
            ArrayList pktList=new ArrayList();
            HashMap pktPrpMap=new HashMap();
            HashMap pktPrpMapttl=new HashMap();
            ArrayList itemHdr=new ArrayList();
            SearchQuery srchQuery = new SearchQuery();
            udf.resetALL();
            if(!group.equals("")){
                          conQ =conQ+ " and nm.grp_nme_idn="+group;
                        }else{
                        if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
                        }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
                        conQ =conQ+" and nm.grp_nme_idn="+dfgrpnmeidn; 
                        }  
                        }
                        
                        if(!empId.equals("") && empId.length()>1){
                          conQ+= " and nm.emp_idn= "+empId;
                        }
                              
                        if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                        conQ += " and (nm.emp_idn= "+dfNmeIdn+" or nm.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= "+dfNmeIdn+")) ";
                        }
                        if(!byrId.equals("")){
                          conQ+= " and nm.nme_idn= "+byrId;
                        }
            if(!vnm.equals("")){
                vnm = util.getVnm(vnm);
                conQ+=" and m.vnm in("+vnm+")";
            }
            
        String conQdte=" and trunc(s.dte) >= trunc(sysdate) - 30 ";
        if(!frmdte.equals("") && !todte.equals(""))
        conQdte = " and trunc(s.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
        if(frmdte.equals("") && !todte.equals(""))
        conQdte = " and trunc(s.dte) between to_date('"+todte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
        if(!frmdte.equals("") && todte.equals(""))
        conQdte = " and trunc(s.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+frmdte+"' , 'dd-mm-yyyy') ";
        

            
            String dtlQ="WITH MIX_SL AS (\n" +
            "select m.sk1,m.vnm,m.idn,sum(js.qty) qty, sum(js.cts) cts\n" +
            ", round(sum(js.rte*js.cts)) bseVlu\n" +
            ", round(sum(trunc(nvl(js.fnl_sal, js.quot)/decode(s.cur, 'INR', s.exh_rte, 1), 2)*js.cts)) slVlu \n" +
            ", round(sum(js.cts*decode(greatest(js.rte, nvl(js.fnl_sal, js.quot)/decode(s.cur, 'INR', s.exh_rte, 1)), js.rte, 0.25, 0.50)*((nvl(js.fnl_sal, js.quot)/decode(s.cur, 'INR', s.exh_rte, 1)) - js.rte))) adjVlu"+
            ", round((sum(trunc(nvl(js.fnl_sal, js.quot)/decode(s.cur, 'INR', s.exh_rte, 1), 2)*js.cts)\n" +
            " *(1 - ((nvl(aadat_comm, 0)+nvl(brk1_comm, 0))/100)))\n" +
            " *(1 - (T.PCT/100))\n" +
            " *(1 - ((NVL(BRK2_COMM, 0)+NVL(BRK3_COMM, 0)+NVL(BRK4_COMM, 0))/100)))NETVLU ,S.TRMS_IDN,T.TERM\n" +
            "FROM MSTK M, MSAL S, JANSAL JS, MTRMS T ,NME_V NM\n" +
            "where m.idn = js.mstk_idn and s.idn = js.idn and s.trms_idn = t.idn and s.nme_idn=nm.nme_idn\n" +
            "AND M.PKT_TY <> 'NR' \n" + conQdte+conQ+
            "and js.stt not like '%RT' and m.vnm not in('131.001')\n" +
            "--and mstk_idn = 800006693\n" +
            "group by m.sk1,m.vnm,m.idn, aadat_comm, brk1_comm, brk2_comm, brk3_comm, brk4_comm, t.pct,s.trms_idn,t.term\n" +
            ") \n" +
            "select sk1,vnm,idn,qty, cts\n" +
            ", netVlu\n" +
            ", netVlu - bseVlu netDiff\n" +
            ", trunc(((netVlu - bseVlu)*100/bseVlu), 2) net_pct\n" +
            ", slVlu\n" +
            ", SLVLU - BSEVLU DIFF\n" +
            ", adjVlu"+
            ", trunc(((slVlu - bseVlu)*100/bseVlu), 2) diff_pct,trms_idn,term,tqty, tcts, tslvlu, tnetvlu, tbsevlu,ttlnetdiff,ttlnet_pct,ttldiff,ttldiff_pct\n" +
            "FROM MIX_SL m, (select sum(qty) tqty,sum(cts) tcts, sum(slVlu) tslVlu, sum(netVlu) tnetVlu, sum(bseVlu) tbseVlu,sum(netVlu) - sum(bseVlu) ttlnetDiff,trunc(((sum(netVlu) - sum(bseVlu))*100/sum(bseVlu)), 2) ttlnet_pct,sum(slvlu) - sum(bsevlu) ttldiff,trunc(((sum(slvlu) - sum(bsevlu))*100/sum(bsevlu)), 2) ttldiff_pct from mix_sl s)\n" +
            "ORDER BY 1,vnm,term";
          ArrayList outLst = db.execSqlLst("reportSql", dtlQ , new ArrayList());
          PreparedStatement  pst = (PreparedStatement)outLst.get(0);
          ResultSet    rs = (ResultSet)outLst.get(1);
            while(rs.next()){
            pktPrpMap=new HashMap();
            pktPrpMap.put("IDN", util.nvl((String)rs.getString("idn")));
            pktPrpMap.put("VNM", util.nvl((String)rs.getString("vnm")));
            pktPrpMap.put("QTY", util.nvl((String)rs.getString("qty")));
            pktPrpMap.put("CTS", util.nvl((String)rs.getString("cts")));
            pktPrpMap.put("NETVLU", util.nvl((String)rs.getString("netVlu")));
            pktPrpMap.put("NETDIFF", util.nvl((String)rs.getString("netDiff")));
            pktPrpMap.put("NETPCT", util.nvl((String)rs.getString("net_pct")));
            pktPrpMap.put("SLVLU", util.nvl((String)rs.getString("slVlu")));
            pktPrpMap.put("ADJVLU", util.nvl((String)rs.getString("adjVlu")));
            pktPrpMap.put("DIFF", util.nvl((String)rs.getString("DIFF")));
            pktPrpMap.put("DIFFPCT", util.nvl((String)rs.getString("diff_pct")));
            pktPrpMap.put("TRMS_IDN", util.nvl((String)rs.getString("trms_idn")));
            pktPrpMap.put("TERM", util.nvl((String)rs.getString("term")));
            pktList.add(pktPrpMap);
            if(pktPrpMapttl.size()==0){
                pktPrpMapttl=new HashMap();
                pktPrpMapttl.put("IDN","TOTAL");
                pktPrpMapttl.put("VNM", "TOTAL");
                pktPrpMapttl.put("QTY", util.nvl((String)rs.getString("tqty")));
                pktPrpMapttl.put("CTS", util.nvl((String)rs.getString("tcts")));
                pktPrpMapttl.put("NETVLU", util.nvl((String)rs.getString("tnetvlu")));
                pktPrpMapttl.put("SLVLU", util.nvl((String)rs.getString("tslVlu"))); 
                pktPrpMapttl.put("ADJVLU", util.nvl((String)rs.getString("adjVlu")));  
                pktPrpMapttl.put("NETDIFF", util.nvl((String)rs.getString("ttlnetdiff")));
                pktPrpMapttl.put("NETPCT", util.nvl((String)rs.getString("ttlnet_pct")));  
                pktPrpMapttl.put("DIFF", util.nvl((String)rs.getString("ttldiff")));
                pktPrpMapttl.put("DIFFPCT", util.nvl((String)rs.getString("ttldiff_pct")));  
            }
        }
        rs.close();  
            pst.close();
            if(pktPrpMapttl.size()>1)
            pktList.add(pktPrpMapttl);
            itemHdr.add("VNM");
            itemHdr.add("QTY");
            itemHdr.add("CTS");
            itemHdr.add("NETVLU");
            itemHdr.add("NETDIFF");
            itemHdr.add("NETPCT");
            itemHdr.add("SLVLU");
            itemHdr.add("ADJVLU");
            itemHdr.add("DIFF");
            itemHdr.add("DIFFPCT");
            itemHdr.add("TERM");
        session.setAttribute("mixprofitpktList", pktList);
        session.setAttribute("mixprofititemHdr", itemHdr);
        req.setAttribute("frmdte", frmdte);
        req.setAttribute("todte", todte);
        req.setAttribute("view", "Y");
        ArrayList empList= srchQuery.getByrList(req,res);
        udf.setByrList(empList);
        util.updAccessLog(req,res,"Rpt Action", "fetchmixprofit end");
        return am.findForward("loadmixprofit");
        }
    }
    public ActionForward mixprofitcreateXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
                util.updAccessLog(req,res,"Report Action", "mixprofitcreateXL start");
            ExcelUtil xlUtil = new ExcelUtil();
            xlUtil.init(db, util, info);
            OutputStream out = res.getOutputStream();
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "mixprofit"+util.getToDteTime()+".xls";
            String typ= util.nvl((String)req.getParameter("typ"));
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
            ArrayList pktList = (ArrayList)session.getAttribute("mixprofitpktList");
            ArrayList itemHdr = (ArrayList)session.getAttribute("mixprofititemHdr");
            if(typ.equals("PKT")){
                pktList = (ArrayList)session.getAttribute("mixprofitpktListpkt");
                itemHdr = (ArrayList)session.getAttribute("mixprofititemHdrpkt");
            }
            HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            hwb.write(out);
            out.flush();
            out.close();
                util.updAccessLog(req,res,"Report Action", "mixprofitcreateXL end");
            return null;
            }
        }
    public ActionForward createXLdlvSummary(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Rpt Action", "createXLdlvSummary start");
        String byr=util.nvl((String)req.getParameter("byr"));
        String packet=util.nvl((String)req.getParameter("packet"));
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "createXLdlvSummary"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList pktList=new ArrayList();
        ArrayList itemHdr=new ArrayList();
            if(packet.equals("")){
                if(byr.equals("")){
                pktList = (ArrayList)session.getAttribute("pktList");
                itemHdr = (ArrayList)session.getAttribute("itemHdr");
                }else{
                    pktList = (ArrayList)session.getAttribute("byrDtlList");
                    itemHdr = (ArrayList)session.getAttribute("itemHdrbyr");    
                }
            } else {
        
            pktList = (ArrayList)session.getAttribute("pktPrpDtlList");
            itemHdr = (ArrayList)session.getAttribute("itemHdrPrp");    
        }        
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Rpt Action", "createXLdlvSummary end");
        return null;
        }
    }
    public void dlvsummaryData(HttpServletRequest req,HttpServletResponse res,ReportForm udf) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String frmdte = util.nvl((String)udf.getValue("dtefr"));
        String todte = util.nvl((String)udf.getValue("dteto"));
        String nmeID = util.nvl((String)udf.getValue("byrIdn"));
        String typ = util.nvl((String)udf.getValue("typ"));
        ArrayList pktDtlList = new ArrayList();
        ArrayList byrDtlList = new ArrayList();
        ArrayList params = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        ArrayList itemHdrbyr = new ArrayList();
        String conQdte=" and trunc(b.dte)=trunc(sysdate)";
        if(!frmdte.equals("") && !todte.equals(""))
        conQdte = " and trunc(b.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
        if(frmdte.equals("") && !todte.equals(""))
        conQdte = " and trunc(b.dte) between to_date('"+todte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
        if(!frmdte.equals("") && todte.equals(""))
        conQdte = " and trunc(b.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+frmdte+"' , 'dd-mm-yyyy') ";
        
        if(!nmeID.equals("")){
            conQdte += " and nvl(a.inv_nme_idn,a.nme_idn)=?";
            params.add(nmeID);
        }
            
        if(typ.equals("INCLUDE")){
        conQdte += " and  nvl(a.inv_nme_idn,a.nme_idn) in (select nme_idn from nme_dtl where mprp='BRANCH_YN' and txt='Y')";
        }else if(typ.equals("EXCLUDE")){
        conQdte += " and  nvl(a.inv_nme_idn,a.nme_idn) not in (select nme_idn from nme_dtl where mprp='BRANCH_YN' and txt='Y')";   
        }
        String dataQ="        select  a.idn dlvid,b.byr,to_char(a.dte,'dd-mm-yyyy') dte,count(*) qty ,to_char(sum(trunc(b.cts,2)),'999,990.99') cts\n" + 
        "        , to_char(sum(trunc(b.cts,2)*(nvl(b.fnl_usd,b.quot))),'9,99,999,9999,999,990.00') vlu \n" + 
        "        from mdlv a,Dlv_pkt_dtl_v b\n" + 
        "        where \n" + 
        "        a.idn=b.idn\n" + 
        "        and b.stt in ('DLV','IS') and a.typ in ('DLV') and a.stt='IS' \n" + conQdte+
        "        group by a.idn, b.byr,a.dte  order by 1";
      ArrayList outLst = db.execSqlLst("dlv", dataQ, params);
      PreparedStatement  pst = (PreparedStatement)outLst.get(0);
      ResultSet    rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            HashMap pktDtl = new HashMap();
            pktDtl.put("DLVID", util.nvl(rs.getString("dlvid")));
            pktDtl.put("BUYER", util.nvl(rs.getString("byr")));
            pktDtl.put("DATE", util.nvl(rs.getString("dte")));
            pktDtl.put("QUANTITY", util.nvl(rs.getString("qty")));
            pktDtl.put("CTS", util.nvl(rs.getString("cts")));
            pktDtl.put("VLU", util.nvl(rs.getString("vlu")));
            pktDtlList.add(pktDtl);
        }
        rs.close();
        pst.close();
        
        String ttlQ="select  'TOTAL' dlvid,'' byr,count(*) qty ,to_char(sum(trunc(b.cts,2)),'999,990.99') cts\n" + 
        "        , to_char(sum(trunc(b.cts,2)*(nvl(b.fnl_usd,b.quot))),'9,99,999,9999,999,990.00') vlu \n" + 
        "        from mdlv a,Dlv_pkt_dtl_v b\n" + 
        "        where \n" + 
        "        a.idn=b.idn\n" + 
        "        and b.stt in ('DLV','IS') and a.typ in ('DLV') and a.stt='IS'"+ conQdte;
       outLst = db.execSqlLst("ttlQ", ttlQ, params);
        pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
         while (rs.next()) {
                    HashMap pktDtl = new HashMap();
                    pktDtl.put("DLVID", util.nvl(rs.getString("dlvid")));
                    pktDtl.put("BUYER", util.nvl(rs.getString("byr")));
                    pktDtl.put("QUANTITY", util.nvl(rs.getString("qty")));
                    pktDtl.put("CTS", util.nvl(rs.getString("cts")));
                    pktDtl.put("VLU", util.nvl(rs.getString("vlu")));
                    pktDtlList.add(pktDtl);
         }
        rs.close();
        pst.close();
        
        dataQ="  select  b.byr,count(*) qty ,to_char(sum(trunc(b.cts,2)),'999,990.99') cts\n" + 
        "        , to_char(sum(trunc(b.cts,2)*(nvl(b.fnl_usd,b.quot))),'9,99,999,9999,999,990.00') vlu \n" + 
        "        from mdlv a,Dlv_pkt_dtl_v b\n" + 
        "        where \n" + 
        "        a.idn=b.idn\n" + 
        "        and b.stt in ('DLV','IS') and a.typ in ('DLV') and a.stt='IS' \n" + conQdte+
        " group by b.byr \n" + 
        " order by 1";
      outLst = db.execSqlLst("dlv", dataQ, params);
       pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            HashMap pktDtl = new HashMap();
            pktDtl.put("BUYER", util.nvl(rs.getString("byr")));
            pktDtl.put("QUANTITY", util.nvl(rs.getString("qty")));
            pktDtl.put("CTS", util.nvl(rs.getString("cts")));
            pktDtl.put("VLU", util.nvl(rs.getString("vlu")));
            byrDtlList.add(pktDtl);
        }
        rs.close();
        pst.close();
        
        ttlQ="        select  'TOTAL' byr,count(*) qty ,to_char(sum(trunc(b.cts,2)),'999,990.99') cts\n" + 
        "        , to_char(sum(trunc(b.cts,2)*(nvl(b.fnl_usd,b.quot))),'9,99,999,9999,999,990.00') vlu \n" + 
        "        from mdlv a,Dlv_pkt_dtl_v b\n" + 
        "        where \n" + 
        "        a.idn=b.idn\n" + 
        "        and b.stt in ('DLV','IS') and a.typ in ('DLV') and a.stt='IS' "+ conQdte;
      outLst = db.execSqlLst("ttlQ", ttlQ, params);
       pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);
         while (rs.next()) {
                    HashMap pktDtl = new HashMap();
                    pktDtl.put("BUYER", util.nvl(rs.getString("byr")));
                    pktDtl.put("QUANTITY", util.nvl(rs.getString("qty")));
                    pktDtl.put("CTS", util.nvl(rs.getString("cts")));
                    pktDtl.put("VLU", util.nvl(rs.getString("vlu")));
                    byrDtlList.add(pktDtl);
         }
        rs.close();
        pst.close();
        
        ArrayList byrList = ((ArrayList)session.getAttribute("branchbyrList") == null)?new ArrayList():(ArrayList)session.getAttribute("branchbyrList");
        if(byrList.size()==0){
          String sqlQury="select a.nme_idn,a.nme\n" + 
          "      from nme_v a,nme_dtl b\n" + 
          "      where \n" + 
          "      a.nme_idn = b.nme_idn and\n" + 
          "      b.mprp='BRANCH_YN' and b.txt='Y'";
          outLst = db.execSqlLst("sqlQuery", sqlQury, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
          while(rs.next()){
            ByrDao byr = new ByrDao();

            byr.setByrIdn(rs.getInt("nme_idn"));
            byr.setByrNme(rs.getString("nme"));
            byrList.add(byr);
          }
          rs.close();
          pst.close();
          session.setAttribute("branchbyrList",byrList);
        }
        udf.setByrList(byrList);
        itemHdr.add("DLVID");
        itemHdr.add("BUYER");
        itemHdr.add("DATE");
        itemHdr.add("QUANTITY");
        itemHdr.add("CTS");
        itemHdr.add("VLU");
        
        itemHdrbyr.add("BUYER");
        itemHdrbyr.add("QUANTITY");
        itemHdrbyr.add("CTS");
        itemHdrbyr.add("VLU");
        session.setAttribute("itemHdr",itemHdr);
        session.setAttribute("pktList", pktDtlList);
        session.setAttribute("itemHdrbyr",itemHdrbyr);
        session.setAttribute("byrDtlList", byrDtlList);
        
        GenericInterface genericInt = new GenericImpl();
        ArrayList prpDspBlocked = info.getPageblockList();
        ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"DAILY_VW","DAILY_VW");
        ArrayList itemHdrPrp = new ArrayList();
        ArrayList pktPrpDtlList = new ArrayList();
        itemHdrPrp.add("DLVID");
        itemHdrPrp.add("VNM");
        itemHdrPrp.add("SALEPERSON");
        itemHdrPrp.add("BYR");
        itemHdrPrp.add("COUNTRY");
            for(int i=0;i<vwprpLst.size();i++){
                String prp=util.nvl((String)vwprpLst.get(i));
                if(prpDspBlocked.contains(prp)){
                }else if(!itemHdr.contains(prp)){
                itemHdrPrp.add(prp);
                if(prp.equals("RTE")){
                     itemHdrPrp.add("SALE RTE");
                     itemHdrPrp.add("SALE_DIS");
                     itemHdrPrp.add("AMOUNT");
                 }
                    if(prp.equals("RAP_RTE")){
                           itemHdrPrp.add("RAPVLU");
                    }
                }  
            } 
        
        
        ArrayList ary = new ArrayList();
        
        String pktDtlSql =  "Select a.idn dlvid,C.Idn,to_char(b.cts,'99999999990.99') cts,C.Vnm,Nvl(C.Upr,C.Cmp) rte,C.Rap_Rte,Byr.Get_Nm(Nvl(d.Emp_Idn,0)) Sale_Name,d.Fnme Byr,nvl(b.fnl_sal, b.quot) memoQuot,decode(c.pkt_ty,'NR',to_char(((nvl(b.fnl_sal, b.quot)/c.rap_rte*100)-100),'999999990.00'),'') slback,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu, "+
            " to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country , " +
            " to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis "+
            " From Mdlv A , Dlv_Pkt_Dtl_V B,Mstk C,Mnme d "+
            " Where A.Idn = B.Idn And B.Mstk_Idn=C.Idn and d.nme_idn=a.nme_idn "+
            " and b.stt in ('DLV','IS') and a.typ in ('DLV') and a.stt='IS' \n" + conQdte+
            " Order By a.idn, C.Sk1";
        
      outLst = db.execSqlLst("pktDtl", pktDtlSql,params);
       pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap pktDtl = new HashMap();
            pktDtl.put("DLVID", util.nvl(rs.getString("dlvid")));
            pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
            pktDtl.put("SALEPERSON", util.nvl(rs.getString("sale_name")));
            pktDtl.put("BYR", util.nvl(rs.getString("byr")));
            pktDtl.put("COUNTRY", util.nvl(rs.getString("country")));
            pktDtl.put("RTE", util.nvl(rs.getString("rte")));
            pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
            pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
            pktDtl.put("SALE_DIS", util.nvl(rs.getString("slback")));
            pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));
            
            
            String pktIdn = util.nvl(rs.getString("idn"));
        
            String getPktPrp =
                " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                + " from stk_dtl a, mprp b, rep_prp c "
                + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'DAILY_VW' and a.mstk_idn = ? and a.grp=1 and c.flg <> 'N' "
                + " order by c.rnk, c.srt ";

            ary = new ArrayList();
            ary.add(pktIdn);
        ArrayList  outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, ary);
         PreparedStatement  pst1 = (PreparedStatement)outLst1.get(0);
          ResultSet   rs1 = (ResultSet)outLst1.get(1);
             while (rs1.next()) {
                String lPrp = util.nvl(rs1.getString("mprp"));
                String lVal = util.nvl(rs1.getString("val"));
                pktDtl.put(lPrp, lVal);
            }
            pktDtl.put("RTE",util.nvl(rs.getString("rte")));
            pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
            pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
            pktDtl.put("RAP_RTE",util.nvl(rs.getString("rap_rte")));
            pktDtl.put("RAPVLU",util.nvl(rs.getString("rapvlu")));
            pktPrpDtlList.add(pktDtl);
            rs1.close();
            pst1.close();
        }
            rs.close();
            pst.close();
            session.setAttribute("itemHdrPrp",itemHdrPrp);
            session.setAttribute("pktPrpDtlList", pktPrpDtlList);
        
        
        
        
        req.setAttribute("View", "Y");
    }
    public ActionForward priceReductionload(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            ReportForm udf = (ReportForm)form;
            ArrayList ary=new ArrayList();
            ArrayList seqList=new ArrayList();
            String seqQuery =" select idn seq_idn ,red_dte_frm||' - '||red_dte_to||' - SeqIdn '||idn||' - App Dte '||dte||' - Stt '||stt reduction_dte " +
                             " from price_reduction_m where trunc(dte)>=trunc(sysdate)-30 order by idn desc ";
           
         ArrayList   outLst = db.execSqlLst("seq Query", seqQuery, new ArrayList());
         PreparedStatement  pst = (PreparedStatement)outLst.get(0);
           ResultSet  rs = (ResultSet)outLst.get(1);
             while (rs.next()) {
                ary = new ArrayList();
                ary.add(util.nvl(rs.getString("seq_idn")));
                ary.add(util.nvl(rs.getString("reduction_dte")));
                seqList.add(ary);
            }
            rs.close();
           pst.close();
         
            session.setAttribute("reductionseq",seqList);
            util.updAccessLog(req,res,"Rpt Action", "load Price Reduction");
            return am.findForward("priceReduction");
        }
    }
    
    public ActionForward priceReductionfetch(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            ReportForm udf = (ReportForm)form;
            ArrayList prpDspBlocked = info.getPageblockList();
            GenericInterface genericInt = new GenericImpl();
            String rapDisFmt = util.nvl((String)info.getDmbsInfoLst().get("RAPDISFMT"));
            ArrayList pktList = new ArrayList();
            ArrayList itemHdr = new ArrayList();
            String seqId = util.nvl((String)udf.getValue("seqid"));
            String view = "";
            int sr=1;
            if(!seqId.equals("")){
                
                ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "REDUCT_VIEW","REDUCT_VIEW");
                itemHdr.add("Sr No");
                itemHdr.add("VNM");
                itemHdr.add("STATUS");
                itemHdr.add("DSP_STT");
                itemHdr.add("REVPCT");
                itemHdr.add("RTE");
                itemHdr.add("RAP_DIS");
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
                itemHdr.add(lprp);
                }}            
                ArrayList param = new ArrayList();
                String mprpStr = "";
                String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||Upper(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1')) str From Rep_Prp Rp Where rp.MDL = ? and flg ='Y' order by srt " ;
                param = new ArrayList();
                param.add("REDUCT_VIEW");
              ArrayList   outLst = db.execSqlLst("mprp ", mdlPrpsQ , param);
              PreparedStatement  pst = (PreparedStatement)outLst.get(0);
                ResultSet  rs = (ResultSet)outLst.get(1);
                while(rs.next()) {
                String val = util.nvl((String)rs.getString("str"));
                mprpStr = mprpStr +" "+val;
                }
                rs.close();
                pst.close();
                
                String rsltQ = " with " +
                " STKDTL as " +
                " ( select c.dsp_stt,c.stt,b.sk1, nvl(nvl(txt,num),val) atr , mprp, b.vnm ,b.cts,b.upr rte1, b.rap_rte , b.certimg, b.diamondimg ,c.rev_pct rev_pct1,to_char(decode(b.rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)),9990.99) dis from stk_dtl a, mstk b , price_reduction_d c" +
                " Where 1=1 " +
                " and a.mstk_idn = b.idn and b.idn = c.stk_idn  and c.seq_idn = ? " +
                " and exists ( select 1 from rep_prp rp where rp.MDL = ? and a.mprp = rp.prp) " +
                " And a.Grp = 1) " +
                " Select * from stkDtl PIVOT " +
                " ( max(atr) " +
                " for mprp in ( "+mprpStr+" ) ) order by 1,2,3 " ;
                
                param = new ArrayList();
                param.add(seqId);
                param.add("REDUCT_VIEW");
                 outLst = db.execSqlLst("search Result", rsltQ, param);
                pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("Sr No", String.valueOf(sr++));
                pktPrpMap.put("VNM", util.nvl((String)rs.getString("vnm")));
                pktPrpMap.put("STATUS", util.nvl((String)rs.getString("stt")));
                pktPrpMap.put("REVPCT", util.nvl((String)rs.getString("rev_pct1")));
                pktPrpMap.put("DSP_STT", util.nvl((String)rs.getString("dsp_stt")));
                for (int i = 0; i < vwPrpLst.size(); i++) {
                String vwPrp = (String)vwPrpLst.get(i);
                String fldName = util.pivot(vwPrp);
                String fldVal = util.nvl((String)rs.getString(fldName));
                
                pktPrpMap.put(vwPrp, fldVal);
                }
                pktPrpMap.put("RTE", util.nvl((String)rs.getString("rte1")));
                pktPrpMap.put("RAP_DIS", util.nvl((String)rs.getString("dis")));
                pktList.add(pktPrpMap);
                }
                rs.close();
                pst.close();
                view="Y"; 
            }
            req.setAttribute("view",view);
            req.setAttribute("itemHdr",itemHdr);
            udf.setPktList(pktList);
            req.setAttribute("PktList",pktList);
            udf.reset();
            util.updAccessLog(req,res,"Rpt Action", "fetch Price Reduction");
            return am.findForward("priceReduction");
        }
    }
    public ActionForward loadlot(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            ReportForm udf = (ReportForm)form;
            udf.reset();
            util.updAccessLog(req,res,"Rpt Action", "loadlot start");
            GenericInterface genericInt = new GenericImpl();
            ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "LOT_RPT_VW","LOT_RPT_VW");
            genericInt.genericPrprVw(req, res, "LOT_RPT_FLT","LOT_RPT_FLT");
            genericInt.genericPrprVw(req, res, "LOT_RPT_FLTSUMM","LOT_RPT_FLTSUMM");
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("LOT_REPORT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("LOT_REPORT");
            allPageDtl.put("LOT_REPORT",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            String mix = util.nvl((String)req.getParameter("MIX"));
            udf.setValue("MIX", mix);
            util.updAccessLog(req,res,"Rpt Action", "loadlot end");
            return am.findForward("loadlot");
        }
    }
    public ActionForward fetchlot(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            ReportForm udf = (ReportForm)form;
            util.updAccessLog(req,res,"Rpt Action", "fetchlot start");
            String lot=util.nvl((String)udf.getValue("lot")).trim();
            String mix = util.nvl((String)udf.getValue("MIX"));
            String rowlprp=util.nvl((String)udf.getValue("rowlprp")).trim();
            String summarylprp=util.nvl((String)udf.getValue("summarylprp")).trim();
            String shapebyrightside=util.nvl((String)udf.getValue("shapebyrightside")).trim();
            String commercial=util.nvl((String)udf.getValue("commercial")).trim();
            String rough_ctsfrom=util.nvl((String)udf.getValue("rough_ctsfrom")).trim();
            String rough_ctsto=util.nvl((String)udf.getValue("rough_ctsto")).trim();
            HashMap dbinfo = info.getDmbsInfoLst();
            String sh = (String)dbinfo.get("SHAPE");
            if(!mix.equals(""))
            sh = "MIX_SHAPE";
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("LOT_REPORT");
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="",lotlprp="";
            GenericInterface genericInt = new GenericImpl();
            ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "LOT_RPT_VW","LOT_RPT_VW");
            ArrayList prpDspBlocked = info.getPageblockList();
            pageList=(ArrayList)pageDtl.get("PRP");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            lotlprp=(String)pageDtlMap.get("dflt_val");
            }
            }
            if(!lot.equals("")){
                if(commercial.equals("")){
                ArrayList roughszLst=new ArrayList();; 
                HashMap roughdatattl=new HashMap();;  
                int indexSH = vWPrpList.indexOf(sh)+1;
                int indexRowlprp = vWPrpList.indexOf(rowlprp)+1;
                int indexSummarylprp = vWPrpList.indexOf(summarylprp)+1;
                int indexLOT= vWPrpList.indexOf(lotlprp)+1;
                String shPrp = util.prpsrtcolumn("prp",indexSH);
                String rowPrp = util.prpsrtcolumn("prp",indexRowlprp);
                String summaryPrp = util.prpsrtcolumn("prp",indexSummarylprp);
                String lotPrp = util.prpsrtcolumn("prp",indexLOT);
                String shSrt = util.prpsrtcolumn("srt",indexSH);
                String rowSrt = util.prpsrtcolumn("srt",indexRowlprp);
                String summarySrt = util.prpsrtcolumn("srt",indexSummarylprp);
                String lotSrt = util.prpsrtcolumn("srt",indexLOT);
                String conQ="";
                ArrayList showprpLst=new ArrayList();
                pageList=(ArrayList)pageDtl.get("SHOWPRP");
                if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                fld_ttl=(String)pageDtlMap.get("fld_ttl");
                dflt_val=(String)pageDtlMap.get("dflt_val");
                fld_typ=(String)pageDtlMap.get("fld_typ");
                lov_qry=(String)pageDtlMap.get("lov_qry");
                int indexprp=0;
                String showgtPrp="";
                if(lov_qry.equals("PRP")){
                indexprp = vWPrpList.indexOf(fld_typ)+1;   
                if(!prpDspBlocked.contains(fld_typ)){
                if(indexprp!=0){
                showgtPrp = util.prpsrtcolumn("prp",indexprp);
                if(dflt_val.equals("AVG")){
                if(fld_typ.equals("AGE"))
                conQ+=",round(sum("+showgtPrp+")/sum(qty)) "+fld_ttl;
                else if(fld_typ.equals("HIT"))
                conQ+=",round(sum("+showgtPrp+")/sum(qty)) "+fld_ttl;
                else if(fld_typ.equals("RTE"))
                conQ+=",trunc(sum(nvl("+showgtPrp+",quot)*trunc(cts,2))/sum(trunc(cts, 2)),0) "+fld_ttl;  
                else{
                val_cond=(String)pageDtlMap.get("val_cond");
                if(val_cond.equals("")){
                if(fld_typ.equals("MFG_PRI"))
                conQ+=",trunc(sum(nvl("+showgtPrp+",quot)*trunc(cts,2))/sum(trunc(cts, 2)),0) "+fld_ttl; 
                else
                conQ+=",trunc(sum("+showgtPrp+"*trunc(cts,2))/sum(trunc(cts, 2)),0) "+fld_ttl; 
                }else{
                int indexprpcts = vWPrpList.indexOf(val_cond)+1; 
                String showgtPrprw = util.prpsrtcolumn("prp",indexprpcts);
                conQ+=",round(sum("+showgtPrp+"*trunc("+showgtPrprw+",2))/sum(trunc("+showgtPrprw+", 2))) "+fld_ttl; 
                }
                }
                showprpLst.add(fld_ttl);   
                }else{
                if(!prpDspBlocked.contains(fld_typ)){
                indexprp = vWPrpList.indexOf(fld_typ)+1; 
                if(indexprp!=0){
                showgtPrp = util.prpsrtcolumn("prp",indexprp);
                conQ+=",trunc(((sum(nvl("+showgtPrp+",quot)*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2)  "+fld_ttl;  
                    showprpLst.add(fld_ttl);   
                }
                }
                }
                }
                }
                }else if(lov_qry.equals("VLU")){
                    if(!prpDspBlocked.contains(fld_typ)){
                    indexprp = vWPrpList.indexOf(fld_typ)+1; 
                    if(indexprp!=0){
                    val_cond=(String)pageDtlMap.get("val_cond");
                    showgtPrp = util.prpsrtcolumn("prp",indexprp);
                    if(val_cond.equals("")){
                    conQ+=",round(sum(trunc(cts,2)*nvl("+showgtPrp+",quot))) "+fld_ttl; 
                    }else{
                        int indexprpcts = vWPrpList.indexOf(val_cond)+1; 
                        String showgtPrprw = util.prpsrtcolumn("prp",indexprpcts);
                        conQ+=",round(sum(trunc("+showgtPrprw+",2)*"+showgtPrp+")) "+fld_ttl;  
                    }
                    showprpLst.add(fld_ttl);      
                    }else{
                        conQ+=",round(sum(trunc(cts,2)*nvl("+fld_typ+",quot))) "+fld_ttl; 
                        showprpLst.add(fld_ttl);   
                    }
                    }
                }else if(lov_qry.equals("SUM")){
                    indexprp = vWPrpList.indexOf(fld_typ)+1; 
                    if(indexprp!=0){
                    showgtPrp = util.prpsrtcolumn("prp",indexprp);
                    conQ+=",to_char(sum(nvl("+showgtPrp+",0)),'99999990.00') "+fld_ttl; 
                    showprpLst.add(fld_ttl); 
                    }else{
                        conQ+=",to_char(sum(nvl("+fld_typ+",0)),'99999990.00') "+fld_ttl; 
                        showprpLst.add(fld_ttl);
                    }
                }else if(lov_qry.equals("AGEVLUAVG")){
                    indexprp = vWPrpList.indexOf(fld_typ)+1; 
                    if(indexprp!=0){
                    showgtPrp = util.prpsrtcolumn("prp",indexprp);
                    conQ+=",round(sum((trunc(cts,2)*quot)*nvl("+showgtPrp+",0))/sum(trunc(cts,2)*quot)) "+fld_ttl; 
                    showprpLst.add(fld_ttl); 
                   }
                 }else if(lov_qry.equals("DIFF")){
                     indexprp = vWPrpList.indexOf(fld_typ)+1; 
                     int indexprprte = vWPrpList.indexOf((String)pageDtlMap.get("val_cond"))+1;
                     if(indexprp!=0 && indexprprte !=0){
                     showgtPrp = util.prpsrtcolumn("prp",indexprp);
                     String showgtPrprte = util.prpsrtcolumn("prp",indexprprte);
                     conQ+=",trunc(100 - (trunc(sum(nvl("+showgtPrprte+",quot)*trunc(cts,2))/sum(trunc(cts, 2)),0)*100/trunc(sum(nvl("+showgtPrp+",quot)*trunc(cts,2))/sum(trunc(cts, 2)),0)), 2) "+fld_ttl; 
                     showprpLst.add(fld_ttl); 
                     }
                 }else if(lov_qry.equals("DYS")){
                     conQ+=",ROUND(sum(prp_090)/sum(prp_089)) "+fld_ttl; 
                     showprpLst.add(fld_ttl); 
                 }
                }
                }
                
                ArrayList ary=new ArrayList();
                ary.add(lotlprp);
                ary.add(lot);
                ary.add("LOT_RPT_VW");
                String lotPRCQ="DP_LOT_ANALYSIS(pPrp => ?, pVal => ?, pMdl => ?)";
                int ct = db.execCall("memoPRCQ", lotPRCQ,ary);
                
                if(!rough_ctsfrom.equals("") || !rough_ctsto.equals("")){
                   if(!rough_ctsfrom.equals("") && rough_ctsto.equals("")) 
                   rough_ctsto=rough_ctsfrom;
                    if(rough_ctsfrom.equals("") && !rough_ctsto.equals("")) 
                    rough_ctsfrom=rough_ctsto;
                    int indexRoughCts= vWPrpList.indexOf("ROUGH_CTS")+1;
                    if(indexRoughCts!=0){
                    String roughCtsPrp = util.prpsrtcolumn("prp",indexRoughCts);
                    db.execUpd("delete", "delete from gt_srch_rslt where "+roughCtsPrp+" not between "+rough_ctsfrom+" and "+rough_ctsto, new ArrayList());
                    }
                }
                
                HashMap dataDtl=new HashMap();
                HashMap datattl=new HashMap();
                HashMap data=new HashMap();
                ArrayList keytable=new ArrayList();
                ArrayList sttLst=new ArrayList();
                ArrayList conatinsdata=new ArrayList();
                int showprpLstsz=showprpLst.size();
                
                String memoQ="select dsp_stt,"+shPrp+" sh,"+shSrt+" sh_so,"+rowPrp+" rowcol,"+rowSrt+" row_so,\n" + 
                "count(*) qty,to_char(sum(cts),'99999990.00') cts " +conQ+ 
                " from gt_srch_rslt gt\n" + 
                "where nvl(cts,0) > 0 \n" + 
                "group by dsp_stt,"+shPrp+" ,"+shSrt+","+rowPrp+" ,"+rowSrt+"\n" + 
                "Order By dsp_stt desc,Sh_So,row_so";
                  ArrayList   outLst = db.execSqlLst("memo by Shape", memoQ, new ArrayList());
                  PreparedStatement  pst = (PreparedStatement)outLst.get(0);
                    ResultSet  rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                    data=new HashMap();   
                    String dsp_stt=util.nvl((String)rs.getString("dsp_stt"));
                    String shapesrt=util.nvl((String)rs.getString("sh_so"));
                    String key=dsp_stt+"_"+shapesrt;
                    if(!keytable.contains(key)){
                        keytable.add(key);
                        conatinsdata=new ArrayList();       
                    }
                    if(!sttLst.contains(dsp_stt)){
                        sttLst.add(dsp_stt);      
                    }
                    data.put("STT",dsp_stt);
                    data.put("SH",util.nvl((String)rs.getString("sh")));
                    data.put("ROW",util.nvl((String)rs.getString("rowcol")));
                    data.put("QTY",util.nvl((String)rs.getString("qty")));
                    data.put("CTS",util.nvl((String)rs.getString("cts")));
                    for(int s=0;s<showprpLstsz;s++){
                    String showprp=(String)showprpLst.get(s);
                    data.put(showprp, util.nvl(rs.getString(showprp)));        
                    }
                    conatinsdata.add(data);
                    dataDtl.put(key,conatinsdata);       
                }
                    rs.close();
                pst.close();
                String shapeQ="select dsp_stt,"+shPrp+" sh,"+shSrt+" sh_so,\n" + 
                "count(*) qty,to_char(sum(cts),'99999990.00') cts " +conQ+ 
                " from gt_srch_rslt gt\n" + 
                "where nvl(cts,0) > 0 \n" + 
                "group by dsp_stt,"+shPrp+" ,"+shSrt+"\n" + 
                "Order By dsp_stt,Sh_So";
                    outLst = db.execSqlLst("Sum by shape", shapeQ, new ArrayList());
                    pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                while(rs.next()){ 
                    data=new HashMap();   
                    String dsp_stt=util.nvl((String)rs.getString("dsp_stt"));
                    String shapesrt=util.nvl((String)rs.getString("sh_so"));
                    String key=dsp_stt+"_"+shapesrt;
                    data.put("STT",dsp_stt);
                    data.put("SH",util.nvl((String)rs.getString("sh")));
                    data.put("QTY",util.nvl((String)rs.getString("qty")));
                    data.put("CTS",util.nvl((String)rs.getString("cts")));
                    for(int s=0;s<showprpLstsz;s++){
                    String showprp=(String)showprpLst.get(s);
                    data.put(showprp, util.nvl(rs.getString(showprp)));        
                    }
                    datattl.put(key,data);  
                }
                    rs.close();
                    pst.close();
                String deptQ="select dsp_stt,\n" + 
                "count(*) qty,to_char(sum(cts),'99999990.00') cts "+conQ+
                " from gt_srch_rslt gt\n" + 
                "where nvl(cts,0) > 0 \n" + 
                "group by prp_001,dsp_stt\n" + 
                "Order By dsp_stt";
                  outLst = db.execSqlLst("Sum by Dept", deptQ, new ArrayList());
                  pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                    data=new HashMap();
                    String dsp_stt=util.nvl((String)rs.getString("dsp_stt"));
                    data.put("STT",dsp_stt);
                    data.put("QTY",util.nvl((String)rs.getString("qty")));
                    data.put("CTS",util.nvl((String)rs.getString("cts")));
                    for(int s=0;s<showprpLstsz;s++){
                    String showprp=(String)showprpLst.get(s);
                    data.put(showprp, util.nvl(rs.getString(showprp)));        
                    }
                    dataDtl.put(dsp_stt,data);    
                }
                    rs.close();
                    pst.close();
                    
                    String shpdeptttQ="select dsp_stt,\n" + 
                    "count(*) qty,to_char(sum(cts),'99999990.00') cts " +conQ+ 
                    " from gt_srch_rslt gt\n" + 
                    "where nvl(cts,0) > 0 and "+shPrp+" not in('ROUND','RD')\n" + 
                    "group by dsp_stt\n" + 
                    "Order By dsp_stt";
                  outLst = db.execSqlLst("Sum by Shape Dept", shpdeptttQ, new ArrayList());
                  pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                    while(rs.next()){
                        data=new HashMap();
                        String dsp_stt=util.nvl((String)rs.getString("dsp_stt"));
                        data.put("STT",dsp_stt);
                        data.put("QTY",util.nvl((String)rs.getString("qty")));
                        data.put("CTS",util.nvl((String)rs.getString("cts")));
                            for(int s=0;s<showprpLstsz;s++){
                            String showprp=(String)showprpLst.get(s);
                            data.put(showprp, util.nvl(rs.getString(showprp)));        
                            }
                        dataDtl.put(dsp_stt+"_TTL",data);    
                    }
                        rs.close();
                        pst.close();
                String grandQ="select count(*) qty,to_char(sum(cts),'99999990.00') cts " +conQ+ 
                " from gt_srch_rslt gt\n" + 
                "where nvl(cts,0) > 0";
                  outLst = db.execSqlLst("Grand Totals", grandQ, new ArrayList());
                  pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                while(rs.next()){ 
                    data=new HashMap();
                    data.put("QTY",util.nvl((String)rs.getString("qty")));
                    data.put("CTS",util.nvl((String)rs.getString("cts")));
                        for(int s=0;s<showprpLstsz;s++){
                        String showprp=(String)showprpLst.get(s);
                        data.put(showprp, util.nvl(rs.getString(showprp)));        
                        }
                    data.put("LOT",lot);   
                    data.put("ROW",rowlprp);
                    data.put("SUMMARY_LPRP",summarylprp);
                    dataDtl.put("GRANDTTL",data);  
                }
                    rs.close();
                    pst.close();
                    
                //new
                shapeQ="select "+summaryPrp+" summary,"+summarySrt+" summary_so,\n" + 
                "count(*) qty,to_char(sum(cts),'99999990.00') cts " +conQ+ 
                " from gt_srch_rslt gt\n" + 
                "where nvl(cts,0) > 0 \n" + 
                "group by "+summaryPrp+" ,"+summarySrt+"\n" + 
                "Order By summary_so";
                  outLst = db.execSqlLst("Sum by shape", shapeQ, new ArrayList());
                  pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                while(rs.next()){ 
                    data=new HashMap();   
                    String summsrt=util.nvl((String)rs.getString("summary_so"));
                    String key=summsrt;
                    data.put("SUMMARY",util.nvl((String)rs.getString("summary")));
                    data.put("QTY",util.nvl((String)rs.getString("qty")));
                    data.put("CTS",util.nvl((String)rs.getString("cts")));
                        for(int s=0;s<showprpLstsz;s++){
                        String showprp=(String)showprpLst.get(s);
                        data.put(showprp, util.nvl(rs.getString(showprp)));        
                        }
                    datattl.put(key,data);  
                }
                    rs.close();   
                    pst.close();
                    
                String pky_typQ="select pkt_ty,\n" + 
                "count(*) qty,to_char(sum(cts),'99999990.00') cts "+ conQ+ 
                " from gt_srch_rslt gt\n" + 
                "where nvl(cts,0) > 0 \n" + 
                "group by pkt_ty\n" + 
                "union\n" + 
                "select 'TTL' pkt_ty,\n" + 
                "count(*) qty,to_char(sum(cts),'99999990.00') cts "+ conQ+ 
                " from gt_srch_rslt gt\n" + 
                "where nvl(cts,0) > 0 \n" + 
                "Order By 1";
                  outLst = db.execSqlLst("Sum by pky_typ", pky_typQ, new ArrayList());
                  pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                while(rs.next()){ 
                    data=new HashMap();   
                    String key=util.nvl((String)rs.getString("pkt_ty"));
                    data.put("QTY",util.nvl((String)rs.getString("qty")));
                    data.put("CTS",util.nvl((String)rs.getString("cts")));
                        for(int s=0;s<showprpLstsz;s++){
                        String showprp=(String)showprpLst.get(s);
                        data.put(showprp, util.nvl(rs.getString(showprp)));        
                        }
                    datattl.put(key,data);  
                }
                rs.close();  
                pst.close();
                
                pageList=(ArrayList)pageDtl.get("ROUGH_DATA");
                if(pageList!=null && pageList.size() >0){
                String mfg_priPrp = util.prpsrtcolumn("prp",vWPrpList.indexOf("MFG_PRI")+1);
                for(int j=0;j<pageList.size();j++){
                double polVlu = 0;
                double rghCts = 0;
                double rghVlu = 0;
                    String roughQ="                       with rgh as (        \n" + 
                    "                           select col02 lotno, col03 rwt, get_rsz(col31) rsz, m.cts, nvl(m.fquot,m.quot) rte,m.stk_idn\n" + 
                    "                           from rgh_upload_ora r, gt_srch_rslt m, stk_dtl lot \n" + 
                    "                           where col02 like '"+lot+"%'\n" + 
                    "                           and m.stk_idn = lot.mstk_idn and lot.grp = 1 and lot.mprp = 'LOT_NO' and lot.txt = r.col02 \n" + 
                    "                           union\n" + 
                    "                           select 'NA' lotno,null rwt,'NA' rsz,a.cts, nvl(a.fquot,a.quot) rte,a.stk_idn from gt_srch_Rslt a where  a.stk_idn not in(\n" + 
                    "                           select m.idn\n" + 
                    "                           from rgh_upload_ora r, mstk m, stk_dtl lot\n" + 
                    "                           where col02 like '"+lot+"%'\n" + 
                    "                           and m.idn = lot.mstk_idn and lot.grp = 1 and lot.mprp = 'LOT_NO' and lot.txt = r.col02\n" + 
                    "                           and m.pkt_ty not in('DEL'))\n" + 
                    "                        )  \n" + 
                    "                        select rsz,to_char(sum(rwt),'99999990.00') wt, to_char(sum(cts),'99999990.00') polCts, round(sum(trunc(cts,2)*rte)) polVlu\n" + 
                    "                        from rgh  \n" + 
                    "                        group by rsz \n" + 
                    "                        order by 1";
//                String roughQ="with rgh as (\n" + 
//                "   select col02 lotno, col03 rwt, get_rsz(col31) rsz, m.cts\n" + 
//                "       , trunc(nvl(js.fnl_sal, js.quot)/greatest(1, nvl(s.fnl_exh_rte, s.exh_rte)), 2) rte\n" + 
//                "       from rgh_upload_ora r, mstk m, stk_dtl lot, jansal js, msal s\n" + 
//                "   where col02 like '"+lot+"%'\n" + 
//                "       and m.idn = lot.mstk_idn and lot.grp = 1 and lot.mprp = 'LOT_NO' and lot.txt = r.col02\n" + 
//                "       and m.stt in ('MKSL', 'MKSD','BRC_MKSD')\n" + 
//                "       and m.idn = js.mstk_idn and js.stt in ('SL', 'DLV') and m.pkt_ty not in('DEL')\n" + 
//                "       and s.idn = js.idn \n" + 
//                "   UNION        \n" + 
//                "   select col02 lotno, col03 rwt, get_rsz(col31) rsz, m.cts, nvl(upr, cmp) rte \n" + 
//                "       from rgh_upload_ora r, mstk m, stk_dtl lot\n" + 
//                "   where col02 like '"+lot+"%'\n" + 
//                "       and m.idn = lot.mstk_idn and lot.grp = 1 and lot.mprp = 'LOT_NO' and lot.txt = r.col02\n" + 
//                "       and m.stt not in ('MKSL', 'MKSD','BRC_MKSD') and m.pkt_ty not in('DEL')\n" + 
//                ") \n" + 
//                "select rsz, to_char(sum(rwt),'99999990.00') wt, to_char(sum(cts),'99999990.00') polCts, to_char(sum(cts*rte),'99999990.00') polVlu\n" + 
//                "   from rgh \n" + 
//                "group by rsz\n" + 
//                "order by 1";
                  outLst = db.execSqlLst("roughQ", roughQ, new ArrayList());
                  pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                while(rs.next()){ 
                String sz=util.nvl((String)rs.getString("rsz"));
                roughszLst.add(sz);
                roughdatattl.put(sz+"_POLVAL",util.nvl((String)rs.getString("polVlu")));  
                roughdatattl.put(sz+"_ROUGHCTS",util.nvl((String)rs.getString("wt")));
                    polVlu = polVlu+Double.parseDouble(util.nvl((String)rs.getString("polVlu"),"0"));
                    rghCts = rghCts+Double.parseDouble(util.nvl((String)rs.getString("wt"),"0"));
                }
                    rs.close();
                    pst.close();
                  roughdatattl.put("TOTAL_POLVAL",String.valueOf(util.roundToDecimals(polVlu,2)));  
                  roughdatattl.put("TOTAL_ROUGHCTS",String.valueOf(util.roundToDecimals(rghCts,2)));    

                  String roughDQ="with rgh_nf as (\n" + 
                  "select get_rsz(col03) rsz, col03 wt, col04 rte\n" + 
                  "--get_rsz(col03) rsz, sum(col03*col04) vlu\n" + 
                  "from rgh_upload_ora r \n" + 
                  "where r.col02 like '"+lot+"%' and col03 is not null\n" + 
                  "and not exists (select 1 from stk_dtl sd, gt_srch_rslt gt \n" + 
                  "   where gt.stk_idn = sd.mstk_idn and sd.grp = 1 and sd.mprp = 'LOT_NO' and sd.txt = r.col02)\n" + 
                  ") \n" + 
                  "select rsz, sum(wt*rte) vlu\n" + 
                  "from rgh_nf\n" + 
                  "group by rsz";
                  outLst = db.execSqlLst("roughQ", roughDQ, new ArrayList());
                  pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                  while(rs.next()){
                      String sz=util.nvl((String)rs.getString("rsz"));
                      roughdatattl.put(sz+"_ROUGHVLU",util.nvl((String)rs.getString("vlu")));
                      if(!roughszLst.contains(sz))
                      roughszLst.add(sz);
                      rghVlu = rghVlu+Double.parseDouble(util.nvl((String)rs.getString("vlu"),"0"));
                  }
                  rs.close();
                    pst.close();
                  roughdatattl.put("TOTAL_ROUGHVLU",String.valueOf(util.roundToDecimals(rghVlu,2)));

//                String roughQ="select cmnt1 sz,round(sum(trunc(cts,2)*nvl("+mfg_priPrp+",quot))) vlu\n" + 
//                "from gt_srch_rslt where cmnt1 is not null and prp_020 is not null and prp_021 is not null\n" + 
//                "group by cmnt1\n" + 
//                "union\n" + 
//                "select 'TOTAL' sz,round(sum(cts*nvl("+mfg_priPrp+",quot)))\n" + 
//                "from gt_srch_rslt";
//                rs = db.execSql("roughQ", roughQ, new ArrayList());
//                while(rs.next()){ 
//                    String sz=util.nvl((String)rs.getString("sz"));
////                    if(!roughszLst.contains(sz) && !sz.equals("TOTAL"))
////                    roughszLst.add(sz); 
//                    roughdatattl.put(sz+"_POLVAL",util.nvl((String)rs.getString("vlu")));  
//                }
//                rs.close();  
//                
//                roughQ="select WT_MIN sz,to_char(sum(nvl(wt_max,0)),'99999990.00') roughwt from gt_rap group by WT_MIN\n" + 
//                "union\n" + 
//                "select 'TOTAL' sz,to_char(sum(nvl(wt_max,0)),'99999990.00') roughwt from gt_rap\n";
//                rs = db.execSql("roughQ", roughQ, new ArrayList());
//                while(rs.next()){ 
//                    String sz=util.nvl((String)rs.getString("sz"));
//                    if(!roughszLst.contains(sz) && !sz.equals("TOTAL"))
//                    roughszLst.add(sz); 
//                    roughdatattl.put(sz+"_ROUGHCTS",util.nvl((String)rs.getString("roughwt"))); 
//                }
//                rs.close();  
//                    
//                    roughQ="select WT_MIN sz,to_char(sum(nvl(wt_max,0)),'99999990.00') roughwt,round(sum(trunc(wt_max,2)*PRICE)) rough_pri_vlu from gt_rap where COLOUR='P' group by WT_MIN\n" + 
//                    "union\n" + 
//                    "select 'TOTAL' sz,to_char(sum(nvl(wt_max,0)),'99999990.00') roughwt,round(sum(trunc(wt_max,2)*PRICE)) rough_pri_vlu from gt_rap where  COLOUR='P'\n";
//                    rs = db.execSql("roughQ", roughQ, new ArrayList());
//                    while(rs.next()){ 
//                        String sz=util.nvl((String)rs.getString("sz"));
//                        roughdatattl.put(sz+"_ROUGHVLU",util.nvl((String)rs.getString("rough_pri_vlu"))); 
//                    }
//                    rs.close();
                Collections.sort(roughszLst);
                dataDtl.put("roughszLst",roughszLst);
                dataDtl.put("roughdatattl",roughdatattl);
                }
                }
                
                
                session.setAttribute("dataDtl", dataDtl);
                session.setAttribute("datattl", datattl);
                session.setAttribute("keytable", keytable);
                session.setAttribute("sttLst", sttLst);
                req.setAttribute("view", "Y");
                req.setAttribute("shapebyrightside", shapebyrightside);
                }else{
                    String szval = (String)dbinfo.get("SIZE");
                   
                    String rowlprpComm="CO_PU_GRP";
                    int indexcopulprp = vWPrpList.indexOf(rowlprpComm)+1;
                    String rowprp = util.prpsrtcolumn("prp",indexcopulprp);
                    int indexszlprp = vWPrpList.indexOf(szval)+1;
                    String colprp = util.prpsrtcolumn("prp",indexszlprp);
                    HashMap dataDtl=new HashMap();
                    ArrayList ary=new ArrayList();
                    ArrayList rowGtLst=new ArrayList();
                    ArrayList colGtLst=new ArrayList();
                    ary.add(lotlprp);
                    ary.add(lot);
                    ary.add("LOT_RPT_VW");
                    String lotPRCQ="DP_LOT_ANALYSIS(pPrp => ?, pVal => ?, pMdl => ?)";
                    int ct = db.execCall("memoPRCQ", lotPRCQ,ary);
                    
                    if(!rough_ctsfrom.equals("") || !rough_ctsto.equals("")){
                       if(!rough_ctsfrom.equals("") && rough_ctsto.equals("")) 
                       rough_ctsto=rough_ctsfrom;
                        if(rough_ctsfrom.equals("") && !rough_ctsto.equals("")) 
                        rough_ctsfrom=rough_ctsto;
                        db.execUpd("delete", "delete from gt_srch_rslt gt \n" + 
                        "where not exists (select 1 from stk_dtl st where gt.stk_idn=st.mstk_idn and st.mprp='ROUGH_CTS' and st.grp=1 and st.num between "+rough_ctsfrom+" and "+rough_ctsto +")", new ArrayList());
                    }
                    
                    String dtlQ="select count(*) qty,to_char(sum(a.cts),'99999990.00') cts,round(sum(trunc(a.cts,2)*a.quot)) vlu,a."+rowprp+",nvl(b.grp,'NA') grp\n" + 
                    "from gt_srch_rslt a,prp b\n" + 
                    "where\n" + 
                    "a."+colprp+"=b.val and b.mprp='"+szval+"'\n" + 
                    "group by a."+rowprp+",nvl(b.grp,'NA')";
                 ArrayList  outLst = db.execSqlLst("memo by Shape", dtlQ, new ArrayList());
               PreparedStatement   pst = (PreparedStatement)outLst.get(0);
                ResultSet  rs = (ResultSet)outLst.get(1);
                    while(rs.next()){
                        String rowval=util.nvl(rs.getString(rowprp));
                        String colval=util.nvl(rs.getString("grp"));
                        dataDtl.put(rowval+"_"+colval+"_QTY", util.nvl(rs.getString("qty")));
                        dataDtl.put(rowval+"_"+colval+"_CTS", util.nvl(rs.getString("cts")));
                        dataDtl.put(rowval+"_"+colval+"_VLU", util.nvl(rs.getString("vlu")));
                    }
                    rs.close();
                    pst.close();
                    
                    String colttlQ="select count(*) qty,to_char(sum(a.cts),'99999990.00') cts,round(sum(trunc(a.cts,2)*a.quot)) vlu,nvl(b.grp,'NA') grp\n" + 
                    "from gt_srch_rslt a,prp b\n" + 
                    "where\n" + 
                    "a."+colprp+"=b.val and b.mprp='"+szval+"'\n" + 
                    "group by nvl(b.grp,'NA')";
                    outLst = db.execSqlLst("memo by Shape", colttlQ, new ArrayList());
                     pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                    while(rs.next()){
                        String colval=util.nvl(rs.getString("grp"));
                        dataDtl.put(colval+"_QTY", util.nvl(rs.getString("qty")));
                        dataDtl.put(colval+"_CTS", util.nvl(rs.getString("cts")));
                        dataDtl.put(colval+"_VLU", util.nvl(rs.getString("vlu")));
                        if(!colGtLst.contains(colval))
                        colGtLst.add(colval);
                    }
                    rs.close();
                    pst.close();
                    
                    String rowttlQ="select count(*) qty,to_char(sum(a.cts),'99999990.00') cts,round(sum(trunc(a.cts,2)*a.quot)) vlu,a."+rowprp+"\n" + 
                    "from gt_srch_rslt a\n" + 
                    "group by a."+rowprp;
                  outLst = db.execSqlLst("memo by Shape", rowttlQ, new ArrayList());
                   pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                    while(rs.next()){
                        String rowval=util.nvl(rs.getString(rowprp));
                        dataDtl.put(rowval+"_QTY", util.nvl(rs.getString("qty")));
                        dataDtl.put(rowval+"_CTS", util.nvl(rs.getString("cts")));
                        dataDtl.put(rowval+"_VLU", util.nvl(rs.getString("vlu")));
                        if(!rowGtLst.contains(rowval))
                        rowGtLst.add(rowval);
                    }
                    rs.close();
                    pst.close();
                    
                    String ttlQ="select count(*) qty,to_char(sum(a.cts),'99999990.00') cts,round(sum(trunc(a.cts,2)*a.quot)) vlu\n" + 
                    "from gt_srch_rslt a";
                  outLst = db.execSqlLst("memo by Shape", ttlQ, new ArrayList());
                   pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                    while(rs.next()){
                        dataDtl.put("TTL_QTY", util.nvl(rs.getString("qty")));
                        dataDtl.put("TTL_CTS", util.nvl(rs.getString("cts")));
                        dataDtl.put("TTL_VLU", util.nvl(rs.getString("vlu")));
                    }
                    rs.close();
                    pst.close();
                    req.setAttribute("dataDtl", dataDtl);
                    req.setAttribute("rowGtLst", rowGtLst);
                    req.setAttribute("colGtLst", colGtLst);
                    req.setAttribute("rowlprpComm", rowlprpComm);
                    req.setAttribute("collprpComm", szval);
                    req.setAttribute("commercial", "Y");
                }
            }else{
                req.setAttribute("msg", "Please Enter Lot");
            }
            udf.reset();
            udf.setValue("MIX", mix);
            util.updAccessLog(req,res,"Rpt Action", "fetchlot end");
            return am.findForward("loadlot");
        }
    }
    
    public ActionForward LOTPKTDTL(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Report Action", "LOTPKTDTL start");
            GenericInterface genericInt = new GenericImpl();
        ArrayList pktList = new ArrayList();
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "LOT_RPT_VW","LOT_RPT_VW");
        int indexLB = vwPrpLst.indexOf("LOT_NO")+1;      
        String lbPrp = "";
        if(indexLB>0)
        lbPrp="to_number(substr("+util.prpsrtcolumn("prp",indexLB)+", instr("+util.prpsrtcolumn("prp",indexLB)+", '-')+1,length("+util.prpsrtcolumn("prp",indexLB)+"))) lotsrt,";
        else
        lbPrp="sk1 , cts";
        String dsp_stt=util.nvl((String)req.getParameter("dsp_stt")).trim();
        String summaryLprp=util.nvl((String)req.getParameter("summaryLprp")).trim();
        String summaryLprpval=util.nvl((String)req.getParameter("summaryLprpval")).trim();
        ArrayList itemHdr = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        itemHdr.add("Sr");
        itemHdr.add("VNM");
        
        String conQ="";
        if(!dsp_stt.equals("ALL") && !dsp_stt.equals("")){
            conQ+=" and dsp_stt='"+dsp_stt+"'";   
        }
        if(!summaryLprp.equals("")){
            int indexSummarylprp = vwPrpLst.indexOf(summaryLprp)+1;
            String summaryPrp = util.prpsrtcolumn("prp",indexSummarylprp);
            conQ+=" and "+summaryPrp+"='"+summaryLprpval+"'";  
        }
        String  srchQ =  " select "+lbPrp+"sk1,stk_idn ,trunc(cts,2) cts, pkt_ty,  vnm, pkt_dte, stt ,dsp_stt,round(quot) quot, qty , rmk,prte,mrte,round(cts*quot) amt ";
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
            if(lprp.equals("RTE")){
                itemHdr.add("AMT");
            }
        }}
        itemHdr.add("STATUS");
        
        int sr=1;
        String rsltQ = srchQ + " from gt_srch_rslt where cts <> 0 "+conQ+" order by 1,2,3";
        
        ArrayList  outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
        PreparedStatement   pst = (PreparedStatement)outLst.get(0);
       ResultSet   rs = (ResultSet)outLst.get(1);
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
                pktPrpMap.put("AMT",util.nvl(rs.getString("amt")));
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                             if(prp.equals("RTE")){
                                 val = util.nvl(rs.getString("quot")) ;
                             }
                             if(prp.equals("CRTWT")){
                                 val = util.nvl(rs.getString("cts")) ;
                             }
                        pktPrpMap.put(prp, val);
                         }
                              
                    pktList.add(pktPrpMap);
                }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("pktList", pktList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Report Action", "LOTPKTDTL end");
        return am.findForward("pktDtl");
        }
    }
    
    public ActionForward loadsaletermswise(ActionMapping am, ActionForm form, HttpServletRequest req,
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
                ReportForm udf = (ReportForm)form;
                udf.reset();
                util.saleperson();
                util.updAccessLog(req,res,"Rpt Action", "loadsaletermswise start");
                util.updAccessLog(req,res,"Rpt Action", "loadsaletermswise end");
                return am.findForward("loadsaletermswise");
            }
        }

    
    public ActionForward fetchsaletermswise(ActionMapping am, ActionForm form, HttpServletRequest req,
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
            ReportForm udf = (ReportForm)form;
            util.updAccessLog(req,res,"Rpt Action", "fetchsaletermswise start");
            datasaletermswise(req,res,udf);
            util.updAccessLog(req,res,"Rpt Action", "fetchsaletermswise end");
            return am.findForward("loadsaletermswise");
        }
    }
    
    public ActionForward fetchsaletermswisebyr(ActionMapping am, ActionForm form, HttpServletRequest req,
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
                ReportForm udf = (ReportForm)form;
                util.updAccessLog(req,res,"Rpt Action", "fetchsaletermswisebyr start");
                String terms=util.nvl((String)req.getParameter("terms"));
                String grp=util.nvl((String)req.getParameter("grp"));
                String conQ="";
                ArrayList ary = new ArrayList();
                ArrayList finaldatatLst = new ArrayList();
                ArrayList datatLst = new ArrayList();
                ArrayList itemHdr = new ArrayList();
                HashMap dataMap = new HashMap();
                HashMap data = new HashMap();
                HashMap empdataMap = new HashMap();
                HashMap byrdataMap = new HashMap();
                
                if(!terms.equals("")){
                    conQ+=" and gt.cert_lab=? \n";
                    ary.add(terms);
                }
                
                if(!grp.equals("")){
                    conQ+=" and gt.grp=? \n";
                    ary.add(grp);
                }
                        
                String sqlQ="select sum(qty) qty, to_char(sum(cts),'99999990.00') cts,trunc(sum(cts*sl_rte), 2) sl,emp,byr, cert_lab term,grp\n" + 
                "from gt_srch_rslt gt, mtrms t\n" + 
                "where gt.cert_lab = t.term\n" +conQ +
                "group by emp,byr,cert_lab,grp\n" + 
                "order by emp,byr,cert_lab,grp";
              ArrayList  outLst = db.execSqlLst("byr", sqlQ,  ary);
              PreparedStatement   pst = (PreparedStatement)outLst.get(0);
              ResultSet   rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                    dataMap = new HashMap();
                    dataMap.put("EMP", util.nvl(rs.getString("emp")));
                    dataMap.put("BYR", util.nvl(rs.getString("byr")));
                    dataMap.put("TERMS", util.nvl(rs.getString("term")));
                    dataMap.put("CTS", util.nvl(rs.getString("cts")));
                    dataMap.put("QTY", util.nvl(rs.getString("qty")));
                    dataMap.put("AMT", util.nvl(rs.getString("sl")));
                    dataMap.put("GRP", util.nvl(rs.getString("grp")));
                    datatLst.add(dataMap);
                }
                rs.close();
                pst.close();
                
                sqlQ="select sum(qty) qty , to_char(sum(cts),'99999990.00') cts,trunc(sum(cts*sl_rte), 2) sl,emp\n" + 
                           "from gt_srch_rslt gt, mtrms t\n" + 
                           "where gt.cert_lab = t.term\n" +conQ +
                           "group by emp\n" + 
                           "order by emp";
                outLst = db.execSqlLst("byr", sqlQ,  ary);
                 pst = (PreparedStatement)outLst.get(0);
                 rs = (ResultSet)outLst.get(1);
                 while (rs.next()) {
                     data = new HashMap();
                     String emp=util.nvl(rs.getString("emp"));
                     data.put("EMP", emp);
                     data.put("CTS", util.nvl(rs.getString("cts")));
                     data.put("QTY", util.nvl(rs.getString("qty")));
                     data.put("AMT", util.nvl(rs.getString("sl")));
                     empdataMap.put(emp, data);    
                 }
                 rs.close();
                pst.close();
                
                 sqlQ="select  sum(qty) qty, to_char(sum(cts),'99999990.00') cts,trunc(sum(cts*sl_rte), 2) sl,byr\n" + 
                           "from gt_srch_rslt gt, mtrms t\n" + 
                           "where gt.cert_lab = t.term\n" +conQ +
                           "group by byr\n" + 
                           "order by byr";
              outLst = db.execSqlLst("byr", sqlQ,  ary);
               pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
                  while (rs.next()) {
                      data = new HashMap();
                      String byr=util.nvl(rs.getString("byr"));
                      data.put("BYR", byr);
                      data.put("CTS", util.nvl(rs.getString("cts")));
                      data.put("QTY", util.nvl(rs.getString("qty")));
                      data.put("AMT", util.nvl(rs.getString("sl")));
                      byrdataMap.put(byr, data);      
                  }
                  rs.close();
                pst.close();
                
                String pemp="";
                String pbyr="";
                
                for(int i=0;i<datatLst.size();i++){
                dataMap = new HashMap();
                dataMap=(HashMap)datatLst.get(i);
                String emp=(String)dataMap.get("EMP");
                String byr=(String)dataMap.get("BYR");
                if(pemp.equals(""))
                    pemp=emp;
                if(pbyr.equals(""))
                pbyr=byr;
                    if(!pbyr.equals(byr)){
                        data = new HashMap();
                        data=(HashMap)byrdataMap.get(pbyr);
                        finaldatatLst.add(data);
                        pbyr=byr;
                    }
                    if(!pemp.equals(emp)){
                        data = new HashMap();
                        data=(HashMap)empdataMap.get(pemp);
                        finaldatatLst.add(data);
                        pemp=emp;
                    }
                    finaldatatLst.add(dataMap);
                    
                }
                    if(!pbyr.equals("")){
                        data = new HashMap();
                        data=(HashMap)byrdataMap.get(pbyr);
                        finaldatatLst.add(data);
                    }
                    if(!pemp.equals("")){
                        data = new HashMap();
                        data=(HashMap)empdataMap.get(pemp);
                        finaldatatLst.add(data);
                    }
                
                itemHdr.add("EMP");
                itemHdr.add("BYR");
                itemHdr.add("TERMS");
                itemHdr.add("GRP");
                itemHdr.add("QTY");
                itemHdr.add("CTS");
                itemHdr.add("AMT");
                session.setAttribute("pktList", finaldatatLst);
                session.setAttribute("itemHdr",itemHdr);
                util.updAccessLog(req,res,"Rpt Action", "fetchsaletermswisebyr end");
                return am.findForward("pktDtl");
            }
        }

    
    
    public void datasaletermswise(HttpServletRequest req,HttpServletResponse res,ReportForm udf)throws Exception{
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr();
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            String frmdte = util.nvl((String)udf.getValue("issfrmdte"));
            String todte = util.nvl((String)udf.getValue("isstodte"));
            String pkt_ty = util.nvl((String)udf.getValue("pkt_ty"));
            String empIdn = util.nvl((String)udf.getValue("empIdn"));
            String nmeID = util.nvl((String)req.getParameter("nmeID"));
            ArrayList ary=new ArrayList();
            ArrayList dayssaletermLst=new ArrayList();
            ArrayList termsaletermLst=new ArrayList();
            HashMap datasaletermLst=new HashMap();
            double initial = 0 ;
            String conQ="";
            if(pkt_ty.equals("NR")){
                conQ+=" and m.pkt_ty = ? ";
                ary.add(pkt_ty);
            }else if(pkt_ty.equals("SMX")){
                conQ+=" and m.pkt_ty = ? ";
                ary.add(pkt_ty);
            }else if(pkt_ty.equals("MIX")){
                conQ+=" and m.pkt_ty <> 'NR' ";
            }
            String delQ = " Delete from gt_srch_rslt ";
            int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            
            String conQdte=" and trunc(p.dte) >= trunc(sysdate) - 365";
            if(!frmdte.equals("") && !todte.equals(""))
            conQdte = " and trunc(p.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
            if(frmdte.equals("") && !todte.equals(""))
            conQdte = " and trunc(p.dte) between to_date('"+todte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
            if(!frmdte.equals("") && todte.equals(""))
            conQdte = " and trunc(p.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+frmdte+"' , 'dd-mm-yyyy') ";
            if(!empIdn.equals("") && !empIdn.equals("0")){
                conQ = conQ+" and p.emp_idn = ?  ";
                ary.add(empIdn);
            }
            if(!nmeID.equals("") && !nmeID.equals("0")){
                conQ = conQ+" and p.nme_idn =? ";
                ary.add(nmeID);
            }
            
            String srchRef = " insert into gt_srch_rslt(srch_id, stk_idn, vnm, cmp, byr, emp,qty, cts, sl_rte, sl_dte, cert_lab)\n" + 
            "select s.idn, p.idn, m.vnm, nvl(m.upr, cmp), p.byr, p.emp,p.qty, p.cts, p.fnl_usd, p.dte, p.trm \n" + 
            "from SAL_MAX_IDN_PKT_NRMIX_V s, sal_pkt_dtl_v p, mstk m\n" + 
            "where s.idn = p.idn and s.mstk_idn = p.mstk_idn and m.idn = p.mstk_idn and m.stt not in('HIDE') and m.vnm not in('131.001') "+conQ+conQdte ;
             ct = db.execUpd("insert gt",srchRef, ary);
            
            String upddys = "  Update gt_srch_rslt set grp = CASE \n" + 
            "  WHEN trunc(sysdate) - trunc(sl_dte) BETWEEN 0 and 30 THEN 30\n" + 
            "  WHEN trunc(sysdate) - trunc(sl_dte) BETWEEN 31 and 60 THEN 60\n" + 
            "  WHEN trunc(sysdate) - trunc(sl_dte) BETWEEN 61 and 90 THEN 90\n" + 
            "  WHEN trunc(sysdate) - trunc(sl_dte) BETWEEN 91 and 180 THEN 180\n" + 
            "  ELSE 365  \n" + 
            "  END" ;
             ct = db.execUpd("insert gt",upddys, new ArrayList());
             
            String    getdata  = "select trunc(sum(trunc(cts,2)*sl_rte)/1000000,2) sl_mln, cert_lab term, grp\n" + 
            "from gt_srch_rslt gt, mtrms t\n" + 
            "where gt.cert_lab = t.term\n" + 
            "group by cert_lab, t.dys, grp\n" + 
            "order by t.dys, gt.grp";
         ArrayList outLst = db.execSqlLst("byr", getdata,  new ArrayList());
        PreparedStatement   pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String dys=util.nvl(rs.getString("grp"));
                String term=util.nvl(rs.getString("term"));
                String sl_mln=util.nvl(rs.getString("sl_mln"));
                datasaletermLst.put(dys+"_"+term, sl_mln);
                
                if(!dayssaletermLst.contains(dys))
                dayssaletermLst.add(dys);
                if(!termsaletermLst.contains(term))
                termsaletermLst.add(term);
    //            
    //            BigDecimal currBigsl_mlnVlu = new BigDecimal(sl_mln);
    //            
    //            BigDecimal dyslprpBigsl_mlnvlu = (BigDecimal)datasaletermLst.get(dys+"_TTL");
    //            if(dyslprpBigsl_mlnvlu==null)
    //            dyslprpBigsl_mlnvlu=new BigDecimal(initial);
    //            if(!sl_mln.equals("0"))
    //            dyslprpBigsl_mlnvlu = dyslprpBigsl_mlnvlu.add(currBigsl_mlnVlu);
    //            datasaletermLst.put(dys+"_TTL", dyslprpBigsl_mlnvlu);
    //            
    //            BigDecimal termlprpBigsl_mlnvlu = (BigDecimal)datasaletermLst.get(term+"_TTL");
    //            if(termlprpBigsl_mlnvlu==null)
    //            termlprpBigsl_mlnvlu=new BigDecimal(initial);
    //            if(!sl_mln.equals("0"))
    //            termlprpBigsl_mlnvlu = termlprpBigsl_mlnvlu.add(currBigsl_mlnVlu);
    //            datasaletermLst.put(term+"_TTL", termlprpBigsl_mlnvlu);
            }
            rs.close();
            pst.close();
            
            getdata  = "select trunc(sum(trunc(cts,2)*sl_rte)/1000000,2) sl_mln, cert_lab term\n" + 
                   "from gt_srch_rslt gt, mtrms t\n" + 
                   "where gt.cert_lab = t.term\n" + 
                   "group by cert_lab\n"  ;
               outLst = db.execSqlLst("byr", getdata,  new ArrayList());
              pst = (PreparedStatement)outLst.get(0);
              rs = (ResultSet)outLst.get(1);
                   while (rs.next()) {
                       datasaletermLst.put(util.nvl(rs.getString("term"))+"_TTL", util.nvl(rs.getString("sl_mln")));
                   }
                   rs.close();
                   pst.close();
                   
            getdata  = "select trunc(sum(trunc(cts,2)*sl_rte)/1000000,2) sl_mln, grp\n" + 
                   "from gt_srch_rslt gt, mtrms t\n" + 
                   "where gt.cert_lab = t.term\n" + 
                   "group by grp\n"  ;
          outLst = db.execSqlLst("byr", getdata,  new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
                   while (rs.next()) {
                       datasaletermLst.put(util.nvl(rs.getString("grp"))+"_TTL", util.nvl(rs.getString("sl_mln")));
                   }
                   rs.close();
                   pst.close();
                   
            getdata  = "select trunc(sum(trunc(cts,2)*sl_rte)/1000000,2) sl_mln\n" + 
                   "from gt_srch_rslt gt, mtrms t\n" + 
                   "where gt.cert_lab = t.term\n";
          outLst = db.execSqlLst("byr", getdata,  new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
                   while (rs.next()) {
                       datasaletermLst.put("TOTAL_TTL", util.nvl(rs.getString("sl_mln")));
                   }
                   rs.close();
            pst.close();
    //        for(int i=0;i<dayssaletermLst.size();i++){
    //            String dys=(String)dayssaletermLst.get(i);
    //            BigDecimal currBigsl_mlnVlu = (BigDecimal)datasaletermLst.get(dys+"_TTL");
    //            BigDecimal grandBigsl_mlnvlu = (BigDecimal)datasaletermLst.get("GRAND_TTL");
    //            if(grandBigsl_mlnvlu==null)
    //            grandBigsl_mlnvlu=new BigDecimal(initial);
    //            if(currBigsl_mlnVlu!=null)
    //            grandBigsl_mlnvlu = grandBigsl_mlnvlu.add(currBigsl_mlnVlu);
    //            datasaletermLst.put("GRAND_TTL", grandBigsl_mlnvlu);
    //        }
            req.setAttribute("dayssaletermLst", dayssaletermLst);
            req.setAttribute("termsaletermLst", termsaletermLst);
            req.setAttribute("datasaletermLst", datasaletermLst);
            req.setAttribute("view", "Y");
             
        }


    
    public ActionForward loadWEKLST(ActionMapping am, ActionForm form, HttpServletRequest req,

                                          HttpServletResponse res) throws Exception {

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

             return am.findForward("loadWEKLST"); 

         }

         }
    public ActionForward weeklyList(ActionMapping am, ActionForm form, HttpServletRequest req,

                                                 HttpServletResponse res) throws Exception {

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

                    ReportForm udf = (ReportForm)form;

                    String dte1fr = " trunc(sysdate) ";

                    String dte1to = " trunc(sysdate-7) ";

                    String dte4fr = " trunc(sysdate) ";

                    String dte4to = " trunc(sysdate-28) ";

                    String dte8fr = " trunc(sysdate) ";

                    String dte8to = " trunc(sysdate-56) ";

                   

                    dte1fr = util.nvl((String)udf.getValue("dte1fr"));

                    if(!dte1fr.equals(""))

                        dte1fr = "to_date('"+dte1fr+"' , 'dd-mm-yyyy')";

                   

                    dte1to = util.nvl((String)udf.getValue("dte1to"));

                    if(!dte1to.equals(""))

                        dte1to = "to_date('"+dte1to+"' , 'dd-mm-yyyy')";

                   

                    dte4fr = util.nvl((String)udf.getValue("dte4fr"));

                    if(!dte4fr.equals(""))

                        dte4fr = "to_date('"+dte4fr+"' , 'dd-mm-yyyy')";

                   

                    dte4to = util.nvl((String)udf.getValue("dte4to"));

                    if(!dte4to.equals(""))

                        dte4to = "to_date('"+dte4to+"' , 'dd-mm-yyyy')";

                    dte8fr = util.nvl((String)udf.getValue("dte8fr"));

                    if(!dte8fr.equals(""))

                        dte8fr = "to_date('"+dte8fr+"' , 'dd-mm-yyyy')";

                   

                    dte8to = util.nvl((String)udf.getValue("dte8to"));

                    if(!dte8to.equals(""))

                        dte8to = "to_date('"+dte8to+"' , 'dd-mm-yyyy')";

                   

                   

                    HashMap dataDtlMap = new HashMap();

                    ArrayList mixSzList = new ArrayList();

                    String excelSql ="Select SH.val shape,SZ.val mixsz,CLR.val mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" +

                    "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) days,trunc(sum(DYSPCT.num),0) daysPct,trunc(avg(SRT.num),0) srt,trunc(avg(FA_PRI.num),0) FA_PRI\n" +

                    " , trunc((((trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) /trunc(avg(FA_PRI.num),0))*100)-100),2) lp\n"+

                    " From MSTK A\n" +

                    " , STK_DTL SH\n" +

                    " , STK_DTL DEPT\n" +

                    " , STK_DTL CLR\n" +

                    " , STK_DTL SZ\n" +

                    " , STK_DTL DYS\n" +

                    " , STK_DTL DYSPCT\n" +

                    " , STK_DTL SRT\n" +

                    " , STK_DTL FA_PRI\n" +

                    " Where a.STT = 'MKAV' and a.pkt_ty <> 'NR' \n" +

                    " and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val\n" +

                    "  in ('ROUND')  And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" +

                    "  and DEPT.val='18-96-MIX'  and a.idn = SZ.mstk_idn and SZ.grp = 1 and SZ.mprp = 'MIX_SIZE'\n" +

                    " and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_GRP'\n" +

                    " and upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')) not in('NATTS','TLB','TLC','FCCUT','FCCOL','SAM','REP','RSP','MIX')\n" +

                    " And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS'\n" +

                    " And DYS.Mstk_Idn = DYSPCT.Mstk_Idn And DYSPCT.Grp = 1 And DYSPCT.Mprp = 'IN_STK_PCT'\n" +

                    " And DYSPCT.Mstk_Idn = SRT.Mstk_Idn And SRT.Grp = 1 And SRT.Mprp = 'MIX_RTE'\n" +

                    " And FA_PRI.Mstk_Idn = SRT.Mstk_Idn And FA_PRI.Grp = 1 And FA_PRI.Mprp = 'FA_PRI' "+

                    " and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0 and FA_PRI.num > 0 group by SH.val,SZ.val, CLR.val\n" +

                    "union\n" +

                    "Select SH.val shape,SZ.val mixsz,'TTL' mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" +

                    "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) days,trunc(sum(DYSPCT.num),0) daysPct,trunc(avg(SRT.num),0) srt,trunc(avg(FA_PRI.num),0) FA_PRI\n" +

                    " , trunc((((trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) /trunc(avg(FA_PRI.num),0))*100)-100),2) lp\n"+

                    " From MSTK A\n" +

                    " , STK_DTL SH\n" +

                    " , STK_DTL DEPT\n" +

                    " , STK_DTL CLR\n" +

                    " , STK_DTL SZ\n" +

                    " , STK_DTL DYS\n" +

                    " , STK_DTL DYSPCT\n" +

                    " , STK_DTL SRT\n" +

                    " , STK_DTL FA_PRI\n" +

                    " Where a.STT = 'MKAV' and a.pkt_ty <> 'NR' \n" +

                    " and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val \n" +

                    "  in ('ROUND')  And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" +

                    "  and DEPT.val='18-96-MIX'  and a.idn = SZ.mstk_idn and SZ.grp = 1 and SZ.mprp = 'MIX_SIZE'\n" +

                    " and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_GRP'\n" +

                    " and upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')) not in('NATTS','TLB','TLC','FCCUT','FCCOL','SAM','REP','RSP','MIX')\n" +

                    " And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS'\n" +

                    " And DYS.Mstk_Idn = DYSPCT.Mstk_Idn And DYSPCT.Grp = 1 And DYSPCT.Mprp = 'IN_STK_PCT'\n" +

                    " And DYSPCT.Mstk_Idn = SRT.Mstk_Idn And SRT.Grp = 1 And SRT.Mprp = 'MIX_RTE'\n" +

                    " And FA_PRI.Mstk_Idn = SRT.Mstk_Idn And FA_PRI.Grp = 1 And FA_PRI.Mprp = 'FA_PRI' "+

                    " and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0 and FA_PRI.num > 0 group by SH.val,SZ.val\n" +

                    " union\n" +

                    "Select 'TTL' shape,'TTL' mixsz,'TTL' mixclr,to_char(trunc(sum(trunc(nvl(a.cts, 0) - nvl(a.cts_iss, 0), 2)),2),'9999999999990.00') cts,\n" +

                    "trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) avg,trunc(avg(DYS.num),0) days,trunc(sum(DYSPCT.num),0) daysPct,trunc(avg(SRT.num),0) srt,trunc(avg(FA_PRI.num),0) FA_PRI\n" +

                    " , trunc((((trunc(greatest(sum(to_number(nvl(upr,cmp))*(nvl(a.cts, 0) - nvl(a.cts_iss, 0))),1)/sum(nvl(a.cts, 0) - nvl(a.cts_iss, 0)),2) /trunc(avg(FA_PRI.num),0))*100)-100),2) lp\n"+

                    " From MSTK A\n" +

                    " , STK_DTL SH\n" +

                    " , STK_DTL DEPT\n" +

                    " , STK_DTL CLR\n" +

                    " , STK_DTL SZ\n" +

                    " , STK_DTL DYS\n" +

                    " , STK_DTL DYSPCT\n" +

                    " , STK_DTL SRT\n" +

                    " , STK_DTL FA_PRI\n" +

                    " Where a.STT = 'MKAV' and a.pkt_ty <> 'NR' \n" +

                    " and a.idn = SH.mstk_idn and SH.grp = 1 and SH.mprp = 'SHAPE' and SH.val\n" +

                    "  in ('ROUND')  And a.Idn = DEPT.Mstk_Idn And DEPT.Grp = 1 And DEPT.Mprp = 'DEPT'\n" +

                    "  and DEPT.val='18-96-MIX'  and a.idn = SZ.mstk_idn and SZ.grp = 1 and SZ.mprp = 'MIX_SIZE'\n" +

                    " and a.idn = clr.mstk_idn and clr.grp = 1 and clr.mprp = 'MIX_GRP'\n" +

                    " and upper(replace(replace(replace(replace(replace(replace(replace(clr.val,'W.',''),'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')) not in('NATTS','TLB','TLC','FCCUT','FCCOL','SAM','REP','RSP','MIX')\n" +

                    " And a.Idn = DYS.Mstk_Idn And DYS.Grp = 1 And DYS.Mprp = 'IN_STK_DYS'\n" +

                    " And DYS.Mstk_Idn = DYSPCT.Mstk_Idn And DYSPCT.Grp = 1 And DYSPCT.Mprp = 'IN_STK_PCT'\n" +

                    " And DYSPCT.Mstk_Idn = SRT.Mstk_Idn And SRT.Grp = 1 And SRT.Mprp = 'MIX_RTE'\n" +

                    " And FA_PRI.Mstk_Idn = SRT.Mstk_Idn And FA_PRI.Grp = 1 And FA_PRI.Mprp = 'FA_PRI' "+

                    " and nvl(a.cts, 0) - nvl(a.cts_iss, 0) > 0 and FA_PRI.num > 0";
                    
                    String pMixSz="";
                    ArrayList mixClrList = new ArrayList();
                    HashMap mixclrSizeMap = new HashMap();
                    ArrayList rsLst = db.execSqlLst("weekly sql", excelSql, new ArrayList());

                    PreparedStatement pstmt = (PreparedStatement)rsLst.get(0);

                    ResultSet rs =(ResultSet)rsLst.get(1);

                    while(rs.next()){
                        String mixsz = rs.getString("mixsz");
                        
                        if(pMixSz.equals(""))
                            pMixSz=mixsz;
 
                        String shape = rs.getString("shape");

                        

                        String mixclr = rs.getString("mixclr");

                        String cts = rs.getString("cts");

                        String avg = rs.getString("avg");

                        String days = rs.getString("days");

                        String dayspct = rs.getString("dayspct");

                        String srt = rs.getString("srt");

                        String FA_PRI = rs.getString("FA_PRI");

                        String lp = rs.getString("lp")+"%";

                        dataDtlMap.put(shape+"_"+mixsz+"_"+mixclr+"_CTS", cts);

                        dataDtlMap.put(shape+"_"+mixsz+"_"+mixclr+"_AVG", avg);

                        dataDtlMap.put(shape+"_"+mixsz+"_"+mixclr+"_DAYS", days);

                        dataDtlMap.put(shape+"_"+mixsz+"_"+mixclr+"_DAYPCT", dayspct);

                        dataDtlMap.put(shape+"_"+mixsz+"_"+mixclr+"_SRT", srt);

                        dataDtlMap.put(shape+"_"+mixsz+"_"+mixclr+"_FA_PRI", FA_PRI);

                        dataDtlMap.put(shape+"_"+mixsz+"_"+mixclr+"_LP", lp);
                        
                        if(!mixsz.equals(pMixSz)){
                            mixclrSizeMap.put(pMixSz, mixClrList);
                            pMixSz = mixsz;
                            mixClrList = new ArrayList();
                        }
                        mixClrList.add(mixclr);
                       
                        if(mixSzList.indexOf(mixsz)==-1)
                            mixSzList.add(mixsz);

                    }
                    if(!pMixSz.equals(""))
                        mixclrSizeMap.put(pMixSz, mixClrList);
                        
                    rs.close();

                    pstmt.close();

                   

                    String sizeSummry="with \n" +

                    "MKT_TTL as (\n" +

                    "  select sum(a.cts) cts, sum(a.cts*nvl(a.upr, a.cmp)) vlu\n" +

                    "    from mstk a, stk_dtl dept\n" +

                    "  where a.idn = dept.mstk_idn and a.stt = 'MKAV'\n" +

                    "    and a.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" +

                    ")\n" +

                    ", MKT as (\n" +

                    "  select sz.val sz, sum(a.cts) cts, sum(a.cts*nvl(a.upr, a.cmp)) vlu\n" +

                    "    , DECODE(SUM(a.cts), 0, 0, ROUND(sum(a.cts*nvl(a.upr, a.cmp)*dys.num)/sum(a.cts*nvl(a.upr, a.cmp)))) dys\n" +

                    "    from mstk a, stk_dtl dept, stk_dtl sz, stk_dtl dys\n" +

                    "  where 1 = 1 and a.pkt_ty = 'MIX' and a.stt = 'MKAV'\n" +

                    "    and a.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" +

                    "    and a.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_SIZE' --and sz.val = '1/5'\n" +

                    "    and a.idn = dys.mstk_idn and dys.grp = 1 and dys.mprp = 'IN_STK_DYS' --and sz.val = '1/5'\n" +

                    "  group by sz.val\n" +

                    ")\n" +

                    ", ADD_ON_TTL as (\n" +

                    "  select sum(a.cts), sum(a.cts*nvl(a.upr, a.cmp)) vlu\n" +

                    "    from mstk a, mix_trf_log b, stk_dtl dept\n" +

                    "  where a.idn = b.frm_idn \n" +

                    "    and a.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" +

                    "    and trunc(b.log_dte) between "+dte1fr+" and "+dte1to+" \n" +

                    "    and a.stt = 'TRF_MRG'  and a.cts <> 0\n" +

                    ")\n" +

                    ", ADD_ON as (\n" +

                    "  select sz.val sz, sum(a.cts) cts, sum(a.cts*nvl(a.upr, a.cmp)) vlu\n" +

                    "    from mstk a, mix_trf_log b, stk_dtl dept, stk_dtl sz\n" +

                    "  where a.idn = b.frm_idn \n" +

                    "    and a.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" +

                    "    and a.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_SIZE' --and sz.val = '1/5'\n" +

                    "    and trunc(b.log_dte) between  "+dte1fr+" and "+dte1to+" \n" +

                    "    and a.stt = 'TRF_MRG'  and a.cts <> 0\n" +

                    "  group by sz.val\n" +

                    ")\n" +

                    ", SOLD4W_TTL as (\n" +

                    "  select sum(js.cts - nvl(js.cts_rtn, 0)) cts, sum((js.cts - nvl(js.cts_rtn, 0))*nvl(js.fnl_sal, js.quot)) vlu\n" +

                    "    from jansal js, mstk m, stk_dtl dept\n" +

                    "  where 1 = 1\n" +

                    "    and m.idn = js.mstk_idn and m.pkt_ty = 'MIX'\n" +

                    "    and trunc(js.dte) between "+dte4fr+" and "+dte4to+"  and js.stt not in ('CL', 'RT')\n" +

                    "    and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" +

                    ")\n" +

                    ", SOLD4W as (\n" +

                    "  select sz.val sz, sum(js.cts - nvl(js.cts_rtn, 0)) cts, sum((js.cts - nvl(js.cts_rtn, 0))*nvl(js.fnl_sal, js.quot)) vlu\n" +

                    "    from jansal js, mstk m, stk_dtl dept, stk_dtl sz\n" +

                    "  where 1 = 1\n" +

                    "    and m.idn = js.mstk_idn and m.pkt_ty = 'MIX'\n" +

                    "    and trunc(js.dte) between "+dte4fr+" and "+dte4to+"  and js.stt not in ('CL', 'RT')\n" +

                    "    and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" +

                    "    and m.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_SIZE' --and sz.val = '1/5'\n" +

                    "  group by sz.val\n" +

                    ")\n" +

                    ", SOLD8W_TTL as (\n" +

                    "  select sum(js.cts - nvl(js.cts_rtn, 0)) cts, sum((js.cts - nvl(js.cts_rtn, 0))*nvl(js.fnl_sal, js.quot)) vlu\n" +

                    "    from jansal js, mstk m, stk_dtl dept\n" +

                    "  where 1 = 1\n" +

                    "    and m.idn = js.mstk_idn and m.pkt_ty = 'MIX'\n" +

                    "    and trunc(js.dte) between "+dte8fr+" and "+dte8to+"  and js.stt not in ('CL', 'RT')\n" +

                    "    and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" +

                    ")\n" +

                    ", SOLD8W as (\n" +

                    "  select sz.val sz, sum(js.cts - nvl(js.cts_rtn, 0)) cts, sum((js.cts - nvl(js.cts_rtn, 0))*nvl(js.fnl_sal, js.quot)) vlu\n" +

                    "    from jansal js, mstk m, stk_dtl dept, stk_dtl sz\n" +

                    "  where 1 = 1\n" +

                    "    and m.idn = js.mstk_idn and m.pkt_ty = 'MIX'\n" +

                    "    and trunc(js.dte) between "+dte8fr+" and "+dte8to+"  and js.stt not in ('CL', 'RT')\n" +

                    "    and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" +

                    "    and m.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_SIZE' --and sz.val = '1/5'\n" +

                    "  group by sz.val\n" +

                    ")\n" +

                    ", SOLD_TTL as (\n" +

                    "  select sum(js.cts - nvl(js.cts_rtn, 0)) cts, sum((js.cts - nvl(js.cts_rtn, 0))*nvl(js.fnl_sal, js.quot)) vlu\n" +

                    "    from jansal js, mstk m, stk_dtl dept\n" +

                    "  where 1 = 1\n" +

                    "    and m.idn = js.mstk_idn and m.pkt_ty = 'MIX'\n" +

                    "    and trunc(js.dte) between "+dte1fr+" and "+dte1to+" and js.stt not in ('CL', 'RT')\n" +

                    "    and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" +

                    ")\n" +

                    ", SOLD as (\n" +

                    "  select sz.val sz, sum(js.cts - nvl(js.cts_rtn, 0)) cts, sum((js.cts - nvl(js.cts_rtn, 0))*nvl(js.fnl_sal, js.quot)) vlu\n" +

                    "    from jansal js, mstk m, stk_dtl dept, stk_dtl sz\n" +

                    "  where 1 = 1\n" +

                    "    and m.idn = js.mstk_idn and m.pkt_ty = 'MIX'\n" +

                    "    and trunc(js.dte) between "+dte1fr+" and "+dte1to+" and js.stt not in ('CL', 'RT')\n" +

                    "    and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" +

                    "    and m.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_SIZE' --and sz.val = '1/5'\n" +

                    "  group by sz.val\n" +

                    ")\n" +

                    "select\n" +

                    "  addon.sz\n" +

                    "  , mkt.dys dys\n" +

                    "  , ROUND(mkt.vlu/1000000, 2) mktVlu\n" +

                    "  , ROUND(mkt.vlu*100/mttl.vlu) mktpct\n" +

                    "  , ROUND(addon.cts) addonCts, ROUND(addon.vlu/1000000, 2) addonVlu\n" +

                    "  , ROUND(addon.vlu*100/attl.vlu) addonpct\n" +

                    "  , ROUND(sl.cts) slCts, ROUND(sl.vlu/1000000, 2) slVlu\n" +

                    "  , ROUND(sl.vlu*100/sttl.vlu) slPct\n" +

                    "  , ROUND(sl4.cts) Cts4W, ROUND(sl8.vlu/1000000, 2) vlu4W\n" +

                    "  , ROUND(sl4.vlu*100/s4wttl.vlu) sl4wPct\n" +

                    "  , ROUND(sl8.cts) Cts8W, ROUND(sl8.vlu/1000000, 2) vlu8W\n" +

                    "  , ROUND(sl8.vlu*100/s8wttl.vlu) sl8wPct\n" +

                    "from sold sl, sold4W sl4, sold8w sl8, add_on addon, prp p\n" +

                    "    , add_on_ttl attl, sold_ttl sttl, sold4w_ttl s4wttl, sold8w_ttl s8wttl\n" +

                    "    , mkt mkt, mkt_ttl mttl\n" +

                    "where 1 = 1\n" +

                    "  and p.mprp = 'MIX_SIZE' \n" +

                    "  and mkt.sz = p.val\n" +

                    "  and addon.sz = p.val and sl.sz = p.val\n" +

                    "  and sl4.sz = p.val and sl8.sz = p.val\n" +

                    "order by p.srt";

                  

                    ArrayList mixSz1List = new ArrayList();

                   rsLst = db.execSqlLst("weekly sql", sizeSummry, new ArrayList());

                     pstmt = (PreparedStatement)rsLst.get(0);

                    rs =(ResultSet)rsLst.get(1);

                    while(rs.next()){

                

                        String mixsz = rs.getString("sz");

                        String addpct = rs.getString("addonpct");

                        String addvlu = rs.getString("addonVlu");

                        String slpct = rs.getString("slpct");

                        String dys = rs.getString("dys");

                        String slwpct = rs.getString("sl4wPct");

                        String slEwpct = rs.getString("sl8wPct");

                        String vluFw = rs.getString("vlu4W");

                    

                        dataDtlMap.put(mixsz+"_ADDPCT", addpct);

                        dataDtlMap.put(mixsz+"_ADDVLU", addvlu);

                        dataDtlMap.put(mixsz+"_SLPCT", slpct);

                        dataDtlMap.put(mixsz+"_DYS", dys);

                        dataDtlMap.put(mixsz+"_SLWPCT", slwpct);

                        dataDtlMap.put(mixsz+"_SLEWPCT", slEwpct);

                        dataDtlMap.put(mixsz+"_VLUFW", vluFw);

                        mixSz1List.add(mixsz);

                    }

                    rs.close();

                    pstmt.close();


                    String mixClrSummry="with\n" + 
                    "MKT_TTL as (\n" + 
                    "  select sum(a.cts) cts, sum(a.cts*nvl(a.upr, a.cmp)) vlu\n" + 
                    "    from mstk a, stk_dtl dept\n" + 
                    "  where a.idn = dept.mstk_idn and a.stt = 'MKAV'\n" + 
                    "    and a.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" + 
                    ")\n" + 
                    ", MKT as (\n" + 
                    "  select pp.val sz, sum(a.cts) cts, sum(a.cts*nvl(a.upr, a.cmp)) vlu\n" + 
                    "    , ROUND(sum(a.cts*nvl(a.upr, a.cmp)*dys.num)/sum(a.cts*nvl(a.upr, a.cmp))) dys\n" + 
                    "    from mstk a, stk_dtl dept, stk_dtl sz, stk_dtl dys , prp pp\n" + 
                    "  where 1 = 1 and a.pkt_ty = 'MIX' and a.stt = 'MKAV'\n" + 
                    "    and a.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" + 
                    "    and a.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_GRP' --and sz.val = '1/5'\n" + 
                    "    and a.idn = dys.mstk_idn and dys.grp = 1 and dys.mprp = 'IN_STK_DYS' --and sz.val = '1/5'\n" + 
                    "    and sz.mprp = pp.mprp  and sz.val = pp.val\n" + 
                    "    and a.cts > 0\n" + 
                    "  group by pp.val\n" + 
                    ")\n" + 
                    ", ADD_ON_TTL as (\n" + 
                    "  select sum(a.cts), sum(a.cts*nvl(a.upr, a.cmp)) vlu\n" + 
                    "    from mstk a, mix_trf_log b, stk_dtl dept\n" + 
                    "  where a.idn = b.frm_idn\n" + 
                    "    and a.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" + 
                    "    and trunc(b.log_dte) between  "+dte1fr+" and "+dte1to+"\n" + 
                    "    and a.stt = 'TRF_MRG'  and a.cts <> 0\n" + 
                    ")\n" + 
                    ", ADD_ON as (\n" + 
                    "  select nvl(pp.val, 'NN') sz, sum(a.cts) cts, sum(a.cts*nvl(a.upr, a.cmp)) vlu\n" + 
                    "    from mstk a, mix_trf_log b, stk_dtl dept, stk_dtl sz,prp pp\n" + 
                    "  where a.idn = b.frm_idn\n" + 
                    "    and a.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" + 
                    "    and a.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_GRP' --and sz.val = '1/5'\n" + 
                    "    and trunc(b.log_dte) between  "+dte1fr+" and "+dte1to+"\n" +
                    "    and a.stt = 'TRF_MRG' \n" + 
                    "    and sz.mprp = pp.mprp\n" + 
                    "    and a.cts > 0\n" + 
                    "  group by pp.val\n" + 
                    ")\n" + 
                    ", SOLD4W_TTL as (\n" + 
                    "  select sum(js.cts - nvl(js.cts_rtn, 0)) cts, sum((js.cts - nvl(js.cts_rtn, 0))*nvl(js.fnl_sal, js.quot)) vlu\n" + 
                    "    from jansal js, mstk m, stk_dtl dept\n" + 
                    "  where 1 = 1\n" + 
                    "    and m.idn = js.mstk_idn and m.pkt_ty = 'MIX'\n" + 
                    "    and trunc(js.dte) between "+dte4fr+" and "+dte4to+" and js.stt not in ('CL', 'RT')\n" + 
                    "    and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" + 
                    ")\n" + 
                    ", SOLD4W as (\n" + 
                    "  select pp.val sz, sum(js.cts - nvl(js.cts_rtn, 0)) cts, sum((js.cts - nvl(js.cts_rtn, 0))*nvl(js.fnl_sal, js.quot)) vlu\n" + 
                    "    from jansal js, mstk m, stk_dtl dept, stk_dtl sz ,prp pp\n" + 
                    "  where 1 = 1\n" + 
                    "    and m.idn = js.mstk_idn and m.pkt_ty = 'MIX'\n" + 
                    "    and trunc(js.dte) between "+dte4fr+" and "+dte4to+"  and js.stt not in ('CL', 'RT')\n" + 
                    "    and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" + 
                    "    and m.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_GRP' --and sz.val = '1/5'\n" + 
                    "   and sz.mprp = pp.mprp  and sz.val = pp.val\n" + 
                    "   group by pp.val\n" + 
                    ")\n" + 
                    ", SOLD8W_TTL as (\n" + 
                    "  select sum(js.cts - nvl(js.cts_rtn, 0)) cts, sum((js.cts - nvl(js.cts_rtn, 0))*nvl(js.fnl_sal, js.quot)) vlu\n" + 
                    "    from jansal js, mstk m, stk_dtl dept\n" + 
                    "  where 1 = 1\n" + 
                    "    and m.idn = js.mstk_idn and m.pkt_ty = 'MIX'\n" + 
                    "    and trunc(js.dte) between "+dte8fr+" and "+dte8to+"  and js.stt not in ('CL', 'RT')\n" + 
                    "    and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" + 
                    ")\n" + 
                    ", SOLD8W as (\n" + 
                    "  select nvl(pp.val, 'NN') sz, sum(js.cts - nvl(js.cts_rtn, 0)) cts, sum((js.cts - nvl(js.cts_rtn, 0))*nvl(js.fnl_sal, js.quot)) vlu\n" + 
                    "    from jansal js, mstk m, stk_dtl dept, stk_dtl sz,prp pp\n" + 
                    "  where 1 = 1\n" + 
                    "    and m.idn = js.mstk_idn and m.pkt_ty = 'MIX'\n" + 
                    "    and trunc(js.dte) between "+dte8fr+" and "+dte8to+"  and js.stt not in ('CL', 'RT')\n" + 
                    "    and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" + 
                    "    and m.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_GRP' --and sz.val = '1/5'\n" + 
                    "   and sz.mprp = pp.mprp and sz.val = pp.val\n" + 
                    "   group by pp.val\n" + 
                    ")\n" + 
                    ", SOLD_TTL as (\n" + 
                    "  select sum(js.cts - nvl(js.cts_rtn, 0)) cts, sum((js.cts - nvl(js.cts_rtn, 0))*nvl(js.fnl_sal, js.quot)) vlu\n" + 
                    "    from jansal js, mstk m, stk_dtl dept\n" + 
                    "  where 1 = 1\n" + 
                    "    and m.idn = js.mstk_idn and m.pkt_ty = 'MIX'\n" + 
                    "    and trunc(js.dte) between "+dte1fr+" and "+dte1to+" and js.stt not in ('CL', 'RT')\n" + 
                    "    and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" + 
                    ")\n" + 
                    ", SOLD as (\n" + 
                    "  select nvl(pp.val, 'NN') sz, sum(js.cts - nvl(js.cts_rtn, 0)) cts, sum((js.cts - nvl(js.cts_rtn, 0))*nvl(js.fnl_sal, js.quot)) vlu\n" + 
                    "    from jansal js, mstk m, stk_dtl dept, stk_dtl sz ,prp pp\n" + 
                    "  where 1 = 1\n" + 
                    "    and m.idn = js.mstk_idn and m.pkt_ty = 'MIX'\n" + 
                    "    and trunc(js.dte)  between "+dte1fr+" and "+dte1to+"  and js.stt not in ('CL', 'RT')\n" + 
                    "    and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = '18-96-MIX'\n" + 
                    "    and m.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'MIX_GRP' --and sz.val = '1/5'\n" + 
                    "    and sz.mprp = pp.mprp and sz.val = pp.val\n" + 
                    "   group by pp.val\n" + 
                    ")\n" + 
                    "select distinct\n" + 
                    "  mkt.sz\n" + 
                    "  , mkt.dys dys\n" + 
                    "  , ROUND(mkt.vlu/1000000, 2) mktVlu\n" + 
                    "  , ROUND(mkt.vlu*100/mttl.vlu) mktpct\n" + 
                    "  , ROUND(addon.cts) addonCts, ROUND(addon.vlu/1000000, 2) addonVlu\n" + 
                    "  , ROUND(addon.vlu*100/attl.vlu) addonpct\n" + 
                    "  , ROUND(sl.cts) slCts, ROUND(sl.vlu/1000000, 2) slVlu\n" + 
                    "  , ROUND(sl.vlu*100/sttl.vlu) slPct\n" + 
                    "  , ROUND(sl4.cts) Cts4W, ROUND(sl8.vlu/1000000, 2) vlu4W\n" + 
                    "  , ROUND(sl4.vlu*100/s4wttl.vlu) sl4wPct\n" + 
                    "  , ROUND(sl8.cts) Cts8W, ROUND(sl8.vlu/1000000, 2) vlu8W\n" + 
                    "  , ROUND(sl8.vlu*100/s8wttl.vlu) sl8wPct\n" + 
                    "from sold sl, sold4W sl4, sold8w sl8, add_on addon, prp p\n" + 
                    "    , add_on_ttl attl, sold_ttl sttl, sold4w_ttl s4wttl, sold8w_ttl s8wttl\n" + 
                    "    , mkt mkt, mkt_ttl mttl\n" + 
                    "where 1 = 1\n" + 
                    "  and p.mprp = 'MIX_GRP'\n" + 
                    "  and mkt.sz = p.val\n" + 
                    "  and addon.sz = p.val and sl.sz = p.val\n" + 
                    "  and sl4.sz = p.val and sl8.sz = p.val\n" + 
                    "--group by mkt.sz\n" + 
                    "order by 1\n" + 
                    "--order by p.srt\n";

                    


                    rsLst = db.execSqlLst("weekly sql", mixClrSummry, new ArrayList());

                     pstmt = (PreparedStatement)rsLst.get(0);

                    rs =(ResultSet)rsLst.get(1);

                    while(rs.next()){

                    

                        String mixClr = rs.getString("sz");

                        String addpct = rs.getString("addonpct");

                        String addvlu = rs.getString("addonVlu");

                        String slpct = rs.getString("slpct");

                        String dys = rs.getString("dys");

                        String slwpct = rs.getString("sl4wPct");

                        String slEwpct = rs.getString("sl8wPct");

                        String vluFw = rs.getString("vlu4W");

                    

                        dataDtlMap.put(mixClr+"_ADDPCT", addpct);

                        dataDtlMap.put(mixClr+"_ADDVLU", addvlu);

                        dataDtlMap.put(mixClr+"_SLPCT", slpct);

                        dataDtlMap.put(mixClr+"_DYS", dys);

                        dataDtlMap.put(mixClr+"_SLWPCT", slwpct);

                        dataDtlMap.put(mixClr+"_SLEWPCT", slEwpct);

                        dataDtlMap.put(mixClr+"_VLUFW", vluFw);

                        mixClrList.add(mixClr);

                    }

                    rs.close();

                    pstmt.close();
                    
                    
                   
                  

                    try {

                        String CONTENT_TYPE = "getServletContext()/vnd-excel";

                        String fileNm = "WeeklyList"+ util.getToDteTime() + "_CNT.xls";

                        util.updAccessLog(req, res, "Search Page", "Create Excel");

                        HSSFWorkbook hwb = new HSSFWorkbook();

                        res.setContentType(CONTENT_TYPE);

                        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);

                        ExcelUtilObj excelUtil = new ExcelUtilObj();

                        excelUtil.init(db, util, info, gtMgr);

                        OutputStream out = res.getOutputStream();

                        hwb = excelUtil.WeeklyExcel(dataDtlMap, mixSzList,mixSz1List,mixClrList,mixclrSizeMap, req);

                        hwb.write(out);

                        out.flush();

                        out.close();

                    } catch (IOException ioe) {

                        // TODO: Add catch code

                        ioe.printStackTrace();

                    }

                   

                 return null;

                }

            }
    
    
    public ActionForward webAlcReport(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Rpt Action", "webAlcReport start");
            ArrayList itemHdr=new ArrayList();
            ReportForm udf = (ReportForm)af;
            SearchQuery srchQuery = new SearchQuery();
            String frmdte=util.nvl((String)udf.getValue("frmdte")); 
            String todte=util.nvl((String)udf.getValue("todte"));
            String vnm = util.nvl((String)udf.getValue("vnmLst"));
            ArrayList pktList = new  ArrayList();
            ArrayList ary = new ArrayList();
            String conQ="";
                
            if(!vnm.equals("")){
            vnm = util.getVnm(vnm);
            conQ += " and ( ms.vnm in ("+vnm+") or ms.tfl3 in ("+vnm+") ) ";
            }
                
            if(!frmdte.equals("") && !todte.equals("")){
                    conQ = conQ+" and trunc(i.dte) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
            }
            
            if(conQ.equals("")){
                conQ += " and trunc(i.dte) >= trunc(sysdate) - 3 "; 
            }
            
            String rsltQ = "select m.inv_id, m.inv_typ, m.cmnts\n" + 
            ", to_char(m.dte, 'DD-MON HH24:mi:ss') order_dte, to_char(i.alc_dte, 'DD-MON HH24:mi:ss') alc_dte, nvl(i.alc_stt, 'N') alc_stt, i.alc_memo\n" + 
            ", trunc((i.alc_dte - m.dte)*24, 2) diff_hrs\n" + 
            ", byr.get_nm(mcust_idn) byr, get_trm_dsc(rel_idn) dsc\n" + 
            ", i.mstk_idn, i.cts, i.rte, i.quot, i.mkt_stt,ms.vnm\n" + 
            "from web_minv m, web_inv_dtl i,mstk ms\n" + 
            "where m.inv_id = i.inv_id and m.inv_typ = 'WA' and i.mstk_idn=ms.idn\n" + conQ+
            "order by m.inv_id";

            
             ary = new ArrayList();  
             ArrayList outLst = db.execSqlLst("Collection", rsltQ, ary);
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet  rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
                HashMap pktPrpMap = new HashMap();
                 pktPrpMap.put("Invoice Id", util.nvl(rs.getString("inv_id")));
                 pktPrpMap.put("Invoice Type",util.nvl(rs.getString("inv_typ")));
                 pktPrpMap.put("Comments",util.nvl(rs.getString("cmnts")));
                 pktPrpMap.put("Order Date",util.nvl(rs.getString("order_dte")));
                 pktPrpMap.put("Alc Date",util.nvl(rs.getString("alc_dte")));
                 pktPrpMap.put("Alc Status",util.nvl(rs.getString("alc_stt")));
                 pktPrpMap.put("Alc Memo",util.nvl(rs.getString("alc_memo")));
                 pktPrpMap.put("Diff hrs",util.nvl(rs.getString("diff_hrs")));
                 pktPrpMap.put("Buyer",util.nvl(rs.getString("byr")));
                 pktPrpMap.put("Terms",util.nvl(rs.getString("dsc")));
                 pktPrpMap.put("MSTK_IDN",util.nvl(rs.getString("mstk_idn")));
                 pktPrpMap.put("Cts",util.nvl(rs.getString("cts")));
                 pktPrpMap.put("Rte",util.nvl(rs.getString("rte")));
                 pktPrpMap.put("Quot",util.nvl(rs.getString("quot")));
                 pktPrpMap.put("Mkt Status",util.nvl(rs.getString("mkt_stt")));
                 pktPrpMap.put("Packet Id",util.nvl(rs.getString("vnm")));
                 pktList.add(pktPrpMap);
             }
            rs.close();
                pst.close();
            
                            itemHdr.add("Invoice Id");
                            itemHdr.add("Invoice Type");
                            itemHdr.add("Packet Id");
                            itemHdr.add("Order Date");
                            itemHdr.add("Alc Date");
                            itemHdr.add("Alc Status");
                            itemHdr.add("Alc Memo");
                            itemHdr.add("Diff hrs");
                            itemHdr.add("Buyer");
                            itemHdr.add("Terms");
                            itemHdr.add("Cts");
                            itemHdr.add("Rte");
                            itemHdr.add("Quot");
                            itemHdr.add("Mkt Status");
                            itemHdr.add("Comments");
            session.setAttribute("pktListWLC", pktList);
            session.setAttribute("itemHdrWLC", itemHdr);
            req.setAttribute("view", "Y");  
            udf.reset();
            util.updAccessLog(req,res,"Rpt Action", "webAlcReport end");
            return am.findForward("webAlcReport");
            }
        }
    
    public ActionForward opStk(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Rpt Action", "webAlcReport start");
            ReportForm udf = (ReportForm)af;
            ArrayList ary = new ArrayList();
            ArrayList shlist = new ArrayList();
            HashMap dataDtl = new HashMap();
                
            String shQ = "select b.val,sum(a.qty) qty,  to_char(trunc(sum(trunc(a.cts, 3)),3),'99999990.00') cts, \n" + 
            "trunc(sum(a.upr*trunc(a.cts,3))/sum(trunc(a.cts, 3)),0) avg,trunc(sum(a.upr*trunc(a.cts, 3)),0) vlu\n" + 
            "from mstk a,stk_dtl b  \n" + 
            "where \n" + 
            "a.stt='OPSTK15' and a.cts > 0 and\n" + 
            "a.idn=b.mstk_idn and b.mprp='SH' and b.grp=1\n" + 
            "group by b.val";
             ary = new ArrayList();  
              ArrayList outLst = db.execSqlLst("Collection", shQ, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet  rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
                 String sh=util.nvl(rs.getString("val"));
                 if(!shlist.contains(sh)){
                     shlist.add(sh);
                 }
                 dataDtl.put(sh+"_SH", sh);
                 dataDtl.put(sh+"_QTY",util.nvl(rs.getString("qty")));
                 dataDtl.put(sh+"_CTS",util.nvl(rs.getString("cts")));
                 dataDtl.put(sh+"_AVG",util.nvl(rs.getString("avg")));
                 dataDtl.put(sh+"_VLU",util.nvl(rs.getString("vlu")));
             }
             rs.close();
                pst.close();
                
                String ttlQ = "select sum(a.qty) qty,  to_char(trunc(sum(trunc(a.cts, 3)),3),'99999990.00') cts, \n" + 
                "trunc(sum(a.upr*trunc(a.cts,3))/sum(trunc(a.cts, 3)),0) avg,trunc(sum(a.upr*trunc(a.cts, 3)),0) vlu\n" + 
                "from mstk a,stk_dtl b  \n" + 
                "where \n" + 
                "a.stt='OPSTK15' and a.cts > 0 and\n" + 
                "a.idn=b.mstk_idn and b.mprp='SH' and b.grp=1";
                 ary = new ArrayList();  
               outLst = db.execSqlLst("Collection", ttlQ, ary);
               pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
                 while(rs.next()) {
                     dataDtl.put("QTY",util.nvl(rs.getString("qty")));
                     dataDtl.put("CTS",util.nvl(rs.getString("cts")));
                     dataDtl.put("AVG",util.nvl(rs.getString("avg")));
                     dataDtl.put("VLU",util.nvl(rs.getString("vlu")));
                 }
                 rs.close();
                pst.close();
            req.setAttribute("dataDtl", dataDtl);
            req.setAttribute("shlist", shlist);
            util.updAccessLog(req,res,"Rpt Action", "webAlcReport end");
            return am.findForward("opStk");
            }
        }
    
    public ActionForward createWlcXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Rpt Action", "createWlcXL start");
        HashMap dbinfo = info.getDmbsInfoLst();
        String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
        int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        ArrayList pktList = (ArrayList)session.getAttribute("pktListWLC");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdrWLC");
        int pktListsz=pktList.size();
     
            HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
            String contentTypezip = "application/zip";
            String fileNmzip = "WebAllocationReport_"+util.getToDteTime();
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
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "WebAllocationReport_"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        hwb.write(out);
        out.flush();
        out.close();
        }
            util.updAccessLog(req,res,"Rpt Action", "createWlcXL end");
        return null;
        }
    }
    
    
    public ActionForward ppAlcReport(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Rpt Action", "ppAlcReport start");
            ArrayList itemHdr=new ArrayList();
            ReportForm udf = (ReportForm)af;
            SearchQuery srchQuery = new SearchQuery();
            String frmdte=util.nvl((String)udf.getValue("frmdte")); 
            String todte=util.nvl((String)udf.getValue("todte"));
            String alcstt=util.nvl((String)udf.getValue("alcstt"),"ALC");
            String typ=util.nvl((String)udf.getValue("typ"),"ALL");
            String vnm = util.nvl((String)udf.getValue("vnmLst"));
                
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt=util.nvl((String)dbinfo.get("CNT"));
            
            ArrayList pktList = new  ArrayList();
            GenericInterface genericInt = new GenericImpl();
            ArrayList vwprpLst = genericInt.genericPrprVw(req, res, "PDS_ALC_VW","PDS_ALC_VW");
            ArrayList ary = new ArrayList();
            String conQ="";
            ary = new ArrayList(); 
            if(!vnm.equals("")){
            vnm = util.getVnm(vnm);
            conQ += " and ( c.vnm in ("+vnm+") or c.tfl3 in ("+vnm+") ) ";
            }
                
            if(!frmdte.equals("") && !todte.equals("")){
                    conQ = conQ+" and trunc(nvl(a.dte,sysdate)) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
            }
            
            if(conQ.equals("")){
                conQ += " and trunc(nvl(a.dte,sysdate)) = trunc(sysdate) - 0 "; 
            }
            if(!alcstt.equals("ALL")){
                    conQ += " and a.stt = ? "; 
                    ary.add(alcstt);
            }
                
                if(!typ.equals("ALL")){
                    if(typ.equals("BID")){
                        conQ += " and a.typ in('BD','PP') "; 
                    }else{
                        conQ += " and a.typ = ? "; 
                        ary.add(typ);
                    }
                }
                
                String mprpStr = "";
                String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||\n" + 
                "Upper(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT')) \n" + 
                "str From Rep_Prp Rp Where rp.MDL = ? order by srt " ;
                ArrayList param = new ArrayList();
                param.add("PDS_ALC_VW");
            ArrayList  outLst = db.execSqlLst("mprp ", mdlPrpsQ , param);
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
                while(rs.next()) {
                String val = util.nvl((String)rs.getString("str"));
                mprpStr = mprpStr +" "+val;
                }
                rs.close();
                pst.close();
            
                String getPktData = " with STKDTL as " +
                    " ( Select nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr ,st.mprp, c.sk1,a.rnk, e.cur||' -'||e.aadat_comm||' '||byr.get_trms(e.nmerel_idn) term,byr.get_nm(d.emp_idn) emp , round(a.net_rte) net_rte ,  b.chg_typ , b.diff, b.rel_idn ,decode(a.stt,'ALC','YES','NO') orStt, d.nme byrNm , \n" + 
            "to_char(decode(a.req_quot, 0, 0, decode(1, 0, 0, trunc(((a.req_quot*100/decode(e.cur, 'USD', 1, b.exh_rte))/greatest(c.rap_rte,100)) - 100,2))),'999999990.00') r_dis  ,\n" + 
            "to_char((((round(a.net_rte)*100)/greatest(a.rap_rte,100)) - 100),'999999990.00') net_dis ,\n" + 
            "to_char(trunc(((round(b.quot)*100)/greatest(a.rap_rte,100)) - 100,2),'999999990.00') byr_dis ,b.quot byr_prc,\n" + 
            "c.idn, b.quot, c.vnm, c.rap_rte , to_char(trunc(cts,2),'999999990.99') cts, b.idn bidIdn , \n" + 
            "a.stt, b.ofr_rte,to_char(a.alc_dte , 'dd-Mon-rrrr') alc_dte,a.typ,a.alc_memo,to_char( b.frm_dt , 'dd-Mon-rrrr') ofrDte,a.req_quot,nvl(c.upr, c.cmp) mem_prc,\n" + 
            "to_char(decode(nvl(c.upr, c.cmp), 0, 0, decode(1, 0, 0, trunc(((nvl(c.upr, c.cmp)*100)/greatest(c.rap_rte,100)) - 100,2))),'999999990.00') mr_dis,e.ttl_trm_pct ,e.mbrk2_comm\n"+
            "from\n" + 
            "stk_dtl st,malloc a,web_bid_wl b,mstk c ,nme_v d,nmerel e , nme_rel_all_v f\n" + 
            "where\n" + 
            "st.mstk_idn=a.mstk_idn and a.mstk_idn=b.mstk_idn and a.req_idn=b.idn and b.mstk_idn=c.idn and b.byr_idn = d.nme_idn \n" + 
            "and d.nme_idn=e.nme_idn and e.nmerel_idn = b.rel_idn  and e.nmerel_idn = f.nmerel_idn\n" + conQ+
                    " and exists (select 1 from rep_prp rp where rp.MDL = 'PDS_ALC_VW' and st.mprp = rp.prp)  And st.Grp = 1) " +
                    " Select * from stkDtl PIVOT " +
                    " ( max(atr) " +
                    " for mprp in ( "+mprpStr+" ) ) order by 1,2" ;
                
                outLst = db.execSqlLst(" memo pkts", getPktData, ary);
                pst = (PreparedStatement)outLst.get(0);
                 rs = (ResultSet)outLst.get(1);
                while (rs.next()) {
                HashMap pktPrpMap = new HashMap();
                 pktPrpMap.put("Alc Memo", util.nvl(rs.getString("alc_memo")));
                 pktPrpMap.put("Packet Id",util.nvl(rs.getString("vnm")));
                 pktPrpMap.put("Offer Date",util.nvl(rs.getString("ofrDte")));
                 pktPrpMap.put("Alc Date",util.nvl(rs.getString("alc_dte")));
                 pktPrpMap.put("Alc Status",util.nvl(rs.getString("orStt")));
                 pktPrpMap.put("Alc Type",util.nvl(rs.getString("typ")));
                 pktPrpMap.put("Sale Ex",util.nvl(rs.getString("emp")));
                 pktPrpMap.put("Buyer",util.nvl(rs.getString("byrNm")));
                 pktPrpMap.put("Seller",util.nvl(rs.getString("emp")));
                 pktPrpMap.put("Company",util.nvl(rs.getString("byrNm")));
                 pktPrpMap.put("Terms",util.nvl(rs.getString("term")));
                 pktPrpMap.put("Rap Rate",util.nvl(rs.getString("rap_rte")));
                 pktPrpMap.put("Cts",util.nvl(rs.getString("cts")));
                 pktPrpMap.put("Net Price",util.nvl(rs.getString("net_rte")));
                 pktPrpMap.put("Net Dis",util.nvl(rs.getString("net_dis")));
                 pktPrpMap.put("Offer Rte",util.nvl(rs.getString("req_quot")));
                 pktPrpMap.put("Offer Dis",util.nvl(rs.getString("r_dis")));
                 pktPrpMap.put("Bid Rte",util.nvl(rs.getString("req_quot")));
                 pktPrpMap.put("Bid Dis",util.nvl(rs.getString("r_dis")));
                 pktPrpMap.put("Base Price",util.nvl(rs.getString("mem_prc")));
                 pktPrpMap.put("Base Dis",util.nvl(rs.getString("mr_dis")));
                 pktPrpMap.put("Byr Price",util.nvl(rs.getString("byr_prc")));
                 pktPrpMap.put("Byr Dis",util.nvl(rs.getString("byr_dis")));
                 pktPrpMap.put("Term PCT",util.nvl(rs.getString("ttl_trm_pct")));
                 pktPrpMap.put("Broker1Comm",util.nvl(rs.getString("mbrk2_comm")));
                 for (int v = 0; v < vwprpLst.size(); v++) {
                 String vwPrp = (String)vwprpLst.get(v);
                 String fldName = vwPrp;
                 if(vwPrp.toUpperCase().equals("H&A"))
                 fldName = "H_A";
                 
                 if(vwPrp.toUpperCase().equals("COMMENT"))
                 fldName = "COM1";
                 
                 if(vwPrp.toUpperCase().equals("REMARKS"))
                 fldName = "REM1";
                 
                 if(vwPrp.toUpperCase().equals("COL-DESC"))
                 fldName = "COL_DESC";
                 
                 if(vwPrp.toUpperCase().equals("COL-SHADE"))
                 fldName = "COL_SHADE";
                 
                 if(vwPrp.toUpperCase().equals("FL-SHADE"))
                 fldName = "FL_SHADE";
                 
                 if(vwPrp.toUpperCase().equals("STK-CTG"))
                 fldName = "STK_CTG";
                 
                 if(vwPrp.toUpperCase().equals("STK-CODE"))
                 fldName = "STK_CODE";
                 
                 if(vwPrp.toUpperCase().equals("SUBSIZE"))
                 fldName = "SUBSIZE1";
                 
                 if(vwPrp.toUpperCase().equals("SIZE"))
                 fldName = "SIZE1";
                 
                 if(vwPrp.toUpperCase().equals("MIX_SIZE"))
                 fldName = "MIX_SIZE1";
                 
                 if(vwPrp.toUpperCase().equals("MEM_COMMENT"))
                 fldName = "MEM_COM1";
                 
                 if(vwPrp.toUpperCase().equals("STK-CTG"))
                 fldName = "STK_CTG";
                 
                 if(vwPrp.toUpperCase().equals("CRN-OP"))
                 fldName = "CRN_OP";
                 
                 if(vwPrp.toUpperCase().equals("CRTWT"))
                 fldName = "cts";
                 
                 if(vwPrp.toUpperCase().equals("RAP_DIS"))
                 fldName = "r_dis";
                 
                
                     if(vwPrp.toUpperCase().equals("RTE"))
                     fldName = "mem_prc";
                 
                 
                 if (vwPrp.toUpperCase().equals("RAP_RTE"))
                 fldName = "raprte";
                 if (vwPrp.toUpperCase().equals("CERT NO."))
                 fldName = "certno";
                 
                 String fldVal = util.nvl((String)rs.getString(fldName));
                 
                 pktPrpMap.put(vwPrp, fldVal);
                 }
                 pktList.add(pktPrpMap);
             }
            rs.close();
                pst.close();
                if(cnt.equals("kj")){
                    itemHdr.add("Company");
                    itemHdr.add("Seller");
                    itemHdr.add("Packet Id");
                    itemHdr.add("Alc Type");
                   int rteinx= vwprpLst.indexOf("RTE") ;
                     vwprpLst.add(rteinx+3, "Bid Dis");
                    vwprpLst.add(rteinx+4, "Bid Rte");
                    vwprpLst.add(rteinx+5, "Byr Dis");
                    vwprpLst.add(rteinx+6, "Byr Price");
                }else{
                    itemHdr.add("Alc Memo");
                    itemHdr.add("Packet Id");
                    itemHdr.add("Offer Date");
                    itemHdr.add("Alc Date");
                    itemHdr.add("Alc Status");
                    itemHdr.add("Alc Type");
                    itemHdr.add("Sale Ex");
                    itemHdr.add("Buyer");
                    itemHdr.add("Terms");
                    itemHdr.add("Term PCT");
                    itemHdr.add("Broker1Comm");
                    itemHdr.add("Cts");
                    itemHdr.add("Rap Rate");
                    itemHdr.add("Net Dis");
                    itemHdr.add("Net Price");
                    itemHdr.add("Offer Dis");
                    itemHdr.add("Offer Rte");
                    itemHdr.add("Base Dis");
                    itemHdr.add("Base Price");
                    itemHdr.add("Byr Dis");
                    itemHdr.add("Byr Price");
                }
                    itemHdr.addAll(vwprpLst);
            session.setAttribute("pktListPPC", pktList);
            session.setAttribute("itemHdrPPC", itemHdr);
            req.setAttribute("view", "Y");  
            udf.reset();
            util.updAccessLog(req,res,"Rpt Action", "ppAlcReport end");
            return am.findForward("ppAlcReport");
            }
        }
    
    public ActionForward createPpcXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Rpt Action", "createPpcXL start");
        HashMap dbinfo = info.getDmbsInfoLst();
        String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
        int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        ArrayList pktList = (ArrayList)session.getAttribute("pktListPPC");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdrPPC");
        int pktListsz=pktList.size();
     
            HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
            String contentTypezip = "application/zip";
            String fileNmzip = "AllocationReport_"+util.getToDteTime();
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
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "AllocationReport_"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        hwb.write(out);
        out.flush();
        out.close();
        }
            util.updAccessLog(req,res,"Rpt Action", "createPpcXL end");
        return null;
        }
    }
    
    
    public ActionForward loadRecptDt(ActionMapping am, ActionForm af, HttpServletRequest req,
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
        util.updAccessLog(req,res,"Mfg Inward Action", "load start");
        ReportForm udf = (ReportForm)af;
        udf.resetALL();
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "RECPTDT_VW","RECPTDT_VW");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("RECPT_DT");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("RECPT_DT");
        allPageDtl.put("RECPT_DT",pageDtl);
        }
        info.setPageDetails(allPageDtl); 
            
        return am.findForward("loadRecptDt");
        }
    }
    
    public ActionForward fetchRecptDt(ActionMapping am, ActionForm af, HttpServletRequest req,
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
        util.updAccessLog(req,res,"Mfg Inward Action", "load start");
        ReportForm udf = (ReportForm)af;
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "RECPTDT_VW","RECPTDT_VW");
        String frmDte = util.nvl((String)udf.getValue("frmDte"));
        String toDte = util.nvl((String)udf.getValue("toDte"));
        udf.resetALL();
        ArrayList ary = new ArrayList();
        ArrayList rowlprpLst = new ArrayList();
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("RECPT_DT");
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        HashMap dataDtl = new HashMap();
        String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
        String rowLprp="IFRS",columnLprp="PUR";
        pageList=(ArrayList)pageDtl.get("LEVEL1LPRP");
        if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            rowLprp=(String)pageDtlMap.get("fld_nme");
            }
        }
        int indexrow = vwPrpLst.indexOf(rowLprp)+1;
        String rowPrp = util.prpsrtcolumn("prp", indexrow);  
        int indexcolumn = vwPrpLst.indexOf(columnLprp)+1;
        String columnPrp = util.prpsrtcolumn("prp", indexcolumn);    
        int indexMfgCts = vwPrpLst.indexOf("MFG_CTS")+1;
        String mfgCtsPrp = util.prpsrtcolumn("prp", indexMfgCts); 
            
        ary = new ArrayList();
        ary.add(frmDte);
        ary.add(toDte);
        ary.add("RECPTDT_VW");
        String proc="DP_RECPT_DT(pDteFrm =>to_date(?, 'dd-mm-rrrr'), pDteTo =>to_date(?, 'dd-mm-rrrr'),pMdl =>?)";
        int ct = db.execCall("DP_RECPT_DT", proc, ary);
        
            String dtlQ = "select to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts,to_char(trunc(sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2),'99999990.00') mfgcts,\n" + 
            "nvl("+rowPrp+",'NA') rowC,decode("+columnPrp+",'YES','YES','NO') columnC\n" + 
            "from gt_srch_rslt\n" + 
            "group by nvl("+rowPrp+",'NA'),decode("+columnPrp+",'YES','YES','NO')";
             ary = new ArrayList();  
         ArrayList outLst = db.execSqlLst("Collection", dtlQ, ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
                 String row=util.nvl(rs.getString("rowC"));
                 String column=util.nvl(rs.getString("columnC"));
                 if(!rowlprpLst.contains(row)){
                     rowlprpLst.add(row);
                 }
                 dataDtl.put(row+"_"+column+"_CTS",util.nvl(rs.getString("cts")));
                 dataDtl.put(row+"_"+column+"_MFGCTS",util.nvl(rs.getString("mfgcts")));
             }
             rs.close(); 
            pst.close();
            
            String rowQ = "select to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts,to_char(trunc(sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2),'99999990.00') mfgcts,\n" + 
            "nvl("+rowPrp+",'NA') rowC\n" + 
            "from gt_srch_rslt\n" + 
            "group by nvl("+rowPrp+",'NA')";
             ary = new ArrayList();  
           outLst = db.execSqlLst("Collection", rowQ, ary);
           pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
                 String row=util.nvl(rs.getString("rowC"));
                 dataDtl.put(row+"_CTS",util.nvl(rs.getString("cts")));
                 dataDtl.put(row+"_MFGCTS",util.nvl(rs.getString("mfgcts")));
             }
             rs.close();
            pst.close();
            
            String columnQ = "select to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts,to_char(trunc(sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2),'99999990.00') mfgcts,\n" + 
            "decode("+columnPrp+",'YES','YES','NO') columnC\n" + 
            "from gt_srch_rslt\n" + 
            "group by decode("+columnPrp+",'YES','YES','NO')";
             ary = new ArrayList();  
          outLst = db.execSqlLst("Collection", columnQ, ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
                 String column=util.nvl(rs.getString("columnC"));
                 dataDtl.put(column+"_CTS",util.nvl(rs.getString("cts")));
                 dataDtl.put(column+"_MFGCTS",util.nvl(rs.getString("mfgcts")));
             }
             rs.close();
            pst.close();
            String ttQ = "select to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts,to_char(trunc(sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2),'99999990.00') mfgcts\n" + 
            "from gt_srch_rslt";
             ary = new ArrayList();  
          outLst = db.execSqlLst("Collection", ttQ, ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
                 dataDtl.put("CTS",util.nvl(rs.getString("cts")));
                 dataDtl.put("MFGCTS",util.nvl(rs.getString("mfgcts")));
             }
             rs.close();
            pst.close();
             req.setAttribute("dataDtl", dataDtl);
             req.setAttribute("rowlprpLst", rowlprpLst);
             req.setAttribute("view", "Y");
        return am.findForward("loadRecptDt");
        }
    }
    
    public ActionForward pktDtlRecptDt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
           util.updAccessLog(req,res,"Mfg Inward Action", "pktDtlRecptDt start");
        ReportForm udf = (ReportForm)af;
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "RECPTDT_VW","RECPTDT_VW");
           HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
           HashMap pageDtl=(HashMap)allPageDtl.get("RECPT_DT");
           ArrayList pageList=new ArrayList();
           HashMap pageDtlMap=new HashMap();
           String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
           String rowLprp="IFRS",columnLprp="PUR";
           pageList=(ArrayList)pageDtl.get("LEVEL1LPRP");
           if(pageList!=null && pageList.size() >0){
               for(int j=0;j<pageList.size();j++){
               pageDtlMap=(HashMap)pageList.get(j);
               rowLprp=(String)pageDtlMap.get("fld_nme");
               }
           }
           String rowLprpVal=util.nvl((String)req.getParameter("rowLprpVal"));
           String columnLprpVal=util.nvl((String)req.getParameter("columnLprpVal"));
           int indexrow = vwPrpLst.indexOf(rowLprp)+1;
           String rowPrp = util.prpsrtcolumn("prp", indexrow);  
           int indexcolumn = vwPrpLst.indexOf(columnLprp)+1;
           String columnPrp = util.prpsrtcolumn("prp", indexcolumn);    
           int indexMfgCts = vwPrpLst.indexOf("MFG_CTS")+1;
           String mfgCtsPrp = util.prpsrtcolumn("prp", indexMfgCts); 
        ArrayList pktList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        HashMap dbinfo = info.getDmbsInfoLst();
        ArrayList prpDspBlocked = info.getPageblockList();
        itemHdr.add("Sr No");
        itemHdr.add("Packet Id");
        itemHdr.add("Status");
        itemHdr.add("Qty");
        int s=1;
        for (int i = 0; i < vwPrpLst.size(); i++) {
             String prp=util.nvl((String)vwPrpLst.get(i));
             itemHdr.add(prp);
         }
           
           String conQ="";           

           if(!rowLprpVal.equals("") && !rowLprpVal.equals("ALL")){
               conQ+=" and nvl("+rowPrp+",'NA')='"+rowLprpVal+"'";
           }
           if(!columnLprpVal.equals("") && !columnLprpVal.equals("ALL")){
               if(columnLprpVal.equals("YES"))
               conQ+=" and nvl("+columnPrp+",'NO')='"+columnLprpVal+"'";
               else
               conQ+=" and nvl("+columnPrp+",'NO')<>'YES'";
           }

           String  srchQ =  " select stk_idn ,qty,quot,vnm , stt,flg,rmk tfl3,trunc(cts,2) cts,to_char(trunc(cts,3) * quot, '9999999990.00') vlu,sk1  ";
           
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

           
           String rsltQ = srchQ + " from gt_srch_rslt where 1=1 "+conQ+" order by sk1,stk_idn,cts ";
           
        ArrayList  outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
      PreparedStatement   pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
           try {
               while(rs.next()) {
                   String stkIdn = util.nvl(rs.getString("stk_idn"));
                   HashMap pktPrpMap = new HashMap();
                   pktPrpMap.put("Status", util.nvl(rs.getString("stt")));
                   String vnm = util.nvl(rs.getString("vnm"));
                   pktPrpMap.put("Packet Id",vnm);
                   pktPrpMap.put("Sr No",String.valueOf(s++));
                   pktPrpMap.put("Qty", util.nvl(rs.getString("qty")));
                   pktPrpMap.put("Cts", util.nvl(rs.getString("cts")));
                   for(int j=0; j < vwPrpLst.size(); j++){
                        String prp = (String)vwPrpLst.get(j);
                         
                         String fld="prp_";
                         if(j < 9)
                                 fld="prp_00"+(j+1);
                         else    
                                 fld="prp_0"+(j+1);
                         
                         String val = util.nvl(rs.getString(fld)) ;
                         if(prp.equals("CRTWT"))
                         val = util.nvl(rs.getString("cts"));
                         if(prp.equals("RTE"))
                         val = util.nvl(rs.getString("quot"));
                         if (prp.toUpperCase().equals("RFIDCD"))
                         val = util.nvl(rs.getString("tfl3"));
                       pktPrpMap.put(prp, val);    
                   }
                   pktList.add(pktPrpMap);
               }
               rs.close();
               pst.close();
           } catch (SQLException sqle) {

               // TODO: Add catch code
               sqle.printStackTrace();
           }
           
               int pktListsz=pktList.size();
               String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
               int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100"));
               ExcelUtil xlUtil = new ExcelUtil();
               xlUtil.init(db, util, info);
               HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
               if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
               String contentTypezip = "application/zip";
               String fileNmzip = "Recpt_dtExcel"+util.getToDteTime();
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
               OutputStream out = res.getOutputStream();
               String CONTENT_TYPE = "getServletContext()/vnd-excel";
               String fileNm = "Recpt_dtExcel"+util.getToDteTime()+".xls";
               res.setContentType(CONTENT_TYPE);
               res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
               hwb.write(out);
               out.flush();
               out.close();
               }
             return null;
       }
    }
    
    public ActionForward loadCustomScreen(ActionMapping am, ActionForm af, HttpServletRequest req,
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
        util.updAccessLog(req,res,"Mfg Inward Action", "load start");
        ReportForm udf = (ReportForm)af;
        udf.resetALL();
        GenericInterface genericInt = new GenericImpl();
        HashMap dataTbl=new HashMap();
        ArrayList gridList=new ArrayList();
        String grid1Cts="0",grid1Qty="0",grid1Vlu="0";
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("CUSTOM_SC");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("CUSTOM_SC");
            allPageDtl.put("CUSTOM_SC",pageDtl);
            }
            info.setPageDetails(allPageDtl);
        ArrayList vWPrpList=genericInt.genericPrprVw(req, res, "CUSTOM_SCREEN_VIEW","CUSTOM_SCREEN_VIEW");
        HashMap dbinfo = info.getDmbsInfoLst();
        String locval = (String)dbinfo.get("LOC"); 
        int indexloc = vWPrpList.indexOf(locval)+1;
        String locPrp = util.prpsrtcolumn("prp",indexloc);
        String locSrt =util.prpsrtcolumn("srt",indexloc); 
            int indexsalbyr = vWPrpList.indexOf("SAL_BYR")+1;
            String salbyrPrp = util.prpsrtcolumn("prp",indexsalbyr);
            int indexbgm = vWPrpList.indexOf("BGM")+1;
            String bgmPrp = util.prpsrtcolumn("prp",indexbgm);
            int indexpur = vWPrpList.indexOf("PUR")+1;
            String purPrp = util.prpsrtcolumn("prp",indexpur);
            
            int indexLAB_ISS_DTE = vWPrpList.indexOf("LAB_ISS_DTE")+1;
            String LAB_ISS_DTEPrp = util.prpsrtcolumn("prp",indexLAB_ISS_DTE);
            int indexLAB_RTN_DTE = vWPrpList.indexOf("LAB_RTN_DTE")+1;
            String LAB_RTN_DTEPrp = util.prpsrtcolumn("prp",indexLAB_RTN_DTE);
            int indexLAB_PRC = vWPrpList.indexOf("LAB_PRC")+1;
            String LAB_PRCPrp = util.prpsrtcolumn("prp",indexLAB_PRC);
            int indexRECPT_DT = vWPrpList.indexOf("RECPT_DT")+1;
            String RECPT_DTPrp = util.prpsrtcolumn("prp",indexRECPT_DT);
            int indexBSE_PRI = vWPrpList.indexOf("BSE_PRI")+1;
            String BSE_PRIPrp = util.prpsrtcolumn("prp",indexBSE_PRI);
            int indexCol = vWPrpList.indexOf("COL")+1;
            String colsrt = util.prpsrtcolumn("srt",indexCol);
            String BSE_PRIsrt = util.prpsrtcolumn("srt",indexBSE_PRI);
        int ct = db.execCall("CUSTOM_SCREEN_VIEW", "DP_CUSTOM_SCREEN('CUSTOM_SCREEN_VIEW')", new ArrayList());
        HashMap grid1ttl =new HashMap();
       
      String  dataQ="\n" + 
        "select cert_lab lab, b.grp2 , d.fld_ttl,d.dflt_val,d.srt,\n" + 
        "sum(cts) cts,count(*) qty,CEIL(sum(cts*prp_023)) vlu\n" + 
        "from gt_srch_rslt a , df_stk_stt b, df_pg c , df_pg_itm d\n" + 
        "where a.stt=b.stt and b.grp2=d.fld_nme and d.itm_typ=?  \n" + 
        "and c.mdl=? \n" + 
        "group by cert_lab , b.grp2 , d.fld_ttl,d.dflt_val,d.srt\n" + 
        "order by d.srt";
            ArrayList params = new ArrayList();
            params.add("GRP");
            params.add("CUSTOM_SC");
                
          ArrayList  outLst = db.execSqlLst("Data", dataQ, params);
          PreparedStatement   pst = (PreparedStatement)outLst.get(0);
           ResultSet rs = (ResultSet)outLst.get(1);
            double ttlCts = 0.0;
            int ttlQty=0;
            double ttlvlu=0.0;
        while (rs.next()) { 
            HashMap dtlMap=new HashMap();
            String cts= util.nvl(rs.getString("cts"),"0").trim();
            String qty=util.nvl(rs.getString("qty"),"0").trim();
            String vlu=util.nvl(rs.getString("vlu"),"0").trim();
            dtlMap.put("LAB", util.nvl(rs.getString("lab"),"0"));
            dtlMap.put("GRP", util.nvl(rs.getString("grp2"),"0"));
            dtlMap.put("FLDTTL", util.nvl(rs.getString("fld_ttl"),"0"));
            dtlMap.put("DSC", util.nvl(rs.getString("dflt_val"),"0"));
            dtlMap.put("CTS", util.nvl(rs.getString("cts"),"0"));
            dtlMap.put("QTY", util.nvl(rs.getString("qty"),"0"));
            dtlMap.put("VLU", util.nvl(rs.getString("vlu"),"0"));
            
            ttlCts=ttlCts+Double.parseDouble(cts);
            ttlvlu=ttlvlu+Double.parseDouble(vlu);
            ttlQty=ttlQty+Integer.parseInt(qty);
            
            gridList.add(dtlMap);
        }
        rs.close();
        pst.close();
            BigDecimal ttlBigVal = new BigDecimal(ttlvlu);
            grid1ttl.put("GRID1CTS",String.valueOf(util.roundToDecimals(ttlCts,2)));
            grid1ttl.put("GRID1QTY", String.valueOf(ttlQty));
            grid1ttl.put("GRID1VLU", String.valueOf(ttlBigVal.setScale(2, RoundingMode.HALF_EVEN)));
            dataTbl.put("GRID1TTL", grid1ttl); 
            dataTbl.put("GRID1", gridList); 
            gridList=new ArrayList();
            dataQ=  "select cert_lab lab,'SOLD' stt, cert_lab||' in Sold' process,\n" + 
            "sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 05 srt \n" + 
            "from gt_srch_rslt where stt in ('MKSL','MKSD','BRC_MKSD') group by cert_lab  order by srt";
            outLst = db.execSqlLst("Data", dataQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            double gridApsd_ttlCts = 0.0;
            int gridApsd_ttlQty=0;
            double gridApsd_ttlvlu=0.0;
            while (rs.next()) { 
                HashMap dtlMap=new HashMap();
                String cts=util.nvl(rs.getString("cts"),"0").trim();
                String qty=util.nvl(rs.getString("qty"),"0").trim();
                String vlu=util.nvl(rs.getString("vlu"),"0").trim();
                dtlMap.put("LAB", util.nvl(rs.getString("lab"),"0"));
                dtlMap.put("STT", util.nvl(rs.getString("stt"),"0"));
                dtlMap.put("PROCESS", util.nvl(rs.getString("process"),"0"));
                dtlMap.put("CTS", util.nvl(rs.getString("cts"),"0"));
                dtlMap.put("QTY", util.nvl(rs.getString("qty"),"0"));
                dtlMap.put("VLU", util.nvl(rs.getString("vlu"),"0"));
                String per="0";
                if(!grid1Cts.equals("0") && !cts.equals("0"))
                per=String.valueOf((util.roundToDecimals(((Double.parseDouble(cts)/Double.parseDouble(grid1Cts))*100),2)));    
                dtlMap.put("PER", per);
                gridList.add(dtlMap);
                gridApsd_ttlCts=gridApsd_ttlCts+Double.parseDouble(cts);
                gridApsd_ttlvlu=gridApsd_ttlvlu+Double.parseDouble(vlu);
                gridApsd_ttlQty=gridApsd_ttlQty+Integer.parseInt(qty);
            }
            rs.close();
            pst.close();
            BigDecimal gridApsd_bigttlvlu = new BigDecimal(gridApsd_ttlvlu);
            dataTbl.put("GRIDAPSDCTS",String.valueOf(util.roundToDecimals(gridApsd_ttlCts,2)));
            dataTbl.put("GRIDAPSDQTY", String.valueOf(gridApsd_ttlQty));
            dataTbl.put("GRIDAPSDVLU", String.valueOf(gridApsd_bigttlvlu.setScale(2, RoundingMode.HALF_EVEN)));
            dataTbl.put("GRIDAPSD", gridList); 
                
            
            gridList=new ArrayList();
            dataQ="select * from (\n" + 
            "select 'RFR' stt,'RFR' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 10 srt from gt_srch_rslt where stt in ('MKRE')  \n" + 
            "union\n" + 
            "select 'SHOW' stt,"+locPrp+" process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 20 srt from gt_srch_rslt where stt in ('MKAV','MKIS', 'MKEI', 'MKWH', 'MKCS','MKWA','MKPP') and "+locPrp+" like '%SHOW%'  group by "+locPrp+"\n" + 
            "union\n" + 
            "select 'HK' stt,"+locPrp+" process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 30 srt from gt_srch_rslt where stt in ('MKAV','MKIS', 'MKEI', 'MKWH','MKWA','MKPP') and "+locPrp+"='HK'  group by "+locPrp+"\n" + 
            "union\n" + 
            "select 'MEMO' stt,'MEMO' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 35 srt from gt_srch_rslt where stt in ('MKEI')"+
            "union\n" + 
            "select 'Box Memo' stt,'Box Memo' process, sum(cts),count(*),CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 40 srt from gt_srch_rslt a1 where \n" + 
            "exists ( select 1 from jandtl b1,mjan c1 where b1.idn = c1.idn and b1.stt ='IS' and c1.RMK ='BOX' and a1.stk_idn = b1.mstk_idn)\n" + 
            "union\n" + 
            "select 'ON HOLD' stt,'ON HOLD' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 50 srt from gt_srch_rslt where stt in ('MKWH')\n" + 
            "union\n" + 
            "select 'MKCSHK' stt,Initcap("+salbyrPrp+") process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 60 srt from gt_srch_rslt where stt in ('MKCS') and "+locPrp+"='HK'  group by Initcap("+salbyrPrp+")\n" + 
            "union\n" + 
            "select 'ADVICE' stt,'ADVICE' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 70 srt from gt_srch_rslt where stt in ('SUADV')\n" + 
            "union\n" + 
            "select 'SURAT' stt,'SURAT' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 70 srt from gt_srch_rslt where stt in ('REP_IS')\n" + 
            "union\n" + 
            "select 'P.SELECTION' stt,'P.SELECTION' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 80 srt from gt_srch_rslt where stt in ('MKAV','MKIS', 'MKEI', 'MKWH', 'MKCS','MKWA','MKPP') and "+purPrp+"='YES'\n" + 
            "union\n" + 
            "select 'ON APPROVAL' stt,'ON APPROVAL' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 90 srt from gt_srch_rslt where stt in ('MKWA','MKPP') and "+purPrp+"<>'YES'\n" + 
            "union\n" + 
            "select 'BGM' stt,'BGM' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 100 srt from gt_srch_rslt where stt in ('MKAV','MKIS', 'MKEI', 'MKWH', 'MKCS','MKWA','MKPP') and "+bgmPrp+"='YES'\n" + 
            "union\n"+
            "select 'SHOW-AV' stt,'SHOW-AV' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 100 srt from gt_srch_rslt where stt in ('SHAV')\n" + 
            "union\n" + 
            "select 'AP' stt,Initcap(a."+salbyrPrp+") process, sum(a.cts) cts,count(*) qty,CEIL(sum(a.cts*a."+BSE_PRIPrp+")) vlu, 110 srt \n" + 
            "from gt_srch_rslt a \n" + 
            "where a.stt in ('MKAP','MKWA') \n" + 
            "and exists(select 1 from rule_dtl b where a."+salbyrPrp+"=b.dsc and rule_idn=1986)\n" + 
            "group by a."+salbyrPrp+"\n" + 
            ") \n" + 
            "order by srt";
          outLst = db.execSqlLst("Data", dataQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            double grid2_ttlCts = 0.0;
            int grid2_ttlQty=0;
            double grid2_ttlvlu=0.0;
            while (rs.next()) { 
                HashMap dtlMap=new HashMap();
                String cts=util.nvl(rs.getString("cts"),"0").trim();
                String qty=util.nvl(rs.getString("qty"),"0").trim();
                String vlu=util.nvl(rs.getString("vlu"),"0").trim();
                dtlMap.put("STT", util.nvl(rs.getString("stt"),"0"));
                dtlMap.put("PROCESS", util.nvl(rs.getString("process"),"0"));
                dtlMap.put("CTS", util.nvl(rs.getString("cts"),"0"));
                dtlMap.put("QTY", util.nvl(rs.getString("qty"),"0"));
                dtlMap.put("VLU", util.nvl(rs.getString("vlu"),"0"));
                String per="0";
                if(!grid1Cts.equals("0") && !cts.equals("0"))
                per=String.valueOf((util.roundToDecimals(((Double.parseDouble(cts)/Double.parseDouble(grid1Cts))*100),2)));    
                dtlMap.put("PER", per);
                gridList.add(dtlMap);
                grid2_ttlCts=grid2_ttlCts+Double.parseDouble(cts);
                grid2_ttlvlu=grid2_ttlvlu+Double.parseDouble(vlu);
                grid2_ttlQty=grid2_ttlQty+Integer.parseInt(qty);
            }
            rs.close();
            pst.close();
            BigDecimal grid2_bigttlvlu = new BigDecimal(grid2_ttlvlu);
            dataTbl.put("GRID2CTS",String.valueOf(util.roundToDecimals(grid2_ttlCts,2)));
            dataTbl.put("GRID2QTY", String.valueOf(grid2_ttlQty));
            dataTbl.put("GRID2VLU", String.valueOf(grid2_bigttlvlu.setScale(2, RoundingMode.HALF_EVEN)));
            dataTbl.put("GRID2", gridList); 
            
            gridList=new ArrayList();
            dataQ="select 'ALL' lab,'NONMKSD' stt,'Stones In '||"+locPrp+" process,"+locSrt+", sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 10 srt \n" + 
            "from gt_srch_rslt where stt not in('MKSD') group by "+locPrp+","+locSrt+" order by "+locSrt;
          outLst = db.execSqlLst("Data", dataQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            double grid3_ttlCts = 0.0;
            int grid3_ttlQty=0;
            double grid3_ttlvlu=0.0;
            while (rs.next()) { 
                HashMap dtlMap=new HashMap();
                String cts=util.nvl(rs.getString("cts"),"0").trim();
                String qty=util.nvl(rs.getString("qty"),"0").trim();
                String vlu=util.nvl(rs.getString("vlu"),"0").trim();
                dtlMap.put("LAB", util.nvl(rs.getString("lab"),"0"));
                dtlMap.put("STT", util.nvl(rs.getString("stt"),"0"));
                dtlMap.put("PROCESS", util.nvl(rs.getString("process"),"0"));
                dtlMap.put("CTS", util.nvl(rs.getString("cts"),"0"));
                dtlMap.put("QTY", util.nvl(rs.getString("qty"),"0"));
                dtlMap.put("VLU", util.nvl(rs.getString("vlu"),"0"));
                String per="0";
                if(!grid1Cts.equals("0") && !cts.equals("0"))
                per=String.valueOf((util.roundToDecimals(((Double.parseDouble(cts)/Double.parseDouble(grid1Cts))*100),2)));    
                dtlMap.put("PER", per);
                grid3_ttlCts=grid3_ttlCts+Double.parseDouble(cts);
                grid3_ttlvlu=grid3_ttlvlu+Double.parseDouble(vlu);
                grid3_ttlQty=grid3_ttlQty+Integer.parseInt(qty);
                gridList.add(dtlMap);
            }
            rs.close();
            pst.close();
            BigDecimal grid3_bigttlvlu = new BigDecimal(grid3_ttlvlu);
            dataTbl.put("GRID3CTS",String.valueOf(util.roundToDecimals(grid3_ttlCts,2)));
            dataTbl.put("GRID3QTY", String.valueOf(grid3_ttlQty));
            dataTbl.put("GRID3VLU", String.valueOf(grid3_bigttlvlu.setScale(2, RoundingMode.HALF_EVEN)));
        dataTbl.put("GRID3", gridList); 

            gridList=new ArrayList();
            dataQ="select * from ( \n" + 
            "select 'GIA' lab,'LAB_RT' stt,'GIA' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 05 srt from gt_srch_rslt where "+LAB_RTN_DTEPrp+"=to_char(sysdate, 'dd-MON-rrrr') and cert_lab='GIA'  \n" + 
            "union\n" + 
            "select 'IGI' lab,'LAB_RT' stt,'IGI' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 10 srt from gt_srch_rslt where "+LAB_RTN_DTEPrp+"=to_char(sysdate, 'dd-MON-rrrr') and cert_lab='IGI'  \n" + 
            "union\n" + 
            "select 'HRD' lab,'LAB_RT' stt,'HRD' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 20 srt from gt_srch_rslt where "+LAB_RTN_DTEPrp+"=to_char(sysdate, 'dd-MON-rrrr') and cert_lab='HRD'  \n" + 
            "union\n" + 
            "select 'GIA' lab,'LAB_IS' stt,' Send '||'GIA' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 40 srt from gt_srch_rslt where "+LAB_ISS_DTEPrp+"=to_char(sysdate, 'dd-MON-rrrr') and "+LAB_PRCPrp+"='GIA'\n" + 
            "union\n" + 
            "select 'IGI' lab,'LAB_IS' stt,' Send '||'IGI' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 50 srt from gt_srch_rslt where "+LAB_ISS_DTEPrp+"=to_char(sysdate, 'dd-MON-rrrr') and "+LAB_PRCPrp+"='IGI' \n" + 
            "union\n" + 
            "select 'HRD' lab,'LAB_IS' stt,' Send '||'HRD' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 60 srt from gt_srch_rslt where "+LAB_ISS_DTEPrp+"=to_char(sysdate, 'dd-MON-rrrr') and "+LAB_PRCPrp+"='HRD'\n" + 
            "union\n" +  
            "select 'GEC' lab,'MFG' stt,'GEC' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 80 srt from gt_srch_rslt where "+RECPT_DTPrp+"=to_char(sysdate, 'dd-MON-rrrr') and "+purPrp+"<>'YES'\n" + 
            ")  \n" + 
            "order by srt";
          outLst = db.execSqlLst("Data", dataQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            double grid4_ttlCts = 0.0;
            int grid4_ttlQty=0;
            double grid4_ttlvlu=0.0;
            while (rs.next()) { 
                HashMap dtlMap=new HashMap();
                String cts=util.nvl(rs.getString("cts"),"0").trim();
                String qty=util.nvl(rs.getString("qty"),"0").trim();
                String vlu=util.nvl(rs.getString("vlu"),"0").trim();
                dtlMap.put("LAB", util.nvl(rs.getString("lab"),"0"));
                dtlMap.put("STT", util.nvl(rs.getString("stt"),"0"));
                dtlMap.put("PROCESS", util.nvl(rs.getString("process"),"0"));
                dtlMap.put("CTS", util.nvl(rs.getString("cts"),"0"));
                dtlMap.put("QTY", util.nvl(rs.getString("qty"),"0"));
                dtlMap.put("VLU", util.nvl(rs.getString("vlu"),"0"));
                grid4_ttlCts=grid4_ttlCts+Double.parseDouble(cts);
                grid4_ttlvlu=grid4_ttlvlu+Double.parseDouble(vlu);
                grid4_ttlQty=grid4_ttlQty+Integer.parseInt(qty);
                gridList.add(dtlMap);
            }
            rs.close();
            pst.close();
            BigDecimal grid4_bigttlvlu = new BigDecimal(grid4_ttlvlu);
            dataTbl.put("GRID4CTS",String.valueOf(util.roundToDecimals(grid4_ttlCts,2)));
            dataTbl.put("GRID4QTY", String.valueOf(grid4_ttlQty));
            dataTbl.put("GRID4VLU", String.valueOf(grid4_bigttlvlu.setScale(2, RoundingMode.HALF_EVEN)));
            dataTbl.put("GRID4", gridList);
            
            gridList=new ArrayList();
            dataQ="select * from (  \n" + 
            "select 'MEMO' lab,'MKEI' stt,'MEMO' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 10 srt \n" + 
            "from gt_srch_rslt a1 where a1.stt='MKEI' and\n" + 
            "exists ( select 1 from jandtl b1,mjan c1 where b1.idn = c1.idn and b1.stt ='IS' and b1.typ='E' and a1.stk_idn = b1.mstk_idn and trunc(b1.dte)=trunc(sysdate))\n" + 
            "union\n" + 
            "select 'ADVICE' lab,'ADVICE' stt,'ADVICE' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 20 srt from gt_srch_rslt where stt in ('SUADV') and "+RECPT_DTPrp+"=to_char(sysdate, 'dd-MON-rrrr')\n" + 
            "union\n" + 
            "select 'PURCHASE' lab,'PURCHASE' stt,'PURCHASE' process, sum(cts) cts,count(*) qty,CEIL(sum(cts*"+BSE_PRIPrp+")) vlu, 30 srt from gt_srch_rslt where "+RECPT_DTPrp+"=to_char(sysdate, 'dd-MON-rrrr') and "+purPrp+"='YES'\n" + 
            ")   \n" + 
            "order by srt";
          outLst = db.execSqlLst("Data", dataQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            double grid5_ttlCts = 0.0;
            int grid5_ttlQty=0;
            double grid5_ttlvlu=0.0;
            while (rs.next()) { 
                HashMap dtlMap=new HashMap();
                String cts=util.nvl(rs.getString("cts"),"0").trim();
                String qty=util.nvl(rs.getString("qty"),"0").trim();
                String vlu=util.nvl(rs.getString("vlu"),"0").trim();
                dtlMap.put("LAB", util.nvl(rs.getString("lab"),"0"));
                dtlMap.put("STT", util.nvl(rs.getString("stt"),"0"));
                dtlMap.put("PROCESS", util.nvl(rs.getString("process"),"0"));
                dtlMap.put("CTS", util.nvl(rs.getString("cts"),"0"));
                dtlMap.put("QTY", util.nvl(rs.getString("qty"),"0"));
                dtlMap.put("VLU", util.nvl(rs.getString("vlu"),"0"));
                grid5_ttlCts=grid5_ttlCts+Double.parseDouble(cts);
                grid5_ttlvlu=grid5_ttlvlu+Double.parseDouble(vlu);
                grid5_ttlQty=grid5_ttlQty+Integer.parseInt(qty);
                gridList.add(dtlMap);
            }
            rs.close();
            pst.close();
            BigDecimal grid5_bigttlvlu = new BigDecimal(grid5_ttlvlu);
            dataTbl.put("GRID5CTS",String.valueOf(util.roundToDecimals(grid5_ttlCts,2)));
            dataTbl.put("GRID5QTY", String.valueOf(grid5_ttlQty));
            dataTbl.put("GRID5VLU", String.valueOf(grid5_bigttlvlu.setScale(2, RoundingMode.HALF_EVEN)));
            dataTbl.put("GRID5", gridList);
            int grid6ttlQty=0;
            double grid6ttlCts=0.0;
            double grid6ttlRate=0.0;
            gridList=new ArrayList();
            dataQ="select 'Fancy Color' typ, count(*) cnt, sum(qty) qty, sum(cts) cts, sum(cts*"+BSE_PRIsrt+") rte\n" + 
            "from gt_srch_rslt\n" + 
            "where \n" + 
            " pkt_ty = 'NR' and stt not in ('PCHK', 'MKSD','BRC_MKSD') and stt not like '%SUS' \n" + 
            "and "+colsrt+" >=190\n" + 
            "union\n" + 
            "select 'Dossiers' typ, count(*) cnt, sum(qty) qty, sum(cts) cts, sum(cts*"+BSE_PRIsrt+") rte\n" + 
            "from gt_srch_rslt\n" + 
            "where \n" + 
            " pkt_ty = 'NR' and stt not in ('PCHK', 'MKSD','BRC_MKSD') and stt not like '%SUS' \n" + 
            "and cts < 1.00  and "+colsrt+" < 190";
          outLst = db.execSqlLst("Data", dataQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while (rs.next()) { 
                HashMap dtlMap=new HashMap();
                String qty = util.nvl(rs.getString("qty"),"0");
                String cts = util.nvl(rs.getString("cts"),"0");
                String rte = util.nvl(rs.getString("rte"),"0");
                dtlMap.put("Type", util.nvl(rs.getString("typ")));
                dtlMap.put("Cnt", util.nvl(rs.getString("cnt"),"0"));
                dtlMap.put("QTY", util.nvl(rs.getString("qty"),"0"));
                dtlMap.put("CTS", util.nvl(rs.getString("cts"),"0"));
                dtlMap.put("RTE", util.nvl(rs.getString("rte"),"0"));
                gridList.add(dtlMap);
                grid6ttlCts=grid6ttlCts+Double.parseDouble(cts);
                grid6ttlRate=grid6ttlRate+Double.parseDouble(rte);
                grid6ttlQty=grid6ttlQty+Integer.parseInt(qty);
            }
            rs.close();
            pst.close();
            BigDecimal grid6bigttlRate = new BigDecimal(grid6ttlRate);
            dataTbl.put("GRID6CTS",String.valueOf(util.roundToDecimals((grid6ttlCts),2)));
            dataTbl.put("GRID6QTY", String.valueOf(grid6ttlQty));
            dataTbl.put("GRID6RTE", String.valueOf(grid6bigttlRate.setScale(2, RoundingMode.HALF_EVEN)));
            rs.close();
            dataTbl.put("GRID6", gridList); 
            
            
        req.setAttribute("customdataTbl", dataTbl);
        return am.findForward("loadCustomScreen");
        }
    }
    
    public ActionForward loadPktCustomScreen(ActionMapping am, ActionForm af,
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
        util.updAccessLog(req,res,"Daily Sale Report", "pktDtl start");
        ReportForm udf = (ReportForm)af;
        GenericInterface genericInt = new GenericImpl();
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        ArrayList ary = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        ArrayList vWPrpList=genericInt.genericPrprVw(req, res, "CUSTOM_SCREEN_VIEW","CUSTOM_SCREEN_VIEW");
        String grid = util.nvl(req.getParameter("grid"));
        String lab = util.nvl(req.getParameter("lab"));
        String stt = util.nvl(req.getParameter("stt"));
        String process = util.nvl(req.getParameter("process"));
            int cnt=1;
            HashMap dbinfo = info.getDmbsInfoLst();
            String locval = (String)dbinfo.get("LOC"); 
            int indexloc = vWPrpList.indexOf(locval)+1;
            String locPrp = util.prpsrtcolumn("prp",indexloc);
            String locSrt =util.prpsrtcolumn("srt",indexloc); 
                int indexsalbyr = vWPrpList.indexOf("SAL_BYR")+1;
                String salbyrPrp = util.prpsrtcolumn("prp",indexsalbyr);
                int indexbgm = vWPrpList.indexOf("BGM")+1;
                String bgmPrp = util.prpsrtcolumn("prp",indexbgm);
                int indexpur = vWPrpList.indexOf("PUR")+1;
                String purPrp = util.prpsrtcolumn("prp",indexpur);
            int indexLAB_ISS_DTE = vWPrpList.indexOf("LAB_ISS_DTE")+1;
            String LAB_ISS_DTEPrp = util.prpsrtcolumn("prp",indexLAB_ISS_DTE);
            int indexLAB_RTN_DTE = vWPrpList.indexOf("LAB_RTN_DTE")+1;
            String LAB_RTN_DTEPrp = util.prpsrtcolumn("prp",indexLAB_RTN_DTE);
            int indexLAB_PRC = vWPrpList.indexOf("LAB_PRC")+1;
            String LAB_PRCPrp = util.prpsrtcolumn("prp",indexLAB_PRC);
            int indexRECPT_DT = vWPrpList.indexOf("RECPT_DT")+1;
            String RECPT_DTPrp = util.prpsrtcolumn("prp",indexRECPT_DT);
            int indexBSE_PRI = vWPrpList.indexOf("BSE_PRI")+1;
            String BSE_PRIPrp = util.prpsrtcolumn("prp",indexBSE_PRI);
            int indexCol = vWPrpList.indexOf("COL")+1;
            String colsrt = util.prpsrtcolumn("srt",indexCol);
        itemHdr.add("SR NO");
        itemHdr.add("VNM");
        itemHdr.add("STT");
        for(int i=0;i<vWPrpList.size();i++){
                String prp=util.nvl((String)vWPrpList.get(i));
                if(prpDspBlocked.contains(prp)){
                }else if(!itemHdr.contains(prp)){
                itemHdr.add(prp);
                }              
        }
            String  srchQ =  " select stk_idn,vnm,"+BSE_PRIPrp+" vlu,a1.stt,to_char(cts,'99999999990.99') cts ";
            for (int i = 0; i < vWPrpList.size(); i++) {
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
            String rsltQ = srchQ + " from gt_srch_rslt a1 where 1=1";
            if(grid.equals("GRID1")){
               if(stt.equals("ALL"))
                   rsltQ+=" and stt not in ('MKSL','MKSD','BRC_MKSD')";
               else
                rsltQ = srchQ + " from  gt_srch_rslt a1, df_stk_stt b where 1=1\n" + 
            "  and a1.stt=b.stt and b.grp2 in ('"+stt+"') and cert_lab in ('"+lab+"') "; 
                   
            }else if(grid.equals("GRID2")){
                if(stt.equals("RFR")){
                    rsltQ+=" and stt in('MKRE')";
                }
                if(stt.equals("SHOW")){
                    rsltQ+=" and stt in ('MKAV','MKIS', 'MKEI', 'MKWH', 'MKCS','MKWA','MKPP') and prp_006 like '%SHOW%'";
                    rsltQ+=" and "+locPrp+"='"+process+"'";
                }
                if(stt.equals("HK")){
                    rsltQ+=" and stt in ('MKAV','MKIS', 'MKEI', 'MKWH','MKWA','MKPP')";
                    rsltQ+=" and "+locPrp+"='HK'";
                }
                if(stt.equals("MEMO")){
                    rsltQ+=" and stt in ('MKEI')";
                }
                if(stt.equals("Box Memo")){
                    rsltQ+=" and  exists ( select 1 from jandtl b1,mjan c1 where b1.idn = c1.idn and b1.stt ='IS' and c1.RMK ='BOX' and a1.stk_idn = b1.mstk_idn)";
                }
                if(stt.equals("ON HOLD")){
                    rsltQ+=" and  stt in ('MKWH')";
                }
                if(stt.equals("MKCSHK")){
                    rsltQ+=" and  stt in ('MKCS') and "+locPrp+"='HK'";
                    rsltQ+=" and Initcap("+salbyrPrp+")='"+process+"'";
                }
                if(stt.equals("ADVICE")){
                    rsltQ+=" and  stt in ('SUADV')";
                }
                if(stt.equals("P.SELECTION")){
                    rsltQ+=" and  stt in ('MKAV','MKIS', 'MKEI', 'MKWH', 'MKCS','MKWA','MKPP') and "+purPrp+"='YES'";
                }
                if(stt.equals("ON APPROVAL")){
                    rsltQ+=" and  stt in ('MKWA','MKPP') and "+purPrp+"<>'YES'";
                }
                if(stt.equals("BGM")){
                    rsltQ+=" and  stt in ('MKAV','MKIS', 'MKEI', 'MKWH', 'MKCS','MKWA','MKPP') and "+bgmPrp+"='YES'";
                }
                if(stt.equals("SURAT")){
                    rsltQ+=" and  stt in ('REP_IS') ";
                }
                if(stt.equals("SHOW-AV")){
                    rsltQ+=" and  stt in ('SHAV') ";
                }
                if(stt.equals("AP")){
                    rsltQ+=" and  a1.stt in ('MKAP','MKWA') \n" + 
                    "and exists(select 1 from rule_dtl b where Initcap(a1."+salbyrPrp+")=Initcap(b.dsc) and rule_idn=1986)";
                }
            }else if(grid.equals("GRIDAPSD")){ 
            
                if(stt.equals("APPROVED")){
                    rsltQ+=" and  stt in ('MKWA','MKAP') ";
                }
                
                if(stt.equals("SOLD")){
                    rsltQ+=" and  stt in ('MKSD','MKSL','BRC_MKSD') ";
                }
                if(!lab.equals("ALL")){
                    rsltQ+=" and cert_lab='"+lab+"'";
                }
            }else if(grid.equals("GRID4")){
                if(stt.equals("LAB_RT")){
                    rsltQ+=" and "+LAB_RTN_DTEPrp+"=to_char(sysdate, 'dd-MON-rrrr')";
                    rsltQ+=" and cert_lab='"+lab+"'";
                }
                if(stt.equals("LAB_IS")){
                    rsltQ+=" and "+LAB_ISS_DTEPrp+"=to_char(sysdate, 'dd-MON-rrrr')";
                    rsltQ+=" and "+LAB_PRCPrp+"='"+lab+"'";
                }
                if(stt.equals("MFG")){
                    rsltQ+=" and "+RECPT_DTPrp+"=to_char(sysdate, 'dd-MON-rrrr')";
                    rsltQ+=" and "+purPrp+"<>'YES'";
                }
            }else if(grid.equals("GRID5")){
                if(stt.equals("MKEI")){
                    rsltQ+=" stt='MKEI' and\n" + 
                    "exists ( select 1 from jandtl b1,mjan c1 where b1.idn = c1.idn and b1.stt ='IS' and b1.typ='E' and stk_idn = b1.mstk_idn and trunc(b1.dte)=trunc(sysdate))";
                }
                if(stt.equals("ADVICE")){
                    rsltQ+=" and  stt in ('SUADV')";
                    rsltQ+=" and "+RECPT_DTPrp+"=to_char(sysdate, 'dd-MON-rrrr')";
                }
                if(stt.equals("PURCHASE")){
                    rsltQ+=" and "+RECPT_DTPrp+"=to_char(sysdate, 'dd-MON-rrrr')";
                    rsltQ+=" and "+purPrp+"='YES'";
                }
             }else if(grid.equals("GRID6")){
                 if(stt.equals("Dossiers")){
                     rsltQ+=" and pkt_ty = 'NR' and stt not in ('PCHK', 'MKSD','BRC_MKSD') and stt not like '%SUS' and  "+colsrt+" < 190 and cts < 1.00 ";

                 }else{
                     rsltQ+=" and pkt_ty = 'NR' and stt not in ('PCHK', 'MKSD','BRC_MKSD') and stt not like '%SUS' and  "+colsrt+" >= 190 ";

                 }
            
             }else{
                rsltQ+=" and stt not in('MKSD','BRC_MKSD')";
                process=process.replaceAll("Stones In ", "").trim();
                if(process.equals(""))
                rsltQ+=" and "+locPrp+" is null";
                else
                rsltQ+=" and "+locPrp+"='"+process+"'";
            }
         ArrayList outLst = db.execSqlLst("search Result", rsltQ+" order by sk1,stk_idn,cts", new ArrayList());
         PreparedStatement  pst = (PreparedStatement)outLst.get(0);
        ResultSet  rs = (ResultSet)outLst.get(1);
            try {
                while(rs.next()) {                  
                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap.put("SR NO", String.valueOf(cnt++));
                    pktPrpMap.put("STT", util.nvl(rs.getString("stt")));
                    String vnm = util.nvl(rs.getString("vnm"));
                        String upr = util.nvl(rs.getString("vlu"));
                        String stk_idn = util.nvl(rs.getString("stk_idn"));
                    pktPrpMap.put("VNM",vnm);
                        pktPrpMap.put("upr",upr);
                        pktPrpMap.put("stk_idn",stk_idn);                 

                    for(int j=0; j < vWPrpList.size(); j++){
                         String prp = (String)vWPrpList.get(j);
                          
                          String fld="prp_";
                          if(j < 9)
                                  fld="prp_00"+(j+1);
                          else    
                                  fld="prp_0"+(j+1);
                          
                          String val = util.nvl(rs.getString(fld)) ;
                          if(prp.equals("CRTWT"))
                          val = util.nvl(rs.getString("cts"));
                            pktPrpMap.put(prp, val);
                             }
                                  
                        pktDtlList.add(pktPrpMap);
                    }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {

                // TODO: Add catch code
                sqle.printStackTrace();
            }
       session.setAttribute("pktList", pktDtlList);
       session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtl end");
        return am.findForward("pktDtl");
        }
    }
    
    public ActionForward loadHhBsAccessLog(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "fetchloc start");
            ReportForm udf = (ReportForm)af;
           
            String  toDte = util.nvl((String)udf.getValue("todte"),util.getBackDate("dd-MM-yyyy", 0));
            String  fromDte = util.nvl((String)udf.getValue("frmdte"),util.getBackDate("dd-MM-yyyy", 2));
            
            String   qfromDte = "to_date('"+fromDte+"' , 'dd-mm-yyyy')";
            String   qtoDte = "to_date('"+toDte+"' , 'dd-mm-yyyy')";
            
            String  hhStatus = util.nvl((String)udf.getValue("hhStatus"));
            String  bpfStatus = util.nvl((String)udf.getValue("bpfStatus"));
           
            
            String  pgSql="";
            
                if(hhStatus.equals("") && bpfStatus.equals("")){
                        pgSql = " and a.pg in ('loadBPF','loadHH')";
                        udf.setValue("hhStatus", hhStatus);
                        udf.setValue("bpfStatus", bpfStatus);
                }else if(!hhStatus.equals("") && bpfStatus.equals("")){
                        pgSql = " and a.pg in ('loadHH')";
                        udf.setValue("hhStatus", hhStatus);
             }else if(hhStatus.equals("") && !bpfStatus.equals("")){
                        pgSql = " and a.pg in ('loadBPF')";
                        udf.setValue("bpfStatus", bpfStatus);
                    }else if(!hhStatus.equals("") && !bpfStatus.equals("")){
                        pgSql = " and a.pg in ('loadBPF','loadHH')";
                        udf.setValue("hhStatus", hhStatus);
                        udf.setValue("bpfStatus", bpfStatus);
                    }
            
            
           String dataQ="select count(*) cnt, decode(a.pg,'loadBPF','BPF','loadHH','Happy Hour') pgtyp,byr.get_nm(d.nme_idn) byr,b.usr_id ,d.nme_idn from\n" + 
           "web_access_log a,web_login_log b,web_usrs c,nmerel d,mnme e\n" + 
           "where a.log_id=b.log_id\n" + 
           "and B.USR_ID=c.usr_id\n" + 
           "and c.rel_idn=d.nmerel_idn\n" + 
           "and d.nme_idn=e.nme_idn\n" + 
           "and d.vld_dte is null\n" + 
           "and e.vld_dte is null "+pgSql+" \n" + 
           "and trunc(b.dt_tm) between "+qfromDte+" and "+qtoDte+"\n" + 
           "Group By A.Pg,Byr.Get_Nm(D.Nme_Idn),B.Usr_Id,D.nme_idn\n" + 
           "Order By a.pg,Cnt Desc ";
            System.out.println(dataQ);   
          ArrayList outLst = db.execSqlLst("Data", dataQ, new ArrayList());
          PreparedStatement  pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
            ArrayList hhbpfList = new ArrayList();
            while (rs.next()) { 
                HashMap hhbpfMap=new HashMap();
                hhbpfMap.put("cnt", util.nvl(rs.getString("cnt")));
                hhbpfMap.put("pgtyp", util.nvl(rs.getString("pgtyp")));
                hhbpfMap.put("byr", util.nvl(rs.getString("byr")));
                hhbpfMap.put("usr_id", util.nvl(rs.getString("usr_id")));
                hhbpfMap.put("nme_idn", util.nvl(rs.getString("nme_idn")));
                hhbpfList.add(hhbpfMap);
            }
            rs.close();
            pst.close();
            System.out.print(hhbpfList.size());
            req.setAttribute("hhbpfList", hhbpfList);
            req.setAttribute("fromDte", fromDte);
            req.setAttribute("toDte", toDte);
            udf.setValue("hhStatus", "");
            udf.setValue("bpfStatus", "");
            udf.setValue("frmdte", fromDte);
            udf.setValue("todte", toDte);
            util.updAccessLog(req,res,"Rpt Action", "fetchloc end");
        return am.findForward("fetchHsBsAccessLog");
        }
    }
    
    public ActionForward fetchHsBsAccessDtl(ActionMapping am, ActionForm af, HttpServletRequest req,
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
            util.updAccessLog(req,res,"Rpt Action", "fetchloc start");
            ReportForm udf = (ReportForm)af;
            
            String  toDte = util.nvl(req.getParameter("todte"),util.getBackDate("dd-MM-yyyy", 0));
            String  fromDte = util.nvl(req.getParameter("frmdte"),util.getBackDate("dd-MM-yyyy",2));
            String user_id = util.nvl(req.getParameter("usr_id"));
            String nme_idn = util.nvl(req.getParameter("nme_idn"));
            String pgtyp = util.nvl(req.getParameter("pgtyp"));
            String   qfromDte = "to_date('"+fromDte+"' , 'dd-mm-yyyy')";
            String   qtoDte = "to_date('"+toDte+"' , 'dd-mm-yyyy')";
            ArrayList qary = new ArrayList();
            String dataQ="select byr.get_nm(d.nme_idn) byr,a.pg,to_char(a.dt_tm, 'dd-Mon HH24:mi:ss') dt_tm from\n" + 
            "web_access_log a,web_login_log b,web_usrs c,nmerel d,mnme e\n" + 
            "where a.log_id=b.log_id\n" + 
            "and B.USR_ID=c.usr_id\n" + 
            "and c.rel_idn=d.nmerel_idn\n" + 
            "and d.nme_idn=e.nme_idn\n" + 
            "and d.vld_dte is null and d.nme_idn= ? and b.usr_id= ? \n" + 
            "and e.vld_dte is null and a.pg in ('loadBPF','loadHH') and a.pg=decode('"+pgtyp+"','BPF','loadBPF','Happy Hour','Happy Hour',a.pg)\n" + 
            "and trunc(b.dt_tm) between "+qfromDte+" and "+qtoDte+"\n" + 
            "Order By 3 desc ";
            qary.add(nme_idn);
            qary.add(user_id);
            System.out.println(dataQ);   
          ArrayList outLst = db.execSqlLst("Data", dataQ, qary);
          PreparedStatement  pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
            ArrayList hhbpfList = new ArrayList();
            while (rs.next()) { 
                HashMap hhbpfMap=new HashMap();
                hhbpfMap.put("pg", util.nvl(rs.getString("pg")));
                hhbpfMap.put("dt_tm", util.nvl(rs.getString("dt_tm")));
                hhbpfMap.put("byr", util.nvl(rs.getString("byr")));
                hhbpfList.add(hhbpfMap);
            }
            rs.close();
            pst.close();
            req.setAttribute("hhbpfList", hhbpfList);
           
            
           
            util.updAccessLog(req,res,"Rpt Action", "fetchloc end");
        return am.findForward("fetchHsBsAccessDtl");
        }
    }

    public RptAction() {
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
                util.updAccessLog(req,res,"Rpt Action", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Rpt Action", "init");
            }
            }
            return rtnPg;
            }

}


