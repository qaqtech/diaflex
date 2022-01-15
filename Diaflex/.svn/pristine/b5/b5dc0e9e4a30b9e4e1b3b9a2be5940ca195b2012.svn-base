package ft.com.pri;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.PrcPrpBean;
import ft.com.generic.GenericImpl;
import ft.com.marketing.SearchQuery;
import ft.com.pricing.PriceCalcFrm;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PriceCalcMultiAction  extends DispatchAction {
    
   
    public PriceCalcMultiAction() {
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
          util.updAccessLog(req,res,"PriceCalc Multi", "load");
         PriceCalcMultiForm udf = (PriceCalcMultiForm)af;
        udf.reset();
        ArrayList prpList = new ArrayList();
        String calcPrpSql = "select prp from rep_prp where mdl='PRI_CALC' order by rnk ";
        ArrayList outLst = db.execSqlLst("calcPrp", calcPrpSql, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            prpList.add(rs.getString("prp"));
        }
        rs.close();
        pst.close();
        session.setAttribute("prpCalcList", prpList);
          util.updAccessLog(req,res,"PriceCalc Multi", "load end");
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
          util.updAccessLog(req,res,"PriceCalc Multi", "fetch");
        PriceCalcMultiForm form = (PriceCalcMultiForm)af;
        String mstkIdn ="";
        String frmID = util.nvl((String)form.getValue("vnm"));
        form.reset();
        String getPrpQ = " select a.mprp, a.mstk_idn , decode(c.dta_typ, 'C', val, 'N', to_char(nvl(num, 0)), 'D', to_char(nvl(a.dte, sysdate), 'dd-MON-rrrr'), a.txt) val "+
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
        }
        rs.close();
        pst.close();
        form.setValue("vnm", frmID);
        form.setValue("stkIdn", mstkIdn);
        util.SOP("Fetch Completed");
          util.updAccessLog(req,res,"PriceCalc Multi", "fetch end");
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
           util.updAccessLog(req,res,"PriceCalc Multi", "generate");
        PriceCalcMultiForm form = (PriceCalcMultiForm)af;
        HashMap mprp = info.getMprp();
         ArrayList ary = new ArrayList();
         ArrayList prpCalcList = (ArrayList)session.getAttribute("prpCalcList");
         String idnLst="";
         for(int m=1;m<=15;m++){
         String isChecked = util.nvl((String)form.getValue("CHK_"+m));
             
        if(isChecked.equals("yes")){
        String frmID = util.nvl((String)form.getValue("Idn_"+m));
        String mstkIdn =  frmID;
        if(frmID.equals("")){
            int prcChkIdn = 0 ;
            String getPrcChkIdn = " select prc_chk_pkg.get_idn from dual ";
            
          ArrayList outLst = db.execSqlLst(" Prc Chk Idn", getPrcChkIdn, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            if(rs.next()) {
                prcChkIdn = rs.getInt(1);
                String insMst = " prc_chk_pkg.add_new(?, '') ";
                ary = new ArrayList();
                ary.add(Integer.toString(prcChkIdn));
                
                db.execCall(" Prc Mst", insMst, ary);
                
                  for(int i=0; i < prpCalcList.size(); i++) {
                      String lprp = (String)prpCalcList.get(i);
                      String val = util.nvl((String)form.getValue(lprp+"_"+m));
                      form.setValue(lprp+"_"+m, val);
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
            pst.close();
        }else{
           for(int i=0; i < prpCalcList.size(); i++) {
            String lprp = (String)prpCalcList.get(i);
            String val = util.nvl((String)form.getValue(lprp+"_"+m));
            if((val != null) && (val.length() > 0)) { 
            form.setValue(lprp+"_"+m, val);    
            ary = new ArrayList();
            ary.add(mstkIdn);
            ary.add(lprp);
            ary.add(val);
           
            String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
             db.execCallDir("update stk Dtl", stockUpd, ary);
            }
         }       
        }
             idnLst+=","+mstkIdn;
            ary = new ArrayList();
            ary.add(mstkIdn);
            int ct =  db.execCall("stk_rap_upd", "stk_rap_upd(pIdn => ?)", ary);
            
            ary = new ArrayList();
            ary.add(mstkIdn);
            String getPrcDtl = " select b.rap_rte, trunc(sum(a.pct), 2) dis, vlu,b.cts cts"+
                             " from prm_dis_v a , mstk b "+
                            " where a.mstk_idn = b.idn and mstk_idn=? " +
                            " group by a.mstk_idn, b.rap_rte,vlu,b.cts  ";
            ArrayList  outLst = db.execSqlLst(" get vals", getPrcDtl, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                String rte=util.nvl(rs.getString("vlu"));
                String crtwt=util.nvl(rs.getString("cts"));
                String venpri=util.nvl((String)form.getValue("PUR_PRI_"+m));
                if(!rte.equals("")){
                form.setValue("rte_"+m, rte);//computed
                form.setValue("val_"+m, Integer.parseInt(rte)*Float.parseFloat(crtwt));
                }else{
                    form.setValue("rte_"+m,"0");//computed
                    form.setValue("val_"+m, "0");   
                }
                if(!venpri.equals("")){
                form.setValue("vrte_"+m, venpri);//vender
                form.setValue("vval_"+m, Integer.parseInt(venpri)*Float.parseFloat(crtwt));
                }else{
                form.setValue("vrte_"+m, "0");//vender
                form.setValue("vval_"+m, "0");    
                }
                //form.setValue("avg_"+m, rte);
                //form.setValue("vavg_"+m, rte);
            }
            rs.close();
            pst.close();
            form.setValue("Idn_"+m, mstkIdn);
            req.setAttribute("view", "Y");
         } 
         }
         ResultSet rs = null;
         idnLst=idnLst.replaceFirst(",", "");
         String grantQ="select trunc(sum(a.vlu*trunc(b.cts,2)),2) vlu , " + 
         "trunc(sum(a.vlu*trunc(b.cts,2))/sum(trunc(b.cts,2)),2) avg from mstk b,prm_dis_v a " + 
         "where b.idn = a.mstk_idn and a.mstk_idn in ("+idnLst+")";
           ArrayList outLst = db.execSqlLst(" Prc Chk Idn", grantQ, new ArrayList());
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
         while(rs.next()){
             req.setAttribute("vlu", util.nvl(rs.getString("vlu"),"0"));
             req.setAttribute("avg", util.nvl(rs.getString("avg"),"0")); 
         }  
         rs.close();
        pst.close();
         String grantVenderQ="select trunc(sum(a.num*trunc(b.cts,2)),2) vlu ,"+ 
        " trunc(sum(a.num*trunc(b.cts,2))/sum(trunc(b.cts,2)),2) avg from mstk b,stk_dtl a"+
        " where b.idn = a.mstk_idn and a.mprp='PUR_PRI' and a.mstk_idn in ("+idnLst+")";
            outLst = db.execSqlLst("grantVenderQ", grantVenderQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
         while(rs.next()){
             req.setAttribute("Vendervlu", util.nvl(rs.getString("vlu"),"0"));
             req.setAttribute("Venderavg", util.nvl(rs.getString("avg"),"0")); 
         }    
         rs.close();
        pst.close();
           util.updAccessLog(req,res,"PriceCalc Multi", "genate end");
        return mapping.findForward("load");
         }
     }
    
    public ArrayList genPriceView(String mstkIdn,HttpServletRequest req){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList pktDtlList = new ArrayList();
        ArrayList ary = new ArrayList();
        String  prmDisFetch = "select grp, prmnme , nvl(pct,vlu) val from prm_dis_v where mstk_idn = ? order by  pct";
        ary.add(mstkIdn);
        ArrayList outLst = db.execSqlLst(" get vals", prmDisFetch, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
            HashMap pktDtl = new HashMap();
             pktDtl.put("grp",util.nvl(rs.getString("grp")));
             pktDtl.put("prmnme",util.nvl(rs.getString("prmnme")));
          
             pktDtl.put("vlu",util.nvl(rs.getString("val")));
             pktDtlList.add(pktDtl);
            }
       
        rs.close();
        pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return pktDtlList;
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
          util.updAccessLog(req,res,"Price Calc multi", "init");
          }
          }
          return rtnPg;
          }

}
