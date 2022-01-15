package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
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

public class MixMarketingTransferAction extends DispatchAction {

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
          MixMarketingTransferForm tansferToMktform = (MixMarketingTransferForm)form;
          tansferToMktform.resetAll();
            GenericInterface genericInt =new  GenericImpl();
            ArrayList trfSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_TRF_MKT_MIX_IS") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_TRF_MKT_MIX_IS");
          info.setGncPrpLst(trfSrchList);
          return am.findForward("load");

        }}
    
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
      util.updAccessLog(req,res,"TransferToMKTMIX", "fetch");
    MixMarketingTransferForm tansferToMktform = (MixMarketingTransferForm)af;
    GenericInterface genericInt =new  GenericImpl();
     String fetchall = util.nvl(req.getParameter("fetchall"));
      if(fetchall.equals("")){
    HashMap prp = info.getPrp();
    HashMap mprp = info.getMprp();
    HashMap paramsMap = new HashMap();
    ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_TRF_MKT_MIX_IS") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_TRF_MKT_MIX_IS");
    info.setGncPrpLst(genricSrchLst);
    
    for (int i = 0; i < genricSrchLst.size(); i++) {
    ArrayList srchPrp = (ArrayList)genricSrchLst.get(i);
    String lprp = (String)srchPrp.get(0);
    String flg= (String)srchPrp.get(1);
    String prpSrt = lprp ;  
    String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
        if(flg.equals("M")) {
            ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
            ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
            for(int j=0; j < lprpS.size(); j++) {
            String lSrt = (String)lprpS.get(j);
            String lVal = (String)lprpV.get(j);    
            String reqVal1 = util.nvl((String)tansferToMktform.getValue(lprp + "_" + lVal),"");
            if(!reqVal1.equals("")){
            paramsMap.put(lprp + "_" + lVal, reqVal1);
            }
            }
        }else{
            String fldVal1 = util.nvl((String)tansferToMktform.getValue(lprp+"_1"));
           String fldVal2 = util.nvl((String)tansferToMktform.getValue(lprp+"_2"));
           if(fldVal2.equals(""))
               fldVal2=fldVal1;
             if(!fldVal1.equals("") && !fldVal2.equals("")){
                            paramsMap.put(lprp+"_1", fldVal1);
                            paramsMap.put(lprp+"_2", fldVal2);
            }   
        }            
    }
    paramsMap.put("stt", "MX_FN");
    paramsMap.put("mdl", "TRF_MKT_MIX");
    paramsMap.put("MIX", "Y");
    util.genericSrch(paramsMap);
      }else{
        FecthResult(req, res);
      }
    ArrayList stockList = SearchResult(req, res,"", "TRF_MKT_MIX");
    req.setAttribute("StockList", stockList);
      req.setAttribute("view", "yes");
    return am.findForward("load");

  }}
  
  public void FecthResult(HttpServletRequest req,HttpServletResponse res){
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    ArrayList stockList = new ArrayList();
    if(info!=null){  
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
     
      
      ArrayList ary = null;
    
      String delQ = " Delete from gt_srch_rslt ";
      int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
     
      String srchRefQ = 
      "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk ) " + 
      " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , sk1 , tfl3  "+
      "     from mstk b "+
      " where stt ='MX_FN' and cts > 0 and pkt_ty <> 'NR' ";

      
        
        ct = db.execUpd(" Srch Prp ", srchRefQ, new ArrayList());
        
      String pktPrp = "pkgmkt.pktPrp(0,?)";
      ary = new ArrayList();
      ary.add("TRF_MKT_MIX");
      ct = db.execCall(" Srch Prp ", pktPrp, ary);
    }
      
    }
  public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
    MixMarketingTransferForm tansferToMktform = (MixMarketingTransferForm)af;
      String trans= util.nvl((String)tansferToMktform.getValue("trans"));
     String merge= util.nvl((String)tansferToMktform.getValue("transMrg"));
      String isMerge="N";
      if(!merge.equals(""))
          isMerge="Y";
    ArrayList ary = null;
      ArrayList msgLst = new ArrayList();
  Enumeration reqNme = req.getParameterNames();
   while (reqNme.hasMoreElements()) {
      String paramNm = (String) reqNme.nextElement();
      if (paramNm.indexOf("cb_stk") > -1) {
          String stkIdn = req.getParameter(paramNm);
           ary = new ArrayList();
           ary.add(stkIdn);
           ary.add(isMerge);
           ArrayList out = new ArrayList();
           out.add("V");
           CallableStatement cts = db.execCall("dp_mix_mktg", "DP_MIX_MKTG_TRF(pStkIdn => ?, pMerge => ? , pMsg =>? )", ary, out);
           String pmsg = util.nvl(cts.getString(ary.size()+1));
           msgLst.add(pmsg);
      }}
      req.setAttribute("msgList", msgLst);
        tansferToMktform.resetAll();
        return am.findForward("load");

   } 
  }
  
  public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res, String vnmLst,String mdl ){
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    ArrayList pktList = new ArrayList();
    if(info!=null){
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
     init(req,res,session,util);
     
      ArrayList vwPrpLst = ASPrprViw(req,res,mdl);
      String  srchQ =  " select stk_idn , pkt_ty,stt,  vnm, pkt_dte, stt , qty , cts , srch_id  ";

      

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
      ArrayList  rsLst = db.execSqlLst("search Result", rsltQ, ary);
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
      try {
          while(rs.next()) {

              HashMap pktPrpMap = new HashMap();
              pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
              String vnm = util.nvl(rs.getString("vnm"));
              pktPrpMap.put("vnm",vnm);
      
              pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
              pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
              pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                pktPrpMap.put("stt",util.nvl(rs.getString("stt")));

              for(int j=0; j < vwPrpLst.size(); j++){
                   String prp = (String)vwPrpLst.get(j);
                    
                    String fld="prp_";
                    if(j < 9)
                            fld="prp_00"+(j+1);
                    else    
                            fld="prp_0"+(j+1);
                    
                    String val = util.nvl(rs.getString(fld)) ;
                   
                      
                      pktPrpMap.put(prp, val);
                       }
                            
                  pktList.add(pktPrpMap);
              }
          rs.close();
          stmt.close();
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
            
            ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = ? and flg='Y' order by rnk ",
                        ary);
            PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
            ResultSet  rs1 =(ResultSet)rsLst.get(1);
              while (rs1.next()) {

                  asViewPrp.add(rs1.getString("prp"));
              }
              rs1.close();
              stmt.close();
              session.setAttribute(mdl, asViewPrp);
          }

      } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
      }
    }
      return asViewPrp;
  }
  
    public MixMarketingTransferAction() {
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
              util.updAccessLog(req,res,"Mix Marketing Transfer", "Unauthorized Access");
              else
              util.updAccessLog(req,res,"Mix Marketing Transfer", "init");
          }
          }
          return rtnPg;
          }
  
}
