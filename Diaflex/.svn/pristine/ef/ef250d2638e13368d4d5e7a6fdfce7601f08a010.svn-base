package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.PktDtl;
import ft.com.dao.TrmsDao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MemoReturnTest {

   
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

            }
            }
            return rtnPg;
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
        info.setSlPktList(new ArrayList());
        MemoReturnFrm udf = (MemoReturnFrm) af;

      
        int       memoIdn = 0;
        String    view    = "NORMAL";
        ArrayList pkts    = new ArrayList();
        String    pnd     = req.getParameter("pnd");
        ArrayList ary = new ArrayList();
        if (pnd != null) {
            memoIdn = Integer.parseInt(req.getParameter("memoId"));
            udf.setMemoIdn(memoIdn);
        } else {
            memoIdn = udf.getMemoIdn();
            view    = udf.getView();
        }

        ArrayList params = null;
        String pktLst = util.nvl((String) udf.getValue("vnmLst"));

        String memoStr = "";
        
       
        if (pktLst.length() > 3) {
              
            if((util.nvl(udf.getSubmit()).equalsIgnoreCase("View All")) && (pktLst.indexOf(",") == -1)){
                params = new ArrayList();
                params.add(pktLst);
                params.add("IS");
                ArrayList out = new ArrayList();
                out.add("I");
                CallableStatement cst = db.execCall("findMemo","memo_pkg.find_ref_idn(pVnm => ?, pTyp =>? , pIdn => ?)", params ,out);
                memoIdn = cst.getInt(3);
                udf.setMemoIdn(memoIdn);
                req.setAttribute("lMemoIdn", String.valueOf(memoIdn));
                req.setAttribute("viewAll", "yes");
              cst.close();
              cst=null;
                /*
                ActionForward forward = am.findForward( "rtrn" );  
                String path = util.nvl(forward.getPath());
                
                util.SOP(" Rtrn Path : "+ path);
                //StringBuffer path = new StringBuffer( forward.getPath() );  
                String urlParams = "memoId="+memoIdn+"&view=Y&pnd=Y";
                
                if(path.indexOf("?") >= 0)  
                  path += "&"+urlParams;
                else
                  path += "?"+urlParams;  
                return new ActionForward( path);  
                */
            } else if(pktLst.indexOf(",") > -1) {
            pktLst = pktLst.replaceAll(",", "','");
            pktLst = "'" + pktLst + "'";
            memoStr = " and (b.vnm in (" + pktLst + ") or b.tfl3 in ("+ pktLst + "))";
            req.setAttribute("lMemoIdn", String.valueOf(memoIdn));
            req.setAttribute("viewAll", "no");
            }else{
                pktLst = pktLst.replaceAll(",", "','");
                pktLst = "'" + pktLst + "'";
                memoStr = " and (b.vnm in (" + pktLst + ") or b.idn in ("+ pktLst + "))  ";
               
            }
               
        }
        String getMemoInfo =
            " select nme_idn, nmerel_idn, byr.get_nm(nme_idn) byr, typ, qty, cts, vlu, to_char(dte, 'dd-Mon-rrrr HH24:mi:ss') dte "
            + " from mjan where idn = ? and stt <> 'RT' and typ in ('I', 'E', 'WH','WA','K','O','BID') ";

        ary = new ArrayList();
        ary.add(String.valueOf(memoIdn));

        ResultSet mrs = db.execSql(" Memo Info", getMemoInfo, ary);

        if (mrs.next()) {
            udf.setNmeIdn(mrs.getInt("nme_idn"));
            udf.setRelIdn(mrs.getInt("nmerel_idn"));
            udf.setByr(mrs.getString("byr"));
            udf.setTyp(mrs.getString("typ"));
            udf.setQty(mrs.getString("qty"));
            udf.setCts(mrs.getString("cts"));
            udf.setVlu(mrs.getString("vlu"));
            udf.setDte(mrs.getString("dte"));
            udf.setOldMemoIdn(memoIdn);
            if (view.equalsIgnoreCase("Normal")) {
                String getPktData =
                    " select mstk_idn, b.vnm , nvl(fnl_sal, quot) memoQuot, quot, fnl_sal"
                    + ", b.cts, nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte, a.stt "
                    + " , trunc(100 - ((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100),2) dis, trunc(100 - ((nvl(fnl_sal, quot)/greatest(b.rap_rte,1))*100),2) mDis "
                    + " from jandtl a, mstk b where a.mstk_idn = b.idn and a.stt = 'IS'  ";
               
              
              
                 getPktData = getPktData+" "+memoStr+" and a.idn = ? order by a.srl, b.sk1 ";
                  params = new ArrayList();
                  params.add(String.valueOf(memoIdn));
                
                

                // " and decode(typ, 'I','IS', 'E', 'IS', 'WH', 'IS', 'AP') = stt ";
              

                ResultSet rs = db.execSql(" memo pkts", getPktData, params);

                while (rs.next()) {
                    PktDtl pkt    = new PktDtl();
                    long   pktIdn = rs.getLong("mstk_idn");

                    pkt.setPktIdn(pktIdn);
                    pkt.setRapRte(util.nvl(rs.getString("rap_rte")));
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

                    String getPktPrp =
                        " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                        + " from stk_dtl a, mprp b, rep_prp c "
                        + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'MEMO_RTRN' and a.mstk_idn = ? "
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
            }
        }
        mrs.close();
        udf.setPkts(pkts);
        info.setValue(memoIdn + "_PKTS", pkts);
       
        info.setValue("RTRN_PKTS", pkts);
        return am.findForward(view);
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

        MemoReturnFrm udf = (MemoReturnFrm) af;

        ArrayList pkts       = new ArrayList();
        int       memoIdn    = udf.getOldMemoIdn();
        int       appMemoIdn = 0;
        String    memoTyp    = "",
                  lFlg       = "IH";

      

        pkts = (ArrayList) info.getValue("RTRN_PKTS");
            //(ArrayList) info.getValue(memoIdn + "_PKTS");    // udf.getPkts();

        for (int i = 0; i < pkts.size(); i++) {
            PktDtl pkt     = (PktDtl) pkts.get(i);
            long   lPktIdn = pkt.getPktIdn();
            String lStt    = util.nvl((String) udf.getValue("stt_" + lPktIdn));

           

            String updPkt = "";

            params = new ArrayList();
            params.add(String.valueOf(memoIdn));
            params.add(Long.toString(lPktIdn));

            if (lStt.equals("AP")) {
                if (appMemoIdn == 0) {
                    memoTyp = udf.getTyp().substring(0, 1) + "AP";

                    if (memoTyp.equals("WAP")) {
                        lFlg = "WEB";
                    }

                    ArrayList ary = new ArrayList();

                    ary.add(Integer.toString(udf.getNmeIdn()));
                    ary.add(Integer.toString(udf.getRelIdn()));
                    ary.add("IS");
                    ary.add(memoTyp);
                    ary.add("NN");
                    ary.add(String.valueOf(memoIdn));

                    ArrayList out = new ArrayList();

                    out.add("I");

                    CallableStatement cst = null;

                    cst = db.execCall(
                        "MKE_HDR ",
                        "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ? , pTyp => ? , pflg => ? , pFrmId => ?, pMemoIdn => ?)",
                        ary, out);
                    appMemoIdn = cst.getInt(7);
                  cst.close();
                  cst=null;
                }

                updPkt = " memo_pkg.app_pkt(?, ?)";
            }

            if (lStt.equals("RT")) {
                
                updPkt = " memo_pkg.rtn_pkt(?, ?)";
            }

            int ct = 0;

            if (updPkt.length() > 0) {
                ct = db.execCall(" App Pkts", updPkt, params);
            }

            if (lStt.equals("RT")) {
                String pndgAlc = "memo_pkg.pndg_alc(?, ?)";
                ArrayList ary     = new ArrayList();

                ary.add(String.valueOf(memoIdn));
                ary.add(Long.toString(lPktIdn));
                ct = db.execCall("pngd", pndgAlc, ary);
            }

            if ((lStt.equals("AP")) && (appMemoIdn > 0)) {
                String app_memoPkt = "memo_pkg.App_Memo_Pkt(pFrmMemoId => ? , pAppMemoId => ?, pStkIdn => ?, pAppFlg => ?)";
                params = new ArrayList();
                params.add(String.valueOf(memoIdn));
                params.add(String.valueOf(appMemoIdn));
                params.add(Long.toString(lPktIdn));
                params.add(lFlg);
                ct = db.execCall("app_memo_pkt",app_memoPkt , params);
                
    //                String appMemoPkt =
    //                    " insert into jandtl(idn, mstk_idn, qty, cts, rte, quot, dte, stt, rap_dis)\n"
    //                    + " select ?, a.idn, a.qty, a.cts, nvl(upr, cmp), b.quot, sysdate, 'AP', decode(a.rap_rte, 1, null, trunc(100 - (quot*100/a.rap_rte),2))\n"
    //                    + " from mstk a, jandtl b where a.idn = b.mstk_idn and b.idn = ? and b.mstk_idn = ? ";
    //                ArrayList ary = new ArrayList();
    //
    //                ary.add(String.valueOf(appMemoIdn));
    //                ary.add(String.valueOf(memoIdn));
    //                ary.add(Long.toString(lPktIdn));
    //                ct = db.execUpd(" app pkt ", appMemoPkt, ary);
    //
    //                if (ct > 0) {
    //                    String updMStk = " update mstk set odt = sysdate, stt = 'MKAP', flg = ? where idn = ? ";
    //
    //                    ary = new ArrayList();
    //                    ary.add(lFlg);
    //                    ary.add(Long.toString(lPktIdn));
    //                    ct = db.execUpd(" app pkt ", updMStk, ary);
    //                }
            }
        }

        String updJanQty = " jan_qty(?) ";

        params = new ArrayList();
        params.add(String.valueOf(memoIdn));

        int ct = 0;

        ct = db.execCall("JanQty", updJanQty, params);

        if (appMemoIdn > 0) {
            updJanQty = " jan_qty(?) ";
            params    = new ArrayList();
            params.add(Integer.toString(appMemoIdn));
            ct = 0;
            ct = db.execCall("JanQty", updJanQty, params);
        }

        req.setAttribute("memoId", String.valueOf(appMemoIdn));

        return am.findForward("memo");
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
        MemoReturnFrm udf = (MemoReturnFrm) af;

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
        MemoReturnFrm udf = (MemoReturnFrm) af;

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

        return am.findForward("pndPage");
        }
    }

    public MemoReturnTest() {
        super();
    }
}
