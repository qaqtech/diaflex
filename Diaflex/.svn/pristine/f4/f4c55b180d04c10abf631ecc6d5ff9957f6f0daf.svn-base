package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.MNme;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LotMergeAction extends DispatchAction {
    
    public ActionForward load(ActionMapping am, ActionForm af,
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
          return am.findForward(rtnPg);   
      }else{
          util.updAccessLog(req,res,"LotMerge", "load");
          GenericInterface genericInt  = new GenericImpl();
          LotMergeForm udf = (LotMergeForm)af;
          udf.resetAll();
          ArrayList assortSrchList = genericInt.genricSrch(req,res,"LOTMRG_SRCH","LOTMRG_SRCH");
          info.setGncPrpLst(assortSrchList);
          
          ArrayList empList = getEmp(db);
          udf.setEmpList(empList);
          
          HashMap allPageDtl =(info.getPageDetails() == null) ? new HashMap() : (HashMap)info.getPageDetails();
          HashMap pageDtl = (HashMap)allPageDtl.get("LOT_MERGE");
          if (pageDtl == null || pageDtl.size() == 0) {
              pageDtl = new HashMap();
              pageDtl = util.pagedef("LOT_MERGE");
              allPageDtl.put("LOT_MERGE", pageDtl);
          }
          info.setPageDetails(allPageDtl);
          
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
          util.updAccessLog(req,res,"LotMerge", "loadGrid");
          
       LotMergeForm udf = (LotMergeForm)form;
        HashMap paramsMap = new HashMap();
           HashMap dbmsSysInfo = info.getDmbsInfoLst();
          String lotNo = util.nvl((String)dbmsSysInfo.get("LOT_NO"),"LOT_NO");
            GenericInterface genericInt  = new GenericImpl();
            ArrayList genricSrchLst = genericInt.genricSrch(req,res,"LOTMRG_SRCH","LOTMRG_SRCH");
            info.setGncPrpLst(genricSrchLst);
            String stt = util.nvl((String)udf.getValue("stt"));
          String inStt="LOT_MRG_AV";
          String outStt="MKAV";
          if(stt.indexOf("@")!=-1){
              String[] sttLst = stt.split("@");
              inStt=sttLst[0];
              outStt=sttLst[1];
          }
          String fetchall = util.nvl((String)udf.getValue("fetchall"));
            int ct = db.execUpd("gt insert", "Delete from gt_srch_rslt", new ArrayList());
            String lprpStr="";
          String lprpValStr="";
          if(!fetchall.equals("")){
              FetchResult(db,inStt);
          }else{
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
                  boolean isSelected =false;
                  String lprpVal="";
                  for(int j=0; j < lprpS.size(); j++) {
                  String lSrt = (String)lprpS.get(j);
                  String lVal = (String)lprpV.get(j);    
                  String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                  if(!reqVal1.equals("")){
                  paramsMap.put(lprp + "_" + lVal, reqVal1);
                   isSelected=true;
                    lprpVal=lprpVal+"~"+reqVal1;
                  }
                  }
                  lprpVal=lprpVal.replaceFirst("~", "");
                  if(isSelected && !lprp.equals(lotNo)){
                      lprpStr=lprpStr+"#"+lprp;
                      lprpValStr=lprpValStr+"#"+lprpVal;
                  }
                  isSelected=false;
              }else{
                  ArrayList lprpV = (ArrayList)prp.get(lprp + "V");
                  String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                 String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                  String lprpVal="";
                 if(fldVal2.equals(""))
                     fldVal2=fldVal1;
                   if(!fldVal1.equals("") && !fldVal2.equals("")){
                                  paramsMap.put(lprp+"_1", fldVal1);
                                  paramsMap.put(lprp+"_2", fldVal2);
                         if(lprpTyp.equals("C")){
                      int fromIndex = lprpV.indexOf(fldVal1);
                      int toIndex = lprpV.indexOf(fldVal2);
                      List lprpValLst = lprpV.subList(fromIndex, toIndex);
                      for(int k=0;k<lprpValLst.size();k++){
                          lprpVal=lprpVal+"~"+lprpValLst.get(k);
                      }
                          lprpVal=lprpVal.replaceFirst("~", "");
                        }else if(lprpTyp.equals("T")){
                             lprpVal=fldVal1.replaceAll(",", "~");
                             lprpVal=lprpVal.replaceAll(" ", "");
                         }else{
                             lprpVal=fldVal1+"~"+fldVal2;
                         }
                    if (!lprp.equals(lotNo)){
                      lprpStr=lprpStr+"#"+lprp;
                      lprpValStr=lprpValStr+"#"+lprpVal;
                    }
                  }   
              }            
          }
        lprpStr=lprpStr.replaceFirst("#", "");
        lprpValStr=lprpValStr.replaceFirst("#", "");
        String party = util.nvl((String)udf.getValue("party"));
              if(!party.equals("")){
                  paramsMap.put("MFG_PARTY_1", party);
                  paramsMap.put("MFG_PARTY_2", party);
                  
              }
    if(paramsMap.size()>0){
    paramsMap.put("stt", inStt);
    paramsMap.put("mdl", "MIX_VIEW");
    paramsMap.put("MIX","Y");
    util.genericSrch(paramsMap);
    }}
          req.setAttribute("lprpStr", lprpStr);
          req.setAttribute("lprpValStr", lprpValStr);
       SearchResult(req,session,db);
          udf.setValue("srch", "");
            udf.setValue("fetchall", "");
          udf.setValue("INSTT", inStt);
            udf.setValue("OUTSTT", outStt);
     return am.findForward("load");
        }
    }
    
    public ActionForward save(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"LotMerge", "loadGrid");
         LotMergeForm udf = (LotMergeForm)form;
          ArrayList msgLst = new ArrayList();
          Enumeration reqNme = req.getParameterNames(); 
          HashMap dbmsSysInfo = info.getDmbsInfoLst();
          String lotNo = util.nvl((String)dbmsSysInfo.get("LOT_NO"),"LOT_NO");
          String empIdn =(String)udf.getValue("empIdn");
          String inStt =util.nvl((String)udf.getValue("INSTT"));
          String outStt =util.nvl((String)udf.getValue("OUTSTT"));
          String lprpStr = util.nvl(req.getParameter("lprpStr"));
          String lprpValStr =util.nvl(req.getParameter("lprpValStr"));
         String lotStr="";
          while (reqNme.hasMoreElements()) {
              String paramNm = (String) reqNme.nextElement();
              if(paramNm.indexOf("merge_") > -1) {
                  String val = req.getParameter(paramNm);
                 lotStr=lotStr+"~"+val;
              }
          }
          if(!lotStr.equals("")){
          lotStr=lotStr.replaceFirst("~", "");
          lprpStr=lotNo+"#"+lprpStr;
          lprpValStr=lotStr+"#"+lprpValStr;
          ArrayList params = new ArrayList();
          params.add(lotStr);
          params.add(empIdn);
          params.add(lprpStr);
          params.add(lprpValStr);
          params.add(inStt);
          params.add(outStt);
          ArrayList out = new ArrayList();
          out.add("V");
          String mergeLot= "DP_LOT_MERGE(pLotToMerge => ? ,pEmpIdn => ?, pPrpLst => ? , pValLst => ? ,pInStt => ?,pOutStt => ? , pMsg => ?)";
          CallableStatement cst = db.execCall("mergeLot",mergeLot, params, out);
          String msg = cst.getString(params.size()+1);
          msgLst.add(msg);
            cst.close();
            cst=null;
          }
       req.setAttribute("msgLst", msgLst);
          udf.reset();
          return am.findForward("load");   
      }
    }
    
    
    public void FetchResult(DBMgr db,String stt){
        
       
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn , vnm , pkt_dte, stt , flg , qty , cts , rmk,sk1,cmp,quot ) " + 
        " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , tfl3 ,sk1 ,nvl(cmp,upr) ,upr  "+
        "     from mstk b "+
        " where stt =? and cts > 0  and pkt_ty='MIX' ";
      
       ArrayList ary = new ArrayList();
        ary.add(stt);
       int  ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add("MIX_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
    
    }
    
    public void SearchResult(HttpServletRequest req,HttpSession session,DBMgr db){
      
      
     
        ArrayList viewPrpLst = ASPrprViw(session,db);
        int indexSH = viewPrpLst.indexOf("MIX_SHAPE");
     
            if(indexSH==-1)
                indexSH = viewPrpLst.indexOf("SHAPE");
        indexSH=indexSH+1;  
        int indexLT = viewPrpLst.indexOf("LOTNO");
            if(indexLT==-1)
                indexLT = viewPrpLst.indexOf("MFG_LOT_NO");
        indexLT=indexLT+1;
        ArrayList lotsList = new ArrayList();
        ArrayList pktDtlList= new ArrayList();
        HashMap ttl= new HashMap();
        String shPrp = "prp_00"+indexSH;
        String shSrt = "srt_00"+indexSH;
        
        if(indexSH > 9){
            shPrp = "prp_0"+indexSH;
            shSrt = "srt_0"+indexSH;
        }
            
        String szPrp = "prp_00"+indexLT;
        String szSrt = "srt_00"+indexLT;
        if(indexLT > 9){
            szPrp = "prp_0"+indexLT;
            szSrt = "srt_0"+indexLT;
        }
        HashMap pktTtlMap = new HashMap();
        String gtLotTtls = " select sum(trunc(qty)) qty, to_char(sum(trunc(cts,2)),'99999990.00') cts, "+
                       " trunc(((sum(trunc(cts,2)* quot) / sum(trunc(cts,2))))) avgPrc , "+
                       " to_char(trunc(sum(trunc(cts,2)* quot), 2),'99999990.00') val, "+szPrp+" lotNo  "+
                       " from gt_srch_rslt where cts <> 0 group by "+szPrp+"  order by "+szPrp ;
        ArrayList  rsLst = db.execSqlLst("grpNme", gtLotTtls, new ArrayList());
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
        
        try {
            while (rs.next()) {
                String lotVal = nvl(rs.getString("lotNo"));
                   HashMap pktTtls = new HashMap();
                   pktTtls.put("QTY", nvl(rs.getString("qty")));
                   pktTtls.put("CTS", nvl(rs.getString("cts")));
                   pktTtls.put("AVGPRC", nvl(rs.getString("avgPrc")));
                   pktTtls.put("VAL", nvl(rs.getString("val")));
                   pktTtlMap.put(lotVal, pktTtls);
               
                  lotsList.add(lotVal);
            }
            rs.close();
            stmt.close();
            
       } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
     
        String gtTtls = " select sum(trunc(qty)) qty, to_char(sum(trunc(cts,2)),'99999990.00') cts, "+
                       " trunc(((sum(trunc(cts,2)* quot) / sum(trunc(cts,2))))) avgPrc , "+
                       " to_char(trunc(sum(trunc(cts,2)* quot), 2),'99999990.00') val, "+shPrp+" sh, "+szPrp+" lotNo,"+shSrt+" shSrt ,"+szSrt+" szSrt "+
                       " from gt_srch_rslt where cts <> 0 group by "+shPrp+" , "+szPrp+","+shSrt+","+szSrt+" order by "+szSrt+","+shSrt ;
        rsLst = db.execSqlLst("grpNme", gtTtls, new ArrayList());
        stmt =(PreparedStatement)rsLst.get(0);
        rs =(ResultSet)rsLst.get(1);
       
        try {
            while (rs.next()) {
                String shVal = nvl(rs.getString("sh"));
                String lotVal = nvl(rs.getString("lotNo"));
                   HashMap pktTtls = new HashMap();
                  pktTtls.put("QTY", nvl(rs.getString("qty")));
                   pktTtls.put("CTS", nvl(rs.getString("cts")));
                   pktTtls.put("AVGPRC", nvl(rs.getString("avgPrc")));
                   pktTtls.put("VAL", nvl(rs.getString("val")));
                   pktTtlMap.put(shVal+"_"+lotVal, pktTtls);
               
            }
            rs.close();
            stmt.close();
            
            
            
       
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        String gtGtTtls = " select sum(trunc(qty)) qty, to_char(sum(trunc(cts,2)),'99999990.00') cts, "+
                       " trunc(((sum(trunc(cts,2)* quot) / sum(trunc(cts,2))))) avgPrc , "+
                       " to_char(trunc(sum(trunc(cts,2)* quot), 2),'99999990.00') val "+
                       " from gt_srch_rslt where cts <> 0 ";
        rsLst = db.execSqlLst("grpNme", gtGtTtls, new ArrayList());
        stmt =(PreparedStatement)rsLst.get(0);
        rs =(ResultSet)rsLst.get(1);
        
        try {
            while (rs.next()) {
                   HashMap pktTtls = new HashMap();
                   pktTtls.put("QTY", nvl(rs.getString("qty")));
                   pktTtls.put("CTS", nvl(rs.getString("cts")));
                   pktTtls.put("AVGPRC", nvl(rs.getString("avgPrc")));
                   pktTtls.put("VAL", nvl(rs.getString("val")));
                   pktTtlMap.put("GTTTL", pktTtls);
               
            }
            rs.close();
            stmt.close();
            
            
            
        
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("LOTLIST", lotsList);
        req.setAttribute("pktDtlList", pktDtlList);
        req.setAttribute("PKTTTLMAP", pktTtlMap);
        req.setAttribute("TTL", ttl);
        req.setAttribute("view", "Y");
      
    }
    
    public ArrayList getEmp(DBMgr db) {
        ArrayList ary = new ArrayList();
        ArrayList empList = new ArrayList();
        String empSql = "select nme_idn, nme from nme_v where typ = 'EMPLOYEE' order by nme";
      ArrayList  rsLst = db.execSqlLst("empSql", empSql, ary);
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
        
        try {
            while (rs.next()) {
                MNme emp = new MNme();
                emp.setEmp_idn(rs.getString("nme_idn"));
                emp.setEmp_nme(rs.getString("nme"));
                empList.add(emp);
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }

        return empList;
    }

    
    public ArrayList ASPrprViw( HttpSession session,DBMgr db){
       
        ArrayList asViewPrp = (ArrayList)session.getAttribute("MIX_VIEW");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
             
              ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MIX_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs1 =(ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
              stmt.close();
                session.setAttribute("MIX_VIEW", asViewPrp);
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
                util.updAccessLog(req,res,"Lot Merge", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Lot Merge", "init");
            }
            }
            return rtnPg;
            }

    public LotMergeAction() {
        super();
    }
}
