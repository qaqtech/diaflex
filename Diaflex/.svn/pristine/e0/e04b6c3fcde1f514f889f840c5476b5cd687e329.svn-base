package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.PdfforReport;
import ft.com.assort.AssortIssueImpl;
import ft.com.dao.DFMenu;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.PacketLookupForm;
import ft.com.marketing.SearchQuery;

import ft.com.report.GenericReportForm;

import java.awt.Color;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;

import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MixGenericReportAction extends DispatchAction {
   
    public MixGenericReportAction() {
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
        MixGenericReportForm udf = (MixGenericReportForm)af;
        GenericInterface  genericInt = new GenericImpl();
        udf.reset();
        HashMap mprp = info.getSrchMprp();
        HashMap prp = info.getSrchPrp();
//        if(prp==null){
//        util.initPrpSrch();
//        prp = info.getSrchPrp();
//        mprp = info.getSrchMprp();
//        }
            HashMap prpMap = new HashMap();
            ArrayList basicPrpLst = (ArrayList)session.getAttribute("mixbasicPrpLst");
            if(basicPrpLst==null){
                basicPrpLst = new ArrayList();
            String getSrchPrp = "Select prp , flg from rep_prp where mdl='MIX_ANLS_BSC_PRP' and flg in('M','S') order by rnk ";
              ArrayList  rsLst = db.execSqlLst(" Srch Prp ", getSrchPrp, new ArrayList());
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs =(ResultSet)rsLst.get(1);
            while (rs.next()) {
                 ArrayList srchMdl = new ArrayList();
                 srchMdl.add(util.nvl(rs.getString("prp")));
                 srchMdl.add(util.nvl(rs.getString("flg")));
                 basicPrpLst.add(srchMdl);
            }
                rs.close();
                stmt.close();
            session.setAttribute("mixbasicPrpLst", basicPrpLst);
            
            }
            prpMap.put("BSC", "mixbasicPrpLst");
            prpMap.put("BSCTTL", "Basic Properties");
            
            session.setAttribute("mixprpMap", prpMap);
            
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("MIXGENERIC_REPORT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("MIXGENERIC_REPORT");
            allPageDtl.put("MIXGENERIC_REPORT",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            ArrayList prpList = new ArrayList();
            pageList= ((ArrayList)pageDtl.get("TABLINK") == null)?new ArrayList():(ArrayList)pageDtl.get("TABLINK");  
            if(pageList!=null && pageList.size() >0){
                  for(int j=0;j<pageList.size();j++){
                  pageDtlMap=(HashMap)pageList.get(j);
                  prpList.add(util.nvl((String)pageDtlMap.get("fld_nme")));
            }}
            session.setAttribute("mixprpList",prpList);
            pageList= ((ArrayList)pageDtl.get("DAYS") == null)?new ArrayList():(ArrayList)pageDtl.get("DAYS");            
            
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            String dflt_val=(String)pageDtlMap.get("dflt_val");
            ArrayList params= new ArrayList();
            String dtePrpQ="select to_char(trunc(sysdate-?), 'dd-mm-rrrr') frmdte,to_char(trunc(sysdate), 'dd-mm-rrrr') todte from dual";
            params.add(dflt_val);
              ArrayList  rsLst = db.execSqlLst("Date calc", dtePrpQ,params);
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs =(ResultSet)rsLst.get(1);
            while (rs.next()) { 
            udf.setValue("frmDte",(util.nvl(rs.getString("frmdte"))));
            udf.setValue("toDte",(util.nvl(rs.getString("todte"))));
            }    
                rs.close();
                stmt.close();
            }
            }
            udf.setValue("srchRef","vnm");
            udf.setValue("srchBy","C");
        return am.findForward("load");
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
            MixGenericReportForm udf = (MixGenericReportForm)af;
            GenericInterface  genericInt = new GenericImpl();
            long lStartTime = new Date().getTime();
            HashMap dbinfo = info.getDmbsInfoLst();
            int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
            db.execUpd("gt delete","delete from gt_srch_rslt", new ArrayList());
            db.execUpd(" Del Old Pkts ", "Delete from gt_pkt_scan", new ArrayList());
            String btn = util.nvl((String)udf.getValue("colClrUpd"));
            String vnm = util.nvl((String)udf.getValue("vnm"));
            String srchRef = util.nvl((String)udf.getValue("srchRef"));
            String srchTyp = util.nvl((String)udf.getValue("srchTyp"));
            String srchBy = util.nvl((String)udf.getValue("srchBy"));
            String imageDtl = util.nvl((String)udf.getValue("imageDtl"));
            String frmDte = util.nvl((String)udf.getValue("frmDte"));
            String toDte = util.nvl((String)udf.getValue("toDte"));
            String gridfmt = "MIX";
            String p1frm = util.nvl((String)udf.getValue("p1frm"));
            String p1to = util.nvl((String)udf.getValue("p1to"));
            String p2frm = util.nvl((String)udf.getValue("p2frm"));
            String p2to = util.nvl((String)udf.getValue("p2to"));
            String p3frm = util.nvl((String)udf.getValue("p3frm"));
            String p3to = util.nvl((String)udf.getValue("p3to"));
            String psearch="N";
            String stt="";
            String mdl="MIX_ANLS_VW";
            int cnt=0;
            ArrayList ary = null;
            ArrayList status=new ArrayList();
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("MIXGENERIC_REPORT");
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
                String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
                pageList= ((ArrayList)pageDtl.get("STTLST") == null)?new ArrayList():(ArrayList)pageDtl.get("STTLST");                
                if(pageList!=null && pageList.size() >0){
                          for(int j=0;j<pageList.size();j++){
                          pageDtlMap=(HashMap)pageList.get(j);
                          fld_nme=(String)pageDtlMap.get("fld_nme");
                          fld_ttl=(String)pageDtlMap.get("fld_ttl");
                          fld_typ=(String)pageDtlMap.get("fld_typ");
                          dflt_val=(String)pageDtlMap.get("dflt_val");
                          val_cond=(String)pageDtlMap.get("val_cond");
                    stt = util.nvl((String)fld_nme);
                    if(!stt.equals(""))
                    status.add(stt); 
                }}
               
            String pMdl = "Z";
            if(!p1frm.equals("") && !p1to.equals("") && !p2frm.equals("")  && !p2to.equals("")){
            pMdl = "P1";  
            psearch="Y";
            }
            if(!p1frm.equals("") && !p1to.equals("") && !p3frm.equals("")  && !p3to.equals("")){
            pMdl = "P1";  
            psearch="Y";
            }
             if(btn.equals("Assort Upgrade"))
                pMdl="AS_UP";
             if(!srchBy.equals("C") && !vnm.equals("")){
                 vnm = util.getVnm(vnm);
                 String[] vnmLst = vnm.split(",");
                 int loopCnt = 1 ;
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
                     
                 vnmSub = vnmSub.replaceAll(",", "");
                 vnmSub = vnmSub.replaceAll("''", ",");
                 vnmSub = vnmSub.replaceAll("'", "");
                     if(!vnmSub.equals("")){
                 vnmSub="'"+vnmSub+"'";
                 ArrayList params = new ArrayList();
                 //        params.add(vnmSub);
                 String insScanPkt = " insert into gt_pkt_scan select * from TABLE(PARSE_TO_TBL("+vnmSub+"))";
                   cnt = db.execDirUpd(" ins scan", insScanPkt,params);
                   System.out.println(insScanPkt);  
                     }
                 }
                   String srchStr = "";
                   if(srchRef.equals("vnm"))
                       srchStr = " (b.vnm = a.vnm or b.tfl3 = a.vnm) ";
                   else if(srchRef.equals("cert_no"))
                       srchStr = " b.cert_no= a.vnm";
                   String srchRefQ =
                   "Insert into gt_srch_rslt (rln_idn, srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt,dsp_stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis) \n" + 
                   "select 1, 1 srchId, pkt_ty, b.idn, b.vnm, b.qty qty, trunc(b.cts,2) cts, b.dte, c.grp1 stt,b.stt \n" + 
                   ", round(nvl(upr, cmp)) rte, rap_rte, cert_lab, cert_no, 'Z' flg, sk1, decode(nvl(upr,0), 0, cmp, upr), decode(rap_rte ,'1',null,'0',null, trunc((nvl(upr,cmp)/greatest(rap_rte,1)*100)-100, 2)) \n" + 
                   "from gt_pkt_scan a,mstk b, df_stk_stt c\n" + 
                   "where b.stt = c.stt and pkt_ty in ('NR') and "+srchStr;
                   
                   if(!imageDtl.equals("")){
                       srchRefQ =
                   "Insert into gt_srch_rslt (rln_idn, srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt,dsp_stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis, CERTIMG, DIAMONDIMG, JEWIMG, SRAYIMG, AGSIMG, MRAYIMG, RINGIMG, LIGHTIMG, REFIMG, VIDEOS, VIDEO_3D) \n" + 
                   "select 1, 1 srchId, pkt_ty, b.idn, b.vnm, b.qty qty, trunc(b.cts,2) cts, b.dte, c.grp1 stt,b.stt \n" + 
                   ", round(nvl(upr, cmp)) rte, rap_rte, cert_lab, cert_no, 'Z' flg, sk1, decode(nvl(upr,0), 0, cmp, upr), decode(rap_rte ,'1',null,'0',null, trunc((nvl(upr,cmp)/greatest(rap_rte,1)*100)-100, 2)), CERTIMG, DIAMONDIMG, JEWIMG, SRAYIMG, AGSIMG, MRAYIMG, RINGIMG, LIGHTIMG, REFIMG, VIDEOS, VIDEO_3D \n" + 
                   "from gt_pkt_scan a,mstk b, df_stk_stt c\n" + 
                   "where b.stt = c.stt and pkt_ty in ('NR') and "+srchStr;    
                   }
                   cnt = db.execDirUpd(" Srch Prp ", srchRefQ, new ArrayList()); 
                   System.out.println(srchRefQ);
                   String pktPrp = "SRCH_PKG.POST_GRP_SRCH(pMdl => ?)";
                   ary = new ArrayList();
                   ary.add(mdl);
                   cnt = db.execCall(" Srch Prp ", pktPrp, ary);
                 status=new ArrayList();
                 String getSrchPrp = "select  distinct stt from gt_srch_rslt order by stt";
               ArrayList  rsLst = db.execSqlLst(" Srch Prp ", getSrchPrp, new ArrayList());
               PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
               ResultSet  rs =(ResultSet)rsLst.get(1);
                 while (rs.next()) {
                  status.add(util.nvl(rs.getString("stt")));    
                 }
                 rs.close();
                 stmt.close();
                 info.setSrchId(0);
                 if(status.size()==0)
                     status.add("MKT");
    //             vnm = util.getVnm(vnm);
    //             String srchRefQ = 
    //             "    Insert into gt_srch_rslt (stk_idn , vnm  , flg , qty , cts , stt ,  cmp, rap_rte , quot , rap_dis , sk1 , pkt_ty ) " + 
    //             " select  b.idn, b.vnm,  'Z' , qty , cts , stt ,cmp , rap_rte , nvl(upr,cmp) , decode(rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2)) , sk1  "+
    //             "  , pkt_ty   from mstk b "+
    //             " where ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))";
    //              int ct = db.execUpd(" Srch Prp ", srchRefQ, new ArrayList());
    //              
    //             ArrayList ary = new ArrayList();
    //             String pktPrp = "pkgmkt.pktPrp(0,?)";
    //             ary = new ArrayList();
    //             ary.add("CO_CLR_UPG_VW");
    //             ct = db.execCall(" Srch Prp ", pktPrp, ary);
             }else{
            int lSrchId=0;
            int pSrchId=0;     
        
            String getSrchId = "srch_pkg.get_srch_id(pRlnId => ?, pTyp => ?, pTrmVal => ?, pMdl => ?, pFlg => ?, pSrchId => ?)";
            ArrayList params = new ArrayList();
            params.add(String.valueOf(0));
            params.add(status.get(0));  
            params.add("1");
            params.add(pMdl);
            params.add("");  
            ArrayList outParams = new ArrayList();
            outParams.add("I");
            CallableStatement cst = db.execCall(" Add Srch Hdr ", getSrchId, params, outParams);
            try {
                lSrchId = cst.getInt(params.size()+1);
              } catch (SQLException e) {
              }
               cst.close();
               cst=null;
            info.setSrchId(lSrchId);
            System.out.println("srch ID:"+lSrchId);
            ArrayList prpNameList = (ArrayList)session.getAttribute("prpList");
            HashMap prpMap = (HashMap)session.getAttribute("prpMap");
            HashMap mprp = info.getMprp();
            HashMap prp = info.getPrp();
            String addSrchDtl = "srch_pkg.add_srch_dtl(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
            String addSrchDtlVal = "srch_pkg.add_srch_dtl_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
            String addSrchDtlSub = "srch_pkg.add_srch_dtl_sub(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
            String addSrchDtlSubVal = "srch_pkg.add_srch_dtl_sub_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
            String addSrchDtlDte = "srch_pkg.add_srch_dtl_dte(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
            
            for(int i=0;i< prpNameList.size();i++){
              String grpNme = (String)prpNameList.get(i);
              String listName = (String)prpMap.get(grpNme);
              ArrayList prpList = (ArrayList)session.getAttribute(listName);
               if(prpList!=null && prpList.size()>0){
                  for(int j=0;j<prpList.size();j++){
                      boolean dtlAddedOnce = false ;
                    ArrayList prpDtl = (ArrayList)prpList.get(j);
                    String lprp= (String)prpDtl.get(0);
                    ArrayList lprpS = (ArrayList)prp.get(lprp+"S");
                    ArrayList lprpV = (ArrayList)prp.get(lprp+"V");
                    String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
                      if(lprpS!=null){
                      for(int m=0; m < lprpV.size(); m++) {
                      
                          String lVal = (String)lprpV.get(m);
                          String lFld =  lprp + "_" + lVal; 
                          String reqVal = util.nvl((String)udf.getValue(lFld));
                          //util.SOP(lprp + " : " + lFld + " : " + reqVal);  
                            if(reqVal.length() > 0 && !reqVal.equals("0")) {  
                              if(!dtlAddedOnce) {
                                params = new ArrayList();
                                params.add(String.valueOf(lSrchId));
                                params.add(lprp);
                                params.add(reqVal);
                                params.add(reqVal);
                                cnt = db.execCall(" SrchDtl ", addSrchDtlVal, params);
                                dtlAddedOnce = true;
                              }
                              params = new ArrayList();
                              params.add(String.valueOf(lSrchId));
                              params.add(lprp);
                              params.add(reqVal);
                              params.add(reqVal);
                              cnt = db.execCall(" SrchDtl ", addSrchDtlSubVal, params);
                          }
                      }
                      }else{
                          String reqVal1 = util.nvl((String)udf.getValue(lprp + "_1"),"");
                          String reqVal2 = util.nvl((String)udf.getValue(lprp + "_2"),"");
                            if(lprpTyp.equals("T"))
                                reqVal2 = reqVal1;
                            
                          
                          if(((reqVal1.length() == 0) || (reqVal2.length() == 0))
                            || ((reqVal1.equals("0")) && (reqVal2.equals("0")) )) {
                              //ignore no selection;
                          } else {
                          if(lprpTyp.equals("T")){
                             ArrayList fmtVal = util.getStrToArr(reqVal1);
                             if(fmtVal!=null && fmtVal.size()>0){
                             for(int k=0;k<fmtVal.size();k++){
                                 String txtVal = (String)fmtVal.get(k);
                                 if(!dtlAddedOnce) {
                                 params = new ArrayList();
                                 params.add(String.valueOf(lSrchId));
                                 params.add(lprp);
                                 params.add(txtVal);
                                 params.add(txtVal);
                                 cnt = db.execCallDir(" SrchDtl ", addSrchDtlVal, params);
                                 dtlAddedOnce = true;
                                 }
                                 params = new ArrayList();
                                 params.add(String.valueOf(lSrchId));
                                 params.add(lprp);
                                 params.add(txtVal);
                                 params.add(txtVal);
                                 cnt = db.execCallDir(" SrchDtl ", addSrchDtlSubVal, params);
                             }
                             }
                             }else{
                            params = new ArrayList();
                            params.add(String.valueOf(lSrchId));
                            params.add(lprp);
                            params.add(reqVal1);
                            params.add(reqVal2);
                          if(lprpTyp.equals("T"))
                            cnt = db.execCall(" SrchDtl ", addSrchDtlVal, params);
                          else if(lprpTyp.equals("D"))
                            cnt = db.execCall(" SrchDtl ", addSrchDtlDte, params);
                          else
                              cnt = db.execCall(" SrchDtl ", addSrchDtl, params);  
                      }}
                  }
              }
            }}
           
            String idn = util.nvl((String)udf.getValue("idn"));
            String byr = util.nvl((String)udf.getValue("byr"));
            if(idn.equals(""))
            idn = byr;            
            if(!srchTyp.equals("0") && !srchTyp.equals("")){
            String insrtAddon = " insert into srch_addon( srch_id , cprp , cidn) "+
            "select ? , ? , ?  from dual ";
            ary = new ArrayList();
            ary.add(String.valueOf(lSrchId));
            ary.add(srchTyp);
            ary.add(idn);
            cnt = db.execUpd("", insrtAddon, ary);
            }
              String Stt=(String)status.get(0); 
              if(Stt.equals("SOLD") && !frmDte.equals("") && !toDte.equals("") && psearch.equals("N")){
              String insrtAddon = " insert into srch_addon( srch_id , cprp , cidn , frm_dte , to_dte ) "+
              "select ? , ? , ? ,to_date('"+frmDte+"', 'dd-mm-rrrr') , to_date('"+toDte+"', 'dd-mm-rrrr') from dual ";
              ary = new ArrayList();
              ary.add(String.valueOf(lSrchId));
              ary.add("SALE");
              ary.add(idn);
              cnt = db.execUpd("insrtAddon Sold", insrtAddon, ary);
              }     
                 
                 ArrayList srchids=new ArrayList();
                 srchids.add(String.valueOf(lSrchId));
                 if(status.size()>1){
                    String getfinalSrchid = "srch_pkg.COPY_SRCH(pFrmId => ?, pTyp => ?, pSrchid => ?)";
                    for(int st=1;st<status.size();st++){
                    Stt=(String)status.get(st);
                    params = new ArrayList();
                    params.add(String.valueOf(lSrchId));
                    params.add(Stt);  
                    outParams = new ArrayList();
                    outParams.add("I");
                    cst = db.execCall(" Add Srch Hdr ", getfinalSrchid, params, outParams);
                    try {
                        pSrchId = cst.getInt(params.size()+1);
                          cst.close();
                      } catch (SQLException e) {
                      }
                        System.out.println(pSrchId);
                        if(pSrchId>0){
                        srchids.add(String.valueOf(pSrchId));
                        if(Stt.equals("SOLD") && !frmDte.equals("") && !toDte.equals("")){
                        String insrtAddon = " insert into srch_addon( srch_id , cprp , cidn , frm_dte , to_dte ) "+
                        "select ? , ? , ? ,to_date('"+frmDte+"', 'dd-mm-rrrr') , to_date('"+toDte+"', 'dd-mm-rrrr') from dual ";
        
                            ary = new ArrayList();
                            ary.add(String.valueOf(pSrchId));
                            ary.add("SALE");
                            ary.add(idn);
                            cnt = db.execUpd("insrtAddon Sold", insrtAddon, ary);
                            } 
                            if(Stt.equals("SOLD") && !srchTyp.equals("0") && !srchTyp.equals("")){
                            String insrtAddon = " insert into srch_addon( srch_id , cprp , cidn) "+
                            "select ? , ? , ?  from dual ";
                            ary = new ArrayList();
                            ary.add(String.valueOf(pSrchId));
                            ary.add(srchTyp);
                            ary.add(idn);
                            cnt = db.execUpd("", insrtAddon, ary);
                            }
                        }
                    }
                 }
                 if(psearch.equals("Y")){
                     status=new ArrayList();
                     status.add("P1");
                     if(!p2frm.equals("")  && !p2to.equals(""))
                     status.add("P2");
                     if(!p3frm.equals("")  && !p3to.equals(""))
                     status.add("P3");
                     String insrtAddon = " insert into srch_addon( srch_id , cprp , frm_dte , to_dte ) "+
                     "select ? , ? , to_date('"+p1frm+"', 'dd-mm-rrrr') , to_date('"+p1to+"', 'dd-mm-rrrr') from dual ";
                     ary = new ArrayList();
                     ary.add(String.valueOf(lSrchId));
                     ary.add("SALE");
                     cnt = db.execUpd("insrtAddon Sold", insrtAddon, ary);
                     String getfinalSrchid = "srch_pkg.COPY_SRCH(pFrmId => ?, pTyp => ?, pSrchid => ?)";
                     for(int st=1;st<status.size();st++){
                     Stt=(String)status.get(st);
                     params = new ArrayList();
                     params.add(String.valueOf(lSrchId));
                     params.add("SOLD");  
                     outParams = new ArrayList();
                     outParams.add("I");
                     cst = db.execCall(" Add Srch Hdr ", getfinalSrchid, params, outParams);
                     try {
                         pSrchId = cst.getInt(params.size()+1);
                           cst.close();
                       } catch (SQLException e) {
                       }    
                     srchids.add(String.valueOf(pSrchId));
                     params = new ArrayList();
                     params.add(Stt);
                     params.add(String.valueOf(pSrchId));
                     db.execUpd("Update", "update msrch set mdl=? where srch_id =?", params);
                     if(Stt.equals("P2"))
                     insrtAddon = " insert into srch_addon( srch_id , cprp , frm_dte , to_dte ) "+
                     "select ? , ? , to_date('"+p2frm+"', 'dd-mm-rrrr') , to_date('"+p2to+"', 'dd-mm-rrrr') from dual ";
                     if(Stt.equals("P3"))
                     insrtAddon = " insert into srch_addon( srch_id , cprp , frm_dte , to_dte ) "+
                     "select ? , ? , to_date('"+p3frm+"', 'dd-mm-rrrr') , to_date('"+p3to+"', 'dd-mm-rrrr') from dual ";
                     ary = new ArrayList();
                     ary.add(String.valueOf(pSrchId));
                     ary.add("SALE");
                     cnt = db.execUpd("insrtAddon Sold", insrtAddon, ary);
                     if(!srchTyp.equals("0") && !srchTyp.equals("")){
                     insrtAddon = " insert into srch_addon( srch_id , cprp , cidn) "+
                     "select ? , ? , ?  from dual ";
                     ary = new ArrayList();
                     ary.add(String.valueOf(pSrchId));
                     ary.add(srchTyp);
                     ary.add(idn);
                     cnt = db.execUpd("", insrtAddon, ary);
                     }
                     }
                 }
                 String srch_pkg = "DP_MIX_SRCH(pSrchId => ?, pMdl=> ?)";
                 if(srchids.size()>0){
                     for(int st=0;st<srchids.size();st++){
                     ary = new ArrayList();
                     ary.add(srchids.get(st));
                     ary.add(mdl);
                     int ct = db.execCall("stk_srch", srch_pkg, ary);
                     }
                 }
                 req.setAttribute("srchids", srchids);
             
             }
             int days=0;
             if(!frmDte.equals("") && !toDte.equals(""))
             days=util.daysbetweendate(frmDte,toDte);
             session.setAttribute("days", String.valueOf(days)); 
             session.setAttribute("GenStt", stt);
             session.setAttribute("statusLst", status);
             session.setAttribute("psearch", psearch);
    //       if(gridfmt.equals("0"))
    //         gridfmt=insertANLS_GRP(req,res,udf);  
    //         session.setAttribute("gridKey", gridfmt);
             gridstructure(gridfmt,session,db);
//             sttColorCode(session,db);
           
                SearchRslt(req,info,db,session);
                long lEndTime = new Date().getTime();
                long difference = lEndTime - lStartTime;
                req.setAttribute("processtm", util.convertMillis(difference));
                return am.findForward("load");
            }
        }
    
    public void SearchRslt( HttpServletRequest req,InfoMgr info,DBMgr db,HttpSession session)
    throws Exception {
    JspUtil jspUtil = new JspUtil();
    HashMap dbinfo = info.getDmbsInfoLst();
    String ageval = (String)dbinfo.get("AGE");
    String hitval = (String)dbinfo.get("HIT");
    ArrayList ANLSVWList = ASPrprViw(session,db);
    int indexAge = ANLSVWList.indexOf(ageval)+1;
    int indexHit = ANLSVWList.indexOf(hitval)+1;
    String agePrp = jspUtil.prpsrtcolumn("prp",indexAge);
    String hitPrp = jspUtil.prpsrtcolumn("prp",indexHit);
    HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
    ArrayList gridcommLst=new ArrayList();
    ArrayList gridrowLst=new ArrayList();
    ArrayList gridcolLst=new ArrayList();
    gridcommLst=(ArrayList)gridstructure.get("COMM");
    gridrowLst=(ArrayList)gridstructure.get("ROW");
    gridcolLst=(ArrayList)gridstructure.get("COL");
    String colPrp = jspUtil.prpsrtcolumn("prp",ANLSVWList.indexOf(gridcolLst.get(0))+1);
    String rowPrp = jspUtil.prpsrtcolumn("prp",ANLSVWList.indexOf(gridrowLst.get(0))+1);
    String conQ="";
    String commprpQ=getconQ("prp",gridcommLst,ANLSVWList);
    String commsrtQ=getconQ("srt",gridcommLst,ANLSVWList);
    if(indexAge!=0){
    conQ="round(sum("+agePrp+")/sum(qty)) age,";
    }
    if(indexHit!=0){
    conQ=conQ+"round(sum("+hitPrp+")/sum(qty)) hit,";
    }
    String shSzSql = "select distinct "+commprpQ+","+commsrtQ+" from gt_srch_rslt order by "+commsrtQ;
      ArrayList  rsLst = db.execSqlLst("shSzSql", shSzSql, new ArrayList());
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
    ArrayList keyList = new ArrayList();
    while(rs.next()){
    String key="";
    for (int i = 0; i < gridcommLst.size(); i++) {
            int index = ANLSVWList.indexOf(gridcommLst.get(i))+1;    
            String fld=jspUtil.prpsrtcolumn("prp",index);
            key += "_" + rs.getString(fld);
    }
    key=key.replaceFirst("\\_", "");    
    keyList.add(key);
    }
        rs.close();
      stmt.close();
    String getTotal = "select stt, sum(qty) qty, trunc(sum(trunc(cts, 2)),2) cts " +
    ", trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),0) avg " +
    " , trunc(sum(rap_rte*trunc(cts,2))/sum(trunc(cts, 2)),2) rap_avg "+
    ", trunc(((sum(quot*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2) avg_dis " +
    " ,"+conQ+""+commprpQ+", "+rowPrp+", "+colPrp+" " +
    " from gt_srch_rslt where 1 = 1 " + //+ dysQ
    " group by "+commprpQ+", "+rowPrp+", "+colPrp+", stt    " ;
    
      rsLst = db.execSqlLst("getTotal", getTotal, new ArrayList());
      stmt =(PreparedStatement)rsLst.get(0);
    rs =(ResultSet)rsLst.get(1);

    HashMap dataDtl = new HashMap();
        ArrayList colList = new ArrayList();
        ArrayList rowList = new ArrayList();
    while(rs.next()){
        String rowVal = rs.getString(rowPrp);
        String colVal = rs.getString(colPrp);
        String status = rs.getString("stt");
        String key="";
        for (int i = 0; i < gridcommLst.size(); i++) {
                int index = ANLSVWList.indexOf(gridcommLst.get(i))+1;    
                String fld=jspUtil.prpsrtcolumn("prp",index);
                key += "_" + rs.getString(fld);
        }
        key=key.replaceFirst("\\_", ""); 
        key = key+"_"+rowVal+"_"+colVal;
    if(!rowList.contains(rowVal))
        rowList.add(rowVal);
    if(!colList.contains(colVal))
        colList.add(colVal);
    dataDtl.put(key+"_"+status+"_QTY", jspUtil.nvl(rs.getString("qty")));
    dataDtl.put(key+"_"+status+"_CTS", jspUtil.nvl(rs.getString("cts")));
    dataDtl.put(key+"_"+status+"_AVG", jspUtil.nvl(rs.getString("avg")));
    dataDtl.put(key+"_"+status+"_RAP", jspUtil.nvl(rs.getString("avg_dis")));
    if(indexAge!=0)
    dataDtl.put(key+"_"+status+"_AGE", jspUtil.nvl(rs.getString("age")));
    if(indexHit!=0)    
    dataDtl.put(key+"_"+status+"_HIT", jspUtil.nvl(rs.getString("hit")));
    }
        rs.close();
        stmt.close();
    session.setAttribute("rowList", rowList);
    session.setAttribute("colList", colList);
    session.setAttribute("commkeyList", keyList);

    String getClrTotal="select sum(qty) qty ,trunc(sum(trunc(cts, 2)),2) cts,trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),0) avg," +
    "trunc(sum(rap_rte*trunc(cts,2))/sum(trunc(cts, 2)),2) rap_avg ," +
    "trunc(((sum(quot*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2) avg_dis , " +
    "stt,"+conQ+""+commprpQ+","+colPrp+
    " from gt_srch_rslt where 1 = 1 and "+colPrp+" !='NA' " +
    " group by "+commprpQ+","+colPrp+",stt" ;
        rsLst = db.execSqlLst("getClrTotal", getClrTotal, new ArrayList());
        stmt =(PreparedStatement)rsLst.get(0);
       rs =(ResultSet)rsLst.get(1);
    while(rs.next()){
    String sum_qty = rs.getString("qty");
    String sum_cts = rs.getString("cts");
    String sum_avg = rs.getString("avg");
    String sum_avgdis = rs.getString("avg_dis");
    String colVal = rs.getString(colPrp);
    String statusClr = rs.getString("stt");
    String key="";
    for (int i = 0; i < gridcommLst.size(); i++) {
    int index = ANLSVWList.indexOf(gridcommLst.get(i))+1;    
    String fld=jspUtil.prpsrtcolumn("prp",index);
    key += "_" + rs.getString(fld);
    }
    key=key.replaceFirst("\\_", ""); 
    dataDtl.put(key+"_ALL_"+colVal+"_"+statusClr+"_QTY", jspUtil.nvl(sum_qty));
    dataDtl.put(key+"_ALL_"+colVal+"_"+statusClr+"_CTS", jspUtil.nvl(sum_cts));
    dataDtl.put(key+"_ALL_"+colVal+"_"+statusClr+"_AVG", jspUtil.nvl(sum_avg));
    dataDtl.put(key+"_ALL_"+colVal+"_"+statusClr+"_RAP", jspUtil.nvl(sum_avgdis));
    if(indexAge!=0)
    dataDtl.put(key+"_ALL_"+colVal+"_"+statusClr+"_AGE", jspUtil.nvl(rs.getString("age")));
    if(indexHit!=0)    
    dataDtl.put(key+"_ALL_"+colVal+"_"+statusClr+"_HIT",jspUtil.nvl(rs.getString("hit")));
    }
        rs.close();
     stmt.close();        
    String getColTotal="select sum(qty) qty ,trunc(sum(trunc(cts, 2)),2) cts ,trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),0) avg,"+
    "trunc(sum(rap_rte*trunc(cts,2))/sum(trunc(cts, 2)),2) rap_avg ," +
    "trunc(((sum(quot*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2) avg_dis , " +
    "stt,"+conQ+""+commprpQ+","+rowPrp+
    " from gt_srch_rslt where 1 = 1 and "+rowPrp+" !='NA' " + " group by "+commprpQ+","+rowPrp+",stt" ;
      rsLst = db.execSqlLst("getColTotal", getColTotal, new ArrayList());
      stmt =(PreparedStatement)rsLst.get(0);
      rs =(ResultSet)rsLst.get(1);
    while(rs.next()){
    String sum_qty = rs.getString("qty");
    String sum_cts = rs.getString("cts");
    String sum_avg = rs.getString("avg");
    String sum_avgdis = rs.getString("avg_dis");
    String rowVal = rs.getString(rowPrp);
    String statusCol = rs.getString("stt");
    String key="";
    for (int i = 0; i < gridcommLst.size(); i++) {
    int index = ANLSVWList.indexOf(gridcommLst.get(i))+1;    
    String fld=jspUtil.prpsrtcolumn("prp",index);
    key += "_" + rs.getString(fld);
    }
        key=key.replaceFirst("\\_", "");    
    dataDtl.put(key+"_"+rowVal+"_ALL_"+statusCol+"_QTY", jspUtil.nvl(sum_qty));
    dataDtl.put(key+"_"+rowVal+"_ALL_"+statusCol+"_CTS", jspUtil.nvl(sum_cts));
    dataDtl.put(key+"_"+rowVal+"_ALL_"+statusCol+"_AVG", jspUtil.nvl(sum_avg));
    dataDtl.put(key+"_"+rowVal+"_ALL_"+statusCol+"_RAP", jspUtil.nvl(sum_avgdis));
        if(indexAge!=0)
    dataDtl.put(key+"_"+rowVal+"_ALL_"+statusCol+"_AGE", jspUtil.nvl(rs.getString("age")));
        if(indexHit!=0)    
    dataDtl.put(key+"_"+rowVal+"_ALL_"+statusCol+"_HIT",jspUtil.nvl(rs.getString("hit")));
    }
        rs.close();
        stmt.close();
    session.setAttribute("dataDtl", dataDtl);
    String grandSQL="select sum(qty) qty,trunc(sum(trunc(cts, 2)),2) cts,trunc(sum(quot*trunc(cts,2))/sum(trunc(cts, 2)),0) avg," +
    "trunc(sum(rap_rte*trunc(cts,2))/sum(trunc(cts, 2)),2) rap_avg ," +
    "trunc(((sum(quot*trunc(cts,2))/sum(trunc(cts, 2)))/(sum(rap_Rte*trunc(cts,2))/sum(trunc(cts, 2)))*100) - 100, 2) avg_dis , " +
    "stt,"+conQ+""+commprpQ+
    " from gt_srch_rslt where "+colPrp+" !='NA' and "+rowPrp+" !='NA'  group by "+commprpQ+",stt" ;
      rsLst = db.execSqlLst("getGrandTotalList", grandSQL, new ArrayList());
      stmt =(PreparedStatement)rsLst.get(0);
      rs =(ResultSet)rsLst.get(1);
    HashMap getGrandTotalList=new HashMap();
    while(rs.next()){
    String sum_qty = rs.getString("qty");
    String sum_cts = rs.getString("cts");
    String sum_avg = rs.getString("avg");
    String sum_avgdis = rs.getString("avg_dis");
    String status = rs.getString("stt");
    String key="";
        for (int i = 0; i < gridcommLst.size(); i++) {
        int index = ANLSVWList.indexOf(gridcommLst.get(i))+1;    
        String fld=jspUtil.prpsrtcolumn("prp",index);
        key += "_" + rs.getString(fld);
        }
        key=key.replaceFirst("\\_", "");     
    getGrandTotalList.put(key+"_"+status+"_QTY", jspUtil.nvl(sum_qty));
    getGrandTotalList.put(key+"_"+status+"_CTS", jspUtil.nvl(sum_cts));
    getGrandTotalList.put(key+"_"+status+"_AVG", jspUtil.nvl(sum_avg));
    getGrandTotalList.put(key+"_"+status+"_RAP", jspUtil.nvl(sum_avgdis));
    if(indexAge!=0)
    getGrandTotalList.put(key+"_"+status+"_AGE", jspUtil.nvl(rs.getString("age")));
    if(indexHit!=0)    
    getGrandTotalList.put(key+"_"+status+"_HIT", jspUtil.nvl(rs.getString("hit")));
    }
    rs.close();
    stmt.close();
    session.setAttribute("getGrandTotalList", getGrandTotalList);

    }
    
    public ArrayList ASPrprViw(HttpSession session,DBMgr db ){
       
        ArrayList asViewPrp = (ArrayList)session.getAttribute("ANLS_VW");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
              
              ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'ANLS_VW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs1 =(ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                stmt.close();
                session.setAttribute("ANLS_VW", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
    
    public String getconQ(String column,ArrayList Lst,ArrayList vwList){
        String rtnQ="";
        for (int i = 0; i < Lst.size(); i++) {
        int index = vwList.indexOf(Lst.get(i))+1;    
        String fld = column+"_";
        if (index < 10)
        fld += "00" + index;
        else if (index < 100)
        fld += "0" + index;
        rtnQ += ", " + fld;
        }
        rtnQ=rtnQ.replaceFirst("\\,", "");
        return rtnQ;
    }
//    public void sttColorCode(HttpSession session,DBMgr db){
//      JspUtil jspUtil = new JspUtil();
//    HashMap sttColorCodeMap = (session.getAttribute("sttColorCodeMap") == null)?new HashMap():(HashMap)session.getAttribute("sttColorCodeMap");
//
//    try {
//    if(sttColorCodeMap.size() == 0) {
//    String gtView = "select chr_fr, chr_to , dsc , dta_typ from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
//    " b.mdl = 'JFLEX' and b.nme_rule = 'GENERIC_REPORT' and a.til_dte is null order by a.srt_fr ";
//    ResultSet rs = db.execSql("gtView", gtView, new ArrayList());
//    while (rs.next()) {
//    sttColorCodeMap.put(jspUtil.nvl(rs.getString("dsc")), jspUtil.nvl(rs.getString("chr_fr")));
//    }
//        rs.close();
//    session.setAttribute("sttColorCodeMap", sttColorCodeMap);
//    }
//    } catch (SQLException sqle) {
//    // TODO: Add catch code
//    sqle.printStackTrace();
//    }
//    }
    public void gridstructure(String gridKey,HttpSession session,DBMgr db){
        JspUtil jspUtil = new JspUtil();
    HashMap gridstructure = new HashMap();
    String typold="";
    ArrayList Lst=new ArrayList();
    ArrayList ary=new ArrayList();
    try {
    String gtView = "select b.mprp,b.typ from  ANLS_GRP a,ANLS_GRP_PRP b\n" + 
    "where a.nme=b.nme and a.nme=? and a.stt='A' and b.stt='A' and a.vld_dte is null and b.vld_dte is null order by b.srt";
    ary.add(gridKey);
      ArrayList  rsLst = db.execSqlLst("gtView", gtView, ary);
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
    while (rs.next()) {
        String typ=jspUtil.nvl(rs.getString("typ"));
        if(!typold.equals(typ) || typold.equals("")){
            typold=typ ; 
            Lst=new ArrayList();
        }
        Lst.add(jspUtil.nvl(rs.getString("mprp")));
        gridstructure.put(typ,Lst);
    }
    rs.close();
        stmt.close();
    session.setAttribute("gridstructure", gridstructure);
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
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
              util.updAccessLog(req,res,"Mix Generic Rpt", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Mix Generic Rpt", "init");
          }
          }
          return rtnPg;
          }

}