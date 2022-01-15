package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;


import ft.com.JspUtil;
import ft.com.dao.Mprc;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.IOException;

import java.io.OutputStream;

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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MixDiscoverReportAction extends DispatchAction {

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
            util.updAccessLog(req,res,"Mix Discover Report", "load start");
        DiscoverReportForm dis=(DiscoverReportForm)form ;
        GenericInterface genericInt = new GenericImpl();
        dis.reset();
            ArrayList disSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DISGNCSrchMix") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DISGNCSrchMix");
        info.setGncPrpLst(disSrchList);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("DISCOVER_MIX");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("DISCOVER_MIX");
        allPageDtl.put("DISCOVER_MIX",pageDtl);
        }
        info.setPageDetails(allPageDtl);                   
            util.updAccessLog(req,res,"Mix Discover Report", "load end");
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
     util.updAccessLog(req,res,"Mix Discover Report", "fecth start");
     DiscoverReportForm dis=(DiscoverReportForm)form ;
     GenericInterface genericInt = new GenericImpl();
     int ctg =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_rslt", new ArrayList());
     int ctm =db.execUpd(" Del Old Pkts ", " Delete from gt_srch_multi", new ArrayList());
     String dwldExcel=util.nvl((String)dis.getValue("dwldExcel"));
     ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DISGNCSrchMix") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_DISGNCSrchMix");
     String dtefrom = "";
     String dteto = "";
     String dfr = util.nvl((String)dis.getValue("dtefr"));
     String dto = util.nvl((String)dis.getValue("dteto")); 
     String sttstr = util.nvl((String)dis.getValue("stt"));
     String vnm = util.nvl((String)dis.getValue("vnm"));
     String srchTyp =util.nvl((String)dis.getValue("srchRef"));
     HashMap dbinfo = info.getDmbsInfoLst();
     String cnt = (String)dbinfo.get("CNT");
     String[] disStt=sttstr.split(",");
     String mdl = "DIS_MIX_VIEW";
     ArrayList params = null;
     ArrayList ary = new ArrayList();
             if(!dfr.equals(""))
                 dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
                    
               if(!dto.equals(""))
                  dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";        
             
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

          if(stt.equals("MKSL") && !dfr.equals("") && !dto.equals("") ){
                String    insrtAddonQ ="  insert into srch_addon( srch_id , cprp ,frm_dte , to_dte ) "+
                                    "select ? ,'SALE' ,"+dtefrom+" ,"+dteto+" from dual ";
                        params = new ArrayList();
                        params.add(String.valueOf(lSrchId));
                    
                      ct = db.execUpd("", insrtAddonQ, params);  
//                       System.out.println("ct="+ct);
//                       System.out.println("ct="+insrtAddonQ);

                        }

             }}
         if(dwldExcel.equals("")){
         String srch_pkg = "DP_STK_STT_SRCH(?,?,?)";
         ary = new ArrayList();
         ary.add(String.valueOf(lSrchId));
         ary.add(mdl);
         ary.add("MIX");
         int ct = db.execCall("stk_srch", srch_pkg, ary);
         }else{
//             String srch_pkg = "DP_DISCO_SRCH(?)";
//             ary = new ArrayList();
//             ary.add(String.valueOf(lSrchId));
//             int ct = db.execCall("stk_srch", srch_pkg, ary); 
//             return createXLSTK(am,dis,req,res);
         }
     }
     }
     }else{
         ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "DIS_MIX_VW","DIS_MIX_VIEW");
         int indexDIA1 = vwPrpLst.indexOf("DIA_MX")+1;
         int indexDIA2 = vwPrpLst.indexOf("DIA_MN")+1;
         String dia1Prp = util.prpsrtcolumn("prp",indexDIA1);
         String dia2Prp =  util.prpsrtcolumn("prp",indexDIA2);
         String conQ="";
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
         
         
         String srchRefQ =
         "    Insert into gt_srch_rslt (srch_id,stk_idn , vnm  , flg , qty , cts , stt ,  cmp, rap_rte , quot , rap_dis ,cert_lab, sk1 , pkt_ty,pkt_dte,cert_no,rmk,prte,Img ) " +
         " select  0,b.idn, b.vnm,  'Z' , qty , cts , stt ,decode(nvl(upr,0), 0, cmp, upr) , rap_rte , decode(nvl(upr,0), 0, cmp, upr) , decode(b.rap_rte, 1, null, trunc((nvl(b.upr, b.cmp)/b.rap_rte*100)-100, 2)) upr_dis,cert_lab, sk1  "+
         "  ,pkt_ty,dte,cert_no,tfl3,b.fcpr,b.flg   from mstk b where pkt_ty='MIX' and "+srchStr+conQ;
         
         
          // ary.add(vnm);
          
          ct = db.execUpd(" Srch Prp ", srchRefQ, new ArrayList());
         
         }
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
         dis.reset();
         if(cnt.equals("xljf")){
             String upqQ="Update Gt_Srch_Rslt A Set (A.ofr_rte)=\n" + 
             "              (Select Max(B.Ofr_Rte)\n" + 
             "              from web_bid_wl b where a.stk_idn = b.mstk_idn)" ;  
             int ct = db.execUpd(" Update dia2Prp ", upqQ, new ArrayList());
         }
         ArrayList pktList = SearchResult(req, res);
         HashMap totalMap = GetTotal(req);
         dis.setPktList(pktList);
         req.setAttribute("totalMap", totalMap);
         req.setAttribute("PktList", pktList);
         req.setAttribute("view", "Y");
         util.updAccessLog(req,res,"Mix Discover Report", "fecth end");
     return am.findForward("load");
         }
     }


     public ArrayList SearchResult(HttpServletRequest req , HttpServletResponse res ){
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
     ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "DIS_MIX_VW","DIS_MIX_VIEW");
     String conQ="";
     if(cnt.equals("xljf")){
         conQ=",trunc(prte*cts,2) cst_val,trunc(Quot*cts,2) net_vlu,ofr_rte,decode(ofr_rte ,'1',null,'0',null, trunc((ofr_rte/greatest(rap_rte,1)*100)-100, 2)) ofr_dis,img mstkflg";
     }
     itemHdr.add("sr");
     itemHdr.add("vnm");
     itemHdr.add("stt");
     if(cnt.equals("xljf"))
     itemHdr.add("flg");
     String srchQ = " select stk_idn , pkt_ty, vnm, pkt_dte, stt , qty ,cmnt,cert_no,rmk tfl3, cts ,kts_vw kts, quot ,rap_rte,decode(rap_rte, 1, '', trunc(((nvl(quot,0)/greatest(rap_rte,1))*100)-100,2)) dis,trunc(quot*cts,2) total_val,trunc(rap_rte*cts,2) rap_val "+conQ;
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
//      if(cnt.equals("kj")){
//        if(lprp.equals("PU")){
//            itemHdr.add("quot"); 
//             itemHdr.add("total_val"); 
//         }
//         if(lprp.equals("LOT_NO")){
//             itemHdr.add("rap_val");  
//         }
//         }else{
//         if(lprp.equals("RTE")){
//             itemHdr.add("total_val");  
//         }

             if(cnt.equals("xljf")){
             itemHdr.add("cst_val");
             itemHdr.add("net_vlu");
             itemHdr.add("ofr_rte");
             itemHdr.add("ofr_dis");
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
   //  pktPrpMap.put("quot",util.nvl(rs.getString("quot")));
   //  pktPrpMap.put("total_val",util.nvl(rs.getString("total_val")));
   //  pktPrpMap.put("rap_val",util.nvl(rs.getString("rap_val")));
     pktPrpMap.put("CERT NO",util.nvl(rs.getString("cert_no")));
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
       
//             if (prp.toUpperCase().equals("RAP_DIS"))
//             {
//                 val = util.nvl(rs.getString("dis"));
//             }
             if(prp.equalsIgnoreCase("KTSVIEW"))
               val = util.nvl(rs.getString("kts"));
                 if(prp.equalsIgnoreCase("CRTWT"))
                   val = util.nvl(rs.getString("cts"));
               if(prp.equalsIgnoreCase("COMMENT"))
                 val = util.nvl(rs.getString("cmnt"));
       
//             if (prp.toUpperCase().equals("RAP_RTE"))
//                 val = util.nvl(rs.getString("rap_rte"));
             if (prp.toUpperCase().equals("RFIDCD"))
                 val = util.nvl(rs.getString("tfl3"));
//             if(prp.toUpperCase().equals("RTE"))
//                 val = util.nvl(rs.getString("quot"));
                 pktPrpMap.put(prp, val);
             }

     pktList.add(pktPrpMap);
     }

     rs.close(); pst.close();

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
     String gtTotal ="Select count(*) qty, sum(cts) cts from gt_srch_rslt where flg = ?";
     ArrayList ary = new ArrayList();
     ary.add("Z");

         ArrayList outLst = db.execSqlLst("getTotal", gtTotal, ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
     try {
     if (rs.next()) {
     gtTotalMap.put("qty", util.nvl(rs.getString("qty")));
     gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
     }
     rs.close(); pst.close();
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
           util.updAccessLog(req,res,"Mix Discover Report", "createXL start");
       ExcelUtil xlUtil = new ExcelUtil();
       xlUtil.init(db, util, info);
       OutputStream out = res.getOutputStream();
       String CONTENT_TYPE = "getServletContext()/vnd-excel";
       String fileNm = "WeeklyStockReportXL"+util.getToDteTime()+".xls";
       res.setContentType(CONTENT_TYPE);
       res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
       
       HSSFWorkbook hwb = xlUtil.getDataAllInXl("SRCH",req,"DIS_MIX_VIEW");
       hwb.write(out);
       out.flush();
       out.close();
           util.updAccessLog(req,res," Mix Discover Report", "createXL end");
      return null;
       }
   }

//        public ActionForward createXLSTK(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
//        HttpSession session = req.getSession(false);
//        InfoMgr info = (InfoMgr)session.getAttribute("info");
//        DBUtil util = new DBUtil();
//        DBMgr db = new DBMgr();
//        String rtnPg="sucess";
//        if(info!=null){
//        Connection conn=info.getCon();
//        if(conn!=null){
//        db.setCon(info.getCon());
//        util.setDb(db);
//        util.setInfo(info);
//        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//        util.setLogApplNm(info.getLoApplNm());
//        rtnPg=init(req,res,session,util);
//        }else{
//        rtnPg="connExists";
//        }
//        }else
//        rtnPg="sessionTO";
//        if(!rtnPg.equals("sucess")){
//        return am.findForward(rtnPg);
//        }else{
//        util.updAccessLog(req,res,"Discover Report", "createXLSTK start");
//        HashMap dbinfo = info.getDmbsInfoLst();
//        String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
//        int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100"));
//        ExcelUtil xlUtil = new ExcelUtil();
//        xlUtil.init(db, util, info);
//        GenericInterface genericInt = new GenericImpl();
//        ArrayList pktList = new ArrayList();
//        ArrayList itemHdr = new ArrayList();
//        ArrayList prpDspBlocked = info.getPageblockList();
//        String rapDisFmt = util.nvl((String)info.getDmbsInfoLst().get("RAPDISFMT"));
//        if(rapDisFmt.equals(""))
//        rapDisFmt="90.00";
//        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "DIS_MIX_VIEW_DIRECT","DIS_MIX_VIEW_DIRECT");
//        itemHdr.add("VNM");
//        itemHdr.add("STATUS");
//        for (int i = 0; i < vwPrpLst.size(); i++) {
//        String lprp=(String)vwPrpLst.get(i);
//        String fld = "prp_";
//        int j = i + 1;
//        if (j < 10)
//        fld += "00" + j;
//        else if (j < 100)
//        fld += "0" + j;
//        else if (j > 100)
//        fld += j;
//        if(prpDspBlocked.contains(lprp)){
//        }else{
//        itemHdr.add(lprp);
//        }}
//        
//        
//        ArrayList param = new ArrayList();
//        String mprpStr = "";
//        String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||Upper(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1')) str From Rep_Prp Rp Where rp.MDL = ? order by srt " ;
//        param = new ArrayList();
//        param.add("DIS_MIX_VIEW_DIRECT");
//        ResultSet rs = db.execSql("mprp ", mdlPrpsQ , param);
//        while(rs.next()) {
//        String val = util.nvl((String)rs.getString("str"));
//        mprpStr = mprpStr +" "+val;
//        }
//        rs.close(); pst.close();
//        
//        String rsltQ = " with " +
//        " STKDTL as " +
//        " ( select b.sk1, nvl(nvl(txt,num),val) atr , mprp, b.vnm , to_char(trunc(c.cts,2),'9999990.99') cts , c.rap_rte rapr , decode(c.rap_rte, 1, '', decode(least(c.rap_dis, 0),0, '+'||c.rap_dis, to_char(c.rap_dis, '"+rapDisFmt+"'))) rapd , trunc(c.cts,2)*c.rap_rte rap_vlu , to_char(trunc(c.cts,2) * c.quot , '99999999990.99') amt , c.quot rteq ,b.stt , b.certimg, b.diamondimg from stk_dtl a, mstk b , gt_srch_rslt c" +
//        " Where 1=1 " +
//        " and a.mstk_idn = b.idn and b.idn = c.stk_idn " +
//        " and exists ( select 1 from rep_prp rp where rp.MDL = ? and a.mprp = rp.prp) " +
//        " And a.Grp = 1 Order By B.Sk1 ) " +
//        " Select * from stkDtl PIVOT " +
//        " ( max(atr) " +
//        " for mprp in ( "+mprpStr+" ) ) " ;
//        
//        param = new ArrayList();
//        param.add("DIS_MIX_VIEW_DIRECT");
//        rs = db.execSql("search Result", rsltQ, param);
//        while (rs.next()) {
//        HashMap pktPrpMap = new HashMap();
//        pktPrpMap.put("VNM", util.nvl((String)rs.getString("vnm")));
//        pktPrpMap.put("STATUS", util.nvl((String)rs.getString("stt")));
//        for (int i = 0; i < vwPrpLst.size(); i++) {
//        String vwPrp = (String)vwPrpLst.get(i);
//        String fldName = vwPrp;
//        if(vwPrp.toUpperCase().equals("H&A"))
//        fldName = "H_A";
//        
//        if(vwPrp.toUpperCase().equals("COMMENT"))
//        fldName = "COM1";
//        
//        if(vwPrp.toUpperCase().equals("REMARKS"))
//        fldName = "REM1";
//        
//        if(vwPrp.toUpperCase().equals("COL-DESC"))
//        fldName = "COL_DESC";
//        
//        if(vwPrp.toUpperCase().equals("COL-SHADE"))
//        fldName = "COL_SHADE";
//        
//        if(vwPrp.toUpperCase().equals("FL-SHADE"))
//        fldName = "FL_SHADE";
//        
//        if(vwPrp.toUpperCase().equals("STK-CTG"))
//        fldName = "STK_CTG";
//        
//        if(vwPrp.toUpperCase().equals("STK-CODE"))
//        fldName = "STK_CODE";
//        
//        if(vwPrp.toUpperCase().equals("SUBSIZE"))
//        fldName = "SUBSIZE1";
//        
//        if(vwPrp.toUpperCase().equals("SIZE"))
//        fldName = "SIZE1";
//        
//        if(vwPrp.toUpperCase().equals("MIX_SIZE"))
//        fldName = "MIX_SIZE1";
//        
//        if(vwPrp.toUpperCase().equals("STK-CTG"))
//        fldName = "STK_CTG";
//        
//        if(vwPrp.toUpperCase().equals("CRN-OP"))
//        fldName = "CRN_OP";
//        
//        if(vwPrp.toUpperCase().equals("CRTWT"))
//        fldName = "CTS";
//        
//        if(vwPrp.toUpperCase().equals("RAP_DIS"))
//        fldName = "rapd";
//        
//        if(vwPrp.toUpperCase().equals("RTE"))
//        fldName = "rteq";
//        
//        if (vwPrp.toUpperCase().equals("RAP_RTE"))
//        fldName = "rapr";
//        
//        String fldVal = util.nvl((String)rs.getString(fldName));
//        
//        pktPrpMap.put(vwPrp, fldVal);
//        }
//        pktList.add(pktPrpMap);
//        }
//        rs.close(); pst.close();
//        int pktListsz=pktList.size();
//        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
//            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
//            String contentTypezip = "application/zip";
//            String fileNmzip = "ResultExcel"+util.getToDteTime();
//            OutputStream outstm = res.getOutputStream();
//            ZipOutputStream zipOut = new ZipOutputStream(outstm);
//            ZipEntry entry = new ZipEntry(fileNmzip+".xls");
//            zipOut.putNextEntry(entry);
//            res.setHeader("Content-Disposition","attachment; filename="+fileNmzip+".zip");
//            res.setContentType(contentTypezip);
//            hwb.write(zipOut);          
//            zipOut.closeEntry();
//            outstm.flush();
//            outstm.close(); 
//            zipOut.flush();
//            zipOut.close(); 
//            }else{
//            OutputStream out = res.getOutputStream();
//            String CONTENT_TYPE = "getServletContext()/vnd-excel";
//            String fileNm = "ResultExcel"+util.getToDteTime()+".xls";
//            res.setContentType(CONTENT_TYPE);
//            res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
//            hwb.write(out);
//            out.flush();
//            out.close();
//            }
//        util.updAccessLog(req,res,"Discover Report", "createXLSTK end");
//        return null;
//        }
//        }

    public MixDiscoverReportAction() {
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
                 util.updAccessLog(req,res,"Mix Discover Report", "Unauthorized Access");
                 else
                 util.updAccessLog(req,res,"Mix Discover Report", "init");
             }
             }
             return rtnPg;
             }
 }

