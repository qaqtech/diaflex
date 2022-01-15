package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;
import ft.com.assort.AssortReturnAction;
import ft.com.assort.AssortReturnForm;
import ft.com.assort.AssortReturnImpl;
import ft.com.assort.AssortReturnInterface;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;

import java.io.OutputStream;

import java.sql.CallableStatement;
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

public class MixAssortRtnAction  extends DispatchAction {
    public MixAssortRtnAction() {
        super();
    }
    
  public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
     MixAssortRtnForm assortForm = (MixAssortRtnForm)form;
 
      
      assortForm.reset();
      String grp = util.nvl(req.getParameter("grp"));
      ArrayList mprcList = getPrc(req,res);
      assortForm.setMprcList(mprcList);
      
      ArrayList empList = getEmp(req,res);
      assortForm.setEmpList(empList);
      assortForm.setValue("grpList",grp);
        return am.findForward("load");
      }
  }
  public ActionForward fecth(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        MixAssortRtnForm assortForm = (MixAssortRtnForm)form;
        util.updAccessLog(req,res,"Assort Return", "fecth");
     
      String mprcIdn = util.nvl((String)assortForm.getValue("mprcIdn"));
      String empId = util.nvl((String)assortForm.getValue("empIdn"));
      String issueId = util.nvl((String)assortForm.getValue("issueId"));
      HashMap prcBeansList = (HashMap)session.getAttribute("mprcBean");
      String grpList = util.nvl((String)assortForm.getValue("grpList"));
      String prcStt = "";
      ArrayList stockList=new ArrayList();
      session.setAttribute("AssortStockList", new ArrayList());
      if(!issueId.equals("") && mprcIdn.equals("0")){
          util.updAccessLog(req,res,"Assort Return", "Issue Id : "+issueId);
          ArrayList ary = new ArrayList();
          String issuStt = " select b.is_stt , b.idn from iss_rtn a , mprc b where a.prc_id = b.idn and a.iss_id= ? ";
          ary.add(issueId);
        ArrayList  rsLst = db.execSqlLst("issueStt", issuStt, ary);
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
          while(rs.next()){
            prcStt = util.nvl(rs.getString("is_stt"));
            mprcIdn = util.nvl(rs.getString("idn"));
              
          }
          rs.close();
          stmt.close();
      }else if(!mprcIdn.equals("0")){
        ArrayList ary = new ArrayList();
        String issuStt = " select b.is_stt  from  mprc b where b.idn = ? ";
        ary.add(mprcIdn);
           ArrayList  rsLst = db.execSqlLst("issueStt", issuStt, ary);
           PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
           ResultSet  rs =(ResultSet)rsLst.get(1);
        while(rs.next()){
          prcStt = util.nvl(rs.getString("is_stt"));
            
        }
        rs.close();
        stmt.close();
         }
      if(!prcStt.equals("")){
      HashMap params = new HashMap();
      params.put("stt", prcStt);
      params.put("empIdn", empId);
      params.put("issueId", issueId);
      params.put("grpList", grpList);
      params.put("mdl", "MIX_VIEW");

      stockList = FecthResult(req,res, params);
      if(stockList.size()>0){
      HashMap totals = GetTotal(req,res);
      req.setAttribute("totalMap", totals);
      }
      }
        HashMap pktCountMap = new HashMap(); 

        String pktCount= "select count(*) cnt , pkt_rt from iss_rtn_dtl a , gt_srch_rslt b\n" + 
        "where a.iss_id= b.srch_id and a.pkt_rt = b.stk_idn GROUP BY pkt_rt ";
        ArrayList  rsLst = db.execSqlLst("pktCount", pktCount, new ArrayList() );
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
          while(rs.next()){
            String cnt = rs.getString("cnt");
            String pkt = rs.getString("pkt_rt");
              pktCountMap.put(pkt, cnt);
          }
          rs.close();
        stmt.close();
          req.setAttribute("pktCountMap", pktCountMap);
        
      req.setAttribute("view", "Y");
      assortForm.setValue("prcId", mprcIdn);
      assortForm.setValue("empId", empId);
      req.setAttribute("prcId", mprcIdn);
      session.setAttribute("AssortStockList", stockList);

       
      return am.findForward("load");
      }
  }
  
    public ActionForward SplitStone(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
       MixAssortRtnForm assortForm = (MixAssortRtnForm)form;
       String stkIdn = req.getParameter("mstkIdn");
       String issId = req.getParameter("issIdn");
      
        ArrayList ary = null;
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk , rln_idn , sk1 ) " + 
        " select  a.pkt_ty, a.idn, a.vnm,  a.dte, b.stt , 'Z' , a.qty , a.cts , b.iss_id , a.tfl3 , b.iss_emp_id , a.sk1 "+
        "   from mstk a , iss_rtn_dtl b where a.idn = b.iss_stk_idn and b.iss_id = ? and b.pkt_rt=?  "+
        " and a.cts > 0   ";
        ary = new ArrayList();
        ary.add(issId);
        ary.add(stkIdn);
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "DP_GT_PRP_UPD(?)";
        ary = new ArrayList();
        ary.add("SPLIT_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        HashMap params = new HashMap();
        params.put("mdl", "MIX_VIEW");
        ArrayList  stockList = SearchResult(req ,res,"","SPLIT_VIEW");
        req.setAttribute("SplitStoneList", stockList);
        return am.findForward("loadPop");

      }
    }
  
    public ActionForward Verify(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          ArrayList ary=null;
       String count = util.nvl(req.getParameter("COUNT"));
        String mstkIdn = req.getParameter("mstkIdn");
        String issId = req.getParameter("issIdn");

        ArrayList vwPrpLst = (ArrayList)session.getAttribute("SPLIT_VIEW");

          if(!count.equals("")){
              int loop = Integer.parseInt(count);
              for(int i=0;i<loop;i++){
                String cts = util.nvl(req.getParameter("CRTWT_"+i));
                String idn = util.nvl(req.getParameter("IDN_"+i));

                  if(!cts.equals("")){
                    int newIdn = 0;
                      if(idn.equals("")){
                    String insMst =
                        "MIX_PKG.GEN_PKT(pStt => ?,pPktRt => ?, pPktTyp => ?,pIdn =>?,pVnm =>?)";
                    ary = new ArrayList();
                    ary.add("MX_AS_IS");
                    ary.add(mstkIdn);
                    ary.add("MIX");
                    
                    ArrayList out = new ArrayList();
                    out.add("I");
                    out.add("V");
                    CallableStatement cst = db.execCall("findMstkId", insMst, ary, out);
                    newIdn = cst.getInt(ary.size()+1);
                        cst.close();
                        cst=null;
                      }else{
                        newIdn = Integer.parseInt(idn);
                      }
                           
                    for(int j=0;j<vwPrpLst.size();j++){
                      String lprp =(String)vwPrpLst.get(j);
                      String val = util.nvl(req.getParameter(lprp+"_"+i));

                    ary = new ArrayList();
                    ary.add(String.valueOf(newIdn));
                    ary.add(lprp);
                    ary.add(val);
                    String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                    int ct = db.execCall("stockUpd",stockUpd, ary);
                    if(!idn.equals("")){
                       if(lprp.equals("CRTWT") || lprp.equals("RTE")){
                       ary = new ArrayList();
                       ary.add(issId);
                       ary.add(String.valueOf(newIdn));
                       ary.add(lprp);
                       ary.add(val);
                       String rtbPktPrp = "MIX_IR_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn => ?, pPrp => ?, pVal => ?)";
                        ct = db.execCall("stockUpd",rtbPktPrp, ary);
                        }
                        
                     }
                    
                    }
                    if(idn.equals("")){

                    ary = new ArrayList();
                    ary.add(issId);
                    ary.add(mstkIdn);
                    ary.add(String.valueOf(newIdn));
                    String issPkt = "MIX_IR_PKG.ISS_PKT(pIssId => ?, pRtPkt => ?, pStkIdn => ?)";
                    int ct = db.execCall("iss Pkt",issPkt, ary);
                    }
                    
                    
                  }
              }
          }
        ary = new ArrayList();
        ary.add(issId);
        ary.add(mstkIdn);
        ArrayList out = new ArrayList();
          out.add("V");
          out.add("V");
        
        String verifyPkt = "MIX_IR_PKG.VERIFY_PKT(pIssId => ?, pRtPkt => ?, pVerified => ?, pMsg => ?)";
        CallableStatement cts= db.execCall("iss Pkt",verifyPkt, ary,out);
        String isVerify = cts.getString(ary.size()+1);
        String pMsg = cts.getString(ary.size()+2);
        ArrayList msgLst = new ArrayList();
          msgLst.add(isVerify);
          msgLst.add(pMsg);
          req.setAttribute("msgLst", msgLst);
        return SplitStone(am, form, req, res);

      }
    }
    
    public ActionForward Return(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        MixAssortRtnForm assortForm = (MixAssortRtnForm)form;
         ArrayList msgLst = new ArrayList();
        ArrayList stockList = (ArrayList)session.getAttribute("AssortStockList");
          for(int i=0;i<stockList.size();i++){
            HashMap pktDtl = (HashMap)stockList.get(i);
            String stk_idn = (String)pktDtl.get("stk_idn");
            String issIdn = (String)pktDtl.get("issIdn");

            String isChecked = util.nvl((String)assortForm.getValue("CHK_"+stk_idn));
              if(isChecked.equals("yes")){
                  ArrayList ary = new ArrayList();
                  ary.add(issIdn);
                  ary.add(stk_idn);
                  ArrayList out = new ArrayList();
                  out.add("V");
                  String rtnPkt = "MIX_IR_PKG.RTN_PKT(pIssId => ?, pRtPkt => ?,pMsg=>?)";
                  CallableStatement cts= db.execCall("return Pkt",rtnPkt, ary,out);
                  String pmsg = cts.getString(ary.size()+1);
                  msgLst.add(pmsg);
              }
            
          }
          req.setAttribute("msgList", msgLst);
        return am.findForward("load");

      }
    }
    public HashMap GetTotal(HttpServletRequest req , HttpServletResponse res){
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
        ary.add("Z");
        ArrayList  rsLst = db.execSqlLst("getTotal", gtTotal, ary);
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            if (rs.next()) {
             gtTotalMap.put("qty", util.nvl(rs.getString("qty")));
             gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return gtTotalMap ;
    }
    
  
    public ArrayList FecthResult(HttpServletRequest req,HttpServletResponse res, HashMap params){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList stockList = new ArrayList();
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
      
        String stt = (String)params.get("stt");
        String issId = (String)params.get("issueId");
        String empIdn = (String)params.get("empIdn");
        String mdl = (String)params.get("mdl");
        ArrayList ary = null;
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk , rln_idn , sk1 ) " + 
        " select  a.pkt_ty, a.idn, a.vnm,  a.dte, b.stt , 'Z' , a.qty , a.cts , b.iss_id , a.tfl3 , b.iss_emp_id , a.sk1 "+
        "   from mstk a , iss_rtn_dtl b where a.idn = b.iss_stk_idn and b.stt in ('IS','LK')  "+
        " and a.stt =? and a.cts > 0  and b.pkt_rt is null  ";
        ary = new ArrayList();
        ary.add(stt);
        if(!issId.equals("")){
            srchRefQ = srchRefQ+" and b.iss_id = ? " ;
            ary.add(issId);
        }
        if(!empIdn.equals("0")){
            
            srchRefQ = srchRefQ+" and b.iss_emp_id = ? " ;
            ary.add(empIdn);
        }
       
        ct = db.execDirUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add("MIX_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        
        stockList = SearchResult(req ,res, "",mdl);
      }
        return stockList;
    }
    
    public ArrayList SearchResult(HttpServletRequest req ,HttpServletResponse res, String vnmLst,String mdl ){
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
       
        ArrayList vwPrpLst = ASPrprViw(req,res,mdl);
        String  srchQ =  " select stk_idn , pkt_ty,stt,  vnm, pkt_dte, stt , qty , cts , srch_id ,rmk , byr.get_nm(rln_idn) emp ";

        

        for (int i = 0; i < vwPrpLst.size(); i++) {
            String fld = "srt_";
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
        ArrayList  rsLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while(rs.next()) {

                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                pktPrpMap.put("emp",  util.nvl(rs.getString("emp")));
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
                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                  pktPrpMap.put("stt",util.nvl(rs.getString("stt")));

                    pktPrpMap.put("issIdn",util.nvl(rs.getString("srch_id")));
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="srt_";
                      if(j < 9)
                              fld="srt_00"+(j+1);
                      else    
                              fld="srt_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                     
                        
                        pktPrpMap.put(prp, val);
                         }
                              
                    pktList.add(pktPrpMap);
                }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        ArrayList list = (ArrayList)req.getAttribute("msgList");
        if(list!=null && list.size()>0){
        }else{
        if(!vnmLst.equals("")){
            vnmLst = util.pktNotFound(vnmLst);
            req.setAttribute("vnmNotFnd", vnmLst);
        }
        }
      }
        return pktList;
    }
    
    public ArrayList ASPrprViw(HttpServletRequest req , HttpServletResponse res,String mdl){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute(mdl);
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
                ArrayList ary = new ArrayList();
                ary.add(mdl);
               
              ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = ? and flg='Y' order by rnk ",
                          ary);
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs1 =(ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                stmt.close();
                session.setAttribute(mdl, asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return asViewPrp;
    }
    
//  
//  public ActionForward Openpop(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
//          throws Exception {
//    HttpSession session = req.getSession(false);
//    InfoMgr info = (InfoMgr)session.getAttribute("info");
//    DBUtil util = new DBUtil();
//    DBMgr db = new DBMgr();
//    String rtnPg="sucess";
//    if(info!=null){  
//    db.setCon(info.getCon());
//    util.setDb(db);
//    util.setInfo(info);
//    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//    util.setLogApplNm(info.getLoApplNm());
//    rtnPg=init(req,res,session,util);
//    }else
//    rtnPg="sessionTO";
//    if(!rtnPg.equals("sucess")){
//        return am.findForward(rtnPg);   
//    }else{
//     AssortReturnForm assortForm = (AssortReturnForm)af;
//      AssortReturnInterface assortInt = new AssortReturnImpl();
//          util.updAccessLog(req,res,"Assort Return", "Openpop");
//    
//      ArrayList prpList=new ArrayList();
//      
//      String prcId = req.getParameter("prcID");
//      String sql="select mprp from prc_prp_alw where prc_idn=? and flg='EDIT' order by srt";
//      ArrayList ary = new ArrayList();
//      ary.add(prcId);
//      ResultSet rs = db.execSql("sql", sql, ary);
//      while(rs.next()){
//        String lprp = rs.getString("mprp");
//         prpList.add(lprp);
//          assortForm.setValue(lprp,"");
//      }
//      rs.close();
//      session.setAttribute("prpList",prpList);
//          util.updAccessLog(req,res,"Assort Return", "prpList :"+prpList.size());
//          util.updAccessLog(req,res,"Assort Return", "End");
//      return am.findForward("multiPrp");
//      }
//  }
//  
// 
//    public ActionForward Return(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
//    HttpSession session = req.getSession(false);
//    InfoMgr info = (InfoMgr)session.getAttribute("info");
//    DBUtil util = new DBUtil();
//    DBMgr db = new DBMgr();
//    String rtnPg="sucess";
//    if(info!=null){  
//    db.setCon(info.getCon());
//    util.setDb(db);
//    util.setInfo(info);
//    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//    util.setLogApplNm(info.getLoApplNm());
//    rtnPg=init(req,res,session,util);
//    }else
//    rtnPg="sessionTO";
//    if(!rtnPg.equals("sucess")){
//        return am.findForward(rtnPg);   
//    }else{
//     AssortReturnForm assortForm = (AssortReturnForm)form;
//     util.updAccessLog(req,res,"Assort Return", "Return");
//     
//      ArrayList RtnMsgList = new ArrayList();
//      String msg = "";
//      int cnt = 0;
//      ArrayList params = null;
//      ArrayList returnPkt = new ArrayList();
//      ResultSet rs = db.execSql("gt fetch", "select srch_id , stk_idn from gt_srch_rslt where flg='Y'", new ArrayList());
//      while(rs.next()){
//          String issId = util.nvl(rs.getString("srch_id"));
//          String stkIdn = util.nvl(rs.getString("stk_idn"));
//          String lStkStt = util.nvl((String)assortForm.getValue("STT_"+stkIdn), "NA");
//          ArrayList RtnMsg = new ArrayList();
//          returnPkt.add(stkIdn);
//          params = new ArrayList();
//          params.add(issId);
//          params.add(stkIdn);
//          params.add("RT");
//          params.add(lStkStt);
//          ArrayList out = new ArrayList();
//          out.add("I");
//          out.add("V");
//          String issuePkt = "ISS_RTN_PKG.RTN_PKT(pIssId => ?, pStkIdn => ? , pStt => ? , pStkStt => ? , pCnt=>? , pMsg => ? )";
//          CallableStatement ct = db.execCall("issue Rtn", issuePkt, params, out);
//          cnt = ct.getInt(5);
//          msg = ct.getString(6);
//          RtnMsg.add(cnt);
//          RtnMsg.add(msg);
//          RtnMsgList.add(RtnMsg);
//          assortForm.setValue(stkIdn,"");
//      }
//      rs.close();
//  if(returnPkt.size()>0)
//      req.setAttribute("msgList",RtnMsgList);
//          util.updAccessLog(req,res,"Assort Return", "End");
//   return fecth(am, form, req, res);
//      }
//  }
  
    public ArrayList getPrc(HttpServletRequest req ,HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList prcList = new ArrayList();
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      
        ArrayList ary = new ArrayList();
        String prcSql = "select idn, prc , in_stt from mprc where  grp ='MIXING' " ;
        ary = new ArrayList();
        ArrayList  rsLst = db.execSqlLst("prcSql", prcSql, ary);
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
          try {
              while (rs.next()) {
                  Mprc mprc = new Mprc();
                 
                  mprc.setMprcId(rs.getString("idn"));
                  mprc.setPrc(rs.getString("prc"));
                  mprc.setIn_stt(rs.getString("in_stt"));
                  prcList.add(mprc);
                 
              }
              rs.close();
              stmt.close();
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
      }
       return prcList;
    }
    
      public ActionForward LockPkt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse response) throws Exception {
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr(); 
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
         response.setContentType("text/xml"); 
         response.setHeader("Cache-Control", "no-cache"); 
         
          String stkIdn = req.getParameter("stkIdn");
         String issId = req.getParameter("issId");
         
         String updateIssDtl = "update iss_rtn_dtl set stt=? where iss_stk_idn=? and iss_id=?";
         ArrayList ary = new ArrayList();
         ary.add("LK");
         ary.add(stkIdn);
         ary.add(issId);
         int upd = db.execCall("updateissDtl", updateIssDtl, ary);
         if(upd>0){
           response.setContentType("text/xml"); 
           response.setHeader("Cache-Control", "no-cache"); 
           response.getWriter().write("<message>yes</message>");
         }
         else{
           response.setContentType("text/xml"); 
           response.setHeader("Cache-Control", "no-cache"); 
           response.getWriter().write("<message>No</message>");
         }

         
          return null;
       }
      
    public ActionForward unLockPkt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse response) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
       response.setContentType("text/xml"); 
       response.setHeader("Cache-Control", "no-cache"); 
       
        String stkIdn = req.getParameter("stkIdn");
       String issId = req.getParameter("issId");
       
       String updateIssDtl = "update iss_rtn_dtl set stt=? where iss_stk_idn=? and iss_id=?";
       ArrayList ary = new ArrayList();
       ary.add("IS");
       ary.add(stkIdn);
       ary.add(issId);
       int upd = db.execCall("updateissDtl", updateIssDtl, ary);
       if(upd>0){
         response.setContentType("text/xml"); 
         response.setHeader("Cache-Control", "no-cache"); 
         response.getWriter().write("<message>yes</message>");
       }
       else{
         response.setContentType("text/xml"); 
         response.setHeader("Cache-Control", "no-cache"); 
         response.getWriter().write("<message>No</message>");
       }

       
        return null;
     }
    
    public ArrayList getEmp(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList empList = new ArrayList();
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = new ArrayList();
       
        String empSql = "select nme_idn, nme from nme_v where typ = 'EMPLOYEE' order by nme";
        ArrayList  rsLst = db.execSqlLst("empSql", empSql, ary);
        PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
        ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
            while (rs.next()) {
                MNme emp = new MNme();
                emp.setEmp_idn(rs.getString("nme_idn"));
                emp.setEmp_nme(rs.getString("nme"));
                empList.add(emp);
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return empList;
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
            util.updAccessLog(req,res,"Assort Issue Action", "Unauthorized Access");
            else
        util.updAccessLog(req,res,"Assort Issue Action", "init");
        }
        }
        return rtnPg;
        }
  }

