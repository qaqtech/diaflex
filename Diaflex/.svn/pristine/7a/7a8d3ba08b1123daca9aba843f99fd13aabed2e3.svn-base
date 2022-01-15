package ft.com.pricing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.MailAction;
import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.sql.CallableStatement;

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

public class AjaxPrcAction extends DispatchAction {
   
    public ActionForward selectList(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr();
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
        int ct = 0;
        String nwFlg = "Z";
        String status = req.getParameter("stt");
        String stkid = req.getParameter("stkIdn");
        String all = util.nvl(req.getParameter("ALL"));
        if(status.equals("true"))
                nwFlg = "S";
        ArrayList params = new ArrayList();
        params.add(nwFlg);
        String updGT="Update gt_srch_rslt set flg = ? " ;
        if(!all.equals("ALL")){
            updGT="Update gt_srch_rslt set flg = ? where stk_idn = ? ";
            
            params.add(stkid);
        }
            
         ct = db.execUpd("update gt", updGT, params) ;
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            StringBuffer sb = new StringBuffer();
            sb.append("yes");
            res.getWriter().write("<values>"+sb.toString()+"</values>");
            
         return null;
        }
    public ActionForward status(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr();
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
        int ct = 0;
        String nwFlg = "Z";
        String status = req.getParameter("stt");
        String stkid = req.getParameter("stkIdn");
        String all = util.nvl(req.getParameter("ALL"));
        if(status.equals("true"))
                nwFlg = "S";
        ArrayList params = new ArrayList();
        params.add(nwFlg);
        String updGT="Update gt_srch_rslt set flg = ? " ;
        if(!all.equals("ALL")){
            updGT="Update gt_srch_rslt set flg = ? where stk_idn = ? ";
            
            params.add(stkid);
        }
            
         ct = db.execUpd("update gt", updGT, params) ;
            res.setContentType("text/xml");
            res.setHeader("Cache-Control", "no-cache");
            StringBuffer sb = new StringBuffer();
            sb.append("yes");
            res.getWriter().write("<values>"+sb.toString()+"</values>");
            
         return null;
        }
    public ActionForward security(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        String key = util.nvl(req.getParameter("key"));
        ArrayList ary = new ArrayList();
        ary.add(key);
        ary.add("2");
        ArrayList out = new ArrayList();
        out.add("V");
        CallableStatement ct =db.execCall("chkPwd", "sec_pkg.chk_pwd( pPwd => ? , pLvl => ?, pValid => ?)", ary, out) ;
        String msg = ct.getString(ary.size()+1);
        res.getWriter().write("<msg>"+msg+"</msg>");
        ct.close();
        return null;
    }
    
    public ActionForward price(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    StringBuffer sb = new StringBuffer();
    String stkIdn = util.nvl(req.getParameter("stkIdn"));
    if(stkIdn.equals("ALL"))
    stkIdn = "";
    ArrayList ary = new ArrayList();
    String typ = util.nvl(req.getParameter("typ"));
    String diff = util.nvl(req.getParameter("diff"));
    String Idn = util.nvl(req.getParameter("Idn"));
   String[] memoIdLst = Idn.split(",");
   if(memoIdLst!=null && memoIdLst.length>0){
     for(int j=0; j <memoIdLst.length; j++) {    
     String    memoIdn=(String)memoIdLst[j];
    String modQuot="memo_pkg.Mod_Quot(pIdn => ?, pTyp => ?, pDiff => ?, pPktIdn => ?)";
    ary = new ArrayList();
    ary.add(memoIdn);
    ary.add(typ);
    ary.add(diff);
    ary.add(stkIdn);
    if(typ.equals("XRT")){
    modQuot="memo_pkg.Mod_Quot(pIdn => ?, pTyp => ?, pDiff => ?, pPktIdn => ?,pXrt => ?)";
    ary.add(diff);
    }
    int ct = db.execCall("mod_quot", modQuot, ary);
     }
     }
    String sumTotal = "select trunc(sum(nvl(a.fnl_vlu,a.quot_vlu))/sum(trunc(a.cts,2)),2) avg , sum(a.rap_vlu) rapVlu ,sum(nvl(a.fnl_vlu,a.quot_vlu)) ttlVlu ,\n" + 
    "trunc((sum(nvl(a.fnl_vlu,a.quot_vlu)/a.exh_rte)/sum(decode(a.rap_rte,1, 1, a.rap_vlu))*100) - 100, 6) avg_dis from gt_memo_pri_chng a ";
    ArrayList outLst = db.execSqlLst("sumTotal",sumTotal, new ArrayList());
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<price>");
    sb.append("<stkIdn>TOTAL</stkIdn>");
    sb.append("<fnlVlu>"+util.nvl(rs.getString("ttlVlu"),"0")+"</fnlVlu>");
    sb.append("<fnlQuot>"+util.nvl(rs.getString("avg"),"0")+"</fnlQuot>");
    sb.append("<fnlDis>"+util.nvl(rs.getString("avg_dis"),"0")+"</fnlDis>");
    sb.append("<rapVlu>"+util.nvl(rs.getString("rapVlu"),"0")+"</rapVlu>");
    sb.append("</price>");
    }
    rs.close();
    pst.close();

    String gt_memo_pri = "select mstk_idn , fnl_vlu , rap_vlu , fnl_quot , fnl_dis " +
    " from gt_memo_pri_chng where mstk_idn = nvl(?, mstk_idn) ";
    ary = new ArrayList();
    ary.add(stkIdn);
    outLst = db.execSqlLst("gt_memo_pri",gt_memo_pri, ary);
    pst = (PreparedStatement)outLst.get(0);
    rs = (ResultSet)outLst.get(1);
    while(rs.next()){
    sb.append("<price>");
    sb.append("<stkIdn>"+util.nvl(rs.getString("mstk_idn"),"0")+"</stkIdn>");
    sb.append("<fnlVlu>"+util.nvl(rs.getString("fnl_vlu"),"0")+"</fnlVlu>");
    sb.append("<fnlQuot>"+util.nvl(rs.getString("fnl_quot"),"0")+"</fnlQuot>");
    sb.append("<fnlDis>"+util.nvl(rs.getString("fnl_dis"),"0")+"</fnlDis>");
    sb.append("<rapVlu>"+util.nvl(rs.getString("rap_vlu"),"0")+"</rapVlu>");
    sb.append("</price>");
    }
    rs.close();
    pst.close();
    res.getWriter().write("<prices>"+sb.toString()+"</prices>");
    return null;
    }
    public ActionForward checkMemoBuyer(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        StringBuffer sb = new StringBuffer();
        String memoId = util.nvl(req.getParameter("memoId"));
         int count=0;
        memoId =util.getVnm(memoId);  
        String checkByr = "select distinct nme_idn  from mjan where idn in ("+memoId+") ";
        ArrayList outLst = db.execSqlLst("checkByr",checkByr, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String nmeIdn=(String)util.nvl(rs.getString("nme_idn"));
            count++ ;
         }
        rs.close();
        pst.close();
        if(count!=1){
            res.setContentType("text/xml"); 
            res.setHeader("Cache-Control", "no-cache"); 
            res.getWriter().write("<message>Yes</message>");
        }else{
             res.setContentType("text/xml"); 
             res.setHeader("Cache-Control", "no-cache"); 
             res.getWriter().write("<message>No</message>");
     }
        return null;
    }


    public ActionForward appToLive(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        StringBuffer sb = new StringBuffer();
        String rem = util.nvl(req.getParameter("rem"));
        String nme = util.nvl(req.getParameter("nme"));
        String liveIdn = util.nvl(req.getParameter("liveIdn"));
        ArrayList ary = new ArrayList();
        ary.add(rem);
        ary.add(nme);
        int ct = 1;
         ct = db.execCall("df_trf_prop", "DP_TRF_FRM_PROP(pGrp=>? , pNme => ?)", ary);
          if(ct>0){
         res.getWriter().write("<msg>DONE</msg>");
          HashMap matDtlList = (HashMap)session.getAttribute("matDtlMap");
          HashMap bfVrifyMatDtl = (HashMap)matDtlList.get("LIVE_"+liveIdn);
          int newLiveIdn = 0;
              String getNme = " select idn from dyn_mst_t where rem =? and nme = ?";
              ary = new ArrayList();
              ary.add(rem);
              ary.add(nme);
            ArrayList rsLst = db.execSqlLst("get Nme", getNme, ary);
            PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
              try {
                  if(rs.next()) {
                  newLiveIdn = rs.getInt(1);
              }
                  rs.close();
                  stmt.close();
              } catch (SQLException e) {
              }
          HashMap afVrifyMatDtl = util.getMatrixDtl(newLiveIdn,"");
          MailAction mailAction = new  MailAction();
          HashMap params = new HashMap();
          params.put("BFMATDTL", bfVrifyMatDtl);
          params.put("AFMATDTL", afVrifyMatDtl);
          params.put("GRP",rem);
          params.put("NME", nme); 
          mailAction.VerifyPriceMail(params, req , res);
         
          }else{
         res.getWriter().write("<msg>FAIL</msg>");
          }
          
        return null;
    }
    public ActionForward priceCalc(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    StringBuffer sb = new StringBuffer();
    String stkIdn = util.nvl(req.getParameter("stkIdn"));
    String memoIdn = util.nvl(req.getParameter("memoIdn"));

    stkIdn = stkIdn.replaceFirst(",", "");
    memoIdn = memoIdn.replaceFirst(",", "");

    if(!stkIdn.equals("") || !memoIdn.equals("")) {

    String getSum = "select count(*) qty, sum(trunc(a.cts,2)) cts , trunc(sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)), 2) vlu, trunc(((sum(trunc(a.cts,2)*(nvl(a.fnl_sal, a.quot))/c.exh_rte) / sum(trunc(a.cts,2)*b.rap_rte))*100) - 100, 2) avg_dis ," +
    " trunc(((sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts,2))))) avg_Rte from jandtl a, mstk b , mjan c " +
    " where a.mstk_idn = b.idn and a.stt = 'AP' and c.idn = a.idn  and c.stt='IS'  " +
    " and a.mstk_idn in (" + stkIdn + ") and a.idn in (" + memoIdn + ") ";


    ArrayList params = new ArrayList();

      ArrayList outLst = db.execSqlLst(" Memo Info", getSum , params);
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
    
    public ActionForward memosale(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    StringBuffer sb = new StringBuffer();
    String stkIdn = util.nvl(req.getParameter("stkIdn"));
    String memoIdn = util.nvl(req.getParameter("memoIdn"));
    String stt = util.nvl(req.getParameter("stt"));
    String typ = util.nvl(req.getParameter("typ"));
    ArrayList params =new ArrayList();
    params.add(stt);
    String updGT="Update gt_pkt set flg = ? ";
    if(typ.equals("S")){
        updGT="Update gt_pkt set flg = ? where mstk_idn=? ";
        params.add(stkIdn);
    }
    int ct = db.execUpd("Upd Usr", updGT, params);
    
    if(ct>0) {

    String getSum = "with jSmry as (\n" + 
    "select 1 eg\n" + 
    " , sum(decode(m.pkt_ty, 'MIX', jd.qty, 1)) qty \n" + 
    " , sum(decode(m.pkt_ty, 'NR', trunc(jd.cts, 2), jd.cts)) cts\n" + 
    " , sum(decode(m.pkt_ty, 'NR', 0, jd.cts_rtn)) cts_rtn\n" + 
    " , sum(decode(m.pkt_ty, 'NR', 0, jd.cts_sal)) cts_sal\n" + 
    " , sum(decode(m.pkt_ty, 'NR', trunc(jd.cts, 2), jd.cts)*quot/nvl(j.exh_rte, 1)) quotVlu\n" + 
    " , sum(decode(m.pkt_ty, 'NR', trunc(jd.cts, 2), jd.cts)*nvl(fnl_sal, quot)/nvl(j.exh_rte, 1)) fnlVlu\n" + 
    " , sum(decode(m.pkt_ty, 'NR', trunc(jd.cts, 2), jd.cts)*decode(m.rap_rte, 1, 0, m.rap_rte)) rapVlu\n" + 
    "from mstk m, mjan j, jandtl jd\n" + 
    "where 1 = 1\n" + 
    " and j.idn = jd.idn\n" + 
    " and m.idn = jd.mstk_idn and jd.idn in ("+memoIdn+") \n" + 
    " and m.stt not in ('LB_PRI_AP') and jd.stt = 'AP' and exists (select 1 from gt_pkt d where d.flg='SL' and m.idn=d.mstk_idn)\n" + 
    ")\n" + 
    "select qty, cts, cts_rtn, cts_sal, trunc(quotVlu,2) quotVlu,trunc(fnlVlu,2) fnlVlu, rapVlu\n" + 
    " , trunc(fnlVlu/cts, 2) avgRte, decode(rapVlu, 0, null, trunc((fnlVlu*100/rapVlu) - 100, 2)) avgRapDis\n" + 
    "from jSmry";


    params = new ArrayList();

      ArrayList outLst = db.execSqlLst(" Memo Info", getSum , params);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    if(rs.next()){
    sb.append("<price>");
    sb.append("<avgDis>"+util.nvl(rs.getString("avgRapDis"),"0")+"</avgDis>");
    sb.append("<avgRte>"+util.nvl(rs.getString("avgRte"),"0")+"</avgRte>");
    sb.append("<Qty>"+util.nvl(rs.getString("qty"),"0")+"</Qty>");
    sb.append("<Cts>"+util.nvl(rs.getString("cts"),"0")+"</Cts>");
    sb.append("<Vlu>"+util.nvl(rs.getString("fnlVlu"),"0")+"</Vlu>");
    sb.append("</price>");
    } else {

    sb.append("<price>");
    sb.append("<avgDis>0</avgDis>");
    sb.append("<avgRte>0</avgRte>");
    sb.append("<Qty>0</Qty>");
    sb.append("<Cts>0</Cts>");
    sb.append("<Vlu>0</Vlu>");
    sb.append("</price>");

    }
        rs.close();
        pst.close();
    }
    res.getWriter().write("<prices>"+sb.toString()+"</prices>");
    return null;
    }
    
    public ActionForward memosaleSH(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    StringBuffer sb = new StringBuffer();
    String stkIdn = util.nvl(req.getParameter("stkIdn"));
    String memoIdn = util.nvl(req.getParameter("memoIdn"));
    String stt = util.nvl(req.getParameter("stt"));
    String typ = util.nvl(req.getParameter("typ"));
    ArrayList params =new ArrayList();
    int l_Rap_Diff=0;
    float l_Diff=1;
    params.add(stt);
    String updGT="Update gt_pkt set flg = ? ";
    if(typ.equals("S")){
        updGT="Update gt_pkt set flg = ? where mstk_idn=? ";
        params.add(stkIdn);
    }
    int ct = db.execUpd("Upd Usr", updGT, params);
    
    if(ct>0) {
    ArrayList chargesLstSH= ((ArrayList)session.getAttribute("chargesLstSH") == null)?new ArrayList():(ArrayList)session.getAttribute("chargesLstSH"); 
    HashMap chargesLstDtlSH= ((HashMap)session.getAttribute("chargesLstDtlSH") == null)?new HashMap():(HashMap)session.getAttribute("chargesLstDtlSH"); 
    if(chargesLstSH.contains("MGMT"))
    l_Rap_Diff=Integer.parseInt(String.valueOf(chargesLstDtlSH.get("MGMT"))); 
    
    for(int i=0;i<chargesLstSH.size();i++){
        String chargetyp=(String)chargesLstSH.get(i);
        if(!chargetyp.equals("MGMT"))
        l_Diff+=(Float.parseFloat(String.valueOf(chargesLstDtlSH.get(chargetyp)))/100);
    }
    String getSum = "select count(*) qty, sum(trunc(a.cts,2)) cts , trunc(sum(trunc(a.cts,2)*trunc((B.Rap_Rte*(100 - (100 - (Nvl(a.fnl_sal, A.Quot)/B.Rap_Rte)*100)- "+l_Rap_Diff+")/100)/"+l_Diff+"*1,2)), 2) vlu, trunc(((sum(trunc(a.cts,2)*(trunc((B.Rap_Rte*(100 - (100 - (Nvl(a.fnl_sal, A.Quot)/B.Rap_Rte)*100)- "+l_Rap_Diff+")/100)/"+l_Diff+"*1,2))/c.exh_rte) / sum(trunc(a.cts,2)*b.rap_rte))*100) - 100, 2) avg_dis ," +
    " trunc(((sum(trunc(a.cts,2)*trunc((B.Rap_Rte*(100 - (100 - (Nvl(a.fnl_sal, A.Quot)/B.Rap_Rte)*100)- "+l_Rap_Diff+")/100)/"+l_Diff+"*1,2)) / sum(trunc(a.cts,2))))) avg_Rte from jandtl a, mstk b , mjan c " +
    " where a.mstk_idn = b.idn and a.stt = 'AP' and c.idn = a.idn  and c.stt='IS' and b.stt not in('LB_PRI_AP')  " +
    " and a.idn in (" + memoIdn + ") and exists (select 1 from gt_pkt d where d.flg='SL' and b.idn=d.mstk_idn) ";


    params = new ArrayList();

      ArrayList outLst = db.execSqlLst(" Memo Info", getSum , params);
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
    public ActionForward priceCalcConsignment(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    StringBuffer sb = new StringBuffer();
    String stkIdn = util.nvl(req.getParameter("stkIdn"));
    String memoIdn = util.nvl(req.getParameter("memoIdn"));

    stkIdn = stkIdn.replaceFirst(",", "");
    memoIdn = memoIdn.replaceFirst(",", "");

    if(!stkIdn.equals("") || !memoIdn.equals("")) {

    String getSum = "select sum(a.qty) qty, sum(trunc(a.cts,2)) cts , trunc(sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)), 2) vlu, trunc(((sum(trunc(a.cts,2)*(nvl(a.fnl_sal, a.quot))/c.exh_rte) / sum(trunc(a.cts,2)*b.rap_rte))*100) - 100, 2) avg_dis ,\n" + 
    "    trunc(((sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts,2))))) avg_Rte from jandtl a, mstk b , mjan c \n" + 
    "    where a.mstk_idn = b.idn and a.stt = 'IS' and a.typ='CS' and c.idn = a.idn  and c.stt='IS' " +
    " and a.mstk_idn in (" + stkIdn + ") and a.idn in (" + memoIdn + ") ";


    ArrayList params = new ArrayList();

      ArrayList outLst = db.execSqlLst(" Memo Info", getSum , params);
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
   
    public AjaxPrcAction() {
        super();
    }
}
