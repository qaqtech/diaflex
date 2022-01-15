package ft.com.mixakt;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.ByrDao;
import ft.com.dao.PktDtl;
import ft.com.dao.TrmsDao;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
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

public class MixDeliveryUpdateAction extends DispatchAction
{
  public MixDeliveryUpdateAction() {}
  
  public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    String rtnPg = "sucess";
    if (info != null) {
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm());db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg = init(req, res, session, util);
    } else {
      rtnPg = "sessionTO"; }
    if (!rtnPg.equals("sucess")) {
      return am.findForward(rtnPg);
    }
    MixDeliveryUpdateForm udf = (MixDeliveryUpdateForm)af;
    udf.resetAll();
    ArrayList byrList = new ArrayList();
    String getByr = "select  distinct byr , BYR_IDN from DLV_PNDG_V where typ='DLV' and pkt_ty in('MIX','MX') order by byr";
    
    ArrayList outLst = db.execSqlLst("byr", getByr, new ArrayList());
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
    
    while (rs.next()) {
      ByrDao byr = new ByrDao();
      
      byr.setByrIdn(rs.getInt("BYR_IDN"));
      byr.setByrNme(rs.getString("byr"));
      byrList.add(byr);
    }
    rs.close();
    pst.close();
    udf.setByrLst(byrList);
    HashMap allPageDtl = info.getPageDetails() == null ? new HashMap() : info.getPageDetails();
    HashMap pageDtl = (HashMap)allPageDtl.get("MIXDELIVERY_CONFIRMATION");
    if ((pageDtl == null) || (pageDtl.size() == 0)) {
      pageDtl = new HashMap();
      pageDtl = util.pagedef("MIXDELIVERY_CONFIRMATION");
      allPageDtl.put("MIXDELIVERY_CONFIRMATION", pageDtl);
    }
    info.setPageDetails(allPageDtl);
    util.updAccessLog(req, res, "MixSaleDelivery", "load end");
    session.setAttribute("PktList", new ArrayList());
    return am.findForward("load");
  }
  

  public ActionForward loadPkt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    String rtnPg = "sucess";
    if (info != null) {
      Connection conn = info.getCon();
      if (conn != null) {
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        rtnPg = init(req, res, session, util);
      } else {
        rtnPg = "connExists";
      }
    } else {
      rtnPg = "sessionTO"; }
    if (!rtnPg.equals("sucess")) {
      return am.findForward(rtnPg);
    }
    util.updAccessLog(req, res, "Delivery Return", "Delivery Return load pkts");
    MixDeliveryUpdateForm udf = (MixDeliveryUpdateForm)af;
    ArrayList pkts = new ArrayList();
    String flnByr = util.nvl((String)udf.getValue("prtyIdn"));
    String dtefr = util.nvl((String)udf.getValue("dtefr"));
      String dteto = util.nvl((String)udf.getValue("dteto"));
      String byrId = util.nvl((String)udf.getValue("byrIdn"));
    String saleId = "";
    String vnmLst = util.nvl((String)udf.getValue("vnmLst"));
    ArrayList asViewPrp = MIXDlvPrprViw(session, db);
    String restrict_sale_rtn_days = util.nvl((String)info.getDmbsInfoLst().get("RESTRICT_SALE_RTN_DAYS"), "30");
    
    ArrayList params = null;
    Enumeration reqNme = req.getParameterNames();
    saleId = util.nvl((String)udf.getValue("dlvID"));
    while (reqNme.hasMoreElements()) {
      String paramNm = (String)reqNme.nextElement();
      if (paramNm.indexOf("cb_memo") > -1) {
        String val = req.getParameter(paramNm);
        
        if (saleId.equals("")) {
          saleId = val;
        } else {
          saleId = saleId + "," + val;
        }
      }
    }
    HashMap prp = info.getPrp();
    ArrayList boxPList = (ArrayList)prp.get("BOX_IDP");
    ArrayList boxDList = (ArrayList)prp.get("BOX_IDD");
    double exh_rte = 1.0D;
   
    String mprpStr = "";
    String mdlPrpsQ = "Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||\nUpper(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT')) \nstr From Rep_Prp Rp Where rp.MDL = ? and rp.flg=? order by srt ";
    

    ArrayList ary = new ArrayList();
    ary.add("MIX_DLV_VW");
      ary.add("Y");
    ArrayList outLst = db.execSqlLst("mprp ", mdlPrpsQ, ary);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
      String val = util.nvl(rs.getString("str"));
      mprpStr = mprpStr + " " + val;
    }
    

    rs.close();
    pst.close();
    String getPktData = " with STKDTL as  ( Select b.sk1,b.cert_no certno,a.idn , a.qty ,byr.get_nm(c.nme_idn) byr, a.col,a.clr,a.dia,a.ht,a.ln,a.wd, nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , st.mprp, a.mstk_idn, nvl(fnl_sal, quot) memoQuot, quot, fnl_sal,a.rmk  , a.cts,trunc(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot), 2) vlu, nvl(b.upr, b.cmp) rate, b.rap_rte raprte, a.stt,to_char(trunc(a.cts,2) * b.rap_rte, '99999999990.00') rapVlu, DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), b.vnm) vnm  , to_char(trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2),9990.99) dis, to_char(decode(b.rap_rte, 1, '',trunc(((nvl(fnl_sal, quot)/" + exh_rte + "/greatest(b.rap_rte,1))*100)-100,2)),9990.99) mDis " + " from stk_dtl st,dlv_dtl a, mstk b , mdlv c where st.mstk_idn=b.idn and a.mstk_idn = b.idn and c.idn = a.idn and a.stt in ('DLV','IS') and b.pkt_ty='MIX' and c.typ in ('DLV') and c.stt='IS' ";
    




    params = new ArrayList();
  
    if (!saleId.equals("")) {
      getPktData = getPktData + " and c.idn in (" + saleId + ")  ";
    }
    
    if(!dtefr.equals("") && !dteto.equals("") ){
    getPktData = getPktData + "  and trunc(a.dte)  between to_date('"+dtefr+"' , 'dd-mm-yyyy') and to_date('"+dteto+"' , 'dd-mm-yyyy') ";
      
    }else{
      
    getPktData = getPktData + " \n" + " and trunc(a.dte)>=trunc(sysdate)-" + restrict_sale_rtn_days ;

    }
    
    getPktData = getPktData +  " and exists (select 1 from rep_prp rp where rp.MDL = 'MIX_DLV_VW' and st.mprp = rp.prp)  And st.Grp = 1) " + " Select * from stkDtl PIVOT " + " ( max(atr) " + " for mprp in ( " + mprpStr + " ) ) order by 1 ";
    
    

     outLst = db.execSqlLst(" memo pkts", getPktData, params);
    pst = (PreparedStatement)outLst.get(0);
      rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
      PktDtl pkt = new PktDtl();
      long pktIdn = rs.getLong("mstk_idn");
      
      pkt.setPktIdn(pktIdn);
      pkt.setSaleId(util.nvl(rs.getString("idn")));
      pkt.setRapRte(util.nvl(rs.getString("raprte")));
      pkt.setQty(util.nvl(rs.getString("qty")));
      pkt.setCts(util.nvl(rs.getString("cts")));
      pkt.setRte(util.nvl(rs.getString("rate")));
      pkt.setMemoQuot(util.nvl(rs.getString("memoQuot")));
      pkt.setByrRte(util.nvl(rs.getString("quot")));
      pkt.setFnlRte(util.nvl(rs.getString("fnl_sal")));
      pkt.setDis(rs.getString("dis"));
      pkt.setByrDis(rs.getString("mDis"));
      pkt.setVnm(util.nvl(rs.getString("vnm")));
      pkt.setValue("vlu", util.nvl(rs.getString("vlu")));
        pkt.setValue("byr", util.nvl(rs.getString("byr")));
      String lStt = rs.getString("stt");
        String rmk = util.nvl(rs.getString("rmk"));
        udf.setValue("QTY_" + pktIdn, util.nvl(rs.getString("qty")));
      udf.setValue("COL_" + pktIdn, util.nvl(rs.getString("col")));
      udf.setValue("CLR_" + pktIdn, util.nvl(rs.getString("clr")));
      udf.setValue("DIA_" + pktIdn, util.nvl(rs.getString("dia")));
      udf.setValue("HT_" + pktIdn, util.nvl(rs.getString("ht")));
      udf.setValue("LN_" + pktIdn, util.nvl(rs.getString("ln")));
      udf.setValue("WD_" + pktIdn, util.nvl(rs.getString("wd")));
      udf.setValue("RMK_" + pktIdn,rmk);
      pkt.setStt(lStt);
      udf.setValue("stt_" + pktIdn, lStt);
      pkt.setValue("rapVlu", rs.getString("rapVlu"));
      pkt.setValue("rmk", rs.getString("rmk"));
      for (int v = 0; v < asViewPrp.size(); v++) {
        String vwPrp = (String)asViewPrp.get(v);
        String fldName = vwPrp;
        if (vwPrp.toUpperCase().equals("H&A")) {
          fldName = "H_A";
        }
        if (vwPrp.toUpperCase().equals("COMMENT")) {
          fldName = "COM1";
        }
        if (vwPrp.toUpperCase().equals("REMARKS")) {
          fldName = "REM1";
        }
        if (vwPrp.toUpperCase().equals("COL-DESC")) {
          fldName = "COL_DESC";
        }
        if (vwPrp.toUpperCase().equals("COL-SHADE")) {
          fldName = "COL_SHADE";
        }
        if (vwPrp.toUpperCase().equals("FL-SHADE")) {
          fldName = "FL_SHADE";
        }
        if (vwPrp.toUpperCase().equals("STK-CTG")) {
          fldName = "STK_CTG";
        }
        if (vwPrp.toUpperCase().equals("STK-CODE")) {
          fldName = "STK_CODE";
        }
        if (vwPrp.toUpperCase().equals("SUBSIZE")) {
          fldName = "SUBSIZE1";
        }
        if (vwPrp.toUpperCase().equals("SIZE")) {
          fldName = "SIZE1";
        }
        if (vwPrp.toUpperCase().equals("MIX_SIZE")) {
          fldName = "MIX_SIZE1";
        }
        if (vwPrp.toUpperCase().equals("STK-CTG")) {
          fldName = "STK_CTG";
        }
        if (vwPrp.toUpperCase().equals("CRN-OP")) {
          fldName = "CRN_OP";
        }
        if (vwPrp.toUpperCase().equals("CRTWT")) {
          fldName = "cts";
        }
        if (vwPrp.toUpperCase().equals("RAP_DIS")) {
          fldName = "r_dis";
        }
        if (vwPrp.toUpperCase().equals("RTE")) {
          fldName = "rate";
        }
        if (vwPrp.toUpperCase().equals("RAP_RTE")) {
          fldName = "raprte";
        }
        if (vwPrp.toUpperCase().equals("CERT NO.")) {
          fldName = "certno";
        }
        if (vwPrp.toUpperCase().equals("MEM_COMMENT"))
          fldName = "MEM_COM1";
        String fldVal = util.nvl(rs.getString(fldName));
        
        if (vwPrp.equals("BOX_ID")) {
            if(boxPList.indexOf(fldVal)!=-1){
          String boxDsc = (String)boxDList.get(boxPList.indexOf(fldVal));
          if(rmk.equals(""))
          udf.setValue("RMK_" + pktIdn, boxDsc);
            }
        }
        
        pkt.setValue(vwPrp, fldVal);
      }
      pkts.add(pkt);
    }
    rs.close();
    pst.close();
    req.setAttribute("view", "Y");
    udf.setValue("byrIdn", byrId);
    req.setAttribute("PktList", pkts);
    util.updAccessLog(req, res, "Delivery Return", "Delivery Return load pkt done size " + pkts.size());
    
    return am.findForward("load");
  }
  
  public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception
  {
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    String rtnPg = "sucess";
    if (info != null) {
      Connection conn = info.getCon();
      if (conn != null) {
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm());db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        rtnPg = init(req, res, session, util);
      } else {
        rtnPg = "connExists";
      }
    } else {
      rtnPg = "sessionTO"; }
    if (!rtnPg.equals("sucess")) {
      return am.findForward(rtnPg);
    }
    Enumeration reqNme = req.getParameterNames();
    int ct;
      MixDeliveryUpdateForm udf = (MixDeliveryUpdateForm)af;
    while (reqNme.hasMoreElements()) {
      String paramNm = (String)reqNme.nextElement();
      if (paramNm.indexOf("cb_dlv_") > -1) {
        String val = req.getParameter(paramNm);
        String[] valLst = val.split("_");
        String dlvIdn = valLst[0];
        String stkIdn = valLst[1];
          String qty = util.nvl((String)udf.getValue("QTY_" + stkIdn));
        String col = util.nvl((String)udf.getValue("COL_" + stkIdn));
        String clr = util.nvl((String)udf.getValue("CLR_" + stkIdn));
        String rmk = util.nvl((String)udf.getValue("RMK_" + stkIdn));
        String dia = util.nvl((String)udf.getValue("DIA_" + stkIdn));
        String ht = util.nvl((String)udf.getValue("HT_" + stkIdn));
        String ln = util.nvl((String)udf.getValue("LN_" + stkIdn));
        String wd = util.nvl((String)udf.getValue("WD_" + stkIdn));
          
        
        String updateDlv = "update dlv_dtl set col=? , clr=? ,rmk = ?,qty=?, dia=?, ht=?, ln=?, wd=? where  mstk_idn=? and idn=?";
        ArrayList ary = new ArrayList();
        ary.add(col);
        ary.add(clr);
        ary.add(rmk);
        ary.add(qty);
        ary.add(dia);
        ary.add(ht);
        ary.add(ln);
        ary.add(wd);
        ary.add(stkIdn);
        ary.add(dlvIdn);
        ct = db.execUpd("updateDlvDtl", updateDlv, ary);
      }
    }
    
    req.setAttribute("msg", "Process Done Successfully....");
    return am.findForward("load");
  }
  

  public ActionForward FinalByrMIX(ActionMapping mp, ActionForm fm, HttpServletRequest req, HttpServletResponse res)
  {
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    String rtnPg = "sucess";
    if (info != null) {
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm());db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg = init(req, res, session, util);
    } else {
      rtnPg = "sessionTO"; }
    if (!rtnPg.equals("sucess")) {
      return mp.findForward(rtnPg);
    }
    util.updAccessLog(req, res, "MixSaleDelivery", "fnlByr");
    StringBuffer sb = new StringBuffer();
    String byrId = req.getParameter("bryIdn");
    String typ = util.nvl(req.getParameter("typ"), "DLV");
    String finalByr = "select  distinct form_url_encode(fnl_byr) fnl_byr, FNL_BYR_IDN  from dlv_pndg_v where BYR_IDN=? and typ = ?  and pkt_ty in('MIX','MX') order by fnl_byr";
    
    ArrayList ary = new ArrayList();
    ary.add(byrId);
    ary.add(typ);
    
    try
    {
     ArrayList outLst = db.execSqlLst("sqlVlu", finalByr, ary);
     PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      while (rs.next()) {
        String nme = util.nvl(rs.getString(1), "0").replaceAll("&", "&amp;");
        


        sb.append("<nmes>");
        sb.append("<nmeid>" + util.nvl(rs.getString(2)) + "</nmeid>");
        sb.append("<nme>" + nme + "</nme>");
        sb.append("</nmes>");
      }
      

      rs.close();
      pst.close();
      res.setContentType("text/xml");
      res.setHeader("Cache-Control", "no-cache");
      res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");
    }
    catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
    
    return null;
  }
  
  public ActionForward loadSLTrm(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception
  {
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    String rtnPg = "sucess";
    if (info != null) {
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm());db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg = init(req, res, session, util);
    } else {
      rtnPg = "sessionTO"; }
    if (!rtnPg.equals("sucess")) {
      return am.findForward(rtnPg);
    }
    
    int fav = 0;
    int dmd = 0;
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    ArrayList ary = new ArrayList();
    
    StringBuffer sb = new StringBuffer();
    ArrayList trmsLst = new ArrayList();
    String rlnId = util.nvl(req.getParameter("bryId"));
    ary = new ArrayList();
    ary.add(rlnId);
    String str = " and nme_idn=?";
    if (rlnId.equals("0")) {
      str = "";
      ary = new ArrayList();
    }
    String favSrch = "select b.term||' '||cur||' : '||form_url_encode(byr.get_nm(AADAT_IDN))||'/'||form_url_encode(byr.get_nm(mbrk2_idn)) dtls, nmerel_idn, nme_idn from nmerel a, mtrms b where a.trms_idn = b.idn and a.vld_dte is null  and exists (select 1 from mdlv c where c.stt = 'IS' and (c.nmerel_idn = a.nmerel_idn or a.nmerel_idn = c.fnl_trms_idn)) " + str;
    ArrayList outLst = db.execSqlLst("favSrch", favSrch, ary);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
     ResultSet rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
      fav = 1;
      
      TrmsDao trms = new TrmsDao();
      trms.setRelId(rs.getInt(2));
      trms.setTrmDtl(rs.getString(1));
      sb.append("<trm>");
      sb.append("<trmDtl>" + util.nvl(rs.getString(1).toLowerCase()) + "</trmDtl>");
      sb.append("<relId>" + util.nvl(rs.getString(2)) + "</relId>");
      sb.append("</trm>");
      trmsLst.add(trms);
    }
    if (fav == 1) {
      res.getWriter().write("<trms>" + sb.toString() + "</trms>");
    }
    


    rs.close();
    pst.close();
    return null;
  }
  
  public ActionForward saleIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception
  {
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    String rtnPg = "sucess";
    if (info != null) {
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm());db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg = init(req, res, session, util);
    } else {
      rtnPg = "sessionTO"; }
    if (!rtnPg.equals("sucess")) {
      return am.findForward(rtnPg);
    }
    
    StringBuffer sb = new StringBuffer();
    String nmeId = req.getParameter("nameIdn");
    String typ = req.getParameter("typ");
    String rlnId = util.nvl(req.getParameter("rlnId"));
    ArrayList ary = new ArrayList();
    ary.add(nmeId);
    String sql = "select distinct a.idn , to_char(a.dte, 'dd-Mon HH24:mi') dte from mdlv a , dlv_dtl b,mstk c  where a.stt ='IS' and a.typ in ('" + typ + "') and c.idn=b.mstk_idn and  c.pkt_ty in ('MIX','MX') " + " and a.idn = b.idn and b.stt = 'DLV' " + " and nvl(a.inv_nme_idn, a.nme_idn) = ? ";
    


    if (!rlnId.equals("")) {
      sql = sql + " and nmerel_idn = ? ";
      ary.add(rlnId);
    }
    sql = sql + " order by a.idn ";
    
    ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
     ResultSet rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
      sb.append("<memo>");
      sb.append("<id>" + util.nvl(rs.getString("idn"), "0") + "</id>");
      sb.append("<dte>" + util.nvl(rs.getString("dte"), "") + "</dte>");
      sb.append("</memo>");
    }
    rs.close();
    pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<memos>" + sb.toString() + "</memos>");
    return null;
  }
  
  public ArrayList MIXDlvPrprViw(HttpSession session, DBMgr db)
  {
    ArrayList asViewPrp = (ArrayList)session.getAttribute("mixdlvViewLst");
    try {
      if (asViewPrp == null)
      {
        asViewPrp = new ArrayList();
        
        ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MIX_DLV_VW' and flg='Y' order by rnk ", new ArrayList());
        
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs1 = (ResultSet)outLst.get(1);
        while (rs1.next())
        {
          asViewPrp.add(rs1.getString("prp"));
        }
        rs1.close();
        pst.close();
        session.setAttribute("mixdlvViewLst", asViewPrp);
      }
    }
    catch (SQLException sqle)
    {
      sqle.printStackTrace();
    }
    
    return asViewPrp;
  }
  
  public String init(HttpServletRequest req, HttpServletResponse res, HttpSession session, DBUtil util) {
    String rtnPg = "sucess";
    String invalide = "";
    String connExists = util.nvl(util.getConnExists());
    if (!connExists.equals("N"))
      invalide = util.nvl(util.chkTimeOut(), "N");
    if (session.isNew())
      rtnPg = "sessionTO";
    if (connExists.equals("N"))
      rtnPg = "connExists";
    if (invalide.equals("Y"))
      rtnPg = "chktimeout";
    if (rtnPg.equals("sucess")) {
      boolean sout = util.getLoginsession(req, res, session.getId());
      if (!sout) {
        rtnPg = "sessionTO";
        System.out.print("New Session Id :=" + session.getId());
      } else {
        rtnPg = util.checkUserPageRights("", util.getFullURL(req));
        if (rtnPg.equals("unauthorized")) {
          util.updAccessLog(req, res, "Mix Assort Rtn", "Unauthorized Access");
        } else
          util.updAccessLog(req, res, "Mix Assort Rtn", "init");
      }
    }
    return rtnPg;
  }
}
 