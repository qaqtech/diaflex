package ft.com.mixakt;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.ByrDao;
import ft.com.dao.MAddr;
import ft.com.dao.MNme;
import ft.com.dao.PktDtl;
 
import ft.com.marketing.SearchQuery;

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

public class MixDeliveryByBillingParty  extends DispatchAction {
    
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
        util.updAccessLog(req,res,"Sale Delivery Billing", "load");
        MixDeliveryForm udf = (MixDeliveryForm) af;
        
        ArrayList byrList = new ArrayList();
            String    getByr  = "select  distinct byr , nme_idn from sal_pndg_v where typ='SL' and pkt_ty in ('MIX','MX') order by byr";


          ArrayList outLst = db.execSqlLst("byr", getByr, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);


        while (rs.next()) {
            ByrDao byr = new ByrDao();

            byr.setByrIdn(rs.getInt("nme_idn"));
            byr.setByrNme(rs.getString("byr"));
            byrList.add(byr);
        }
        rs.close();
            pst.close();
        udf.setInvByrLst(byrList);
       
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
            util.updAccessLog(req,res,"Sale Delivery Billing", "loadpkt");
        MixDeliveryForm udf = (MixDeliveryForm) af;
        SearchQuery srchQuery = new SearchQuery();
        info.setDlvPktList(new ArrayList());
        ArrayList pkts        = new ArrayList();
        int   invByrId      = udf.getInvByrIdn();
        String    vnmLst      = util.nvl((String)udf.getValue("vnmLst"));
        ResultSet rs          = null;
        ArrayList    params      = null;
        HashMap avgdtl=new HashMap();
      
        Enumeration reqNme = req.getParameterNames();
        String nmeId="";
        while (reqNme.hasMoreElements()) {
            String paramNm = (String) reqNme.nextElement();
           if (paramNm.indexOf("cb_nme") > -1) {
                String val = req.getParameter(paramNm);

                if (nmeId.equals("")) {
                    nmeId = val;
                } else {
                   nmeId = nmeId + "," + val;
                }
            }
        }
        int invRelIdn =0;
        String ByrNme="";
        if(nmeId.equals("")){
                    boolean isRtn = true;
                    if(vnmLst.length()>0){
                    int cnt=0;
                  
                        String pktLst = util.getVnm(vnmLst);
                        String checkSql ="select distinct a.nme_idn\n" + 
                        "from nme_v a ,nme_grp b,nme_grp_dtl c, msal d ,jansal e, mstk f\n" + 
                        "where 1 = 1\n" + 
                        "and a.nme_idn = c.nme_idn\n" + 
                        "and b.nme_grp_idn = c.nme_grp_idn\n" + 
                        "and d.idn = e.idn\n" + 
                        "and e.mstk_idn = f.idn\n" + 
                        "and b.nme_idn = d.nme_idn\n" + 
                        "and f.pkt_ty='MIX' and d.stt='IS' and e.stt='SL' and d.typ in ('SL') and d.flg1='CNF' \n" + 
                        " and  ( f.vnm in ("+pktLst+") or f.tfl3 in ("+pktLst+")) "+
                        "and b.typ = 'BUYER' and c.flg is null ";
                      ArrayList outLst = db.execSqlLst("check rel", checkSql, new ArrayList());
                      PreparedStatement pst = (PreparedStatement)outLst.get(0);
                      ResultSet rs1 = (ResultSet)outLst.get(1);

                        while(rs1.next()){
                            cnt++;
                           
                        }
                        rs1.close();
                        pst.close();
                        if(cnt==1){   
                            isRtn = false;
                            String saleIdSql = "select distinct b.nme_idn\n" + 
                            "                        from nme_v a ,nme_grp b,nme_grp_dtl c, msal d,jansal e, mstk f \n" + 
                            "                       where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn \n" + 
                            "                       and f.pkt_ty='MIX' and b.nme_idn=d.nme_idn and e.mstk_idn = f.idn and e.idn=d.idn \n" + 
                            "                    and e.stt = 'SL' and d.stt='IS' and d.typ in ('SL') and c.flg is null " + 
                            " and  ( f.vnm in ("+pktLst+") or f.tfl3 in ("+pktLst+")) ";
                          outLst = db.execSqlLst("saleID",saleIdSql, new ArrayList());
                             pst = (PreparedStatement)outLst.get(0);
                            rs1 = (ResultSet)outLst.get(1);

                               while(rs1.next()){
                                String nameIdnval = util.nvl(rs1.getString("nme_idn"));
                                if (nmeId.equals("")) {
                                    nmeId = nameIdnval;
                                } else {
                                   nmeId = nmeId + "," + nameIdnval;
                                }
                            }
                            rs1.close();
                            pst.close();
                        }
                    }
                    if(isRtn){
                        req.setAttribute("RTMSG", "Please verify packets");
                       return load(am, af, req, res);
                    }
    //           String    getMemoInfo =
    //               "select  Inv_Nme_Idn, nmerel_idn,byr.get_trms(nmerel_idn) trms, byr.get_nm(nme_idn) byr, stt, inv_nme_idn , fnl_trms_idn , inv_addr_idn , exh_rte exhRte , "
    //               + " typ, qty, cts, vlu, to_char(dte, 'dd-Mon-rrrr HH24:mi:ss') dte from msal where  typ in ('SL') and stt='IS' and flg1='CNF' ";
    //           params = new ArrayList();
    //           if(!nmeId.equals("")){
    //           getMemoInfo = getMemoInfo+" and nme_idn in ("+nmeId+")  " ;
    //           }
    //           ResultSet mrs = db.execSql(" Memo Info", getMemoInfo, params);
    //           if (mrs.next()) {
    //               invByrId= mrs.getInt(1);
    //               ByrNme = util.nvl(mrs.getString("byr"));
    //           }
       } 
       String invRelsql = "select nmerel_idn,byr.get_nm(nme_idn) byr from nmerel where nme_idn =? and dflt_yn = 'Y' ";
       params = new ArrayList();
       params.add(Integer.toString(invByrId));
          ArrayList outLst = db.execSqlLst("invRelsql", invRelsql, params);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
       while(rs.next()){
           invRelIdn = rs.getInt(1);
           ByrNme = util.nvl(rs.getString("byr"));
       }
            rs.close();
            pst.close();
        String getPktData =

        " select a.idn ,c.nme_idn, e.nme , a.qty , a.mstk_idn, nvl(a.fnl_sal, a.quot) memoQuot, quot, to_char(c.dte,'dd-mm-yyyy') dte , "+
        " nvl(a.fnl_sal,a. quot) fnl_sal,to_char(trunc(A.cts,2) * quot, '99999990.00') orgamt, "+
        "  to_char(trunc(A.cts,2) * nvl(fnl_sal, quot), '99999990.00') byramt  , trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)) inc_dys  , "+
        " trunc(nvl(a.fnl_sal, a.quot)*trunc(a.cts,2) *((trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)))*12/100/365),2) inc_vlu  , "+
        "  b.cts, nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte, a.stt , b.vnm , b.tfl3    ,to_char(trunc(a.cts,2) * b.rap_rte, '99999999990.00') rapVlu, "+
        " decode(b.rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)) dis,   "+
        " decode(b.rap_rte, 1, '',trunc(((nvl(a.fnl_sal, a.quot)/c.exh_rte/greatest(b.rap_rte,1))*100)-100,2)) mDis   , "+
        " decode(b.rap_rte, 1, '',trunc(((a.quot/c.exh_rte/greatest(b.rap_rte,1))*100)-100,2)) oDis  "+
        " from jansal a, mstk b , msal c ,  nmerel d , nme_v e where a.mstk_idn = b.idn and "+
        " c.idn = a.idn and a.stt='SL' and c.typ in ('SL') and c.flg1='CNF' and b.pkt_ty='MIX'  and c.nmerel_idn = d.nmerel_idn and d.nme_idn=e.nme_idn and  c.stt='IS'  ";
        
        params = new ArrayList();
    //        if(invByrId!=0){
    //        getPktData = getPktData+" and c.inv_nme_idn =?  " ;
    //        params.add(String.valueOf(invByrId));
    //        }
        if(nmeId.length()>0){
        getPktData =  getPktData+" and c.nme_idn in ("+nmeId+") " ;
        }
        if(!vnmLst.equals("")){
        vnmLst=util.getVnm(vnmLst);
        getPktData = getPktData + " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
        }
        getPktData = getPktData +" order by  b.sk1 ";
          outLst = db.execSqlLst(" memo pkts", getPktData, params);
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
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
            pkt.setValue("nme", util.nvl(rs.getString("nme")));
            pkt.setValue("ByrNme", ByrNme);
            pkt.setValue("SALEDTE", util.nvl(rs.getString("dte")));
            String lStt = util.nvl(rs.getString("stt")).trim();
            pkt.setStt(lStt);
            udf.setValue("stt_" + pktIdn, lStt);
            pkt.setValue("rapVlu", util.nvl(rs.getString("rapVlu")));
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
                               + " where a.mprp = b.prp and a.grp=1 and b.prp = c.prp and c.mdl = 'MIX_VIEW' and a.mstk_idn = ? "
                               + " order by c.rnk, c.srt ";

            params = new ArrayList();
            params.add(Long.toString(pktIdn));

          ArrayList outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, params);
          PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
          ResultSet rs1 = (ResultSet)outLst1.get(1);

            while (rs1.next()) {
                String lPrp = rs1.getString("mprp");
                String lVal = rs1.getString("val");

                pkt.setValue(lPrp, lVal);
            }
            rs1.close();
            pst1.close();
            pkts.add(pkt);
        }
       rs.close();
        pst.close();
      
        ArrayList trmList = new ArrayList();
        ArrayList ary = new ArrayList();
        trmList = srchQuery.getTermALL(req,res, invByrId);
        udf.setInvTrmsLst(trmList);
        udf.setValue("invByrTrm",invRelIdn); 
        
        ArrayList byrList = new ArrayList();
        String    getByr  =
            "select nme_idn,  byr.get_nm(nme_idn) byr from mnme a " + " where nme_idn = ? "
            + " or exists (select 1 from nme_grp_dtl c, nme_grp b where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn "
            + " and b.nme_idn = ? and b.typ = 'BUYER') " + " order by 2 ";

        ary = new ArrayList();
        ary.add(String.valueOf(invByrId));
        ary.add(String.valueOf(invByrId));
          outLst = db.execSqlLst("byr", getByr, ary);
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
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
       pst.close();
        udf.setInvByrLst(byrList);
        udf.setValue("invByrIdn", String.valueOf(invByrId));
      
        String brokerSql =
            "select   byr.get_nm(mbrk1_idn) brk1  , mbrk1_comm , mbrk1_paid , byr.get_nm(mbrk2_idn) brk2  ,mbrk2_comm , mbrk2_paid, byr.get_nm(mbrk3_idn) brk3  ,"
            + "mbrk3_comm , mbrk3_paid, byr.get_nm(mbrk4_idn) brk4  ,mbrk4_comm , mbrk4_paid , byr.get_nm(aadat_idn) aaDat  , aadat_paid , aadat_comm  from nmerel where nmerel_idn = ?";

        ary = new ArrayList();
        ary.add(String.valueOf(invRelIdn));
          outLst = db.execSqlLst("", brokerSql, ary);
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
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
        pst.close();
        ary = new ArrayList();

        String sql =
            "select a.addr_idn , unt_num, bldg ||''|| street ||''|| landmark ||''|| area ||''|| b.city_nm||''|| c.country_nm addr "
            + " from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn = ? order by a.dflt_yn desc ";

        ary.add(String.valueOf(invByrId));

        ArrayList maddrList = new ArrayList();

          outLst = db.execSqlLst("memo pkt", sql, ary);
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            MAddr addr = new MAddr();

            addr.setIdn(rs.getString("addr_idn"));
            addr.setAddr(rs.getString("addr"));
            maddrList.add(addr);
        }
        rs.close();
       pst.close();
        ArrayList bnkAddrList = new ArrayList();
            boolean dfltbankgrp=true;
            String banknmeIdn=  "" ;
            ary = new ArrayList();
            ary.add(String.valueOf(invByrId));
            String setbnkCouQ="select bnk_idn,courier from  mdlv where idn in(select max(idn) from mdlv where nme_idn=?)";
          outLst = db.execSqlLst("setbnkCouQ", setbnkCouQ, ary);
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            if(rs.next()){
                banknmeIdn=util.nvl(rs.getString("bnk_idn"));
                udf.setValue("courier", util.nvl(rs.getString("courier"),"NA"));
            }
            rs.close();
            pst.close();
            if(!banknmeIdn.equals("")){
                String defltBnkQ="select b.nme_idn bnkidn,a.addr_idn addr_idn,  a.bldg ||''|| a.street ||''|| a.landmark ||''|| a.area addr \n" + 
                "from maddr a, mnme b\n" + 
                "where 1 = 1 \n" + 
                "and a.nme_idn = b.nme_idn  and b.typ in('GROUP','BANK')\n" + 
                "and b.nme_idn = ? order by a.dflt_yn desc";
                ary = new ArrayList();
                ary.add(banknmeIdn);
              outLst = db.execSqlLst("defltBnkQ", defltBnkQ, ary);
               pst = (PreparedStatement)outLst.get(0);
              rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                    udf.setValue("bankIdn", util.nvl(rs.getString("bnkidn"),"NA"));
                    MAddr addr = new MAddr();
                    addr.setIdn(rs.getString("addr_idn"));
                    addr.setAddr(rs.getString("addr"));
                    bnkAddrList.add(addr);
                }
                rs.close();   
                pst.close();
                dfltbankgrp=false;
            }
            if(dfltbankgrp){
        String defltBnkQ="select c.nme_idn bnkidn,b.mprp,b.txt,a.addr_idn addr_idn,  a.bldg ||''|| a.street ||''|| a.landmark ||''|| a.area addr  \n" + 
        "        from maddr a,nme_dtl b , mnme c\n" + 
        "        where 1 = 1 and a.nme_idn=b.nme_idn\n" + 
        "        and a.nme_idn = c.nme_idn  and c.typ in('GROUP','BANK')\n" + 
        "        and b.mprp='PERFORMABANK' and b.txt='Y'\n" + 
        "        and b.vld_dte is null";
              outLst = db.execSqlLst("defltBnkQ", defltBnkQ, ary);
               pst = (PreparedStatement)outLst.get(0);
              rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            udf.setValue("bankIdn", util.nvl(rs.getString("bnkidn"),"NA"));
            MAddr addr = new MAddr();
            addr.setIdn(rs.getString("addr_idn"));
            addr.setAddr(rs.getString("addr"));
            bnkAddrList.add(addr);
        }
        rs.close();
        pst.close();
            }
        ArrayList chargesLst=new ArrayList();
        String chargesQ="Select  c.idn,c.typ,c.dsc,c.flg,c.fctr,c.db_call,c.rmk  From Rule_Dtl A, Mrule B , Charges_Typ C Where A.Rule_Idn = B.Rule_Idn And \n" + 
        "       c.typ = a.Chr_Fr and b.mdl = 'JFLEX' and b.nme_rule = 'DLV_CHARGES' and c.stt='A' and a.til_dte is null order by a.srt_fr , a.dsc , a.chr_fr";
          outLst = db.execSqlLst("chargesQ", chargesQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap dtl=new HashMap();
            dtl.put("idn",util.nvl(rs.getString("idn")));
            dtl.put("dsc",util.nvl(rs.getString("dsc")));
            dtl.put("flg",util.nvl(rs.getString("flg")));
            dtl.put("typ",util.nvl(rs.getString("typ")));
            dtl.put("fctr",util.nvl(rs.getString("fctr")));
            dtl.put("fun",util.nvl(rs.getString("db_call")));
            dtl.put("rmk",util.nvl(rs.getString("rmk")));
            chargesLst.add(dtl);
        }
        rs.close();
            pst.close();
        ArrayList thruBankList = new ArrayList();
        String thruBankSql = "select b.nme_dtl_idn , b.txt from mnme a , nme_dtl b " + 
                             "where a.nme_idn = b.nme_idn and b.mprp like 'THRU_BANK%' and a.nme_idn=?";
        ary = new ArrayList();
        ary.add(String.valueOf(invByrId));
          outLst = db.execSqlLst("thruBank", thruBankSql, ary );
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            MNme dtl = new MNme();
            String txt=util.nvl(rs.getString("txt"));
            dtl.setIdn(util.nvl(rs.getString("nme_dtl_idn")));
            txt.replaceAll("\\#"," ");
            dtl.setFnme(txt);
            thruBankList.add(dtl);
        }
            rs.close();
            pst.close();
        
        req.setAttribute("avgdtl", avgdtl);
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
            synchronized(this){
            util.updAccessLog(req,res,"Sale Delivery Billing", "save");
        ArrayList params = null;

        MixDeliveryForm udf = (MixDeliveryForm)af;

        ArrayList pkts      = new ArrayList();
        String    pktNmsSl  = "";
        String    pktNmsRT  = "";
        String  pktNmsCAN = "";
        int       appDlvIdn = 0;
        boolean isRtn = false;
        pkts = (ArrayList) info.getValue("PKTS");    // udf.getPkts();

        for (int i = 0; i < pkts.size(); i++) {
            PktDtl pkt     = (PktDtl) pkts.get(i);
            long   lPktIdn = pkt.getPktIdn();
            String saleIdn = pkt.getSaleId();
            String vnm = pkt.getVnm();
            String lStt    = util.nvl((String) udf.getValue("stt_" + lPktIdn));

           

            String updPkt = "";

            if (lStt.equals("DLV")) {
                if (appDlvIdn == 0) {
                    ArrayList ary = new ArrayList();
                    ary.add(udf.getValue("invByrIdn"));
                    ary.add(udf.getValue("invByrTrm"));
                    ary.add(udf.getValue("invByrTrm"));
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
                        "dlv_pkg.Gen_Hdr(pByrId => ? , pRlnIdn => ? , pTrmsIdn =>?  , pInvNmeIdn => ? , pInvAddrIdn => ? , pTyp => ? , pStt => ?,pFrmId => ?, pFlg => ? ,paadat_paid => ?, pMBRK1_PAID => ?, pMBRK2_PAID => ?, pMBRK3_PAID => ? , "
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

                pktNmsSl = pktNmsSl + "," +vnm;
                }} else if (lStt.equals("RT")) {
                        String rmk = util.nvl((String)udf.getValue("rmk_"+Long.toString(lPktIdn)));
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
            int upQuery = db.execCall("calQuot", "SL_PKG.UPD_QTY(pIdn => ?,pTyp => 'DLV' )", params);
          }
        
       
        if (appDlvIdn != 0) {
            
            params = new ArrayList();
            params.add(String.valueOf(appDlvIdn));

            int calCt = db.execCall("Cal_Fnl_Quot", "DLV_PKG.Cal_Fnl_Quot(pIdn => ?)", params);

            params = new ArrayList();
            params.add(String.valueOf(appDlvIdn));

            int upQuery = db.execCall("UPD_QTY", "DLV_PKG.UPD_QTY(pIdn => ? )", params);

            params = new ArrayList();
            params.add(String.valueOf(appDlvIdn));
            int upbrcDlv = db.execCall("DP_GEN_BRC_DLV", "DP_GEN_BRC_DLV(pDlvIdn => ? )", params);
            
            params = new ArrayList();
            params.add(udf.getValue("rmk"));
            params.add(String.valueOf(appDlvIdn));
            int updateRmk = db.execCall("update remark", "update mdlv set rmk=? where idn=?", params);
            
            
            req.setAttribute("dlvId", String.valueOf(appDlvIdn));
            pktNmsSl=pktNmsSl.replaceFirst("\\,", "");
            req.setAttribute("SLMSG", "Packets get Deliver : " + pktNmsSl + " with delivery Id :" + appDlvIdn);
            req.setAttribute("performaLink","Y");
            int accessidn=util.updAccessLog(req,res,"Sale Delivery By Billing", "End");    
            req.setAttribute("accessidn", String.valueOf(accessidn));;
        }
           

        return load(am, af, req, res);
        }
            }
    }
    
    public ArrayList rghPrpview(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute("MIX_VIEW");
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
                ArrayList ary = new ArrayList();
                ary.add("MIX_VIEW");
                ary.add("Y");
               
              ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl =? and flg=? order by rnk ",
                               ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                pst.close();
                session.setAttribute("MIX_VIEW", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return asViewPrp;
    }
    
    public MixDeliveryByBillingParty() {
        super();
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
