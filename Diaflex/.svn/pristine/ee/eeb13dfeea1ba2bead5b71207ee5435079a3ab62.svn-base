package ft.com.mixakt;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.GtPktDtl;
import ft.com.dao.boxDet;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import ft.com.lab.LabIssueForm;
import ft.com.lab.LabIssueImpl;
import ft.com.lab.LabIssueInterface;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;

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

public class MixTransferKapuAction  extends DispatchAction {
    
    public MixTransferKapuAction() {
        super();
    }
    
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
          MixTransferKapuForm udf = (MixTransferKapuForm)af;
          udf.resetAll();
          GenericInterface genericInt = new GenericImpl();
          ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MixTransSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MixTransSrch");
          info.setGncPrpLst(assortSrchList);
         
          return am.findForward("load");
      }
   }
    
    public ActionForward fetch(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
            util.updAccessLog(req,res,"Lab Issue", "fetch start");
            MixTransferKapuForm udf = (MixTransferKapuForm)form;
          String trntyp = util.nvl((String)udf.getValue("typ"));
    boolean isGencSrch = false;
    HashMap stockList = new HashMap();
    ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MixTransSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MixTransSrch");
    info.setGncPrpLst(genricSrchLst);
    //      ArrayList genricSrchLst = info.getGncPrpLst();
    HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
    String vnm = util.nvl((String)udf.getValue("vnmLst"));
    
    HashMap paramsMap = new HashMap();
    if(vnm.equals("")){
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
        paramsMap.put("stt", "MX_AV");
        paramsMap.put("mdl", "MIX_VW");
        isGencSrch = true;
        util.genericSrch(paramsMap);
        stockList = SearchResultGT(req,res,vnm,udf);
    }else{
        stockList =FetchResult(req,res,vnm,udf);
    }
    String lstNme = "MIXTRAN_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
    gtMgr.setValue(lstNme+"_SEL",new ArrayList());
    gtMgr.setValue(lstNme, stockList);
    gtMgr.setValue("lstNmeMIXTRN", lstNme);
    if(stockList.size()>0){
        HashMap dtlMap = new HashMap();
        ArrayList selectstkIdnLst = new ArrayList();
        Set<String> keys = stockList.keySet();
               for(String key: keys){
              selectstkIdnLst.add(key);
               }
        dtlMap.put("selIdnLst",selectstkIdnLst);
        dtlMap.put("pktDtl", stockList);
        dtlMap.put("flg", "Z");
        dtlMap.put("CTSDIGIT", "3");
        HashMap ttlMap = util.getTTL(dtlMap);
                  
        gtMgr.setValue(lstNme+"_TTL", ttlMap);
       String boxTyp = "";
        ArrayList boxIsLst = (ArrayList)prp.get("BOX_TYPV");
        ArrayList boxPIsLst = (ArrayList)prp.get("BOX_TYPP");
        ArrayList boxTypList = new ArrayList();
        for(int i=0;i<boxIsLst.size();i++){
            String boxVal = (String)boxIsLst.get(i);
                  if(i==0)
                   boxTyp=boxVal;
                boxDet boxObj = new boxDet();
                String dsc = util.nvl((String)boxPIsLst.get(i));
                boxObj.setBoxDesc(dsc);
                boxObj.setBoxVal(boxVal);
                boxTypList.add(boxObj);
            
         }
        udf.setBoxList(boxTypList);
        
        ArrayList boxIDVLst = (ArrayList)prp.get("BOX_IDV");
        ArrayList boxIDPLst = (ArrayList)prp.get("BOX_IDP");
        ArrayList boxIDList = new ArrayList();
        for(int i=0;i<boxIDVLst.size();i++){
                String boxVal = (String)boxIDVLst.get(i);
                boxDet boxObj = new boxDet();
                String dsc = util.nvl((String)boxIDPLst.get(i));
                boxObj.setBoxDesc(dsc);
                boxObj.setBoxVal(boxVal);
                boxIDList.add(boxObj);
            
         }
        udf.setBoxIDList(boxIDList);
       
        req.setAttribute("typ",trntyp);
    }
    req.setAttribute("view", "Y");
            
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
            MixTransferKapuForm udf = (MixTransferKapuForm)form;
            ArrayList stkIdnList = new ArrayList();
            String typ = (String)udf.getValue("typ");
            Enumeration reqNme = req.getParameterNames();
            while (reqNme.hasMoreElements()) {
                String paramNm = (String) reqNme.nextElement();
               if (paramNm.indexOf("cb_stk_") > -1) {
                    String val = req.getParameter(paramNm);
                    stkIdnList.add(val);
                }
            }
            String smxPkt="";
            String boxTypVal = (String)udf.getValue("box_typ");
            String boxIdVal = util.nvl((String)udf.getValue("box_id"),"0");
            String fnlCts = util.nvl((String)udf.getValue("fnlCts"),"0");
            if(stkIdnList.size()>0){
               String stt ="MKAV";
               if(typ.equals("MIX"))
                   stt="MIX_MRG";
                for(int i=0;i<stkIdnList.size();i++){
                    String stkIdn = (String)stkIdnList.get(i);
                    if(typ.equals("SMX")){
                     boxTypVal = (String)udf.getValue("box_typ_"+stkIdn);
                     boxIdVal = (String)udf.getValue("box_id_"+stkIdn);
                    }
                    
                    String updmstk = "update mstk set stt=? , pkt_ty= ? where idn = ?";
                    ArrayList ary = new ArrayList();
                     ary.add(stt);
                     ary.add(typ);
                    ary.add(stkIdn);
                    int ct = db.execUpd("upmstk", updmstk, ary);
                    if(ct>0){
                    ary = new ArrayList();
                    ary.add(stkIdn);
                    ary.add("BOX_TYP");
                    ary.add(boxTypVal);
                    String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                     ct = db.execCall("stockUpd",stockUpd, ary);
                     
                     String ctsVal = util.nvl((String)udf.getValue("cb_cts_"+stkIdn)).trim();
                     ary = new ArrayList();
                     ary.add(stkIdn);
                     ary.add("CRTWT");
                     ary.add(ctsVal);
                     stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                     ct = db.execCall("stockUpd",stockUpd, ary);
                     
                     if(!boxIdVal.equals("") && !boxIdVal.equals("0")){
                         ary = new ArrayList();
                         ary.add(stkIdn);
                         ary.add("BOX_ID");
                         ary.add(boxIdVal);
                         stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                         ct = db.execCall("stockUpd",stockUpd, ary);
                         }
                       }
                    if(ct>0)
                        smxPkt = smxPkt+stkIdn+",";
                }
                if(typ.equals("MIX")){
                    String procedure="MIX_PKG.ASSORT_MERGE(pBoxTyp => ?,pfnlCts => ? ,pMsg=>?)";
                    ArrayList  ary = new ArrayList();
                    ary.add(boxTypVal);
                    ary.add(fnlCts);
                    ArrayList out = new ArrayList();
                    out.add("V");
                    if(!boxIdVal.equals("") && !boxIdVal.equals("0")){
                        procedure="MIX_PKG.MKT_MERGE(pBoxTyp => ?,pfnlCts => ?,pBoxId => ?,pMsg=>?)";
                        ary.add(boxIdVal);
                    }
                    CallableStatement cst = db.execCall("findIssueId",procedure, ary ,out);
                    String msg = cst.getString(ary.size()+1);
                  cst.close();
                  cst=null;
                    if(msg.equals("ERROR")){
                        String updmstk = "update mstk set stt=? , pkt_ty=? where stt = ?";
                        ary = new ArrayList();
                        ary.add("MX_AV");
                        ary.add("NR");
                        ary.add("MIX_MRG");
                        int ct = db.execUpd("upmstk", updmstk, ary);
                    }else{
                        req.setAttribute("msg", msg);
                    }
                }else{
                    if(smxPkt.length()>3)
                        req.setAttribute("msg", "SMX Packets move Successfully :-"+smxPkt);
                }
            }
          
              udf.reset();
                  
            return am.findForward("load");  
        }
    }
  public HashMap FetchResult(HttpServletRequest req,HttpServletResponse res, String  vnm ,MixTransferKapuForm udf){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      
   
    
      String stt="MX_AV";
      ArrayList ary = null;
      ArrayList delAry=new ArrayList();
      delAry.add("Z");
      String delQ = " Delete from gt_srch_rslt where flg in (?)";
      int ct =db.execUpd(" Del Old Pkts ", delQ, delAry);
      
      String srchRefQ = 
      "    Insert into gt_srch_rslt (pkt_ty, stk_idn , vnm , pkt_dte, stt , flg , qty , cts , rmk,sk1,quot ) " + 
      " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , tfl3 ,sk1,nvl(upr,cmp)  "+
      "     from mstk b "+
      " where stt =? and cts > 0  ";
      
      if(!vnm.equals("")){
               vnm = util.getVnm(vnm);
              srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))" ;
              
        }
      ary = new ArrayList();
      ary.add(stt);
    
      ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
      
      String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
      ary = new ArrayList();
      ary.add("MIX_VW");
      ct = db.execCall(" Srch Prp ", pktPrp, ary);
      
      HashMap stockList = SearchResultGT(req ,res , vnm,udf);
      req.setAttribute("pktList", stockList);
      return stockList;
  }
  
  public HashMap SearchResultGT(HttpServletRequest req ,HttpServletResponse res, String vnmLst,MixTransferKapuForm udf  ){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      HashMap pktList = new HashMap();
      ArrayList vwPrpLst = MixPrpVw(req,res);
      String  srchQ =  " select sk1,stk_idn , pkt_ty, vnm,rap_rte, pkt_dte, stt , qty ,  to_char(cts,'999,990.000') cts , rmk,quot rte,to_char(cts * quot, '99999990.00') amt,to_char(cts * rap_rte, '99999990.00') rapvlu  ";

      

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

      
      String rsltQ = srchQ + " from gt_srch_rslt where flg =? " ;
     
       rsltQ  = rsltQ+ "order by sk1 , cts ";
      
      ArrayList ary = new ArrayList();
      ary.add("Z");
    ArrayList rsLst = db.execSqlLst("search Result", rsltQ, ary);
    PreparedStatement pst = (PreparedStatement)rsLst.get(0);
    ResultSet rs = (ResultSet)rsLst.get(1);
      try {
          while(rs.next()) {
                GtPktDtl pktPrpMap = new GtPktDtl();
              float cts = rs.getFloat("cts");
              String stk_idn = util.nvl(rs.getString("stk_idn"));
             pktPrpMap.setSk1(new BigDecimal(util.nvl(rs.getString("sk1"),"0")));
              String vnm = util.nvl(rs.getString("vnm"));
              pktPrpMap.setValue("vnm",vnm);
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
              pktPrpMap.setValue("stk_idn", stk_idn);
              pktPrpMap.setValue("qty",util.nvl(rs.getString("qty")));
              pktPrpMap.setValue("cts",util.nvl(rs.getString("cts")));
                  pktPrpMap.setValue("USDVAL",util.nvl(rs.getString("rte")));
                  pktPrpMap.setValue("AMT",util.nvl(rs.getString("amt")));
              for(int j=0; j < vwPrpLst.size(); j++){
                   String prp = (String)vwPrpLst.get(j);
                    
                    String fld="prp_";
                    if(j < 9)
                            fld="prp_00"+(j+1);
                    else    
                            fld="prp_0"+(j+1);
                    
                    String val = util.nvl(rs.getString(fld)) ;
                  if(prp.equals("BOX_ID") && !util.nvl(val,"NA").equals("NA")){
                      String[] vals = val.split("_");
                      udf.setValue("box_typ_"+stk_idn, vals[0]);
                      udf.setValue("box_id_"+stk_idn, val);
                  }
                   pktPrpMap.setValue(prp, val);
                }
                 
                  pktList.put(stk_idn, pktPrpMap);
              }
          rs.close();
          pst.close();
      } catch (SQLException sqle) {

          // TODO: Add catch code
          sqle.printStackTrace();
      }
      if(!vnmLst.equals("")){
      vnmLst = util.pktNotFound(vnmLst);
      req.setAttribute("vnmNotFnd", vnmLst);
      }
    pktList =(HashMap)util.sortByComparator(pktList);
      return pktList;
  }
  
    public ArrayList MixPrpVw(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("MIX_VW");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
               
              ArrayList rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MIX_VW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement pst = (PreparedStatement)rsLst.get(0);
              ResultSet rs1 = (ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                pst.close();
                session.setAttribute("MIX_VW", asViewPrp);
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
            util.updAccessLog(req,res,"Price Calc", "init");
            }
            }
            return rtnPg;
            }
}
