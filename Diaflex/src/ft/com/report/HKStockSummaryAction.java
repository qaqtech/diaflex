package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.IOException;

import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

public class HKStockSummaryAction extends DispatchAction {
    public HKStockSummaryAction() {
        super();
    }
    
    public ActionForward load(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Stock Summary", "load start");
        HKStockSummaryForm hkStockForm = (HKStockSummaryForm)form;
        GenericInterface genericInt = new GenericImpl();
        ArrayList assortSrchList =genericInt.genricSrch(req,res,"RPTGNCSrch","STK_SMM_RPT_SRCH");
        info.setGncPrpLst(assortSrchList);
        String reportPkg = "REPORT_PKG.mkt_trf_srch(pMdl => ?)";
        ArrayList ary = new ArrayList();
            ary.add("STK_SMM_RPT");
        int ct = db.execCall("reportPkg", reportPkg, ary);
        HashMap pktList = new HashMap();
        ArrayList vWPrpList= genericInt.genericPrprVw(req, res, "STKSMMRPTLst","STK_SMM_RPT");;
        HashMap dbinfo = info.getDmbsInfoLst();
        String sh = (String)dbinfo.get("SHAPE");
        String szval = (String)dbinfo.get("SIZE");
        String colval = (String)dbinfo.get("COL");
        String clrval = (String)dbinfo.get("CLR");
        int indexLB = vWPrpList.indexOf("LAB")+1;
        int indexSH = vWPrpList.indexOf(sh)+1;
        int indexSZ = vWPrpList.indexOf(szval)+1;
        int indexCol = vWPrpList.indexOf(colval)+1;
        int indexClr = vWPrpList.indexOf(clrval)+1;
        int indexTrfCmp = vWPrpList.indexOf("TRF_CMP")+1;
        int indexAge= vWPrpList.indexOf("AGE")+1;
        int indexdept= vWPrpList.indexOf("DEPT")+1;
        int indextrfdt= vWPrpList.indexOf("TRF_DTE")+1;
        String lbPrp = "prp_00"+indexLB;
        String shPrp = "prp_00"+indexSH;
        String szPrp = "prp_00"+indexSZ;
        String colPrp = "prp_00"+indexCol;
        String clrPrp = "prp_00"+indexClr;
        String trfcmpPrp = "prp_00"+indexTrfCmp;
        String agePrp = "prp_00"+indexAge;
        String trfdtPrp = "prp_00"+indextrfdt;
        String deptPrp = "prp_0"+indexdept;
        String lbSrt = "srt_00"+indexLB;
        String shSrt = "srt_00"+indexSH;
        String szSrt = "srt_00"+indexSZ;
        String colSrt = "srt_00"+indexCol;
        String clrSrt = "srt_00"+indexClr;
        String gtTtlFetch= " select count(*) qty, trunc(sum(cts*quot),2) vlu  , decode("+lbPrp+", 'GIA', 'GIA', 'IGI') lab "+
        " from gt_srch_rslt a, mrule b, rule_dtl c "+ 
        " where b.rule_idn = c.rule_idn and b.mdl = 'AGE_GRP' and upper(b.nme_rule) = 'AGE GROUP' "+
        " and nvl(to_number(decode("+agePrp+",'NA',0,null,0,"+agePrp+")),0) between c.num_fr and c.num_to "+
        " group by decode("+lbPrp+", 'GIA', 'GIA', 'IGI')"+
        " order by 1 ";

            ArrayList outLst = db.execSqlLst("gtTtl", gtTtlFetch, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
         double tttVlu =0;
         while(rs.next()){
             String lab = util.nvl(rs.getString("lab"));
             String vlu = util.nvl(rs.getString("qty"));
              pktList.put(lab+"TTL",vlu);
             if(vlu.equals(""))
                 vlu ="0";
             tttVlu =  tttVlu + Double.parseDouble(vlu);
         }
        rs.close(); pst.close();
         pktList.put("TTLVLU",Double.toString(tttVlu));
        ArrayList labList = new ArrayList();
        ArrayList ageList = new ArrayList();
       
        String gtFetch =  " select c.dsc, "+lbPrp+" lab , count(*) qty "+
                          " , sum(cts*quot) vlu , sum(cts*decode(nvl(Decode("+trfcmpPrp+",'NA',0,Null,0,"+trfcmpPrp+"), 0), 0, quot, "+trfcmpPrp+")) trf_vlu "+
                           " from gt_srch_rslt a, mrule b, rule_dtl c "+
                          " where b.rule_idn = c.rule_idn and b.mdl = 'AGE_GRP' and upper(b.nme_rule) = 'AGE GROUP' "+
                        " and nvl(to_number(decode("+agePrp+",'NA',0,null,0,"+agePrp+")),0) between c.num_fr and c.num_to group by c.dsc, "+lbPrp+" order by 1, 2  ";    

            outLst = db.execSqlLst("gtFetch", gtFetch, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String ageDsc = util.nvl(rs.getString("dsc"));
            String qty = util.nvl(rs.getString("qty"));
            String vlu = util.nvl(rs.getString("vlu"));
            String lab = util.nvl(rs.getString("lab"));
            
            if(!labList.contains(lab)){
                labList.add(lab);
            }
            if(!ageList.contains(ageDsc)){
                ageList.add(ageDsc);
            }
            pktList.put(ageDsc+"_"+lab+"_QTY", qty);
            pktList.put(ageDsc+"_"+lab+"_VLU", vlu);
            
        }
        rs.close(); pst.close();
        String gtdiff = " select dsc, sum(qty) qty , sum(vlu) vlu , sum(trf_vlu) trf_vlu "+
                         " , decode(sum(trf_vlu), 0, null, trunc((sum(trf_vlu) - sum(vlu))/sum(trf_vlu)*100, 2)) diff_pct from ( "+
                        " select c.dsc , count(*) qty , sum(cts*quot) vlu , sum(cts*decode(nvl(Decode("+trfcmpPrp+",'NA',0,Null,0,"+trfcmpPrp+"), 0), 0, quot, "+trfcmpPrp+")) trf_vlu "+
                        " from gt_srch_rslt a, mrule b, rule_dtl c where b.rule_idn = c.rule_idn and b.mdl = 'AGE_GRP' and upper(b.nme_rule) = 'AGE GROUP'"+
                       " and nvl(to_number(decode("+agePrp+",'NA',0,null,0,"+agePrp+")),0) between c.num_fr and c.num_to group by c.dsc) group by dsc order by 1, 2  ";

            outLst = db.execSqlLst("gtFetch", gtdiff, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
     while(rs.next()){
         String ageDsc = util.nvl(rs.getString("dsc"));
         String diffPct = util.nvl(rs.getString("diff_pct"));
         pktList.put(ageDsc+"_DIFF", diffPct);
     }
        rs.close(); pst.close();
        req.setAttribute("labList", labList);
        req.setAttribute("ageList", ageList);
        req.setAttribute("pktList", pktList);
            util.updAccessLog(req,res,"Stock Summary", "load end");
        return am.findForward("load");
        }
    }
    public ActionForward fetch(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Stock Summary", "fetch start");
        HKStockSummaryForm hkStockForm = (HKStockSummaryForm)form;
            GenericInterface genericInt = new GenericImpl();
        ArrayList assortSrchList = genericInt.genricSrch(req,res,"RPTGNCSrch","STK_SMM_RPT_SRCH");
        info.setGncPrpLst(assortSrchList);
        HashMap pktList = new HashMap();
        HashMap paramsMap = new HashMap();
        String srchQ="";
        paramsMap=fetchDept(form,req,res);
        ArrayList vWPrpList =genericInt.genericPrprVw(req, res, "STKSMMRPTLst","STK_SMM_RPT");;
        HashMap dbinfo = info.getDmbsInfoLst();
        String sh = (String)dbinfo.get("SHAPE");
        String szval = (String)dbinfo.get("SIZE");
        String colval = (String)dbinfo.get("COL");
        String clrval = (String)dbinfo.get("CLR");
        int indexLB = vWPrpList.indexOf("LAB")+1;
        int indexSH = vWPrpList.indexOf(sh)+1;
        int indexSZ = vWPrpList.indexOf(szval)+1;
        int indexCol = vWPrpList.indexOf(colval)+1;
        int indexClr = vWPrpList.indexOf(clrval)+1;
        int indexTrfCmp = vWPrpList.indexOf("TRF_CMP")+1;
        int indexAge= vWPrpList.indexOf("AGE")+1;
        int indexdept= vWPrpList.indexOf("DEPT")+1;
        int indextrfdt= vWPrpList.indexOf("TRF_DTE")+1;
        String lbPrp = "prp_00"+indexLB;
        String shPrp = "prp_00"+indexSH;
        String szPrp = "prp_00"+indexSZ;
        String colPrp = "prp_00"+indexCol;
        String clrPrp = "prp_00"+indexClr;
        String trfcmpPrp = "prp_00"+indexTrfCmp;
        String agePrp = "prp_00"+indexAge;
        String trfdtPrp = "prp_00"+indextrfdt;
        String deptPrp = "prp_0"+indexdept;
        String lbSrt = "srt_00"+indexLB;
        String shSrt = "srt_00"+indexSH;
        String szSrt = "srt_00"+indexSZ;
        String colSrt = "srt_00"+indexCol;
        String clrSrt = "srt_00"+indexClr;
        if(paramsMap!=null && paramsMap.size()>0){
        for(int i=0;i<assortSrchList.size();i++){
        ArrayList prplist =(ArrayList)assortSrchList.get(i);
        String lprp = (String)prplist.get(0);
          
            String val1 = util.nvl((String)paramsMap.get(lprp+"_1"));          
            String val2 = util.nvl((String)paramsMap.get(lprp+"_2"));
         
                if(!val1.equals("") && !val2.equals("")){
                srchQ = " and a.prp_010 between '"+val1+"' and '"+val2+"' ";
               
             }
        }
        }
        String gtTtlFetch= " select count(*) qty, trunc(sum(cts*quot),2) vlu  , decode("+lbPrp+", 'GIA', 'GIA', 'IGI') lab "+
        " from gt_srch_rslt a, mrule b, rule_dtl c "+ 
        " where b.rule_idn = c.rule_idn and b.mdl = 'AGE_GRP' and upper(b.nme_rule) = 'AGE GROUP' "+
        " and nvl(to_number(decode("+agePrp+",'NA',0,null,0,"+agePrp+")),0) between c.num_fr and c.num_to "+srchQ+
        " group by decode(prp_001, 'GIA', 'GIA', 'IGI')"+
        " order by 1 ";

            ArrayList outLst = db.execSqlLst("gtTtl", gtTtlFetch, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
         double tttVlu =0;
         while(rs.next()){
             String lab = util.nvl(rs.getString("lab"));
             String vlu = util.nvl(rs.getString("qty"));
              pktList.put(lab+"TTL",vlu);
             if(vlu.equals(""))
                 vlu ="0";
             tttVlu =  tttVlu + Double.parseDouble(vlu);
         }
        rs.close(); pst.close();
         pktList.put("TTLVLU",Double.toString(tttVlu));
        ArrayList labList = new ArrayList();
        ArrayList ageList = new ArrayList();
       
        String gtFetch =  " select c.dsc, "+lbPrp+" lab , count(*) qty "+
                          " , sum(cts*quot) vlu , sum(cts*decode(nvl(Decode("+trfcmpPrp+",'NA',0,Null,0,"+trfcmpPrp+"), 0), 0, quot, "+trfcmpPrp+")) trf_vlu "+
                           " from gt_srch_rslt a, mrule b, rule_dtl c "+
                          " where b.rule_idn = c.rule_idn and b.mdl = 'AGE_GRP' and upper(b.nme_rule) = 'AGE GROUP' "+srchQ+
                        " and nvl(to_number(decode("+agePrp+",'NA',0,null,0,"+agePrp+")),0) between c.num_fr and c.num_to group by c.dsc, "+lbPrp+" order by 1, 2  ";    
    
            outLst = db.execSqlLst("gtFetch", gtFetch, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String ageDsc = util.nvl(rs.getString("dsc"));
            String qty = util.nvl(rs.getString("qty"));
            String vlu = util.nvl(rs.getString("vlu"));
            String lab = util.nvl(rs.getString("lab"));
            
            if(!labList.contains(lab)){
                labList.add(lab);
            }
            if(!ageList.contains(ageDsc)){
                ageList.add(ageDsc);
            }
            pktList.put(ageDsc+"_"+lab+"_QTY", qty);
            pktList.put(ageDsc+"_"+lab+"_VLU", vlu);
            
        }
        rs.close(); pst.close();
        String gtdiff = " select dsc, sum(qty) qty , sum(vlu) vlu , sum(trf_vlu) trf_vlu "+
                         " , decode(sum(trf_vlu), 0, null, trunc((sum(trf_vlu) - sum(vlu))/sum(trf_vlu)*100, 2)) diff_pct from ( "+
                        " select c.dsc , count(*) qty , sum(cts*quot) vlu , sum(cts*decode(nvl(Decode("+trfcmpPrp+",'NA',0,Null,0,"+trfcmpPrp+"), 0), 0, quot, "+trfcmpPrp+")) trf_vlu "+
                        " from gt_srch_rslt a, mrule b, rule_dtl c where b.rule_idn = c.rule_idn and b.mdl = 'AGE_GRP' and upper(b.nme_rule) = 'AGE GROUP' "+srchQ+
                       " and nvl(to_number(decode("+agePrp+",'NA',0,null,0,"+agePrp+")),0) between c.num_fr and c.num_to group by c.dsc) group by dsc order by 1, 2  ";

            outLst = db.execSqlLst("gtFetch", gtdiff, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
     while(rs.next()){
         String ageDsc = util.nvl(rs.getString("dsc"));
         String diffPct = util.nvl(rs.getString("diff_pct"));
         pktList.put(ageDsc+"_DIFF", diffPct);
     }
        rs.close(); pst.close();
        req.setAttribute("labList", labList);
        req.setAttribute("ageList", ageList);
        req.setAttribute("pktList", pktList);
            util.updAccessLog(req,res,"Stock Summary", "fetch end");
        return am.findForward("load");
        }
    }
    public ActionForward loadRt(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Stock Summary", "loadRt start");
        HKStockSummaryForm hkStockForm = (HKStockSummaryForm)form;
            GenericInterface genericInt = new GenericImpl();
        hkStockForm.reset();
        String reportPkg = "REPORT_PKG.mkt_smry(pMdl => ?)";
        ArrayList ary = new ArrayList();
        ary.add("STK_SMM_RPT");
        int ct = db.execCall("reportPkg", reportPkg, ary);
            genericInt.genericPrprVw(req, res, "STKSMMRPTLst","STK_SMM_RPT");
        ArrayList assortSrchList = genericInt.genricSrch(req,res,"RPTGNCSrch","STK_SMM_RPT_SRCH");
        info.setGncPrpLst(assortSrchList);
            util.updAccessLog(req,res,"Stock Summary", "loadRt end");
        return am.findForward("loadRt");
        }
    }
    public ActionForward viewRt(ActionMapping am, ActionForm form,
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
           util.updAccessLog(req,res,"Stock Summary", "viewRt start");
        HKStockSummaryForm hkStockForm = (HKStockSummaryForm)form;
           GenericInterface genericInt = new GenericImpl();
        String frmDte = (String)hkStockForm.getValue("frmdte");
        String toDte = (String)hkStockForm.getValue("todte");
        String prp = util.nvl((String)hkStockForm.getValue("prp"));
        ArrayList vWPrpList =genericInt.genericPrprVw(req, res, "STKSMMRPTLst","STK_SMM_RPT");;
        ArrayList assortSrchList = genericInt.genricSrch(req,res,"RPTGNCSrch","STK_SMM_RPT_SRCH");
        info.setGncPrpLst(assortSrchList);
        HashMap pktList = new HashMap();
       HashMap paramsMap = new HashMap();
       String srchQ="";
       HashMap dbinfo = info.getDmbsInfoLst();
       String sh = (String)dbinfo.get("SHAPE");
       String szval = (String)dbinfo.get("SIZE");
       String colval = (String)dbinfo.get("COL");
       String clrval = (String)dbinfo.get("CLR");
       int indexLB = vWPrpList.indexOf("LAB")+1;
       int indexSH = vWPrpList.indexOf(sh)+1;
       int indexSZ = vWPrpList.indexOf(szval)+1;
       int indexCol = vWPrpList.indexOf(colval)+1;
       int indexClr = vWPrpList.indexOf(clrval)+1;
       int indexTrfCmp = vWPrpList.indexOf("TRF_CMP")+1;
       int indexAge= vWPrpList.indexOf("AGE")+1;
       int indexdept= vWPrpList.indexOf("DEPT")+1;
       String lbPrp = "prp_00"+indexLB;
       String shPrp = "prp_00"+indexSH;
       String szPrp = "prp_00"+indexSZ;
       String colPrp = "prp_00"+indexCol;
       String clrPrp = "prp_00"+indexClr;
       String trfcmpPrp = "prp_00"+indexTrfCmp;
       String agePrp = "prp_00"+indexAge;
       String deptPrp = "prp_0"+indexdept;
       String lbSrt = "srt_00"+indexLB;
       String shSrt = "srt_00"+indexSH;
       String szSrt = "srt_00"+indexSZ;
       String colSrt = "srt_00"+indexCol;
       String clrSrt = "srt_00"+indexClr;
       paramsMap=fetchDept(form,req,res);
       if(paramsMap!=null && paramsMap.size()>0){
       for(int i=0;i<assortSrchList.size();i++){
       ArrayList prplist =(ArrayList)assortSrchList.get(i);
       String lprp = (String)prplist.get(0);
         
           String val1 = util.nvl((String)paramsMap.get(lprp+"_1"));          
           String val2 = util.nvl((String)paramsMap.get(lprp+"_2"));
        
               if(!val1.equals("") && !val2.equals("")){
               srchQ = " and "+deptPrp+" between '"+val1+"' and '"+val2+"' ";
              
            }
       }
       }
        String ttlVluSql = "select sum(cts*quot) vlu from gt_srch_rslt where 1=1 "+srchQ;

           ArrayList outLst = db.execSqlLst("ttVlu", ttlVluSql, new ArrayList());
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet rs = (ResultSet)outLst.get(1);
        if(rs.next()){
            pktList.put("TTLVLU", util.nvl(rs.getString("vlu")));
        }
       rs.close(); pst.close();
        String ttlDatRng = "select sum(cts*quot) dteRng from gt_srch_rslt where PKT_DTE BETWEEN to_date('"+frmDte+"' , 'dd-mm-yyyy') and to_date('"+toDte+"' , 'dd-mm-yyyy') "+srchQ;

           outLst = db.execSqlLst("ttlDatRng",ttlDatRng, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        if(rs.next()){
            pktList.put("TTLDTERNG", util.nvl(rs.getString("dteRng")));
        }
       rs.close(); pst.close();
        String str = "" ;
       String grpBy = "";
        if(prp.equals("SHAPE")){
            str = "  shSrt , sh ";
            grpBy = " shSrt , sh , lab ";
//        str = "  shSrt , sh||' '||lab ";
//        grpBy = " shSrt , sh||' '||lab  , lab ";
        
        } else if(prp.equals("COL")){
        str = " colSrt , col ";
        grpBy = " colSrt , col ";
        }else if(prp.equals("CLR")){
        str = " clrSrt , clr ";
        grpBy = " clrSrt , clr ";
        }else{
        str = " szSrt , sz  ";
        grpBy = " szSrt , sz  ";
        }
        
        String gtFetch = "select "+str+" dsc , count(*) qty, trunc(sum(cts*quot),2) vlu " + 
        ", sum(cts*decode(nvl(Decode(trf_cmp,'NA',0,Null,0,trf_cmp), 0), 0, quot, trf_cmp)) trf_vlu " + 
        ", round(avg(nvl(Decode(age,'NA',0,Null,0,age), 0))) avg_dys " + 
        ", sum(cts*quot*dte_rng) dte_rng_vlu " + 
        "from " + 
        "(select "+shPrp+" sh, "+shSrt+" shSrt , "+lbPrp+" lab , "+lbSrt+" labSrt, "+szPrp+" sz , "+szSrt+" szSrt , "+colPrp+" col , "+colSrt+" colSrt , "+clrPrp+" clr , "+clrSrt+" clrSrt, cts, quot, "+trfcmpPrp+" trf_cmp, "+agePrp+" age " + 
        ", CASE " + 
        "WHEN PKT_DTE BETWEEN to_date('"+frmDte+"' , 'dd-mm-yyyy') and to_date('"+toDte+"' , 'dd-mm-yyyy') Then 1 " + 
        "ELSE 0 " + 
        "END " + 
        "DTE_RNG " + 
        "from gt_srch_rslt a " + 
        "where 1 = 1" + srchQ+
        ") a" + 
        " group by "+grpBy+" " + 
        " order by 1 ";

           outLst = db.execSqlLst("gtFetch",gtFetch, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
       ArrayList dataList = new ArrayList();
       while(rs.next()){
           HashMap dataMap = new HashMap();
           dataMap.put("DSC", util.nvl(rs.getString("dsc")));
           dataMap.put("VLU", util.nvl(rs.getString("vlu")));
           dataMap.put("AVGDAY", util.nvl(rs.getString("avg_dys")));
           dataMap.put("DTERNGVLU", util.nvl(rs.getString("dte_rng_vlu")));
           dataList.add(dataMap);
       }
       rs.close(); pst.close();
       req.setAttribute("TTLMAP", pktList);
       req.setAttribute("dataList", dataList);
           util.updAccessLog(req,res,"Stock Summary", "viewRt end");
       return am.findForward("loadRt");
       }
   }
    
    public HashMap fetchDept(ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HKStockSummaryForm hkStockForm = (HKStockSummaryForm)form;
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp(); 
        HashMap paramsMap = new HashMap();
        ArrayList assortSrchList = (ArrayList)session.getAttribute("RPTGNCSrch");
        for(int i=0;i<assortSrchList.size();i++){
            ArrayList prplist =(ArrayList)assortSrchList.get(i);
            String lprp = (String)prplist.get(0);
            String typ = util.nvl((String)mprp.get(lprp+"T"));
            String fldVal1 = util.nvl((String)hkStockForm.getValue(lprp+"_1"));
            String fldVal2 = util.nvl((String)hkStockForm.getValue(lprp+"_2"));
            if(fldVal2.equals(""))
            fldVal2=fldVal1;
            if(typ.equals("C")){
            ArrayList prpSrtSize = (ArrayList)prp.get(lprp+"S");
            ArrayList prpValSize = (ArrayList)prp.get(lprp+"V");  
            if(!fldVal1.equals("")){
            fldVal1=(String)prpValSize.get(prpSrtSize.indexOf(fldVal1));
            fldVal2=(String)prpValSize.get(prpSrtSize.indexOf(fldVal2));
            }
            }
            if(typ.equals("T")){
                fldVal1 = util.nvl((String)hkStockForm.getValue(lprp+"_1"));
                fldVal2 = util.nvl((String)hkStockForm.getValue(lprp+"_2"));
            }
           
            if(!fldVal1.equals("") && !fldVal2.equals("")){
                paramsMap.put(lprp+"_1", fldVal1);
                paramsMap.put(lprp+"_2", fldVal2);               
            }             
        }  
        return paramsMap;
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
            util.updAccessLog(req,res,"Stock Summary", "createXL start");
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "AgeLabReport"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Stock Summary", "createXL end");
        return null;
        }
    }
    

    
//    public ArrayList ASGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("RPTGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'STK_SMM_RPT_SRCH' and flg in ('Y','S') order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("RPTGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }

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
                util.updAccessLog(req,res,"Stock Summary", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Stock Summary", "init");
            }
            }
            return rtnPg;
            }
}
