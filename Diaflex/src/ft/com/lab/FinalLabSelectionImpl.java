package ft.com.lab;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.labDet;

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

public class FinalLabSelectionImpl implements FinalLabSelectionInterface {
    public ArrayList FetchResult(HttpServletRequest req ,HttpServletResponse res, FinalLabSelectionForm form){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap dbInfoSys = info.getDmbsInfoLst();
        String cnt = (String)dbInfoSys.get("CNT");
        String vnm = util.nvl((String)form.getValue("vnmLst"));
        String view =util.nvl(form.getView());
        form.reset();
      
        ArrayList ary = new ArrayList();
        
        ary=new ArrayList();
       
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, ary);
        
        String conQ=" where stt in ('LB_FN') ";
        if(cnt.equals("sd") || cnt.equals("ag")){
            conQ=" where stt in ('LB_FN','MKAV') ";
        }
        if(cnt.equals("hk")){
            conQ=" where stt in ('LB_FN','MKT_TRF','MKAV') ";
        }
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1  ,  cert_lab , rmk,rap_dis ) " + 
        " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , sk1 ,  cert_lab , tfl3,decode(rap_rte ,'1',null,'0',null, trunc((nvl(upr,cmp)/greatest(rap_rte,1)*100)-100, 2)) "+
        "     from mstk b "+conQ;
      
        if(view.length() > 0){
        if(!vnm.equals("")){
             vnm = util.getVnm(vnm);
            srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))" ;
            
        }}
        ary = new ArrayList();
        
         ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("AS_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        ArrayList  stockList = SearchResult(req ,res, form , vnm);
       
        return stockList;
        
    }
      public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res, FinalLabSelectionForm form ,String vnmLst ){
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr(); 
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
        getLab(req , res , form);
      ArrayList pktList = new ArrayList();
      ArrayList vwPrpLst = ASPrprViw(req,res);
      ArrayList prcStt=getOptions(req,res);
      String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , cert_lab , rmk,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis  ";

      

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
              if(prcStt!=null && prcStt.size()>0)
              form.setValue("STT_"+stkIdn, prcStt.get(0)); 
              else
              form.setValue("STT_"+stkIdn, "AS_PRC");  
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
                   
                     if (prp.toUpperCase().equals("RAP_DIS"))
                     val = util.nvl(rs.getString("r_dis"));
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
  public HashMap getLab(HttpServletRequest req , HttpServletResponse res, FinalLabSelectionForm form){
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
      ArrayList labLst  = new ArrayList();
      HashMap labMap = new HashMap();
      String labSelection = " select a.stk_idn , b.val , b.grp from gt_srch_rslt a , stk_dtl b  where a.stk_idn = b.mstk_idn and mprp = ? order by a.stk_idn , b.grp ";
      ary = new ArrayList();
      ary.add("LAB");

      ArrayList outLst = db.execSqlLst("labQry", labSelection, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
      String pstkIdn = "0";
      try {
      while (rs.next()) {
          boolean isDiff = false;
          String lstkIdn = rs.getString("stk_idn");
          if(pstkIdn.equals("0")){
              pstkIdn = lstkIdn;
              isDiff = true;
          }
          
          if(!pstkIdn.equals(lstkIdn)){
              labMap.put(pstkIdn, labList);
              labList = new ArrayList();
              pstkIdn = lstkIdn;
              isDiff = true;
          }
          
          labDet labDet = new labDet();
          String labVal = rs.getString("val");
          String grp = util.nvl(rs.getString("grp"));
          labDet.setLabVal(labVal);
          labDet.setLabDesc(labVal);
          labDet.setGrp(grp);
          labList.add(labDet);
          if(isDiff){
              form.setValue("RD_"+pstkIdn, grp+"_"+labVal);
              form.setValue(labVal+"_"+pstkIdn+"_"+grp, "yes");
          }
          if(!labLst.contains(labVal))
              labLst.add(labVal);
         
      }
          rs.close(); pst.close();
      } catch (SQLException sqle) {
      // TODO: Add catch code
      sqle.printStackTrace();
      }
      
      if(!pstkIdn.equals("0"))
          labMap.put(pstkIdn, labList);
      
      session.setAttribute("labMap",labMap);
      session.setAttribute("AllLabLst", labLst);
      return labMap;
      
  }
    public ArrayList getOptions( HttpServletRequest req ,HttpServletResponse res){
    //HashMap prcStt = new HashMap();
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
    ResultSet rs=null;
    
    String PrcQ="select idn,prc,dsc from mprc where is_stt = ? " ;
    ary.add("LB_FN_AS");
    String PrcId="";

        ArrayList outLst = db.execSqlLst(" get PrcId", PrcQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        try {
        while(rs.next()){
        PrcId=util.nvl(rs.getString("idn"));
        }
            rs.close(); pst.close();
        } catch (SQLException e) {
        }
    ArrayList prcStt = new ArrayList();

    String getPrcToPrc = " select b.in_stt in_Stt from prc_to_prc a , mprc b " + 
    "where a.prc_id = ? and a.flg1='Y' and a.prc_to_id = b.idn";

    ary = new ArrayList();
    ary.add(PrcId);


        outLst = db.execSqlLst(" get options", getPrcToPrc, ary);
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
    try {
    while(rs.next()){
    prcStt.add(rs.getString("in_stt"));
    //prcStt.put(rs.getString("in_stt"), rs.getString("in_stt"));
    }
        rs.close(); pst.close();
    } catch (SQLException e) {
    
    }
    return prcStt ;
    }
    public HashMap GetTotal(HttpServletRequest req ,HttpServletResponse res){
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
    public FinalLabSelectionImpl() {
        super();
    }
    public void labDetail(HttpServletRequest req ,HttpServletResponse res, HashMap param){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String mdl = util.nvl((String)param.get("mdl"));
        if(mdl.equals("")){
            mdl="LAB_VIEW";
        }else{
          memoVeiw(req, res);
        }
        ArrayList grpList = new ArrayList();
        HashMap stockPrpUpd = new HashMap();
        String stockPrp = "select mstk_idn, lab,b.dta_typ, mprp, a.srt, grp " +
            ", decode(b.dta_typ, 'C', a.val,'N', to_char(a.num)||prop_pkg.get_val(mstk_idn, mprp)" +
            ", 'D', to_char(dte,'dd-mm-rrrr'), nvl(txt,'')) val " + 
        "from stk_dtl a, mprp b , rep_prp c " + 
        "where a.mprp = b.prp and mstk_idn =?  and b.dta_typ in ('C','N','T','D') and c.prp = b.prp and c.flg = 'Y' and mdl=? " + 
        "order by grp, psrt, mprp,lab ";
        ArrayList ary = new ArrayList();
        ary.add(param.get("stkIdn"));
        ary.add(mdl);

        ArrayList outLst = db.execSqlLst("stockDtl", stockPrp, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                String grp = util.nvl(rs.getString("grp"));
                if(!grpList.contains(grp))
                    grpList.add(grp);
                
                String mprp =util.nvl(rs.getString("mprp"));
                String val = util.nvl(rs.getString("val"));
              
                String msktIdn = util.nvl(rs.getString("mstk_idn"));
                stockPrpUpd.put(msktIdn+"_"+mprp+"_"+grp, val);
               
            }
            
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
       
        
        req.setAttribute("labDtlList",stockPrpUpd);
        req.setAttribute("grpList", grpList);
    
    }
    
    public ArrayList ASPrprViw(HttpServletRequest req ,HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("asViewLst");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();

                ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'AS_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close(); pst.close();
                session.setAttribute("asViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
    public ArrayList memoVeiw(HttpServletRequest req ,HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("MEMO_VW");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();

                ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MEMO_VW' and flg='Y' order by rnk ",
                               new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close(); pst.close();
                session.setAttribute("MEMO_VW", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
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
                rs1.close(); pst.close();
                session.setAttribute("LabViewLst", viewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return viewPrp;
    }
    
    public ArrayList LBGenricSrch(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("LBGNCSrch");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();

                ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp , flg  from rep_prp where mdl = 'LB_SRCH' and flg <> 'N'  order by rnk ",
                               new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    ArrayList asViewdtl=new ArrayList();
                    asViewdtl.add(rs1.getString("prp"));
                    asViewdtl.add(rs1.getString("flg"));
                    asViewPrp.add(asViewdtl);
                }
                rs1.close(); pst.close();
                session.setAttribute("LBGNCSrch", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
}
