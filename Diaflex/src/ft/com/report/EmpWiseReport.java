package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.PdfforReport;
import ft.com.assort.AssortIssueImpl;
import ft.com.assort.AssortIssueInterface;
import ft.com.dao.ByrDao;
import ft.com.dao.DFMenu;

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

import java.sql.PreparedStatement;

public class EmpWiseReport extends DispatchAction {

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
        util.updAccessLog(req,res,"Rpt Action", "EmpWiseReport start");
        EmpWiseForm udf = (EmpWiseForm)af;
            udf.reset();
            ArrayList empList = new ArrayList();
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
            
        udf.setEmpList(empList);
        AssortIssueInterface assortInt = new AssortIssueImpl();
        ArrayList mprcList = assortInt.getPrc(req,res);
        udf.setMprcList(mprcList); 
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("EMP_WISE_RPT");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("EMP_WISE_RPT");
        allPageDtl.put("EMP_WISE_RPT",pageDtl);
        }
        info.setPageDetails(allPageDtl); 
        util.updAccessLog(req,res,"EmpWiseReport Action", "EmpWiseReport end");
        return am.findForward("empWiseReport");
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
         EmpWiseForm udf = (EmpWiseForm)af;
         util.updAccessLog(req,res,"EmpWiseReport Action", "fetch");
         ArrayList ary=new ArrayList(); 
         ArrayList prcLst=new ArrayList(); 
         ArrayList empdeptLst=new ArrayList();
         ArrayList groupbyLst=new ArrayList();
         HashMap dataDtl=new HashMap();
         String[] empLst=udf.getEmpLst();    
         String prcIdn = util.nvl((String)udf.getValue("prcIdn"));
         String rtnfrmdte = util.nvl((String)udf.getValue("rtnfrmdte"));
         String rtntodte = util.nvl((String)udf.getValue("rtntodte"));
         String issfrmdte = util.nvl((String)udf.getValue("issfrmdte"));
         String isstodte = util.nvl((String)udf.getValue("isstodte"));
         String empidn="";
         HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
         HashMap pageDtl=(HashMap)allPageDtl.get("EMP_WISE_RPT");
         if(pageDtl==null || pageDtl.size()==0){
         pageDtl=new HashMap();
         pageDtl=util.pagedef("EMP_WISE_RPT");
         allPageDtl.put("EMP_WISE_RPT",pageDtl);
         }
         info.setPageDetails(allPageDtl);
         ArrayList pageList=new ArrayList();
         HashMap pageDtlMap=new HashMap();
         String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="",groupby="DEPT";
         pageList= ((ArrayList)pageDtl.get("GROUPBY") == null)?new ArrayList():(ArrayList)pageDtl.get("GROUPBY");             
         if(pageList!=null && pageList.size() >0){
         for(int j=0;j<pageList.size();j++){
         pageDtlMap=(HashMap)pageList.get(j);
         groupby=(String)pageDtlMap.get("dflt_val");
         }
         }

         String conQ=" and d.stt <> 'CL'";   
         if(empLst!=null){
         for(int i=0;i<empLst.length;i++){
         empidn=empidn+","+empLst[i];  
         }   
         empidn=empidn.replaceFirst("\\,", "");
             
         if(!empidn.equals(""))
         conQ=" and i.emp_id in("+empidn+") ";
         }
         if(!prcIdn.equals(""))
         conQ=" and I.Prc_Id in("+prcIdn+") ";
         if(!rtnfrmdte.equals("") && !rtntodte.equals("")){
                 conQ = conQ+" and trunc(d.rtn_dt) between to_date('"+rtnfrmdte+"' , 'dd-mm-yyyy') and to_date('"+rtntodte+"' , 'dd-mm-yyyy') ";
         }
         if(!issfrmdte.equals("") && !isstodte.equals("")){
                 conQ = conQ+" and trunc(d.iss_dt) between to_date('"+issfrmdte+"' , 'dd-mm-yyyy') and to_date('"+isstodte+"' , 'dd-mm-yyyy') ";
         }    
         String groupbyQ=" dept.val";
         
         if(groupby.equals("SH") || groupby.equals("SHAPE")){
             groupbyQ=" decode(dept.val, 'RD', 'RD','ROUND','RD', 'FN')";
         }
         String dtlQ="select i.prc_id, i.prc, i.emp, "+groupbyQ+" dept, count(*) pkts, sum(qty) qty, sum(cts) cts,trunc(sum(nvl(cts,0)*nvl(upr, cmp)),2) vlu\n" + 
         "from mstk m, iss_rtn_v i, iss_rtn_dtl d, stk_dtl dept\n" + 
         "where i.iss_id = d.iss_id and d.iss_stk_idn = m.idn\n" + 
         "and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = '"+groupby+"'\n" + conQ+
         "group by i.prc_id, i.prc, i.emp, \n" + groupbyQ+
         " Order By I.Prc, I.Emp, 4";

             ArrayList outLst = db.execSqlLst("search Result", dtlQ, ary);
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet rs = (ResultSet)outLst.get(1);
         while(rs.next()) {
         String dept=util.nvl(rs.getString("dept"));
         String prc=util.nvl(rs.getString("prc"));
         String emp=util.nvl(rs.getString("emp"));
         dataDtl.put(dept+"_"+prc+"_"+emp+"_PKTS", util.nvl(rs.getString("pkts")));
         dataDtl.put(dept+"_"+prc+"_"+emp+"_QTY", util.nvl(rs.getString("qty")));
         dataDtl.put(dept+"_"+prc+"_"+emp+"_CTS", util.nvl(rs.getString("cts")));
         dataDtl.put(dept+"_"+prc+"_"+emp+"_VLU", util.nvl(rs.getString("vlu")));
         }
         rs.close(); pst.close();
        
         String prvdept="";
         prcLst=new ArrayList();
         String prcdtlQ="select i.prc_id, i.prc,"+groupbyQ+" dept, count(*) pkts, sum(qty) qty, sum(cts) cts,trunc(sum(nvl(cts,0)*nvl(upr, cmp)),2) vlu\n" + 
         "from mstk m, iss_rtn_v i, iss_rtn_dtl d, stk_dtl dept\n" + 
         "where i.iss_id = d.iss_id and d.iss_stk_idn = m.idn\n" + 
         "and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = '"+groupby+"'\n"  + conQ+
         "Group By I.Prc_Id, I.Prc, \n" + groupbyQ+
         " Order By 3,I.Prc";

             outLst = db.execSqlLst("prcdtlQ", prcdtlQ, ary);
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while(rs.next()) {
         String dept=util.nvl(rs.getString("dept"));
         String prc=util.nvl(rs.getString("prc"));
         dataDtl.put(dept+"_"+prc+"_PKTS", util.nvl(rs.getString("pkts")));
         dataDtl.put(dept+"_"+prc+"_QTY", util.nvl(rs.getString("qty")));
         dataDtl.put(dept+"_"+prc+"_CTS", util.nvl(rs.getString("cts")));
         dataDtl.put(dept+"_"+prc+"_VLU", util.nvl(rs.getString("vlu")));
         if(prvdept.equals(""))
             prvdept=dept;
         if(!prvdept.equals(dept)){
         dataDtl.put(prvdept+"_PRC", prcLst);  
         prcLst=new ArrayList();
         prvdept=dept;
         }
         prcLst.add(prc);
         }
         rs.close(); pst.close();
         if(!prvdept.equals(""))
         dataDtl.put(prvdept+"_PRC", prcLst); 
             
         prvdept="";
         String empdtlQ="select i.emp, "+groupbyQ+" dept, count(*) pkts, sum(qty) qty, sum(cts) cts,trunc(sum(nvl(cts,0)*nvl(upr, cmp)),2) vlu\n" + 
         "from mstk m, iss_rtn_v i, iss_rtn_dtl d, stk_dtl dept\n" + 
         "where i.iss_id = d.iss_id and d.iss_stk_idn = m.idn\n" + 
         "and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = '"+groupby+"'\n" + conQ+
         "Group By I.Emp, \n" + groupbyQ+
         " Order By 2,i.emp";

             outLst = db.execSqlLst("empdtlQ", empdtlQ, ary);
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
             String dept=util.nvl(rs.getString("dept"));
             String emp=util.nvl(rs.getString("emp"));
             dataDtl.put(dept+"_"+emp+"_PKTS", util.nvl(rs.getString("pkts")));
             dataDtl.put(dept+"_"+emp+"_QTY", util.nvl(rs.getString("qty")));
             dataDtl.put(dept+"_"+emp+"_CTS", util.nvl(rs.getString("cts")));
             dataDtl.put(dept+"_"+emp+"_VLU", util.nvl(rs.getString("vlu")));
             if(prvdept.equals(""))
                 prvdept=dept;
             if(!prvdept.equals(dept)){
             dataDtl.put(prvdept+"_EMP", empdeptLst);  
             prvdept=dept;
             empdeptLst=new ArrayList();
             }
             empdeptLst.add(emp);
             }
             rs.close(); pst.close();
             if(!prvdept.equals(""))
             dataDtl.put(prvdept+"_EMP", empdeptLst); 
             
             String ttlQ="select dept.val dept,dept.srt, count(*) pkts, sum(qty) qty, sum(cts) cts,trunc(sum(nvl(cts,0)*nvl(upr, cmp)),2) vlu\n" + 
             "from mstk m, iss_rtn_v i, iss_rtn_dtl d, stk_dtl dept\n" + 
             "where i.iss_id = d.iss_id and d.iss_stk_idn = m.idn\n" + 
             "and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = '"+groupby+"'\n" + conQ+
             "Group By Dept.Val,dept.srt\n" + 
             "Order By dept.srt";
             
             if(groupby.equals("SH") || groupby.equals("SHAPE")){
                 ttlQ="select "+groupbyQ+" dept, count(*) pkts, sum(qty) qty, sum(cts) cts,trunc(sum(nvl(cts,0)*nvl(upr, cmp)),2) vlu\n" + 
                              "from mstk m, iss_rtn_v i, iss_rtn_dtl d, stk_dtl dept\n" + 
                              "where i.iss_id = d.iss_id and d.iss_stk_idn = m.idn\n" + 
                              "and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = '"+groupby+"'\n" + conQ+
                              "Group By \n" +groupbyQ+
                              "Order By 1 desc";
             }

             outLst = db.execSqlLst("ttlQ", ttlQ, ary);
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
             String dept=util.nvl(rs.getString("dept"));
             dataDtl.put(dept+"_PKTS", util.nvl(rs.getString("pkts")));
             dataDtl.put(dept+"_QTY", util.nvl(rs.getString("qty")));
             dataDtl.put(dept+"_CTS", util.nvl(rs.getString("cts")));
             dataDtl.put(dept+"_VLU", util.nvl(rs.getString("vlu")));
             groupbyLst.add(dept);
             }
             rs.close(); pst.close();
             udf.reset();
             dataDtl.put(groupby, groupbyLst);
             req.setAttribute("dataDtl", dataDtl);
             req.setAttribute("view", "Y");
             udf.setValue("rtnfrmdte", rtnfrmdte);
             udf.setValue("rtntodte", rtntodte);
             udf.setValue("issfrmdte", issfrmdte);
             udf.setValue("isstodte", isstodte);
             udf.setValue("prcIdn", prcIdn);
         util.updAccessLog(req,res,"EmpWiseReport Action", "End");
         return am.findForward("empWiseReport");
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
                util.updAccessLog(req,res,"Rpt Action", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Rpt Action", "init");
            }
            }
            return rtnPg;
            }


}

   
        