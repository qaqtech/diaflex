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

public class WeeklyStockReportAction extends DispatchAction {

     
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
          util.updAccessLog(req,res,"Weekly Stock Report", "load start");
      WeeklyStockReportForm udf = (WeeklyStockReportForm)af;
      udf.resetAll();
          util.updAccessLog(req,res,"Weekly Stock Report", "load end");
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
            util.updAccessLog(req,res,"Weekly Stock Report", "fetch start");
      WeeklyStockReportForm udf = (WeeklyStockReportForm)af;
      GenericInterface genericInf = new GenericImpl();
      String shape = util.nvl((String)udf.getValue("shape"));  
      db.execUpd("delete gt", "delete from gt_srch_rslt", new ArrayList());
      String dept = udf.getDept();
      ArrayList params = new ArrayList();
      params.add(dept);
      params.add(shape);
      int ct = db.execCall("weekly", "REPORT_PKG.WEEKLY_SMRY( pDept => ?,pShp =>?)", params);
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
      
      HashMap prpShMap = new HashMap();
      HashMap prpWiseGd = new HashMap();
      
      HashMap paramsList = new HashMap();
      paramsList.put("vWList", vWPrpList);
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
      paramsList.put("PRPSHMAP", prpShMap);
      paramsList.put("PrpWiseGd", prpWiseGd);
      getMstGrid(req, paramsList);
      getLabGrid(req, paramsList);
      getAllStockGrid(req, paramsList);
      getpridiffGrid(req, paramsList);
      getShapeWiseGrid(req, paramsList);
      getPrpWiseGrid(req, paramsList, "SIZE");
      getPrpWiseGrid(req, paramsList, "COL");
      getPrpWiseGrid(req, paramsList, "CLR");
      req.setAttribute("view", "Y");
      req.setAttribute("shape", shape);
            util.updAccessLog(req,res,"Weekly Stock Report", "fetch end");
      return am.findForward("load");
        }
    }
    public WeeklyStockReportAction() {
        super();
    }
    
    public void getMstGrid(HttpServletRequest req , HashMap paramsList){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      ArrayList vWPrpList = (ArrayList)paramsList.get("vWList");
      String shPrp = (String)paramsList.get("SHPRP");
      String shSrt = (String)paramsList.get("SHSRT");
      String agPrp = (String)paramsList.get("AGPRP");
     
      ArrayList dayGrpList = new ArrayList();
      ArrayList shList = new ArrayList();
      HashMap dys_ShMap = new HashMap();
      HashMap shTtlMap = new HashMap();
      int ttlPcs = 0;
        try {
            String dayShapeG =
                " select nvl(dsp_stt, '0-15') dys_grp, " + shSrt + " , " +shPrp +
                " sh, count(*) cnt, sum(cts) cts, sum(cts*quot) vlu, sum(cts*quot*" +agPrp + ") dys_vlu \n" +
                "from gt_srch_rslt where flg='Z' group by nvl(dsp_stt, '0-15'), " + shSrt +
                ", " + shPrp + " order by 1, " + shSrt + "";
            ArrayList outLst =
                db.execSqlLst("dayShGD", dayShapeG, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
          
            int ttlPcDay = 0;
            while (rs.next()) {
                String dys_grp = util.nvl(rs.getString("dys_grp"));
                String sh = util.nvl(rs.getString("sh"));
                String cnt = util.nvl(rs.getString("cnt"));
                if (!dayGrpList.contains(dys_grp))
                    dayGrpList.add(dys_grp);
                if (!shList.contains(sh))
                    shList.add(sh);
                dys_ShMap.put(dys_grp + "_" + sh, cnt);
            }
            rs.close();
            pst.close();
            
            String shTotal =
                " select count (*) cnt , " + shSrt + " , " + shPrp +
                " sh from  gt_srch_rslt where flg='Z'  group by " + shSrt + " , " + shPrp;
            outLst = db.execSqlLst("dayShGD", shTotal, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            
            while (rs.next()) {
                String sh = util.nvl(rs.getString("sh"));
                String cnt = util.nvl(rs.getString("cnt"));
                shTtlMap.put(sh, cnt);
                ttlPcs = ttlPcs + Integer.parseInt(cnt);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (NumberFormatException nfe) {
            // TODO: Add catch code
            nfe.printStackTrace();
        }
      session.setAttribute("ttlShMap", shTtlMap);
      session.setAttribute("ttlPcs", ttlPcs);
      session.setAttribute("DayShMap", dys_ShMap);
      session.setAttribute("dayGrpList", dayGrpList);
      session.setAttribute("shList", shList);
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
//       String labGrid = "WITH PRP_DTL as\n" + 
//       "  (\n" + 
//       "   select dsp_stt dys_grp\n" + 
//       "   , count(*) cnt\n" + 
//       "   , decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
//       "   , "+lbPrp+" prp \n" + 
//       "   , count(*) qty, sum(cts) cts\n" + 
//       "   , sum(cts*quot) prpVlu\n" + 
//       "   , sum(cts*quot*"+agPrp+") dysVlu\n" + 
//       "   from gt_srch_rslt gt where  gt.flg='Z'\n" + 
//       "   group by dsp_Stt, "+lbPrp+", decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
//       "  ), SHP_SMRY as (\n" + 
//       "   Select\n" + 
//       "   decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
//       "   , count(*) ttlCnt\n" + 
//       "   , sum(cts*quot) ttlVlu\n" + 
//      
//       "   from gt_srch_rslt  where  flg='Z'\n" + 
//       "   group by decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
//       "  ), DYS_SMRY as (\n" + 
//       "   Select dsp_stt dys_grp\n" + 
//       "   , decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
//       "   , count(*) ttlDysCnt\n" + 
//       "   , sum(cts*quot) ttlDysVlu\n" + 
//       "   from gt_srch_rslt where flg='Z' \n" + 
//       "   group by dsp_stt, decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
//       "  )\n" + 
//       "  select a.dys_grp, b.ttlCnt, a.cnt, a.shp, a.prp, round(a.prpVlu) prpVlu\n" + 
//       "	, c.ttlDysCnt ttlDysCnt, round(c.ttlDysVlu) ttlDysVlu, round(c.ttlDysVlu/b.ttlVlu*100) pct, round(a.dysVlu/a.cnt) dys\n" + 
//       "  from PRP_DTL a, SHP_SMRY b, DYS_SMRY c\n" + 
//       "  where a.shp = b.shp and a.shp = c.shp and a.dys_grp = c.dys_grp\n" + 
//       "  order by 1, a.shp desc, a.prp";
     String labGrid="WITH PRP_DTL as\n" + 
     "  (\n" + 
     "   select dsp_stt dys_grp\n" + 
     "   , count(*) cnt\n" + 
     "   , decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
     "   , "+lbPrp+" prp--, "+lbSrt+" srt\n" + 
     "   , count(*) qty, sum(cts) cts\n" + 
     "   , sum(cts*quot) prpVlu\n" + 
     "   , sum(cts*quot*"+agPrp+") dysVlu\n" + 
     "   from gt_srch_rslt gt\n" + 
     "   group by dsp_Stt, "+lbPrp+", decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
     "  ), SHP_LAB_SMRY as (\n" + 
     "   Select\n" + 
     "   decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
     "   , "+lbPrp+" prp\n" + 
     "   , count(*) ttlLabCnt\n" + 
     "   , sum(cts*quot) ttlLabVlu\n" + 
     "   --, sum(cts*quot*"+agPrp+") ttlDysvlu\n" + 
     "   from gt_srch_rslt\n" + 
     "   group by "+lbPrp+", decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
     "  ) , SHP_SMRY as (\n" + 
     "   Select\n" + 
     "   decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
     "   , count(*) ttlCnt\n" + 
     "   , sum(cts*quot) ttlVlu\n" + 
     "   --, sum(cts*quot*"+agPrp+") ttlDysvlu\n" + 
     "   from gt_srch_rslt\n" + 
     "   group by decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
     "  ), DYS_SMRY as (\n" + 
     "   Select dsp_stt dys_grp\n" + 
     "   , decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
     "   , count(*) ttlDysCnt\n" + 
     "   , sum(cts*quot) ttlDysVlu\n" + 
     "   from gt_srch_rslt\n" + 
     "   group by dsp_stt, decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
     "  )\n" + 
     "  select a.dys_grp, l.ttlLabCnt ttlcnt, a.cnt, a.shp, a.prp, round(a.prpVlu) prpVlu\n" + 
     ", c.ttlDysCnt ttlDysCnt, round(c.ttlDysVlu) ttlDysVlu, round(c.ttlDysVlu/b.ttlVlu*100) pct, round(a.dysVlu/a.cnt) dys\n" + 
     "  from PRP_DTL a, SHP_SMRY b, DYS_SMRY c, SHP_LAB_SMRY l\n" + 
     "  where a.shp = b.shp and a.shp = c.shp and a.dys_grp = c.dys_grp\n" + 
     "  and a.shp = l.shp and a.prp = l.prp\n" + 
     "  order by 1, a.shp desc, a.prp\n";
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
            
                labWsDataMap.put(dys_grp+"_"+sh+"_"+lab+"_CNT", cnt);
                labWsDataMap.put(dys_grp+"_"+sh+"_"+lab+"_SH", sh);
                labWsDataMap.put(dys_grp+"_"+sh+"_VAL", prpVlu);
               labWsDataMap.put(dys_grp+"_"+sh+"_PCT", pct);
               labWsDataMap.put(dys_grp+"_"+sh+"_TTLCNT", ttlDysCnt);
                if(!daysLabList.contains(dys_grp))
                    daysLabList.add(dys_grp);
               labWsDataMap.put(sh+"_"+lab+"_CNT", ttlCnt);
              

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
    public void getShapeWiseGrid(HttpServletRequest req ,  HashMap paramsList){
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
        String shSrt = (String)paramsList.get("SHSRT");
        String agPrp = (String)paramsList.get("AGPRP");
        String fancySql = "\n" + 
        " WITH PRP_DTL as\n" + 
        "  (\n" + 
        "   select\n" + 
        "    count(*) cnt, 'FANCY' SHP\n" + 
        "   , "+shPrp+" prp, "+shSrt+" srt\n" + 
        "   , count(*) qty, sum(cts) cts\n" + 
        "   , sum(cts*quot) prpVlu\n" + 
        "   , sum(cts*quot*"+agPrp+") dysVlu\n" + 
        "   from gt_srch_rslt gt  where "+shPrp+" <> 'ROUND' and gt.flg='Z'\n" + 
        "   group by "+shSrt+", "+shPrp+", decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
        "  ), SHP_SMRY as (\n" + 
        "   Select\n" + 
        "   decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
        "   , sum(cts*quot) ttlVlu\n" + 
        "   , sum(cts*quot*"+agPrp+") ttlDysvlu\n" + 
        "   from gt_srch_rslt where "+shPrp+" <> 'ROUND' and flg='Z'\n" + 
        "   group by decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
        "  )\n" + 
        "  select srt,a.shp , prp, round(prpVlu) prpVlu, round(ttlVlu) ttlVlu , round(dysVlu) dysVlu, round(ttlDysVlu) ttlDysVlu , round(prpVlu/ttlVlu*100) pct,round(ttlDysVlu/ttlVlu) ttlDys , round(dysVlu/prpVlu) dys\n" + 
        " from prp_dtl a, shp_smry b\n" + 
        " where a.shp = b.shp order by srt ";
      ArrayList outLst = db.execSqlLst("fancySql", fancySql, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      HashMap fncyShMap = new HashMap();
      ArrayList fncyShList = new ArrayList();
        try {
            while (rs.next()) {
              
                String sh = util.nvl(rs.getString("SHP"));
                String prp = util.nvl(rs.getString("prp"));
                String prpVlu = util.nvl(rs.getString("prpVlu"));
                String dysVlu = util.nvl(rs.getString("dysVlu"));
                  String pct = util.nvl(rs.getString("pct"));
                String dys = util.nvl(rs.getString("dys"));
                String ttlVal = util.nvl(rs.getString("ttlVlu"));
                String ttlDysVal = util.nvl(rs.getString("ttlDysvlu"));
               String ttlDys = util.nvl(rs.getString("ttlDys"));
               
                fncyShMap.put(sh+"_"+prp+"_VAL", prpVlu);
                fncyShMap.put(sh+"_"+prp+"_DYSVAL", dysVlu);
                  fncyShMap.put(sh+"_"+prp+"_PCT", pct);
              fncyShMap.put(sh+"_"+prp+"_DYS", dys);
                fncyShMap.put(sh+"_TTLVAL", ttlVal);
                fncyShMap.put(sh+"_TTLDAYVAL", ttlDysVal);
                  fncyShMap.put(sh+"_TTLDYS", ttlDys);
                if(!fncyShList.contains(prp))
                    fncyShList.add(prp);
            }
          rs.close();
          pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
       
      session.setAttribute("FncyShMap", fncyShMap);
      session.setAttribute("FncyShList", fncyShList);
     
      
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
    String lprpPrp = (String)paramsList.get(lprp+"PRP");
    String lprpSrt = (String)paramsList.get(lprp+"SRT");
     HashMap prpShMap = (HashMap)paramsList.get("PRPSHMAP");
//    String getprpSql ="WITH PRP_DTL as\n" + 
//    "  (\n" + 
//    "   select\n" + 
//    "    count(*) cnt\n" + 
//    "   , decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
//    "   , c.grp prp\n" + 
//    "   , count(*) qty, sum(cts) cts\n" + 
//    "   , sum(cts*quot) prpVlu\n" + 
//    "   , sum(cts*quot*"+agPrp+") dysVlu\n" + 
//    "   from gt_srch_rslt gt, PRP C\n" + 
//    "   where "+lprpPrp+" = c.val and gt.flg='Z' and c.mprp = ? \n" + 
//    "   group by c.grp, decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
//    "  ), SHP_SMRY as (\n" + 
//    "   Select\n" + 
//    "   decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
//    "   , count(*) ttlCnt ,sum(cts*quot) ttlVlu\n" + 
//    "   , sum(cts*quot*"+agPrp+") ttlDysvlu\n" + 
//    "   from gt_srch_rslt where flg='Z'\n" + 
//    "   group by decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
//    "  )\n" + 
//    "  select a.shp,ttlCnt  , cnt, round(ttlVlu) ttlVlu,round(ttlDysVlu/ttlVlu) ttlDys , round(ttlDysVlu) ttlDysVlu\n" + 
//    "  , a.prp prp, round(prpVlu) prpVlu, round(dysVlu) dysVlu, round(prpVlu/ttlVlu*100) pct, round(dysVlu/prpVlu) dys\n" + 
//    "  from PRP_DTL a, SHP_SMRY b\n" + 
//    "  where a.shp = b.shp\n" + 
//    "  order by 1 desc, a.prp";
     String getprpSql="WITH PRP_DTL as\n" + 
     "   (\n" + 
     "    select\n" + 
     "     count(*) cnt\n" + 
     "    , decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
     "    , c.grp prp\n" + 
     "    , count(*) qty, sum(cts) cts\n" + 
     "    , sum(cts*quot) prpVlu\n" + 
     "    , sum(cts*quot*"+agPrp+") dysVlu\n" + 
     "    from gt_srch_rslt gt, PRP C\n" + 
     "    where "+lprpPrp+" = c.val and c.mprp = ?\n" + 
     "    group by c.grp, decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
     "   ), SHP_SMRY as (\n" + 
     "    Select\n" + 
     "    decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
     "    , count(*) ttlCnt\n" + 
     "    , sum(cts*quot) ttlVlu\n" + 
     "    , sum(cts*quot*"+agPrp+") ttlDysvlu\n" + 
     "    from gt_srch_rslt\n" + 
     "    group by decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
     "   )\n" + 
     "   select a.shp, ttlCnt, cnt, round(ttlVlu) ttlVlu,round(ttlDysVlu/ttlVlu) ttlDys, round(ttlDysVlu) ttlDysVlu\n" + 
     "   , a.prp prp, round(prpVlu) prpVlu, round(dysVlu) dysVlu, round(prpVlu/ttlVlu*100) pct, round(dysVlu/prpVlu) dys\n" + 
     "   from PRP_DTL a, SHP_SMRY b\n" + 
     "   where a.shp = b.shp\n" + 
     "   order by 1 desc, a.prp\n";
     
    ArrayList params = new ArrayList();
    params.add(lprp);
    ArrayList outLst = db.execSqlLst("fancySql", getprpSql, params);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
    HashMap prpWiseGd =(HashMap)paramsList.get("PrpWiseGd");
   
  
      try {
            while (rs.next()) {
              String cnt = util.nvl(rs.getString("cnt"));
              String sh = util.nvl(rs.getString("SHP"));
              String prp = util.nvl(rs.getString("prp"));
              String prpVlu = util.nvl(rs.getString("prpVlu"));
              String dysVlu = util.nvl(rs.getString("dysVlu"));
             String pct = util.nvl(rs.getString("pct"));
                String dys = util.nvl(rs.getString("dys"));
              prpWiseGd.put(sh+"_"+prp+"_CNT", cnt);
              prpWiseGd.put(sh+"_"+prp+"_VAL", prpVlu);
              prpWiseGd.put(sh+"_"+prp+"_DYSVAL", dysVlu);
             prpWiseGd.put(sh+"_"+prp+"_PCT", pct);
             prpWiseGd.put(sh+"_"+prp+"_DYS", dys);
             String ttlVal = util.nvl(rs.getString("ttlVlu"));
             String ttlDysVal = util.nvl(rs.getString("ttlDysVlu"));
             String ttlCnt = util.nvl(rs.getString("ttlCnt"));
             String ttlDys = util.nvl(rs.getString("ttlDys"));
              ArrayList prpList = (ArrayList)prpShMap.get(sh+"_"+lprp);
              if(prpList==null)
                  prpList = new ArrayList();
              if(!prpList.contains(prp))
                  prpList.add(prp);
                prpShMap.put(sh+"_"+lprp, prpList);
             prpWiseGd.put(sh+"_"+lprp+"_TTLVAL", ttlVal);
             prpWiseGd.put(sh+"_"+lprp+"_TTLDAYVAL", ttlDysVal);
             prpWiseGd.put(sh+"_"+lprp+"_TTLCNT", ttlCnt);
             prpWiseGd.put(sh+"_"+lprp+"_TTLDYS", ttlDys);
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
    HashMap allStockMap = new HashMap();
    ArrayList allStkPrpList = new ArrayList();
    String allStock ="WITH PRP_DTL as\n" + 
    " (\n" + 
    "  select\n" + 
    "  count(*) cnt\n" + 
    "  , decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
    "  , decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')||' '||prp_001 prp\n" + 
    "  , count(*) qty, sum(cts) cts\n" + 
    "  , sum(cts*quot) prpVlu\n" + 
    "  , sum(cts*quot*"+agPrp+") dysVlu\n" + 
    "  from gt_srch_rslt gt where rap_rte > 1 and gt.flg='Z'\n" + 
    "  group by prp_001, decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
    " UNION\n" + 
    "  select\n" + 
    "  count(*) cnt\n" + 
    "  , decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
    "  , 'FANCY COLOR' prp\n" + 
    "  , count(*) qty, sum(cts) cts\n" + 
    "  , sum(cts*quot) prpVlu\n" + 
    "  , sum(cts*quot*"+agPrp+") dysVlu\n" + 
    "  from gt_srch_rslt gt where rap_rte <= 1 and flg='Z'\n" + 
    "  group by decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
    " ), SHP_SMRY as (\n" + 
    "  Select\n" + 
    "  decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
    "  , sum(cts*quot) ttlVlu\n" + 
    "  , sum(cts*quot*"+agPrp+") ttlDysvlu\n" + 
    "  from gt_srch_rslt where flg='Z'\n" + 
    "  group by decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
    " )\n" + 
    " select a.shp, prp, round(prpVlu) prpVlu, round(dysVlu) dysVlu, round(prpVlu/ttlVlu*100) pct, round(dysVlu/prpVlu) dys\n" + 
    ",  round(ttlVlu) ttlVlu,  round(ttlDysVlu) ttlDysVlu, round(ttlDysVlu/ttlVlu) ttlDys from PRP_DTL a, SHP_SMRY b\n" + 
    "where a.shp = b.shp\n" + 
    "order by a.shp desc, a.prp desc\n";
    
    ArrayList outLst = db.execSqlLst("fancySql", allStock, new ArrayList());
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
    
    try {
            while (rs.next()) {
             
              String sh = util.nvl(rs.getString("SHP"));
              String prp = util.nvl(rs.getString("prp"));
              String prpVlu = util.nvl(rs.getString("prpVlu"));
              String dysVlu = util.nvl(rs.getString("dysVlu"));
              String pct = util.nvl(rs.getString("pct"));
                String dys = util.nvl(rs.getString("dys"));
             
              allStockMap.put(sh+"_"+prp+"_VAL", prpVlu);
              allStockMap.put(sh+"_"+prp+"_DYSVAL", dysVlu);
              allStockMap.put(sh+"_"+prp+"_PCT", pct);
              allStockMap.put(sh+"_"+prp+"_DYS", dys);
              String ttlVal = util.nvl(rs.getString("ttlVlu"));
              String ttlDysVal = util.nvl(rs.getString("ttlDysvlu"));
              String ttlDys = util.nvl(rs.getString("ttlDys"));
              allStockMap.put(sh+"_TTLVAL", ttlVal);
              allStockMap.put(sh+"_TTLDAYVAL", ttlDysVal);
              allStockMap.put(sh+"_TTLDYS", ttlDys);
              
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
  
  public void getpridiffGrid(HttpServletRequest req ,  HashMap paramsList){
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
    String trfPrp = (String)paramsList.get("TRFPRP");
    String gtPriDiff ="WITH PRP_DTL as\n" + 
      " (\n" + 
      "  select dsp_stt dys_grp\n" + 
      "  , count(*) cnt\n" + 
      "  , decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY') SHP\n" + 
      "  , count(*) qty, sum(cts) cts\n" + 
      "  , sum(cts*quot) QuotVlu\n" + 
      "  , sum(cts*"+trfPrp+") TrfVlu\n" + 
      "  from gt_srch_rslt gt where gt.flg='Z'\n" + 
      "  group by dsp_Stt, decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')\n" + 
      " )\n" + 
      " select dys_grp, a.shp, round((TrfVlu - QuotVlu)/TrfVlu*100) pct , round((TrfVlu - QuotVlu)/TrfVlu*100) avg_pct \n" + 
      " from PRP_DTL a\n" + 
      " order by 1, 2 desc";
    ArrayList dys_grpList = new ArrayList();
    HashMap dys_grpMap = new HashMap();
    ArrayList outLst = db.execSqlLst("fancySql", gtPriDiff, new ArrayList());
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
    try {
            while (rs.next()) {
              String pct = util.nvl(rs.getString("pct"));
              String sh = util.nvl(rs.getString("SHP"));
              String dys_grp = util.nvl(rs.getString("dys_grp"));
              String avg_pct = util.nvl(rs.getString("avg_pct"));
              if(!dys_grpList.contains(dys_grp))
                  dys_grpList.add(dys_grp);
                dys_grpMap.put(sh+"_"+dys_grp, pct);
                dys_grpMap.put(sh+"_AVGPCT", avg_pct);

            }
          rs.close();
        pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    session.setAttribute("Dys_grpList", dys_grpList);
    session.setAttribute("Dys_grpMap",dys_grpMap);
  
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
          util.updAccessLog(req,res,"Weekly Stock Report", "createXL start");
      ExcelUtil xlUtil = new ExcelUtil();
      xlUtil.init(db, util, info);
      OutputStream out = res.getOutputStream();
      String CONTENT_TYPE = "getServletContext()/vnd-excel";
      String shape = util.nvl((String)req.getParameter("shape")); 
      String fileNm = "WeeklyStockReportXL"+util.getToDteTime()+".xls";
      res.setContentType(CONTENT_TYPE);
      res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
      
      HSSFWorkbook hwb = xlUtil.WeeklyStockReportXL(req,shape);
      hwb.write(out);
      out.flush();
      out.close();
          util.updAccessLog(req,res,"Weekly Stock Report", "createXL end");
     return null;
      }
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
            util.updAccessLog(req,res,"Weekly Stock Report", "pktDtlExcel start");
        WeeklyStockReportForm udf = (WeeklyStockReportForm)form;
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
        int indexLB = vWPrpList.indexOf(labval)+1;
        int indexSH = vWPrpList.indexOf(sh)+1;
        int indexSZ = vWPrpList.indexOf(szval)+1;
        int indexCol = vWPrpList.indexOf(colval)+1;
        int indexClr = vWPrpList.indexOf(clrval)+1;
        int indexloc = vWPrpList.indexOf(locval)+1;
        int indexTrfCmp = vWPrpList.indexOf("TRF_CMP")+1;
        String lbPrp = util.prpsrtcolumn("prp",indexLB);
        String shPrp =  util.prpsrtcolumn("prp",indexSH);
        String szPrp =  util.prpsrtcolumn("prp",indexSZ);
        String colPrp =  util.prpsrtcolumn("prp",indexCol);
        String clrPrp = util.prpsrtcolumn("prp",indexClr);
        String locPrp = util.prpsrtcolumn("prp",indexloc);
        String trfCmpPrp = util.prpsrtcolumn("prp",indexTrfCmp);
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
            rsltQ+=" and nvl(dsp_stt,'0-15')='"+day+"'";  
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
            rsltQ+=" and b.mprp=? and "+grpPrp+"=b.val ";
            ary.add(grplprp);
            
            if(!grpval.equals("") && !grpval.equals("ALL"))
            rsltQ+=" and b.grp='"+grpval+"' "; 
        }
        if(!allsh.equals("") && !allsh.equals("ALL")){
            rsltQ+=" and decode("+shPrp+", 'ROUND', 'ROUND', 'FANCY')=?";  
            ary.add(allsh);
        }
        if(!alllab.equals("") && !alllab.equals("ALL") && !alllab.equals("FANCY COLOR")){
            rsltQ+=" and "+lbPrp+"=? and rap_rte > 1 ";
            ary.add(alllab.substring(alllab.indexOf(" ")+1,alllab.length()));
        }else if(alllab.equals("FANCY COLOR")){
            rsltQ+=" and rap_rte <= 1 ";
        }
        rsltQ=rsltQ+conQ+" order by sk1,stk_idn,cts";
        ResultSet rs = db.execSql("search Result", rsltQ, ary);

        try {
        while (rs.next()) {
        String stkIdn = util.nvl(rs.getString("stk_idn"));
        HashMap pktPrpMap = new HashMap();
        String vnm = util.nvl(rs.getString("vnm"));
        pktPrpMap.put("SrNo", String.valueOf(count++));
        pktPrpMap.put("vnm", vnm);
        pktPrpMap.put("stk_idn", stkIdn);
        pktPrpMap.put("qty", util.nvl(rs.getString("qty")));
        pktPrpMap.put("STT", util.nvl(rs.getString("stt")));
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
            util.updAccessLog(req,res,"Weekly Stock Report", "pktDtlExcel end");
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
                util.updAccessLog(req,res,"Weekly Stock Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Weekly Stock Report", "init");
            }
            }
            return rtnPg;
            }
}
