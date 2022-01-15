package ft.com.box;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.PktDtl;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;

import java.sql.Connection;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class BoxDeliveryReturn extends DispatchAction {
   
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
            util.updAccessLog(req,res,"Box Dlv Return", "load start");
      BoxSaleReturnForm udf = (BoxSaleReturnForm) af;
      String typ=util.nvl(req.getParameter("saltyp"));
      udf.setValue("salTyp", typ);
            util.updAccessLog(req,res,"Box Dlv Return", "load end");
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
            util.updAccessLog(req,res,"Box Dlv Return", "view start");
        BoxSaleReturnForm udf = (BoxSaleReturnForm) af;
        GenericInterface genericInt=new GenericImpl(); 
        String salTyp = util.nvl((String)udf.getValue("salTyp"));
        if(salTyp.equals(""))
            salTyp="SL";
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
        if(saleId.equals("")){
            saleId = (String)udf.getValue("saleIdn");
        }
      
        String getPktData =
            " select a.idn , (a.qty - nvl(a.qty_rtn,0)) qty , mstk_idn, nvl(fnl_sal, quot) memoQuot, quot, fnl_sal , a.qty_rtn , (a.cts - nvl(a.cts_rtn,0)) cts  ,  a.cts_rtn  "
            + " , b.cts, nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte, a.stt , b.vnm , b.tfl3  "
            + "  , trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2) dis, trunc(((nvl(fnl_sal, quot)/greatest(b.rap_rte,1))*100)-100,2) mDis "
            + " from dlv_dtl a, mstk b , mdlv c where a.mstk_idn = b.idn and c.idn = a.idn and a.stt <> 'RT' and c.typ =?  and c.stt=? ";
        params = new ArrayList();
            params.add("DLV");
            params.add("IS");
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
        rs = db.execSql(" memo pkts", getPktData, params);

        while (rs.next()) {
            PktDtl pkt    = new PktDtl();
            long   pktIdn = rs.getLong("mstk_idn");
            String mstkIdn = String.valueOf(pktIdn);
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

            ResultSet rs1 = db.execSql(" Pkt Prp", getPktPrp, params);

            while (rs1.next()) {
                String lPrp = rs1.getString("mprp");
                String lVal = rs1.getString("val");

                pkt.setValue(lPrp, lVal);
            }
            rs1.close();
            pkts.add(pkt);
        }
       
        rs.close();
        if(!vnmLst.equals("")){
            vnmLst = util.pktNotFound(vnmLst);
            req.setAttribute("vnmNotFnd", vnmLst);
        }

        load(am, af, req, res);

       
        info.setValue("PKTS", pkts);
        req.setAttribute("view", "Y");
            genericInt.genericPrprVw(req, res, "BOX_SAL_RTN", "BOX_SAL_RTN");
            util.updAccessLog(req,res,"Box Dlv Return", "view end");
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
            util.updAccessLog(req,res,"Box Dlv Return", "return start");
        BoxSaleReturnForm udf = (BoxSaleReturnForm) af;
        ArrayList ary = new ArrayList();
        ArrayList pktList = (ArrayList)info.getValue("PKTS");
        int ct  = 0;
        String rtnPkt = "";
        for(int i=0;i<pktList.size();i++){
            PktDtl pktDtl = (PktDtl)pktList.get(i);
            long pktIdn = pktDtl.getPktIdn();
            String saleIdn = pktDtl.getSaleId();
            String mstkIdn = String.valueOf(pktIdn);
            String vnm = pktDtl.getVnm();
            String isChecked = util.nvl((String)udf.getValue(mstkIdn));
            if(isChecked.equals("yes")){
                ary = new ArrayList();
                ary.add(saleIdn);
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
            util.updAccessLog(req,res,"Box Dlv Return", "return end");
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
        util.updAccessLog(req,res,"Box Sale Return", "Unauthorized Access");
        else
    util.updAccessLog(req,res,"Box Sale Return", "init");
    }
    }
    return rtnPg;
    }
  

    public BoxDeliveryReturn() {
        super();
    }
}
