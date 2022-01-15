package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.io.OutputStream;

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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class StockSummaryAction extends DispatchAction {


    public ActionForward loadGRPSttLab(ActionMapping am, ActionForm form,
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
            StockSummaryForm stockSumryForm = (StockSummaryForm)form;
            ArrayList labList=new ArrayList();
            ArrayList grpList=new ArrayList();
            HashMap labGrpList=new HashMap();
            HashMap ttlGrpList=new HashMap();
            HashMap ttlLabList=new HashMap();
            HashMap ttlanddscList=new HashMap();
            
            String grpQ="select nvl(a.cert_lab,'NA') cert_lab,b.grp1 Grp, decode(b.grp1, 'MKT','Marketing','LAB','Lab','ASRT','Assort','SOLD','Sold', b.grp1) dsc,sum(a.qty) totQty,sum(to_char(trunc(a.cts,2),'99999990.00')) totCts " + 
            "from mstk a , df_stk_stt b " + 
            "where b.grp1 <> 'SOLD' " + 
            "and a.stt = b.stt and b.flg='A' " + 
            "group by nvl(a.cert_lab,'NA'), b.grp1 order by cert_lab";
              ArrayList outLst = db.execSqlLst("Group Status Lab", grpQ, new ArrayList());
              PreparedStatement  pst = (PreparedStatement)outLst.get(0);
              ResultSet  rs = (ResultSet)outLst.get(1);
            while (rs.next()) { 
                String cert_lab=util.nvl(rs.getString("cert_lab"));
                String Grp=util.nvl(rs.getString("Grp"));
                String totQty=util.nvl(rs.getString("totQty"));
                String totCts=util.nvl(rs.getString("totCts"));
                
                if(!labList.contains(cert_lab) && !cert_lab.equals(("")))
                    labList.add(cert_lab);
                if(!grpList.contains(Grp) && !Grp.equals((""))){
                    grpList.add(Grp);
                    ttlanddscList.put(Grp,util.nvl(rs.getString("dsc")));
                }
                labGrpList.put(cert_lab+"_"+Grp+"_QTY",totQty);
                labGrpList.put(cert_lab+"_"+Grp+"_CTS",totCts);
                    
            } 
                rs.close();
                pst.close();
            String ttlGrpQ="select b.grp1 Grp,sum(a.qty) totQty,sum(to_char(trunc(a.cts,2),'99999990.00')) totCts " + 
            "from mstk a , df_stk_stt b " + 
            "where 1 = 1 and b.grp1 <> 'SOLD' and a.stt = b.stt " + 
            "group by b.grp1";
               outLst = db.execSqlLst("Group Total", ttlGrpQ, new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
            while (rs.next()) { 
                String Grp=util.nvl(rs.getString("Grp"));
                String totQty=util.nvl(rs.getString("totQty"));
                String totCts=util.nvl(rs.getString("totCts"));
                ttlGrpList.put(Grp+"_QTY",totQty);
                ttlGrpList.put(Grp+"_CTS",totCts);
                    
            } 
                rs.close();
                pst.close();
            String ttlLabQ="select nvl(a.cert_lab,'NA') cert_lab,sum(a.qty) totQty,sum(to_char(trunc(a.cts,2),'99999990.00')) totCts " + 
            "from mstk a , df_stk_stt b " + 
            "where b.grp1 <> 'SOLD' and a.stt = b.stt " + 
            "group by nvl(a.cert_lab,'NA') ";
              outLst = db.execSqlLst("Group Total", ttlLabQ, new ArrayList());
               pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
            while (rs.next()) { 
                String cert_lab=util.nvl(rs.getString("cert_lab"));
                String totQty=util.nvl(rs.getString("totQty"));
                String totCts=util.nvl(rs.getString("totCts"));
                ttlLabList.put(cert_lab+"_QTY",totQty);
                ttlLabList.put(cert_lab+"_CTS",totCts);
                    
            } 
                rs.close();
                pst.close();
            String grandTtlQ="select sum(a.qty) totQty,sum(to_char(trunc(a.cts,2),'99999990.00')) totCts " + 
            "from mstk a , df_stk_stt b " + 
            "where b.grp1 <> 'SOLD' and a.stt = b.stt ";
              outLst = db.execSqlLst("Group Total", grandTtlQ, new ArrayList());
               pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
            while (rs.next()) { 
                String totQty=util.nvl(rs.getString("totQty"));
                String totCts=util.nvl(rs.getString("totCts"));
                ttlanddscList.put("grand_QTY",totQty);
                ttlanddscList.put("grand_CTS",totCts);
                    
            } 
                rs.close();
                pst.close();
            ArrayList dfStkStt=new ArrayList();
            ArrayList sttList = new ArrayList();
            String sttQry = "select * from df_stk_stt where stt <> 'MKSD' and stt not like 'SUS%'  and flg='A' order by srt";
              outLst = db.execSqlLst("statusInfrmDsc", sttQry, new ArrayList());
               pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
            
                while (rs.next()) {
                    sttList = new ArrayList();
                    sttList.add(rs.getString("stt"));
                    sttList.add(rs.getString("dsc"));
                    dfStkStt.add(sttList);
                }
                rs.close();
                pst.close();
            info.setDfStkStt(dfStkStt);
            
            req.setAttribute("labList", labList);
            req.setAttribute("grpList", grpList);
            req.setAttribute("labGrpList", labGrpList);
            req.setAttribute("ttlGrpList", ttlGrpList);
            req.setAttribute("ttlLabList", ttlLabList);
            req.setAttribute("ttlanddscList", ttlanddscList);
            
            return am.findForward("loadGRPSttLab");
            }
        }
    
    public ActionForward loadSttLab(ActionMapping am, ActionForm form,
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
        StockSummaryForm stockSumryForm = (StockSummaryForm)form;
         HashMap dbinfo = info.getDmbsInfoLst();
         ArrayList ary = new ArrayList();
        String labv = (String)dbinfo.get("LAB");
        /*  Display LAB                      */
        ArrayList labList = new ArrayList();
    //    String labqry = "select distinct a.val,a.srt from prp a,mstk b where a.mprp = ? and a.val=b.cert_lab order by a.srt ";
       String labqry="select distinct a.val,a.srt from prp a where a.mprp =?  order by a.srt ";
       ary = new ArrayList();
       ary.add(labv);
        ArrayList  outLst = db.execSqlLst("labInfrm", labqry, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet   rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            labList.add(rs.getString("val"));
        }
            rs.close();
            pst.close();
        info.setLabList(labList);
        /*          Display STATUS  */
        ArrayList sttList = new ArrayList();
        String sttQry = "select distinct stt from mstk where stt <> 'MKSD' and stt not like 'SUS%' order by stt";
            outLst = db.execSqlLst("statusInfrm", sttQry, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            sttList.add(rs.getString("stt"));
        }
            rs.close();
            pst.close();
        info.setSttList(sttList);
        rs = null;
        //Display Status Dsc
         
        sttList = new ArrayList();
        ArrayList dfStkStt=new ArrayList();
        sttQry = "select * from df_stk_stt where stt <> 'MKSD' and stt not like 'SUS%'  and flg='A' order by srt";
          outLst = db.execSqlLst("statusInfrmDsc", sttQry, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                sttList = new ArrayList();
                sttList.add(rs.getString("stt"));
                sttList.add(rs.getString("dsc"));
                dfStkStt.add(sttList);
            }
            rs.close();
            pst.close();
        info.setDfStkStt(dfStkStt);
        rs = null;
        
        /*  To display quantity and cts */
        HashMap qty_ctstable = new HashMap();
        String qty_ctsqry =
            "select nvl(cert_lab,'NA') cert_lab,stt,sum(qty) totQty,sum(to_char(trunc(cts,2),'99999990.00')) totCts from mstk where stt <> 'MKSD' and stt not like 'SUS%' group by cert_lab,stt";
          outLst = db.execSqlLst("sumofqty-cts", qty_ctsqry, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String lab_stt =
                rs.getString("cert_lab") + "_" + rs.getString("stt");
            String qty_cts =
                rs.getString("totQty") + "|" +
                rs.getString("totCts");
            qty_ctstable.put(lab_stt, qty_cts);
        }
            rs.close();
            pst.close();
      info.setQty_ctstbl(qty_ctstable);
        // To get total of each Lab at right side.
        HashMap lab_qtyctstable=new HashMap();
        String  lab_qtyctsQry="select nvl(cert_lab, 'NA') cert_lab,sum(qty) totQty, sum(to_char(trunc(cts,2),'99999990.00')) totCts from mstk where stt <> 'MKSD' and stt not like 'SUS%' and 1 = 1  group by nvl(cert_lab, 'NA')  ";
          outLst = db.execSqlLst("lab_qtyctsQry", lab_qtyctsQry, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
      while (rs.next()) {
          String lab =util.nvl(rs.getString("cert_lab"));
          String qty_cts =
            util.nvl(rs.getString("totQty") )+ "|" +
            util.nvl(rs.getString("totCts"));
        lab_qtyctstable.put(lab,qty_cts)  ;
      }
            rs.close();
            pst.close();
       info.setLab_qtyctstable(lab_qtyctstable);
       
       // To get total of each status 
       
       HashMap stt_qtyctstable=new HashMap();
       String  status_qtyctsQry="select stt,sum(qty) totQty,sum(to_char(trunc(cts,2),'99999990.00')) totCts from mstk where stt <> 'MKSD' and stt not like 'SUS%' group by stt";
          outLst = db.execSqlLst("status_qtyctsQry", status_qtyctsQry, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
       while (rs.next()) {
         String stt =util.nvl(rs.getString("stt"));
     /*      if(stt.equals("")) {
             stt="NA";
           }  */
         String qty_cts =
           util.nvl(rs.getString("totQty")) + "|" +
           util.nvl(rs.getString("totCts"));
       stt_qtyctstable.put(stt,qty_cts)  ;
       }
            rs.close();
            pst.close();
       info.setStt_qtyctstable(stt_qtyctstable);
       
      // grand total of cts and qty
      String gndCtsQty="";
      String  gndCtsQtyQry="select sum(qty) totQty,sum(to_char(trunc(cts,2),'99999990.00')) totCts from mstk where stt <> 'MKSD' and stt not like 'SUS%'";
          outLst = db.execSqlLst("gndCtsQtyQry", gndCtsQtyQry, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
      while (rs.next()) {
          gndCtsQty =
          util.nvl(rs.getString("totQty")) + "|" +
          util.nvl(rs.getString("totCts"));
          }
            rs.close();
            pst.close();
     session.setAttribute("gndCtsQty", gndCtsQty);
        return am.findForward("loadSttLab");
        }
    }

    public ActionForward loadShSize(ActionMapping am, ActionForm form,
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
        StockSummaryForm stockSumryForm = (StockSummaryForm)form;

        String status = util.nvl(req.getParameter("status")).trim();
        String lab = util.nvl(req.getParameter("lab")).trim();;
        String rpttype = util.nvl(req.getParameter("rpt")).trim();;
        String group = util.nvl(req.getParameter("group")).trim();;
        HashMap dbinfo = info.getDmbsInfoLst();
        String sh = (String)dbinfo.get("SHAPE");
        String szval = (String)dbinfo.get("SIZE");
        req.setAttribute("status", status);
        req.setAttribute("lab", lab);
        req.setAttribute("group", group);
        ArrayList ary = new ArrayList();

        /*    SHAPE property            */
        ArrayList shapeList = new ArrayList();
        //String shapeqry ="select val,srt from prp where mprp= ? and flg is null order by srt ";
        String shapeqry="select distinct a.val , a.srt from stk_dtl a where a.mprp=? and grp=1 order by a.srt ";
        ary = new ArrayList();
        ary.add(sh);
       ArrayList   outLst = db.execSqlLst("ShapeInfrm", shapeqry, ary);
        PreparedStatement   pst = (PreparedStatement)outLst.get(0);
        ResultSet   rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            if(!shapeList.contains(rs.getString("val"))){
            shapeList.add(rs.getString("val"));
            }
        }
        info.setShapeList(shapeList);
       rs.close();
            pst.close();

        /*    SIZE property            */
        ArrayList sizeList = new ArrayList();
        //String sizeqry ="select distinct a.val , a.srt from stk_dtl a where a.mprp=? and grp=1 order by a.srt ";
        String sizeqry ="select distinct a.val , b.srt " + 
        "from stk_dtl a , prp b " + 
        "where a.mprp = b.mprp and a.val = b.val and a.mprp=? and a.grp=1 order by b.srt ";
        ary = new ArrayList();
        ary.add(szval);
             outLst = db.execSqlLst("SizeInfrm", sizeqry, ary);
              pst = (PreparedStatement)outLst.get(0);
              rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            sizeList.add(rs.getString("val"));
        }
        info.setSizeList(sizeList);
       rs.close();
            pst.close();

        /*  To display quantity and cts */
        HashMap qtyctsshsztbl = new HashMap();


        String qtyctsshszqry =
            "select  sum(c.qty) totqty,sum(to_char(trunc(c.cts,2),'99999990.00')) totcts,a.val sh,b.val sz from stk_dtl a ,stk_dtl b,mstk c,df_stk_stt d where a.mprp ='"+sh+"' and " +
            "b.mprp ='"+szval+"' and c.stt <> 'MKSD' and c.stt not like 'SUS%' and a.mstk_idn=b.mstk_idn " +
            "and a.mstk_idn=c.idn and a.grp=1 and b.grp=1 and d.stt=c.stt ";
        if (!status.equals("")) {
            qtyctsshszqry += " and c.stt='" + status + "'";

        }
        if (!lab.equals("")) {
            qtyctsshszqry += " and c.cert_lab='" + lab + "'";
        }
        if (!group.equals("")) {
            if(group.equals("ALL"))
            qtyctsshszqry += " and c.stt = d.stt and d.grp1 <> 'SOLD' ";
            else
            qtyctsshszqry += " and c.stt = d.stt and d.grp1='" + group + "'";
        }
        qtyctsshszqry += " group by  a.val,b.val";
          outLst = db.execSqlLst("qtyctsshszqry", qtyctsshszqry, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String sh_sz = rs.getString("sh") + "_" + rs.getString("sz");
            String qty_cts =
                rs.getString("totqty") + "|" +
                rs.getString("totcts");
            qtyctsshsztbl.put(sh_sz, qty_cts);
        }
            rs.close();
            pst.close();
        info.setQtyctsshsztbl(qtyctsshsztbl);
        // To get Total of each Shape on right side
        HashMap sh_QtyCtstbl=new HashMap();
        String shapeQtycts="select  sum(c.qty) totqty,sum(to_char(trunc(c.cts,2),'99999990.00')) totcts,a.val sh from stk_dtl a ,stk_dtl b,mstk c,df_stk_stt d " + 
        "where a.mprp ='"+sh+"' and b.mprp ='"+szval+"' and c.stt <> 'MKSD' and c.stt not like 'SUS%' and a.mstk_idn=b.mstk_idn and a.mstk_idn=c.idn and a.grp=1  and d.stt=c.stt " + 
        "and b.grp=1 " ;
      if (!status.equals("")) {
         shapeQtycts+=   "  and c.stt='" + status + "'" ;
      }
      if (!lab.equals("")) {
         shapeQtycts+= " and c.cert_lab='" + lab + "'" ;
      }
        if (!group.equals("")) {
            if(group.equals("ALL"))
            shapeQtycts += " and c.stt = d.stt and d.grp1 <> 'SOLD' ";
            else
            shapeQtycts += " and c.stt = d.stt and d.grp1='" + group + "'";
        }
      shapeQtycts+=" group by  a.val ";
          outLst = db.execSqlLst("shapeQtycts", shapeQtycts, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
      while (rs.next()) {
          String shp =util.nvl(rs.getString("sh"));
          String qty_cts =
            util.nvl(rs.getString("totQty") )+ "|" +
            util.nvl(rs.getString("totCts"));
           sh_QtyCtstbl.put(shp,qty_cts)  ;
      }
            rs.close();
            pst.close();
       info.setSh_QtyCtstbl(sh_QtyCtstbl);
       // To get Total of each Size on bottom
       HashMap sz_Qtyctstbl=new HashMap();
       String sizeQtycts="select  sum(c.qty) totqty,sum(to_char(trunc(c.cts,2),'99999990.00')) totcts,b.val sz from stk_dtl a ,stk_dtl b,mstk c,df_stk_stt d \n" + 
       "where a.mprp ='"+sh+"' and b.mprp ='"+szval+"' and c.stt <> 'MKSD' and c.stt not like 'SUS%' and a.mstk_idn=b.mstk_idn and a.mstk_idn=c.idn and a.grp=1   and d.stt=c.stt \n" + 
       "and b.grp=1  " ;
      if (!status.equals("")) {
        sizeQtycts+= " and c.stt='" + status + "'";
      }
      if (!lab.equals("")) {
        sizeQtycts+=" and c.cert_lab='" + lab + "'";
      }
        if (!group.equals("")) {
            if(group.equals("ALL"))
            sizeQtycts += " and c.stt = d.stt and d.grp1 <> 'SOLD' ";
            else
            sizeQtycts += "and c.stt = d.stt and d.grp1='" + group + "'";
        }
        sizeQtycts+=" group by  b.val";
          outLst = db.execSqlLst("sizeQtycts", sizeQtycts, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
      while (rs.next()) {
          String sz =util.nvl(rs.getString("sz"));
          String qty_cts =
            util.nvl(rs.getString("totQty") )+ "|" +
            util.nvl(rs.getString("totCts"));
           sz_Qtyctstbl.put(sz,qty_cts)  ;
      }
            rs.close();
            pst.close();
      info.setSz_Qtyctstbl(sz_Qtyctstbl);
      
      // To get grand Total 
      String grdshSzctsqty="";
      String grd="select  sum(c.qty) totqty,sum(to_char(trunc(c.cts,2),'99999990.00')) totcts from stk_dtl a ,stk_dtl b,mstk c,df_stk_stt d "+
  " where a.mprp ='"+sh+"' and b.mprp ='"+szval+"' and c.stt <> 'MKSD' and c.stt not like 'SUS%' and a.mstk_idn=b.mstk_idn and a.mstk_idn=c.idn and a.grp=1   and d.stt=c.stt "+
 " and b.grp=1  " ;
      if (!status.equals("")) {
        grd+=  " and c.stt='"+status+"'";
      }
      if (!lab.equals("")) {
        grd+= " and c.cert_lab='" + lab + "'";   
      }
        if (!group.equals("")) {
            if(group.equals("ALL"))
            grd += " and c.stt = d.stt and d.grp1 <> 'SOLD' ";
            else  
          grd+= "and c.stt = d.stt and d.grp1='" + group + "'";
        }
          outLst = db.execSqlLst("grd", grd, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
      while (rs.next()) {
         grdshSzctsqty =
          util.nvl(rs.getString("totQty") )+ "|" +
          util.nvl(rs.getString("totCts"));
                  
      }
            rs.close();
            pst.close();
      session.setAttribute("grdshSzctsqty", grdshSzctsqty);
        rs = null;
        return am.findForward("loadShSize");
        }
    }

    public ActionForward loadClrPurity(ActionMapping am, ActionForm form,
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
        StockSummaryForm stockSumryForm = (StockSummaryForm)form;
        String status = util.nvl(req.getParameter("status")).trim();;
        String lab = util.nvl(req.getParameter("lab")).trim();;
        String shape = util.nvl(req.getParameter("shape")).trim();;
        String size = util.nvl(req.getParameter("size")).trim();
        String group = util.nvl(req.getParameter("group")).trim();
        HashMap dbinfo = info.getDmbsInfoLst();
        String colval = (String)dbinfo.get("COL");
        String clrval = (String)dbinfo.get("CLR");
        String shval = (String)dbinfo.get("SHAPE");
        String szval = (String)dbinfo.get("SIZE");
        /*    COLOR property            */
        ArrayList colorList = new ArrayList();
        String clrqry =
            "select distinct a.val , a.srt from stk_dtl a where a.mprp='" + colval + "' and grp=1 and a.val not like '%-' and a.val not like '%+' order by a.srt ";
         ArrayList outLst = db.execSqlLst("ColorInfrm", clrqry, new ArrayList());
        PreparedStatement   pst = (PreparedStatement)outLst.get(0);
         ResultSet  rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            colorList.add(rs.getString("val"));
        }
        info.setColorList(colorList);
       rs.close();
       pst.close();
        /*    PURITY property            */
        ArrayList purityList = new ArrayList();
        String purityqry =
            "select distinct a.val , a.srt from stk_dtl a where a.mprp='" + clrval + "' and a.grp=1 and a.val not like '%-' and a.val not like '%+' order by a.srt ";
           outLst = db.execSqlLst("purityInfrm", purityqry, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            purityList.add(rs.getString("val"));
        }
        info.setPurityList(purityList);
         rs.close();
          pst.close();

        /*  To display quantity and cts */
        HashMap qtyctsClrPurtbl = new HashMap();
        ArrayList ary = new ArrayList();
        String qtyctsClrPurqry =
            "select sum(c.qty) totqty,sum(to_char(trunc(c.cts,2),'99999990.00')) totcts, Replace(Replace(a.val,'+',''),'-','') color," + 
            "       Replace(Replace(b.val,'+',''),'-','') purity  from stk_dtl a ,stk_dtl b,mstk c ";
        if (!shape.equals("")) {
            qtyctsClrPurqry += ",stk_dtl d";
        }
        if (!size.equals("")) {
            qtyctsClrPurqry += ",stk_dtl e";
        }
        if (!group.equals("")) {
            qtyctsClrPurqry += ",df_stk_stt f";
        }
        qtyctsClrPurqry +=
                " where a.mprp ='"+colval+"' and " + "b.mprp ='"+clrval+"' and c.stt <> 'MKSD' and c.stt not like 'SUS%' and a.mstk_idn=b.mstk_idn and  " +
                "a.mstk_idn=c.idn  and a.grp=1 and b.grp=1 ";
        if (!status.equals("")) {
            qtyctsClrPurqry += " and  c.stt='" + status + "'";
            // ary.add(status);
        }
        if (!lab.equals("")) {
            qtyctsClrPurqry += " and c.cert_lab='" + lab + "'";
            // ary.add(lab);
        }
        if (!shape.equals("")) {
            qtyctsClrPurqry +=
                    " and d.mprp='"+shval+"' and d.val='" + shape + "' and d.mstk_idn=a.mstk_idn  and d.grp=1 ";///Mayur shval
            //ary.add(shape);
        }
        if (!size.equals("")) {
            qtyctsClrPurqry +=
                    " and e.mprp='"+szval+"' and ( e.val='"+size+"' or e.val='+"+size+"') and e.mstk_idn=a.mstk_idn and e.grp=1";
            //  ary.add(size);
        }
        if (!lab.equals("")) {
            qtyctsClrPurqry += " and c.cert_lab='" + lab + "'";
            // ary.add(lab);
        }
        if (!group.equals("")) {
            if(group.equals("ALL"))
            qtyctsClrPurqry+=" and c.stt = f.stt and f.grp1 <> 'SOLD' ";
            else
            qtyctsClrPurqry += " and c.stt = f.stt and f.grp1='" + group + "'";
            // ary.add(lab);
        }
        qtyctsClrPurqry += " group by Replace(Replace(a.val,'+',''),'-',''), Replace(Replace(b.val,'+',''),'-','') ";
          outLst = db.execSqlLst("qtyctsClrPurqry", qtyctsClrPurqry, ary);
            pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            String colr_pur =
                rs.getString("color") + "_" + rs.getString("purity");
            String qty_cts =
                rs.getString("totqty") + "|" +
                rs.getString("totcts");
            qtyctsClrPurtbl.put(colr_pur, qty_cts);
        }
            rs.close();
            pst.close();
        info.setQtyctsClrPurtbl(qtyctsClrPurtbl);
        // To get Color quantity-Cts
        HashMap col_ctsqty=new HashMap();
        String col_ctsqtyqry="select sum(c.qty) totqty,sum(to_char(trunc(c.cts,2),'99999990.00')) totcts, Replace(Replace(a.val,'+',''),'-','') color from stk_dtl a ," +
            "stk_dtl b,mstk c " ;
      if (!shape.equals("")) {
        col_ctsqtyqry+=",stk_dtl d";
      }
      if (!size.equals("")) {
          col_ctsqtyqry += ",stk_dtl e";
      }
        if (!group.equals("")) {
            col_ctsqtyqry += ",df_stk_stt f ";
        }
      col_ctsqtyqry+=
            "  where a.mprp ='"+colval+"' and b.mprp ='"+clrval+"' and c.stt <> 'MKSD' and c.stt not like 'SUS%' and a.mstk_idn=b.mstk_idn and  a.mstk_idn=c.idn " +
            " and a.grp=1 and b.grp=1 ";
      if (!status.equals("")) {
          col_ctsqtyqry += " and  c.stt='" + status + "'";
          // ary.add(status);
      }
      if (!lab.equals("")) {
          col_ctsqtyqry += " and c.cert_lab='" + lab + "'";
          // ary.add(lab);
      }
      if (!shape.equals("")) {
          col_ctsqtyqry +=
                  " and d.mprp='"+shval+"' and d.val='" + shape + "' and d.mstk_idn=a.mstk_idn  and d.grp=1 ";
          //ary.add(shape);
      }
      if (!size.equals("")) {
          col_ctsqtyqry +=
                  " and e.mprp='"+szval+"' and ( e.val='"+size+"' or e.val='+"+size+"') and e.mstk_idn=a.mstk_idn and e.grp=1 ";
          //  ary.add(size);
      }
      if(!group.equals("")){
          if(group.equals("ALL"))
          col_ctsqtyqry+=" and c.stt = f.stt and f.grp1 <> 'SOLD' ";
          else
          col_ctsqtyqry+= " and c.stt = f.stt and f.grp1='" + group + "'";
      }
      col_ctsqtyqry += " group by Replace(Replace(a.val,'+',''),'-','') ";
          outLst = db.execSqlLst("qtyctsClrPurqry", qtyctsClrPurqry, ary);
            pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
      while (rs.next()) {
        String colr =
            rs.getString("color");
        String qty_cts =
            rs.getString("totqty") + "|" +
            rs.getString("totcts"); 
        col_ctsqty.put(colr,qty_cts);  
      }
            rs.close();
            pst.close();
      info.setCol_ctsqty(col_ctsqty);
      
      //To get Clarity Quantity-Cts
      
      HashMap clry_ctsqty=new HashMap();
      String clry_Qtyctsqry="select sum(c.qty) totqty,sum(to_char(trunc(c.cts,2),'99999990.00')) totcts, Replace(Replace(b.val,'+',''),'-','') purity  from stk_dtl a ," +
          "stk_dtl b,mstk c ";
      if (!shape.equals("")) {
        clry_Qtyctsqry+=",stk_dtl d";
      }
      if (!size.equals("")) {
          clry_Qtyctsqry += ",stk_dtl e";
      }
        if (!group.equals("")) {
            clry_Qtyctsqry += ",df_stk_stt f";
        }
      clry_Qtyctsqry+=
            " where a.mprp ='"+colval+"' and b.mprp ='"+clrval+"' and c.stt <> 'MKSD' and c.stt not like 'SUS%' and a.mstk_idn=b.mstk_idn and  a.mstk_idn=c.idn " +
            "and a.grp=1 and b.grp=1 ";
      if (!status.equals("")) {
          clry_Qtyctsqry += " and  c.stt='" + status + "'";
          // ary.add(status);
      }
      if (!lab.equals("")) {
          clry_Qtyctsqry += " and c.cert_lab='" + lab + "'";
          // ary.add(lab);
      }
      if (!shape.equals("")) {
          clry_Qtyctsqry +=
                  " and d.mprp='"+shval+"' and d.val='" + shape + "' and d.mstk_idn=a.mstk_idn  and d.grp=1 ";
          //ary.add(shape);
      }
      if (!size.equals("")) {
          clry_Qtyctsqry +=
                  " and e.mprp='"+szval+"' and ( e.val='"+size+"' or e.val='+"+size+"') and e.mstk_idn=a.mstk_idn and e.grp=1";
          //  ary.add(size);
      }
      
      if(!group.equals("")){
          if(group.equals("ALL"))
          clry_Qtyctsqry+=" and c.stt = f.stt and f.grp1 <> 'SOLD' ";
          else
          clry_Qtyctsqry += " and c.stt = f.stt and f.grp1='" + group + "'";
      }
      clry_Qtyctsqry += " group by Replace(Replace(b.val,'+',''),'-','')";
          outLst = db.execSqlLst("clry_Qtyctsqry", clry_Qtyctsqry, ary);
            pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
      while (rs.next()) {
         String clarity=rs.getString("purity"); 
        String qty_cts =
            rs.getString("totqty") + "|" +
            rs.getString("totcts"); 
        clry_ctsqty.put(clarity,qty_cts);
      }
            rs.close();
            pst.close();
      info.setClry_ctsqty(clry_ctsqty);
      String grdqty_cts ="";
      String grndcolClrqry="select sum(c.qty) totqty,sum(to_char(trunc(c.cts,2),'99999990.00')) totcts  from stk_dtl a ,stk_dtl b,mstk c  " ;
      if (!shape.equals("")) {
        grndcolClrqry+=",stk_dtl d";
      }
      if (!size.equals("")) {
          grndcolClrqry += ",stk_dtl e";
      }
        if (!group.equals("")) {
            grndcolClrqry += ",df_stk_stt f";
        }
      grndcolClrqry+=
            " where a.mprp ='"+colval+"' and b.mprp ='"+clrval+"' and c.stt <> 'MKSD' and c.stt not like 'SUS%' and a.mstk_idn=b.mstk_idn and  a.mstk_idn=c.idn " +
            "and a.grp=1 and b.grp=1 ";
      if (!status.equals("")) {
          grndcolClrqry += " and  c.stt='" + status + "'";
          // ary.add(status);
      }
      if (!lab.equals("")) {
          grndcolClrqry += " and c.cert_lab='" + lab + "'";
          // ary.add(lab);
      }
      if (!shape.equals("")) {
          grndcolClrqry +=
                  " and d.mprp='"+shval+"' and d.val='" + shape + "' and d.mstk_idn=a.mstk_idn  and d.grp=1 ";
          //ary.add(shape);
      }
      if (!size.equals("")) {
          grndcolClrqry +=
                  " and e.mprp='"+szval+"' and ( e.val='"+size+"' or e.val='+"+size+"') and e.mstk_idn=a.mstk_idn and e.grp=1";
          //  ary.add(size);
      }
      
        if (!group.equals("")) {
            if(group.equals("ALL"))
            grndcolClrqry+=" and c.stt = f.stt and f.grp1 <> 'SOLD' ";
            else
            grndcolClrqry += "  and c.stt = f.stt and f.grp1='" + group + "' ";
        }
        
          outLst = db.execSqlLst("grndcolClrqry", grndcolClrqry, ary);
            pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
      while(rs.next()) {
         grdqty_cts =
            rs.getString("totqty") + "|" +
            rs.getString("totcts");  
        
      }
            rs.close();
            pst.close();
      session.setAttribute("grndcolClr", grdqty_cts);
        rs = null;
        return am.findForward("loadColrPurty");
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
    StockSummaryForm stockSumryForm = (StockSummaryForm)form;
    String status = util.nvl(req.getParameter("status")).trim();
    String group = util.nvl(req.getParameter("group")).trim();
    String lab = util.nvl(req.getParameter("lab")).trim();
    String shape=util.nvl(req.getParameter("shape")).trim();
    String size=util.nvl(req.getParameter("size")).trim();
   
    String color=util.nvl(req.getParameter("color"));
    String purity=util.nvl(req.getParameter("purity"));
    session.setAttribute("status", status);
    session.setAttribute("lab", lab);
    session.setAttribute("shape", shape);
    session.setAttribute("size", size);
    session.setAttribute("color", color);
    session.setAttribute("purity", purity);
    session.setAttribute("group", group);
        HashMap dbinfo = info.getDmbsInfoLst();
        String colval = (String)dbinfo.get("COL");
        String clrval = (String)dbinfo.get("CLR");
        String shval = (String)dbinfo.get("SHAPE");
        String szval = (String)dbinfo.get("SIZE");
    ArrayList aryp=new ArrayList();
    String dspPrp="";
    ArrayList prpAry=new ArrayList();
    String prpQry="select prp from rep_prp where mdl='WEB_VW' and flg='Y' order by RNK";
    ArrayList outLst = db.execSqlLst("Display Properties", prpQry, aryp);
  PreparedStatement  pst = (PreparedStatement)outLst.get(0);
   ResultSet  rs = (ResultSet)outLst.get(1);
    while(rs.next()) {
    dspPrp+=rs.getString("prp")+",";
    prpAry.add(rs.getString("prp"));
    }
    rs.close();
    pst.close();
            
    session.setAttribute("prpArray", prpAry);
    dspPrp=dspPrp.substring(0,dspPrp.length()-2);
    ArrayList aryd=new ArrayList();
    String delQ = " Delete from gt_srch_rslt ";
    int ct = db.execUpd(" Del Old Pkts ", delQ, aryd);
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());


    String srchRefQ = " Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt  , qty , cts , sk1 , cert_lab) " +
    // " select pkt_ty, b.idn, b.vnm, b.dte, stt , flg , qty , cts , sk1 , cert_lab " +
    // " from mstk b " +
    // " where stt= ? and cert_lab=?" ;
    " select distinct a.pkt_ty, a.idn, a.vnm, a.dte, a.stt  , a.qty , a.cts , a.sk1 , a.cert_lab "+
    " from mstk a " ;
    if(!shape.equals("") ) {
    srchRefQ+= ",stk_dtl b" ;
    }
    if(!size.equals("")) {
    srchRefQ+= ",stk_dtl c " ;
    }
    if(!color.equals("") ){
    srchRefQ+=" ,stk_dtl d ";
    }
    if(!purity.equals("")){
    srchRefQ+=" ,stk_dtl e ";
    }
        if(!group.equals("")){
        srchRefQ+=" ,df_stk_stt f ";
        }
    srchRefQ+=" where  a.stt <> 'MKSD' and a.stt not like 'SUS%' and  " ;
    if(!status.equals(""))
    {
    srchRefQ+=" a.stt= ? and" ;
    aryd.add(status);

    }
    if(!lab.equals("")) {
    srchRefQ+=" nvl(a.cert_lab,'NA')=? and ";
    aryd.add(lab);
    }
    if(!shape.equals("") && !size.equals("")) {
    srchRefQ+=" a.idn=b.mstk_idn and a.idn=c.mstk_idn "+
    " and b.grp=1 and c.grp=1 and b.mstk_idn=c.mstk_idn and b.mprp='"+shval+"' and b.val=? "+
    " and c.mprp='"+szval+"' and  ( c.val='"+size+"' or c.val='+"+size+"') and ";
    aryd.add(shape);
    // aryd.add(size);
    }
    else
    {
    if(!shape.equals("")) {
    srchRefQ+=" a.idn=b.mstk_idn "+
    " and b.grp=1 and b.mprp='"+shval+"' and b.val=? and ";

    aryd.add(shape);
    // aryd.add(size);
    }
    if(!size.equals("")) {
    srchRefQ+=" a.idn=c.mstk_idn "+
    " and c.grp=1 "+
    " and c.mprp='"+szval+"' and ( c.val='"+size+"' or c.val='+"+size+"') and ";
    }
    }
    if(!color.equals("") && !purity.equals("")){
    srchRefQ+=" a.idn=d.mstk_idn and a.idn=e.mstk_idn and d.mstk_idn =e.mstk_idn and "+
    " d.mprp='"+colval+"' and d.val=? and e.mprp='"+clrval+"' and Replace(Replace(e.val,'+',''),'-','')=? and ";
    aryd.add(color);
    aryd.add(purity);
    }
    else {
    if(!color.equals("") ){
    srchRefQ+=" a.idn=d.mstk_idn and "+
    " d.mprp='"+colval+"' and d.val=? and ";
    aryd.add(color);
    }
    if(!purity.equals("")){
    srchRefQ+=" a.idn=e.mstk_idn and "+
    " e.mprp='"+clrval+"' and e.val=? and ";
    aryd.add(purity);
    }
    }
        if(!group.equals("")){
            if(group.equals("ALL"))
                srchRefQ+=" a.stt = f.stt and f.grp1 <> 'SOLD' and ";
            else{   
            srchRefQ+=" a.stt = f.stt and f.grp1=? and ";
            aryd.add(group);   
            }
        }
  //  srchRefQ+=" cts > 0  ";
  srchRefQ+=" 1=1";
   

    ct = db.execUpd(" Srch Prp ", srchRefQ, aryd);

    String pktPrp = "pkgmkt.pktPrp(0,?)";
    ArrayList aryv = new ArrayList();
    aryv.add("WEB_VW");
    ct = db.execCall(" Srch Prp ", pktPrp, aryv);
    ArrayList pktList = new ArrayList();
    String cond="";
    String srchQ =" select stk_idn , pkt_ty, vnm, pkt_dte, stt , qty , cts , cert_lab ";


    for (int i = 0; i < prpAry.size(); i++) {
    String prp=(String)prpAry.get(i);
    String fld = "prp_";
    int j = i + 1;
    if (j < 10)
    fld += "00" + j;
    else if (j < 100)
    fld += "0" + j;
    else if (j > 100)
    fld += j;


    srchQ += ", " + fld;
    /* if(!shape.equals(""))
    {
    if(prp.equals("SHAPE")) {
    cond+=" where "+fld+"='"+shape+"'";
    }
    }
    if(!size.equals(""))
    {
    if(prp.equals("CRTWT")) {
    cond+=" and "+fld+"="+size+"";
    }
    } */

    }


    String rsltQ = srchQ + " from gt_srch_rslt " ;
    /* if(!cond.equals("")) {
    rsltQ+=cond;
    } */
    rsltQ+= " order by sk1";

    ArrayList ary = new ArrayList();

     outLst = db.execSqlLst("search Result", rsltQ, ary);
      pst = (PreparedStatement)outLst.get(0);
       rs = (ResultSet)outLst.get(1);

    try {
    while (rs.next()) {
    String cert_lab = util.nvl(rs.getString("cert_lab"));
    String stkIdn = util.nvl(rs.getString("stk_idn"));
    HashMap pktPrpMap = new HashMap();
    pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
    String vnm = util.nvl(rs.getString("vnm"));
    pktPrpMap.put("vnm", vnm);
    pktPrpMap.put("stk_idn", stkIdn);
    pktPrpMap.put("qty", util.nvl(rs.getString("qty")));
    pktPrpMap.put("cts", util.nvl(rs.getString("cts")));

    for (int j = 0; j < prpAry.size(); j++) {
    String prp = (String)prpAry.get(j);

    String fld = "prp_";
    if (j < 9)
    fld = "prp_00" + (j + 1);
    else
    fld = "prp_0" + (j + 1);

    String val = util.nvl(rs.getString(fld));


    pktPrpMap.put(prp, val);
    }

    pktList.add(pktPrpMap);
    }
     rs.close();
        pst.close();
    session.setAttribute("pktList", pktList);
    } catch (SQLException sqle) {

    // TODO: Add catch code
    sqle.printStackTrace();
    }
    return am.findForward("loadPktDtl");
        }
    }
 
    public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res, ArrayList vwPrpLst){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList pktList = new ArrayList();
       
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , srch_id , rmk , rap_rte ,  rap_dis , quot rte ";

        

        for (int i = 0; i < vwPrpLst.size(); i++) {
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

        
        String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by sk1";
        
        ArrayList ary = new ArrayList();
        ary.add("Z");
      ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {

                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.put("vnm",vnm);
                          
                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
               
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                      if (prp.toUpperCase().equals("RAP_DIS"))
                                 val = util.nvl(rs.getString("rap_dis"));
                                 
                     if (prp.toUpperCase().equals("RAP_RTE"))
                                 val = util.nvl(rs.getString("rap_rte"));
                    if(prp.equals("RTE"))
                                 val = util.nvl(rs.getString("rte"));
                        
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
        

        return pktList;
    }
    public ActionForward createStatusLabXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "StatusLabReportExcel"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        HSSFWorkbook hwb = null;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        hwb = xlUtil.getDataStatusLabInXl(req);
       // hwb = xlUtil.getInXl("SRCH", req, "AS_VIEW");
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
        return null;
        }
    }
    
    public ActionForward createColorPurityXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "ColorPurityReportExcel"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        HSSFWorkbook hwb = null;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        hwb = xlUtil.getDataColorPurityInXl(req);
       // hwb = xlUtil.getInXl("SRCH", req, "AS_VIEW");
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
        return null;
        }
    }
    
    public ActionForward createShapeSzXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "ShapeSizeReportExcel"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        HSSFWorkbook hwb = null;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        hwb = xlUtil.getDataShapeSzInXl(req);
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
        return null;
        }
    }
    
    public ActionForward createDtlXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          ExcelUtil xlUtil = new ExcelUtil();
          xlUtil.init(db, util, info);
          OutputStream out = res.getOutputStream();
          String CONTENT_TYPE = "getServletContext()/vnd-excel";
          String fileNm = "DtlReportExcel"+util.getToDteTime()+".xls";
          res.setContentType(CONTENT_TYPE);
          res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
          ArrayList pktList = (ArrayList)session.getAttribute("pktList");
          ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
       //   HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList,req);
       //   hwb.write(out);
          out.flush();
          out.close();
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
            }
            }
            return rtnPg;
            }
}
