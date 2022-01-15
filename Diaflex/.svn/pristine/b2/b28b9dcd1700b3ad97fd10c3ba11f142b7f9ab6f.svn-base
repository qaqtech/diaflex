package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MemoSrchAction  extends DispatchAction {
    public MemoSrchAction() {
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
        MemoSrchForm udf = (MemoSrchForm)af;
        udf.resetALL();
          util.updAccessLog(req,res,"Memo Search", "Memo Search load");
        SearchQuery srchQuery = new SearchQuery();
//        ArrayList byrList = srchQuery.getPartyList(req ,res);
//        ArrayList brokerList = srchQuery.getBrokerList(req,res);
//        udf.setByrList(byrList);
        ArrayList brokerList = srchQuery.getBrokerList(req,res);
        udf.setBrokerList(brokerList);
        info.setIsMix("");
        ArrayList memoList = srchQuery.getMemoType(req,res);
        udf.setValue("memoList", memoList);
//        udf.setMemoTypList(memoList);
        ArrayList byrCbList = srchQuery.getBuyerCabin(req, res);
        if(byrCbList !=null && byrCbList.size()>0)
        udf.setValue("ByrCbList", byrCbList);
        util.noteperson();
        String dtePrpQ="select to_char(trunc(sysdate-0), 'dd-mm-rrrr') frmdte,to_char(trunc(sysdate), 'dd-mm-rrrr') todte from dual";
          ArrayList  outLst = db.execSqlLst("Date calc", dtePrpQ,new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        while (rs.next()) { 
        udf.setValue("memoDte",(util.nvl(rs.getString("frmdte"))));
        udf.setValue("memotoDte",(util.nvl(rs.getString("todte"))));
        } 
        rs.close();
        pst.close();
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_SRCH");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("MEMO_SRCH");
        allPageDtl.put("MEMO_SRCH",pageDtl);
        }
        info.setPageDetails(allPageDtl);
          util.updAccessLog(req,res,"Memo Search", "Memo Search load done");
        return am.findForward("load");
        }
    }
    
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        MemoSrchForm udf = (MemoSrchForm)af;
            SearchQuery srchQuery = new SearchQuery();
          util.updAccessLog(req,res,"Memo Search", "Memo Search Fetch");
        ArrayList memodtlList = new ArrayList();
        ArrayList ary = new ArrayList();
        String memoId = util.nvl((String)udf.getValue("memoId"));
        String vnm = util.nvl((String)udf.getValue("vnm"));
      //Start -- ticket 3932 - changed for report no.
        String refNo = util.nvl((String)udf.getValue("refNo"));
      //End -- ticket 3932 - changed for report no.
        String salCo = util.nvl((String)udf.getValue("salCo"));
        String byr = util.nvl((String)udf.getNmeID());
        String stt = util.nvl((String)udf.getValue("stt"));
        String memostt = util.nvl((String)udf.getValue("memostt"));
        String memoDte = util.nvl((String)udf.getValue("memoDte"));
        //Start -- modified MemoCreateDate, added memo expiray date and memo expiray days search for ticket 3932
        String memotoDte = util.nvl((String)udf.getValue("memotoDte"));
        String memoexpDte = util.nvl((String)udf.getValue("memoexpDte"));
        String memoexptoDte = util.nvl((String)udf.getValue("memoexptoDte"));
        String memoexpDay = util.nvl((String)udf.getValue("memoexpDay"));
        String memoexptoDay = util.nvl((String)udf.getValue("memoexptoDay"));
        String cabin=util.nvl((String)udf.getValue("cabin"));
        String pktty=util.nvl((String)udf.getValue("pktty"));
        String dfltPktTyp = util.nvl((String)udf.getValue("dfltPktTyp"));
        String typ=util.nvl((String)udf.getValue("typ"));
        String noteperson = util.nvl((String)udf.getValue("noteperson")).trim();
        String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
        String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
        String usrFlg=util.nvl((String)info.getUsrFlg());
        ArrayList rolenmLst=(ArrayList)info.getRoleLst();
        String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
        udf.resetALL();
        //End -- modified MemoCreateDate, added memo expiray date and memo expiray days search for ticket 3932
        if(memostt.equals(""))
            memostt="IS";  
        String memoSrchconQ="";
        String noteQ="";
        String client = (String)info.getDmbsInfoLst().get("CNT");
        if(client.equals("ag"))
        noteQ=",a.note_person";
            
        if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
            }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
            memoSrchconQ +=" and nvl(d.grp_nme_idn,0) =? "; 
            ary.add(dfgrpnmeidn);
        } 
        if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
            if(util.nvl((String)info.getDmbsInfoLst().get("EMP_LOCK")).equals("Y")){
            memoSrchconQ += " and (d.emp_idn= ? or d.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
            ary.add(dfNmeIdn);
            ary.add(dfNmeIdn);
            }
        }
        String memoSrch = "select to_number(to_char(a.dte, 'rrrrmmdd')) srt_dte,to_char(a.dte,'dd-mm-rrrr') dte,a.idn,a.byr_cabin,d.nme byr,e.nme emp,nr.dtls,a.rmk"+noteQ+",a.typ  , a.stt,a.exp_dys,count(distinct a.idn) cnt,sum(nvl(b.qty,a.qty)) qty,\n" + 
        "trunc(sum(trunc(b.cts,2)),2) cts, trunc(sum(trunc(b.cts,2)*nvl(b.fnl_sal, b.quot)),2) vlu\n" + 
        ", sum(decode(b.stt, t.dtl_stt, 1, 0)) on_memo_qty \n" + 
        ", sum(decode(b.stt, t.dtl_stt, trunc(b.cts,2), 0)) on_memo_cts \n" + 
        ", sum(decode(b.stt, t.dtl_stt, trunc(b.cts,2)*nvl(b.fnl_sal, b.quot), 0)) on_memo_amt\n"+
        "from mjan a, jandtl b,mstk c,nme_v d,emp_v e,nme_rel_v nr,memo_typ t \n" + 
        "where a.idn = b.idn and b.mstk_idn = c.idn and a.nme_idn = d.nme_idn and nvl(d.emp_idn,0)=nvl(e.nme_idn,0) and a.nme_idn=nr.nme_idn and a.nmerel_idn=nr.nmerel_idn ";
        
//          String  memoSrch = " select distinct to_char(c.dte,'dd-mm-rrrr') dte , c.idn , byr.get_nm(c.nme_idn) byr,  "+
//                "  c.typ  , c.stt , c.qty , to_char(c.cts,'99999990.00') cts , to_char(c.vlu,'9999999990.00') vlu , c.exp_dys "+
//               " from jandtl a, mstk b , mjan c , mnme d  where a.mstk_idn = b.idn and a.idn = c.idn and c.nme_idn = d.nme_idn  ";
            
         //Start -- ticket 3932 - changed for report no.
            if(!vnm.equals("")) {
              vnm = util.getVnm(vnm);
              memoSrchconQ = memoSrchconQ + " and ( c.vnm in ("+vnm+") or c.tfl3 in ("+vnm+") or c.cert_no in ("+vnm+") )";
            }
            
            if(!refNo.equals("")) {
              refNo = util.getVnm(refNo);
              memoSrchconQ = memoSrchconQ +" and c.cert_no in ("+refNo+") ";
            }
         //End -- ticket 3932 - changed for report no.
       
        if(!memoId.equals("")){
            memoSrchconQ = memoSrchconQ +" and a.idn = ? ";
            ary.add(memoId);
        }
       
        if(!salCo.equals("0")){
            memoSrchconQ = memoSrchconQ + " and ( a.mbrk1_idn = ? or a.mbrk2_idn = ? or a.mbrk3_idn = ?) ";
            ary.add(salCo);
            ary.add(salCo);
            ary.add(salCo);
        }
        if(!byr.equals("0") && !byr.equals("")){
            memoSrchconQ = memoSrchconQ + " and a.nme_idn = ? ";
            ary.add(byr);
        }
            String strStt="";
        if(!stt.equals("")&& !stt.equals("0")){
           
            strStt=" and b.stt = '"+stt+"'";
            
        }
            
            if(!noteperson.equals("")){
                memoSrchconQ = memoSrchconQ + " and a.note_person='"+noteperson+"' ";
            }
        if(!typ.equals("")){
            memoSrchconQ = memoSrchconQ + " and a.typ = ? ";
            ary.add(typ);
        }
        
      //Start -- ticket 3932
        if(!memoDte.equals("")){
            memoSrchconQ = memoSrchconQ + " and trunc(a.dte) between trunc(to_date('"+memoDte+"','dd-mm-rrrr')) and trunc(to_date('"+memotoDte+"','dd-mm-rrrr')) ";
        }
        
        if(!memoexpDte.equals("")){
            memoSrchconQ = memoSrchconQ + " and trunc(a.exp_dte) between trunc(to_date('"+memoexpDte+"','dd-mm-rrrr')) and trunc(to_date('"+memoexptoDte+"','dd-mm-rrrr')) ";
        }
        
        if(!memoexpDay.equals("")){
            memoSrchconQ = memoSrchconQ + " and trunc(a.exp_dys) between "+memoexpDay+" and "+memoexptoDay+" ";
        }
        if(!cabin.equals("")){
            memoSrchconQ = memoSrchconQ + " and a.byr_cabin='"+cabin+"'";
        }
        if(!memostt.equals("") && !memostt.equals("ALL")){
            memoSrchconQ = memoSrchconQ + " and a.stt=?";
            ary.add(memostt);
            if(memostt.equals("IS"))
            memoSrchconQ = memoSrchconQ + " and a.qty > 0";
        }
        if(!pktty.equals("")){
                memoSrchconQ = memoSrchconQ + " and c.pkt_ty=? ";
                ary.add(pktty);
        }
            if(!dfltPktTyp.equals("")){
                    memoSrchconQ = memoSrchconQ + " and c.pkt_ty=? ";
                    ary.add(dfltPktTyp);
            }
      //End -- ticket 3932
        
        memoSrch = memoSrch+memoSrchconQ+ " and a.typ = t.typ "+strStt+"  group by to_number(to_char(a.dte, 'rrrrmmdd')),to_char(a.dte,'dd-mm-rrrr'),a.idn,a.byr_cabin,d.nme,e.nme,nr.dtls,a.rmk"+noteQ+",a.typ,a.stt,a.exp_dys order by 1 desc,d.nme  ";
          ArrayList  outLst = db.execSqlLst("memoSrch", memoSrch , ary );
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        int cnt = 0;
        while(rs.next()){
            cnt++;
            HashMap memoMap = new HashMap();
            memoMap.put("sr", String.valueOf(cnt));
            memoMap.put("dte",util.nvl(rs.getString("dte")));
            memoMap.put("memo", util.nvl(rs.getString("idn")));
            memoMap.put("memoidn", util.nvl(rs.getString("idn")));
            memoMap.put("byr_cabin", util.nvl(rs.getString("byr_cabin")));
            memoMap.put("emp",util.nvl(rs.getString("emp")));
            memoMap.put("dtls",util.nvl(rs.getString("dtls")));
            memoMap.put("rmk",util.nvl(rs.getString("rmk")));
            memoMap.put("byr",util.nvl(rs.getString("byr")));
            memoMap.put("typ",util.nvl(rs.getString("typ")));
            memoMap.put("stt",util.nvl(rs.getString("stt")));
            memoMap.put("qty",util.nvl(rs.getString("qty")));
            memoMap.put("cts",util.nvl(rs.getString("cts")));
            memoMap.put("vlu",util.nvl(rs.getString("vlu")));
            memoMap.put("on_memo_qty",util.nvl(rs.getString("on_memo_qty")));
            memoMap.put("on_memo_cts",util.nvl(rs.getString("on_memo_cts")));
            memoMap.put("on_memo_amt",util.nvl(rs.getString("on_memo_amt")));
            memoMap.put("exp_dys",util.nvl(rs.getString("exp_dys")));
            memoMap.put("pktdtl", "pktdtl");
            memoMap.put("reqUrl", info.getReqUrl());
            if(client.equals("ag"))
            memoMap.put("note_person", util.nvl(rs.getString("note_person")));
            memodtlList.add(memoMap);
        }
        rs.close();
        pst.close();
        ArrayList byrLst=new ArrayList();
        HashMap byrDataDtl=new HashMap();
        ArrayList params=new ArrayList();
        String summaryQ="select d.nme byr,e.nme emp, decode(b.stt,'RT','RT','APRT','RT','IS','IS','AP') stt,count(distinct a.idn) cnt,sum(nvl(b.qty,a.qty)) qty,\n" + 
        "trunc(sum(trunc(b.cts,2)),2) cts, trunc(sum(trunc(b.cts,2)*nvl(b.fnl_sal, b.quot)),2) vlu\n" + 
        "from mjan a, jandtl b,mstk c,nme_v d,emp_v e \n" + 
        "where a.idn = b.idn and b.mstk_idn = c.idn \n" + 
        "and a.nme_idn = d.nme_idn and nvl(d.emp_idn,0)=nvl(e.nme_idn,0)  \n" + memoSrchconQ+
        "group by d.nme,e.nme,decode(b.stt,'RT','RT','APRT','RT','IS','IS','AP') \n" + 
        "union\n" + 
        "select d.nme byr,e.nme, 'TTL' stt,count(distinct a.idn) cnt,sum(nvl(b.qty,a.qty)) qty,\n" + 
        "trunc(sum(trunc(b.cts,2)),2) cts, trunc(sum(trunc(b.cts,2)*nvl(b.fnl_sal, b.quot)),2) vlu\n" + 
        "from mjan a, jandtl b,mstk c,nme_v d,emp_v e \n" + 
        "where a.idn = b.idn and b.mstk_idn = c.idn\n" + 
        "and a.nme_idn = d.nme_idn and nvl(d.emp_idn,0)=nvl(e.nme_idn,0)\n" + memoSrchconQ+
        "group by d.nme,e.nme\n" + 
        "order by 1";
        params.addAll(ary);
        params.addAll(ary);
         outLst = db.execSqlLst("memoSrch", summaryQ , params);
           pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String byrNm=util.nvl((String)rs.getString("byr"));
            String st=util.nvl((String)rs.getString("stt"));
            byrDataDtl.put(byrNm+"_"+st+"_CNT", util.nvl((String)rs.getString("cnt")));
            byrDataDtl.put(byrNm+"_"+st+"_QTY", util.nvl((String)rs.getString("qty")));
            byrDataDtl.put(byrNm+"_"+st+"_CTS", util.nvl((String)rs.getString("cts")));
            byrDataDtl.put(byrNm+"_"+st+"_VLU", util.nvl((String)rs.getString("vlu")));
            byrDataDtl.put(byrNm, util.nvl((String)rs.getString("emp")));
            if(!byrLst.contains(byrNm))
            byrLst.add(byrNm);   
        }
            rs.close();
            pst.close();
            
            udf.setValue("memoId", memoId);
            udf.setValue("vnm", vnm.replaceAll("'", ""));
            udf.setValue("refNo", refNo);
            udf.setValue("salCo", salCo);
            udf.setValue("stt", stt);
            udf.setValue("memostt", memostt);
            udf.setValue("memoDte", memoDte);
            udf.setValue("memotoDte", memotoDte);
            udf.setValue("memoexpDte", memoexpDte);
            udf.setValue("memoexptoDte", memoexptoDte);
            udf.setValue("memoexpDay", memoexpDay);
            udf.setValue("memoexptoDay", memoexptoDay);
            udf.setValue("cabin", cabin);
            udf.setValue("pktty", pktty);
            udf.setValue("typ", typ);
            udf.setValue("noteperson", noteperson);
        req.setAttribute("byrLst", byrLst); 
        req.setAttribute("byrDataDtl", byrDataDtl); 
        ArrayList memoList = srchQuery.getMemoType(req,res);
        udf.setValue("memoList", memoList);
        ArrayList byrCbList = srchQuery.getBuyerCabin(req, res);
        if(byrCbList !=null && byrCbList.size()>0)
        udf.setValue("ByrCbList", byrCbList);
        udf.setPktDtlList(memodtlList);
        ArrayList brokerList = srchQuery.getBrokerList(req,res);
        udf.setBrokerList(brokerList);
        req.setAttribute("view", "Y");
          util.updAccessLog(req,res,"Memo Search", "Memo Search Fetch Done Size "+memodtlList.size());
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
          util.updAccessLog(req,res,"Memo Search", "Memo Search load pkts");
          
        String memoId=util.nvl(req.getParameter("memoId"));
        ArrayList vwPrpLst = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
        ArrayList pktList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        ArrayList params = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        int sr=1;
        itemHdr.add("Sr");
        itemHdr.add("Memo Id");
        itemHdr.add("Memo Status");
        itemHdr.add("PacketCode");
        int ct = db.execUpd(" del gt", "delete from gt_srch_rslt", new ArrayList());
        String insgtPkt =
            "Insert into gt_srch_rslt (srch_id,pair_id,rln_idn,stk_idn, vnm ,pkt_ty,qty,cts,stt,flg,quot,fquot,prte,sk1,rap_rte,byr,exh_rte,rmk)\n" + 
            "select a.idn ,c.nme_idn,c.nmerel_idn,b.idn, b.vnm,b.pkt_ty,a.qty ,b.cts,a.stt,c.typ, quot, nvl(fnl_sal, quot),nvl(b.upr, b.cmp), nvl(b.sk1,1), b.rap_rte, byr.get_nm(c.nme_idn),get_xrt(c.cur),c.rmk\n" + 
            "from jandtl a, mstk b,mjan c where a.mstk_idn = b.idn and a.idn=c.idn and a.idn in (" + memoId + ") ";
        
        ct = db.execUpd(" ins scan", insgtPkt, new ArrayList());
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        params.add("MEMO_RTRN");
        ct = db.execCall(" Srch Prp ", pktPrp, params);
        
        String srchQ="select pair_id nme_idn,qty,cts,srch_id idn,stk_idn,vnm,quot,fquot,prte rte,sk1,rap_rte,stt,rmk,\n" + 
        "decode(rap_rte, 1, '', trunc(((prte/greatest(rap_rte,1))*100)-100,2)) dis,\n" + 
        "decode(rap_rte, 1, '', trunc(((fquot/exh_rte/greatest(rap_rte,1))*100)-100,2)) mDis \n" ;
            for (int i = 0; i < vwPrpLst.size(); i++) {
            String lprp=(String)vwPrpLst.get(i);
                String fld = "prp_";
                int j = i + 1;
                if (j < 10)
                    fld += "00" + j;
                else if (j < 100)
                    fld += "0" + j;
                else if (j > 100)
                    fld += j;

                srchQ += ", " + fld;
                if(prpDspBlocked.contains(lprp)){
                }else{
                itemHdr.add(lprp);
            }}
        String rsltQ = srchQ +" from gt_srch_rslt\n" + 
        "order by byr,srch_id,sk1\n";
          ArrayList  outLst = db.execSqlLst(" Memo Info", rsltQ , new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
            try {
                while(rs.next()) {

                    HashMap pktPrpMap = new HashMap();
                    pktPrpMap.put("Sr", String.valueOf(sr++));
                    pktPrpMap.put("Memo Id", util.nvl(rs.getString("idn")));
                    String vnm = util.nvl(rs.getString("vnm"));
                    pktPrpMap.put("PacketCode",vnm);
                    pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                    pktPrpMap.put("RapRte",util.nvl(rs.getString("rap_rte")));
                    pktPrpMap.put("Quot",util.nvl(rs.getString("fquot")));
                    pktPrpMap.put("ByrDis",util.nvl(rs.getString("mDis")));
                    pktPrpMap.put("RTE",util.nvl(rs.getString("rte")));
                    pktPrpMap.put("Dis",util.nvl(rs.getString("dis")));
                    pktPrpMap.put("Remark",util.nvl(rs.getString("rmk")));
                    pktPrpMap.put("Memo Status",util.nvl(rs.getString("stt")));
                    for(int j=0; j < vwPrpLst.size(); j++){
                         String prp = (String)vwPrpLst.get(j);
                          
                          String fld="prp_";
                          if(j < 9)
                                  fld="prp_00"+(j+1);
                          else    
                                  fld="prp_0"+(j+1);
                          
                          String val = util.nvl(rs.getString(fld)) ;
                         
                            
                            pktPrpMap.put(prp, val);
                             }
                                  
                        pktList.add(pktPrpMap);
                    }
                rs.close();
                pst.close();
            } catch (SQLException sqle) {

                // TODO: Add catch code
                sqle.printStackTrace();
            }
        itemHdr.add("RapRte");
        itemHdr.add("Dis");
        itemHdr.add("RTE");
        itemHdr.add("ByrDis");
        itemHdr.add("Quot");
            itemHdr.add("Remark");
            session.setAttribute("pktList", pktList);
            session.setAttribute("itemHdr",itemHdr);
            req.setAttribute("EXCL","Y");
          util.updAccessLog(req,res,"Memo Search", "Memo Search load pkts size "+pktList.size());
            return am.findForward("pktDtl");
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
                util.updAccessLog(req,res,"Memo Search", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Memo Search", "init");
            }
            }
            return rtnPg;
            }
}
