package ft.com.receipt;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.dao.ByrDao;

import ft.com.dao.ObjBean;

import java.math.BigDecimal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

public class PaymentTransAction extends  DispatchAction {
    public PaymentTransAction() {
        super();
    }
   public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
                PaymentTransForm udf =(PaymentTransForm)form;
                udf.resetAll();
                GtMgrReset(req);
                ArrayList byrList = new ArrayList();
                String gMst ="select DISTINCT idn ,nm from GL_OS where ENTRY_POINT <> 'DFLT' and amt > 0  order by nm ";
                ArrayList rsLst = db.execSqlLst("data sql", gMst, new ArrayList());
                PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
                ResultSet rs = (ResultSet)rsLst.get(1);
                while (rs.next()) {
                   ByrDao byr = new ByrDao();
                    byr.setByrIdn(rs.getInt("idn"));
                    byr.setByrNme(rs.getString("nm"));
                    byrList.add(byr);
                }
                udf.setByrLst(byrList);
                rs.close();
                psmt.close();
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
                 gtMgr.setValue("DTLMAP", new HashMap());
                 PaymentTransForm udf =(PaymentTransForm)form;
                 String mode = util.nvl((String)udf.getValue("payMode"));
                 String cur = util.nvl((String)udf.getValue("cur"));
                 HashMap dtlMap = new HashMap();
                 ArrayList payableList = new ArrayList();
                 ArrayList receiveList = new ArrayList();
                 String partyIdn = (String)udf.getValue("byrIdn");
                String xrt = "";
                 String partyNme ="";
                 ArrayList params = new ArrayList();
                 params.add(partyIdn);
                String payableSql="select os.nm, os.flg, os.REF_TYP,sm.acc_typ,sm.cntidn, os.REF_IDN,sm.DYS,to_char(sm.DUE_DTE,'dd-MON-yyyy') DUEDTE ,to_char(sm.ref_dte,'dd-MON-yyyy') refdte,sm.cts,to_char(trunc(os.amt,2),'9999999999990.00')  amt ,to_char(trunc(os.amt/sm.xrt,2),'9999999999990.00')  cur,sm.xrt\n" + 
                 "from GL_TRNS_OS os , gl_trns_smry sm\n" + 
                 "where os.REF_IDN=sm.REF_IDN and os.idn = ? and sm.amt > 0 " ;
                 if(!mode.equals("ALL")){
                     payableSql=payableSql+" AND os.FLG=? ";
                     params.add(mode);
                 }
                    
                 ArrayList rsLst = db.execSqlLst("data sql", payableSql, params);
                 PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
                 ResultSet rs = (ResultSet)rsLst.get(1);
                 while (rs.next()) {
                    HashMap trnsDtl = new HashMap();
                     String flg = util.nvl(rs.getString("flg"));
                     trnsDtl.put("REFTYP", util.nvl(rs.getString("ref_typ")));
                     trnsDtl.put("REFIDN", util.nvl(rs.getString("ref_idn")));
                     trnsDtl.put("DYS", util.nvl(rs.getString("dys")));
                     trnsDtl.put("DUE_DTE", util.nvl(rs.getString("DUEDTE")));
                     trnsDtl.put("XRT", util.nvl(rs.getString("xrt")));
                     xrt=util.nvl(rs.getString("xrt"));
                     trnsDtl.put("AMT", util.nvl(rs.getString("amt")).trim());
                     trnsDtl.put("CUR", util.nvl(rs.getString("cur")).trim());
                     trnsDtl.put("REF_DTE", util.nvl(rs.getString("refdte")));
                     trnsDtl.put("CTS", util.nvl(rs.getString("cts")));
                     trnsDtl.put("ACCTYP", util.nvl(rs.getString("acc_typ")));
                     trnsDtl.put("CNTIDN", util.nvl(rs.getString("cntidn")));
                     partyNme = util.nvl(rs.getString("nm"));
                     if(flg.equals("R"))
                      receiveList.add(trnsDtl);
                   else
                      payableList.add(trnsDtl);
                     
                     
                     
                 }
                 rs.close();
                psmt.close();
                 ArrayList ary = new ArrayList();
                 dtlMap.put("PAY", payableList);
                 dtlMap.put("REC", receiveList);
                 dtlMap.put("PARTYNME", partyNme);
                 dtlMap.put("CUR", cur);
                 udf.setValue("cur", cur);
                 udf.setValue("curXrt", xrt);
                 
                 String partySql="select DISTINCT idn , nm from GL_OS where ENTRY_POINT <> 'DFLT' and amt > 0 " ;
                 if(!mode.equals("ALL")){
                     partySql=partySql+" AND FLG=? ";
                     ary.add(mode);
                 }
                   partySql=partySql+ " order by nm ";
                 
                rs = db.execSql("partySql",partySql, ary);
                 ArrayList byrList= new ArrayList();
                 while (rs.next()) {
                    ByrDao byr = new ByrDao();
                     byr.setByrIdn(rs.getInt("idn"));
                     byr.setByrNme(rs.getString("nm"));
                     byrList.add(byr);
                 }
                 udf.setByrLst(byrList);
                   rs.close();
                
                gtMgr.setValue("DTLMAP", dtlMap);
                 return am.findForward("load");   
             } 
    }
    
    public ActionForward BuyerList(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
       res.setContentType("text/xml"); 
       res.setHeader("Cache-Control", "no-cache"); 
        ArrayList ary = new ArrayList();
      
      StringBuffer sb = new StringBuffer();
      String typ = req.getParameter("TYP");
      String partySql="select DISTINCT idn ,form_url_encode(nm) nm from GL_OS where ENTRY_POINT <> 'DFLT' and amt > 0 " ;
      if(!typ.equals("ALL")){
          partySql=partySql+" AND FLG=? ";
          ary.add(typ);
      }
        partySql=partySql+ " order by nm ";
      
      ResultSet rs = db.execSql("partySql",partySql, ary);
        while(rs.next()){
            sb.append("<NME>");
            sb.append("<IDN>"+util.nvl(rs.getString("idn"))+"</IDN>");
            sb.append("<DSC>"+util.nvl(rs.getString("nm")) +"</DSC>");
            sb.append("</NME>");
        }
        rs.close();
       
        res.getWriter().write("<NMES>" +sb.toString()+ "</NMES>");
        
       
        return null;
    }
    
    public ActionForward PartyList(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
     
      ResultSet rs = null;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      ArrayList ary = new ArrayList();
      StringBuffer sb = new StringBuffer();
      String via = util.nvl(req.getParameter("VIA"));
      String typ = util.nvl(req.getParameter("TYP"));
      if(via.equals("T")){
         String flg="P";
         if(typ.equals("PAY"))
             flg="R";
          String partyList ="select form_url_encode(cd) cd ,os.typ,to_char(sm.ref_dte,'dd-MON-yyyy') ref_dte,sm.xrt, sm.REF_TYP,sm.REF_IDN,sm.dys ,to_char(sm.DUE_DTE,'dd-MON-yyyy') DUE_DTE,sm.cts,to_char(trunc(os.amt,2),'9999999999990.00') amt ,to_char(trunc(os.amt/sm.xrt,2),'9999999999990.00') cur ,os.IDN from GL_TRNS_OS os ,gl_trns_smry sm\n" + 
          "where os.amt > 1 and os.REF_IDN=sm.REF_IDN\n" + 
          "and flg=? and os.ENTRY_POINT <> 'DFLT' order by cd";
          ary = new ArrayList();
          ary.add(flg);
          rs = db.execSql("partyList",partyList, ary);
      }else{
          String partySql = "select idn, form_url_encode(cd) cd , to_char(trunc(gl_get_os(idn),2),'9999999999990.00')  amt , 0 ref_idn ,0 ref_typ,0 xrt,0 cur from gl_mst where typ=? and stt=? order by cd";
          ary = new ArrayList();
          ary.add(via);
          ary.add("A");
          if(typ.equals("PAY")){
             partySql = "select idn, cd ,to_char(trunc(amt,2),'9999999999990.00')  amt ,to_char(trunc(cur,2),'9999999999990.00')  cur, 0 ref_idn ,0 ref_typ,0 xrt from GL_OS where typ=? order by cd";
              ary = new ArrayList();
              ary.add(via);
          }
          rs = db.execSql("partySql",partySql, ary);
      }
      HashMap partMap = new HashMap();
        while(rs.next()){
            sb.append("<NME>");
            sb.append("<IDN>"+util.nvl(rs.getString("idn"))+"</IDN>");
            sb.append("<DSC>"+util.nvl(rs.getString("cd")) +"</DSC>");
            sb.append("<AMT>"+util.nvl(rs.getString("amt")) +"</AMT>");
            sb.append("<REFIDN>"+util.nvl(rs.getString("ref_idn")) +"</REFIDN>");
            sb.append("<REFTYP>"+util.nvl(rs.getString("ref_typ")) +"</REFTYP>");
            sb.append("<XRT>"+util.nvl(rs.getString("xrt")) +"</XRT>");
            sb.append("<CUR>"+util.nvl(rs.getString("CUR")) +"</CUR>");
            sb.append("</NME>");
            if(via.equals("T")){
            HashMap triedPartyMap = new HashMap();
            triedPartyMap.put("REF_DTE", util.nvl(rs.getString("ref_dte")));
            triedPartyMap.put("REFTYP", util.nvl(rs.getString("ref_typ")));
            triedPartyMap.put("REFIDN", util.nvl(rs.getString("ref_idn")));
            triedPartyMap.put("DYS", util.nvl(rs.getString("dys")));
            triedPartyMap.put("DUE_DTE", util.nvl(rs.getString("DUE_DTE")));
            triedPartyMap.put("CTS", util.nvl(rs.getString("cts")));
            triedPartyMap.put("XRT", util.nvl(rs.getString("xrt")));
            partMap.put(util.nvl(rs.getString("idn"))+"_"+util.nvl(rs.getString("ref_idn")), triedPartyMap);
            }
        }
        gtMgr.setValue("TRIDPRTYMAP",partMap);
        rs.close();
       
        res.getWriter().write("<NMES>" +sb.toString()+ "</NMES>");
        
       
        return null;
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
                    HashMap dtlMap = (HashMap)gtMgr.getValue("DTLMAP");
                   db.setAutoCommit(false);
                    try{
                    ArrayList typLst = new ArrayList();
                    typLst.add("REC");
                    typLst.add("PAY");
                    ArrayList prcfileLst = new ArrayList();
                    ArrayList prcSuccLst = new ArrayList();
                    PaymentTransForm udf =(PaymentTransForm)form;
                  
                    String partyNme = (String)udf.getValue("byrIdn");
                    String confirmBy = util.nvl(req.getParameter("confirmBy"));
                    String rmk = util.nvl(req.getParameter("rmk"));
                    String gl_ent_seq="";
                    for(int i=0;i<typLst.size();i++){
                   String typ = (String)typLst.get(i);
                    ArrayList recevLst = (ArrayList)dtlMap.get(typ);
                    String paramNm = "CB_"+typ;
                   String cnt = req.getParameter(paramNm);
                        if(cnt!=null){     
                              int lstInx = Integer.parseInt(cnt)-1;
                              HashMap entryDtl = (HashMap)recevLst.get(lstInx);
                             String isFull = util.nvl(req.getParameter(typ+"_"+cnt));
                              String refId =util.nvl((String)entryDtl.get("REFIDN"));
                            String cntIdn = util.nvl(req.getParameter(typ+"CNTIDN_"+cnt)).trim();
                              String amt = util.nvl(req.getParameter(typ+"TXT_"+cnt)).trim();
                             String shortAcct = util.nvl(req.getParameter(typ+"SHRT_"+cnt)).trim();
                             String shortAmt = util.nvl(req.getParameter(typ+"SHRTTXT_"+cnt)).trim();
                             String ttlAmt = amt;
                             if(!shortAmt.equals("")){
                                BigDecimal amtB =  new BigDecimal(amt);
                                 BigDecimal samtB =  new BigDecimal(shortAmt);
                                 amtB = amtB.add(samtB);
                                 ttlAmt = String.valueOf(amtB);
                             }
                             
                            
                              if(!amt.equals("")){
                               
                              
                              String seqNme = "select GL_ENT_SEQ.nextval from dual";
                              ResultSet rs = db.execSql("rfID", seqNme, new ArrayList());
                              if(rs.next())
                                 gl_ent_seq = rs.getString(1);
                              rs.close();
                              int byCnt = Integer.parseInt(util.nvl(req.getParameter(typ+"BYCNT"),"0"));
                              ArrayList params = new ArrayList();
                              int ct=0;
                              String byTyp =util.nvl(req.getParameter(typ));
                              // Pay/Rec Via entries...
                              if(byTyp.equals("T")){
                                  HashMap TriedPartyMap = (HashMap)gtMgr.getValue("TRIDPRTYMAP");
                               for(int j=1;j<=byCnt;j++){
                                  
                                   String byamt = util.nvl(req.getParameter(typ+"BYAMT_"+j)).trim();
                                   String gl_idn = util.nvl(req.getParameter(typ+"BYIDN_"+j));
                                   String ref_idn = util.nvl(req.getParameter(typ+"REFIDN_"+j));
                                   HashMap entryTDtl = (HashMap)TriedPartyMap.get(gl_idn+"_"+ref_idn);
                                       
                                  if(!byamt.equals("")){
                                   double  oldXrt =Double.parseDouble(util.nvl((String)entryTDtl.get("XRT")));
                                   double newXrt = Double.parseDouble(util.nvl(req.getParameter(typ+"BYXRT_"+j)));
                                   if(oldXrt!=newXrt){
                                    BigDecimal bycur = new BigDecimal(util.nvl(req.getParameter(typ+"BYCUR_"+j)));
                                    BigDecimal xrtDiff = new BigDecimal(oldXrt-newXrt);
                                    BigDecimal oldXrtB = new BigDecimal(oldXrt);
                                    double diffAmt = Double.parseDouble(xrtDiff.multiply(bycur).toString());
                                    if(typ.equals("REC"))
                                        diffAmt =diffAmt*-1;
                                    String gl_entriescash= "insert into gl_entries(idn, gl_idn, t_dte, typ, sub_typ, fctr, ref_dte, ref_typ, ref_idn, dys, due_dte, cts, amt, xrt,ent_seq,flg1,rmk,cntidn)"+
                                               " select gl_entries_seq.nextval,?, sysdate, ?, ?,?, to_date(?,'dd-MON-yyyy'), ?, ?, ?, to_date(?,'dd-MON-yyyy'), ?, ?, ? ,?,? ,?,?,? "+
                                               " from dual";
                                     params = new ArrayList();
                                     params.add(gl_idn);
                                     if(typ.equals("PAY"))
                                     params.add("REC");
                                     else
                                     params.add("PAY");
                                     params.add("BUYER");
                                     params.add("1");
                                     params.add(util.nvl((String)entryTDtl.get("REF_DTE")));  
                                     params.add(util.nvl((String)entryTDtl.get("REFTYP"))); 
                                     params.add(util.nvl((String)entryTDtl.get("REFIDN")));  
                                     params.add(util.nvl((String)entryTDtl.get("DYS")));  
                                     params.add(util.nvl((String)entryTDtl.get("DUE_DTE")));  
                                     params.add(util.nvl((String)entryTDtl.get("CTS")));  
                                     params.add(String.valueOf(diffAmt));
                                     params.add(String.valueOf(newXrt)); 
                                     params.add(gl_ent_seq);
                                       params.add(confirmBy);
                                       params.add(rmk);
                                       params.add(cntIdn);
                                        ct = db.execCall("gl_entries", gl_entriescash, params);
                                       diffAmt=diffAmt*-1;
                                       String gl_entriessal= "insert into gl_entries(idn, gl_idn, t_dte, typ, sub_typ, fctr, ref_dte, ref_typ, ref_idn, dys, due_dte, cts, amt, xrt,ent_seq,flg1,rmk,cntidn)"+
                                                 " select gl_entries_seq.nextval,b.idn, sysdate, ?, ?,?, to_date(?,'dd-MON-yyyy'), ?, ?, ?, to_date(?,'dd-MON-yyyy'), ?, ?, ? , ? , ?,? ,?"+
                                                 " from gl_mst b " + 
                                                 " where b.cd=? ";
                                         params = new ArrayList();
                                        if(typ.equals("PAY"))
                                         params.add("REC");
                                         else
                                         params.add("PAY");
                                         params.add("SELF");
                                         params.add("1");
                                         params.add(util.nvl((String)entryTDtl.get("REF_DTE")));  
                                         params.add(util.nvl((String)entryTDtl.get("REFTYP"))); 
                                         params.add(util.nvl((String)entryTDtl.get("REFIDN")));  
                                         params.add(util.nvl((String)entryTDtl.get("DYS")));  
                                         params.add(util.nvl((String)entryTDtl.get("DUE_DTE")));  
                                         params.add(util.nvl((String)entryTDtl.get("CTS")));  
                                         params.add(String.valueOf(diffAmt));
                                        params.add(String.valueOf(newXrt)); 
                                         params.add(gl_ent_seq);
                                         params.add(confirmBy);
                                       params.add(rmk);
                                       params.add(cntIdn);
                                         params.add("XRT");
                                      
                                        ct  = db.execCall("gl_entries", gl_entriessal, params);
                                        
                                       
                                   }
                                  String gl_entriescash= "insert into gl_entries(idn, gl_idn, t_dte, typ, sub_typ, fctr, ref_dte, ref_typ, ref_idn, dys, due_dte, cts, amt, xrt,ent_seq,flg1,rmk,cntidn)"+
                                          " select gl_entries_seq.nextval,?, sysdate, ?, ?,?, to_date(?,'dd-MON-yyyy'), ?, ?, ?, to_date(?,'dd-MON-yyyy'), ?, ?, ? ,? ,?,? ,?"+
                                          " from dual";
                                  params = new ArrayList();
                                  params.add(gl_idn);
                                  if(typ.equals("PAY"))
                                   params.add("REC");
                                 else
                                   params.add("PAY");
                                  params.add("BUYER");
                                 if(typ.equals("PAY"))
                                    params.add("1");
                                else
                                    params.add("-1");
                                  params.add(util.nvl((String)entryTDtl.get("REF_DTE")));  
                                  params.add(util.nvl((String)entryTDtl.get("REFTYP"))); 
                                  params.add(util.nvl((String)entryTDtl.get("REFIDN")));  
                                  params.add(util.nvl((String)entryTDtl.get("DYS")));  
                                  params.add(util.nvl((String)entryTDtl.get("DUE_DTE")));  
                                  params.add(util.nvl((String)entryTDtl.get("CTS")));  
                                  params.add(byamt);
                                  params.add(String.valueOf(newXrt)); 
                                  params.add(gl_ent_seq);
                                          params.add(confirmBy);
                                          params.add(rmk);
                                          params.add(cntIdn);
                                   ct = db.execCall("gl_entries", gl_entriescash, params);
                                   
                                   
                                  String gl_entriessal= "insert into gl_entries(idn, gl_idn, t_dte, typ, sub_typ, fctr, ref_dte, ref_typ, ref_idn, dys, due_dte, cts, amt, xrt,ent_seq,flg1,rmk,cntidn)"+
                                 " select gl_entries_seq.nextval,b.idn, sysdate, ?, ?,?, to_date(?,'dd-MON-yyyy'), ?, ?, ?, to_date(?,'dd-MON-yyyy'), ?, ?, ?,?,?,?,? "+
                                 " from gl_trns_smry a ,gl_mst b " + 
                                 " where a.typ=b.nm and a.ref_idn=? ";
                                          params = new ArrayList();
                                          if(typ.equals("PAY"))
                                           params.add("REC");
                                          else
                                           params.add("PAY");
                                          params.add("SELF");
                                          if(typ.equals("PAY"))
                                             params.add("-1");
                                          else
                                             params.add("1");
                                          params.add(util.nvl((String)entryTDtl.get("REF_DTE")));  
                                          params.add(util.nvl((String)entryTDtl.get("REFTYP"))); 
                                          params.add(util.nvl((String)entryTDtl.get("REFIDN")));  
                                          params.add(util.nvl((String)entryTDtl.get("DYS")));  
                                          params.add(util.nvl((String)entryTDtl.get("DUE_DTE")));  
                                          params.add(util.nvl((String)entryTDtl.get("CTS")));  
                                          params.add(byamt);
                                          params.add(String.valueOf(newXrt));  
                                          params.add(gl_ent_seq);
                                          params.add(confirmBy);
                                          params.add(rmk);
                                          params.add(cntIdn);
                                          params.add(util.nvl((String)entryTDtl.get("REFIDN"))); 
                                         
                                           ct = db.execCall("gl_entries", gl_entriessal, params);
                                   
                                   
                                      }
                                  }
                                  
                              }else{
                              for(int j=1;j<=byCnt;j++){
                                  
                             String byamt = util.nvl(req.getParameter(typ+"BYAMT_"+j));
                             String gl_idn = util.nvl(req.getParameter(typ+"BYIDN_"+j));
                            if(!byamt.equals("")){ 
                            String byxrt = util.nvl(req.getParameter(typ+"BYXRT_"+j));
                            params = new ArrayList();
                             String gl_entriescash= "insert into gl_entries(idn, gl_idn, t_dte, typ, sub_typ, fctr, ref_dte, ref_typ, ref_idn, dys, due_dte, cts, amt, xrt,ent_seq,flg1,rmk,cntidn)"+
                                      " select gl_entries_seq.nextval,?, sysdate, ?, ?,?, to_date(?,'dd-MON-yyyy'), ?, ?, ?, to_date(?,'dd-MON-yyyy'), ?, ?, ?,? ,?,? ,?"+
                                      " from dual";
                              params.add(gl_idn);
                              params.add(typ);
                              params.add("SELF");
                              if(typ.equals("REC"))
                              params.add("1");
                             else
                              params.add("-1");
                              params.add(util.nvl((String)entryDtl.get("REF_DTE")));  
                              params.add(util.nvl((String)entryDtl.get("REFTYP"))); 
                              params.add(util.nvl((String)entryDtl.get("REFIDN")));  
                              params.add(util.nvl((String)entryDtl.get("DYS")));  
                              params.add(util.nvl((String)entryDtl.get("DUE_DTE")));  
                              params.add(util.nvl((String)entryDtl.get("CTS")));  
                              params.add(byamt);
                              params.add(byxrt);
                              params.add(gl_ent_seq);
                                      params.add(confirmBy);
                                      params.add(rmk);
                                      params.add(cntIdn);
                               ct = db.execCall("gl_entries", gl_entriescash, params);
                                  }
                                  String chamt = util.nvl(req.getParameter(typ+"CHAMT_"+j));
                          if(!chamt.equals("")){
                                String byxrt = util.nvl(req.getParameter(typ+"BYXRT_"+j));
                                String gl_entriessal= "insert into gl_entries(idn, gl_idn, t_dte, typ, sub_typ, fctr, ref_dte, ref_typ, ref_idn, dys, due_dte, cts, amt, xrt,ent_seq,flg1,rmk,cntidn)"+
                                          " select gl_entries_seq.nextval,b.idn, sysdate, ?, ?,?, to_date(?,'dd-MON-yyyy'), ?, ?, ?, to_date(?,'dd-MON-yyyy'), ?, ?, ? , ? , ? , ?,? "+
                                          " from gl_mst b " + 
                                          " where b.cd=? ";
                                  params = new ArrayList();
                                  params.add(typ);
                                  params.add("SELF");
                                 if(typ.equals("REC"))
                                 params.add("1");
                                 else
                                 params.add("-1");
                                  params.add(util.nvl((String)entryDtl.get("REF_DTE")));  
                                  params.add(util.nvl((String)entryDtl.get("REFTYP"))); 
                                  params.add(util.nvl((String)entryDtl.get("REFIDN")));  
                                  params.add(util.nvl((String)entryDtl.get("DYS")));  
                                  params.add(util.nvl((String)entryDtl.get("DUE_DTE")));  
                                  params.add(util.nvl((String)entryDtl.get("CTS")));  
                                  params.add(chamt);
                                params.add(byxrt);
                                params.add(gl_ent_seq);
                                params.add(confirmBy);
                                params.add(rmk);
                                params.add(cntIdn);
                                 params.add("CHARGE");
                               
                                  ct  = db.execCall("gl_entries", gl_entriessal, params);
                              
                                      
                            }
                                  
                                  
                              }
                               }
                              // transcation entries...
                              double  oldXrt =Double.parseDouble(util.nvl((String)entryDtl.get("XRT")));
                              double newXrt = Double.parseDouble(util.nvl(req.getParameter(typ+"XRTTXT_"+cnt)));
                              if(oldXrt!=newXrt){
                               BigDecimal bycur = new BigDecimal(util.nvl(req.getParameter(typ+"CURTXT_"+cnt)));
                               BigDecimal xrtDiff = new BigDecimal(oldXrt-newXrt);
                               double diffAmt = Double.parseDouble(xrtDiff.multiply(bycur).toString());
                                if(typ.equals("PAY"))
                                       diffAmt =diffAmt*-1;
                               String gl_entriescash= "insert into gl_entries(idn, gl_idn, t_dte, typ, sub_typ, fctr, ref_dte, ref_typ, ref_idn, dys, due_dte, cts, amt, xrt,ent_seq,flg1,rmk,cntidn)"+
                                          " select gl_entries_seq.nextval,?, sysdate, ?, ?,?, to_date(?,'dd-MON-yyyy'), ?, ?, ?, to_date(?,'dd-MON-yyyy'), ?, ?, ? ,? , ?,? ,?"+
                                          " from dual";
                                params = new ArrayList();
                                params.add(partyNme);
                                params.add(typ);
                                params.add("BUYER");
                                params.add("1");
                                params.add(util.nvl((String)entryDtl.get("REF_DTE")));  
                                params.add(util.nvl((String)entryDtl.get("REFTYP"))); 
                                params.add(util.nvl((String)entryDtl.get("REFIDN")));  
                                params.add(util.nvl((String)entryDtl.get("DYS")));  
                                params.add(util.nvl((String)entryDtl.get("DUE_DTE")));  
                                params.add(util.nvl((String)entryDtl.get("CTS")));  
                                params.add(String.valueOf(diffAmt));
                                params.add(String.valueOf(newXrt)); 
                                params.add(gl_ent_seq);
                                  params.add(confirmBy);
                                  params.add(rmk);
                                  params.add(cntIdn);
                                   ct = db.execCall("gl_entries", gl_entriescash, params);
                                   diffAmt=diffAmt*-1;
                                  String gl_entriessal= "insert into gl_entries(idn, gl_idn, t_dte, typ, sub_typ, fctr, ref_dte, ref_typ, ref_idn, dys, due_dte, cts, amt, xrt,ent_seq,flg1,rmk,cntidn)"+
                                            " select gl_entries_seq.nextval,b.idn, sysdate, ?, ?,?, to_date(?,'dd-MON-yyyy'), ?, ?, ?, to_date(?,'dd-MON-yyyy'), ?, ?, ? , ? ,?,? ,?"+
                                            " from gl_mst b " + 
                                            " where b.cd=? ";
                                    params = new ArrayList();
                                    params.add(typ);
                                    params.add("SELF");
                                    params.add("1");
                                    params.add(util.nvl((String)entryDtl.get("REF_DTE")));  
                                    params.add(util.nvl((String)entryDtl.get("REFTYP"))); 
                                    params.add(util.nvl((String)entryDtl.get("REFIDN")));  
                                    params.add(util.nvl((String)entryDtl.get("DYS")));  
                                    params.add(util.nvl((String)entryDtl.get("DUE_DTE")));  
                                    params.add(util.nvl((String)entryDtl.get("CTS")));  
                                    params.add(String.valueOf(diffAmt));
                                    params.add(String.valueOf(newXrt)); 
                                    params.add(gl_ent_seq);
                                    params.add(confirmBy);
                                   params.add(rmk);
                                  params.add(cntIdn);
                                    params.add("XRT");
                                 
                                   ct  = db.execCall("gl_entries", gl_entriessal, params);
                                   
                                  
                              }
                              if(ct>0){
                             String gl_entriessal= "insert into gl_entries(idn, gl_idn, t_dte, typ, sub_typ, fctr, ref_dte, ref_typ, ref_idn, dys, due_dte, cts, amt, xrt,ent_seq,flg1,rmk,cntidn)"+
                                       " select gl_entries_seq.nextval,b.idn, sysdate, ?, ?,?, to_date(?,'dd-MON-yyyy'), ?, ?, ?, to_date(?,'dd-MON-yyyy'), ?, ?, ?,? ,?,? ,?"+
                                       " from gl_trns_smry a ,gl_mst b " + 
                                       " where a.typ=b.nm and a.ref_idn=? ";
                              params = new ArrayList();
                               params.add(typ);
                               params.add("SELF");
                              if(typ.equals("REC"))
                              params.add("-1");
                              else
                              params.add("1");
                               params.add(util.nvl((String)entryDtl.get("REF_DTE")));  
                               params.add(util.nvl((String)entryDtl.get("REFTYP"))); 
                               params.add(util.nvl((String)entryDtl.get("REFIDN")));  
                               params.add(util.nvl((String)entryDtl.get("DYS")));  
                               params.add(util.nvl((String)entryDtl.get("DUE_DTE")));  
                               params.add(util.nvl((String)entryDtl.get("CTS")));  
                               params.add(ttlAmt);
                              params.add(String.valueOf(newXrt)); 
                               params.add(gl_ent_seq);
                                  params.add(confirmBy);
                                  params.add(rmk);
                                  params.add(cntIdn);
                              params.add(util.nvl((String)entryDtl.get("REFIDN")));
                                 
                               ct  = db.execCall("gl_entries", gl_entriessal, params);
                              }
                               if(ct>0){
                              String gl_entriesparty= "insert into gl_entries(idn, gl_idn, t_dte, typ, sub_typ, fctr, ref_dte, ref_typ, ref_idn, dys, due_dte, cts, amt, xrt,ent_seq,flg1,rmk,cntidn)"+
                                        " select gl_entries_seq.nextval,?, sysdate, ?, ?,?, to_date(?,'dd-MON-yyyy'), ?, ?, ?, to_date(?,'dd-MON-yyyy'), ?, ?, ? ,? , ? , ? ,?"+
                                        " from dual ";
                               params = new ArrayList();
                                params.add(partyNme);
                                params.add(typ);
                                params.add("BUYER");
                              if(typ.equals("REC"))
                              params.add("1");
                              else
                              params.add("-1");
                                params.add(util.nvl((String)entryDtl.get("REF_DTE")));  
                                params.add(util.nvl((String)entryDtl.get("REFTYP"))); 
                                params.add(util.nvl((String)entryDtl.get("REFIDN")));  
                                params.add(util.nvl((String)entryDtl.get("DYS")));  
                                params.add(util.nvl((String)entryDtl.get("DUE_DTE")));  
                                params.add(util.nvl((String)entryDtl.get("CTS")));  
                                params.add(ttlAmt);
                                   params.add(String.valueOf(newXrt)); 
                                   params.add(gl_ent_seq);
                                   params.add(confirmBy);
                                   params.add(rmk);
                                   params.add(cntIdn);
                                ct  = db.execCall("gl_entriesparty", gl_entriesparty, params);
                               }
                              if(!shortAcct.equals("") && ct >0 && !shortAmt.equals("")){
                                  
                                   String gl_entriessal= "insert into gl_entries(idn, gl_idn, t_dte, typ, sub_typ, fctr, ref_dte, ref_typ, ref_idn, dys, due_dte, cts, amt, xrt,ent_seq,flg1,rmk,cntidn)"+
                                             " select gl_entries_seq.nextval,b.idn, sysdate, ?, ?,?, to_date(?,'dd-MON-yyyy'), ?, ?, ?, to_date(?,'dd-MON-yyyy'), ?, ?, ? , ? , ? , ? ,? "+
                                             " from gl_mst b " + 
                                             " where b.cd=? ";
                                     params = new ArrayList();
                                     params.add(typ);
                                     params.add("SELF");
                                    if(typ.equals("REC"))
                                    params.add("1");
                                    else
                                    params.add("-1");
                                     params.add(util.nvl((String)entryDtl.get("REF_DTE")));  
                                     params.add(util.nvl((String)entryDtl.get("REFTYP"))); 
                                     params.add(util.nvl((String)entryDtl.get("REFIDN")));  
                                     params.add(util.nvl((String)entryDtl.get("DYS")));  
                                     params.add(util.nvl((String)entryDtl.get("DUE_DTE")));  
                                     params.add(util.nvl((String)entryDtl.get("CTS")));  
                                     params.add(shortAmt);
                                   params.add(util.nvl((String)entryDtl.get("XRT")));
                                   params.add(gl_ent_seq);
                                   params.add(confirmBy);
                                   params.add(rmk);
                                   params.add(cntIdn);
                                    params.add("SHORT");
                                 
                                     ct  = db.execCall("gl_entries", gl_entriessal, params);
                               }
                              if(ct>0){
                               prcSuccLst.add(refId);
                               if(!isFull.equals("")){
                                   params = new ArrayList();
                                   params.add(refId);
                                   ct = db.execUpd("update gt_full", "update gl_trns_smry set FULL_YN='Y' where ref_idn= ?", params);
                               }
                              } else
                              prcfileLst.add(refId);
                          }
                            
                        }}
                   
                    udf.reset();
                    String msg="";
                    if(prcSuccLst.size()>0){
                        msg=msg+"Process done successfully For Transactions ID :-"+gl_ent_seq;
                        db.doCommit();
                    }
                    if(prcfileLst.size()>0){
                        msg=msg+" , Process Failed For Transactions ID :-"+gl_ent_seq;
                        db.doRollBack();
                    }
                    req.setAttribute("msg",msg);
                    GtMgrReset(req);
                    }catch(Exception es){
                        db.doRollBack();
                    }finally{
                            db.setAutoCommit(true);  
                 }
                    return load(am, form, req, res);
                }
       }
    
    
    public void GtMgrReset(HttpServletRequest req){
          HttpSession session = req.getSession(false);
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
          HashMap gtMgrMap = (HashMap)gtMgr.getValues();
            gtMgrMap.remove("DTLMAP");
            gtMgrMap.remove("TRIDPRTYMAP");
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
                util.updAccessLog(req,res,"Payment Trns", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Payment Trns", "init");
            }
            }
            return rtnPg;
            }
}
