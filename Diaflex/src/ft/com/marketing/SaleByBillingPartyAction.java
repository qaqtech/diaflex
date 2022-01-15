package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.ByrDao;

import ft.com.dao.MAddr;
import ft.com.dao.MNme;
import ft.com.dao.PktDtl;

import ft.com.generic.GenericImpl;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

public class SaleByBillingPartyAction  extends DispatchAction {
    public SaleByBillingPartyAction() {
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
        SaleByBillingPartyForm udf = (SaleByBillingPartyForm) af;
        util.updAccessLog(req,res,"Sale By Billing party", "Sale By Billing party load");
        ArrayList byrList = new ArrayList();
        String    getByr  =
            "select distinct a.nme_idn, a.nme byr \n" + 
            "            from nme_v a ,nme_grp b,nme_grp_dtl c, mjan d \n" + 
            "            where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn \n" + 
            "            and b.nme_idn=d.nme_idn and d.stt='IS' and d.typ in('EAP', 'IAP', 'WAP', 'LAP','MAP','SAP','OAP','HAP','BAP','KAP','BIDAP')\n" + 
            "            and b.typ = 'BUYER' and c.flg is null  order by 2";

          ArrayList  outLst = db.execSqlLst("byr", getByr, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);

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
        HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_SALE_BILLING");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("MEMO_SALE_BILLING");
        allPageDtl.put("MEMO_SALE_BILLING",pageDtl);
        }
        info.setPageDetails(allPageDtl);
          util.updAccessLog(req,res,"Sale By Billing party", "Sale By Billing party load done");
            finalizeObject(db,util);

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
        SaleByBillingPartyForm udf = (SaleByBillingPartyForm) af;
        util.updAccessLog(req,res,"Sale By Billing party", "Sale By Billing party load pkts");
        SearchQuery srchQuery = new SearchQuery();
        info.setDlvPktList(new ArrayList());
        ArrayList pkts        = new ArrayList();
        ArrayList byrrln    = new ArrayList();
        ArrayList byrIdnLst        = new ArrayList();
        HashMap memoPktMap = new HashMap();
        String    vnmLst      = util.nvl((String)udf.getValue("vnmLst"));
        HashMap byrDtl =new HashMap();
        int   invByrId      = udf.getInvByrIdn();
        ArrayList    params      = null;
        ArrayList vwPrpLst = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
        Enumeration reqNme = req.getParameterNames();
        String nmeId="";
        String pnmeidn="";
        int ct = db.execUpd(" del gt", "delete from gt_srch_rslt", new ArrayList());
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
        if(nmeId.equals("")){
            boolean isRtn = true;
            if(vnmLst.length()>0){
            int cnt=0;
            vnmLst=util.getVnm(vnmLst); 
                String checkSql ="select distinct a.nme_idn\n" + 
                "from nme_v a ,nme_grp b,nme_grp_dtl c, mjan d ,jandtl e, mstk f\n" + 
                "where 1 = 1\n" + 
                "and a.nme_idn = c.nme_idn\n" + 
                "and b.nme_grp_idn = c.nme_grp_idn\n" + 
                "and d.idn = e.idn\n" + 
                "and e.mstk_idn = f.idn\n" + 
                "and b.nme_idn = d.nme_idn\n" + 
                "and d.stt='IS' and d.typ in('EAP', 'IAP', 'WAP', 'LAP','MAP','SAP','HAP','BAP','KAP','BIDAP','OAP') and e.stt = 'AP' \n" + 
                " and  ( f.vnm in ("+vnmLst+") or f.tfl3 in ("+vnmLst+")) "+
                "and b.typ = 'BUYER' and c.flg is null ";
                
              ArrayList  outLst = db.execSqlLst("check rel", checkSql, new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet  rs = (ResultSet)outLst.get(1);

                if(rs.next()){
                    cnt++;
                    invByrId=rs.getInt("nme_idn");
                }
                rs.close();
                pst.close();
                if(cnt==1){   
                    isRtn = false;
                    String saleIdSql = "select distinct b.nme_idn\n" + 
                    "                        from nme_v a ,nme_grp b,nme_grp_dtl c, mjan d,jandtl e, mstk f \n" + 
                    "                       where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn \n" + 
                    "                        and b.nme_idn=d.nme_idn and e.mstk_idn = f.idn and e.idn=d.idn \n" + 
                    "                    and e.stt = 'AP' and d.stt='IS' and d.typ like '%AP' and c.flg is null " + 
                    " and  ( f.vnm in ("+vnmLst+") or f.tfl3 in ("+vnmLst+")) ";
                 outLst = db.execSqlLst("saleID",saleIdSql, new ArrayList());
                 pst = (PreparedStatement)outLst.get(0);
                 rs = (ResultSet)outLst.get(1);
                  while(rs.next()){
                        String nameIdnval = util.nvl(rs.getString("nme_idn"));
                        if (nmeId.equals("")) {
                            nmeId = nameIdnval;
                        } else {
                           nmeId = nmeId + "," + nameIdnval;
                        }
                    }
                    rs.close();
                    pst.close();
                }
            }
            if(isRtn){
                req.setAttribute("RTMSG", "Please verify packets");
               return load(am, af, req, res);
            }
        }else{
            if(!vnmLst.equals("")){
            vnmLst=util.getVnm(vnmLst);
            }
        }
        
        String insgtPkt =
            "Insert into gt_srch_rslt (srch_id,pair_id,rln_idn,stk_idn, vnm ,pkt_ty,qty,cts,stt,dsp_stt,flg,quot,fquot,prte,sk1,rap_rte,byr,exh_rte)\n" + 
            "select a.idn ,c.nme_idn,c.nmerel_idn,b.idn, b.vnm,b.pkt_ty,a.qty ,b.cts,a.stt,b.stt,c.typ, quot, nvl(fnl_sal, quot),nvl(b.upr, b.cmp), nvl(b.sk1,1), b.rap_rte, byr.get_nm(c.nme_idn),get_xrt(c.cur)\n" + 
            "from jandtl a, mstk b,mjan c where a.mstk_idn = b.idn and a.idn=c.idn and c.nme_idn in (" + nmeId + ")\n" + 
            "and a.stt = 'AP' and c.stt='IS' and c.typ like '%AP' ";
        if(!vnmLst.equals("")){
        insgtPkt = insgtPkt + " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
        }
        ct = db.execUpd(" ins scan", insgtPkt, new ArrayList());
        
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        params = new ArrayList();
        params.add("MEMO_RTRN");
        ct = db.execCall(" Srch Prp ", pktPrp, params);
        
        String srchQ="select distinct pair_id nme_idn,byr,flg typ,sum(qty) qty,sum(cts) cts\n" + 
        "from gt_srch_rslt\n" + 
        " group by pair_id , byr ,flg";
        params = new ArrayList();
         ArrayList outLst = db.execSqlLst(" Memo Info", srchQ , new ArrayList()); 
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String nmeidn=util.nvl(rs.getString("nme_idn"));  
            byrDtl.put(nmeidn+"_BYR", util.nvl(rs.getString("byr")));  
            byrDtl.put(nmeidn+"_TYP", util.nvl(rs.getString("typ")));
            byrDtl.put(nmeidn+"_QTY", util.nvl(rs.getString("qty")));
            byrDtl.put(nmeidn+"_CTS", util.nvl(rs.getString("cts")));
        }
        rs.close();
        pst.close();
        srchQ="select distinct a.pair_id nme_idn,a.rln_idn,c.term||' '||b.cur||' : '||byr.get_nm(b.AADAT_IDN)||'/'||byr.get_nm(b.mbrk2_idn) trms\n" + 
        "from gt_srch_rslt a,nmerel b,mtrms c where a.pair_id=b.nme_idn and a.rln_idn=b.nmerel_idn and b.trms_idn = c.idn\n" + 
        " group by a.pair_id , a.rln_idn , c.term||' '||b.cur||' : '||byr.get_nm(b.AADAT_IDN)||'/'||byr.get_nm(b.mbrk2_idn) order by a.pair_id";
        params = new ArrayList();
          outLst = db.execSqlLst(" Memo Info", srchQ , new ArrayList());  
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String nmeidn=util.nvl(rs.getString("nme_idn"));  
            if(pnmeidn.equals(""))
            pnmeidn = nmeidn;
            if(!pnmeidn.equals(nmeidn)){
            byrDtl.put(pnmeidn+"_RLN", byrrln);
            byrrln = new ArrayList();
            pnmeidn = nmeidn;
            }
            ArrayList byrrlndtl=new ArrayList();
            byrrlndtl.add(util.nvl(rs.getString("trms")));
            byrrlndtl.add(util.nvl(rs.getString("rln_idn")));
            byrrln.add(byrrlndtl);
        }
            rs.close();
            pst.close();
        if(!pnmeidn.equals("")){
            byrDtl.put(pnmeidn+"_RLN", byrrln);
        }
        
        srchQ="select pair_id nme_idn,qty,cts,srch_id idn,stk_idn,vnm,quot,fquot,prte rte,sk1,rap_rte,stt,dsp_stt pktstt,to_char(trunc(cts,2) * rap_rte, '99999999990.00') rapVlu,\n" + 
        "decode(rap_rte, 1, '', trunc(((prte/greatest(rap_rte,1))*100)-100,2)) dis,\n" + 
        "decode(rap_rte, 1, '', trunc(((fquot/exh_rte/greatest(rap_rte,1))*100)-100,2)) mDis \n" ;
        for (int i = 0; i < vwPrpLst.size(); i++) {
            String fld = "prp_";
            int j = i + 1;
            if (j < 10)
                fld += "00" + j;
            else if (j < 100)
                fld += "0" + j;
            else if (j > 100)
                fld += j;
            srchQ += ", " + fld;
         }
        String rsltQ = srchQ +" from gt_srch_rslt\n" + 
        "order by byr,srch_id,sk1\n";
        pnmeidn="";
          outLst = db.execSqlLst(" Memo Info", rsltQ , new ArrayList()); 
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
           String nmeidn=util.nvl(rs.getString("nme_idn"));  
                if(pnmeidn.equals(""))
                    pnmeidn = nmeidn;
                if(!pnmeidn.equals(nmeidn)){
                    memoPktMap.put(String.valueOf(pnmeidn), pkts);
                    byrIdnLst.add(String.valueOf(pnmeidn));
                    pkts = new ArrayList();
                    pnmeidn = nmeidn;
                }
                PktDtl pkt    = new PktDtl();
                long   pktIdn = rs.getLong("stk_idn");
                pkt.setPktIdn(pktIdn);
                pkt.setMemoId(util.nvl(rs.getString("idn")));
                pkt.setRapRte(util.nvl(rs.getString("rap_rte")));
                pkt.setQty(util.nvl(rs.getString("qty")));
                pkt.setCts(util.nvl(rs.getString("cts")));
                pkt.setRte(util.nvl(rs.getString("rte")));
                pkt.setMemoQuot(util.nvl(rs.getString("fquot")));
                pkt.setByrRte(util.nvl(rs.getString("quot")));
                pkt.setFnlRte(util.nvl(rs.getString("fquot")));
                pkt.setDis(util.nvl(rs.getString("dis")));
                pkt.setByrDis(util.nvl(rs.getString("mDis")));
                pkt.setVnm(util.nvl(rs.getString("vnm")));
                String lStt = util.nvl(rs.getString("stt"));

                pkt.setStt(lStt);
                udf.setValue("stt_" + pktIdn, lStt);
                pkt.setValue("pktstt", util.nvl(rs.getString("pktstt")));
                pkt.setValue("rapVlu", util.nvl(rs.getString("rapVlu")));
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      String val = util.nvl(rs.getString(fld)) ;
                      pkt.setValue(prp, val);
                      }
                pkts.add(pkt);
            
            }
            rs.close();
            pst.close();
        if(!pnmeidn.equals("")){
            memoPktMap.put(String.valueOf(pnmeidn), pkts);
            byrIdnLst.add(String.valueOf(pnmeidn));
        }
        ArrayList byrList = new ArrayList();
        String    getByr  =
            "select nme_idn,  byr.get_nm(nme_idn) byr from mnme a " + " where nme_idn = ? "
            + " or exists (select 1 from nme_grp_dtl c, nme_grp b where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn "
            + " and b.nme_idn = ? and b.typ = 'BUYER' and c.flg is null)  " + " order by 2 ";

        ArrayList ary = new ArrayList();
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
        ArrayList bnkAddrList = new ArrayList();
            boolean dfltbankgrp=true;
            String banknmeIdn=  "" ;
            ary = new ArrayList();
            ary.add(String.valueOf(invByrId));
            String setbnkCouQ="select bank_id,courier from  msal where idn in(select max(idn) from msal where nme_idn=?)";
          outLst = db.execSqlLst("setbnkCouQ", setbnkCouQ, ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            if(rs.next()){
                banknmeIdn=util.nvl(rs.getString("bank_id"));
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
              outLst = db.execSqlLst("defltBnkQ", defltBnkQ, new ArrayList());
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
            String chargesQ="Select ct.idn,ct.typ,ct.dsc,ct.stg optional,ct.flg,ct.fctr,ct.db_call,ct.rmk\n" + 
            "    From Df_Pg A,Df_Pg_Itm B,Charges_Typ ct\n" + 
            "    Where A.Idn = B.Pg_Idn\n" + 
            "    and b.fld_nme=ct.typ\n" + 
            "    And A.Mdl = 'MEMO_SALE_BILLING'\n" + 
            "    and b.itm_typ=?\n" + 
            "    And A.Stt='A'\n" + 
            "    And B.Stt='A'\n" + 
            "    and ct.stt='A'\n" + 
            "    And A.Vld_Dte Is Null \n" + 
            "    And Not Exists (Select 1 From Df_Pg_Itm_Usr C Where B.Idn=C.Itm_Idn And C.Stt='IA' And C.Usr_Idn=?)\n" + 
            "    order by b.itm_typ,b.srt";
            ary = new ArrayList();
            ary.add("SALE_CHARGES");
            ary.add(String.valueOf(info.getUsrId()));
          outLst = db.execSqlLst("chargesQ", chargesQ,ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
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
            pst.close();
        session.setAttribute("chargesLst", chargesLst);
        udf.setValue("invByrIdn", String.valueOf(invByrId));
        ArrayList groupList = srchQuery.getgroupList(req, res);
        ArrayList bankList = srchQuery.getBankList(req, res);
        ArrayList courierList = srchQuery.getcourierList(req, res);
        udf.setCourierList(courierList);
        udf.setBnkAddrList(bnkAddrList);
        udf.setGroupList(groupList);
        udf.setBankList(bankList);
        session.setAttribute("pktMemoMap", memoPktMap);
        session.setAttribute("byrIdnLst", byrIdnLst);
        session.setAttribute("byrDtl", byrDtl);
        req.setAttribute("view","Y");
          util.updAccessLog(req,res,"Sale By Billing party", "Sale By Billing party pkt list size "+pkts.size());
            finalizeObject(db,util);

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
    SaleByBillingPartyForm udf = (SaleByBillingPartyForm) af;
    util.updAccessLog(req,res,"Sale By Billing party", "Sale By Billing party save");
    HashMap pktMemoMap =(HashMap)session.getAttribute("pktMemoMap");
    ArrayList byrIdnLst =(ArrayList)session.getAttribute("byrIdnLst");
    HashMap byrDtl =(HashMap)session.getAttribute("byrDtl");   
    String   invByrId      = util.nvl((String)udf.getValue("invByrIdn"));
    int byrIdnLstsz=byrIdnLst.size();
    String typ="SL";
    ArrayList salidnLst=new ArrayList();
    ArrayList params = null;
//    String    pktNmsSl = "";
//    String    pktNmsRT = "";
    for(int k=0 ; k < byrIdnLstsz;k++){
    String nmeidn = (String)byrIdnLst.get(k);
    String rlnidn =util.nvl((String)udf.getValue(nmeidn));  
    ArrayList pkts =(ArrayList)pktMemoMap.get(nmeidn);   
    int appSlIdn = 0;
        if(pkts!=null && pkts.size()>0){
        for (int i = 0; i < pkts.size(); i++) {
            PktDtl pkt     = (PktDtl) pkts.get(i);
            long   lPktIdn = pkt.getPktIdn();
            String memoIdn = pkt.getMemoId();
            String lStt    = util.nvl((String) udf.getValue("stt_" + lPktIdn));
            String vnm = pkt.getVnm();

        if (lStt.equals("SL")) {
            if (appSlIdn == 0) {
                ArrayList ary = new ArrayList();
                String addr="";
                String brokerSql =
                    "select   byr.get_nm(mbrk1_idn) brk1  , mbrk1_comm , mbrk1_paid , byr.get_nm(mbrk2_idn) brk2  ,mbrk2_comm , mbrk2_paid, byr.get_nm(mbrk3_idn) brk3  ,"
                    + "mbrk3_comm , mbrk3_paid, byr.get_nm(mbrk4_idn) brk4  ,mbrk4_comm , mbrk4_paid , byr.get_nm(aadat_idn) aaDat  , aadat_paid , aadat_comm,get_xrt(cur) xrt  from nmerel where nmerel_idn = ?";
                ary = new ArrayList();
                ary.add(rlnidn);
              ArrayList  outLst = db.execSqlLst("", brokerSql, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet  rs = (ResultSet)outLst.get(1);
                if(rs.next()){
                String sql =  "select addr_idn from maddr where nme_idn =? ";
                ary = new ArrayList();
                ary.add(nmeidn);
              ArrayList  outLst1 = db.execSqlLst("memo pkt", sql, ary);
              PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
              ResultSet  rs1 = (ResultSet)outLst1.get(1);
                if(rs1.next()){
                    addr=util.nvl((String)rs1.getString("addr_idn"));
                }
                rs1.close();
                pst1.close();
                ary = new ArrayList();
                ary.add(nmeidn);
                ary.add(rlnidn);
                ary.add(rlnidn);
                ary.add(invByrId);
                ary.add(addr);
                ary.add(typ);
                ary.add("IS");
                ary.add(memoIdn);
                ary.add("NN");
                ary.add(nvl((String)rs.getString("aadat_paid"), "Y"));
                ary.add(nvl((String)rs.getString("mbrk1_paid"), "Y"));
                ary.add(nvl((String)rs.getString("mbrk2_paid"), "Y"));
                ary.add(nvl((String)rs.getString("mbrk3_paid"), "Y"));
                ary.add(nvl((String)rs.getString("mbrk4_paid"), "Y"));
                ary.add(util.nvl((String)rs.getString("xrt")));
                ary.add(util.nvl((String) udf.getValue("bankIdn")));
                ary.add(util.nvl((String) udf.getValue("courier")));
                ary.add(util.nvl((String)udf.getValue("throubnk")));
                ary.add(util.nvl((String)udf.getValue("grpIdn")));
                ArrayList out = new ArrayList();

                out.add("I");

                CallableStatement cst = null;

                cst = db.execCall(
                    "MKE_HDR ",
                    "sl_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pTrmsIdn => ?, pInvNmeIdn => ?, pInvAddrIdn => ?, pTyp => ?, pStt => ?, pFrmId => ? , pFlg => ? , paadat_paid => ?, pMBRK1_PAID => ? , pMBRK2_PAID => ?, pMBRK3_PAID => ?, pMBRK4_PAID => ?, pExhRte => ?, p_bank_id => ? ,p_courier => ? ,pThruBnk => ?,co_idn => ?,  pIdn => ? )", ary, out);
                appSlIdn = cst.getInt(ary.size()+1);
              cst.close();
              cst=null;
                salidnLst.add(String.valueOf(appSlIdn));
            }
                rs.close();
                pst.close();
            }
            if (appSlIdn != 0) {
            params = new ArrayList();
            params.add("SL");
            params.add(Long.toString(lPktIdn));
            params.add(memoIdn);

            int upJan = db.execUpd("updateJAN", "update jandtl set stt=? , dte_sal=sysdate where mstk_idn=? and idn= ? ", params);
            params = new ArrayList();
            params.add(String.valueOf(appSlIdn));
            params.add(memoIdn);
            params.add(Long.toString(lPktIdn));
            params.add(pkt.getQty());
            params.add(pkt.getCts());
            params.add(pkt.getMemoQuot());
            params.add("SL");

            int SalPkt = db.execCall("sale from memo",
                                     "sl_pkg.Sal_Pkt(" + "pIdn => ?" + ", pMemoIdn =>?" + ", pStkIdn => ?"
                                     + ", pQty => ?" + ", pCts => ?" + ", pFnlSal=> ?" + ", pStt => ?)", params);

//            pktNmsSl = pktNmsSl + "," + Long.toString(lPktIdn);
            }
        }else if (lStt.equals("RT")) {
                    String rmk = util.nvl((String)udf.getValue("rmk_"+Long.toString(lPktIdn)));
                    params = new ArrayList();
                    params.add(memoIdn);
                    params.add(Long.toString(lPktIdn));
                    params.add(rmk);
                    int ct = db.execCall(" App Pkts", "memo_pkg.rtn_pkt( pMemoId =>?, pStkIdn=> ? , pRem => ?)", params);

//                    pktNmsRT = pktNmsRT + "," +vnm;
                    req.setAttribute("RTMSG", "Packets get return ");
            }else{
                
                
            }
            if (!lStt.equals("AP")) {
            String updJanQty = " jan_qty(?) ";

            params = new ArrayList();
            params.add(memoIdn);
            int ct1 = db.execCall("JanQty", updJanQty, params);
            }
        }
        }
        if (appSlIdn != 0) {
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
            "VALUES (TRNS_CHARGES_SEQ.nextval, 'SAL', ?,?,?,?,?,?,?,?,'A',?)";  
            ArrayList ary=new ArrayList();
            ary.add(String.valueOf(appSlIdn));
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
            params.add(String.valueOf(appSlIdn));

            int calCt = db.execCall("calQuot", "SL_PKG.Cal_Fnl_Quot(pIdn => ?)", params);

            params = new ArrayList();
            params.add(String.valueOf(appSlIdn));

            int upQuery = db.execCall("calQuot", "SL_PKG.UPD_QTY(pIdn => ? )", params);
          util.updAccessLog(req,res,"Sale By Billing party", "Sale By Billing party save sale ID "+appSlIdn);
        }
    }
    if(salidnLst.size()>0){
        String saleidn = salidnLst.toString();
        saleidn = saleidn.replaceAll("\\[", "");
        saleidn = saleidn.replaceAll("\\]", "").replaceAll(" ", "");
    req.setAttribute("SLMSG", "Sale Ids" + saleidn);
    }
          util.updAccessLog(req,res,"Sale By Billing party", "Sale By Billing party save done");       
            finalizeObject(db,util);

    return load(am, af, req, res);
        }
                }
    }
    
    public ActionForward priceCalcSaleByBill(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    StringBuffer sb = new StringBuffer();
    String stkIdn = util.nvl(req.getParameter("stkIdn"));
    String memoIdn = util.nvl(req.getParameter("memoIdn"));

    stkIdn = stkIdn.replaceFirst(",", "");
    memoIdn = memoIdn.replaceFirst(",", "");

    if(!stkIdn.equals("") || !memoIdn.equals("")) {

    String getSum = "select count(*) qty, sum(trunc(a.cts,2)) cts , trunc(sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)), 2) vlu, trunc(((sum(trunc(a.cts,2)*(nvl(a.fnl_sal, a.quot))/get_xrt(c.cur)) / sum(trunc(a.cts,2)*b.rap_rte))*100) - 100, 2) avg_dis ," +
    " trunc(((sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts,2))))) avg_Rte from jandtl a, mstk b , mjan c " +
    " where a.mstk_idn = b.idn and a.stt = 'AP' and c.idn = a.idn  and c.stt='IS' and b.stt not in('LB_PRI_AP') " +
    " and a.mstk_idn in (" + stkIdn + ") and a.idn in (" + memoIdn + ") ";


    ArrayList params = new ArrayList();

      ArrayList  outLst = db.execSqlLst(" Memo Info", getSum , params);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);

    while(rs.next()){
    sb.append("<price>");
    sb.append("<avgDis>"+util.nvl(rs.getString("avg_dis"),"0")+"</avgDis>");
    sb.append("<avgRte>"+util.nvl(rs.getString("avg_Rte"),"0")+"</avgRte>");
    sb.append("<Qty>"+util.nvl(rs.getString("qty"),"0")+"</Qty>");
    sb.append("<Cts>"+util.nvl(rs.getString("cts"),"0")+"</Cts>");
    sb.append("<Vlu>"+util.nvl(rs.getString("vlu"),"0")+"</Vlu>");
    sb.append("</price>");
    }
        rs.close();
        pst.close();
    } else {

    sb.append("<price>");
    sb.append("<avgDis>0</avgDis>");
    sb.append("<avgRte>0</avgRte>");
    sb.append("<Qty>0</Qty>");
    sb.append("<Cts>0</Cts>");
    sb.append("<Vlu>0</Vlu>");
    sb.append("</price>");

    }

    res.getWriter().write("<prices>"+sb.toString()+"</prices>");
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
                util.updAccessLog(req,res,"Sale By Billing party", "init");
            }
            }
            return rtnPg;
            }
    
}
