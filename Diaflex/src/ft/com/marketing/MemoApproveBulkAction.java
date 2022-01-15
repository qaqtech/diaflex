package ft.com.marketing;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.MailAction;
//import ft.com.SyncOnRap;
import ft.com.contact.NmeFrm;
import ft.com.dao.PktDtl;
import ft.com.dao.TrmsDao;
import ft.com.dao.UIForms;

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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemoApproveBulkAction extends DispatchAction {

    public MemoApproveBulkAction() {
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
                rtnPg=util.checkUserPageRights("marketing/memoBulkReturn.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Approve Memo Return bulk", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Approve Memo Return bulk", "init");
            }
            }
            return rtnPg;
            }
    public ActionForward loadPg(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"Approve Memo Return bulk", "Approve Memo Return bulk load");
        MemoReturnBulkFrm udf = (MemoReturnBulkFrm) af;
            finalizeObject(db, util);
        return am.findForward("view"); 
        }
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
          util.updAccessLog(req,res,"Approve Memo Return bulk", "Approve Memo Return bulk load pkts");
        info.setSlPktList(new ArrayList());
        HashMap memoPktMap = new HashMap();
        ArrayList memoList = new ArrayList();
        MemoReturnBulkFrm udf = (MemoReturnBulkFrm) af;
        ArrayList vwprpLst = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
      
        ArrayList params = null;
        ArrayList pkts    = new ArrayList();
  
        String pktLst = util.nvl((String)udf.getValue("vnmLst")).trim();
       
      
        int count = 0;
        if(pktLst.length()>0){
            HashMap byrDtl =new HashMap();
            pktLst = util.getVnm(pktLst);
            int pnmeIdn =0;
            String sql = "select count(*), nmerel_idn  , a.nme_idn , byr.get_nm(nme_idn) byr,  sum(b.qty) qty, sum(b.cts) cts , a.typ , a.idn , a.exh_rte  from mjan a, jandtl b  , mstk c  where a.idn = b.idn  " + 
             "and b.stt='AP' and a.typ like '%AP' and a.stt='IS' and b.mstk_idn = c.idn and (c.vnm in ("+pktLst+") or c.tfl3  in ("+pktLst+") ) group by nmerel_idn , nme_idn , byr.get_nm(nme_idn) , a.typ , a.idn , a.exh_rte ";
          ArrayList  outLst = db.execSqlLst("check", sql, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                
                int lnmeIdn = rs.getInt("nme_idn");
                if(pnmeIdn==0)
                   pnmeIdn = lnmeIdn;
                if(pnmeIdn!=lnmeIdn){
                    count++;
                    pnmeIdn = lnmeIdn;
                }
                HashMap byrObj = new HashMap();
                byrObj.put("nmeIdn",String.valueOf(rs.getInt("nme_idn")));
                byrObj.put("nmeRleIdn",String.valueOf(rs.getInt("nmerel_idn")));
                byrObj.put("byr",rs.getString("byr"));
                byrObj.put("qty",rs.getString("qty"));
                byrObj.put("cts",rs.getString("cts"));
                byrObj.put("typ",rs.getString("typ"));
                byrDtl.put(rs.getString("idn"), byrObj);
                udf.setNmeIdn(rs.getInt("nme_idn"));
                 udf.setRelIdn(rs.getInt("nmerel_idn"));
                udf.setValue("exh_rte", rs.getString("exh_rte"));        
                 udf.setTyp(rs.getString("typ"));
                      
            }
            rs.close();
            pst.close();
            req.setAttribute("byrDtl", byrDtl);
               
                int pmemoIdn = 0;
            String mprpStr = "";
            String mdlPrpsQ = "Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||\n" + 
            "Upper(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT')) \n" + 
            "str From Rep_Prp Rp Where rp.MDL = ? order by srt " ;
            ArrayList ary = new ArrayList();
            ary.add("MEMO_RTRN");
            outLst = db.execSqlLst("mprp ", mdlPrpsQ , ary);
           pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while(rs.next()) {
            String val = util.nvl((String)rs.getString("str"));
            mprpStr = mprpStr +" "+val;
            }
            rs.close();
            pst.close();
                String getPktData = " with STKDTL as " +
                    " ( Select b.sk1,a.idn,b.cert_no certno , a.mstk_idn,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , st.mprp, DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), b.vnm) vnm , b.tfl3 , nvl(a.fnl_sal, a.quot) memoQuot, a.quot, a.fnl_sal , a.typ "
                    + ", b.cts, nvl(b.upr, b.cmp) rate, b.rap_rte raprte,to_char(trunc(a.cts,2) * b.rap_rte, '99999999990.00') rapVlu, a.stt "
                    + " , trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2) dis, trunc(((nvl(a.fnl_sal, a.quot)/greatest(b.rap_rte,1))*100)-100,2) mDis "
                    + " from stk_dtl st,jandtl a, mstk b , mjan c where st.mstk_idn=b.idn and a.mstk_idn = b.idn and c.idn = a.idn and a.stt='AP' and c.typ like '%AP' and c.stt='IS'  and (b.vnm in ("+pktLst+") or b.tfl3  in ("+pktLst+")) "+
                "and exists (select 1 from rep_prp rp where rp.MDL = 'MEMO_RTRN' and st.mprp = rp.prp)  And st.Grp = 1) " +
                " Select * from stkDtl PIVOT " +
                " ( max(atr) " +
                " for mprp in ( "+mprpStr+" ) ) order by 2,1 " ;
          outLst = db.execSqlLst("pktDate", getPktData,new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                    int memoIdn = rs.getInt("idn");
                    if(pmemoIdn==0)
                        pmemoIdn = memoIdn;
                    if(pmemoIdn != memoIdn){
                        memoPktMap.put(String.valueOf(pmemoIdn), pkts);
                        memoList.add(String.valueOf(pmemoIdn));
                        pkts = new ArrayList();
                        pmemoIdn = memoIdn;
                    }
                    PktDtl pkt    = new PktDtl();
                    long   pktIdn = rs.getLong("mstk_idn");
                    String typ=util.nvl(rs.getString("typ"));
                    pkt.setPktIdn(pktIdn);
                    pkt.setRapRte(util.nvl(rs.getString("raprte")));
                    pkt.setCts(util.nvl(rs.getString("cts")));
                    pkt.setRte(util.nvl(rs.getString("rate")));
                    pkt.setMemoQuot(util.nvl(rs.getString("memoQuot")));
                    pkt.setByrRte(util.nvl(rs.getString("quot")));
                    pkt.setFnlRte(util.nvl(rs.getString("fnl_sal")));
                    pkt.setDis(rs.getString("dis"));
                    pkt.setByrDis(rs.getString("mDis"));
                    pkt.setVnm(util.nvl(rs.getString("vnm")));
                    pkt.setTyp(typ);
                    if(typ.equals("CS"))
                    count++;
                    String lStt = rs.getString("stt");
                    
                    pkt.setStt(lStt);
                    udf.setValue("stt_"+memoIdn+"_"+ pktIdn, lStt);
                        
                    String vnm = util.nvl(rs.getString("vnm"));
                    String tfl3 = util.nvl(rs.getString("tfl3"));
                    pkt.setValue("rapVlu", util.nvl(rs.getString("rapVlu")));
                    if(!pktLst.equals("")){
                        if(pktLst.indexOf(tfl3)!=-1 && !tfl3.equals("")){
                            if(pktLst.indexOf("'")==-1)
                                pktLst =  pktLst.replaceAll(tfl3,"");
                            else
                                pktLst =  pktLst.replaceAll("'"+tfl3+"'", "");
                        } else if(pktLst.indexOf(vnm)!=-1 && !vnm.equals("")){
                            if(pktLst.indexOf("'")==-1)
                                pktLst =  pktLst.replaceAll(vnm,"");
                            else
                                pktLst =  pktLst.replaceAll("'"+vnm+"'", "");
                        }  
                    }
                        
                    for (int v = 0; v < vwprpLst.size(); v++) {
                    String vwPrp = (String)vwprpLst.get(v);
                    String fldName = vwPrp;
                    if(vwPrp.toUpperCase().equals("H&A"))
                    fldName = "H_A";
                    
                    if(vwPrp.toUpperCase().equals("COMMENT"))
                    fldName = "COM1";
                    
                    if(vwPrp.toUpperCase().equals("REMARKS"))
                    fldName = "REM1";
                    
                    if(vwPrp.toUpperCase().equals("COL-DESC"))
                    fldName = "COL_DESC";
                    
                    if(vwPrp.toUpperCase().equals("COL-SHADE"))
                    fldName = "COL_SHADE";
                    
                    if(vwPrp.toUpperCase().equals("FL-SHADE"))
                    fldName = "FL_SHADE";
                    
                    if(vwPrp.toUpperCase().equals("STK-CTG"))
                    fldName = "STK_CTG";
                    
                    if(vwPrp.toUpperCase().equals("STK-CODE"))
                    fldName = "STK_CODE";
                    
                    if(vwPrp.toUpperCase().equals("SUBSIZE"))
                    fldName = "SUBSIZE1";
                    
                    if(vwPrp.toUpperCase().equals("SIZE"))
                    fldName = "SIZE1";
                    
                    if(vwPrp.toUpperCase().equals("MIX_SIZE"))
                    fldName = "MIX_SIZE1";
                    
                    if(vwPrp.toUpperCase().equals("STK-CTG"))
                    fldName = "STK_CTG";
                    
                    if(vwPrp.toUpperCase().equals("CRN-OP"))
                    fldName = "CRN_OP";
                    
                    if(vwPrp.toUpperCase().equals("CRTWT"))
                    fldName = "cts";
                    
                    if(vwPrp.toUpperCase().equals("RAP_DIS"))
                    fldName = "r_dis";
                    
                    if(vwPrp.toUpperCase().equals("RTE"))
                    fldName = "rate";
                    
                    if (vwPrp.toUpperCase().equals("RAP_RTE"))
                    fldName = "raprte";
                        if (vwPrp.toUpperCase().equals("CERT NO."))
                        fldName = "certno";
                        if(vwPrp.toUpperCase().equals("MEM_COMMENT"))
                        fldName = "MEM_COM1";
                    String fldVal = util.nvl((String)rs.getString(fldName));
                    
                    pkt.setValue(vwPrp, fldVal);
                    }
                    pkts.add(pkt);
                }
                rs.close();
                if(pmemoIdn!=0){
                    memoPktMap.put(String.valueOf(pmemoIdn), pkts);
                    memoList.add(String.valueOf(pmemoIdn));
                }
                
            session.setAttribute("pktMemoMap", memoPktMap);
            session.setAttribute("memoList", memoList);
            util.noteperson();
            req.setAttribute("view", "Y");
                if(!pktLst.equals("") && !pktLst.equals(",")){
                    pktLst = util.pktNotFound(pktLst);
                    req.setAttribute("vnmNotFnd", pktLst);
                }
          util.updAccessLog(req,res,"Approve Memo Return bulk", "Approve Memo Return bulk load pkts done memo size "+memoList.size());
            req.setAttribute("byrcont", count);
        }
            finalizeObject(db, util);
      return am.findForward("view");
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
          util.updAccessLog(req,res,"Approve Memo Return bulk", "Approve Memo Return bulk save");
        ArrayList params = null;
        MemoReturnBulkFrm udf = (MemoReturnBulkFrm) af;
        String rtnPktIdn = "";
        int ct = 0;
        String exh_rte = util.nvl((String)udf.getValue("exh_rte"));
        HashMap dbinfo = info.getDmbsInfoLst();
        String rapsync = util.nvl((String)dbinfo.get("RAPSYNC"));
        String cnt = util.nvl((String)dbinfo.get("CNT"));
        ArrayList memoList = (ArrayList)session.getAttribute("memoList");
        HashMap memoPkt = (HashMap)session.getAttribute("pktMemoMap");
        String globalrmk = util.nvl((String)udf.getValue("rmk"));
        String  updPkt = " memo_pkg.rtn_pkt( pMemoId =>?, pStkIdn=> ? , pRem => ?)";
        for(int k=0 ; k < memoList.size();k++){
          String memoIdn = (String)memoList.get(k);
           ArrayList pkts =(ArrayList)memoPkt.get(memoIdn);
            for (int i = 0; i < pkts.size(); i++) {
                PktDtl pkt     = (PktDtl) pkts.get(i);
                String vnm = pkt.getVnm();
                long   lPktIdn = pkt.getPktIdn();
                String lStt    = util.nvl((String) udf.getValue("stt_"+memoIdn+"_"+ lPktIdn));
                if (lStt.equals("RT")) {
                    params = new ArrayList();
                    params.add(memoIdn);
                    params.add(Long.toString(lPktIdn));
                    String rmk = util.nvl((String)udf.getValue("rmk_"+Long.toString(lPktIdn)));
                    if(rmk.equals(""))
                    rmk=globalrmk;
                    params.add(rmk);
                    ct = db.execCall(" App Pkts", updPkt, params);
                    rtnPktIdn = rtnPktIdn+" , "+vnm;
                }
                
            }
            String updJanQty = " jan_qty(?) ";
            params = new ArrayList();
            params.add(String.valueOf(memoIdn));
            ct = db.execCall("JanQty", updJanQty, params);

        }

           rtnPktIdn=rtnPktIdn.replaceFirst("\\,", "");
           req.setAttribute("rtnPtk", "Packets get return : "+rtnPktIdn ); 
           util.updAccessLog(req,res,"Approve Memo Return bulk", "Approve Memo Return bulk done");
           finalizeObject(db, util);
           return am.findForward("view");
    }
    }

    public ActionForward merge(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        MemoReturnBulkFrm udf = (MemoReturnBulkFrm) af;

        ArrayList    ary      = null;
        String    nmeIdn   = req.getParameter("nmeIdn");
        ArrayList trmList  = new ArrayList();
        String    sqlMerge = "select distinct a.nmerel_idn, b.trm||' '||b.rln terms from "
                             + "mjan a, cus_rel_v b where 1 = 1 " + "and a.nmerel_idn = b.rel_idn "
                             + "and a.nme_idn = ? and a.stt = 'IS' and a.typ in('WA')";

        ary = new ArrayList();
        ary.add(nmeIdn);

        ResultSet rs = db.execSql("trms", sqlMerge, ary);

        while (rs.next()) {
            TrmsDao trms = new TrmsDao();

            trms.setRelId(rs.getInt("nmerel_idn"));
            trms.setTrmDtl(rs.getString("terms"));
            trmList.add(trms);
        }
        rs.close();
        udf.setTrmsLst(trmList);
        udf.setValue("nmeIdn", nmeIdn);
            finalizeObject(db, util);
        return am.findForward("merge");
        }
    }

    public ActionForward mergeMemo(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        MemoReturnBulkFrm udf = (MemoReturnBulkFrm) af;

        ArrayList      ary     = new ArrayList();
        String      memoIds = "";
        String      nmeIdn  = (String) udf.getValue("nmeIdn");
        Enumeration reqNme  = req.getParameterNames();

        while (reqNme.hasMoreElements()) {
            String paramNm = (String) reqNme.nextElement();

            if (paramNm.indexOf("cb_memo") > -1) {
                String val = req.getParameter(paramNm);

                if (memoIds.equals("")) {
                    memoIds = val;
                } else {
                    memoIds = memoIds + "," + val;
                }
            }
        }

        ary.add(nmeIdn);
        ary.add(String.valueOf(memoIds));

        ArrayList out = new ArrayList();

        out.add("I");

        CallableStatement cst = null;

        cst = db.execCall("MKE_HDR ", "memo_pkg.Merge_Memo(pNmeIdn => ?, pIds => ?,  pMemoIdn => ?)", ary, out);

        int MemoIdn = cst.getInt(3);
          cst.close();
          cst=null;
            finalizeObject(db, util);
        return am.findForward("pndPage");
        }
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
