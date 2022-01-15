package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.ByrDao;

import ft.com.dao.MAddr;
import ft.com.dao.MNme;
import ft.com.dao.PktDtl;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
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

public class XlDeliveryAction extends DispatchAction {

    public XlDeliveryAction() {
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
      SaleDeliveryForm udf = (SaleDeliveryForm) af;
      
      ArrayList byrList = new ArrayList();
      ResultSet rs      = null;
      String    getByr  = "select  distinct byr , nme_idn from sal_pndg_v where typ='SL' and pkt_ty in('NR') order by byr";

      rs = db.execSql("byr", getByr, new ArrayList());

      while (rs.next()) {
          ByrDao byr = new ByrDao();

          byr.setByrIdn(rs.getInt("nme_idn"));
          byr.setByrNme(rs.getString("byr"));
          byrList.add(byr);
      }
      rs.close();
      udf.setByrLst(byrList);
      
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
      HashMap pageDtl=(HashMap)allPageDtl.get("XLDELIVERY_CONFIRMATION");
      if(pageDtl==null || pageDtl.size()==0){
      pageDtl=new HashMap();
      pageDtl=util.pagedef("XLDELIVERY_CONFIRMATION");
      allPageDtl.put("XLDELIVERY_CONFIRMATION",pageDtl);
      }
      info.setPageDetails(allPageDtl);
      return am.findForward("load");
      }
  }
  
  public ActionForward loadPkt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      SaleDeliveryForm udf = (SaleDeliveryForm) af;
      SearchQuery srchQuery = new SearchQuery();
      info.setDlvPktList(new ArrayList());
      ArrayList pkts        = new ArrayList();
      String    flnByr      = util.nvl(udf.getPrtyIdn());
      String    saleId      = "";
      String    vnmLst      = util.nvl((String)udf.getValue("vnmLst"));
      ResultSet rs          = null;
      ArrayList    params      = null;
      boolean valid = false,sl_typ=true;
      HashMap avgdtl=new HashMap();
      Enumeration reqNme = req.getParameterNames();
      saleId=util.nvl(req.getParameter("saleId"));
      while (reqNme.hasMoreElements()) {
          String paramNm = (String) reqNme.nextElement();
         if (paramNm.indexOf("cb_memo") > -1) {
              String val = req.getParameter(paramNm);

              if (saleId.equals("")) {
                  saleId = val;
              } else {
                 saleId = saleId + "," + val;
              }
          }
      }
      String typ = util.nvl((String)udf.getValue("typ"),"ALL");
        String sttStr = "a.stt in ('SL','PR') ";
       if(!typ.equals("ALL")){
            sttStr ="a.stt in ('"+typ+"') ";
          }
      if(saleId.equals("")){
         boolean isRtn = true;
         String txtSaleIdn = util.nvl((String)udf.getValue("saleIdn"));
         if(!txtSaleIdn.equals("") && txtSaleIdn.length()>0 ){
           int cnt=0;
           saleId = util.getVal(txtSaleIdn);
           saleId = saleId.replaceAll("'", "");
           rs = db.execSql("check", "select distinct nmerel_idn from msal where idn in (" + saleId + ")", new ArrayList());

           while (rs.next()) {
             cnt++;
           }
             rs.close();
           if(cnt==1){
             isRtn =false;
           }
               rs.close();
         }
          if(vnmLst.length()>0){
          int cnt=0;
        
              String pktLst = util.getVnm(vnmLst);
              String checkSql ="select distinct c.nmerel_idn  from jansal a, mstk b , msal c " + 
              "where a.mstk_idn = b.idn and c.idn = a.idn  and "+sttStr+" and c.typ in ('SL') and c.stt='IS' and b.pkt_ty in('NR') " + 
              " and  ( b.vnm in ("+pktLst+") or b.tfl3 in ("+pktLst+"))";
              ResultSet rs1 = db.execSql("check rel", checkSql, new ArrayList());
              while(rs1.next()){
                  cnt++;
                 
              }
              rs1.close();
              if(cnt==1){   
                  isRtn = false;
                  String saleIdSql = "select distinct c.idn  from jansal a, mstk b , msal c " + 
                  "  where a.mstk_idn = b.idn and c.idn = a.idn  and "+sttStr+" and c.typ in ('SL') and c.stt='IS'  and b.pkt_ty in('NR')  " + 
                  "  and  ( b.vnm in ("+pktLst+") or b.tfl3 in ("+pktLst+")) ";
                  rs1 = db.execSql("saleID",saleIdSql, new ArrayList());
                  while(rs1.next()){
                      String salIdnval = util.nvl(rs1.getString("idn"));
                      if (saleId.equals("")) {
                          saleId = salIdnval;
                      } else {
                         saleId = saleId + "," + salIdnval;
                      }
                  }
                  rs1.close();
              }
          }
          if(isRtn){
              req.setAttribute("RTMSG", "Please verify packets");
             return load(am, af, req, res);
          }
      }
      String getAvgDis = "select sum(a.qty) qty,sum(a.cts) cts,trunc(sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)), 2) vlu,trunc(((sum(trunc(a.cts,2)*(nvl(a.fnl_sal, a.quot)/c.exh_rte)) / sum(trunc(a.cts,2)*b.rap_rte))*100) - 100, 2) avg_dis ," +
                        " trunc(((sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts,2))))) avg_Rte , a.stt from jansal a, mstk b,msal c " + 
                         " where a.mstk_idn = b.idn and a.idn=c.idn and c.stt='IS'  and c.typ in ('SL') and "+sttStr+" " + 
                          " and a.idn in (" + saleId + ") and b.stt in ('MKSL') ";
      if(!vnmLst.equals(""))
      getAvgDis = getAvgDis+" and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
      
      getAvgDis = getAvgDis+" group by a.stt";
      params = new ArrayList();
      
      ResultSet mrs = db.execSql(" Memo Info", getAvgDis , params);
      while(mrs.next()){
        String stt = util.nvl(mrs.getString("stt"));
        String str="sl_";
        if(stt.equals("PR"))
            str="pr_";
        udf.setValue(str+"avgPrc",mrs.getString("avg_dis"));
        udf.setValue(str+"avgDis",mrs.getString("avg_Rte"));
        udf.setValue(str+"qty",mrs.getString("qty"));
        udf.setValue(str+"cts",mrs.getString("cts"));
        udf.setValue(str+"vlu",mrs.getString("vlu"));
      }
      mrs.close();
      String    getMemoInfo =
          "select  a.nme_idn, a.nmerel_idn,byr.get_trms(a.nmerel_idn) trms, byr.get_nm(a.nme_idn) byr,a.stt, a.inv_nme_idn , a.fnl_trms_idn , a.inv_addr_idn , a.exh_rte exhRte , "
          + " a.typ, a.qty, a.cts, a.vlu, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte from msal a  where a.typ in ('SL') and a.stt='IS' ";
      params = new ArrayList();
      if(!flnByr.equals("") && !flnByr.equals("0")){
      getMemoInfo = getMemoInfo+" and a.inv_nme_idn =?  " ;
      params.add(flnByr);
      }
      if(!saleId.equals("")){
      getMemoInfo = getMemoInfo+" and a.idn in ("+saleId+")  " ;

      }
     mrs = db.execSql(" Memo Info", getMemoInfo, params);

      if (mrs.next()) {
          udf.setNmeIdn(mrs.getInt("nme_idn"));
          udf.setRelIdn(mrs.getInt("nmerel_idn"));
          udf.setByr(mrs.getString("byr"));
          udf.setTyp(mrs.getString("typ"));
          udf.setQty(mrs.getString("qty"));
          udf.setCts(mrs.getString("cts"));
          udf.setVlu(mrs.getString("vlu"));
          udf.setDte(mrs.getString("dte"));
          udf.setInvByrIdn(mrs.getInt("inv_nme_idn"));
          udf.setInvTrmsIdn(mrs.getInt("fnl_trms_idn"));
          udf.setValue("trmsLb", mrs.getString("trms"));
          udf.setInvAddrIdn(mrs.getInt("inv_addr_idn"));
          udf.setByrIdn(String.valueOf(mrs.getInt("nme_idn")));
          udf.setValue("exhRte", util.nvl(mrs.getString("exhRte"),"1")); 
          HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(mrs.getInt("nme_idn")));
          udf.setValue("email",util.nvl((String)buyerDtlMap.get("EML")));
          udf.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
          udf.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
          double exh_rte = Double.parseDouble(util.nvl(mrs.getString("exhRte"),"1"));
//          String getAvgDis = "select sum(a.qty) qty,sum(a.cts) cts,trunc(sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)), 2) vlu,trunc(((sum(trunc(a.cts,2)*(nvl(a.fnl_sal, a.quot)/"+exh_rte+")) / sum(trunc(a.cts,2)*b.rap_rte))*100) - 100, 2) byravg_dis ," +
//                            " trunc(((sum(trunc(a.cts,2)*a.quot) / sum(trunc(a.cts,2))))) orgavg_Rte,trunc(((sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts,2))))) byravg_Rte," + 
//                            "trunc(((sum(trunc(a.cts,2)*(nvl(a.fnl_sal, a.quot)/"+exh_rte+")) / sum(trunc(a.cts,2)*b.rap_rte))*100) - 100, 2) orgavg_dis "+
//                             " from jansal a, mstk b , msal c ,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn  and a.stt in ('SL','PR') and c.typ in ('SL')  and c.nmerel_idn = d.nmerel_idn and c.stt='IS'  and b.pkt_ty in('NR') "; 
//          params = new ArrayList();
//          if(!flnByr.equals("") && !flnByr.equals("0")){
//          getAvgDis = getAvgDis+" and c.inv_nme_idn =?  " ;
//          params.add(flnByr);
//          }
//          if(!saleId.equals("")){
//          getAvgDis = getAvgDis+" and c.idn in ("+saleId+")  " ;
//          
//          }
//          if(!vnmLst.equals("")){
//          vnmLst = util.getVnm(vnmLst);
//          getAvgDis = getAvgDis + " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
//          }
//          mrs = db.execSql(" Memo Info", getAvgDis , params);
//          if(mrs.next()){
//              avgdtl.put("ByrDis",util.nvl(mrs.getString("byravg_dis")));
//              avgdtl.put("orgDis",util.nvl(mrs.getString("orgavg_dis")));
//              avgdtl.put("Byr Quot",util.nvl(mrs.getString("byravg_Rte")));
//              avgdtl.put("org Quot",util.nvl(mrs.getString("orgavg_Rte")));
//          }
//          mrs.close();
      String getPktData =
          " select a.idn , a.qty , mstk_idn, nvl(a.fnl_sal, a.quot) memoQuot, a.quot, nvl(a.fnl_sal, a.quot) fnl_sal,to_char(trunc(A.cts,2) * a.quot, '99999990.00') orgamt,to_char(trunc(A.cts,2) * nvl(a.fnl_sal, a.quot), '99999990.00') byramt "+
          " , trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)) inc_dys "+
          " , trunc(nvl(a.fnl_sal, a.quot)*trunc(a.cts,2) *((trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)))*12/100/365),2) inc_vlu "
          + " , a.cts, nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte, a.stt , b.vnm , b.tfl3,b.cert_lab  "
          + "  , decode(b.rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)) dis,  decode(b.rap_rte, 1, '',trunc(((nvl(a.fnl_sal, a.quot)/"+exh_rte+"/greatest(b.rap_rte,1))*100)-100,2)) mDis "
          + "  ,decode(b.rap_rte, 1, '',trunc(((a.quot/"+exh_rte+"/greatest(b.rap_rte,1))*100)-100,2)) oDis "
          + " from jansal a, mstk b , msal c ,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn and "+sttStr+" and c.typ in ('SL')  and c.nmerel_idn = d.nmerel_idn and c.stt='IS'  and b.pkt_ty in('NR') ";
      params = new ArrayList();
      if(!flnByr.equals("") && !flnByr.equals("0")){
      getPktData = getPktData+" and c.inv_nme_idn =?  " ;
      params.add(flnByr);
      }
      if(!saleId.equals("")){
      getPktData = getPktData+" and c.idn in ("+saleId+")  " ;
    
      }
      if(!vnmLst.equals("")){
      getPktData = getPktData + " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
      }
      getPktData = getPktData +" order by  b.sk1 ";
      rs = db.execSql(" memo pkts", getPktData, params);

      while (rs.next()) {
          PktDtl pkt    = new PktDtl();
          long   pktIdn = rs.getLong("mstk_idn");

          pkt.setPktIdn(pktIdn);
          pkt.setSaleId(util.nvl(rs.getString("idn")));
          pkt.setRapRte(util.nvl(rs.getString("rap_rte")));
          pkt.setQty(util.nvl(rs.getString("qty")));
          pkt.setCts(util.nvl(rs.getString("cts")));
          pkt.setRte(util.nvl(rs.getString("rte")));
          pkt.setMemoQuot(util.nvl(rs.getString("memoQuot")));
          pkt.setByrRte(util.nvl(rs.getString("quot")));
          pkt.setFnlRte(util.nvl(rs.getString("fnl_sal")));
          pkt.setDis(rs.getString("dis"));
          pkt.setByrDis(rs.getString("mDis"));
          pkt.setVnm(util.nvl(rs.getString("vnm")));
          pkt.setInc_day(util.nvl(rs.getString("inc_dys")));
          pkt.setInc_vlu(util.nvl(rs.getString("inc_vlu")));
          pkt.setValue("inc_dys", util.nvl(rs.getString("inc_dys")));
          pkt.setValue("inc_vlu", util.nvl(rs.getString("inc_vlu")));
          pkt.setValue("orgamt",util.nvl(rs.getString("orgamt")));  
          pkt.setValue("byramt",util.nvl(rs.getString("byramt")));
          pkt.setValue("orgDis",util.nvl(rs.getString("oDis"))); 
          String lStt = util.nvl(rs.getString("stt")).trim();
          pkt.setStt(lStt);
          udf.setValue("stt_" + pktIdn, lStt);
          pkt.setValue("cert_lab",util.nvl(rs.getString("cert_lab")));
          String vnm = util.nvl(rs.getString("vnm"));
          String tfl3 = util.nvl(rs.getString("tfl3"));
          if(!vnmLst.equals("")){
              if(vnmLst.indexOf(tfl3)!=-1 && !tfl3.equals("")){
                  if(vnmLst.indexOf("'")==-1)
                      vnmLst =  vnmLst.replaceAll(tfl3,"");
                  else
                      vnmLst =  vnmLst.replaceAll("'"+tfl3+"'", "");
              } else if(vnmLst.indexOf(vnm)!=-1 && !vnm.equals("")){
                  if(vnmLst.indexOf("'")==-1)
                      vnmLst =  vnmLst.replaceAll(vnm,"");
                  else
                      vnmLst =  vnmLst.replaceAll("'"+vnm+"'", "");
              }  
          }
             
             
          String getPktPrp = " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                             + " from stk_dtl a, mprp b, rep_prp c "
                             + " where a.mprp = b.prp and a.grp=1 and b.prp = c.prp and c.mdl = 'MEMO_RTRN' and a.mstk_idn = ? "
                             + " order by c.rnk, c.srt ";

          params = new ArrayList();
          params.add(Long.toString(pktIdn));

          ResultSet rs1 = db.execSql(" Pkt Prp", getPktPrp, params);

          while (rs1.next()) {
              String lPrp = rs1.getString("mprp");
              String lVal = rs1.getString("val");
              if(lPrp.equals("CRTWT"))
                  lVal = rs.getString("cts");
              pkt.setValue(lPrp, lVal);
          }
          rs1.close();
          pkts.add(pkt);
          if(sl_typ){
              rs1 = db.execSql(" Pkt Prp", "select val from stk_dtl where mstk_idn=? and mprp='SL_TYP' and grp=1", params);
              while (rs1.next()) {
              udf.setValue("sale_typ", rs1.getString("val"));
              }
              rs1.close();
              sl_typ=false;
          }
      }
          rs.close();
      }
      mrs.close();
      if(!vnmLst.equals("")){
          vnmLst = util.pktNotFound(vnmLst);
          req.setAttribute("vnmNotFnd", vnmLst);
      }

      load(am, af, req, res);

       ArrayList trmList = new ArrayList();
       ArrayList ary = new ArrayList();
      trmList = srchQuery.getTermALL(req,res, udf.getNmeIdn());
      udf.setInvTrmsLst(trmList);
      udf.setValue("invByrTrm",udf.getInvTrmsIdn());
      ArrayList byrList = new ArrayList();
      String    getByr  =
          "select nme_idn,  byr.get_nm(nme_idn) byr from mnme a " + " where nme_idn = ? "
          + " or exists (select 1 from nme_grp_dtl c, nme_grp b where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn "
          + " and b.nme_idn = ? and b.typ = 'BUYER' and c.vld_dte is null) " + " order by 2 ";

      ary = new ArrayList();
      ary.add(String.valueOf(udf.getNmeIdn()));
      ary.add(String.valueOf(udf.getNmeIdn()));
      rs = db.execSql("byr", getByr, ary);
     int nmeIdn = 0;
      while (rs.next()) {
          ByrDao byr = new ByrDao();
         if(nmeIdn==0)
           nmeIdn = rs.getInt("nme_idn");
          byr.setByrIdn(rs.getInt("nme_idn"));

          String nme = util.nvl(rs.getString("byr"));

          if (nme.indexOf("&") > -1) {
              nme = nme.replaceAll("&", "&amp;");
          }

          byr.setByrNme(nme);
          byrList.add(byr);
      }
      rs.close();
      udf.setInvByrLst(byrList);
      udf.setValue("invByrIdn", udf.getInvByrIdn());
      String brokerSql =
          "select   byr.get_nm(mbrk1_idn) brk1  , mbrk1_comm , mbrk1_paid , byr.get_nm(mbrk2_idn) brk2  ,mbrk2_comm , mbrk2_paid, byr.get_nm(mbrk3_idn) brk3  ,"
          + "mbrk3_comm , mbrk3_paid, byr.get_nm(mbrk4_idn) brk4  ,mbrk4_comm , mbrk4_paid , byr.get_nm(aadat_idn) aaDat  , aadat_paid , aadat_comm  from nmerel where nmerel_idn = ?";

      ary = new ArrayList();
      ary.add(String.valueOf(udf.getRelIdn()));
      rs = db.execSql("", brokerSql, ary);

      if (rs.next()) {
          udf.setValue("brk1", rs.getString("brk1"));
          udf.setValue("brk2", rs.getString("brk2"));
          udf.setValue("brk3", rs.getString("brk3"));
          udf.setValue("brk1comm", rs.getString("mbrk1_comm"));
          udf.setValue("brk2comm", rs.getString("mbrk2_comm"));
          udf.setValue("brk3comm", rs.getString("mbrk3_comm"));
          udf.setValue("brk4comm", rs.getString("mbrk4_comm"));
          udf.setValue("brk1paid", rs.getString("mbrk1_paid"));
          udf.setValue("brk2paid", rs.getString("mbrk2_paid"));
          udf.setValue("brk3paid", rs.getString("mbrk3_paid"));
          udf.setValue("brk4paid", rs.getString("mbrk4_paid"));
          udf.setValue("aaDat", rs.getString("aaDat"));
          udf.setValue("aadatpaid", rs.getString("aadat_paid"));
          udf.setValue("aadatcomm", rs.getString("aadat_comm"));
      }
      rs.close();
      ary = new ArrayList();

      String sql =
          "select a.addr_idn , unt_num, bldg ||''|| street ||''|| landmark ||''|| area ||''|| b.city_nm||''|| c.country_nm addr "
          + " from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn = ?  and a.vld_dte is null order by a.dflt_yn desc ";

      ary.add(String.valueOf(nmeIdn));

      ArrayList maddrList = new ArrayList();

      rs = db.execSql("memo pkt", sql, ary);
      
      while (rs.next()) {
          MAddr addr = new MAddr();

          addr.setIdn(rs.getString("addr_idn"));
          addr.setAddr(rs.getString("addr"));
          maddrList.add(addr);
      }
      rs.close();
  //        String bankSql = "select nme_idn, fnme  from mnme  where 1 = 1 and vld_dte is null  and typ  in('GROUP','BANK')";
  //        ArrayList bankList = new ArrayList();
  //        ArrayList groupList = new ArrayList();
  //        rs = db.execSql("bank Sql", bankSql, new ArrayList());
  //        while(rs.next()){
  //            MAddr addr = new MAddr();
  //            addr.setIdn(rs.getString("nme_idn"));
  //            addr.setAddr(rs.getString("fnme"));
  //            bankList.add(addr);
  //        }
  //        rs.close();
  //        String companyQ="select a.nme_idn, a.fnme  from mnme A , nme_dtl b where 1 = 1  " +
  //        "and a.nme_idn=b.nme_idn and  b.mprp='PERFORMA' and b.txt='YES' " +
  //        "and a.vld_dte is null  and typ = 'GROUP'";
  //        rs = db.execSql("Group Sql", companyQ, new ArrayList());
  //        while(rs.next()){
  //            MAddr addr = new MAddr();
  //            addr.setIdn(rs.getString("nme_idn"));
  //            addr.setAddr(rs.getString("fnme"));
  //            groupList.add(addr);
  //        }
  //        rs.close();
      ArrayList bnkAddrList = new ArrayList();
          boolean dfltbankgrp=true;
          String banknmeIdn=  "" ;
          ary = new ArrayList();
          ary.add(String.valueOf(nmeIdn));
          String setbnkCouQ="select bnk_idn,courier from  mdlv where idn in(select max(idn) from mdlv where nme_idn=?)";
          rs = db.execSql("setbnkCouQ", setbnkCouQ, ary);
          if(rs.next()){
              banknmeIdn=util.nvl(rs.getString("bnk_idn"));
              udf.setValue("courier", util.nvl(rs.getString("courier"),"NA"));
          }
          rs.close();
          if(!banknmeIdn.equals("")){
              String defltBnkQ="select b.nme_idn bnkidn,a.addr_idn addr_idn,  a.bldg ||''|| a.street ||''|| a.landmark ||''|| a.area addr \n" + 
              "from maddr a, mnme b\n" + 
              "where 1 = 1 \n" + 
              "and a.nme_idn = b.nme_idn  and b.typ in('GROUP','BANK')\n" + 
              "and b.nme_idn = ? order by a.dflt_yn desc";
              ary = new ArrayList();
              ary.add(banknmeIdn);
              rs = db.execSql("defltBnkQ", defltBnkQ, ary);
              while(rs.next()){
                  udf.setValue("bankIdn", util.nvl(rs.getString("bnkidn"),"NA"));
                  MAddr addr = new MAddr();
                  addr.setIdn(rs.getString("addr_idn"));
                  addr.setAddr(rs.getString("addr"));
                  bnkAddrList.add(addr);
              }
              rs.close();   
              dfltbankgrp=false;
          }
          if(dfltbankgrp){
      String defltBnkQ="select c.nme_idn bnkidn,b.mprp,b.txt,a.addr_idn addr_idn,  a.bldg ||''|| a.street ||''|| a.landmark ||''|| a.area addr  \n" + 
      "        from maddr a,nme_dtl b , mnme c\n" + 
      "        where 1 = 1 and a.nme_idn=b.nme_idn\n" + 
      "        and a.nme_idn = c.nme_idn  and c.typ in('GROUP','BANK')\n" + 
      "        and b.mprp='PERFORMABANK' and b.txt='Y'\n" + 
      "        and b.vld_dte is null";
      rs = db.execSql("defltBnkQ", defltBnkQ, new ArrayList());
      while(rs.next()){
          udf.setValue("bankIdn", util.nvl(rs.getString("bnkidn"),"NA"));
          MAddr addr = new MAddr();
          addr.setIdn(rs.getString("addr_idn"));
          addr.setAddr(rs.getString("addr"));
          bnkAddrList.add(addr);
      }
      rs.close();
          }
      int invAddIdn=0;
      String validQ="Select Inv_Addr_Idn,Nmerel_Idn,Bank_Id, Courier,Co_Idn From Msal Where Idn in ("+saleId+") And Inv_Addr_Idn Is Not Null And Nmerel_Idn Is Not Null " +
          " and Bank_Id is not null and Courier is not null and co_idn is not null having count(*)=1 Group By Inv_Addr_Idn,Nmerel_Idn,Bank_Id,Courier,co_idn";
      rs = db.execSql("validQ", validQ, new ArrayList());
      while (rs.next()) {
      valid = true;
          invAddIdn = rs.getInt("Inv_Addr_Idn");
      }
          rs.close();
      if(!valid){
      bnkAddrList=new ArrayList();    
      String setQ="Select C.Nme_Idn bnkidn,d.courier,A.Addr_Idn addr_idn,  A.Bldg ||''|| A.Street ||''|| A.Landmark ||''|| A.Area addr \n" + 
      "From Maddr A,Nme_Dtl B , Mnme C,Msal D\n" + 
      "Where D.Idn In ("+saleId+")  And A.Nme_Idn=d.Bank_Id\n" + 
      "And B.Nme_Idn=d.Bank_Id And C.Nme_Idn=d.Bank_Id\n" + 
      "And A.Nme_Idn=B.Nme_Idn\n" + 
      "and a.nme_idn = c.nme_idn \n" + 
      "and b.vld_dte is null"; 
      rs = db.execSql("setQ", setQ, new ArrayList());
      while(rs.next()){
      udf.setValue("bankIdn", util.nvl(rs.getString("bnkidn"),"NA"));
      udf.setValue("courier", util.nvl(rs.getString("courier")));
      MAddr addr = new MAddr();
      addr.setIdn(rs.getString("addr_idn"));
      addr.setAddr(rs.getString("addr"));
      bnkAddrList.add(addr);
      }
          rs.close();
      }
  //        ArrayList courierList = new ArrayList();
  //        String courierQ="select chr_fr,dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and\n" +
  //        "             b.mdl = 'JFLEX' and b.nme_rule = 'COURIER' and a.til_dte is null order by a.srt_fr";
  //        rs = db.execSql("courierQ", courierQ, new ArrayList());
  //        while(rs.next()){
  //            MAddr addr = new MAddr();
  //            addr.setIdn(util.nvl(rs.getString("chr_fr")));
  //            addr.setAddr(util.nvl(rs.getString("dsc")));
  //            courierList.add(addr);
  //        }
  //        rs.close();
      ArrayList chargesLst=new ArrayList();
      String chargesQ="Select  c.idn,c.typ,c.dsc,c.stg optional,c.flg,c.fctr,c.db_call,c.rmk  From Rule_Dtl A, Mrule B , Charges_Typ C Where A.Rule_Idn = B.Rule_Idn And \n" + 
      "       c.typ = a.Chr_Fr and b.mdl = 'JFLEX' and b.nme_rule = 'DLV_CHARGES' and c.stt='A' and a.til_dte is null order by a.srt_fr , a.dsc , a.chr_fr";
      rs = db.execSql("chargesQ", chargesQ, new ArrayList());
      while(rs.next()){
          HashMap dtl=new HashMap();
          dtl.put("idn",util.nvl(rs.getString("idn")));
          dtl.put("dsc",util.nvl(rs.getString("dsc")));
          dtl.put("autoopt",util.nvl(rs.getString("optional")));
          dtl.put("flg",util.nvl(rs.getString("flg")));
          dtl.put("typ",util.nvl(rs.getString("typ")));
          dtl.put("fctr",util.nvl(rs.getString("fctr")));
          dtl.put("fun",util.nvl(rs.getString("db_call")));
          dtl.put("rmk",util.nvl(rs.getString("rmk")));
          chargesLst.add(dtl);
      }
      rs.close();
      HashMap setcharge=new HashMap();
      String chQ="Select A.Typ Typ,a.flg mod,b.charges From Charges_Typ A,Trns_Charges B\n" + 
      "      Where A.Idn=B.Charges_Idn And A.Stt='A' And nvl(B.Flg,'Y')='Y' And Ref_Idn In ("+saleId+")\n" + 
      "      and ref_typ='SAL'\n" + 
      "      GROUP BY A.Typ,a.flg,b.charges";
          
      rs = db.execSql("chQ", chQ, new ArrayList());
      while(rs.next()){
      String chtyp=util.nvl(rs.getString("Typ"));
      String mode=util.nvl(rs.getString("mod"));
      String charges=util.nvl(rs.getString("charges"));
      if(mode.equals("AUTO")){
      setcharge.put(chtyp,"Y");
      udf.setValue(chtyp+"_AUTOOPT","Y");
      }else if(mode.equals("MNL")){
       udf.setValue(chtyp+"_save",charges);  
       setcharge.put(chtyp,charges);
      }
      }
      rs.close();
      String salCharge = " Select A.Typ typ, to_char(Sum(B.Charges),'99999990.00') charges,a.app_typ,a.inv ,a.stg, b.rmk, A.srt , a.flg From Charges_Typ A,Trns_Charges B\n" + 
        " where a.idn=b.charges_idn and a.stt='A' and  ref_typ='SAL' and ref_idn  In ("+saleId+") and App_Typ='TTL'\n" + 
        " GROUP BY A.Typ, a.app_typ, a.inv, a.stg, b.rmk, A.srt, a.flg order by A.srt";
        rs = db.execSql("salCharge", salCharge, new ArrayList());
        while(rs.next()){
          String chtyp=util.nvl(rs.getString("Typ"));
          String charges = util.nvl(rs.getString("charges"));
          udf.setValue(chtyp+"_TTL",charges);  
        }
        rs.close();
      ArrayList thruBankList = new ArrayList();
      String thruBankSql = "select b.nme_dtl_idn , b.txt from mnme a , nme_dtl b " + 
                           "where a.nme_idn = b.nme_idn and b.mprp like 'THRU_BANK%' and a.nme_idn=? and b.vld_dte is null";
      ary = new ArrayList();
      ary.add(String.valueOf(nmeIdn));
      rs = db.execSql("thruBank", thruBankSql, ary );
      String nmeDtlIdn = "";
      while(rs.next()){
          MNme dtl = new MNme();
          String txt=util.nvl(rs.getString("txt"));
          dtl.setIdn(util.nvl(rs.getString("nme_dtl_idn")));
          txt.replaceAll("\\#"," ");
          dtl.setFnme(txt);
          thruBankList.add(dtl);
          nmeDtlIdn = util.nvl(rs.getString("nme_dtl_idn"));
      }
          rs.close();
      req.setAttribute("setcharge", setcharge);
      req.setAttribute("avgdtl", avgdtl);
      ArrayList groupList = srchQuery.getgroupList(req, res);
      ArrayList bankList = srchQuery.getBankList(req, res);
      ArrayList courierList = srchQuery.getcourierList(req, res);
      session.setAttribute("chargesLst", chargesLst);
      udf.setThruBankList(thruBankList);
      udf.setValue("throubnk", nmeDtlIdn);
      udf.setCourierList(courierList);
      udf.setBnkAddrList(bnkAddrList);
      udf.setGroupList(groupList);
      udf.setBankList(bankList);
      udf.setInvAddLst(maddrList);
      udf.setInvAddrIdn(invAddIdn);
      info.setValue("PKTS", pkts);
      req.setAttribute("view", "Y");

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
      ArrayList params = null;

      SaleDeliveryForm udf = (SaleDeliveryForm) af;

      ArrayList pkts      = new ArrayList();
      String    pktNmsDLV  = "";
      String    pktNmsRT  = "";
      String    pktNmsCAN = "";
      String    pktNmsPR = "";
      String    pktNmsIS = "";
      int       appDlvIdn = 0;
      int       isDlvIdn=0;
      boolean isRtn = false;
      pkts = (ArrayList) info.getValue("PKTS");    // udf.getPkts();

      for (int i = 0; i < pkts.size(); i++) {
          PktDtl pkt     = (PktDtl) pkts.get(i);
          long   lPktIdn = pkt.getPktIdn();
          String saleIdn = pkt.getSaleId();
          String vnm = pkt.getVnm();
          String lStt    = util.nvl((String) udf.getValue("stt_" + lPktIdn));
          if(lStt.equals("DLV") && appDlvIdn == 0){
            ArrayList ary = new ArrayList();

            ary.add(Integer.toString(udf.getNmeIdn()));
            ary.add(Integer.toString(udf.getRelIdn()));
            ary.add(util.nvl((String)udf.getValue("invByrTrm"),Integer.toString(udf.getRelIdn())));
            ary.add(udf.getValue("invByrIdn"));
            ary.add(udf.getValue("invAddr"));
            ary.add("DLV");
            ary.add("IS");
            ary.add(saleIdn);
            ary.add("NN");
            ary.add(util.nvl((String) udf.getValue("aadatpaid"), "Y"));
            ary.add(util.nvl((String) udf.getValue("brk1paid"), "Y"));
            ary.add(util.nvl((String) udf.getValue("brk2paid"), "Y"));
            ary.add(util.nvl((String) udf.getValue("brk3paid"), "Y"));
            ary.add(util.nvl((String) udf.getValue("brk4paid"), "Y"));
            ary.add(util.nvl((String) udf.getValue("bankIdn")));
            ary.add(util.nvl((String) udf.getValue("grpIdn")));
            ary.add(util.nvl((String) udf.getValue("courier")));
            ary.add(util.nvl((String)udf.getValue("throubnk")));
            ArrayList out = new ArrayList();

            out.add("I");

            CallableStatement cst = null;

            cst = db.execCall(
                "Gen_HDR ",
                "dlv_pkg.Gen_Hdr(pByrId => ? , pRlnIdn => ? , pTrmsIdn => ? , pInvNmeIdn => ? , pInvAddrIdn => ? , "
                + "pTyp => ? , pStt => ?,pFrmId => ?, pFlg => ? ,paadat_paid => ?, pMBRK1_PAID => ?, pMBRK2_PAID => ?, pMBRK3_PAID => ? , "
                + " pMBRK4_PAID => ?, bnk_idn =>? , co_idn => ? ,p_courier => ? ,pThruBnk => ? , pIdn => ?) ", ary, out);
            appDlvIdn = cst.getInt(ary.size()+1);
            cst.close();
            cst=null;
          }
          if(lStt.equals("IS") && isDlvIdn == 0){
            ArrayList ary = new ArrayList();

            ary.add(Integer.toString(udf.getNmeIdn()));
            ary.add(Integer.toString(udf.getRelIdn()));
            ary.add(util.nvl((String)udf.getValue("invByrTrm"),Integer.toString(udf.getRelIdn())));
            ary.add(udf.getValue("invByrIdn"));
            ary.add(udf.getValue("invAddr"));
            ary.add("DLV");
            ary.add("IS");
            ary.add(saleIdn);
            ary.add("NN");
            ary.add(util.nvl((String) udf.getValue("aadatpaid"), "Y"));
            ary.add(util.nvl((String) udf.getValue("brk1paid"), "Y"));
            ary.add(util.nvl((String) udf.getValue("brk2paid"), "Y"));
            ary.add(util.nvl((String) udf.getValue("brk3paid"), "Y"));
            ary.add(util.nvl((String) udf.getValue("brk4paid"), "Y"));
            ary.add(util.nvl((String) udf.getValue("bankIdn")));
            ary.add(util.nvl((String) udf.getValue("grpIdn")));
            ary.add(util.nvl((String) udf.getValue("courier")));
            ary.add(util.nvl((String)udf.getValue("throubnk")));
            ArrayList out = new ArrayList();

            out.add("I");

            CallableStatement cst = null;

            cst = db.execCall(
                "Gen_HDR ",
                "dlv_pkg.Gen_Hdr(pByrId => ? , pRlnIdn => ? , pTrmsIdn => ? , pInvNmeIdn => ? , pInvAddrIdn => ? , "
                + "pTyp => ? , pStt => ?,pFrmId => ?, pFlg => ? ,paadat_paid => ?, pMBRK1_PAID => ?, pMBRK2_PAID => ?, pMBRK3_PAID => ? , "
                + " pMBRK4_PAID => ?, bnk_idn =>? , co_idn => ? ,p_courier => ? ,pThruBnk => ? , pIdn => ?) ", ary, out);
            isDlvIdn = cst.getInt(ary.size()+1);
            cst.close();
            cst=null;
          }

          if (lStt.equals("DLV") || lStt.equals("IS") ) {
               int dlvIdn = appDlvIdn;
               if(lStt.endsWith("IS")){
               dlvIdn = isDlvIdn;
               pktNmsIS= pktNmsIS + "," +vnm;
               }else{
               pktNmsDLV= pktNmsDLV + "," +vnm;    
               }
              params = new ArrayList();
              params.add("DLV");
              params.add(Long.toString(lPktIdn));
              params.add(saleIdn);

              int upJan = db.execUpd("updateJAN", "update jansal set stt=? , dte_sal=sysdate where mstk_idn=? and idn= ? ", params);

              params = new ArrayList();
              params.add(String.valueOf(dlvIdn));
              params.add(saleIdn);
              params.add(Long.toString(lPktIdn));
              params.add(pkt.getQty());
              params.add(pkt.getCts());
              params.add(pkt.getMemoQuot());
              params.add(lStt);

              int SalPkt =
                  db.execCall(
                      "sale from memo",
                      " DLV_PKG.Dlv_Pkt(pIdn => ? , pSaleIdn => ?, pStkIdn => ?, pQty => ? , pCts => ?, pFnlSal => ?, pStt => ?)",
                      params);
                  String sl_typ = util.nvl((String)udf.getValue("sale_typ"));
                  if(!sl_typ.equals("")){
                        String cert_lab = util.nvl((String)pkt.getValue("cert_lab"));
                        ArrayList ary = new ArrayList();
                        ary.add(Long.toString(lPktIdn));
                        ary.add("SL_TYP");
                        ary.add(sl_typ);
                        ary.add(cert_lab);
                        ary.add("C");
                        ary.add("1");
                        String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?, pPrpTyp => ?, pgrp => ? )";
                        int ct = db.execCall("stockUpd",stockUpd, ary);
                  }
              } else if (lStt.equals("RT")) {
                      String rmk = util.nvl((String)udf.getValue("rmk_"+Long.toString(lPktIdn)));
                      params = new ArrayList();
                      params.add(saleIdn);
                      params.add(Long.toString(lPktIdn));
                      params.add(rmk);
                      int ct = db.execCall(" App Pkts", " DLV_PKG.rtn_pkt(pIdn => ? , pStkIdn => ?,pRem => ?)", params);
                      pktNmsRT = pktNmsRT + "," +vnm;
          } else if (lStt.equals("CAN")) {
                  params = new ArrayList();
                  params.add(saleIdn);
                  params.add(Long.toString(lPktIdn));
                  int ct = db.execCall(" Cancel Pkts ", "sl_pkg.Cancel_Pkt( pIdn =>?, pStkIdn=> ?)", params);
                  pktNmsCAN = pktNmsCAN + "," +vnm;
          } else if (lStt.equals("PR")) {
            params = new ArrayList();
            params.add(Long.toString(lPktIdn));
            params.add(lStt);
            params.add(saleIdn);
            int ct = db.execCall(" Cancel Pkts ", "DLV_PKG.DLV_TRNS(pStkIdn =>?, pStt => ?,  pSalIdn =>? )", params);
            pktNmsPR = pktNmsPR + "," +vnm;
          }
          
          
          params = new ArrayList();
          params.add(saleIdn);
          int upQuery = db.execCall("calQuot", "SL_PKG.UPD_QTY(pIdn => ? , pTyp => 'DLV')", params);
        }
      
     
      if (appDlvIdn != 0) {
          ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
              if(chargesLst!=null && chargesLst.size()>0){
              for(int i=0;i<chargesLst.size();i++){
              HashMap dtl=new HashMap();
              dtl=(HashMap)chargesLst.get(i);
              String idn=(String)dtl.get("idn");
              String dsc=(String)dtl.get("dsc");
              String flg=(String)dtl.get("flg");
              String typcharge=(String)dtl.get("typ");
              String fctr=(String)dtl.get("fctr");
              String fun=(String)dtl.get("fun");
              String autoopt=util.nvl((String)dtl.get("autoopt"));
              String autooptional= util.nvl((String)udf.getValue(typcharge+"_AUTOOPT"));  
              String calcdis= util.nvl((String)udf.getValue(typcharge+"_save"),"0");  
              String vlu= util.nvl((String)udf.getValue("DLV_vluamt"));
              String exhRte=util.nvl((String)udf.getValue("exhRte"),"1");
              String extrarmk= util.nvl((String)udf.getValue(typcharge+"_rmksave")); 
              if(!vlu.equals("") && !vlu.equals("0")){
              if((!calcdis.equals("") && !calcdis.equals("0")) || (autoopt.equals("OPT"))){
              String insertQ="Insert Into Trns_Charges (trns_idn, ref_typ, ref_idn,charges_idn,amt,amt_usd,charges,CHARGES_PCT,net_usd,rmk,stt,flg)\n" + 
              "VALUES (TRNS_CHARGES_SEQ.nextval, 'DLV', ?,?,?,?,?,?,?,?,'A',?)";   
              ArrayList ary=new ArrayList();
              ary.add(String.valueOf(appDlvIdn));
              ary.add(idn);
              ary.add(vlu);
              float amt_usd=Float.parseFloat(vlu)/Float.parseFloat(exhRte);
              ary.add(String.valueOf(amt_usd));
              ary.add(calcdis);
              ary.add(calcdis);
              float net_usd=amt_usd+Float.parseFloat(calcdis);
              ary.add(String.valueOf(net_usd));
              ary.add(extrarmk);
              ary.add(autooptional);
              int ct = db.execUpd("Insert", insertQ, ary);
              }
              }
              }
              }
          params = new ArrayList();
          params.add(String.valueOf(appDlvIdn));

          int calCt = db.execCall("calQuot", "DLV_PKG.Cal_Fnl_Quot(pIdn => ?)", params);

          params = new ArrayList();
          params.add(String.valueOf(appDlvIdn));

          int upQuery = db.execCall("calQuot", "DLV_PKG.UPD_QTY(pIdn => ? )", params);
          req.setAttribute("dlvIdDLV", String.valueOf(appDlvIdn));
          req.setAttribute("DLVMSG", "Packets get Deliver : " + pktNmsDLV + " with delivery Id " + appDlvIdn);
          req.setAttribute("performaLinkDLV","Y");
      }
    if (isDlvIdn != 0) {
        ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
        if(chargesLst!=null && chargesLst.size()>0){
        for(int i=0;i<chargesLst.size();i++){
        HashMap dtl=new HashMap();
        dtl=(HashMap)chargesLst.get(i);
        String idn=(String)dtl.get("idn");
        String dsc=(String)dtl.get("dsc");
        String flg=(String)dtl.get("flg");
        String typcharge=(String)dtl.get("typ");
        String fctr=(String)dtl.get("fctr");
        String fun=(String)dtl.get("fun");
        String autoopt=util.nvl((String)dtl.get("autoopt"));
        String autooptional= util.nvl((String)udf.getValue(typcharge+"_AUTOOPT"));  
        String calcdis= util.nvl((String)udf.getValue(typcharge+"_save"),"0");  
        String vlu= util.nvl((String)udf.getValue("IS_vluamt"));
        String exhRte=util.nvl((String)udf.getValue("exhRte"),"1");
        String extrarmk= util.nvl((String)udf.getValue(typcharge+"_rmksave")); 
        if(!vlu.equals("") && !vlu.equals("0")){
        if((!calcdis.equals("") && !calcdis.equals("0")) || (autoopt.equals("OPT"))){
        String insertQ="Insert Into Trns_Charges (trns_idn, ref_typ, ref_idn,charges_idn,amt,amt_usd,charges,CHARGES_PCT,net_usd,rmk,stt,flg)\n" + 
        "VALUES (TRNS_CHARGES_SEQ.nextval, 'DLV', ?,?,?,?,?,?,?,?,'A',?)";   
        ArrayList ary=new ArrayList();
        ary.add(String.valueOf(isDlvIdn));
        ary.add(idn);
        ary.add(vlu);
        float amt_usd=Float.parseFloat(vlu)/Float.parseFloat(exhRte);
        ary.add(String.valueOf(amt_usd));
        ary.add(calcdis);
        ary.add(calcdis);
        float net_usd=amt_usd+Float.parseFloat(calcdis);
        ary.add(String.valueOf(net_usd));
        ary.add(extrarmk);
        ary.add(autooptional);
        int ct = db.execUpd("Insert", insertQ, ary);
        }
        }
        }
        }
        params = new ArrayList();
        params.add(String.valueOf(isDlvIdn));

        int calCt = db.execCall("calQuot", "DLV_PKG.Cal_Fnl_Quot(pIdn => ?)", params);

        params = new ArrayList();
        params.add(String.valueOf(isDlvIdn));

        int upQuery = db.execCall("calQuot", "DLV_PKG.UPD_QTY(pIdn => ? )", params);
        req.setAttribute("dlvIdIS", String.valueOf(isDlvIdn));
        req.setAttribute("ISMSG", "Packets get Deliver : " + pktNmsIS + " with delivery Without payment Id " + isDlvIdn);
        req.setAttribute("performaLinkIS","Y");
    }
    
      if(!pktNmsRT.equals("")){
          pktNmsRT=pktNmsRT.replaceFirst("\\,","");
          req.setAttribute("RTMSG", "Packets get return: " + pktNmsRT);
      }
      if(!pktNmsCAN.equals("")){
          pktNmsCAN=pktNmsCAN.replaceFirst("\\,","");
          req.setAttribute("CANMSG", "Packets get Cansal: " + pktNmsCAN);
      }
      if(!pktNmsPR.equals("")){
          pktNmsPR=pktNmsPR.replaceFirst("\\,","");
          req.setAttribute("PRMSG", "Payment Recive For Packets : " + pktNmsPR);
      }        
      int accessidn=util.updAccessLog(req,res,"Sale Delivery", "End");    
      req.setAttribute("accessidn", String.valueOf(accessidn));;
      return load(am, af, req, res);
      }
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
                util.updAccessLog(req,res,"Sale Delivery", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Sale Delivery", "init");
            }
            }
            return rtnPg;
            }

}
