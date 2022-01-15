package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

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

public class MergeMemoMixAction extends DispatchAction {
  
    public MergeMemoMixAction() {
        super();
    }
    public ActionForward load(ActionMapping am, ActionForm form, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"MergeMemoMix", "load");
       GenericInterface genericInt  = new GenericImpl();
       MergeMemoMixForm udf = (MergeMemoMixForm)form;
       udf.reset();
        ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MERGE_MIX_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MERGE_MIX_SRCH");
        info.setGncPrpLst(assortSrchList);
          util.updAccessLog(req,res,"MergeMemoMix", "load end");
      return am.findForward("load");
        }
    }
    public ActionForward loadGrid(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"MergeMemoMix", "loadGrid");
       MergeMemoMixForm udf = (MergeMemoMixForm)form;
        HashMap paramsMap = new HashMap();
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MERGE_MIX_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MERGE_MIX_SRCH");
        info.setGncPrpLst(genricSrchLst);
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
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
                  String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                  if(!reqVal1.equals("")){
                  paramsMap.put(lprp + "_" + lVal, reqVal1);
                  }
                  }
              }else{
                  String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                 String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                 if(fldVal2.equals(""))
                     fldVal2=fldVal1;
                  if(lprp.equals("MEMO"))
                      req.setAttribute("memoLst", fldVal2);
                   if(!fldVal1.equals("") && !fldVal2.equals("")){
                                  paramsMap.put(lprp+"_1", fldVal1);
                                  paramsMap.put(lprp+"_2", fldVal2);
                  }   
              }            
          }
 
    if(paramsMap.size()>0){
    paramsMap.put("stt", "MX_AS_FN");
    paramsMap.put("mdl", "MERGE_MIX_VW");
    paramsMap.put("MIX","Y");
    util.genericSrch(paramsMap);
    }
       SearchResult(req,session,db);
          util.updAccessLog(req,res,"MergeMemoMix", "Grid end");
     return am.findForward("load");
        }
    }
    
    public ActionForward marge(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"MergeMemoMix", "merge");
       MergeMemoMixForm udf = (MergeMemoMixForm)form;
            String fldVal1 = util.nvl((String)udf.getValue("MEMO_1"));
            String fldVal2 = util.nvl((String)udf.getValue("MEMO_2"));
            if(fldVal2.equals(""))
               fldVal2=fldVal1;
          String memoLst = util.nvl(req.getParameter("memoLst"));
            memoLst = util.getVnm(memoLst);
            memoLst=memoLst.replaceAll("'", "");
        HashMap prp = info.getPrp();
        ArrayList shValLst = (ArrayList)prp.get("SHAPEV");
          ArrayList szValLst = (ArrayList)prp.get("MIX_SIZEV");
           ArrayList shSrtLst = (ArrayList)prp.get("SHAPES");
          ArrayList szSrtLst = (ArrayList)prp.get("MIX_SIZES");
        ArrayList shSzList = (ArrayList)session.getAttribute("SHSZLIST");
        ArrayList msgLst = new ArrayList();
        for(int i=0;i<shSzList.size();i++){
            String keyStr = (String)shSzList.get(i);
            String isChecked = util.nvl(req.getParameter(keyStr));
            if(isChecked.equals("yes")){
                String[] key = keyStr.split("_");
                   String shSrt = key[0];
                   String szSrt = key[1];
                   String shVal = (String)shValLst.get(shSrtLst.indexOf(shSrt));
                   String szVal = (String)szValLst.get(szSrtLst.indexOf(szSrt));
                   ArrayList  params = new ArrayList();
                   params.add(memoLst);
                   params.add(shVal);
                   params.add(szVal);
                   ArrayList out = new ArrayList();
                   out.add("V");
                   CallableStatement cst = db.execCall("DP_MIX_MEMO_MERGE", "DP_MIX_LOT_MERGE(pLot => ? , pShp => ? , pMixSz => ?, pMsg => ?)", params, out);
                 String msg = cst.getString(params.size()+1);
                 msgLst.add(msg);
              cst.close();
              cst=null;
            }
        }
        req.setAttribute("msgLst", msgLst);
          util.updAccessLog(req,res,"MergeMemoMix", "merge end");
        return am.findForward("load");
        }
    }
    public void SearchResult(HttpServletRequest req,HttpSession session,DBMgr db){
      
      
     
        ArrayList viewPrpLst = ASPrprViw(session,db);
        int indexSH = viewPrpLst.indexOf("SHAPE")+1;
        int indexSZ = viewPrpLst.indexOf("MIX_SIZE")+1;
        int indexMemo = viewPrpLst.indexOf("MEMO")+1;
        ArrayList shSzList = new ArrayList();
        ArrayList pktDtlList= new ArrayList();
        HashMap ttl= new HashMap();
        String shPrp = "prp_00"+indexSH;
        String shSrt = "srt_00"+indexSH;
        
        if(indexSH > 9){
            shPrp = "prp_0"+indexSH;
            shSrt = "srt_0"+indexSH;
        }
            
        String szPrp = "prp_00"+indexSZ;
        String szSrt = "srt_00"+indexSZ;
        String memoPrp = "prp_00"+indexMemo;
        String memoSrt = "srt_00"+indexMemo;
        if(indexSZ > 9){
            szPrp = "prp_00"+indexSZ;
            szSrt = "srt_00"+indexSZ;
        }
        HashMap pktTtlMap = new HashMap();
        String gtTtls = " select to_char(sum(trunc(cts,2)),'99999990.00') cts, "+
                       " trunc(((sum(trunc(cts,2)* cmp) / sum(trunc(cts,2))))) avgPrc , "+
                       " to_char(trunc(sum(trunc(cts,2)* cmp), 2),'99999990.00') val, "+shPrp+" sh, "+szPrp+" mix_size,"+shSrt+" shSrt ,"+szSrt+" szSrt "+
                       " from gt_srch_rslt where cts > 0 group by "+shPrp+" , "+szPrp+","+shSrt+","+szSrt+" order by "+shSrt+","+szSrt+"";
      ArrayList  rsLst = db.execSqlLst("gtTtl", gtTtls, new ArrayList());
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while (rs.next()) {
                String shSrtVal = nvl(rs.getString("shSrt"));
                String szSrtVal = nvl(rs.getString("szSrt"));
                   HashMap pktTtls = new HashMap();
                   pktTtls.put("CTS", nvl(rs.getString("cts")));
                   pktTtls.put("AVGPRC", nvl(rs.getString("avgPrc")));
                   pktTtls.put("VAL", nvl(rs.getString("val")));
                   pktTtlMap.put(rs.getString("shSrt")+"_"+rs.getString("szSrt"), pktTtls);
                if(!shSzList.contains(shSrtVal+"_"+szSrtVal))
                  shSzList.add(shSrtVal+"_"+szSrtVal);
            }
      rs.close();
        stmt.close();
       
        String gtFetch = "select to_char(sum(trunc(cts,2)),'99999990.00') cts,"+
        "trunc(((sum(trunc(cts,2)* cmp) / sum(trunc(cts,2))))) avgPrc ,"+
        "to_char(trunc(sum(trunc(cts,2)* cmp), 2),'99999990.00') val , "+memoPrp+" memo from gt_srch_rslt where cts > 0 group by "+memoPrp+" order by "+memoPrp;
          rsLst = db.execSqlLst("gtRslt", gtFetch, new ArrayList());
          stmt =(PreparedStatement)rsLst.get(0);
          rs =(ResultSet)rsLst.get(1);
        while(rs.next()){
            HashMap dataTable=new HashMap();
            dataTable.put("CTS",nvl(rs.getString("cts")));
            dataTable.put("AVGPRC",nvl(rs.getString("avgPrc")));
            dataTable.put("VAL",nvl(rs.getString("val")));
            dataTable.put("MEMO",nvl(rs.getString("memo")));
            pktDtlList.add(dataTable);
        }
        rs.close();
        stmt.close();
            String gtttl = "Select Trunc(Sum(Trunc(Cts, 2)),2) cts,Trunc(Sum(Cmp*Trunc(Cts,2))/Sum(Trunc(Cts, 2)),0) avg,\n" + 
            "To_Char(Trunc(Sum(Trunc(Cts,2)* Cmp), 2),'9999999990.00') val \n" + 
            "From Gt_Srch_Rslt where  cts > 0 " ;
          rsLst = db.execSqlLst("gtttl", gtttl, new ArrayList());
          stmt =(PreparedStatement)rsLst.get(0);
          rs =(ResultSet)rsLst.get(1);
            while(rs.next()){
                ttl=new HashMap();
                ttl.put("CTS",nvl(rs.getString("cts")));
                ttl.put("AVGPRC",nvl(rs.getString("avg")));
                ttl.put("VAL",nvl(rs.getString("val")));
            } 
        rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("SHSZLIST", shSzList);
        req.setAttribute("pktDtlList", pktDtlList);
        req.setAttribute("PKTTTLMAP", pktTtlMap);
        req.setAttribute("TTL", ttl);
        req.setAttribute("view", "Y");
      
    }
//    public void SearchResult(HttpServletRequest req){
//        ArrayList viewPrpLst = ASPrprViw();
//        int indexSH = viewPrpLst.indexOf("SHAPE")+1;
//        int indexSZ = viewPrpLst.indexOf("MIX_SIZE")+1;
//        HashMap pktDtlMap = new HashMap();
//        ArrayList shSzList = new ArrayList();
//        String shPrp = "prp_00"+indexSH;
//        String shSrt = "srt_00"+indexSH;
//        if(indexSH > 9){
//            shPrp = "prp_0"+indexSH;
//            shSrt = "srt_0"+indexSH;
//        }
//            
//        String szPrp = "prp_00"+indexSZ;
//        String szSrt = "srt_00"+indexSZ;
//        if(indexSZ > 9){
//            szPrp = "prp_00"+indexSZ;
//            szSrt = "srt_00"+indexSZ;
//        }
//        HashMap pktTtlMap = new HashMap();
//        String gtTtls = " select to_char(sum(trunc(cts,2)),'9990.99') cts, "+
//                       " trunc(((sum(trunc(cts,2)* cmp) / sum(trunc(cts,2))))) avgPrc , "+
//                       " trunc(sum(trunc(cts,2)* cmp), 2) val, "+shPrp+" sh, "+szPrp+" mix_size,"+shSrt+" shSrt ,"+szSrt+" szSrt "+
//                       " from gt_srch_rslt where cts <> 0 group by "+shPrp+" , "+szPrp+","+shSrt+","+szSrt+" order by "+shSrt+","+szSrt+"";
//        ResultSet rs = db.execSql("gtTtl", gtTtls, new ArrayList());
//        try {
//            while (rs.next()) {
//                   HashMap pktTtls = new HashMap();
//                   pktTtls.put("CTS", util.nvl(rs.getString("cts")));
//                   pktTtls.put("AVGPRC", util.nvl(rs.getString("avgPrc")));
//                   pktTtls.put("VAL", util.nvl(rs.getString("val")));
//                   pktTtlMap.put(rs.getString("shSrt")+"_"+rs.getString("szSrt"), pktTtls);
//            }
//      
//       
//        String gtFetch = "select stk_idn , quot , cts , "+shSrt+", "+szSrt+"  ";
//        for (int i = 0; i < viewPrpLst.size(); i++) {
//            String fld = "prp_";
//            int j = i + 1;
//            if (j < 10)
//                fld += "00" + j;
//            else if (j < 100)
//                fld += "0" + j;
//            else if (j > 100)
//                fld += j;
//              gtFetch += ", " + fld;
//           
//         }
//        String rsltQ = gtFetch + " from gt_srch_rslt where cts <> 0 order by "+shSrt+" , "+szSrt+"";
//        rs = db.execSql("gtRslt", rsltQ, new ArrayList());
//        while(rs.next()){
//            String shSrtVal = util.nvl(rs.getString(shSrt));
//            String szSrtVal = util.nvl(rs.getString(szSrt));
//            ArrayList pktDtlList = (ArrayList)pktDtlMap.get(shSrtVal+"_"+szSrtVal);
//            if(pktDtlList==null)
//                pktDtlList = new ArrayList();
//            HashMap pktMap = new HashMap();
//            pktMap.put("stkIdn", util.nvl(rs.getString("stk_idn")));
//            pktMap.put("quot", util.nvl(rs.getString("quot")));
//            pktMap.put("cts", util.nvl(rs.getString("cts")));
//            for (int i = 0; i < viewPrpLst.size(); i++) {
//                String prp = (String)viewPrpLst.get(i);
//                String fld="prp_";
//                if(i < 9)
//                        fld="prp_00"+(i+1);
//                else    
//                        fld="prp_0"+(i+1);
//                
//                pktMap.put(prp, util.nvl(rs.getString(fld)));
//            }
//            pktDtlList.add(pktMap);
//            pktDtlMap.put(shSrtVal+"_"+szSrtVal, pktDtlList);
//            if(!shSzList.contains(shSrtVal+"_"+szSrtVal))
//              shSzList.add(shSrtVal+"_"+szSrtVal);
//        }
//            
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        session.setAttribute("SHSZLIST", shSzList);
//        req.setAttribute("PKTDTLMAP", pktDtlMap);
//        req.setAttribute("PKTTTLMAP", pktTtlMap);
//        req.setAttribute("view", "Y");
//    }
//    public ArrayList MIXGenricSrch(){
//        
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("MERGE_MIX_SRCH");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp,flg  from rep_prp where mdl = 'MERGE_MIX_SRCH' and flg in ('Y','S','M') order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//
//                session.setAttribute("MERGE_MIX_SRCH", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
//    
    public ArrayList ASPrprViw( HttpSession session,DBMgr db){
       
        ArrayList asViewPrp = (ArrayList)session.getAttribute("MERGE_MIX_VW");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
             
              ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MERGE_MIX_VW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs1 =(ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                stmt.close();
                session.setAttribute("MERGE_MIX_VW", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
    
  public String nvl(String pVal) {
      return nvl(pVal, "");
  }
  
  public String nvl(String pVal, String rVal) {

      String val = pVal ;
      if(pVal == null)
          val = rVal;
      else if(pVal.equals(""))
        val = rVal;
      return val;

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
              util.updAccessLog(req,res,"MergeMemoMix", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"MergeMemoMix", "init");
          }
          }
          return rtnPg;
       }
}
