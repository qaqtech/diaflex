package ft.com.lab;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.GtPktDtl;
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

public class LabSelectionImpl implements LabSelectionInterface {
   
  public HashMap FetchResultGT(HttpServletRequest req ,HttpServletResponse res, LabSelectionForm form){
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
      "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1  ,  cert_lab , rmk , quot,rap_rte,rap_dis) " + 
      " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , sk1 ,  cert_lab , tfl3 , nvl(upr,cmp),rap_rte, decode(rap_rte ,'1',null,'0',null, trunc((nvl(upr,cmp)/greatest(rap_rte,1)*100)-100, 2))  "+
      "     from mstk b "+
          " where stt in ('AS_FN','LB_AV','LB_SEL_AV','LB_RT') and b.pkt_ty='NR' ";
      
      if(view.length() > 0){
      if(!vnm.equals("")){
           vnm = util.getVnm(vnm);
          srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))" ;
          
      }}
      ary = new ArrayList();
      
       ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
      
      String pktPrp = "pkgmkt.pktPrp(0,?)";
      ary = new ArrayList();
      ary.add("LAB_SEL_VW");
      ct = db.execCall(" Srch Prp ", pktPrp, ary);
      
      HashMap  stockList = SearchResultGT(req , res, form);
      
      return stockList;
      
  }
  
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
        ary.add("LAB_PRC");

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
           gtTotalMap.put("qty", util.nvl(rs.getString("qty")));
           gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
          }
          rs.close(); pst.close();
      } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
      }
      
      return gtTotalMap ;
  }
    
    
    public HashMap SearchResultGT(HttpServletRequest req , HttpServletResponse res, LabSelectionForm form ){
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
        HashMap pktListMap = new HashMap();
        ArrayList vwPrpLst = ASPrprViw(req,res);
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt ,sk1, qty , cts , cert_lab , rmk , quot , to_char(trunc(cts,2) * rap_rte, '99999990.00') rapval,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis,rap_rte   ";

        

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
                if(!cert_lab.equals("")){
                    form.setValue("lab_"+stkIdn, cert_lab);
                    form.setValue("stt_"+stkIdn, "LB");
                }
                    
                GtPktDtl pktPrpMap = new GtPktDtl();
                pktPrpMap.setValue("stt", util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.setValue("vnm",vnm);
                pktPrpMap.setValue("RAPVAL",rs.getString("rapval"));
                    pktPrpMap.setValue("sk1", util.nvl(rs.getString("sk1")));
                    pktPrpMap.setSk1(new BigDecimal(util.nvl(rs.getString("sk1"),"0")));
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
                pktPrpMap.setValue("stk_idn",stkIdn);
                pktPrpMap.setValue("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.setValue("cts",util.nvl(rs.getString("cts")));
                 pktPrpMap.setValue("quot",util.nvl(rs.getString("quot"),"0"));
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      if(prp.equals("RTE"))
                        fld="quot";
                      String val = util.nvl(rs.getString(fld)) ;
                    if (prp.toUpperCase().equals("RAP_RTE"))
                    val = util.nvl(rs.getString("rap_rte"));
                    if (prp.toUpperCase().equals("RAP_DIS"))
                    val = util.nvl(rs.getString("r_dis"));
                      if(prp.equals("LAB_PRC") && !val.equals("") && !val.equals("NA"))
                      form.setValue("lab_"+stkIdn, val);
                    pktPrpMap.setValue(prp, val);
                }
                              
                    pktListMap.put(stkIdn,pktPrpMap);
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
        pktListMap =(HashMap)util.sortByComparator(pktListMap);
        return pktListMap;
    }
    
   
    public LabSelectionImpl() {
        super();
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
        ArrayList asViewPrp = (ArrayList)session.getAttribute("LabSelViewLst");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();

                ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'LAB_SEL_VW' and flg='Y' order by rnk ",
                               new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close(); pst.close();
                session.setAttribute("LabSelViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
    
    
    
    
//    public ArrayList LBGenricSrch(HttpServletRequest req , HttpServletResponse res){
//      init(req,res);
//      ArrayList asViewPrp = (ArrayList)session.getAttribute("LBGNCSrch");
//      try {
//          if (asViewPrp == null) {
//
//              asViewPrp = new ArrayList();
//              ResultSet rs1 =
//                  db.execSql(" Vw Lst ", "Select prp,flg  from rep_prp where mdl = 'LB_SRCH' and flg <> 'N' order by rnk ",
//                             new ArrayList());
//              while (rs1.next()) {
//                  ArrayList asViewdtl=new ArrayList();
//                  asViewdtl.add(rs1.getString("prp"));
//                  asViewdtl.add(rs1.getString("flg"));
//                  asViewPrp.add(asViewdtl);
//              }
//              rs1.close(); pst.close();
//              session.setAttribute("LBGNCSrch", asViewPrp);
//          }
//
//      } catch (SQLException sqle) {
//          // TODO: Add catch code
//          sqle.printStackTrace();
//      }
//      
//      return asViewPrp;
//    }
//    
}
