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
import java.sql.SQLException;

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

public class LocalSalRtnAction extends DispatchAction {

    
    public LocalSalRtnAction() {
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
          util.updAccessLog(req,res,"Local Sale Return", "Local Sale Return done");
        LocalSalRtnForm udf = (LocalSalRtnForm) af;
        ArrayList byrList = new ArrayList();
        String restrict_sale_rtn_days = util.nvl((String)info.getDmbsInfoLst().get("RESTRICT_SALE_RTN_DAYS"),"30");
        String    getByr  = "select  distinct sv.byr , sv.nme_idn from sal_pndg_v sv where sv.typ='LS' and exists (select 1 from msal ms where sv.nme_idn=nvl(ms.inv_nme_idn, ms.nme_idn) and trunc(ms.dte)>=trunc(sysdate)-"+restrict_sale_rtn_days+" and ms.typ='LS') order by sv.byr";

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
            
          HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
          HashMap pageDtl=(HashMap)allPageDtl.get("LOCAL_SALE");
          if(pageDtl==null || pageDtl.size()==0){
          pageDtl=new HashMap();
          pageDtl=util.pagedef("LOCAL_SALE");
          allPageDtl.put("LOCAL_SALE",pageDtl);
          }
          info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Local Sale Return", "End");
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
          util.updAccessLog(req,res,"Local Sale Return", "Local Sale Return load Pkt");
        LocalSalRtnForm udf = (LocalSalRtnForm) af;
        SearchQuery srchQuery = new SearchQuery();
        info.setDlvPktList(new ArrayList());
        ArrayList pkts        = new ArrayList();
        String    flnByr      = util.nvl(udf.getPrtyIdn());
        String    saleId      = "";
        String    vnmLst      = util.nvl((String)udf.getValue("vnmLst"));
        String restrict_sale_rtn_days = util.nvl((String)info.getDmbsInfoLst().get("RESTRICT_SALE_RTN_DAYS"),"30");
        ResultSet rs          = null;
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
                    "where a.mstk_idn = b.idn and c.idn = a.idn and a.stt in( 'SL','ADJ') and c.typ in ('LS') and c.flg1='CNF' and trunc(a.dte)>=trunc(sysdate)-"+restrict_sale_rtn_days+" " + 
                    " and  ( b.vnm in ("+pktLst+") or b.tfl3 in ("+pktLst+"))";
                  ArrayList  outLst = db.execSqlLst("check rel", checkSql, new ArrayList());
                  PreparedStatement pst = (PreparedStatement)outLst.get(0);
                  ResultSet rs1 = (ResultSet)outLst.get(1);
                    while(rs1.next()){
                        cnt++;
                       
                    }
                    rs1.close();
                    pst.close();
                    if(cnt==1){   
                        isRtn = false;
                        String saleIdSql = "select distinct c.idn  from jansal a, mstk b , msal c " + 
                        "  where a.mstk_idn = b.idn and c.idn = a.idn and a.stt in( 'SL','ADJ') and c.typ in ('LS') and c.flg1='CNF'  and trunc(a.dte)>=trunc(sysdate)-"+restrict_sale_rtn_days+" " + 
                        "  and  ( b.vnm in ("+pktLst+") or b.tfl3 in ("+pktLst+")) ";
                        rs1 = db.execSql("saleID",saleIdSql, new ArrayList());
                        
                        outLst = db.execSqlLst("saleID",saleIdSql, new ArrayList());
                      pst = (PreparedStatement)outLst.get(0);
                       rs1 = (ResultSet)outLst.get(1);
                        while(rs1.next()){
                            String salIdnval = util.nvl(rs1.getString("idn"));
                            if (saleId.equals("")) {
                                saleId = salIdnval;
                            } else {
                               saleId = saleId + "," + salIdnval;
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
            }
        String    getMemoInfo =
            "select  nme_idn, nmerel_idn,byr.get_trms(nmerel_idn) trms, byr.get_nm(nme_idn) byr, stt, inv_nme_idn , fnl_trms_idn , inv_addr_idn , exh_rte exhRte , "
            + " typ, qty, cts, vlu, to_char(dte, 'dd-Mon-rrrr HH24:mi:ss') dte from msal where  typ in ('LS') and flg1='CNF' ";
        params = new ArrayList();
        if(!flnByr.equals("") && !flnByr.equals("0")){
        getMemoInfo = getMemoInfo+" and inv_nme_idn =?  " ;
        params.add(flnByr);
        }
        if(!saleId.equals("")){
        getMemoInfo = getMemoInfo+" and idn in ("+saleId+")  " ;

        }
            
        ArrayList  outLst = db.execSqlLst(" Memo Info", getMemoInfo, params);
        PreparedStatement  pst = (PreparedStatement)outLst.get(0);
         ResultSet mrs = (ResultSet)outLst.get(1);
         if(mrs.next()) {
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
            + " , b.cts, nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte,to_char(trunc(a.cts,2) * b.rap_rte, '99999999990.00') rapVlu, a.stt , b.vnm , b.tfl3  "
            + "  , decode(rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)) dis,  decode(rap_rte, 1, '',trunc(((nvl(fnl_sal, quot)/greatest(b.rap_rte,1))*100)-100,2)) mDis "
            + " from jansal a, mstk b , msal c where a.mstk_idn = b.idn and c.idn = a.idn and a.stt in( 'SL','ADJ') and c.typ in ('LS')  and c.flg1='CNF' ";
        params = new ArrayList();
        if(!flnByr.equals("") && !flnByr.equals("0")){
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
          PreparedStatement  pst1 = (PreparedStatement)outLst1.get(0);
           ResultSet rs1 = (ResultSet)outLst1.get(1);

        while (rs1.next()) {
            PktDtl pkt    = new PktDtl();
            long   pktIdn = rs1.getLong("mstk_idn");

            pkt.setPktIdn(pktIdn);
            pkt.setSaleId(util.nvl(rs1.getString("idn")));
            pkt.setRapRte(util.nvl(rs1.getString("rap_rte")));
            pkt.setQty(util.nvl(rs1.getString("qty")));
            pkt.setCts(util.nvl(rs1.getString("cts")));
            pkt.setRte(util.nvl(rs1.getString("rte")));
            pkt.setMemoQuot(util.nvl(rs1.getString("memoQuot")));
            pkt.setByrRte(util.nvl(rs1.getString("quot")));
            pkt.setFnlRte(util.nvl(rs1.getString("fnl_sal")));
            pkt.setDis(util.nvl(rs1.getString("dis")));
            pkt.setByrDis(util.nvl(rs1.getString("mDis")));
            pkt.setVnm(util.nvl(rs1.getString("vnm")));
            String lStt = util.nvl(rs1.getString("stt")).trim();
            pkt.setStt(lStt);
            udf.setValue("stt_" + pktIdn, lStt);
            pkt.setValue("rapVlu", util.nvl(rs1.getString("rapVlu")));
            String vnm = util.nvl(rs1.getString("vnm"));
            String tfl3 = util.nvl(rs1.getString("tfl3"));
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
          PreparedStatement  pst2 = (PreparedStatement)outLst2.get(0);
           ResultSet rs2 = (ResultSet)outLst2.get(1);
            while (rs2.next()) {
                String lPrp = rs2.getString("mprp");
                String lVal = rs2.getString("val");
                if(lPrp.equals("CRTWT"))
                    lVal = rs1.getString("cts");
                pkt.setValue(lPrp, lVal);
            }
            rs2.close();
            pst2.close();
            pkts.add(pkt);
        }
        rs1.close();
        pst1.close();
        }
        mrs.close();
        pst.close();
        if(!vnmLst.equals("")){
            vnmLst = util.pktNotFound(vnmLst);
            req.setAttribute("vnmNotFnd", vnmLst);
        }

        load(am, af, req, res);

        
        info.setValue("PKTS", pkts);
        req.setAttribute("view", "Y");
        util.updAccessLog(req,res,"Local Sale Return", "Local Sale Return load Pkt done pkt size "+pkts.size()); 
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
        ArrayList params = null;

            LocalSalRtnForm udf = (LocalSalRtnForm) af;

        ArrayList pkts      = new ArrayList();
    
        String    pktNmsRT  = "";
        String    pktNmsCAN = "";

        pkts = (ArrayList) info.getValue("PKTS");    // udf.getPkts();
            if(pkts!=null && pkts.size()>0){
              util.updAccessLog(req,res,"Local Sale Return", "Local Sale Return save pkt size "+pkts.size()); 
        for (int i = 0; i < pkts.size(); i++) {
            PktDtl pkt     = (PktDtl) pkts.get(i);
            long   lPktIdn = pkt.getPktIdn();
            String saleIdn = pkt.getSaleId();
            String vnm = pkt.getVnm();
            String lStt    = util.nvl((String) udf.getValue("stt_" + lPktIdn));

   

        
                if (lStt.equals("RT")) {
                    String rmk = util.nvl((String)udf.getValue("rmk_"+Long.toString(lPktIdn)));
                    params = new ArrayList();
                    params.add(saleIdn);
                    params.add(Long.toString(lPktIdn));
                    params.add(rmk);
                    int ct = db.execCall(" App Pkts", " DLV_PKG.rtn_pkt(pIdn => ? , pStkIdn => ?,pRem => ?)", params);

                    pktNmsRT = pktNmsRT + "," +vnm;
                    req.setAttribute("RTMSG", "Packets get return: " + pktNmsRT);
                    
                }else if (lStt.equals("CAN")) {
                    params = new ArrayList();
                    params.add(saleIdn);
                    params.add(Long.toString(lPktIdn));
                    int ct = db.execCall(" Cancel Pkts ", "sl_pkg.Cancel_Pkt( pIdn =>?, pStkIdn=> ?)", params);
                    pktNmsCAN = pktNmsCAN + "," +vnm;
                    req.setAttribute("CANMSG", "Packets cancelled " + pktNmsCAN);
               } 
            
        }
              util.updAccessLog(req,res,"Local Sale Return", "Local Sale Return done"); 
            }
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
                util.updAccessLog(req,res,"Local Sale Return", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Local Sale Return", "end");
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
