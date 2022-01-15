package ft.com.assorthk;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.Mprc;
import ft.com.fileupload.FileUploadForm;
import ft.com.fileupload.FileUploadInterface;

import java.sql.CallableStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PendingRcvAction extends DispatchAction {
  
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
            util.updAccessLog(req,res,"Pending Rcv", "load start");
        PendingRcvForm assortForm = (PendingRcvForm)form;
        assortForm.reset();
        
        /*ArrayList mprcList = assortInt.getPrc(req);
        assortForm.setMprcList(mprcList);
        */
        String prcid = "";
        if(req.getParameter("prc_id") != null)
        {
            prcid = req.getParameter("prc_id");
        }
        else
        {
            prcid = req.getAttribute("prcId").toString();
        }
        req.setAttribute("prcId", prcid);
        //req.setAttribute("prcid", prcid);
        
        /*ArrayList empList = assortInt.getEmp(req);
        assortForm.setEmpList(empList);
        */
        
        //load the form as per the prc id
        
        /*ArrayList assortSrchList = assortInt.ASGenricSrch(req);
        info.setGncPrpLst(assortSrchList);*/
        util.updAccessLog(req,res,"Pending Rcv", "load end");
      return am.findForward("load");
        }
    }
    
    public ActionForward fetch(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Pending Rcv", "fetch start");
        PendingRcvForm assortForm = (PendingRcvForm)form;
        AssortReturnInterface  assortInt = new AssortReturnImpl();
        String mprcIdn = util.nvl((String)assortForm.getValue("mprcIdn"));
        String stoneId = util.nvl((String)assortForm.getValue("vnmLst"));
        String stktyp = util.nvl((String)assortForm.getValue("typ"));
        String dept = util.nvl((String)assortForm.getValue("dept"));
        String memoidnLst = util.nvl((String)assortForm.getValue("memoidn"));
        
        //get the idn of the logged in employee
        String empId = info.getDfNmeIdn();
        //String empId = util.nvl((String)assortForm.getValue("empIdn"));
        
        //fetch the status as per process id
        /*String sttquery = "select is_stt from mprc where idn = '" + mprcIdn + "' ";
        ResultSet rs = db.execSql("search Result", sttquery, new ArrayList());
        
        String stt = "";
        if(rs.next()) {
          stt = rs.getString("is_stt");
        }
        */
        //fetch the records as per selection
        //String delQ = " Delete from gt_srch_rslt where flg in ('AV')";
        String delQ = " Delete from gt_srch_rslt";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        
        String srchRefQ = "";
        ArrayList ary = new ArrayList();
        String vnm = "";
        if(!memoidnLst.equals("")){
            memoidnLst=util.getVnm(memoidnLst);
        }
//        if(stoneId.length()>1) {
//          
//          vnm = util.getVnm(stoneId);
//          srchRefQ = " Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , srch_id , rmk ) " + 
//            " select  b.pkt_ty, b.idn, b.vnm,  b.dte, b.stt , 'AV' , b.qty , b.cts , b.sk1 , a.iss_id , b.tfl3  "+
//            "     from mstk b, iss_rtn_dtl a  " +
//            " where ( b.vnm in ("+ vnm +") " +
//              " or b.tfl3 in ("+ vnm +")) " +
//              " and b.idn = a.iss_stk_idn and a.stt='IS' ";
//          
//        }
        if(stktyp.equalsIgnoreCase("Tr_MX"))
        {
          srchRefQ =
              "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , srch_id , rmk ) " + 
              " select  b.pkt_ty, b.idn, b.vnm,  b.dte, b.stt , 'AV' , b.qty , b.cts , b.sk1 , a.iss_id , b.tfl3  "+
              "     from mstk b, stk_dtl c,stk_dtl d, iss_rtn_dtl a "+
              " where b.idn = c.mstk_idn and b.idn = d.mstk_idn and b.idn = a.iss_stk_idn and b.stt = 'Tr_MX'  " +
              " and c.mprp = 'DEPT' and c.val = '96-UP-GIA' " +
              " and b.cts > 0  and c.grp=1 and d.grp=1" +
              " and a.stt='IS' ";
          if(stoneId.length()>0){
              vnm=util.getVnm(stoneId);
              srchRefQ=srchRefQ+ " and b.vnm in ("+ vnm +") ";
          }
            if(memoidnLst.equals("")){ 
               srchRefQ=srchRefQ+ " and d.mprp = 'MEMO' and d.txt = d.txt ";
            }else{
              srchRefQ=srchRefQ+" and d.mprp = 'MEMO' and d.txt in("+memoidnLst+") ";
            }
          
        }
        else 
        {
          srchRefQ =
              "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , srch_id , rmk ) " + 
              " select  b.pkt_ty, b.idn, b.vnm,  b.dte, b.stt , 'AV' , b.qty , b.cts , b.sk1 , a.iss_id , b.tfl3  "+
              "     from mstk b, stk_dtl c,stk_dtl d,stk_dtl e, iss_rtn_dtl a  "+
              " where b.stt ='MF_FL_IS' and c.mprp = 'MFG_TYP' and c.val = ? " ;
            if(stoneId.length()>0){
                vnm=util.getVnm(stoneId);
                srchRefQ=srchRefQ+ " and b.vnm in ("+ vnm +") ";
            }
              ary.add(stktyp);
            if(dept.equals("ALL")){ 
               srchRefQ=srchRefQ+ " and d.mprp = 'DEPT' and d.val = d.val ";
            }else{
              srchRefQ=srchRefQ+" and d.mprp = 'DEPT' and d.val = ? ";
              ary.add(dept);  
            }
            if(memoidnLst.equals("")){ 
               srchRefQ=srchRefQ+ " and e.mprp = 'MEMO' and e.txt = e.txt ";
            }else{
              srchRefQ=srchRefQ+" and e.mprp = 'MEMO' and e.txt in("+memoidnLst+") ";
            }
              srchRefQ=srchRefQ+" and b.cts > 0  and c.grp=1 and d.grp=1 and e.grp=1 and b.idn = c.mstk_idn and b.idn = d.mstk_idn and b.idn = e.mstk_idn and b.idn = a.iss_stk_idn and a.stt='IS' ";      
          
        }
        
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary); 
        
        //prepare the gt_srch_rstl table
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("PNDTORC_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        ArrayList stockList = new ArrayList();
        
        stockList = SearchResult(req , vnm);
        
        /*HashMap prcBeansList = (HashMap)session.getAttribute("mprcBean");
        Mprc mprcDto = (Mprc)prcBeansList.get(mprcIdn);
        String prcStt = mprcDto.getIn_stt();
        
        String prcStt = "MF_FL";
        HashMap params = new HashMap();
        params.put("stt", prcStt);
        params.put("vnm",stoneId);
        params.put("empIdn", empId);
        params.put("mprcIdn", mprcIdn);
        ArrayList stockList = assortInt.FecthResult(req, params);
        if(stockList.size()>0){
        HashMap totals = assortInt.GetTotal(req);
        req.setAttribute("totalMap", totals);
        }
        req.setAttribute("view", "Y");
        assortForm.setValue("prcId", mprcIdn);
        assortForm.setValue("empId", empId);*/
        if(stockList.size()>0){
          HashMap totals = assortInt.GetTotal(req);
          ASPrprViw(req);
          req.setAttribute("totalMap", totals);
        }
        
        
        assortForm.setValue("prcId", mprcIdn);
        req.setAttribute("prcId", mprcIdn);
        req.setAttribute("view", "Y");
        session.setAttribute("StockList", stockList);
        req.setAttribute("stktyp", stktyp);
        
            util.updAccessLog(req,res,"Pending Rcv", "fetch end");
        return am.findForward("load");
        }
    }
    public ActionForward Issue(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Pending Rcv", "Issue start");
        PendingRcvForm assortForm = (PendingRcvForm)form;
        ArrayList params = null;
        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
        ArrayList RtnMsgList = new ArrayList();
        ArrayList returnPkt = new ArrayList();
        for(int i=0;i< stockList.size();i++)
        {
          HashMap stockPkt = (HashMap)stockList.get(i);
          String stkIdn = (String)stockPkt.get("stk_idn");
          String issid = (String)stockPkt.get("issIdn");
          String isChecked = util.nvl((String)assortForm.getValue(stkIdn));
          String lStkStt = util.nvl((String)assortForm.getValue("STT_"+stkIdn), "NA");
//          returnPkt.add(stkIdn);
          String msg = "";
          int cnt = 1;
          if(isChecked.equals("yes"))
          {   
              returnPkt.add(stkIdn);
              ArrayList RtnMsg = new ArrayList();
              params = new ArrayList();
              params.add(issid);
              params.add(stkIdn);
              params.add("RT");
              params.add(lStkStt);
              ArrayList out = new ArrayList();
              out.add("I");
              out.add("V");
              String issuePkt = "ISS_RTN_PKG.RTN_PKT(pIssId => ?, pStkIdn => ? , pStt => ? , pStkStt => ? , pCnt=>? , pMsg => ? )";
              CallableStatement ct = db.execCall("issue Rtn", issuePkt, params, out);
              cnt = ct.getInt(5);
              msg = ct.getString(6);
              RtnMsg.add(cnt);
              RtnMsg.add(msg);
              RtnMsgList.add(RtnMsg);
              ct.close();
              params = new ArrayList();
              params.add(stkIdn);
              int upGt = db.execUpd("update gt", "update gt_srch_rslt set flg='RT' where stk_idn = ? ", params);
          }
          }
            
        if(returnPkt.size()>0){
            int ct = db.execCall("Pro Single To Mix", "iss_rtn_pkg.gen_sngl_to_mix", new ArrayList());
//             ct = db.execCall("merge Mix", "merge_mix", new ArrayList());
             ct = db.execCall("merge Mix", "Merge_Mix_Post_Verification", new ArrayList());
          req.setAttribute("msgList",RtnMsgList);
        }
            util.updAccessLog(req,res,"Pending Rcv", "Issue end");
         return am.findForward("loadPending");
        }
    }
    
    public ArrayList SearchResult(HttpServletRequest req , String vnmLst ){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg="sucess";
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList pktList = new ArrayList();
        ArrayList vwPrpLst = ASPrprViw(req);
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , srch_id ,rmk  ";

        

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
        ary.add("AV");
        ResultSet rs = db.execSql("search Result", rsltQ, ary);
        try {
            while(rs.next()) {

                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
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
                    pktPrpMap.put("issIdn",util.nvl(rs.getString("srch_id")));
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      if(prp.equals("CRTWT"))
                          fld="cts";
                      String val = util.nvl(rs.getString(fld)) ;
                     
                        
                        pktPrpMap.put(prp, val);
                         }
                              
                    pktList.add(pktPrpMap);
                }
            rs.close();
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
    public ArrayList ASPrprViw(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        String rtnPg="sucess";
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("pendingViewLst");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
                ResultSet rs1 =
                    db.execSql(" Vw Lst ", "Select prp  from rep_prp where mdl = 'PNDTORC_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                session.setAttribute("pendingViewLst", asViewPrp);
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
                util.updAccessLog(req,res,"Pending Rcv", "init");
            }
            }
            return rtnPg;
            }
    public PendingRcvAction() {
        super();
    }
}
