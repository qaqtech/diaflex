package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.PktDtl;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.sql.CallableStatement;
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

public class TransferToMKTMIXAction extends DispatchAction {
   
    public TransferToMKTMIXAction() {
        super();
    }
    
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             util.getOpenCursorConnection(db,util,info);
           util.updAccessLog(req,res,"TransferToMKTMIX", "load");
         TransferToMKTMIXFrom tansferToMktform = (TransferToMKTMIXFrom)af;
         tansferToMktform.resetAll();
           GenericInterface genericInt =new  GenericImpl();
             ArrayList trfSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_TRF_MKT_MIX_IS") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_TRF_MKT_MIX_IS");
         info.setGncPrpLst(trfSrchList);
//         int ct = db.execCall("delete gt","delete from gt_srch_rslt", new ArrayList());
//          String mixPkg = "MIX_PKG.POP_TRF_TO_MKTG_MIX(pMdl => ?)";
//         ArrayList ary = new ArrayList();
//         ary.add("TRF_MKT_MIX");
//         ct = db.execCall("mixPkg", mixPkg, ary);
//         ArrayList prpList = new ArrayList();
//         String gtSh = "select distinct prp_001 , srt_001 from gt_srch_rslt order by srt_001";
//         ResultSet rs = db.execSql("gtSh", gtSh, new ArrayList());
//         while(rs.next()){
//             PktDtl pktDtl = new PktDtl();
//             pktDtl.setPrp(rs.getString("prp_001"));
//             prpList.add(pktDtl);
//         }
//         tansferToMktform.setShapeList(prpList);
            util.updAccessLog(req,res,"TransferToMKTMIX", "load end");
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
        util.updAccessLog(req,res,"TransferToMKTMIX", "fetch");
      TransferToMKTMIXFrom tansferToMktform = (TransferToMKTMIXFrom)af;
      HashMap prp = info.getPrp();
      HashMap mprp = info.getMprp();
      HashMap paramsMap = new HashMap();
      ArrayList prpValSize=null;
      ArrayList prpSrtSize = null;
          ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_TRF_MKT_MIX_IS") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_TRF_MKT_MIX_IS");
      info.setGncPrpLst(genricSrchLst);
//      ArrayList genricSrchLst = info.getGncPrpLst();
      ArrayList prpPrtSz=null;
      ArrayList prpValSz=null;
      ArrayList prpSrtSz = null;
      ArrayList prpPrtClr=null;
      ArrayList prpValClr=null;
      ArrayList prpSrtClr = null;
      prpPrtSz = (ArrayList)prp.get("MIX_SIZE"+"P");
      prpSrtSz = (ArrayList)prp.get("MIX_SIZE"+"S");
      prpValSz = (ArrayList)prp.get("MIX_SIZE"+"V");
      
      prpPrtClr=new ArrayList();
      prpValClr=new ArrayList();
      prpSrtClr =new ArrayList();
      prpPrtClr = (ArrayList)prp.get("MIX_CLARITY"+"P");
      prpSrtClr = (ArrayList)prp.get("MIX_CLARITY"+"S");
      prpValClr = (ArrayList)prp.get("MIX_CLARITY"+"V");
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
      
      paramsMap.put("stt", "MKAV");
      paramsMap.put("DELGT", "NO");
      util.genericSrch(paramsMap);
      
      db.execUpd("delete gt", "delete from gt_srch_rslt where cts=0 ", new ArrayList());
      String fldSh = util.nvl((String)tansferToMktform.getValue("SHAPE_1"));
      prpSrtSize = (ArrayList)prp.get("SHAPE"+"S");
      prpValSize = (ArrayList)prp.get("SHAPE"+"V");
      fldSh=(String)prpValSize.get(prpSrtSize.indexOf(fldSh));

     if(fldSh.equals(""))
         fldSh="ROUND";
   
      ArrayList trfMktMix = TrfMKTMix(session,db);
      int shInx = trfMktMix.indexOf("SHAPE")+1;
      String shPrp = "prp_00"+shInx;
      int szInx = trfMktMix.indexOf("MIX_SIZE")+1;
      String szPrp = "prp_00"+szInx;
      String szSrt = "srt_00"+szInx;
      int clrInx = trfMktMix.indexOf("MIX_CLARITY")+1;
      String clrPrp = "prp_00"+clrInx;
      String clrSrt = "srt_00"+clrInx;
      int dpInx = trfMktMix.indexOf("DP")+1;
      String dpPrp = "prp_00"+dpInx;
      
      int deptInx = trfMktMix.indexOf("DEPT")+1;
      String deptPrp = "prp_00"+deptInx;
      HashMap ttCtsMap = new HashMap();
      String ttlsQry = "select sum(cts) ttlCts , decode(stt, 'MX_FN', 'NEW', 'LIVE') stt ,Trunc(((Sum(Trunc(Cts,2)*Nvl(cmp,quot)) / Sum(Cts)))) avg_rte, "+shPrp+" sh , "+szPrp+" sz , "+clrPrp+" clr , decode(instr("+deptPrp+", '18-96'),1,nvl("+dpPrp+",0), 0)  dp from gt_srch_rslt " +
          "where cts > 0 group by Decode(Stt, 'MX_FN', 'NEW', 'LIVE') , "+shPrp+" , "+szPrp+" , "+clrPrp+" , decode(instr("+deptPrp+", '18-96'),1,nvl("+dpPrp+",0), 0) " ;
        ArrayList  rsLst = db.execSqlLst("ttlsQty", ttlsQry, new ArrayList());
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
      ArrayList shList = new ArrayList();
      ArrayList szList = new ArrayList();
      ArrayList clrList = new ArrayList();
      ArrayList szSrtList = new ArrayList();
      ArrayList clrSrtrList = new ArrayList();
      while(rs.next()){
          String ttlcts = util.nvl(rs.getString("ttlCts"));
          String dspStt = util.nvl(rs.getString("stt"));
          String sh = util.nvl(rs.getString("sh"));
          fldSh = sh;
          String sz = util.nvl(rs.getString("sz"));
          String clr = util.nvl(rs.getString("clr"));
          String dp = util.nvl(rs.getString("dp"),"0").trim();
          if(dp.indexOf("65")!=-1)
              dp="65";
          else
              dp="0";
          ttCtsMap.put(sh+"_"+sz+"_"+clr+"_"+dp+"_"+dspStt, ttlcts);
          ttCtsMap.put(sh+"_"+sz+"_"+clr+"_"+dp+"_"+dspStt+"_RTE", util.nvl(rs.getString("avg_rte")));
          if(!shList.contains(sh))
              shList.add(sh);
//          if(!szList.contains(sz))
//              szList.add(sz);
//          if(!clrList.contains(clr))
//              clrList.add(clr);
      }
      rs.close();
        stmt.close();
      String grttlsQry = "select sum(cts) ttlCts , decode(stt, 'MX_FN', 'NEW', 'LIVE') stt ,Trunc(((Sum(Trunc(Cts,2)*Nvl(cmp,quot)) / Sum(Cts)))) avg_rte, "+szPrp+" sz, decode(instr("+deptPrp+", '18-96'),1,nvl("+dpPrp+",0), 0)  dp from gt_srch_rslt " +
          "where cts > 0 group by Decode(Stt, 'MX_FN', 'NEW', 'LIVE'), "+szPrp+" , decode(instr("+deptPrp+", '18-96'),1,nvl("+dpPrp+",0), 0) " ;
        rsLst = db.execSqlLst("Grand Ttl", grttlsQry, new ArrayList());
          stmt =(PreparedStatement)rsLst.get(0);
         rs =(ResultSet)rsLst.get(1);
      while(rs.next()){
          String ttlcts = util.nvl(rs.getString("ttlCts"));
          String dspStt = util.nvl(rs.getString("stt"));
          String sz = util.nvl(rs.getString("sz"));
          String dp = util.nvl(rs.getString("dp"),"0");
          if(dp.indexOf("65")!=-1)
              dp="65";
          else
              dp="0";
          ttCtsMap.put(sz+"_"+dp+"_"+dspStt+"_TTL", ttlcts);
          ttCtsMap.put(sz+"_"+dp+"_"+dspStt+"_RTE_TTL", util.nvl(rs.getString("avg_rte")));
      }
      rs.close();
        stmt.close();
      String szQ="select distinct "+szPrp+" szPrp,"+szSrt+" szsrt from gt_srch_rslt where cts > 0 order by "+szSrt;
        rsLst = db.execSqlLst("szQ", szQ, new ArrayList());
          stmt =(PreparedStatement)rsLst.get(0);
         rs =(ResultSet)rsLst.get(1);
      while(rs.next()){
          if(!util.nvl(rs.getString("szPrp")).equals("")){
          szSrtList.add(util.nvl(rs.getString("szsrt")));
          szList.add(util.nvl(rs.getString("szPrp"))); 
          }
      }
      rs.close();
        stmt.close();
      String clrQ="select distinct "+clrPrp+" clrPrp,"+clrSrt+" clrsrt from gt_srch_rslt where cts > 0 order by "+clrSrt;
        rsLst = db.execSqlLst("clrQ", clrQ, new ArrayList());
          stmt =(PreparedStatement)rsLst.get(0);
         rs =(ResultSet)rsLst.get(1);
      while(rs.next()){
        if(!util.nvl(rs.getString("clrPrp")).equals("")){
          clrSrtrList.add(util.nvl(rs.getString("clrsrt")));
          clrList.add(util.nvl(rs.getString("clrPrp")));
        }
      }
      rs.close();
        stmt.close();
      session.setAttribute("ttCtsMap", ttCtsMap);
      session.setAttribute("shList", shList);
      session.setAttribute("szList", szList);
      session.setAttribute("clrList", clrList);
      session.setAttribute("clrSrtList", clrSrtrList);
      session.setAttribute("szSrtList", szSrtList);
      tansferToMktform.resetAll();
      tansferToMktform.setValue("shVal",fldSh);
      if(ttCtsMap!=null && ttCtsMap.size()>0)
     req.setAttribute("View", "Y"); 
        util.updAccessLog(req,res,"TransferToMKTMIX", "fetch end");
      return am.findForward("load");
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
          util.updAccessLog(req,res,"TransferToMKTMIX", "save");
        TransferToMKTMIXFrom tansferToMktform = (TransferToMKTMIXFrom)af;
      
        ArrayList szList = (ArrayList)session.getAttribute("szList");
        ArrayList clrList = (ArrayList)session.getAttribute("clrList");
        HashMap ttlCtsMap = (HashMap)session.getAttribute("ttCtsMap");
        ArrayList szSrtList = (ArrayList)session.getAttribute("szSrtList");
           ArrayList clrSrtList = (ArrayList)session.getAttribute("clrSrtList");
        ArrayList msgLst = new ArrayList();
        String sh = util.nvl((String)tansferToMktform.getValue("shVal"));
        HashMap prp = info.getPrp();
        ArrayList szPrtLst = (ArrayList)prp.get("MIX_SIZEP2");
        ArrayList szValLst = (ArrayList)prp.get("MIX_SIZEV");
        ArrayList szSrtLst = (ArrayList)prp.get("MIX_SIZES");
        for(int j=0;j<clrList.size();j++){
                String clrVal = (String)clrList.get(j);
           for(int i=0;i<szList.size();i++){
               ArrayList dpLst = new ArrayList();
               dpLst.add("0");
                String szVal = (String)szList.get(i);
               String clrFldVal = util.nvl((String)tansferToMktform.getValue(clrVal));
                String szFldVal = util.nvl((String)tansferToMktform.getValue(szVal));
                String szSrt = util.nvl((String)szSrtList.get(i));
                 String isDp = (String)szPrtLst.get(szSrtLst.indexOf(szSrt));
                 if(isDp.equals("DP"))
                     dpLst.add("65");
               if(!clrFldVal.equals("") && !szFldVal.equals("")){
                   for(int k=0;k<dpLst.size();k++){
                    String dpVal = (String)dpLst.get(k);
                    String ctsVal =util.nvl((String)ttlCtsMap.get(sh+"_"+szVal+"_"+clrVal+"_"+dpVal+"_NEW"));
                     if(!ctsVal.equals("0")&&!ctsVal.equals("") ){
                    ArrayList ary = new ArrayList();
                    ary.add(sh);
                    ary.add(szVal);
                    ary.add(clrVal);
                    ary.add(dpVal);
                    ary.add(ctsVal);
                    ArrayList out = new ArrayList();
                    out.add("V");
                   String mixPkg = "MIX_PKG.TRF_TO_MKTG(pShp => ?, pMixSz => ?, pMixClr => ?, pDp => ?,  pCts => ?, pMsg => ?) "; 
                  CallableStatement cts = db.execCall("mixPkg", mixPkg, ary, out);
                 String msg = util.nvl(cts.getString(ary.size()+1)) +"Transfer successfully";
                   msgLst.add(msg);
                       }
                   }
               }
            }
            
        }
       
     
        req.setAttribute("msg", msgLst);
          util.updAccessLog(req,res,"TransferToMKTMIX", "save end");
      return load(am, af, req, res);
        }
    }
    public ArrayList TrfMKTMix(HttpSession session , DBMgr db){
       
        ArrayList asViewPrp = (ArrayList)session.getAttribute("TRFMKTMIXLst");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
            
              ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'TRF_MKT_MIX' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs1 =(ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                stmt.close();
                session.setAttribute("TRFMKTMIXLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
    
//    public ArrayList MIXGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("TRF_MKT_MIX_IS");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp,flg  from rep_prp where mdl = 'TRF_MKT_MIX_IS' and flg in ('Y','S','M') order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                 rs1.close();
//                session.setAttribute("TRF_MKT_MIX_IS", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
    
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
       util.updAccessLog(req,res,"Transfer MTK MIX", "Unauthorized Access");
       else
   util.updAccessLog(req,res,"Transfer MTK MIX", "init");
   }
   }
   return rtnPg;
   }
}
