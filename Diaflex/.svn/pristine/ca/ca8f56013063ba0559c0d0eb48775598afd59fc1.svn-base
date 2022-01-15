package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.ByrDao;
import ft.com.dao.MAddr;
import ft.com.dao.PktDtl;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class TransferToMixAction extends DispatchAction {

    public TransferToMixAction() {
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
        util.updAccessLog(req,res,"Transfer To Mix", "load");
        TransferToMixForm udf = (TransferToMixForm) af;
        udf.reset();
        ArrayList byrList = new ArrayList();
        String    getByr  = "select  distinct byr , nme_idn from sal_pndg_v order by byr";

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
        udf.setByrLst(byrList);

        return am.findForward("load");
        }
    }

    public ActionForward loadPkt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception 
    
    {
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
            util.updAccessLog(req,res,"Transfer To Mix", "loadpkt");
        TransferToMixForm udf = (TransferToMixForm)af;
        SearchQuery srchQuery = new SearchQuery();
        info.setDlvPktList(new ArrayList());
        ArrayList pkts        = new ArrayList();
        String    flnByr      = util.nvl(udf.getPrtyIdn());
        String    saleId      = "";
        String    vnmLst      = util.nvl((String)udf.getValue("vnmLst"));
        ResultSet rs          = null;
        ArrayList    params      = null;
        Enumeration reqNme = req.getParameterNames();

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
        String    getMemoInfo =
            "select nme_idn, nmerel_idn,byr.get_trms(nmerel_idn) trms, byr.get_nm(nme_idn) byr, stt, inv_nme_idn , fnl_trms_idn , inv_addr_idn , "
            + " typ, qty, cts, vlu, to_char(dte, 'dd-Mon-rrrr HH24:mi:ss') dte from msal where  typ in ('LS') and stt='IS' ";
        params = new ArrayList();
        if(!flnByr.equals("")){
        getMemoInfo = getMemoInfo+" and inv_nme_idn =?  " ;
        params.add(flnByr);
        }
        if(!saleId.equals("")){
        getMemoInfo = getMemoInfo+" and idn in ("+saleId+")  " ;

        }

          ArrayList outLst = db.execSqlLst(" Memo Info", getMemoInfo, params);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet mrs = (ResultSet)outLst.get(1);
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
        }
        mrs.close();
            pst.close();
        String getPktData =
            " select a.idn , a.qty , mstk_idn, nvl(fnl_sal, quot) memoQuot, quot, fnl_sal , b.tfl3 "
            + " , b.cts, nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte, a.stt , b.vnm  "
            + "  , trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2) dis, trunc(((nvl(fnl_sal, quot)/greatest(b.rap_rte,1))*100)-100,2) mDis "
            + " from jansal a, mstk b , msal c where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='SL' and c.typ in ('LS') and c.stt='IS' ";
        params = new ArrayList();
        if(!flnByr.equals("")){
        getPktData = getPktData+" and c.inv_nme_idn =?  " ;
        params.add(flnByr);
        }
        if(!saleId.equals("")){
        getPktData = getPktData+" and c.idn in ("+saleId+")  " ;
      
        }
        if(!vnmLst.equals("")){
        vnmLst = util.getVnm(vnmLst);
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
            String lStt = rs.getString("stt");

            pkt.setStt(lStt);
            udf.setValue("stt_" + pktIdn, lStt);
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
                               + " where a.mprp = b.prp and b.prp = c.prp and a.grp=1 and c.mdl = 'MEMO_RTRN' and a.mstk_idn = ? "
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
            pst.close();
            pkts.add(pkt);
        }
        rs.close();
            pst.close();
        if(!vnmLst.equals("")){
            vnmLst = util.pktNotFound(vnmLst);
            req.setAttribute("vnmNotFnd", vnmLst);
        }
        load(am, af, req, res);

         ArrayList trmList = new ArrayList();
         ArrayList ary = new ArrayList();
        trmList = srchQuery.getTerm(req,res, udf.getNmeIdn());
        udf.setInvTrmsLst(trmList);
        udf.setValue("invByrTrm",udf.getInvTrmsIdn());
        ArrayList byrList = new ArrayList();
        String    getByr  =
            "select nme_idn, initcap(nme) byr from nme_v a " + " where nme_idn = ? "
            + " or exists (select 1 from nme_grp_dtl c, nme_grp b where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn "
            + " and b.nme_idn = ? and b.typ = 'BUYER') " + " order by 2 ";

        ary = new ArrayList();
        ary.add(String.valueOf(udf.getNmeIdn()));
        ary.add(String.valueOf(udf.getNmeIdn()));
          outLst = db.execSqlLst("byr", getByr, ary);
          pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);

        while (rs.next()) {
            ByrDao byr = new ByrDao();

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
        udf.setValue("invByrIdn", udf.getInvByrIdn());
       
        ary = new ArrayList();

        String sql =
            "select a.addr_idn , unt_num, bldg ||''|| street ||''|| landmark ||''|| area ||''|| b.city_nm||''|| c.country_nm addr "
            + " from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn = ? order by a.dflt_yn desc ";

        ary.add(String.valueOf(udf.getInvAddrIdn()));

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
        String bankSql = "select nme_idn, fnme  from mnme  where 1 = 1 and vld_dte is null  and typ = 'GROUP'";
        ArrayList bankList = new ArrayList();
          outLst = db.execSqlLst("bank Sql", bankSql, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            MAddr addr = new MAddr();
            addr.setIdn(rs.getString("nme_idn"));
            addr.setAddr(rs.getString("fnme"));
            bankList.add(addr);
        }
        rs.close();
         pst.close();
        udf.setBankList(bankList);
        udf.setInvAddLst(maddrList);
        info.setValue("PKTS", pkts);
        ArrayList mxStkIdnList = new ArrayList();
        String mxPkt = "select vnm , idn from mstk where pkt_ty = 'MX' order by vnm ";
          outLst = db.execSqlLst("mxPkt", mxPkt, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            PktDtl pktDtl = new PktDtl();
            long pktIdn = rs.getLong("idn");
            pktDtl.setPktIdn(pktIdn);
            pktDtl.setVnm(rs.getString("vnm"));
            mxStkIdnList.add(pktDtl);
        }
        rs.close();
            pst.close();
        udf.setMxPktList(mxStkIdnList);
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
            util.updAccessLog(req,res,"Transfer To Mix", "save");
        ArrayList params = null;
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        TransferToMixForm udf = (TransferToMixForm)af;
        String mixPktIdn = (String)udf.getValue("mixStkIdn");
        ArrayList pkts      = new ArrayList();
      
        pkts = (ArrayList) info.getValue("PKTS");    // udf.getPkts();

        for (int i = 0; i < pkts.size(); i++) {
            PktDtl pkt     = (PktDtl) pkts.get(i);
            long   lPktIdn = pkt.getPktIdn();
            String saleIdn = pkt.getSaleId();
            String vnm = pkt.getVnm();
            String lStt    = util.nvl((String) udf.getValue("MIX_" + lPktIdn));
            if(lStt.equals("no")){
                String gtInsert = " Insert into gt_srch_rslt ( stk_idn, flg, vnm )  select ?,'M', ? from dual";
                params = new ArrayList();
                params.add(String.valueOf(lPktIdn));
                params.add(vnm);
               ct = db.execUpd("gtInsert", gtInsert, params);
            }

        }
       
        String tranMix = "DLV_PKG.Trf_To_Mix(pStkIdn=>?)";
        params = new ArrayList();
        params.add(mixPktIdn);
       ct = db.execCall("tranMix", tranMix, params);
       if(ct > 0){
           String mstkUpdate ="update jansal set stt='MX' where stt = 'SL' " + 
                            " and mstk_idn in (select stk_idn from gt_srch_rslt where flg='M') and " +
                            " idn in (select idn from msal where typ = 'LS' and stt = 'IS') ";
           ct = db.execUpd("update mstk",mstkUpdate, new ArrayList());
           req.setAttribute("msg", "Selected pakets get Transfer To Mix");
       }
   
        return load(am, af, req, res);
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
                util.updAccessLog(req,res,"Transfer To Mix", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Transfer To Mix", "init");
            }
            }
            return rtnPg;
            }
}
