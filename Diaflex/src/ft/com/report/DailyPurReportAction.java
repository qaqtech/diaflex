package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.mpur.PurchasePricingForm;

import java.io.IOException;

import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

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


public class DailyPurReportAction extends DispatchAction {
    public ActionForward load(ActionMapping am, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg="create temp table if not exists temp_stock_search_pkts as \n" + 
        "select pkt_idn,pkt_code,stock_type,status,'' dsp_stt,'' dsp_stt_color,qty_on_hand,weight_on_hand,benchmark_rte,asking_rte,sort\n" + 
        "from stock_m\n" + 
        "where";
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
        util.getOpenCursorConnection(db,util,info);
        util.updAccessLog(req,res,"Daily Purchase Report", "load start");
        DailyPurReportForm udf = (DailyPurReportForm)form;
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("DAILY_PUR");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("DAILY_PUR");
            allPageDtl.put("DAILY_PUR",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            ArrayList pageDtlList = (ArrayList)pageDtl.get("ADDCOL");
            String fld_nme ="";String  fld_ttl ="";String  dflt_val ="";
            ArrayList colName = new ArrayList();
            ArrayList colTtl = new ArrayList();
            ArrayList gftVal = new ArrayList();
            if(pageDtlList != null && pageDtlList.size() > 0 ){
                for(int i=0;i<pageDtlList.size();i++){
                    HashMap pageDteailMap = (HashMap)pageDtlList.get(i);
                        fld_nme = (String)pageDteailMap.get("fld_nme");
                        fld_ttl = (String)pageDteailMap.get("fld_ttl");
                        dflt_val = (String)pageDteailMap.get("dflt_val");
                        colName.add(fld_nme);
                        gftVal.add(dflt_val);
                        colTtl.add(fld_ttl);
                    }
                }
            boolean isTrems = false;
             pageDtlList = (ArrayList)pageDtl.get("TERMS");
            if(pageDtlList != null && pageDtlList.size() > 0 )
                isTrems=true;
                
            String  colSName  = "";
            String  gftSVal  = "";
            
            if(colName != null && colName.size() > 0){
             colSName = colName.toString();
               colSName= colSName.replace("[", "");
                colSName= colSName.replace("]", "");
                colSName= ","+colSName;
            }
            
            if(gftVal != null && gftVal.size() > 0){ 
                   gftSVal = gftVal.toString();
                gftSVal= gftSVal.replace("[", "");
                gftSVal= gftSVal.replace("]", "");
                gftSVal= ","+gftSVal;
            }
            
        ResultSet rs = null;
        ArrayList ary = new ArrayList();
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        String dtefrom = " trunc(sysdate) ";
        String dteto = " trunc(sysdate) ";
        String flg=util.nvl(req.getParameter("flg"));
        if(flg.equals("New")){
            udf.reset();
        }
        String dfr = util.nvl((String)udf.getValue("dtefr"));
        String dto = util.nvl((String)udf.getValue("dteto"));
        String nmeID = util.nvl((String)req.getParameter("nmeID"));
        String spersonId = util.nvl((String)udf.getValue("styp"));
        String loc = util.nvl((String)udf.getValue("loc"));
        String pkt_ty = util.nvl((String)udf.getValue("pkt_ty")).trim();
        if(!dfr.equals(""))
          dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
        if(!dto.equals(""))
          dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
            String srchRefQ = "Insert into gt_srch_rslt (srch_id,rln_idn, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, dsp_stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis,fquot,byr,exh_rte,mrte,ofr_dis "+gftSVal+")\n" + 
            "select a.pur_idn,a.vndr_idn, 'NR', b.pur_dtl_idn, b.ref_idn, nvl(qty,1) qtyval,b.cts, a.pur_dte\n" + 
            ", a.typ stt, decode(a.typ, 'R', 'Review', 'O', 'Offer', 'B', 'Buy', 'ON', 'On Approval', a.typ)\n" + 
            ", b.cmp, b.rap, 'NA', null, 'Z' flg, null, b.rte, decode(b.rap,1,null,b.dis),nvl(b.ofr_rte, b.rte),decode(a.flg2,'1','Mix Party',c.nme) nme,nvl(a.exh_rte,1),\n" + 
            "  ( nvl(a.aadat_comm, 0) + nvl(a.mbrk1_comm, 0) + nvl(a.mbrk1_comm, 0) + nvl(a.mbrk1_comm, 0) + nvl(a.mbrk1_comm, 0)), nvl(a.aadat_comm, 0)  "+colSName+" \n"+              
            "From Mpur a, Pur_Dtl b,nme_v c  \n" + 
            "Where\n" + 
            "a.vndr_idn=c.nme_idn and b.Pur_Idn = a.Pur_Idn \n" + 
            "and b.flg='TRF' and nvl(a.flg2, 'NA') <> 'DEL' and trunc(a.pur_dte) between "+dtefrom+" and "+dteto+"";
            
            if(isTrems){
        srchRefQ = "Insert into gt_srch_rslt (srch_id,rln_idn, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, dsp_stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis,fquot,byr,exh_rte,mrte,ofr_dis,cmnt"+gftSVal+")\n" + 
        "select a.pur_idn,a.vndr_idn, 'NR', b.pur_dtl_idn, b.ref_idn, nvl(qty,1) qtyval,b.cts, a.pur_dte\n" + 
        ", a.typ stt, decode(a.typ, 'R', 'Review', 'O', 'Offer', 'B', 'Buy', 'ON', 'On Approval', a.typ)\n" + 
        ", b.cmp, b.rap, 'NA', null, 'Z' flg, null, b.rte, decode(b.rap,1,null,b.dis),nvl(b.ofr_rte, b.rte),decode(a.flg2,'1','Mix Party',c.nme) nme,nvl(a.exh_rte,1),\n" + 
        "  ( nvl(a.aadat_comm, 0) + nvl(a.mbrk1_comm, 0) + nvl(a.mbrk1_comm, 0) + nvl(a.mbrk1_comm, 0) + nvl(a.mbrk1_comm, 0)), nvl(a.aadat_comm, 0) , t.term "+colSName+" \n"+              
        "From Mpur a, Pur_Dtl b,nme_v c , mtrms t \n" + 
        "Where\n" + 
        "a.vndr_idn=c.nme_idn and b.Pur_Idn = a.Pur_Idn and a.trms_idn = t.idn \n" + 
        "and b.flg='TRF' and nvl(a.flg2, 'NA') <> 'DEL' and trunc(a.pur_dte) between "+dtefrom+" and "+dteto+"";
            }else{
                
                
            }
            
        if(!nmeID.equals("")){
            srchRefQ+=" and a.vndr_idn=? ";
            ary.add(nmeID);     
        }
        if(!spersonId.equals("") && !spersonId.equals("0")){
                srchRefQ+=" and a.emp_idn=? ";
                ary.add(spersonId);     
        }
        if(!pkt_ty.equals("")) 
                 srchRefQ+=" and nvl(a.flg,'S')='"+pkt_ty+"' ";
            if(!loc.equals("")){
                srchRefQ+=" and nvl(b.loc,'NA')=? ";
                ary.add(loc);  
            }
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "PUR_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add("DAILYPUR_VW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        ArrayList pktList = SearchResult(req,res,udf,colName,gftVal);
        HashMap totalMap = GetTotal(req);
        req.setAttribute("totalMap", totalMap);
        req.setAttribute("pktList", pktList);
        req.setAttribute("view", "Y");
            req.setAttribute("colNmeLst", colName);
            req.setAttribute("colTtlList", colTtl);
        util.saleperson();
        util.updAccessLog(req,res,"Daily Purchase Report", "load end"); 
        return am.findForward("load");
        }
    }
    
    public ArrayList SearchResult(HttpServletRequest req,HttpServletResponse res,ActionForm af,ArrayList colName,ArrayList gftVal){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        DailyPurReportForm udf = (DailyPurReportForm) af;
        ArrayList venderidnList = new ArrayList();
        HashMap venderDtl = new HashMap();
        ArrayList pktList = new ArrayList();
        HashMap dataDtl = new HashMap();
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst = genericInt.genericPrprVw(req,res,"DailyPurViewLst","DAILYPUR_VW");
        String gftSVal = "";
        String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
        if(gftVal != null && gftVal.size() > 0){ 
               gftSVal = gftVal.toString();
            gftSVal= gftSVal.replace("[", "");
            gftSVal= gftSVal.replace("]", "");
            gftSVal=" ,"+gftSVal;
        }
        try {
            String cpPrp = "quot";
            if(vwPrpLst.contains("CP") && cnt.equals("ri"))
            cpPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("CP")+1);
        String  srchQ =  " select srch_id,byr,rln_idn vndr_idn,stk_idn,rap_rte,exh_rte, pkt_ty,  vnm,to_char(pkt_dte,'dd-mm-yyyy') dte, stt , qty ,to_char(cts,'9999999999990.99') cts , cmp ,cmnt, quot , rmk ,mrte,  decode(rap_rte, 1, '', trunc(((cmp/greatest(rap_rte,1))*100)-100,2)) cmpdis,decode(rap_rte, 1, '', trunc((("+cpPrp+"/greatest(rap_rte,1))*100)-100,2)) cpdis , decode(rap_rte, 1, '', trunc(((fquot/greatest(rap_rte,1))*100)-100,2)) dis , fquot "+
            ",Trunc(Quot*Cts,2) vlu,Trunc("+cpPrp+"*Cts,2) cpvlu,decode(rap_rte, 1, '', trunc(((prte/greatest(rap_rte,1))*100)-100,2)) netdis,Trunc(prte*Cts,2) netAmt,Trunc(((prte*Cts)*nvl(exh_rte,1)),2) netAmtINR ,ofr_dis purdis, Trunc(((Quot*Cts)*nvl(exh_rte,1)),2) exhvlu ";
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
              

       String rsltQ = srchQ + gftSVal+" from gt_srch_rslt where flg =? order by byr,sk1 , cts ";
        ArrayList ary = new ArrayList();
        ary.add("Z");
            System.out.println("rsltQ"+rsltQ);   

  
            ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()) {
               String vndr_idn = util.nvl(rs.getString("vndr_idn"));
               if (!venderidnList.contains(vndr_idn)) {
                        venderidnList.add(vndr_idn);
                        venderDtl.put(vndr_idn, util.nvl(rs.getString("byr")));
                        pktList = new ArrayList();
                }
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                long   pktIdn = rs.getLong("stk_idn");
                pktPrpMap.put("vnm",vnm);
                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                pktPrpMap.put("pur_idn",util.nvl(rs.getString("srch_id")));
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                    pktPrpMap.put("terms",util.nvl(rs.getString("cmnt")));
                    pktPrpMap.put("Vendor",util.nvl(rs.getString("byr")));
                    pktPrpMap.put("vndridn",util.nvl(rs.getString("vndr_idn")));
                    pktPrpMap.put("cmp",util.nvl(rs.getString("cmp")));
                    pktPrpMap.put("rte",util.nvl(rs.getString("quot")));
                    pktPrpMap.put("cmpDis",util.nvl(rs.getString("cmpdis")));
                    pktPrpMap.put("dis",util.nvl(rs.getString("dis")));
                    pktPrpMap.put("vlu",util.nvl(rs.getString("vlu")));
                    pktPrpMap.put("exh_vlu",util.nvl(rs.getString("exhvlu")));
                    pktPrpMap.put("exh_rte",util.nvl(rs.getString("exh_rte")));
                  pktPrpMap.put("purdte",util.nvl(rs.getString("dte")));
                    pktPrpMap.put("addComm",util.nvl(rs.getString("mrte")));
                    pktPrpMap.put("purdis",util.nvl(rs.getString("purdis")));
                if(gftVal!=null && gftVal.size()>0){
                    for(int j=0; j < gftVal.size(); j++){
                         String gtcolNme = (String)gftVal.get(j);
                        String keyNme = (String)colName.get(j);
                        pktPrpMap.put(keyNme,util.nvl(rs.getString(gtcolNme)));
                    }
                    
                }
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                      if(prp.equals("CP_DIS"))
                            val = util.nvl(rs.getString("cpdis"));
                     
                        if(prp.equals("RAP_RTE"))
                        val = util.nvl(rs.getString("rap_rte"));
                    if(prp.equals("NET_PUR_DIS"))
                             val = util.nvl(rs.getString("netdis"));
                    if(prp.equals("NET_PUR_RTE")){
                      
                        pktPrpMap.put("netAmt", util.nvl(rs.getString("netAmt")));
                        pktPrpMap.put("netvluInr", util.nvl(rs.getString("netAmtINR")));
                    }
                    if(prp.equals("CP_VLU"))
                    val = util.nvl(rs.getString("cpvlu"));   
                    
                        pktPrpMap.put(prp, val);
                         }
                              
                    pktList.add(pktPrpMap);
                    dataDtl.put(vndr_idn, pktList);
                }
            rs.close(); pst.close();
        
        String ttlQ =
            "select Rln_Idn byr_idn,to_char(sum(cts),'9999999999990.99') cts,sum(qty) qty,to_char(sum(Trunc(Quot*Cts,2)),'9999999999990.00') vlu,to_char(sum(Trunc((Quot*Cts)*nvl(exh_rte,1),2)),'9999999999990.00') exhvlu from gt_srch_rslt group by Rln_Idn";
            System.out.println("ttlQ"+ttlQ);
        rs = db.execSql("Totals", ttlQ, new ArrayList());
        while (rs.next()) {
            String byr_idn = util.nvl(rs.getString("byr_idn"));
            HashMap ttlDtl = new HashMap();
            ttlDtl.put("cts", util.nvl(rs.getString("cts")));
            ttlDtl.put("vlu", util.nvl(rs.getString("vlu")));
            ttlDtl.put("exh_vlu", util.nvl(rs.getString("exhvlu")));
            ttlDtl.put("qty", util.nvl(rs.getString("qty")));
            dataDtl.put(byr_idn + "_TTL", ttlDtl);
        }
        rs.close(); pst.close();
        ttlQ =
        "select to_char(sum(cts),'9999999999990.99') cts,sum(qty) qty,to_char(sum(Quot*Cts),'9999999999990.00') vlu,to_char(sum(Trunc((Quot*Cts)*nvl(exh_rte,1),2)),'9999999999990.00') exhvlu from gt_srch_rslt where flg= ?  ";
            System.out.println("Grand Totals"+ttlQ);
        rs = db.execSql("Grand Totals", ttlQ, ary);
        while (rs.next()) {
            HashMap ttlDtl = new HashMap();
            ttlDtl.put("Vendor", "Grand Total");
            ttlDtl.put("cts", util.nvl(rs.getString("cts")));
            ttlDtl.put("vlu", util.nvl(rs.getString("vlu")));
            ttlDtl.put("exh_vlu", util.nvl(rs.getString("exhvlu")));
            ttlDtl.put("qty", util.nvl(rs.getString("qty")));
            dataDtl.put("GRANDTTL", ttlDtl);
        }
        rs.close(); pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        req.setAttribute("dataDtl", dataDtl);
        req.setAttribute("venderDtl", venderDtl);
        req.setAttribute("venderidnList", venderidnList);
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
            util.updAccessLog(req,res,"Daily Purchase Report", "createXL start");
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "dailyPurchase"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Daily Purchase Report", "createXL end");
        return null;
        }
    }
    
    public ActionForward createPurchaseOrderXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Daily Purchase Report", "createPurchaseOrderXL start");
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "purchaseorder"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        String purIdn = util.nvl(req.getParameter("purId"));
        ArrayList ary = new ArrayList();
        HashMap ttlDtl = new HashMap();
        ArrayList pktDtlList = new ArrayList();
        HashMap pktDtl = new HashMap();
        ArrayList itemHdr = new ArrayList();
        String cmyName = util.nvl((String)info.getDmbsInfoLst().get("SENDERNM"));
        ttlDtl.put("cmyName", cmyName);
        String vendor_idn="";
            String purdis="",paymentInt="";
        int ct=0;
        String sqlQ ="select a.avg_rte,a.avg_dis, a.pur_idn,byr.get_nm(a.vndr_idn) byr,byr.get_nm(a.mbrk1_idn) brk,to_char(a.dte,'DD-MM-YYYY') dte,a.vndr_idn, b.term\n" + 
        "from mpur a,mtrms b where nvl(a.trms_idn,133) = b.idn and a.pur_idn=?";
        ary = new ArrayList();
        ary.add(purIdn); 

            ArrayList outLst = db.execSqlLst("Get Contact Details", sqlQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                ttlDtl.put("byr", util.nvl(rs.getString("byr")));
                ttlDtl.put("dte", util.nvl(rs.getString("dte")));
                ttlDtl.put("term", util.nvl(rs.getString("term")));
                ttlDtl.put("brk", util.nvl(rs.getString("brk")));
                purdis= util.nvl(rs.getString("avg_dis"),"0");
               paymentInt= util.nvl(rs.getString("avg_rte"),"0");
                vendor_idn=util.nvl(rs.getString("vndr_idn"));
                ttlDtl.put("vndr_idn", vendor_idn);
            }
       rs.close(); pst.close();
            
       ttlDtl.put("pur", purIdn);
       ArrayList addarry = new ArrayList();
        sqlQ = "select mn.nme_idn,a.addr_idn , unt_num, bldg ||''|| street bldg , landmark ||''|| area landMk , b.city_nm||' '|| c.country_nm||' '|| a.zip addr \n" + 
        "from mnme mn,maddr a, mcity b, mcountry c \n" + 
        "where mn.nme_idn=a.nme_idn and a.city_idn = b.city_idn and b.country_idn = c.country_idn and mn.nme_idn = ? \n" + 
        "order by a.dflt_yn desc";
        addarry.add(vendor_idn); 
        ary = new ArrayList();
        ary.add(purIdn);  
        outLst = db.execSqlLst("Addr List", sqlQ, ary);
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                ttlDtl.put("nme_idn", util.nvl(rs.getString("nme_idn")));
                ttlDtl.put("addr_idn", util.nvl(rs.getString("addr_idn")));
                ttlDtl.put("unt_num", util.nvl(rs.getString("unt_num")));
                ttlDtl.put("bldg", util.nvl(rs.getString("bldg")));
                ttlDtl.put("landmk", util.nvl(rs.getString("landmk")));
                ttlDtl.put("addr", util.nvl(rs.getString("addr")));
            }
            rs.close(); pst.close(); 
            
            
        ttlDtl.put("Broker", "");
        ttlDtl.put("PurchaseOIDN", "");
         
            sqlQ = "select 'polish diamond' dsc,b.cts,Trunc(((b.rte)*nvl(a.exh_rte,1)),2) rteINR,Trunc(((b.rte*b.Cts)*nvl(a.exh_rte,1)),2) amtINR\n" + 
            "From Mpur a, Pur_Dtl b\n" + 
            "Where \n" + 
            "b.Pur_Idn = a.Pur_Idn\n" + 
            "and b.flg='TRF' and nvl(a.flg2, 'NA') <> 'DEL' and a.pur_idn=?";
            ary = new ArrayList();
            ary.add(purIdn);  
                rs = db.execSql("packet List", sqlQ, ary);
                while (rs.next()) {
                    pktDtl = new HashMap();
                    pktDtl.put("Sr. No.", String.valueOf(++ct));
                    pktDtl.put("Description", util.nvl(rs.getString("dsc")));
                    pktDtl.put("Carat", util.nvl(rs.getString("cts")));
                    pktDtl.put("Rate INR", util.nvl(rs.getString("rteINR")));
                    pktDtl.put("Amount INR", util.nvl(rs.getString("amtINR")));
                    pktDtlList.add(pktDtl);
                }
                rs.close(); pst.close(); 
            String ttlcts="";
            String amt="";
            sqlQ = "select to_char(sum(b.cts),'9999999999990.99') cts,\n" + 
            "to_char(sum(Trunc((b.rte*b.cts)*nvl(a.exh_rte,1),2)),'9999999999990.00') amtINR \n" + 
            "from Mpur a, Pur_Dtl b \n" + 
            "Where \n" + 
            "b.Pur_Idn = a.Pur_Idn\n" + 
            "and b.flg='TRF' and nvl(a.flg2, 'NA') <> 'DEL' and a.pur_idn=?";
            ary = new ArrayList();
            ary.add(purIdn);   
            rs = db.execSql("Totals", sqlQ, ary);
            while (rs.next()) {
                ttlcts = util.nvl(rs.getString("cts"));
               amt =  util.nvl(rs.getString("amtINR"));
             }
             rs.close(); pst.close(); 
            
            pktDtl = new HashMap();
            pktDtl.put("Description", "Purchase Discount INR");
            pktDtl.put("Amount INR", purdis);
            pktDtlList.add(pktDtl);
            
            pktDtl = new HashMap();
            pktDtl.put("Description", "Payment Interest INR");
            pktDtl.put("Amount INR", paymentInt);
            pktDtlList.add(pktDtl);
            if(!ttlcts.equals("") && !amt.equals("")){
             double ttlAmt = Double.parseDouble(amt)+Double.parseDouble(purdis)+ Double.parseDouble(paymentInt);
            String calAmmout = String.valueOf(ttlAmt);
            
            pktDtl = new HashMap();
            pktDtl.put("Description", "Total");
            pktDtl.put("Carat", ttlcts);
            pktDtl.put("Amount INR", calAmmout);
            pktDtlList.add(pktDtl);
            }
            
            
        itemHdr.add("Sr. No.");
        itemHdr.add("Description");
        itemHdr.add("Carat");
        itemHdr.add("Rate INR");
        itemHdr.add("Amount INR");
        HSSFWorkbook hwb = xlUtil.getPurOrdGenXl(ttlDtl,itemHdr, pktDtlList);
        hwb.write(out);
        out.flush();
        out.close();
            util.updAccessLog(req,res,"Daily Purchase Report", "createPurchaseOrderXL end");
        return null;
        }
    }
    
    public ActionForward createPurchaseReportXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Daily Purchase Report", "createPurchaseOrderXL start");
            GenericInterface genericInt = new GenericImpl();
            ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"DAILY_VW_CD","DAILY_VW_CD");
            ArrayList pktDtlList = new ArrayList();
            ArrayList itemHdr = new ArrayList();
            ArrayList ary = new ArrayList();
            HashMap pktDtl = new HashMap();
            ArrayList prpDspBlocked = info.getPageblockList();
            String purIdn = util.nvl(req.getParameter("purId"));
            itemHdr.add("PARTY NAME");
            itemHdr.add("PACK");
            itemHdr.add("CTS");
            itemHdr.add("CTS ");
            itemHdr.add("RATE(RS)");
            itemHdr.add("RATE($)");
            itemHdr.add("A/L(Pur)");
            itemHdr.add("A/L(Pur1)");
            itemHdr.add("X");
            itemHdr.add("REJ");
            itemHdr.add("SEL");
            itemHdr.add("SEL ");
            itemHdr.add("BROKER");
            itemHdr.add("DAYS");
            itemHdr.add("REMARKS");
//                for(int i=0;i<vwprpLst.size();i++){
//                    String prp=util.nvl((String)vwprpLst.get(i));
//                    if(prpDspBlocked.contains(prp)){
//                    }else if(!itemHdr.contains(prp)){
//                    itemHdr.add(prp);     
//                }
//                }
            pktDtl = new HashMap();
            pktDtl.put("PARTY NAME", "*");
            pktDtl.put("PACK", "NO");
            pktDtl.put("CTS", "PAR");
            pktDtl.put("CTS ", "SEL");
            pktDtl.put("RATE(RS)", "RS");
            pktDtl.put("RATE($)", "$");
            pktDtl.put("A/L(Pur)", "%");
            pktDtl.put("A/L(Pur1)", "%");
            pktDtl.put("REJ", "%");
            pktDtl.put("SEL", "PUR");
            pktDtl.put("SEL ", "SAL");
            pktDtl.put("BROKER", "*");
            pktDtl.put("REMARKS", "*");
            
            pktDtlList.add(pktDtl);
            String mprpStr = "";
            String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||Upper(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1')) str From Rep_Prp Rp Where rp.MDL = ? order by srt " ;
            ary = new ArrayList();
            ary.add("DAILY_VW_CD");

            ArrayList outLst = db.execSqlLst("mprp ", mdlPrpsQ , ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()) {
            String val = util.nvl((String)rs.getString("str"));
            mprpStr = mprpStr +" "+val;
            }
            rs.close(); pst.close();
            String conQ="";
            String pktDtlSql = "with  STKDTL as  ( Select b.pur_idn,a.pur_dtl_idn,nvl(nvl(nvl(st.txt,st.dte),st.num),'NA') atr , st.mprp,to_char(a.cts,'99999999990.99') cts,byr.get_nm(b.vndr_idn) byr,\n" + 
            "decode(b.cur,'INR',a.rte*nvl(b.exh_rte,1),'') rters,decode(b.cur,'USD',a.rte,'') rtedl,b.flg3 pur,b.aadat_comm pur1,byr.get_nm(b.mbrk1_idn) brk,b.rmk,nvl(b.exh_rte,1) exh_rte,Byr.get_mtrms(b.trms_idn) days\n" + 
            "from pur_prp st,pur_dtl a,mpur b\n" + 
            "where \n" + 
            "st.pur_dtl_idn=a.pur_dtl_idn and a.pur_idn=b.pur_idn\n" + 
            "and a.flg='TRF' and nvl(b.flg2, 'NA') <> 'DEL' \n";

            ary = new ArrayList();
            if(!purIdn.equals("")){
            conQ=" and b.pur_idn=? ";
            ary.add(purIdn);
            }else{
                conQ=" and exists (select 1 from gt_srch_rslt gt where b.pur_idn=gt.srch_id and a.pur_dtl_idn=gt.stk_idn) ";
            }
            pktDtlSql=pktDtlSql+conQ;
            pktDtlSql=pktDtlSql+"and exists (select 1 from rep_prp rp where rp.MDL = 'DAILY_VW_CD' and st.mprp = rp.prp))  \n" + 
            "Select * from stkDtl PIVOT  \n" + 
            "( max(atr)  for mprp in ("+mprpStr+") ) order by 1" ;

            outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while(rs.next()){
            pktDtl = new HashMap();
            pktDtl.put("PARTY NAME", util.nvl(rs.getString("byr")));
            pktDtl.put("PACK", util.nvl(rs.getString("LOTID")));
            pktDtl.put("RATE(RS)", util.nvl(rs.getString("rters")));
            pktDtl.put("RATE($)", util.nvl(rs.getString("rtedl")));
            pktDtl.put("A/L(Pur)", util.nvl(rs.getString("pur")));
            pktDtl.put("A/L(Pur1)", util.nvl(rs.getString("pur1")));
            pktDtl.put("X", util.nvl(rs.getString("exh_rte")));
                pktDtl.put("SEL", util.nvl(rs.getString("cts")));
                pktDtl.put("BROKER", util.nvl(rs.getString("brk")));
                pktDtl.put("DAYS", util.nvl(rs.getString("days")));
                pktDtl.put("REMARKS", util.nvl(rs.getString("rmk")));
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
            
            if(vwPrp.toUpperCase().equals("STK-CTG"))
            fldName = "STK_CTG";
            
            if(vwPrp.toUpperCase().equals("CRN-OP"))
            fldName = "CRN_OP";
            
            if(vwPrp.toUpperCase().equals("CRTWT"))
            fldName = "cts";
            
            if(vwPrp.toUpperCase().equals("RAP_DIS"))
            fldName = "r_dis";
            
            if(vwPrp.toUpperCase().equals("RTE"))
            fldName = "rate";
            
            if (vwPrp.toUpperCase().equals("RAP_RTE"))
            fldName = "raprte";
              if(vwPrp.toUpperCase().equals("MEM_COMMENT"))
              fldName = "MEM_COM1";
            String fldVal = util.nvl((String)rs.getString(fldName));
            
            pktDtl.put(vwPrp, fldVal);
            }
            pktDtlList.add(pktDtl);
            }
            rs.close(); pst.close();
            
            
            ExcelUtil xlUtil = new ExcelUtil();
            xlUtil.init(db, util, info);
            OutputStream out = res.getOutputStream();
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "purchaseReport"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
            HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktDtlList);
            hwb.write(out);
            out.flush();
            out.close();
            util.updAccessLog(req,res,"Daily Purchase Report", "createPurchaseOrderXL end");
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
                rtnPg=util.checkUserPageRights("report/dailyPurReport.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Daily Purchase Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Daily Purchase Report", "init");
            }
            }
            return rtnPg;
            }
    public DailyPurReportAction() {
        super();
    }
}
