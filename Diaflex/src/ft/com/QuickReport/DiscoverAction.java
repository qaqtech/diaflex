
package ft.com.QuickReport;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.ExcelUtilObj;
import ft.com.GtMgr;
import ft.com.InfoMgr;


import ft.com.JspUtil;
import ft.com.dao.Mprc;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.OutputStream;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DiscoverAction extends DispatchAction {

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
            util.updAccessLog(req,res,"Discover Report", "load start");
        DiscoverReportForm dis=(DiscoverReportForm)form ;
        GenericInterface genericInt = new GenericImpl();
        dis.reset();
        ArrayList disSrchList =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DISGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DISGNCSrch"); 
        info.setGncPrpLst(disSrchList);
            ArrayList sttList = new ArrayList();
            String stkStt = "select distinct grp1 , decode(grp1, 'MKT','Marketing','LAB','Lab','ASRT','Assort','SOLD','Sold', grp1) dsc " +
                            " from df_stk_stt where flg='A' and vld_dte is null and grp1 is not null order by dsc ";
          ArrayList outLst = db.execSqlLst("stkStt", stkStt, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                ArrayList sttDtl = new ArrayList();
                sttDtl.add(rs.getString("grp1"));
                sttDtl.add(rs.getString("dsc"));
                sttList.add(sttDtl);
            }
            rs.close();
            pst.close();
            session.setAttribute("sttLst",sttList);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("DISCOVER");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("DISCOVER");
        allPageDtl.put("DISCOVER",pageDtl);
        }
        info.setPageDetails(allPageDtl);                   
            util.updAccessLog(req,res,"Discover Report", "load end");
    return am.findForward("load");
        }
    }
     public ActionForward fecth(ActionMapping am, ActionForm form,

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
     util.updAccessLog(req,res,"Discover Report", "fecth start");
     DiscoverReportForm dis=(DiscoverReportForm)form ;
     GenericInterface genericInt = new GenericImpl();
     int ctg =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_rslt", new ArrayList());
     int ctm =db.execUpd(" Del Old Pkts ", " Delete from gt_srch_multi", new ArrayList());
     int ctp =db.execUpd(" Del Old Pkts ", " Delete from gt_pkt", new ArrayList());
     String dwldExcel=util.nvl((String)dis.getValue("dwldExcel"));
     String termsRteExcel=util.nvl((String)dis.getValue("termsRteExcel"));
     String applybyrte=util.nvl((String)dis.getValue("applybyrte"));
     String applybydis=util.nvl((String)dis.getValue("applybydis"));
     ArrayList genricSrchLst =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DISGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DISGNCSrch"); 
     String trftomktkgExcel=util.nvl((String)dis.getValue("trftomktkgExcel"));
     String dfr = util.nvl((String)dis.getValue("dtefr"));
     String dto = util.nvl((String)dis.getValue("dteto")); 
     String sttstr = util.nvl((String)dis.getValue("stt"));
     String vnm = util.nvl((String)dis.getValue("vnm"));
     String srchTyp =util.nvl((String)dis.getValue("srchRef"));
     String pkt_ty=util.nvl((String)dis.getValue("pkt_ty"));
     HashMap dbinfo = info.getDmbsInfoLst();
     String cnt = (String)dbinfo.get("CNT");
     String[] disStt=sttstr.split(",");
     String mdl = "DIS_VIEW";
     ArrayList params = null;
     ArrayList ary = new ArrayList();     
     ArrayList pkttyLst=new ArrayList();
     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("DISCOVER");
     ArrayList pageList=new ArrayList();
     HashMap pageDtlMap=new HashMap();
     String fld_nme="",lov_qry="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";

     if(pkt_ty.equals("ALL")){
         pageList= ((ArrayList)pageDtl.get("PKT_TY") == null)?new ArrayList():(ArrayList)pageDtl.get("PKT_TY");
         if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         pageDtlMap=(HashMap)pageList.get(k);
         dflt_val=(String)pageDtlMap.get("dflt_val");
         if(!dflt_val.equals("ALL"))
             pkttyLst.add(dflt_val);
         }
         } 
     }else{
         pkttyLst.add(pkt_ty);
     }
    
     int pkttyLstsz=pkttyLst.size(); 
     if(vnm.equals("")){
     if(disStt.length>0){
     String stt=disStt[0].trim();
     stt=disStt[0];
     info.setGncPrpLst(genricSrchLst);
     HashMap prp = info.getPrp();
     HashMap mprp = info.getMprp();
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
     String reqVal1 = util.nvl((String)dis.getValue(lprp + "_" + lVal),"");
     if(!reqVal1.equals("")){
     paramsMap.put(lprp + "_" + lVal, reqVal1);
     }
     }
     }else{
     String fldVal1 = util.nvl((String)dis.getValue(lprp+"_1"));
     String fldVal2 = util.nvl((String)dis.getValue(lprp+"_2"));
     if(typ.equals("T")){
     fldVal1 = util.nvl((String)dis.getValue(lprp+"_1"));
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

     if(paramsMap.size()>0){
     paramsMap.put("stt", stt);
     paramsMap.put("mdl", mdl);
     int lSrchId=util.genericSrchEntries(paramsMap);
       String insrtAddon = " insert into srch_addon( srch_id , cprp , cval) "+
                  "select ? , ? , ?  from dual ";
         for(int st=0;st<disStt.length;st++){
             stt=disStt[st].trim();
             if(!stt.equals("")){
             params = new ArrayList();
             params.add(String.valueOf(lSrchId));
             params.add("STT");
             params.add(stt);
             int ct = db.execUpd("", insrtAddon, params);  
             }}
         if(dwldExcel.equals("") && termsRteExcel.equals("") && trftomktkgExcel.equals("")){
         String srch_pkg = "srch_pkg.DISCO_SRCH(pSrchId => ? ,pMdl  => ? ,pPktTy => ?,pDp => ?)";
         for(int p=0;p< pkttyLstsz;p++){
         ary = new ArrayList();
         ary.add(String.valueOf(lSrchId));
         ary.add(mdl);
         ary.add(pkttyLst.get(p));
         if(p==(pkttyLstsz-1))
         ary.add("Y");
         else
         ary.add("N");
         int ct = db.execCall("stk_srch", srch_pkg, ary);
         }
         }else{
             String mdlDirect="DIS_VIEW_DIRECT";
             if(!termsRteExcel.equals(""))
             mdlDirect="DIS_VIEW_TERMS";
             
             String srch_pkg = "DP_DISCO_SRCH(pSrchId => ?, pMdl => ?,pPktTy => ?,pDp => ?)";
             for(int p=0;p< pkttyLstsz;p++){
             ary = new ArrayList();
             ary.add(String.valueOf(lSrchId));
             ary.add(mdlDirect);
             ary.add(pkttyLst.get(p));
             if(p==(pkttyLstsz-1))
             ary.add("Y");
             else
             ary.add("N");
             int ct = db.execCall("stk_srch", srch_pkg, ary); 
             }
             if(!dwldExcel.equals(""))
             return createXLSTK(am,dis,req,res,mdlDirect);
         }
     }
     }
     }else{
         ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "DIS_VW","DIS_VIEW");
         int indexDIA1 = vwPrpLst.indexOf("DIA_MX")+1;
         int indexDIA2 = vwPrpLst.indexOf("DIA_MN")+1;
         String dia1Prp = util.prpsrtcolumn("prp",indexDIA1);
         String dia2Prp =  util.prpsrtcolumn("prp",indexDIA2);
//         if(!sttstr.equals("")){
//         sttstr=sttstr.replaceAll("\\,", "','");    
//         conQ = " and b.stt in ('"+sttstr+"')";        
//         }
         int ct=0;
         int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
         vnm = util.getVnm(vnm);
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
              srchStr = " ( b.vnm in ("+vnmSub+") or b.tfl3 in ("+vnmSub+") ) ";
          else if(srchTyp.equals("cert_no"))
              srchStr = " b.cert_no in ("+vnmSub+")";
         
        if(!srchStr.equals("")){
         String srchRefQ =
         "    Insert into gt_srch_rslt (srch_id,stk_idn , vnm  , flg , qty , cts , stt ,  cmp, rap_rte , quot , rap_dis ,cert_lab, sk1 , pkt_ty,pkt_dte,cert_no,rmk,prte,Img ) " +
         " select  0,b.idn, b.vnm,  'Z' , qty , cts , stt ,decode(nvl(upr,0), 0, cmp, upr) , rap_rte , decode(nvl(upr,0), 0, cmp, upr) , decode(b.rap_rte, 1, null, trunc((nvl(b.upr, b.cmp)/b.rap_rte*100)-100, 2)) upr_dis,cert_lab, sk1  "+
         "  ,pkt_ty,dte,cert_no,tfl3,b.fcpr,b.flg   from mstk b where "+srchStr;
         
         
          // ary.add(vnm);
          
          ct = db.execUpd(" Srch Prp ", srchRefQ, new ArrayList());
          }
         }
         
         if(termsRteExcel.equals("") && trftomktkgExcel.equals("")){
         String pktPrp = "DP_GT_PRP_UPD(pMdl => ?, pTyp => 'PRT')";
         ary = new ArrayList();
         ary.add(mdl);
         ct = db.execCall(" Srch Prp ", pktPrp, ary);
         if(cnt.equals("kj")){
         String upqQ="update gt_srch_rslt gt set "+dia2Prp+" = (select meas.num from stk_dtl meas where gt.stk_idn = meas.mstk_idn and meas.grp = 1 and meas.mprp = 'LN')\n" + 
         "where not exists (select 1 from stk_dtl sh where gt.stk_idn = sh.mstk_idn and sh.grp = 1 and sh.mprp = 'SH' and sh.val in ('RO', 'RD', 'ROUND', 'BR'))" ;  
         ct = db.execUpd(" Update dia2Prp ", upqQ, new ArrayList());
         upqQ="update gt_srch_rslt gt set "+dia1Prp+" = (select meas.num from stk_dtl meas where gt.stk_idn = meas.mstk_idn and meas.grp = 1 and meas.mprp = 'WD')\n" + 
         "where not exists (select 1 from stk_dtl sh where gt.stk_idn = sh.mstk_idn and sh.grp = 1 and sh.mprp = 'SH' and sh.val in ('RO', 'RD', 'ROUND', 'BR'))" ;  
         ct = db.execUpd(" Update dia2Prp ", upqQ, new ArrayList());            
         }
         }
         }
        
         if(!termsRteExcel.equals("")){
         int ct = db.execCall(" Srch Prp ", "DP_DISCO_SRCH_TERMS(pMdl =>'DIS_VIEW_TERMS')", new ArrayList());  
         return createtermsRteXLSTK(am,dis,req,res);   
         }
         
         if(!trftomktkgExcel.equals("")){
             return createXLSTK(am,dis,req,res,"DIS_TRF_MKT");   
         }

         dis.reset();
         if(cnt.equals("xljf")){
             String upqQ="Update Gt_Srch_Rslt A Set (A.ofr_rte)=\n" + 
             "              (Select Max(B.Ofr_Rte)\n" + 
             "              from web_bid_wl b where a.stk_idn = b.mstk_idn)" ;  
             int ct = db.execUpd(" Update dia2Prp ", upqQ, new ArrayList());
         }
             
             if(!dfr.equals("") && !dto.equals("")){
             String p1p2Q="DP_PRI_COMPARISON(pDte => to_date(?, 'dd-mm-rrrr'), pDte2 => to_date(?, 'dd-mm-rrrr'))";
             ary = new ArrayList();
             ary.add(dfr);
             ary.add(dto);
             int ct = db.execCall("DP_PRI_COMPARISON", p1p2Q, ary);
             }
             
             if(!applybyrte.equals("") || !applybydis.equals("")){
             String insertQ="insert into gt_pkt(mstk_idn) \n" + 
             "        select stk_idn from gt_srch_rslt ";
             
             int ct = db.execUpd(" insert into gt_pkt ", insertQ, new ArrayList());
             
             String apply="PKG_RAP.APPLY_ON_ALL(pTyp => 'RTE')";
             if(!applybydis.equals(""))
             apply="PKG_RAP.APPLY_ON_ALL(pTyp => 'DIS')";
             
             ct = db.execCall("APPLY_ON_ALL", apply, new ArrayList());
                 
             String updateFlg = "update gt_srch_rslt a set (a.rap_rte,a.rap_dis)=\n" + 
             "(select b.rap_rte, decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))\n" + 
             "from mstk b where a.stk_idn = b.idn) ";
             int cntrap = db.execUpd("update Raprte", updateFlg, new ArrayList());
             }
             
         ArrayList pktList = SearchResult(req, res,dfr,dto);
         HashMap totalMap = GetTotal(req);
         dis.setPktList(pktList);
         req.setAttribute("totalMap", totalMap);
         req.setAttribute("PktList", pktList);
         req.setAttribute("view", "Y");
         util.updAccessLog(req,res,"Discover Report", "fecth end");
     return am.findForward("load");
         }
     }


     public ArrayList SearchResult(HttpServletRequest req , HttpServletResponse res ,String dfr,String dto){
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
     int count = 0;
     String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
     ArrayList pktList = new ArrayList();
     ArrayList itemHdr = new ArrayList();
     ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "DIS_VW","DIS_VIEW");
     String conQ="";
     if(cnt.equals("xljf")){
         conQ=",trunc(prte*cts,2) cst_val,trunc(Quot*cts,2) net_vlu,ofr_rte,decode(ofr_rte ,'1',null,'0',null, trunc((ofr_rte/greatest(rap_rte,1)*100)-100, 2)) ofr_dis,img mstkflg";
     }
     itemHdr.add("sr");
     itemHdr.add("vnm");
     itemHdr.add("stt");
     if(cnt.equals("xljf"))
     itemHdr.add("flg");
     String srchQ = " select stk_idn , pkt_ty, vnm, pkt_dte,prte,mrte, stt , qty ,cmnt,cert_no,rmk tfl3, cts ,kts_vw kts, quot ,rap_rte,decode(rap_rte, 1, '', trunc(((nvl(quot,0)/greatest(rap_rte,1))*100)-100,2)) dis,trunc(quot*cts,2) total_val,trunc(rap_rte*cts,2) rap_val "+conQ;
     HashMap srchReckObsMap = new HashMap();


     for (int i = 0; i < vwPrpLst.size(); i++) {
     String lprp = (String)vwPrpLst.get(i);
     String fld = "prp_";
     int j = i + 1;
     if (j < 10)
     fld += "00" + j;
     else if (j < 100)
     fld += "0" + j;
     else if (j > 100)
     fld += j;



     srchQ += ", " + fld;
         if(lprp.equals("CERT NO."))
             lprp="CERT NO"; 
         itemHdr.add(lprp);
         if(cnt.equals("kj")){
         if(lprp.equals("PU")){
             itemHdr.add("quot"); 
             itemHdr.add("total_val"); 
         }
         if(lprp.equals("LOT_NO")){
             itemHdr.add("rap_val");  
         }
         }else{
         if(lprp.equals("RTE")){
             itemHdr.add("total_val");  
             if(!dfr.equals("") && !dto.equals("")){
                 itemHdr.add(dfr);
                 itemHdr.add(dto);
             }
         }
         if(lprp.equals("RAP_RTE")){
             itemHdr.add("rap_val");  
             if(cnt.equals("xljf")){
             itemHdr.add("cst_val");
             itemHdr.add("net_vlu");
             itemHdr.add("ofr_rte");
             itemHdr.add("ofr_dis");
             }
         }
         }
     }


     String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by sk1,cts";
     ArrayList ary = new ArrayList();
     ary.add("Z");
       ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
     try {
     while(rs.next()) {
     count++;
     String stkIdn = util.nvl(rs.getString("stk_idn"));
     String stt = util.nvl(rs.getString("stt"));


     HashMap pktPrpMap = new HashMap();
     pktPrpMap.put("stt", stt);  
     pktPrpMap.put("sr", String.valueOf(count));
     String vnm = util.nvl(rs.getString("vnm"));
     pktPrpMap.put("vnm",vnm);
     pktPrpMap.put("stk_idn", stkIdn);
     pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
     pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
     pktPrpMap.put("quot",util.nvl(rs.getString("quot")));
     pktPrpMap.put("total_val",util.nvl(rs.getString("total_val")));
     pktPrpMap.put("rap_val",util.nvl(rs.getString("rap_val")));
     pktPrpMap.put("CERT NO",util.nvl(rs.getString("cert_no")));
     pktPrpMap.put(util.nvl(dfr,"P1"),util.nvl(rs.getString("prte")));
     pktPrpMap.put(util.nvl(dto,"P2"),util.nvl(rs.getString("mrte")));
     if(cnt.equals("xljf")){
         pktPrpMap.put("cst_val",util.nvl(rs.getString("cst_val")));
         pktPrpMap.put("net_vlu",util.nvl(rs.getString("cst_val")));
         pktPrpMap.put("ofr_rte",util.nvl(rs.getString("ofr_rte")));
         pktPrpMap.put("ofr_dis",util.nvl(rs.getString("ofr_dis"))); 
         pktPrpMap.put("flg",util.nvl(rs.getString("mstkflg"))); 
     }
     srchReckObsMap.put(stkIdn, "");
         for (int j = 0; j < vwPrpLst.size(); j++) {
             String prp = (String)vwPrpLst.get(j);

             String fld = "prp_";
             if (j < 9)
                 fld = "prp_00" + (j + 1);
             else
                 fld = "prp_0" + (j + 1);

             String val = util.nvl(rs.getString(fld));
       
             if (prp.toUpperCase().equals("RAP_DIS"))
             {
                 val = util.nvl(rs.getString("dis"));
             }
             if(prp.equalsIgnoreCase("KTSVIEW"))
               val = util.nvl(rs.getString("kts"));
                 if(prp.equalsIgnoreCase("CRTWT"))
                   val = util.nvl(rs.getString("cts"));
               if(prp.equalsIgnoreCase("COMMENT"))
                 val = util.nvl(rs.getString("cmnt"));
       
             if (prp.toUpperCase().equals("RAP_RTE"))
                 val = util.nvl(rs.getString("rap_rte"));
             if (prp.toUpperCase().equals("RFIDCD"))
                 val = util.nvl(rs.getString("tfl3"));
             if(prp.toUpperCase().equals("RTE"))
                 val = util.nvl(rs.getString("quot"));
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
     req.setAttribute("itemHdr", itemHdr);
     return pktList;
     }
     
     public HashMap GetTotal(HttpServletRequest req){
         HttpSession session = req.getSession(false);
         InfoMgr info = (InfoMgr)session.getAttribute("info");
         DBUtil util = new DBUtil();
         DBMgr db = new DBMgr();
         db.setCon(info.getCon());
         util.setDb(db);
         util.setInfo(info);
         db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
         util.setLogApplNm(info.getLoApplNm());
     HashMap gtTotalMap = new HashMap();
     String gtTotal ="Select count(*) qty, sum(cts) cts,to_char(sum(cts*quot) , '9999999999999990.99') amt from gt_srch_rslt where flg = ?";
     ArrayList ary = new ArrayList();
     ary.add("Z");
       ArrayList outLst = db.execSqlLst("getTotal", gtTotal, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
     try {
     if (rs.next()) {
     gtTotalMap.put("qty", util.nvl(rs.getString("qty")));
     gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
     gtTotalMap.put("vlu", util.nvl(rs.getString("amt")));
     }
     rs.close();
      pst.close();
     } catch (SQLException sqle) {
     // TODO: Add catch code
     sqle.printStackTrace();
     }

     return gtTotalMap ;
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
     ExcelUtil xlUtil = new ExcelUtil();
     xlUtil.init(db, util, info);
     OutputStream out = res.getOutputStream();
     String CONTENT_TYPE = "getServletContext()/vnd-excel";
     String fileNm = "PacketDtls"+util.getToDteTime()+".xls";
     res.setContentType(CONTENT_TYPE);
     res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
     ArrayList pktList = (ArrayList)session.getAttribute("PktList");
     ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
     HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
     hwb.write(out);
     out.flush();
     out.close();
     return null;
     }
     }
     
     
     public ActionForward createtermsRteXLSTK(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
     util.updAccessLog(req,res,"Discover Report", "createXLSTK start");
     HashMap dbinfo = info.getDmbsInfoLst();
     String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
     int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100"));
     ExcelUtil xlUtil = new ExcelUtil();
     xlUtil.init(db, util, info);
     GenericInterface genericInt = new GenericImpl();
     ArrayList pktList = new ArrayList();
     ArrayList itemHdr = new ArrayList();
     ArrayList prpDspBlocked = info.getPageblockList();
     String rapDisFmt = util.nvl((String)info.getDmbsInfoLst().get("RAPDISFMT"));
     if(rapDisFmt.equals(""))
     rapDisFmt="90.00";
     ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "DIS_VIEW_TERMS","DIS_VIEW_TERMS");
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
     if(lprp.equals("RECPT_DT"))
         itemHdr.add("TERMS");
     if(lprp.equals("EST1"))
         itemHdr.add("TERMS_RTE");
     }
     }
     
         String  srchQ =  " select stk_idn,cts,vnm,quot,rmk,sk1";

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

         
         String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by sk1";
         
         ArrayList ary = new ArrayList();
         ary.add("Z");
       ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
         try {
             while(rs.next()) {
                 HashMap pktPrpMap = new HashMap();
                 pktPrpMap.put("TERMS",util.nvl(rs.getString("rmk")));
                 pktPrpMap.put("TERMS_RTE",util.nvl(rs.getString("quot")));
                 for(int j=0; j < vwPrpLst.size(); j++){
                      String prp = (String)vwPrpLst.get(j);
                       String fld="prp_";
                       if(j < 9)
                               fld="prp_00"+(j+1);
                       else    
                               fld="prp_0"+(j+1);
                       String val = util.nvl(rs.getString(fld)) ;
                       if(prp.equals("VNM"))
                       val=util.nvl(rs.getString("vnm"));
                       if(prp.equals("CRTWT"))
                       val=util.nvl(rs.getString("cts"));
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
     util.updAccessLog(req,res,"Discover Report", "createXLSTK end");
     return null;
     }
     }

        public ActionForward createXLSTK(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res,String mdl) throws Exception {
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
        util.updAccessLog(req,res,"Discover Report", "createXLSTK start");
        HashMap dbinfo = info.getDmbsInfoLst();
        String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
        int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100"));
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        GenericInterface genericInt = new GenericImpl();
        ArrayList pktList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        String rapDisFmt = util.nvl((String)info.getDmbsInfoLst().get("RAPDISFMT"));
        if(rapDisFmt.equals(""))
        rapDisFmt="90.00";
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, mdl,mdl);
        itemHdr.add("STATUS");
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
        String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||Upper(replace(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CR EX FACET','CREXFACET'),'PAV EX FACET','PAVEXFACET')) str From Rep_Prp Rp Where rp.MDL = ? order by srt " ;
        param = new ArrayList();
        param.add(mdl);
          ArrayList outLst = db.execSqlLst("mprp ", mdlPrpsQ , param);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()) {
        String val = util.nvl((String)rs.getString("str"));
        mprpStr = mprpStr +" "+val;
        }
        rs.close();
            pst.close();
        
        String rsltQ = " with " +
        " STKDTL as " +
        " ( select b.sk1, nvl(nvl(nvl(a.txt,a.dte),a.num),a.val) atr , mprp, b.vnm pktno, to_char(trunc(c.cts,2),'9999990.99') cts , c.rap_rte rapr , decode(c.rap_rte, 1, '', decode(least(c.rap_dis, 0),0, '+'||c.rap_dis, to_char(c.rap_dis, '"+rapDisFmt+"'))) rapd , trunc(c.cts,2)*c.rap_rte rap_vlu , to_char(trunc(c.cts,2) * c.quot , '99999999990.99') amt , c.quot rteq ,b.stt , b.certimg, b.diamondimg from stk_dtl a, mstk b , gt_srch_rslt c" +
        " Where 1=1 " +
        " and a.mstk_idn = b.idn and b.idn = c.stk_idn " +
        " and exists ( select 1 from rep_prp rp where rp.MDL = ? and a.mprp = rp.prp) " +
        " And a.Grp = 1 Order By B.Sk1 ) " +
        " Select * from stkDtl PIVOT " +
        " ( max(atr) " +
        " for mprp in ( "+mprpStr+" ) ) " ;
        
        param = new ArrayList();
        param.add(mdl);
           outLst = db.execSqlLst("search Result", rsltQ, param);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
        HashMap pktPrpMap = new HashMap();
        pktPrpMap.put("VNM", util.nvl((String)rs.getString("pktno")));
        pktPrpMap.put("STATUS", util.nvl((String)rs.getString("stt")));
        for (int i = 0; i < vwPrpLst.size(); i++) {
        String vwPrp = (String)vwPrpLst.get(i);
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
        
        if(vwPrp.toUpperCase().equals("STK-CTG"))
        fldName = "STK_CTG";
        
        if(vwPrp.toUpperCase().equals("CRN-OP"))
        fldName = "CRN_OP";
        
        if(vwPrp.toUpperCase().equals("CRTWT"))
        fldName = "CTS";
        
        if(vwPrp.toUpperCase().equals("RAP_DIS"))
        fldName = "rapd";
        
        if(vwPrp.toUpperCase().equals("RTE"))
        fldName = "rteq";
            
        if(vwPrp.toUpperCase().equals("VNM"))
            fldName = "pktno";
            if(vwPrp.toUpperCase().equals("CR EX FACET"))
                fldName = "CREXFACET";
            if(vwPrp.toUpperCase().equals("PAV EX FACET"))
                fldName = "PAVEXFACET";
        
        if (vwPrp.toUpperCase().equals("RAP_RTE"))
        fldName = "rapr";
            if(vwPrp.toUpperCase().equals("MEM_COMMENT"))
            fldName = "MEM_COM1";
        String fldVal = util.nvl((String)rs.getString(fldName));
        
        pktPrpMap.put(vwPrp, fldVal);
        }
        pktList.add(pktPrpMap);
        }
        rs.close();
            pst.close();
        int pktListsz=pktList.size();
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
        util.updAccessLog(req,res,"Discover Report", "createXLSTK end");
        return null;
        }
        }
     public ActionForward getStatusbyGrp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
         HttpSession session = req.getSession(false);
         InfoMgr info = (InfoMgr)session.getAttribute("info");
         DBUtil util = new DBUtil();
         DBMgr db = new DBMgr(); 
         db.setCon(info.getCon());
         util.setDb(db);
         util.setInfo(info);
         db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
         util.setLogApplNm(info.getLoApplNm());
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        ArrayList ary = new ArrayList();
       StringBuffer sb = new StringBuffer();
       String grp = util.nvl(req.getParameter("grp"));
       grp=util.getVnm(grp);
       String sqlQ="select stt  from df_stk_stt where grp1 in("+grp+") order by srt" ;
       ArrayList outLst = db.execSqlLst("df_stk_stt",sqlQ, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
         while(rs.next()){
             sb.append("<detail>");
             sb.append("<stt>"+util.nvl(rs.getString("stt")) +"</stt>");
             sb.append("</detail>");
         }
         rs.close();
         pst.close();
         res.getWriter().write("<details>" +sb.toString()+ "</details>");
         return null;
     }
    public DiscoverAction() {
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
                 util.updAccessLog(req,res,"Discover Report", "Unauthorized Access");
                 else
                 util.updAccessLog(req,res,"Discover Report", "init");
             }
             }
             return rtnPg;
             }
     
     public ActionForward FECTHMG(ActionMapping am, ActionForm form,

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
                  DiscoverReportForm dis=(DiscoverReportForm)form ;
                  GenericInterface genericInt = new GenericImpl();
                  ArrayList genricSrchLst =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DISGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DISGNCSrch"); 
                  info.setGncPrpLst(genricSrchLst);
                  HashMap prp = info.getPrp();
                  HashMap mprp = info.getMprp();
                  HashMap paramsMap = new HashMap();
                  HashMap dbinfo = info.getDmbsInfoLst();
                  String rep_path = util.nvl((String)dbinfo.get("REP_PATHNEW"),util.nvl((String)dbinfo.get("REP_PATH")));
                  String mongodb = (String)dbinfo.get("MONGODB");
                  String sttStr= util.nvl((String)dis.getValue("stt"));
                  String pkt_tyStr=util.nvl((String)dis.getValue("pkt_ty"));
                  String vnmStr = util.nvl((String)dis.getValue("vnm"));
                  String srchTyp =util.nvl((String)dis.getValue("srchRef"));
                  String lprpStr="";
                  String lprpVal="";
                  String lprpTyp="";
                  ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "DIS_VW","DIS_VIEW");
                  String dwldExcel=util.nvl((String)dis.getValue("dwldExcel"));
                  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                  HashMap pageDtl=(HashMap)allPageDtl.get("DISCOVER");
                  ArrayList pageList=new ArrayList();
                  HashMap pageDtlMap=new HashMap();
                  String fld_nme="",lov_qry="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
                  
                  if(pkt_tyStr.equals("ALL")){
                      pkt_tyStr="";
                      pageList= ((ArrayList)pageDtl.get("PKT_TY") == null)?new ArrayList():(ArrayList)pageDtl.get("PKT_TY");
                      if(pageList!=null && pageList.size() >0){
                      for(int k=0;k<pageList.size();k++){
                      pageDtlMap=(HashMap)pageList.get(k);
                      dflt_val=(String)pageDtlMap.get("dflt_val");
                      if(!dflt_val.equals("ALL")){
                          if(pkt_tyStr.equals(""))
                          pkt_tyStr=dflt_val;
                          else
                          pkt_tyStr+=","+dflt_val;
                      }
                      }
                      } 
                  }
                  
                  if(vnmStr.equals("")){
                          for(int i=0;i<genricSrchLst.size();i++){
                          ArrayList prplist =(ArrayList)genricSrchLst.get(i);
                          String lprp = (String)prplist.get(0);
                          String flg= (String)prplist.get(1);
                          String typ = util.nvl((String)mprp.get(lprp+"T"));
                          String prpSrt = lprp ;
                              String prpStr="";
                              String prpValStr="";
                         
                          if(flg.equals("M")) {
                          ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                          ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                       
                          for(int j=0; j < lprpS.size(); j++) {
                          String lSrt = (String)lprpS.get(j);
                          String lVal = (String)lprpV.get(j);
                          String reqVal1 = util.nvl((String)dis.getValue(lprp + "_" + lVal),"");
                          
                              if(!reqVal1.equals("")){
                                  String srtVal=(String)lprpS.get(lprpV.indexOf(reqVal1));
                                  if(prpValStr.equals(""))
                                      prpValStr= srtVal;
                                  else
                                    prpValStr= prpValStr+"@"+srtVal;
                                  if(prpStr.equals(""))
                                      prpStr=lprp;
                              }
                                 
                            
                          }

                         
                          }else{
                          String fldVal1 = util.nvl((String)dis.getValue(lprp+"_1"));
                          String fldVal2 = util.nvl((String)dis.getValue(lprp+"_2"));
                          if(typ.equals("T")){
                          fldVal1 = util.nvl((String)dis.getValue(lprp+"_1"));
                          fldVal2 = fldVal1;
                          }
                          if(typ.equals("D")){
                            fldVal1 = util.nvl((String)dis.getValue(lprp+"_1"));
                            fldVal2 = util.nvl((String)dis.getValue(lprp+"_2"));
                            if(!fldVal1.equals("") && !fldVal2.equals("")){
                                  String arr[] = fldVal1.split("-");
                                  fldVal1 = arr[2]+""+arr[1]+""+arr[0];
                                  String arr1[] = fldVal2.split("-");
                                  fldVal2 = arr1[2]+""+arr1[1]+""+arr1[0];
                                  System.out.println("fldValue"+fldVal1);
                            }
                          }
                                
                          if(fldVal2.equals(""))
                          fldVal2=fldVal1;
                         
                              if(!fldVal1.equals("") && !fldVal2.equals("")){
                                  prpValStr=fldVal1+"@"+fldVal2;
                                  if(prpStr.equals(""))
                                      prpStr=lprp;
                              }
                         
                          }
                              if(!prpStr.equals("")){
                              if(lprpVal.equals("")){
                                  lprpVal=prpValStr;
                                  lprpStr=prpStr;
                                  if(typ.equals("T"))
                                  lprpTyp="M";
                                  else
                                  lprpTyp=flg;
                              }else{
                                  lprpVal=lprpVal+"#"+prpValStr;
                                  lprpStr=lprpStr+"#"+prpStr;
                                  if(typ.equals("T"))
                                  lprpTyp=lprpTyp=lprpTyp+"#M";
                                  else
                                  lprpTyp=lprpTyp+"#"+flg;
                              }}
                              
                              
                          }
                  }else{
                      vnmStr = util.getVnm(vnmStr);
                      vnmStr = vnmStr.replaceAll("'", "");
                      lprpVal = vnmStr;
                      lprpStr = srchTyp;
                      lprpTyp = "M";
                      sttStr="";
                      System.out.println("lprpVal"+lprpVal);
                      
                  }
                  System.out.println(lprpTyp);
                  System.out.println(lprpStr);
                  System.out.println(lprpVal);
                  DefaultHttpClient httpClient = new DefaultHttpClient();
                  HttpPost postRequest = new HttpPost(rep_path+"/diaflexWebService/REST/WebService/searchValues");
                  postRequest.setHeader("Accept", "application/json");
                  postRequest.setHeader("Content-type", "application/json");
                  JSONObject jObj = new JSONObject();
                
                  try{
                           jObj.put("prpTyp", lprpTyp);
                           jObj.put("prp", lprpStr);
                           jObj.put("prpVal", lprpVal);
                           jObj.put("stt", sttStr);

                           jObj.put("mdl", "DIS_VIEW");
                           jObj.put("pkt_ty", pkt_tyStr);
                           jObj.put("clientName", mongodb);

                           

                                            
                  } catch (JSONException jsone) {
                  jsone.printStackTrace();
                  }
                               
                  StringEntity insetValue = new StringEntity(jObj.toString());
                  insetValue.setContentType(MediaType.APPLICATION_JSON);
                  postRequest.setEntity(insetValue);
                                                      
                  HttpResponse responsejson = httpClient.execute(postRequest);
                                                      
                  if (responsejson.getStatusLine().getStatusCode() !=200) {
                  throw new RuntimeException("Failed : HTTP error code : "
                                         + responsejson.getStatusLine().getStatusCode());
                  }
                                                      
                                                             
                  BufferedReader br = new BufferedReader(new InputStreamReader((responsejson.getEntity().getContent())));
                  String outsetValue="";
                  String jsonStr="";
                //System.out.println("OutsetValue from Server .... \n"+br.readLine());
                  while ((outsetValue = br.readLine()) != null) {
                                                     //    System.out.println(outsetValue);
                     jsonStr =jsonStr+outsetValue ;
                  }
                                             //    System.out.println(jsonStr);
                httpClient.getConnectionManager().shutdown();
                ArrayList pktList = new ArrayList();
               
                int count = 0;
                  if(!jsonStr.equals("")){
                  JSONObject  jObject = new JSONObject(jsonStr);
                  JSONArray  dataObject = (JSONArray)jObject.get("STKDTL");
                  if(dataObject!=null){
                      for (int i = 0; i < dataObject.length(); i++) {
                          count++;
                          JSONObject pktDtl = dataObject.getJSONObject(i);
                          String stkIdn = util.nvl((String)pktDtl.get("STK_IDN"));
                          String stt = util.nvl((String)pktDtl.get("STATUS"));
                          String cts = util.nvl((String)pktDtl.get("CRTWT"),"0.01");
                          String rap_rte = util.nvl((String)pktDtl.get("RAP_RTE"),"1");
                          String rte = util.nvl((String)pktDtl.get("RTE"),"1");
                          double amt = util.roundToDecimals(Double.parseDouble(cts)*Double.parseDouble(rte),2);
                          double rapVal = util.roundToDecimals(Double.parseDouble(cts)*Double.parseDouble(rap_rte),2);
                         
                          HashMap pktPrpMap = new HashMap();
                          pktPrpMap.put("stt", stt);  
                          pktPrpMap.put("sr", String.valueOf(count));
                          String vnm = util.nvl((String)pktDtl.get("PACKETCODE"));
                          pktPrpMap.put("vnm",vnm);
                          pktPrpMap.put("stk_idn", stkIdn);
                          pktPrpMap.put("qty",util.nvl((String)pktDtl.get("QTY")));
                          
                          pktPrpMap.put("total_val",String.valueOf(amt));
                          pktPrpMap.put("rap_val",String.valueOf(rapVal));
                        
                          for (int j = 0; j < vwPrpLst.size(); j++) {
                            try{
                                  String lprp = (String)vwPrpLst.get(j);
                                  String typ = util.nvl((String)mprp.get(lprp+"T"));
                                  String prpVal="";
                                  String fldVal = util.nvl((String)pktDtl.get(lprp));
                                  if(typ.equals("C")){
                                     
                                      ArrayList lprpS = (ArrayList)prp.get(lprp + "S");
                                      ArrayList lprpV = (ArrayList)prp.get(lprp + "V");
                                     
                                if(fldVal!=""){
                                                Double obj = new Double(fldVal);
                                                int prpSrt = obj.intValue();
                                                String prepSrtVal = String.valueOf(prpSrt);
                                                    try{
                                                    prpVal = (String)lprpV.get(lprpS.indexOf(prepSrtVal)); 
                                                    }catch(ArrayIndexOutOfBoundsException a){
                                                        prpVal=""; 
                                                    }
                                }
                                }else{
                                      prpVal = fldVal;
                                }
                              
                                      pktPrpMap.put(lprp, prpVal);
                            }catch(JSONException js){
                                
                            }
                        }
                              pktList.add(pktPrpMap);
                      }
                  }
                  }
                  
                  HashMap totalMap = GetTotalMG(pktList);
                  dis.setPktList(pktList);
                  req.setAttribute("totalMap", totalMap);
                  session.setAttribute("PktList", pktList);
                 
                  req.setAttribute("view", "Y");
                  util.updAccessLog(req,res,"Discover Report", "fecth end");
                  if(!dwldExcel.equals("")){
                      ArrayList itemList = new ArrayList();
                      itemList.add("sr"); itemList.add("vnm"); itemList.add("stt");itemList.add("qty");
                      for(int j=0; j < vwPrpLst.size(); j++ ){
                          String lprp = (String)vwPrpLst.get(j);
                          itemList.add(lprp);
                          if(lprp.equals("RTE"))
                            itemList.add("total_val");
                          if(lprp.equals("RAP_RTE"))
                            itemList.add("rap_val");
                      }
                    session.setAttribute("itemHdr", itemList);    
                    return  createXL(am, form, req, res);
                  }
                  return am.findForward("load");
              }
          
           }
     
     public HashMap GetTotalMG(ArrayList pktList){ 
        HashMap gtTotalMap = new HashMap();
        int ttlPcs = 0 ;
        double ttlcts = 0 ;
        double avgrte = 0 ;
         
        for(int i=0;i < pktList.size();i++){
            HashMap map  = (HashMap)pktList.get(i); 
            String qty = (String)map.get("qty");
            String curQty = nvl(qty,"0").trim();
           // ttlPcs = ttlPcs + (int)Integer.parseInt(curQty);
            ttlPcs++;
            String cts = (String)map.get("CRTWT");
            String curCts = nvl(cts,"0").trim();
            ttlcts = ttlcts + (double)Double.parseDouble(curCts);
            String quot = (String)map.get("RTE");
            String curQuot = nvl(quot,"0").trim();
            double ttlRte = ((double)Double.parseDouble(curQuot))*((double)Double.parseDouble(curCts));
            avgrte = avgrte + ttlRte;
        }
         BigDecimal currBigCts = new BigDecimal(ttlcts);
         BigDecimal currBigRte = new BigDecimal(avgrte);
         gtTotalMap.put("qty", String.valueOf(ttlPcs));
         gtTotalMap.put("cts", String.valueOf(currBigCts.setScale(2, RoundingMode.HALF_EVEN)));
         gtTotalMap.put("vlu", String.valueOf(currBigRte.setScale(2, RoundingMode.HALF_EVEN)));

     return gtTotalMap ;
     }
     
     public String nvl(String pVal, String rVal) {
         if(pVal == null)
             pVal = rVal;
         else if(pVal.equals(""))
           pVal = rVal;
         return pVal;
     }
     
 }

