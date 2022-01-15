package ft.com.assort;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.labDet;
import ft.com.lab.FinalLabSelectionForm;
import ft.com.lab.FinalLabSelectionImpl;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AssortSelectionImpl implements AssortSelectionInterface {
  
    public AssortSelectionImpl() {
        super();
    }
    public ArrayList FetchResult(HttpServletRequest req , HttpServletResponse res, HashMap params,AssortSelectionForm assortForm){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList  stockList = new ArrayList();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
        ArrayList ary = new ArrayList();
        String stt=(String)assortForm.getValue("stt");
        String vnm = "";
        ary=new ArrayList();
       
        String delQ = " Delete from gt_srch_rslt  ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, ary);
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1  ,  cert_lab , rmk,quot,rap_dis,rap_rte ) " + 
        " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , sk1 ,  cert_lab , tfl3,nvl(upr,cmp) rte,decode(rap_rte ,'1',null,'0',null, trunc((nvl(upr,cmp)/greatest(rap_rte,1)*100)-100, 2)),b.rap_rte  "+
        "     from mstk b "+
        " where stt in ('"+stt+"')  ";
        
        if(params.size()>0){
             vnm = (String)params.get("vnm");
            srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))" ;
            
        }
            
        ary = new ArrayList();
        
         ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add("AS_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
         stockList = SearchResult(req ,res , vnm,assortForm);
      }
        return stockList;
        
    }
      public ArrayList SearchResult(HttpServletRequest req , HttpServletResponse res, String vnmLst,AssortSelectionForm assortForm){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        ArrayList pktList = new ArrayList();
        if(info!=null){
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
         init(req,res,session,util);
    
      ArrayList vwPrpLst = ASPrprViw(req,res);
      String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , cert_lab,quot , rmk,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis,rap_rte ";

      

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
          ArrayList  outLst = db.execSqlLst("search Result", rsltQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
      try {
          while(rs.next()) {
            
              String stkIdn = util.nvl(rs.getString("stk_idn"));
            
              HashMap pktPrpMap = new HashMap();
              pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
              String vnm = util.nvl(rs.getString("vnm"));
             
              pktPrpMap.put("vnm",vnm);
              pktPrpMap.put("stk_idn",stkIdn);
              pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
              pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
              assortForm.setValue("stt_" + stkIdn, util.nvl(rs.getString("stt")));
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
             
              for(int j=0; j < vwPrpLst.size(); j++){
                   String prp = (String)vwPrpLst.get(j);
                    
                    String fld="prp_";
                    if(j < 9)
                            fld="prp_00"+(j+1);
                    else    
                            fld="prp_0"+(j+1);
                    
                    String val = util.nvl(rs.getString(fld)) ;
                   
                     if (prp.toUpperCase().equals("RAP_DIS"))
                     val = util.nvl(rs.getString("r_dis"));
                     if (prp.toUpperCase().equals("RTE"))
                     val = util.nvl(rs.getString("quot"));
                     if (prp.toUpperCase().equals("RAP_RTE"))
                     val = util.nvl(rs.getString("rap_rte"));
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
      if(!vnmLst.equals("")){
          vnmLst = util.pktNotFound(vnmLst);
          req.setAttribute("vnmNotFnd", vnmLst);
      }
        }
      return pktList;
      }
  
   
    
    public ArrayList ASPrprViw(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute("asViewLst");
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
      
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
               
              ArrayList  outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'AS_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                pst.close();
                session.setAttribute("asViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return asViewPrp;
    }
    
    public HashMap GetTotal(HttpServletRequest req , HttpServletResponse res , String flg ){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      HashMap gtTotalMap = new HashMap();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
       
        String gtTotal ="Select count(*) qty, sum(cts) cts from gt_srch_rslt where flg = ?";
        ArrayList ary = new ArrayList();
        ary.add(flg);
        ArrayList  outLst = db.execSqlLst("getTotal", gtTotal, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if (rs.next()) {
             gtTotalMap.put("qty", util.nvl(rs.getString("qty")));
             gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return gtTotalMap ;
    }
    
//    public ArrayList ASGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("asGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'AS_SRCH' and flg <> 'N' order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("asGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
    
    public void init(HttpServletRequest req , HttpServletResponse res,HttpSession session,DBUtil util) {
          String rtnPg="sucess";
          String invalide="";
          String connExists=util.nvl(util.getConnExists());
        try {
            if (!connExists.equals("N"))
                invalide = util.nvl(util.chkTimeOut(), "N");
            if (session.isNew())
                res.sendRedirect("../error_page.jsp");
            if (connExists.equals("N"))
                res.sendRedirect("../error_page.jsp?connExists=N");
            if (invalide.equals("Y"))
                res.sendRedirect("../loginInvalid.jsp");
            if (rtnPg.equals("sucess")) {
                boolean sout = util.getLoginsession(req, res, session.getId());
                if (!sout) {
                  res.sendRedirect("../error_page.jsp");
                    System.out.print("New Session Id :=" + session.getId());
                } else {
                    util.updAccessLog(req, res, "Assort Selection Impl",
                                      "init");
                }
            }
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
        
       }
}
