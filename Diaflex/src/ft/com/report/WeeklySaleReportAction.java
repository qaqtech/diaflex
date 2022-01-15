package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.IOException;

import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class WeeklySaleReportAction extends DispatchAction {
    
   public WeeklySaleReportAction() {
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
          util.updAccessLog(req,res,"Weekly Sale Report", "load start");
      WeeklySaleReportForm udf = (WeeklySaleReportForm)af;
      udf.resetAll();
          util.updAccessLog(req,res,"Weekly Sale Report", "load end");
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
             util.updAccessLog(req,res,"Weekly Sale Report", "fetch start");
      WeeklySaleReportForm udf = (WeeklySaleReportForm)af;
      GenericInterface genericInf = new GenericImpl();
      
      db.execUpd("delete gt", "delete from gt_srch_rslt", new ArrayList());
      String dept = (String)udf.getValue("dept");
      String p1DteFrm = (String)udf.getValue("p1dtefr");
      String p1DteTo = (String)udf.getValue("p1dteto");
      String p2DteFrm = (String)udf.getValue("p2dtefr");
      String p2DteTo = (String)udf.getValue("p2dteto");
      String trfDteFrm = (String)udf.getValue("trfdtefr");
      String trfDteTo = (String)udf.getValue("trfdteto");
      String shape = util.nvl((String)udf.getValue("shape"));     
      ArrayList params = new ArrayList();
      params.add(dept);
      params.add(p1DteFrm);
      params.add(p1DteTo);
      params.add(p2DteFrm);
      params.add(p2DteTo);
      params.add(trfDteFrm);
      params.add(trfDteTo);
      params.add(shape);
      int ct = db.execCall("weekly", "report_pkg.weekly_smry_sale(pDept => ? \n" + 
      "    , pDteFrm => ? , pDteTo => ? \n" + 
      "    , pSlDteFrm => ? , pSlDteTo => ? \n" + 
      "    , pTrfDteFrm => ? , pTrfDteTo => ?,pShp => ? )", params);
      ArrayList vWPrpList = genericInf.genericPrprVw(req, res, "STK_SMM_RPT", "STK_SMM_RPT");
      int indexSH = vWPrpList.indexOf("SHAPE")+1;
      String shPrp = util.prpsrtcolumn("prp", indexSH);
      String shSrt = util.prpsrtcolumn("srt", indexSH);
      int indexAG = vWPrpList.indexOf("AGE")+1;
      String agPrp = util.prpsrtcolumn("prp", indexAG);
      int indexLb = vWPrpList.indexOf("LAB")+1;
      String lbPrp = util.prpsrtcolumn("prp", indexLb);
      String lbSrt = util.prpsrtcolumn("srt", indexLb);
      int indexSz = vWPrpList.indexOf("SIZE")+1;
      String szPrp = util.prpsrtcolumn("prp", indexSz);
      String szSrt = util.prpsrtcolumn("srt", indexSz);
      int indexCol = vWPrpList.indexOf("COL")+1;
      String colPrp = util.prpsrtcolumn("prp", indexCol);
      String colSrt = util.prpsrtcolumn("srt", indexCol);
      int indexClr = vWPrpList.indexOf("CLR")+1;
      String clrPrp = util.prpsrtcolumn("prp", indexClr);
      String clrSrt = util.prpsrtcolumn("srt", indexClr);
      int indexTrf = vWPrpList.indexOf("TRF_CMP")+1;
      String trfPrp = util.prpsrtcolumn("prp", indexTrf);
      String trfSrt = util.prpsrtcolumn("srt", indexTrf);
       int indexTrfDte = vWPrpList.indexOf("TRF_DTE")+1;
       String trfDtePrp = util.prpsrtcolumn("prp", indexTrfDte);
       String trfDteSrt = util.prpsrtcolumn("srt", indexTrfDte);
       HashMap prpShMap = new HashMap();
       HashMap prpWiseGd = new HashMap();
       
       HashMap paramsList = new HashMap();
       paramsList.put("vWList", vWPrpList);
       paramsList.put("DEPT", dept);
       paramsList.put("SHPRP", shPrp);
       paramsList.put("SHSRT", shSrt);
       paramsList.put("AGPRP", agPrp);
       paramsList.put("LABPRP", lbPrp);
       paramsList.put("LABSRT", lbSrt);
       paramsList.put("SIZEPRP", szPrp);
       paramsList.put("SIZESRT", szSrt);
       paramsList.put("COLPRP", colPrp);
       paramsList.put("COLSRT", colSrt);
       paramsList.put("CLRPRP", clrPrp);
       paramsList.put("CLRSRT", clrSrt);
       paramsList.put("TRFPRP", trfPrp);
       paramsList.put("TRFSRT", trfSrt);
       paramsList.put("TRFDTEPRP", trfDtePrp);
       paramsList.put("TRFDTESRT", trfDteSrt);
       paramsList.put("PRPSHMAP", prpShMap);
       paramsList.put("PrpWiseGd", prpWiseGd);
       paramsList.put("trfDteFrm", trfDteFrm);
       paramsList.put("trfDteTo", trfDteTo);
       getLabGrid(req, paramsList);
       getAllStockGrid(req, paramsList);
       getPrpWiseGrid(req, paramsList, "SIZE");
       getPrpWiseGrid(req, paramsList, "COL");
       getPrpWiseGrid(req, paramsList, "CLR");
       req.setAttribute("view", "Y");
       req.setAttribute("shape", shape);
             util.updAccessLog(req,res,"Weekly Sale Report", "fetch end");
       return am.findForward("load");
         }
     }
   
  public void getLabGrid(HttpServletRequest req ,  HashMap paramsList){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    String shPrp = (String)paramsList.get("SHPRP");
    String lbPrp = (String)paramsList.get("LABPRP");
    String lbSrt = (String)paramsList.get("LABSRT");
    String agPrp = (String)paramsList.get("AGPRP");
    String trfPrp = (String)paramsList.get("TRFPRP");
    
      String labGrid = "  WITH PRP_DTL as\n" + 
      "    (\n" + 
      "     select dsp_stt dys_grp\n" + 
      "     , count(*) cnt\n" + 
      "     , decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
      "     , "+lbPrp+" prp \n" + 
      "     , count(*) qty, sum(cts) cts\n" + 
      "     , sum(cts*quot) prpVlu\n" + 
      "     , sum(cts*quot*"+agPrp+") dysVlu\n" + 
      "     , sum(cts*quot) QuotVlu\n" + 
      "     , sum(cts*"+trfPrp+") TrfVlu\n" + 
      "     from gt_srch_rslt gt where gt.stt='SOLD'\n" + 
      "     group by dsp_Stt, "+lbPrp+", decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
      "    ), SHP_LAB_SMRY as (\n" + 
      "     Select\n" + 
      "     decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
      "     , "+lbPrp+" prp\n" + 
      "     , count(*) ttlLabCnt\n" + 
      "     , sum(cts*quot) ttlLabVlu\n" + 
      "     from gt_srch_rslt where stt='SOLD'\n" + 
      "     group by "+lbPrp+", decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
      "    ), SHP_SMRY as (\n" + 
      "     Select\n" + 
      "     decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
      "     , count(*) ttlCnt\n" + 
      "     , sum(cts*quot) ttlVlu\n" + 
      "     from gt_srch_rslt where stt='SOLD'\n" + 
      "     group by decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
      "    ), DYS_SMRY as (\n" + 
      "     Select dsp_stt dys_grp\n" + 
      "     , decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
      "     , count(*) ttlDysCnt\n" + 
      "     , sum(cts*quot) ttlDysVlu\n" + 
      "     , sum(cts*quot) TtlQuotVlu\n" + 
      "     , sum(cts*"+trfPrp+") TtlTrfVlu\n" + 
      "     from gt_srch_rslt where  stt='SOLD'\n" + 
      "     group by dsp_stt, decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
      "    )\n" + 
      "    select a.dys_grp, d.ttlLabCnt ttlCnt, a.cnt, a.shp, a.prp, round(a.prpVlu) prpVlu\n" + 
      "     , c.ttlDysCnt ttlDysCnt, round(c.ttlDysVlu) ttlDysVlu, round(c.ttlDysVlu/b.ttlVlu*100) pct, round(a.dysVlu/a.cnt) dys\n" + 
      "     , round((TrfVlu - QuotVlu)/TrfVlu*100) pri_pct, round((TtlTrfVlu - TtlQuotVlu)/TtlTrfVlu*100) pri_avg_pct\n" + 
      "    from PRP_DTL a, SHP_SMRY b, DYS_SMRY c, SHP_LAB_SMRY d\n" + 
      "    where a.shp = b.shp and a.shp = c.shp and a.dys_grp = c.dys_grp\n" + 
      "    and a.shp = d.shp and a.prp = d.prp\n" + 
      "   order by 1, a.shp desc, a.prp ";
   ArrayList outLst = db.execSqlLst("labGrid", labGrid, new ArrayList());
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
    HashMap labWsDataMap = new HashMap();
    ArrayList daysLabList = new ArrayList();
    try {
           while (rs.next()) {
               String cnt  = util.nvl(rs.getString("cnt"));
               String sh = util.nvl(rs.getString("SHP"));
               String lab = util.nvl(rs.getString("prp"));
               String dys_grp = util.nvl(rs.getString("dys_grp"));
               String prpVlu = util.nvl(rs.getString("ttlDysVlu"));
              String ttlCnt = util.nvl(rs.getString("ttlCnt"));
              String ttlDysCnt= util.nvl(rs.getString("ttlDysCnt"));
              String pct = util.nvl(rs.getString("pct"));
              String pri_pct = util.nvl(rs.getString("pri_pct"));
              String pri_avg_pct = util.nvl(rs.getString("pri_avg_pct"));
               labWsDataMap.put(dys_grp+"_"+sh+"_"+lab+"_CNT", cnt);
               labWsDataMap.put(dys_grp+"_"+sh+"_"+lab+"_SH", sh);
               labWsDataMap.put(dys_grp+"_"+sh+"_VAL", prpVlu);
              labWsDataMap.put(dys_grp+"_"+sh+"_PCT", pct);
              labWsDataMap.put(dys_grp+"_"+sh+"_PRI", pri_pct);
              labWsDataMap.put(dys_grp+"_"+sh+"_TTLCNT", ttlDysCnt);
               if(!daysLabList.contains(dys_grp))
                   daysLabList.add(dys_grp);
              labWsDataMap.put(sh+"_"+lab+"_CNT", ttlCnt);
              labWsDataMap.put(sh+"_AVG_PRI", pri_avg_pct);
             

            }
        rs.close();
        pst.close();
       } catch (SQLException sqle) {
           // TODO: Add catch code
           sqle.printStackTrace();
       }
    
    session.setAttribute("LabWsDataMap", labWsDataMap);
     session.setAttribute("DaysLabList", daysLabList);
  }
  
  public void getPrpWiseGrid(HttpServletRequest req ,  HashMap paramsList , String lprp){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    String shPrp = (String)paramsList.get("SHPRP");
    String agPrp = (String)paramsList.get("AGPRP");
    String trfDtePrp = (String)paramsList.get("TRFDTEPRP");
    String lprpPrp = (String)paramsList.get(lprp+"PRP");
    String lprpSrt = (String)paramsList.get(lprp+"SRT");
    String trfDteFrm = (String)paramsList.get("trfDteFrm");
    String trfDteTo = (String)paramsList.get("trfDteTo");
     HashMap prpShMap = (HashMap)paramsList.get("PRPSHMAP");
    String getprpSql ="WITH PRP_DTL as\n" + 
    "  (\n" + 
    "   select\n" + 
    "    count(*) cnt\n" + 
    "	, gt.flg flg\n" + 
    "   , decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
    "\n" + 
    "   , p.grp prp\n" + 
    "   , count(*) qty, sum(cts) cts\n" + 
    "   , sum(cts*quot) prpVlu\n" + 
    "   , sum(cts*quot*"+agPrp+") dysVlu\n" + 
    "   from gt_srch_rslt gt, PRP P \n" + 
    "   where gt."+lprpPrp+" = p.val and p.mprp = ? and gt.stt='SOLD' \n" + 
    "   group by gt.flg, p.grp, decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
    "  ), SHP_FLG_SMRY as (\n" + 
    "   Select\n" + 
    "   flg\n" + 
    "   , count(*) flg_cnt\n" + 
    "   , decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
    "   , sum(cts*quot) ttlFlgVlu\n" + 
    "   , sum(cts*quot*"+agPrp+") ttlFlgDysvlu\n" + 
    "\n" + 
    "   from gt_srch_rslt where stt='SOLD' \n" + 
    "   group by flg, decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
    "  ), SHP_SMRY as (\n" + 
    "   Select\n" + 
    "    decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
    "   , sum(cts*quot) ttlVlu\n" + 
    "   , sum(cts*quot*"+agPrp+") ttlDysvlu\n" + 
    "   , sum(cts*quot*decode("+trfDtePrp+", null, 0, \n" + 
    "	CASE \n" + 
    "		WHEN "+trfDtePrp+" between to_date('"+trfDteFrm+"', 'dd-mm-rrrr') and to_date('"+trfDteTo+"', 'dd-mm-rrrr') Then 1\n" + 
    "		ELSE 0\n" + 
    "		END)) nwGdsTtlVlu\n" + 
    "   from gt_srch_rslt \n" + 
    "   group by decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
    "  ), NW_GDS as (\n" + 
    "   select\n" + 
    "	decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
    "\n" + 
    "   , p.grp prp\n" + 
    "   , sum(cts*quot) nwGdsVlu\n" + 
    "   from gt_srch_rslt gt, PRP P \n" + 
    "   where "+trfDtePrp+" is not null\n" + 
    "	and gt."+lprpPrp+" = p.val and p.mprp = ? \n" + 
    "    and decode("+trfDtePrp+", null, sysdate, to_date("+trfDtePrp+", 'dd-MON-rrrr'))\n" + 
    "    between to_date('"+trfDteFrm+"', 'dd-mm-rrrr') and to_date('"+trfDteTo+"', 'dd-mm-rrrr')\n" + 
    "	group by p.grp, decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
    "  )\n" + 
    "  select decode(a.flg, 'Z', 'Stock', 'W', 'P1', 'S', 'P2') flg, a.shp, cnt, a.prp\n" + 
    "  , flg_cnt pcs, round(prpVlu/ttlFlgVlu*100) pct, round(nwGdsVlu/nwGdsTtlVlu*100) add_pct\n" + 
    "  , round(dysVlu/prpVlu) dys, round(ttlDysVlu/ttlVlu) avgDys \n" + 
    "  from PRP_DTL a, SHP_FLG_SMRY f, SHP_SMRY b, NW_GDS c\n" + 
    "  where a.shp = f.shp and a.flg = f.flg \n" + 
    "  and a.shp = b.shp and a.shp = c.shp and a.prp = c.prp\n" + 
    "order by 1 desc, a.shp, a.prp";
    ArrayList params = new ArrayList();
    params.add(lprp);
   
    params.add(lprp);
  
    
    ArrayList outLst = db.execSqlLst("fancySql", getprpSql, params);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
    HashMap prpWiseGd =(HashMap)paramsList.get("PrpWiseGd");
   
  
      try {
            while (rs.next()) {
             String flg = util.nvl(rs.getString("flg"));
              String cnt = util.nvl(rs.getString("cnt"));
              String sh = util.nvl(rs.getString("SHP"));
              String prp = util.nvl(rs.getString("prp"));
              String pcs = util.nvl(rs.getString("PCS"));
              String add_pct = util.nvl(rs.getString("add_pct"));
              String pct = util.nvl(rs.getString("pct"));
              String dys = util.nvl(rs.getString("dys"));
              String avgdys = util.nvl(rs.getString("avgdys"));
                
              prpWiseGd.put(sh+"_"+flg+"_"+prp+"_CNT", cnt);
              prpWiseGd.put(sh+"_"+flg+"_"+prp+"_VAL", prp);
              prpWiseGd.put(sh+"_"+flg+"_"+prp+"_DYS", dys);
             prpWiseGd.put(sh+"_"+flg+"_"+prp+"_PCT", pct);
             prpWiseGd.put(sh+"_"+flg+"_"+prp+"_ADD_PCT", add_pct);
            
              ArrayList prpList = (ArrayList)prpShMap.get(sh+"_"+lprp);
              if(prpList==null)
                  prpList = new ArrayList();
              if(!prpList.contains(prp))
                  prpList.add(prp);
                prpShMap.put(sh+"_"+lprp, prpList);
             prpWiseGd.put(sh+"_"+flg+"_"+lprp+"_TTLPCS",pcs);
             prpWiseGd.put(sh+"_"+flg+"_"+lprp+"_TTLAVGDAY", avgdys);
           
           }
          rs.close();
          pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      
      String asPrct = "WITH ASRT_STK as (\n" + 
      "    select\n" + 
      "            decode(sh.val, 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
      "            , p.grp prp\n" + 
      "            , sum(cts*nvl(upr, cmp)) asrtVlu\n" + 
      "    from mstk m, df_stk_stt df, stk_dtl sh, stk_dtl dept, stk_dtl prp, prp p\n" + 
      "    where 1 = 1 and m.pkt_ty = 'NR' and m.stt = df.stt and df.grp1 in ('ASRT', 'LAB', 'LABOK')\n" + 
      "    and m.idn = sh.mstk_idn and sh.grp = 1 and sh.mprp = 'SHAPE'\n" + 
      "    and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = ? \n" + 
      "    and m.idn = prp.mstk_idn and prp.grp = 1 and prp.mprp = ? and prp.mprp = p.mprp and prp.val = p.val\n" + 
      "    group by p.grp, decode(sh.val, 'ROUND', 'ROUND', 'FANCY')\n" + 
      "   ), ASRT_SMRY as (\n" + 
      "    select\n" + 
      "            decode(sh.val, 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
      "            , sum(cts*nvl(upr, cmp)) asrtTtlVlu\n" + 
      "    from mstk m, df_stk_stt df, stk_dtl sh, stk_dtl dept\n" + 
      "    where 1 = 1 and m.pkt_ty = 'NR' and m.stt = df.stt and df.grp1 in ('ASRT', 'LAB', 'LABOK')\n" + 
      "    and m.idn = sh.mstk_idn and sh.grp = 1 and sh.mprp = 'SHAPE'\n" + 
      "    and m.idn = dept.mstk_idn and dept.grp = 1 and dept.mprp = 'DEPT' and dept.val = ? \n" + 
      "    group by decode(sh.val, 'ROUND', 'ROUND', 'FANCY')\n" + 
      "   )\n" + 
      " select a.shp, a.prp, round(asrtVlu/asrtTtlVlu*100) asrt_pct\n" + 
      " from asrt_stk a, asrt_smry b\n" + 
      " where a.shp = b.shp ";
       params = new ArrayList();
       params.add(paramsList.get("DEPT"));
       params.add(lprp);
       params.add(paramsList.get("DEPT"));
        try {
            outLst = db.execSqlLst("fancySql", asPrct, params);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                String shp = util.nvl(rs.getString("shp"));
                String prp = util.nvl(rs.getString("prp"));
                String asrt_pct = util.nvl(rs.getString("asrt_pct"));
                prpWiseGd.put(shp+"_"+prp+"_ASRTPCT", asrt_pct);

            }
          rs.close();
          pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      
      session.setAttribute("PrpWiseGd", prpWiseGd);
      session.setAttribute("PrpShMap", prpShMap);
        
  }
  
  public void getAllStockGrid(HttpServletRequest req ,  HashMap paramsList){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    String shPrp = (String)paramsList.get("SHPRP");
    String agPrp = (String)paramsList.get("AGPRP");
    String labPrp = (String)paramsList.get("LABPRP");
    String labSrt = (String)paramsList.get("LABSRT");
    String trfDtePrp = (String)paramsList.get("TRFDTEPRP");
    String trfDteFrm = (String)paramsList.get("trfDteFrm");
    String trfDteTo = (String)paramsList.get("trfDteTo");
    HashMap allStockMap = new HashMap();
    ArrayList allStkPrpList = new ArrayList();
    String allStock ="WITH PRP_DTL as\n" + 
    "  (\n" + 
    "   select\n" + 
    "   count(*) cnt\n" + 
    "   , gt.flg \n" + 
    "   , decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
    "   , decode(greatest(rap_rte, 1), 1, 'FANCY COLOR', decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')||' '||"+labPrp+") prp\n" + 
    "   , count(*) qty, sum(cts) cts\n" + 
    "   , sum(cts*quot) prpVlu\n" + 
    "   , sum(cts*quot*"+agPrp+") dysVlu\n" + 
    "   from gt_srch_rslt gt where rap_rte > 1 and gt.stt='SOLD'\n" + 
    "   group by gt.flg, decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
    "	, decode(greatest(rap_rte, 1), 1, 'FANCY COLOR', decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')||' '||"+labPrp+")\n" + 
    "  ), SHP_SMRY as (\n" + 
    "   Select\n" + 
    "   decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
    "   , sum(cts*quot) ttlVlu\n" + 
    "   , sum(cts*quot*"+agPrp+") ttlDysvlu\n" + 
    "   , sum(cts*quot*decode("+trfDtePrp+", null, 0, \n" + 
    "	CASE \n" + 
    "		WHEN "+trfDtePrp+" between to_date(?, 'dd-mm-rrrr') and to_date(?, 'dd-mm-rrrr') Then 1\n" + 
    "		ELSE 0\n" + 
    "		END)) nwGdsTtlVlu\n" + 
    "   from gt_srch_rslt \n" + 
    "   group by decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
    "  ), SHP_FLG_SMRY as (\n" + 
    "   Select\n" + 
    "   flg\n" + 
    "   , count(*) flg_cnt\n" + 
    "   , decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
    "   , sum(cts*quot) ttlFlgVlu\n" + 
    "   , sum(cts*quot*"+agPrp+") ttlFlgDysvlu\n" + 
    "   from gt_srch_rslt where stt='SOLD'\n" + 
    "   group by flg, decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
    "  ) , NW_GDS as (\n" + 
    "   select\n" + 
    "	decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
    "   , sum(cts*quot) nwGdsVlu\n" + 
    "   , decode(greatest(rap_rte, 1), 1, 'FANCY COLOR', decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')||' '||"+labPrp+") prp\n" + 
    "   from gt_srch_rslt gt\n" + 
    "   where "+trfDtePrp+" is not null\n" + 
    "    and decode("+trfDtePrp+", null, sysdate, to_date("+trfDtePrp+", 'dd-MON-rrrr'))\n" + 
    "    between to_date(?, 'dd-mm-rrrr') and to_date(?, 'dd-mm-rrrr')\n" + 
    "	group by decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
    "		, decode(greatest(rap_rte, 1), 1, 'FANCY COLOR', decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')||' '||"+labPrp+")\n" + 
    "  )\n" + 
    "  select decode(a.flg, 'Z', 'Stock', 'W', 'P1', 'S', 'P2') flg\n" + 
    "  ,cnt,flg_cnt pcs , a.shp, a.prp, round(prpVlu) prpVlu, round(dysVlu) dysVlu, round(prpVlu/ttlFlgVlu*100) pct\n" + 
    "  , round(dysVlu/prpVlu) dys, round(ttlDysVlu/ttlVlu) avgDys \n" + 
    "  , round(nwGdsVlu/nwGdsTtlVlu*100) add_pct\n" + 
    " from PRP_DTL a, SHP_SMRY b, NW_GDS c, SHP_FLG_SMRY f\n" + 
    " where a.shp = b.shp and a.shp = c.shp and a.prp = c.prp\n" + 
    " and a.shp = f.shp and a.flg = f.flg\n" + 
  
    " order by a.flg, a.shp desc, a.prp desc\n";
    ArrayList params = new ArrayList();
    params.add(trfDteFrm);
    params.add(trfDteTo);
    params.add(trfDteFrm);
    params.add(trfDteTo);
    
    ArrayList outLst = db.execSqlLst("fancySql", allStock, params);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
    
    try {
            while (rs.next()) {
             
              String flg = util.nvl(rs.getString("flg"));
               String cnt = util.nvl(rs.getString("cnt"));
               String sh = util.nvl(rs.getString("SHP"));
               String prp = util.nvl(rs.getString("prp"));
               String pcs = util.nvl(rs.getString("PCS"));
               String add_pct = util.nvl(rs.getString("add_pct"));
               String pct = util.nvl(rs.getString("pct"));
               String dys = util.nvl(rs.getString("dys"));
               String avgdys = util.nvl(rs.getString("avgdys"));
                 
               allStockMap.put(sh+"_"+flg+"_"+prp+"_CNT", cnt);
               allStockMap.put(sh+"_"+flg+"_"+prp+"_VAL", prp);
               allStockMap.put(sh+"_"+flg+"_"+prp+"_DYS", dys);
               allStockMap.put(sh+"_"+flg+"_"+prp+"_PCT", pct);
               allStockMap.put(sh+"_"+flg+"_"+prp+"_ADD_PCT", add_pct);
              allStockMap.put(sh+"_"+flg+"_TTLPCS",pcs);
              allStockMap.put(sh+"_"+flg+"_TTLAVGDAY", avgdys);
              
              
            }
          rs.close();
        pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    allStkPrpList.add("SH GIA");
    allStkPrpList.add("SH IGI");
    allStkPrpList.add("FANCY COLOR");
    session.setAttribute("AllStockMap", allStockMap);
    session.setAttribute("AllStkPrpList", allStkPrpList);
  }
    public ActionForward pktDtlExcel(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Weekly Sale Report", "pktDtlExcel start");
        WeeklySaleReportForm udf = (WeeklySaleReportForm)form;
        GenericInterface genericInf = new GenericImpl();
        String selectsh = util.nvl(req.getParameter("sh"));
        String labsh = util.nvl(req.getParameter("labsh"));
        String day = util.nvl(req.getParameter("days"));
        String lab = util.nvl(req.getParameter("lab"));
        String grplprp = util.nvl(req.getParameter("grplprp"));
        String grpval = util.nvl(req.getParameter("grpval"));
        String grpsh = util.nvl(req.getParameter("grpsh"));
        String allsh = util.nvl(req.getParameter("allsh"));
        String alllab = util.nvl(req.getParameter("alllab"));
        String flg = util.nvl(req.getParameter("flg"));
        ArrayList itemHdr = new ArrayList();
        ArrayList ary = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        ArrayList vWPrpList = genericInf.genericPrprVw(req, res, "STK_SMM_RPT", "STK_SMM_RPT");
        HashMap dbinfo = info.getDmbsInfoLst();
        String sh = (String)dbinfo.get("SHAPE");
        String szval = (String)dbinfo.get("SIZE");
        String colval = (String)dbinfo.get("COL");
        String clrval = (String)dbinfo.get("CLR");
        String labval = (String)dbinfo.get("LAB");
        String locval = (String)dbinfo.get("LOC");
        int indexTrfCmp = vWPrpList.indexOf("TRF_CMP")+1;
        int indexLB = vWPrpList.indexOf(labval)+1;
        int indexSH = vWPrpList.indexOf(sh)+1;
        int indexSZ = vWPrpList.indexOf(szval)+1;
        int indexCol = vWPrpList.indexOf(colval)+1;
        int indexClr = vWPrpList.indexOf(clrval)+1;
        int indexloc = vWPrpList.indexOf(locval)+1;
        String trfCmpPrp = util.prpsrtcolumn("prp",indexTrfCmp);
        String lbPrp = util.prpsrtcolumn("prp",indexLB);
        String shPrp =  util.prpsrtcolumn("prp",indexSH);
        String szPrp =  util.prpsrtcolumn("prp",indexSZ);
        String colPrp =  util.prpsrtcolumn("prp",indexCol);
        String clrPrp = util.prpsrtcolumn("prp",indexClr);
        String locPrp = util.prpsrtcolumn("prp",indexloc);
        String lbSrt = util.prpsrtcolumn("srt",indexLB);
        String shSrt = util.prpsrtcolumn("srt",indexSH);
        String szSrt = util.prpsrtcolumn("srt",indexSZ);
        String colSrt = util.prpsrtcolumn("srt",indexCol);
        String clrSrt = util.prpsrtcolumn("srt",indexClr);
        String locSrt =util.prpsrtcolumn("srt",indexloc);
        ArrayList pktList = new ArrayList();
        String conQ="";
        int count=1;
        itemHdr.add("SrNo");
        itemHdr.add("vnm");
        itemHdr.add("qty");
        itemHdr.add("cts");
        String srchQ =" select stk_idn , pkt_ty, vnm, pkt_dte, stt , qty , cts , cert_lab,quot ,rap_rte, rmk ,kts_vw kts,cmnt,img2, (cts*quot) amt, "+
            "decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis,trunc(((cmp*100)/greatest(rap_rte,1)) - 100,2) cmp_dis,trunc(((nvl("+trfCmpPrp+",quot)/greatest(rap_rte,1))*100)-100,2) trf_back";

            for (int i = 0; i < vWPrpList.size(); i++) {
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
        String rsltQ = srchQ + " from gt_srch_rslt";
        if(grplprp.equals("") && grpval.equals("") && grpsh.equals("")){
            rsltQ+=" where 1=1";   
        }else{
            rsltQ+=",prp b where 1=1 ";  
        }
        if(!selectsh.equals("") && !selectsh.equals("ALL") && !selectsh.equals("FANCY")){
            rsltQ+=" and "+shPrp+"=? ";
            ary.add(selectsh);
        }else if(selectsh.equals("FANCY")){
            rsltQ+=" and decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')=?";  
            ary.add(selectsh);
        }
        
        if(!day.equals("")  && !day.equals("ALL")){
            rsltQ+=" and dsp_stt='"+day+"'";  
        }
        if(!labsh.equals("") && !labsh.equals("ALL")){
            rsltQ+=" and decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')=?";  
            ary.add(labsh);
        }
        if(!lab.equals("") && !lab.equals("ALL")){
            rsltQ+=" and "+lbPrp+"=? ";
            ary.add(lab);
        }
        if(!grplprp.equals("") && !grpval.equals("") && !grpsh.equals("")){
            int indexgrplprp = vWPrpList.indexOf(grplprp)+1;
            String grpPrp = util.prpsrtcolumn("prp",indexgrplprp);
            if(!grpsh.equals("") && !grpsh.equals("ALL")){
                rsltQ+=" and decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')=?";  
                ary.add(grpsh);
            }
            rsltQ+=" and b.mprp=? and "+grpPrp+"=b.val and gt_srch_rslt.flg=? ";
            ary.add(grplprp);
            ary.add(flg);
            
            if(!grpval.equals("") && !grpval.equals("ALL"))
            rsltQ+=" and b.grp='"+grpval+"' "; 
        }
        if(!allsh.equals("") && !allsh.equals("ALL")){
            rsltQ+=" and decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')=? and gt_srch_rslt.flg=?";  
            ary.add(allsh);
            ary.add(flg);
            
        }
        if(!alllab.equals("") && !alllab.equals("ALL") && !alllab.equals("FANCY COLOR")){
            rsltQ+=" and "+lbPrp+"=? and rap_rte > 1 ";
            ary.add(alllab.substring(alllab.indexOf(" ")+1,alllab.length()));
        }else if(alllab.equals("FANCY COLOR")){
            rsltQ+=" and rap_rte <= 1 ";
        }
        rsltQ=rsltQ+conQ+" order by sk1,stk_idn,cts";
          ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet rs = (ResultSet)outLst.get(1);
        try {
        while (rs.next()) {
        String stkIdn = util.nvl(rs.getString("stk_idn"));
        HashMap pktPrpMap = new HashMap();
        String vnm = util.nvl(rs.getString("vnm"));
        pktPrpMap.put("SrNo", String.valueOf(count++));
        pktPrpMap.put("vnm", vnm);
        pktPrpMap.put("stk_idn", stkIdn);
        pktPrpMap.put("STT", util.nvl(rs.getString("stt")));
        pktPrpMap.put("qty", util.nvl(rs.getString("qty")));
        pktPrpMap.put("cts", util.nvl(rs.getString("cts")));
        pktPrpMap.put("RAP_DIS", util.nvl(rs.getString("r_dis")));
        pktPrpMap.put("Rte", util.nvl(rs.getString("quot")));
        pktPrpMap.put("Amount", util.nvl(rs.getString("amt")));
        pktPrpMap.put("TRF_BACK", util.nvl(rs.getString("trf_back")));
        for (int j = 0; j < vWPrpList.size(); j++) {
        String prp = (String)vWPrpList.get(j);

        String fld = "prp_";
        if (j < 9)
        fld = "prp_00" + (j + 1);
        else
        fld = "prp_0" + (j + 1);

        String val = util.nvl(rs.getString(fld));
            if (prp.toUpperCase().equals("CRTWT"))
                val = util.nvl(rs.getString("cts"));
            if (prp.toUpperCase().equals("RAP_DIS"))
                val = util.nvl(rs.getString("r_dis"));
            if (prp.toUpperCase().equals("CMP_DIS")){
                if(val.equals(""))
                val = util.nvl(rs.getString("cmp_dis")); 
            }
            if (prp.toUpperCase().equals("RAP_RTE"))
                val = util.nvl(rs.getString("rap_rte"));
            if(prp.equalsIgnoreCase("MEM_COMMENT"))
                val = util.nvl(rs.getString("img2"));
            if(prp.equals("RTE"))
                val = util.nvl(rs.getString("quot"));
            if(prp.equals("KTSVIEW"))
                val = util.nvl(rs.getString("kts"));
            if(prp.equals("COMMENT"))
                val = util.nvl(rs.getString("cmnt"));
            if(prpDspBlocked.contains(prp)){
            }else if(!itemHdr.contains(prp)){
            itemHdr.add(prp);
            }
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
        itemHdr.add("TRF_BACK");
        itemHdr.add("Rte");
        itemHdr.add("RAP_DIS");
        itemHdr.add("Amount");
        itemHdr.add("STT");
        session.setAttribute("pktList", pktList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Weekly Sale Report", "pktDtlExcel end");
        return am.findForward("pktDtl");  
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
          util.updAccessLog(req,res,"Weekly Sale Report", "createXL start");
      ExcelUtil xlUtil = new ExcelUtil();
      xlUtil.init(db, util, info);
      OutputStream out = res.getOutputStream();
      String CONTENT_TYPE = "getServletContext()/vnd-excel";
      String shape = util.nvl((String)req.getParameter("shape"));
      String fileNm = "WeeklyStockReportXL"+util.getToDteTime()+".xls";
      res.setContentType(CONTENT_TYPE);
      res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
      
      HSSFWorkbook hwb = xlUtil.WeeklySaleReportXL(req,shape);
      hwb.write(out);
      out.flush();
      out.close();
          util.updAccessLog(req,res,"Weekly Sale Report", "createXL end");
     return null;
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
                util.updAccessLog(req,res,"Weekly Sale Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Weekly Sale Report", "init");
            }
            }
            return rtnPg;
            }
}
