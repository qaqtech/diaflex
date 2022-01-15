package ft.com.pri;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.dao.PrcPrpBean;
import ft.com.generic.GenericImpl;
import ft.com.marketing.SearchQuery;
import ft.com.pricing.PriceCalcFrm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.OutputStream;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.ArrayList;

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

public class PriceCalcAction  extends DispatchAction {
    
  
    public PriceCalcAction() {
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
          util.updAccessLog(req,res,"PriceCalc", "load");
        PriceCalcForm udf = (PriceCalcForm)af;
        udf.reset();
        ArrayList prpList = new ArrayList();
        String prcCalcGrp=util.nvl((String)req.getParameter("prcCalcGrp"));
        
        if(!prcCalcGrp.equals(""))
        prcCalcGrp+="_";
        String calcPrpSql = "select prp from rep_prp where mdl='"+prcCalcGrp+"PRI_CALC' and flg='Y' order by rnk ";
        ArrayList outLst = db.execSqlLst("calcPrp", calcPrpSql, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            prpList.add(rs.getString("prp"));
        }
        rs.close();
        pst.close();
        session.setAttribute(prcCalcGrp+"prpCalcList", prpList);
          
            ArrayList prpCalcList = new ArrayList();
            String calcSql = "select prp from rep_prp where mdl='PRI_CALC_PRP' and flg='Y' order by rnk ";
            outLst = db.execSqlLst("calcPrp", calcSql, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                prpCalcList.add(rs.getString("prp"));
            }
            rs.close();
            pst.close();
            session.setAttribute("prpCalcPriList", prpCalcList);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("PRICE_CALC");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("PRICE_CALC");
        allPageDtl.put("PRICE_CALC",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        req.setAttribute("prcCalcGrp", prcCalcGrp);
        String searchPriCalcView = util.nvl(req.getParameter("searchPriCalcView"),"GRID");
        info.setSearchPriCalcView(searchPriCalcView);
        util.updAccessLog(req,res,"PriceCalc", "load end");
     return am.findForward("load");
        }
    }
    public ActionForward fetch(ActionMapping mapping,
                                 ActionForm af,
                                 HttpServletRequest req,
                                 HttpServletResponse res) throws Exception {
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
          return mapping.findForward(rtnPg);   
      }else{
          util.updAccessLog(req,res,"PriceCalc", "fetch");
        PriceCalcForm form = (PriceCalcForm)af;
        String mstkIdn ="";
          HashMap dbmsSysInfo = info.getDmbsInfoLst();
        String frmID = util.nvl((String)form.getValue("vnm")).trim();
        String prcCalcGrp=util.nvl((String)form.getValue("prcCalcGrp"));
        form.reset();
        String getPrpQ = "select a.mprp, a.mstk_idn , decode(c.dta_typ, 'C', val, 'N', to_char(nvl(num, 0)), 'D', to_char(nvl(a.dte, sysdate), 'dd-MON-rrrr'), a.txt) val,idn "+
            " from stk_dtl a, mstk b , mprp c " +
             " where a.mstk_idn = b.idn  and  a.mprp = c.prp and ( b.vnm in ('"+frmID+"') or b.tfl3 in ('"+frmID+"') ) and a.grp = 1 ";
        
        ArrayList ary = new ArrayList();
       
        ArrayList outLst = db.execSqlLst(" get vals", getPrpQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()) {
          String mprp = rs.getString("mprp");
          String val = rs.getString("val");
            mstkIdn = rs.getString("mstk_idn");
          util.SOP(" Prp : "+ mprp + " | Val : "+ val);
          form.setValue(mprp, val);
            form.setValue("Idn", util.nvl(rs.getString("idn")));   
            if(mprp.equals("SHAPE") && (val.equals("ROUND") || val.equals("RD"))){
                form.setValue("RAP_DTE",util.nvl((String)dbmsSysInfo.get("RD_RAP_DTE")));
            }else{
                form.setValue("RAP_DTE", util.nvl((String)dbmsSysInfo.get("FNCY_RAP_DTE")));
            }
        }
        rs.close();
        pst.close();
        req.setAttribute("prcCalcGrp", prcCalcGrp);
        util.SOP("Fetch Completed");
          util.updAccessLog(req,res,"PriceCalc", "fetch end");
        
        return mapping.findForward("load");
        }
    }
    public ActionForward generate(ActionMapping mapping,
                                 ActionForm af,
                                 HttpServletRequest req,
                                 HttpServletResponse res) throws Exception {
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
           return mapping.findForward(rtnPg);   
       }else{
           util.updAccessLog(req,res,"PriceCalc", "generate");
        PriceCalcForm form = (PriceCalcForm)af;
        HashMap mprp = info.getMprp();
         ArrayList ary = new ArrayList();
        String reset = util.nvl((String)form.getValue("reset"));
        String generate = util.nvl((String)form.getValue("generate"));
        String prcCalcGrp = util.nvl((String)form.getValue("prcCalcGrp"));
        String pkt_ty = util.nvl((String)form.getValue("pkt_ty"));
        ArrayList packetHis = (session.getAttribute("packetHis") == null)?new ArrayList():(ArrayList)session.getAttribute("packetHis");
         if(!generate.equals("")){
             form.setValue("stkIdn", "");
             form.setValue("vnm", "");
             form.setValue("Idn", "");   
         }
        if(reset.equals("")){
       
        String mstkIdn =  util.nvl((String)form.getValue("stkIdn"));
         ArrayList prpCalcList = (ArrayList)session.getAttribute(prcCalcGrp+"prpCalcList");
        if(mstkIdn.equals("")){
            int prcChkIdn = 0 ;
            String getPrcChkIdn = " select prc_chk_pkg.get_idn from dual ";
            
            ResultSet rs = db.execSql(" Prc Chk Idn", getPrcChkIdn, new ArrayList());
            if(rs.next()) {
                prcChkIdn = rs.getInt(1);
                String insMst = " prc_chk_pkg.add_new(?, '') ";
                ary = new ArrayList();
                ary.add(Integer.toString(prcChkIdn));

                db.execCall(" Prc Mst", insMst, ary);
                  
                  if(prcCalcGrp.indexOf("MFG") > -1 && !pkt_ty.equals("")){
                  String sql = "update mstk set pkt_ty = ? where idn=?" ;
                  ary = new ArrayList();
                  ary.add(pkt_ty);
                  ary.add(Integer.toString(prcChkIdn));
                  int ct = db.execUpd("sql", sql, ary);
                  }
                  
                  for(int i=0; i < prpCalcList.size(); i++) {
                      String lprp = (String)prpCalcList.get(i);
                      String val = util.nvl((String)form.getValue(lprp));
                      if((val != null) && (val.length() > 0)) {   
                        String insDtl = " prc_chk_pkg.add_dtl(pIdn => ?, pPrp => ?, pVal=> ?) ";
                        ary = new ArrayList();
                        ary.add(Integer.toString(prcChkIdn));
                        ary.add(lprp);
                        ary.add(val);
                        db.execCallDir(" Prc Chk Dtl", insDtl, ary);
            }  }
              mstkIdn = String.valueOf(prcChkIdn);      
          }
            rs.close();
            packetHis.add(mstkIdn);   
        }else{
           for(int i=0; i < prpCalcList.size(); i++) {
            String lprp = (String)prpCalcList.get(i);
            String val = util.nvl((String)form.getValue(lprp));
            if((val != null) && (val.length() > 0)) {   
            ary = new ArrayList();
            ary.add(mstkIdn);
            ary.add(lprp);
            ary.add(val);
           
            String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
             db.execCallDir("update stk Dtl", stockUpd, ary);
            }
         }       
        }
        
            ary = new ArrayList();
            ary.add(mstkIdn);
            db.execCall("Add_Calc_Prp", "prc_chk_pkg.Add_Calc_Prp(pIdn => ?)", ary);
            
           
            
            ary = new ArrayList();
            ary.add(mstkIdn);
            int ct =  db.execCall("stk_rap_upd", "stk_rap_upd(pIdn => ?)", ary);
            
            String prc="prc.ITM_PRI(pIdn => ?)";
            ary = new ArrayList();
            ary.add(mstkIdn);
            if(!prcCalcGrp.equals("")){
            prc="prc.ITM_PRI(pIdn => ?,pTyp =>?)";
            ary.add(prcCalcGrp.replaceAll("_", ""));
            }
            db.execCall("prc.ITM_PRI", prc, ary);
            
            ArrayList idnList=new ArrayList();
            String idn=util.nvl((String)form.getValue("Idn"));
            if(!idn.equals("") && util.nvl(req.getParameter("submit")).equals("Compare")){
                idnList.add(idn);
            }
            idnList.add(mstkIdn);
            String idnPcs = idnList.toString();
            idnPcs = idnPcs.replace('[','(');
            idnPcs = idnPcs.replace(']',')');
            
            ArrayList prpCalcPriList = (ArrayList)session.getAttribute("prpCalcPriList");
            String getPri = ", max(decode(b.mprp, 'CMP_DIS', b.num, null)) CMP_DIS " + 
            ", max(decode(b.mprp, 'CMP_RTE', b.num, null)) CMP_RTE " + 
            ", max(decode(b.mprp, 'MIX_RTE', b.num, null)) MIX_RTE " + 
            ", max(decode(b.mprp, 'MFG_PRI', b.num, null)) MFG_PRI " + 
            ", max(decode(b.mprp, 'MFG_DIS', b.num, null)) MFG_DIS "+
            ", max(decode(b.mprp, 'MFG_CMP', b.num, null)) MFG_CMP "+
            ", max(decode(b.mprp, 'MFG_CMP_DIS', b.num, null)) MFG_CMP_DIS "+
            ", max(decode(b.mprp, 'MFG_CMP_VLU', b.num, null)) MFG_CMP_VLU ";
            
           
            ArrayList idnDtl=new ArrayList();
            String  getPrcDtl = "select a.idn, a.vnm,a.cts, a.rap_rte " +getPri+
            " from mstk a, stk_dtl b " + 
            " where a.idn = b.mstk_idn and b.grp = 1 and a.idn in "+idnPcs+
            " group by a.idn,a.cts, a.vnm, a.rap_rte ";
            ArrayList  outLst = db.execSqlLst(" get vals", getPrcDtl, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
//                req.setAttribute("uprDis", util.nvl(rs.getString("dis")));
//                req.setAttribute("cmp", util.nvl(rs.getString("rte")));
//                req.setAttribute("vnm", util.nvl(rs.getString("vnm")));
                String cts = util.nvl(rs.getString("cts"),"0");
                String rte =util.nvl(rs.getString("CMP_RTE"),"0");
                Double vluD = util.roundToDecimals((Double.parseDouble(cts)*Double.parseDouble(rte)),2);
                String vlu =String.valueOf(vluD);
                HashMap idnDtlList=new HashMap();
               
                 idnDtlList.put("vnm",util.nvl(rs.getString("vnm")));  
                 
                idnDtlList.put("AMT",vlu); 
               
                idnDtlList.put("MFG_PRI", util.nvl(rs.getString("MFG_PRI"))); 
                idnDtlList.put("MFG_DIS", util.nvl(rs.getString("MFG_DIS")));
                idnDtlList.put("CMP_DIS", util.nvl(rs.getString("CMP_DIS")));  
                idnDtlList.put("CMP_RTE", util.nvl(rs.getString("CMP_RTE")));
                idnDtlList.put("MIX_RTE", util.nvl(rs.getString("MIX_RTE"))); 
                idnDtlList.put("MFG_CMP", util.nvl(rs.getString("MFG_CMP"))); 
                idnDtlList.put("MFG_CMP_DIS", util.nvl(rs.getString("MFG_CMP_DIS")));
                idnDtlList.put("MFG_CMP_VLU", util.nvl(rs.getString("MFG_CMP_VLU")));
                
                idnDtlList.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
                idnDtl.add(idnDtlList);
            }
            rs.close();
            pst.close();
            req.setAttribute("submit",util.nvl(req.getParameter("submit")));
            req.setAttribute("idnDtl", idnDtl);
            genPriceView(idnPcs,req,prcCalcGrp);
            req.setAttribute("mstkIdn", mstkIdn);
            form.setValue("vnm", mstkIdn);
            form.setValue("stkIdn", mstkIdn);
            req.setAttribute("view", "Y");
            form.setValue("reset", "");
            form.setValue("generate", "");
            if(packetHis.size()>0)
            session.setAttribute("packetHis", packetHis);    
        }else{
                form.reset();
        }
           req.setAttribute("prcCalcGrp", prcCalcGrp);
           util.updAccessLog(req,res,"PriceCalc", "generate end");
        return mapping.findForward("load");
         }
     }
    public ActionForward history(ActionMapping mapping,
                                 ActionForm af,
                                 HttpServletRequest req,
                                 HttpServletResponse res) throws Exception {
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
           return mapping.findForward(rtnPg);   
       }else{
           util.updAccessLog(req,res,"PriceCalc", "history");
        PriceCalcForm form = (PriceCalcForm)af;
        ArrayList packetHis=(ArrayList)session.getAttribute("packetHis");
        if(packetHis!=null && packetHis.size()>0){
            db.execUpd("delete", "delete from gt_srch_rslt", new ArrayList());
            db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
            String idnPcs = packetHis.toString();
            idnPcs = idnPcs.replace('[','(');
            idnPcs = idnPcs.replace(']',')');
            String insertQ="Insert into gt_srch_rslt ( srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis ) " + 
            "     select  1 srchId , pkt_ty, idn, vnm, qty qty, cts cts, dte, stt, cmp , rap_rte " +
            "     , cert_lab, cert_no, 'Z' flg, sk1, nvl(upr, cmp) " + 
            ", decode(greatest(nvl(rap_rte,1), 1), 1, null, trunc((nvl(upr,cmp)/rap_rte*100) - 100, 2)) dis " + 
            "    from mstk Where Idn In "+idnPcs;
            int ct = db.execUpd(" insert Gt ", insertQ, new ArrayList());
            
            String pktPrp = "pkgmkt.pktPrp(0,?)";
            ArrayList ary=null;
            ary = new ArrayList();
            ary.add("PKT_HISVW");
            ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
             ArrayList pktList = new ArrayList();
             ArrayList vwPrpLst = ASPrprViw(req);
             String  srchQ =  "select Stk_Idn , Pkt_Ty,  Vnm, Pkt_Dte, Stt , Qty , Cts , Cmp , Quot , Rmk ,\n" + 
             "trunc(((cmp*100)/greatest(rap_rte,1)) - 100,2) cmp_dis,rap_rte,\n" + 
             "To_Char(Trunc(Cts,2) * Quot, '99999990.00') Amt,\n" + 
             "decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis  ";
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

            String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by sk1 , cts ";
             ary = new ArrayList();
             ary.add("Z");
          ArrayList rsList = db.execSqlLst("search Result", rsltQ, ary);
          PreparedStatement pst=(PreparedStatement)rsList.get(0);
          ResultSet rs = (ResultSet)rsList.get(1);
             try {
                 while(rs.next()) {

                     HashMap pktPrpMap = new HashMap();
                     pktPrpMap.put("STATUS", util.nvl(rs.getString("stt")));
                     String vnm = util.nvl(rs.getString("vnm"));
                     pktPrpMap.put("PACKETID",vnm);
                     pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                     pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                     pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                         pktPrpMap.put("cmp",util.nvl(rs.getString("cmp")));
                         pktPrpMap.put("quot",util.nvl(rs.getString("quot")));
                         pktPrpMap.put("cmpDis",util.nvl(rs.getString("cmp_dis")));
                         pktPrpMap.put("rap_rte",util.nvl(rs.getString("rap_rte")));
                         pktPrpMap.put("AMOUNT", util.nvl(rs.getString("amt")));
                         pktPrpMap.put("r_dis",util.nvl(rs.getString("r_dis")));
                         for (int j = 0; j < vwPrpLst.size(); j++) {
                             String prp = (String)vwPrpLst.get(j);

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
                             if (prp.toUpperCase().equals("RAP_RTE"))
                                 val = util.nvl(rs.getString("rap_rte"));
                             if(prp.equals("RTE"))
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
             ArrayList itemHdr = new ArrayList();
             itemHdr.add("PACKETID");
             itemHdr.add("STATUS");
             for(int m=0;m<vwPrpLst.size();m++){
             itemHdr.add(vwPrpLst.get(m));
             }
             itemHdr.add("AMOUNT");
             req.setAttribute("view", "Y");
             session.setAttribute("itemHdr", itemHdr);
             session.setAttribute("pktList", pktList);
             int planInx = vwPrpLst.indexOf("Plan_Name");
             if(planInx!=-1){
                 planInx = planInx+1;
                 String planPrp = "prp_00"+planInx;
                 if(planInx>9)
                     planPrp = "prp_0"+planInx;
                 
             }
        }else{
            req.setAttribute("msg", "No Result Found");
            session.setAttribute("itemHdr", new ArrayList());
            session.setAttribute("pktList", new ArrayList()); 
        }
           util.updAccessLog(req,res,"PriceCalc", "history end");
        return mapping.findForward("history");
         }
     }
    public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"PriceCalc", "create xl");
            HashMap dbinfo = info.getDmbsInfoLst();
            String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
            int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String contentTypezip = "application/zip";
        String fileNm = "PacketHistory"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        ArrayList packetHis = (session.getAttribute("packetHis") == null)?new ArrayList():(ArrayList)session.getAttribute("packetHis");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            int pktListsz=pktList.size();
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
            String fileNmzip = "PacketHistory"+util.getToDteTime();
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
        hwb.write(out);
        out.flush();
        out.close();
            }
        if(packetHis.size()>0){
        session.setAttribute("packetHis", new ArrayList()); 
        req.setAttribute("msg", "Packet History Get Cleared");
            session.setAttribute("itemHdr", new ArrayList());
            session.setAttribute("pktList", new ArrayList()); 
        }
        return null;
        }
    }
    public ArrayList ASPrprViw(HttpServletRequest req){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       
        ArrayList asViewPrp = (ArrayList)session.getAttribute("PktHisLst");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
              
                
              ArrayList rsList = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'PKT_HISVW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement pst=(PreparedStatement)rsList.get(0);
              ResultSet rs1 = (ResultSet)rsList.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                pst.close();
                session.setAttribute("PktHisLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
    public void genPriceView(String mstkIdn,HttpServletRequest req,String prcCalcGrp){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
      HashMap pageDtl=(HashMap)allPageDtl.get("PRICE_CALC");
      ArrayList  pageList=(ArrayList)pageDtl.get("GRPDTE");
     boolean isGRPDte = false;
      if(pageList!=null && pageList.size() >0)
        isGRPDte = true;
        String prmDisDtl = "select pct, grp,mstk_idn , pkt_typ ";
        if(isGRPDte)
            prmDisDtl = prmDisDtl+" ,to_char(rev_dte, 'dd-Mon-rrrr HH24:mi:ss') rev_dte ";
        prmDisDtl = prmDisDtl+" from itm_prm_dis_dsp_v where mstk_idn in "+mstkIdn+" and pct is not null order by grp_srt, sub_grp_srt "; 
        
        ArrayList ary = new ArrayList();
        HashMap grpPktTyp = new HashMap();
        ArrayList mstkidnLst=new ArrayList();
        ArrayList pktTypLst = new ArrayList();
        HashMap grp_idnList=new HashMap();
         try {
        if(prcCalcGrp.indexOf("NN") > -1|| prcCalcGrp.indexOf("MKT") > -1 || prcCalcGrp.equals("")){
          ArrayList rsList = db.execSqlLst(" Dis Dtl", prmDisDtl, ary);
          PreparedStatement pst=(PreparedStatement)rsList.get(0);
          ResultSet rs = (ResultSet)rsList.get(1);
        while(rs.next()) {
             String mstk_idn= (String)util.nvl(rs.getString("mstk_idn"));
            String pktTyp = util.nvl(rs.getString("pkt_typ"));
             if(!mstkidnLst.contains(mstk_idn)){
                 mstkidnLst.add(mstk_idn);
             }
            String grpName= util.nvl(rs.getString("grp")); 
            ArrayList grpList = (ArrayList)grpPktTyp.get(pktTyp);
            if(grpList==null)
                grpList= new ArrayList();
            if(!grpList.contains(grpName)){
                grpList.add(grpName);
                grpPktTyp.put(pktTyp, grpList);
            }
            if(!pktTypLst.contains(pktTyp)){
                pktTypLst.add(pktTyp);
            }
            grp_idnList.put(mstk_idn+"_"+grpName+"_"+pktTyp,util.nvl(rs.getString("pct")));
            if(isGRPDte)
             grp_idnList.put(mstk_idn+"_"+grpName+"_"+pktTyp+"_DTE",util.nvl(rs.getString("rev_dte")));  
         } 
            rs.close();
            pst.close();
        }
        if(prcCalcGrp.indexOf("MFG") > -1 || prcCalcGrp.indexOf("MKT") > -1){   
            prmDisDtl=prmDisDtl.replaceAll("itm_prm_dis_dsp_v", "ITM_PRM_DIS_MFG_DSP_V");
          ArrayList rsList = db.execSqlLst(" Dis Dtl", prmDisDtl, ary);
          PreparedStatement pst=(PreparedStatement)rsList.get(0);
          ResultSet rs = (ResultSet)rsList.get(1);
            while(rs.next()) {
                 String mstk_idn= (String)util.nvl(rs.getString("mstk_idn"));
                String pktTyp = util.nvl(rs.getString("pkt_typ"));
                 if(!mstkidnLst.contains(mstk_idn)){
                     mstkidnLst.add(mstk_idn);
                 }
                String grpName= util.nvl(rs.getString("grp")); 
                ArrayList grpList = (ArrayList)grpPktTyp.get(pktTyp);
                if(grpList==null)
                    grpList= new ArrayList();
                if(!grpList.contains(grpName)){
                    grpList.add(grpName);
                    grpPktTyp.put(pktTyp, grpList);
                }
                if(!pktTypLst.contains(pktTyp)){
                    pktTypLst.add(pktTyp);
                }
                grp_idnList.put(mstk_idn+"_"+grpName+"_"+pktTyp,util.nvl(rs.getString("pct")));
                if(isGRPDte)
                 grp_idnList.put(mstk_idn+"_"+grpName+"_"+pktTyp+"_DTE",util.nvl(rs.getString("rev_dte")));  
             } 
                rs.close();
                pst.close();
        }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        req.setAttribute("grp_idnList", grp_idnList); 
        req.setAttribute("grpPktTyp", grpPktTyp);
        req.setAttribute("mstkidnLst", mstkidnLst);
        req.setAttribute("pktTypLst",pktTypLst);
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
          util.updAccessLog(req,res,"Price Calc", "init");
          }
          }
          return rtnPg;
          }
}
