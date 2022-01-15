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

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.InetSocketAddress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.concurrent.Future;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.spy.memcached.MemcachedClient;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class GroupWiseActionGrpWise extends DispatchAction {

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
             util.updAccessLog(req,res,"Group Report", "load start");
         GroupWiseForm udf = (GroupWiseForm)af;
             GenericInterface genericInt = new GenericImpl();
         String mlot=util.nvl(req.getParameter("mlot"));
         String mlotQ="";
         String pkt_typ=util.nvl(req.getParameter("PKT_TYP"));
         String vlu=util.nvl(req.getParameter("VLU"));
         String location = util.nvl(req.getParameter("LOC")); 
         HashMap reportDtl=new HashMap();
         HashMap dbinfo = info.getDmbsInfoLst();
         String sh = (String)dbinfo.get("SHAPE");
         String szval = (String)dbinfo.get("SIZE");
         String colval = (String)dbinfo.get("COL");
         String clrval = (String)dbinfo.get("CLR");
         String locval = (String)dbinfo.get("LOC");
         String faprival = "FA_PRI";
         String mfgprival = "MFG_PRI";
         String ageval = "AGE";
         ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "GROUP_REPORT","GROUP_VIEW");
         int indexLB = vWPrpList.indexOf("LAB")+1;
         int indexSH = vWPrpList.indexOf(sh)+1;
         int indexSZ = vWPrpList.indexOf(szval)+1;
         int indexCol = vWPrpList.indexOf(colval)+1;
         int indexClr = vWPrpList.indexOf(clrval)+1;
         int indexloc = vWPrpList.indexOf(locval)+1;
         int indexFapri = vWPrpList.indexOf(faprival)+1;
         int indexMfgpri = vWPrpList.indexOf(mfgprival)+1;   
         int indexagepri = vWPrpList.indexOf(ageval)+1;   
         String lbPrp = util.prpsrtcolumn("prp",indexLB);
         String shPrp =  util.prpsrtcolumn("prp",indexSH);
         String szPrp =  util.prpsrtcolumn("prp",indexSZ);
         String colPrp =  util.prpsrtcolumn("prp",indexCol);
         String clrPrp = util.prpsrtcolumn("prp",indexClr);
         String locPrp = util.prpsrtcolumn("prp",indexloc);
         String fapriPrp = util.prpsrtcolumn("prp",indexFapri);
         String mfgPrp = util.prpsrtcolumn("prp",indexMfgpri);
         String agePrp = util.prpsrtcolumn("prp",indexagepri);
         String lbSrt = util.prpsrtcolumn("srt",indexLB);
         String shSrt = util.prpsrtcolumn("srt",indexSH);
         String szSrt = util.prpsrtcolumn("srt",indexSZ);
         String colSrt = util.prpsrtcolumn("srt",indexCol);
         String clrSrt = util.prpsrtcolumn("srt",indexClr);
         String locSrt =util.prpsrtcolumn("srt",indexloc);         
         String fpriSrt = util.prpsrtcolumn("srt",indexFapri);
         String faSrt = util.prpsrtcolumn("srt",indexMfgpri);
         String ageSrt = util.prpsrtcolumn("srt",indexagepri);
         ArrayList labLst=new ArrayList();
         ArrayList grpList=new ArrayList();
         ResultSet rs = null;
         String conQ="";
         String locQ="";
                  if(!location.equals("")){
                  locQ=" and "+locPrp+"='"+location+"' ";
         }
         String reportNme="Group Report Single";
         String srch_pkg = "srch_pkg.GRP3_NR()";
         if(pkt_typ.equals("")){
                      srch_pkg = "srch_pkg.GRP3_MIX()"; 
                      reportNme="Group Report Mix";
         }
         if(mlot.equals("")){
         int ct = db.execCall("stk_srch", srch_pkg, new ArrayList()); 
         //ct = db.execUpd("gtUpdate","update gt_srch_rslt a set a.prp_001=(select b.val from stk_dtl b where a.stk_idn=b.mstk_idn and b.grp=1 and b.mprp='LAB') ", new ArrayList());
//         ct = db.execUpd("gtUpdate","update gt_srch_rslt a set a.prp_011=(select b.val from stk_dtl b where a.stk_idn=b.mstk_idn and b.grp=1 and b.mprp='LOC')", new ArrayList());
         }else{
         int ct = db.execUpd("gtUpdate","update gt_srch_rslt set prp_001 = null", new ArrayList());
         String pktPrp = "pkgmkt.pktPrp(0,?)";
         ArrayList ary = new ArrayList();
         ary.add("GROUP_VIEW");
         ct = db.execCall(" Srch Prp ", pktPrp, ary); 
         mlotQ=" and pkt_ty='NR' and nvl(dsp_stt, 'NA') <> 'NA'  ";
         }
         String labPrpQ="select distinct "+lbPrp+" labPrp , "+lbSrt+" labsrt from gt_srch_rslt Where 1=1 "+locQ+mlotQ+" order by "+lbSrt;

             ArrayList outLst = db.execSqlLst("Lab Property", labPrpQ, new ArrayList());
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             labLst.add(util.nvl(rs.getString("labPrp")));
         }
         rs.close(); pst.close();
             if(indexFapri!=0){
                 conQ=" trunc(((sum(trunc(cts,2)* "+fapriPrp+") / sum(trunc(cts,2))))) fapri, ";
             }
             if(indexMfgpri!=0){
                conQ=conQ+" trunc(((sum(trunc(cts,2)* "+mfgPrp+") / sum(trunc(cts,2))))) mfgpri,";
           
             }
             if(indexagepri!=0){
                 conQ=conQ+"round(sum("+agePrp+")/sum(qty)) age,";
             }
         HashMap dataTbl=new HashMap();
         String dataQ="select "+lbPrp+" lab,sum(qty) qty,to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg," + 
         "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis,"+conQ+" dsp_stt grp from gt_srch_rslt  where 1=1 " +locQ+ mlotQ+
         "group by "+lbPrp+", dsp_stt";

             outLst = db.execSqlLst("Data", dataQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             HashMap data=new HashMap();
             String lab=util.nvl(rs.getString("lab"));
             String grp=util.nvl(rs.getString("grp"));
             data.put("QTY",util.nvl(rs.getString("qty")));
             data.put("CTS",util.nvl(rs.getString("cts")));
             data.put("VLU", util.nvl(rs.getString("vlu")));
             data.put("AVG", util.nvl(rs.getString("avg")));
             data.put("RAP", util.nvl(rs.getString("avg_dis")));
             if(indexFapri!=0)
             data.put("FAPRI", util.nvl(rs.getString("fapri")));             
             if(indexMfgpri!=0)
             data.put("MFGPRI", util.nvl(rs.getString("mfgpri")));      
             if(indexagepri!=0)
             data.put("AGE", util.nvl(rs.getString("age")));    
             dataTbl.put(grp+"_"+lab,data);       
         }
         rs.close(); pst.close();
         String grpTtlQ="select sum(qty) qty,to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg, " + 
         "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis ,"+conQ+" dsp_stt grp from gt_srch_rslt,rule_dtl a,mrule b " +
         "where a.rule_idn = b.rule_idn and b.mdl = 'JFLEX' and b.nme_rule = 'GROUP_DTL' and dsp_stt=dsc "+locQ+mlotQ+"  group by dsp_stt,srt_fr " + 
         "order by srt_fr";

             outLst = db.execSqlLst("Group Total", grpTtlQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             HashMap data=new HashMap();
             String grp=util.nvl(rs.getString("grp"));
             if(!grpList.contains(grp) && !grp.equals((""))){
                 grpList.add(grp);
             }
             data.put("QTY",util.nvl(rs.getString("qty")));
             data.put("CTS",util.nvl(rs.getString("cts")));
             data.put("VLU", util.nvl(rs.getString("vlu")));
             data.put("AVG", util.nvl(rs.getString("avg")));
             data.put("RAP", util.nvl(rs.getString("avg_dis")));
             if(indexFapri!=0)
             data.put("FAPRI", util.nvl(rs.getString("fapri")));
             if(indexMfgpri!=0)
             data.put("MFGPRI", util.nvl(rs.getString("mfgpri"))); 
             if(indexagepri!=0)
             data.put("AGE", util.nvl(rs.getString("age")));    
             dataTbl.put(grp+"_TTL",data);       
         }
             rs.close(); pst.close();
         String labTtlQ="select "+lbPrp+" lab,"+conQ+" sum(qty) qty,to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg," + 
         "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis from gt_srch_rslt  where 1=1 " +locQ+ mlotQ+
         "group by "+lbPrp;
  
             outLst = db.execSqlLst("Lab Total", labTtlQ, new ArrayList());    
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             HashMap data=new HashMap();
             String lab=util.nvl(rs.getString("lab"));
             data.put("QTY",util.nvl(rs.getString("qty")));
             data.put("CTS",util.nvl(rs.getString("cts")));
             data.put("VLU", util.nvl(rs.getString("vlu")));
             data.put("AVG", util.nvl(rs.getString("avg")));
             data.put("RAP", util.nvl(rs.getString("avg_dis")));
             if(indexFapri!=0)
             data.put("FAPRI", util.nvl(rs.getString("fapri")));
             if(indexMfgpri!=0)
             data.put("MFGPRI", util.nvl(rs.getString("mfgpri")));
             if(indexagepri!=0)
             data.put("AGE", util.nvl(rs.getString("age")));    
             dataTbl.put(lab+"_TTL",data);       
         }
             rs.close(); pst.close();
         String grandTtlQ="select sum(qty) qty,"+conQ+" to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg, " + 
         "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis from gt_srch_rslt  where 1=1 " +locQ+mlotQ;
 
             outLst = db.execSqlLst("Grand Total", grandTtlQ, new ArrayList());   
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             HashMap data=new HashMap();
             data.put("QTY",util.nvl(rs.getString("qty")));
             data.put("CTS",util.nvl(rs.getString("cts")));
             data.put("VLU", util.nvl(rs.getString("vlu")));
             data.put("AVG", util.nvl(rs.getString("avg")));
             data.put("RAP", util.nvl(rs.getString("avg_dis")));
             if(indexFapri!=0){
             data.put("FAPRI", util.nvl(rs.getString("fapri")));
             }
             if(indexMfgpri!=0){
             data.put("MFGPRI", util.nvl(rs.getString("mfgpri")));
             }
             if(indexagepri!=0)
             data.put("AGE", util.nvl(rs.getString("age")));    
             dataTbl.put("TTL",data);       
         }
         rs.close(); pst.close();
         dataTbl.put("TITLE","Group/Lab");
         HashMap groupDsc =((HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_groupDsc") == null)?new HashMap():(HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_groupDsc"); 
         dataTbl.putAll(groupDsc);
         reportDtl.put("GRP_HDR", labLst);
         reportDtl.put("GRP_CONTENT", grpList);
         reportDtl.put("GRP_DATA", dataTbl);
         reportDtl.put("STT_HDR", labLst);
         session.setAttribute("reportDtl", reportDtl);
         udf.setReportNme(reportNme);
         
         HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
         HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_SUMMARY");
         if(pageDtl==null || pageDtl.size()==0){
         pageDtl=new HashMap();
         pageDtl=util.pagedef("STOCK_SUMMARY");
         allPageDtl.put("STOCK_SUMMARY",pageDtl);
         }
         info.setPageDetails(allPageDtl); 
         req.setAttribute("loc", location);
         udf.setValue("LOC",location);
         udf.setValue("valueDisplay",vlu);
         req.setAttribute("valueDisplay", vlu);
         req.setAttribute("mlot", mlot);
             util.updAccessLog(req,res,"Group Report", "load end");
        return am.findForward("load");
         }
     }
    
    public ActionForward loadStatus(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             util.updAccessLog(req,res,"Group Report", "loadStatus start");
        GroupWiseForm udf = (GroupWiseForm)af;
             GenericInterface genericInt = new GenericImpl();
         String groupS = util.nvl(req.getParameter("group")).trim(); 
         String labS = util.nvl(req.getParameter("lab")).trim();
         String location = util.nvl(req.getParameter("loc")).trim();
         String valueDisplay = util.nvl(req.getParameter("valueDisplay")).trim();
         String mlot = util.nvl(req.getParameter("mlot")).trim();   
          HashMap dbinfo = info.getDmbsInfoLst();
          String sh = (String)dbinfo.get("SHAPE");
          String szval = (String)dbinfo.get("SIZE");
          String colval = (String)dbinfo.get("COL");
          String clrval = (String)dbinfo.get("CLR");
          String locval = (String)dbinfo.get("LOC");
          String faprival = "FA_PRI";
          String mfgprival = "MFG_PRI";
          ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "GROUP_REPORT","GROUP_VIEW");
          int indexLB = vWPrpList.indexOf("LAB")+1;
          int indexSH = vWPrpList.indexOf(sh)+1;
          int indexSZ = vWPrpList.indexOf(szval)+1;
          int indexCol = vWPrpList.indexOf(colval)+1;
          int indexClr = vWPrpList.indexOf(clrval)+1;
          int indexloc = vWPrpList.indexOf(locval)+1;
          int indexFapri = vWPrpList.indexOf(faprival)+1;
          int indexMfgpri = vWPrpList.indexOf(mfgprival)+1;  
         String lbPrp = util.prpsrtcolumn("prp",indexLB);
         String shPrp =  util.prpsrtcolumn("prp",indexSH);
         String szPrp =  util.prpsrtcolumn("prp",indexSZ);
         String colPrp =  util.prpsrtcolumn("prp",indexCol);
         String clrPrp = util.prpsrtcolumn("prp",indexClr);
         String locPrp = util.prpsrtcolumn("prp",indexloc);
         String fapriPrp = util.prpsrtcolumn("prp",indexFapri);
         String mfgPrp = util.prpsrtcolumn("prp",indexMfgpri);
         String lbSrt = util.prpsrtcolumn("srt",indexLB);
         String shSrt = util.prpsrtcolumn("srt",indexSH);
         String szSrt = util.prpsrtcolumn("srt",indexSZ);
         String colSrt = util.prpsrtcolumn("srt",indexCol);
         String clrSrt = util.prpsrtcolumn("srt",indexClr);
         String locSrt =util.prpsrtcolumn("srt",indexloc); 
         String fpriSrt = util.prpsrtcolumn("srt",indexFapri);
         String faSrt = util.prpsrtcolumn("srt",indexMfgpri);
         String ageval = "AGE";
         int indexagepri = vWPrpList.indexOf(ageval)+1;   
         String agePrp = util.prpsrtcolumn("prp",indexagepri);
         String ageSrt = util.prpsrtcolumn("srt",indexagepri);
          ArrayList labLst=new ArrayList();
          ArrayList sttList=new ArrayList();
          ResultSet rs = null;
          String reportNme="Status Wise Lab Report";
          String criteria="Group-"+groupS+" , Lab-"+labS;
          String conQ="";
          String conQprp="";   
          String mlotQ="";
          HashMap reportDtl= (HashMap)session.getAttribute("reportDtl");
          HashMap dataTbl=new HashMap();
          String locQ="";
          if(!location.equals("")){
          locQ=" and "+locPrp+"='"+location+"' ";
          }
             if(indexFapri!=0){
                 conQprp=" trunc(((sum(trunc(cts,2)* "+fapriPrp+") / sum(trunc(cts,2))))) fapri, ";
             }
             if(indexMfgpri!=0){
                conQprp=conQprp+" trunc(((sum(trunc(cts,2)* "+mfgPrp+") / sum(trunc(cts,2))))) mfgpri,";
             
             }
             if(indexagepri!=0){
                 conQprp=conQprp+"round(sum("+agePrp+")/sum(qty)) age,";
             }
         if(!mlot.equals(""))
         mlotQ="  and pkt_ty='NR'  and nvl(dsp_stt, 'NA') <> 'NA'  ";
         if(!groupS.equals("ALL") && !labS.equals("ALL"))
             conQ=" where dsp_stt='"+groupS+"' and "+lbPrp+"='"+labS+"' "+locQ+mlotQ;
         if(!groupS.equals("ALL") && labS.equals("ALL"))
             conQ=" where dsp_stt='"+groupS+"' "+locQ+mlotQ;
         if(groupS.equals("ALL") && !labS.equals("ALL"))
             conQ=" where "+lbPrp+"='"+labS+"' "+locQ+mlotQ;
         if(groupS.equals("ALL") && labS.equals("ALL") && !location.equals(""))
             conQ=" where 1=1 "+locQ+mlotQ;
         if(conQ.equals("") && !mlotQ.equals("")){
             conQ=" where 1=1 "+locQ+mlotQ; 
         }
         String labPrpQ="select distinct "+lbPrp+" labPrp , "+lbSrt+" labsrt from gt_srch_rslt "+conQ+" order by "+lbSrt;

             ArrayList outLst = db.execSqlLst("Lab Property", labPrpQ, new ArrayList());
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             labLst.add(util.nvl(rs.getString("labPrp")));
         }
         rs.close(); pst.close();
          String dataQ="select "+lbPrp+" lab,sum(qty) qty,"+conQprp+" to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg," +
              "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis,stt  from gt_srch_rslt  ";
          dataQ=dataQ+conQ+" group by "+lbPrp+", stt";
             outLst = db.execSqlLst("Data", dataQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
          while (rs.next()) { 
              HashMap data=new HashMap();
              String lab=util.nvl(rs.getString("lab"));
              String stt=util.nvl(rs.getString("stt"));
              data.put("QTY",util.nvl(rs.getString("qty")));
              data.put("CTS",util.nvl(rs.getString("cts")));
              data.put("VLU", util.nvl(rs.getString("vlu")));
              data.put("AVG", util.nvl(rs.getString("avg")));
              data.put("RAP", util.nvl(rs.getString("avg_dis")));
              if(indexFapri!=0)
              data.put("FAPRI", util.nvl(rs.getString("fapri")));             
              if(indexMfgpri!=0)
              data.put("MFGPRI", util.nvl(rs.getString("mfgpri")));  
              if(indexagepri!=0)
              data.put("AGE", util.nvl(rs.getString("age")));    
              dataTbl.put(stt+"_"+lab,data);      
          }
         rs.close(); pst.close();
          String grpTtlQ="select sum(a.qty) qty,"+conQprp+" to_char(trunc(sum(trunc(a.cts, 2)),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg, " + 
          "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis,a.stt stt,b.dsc dsc from gt_srch_rslt a,df_stk_stt b " ;
          if(groupS.equals("ALL") && labS.equals("ALL"))
              grpTtlQ=grpTtlQ+" where a.stt=b.stt "+locQ+mlotQ+" group by a.stt,b.srt,b.dsc order by b.srt";  
          else
              grpTtlQ=grpTtlQ+conQ+" and a.stt=b.stt group by a.stt,b.srt,b.dsc order by b.srt";

             outLst = db.execSqlLst("Status Total", grpTtlQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
          while (rs.next()) { 
              HashMap data=new HashMap();
              String stt=util.nvl(rs.getString("stt"));
              if(!sttList.contains(stt) && !stt.equals((""))){
                  sttList.add(stt);
              }
              data.put("QTY",util.nvl(rs.getString("qty")));
              data.put("CTS",util.nvl(rs.getString("cts")));
              data.put("VLU", util.nvl(rs.getString("vlu")));
              data.put("AVG", util.nvl(rs.getString("avg")));
              data.put("RAP", util.nvl(rs.getString("avg_dis")));
              if(indexFapri!=0)
              data.put("FAPRI", util.nvl(rs.getString("fapri")));             
              if(indexMfgpri!=0)
              data.put("MFGPRI", util.nvl(rs.getString("mfgpri")));  
              dataTbl.put(stt+"_TTL",data);
              if(indexagepri!=0)
              data.put("AGE", util.nvl(rs.getString("age")));   
              dataTbl.put(stt,util.nvl(rs.getString("dsc")));  
          }
         rs.close(); pst.close();
          String labTtlQ="select "+lbPrp+" lab,sum(qty) qty,"+conQprp+" to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg, " + 
          "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis from gt_srch_rslt  " ; 
          labTtlQ=labTtlQ+conQ+" group by "+lbPrp; 
             outLst = db.execSqlLst("Lab Total", labTtlQ, new ArrayList());    
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
          while (rs.next()) { 
              HashMap data=new HashMap();
              String lab=util.nvl(rs.getString("lab"));
              data.put("QTY",util.nvl(rs.getString("qty")));
              data.put("CTS",util.nvl(rs.getString("cts")));
              data.put("VLU", util.nvl(rs.getString("vlu")));
              data.put("AVG", util.nvl(rs.getString("avg")));
              data.put("RAP", util.nvl(rs.getString("avg_dis")));
              if(indexFapri!=0)
              data.put("FAPRI", util.nvl(rs.getString("fapri")));             
              if(indexMfgpri!=0)
              data.put("MFGPRI", util.nvl(rs.getString("mfgpri")));  
              if(indexagepri!=0)
              data.put("AGE", util.nvl(rs.getString("age")));   
              dataTbl.put(lab+"_TTL",data);       
          }
         rs.close(); pst.close();
          String grandTtlQ="select sum(qty) qty,"+conQprp+" to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg, " + 
          "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis from gt_srch_rslt ";
          grandTtlQ=grandTtlQ+conQ;

             outLst = db.execSqlLst("Grand Total", grandTtlQ, new ArrayList()); 
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
          while (rs.next()) { 
              HashMap data=new HashMap();
              data.put("QTY",util.nvl(rs.getString("qty")));
              data.put("CTS",util.nvl(rs.getString("cts")));
              data.put("VLU", util.nvl(rs.getString("vlu")));
              data.put("AVG", util.nvl(rs.getString("avg")));
              data.put("RAP", util.nvl(rs.getString("avg_dis")));
              if(indexFapri!=0)
              data.put("FAPRI", util.nvl(rs.getString("fapri")));             
              if(indexMfgpri!=0)
              data.put("MFGPRI", util.nvl(rs.getString("mfgpri")));  
              if(indexagepri!=0)
              data.put("AGE", util.nvl(rs.getString("age")));   
              dataTbl.put("TTL",data);       
          }
         rs.close(); pst.close();
         dataTbl.put("TITLE","Status/Lab"); 
         reportDtl.put("STT_CONTENT", sttList);
         reportDtl.put("STT_DATA", dataTbl);
         reportDtl.put("STT_HDR", labLst);
         session.setAttribute("reportDtl", reportDtl);
         req.setAttribute("group", groupS);
         req.setAttribute("lab", labS);
         req.setAttribute("loc", location);
         req.setAttribute("mlot", mlot);
         req.setAttribute("valueDisplay", valueDisplay);
         udf.setReportNme(reportNme);
         udf.setCriteria(criteria);
         util.updAccessLog(req,res,"Group Report", "loadStatus end");
        return am.findForward("loadstt");
         }
     }
    
    public ActionForward loadSHSZ(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             util.updAccessLog(req,res,"Group Report", "loadSHSZ start");
        GroupWiseForm udf = (GroupWiseForm)af;
         GenericInterface genericInt = new GenericImpl();
         String sttS = util.nvl(req.getParameter("stt")).trim(); 
         String labS = util.nvl(req.getParameter("lab")).trim(); 
         String groupS = util.nvl(req.getParameter("group")).trim(); 
         String location = util.nvl(req.getParameter("loc")).trim();
         String valueDisplay = util.nvl(req.getParameter("valueDisplay")).trim();
         String mlot = util.nvl(req.getParameter("mlot")).trim();   
          HashMap dbinfo = info.getDmbsInfoLst();
          String sh = (String)dbinfo.get("SHAPE");
          String szval = (String)dbinfo.get("SIZE");
          String colval = (String)dbinfo.get("COL");
          String clrval = (String)dbinfo.get("CLR");
          String labval = (String)dbinfo.get("LAB");
          String locval = (String)dbinfo.get("LOC");
          String faprival = "FA_PRI";
          String mfgprival = "MFG_PRI";
          ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "GROUP_REPORT","GROUP_VIEW");
          int indexLB = vWPrpList.indexOf(labval)+1;
          int indexSH = vWPrpList.indexOf(sh)+1;
          int indexSZ = vWPrpList.indexOf(szval)+1;
          int indexCol = vWPrpList.indexOf(colval)+1;
          int indexClr = vWPrpList.indexOf(clrval)+1;
          int indexloc = vWPrpList.indexOf(locval)+1;
          int indexFapri = vWPrpList.indexOf(faprival)+1;
          int indexMfgpri = vWPrpList.indexOf(mfgprival)+1;
         String lbPrp = util.prpsrtcolumn("prp",indexLB);
         String shPrp =  util.prpsrtcolumn("prp",indexSH);
         String szPrp =  util.prpsrtcolumn("prp",indexSZ);
         String colPrp =  util.prpsrtcolumn("prp",indexCol);
         String clrPrp = util.prpsrtcolumn("prp",indexClr);
         String locPrp = util.prpsrtcolumn("prp",indexloc);
         String fapriPrp = util.prpsrtcolumn("prp",indexFapri);
         String mfgPrp = util.prpsrtcolumn("prp",indexMfgpri);
         String lbSrt = util.prpsrtcolumn("srt",indexLB);
         String shSrt = util.prpsrtcolumn("srt",indexSH);
         String szSrt = util.prpsrtcolumn("srt",indexSZ);
         String colSrt = util.prpsrtcolumn("srt",indexCol);
         String clrSrt = util.prpsrtcolumn("srt",indexClr);
         String locSrt =util.prpsrtcolumn("srt",indexloc); 
         String fpriSrt = util.prpsrtcolumn("srt",indexFapri);
         String faSrt = util.prpsrtcolumn("srt",indexMfgpri);
             String ageval = "AGE";
             int indexagepri = vWPrpList.indexOf(ageval)+1;   
             String agePrp = util.prpsrtcolumn("prp",indexagepri);
             String ageSrt = util.prpsrtcolumn("srt",indexagepri);
          ArrayList szLst=new ArrayList();
          ArrayList shList=new ArrayList();
         HashMap reportDtl= (HashMap)session.getAttribute("reportDtl");
          String reportNme="Shape Size Report ";
          String criteria="Group-"+groupS+" , Status-"+sttS+" , Lab-"+labS;
          String conQ="";
          String conQprp="";
          HashMap dataTbl=new HashMap();
          String locQ="";
          String mlotQ="";
          if(!location.equals("")){
          locQ=" and "+locPrp+"='"+location+"' ";
          }
             if(indexFapri!=0){
                 conQprp=" trunc(((sum(trunc(cts,2)* "+fapriPrp+") / sum(trunc(cts,2))))) fapri, ";
             }
             if(indexMfgpri!=0){
                conQprp=conQprp+" trunc(((sum(trunc(cts,2)* "+mfgPrp+") / sum(trunc(cts,2))))) mfgpri,";
             
             }
             if(indexagepri!=0){
                 conQprp=conQprp +"round(sum("+agePrp+")/sum(qty)) age,";
             }
          if(!mlot.equals(""))
          mlotQ="  and pkt_ty='NR'  and nvl(dsp_stt, 'NA') <> 'NA' ";
          String groupQ=" dsp_stt='"+groupS+"' "+locQ+mlotQ+"  and";
         if(groupS.equals("ALL"))
             groupQ="";
          
         if(!sttS.equals("ALL") && !labS.equals("ALL"))
             conQ=" where "+groupQ+" stt='"+sttS+"' and "+lbPrp+"='"+labS+"' "+locQ+mlotQ;
         if(!sttS.equals("ALL") && labS.equals("ALL"))
             conQ=" where "+groupQ+" stt='"+sttS+"' "+locQ+mlotQ;
         if(sttS.equals("ALL") && !labS.equals("ALL"))
             conQ=" where "+groupQ+" "+lbPrp+"='"+labS+"' "+locQ+mlotQ;
         if(sttS.equals("ALL") && labS.equals("ALL") && !groupS.equals("ALL"))
             conQ=" where dsp_stt='"+groupS+"'  "+locQ+mlotQ;
         if(sttS.equals("ALL") && labS.equals("ALL") && groupS.equals("ALL") && (!location.equals("") || !mlotQ.equals("")))
             conQ=" where 1=1 "+locQ+mlotQ;
         
         String szPrpQ="select distinct "+szPrp+" szPrp , "+szSrt+" szSrt from gt_srch_rslt "+conQ+"order by "+szSrt;

             ArrayList outLst = db.execSqlLst("Size Property", szPrpQ, new ArrayList());
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             String gtsz=util.nvl(rs.getString("szPrp"));
             if(!szLst.contains(gtsz))
             szLst.add(gtsz);
         }
         rs.close(); pst.close();
         String dataQ="select "+szPrp+" sz,"+shPrp+" sh,sum(qty) qty,"+conQprp+" to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg,\n" + 
         "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis from gt_srch_rslt ";
         dataQ=dataQ+conQ+" group by "+szPrp+", "+shPrp;

             outLst = db.execSqlLst("Data", dataQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
          while (rs.next()) { 
              HashMap data=new HashMap();
              String sz=util.nvl(rs.getString("sz"));
              String shape=util.nvl(rs.getString("sh"));
              data.put("QTY",util.nvl(rs.getString("qty")));
              data.put("CTS",util.nvl(rs.getString("cts")));
              data.put("VLU", util.nvl(rs.getString("vlu")));
              data.put("AVG", util.nvl(rs.getString("avg")));
              data.put("RAP", util.nvl(rs.getString("avg_dis")));
              if(indexFapri!=0)
              data.put("FAPRI", util.nvl(rs.getString("fapri")));             
              if(indexMfgpri!=0)
              data.put("MFGPRI", util.nvl(rs.getString("mfgpri")));  
              if(indexagepri!=0)
              data.put("AGE", util.nvl(rs.getString("age")));   
              dataTbl.put(shape+"_"+sz,data);       
          }
         rs.close(); pst.close();
         String grpTtlQ="select "+shPrp+" sh,sum(a.qty) qty,"+conQprp+" to_char(trunc(sum(trunc(a.cts, 2)),2),'99999990.00') cts ,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg," + 
         "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis from gt_srch_rslt a";
         if(sttS.equals("ALL") && labS.equals("ALL")){
              if(groupS.equals("ALL"))   
              grpTtlQ=grpTtlQ+" where  1=1 "+locQ+mlotQ+" group by "+shPrp; 
              else
              grpTtlQ=grpTtlQ+" where  dsp_stt='"+groupS+"' "+locQ+mlotQ+" group by "+shPrp;     
         }else
         grpTtlQ=grpTtlQ+conQ+" group by "+shPrp; 
          

             outLst = db.execSqlLst("Shape Total", grpTtlQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
          while (rs.next()) { 
              HashMap data=new HashMap();
              String shape=util.nvl(rs.getString("sh"));
              if(!shList.contains(shape) && !shape.equals((""))){
                  shList.add(shape);
              }
              data.put("QTY",util.nvl(rs.getString("qty")));
              data.put("CTS",util.nvl(rs.getString("cts")));
              data.put("VLU", util.nvl(rs.getString("vlu")));
              data.put("AVG", util.nvl(rs.getString("avg")));
              data.put("RAP", util.nvl(rs.getString("avg_dis")));
              if(indexFapri!=0)
              data.put("FAPRI", util.nvl(rs.getString("fapri")));             
              if(indexMfgpri!=0)
              data.put("MFGPRI", util.nvl(rs.getString("mfgpri")));  
              if(indexagepri!=0)
              data.put("AGE", util.nvl(rs.getString("age")));   
              dataTbl.put(shape+"_TTL",data); 
          }
         rs.close(); pst.close();
         String labTtlQ="select "+szPrp+" sz,sum(qty) qty,"+conQprp+" to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg," + 
         "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis from gt_srch_rslt ";
         labTtlQ=labTtlQ+conQ+" group by "+szPrp;

             outLst = db.execSqlLst("Size Total", labTtlQ, new ArrayList());    
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
          while (rs.next()) { 
              HashMap data=new HashMap();
              String sz=util.nvl(rs.getString("sz"));
              data.put("QTY",util.nvl(rs.getString("qty")));
              data.put("CTS",util.nvl(rs.getString("cts")));
              data.put("VLU", util.nvl(rs.getString("vlu")));
              data.put("AVG", util.nvl(rs.getString("avg")));
              data.put("RAP", util.nvl(rs.getString("avg_dis")));
              if(indexFapri!=0)
              data.put("FAPRI", util.nvl(rs.getString("fapri")));             
              if(indexMfgpri!=0)
              data.put("MFGPRI", util.nvl(rs.getString("mfgpri")));  
              if(indexagepri!=0)
              data.put("AGE", util.nvl(rs.getString("age")));   
              dataTbl.put(sz+"_TTL",data);       
          }
         rs.close(); pst.close();
          String grandTtlQ="select sum(qty) qty,"+conQprp+" to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg," + 
          "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis from gt_srch_rslt ";
          grandTtlQ=grandTtlQ+conQ;

             outLst = db.execSqlLst("Grand Total", grandTtlQ, new ArrayList());  
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
          while (rs.next()) { 
              HashMap data=new HashMap();
              data.put("QTY",util.nvl(rs.getString("qty")));
              data.put("CTS",util.nvl(rs.getString("cts")));
              data.put("VLU", util.nvl(rs.getString("vlu")));
              data.put("AVG", util.nvl(rs.getString("avg")));
              data.put("RAP", util.nvl(rs.getString("avg_dis")));
              if(indexFapri!=0)
              data.put("FAPRI", util.nvl(rs.getString("fapri")));             
              if(indexMfgpri!=0)
              data.put("MFGPRI", util.nvl(rs.getString("mfgpri")));  
              if(indexagepri!=0)
              data.put("AGE", util.nvl(rs.getString("age")));   
              dataTbl.put("TTL",data);       
          }
         rs.close(); pst.close();
         dataTbl.put("TITLE","Shape/Size");  
         reportDtl.put("SH_HDR", szLst);
         reportDtl.put("SH_CONTENT", shList);
         reportDtl.put("SH_DATA", dataTbl);
         session.setAttribute("reportDtl", reportDtl);
         req.setAttribute("group", groupS);
         req.setAttribute("lab", labS);
         req.setAttribute("stt", sttS);
         req.setAttribute("loc", location);
         req.setAttribute("mlot", mlot);
         req.setAttribute("valueDisplay", valueDisplay);
//         groupDtls(req);
         udf.setReportNme(reportNme);
         udf.setCriteria(criteria);
             util.updAccessLog(req,res,"Group Report", "loadSHSZ end");
        return am.findForward("loadShSz");
         }
     }
    public ActionForward loadCOPU(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             util.updAccessLog(req,res,"Group Report", "loadCOPU start");
        GroupWiseForm udf = (GroupWiseForm)af;
             GenericInterface genericInt = new GenericImpl();
         String sttS = util.nvl(req.getParameter("stt")).trim(); 
         String labS = util.nvl(req.getParameter("lab")).trim(); 
         String groupS = util.nvl(req.getParameter("group")).trim(); 
         String shapeS= util.nvl(req.getParameter("sh")).trim(); 
         String sizeS = util.nvl(req.getParameter("sz")).trim(); 
         String location = util.nvl(req.getParameter("loc")).trim();
         String valueDisplay = util.nvl(req.getParameter("valueDisplay")).trim();
         String mlot = util.nvl(req.getParameter("mlot")).trim();
          HashMap dbinfo = info.getDmbsInfoLst();
          String sh = (String)dbinfo.get("SHAPE");
          String szval = (String)dbinfo.get("SIZE");
          String colval = (String)dbinfo.get("COL");
          String clrval = (String)dbinfo.get("CLR");
          String labval = (String)dbinfo.get("LAB");
          String locval = (String)dbinfo.get("LOC");
             String faprival = "FA_PRI";
             String mfgprival = "MFG_PRI";
          ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "GROUP_REPORT","GROUP_VIEW");
          int indexLB = vWPrpList.indexOf(labval)+1;
          int indexSH = vWPrpList.indexOf(sh)+1;
          int indexSZ = vWPrpList.indexOf(szval)+1;
          int indexCol = vWPrpList.indexOf(colval)+1;
          int indexClr = vWPrpList.indexOf(clrval)+1;
         int indexloc = vWPrpList.indexOf(locval)+1;
         int indexFapri = vWPrpList.indexOf(faprival)+1;
         int indexMfgpri = vWPrpList.indexOf(mfgprival)+1;
         String lbPrp = util.prpsrtcolumn("prp",indexLB);
         String shPrp =  util.prpsrtcolumn("prp",indexSH);
         String szPrp =  util.prpsrtcolumn("prp",indexSZ);
         String colPrp =  util.prpsrtcolumn("prp",indexCol);
         String clrPrp = util.prpsrtcolumn("prp",indexClr);
         String locPrp = util.prpsrtcolumn("prp",indexloc);
         String fapriPrp = util.prpsrtcolumn("prp",indexFapri);
         String mfgPrp = util.prpsrtcolumn("prp",indexMfgpri);
         String lbSrt = util.prpsrtcolumn("srt",indexLB);
         String shSrt = util.prpsrtcolumn("srt",indexSH);
         String szSrt = util.prpsrtcolumn("srt",indexSZ);
         String colSrt = util.prpsrtcolumn("srt",indexCol);
         String clrSrt = util.prpsrtcolumn("srt",indexClr);
         String locSrt =util.prpsrtcolumn("srt",indexloc); 
         String fpriSrt = util.prpsrtcolumn("srt",indexFapri);
         String faSrt = util.prpsrtcolumn("srt",indexMfgpri);
             String ageval = "AGE";
             int indexagepri = vWPrpList.indexOf(ageval)+1;   
             String agePrp = util.prpsrtcolumn("prp",indexagepri);
             String ageSrt = util.prpsrtcolumn("srt",indexagepri);
          ArrayList clrLst=new ArrayList();
          ArrayList coList=new ArrayList();
          ResultSet rs = null;
          HashMap reportDtl= (HashMap)session.getAttribute("reportDtl");
          String reportNme="Color Purity Report";
          String criteria=":Group-"+groupS+" , Status-"+sttS+" , Lab-"+labS+" , Shape-"+shapeS+" , Size-"+sizeS;
          String conQ="";
          HashMap dataTbl=new HashMap();
          String locQ="";
          String mlotQ="";
          String conQprp="";
          if(!location.equals("")){
          locQ=" and "+locPrp+"='"+location+"' ";
          }
             if(indexFapri!=0){
                 conQprp=" trunc(((sum(trunc(cts,2)* "+fapriPrp+") / sum(trunc(cts,2))))) fapri, ";
             }
             if(indexMfgpri!=0){
                conQprp=conQprp+" trunc(((sum(trunc(cts,2)* "+mfgPrp+") / sum(trunc(cts,2))))) mfgpri,";
             
             }
             if(indexagepri!=0){
                 conQprp=conQprp+"round(sum("+agePrp+")/sum(qty)) age,";
             }
         if(!mlot.equals(""))
         mlotQ="  and pkt_ty='NR' and nvl(dsp_stt, 'NA') <> 'NA' ";
          String groupQ="dsp_stt='"+groupS+"' "+locQ+mlotQ+"   and";
         if(groupS.equals("ALL"))
             groupQ="";
          
         if(!sttS.equals("ALL") && !labS.equals("ALL"))
             conQ=" where "+groupQ+" stt='"+sttS+"' and "+lbPrp+"='"+labS+"' "+mlotQ;
         if(!sttS.equals("ALL") && labS.equals("ALL"))
             conQ=" where "+groupQ+" stt='"+sttS+"' "+mlotQ;
         if(sttS.equals("ALL") && !labS.equals("ALL"))
             conQ=" where "+groupQ+" "+lbPrp+"='"+labS+"' "+mlotQ;
         if(sttS.equals("ALL") && labS.equals("ALL") && !groupS.equals("ALL"))
             conQ=" where dsp_stt='"+groupS+"' "+locQ+mlotQ;
         String conQ2=" and ";
         if(groupS.equals("ALL") && sttS.equals("ALL") && labS.equals("ALL"))//Mayur Lab
             conQ2=" where ";
         
         if(!shapeS.equals("ALL") && !sizeS.equals("ALL"))  {
             conQ2=conQ2+shPrp+"='"+shapeS+"' and ("+szPrp+"='"+sizeS+"' or "+szPrp+"='+"+sizeS+"') "+mlotQ;
         }
         if(shapeS.equals("ALL") && !sizeS.equals("ALL"))  {
             conQ2=conQ2+" ("+szPrp+"='"+sizeS+"' or "+szPrp+"='+"+sizeS+"') "+mlotQ;
         }
         if(!shapeS.equals("ALL") && sizeS.equals("ALL"))  {
             conQ2=conQ2+shPrp+"='"+shapeS+"' "+mlotQ;
         }
         if(shapeS.equals("ALL") && sizeS.equals("ALL"))  {
             conQ2=""; 
         }
         
         if(groupS.equals("ALL") && sttS.equals("ALL") && labS.equals("ALL") && !location.equals("") && shapeS.equals("ALL") && sizeS.equals("ALL"))//Mayur Lab
             conQ2=" where 1=1 "+locQ+mlotQ;
         if(groupS.equals("ALL") && sttS.equals("ALL") && labS.equals("ALL") && locQ.equals("") && shapeS.equals("ALL") && sizeS.equals("ALL"))//Mayur Lab
             conQ2=" where 1=1 "+mlotQ;
              
         String clrPrpQ="select distinct a.grp clrPrp, a.srt from prp a, gt_srch_rslt b "+conQ+conQ2+" and a.mprp ='"+clrval+"' and a.grp = b."+clrPrp+"  order by a.srt ";

             ArrayList outLst = db.execSqlLst("Purity Property", clrPrpQ, new ArrayList());
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             String gtclr=util.nvl(rs.getString("clrPrp"));
             if(!clrLst.contains(gtclr))
             clrLst.add(gtclr);
         }
         rs.close(); pst.close();
         String dataQ="select "+clrPrp+" clr,"+colPrp+" col,sum(qty) qty,"+conQprp+" to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg," + 
         "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis from gt_srch_rslt ";
         dataQ=dataQ+conQ+conQ2+" group by "+clrPrp+", "+colPrp;

             outLst = db.execSqlLst("Data", dataQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
          while (rs.next()) { 
              HashMap data=new HashMap();
              String col=util.nvl(rs.getString("col"));
              String clr=util.nvl(rs.getString("clr"));
              data.put("QTY",util.nvl(rs.getString("qty")));
              data.put("CTS",util.nvl(rs.getString("cts")));
              data.put("VLU", util.nvl(rs.getString("vlu")));
              data.put("AVG", util.nvl(rs.getString("avg")));
              data.put("RAP", util.nvl(rs.getString("avg_dis")));
              if(indexFapri!=0)
              data.put("FAPRI", util.nvl(rs.getString("fapri")));             
              if(indexMfgpri!=0)
              data.put("MFGPRI", util.nvl(rs.getString("mfgpri")));  
              if(indexagepri!=0)
              data.put("AGE", util.nvl(rs.getString("age")));   
              dataTbl.put(col+"_"+clr,data);       
          }
         rs.close(); pst.close();
         String grpTtlQ="select "+colPrp+" col,sum(a.qty) qty,"+conQprp+" to_char(trunc(sum(trunc(a.cts, 2)),2),'99999990.00') cts ,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg," + 
         "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis from gt_srch_rslt a";
         if(sttS.equals("ALL") && labS.equals("ALL")){
             if(groupS.equals("ALL")){   
             String conQ3=conQ2.replaceFirst(" where ", " and ");    
             grpTtlQ=grpTtlQ+" where  1=1 "+conQ3+" group by "+colPrp; 
             }else
             grpTtlQ=grpTtlQ+" where  dsp_stt='"+groupS+"' "+conQ2+" group by "+colPrp+""; 
         }else
         grpTtlQ=grpTtlQ+conQ+conQ2+" group by "+colPrp; 
          

             outLst = db.execSqlLst("Color Total", grpTtlQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
          while (rs.next()) { 
              HashMap data=new HashMap();
              String col=util.nvl(rs.getString("col"));
              if(!coList.contains(col) && !col.equals((""))){
                  coList.add(col);
              }
              data.put("QTY",util.nvl(rs.getString("qty")));
              data.put("CTS",util.nvl(rs.getString("cts")));
              data.put("VLU", util.nvl(rs.getString("vlu")));
              data.put("AVG", util.nvl(rs.getString("avg")));
              data.put("RAP", util.nvl(rs.getString("avg_dis")));
              if(indexFapri!=0)
              data.put("FAPRI", util.nvl(rs.getString("fapri")));             
              if(indexMfgpri!=0)
              data.put("MFGPRI", util.nvl(rs.getString("mfgpri")));  
              if(indexagepri!=0)
              data.put("AGE", util.nvl(rs.getString("age")));   
              dataTbl.put(col+"_TTL",data); 
          }
         rs.close(); pst.close();
         String labTtlQ="select "+clrPrp+" clr,sum(qty) qty,"+conQprp+" to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg," + 
         "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis from gt_srch_rslt ";
         labTtlQ=labTtlQ+conQ+conQ2+" group by "+clrPrp;
  
             outLst = db.execSqlLst("Purity Total", labTtlQ, new ArrayList());    
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
          while (rs.next()) { 
              HashMap data=new HashMap();
              String clr=util.nvl(rs.getString("clr"));
              data.put("QTY",util.nvl(rs.getString("qty")));
              data.put("CTS",util.nvl(rs.getString("cts")));
              data.put("VLU", util.nvl(rs.getString("vlu")));
              data.put("AVG", util.nvl(rs.getString("avg")));
              data.put("RAP", util.nvl(rs.getString("avg_dis")));
              if(indexFapri!=0)
              data.put("FAPRI", util.nvl(rs.getString("fapri")));             
              if(indexMfgpri!=0)
              data.put("MFGPRI", util.nvl(rs.getString("mfgpri")));
              if(indexagepri!=0)
              data.put("AGE", util.nvl(rs.getString("age")));   
              dataTbl.put(clr+"_TTL",data);       
          }
         rs.close(); pst.close();
          String grandTtlQ="select sum(qty) qty,"+conQprp+" to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg," + 
          "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis from gt_srch_rslt ";
          grandTtlQ=grandTtlQ+conQ+conQ2;
 
             outLst = db.execSqlLst("Grand Total", grandTtlQ, new ArrayList());  
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
          while (rs.next()) { 
              HashMap data=new HashMap();
              data.put("QTY",util.nvl(rs.getString("qty")));
              data.put("CTS",util.nvl(rs.getString("cts")));
              data.put("VLU", util.nvl(rs.getString("vlu")));
              data.put("AVG", util.nvl(rs.getString("avg")));
              data.put("RAP", util.nvl(rs.getString("avg_dis")));
              if(indexFapri!=0)
              data.put("FAPRI", util.nvl(rs.getString("fapri")));             
              if(indexMfgpri!=0)
              data.put("MFGPRI", util.nvl(rs.getString("mfgpri")));  
              if(indexagepri!=0)
              data.put("AGE", util.nvl(rs.getString("age")));   
              dataTbl.put("TTL",data);       
          }
         rs.close(); pst.close();
         dataTbl.put("TITLE","Color/Purity");  
         reportDtl.put("CO_HDR", clrLst);
         reportDtl.put("CO_CONTENT", coList);
         reportDtl.put("CO_DATA", dataTbl);
         session.setAttribute("reportDtl", reportDtl);
         req.setAttribute("sh", shapeS);
         req.setAttribute("sz", sizeS);
         req.setAttribute("group", groupS);
         req.setAttribute("lab", labS);
         req.setAttribute("stt", sttS);
         req.setAttribute("loc", location);
         req.setAttribute("mlot", mlot);
         req.setAttribute("valueDisplay", valueDisplay);
//         groupDtls(req);
         udf.setReportNme(reportNme);
         udf.setCriteria(criteria);
             util.updAccessLog(req,res,"Group Report", "loadCOPU end");
        return am.findForward("loadCOPU");
         }
     }
    public ActionForward pktDtl(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Group Report", "pktDtl start");
        GroupWiseForm udf = (GroupWiseForm)form;
         GenericInterface genericInt = new GenericImpl();
         String sttS = util.nvl(req.getParameter("stt")).trim(); 
         String labS = util.nvl(req.getParameter("lab")).trim(); 
         String groupS = util.nvl(req.getParameter("group")).trim(); 
         String shapeS= util.nvl(req.getParameter("sh")).trim(); 
         String sizeS = util.nvl(req.getParameter("sz")).trim(); 
         String colorS= util.nvl(req.getParameter("col")).trim(); 
         String clrS = util.nvl(req.getParameter("clr")).trim();
        String location = util.nvl(req.getParameter("loc")).trim();
        String mlot = util.nvl(req.getParameter("mlot")).trim();   
        String valueDisplay = util.nvl(req.getParameter("valueDisplay")).trim();
          HashMap dbinfo = info.getDmbsInfoLst();
          String sh = (String)dbinfo.get("SHAPE");
          String szval = (String)dbinfo.get("SIZE");
          String colval = (String)dbinfo.get("COL");
          String clrval = (String)dbinfo.get("CLR");
          String labval = (String)dbinfo.get("LAB");
         String locval = (String)dbinfo.get("LOC");
           
          ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "GROUP_REPORT","GROUP_VIEW");
          int indexLB = vWPrpList.indexOf(labval)+1;
          int indexSH = vWPrpList.indexOf(sh)+1;
          int indexSZ = vWPrpList.indexOf(szval)+1;
          int indexCol = vWPrpList.indexOf(colval)+1;
          int indexClr = vWPrpList.indexOf(clrval)+1;
          int indexloc = vWPrpList.indexOf(locval)+1;
         
        String lbPrp = util.prpsrtcolumn("prp",indexLB);
        String shPrp =  util.prpsrtcolumn("prp",indexSH);
        String szPrp =  util.prpsrtcolumn("prp",indexSZ);
        String colPrp =  util.prpsrtcolumn("prp",indexCol);
        String clrPrp = util.prpsrtcolumn("prp",indexClr);
        String locPrp = util.prpsrtcolumn("prp",indexloc);
        String lbSrt = util.prpsrtcolumn("srt",indexLB);
        String shSrt = util.prpsrtcolumn("srt",indexSH);
        String szSrt = util.prpsrtcolumn("srt",indexSZ);
        String colSrt = util.prpsrtcolumn("srt",indexCol);
        String clrSrt = util.prpsrtcolumn("srt",indexClr);
        String locSrt =util.prpsrtcolumn("srt",indexloc); 
        
        
        String conQ="";
        String locQ="";
        String mlotQ="";
     
        if(!location.equals("")){
        locQ=" and "+locPrp+"='"+location+"' ";
        }
        if(!mlot.equals(""))
        mlotQ="  and pkt_ty='NR' and nvl(dsp_stt, 'NA') <> 'NA' ";
    ArrayList pktList = new ArrayList();
    String srchQ =" select stk_idn , pkt_ty, vnm, pkt_dte, stt , qty , cts , cert_lab,quot ,rap_rte, rmk ,kts_vw kts,cmnt,img2, (cts*quot) amt, "+
        "decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis,trunc(((cmp*100)/greatest(rap_rte,1)) - 100,2) cmp_dis";

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
    String rsltQ = srchQ + " from gt_srch_rslt where 1=1 "+locQ+mlotQ ;  
    if(!groupS.equals("ALL") && !labS.equals("ALL"))
    conQ=" and dsp_stt='"+groupS+"' and "+lbPrp+"='"+labS+"' ";
    if(!groupS.equals("ALL") && labS.equals("ALL"))
    conQ=" and dsp_stt='"+groupS+"' ";
    if(groupS.equals("ALL") && !labS.equals("ALL"))
    conQ=" and "+lbPrp+"='"+labS+"' ";
    
    
    if(!sttS.equals("ALL") && !labS.equals("ALL") && !sttS.equals(""))
    conQ=conQ+" and stt='"+sttS+"' and "+lbPrp+"='"+labS+"' ";
    if(!sttS.equals("ALL") && labS.equals("ALL") && !sttS.equals(""))
    conQ=conQ+" and   stt='"+sttS+"' ";
    if(sttS.equals("ALL") && !labS.equals("ALL") && !sttS.equals(""))
    conQ=conQ+" and  "+lbPrp+"='"+labS+"' ";
    
    if(!shapeS.equals("ALL") && !sizeS.equals("ALL") && !shapeS.equals(""))
            conQ=conQ+" and "+shPrp+"='"+shapeS+"' and ("+szPrp+"='"+sizeS+"' or "+szPrp+"='+"+sizeS+"') ";
    if(shapeS.equals("ALL") && !sizeS.equals("ALL") && !shapeS.equals(""))
            conQ=conQ+" and ("+szPrp+"='"+sizeS+"' or "+szPrp+"='+"+sizeS+"') ";
    if(!shapeS.equals("ALL") && sizeS.equals("ALL") && !shapeS.equals(""))
            conQ=conQ+" and "+shPrp+"='"+shapeS+"' "; 
    
    if(!colorS.equals("ALL") && !clrS.equals("ALL") && !colorS.equals(""))
                conQ=conQ+" and "+colPrp+"='"+colorS+"' and ("+clrPrp+"='"+clrS+"' or "+clrPrp+"='+"+clrS+"') ";
    if(colorS.equals("ALL") && !clrS.equals("ALL") && !colorS.equals(""))
                conQ=conQ+" and ("+clrPrp+"='"+clrS+"' or "+clrPrp+"='+"+clrS+"') ";
    if(!colorS.equals("ALL") && clrS.equals("ALL") && !colorS.equals(""))
                conQ=conQ+" and "+colPrp+"='"+colorS+"' "; 
    
    rsltQ=rsltQ+conQ+" order by sk1,stk_idn,cert_lab";

    ArrayList ary = new ArrayList();


            ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
    try {
    while (rs.next()) {
    String cert_lab = util.nvl(rs.getString("cert_lab"));
    String stkIdn = util.nvl(rs.getString("stk_idn"));
    HashMap pktPrpMap = new HashMap();
    pktPrpMap.put("stt", util.nvl(rs.getString("rmk")));
    String vnm = util.nvl(rs.getString("vnm"));
    pktPrpMap.put("vnm", vnm);
    pktPrpMap.put("stk_idn", stkIdn);
    pktPrpMap.put("qty", util.nvl(rs.getString("qty")));
    pktPrpMap.put("cts", util.nvl(rs.getString("cts")));
    pktPrpMap.put("RAP_DIS", util.nvl(rs.getString("r_dis")));
    pktPrpMap.put("Rte", util.nvl(rs.getString("quot")));
    pktPrpMap.put("Amount", util.nvl(rs.getString("amt")));
     

    for (int j = 0; j < vWPrpList.size(); j++) {
    String prp = (String)vWPrpList.get(j);

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
        if (prp.toUpperCase().equals("CMP_DIS")){
            if(val.equals(""))
            val = util.nvl(rs.getString("cmp_dis")); 
        }
        if (prp.toUpperCase().equals("RAP_RTE"))
            val = util.nvl(rs.getString("rap_rte"));
        if(prp.equalsIgnoreCase("MEM_COMMENT"))
            val = util.nvl(rs.getString("img2"));
        if(prp.equals("RTE"))
            val = util.nvl(rs.getString("quot"));
        if(prp.equals("KTSVIEW"))
            val = util.nvl(rs.getString("kts"));
        if(prp.equals("COMMENT"))
            val = util.nvl(rs.getString("cmnt"));
    pktPrpMap.put(prp, val);
    }
    pktList.add(pktPrpMap);
    }
        rs.close(); pst.close();
    session.setAttribute("pktList", pktList);
    req.setAttribute("valueDisplay", valueDisplay);
    } catch (SQLException sqle) {

    // TODO: Add catch code
    sqle.printStackTrace();
    }
            util.updAccessLog(req,res,"Group Report", "pktDtl end");
    return am.findForward("loadPktDtl");
        }
    }
    
    
    public ActionForward loadlocation(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             util.updAccessLog(req,res,"Group Report", "loadlocation start");
        GroupWiseForm udf = (GroupWiseForm)af;
             GenericInterface genericInt = new GenericImpl();
         HashMap reportDtl= (HashMap)session.getAttribute("reportDtl");
         HashMap dbinfo = info.getDmbsInfoLst();
         String sh = (String)dbinfo.get("SHAPE");
         String szval = (String)dbinfo.get("SIZE");
         String colval = (String)dbinfo.get("COL");
         String clrval = (String)dbinfo.get("CLR");
         String locval = (String)dbinfo.get("LOC");
         ArrayList vWPrpList = genericInt.genericPrprVw(req, res, "GROUP_REPORT","GROUP_VIEW");
         int indexLB = vWPrpList.indexOf("LAB")+1;
         int indexSH = vWPrpList.indexOf(sh)+1;
         int indexSZ = vWPrpList.indexOf(szval)+1;
         int indexCol = vWPrpList.indexOf(colval)+1;
         int indexClr = vWPrpList.indexOf(clrval)+1;
         int indexloc = vWPrpList.indexOf(locval)+1;
         String lbPrp = util.prpsrtcolumn("prp",indexLB);
         String shPrp =  util.prpsrtcolumn("prp",indexSH);
         String szPrp =  util.prpsrtcolumn("prp",indexSZ);
         String colPrp =  util.prpsrtcolumn("prp",indexCol);
         String clrPrp = util.prpsrtcolumn("prp",indexClr);
         String locPrp = util.prpsrtcolumn("prp",indexloc);
         String lbSrt = util.prpsrtcolumn("srt",indexLB);
         String shSrt = util.prpsrtcolumn("srt",indexSH);
         String szSrt = util.prpsrtcolumn("srt",indexSZ);
         String colSrt = util.prpsrtcolumn("srt",indexCol);
         String clrSrt = util.prpsrtcolumn("srt",indexClr);
         String locSrt =util.prpsrtcolumn("srt",indexloc); 
        
         ArrayList labLst=new ArrayList();
         String location = util.nvl((String)udf.getValue("LOC")); 
         String valueDisplay = util.nvl((String)udf.getValue("valueDisplay")).trim();
         String mlot = util.nvl(req.getParameter("mlot")).trim();
         ArrayList grpList=new ArrayList();
         ResultSet rs = null;
         String reportNme="Group Report Single";
         HashMap dataTbl=new HashMap();
         String locQ="";
         String mlotQ="";
         if(!location.equals("")){
         locQ=" and "+locPrp+"='"+location+"' ";
         }
         if(!mlot.equals(""))
         mlotQ="  and pkt_ty='NR' and nvl(dsp_stt, 'NA') <> 'NA' ";
         String labPrpQ="select distinct "+lbPrp+" labPrp , "+lbSrt+" labsrt from gt_srch_rslt Where 1=1 "+locQ+mlotQ+" order by "+lbSrt;

             ArrayList outLst = db.execSqlLst("Lab Property", labPrpQ, new ArrayList());
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             labLst.add(util.nvl(rs.getString("labPrp")));
         }
         rs.close(); pst.close();
         String dataQ="select "+lbPrp+" lab,sum(qty) qty,to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg," + 
         "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis,dsp_stt grp from gt_srch_rslt  where 1=1 " +locQ+mlotQ+ 
         "group by "+lbPrp+", dsp_stt";

             outLst = db.execSqlLst("Data", dataQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             HashMap data=new HashMap();
             String lab=util.nvl(rs.getString("lab"));
             String grp=util.nvl(rs.getString("grp"));
             data.put("QTY",util.nvl(rs.getString("qty")));
             data.put("CTS",util.nvl(rs.getString("cts")));
             data.put("VLU", util.nvl(rs.getString("vlu")));
             data.put("AVG", util.nvl(rs.getString("avg")));
             data.put("RAP", util.nvl(rs.getString("avg_dis")));
             dataTbl.put(grp+"_"+lab,data);       
         }
         rs.close(); pst.close();
         String grpTtlQ="select sum(qty) qty,to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg, " + 
         "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis ,dsp_stt grp from gt_srch_rslt,rule_dtl a,mrule b " +
         "where a.rule_idn = b.rule_idn and b.mdl = 'JFLEX' and b.nme_rule = 'GROUP_DTL' and dsp_stt=dsc "+locQ+mlotQ+" group by dsp_stt,srt_fr " + 
         "order by srt_fr";

             outLst = db.execSqlLst("Group Total", grpTtlQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             HashMap data=new HashMap();
             String grp=util.nvl(rs.getString("grp"));
             if(!grpList.contains(grp) && !grp.equals((""))){
                 grpList.add(grp);
             }
             data.put("QTY",util.nvl(rs.getString("qty")));
             data.put("CTS",util.nvl(rs.getString("cts")));
             data.put("VLU", util.nvl(rs.getString("vlu")));
             data.put("AVG", util.nvl(rs.getString("avg")));
             data.put("RAP", util.nvl(rs.getString("avg_dis")));
             dataTbl.put(grp+"_TTL",data);       
         }
         rs.close(); pst.close();
         
         String labTtlQ="select "+lbPrp+" lab,sum(qty) qty,to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg," + 
         "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis from gt_srch_rslt where 1=1 " +locQ+mlotQ+ 
         "group by "+lbPrp;
 
             outLst = db.execSqlLst("Lab Total", labTtlQ, new ArrayList());    
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             HashMap data=new HashMap();
             String lab=util.nvl(rs.getString("lab"));
             data.put("QTY",util.nvl(rs.getString("qty")));
             data.put("CTS",util.nvl(rs.getString("cts")));
             data.put("VLU", util.nvl(rs.getString("vlu")));
             data.put("AVG", util.nvl(rs.getString("avg")));
             data.put("RAP", util.nvl(rs.getString("avg_dis")));
             dataTbl.put(lab+"_TTL",data);       
         }
         rs.close(); pst.close();
         String grandTtlQ="select sum(qty) qty,to_char(trunc(sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),2),'99999990.00') cts,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) vlu,trunc(sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))),0) avg, " + 
         "trunc(((sum(quot*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))/(sum(rap_Rte*decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3)))/sum(decode(PKT_TY,'NR',trunc(cts, 2),trunc(cts,3))))*100) - 100, 2) avg_dis from gt_srch_rslt where 1=1 " +locQ+mlotQ;
 
             outLst = db.execSqlLst("Grand Total", grandTtlQ, new ArrayList());  
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
         while (rs.next()) { 
             HashMap data=new HashMap();
             data.put("QTY",util.nvl(rs.getString("qty")));
             data.put("CTS",util.nvl(rs.getString("cts")));
             data.put("VLU", util.nvl(rs.getString("vlu")));
             data.put("AVG", util.nvl(rs.getString("avg")));
             data.put("RAP", util.nvl(rs.getString("avg_dis")));
             dataTbl.put("TTL",data);       
         }
         rs.close(); pst.close();
         dataTbl.put("TITLE","Group/Lab");
         HashMap groupDsc =((HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_groupDsc") == null)?new HashMap():(HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_groupDsc"); 
         dataTbl.putAll(groupDsc);
         reportDtl.put("GRP_HDR", labLst);
         reportDtl.put("GRP_CONTENT", grpList);
         reportDtl.put("GRP_DATA", dataTbl);
         session.setAttribute("reportDtl", reportDtl);
         req.setAttribute("loc", location);
         req.setAttribute("mlot", mlot);
         req.setAttribute("valueDisplay", valueDisplay);
         udf.setValue("LOC",location);
         udf.setValue("valueDisplay",valueDisplay);
         udf.setReportNme(reportNme);
             util.updAccessLog(req,res,"Group Report", "loadlocation end");
        return am.findForward("load");
         }
     }
    public ActionForward createGridXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Group Report", "createGridXL start");
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String excelNm=util.nvl(req.getParameter("excelNm"));
        String report=util.nvl(req.getParameter("Report"));
        String colSpan=util.nvl(req.getParameter("colSpan"));
        String qty=util.nvl(req.getParameter("qty"));
        String cts=util.nvl(req.getParameter("cts"));
        String avg=util.nvl(req.getParameter("avg"));
        String rap=util.nvl(req.getParameter("rap"));
        String vlu=util.nvl(req.getParameter("vlu"));
        String fapri=util.nvl(req.getParameter("fap"));
        String mfgpri=util.nvl(req.getParameter("mfgp"));
        String age=util.nvl(req.getParameter("age"));
            
        String fileNm = excelNm+"ReportExcel"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        HSSFWorkbook hwb = null;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        hwb = xlUtil.getDataGridInXl(req,colSpan,qty,cts,avg,rap,vlu,fapri,mfgpri,age,report);
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Group Report", "createGridXL end");
        return null;
        }
    }
    
    public ActionForward createPKTDtlXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Group Report", "createPKTDtlXL start");
          ExcelUtil xlUtil = new ExcelUtil();
          xlUtil.init(db, util, info);
          OutputStream out = res.getOutputStream();
          String CONTENT_TYPE = "getServletContext()/vnd-excel";
          String fileNm = "PacketDtlReportExcel"+util.getToDteTime()+".xls";
          res.setContentType(CONTENT_TYPE);
          res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
          ArrayList pktList = (ArrayList)session.getAttribute("pktList");
          ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
          HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
          hwb.write(out);
          out.flush();
          out.close();
            util.updAccessLog(req,res,"Group Report", "createPKTDtlXL end");
          return null;
        }
    }
 
//    public void groupDtls(HttpServletRequest req){
//        HttpSession session = req.getSession(false);
//        InfoMgr info = (InfoMgr)session.getAttribute("info");
//        DBUtil util = new DBUtil();
//        DBMgr db = new DBMgr();
//        db.setCon(info.getCon());
//        util.setDb(db);
//        util.setInfo(info);
//        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//        util.setLogApplNm(info.getLoApplNm());
//    HashMap groupDsc = (session.getAttribute("groupDsc") == null)?new HashMap():(HashMap)session.getAttribute("groupDsc");
//
//    try {
//    if(groupDsc.size() == 0) {
//        HashMap dbinfo = info.getDmbsInfoLst();
//        String cnt=util.nvl((String)dbinfo.get("CNT"));
//        String mem_ip=util.nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//        int mem_port=Integer.parseInt(util.nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//        MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//        groupDsc=(HashMap)mcc.get(cnt+"_groupDsc");
//        if(groupDsc==null){
//        groupDsc=new HashMap();
//    String gtView = "select chr_fr, chr_to , dsc , dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
//    " b.mdl = 'JFLEX' and b.nme_rule = 'GROUP_DTL' and a.til_dte is null order by a.srt_fr ";
//    ResultSet rs = db.execSql("gtView", gtView, new ArrayList());
//    while (rs.next()) {
//    groupDsc.put(util.nvl(rs.getString("dsc")), util.nvl(rs.getString("chr_fr")));
//    }
//        rs.close(); pst.close();
//        Future fo = mcc.delete(cnt+"_groupDsc");
//        System.out.println("add status:_groupDsc" + fo.get());
//        fo = mcc.set(cnt+"_groupDsc", 24*60*60, groupDsc);
//        System.out.println("add status:_groupDsc" + fo.get());
//        }
//        mcc.shutdown();
//    session.setAttribute("groupDsc", groupDsc);
//    }
//    } catch (SQLException sqle) {
//    // TODO: Add catch code
//    sqle.printStackTrace();
//        }catch(Exception ex){
//         System.out.println( ex.getMessage() );
//        }
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
                util.updAccessLog(req,res,"Group Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Group Report", "init");
            }
            }
            return rtnPg;
            }
    
    public GroupWiseActionGrpWise() {
        super();
    }
}
