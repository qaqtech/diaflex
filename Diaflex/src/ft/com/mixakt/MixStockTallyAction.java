package ft.com.mixakt;

import ft.com.DBMgr;
import ft.com.DBUtil;

import ft.com.GtMgr;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.dao.ObjBean;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import ft.com.marketing.StockTallyForm;

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

public class MixStockTallyAction extends DispatchAction {
    public MixStockTallyAction() {
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
          MixStockTallyForm udf = (MixStockTallyForm)af;
          udf.resetAll();
          GenericInterface genericInt = new GenericImpl();
          ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIX_TLLY_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIX_TLLY_SRCH");
          info.setGncPrpLst(assortSrchList);
         
          HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
          HashMap pageDtl=(HashMap)allPageDtl.get("MIX_STK_TALLY");
          if(pageDtl==null || pageDtl.size()==0){
          pageDtl=new HashMap();
          pageDtl=util.pagedef("MIX_STK_TALLY");
          allPageDtl.put("MIX_STK_TALLY",pageDtl);
          }
          info.setPageDetails(allPageDtl);
          
          return am.findForward("load");
      }
    }
    
    
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req,
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
          MixStockTallyForm udf = (MixStockTallyForm)af;/*  */
          GenericInterface genericInt = new GenericImpl();
          ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIX_TLLY_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIX_TLLY_SRCH");
          info.setGncPrpLst(genricSrchLst);
          HashMap prp = info.getPrp();
          HashMap mprp = info.getMprp();
          HashMap paramsMap = new HashMap();
          String stt = util.nvl((String)udf.getValue("stt"));
          String submit = util.nvl((String)udf.getValue("submit"));
          String box_typ = util.nvl((String)udf.getValue("BOX_TYP"));
          String box_id = util.nvl((String)udf.getValue("BOX_ID"));
          String dscr="ALL";
          if(!box_typ.equals(""))
              dscr="BOX_TYP";
          if(!box_id.equals(""))
              dscr="BOX_ID";
          stt =","+stt;
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
          int seqNo=0;
              
           String[] sttStr = stt.split(",");
          
          paramsMap.put("cprp", "STT");
          paramsMap.put("cprpValLst",sttStr);
          paramsMap.put("PRCD", "MIX_STK_TLY");
          paramsMap.put("mdl", "MIX_TLLY_VW");
          paramsMap.put("MIX","Y");
          util.genericSrch(paramsMap);
          ArrayList ary = new ArrayList();
          ary.add(dscr);
          ArrayList out = new ArrayList();
          out.add("I");
          CallableStatement  cts =db.execCall("dppopMix", "DP_POP_MIX_STK_TLY(pDscr => ?,lSeq => ?)",ary,out);
          seqNo = cts.getInt(ary.size()+1);
          
          if(seqNo==0){
          req.setAttribute("msg", "There are not data in selected criteria");
              return am.findForward("load"); 
          }else{
              udf.setValue("SEQNO", String.valueOf(seqNo));
               return am.findForward("loadRtn");  
          }
      }
    }
    public ActionForward loadRtn(ActionMapping am, ActionForm af, HttpServletRequest req,
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
          MixStockTallyForm udf = (MixStockTallyForm)af;
          ArrayList vwPrpLst = PrpViewLst(req, res);
          ArrayList seqLst = new ArrayList();
          String seqStr = "select DISTINCT seq_idn from stk_tly where trunc(ISS_DTE)=trunc(sysdate)";
          ArrayList rsLst = db.execSqlLst("seqStr", seqStr, new ArrayList());
          PreparedStatement pst = (PreparedStatement)rsLst.get(0);
          ResultSet rs = (ResultSet)rsLst.get(1);
          while(rs.next()){
              ObjBean obj = new ObjBean();
              obj.setNme(rs.getString("seq_idn"));
              obj.setDsc(rs.getString("seq_idn"));
              seqLst.add(obj);
          }
          udf.setSeqList(seqLst);
          rs.close();
          pst.close();
          
          GenericInterface genericInt = new GenericImpl();
          ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIX_TLLY_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIX_TLLY_SRCH");
          info.setGncPrpLst(genricSrchLst);
          HashMap mprp = info.getMprp();
          HashMap prp = info.getPrp();
          String strQuery="";
          for(int i=0;i<genricSrchLst.size();i++){
              ArrayList prplist =(ArrayList)genricSrchLst.get(i);
              String lprp = (String)prplist.get(0);
              String flg= (String)prplist.get(1);
              String typ = util.nvl((String)mprp.get(lprp+"T"));
            
              String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
              String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
              if(typ.equals("T")){
                  fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                  fldVal2 = fldVal1;
              }
              if(fldVal2.equals(""))
              fldVal2=fldVal1;
              if(!fldVal1.equals("") && !fldVal2.equals("")){
                  int j=i+1;
                  strQuery=strQuery+" and srt"+j+"_val between "+fldVal1+" and "+fldVal2 ;
              }
              
          }
          
          
          String lseqNo = util.nvl((String)udf.getValue("SEQNO"));
          req.setAttribute("msg", udf.getValue("msg"));
          if(!lseqNo.equals("")){
          udf.reset();
          udf.setValue("SEQNO", lseqNo);
          
          int ttlRtnQty=0;
          double ttlRtnCts=0;
          int ttlIssQty=0;
          double ttlIssCts=0;
        
          String sqlSmmry = "select idn , dscr, seq_idn, iss_usr, pkt_cnt, stk_stt, iss_qty, iss_cts ,rtn_qty,rtn_cts,stt,rmk ,prp2_val from stk_tly_mix where seq_idn = ? "+strQuery+" order by sk1 ";
          ArrayList ary = new ArrayList();
          ary.add(lseqNo);
           rsLst = db.execSqlLst("sqlSmmry", sqlSmmry, ary);
          ArrayList pktDtlLst = new ArrayList();
          pst = (PreparedStatement)rsLst.get(0);
          rs = (ResultSet)rsLst.get(1);
          while(rs.next()){
              int issQty=rs.getInt("iss_qty");
              int rtnQty=rs.getInt("rtn_qty");
              double issCts = rs.getDouble("iss_cts");
              double rtnCts = rs.getDouble("rtn_cts");
             String stt = rs.getString("stt");
              ttlIssQty=ttlIssQty+issQty;
              ttlIssCts=ttlIssCts+issCts;
              if(stt.equals("RT")){
              ttlRtnQty=ttlRtnQty+rtnQty;
              ttlRtnCts=ttlRtnCts+rtnCts;
              }
              HashMap dtl = new HashMap();
              String idn= rs.getString("idn");
              dtl.put("DSC", rs.getString("dscr"));
              dtl.put("SEQNO", rs.getString("seq_idn"));
              dtl.put("CNT", rs.getString("pkt_cnt"));
              dtl.put("STT", rs.getString("stk_stt"));
              dtl.put("ISSQTY", rs.getString("iss_qty"));
              dtl.put("ISSCTS", rs.getString("iss_cts"));
              dtl.put("RTNQTY", rs.getString("rtn_qty"));
              dtl.put("RTNCTS", rs.getString("rtn_cts"));
              dtl.put("STT", stt);
              dtl.put("IDN", rs.getString("idn"));
              dtl.put("BOX_ID", rs.getString("prp2_val"));
              udf.setValue("RTNQTY_"+idn, dtl.get("ISSQTY"));
              udf.setValue("RTNCTS_"+idn, dtl.get("ISSCTS"));
              udf.setValue("RMK_"+idn, rs.getString("rmk"));
              pktDtlLst.add(dtl);
          }
          rs.close();
          pst.close();
              req.setAttribute("PKTDTL", pktDtlLst);
              req.setAttribute("ttlRtnQty",String.valueOf(ttlRtnQty));
              req.setAttribute("ttlIssQty", String.valueOf(ttlIssQty));
              req.setAttribute("ttlRtnCts", String.valueOf(util.roundToDecimals(ttlRtnCts,2)));
              req.setAttribute("ttlIssCts", String.valueOf(util.roundToDecimals(ttlIssCts,2)));
          }
          return am.findForward("RtnTally");
      }
    }
    
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req,
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
          MixStockTallyForm udf = (MixStockTallyForm)af;
          Enumeration reqNme = req.getParameterNames();
         ArrayList idnList = new ArrayList();
          while (reqNme.hasMoreElements()) {
              String paramNm = (String) reqNme.nextElement();
              if (paramNm.indexOf("CHK_") > -1) {
                  String val = req.getParameter(paramNm);
                  idnList.add(val);
                 }
          }
          double ttlRtnCst=0;
          if(idnList!=null  && idnList.size()>0){
              for(int i=0;i<idnList.size();i++){
                  String idn =(String)idnList.get(i);
                  String boxId =util.nvl(req.getParameter("BOX_"+idn));
                 double rtnCts=Double.parseDouble(util.nvl((String)udf.getValue("RTNCTS_"+idn),"0"));
                  double issCts=Double.parseDouble(util.nvl((String)udf.getValue("ISSCTS_"+idn),"0"));
                  issCts = issCts+0.5;
                  String stt="RT";
                  if(rtnCts>issCts)
                      stt="IS";
                  if(rtnCts!=0){
                  String updateSql = "update stk_tly_mix set rtn_qty = ? , rtn_cts = ? , rtn_dte = sysdate  ,stt = ?,rmk = ? ,rtn_usr  = pack_var.get_usr "+
                      " where idn = ? ";
                  ArrayList ary = new ArrayList();
                  ary.add(util.nvl((String)udf.getValue("RTNQTY_"+idn)));
                  ary.add(String.valueOf(rtnCts));
                  ary.add(stt);
                  ary.add(util.nvl((String)udf.getValue("RMK_"+idn)));
                  ary.add(idn);
                  int ct = db.execUpd("updatestTly", updateSql, ary);
                  if(ct>0){
                      String lstt ="";
                      String getstt = "select distinct stk_stt from stk_tly where seq_idn=?";
                      ary = new ArrayList();
                      ary.add(idn);
                      ArrayList rsLst = db.execSqlLst("getStkIdn", getstt, ary);
                      PreparedStatement pst = (PreparedStatement)rsLst.get(0);
                      ResultSet rs = (ResultSet)rsLst.get(1);
                      if(rs.next()){
                          lstt = rs.getString("stk_stt");
                      }
                      rs.close();
                      pst.close();
                      ttlRtnCst=ttlRtnCst+rtnCts;
                      ary = new ArrayList();
                      ary.add(boxId);
                      ary.add(lstt);
                      String getStkIdn = "select b.idn from stk_dtl a , mstk b where a.mprp='BOX_ID' and a.val= ? and a.grp=1 " +
                          " and a.mstk_idn =b.idn and b.stt= ? ";
                      rsLst = db.execSqlLst("getStkIdn", getStkIdn, ary);
                      pst = (PreparedStatement)rsLst.get(0);
                      rs = (ResultSet)rsLst.get(1);
                      if(rs.next()){
                          String mstkIdn = rs.getString("mstk_idn");
                           String insertSql= "INSERT INTO MIX_TRF_LOG(LOG_DTE, FRM_IDN, TO_IDN, QTY, CTS,TYP,RMK,UNM)"+
                                             "VALUES(SYSDATE, ? ,?, ?, ?,?,?, PACK_VAR.GET_USR)";
                          
                           ary = new ArrayList();
                           ary.add(mstkIdn);
                           ary.add("0");
                           ary.add(util.nvl((String)udf.getValue("RTNQTY_"+idn)));
                           ary.add(rtnCts);
                           ary.add("Stock Tally");
                           ary.add("Stock Tally "+idn);
                           ct = db.execUpd("log insert", insertSql, ary);
                          
                      }
                      rs.close();
                      pst.close();
                  }
                  }
                  }
              udf.setValue("msg", "Total Return Carat :-"+ttlRtnCst);
          }else{
             udf.setValue("msg", "Nothing to found for return..");
          }
    
     
          return am.findForward("loadRtn");
      }
    }
    
    public ActionForward loadTallyhistory(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
        throws Exception {
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
     
    
     util.updAccessLog(req,res,"Stock Tally", "Stock Tally loadTallyhistory start");
    
        MixStockTallyForm udf = (MixStockTallyForm)af;
        udf.reset();
        GenericInterface genericInt = new GenericImpl();
        ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIX_TLLY_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIX_TLLY_SRCH");
        info.setGncPrpLst(assortSrchList);
        
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("MIX_STK_TALLY");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("MIX_STK_TALLY");
        allPageDtl.put("MIX_STK_TALLY",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        
        ArrayList seqLst = new ArrayList();
        String seqStr = "select DISTINCT seq_idn from stk_tly_mix where trunc(ISS_DTE) > trunc(sysdate-30) order by seq_idn desc";
        ArrayList rsLst = db.execSqlLst("seqStr", seqStr, new ArrayList());
        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
        ResultSet rs = (ResultSet)rsLst.get(1);
        while(rs.next()){
            ObjBean obj = new ObjBean();
            obj.setNme(rs.getString("seq_idn"));
            obj.setDsc(rs.getString("seq_idn"));
            seqLst.add(obj);
        }
        udf.setSeqList(seqLst);
        rs.close();
        pst.close();
    return am.findForward("history");
    }
    }
    public ActionForward history(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
        throws Exception {
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
    MixStockTallyForm udf = (MixStockTallyForm)af;
    String mstkStt = "";
        util.updAccessLog(req,res,"Stock Tally", "Stock Tally history start");
     String stt =   util.nvl((String)udf.getValue("stt"));
    String issdte = util.nvl((String)udf.getValue("issdte"));
    String rtndte = util.nvl((String)udf.getValue("rtndte"));
    String seq = util.nvl((String)udf.getValue("seq"));
    String conQ="";
    

 
    ArrayList ary = new ArrayList();
    if(!seq.equals("") && !seq.equals("0")){
    conQ = conQ+" and a.seq_idn = ? ";
    ary.add(seq);
   
    }
    if(!issdte.equals("")){
        conQ = conQ+" and trunc(a.iss_dte) between to_date('"+issdte+"' , 'dd-mm-yyyy') and to_date('"+issdte+"' , 'dd-mm-yyyy') ";
    }
    if(!rtndte.equals("")){
        conQ = conQ+" and trunc(a.rtn_dte) between to_date('"+rtndte+"' , 'dd-mm-yyyy') and to_date('"+rtndte+"' , 'dd-mm-yyyy') ";
    }
    if(!stt.equals("")){
        String sttStr = stt.replaceAll(",", "','");
         sttStr=sttStr.replaceAll(" ", "");
            conQ=conQ+" and a.stk_stt in ('"+sttStr+"')";
        
     }
        ArrayList pktList = new ArrayList();
           String sqlTally = "select a.dscr, to_char(a.iss_dte,'DD-MON-YYYY') iss_dte ,to_char(a.rtn_dte,'DD-MON-YYYY') rtn_dte ,decode(a.stk_stt,'MKT','Marketing','Non Marketing') stt, a.iss_qty,a.iss_cts,a.rtn_qty,a.rtn_cts,a.iss_usr \n" + 
                             " from stk_tly_mix a where 1=1 "+conQ+" order by seq_idn , sk1 ";
           
           
      ArrayList rsLst = db.execSqlLst("sql", sqlTally, ary);
      PreparedStatement pst = (PreparedStatement)rsLst.get(0);
      ResultSet rs = (ResultSet)rsLst.get(1);
            try {
                while (rs.next()) {
                    HashMap dtlMap = new HashMap();
                    String dscr = util.nvl(rs.getString("dscr"));
                    String iss_dte = util.nvl(rs.getString("iss_dte"));
                    String rtn_dte = util.nvl(rs.getString("rtn_dte"));
                    String iss_qty = util.nvl(rs.getString("iss_qty"));
                    String iss_cts = util.nvl(rs.getString("iss_cts"));
                    String rtn_qty = util.nvl(rs.getString("rtn_qty"));
                    String rtn_cts = util.nvl(rs.getString("rtn_cts"));
                    String iss_usr = util.nvl(rs.getString("iss_usr"));
                    String stkstt = util.nvl(rs.getString("stt"));
                    dtlMap.put("DSC", dscr);
                    dtlMap.put("ISS_DTE", iss_dte);/*  */
                    dtlMap.put("RTN_DTE", rtn_dte);
                    dtlMap.put("ISS_QTY", iss_qty);
                    dtlMap.put("ISS_CTS", iss_cts);
                    dtlMap.put("RTN_QTY", rtn_qty);
                    dtlMap.put("RTN_CTS", rtn_cts);
                    dtlMap.put("ISS_USR", iss_usr);
                    dtlMap.put("STKSTT", stkstt);
                    pktList.add(dtlMap);
                    
                }
            rs.close();
                pst.close();
          
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        req.setAttribute("pktDtlList", pktList);  
        req.setAttribute("view", "yes");   
    udf.setValue("issdte",issdte);
    udf.setValue("rtndte",rtndte);
    udf.setValue("seq",seq);
        util.updAccessLog(req,res,"Stock Tally", "Stock Tally history end");
    return am.findForward("history");
    }
    }
    public ArrayList PrpViewLst(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute("MIX_TLLY_VW");
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
            
              ArrayList rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MIX_TLLY_VW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement pst = (PreparedStatement)rsLst.get(0);
              ResultSet rs1 = (ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close(); 
                pst.close();
                session.setAttribute("MIX_TLLY_VW", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
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
        util.updAccessLog(req,res,"Box Return", "Unauthorized Access");
        else
        util.updAccessLog(req,res,"Box Return", "init");
    }
    }
    return rtnPg;
    }
}
