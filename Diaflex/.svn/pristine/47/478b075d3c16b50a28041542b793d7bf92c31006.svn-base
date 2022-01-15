package ft.com.rough;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.ByrDao;

import ft.com.dao.MAddr;
import ft.com.dao.MNme;
import ft.com.dao.PktDtl;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;

import ft.com.mixakt.MixReturnForm;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

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

public class RoughClosureAction extends DispatchAction {
    public RoughClosureAction() {
        super();
    }
    
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
       HttpSession session = req.getSession(false);
       InfoMgr info = (InfoMgr)session.getAttribute("info");
       DBUtil util = new DBUtil();
       DBMgr db = new DBMgr();
       String rtnPg="sucess";
       if(info!=null){  
       db.setCon(info.getCon());
       util.setDb(db);
       util.setInfo(info);
       db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
       util.setLogApplNm(info.getLoApplNm());
       rtnPg=init(req,res,session,util);
       }else
       rtnPg="sessionTO";
       if(!rtnPg.equals("sucess")){
           return am.findForward(rtnPg);   
       }else{
           RoughClosureForm udf =(RoughClosureForm)af;
           udf.resetAll();
           SearchQuery query = new SearchQuery();

           ArrayList byrList = new ArrayList();
           String    getByr  =
               "select distinct byr.get_nm(c.nme_idn) byr , c.nme_idn nme_idn" + 
               " from mstk a, jandtl b , mjan c Where a.idn = b.mstk_idn and a.pkt_ty in ('RGH') and b.stt='IS' And b.idn=c.idn";

         ArrayList outLst = db.execSqlLst("byr", getByr, new ArrayList());
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
             
           udf.setByrLstFetch(byrList);
           udf.setByrLst(byrList);
           ArrayList memoList = query.getMemoType(req,res);
           udf.setMemoList(memoList);
           
          
           util.updAccessLog(req,res,"Box Return", "loadRtn end");
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
             util.updAccessLog(req,res,"Box Return", "load start");
            RoughClosureForm udf = (RoughClosureForm) af;
            int       memoIdn = 0;
            String    view    = "NORMAL";
            String    memoIds     = "";
            String    pand        = req.getParameter("pnd");
        ArrayList pkts        = new ArrayList();
        String    memoSrchTyp = util.nvl((String) udf.getValue("memoSrch"));
        String    vnmLst      = "";
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
        }
        String app = (String)req.getAttribute("APP");
        if(app!=null)
        memoIds = util.nvl((String)req.getAttribute("memoId"));
        ArrayList params = null;
        String memoStr = "";
          boolean isMfgLot = false;
            String getAvgDis = "select sum(a.qty-nvl(a.qty_rtn,'0')) qty,sum(a.cts-nvl(a.cts_rtn,'0')) cts,trunc(sum(trunc((a.cts-nvl(a.cts_rtn,'0')),2)*nvl(a.fnl_sal, a.quot)), 2) vlu,trunc(((sum(trunc(a.cts-nvl(a.cts_rtn,'0'),2)*(nvl(a.fnl_sal, a.quot)/c.exh_rte)) / sum(trunc(a.cts-nvl(a.cts_rtn,'0'),2)*b.rap_rte))*100) - 100, 2) avg_dis ," +
                              " trunc(((sum(trunc(a.cts-nvl(a.cts_rtn,'0'),2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts-nvl(a.cts_rtn,'0'),2))))) avg_Rte from jandtl a, mstk b ,mjan c " + 
                               " where a.mstk_idn = b.idn and a.stt = 'IS' and a.idn=c.idn " + 
                                " and a.idn in ("+ memoIds +") ";
            if(!vnmLst.equals(""))
            getAvgDis = getAvgDis+" and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
            params = new ArrayList();
          ArrayList outLst = db.execSqlLst("getAvgDis", getAvgDis, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet mrs = (ResultSet)outLst.get(1);
            if(mrs.next()){
                udf.setQty(mrs.getString("qty"));
                udf.setCts(mrs.getString("cts"));
                udf.setVlu(mrs.getString("vlu"));
                udf.setValue("MemoId", memoIds);
            }
            mrs.close();
           pst.close();
             String getPktData =
              " select mstk_idn, to_char(a.dte, 'dd-Mon-rrrr') dte,  (a.qty-nvl(a.qty_rtn,'0')) qty, a.qty_sal , a.qty_rtn , a.cts-nvl(a.cts_rtn,'0') cts , a.cts_sal , a.cts_rtn , b.vnm , nvl(fnl_sal, quot) memoQuot, quot, fnl_sal"
            + ", nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1,a.idn, b.rap_rte, a.stt,a.rmk "
            + " , trunc(100 - ((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100),2) dis, trunc(100 - ((nvl(fnl_sal, quot)/greatest(b.rap_rte,1))*100),2) mDis "
            + " from jandtl a, mstk b where a.mstk_idn = b.idn and a.stt = 'IS'  ";
                   
                  
                String mstkIdn="";
                String upr="";
                     getPktData = getPktData+" "+memoStr+" and a.idn  in (" + memoIds+ ") order by a.srl, b.sk1 ";                    
               outLst = db.execSqlLst("memo pkts", getPktData, new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                mrs = (ResultSet)outLst.get(1);
                    while (mrs.next()) {
                        PktDtl pkt    = new PktDtl();
                        long   pktIdn = mrs.getLong("mstk_idn");
                         mstkIdn = String.valueOf(pktIdn);
                        pkt.setPktIdn(pktIdn);
                        pkt.setRapRte(util.nvl(mrs.getString("rap_rte")));
                        pkt.setQty(util.nvl(mrs.getString("qty")));
                        pkt.setCts(util.nvl(mrs.getString("cts")));
                        pkt.setRte(util.nvl(mrs.getString("rte")));
                        pkt.setMemoId(util.nvl(mrs.getString("idn")));
                        pkt.setVnm(util.nvl(mrs.getString("vnm")));
                        pkt.setValue("dte",util.nvl(mrs.getString("dte")));
                        upr=util.nvl(pkt.getRte());
                        udf.setValue("RMK_"+mstkIdn,util.nvl(mrs.getString("rmk")));
                        String lStt = mrs.getString("stt");

                        pkt.setStt(lStt);
                      

                        String getPktPrp =
                            " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                            + " from stk_dtl a, mprp b, rep_prp c "
                            + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'RGHCLU_VW' and a.grp=1 and a.mstk_idn = ? "
                            + " order by c.rnk, c.srt ";

                        params = new ArrayList();
                        params.add(Long.toString(pktIdn));

                      ArrayList outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, params);
                      PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
                      ResultSet  rs1 = (ResultSet)outLst1.get(1);
                        while (rs1.next()) {
                            String lPrp = util.nvl(rs1.getString("mprp"));
                            String lVal = util.nvl(rs1.getString("val"));
                            if(lPrp.equals("MFG_LOT_NO") && !lVal.equals("")){
                                isMfgLot = true;
                            }
                            pkt.setValue(lPrp, lVal);
                        }
                        rs1.close();
                        pst1.close();
                        pkts.add(pkt);
                    }
                mrs.close();
                pst.close();
            ArrayList mdlList = viewList(req, res, "RGHCLU_VW");
                
            req.setAttribute("pktList", pkts);
            req.setAttribute("viewList", mdlList);
            udf.setValue("mstkIdn", mstkIdn);
            udf.setValue("memoId", memoIds);
            udf.setValue("upr", upr);
            util.updAccessLog(req,res,"Box Return", "load end");
            return am.findForward("load");
            }
        }
    public ActionForward SaveLotNo(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String mstkIdn  = util.nvl(req.getParameter("mstkIdn"));
        String mfgLotNo = util.nvl(req.getParameter("mfgLotNo"));
        String memoId = util.nvl(req.getParameter("memoId"));
        int idnSeq=0;
        String msg="FAIL";
        StringBuffer sb = new StringBuffer();
        if(!mstkIdn.equals("") && !mfgLotNo.equals("")){
            
            ArrayList ary = new ArrayList();
            ary.add(mstkIdn);
            ary.add("MFG_LOT_NO");
            ary.add(mfgLotNo);
            String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? )";
            int ct = db.execCall("stockUpd",stockUpd, ary);
            
            if(ct>0){
                idnSeq=util.getSeqVal("SEQMLOT");
                ary = new ArrayList();
                ary.add(String.valueOf(idnSeq));
                ary.add(mfgLotNo);
                ary.add(memoId);                
                String insertMlot="insert into mlot(idn,dsc,memo_id,cnum,cinr,dte) select ?,? ,?,1,1,sysdate from dual";
                ct = db.execUpd("insert mkt_prc", insertMlot, ary);
                msg="SUCESS";
            }
            
        }
        sb.append("<idndata>");
        sb.append("<idn>"+idnSeq+"</idn>");
        sb.append("<message>"+msg+"</message>");
        sb.append("</idndata>");
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<idndt>" + sb.toString() + "</idndt>");
        return null;
    
    }
    
    public ArrayList viewList(HttpServletRequest req , HttpServletResponse res,String mdl){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute(mdl);
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
     
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
                ArrayList ary = new ArrayList();
                ary.add(mdl);
              
              ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = ? and flg='Y' order by rnk ",
                               ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close(); 
                pst.close();
                session.setAttribute(mdl, asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return asViewPrp;
    }
    
    public ActionForward loadPkt(ActionMapping am, ActionForm af, HttpServletRequest req,
                                  HttpServletResponse res) throws Exception {
           HttpSession session = req.getSession(false);
           InfoMgr info = (InfoMgr)session.getAttribute("info");
           DBUtil util = new DBUtil();
           DBMgr db = new DBMgr();
           String rtnPg="sucess";
           if(info!=null){  
           db.setCon(info.getCon());
           util.setDb(db);
           util.setInfo(info);
           db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
           util.setLogApplNm(info.getLoApplNm());
           rtnPg=init(req,res,session,util);
           }else
           rtnPg="sessionTO";
           if(!rtnPg.equals("sucess")){
               return am.findForward(rtnPg);   
           }else{
         GenericImpl generic = new GenericImpl();
        ArrayList vwPrpList = generic.genericPrprVw(req, res, "RGHCLU_PKT", "RGHCLU_PKT");
               if(vwPrpList==null)
                   vwPrpList=new ArrayList();
         String mstkIdn = util.nvl(req.getParameter("mstkIdn"));
         String memoId = util.nvl(req.getParameter("memoId"));
         RoughClosureForm udf = (RoughClosureForm) af;
            ArrayList pktList = new ArrayList();
          String sql= "select a.idn,a.cts ,a.qty, c.num rghcts , d.num rghQty , a.upr , decode(a.pkt_ty,'MIX','Mixing','NR','Single','Rough') pktTy,a.stt from mstk a , jandtl b, stk_dtl c , stk_dtl d "+
          " where a.idn=b.mstk_idn "+
          " and b.mstk_idn=c.mstk_idn and c.mprp='RGH_CTS' and c.grp=1 "+
          " and b.mstk_idn=d.mstk_idn and d.mprp='RGH_QTY' and d.grp=1 "+
          " and b.idn=? and a.pkt_rt=? and b.stt=? ";
           ArrayList params = new ArrayList();
           params.add(memoId);
           params.add(mstkIdn);
           params.add("MR");
           ArrayList rsLst = db.execSqlLst("memoPkt", sql, params);
           PreparedStatement pst = (PreparedStatement)rsLst.get(0);
           ResultSet rs = (ResultSet)rsLst.get(1);
           while(rs.next()){
               HashMap pktDtl = new HashMap();
               String idn=util.nvl(rs.getString("idn"));
               pktDtl.put("idn", util.nvl(rs.getString("idn")));
               pktDtl.put("qty", util.nvl(rs.getString("rghQty")));
               pktDtl.put("cts", util.nvl(rs.getString("rghcts")));
               pktDtl.put("polcts", util.nvl(rs.getString("cts")));
               pktDtl.put("polQty", util.nvl(rs.getString("qty")));
               pktDtl.put("upr", util.nvl(rs.getString("upr")));
               pktDtl.put("stt", util.nvl(rs.getString("stt")));
               pktDtl.put("pktTy", util.nvl(rs.getString("pktTy")));
               String getPktPrp =
                   " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                   + " from stk_dtl a, mprp b, rep_prp c "
                   + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'RGHCLU_PKT' and a.grp=1 and a.mstk_idn = ? "
                   + " order by c.rnk, c.srt ";

               params = new ArrayList();
               params.add(idn);
               ResultSet rs1 = db.execSql(" Pkt Prp", getPktPrp, params);
               while (rs1.next()) {
                   String lprp = util.nvl(rs1.getString("mprp"));
                   String lval = util.nvl(rs1.getString("val"));
                   pktDtl.put(lprp, lval);
                   udf.setValue(lprp+"_"+idn, lval);
               }
              rs1.close();
               
               pktList.add(pktDtl);
           }
           rs.close();
           pst.close();
           HashMap pktSummary = new HashMap();
           String summary="select  trunc(sum(a.cts),3) cts, trunc(sum(c.num),3) rghcts , sum(d.num) rghQty , sum(a.qty) qty ,\n" + 
           "trunc((sum(c.num * a.upr) / sum(greatest(c.num,1))),2) avgRte\n" + 
           "from mstk a , jandtl b, stk_dtl c , stk_dtl d \n" + 
           "where a.idn=b.mstk_idn  and b.mstk_idn=c.mstk_idn and c.mprp='RGH_CTS' and c.grp=1  \n" + 
           "and b.mstk_idn=d.mstk_idn and d.mprp='RGH_QTY' and d.grp=1  \n" + 
           "and b.idn=? and a.pkt_rt=? and b.stt=?  ";
            params = new ArrayList();
           params.add(memoId);
           params.add(mstkIdn);
           params.add("MR");
           rsLst = db.execSqlLst("memoPkt", summary, params);
           pst = (PreparedStatement)rsLst.get(0);
           rs = (ResultSet)rsLst.get(1);
           while(rs.next()){
               pktSummary.put("cts", util.nvl(rs.getString("rghcts")));
               pktSummary.put("polcts", util.nvl(rs.getString("cts")));
               pktSummary.put("polQty", util.nvl(rs.getString("qty")));
               pktSummary.put("qty", util.nvl(rs.getString("rghQty")));
               pktSummary.put("avgRte", util.nvl(rs.getString("avgRte")));
           }
           rs.close();
           pst.close();
           req.setAttribute("pktSummary", pktSummary);
            req.setAttribute("pktDtlList", pktList);
        
         return am.findForward("loadPkt");   
    
     }
    }
    
    public ActionForward saveClosure(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
       HttpSession session = req.getSession(false);
       InfoMgr info = (InfoMgr)session.getAttribute("info");
       DBUtil util = new DBUtil();
       DBMgr db = new DBMgr();
       String rtnPg="sucess";
       if(info!=null){  
       db.setCon(info.getCon());
       util.setDb(db);
       util.setInfo(info);
       db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
       util.setLogApplNm(info.getLoApplNm());
       rtnPg=init(req,res,session,util);
       }else
       rtnPg="sessionTO";
       if(!rtnPg.equals("sucess")){
           return am.findForward(rtnPg);   
       }else{
           RoughClosureForm udf = (RoughClosureForm) af;
           String rtmstkIdn = util.nvl(req.getParameter("mstkIdn"));
           String memoId = util.nvl(req.getParameter("memoId"));
           String mfg_lot_no = util.nvl(req.getParameter("MFGLOTNO"));
           String ttlcts = util.nvl(req.getParameter("ttlcts"));
           ArrayList vwPrpList = (ArrayList)session.getAttribute("RGHCLU_PKT");
           String cnt =  util.nvl(req.getParameter("cnt_"+rtmstkIdn));
           if(!cnt.equals("")){
               int count = Integer.parseInt(cnt);
           for(int i=1;i<=count;i++){
               String fldId=rtmstkIdn+"_"+i;
               String typId="TYP_"+fldId;
               String ctsId="CTS_"+fldId;
               String polctsId="POLCTS_"+fldId;
               String qtyId="QTY_"+fldId;
               String polqtyId="POLQTY_"+fldId;
               String rteId="RTE_"+fldId;
               String rmkId="RMK_"+fldId;
               String typVal = util.nvl(req.getParameter(typId));
               String ctsVal = util.nvl(req.getParameter(ctsId));
               String ctsPolVal = util.nvl(req.getParameter(polctsId));
               String qtyVal = util.nvl(req.getParameter(qtyId));
               String qtyPolVal = util.nvl(req.getParameter(polqtyId));
               String rteVal =  util.nvl(req.getParameter(rteId));
               String rmkVal =  util.nvl(req.getParameter(rmkId));
               if(!ctsVal.equals("")){
               ArrayList params = new ArrayList();
               params.add(memoId);
               params.add(rtmstkIdn);
               params.add(qtyPolVal);
               params.add(ctsPolVal);
               params.add(rteVal);
               ArrayList ary = new ArrayList();
               ary.add("I");
               ary.add("V");
               String issuePkt = "RGH_PKG.rgh_iss_pkt(pMemoIdn => ?, pRtPkt => ?, pQty => ? , pCts => ?, pRte => ?" + 
               "    , pStkIdn => ? , pMsg => ?)";
               CallableStatement cst  = db.execCall("issuePkt", issuePkt, params,ary);
               long  stkIdn = cst.getLong(params.size()+1);
                 cst.close();
                 cst=null;
               
//                   String insertStkDtl =
//                       "insert into stk_dtl(mstk_idn,grp,mprp,val,num,dte,psrt,prt1,srt,lab,txt) " +
//                       "select ?,grp,mprp,val,num,dte,psrt,prt1,srt,lab,txt  from stk_dtl where mstk_idn=? and grp=?";
//                   params = new ArrayList();
//                   params.add(String.valueOf(stkIdn));
//                   params.add(rtmstkIdn);
//                   params.add("1");
//                   int ct = db.execUpd("insert stkDtl", insertStkDtl, params);
                   
                   String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";

                 for(int j=0;j<vwPrpList.size();j++){
                   String lprp = (String)vwPrpList.get(j);
                    String fldNme = fldId+"_"+lprp;
                    String fldVal = util.nvl(req.getParameter(fldNme));
                     if(!fldVal.equals("")){
                        params = new ArrayList();
                        params.add(String.valueOf(stkIdn));
                        params.add(lprp);
                        params.add(fldVal);
                       int ct = db.execCall("stockUpd", stockUpd, params);

                         
                    }
                   }
               
               

                params = new ArrayList();
                params.add(String.valueOf(stkIdn));
                params.add("MFG_LOT_NO");
                params.add(mfg_lot_no);
               int ct = db.execCall("stockUpd", stockUpd, params);


               params = new ArrayList();
               params.add(String.valueOf(stkIdn));
               params.add("RGH_CTS");
               params.add(ctsVal);
                ct = db.execCall("stockUpd", stockUpd, params);


               params = new ArrayList();
               params.add(String.valueOf(stkIdn));
               params.add("RGH_QTY");
               params.add(qtyVal);
               ct = db.execCall("stockUpd", stockUpd, params);
               
                
               }
               
           }}
           req.setAttribute("mstkIdn", rtmstkIdn);
            req.setAttribute("memoId", memoId);
           req.setAttribute("MFGLOTNO", mfg_lot_no);
           req.setAttribute("cts", ttlcts);
           return loadPkt(am, af, req, res);
       }
    
     }
    
    public ActionForward ReturnClosure(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
       HttpSession session = req.getSession(false);
       InfoMgr info = (InfoMgr)session.getAttribute("info");
       DBUtil util = new DBUtil();
       DBMgr db = new DBMgr();
       String rtnPg="sucess";
       if(info!=null){  
       db.setCon(info.getCon());
       util.setDb(db);
       util.setInfo(info);
       db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
       util.setLogApplNm(info.getLoApplNm());
       rtnPg=init(req,res,session,util);
       }else
       rtnPg="sessionTO";
       if(!rtnPg.equals("sucess")){
           return am.findForward(rtnPg);   
       }else{
           RoughClosureForm udf = (RoughClosureForm)af;
           String memoId = (String)udf.getValue("memoId");
           String mstkIdn = (String)udf.getValue("mstkIdn");
           String mlotidnVal = util.nvl((String)udf.getValue("mlotidnVal"));
           String upr = util.nvl((String)udf.getValue("upr"));
           String updatejandtl = "update jandtl set stt=?, dte_rtn=sysdate  where idn=? and mstk_idn=?";
           ArrayList ary = new ArrayList();
           ary.add("RT");
           ary.add(memoId);
           ary.add(mstkIdn);
           int ct = db.execUpd("updatejandtl", updatejandtl, ary);
           if(ct>0){
               updatejandtl = "update mstk set stt=?  where idn=? ";
               ary = new ArrayList();
               ary.add("RGH_CMP");
               ary.add(mstkIdn);
              db.execUpd("updatejandtl", updatejandtl, ary);
              
               String lpCts = util.nvl(req.getParameter("lpCts"));
               String totalStone = util.nvl(req.getParameter("totalStone"));
               String labourPerStone = util.nvl(req.getParameter("labourPerStone"));
               String rejectcts = util.nvl(req.getParameter("rejectcts"),"0");
               String rejectrte = util.nvl(req.getParameter("rejectrte"),"0");
               String rghcts = util.nvl(req.getParameter("rghcts"));
              String mfg_lot_no = util.nvl(req.getParameter("mfg_lot_no"));
               String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";

               ary = new ArrayList();
               ary.add(mstkIdn);
               ary.add("LP_CTS");
               ary.add(lpCts);
               ct = db.execCall("stockUpd", stockUpd, ary);


               ary = new ArrayList();
               ary.add(mstkIdn);
               ary.add("TTL_STONE");
               ary.add(totalStone);
               ct = db.execCall("stockUpd", stockUpd, ary);
               
               ary = new ArrayList();
               ary.add(mstkIdn);
               ary.add("REJ_CTS");
               ary.add(rejectcts);
               ct = db.execCall("stockUpd", stockUpd, ary);
               
               ary = new ArrayList();
               ary.add(mstkIdn);
               ary.add("REJ_RTE");
               ary.add(rejectrte);
               ct = db.execCall("stockUpd", stockUpd, ary);
               
               
               
               ary = new ArrayList();
               ary.add(mstkIdn);
               ary.add("LBPERSTONE");
               ary.add(labourPerStone);
               ct = db.execCall("stockUpd", stockUpd, ary);
               
               ary = new ArrayList();
               ary.add(mstkIdn);
               ary.add("MEMO");
               ary.add(memoId);
               ArrayList out = new ArrayList();
               out.add("V");
              CallableStatement  cts = db.execCall("stockUpd", "DP_STK_CST_AVG(pStkIdn => ?, pRefTyp => ?, pRefIdn => ?, pMsg => ?)", ary,out);
              String msg = cts.getString(ary.size()+1);
              req.setAttribute("msg", "Process done successfully");
              
               double xrt=65;
               double ttl_vlu=Math.round((Double.parseDouble(rghcts)*Double.parseDouble(upr))+(Double.parseDouble(labourPerStone)/xrt)-(Double.parseDouble(rejectcts)*Double.parseDouble(rejectrte)));
               String updateMlotSql="update mlot set lp_cts=?,ttl_stone=?,rej_cts=?,lbperstone=?,exh_rte=?,rgh_cts=?,ttl_vlu=? where dsc=?";
               ary = new ArrayList();
               ary.add(lpCts);
               ary.add(totalStone);
               ary.add(rejectcts);
               ary.add(labourPerStone);
               ary.add(String.valueOf(xrt));
               ary.add(rghcts);
               ary.add(String.valueOf(ttl_vlu));
               ary.add(mfg_lot_no);
               ct = db.execCall("updateMlotSql", updateMlotSql, ary);
               
               String recptsql="select mstk_idn from jandtl where idn=? and stt=?";
                ary = new ArrayList();
                ary.add(memoId);
                ary.add("MR");
               ArrayList rsList = db.execSqlLst("recpt", recptsql, ary);
               PreparedStatement pst = (PreparedStatement)rsList.get(0);
               ResultSet rs = (ResultSet)rsList.get(1);
               while(rs.next()){
                   String plstkIdn = rs.getString("mstk_idn");
                 String prod = "STK_PKG.PKT_PRP_UPD(pIdn => ?, pPrp => ? , pVal => to_char(sysdate, 'dd-MON-rrrr') )";
                 ary = new ArrayList();
                 ary.add(plstkIdn);
                 ary.add("TRF_DTE");
                 ct = db.execCall("stockUpdprod", prod, ary);
               }
               rs.close();
               pst.close();
               if(!rejectcts.equals("") && !rejectcts.equals("0")){
                   String insMst =
                       "MIX_PKG.GEN_PKT(pStt => ?,pCts => ?,pPktRt=>?, pPktTyp => ?,pIdn =>?,pVnm =>?)";
                    ary = new ArrayList();
                   ary.add("REJ_AV");
                   ary.add(rejectcts);
                   ary.add(mstkIdn);
                   ary.add("RGH");
                 
                   out = new ArrayList();
                   out.add("I");
                   out.add("V");
                   CallableStatement cst  = db.execCall("findMstkId", insMst, ary, out);
                   Long  newIdn = cst.getLong(ary.size() + 1);
                 cst.close();
                 cst=null;
                   if(newIdn>0){
                       ary = new ArrayList();
                       ary.add(String.valueOf(newIdn));
                       ary.add("CRTWT");
                       ary.add(rejectcts);
                       ct = db.execCall("stockUpd", stockUpd, ary);
                       
                       ary = new ArrayList();
                       ary.add(String.valueOf(newIdn));
                       ary.add("RTE");
                       ary.add(rejectrte);
                       ct = db.execCall("stockUpd", stockUpd, ary);
                   }
                   
                   
               }
              
              
           }else{
               req.setAttribute("msg", "Some Error in Process");
             
           }
           udf.resetAll();
           return am.findForward("ReturnClosure");   
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
                util.updAccessLog(req,res,"Split Return Action", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Split Return Action", "init");
            }
            }
            return rtnPg;
            }
}
