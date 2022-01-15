package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;
import ft.com.mix.TransferPktAction;

import java.io.OutputStream;

import java.sql.Connection;
import java.sql.ResultSet;

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

public class DailySaleReportCD extends DispatchAction {
      
  public DailySaleReportCD() {
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
      DailySalesReportForm dailySalesReportForm = (DailySalesReportForm)af;
      dailySalesReportForm.reset();   
     
      return am.findForward("load");   

    }}
   
  public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
               throws Exception {
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
     DailySalesReportForm dailySalesReportForm = (DailySalesReportForm)af;
     ResultSet rs = null;
     ArrayList ary = new ArrayList();
     String dtefrom = " trunc(sysdate) ";
     String dteto = " trunc(sysdate) ";
     String flg=util.nvl(req.getParameter("flg"));
     if(flg.equals("New")){
         dailySalesReportForm.reset();
     }
     String dfr = util.nvl((String)dailySalesReportForm.getValue("dtefr"));
     String dto = util.nvl((String)dailySalesReportForm.getValue("dteto"));
     String jper = util.nvl((String)dailySalesReportForm.getValue("jper"));
     String jSel = util.nvl((String)dailySalesReportForm.getValue("jSel"));
     
     if(!dfr.equals(""))
       dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
     
     if(!dto.equals(""))
       dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
     
     String dteQ=" and trunc(a.dte)  between "+dtefrom+" and "+dteto;  
     
     HashMap dtlCollection = new HashMap();
     dtlCollection.put("JP", jper);
       dtlCollection.put("JS", jSel);
     String dataQury =" select upper(trim(e.byr)) byr ,  b.vnm , lower(SUBSTR(b.vnm,0,3)) packNo,TRUNC (NVL (a.fnl_sal, a.quot) / NVL (e.exh_rte, 1), 2) fnl_usd, decode(e.cur,'INR', nvl(a.fnl_sal,a.quot),'') rteRs ," + 
     "decode(b.pkt_ty,'NR', to_char(a.cts,'9999990.00'),'')  selsel , ''  selpur, '' ctsper, '' al, decode(b.pkt_ty,'MIX', to_char(a.cts,'9999990.00'),'') ctssel,decode(nvl(e.exh_rte,1),1,'',e.exh_rte) exh_rte, (d.num+nvl(e.mb_c,0)) mem_dis, " + 
     "decode(b.pkt_ty,'MIX',trunc((a.cts/c.num)*100,2),'') rejPer , e.trm , e.sb , a.rmk " + 
     "from dlv_dtl a , mstk b , stk_dtl c , stk_dtl d , dlv_v e " + 
     "where a.stt='DLV' and a.mstk_idn=b.idn and b.idn=c.mstk_idn and c.mstk_idn=d.mstk_idn " + 
     "and c.mprp='CRTWT' and c.grp=1 and d.mprp='MEM_DIS' and d.grp=1 and a.idn = e.idn " +dteQ+" order by 1";
       
        rs = db.execSql("dataCl", dataQury, new ArrayList());
       ArrayList pktList = new ArrayList();
       while(rs.next()){
         HashMap dataDtl = new HashMap();
         dataDtl.put("BYR", util.nvl(rs.getString("byr")));
         dataDtl.put("VNM", util.nvl(rs.getString("vnm")));
         dataDtl.put("PACKNO", util.nvl(rs.getString("packNo")));
         dataDtl.put("RTE", util.nvl(rs.getString("rteRs")));
         dataDtl.put("RTEUSD", util.nvl(rs.getString("fnl_usd")));
         dataDtl.put("SEL", util.nvl(rs.getString("selsel")));
         dataDtl.put("SELPUR", util.nvl(rs.getString("selpur")));
         dataDtl.put("CTSPER", util.nvl(rs.getString("ctsper")));
         dataDtl.put("CTSSEL", util.nvl(rs.getString("ctssel")));
         dataDtl.put("EXHRTE", util.nvl(rs.getString("exh_rte")));
         dataDtl.put("AL1", util.nvl(rs.getString("mem_dis")));
         dataDtl.put("AL", util.nvl(rs.getString("al")));

         dataDtl.put("REJPER", util.nvl(rs.getString("rejper")));
         dataDtl.put("TRM", util.nvl(rs.getString("trm")));
         dataDtl.put("SB", util.nvl(rs.getString("sb")));
         dataDtl.put("RMK", util.nvl(rs.getString("rmk")));

         pktList.add(dataDtl);
       }
       rs.close();
      dataQury =" select upper(trim(e.byr)) byr ,  b.vnm , lower(SUBSTR(b.vnm,0,3)) packNo,TRUNC (NVL (a.fnl_sal, a.quot) / NVL (e.exh_rte, 1), 2) fnl_usd, decode(e.cur,'INR', nvl(a.fnl_sal,a.quot),'') rteRs ," + 
     "decode(b.pkt_ty,'NR', to_char(a.cts,'9999990.00'),'')  selsel , ''  selpur,'' al, '' ctsper, decode(b.pkt_ty,'MIX', to_char(a.cts,'9999990.00'),'') ctssel,decode(nvl(e.exh_rte,1),1,'',e.exh_rte) exh_rte, (d.num+nvl(e.mb_c,0)) mem_dis, " + 
     "decode(b.pkt_ty,'MIX',trunc((a.cts/c.num)*100,2),'') rejPer , e.trm , e.sb , a.rmk " + 
     " from brc_dlv_dtl a , mstk b , stk_dtl c , stk_dtl d , brcdlv_v e "+
     " where a.stt='DLV' and a.mstk_idn=b.idn and b.idn=c.mstk_idn and c.mstk_idn=d.mstk_idn "+
     " and c.mprp='CRTWT' and c.grp=1 and d.mprp='MEM_DIS' and d.grp=1 and a.idn = e.idn "+
     "" +dteQ+" order by 1";
       
        rs = db.execSql("dataCl", dataQury, new ArrayList());
       while(rs.next()){
         HashMap dataDtl = new HashMap();
         dataDtl.put("BYR", util.nvl(rs.getString("byr")));
         dataDtl.put("VNM", util.nvl(rs.getString("vnm")));
         dataDtl.put("PACKNO", util.nvl(rs.getString("packNo")));
         dataDtl.put("RTE", util.nvl(rs.getString("rteRs")));
         dataDtl.put("RTEUSD", util.nvl(rs.getString("fnl_usd")));
         dataDtl.put("SEL", util.nvl(rs.getString("selsel")));
         dataDtl.put("SELPUR", util.nvl(rs.getString("selpur")));
         dataDtl.put("CTSPER", util.nvl(rs.getString("ctsper")));
         dataDtl.put("CTSSEL", util.nvl(rs.getString("ctssel")));
         dataDtl.put("EXHRTE", util.nvl(rs.getString("exh_rte")));
         dataDtl.put("AL1", util.nvl(rs.getString("mem_dis")));
         dataDtl.put("AL", util.nvl(rs.getString("al")));
         dataDtl.put("REJPER", util.nvl(rs.getString("rejper")));
         dataDtl.put("TRM", util.nvl(rs.getString("trm")));
         dataDtl.put("SB", util.nvl(rs.getString("sb")));
         dataDtl.put("RMK", util.nvl(rs.getString("rmk")));

         pktList.add(dataDtl);
       }
     rs.close();
     dataQury ="select  byr.get_nm(a.vndr_idn) byr ,  b.ref_idn vnm , lower(SUBSTR(b.ref_idn,0,3)) packNo, TRUNC (b.rte / NVL (a.exh_rte, 1), 2) fnl_usd, decode(a.cur,'INR', b.rte,'') rteRs ,to_char(b.cts,'9999990.00') selpur,''  selSel, '' ctsper, '' ctssel,decode(nvl(a.exh_rte,1),1,'',a.exh_rte) exh_rte , GET_PUR_PRP(b.pur_dtl_idn,'PUR_RTE') mem_dis,GET_PUR_PRP(b.pur_dtl_idn,'PUR_XRT') al,''  rejPer,\n" + 
     " '' trm , '' sb, '' rmk\n" + 
     "from mpur a , pur_dtl b , mnme c  where a.pur_idn = b.pur_idn\n" + 
     "and a.vndr_idn = c.nme_idn \n" + 
     "and a.typ='B' and trunc(a.pur_dte)  between "+dtefrom+" and "+dteto ;
       rs = db.execSql("dataCl", dataQury, new ArrayList());
      while(rs.next()){
        HashMap dataDtl = new HashMap();
        dataDtl.put("BYR", util.nvl(rs.getString("byr")));
        dataDtl.put("VNM", util.nvl(rs.getString("vnm")));
        dataDtl.put("PACKNO", util.nvl(rs.getString("packNo")));
        dataDtl.put("RTE", util.nvl(rs.getString("rteRs")));
        dataDtl.put("RTEUSD", util.nvl(rs.getString("fnl_usd")));
        dataDtl.put("SEL", util.nvl(rs.getString("selsel")));
        dataDtl.put("SELPUR", util.nvl(rs.getString("selpur")));
        dataDtl.put("CTSPER", util.nvl(rs.getString("ctsper")));
        dataDtl.put("CTSSEL", util.nvl(rs.getString("ctssel")));
        dataDtl.put("EXHRTE", util.nvl(rs.getString("exh_rte")));
        dataDtl.put("AL1", util.nvl(rs.getString("mem_dis")));
        dataDtl.put("AL", util.nvl(rs.getString("al")));
          dataDtl.put("REJPER", util.nvl(rs.getString("rejper")));
        dataDtl.put("TRM", util.nvl(rs.getString("trm")));
        dataDtl.put("SB", util.nvl(rs.getString("sb")));
        dataDtl.put("RMK", util.nvl(rs.getString("rmk")));

        pktList.add(dataDtl);
      }
       rs.close();
       dtlCollection.put("pktList", pktList);
       String b2cSql ="select count(*) cnt, sum(to_char(a.quot*a.cts,'999999990.00')) ttlVal,sum(to_char(a.cts,'999999990.00')) ttlCts  " + 
       "from brc_dlv_dtl a , stk_dtl b , brcdlv_v c " + 
       "where a.stt in ('RC') and a.mstk_idn=b.mstk_idn and b.mprp='LAB' and a.idn=c.idn " + 
       "and b.val  in ('B2C') " + 
       "and b.grp=1 ";
        rs = db.execSql("b2cSql", b2cSql, new ArrayList());
       while(rs.next()){
           HashMap b2cMap = new HashMap();
           b2cMap.put("CNT", rs.getString("cnt"));
          b2cMap.put("ttlCts", rs.getString("ttlCts"));
         b2cMap.put("ttlVlu", rs.getString("ttlVal"));
         dtlCollection.put("B2CMAP", b2cMap);

       }
       rs.close();
       
     String noneb2cSql ="select count(*) cnt, a.stt,sum(to_char(a.cts,'999999990.00')) ttlCts, sum(to_char(a.quot*a.cts,'999999990.00')) ttlVal , c.brc_nme " + 
     "from brc_dlv_dtl a , stk_dtl b , brcdlv_v c " + 
     "where a.stt in ('RC') and a.mstk_idn=b.mstk_idn and b.mprp='LAB' and a.idn=c.idn " + 
     "and b.val not in ('B2C') " + 
     "and b.grp=1 group by c.brc_nme, a.stt ";
     rs = db.execSql("b2cSql", noneb2cSql, new ArrayList());
       ArrayList  nonB2CList = new ArrayList();
     while(rs.next()){
        HashMap b2cMap = new HashMap();
        b2cMap.put("CNT", rs.getString("cnt"));
       b2cMap.put("ttlCts", rs.getString("ttlCts"));
      b2cMap.put("ttlVlu", rs.getString("ttlVal"));
       b2cMap.put("brcNme", rs.getString("brc_nme"));
        nonB2CList.add(b2cMap);
     }
     dtlCollection.put("NONB2CMAP", nonB2CList);

     rs.close();
     
     
       String nonCrt="select count(*) cnt , sum(to_char(nvl(upr,cmp)* cts,'999999990.00')) ttlVal  \n" + 
       ", sum(to_char(cts,'999999990.00')) ttlCts \n" + 
       ", decode(c.val, 'NON', 'NC', 'C') lab\n" + 
       "from mstk a, stk_dtl b, stk_dtl c\n" + 
       "where 1 = 1 \n" + 
       "and a.stt in ('AS_PRC', 'MKAV', 'MKIS', 'MKEI','SHIS','MKSA', 'MKWA', 'MKAP','MKWH','MKKS_IS','MKOS_IS') \n" + 
       "and a.idn = b.mstk_idn and b.grp = 1 and b.mprp = 'LOC' and b.val = 'IND'\n" + 
       "and a.idn = c.mstk_idn and c.grp = 1 and c.mprp = 'LAB' \n" + 
       "group by decode(c.val, 'NON', 'NC', 'C')";
     rs = db.execSql("b2cSql", nonCrt, new ArrayList());
      
     while(rs.next()){
        HashMap b2cMap = new HashMap();
        b2cMap.put("CNT", rs.getString("cnt"));
       b2cMap.put("ttlCts", rs.getString("ttlCts"));
      b2cMap.put("ttlVlu", rs.getString("ttlVal"));
       b2cMap.put("lab", rs.getString("lab"));
       dtlCollection.put(rs.getString("lab"), b2cMap);

     }
     rs.close();
      req.setAttribute("view", "yes");
      req.setAttribute("DtlData", dtlCollection);
     return am.findForward("load");   

   }}
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
        String fileNm = "DailyReportExcel"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList pktList = (ArrayList)session.getAttribute("pktDtlList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
        hwb.write(out);
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
              rtnPg=util.checkUserPageRights("report/dailySaleReportCD.do?","");
              if(rtnPg.equals("unauthorized"))
              util.updAccessLog(req,res,"Daily Sale Rpt", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Daily Sale Rpt", "init");
          }
          }
          return rtnPg;
          }

}
