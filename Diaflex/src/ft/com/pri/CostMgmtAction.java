package ft.com.pri;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.PriceChangeForm;
import ft.com.marketing.SearchQuery;
import ft.com.report.ReportForm;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class CostMgmtAction  extends DispatchAction {
   
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"CostMgmt", "Load");
       CostMgmtForm udf = (CostMgmtForm)form;
          GenericInterface genericInt = new GenericImpl();
            ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_COSTGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_COSTGNCSrch");
        info.setGncPrpLst(assortSrchList);
          util.updAccessLog(req,res,"CostMgmt", "Load end");
      return am.findForward("load");
        }
    }
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"CostMgmt", "fecth");
       CostMgmtForm udf = (CostMgmtForm)af;
        SearchQuery srchQuery = new SearchQuery();
        packetData(req, res , udf);
        ArrayList vwPrpLst = MFGPrprViw(req,res);
        HashMap pktList = srchQuery.SearchResult(req,res,"'Z'",vwPrpLst);
        req.setAttribute("pktList", pktList);
        req.setAttribute("view","Y");
          util.updAccessLog(req,res,"CostMgmt", "fetch end");
    return am.findForward("load");
        }
    }
    public ActionForward pktGrpDtl(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception {
      HttpSession session = request.getSession(false);
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
      rtnPg=init(request,response,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
          util.updAccessLog(request,response,"CostMgmt", "pktGrpDtl");
        ArrayList grpList = new ArrayList();
        String stkIdn = util.nvl(request.getParameter("mstkIdn"));
        String vnm = util.nvl(request.getParameter("vnm"));
        HashMap stockPrpUpd = new HashMap();
        HashMap labList = new HashMap();
        String stockPrp = "SELECT A.MSTK_IDN, A.LAB,B.DTA_TYP, A.MPRP, A.SRT, A.GRP \n" + 
        "            , DECODE(B.DTA_TYP, 'C', A.VAL,'N', TO_CHAR(A.NUM)||PROP_PKG.GET_VAL(a.MSTK_IDN, a.MPRP)\n" + 
        "            , 'D', TO_CHAR(a.DTE,'dd-mm-rrrr'), NVL(TXT,'')) VAL \n" + 
        "        FROM STK_DTL A, MPRP B , REP_PRP C,STK_RTE D\n" + 
        "        WHERE A.MSTK_IDN=d.MSTK_IDN AND A.GRP=D.GRP and a.lab=d.lab AND A.MPRP = B.PRP AND a.MSTK_IDN =?  AND B.DTA_TYP IN ('C','N','T','D') AND C.PRP = B.PRP AND C.FLG = 'Y' AND MDL IN ('LOOKUP_PKTDTL_LST') \n" + 
        "        order by a.grp, a.psrt, a.mprp,a.lab ";
        ArrayList ary = new ArrayList();
        ary.add(stkIdn);
          ArrayList rsList = db.execSqlLst("stockDtl", stockPrp, ary);
          PreparedStatement pst=(PreparedStatement)rsList.get(0);
          ResultSet rs = (ResultSet)rsList.get(1);
        try {
            while(rs.next()) {
                String grp = util.nvl(rs.getString("GRP"));
                if(!grpList.contains(grp)){
                    grpList.add(grp);
                    labList.put(grp, util.nvl(rs.getString("LAB")));
                }
                
                String mprp =util.nvl(rs.getString("MPRP"));
                String val = util.nvl(rs.getString("VAL"));
              
                String msktIdn = util.nvl(rs.getString("MSTK_IDN"));
                stockPrpUpd.put(msktIdn+"_"+mprp+"_"+grp, val);
               
            }
            
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        pktPrprViw(request,response);
        request.setAttribute("labDtlList",stockPrpUpd);
        request.setAttribute("grpList", grpList);
        request.setAttribute("labList", labList);
        request.setAttribute("vnm", vnm);
          util.updAccessLog(request,response,"CostMgmt", "pktGrpDtl end");
        return am.findForward("grpDtl");
        }
    }
    
    public void packetData(HttpServletRequest req, HttpServletResponse res, CostMgmtForm form) {
        int ct=0;
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
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        String delQ = " Delete from gt_srch_rslt ";
        ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        String vnm = util.nvl((String)form.getValue("vnm"));
        String srchTyp =util.nvl((String)form.getValue("srchRef"));
      if(!vnm.equals(""))
      vnm = util.getVnm(vnm);
     
         if(!vnm.equals("")){
          if(srchTyp.equals("vnm") || srchTyp.equals("cert_no")){
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
                String vnmSub =vnm.substring(fromLoc, toLoc);
          
          String srchRefQ =
          "    Insert into gt_srch_rslt ( srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt,  rap_rte,rap_dis , cert_lab, cert_no, flg, sk1, fquot, quot , cmp  ) " +
          "     select  1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt,  rap_rte ,  decode(rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2)) " +
          "     , cert_lab, cert_no, 'Z' flg, sk1, upr, cmp , cmp " +
          "    from mstk b " +
          "     where  pkt_ty = 'NR' " ;
              String srchStr = "";  
              if(srchTyp.equals("vnm"))
                  srchStr = " and ( b.vnm in ("+vnmSub+") or b.tfl3 in ("+vnmSub+") ) ";
              else if(srchTyp.equals("cert_no"))
                  srchStr = " and b.cert_no in ("+vnmSub+")";  
              srchRefQ=srchRefQ+srchStr;
              ct = db.execUpd(" Srch Ref ", srchRefQ, new ArrayList());
          }}else if(srchTyp.equals("memo")) {
            String srchRefQ = 
            "    Insert into gt_srch_rslt (srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, rap_dis , cert_lab, cert_no, flg, sk1, fquot, quot ) " + 
            "                select 1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt" +
                " , cmp , rap_rte,  decode(rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2)) , cert_lab, cert_no, 'Z' flg, sk1, upr, cmp  " + 
            "                from mstk b where  " + 
            "                pkt_ty = 'NR'" + 
            " and exists (select 1 from jandtl a where a.mstk_idn = b.idn and a.idn in ("+ vnm + "))";
                    
             ct = db.execUpd(" Srch Ref ", srchRefQ, new ArrayList());
           }
      ArrayList ary = new ArrayList();
      String pktPrp = "pkgmkt.pktPrp(0,?)";
      ary = new ArrayList();
      ary.add("COST_VW");
      ct = db.execCall(" Srch Prp ", pktPrp, ary);
      }else{
          HashMap mprp = info.getMprp();
          HashMap prp = info.getPrp();
          ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_COSTGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_COSTGNCSrch");
          info.setGncPrpLst(genricSrchLst);
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
                      String reqVal1 = util.nvl((String)form.getValue(lprp + "_" + lVal),"");
                      if(!reqVal1.equals("")){
                      paramsMap.put(lprp + "_" + lVal, reqVal1);
                      }
                         
                      }
                  }else{
                  String fldVal1 = util.nvl((String)form.getValue(lprp+"_1"));
                  String fldVal2 = util.nvl((String)form.getValue(lprp+"_2"));
                  if(typ.equals("T")){
                      fldVal1 = util.nvl((String)form.getValue(lprp+"_1"));
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
          paramsMap.put("stt", "MKT");
          paramsMap.put("mdl", "COST_VW");
          util.genericSrch(paramsMap);
      }
    }
    public ArrayList pktPrprViw(HttpServletRequest req ,HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    ArrayList asViewPrp = (ArrayList)session.getAttribute("pktViewLst");
    try {
    if (asViewPrp == null) {

    asViewPrp = new ArrayList();

    
      ArrayList rsList = db.execSqlLst(" Vw Lst ", "Select prp from rep_prp where mdl = 'LOOKUP_PKTDTL_LST' and flg='Y' order by rnk ",
    new ArrayList());
      PreparedStatement pst=(PreparedStatement)rsList.get(0);
      ResultSet rs1 = (ResultSet)rsList.get(1);
    while (rs1.next()) {

    asViewPrp.add(rs1.getString("prp"));
    }
        rs1.close();
        pst.close();
    session.setAttribute("pktViewLst", asViewPrp);
    }

    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }

    return asViewPrp;
    }
//    public ArrayList MFGGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("COSTGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'COST_SRCH' and flg <> 'N' order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("COSTGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
    
    public ArrayList MFGPrprViw(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("costViewLst");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
                
              ArrayList rsList = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'COST_VW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement pst=(PreparedStatement)rsList.get(0);
              ResultSet rs1 = (ResultSet)rsList.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                pst.close();
                session.setAttribute("costViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
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
          util.updAccessLog(req,res,"Cost Mgmt", "init");
          }
          }
          return rtnPg;
          }
    public CostMgmtAction() {
        super();
    }
    
}
