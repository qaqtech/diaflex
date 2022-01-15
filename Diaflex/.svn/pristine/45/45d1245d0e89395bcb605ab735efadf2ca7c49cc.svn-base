package ft.com.pri;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;


import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AjaxPriceAction extends DispatchAction {
    
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
    public ActionForward prcCal(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
        
         String mstkIdn = util.nvl(req.getParameter("stkIdn"));
         String lprp = util.nvl(req.getParameter("lprp"));
         String val = util.nvl(req.getParameter("val"));
        String prcCalcGrp = util.nvl(req.getParameter("prcCalcGrp")).replaceAll("_", "");
        ArrayList ary = new ArrayList();
        
        ary = new ArrayList();
        ary.add(mstkIdn);
        ary.add(lprp);
        ary.add(val);
         String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?  )";
         db.execCallDir("update stk Dtl", stockUpd, ary);
        
        ary = new ArrayList();
        ary.add(mstkIdn);
        db.execCallDir("Add_Calc_Prp", "prc_chk_pkg.Add_Calc_Prp(pIdn => ?)", ary);
        
       
        ary = new ArrayList();
        ary.add(mstkIdn);
        int ct =  db.execCallDir("stk_rap_upd", "stk_rap_upd(pIdn => ?)", ary);
        
        String prc="prc.ITM_PRI(pIdn => ?)";
        ary = new ArrayList();
        ary.add(mstkIdn);
        if(!prcCalcGrp.equals("")){
        prc="prc.ITM_PRI(pIdn => ?,pTyp =>?)";
        ary.add(prcCalcGrp);
        }
        db.execCall("prc.ITM_PRI", prc, ary);
        
        ArrayList prpCalcPriList = (ArrayList)session.getAttribute("prpCalcPriList");
        String getPri = ", max(decode(b.mprp, 'CMP_DIS', b.num, null)) CMP_DIS " + 
        ", max(decode(b.mprp, 'CMP_RTE', b.num, null)) CMP_RTE " + 
        ", max(decode(b.mprp, 'MIX_RTE', b.num, null)) MIX_RTE " + 
        ", max(decode(b.mprp, 'MFG_PRI', b.num, null)) MFG_PRI " + 
        ", max(decode(b.mprp, 'MFG_DIS', b.num, null)) MFG_DIS "+
        ", max(decode(b.mprp, 'MFG_CMP', b.num, null)) MFG_CMP "+
        ", max(decode(b.mprp, 'MFG_CMP_DIS', b.num, null)) MFG_CMP_DIS "+
        ", max(decode(b.mprp, 'MFG_CMP_VLU', b.num, null)) MFG_CMP_VLU ";
        
        
       
        ary = new ArrayList();
        ary.add(mstkIdn);
        String getPrcDtl = "select a.idn, a.vnm, a.rap_rte,a.cts " +getPri+
                    
            " from mstk a, stk_dtl b " + 
            " where a.idn = b.mstk_idn and b.grp = 1 and a.idn = ? "+
            " group by a.idn,a.cts, a.vnm, a.rap_rte ";
        ArrayList outLst = db.execSqlLst(" get vals", getPrcDtl, ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
        if (rs.next()) {
             String cts = util.nvl(rs.getString("cts"),"0");
             String rte =util.nvl(rs.getString("CMP_RTE"),"0");
             Double vluD = util.roundToDecimals((Double.parseDouble(cts)*Double.parseDouble(rte)),2);
             String vlu =String.valueOf(vluD);
             
             sb.append("<prc>"); 
             sb.append("<grp>"+util.nvl(rs.getString("CMP_RTE"),"0")+"</grp>"); 
             sb.append("<val>"+util.nvl(rs.getString("CMP_DIS"),"0")+"</val>"); 
             sb.append("<val1>"+util.nvl(rs.getString("MIX_RTE"),"0")+"</val1>"); 
             sb.append("<val2>"+util.nvl(rs.getString("MFG_PRI"),"0")+"</val2>"); 
             sb.append("<val3>"+util.nvl(rs.getString("MFG_DIS"),"0")+"</val3>"); 
             sb.append("<val4>"+util.nvl(rs.getString("rap_rte"),"0")+"</val4>"); 
             sb.append("<val5>"+util.nvl(rs.getString("MFG_CMP"),"0")+"</val5>"); 
             sb.append("<val6>"+util.nvl(rs.getString("MFG_CMP_DIS"),"0")+"</val6>"); 
             sb.append("<val7>"+util.nvl(rs.getString("MFG_CMP_VLU"),"0")+"</val7>"); 
             sb.append("<amt>"+vlu+"</amt>"); 
             sb.append("</prc>");
         }
        rs.close();
        pst.close();
        if(prcCalcGrp.equals("NN") || prcCalcGrp.equals("MKT") || prcCalcGrp.equals("")){
        String  prmDisFetch ="select pct, grp,mstk_idn , pkt_typ , to_char(rev_dte, 'dd-Mon-rrrr HH24:mi:ss') rev_dte" + 
        " from itm_prm_dis_dsp_v where mstk_idn=? and pct is not null order by pkt_typ desc, grp_srt, sub_grp_srt " ;
        ary = new ArrayList();
        ary.add(mstkIdn);
        outLst = db.execSqlLst(" get vals", prmDisFetch, ary);
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
         while (rs.next()) {
            sb.append("<prc>"); 
            sb.append("<grp>"+util.nvl(rs.getString("grp"),"0")+"</grp>"); 
            sb.append("<val>"+util.nvl(rs.getString("pct"),"0")+"</val>"); 
            sb.append("<val1>"+util.nvl(rs.getString("pkt_typ"),"0")+"</val1>"); 
            sb.append("<val2>"+util.nvl(rs.getString("rev_dte"),"0")+"</val2>"); 
            sb.append("<val3>0</val3>");  
             sb.append("<val4>0</val4>");
            sb.append("<val5>0</val5>");  
             sb.append("<val6>0</val6>");
            sb.append("<val7>0</val7>");
            sb.append("<amt>0</amt>"); 
            sb.append("</prc>"); 
        }
        rs.close();
        pst.close();
        }
        if(prcCalcGrp.equals("MFG") || prcCalcGrp.equals("MKT")){
         String   prmDisFetch ="select pct, grp,mstk_idn , pkt_typ , to_char(rev_dte, 'dd-Mon-rrrr HH24:mi:ss') rev_dte" + 
                    " from ITM_PRM_DIS_MFG_DSP_V where mstk_idn=? and pct is not null order by pkt_typ desc, grp_srt, sub_grp_srt " ;
        ary = new ArrayList();
        ary.add(mstkIdn);
        outLst = db.execSqlLst(" get vals", prmDisFetch, ary);
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
         while (rs.next()) {
            sb.append("<prc>"); 
            sb.append("<grp>"+util.nvl(rs.getString("grp"),"0")+"</grp>"); 
            sb.append("<val>"+util.nvl(rs.getString("pct"),"0")+"</val>"); 
            sb.append("<val1>"+util.nvl(rs.getString("pkt_typ"),"0")+"</val1>"); 
            sb.append("<val2>"+util.nvl(rs.getString("rev_dte"),"0")+"</val2>"); 
            sb.append("<val3>0</val3>");  
             sb.append("<val4>0</val4>");
            sb.append("<val5>0</val5>");  
             sb.append("<val6>0</val6>");
            sb.append("<val7>0</val7>");
            sb.append("<amt>0</amt>"); 
            sb.append("</prc>"); 
        }
        rs.close();
        pst.close();
        }
        res.getWriter().write("<prcs>"+sb.toString()+"</prcs>");
        return null;
    }
    
    public ActionForward Getmnjpri(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
        String dis = req.getParameter("dis");
        String stkIdn = req.getParameter("stkIdn");
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        if(cnt.equals("hk")){
        try{
        Double.parseDouble(dis);
        String get_mnj_pri = "select PRC.GET_MNJ_PRI(? , ?) from dual";
        ArrayList params = new ArrayList();
        params.add(stkIdn);
        params.add(dis);
          ArrayList rsLst = db.execSqlLst("mnj_pri", get_mnj_pri, params);
          PreparedStatement pst = (PreparedStatement)rsLst.get(0);
          ResultSet rs = (ResultSet)rsLst.get(1);
        while(rs.next()){
            res.getWriter().write("<dis>"+rs.getString(1)+"</dis>");
        }
        
        rs.close();
            pst.close();
        }catch(NumberFormatException nfe){  
                res.getWriter().write("<dis>0</dis>");
        }
        }else{
            res.getWriter().write("<dis>0</dis>");
        }
        return null;
    }
    
    public ActionForward getPriceInfo(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res){
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
    boolean isPnt = true;
    boolean isvender = true;
    StringBuffer sb = new StringBuffer();
    String stkIdn = req.getParameter("stkIdn");
    ArrayList ary = new ArrayList();
    //Start -- changes for Memo typ display
    String sqlPrice = " select  nm.nme,to_char(b.iss_dt,'dd-MON-rrrr') iss_dte,to_char(b.rtn_dt,'dd-MON-rrrr') rtn_dte,\n" + 
    "c.mprp, nvl(c.rtn_num,c.iss_num) rtnrte from iss_rtn a ,nme_v nm, iss_rtn_dtl b , iss_rtn_prp c , mprc mp\n" + 
    "where a.iss_id=b.iss_id and b.iss_id=c.iss_id and \n" + 
    "b.iss_stk_idn=c.iss_stk_idn and a.prc_id=mp.idn and mp.grp= ? and\n" + 
    "a.emp_id=nm.nme_idn\n" + 
    "and c.mprp='RTE' and b.iss_stk_idn= ? order by a.iss_id desc ";
        ary.add("PRICING");   
        ary.add(stkIdn); 
        System.out.println(sqlPrice);       
      ArrayList rsLst = db.execSqlLst("sqlPrice", sqlPrice, ary);
      PreparedStatement pst = (PreparedStatement)rsLst.get(0);
      ResultSet rs = (ResultSet)rsLst.get(1);

        try {
            while (rs.next()) {
                sb.append("<prc>");
                sb.append("<nme>" + util.nvl(rs.getString("nme"), "0") + "</nme>");
                sb.append("<iss_dte>" + util.nvl(rs.getString("iss_dte"), "0") + "</iss_dte>");
                sb.append("<rtn_dte>" + util.nvl(rs.getString("rtn_dte"), "0") + "</rtn_dte>");
                sb.append("<mprp>" + util.nvl(rs.getString("mprp"), "0") + "</mprp>");
                sb.append("<rtnrte>" + util.nvl(rs.getString("rtnrte"), "0") + "</rtnrte>");
                sb.append("</prc>");
            }
            rs.close();
            pst.close();

            res.getWriter().write("<prcDtl>" + sb.toString() + "</prcDtl>");
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }

    return null;
    }
    
    public AjaxPriceAction() {
        super();
    }
  
}
