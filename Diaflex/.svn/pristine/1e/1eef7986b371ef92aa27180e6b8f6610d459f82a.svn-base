package ft.com.rough;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;
import ft.com.dao.GtPktDtl;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PlaningReturnAction  extends DispatchAction {
    public PlaningReturnAction() {
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
          PlaningReturnForm udf = (PlaningReturnForm)af;
          udf.resetAll();
          ArrayList mprcList = getPrc(req,res);
          udf.setMprcList(mprcList);
          
          ArrayList empList = getEmp(req,res);
          udf.setEmpList(empList);
          
          return am.findForward("load");
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
        ArrayList outLst = db.execSqlLst("empSql", empSql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
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
    
    public ArrayList getPrc(HttpServletRequest req ,HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList prcList = new ArrayList();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
       
        ArrayList ary = new ArrayList();
        HashMap prcSttMap = new HashMap();
        
        
          String grp = util.nvl(req.getParameter("grp"));
          
          String prcSql = "select idn, prc , in_stt from mprc where  stt = ? and is_stt in ('RGH_PLN_IS') " ;
              if(!grp.equals("")){
              prcSql+= " and grp in ("+grp+") ";
              }
              prcSql+=" order by srt";
          ary = new ArrayList();
          ary.add("A");
       ArrayList outLst = db.execSqlLst("prcSql", prcSql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
          try {
              while (rs.next()) {
                  Mprc mprc = new Mprc();
                  String mprcId = rs.getString("idn");
                  mprc.setMprcId(rs.getString("idn"));
                  mprc.setPrc(rs.getString("prc"));
                  mprc.setIn_stt(rs.getString("in_stt"));
                  prcList.add(mprc);
                  prcSttMap.put(mprcId, mprc);
              }
              rs.close();
              pst.close();
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
          
        
        
      
        
      }
        return prcList;
    }
    
    public ActionForward fecth(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          PlaningReturnForm udf = (PlaningReturnForm)form;
          HashMap stockList = new HashMap();
          String mprcIdn = util.nvl((String)udf.getValue("mprcIdn"));
          String empId = util.nvl((String)udf.getValue("empIdn"));
          String issueId = util.nvl((String)udf.getValue("issueId"));
          String grpList = util.nvl((String)udf.getValue("grpList"));
          String lotNo = util.nvl((String)udf.getValue("lotNo"));

          String prcStt = "";
          if(!issueId.equals("") && mprcIdn.equals("0")){
          ArrayList ary = new ArrayList();
          String issuStt = " select b.is_stt , b.idn from iss_rtn a , mprc b where a.prc_id = b.idn and a.iss_id= ? ";
          ary.add(issueId);
          ResultSet rs = db.execSql("issueStt", issuStt, ary);
          while(rs.next()){
            prcStt = util.nvl(rs.getString("is_stt"));
            mprcIdn = util.nvl(rs.getString("idn"));
              
          }
          rs.close();
          }else if(!mprcIdn.equals("0")){
          ArrayList ary = new ArrayList();
          String issuStt = " select b.is_stt  from  mprc b where b.idn = ? ";
          ary.add(mprcIdn);
          ResultSet rs = db.execSql("issueStt", issuStt, ary);
          while(rs.next()){
          prcStt = util.nvl(rs.getString("is_stt"));
            
          }
          rs.close();
          }
          if(!prcStt.equals("")){
          HashMap params = new HashMap();
          params.put("stt", prcStt);
          params.put("empIdn", empId);
          params.put("issueId", issueId);
          params.put("grpList", grpList);
          params.put("mdl", "RGH_VIEW");
          params.put("lotNo", lotNo);

          stockList = FecthResult(req,res, params);
         
          }
          
          req.setAttribute("view", "Y");
          udf.setValue("prcId", mprcIdn);
          udf.setValue("empId", empId);
          req.setAttribute("prcId", mprcIdn);
          String lstNme = "PLNRTN_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
          gtMgr.setValue(lstNme+"_SEL",new ArrayList());
          gtMgr.setValue(lstNme, stockList);
          gtMgr.setValue("lstNmePLNRTN", lstNme);
          if(stockList.size()>0){
          HashMap dtlMap = new HashMap();
          ArrayList selectstkIdnLst = new ArrayList();
          Set<String> keys = stockList.keySet();
                 for(String key: keys){
                selectstkIdnLst.add(key);
                 }
          dtlMap.put("selIdnLst",selectstkIdnLst);
          dtlMap.put("pktDtl", stockList);
          dtlMap.put("flg", "M");
          HashMap ttlMap = util.getTTL(dtlMap);
          HashMap planDtl = getPlanDtl(req, res);
          gtMgr.setValue(lstNme+"_TTL", ttlMap);
          gtMgr.setValue(lstNme+"_PLN", planDtl);
          
          }
          
          return am.findForward("load");
          }
    }
    
    public HashMap getPlanDtl(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        HashMap planDtl = new HashMap();
        if(info!=null){
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
         init(req,res,session,util);
         String planDtlSql = "select a.plan_seq , a.qty , a.cts ,a.rte ,a.vlu,a.ref_idn , to_char(a.vlu/b.cts,'99999999990.00') rrte from PLAN_M a , gt_srch_rslt b\n" + 
         "where a.ref_idn=b.stk_idn order by a.ref_idn,plan_seq";
          ArrayList outLst = db.execSqlLst("", planDtlSql, new ArrayList());
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet rs = (ResultSet)outLst.get(1);
         try {
                while (rs.next()) {
                    String planSeq = util.nvl(rs.getString("plan_seq"));
                    String ref_idn = util.nvl(rs.getString("ref_idn"));
                    HashMap calDtl = new HashMap();
                    calDtl.put("CTS", util.nvl(rs.getString("cts")));
                    calDtl.put("QTY", util.nvl(rs.getString("qty")));
                    calDtl.put("RTE", util.nvl(rs.getString("rte")));
                    calDtl.put("VLU", util.nvl(rs.getString("vlu")));
                    calDtl.put("RRTE", util.nvl(rs.getString("rrte")));
                    planDtl.put(ref_idn+"_"+planSeq,calDtl);
                }
                rs.close();
             pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        }
        
        return planDtl;
    }
    public HashMap GetTotal(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap gtTotalMap = new HashMap();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
       
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
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return gtTotalMap ;
    }
    
    
    public HashMap FecthResult(HttpServletRequest req,HttpServletResponse res, HashMap params){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap stockList = new HashMap();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
      
        String stt = (String)params.get("stt");
        String issId = (String)params.get("issueId");
        String empIdn = (String)params.get("empIdn");
        String mdl = (String)params.get("mdl");
        String lotNo = (String)params.get("lotNo");
        ArrayList ary = null;
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk , rln_idn , sk1 ) " + 
        " select  a.pkt_ty, a.idn, a.vnm,  a.dte, b.stt , 'Z' , a.qty , a.cts , b.iss_id , a.tfl3 , b.iss_emp_id , a.sk1 "+
        "   from mstk a , iss_rtn_dtl b where a.idn = b.iss_stk_idn and b.stt in ('IS','LK')  "+
        " and a.stt =? and a.cts > 0  and b.pkt_rt is null  ";
        ary = new ArrayList();
        ary.add(stt);
        if(!issId.equals("")){
            srchRefQ = srchRefQ+" and b.iss_id = ? " ;
            ary.add(issId);
        }
        if(!empIdn.equals("0")){
            
            srchRefQ = srchRefQ+" and b.iss_emp_id = ? " ;
            ary.add(empIdn);
        }
        if(!lotNo.equals("")){
            srchRefQ = srchRefQ+" and EXISTS ( select 1 from stk_dtl c where a.idn = c.mstk_idn and c.mprp='LOTNO' and c.txt = ?)";
            ary.add(lotNo);
        }
       
        ct = db.execDirUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add("RGH_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
      
        stockList = SearchResult(req ,res, "",mdl);
      }
        return stockList;
    }
    
    
    
    public HashMap SearchResult(HttpServletRequest req ,HttpServletResponse res, String vnmLst,String mdl ){
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
       
        ArrayList vwPrpLst = ASPrprViw(req,res,mdl);
        String  srchQ =  " select stk_idn , pkt_ty,stt,  vnm, pkt_dte, stt , qty , cts , srch_id ,rmk , byr.get_nm(rln_idn) emp ";

        

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
        ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                String stkIdn=util.nvl(rs.getString("stk_idn"));
                GtPktDtl pktPrpMap = new GtPktDtl();
                pktPrpMap.setValue("stt", util.nvl(rs.getString("stt")));
                pktPrpMap.setValue("emp",  util.nvl(rs.getString("emp")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.setValue("vnm",vnm);
                    String tfl3 = util.nvl(rs.getString("rmk"));
                    if(vnmLst.indexOf(tfl3)!=-1 && !tfl3.equals("")){
                        if(vnmLst.indexOf("'")==-1 && !tfl3.equals(""))
                            vnmLst =  vnmLst.replaceAll(tfl3,"");
                        else
                            vnmLst =  vnmLst.replaceAll("'"+tfl3+"'", "");
                    } else if(vnmLst.indexOf(vnm)!=-1 && !vnm.equals("")){
                        if(vnmLst.indexOf("'")==-1)
                            vnmLst =  vnmLst.replaceAll(vnm,"");
                        else
                            vnmLst =  vnmLst.replaceAll("'"+vnm+"'", "");
                    }      
                pktPrpMap.setValue("stk_idn",stkIdn);
                pktPrpMap.setValue("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.setValue("cts",util.nvl(rs.getString("cts")));
                  pktPrpMap.setValue("stt",util.nvl(rs.getString("stt")));

                    pktPrpMap.setValue("issIdn",util.nvl(rs.getString("srch_id")));
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
                              
                    pktList.put(stkIdn, pktPrpMap);
                }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        ArrayList list = (ArrayList)req.getAttribute("msgList");
        if(list!=null && list.size()>0){
        }else{
        if(!vnmLst.equals("")){
            vnmLst = util.pktNotFound(vnmLst);
            req.setAttribute("vnmNotFnd", vnmLst);
        }
        }
      }
        return pktList;
    }
    public ArrayList ASPrprViw(HttpServletRequest req , HttpServletResponse res,String mdl){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute(mdl);
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
                ArrayList ary = new ArrayList();
                ary.add(mdl);
            
              ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = ? and flg='Y' order by rnk ",
                          ary);
               PreparedStatement pst = (PreparedStatement)outLst.get(0);
               ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                pst.close();
                session.setAttribute(mdl, asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return asViewPrp;
    }
    public ActionForward loadPlan(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             PlaningReturnForm udf = (PlaningReturnForm)af;
           
             ArrayList ary = new ArrayList();
             String stkIdn=util.nvl(req.getParameter(("stkIdn")));
             String currloop=util.nvl(req.getParameter(("loop")));
                 if(stkIdn.equals(""))
                   stkIdn=util.nvl((String)udf.getValue("stkIdn"));
             String planseq=util.nvl(req.getParameter(("plan")));
             if(planseq.equals(""))
                 planseq=util.nvl((String)udf.getValue("planSeq"));
             String issueIdn=util.nvl(req.getParameter(("issueIdn")));
             if(issueIdn.equals(""))
                 issueIdn=util.nvl((String)udf.getValue("issueIdn"));
                 udf.reset();
             GenericInterface genericInt = new GenericImpl();
             ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "PLAN_ENTRY","PLAN_ENTRY");
             int loop=1;
                 
             String mprpStr = "";
             String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||\n" + 
             "Upper(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT')) \n" + 
             "str From Rep_Prp Rp Where rp.MDL = ? and flg='Y' order by srt " ;
             ary = new ArrayList();
             ary.add("PLAN_ENTRY");
             ArrayList outLst = db.execSqlLst("mprp ", mdlPrpsQ , ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
             String val = util.nvl((String)rs.getString("str"));
             mprpStr = mprpStr +" "+val;
             }
             rs.close();
              pst.close();
             
             String getPktData = "with PLANPKTDTL as  (Select pm.plan_seq,p_pkt.sub_plan_seq,pm.idn,pm.pkt_typ,p_pkt.idn pktidn,p_pkt.cts,p_pkt.rmk,p_pkt.rap_rte,p_pkt.rte,decode(p_pkt.rap_rte, 1, '', trunc(((p_pkt.rte/greatest(p_pkt.rap_rte,1))*100)-100,2)) dis,to_char((trunc(p_pkt.cts,2)*p_pkt.rte),'999,9999,999,990.00') vlu,to_char((trunc(p_pkt.cts,2)*p_pkt.rap_rte),'999,9999,999,990.00') rap_vlu,\n" + 
             "p_prp.plan_pkt_idn,p_prp.mprp,nvl(nvl(nvl(p_prp.txt,p_prp.dte),p_prp.num),p_prp.val) atr  \n" + 
             "from PLAN_M pm,PLAN_PKT p_pkt,PLAN_PKT_DTL p_prp\n" + 
             "where\n" + 
             "pm.ref_idn=? and pm.plan_seq=? and pm.idn=p_pkt.plan_idn and p_pkt.idn=p_prp.plan_pkt_idn)\n" + 
             "Select * from PLANPKTDTL PIVOT  (max(atr)  for mprp in ("+mprpStr+") )\n" + 
             "order by 1,2 ";
             ary = new ArrayList();
             ary.add(stkIdn);
             ary.add(planseq);
            outLst = db.execSqlLst(" PLANPKTDTL pkts", getPktData, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
             while (rs.next()) {
                 udf.setValue("plan_"+loop,"Y"); 
                 udf.setValue("plan_m_idn_"+loop, util.nvl((String)rs.getString("idn"))); 
                 udf.setValue("plan_pkt_idn_"+loop, util.nvl((String)rs.getString("pktidn"))); 
                 udf.setValue("plan_seq_"+loop, util.nvl((String)rs.getString("plan_seq")));    
                 udf.setValue("sub_plan_seq_"+loop,util.nvl((String)rs.getString("sub_plan_seq")));     
                 udf.setValue("cts_"+loop, util.nvl((String)rs.getString("cts")));    
                 udf.setValue("rap_rte_"+loop, util.nvl((String)rs.getString("rap_rte")));    
                 udf.setValue("rte_"+loop, util.nvl((String)rs.getString("rte")));    
                 udf.setValue("dis_"+loop,util.nvl((String)rs.getString("dis")));    
                 udf.setValue("vlu_"+loop, util.nvl((String)rs.getString("vlu")));   
                 udf.setValue("rap_vlu_"+loop, util.nvl((String)rs.getString("rap_vlu")));
                 udf.setValue("rmk_"+loop, util.nvl((String)rs.getString("rmk"))); 
                     for (int i = 0; i < vwPrpLst.size(); i++) {
                     String vwPrp = (String)vwPrpLst.get(i);
                     String fldName = util.pivot(vwPrp);
                     String fldVal = util.nvl((String)rs.getString(fldName));
                     udf.setValue(vwPrp+"_"+loop, fldVal);
                     }
                 loop++;
             }
             rs.close();
                 pst.close();
           udf.setValue("stkIdn", stkIdn);
           udf.setValue("planSeq", planseq);
          udf.setValue("issueIdn", issueIdn);
                 req.setAttribute("stkIdn", stkIdn);
             req.setAttribute("planSeq", planseq);
             req.setAttribute("currloop", currloop);
          return am.findForward("loadPlan");
         }
         }
        
        public ActionForward savePlan(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             PlaningReturnForm udf = (PlaningReturnForm)af;
             
             String stkIdn=(String)udf.getValue("stkIdn");
              String issueIdn = (String)udf.getValue("issueIdn");
             int loop=Integer.parseInt((String)info.getDmbsInfoLst().get("PLAN_ENTRY_DFLT"));
             String loopStr=(String)info.getDmbsInfoLst().get("PLAN_ENTRY_DFLT");
             GenericInterface genericInt = new GenericImpl();
                 String dfultYN="Y";
               String checkedPlan ="select * from plan_m where ref_idn=? and  issue_id = ? and DFLT_YN =? " ;
                 ArrayList ary = new ArrayList();
                 ary.add(stkIdn);
                 ary.add(issueIdn);
                 ary.add("Y");
                 ArrayList rsList = db.execSqlLst("", checkedPlan, ary);
                 PreparedStatement pst =(PreparedStatement)rsList.get(0);
                 ResultSet rs1 = (ResultSet)rsList.get(1);
                 if(rs1.next()){
                     dfultYN="N";
                 }
                 rs1.close();
                 pst.close();
             ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "PLAN_ENTRY","PLAN_ENTRY");
             ArrayList params=new ArrayList();
             String updateQ="update PLAN_PKT set rmk=? where idn=? and plan_idn=?";
             String updaterapQ="update PLAN_PKT set rap_rte=? where idn=?";
             String plan_seqQ="select pm.idn pmidn\n" + 
             "from PLAN_M pm,PLAN_PKT p_pkt\n" + 
             "where \n" + 
             "pm.ref_idn=? and pm.idn=p_pkt.plan_idn and pm.plan_seq=?";
             String insertplan_pktQ="insert into plan_pkt(idn,plan_idn,sub_plan_seq,rmk)\n" + 
             "select ?,?,?,? from dual";
             String insertplan_mQ="insert into plan_m(idn,ref_idn,plan_seq,issue_id,DFLT_YN)\n" + 
             "select ?,?,?,?,? from dual";
                 
             for (int i=1;i<=loop;i++){
             String plantrns=util.nvl((String)udf.getValue("plan_"+i));
                 if(plantrns.equals("Y")){
                     String planMasterIdn=util.nvl((String)udf.getValue("plan_m_idn_"+i));
                     String planpktIdn=util.nvl((String)udf.getValue("plan_pkt_idn_"+i));
                     String rmk=util.nvl((String)udf.getValue("rmk_"+i));
                     if(planMasterIdn.equals("") && planpktIdn.equals("")){
                         String plan_seq=util.nvl((String)udf.getValue("plan_seq_"+i));
                         String sub_plan_seq=util.nvl((String)udf.getValue("sub_plan_seq_"+i));
                             params=new ArrayList();
                             params.add(stkIdn);
                             params.add(plan_seq);
                             ResultSet rs = db.execSql("search Result", plan_seqQ, params);
                             while (rs.next()) {
                                 planMasterIdn=util.nvl((String)rs.getString("pmidn"));
                             }
                             rs.close();
                                     if(!planMasterIdn.equals("")){
                                         planpktIdn=String.valueOf(util.getSeqVal("PLAN_PKT_DTL_SEQ"));
                                         params=new ArrayList();
                                         params.add(planpktIdn);
                                         params.add(planMasterIdn);
                                         params.add(sub_plan_seq);
                                         params.add(rmk);
                                         int ct = db.execUpd("insert PLAN_PKT", insertplan_pktQ, params);  
                                         if(ct>0){
                                         for (int j = 0; j < vwPrpLst.size(); j++) {
                                         String vwPrp = (String)vwPrpLst.get(j);
                                             
                                         String fldVal=util.nvl((String)udf.getValue(vwPrp+"_"+i));
                                             if(!fldVal.equals("")){
                                                 params=new ArrayList();
                                                 params.add(planpktIdn);
                                                 params.add(vwPrp);
                                                 params.add(fldVal);
                                                 int updpkt =db.execCall("RGH_PKG.PKT_PRP_UPD","RGH_PKG.PKT_PRP_UPD(pPktIdn=>?, pPrp=>?, pVal=>?)",params);
                                             }
                                         }
                                         params=new ArrayList();
                                         params.add(util.getRghrap_rte(planpktIdn));
                                         params.add(planpktIdn);
                                         db.execUpd("Upd PLAN_PKT rap_rte", updaterapQ, params); 
                                         
                                         params=new ArrayList();
                                         params.add(planpktIdn);
                                         db.execCall("RGH_PKG.PLAN_PKT_PRI","RGH_PKG.PLAN_PKT_PRI(?)",params);
                                         }
                                     }
                                     else{
                                         planpktIdn=String.valueOf(util.getSeqVal("PLAN_PKT_DTL_SEQ"));
                                         planMasterIdn=String.valueOf(util.getSeqVal("PLAN_M_SEQ"));
                                         params=new ArrayList();
                                         params.add(planMasterIdn);
                                         params.add(stkIdn);
                                         params.add(plan_seq);
                                         params.add(issueIdn);
                                         params.add(dfultYN);
                                         int ct = db.execUpd("insert PLAN_M", insertplan_mQ, params);
                                         if(ct>0){
                                             params=new ArrayList();
                                             params.add(planpktIdn);
                                             params.add(planMasterIdn);
                                             params.add(sub_plan_seq);
                                             params.add(rmk);
                                             ct = db.execUpd("insert PLAN_PKT", insertplan_pktQ, params); 
                                             if(ct>0){
                                                 for (int j = 0; j < vwPrpLst.size(); j++) {
                                                 String vwPrp = (String)vwPrpLst.get(j);
                                                 String fldVal=util.nvl((String)udf.getValue(vwPrp+"_"+i));
                                                     if(!fldVal.equals("")){
                                                         params=new ArrayList();
                                                         params.add(planpktIdn);
                                                         params.add(vwPrp);
                                                         params.add(fldVal);
                                                         int updpkt =db.execCall("RGH_PKG.PKT_PRP_UPD","RGH_PKG.PKT_PRP_UPD(pPktIdn=>?, pPrp=>?, pVal=>?)",params);
                                                     }
                                                 }
                                                 params=new ArrayList();
                                                 params.add(util.getRghrap_rte(planpktIdn));
                                                 params.add(planpktIdn);
                                                 db.execUpd("Upd PLAN_PKT rap_rte", updaterapQ, params); 
                                                 
                                                 params=new ArrayList();
                                                 params.add(planpktIdn);
                                                 int updpkt =db.execCall("RGH_PKG.PLAN_PKT_PRI","RGH_PKG.PLAN_PKT_PRI(?)",params);
                                             }
                                         }
                                     }
                     }else{
                         params=new ArrayList();
                         params.add(rmk);
                         params.add(planpktIdn);
                         params.add(planMasterIdn);
                         int ct = db.execUpd("Upd PLAN_PKT", updateQ, params);  
                         for (int j = 0; j < vwPrpLst.size(); j++) {
                         String vwPrp = (String)vwPrpLst.get(j);
                         String fldVal=util.nvl((String)udf.getValue(vwPrp+"_"+i));
                             if(!fldVal.equals("")){
                                 params=new ArrayList();
                                 params.add(planpktIdn);
                                 params.add(vwPrp);
                                 params.add(fldVal);
                                 int updpkt =db.execCall("RGH_PKG.PKT_PRP_UPD","RGH_PKG.PKT_PRP_UPD(pPktIdn=>?, pPrp=>?, pVal=>?)",params);
                             }
                         }
                         params=new ArrayList();
                         params.add(util.getRghrap_rte(planpktIdn));
                         params.add(planpktIdn);
                         db.execUpd("Upd PLAN_PKT rap_rte", updaterapQ, params); 
                         
                         params=new ArrayList();
                         params.add(planpktIdn);
                         db.execCall("RGH_PKG.PLAN_PKT_PRI","RGH_PKG.PLAN_PKT_PRI(?)",params);
                     }
                 }
             }
           req.setAttribute("savePlan", "Y");
             return loadPlan(am, af, req, res);
         }
         }
        
    public ActionForward Return(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
                throws Exception {
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
             PlaningReturnForm udf = (PlaningReturnForm)af;
                
                 String lstNme = (String)gtMgr.getValue("lstNmePLNRTN");
                 HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme);
                 ArrayList stockIdnLst = new ArrayList();
                 Set<String> keys = stockListMap.keySet();
                 for(String key: keys){
                        stockIdnLst.add(key);
                 }
                ArrayList msgLst = new ArrayList();
                 for(int i=0;i<stockIdnLst.size();i++){
                   String stk_idn = (String)stockIdnLst.get(i);
                     String isChecked = util.nvl((String)udf.getValue("CHK_"+stk_idn));
                     GtPktDtl pktDtl = (GtPktDtl)stockListMap.get(stk_idn);
                     String issIdn = (String)pktDtl.getValue("issIdn");
                     String vnm = (String)pktDtl.getValue("vnm");
                       if(isChecked.equals("yes")){
                           String plan = util.nvl(req.getParameter("FNPLN_"+stk_idn));
                           String planUdt = "update plan_m set stt='F' where ref_idn=? and plan_seq=?";
                           ArrayList ary = new ArrayList();
                           ary.add(stk_idn);
                           ary.add(plan);
                           int ct = db.execUpd("planUdt", planUdt, ary);
                           if(ct>0){
                           String issRtnUpd = "update iss_rtn_dtl set stt='RT' where iss_stk_idn=? and iss_id=?";
                          ary = new ArrayList();
                           ary.add(stk_idn);
                           ary.add(issIdn);
                            ct = db.execUpd("issRtnUpd", issRtnUpd, ary);
                           if(ct>0){
                           String mstkupd = "update mstk set stt='RGH_PRD' where idn=?";
                            ary = new ArrayList();
                           ary.add(stk_idn);
                             ct = db.execUpd("mstkupd", mstkupd, ary);
                             
                             msgLst.add("Plan : "+plan+" update for packet :"+vnm+" successfully.");
                           }
                           }else{
                               msgLst.add("error in process");
                           }
                           
                           
                       }
                     
                 }
                 req.setAttribute("msgList", msgLst);
                 GtMgrReset(req);
                 udf.reset();
                 return am.findForward("load");
             }
                
    }
        
    public ActionForward PlanCal(ActionMapping mapping, ActionForm form,
                               HttpServletRequest req,
                               HttpServletResponse response) throws Exception {
           HttpSession session = req.getSession(false);
           InfoMgr info = (InfoMgr)session.getAttribute("info");
           DBUtil util = new DBUtil();
           DBMgr db = new DBMgr(); 
           db.setCon(info.getCon());
           util.setDb(db);
           util.setInfo(info);
           db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
           util.setLogApplNm(info.getLoApplNm());
           StringBuffer sb = new StringBuffer();
       String stkIdn = util.nvl(req.getParameter("stkIdn"));
     
       ArrayList params = new ArrayList();
       params.add(stkIdn);
      
     
       String planCal = "select plan_seq , qty , cts ,rte ,vlu from PLAN_M  where ref_idn=? order by vlu";
         ArrayList outLst = db.execSqlLst("planCal", planCal, params);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
       if(rs.next()){
           sb.append("<VALUE>");
           sb.append("<SEQ>"+rs.getString("plan_seq")+"</SEQ>");
           sb.append("<QTY>"+rs.getString("qty")+"</QTY>");
           sb.append("<CTS>"+rs.getString("cts")+"</CTS>");
           sb.append("<RTE>"+rs.getString("rte")+"</RTE>");
           sb.append("<VLU>"+rs.getString("vlu")+"</VLU>");
          sb.append("</VALUE>");
           
       }
           rs.close();
           pst.close();
           response.setContentType("text/xml"); 
           response.setHeader("Cache-Control", "no-cache"); 
           response.getWriter().write("<VALUES>"+sb.toString()+"</VALUES>");
       return null;
       }
    public void GtMgrReset(HttpServletRequest req){
          HttpSession session = req.getSession(false);
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
          String lstNme = (String)gtMgr.getValue("lstNmePLNRTN");
          HashMap gtMgrMap = (HashMap)gtMgr.getValues();
           gtMgrMap.remove(lstNme);
           gtMgrMap.remove(lstNme+"_SEL");
           gtMgrMap.remove(lstNme+"_TTL");
            gtMgrMap.remove(lstNme+"_PLN");
           gtMgrMap.remove("lstNmePLNRTN");
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
        
        util.updAccessLog(req,res,"Planning Return", "createXL");
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "PlanningReturnExcel"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
          
        
        ArrayList ary = new ArrayList();  
        ArrayList itemHdr = new ArrayList();
        ArrayList itemHdrPkt = new ArrayList(); 
        HashMap itemDtlsPkt = new HashMap();   
        HashMap itemDtls = new HashMap();
        ArrayList stockIdnLst =new ArrayList();
           
       String idn = util.nvl((String)req.getParameter("idn"));
       idn = idn.replaceFirst(",", "");
       
       
       if(!idn.equals("")){
       
       int ct = db.execUpd(" Delete GT  ", "delete from gt_srch_rslt", new ArrayList());
          
       String sql = 
       " Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts ,  rmk, sk1, srch_id  ) "+ 
       " select  a.pkt_ty, a.idn, a.vnm,  a.dte, a.stt , 'Z' , a.qty , a.cts ,  a.tfl3 ,  a.sk1 , b.iss_id "+  
       " from mstk a , iss_rtn_dtl b where a.idn = b.iss_stk_idn and a.idn in ("+ idn + ") and b.stt in ('IS','LK') ";   
       ary = new ArrayList();     
       ct = db.execDirUpd(" GT Insert ", sql, ary);
       
       String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
       ary = new ArrayList();
       ary.add("RGH_VIEW");
       ct = db.execCall(" Srch Prp ", pktPrp, ary);
       
       ArrayList vwPrpLst = ASPrprViw(req,res,"RGH_VIEW");
       
       
       itemHdr.add("Issue Id");   
       itemHdr.add("Packet No.");   
       for(int j=0; j < vwPrpLst.size(); j++ ){
       String prp = (String)vwPrpLst.get(j);
       itemHdr.add(prp);              
       }
       
       
       HashMap itemDtl = new HashMap();
       HashMap stockList = SearchResult(req ,res, "","RGH_VIEW");
       Set<String> keys = stockList.keySet();
       for(String key: keys){
       stockIdnLst.add(key);
       }
       for(int i=0; i < stockIdnLst.size(); i++ ){
       String stkIdn = (String)stockIdnLst.get(i);
       GtPktDtl stockPkt = (GtPktDtl)stockList.get(stkIdn);
       itemDtl = new HashMap();    
       String issIdn = (String)stockPkt.getValue("issIdn");
       String vnm = (String)stockPkt.getValue("vnm");
       itemDtl.put("Issue Id",issIdn);
       itemDtl.put("Packet No.",vnm);
       for(int j=0; j < vwPrpLst.size(); j++ ){
       String prp = (String)vwPrpLst.get(j);
       String val = (String)stockPkt.getValue(prp);
       itemDtl.put(prp,val);
       }
       itemDtls.put(stkIdn,itemDtl);
       }
       
       
            vwPrpLst = ASPrprViw(req,res,"PLAN_ENTRY");
          
            
            itemHdrPkt.add("Plan Seq");   
            itemHdrPkt.add("Sub Plan Seq");   
            itemHdrPkt.add("Rap");   
            itemHdrPkt.add("Rte");   
            itemHdrPkt.add("Dis");   
            itemHdrPkt.add("Vlu");   
            itemHdrPkt.add("Rap Vlu");   
            itemHdrPkt.add("Rmk");   
            for(int j=0; j < vwPrpLst.size(); j++ ){
            String prp = (String)vwPrpLst.get(j);
            itemHdrPkt.add(prp);              
            }
       
            String mprpStr = "";
            String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||\n" + 
            "Upper(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT')) \n" + 
            "str From Rep_Prp Rp Where rp.MDL = ? and flg='Y' order by srt " ;
            ary = new ArrayList();
            ary.add("PLAN_ENTRY");
          ArrayList outLst = db.execSqlLst("mprp ", mdlPrpsQ , ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()) {
            String val = util.nvl((String)rs.getString("str"));
            mprpStr = mprpStr +" "+val;
            }
            rs.close();
            pst.close();
            
            HashMap itemDtlPkt = new HashMap();
          
            String sql1 = 
            " with PLANPKTDTL as  (Select gt.stk_idn, pm.plan_seq,p_pkt.sub_plan_seq,pm.idn,pm.pkt_typ,p_pkt.idn pktidn,p_pkt.cts,p_pkt.rmk,p_pkt.rap_rte,p_pkt.rte,decode(p_pkt.rap_rte, 1, '', trunc(((p_pkt.rte/greatest(p_pkt.rap_rte,1))*100)-100,2)) dis,to_char((trunc(p_pkt.cts,2)*p_pkt.rte),'999,9999,999,990.00') vlu,to_char((trunc(p_pkt.cts,2)*p_pkt.rap_rte),'999,9999,999,990.00') rap_vlu, " + 
            "p_prp.plan_pkt_idn,p_prp.mprp,nvl(nvl(nvl(p_prp.txt,p_prp.dte),p_prp.num),p_prp.val) atr  " + 
            "from PLAN_M pm,PLAN_PKT p_pkt,PLAN_PKT_DTL p_prp,gt_srch_rslt gt " + 
            "where " + 
            "pm.ref_idn=gt.stk_idn and pm.stt='F' and pm.idn=p_pkt.plan_idn and p_pkt.idn=p_prp.plan_pkt_idn) " + 
            "Select * from PLANPKTDTL PIVOT  (max(atr)  for mprp in ("+mprpStr+") ) " + 
             "order by 1,2 ";
            
            
          
            ary = new ArrayList();  
           outLst = db.execSqlLst("planCal", sql1, ary);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                itemDtlPkt = new HashMap();
                String stkidn = util.nvl((String)rs.getString("stk_idn"));
                itemDtlPkt.put("Plan Seq", util.nvl((String)rs.getString("plan_seq")));    
                itemDtlPkt.put("Sub Plan Seq",util.nvl((String)rs.getString("sub_plan_seq")));     
                itemDtlPkt.put("Rmk", util.nvl((String)rs.getString("rmk"))); 
                itemDtlPkt.put("Rap", util.nvl((String)rs.getString("rap_rte")));    
                itemDtlPkt.put("Rte", util.nvl((String)rs.getString("rte")));    
                itemDtlPkt.put("Dis",util.nvl((String)rs.getString("dis")));    
                itemDtlPkt.put("Vlu", util.nvl((String)rs.getString("vlu")));   
                itemDtlPkt.put("Rap Vlu", util.nvl((String)rs.getString("rap_vlu")));
            
                for (int i = 0; i < vwPrpLst.size(); i++) {
                    String vwPrp = (String)vwPrpLst.get(i);
                    String fldName = util.pivot(vwPrp);
                    String fldVal = util.nvl((String)rs.getString(fldName));
                    itemDtlPkt.put(vwPrp, fldVal);
                }
                itemDtlsPkt.put(stkidn,itemDtlPkt);
                }
            
            rs.close();
            pst.close();
        
        }
        HSSFWorkbook hwb = null;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        hwb = xlUtil.getGenXlRough(itemHdr,itemDtls,itemHdrPkt,itemDtlsPkt,stockIdnLst);
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
        
            util.updAccessLog(req,res,"Planning Return", "End");
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
                util.updAccessLog(req,res,"Planning Return", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Planning Return", "init");
            }
            }
            return rtnPg;
            }
    
}
