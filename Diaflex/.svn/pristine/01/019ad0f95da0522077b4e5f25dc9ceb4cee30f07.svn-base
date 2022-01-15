package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.ExcelUtilObj;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.assort.AssortFinalRtnForm;

import java.io.IOException;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class BranchReceivedAction extends DispatchAction {
    
    public BranchReceivedAction() {
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
          BranchReceivedForm udf;
          udf = (BranchReceivedForm)af;
          String pkt_ty = util.nvl(req.getParameter("PKT_TY"));
         util.updAccessLog(req,res,"Branch Received", "load");
          
         udf.resetALL();
          HashMap params = new HashMap();
          params.put("PKT_TY", pkt_ty);
         dataCollection(req, params);
         util.updAccessLog(req,res,"Branch Received", "End");
      }
        finalizeObject(db, util);
      return am.findForward("load");
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
          BranchReceivedForm udf;
          udf = (BranchReceivedForm)af;
          util.updAccessLog(req,res,"Branch Received", "Fetch");
          String pkt_ty = util.nvl(req.getParameter("PKT_TY"),"NR");
          HashMap params = new HashMap();
          params.put("vnm", udf.getValue("vnm"));
          params.put("PKT_TY", pkt_ty);
         dataCollection(req, params);
        udf.reset();
          util.updAccessLog(req,res,"Branch Received", "End");
        util.setMdl("Branch Received");
      }
        finalizeObject(db, util);
      return am.findForward("load");
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
          BranchReceivedForm udf;
          udf = (BranchReceivedForm)af;
          util.updAccessLog(req,res,"Branch Received", "Save");
          ArrayList ary = new ArrayList();
        Enumeration reqNme = req.getParameterNames();
          while(reqNme.hasMoreElements()) {
              String paramNm = util.nvl((String)reqNme.nextElement());
             if(paramNm.indexOf("RC_") > -1) {
                String val = req.getParameter(paramNm);
                if(val!=null && val.length()>1){
                String[] valLst = val.split("_");
                if(valLst.length==2){
                String dlvIdn = valLst[0];
                String mstkIdn = valLst[1];
               ary = new ArrayList();
               ary.add(dlvIdn);
               ary.add(mstkIdn);
               int ct = db.execCall("insert gt","BRC_DLV_PKG.RECEIVE(pIdn => ? , pStkIdn => ?) ", ary);
                }
               
                }
                 
             }
          }
          
          
  
      }
     req.setAttribute("msg", "Process Done Successfully....");
       util.updAccessLog(req,res,"Branch Received", "End");
       finalizeObject(db, util);
            return load(am, af, req, res);
   }
   
  public void dataCollection(HttpServletRequest req, HashMap params){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      String cnt =util.nvl((String)info.getDmbsInfoLst().get("CNT"));
      String pkt_ty=util.nvl((String)params.get("PKT_TY"));
      ArrayList ary = new ArrayList();
      String sql = "select a.idn , b.dlv_idn ,b.sal_idn, e.nme ,  f.nme brnNme , d.dtls , to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte,to_char(trunc(b.cts,2) * nvl(b.fnl_sal,b.quot), '99999990.00') amt, \n" + 
      "b.mstk_idn,mst.vnm,b.qty,to_char(b.cts,'999990.99') cts,b.sh,b.sz,b.col,b.clr,b.rte,b.quot,nvl(b.fnl_sal,b.quot) fnl_sal ,  \n" + 
      "to_char(decode(mst.rap_rte, 1, '', trunc(((nvl(b.fnl_sal,b.quot)/greatest(mst.rap_rte,1))*100)-100,2)),9990.99) saldis,mst.sk1  \n" + 
      "from\n" + 
      "brc_mdlv a,brc_dlv_dtl b,mstk mst,msal c,nme_rel_all_v d,nme_v e,nme_v f\n" + 
      "where\n" + 
      "a.idn=b.idn and b.sal_idn=c.idn and b.mstk_idn=mst.idn and c.nmerel_idn=d.nmerel_idn and c.nme_idn=e.nme_idn and a.inv_nme_idn=f.nme_idn and a.stt='IS' and b.stt='IS' ";
      String vnm = util.nvl((String)params.get("vnm"));
      if(!vnm.equals("")){
      vnm = util.getVnm(vnm);
      sql = sql+" and mst.vnm in ("+vnm+")";
      }
      if(!pkt_ty.equals("")){
      sql=sql+" and mst.pkt_ty = ?";
          ary.add(pkt_ty);
      }
      sql = sql+" order by a.dlv_idn desc,e.nme,mst.sk1 ";
      if(cnt.equalsIgnoreCase("NJ")){
          ary = new ArrayList();
          sql = "select a.idn , b.dlv_idn ,b.sal_idn, e.nme ,  f.nme brnNme , d.dtls , to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte,to_char(trunc(b.cts,2) * nvl(b.fnl_sal,b.quot), '99999990.00') amt, \n" + 
               "b.mstk_idn,mst.vnm,b.qty,to_char(b.cts,'999990.99') cts,b.sh,b.sz,b.col,b.clr,b.rte,b.quot,nvl(b.fnl_sal,b.quot) fnl_sal ,  \n" + 
                " to_char(decode(mst.rap_rte, 1, '', trunc(((sd.NUM/greatest(mst.rap_rte,1))*100)-100,2)),9990.99) cpdis,sd.num cprte, "+
               "to_char(decode(mst.rap_rte, 1, '', trunc(((nvl(b.fnl_sal,b.quot)/greatest(mst.rap_rte,1))*100)-100,2)),9990.99) saldis,mst.sk1  \n" + 
               "from\n" + 
               "brc_mdlv a,brc_dlv_dtl b,mstk mst,msal c,nme_rel_all_v d,nme_v e,nme_v f ,stk_dtl sd \n" + 
               "where\n" + 
               "  b.mstk_idn=sd.mstk_idn and sd.mprp='CP'  and sd.grp=1 and a.idn=b.idn and b.sal_idn=c.idn and b.mstk_idn=mst.idn and c.nmerel_idn=d.nmerel_idn and c.nme_idn=e.nme_idn and a.inv_nme_idn=f.nme_idn and a.stt='IS' and b.stt='IS' ";
               if(!vnm.equals("")){
               sql = sql+" and mst.vnm in ("+vnm+")";
               }
               
               if(!pkt_ty.equals("")){
                   sql = sql+" and mst.pkt_ty=? ";
                   ary.add(pkt_ty);
               }
               sql = sql+" order by a.dlv_idn desc,e.nme,mst.sk1 ";
          
      }
     int pDlvIdn = 0;
    
      
     ArrayList lDlvIdnLst = new ArrayList();
     HashMap brchDlvDataMap = new HashMap();
     HashMap hdrDtl = new HashMap();
     ArrayList dataDtl = new ArrayList();
     int qty =0;
     float cts = 0;
     float vlu = 0;
    
    ArrayList  outLst = db.execSqlLst("getDate", sql, ary);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
     try {
            while (rs.next()) {
                String mstk_idn= util.nvl(rs.getString("mstk_idn"));
                 qty = qty +1;
                cts = cts + Float.valueOf(util.nvl(rs.getString("cts"),"0"));
                vlu = vlu + Float.valueOf(util.nvl(rs.getString("amt"),"0"));
                int lDlvIdn = rs.getInt("dlv_idn");
                if(pDlvIdn==0){
                    pDlvIdn=lDlvIdn;
                  hdrDtl.put("Dte",util.nvl(rs.getString("dte")));
                  hdrDtl.put("brnNme",util.nvl(rs.getString("brnNme")));
                  hdrDtl.put("IDN",util.nvl(rs.getString("idn")));
                }
                if(pDlvIdn!=lDlvIdn){
                    lDlvIdnLst.add(String.valueOf(pDlvIdn));
                    brchDlvDataMap.put(String.valueOf(pDlvIdn)+"_HDR", hdrDtl);
                    brchDlvDataMap.put(String.valueOf(pDlvIdn)+"_DTL", dataDtl);
                  hdrDtl = new HashMap();
                  hdrDtl.put("Dte",util.nvl(rs.getString("dte")));
                  hdrDtl.put("brnNme",util.nvl(rs.getString("brnNme")));
                  hdrDtl.put("IDN",util.nvl(rs.getString("idn")));
                  pDlvIdn=lDlvIdn;
                  dataDtl = new ArrayList();
                }
                HashMap pktDataDtlMap = new HashMap();
                pktDataDtlMap.put("brc_idn", util.nvl(rs.getString("idn")));
                pktDataDtlMap.put("byr", util.nvl(rs.getString("nme")));
                pktDataDtlMap.put("sal_idn", util.nvl(rs.getString("sal_idn")));
                pktDataDtlMap.put("mstkIdn", util.nvl(rs.getString("mstk_idn")));
                pktDataDtlMap.put("vnm", util.nvl(rs.getString("vnm")));
                pktDataDtlMap.put("qty", util.nvl(rs.getString("qty")));
                pktDataDtlMap.put("cts", util.nvl(rs.getString("cts")));
                pktDataDtlMap.put("amt", util.nvl(rs.getString("amt")));
                pktDataDtlMap.put("SH", util.nvl(rs.getString("sh")));
                pktDataDtlMap.put("SZ", util.nvl(rs.getString("sz")));
                pktDataDtlMap.put("COL", util.nvl(rs.getString("col")));
                pktDataDtlMap.put("CLR", util.nvl(rs.getString("clr")));
                pktDataDtlMap.put("RTE", util.nvl(rs.getString("rte")));
                pktDataDtlMap.put("QUOT", util.nvl(rs.getString("quot")));
                pktDataDtlMap.put("FNLSAL", util.nvl(rs.getString("fnl_sal")));
              pktDataDtlMap.put("FNLSALDIS", util.nvl(rs.getString("saldis")));
                if(cnt.equalsIgnoreCase("NJ")){
                    pktDataDtlMap.put("CPRTE", util.nvl(rs.getString("cprte")));
                    pktDataDtlMap.put("CPDIS", util.nvl(rs.getString("cpdis")));
                      
                }
                
                String getPktPrp = " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                                   + " from stk_dtl a, mprp b, rep_prp c "
                                   + " where a.mprp = b.prp and a.grp=1 and b.prp = c.prp and c.mdl = 'MEMO_RTRN' and a.mstk_idn = ? "
                                   + " order by c.rnk, c.srt ";

                  ary = new ArrayList();
                ary.add(mstk_idn);

              ArrayList  outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, ary);
              PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
              ResultSet rs1= (ResultSet)outLst1.get(1);

                while (rs1.next()) {
                    String lPrp = rs1.getString("mprp");
                    String lVal = rs1.getString("val");

                    pktDataDtlMap.put(lPrp, lVal);
                }
                rs1.close();
                pst1.close();
                pktDataDtlMap.put("CRTWT", util.nvl(rs.getString("cts")));
               dataDtl.add(pktDataDtlMap);
            }
            rs.close();
         pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
     if(pDlvIdn!=0){
       lDlvIdnLst.add(String.valueOf(pDlvIdn));
       brchDlvDataMap.put(String.valueOf(pDlvIdn)+"_HDR", hdrDtl);
       brchDlvDataMap.put(String.valueOf(pDlvIdn)+"_DTL", dataDtl);
     }
    brchDlvDataMap.put("TTLQTY", String.valueOf(qty));
    brchDlvDataMap.put("TTLCTS", String.valueOf(cts));
    brchDlvDataMap.put("TTLVLU", String.valueOf(vlu));
    req.setAttribute("DlvIdnLst", lDlvIdnLst);  
    req.setAttribute("BrchDlvDataMap", brchDlvDataMap);
      
  }
  
 
    public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
            GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

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
                BranchReceivedForm udf;
                udf = (BranchReceivedForm)form;
                         


                ArrayList DlvIdnLst = (ArrayList)session.getAttribute("DlvIdnLst");
                HashMap BrchDlvDataMap = (HashMap)session.getAttribute("BrchDlvDataMap");
                ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
            util.updAccessLog(req,res,"Branch Received ", "createXL");
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "Received"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
            HSSFWorkbook hwb = new HSSFWorkbook();
            ExcelUtilObj excelUtil = new ExcelUtilObj();
            excelUtil.init(db, util, info,gtMgr);
            hwb = excelUtil.getBranchReceivedXls(itemHdr ,DlvIdnLst,BrchDlvDataMap);
            OutputStream out = res.getOutputStream();
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
                rtnPg=util.checkUserPageRights("",util.getFullURL(req));
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Branch Received", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Branch Received", "End");
            }
            }
            return rtnPg;
            }
    
    public void finalizeObject(DBMgr db, DBUtil util){
        try {
            db=null;
            util=null;
        } catch (Throwable t) {
            // TODO: Add catch code
            t.printStackTrace();
        }
       
    }
}