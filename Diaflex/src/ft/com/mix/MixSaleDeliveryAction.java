package ft.com.mix;
//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;
import ft.com.dao.ByrDao;
import ft.com.dao.MAddr;
import ft.com.dao.MNme;
import ft.com.dao.PktDtl;
import ft.com.dao.TrmsDao;
import ft.com.marketing.SearchQuery;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.OutputStream;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Vector;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class MixSaleDeliveryAction extends DispatchAction {
  
    public MixSaleDeliveryAction() {
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
          util.updAccessLog(req,res,"MixSaleDelivery", "load");
        MixSaleDeliveryForm udf = (MixSaleDeliveryForm) af;
        
        ArrayList byrList = new ArrayList();
        String    getByr  = "select  distinct byr , nme_idn from sal_pndg_v where typ='SL' and pkt_ty in('MIX','MX') order by byr";

          ArrayList  rsLst = db.execSqlLst("byr", getByr, new ArrayList());
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);

        while (rs.next()) {
            ByrDao byr = new ByrDao();

            byr.setByrIdn(rs.getInt("nme_idn"));
            byr.setByrNme(rs.getString("byr"));
            byrList.add(byr);
        }
        rs.close();
          stmt.close();
        udf.setByrLst(byrList); 
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("MIXDELIVERY_CONFIRMATION");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("MIXDELIVERY_CONFIRMATION");
        allPageDtl.put("MIXDELIVERY_CONFIRMATION",pageDtl);
        }
        info.setPageDetails(allPageDtl);
          util.updAccessLog(req,res,"MixSaleDelivery", "load end");
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
          util.updAccessLog(req,res,"MixSaleDelivery", "loadpkt");
        MixSaleDeliveryForm udf = (MixSaleDeliveryForm) af;
        SearchQuery srchQuery = new SearchQuery();
        info.setDlvPktList(new ArrayList());
        ArrayList pkts        = new ArrayList();
        String    flnByr      = util.nvl(udf.getPrtyIdn());
        String    saleId      = "";
        String    vnmLst      = util.nvl((String)udf.getValue("vnmLst"));
        ArrayList    params      = null;
        boolean valid = false;
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
        
            String app = (String)req.getAttribute("APP");
            if(app!=null)
            saleId = util.nvl((String)req.getAttribute("memoId"));
        if(saleId.equals("")){
            boolean isRtn = true;
            if(vnmLst.length()>0){
            int cnt=0;
          
                String pktLst = util.getVnm(vnmLst);
                String checkSql ="select distinct c.nmerel_idn  from jansal a, mstk b , msal c " + 
                "where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='SL' and c.typ in ('SL') and c.stt='IS' and b.pkt_ty in('MIX','MX') " + 
                " and  ( b.vnm in ("+pktLst+") or b.tfl3 in ("+pktLst+"))";
              ArrayList  rsLst = db.execSqlLst("check rel", checkSql, new ArrayList());
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs1 =(ResultSet)rsLst.get(1);
                while(rs1.next()){
                    cnt++;
                   
                }
                rs1.close();
                stmt.close();
                if(cnt==1){   
                    isRtn = false;
                    String saleIdSql = "select distinct c.idn  from jansal a, mstk b , msal c " + 
                    "  where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='SL' and c.typ in ('SL') and c.stt='IS'  and b.pkt_ty in('MIX','MX')  " + 
                    "  and  ( b.vnm in ("+pktLst+") or b.tfl3 in ("+pktLst+")) ";
                   rsLst = db.execSqlLst("saleID",saleIdSql, new ArrayList());
                  stmt =(PreparedStatement)rsLst.get(0);
                   rs1 =(ResultSet)rsLst.get(1);
                    while(rs1.next()){
                        String salIdnval = util.nvl(rs1.getString("idn"));
                        if (saleId.equals("")) {
                            saleId = salIdnval;
                        } else {
                           saleId = saleId + "," + salIdnval;
                        }
                    }
                    rs1.close();
                    stmt.close();
                }
            }
            if(isRtn){
                req.setAttribute("RTMSG", "Please verify packets");
               return load(am, af, req, res);
            }
        }
        
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
          ArrayList  rsLst = db.execSqlLst(" Memo Info", getMemoInfo, params);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  mrs =(ResultSet)rsLst.get(1);
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
            HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(mrs.getInt("nme_idn")));
            udf.setValue("email",util.nvl((String)buyerDtlMap.get("EML")));
            udf.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
            udf.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
            udf.setValue("exhRte", util.nvl(mrs.getString("exhRte"),"1")); 
            double exh_rte = Double.parseDouble(util.nvl(mrs.getString("exhRte"),"1"));
            String getAvgDis = "select sum(a.qty) qty,sum(a.cts) cts,trunc(sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)), 2) vlu,trunc(((sum(trunc(a.cts,2)*(nvl(a.fnl_sal, a.quot)/"+exh_rte+")) / sum(trunc(a.cts,2)*b.rap_rte))*100) - 100, 2) byravg_dis ," +
                              " trunc(((sum(trunc(a.cts,2)*a.quot) / sum(trunc(a.cts,2))))) orgavg_Rte,trunc(((sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts,2))))) byravg_Rte," + 
                              "trunc(((sum(trunc(a.cts,2)*(nvl(a.fnl_sal, a.quot)/"+exh_rte+")) / sum(trunc(a.cts,2)*b.rap_rte))*100) - 100, 2) orgavg_dis "+
                               " from jansal a, mstk b , msal c ,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='SL' and c.typ in ('SL')  and c.nmerel_idn = d.nmerel_idn and c.stt='IS'  and b.pkt_ty in('MIX','MX') "; 
            params = new ArrayList();
            if(!flnByr.equals("") && !flnByr.equals("0")){
            getAvgDis = getAvgDis+" and c.inv_nme_idn =?  " ;
            params.add(flnByr);
            }
            if(!saleId.equals("")){
            getAvgDis = getAvgDis+" and c.idn in ("+saleId+")  " ;
            
            }
            if(!vnmLst.equals("")){
            vnmLst = util.getVnm(vnmLst);
            getAvgDis = getAvgDis + " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
            }
          ArrayList  rsLst1 = db.execSqlLst(" Memo Info", getAvgDis , params);
          PreparedStatement  stmt1 =(PreparedStatement)rsLst1.get(0);
          ResultSet  rs1 =(ResultSet)rsLst1.get(1);
            if(rs1.next()){
                avgdtl.put("ByrDis",util.nvl(rs1.getString("byravg_dis")));
                avgdtl.put("orgDis",util.nvl(rs1.getString("orgavg_dis")));
                avgdtl.put("Byr Quot",util.nvl(rs1.getString("byravg_Rte")));
                avgdtl.put("org Quot",util.nvl(rs1.getString("orgavg_Rte")));
            }
            rs1.close();
            stmt1.close();
        String getPktData =
            " select a.idn , to_char(c.dte, 'dd-Mon-rrrr') dte, a.qty , mstk_idn, nvl(fnl_sal, quot) memoQuot, quot, nvl(fnl_sal, quot) fnl_sal,to_char(trunc(A.cts,2) * quot, '99999990.00') orgamt,to_char(trunc(A.cts,2) * nvl(fnl_sal, quot), '99999990.00') byramt "+
            " , trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)) inc_dys "+
            " , trunc(nvl(fnl_sal, quot)*trunc(a.cts,2) *((trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)))*12/100/365),2) inc_vlu "
            + " , a.cts, nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte, a.stt , b.vnm , b.tfl3  "
            + "  , decode(rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)) dis,  decode(rap_rte, 1, '',trunc(((nvl(fnl_sal, quot)/"+exh_rte+"/greatest(b.rap_rte,1))*100)-100,2)) mDis "
            + "  ,decode(rap_rte, 1, '',trunc(((quot/"+exh_rte+"/greatest(b.rap_rte,1))*100)-100,2)) oDis "
            + " from jansal a, mstk b , msal c ,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='SL' and c.typ in ('SL')  and c.nmerel_idn = d.nmerel_idn and c.stt='IS'  and b.pkt_ty in('MIX','MX') ";
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
           rsLst1 = db.execSqlLst(" memo pkts", getPktData, params);
           stmt1 =(PreparedStatement)rsLst1.get(0);
         ResultSet rs =(ResultSet)rsLst1.get(1);
        while (rs.next()) {
            PktDtl pkt    = new PktDtl();
            long   pktIdn = rs.getLong("mstk_idn");
            String slIdn=util.nvl(rs.getString("idn"));
            pkt.setPktIdn(pktIdn);
            pkt.setSaleId(slIdn);
            pkt.setValue("dte",util.nvl(rs.getString("dte")));
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
            pkt.setValue("CRTWT", util.nvl(rs.getString("cts")));
            pkt.setValue("inc_dys", util.nvl(rs.getString("inc_dys")));
            pkt.setValue("inc_vlu", util.nvl(rs.getString("inc_vlu")));
            pkt.setValue("orgamt",util.nvl(rs.getString("orgamt")));  
            pkt.setValue("byramt",util.nvl(rs.getString("byramt")));
            pkt.setValue("orgDis",util.nvl(rs.getString("oDis"))); 
            String lStt = util.nvl(rs.getString("stt")).trim();
            pkt.setStt(lStt);
            udf.setValue("stt_"+slIdn+"_" + pktIdn, lStt);
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
                               + " where a.mprp = b.prp and a.grp=1 and b.prp = c.prp and c.mdl = 'MIX_DLV_VW' and a.mstk_idn = ? "
                               + " order by c.rnk, c.srt ";

            params = new ArrayList();
            params.add(Long.toString(pktIdn));

           
          ArrayList rsLst2 = db.execSqlLst(" Pkt Prp", getPktPrp, params);
          PreparedStatement stmt2 =(PreparedStatement)rsLst2.get(0);
          ResultSet rs2 =(ResultSet)rsLst2.get(1);

            while (rs2.next()) {
                String lPrp = rs2.getString("mprp");
                String lVal = rs2.getString("val");
                if(lPrp.equals("CRTWT"))
                    lVal = rs.getString("cts");
                pkt.setValue(lPrp, lVal);
            }
            rs2.close();
            stmt2.close();
            pkts.add(pkt);
        }
            rs.close();
            stmt1.close();
        }
        mrs.close();
          stmt.close();
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
        rsLst = db.execSqlLst("byr", getByr, ary);
        stmt =(PreparedStatement)rsLst.get(0);
       ResultSet  rs =(ResultSet)rsLst.get(1);
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
          stmt.close();
        udf.setInvByrLst(byrList);
        udf.setValue("invByrIdn", udf.getInvByrIdn());
        String brokerSql =
            "select   byr.get_nm(mbrk1_idn) brk1  , mbrk1_comm , mbrk1_paid , byr.get_nm(mbrk2_idn) brk2  ,mbrk2_comm , mbrk2_paid, byr.get_nm(mbrk3_idn) brk3  ,"
            + "mbrk3_comm , mbrk3_paid, byr.get_nm(mbrk4_idn) brk4  ,mbrk4_comm , mbrk4_paid , byr.get_nm(aadat_idn) aaDat  , aadat_paid , aadat_comm  from nmerel where nmerel_idn = ?";

        ary = new ArrayList();
        ary.add(String.valueOf(udf.getRelIdn()));
          rsLst = db.execSqlLst("", brokerSql, ary);
          stmt =(PreparedStatement)rsLst.get(0);
           rs =(ResultSet)rsLst.get(1);
        if (rs.next()) {
            udf.setValue("brk1", rs.getString("brk1"));
            udf.setValue("brk2", rs.getString("brk2"));
            udf.setValue("brk3", rs.getString("brk3"));
            udf.setValue("brk4", rs.getString("brk4"));
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
          stmt.close();
        ary = new ArrayList();

        String sql =
            "select a.addr_idn , unt_num, bldg ||''|| street ||''|| landmark ||''|| area ||''|| b.city_nm||''|| c.country_nm addr "
            + " from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn = ?  and a.vld_dte is null  order by a.dflt_yn desc ";

        ary.add(String.valueOf(nmeIdn));

        ArrayList maddrList = new ArrayList();

          rsLst = db.execSqlLst("memo pkt", sql, ary);
          stmt =(PreparedStatement)rsLst.get(0);
           rs =(ResultSet)rsLst.get(1);
        
        while (rs.next()) {
            MAddr addr = new MAddr();

            addr.setIdn(rs.getString("addr_idn"));
            addr.setAddr(rs.getString("addr"));
            maddrList.add(addr);
        }
        rs.close();
          stmt.close();
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
          rsLst = db.execSqlLst("setbnkCouQ", setbnkCouQ, ary);
          stmt =(PreparedStatement)rsLst.get(0);
           rs =(ResultSet)rsLst.get(1);
            if(rs.next()){
                banknmeIdn=util.nvl(rs.getString("bnk_idn"));
                udf.setValue("courier", util.nvl(rs.getString("courier"),"NA"));
            }
            rs.close();
          stmt.close();
            if(!banknmeIdn.equals("")){
                String defltBnkQ="select b.nme_idn bnkidn,a.addr_idn addr_idn,  a.bldg ||''|| a.street ||''|| a.landmark ||''|| a.area addr \n" + 
                "from maddr a, mnme b\n" + 
                "where 1 = 1 \n" + 
                "and a.nme_idn = b.nme_idn  and b.typ in('GROUP','BANK')\n" + 
                "and b.nme_idn = ? order by a.dflt_yn desc";
                ary = new ArrayList();
                ary.add(banknmeIdn);
              rsLst = db.execSqlLst("defltBnkQ", defltBnkQ, ary);
              stmt =(PreparedStatement)rsLst.get(0);
               rs =(ResultSet)rsLst.get(1);
                while(rs.next()){
                    udf.setValue("bankIdn", util.nvl(rs.getString("bnkidn"),"NA"));
                    MAddr addr = new MAddr();
                    addr.setIdn(rs.getString("addr_idn"));
                    addr.setAddr(rs.getString("addr"));
                    bnkAddrList.add(addr);
                }
                rs.close();  
                stmt.close();
                dfltbankgrp=false;
            }
            if(dfltbankgrp){
        String defltBnkQ="select c.nme_idn bnkidn,b.mprp,b.txt,a.addr_idn addr_idn,  a.bldg ||''|| a.street ||''|| a.landmark ||''|| a.area addr  \n" + 
        "        from maddr a,nme_dtl b , mnme c\n" + 
        "        where 1 = 1 and a.nme_idn=b.nme_idn\n" + 
        "        and a.nme_idn = c.nme_idn  and c.typ in('GROUP','BANK')\n" + 
        "        and b.mprp='PERFORMABANK' and b.txt='Y'\n" + 
        "        and b.vld_dte is null";
              rsLst = db.execSqlLst("defltBnkQ", defltBnkQ, new ArrayList());
              stmt =(PreparedStatement)rsLst.get(0);
               rs =(ResultSet)rsLst.get(1);
        while(rs.next()){
            udf.setValue("bankIdn", util.nvl(rs.getString("bnkidn"),"NA"));
            MAddr addr = new MAddr();
            addr.setIdn(rs.getString("addr_idn"));
            addr.setAddr(rs.getString("addr"));
            bnkAddrList.add(addr);
        }
        rs.close();
        stmt.close();
            }
        String validQ="Select Inv_Addr_Idn,Nmerel_Idn,Bank_Id, Courier,Co_Idn From Msal Where Idn in ("+saleId+") And Inv_Addr_Idn Is Not Null And Nmerel_Idn Is Not Null " +
            " and Bank_Id is not null and Courier is not null and co_idn is not null having count(*)=1 Group By Inv_Addr_Idn,Nmerel_Idn,Bank_Id,Courier,co_idn";
          rsLst = db.execSqlLst("validQ", validQ, new ArrayList());
          stmt =(PreparedStatement)rsLst.get(0);
           rs =(ResultSet)rsLst.get(1);
        while (rs.next()) {
        valid = true;
        }
            rs.close();
          stmt.close();
        if(!valid){
        bnkAddrList=new ArrayList();    
        String setQ="Select C.Nme_Idn bnkidn,d.courier,A.Addr_Idn addr_idn,  A.Bldg ||''|| A.Street ||''|| A.Landmark ||''|| A.Area addr \n" + 
        "From Maddr A,Nme_Dtl B , Mnme C,Msal D\n" + 
        "Where D.Idn In ("+saleId+")  And A.Nme_Idn=d.Bank_Id\n" + 
        "And B.Nme_Idn=d.Bank_Id And C.Nme_Idn=d.Bank_Id\n" + 
        "And A.Nme_Idn=B.Nme_Idn\n" + 
        "and a.nme_idn = c.nme_idn \n" + 
        "and b.vld_dte is null"; 
          rsLst = db.execSqlLst("setQ", setQ, new ArrayList());
          stmt =(PreparedStatement)rsLst.get(0);
           rs =(ResultSet)rsLst.get(1);
        while(rs.next()){
        udf.setValue("bankIdn", util.nvl(rs.getString("bnkidn"),"NA"));
        udf.setValue("courier", util.nvl(rs.getString("courier")));
        MAddr addr = new MAddr();
        addr.setIdn(rs.getString("addr_idn"));
        addr.setAddr(rs.getString("addr"));
        bnkAddrList.add(addr);
        }
            rs.close();
            stmt.close();
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
        "       c.typ = a.Chr_Fr and b.mdl = 'JFLEX' and b.nme_rule = 'MIXDLV_CHARGES' and c.stt='A' and a.til_dte is null order by a.srt_fr , a.dsc , a.chr_fr";
          rsLst = db.execSqlLst("chargesQ", chargesQ, new ArrayList());
          stmt =(PreparedStatement)rsLst.get(0);
           rs =(ResultSet)rsLst.get(1);
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
          stmt.close();
        ArrayList setcharge=new ArrayList();
        String chQ="Select A.Typ Typ From Charges_Typ A,Trns_Charges B\n" + 
        "Where A.Idn=B.Charges_Idn And A.Stt='A' And B.Flg='Y' And Ref_Idn In ("+saleId+")\n" + 
        "and a.stg='OPT'\n" + 
        "GROUP BY A.Typ";
          rsLst = db.execSqlLst("chQ", chQ, new ArrayList());
          stmt =(PreparedStatement)rsLst.get(0);
           rs =(ResultSet)rsLst.get(1);
        while(rs.next()){
        String chtyp=util.nvl(rs.getString("Typ"));
        setcharge.add(chtyp);
        udf.setValue(chtyp+"_AUTOOPT","Y");
        }
        rs.close();
          stmt.close();
        ArrayList thruBankList = new ArrayList();
        String thruBankSql = "select b.nme_dtl_idn , b.txt from mnme a , nme_dtl b " + 
                             "where a.nme_idn = b.nme_idn and b.mprp like 'THRU_BANK%' and a.nme_idn=?";
        ary = new ArrayList();
        ary.add(String.valueOf(nmeIdn));
          rsLst = db.execSqlLst("thruBank", thruBankSql, ary );
          stmt =(PreparedStatement)rsLst.get(0);
           rs =(ResultSet)rsLst.get(1);
        while(rs.next()){
            MNme dtl = new MNme();
            String txt=util.nvl(rs.getString("txt"));
            dtl.setIdn(util.nvl(rs.getString("nme_dtl_idn")));
            txt.replaceAll("\\#"," ");
            dtl.setFnme(txt);
            thruBankList.add(dtl);
        }
            rs.close();
          stmt.close();
        MIXDlvPrprViw(session,db);
        req.setAttribute("avgdtl", avgdtl);
        req.setAttribute("setcharge", setcharge);
        ArrayList groupList = srchQuery.getgroupList(req, res);
        ArrayList bankList = srchQuery.getBankList(req, res);
        ArrayList courierList = srchQuery.getcourierList(req, res);
        session.setAttribute("chargesLst", chargesLst);
        udf.setThruBankList(thruBankList);
        udf.setCourierList(courierList);
        udf.setBnkAddrList(bnkAddrList);
        udf.setGroupList(groupList);
        udf.setBankList(bankList);
        udf.setInvAddLst(maddrList);
        info.setValue("PKTS", pkts);
        req.setAttribute("view", "Y");
          util.updAccessLog(req,res,"MixSaleDelivery", "loadPkt end");
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
          util.updAccessLog(req,res,"MixSaleDelivery", "save");
        MixSaleDeliveryForm udf = (MixSaleDeliveryForm) af;
         ArrayList params = new ArrayList();
        ArrayList pkts      = new ArrayList();
        String    pktNmsSl  = "";
        String    pktNmsRT  = "";
        String    pktNmsCAN = "";
        int       appDlvIdn = 0;
        boolean isRtn = false;
        pkts = (ArrayList) info.getValue("PKTS");    // udf.getPkts();
        String    rmk1      = util.nvl((String)udf.getValue("rmk1"));
        for (int i = 0; i < pkts.size(); i++) {
            PktDtl pkt     = (PktDtl) pkts.get(i);
            long   lPktIdn = pkt.getPktIdn();
            String saleIdn = pkt.getSaleId();
            String vnm = pkt.getVnm();
            String lStt    = util.nvl((String) udf.getValue("stt_" +saleIdn+"_"+lPktIdn));

           

            String updPkt = "";

            if (lStt.equals("DLV")) {
                if (appDlvIdn == 0) {
                    ArrayList ary = new ArrayList();

                    ary.add(Integer.toString(udf.getNmeIdn()));
                    ary.add(Integer.toString(udf.getRelIdn()));
                    ary.add(udf.getValue("invByrTrm"));
                    ary.add(udf.getValue("invByrIdn"));
                    ary.add(udf.getValue("invAddr"));
                    ary.add("DLV");
                    ary.add("IS");
                    ary.add(saleIdn);
                    ary.add("NN");
                    ary.add(nvl((String) udf.getValue("aadatpaid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk1paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk2paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk3paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk4paid"), "Y"));
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
                if(appDlvIdn!=0){
                params = new ArrayList();
                params.add("DLV");
                params.add(Long.toString(lPktIdn));
                params.add(saleIdn);

                int upJan = db.execUpd("updateJAN", "update jansal set stt=? , dte_sal=sysdate where mstk_idn=? and idn= ? ", params);

                params = new ArrayList();
                params.add(String.valueOf(appDlvIdn));
                params.add(saleIdn);
                params.add(Long.toString(lPktIdn));
                params.add(pkt.getQty());
                params.add(pkt.getCts());
                params.add(pkt.getMemoQuot());
                params.add("DLV");

                int SalPkt =
                    db.execCall(
                        "sale from memo",
                        " DLV_PKG.Dlv_Pkt(pIdn => ? , pSaleIdn => ?, pStkIdn => ?, pQty => ? , pCts => ?, pFnlSal => ?, pStt => ?)",
                        params);

                pktNmsSl = pktNmsSl + "," + Long.toString(lPktIdn);
                }} else if (lStt.equals("RT")) {
                        String rmk = util.nvl((String)udf.getValue("rmk_" +saleIdn+"_"+Long.toString(lPktIdn)));
                        params = new ArrayList();
                        params.add(saleIdn);
                        params.add(Long.toString(lPktIdn));
                        params.add(rmk);
                        int ct = db.execCall(" App Pkts", " DLV_PKG.rtn_pkt(pIdn => ? , pStkIdn => ?,pRem => ?)", params);

                        pktNmsRT = pktNmsRT + "," +vnm;
                        req.setAttribute("RTMSG", "Packets get return: " + pktNmsRT);
                        isRtn = true;
            } else if (lStt.equals("CAN")) {
                    params = new ArrayList();
                    params.add(saleIdn);
                    params.add(Long.toString(lPktIdn));
                    int ct = db.execCall(" Cancel Pkts ", "sl_pkg.Cancel_Pkt( pIdn =>?, pStkIdn=> ?)", params);
                    pktNmsCAN = pktNmsCAN + "," +vnm;
                    req.setAttribute("CANMSG", "Packets cancelled " + pktNmsCAN);
            } 
            
            
            params = new ArrayList();
            params.add(saleIdn);
            int upQuery = db.execCall("calQuot", "SL_PKG.UPD_QTY(pIdn => ? , pTyp => 'DLV')", params);
          }
        
       
        if (appDlvIdn != 0) {
            if(!rmk1.equals("")){
            ArrayList ary = new ArrayList();
            ary.add(rmk1);
            ary.add(String.valueOf(appDlvIdn));
            String updateFlg = "update mdlv set rmk=? where idn=?";
            int cntmj = db.execUpd("update mjan", updateFlg, ary);
            }
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
                String calcdis= util.nvl((String)udf.getValue(typcharge+"_save"),"0");  
                String autooptional= util.nvl((String)udf.getValue(typcharge+"_AUTOOPT"));  
                String vlu= util.nvl((String)udf.getValue("vluamt"));
                String exhRte=util.nvl((String)udf.getValue("exhRte"),"1");
                String extrarmk= util.nvl((String)udf.getValue(typcharge+"_rmksave"));
                    if(!vlu.equals("") && !vlu.equals("0")  && !calcdis.equals("NaN")  && !vlu.equals("NaN")){
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
            int accessidn=util.updAccessLog(req,res,"Mix Sale Delivery", "Mix Sale Delivery");
            int upQuery = db.execCall("calQuot", "DLV_PKG.UPD_QTY(pIdn => ? )", params);
            req.setAttribute("dlvId", String.valueOf(appDlvIdn));
            req.setAttribute("SLMSG", "Packets get Deliver : " + pktNmsSl + " with delivery Id" + appDlvIdn);
            req.setAttribute("performaLink","Y");
            req.setAttribute("accessidn", String.valueOf(accessidn));
        }
          util.updAccessLog(req,res,"MixSaleDelivery", "save end");
        return load(am, af, req, res);
        }
    }
    
    public ArrayList MIXDlvPrprViw(HttpSession session,DBMgr db){
        ArrayList asViewPrp = (ArrayList)session.getAttribute("mixdlvViewLst");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
                
              ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MIX_DLV_VW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs1 =(ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                stmt.close();
                session.setAttribute("mixdlvViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
    
    public ActionForward FinalByrMIX(ActionMapping mp , ActionForm fm , HttpServletRequest req , HttpServletResponse res){
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
          return mp.findForward(rtnPg);   
      }else{
          util.updAccessLog(req,res,"MixSaleDelivery", "fnlByr");
        StringBuffer sb = new StringBuffer();
        String byrId = req.getParameter("bryIdn");
        String typ = util.nvl(req.getParameter("typ"),"SL");
        String finalByr = "select  distinct form_url_encode(fnl_byr) fnl_byr, fnl_nme_idn  from sal_pndg_v where nme_idn=? and typ = ?  and pkt_ty in('MIX','MX') order by fnl_byr";
        if(typ.equals("LS")){
            finalByr = "select  distinct form_url_encode(fnl_byr) fnl_byr, fnl_nme_idn  from sal_pndg_v where nme_idn=? and typ = ? order by fnl_byr";    
        }
          if(typ.equals("DLV")){
                  finalByr = "select  distinct form_url_encode(FNL_BYR) FNL_BYR, FNL_BYR_IDN  from DLV_MIXPNDG_V where BYR_IDN= ? and typ = ? order by FNL_BYR";    
              }
        ArrayList ary = new ArrayList();
        ary.add(byrId);
        ary.add(typ);

        try {
            
          ArrayList  rsLst = db.execSqlLst("sqlVlu", finalByr, ary);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
            while (rs.next()) {
                String nme =
                    util.nvl(rs.getString(1), "0").replaceAll("&", "&amp;");


                sb.append("<nmes>");
                sb.append("<nmeid>" + util.nvl(rs.getString(2)) + "</nmeid>");
                sb.append("<nme>" + nme + "</nme>");
                sb.append("</nmes>");

            }

            rs.close();
            stmt.close();

            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
        
        return null;
        }
    }
        
   public ActionForward loadSLTrm(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
       
      int fav = 0;
      int dmd = 0 ;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      ArrayList ary = new ArrayList();

      StringBuffer sb = new StringBuffer();
      ArrayList trmsLst = new ArrayList();
      String rlnId = util.nvl(req.getParameter("bryId"));
    String typ = util.nvl(req.getParameter("typ"));
        ary = new ArrayList();
        ary.add(rlnId);
      String str = " and nme_idn=?";
      if(rlnId.equals("0")){
          str = "";
          ary = new ArrayList();
      }
      String favSrch = "select b.term||' '||cur||' : '||form_url_encode(byr.get_nm(AADAT_IDN))||'/'||form_url_encode(byr.get_nm(mbrk2_idn)) dtls, nmerel_idn, nme_idn from nmerel a, mtrms b where a.trms_idn = b.idn and a.vld_dte is null  and exists (select 1 from msal c where c.stt = 'IS' and (c.nmerel_idn = a.nmerel_idn or a.nmerel_idn = c.fnl_trms_idn)) "+str ;
       if(typ.equals("DLV")){
       favSrch = "select b.term||' '||cur||' : '||form_url_encode(byr.get_nm(AADAT_IDN))||'/'||form_url_encode(byr.get_nm(mbrk2_idn)) dtls, \n" + 
       "nmerel_idn, nme_idn from nmerel a, mtrms b where a.trms_idn = b.idn and a.vld_dte is null \n" + 
       "and exists (select 1 from mdlv c where c.stt = 'IS' and (c.nmerel_idn = a.nmerel_idn or a.nmerel_idn = c.fnl_trms_idn)) "+str ;
              }
    
          ArrayList  rsLst = db.execSqlLst("favSrch",favSrch, ary);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
      while(rs.next()){
          fav=1;
     
          TrmsDao trms = new TrmsDao();
          trms.setRelId(rs.getInt(2));
          trms.setTrmDtl(rs.getString(1));
          sb.append("<trm>");
          sb.append("<trmDtl>"+util.nvl(rs.getString(1).toLowerCase()) +"</trmDtl>");
          sb.append("<relId>"+util.nvl(rs.getString(2)) +"</relId>");
          sb.append("</trm>");
          trmsLst.add(trms);
      }
       if(fav==1)
        res.getWriter().write("<trms>" +sb.toString()+ "</trms>");
       
    
        
        
        rs.close();
          stmt.close();
        return null;
        }
    }
        
     public ActionForward saleIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
        String nmeId = req.getParameter("nameIdn");
        String typ = req.getParameter("typ");
        String rlnId = util.nvl(req.getParameter("rlnId"));
        ArrayList ary = new ArrayList();
        ary.add(nmeId);
        String  sql = "select distinct a.idn , to_char(a.dte, 'dd-Mon HH24:mi') dte from msal a , jansal b,mstk c "+
           " where a.stt ='IS' and a.typ in ('"+typ+"') and c.idn=b.mstk_idn and  c.pkt_ty in ('MIX','MX') "+
           " and a.idn = b.idn and b.stt = 'SL' "+
           " and nvl(a.inv_nme_idn, a.nme_idn) = ? ";
          
          if(typ.equals("DLV")){
             sql = "select distinct a.idn , to_char(a.dte, 'dd-Mon HH24:mi') dte from mdlv a , dlv_dtl b,mstk c "+
             " where a.stt ='IS' and a.typ in ('"+typ+"') and c.idn=b.mstk_idn and  c.pkt_ty in ('MIX','MX') "+
              " and a.idn = b.idn and b.stt = 'DLV' "+
               " and nvl(a.inv_nme_idn, a.nme_idn) = ? ";
              } 
        if(!rlnId.equals("")){
            sql = sql+" and nvl(a.fnl_trms_idn, a.nmerel_idn) = ?  ";
            ary.add(rlnId);
        }
           sql = sql+ " order by a.idn ";

          ArrayList  rsLst = db.execSqlLst("memo pkt", sql, ary);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
        while(rs.next()){
            sb.append("<memo>");
            sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
            sb.append("<dte>"+util.nvl(rs.getString("dte"),"") +"</dte>");
            sb.append("</memo>");
        }
        rs.close();
          stmt.close();
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.getWriter().write("<memos>"+sb.toString()+"</memos>");
        return null;
        }
    }
    
    public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"MixSaleDelivery", "creat XL");
        HashMap dbinfo = info.getDmbsInfoLst();
        String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
            int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            int pktListsz=pktList.size();
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
                String contentTypezip = "application/zip";
            String fileNmzip = "SaleDelivery"+util.getToDteTime();
            OutputStream outstm = res.getOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(outstm);
            ZipEntry entry = new ZipEntry(fileNmzip+".xls");
            zipOut.putNextEntry(entry);
            res.setHeader("Content-Disposition","attachment; filename="+fileNmzip+".zip");
            res.setContentType(contentTypezip);
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
               hwb.write(bos);
               bos.writeTo(zipOut);      
              zipOut.closeEntry();
               zipOut.flush();
               zipOut.close();
               outstm.flush();
               outstm.close(); 
            }else{
                OutputStream out = res.getOutputStream();
                String CONTENT_TYPE = "getServletContext()/vnd-excel";
                String fileNm = "SaleDelivery"+util.getToDteTime()+".xls";
                res.setContentType(CONTENT_TYPE);
                res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        hwb.write(out);
        out.flush();
        out.close();
            }
        return null;
        }
    }       
  
    public String nvl(String pVal, String rVal) {
        String val = pVal;

        if (pVal == null) {
            val = rVal;
        }

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
              util.updAccessLog(req,res,"Mix Sale Delivery", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Mix Sale Delivery", "init");
          }
          }
          return rtnPg;
          }
}


//~ Formatted by Jindent --- http://www.jindent.com
