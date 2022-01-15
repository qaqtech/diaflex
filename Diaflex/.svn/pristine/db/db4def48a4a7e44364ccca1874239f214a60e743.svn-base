package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.assort.AssortReturnForm;
import ft.com.assort.AssortReturnImpl;
import ft.com.assort.AssortReturnInterface;
import ft.com.dao.GtPktDtl;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.sql.CallableStatement;
import java.sql.Connection;

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

public class BiddingReturnAction  extends DispatchAction {

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
          BiddingReturnForm udf = (BiddingReturnForm)af;
          udf.resetAll();
          GenericInterface genericInt = new GenericImpl();
          ArrayList mprcList = getPrc(req, res,db);
          udf.setMprcList(mprcList);
          
          ArrayList empList = getEmp(req, res);
          udf.setEmpList(empList);
//          ArrayList assortSrchList = genericInt.genricSrch(req,res,"mktGNCSrch","MKT_SRCH");
//          info.setGncPrpLst(assortSrchList);
          finalizeObject(db, util);

          return am.findForward("load");
      }
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
       BiddingReturnForm udf = (BiddingReturnForm)form;
        
        String mprcIdn = util.nvl((String)udf.getValue("mprcIdn"));
        String stoneId = util.nvl((String)udf.getValue("vnmLst"));
        String empId = util.nvl((String)udf.getValue("empIdn"));
        String issueId = util.nvl((String)udf.getValue("issueId"));
        HashMap prcBeansList = (HashMap)session.getAttribute("mprcBean");

         String prcStt = "";
        HashMap stockList=new HashMap();
        
            util.updAccessLog(req,res,"Assort Return", "Issue Id : "+issueId);
            ArrayList ary = new ArrayList();
            String issuStt = " select b.is_stt from  mprc b where b.idn= ? ";
            ary.add(mprcIdn);
          ArrayList  outLst = db.execSqlLst("issueStt", issuStt, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
              prcStt = util.nvl(rs.getString("is_stt"));
                
            }
            rs.close();
          pst.close();
       
        if(!prcStt.equals("")){
        HashMap params = new HashMap();
        params.put("stt", prcStt);
        params.put("vnm",stoneId);
        params.put("empIdn", empId);
        params.put("issueId", issueId);
        stockList = FecthResultGt(req,res, params);
      
        }
        req.setAttribute("view", "Y");
        udf.setValue("prcId", mprcIdn);
        udf.setValue("empId", empId);
        req.setAttribute("prcId", mprcIdn);
          String lstNme = "BIDRTN_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
          udf.setValue("lstNme", lstNme);
          gtMgr.setValue("lstNmeBIDRTN", lstNme);
          gtMgr.setValue(lstNme, stockList);
          gtMgr.setValue(lstNme+"_SEL", new ArrayList());
            finalizeObject(db, util);

        return am.findForward("load");
        }
    }
    public ActionForward save(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
       BiddingReturnForm udf = (BiddingReturnForm)form;
        String rtn = util.nvl((String)udf.getValue("return")); 
        String apply = util.nvl((String)udf.getValue("apply"));
        String returnbyOriginalPrc = util.nvl((String)udf.getValue("returnbyOriginalPrc"));
          Enumeration reqNme = req.getParameterNames();
          ArrayList stkIdnLst = new ArrayList();
          String stkIdnstr="";
           while (reqNme.hasMoreElements()) {
              String paramNm = (String) reqNme.nextElement();
              if (paramNm.indexOf("cb_stk") > -1) {
                  String stkIdn = req.getParameter(paramNm);
                  stkIdnstr = stkIdnstr+","+stkIdn;
                  stkIdnLst.add(stkIdn);
              }
            }
          String lstNme = (String)udf.getValue("lstNme");
          HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
          
          if(!apply.equals("")){
             String msg = "";
               for(int i=0;i<stkIdnLst.size();i++){
                  String stkIdn = (String)stkIdnLst.get(i);
                  String newPrc = util.nvl(req.getParameter("upr_"+stkIdn));
                  String newdis = util.nvl(req.getParameter("uprDis_"+stkIdn));
                  GtPktDtl pktDtl = (GtPktDtl)stockList.get(stkIdn);
                   String vnm = pktDtl.getVnm();
                  String issId = pktDtl.getValue("issIdn");
               
                  ArrayList params = null;
                  if(!newPrc.equals("") && !newdis.equals("")){
                      newPrc = util.roundToDecimals2(newPrc, 0);
                      newdis = util.roundToDecimals2(newdis, 0);
                  params = new ArrayList();
                  params.add(String.valueOf(issId));
                  params.add(stkIdn);
                  params.add("RTE");
                  params.add(newPrc);
                  int ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", params);
                  
                  params = new ArrayList();
                  params.add(String.valueOf(issId));
                  params.add(stkIdn);
                  params.add("RAP_DIS");
                  params.add(newdis);
                  ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", params);
                  
                  params = new ArrayList();
                  params.add(stkIdn);
                  params.add("RTE");
                  params.add(newPrc);
                  ct = db.execCall("stockUpd","stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? )", params);
                  
                  params = new ArrayList();
                  params.add(stkIdn);
                  params.add("RAP_DIS");
                  params.add(newdis);
                  ct = db.execCall("stockUpd","stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? )", params);
                 if(ct>0)
                     msg = msg+""+vnm+",";
                  }
             
             
              }
             req.setAttribute("msg","Price Changes done For :-"+msg);
         }else{
              ArrayList RtnMsgList = new ArrayList();
            for(int i=0;i<stkIdnLst.size();i++){
                String stkIdn = (String)stkIdnLst.get(i);
                ArrayList RtnMsg = new ArrayList();
                GtPktDtl pktDtl = (GtPktDtl)stockList.get(stkIdn);
                String issId = pktDtl.getValue("issIdn");
                ArrayList params = new ArrayList();
                if(!returnbyOriginalPrc.equals("")){
                    params = new ArrayList();
                    params.add(issId);
                    params.add(stkIdn);
                    params.add("RTE");
                    int cnt = db.execUpd(" Update ", "update iss_rtn_prp set rtn_num=iss_num where iss_id=? and iss_stk_idn=? and mprp=?", params);
                }
                params = new ArrayList();
                params.add(issId);
                params.add(stkIdn);
                params.add("RT");
                params.add("MKAV");
                ArrayList out = new ArrayList();
                out.add("I");
                out.add("V");
                String issuePkt = "ISS_RTN_PKG.RTN_PKT(pIssId => ?, pStkIdn => ? , pStt => ? , pStkStt => ? , pCnt=>? , pMsg => ? )";
                CallableStatement ct = db.execCall("issue Rtn", issuePkt, params, out);
                int cnt = ct.getInt(5);
                String msg = ct.getString(6);
                RtnMsg.add(cnt);
                RtnMsg.add(msg);
                RtnMsgList.add(RtnMsg);
            }
            if(RtnMsgList.size()>0)
                req.setAttribute("msgList",RtnMsgList);
        }
          finalizeObject(db, util);

          return am.findForward("load");
      }
    }
    public HashMap FecthResultGt(HttpServletRequest req,HttpServletResponse res, HashMap params){
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
        String vnm = (String)params.get("vnm");
        String issId = (String)params.get("issueId");
        String empIdn = (String)params.get("empIdn");
        String grpList = (String)params.get("grpList");
        ArrayList ary = null;
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk , rln_idn , sk1 ) " + 
        " select  a.pkt_ty, a.idn, a.vnm,  a.dte, a.stt , 'Z' , a.qty , a.cts , b.iss_id , a.tfl3 , b.iss_emp_id , a.sk1 "+
        "   from mstk a , iss_rtn_dtl b where a.idn = b.iss_stk_idn and b.stt='IS'  "+
        " and a.stt =? and a.cts > 0   ";
        ary = new ArrayList();
        ary.add(stt);
        if(vnm.length()>2){
           vnm = util.getVnm(vnm);
            srchRefQ = srchRefQ+" and ( a.vnm in ("+vnm+") or a.tfl3 in ("+vnm+") ) " ;
        }
        if(!issId.equals("")){
            srchRefQ = srchRefQ+" and b.iss_id = ? " ;
            ary.add(issId);
        }
        if(!empIdn.equals("0")){
            
            srchRefQ = srchRefQ+" and b.iss_emp_id = ? " ;
            ary.add(empIdn);
        }
       
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add("MKT_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
         
        stockList = SearchResultGt(req ,res, vnm);
      }
        finalizeObject(db, util);

        return stockList;
    }
    
    public HashMap SearchResultGt(HttpServletRequest req ,HttpServletResponse res, String vnmLst ){
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
       
        ArrayList vwPrpLst = mktPrprViw(req,res);
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , srch_id ,rmk , byr.get_nm(rln_idn) emp ";

        

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
        
        ArrayList  outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
               
                  String stk_idn = util.nvl(rs.getString("stk_idn"));
                  GtPktDtl pktPrpMap = new GtPktDtl();
                  pktPrpMap.setStt(util.nvl(rs.getString("stt")));
                  String vnm = util.nvl(rs.getString("vnm"));
                  pktPrpMap.setVnm(vnm);
                  pktPrpMap.setValue("emp", util.nvl(rs.getString("emp")));
                  pktPrpMap.setValue("vnm", vnm);

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
                  pktPrpMap.setPktIdn(rs.getLong("stk_idn"));
                  pktPrpMap.setQty(util.nvl(rs.getString("qty")));
                  pktPrpMap.setCts(util.nvl(rs.getString("cts")));
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
                              
                  pktList.put(stk_idn,pktPrpMap);
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
        finalizeObject(db, util);

        return pktList;
    }
    
  
    public ArrayList getPrc(HttpServletRequest req ,HttpServletResponse res,DBMgr db){
      
      
        ArrayList ary = new ArrayList();
        ArrayList prcList = new ArrayList();
           
          String prcSql = "select idn, prc , in_stt from mprc where grp='BID' and stt='A'" ;
      ArrayList  outLst = db.execSqlLst("prcSql", prcSql, ary);
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
                 
              }
              rs.close();
              pst.close();
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
          
        return prcList;
    }
    public ArrayList mktPrprViw(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute("mktViewLst");
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
              ArrayList  outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MKT_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                pst.close();
                session.setAttribute("mktViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        finalizeObject(db, util);

        return asViewPrp;
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
        ArrayList  outLst = db.execSqlLst("empSql", empSql, ary);
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
        finalizeObject(db, util);

        return empList;
    }
    public BiddingReturnAction() {
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
                util.updAccessLog(req,res,"Bidding Return", "Unauthorized Access");
                else                
            util.updAccessLog(req,res,"Bidding Return", "init");
            }
            }
            return rtnPg;
    }
    public void finalizeObject(DBMgr db, DBUtil util){
        try {
            db=null;
            util=null;
        } catch (Throwable t) {
            // TODO: Add catch code
            t.printStackTrace();
        }
       
    }
}

    

