package ft.com.mixakt;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.dao.GtPktDtl;

import ft.com.dao.MNme;

import ft.com.dao.boxDet;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class VerificationAction extends DispatchAction {
    
    public VerificationAction() {
        super();
    }
    
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
          HashMap stockDtl = new HashMap();
          VerificationForm udf = (VerificationForm)af;
          udf.resetAll();
        String currsummary = "select sum(a.qty) cnt, to_char(sum(a.cts),'999,990.999') cts, trunc(((sum(a.cts *nvl(a.upr, a.cmp)) / sum(a.cts)))) avg_Rte, b.val , b.srt from mstk a , stk_dtl b , stk_dtl c " + 
        "where a.idn=b.mstk_idn and b.MSTK_IDN = c.MSTK_IDN " + 
        "and a.stt='MF_IN' and b.mprp='BOX_TYP' and b.grp=1 and a.pkt_ty='MIX' and a.cts > 0 " + 
        "and c.mprp='RECPT_DT' and c.grp=1 and  trunc(c.DTE) = trunc(sysdate) " + 
        "GROUP BY b.val,b.srt order by b.srt ";
        ArrayList rsLst = db.execSqlLst("currSummary", currsummary, new ArrayList());
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
          while(rs.next()){
              String boxTyp = util.nvl(rs.getString("val"));
              stockDtl.put("CUR_"+boxTyp+"_CNT", util.nvl(rs.getString("cnt")));
              stockDtl.put("CUR_"+boxTyp+"_CTS", util.nvl(rs.getString("cts")));
              stockDtl.put("CUR_"+boxTyp+"_RTE", util.nvl(rs.getString("avg_Rte")));
          }
          rs.close();
          pst.close();
          String totalsummary = "select sum(a.qty) cnt, to_char(sum(a.cts),'999,990.00') cts,trunc(((sum(a.cts *nvl(a.upr, a.cmp)) / sum(a.cts)))) avg_Rte, b.val , b.srt from mstk a , stk_dtl b , stk_dtl c " + 
          "where a.idn=b.mstk_idn and b.mstk_idn = c.mstk_idn and a.pkt_ty='MIX' and a.cts > 0 " + 
          "and a.stt='MF_IN' and b.mprp='BOX_TYP' and b.grp=1 and c.mprp='RECPT_DT' and c.grp=1 " + 
          "GROUP BY b.val,b.srt order by b.srt";
         rsLst = db.execSqlLst("totalsummary", totalsummary, new ArrayList());
         pst = (PreparedStatement)rsLst.get(0);
         rs = (ResultSet)rsLst.get(1);
            while(rs.next()){
                String boxTyp = util.nvl(rs.getString("val"));
                String cts = util.nvl(rs.getString("cts"),"0");
                String avg_Rte = util.nvl(rs.getString("avg_Rte"),"0");
                String curCts = util.nvl((String)stockDtl.get("CUR_"+boxTyp+"_CTS"),"0");
                String openCts = String.valueOf(Float.parseFloat(cts) - Float.parseFloat(curCts));
                openCts = String.valueOf(util.roundToDecimals(openCts, 2));
                String cnt  = util.nvl(rs.getString("cnt"),"0");
                String curCnt = util.nvl((String)stockDtl.get("CUR_"+boxTyp+"_CNT"),"0");
                String openCnt = String.valueOf(Integer.parseInt(cnt) - Integer.parseInt(curCnt));
                
                stockDtl.put("TTL_"+boxTyp+"_CNT", cnt);
                stockDtl.put("TTL_"+boxTyp+"_CTS", cts);
                stockDtl.put("TTL_"+boxTyp+"_RTE", avg_Rte);
                stockDtl.put("OPN_"+boxTyp+"_CTS", openCts);
                stockDtl.put("OPN_"+boxTyp+"_CNT", openCnt);
                
            }
          rs.close();
          pst.close();
          ArrayList empList = getEmp(req, res);
          udf.setEmpList(empList);
          gtMgr.setValue("stockDtl", stockDtl);
          return am.findForward("load");   
  
      }
     }
    
    public ActionForward loadKG(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
          HashMap stockDtl = new HashMap();
          VerificationForm udf = (VerificationForm)af;
          udf.resetAll();
        String currsummary = "select sum(a.qty) cnt, to_char(sum(a.cts),'999,990.00') cts," +
            " trunc(((sum(a.cts*nvl(a.upr, a.cmp)) / sum(a.cts)))) avg_Rte , " +
            "b.val , b.srt from mstk a , stk_dtl b " + 
        "where a.idn=b.mstk_idn and a.pkt_ty='MIX' " + 
        "and a.stt='MIX_AS_AV' and b.mprp='BOX_TYP' and b.grp=1 " +  
        "GROUP BY b.val,b.srt order by b.srt ";
        ArrayList rsLst = db.execSqlLst("currSummary", currsummary, new ArrayList());
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
          while(rs.next()){
              String boxTyp = util.nvl(rs.getString("val"));
              stockDtl.put(boxTyp+"_CNT", util.nvl(rs.getString("cnt")));
              stockDtl.put(boxTyp+"_CTS", util.nvl(rs.getString("cts")));
              stockDtl.put(boxTyp+"_AVG", util.nvl(rs.getString("avg_Rte")));
          }
          rs.close();
          pst.close();
         req.setAttribute("KG", "Y");
          ArrayList empList = getEmp(req, res);
          udf.setEmpList(empList);
          gtMgr.setValue("stockDtl", stockDtl);
          return am.findForward("load");   
    
      }
     }
    public ActionForward assort(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
          VerificationForm udf = (VerificationForm)af;
          ArrayList boxTypLst  = new ArrayList();
          Enumeration reqNme = req.getParameterNames();
          while (reqNme.hasMoreElements()) {
              String paramNm = (String) reqNme.nextElement();
             if (paramNm.indexOf("CHK_") > -1) {
                  String val = req.getParameter(paramNm);
                  boxTypLst.add(val);
              }
          }
          String empIdn = util.nvl((String)udf.getValue("empIdn"));
          String prcId = "0";
          String mprcIdn ="select idn from mprc where is_stt='MIX_AS_IS'";
        ArrayList rsLst = db.execSqlLst("mprcInd", mprcIdn, new ArrayList());
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
          while(rs.next()){
              prcId = rs.getString("idn");
          }
          rs.close();
          pst.close();
          ArrayList params = new ArrayList();
          params.add(prcId);
          params.add(empIdn);
          ArrayList out = new ArrayList();
          out.add("I");
          CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", params ,out);
          int issueIdn = cst.getInt(3);
        cst.close();
        cst=null;
          String msg ="";
          try{
          db.setAutoCommit(false); 
          for(int i=0;i<boxTypLst.size();i++){
              String boxTyp = (String)boxTypLst.get(i);
              params = new ArrayList();
              params.add(String.valueOf(issueIdn));
              params.add(boxTyp);
              out = new ArrayList();
              out.add("V");
              cst = db.execCall("issuePkt", "MIX_PKG.ASSORT_ISSUE(pIssId => ?, pBoxTyp => ?, pMsg => ? )", params,out);
               msg = util.nvl(cst.getString(params.size()+1));
              if(!msg.equals("SUCCESS"))
                  break;
          } 
              
              if(msg.equals("SUCCESS")){
                  db.doCommit();
                  req.setAttribute("msg", "Requested packets get Issued with Issue Id "+issueIdn);
              } else{
                  req.setAttribute("msg", "Error in process..");
                  db.doRollBack();
              }

          } catch (Exception e) {
            db.doRollBack();
                    // TODO: Add catch code
                    e.printStackTrace();
          } finally {
              db.setAutoCommit(true);           
          }
          
          
          return load(am, af, req, res);
      }
    }
    public ActionForward loadPkt(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
          VerificationForm udf = (VerificationForm)af;
          String box_typ = util.nvl(req.getParameter("Box_typ"));
          String stt = util.nvl(req.getParameter("stt"));
          String delQ = " Delete from gt_srch_rslt ";
          int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
          
          
          String gtInsert = "Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , quot , sk1 )\n" + 
          "select a.PKT_TY,a.idn,a.vnm,a.DTE,a.stt,'Z',a.qty,a.cts,nvl(upr,cmp),a.sk1 from mstk a , stk_dtl b , stk_dtl c\n" + 
          "where a.idn=b.mstk_idn and b.mstk_idn=c.mstk_idn and c.mprp='RECPT_DT' and c.grp=1  \n" + 
          "and a.stt='MF_IN' and b.mprp='BOX_TYP' and b.val=? ";
          if(stt.equals("CUR")){
              gtInsert = gtInsert +" and  trunc(c.DTE) = trunc(sysdate)";
          }else if(stt.equals("TTL")){
              gtInsert = gtInsert +" and  trunc(c.DTE) = trunc(c.DTE)";
          }else{
              gtInsert = gtInsert +" and  trunc(c.DTE) < trunc(sysdate)";
          }
          gtInsert = gtInsert +" order by sk1";
          ArrayList ary = new ArrayList();
          ary.add(box_typ);
           ct = db.execCall("gtInsert", gtInsert, ary);
          
          String pktPrp = "SRCH_PKG.POP_PKT_PRP_TEST(pMdl => ?)";
          ary = new ArrayList();
          ary.add("BOX_VIEW");
           ct = db.execCall(" Srch Prp ", pktPrp, ary);
          HashMap prp = info.getPrp();
          ArrayList boxIsLst = (ArrayList)prp.get("BOX_TYPV");
          ArrayList boxPIsLst = (ArrayList)prp.get("BOX_TYPP");
          ArrayList boxIdList = new ArrayList();
          for(int i=0;i<boxIsLst.size();i++){
              String boxVal = (String)boxIsLst.get(i);
                  boxDet boxObj = new boxDet();
                  String dsc = util.nvl((String)boxPIsLst.get(i));
                  boxObj.setBoxDesc(dsc);
                  boxObj.setBoxVal(boxVal);
                  boxIdList.add(boxObj);
              
                  
          }
          udf.setBoxList(boxIdList);
          HashMap stockMap = SearchResultGt(req, res,udf);
          String lstNme = "ASPND_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
          gtMgr.setValue("lstNmeASPND", lstNme);
          gtMgr.setValue(lstNme, stockMap);
          return am.findForward("loadPkt");   

      }
     }
    
    public HashMap SearchResultGt(HttpServletRequest req ,HttpServletResponse res,VerificationForm udf ){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap pktList = new HashMap();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
       
        ArrayList vwPrpLst = PrpViewLst(req,res);
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , to_char(cts,'99999.90') cts , srch_id , rmk , quot ";
          String box_typ = util.nvl(req.getParameter("Box_typ"));
        

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
        
        ArrayList ary = new ArrayList();
        ary.add("Z");
        ArrayList rsLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        try {
            while(rs.next()) {

                  String stk_idn = util.nvl(rs.getString("stk_idn"));
                  GtPktDtl pktPrpMap = new GtPktDtl();
                  pktPrpMap.setStt(util.nvl(rs.getString("stt")));
                  String vnm = util.nvl(rs.getString("vnm"));
                  pktPrpMap.setVnm(vnm);
                    pktPrpMap.setValue("vnm", vnm);

                  udf.setValue("BOX_TYP"+stk_idn, box_typ);
                  pktPrpMap.setPktIdn(rs.getLong("stk_idn"));
                  pktPrpMap.setQty(util.nvl(rs.getString("qty")));
                  pktPrpMap.setCts(util.nvl(rs.getString("cts")));
                  for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                           String val = util.nvl(rs.getString(fld)) ;
                             pktPrpMap.setValue(prp, val);
                    }
                              
                  pktList.put(stk_idn,pktPrpMap);
                  }
                  rs.close();
            pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
       
        
      }
        return pktList;
    }
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
          VerificationForm udf = (VerificationForm)af;
          String lstNme = (String)gtMgr.getValue("lstNmeASPND");
          HashMap pktDtl = (HashMap)gtMgr.getValue(lstNme);
           
          ArrayList stockIdnLst  = new ArrayList();
          Enumeration reqNme = req.getParameterNames();
          while (reqNme.hasMoreElements()) {
              String paramNm = (String) reqNme.nextElement();
             if (paramNm.indexOf("CHK_") > -1) {
                  String val = req.getParameter(paramNm);
                  stockIdnLst.add(val);
              }
          }
          String msgsusStr="";
          String msgerStr="";
        String msg="";
          String chnageTyp = util.nvl((String)udf.getValue("chnageTyp"));
          if(!chnageTyp.equals("")){
              if(stockIdnLst!=null && stockIdnLst.size()>0){
              for(int i=0;i<stockIdnLst.size();i++){
                  String stkIdn =(String)stockIdnLst.get(i);
                  String boxTyp= (String)udf.getValue("BOX_TYP"+stkIdn);
                  GtPktDtl pkts = (GtPktDtl)pktDtl.get(stkIdn);
                  String vnm = pkts.getValue("vnm");
                  
                  String updateStkDtl = "update stk_dtl set val= ? where mprp='BOX_TYP' and grp=1 and mstk_idn = ?";
                  ArrayList param = new ArrayList();
                  param.add(boxTyp);
                  param.add(stkIdn);
                int ct = db.execUpd("update stkDtl", updateStkDtl, param);
                  if(ct>0){
                      udf.setValue("BOX_TYP"+stkIdn, boxTyp);
                      pkts.setValue("BOX_TYP", boxTyp);
                      pktDtl.put(stkIdn, pkts);
                  }else{
                      msgerStr=msgerStr+","+vnm;
                  }
                  
              }
            
              if(msgerStr.length()>0)
                  msg="Error To Update Box Typ. Packets :"+msgerStr;
              req.setAttribute("msg", msg);
              gtMgr.setValue(lstNme, pktDtl);
              }
          }else{
        
          if(stockIdnLst!=null && stockIdnLst.size()>0){
          for(int i=0;i<stockIdnLst.size();i++){
              String stkIdn =(String)stockIdnLst.get(i);
              String boxTyp= (String)udf.getValue("BOX_TYP"+stkIdn);
              String boxId = util.nvl(req.getParameter("BOX_ID_TXT1"+stkIdn));
              GtPktDtl pkts = (GtPktDtl)pktDtl.get(stkIdn);
              String vnm = pkts.getValue("vnm");
              ArrayList param = new ArrayList();
              param.add(stkIdn);
              param.add(boxId);
              param.add(boxTyp);
              ArrayList out = new ArrayList();
              out.add("V");
              CallableStatement cts = db.execCall("mktTrf", "MIX_PKG.MKT_TRF(pStkIdn => ?, pBoxID =>?,pBoxTyp => ? , pMsg =>?) ", param, out);
               msg = util.nvl(cts.getString(param.size()+1));
              if(msg.equals("SUCCESS")){
             pktDtl.remove(stkIdn);
                  msgsusStr=msgsusStr+","+vnm;
              }else{
                  msgerStr=msgerStr+","+vnm;
              }
          }}
        
          if(msgsusStr.length()>0)
              msg="Packet Transfer Successfully :"+msgsusStr;
          if(msgerStr.length()>0)
              msg=msg+"Error To Transfer Packets :"+msgerStr;
          req.setAttribute("msg", msg);
          gtMgr.setValue(lstNme, pktDtl);
              udf.reset();
          }
         
          return am.findForward("loadPkt");
      }
    }
    
    public ArrayList getEmp(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList empList = new ArrayList();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
        ArrayList ary = new ArrayList();
      
        String empSql = "select nme_idn, nme from nme_v where typ = 'EMPLOYEE' order by nme";
        ArrayList rsLst = db.execSqlLst("empSql", empSql, ary);
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        try {
            while (rs.next()) {
                MNme emp = new MNme();
                emp.setEmp_idn(rs.getString("nme_idn"));
                emp.setEmp_nme(rs.getString("nme"));
                empList.add(emp);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return empList;
    }
    
    public ArrayList PrpViewLst(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute("boxViewLst");
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
     
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
              
              ArrayList rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'BOX_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement pst = (PreparedStatement)rsLst.get(0);
              ResultSet rs1 = (ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close(); 
                pst.close();
                session.setAttribute("boxViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return asViewPrp;
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
                util.updAccessLog(req,res,"Verification", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Verification", "init");
            }
            }
            return rtnPg;
            }
}
