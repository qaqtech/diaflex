package ft.com.lab;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.assort.AssortReturnForm;
import ft.com.dao.GtPktDtl;
import ft.com.dao.labDet;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.IOException;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LabResultImpl implements LabResultInterface{

  
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
       
        String labQry=" select val , dsc from prp where mprp = ?  and vld_till is null and flg is null";
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
    public ArrayList getLabFRGt(HttpServletRequest req , HttpServletResponse res){
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
       
        String labQry="select distinct cert_lab from gt_srch_rslt";
        ary = new ArrayList();

        ArrayList outLst = db.execSqlLst("labQry", labQry, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
        while (rs.next()) {
            labDet labDet = new labDet();
            String labVal = rs.getString("cert_lab");
            labDet.setLabVal(labVal);
            labDet.setLabDesc(rs.getString("cert_lab"));
            labList.add(labDet);
           
        }
              rs.close(); pst.close();
          } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
          }
    
    return labList;
     
    }
    public HashMap FetchResult(HttpServletRequest req, HttpServletResponse res, HashMap params , LabResultForm form){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        
        String vnm = util.nvl((String)params.get("vnm"));
        String seq = util.nvl((String)params.get("seq"));
        String lab = util.nvl((String)params.get("lab"));
        String frmdte = util.nvl((String)params.get("frmdte"));
        String todte = util.nvl((String)params.get("todte"));
        String labStt=util.nvl((String)params.get("labStt"));
        String issue = util.nvl((String)params.get("issue"));
        String srv = util.nvl((String)params.get("SRV"));
        ArrayList ary = null;
        ary = new ArrayList();
       
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, ary);
        
        if(labStt.equals("ALL") || labStt.equals("")){
            labStt="LB_RS','LB_RC','LB_OB','LB_RI','LB_CF','LB_CFRS";
        }
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn , vnm , pkt_dte, stt , flg , rmk , qty , cts , cert_lab , sk1,  cmp, rap_rte , quot , rap_dis) " + 
        " select  pkt_ty, b.idn, b.vnm,  b.dte, stt , 'Z' , tfl3, qty , cts , cert_lab, sk1 ,nvl(b.cmp,b.upr) , b.rap_rte , nvl(b.upr,b.cmp) , decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))  "+
        "     from mstk b "+
        " where stt in ('"+labStt+"') and cts > 0  " ;
        
           if(!seq.equals("") || (!frmdte.equals("") && !todte.equals(""))){
           
            srchRefQ = 
                    "    Insert into gt_srch_rslt (stk_idn , vnm , pkt_dte, stt , flg , qty , cts , rmk , cert_lab , sk1, pkt_ty,  cmp, rap_rte , quot , rap_dis ) " + 
                    " select distinct b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , tfl3 , b.cert_lab , b.sk1, pkt_ty,nvl(b.cmp,b.upr) , b.rap_rte , nvl(b.upr,b.cmp) , decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))  "+
                    "     from lab_inward_ora a , mstk b  "+
                    " where a.idn = b.idn  and b.stt in ('"+labStt+"') and b.cts > 0  and a.flg='P' " ;
            if(!seq.equals("")){
            srchRefQ = srchRefQ+ " and  a.load_seq = ? ";
            ary.add(seq);
            }else{
                srchRefQ = srchRefQ+ " and trunc(a.sdt) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') and a.idn = b.idn" ;
            }
        }
           
           if(!issue.equals("")){
                labStt = util.nvl((String)params.get("labStt"));
               if(labStt.equals("ALL") || labStt.equals(""))
                labStt="LB_RS','LB_RI";
                srchRefQ = 
               "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk , rln_idn , sk1,  cmp, rap_rte , quot , rap_dis ) " + 
               " select  b.pkt_ty, b.idn, b.vnm,  b.dte, b.stt , 'Z' , b.qty , b.cts , c.iss_id , b.tfl3 , c.iss_emp_id , b.sk1,nvl(b.cmp,b.upr) , b.rap_rte , nvl(b.upr,b.cmp) , decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))  "+
               "   from mstk b , iss_rtn_dtl c where b.idn = c.iss_stk_idn   "+
               "  and b.cts > 0  and b.stt in ('"+labStt+"') and c.iss_id = ? ";
              ary = new ArrayList();
              ary.add(issue);
              
           }
        if(!vnm.equals("")){
             vnm = util.getVnm(vnm);
            srchRefQ = srchRefQ+" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+"))" ;
            
        }
        if(!lab.equals("NA")){
                
                    srchRefQ = srchRefQ+"  and exists (select 1 from stk_dtl a where a.mstk_idn=b.idn and a.mprp='LAB_PRC' and a.val =? and a.grp=1)  " ;
                    ary.add(lab);
         }
      if(!srv.equals("0") && !srv.equals("")){
              
                  srchRefQ = srchRefQ+"  and exists (select 1 from stk_dtl f  where f.mstk_idn=b.idn and f.mprp='SERVICE' and f.val =? and f.grp=1)  " ;
                  ary.add(srv);
       }
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        System.out.println(srchRefQ);
   
      HashMap mprp = info.getMprp();
      HashMap prp = info.getPrp();
      HashMap paramsMap = new HashMap();
      ArrayList genricSrchLst = info.getGncPrpLst();
          for(int i=0;i<genricSrchLst.size();i++){
              ArrayList prplist =(ArrayList)genricSrchLst.get(i);
              String lprp = (String)prplist.get(0);
              String flg= (String)prplist.get(1);
              String typ = util.nvl((String)mprp.get(lprp+"T"));
              String prpSrt = lprp ;  
              if(flg.equals("M")) {
              
                  ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                  ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                  for(int j=0; j < lprpS.size(); j++) {
                  String lSrt = (String)lprpS.get(j);
                  String lVal = (String)lprpV.get(j);    
                  String reqVal1 = util.nvl((String)form.getValue(lprp + "_" + lVal),"");
                  if(!reqVal1.equals("")){
                  paramsMap.put(lprp + "_" + lVal, reqVal1);
                  }
                     
                  }
              }else{
              String fldVal1 = util.nvl((String)form.getValue(lprp+"_1"));
              String fldVal2 = util.nvl((String)form.getValue(lprp+"_2"));
              if(typ.equals("T")){
                  fldVal1 = util.nvl((String)form.getValue(lprp+"_1"));
                  fldVal2 = fldVal1;
              }
              if(fldVal2.equals(""))
              fldVal2=fldVal1;
              if(!fldVal1.equals("") && !fldVal2.equals("")){
                  paramsMap.put(lprp+"_1", fldVal1);
                  paramsMap.put(lprp+"_2", fldVal2);
              }
              }
          }
          
      if(paramsMap.size()>0 &&  vnm.equals("")){
        
          paramsMap.put("stt", "ALL");
          paramsMap.put("mdl", "LAB_VIEW");
          paramsMap.put("DELGT", "NO");
          util.genericSrchGRP3_SRCH(paramsMap);
      }else{
        String pktPrp = "srch_pkg.pop_pkt_prp(?)";
        ary = new ArrayList();
        ary.add("LAB_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
      }
        
        HashMap stockList = SearchResultGT(req,res, vnm , form);
        ArrayList labList = labIssueList(req);
        req.setAttribute("labList", labList);
        HashMap serviceList = getService(req,form);
        ArrayList issRtnComVAL = IssRtnComVal(req,"ISS_RTN_COM");
        HashMap cloclrMap = util.getColorClarityRS(issRtnComVAL);
        getUpDwDtl(req);
        req.setAttribute("ColClrMap", cloclrMap);
        return stockList;
        
    }
    
    public ArrayList labIssueList(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
     ArrayList labList = new ArrayList();
     String labSql = "select a.idn from mstk a , iss_rtn_dtl b where a.idn = b.iss_stk_idn and a.stt='LB_RI' and b.stt='IS'";

        ArrayList outLst = db.execSqlLst("labList", labSql, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
             labList.add(rs.getString("idn"));
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
     return labList;
    }
    
    public HashMap getService(HttpServletRequest req,LabResultForm form){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
//    int ct = db.execCall("refresh vw", "DBMS_MVIEW.REFRESH('MX_LAB_PRC_ISS_MV')", new ArrayList()); 
        
     HashMap serviceList = new HashMap();
//     String labSql = "select a.stk_idn , nvl(b.rtn_val , b.iss_val ) iss_val " + 
//     " from gt_srch_rslt a , iss_rtn_prp b, MX_LAB_PRC_ISS_MV m " + 
//     " where a.stk_idn = b.iss_stk_idn and b.iss_id = m.iss_id " + 
//     " and a.stk_idn = m.iss_stk_idn and b.mprp=? ";
//          String labSql = "select a.stk_idn , nvl(b.rtn_val , b.iss_val ) iss_val " + 
//     " from gt_srch_rslt a , iss_rtn_prp b " + 
//     " where a.srch_id=b.iss_id and a.stk_idn = b.iss_stk_idn " + 
//     " and b.mprp=? ";
     String labSql="  select b.iss_stk_idn stk_idn, nvl(b.rtn_val , b.iss_val ) iss_val\n" + 
     " from iss_rtn_prp b, gt_srch_rslt gt\n" + 
     " where b.iss_id = get_mx_prc_grp_iss_id(gt.stk_idn)\n" + 
     " and b.iss_stk_idn = gt.stk_idn and b.mprp= ?";
     ArrayList ary = new ArrayList();
     ary.add("SERVICE");

        ArrayList outLst = db.execSqlLst("labList", labSql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                String stkIdn = util.nvl(rs.getString("stk_idn"));
                form.setValue("SV_"+stkIdn, util.nvl(rs.getString("iss_val")));
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
     return serviceList;
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
    
   
    public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res,String vnmLst, LabResultForm form ){
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
        String reptTyp = util.nvl((String)form.getValue("reportTyp"));
         ArrayList ary = new ArrayList();
        ary.add(reptTyp);
         int ct = db.execCall("labRtn", "ISS_RTN_PKG.LAB_RTN_ISS_TYP(pTyp => ? , pLoc => 'LS')", ary);
        ArrayList vwPrpLst = LabPrprViw(req,res);
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, sk1 , stt ,rmk , qty , cts , cert_lab  ";
        HashMap srchReckObsMap = new HashMap();
        

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

        
        String rsltQ = srchQ + "  from gt_srch_rslt where flg =? order by sk1 , cts ";
        
         ary = new ArrayList();
        ary.add("Z");

        ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
               
                String stkIdn = util.nvl(rs.getString("stk_idn"));
                String stt = util.nvl(rs.getString("stt"));
            
                form.setValue("stt_"+stkIdn, stt);
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", stt);
                    pktPrpMap.put("sk1", util.nvl(rs.getString("sk1")));
                    
                String vnm = util.nvl(rs.getString("vnm"));
                    String tfl3 = util.nvl(rs.getString("rmk"));
                    if(vnmLst.indexOf(tfl3)!=-1 && !tfl3.equals("") ){
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
                pktPrpMap.put("vnm",vnm);
                pktPrpMap.put("stk_idn", stkIdn);
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                 pktPrpMap.put("cert_lab",util.nvl(rs.getString("cert_lab")));
                srchReckObsMap.put(stkIdn, "");
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                    
                    if(prp.equals("LAB"))
                        fld="cert_lab";
                    String val = util.nvl(rs.getString(fld)) ;
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
        session.setAttribute("srchReckObsMap", srchReckObsMap);
        return pktList;
    }
    
    public HashMap SearchResultGT(HttpServletRequest req ,HttpServletResponse res,String vnmLst, LabResultForm form ){
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
        HashMap pktList = new HashMap();
        String reptTyp = util.nvl((String)form.getValue("reportTyp"));
         ArrayList ary = new ArrayList();
        ary.add(reptTyp);
         int ct = db.execCall("labRtn", "ISS_RTN_PKG.LAB_RTN_ISS_TYP(pTyp => ? , pLoc => 'LS')", ary);
        ArrayList vwPrpLst = LabPrprViw(req,res);
        String  srchQ =  " select stk_idn , pkt_ty, srch_id, vnm,  to_char(pkt_dte,'dd-mm-yyyy') dte, sk1 , stt ,rmk ,cmp,rap_rte, qty , cts , cert_lab ,quot, cert_no ";
        HashMap srchReckObsMap = new HashMap();
        ArrayList issIdnLst = new ArrayList();

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

        
        String rsltQ = srchQ + "  from gt_srch_rslt where flg =? order by sk1 , cts ";
        
         ary = new ArrayList();
        ary.add("Z");

        ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
               
                String stkIdn = util.nvl(rs.getString("stk_idn"));
                String stt = util.nvl(rs.getString("stt"));
                String iss_id = util.nvl(rs.getString("srch_id"));
                if(!issIdnLst.contains(iss_id))
                    issIdnLst.add(iss_id);
                form.setValue("stt_"+stkIdn, stt);
                    GtPktDtl pktPrpMap = new GtPktDtl();
                pktPrpMap.setValue("stt", stt);
                    pktPrpMap.setValue("sk1", util.nvl(rs.getString("sk1")));
                    pktPrpMap.setSk1(new BigDecimal(util.nvl(rs.getString("sk1"),"0")));
                String vnm = util.nvl(rs.getString("vnm"));
                    String tfl3 = util.nvl(rs.getString("rmk"));
                    if(vnmLst.indexOf(tfl3)!=-1 && !tfl3.equals("") ){
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
                pktPrpMap.setValue("vnm",vnm);
                pktPrpMap.setValue("stk_idn", stkIdn);
                pktPrpMap.setValue("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.setValue("cts",util.nvl(rs.getString("cts")));
                pktPrpMap.setValue("iss_idn",iss_id);
                pktPrpMap.setValue("cert_lab",util.nvl(rs.getString("cert_lab")));
                pktPrpMap.setValue("cert_no",util.nvl(rs.getString("cert_no")));
                pktPrpMap.setValue("cmp",util.nvl(rs.getString("cmp")));
                pktPrpMap.setValue("rap_rte",util.nvl(rs.getString("rap_rte")));
                pktPrpMap.setValue("quot",util.nvl(rs.getString("quot")));
                pktPrpMap.setValue("pkt_dte",util.nvl(rs.getString("dte")));

                    
                srchReckObsMap.put(stkIdn, "");
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                    
                    if(prp.equals("LAB"))
                        fld="cert_lab";
                    String val = util.nvl(rs.getString(fld)) ;
                      pktPrpMap.setValue(prp, val);
                 }
                              
                    pktList.put(stkIdn ,pktPrpMap);
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
        gtMgr.setValue("IssueIdnLst", issIdnLst);
        gtMgr.setValue("srchReckObsMap", srchReckObsMap);
        pktList =(HashMap)util.sortByComparator(pktList);
        return pktList;
    }
    
    public ArrayList LabPrprViw(HttpServletRequest req ,HttpServletResponse res){
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
    public ArrayList StockView(HttpServletRequest req,HttpServletResponse res, HashMap params){
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
        HashMap srchReckObsMap = new HashMap();
      
        int issIdn=0;
        ResultSet rs =null;
        ArrayList outLst =null;
        PreparedStatement pst = null;
        ArrayList ary = null;
        ary = new ArrayList();
        ary.add(params.get("mstkIdn"));
        String getIssIdn = "select ISS_RTN_PKG.LST_ISS_ID(?) issIdn from dual";
        try {

            outLst = db.execSqlLst("issIdn", getIssIdn, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            if(rs.next()){
                issIdn = rs.getInt(1);
            }
            rs.close(); pst.close();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        
        
        ArrayList stockPrpList = new ArrayList();
        
        HashMap mprp = info.getMprp();
        String stockPrp ="select a.*" +
            ", decode(d.dta_typ, 'C', c.val, 'N', to_char(c.num), 'D', to_char(c.dte, 'dd-Mon-rrrr'), c.txt) stk_val , " +
            " to_char(a.sdt,'dd-mm-yyyy') iss_dt from iss_rtn_prp a , rep_prp b, stk_dtl c, mprp d " +
            " where b.mdl='LAB_RS' and a.mprp = b.prp and a.iss_stk_idn = c.mstk_idn and c.mprp = d.prp and c.grp = 1 and a.mprp = d.prp "+
            "and a.iss_id= ? and a.iss_stk_idn =? and b.flg='Y' " +
            " order by a.grp , b.rnk ";

        ary = new ArrayList();
        ary.add(String.valueOf(issIdn));
        ary.add(params.get("mstkIdn"));

        outLst = db.execSqlLst("stockDtl", stockPrp, ary);
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                HashMap stockPrpUpd = new HashMap();
                String lprp = util.nvl(rs.getString("mprp"));
                String stkVal = util.nvl(rs.getString("stk_val"));
                String issVal = util.nvl(rs.getString("iss_val"));
                String rtnVal = util.nvl(rs.getString("rtn_val"));
                String issDt = util.nvl(rs.getString("iss_dt"));
                String dataTyp = util.nvl((String)mprp.get(lprp+"T"));
                if(dataTyp.equals("N")){
                    issVal = util.nvl(rs.getString("iss_num"));
                    rtnVal = util.nvl(rs.getString("rtn_num"));
                }
                if(dataTyp.equals("T")){
                    issVal = util.nvl(rs.getString("txt"));
                    rtnVal = util.nvl(rs.getString("txt"));
                }
                stockPrpUpd.put("mprp",lprp);
                stockPrpUpd.put("stkVal",stkVal);
                stockPrpUpd.put("issVal",issVal);
                stockPrpUpd.put("rtnVal",rtnVal) ;
                stockPrpUpd.put("issDt",issDt) ;
                stockPrpUpd.put("mstkIdn",util.nvl(rs.getString("iss_stk_idn")));
                stockPrpList.add(stockPrpUpd);
                if(lprp.equals("RCHK"))
                    srchReckObsMap.put(util.nvl(rs.getString("iss_stk_idn")), issVal);
               
            }
            
            rs.close(); pst.close();
            
           String stockSql=" select to_char(iss_dt,'dd-mm-yyyy') iss_dt,to_char(rtn_dt,'dd-mm-yyyy') rtn_dt from iss_rtn_dtl where iss_id = ? and iss_stk_idn=?" ;
            ary = new ArrayList();
            ary.add(String.valueOf(issIdn));
            ary.add(params.get("mstkIdn"));

            outLst = db.execSqlLst("stockDtl", stockSql, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            if(rs.next()){
                req.setAttribute("iss_dt", rs.getString("iss_dt"));
                req.setAttribute("rtn_dt", rs.getString("rtn_dt"));
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
       params.put("iss_id", issIdn);
        HashMap stockHis = StockHistory( req,res, params);
        req.setAttribute("StockHisList", stockHis);
        gtMgr.setValue("srchReckObsMap", srchReckObsMap);
        return stockPrpList;
    }
    public HashMap AssortHistory(HttpServletRequest req ,HttpServletResponse res, HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap dataDtlMap = new HashMap();
        ArrayList issueList = new ArrayList();
        ArrayList prpList = new ArrayList();
        String assortHis ="select a.iss_id , a.mprp , decode(f.dta_typ, 'C', a.rtn_val,'N', to_char(a.rtn_num),  nvl(txt,'')) val , c.prc ,byr.get_nm(b.emp_id) emp "+
       " , to_char( b.iss_dt, 'dd-Mon-rrrr HH24:mi:ss') dte from iss_rtn_prp a, iss_rtn b , mprc c , rep_prp e , mprp f where a.iss_id = b.iss_id and b.prc_id = c.idn "+
       " and a.iss_stk_idn =? and e.prp = a.mprp and e.mdl=? and e.prp=f.prp and e.flg ='Y' and c.grp in ('ASRT','FNL ASRT')  "+
       " order by b.iss_id desc , e.rnk";
        ArrayList ary = new ArrayList();
        ary.add(params.get("mstkIdn"));
        ary.add("LAB_RS");
        ArrayList outLst = db.execSqlLst("stockDtl", assortHis, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                String iss_id = util.nvl(rs.getString("iss_id"));
                String mprp = util.nvl(rs.getString("mprp"));
                String val = util.nvl(rs.getString("val"));
                if(!issueList.contains(iss_id))
                  issueList.add(iss_id);
                if(!prpList.contains(mprp))
                   prpList.add(mprp);
                dataDtlMap.put(iss_id+"_"+mprp , val);
                dataDtlMap.put(iss_id+"_EMP", util.nvl(rs.getString("emp"))); 
                dataDtlMap.put(iss_id+"_PRC", util.nvl(rs.getString("prc")));  
                dataDtlMap.put(iss_id+"_DTE", util.nvl(rs.getString("dte")));  
            }
            rs.close(); pst.close();
        HashMap stkDtlPrpMap = new HashMap();
        String stkDTl ="  select a.mprp, decode(b.dta_typ, 'C', a.val, 'N', a.num, 'D', a.dte, a.txt) val " + 
        " from stk_dtl a, mprp b, rep_prp c " + 
        " where a.mprp = b.prp and b.prp = c.prp  and a.mstk_idn = ? and c.mdl = ? and a.grp=1 " + 
        " order by c.rnk, c.srt";

            outLst = db.execSqlLst("stkDtl", stkDTl, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            stkDtlPrpMap.put(util.nvl(rs.getString("mprp")), util.nvl(rs.getString("val")));
        }
            rs.close(); pst.close();
        req.setAttribute("stkDtlMap", stkDtlPrpMap);
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
       
        req.setAttribute("issueList", issueList);
        req.setAttribute("prpList", prpList);
        return dataDtlMap;
    }
    
    public HashMap ProcessHistory(HttpServletRequest req ,HttpServletResponse res, HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap dataDtlMap = new HashMap();
        ArrayList issueList = new ArrayList();
        ArrayList prpList = new ArrayList();
        String assortHis ="select a.iss_id , a.mprp , decode(f.dta_typ, 'C', a.rtn_val,'N', to_char(a.rtn_num),  nvl(txt,'')) val , decode(f.dta_typ, 'C', a.iss_val,'N', to_char(a.iss_num),  nvl(txt,'')) Issval , c.prc ,byr.get_nm(b.emp_id) emp "+
       " , to_char( b.iss_dt, 'dd-Mon-rrrr HH24:mi:ss') dte from iss_rtn_prp a, iss_rtn b , mprc c , rep_prp e , mprp f where a.iss_id = b.iss_id and b.prc_id = c.idn "+
       " and a.iss_stk_idn =? and e.prp = a.mprp and e.mdl=? and b.stt <> 'CL'  and e.prp=f.prp and e.flg ='Y'   "+
       " order by b.iss_id desc , e.rnk";
        ArrayList ary = new ArrayList();
        ary.add(params.get("mstkIdn"));
        ary.add("LAB_RS");
        ArrayList outLst = db.execSqlLst("stockDtl", assortHis, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                String iss_id = util.nvl(rs.getString("iss_id"));
                String mprp = util.nvl(rs.getString("mprp"));
                String val = util.nvl(rs.getString("val"));
                String issval = util.nvl(rs.getString("Issval"));
                if(!issueList.contains(iss_id))
                  issueList.add(iss_id);
                if(!prpList.contains(mprp))
                   prpList.add(mprp);
                dataDtlMap.put(iss_id+"_"+mprp+"RTN" , val);
                dataDtlMap.put(iss_id+"_"+mprp+"ISS" , issval);
                dataDtlMap.put(iss_id+"_EMP", util.nvl(rs.getString("emp"))); 
                dataDtlMap.put(iss_id+"_PRC", util.nvl(rs.getString("prc")));  
                dataDtlMap.put(iss_id+"_DTE", util.nvl(rs.getString("dte")));  
            }
            rs.close(); pst.close();
      
      
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
       
        req.setAttribute("issueList", issueList);
        req.setAttribute("prpList", prpList);
        return dataDtlMap;
    }
    public HashMap StockHistory(HttpServletRequest req ,HttpServletResponse res, HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList issueList = new ArrayList();
        int issuID = (Integer)params.get("iss_id");
        ArrayList stockPrpList = new ArrayList();
        HashMap stockPrpMap = new HashMap();
        String stockPrp = "select a.iss_id ,to_char(b.iss_dt,'dd-mm-yyyy') iss_dt,to_char(g.rtn_dt,'dd-mm-yyyy') rtn_dt, a.mprp , decode(f.dta_typ, 'C', a.rtn_val,'N', to_char(a.rtn_num),  nvl(txt,'')) val , replace(upper(c.prc||b.emp) , 'LAB', '') emp_prc   " +
            " from iss_rtn_prp a, iss_rtn_v b , mprc c , rep_prp e , mprp f , iss_rtn_dtl g " + 
        " where a.iss_id = b.iss_id and b.prc_id = c.idn and a.iss_stk_idn =? and e.prp = a.mprp and e.mdl=? and e.prp=f.prp " + 
        " and e.flg ='Y' and a.iss_id <> "+issuID+ " and g.iss_stk_idn = a.iss_stk_idn and a.iss_id = g.iss_id "+
        " and c.is_stt in ('LB_IS','LB_RI') order by b.iss_id desc , e.rnk ";
        ArrayList ary = new ArrayList();
        ary.add(params.get("mstkIdn"));
        ary.add("LAB_RS");
       
     

        ArrayList outLst = db.execSqlLst("stockDtl", stockPrp, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                String iss_id = util.nvl(rs.getString("iss_id"));
                String mprp = util.nvl(rs.getString("mprp"));
                String val = util.nvl(rs.getString("val"));
                if(!issueList.contains(iss_id))
                  issueList.add(iss_id);
               
                 stockPrpMap.put(iss_id+"_"+mprp , val); 
                 stockPrpMap.put(iss_id+"_ISSDTE" , util.nvl(rs.getString("iss_dt")));
                 stockPrpMap.put(iss_id+"_RTNDTE" , util.nvl(rs.getString("rtn_dt")));
                 stockPrpMap.put(iss_id+"_HDR" , util.nvl(rs.getString("emp_prc")));
             }
            
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        String repHis = "select a.iss_id ,to_char(e.rtn_dt,'dd-mm-yyyy') rtn_dt, to_char(b.iss_dt,'dd-mm-yyyy') iss_dt, a.mprp , \n" + 
        "decode(d.dta_typ, 'C', a.rtn_val,'N', to_char(a.rtn_num),  nvl(txt,'')) val ,\n" + 
        "replace(upper(c.prc||b.emp) , 'LAB', '') emp_prc   from iss_rtn_prp a, iss_rtn_v b , mprc c , mprp d , iss_rtn_dtl e\n" + 
        "where a.iss_id = b.iss_id and b.prc_id = c.idn   and c.is_stt in ('REP_IS') and a.iss_stk_idn = e.iss_stk_idn and a.iss_stk_idn = ?\n" + 
        "and a.mprp=d.prp  and a.iss_id = e.iss_id \n" + 
        "order by b.iss_id desc ";
      ary = new ArrayList();
      ary.add(params.get("mstkIdn"));

        outLst = db.execSqlLst("repHis", repHis, ary);
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
      try {
          while(rs.next()) {
              String iss_id = util.nvl(rs.getString("iss_id"));
              String mprp = util.nvl(rs.getString("mprp"));
              String val = util.nvl(rs.getString("val"));
              if(!issueList.contains(iss_id))
                issueList.add(iss_id);
             
               stockPrpMap.put(iss_id+"_"+mprp , val); 
               stockPrpMap.put(iss_id+"_ISSDTE" , util.nvl(rs.getString("iss_dt")));
               stockPrpMap.put(iss_id+"_RTNDTE" , util.nvl(rs.getString("rtn_dt")));
               stockPrpMap.put(iss_id+"_HDR" , util.nvl(rs.getString("emp_prc")));
           }
          
          rs.close(); pst.close();
      } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
      }
        req.setAttribute("issueList", issueList);
      
       return stockPrpMap;
    }
    
    public LabResultImpl() {
        super();
    }
    
//    public ArrayList LBRSGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("LBRSGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp , flg  from rep_prp where mdl = 'LBRS_SRCH' and flg in ('Y','S') order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close(); pst.close();
//                session.setAttribute("LBRSGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
//    
        public ArrayList pktList(HttpServletRequest req , HttpServletResponse res,String flg){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst =genericInt.genericPrprVw(req, res, "ASRT_LAB_COMP","ASRT_LAB_COMP");
        ArrayList pktDtlList = new ArrayList();
        int sr=1;
        String prvVnm="";
        HashMap pktDtl = null;
        ArrayList itmHdr = new ArrayList();
        itmHdr.add("SR NO");
        itmHdr.add("VNM");
        
         String pktPrp = "DP_LAB_ASRT_COMP(pMdl => 'ASRT_LAB_COMP',pFlg => ?)";
         ArrayList ary = new ArrayList();
         ary.add(flg);
         int ct = db.execCall(" Srch Prp ", pktPrp, ary);
            
        String reportQuery = " select vnm ,sk1, stk_idn ,to_char(pkt_dte,'dd-MON-yyyy') pkt_dte, rap_rte ,cts,vlu_inr diff, decode(rap_rte ,'1',null, trunc((quot/greatest(rap_rte,1)*100)-100, 2)) r_dis, to_char(trunc(cts,2) * quot, '99999990.00') vlu,to_char(trunc(cts,2) * rap_rte, '99999990.00') rapvlu,kts_vw,cmnt ";
         
         for (int i = 0; i < vwPrpLst.size(); i++) {
             String prp=(String)vwPrpLst.get(i);
             String fld = "prp_";
             int j = i + 1;
             if (j < 10)
                 fld += "00" + j;
             else if (j < 100)
                 fld += "0" + j;
             else if (j > 100)
                 fld += j;

            reportQuery += ", " + fld;    
            itmHdr.add(prp);
             if(prp.equals("MFG_CMP"))
                 itmHdr.add("DIFF");
         }
         
         itmHdr.add("RAP_RTE");
         itmHdr.add("RAP VLU");
         reportQuery = reportQuery +" from GT_SRCH_MULTI order by sk1,vnm,stt";
 
            ArrayList outLst = db.execSqlLst("conditionSql", reportQuery ,new ArrayList() );
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
          try {
             while (rs.next()) {
                pktDtl = new HashMap();
                String vnm=util.nvl(rs.getString("vnm"));
                 
                if(prvVnm.equals("")){
                prvVnm=vnm;
                pktDtl.put("SR NO",Integer.toString(sr));
                pktDtl.put("VNM",prvVnm);
                sr++;
                }
                 
                if(!prvVnm.equals(vnm)){
                     prvVnm=vnm; 
                     pktDtlList.add(new HashMap());
                     pktDtl.put("SR NO",Integer.toString(sr));
                     pktDtl.put("VNM",prvVnm);
                     sr++;
                }
                
                pktDtl.put("STK_IDN", util.nvl(rs.getString("stk_idn")));
                pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
                pktDtl.put("RAP VLU", util.nvl(rs.getString("rapvlu")));
                pktDtl.put("STK_IDN", util.nvl(rs.getString("stk_idn")));
                pktDtl.put("RAP_DIS", util.nvl(rs.getString("r_dis")));
                pktDtl.put("DIFF", util.nvl(rs.getString("diff")));
                 for(int j=0; j < vwPrpLst.size(); j++){
                      String prp = (String)vwPrpLst.get(j);
                       String fld="prp_";
                       if(j < 9)
                               fld="prp_00"+(j+1);
                       else    
                               fld="prp_0"+(j+1);
                       String val = util.nvl(rs.getString(fld)) ;
                     if(prp.equals("KTSVIEW"))
                         val = util.nvl(rs.getString("kts_vw"));
                     if(prp.equals("COMMENT"))
                         val = util.nvl(rs.getString("cmnt"));
                     if(prp.equals("RAP_RTE"))
                         val = util.nvl(rs.getString("rap_rte"));
                     if(prp.equals("RAP_DIS"))
                         val = util.nvl(rs.getString("r_dis"));
                     if(prp.equals("DTE"))
                         val = util.nvl(rs.getString("pkt_dte"));
                     if(prp.equals("CRTWT"))
                         val = util.nvl(rs.getString("cts"));
                         
                         pktDtl.put(prp, val);
                 }
                 pktDtlList.add(pktDtl);          
            }
             rs.close(); pst.close();
         } catch (SQLException sqle) {
             // TODO: Add catch code
             sqle.printStackTrace();
         }
            req.setAttribute("ItemHdr", itmHdr);
        return pktDtlList ;    
        }
//    public ArrayList pktList(HttpServletRequest req , HttpServletResponse res,String flg){
//        HttpSession session = req.getSession(false);
//        InfoMgr info = (InfoMgr)session.getAttribute("info");
//        DBUtil util = new DBUtil();
//        DBMgr db = new DBMgr(); 
//        db.setCon(info.getCon());
//        util.setDb(db);
//        util.setInfo(info);
//        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//        util.setLogApplNm(info.getLoApplNm());
//        HashMap dbinfo = info.getDmbsInfoLst();
//        String sh = (String)dbinfo.get("SHAPE");
//        String col = (String)dbinfo.get("COL");
//        String clr =(String)dbinfo.get("CLR");
//        ArrayList pktList = new ArrayList();
//        ArrayList itmHdr = new ArrayList();
//        itmHdr.add("SR NO");
//        itmHdr.add("VNM");
//       
//        String mstkSql ="select a.vnm , a.idn , a.rap_rte ,a.cts , nvl(a.upr,a.cmp) upr , decode(a.rap_rte ,'1',null, trunc((nvl(a.upr,a.cmp)/greatest(a.rap_rte,1)*100)-100, 2)) r_dis, to_char(trunc(a.cts,2) * nvl(a.upr,a.cmp), '99999990.00') amt , a.upr_dis from mstk a , gt_srch_rslt b where a.idn = b.stk_idn and b.flg='"+flg+"' order by a.sk1 , a.vnm ";
//        ResultSet rs = db.execSql("mstk", mstkSql, new ArrayList());
//        try {
//            int sr=0;
//            while (rs.next()) {
//                sr++;
//                String iss_dte = "";
//                String rtn_dte ="";
//               HashMap pktDtl = new HashMap();
//                String r_dis = util.nvl(rs.getString("r_dis"));
//                String idn = util.nvl(rs.getString("idn"));
//                String vnm = util.nvl(rs.getString("vnm"));
//                String cts = util.nvl(rs.getString("cts"));
//                String upr = util.nvl(rs.getString("upr"));
//                String upr_dis = util.nvl(rs.getString("upr_dis"));
//                String newrap_rte = util.nvl(rs.getString("rap_rte"));
//                pktDtl.put("STK_IDN", idn);
//                pktDtl.put("VNM", vnm);
//                pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
//                pktDtl.put("RAP Vlu", util.nvl(rs.getString("amt")));
//                pktDtl.put("SR NO",Integer.toString(sr));
//                String getPktPrp =
//                    " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', nvl(to_char(trunc(a.num,2), '999990.09'),''), 'D', to_char(a.dte, 'dd-Mon-rrrr'), a.txt) val ,  grp "
//                    + " from stk_dtl a, mprp b, rep_prp c "
//                    + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'ASRT_LAB_COMP' and c.flg='Y' and a.mstk_idn = ? "
//                    + " and grp in (1,2) order by a.grp desc  ";
//
//                ArrayList params = new ArrayList();
//                params.add(idn);
//                ResultSet rs1 = db.execSql("stkDtl", getPktPrp, params);
//                int pGrp = 0;
//              double newrap_dis =0;
//                while(rs1.next()){
//                
//                    int grp = rs1.getInt("grp");
//                    if(pGrp==0)
//                        pGrp=grp;
//                    
//                    if(pGrp!=grp){
//                        if(pGrp==2){
//                            String getIssIdn = "select ISS_RTN_PKG.LST_ISS_ID(?,?) issIdn from dual";
//                            params = new ArrayList();
//                            params.add(idn);
//                            params.add("FNL ASRT");
//                            ResultSet rs2 = db.execSql("issIdn", getIssIdn, params);
//                            if(rs2.next()){
//                             int issIdn = rs2.getInt(1);
//                             String issRtnVal = " select a.mprp , decode(b.dta_typ, 'C', c.prt1, 'N', to_char(a.rtn_num) , a.txt) rtn_val "+
//                                              " from iss_rtn_prp a , mprp b, prp c where a.mprp = b.prp and b.prp = c.mprp and  "+
//                                             " c.val = decode(b.dta_typ, 'C', a.rtn_Val, 'NA') and a.iss_id=? and a.iss_stk_idn = ? ";
//                                params = new ArrayList();
//                                params.add(String.valueOf(issIdn));
//                                params.add(idn);
//                                rs2 = db.execSql("issRtn", issRtnVal, params);
//                                while(rs2.next()){
//                                    String lprp = rs2.getString("mprp");
//                                    String val = util.nvl(rs2.getString("rtn_val"));
//                                    pktDtl.put(lprp, val);
//                                }
//                             
//                            }
//                            
//                        }
//                    String shval = (String)pktDtl.get(sh);
//                    String colVal = (String)pktDtl.get(col);
//                    String clrVal = (String)pktDtl.get(clr);
//                    String gtPrc = " select RAP_PRC(?,?,?,?,?) rap_rte from dual" ;
//                    ArrayList ary = new ArrayList();
//                    ary.add(shval);
//                    ary.add(cts);
//                    ary.add(clrVal);
//                    ary.add(colVal);
//                    ary.add("1");
//                    ResultSet rs3 = db.execSql("gtPrc",gtPrc, ary);
//                      
//                    while(rs3.next()){
//                        String rap_rte = util.nvl(rs3.getString("rap_rte"));
//                        if(!rap_rte.equals("") && !upr.equals("") ){
//                           double rap = Double.parseDouble(rap_rte);
//                          double rapVlu = Double.parseDouble(rap_rte)*Double.parseDouble(cts);
//                           double rte = Double.parseDouble(upr);
//                           if(!upr_dis.equals(""))
//                           newrap_dis = util.Round((((rap*(100+Double.parseDouble(upr_dis))/100)/Double.parseDouble(newrap_rte)*100) - 100),2);
//                           else
//                           newrap_dis=0;  
//                           pktDtl.put("RAP_RTE", Double.toString(rap));
//                           pktDtl.put("RAP_DIS", r_dis);
//                          pktDtl.put("RAP Vlu",  Double.toString(rapVlu));
//                        }
//                    }
//                   
//              
//                    String labdte = "DP_GET_LST_LAB_DTE(pStkIdn => ? , pIssDte => ?, pRtnDte => ?)";
//                    params = new ArrayList();
//                    params.add(idn);
//                    ArrayList out = new ArrayList();
//                    out.add("V");
//                    out.add("V");
//                    CallableStatement ctsDte = db.execCall("lab dte",labdte, params,out);
//                    iss_dte = util.nvl(ctsDte.getString(params.size()+1));
//                    rtn_dte = util.nvl(ctsDte.getString(params.size()+2));
//                    pktDtl.put("DTE", iss_dte);
//                    pktList.add(pktDtl);
//                    pktDtl = new HashMap();
//                    pGrp=grp;
//                    pktDtl.put("STK_IDN","");
//                    pktDtl.put("VNM", "");
//                    pktDtl.put("SR NO", "");
//                    pktDtl.put("DTE",rtn_dte);
//                    pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
//                    pktDtl.put("RAP Vlu", util.nvl(rs.getString("amt")));
//                    
//                    }
//                    
//                    
//                    String lprp = rs1.getString("mprp");
//                    String val = util.nvl(rs1.getString("val"));
//                    if(lprp.equals("DTE") )
//                        val= rtn_dte;
//                    if(lprp.equals("RAP_DIS"))
//                        val= String.valueOf(newrap_dis);
//                    pktDtl.put(lprp, val);
//                    
//                }
//               
//                if(pGrp!=0){                    
//                    pktList.add(pktDtl);
//                }
//                pktDtl = new HashMap();
//                pktDtl.put("NEW_LN","");
//                pktList.add(pktDtl);
//            }
//            rs.close(); pst.close();
//           
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        itmHdr = assortLabCom(req,itmHdr);
//        itmHdr.add("RAP_RTE");
//        itmHdr.add("RAP Vlu");
//        req.setAttribute("ItemHdr", itmHdr);
//       return pktList;
//    }
    public void packetData(HttpServletRequest req, HttpServletResponse res, LabResultForm form) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        int ct=0;
        HashMap dbinfo = info.getDmbsInfoLst();
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        String delQ = " Delete from gt_srch_rslt ";
        ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        String vnm = util.nvl((String)form.getValue("vnm"));
        ArrayList ary = new ArrayList();
      if(!vnm.equals(""))
      vnm = util.getVnm(vnm);
     
         if(!vnm.equals("")){
          String[] vnmLst = vnm.split(",");
          int loopCnt = 1 ;
          System.out.println(vnmLst.length);
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
                String vnmSub =vnm.substring(fromLoc, toLoc);
          
          String srchRefQ =
          "    Insert into gt_srch_rslt ( srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt,  rap_rte,rap_dis , cert_lab, cert_no, flg, sk1, fquot, quot , cmp  ) " +
          "     select  1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt,  rap_rte ,  decode(rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2)) " +
          "     , cert_lab, cert_no, 'Z' flg, sk1, upr, cmp , cmp " +
          "    from mstk b " +
          "     where  ( b.vnm in ("+vnmSub+") or b.tfl3 in ("+vnmSub+") ) "+
          " and b.stt in('LB_RS','LB_IS','LB_RI','LB_CS','LB_CF','LB_CSRS')";
              ct = db.execUpd(" Srch Ref ", srchRefQ,new ArrayList());
          }
      ary = new ArrayList();
      String pktPrp = "pkgmkt.pktPrp(0,?)";
      ary = new ArrayList();
      ary.add("LAB_VIEW");
      ct = db.execCall(" Srch Prp ", pktPrp, ary);
      HashMap serviceList = getService(req,form);
      }
    }
    
    public HashMap SearchResult(HttpServletRequest req,HttpServletResponse res, String flg , ArrayList vwPrpLst) {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            HashMap pktListMap = new HashMap();
            ArrayList pktStkIdnList = new ArrayList();    
            String srchQ = "";
            HashMap pktPrpMap = new HashMap();
            String srchQ1 = " select sk1, 0 dsp , a.stk_idn , cert_lab , dsp_stt , decode(stt " + 
            ", 'MKPP','color:Green'" + 
            " , 'MKLB','color:Maroon'" + 
            " , 'MKAV', decode(instr(upper(dsp_stt), 'NEW'), 0, ' ', 'color:Blue')" + 
            " , 'MKIS', 'color:Red'" + 
            " , 'SOLD') class ,  ";
            String srchQ2 = " select sk1, 1 dsp , a.stk_idn , cert_lab , '' dsp_stt , '' class , ";
            String dspStk = "stkDspFF";
            String getQuot = "quot rte";
           srchQ += "  cts crtwt, " +
                    //decode(quot, 0, 0, decode(nvl(rap_dis,0), 0, 101, trunc(((quot*100)/greatest(rap_rte,100)) - 100,2))) rr_dis, "+
                    " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis " +
                    " , stk_idn  , vnm, kts_vw kts, cert_lab cert, cert_no, rmk, img, cmp , trunc(((cmp*100)/greatest(rap_rte,1)) - 100,2) cmp_dis ,  to_char(trunc(cts,2),'9990.99') cts, qty , rap_rte , " +
                    getQuot +
                    ", cmnt,stt stt1,  nvl(fquot,0) fquot , flg , to_char(trunc(cts,2) * quot, '99999990.00') amt , " +
                    " decode(nvl(fquot,0),0, 'NA', decode(fquot, quot, 'NA', decode(greatest(fquot, quot),quot, 'UP','DN'))) cmp_flg,srch_id ";

         

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

         
            String rsltQ =
               srchQ1+" "+srchQ + " from gt_srch_rslt a where flg in ("+flg+") union " +srchQ2+ " "+srchQ +"   from gt_srch_multi a " ;
                rsltQ = rsltQ+  " order by sk1";
            
            ArrayList ary = new ArrayList();
            

            ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            pktListMap = new HashMap();
            try {
                while (rs.next()) {
                    String vnm =util.nvl(rs.getString("vnm"));
                    String cert_No = util.nvl(rs.getString("cert_no"));
                    pktPrpMap = new HashMap();
                    String stkIdn = util.nvl(rs.getString("stk_idn"));
                    pktPrpMap.put("flg", util.nvl(rs.getString("flg")));
                    pktPrpMap.put("stt", util.nvl(rs.getString("dsp_stt")));
                    pktPrpMap.put("iss_idn",util.nvl(rs.getString("srch_id")));
                    pktPrpMap.put("stk_idn",stkIdn);
                    pktPrpMap.put("vnm",vnm);
                    pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                    pktPrpMap.put("class",util.nvl(rs.getString("class")));
                    pktPrpMap.put("cts", util.nvl(rs.getString("cts")));
                    pktPrpMap.put("cert_no",cert_No);
                    pktPrpMap.put("stt1",util.nvl(rs.getString("stt1")));
                    pktPrpMap.put("quot",util.nvl(rs.getString("rte")));
                    pktPrpMap.put("fquot",util.nvl(rs.getString("fquot")));
                    pktPrpMap.put("cmp",util.nvl(rs.getString("cmp")));
                    pktPrpMap.put("cmp_dis",util.nvl(rs.getString("cmp_dis")));
                    pktPrpMap.put("rap_rte",util.nvl(rs.getString("rap_rte")));
                    pktPrpMap.put("r_dis",util.nvl(rs.getString("r_dis")));
                    pktPrpMap.put("cert_lab", util.nvl(rs.getString("cert_lab")));
                    pktPrpMap.put("amt", util.nvl(rs.getString("amt")));
                    for (int j = 0; j < vwPrpLst.size(); j++) {
                        String prp = (String)vwPrpLst.get(j);

                        String fld = "prp_";
                        if (j < 9)
                            fld = "prp_00" + (j + 1);
                        else
                            fld = "prp_0" + (j + 1);

                        String val = util.nvl(rs.getString(fld));
                        if (prp.toUpperCase().equals("RAP_DIS"))
                            val = util.nvl(rs.getString("r_dis"));
                        if (prp.toUpperCase().equals("RAP_RTE"))
                            val = util.nvl(rs.getString("rap_rte"));
                        if(prp.equals("RTE"))
                            val = util.nvl(rs.getString("rte"));
                        if(prp.equals("KTSVIEW"))
                            val = util.nvl(rs.getString("kts"));
                        if(prp.equals("COMMENT"))
                            val = util.nvl(rs.getString("cmnt"));
                           

                            pktPrpMap.put(prp, val);
                    }

                    pktListMap.put(stkIdn , pktPrpMap);
                    pktStkIdnList.add(stkIdn);
                }
                rs.close(); pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }           
           session.setAttribute("pktStkIdnList", pktStkIdnList);
            return pktListMap;
        }
    
    public HashMap SearchResultGT(HttpServletRequest req,HttpServletResponse res, String flg , ArrayList vwPrpLst) {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            HashMap pktListMap = new HashMap();
            ArrayList pktStkIdnList = new ArrayList();    
            String srchQ = "";
            String srchQ1 = " select sk1, 0 dsp , a.stk_idn , cert_lab , dsp_stt , decode(stt " + 
            ", 'MKPP','color:Green'" + 
            " , 'MKLB','color:Maroon'" + 
            " , 'MKAV', decode(instr(upper(dsp_stt), 'NEW'), 0, ' ', 'color:Blue')" + 
            " , 'MKIS', 'color:Red'" + 
            " , 'SOLD') class ,  ";
            String srchQ2 = " select sk1, 1 dsp , a.stk_idn , cert_lab , '' dsp_stt , '' class , ";
            String dspStk = "stkDspFF";
            String getQuot = "quot rte";
           srchQ += "  cts crtwt, " +
                    //decode(quot, 0, 0, decode(nvl(rap_dis,0), 0, 101, trunc(((quot*100)/greatest(rap_rte,100)) - 100,2))) rr_dis, "+
                    " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis " +
                    " , stk_idn  , vnm, kts_vw kts, cert_lab cert, cert_no, rmk, img, cmp , trunc(((cmp*100)/greatest(rap_rte,1)) - 100,2) cmp_dis ,  to_char(trunc(cts,2),'9990.99') cts, qty , rap_rte , " +
                    getQuot +
                    ", cmnt,stt stt1,  nvl(fquot,0) fquot , flg , to_char(trunc(cts,2) * quot, '99999990.00') amt , " +
                    " decode(nvl(fquot,0),0, 'NA', decode(fquot, quot, 'NA', decode(greatest(fquot, quot),quot, 'UP','DN'))) cmp_flg,srch_id ";

         

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

         
            String rsltQ =
               srchQ1+" "+srchQ + " from gt_srch_rslt a where flg in ("+flg+") union " +srchQ2+ " "+srchQ +"   from gt_srch_multi a " ;
                rsltQ = rsltQ+  " order by sk1";
            
            ArrayList ary = new ArrayList();

            ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            pktListMap = new HashMap();
            try {
                while (rs.next()) {
                    GtPktDtl pktPrpMap = new GtPktDtl();

                    String vnm =util.nvl(rs.getString("vnm"));
                    String cert_No = util.nvl(rs.getString("cert_no"));
                  
                    String stkIdn = util.nvl(rs.getString("stk_idn"));
                    pktPrpMap.setValue("flg", util.nvl(rs.getString("flg")));
                    pktPrpMap.setValue("stt", util.nvl(rs.getString("dsp_stt")));
                    pktPrpMap.setValue("iss_idn",util.nvl(rs.getString("srch_id")));
                    pktPrpMap.setValue("sk1",util.nvl(rs.getString("sk1")));

                    pktPrpMap.setValue("stk_idn",stkIdn);
                    pktPrpMap.setValue("vnm",vnm);
                    pktPrpMap.setValue("qty",util.nvl(rs.getString("qty")));
                    pktPrpMap.setValue("class",util.nvl(rs.getString("class")));
                    pktPrpMap.setValue("cts", util.nvl(rs.getString("cts")));
                    pktPrpMap.setValue("cert_no",cert_No);
                    pktPrpMap.setValue("stt1",util.nvl(rs.getString("stt1")));
                    pktPrpMap.setValue("quot",util.nvl(rs.getString("rte")));
                    pktPrpMap.setValue("fquot",util.nvl(rs.getString("fquot")));
                    pktPrpMap.setValue("cmp",util.nvl(rs.getString("cmp")));
                    pktPrpMap.setValue("cmp_dis",util.nvl(rs.getString("cmp_dis")));
                    pktPrpMap.setValue("rap_rte",util.nvl(rs.getString("rap_rte")));
                    pktPrpMap.setValue("r_dis",util.nvl(rs.getString("r_dis")));
                    pktPrpMap.setValue("cert_lab", util.nvl(rs.getString("cert_lab")));
                    pktPrpMap.setValue("amt", util.nvl(rs.getString("amt")));
                    for (int j = 0; j < vwPrpLst.size(); j++) {
                        String prp = (String)vwPrpLst.get(j);

                        String fld = "prp_";
                        if (j < 9)
                            fld = "prp_00" + (j + 1);
                        else
                            fld = "prp_0" + (j + 1);

                        String val = util.nvl(rs.getString(fld));
                        if (prp.toUpperCase().equals("RAP_DIS"))
                            val = util.nvl(rs.getString("r_dis"));
                        if (prp.toUpperCase().equals("RAP_RTE"))
                            val = util.nvl(rs.getString("rap_rte"));
                        if(prp.equals("RTE"))
                            val = util.nvl(rs.getString("rte"));
                        if(prp.equals("KTSVIEW"))
                            val = util.nvl(rs.getString("kts"));
                        if(prp.equals("COMMENT"))
                            val = util.nvl(rs.getString("cmnt"));
                           

                            pktPrpMap.setValue(prp, val);
                    }

                    pktListMap.put(stkIdn , pktPrpMap);
                    pktStkIdnList.add(stkIdn);
                }
                rs.close(); pst.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }           
           session.setAttribute("pktStkIdnList", pktStkIdnList);
            return pktListMap;
        }
    
    public void getUpDwDtl(HttpServletRequest req){
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
      ArrayList genViewPrp = upDWPrprVw(req,"LAB_UP_DW","LAB_UP_DW");
      ArrayList vwPrpLst=(ArrayList)session.getAttribute("LabViewLst");
//      int ct = db.execCall("refresh vw", "DBMS_MVIEW.REFRESH('MX_LAB_PRC_ISS_MV')", new ArrayList()); 
      ArrayList viewPrp = (ArrayList)session.getAttribute("LabViewLst");
        HashMap dbinfo = info.getDmbsInfoLst();
        String ctval = (String)dbinfo.get("CUT");
        String symval = (String)dbinfo.get("SYM");
        String polval = (String)dbinfo.get("POL");
        String flval = (String)dbinfo.get("FL");
        HashMap colClrMap = new HashMap();
        HashMap colClrPktMap = new HashMap();
        
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("LAB_RESULT");
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
        String plusCom = "";
        pageList= ((ArrayList)pageDtl.get("PLUSMNCOM") == null)?new ArrayList():(ArrayList)pageDtl.get("PLUSMNCOM");
        if(pageList!=null && pageList.size() >0){
        pageDtlMap=(HashMap)pageList.get(0);
        plusCom=util.nvl((String)pageDtlMap.get("dflt_val")); 
        if(plusCom.equals(""))
        plusCom="N";
        }
        
      if(genViewPrp!=null && genViewPrp.size()>0){
      for(int i=0;i<genViewPrp.size();i++){
      String prpVal=util.nvl((String)genViewPrp.get(i));
      int indexPrp = vwPrpLst.indexOf(prpVal)+1;
      if(indexPrp > -1){
      String lbPrp = util.prpsrtcolumn("prp",indexPrp);
      String lbSrt = util.prpsrtcolumn("srt",indexPrp);
      String upDwsqlCol="";
      String upDwsqlColPkt="";

      if(plusCom.equals("N")){
      upDwsqlCol ="select count(*) cnt\n" + 
      ", decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-')) dsc\n" + 
      "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" + 
      "where 1 = 1\n" + 
      "and gt.stk_idn = irp.iss_stk_idn\n" + 
      "and gt.srch_id = irp.iss_id\n" + 
      "and p1.mprp = irp.mprp and p1.val = replace(replace(irp.iss_val, '+', ''), '-', '')\n" + 
      "and decode(replace(replace(gt."+lbPrp+", '+', ''), '-', ''),p1.val,p1.srt,gt."+lbSrt+") =  p2.srt\n"+
      "and p1.mprp in ('"+prpVal+"') and p2.mprp in ('"+prpVal+"') \n" + 
      "GROUP BY decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-'))";
      
          upDwsqlColPkt ="select gt.STK_IDN, p1.mprp \n" + 
          ", decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-')) dsc\n" + 
          "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" + 
          "where 1 = 1\n" + 
          "and gt.stk_idn = irp.iss_stk_idn\n" + 
          "and gt.srch_id = irp.iss_id\n" + 
          "and p1.mprp = irp.mprp and p1.val = replace(replace(irp.iss_val, '+', ''), '-', '')\n" + 
          "and decode(replace(replace(gt."+lbPrp+", '+', ''), '-', ''),p1.val,p1.srt,gt."+lbSrt+") =  p2.srt\n"+
          "and p1.mprp in ('"+prpVal+"') and p2.mprp in ('"+prpVal+"') \n";
      }else{
          upDwsqlCol ="select count(*) cnt\n" + 
          ", decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-')) dsc\n" + 
          "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" + 
          "where 1 = 1\n" + 
          "and gt.stk_idn = irp.iss_stk_idn\n" + 
          "and gt.srch_id = irp.iss_id\n" + 
          "and p1.mprp = irp.mprp and p1.val = irp.iss_val\n" + 
          "and decode(gt."+lbPrp+",p1.val,p1.srt,gt."+lbSrt+") =  p2.srt\n"+
          "and p1.mprp in ('"+prpVal+"') and p2.mprp in ('"+prpVal+"') \n" + 
          "GROUP BY decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-'))";
          
              upDwsqlColPkt ="select gt.STK_IDN, p1.mprp \n" + 
              ", decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-')) dsc\n" + 
              "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" + 
              "where 1 = 1\n" + 
              "and gt.stk_idn = irp.iss_stk_idn\n" + 
              "and gt.srch_id = irp.iss_id\n" + 
              "and p1.mprp = irp.mprp and p1.val = irp.iss_val\n" + 
              "and decode(gt."+lbPrp+",p1.val,p1.srt,gt."+lbSrt+") =  p2.srt\n"+
              "and p1.mprp in ('"+prpVal+"') and p2.mprp in ('"+prpVal+"') \n";
      }
      
      if(prpVal.equals(ctval) || prpVal.equals(symval) || prpVal.equals(polval)) {
          if(plusCom.equals("N")){
                upDwsqlCol ="select count(*) cnt\n" + 
                ", decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-')) dsc\n" + 
                "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" + 
                "where 1 = 1\n" + 
                "and gt.stk_idn = irp.iss_stk_idn\n" + 
                "and gt.srch_id = irp.iss_id\n" + 
                "and p1.mprp = irp.mprp and p1.val = replace(replace(replace(replace(replace(replace(irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" + 
                "and replace(replace(replace(replace(replace(replace(gt."+lbPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')  = p2.val \n" + 
                "and p1.mprp in ('"+prpVal+"') and p2.mprp in ('"+prpVal+"') \n" + 
                "GROUP BY decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-'))"; 
                
          upDwsqlColPkt ="select gt.STK_IDN, p1.mprp \n" + 
          ", decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-')) dsc\n" +
          "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" +
          "where 1 = 1\n" +
          "and gt.stk_idn = irp.iss_stk_idn\n" +
          "and gt.srch_id = irp.iss_id\n" +
          "and p1.mprp = irp.mprp and p1.val = replace(replace(replace(replace(replace(replace(irp.iss_val,'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')\n" +
          "and replace(replace(replace(replace(replace(replace(gt."+lbPrp+",'4',''),'3',''),'2',''),'1',''), '+', ''), '-', '')  = p2.val \n" +
          "and p1.mprp in ('"+prpVal+"') and p2.mprp in ('"+prpVal+"') \n";     
          }else{
              upDwsqlCol ="select count(*) cnt\n" + 
              ", decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-')) dsc\n" + 
              "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" + 
              "where 1 = 1\n" + 
              "and gt.stk_idn = irp.iss_stk_idn\n" + 
              "and gt.srch_id = irp.iss_id\n" + 
              "and p1.mprp = irp.mprp and p1.val = irp.iss_val\n" + 
              "and gt."+lbPrp+"  = p2.val \n" + 
              "and p1.mprp in ('"+prpVal+"') and p2.mprp in ('"+prpVal+"') \n" + 
              "GROUP BY decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-'))"; 
              
              upDwsqlColPkt ="select gt.STK_IDN, p1.mprp \n" +
              ", decode(p1.srt, p2.srt, '=', decode(greatest(p1.srt, p2.srt), p1.srt, '+', '-')) dsc\n" +
              "from gt_srch_rslt gt, iss_rtn_prp irp, prp p1, prp p2\n" +
              "where 1 = 1\n" +
              "and gt.stk_idn = irp.iss_stk_idn\n" +
              "and gt.srch_id = irp.iss_id\n" +
              "and p1.mprp = irp.mprp and p1.val = irp.iss_val\n" +
              "and gt."+lbPrp+"  = p2.val \n" +
              "and p1.mprp in ('"+prpVal+"') and p2.mprp in ('"+prpVal+"') \n";
          }
      }
          ArrayList outLst = db.execSqlLst("UpDwSql", upDwsqlCol, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
            String cnt = util.nvl(rs.getString("cnt"));
            String dsc = util.nvl(rs.getString("dsc"));
            colClrMap.put(prpVal+"_"+dsc, cnt);
            }
            rs.close(); pst.close();
           

            outLst = db.execSqlLst("UpDwSql", upDwsqlColPkt, new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
            String stk_idn = util.nvl(rs.getString("STK_IDN"));
            String dsc = util.nvl(rs.getString("dsc"));
            ArrayList pktIdnLst = (ArrayList)colClrPktMap.get(prpVal+"_"+dsc);
            if(pktIdnLst==null)
                pktIdnLst = new ArrayList();
                
                pktIdnLst.add(stk_idn);
            
            colClrPktMap.put(prpVal+"_"+dsc, pktIdnLst);
            }
            rs.close(); pst.close();
            
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
      }
      }
        gtMgr.setValue("PRPUPDWMAP", colClrPktMap);
        req.setAttribute("colClrUPDWMap", colClrMap);
        
    }
    public ArrayList assortLabCom(HttpServletRequest req,ArrayList itemLst){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());

        try {
            ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp , flg  from rep_prp where mdl = 'ASRT_LAB_COMP' and flg in ('Y') order by rnk ",
                           new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs1 = (ResultSet)outLst.get(1);
            while (rs1.next()) {

            itemLst.add(rs1.getString("prp"));
               
                
            }
            rs1.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }

               
            
        return itemLst;
    }
    public ArrayList upDWPrprVw(HttpServletRequest req , String sname,String mdl){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList genViewPrp = (session.getAttribute(sname) == null)?new ArrayList():(ArrayList)session.getAttribute(sname); 
        try {
            if(genViewPrp.size() == 0) {

                ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = '"+mdl.trim()+"' and flg='Y' order by rnk ",
                               new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {

                    genViewPrp.add(rs.getString("prp"));
                }
                rs.close(); pst.close();
                session.setAttribute(sname, genViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return genViewPrp;
    }
    
    public ArrayList IssRtnComVal(HttpServletRequest req , String mdl){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList genViewPrp = (session.getAttribute(mdl) == null)?new ArrayList():(ArrayList)session.getAttribute(mdl); 
        try {
            if(genViewPrp.size() == 0) {
                ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = '"+mdl.trim()+"' and flg='Y' order by rnk ",
                               new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
                while (rs.next()) {

                    genViewPrp.add(rs.getString("prp"));
                }
                rs.close(); pst.close();
                session.setAttribute(mdl, genViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return genViewPrp;
    }
}
