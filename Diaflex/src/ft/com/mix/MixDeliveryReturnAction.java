package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.ByrDao;
import ft.com.dao.MAddr;
import ft.com.dao.PktDtl;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SaleDeliveryForm;
import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MixDeliveryReturnAction extends DispatchAction {
   
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
            util.updAccessLog(req,res,"Mix Delivery Return", "load start");
      MixDeliveryReturnForm udf = (MixDeliveryReturnForm) af;
      String typ=util.nvl(req.getParameter("saltyp"));
      udf.setValue("salTyp", typ);
      String restrict_sale_rtn_days = util.nvl((String)info.getDmbsInfoLst().get("RESTRICT_SALE_RTN_DAYS"),"30");
      ArrayList byrList = new ArrayList();
       String    getByr  = "select  distinct dv.byr , dv.byr_idn from  DLV_MIXPNDG_V dv where exists (select 1 from mdlv md where dv.byr_idn=md.nme_idn and trunc(md.dte)>=trunc(sysdate)-"+restrict_sale_rtn_days+")  order by dv.byr";
          ArrayList  rsLst = db.execSqlLst("byr", getByr, new ArrayList());
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
                  while (rs.next()) {
                      ByrDao byr = new ByrDao();
                      byr.setByrIdn(rs.getInt("byr_idn"));
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
            util.updAccessLog(req,res,"Mix Delivery Return", "load end");
     return am.findForward("load");
        }
    }
    public ActionForward view(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Mix Delivery Return", "view start");
        MixDeliveryReturnForm udf = (MixDeliveryReturnForm) af;
        GenericInterface genericInt=new GenericImpl(); 
        String salTyp = "DLV";
        SearchQuery srchQuery = new SearchQuery();
        info.setDlvPktList(new ArrayList());
        ArrayList pkts        = new ArrayList();
        String    flnByr      = util.nvl(udf.getPrtyIdn());
        String    dlvId      = "";
        String    vnmLst      = util.nvl((String)udf.getValue("vnmLst"));
        ArrayList    params      = null;
        Enumeration reqNme = req.getParameterNames();

        while (reqNme.hasMoreElements()) {
            String paramNm = (String) reqNme.nextElement();
           if (paramNm.indexOf("cb_memo") > -1) {
                String val = req.getParameter(paramNm);

                if (dlvId.equals("")) {
                    dlvId = val;
                } else {
                   dlvId = dlvId + "," + val;
                }
            }
        }
        if(dlvId.equals("")){
            dlvId = (String)udf.getValue("dlvIdn");
        }
        
        String    getMemoInfo =
            "select  nme_idn, nmerel_idn,byr.get_trms(nmerel_idn) trms, byr.get_nm(nme_idn) byr, stt, inv_nme_idn , fnl_trms_idn , inv_addr_idn , "
            + " typ, qty, cts, vlu, to_char(dte, 'dd-Mon-rrrr HH24:mi:ss') dte from mdlv where  typ in ('"+salTyp+"') and stt='IS' ";
        params = new ArrayList();
        if(!flnByr.equals("")){
        getMemoInfo = getMemoInfo+" and inv_nme_idn =?  " ;
        params.add(flnByr);
        }
        if(!dlvId.equals("")){
        getMemoInfo = getMemoInfo+" and idn in ("+dlvId+")  " ;

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
            
        }
        mrs.close();
          stmt.close();
          String getPktData =
            " select a.idn , (a.qty - nvl(a.qty_rtn,0)) qty , mstk_idn, nvl(fnl_sal, quot) memoQuot, quot, fnl_sal , a.qty_rtn , (a.cts - nvl(a.cts_rtn,0)) cts  , a.cts_rtn  "
            + " , b.cts, nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte, a.stt , b.vnm , b.tfl3, b.pkt_ty  "
            + "  , trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2) dis, trunc(((nvl(fnl_sal, quot)/greatest(b.rap_rte,1))*100)-100,2) mDis "
            + " from dlv_dtl a, mstk b , mdlv c where a.mstk_idn = b.idn and c.idn = a.idn and a.stt = 'DLV' and c.typ in ('"+salTyp+"')  ";
        params = new ArrayList();
        if(!flnByr.equals("")){
        getPktData = getPktData+" and c.inv_nme_idn =?  " ;
        params.add(flnByr);
        }
        if(!dlvId.equals("")){
        getPktData = getPktData+" and c.idn in ("+dlvId+")  " ;
        
        }
        if(!vnmLst.equals("")){
        vnmLst = util.getVnm(vnmLst);
        getPktData = getPktData + " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
        }
        getPktData = getPktData +" order by  b.sk1 ";
          rsLst = db.execSqlLst(" memo pkts", getPktData, params);
           stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
        while (rs.next()) {
            PktDtl pkt    = new PktDtl();
            long   pktIdn = rs.getLong("mstk_idn");
            String mstkIdn = String.valueOf(pktIdn);
            pkt.setPktIdn(pktIdn);
            pkt.setDlvId(util.nvl(rs.getString("idn")));
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
            req.setAttribute("PKT_TY", util.nvl(rs.getString("pkt_ty")));
            pkt.setStt(lStt);
            udf.setValue("stt_" + pktIdn, lStt);
            udf.setValue("QR_"+mstkIdn,util.nvl(rs.getString("qty")));
            udf.setValue("CR_"+mstkIdn,util.nvl(rs.getString("cts")));
            udf.setValue("PRCR_"+mstkIdn,util.nvl(rs.getString("memoQuot")));
            
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
                               + " where a.mprp = b.prp and b.prp = c.prp and a.grp=1 and  c.mdl = 'BOX_SAL_RTN' and a.mstk_idn = ? "
                               + " order by c.rnk, c.srt ";

            params = new ArrayList();
            params.add(Long.toString(pktIdn));

          ArrayList  rsLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, params);
          PreparedStatement  stmt1 =(PreparedStatement)rsLst1.get(0);
          ResultSet  rs1 =(ResultSet)rsLst1.get(1);

            while (rs1.next()) {
                String lPrp = rs1.getString("mprp");
                String lVal = rs1.getString("val");

                pkt.setValue(lPrp, lVal);
            }
            rs1.close();
            stmt1.close();
            pkts.add(pkt);
        }
       
        rs.close();
        stmt.close();
        if(!vnmLst.equals("")){
            vnmLst = util.pktNotFound(vnmLst);
            req.setAttribute("vnmNotFnd", vnmLst);
        }

        load(am, af, req, res);

       
        info.setValue("PKTS", pkts);
        req.setAttribute("view", "Y");
            genericInt.genericPrprVw(req, res, "BOX_SAL_RTN", "BOX_SAL_RTN");
            util.updAccessLog(req,res,"Mix Delivery Return", "view end");
     return am.findForward("load");
        }
    }
    public ActionForward Return(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Mix Delivery Return", "return start");
        MixDeliveryReturnForm udf = (MixDeliveryReturnForm) af;
        ArrayList ary = new ArrayList();
        ArrayList pktList = (ArrayList)info.getValue("PKTS");
        int ct  = 0;
        String rtnPkt = "";
        for(int i=0;i<pktList.size();i++){
            PktDtl pktDtl = (PktDtl)pktList.get(i);
            long pktIdn = pktDtl.getPktIdn();
            String dlvIdn = pktDtl.getDlvId();
            String mstkIdn = String.valueOf(pktIdn);
            String vnm = pktDtl.getVnm();
            String isChecked = util.nvl((String)udf.getValue(mstkIdn));
            if(isChecked.equals("yes")){
                ary = new ArrayList();
                ary.add(dlvIdn);
                ary.add(mstkIdn);
                ary.add(util.nvl((String)udf.getValue("QR_"+mstkIdn)));
                ary.add(util.nvl((String)udf.getValue("CR_"+mstkIdn)));
                ary.add(util.nvl((String)udf.getValue("PRCR_"+mstkIdn)));
                ary.add("RT");
                String mixRtn ="dlv_pkg.Mix_Rtn_Pkt(pIdn => ? , pStkIdn => ? , pQty => ? , pCts => ? , pRte => ?, pStt => ?)";
                ct = db.execCall("mixRtn", mixRtn, ary);
                if(ct>0)
                    rtnPkt = rtnPkt+" "+vnm+" , ";
            }
        }
        
        if(ct>0)
            req.setAttribute("msg", "Packet get Return :"+rtnPkt);
            util.updAccessLog(req,res,"Mix Delivery Return", "return end");
        return am.findForward("load");
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
        util.updAccessLog(req,res,"Mix Delivery Return", "Unauthorized Access");
        else
    util.updAccessLog(req,res,"Mix Delivery Return", "init");
    }
    }
    return rtnPg;
    }
    public MixDeliveryReturnAction() {
        super();
    }
}
