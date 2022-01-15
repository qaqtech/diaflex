package ft.com.marketing;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;
import ft.com.MailAction;
import ft.com.dao.ByrDao;
import ft.com.dao.MAddr;
import ft.com.dao.MNme;
import ft.com.dao.PktDtl;
import ft.com.dao.TrmsDao;

import ft.com.report.DailySalesReportForm;

import java.io.IOException;

import java.io.OutputStream;

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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class MemoSaleActionSH extends DispatchAction {
    public MemoSaleActionSH() {
        super();
    }

    public ActionForward loadSale(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        MemoSaleFrm udf = (MemoSaleFrm) af;
          util.updAccessLog(req,res,"Memo sale", "Memo sale load");
        ArrayList byrList = new ArrayList();
        String    getByr  =
            "select nme_idn, initcap(byr) byr from jan_byr_pndg_v where nvl(appQty, 0) > 0 order by byr";

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
        byrList= (byrList == null)?new ArrayList():(ArrayList)byrList;
//        udf.setValue("byrList", byrList);
        udf.setByrLstFetch(byrList);
        udf.setByrLst(byrList);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_SALE");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("MEMO_SALE");
        allPageDtl.put("MEMO_SALE",pageDtl);
        }
        info.setPageDetails(allPageDtl);
          util.updAccessLog(req,res,"Memo sale", "Memo sale load done");
        return am.findForward("loadSH");
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
        MemoSaleFrm udf = (MemoSaleFrm) af;
          util.updAccessLog(req,res,"Memo sale", "Memo sale load pkts ");
        SearchQuery srchQuery = new SearchQuery();
        String    view        = "NORMAL";
        ArrayList trmList     = new ArrayList();
        String    memoIds     = "";
        ArrayList params = null;
        String    pand        = req.getParameter("pnd");
        ArrayList pkts        = new ArrayList();
        String    memoSrchTyp = util.nvl((String) udf.getValue("memoSrch"));
        String    vnmLst      = "";
        String    dlvyn      = "Yes";
        int ct = db.execUpd(" del gt", "delete from gt_pkt", new ArrayList());
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_SALE");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("MEMO_SALE");
            allPageDtl.put("MEMO_SALE",pageDtl);
            }
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
                
        if (memoSrchTyp.equals("ByrSrch")) {
            Enumeration reqNme = req.getParameterNames();

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
        } else {
            memoIds =util.nvl(String.valueOf((udf.getMemoIdn())));
            vnmLst=util.nvl((String)udf.getValue("vnmLst"));
        }

        if (pand != null) {
            memoIds = util.nvl(req.getParameter("memoId"));
            String nmeId = util.nvl(req.getParameter("nmeId"));
            String trmId = util.nvl(req.getParameter("trmId"));
            if(!nmeId.equals("") && !trmId.equals("")){
            String getmemoIds="select idn from mjan where nme_idn=? and trms_idn=? and stt='IS' and typ like '%AP' order by idn";    
            params = new ArrayList();
            params.add(nmeId);
            params.add(trmId);
              ArrayList  outLst = db.execSqlLst("get memo Ids", getmemoIds,  params);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet  rs = (ResultSet)outLst.get(1);
            while(rs.next()){
            if (memoIds.equals(""))
            memoIds = util.nvl(rs.getString("idn"));
            else
            memoIds = memoIds + "," + util.nvl(rs.getString("idn"));
            }
                rs.close();
                pst.close();
            }
        }
        String app = (String)req.getAttribute("APP");
        if(app!=null)
            memoIds = util.nvl((String)req.getAttribute("memoId"));
        if(!vnmLst.equals("")){
            boolean isRtn = true;
            vnmLst=util.getVnm(vnmLst);
            int cnt=0;
            String checkSql ="select distinct c.nmerel_idn  from jandtl a, mstk b , mjan c " + 
            "where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='AP' and c.typ like '%AP' and c.stt='IS' " + 
            " and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) and b.stt in ('MKAP','MKWA','LB_PRI_AP','MKSA')";
          ArrayList  outLst = db.execSqlLst("check rel", checkSql, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                cnt++;
            }
            rs.close();
            pst.close();
            if(cnt==1){  
                isRtn = false;
                String saleIdSql = "select distinct c.idn  from jandtl a, mstk b , mjan c " + 
                "  where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='AP' and c.typ like '%AP' and c.stt='IS' " + 
                "  and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) and b.stt in ('MKAP','MKWA','LB_PRI_AP','MKSA') ";
              outLst = db.execSqlLst("saleID",saleIdSql, new ArrayList());
              pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                    String appIdnval = util.nvl(rs.getString("idn"));
                    if (memoIds.equals("")) {
                        memoIds = appIdnval;
                    } else {
                       memoIds = memoIds + "," + appIdnval;
                    }
                }
                rs.close();
                pst.close();
            }
            if(isRtn){
                req.setAttribute("RTMSG", "Please verify packets");
               return loadSale(am, af, req, res);
            }
            
        }
 
        if(!memoIds.equals("")) {
            if(memoIds.length()< 80){
              util.updAccessLog(req,res,"Memo sale", "Memo Idn "+memoIds);
            }
        
            memoIds = util.getVnm(memoIds);
        int cout = 0;

          ArrayList  outLst = db.execSqlLst("check", "select distinct nme_idn from mjan where idn in (" + memoIds + ")", new ArrayList());
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            cout++;
        }
            rs.close();
            pst.close();
        if (cout < 2) {
            String cur="";
            double exh_rte = 0;
             outLst = db.execSqlLst("check", "select max(exh_rte) exhRte , a.nmerel_idn , nvl(b.cur,'USD') cur " + 
            " from mjan a , nmerel b where idn in ("+memoIds+") " + 
            "  and a.typ like '%AP' and a.stt='IS' and a.nmerel_idn = b.nmerel_idn  group by a.nmerel_idn, b.cur ", new ArrayList());
        
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
         
            while(rs.next()){
               exh_rte = Double.parseDouble(util.nvl(rs.getString("exhRte"),"1"));
               udf.setValue("exhRte", exh_rte); 
               cur=util.nvl(rs.getString("cur").toUpperCase());
            }
            rs.close();
            pst.close();
            
            udf.setValue("cur",cur);
            pageList=(ArrayList)pageDtl.get("FNLEXHRTE");
            if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_val=(String)pageDtlMap.get("dflt_val");
                if(dflt_val.equals("Y")){
                udf.setValue("fnlexhRteDIS", "Y"); 
                udf.setValue("fnlexhRte", exh_rte); 
                }
                }
            }
            
            String dlvpopup_yn="N";
            pageList=(ArrayList)pageDtl.get("DLV_POPUP");
            if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_val=(String)pageDtlMap.get("dflt_val");
                if(dflt_val.equals(cur)){
                dlvpopup_yn="Y"; 
                }
                }
            }
            udf.setValue("DLV_POPUP", dlvpopup_yn);
            String getAvgDis = "select sum(a.qty) qty,sum(a.cts) cts,trunc(sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)), 2) vlu,trunc(((sum(trunc(a.cts,2)*(nvl(a.fnl_sal, a.quot)/"+exh_rte+")) / sum(trunc(a.cts,2)*b.rap_rte))*100) - 100, 2) avg_dis ," +
                              " trunc(((sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts,2))))) avg_Rte from jandtl a, mstk b " + 
                               " where a.mstk_idn = b.idn and a.stt = 'AP' " + 
                                " and a.idn in (" + memoIds + ") and b.stt in ('MKAP','MKWA','LB_PRI_AP','MKSA') ";
            if(!vnmLst.equals(""))
            getAvgDis = getAvgDis+" and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
            params = new ArrayList();
           outLst = db.execSqlLst(" Memo Info", getAvgDis , params);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            if(rs.next()){
                udf.setAvgDis(rs.getString("avg_dis"));
                udf.setAvgPrc(rs.getString("avg_Rte"));
                udf.setQty(rs.getString("qty"));
                udf.setCts(rs.getString("cts"));
                udf.setVlu(rs.getString("vlu"));
            }
            rs.close();
            pst.close();
            String getMemoInfo =
                " select a.nme_idn, a.nmerel_idn,byr.get_trms(a.nmerel_idn) trms,  byr.get_nm(a.nme_idn) byr, a.typ, a.qty, a.cts, a.vlu, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte "
                + " from mjan a  where a.idn in (" + memoIds + ") and a.stt='IS' and typ like '%AP' ";

            params = new ArrayList();
          outLst = db.execSqlLst(" Memo Info", getMemoInfo, params);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            if (rs.next()) {
                udf.setNmeIdn(rs.getInt("nme_idn"));
                udf.setRelIdn(rs.getInt("nmerel_idn"));
                udf.setByr(rs.getString("byr"));
                udf.setTyp(rs.getString("typ"));
                udf.setDte(rs.getString("dte"));
                udf.setValue("trmsLb", rs.getString("trms"));
                HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(rs.getInt("nme_idn")));
                udf.setValue("email",util.nvl((String)buyerDtlMap.get("EML")));
                udf.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
                udf.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));

                if (view.equalsIgnoreCase("Normal")) {
                    String getPktData =
                        " select a.idn , a.qty , mstk_idn, nvl(fnl_sal, quot) memoQuot,trunc(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot), 2) vlu, quot, nvl(fnl_sal, quot) fnl_sal , DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), b.vnm) vnm "
                        + ", b.cts, nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte, a.stt,to_char(trunc(a.cts,2) * b.rap_rte, '99999999990.00') rapVlu,b.stt pktstt "
                        + " ,  decode(rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)) dis,  decode(rap_rte, 1, '', trunc(((nvl(fnl_sal, quot)/"+exh_rte+"/greatest(b.rap_rte,1))*100)-100,2)) mDis,to_char(a.dte, 'dd-Mon-rrrr') dte "
                        + " from jandtl a, mstk b where a.mstk_idn = b.idn " + " and a.idn in (" + memoIds+ ") and b.stt in ('MKAP','MKWA','LB_PRI_AP','MKSA')";
                        
                    if(!vnmLst.equals("")){
                    getPktData = getPktData + " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
                    }
                    // " and decode(typ, 'I','IS', 'E', 'IS', 'WH', 'IS', 'AP') = stt ";
                    getPktData = getPktData + " and a.stt = 'AP'" + " order by b.sk1";
                    
                    params = new ArrayList();
                ArrayList  outLst1 = db.execSqlLst(" memo pkts", getPktData, params);
                 PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
                 ResultSet rs1 = (ResultSet)outLst1.get(1);
                    while (rs1.next()) {
                        PktDtl pkt    = new PktDtl();
                        long   pktIdn = rs1.getLong("mstk_idn");

                        pkt.setPktIdn(pktIdn);
                        pkt.setMemoId(util.nvl(rs1.getString("idn")));
                        pkt.setRapRte(util.nvl(rs1.getString("rap_rte")));
                        pkt.setQty(util.nvl(rs1.getString("qty")));
                        pkt.setCts(util.nvl(rs1.getString("cts")));
                        pkt.setRte(util.nvl(rs1.getString("rte")));
                        pkt.setMemoQuot(util.nvl(rs1.getString("memoQuot")));
                        pkt.setByrRte(util.nvl(rs1.getString("quot")));
                        pkt.setFnlRte(util.nvl(rs1.getString("fnl_sal")));
                        pkt.setDis(rs1.getString("dis"));
                        pkt.setByrDis(rs1.getString("mDis"));
                        pkt.setVnm(rs1.getString("vnm"));
                        pkt.setValue("SalAmt", util.nvl(rs1.getString("vlu")));
                        pkt.setValue("appdte", util.nvl(rs1.getString("dte")));
                        pkt.setValue("pktstt", util.nvl(rs1.getString("pktstt")));
                        pkt.setValue("rapVlu", util.nvl(rs1.getString("rapVlu")));
                        String lStt = rs1.getString("stt");

                        pkt.setStt(lStt);
                        udf.setValue("stt_" + pktIdn, lStt);

                        String getPktPrp =
                            " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                            + " from stk_dtl a, mprp b, rep_prp c "
                            + " where a.mprp = b.prp and b.prp = c.prp and a.grp=1 and c.mdl = 'MEMO_RTRN' and a.mstk_idn = ? "
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
                    rs1.close();
                    pst1.close();
                }

                req.setAttribute("view", "Y");
            }
            rs.close();
            pst.close();
            udf.setValue("noofmemoid",memoIds);
            String insertQ =
                " insert into gt_pkt(mstk_idn,flg)" + 
                " select mstk_idn,a.stt from jandtl a, mstk b where a.mstk_idn = b.idn " + " and a.idn in (" + memoIds+ ") and b.stt in ('MKAP','MKWA','LB_PRI_AP','MKSA')";
                
            if(!vnmLst.equals("")){
            insertQ = insertQ + " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
            }
            // " and decode(typ, 'I','IS', 'E', 'IS', 'WH', 'IS', 'AP') = stt ";
            insertQ = insertQ + " and a.stt = 'AP'";
            ct = db.execUpd("Insert", insertQ, new ArrayList());
        } else {
            req.setAttribute("error", "please select memoIds of one buyer");
            req.setAttribute("view", "N");
            return  loadSale(am, af, req, res);
        }}else {
            req.setAttribute("error", "please select memoIds of one buyer");
            req.setAttribute("view", "N");
            return  loadSale(am, af, req, res);
        }

        loadSale(am, af, req, res);
        ArrayList ary = new ArrayList();
        trmList = srchQuery.getTerm(req,res, udf.getNmeIdn());

//        String getTrm = " select a.idn, a.term from mtrms a, nmerel b "
//                        + " where a.idn = b.trms_idn and b.nme_idn = ? " + " order by a.pct, a.dys  ";
//        ArrayList ary = new ArrayList();
//
//        ary.add(String.valueOf(udf.getNmeIdn()));
//        rs = db.execSql("favSrch", getTrm, ary);
//
//        while (rs.next()) {
//            TrmsDao trms = new TrmsDao();
//
//            trms.setRelId(rs.getInt(1));
//            trms.setTrmDtl(rs.getString(2));
//            trmList.add(trms);
//        }

        ArrayList byrList = new ArrayList();
        String    getByr  =
            "select nme_idn, a.nme byr from nme_v a " + " where nme_idn = ? "
            + " or exists (select 1 from nme_grp_dtl c, nme_grp b where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn "
            + " and b.nme_idn = ? and b.typ = 'BUYER' and c.vld_dte is null) " + " order by 2 ";

        ary = new ArrayList();
        ary.add(String.valueOf(udf.getNmeIdn()));
        ary.add(String.valueOf(udf.getNmeIdn()));
          ArrayList  outLst = db.execSqlLst("byr", getByr, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);

        while (rs.next()) {
            ByrDao byr = new ByrDao();

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
        String sql =  "select a.addr_idn , unt_num, bldg ||''|| street ||''|| landmark ||''|| area ||''|| b.city_nm||''|| c.country_nm addr "+
                      " from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn = ?  and a.vld_dte is null  order by a.dflt_yn desc ";
        
        ary.add(String.valueOf(udf.getNmeIdn()));
        ArrayList maddrList = new ArrayList();
          outLst = db.execSqlLst("memo pkt", sql, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            MAddr addr = new MAddr();
            addr.setIdn(rs.getString("addr_idn"));
            addr.setAddr(rs.getString("addr"));
            maddrList.add(addr);
        }
        rs.close();
        pst.close();
//        String bankSql = "select nme_idn, fnme  from mnme  where 1 = 1 and vld_dte is null  and typ in('GROUP','BANK')";
//        rs = db.execSql("bank Sql", bankSql, new ArrayList());
//        while(rs.next()){
//            MAddr addr = new MAddr();
//            addr.setIdn(rs.getString("nme_idn"));
//            addr.setAddr(rs.getString("fnme"));
//            bankList.add(addr);
//        }
//        rs.close();
//        ArrayList groupList = new ArrayList();
//        String companyQ="select a.nme_idn, a.fnme  from mnme A , nme_dtl b where 1 = 1  " + 
//        "and a.nme_idn=b.nme_idn and  b.mprp='PERFORMA' and b.txt='YES' " + 
//        "and a.vld_dte is null  and typ = 'GROUP'";
//        rs = db.execSql("Group Sql", companyQ, new ArrayList());
//        while(rs.next()){
//            MAddr addr = new MAddr();
//            addr.setIdn(rs.getString("nme_idn"));
//            addr.setAddr(rs.getString("fnme"));
//            groupList.add(addr);
//        }
//        rs.close();
        ArrayList bnkAddrList = new ArrayList();
        boolean dfltbankgrp=true;
        String banknmeIdn=  "" ;
        ary = new ArrayList();
        ary.add(String.valueOf(udf.getNmeIdn()));
        String setbnkCouQ="select bnk_idn,courier from  mdlv where idn in(select max(idn) from mdlv where nme_idn=?)";
          outLst = db.execSqlLst("setbnkCouQ", setbnkCouQ, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        if(rs.next()){
            banknmeIdn=util.nvl(rs.getString("bnk_idn"));
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
        String chargesQ="Select ct.idn,ct.typ,ct.dsc,ct.stg optional,ct.flg,ct.fctr,ct.db_call,ct.rmk,ct.calc_typ,ct.app_typ\n" + 
        "    From Df_Pg A,Df_Pg_Itm B,Charges_Typ ct\n" + 
        "    Where A.Idn = B.Pg_Idn\n" + 
        "    and b.fld_nme=ct.typ\n" + 
        "    And A.Mdl = 'MEMO_SALE'\n" + 
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
            dtl.put("calc_typ",util.nvl(rs.getString("calc_typ")));
            dtl.put("app_typ",util.nvl(rs.getString("app_typ")));
            chargesLst.add(dtl);
        }
        rs.close();
        pst.close();
        ArrayList thruBankList = new ArrayList();
        String thruBankSql = "select b.nme_dtl_idn , b.txt from mnme a , nme_dtl b " + 
                             "where a.nme_idn = b.nme_idn and b.mprp like 'THRU_BANK%' and a.nme_idn=? and b.vld_dte is null";
        ary = new ArrayList();
        ary.add(String.valueOf(udf.getNmeIdn()));
          outLst = db.execSqlLst("thruBank", thruBankSql, ary );
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            MNme dtl = new MNme();
            String txt=util.nvl(rs.getString("txt"));
            dtl.setIdn(util.nvl(rs.getString("nme_dtl_idn")));
            txt.replaceAll("\\#"," ");
            dtl.setFnme(txt);
            thruBankList.add(dtl);
        }
            rs.close();
            pst.close();
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
        fisalcharges(req);
        ArrayList daytrmList = srchQuery.getdayTerm(req,res);
        ArrayList groupList = srchQuery.getgroupList(req, res);
        ArrayList bankList = srchQuery.getBankList(req, res);
        ArrayList courierList = srchQuery.getcourierList(req, res);
        session.setAttribute("chargesLst", chargesLst);
        udf.setThruBankList(thruBankList);
        udf.setCourierList(courierList);
        udf.setBnkAddrList(bnkAddrList);
        udf.setGroupList(groupList);
        udf.setBankList(bankList);
        udf.setAddrList(maddrList);
//        udf.setValue("byrList", byrList);
        udf.setByrLstFetch(byrList);
        udf.setByrLst(byrList);
        udf.setValue("byrIdn", udf.getNmeIdn());
        udf.setValue("byrTrm", udf.getRelIdn());
        udf.setValue("memoIdn", memoIds);
        udf.setByrIdn(String.valueOf(udf.getNmeIdn()));
        udf.setTrmsLst(trmList);
        udf.setDaytrmsLst(daytrmList);
        udf.setPkts(pkts);
        info.setValue("PKTS", pkts);
        pageList=(ArrayList)pageDtl.get("DLVPOPYN");
        if(pageList!=null && pageList.size() >0){
           for(int j=0;j<pageList.size();j++){
           pageDtlMap=(HashMap)pageList.get(j);
           dlvyn=(String)pageDtlMap.get("dflt_val");
           }
        }
        udf.setValue("isDLV", dlvyn);
        util.updAccessLog(req,res,"Memo sale", "Memo sale load pkts size "+pkts.size());
        req.setAttribute("NMEIDN",String.valueOf(udf.getNmeIdn()));
        session.setAttribute("chargesLstSH", new ArrayList());
        session.setAttribute("chargesLstDtlSH", new HashMap());
        return am.findForward("viewSH");
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
          util.updAccessLog(req,res,"Memo sale", "Memo sale save");
        MemoSaleFrm udf = (MemoSaleFrm) af;

        ArrayList pkts     = new ArrayList();
        String    pktNmsSl = "";
        String    pktNmsRT = "";
        String buttonPressed = "";
        int       appSlIdn = 0;
        
        
        if (udf.getValue("consignment")!=null)
        buttonPressed = "consignment";
        
        String typ="SL";
        String isDlv = util.nvl((String)udf.getValue("isDLV"));
        if(isDlv.equals("No"))
            typ="LS";
        if(buttonPressed.equalsIgnoreCase("consignment"))
            typ="CS";
        String dlvDte = util.nvl((String)udf.getValue("dlvDte"));
        
        pkts = (ArrayList) info.getValue("PKTS");    // udf.getPkts();
//        for (int i = 0; i < pkts.size(); i++) {
//            PktDtl pkt     = (PktDtl) pkts.get(i);
//            long   lPktIdn = pkt.getPktIdn();
//            String memoIdn = pkt.getMemoId();
//            String lStt    = util.nvl((String) udf.getValue("stt_" + lPktIdn));
//            String insertGt = "insert into gt_pkt_jan select ? , ? , sysdate , ? from dual";
//            ArrayList ary = new ArrayList();
//            ary.add(Long.toString(lPktIdn));
//            ary.add(memoIdn);
//            ary.add(lStt);
//            int ct = db.execUpd("insertGt", insertGt, ary);
//        }

        for (int i = 0; i < pkts.size(); i++) {
            PktDtl pkt     = (PktDtl) pkts.get(i);
            long   lPktIdn = pkt.getPktIdn();
            String memoIdn = pkt.getMemoId();
            String lStt    = util.nvl((String) udf.getValue("stt_" + lPktIdn));
            String vnm = pkt.getVnm();
          
            String updPkt = "";

            if (lStt.equals("SL")) {
                if (appSlIdn == 0) {
                    ArrayList ary = new ArrayList();

                    ary.add(Integer.toString(udf.getNmeIdn()));
                    ary.add(Integer.toString(udf.getRelIdn()));
                    ary.add(udf.getValue("byrTrm"));
                    ary.add(udf.getByrIdn());
                    ary.add(udf.getValue("addr"));
                    ary.add(typ);
                    ary.add("IS");
                    ary.add(memoIdn);
                    ary.add("NN");
                    ary.add(nvl((String) udf.getValue("aadatpaid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk1paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk2paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk3paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk4paid"), "Y"));
                    ary.add(util.nvl((String)udf.getValue("exhRte")));
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
                }
                if (appSlIdn != 0) {
                params = new ArrayList();
                if(buttonPressed.equalsIgnoreCase("consignment"))
                params.add("CS");
                else
                params.add("SL");
                params.add(Long.toString(lPktIdn));
                params.add(memoIdn);

                int upJan = db.execUpd("updateJAN", "update jandtl set stt=? , dte_sal=sysdate where mstk_idn=? and idn= ? ", params);

                // params = new ArrayList();
                // params.add(String.valueOf(appSlIdn));
                // params.add(memoIdn);
                // int ct = db.execCall("sale from memo", "sl_pkg.Sal_Frm_Memo(pIdn => ?, pMemoIdn =>?)", params);
                params = new ArrayList();
                params.add(String.valueOf(appSlIdn));
                params.add(memoIdn);
                params.add(Long.toString(lPktIdn));
                params.add(pkt.getQty());
                params.add(pkt.getCts());
                params.add(pkt.getMemoQuot());
                if(buttonPressed.equalsIgnoreCase("consignment"))
                params.add("CS");
                else
                params.add("SL");

                int SalPkt = db.execCall("sale from memo",
                                         "sl_pkg.Sal_Pkt(" + "pIdn => ?" + ", pMemoIdn =>?" + ", pStkIdn => ?"
                                         + ", pQty => ?" + ", pCts => ?" + ", pFnlSal=> ?" + ", pStt => ?)", params);
                String sl_typ = util.nvl((String)udf.getValue("sale_typ"));
                if(!sl_typ.equals("")){
                  String mstkLab = "select cert_lab from mstk where idn=?";
                    params = new ArrayList();
                    params.add(Long.toString(lPktIdn));
                  ArrayList  outLst = db.execSqlLst("mstkLab", mstkLab, params);
                  PreparedStatement pst = (PreparedStatement)outLst.get(0);
                  ResultSet  rs = (ResultSet)outLst.get(1);
                    if(rs.next()){
                      String cert_lab = rs.getString("cert_lab");
                      ArrayList ary = new ArrayList();
                      ary.add(Long.toString(lPktIdn));
                      ary.add("SL_TYP");
                      ary.add(sl_typ);
                      ary.add(cert_lab);
                      ary.add("C");
                      ary.add("1");
                      String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?, pPrpTyp => ?, pgrp => ? )";
                      int ct = db.execCall("stockUpd",stockUpd, ary);
                      
                    }
                    rs.close();
                    pst.close();
                }
              
                pktNmsSl = pktNmsSl +"," +vnm;
                }
            } else if (lStt.equals("RT")) {
                    String rmk = util.nvl((String)udf.getValue("rmk_"+Long.toString(lPktIdn)));
                   
                    params = new ArrayList();
                    params.add(memoIdn);
                    params.add(Long.toString(lPktIdn));
                    params.add(rmk);
                    int ct = db.execCall(" App Pkts", "memo_pkg.rtn_pkt( pMemoId =>?, pStkIdn=> ? , pRem => ?)", params);

                    pktNmsRT = pktNmsRT + "," +vnm;
                    req.setAttribute("RTMSG", "Packets get return ");
            }else if (lStt.equals("CAN")){
                
                
            }else{
              
            }
            
            
            String updJanQty = " jan_qty(?) ";

            params = new ArrayList();
            params.add(memoIdn);
            int ct1 = db.execCall("JanQty", updJanQty, params);

        }
        info.setValue("PKTS",new ArrayList());

        if (appSlIdn != 0) {
            String fnlexhRte=util.nvl((String)udf.getValue("fnlexhRte"));
            if(!fnlexhRte.equals("")){
                params = new ArrayList();
                params.add(fnlexhRte);
                params.add(String.valueOf(appSlIdn));
                int upFnlExh = db.execUpd("upFnlExhRTE", "update msal set fnl_exh_rte=? where idn= ? ", params);
            }
            String byrDayTrmIdn=util.nvl((String)udf.getValue("byrDayTrm"));
            if(!byrDayTrmIdn.equals("") && !byrDayTrmIdn.equals("0")){
                params = new ArrayList();
                params.add(byrDayTrmIdn);
                params.add(String.valueOf(appSlIdn));
                int upFnlExh = db.execUpd("upFnlExhRTE", "update msal set trms_idn=? where idn= ? ", params);
            }
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
            if(!vlu.equals("") && !vlu.equals("0")){
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
            if(!dlvDte.equals("")){
            ArrayList ary=new ArrayList();
            ary.add(String.valueOf(appSlIdn));
            int upmsal = db.execUpd("updatemsal", "update msal set rmk='"+dlvDte+"' where idn= ? ", ary);
            }
            
            req.setAttribute("saleId", String.valueOf(appSlIdn));
            pktNmsSl=pktNmsSl.replaceFirst("\\,", "");
            req.setAttribute("SLMSG", "Packets get Sale : " + pktNmsSl + " with sale Id : " + appSlIdn);
           
            req.setAttribute("performaLink","Y");
//           pktNmsSl=pktNmsSl.replaceFirst("\\,", "");
//           req.setAttribute("performaLink", info.getReqUrl()+"/marketing/performa.do?method=perInv&idn="+appSlIdn+"&byrIdn="+udf.getByrIdn()+"&byrAddIdn="+util.nvl((String)udf.getValue("addr"))+"&bankIdn="+util.nvl((String)udf.getValue("bankIdn"))+
//                                            "&bankAddIdn="+util.nvl((String)udf.getValue("bankAddr"))+"&perInvIdn="+util.nvl(pktNmsSl)
//                                            +"&relIdn="+util.nvl((String)udf.getValue("byrTrm"))+"&echarge="+util.nvl(req.getParameter("echarge"))+"&grpIdn="+util.nvl((String)udf.getValue("grpIdn"))
//                                            +"&courier="+util.nvl((String)udf.getValue("courier")));
       int accessidn=util.updAccessLog(req,res,"Memo sale", "Memo sale idn "+appSlIdn); 
       req.setAttribute("accessidn", String.valueOf(accessidn));; 
        }
          
        return loadSale(am, af, req, res);
        }
    }

  
    public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Memo sale", "Create Excel");
        String mail=util.nvl(req.getParameter("mail"));
        String nmeIdn=util.nvl(req.getParameter("nmeIdn"));
        String byrDtl=util.nvl(req.getParameter("byrDtl"));
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "MemoSale"+util.getToDteTime()+".xls";
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        if(!byrDtl.equals("")){
            HashMap pktPrpMap=(HashMap)pktList.get(0);
            String memo_id=util.nvl((String)pktPrpMap.get("Memo Id"));
            ArrayList params=new ArrayList();
            itemHdr=new ArrayList();
            itemHdr.add("Memo Id");itemHdr.add("Packet Code");itemHdr.add("SH");itemHdr.add("CRTWT");itemHdr.add("CO");itemHdr.add("PU");
            itemHdr.add("Quot");itemHdr.add("SalAmt");itemHdr.add("ByrDis");itemHdr.add("RapRte");
            itemHdr.add("BYR");
            itemHdr.add("Date");itemHdr.add("CERT NO.");
            itemHdr.add("AADATCOMM");
            itemHdr.add("TERM");
            itemHdr.add("BROKER");
            itemHdr.add("EMP");itemHdr.add("LAB");itemHdr.add("LOC");
            String memoQ="select \n" + 
            "byr.get_nm(Nvl(Nvl(a.Mbrk2_Idn,a.Mbrk3_Idn),a.Mbrk4_Idn)) broker,byr.get_trms(a.nmerel_idn) trms\n" + 
            ",byr.get_nm(a.nme_idn) byr,a.aadat_comm,byr.get_nm(b.emp_idn) emp,to_char(a.dte, 'dd-Mon-rrrr') dte\n" + 
            "from mjan a,nme_v b\n" + 
            "where \n" + 
            "a.nme_idn=b.nme_idn and\n" + 
            "a.idn=?";
            params.add(memo_id);
          ArrayList  outLst = db.execSqlLst("memo by Shape", memoQ, params);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                pktPrpMap.put("BYR", util.nvl(rs.getString("byr")));
                pktPrpMap.put("Date", util.nvl(rs.getString("dte")));
                pktPrpMap.put("AADATCOMM", util.nvl(rs.getString("aadat_comm")));
                pktPrpMap.put("TERM", util.nvl(rs.getString("trms")));
                pktPrpMap.put("BROKER", util.nvl(rs.getString("broker")));
                pktPrpMap.put("EMP", util.nvl(rs.getString("emp")));
            }
            rs.close();
            pst.close();
        }
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
        if(mail.equals("")){
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        hwb.write(out);
        out.flush();
        out.close();
        return null;
        }else{
            SearchQuery query=new SearchQuery();
            query.MailExcelmass(hwb, fileNm, req,res);  
            ArrayList emailids=util.byrAllEmail(nmeIdn);
            req.setAttribute("ByrEmailIds",emailids);
            return am.findForward("mail");
        }
        }
    }
    
       
    public String nvl(String pVal, String rVal) {
        String val = pVal;

        if (pVal == null) {
            val = rVal;
        }

        return val;
    }
    public void fisalcharges(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        Connection conn=info.getCon();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    HashMap fisalcharges = (session.getAttribute("fisalcharges") == null)?new HashMap():(HashMap)session.getAttribute("fisalcharges");

    try {
    if(fisalcharges.size() == 0) {
    String ptyp="";
    ArrayList dataLst=new ArrayList();
    ArrayList data=new ArrayList();
    String gtView = "Select  c.typ,a.chr_fr,a.chr_to  From Rule_Dtl A, Mrule B , Charges_Typ C \n" + 
    "Where A.Rule_Idn = B.Rule_Idn And  \n" + 
    "c.typ = a.dsc and b.mdl = 'JFLEX' \n" + 
    "and b.nme_rule = 'FIX_SALE_CHARGES' and c.stt='A' \n" + 
    "and a.til_dte is null order by a.srt_fr , a.dsc , a.chr_fr";
      ArrayList  outLst = db.execSqlLst("gtView", gtView, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
    data=new ArrayList();
    String typ=util.nvl(rs.getString("typ"));
        if(ptyp.equals(""))
        ptyp=typ;
        if(!ptyp.equals(typ)){
            fisalcharges.put(ptyp,dataLst);
            dataLst = new ArrayList();
            ptyp=typ;
        }
        data.add(util.nvl(rs.getString("chr_fr")));
        data.add(util.nvl(rs.getString("chr_to")));
        dataLst.add(data);
    }
        rs.close();
        pst.close();
        if(!ptyp.equals("")){
        fisalcharges.put(ptyp,dataLst);
        }
    rs.close();
    session.setAttribute("fisalcharges", fisalcharges);
    }
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    }
    
    
    
    public ActionForward loadauthenticate(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        MemoSaleFrm udf = (MemoSaleFrm) form;
        udf.reset();
        util.updAccessLog(req,res,"Memo sale", "loadauthenticate start");
        ArrayList ary=new ArrayList();
        ArrayList parms = new ArrayList();
        ArrayList pktList = new ArrayList();
        String sqlQ="          Select A.Idn Saleid,a.aud_created_by UserName,Sum(B.Qty) Qty,To_Char(Sum(Trunc(B.Cts,2)),'999990.99') Cts, \n" + 
        "          trunc(sum(Nvl(C.Upr,C.Cmp)*trunc(b.cts,2))/sum(trunc(b.cts, 2)),0) Rte, \n" + 
        "          byr.get_nm(nvl(d.nme_idn,0)) Byr, \n" + 
        "          Trunc(Sum((NVL (b.fnl_sal, b.quot)  / NVL (a.exh_rte, 1))*Trunc(b.Cts,2)),0) fnl_sal, \n" + 
        "          To_Char(Sum(Trunc(B.Cts,2)*(C.Rap_Rte)),'9999999999990.00') Rapvlu, \n" + 
        "          To_Char(Sum(Trunc(B.Cts,2)*((NVL (b.fnl_sal, b.quot)  / NVL (a.exh_rte, 1)))),'999,9999999990.00') Amt, \n" + 
        "          To_Char((Sum(((NVL (b.fnl_sal, b.quot)  / NVL (a.exh_rte, 1)))*Trunc(B.Cts,2))/Sum(Trunc(B.Cts, 2))),'999,9999999990.00') avg_amt, \n" + 
        "          To_Char(Trunc(((Sum(((NVL (b.fnl_sal, b.quot)  / NVL (a.exh_rte, 1)))*Trunc(B.Cts,2))/Sum(Trunc(B.Cts, 2)))/(Sum(C.Rap_Rte*Trunc(B.Cts,2))/Sum(Trunc(B.Cts, 2)))*100) - 100, 2) ,'999999990.00') R_Dis, \n" + 
        "          Byr.Get_Nm(A.Mbrk1_Idn) Mb, \n" + 
        "          nvl(a.brk1_comm,0) mbcomm, \n" + 
        "          Byr.Get_Nm(A.Mbrk2_Idn) Sub, Nvl(A.Brk2_Comm,0) Subcomm ,To_Char(A.Dte,'dd-mm-yyyy') Saldte , To_Char(F.Dte,'dd-mm-yyyy') Appdte , \n" + 
        "          F.Idn Memoid , F.Typ , B.Stt Salstt,a.typ saletyp,mt.term Trms,a.exh_rte,a.fnl_exh_rte,a.rmk \n" + 
        "          from msal a,jansal b, mstk c,mnme d,nmerel e, mjan f,mtrms mt  \n" + 
        "          where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn\n" + 
        "          and a.stt='IS'  and b.stt = 'SL'  and a.typ in('SL','LS')  and a.flg1='PND'\n" + 
        "          And A.Nmerel_Idn=E.Nmerel_Idn And A.Memo_Id=F.Idn and e.trms_idn=mt.idn \n" + 
        "          Group By A.Idn,A.Aud_Created_By, byr.get_nm(nvl(d.nme_idn,0)), Byr.Get_Nm(A.Mbrk1_Idn), Nvl(A.Brk1_Comm,0), Byr.Get_Nm(A.Mbrk2_Idn), Nvl(A.Brk2_Comm,0), To_Char(A.Dte,'dd-mm-yyyy'), To_Char(F.Dte,'dd-mm-yyyy'), F.Idn, F.Typ, B.Stt,a.typ, mt.term,a.exh_rte, a.fnl_exh_rte,a.rmk \n" + 
        "          order by a.idn";
          ArrayList  outLst = db.execSqlLst("sqlQ", sqlQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
            int i = 0;
            while (rs.next()) {
                HashMap pktPrpMap = new HashMap();
                String saleidn = rs.getString("Saleid");
                pktPrpMap.put("SR", String.valueOf(++i));
                udf.setValue("stt_"+saleidn, "PND");
                pktPrpMap.put("SALEIDN",saleidn);
                pktPrpMap.put("CREATEDBY", util.nvl(rs.getString("UserName")));
                pktPrpMap.put("QTY", util.nvl(rs.getString("Qty")));
                pktPrpMap.put("CTS", util.nvl(rs.getString("Cts")));
                pktPrpMap.put("RTE", util.nvl(rs.getString("Rte")));
                pktPrpMap.put("BYR", util.nvl(rs.getString("Byr")));
                pktPrpMap.put("FNL_SAL", util.nvl(rs.getString("fnl_sal")));
                pktPrpMap.put("RAPVLU", util.nvl(rs.getString("Rapvlu")));
                pktPrpMap.put("VLU", util.nvl(rs.getString("Amt")));
                pktPrpMap.put("AVGAMT", util.nvl(rs.getString("avg_amt")));
                pktPrpMap.put("DIS", util.nvl(rs.getString("R_Dis")));
                pktPrpMap.put("MB", util.nvl(rs.getString("Mb")));
                pktPrpMap.put("MBCOMM", util.nvl(rs.getString("mbcomm")));
                pktPrpMap.put("SUB", util.nvl(rs.getString("Sub")));
                pktPrpMap.put("SUBCOMM", util.nvl(rs.getString("Subcomm")));
                pktPrpMap.put("SALEDTE", util.nvl(rs.getString("Saldte")));
                pktPrpMap.put("APPROVEDTE", util.nvl(rs.getString("Appdte")));
                pktPrpMap.put("MEMOIDN", util.nvl(rs.getString("Memoid")));
                pktPrpMap.put("TYP", util.nvl(rs.getString("Typ")));
                pktPrpMap.put("SALETYP", util.nvl(rs.getString("saletyp")));
                pktPrpMap.put("SALESTT", util.nvl(rs.getString("Salstt")));
                pktPrpMap.put("TRMS", util.nvl(rs.getString("Trms")));
                pktPrpMap.put("EXH_RTE", util.nvl(rs.getString("exh_rte")));
                pktPrpMap.put("FNL_XRT", util.nvl(rs.getString("fnl_exh_rte")));
                pktPrpMap.put("RMK", util.nvl(rs.getString("rmk")));
                udf.setValue("stt_" + saleidn,"PND");
                
                String getPktcharges = "                Select b.ref_idn,\n" + 
                "                Max (Decode (A.Typ, 'ADDL_DIS', B.Charges, Null)) ADDL_DIS\n" + 
                "                From Charges_Typ A,Trns_Charges B\n" + 
                "                Where A.Idn=B.Charges_Idn And A.Stt='A' And Nvl(B.Flg,'NA') Not In('Y') And Ref_Idn In (?) And App_Typ='PP' and ref_typ='SAL' \n" + 
                "                Group By B.Ref_Idn";
                parms = new ArrayList();
                parms.add(saleidn);
                ResultSet rs1 = db.execSql("getPktcharges", getPktcharges, parms);
                while (rs1.next()) {
                    pktPrpMap.put("ADDL_DIS", util.nvl(rs1.getString("ADDL_DIS")));
                }
                rs1.close();
                pktList.add(pktPrpMap);
            }
            rs.close();
            pst.close();
            session.setAttribute("pndsalepktList", pktList);
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("SALE_AUTHENTICATION");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("SALE_AUTHENTICATION");
            allPageDtl.put("SALE_AUTHENTICATION",pageDtl);
            }
            info.setPageDetails(allPageDtl);
        util.updAccessLog(req,res,"Memo sale", "loadauthenticate end");
        return am.findForward("authenticate");
        }
    }
    
    public ActionForward saveauthenticate(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        MemoSaleFrm udf = (MemoSaleFrm) form;
        util.updAccessLog(req,res,"Memo sale", "saveauthenticate start");
        ArrayList pndsalepktList= (session.getAttribute("pndsalepktList") == null)?new ArrayList():(ArrayList)session.getAttribute("pndsalepktList");
            ArrayList params = null;
            ArrayList ary = null;
            ArrayList pkts      = new ArrayList();
            String    pktNmsSl  = "";
            String    pktNmsRT  = "";
            String    pktNmsCAN = "";
            boolean isRtn=false;
        int pndsalepktListsz=pndsalepktList.size();
        if(pndsalepktListsz>0){
            for(int i=0;i<pndsalepktListsz;i++){
                isRtn=false;
                HashMap pktPrpMap = new HashMap();
                pktPrpMap=(HashMap)pndsalepktList.get(i);
                String saleIdn = util.nvl((String)pktPrpMap.get("SALEIDN"));
                String lStt    = util.nvl((String) udf.getValue("stt_" + saleIdn));
                String rmk    = util.nvl((String) udf.getValue("rmk_" + saleIdn));
                if (lStt.equals("AP")) {
                    ary=new ArrayList();
                    ary.add(saleIdn);
                    int upMsal = db.execUpd("updateJAN", "update msal set flg1='CNF' where idn= ? ", ary);
                    pktNmsSl = pktNmsSl + "," +saleIdn;
                }   else if (lStt.equals("RT")) {
                    ary=new ArrayList();
                    ary.add(saleIdn);
                    String sqlQ="select c.vnm,c.idn\n" + 
                    "from\n" + 
                    "msal a,jansal b,mstk c \n" + 
                    "where \n" + 
                    "a.idn=b.idn and a.idn=? and \n" + 
                    "b.mstk_idn=c.idn and\n" + 
                    "a.stt='IS'  and b.stt = 'SL' and a.typ in('SL','LS')  and a.flg1='PND' and\n" + 
                    "c.stt in('MKSL','MKSD','BRC_MKSD')";
                  ArrayList  outLst = db.execSqlLst("packets", sqlQ, ary);
                  PreparedStatement pst = (PreparedStatement)outLst.get(0);
                  ResultSet  rs1 = (ResultSet)outLst.get(1);
                  while (rs1.next()) {
                        String stkIdn=util.nvl(rs1.getString("idn"));
                        String vnm=util.nvl(rs1.getString("vnm"));
                        params = new ArrayList();
                        params.add(saleIdn);
                        params.add(stkIdn);
                        params.add(rmk);
                        int ct = db.execCall(" App Pkts", " DLV_PKG.rtn_pkt(pIdn => ? , pStkIdn => ?,pRem => ?)", params);
                        pktNmsRT = pktNmsRT + "," +vnm;
                   }
                   rs1.close();
                   pst.close();
                   isRtn = true;
                } else if (lStt.equals("CAN")) {
                    ary=new ArrayList();
                    ary.add(saleIdn);
                    String sqlQ="select c.vnm,c.idn\n" + 
                    "from\n" + 
                    "msal a,jansal b,mstk c \n" + 
                    "where \n" + 
                    "a.idn=b.idn and a.idn=? and \n" + 
                    "b.mstk_idn=c.idn and\n" + 
                    "a.stt='IS'  and b.stt = 'SL'  and a.typ in('SL','LS')  and a.flg1='PND' and\n" + 
                    "c.stt in('MKSL','MKSD','BRC_MKSD')";
                  ArrayList  outLst = db.execSqlLst("packets", sqlQ, ary);
                  PreparedStatement pst = (PreparedStatement)outLst.get(0);
                  ResultSet  rs1 = (ResultSet)outLst.get(1);
                    while (rs1.next()) {
                    String stkIdn=util.nvl(rs1.getString("idn"));
                    String vnm=util.nvl(rs1.getString("vnm"));
                    params = new ArrayList();
                    params.add(saleIdn);
                    params.add(stkIdn);
                    int ct = db.execCall(" Cancel Pkts ", "sl_pkg.Cancel_Pkt( pIdn =>?, pStkIdn=> ?)", params);
                    pktNmsCAN = pktNmsCAN + "," +vnm;
                    }
                    rs1.close();
                    pst.close();
                    isRtn = true;
                }
                if(isRtn){
                params = new ArrayList();
                params.add(saleIdn);
                int upQuery = db.execCall("calQuot", "SL_PKG.UPD_QTY(pIdn => ? , pTyp => 'DLV')", params);
                }
            }
                
        }
        if(!pktNmsRT.equals("")){
        pktNmsRT=pktNmsRT.replaceFirst("\\,", "");
        req.setAttribute("RTMSG", "Packets get return: " + pktNmsRT);
        }
        if(!pktNmsCAN.equals("")){
        pktNmsCAN=pktNmsCAN.replaceFirst("\\,", "");
        req.setAttribute("CANMSG", "Packets cancelled " + pktNmsCAN);
        }
            
        if(!pktNmsSl.equals("")){
        pktNmsSl=pktNmsSl.replaceFirst("\\,", "");
        req.setAttribute("CONFIRMMSG", "Sale Confirmed " + pktNmsSl);
        }
        util.updAccessLog(req,res,"Memo sale", "saveauthenticate end");
        return loadauthenticate(am, form, req, res);
        }
    }
    
    public ActionForward pktDtl(ActionMapping am, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtl start");
        MemoSaleFrm udf = (MemoSaleFrm) form;
        String salId = util.nvl(req.getParameter("saleId"));
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        itemHdr.add("SRNO");
        itemHdr.add("SALEID");
        itemHdr.add("VNM");
        itemHdr.add("SALEPERSON");
        itemHdr.add("BYR");
        itemHdr.add("COUNTRY");
        ArrayList ary = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        int sr=1;
        if(!salId.equals("")){
            String pktDtlSql =
                          " select a.idn saleid,c.idn,to_char(b.cts,'99999999990.99') cts,DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,nvl(c.upr,c.cmp) rte,c.rap_rte,byr.get_nm(nvl(d.emp_idn,0)) sale_name,byr.get_nm(nvl(d.nme_idn,0)) byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu, \n" + 
                          "                           to_char(trunc(b.cts,2) * TRUNC (NVL (b.fnl_sal, b.quot) / NVL (a.exh_rte, 1), 2), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((TRUNC (NVL (b.fnl_sal, b.quot) / NVL (a.exh_rte, 1), 2))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis  \n" + 
                          "                           from msal a,jansal b, mstk c,mnme d \n" + 
                          "                           where a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn  \n" + 
                          "                           And B.Stt Not In ('RT','CS') And B.Typ In ('SL','DLV') And C.Stt In('MKSL', 'MKSD','BRC_MKSD') And a.idn= ? \n" + 
                          "                           order by c.sk1 ";
        ary.add(salId);
          ArrayList  outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap pktDtl = new HashMap();
            pktDtl.put("SRNO", String.valueOf(sr++));
            pktDtl.put("SALEID", util.nvl(rs.getString("saleid")));
            pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
            pktDtl.put("SALEPERSON", util.nvl(rs.getString("sale_name")));
            pktDtl.put("BYR", util.nvl(rs.getString("byr")));
            pktDtl.put("COUNTRY", util.nvl(rs.getString("country")));
            pktDtl.put("RTE", util.nvl(rs.getString("rte")));
            pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
            pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));
            pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
            String pktIdn = rs.getString("idn");
            String getPktPrp =
                " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                + " from stk_dtl a, mprp b, rep_prp c "
                + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'DAILY_VW' and a.mstk_idn = ? and a.grp=1  and c.flg <> 'N' "
                + " order by c.rnk, c.srt ";

            ary = new ArrayList();
            ary.add(pktIdn);
          ArrayList  outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, ary);
          PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
          ResultSet  rs1 = (ResultSet)outLst1.get(1);
             while (rs1.next()) {
                String lPrp = util.nvl(rs1.getString("mprp"));
                String lVal = util.nvl(rs1.getString("val"));
                pktDtl.put(lPrp, lVal);
                if(prpDspBlocked.contains(lPrp)){
                }else if(!itemHdr.contains(lPrp)){
                itemHdr.add(lPrp);
                    if(lPrp.equals("RTE")){
                      itemHdr.add("SALE RTE");
                      itemHdr.add("AMOUNT");
                    }
                    if(lPrp.equals("RAP_RTE")){
                           itemHdr.add("RAPVLU");
                    }
                }
            }
            rs1.close();
            pst1.close();
            pktDtl.put("RTE",util.nvl(rs.getString("rte")));
            pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
            pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
            pktDtl.put("RAP_RTE",util.nvl(rs.getString("rap_rte")));
            pktDtl.put("RAPVLU",util.nvl(rs.getString("rapvlu")));
            pktDtlList.add(pktDtl);
        }
            rs.close();
            pst.close();
            
        }
       session.setAttribute("pktList", pktDtlList);
       session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtl end");
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
                util.updAccessLog(req,res,"Memo sale", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Memo sale", "init");
            }
            }
            return rtnPg;
            }
}


//~ Formatted by Jindent --- http://www.jindent.com
