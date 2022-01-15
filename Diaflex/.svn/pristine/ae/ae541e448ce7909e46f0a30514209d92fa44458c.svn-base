package ft.com.marketing;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.ByrDao;
import ft.com.dao.MAddr;
import ft.com.dao.PktDtl;
import ft.com.dao.TrmsDao;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

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
import java.util.Currency;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LocationDlvAction extends DispatchAction {
    public LocationDlvAction() {
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
          util.updAccessLog(req,res,"Local Dlv ", "Local dlv load"); 
        LocationDlvForm udf = (LocationDlvForm) af;
        udf.resetAll();
        ArrayList byrList = new ArrayList();
        String location=util.nvl(req.getParameter("LOC"));
        if(location.equals(""))
            location=util.nvl((String)req.getAttribute("location"));
        String    getByr  = "select  distinct byr , byr_idn from Dlv_Pndg_Loc_V where typ='DLV' And Loc='"+location+"'  order by byr";
          ArrayList  outLst = db.execSqlLst("byr", getByr, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            ByrDao byr = new ByrDao();
            byr.setByrIdn(rs.getInt("byr_idn"));
            byr.setByrNme(rs.getString("byr"));
            byrList.add(byr);
        }
        rs.close();
        pst.close();
        udf.setByrLst(byrList);  
        udf.setValue("location", location);
          util.updAccessLog(req,res,"Local Dlv ", "Local dlv load done "+location); 
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
          util.updAccessLog(req,res,"Local Dlv ", "Local dlv load pkt");
        LocationDlvForm udf = (LocationDlvForm) af;
        info.setDlvPktList(new ArrayList());
        info.setRecPktList(new ArrayList());
        ArrayList pkts        = new ArrayList();
        SearchQuery srchQuery = new SearchQuery();
        String    byrId  = util.nvl((String)udf.getByrIdn());;
        String    saleId      = "";
        String    vnmLst      = util.nvl((String)udf.getValue("vnmLst"));
        String location=util.nvl((String)udf.getValue("location"));
        ResultSet rs          = null;
        ArrayList    params      = null;
        udf.reset();
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
        String    getMemoInfo =
            "select  a.nme_idn, a.nmerel_idn,byr.get_trms(a.nmerel_idn) trms, byr.get_nm(a.nme_idn) byr,a.stt, a.inv_nme_idn , a.fnl_trms_idn , a.inv_addr_idn , a.exh_rte exhRte , "
            + " a.typ, a.qty, a.cts, a.vlu, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte from mdlv a where a.typ in ('DLV') and a.stt='IS' ";
        params = new ArrayList();
        if(!byrId.equals("")){
        getMemoInfo = getMemoInfo+" and a.inv_nme_idn =?  " ;
        params.add(byrId);
        }
        if(!saleId.equals("")){
        getMemoInfo = getMemoInfo+" and a.idn in ("+saleId+")  " ;

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
            HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(mrs.getInt("nme_idn")));
            udf.setValue("email",util.nvl((String)buyerDtlMap.get("EML")));
            udf.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
            udf.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
            double exh_rte = Double.parseDouble(util.nvl(mrs.getString("exhRte"),"1"));
        }
        mrs.close();
        pst.close();
        String getPktData =
              " select a.idn , a.qty , mstk_idn, nvl(fnl_sal, quot) memoQuot, quot, fnl_sal "+
              " , b.cts, nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte, a.stt , b.vnm  "+
             " , trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2) dis, trunc(((nvl(fnl_sal, quot)/greatest(b.rap_rte,1))*100)-100,2) mDis "+
             " from dlv_dtl a, mstk b , mdlv c where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='DP' and c.typ in ('DLV') and c.stt='IS' " ;
      
         params = new ArrayList();
        if(!byrId.equals("")){
        getPktData = getPktData+" and c.nme_idn in ("+byrId+")  " ;
        
        } 
        if(!saleId.equals("")){
        getPktData = getPktData+" and c.idn in ("+saleId+")  " ;
      
        }
        if(!vnmLst.equals("")){
        vnmLst = util.getVnm(vnmLst);
        getPktData = getPktData + " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+") ) ";
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
            udf.setValue("stt_" + pktIdn, lStt.trim());

            String getPktPrp = " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                               + " from stk_dtl a, mprp b, rep_prp c "
                               + " where a.mprp = b.prp and a.grp=1 and b.prp = c.prp and c.mdl = 'MEMO_RTRN' and a.mstk_idn = ? "
                               + " order by c.rnk, c.srt ";

            params = new ArrayList();
            params.add(Long.toString(pktIdn));

            
           ArrayList  outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, params);
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
        trmList = srchQuery.getTermALL(req,res, udf.getNmeIdn());
        udf.setInvTrmsLst(trmList);
        udf.setValue("invByrTrm",udf.getInvTrmsIdn());
        ArrayList byrList = new ArrayList();
        String    getByr  =
           "select nme_idn,  byr.get_nm(nme_idn) byr from mnme a " + " where nme_idn = ? "
           + " or exists (select 1 from nme_grp_dtl c, nme_grp b where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn "
           + " and b.nme_idn = ? and b.typ = 'BUYER') " + " order by 2 ";

        ary = new ArrayList();
        ary.add(String.valueOf(udf.getNmeIdn()));
        ary.add(String.valueOf(udf.getNmeIdn()));
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
        udf.setValue("invByrIdn", udf.getInvByrIdn());
        String brokerSql =
           "select   byr.get_nm(mbrk1_idn) brk1  , mbrk1_comm , mbrk1_paid , byr.get_nm(mbrk2_idn) brk2  ,mbrk2_comm , mbrk2_paid, byr.get_nm(mbrk3_idn) brk3  ,"
           + "mbrk3_comm , mbrk3_paid, byr.get_nm(mbrk4_idn) brk4  ,mbrk4_comm , mbrk4_paid , byr.get_nm(aadat_idn) aaDat  , aadat_paid , aadat_comm  from nmerel where nmerel_idn = ?";

        ary = new ArrayList();
        ary.add(String.valueOf(udf.getRelIdn()));
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

        ary.add(String.valueOf(nmeIdn));

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
        String bankSql = "select nme_idn, fnme  from mnme  where 1 = 1 and vld_dte is null  and typ  in('GROUP','BANK')";
        ArrayList bankList = new ArrayList();
        ArrayList groupList = new ArrayList();
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
        String companyQ="select a.nme_idn, a.fnme  from mnme A , nme_dtl b where 1 = 1  " +
        "and a.nme_idn=b.nme_idn and  b.mprp='PERFORMA' and b.txt='YES' " +
        "and a.vld_dte is null  and typ = 'GROUP'";
          outLst = db.execSqlLst("Group Sql", companyQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while(rs.next()){
           MAddr addr = new MAddr();
           addr.setIdn(rs.getString("nme_idn"));
           addr.setAddr(rs.getString("fnme"));
           groupList.add(addr);
        }
        rs.close();
            pst.close();
        ArrayList bnkAddrList = new ArrayList();
        String defltBnkQ="select c.nme_idn bnkidn,b.mprp,b.txt,a.addr_idn addr_idn,  a.bldg ||''|| a.street ||''|| a.landmark ||''|| a.area addr  \n" +
        "        from maddr a,nme_dtl b , mnme c\n" +
        "        where 1 = 1 and a.nme_idn=b.nme_idn\n" +
        "        and a.nme_idn = c.nme_idn  and c.typ in('GROUP','BANK')\n" +
        "        and b.mprp='PERFORMABANK' and b.txt='Y'\n" +
        "        and b.vld_dte is null";
          outLst = db.execSqlLst("defltBnkQ", defltBnkQ, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while(rs.next()){
           udf.setValue("comIdn", util.nvl(rs.getString("bnkidn"),"NA"));
           MAddr addr = new MAddr();
           addr.setIdn(rs.getString("addr_idn"));
           addr.setAddr(rs.getString("addr"));
           bnkAddrList.add(addr);
        }
        rs.close();
            pst.close();
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
        ArrayList courierList = srchQuery.getcourierList(req, res);
        udf.setCourierList(courierList);
        udf.setBnkAddrList(bnkAddrList);
        udf.setGroupList(groupList);
        udf.setBankList(bankList);
        udf.setInvAddLst(maddrList);
        req.setAttribute("view", "Y");
        req.setAttribute("location", location);
        udf.setValue("byrIdn", byrId);
        udf.setValue("location", location);
        session.setAttribute("PktList", pkts);
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("PERFORMA_INV");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("PERFORMA_INV");
            allPageDtl.put("PERFORMA_INV",pageDtl);
            }
            info.setPageDetails(allPageDtl);
          util.updAccessLog(req,res,"Local Dlv ", "Local dlv load pkt done pkts size "+pkts.size());
            finalizeObject(db, util);

        return am.findForward("load");
        }
    }
    public ActionForward RtnPkt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        LocationDlvForm udf = (LocationDlvForm) af;
          util.updAccessLog(req,res,"Local Dlv ", "Local dlv rtn start");
        String msgrecive="";
        String msgdlv="";
        ArrayList params = null;
        String location=util.nvl((String)udf.getValue("location"));
        ArrayList pktList = (ArrayList)session.getAttribute("PktList");
        if(pktList!=null && pktList.size()>0){
          util.updAccessLog(req,res,"Local Dlv ", "Local dlv rtn pktList "+pktList.size());
            for(int i=0; i<pktList.size() ; i++){
                params=new ArrayList();
                PktDtl pktDtl = (PktDtl)pktList.get(i);
                String dlvIdn = pktDtl.getSaleId();
                String pktIdn = String.valueOf(pktDtl.getPktIdn());
                String vnm = pktDtl.getVnm();
                params.add(dlvIdn);   
                params.add(pktIdn); 
                String isCheckecd = util.nvl((String)udf.getValue("stt_"+pktIdn));
                if(isCheckecd.equals("DLV")){    
                int updlvdtl = db.execUpd("update dlvdtl", "update dlv_dtl set stt='PS',dte_rtn=sysdate where idn=? and mstk_idn=?", params);    
                msgdlv=msgdlv+","+vnm;
                }else if(isCheckecd.equals("REC")){
                int updlvdtl = db.execUpd("update dlvdtl", "update dlv_dtl set stt='RT',dte_rtn=sysdate where idn=? and mstk_idn=?", params); 
                params=new ArrayList();
                params.add(pktIdn); 
                int upmstk = db.execUpd("update Mstk", "update mstk set stt='MKAV' where idn=?", params); 
                String lab=util.nvl(pktDtl.getValue("LAB"));
                params = new ArrayList();
                params.add(pktIdn);
                params.add("LOC");
                params.add(location);
                params.add(lab);
                params.add("C");
                String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?, pPrpTyp => ? )";
                int ct = db.execCall("stockUpd",stockUpd, params);
                msgrecive=msgrecive+","+vnm;
            }
            }
        }
        if(!msgrecive.equals("") && msgrecive!=null){
            msgrecive=msgrecive.replaceFirst(",", "");    
        req.setAttribute("msgrecive", "Packets get return :"+msgrecive);
        }
        if(!msgdlv.equals("") && msgdlv!=null){
            msgdlv=msgdlv.replaceFirst(",", "");    
        req.setAttribute("msgdlv", "Packets get Delivered :"+msgdlv);
        }
        util.updAccessLog(req,res,"Local Dlv ", "Local dlv rtn pktList done");
        req.setAttribute("location", location);
        udf.reset();
            finalizeObject(db, util);

        return am.findForward("load");
        }
    }
    
    public ActionForward perInv(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
    LocationDlvForm udf = (LocationDlvForm) af;
    udf.resetAll();
      util.updAccessLog(req,res,"Local Dlv ", "Local dlv performa");
    ArrayList ary = new ArrayList();
        GenericInterface genericInt = new GenericImpl();
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
    String byrIdn = util.nvl(req.getParameter("byrIdn"));
    String grpIdn = util.nvl(req.getParameter("grpIdn"));
    String relIdn = util.nvl(req.getParameter("relIdn"));
    String addrIdn = util.nvl(req.getParameter("byrAddIdn"));
    String bankIdn = util.nvl(req.getParameter("bankIdn"));
    String bankaddrIdn = util.nvl(req.getParameter("bankAddIdn"));
    String stkIdnList = util.nvl(req.getParameter("perInvIdn"));
    String brocommval = util.nvl(req.getParameter("brocommval"));
    String location = util.nvl(req.getParameter("location"));
    String echarge= util.nvl(req.getParameter("echarge"));
    String salidn= util.nvl(req.getParameter("idn"));
    if(!brocommval.equals(""))
        brocommval=String.valueOf(Float.parseFloat(brocommval)/100);
    else
        brocommval="0";  
    if(stkIdnList!=null){
        stkIdnList = util.getVnm(stkIdnList);
    }
    //    String byrNme ="select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
    //    " b.mdl = 'JFLEX' and b.nme_rule ='PRM_INV' and a.til_dte is null order by a.srt_fr ";
    //    ary = new ArrayList();
    //
    //    rs = db.execSql("byrName", byrNme, ary);
    //    while(rs.next()){
    //    udf.setValue(rs.getString("dsc"), rs.getString("chr_fr"));
    //    }
        String grp="select mprp, txt from nme_dtl where nme_idn=? and vld_dte is null";
        ary = new ArrayList();
        ary.add(grpIdn);
      ArrayList  outLst = db.execSqlLst("Company DtlQ", grp, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
            
        while(rs.next()){
        udf.setValue(util.nvl(rs.getString("mprp")), util.nvl(rs.getString("txt"),"NA"));
        }
        rs.close();
        pst.close();
            
        String nmev = "select co_nme from nme_v where nme_idn=? ";
        ary =  new ArrayList();
        ary.add(byrIdn);
       outLst = db.execSqlLst("addr",nmev, ary);
       pst = (PreparedStatement)outLst.get(0);
       rs = (ResultSet)outLst.get(1);
        if(rs.next()){
            udf.setValue("byrnme", util.nvl(rs.getString("co_nme")));  
        }
        rs.close();
        pst.close();
        
    String addrSql = "select a.addr_idn , unt_num, byr.get_nm(nme_idn) byr, bldg ||''|| street bldg , landmark ||''|| area landMk , b.city_nm||' '|| c.country_nm addr "+
    " from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.addr_idn = ? order by a.dflt_yn desc ";
    ary = new ArrayList();
    ary.add(addrIdn);
     outLst = db.execSqlLst("addr", addrSql, ary);
      pst = (PreparedStatement)outLst.get(0);
      rs = (ResultSet)outLst.get(1);
    if(rs.next()){
    udf.setValue("unit_no", rs.getString("unt_num"));
    udf.setValue("bldg", rs.getString("bldg"));
    udf.setValue("landMk", rs.getString("landMk"));
    //udf.setValue("city", rs.getString("addr"));
    udf.setValue("addr", rs.getString("addr"));
    }
   rs.close();
   pst.close();
            
    String addDtl = "select mprp, txt from nme_dtl where nme_idn = ? and mprp in ('TEL_NO','EMAIL','FAX','PRE-CARRIAGE-BY','VESSEL/FLIGHT.NO','PORT OF DISCHARGE','FINAL DESTINATION') ";
    ary = new ArrayList();
    ary.add(byrIdn);
      outLst = db.execSqlLst("addDtl", addDtl, ary);
       pst = (PreparedStatement)outLst.get(0);
       rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    udf.setValue(util.nvl(rs.getString("mprp")), util.nvl(rs.getString("txt"),"NA"));

    }
        rs.close();
        pst.close();
    String terms="";
    String termsDtl ="select b.nmerel_idn, nvl(c.trm_prt, c.term) trms from nmerel b, mtrms c where b.trms_idn = c.idn and b.nmerel_idn=? ";
    ary = new ArrayList();
    ary.add(relIdn);
      outLst = db.execSqlLst("termsDtl", termsDtl, ary);
       pst = (PreparedStatement)outLst.get(0);
       rs = (ResultSet)outLst.get(1);
    if(rs.next()){
        terms=util.nvl(rs.getString("trms"));
    // udf.setValue("terms", util.nvl(rs.getString("dtls")));
    udf.setValue("terms",terms);

    }   
        rs.close();
        pst.close();
    String consignee = "select del_typ,cur from nmerel where nmerel_idn=? ";
    ary = new ArrayList();
    ary.add(relIdn);
      outLst = db.execSqlLst("consignee", consignee, ary);
       pst = (PreparedStatement)outLst.get(0);
       rs = (ResultSet)outLst.get(1);
    if(rs.next()){
    // udf.setValue("terms", util.nvl(rs.getString("dtls")));
    udf.setValue("consignee", util.nvl(rs.getString("del_typ")));
    udf.setValue("termsSign",util.nvl(rs.getString("cur")));
    }
        rs.close();
        pst.close();
    String bankDtl = "select a.addr_idn , d.rmk , b.city_nm , d.fnme byrNme, bldg ||''|| street bldg , landmark ||''|| area landMk , b.city_nm||' '|| c.country_nm||' '|| a.zip addr  " +
    " from maddr a, mcity b, mcountry c , mnme d where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn = d.nme_idn " +
    " and a.nme_idn = ? and a.addr_idn=? order by a.dflt_yn desc ";
    ary = new ArrayList();
    ary.add(bankIdn);
    ary.add(bankaddrIdn);
      outLst = db.execSqlLst("addr",bankDtl, ary);
       pst = (PreparedStatement)outLst.get(0);
       rs = (ResultSet)outLst.get(1);
    if(rs.next()){
    udf.setValue("bankBldg", rs.getString("bldg"));
    udf.setValue("bankLand", rs.getString("landMk"));
    udf.setValue("bankCity", rs.getString("addr"));
    udf.setValue("bankNme", rs.getString("byrNme"));
    udf.setValue("rmk", rs.getString("rmk"));
    udf.setValue("cityNme", rs.getString("city_nm"));
    }
        rs.close();
        pst.close();
    ArrayList pktList = new ArrayList();
    String getInvPkts = " select a.idn ,b.cert_no, a.qty ,b.vnm , a.mstk_idn,  to_char((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?),'9999999999,990.00') quot, "+
                " to_char(b.cts,'90.00') cts ,b.rap_rte, to_char(trunc(trunc(b.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?), 2),'9999990.00') vlu , "+
                " To_Char(trunc((((Nvl(Fnl_Sal, Quot)-Nvl(Fnl_Sal,Quot)*?)/greatest(b.rap_rte,1))*100)-100,2),'9990.99') dis,to_char(sum(trunc(a.cts,2)) over(partition by a.stt),'90.99') ttl_cts , "+
                " to_char(sum(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?)) over(partition by a.stt) ,'999999990.00') ttl_vlu , sum(a.qty) over(partition by a.stt) ttl_qty "+
                " from Dlv_Dtl a, mstk b , Mdlv c where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='DP' and c.typ in ('DLV') and c.stt='IS' and a.mstk_idn in ("+stkIdnList+") ";
    ArrayList params=new ArrayList();
        params.add(brocommval);
        params.add(brocommval);
        params.add(brocommval);
        params.add(brocommval);  
      outLst = db.execSqlLst("performInv", getInvPkts, params);
       pst = (PreparedStatement)outLst.get(0);
       rs = (ResultSet)outLst.get(1);
        String ttl = null;
    while(rs.next()){
    HashMap pktListMap = new HashMap();
    String mstkIdn = rs.getString("mstk_idn");
    pktListMap.put("cts",util.nvl(rs.getString("cts")));
    pktListMap.put("qty",util.nvl(rs.getString("qty")));
    pktListMap.put("quot",util.nvl(rs.getString("quot")));
    pktListMap.put("vlu",util.nvl(rs.getString("vlu")));
    pktListMap.put("rap_rte",util.nvl(rs.getString("rap_rte")));
    pktListMap.put("rapdis",util.nvl(rs.getString("dis")));
    pktListMap.put("cert_no",util.nvl(rs.getString("cert_no")));
   
    String getPktPrp =
    " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
    + " from stk_dtl a, mprp b, rep_prp c "
    + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'PERINV_VW_LOC' and grp=1 and a.mstk_idn = ? "
    + " order by c.rnk, c.srt ";

    ary = new ArrayList();
    ary.add(mstkIdn);
     ArrayList outLst1 = db.execSqlLst("performInv", getPktPrp, ary);
     PreparedStatement  pst1 = (PreparedStatement)outLst1.get(0);
    ResultSet rs1 = (ResultSet)outLst1.get(1);
    while (rs1.next()) {
    String lPrp = util.nvl(rs1.getString("mprp"));
    String lVal = util.nvl(rs1.getString("val"));

    pktListMap.put(lPrp, lVal);  
    }
    rs1.close();
    pst1.close();
        
    pktListMap.put("VNM",util.nvl(rs.getString("vnm")));
    pktList.add(pktListMap);
    udf.setValue("ttlCts", rs.getString("ttl_cts"));
    udf.setValue("ttlQty", rs.getString("ttl_qty"));
    ttl=util.nvl(rs.getString("ttl_vlu")).trim();
    udf.setValue("ttlVlu", ttl);
    udf.setValue("echarge", echarge);
        
        
    
    }
        rs.close();
        pst.close();
    if(ttl!=null && !ttl.equals("")){
     String Gtotal="0";    
    if(!echarge.equals(""))
        Gtotal=String.valueOf(Double.parseDouble(ttl)+Double.parseDouble(echarge));
    else
        Gtotal=ttl;
    params=new ArrayList();
    if(!salidn.equals("")){
    params.add(salidn);
    String imcQ="Select A.Typ typ,A.dsc dsc,b.charges From Charges_Typ A,Trns_Charges B\n" + 
    "  where a.idn=b.charges_idn and ref_idn=? ";
      outLst = db.execSqlLst("Charges", imcQ, params); 
       pst = (PreparedStatement)outLst.get(0);
       rs = (ResultSet)outLst.get(1);
    ArrayList chargeLst=new ArrayList();      
    while(rs.next()){
        HashMap charData=new HashMap();
        String charge=util.nvl(rs.getString("charges"));
        charData.put("DSC", util.nvl(rs.getString("dsc")));
        charData.put("CHARGE",charge);
        chargeLst.add(charData);
        Gtotal=String.valueOf(Math.round(Double.parseDouble(Gtotal)+Double.parseDouble(charge)));
    }
        rs.close();
        pst.close();
        req.setAttribute("chargeLst", chargeLst);
    }
    udf.setValue("grandttlVlu", Gtotal);
        //String num=rs.getString("ttl_vlu").trim();
    String words=util.convertNumToAlp(Gtotal);
     udf.setValue("inwords",words);
    }
    genericInt.genericPrprVw(req, res, "perInvViewLstHk", "PERINV_VW_LOC");
    util.displayDtlsLoc(udf,location);
    req.setAttribute("pktList", pktList);
      util.updAccessLog(req,res,"Local Dlv ", "Local dlv performa done");
        finalizeObject(db, util);

    return am.findForward("perInv");
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
                util.updAccessLog(req,res,"Local Dlv ", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Local Dlv ", "init");
            }
            }
            return rtnPg;
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
}


//~ Formatted by Jindent --- http://www.jindent.com

