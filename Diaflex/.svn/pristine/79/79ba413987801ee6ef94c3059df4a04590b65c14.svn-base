package ft.com.rough;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;


import ft.com.JspUtil;
import ft.com.dao.GtPktDtl;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import ft.com.marketing.CertIssueForm;

import ft.com.marketing.SearchQuery;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class SplitIssueAction extends DispatchAction {
    
    public SplitIssueAction() {
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
          SplitIssueForm splitForm = (SplitIssueForm)af;
          GenericInterface genericInt = new GenericImpl();
          util.updAccessLog(req,res,"Assort Issue", "load");
          
          
          splitForm.resetAll();
          ArrayList mprcList = getPrc(req,res);
          splitForm.setMprcList(mprcList);
          
          ArrayList empList = getEmp(req,res);
          splitForm.setEmpList(empList);
          
          
          HashMap allPageDtl = (info.getPageDetails() == null) ? new HashMap() : (HashMap)info.getPageDetails();
          HashMap pageDtl = (HashMap)allPageDtl.get("RGH_ISSUE");
          if (pageDtl == null || pageDtl.size() == 0) {
              pageDtl = new HashMap();
              pageDtl = util.pagedef("RGH_ISSUE");
              allPageDtl.put("RGH_ISSUE", pageDtl);
          }
          info.setPageDetails(allPageDtl);
          ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_RGHGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_RGHGNCSrch");
          info.setGncPrpLst(assortSrchList);
          return am.findForward("load"); 
      }
      
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
          
          String prcSql = "select idn, prc , in_stt from mprc where  stt = ?  " ;
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
         SplitIssueForm udf = (SplitIssueForm)form;
         String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        String mprcIdn = util.nvl((String)udf.getValue("mprcIdn"));
        String stoneId = util.nvl((String)udf.getValue("vnmLst"));
        String empId = util.nvl((String)udf.getValue("empIdn"));
        String TYP = util.nvl((String)udf.getValue("TYP"));
        String idn = util.nvl((String)udf.getValue("idn"));
        ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_RGHGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_RGHGNCSrch");
        info.setGncPrpLst(genricSrchLst);
    //      ArrayList genricSrchLst = info.getGncPrpLst();
        String stt = "";
        String grp="";
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        String sttSql = "select in_stt,grp from mprc where idn=?";
        ArrayList ary = new ArrayList();
        ary.add(mprcIdn);
          ArrayList outLst = db.execSqlLst("sttSql", sttSql, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
          while(rs.next()){
              stt = util.nvl(rs.getString("in_stt"));
              if(stt.equals("NA"))
                  stt=util.nvl((String)udf.getValue("stt"));
              grp = rs.getString("grp");
          }
            rs.close();
          pst.close();
        HashMap params = new HashMap();
        params.put("stt", stt);
        params.put("vnm",stoneId);
        params.put("empIdn", empId);
        params.put("mprcIdn", mprcIdn);
        params.put("typ", TYP);
        params.put("Idn", idn);
        if(grp.indexOf("MIX")!=-1)
            params.put("Pkt_ty", "MIX");
        else
            params.put("Pkt_ty", "RGH");
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
            }else if(flg.equals("SM")){
                String reqVal1 = util.nvl((String)udf.getValue(lprp));
                    if(!reqVal1.equals("")){
                    String[] srtLst = reqVal1.split(",");
                    if(srtLst.length>0) {  
                    paramsMap.put(lprp, srtLst);
                    }}
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
        HashMap stockList = null;
        if(paramsMap.size()>0){
        paramsMap.put("stt", stt);
        paramsMap.put("mdl", "RGH_VIEW");
        if(grp.indexOf("MIX")!=-1)
         paramsMap.put("MIX", "Y");
        else
        paramsMap.put("PRCD", "ROUGH");
        util.genericSrch(paramsMap);
        
       stockList = SearchResult(req,res, "");
        }else{
        stockList = FecthResult(req,res, params);
        }
            String lstNme = "RGHISSUE_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
            gtMgr.setValue(lstNme+"_SEL",new ArrayList());
            gtMgr.setValue(lstNme, stockList);
            gtMgr.setValue("lstNmeRGH", lstNme);
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
            gtMgr.setValue(lstNme+"_TTL", ttlMap);
        }
            udf.setValue("prcId", mprcIdn);
            udf.setValue("empId", empId);
            
        req.setAttribute("view", "Y");
           
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
    
    public ActionForward Issue(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        SplitIssueForm udf = (SplitIssueForm)form;
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
          if(!stkIdnstr.equals(""))
              stkIdnstr = stkIdnstr.replaceFirst(",", "");
          int ct=0;
        HashMap dbinfo = info.getDmbsInfoLst();
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        String prcId = (String)udf.getValue("prcId");
        String empId = (String)udf.getValue("empId");
          String lstNme = util.nvl((String)udf.getValue("lstNmeRGH"));
        String delQ = " Delete from gt_srch_rslt ";
        ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        

        ArrayList params = null;
        int issueIdn = 0;
          
        if(!stkIdnstr.equals("")){
           stkIdnstr = util.getVnm(stkIdnstr);
           String[] vnmLst = stkIdnstr.split(",");
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
                  
                  toLoc = Math.min(stkIdnstr.lastIndexOf(lookupVnm) + lookupVnm.length(), stkIdnstr.length());
                  String vnmSub = stkIdnstr.substring(fromLoc, toLoc);
               
               vnmSub=vnmSub.toUpperCase();
               vnmSub= vnmSub.replaceAll (" ", "");
               vnmSub= vnmSub.replaceAll ("'", "");
               String insScanPkt = " insert into gt_srch_rslt(stk_idn,flg) select vnm ,'S' from TABLE(PARSE_TO_TBL('"+vnmSub+"'))";
                 ct = db.execDirUpd(" ins scan", insScanPkt,new ArrayList());
               
           }
        }
        
       
        if(ct>0){
        params = new ArrayList();
        params.add(prcId);
        params.add(empId);
        ArrayList out = new ArrayList();
        out.add("I");
        CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", params ,out);
        issueIdn = cst.getInt(3);
       cst.close();
       cst=null;
            util.updAccessLog(req,res,"Assort Issue", "issueIdn" +issueIdn);
         params = new ArrayList();
         params.add(String.valueOf(issueIdn));
         params.add("1");
         params.add("IS");
         String issuePkt = "ISS_RTN_PKG.ALL_ISS_PKT(pIssId =>?, pGrp => ?, pStt => ?)";
         ct = db.execCall("issuePkt", issuePkt, params);
     }
    if(ct > 0){
    req.setAttribute( "msg","Requested packets get Issue with Issue Id "+issueIdn);
    req.setAttribute( "issueidn",String.valueOf(issueIdn));
    }else{
        req.setAttribute( "msg","Error in Issue process"); 
    }
      
        GtMgrReset(req);
    udf.reset();
    int accessidn=util.updAccessLog(req,res,"Assort Issue", "End");
    req.setAttribute("accessidn", String.valueOf(accessidn));;
    return am.findForward("load");
    }
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
          HashMap dbinfo = info.getDmbsInfoLst();
          int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
          String pkt_ty = util.nvl((String)params.get("Pkt_ty"));
        String vnm = (String)params.get("vnm");
        String stt = (String)params.get("stt");
        ArrayList ary = null;
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        
        ary = new ArrayList();
      
        String srchRefQ = "";
       if(!vnm.equals("")){
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
              
              vnmSub=vnmSub.toUpperCase();
              vnmSub= vnmSub.replaceAll (" ", "");
              srchRefQ = 
               "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk,rap_rte,rap_dis ) " + 
               " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , sk1 , tfl3,b.rap_rte,decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))  "+
               "     from mstk b "+
               " where  nvl(cts,0)-nvl(cts_iss,0) > 0 and stt = ? and pkt_ty = ?  ";
              srchRefQ = srchRefQ+" and ( b.vnm in ("+vnmSub+") or b.tfl3 in ("+vnmSub+") or b.cert_no in ("+vnmSub+")) " ;
              ary = new ArrayList();
              ary.add(stt);
              ary.add(pkt_ty);
              ct = db.execUpd(" ins gt_srch_rslt", srchRefQ,ary); 
          }
           
       }else{
         srchRefQ =   "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk,rap_rte,rap_dis ) " + 
           " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , sk1 , tfl3,b.rap_rte,decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))  "+
           "     from mstk b "+
           " where  nvl(cts,0)-nvl(cts_iss,0) > 0  and stt = ? and pkt_ty = ? ";
          
           ary = new ArrayList();
           ary.add(stt);
           ary.add(pkt_ty);
           ct = db.execUpd(" ins gt_srch_rslt", srchRefQ,ary);
           
       }
        
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add("RGH_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        stockList = SearchResult(req ,res, vnm);
      }
        return stockList;
    }
    
    public HashMap SearchResult(HttpServletRequest req ,HttpServletResponse res, String vnmLst ){
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
          SearchQuery query=new SearchQuery();

        ArrayList vwPrpLst = rghPrpview(req,res);
        String  srchQ =  " select stk_idn , pkt_ty, sk1, vnm, pkt_dte, stt , qty , to_char(cts,'99990.00') cts , rmk,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis ";

        

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
                 String stk_idn = util.nvl(rs.getString("stk_idn"));
                GtPktDtl pktPrpMap = new GtPktDtl();
                pktPrpMap.setStt(util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.setVnm(vnm);
                    String tfl3 = util.nvl(rs.getString("rmk"));
                    if(vnmLst.indexOf(tfl3)!=-1 && !tfl3.equals("")){
                        if(vnmLst.indexOf("'")==-1)
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
                pktPrpMap.setValue("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.setValue("cts",util.nvl(rs.getString("cts")));
              pktPrpMap.setSk1(new BigDecimal(util.nvl(rs.getString("sk1"),"0")));
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                      if (prp.toUpperCase().equals("RAP_DIS"))
                      val = util.nvl(rs.getString("r_dis")) ;  
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
          pktList =(HashMap)query.sortByComparator(pktList);
        if(!vnmLst.equals("")){
            vnmLst = util.pktNotFound(vnmLst);
            req.setAttribute("vnmNotFnd", vnmLst);
        }
      }
        return pktList;
    }
    
    public ArrayList rghPrpview(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute("rghViewLst");
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
           
              ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'RGH_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                pst.close();
                session.setAttribute("rghViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return asViewPrp;
    }
    
    public void GtMgrReset(HttpServletRequest req){
          HttpSession session = req.getSession(false);
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
          String lstNme = (String)gtMgr.getValue("lstNmeRGH");
          HashMap gtMgrMap = (HashMap)gtMgr.getValues();
          gtMgrMap.remove(lstNme);
    
           gtMgrMap.remove(lstNme+"_SEL");
           gtMgrMap.remove(lstNme+"_TTL");
           gtMgrMap.remove("lstNmeCRT");
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
                util.updAccessLog(req,res,"Split issue Action", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Split issue Action", "init");
            }
            }
            return rtnPg;
            }
}
