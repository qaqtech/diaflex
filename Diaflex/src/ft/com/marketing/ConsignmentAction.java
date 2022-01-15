package ft.com.marketing;
//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.ByrDao;
import ft.com.dao.MAddr;
import ft.com.dao.PktDtl;
import ft.com.dao.TrmsDao;

import java.io.IOException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConsignmentAction extends DispatchAction {
    public ConsignmentAction() {
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
        util.updAccessLog(req,res,"Consignment Page", "Consignment Load");
        ConsignmentForm udf = (ConsignmentForm) af;
        udf.reset();
        ArrayList byrList = new ArrayList();
        String    getByr  = "select  distinct byr , nme_idn from cs_pndg_v order by byr";

          ArrayList  outLst = db.execSqlLst("byr", getByr, new ArrayList());
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
        util.updAccessLog(req,res,"Consignment Page", "End");
            finalizeObject(db, util);
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
          util.updAccessLog(req,res,"Consignment Page", "Consignment Fetch Pkts");

        ConsignmentForm udf = (ConsignmentForm) af;
        SearchQuery srchQuery = new SearchQuery();
        info.setDlvPktList(new ArrayList());
        ArrayList pkts        = new ArrayList();
        String    flnByr      = util.nvl(udf.getPrtyIdn());
        String    saleId      = "";
        String    vnmLst      = util.nvl((String)udf.getValue("vnmLst"));
        ArrayList    params      = null;
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
        
        if(saleId.equals("")){
            boolean isRtn = true;
            if(vnmLst.length()>0){
            int cnt=0;
                String pktLst = util.getVnm(vnmLst);
                String checkSql ="select distinct c.nmerel_idn  from jansal a, mstk b , msal c " + 
                "where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='CS' and c.typ in ('CS') and c.stt='IS' " + 
                " and  ( b.vnm in ("+pktLst+") or b.tfl3 in ("+pktLst+"))";
              ArrayList  outLst = db.execSqlLst("check rel", checkSql, new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs1 = (ResultSet)outLst.get(1);
                while(rs1.next()){
                    cnt++;
                }
                rs1.close();
                pst.close();
                if(cnt==1)   
                    isRtn = false;
            }
            if(isRtn){
                req.setAttribute("RTMSG", "Please verify packets");
               return load(am, af, req, res);
            }
        }
        String    getMemoInfo =
            "select  nme_idn, nmerel_idn,byr.get_trms(nmerel_idn) trms, byr.get_nm(nme_idn) byr, stt, inv_nme_idn , fnl_trms_idn , inv_addr_idn , exh_rte exhRte , "
            + " typ, qty, cts, vlu, to_char(dte, 'dd-Mon-rrrr HH24:mi:ss') dte from msal where  typ in ('CS') and stt='IS' ";
        params = new ArrayList();
        if(!flnByr.equals("")){
        getMemoInfo = getMemoInfo+" and inv_nme_idn =?  " ;
        params.add(flnByr);
        }
        if(!saleId.equals("")){
        getMemoInfo = getMemoInfo+" and idn in ("+saleId+")  " ;

        }
            
          ArrayList  outLst = db.execSqlLst(" Memo Info", getMemoInfo, params);
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
            udf.setValue("exhRte", mrs.getString("exhRte")); 
        

        String getPktData =
            " select a.idn , a.qty , mstk_idn, nvl(fnl_sal, quot) memoQuot, quot, fnl_sal "
            + " , b.cts, nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte, a.stt , b.vnm , b.tfl3  "
            + "  , to_char(decode(rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)),9990.99) dis,  to_char(decode(rap_rte, 1, '',trunc(((nvl(fnl_sal, quot)/greatest(b.rap_rte,1))*100)-100,2)),9990.99) mDis "
            + " from jansal a, mstk b , msal c where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='CS' and c.typ in ('CS') and c.stt='IS' ";
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
          ArrayList  outLst1 = db.execSqlLst(" memo pkts", getPktData, params);
          PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
          ResultSet rs = (ResultSet)outLst1.get(1);

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
                               + " where a.mprp = b.prp and a.grp=1 and b.prp = c.prp and c.mdl = 'MEMO_RTRN' and a.mstk_idn = ? "
                               + " order by c.rnk, c.srt ";

            params = new ArrayList();
            params.add(Long.toString(pktIdn));

          ArrayList  outLst2 = db.execSqlLst(" Pkt Prp", getPktPrp, params);
          PreparedStatement pst2 = (PreparedStatement)outLst2.get(0);
          ResultSet rs2 = (ResultSet)outLst2.get(1);

            while (rs2.next()) {
                String lPrp = rs2.getString("mprp");
                String lVal = rs2.getString("val");

                pkt.setValue(lPrp, lVal);
            }
            rs2.close();
            pst2.close();
            pkts.add(pkt);
        }
            rs.close();
            pst1.close();
        }
        mrs.close();
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
           mrs = (ResultSet)outLst.get(1);

       int nmeIdn = 0;
        while (mrs.next()) {
            ByrDao byr = new ByrDao();
           if(nmeIdn==0)
             nmeIdn = mrs.getInt("nme_idn");
            byr.setByrIdn(mrs.getInt("nme_idn"));

            String nme = util.nvl(mrs.getString("byr"));

            if (nme.indexOf("&") > -1) {
                nme = nme.replaceAll("&", "&amp;");
            }

            byr.setByrNme(nme);
            byrList.add(byr);
        }
        mrs.close();
          pst.close();
        udf.setInvByrLst(byrList);
        udf.setValue("invByrIdn", udf.getInvByrIdn());
        String brokerSql =
            "select   byr.get_nm(mbrk1_idn) brk1  , mbrk1_comm , mbrk1_paid , byr.get_nm(mbrk2_idn) brk2  ,mbrk2_comm , mbrk2_paid, byr.get_nm(mbrk3_idn) brk3  ,"
            + "mbrk3_comm , mbrk3_paid, byr.get_nm(mbrk4_idn) brk4  ,mbrk4_comm , mbrk4_paid , byr.get_nm(aadat_idn) aaDat  , aadat_paid , aadat_comm  from nmerel where nmerel_idn = ?";

        ary = new ArrayList();
        ary.add(String.valueOf(udf.getRelIdn()));
          outLst = db.execSqlLst("", brokerSql, ary);
          pst = (PreparedStatement)outLst.get(0);
           mrs = (ResultSet)outLst.get(1);
        if (mrs.next()) {
            udf.setValue("brk1", mrs.getString("brk1"));
            udf.setValue("brk2", mrs.getString("brk2"));
            udf.setValue("brk3", mrs.getString("brk3"));
            udf.setValue("brk1comm", mrs.getString("mbrk1_comm"));
            udf.setValue("brk2comm", mrs.getString("mbrk2_comm"));
            udf.setValue("brk3comm", mrs.getString("mbrk3_comm"));
            udf.setValue("brk4comm", mrs.getString("mbrk4_comm"));
            udf.setValue("brk1paid", mrs.getString("mbrk1_paid"));
            udf.setValue("brk2paid", mrs.getString("mbrk2_paid"));
            udf.setValue("brk3paid", mrs.getString("mbrk3_paid"));
            udf.setValue("brk4paid", mrs.getString("mbrk4_paid"));
            udf.setValue("aaDat", mrs.getString("aaDat"));
            udf.setValue("aadatpaid", mrs.getString("aadat_paid"));
            udf.setValue("aadatcomm", mrs.getString("aadat_comm"));
        }
        mrs.close();
            pst.close();
        ary = new ArrayList();

        String sql =
            "select a.addr_idn , unt_num, bldg ||''|| street ||''|| landmark ||''|| area ||''|| b.city_nm||''|| c.country_nm addr "
            + " from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn = ? order by a.dflt_yn desc ";

        ary.add(String.valueOf(nmeIdn));

        ArrayList maddrList = new ArrayList();

          outLst = db.execSqlLst("memo pkt", sql, ary);
          pst = (PreparedStatement)outLst.get(0);
           mrs = (ResultSet)outLst.get(1);
        
        while (mrs.next()) {
            MAddr addr = new MAddr();

            addr.setIdn(mrs.getString("addr_idn"));
            addr.setAddr(mrs.getString("addr"));
            maddrList.add(addr);
        }
        mrs.close();
            pst.close();
        String bankSql = "select nme_idn, fnme  from mnme  where 1 = 1 and vld_dte is null  and typ = 'GROUP'";
        ArrayList bankList = new ArrayList();
          outLst = db.execSqlLst("bank Sql", bankSql, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
           mrs = (ResultSet)outLst.get(1);
        while(mrs.next()){
            MAddr addr = new MAddr();
            addr.setIdn(mrs.getString("nme_idn"));
            addr.setAddr(mrs.getString("fnme"));
            bankList.add(addr);
        }
        mrs.close();
        pst.close();
        udf.setBankList(bankList);
        udf.setInvAddLst(maddrList);
        info.setValue("PKTS", pkts);
        req.setAttribute("view", "Y");
          util.updAccessLog(req,res,"Consignment Page", "Consignment fetch pkt done");
            finalizeObject(db, util);
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
          util.updAccessLog(req,res,"Consignment Page", "Consignment Save");
        ArrayList params = null;
            ConsignmentForm udf = (ConsignmentForm) af;
            SearchQuery srchQuery = new SearchQuery();

        ArrayList pkts      = new ArrayList();
        String    pktNmsCs  = "";
        String    pktNmsRT  = "";
        int       appDlvIdn = 0;

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
                    ary.add(util.nvl((String) udf.getValue("comIdn")));
                    ArrayList out = new ArrayList();

                    out.add("I");

                    CallableStatement cst = null;

                    cst = db.execCall(
                        "Gen_HDR ",
                        "dlv_pkg.Gen_Hdr(pByrId => ? , pRlnIdn => ? , pTrmsIdn => ? , pInvNmeIdn => ? , pInvAddrIdn => ? , "
                        + "pTyp => ? , pStt => ?,pFrmId => ?, pFlg => ? ,paadat_paid => ?, pMBRK1_PAID => ?, pMBRK2_PAID => ?, pMBRK3_PAID => ? , "
                        + " pMBRK4_PAID => ?, bnk_idn =>? , co_idn => ? , pIdn => ?) ", ary, out);
                    appDlvIdn = cst.getInt(17);
                  cst.close();
                  cst=null;
                }
                if(appDlvIdn!=0){
                params = new ArrayList();
                params.add("DLV");
                params.add(Long.toString(lPktIdn));
                params.add(saleIdn);

                int upJan = db.execUpd("updateJAN", "update jansal set stt=? where mstk_idn=? and idn= ? ", params);

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

                pktNmsCs = pktNmsCs + "," +vnm;
                }} else {
                if (lStt.equals("RT")) {
                    params = new ArrayList();
                    params.add(saleIdn);
                    params.add(Long.toString(lPktIdn));

                    int ct = db.execCall(" App Pkts", " DLV_PKG.rtn_pkt(?, ?)", params);

                    pktNmsRT = pktNmsRT + "," +vnm;
                    req.setAttribute("RTMSG", "Packets get return: " + pktNmsRT);
                    
                }
            }
        }

        if (appDlvIdn != 0) {
            params = new ArrayList();
            params.add(String.valueOf(appDlvIdn));

            int calCt = db.execCall("calQuot", "DLV_PKG.Cal_Fnl_Quot(pIdn => ?)", params);

            params = new ArrayList();
            params.add(String.valueOf(appDlvIdn));

            int upQuery = db.execCall("calQuot", "DLV_PKG.UPD_QTY(pIdn => ? )", params);

            req.setAttribute("dlvId", String.valueOf(appDlvIdn));
            pktNmsCs=pktNmsCs.replaceFirst("\\,", "");
            req.setAttribute("CSMSG", "Packets get Deliver : " + pktNmsCs + " with delivery ID " + appDlvIdn);
        }
        int accessidn=util.updAccessLog(req,res,"Consignment Page", "Consignment Save done with ID "+appDlvIdn);
        req.setAttribute("accessidn", String.valueOf(accessidn));
            finalizeObject(db, util);
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
    public void finalizeObject(DBMgr db, DBUtil util){
        try {
            db=null;
            util=null;
        } catch (Throwable t) {
            // TODO: Add catch code
            t.printStackTrace();
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
                util.updAccessLog(req,res,"Consignment Page", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Consignment Page", "init");
            }
            }
            return rtnPg;
            }
}


//~ Formatted by Jindent --- http://www.jindent.com
