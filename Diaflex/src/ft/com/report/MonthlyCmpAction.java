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
import ft.com.dao.ObjBean;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.PacketPrintForm;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.sql.CallableStatement;
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

public class MonthlyCmpAction extends DispatchAction {

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
             util.updAccessLog(req,res,"Monthly Comparision", "load start");
             GenericInterface genericInt = new GenericImpl();
         MonthlyCmpForm udf = (MonthlyCmpForm)af;
         ArrayList empList = new ArrayList();
         udf.resetAll();
         String deptQ = "select a.nme_idn, trim(initcap(a.nme)) nme from nme_v a where a.typ = 'EMPLOYEE' " + 
                        "and exists (select 1 from iss_rtn b where a.nme_idn = b.emp_id) order by 2";

             ArrayList outLst = db.execSqlLst("stkDtl", deptQ, new ArrayList());
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet rs = (ResultSet)outLst.get(1);
         while(rs.next()){ 
             MNme emp = new MNme();
             emp.setEmp_idn(rs.getString("nme_idn"));
             emp.setEmp_nme(rs.getString("nme"));
             empList.add(emp);
         }
         rs.close(); pst.close();
         ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MonthCmpGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MonthCmpGNCSrch");
         info.setGncPrpLst(assortSrchList);
         udf.setEmpList(empList);
         udf.resetAll();
         String dtePrpQ="select to_char(trunc(sysdate-90), 'dd-mm-rrrr') frmdte,to_char(trunc(sysdate), 'dd-mm-rrrr') todte from dual";

             outLst = db.execSqlLst("Date calc", dtePrpQ,new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
         udf.setValue("dtefr",(util.nvl(rs.getString("frmdte"))));
         udf.setValue("dteto",(util.nvl(rs.getString("todte"))));
         }  
             rs.close(); pst.close();
             HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
             HashMap pageDtl=(HashMap)allPageDtl.get("MONTHLY_CMP");
             if(pageDtl==null || pageDtl.size()==0){
             pageDtl=new HashMap();
             pageDtl=util.pagedef("MONTHLY_CMP");
             allPageDtl.put("MONTHLY_CMP",pageDtl);
             }
             info.setPageDetails(allPageDtl);
             util.updAccessLog(req,res,"Monthly Comparision", "load end");
        return am.findForward("load");
         }
     }
    
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             util.updAccessLog(req,res,"Monthly Comparision", "fetch start");
         MonthlyCmpForm udf = (MonthlyCmpForm)af; 
         GenericInterface genericInt = new GenericImpl();
             ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MonthCmpGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MonthCmpGNCSrch");
         info.setGncPrpLst(genricSrchLst);
         HashMap prp = info.getPrp();
         HashMap mprp = info.getMprp();
         String dfr = util.nvl((String)udf.getValue("dtefr"));
         String dto = util.nvl((String)udf.getValue("dteto"));
         String memobtn = util.nvl((String)udf.getValue("memo"));
         String monthbtn = util.nvl((String)udf.getValue("monthwise"));
         String rpttyp = util.nvl((String)udf.getValue("rpttyp"),"D");
         String[] empLst=udf.getEmpLst();
         ArrayList monEmpLst=new ArrayList();
         ArrayList monEmpIdnLst=new ArrayList();
         ArrayList monthLst=new ArrayList();
         HashMap datatable=new HashMap();
         HashMap dbinfo = info.getDmbsInfoLst();
         String memoval = (String)dbinfo.get("MEMO");
         String colval = (String)dbinfo.get("COL");
         String clrval = (String)dbinfo.get("CLR");
         ArrayList ANLSVWList=genericInt.genericPrprVw(req, res, "memocomp_vw","MTH_COMP");
         int indexClr= ANLSVWList.indexOf(clrval)+1;
         int indexCol = ANLSVWList.indexOf(colval)+1;
         int indexMemo = ANLSVWList.indexOf(memoval)+1;
         String colPrp = util.prpsrtcolumn("prp",indexCol);
         String memoPrp = util.prpsrtcolumn("prp",indexMemo);
         String clrPrp = util.prpsrtcolumn("prp",indexClr);
         String colSrt = util.prpsrtcolumn("srt",indexCol);
         String clrSrt = util.prpsrtcolumn("srt",indexClr);
         String memoSrt = util.prpsrtcolumn("srt",indexMemo);
         int cnt=0;
         db.execUpd("delete", "delete from gt_srch_rslt", new ArrayList());
         db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
         ArrayList ary=new ArrayList();
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
         paramsMap.put("stt", "COMP");
         paramsMap.put("mdl", "COMP");
         paramsMap.put("flg", "NN");
         int lSrchId=util.genericSrchEntries(paramsMap);
         System.out.println(lSrchId);
         info.setSrchId(lSrchId);
//         String getSrchId = "srch_pkg.get_srch_id(pRlnId => ?, pTyp => ?, pTrmVal => ?, pMdl => ?, pFlg => ?, pSrchId => ?)";
//         ArrayList params = new ArrayList();
//         params.add(String.valueOf(0));
//         params.add("COMP");
//         params.add("1");
//         params.add("COMP");
//         params.add("NN");
//         ArrayList outParams = new ArrayList();
//         outParams.add("I");
//         CallableStatement cst = db.execCall(" Add Srch Hdr ", getSrchId, params, outParams);
//         try {
//         lSrchId = cst.getInt(params.size()+1);
//             System.out.println("Srch ID = >"+lSrchId);
//            cst.close();
//         } catch (SQLException e) {
//         }
//         
         String dateQ="to_date(?, 'dd-mm-rrrr') , to_date(?, 'dd-mm-rrrr') from dual";
         if(dfr.equals("") && dto.equals(""))
             dateQ="to_date(SYSDATE, 'dd-mm-rrrr') , to_date(SYSDATE, 'dd-mm-rrrr') from dual"; 
         if(dfr.equals("") && !dto.equals(""))
             dateQ="to_date(SYSDATE, 'dd-mm-rrrr') , to_date(?, 'dd-mm-rrrr') from dual";
         if(!dfr.equals("") && dto.equals(""))
             dateQ="to_date(?, 'dd-mm-rrrr') , to_date(SYSDATE, 'dd-mm-rrrr') from dual"; 
         String insrtAddon = " insert into srch_addon( srch_id , cprp ,cstt, frm_dte , to_dte ) "+
         "select ? , ? , ? ,"+dateQ;
         ary = new ArrayList();
         ary.add(String.valueOf(lSrchId));
         ary.add("RTN_DTE");
         ary.add("ALL");
         if(!dfr.equals("") && !dto.equals("")){
         ary.add(dfr);
         ary.add(dto);
         }
         if(!dfr.equals("") && dto.equals(""))
         ary.add(dfr);
         if(dfr.equals("") && !dto.equals(""))
         ary.add(dto);
         cnt = db.execUpd("insert RTN_DTE", insrtAddon, ary);
         
         String empidn="";
         for(int i=0;i<empLst.length;i++){
         insrtAddon = " insert into srch_addon( srch_id , cprp , cidn) "+
         "select ? , ? , ? from dual ";
         empidn=empLst[i];  
         ary = new ArrayList();
         ary.add(String.valueOf(lSrchId));
         ary.add("EMPLOYEE");
         ary.add(empidn);
         if(!empidn.equals(""))
         cnt = db.execUpd("", insrtAddon, ary);
         }
         
         
         if(memobtn.length()==0){
         ResultSet rs=null;
         String monthCmp ="REPORT_PKG.ASSORT_EMP_WS(pSrchId =>?)";
         ary = new ArrayList();
         ary.add(String.valueOf(lSrchId));
         int ct = db.execCall("monthCmp", monthCmp, ary);

         ArrayList gridLst=new ArrayList();
         HashMap ttlmap=new HashMap();
         gridLst.add(colval);
         gridLst.add(clrval);
         String key="EMP";
         if(!monthbtn.equals("")){
             key="MONTH";
             datatable.put("ROW","MONTH");
             datatable.put("COL","EMP"); 
             datatable.put("HEAD","Month");
         }else{
             datatable.put("ROW","EMP");
             datatable.put("COL","MONTH");
             datatable.put("HEAD","Employee");
         }
             datatable.put("RPTTYP",rpttyp);
         for(int i=0;i<gridLst.size();i++){
         String grid=(String)gridLst.get(i);
         int indexGRID = ANLSVWList.indexOf(grid)+1;   
         String gridPrp = util.prpsrtcolumn("prp",indexGRID);
         String gridSrt = util.prpsrtcolumn("srt",indexGRID);
             String srchQ=" select n.nme ,  n.nme_idn \n" + 
             ", to_number(to_char(b.pkt_dte, 'rrrrmm')) srt_mon, to_char(b.pkt_dte, 'MONrrrr') MON_YR\n" + 
             ", count(*) cnt , '+' Dsc\n" + 
             " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
             " where a.stk_idn = b.stk_idn and a."+gridSrt+" < b."+gridSrt+"\n" + 
             " and b.rln_idn = n.nme_idn\n" + 
             " group by  n.nme, n.nme_idn,  to_number(to_char(b.pkt_dte, 'rrrrmm')), to_char(b.pkt_dte, 'MONrrrr')\n" + 
             " UNION\n" + 
             " select n.nme ,  n.nme_idn \n" + 
             ", to_number(to_char(b.pkt_dte, 'rrrrmm')) srt_mon, to_char(b.pkt_dte, 'MONrrrr') MON_YR\n" + 
             ", count(*) cnt, '=' Dsc\n" + 
             " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
             " where a.stk_idn = b.stk_idn and a."+gridSrt+" = b."+gridSrt+"\n" + 
             " and b.rln_idn = n.nme_idn\n" + 
             " group by  n.nme,  n.nme_idn , to_number(to_char(b.pkt_dte, 'rrrrmm')), to_char(b.pkt_dte, 'MONrrrr')\n" + 
             " UNION\n" + 
             " select n.nme ,  n.nme_idn \n" + 
             ", to_number(to_char(b.pkt_dte, 'rrrrmm')) srt_mon, to_char(b.pkt_dte, 'MONrrrr') MON_YR\n" + 
             ", count(*) cnt, '-' Dsc\n" + 
             " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
             " where a.stk_idn = b.stk_idn and a."+gridSrt+" > b."+gridSrt+"\n" + 
             " and b.rln_idn = n.nme_idn\n" + 
             " group by  n.nme, n.nme_idn , to_number(to_char(b.pkt_dte, 'rrrrmm')), to_char(b.pkt_dte, 'MONrrrr')\n" + 
             " UNION\n" + 
             " select n.nme ,  n.nme_idn \n" + 
             ", to_number(to_char(b.pkt_dte, 'rrrrmm')) srt_mon, to_char(b.pkt_dte, 'MONrrrr') MON_YR\n" + 
             ", count(*) cnt,  'Total' Dsc\n" + 
             " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
             " where a.stk_idn = b.stk_idn\n" + 
             " and b.rln_idn = n.nme_idn\n" + 
             " group by n.nme, n.nme_idn , to_number(to_char(b.pkt_dte, 'rrrrmm')), to_char(b.pkt_dte, 'MONrrrr')\n" + 
             " order by 1,2,3"  ;
         

             ArrayList outLst = db.execSqlLst("srchQ", srchQ, new ArrayList());
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) {
          HashMap data = new HashMap();
          String nme=util.nvl(rs.getString("nme"));   
          String nmeIdn= util.nvl(rs.getString("nme_idn"));
          String month=util.nvl(rs.getString("MON_YR")); 
          String dsc=util.nvl(rs.getString("Dsc"));
          if(!monEmpIdnLst.contains(nmeIdn)){
             monEmpIdnLst.add(nmeIdn);
             datatable.put(nmeIdn,nme);   
          }   
          data.put("NME",nme);   
          data.put("MON",month); 
          data.put("CNT",util.nvl(rs.getString("cnt")));
          data.put("DSC",dsc);
          if(key.equals("EMP"))
          datatable.put(nmeIdn+"_"+month+"_"+dsc+"_"+grid,data); 
          else
          datatable.put(month+"_"+nmeIdn+"_"+dsc+"_"+grid,data);     
         }
         rs.close(); pst.close();
             if(key.equals("EMP")){
             srchQ=" select n.nme ,  n.nme_idn" +
                          ", count(*) cnt ,trunc(sum(a.rap_rte*trunc(a.cts,2)),0) curVal,trunc(sum(nvl(b.rap_rte,a.rap_rte)*trunc(a.cts,2)),0) assortVal, '+' Dsc\n" + 
                          " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
                          " where a.stk_idn = b.stk_idn and a."+gridSrt+" < b."+gridSrt+"\n" + 
                          " and b.rln_idn = n.nme_idn\n" + 
                          " group by n.nme ,  n.nme_idn" + 
                          " UNION\n" + 
                          " select n.nme ,  n.nme_idn" +
                          ", count(*) cnt,trunc(sum(a.rap_rte*trunc(a.cts,2)),0) curVal,trunc(sum(nvl(b.rap_rte,a.rap_rte)*trunc(a.cts,2)),0) assortVal, '=' Dsc\n" + 
                          " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
                          " where a.stk_idn = b.stk_idn and a."+gridSrt+" = b."+gridSrt+"\n" + 
                          " and b.rln_idn = n.nme_idn\n" + 
                          " group by n.nme ,  n.nme_idn" +
                          " UNION\n" + 
                          " select n.nme ,  n.nme_idn" + 
                          ", count(*) cnt,trunc(sum(a.rap_rte*trunc(a.cts,2)),0) curVal,trunc(sum(nvl(b.rap_rte,a.rap_rte)*trunc(a.cts,2)),0) assortVal, '-' Dsc\n" + 
                          " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
                          " where a.stk_idn = b.stk_idn and a."+gridSrt+" > b."+gridSrt+"\n" + 
                          " and b.rln_idn = n.nme_idn\n" + 
                          " group by n.nme ,  n.nme_idn" +
                          " UNION\n" + 
                          " select n.nme ,  n.nme_idn" +
                          ", count(*) cnt,trunc(sum(a.rap_rte*trunc(a.cts,2)),0) curVal,trunc(sum(nvl(b.rap_rte,a.rap_rte)*trunc(a.cts,2)),0) assortVal,  'Total' Dsc\n" + 
                          " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
                          " where a.stk_idn = b.stk_idn\n" + 
                          " and b.rln_idn = n.nme_idn\n" + 
                          " group by n.nme ,  n.nme_idn";
                      
             }else{
                 srchQ=" select  to_number(to_char(b.pkt_dte, 'rrrrmm')) srt_mon, to_char(b.pkt_dte, 'MONrrrr') MON_YR\n" +
                              ", count(*) cnt ,trunc(sum(a.rap_rte*trunc(a.cts,2)),0) curVal,trunc(sum(nvl(b.rap_rte,a.rap_rte)*trunc(a.cts,2)),0) assortVal, '+' Dsc\n" + 
                              " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
                              " where a.stk_idn = b.stk_idn and a."+gridSrt+" < b."+gridSrt+"\n" + 
                              " and b.rln_idn = n.nme_idn\n" + 
                              " group by to_number(to_char(b.pkt_dte, 'rrrrmm')), to_char(b.pkt_dte, 'MONrrrr')\n" + 
                              " UNION\n" + 
                              " select to_number(to_char(b.pkt_dte, 'rrrrmm')) srt_mon, to_char(b.pkt_dte, 'MONrrrr') MON_YR\n" +
                              ", count(*) cnt,trunc(sum(a.rap_rte*trunc(a.cts,2)),0) curVal,trunc(sum(nvl(b.rap_rte,a.rap_rte)*trunc(a.cts,2)),0) assortVal, '=' Dsc\n" + 
                              " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
                              " where a.stk_idn = b.stk_idn and a."+gridSrt+" = b."+gridSrt+"\n" + 
                              " and b.rln_idn = n.nme_idn\n" + 
                              " group by to_number(to_char(b.pkt_dte, 'rrrrmm')), to_char(b.pkt_dte, 'MONrrrr')\n" +
                              " UNION\n" + 
                              " select to_number(to_char(b.pkt_dte, 'rrrrmm')) srt_mon, to_char(b.pkt_dte, 'MONrrrr') MON_YR\n" +
                              ", count(*) cnt,trunc(sum(a.rap_rte*trunc(a.cts,2)),0) curVal,trunc(sum(nvl(b.rap_rte,a.rap_rte)*trunc(a.cts,2)),0) assortVal,'-' Dsc\n" + 
                              " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
                              " where a.stk_idn = b.stk_idn and a."+gridSrt+" > b."+gridSrt+"\n" + 
                              " and b.rln_idn = n.nme_idn\n" + 
                              " group by to_number(to_char(b.pkt_dte, 'rrrrmm')), to_char(b.pkt_dte, 'MONrrrr')\n" +
                              " UNION\n" + 
                              " select to_number(to_char(b.pkt_dte, 'rrrrmm')) srt_mon, to_char(b.pkt_dte, 'MONrrrr') MON_YR\n" +
                              ", count(*) cnt,trunc(sum(a.rap_rte*trunc(a.cts,2)),0) curVal,trunc(sum(nvl(b.rap_rte,a.rap_rte)*trunc(a.cts,2)),0) assortVal,  'Total' Dsc\n" + 
                              " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
                              " where a.stk_idn = b.stk_idn\n" + 
                              " and b.rln_idn = n.nme_idn\n" + 
                              " group by to_number(to_char(b.pkt_dte, 'rrrrmm')), to_char(b.pkt_dte, 'MONrrrr')\n";
             }

             outLst = db.execSqlLst("srchQ", srchQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
                      while (rs.next()) {
                       String grp="";
                       if(key.equals("EMP"))
                       grp=util.nvl(rs.getString("nme_idn"));   
                       else
                       grp=util.nvl(rs.getString("MON_YR"));  
                       String dsc=util.nvl(rs.getString("Dsc")); 
                       ttlmap.put(grp+"_"+dsc+"_"+grid,util.nvl(rs.getString("cnt"))); 
                       ttlmap.put(grp+"_"+dsc+"_"+grid+"_CURVAL",util.nvl(rs.getString("curVal"))); 
                       ttlmap.put(grp+"_"+dsc+"_"+grid+"_ASRTVAL",util.nvl(rs.getString("assortVal"))); 
                      }
                      rs.close(); pst.close();
         }

         String monQ="Select Distinct(To_Char(Pkt_Dte, 'MONrrrr')) Mon_Yr,To_Char(Pkt_Dte, 'rrrrmm') Mon_Yr1 From Gt_Srch_Multi\n" + 
         "order by MON_YR1";

             ArrayList  outLst = db.execSqlLst("srchQ", monQ, new ArrayList());
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) {
             monthLst.add(util.nvl(rs.getString("Mon_Yr")));
         }
         rs.close(); pst.close();
         req.setAttribute("View", "Y");
         
         if(key.equals("EMP")){
             session.setAttribute("rowLst", monEmpIdnLst);
             session.setAttribute("colLst", monthLst);
         }else{
             session.setAttribute("rowLst", monthLst);
             session.setAttribute("colLst", monEmpIdnLst);
         }
         session.setAttribute("datatable", datatable);
         session.setAttribute("gridLst", gridLst);
         session.setAttribute("ttlmap", ttlmap);
         }else{
             String monthCmp ="REPORT_PKG.ASSORT_MEMO_WS(pSrchId =>?)";
             ary = new ArrayList();
             ary.add(String.valueOf(lSrchId));
             int ct = db.execCall("monthCmp", monthCmp, ary);
         ArrayList itemLst=new ArrayList();
         ArrayList memoLst=new ArrayList(); 
         String key="";
         String memochk="";
         String coldataQ="select a."+memoPrp+",a."+colPrp+",a."+colSrt+",count(*) cnt , '+' Dsc\n" + 
         " From Gt_Srch_Rslt A, Gt_Srch_Multi B, Nme_V N\n" + 
         " where a.stk_idn = b.stk_idn and a."+colSrt+" < b."+colSrt+"\n" + 
         " And B.Rln_Idn = N.Nme_Idn\n" + 
         " Group by a."+memoPrp+",a."+colPrp+",a."+colSrt+"\n"+
         " Union\n" + 
         "select a."+memoPrp+",a."+colPrp+",a."+colSrt+",count(*) cnt , '=' Dsc\n" + 
         " From Gt_Srch_Rslt A, Gt_Srch_Multi B, Nme_V N\n" + 
         " where a.stk_idn = b.stk_idn and a."+colSrt+" = b."+colSrt+"\n" + 
         " And B.Rln_Idn = N.Nme_Idn\n" + 
         " Group by a."+memoPrp+",a."+colPrp+",a."+colSrt+"\n"+
         " Union\n" + 
         "select a."+memoPrp+",a."+colPrp+",a."+colSrt+",count(*) cnt , '-' Dsc\n" + 
         " From Gt_Srch_Rslt A, Gt_Srch_Multi B, Nme_V N\n" + 
         " where a.stk_idn = b.stk_idn and a."+colSrt+" > b."+colSrt+"\n" + 
         " And B.Rln_Idn = N.Nme_Idn\n" + 
         " Group by a."+memoPrp+",a."+colPrp+",a."+colSrt+"\n"+
         " Union\n" + 
         "select a."+memoPrp+",a."+colPrp+",a."+colSrt+",count(*) cnt , 'Total' Dsc\n" + 
         " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
         " where a.stk_idn = b.stk_idn\n" + 
         " And B.Rln_Idn = N.Nme_Idn\n" + 
         " Group by a."+memoPrp+",a."+colPrp+",a."+colSrt+"\n"+
         " Order By 1,3";

             ArrayList outLst = db.execSqlLst("coldataQ", coldataQ, new ArrayList());
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet rs = (ResultSet)outLst.get(1);
         while(rs.next()){
             String memoVal = util.nvl(rs.getString(memoPrp));
             String colVal = util.nvl(rs.getString(colPrp));
             String dsc=util.nvl(rs.getString("Dsc"));
             key=memoVal+"_"+colVal+"_"+dsc+"_COL";
             datatable.put(key,util.nvl(rs.getString("cnt")));  
             if(!memoLst.contains(memoVal))
                 memoLst.add(memoVal);
             if(memochk.equals("") || !memochk.equals(memoVal)){   
             datatable.put(memochk+"_COL",itemLst);
             itemLst=new ArrayList();
             memochk=memoVal; 
             }
             if(!itemLst.contains(colVal))
             itemLst.add(colVal); 
         }
             rs.close(); pst.close();
         datatable.put(memochk+"_COL",itemLst);
         itemLst=new ArrayList();
         memochk="";
         String clrdataQ="select a."+memoPrp+",a."+clrPrp+",a."+clrSrt+",count(*) cnt , '+' Dsc\n" + 
         " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
         " where a.stk_idn = b.stk_idn and a."+clrSrt+" < b."+clrSrt+"\n" + 
         " And B.Rln_Idn = N.Nme_Idn\n" + 
         " Group by a."+memoPrp+",a."+clrPrp+",a."+clrSrt+"\n" + 
         " Union\n" + 
         "select a."+memoPrp+",a."+clrPrp+",a."+clrSrt+",count(*) cnt , '=' Dsc\n" + 
         " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
         " where a.stk_idn = b.stk_idn and a."+clrSrt+" = b."+clrSrt+"\n" + 
         " And B.Rln_Idn = N.Nme_Idn\n" + 
         " Group by a."+memoPrp+",a."+clrPrp+",a."+clrSrt+"\n" + 
         " Union\n" + 
         "select a."+memoPrp+",a."+clrPrp+",a."+clrSrt+",count(*) cnt , '-' Dsc\n" + 
         " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
         " where a.stk_idn = b.stk_idn and a."+clrSrt+" > b."+clrSrt+"\n" + 
         " And B.Rln_Idn = N.Nme_Idn\n" + 
         " Group by a."+memoPrp+",a."+clrPrp+",a."+clrSrt+"\n" + 
         " Union\n" + 
         "select a."+memoPrp+",a."+clrPrp+",a."+clrSrt+",count(*) cnt , 'Total' Dsc\n" + 
         " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
         " where a.stk_idn = b.stk_idn\n" + 
         " And B.Rln_Idn = N.Nme_Idn\n" + 
         " Group by a."+memoPrp+",a."+clrPrp+",a."+clrSrt+"\n" + 
         " Order By 1,3";

             outLst = db.execSqlLst("coldataQ", clrdataQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while(rs.next()){
                 String memoVal = util.nvl(rs.getString(memoPrp));
                 String clrVal = util.nvl(rs.getString(clrPrp));
                 String dsc=util.nvl(rs.getString("Dsc"));
                 key=memoVal+"_"+clrVal+"_"+dsc+"_CLA";
                 datatable.put(key,util.nvl(rs.getString("cnt")));  
                 if(memochk.equals("") || !memochk.equals(memoVal)){   
                 datatable.put(memochk+"_CLA",itemLst);
                 itemLst=new ArrayList();
                 memochk=memoVal; 
                 }
                 if(!itemLst.contains(clrVal))
                 itemLst.add(clrVal); 
             }
             datatable.put(memochk+"_CLA",itemLst);
             rs.close(); pst.close();
        String memocolQ="select a."+memoPrp+",count(*) cnt , '+' Dsc\n" + 
         " From Gt_Srch_Rslt A, Gt_Srch_Multi B, Nme_V N\n" + 
         " where a.stk_idn = b.stk_idn and a."+colSrt+" < b."+colSrt+"\n" + 
         " And B.Rln_Idn = N.Nme_Idn\n" + 
         " Group by a."+memoPrp+"\n"+
         " Union\n" + 
         "select a."+memoPrp+",count(*) cnt , '=' Dsc\n" + 
         " From Gt_Srch_Rslt A, Gt_Srch_Multi B, Nme_V N\n" + 
         " where a.stk_idn = b.stk_idn and a."+colSrt+" = b."+colSrt+"\n" + 
         " And B.Rln_Idn = N.Nme_Idn\n" + 
         " Group by a."+memoPrp+"\n"+
         " Union\n" + 
         "select a."+memoPrp+",count(*) cnt , '-' Dsc\n" + 
         " From Gt_Srch_Rslt A, Gt_Srch_Multi B, Nme_V N\n" + 
         " where a.stk_idn = b.stk_idn and a."+colSrt+" > b."+colSrt+"\n" + 
         " And B.Rln_Idn = N.Nme_Idn\n" + 
         " Group by a."+memoPrp+"\n"+
         " Union\n" + 
         "select a."+memoPrp+",count(*) cnt , 'Total' Dsc\n" + 
         " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
         " where a.stk_idn = b.stk_idn\n" + 
         " And B.Rln_Idn = N.Nme_Idn\n" + 
         " Group by a."+memoPrp+"\n"+
         " Order By 1";

             outLst = db.execSqlLst("memocolQ", memocolQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while(rs.next()){
             String memoVal = util.nvl(rs.getString(memoPrp));
             String dsc=util.nvl(rs.getString("Dsc"));
             key=memoVal+"_"+dsc+"_COL_TTL";
             datatable.put(key,util.nvl(rs.getString("cnt")));
         }
             rs.close(); pst.close();
         String memoclrQ="select a."+memoPrp+",count(*) cnt , '+' Dsc\n" + 
         " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
         " where a.stk_idn = b.stk_idn and a."+clrSrt+" < b."+clrSrt+"\n" + 
         " And B.Rln_Idn = N.Nme_Idn\n" + 
         " Group by a."+memoPrp+"\n" + 
         " Union\n" + 
         "select a."+memoPrp+",count(*) cnt , '=' Dsc\n" + 
         " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
         " where a.stk_idn = b.stk_idn and a."+clrSrt+" = b."+clrSrt+"\n" + 
         " And B.Rln_Idn = N.Nme_Idn\n" + 
         " Group by a."+memoPrp+"\n" + 
         " Union\n" + 
         "select a."+memoPrp+",count(*) cnt , '-' Dsc\n" + 
         " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
         " where a.stk_idn = b.stk_idn and a."+clrSrt+" > b."+clrSrt+"\n" + 
         " And B.Rln_Idn = N.Nme_Idn\n" + 
         " Group by a."+memoPrp+"\n" + 
         " Union\n" + 
         "select a."+memoPrp+",count(*) cnt , 'Total' Dsc\n" + 
         " from gt_srch_rslt a, gt_srch_multi b, nme_v n\n" + 
         " where a.stk_idn = b.stk_idn\n" + 
         " And B.Rln_Idn = N.Nme_Idn\n" + 
         " Group by a."+memoPrp+"\n" + 
         " Order By 1";

             outLst = db.execSqlLst("memoclrQ", memoclrQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while(rs.next()){
         String memoVal = util.nvl(rs.getString(memoPrp));
         String dsc=util.nvl(rs.getString("Dsc"));
         key=memoVal+"_"+dsc+"_CLA_TTL";
         datatable.put(key,util.nvl(rs.getString("cnt")));
         }
             rs.close(); pst.close();
             session.setAttribute("memoLst", memoLst);
             session.setAttribute("datatable", datatable);
             req.setAttribute("View", "N");
         }
         udf.resetAll();
             req.setAttribute("srchDscription", util.srchDscription(String.valueOf(info.getSrchId())));
             util.updAccessLog(req,res,"Monthly Comparision", "fetch end");
         return am.findForward("load");
         }
     }
    
    
    public ActionForward grpWiseRpt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        util.updAccessLog(req,res,"Monthly Comparision", "grpWiseRpt start");
        GenericInterface genericInt = new GenericImpl();
    MonthlyCmpForm udf = (MonthlyCmpForm)af; 
    HashMap dbinfo = info.getDmbsInfoLst();
    String sh = (String)dbinfo.get("SHAPE");
    String szval = (String)dbinfo.get("SIZE");
    ArrayList allowGrpVw = genericInt.genericPrprVw(req,res,"ALLOWGRP_PRPVW","ALLOWGRP_PRPVW");   
    ArrayList ANLSVWList=genericInt.genericPrprVw(req, res, "memocomp_vw","MTH_COMP");
    HashMap grpdatatable =new HashMap();
    ArrayList szGrpLst =new ArrayList();
    ArrayList lprpGrpLst =new ArrayList();
    ResultSet rs=null;
        int indexsh = ANLSVWList.indexOf(sh)+1;   
        String shPrp = util.prpsrtcolumn("prp",indexsh);
        String shSrt = util.prpsrtcolumn("srt",indexsh);
        int indexsz = ANLSVWList.indexOf(szval)+1;   
        String szPrp = util.prpsrtcolumn("prp",indexsz);
        String szSrt = util.prpsrtcolumn("srt",indexsz);
    for(int i=0;i<allowGrpVw.size();i++){
        lprpGrpLst =new ArrayList();
        String lprp=(String)allowGrpVw.get(i);
        int indexlprp = ANLSVWList.indexOf(lprp)+1;   
        String lprpPrp = util.prpsrtcolumn("prp",indexlprp);
        String lprpSrt = util.prpsrtcolumn("srt",indexlprp);
        
        String srchQ="  select decode(a."+shPrp+",'RD','ROUND','ROUND','ROUND','FANCY') shape,sz.grp szgrp,pgrp.grp, count(*) cnt , '+' Dsc\n" + 
        " from gt_srch_rslt a, gt_srch_multi ir, prp pgrp,prp sz \n" + 
        " where a.stk_idn = ir.stk_idn \n" + 
        " and ir."+lprpSrt+"=pgrp.srt and pgrp.mprp='"+lprp+"'\n" + 
        " and a."+szPrp+"=sz.val and sz.mprp='"+szval+"'\n" + 
        " and a."+lprpSrt+" < ir."+lprpSrt+"\n" + 
        " group by decode(a."+shPrp+",'RD','ROUND','ROUND','ROUND','FANCY'),sz.grp,pgrp.grp\n" + 
        "  UNION\n" + 
        " select decode(a."+shPrp+",'RD','ROUND','ROUND','ROUND','FANCY') shape,sz.grp szgrp,pgrp.grp, count(*) cnt, '=' Dsc\n" + 
        " from gt_srch_rslt a, gt_srch_multi ir, prp pgrp ,prp sz\n" + 
        " where a.stk_idn = ir.stk_idn \n" + 
        " and ir."+lprpSrt+"=pgrp.srt and pgrp.mprp='"+lprp+"'\n" + 
        "  and a."+szPrp+"=sz.val and sz.mprp='"+szval+"'\n" + 
        " and nvl(a."+lprpSrt+",ir."+lprpSrt+") = nvl(ir."+lprpSrt+",a."+lprpSrt+")\n" + 
        "group by decode(a."+shPrp+",'RD','ROUND','ROUND','ROUND','FANCY'),sz.grp,pgrp.grp\n" + 
        " UNION\n" + 
        " select decode(a."+shPrp+",'RD','ROUND','ROUND','ROUND','FANCY') shape,sz.grp szgrp,pgrp.grp, count(*) cnt, '-' Dsc\n" + 
        " from gt_srch_rslt a, gt_srch_multi ir, prp pgrp ,prp sz\n" + 
        " where a.stk_idn = ir.stk_idn \n" + 
        " and ir."+lprpSrt+"=pgrp.srt and pgrp.mprp='"+lprp+"'\n" + 
        "  and a."+szPrp+"=sz.val and sz.mprp='"+szval+"'\n" + 
        " and a."+lprpSrt+" > ir."+lprpSrt+"\n" + 
        "group by decode(a."+shPrp+",'RD','ROUND','ROUND','ROUND','FANCY'),sz.grp,pgrp.grp\n" + 
        " UNION\n" + 
        " select decode(a."+shPrp+",'RD','ROUND','ROUND','ROUND','FANCY') shape,sz.grp szgrp,'GRPTotal' grp, count(*) cnt,  'Total' Dsc\n" + 
        " from gt_srch_rslt a, gt_srch_multi ir,prp sz\n" + 
        " where a.stk_idn = ir.stk_idn \n" + 
        " and a."+szPrp+"=sz.val and sz.mprp='"+szval+"'\n" + 
        "group by decode(a."+shPrp+",'RD','ROUND','ROUND','ROUND','FANCY'),sz.grp";

        ArrayList outLst = db.execSqlLst("srchQ", srchQ, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String shape=util.nvl(rs.getString("shape"));
            String szgrp=util.nvl(rs.getString("szgrp"));
            String grp=util.nvl(rs.getString("grp"));
            String dsc=util.nvl(rs.getString("Dsc"));
            grpdatatable.put(shape+"_"+szgrp+"_"+grp+"_"+dsc+"_"+lprp, util.nvl(rs.getString("cnt"))) ;
            if(!szGrpLst.contains(szgrp))
                szGrpLst.add(szgrp); 
            if(!lprpGrpLst.contains(grp) && !grp.equals("GRPTotal"))
                lprpGrpLst.add(grp); 
        }
        rs.close(); pst.close();
        Collections.sort(lprpGrpLst);
        grpdatatable.put(lprp,lprpGrpLst);
        
        srchQ="  select decode(a."+shPrp+",'RD','ROUND','ROUND','ROUND','FANCY') shape, count(*) cnt , '+' Dsc\n" + 
        " from gt_srch_rslt a, gt_srch_multi ir\n" + 
        " where a.stk_idn = ir.stk_idn \n" + 
        " and a."+lprpSrt+" < ir."+lprpSrt+"\n" + 
        " group by decode(a."+shPrp+",'RD','ROUND','ROUND','ROUND','FANCY')\n" + 
        "  UNION\n" + 
        " select decode(a."+shPrp+",'RD','ROUND','ROUND','ROUND','FANCY') shape, count(*) cnt, '=' Dsc\n" + 
        " from gt_srch_rslt a, gt_srch_multi ir\n" + 
        " where a.stk_idn = ir.stk_idn \n" + 
        " and nvl(a."+lprpSrt+",ir."+lprpSrt+") = nvl(ir."+lprpSrt+",a."+lprpSrt+")\n" + 
        "group by decode(a."+shPrp+",'RD','ROUND','ROUND','ROUND','FANCY')\n" + 
        " UNION\n" + 
        " select decode(a."+shPrp+",'RD','ROUND','ROUND','ROUND','FANCY') shape, count(*) cnt, '-' Dsc\n" + 
        " from gt_srch_rslt a, gt_srch_multi ir\n" + 
        " where a.stk_idn = ir.stk_idn \n" + 
        " and a."+lprpSrt+" > ir."+lprpSrt+"\n" + 
        "group by decode(a."+shPrp+",'RD','ROUND','ROUND','ROUND','FANCY')\n" + 
        " UNION\n" + 
        " select decode(a."+shPrp+",'RD','ROUND','ROUND','ROUND','FANCY') shape, count(*) cnt,  'Total' Dsc\n" + 
        " from gt_srch_rslt a, gt_srch_multi ir\n" + 
        " where a.stk_idn = ir.stk_idn \n" + 
        "group by decode(a."+shPrp+",'RD','ROUND','ROUND','ROUND','FANCY')\n"+
        " UNION\n" + 
        " select 'SHTotal' shape, count(*) cnt,  'Total' Dsc\n" + 
        " from gt_srch_rslt a, gt_srch_multi ir\n" + 
        " where a.stk_idn = ir.stk_idn ";

        outLst = db.execSqlLst("srchQ", srchQ, new ArrayList());
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String shape=util.nvl(rs.getString("shape"));
            String dsc=util.nvl(rs.getString("Dsc"));
            grpdatatable.put(shape+"_"+dsc+"_"+lprp, util.nvl(rs.getString("cnt"))) ;
        }
        rs.close(); pst.close();
        
    }
        if(szGrpLst.size()>0){
        Collections.sort(szGrpLst);
        grpdatatable.put("SIZEGRP",szGrpLst);
        }
        session.setAttribute("grpdatatable", grpdatatable);
        req.setAttribute("View", "Y");
        util.updAccessLog(req,res,"Monthly Comparision", "grpWiseRpt end");
    return am.findForward("grpWiseRpt");
    }
    }
    public ActionForward moncmpPKTDTL(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Monthly Comparision", "moncmpPKTDTL start");
        GenericInterface genericInt = new GenericImpl();
        ArrayList pktList = new ArrayList();
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "memocomp_vw","MTH_COMP");
        ArrayList itemHdr = new ArrayList();
        ArrayList ary = new ArrayList();
        String sign = util.nvl(req.getParameter("sign"));
        String cnt = util.nvl(req.getParameter("cnt"));
        String key = util.nvl(req.getParameter("key"));
        String prp = "prp_00"+cnt;
        String srt = "srt_00"+cnt;
        String nmeIdn = util.nvl(req.getParameter("nmeIdn"));
        String memoVal = util.nvl(req.getParameter("memoVal"));
        String row = util.nvl(req.getParameter("row"));
        
        itemHdr.add("Sr");
        itemHdr.add("VNM");
        String  srchQ =  " select a.stk_idn , a.pkt_ty,  a.vnm, a.pkt_dte, a.stt ,a.dsp_stt, a.qty , a.cts , a.rmk ";
        for (int i = 0; i < vwPrpLst.size(); i++) {
            String fld = "a.prp_";
            int j = i + 1;
            if (j < 10)
                fld += "00" + j;
            else if (j < 100)
                fld += "0" + j;
            else if (j > 100)
                fld += j;

            srchQ += ", " + fld;
            itemHdr.add((String)vwPrpLst.get(i));
        }
        
        int sr=1;
        String rsltQ = srchQ + " from gt_srch_rslt a, gt_srch_multi b "+
                       " where a.stk_idn = b.stk_idn ";
        if(sign.equals("P"))
        rsltQ=rsltQ+" and a."+srt+" < b."+srt;
        if(sign.equals("E"))
        rsltQ=rsltQ+" and a."+srt+" = b."+srt;
        if(sign.equals("M"))
        rsltQ=rsltQ+" and a."+srt+" > b."+srt;
        if(!nmeIdn.equals("") && key.equals("EMP")){
        rsltQ=rsltQ+" and b.rln_idn =? ";    
        ary.add(nmeIdn);
        }
        if(!nmeIdn.equals("")){
        rsltQ=rsltQ+" and b.rln_idn =? ";    
        ary.add(nmeIdn);
        }
        if(!row.equals("")  && key.equals("EMP")){
        rsltQ=rsltQ+" and b.rln_idn =? ";    
        ary.add(row);
        }
        if(!row.equals("")  && key.equals("MONTH")){
            rsltQ=rsltQ+" and to_char(b.pkt_dte, 'MONrrrr')=? ";    
            ary.add(row);
        }
        rsltQ=rsltQ+" order by a.sk1 , a.cts ";

            ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
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
                for(int j=0; j < vwPrpLst.size(); j++){
                     String lprp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                     
                        
                        pktPrpMap.put(lprp, val);
                         }
                              
                    pktList.add(pktPrpMap);
                }
            rs.close(); pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("pktList", pktList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Monthly Comparision", "moncmpPKTDTL end");
        return am.findForward("pktDtl");
        }
    }
    
    public ActionForward moncmpGrpPKTDTL(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Monthly Comparision", "moncmpGrpPKTDTL start");
        GenericInterface genericInt = new GenericImpl();
        ArrayList pktList = new ArrayList();
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "memocomp_vw","MTH_COMP");
        ArrayList itemHdr = new ArrayList();
        ArrayList ary = new ArrayList();
            HashMap dbinfo = info.getDmbsInfoLst();
            ArrayList ANLSVWList=genericInt.genericPrprVw(req, res, "memocomp_vw","MTH_COMP");
            String sh = (String)dbinfo.get("SHAPE");
            String szval = (String)dbinfo.get("SIZE");
            int indexsh = ANLSVWList.indexOf(sh)+1;   
            String shPrp = util.prpsrtcolumn("prp",indexsh);
            String shSrt = util.prpsrtcolumn("srt",indexsh);
            int indexsz = ANLSVWList.indexOf(szval)+1;   
            String szPrp = util.prpsrtcolumn("prp",indexsz);
            String szSrt = util.prpsrtcolumn("srt",indexsz);
            String sign = util.nvl(req.getParameter("sign"));
            String lprp = util.nvl(req.getParameter("lprp")).trim();
            String shape = util.nvl(req.getParameter("shape")).trim();
            String grp = util.nvl(req.getParameter("grp")).trim();
            String szgrp = util.nvl(req.getParameter("szgrp")).trim();
            String gtplgtmul = util.nvl(req.getParameter("gtplgtmul")).trim();
            int indexlprp = ANLSVWList.indexOf(lprp)+1;   
            String lprpPrp = util.prpsrtcolumn("prp",indexlprp);
            String lprpSrt = util.prpsrtcolumn("srt",indexlprp);
            String gtprpVal="";
            String gtmultiprpVal="";
            if(!gtplgtmul.equals("")){
                String[] gtplgtmulVal = gtplgtmul.split(":");  
                gtprpVal=gtplgtmulVal[1].trim();
                gtmultiprpVal  =gtplgtmulVal[0].trim();
            }
                
        itemHdr.add("Sr");
        itemHdr.add("VNM");
        String  srchQ =  " select a.stk_idn , a.pkt_ty,  a.vnm, a.pkt_dte, a.stt ,a.dsp_stt, a.qty , a.cts , a.rmk ";
        for (int i = 0; i < vwPrpLst.size(); i++) {
            String fld = "a.prp_";
            int j = i + 1;
            if (j < 10)
                fld += "00" + j;
            else if (j < 100)
                fld += "0" + j;
            else if (j > 100)
                fld += j;

            srchQ += ", " + fld;
            itemHdr.add((String)vwPrpLst.get(i));
        }
        
        int sr=1;
        String rsltQ = srchQ + " from gt_srch_rslt a, gt_srch_multi ir, prp pgrp,prp sz  "+
                       " where a.stk_idn = ir.stk_idn  and ir."+lprpSrt+"=pgrp.srt and pgrp.mprp='"+lprp+"' and sz.grp='"+szgrp+"'\n" + 
                       " and a."+szPrp+"=sz.val and sz.mprp='"+szval+"' ";
        if(shape.equals("ROUND"))
        rsltQ=rsltQ+" and a."+shPrp+" in('RD','ROUND')";
        else
        rsltQ=rsltQ+" and a."+shPrp+" not in('RD','ROUND')";
        if(!grp.equals("TOTAL"))
        rsltQ=rsltQ+" and pgrp.grp='"+grp+"'"; 
        if(sign.equals("P"))
        rsltQ=rsltQ+" and a."+lprpSrt+" < ir."+lprpSrt;
        if(sign.equals("E"))
        rsltQ=rsltQ+"  and nvl(a."+lprpSrt+",ir."+lprpSrt+") = nvl(ir."+lprpSrt+",a."+lprpSrt+") ";
        if(sign.equals("M"))
        rsltQ=rsltQ+" and a."+lprpSrt+" > ir."+lprpSrt;
        if(!gtprpVal.equals(""))
        rsltQ=rsltQ+" and a."+lprpPrp+"='"+gtprpVal+"'";
        if(!gtmultiprpVal.equals(""))
        rsltQ=rsltQ+" and ir."+lprpPrp+"='"+gtmultiprpVal+"'";
            
        rsltQ=rsltQ+" order by a.sk1 , a.cts ";

            ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
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
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("pktList", pktList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Monthly Comparision", "moncmpGrpPKTDTL end");
        return am.findForward("pktDtl");
        }
    }
    
    public ActionForward createMonthCmpXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Monthly Comparision", "createMonthCmpXL start");
        String CONTENT_TYPE = "getServletContext()/vnd-excel";       
        String fileNm = "MonthlyComparision"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        HSSFWorkbook hwb = null;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        hwb = xlUtil.getMonthlyCmpInXl(req);
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Monthly Comparision", "createMonthCmpXL end");
        return null;
        }
    }
    
    public ActionForward PrpWise(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
      String cnt = req.getParameter("cnt");
      String prp = "prp_00"+cnt;
      String srt = "srt_00"+cnt;
      String row = util.nvl((String)req.getParameter("row"));
      String key = util.nvl((String)req.getParameter("key"));
      String conQ=" and b.rln_idn = ? ";
      if(!key.equals("EMP")){
          conQ=" and to_char(b.pkt_dte, 'MONrrrr')=? ";
      }
      String prpWise=" select b.rln_idn,to_number(to_char(b.pkt_dte, 'rrrrmm')) srt_mon, to_char(b.pkt_dte, 'MONrrrr') MON_YR " + 
      ", count(*) cnt , '+' Dsc , b."+prp+"||' : '||a."+prp+" prp , a."+srt+" srt "+
      " from gt_srch_rslt a, gt_srch_multi b\n" + 
      " where a.stk_idn = b.stk_idn and a."+srt+" < b."+srt+ conQ+
      " group by  b.rln_idn,to_number(to_char(b.pkt_dte, 'rrrrmm')), to_char(b.pkt_dte, 'MONrrrr'), '+', a."+prp+", a."+srt+",b."+prp+ 
      " UNION\n" + 
      " select  b.rln_idn,to_number(to_char(b.pkt_dte, 'rrrrmm')) srt_mon, to_char(b.pkt_dte, 'MONrrrr') MON_YR\n" + 
      ", count(*) cnt, '=' Dsc , a."+prp+" prp ,a."+srt+" srt "+
      " from gt_srch_rslt a, gt_srch_multi b\n" + 
      " where a.stk_idn = b.stk_idn and a."+srt+" = b."+srt+ conQ+
      " group by  b.rln_idn,to_number(to_char(b.pkt_dte, 'rrrrmm')), to_char(b.pkt_dte, 'MONrrrr') ,'=',a."+prp+",a."+srt+
      " UNION\n" + 
      " select b.rln_idn,to_number(to_char(b.pkt_dte, 'rrrrmm')) srt_mon, to_char(b.pkt_dte, 'MONrrrr') MON_YR\n" + 
      ", count(*) cnt, '-' Dsc , b."+prp+"||' : '||a."+prp+" prp,a."+srt+" srt"+
      " from gt_srch_rslt a, gt_srch_multi b\n" + 
      " where a.stk_idn = b.stk_idn and a."+srt+" > b."+srt+ conQ+
      " group by  b.rln_idn,to_number(to_char(b.pkt_dte, 'rrrrmm')), to_char(b.pkt_dte, 'MONrrrr'),'-',a."+prp+",a."+srt+",b."+prp+  
        " order by 5,7 \n";
      ArrayList ary = new ArrayList();
      ary.add(row);
      ary.add(row);
      ary.add(row);
     

            ArrayList outLst = db.execSqlLst("prpWise", prpWise, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        StringBuffer sb = new StringBuffer();
      
      while(rs.next()){
          sb.append("<dtl>");
          sb.append("<cnt>"+util.nvl(rs.getString("cnt")) +"</cnt>");
          sb.append("<dsc>"+util.nvl(rs.getString("Dsc")) +"</dsc>");
          sb.append("<prp>"+util.nvl(rs.getString("prp")) +"</prp>");
          sb.append("<srt>"+util.nvl(rs.getString("srt")) +"</srt>");
          sb.append("<mon>"+util.nvl(rs.getString("MON_YR"))+"</mon>");
          sb.append("<nmeIdn>"+util.nvl(rs.getString("rln_idn"))+"</nmeIdn>");
          sb.append("</dtl>");
      }
            rs.close(); pst.close();
        res.getWriter().write("<dtls>" +sb.toString()+ "</dtls>");
      
      return null;
        }
    }
    
    public ActionForward GrpWise(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            GenericInterface genericInt = new GenericImpl();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        HashMap dbinfo = info.getDmbsInfoLst();
        ArrayList ANLSVWList=genericInt.genericPrprVw(req, res, "memocomp_vw","MTH_COMP");
        String sh = (String)dbinfo.get("SHAPE");
        String szval = (String)dbinfo.get("SIZE");
        int indexsh = ANLSVWList.indexOf(sh)+1;   
        String shPrp = util.prpsrtcolumn("prp",indexsh);
        String shSrt = util.prpsrtcolumn("srt",indexsh);
        int indexsz = ANLSVWList.indexOf(szval)+1;   
        String szPrp = util.prpsrtcolumn("prp",indexsz);
        String szSrt = util.prpsrtcolumn("srt",indexsz);
        String lprp = util.nvl(req.getParameter("lprp")).trim();
        String shape = util.nvl(req.getParameter("shape")).trim();
        String grp = util.nvl(req.getParameter("grp")).trim();
        int indexlprp = ANLSVWList.indexOf(lprp)+1;   
        String lprpPrp = util.prpsrtcolumn("prp",indexlprp);
        String lprpSrt = util.prpsrtcolumn("srt",indexlprp);
        
        String conQ="";
        if(shape.equals("ROUND"))
            conQ+=" and a."+shPrp+" in('RD','ROUND')";
        else
            conQ+=" and a."+shPrp+" not in('RD','ROUND')";
        if(!grp.equals("TOTAL"))
        conQ+=" and pgrp.grp='"+grp+"'";  
        String grpWiseQ=" select  sz.grp szgrp,count(*) cnt , '+' Dsc,ir."+lprpPrp+"||' : '||a."+lprpPrp+" prp , a."+lprpSrt+" srt\n" + 
        " from gt_srch_rslt a, gt_srch_multi ir, prp pgrp,prp sz\n" + 
        " where a.stk_idn = ir.stk_idn \n" + 
        " and ir."+lprpSrt+"=pgrp.srt and pgrp.mprp='"+lprp+"' \n" + 
        " and a."+szPrp+"=sz.val and sz.mprp='"+szval+"'\n" + conQ+
        " and a."+lprpSrt+" < ir."+lprpSrt+"\n" + 
        " group by sz.grp,a."+lprpPrp+" ,a."+lprpSrt+",ir."+lprpPrp+"\n" + 
        "  UNION\n" + 
        "  select  sz.grp szgrp,count(*) cnt , '=' Dsc,ir."+lprpPrp+"||' : '||a."+lprpPrp+" prp , a."+lprpSrt+" srt\n" + 
        " from gt_srch_rslt a, gt_srch_multi ir, prp pgrp,prp sz\n" + 
        " where a.stk_idn = ir.stk_idn \n" + 
        " and ir."+lprpSrt+"=pgrp.srt and pgrp.mprp='"+lprp+"' \n" + 
        "  and a."+szPrp+"=sz.val and sz.mprp='"+szval+"'\n" +  conQ+
        " and nvl(a."+lprpSrt+",ir."+lprpSrt+") = nvl(ir."+lprpSrt+",a."+lprpSrt+")\n" + 
        "group by sz.grp,a."+lprpPrp+" ,a."+lprpSrt+",ir."+lprpPrp+"\n" + 
        "  UNION\n" + 
        "   select  sz.grp szgrp,count(*) cnt , '-' Dsc,ir."+lprpPrp+"||' : '||a."+lprpPrp+" prp , a."+lprpSrt+" srt\n" + 
        " from gt_srch_rslt a, gt_srch_multi ir, prp pgrp,prp sz\n" + 
        " where a.stk_idn = ir.stk_idn \n" + 
        " and ir."+lprpSrt+"=pgrp.srt and pgrp.mprp='"+lprp+"' \n" + 
        "  and a."+szPrp+"=sz.val and sz.mprp='"+szval+"'\n" + conQ+
        " and a."+lprpSrt+" > ir."+lprpSrt+"\n" + 
        "group by sz.grp,a."+lprpPrp+" ,a."+lprpSrt+",ir."+lprpPrp+""+
        " order by 3,5";

        StringBuffer sb = new StringBuffer();
            ArrayList outLst = db.execSqlLst("grpWise", grpWiseQ, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
      
      while(rs.next()){
          sb.append("<dtl>");
          sb.append("<cnt>"+util.nvl(rs.getString("cnt")) +"</cnt>");
          sb.append("<dsc>"+util.nvl(rs.getString("Dsc")) +"</dsc>");
          sb.append("<prp>"+util.nvl(rs.getString("prp")) +"</prp>");
          sb.append("<szgrp>"+util.nvl(rs.getString("szgrp")) +"</szgrp>");
          sb.append("</dtl>");
      }
            rs.close(); pst.close();
        res.getWriter().write("<dtls>" +sb.toString()+ "</dtls>");
      
      return null;
        }
    }
//    public ArrayList memocmpGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("MonthCmpGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'MEMOCMP_SRCH' and flg <> 'N' order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("MonthCmpGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
        public ActionForward pktList(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Monthly Comparision", "pktList start");
            GenericInterface genericInt = new GenericImpl();
        ArrayList pktList = new ArrayList();
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "memocomp_vw","MTH_COMP");
        ArrayList itemHdr = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        itemHdr.add("Sr");
        itemHdr.add("VNM");
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt ,dsp_stt, qty , cts , rmk ";
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
                pktPrpMap.put("STATUS", util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.put("VNM",vnm);
                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
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
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("pktList", pktList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Monthly Comparision", "pktList end");
        return am.findForward("pktDtl");
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
                util.updAccessLog(req,res,"Monthly Comparision", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Monthly Comparision", "end");
            }
            }
            return rtnPg;
            }
    
    
    public MonthlyCmpAction() {
        super();
    }
}
