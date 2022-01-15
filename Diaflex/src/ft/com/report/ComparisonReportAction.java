package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.UIForms;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.lab.LabResultForm;
import ft.com.lab.LabResultImpl;
import ft.com.lab.LabResultInterface;

import java.io.IOException;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ComparisonReportAction  extends DispatchAction {
    public ComparisonReportAction() {
        super();
    }
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
            util.updAccessLog(req,res,"Comparison Report", "load start");
        ComparisonReportForm udf = (ComparisonReportForm)af;
        udf.resetAll();
            GenericInterface genericInt = new GenericImpl();
            ArrayList comSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_COM_RPT_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_COM_RPT_SRCH");
        info.setGncPrpLst(comSrchList);
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("COMPARISON_REPORT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("COMPARISON_REPORT");
            allPageDtl.put("COMPARISON_REPORT",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Comparison Report", "load end");
        return am.findForward("load");
        }
    }
    
    public ActionForward view(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Comparison Report", "view start");
        ComparisonReportForm udf = (ComparisonReportForm)af;
        String reportTyp = util.nvl((String)udf.getValue("reportTyp"));
        String stt = util.nvl((String)udf.getValue("stt"));
         insertGt(req,res,udf);
        ArrayList pktDtlList = FetchResult(req, res, reportTyp);
        session.setAttribute("pktList", pktDtlList);
        req.setAttribute("view", "Y");
        req.setAttribute("rptTyp", reportTyp);
        req.setAttribute("stt", stt);
            util.updAccessLog(req,res,"Comparison Report", "view end");
        return am.findForward("load");
        }
    }
    public ActionForward Issue(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Comparison Report", "Issue start");
        ComparisonReportForm udf = (ComparisonReportForm)af;
        ArrayList ary = null;
        int empId = 0;
        String empIdqry="select nme_idn from nme_v where typ='EMPLOYEE'";

            ArrayList outLst = db.execSqlLst("empId", empIdqry, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs1 = (ResultSet)outLst.get(1);
        if(rs1.next()) {
          empId=rs1.getInt(1);
        }
            rs1.close(); pst.close();
        int prcid=0;

            outLst = db.execSqlLst("mprcId", "select idn from mprc where is_stt='LB_RS' and grp <> 'ODD' and stt='A'", new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs1 = (ResultSet)outLst.get(1);
        if(rs1.next()) {
          prcid = rs1.getInt(1);
        }  
        rs1.close(); pst.close();
        int issueIdn=0;
        ArrayList stockList = (ArrayList)session.getAttribute("pktList");
        for(int i=0;i< stockList.size();i++){
            HashMap stockPkt = (HashMap)stockList.get(i);
             String stkIdn = (String)stockPkt.get("stkIdn");
             String isChecked = util.nvl((String)udf.getValue(stkIdn));
            if(isChecked.equals("yes")){
                if(issueIdn==0){
                    ary = new ArrayList();
                    ary.add(String.valueOf(prcid));
                    ary.add(String.valueOf(empId));
                    ArrayList out = new ArrayList();
                    out.add("I");
                    CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", ary ,out);
                    issueIdn = cst.getInt(3);
                  cst.close();
                  cst=null;
                }
                ary = new ArrayList();
                ary.add(String.valueOf(issueIdn));
                ary.add(stkIdn);
                ary.add(stockPkt.get("cts"));
                ary.add(stockPkt.get("qty"));
                ary.add("RT");
                String issuePkt = "ISS_RTN_PKG.ISS_PKT(pIssId => ?, pStkIdn => ? , pCts => ?,  pQty => ? , pStt => ?)";
                int ct = db.execCall("issuePkt", issuePkt, ary);
                
            }
        }
        udf.reset();
        if(issueIdn!=0)
            req.setAttribute("msg","Requested packets get Issue with Issue Id "+issueIdn);
            util.updAccessLog(req,res,"Comparison Report", "Issue end");
        return am.findForward("load");
        }
    }
    public int insertGt(HttpServletRequest req,HttpServletResponse res,ComparisonReportForm udf){
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
        String seq = util.nvl((String)udf.getValue("seq"));
        String frmdte = util.nvl((String)udf.getValue("frmdte"));
        String todte = util.nvl((String)udf.getValue("todte"));
        String stt = util.nvl((String)udf.getValue("stt"));
        String vnm = util.nvl((String)udf.getValue("vnm"));
        ArrayList ary = new ArrayList();
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        String srchRefQ = "";
        if(!vnm.equals(""))
        vnm = util.getVnm(vnm);
        if(!seq.equals("") || !vnm.equals("") || !stt.equals("0") || (!frmdte.equals("") && !todte.equals(""))){
           srchRefQ ="";
           
             srchRefQ = " Insert into gt_srch_rslt (stk_idn , vnm , pkt_dte, stt , flg , qty , cts , rmk , cert_lab , sk1, pkt_ty,  cmp, rap_rte , quot , rap_dis ) " + 
                            " select distinct b.idn, b.vnm,  b.dte, b.stt , 'Z' , b.qty , b.cts , b.tfl3 , b.cert_lab , b.sk1, b.pkt_ty,nvl(b.cmp,b.upr) , b.rap_rte , nvl(b.upr,b.cmp) , decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))  "+
                            " from  mstk b  "+
                            " where  b.cts > 0  ";
             ary = new ArrayList();             
                 if(!seq.equals("") || (!frmdte.equals("") && !todte.equals(""))){
                
                ary = new ArrayList();
               
                 if(!seq.equals("")){
                   srchRefQ = " Insert into gt_srch_rslt (stk_idn , vnm , pkt_dte, stt , flg , qty , cts , rmk , cert_lab , sk1, pkt_ty,  cmp, rap_rte , quot , rap_dis ) " + 
                                " select distinct b.idn, b.vnm,  b.dte, b.stt , 'Z' , b.qty , b.cts , tfl3 , b.cert_lab , b.sk1, b.pkt_ty,nvl(b.cmp,b.upr) , b.rap_rte , nvl(b.upr,b.cmp) , decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))  "+
                                "     from lab_inward_ora a , mstk b , stk_dtl c "+
                                " where a.idn = b.idn  and b.cts > 0  and a.flg='P' and b.idn = c.mstk_idn and c.mprp='LAB_RTN_DTE'\n" + 
                                "  and c.grp=1 and  a.load_seq = ? " ;
                     ary.add(seq);
                  }else{
                 srchRefQ =
                     " Insert into gt_srch_rslt (stk_idn , vnm , pkt_dte, stt , flg , qty , cts , rmk , cert_lab , sk1, pkt_ty,  cmp, rap_rte , quot , rap_dis  ) " + 
                     " select distinct b.idn, b.vnm,  b.dte, b.stt , 'Z' , b.qty , b.cts , b.tfl3 , b.cert_lab , b.sk1, b.pkt_ty,nvl(b.cmp,b.upr) , b.rap_rte , nvl(b.upr,b.cmp) , decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2)) "+
                      " from mstk b, iss_rtn_v a, iss_rtn_dtl c "+
                      " where b.idn = c.iss_stk_idn and a.iss_id = c.iss_id and a.prc_grp = 'LAB' "+
                      " and trunc(c.rtn_dt) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
                      
                 }
             }
                 
            if(stt.equals("IN"))
               srchRefQ = srchRefQ+" and b.stt in ('LB_IS','LB_RS','LB_RI','LB_CFRS','LB_CF') ";
            else
            srchRefQ = srchRefQ+" and b.stt not in ('LB_IS','LB_RS','LB_RI','LB_CFRS','LB_CF') ";
            
            if(!vnm.equals("")){
                srchRefQ = srchRefQ+ " and (b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+") )";  
            }
        }
        
        String isRecheck = util.nvl((String)udf.getValue("recheck"));
        if(isRecheck.equals("YES")){
          String deleteGt = "delete from  gt_srch_rslt c where  NOT EXISTS ( " + 
          "select 1 from iss_rtn_dtl a , iss_Rtn b , mprc d where a.iss_id = b.iss_id " + 
          "and a.iss_stk_idn = c.stk_idn and b.prc_id = d.idn and d.is_stt='LB_RI')";
          db.execUpd("delete from gt", deleteGt, new ArrayList());
        }
        ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_COM_RPT_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_COM_RPT_SRCH");
        info.setGncPrpLst(genricSrchLst);
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
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
                    String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                    if(!reqVal1.equals("")){
                    paramsMap.put(lprp + "_" + lVal, reqVal1);
                    }
                       
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
                    paramsMap.put(lprp+"_1", fldVal1);
                    paramsMap.put(lprp+"_2", fldVal2);
                }
                }
            }
        if(!srchRefQ.equals("") && (!seq.equals("")  || !vnm.equals("")) || (!frmdte.equals("") && !todte.equals(""))){
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        String pktPrp = "DP_GT_PRP_UPD(pMdl => ?, pTyp => 'PRT')";
        ary = new ArrayList();
        ary.add("LBCOM_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        }
    

        if(paramsMap.size()>0 && !stt.equals("") && vnm.equals("")){
            if(stt.equals("0"))
                stt="RECPT";
            paramsMap.put("stt", "ALL");
            paramsMap.put("mdl", "LBCOM_VIEW");
            paramsMap.put("DELGT", "NO");
            util.genericSrchGRP3_SRCH(paramsMap);
        }
         
        
     return ct;
    }
    
  public void getUpDwDtl(HttpServletRequest req,HttpServletResponse res){
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
    ArrayList genViewPrp = genericInt.genericPrprVw(req, res, "LAB_UP_DW","LAB_UP_DW");
    ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "LbComViewLst","LBCOM_VIEW");
//    int ct = db.execCall("refresh vw", "DBMS_MVIEW.REFRESH('MX_LAB_PRC_ISS_MV')", new ArrayList()); 
    ArrayList viewPrp = (ArrayList)session.getAttribute("LabViewLst");
      HashMap dbinfo = info.getDmbsInfoLst();
      String ctval = (String)dbinfo.get("CUT");
      String symval = (String)dbinfo.get("SYM");
      String polval = (String)dbinfo.get("POL");
      String flval = (String)dbinfo.get("FL");
    HashMap colClrMap = new HashMap();
    if(genViewPrp!=null && genViewPrp.size()>0){
    for(int i=0;i<genViewPrp.size();i++){
    String prpVal=util.nvl((String)genViewPrp.get(i));
    int indexPrp = vwPrpLst.indexOf(prpVal)+1;
    if(indexPrp > -1){
    String lbPrp = util.prpsrtcolumn("prp",indexPrp);
        String lbSrt = util.prpsrtcolumn("srt",indexPrp);
//    String upDwsqlCol ="select count(*) cnt\n" + 
//    ", decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-')) dsc\n" + 
//    "from gt_srch_rslt gt, iss_rtn_prp irp, MX_LAB_PRC_ISS_MV m, prp p1, prp p2\n" + 
//    "where 1 = 1\n" + 
//    "and gt.stk_idn = m.iss_stk_idn\n" + 
//    "and m.iss_id = irp.iss_id\n" + 
//    "and m.iss_stk_idn = irp.iss_stk_idn\n" + 
//    "and p1.mprp = irp.mprp and p1.val = replace(replace(irp.iss_val, '+', ''), '-', '')\n" + 
//    "and replace(replace(gt."+lbPrp+", '+', ''), '-', '') =  p2.prt1 \n" + 
//    "and p1.mprp in ('"+prpVal+"') and p2.mprp in ('"+prpVal+"') \n" + 
//    "GROUP BY decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-'))";
        String upDwsqlCol ="select count(*) cnt\n" + 
        ", decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-')) dsc\n" + 
        "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" + 
        "where 1 = 1\n" + 
        "and gt.stk_idn = irp.iss_stk_idn\n" + 
        "and gt.srch_id = irp.iss_id\n" + 
        "and p1.mprp = irp.mprp and p1.val = replace(replace(irp.iss_val, '+', ''), '-', '')\n" + 
//        "and replace(replace(gt."+lbPrp+", '+', ''), '-', '') =  p2.prt1 \n" + 
        "and decode(replace(replace(gt."+lbPrp+", '+', ''), '-', ''),p1.val,p1.srt,gt."+lbSrt+") =  p2.srt\n"+
        "and p1.mprp in ('"+prpVal+"') and p2.mprp in ('"+prpVal+"') \n" + 
        "GROUP BY decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-'))";
    if(prpVal.equals(ctval) || prpVal.equals(symval) || prpVal.equals(polval)) {
//        upDwsqlCol ="select count(*) cnt\n" + 
//              ", decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-')) dsc\n" + 
//              "from gt_srch_rslt gt, iss_rtn_prp irp, MX_LAB_PRC_ISS_MV m, prp p1, prp p2\n" + 
//              "where 1 = 1\n" + 
//              "and gt.stk_idn = m.iss_stk_idn\n" + 
//              "and m.iss_id = irp.iss_id\n" + 
//              "and m.iss_stk_idn = irp.iss_stk_idn\n" + 
//              "and p1.mprp = irp.mprp and p1.val = replace(replace(replace(replace(replace(replace(irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" + 
//              "and replace(replace(replace(replace(replace(replace(gt."+lbPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')  =  p2.prt1 \n" + 
//              "and p1.mprp in ('"+prpVal+"') and p2.mprp in ('"+prpVal+"') \n" + 
//              "GROUP BY decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-'))";   
        upDwsqlCol ="select count(*) cnt\n" + 
              ", decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-')) dsc\n" + 
              "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" + 
              "where 1 = 1\n" + 
              "and gt.stk_idn = irp.iss_stk_idn\n" + 
              "and gt.srch_id = irp.iss_id\n" + 
              "and p1.mprp = irp.mprp and p1.val = replace(replace(replace(replace(replace(replace(irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" + 
              "and replace(replace(replace(replace(replace(replace(gt."+lbPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')  =  p2.prt1 \n" + 
              "and p1.mprp in ('"+prpVal+"') and p2.mprp in ('"+prpVal+"') \n" + 
              "GROUP BY decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-'))";  
    }

        ArrayList outLst = db.execSqlLst("UpDwSql", upDwsqlCol, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
      try {
          while (rs.next()) {
          String cnt = util.nvl(rs.getString("cnt"));
          String dsc = util.nvl(rs.getString("dsc"));
          colClrMap.put(prpVal+"_"+dsc, cnt);
          }
          rs.close(); pst.close();
      } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
      }
    }
    }
    }
      req.setAttribute("colClrUPDWMap", colClrMap);
      
  }
  

    public ActionForward assortLabXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Comparison Report", "assortLabXL start");
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        LabResultInterface labResultInt=new LabResultImpl();
        ArrayList pktDtl = labResultInt.pktList(req, res,"M");

        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "AssotLabCompExcel"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        HSSFWorkbook hwb = null;
        ArrayList itemHdr = (ArrayList)req.getAttribute("ItemHdr");
        hwb =  xlUtil.getGenXl(itemHdr, pktDtl);
        
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Comparison Report", "assortLabXL end");
        return null;
        }
    }
    
    public ActionForward fnlAssortXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Comparison Report", "fnlAssortXL start");
            GenericInterface genericInt = new GenericImpl();
            ArrayList pktList = new ArrayList();
            ArrayList itemHdr = new ArrayList();
            ArrayList ary = new ArrayList();
            ArrayList prpDspBlocked = info.getPageblockList();
            ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "ISSUEFNLASSORT_VIEW","ISSUEFNLASSORT_VIEW");
            ArrayList assrtVwPrpLst = genericInt.genericPrprVw(req, res, "FNL_ASSORTVW","FNL_ASSORTVW");
            int sr=1;
            db.execUpd("delete", "delete from gt_srch_multi", new ArrayList());
            ExcelUtil xlUtil = new ExcelUtil();
            xlUtil.init(db, util, info);
            OutputStream out1 = res.getOutputStream();
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "PacketDtlReportExcel"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
            HashMap dbinfo = info.getDmbsInfoLst();
            String colval = (String)dbinfo.get("COL");
            String clrval = (String)dbinfo.get("CLR");
            String cutval = (String)dbinfo.get("CUT");
            String polval = (String)dbinfo.get("POL");
            String symval = (String)dbinfo.get("SYM");
            String flval = (String)dbinfo.get("FL");
            
            itemHdr.add("Sr No");
            
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

            
            String query ="insert into gt_srch_multi (sk1,stt,vnm,quot,rap_rte,stk_idn,flg,cts,cert_lab)" +
                          " select sk1,stt,vnm ,quot ,rap_rte,stk_idn,'Z',cts,cert_lab from  gt_srch_rslt where flg='M'\n";
            int ct = db.execUpd(" Srch Prp ", query, new ArrayList());
            
            String pktPrp = "srch_pkg.POP_PKT_PRP_TEST(pMdl=>?,pTbl=>'GT_SRCH_MULTI')";
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

            
            String rsltQ = srchQ + " from gt_srch_multi b where flg ='Z' order by sk1 , cts " ;
                

            outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
            String stk_idn=util.nvl((String)rs.getString("stk_idn"));
                String rte = util.nvl((String)rs.getString("rate"));
                String rap_rte = util.nvl((String)rs.getString("rap_rte"));
            HashMap pktPrpMap = new HashMap();
            pktPrpMap.put("Sr No", String.valueOf(sr++));
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
                    String lprp= util.nvl(lprpLstsplit[i]);
                    String lval = util.nvl(lprpValsplit[i]);
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
            HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            hwb.write(out1);
            out1.flush();
            out1.close();
        return null;
        }
    }
    public ArrayList FetchResult(HttpServletRequest req ,HttpServletResponse res, String reportTyp)throws Exception{
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
       ary.add(reportTyp);
        int ct = db.execCall("labRtn", "ISS_RTN_PKG.LAB_RTN_ISS_TYP(pTyp => ?)", ary);
       
       ArrayList pktDtlList = SearchRslt(req,res,"Z");
       getUpDwDtl(req,res);
       return pktDtlList ;
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
          util.updAccessLog(req,res,"Comparison Report", "pktDtl start");
          GenericInterface genericInt = new GenericImpl();
      HashMap dbinfo = info.getDmbsInfoLst();
      String ctval = (String)dbinfo.get("CUT");
      String symval = (String)dbinfo.get("SYM");
      String polval = (String)dbinfo.get("POL");
      String flval = (String)dbinfo.get("FL");
      ArrayList pktList = new ArrayList();
      ArrayList itemHdr = new ArrayList();
      String sign=util.nvl(req.getParameter("sign"), "+").trim();
      String lprp=util.nvl(req.getParameter("lprp")).trim();
      ArrayList prpDspBlocked = info.getPageblockList();
      ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "LbComViewLst","LBCOM_VIEW");
      HashMap signDtl=new HashMap();
      signDtl.put("P", "+");signDtl.put("M", "-");signDtl.put("E", "=");
      int count=1;
      int indexPrp = vwPrpLst.indexOf(lprp)+1;
      String lbPrp = util.prpsrtcolumn("prp",indexPrp);
      String lbSrt = util.prpsrtcolumn("srt",indexPrp);
      itemHdr.add("SR");
      itemHdr.add("VNM");
      itemHdr.add("STT");
          String  srchQ =  " select gt.stk_idn , gt.pkt_ty,  gt.vnm, gt.pkt_dte, gt.sk1 , gt.stt ,gt.rmk , gt.qty , gt.cts , gt.cert_lab,gt.rap_rte,gt.kts_vw kts,gt.cmnt  ";         

          for (int i = 0; i < vwPrpLst.size(); i++) {
              String prp=(String)vwPrpLst.get(i);
              String fld = "prp_";
              int j = i + 1;
              if (j < 10)
                  fld += "00" + j;
              else if (j < 100)
                  fld += "0" + j;
              else if (j > 100)
                  fld += j;

             srchQ += ", " + fld;
             if(prpDspBlocked.contains(prp)){
             }else
             itemHdr.add(prp);    
           }

          
          String rsltQ = srchQ + "  from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" + 
          "where 1 = 1\n" + 
          "and gt.stk_idn = irp.iss_stk_idn\n" + 
          "and gt.srch_id = irp.iss_id\n" ;
          if(lprp.equals(ctval) || lprp.equals(symval) || lprp.equals(polval)) {              
          rsltQ+=" and p1.mprp = irp.mprp and p1.val = replace(replace(replace(replace(replace(replace(irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" + 
          " and replace(replace(replace(replace(replace(replace(gt."+lbPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')  =  p2.prt1 \n" ; 
          }else{
          rsltQ+=" and p1.mprp = irp.mprp and p1.val = replace(replace(irp.iss_val, '+', ''), '-', '')\n" + 
                 "and decode(replace(replace(gt."+lbPrp+", '+', ''), '-', ''),p1.val,p1.srt,gt."+lbSrt+") =  p2.srt\n";
//              "and replace(replace(gt."+lbPrp+", '+', ''), '-', '') = p2.prt1 \n" ;         
          }
          rsltQ+=" and p1.mprp in ('"+lprp+"') and p2.mprp in ('"+lprp+"') \n" + 
          "and decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-'))='"+(String)signDtl.get(sign)+"' order by gt.sk1 , gt.cts ";

          ArrayList outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
          try {
              while(rs.next()) { 
                  String stt = util.nvl(rs.getString("stt"));
                  HashMap pktPrpMap = new HashMap();
                  pktPrpMap.put("STT", stt);  
                  pktPrpMap.put("SR", String.valueOf(count++));  
                  String vnm = util.nvl(rs.getString("vnm"));   
                  pktPrpMap.put("VNM",vnm);
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
                       if (prp.toUpperCase().equals("RAP_RTE"))
                           val = util.nvl(rs.getString("rap_rte"));
                       if(prp.equals("KTSVIEW"))
                           val = util.nvl(rs.getString("kts"));
                       if(prp.equals("COMMENT"))
                           val = util.nvl(rs.getString("cmnt"));
                       if(prp.equals("LAB"))
                           val = util.nvl(rs.getString("cert_lab"));
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
          util.updAccessLog(req,res,"Comparison Report", "pktDtl end");
      return am.findForward("pktDtl"); 
      }
  }
    public ArrayList SearchRslt( HttpServletRequest req, HttpServletResponse res,String flg)
    throws Exception {
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
         String colval = (String)dbinfo.get("COL");
         String clrval = (String)dbinfo.get("CLR");
         String cutval = util.nvl((String)dbinfo.get("CUT"));
        String symval = (String)dbinfo.get("SYM");
        String polval = (String)dbinfo.get("POL");
        String flval = (String)dbinfo.get("FL");
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst =genericInt.genericPrprVw(req, res, "LbComViewLst","LBCOM_VIEW");
        ArrayList pktDtlList = new ArrayList();
        String reportQuery = " select c.nme emp , a.stk_idn , a.vnm ,  max(decode(b.mprp, '"+colval+"', b.iss_val, null)) iss_col , max(decode(b.mprp, '"+colval+"', b.rtn_val, null)) rtn_col , "+ 
                             " max(decode(b.mprp, '"+clrval+"', b.iss_val, null)) iss_clr , max(decode(b.mprp, '"+clrval+"', b.rtn_val, null)) rtn_clr , max(decode(b.mprp, '"+cutval+"', b.iss_val, null)) iss_cut , max(decode(b.mprp, '"+cutval+"', b.rtn_val, null)) rtn_cut "+
                             " , max(decode(b.mprp, '"+polval+"', b.iss_val, null)) iss_pol , max(decode(b.mprp, '"+polval+"', b.rtn_val, null)) rtn_pol"+
                            " , max(decode(b.mprp, '"+symval+"', b.iss_val, null)) iss_sym , max(decode(b.mprp, '"+symval+"', b.rtn_val, null)) rtn_sym"+
                            " , max(decode(b.mprp, '"+flval+"', b.iss_val, null)) iss_fl , max(decode(b.mprp, '"+flval+"', b.rtn_val, null)) rtn_fl"+
                            " , max(decode(b.mprp, 'CRTWT', b.iss_val, null)) iss_CRTWT , max(decode(b.mprp, 'CRTWT', b.rtn_val, null)) rtn_CRTWT"+
                             " ,a.kts_vw kts,a.cmnt,  a.sk1 ";
        String grp="";
         for (int i = 0; i < vwPrpLst.size(); i++) {
             String fld = "prp_";
             int j = i + 1;
             if (j < 10)
                 fld += "00" + j;
             else if (j < 100)
                 fld += "0" + j;
             else if (j > 100)
                 fld += j;

              

            reportQuery += ", " + fld;
             
             grp += ", " + fld;
            
          }
         reportQuery = reportQuery +" from gt_srch_rslt a, iss_rtn_prp_comp_v b, nme_v c " + 
        " where a.stk_idn = b.idn and b.emp_id = c.nme_idn and a.srch_id = b.iss_id " + 
        " and a.flg = ? and b.mprp in ('"+colval+"','"+clrval+"','"+cutval+"','"+symval+"','"+polval+"','"+flval+"','CRTWT') " + 
        " group by c.nme, a.stk_idn, a.vnm   "+grp+" ,a.kts_vw,a.cmnt, a.sk1 , a.cts order by a.sk1 , a.cts ";
         ArrayList params=new ArrayList();
         params.add(flg);

        ArrayList outLst = db.execSqlLst("conditionSql", reportQuery ,params );
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
         HashMap pktDtl = null;
          try {
             while (rs.next()) {
                 pktDtl = new HashMap();
                 String emp = util.nvl(rs.getString("emp"));
                 String stkIdn = util.nvl(rs.getString("stk_idn"));
        
                 String issCol = util.nvl(rs.getString("iss_col"));
                 String issClr = util.nvl(rs.getString("iss_clr"));
                 String issCut = util.nvl(rs.getString("iss_cut"));
                  String issPol = util.nvl(rs.getString("iss_pol"));
                  String issSym = util.nvl(rs.getString("iss_sym"));
                  String issFl = util.nvl(rs.getString("iss_fl"));
                 String rtnCol = util.nvl(rs.getString("rtn_col"));
                 String rtnClr = util.nvl(rs.getString("rtn_clr"));
                 String rtnCut = util.nvl(rs.getString("rtn_cut"));
                  String rtnPol = util.nvl(rs.getString("rtn_pol"));
                  String rtnSym = util.nvl(rs.getString("rtn_sym"));
                  String rtnFl = util.nvl(rs.getString("rtn_fl"));
                 pktDtl.put("emp", emp);
                 pktDtl.put("vnm", util.nvl(rs.getString("vnm")));
                 pktDtl.put("vnm", util.nvl(rs.getString("vnm")));
                 pktDtl.put("issCol", issCol);
                 pktDtl.put("issClr",issClr);
                 pktDtl.put("issCut",issCut);
                  pktDtl.put("issPol", issPol);
                  pktDtl.put("issSym",issSym);
                  pktDtl.put("issFl",issFl);
                 pktDtl.put("rtnCol", rtnCol);
                 pktDtl.put("rtnClr",rtnClr);
                 pktDtl.put("rtnCut", rtnCut);
                  pktDtl.put("rtnPol", rtnPol);
                  pktDtl.put("rtnSym",rtnSym);
                  pktDtl.put("rtnFl", rtnFl);
              pktDtl.put("issCRTWT",util.nvl(rs.getString("iss_CRTWT")));
              pktDtl.put("rtnCRTWT", util.nvl(rs.getString("rtn_CRTWT")));
                 pktDtl.put("stkIdn", stkIdn);
                pktDtl.put("vnm", util.nvl(rs.getString("vnm")));
                 for(int j=0; j < vwPrpLst.size(); j++){
                      String prp = (String)vwPrpLst.get(j);
                       
                       String fld="prp_";
                       if(j < 9)
                               fld="prp_00"+(j+1);
                       else    
                               fld="prp_0"+(j+1);
                       
                       String val = util.nvl(rs.getString(fld)) ;
                     if(prp.equals("KTSVIEW"))
                         val = util.nvl(rs.getString("kts"));
                     if(prp.equals("COMMENT"))
                         val = util.nvl(rs.getString("cmnt"));
                         
                         pktDtl.put(prp, val);
                 }
                 pktDtlList.add(pktDtl);          
          }
             rs.close(); pst.close();
         } catch (SQLException sqle) {
             // TODO: Add catch code
             sqle.printStackTrace();
         }
        return pktDtlList ;    
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
        util.updAccessLog(req,res,"Comparison Report", "grpWiseRpt start");
    ComparisonReportForm udf = (ComparisonReportForm)af; 
    GenericInterface genericInt = new GenericImpl();
    HashMap dbinfo = info.getDmbsInfoLst();
    String sh = (String)dbinfo.get("SHAPE");
    String szval = (String)dbinfo.get("SIZE");
    String ctval = (String)dbinfo.get("CUT");
    String symval = (String)dbinfo.get("SYM");
    String polval = (String)dbinfo.get("POL");
    String flval = (String)dbinfo.get("FL");
    ArrayList allowGrpVw = genericInt.genericPrprVw(req,res,"ALLOWGRP_PRPVW","ALLOWGRP_PRPVW");   
    ArrayList ANLSVWList = genericInt.genericPrprVw(req, res, "LbComViewLst","LBCOM_VIEW");
    HashMap grpdatatable =new HashMap();
    ArrayList szGrpLst =new ArrayList();
    ArrayList shGrpLst=new ArrayList();
    ArrayList lprpGrpLst =new ArrayList();
        int indexsh = ANLSVWList.indexOf(sh)+1;   
        String shPrp = util.prpsrtcolumn("prp",indexsh);
        String shSrt = util.prpsrtcolumn("srt",indexsh);
        int indexsz = ANLSVWList.indexOf(szval)+1;   
        String szPrp = util.prpsrtcolumn("prp",indexsz);
        String szSrt = util.prpsrtcolumn("srt",indexsz);
    for(int i=0;i<allowGrpVw.size();i++){
        lprpGrpLst =new ArrayList();
        String lprp=(String)allowGrpVw.get(i);
        if(ANLSVWList.contains(lprp) && ANLSVWList.contains(szval) && ANLSVWList.contains(sh)){
        int indexlprp = ANLSVWList.indexOf(lprp)+1;   
        String lprpPrp = util.prpsrtcolumn("prp",indexlprp);
        String lprpSrt = util.prpsrtcolumn("srt",indexlprp);
        
//        String srchQ="select decode(gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY') shape,sz.grp szgrp,p1.grp, count(*) cnt, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-')) Dsc\n" + 
//        "from gt_srch_rslt gt, iss_rtn_prp irp, MX_LAB_PRC_ISS_MV m, prp p1, prp p2,prp sz\n" + 
//        "where 1 = 1\n" + 
//        "and gt.stk_idn = m.iss_stk_idn\n" + 
//        "and m.iss_id = irp.iss_id\n" + 
//        "and m.iss_stk_idn = irp.iss_stk_idn\n" + 
//        "And P1.Mprp = Irp.Mprp And P1.Val = Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
//        "and replace(replace(gt."+lprpPrp+", '+', ''), '-', '') = p2.prt1 \n" + 
//        "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
//        "And Gt."+szPrp+"=Sz.Val And Sz.Mprp='"+szval+"'\n" + 
//        "Group By Decode(Gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY'), Sz.Grp,P1.Grp, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-'))\n" + 
//        "union\n" + 
//        "select decode(gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY') shape,sz.grp szgrp,'GRPTotal'grp, count(*) cnt, 'Total' Dsc\n" + 
//        "from gt_srch_rslt gt, iss_rtn_prp irp, MX_LAB_PRC_ISS_MV m, prp p1, prp p2,prp sz\n" + 
//        "where 1 = 1\n" + 
//        "and gt.stk_idn = m.iss_stk_idn\n" + 
//        "and m.iss_id = irp.iss_id\n" + 
//        "and m.iss_stk_idn = irp.iss_stk_idn\n" + 
//        "And P1.Mprp = Irp.Mprp And P1.Val = Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
//        "and replace(replace(gt."+lprpPrp+", '+', ''), '-', '') = p2.prt1 \n" + 
//        "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
//        "and gt."+szPrp+"=sz.val and sz.mprp='"+szval+"'\n" + 
//        "Group By Decode(Gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY'), Sz.Grp";
        String srchQ="select decode(gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY') shape,sz.grp szgrp,p1.grp, count(*) cnt, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-')) Dsc\n" + 
        "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2,prp sz\n" + 
        "where 1 = 1\n" + 
        "and gt.stk_idn = irp.iss_stk_idn\n" + 
        "and gt.srch_id = irp.iss_id\n" + 
        "And P1.Mprp = Irp.Mprp And P1.Val = Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
        "and replace(replace(gt."+lprpPrp+", '+', ''), '-', '') = p2.prt1 \n" + 
        "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
        "And Gt."+szPrp+"=Sz.Val And Sz.Mprp='"+szval+"'\n" + 
        "Group By Decode(Gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY'), Sz.Grp,P1.Grp, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-'))\n" + 
        "union\n" + 
        "select decode(gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY') shape,sz.grp szgrp,'GRPTotal'grp, count(*) cnt, 'Total' Dsc\n" + 
        "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2,prp sz\n" + 
        "where 1 = 1\n" + 
        "and gt.stk_idn = irp.iss_stk_idn\n" + 
        "and gt.srch_id = irp.iss_id\n" +  
        "And P1.Mprp = Irp.Mprp And P1.Val = Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
        "and replace(replace(gt."+lprpPrp+", '+', ''), '-', '') = p2.prt1 \n" + 
        "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
        "and gt."+szPrp+"=sz.val and sz.mprp='"+szval+"'\n" + 
        "Group By Decode(Gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY'), Sz.Grp";
        if(lprp.equals(ctval) || lprp.equals(symval) || lprp.equals(polval)) {
//            srchQ="select decode(gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY') shape,sz.grp szgrp,p1.grp, count(*) cnt, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-')) Dsc\n" + 
//                    "from gt_srch_rslt gt, iss_rtn_prp irp, MX_LAB_PRC_ISS_MV m, prp p1, prp p2,prp sz\n" + 
//                    "where 1 = 1\n" + 
//                    "and gt.stk_idn = m.iss_stk_idn\n" + 
//                    "and m.iss_id = irp.iss_id\n" + 
//                    "and m.iss_stk_idn = irp.iss_stk_idn\n" + 
//                    "And P1.Mprp = Irp.Mprp And P1.Val = replace(replace(replace(replace(replace(replace(Irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" + 
//                    "and replace(replace(replace(replace(replace(replace(gt."+lprpPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '') = p2.prt1 \n" + 
//                    "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
//                    "And Gt."+szPrp+"=Sz.Val And Sz.Mprp='"+szval+"'\n" + 
//                    "Group By Decode(Gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY'), Sz.Grp,P1.Grp, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-'))\n" + 
//                    "union\n" + 
//                    "select decode(gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY') shape,sz.grp szgrp,'GRPTotal'grp, count(*) cnt, 'Total' Dsc\n" + 
//                    "from gt_srch_rslt gt, iss_rtn_prp irp, MX_LAB_PRC_ISS_MV m, prp p1, prp p2,prp sz\n" + 
//                    "where 1 = 1\n" + 
//                    "and gt.stk_idn = m.iss_stk_idn\n" + 
//                    "and m.iss_id = irp.iss_id\n" + 
//                    "and m.iss_stk_idn = irp.iss_stk_idn\n" + 
//                    "And P1.Mprp = Irp.Mprp And P1.Val = replace(replace(replace(replace(replace(replace(Irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" + 
//                    "and replace(replace(replace(replace(replace(replace(gt."+lprpPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '') = p2.prt1 \n" + 
//                    "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
//                    "and gt."+szPrp+"=sz.val and sz.mprp='"+szval+"'\n" + 
//                    "Group By Decode(Gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY'), Sz.Grp";
            
                        srchQ="select decode(gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY') shape,sz.grp szgrp,p1.grp, count(*) cnt, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-')) Dsc\n" + 
                    "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2,prp sz\n" + 
                    "where 1 = 1\n" + 
                    "and gt.stk_idn =irp.iss_stk_idn\n" + 
                    "and gt.srch_id = irp.iss_id\n" + 
                    "And P1.Mprp = Irp.Mprp And P1.Val = replace(replace(replace(replace(replace(replace(Irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" + 
                    "and replace(replace(replace(replace(replace(replace(gt."+lprpPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '') = p2.prt1 \n" + 
                    "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
                    "And Gt."+szPrp+"=Sz.Val And Sz.Mprp='"+szval+"'\n" + 
                    "Group By Decode(Gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY'), Sz.Grp,P1.Grp, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-'))\n" + 
                    "union\n" + 
                    "select decode(gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY') shape,sz.grp szgrp,'GRPTotal'grp, count(*) cnt, 'Total' Dsc\n" + 
                    "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2,prp sz\n" + 
                    "where 1 = 1\n" + 
                    "and gt.stk_idn =irp.iss_stk_idn\n" + 
                    "and gt.srch_id = irp.iss_id\n" + 
                    "And P1.Mprp = Irp.Mprp And P1.Val = replace(replace(replace(replace(replace(replace(Irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" + 
                    "and replace(replace(replace(replace(replace(replace(gt."+lprpPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '') = p2.prt1 \n" + 
                    "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
                    "and gt."+szPrp+"=sz.val and sz.mprp='"+szval+"'\n" + 
                    "Group By Decode(Gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY'), Sz.Grp";
        }

        ArrayList outLst = db.execSqlLst("srchQ", srchQ, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
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
            if(!shGrpLst.contains(shape))
            shGrpLst.add(shape);
        }
        rs.close(); pst.close();
        Collections.sort(lprpGrpLst);
        grpdatatable.put(lprp,lprpGrpLst);
        
//        srchQ="select decode(gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY') shape, count(*) cnt, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-')) Dsc\n" + 
//        "from gt_srch_rslt gt, iss_rtn_prp irp, MX_LAB_PRC_ISS_MV m, prp p1, prp p2\n" + 
//        "where 1 = 1\n" + 
//        "and gt.stk_idn = m.iss_stk_idn\n" + 
//        "and m.iss_id = irp.iss_id\n" + 
//        "and m.iss_stk_idn = irp.iss_stk_idn\n" + 
//        "And P1.Mprp = Irp.Mprp And P1.Val = Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
//        "and replace(replace(gt."+lprpPrp+", '+', ''), '-', '') = p2.prt1 \n" + 
//        "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
//        "Group By Decode(Gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY'), Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-'))\n" + 
//        "union\n" + 
//        "select decode(gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY') shape, count(*) cnt, 'Total' Dsc\n" + 
//        "from gt_srch_rslt gt, iss_rtn_prp irp, MX_LAB_PRC_ISS_MV m, prp p1, prp p2\n" + 
//        "where 1 = 1\n" + 
//        "and gt.stk_idn = m.iss_stk_idn\n" + 
//        "and m.iss_id = irp.iss_id\n" + 
//        "and m.iss_stk_idn = irp.iss_stk_idn\n" + 
//        "And P1.Mprp = Irp.Mprp And P1.Val = Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
//        "and replace(replace(gt."+lprpPrp+", '+', ''), '-', '') = p2.prt1 \n" + 
//        "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
//        "Group By Decode(Gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY')\n" + 
//        "union\n" + 
//        "select 'SHTotal' shape, count(*) cnt, 'Total' Dsc\n" + 
//        "from gt_srch_rslt gt, iss_rtn_prp irp, MX_LAB_PRC_ISS_MV m, prp p1, prp p2\n" + 
//        "where 1 = 1\n" + 
//        "and gt.stk_idn = m.iss_stk_idn\n" + 
//        "and m.iss_id = irp.iss_id\n" + 
//        "and m.iss_stk_idn = irp.iss_stk_idn\n" + 
//        "And P1.Mprp = Irp.Mprp And P1.Val = Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
//        "and replace(replace(gt."+lprpPrp+", '+', ''), '-', '') = p2.prt1 \n" + 
//        "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') ";
                srchQ="select decode(gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY') shape, count(*) cnt, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-')) Dsc\n" + 
        "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" + 
        "where 1 = 1\n" + 
        "and gt.stk_idn = irp.iss_stk_idn\n" + 
        "and gt.srch_id = irp.iss_id\n" +  
        "And P1.Mprp = Irp.Mprp And P1.Val = Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
        "and replace(replace(gt."+lprpPrp+", '+', ''), '-', '') = p2.prt1 \n" + 
        "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
        "Group By Decode(Gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY'), Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-'))\n" + 
        "union\n" + 
        "select decode(gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY') shape, count(*) cnt, 'Total' Dsc\n" + 
        "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" + 
        "where 1 = 1\n" + 
        "and gt.stk_idn = irp.iss_stk_idn\n" + 
        "and gt.srch_id = irp.iss_id\n" + 
        "And P1.Mprp = Irp.Mprp And P1.Val = Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
        "and replace(replace(gt."+lprpPrp+", '+', ''), '-', '') = p2.prt1 \n" + 
        "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
        "Group By Decode(Gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY')\n" + 
        "union\n" + 
        "select 'SHTotal' shape, count(*) cnt, 'Total' Dsc\n" + 
        "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" + 
        "where 1 = 1\n" + 
        "and gt.stk_idn = irp.iss_stk_idn\n" + 
        "and gt.srch_id = irp.iss_id\n" + 
        "And P1.Mprp = Irp.Mprp And P1.Val = Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
        "and replace(replace(gt."+lprpPrp+", '+', ''), '-', '') = p2.prt1 \n" + 
        "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') ";
        if(lprp.equals(ctval) || lprp.equals(symval) || lprp.equals(polval)) {
//            srchQ="select decode(gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY') shape, count(*) cnt, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-')) Dsc\n" + 
//            "from gt_srch_rslt gt, iss_rtn_prp irp, MX_LAB_PRC_ISS_MV m, prp p1, prp p2\n" + 
//            "where 1 = 1\n" + 
//            "and gt.stk_idn = m.iss_stk_idn\n" + 
//            "and m.iss_id = irp.iss_id\n" + 
//            "and m.iss_stk_idn = irp.iss_stk_idn\n" + 
//            "And P1.Mprp = Irp.Mprp And P1.Val = replace(replace(replace(replace(replace(replace(Irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" + 
//            "and replace(replace(replace(replace(replace(replace(gt."+lprpPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '') = p2.prt1 \n" + 
//            "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
//            "Group By Decode(Gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY'), Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-'))\n" + 
//            "union\n" + 
//            "select decode(gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY') shape, count(*) cnt, 'Total' Dsc\n" + 
//            "from gt_srch_rslt gt, iss_rtn_prp irp, MX_LAB_PRC_ISS_MV m, prp p1, prp p2\n" + 
//            "where 1 = 1\n" + 
//            "and gt.stk_idn = m.iss_stk_idn\n" + 
//            "and m.iss_id = irp.iss_id\n" + 
//            "and m.iss_stk_idn = irp.iss_stk_idn\n" + 
//            "And P1.Mprp = Irp.Mprp And P1.Val = replace(replace(replace(replace(replace(replace(Irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" + 
//            "and replace(replace(replace(replace(replace(replace(gt."+lprpPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '') = p2.prt1 \n" + 
//            "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
//            "Group By Decode(Gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY')\n" + 
//            "union\n" + 
//            "select 'SHTotal' shape, count(*) cnt, 'Total' Dsc\n" + 
//            "from gt_srch_rslt gt, iss_rtn_prp irp, MX_LAB_PRC_ISS_MV m, prp p1, prp p2\n" + 
//            "where 1 = 1\n" + 
//            "and gt.stk_idn = m.iss_stk_idn\n" + 
//            "and m.iss_id = irp.iss_id\n" + 
//            "and m.iss_stk_idn = irp.iss_stk_idn\n" + 
//            "And P1.Mprp = Irp.Mprp And P1.Val = replace(replace(replace(replace(replace(replace(Irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" + 
//            "and replace(replace(replace(replace(replace(replace(gt."+lprpPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '') = p2.prt1 \n" + 
//            "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') ";
            
                        srchQ="select decode(gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY') shape, count(*) cnt, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-')) Dsc\n" + 
            "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" + 
            "where 1 = 1\n" + 
            "and gt.stk_idn = irp.iss_stk_idn\n" + 
            "and gt.srch_id = irp.iss_id\n" + 
            "And P1.Mprp = Irp.Mprp And P1.Val = replace(replace(replace(replace(replace(replace(Irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" + 
            "and replace(replace(replace(replace(replace(replace(gt."+lprpPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '') = p2.prt1 \n" + 
            "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
            "Group By Decode(Gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY'), Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-'))\n" + 
            "union\n" + 
            "select decode(gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY') shape, count(*) cnt, 'Total' Dsc\n" + 
            "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" + 
            "where 1 = 1\n" + 
            "and gt.stk_idn = irp.iss_stk_idn\n" + 
            "and gt.srch_id = irp.iss_id\n" + 
            "And P1.Mprp = Irp.Mprp And P1.Val = replace(replace(replace(replace(replace(replace(Irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" + 
            "and replace(replace(replace(replace(replace(replace(gt."+lprpPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '') = p2.prt1 \n" + 
            "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
            "Group By Decode(Gt."+shPrp+",'RD','ROUND','Round','ROUND','ROUND','ROUND','FANCY')\n" + 
            "union\n" + 
            "select 'SHTotal' shape, count(*) cnt, 'Total' Dsc\n" + 
            "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" + 
            "where 1 = 1\n" + 
            "and gt.stk_idn = irp.iss_stk_idn\n" + 
            "and gt.srch_id = irp.iss_id\n" + 
            "And P1.Mprp = Irp.Mprp And P1.Val = replace(replace(replace(replace(replace(replace(Irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" + 
            "and replace(replace(replace(replace(replace(replace(gt."+lprpPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '') = p2.prt1 \n" + 
            "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') ";
        }

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
    }
        if(szGrpLst.size()>0){
        Collections.sort(szGrpLst);
        Collections.sort(shGrpLst, Collections.reverseOrder());
        grpdatatable.put("SIZEGRP",szGrpLst);
        grpdatatable.put("SHAPEGRP",shGrpLst);    
        }
        session.setAttribute("grpdatatable", grpdatatable);
        req.setAttribute("View", "Y");
        util.updAccessLog(req,res,"Comparison Report", "grpWiseRpt end");
    return am.findForward("grpWiseRpt");
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
            util.updAccessLog(req,res,"Comparison Report", "moncmpGrpPKTDTL start");
        ArrayList pktList = new ArrayList();
            GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "LbComViewLst","LBCOM_VIEW");
        ArrayList itemHdr = new ArrayList();
        ArrayList ary = new ArrayList();
            HashMap dbinfo = info.getDmbsInfoLst();
            ArrayList ANLSVWList = genericInt.genericPrprVw(req, res, "LbComViewLst","LBCOM_VIEW");
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
        String  srchQ =  " select gt.stk_idn , gt.pkt_ty,  gt.vnm, gt.pkt_dte, gt.stt ,gt.dsp_stt, gt.qty , gt.cts , gt.rmk ";
        for (int i = 0; i < vwPrpLst.size(); i++) {
            String fld = "gt.prp_";
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
        String rsltQ = srchQ + " from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2,prp sz\n" + 
        "where 1 = 1\n" + 
        "and gt.stk_idn = irp.iss_stk_idn\n" + 
        "and gt.srch_id = irp.iss_id\n" + 
        "And P1.Mprp = Irp.Mprp \n" + 
        "And P1.Val = Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
        "And Replace(Replace(Gt."+lprpPrp+", '+', ''), '-', '') = p2.prt1 \n" + 
        "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
        "And Gt."+szPrp+"=Sz.Val And Sz.Mprp='"+szval+"' and sz.grp='"+szgrp+"' ";
        if(shape.equals("ROUND"))
        rsltQ=rsltQ+" and gt."+shPrp+" in('RD','ROUND','Round')";
        else
        rsltQ=rsltQ+" and gt."+shPrp+" not in('RD','ROUND','Round')";
        if(!grp.equals("TOTAL"))
        rsltQ=rsltQ+" and p1.grp='"+grp+"'"; 
        if(sign.equals("P"))
        rsltQ=rsltQ+" and Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-'))='+'";
        if(sign.equals("E"))
        rsltQ=rsltQ+" and Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-'))='='";
        if(sign.equals("M"))
        rsltQ=rsltQ+" and Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-'))='-'";
        if(!gtprpVal.equals(""))
        rsltQ=rsltQ+" and Replace(Replace(gt."+lprpPrp+", '+', ''), '-', '')='"+gtprpVal+"'";
        if(!gtmultiprpVal.equals(""))
        rsltQ=rsltQ+" and Replace(Replace(p1.val, '+', ''), '-', '')='"+gtmultiprpVal+"'";
            
        rsltQ=rsltQ+" order by gt.sk1 , gt.cts ";

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
            util.updAccessLog(req,res,"Comparison Report", "moncmpGrpPKTDTL end");
        return am.findForward("pktDtl");
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
        ArrayList ANLSVWList = genericInt.genericPrprVw(req, res, "LbComViewLst","LBCOM_VIEW");
        String sh = (String)dbinfo.get("SHAPE");
        String szval = (String)dbinfo.get("SIZE");
            String ctval = (String)dbinfo.get("CUT");
            String symval = (String)dbinfo.get("SYM");
            String polval = (String)dbinfo.get("POL");
            String flval = (String)dbinfo.get("FL");
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
            conQ+=" and gt."+shPrp+" in('RD','ROUND','Round')";
        else
            conQ+=" and gt."+shPrp+" not in('RD','ROUND','Round')";
        if(!grp.equals("TOTAL"))
        conQ+=" and p1.grp='"+grp+"'";  
//        String grpWiseQ="  select sz.grp szgrp, count(*) cnt, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-')) Dsc,Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')||' : '||Replace(Replace(gt."+lprpPrp+", '+', ''), '-', '') prp\n" + 
//        "from gt_srch_rslt gt, iss_rtn_prp irp, MX_LAB_PRC_ISS_MV m, prp p1, prp p2,prp sz\n" + 
//        "where 1 = 1\n" + 
//        "and gt.stk_idn = m.iss_stk_idn\n" + 
//        "and m.iss_id = irp.iss_id\n" + 
//        "and m.iss_stk_idn = irp.iss_stk_idn\n" + 
//        "And P1.Mprp = Irp.Mprp And P1.Val = Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
//        "and replace(replace(gt."+lprpPrp+", '+', ''), '-', '') = p2.prt1 \n" + 
//        "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
//        "And Gt."+szPrp+"=Sz.Val And Sz.Mprp='SZ'\n" +conQ+ 
//        "Group By Sz.Grp, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-')),Replace(Replace(gt."+lprpPrp+", '+', ''), '-', ''), Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
//        "order by 3,4";
                    String grpWiseQ="  select sz.grp szgrp, count(*) cnt, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-')) Dsc,Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')||' : '||Replace(Replace(gt."+lprpPrp+", '+', ''), '-', '') prp\n" + 
        "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2,prp sz\n" + 
        "where 1 = 1\n" + 
        "and gt.stk_idn = irp.iss_stk_idn\n" + 
        "and gt.srch_id = irp.iss_id\n" + 
        "And P1.Mprp = Irp.Mprp And P1.Val = Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
        "and replace(replace(gt."+lprpPrp+", '+', ''), '-', '') = p2.prt1 \n" + 
        "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
        "And Gt."+szPrp+"=Sz.Val And Sz.Mprp='SZ'\n" +conQ+ 
        "Group By Sz.Grp, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-')),Replace(Replace(gt."+lprpPrp+", '+', ''), '-', ''), Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
        "order by 3,4";
        if(lprp.equals(ctval) || lprp.equals(symval) || lprp.equals(polval)) {
//            grpWiseQ="  select sz.grp szgrp, count(*) cnt, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-')) Dsc,Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')||' : '||Replace(Replace(gt."+lprpPrp+", '+', ''), '-', '') prp\n" + 
//            "from gt_srch_rslt gt, iss_rtn_prp irp, MX_LAB_PRC_ISS_MV m, prp p1, prp p2,prp sz\n" + 
//            "where 1 = 1\n" + 
//            "and gt.stk_idn = m.iss_stk_idn\n" + 
//            "and m.iss_id = irp.iss_id\n" + 
//            "and m.iss_stk_idn = irp.iss_stk_idn\n" + 
//            "And P1.Mprp = Irp.Mprp And P1.Val = replace(replace(replace(replace(replace(replace(Irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" + 
//            "and replace(replace(replace(replace(replace(replace(gt."+lprpPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '') = p2.prt1 \n" + 
//            "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
//            "And Gt."+szPrp+"=Sz.Val And Sz.Mprp='SZ'\n" +conQ+ 
//            "Group By Sz.Grp, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-')),Replace(Replace(gt."+lprpPrp+", '+', ''), '-', ''), Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
//            "order by 3,4";
            grpWiseQ="  select sz.grp szgrp, count(*) cnt, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-')) Dsc,Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')||' : '||Replace(Replace(gt."+lprpPrp+", '+', ''), '-', '') prp\n" + 
            "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2,prp sz\n" + 
            "where 1 = 1\n" + 
            "and gt.stk_idn = irp.iss_stk_idn\n" + 
            "and gt.srch_id = irp.iss_id\n" + 
            "And P1.Mprp = Irp.Mprp And P1.Val = replace(replace(replace(replace(replace(replace(Irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" + 
            "and replace(replace(replace(replace(replace(replace(gt."+lprpPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '') = p2.prt1 \n" + 
            "And P1.Mprp In ('"+lprp+"') And P2.Mprp In ('"+lprp+"') \n" + 
            "And Gt."+szPrp+"=Sz.Val And Sz.Mprp='SZ'\n" +conQ+ 
            "Group By Sz.Grp, Decode(P1.Srt, P2.Srt, '=', Decode(Greatest(P1.Srt, P2.Srt), P1.Srt, '+', '-')),Replace(Replace(gt."+lprpPrp+", '+', ''), '-', ''), Replace(Replace(Irp.Iss_Val, '+', ''), '-', '')\n" + 
            "order by 3,4";
        }

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
            util.updAccessLog(req,res,"Comparison Report", "createXL start");
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "ComparisonReportExcel"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList pktList = SearchRslt(req,res,"M");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdrCOMP");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Comparison Report", "createXL end");
        return null;
        }
    }
  public ActionForward multiComXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"Comparison Report", "multiComXL start");
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        String mutiPRp ="Lab_pkg.Multi_Comp( pMdl=> ? ,  pGrpLmt=> ?)";
        ArrayList ary = new ArrayList();
        ary.add("LBCOM_VIEW");
        ary.add("-1");
        int ct = db.execCall("Multi Comp", mutiPRp, ary);
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "MutipleCompExcel"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        HSSFWorkbook hwb = null;
        
        hwb =  xlUtil.getInXl("memo", req, "LBCOM_VIEW");
        
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
          util.updAccessLog(req,res,"Comparison Report", "multiComXL end");
      return null;
      }
  }
  
    public ActionForward multiLabComXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"Comparison Report", "multiLabComXL start");
          GenericInterface genericInt = new GenericImpl();
          ArrayList itemHdr = genericInt.genericPrprVw(req,res,"MULTI_LAB_CMP","MULTI_LAB_CMP");
          ExcelUtil xlUtil = new ExcelUtil();
          xlUtil.init(db, util, info);
          String CONTENT_TYPE = "getServletContext()/vnd-excel";
          String fileNm = "MutipleLabCompExcel"+util.getToDteTime()+".xls";
          res.setContentType(CONTENT_TYPE);
          res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
          HSSFWorkbook hwb = null;
            
            itemHdr.add(0,"ISS/RTN");
            ArrayList pktDtlList = new ArrayList();
            HashMap mprp = info.getMprp();
            db.execUpd("delete", "delete from gt_srch_multi", new ArrayList());
            String query ="insert into gt_srch_multi(srch_id,stk_idn,flg,vnm,pkt_dte,sl_dte)\n" + 
            "with mprc_v as(select idn from mprc where is_stt in('LB_AU_IS','LB_IS'))\n" + 
            "select a.iss_id,b.iss_stk_idn,'Z',c.vnm,b.iss_dt,b.rtn_dt\n" + 
            "from\n" + 
            "mprc_v mv,iss_rtn a,iss_rtn_dtl b,gt_srch_rslt c\n" + 
            "where \n" + 
            "mv.idn=a.prc_id and a.iss_id=b.iss_id \n" + 
            "and b.iss_stk_idn=c.stk_idn and b.stt in('RT','RI') \n" + 
            "and c.flg='M'\n";
            int ct = db.execUpd(" Srch Prp ", query, new ArrayList());          
          
            query ="delete gt_srch_multi a where exists (\n" + 
            "with count_Stk_idn_v as(\n" + 
            "select count(*) cnt,stk_idn from gt_srch_multi group by stk_idn\n" + 
            ")\n" + 
            "select b.stk_idn from count_Stk_idn_v b where b.cnt <=1\n" + 
            "and a.stk_idn=b.stk_idn\n" + 
            ")\n";
            ct = db.execUpd(" Srch Prp ", query, new ArrayList());   
            
            String prvstk_idn="";
            String prviss_id="";
            HashMap pktDtlIss = new HashMap();
            HashMap pktDtlRtn = new HashMap();
            String pktDtlSql="select a.vnm,to_char(a.pkt_dte,'DD-MM-YYYY') iss_dt,to_char(a.sl_dte,'DD-MM-YYYY') rtn_dt,b.*\n" + 
            "from\n" + 
            "gt_srch_multi a,iss_rtn_prp b\n" + 
            "where\n" + 
            "a.srch_id=b.iss_id and a.stk_idn=b.iss_stk_idn \n" + 
            "order by b.iss_stk_idn,b.iss_id desc";

            ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                String stk_idn=util.nvl(rs.getString("iss_stk_idn"));
                String iss_id=util.nvl(rs.getString("iss_id"));
                String lprp=util.nvl(rs.getString("mprp"));
                if(prvstk_idn.equals(""))
                    prvstk_idn=stk_idn;
                if(prviss_id.equals(""))
                    prviss_id=iss_id;
                
                if(!prviss_id.equals(iss_id)){
                    pktDtlList.add(pktDtlRtn);
                    pktDtlList.add(pktDtlIss);
                    pktDtlIss=new HashMap();
                    pktDtlRtn=new HashMap();
                    prviss_id=iss_id;
                }
                if(!prvstk_idn.equals(stk_idn)){
                    pktDtlList.add(new HashMap());
                    prvstk_idn=stk_idn;
                }
                
                String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
                if(prpTyp.equals("C")){
                    pktDtlIss.put(lprp, util.nvl(rs.getString("iss_val")));
                    pktDtlRtn.put(lprp, util.nvl(rs.getString("rtn_val")));
                }else if(prpTyp.equals("N")){
                    pktDtlIss.put(lprp, util.nvl(rs.getString("iss_num")));
                    pktDtlRtn.put(lprp, util.nvl(rs.getString("rtn_num")));
                }else{
                    pktDtlIss.put(lprp, util.nvl(rs.getString("txt")));
                    pktDtlRtn.put(lprp, util.nvl(rs.getString("txt")));
                }
                pktDtlIss.put("VNM", util.nvl(rs.getString("vnm")));
                pktDtlRtn.put("VNM", util.nvl(rs.getString("vnm")));
                pktDtlIss.put("ISS/RTN", util.nvl(rs.getString("iss_dt")));
                pktDtlRtn.put("ISS/RTN", util.nvl(rs.getString("rtn_dt")));
            }
            rs.close(); pst.close();
            if(!prviss_id.equals("")){
                pktDtlList.add(pktDtlRtn);
                pktDtlList.add(pktDtlIss);
            }
            if(!prvstk_idn.equals("")){
                pktDtlList.add(new HashMap());
            }
          hwb = xlUtil.getGenXl(itemHdr, pktDtlList);
          OutputStream out = res.getOutputStream();
          hwb.write(out);
          out.flush();
          out.close();
          util.updAccessLog(req,res,"Comparison Report", "multiLabComXL end");
        return null;
        }
    }
    
    public ActionForward labExcel(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Comparison Report", "multiComXL start");
          ExcelUtil xlUtil = new ExcelUtil();
          xlUtil.init(db, util, info);
            String pktPrp = "pkgmkt.pktPrp(0,?)";
            ArrayList ary = new ArrayList();
            ary.add("LAB_RS");
            int ct = db.execCall(" Srch Prp ", pktPrp, ary);
            HSSFWorkbook hwb = xlUtil.GetLabComparisionExcel(req);
            OutputStream out = res.getOutputStream();
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "LabResultComparision"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
            hwb.write(out);
            out.flush();
            out.close();
            util.updAccessLog(req,res,"Comparison Report", "multiComXL end");
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
                util.updAccessLog(req,res,"Comparison Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Comparison Report", "init");
            }
            }
            return rtnPg;
            }
}
