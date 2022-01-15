package ft.com.lab;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;
import ft.com.dao.GtPktDtl;
import ft.com.dao.Mprc;
import ft.com.dao.labDet;

import java.io.IOException;

import java.math.BigDecimal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LabIssueImpl implements LabIssueInterface{

  public ArrayList getLab(HttpServletRequest req , HttpServletResponse res){
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
      ArrayList ary = null;
      ArrayList labList = new ArrayList();
      HashMap labSttMap = new HashMap();
      String labQry="select val , dsc from prp where mprp = ? and vld_till is null and val not in ('NA') order by srt";
      ary = new ArrayList();
      ary.add("LAB");

    ArrayList outLst = db.execSqlLst("labQry", labQry, ary);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
  try {
      while (rs.next()) {
          labDet labDet = new labDet();
          String labVal = rs.getString("val");
          labDet.setLabVal(labVal);
          labDet.setLabDesc(rs.getString("dsc"));
          labList.add(labDet);
         
      }
      rs.close(); pst.close();
  } catch (SQLException sqle) {
      // TODO: Add catch code
      sqle.printStackTrace();
  }
 
  return labList;
   
}
  public ArrayList getService(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      ArrayList ary = null;
      ArrayList serviceList = new ArrayList();
     
      String labQry=" select val , dsc from prp where mprp = ?  and vld_till is null and flg is null";
      ary = new ArrayList();
      ary.add("SERVICE");

      ArrayList outLst = db.execSqlLst("labQry", labQry, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      
      try {
      while (rs.next()) {
          labDet labDet = new labDet();
          String labVal = rs.getString("val");
          labDet.setLabVal(labVal);
          labDet.setLabDesc(rs.getString("dsc"));
          serviceList.add(labDet);
         
      }
          rs.close(); pst.close();
      } catch (SQLException sqle) {
      // TODO: Add catch code
      sqle.printStackTrace();
      }
      
      return serviceList;
  }
  public HashMap FetchResult(HttpServletRequest req,HttpServletResponse res, HashMap params , LabIssueForm form,String LabForm){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      
      String lab = (String)params.get("labIdn");
      String vnm = util.nvl((String)params.get("vnm"));
    
      String stt="LB_AV";
      ArrayList ary = null;
      ArrayList delAry=new ArrayList();
      delAry.add("Z");
      String delQ = " Delete from gt_srch_rslt where flg in (?)";
      int ct =db.execUpd(" Del Old Pkts ", delQ, delAry);
      
      String srchRefQ = 
      "    Insert into gt_srch_rslt (pkt_ty, stk_idn , vnm , pkt_dte, stt , flg , qty , cts , rmk,sk1 ) " + 
      " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , tfl3 ,sk1  "+
      "     from mstk b "+
      " where stt =? and cert_lab=? and cts > 0  ";
      
      if(!vnm.equals("")){
               vnm = util.getVnm(vnm);
              srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))" ;
              
        }
      ary = new ArrayList();
      ary.add(stt);
      ary.add(lab);
      ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
      
      String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
      ary = new ArrayList();
      ary.add("LAB_VIEW");
      ct = db.execCall(" Srch Prp ", pktPrp, ary);
      
      HashMap stockList = SearchResultGT(req ,res , vnm , form,LabForm);
      req.setAttribute("pktList", stockList);
      return stockList;
  }
  
  public HashMap SearchResultGT(HttpServletRequest req ,HttpServletResponse res, String vnmLst , LabIssueForm form ,String LabForm){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      HashMap pktList = new HashMap();
      ArrayList vwPrpLst = LabPrprViw(req,res);
      HashMap dbinfo = info.getDmbsInfoLst();
      String clrval = (String)dbinfo.get("CLR");
      String cnt = (String)dbinfo.get("CNT");
      
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
      HashMap pageDtl=(HashMap)allPageDtl.get("LAB_ISSUE");
      ArrayList pageList=new ArrayList();
      HashMap pageDtlMap=new HashMap();
      String dflt_LabLoc="";
      
      if(pageDtl!=null && pageDtl.size() >0){  
           for(int i=0;i<pageDtl.size();i++){
          pageList= ((ArrayList)pageDtl.get("LAB_LOC") == null)?new ArrayList():(ArrayList)pageDtl.get("LAB_LOC");
          for(int j=0;j<pageList.size();j++){
              pageDtlMap=(HashMap)pageList.get(j);
              dflt_LabLoc=(String)pageDtlMap.get("dflt_val");
       }
      }
      }
      String  srchQ =  " select sk1,stk_idn , pkt_ty, vnm,rap_rte, pkt_dte, stt , qty , cts , rmk, decode(rap_rte ,'1',null,'0',null, trunc((quot/greatest(rap_rte,1)*100)-100, 2)) rap_dis ,quot rte,to_char(cts * quot, '99999990.00') amt,to_char(cts * rap_rte, '99999990.00') rapvlu  ";

      

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

      
      String rsltQ = srchQ + " from gt_srch_rslt where flg =? " ;
     
       rsltQ  = rsltQ+ "order by sk1 , cts ";
      
      ArrayList ary = new ArrayList();
      ary.add("Z");

      ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      try {
          while(rs.next()) {
                GtPktDtl pktPrpMap = new GtPktDtl();
              float cts = rs.getFloat("cts");
              String stk_idn = util.nvl(rs.getString("stk_idn"));
                  pktPrpMap.setValue("AMT", util.nvl(rs.getString("amt")));
                  pktPrpMap.setValue("RAPVAL", util.nvl(rs.getString("rapvlu")));
              pktPrpMap.setValue("stt", util.nvl(rs.getString("stt")));
              pktPrpMap.setValue("amt", util.nvl(rs.getString("amt")));
              pktPrpMap.setValue("rapvlu", util.nvl(rs.getString("rapvlu")));
              pktPrpMap.setValue("rap_rte", util.nvl(rs.getString("rap_rte")));
              pktPrpMap.setSk1(new BigDecimal(util.nvl(rs.getString("sk1"),"0")));
              String vnm = util.nvl(rs.getString("vnm"));
              pktPrpMap.setValue("vnm",vnm);
                  String tfl3 = util.nvl(rs.getString("rmk"));
                  if(vnmLst.indexOf(tfl3)!=-1 && !tfl3.equals("")){
                      if(vnmLst.indexOf("'")==-1 && !tfl3.equals(""))
                          vnmLst =  vnmLst.replaceAll(tfl3,"");
                      else
                          vnmLst =  vnmLst.replaceAll("'"+tfl3+"'", "");
                  } else if(vnmLst.indexOf(vnm)!=-1 && !vnm.equals("")){
                      if(vnmLst.indexOf("'")==-1)
                          vnmLst =  vnmLst.replaceAll(vnm,"");
                      else
                          vnmLst =  vnmLst.replaceAll("'"+vnm+"'", "");
                  }       
              pktPrpMap.setValue("stk_idn", stk_idn);
              pktPrpMap.setValue("qty",util.nvl(rs.getString("qty")));
              pktPrpMap.setValue("cts",util.nvl(rs.getString("cts")));
              for(int j=0; j < vwPrpLst.size(); j++){
                   String prp = (String)vwPrpLst.get(j);
                    
                    String fld="prp_";
                    if(j < 9)
                            fld="prp_00"+(j+1);
                    else    
                            fld="prp_0"+(j+1);
                    
                    String val = util.nvl(rs.getString(fld)) ;
                   if(prp.equals("LAB_PRC"))
                      form.setValue("labIdn", val);
                    if(prp.equals("RTE"))
                    val = util.nvl(rs.getString("rte"));
                    if(prp.equals("RAP_DIS"))
                    val = util.nvl(rs.getString("rap_dis"));
                      pktPrpMap.setValue(prp, val);
                }

                  if(cts<=0.99){
                  if(LabForm.equals("NORMAL"))
                  form.setValue("SERVICE_"+stk_idn,"DD");
                  else
                  form.setValue("SERVICE_"+stk_idn,"G.DOSS");  
                  }

                  if(cts>=1.00) {
                  if(!cnt.equals("kj") && !cnt.equals("ag") && !cnt.equals("hk")){
                  if(cnt.equals("sje")){
                  if(LabForm.equals("NORMAL"))
                  form.setValue("SERVICE_"+stk_idn,"DG");
                  else
                  form.setValue("SERVICE_"+stk_idn,"G.DG");
                  }else{
                  if(LabForm.equals("NORMAL"))
                  form.setValue("SERVICE_"+stk_idn,"DG + I");
                  else
                  form.setValue("SERVICE_"+stk_idn,"G.I");
                  }
                  }else{
                  if(LabForm.equals("NORMAL"))
                  form.setValue("SERVICE_"+stk_idn,"DG");
                  else
                  form.setValue("SERVICE_"+stk_idn,"G.DG");
                  if(cnt.equals("ag"))
                  form.setValue("INSCRIPTION_SERVICE_"+stk_idn,"I");
                  }
                  }
                  if(!dflt_LabLoc.equals(""))
                  form.setValue("LAB_LOC_"+stk_idn,dflt_LabLoc);
                  pktList.put(stk_idn, pktPrpMap);
              }
          rs.close(); pst.close();
      } catch (SQLException sqle) {

          // TODO: Add catch code
          sqle.printStackTrace();
      }
      if(!vnmLst.equals("")){
      vnmLst = util.pktNotFound(vnmLst);
      req.setAttribute("vnmNotFnd", vnmLst);
      }
    pktList =(HashMap)util.sortByComparator(pktList);
      return pktList;
  }
  public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res, String vnmLst , LabIssueForm form ){
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
      ArrayList vwPrpLst = LabPrprViw(req,res);
      String  srchQ =  " select stk_idn , pkt_ty,  vnm,rap_rte, pkt_dte, stt , qty , cts , rmk,quot rte,to_char(cts * quot, '99999990.00') amt,to_char(cts * rap_rte, '99999990.00') rapvlu  ";

      

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

      
      String rsltQ = srchQ + " from gt_srch_rslt where flg =? " ;
     
       rsltQ  = rsltQ+ "order by sk1 , cts ";
      
      ArrayList ary = new ArrayList();
      ary.add("Z");

      ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      try {
          while(rs.next()) {
              float cts = rs.getFloat("cts");
              String stk_idn = util.nvl(rs.getString("stk_idn"));
              HashMap pktPrpMap = new HashMap();
              pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
              pktPrpMap.put("amt", util.nvl(rs.getString("amt")));
              pktPrpMap.put("rapvlu", util.nvl(rs.getString("rapvlu")));
              pktPrpMap.put("rap_rte", util.nvl(rs.getString("rap_rte")));
              String vnm = util.nvl(rs.getString("vnm"));
              pktPrpMap.put("vnm",vnm);
                  String tfl3 = util.nvl(rs.getString("rmk"));
                  if(vnmLst.indexOf(tfl3)!=-1 && !tfl3.equals("")){
                      if(vnmLst.indexOf("'")==-1 && !tfl3.equals(""))
                          vnmLst =  vnmLst.replaceAll(tfl3,"");
                      else
                          vnmLst =  vnmLst.replaceAll("'"+tfl3+"'", "");
                  } else if(vnmLst.indexOf(vnm)!=-1 && !vnm.equals("")){
                      if(vnmLst.indexOf("'")==-1)
                          vnmLst =  vnmLst.replaceAll(vnm,"");
                      else
                          vnmLst =  vnmLst.replaceAll("'"+vnm+"'", "");
                  }       
              pktPrpMap.put("stk_idn", stk_idn);
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
                   if(prp.equals("LAB_PRC"))
                      form.setValue("labIdn", val);
                    if(prp.equals("RTE"))
                    val = util.nvl(rs.getString("rte"));
                      pktPrpMap.put(prp, val);
                }
                  String clarity=((String)pktPrpMap.get("CLR"));
                  String cnt = (String)info.getDmbsInfoLst().get("CNT");
                  if(cnt.equals("svk")){

                  if(cts<0.33){
                  form.setValue("SERVICE_"+stk_idn,"DD");
                  }

                  if(cts>=0.33 &&cts<=0.99){
                  form.setValue("SERVICE_"+stk_idn,"DD");
                  }

                  if(cts>=1.00) {
                  form.setValue("SERVICE_"+stk_idn,"DG + I");
                  }

                  if(cts >=1.00 && clarity.equals("SI2")) {
                  form.setValue("SERVICE_"+stk_idn,"DG");
                  }

                  }else{
                  if(cts >=1)
                  form.setValue("SERVICE_"+stk_idn, "DG");
                  else
                  form.setValue("SERVICE_"+stk_idn, "DD");
                  }
         
                  pktList.add(pktPrpMap);
              }
          rs.close(); pst.close();
      } catch (SQLException sqle) {

          // TODO: Add catch code
          sqle.printStackTrace();
      }
      if(!vnmLst.equals("")){
      vnmLst = util.pktNotFound(vnmLst);
      req.setAttribute("vnmNotFnd", vnmLst);
      }
      return pktList;
  }
  public ArrayList LabPrprViw(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      ArrayList viewPrp = (ArrayList)session.getAttribute("LabViewLst");
      try {
          if (viewPrp == null) {

              viewPrp = new ArrayList();

              ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'LAB_VIEW' and flg='Y' order by rnk ",
                             new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs1 = (ResultSet)outLst.get(1);
              while (rs1.next()) {

                  viewPrp.add(rs1.getString("prp"));
              }
              rs1.close();
              session.setAttribute("LabViewLst", viewPrp);
          }

      } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
      }
      
      return viewPrp;
  }
  public HashMap GetTotal(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
  HashMap gtTotalMap = new HashMap();
  String gtTotal ="Select count(*) qty, sum(cts) cts from gt_srch_rslt where flg = ?";
  ArrayList ary = new ArrayList();
  ary.add("Z");

      ArrayList outLst = db.execSqlLst("getTotal", gtTotal, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
  try {
  if (rs.next()) {
  gtTotalMap.put("qty",util.nvl(rs.getString("qty")));
  gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
  }
      rs.close(); pst.close();
  } catch (SQLException sqle) {
  // TODO: Add catch code
  sqle.printStackTrace();
  }

  return gtTotalMap ;
  }
  
    public HashMap GetTotalNew(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    HashMap gtTotalMap = new HashMap();
    String gtTotal ="Select count(*) qty, to_char(sum(cts), '99999990.00') cts,to_char(((sum(cts*quot) / sum(cts))), '99999990.00') avg_Rte,\n" + 
    "    to_char(sum(cts*quot), '9999999999990.00') vlu,to_char(sum(cts*rap_rte), '9999999999990.00') rapvlu\n" + 
    "    from gt_srch_rslt \n" + 
    "where flg = ?";
    ArrayList ary = new ArrayList();
    ary.add("Z");

        ArrayList outLst = db.execSqlLst("getTotal", gtTotal, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    try {
    if (rs.next()) {
    gtTotalMap.put("qty",util.nvl(rs.getString("qty")));
    gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
    gtTotalMap.put("avg_Rte", util.nvl(rs.getString("avg_Rte")));
    gtTotalMap.put("vlu", util.nvl(rs.getString("vlu")));
    gtTotalMap.put("rapvlu", util.nvl(rs.getString("rapvlu")));
    }
        rs.close(); pst.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }

    return gtTotalMap ;
    }
  public void LabIssueEdit(HttpServletRequest req,HttpServletResponse res,String issStt){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      ArrayList prpList = new ArrayList();
      String labIssueEdit ="select b.mprp from mprc a , prc_prp_alw b " + 
                           " where a.idn = b.prc_idn and a.is_stt=? and b.flg='ISSEDIT'  order by b.srt ";
      ArrayList ary = new ArrayList();
      ary.add(issStt);

      ArrayList outLst = db.execSqlLst("labIssue", labIssueEdit, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
             prpList.add(rs.getString("mprp"));
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("LabIssueEditPRP", prpList);
        gtMgr.setValue("LabIssueEditPRP", prpList);
  }
  
  public ArrayList FetchResult(HttpServletRequest req ,HttpServletResponse res, LabIssueForm form){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      ArrayList ary = new ArrayList();
      String view =util.nvl(form.getView());
      String vnm = util.nvl((String)form.getValue("vnmLst"));
      ary=new ArrayList();
     
      String delQ = " Delete from gt_srch_rslt ";
      int ct =db.execUpd(" Del Old Pkts ", delQ, ary);
      
      String srchRefQ = 
      "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1  ,  cert_lab , rmk ) " + 
      " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , sk1 ,  cert_lab , tfl3  "+
      "     from mstk b "+
      " where stt in ('AS_FN','LB_AV','LB_RT') ";
      
      if(view.length() > 0){
      if(!vnm.equals("")){
           vnm = util.getVnm(vnm);
          srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))" ;
          
      }}
      ary = new ArrayList();
      
       ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
      
      String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
      ary = new ArrayList();
      ary.add("LAB_VIEW");
      ct = db.execCall(" Srch Prp ", pktPrp, ary);
      
      ArrayList  stockList = SearchResult(req , res, form);
      
      return stockList;
      
  }
    public HashMap insertgt(HttpServletRequest req ,HttpServletResponse res, LabIssueForm form,String LabForm){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = new ArrayList();
        String vnm = util.nvl((String)form.getValue("vnmLst"));
        ary=new ArrayList();
        HashMap dbinfo = info.getDmbsInfoLst();
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, ary);
        String lab = util.nvl((String)form.getValue("LAB_PRC_1"));
        String formNme = util.nvl((String)form.getValue("FORMNME"));
        ct =db.execUpd(" Del Old gt_pkt_scan ", "delete gt_pkt_scan", ary);
        vnm = util.getVnm(vnm);
        String[] vnmLst = vnm.split(",");
        int loopCnt = 1 ;
        float loops = ((float)vnmLst.length)/stepCnt;
        loopCnt = Math.round(loops);
           if(vnmLst.length <= stepCnt || new Float(loopCnt)>=loops) {
            
        } else
            loopCnt += 1 ;
        if(loopCnt==0)
            loopCnt += 1 ;
        int fromLoc = 0 ;
        int toLoc = 0 ;
        for(int i=1; i <= loopCnt; i++) {
            
          int aryLoc = Math.min(i*stepCnt, vnmLst.length) ;
          
          String lookupVnm = vnmLst[aryLoc-1];
               if(i == 1)
                   fromLoc = 0 ;
               else
                   fromLoc = toLoc+1;
               
               toLoc = Math.min(vnm.lastIndexOf(lookupVnm) + lookupVnm.length(), vnm.length());
               String vnmSub = vnm.substring(fromLoc, toLoc);
            
        vnmSub = vnmSub.replaceAll(",", "");
        vnmSub = vnmSub.replaceAll("''", ",");
        vnmSub = vnmSub.replaceAll("'", "");
            if(!vnmSub.equals("")){
        vnmSub="'"+vnmSub+"'";
        ArrayList params = new ArrayList();
        //        params.add(vnmSub);
        String insScanPkt = " insert into gt_pkt_scan select * from TABLE(PARSE_TO_TBL("+vnmSub+"))";
          ct = db.execDirUpd(" ins scan", insScanPkt,params);
          System.out.println(insScanPkt);  
        }
        }
        
        String srchStr = " ( b.vnm= a.vnm or b.tfl3 = a.vnm ) ";
        if(formNme.equals("AUTO"))
            srchStr = " (  b.tfl3 = a.vnm ) ";
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts ,quot,rap_rte, sk1  ,  cert_lab , rmk ) " + 
        " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , nvl(b.upr,b.cmp),b.rap_rte , sk1 ,  cert_lab , tfl3  "+
        "     from mstk b , gt_pkt_scan a where "+srchStr+
        " and b.stt in ('LB_AV') and exists (select 1 from stk_dtl c where c.mstk_idn=b.idn  and c.mprp='LAB_PRC' and c.srt =? and c.grp=1)  ";
        ary = new ArrayList();
        ary.add(lab);
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
       
        
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("LAB_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        HashMap  stockList = SearchResultGT(req , res,"", form,LabForm);
        
        return stockList;
        
    }
    
    public ArrayList insert(HttpServletRequest req ,HttpServletResponse res, LabIssueForm form){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = new ArrayList();
        String vnm = util.nvl((String)form.getValue("vnmLst"));
        String lab = util.nvl((String)form.getValue("LAB_PRC_1"));
        ary=new ArrayList();
        HashMap dbinfo = info.getDmbsInfoLst();
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, ary);
        ct =db.execUpd(" Del Old gt_pkt_scan ", "delete gt_pkt_scan", ary);
        vnm = util.getVnm(vnm);
        String[] vnmLst = vnm.split(",");
        int loopCnt = 1 ;
        float loops = ((float)vnmLst.length)/stepCnt;
        loopCnt = Math.round(loops);
           if(vnmLst.length <= stepCnt || new Float(loopCnt)>=loops) {
            
        } else
            loopCnt += 1 ;
        if(loopCnt==0)
            loopCnt += 1 ;
        int fromLoc = 0 ;
        int toLoc = 0 ;
        for(int i=1; i <= loopCnt; i++) {
            
          int aryLoc = Math.min(i*stepCnt, vnmLst.length) ;
          
          String lookupVnm = vnmLst[aryLoc-1];
               if(i == 1)
                   fromLoc = 0 ;
               else
                   fromLoc = toLoc+1;
               
               toLoc = Math.min(vnm.lastIndexOf(lookupVnm) + lookupVnm.length(), vnm.length());
               String vnmSub = vnm.substring(fromLoc, toLoc);
            
        vnmSub = vnmSub.replaceAll(",", "");
        vnmSub = vnmSub.replaceAll("''", ",");
        vnmSub = vnmSub.replaceAll("'", "");
            if(!vnmSub.equals("")){
        vnmSub="'"+vnmSub+"'";
        ArrayList params = new ArrayList();
        //        params.add(vnmSub);

        String insScanPkt = " insert into gt_pkt_scan select * from TABLE(PARSE_TO_TBL("+vnmSub+"))";
          ct = db.execDirUpd(" ins scan", insScanPkt,params);
          System.out.println(insScanPkt);  
        }
        }
        
        String srchStr = " ( b.vnm= a.vnm or b.tfl3 = a.vnm ) ";
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts ,quot,rap_rte, sk1  ,  cert_lab , rmk ) " + 
        " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , nvl(b.upr,b.cmp),b.rap_rte , sk1 ,  cert_lab , tfl3  "+
        "     from mstk b , gt_pkt_scan a where "+srchStr+
        " and b.stt in ('LB_AV') and exists (select 1 from stk_dtl c where c.mstk_idn=b.idn and c.mprp='LAB_PRC' and c.srt =? and c.grp=1)  ";
        ary = new ArrayList();
        ary.add(lab);
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("LAB_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        ArrayList  stockList = SearchResult(req , res,"", form);
        
        return stockList;
        
    }
  public ArrayList SearchResult(HttpServletRequest req , HttpServletResponse res, LabIssueForm form ){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      String vnmLst = util.nvl((String)form.getValue("vnmLst"));
      if(!vnmLst.equals(""))
      vnmLst = util.getVnm(vnmLst);
      ArrayList pktList = new ArrayList();
      ArrayList vwPrpLst = ASPrprViw(req,res);
      String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , cert_lab , rmk ";

      

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

      
      String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by sk1 , cts ";
      
      ArrayList ary = new ArrayList();
      ary.add("Z");

      ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      try {
          while(rs.next()) {
              String cert_lab = util.nvl(rs.getString("cert_lab"));
              String stkIdn = util.nvl(rs.getString("stk_idn"));
             
                  
              HashMap pktPrpMap = new HashMap();
              pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
              String vnm = util.nvl(rs.getString("vnm"));
              pktPrpMap.put("vnm",vnm);
                  String tfl3 = util.nvl(rs.getString("rmk"));
                  if(vnmLst.indexOf(tfl3)!=-1 && !tfl3.equals("")){
                      if(vnmLst.indexOf("'")==-1)
                          vnmLst =  vnmLst.replaceAll(tfl3,"");
                      else
                          vnmLst =  vnmLst.replaceAll("'"+tfl3+"'", "");
                  } else if(vnmLst.indexOf(vnm)!=-1 && !vnm.equals("")){
                      if(vnmLst.indexOf("'")==-1)
                          vnmLst =  vnmLst.replaceAll(vnm,"");
                      else
                          vnmLst =  vnmLst.replaceAll("'"+vnm+"'", "");
                  }   
              pktPrpMap.put("stk_idn",stkIdn);
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
                  if(prp.equals("LAB") && !val.equals("")){
                        form.setValue("lab_"+stkIdn, cert_lab);
                        form.setValue("stt_"+stkIdn, "LB");
                    
                  }

                  
                   
                      
                      pktPrpMap.put(prp, val);
                       }
                            
                  pktList.add(pktPrpMap);
              }
          rs.close(); pst.close();
      } catch (SQLException sqle) {

          // TODO: Add catch code
          sqle.printStackTrace();
      }
      
      if(!vnmLst.equals("")){
          vnmLst = util.pktNotFound(vnmLst);
          req.setAttribute("vnmNotFnd", vnmLst);
      }

      return pktList;
  }
  
  public ArrayList ASPrprViw(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr(); 
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      ArrayList asViewPrp = (ArrayList)session.getAttribute("LabViewLst");
      try {
          if (asViewPrp == null) {

              asViewPrp = new ArrayList();

              ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'LAB_VIEW' and flg='Y' order by rnk ",
                             new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs1 = (ResultSet)outLst.get(1);
              while (rs1.next()) {

                  asViewPrp.add(rs1.getString("prp"));
              }
              rs1.close();
              session.setAttribute("LabViewLst", asViewPrp);
          }

      } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
      }
      
      return asViewPrp;
  }
    public void delGt(HttpServletRequest req, HttpServletResponse res, String vnmList){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    vnmList = util.getVnm(vnmList);
    String sql="delete from gt_srch_rslt where vnm not in ("+vnmList+")";
    int ct = db.execUpd("deleteFromGt", sql, new ArrayList());
    }
    
    public ArrayList getPrc(HttpServletRequest req ,HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      
        ArrayList prcList = new ArrayList();
          
          String prcSql = "select idn, prc , in_stt from mprc where  is_stt in ('LB_AU_IS','LB_AURI_IS')   order by srt";
          ArrayList  ary = new ArrayList();
          
 
        ArrayList outLst = db.execSqlLst("prcSql", prcSql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
          try {
              while (rs.next()) {
                  Mprc mprc = new Mprc();
                 
                  mprc.setMprcId(rs.getString("idn"));
                  mprc.setPrc(rs.getString("prc"));
                  mprc.setIn_stt(rs.getString("in_stt"));
                  prcList.add(mprc);
                }
              rs.close(); pst.close();
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
          
       return prcList;
    }
    
    public ArrayList validateMapping(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String updateGtSH="Update gt_srch_rslt gt set flg = 'E', rmk = 'Shape Not Found' \n" + 
        "where flg = 'Z' and not exists (select 1 from stk_dtl sd, GIA_SYNC_PRP_VALS gia\n" + 
        " where gt.stk_idn = sd.mstk_idn and sd.grp = 1 and sd.mprp in ('SHAPE','SH') \n" + 
        "   and sd.mprp = gia.mprp and sd.val = gia.val and gia.flg = 'I')\n" ;
        int ct = db.execUpd("upGT", updateGtSH, new ArrayList());
       
           ArrayList errorList = new ArrayList();
        try {
            String gtFetch = "select flg,rmk,stk_idn,vnm from gt_srch_rslt where flg='E' ";
            ArrayList rsLst = db.execSqlLst("grpNme", gtFetch, new ArrayList());
            PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
              String vnm=rs.getString("vnm");
              String rmk = rs.getString("rmk");
             
                errorList.add(vnm+"-"+rmk);
            }
            rs.close(); 
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return errorList;
    }
//  public ArrayList LBGenricSrch(HttpServletRequest req , HttpServletResponse res){
//      init(req,res);
//      ArrayList asViewPrp = (ArrayList)session.getAttribute("lbISGNCSrch");
//      try {
//          if (asViewPrp == null) {
//
//              asViewPrp = new ArrayList();
//              ResultSet rs1 =
//                  db.execSql(" Vw Lst ", "Select prp,flg  from rep_prp where mdl = 'LB_IS_SRCH' and flg <> 'N' order by rnk ",
//                             new ArrayList());
//              while (rs1.next()) {
//                  ArrayList asViewdtl=new ArrayList();
//                  asViewdtl.add(rs1.getString("prp"));
//                  asViewdtl.add(rs1.getString("flg"));
//                  asViewPrp.add(asViewdtl);
//              }
//              rs1.close();
//              session.setAttribute("lbISGNCSrch", asViewPrp);
//          }
//
//      } catch (SQLException sqle) {
//          // TODO: Add catch code
//          sqle.printStackTrace();
//      }
//      
//      return asViewPrp;
//  }
}