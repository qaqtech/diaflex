package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

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

public class WeeklyReportAction  extends DispatchAction {
    
    public WeeklyReportAction() {
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
      WeeklyReportForm udf = (WeeklyReportForm)af;
      udf.resetAll();
          GenericInterface genericInt = new GenericImpl();
          ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_WEEKLY_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_WEEKLY_SRCH");
          info.setGncPrpLst(assortSrchList);
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
        WeeklyReportForm udf = (WeeklyReportForm)af;
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
        int ct = db.execCall("weekly", "GEN_WEEKLY_REPORT_DATA(pDept => ? \n" + 
        "    , pDteFrm => ? , pDteTo => ? \n" + 
        "    , pSlDteFrm => ? , pSlDteTo => ? \n" + 
        "    , pTrfDteFrm => ? , pTrfDteTo => ?,pShp => ? )", params);
     
         HashMap prpShMap = new HashMap();
         HashMap prpWiseGd = new HashMap();
         
         HashMap paramsList = new HashMap();
         
         paramsList.put("PRPSHMAP", prpShMap);
         paramsList.put("PrpWiseGd", prpWiseGd);
         paramsList.put("trfDteFrm", trfDteFrm);
         paramsList.put("trfDteTo", trfDteTo);
         paramsList.put("Shape", shape);
         getLabGrid(req,paramsList);
         getFancyGrid(req,paramsList);
         getAllStockGrid(req,paramsList);
         getPrpWiseGrid(req,paramsList, "SIZE");
         getPrpWiseGrid(req,paramsList, "COL");
         getPrpWiseGrid(req,paramsList, "CLR");
         
         req.setAttribute("view", "Y");
         req.setAttribute("shape", shape);
         req.setAttribute("dept", dept);
         req.setAttribute("salep1",p1DteFrm+" TO "+p1DteTo);
         req.setAttribute("salep2", p2DteFrm+" TO "+p2DteTo);
         req.setAttribute("trnDte", trfDteFrm+" TO "+trfDteTo);
               util.updAccessLog(req,res,"Weekly Sale Report", "fetch end");
         return am.findForward("load");
           }
       }
    
  
  public void getFancyGrid(HttpServletRequest req,HashMap paramsList){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    String fancyGrid="with\n" + 
    "TTL as (\n" + 
    "  select\n" + 
    "    flg\n" + 
    "    , sh_grp\n" + 
    "    , sum(qty) qty, sum(cts) cts\n" + 
    "    , round(sum(cts*rte)) ttlVlu\n" + 
    "    , round(sum(cts*rte*dys)) ttlDysVlu\n" + 
    "  from gt_weekly_rpt\n" + 
    "  where sh_grp = 'FANCY'\n" + 
    "  group by flg, sh_grp\n" + 
    ")\n" + 
    ", PRP_GRP as (\n" + 
    "  select\n" + 
    "    flg\n" + 
    "    , sh_grp\n" + 
    "    , sh prp\n" + 
    "    , sum(qty) qty, sum(cts) cts\n" + 
    "    , round(sum(cts*rte)) prpVlu\n" + 
    "    , round(sum(cts*rte*dys)) prpDysVlu\n" + 
    "  from gt_weekly_rpt\n" + 
    "  where sh_grp = 'FANCY'\n" + 
    "  group by flg, sh_grp, sh\n" + 
    ")\n" + 
    ", TTL_NEW as (\n" + 
    "select sh_grp\n" + 
    "  , round(sum(cts*rte)) ttlNewVlu\n" + 
    "  from gt_weekly_rpt\n" + 
    "  where sh_grp = 'FANCY' and grp = 'NEW'\n" + 
    "  group by sh_grp\n" + 
    ")\n" + 
    ", PRP_NEW as(\n" + 
    "  select\n" + 
    "    sh_grp\n" + 
    "    , sh prp\n" + 
    "    , round(sum(cts*rte)) prpNewVlu\n" + 
    "  from gt_weekly_rpt\n" + 
    "  where sh_grp = 'FANCY' and grp = 'NEW'\n" + 
    "  group by sh_grp, sh\n" + 
    ")\n" + 
    "select p.flg, p.sh_grp, p.prp, p.prpDysVlu\n" + 
    ", round(prpVlu/ttlVlu*100) pct, round(prpDysVlu/prpVlu) dys\n" + 
    ", trunc(prpVlu/1000000, 2) prpVlu, trunc(ttlVlu/1000000, 2) ttlVlu\n" + 
    ", ROUND(ttlDysVlu/t.ttlVlu) ttlDys, round(prpNewVlu/ttlNewVlu*100) addPct,round(t.ttlDysVlu/t.ttlVlu) avgdys \n" + 
    "from ttl t, prp_grp p, ttl_new tn, prp_new pn\n" + 
    "where 1 = 1\n" + 
    "and t.flg = p.flg and t.sh_grp = p.sh_grp\n" + 
    "and tn.sh_grp = pn.sh_grp \n" + 
    "and t.sh_grp = tn.sh_grp and p.prp = pn.prp\n" + 
    "order by 1, 2 desc, 3 ";
    System.out.println(fancyGrid);
    ArrayList outLst = db.execSqlLst("labGrid",fancyGrid, new ArrayList());
     PreparedStatement pst = (PreparedStatement)outLst.get(0);
     ResultSet rs = (ResultSet)outLst.get(1);
     HashMap shapeDataMap = new HashMap();
     ArrayList shapeList = new ArrayList();
     try {
            while (rs.next()) {
              String sh = util.nvl(rs.getString("sh_grp"));
              String prp = util.nvl(rs.getString("prp"));
              String flg = util.nvl(rs.getString("flg"));
              String pct = util.nvl(rs.getString("pct"));
              String dys = util.nvl(rs.getString("dys"));
              String addPct = util.nvl(rs.getString("addPct"));
           
             
              String avgdys = util.nvl(rs.getString("avgdys"));
              String  prpVlu = util.nvl(rs.getString("prpVlu"));
              String  ttlVlu = util.nvl(rs.getString("ttlVlu"));
              shapeDataMap.put(sh+"_"+prp+"_ADDPCT", addPct+"%");
              shapeDataMap.put(sh+"_"+prp+"_"+flg+"_PCT", pct+"%");
              shapeDataMap.put(sh+"_"+prp+"_"+flg+"_DYS", dys);
            
              shapeDataMap.put(sh+"_"+prp+"_"+flg+"_PCT", pct+"%");
              shapeDataMap.put(sh+"_"+prp+"_"+flg+"_VAL", prpVlu);
            
              shapeDataMap.put(sh+"_"+flg+"_AVGDYS", avgdys);
              shapeDataMap.put(sh+"_"+flg+"_TTLVAL", ttlVlu);
                if(!shapeList.contains(prp))
                    shapeList.add(prp);
            }
    
    
    rs.close();
    pst.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
     
    session.setAttribute("ShapeDateMap", shapeDataMap);
    session.setAttribute("ShapeList", shapeList);
  }
  public void getLabGrid(HttpServletRequest req,HashMap paramsList){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    String filterStr="";
    String shape = util.nvl((String)paramsList.get("Shape"));
    ArrayList params = new ArrayList();
    if(!shape.equals("ALL")){
        filterStr= " sh_grp =  ? and ";
       params.add(shape);
       params.add(shape);
       params.add(shape);
       params.add(shape);
    }
    
      String FNShape = util.nvl((String)paramsList.get("FNShape"));
      
      if(!FNShape.equals("")){
           filterStr=filterStr +" sh = '"+FNShape+"' and ";
       }
      
    String labGrid="WITH \n" + 
    "TTL as (\n" + 
    "  select sh_grp, sum(qty) qty\n" + 
    "    , round(sum(cts*rte)) ttlVlu\n" + 
    "    , round(sum(cts*rte*dys)) ttlDysVlu\n" + 
    "    , round(sum(cts*nvl(trf_rte, rte))) ttlTrfVlu\n" + 
    "  from gt_weekly_rpt \n" + 
    "  where "+filterStr+"   flg = 'Z'\n" + 
    "  group by sh_grp\n" + 
    ") \n" + 
    ", DYS_GRP as (\n" + 
    "  select dys_grp, sh_grp\n" + 
    "    , sum(qty) qty\n" + 
    "    , round(sum(cts*rte)) vlu\n" + 
    "    , round(sum(cts*rte*dys)) dysVlu\n" + 
    "    , round(sum(cts*trf_rte)) trfVlu\n" + 
    "  from gt_weekly_rpt \n" + 
    "  where "+filterStr+"  flg = 'Z'\n" + 
    "  group by dys_grp, sh_grp\n" + 
    ")\n" + 
    ", LAB_TTL as (\n" + 
    "  select sh_grp, lab, sum(qty) qty\n" + 
    "  from gt_weekly_rpt \n" + 
    "  where "+filterStr+"  flg = 'Z'\n" + 
    "  group by sh_grp, lab\n" + 
    ")\n" + 
    ", LAB_DYS_GRP as (\n" + 
    "  select dys_grp, sh_grp, lab, sum(qty) qty\n" + 
    "  from gt_weekly_rpt \n" + 
    "  where "+filterStr+"  flg = 'Z'\n" + 
    "  group by dys_grp, sh_grp, lab\n" + 
    ")\n" + 
    "select d.dys_grp, d.sh_grp, l.lab, l.qty labQty, d.qty dysQty\n" + 
    ", round(d.vlu/t.ttlVlu*100) dysPct, round((d.trfVlu - d.vlu)/d.TrfVlu*100) dysRevPct\n" + 
    ", t.Qty ttlQty, lt.qty ttlLabQty, round((t.ttlTrfVlu - t.ttlVlu)/t.ttlTrfVlu*100) ttlRevPct\n" + 
    "from ttl t, dys_grp d, lab_dys_grp l, lab_ttl lt\n" + 
    "where t.sh_grp = d.sh_grp and t.sh_grp = l.sh_grp\n" + 
    "and d.dys_grp = l.dys_grp and lt.sh_Grp = l.sh_grp and l.lab = lt.lab\n" + 
    "order by 1, 2 desc, 3 ";
    
   ArrayList outLst = db.execSqlLst("labGrid", labGrid, params);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
    HashMap labWsDataMap = new HashMap();
    ArrayList daysLabList = new ArrayList();
    try {
           while (rs.next()) {
               String labcnt  = util.nvl(rs.getString("labQty"));
               String sh = util.nvl(rs.getString("sh_grp"));
               String lab = util.nvl(rs.getString("lab"));
               String dys_grp = util.nvl(rs.getString("dys_grp"));
               String ttlRevPct = util.nvl(rs.getString("ttlRevPct"));
              String ttlLabCnt = util.nvl(rs.getString("ttlLabQty"));
              String ttlQty= util.nvl(rs.getString("ttlQty"));
              String dysQty = util.nvl(rs.getString("dysQty"));
              String pct = util.nvl(rs.getString("dysPct"));
              String revPct = util.nvl(rs.getString("dysRevPct"));
           
              labWsDataMap.put(dys_grp+"_"+sh+"_"+lab+"_LABCNT", labcnt);
              labWsDataMap.put(dys_grp+"_"+sh+"_"+lab+"_SH", sh);
              labWsDataMap.put(dys_grp+"_"+sh+"_QTY", dysQty);
              labWsDataMap.put(dys_grp+"_"+sh+"_PCT", pct+"%");
              labWsDataMap.put(dys_grp+"_"+sh+"_REVPCT", revPct);
            
               if(!daysLabList.contains(dys_grp))
                   daysLabList.add(dys_grp);
              labWsDataMap.put(sh+"_"+lab+"_TTLCNT", ttlLabCnt);
              labWsDataMap.put(sh+"_TTLCNT", ttlQty);
              labWsDataMap.put(sh+"_TTLREVPCT", ttlRevPct);
               
             

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
  public void getAllStockGrid(HttpServletRequest req,HashMap paramsList){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    String filterStr="";
    String shape = util.nvl((String)paramsList.get("Shape"));
    ArrayList params = new ArrayList();
    if(!shape.equals("ALL")){
        filterStr= " sh_grp =  ? and ";
       params.add(shape);
       params.add(shape);
       params.add(shape);
       params.add(shape);
    }
    
      String FNShape = util.nvl((String)paramsList.get("FNShape"));
      
      if(!FNShape.equals("")){
          filterStr=filterStr +" sh = '"+FNShape+"' and ";
       }
      
  
  String allStockGrid = "with\n" + 
  "TTL as (\n" + 
  "  select\n" + 
  "    flg\n" + 
  "    , sh_grp\n" + 
  "    , sum(qty) qty, sum(cts) cts\n" + 
  "    , round(sum(cts*rte)) ttlVlu\n" + 
  "    , round(sum(cts*rte*dys)) ttlDysVlu\n" + 
  "  from gt_weekly_rpt\n" + 
  "  where "+filterStr+" 1=1 \n" + 
  "  group by flg, sh_grp\n" + 
  ")\n" + 
  ", PRP_GRP as (\n" + 
  "  select\n" + 
  "    flg\n" + 
  "    , sh_grp\n" + 
  "    , decode(fncy_yn, 'Y', 'F-COL', lab) prp\n" + 
  "    , sum(qty) qty, sum(cts) cts\n" + 
  "    , round(sum(cts*rte)) prpVlu\n" + 
  "    , round(sum(cts*rte*dys)) prpDysVlu\n" + 
  "  from gt_weekly_rpt\n" + 
  "  where "+filterStr+" 1=1 \n" + 
  "  group by flg, sh_grp, decode(fncy_yn, 'Y', 'F-COL', lab)\n" + 
  ")\n" + 
  ", TTL_NEW as (\n" + 
  "select sh_grp\n" + 
  "  , round(sum(cts*rte)) ttlNewVlu\n" + 
  "  from gt_weekly_rpt\n" + 
  "  where "+filterStr+"  grp = 'NEW'\n" + 
  "  group by sh_grp\n" + 
  ")\n" + 
  ", PRP_NEW as(\n" + 
  "  select\n" + 
  "    sh_grp\n" + 
  "    , decode(fncy_yn, 'Y', 'F-COL', lab) prp\n" + 
  "    , round(sum(cts*rte)) prpNewVlu\n" + 
  "  from gt_weekly_rpt\n" + 
  "  where "+filterStr+"  grp = 'NEW'\n" + 
  "  group by sh_grp, decode(fncy_yn, 'Y', 'F-COL', lab)\n" + 
  ")\n" + 
  "select p.flg, p.sh_grp, p.prp, trunc(p.prpVlu/1000000, 2) prpVlu, p.prpDysVlu\n" + 
  ", round(prpVlu/ttlVlu*100) pct, round(prpDysVlu/prpVlu) dys ,trunc(t.ttlVlu/1000000, 2) ttlVlu\n" + 
  ", round(prpNewVlu/ttlNewVlu*100) addPct , p.qty ,  t.qty ttlQty,round(t.ttlDysVlu/t.ttlVlu) avgdys \n" + 
  "from ttl t, prp_grp p, ttl_new tn, prp_new pn\n" + 
  "where 1 = 1\n" + 
  "and t.flg = p.flg and t.sh_grp = p.sh_grp\n" + 
  "and tn.sh_grp = pn.sh_grp \n" + 
  "and t.sh_grp = tn.sh_grp and p.prp = pn.prp\n" + 
  "order by 1, 2 desc, 3 desc";
    ArrayList outLst = db.execSqlLst("labGrid", allStockGrid, params);
     PreparedStatement pst = (PreparedStatement)outLst.get(0);
     ResultSet rs = (ResultSet)outLst.get(1);
    HashMap allStockMap = new HashMap();
    ArrayList allStkPrpList = new ArrayList();
    try {
            while (rs.next()) {
             
              String sh = util.nvl(rs.getString("sh_grp"));
              String prp = util.nvl(rs.getString("prp"));
              String flg = util.nvl(rs.getString("flg"));
              String pct = util.nvl(rs.getString("pct"));
              String dys = util.nvl(rs.getString("dys"));
              String addPct = util.nvl(rs.getString("addPct"));
              String qty = util.nvl(rs.getString("qty"));
              String ttlQty = util.nvl(rs.getString("ttlQty"));
              String avgdys = util.nvl(rs.getString("avgdys"));
              String  prpVlu = util.nvl(rs.getString("prpVlu"));
              String  ttlVlu = util.nvl(rs.getString("ttlVlu"));
              allStockMap.put(sh+" "+prp+"_ADDPCT", addPct+"%");
              allStockMap.put(sh+" "+prp+"_"+flg+"_PCT", pct+"%");
              allStockMap.put(sh+" "+prp+"_"+flg+"_DYS", dys);
              allStockMap.put(sh+" "+prp+"_"+flg+"_QTY", qty);
              allStockMap.put(sh+" "+prp+"_"+flg+"_PCT", pct+"%");
              allStockMap.put(sh+" "+prp+"_"+flg+"_VAL", prpVlu);
              allStockMap.put(sh+"_"+flg+"_TTLQTY", ttlQty);
              allStockMap.put(sh+"_"+flg+"_AVGDYS", avgdys);
              allStockMap.put(sh+"_"+flg+"_TTLVAL", ttlVlu);
              
            }
          rs.close();
        pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    allStkPrpList.add("SH GIA");
    allStkPrpList.add("SH IGI");
    allStkPrpList.add("SH F-COL");
    session.setAttribute("AllStockMap", allStockMap);
    session.setAttribute("AllStkPrpList", allStkPrpList);
  
  }
  
  public void getPrpWiseGrid(HttpServletRequest req,HashMap paramsList,String lprp ){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    HashMap prpShMap = (HashMap)paramsList.get("PRPSHMAP");
    String grp="sz_grp";
    if(lprp.equals("COL"))
        grp="co_grp";
    if(lprp.equals("CLR"))
        grp="pu_grp";
    String filterStr="";
    String shape = util.nvl((String)paramsList.get("Shape"));
    ArrayList params = new ArrayList();
    if(!shape.equals("ALL")){
        filterStr= " sh_grp =  ? and ";
       params.add(shape);
       params.add(shape);
       params.add(shape);
      params.add(shape);
       
    }
    
      String FNShape = util.nvl((String)paramsList.get("FNShape"));
      
      if(!FNShape.equals("")){
         filterStr=filterStr +" sh = '"+FNShape+"' and ";
       }
      
    String getprpSql ="with\n" + 
    "TTL as (\n" + 
    "  select\n" + 
    "    flg\n" + 
    "    , sh_grp\n" + 
    "    , sum(qty) qty, sum(cts) cts\n" + 
    "    , round(sum(cts*rte)) ttlVlu\n" + 
    "    , round(sum(cts*rte*dys)) ttlDysVlu\n" + 
    "  from gt_weekly_rpt\n" + 
    "  where "+filterStr+" 1=1 \n" + 
    "  group by flg, sh_grp\n" + 
    ")\n" + 
    ", PRP_GRP as (\n" + 
    "  select\n" + 
    "    flg\n" + 
    "    , sh_grp\n" + 
    "    , "+grp+" prp\n" + 
    "    , sum(qty) qty, sum(cts) cts\n" + 
    "    , round(sum(cts*rte)) prpVlu\n" + 
    "    , round(sum(cts*rte*dys)) prpDysVlu\n" + 
    "  from gt_weekly_rpt\n" + 
    "  where "+filterStr+" 1=1 \n" + 
    "  group by flg, sh_grp, "+grp+"\n" + 
    ")\n" + 
    ", TTL_NEW as (\n" + 
    "select sh_grp\n" + 
    "  , round(sum(cts*rte)) ttlNewVlu\n" + 
    "  from gt_weekly_rpt\n" + 
    "  where "+filterStr+" grp = 'NEW'\n" + 
    "  group by sh_grp\n" + 
    ")\n" + 
    ", PRP_NEW as(\n" + 
    "  select\n" + 
    "    sh_grp\n" + 
    "    , "+grp+" prp\n" + 
    "    , round(sum(cts*rte)) prpNewVlu\n" + 
    "  from gt_weekly_rpt\n" + 
    "  where "+filterStr+"  grp = 'NEW'\n" + 
    "  group by sh_grp, "+grp+"\n" + 
    ")\n" + 
    "select p.flg, p.sh_grp, p.prp, p.prpVlu, p.prpDysVlu\n" + 
    ",p.qty , round(prpVlu/ttlVlu*100) pct, round(prpDysVlu/prpVlu) dys\n" + 
    ", round(prpNewVlu/ttlNewVlu*100) addPct , t.qty ttlQty,round(t.ttlDysVlu/t.ttlVlu) avgdys\n" + 
    "from ttl t, prp_grp p, ttl_new tn, prp_new pn\n" + 
    "where 1 = 1\n" + 
    "and t.flg = p.flg and t.sh_grp = p.sh_grp\n" + 
    "and tn.sh_grp = pn.sh_grp \n" + 
    "and t.sh_grp = tn.sh_grp and p.prp = pn.prp\n" + 
    "order by 1, 2 desc, 3 asc";
    
    
    ArrayList outLst = db.execSqlLst("fancySql", getprpSql, params);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
  
    HashMap prpWiseMap =  (HashMap)paramsList.get("PrpWiseGd");
  
      try {
            while (rs.next()) {
             String flg = util.nvl(rs.getString("flg"));
             String sh = util.nvl(rs.getString("sh_grp"));
             String prp = util.nvl(rs.getString("prp"));
             String pct = util.nvl(rs.getString("pct"));
             String add_pct = util.nvl(rs.getString("addpct"));
             String dys = util.nvl(rs.getString("dys"));
             String qty = util.nvl(rs.getString("qty"));
             String ttlQty = util.nvl(rs.getString("ttlQty"));
             String avgdys = util.nvl(rs.getString("avgdys"));
             prpWiseMap.put(sh+"_"+prp+"_ADD_PCT", add_pct+"%");
             prpWiseMap.put(sh+"_"+flg+"_"+prp+"_QTY", qty);
             prpWiseMap.put(sh+"_"+flg+"_"+prp+"_PCT",pct+"%");
             prpWiseMap.put(sh+"_"+flg+"_"+prp+"_DYS", dys);
           
              
             ArrayList prpList = (ArrayList)prpShMap.get(sh+"_"+lprp);
             if(prpList==null)
                 prpList = new ArrayList();
             if(!prpList.contains(prp))
                 prpList.add(prp);
               prpShMap.put(sh+"_"+lprp, prpList);
             prpWiseMap.put(sh+"_"+flg+"_"+lprp+"_TTLQTY", ttlQty);
             prpWiseMap.put(sh+"_"+flg+"_"+lprp+"_AVGDYS", avgdys);
            
           
           }
          rs.close();
          pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      
    session.setAttribute("PrpWiseGd", prpWiseMap);
    session.setAttribute("PrpShMap", prpShMap);
        
  }
  
    public ActionForward GridByShapeWise(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            WeeklyReportForm udf = (WeeklyReportForm)form;
            String fnshape = util.nvl(req.getParameter("FNSHAPE"));
            String trfDteFrm = (String)udf.getValue("trfdtefr");
            String trfDteTo = (String)udf.getValue("trfdteto");
            ResultSet rs = db.execSql("cnt", "select count(*) from gt_weekly_rpt", new ArrayList());
            while(rs.next()){
                System.out.println("CNT : "+ rs.getString(1));
            }
            rs.close();
            String shape ="FANCY";     
            HashMap prpShMap = new HashMap();
            HashMap prpWiseGd = new HashMap();
            
            HashMap paramsList = new HashMap();
            
            paramsList.put("PRPSHMAP", prpShMap);
            paramsList.put("PrpWiseGd", prpWiseGd);
            paramsList.put("trfDteFrm", trfDteFrm);
            paramsList.put("trfDteTo", trfDteTo);
            paramsList.put("Shape", shape);
            paramsList.put("FNShape", fnshape);
            getLabGrid(req,paramsList);
            getFancyGrid(req,paramsList);
            getAllStockGrid(req,paramsList);
            getPrpWiseGrid(req,paramsList, "SIZE");
            getPrpWiseGrid(req,paramsList, "COL");
            getPrpWiseGrid(req,paramsList, "CLR");
            req.setAttribute("FNSHAPE", fnshape);
            return am.findForward("ShapeWise");
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
      
      HSSFWorkbook hwb = xlUtil.WeeklyReportXL(req,shape);
      hwb.write(out);
      out.flush();
      out.close();
          util.updAccessLog(req,res,"Weekly Sale Report", "createXL end");
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
            util.updAccessLog(req,res,"Weekly Sale Report", "pktDtlExcel start");
        WeeklyReportForm udf = (WeeklyReportForm)form;
        String shgrp = util.nvl(req.getParameter("shgrp"));
        String shape = util.nvl(req.getParameter("shape"));
        String flg = util.nvl(req.getParameter("flg"));
        String daygrp = util.nvl(req.getParameter("day"));
        String lab = util.nvl(req.getParameter("lab"));
        String prpgrp = util.nvl(req.getParameter("prpgrp"));
        String grp = util.nvl(req.getParameter("grp"));
        String prpgrpVal = util.nvl(req.getParameter("prpgrpVal"));
        String allshgrp = util.nvl(req.getParameter("allshgrp"));
        ArrayList itemHdr = new ArrayList();
        ArrayList ary = new ArrayList();
        ArrayList pktList = new ArrayList();
        String conQ="";
        int count=1;
        itemHdr.add("SrNo");
        itemHdr.add("VNM");
        itemHdr.add("QTY");
        itemHdr.add("CTS");
        itemHdr.add("TRF_RTE");
        itemHdr.add("RTE");
        itemHdr.add("AMT");
        itemHdr.add("RAP_RTE");
        itemHdr.add("RAP_DIS");
//        itemHdr.add("PKT_DTE");
        itemHdr.add("STT");
        itemHdr.add("LAB");itemHdr.add("DYS");
        itemHdr.add("SH");itemHdr.add("SZ");itemHdr.add("CO");itemHdr.add("PU");
//        itemHdr.add("CT");itemHdr.add("PO");itemHdr.add("SY");itemHdr.add("FL");
        itemHdr.add("DYS_GRP");itemHdr.add("SH_GRP");itemHdr.add("SZ_GRP");itemHdr.add("CO_GRP");itemHdr.add("PU_GRP");
        
        String srchQ =" select stk_idn,VNM,QTY,CTS,LAB,round(DYS) DYS,TRF_RTE,RTE,RAP_RTE,PKT_DTE,STT,SH,SZ,SZ,PU,CT,CO,PO,SY,FL,DYS_GRP,SH_GRP,SZ_GRP,CO_GRP,PU_GRP,to_char(trunc(CTS,2) * RTE, '99999999990.00') AMT,decode(rap_rte ,'1',null, trunc((RTE/greatest(rap_rte,1)*100)-100, 2)) r_dis ";


        String rsltQ = srchQ + " from gt_weekly_rpt where 1=1 ";
        if(!shgrp.equals("")){
            rsltQ+=" and SH_GRP=?";  
            ary.add(shgrp);
        }
            if(!grp.equals("")){
                rsltQ+=" and GRP=?";  
                ary.add(grp);
            }
        if(!shape.equals("")){
                rsltQ+=" and SH=?";  
                ary.add(shape);
        }
            if(!flg.equals("")){
                    rsltQ+=" and flg=?";  
                    ary.add(flg);
            }
            if(!daygrp.equals("")){
                    rsltQ+=" and dys_grp=?";  
                    ary.add(daygrp);
            }
            if(!lab.equals("")){
                    rsltQ+=" and lab=?";  
                    ary.add(lab);
            }
            if(!prpgrpVal.equals("")){
                if(prpgrp.equals("SIZE"))
                    rsltQ+=" and SZ_GRP=?";
                if(prpgrp.equals("COL"))
                    rsltQ+=" and CO_GRP=?"; 
                if(prpgrp.equals("CLR"))
                    rsltQ+=" and PU_GRP=?"; 
            ary.add(prpgrpVal);
            }

            if(!allshgrp.equals("")){
                    String[] vals = allshgrp.split(" ");
                    rsltQ+=" and decode(fncy_yn, 'Y', 'F-COL', lab)=?";  
                    ary.add(vals[1]);
            }
            
        rsltQ=rsltQ+conQ+" order by stk_idn,cts";
        ResultSet rs = db.execSql("search Result", rsltQ, ary);

        try {
        while (rs.next()) {
        String stkIdn = util.nvl(rs.getString("stk_idn"));
        HashMap pktPrpMap = new HashMap();
        String vnm = util.nvl(rs.getString("vnm"));
        pktPrpMap.put("SrNo", String.valueOf(count++));
        pktPrpMap.put("VNM", vnm);
        pktPrpMap.put("stk_idn", stkIdn);
        pktPrpMap.put("QTY", util.nvl(rs.getString("QTY")));
        pktPrpMap.put("CTS", util.nvl(rs.getString("CTS")));
        pktPrpMap.put("TRF_RTE", util.nvl(rs.getString("TRF_RTE")));
        pktPrpMap.put("RTE", util.nvl(rs.getString("RTE")));
        pktPrpMap.put("AMT", util.nvl(rs.getString("AMT")));
        pktPrpMap.put("RAP_RTE", util.nvl(rs.getString("RAP_RTE")));
        pktPrpMap.put("RAP_DIS", util.nvl(rs.getString("r_dis")));
        pktPrpMap.put("PKT_DTE", util.nvl(rs.getString("PKT_DTE")));
        pktPrpMap.put("STT", util.nvl(rs.getString("STT")));
        pktPrpMap.put("LAB", util.nvl(rs.getString("LAB")));
            pktPrpMap.put("DYS", util.nvl(rs.getString("DYS")));
            pktPrpMap.put("SH", util.nvl(rs.getString("SH")));
            pktPrpMap.put("SZ", util.nvl(rs.getString("SZ")));
            pktPrpMap.put("CO", util.nvl(rs.getString("CO")));
            pktPrpMap.put("PU", util.nvl(rs.getString("PU")));
            pktPrpMap.put("CT", util.nvl(rs.getString("CT")));
            pktPrpMap.put("PO", util.nvl(rs.getString("PO")));
            pktPrpMap.put("SY", util.nvl(rs.getString("SY")));
            pktPrpMap.put("FL", util.nvl(rs.getString("FL")));
            pktPrpMap.put("DYS_GRP", util.nvl(rs.getString("DYS_GRP")));
            pktPrpMap.put("SH_GRP", util.nvl(rs.getString("SH_GRP")));
            pktPrpMap.put("SZ_GRP", util.nvl(rs.getString("SZ_GRP")));
            pktPrpMap.put("CO_GRP", util.nvl(rs.getString("CO_GRP")));
            pktPrpMap.put("PU_GRP", util.nvl(rs.getString("PU_GRP")));
        pktList.add(pktPrpMap);
        }
            rs.close();
        } catch (SQLException sqle) {

        // TODO: Add catch code
        sqle.printStackTrace();
        } 
        session.setAttribute("pktList", pktList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Weekly Sale Report", "pktDtlExcel end");
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
                util.updAccessLog(req,res,"Weekly Sale Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Weekly Sale Report", "init");
            }
            }
            return rtnPg;
            }
}
