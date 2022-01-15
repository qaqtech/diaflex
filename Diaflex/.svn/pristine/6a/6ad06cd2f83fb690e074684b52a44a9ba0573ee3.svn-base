package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.ByrDao;
import ft.com.dao.MAddr;
import ft.com.dao.PktDtl;

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

public class ConsignmentByBilingParty extends DispatchAction {
    public ConsignmentByBilingParty() {
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
        ConsignmentByBilingPartyForm udf = (ConsignmentByBilingPartyForm) af;
     
        ArrayList byrList = new ArrayList();
        String    getByr  =
            "select distinct a.nme_idn, a.nme byr \n" + 
            "            from nme_v a ,nme_grp b,nme_grp_dtl c, mjan d \n" + 
            "            where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn \n" + 
            "            and b.nme_idn=d.nme_idn and d.stt='IS' and d.typ like '%AP' \n" + 
            "            and b.typ = 'BUYER' and c.flg is null  order by 2";

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
         udf.setInvByrLst(byrList);
        
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_SALE");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("MEMO_SALE");
        allPageDtl.put("MEMO_SALE",pageDtl);
        }
        info.setPageDetails(allPageDtl);
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
        ConsignmentByBilingPartyForm udf = (ConsignmentByBilingPartyForm) af;
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
        ResultSet rs          = null;
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
                "and d.stt='IS' and d.typ like '%AP' and e.stt = 'AP' \n" + 
                " and  ( f.vnm in ("+vnmLst+") or f.tfl3 in ("+vnmLst+")) "+
                "and b.typ = 'BUYER' and c.flg is null ";
                
              ArrayList  outLst = db.execSqlLst("check rel", checkSql, new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs1 = (ResultSet)outLst.get(1);
                if(rs1.next()){
                    cnt++;
                    invByrId=rs1.getInt("nme_idn");
                }
                rs1.close();
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
        }
            int invRelIdn=0;
            String invRelsql = "select nmerel_idn from nmerel where nme_idn =? and dflt_yn = 'Y' ";
            params = new ArrayList();
            params.add(Integer.toString(invByrId));
         ArrayList outLst = db.execSqlLst("invRelsql", invRelsql, params);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs1 = (ResultSet)outLst.get(1);
            while(rs1.next()){
                invRelIdn = rs1.getInt(1);
            }
            rs1.close();
            pst.close();
        String insgtPkt =
            "Insert into gt_srch_rslt (srch_id,pair_id,rln_idn,stk_idn, vnm ,pkt_ty,qty,cts,stt,dsp_stt,flg,quot,fquot,prte,sk1,rap_rte,byr,exh_rte,RCHK)\n" + 
            "select a.idn ,c.nme_idn,c.nmerel_idn,b.idn, b.vnm,b.pkt_ty,a.qty ,b.cts,a.stt,b.stt,c.typ, quot, nvl(fnl_sal, quot),nvl(b.upr, b.cmp), nvl(b.sk1,1), b.rap_rte, byr.get_nm(c.nme_idn),get_xrt(c.cur),c.note_person\n" + 
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
          outLst = db.execSqlLst(" Memo Info", srchQ , new ArrayList());  
           pst = (PreparedStatement)outLst.get(0);
          rs1 = (ResultSet)outLst.get(1);
        while(rs1.next()){
            String nmeidn=util.nvl(rs1.getString("nme_idn"));  
            byrDtl.put(nmeidn+"_BYR", util.nvl(rs1.getString("byr")));  
            byrDtl.put(nmeidn+"_TYP", util.nvl(rs1.getString("typ")));
            byrDtl.put(nmeidn+"_QTY", util.nvl(rs1.getString("qty")));
            byrDtl.put(nmeidn+"_CTS", util.nvl(rs1.getString("cts")));
        }
            rs1.close();
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
        "decode(rap_rte, 1, '', trunc(((fquot/exh_rte/greatest(rap_rte,1))*100)-100,2)) mDis,rchk note_person \n" ;
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
                pkt.setValue("NOTE_PERSON", util.nvl(rs.getString("note_person")));
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
         ArrayList trmList = new ArrayList();
         ArrayList ary = new ArrayList();
         trmList = srchQuery.getTermALL(req,res, invByrId);
         udf.setInvTrmsLst(trmList);
         udf.setValue("invByrTrm",invRelIdn); 
             
        ArrayList byrList = new ArrayList();
        String    getByr  =
            "select nme_idn,  byr.get_nm(nme_idn) byr from mnme a " + " where nme_idn = ? "
            + " or exists (select 1 from nme_grp_dtl c, nme_grp b where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn "
            + " and b.nme_idn = ? and b.typ = 'BUYER' and c.flg is null)  " + " order by 2 ";

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
    ConsignmentByBilingPartyForm udf = (ConsignmentByBilingPartyForm) af;
    HashMap pktMemoMap =(HashMap)session.getAttribute("pktMemoMap");
    ArrayList byrIdnLst =(ArrayList)session.getAttribute("byrIdnLst");
    HashMap byrDtl =(HashMap)session.getAttribute("byrDtl");   
    String   invByrId      = util.nvl((String)udf.getValue("grpIdn"));
    String   invRelId      = util.nvl((String)udf.getValue("invByrTrm"));
    int byrIdnLstsz=byrIdnLst.size();
            String exh_rte = util.nvl((String)udf.getValue("exh_rte"));
    String typ="CS";
    ArrayList salidnLst=new ArrayList();
    ArrayList params = null;
    //    String    pktNmsSl = "";
    //    String    pktNmsRT = "";
            String rtnPktIdn ="";
    int csMemoIdn = 0;
    for(int k=0 ; k < byrIdnLstsz;k++){
    String nmeidn = (String)byrIdnLst.get(k);
    String rlnidn =util.nvl((String)udf.getValue(nmeidn));  
    ArrayList pkts =(ArrayList)pktMemoMap.get(nmeidn);   
  
        if(pkts!=null && pkts.size()>0){
        for (int i = 0; i < pkts.size(); i++) {
            PktDtl pkt     = (PktDtl) pkts.get(i);
            long   lPktIdn = pkt.getPktIdn();
            String memoIdn = pkt.getMemoId();
            
            String lStt    = util.nvl((String) udf.getValue("stt_" + lPktIdn));
            String vnm = pkt.getVnm();
           

            if (lStt.equals("CS")) {
                if (csMemoIdn == 0) {
                    ArrayList ary = new ArrayList();

                    ary.add(invByrId);
                    ary.add(invRelId);
                    ary.add("RC");
                    ary.add("CS");
                    ary.add("NN");
                    ary.add(String.valueOf(memoIdn));
                  
                 
                    ArrayList out = new ArrayList();

                    out.add("I");

                    CallableStatement cst = null;

                    cst = db.execCall(
                        "MKE_HDR ",
                        "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ? , pTyp => ? , pflg => ? , pFrmId => ? , pMemoIdn => ?)",
                        ary, out);
                    csMemoIdn = cst.getInt(ary.size()+1);
                  cst.close();
                  cst=null;
                }
                
                String updPkt = " memo_pkg.app_pkt(pMemoId => ? , pStkIdn => ?, pStt => ? )";
                params = new ArrayList();
                params.add(String.valueOf(memoIdn));
                params.add(Long.toString(lPktIdn));
                params.add("CS");
               int ct = db.execCall("app_pkt",updPkt , params);
                
                String app_memoPkt = "memo_pkg.App_Memo_Pkt(pFrmMemoId => ? , pAppMemoId => ?, pStkIdn => ?, pAppFlg => ? , pStt=> ? , pRelId => ?)";
                params = new ArrayList();
                params.add(String.valueOf(memoIdn));
                params.add(String.valueOf(csMemoIdn));
                params.add(Long.toString(lPktIdn));
                params.add("CS");
                params.add("CS");
                params.add(rlnidn);
                ct = db.execCall("app_memo_pkt",app_memoPkt , params);
                
                String updJanQty = "jan_qty(?) ";

                params = new ArrayList();
                params.add(String.valueOf(memoIdn));

              

                ct = db.execCall("JanQty", updJanQty, params);
            }
            if (lStt.equals("RT")) {
               
               String updPkt = " memo_pkg.rtn_pkt( pMemoId =>?, pStkIdn=> ?)";
                int ct = db.execCall(" App Pkts", updPkt, params);
                if(ct>0)
                    rtnPktIdn = rtnPktIdn+" , "+vnm;
            }

         

        }
        
          
        }
    
    }
            
            if (csMemoIdn > 0) {
                params = new ArrayList();
                params.add(String.valueOf(csMemoIdn));

               int ct = db.execCall("calQuot", "MEMO_PKG.Cal_Fnl_Quot(pIdn => ?)", params);

                
               String  updJanQty = " jan_qty(?) ";
                params    = new ArrayList();
                params.add(Integer.toString(csMemoIdn));
                ct = 0;
                ct = db.execCall("JanQty", updJanQty, params);
                
                ct = db.execCall("update mjan", "update mjan set stt='PND' where idn = ?", params);
                req.setAttribute("SLMSG", "Consignment Memo ID :-"+csMemoIdn);
            }
   if(rtnPktIdn.length()>3)
       req.setAttribute("RTMSG", "Packet Return Successfully :-"+rtnPktIdn);
            finalizeObject(db, util);

    return load(am, af, req, res);
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
      ResultSet rs = (ResultSet)outLst.get(1);
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
                rtnPg=util.checkUserPageRights("",util.getFullURL(req));
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Sale By Billing party", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Sale By Billing party", "init");
            }
            }
            return rtnPg;
            }
}
