package ft.com.pricing;

import ft.com.DBMgr;

import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.PrcPrpBean;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PriceCalcAction  extends DispatchAction {
   
  public PriceCalcAction() {
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
          util.updAccessLog(req,res,"Assort Issue Action", "init");
          }
          }
          return rtnPg;
          }
  public ActionForward setup(ActionMapping mapping,
                               ActionForm af,
                               HttpServletRequest req,
                               HttpServletResponse res) throws Exception {
      
     
      /*
      HttpSession session = req.getSession(false);
      db = (session.getAttribute("db") == null)?new DBMgr():(DBMgr)session.getAttribute("db");
      info = (session.getAttribute("info") == null)?new InfoMgr():(InfoMgr)session.getAttribute("info");     
      initParams(session);
      */
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
      PriceCalcFrm pf = (PriceCalcFrm)af;
      pf.reset();
      List prcPrp = new ArrayList();
      String getPrpL = " select a.prp, b.dsc prt, b.dta_typ from rep_prp a, mprp b where a.prp = b.prp  and mdl = 'PRC_PRP' and a.flg = 'Y' order by a.rnk ";
        ArrayList outLst = db.execSqlLst(" Price Prp Lst", getPrpL, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()) {
          PrcPrpBean prc = new PrcPrpBean();
          String prp = util.nvl(rs.getString("prp"));
          String dtaTyp = rs.getString("dta_typ");
          String dsc = rs.getString("prt");
          prc.setPrp(prp);
          prc.setPrt1(dsc);
          prc.setPrpTyp(dtaTyp);
          
          List prpL = new ArrayList();
          if(dtaTyp.equals("C"))
              prpL = getPrpLst(req,prp);
          
          prc.setPrpL(prpL);
          prcPrp.add(prc);
      }
      rs.close();
          pst.close();
      //session.setAttribute("db", db);
      //session.setAttribute("info", info);
     session.setAttribute("prpLst", prcPrp);
      pf.reset();
      return mapping.findForward("setup");
      }
  }
  
  public ActionForward fetch(ActionMapping mapping,
                               ActionForm af,
                               HttpServletRequest req,
                               HttpServletResponse res) throws Exception {
      
     
      /*
      HttpSession session = req.getSession(false);
      db = (session.getAttribute("db") == null)?new DBMgr():(DBMgr)session.getAttribute("db");
      info = (session.getAttribute("info") == null)?new InfoMgr():(InfoMgr)session.getAttribute("info");     
      initParams(session);
      */
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
      PriceCalcFrm pf = (PriceCalcFrm)af;
      
      String frmID = util.nvl((String)pf.getValue("vnm")).trim();
      String genButton = util.nvl(pf.getGen());
      pf.reset();
      pf.setValue("vnm", frmID);
      frmID = "'"+frmID+"'";
      //util.SOP("Frm Id : "+ frmID + " | genButton : "+ genButton );
      String getPrpQ = " select mprp, decode(d.dta_typ, 'C', val, 'N', to_char(nvl(num, 0)), 'D', to_char(nvl(a.dte, sysdate), 'dd-MON-rrrr'), a.txt) val,idn "+
          " , b.rap_rte , nvl(b.cmp,b.upr) rte  , decode(b.rap_rte ,'1',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2)) rap_dis from stk_dtl a, mstk b, rep_prp c, mprp d " +
            " where a.mstk_idn = b.idn and a.mprp = c.prp and a.mprp = d.prp and c.mdl = 'PRC_PRP' and  ( b.vnm in ("+frmID+") or b.tfl3 in ("+frmID+") ) and grp = 1 ";
      
      ArrayList ary = new ArrayList();
     
        ArrayList outLst = db.execSqlLst(" get vals", getPrpQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()) {
        String mprp = util.nvl(rs.getString("mprp")).trim();
        String val = util.nvl(rs.getString("val")).trim();
          if(mprp.equals("RAP_RTE"))
              val = rs.getString("rap_rte");
          if(mprp.equals("RAP_DIS"))
              val = rs.getString("rap_dis");
          if(mprp.equals("RTE"))
              val = rs.getString("rte");
          util.SOP(" Prp : "+ mprp + " | Val : "+ val);
          pf.setValue(mprp, val);
          pf.setValue("Rap", util.nvl(rs.getString("rap_rte")));
          pf.setValue("quot", util.nvl(rs.getString("rte")));
          pf.setValue("Idn", util.nvl(rs.getString("idn")));
        util.SOP(" Prp : "+ mprp + " | Val : "+ val);
        pf.setValue(mprp, val);
        pf.setValue("Idn", util.nvl(rs.getString("idn")));  
      }
      rs.close();
       pst.close();
      util.SOP("Fetch Completed");
      return mapping.findForward("setup");
      }
  }
  public List getPrpLst( HttpServletRequest req, String prp) {
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
      List prpL = new ArrayList();
      ArrayList ary = new ArrayList();
      ary.add(prp);
      
      String getPrpL = " select val, srt, prt1 from prp " +
          " where mprp = ? and trunc(nvl(vld_till, sysdate)) >= trunc(sysdate) " +
          //" and val not like '%+%' and val not like '%-%' 
          " and val!='NA' and flg is null "+
          " order by srt ";
      
    ArrayList outLst = db.execSqlLst("Prp L ", getPrpL, ary);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
      try {
          while(rs.next()) {
              PrcPrpBean prpB = new PrcPrpBean();
              prpB.setPrp(prp);
              prpB.setPrt1(rs.getString("prt1"));
              prpB.setVal(rs.getString("val"));
              prpB.setSrt(rs.getInt("srt"));
              prpL.add(prpB);
          }
          rs.close();
          pst.close();
      } catch (SQLException e) {
      }
      return prpL;
  }
    public ActionForward ApplyDiffer(ActionMapping mapping,
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
      PriceCalcFrm pf = (PriceCalcFrm)af;
        ArrayList idnList = (ArrayList)session.getAttribute("mstkidn");
        int ct = db.execCall("Apply Difference", "DP_PCHK_MOD_PKT_PRP(?,?)", idnList);
        //fetch Start
        
        String frmID = util.nvl((String)pf.getValue("vnm")).trim();
        String genButton = util.nvl(pf.getGen());
        pf.reset();
        pf.setValue("vnm", frmID);
        
        frmID = "'"+frmID+"'";
        //util.SOP("Frm Id : "+ frmID + " | genButton : "+ genButton );
        String getPrpQ = " select mprp, decode(d.dta_typ, 'C', val, 'N', to_char(nvl(num, 0)), 'D', to_char(nvl(a.dte, sysdate), 'dd-MON-rrrr'), a.txt) val,idn , b.rap_rte , nvl(b.cmp,b.upr) rte "+
            " , decode(b.rap_rte ,'1',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2)) rap_dis from stk_dtl a, mstk b, rep_prp c, mprp d " +
                         " where a.mstk_idn = b.idn and a.mprp = c.prp and a.mprp = d.prp and c.mdl = 'PRC_PRP' and  ( b.vnm in ("+frmID+") or b.tfl3 in ("+frmID+") ) and grp = 1 ";
        
        ArrayList ary = new ArrayList();
        
          
          ArrayList outLst = db.execSqlLst(" get vals", getPrpQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()) {
          String mprp = rs.getString("mprp");
          String val = rs.getString("val");
            if(mprp.equals("RAP_RTE"))
                val = rs.getString("rap_rte");
            if(mprp.equals("RAP_DIS"))
                val = rs.getString("rap_dis");
            if(mprp.equals("RTE"))
                val = rs.getString("rte");
          util.SOP(" Prp : "+ mprp + " | Val : "+ val);
            pf.setValue(mprp, val);
            pf.setValue("Rap", util.nvl(rs.getString("rap_rte")));
            pf.setValue("quot", util.nvl(rs.getString("rte")));
           pf.setValue("Idn", util.nvl(rs.getString("idn")));  
        }
        rs.close();
          pst.close();
        util.SOP("Fetch Completed");
        //Fetch End
        
        //Compre start
                      
        
        String idnPcs = idnList.toString();
        idnPcs = idnPcs.replace('[','(');
        idnPcs = idnPcs.replace(']',')');
        
        String getPrcDtl = " select idn, cmp, rap_rte rap, trunc(((cmp/rap_rte)*100) - 100,2) rap_dis,to_char(trunc((cts*cmp),2),'9999999990.99')  Vlu "+
              " from mstk where idn in "+idnPcs;
           outLst = db.execSqlLst(" Prc Dtl ", getPrcDtl,new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            ArrayList idnDtl=new ArrayList();
              while(rs.next()) {
                  HashMap idnDtlList=new HashMap();
                   idnDtlList.put("Idn",util.nvl(rs.getString("idn")));   
                   idnDtlList.put("Vlu",util.nvl(rs.getString("Vlu"))); 
                   idnDtlList.put("Rte",util.nvl(rs.getString("cmp")));    
                   idnDtlList.put("Rap",util.nvl(rs.getString("rap")));    
                   idnDtlList.put("RapDis",util.nvl(rs.getString("rap_dis"))); 
                   idnDtl.add(idnDtlList);
               }
        rs.close();
          pst.close();
        req.setAttribute("idnDtl", idnDtl);
        
        String prmDisDtl = " select pct, grp,mstk_idn "+
                 " from ITM_PRM_DIS_V where mstk_idn in "+idnPcs+" and pct is not null and pct !='0' order by grp_srt, sub_grp_srt ";
          outLst = db.execSqlLst(" Dis Dtl", prmDisDtl, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        ArrayList grp=new ArrayList();
        ArrayList mstkidn=new ArrayList();
        HashMap grp_idnList=new HashMap();
        while(rs.next()) {
             String mstk_idn= (String)util.nvl(rs.getString("mstk_idn"));
             if(!mstkidn.contains(mstk_idn)){
                 mstkidn.add(mstk_idn);
             }
            String grpName= (String)util.nvl(rs.getString("grp")); 
            if(!grp.contains(grpName)){
                grp.add(grpName);
            }
            grp_idnList.put(mstk_idn+"_"+grpName,util.nvl(rs.getString("pct")));  
         } 
        rs.close();
          pst.close();
        req.setAttribute("grp_idnList", grp_idnList); 
        req.setAttribute("grp", grp);
        session.setAttribute("mstkidn", mstkidn);
        req.setAttribute("submit",util.nvl(req.getParameter("submit")));
        //Compare end
        
        return mapping.findForward("setup");
        }
    }
  public void initParams(HttpSession session) {
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
      db.setApplId(session.getServletContext().getInitParameter("ApplId"));
      db.setDbHost(session.getServletContext().getInitParameter("HostName"));
      db.setDbPort(session.getServletContext().getInitParameter("Port"));
      db.setDbSID(session.getServletContext().getInitParameter("SID"));
      db.setDbUsr(session.getServletContext().getInitParameter("UserName"));
      db.setDbPwd(session.getServletContext().getInitParameter("Password"));
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
    PriceCalcFrm pf = (PriceCalcFrm)af;
      
      ArrayList prpL = (ArrayList)session.getAttribute("prpLst");
      
      int prcChkIdn = 0 ;
      String getPrcChkIdn = " select prc_chk_pkg.get_idn from dual ";
      
        ArrayList outLst = db.execSqlLst(" Prc Chk Idn", getPrcChkIdn, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
      if(rs.next()) {
          prcChkIdn = rs.getInt(1);
          String insMst = " prc_chk_pkg.add_new(?, '') ";
          ArrayList ary = new ArrayList();
          ary.add(Integer.toString(prcChkIdn));
          
          db.execCall(" Prc Mst", insMst, ary);
          /*String frmID = util.nvl((String)pf.getValue("vnm"));
          String genButton = util.nvl(pf.getGen());
          util.SOP("Frm Id : "+ frmID + " | genButton :  "+ genButton );
          if((genButton.length() > 0) && (frmID.length() > 0)){
            
            String insPrpQ = "insert into stk_dtl (mstk_idn, lab, mprp, val, num, srt) "+
                "select ?, 'NA', a.mprp, a.val, a.num, a.srt " +
                " from stk_dtl a, mstk b where a.mstk_idn = b.idn and b.vnm = ? ";
            
            ary = new ArrayList();
            ary.add(Integer.toString(prcChkIdn));
            ary.add(frmID);
                
            db.execDirUpd(" Ins from VNM ", insPrpQ, ary);    
          } else {
          */
            for(int i=0; i < prpL.size(); i++) {
                PrcPrpBean prc = (PrcPrpBean)prpL.get(i);
                String mprp = prc.getPrp();
                String val = (String)pf.getValue(mprp);
                
                if((val != null) && (val.length() > 0)) {   
                  String insDtl = " prc_chk_pkg.add_dtl(?,?,?) ";
                  ary = new ArrayList();
                  ary.add(Integer.toString(prcChkIdn));
                  ary.add(mprp);
                  ary.add(val);
                  
                  db.execCall(" Prc Chk Dtl", insDtl, ary);
                }  
            }    
          //}            
          String updSz = " sz_chg(pktId => ?) ";
          ary = new ArrayList();
          ary.add(Integer.toString(prcChkIdn));
          db.execCall(" upd prc ", updSz, ary);
          
          String updChange = "dp_lab_charge(p_Idn => ?) ";
          ary = new ArrayList();
          ary.add(Integer.toString(prcChkIdn));
          db.execCall(" upd prc ", updChange, ary);
          
          ary = new ArrayList();
          ary.add(Integer.toString(prcChkIdn));
          ary.add(Integer.toString(prcChkIdn));
          db.execUpd("update stkDtl", "update stk_dtl set lab = (select cert_lab from mstk where idn = ? ) where mstk_idn = ?", ary);
          
        
          String cnf_pri_chng = "pri_pkg.cnf_pri_chng(pStkIdn => ? , pCnt=> ?) ";
          ary = new ArrayList();
          ary.add(Integer.toString(prcChkIdn));
          ArrayList out = new ArrayList();
          out.add("I");
          CallableStatement cstmt = db.execCall(" upd prc ", cnf_pri_chng, ary , out);
          int cnt = cstmt.getInt(ary.size()+1);
          
         
          String updRap = " stk_rap_upd(pIdn => ?) ";
          ary = new ArrayList();
          ary.add(Integer.toString(prcChkIdn));
          db.execCall(" upd prc ", updRap, ary);
        
          String updPrc = " prc.itm_pri(pIdn => ?) ";
          ary = new ArrayList();
          ary.add(Integer.toString(prcChkIdn));
          db.execCall(" upd prc ", updPrc, ary);
          
          session.setAttribute("PRC_CHK", Integer.toString(prcChkIdn));
        cstmt.close();
        cstmt=null;
      }
      rs.close();
        pst.close();
      String cert = (String)pf.getValue("CERT");
      String sh = (String)pf.getValue("SH");
      String crtwt = (String)pf.getValue("CRTWT");
      
      System.out.println(" Cert : "+ cert + " | Sh : "+ sh + " | Crtwt : "+ crtwt);
      
      //Mayur
      ArrayList idnList=new ArrayList();
      String idn=util.nvl((String)pf.getValue("Idn"));
      if(!idn.equals("") && util.nvl(req.getParameter("submit")).equals("Compare")){
          idnList.add(idn);
      }
      idnList.add(prcChkIdn);
    
      
      String idnPcs = idnList.toString();
      idnPcs = idnPcs.replace('[','(');
      idnPcs = idnPcs.replace(']',')');
      
      String getPrcDtl = " select idn, vnm, cmp, rap_rte rap, trunc(((cmp/rap_rte)*100) - 100,2) rap_dis,to_char(trunc((cts*cmp),2),'9999999990.99')  Vlu "+
            " from mstk where idn in "+idnPcs;
         outLst = db.execSqlLst(" Prc Dtl ", getPrcDtl,new ArrayList());
         pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);
          ArrayList idnDtl=new ArrayList();
            while(rs.next()) {
                HashMap idnDtlList=new HashMap();
                 idnDtlList.put("Idn",util.nvl(rs.getString("idn")));  
                 idnDtlList.put("vnm",util.nvl(rs.getString("vnm")));  
                 idnDtlList.put("Vlu",util.nvl(rs.getString("Vlu"))); 
                 idnDtlList.put("Rte",util.nvl(rs.getString("cmp")));    
                 idnDtlList.put("Rap",util.nvl(rs.getString("rap")));    
                 idnDtlList.put("RapDis",util.nvl(rs.getString("rap_dis"))); 
                 idnDtl.add(idnDtlList);
            
             }
      rs.close();
        pst.close();
      req.setAttribute("idnDtl", idnDtl);
      
      String prmDisDtl = " select pct, grp,mstk_idn "+
               " from ITM_PRM_DIS_V where mstk_idn in "+idnPcs+" and pct is not null and pct !='0' order by grp_srt, sub_grp_srt ";
        outLst = db.execSqlLst(" Dis Dtl", prmDisDtl, new ArrayList());
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
      ArrayList grp=new ArrayList();
      ArrayList mstkidn=new ArrayList();
      HashMap grp_idnList=new HashMap();
      while(rs.next()) {
           String mstk_idn= (String)util.nvl(rs.getString("mstk_idn"));
           if(!mstkidn.contains(mstk_idn)){
               mstkidn.add(mstk_idn);
           }
          String grpName= (String)util.nvl(rs.getString("grp")); 
          if(!grp.contains(grpName)){
              grp.add(grpName);
          }
          grp_idnList.put(mstk_idn+"_"+grpName,util.nvl(rs.getString("pct")));  
       }    
      rs.close();
        pst.close();
      req.setAttribute("grp_idnList", grp_idnList); 
      req.setAttribute("grp", grp);
      session.setAttribute("mstkidn", mstkidn);
      req.setAttribute("submit",util.nvl(req.getParameter("submit")));
      return mapping.findForward("setup");
      }
  }

}
