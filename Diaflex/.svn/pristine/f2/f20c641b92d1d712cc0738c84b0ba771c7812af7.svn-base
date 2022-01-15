package ft.com.box;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.assort.AssortIssueAction;
import ft.com.assort.AssortIssueForm;
import ft.com.assort.AssortIssueImpl;
import ft.com.assort.AssortIssueInterface;
import ft.com.dao.GtPktDtl;
import ft.com.dao.Mprc;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;

import java.util.LinkedHashMap;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class SingleToSmx extends DispatchAction {
    
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
          SingleToSmxForm udf = (SingleToSmxForm)form;
          GenericInterface genericInt = new GenericImpl();
        util.updAccessLog(req,res,"Single to Smx", "load");     
        udf.reset();

        ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SNGL_SMX_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SNGL_SMX_SRCH"); 
        info.setGncPrpLst(assortSrchList);
        util.updAccessLog(req,res,"Single to Smx", "End");
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
          SingleToSmxForm udf = (SingleToSmxForm)form;         
          util.updAccessLog(req,res,"Single to Smx", "fecth");
       
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        String stoneId = util.nvl((String)udf.getValue("vnmLst"));
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SNGL_SMX_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SNGL_SMX_SRCH"); 
        info.setGncPrpLst(genricSrchLst);
        HashMap stockList = null;
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
            HashMap paramsMap = new HashMap();
            paramsMap.put("stt", "MX_AV");
            paramsMap.put("mdl", "SNGL_SMX_VW");
          if(stoneId.equals("")){
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
          util.genericSrch(paramsMap);
          }else{
              paramsMap.put("vnm", stoneId);
              FecthResult(req,res, paramsMap);
          }

        stockList = SearchResult(req,res, "");
          
        util.updAccessLog(req,res,"Single to Smx", "stockList : "+stockList.size());
        req.setAttribute("view", "Y");
        String lstNme = "SNGLSMX_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
        gtMgr.setValue(lstNme, stockList);
        req.setAttribute("lstNme", lstNme);
        
        util.updAccessLog(req,res,"Single to Smx", "End");
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
        SingleToSmxForm udf = (SingleToSmxForm)form;
        util.updAccessLog(req,res,"Single to Smx", "Issue");
        String lstNme = util.nvl((String)udf.getValue("lstNme"));
        HashMap stockListMap = new HashMap();
        HashMap prp = info.getPrp();
        ArrayList boxIdList = (ArrayList)prp.get("BOX_IDV");
        int error=0;int sucess=0;
        stockListMap = (HashMap)gtMgr.getValue(lstNme);
          int ct=0;
            if(stockListMap!=null && stockListMap.size()>0){
                String updateProcQ="DP_SINGLE_TO_SMX(pStkIdn => ?,pVal => ?)";
                ArrayList stockIdnLst =new ArrayList();
                ArrayList params=new ArrayList();
                LinkedHashMap stockList = new LinkedHashMap(stockListMap); 
                Set<String> keys = stockList.keySet();
                for(String key: keys){
                stockIdnLst.add(key);
                }
                for(int i=0; i < stockIdnLst.size(); i++ ){
                    String stkIdn = (String)stockIdnLst.get(i);
                    String prpval=util.nvl((String)req.getParameter("BOX_ID_"+stkIdn));
                    if(!prpval.equals("")){
                      int getIndex=boxIdList.indexOf(prpval);
                      if (getIndex > -1) {                       
                        params=new ArrayList();
                        params.add(stkIdn);
                        params.add(prpval);
                        ct = db.execCall("stockUpd",updateProcQ, params); 
                        if(ct>0)
                        sucess++;
                      }else{
                          error++;
                      }
                    }
                }
            }
        
        gtMgr.setValue(lstNme, new HashMap());
        req.setAttribute("msg", "Total No of packets=>"+String.valueOf(sucess+error)+" Sucessfully Trf =>"+sucess+" Error in Proess=>"+error);
        udf.reset();
        int accessidn=util.updAccessLog(req,res,"Single to Smx", "End");
        req.setAttribute("accessidn", String.valueOf(accessidn));
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
          String cnt = (String)dbinfo.get("CNT");
          int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        String stt = (String)params.get("stt");
        String vnm = (String)params.get("vnm");
        ArrayList ary = null;
        String flg = "ADD";
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        
        ary = new ArrayList();
        ary.add(stt);
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
               "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk,rap_rte,rap_dis,quot ) " + 
               " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , sk1 , tfl3,b.rap_rte,decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2)),nvl(upr,cmp)  "+
               "     from mstk b "+
               " where stt =? and cts > 0  ";
              srchRefQ = srchRefQ+" and ( b.vnm in ("+vnmSub+") or b.tfl3 in ("+vnmSub+") ) " ;
              ct = db.execUpd(" ins gt_srch_rslt", srchRefQ,ary); 
          }
            String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
            ary = new ArrayList();
            ary.add("SNGL_SMX_VW");
            ct = db.execCall(" Srch Prp ", pktPrp, ary);
        }
        
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
          GenericInterface genericInt = new GenericImpl();
          ArrayList vwPrpLst = genericInt.genericPrprVw(req,res,"SNGL_SMX_VW","SNGL_SMX_VW");
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts ,quot, rmk,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis ";

        

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
        ResultSet rs = db.execSql("search Result", rsltQ, ary);
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
                      if (prp.toUpperCase().equals("RAP_DIS"))
                      val = util.nvl(rs.getString("r_dis")) ;  
                      if (prp.toUpperCase().equals("RTE"))
                      val = util.nvl(rs.getString("rte")) ; 
                      if (prp.toUpperCase().equals("CRTWT"))
                      val = util.nvl(rs.getString("cts")) ; 
                        pktPrpMap.setValue(prp, val);
                         }
                              
                    pktList.put(stk_idn,pktPrpMap);
                }
            rs.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        if(!vnmLst.equals("")){
            vnmLst = util.pktNotFound(vnmLst);
            req.setAttribute("vnmNotFnd", vnmLst);
        }
      }
        return pktList;
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
                util.updAccessLog(req,res,"Single to Smx", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Single to Smx", "init");
            }
            }
            return rtnPg;
     }
    public SingleToSmx() {
        super();
    }
}
