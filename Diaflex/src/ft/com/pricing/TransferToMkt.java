package ft.com.pricing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.LabComparisionExcel;
import ft.com.MailAction;
import ft.com.dao.labDet;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.inward.TransferToMktSHForm;
import ft.com.marketing.SearchQuery;

import ft.com.marketing.StockPrpUpdForm;

import java.io.IOException;

import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class TransferToMkt extends DispatchAction {
  
    public TransferToMkt() {
        super();
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
          util.updAccessLog(req,res,"Assort Issue Action", "init");
          }
          }
          return rtnPg;
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
        TransferToMktForm form = (TransferToMktForm)af;
          GenericInterface genericInt = new GenericImpl();
        form.resetAll();
            ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_TFMKTSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_TFMKTSrch");
        info.setGncPrpLst(assortSrchList);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("TRANS_TO_MKT");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("TRANS_TO_MKT");
        allPageDtl.put("TRANS_TO_MKT",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        int accessidn=util.updAccessLog(req,res,"Transfer to Marketing", "Transfer to Marketing");
        req.setAttribute("accessidn", String.valueOf(accessidn));
          return am.findForward("load");
        }
     
    }
    public ActionForward view(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"Transfer to Marketing", "view");
        GenericInterface genericInt = new GenericImpl();
          SearchQuery srchQuery = new SearchQuery();
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        boolean isGencSrch = false;
        HashMap pktList = new HashMap();
        ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_TFMKTSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_TFMKTSrch");
        info.setGncPrpLst(genricSrchLst);
        TransferToMktForm form = (TransferToMktForm)af;
        ArrayList repMemoList = getMemoRtn(req);
        String vnm = util.nvl((String)form.getValue("vnmLst"));
        String seq = util.nvl((String)form.getValue("seq"));
        String Stt = util.nvl((String)form.getValue("Stt"));
        String view = util.nvl(form.getView());
        ArrayList parms=new ArrayList();
        parms.add(Stt);
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        if(view.length() > 0){
            if(vnm.equals("") && seq.equals("")){
               
                HashMap paramsMap = new HashMap();
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
                paramsMap.put("stt", Stt);
                paramsMap.put("mdl", "TFMKT_VW");
                isGencSrch = true; 
                util.genericSrch(paramsMap);
                pktList = srchQuery.SearchResult(req,res,"'Z'", repMemoList);
            }
        }
        if(!isGencSrch){
            if(Stt.equals("TFMKT")){
                Stt="AS_PRC,LB_CF";
            }
            if(Stt.equals(""))
                Stt="AS_PRC";
            Stt = util.getVnm(Stt);
        ArrayList params = new ArrayList();
        String srchRefQ = 
        "    Insert into gt_srch_rslt ( srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis ) " + 
        "     select  1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt, cmp , rap_rte " +
        "     , cert_lab, cert_no, 'Z' flg, sk1, nvl(upr, cmp) " + 
        ", decode(greatest(nvl(rap_rte,1), 1), 1, null, trunc((nvl(upr,cmp)/rap_rte*100) - 100, 2)) dis " + 
        "    from mstk b " +
        "     where stt in ("+Stt+") " + 
        "    and pkt_ty = 'NR'" ;
       
         if(!seq.equals("")){
              srchRefQ = 
              "    Insert into gt_srch_rslt ( srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis ) " + 
              "     select  1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt, cmp , rap_rte " +
              "     , cert_lab, cert_no, 'Z' flg, sk1, nvl(upr, cmp) " + 
              ", decode(greatest(nvl(rap_rte,1), 1), 1, null, trunc((nvl(upr,cmp)/rap_rte*100) - 100, 2)) dis " + 
              "     from pri_batch a , mstk b  "+
              " where a.idn = b.idn and a.pri_seq = ? and  stt in ("+Stt+") " + 
              "   and pkt_ty = 'NR'" ;
              
              params.add(seq);
          }
         
            if(!view.equals("")){
            if(!vnm.equals(""))
                vnm = util.getVnm(vnm);
            if(!vnm.equals(""))
                srchRefQ = srchRefQ +" and ( b.vnm in ("+vnm+") or b.tfl3 in ("+vnm+")) " ;
            }
         ct = db.execUpd(" Srch Ref ", srchRefQ, params);
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        params = new ArrayList();
        params.add("TFMKT_VW");
        ct = db.execCall(" Srch Prp ", pktPrp, params);
        ct = db.execUpd("updte gt","update gt_srch_rslt a set (cmp, quot) = (select cmp, upr from mstk b where a.stk_idn = b.idn)", new ArrayList());
        
        req.setAttribute("vnmLst", vnm);
        pktList = srchQuery.SearchResult(req,res,"'Z'", repMemoList);
        
        
        }
        if(pktList!=null && pktList.size() >0){
            HashMap totals = srchQuery.GetTotal(req,res,"Z");
            req.setAttribute("totalMap", totals);
        }
            
        ArrayList tfrList = new ArrayList();
        ArrayList tfrListLb = new ArrayList();
        labDet tfrDtl = new labDet();
        tfrDtl.setLabDesc("NONE");
        tfrDtl.setLabVal("NONE");
        tfrListLb.add(tfrDtl);        
        
        String tfrMktlb ="select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and a.chr_to='LB_CF' and " +
                   " b.mdl = 'TFMKT' and b.nme_rule = 'TFMKT' and a.til_dte is null order by a.srt_fr" ;
                 
          ArrayList outLst = db.execSqlLst("tfrMktlb", tfrMktlb, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
           tfrDtl = new labDet();
          tfrDtl.setLabDesc(rs.getString("dsc"));
          tfrDtl.setLabVal(rs.getString("chr_fr"));
          tfrListLb.add(tfrDtl);
        }
        rs.close();
          pst.close();
        form.setTfrSttPRIList(tfrListLb);
      
        if(util.nvl((String)form.getValue("Stt")).equals("TFMKT") || view.equals("")) { 
        parms=new ArrayList();    
        parms.add("AS_PRC"); 
        }
        String tfrMkt ="select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and a.chr_to=? and" + 
                       " b.mdl = 'TFMKT' and b.nme_rule = 'TFMKT' and a.til_dte is null order by a.srt_fr ";
           outLst = db.execSqlLst("tfrMkt", tfrMkt, parms);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        
         tfrDtl = new labDet();
        tfrDtl.setLabDesc("NONE");
        tfrDtl.setLabVal("NONE");
        tfrList.add(tfrDtl);
        while(rs.next()){
             tfrDtl = new labDet();
            tfrDtl.setLabDesc(rs.getString("dsc"));
            tfrDtl.setLabVal(rs.getString("chr_fr"));
            tfrList.add(tfrDtl);
        }
        form.setTfrSttList(tfrList);
        rs.close();
          pst.close();
        req.setAttribute("pktList", pktList);
        req.setAttribute("view", "Y");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("TRANS_TO_MKT");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("TRANS_TO_MKT");
        allPageDtl.put("TRANS_TO_MKT",pageDtl);
        }
        info.setPageDetails(allPageDtl);
          int accessidn=util.updAccessLog(req,res,"Transfer to Marketing", "view end");
          req.setAttribute("accessidn", String.valueOf(accessidn));
          return am.findForward("load");
        }
      
      
    }
    
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"Transfer to Marketing", "save");
        String typ = req.getParameter("typ");
        ArrayList out = new ArrayList();
        out.add("I");
        int jobCnt = 5;
        String delQ = " Delete from gt_pkt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ArrayList reprcParams = new ArrayList();
        String insQ = " insert into gt_pkt(mstk_idn) select stk_idn from gt_srch_rslt where flg='S'  ";
        ArrayList params = new ArrayList();
        ct = db.execUpd(" reprc memo", insQ, params);
        if(typ.equals("lab")){
         ct = db.execCall("lab", "DP_LAB_RTN_PRP_UPD", new ArrayList());
         if(ct>0)
             req.setAttribute("msg", "Process done Successfully....");
         else
             req.setAttribute("msg", "There is some error in Process.");
         return am.findForward("load");
        }else{
        String reprc = "reprc(num_job => ?, lstt1 => ?, lstt2 => ?,lSeq=> ? )";
        reprcParams.add(String.valueOf(jobCnt));
        reprcParams.add("AS_PRC");
        reprcParams.add("FORM");
        CallableStatement cnt = db.execCall(" reprc : ",reprc, reprcParams,out );
        int  lseq = cnt.getInt(reprcParams.size()+1);
        req.setAttribute("seq",String.valueOf(lseq));
        getstt(req ,res, String.valueOf(lseq));
          int accessidn=util.updAccessLog(req,res,"Transfer to Marketing", "Transfer to Marketing");
          req.setAttribute("accessidn", String.valueOf(accessidn));
      
        }
          util.updAccessLog(req,res,"Transfer to Marketing", "save end");
          return am.findForward("loadPrc");
        }
    
    }
    
   public ActionForward memo(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"Transfer to Marketing", "memo");
        TransferToMktForm form = (TransferToMktForm)af;
        int empId = 0;
        String empIdqry="select nme_idn from nme_v where typ='EMPLOYEE'";
          ArrayList outLst = db.execSqlLst("empId", empIdqry, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        if(rs.next()) {
          empId=rs.getInt(1);
        }
        rs.close();
          pst.close();
        int prcid=0;
           outLst = db.execSqlLst("mprcId", "select idn from mprc where in_stt='AS_PRC' and grp <> 'ODD' and stt='A'", new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        if(rs.next()) {
          prcid = rs.getInt(1);
        }  
        rs.close();
          pst.close();
        int issueIdn = 0;
        ArrayList params = new ArrayList();
        String gtFetch = " select stk_idn , cts , qty from gt_srch_rslt where flg='S'";
          outLst = db.execSqlLst("gtSrch", gtFetch ,new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        String stkIdn = util.nvl(rs.getString("stk_idn"));
        String qty = util.nvl(rs.getString("qty"));
        String cts = util.nvl(rs.getString("cts"));
        if(issueIdn==0){
            params = new ArrayList();
            params.add(Integer.toString(prcid));
            params.add(Integer.toString(empId));
            ArrayList out = new ArrayList();
            out.add("I");
            CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", params ,out);
            issueIdn = cst.getInt(3);
          cst.close();
          cst=null;
        }
        params = new ArrayList();
        params.add(String.valueOf(issueIdn));
        params.add(stkIdn);
        params.add(cts);
        params.add(qty);
        params.add("RT");
        String issuePkt = "ISS_RTN_PKG.ISS_PKT(pIssId => ?, pStkIdn => ? , pCts => ?,  pQty => ? , pStt => ?)";
        int ct = db.execCall("issuePkt", issuePkt, params);
        }
        rs.close();
          pst.close();
        if(issueIdn>0){
           req.setAttribute("issueId",String.valueOf(issueIdn));    
        }
          int accessidn=util.updAccessLog(req,res,"Transfer to Marketing", "memo end");
          req.setAttribute("accessidn", String.valueOf(accessidn));
          return am.findForward("IssueReport");
        }
     
    }
    public ActionForward pktPrint(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"Transfer to Marketing", "pkt print");
        TransferToMktForm form = (TransferToMktForm)af;
              String seq = req.getParameter("seq");
               String delQ = " Delete from gt_pkt ";
               int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
              ArrayList ary = new ArrayList();
              HashMap dbinfo = info.getDmbsInfoLst();
              String cnt = (String)dbinfo.get("CNT");
              String webUrl = (String)dbinfo.get("REP_URL");
              String reqUrl = (String)dbinfo.get("HOME_DIR");
              String repPath = (String)dbinfo.get("REP_PATH");
              String repNme = "price_rep.jsp&p_seq="+seq;
              String typ = req.getParameter("typ");
              if(typ.equals("PNT"))
                  repNme = "pktprint_prc.rdf";
              if(typ.equals("PUR"))
                 repNme = "PUrchase_rep.jsp&p_seq="+seq;
              String usr = "";
              int mkt_prc = 0;
            
             
          ArrayList outLst = db.execSqlLst("mkt_prc", "select  seq_mkt_prc.nextval from dual ", new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
              if(rs.next())
                  mkt_prc = rs.getInt(1);
               rs.close();
             pst.close();
              String insertPrc = " insert into mkt_prc(prc_idn ,mstk_idn ,rep_dte) select ?, idn, sysdate from" +
                  " pri_batch where pri_seq = ? ";
              ary.add(String.valueOf(mkt_prc));
              ary.add(seq);
               ct = db.execUpd("insert mkt_prc", insertPrc, ary);
              if(ct>0){
                 String url = repPath+"/reports/rwservlet?"+cnt+"&report="+reqUrl+"\\reports\\"+repNme ;
                  res.sendRedirect(url);    
              }
          return null;
        }
     
    }
  public ActionForward transMkt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"Transfer to Marketing", "trans mkt");
              
              int AVCount = 0;
              int PPCount = 0;
              int LBCount =0;
              int otherCount=0;
              String errorMsg="";
             ArrayList ary = null;
            TransferToMktForm form = (TransferToMktForm)af;
            String updateMstk = "";
            String tranMkt = util.nvl((String)form.getValue("save"));
            String appyLab = util.nvl((String)form.getValue("saveLab"));
            String saveChg = util.nvl((String)form.getValue("saveChg"));
            String calculPro = util.nvl((String)form.getValue("calPro"));
            String gtFetch = "select stk_idn , vnm , cts , qty , cert_lab from gt_srch_rslt where flg='S' ";
            int ct =1;
          ArrayList outLst = db.execSqlLst("getFecth", gtFetch, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            if(!calculPro.equals("")){
                while(rs.next()){
                    String stk_idn = rs.getString(1);
                    ary = new ArrayList();
                    ary.add(stk_idn);
                    ct = db.execCall("DP_BLK_UPD_PROP", "DP_BLK_UPD_PROP(pIdn => ? )", ary);
                }
                rs.close();
                pst.close();
                if(ct > 0)
                    req.setAttribute("msg", "Changes done successfully..");
            }else{
                int empId = 0;
                String empIdqry="select nme_idn from nme_v where typ='EMPLOYEE'";
              ArrayList outLst1 = db.execSqlLst("empId", empIdqry, new ArrayList());
              PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
              ResultSet rs1 = (ResultSet)outLst1.get(1);
                if(rs1.next()) {
                  empId=rs1.getInt(1);
                }
                rs1.close();
                pst1.close();
                
                int prcid=0;
               outLst1 = db.execSqlLst("mprcId", "select idn from mprc where in_stt='AS_PRC' and grp <> 'ODD' and stt='A'", new ArrayList());
               pst1 = (PreparedStatement)outLst1.get(0);
                rs1 = (ResultSet)outLst1.get(1);
                if(rs1.next()) {
                  prcid = rs1.getInt(1);
                }  
                rs1.close();
                pst1.close();
                ary = new ArrayList();
                ary.add(Integer.toString(prcid));
                ary.add(Integer.toString(empId));
                ArrayList out = new ArrayList();
                out.add("I");
                CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", ary ,out);
                int issueIdn = cst.getInt(3);
              cst.close();
              cst=null;
             while(rs.next()){
                
                String stk_idn = rs.getString("stk_idn");
                String vnm = rs.getString("vnm");
                String certLab = rs.getString("cert_lab");
                 String cts = rs.getString("cts");
                 String qty = rs.getString("qty");
                String sttVal = util.nvl((String)form.getValue("STT_"+stk_idn));
                String mnlVal = util.nvl((String)form.getValue("mnl_"+stk_idn));
                String diffVal = util.nvl((String)form.getValue("DAMT_"+stk_idn));
                    if(!sttVal.equals("")){
                        ct=0;
                    ary = new ArrayList();
                    ary.add(stk_idn);
                    ary.add(sttVal);
                    ary.add(mnlVal);
                    ary.add(diffVal);
                    ct = db.execCall(" Trf ", "iss_rtn_pkg.TRF_TO_MKTG(pStkIdn => ?, pStt => ?, pUpr => ?, pDiff=> ?)", ary);
                    }
                     if(ct>0){
                         if(sttVal.equals("MKAV"))
                             AVCount++;
                         if(sttVal.equals("MKPP"))
                            PPCount++;
                          if(sttVal.equals("MKLB"))
                            LBCount++;
                         if(!sttVal.equals("NONE")){
                             otherCount++;
                             ary = new ArrayList();
                             ary.add(String.valueOf(issueIdn));
                             ary.add(stk_idn);
                             ary.add(cts);
                             ary.add(qty);
                             ary.add("RT");
                             String issuePkt = "ISS_RTN_PKG.ISS_PKT(pIssId => ?, pStkIdn => ? , pCts => ?,  pQty => ? , pStt => ?)";
                              ct = db.execCall("issuePkt", issuePkt, ary);
                         }
                      }else{
                             errorMsg = errorMsg+ vnm+"," ;
                        }
                                     
                
                }
                rs.close();
                pst.close();
            if(AVCount!=0 || PPCount!=0 || LBCount!=0 || otherCount!=0){
                ary = new ArrayList();
                String msg = "Selected stones get Tranfer to Marketing :- ";
                String tffLog = "insert into prc_trf_log(prc_trf_log_idn, frm_stt, to_stt, mdl, unm, trf_dte, trf_cnt) " + 
                "  values ( PRC_TRF_LOG_SEQ.nextval, 'AS_PRC', ? , 'TRF_TO_MKTG', ? , sysdate, ? ) ";
                if(AVCount!=0){
                   msg = msg+" Available : "+AVCount+" , ";
                       ary = new ArrayList();
                       ary.add("MKAV");
                       ary.add(info.getByrNm());
                       ary.add(String.valueOf(AVCount));
                      ct = db.execUpd("insertPrc", tffLog, ary);
                   }
                if(PPCount!=0){
                       msg = msg+" Premium Plus : "+PPCount+" , ";
                       ary = new ArrayList();
                       ary.add("MKPP");
                       ary.add(info.getByrNm());
                       ary.add(String.valueOf(PPCount));
                       ct = db.execUpd("insertPrc", tffLog, ary);
                   } if(LBCount!=0){
                      msg = msg+" Look And Bid : "+LBCount ;
                       ary = new ArrayList();
                       ary.add("MKLB");
                       ary.add(info.getByrNm());
                       ary.add(String.valueOf(LBCount));
                       ct = db.execUpd("insertPrc", tffLog, ary);
                   }
                     if(otherCount!=0){
                         msg = msg+"  : "+otherCount ;
                          ary = new ArrayList();
                          ary.add("OTHER");
                          ary.add(info.getByrNm());
                          ary.add(String.valueOf(otherCount));
                          ct = db.execUpd("insertPrc", tffLog, ary);
                     }
              
                req.setAttribute("msg", msg);
                HashMap params = new HashMap();
                params.put("issueId",issueIdn);
                MailAction mailObj = new MailAction();
                mailObj.TRF_TO_MKTG_MAIL(params, req , res);
                
            }
            if(!errorMsg.equals("")){
                req.setAttribute("errormsg", errorMsg+" Price not found");
                
            }}
          int accessidn=util.updAccessLog(req,res,"Transfer to Marketing", "trans mkt");
          req.setAttribute("accessidn", String.valueOf(accessidn));
          return am.findForward("load");
        }
     
    }
    public ActionForward transMktold(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        TransferToMktForm form = (TransferToMktForm)af;
              
              int AVCount = 0;
              int PPCount = 0;
              int LBCount = 0;
              String errorMsg="";
             ArrayList ary = null;
         
            String updateMstk = "";
            String tranMkt = util.nvl((String)form.getValue("save"));
            String appyLab = util.nvl((String)form.getValue("saveLab"));
            String saveChg = util.nvl((String)form.getValue("saveChg"));
            
            String gtFetch = "select stk_idn , vnm , cert_lab from gt_srch_rslt ";
            int ct =0;
          ArrayList outLst = db.execSqlLst("getFecth", gtFetch, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                
                String stk_idn = rs.getString(1);
                String vnm = rs.getString(2);
                String certLab = rs.getString(3);
                String sttVal = util.nvl((String)form.getValue("STT_"+stk_idn));
                String mnlVal = util.nvl((String)form.getValue("mnl_"+stk_idn));
                String diffVal = util.nvl((String)form.getValue("DAMT_"+stk_idn));
             
                if(!tranMkt.equals("") || !saveChg.equals("")){
                if(!sttVal.equals("NONE")){
                   updateMstk = "update mstk  set stt = ? , dte = sysdate where stt = 'AS_PRC' and (cmp is not null or upr is not null) and idn = ? ";
                    ary = new ArrayList();
                    ary.add(sttVal);
                    ary.add(stk_idn);
                 if(!mnlVal.equals("") && !mnlVal.equals("0")){
                     updateMstk = "update mstk  set stt = ? , upr=? , dte = sysdate  where stt = 'AS_PRC'  and idn = ? ";
                     ary = new ArrayList();
                     ary.add(sttVal);
                     ary.add(mnlVal);
                     ary.add(stk_idn);
                 }
               
               ct = db.execUpd("updateMstk", updateMstk,ary);
               if(ct>0){
                if(sttVal.equals("MKAV"))
                    AVCount++;
                if(sttVal.equals("MKPP"))
                   PPCount++;
                if(sttVal.equals("MKLB"))
                   LBCount++;
               }else{
                    errorMsg = errorMsg+ vnm+"," ;
                }
                }
                
                
                if(!mnlVal.equals("")  && !mnlVal.equals("0")){
                    updateMstk = "update mstk  set  upr=? , dte = sysdate  where idn = ? ";
                    ary = new ArrayList();
                    ary.add(mnlVal);
                    ary.add(stk_idn);
                    ct = db.execUpd("updateMstk", updateMstk,ary);
                    req.setAttribute("msg","Changes Save Successfully....");
                }
                if(!diffVal.equals("")){
                    String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?, pPrpTyp => ? )";
                    ary = new ArrayList();
                    ary.add(stk_idn);
                    ary.add("EXTRADISCAMT");
                    ary.add(diffVal);
                    ary.add(certLab);
                    ary.add("N");
                    ct = db.execCall("stockUpd",stockUpd, ary);
                    req.setAttribute("msg","Changes Save Successfully....");
                }
                
            }}
        rs.close();
          pst.close();
            if(AVCount!=0 || PPCount!=0 || LBCount!=0 ){
                ary = new ArrayList();
                String msg = "Selected stones get Tranfer to Marketing :- ";
                String tffLog = "insert into prc_trf_log(prc_trf_log_idn, frm_stt, to_stt, mdl, unm, trf_dte, trf_cnt) " + 
                "  values ( PRC_TRF_LOG_SEQ.nextval, 'AS_PRC', ? , 'TRF_TO_MKTG', ? , sysdate, ? ) ";
                if(AVCount!=0){
                   msg = msg+" Available : "+AVCount+" , ";
                       ary = new ArrayList();
                       ary.add("MKAV");
                       ary.add(info.getByrNm());
                       ary.add(String.valueOf(AVCount));
                   } if(PPCount!=0){
                       msg = msg+" Premium Plus : "+PPCount+" , ";
                       ary = new ArrayList();
                       ary.add("MKPP");
                       ary.add(info.getByrNm());
                       ary.add(String.valueOf(PPCount));
                   } if(LBCount!=0){
                      msg = msg+" Look And Bid : "+LBCount ;
                       ary = new ArrayList();
                       ary.add("MKLB");
                       ary.add(info.getByrNm());
                       ary.add(String.valueOf(LBCount));
                   }
                ct = db.execUpd("insertPrc", tffLog, ary);
                req.setAttribute("msg", msg);
            }
            if(!errorMsg.equals("")){
                req.setAttribute("errormsg", errorMsg+" Price not found");
                
            }
          return am.findForward("load");
        }
      
    }
    public ActionForward status(ActionMapping am,
                                 ActionForm af,
                                 HttpServletRequest req,
                                 HttpServletResponse res) throws Exception {
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
        TransferToMktForm form = (TransferToMktForm)af;
      
        String lseq = req.getParameter("seq");
        getstt(req , res,lseq);
          int accessidn=util.updAccessLog(req,res,"Transfer to Marketing", "sattus");
          req.setAttribute("accessidn", String.valueOf(accessidn));
          return am.findForward("loadPrc");
        }
     
    }
    public ArrayList  getMemoRtn(HttpServletRequest req){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList  repMemoLst = new ArrayList();
        repMemoLst =(ArrayList)session.getAttribute("tfmktPrpList");
        if(repMemoLst==null){
            repMemoLst = new ArrayList();
        String rep_prpVw = "select prp from rep_prp where mdl='TFMKT_VW' and flg='Y' order by srt, rnk";
          ArrayList outLst = db.execSqlLst("rep_prp",rep_prpVw, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs1 = (ResultSet)outLst.get(1);
        try {
            while (rs1.next()) {
                repMemoLst.add(rs1.getString("prp"));
            }
            rs1.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        }
        session.setAttribute("tfmktPrpList", repMemoLst);
        
       return repMemoLst;
    }
    public void getstt( HttpServletRequest req ,HttpServletResponse res, String lseq){
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
            int count = 0;
            int out = 0;
            ArrayList ary = new ArrayList();
            String msg = "Re Pricing in process....";
          
            if (!lseq.equals("")) {
                String df_pricing_status =
                    "select df_pricing_status(?) stt from dual";
                ary = new ArrayList();
                ary.add(lseq);
              ArrayList outLst = db.execSqlLst("price Status", df_pricing_status, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
                if (rs.next())
                    out = rs.getInt(1);
                rs.close();
                pst.close();
            }
            if (out == 0)
                msg = "Re Priceing done";
            if (out == 1)
                msg = "Re Pricing in process....";
            if (out == -1)
                msg = "Invaild Sequence";
            String sql =
                "select count(*) from pri_batch where pri_seq = ? and flg ='-1'";
            ary = new ArrayList();
            ary.add(lseq);
          ArrayList outLst = db.execSqlLst("price count ", sql, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            if (rs.next())
                count = rs.getInt(1);
            rs.close();
            pst.close();
            req.setAttribute("seq", lseq);
            req.setAttribute("msg", msg);
            req.setAttribute("out", String.valueOf(out));
            req.setAttribute("count", String.valueOf(count));
          int accessidn=util.updAccessLog(req,res,"Transfer to Marketing", "getstt");
          req.setAttribute("accessidn", String.valueOf(accessidn));
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    }
    
    public ActionForward stockUpd(ActionMapping am , ActionForm fm , HttpServletRequest req , HttpServletResponse res){
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
        TransferToMktForm form = (TransferToMktForm)fm;
        String stkIdn = req.getParameter("mstkIdn");
        if(stkIdn==null)
            stkIdn = (String)form .getValue("mstkIdn");
       
        ArrayList grpList = new ArrayList();
        HashMap stockPrpUpd = new HashMap();
        String stockPrp = "select mstk_idn, lab,b.dta_typ, mprp, a.srt, grp , decode(b.dta_typ, 'C', a.val,'N', to_char(a.num), 'D', to_char(dte,'dd-mm-rrrr'), nvl(txt,'')) val " + 
        "from stk_dtl a, mprp b , rep_prp c " + 
        "where a.mprp = b.prp and mstk_idn =?  and b.dta_typ in ('C','N','T','D') and c.prp = b.prp and c.flg = 'Y' and mdl='AS_UPD_DFLT' " + 
        "order by grp, psrt, mprp,lab ";
        ArrayList ary = new ArrayList();
        ary.add(stkIdn);
        String pgrp ="";
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
                if(grp.equals("1")){
                form .setValue(mprp, util.nvl(rs.getString("val")));
                form .setValue("lab", util.nvl(rs.getString("lab")));
                }
               
            }
            
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        form.setValue("mstkIdn", stkIdn);
        PRPUPDDFLTList(req);
        req.setAttribute("stockList",stockPrpUpd);
        req.setAttribute("grpList", grpList);
        req.setAttribute("mstkIdn", stkIdn);
          return am.findForward("update");
        }
   
    }
    
    public ArrayList PRPUPDDFLTList(HttpServletRequest req){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        
        ArrayList viewPrp = (ArrayList)session.getAttribute("ASPRCEDITList");
        try {
            if (viewPrp == null) {

                viewPrp = new ArrayList();
       
              ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'AS_PRC_EDIT' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                   viewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                pst.close();
                session.setAttribute("ASPRCEDITList", viewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return viewPrp;
    }
    
    public ActionForward CrtExcel(ActionMapping am,
                                 ActionForm af,
                                 HttpServletRequest req,
                                 HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"Transfer to Marketing", "create xl");
      HashMap dbinfo = info.getDmbsInfoLst();
      String clnt = (String)dbinfo.get("CNT");
      ExcelUtil xlUtil = new ExcelUtil();
      xlUtil.init(db, util, info);
      int ct = db.execUpd("gtUpdate","update gt_srch_rslt set prp_001 = null where flg='S'", new ArrayList());
       String pktPrp = "pkgmkt.pktPrp(0,?)";
       ArrayList ary = new ArrayList();
       ary.add("TMKT_XL");
       int ct1 = db.execCall(" Srch Prp ", pktPrp, ary);
      String CONTENT_TYPE = "getServletContext()/vnd-excel";
      String fileNm = "TransferToMktExcel"+util.getToDteTime()+".xls";
      res.setContentType(CONTENT_TYPE);
      res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
      HSSFWorkbook hwb = null;
      hwb = xlUtil.getDataAllInXl("TMKT", req, "TMKT_XL");
      OutputStream out = res.getOutputStream();
      hwb.write(out);
      out.flush();
      out.close();
       
      
        }
      return null;
    }
//    public ArrayList LBGenricSrch(HttpServletRequest req){
//        
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("TFMKTSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp , flg  from rep_prp where mdl = 'TFMKT_SRCH' and flg <> 'N'  order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("TFMKTSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
}
