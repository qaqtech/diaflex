
package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import java.sql.Connection;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class IfrsActionKg extends DispatchAction {
    public IfrsActionKg() {
        super();
    }
    
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
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
        util.updAccessLog(req,res,"Ifrs Action", "load start");
        ReportForm udf = (ReportForm)af;
        udf.resetALL();
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "IFRS_OPEN_CLOSE","IFRS_OPEN_CLOSE");
        util.updAccessLog(req,res,"Ifrs Action", "load end");
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("IFRS");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("IFRS");
            allPageDtl.put("IFRS",pageDtl);
            }
            info.setPageDetails(allPageDtl); 
            HashMap bucketLmtDsc = (HashMap)session.getAttribute("bucketLmtDsc");
                    try {
                        if (bucketLmtDsc == null) {
                            bucketLmtDsc = new HashMap();
                            ResultSet rs1 =db.execSql(" Vw Lst ", "select * from ifrs_bucket_lmt",new ArrayList());
                            while (rs1.next()) {
                                bucketLmtDsc.put(util.nvl(rs1.getString("val"))+"_"+util.nvl(rs1.getString("grp")).trim(), " ("+util.nvl(rs1.getString("minnum"))+"-"+util.nvl(rs1.getString("maxnum"))+")");
                            }
                            rs1.close();
                            session.setAttribute("bucketLmtDsc", bucketLmtDsc);
                        }
            
                    } catch (SQLException sqle) {
                        // TODO: Add catch code
                        sqle.printStackTrace();
                    }
        return am.findForward("load");
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
            util.updAccessLog(req,res,"Ifrs Action", "load start");
            ReportForm udf = (ReportForm)af;
            ArrayList ary = new ArrayList();
            ArrayList shlistSingle = new ArrayList();
            ArrayList shlistMix = new ArrayList();
            HashMap dataDtl = new HashMap();
            GenericInterface genericInt = new GenericImpl();
            ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "IFRS_OPEN_CLOSE","IFRS_OPEN_CLOSE");
            HashMap dbinfo = info.getDmbsInfoLst();
            int indexMFG_CTS = vwPrpLst.indexOf("MFG_CTS")+1;
            String mfgCtsPrp = util.prpsrtcolumn("prp", indexMFG_CTS);
            int indexNRV = vwPrpLst.indexOf("NRV")+1;
            String cstNrvPrp = util.prpsrtcolumn("prp", indexNRV);
            int indexCSTRS = vwPrpLst.indexOf("CST_PER_RS")+1;
            String cstRsPrp = util.prpsrtcolumn("prp", indexCSTRS);
            int indexCSTUSD = vwPrpLst.indexOf("CST_PER_FE")+1;
            String cstUsdPrp = util.prpsrtcolumn("prp", indexCSTUSD);
            int indexACCRS = vwPrpLst.indexOf("ACC_PER_RS")+1;
            String accRsPrp = util.prpsrtcolumn("prp", indexACCRS);
            int indexACCUSD = vwPrpLst.indexOf("ACC_PER_FE")+1;
            String accUsdPrp = util.prpsrtcolumn("prp", indexACCUSD);
            String frmDte = util.nvl((String)udf.getValue("frmDte"));
            String toDte = util.nvl((String)udf.getValue("toDte"));
            String ReGenerate = util.nvl((String)udf.getValue("ReGenerate"));
            String gridby = util.nvl((String)udf.getValue("gridby"));
            if(gridby.equals("")){
                gridby = util.nvl((String)udf.getValue("gridbyExisting"),"SH");   
            }
                String bucketby = util.nvl((String)udf.getValue("bucketby"));
                if(bucketby.equals(""))
                bucketby=util.nvl((String)session.getAttribute("bucketby"));
                udf.resetALL();
                if(gridby.equals(""))
                    gridby="IFRS_BUCKET";
                int indexSH = vwPrpLst.indexOf(gridby)+1;
                String shPrp = util.prpsrtcolumn("prp", indexSH);
                
                if(ReGenerate.equals("")){
                ary = new ArrayList();
                ary.add(frmDte);
                ary.add(toDte);
                ary.add(bucketby);
                String proc="DP_IFRS_OPEN_CLOSE(pDteFrm =>to_date(?, 'dd-mm-rrrr'), pDteTo =>to_date(?, 'dd-mm-rrrr'),pMdl =>'IFRS_OPEN_CLOSE',pBucket =>?)";
                int ct = db.execCall("DP_IFRS_OPEN_CLOSE", proc, ary); 
                }
                
                String sttQ = "select stt,decode('"+gridby+"','IFRS_BUCKET',decode(stt,'SOLD',prp_070,"+shPrp+"),"+shPrp+") sh,sum(qty) qty,  to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts,  to_char(trunc(sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2),'99999990.00') mfgcts, \n" + 
                "trunc(sum(nvl(decode(stt,'SOLD',"+accRsPrp+","+cstRsPrp+"),quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgRs,trunc(sum(nvl(decode(stt,'SOLD',"+accRsPrp+","+cstRsPrp+"),quot)*nvl("+mfgCtsPrp+",cts)),2) vluRs,\n" + 
                "trunc(sum(nvl(decode(stt,'SOLD',"+accUsdPrp+","+cstUsdPrp+"),quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgUsd,trunc(sum(nvl(decode(stt,'SOLD',"+accUsdPrp+","+cstUsdPrp+"),quot)*nvl("+mfgCtsPrp+",cts)),2) vluUsd,\n" + 
                "trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstRsPrp+",quot),"+cstNrvPrp+"*exh_rte)*nvl("+mfgCtsPrp+",cts)),2) nrvvluRs,trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstUsdPrp+",quot),"+cstNrvPrp+")*nvl("+mfgCtsPrp+",cts)),2) nrvvluUsd\n" + 
                "from gt_srch_rslt   \n" + 
                "where \n" + 
                "pkt_ty in('NR','NR_MX','SMX') and stt in('CLOSE','SOLD','MIXOUT')\n" + 
                "group by stt,decode('"+gridby+"','IFRS_BUCKET',decode(stt,'SOLD',prp_070,"+shPrp+"),"+shPrp+")";
                 ary = new ArrayList();  
                 ResultSet rs = db.execSql("Collection", sttQ, ary);
                 while(rs.next()) {
                     String sh=util.nvl(rs.getString("sh"));
                     String stt=util.nvl(rs.getString("stt"));
                     if(!shlistSingle.contains(sh)){
                         shlistSingle.add(sh);
                     }
                     dataDtl.put(sh+"_"+stt+"_QTY_NR",util.nvl(rs.getString("qty")));
                     dataDtl.put(sh+"_"+stt+"_CTS_NR",util.nvl(rs.getString("cts")));
                     dataDtl.put(sh+"_"+stt+"_AVGRS_NR",util.nvl(rs.getString("avgRs")));
                     dataDtl.put(sh+"_"+stt+"_VLURS_NR",util.nvl(rs.getString("vluRs")));
                     dataDtl.put(sh+"_"+stt+"_AVGUSD_NR",util.nvl(rs.getString("avgUsd")));
                     dataDtl.put(sh+"_"+stt+"_VLUUSD_NR",util.nvl(rs.getString("vluUsd")));
                     dataDtl.put(sh+"_"+stt+"_MFGCTS_NR",util.nvl(rs.getString("mfgcts")));
                     dataDtl.put(sh+"_"+stt+"_NRVVLURS_NR",util.nvl(rs.getString("nrvvluRs")));
                     dataDtl.put(sh+"_"+stt+"_NRVVLUUSD_NR",util.nvl(rs.getString("nrvvluUsd")));
                 }
                 rs.close();
                
                String dsp_sttQ = "select dsp_stt stt,"+shPrp+" sh,sum(qty) qty,  to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts,  to_char(trunc(sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2),'99999990.00') mfgcts, \n" + 
                "trunc(sum(nvl("+cstRsPrp+",quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgRs,trunc(sum(nvl("+cstRsPrp+",quot)*nvl("+mfgCtsPrp+",cts)),2) vluRs,\n" + 
                "trunc(sum(nvl("+cstUsdPrp+",quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgUsd,trunc(sum(nvl("+cstUsdPrp+",quot)*nvl("+mfgCtsPrp+",cts)),2) vluUsd,\n" + 
                "trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstRsPrp+",quot),"+cstNrvPrp+"*exh_rte)*nvl("+mfgCtsPrp+",cts)),2) nrvvluRs,trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstUsdPrp+",quot),"+cstNrvPrp+")*nvl("+mfgCtsPrp+",cts)),2) nrvvluUsd\n" + 
                "from gt_srch_rslt   \n" + 
                "where \n" + 
                "pkt_ty in('NR','NR_MX','SMX') and dsp_stt in('OPEN','MFGNEW','PURNEW','SININ')\n" + 
                "group by dsp_stt,"+shPrp;
                 ary = new ArrayList();  
                 rs = db.execSql("Collection", dsp_sttQ, ary);
                 while(rs.next()) {
                     String sh=util.nvl(rs.getString("sh"));
                     String stt=util.nvl(rs.getString("stt"));
                     if(!shlistSingle.contains(sh)){
                         shlistSingle.add(sh);
                     }
                     dataDtl.put(sh+"_"+stt+"_QTY_NR",util.nvl(rs.getString("qty")));
                     dataDtl.put(sh+"_"+stt+"_CTS_NR",util.nvl(rs.getString("cts")));
                     dataDtl.put(sh+"_"+stt+"_AVGRS_NR",util.nvl(rs.getString("avgRs")));
                     dataDtl.put(sh+"_"+stt+"_VLURS_NR",util.nvl(rs.getString("vluRs")));
                     dataDtl.put(sh+"_"+stt+"_AVGUSD_NR",util.nvl(rs.getString("avgUsd")));
                     dataDtl.put(sh+"_"+stt+"_VLUUSD_NR",util.nvl(rs.getString("vluUsd")));
                     dataDtl.put(sh+"_"+stt+"_MFGCTS_NR",util.nvl(rs.getString("mfgcts")));
                     dataDtl.put(sh+"_"+stt+"_NRVVLURS_NR",util.nvl(rs.getString("nrvvluRs")));
                     dataDtl.put(sh+"_"+stt+"_NRVVLUUSD_NR",util.nvl(rs.getString("nrvvluUsd")));
                 }
                 rs.close();
                
                
                String ttlsttQ = "select stt,sum(qty) qty,  to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts,  to_char(trunc(sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2),'99999990.00') mfgcts, \n" + 
                "trunc(sum(nvl(decode(stt,'SOLD',"+accRsPrp+","+cstRsPrp+"),quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgRs,trunc(sum(nvl(decode(stt,'SOLD',"+accRsPrp+","+cstRsPrp+"),quot)*nvl("+mfgCtsPrp+",cts)),2) vluRs,\n" + 
                "trunc(sum(nvl(decode(stt,'SOLD',"+accUsdPrp+","+cstUsdPrp+"),quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgUsd,trunc(sum(nvl(decode(stt,'SOLD',"+accUsdPrp+","+cstUsdPrp+"),quot)*nvl("+mfgCtsPrp+",cts)),2) vluUsd,\n" + 
                "trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstRsPrp+",quot),"+cstNrvPrp+"*exh_rte)*nvl("+mfgCtsPrp+",cts)),2) nrvvluRs,trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstUsdPrp+",quot),"+cstNrvPrp+")*nvl("+mfgCtsPrp+",cts)),2) nrvvluUsd\n" + 
                "from gt_srch_rslt   \n" + 
                "where \n" + 
                "pkt_ty in('NR','NR_MX','SMX') and stt in('CLOSE','SOLD','MIXOUT')\n" + 
                "group by stt";
                 ary = new ArrayList();  
                 rs = db.execSql("Collection", ttlsttQ, ary);
                 while(rs.next()) {
                     String stt=util.nvl(rs.getString("stt"));
                     dataDtl.put(stt+"_QTY_NR",util.nvl(rs.getString("qty")));
                     dataDtl.put(stt+"_CTS_NR",util.nvl(rs.getString("cts")));
                     dataDtl.put(stt+"_AVGRS_NR",util.nvl(rs.getString("avgRs")));
                     dataDtl.put(stt+"_VLURS_NR",util.nvl(rs.getString("vluRs")));
                     dataDtl.put(stt+"_AVGUSD_NR",util.nvl(rs.getString("avgUsd")));
                     dataDtl.put(stt+"_VLUUSD_NR",util.nvl(rs.getString("vluUsd")));
                     dataDtl.put(stt+"_MFGCTS_NR",util.nvl(rs.getString("mfgcts")));
                     dataDtl.put(stt+"_NRVVLURS_NR",util.nvl(rs.getString("nrvvluRs")));
                     dataDtl.put(stt+"_NRVVLUUSD_NR",util.nvl(rs.getString("nrvvluUsd")));
                 }
                 rs.close();
                
                String ttldsp_sttQ = "select dsp_stt stt,sum(qty) qty,  to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts,  to_char(trunc(sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2),'99999990.00') mfgcts, \n" + 
                "trunc(sum(nvl("+cstRsPrp+",quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgRs,trunc(sum(nvl("+cstRsPrp+",quot)*nvl("+mfgCtsPrp+",cts)),2) vluRs,\n" + 
                "trunc(sum(nvl("+cstUsdPrp+",quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgUsd,trunc(sum(nvl("+cstUsdPrp+",quot)*nvl("+mfgCtsPrp+",cts)),2) vluUsd,\n" + 
                "trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstRsPrp+",quot),"+cstNrvPrp+"*exh_rte)*nvl("+mfgCtsPrp+",cts)),2) nrvvluRs,trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstUsdPrp+",quot),"+cstNrvPrp+")*nvl("+mfgCtsPrp+",cts)),2) nrvvluUsd\n" + 
                "from gt_srch_rslt   \n" + 
                "where \n" + 
                "pkt_ty in('NR','NR_MX','SMX') and dsp_stt in('OPEN','MFGNEW','PURNEW','SININ')\n" + 
                "group by dsp_stt";
                 ary = new ArrayList();  
                 rs = db.execSql("Collection", ttldsp_sttQ, ary);
                 while(rs.next()) {
                     String stt=util.nvl(rs.getString("stt"));
                     dataDtl.put(stt+"_QTY_NR",util.nvl(rs.getString("qty")));
                     dataDtl.put(stt+"_CTS_NR",util.nvl(rs.getString("cts")));
                     dataDtl.put(stt+"_AVGRS_NR",util.nvl(rs.getString("avgRs")));
                     dataDtl.put(stt+"_VLURS_NR",util.nvl(rs.getString("vluRs")));
                     dataDtl.put(stt+"_AVGUSD_NR",util.nvl(rs.getString("avgUsd")));
                     dataDtl.put(stt+"_VLUUSD_NR",util.nvl(rs.getString("vluUsd")));
                     dataDtl.put(stt+"_MFGCTS_NR",util.nvl(rs.getString("mfgcts")));
                     dataDtl.put(stt+"_NRVVLURS_NR",util.nvl(rs.getString("nrvvluRs")));
                     dataDtl.put(stt+"_NRVVLUUSD_NR",util.nvl(rs.getString("nrvvluUsd")));
                 }
                 rs.close();
                
                if(gridby.equals("IFRS_BUCKET")){
                sttQ = "select 'BUCKETOUT' stt,"+shPrp+" sh,sum(qty) qty,  to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts,  to_char(trunc(sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2),'99999990.00') mfgcts, \n" + 
                "trunc(sum(nvl(decode(stt,'SOLD',"+accRsPrp+","+cstRsPrp+"),quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgRs,trunc(sum(nvl(decode(stt,'SOLD',"+accRsPrp+","+cstRsPrp+"),quot)*nvl("+mfgCtsPrp+",cts)),2) vluRs,\n" + 
                "trunc(sum(nvl(decode(stt,'SOLD',"+accUsdPrp+","+cstUsdPrp+"),quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgUsd,trunc(sum(nvl(decode(stt,'SOLD',"+accUsdPrp+","+cstUsdPrp+"),quot)*nvl("+mfgCtsPrp+",cts)),2) vluUsd,\n" + 
                "trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstRsPrp+",quot),"+cstNrvPrp+"*exh_rte)*nvl("+mfgCtsPrp+",cts)),2) nrvvluRs,trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstUsdPrp+",quot),"+cstNrvPrp+")*nvl("+mfgCtsPrp+",cts)),2) nrvvluUsd\n" + 
                "from gt_srch_rslt   \n" + 
                "where \n" + 
                "pkt_ty in('NR','NR_MX','SMX') and stt in('SOLD') and "+shPrp+"<>prp_070\n" + 
                "group by "+shPrp;
                 ary = new ArrayList();  
                 rs = db.execSql("Collection", sttQ, ary);
                 while(rs.next()) {
                     String sh=util.nvl(rs.getString("sh"));
                     String stt=util.nvl(rs.getString("stt"));
                     if(!shlistSingle.contains(sh)){
                         shlistSingle.add(sh);
                     }
                     dataDtl.put(sh+"_"+stt+"_QTY_NR",util.nvl(rs.getString("qty")));
                     dataDtl.put(sh+"_"+stt+"_CTS_NR",util.nvl(rs.getString("cts")));
                     dataDtl.put(sh+"_"+stt+"_AVGRS_NR",util.nvl(rs.getString("avgRs")));
                     dataDtl.put(sh+"_"+stt+"_VLURS_NR",util.nvl(rs.getString("vluRs")));
                     dataDtl.put(sh+"_"+stt+"_AVGUSD_NR",util.nvl(rs.getString("avgUsd")));
                     dataDtl.put(sh+"_"+stt+"_VLUUSD_NR",util.nvl(rs.getString("vluUsd")));
                     dataDtl.put(sh+"_"+stt+"_MFGCTS_NR",util.nvl(rs.getString("mfgcts")));
                     dataDtl.put(sh+"_"+stt+"_NRVVLURS_NR",util.nvl(rs.getString("nrvvluRs")));
                     dataDtl.put(sh+"_"+stt+"_NRVVLUUSD_NR",util.nvl(rs.getString("nrvvluUsd")));
                 }
                 rs.close();
                    sttQ = "select 'BUCKETIN' stt,prp_070 sh,sum(qty) qty,  to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts,  to_char(trunc(sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2),'99999990.00') mfgcts, \n" + 
                    "trunc(sum(nvl(decode(stt,'SOLD',"+accRsPrp+","+cstRsPrp+"),quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgRs,trunc(sum(nvl(decode(stt,'SOLD',"+accRsPrp+","+cstRsPrp+"),quot)*nvl("+mfgCtsPrp+",cts)),2) vluRs,\n" + 
                    "trunc(sum(nvl(decode(stt,'SOLD',"+accUsdPrp+","+cstUsdPrp+"),quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgUsd,trunc(sum(nvl(decode(stt,'SOLD',"+accUsdPrp+","+cstUsdPrp+"),quot)*nvl("+mfgCtsPrp+",cts)),2) vluUsd,\n" + 
                    "trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstRsPrp+",quot),"+cstNrvPrp+"*exh_rte)*nvl("+mfgCtsPrp+",cts)),2) nrvvluRs,trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstUsdPrp+",quot),"+cstNrvPrp+")*nvl("+mfgCtsPrp+",cts)),2) nrvvluUsd\n" + 
                    "from gt_srch_rslt   \n" + 
                    "where \n" + 
                    "pkt_ty in('NR','NR_MX','SMX') and stt in('SOLD') and "+shPrp+"<>prp_070\n" + 
                    "group by prp_070";
                     ary = new ArrayList();  
                     rs = db.execSql("Collection", sttQ, ary);
                     while(rs.next()) {
                         String sh=util.nvl(rs.getString("sh"));
                         String stt=util.nvl(rs.getString("stt"));
                         if(!shlistSingle.contains(sh)){
                             shlistSingle.add(sh);
                         }
                         dataDtl.put(sh+"_"+stt+"_QTY_NR",util.nvl(rs.getString("qty")));
                         dataDtl.put(sh+"_"+stt+"_CTS_NR",util.nvl(rs.getString("cts")));
                         dataDtl.put(sh+"_"+stt+"_AVGRS_NR",util.nvl(rs.getString("avgRs")));
                         dataDtl.put(sh+"_"+stt+"_VLURS_NR",util.nvl(rs.getString("vluRs")));
                         dataDtl.put(sh+"_"+stt+"_AVGUSD_NR",util.nvl(rs.getString("avgUsd")));
                         dataDtl.put(sh+"_"+stt+"_VLUUSD_NR",util.nvl(rs.getString("vluUsd")));
                         dataDtl.put(sh+"_"+stt+"_MFGCTS_NR",util.nvl(rs.getString("mfgcts")));
                         dataDtl.put(sh+"_"+stt+"_NRVVLURS_NR",util.nvl(rs.getString("nrvvluRs")));
                         dataDtl.put(sh+"_"+stt+"_NRVVLUUSD_NR",util.nvl(rs.getString("nrvvluUsd")));
                     }
                     rs.close();
                    ttlsttQ = "select sum(qty) qty,  to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts,  to_char(trunc(sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2),'99999990.00') mfgcts, \n" + 
                    "trunc(sum(nvl(decode(stt,'SOLD',"+accRsPrp+","+cstRsPrp+"),quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgRs,trunc(sum(nvl(decode(stt,'SOLD',"+accRsPrp+","+cstRsPrp+"),quot)*nvl("+mfgCtsPrp+",cts)),2) vluRs,\n" + 
                    "trunc(sum(nvl(decode(stt,'SOLD',"+accUsdPrp+","+cstUsdPrp+"),quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgUsd,trunc(sum(nvl(decode(stt,'SOLD',"+accUsdPrp+","+cstUsdPrp+"),quot)*nvl("+mfgCtsPrp+",cts)),2) vluUsd,\n" + 
                    "trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstRsPrp+",quot),"+cstNrvPrp+"*exh_rte)*nvl("+mfgCtsPrp+",cts)),2) nrvvluRs,trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstUsdPrp+",quot),"+cstNrvPrp+")*nvl("+mfgCtsPrp+",cts)),2) nrvvluUsd\n" + 
                    "from gt_srch_rslt   \n" + 
                    "where \n" + 
                    "pkt_ty in('NR','NR_MX','SMX') and stt in('SOLD') and "+shPrp+"<>prp_070\n" ;
                     ary = new ArrayList();  
                     rs = db.execSql("Collection", ttlsttQ, ary);
                     while(rs.next()) {
                         dataDtl.put("BUCKETIN_QTY_NR",util.nvl(rs.getString("qty")));
                         dataDtl.put("BUCKETIN_CTS_NR",util.nvl(rs.getString("cts")));
                         dataDtl.put("BUCKETIN_AVGRS_NR",util.nvl(rs.getString("avgRs")));
                         dataDtl.put("BUCKETIN_VLURS_NR",util.nvl(rs.getString("vluRs")));
                         dataDtl.put("BUCKETIN_AVGUSD_NR",util.nvl(rs.getString("avgUsd")));
                         dataDtl.put("BUCKETIN_VLUUSD_NR",util.nvl(rs.getString("vluUsd")));
                         dataDtl.put("BUCKETIN_MFGCTS_NR",util.nvl(rs.getString("mfgcts")));
                         dataDtl.put("BUCKETIN_NRVVLURS_NR",util.nvl(rs.getString("nrvvluRs")));
                         dataDtl.put("BUCKETIN_NRVVLUUSD_NR",util.nvl(rs.getString("nrvvluUsd")));
                         
                         dataDtl.put("BUCKETOUT_QTY_NR",util.nvl(rs.getString("qty")));
                         dataDtl.put("BUCKETOUT_CTS_NR",util.nvl(rs.getString("cts")));
                         dataDtl.put("BUCKETOUT_AVGRS_NR",util.nvl(rs.getString("avgRs")));
                         dataDtl.put("BUCKETOUT_VLURS_NR",util.nvl(rs.getString("vluRs")));
                         dataDtl.put("BUCKETOUT_AVGUSD_NR",util.nvl(rs.getString("avgUsd")));
                         dataDtl.put("BUCKETOUT_VLUUSD_NR",util.nvl(rs.getString("vluUsd")));
                         dataDtl.put("BUCKETOUT_MFGCTS_NR",util.nvl(rs.getString("mfgcts")));
                         dataDtl.put("BUCKETOUT_NRVVLURS_NR",util.nvl(rs.getString("nrvvluRs")));
                         dataDtl.put("BUCKETOUT_NRVVLUUSD_NR",util.nvl(rs.getString("nrvvluUsd")));
                     }
                     rs.close();
                }
                String sttmixQ = "select dsp_stt,decode('"+gridby+"','IFRS_BUCKET',decode(dsp_stt,'SOLD',prp_070,"+shPrp+"),"+shPrp+") sh,sum(qty) qty,  to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts, \n" + 
                "trunc(sum(nvl("+cstRsPrp+",quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgRs,trunc(sum(nvl("+cstRsPrp+",quot)*nvl("+mfgCtsPrp+",cts)),2) vluRs,\n" + 
                "trunc(sum(nvl("+cstUsdPrp+",quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgUsd,trunc(sum(nvl("+cstUsdPrp+",quot)*nvl("+mfgCtsPrp+",cts)),2) vluUsd,\n" + 
                "trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstRsPrp+",quot),"+cstNrvPrp+"*exh_rte)*nvl("+mfgCtsPrp+",cts)),2) nrvvluRs,trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstUsdPrp+",quot),"+cstNrvPrp+")*nvl("+mfgCtsPrp+",cts)),2) nrvvluUsd\n" + 
                "from gt_srch_rslt   \n" + 
                "where \n" + 
                "pkt_ty in('MIX')\n" + 
                "group by dsp_stt,decode('"+gridby+"','IFRS_BUCKET',decode(dsp_stt,'SOLD',prp_070,"+shPrp+"),"+shPrp+")";
                 ary = new ArrayList();  
                 rs = db.execSql("Collection", sttmixQ, ary);
                 while(rs.next()) {
                     String sh=util.nvl(rs.getString("sh"));
                     String stt=util.nvl(rs.getString("dsp_stt"));
                     if(!shlistMix.contains(sh)){
                         shlistMix.add(sh);
                     }
                     dataDtl.put(sh+"_"+stt+"_QTY_MIX",util.nvl(rs.getString("qty")));
                     dataDtl.put(sh+"_"+stt+"_CTS_MIX",util.nvl(rs.getString("cts")));
                     dataDtl.put(sh+"_"+stt+"_AVGRS_MIX",util.nvl(rs.getString("avgRs")));
                     dataDtl.put(sh+"_"+stt+"_VLURS_MIX",util.nvl(rs.getString("vluRs")));
                     dataDtl.put(sh+"_"+stt+"_AVGUSD_MIX",util.nvl(rs.getString("avgUsd")));
                     dataDtl.put(sh+"_"+stt+"_VLUUSD_MIX",util.nvl(rs.getString("vluUsd")));
                     dataDtl.put(sh+"_"+stt+"_NRVVLURS_MIX",util.nvl(rs.getString("nrvvluRs")));
                     dataDtl.put(sh+"_"+stt+"_NRVVLUUSD_MIX",util.nvl(rs.getString("nrvvluUsd")));
                 }
                 rs.close();
                
                String sttmixttlQ = "select dsp_stt,sum(qty) qty,  to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts, \n" + 
                "trunc(sum(nvl("+cstRsPrp+",quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgRs,trunc(sum(nvl("+cstRsPrp+",quot)*nvl("+mfgCtsPrp+",cts)),2) vluRs,\n" + 
                "trunc(sum(nvl("+cstUsdPrp+",quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgUsd,trunc(sum(nvl("+cstUsdPrp+",quot)*nvl("+mfgCtsPrp+",cts)),2) vluUsd,\n" + 
                "trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstRsPrp+",quot),"+cstNrvPrp+"*exh_rte)*nvl("+mfgCtsPrp+",cts)),2) nrvvluRs,trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstUsdPrp+",quot),"+cstNrvPrp+")*nvl("+mfgCtsPrp+",cts)),2) nrvvluUsd\n" + 
                "from gt_srch_rslt   \n" + 
                "where \n" + 
                "pkt_ty in('MIX')\n"+
                "group by dsp_stt";
                 ary = new ArrayList();  
                 rs = db.execSql("Collection", sttmixttlQ, ary);
                 while(rs.next()) {
                     String stt=util.nvl(rs.getString("dsp_stt"));
                     dataDtl.put(stt+"_QTY_MIX",util.nvl(rs.getString("qty")));
                     dataDtl.put(stt+"_CTS_MIX",util.nvl(rs.getString("cts")));
                     dataDtl.put(stt+"_AVGRS_MIX",util.nvl(rs.getString("avgRs")));
                     dataDtl.put(stt+"_VLURS_MIX",util.nvl(rs.getString("vluRs")));
                     dataDtl.put(stt+"_AVGUSD_MIX",util.nvl(rs.getString("avgUsd")));
                     dataDtl.put(stt+"_VLUUSD_MIX",util.nvl(rs.getString("vluUsd")));
                     dataDtl.put(stt+"_NRVVLURS_MIX",util.nvl(rs.getString("nrvvluRs")));
                     dataDtl.put(stt+"_NRVVLUUSD_MIX",util.nvl(rs.getString("nrvvluUsd")));
                 }
                 rs.close();
                
                String sttmixInQ = "select rmk,"+shPrp+" sh,sum(qty) qty,  to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts, \n" + 
                "trunc(sum(nvl("+cstRsPrp+",quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts),2)),2) avgRs,trunc(sum(nvl("+cstRsPrp+",quot)*nvl("+mfgCtsPrp+",cts)),2) vluRs,\n" + 
                "trunc(sum(nvl("+cstUsdPrp+",quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgUsd,trunc(sum(nvl("+cstUsdPrp+",quot)*nvl("+mfgCtsPrp+",cts)),2) vluUsd,\n" +  
                "trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstRsPrp+",quot),"+cstNrvPrp+"*exh_rte)*nvl("+mfgCtsPrp+",cts)),2) nrvvluRs,trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstUsdPrp+",quot),"+cstNrvPrp+")*nvl("+mfgCtsPrp+",cts)),2) nrvvluUsd\n" + 
                "from gt_srch_rslt   \n" + 
                "where \n" + 
                "rmk in('MIXIN','SINOUT')\n" + 
                "group by rmk,"+shPrp;
                 ary = new ArrayList();  
                 rs = db.execSql("Collection", sttmixInQ, ary);
                 while(rs.next()) {
                     String sh=util.nvl(rs.getString("sh"));
                     String stt=util.nvl(rs.getString("rmk"));
                     if(!shlistMix.contains(sh)){
                         shlistMix.add(sh);
                     }
                     dataDtl.put(sh+"_"+stt+"_QTY_MIX",util.nvl(rs.getString("qty")));
                     dataDtl.put(sh+"_"+stt+"_CTS_MIX",util.nvl(rs.getString("cts")));
                     dataDtl.put(sh+"_"+stt+"_AVGRS_MIX",util.nvl(rs.getString("avgRs")));
                     dataDtl.put(sh+"_"+stt+"_VLURS_MIX",util.nvl(rs.getString("vluRs")));
                     dataDtl.put(sh+"_"+stt+"_AVGUSD_MIX",util.nvl(rs.getString("avgUsd")));
                     dataDtl.put(sh+"_"+stt+"_VLUUSD_MIX",util.nvl(rs.getString("vluUsd")));
                     dataDtl.put(sh+"_"+stt+"_NRVVLURS_MIX",util.nvl(rs.getString("nrvvluRs")));
                     dataDtl.put(sh+"_"+stt+"_NRVVLUUSD_MIX",util.nvl(rs.getString("nrvvluUsd")));
                 }
                 rs.close();
                
                String sttmixInttlQ = "select rmk,sum(qty) qty,  to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts, \n" + 
                "trunc(sum(nvl("+cstRsPrp+",quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgRs,trunc(sum(nvl("+cstRsPrp+",quot)*nvl("+mfgCtsPrp+",cts)),2) vluRs,\n" + 
                "trunc(sum(nvl("+cstUsdPrp+",quot)*trunc(nvl("+mfgCtsPrp+",cts),2))/sum(trunc(nvl("+mfgCtsPrp+",cts), 2)),2) avgUsd,trunc(sum(nvl("+cstUsdPrp+",quot)*nvl("+mfgCtsPrp+",cts)),2) vluUsd,\n" + 
                "trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstRsPrp+",quot),"+cstNrvPrp+"*exh_rte)*nvl("+mfgCtsPrp+",cts)),2) nrvvluRs,trunc(sum(decode(nvl("+cstNrvPrp+",0),2,nvl("+cstUsdPrp+",quot),"+cstNrvPrp+")*nvl("+mfgCtsPrp+",cts)),2) nrvvluUsd\n" + 
                "from gt_srch_rslt   \n" + 
                "where \n" + 
                "rmk in('MIXIN','SINOUT')\n"+
                "group by rmk";
                 ary = new ArrayList();  
                 rs = db.execSql("Collection", sttmixInttlQ, ary);
                 while(rs.next()) {
                     String stt=util.nvl(rs.getString("rmk"));
                     dataDtl.put(stt+"_QTY_MIX",util.nvl(rs.getString("qty")));
                     dataDtl.put(stt+"_CTS_MIX",util.nvl(rs.getString("cts")));
                     dataDtl.put(stt+"_AVGRS_MIX",util.nvl(rs.getString("avgRs")));
                     dataDtl.put(stt+"_VLURS_MIX",util.nvl(rs.getString("vluRs")));
                     dataDtl.put(stt+"_AVGUSD_MIX",util.nvl(rs.getString("avgUsd")));
                     dataDtl.put(stt+"_VLUUSD_MIX",util.nvl(rs.getString("vluUsd")));
                     dataDtl.put(stt+"_NRVVLURS_MIX",util.nvl(rs.getString("nrvvluRs")));
                     dataDtl.put(stt+"_NRVVLUUSD_MIX",util.nvl(rs.getString("nrvvluUsd")));
                 }
                 rs.close();
                req.setAttribute("dataDtl", dataDtl);
                req.setAttribute("shlistMix", shlistMix);
                req.setAttribute("shlistSingle", shlistSingle);
                req.setAttribute("view", "Y");
                session.setAttribute("gridby", gridby);
                session.setAttribute("bucketby", bucketby);
                udf.setValue("frmDte", frmDte);
                udf.setValue("toDte", toDte);
                udf.setValue("gridby", gridby);
                udf.setValue("gridbyExisting", gridby);
                udf.setValue("bucketby", bucketby);
            util.updAccessLog(req,res,"Ifrs Action", "load end");
            return am.findForward("load");
            }
    }
    
    public ActionForward pktDtlStockOpenClose(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
           util.updAccessLog(req,res,"Analysis Report", "loadDtl start");
        ReportForm udf = (ReportForm)af;
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "IFRS_OPEN_CLOSE","IFRS_OPEN_CLOSE");
        int indexNRV = vwPrpLst.indexOf("NRV")+1;
        String cstNrvPrp = util.prpsrtcolumn("prp", indexNRV);
        int indexCSTRS = vwPrpLst.indexOf("CST_PER_RS")+1;
        String cstRsPrp = util.prpsrtcolumn("prp", indexCSTRS);
        int indexCSTUSD = vwPrpLst.indexOf("CST_PER_FE")+1;
        String cstUsdPrp = util.prpsrtcolumn("prp", indexCSTUSD);
        ArrayList pktList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        HashMap dbinfo = info.getDmbsInfoLst();
        ArrayList prpDspBlocked = info.getPageblockList();
        String stt=util.nvl((String)req.getParameter("stt"));
        String lprpval=util.nvl((String)req.getParameter("lprpval"));
        String lprp=util.nvl((String)req.getParameter("lprp"));
        String pkt_ty=util.nvl((String)req.getParameter("pkt_ty"));
        itemHdr.add("Sr No");
        itemHdr.add("Packet Id");
        itemHdr.add("Qty");
        int s=1;
        int indexSH = vwPrpLst.indexOf(lprp)+1;
        String shPrp = util.prpsrtcolumn("prp", indexSH);
        if(lprp.equals("IFRS_BUCKET") &&(stt.equals("BUCKETIN") || stt.equals("BUCKETOUT"))){
               
        }

            
        for (int i = 0; i < vwPrpLst.size(); i++) {
             String prp=util.nvl((String)vwPrpLst.get(i));
             itemHdr.add(prp);
            if(prp.equals("NRV"))
            itemHdr.add("NRV_RS");
         }
           
           String conQ="";
           if(stt.equals("CLOSE") || stt.equals("SOLD") || stt.equals("MIXOUT"))
               conQ+=" and stt='"+stt+"'";
           else if(stt.equals("MIXIN") || stt.equals("SINOUT"))
               conQ+=" and rmk='"+stt+"'";
           else if(lprp.equals("IFRS_BUCKET") &&(stt.equals("BUCKETIN") || stt.equals("BUCKETOUT")))
               conQ+=" and stt='SOLD' and "+shPrp+"<>prp_070";
           else
               conQ+=" and dsp_stt='"+stt+"'";
           
           if(!stt.equals("MIXIN") && !stt.equals("SINOUT")){
           if(pkt_ty.equals("MIX"))
           conQ+=" and pkt_ty='"+pkt_ty+"'";
           else
           conQ+=" and pkt_ty <> 'MIX'";
           }
           if(!lprpval.equals("")){
               if(lprp.equals("IFRS_BUCKET") && (stt.equals("BUCKETOUT")))
               conQ+=" and "+shPrp+"='"+lprpval+"'";
               else if(lprp.equals("IFRS_BUCKET") && (stt.equals("BUCKETIN")))
               conQ+=" and prp_070='"+lprpval+"'";
               else
               conQ+=" and "+shPrp+"='"+lprpval+"'";
           }

           String  srchQ =  " select decode(nvl("+cstNrvPrp+",0),2,nvl("+cstUsdPrp+",quot),"+cstNrvPrp+") nrvUsdPer,decode(nvl("+cstNrvPrp+",0),2,nvl("+cstRsPrp+",quot),"+cstNrvPrp+"*exh_rte) nrvRsPer, prp_070 soldbucket,stk_idn ,qty,quot,vnm , stt,flg,rmk tfl3,trunc(cts,2) cts,to_char(trunc(cts,3) * quot, '9999999990.00') vlu,sk1  ";
           
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
           
           ResultSet rs = db.execSql("search Result", rsltQ, new ArrayList());
           try {
               while(rs.next()) {
                   String stkIdn = util.nvl(rs.getString("stk_idn"));
                   HashMap pktPrpMap = new HashMap();
                   pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                   String vnm = util.nvl(rs.getString("vnm"));
                   pktPrpMap.put("Packet Id",vnm);
                   pktPrpMap.put("Sr No",String.valueOf(s++));
                   pktPrpMap.put("Qty", util.nvl(rs.getString("qty")));
                   pktPrpMap.put("Cts", util.nvl(rs.getString("cts")));
                   pktPrpMap.put("NRV_RS", util.nvl(rs.getString("nrvRsPer")));
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
                         if (prp.toUpperCase().equals("NRV"))
                         val = util.nvl(rs.getString("nrvUsdPer"));
                         if (prp.toUpperCase().equals("IFRS_BUCKET") && stt.equals("SOLD"))
                         val = util.nvl(rs.getString("soldbucket"));
                         if (prp.toUpperCase().equals("IFRS_BUCKET") && (stt.equals("BUCKETIN")))
                         val = util.nvl(rs.getString("soldbucket"));
                           pktPrpMap.put(prp, val);
                           if(prpDspBlocked.contains(prp)){
                           }else if(!itemHdr.contains(prp)){
                           itemHdr.add(prp);
                           }
                           }
                                 
                       pktList.add(pktPrpMap);
                   }
               rs.close();
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
               String fileNmzip = "ResultExcel"+util.getToDteTime();
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
               String fileNm = "ResultExcel"+util.getToDteTime()+".xls";
               res.setContentType(CONTENT_TYPE);
               res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
               hwb.write(out);
               out.flush();
               out.close();
               }
             return null;
//           session.setAttribute("pktList", pktList);
//           session.setAttribute("itemHdr",itemHdr);
//       util.updAccessLog(req,res,"Analysis Report", "loadDtl end");
//        return am.findForward("pktDtl");
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
                util.updAccessLog(req,res,"Ifrs Action", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Ifrs Action", "init");
            }
            }
            return rtnPg;
            }
}
