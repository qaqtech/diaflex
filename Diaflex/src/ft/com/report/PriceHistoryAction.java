package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PriceHistoryAction extends DispatchAction {
    public PriceHistoryAction() {
        super();
    }
    
  public ActionForward load(ActionMapping am, ActionForm form, HttpServletRequest req, HttpServletResponse res)
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
        String stkStt = "select distinct grp1 , decode(grp1, 'MKT','Marketing','LAB','Lab','ASRT','Assort','SOLD','Sold', grp1) dsc " +
                        " from df_stk_stt where flg='A' and vld_dte is null and grp1 is not null order by dsc ";
        ArrayList sttList = new ArrayList();

          ArrayList outLst = db.execSqlLst("stkStt", stkStt, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            ArrayList sttDtl = new ArrayList();
            sttDtl.add(rs.getString("grp1"));
            sttDtl.add(rs.getString("dsc"));
            sttList.add(sttDtl);
        }
        rs.close(); pst.close();
        session.setAttribute("sttLst",sttList);
       
           return am.findForward("load");
      }}
  
  
  public ActionForward fetch(ActionMapping am, ActionForm form, HttpServletRequest req, HttpServletResponse res)
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
        String delQ = " Delete from gt_srch_multi ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        PriceHistoryForm udf = (PriceHistoryForm)form;
        String dtefrom = " trunc(sysdate) ";
        String dteto = " trunc(sysdate) ";
        String dfr = util.nvl((String)udf.getValue("dtefr"));
        String dto = util.nvl((String)udf.getValue("dteto"));
        String lstt = util.nvl((String)udf.getValue("stt"));
        if(!dfr.equals(""))
          dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
        if(!dto.equals(""))
          dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
        ArrayList ary = new ArrayList();
          String insertQ = "Insert into gt_srch_multi(stk_idn,fquot , quot , cmp )\n" + 
             " select b.mstk_idn, b.upr , b.old_upr , trunc(((b.upr*100)/b.old_upr)-100,2) "+
              " from df_stk_stt a , stk_pri_log b "+
              " where a.stt = b.stt and trunc(b.dte) between "+dtefrom+" and "+dteto ;
          if(!lstt.equals("")){
            insertQ = insertQ+" and grp1= ?";
            ary.add(lstt);
          }
        ct =db.execUpd("insert Pkts ", insertQ, ary);
          
        
        String pktPrp = "srch_pkg.POP_PKT_PRP_TEST(pMdl=>?,pTbl=>'GT_SRCH_MULTI')";
        ary = new ArrayList();
        ary.add("PRIHIS_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        HashMap gridData = new HashMap();
       HashMap clrMap = new HashMap();
        String query=" select to_char(trunc(avg(cmp),2),'9990.00') diffavg, prp_001 sh,prp_002 sz,prp_003 col,prp_004 clr,srt_001,srt_002 , srt_003,srt_004 from gt_srch_multi" + 
        "     GROUP BY prp_001, prp_002, prp_003, prp_004, srt_001, srt_002,srt_003,srt_004  order by srt_001, srt_002  ,srt_003,srt_004 ";

          ArrayList outLst = db.execSqlLst("gridDate", query, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
          while(rs.next()){
            String sh = rs.getString("sh");
            String sz = rs.getString("sz");
            String col = rs.getString("col");
            String clr = rs.getString("clr");
            String diffavg = rs.getString("diffavg");
            gridData.put(sh+"_"+sz+"_"+col+"_"+clr, diffavg);
             ArrayList clrList = (ArrayList)clrMap.get(sh);
              if(clrList==null){
                  clrList = new ArrayList();
                  clrList.add(clr);
                  clrMap.put(sh, clrList);
              }else{
                  if(!clrList.contains(clr) && clr!=null){
                      clrList.add(clr);
                    clrMap.put(sh, clrList);
                  }
              }
          }
          rs.close(); pst.close();
          req.setAttribute("GridData", gridData);
          req.setAttribute("ClrMap", clrMap);
          viewPrpLst(req, res);
           return am.findForward("load");
      }}
  
  public ArrayList viewPrpLst(HttpServletRequest req ,HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      ArrayList asViewPrp = (ArrayList)session.getAttribute("priHisViewLst");
      try {
          if (asViewPrp == null) {

              asViewPrp = new ArrayList();

              ArrayList outLst1 = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'PRIHIS_VIEW' and flg='Y' order by rnk ",
                             new ArrayList());
              PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
              ResultSet rs1 = (ResultSet)outLst1.get(1);
              while (rs1.next()) {

                  asViewPrp.add(rs1.getString("prp"));
              }
              rs1.close(); pst1.close();
              session.setAttribute("priHisViewLst", asViewPrp);
          }

      } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
      }
      
      return asViewPrp;
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
              util.updAccessLog(req,res,"pricehistory", "Unauthorized Access");
              else
              util.updAccessLog(req,res,"pricehistory", "init");
          }
          }
          return rtnPg;
          }
}
