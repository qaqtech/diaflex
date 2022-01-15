package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.IOException;

import java.math.BigDecimal;

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

public class MixToSingleAction extends DispatchAction {
  
    public MixToSingleAction() {
        super();
    }

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
            util.getOpenCursorConnection(db,util,info);
          util.updAccessLog(req,res,"MixToSingle", "load");
        MixToSingleForm udf = (MixToSingleForm)af;
          GenericInterface genericInt = new GenericImpl();
        udf.resetAll();
            ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXSIG_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXSIG_SRCH");
        info.setGncPrpLst(assortSrchList);
//        ArrayList assortSrchList = MIXGenricSrch(req, res);
//        info.setGncPrpLst(assortSrchList);
        ArrayList mixtoSingleList =
            (session.getAttribute("mixtoSinglesttList") == null) ?
            new ArrayList() :
            (ArrayList)session.getAttribute("mixtoSinglesttList");
        if (mixtoSingleList.size() == 0) {
            mixsttList(db, session);
        }
          util.updAccessLog(req,res,"MixToSingle", "load End");
        return am.findForward("load");
        }
    }

    public ActionForward SplitPkt(ActionMapping am, ActionForm af,
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
          util.updAccessLog(req,res,"MixToSingle", "splitPkt");
        MixToSingleForm udf = (MixToSingleForm)af;
          String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
        NewPktPrprViw(db, session);
        String mstkIdn = util.nvl((String)req.getParameter("mstkIdn"));
        String vnm = util.nvl((String)req.getParameter("vnm"));
        String count = util.nvl((String)req.getParameter("COUNT"));
          if(count.equals(""))
              count="10";
        int countInt = Integer.parseInt(count);
        String stt = "";
        ArrayList ary = null;
        String cts = "";
          String qty="";
        if (mstkIdn.equals(""))
            mstkIdn = util.nvl((String)udf.getValue("mstkIdn"));
        if (vnm.equals(""))
            vnm = util.nvl((String)udf.getValue("vnm"));
        ary = new ArrayList();
        String getctsQ = " select qty-nvl(qty_iss,0) qty, to_char(cts-nvl(cts_iss,0),'999990.90') cts from mstk where idn=?";
        ary.add(mstkIdn);
          ArrayList  rsLst = db.execSqlLst("Get Cts", getctsQ, ary);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
        if (rs.next()) {
            cts = util.nvl((String)rs.getString("cts")).trim();
            qty = util.nvl((String)rs.getString("qty")).trim();
        }
            rs.close();
          stmt.close();
            udf.resetAll();
        if(cnt.indexOf("re")!=-1){
          for(int i=0;i<countInt;i++){
              String getMaxVnm ="select df_vnm_seq.nextval from dual";
              
                  rsLst = db.execSqlLst("Get Cts", getMaxVnm, new ArrayList());
                stmt =(PreparedStatement)rsLst.get(0);
                 rs =(ResultSet)rsLst.get(1);
              if (rs.next()) {
                  String vnmSeq = rs.getString(1);
                  udf.setValue("VNM_"+i, vnmSeq);
              }
              rs.close();
              stmt.close();
              }
        }
      
        udf.setValue("mstkIdn", mstkIdn);
        udf.setValue("vnm", vnm);
        udf.setValue("cts", cts);
        udf.setValue("qty", qty);
        if (!cts.equals("") && !cts.equals("0")) {
            req.setAttribute("view", "Y");
        }
          util.updAccessLog(req,res,"MixToSingle", "splitpkt end");
        return am.findForward("popup");
        }
    }

    public ActionForward genPkt(ActionMapping am, ActionForm af,
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
          util.updAccessLog(req,res,"MixToSingle", "genPkt");
        MixToSingleForm udf = (MixToSingleForm)af;
        ArrayList vwPrpLst = (ArrayList)session.getAttribute("NewPktViewLst");
        int rowcount = Integer.parseInt((String)udf.getValue("rowcount"));
        String mstkIdn = util.nvl((String)udf.getValue("mstkIdn")).trim();
        String vnm = util.nvl((String)udf.getValue("vnm")).trim();
            String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));

            float calcts = 0;
              int calQty = 0;
            int ct=0;
           ArrayList  ary = new ArrayList();
            String getctsQ = " select nvl(qty,0) qty, nvl(cts,0) cts from mstk where idn=?";
            ary.add(mstkIdn);
          ArrayList  rsLst = db.execSqlLst("Get Cts", getctsQ, ary);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
            if (rs.next()) {
                calcts = rs.getFloat("cts");
                calQty = rs.getInt("qty");
            }
          rs.close();
          stmt.close();
            
         
        
        ary = new ArrayList();
        long mstkIdnNew = 0;
        long mstkIdnExists = 0;
          String errMsg="";
          String sccMsg="";
        for (int z = 0; z <rowcount; z++) {
            String vnmusr = util.nvl((String)udf.getValue("VNM_" + z));
            String usrcts = util.nvl((String)udf.getValue("CRTWT_" + z));
            String genpkt = util.nvl((String)udf.getValue("PKT_" + z));
            String stt = util.nvl((String)udf.getValue("STT_" + z));
            String pktIdn = util.nvl((String)udf.getValue("PKTIDN_" + z), "0");
            if (pktIdn.equals(""))
                pktIdn = "0";
            mstkIdnExists =Long.parseLong(pktIdn);
            if ( !usrcts.equals("") && !genpkt.equals("")) {
                if (genpkt.equals("Y")) {
                    String insMst =
                        "MIX_PKG.GEN_PKT(pStt => ?,pQty => ? ,pPktRt => ?, pPktTyp => ?,pIdn =>?,pVnm =>?)";
                    if(!vnmusr.equals(""))
                        insMst ="MIX_PKG.GEN_PKT(pStt => ?,pQty => ? ,pPktRt => ?, pPktTyp => ?,pUsrVnm =>?,pIdn =>?,pVnm =>?)";
                                           
                    ary = new ArrayList();
                    ary.add("LB_AV");
                    ary.add("1");
                    ary.add(mstkIdn);
                    ary.add("NR");
                  if(!vnmusr.equals(""))
                    ary.add(vnmusr);
                    ArrayList out = new ArrayList();
                    out.add("I");
                    out.add("V");
                    

                    CallableStatement cst =
                        db.execCall("findMstkId", insMst, ary, out);
                    mstkIdnNew = cst.getLong(ary.size() + 1);
                    vnmusr = cst.getString(ary.size() + 2);
                  cst.close();
                  cst=null;
                        if(mstkIdnNew>0){
                    ary = new ArrayList();
                    ary.add(String.valueOf(mstkIdnNew));
                    ary.add("MIX_SNGL");
                    ary.add("Y");
                    String stockUpd =
                        "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                    db.execCallDir("update stk Dtl", stockUpd, ary);

                    ary = new ArrayList();
                    ary.add(String.valueOf(mstkIdnNew));
                    ary.add("GRAPH");
                    ary.add("1");
                    db.execCallDir("update stk Dtl", stockUpd, ary);
                     sccMsg=sccMsg+","+vnmusr;
                    
                    }else{
                     errMsg=errMsg+","+vnmusr;       
                    }
                    //                udf.setValue("VNM_"+z, vnmusr);
                } else {
                    String updQ =
                        "update mstk set stt=?,pkt_ty=? where idn=? ";
                    ary = new ArrayList();
                    ary.add("LB_AV");
                    ary.add("NR");
                    ary.add(String.valueOf(mstkIdnExists));
                     ct = db.execUpd("sql", updQ, ary);
                    mstkIdnNew = mstkIdnExists;
                    if (stt.equals("AS_MRG") || stt.equals("POST_MRG")) {
                        String stockUpd =
                            "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                        ary = new ArrayList();
                        ary.add(String.valueOf(mstkIdnNew));
                        ary.add("MIX_SNGL");
                        ary.add("Y");
                        db.execCallDir("update stk Dtl", stockUpd, ary);

                        ary = new ArrayList();
                        ary.add(String.valueOf(mstkIdnNew));
                        ary.add("GRAPH");
                        ary.add("1");
                        db.execCallDir("update stk Dtl", stockUpd, ary);
                    }
                }
                if(mstkIdnNew>0){
                for (int i = 0; i < vwPrpLst.size(); i++) {
                    String lprp = (String)vwPrpLst.get(i);
                    String val =
                        util.nvl((String)udf.getValue(lprp + "_" + z));
                    if ((val != null) && (val.length() > 0)) {
                        //                 udf.setValue(lprp+"_"+z, val);
                        ary = new ArrayList();
                        ary.add(String.valueOf(mstkIdnNew));
                        ary.add(lprp);
                        ary.add(val);
                        String stockUpd =
                            "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                        db.execCallDir("update stk Dtl", stockUpd, ary);
                    }


                }
                
                String updSz = " sz_chg(pktId => ?) ";
                ary = new ArrayList();
                ary.add(String.valueOf(mstkIdnNew));
                db.execCall(" upd prc ", updSz, ary);
                String stockUpd =
                    "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
                String dfltmfg_cts="",dfltmix_rte="";
                String sqlQ="select mc.num mfg_cts,fa.num fa_pri\n" + 
                "from\n" + 
                "mstk m,stk_dtl mc,stk_dtl fa\n" + 
                "where \n" + 
                "m.idn=mc.mstk_idn and mc.mprp='CRTWT' and mc.grp=1 and\n" + 
                "m.idn=fa.mstk_idn and fa.mprp='FA_PRI' and fa.grp=1 and m.idn=?";
                ary = new ArrayList();
                ary.add(String.valueOf(mstkIdnNew));
               rsLst = db.execSqlLst("mem", sqlQ,ary);
                stmt =(PreparedStatement)rsLst.get(0);
              rs =(ResultSet)rsLst.get(1);
                while(rs.next()){
                    dfltmfg_cts=util.nvl(rs.getString("mfg_cts"));
                    dfltmix_rte=util.nvl(rs.getString("fa_pri"));
                }
                rs.close();
                stmt.close();
                if(!dfltmfg_cts.equals("")){
                    ary = new ArrayList();
                    ary.add(String.valueOf(mstkIdnNew));
                    ary.add("MFG_CTS");
                    ary.add(dfltmfg_cts);
                    db.execCallDir("update stk Dtl", stockUpd, ary);
                }
                if(!dfltmix_rte.equals("")){
                    ary = new ArrayList();
                    ary.add(String.valueOf(mstkIdnNew));
                    ary.add("MIX_RTE");
                    ary.add(dfltmix_rte);
                    db.execCallDir("update stk Dtl", stockUpd, ary);
                }
                String dept =
                    "select stk_crt_pkg.get_crt(?, 'DEPT_GRP','DEPT') dept from dual";
                ary = new ArrayList();
                ary.add(String.valueOf(mstkIdnNew));
              rsLst = db.execSqlLst("dept", dept, ary);
               stmt =(PreparedStatement)rsLst.get(0);
              rs =(ResultSet)rsLst.get(1);
                if (rs.next()) {
                    String deptVal = util.nvl(rs.getString("dept"));
                    if (!deptVal.equals("")) {
                        ary = new ArrayList();
                        ary.add(String.valueOf(mstkIdnNew));
                        ary.add("DEPT");
                        ary.add(deptVal);
                        db.execCallDir("update stk Dtl", stockUpd, ary);
                    }
                    if (deptVal.equals("96-UP-GIA")) {
                        String updQ = "update mstk set stt=? where idn=? ";
                        ary = new ArrayList();
                        ary.add("AS_AV");
                        ary.add(String.valueOf(mstkIdnNew));
                         ct = db.execUpd("sql", updQ, ary);
                    }
                }
                rs.close();
                stmt.close();
                ary = new ArrayList();
                   ary.add(String.valueOf(mstkIdnNew));
                db.execCall("stk_rap_upd", "stk_rap_upd(pIdn => ?)", ary);
                
               if(cnt.indexOf("re")!=-1){
                    ary = new ArrayList();
                       ary.add(String.valueOf(mstkIdnNew));
                       ary.add("CP");
                       ary.add("0");
                    db.execCallDir("update stk Dtl", stockUpd, ary);
                String cpUpdate=" Update stk_dtl set num = (select rap_rte*.20 from mstk where idn = ? )\n" +
                " where grp = 1 and mprp = 'CP' and mstk_idn = ? ";
                   ary = new ArrayList();
                   ary.add(String.valueOf(mstkIdnNew));
                   ary.add(String.valueOf(mstkIdnNew));
                   db.execCallDir("update stk Dtl", cpUpdate, ary);
                   
                   
                }
                ary = new ArrayList();
                ary.add(String.valueOf(mstkIdnNew));
                 ct = db.execCall("stk1", "STK_SRT(Pid => ? )", ary);
                calcts = calcts - Float.parseFloat(usrcts);
                calQty = calQty - 1;
                
                String insertSql= "INSERT INTO MIX_TRF_LOG(LOG_DTE, FRM_IDN, TO_IDN, QTY, CTS,UPR,TYP,RMK,UNM)"+
                                   "VALUES(SYSDATE, ? ,?, ?, ?,?,?,?, PACK_VAR.GET_USR)";
                
                 ary = new ArrayList();
                 ary.add(mstkIdn);
                 ary.add(String.valueOf(mstkIdnNew));
                 ary.add(String.valueOf(1));
                 ary.add(usrcts);
                 ary.add(dfltmix_rte);
                 ary.add("SNGL");
                 ary.add("MIX TO SINGLE");
                 ct = db.execUpd("log insert", insertSql, ary);

            }
                
                }
        }

            String updQ =
                       "update stk_dtl set num=? where mprp='CRTWT' and grp=1 and mstk_idn=?";
        ary = new ArrayList();
        ary.add(String.valueOf(calcts));
        ary.add(mstkIdn);
         ct = db.execUpd("sql", updQ, ary);
          if(calQty < 0)
              calQty=0;
       updQ ="update mstk set qty=? ,cts=? where idn=?";
        ary = new ArrayList();
        ary.add(String.valueOf(calQty));
        ary.add(String.valueOf(calcts));
        ary.add(mstkIdn);
         ct = db.execUpd("sql", updQ, ary);
          
            getctsQ = " select qty-nvl(qty_iss,0) qty, cts-nvl(cts_iss,0) cts from mstk where idn=?";
            ary = new ArrayList();
            ary.add(mstkIdn);
           rsLst = db.execSqlLst("Get Cts", getctsQ, ary);
         stmt =(PreparedStatement)rsLst.get(0);
          rs =(ResultSet)rsLst.get(1);
            if (rs.next()) {
                calcts = rs.getFloat("cts");
                calQty = rs.getInt("qty");
            }
                rs.close();
          stmt.close();
          
          
          
            updQ ="update gt_srch_rslt set qty=? , cts = ? where stk_idn=?";
            ary = new ArrayList();
            ary.add(String.valueOf(calQty));
            ary.add(String.valueOf(calcts));
            
            ary.add(mstkIdn);
            ct = db.execUpd("sql", updQ, ary);
          String msg="";
        if(!sccMsg.equals(""))
            msg=sccMsg+" Generation Done..";
        if(!errMsg.equals(""))
            msg=msg+" "+errMsg+" Generation Fail..";
        udf.setValue("mstkIdn", mstkIdn);
        udf.setValue("vnm", vnm);
        udf.setValue("cts", calcts);
        req.setAttribute("msg", msg);
          util.updAccessLog(req,res,"MixToSingle", "genPkt end");
            return am.findForward("popup");
        }
    }

    public ActionForward fetch(ActionMapping am, ActionForm af,
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
          util.updAccessLog(req,res,"MixToSingle", "fetch");
        MixToSingleForm udf = (MixToSingleForm)af;
        db.execUpd("delete gt", "delete from gt_srch_rslt", new ArrayList());
        HashMap paramsMap = new HashMap();
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXSIG_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXSIG_SRCH");
        info.setGncPrpLst(genricSrchLst);
//        ArrayList genricSrchLst = info.getGncPrpLst();
          String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        String stt = util.nvl((String)udf.getValue("stt"));
            String vnm = util.nvl((String)udf.getValue("vnmLst"));
        for (int i = 0; i < genricSrchLst.size(); i++) {
            ArrayList srchPrp = (ArrayList)genricSrchLst.get(i);
            String lprp = (String)srchPrp.get(0);
            String flg = (String)srchPrp.get(1);
            String prpSrt = lprp;
                if(flg.equals("M") || flg.equals("CTA")) {
                    ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                    ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                    ArrayList lprpP = (ArrayList)prp.get(prpSrt + "P");
                    if(flg.equals("M")) {
                    for(int j=0; j < lprpS.size(); j++) {
                    String lSrt = (String)lprpS.get(j);
                    String lVal = (String)lprpV.get(j);    
                    String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                    if(!reqVal1.equals("")){
                    paramsMap.put(lprp + "_" + lVal, reqVal1);
                    } 
                    }
                    }else if(flg.equals("CTA")){
                        String reqVal1 = util.nvl((String)udf.getValue(lprp + "_1"),"");
                        if(!reqVal1.equals("")){
                        ArrayList fmtVal = util.getStrToArr(reqVal1);
                        for(int j=0; j < lprpS.size(); j++) {
                        String lSrt = (String)lprpS.get(j);
                        String lVal = (String)lprpV.get(j);   
                        String lprt = (String)lprpP.get(j);
                        if(fmtVal.contains(lVal) || fmtVal.contains(lprt)){
                        reqVal1 = lVal;
                        paramsMap.put(lprp + "_" + lVal, reqVal1);
                        }
                        } 
                        }        
                    }
                } else {
                String fldVal1 = util.nvl((String)udf.getValue(lprp + "_1"));
                String fldVal2 = util.nvl((String)udf.getValue(lprp + "_2"));
                if (fldVal2.equals(""))
                    fldVal2 = fldVal1;
                if (!fldVal1.equals("") && !fldVal2.equals("")) {
                    paramsMap.put(lprp + "_1", fldVal1);
                    paramsMap.put(lprp + "_2", fldVal2);
                }
            }
        }
        udf.resetAll();
        if (paramsMap.size() > 0) {
            if (!stt.equals("MKAV") && cnt.equalsIgnoreCase("HK")) {
                ArrayList ary = new ArrayList();
                ary.add("MIX_CLARITY");
                ary.add("Y");
                genricSrchLst.add(ary);
                info.setGncPrpLst(genricSrchLst);
                ArrayList lprpS = (ArrayList)prp.get("MIX_CLARITYS");
                ArrayList lprpV = (ArrayList)prp.get("MIX_CLARITYV");
                String lsrt = (String)lprpS.get((lprpV.indexOf("GIA MIX")));
                paramsMap.put("MIX_CLARITY_1", lsrt);
                paramsMap.put("MIX_CLARITY_2", lsrt);
            }
            paramsMap.put("stt", stt);
            paramsMap.put("mdl", "MIX_VIEW");
            paramsMap.put("MIX", "Y");
            util.genericSrch(paramsMap);
        }else{
            paramsMap.put("vnm", vnm);
            paramsMap.put("stt", stt);
            paramsMap.put("mdl", "MIX_VIEW");
            FetchResult(req,res,paramsMap);
        }
        ArrayList stockList = SearchResult(db,session);
        session.setAttribute("stockList", stockList);
        req.setAttribute("view", "Y");

        genricSrchLst = new ArrayList();
        genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXSIG_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXSIG_SRCH");
        info.setGncPrpLst(genricSrchLst);
          util.updAccessLog(req,res,"MixToSingle", "fetch end");
        return am.findForward("load");
        }
    }
    
    public ActionForward popRslt(ActionMapping am, ActionForm af,
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
          util.updAccessLog(req,res,"MixToSingle", "fetch");
        MixToSingleForm udf = (MixToSingleForm)af;
          ArrayList stockList = SearchResult(db,session);
          session.setAttribute("stockList", stockList);
          req.setAttribute("view", "Y");
          return am.findForward("load");
    
      }
    }
 
    public void FetchResult(HttpServletRequest req,HttpServletResponse res, HashMap  paramMap ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String stt = (String)paramMap.get("stt");
        String vnm = (String)paramMap.get("vnm");
        String typ = (String)paramMap.get("TYP");
        String mdl=(String)paramMap.get("mdl");
        ArrayList ary = null;
        HashMap stockList= new HashMap();
            if(!vnm.equals("") || !stt.equals("")){
        ArrayList delAry=new ArrayList();
        delAry.add("Z");
        String delQ = " Delete from gt_srch_rslt where flg in (?)";
        int ct =db.execUpd(" Del Old Pkts ", delQ, delAry);
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn , vnm , pkt_dte, stt , flg , qty , cts , rmk,sk1 ,quot) " + 
        " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty-nvl(qty_iss,0), cts-nvl(cts_iss,0), tfl3 ,sk1 , nvl(upr,cmp) "+
        "     from mstk b "+
        " where stt =? and b.pkt_ty='MIX' and cts > 0  ";
        
        if(!vnm.equals("")){
                 vnm = util.getVnm(vnm);
                srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))" ;
        }
              
        
        ary = new ArrayList();
        ary.add(stt);
      
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add(mdl);
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
            }
      
      
    }
    
    
    public ActionForward verifyVnm(ActionMapping am, ActionForm form,
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
        
        StringBuffer sb = new StringBuffer();
        String vnm = util.nvl(req.getParameter("vnm")).trim();
        ArrayList ary = new ArrayList();
        ArrayList mixtoSingleList =
            (session.getAttribute("mergesttList") == null) ? new ArrayList() :
            (ArrayList)session.getAttribute("mergesttList");
        if (mixtoSingleList.size() == 0)
            mixtoSingleList = mergestt(db, session);
        String sql = " select idn,vnm,stt,cts from mstk where vnm=?";
        ary.add(vnm);
          ArrayList  rsLst = db.execSqlLst("Verify Vnm", sql, ary);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
        if (rs.next()) {
            String stt = util.nvl(rs.getString("stt")).toUpperCase();
            String cts = util.nvl(rs.getString("cts"), "0");
            String idn = util.nvl(rs.getString("idn"), "0");
            sb.append("<detail>");
            sb.append("<exists>Y</exists>");
            if (mixtoSingleList.contains(stt)) {
                sb.append("<pktgen>N</pktgen>");
                sb.append("<pktidn>" + idn + "</pktidn>");
                sb.append("<existscts>" + cts + "</existscts>");
                sb.append("<stt>" + stt + "</stt>");
            } else {
                sb.append("<pktgen>Y</pktgen>");
                sb.append("<pktidn>0</pktidn>");
                sb.append("<existscts>0</existscts>");
                sb.append("<stt>" + stt + "</stt>");
            }
            sb.append("</detail>");
        } else {
            sb.append("<detail>");
            sb.append("<exists>N</exists>");
            sb.append("<pktgen>Y</pktgen>");
            sb.append("<pktidn>0</pktidn>");
            sb.append("<existscts>0</existscts>");
            sb.append("<stt>0</stt>");
            sb.append("</detail>");
        }
        rs.close();
          stmt.close();
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<details>" + sb.toString() + "</details>");
        return null;
        }
    }

    public ArrayList SearchResult(DBMgr db,HttpSession session) {
         JspUtil jspUtil = new JspUtil();
        ArrayList pktList = new ArrayList();
        ArrayList vwPrpLst = ASPrprViw(db, session);
        String srchQ =
            " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts  ";


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


        String rsltQ =
            srchQ + " from gt_srch_rslt where flg =?  order by sk1 , cts ";

        ArrayList ary = new ArrayList();
        ary.add("Z");
      ArrayList  rsLst = db.execSqlLst("search Result", rsltQ, ary);
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while (rs.next()) {

                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", jspUtil.nvl(rs.getString("stt")));
                pktPrpMap.put("stk_idn", jspUtil.nvl(rs.getString("stk_idn")));
                pktPrpMap.put("vnm", jspUtil.nvl(rs.getString("vnm")));
                pktPrpMap.put("cts", jspUtil.nvl(rs.getString("cts")));
                pktPrpMap.put("qty", jspUtil.nvl(rs.getString("qty")));
                for (int j = 0; j < vwPrpLst.size(); j++) {
                    String prp = (String)vwPrpLst.get(j);

                    String fld = "prp_";
                    if (j < 9)
                        fld = "prp_00" + (j + 1);
                    else
                        fld = "prp_0" + (j + 1);
                    if(prp.equals("CRTWT"))
                        fld="cts";
                    String val = jspUtil.nvl(rs.getString(fld));


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

        return pktList;
    }

    public ArrayList mergestt(DBMgr db ,
                              HttpSession session) {
        JspUtil jspUtil = new JspUtil();
        ArrayList mergesttList = new ArrayList();
        String memoPrntOptn =
            "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
            "b.mdl = 'JFLEX' and b.nme_rule = 'MERGESTT' and a.til_dte is null order by a.srt_fr ";
      ArrayList  rsLst = db.execSqlLst("MERGESTT", memoPrntOptn, new ArrayList());
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while (rs.next()) {
                mergesttList.add(jspUtil.nvl(rs.getString("chr_fr")));
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("mergesttList", mergesttList);
        return mergesttList;
    }

    public void mixsttList(DBMgr db ,
                              HttpSession session) {
        JspUtil jspUtil = new JspUtil();
        ArrayList mixsttList = new ArrayList();
        String memoPrntOptn =
            "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
            "b.mdl = 'JFLEX' and b.nme_rule = 'MIXTOSINGLE' and a.til_dte is null order by a.srt_fr ";
      
      ArrayList  rsLst = db.execSqlLst("MIXTOSINGLE", memoPrntOptn, new ArrayList());
      PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
      ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while (rs.next()) {
                ArrayList data = new ArrayList();
                data.add(jspUtil.nvl(rs.getString("chr_fr")));
                data.add(jspUtil.nvl(rs.getString("dsc")));
                mixsttList.add(data);
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("mixtoSinglesttList", mixsttList);
    }

    public ArrayList MIXGenricSrch(DBMgr db ,
                              HttpSession session) {
       
        ArrayList asViewPrp = null;
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
              
              ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp,flg  from rep_prp where mdl = 'MIXSIG_SRCH' and flg in ('Y','S','M') order by rnk ",
                               new ArrayList());
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs1 =(ResultSet)rsLst.get(1);
                while (rs1.next()) {
                    ArrayList asViewdtl = new ArrayList();
                    asViewdtl.add(rs1.getString("prp"));
                    asViewdtl.add(rs1.getString("flg"));
                    asViewPrp.add(asViewdtl);
                }
                rs1.close();
                stmt.close();
                session.setAttribute("MIXSIG_SRCH", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }

        return asViewPrp;
    }

    public ArrayList ASPrprViw(DBMgr db ,
                              HttpSession session) {
 

        ArrayList asViewPrp = (ArrayList)session.getAttribute("MixViewLst");
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
                session.setAttribute("MixViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }

        return asViewPrp;
    }

    public ArrayList NewPktPrprViw(DBMgr db ,
                              HttpSession session) {
     
        ArrayList asViewPrp = (ArrayList)session.getAttribute("NewPktViewLst");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
               
              ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'NEWPKT_VW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs1 =(ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                stmt.close();
                session.setAttribute("NewPktViewLst", asViewPrp);
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
              rtnPg=util.checkUserPageRights("",util.getFullURL(req));
              if(rtnPg.equals("unauthorized"))
              util.updAccessLog(req,res,"Mix To Single Action", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Mix To Single Action", "init");
          }
          }
          return rtnPg;
          }
    

}
