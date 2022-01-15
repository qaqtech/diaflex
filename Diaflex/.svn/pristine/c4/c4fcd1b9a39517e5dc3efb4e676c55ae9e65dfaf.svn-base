package ft.com.pricing;

import ft.com.DBMgr;

import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.PrcPrpBean;

import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.StringTokenizer;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class RepriceAction extends DispatchAction {
    
  public RepriceAction() {
        super();
    }
    
  public ActionForward setup(ActionMapping mapping,
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
        return mapping.findForward(rtnPg);   
    }else{
        util.updAccessLog(req,res,"reprice", "setup");                 
      RepriceFrm rf = (RepriceFrm)af;
      
      ArrayList prcPrpList = new ArrayList();
      String getPrpL = " select * " +
          "from rep_prp a  " +
          "where  a.mdl = 'REPRC_PRP' " +
          "and a.flg in ('M', 'S') order by a.rnk ";
        ArrayList outLst = db.execSqlLst(" Price Prp Lst", getPrpL, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()) {
         ArrayList prcPrp = new ArrayList();
          prcPrp.add(rs.getString("prp"));
          prcPrp.add(rs.getString("flg"));
          prcPrpList.add(prcPrp);
      }
      rs.close();
        pst.close();
      session.setAttribute("prcPrpList", prcPrpList);
  
     
      rf.reset();
       
       String pgDef = "REPRICE";
       HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
       HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);  
       if(pageDtl==null || pageDtl.size()==0){
          pageDtl=new HashMap();
          pageDtl=util.pagedef(pgDef);
          allPageDtl.put(pgDef,pageDtl);
          }
        info.setPageDetails(allPageDtl);
        
        util.updAccessLog(req,res,"reprice", "setup end"); 
        return mapping.findForward("setup");
      }
    
  }
  
  public ActionForward reprc(ActionMapping mapping,
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
        return mapping.findForward(rtnPg);   
    }else{
        util.updAccessLog(req,res,"reprice", "reprc");  
      String delQ = " Delete from gt_pkt ";
      int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
      HashMap dbinfo = info.getDmbsInfoLst();
      int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
     String  jbCntDb = util.nvl((String)dbinfo.get("RPCNT"));
     if(jbCntDb.equals(""))
         jbCntDb="15";
      RepriceFrm form = (RepriceFrm)af;
      String frmID =  util.nvl((String)form.getValue("vnm"));
      String repriceAll = util.nvl(form.getRepriceAll());
      String view = util.nvl(form.getView());
      ArrayList out = new ArrayList();
      out.add("I");
      if(view.length() > 0){
          fetch(req, res ,af);
      }else{
      String reprc = "reprc(num_job => ? , lSeq => ?)";
      ArrayList reprcParams = new ArrayList();
      int jobCnt = Integer.parseInt(jbCntDb);
      reprcParams.add(String.valueOf(jobCnt));
      if(repriceAll.length() > 0) {
          util.updAccessLog(req,res,"reprice", "reprc repriceAll Btn");
      } else {
          util.updAccessLog(req,res,"reprice", "reprc reprice Btn");
        reprc = "reprc(num_job => ?, lstt1 => ?, lstt2 => ?,lSeq=> ? )"; 
        reprcParams.add("AS_PRC");
        reprcParams.add("FORM");  
        
        String lMemoId = util.nvl(String.valueOf(form.getMemoIdn()), "");
        String vnm = util.nvl(form.getVnm());
          if(!vnm.equals(""))
          vnm = util.getVnm(vnm);
        if(lMemoId.length() > 0) {
            String insQ = " insert into gt_pkt(mstk_idn) select mstk_idn from jandtl where idn = ? ";
            ArrayList params = new ArrayList();
            params.add(lMemoId);
            ct = db.execUpd(" reprc memo", insQ, params);
        }else if(vnm.length() > 0){
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
            String insQ = " insert into gt_pkt(mstk_idn) select idn from mstk where ( vnm in ("+vnmSub+") or tfl3 in ("+vnmSub+") ) ";
            ct = db.execUpd(" reprc memo", insQ, new ArrayList());
            }
           
        }else{
           int cnt = msrch(req,res, af);
            if(cnt > 0){
                String insQ = " insert into gt_pkt(mstk_idn) select stk_idn from gt_srch_rslt  ";
                ArrayList params = new ArrayList();
              
                 ct = db.execUpd(" reprc memo", insQ, params);
            }
             
            
         }
           
        }
    int lseq = 0;
     CallableStatement cnt = db.execCall(" reprc : ",reprc, reprcParams,out );
       lseq = cnt.getInt(reprcParams.size()+1);
       req.setAttribute("seqNo","Current Repricing Sequence Number :  "+String.valueOf(lseq));
      }
        util.updAccessLog(req,res,"reprice", "reprc end");  
        return mapping.findForward("setup");
      }
   
  }
  
  
  
  public void fetch(HttpServletRequest req , HttpServletResponse res, ActionForm af ){
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
    util.updAccessLog(req,res,"reprice", "fetch");  
       RepriceFrm form = (RepriceFrm)af;
      HashMap dbinfo = info.getDmbsInfoLst();
      int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
      String lMemoId = util.nvl(String.valueOf(form.getMemoIdn()), "");
      String vnm =  util.nvl(form.getVnm());
          ArrayList params = new ArrayList();
      String delQ = " Delete from gt_srch_rslt ";
      int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
      ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
      if(lMemoId.length()>0 ){
      
          String srchRefQ = 
          "    Insert into gt_srch_rslt (srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, rap_dis , cert_lab, cert_no, flg, sk1, fquot, quot ) " + 
          "                select 1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt" +
              " , cmp , rap_rte,  decode(rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2)) , cert_lab, cert_no, 'Z' flg, sk1, upr, cmp  " + 
          "                from mstk b where stt <> 'MKSL' and " + 
          "                pkt_ty = 'NR'" + 
          " and exists (select 1 from jandtl a where a.mstk_idn = b.idn and a.idn in ("+ lMemoId + "))";
             ct = db.execUpd(" Srch Ref ", srchRefQ, new ArrayList());
          
          String pktPrp = "pkgmkt.pktPrp(0,?)";
          params = new ArrayList();
          params.add("PRC_PRP");
          ct = db.execCall(" Srch Prp ", pktPrp, params);
      }else if(vnm.length()>0){
             vnm = util.getVnm(vnm);
             //StringTokenizer str = new StringTokenizer(vnm, ",");                             
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
                    String vnmSub = vnm.substring(fromLoc, toLoc);

            String srchRefQ = 
                 "    Insert into gt_srch_rslt ( srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt,  rap_rte,rap_dis , cert_lab, cert_no, flg, sk1, fquot, quot , cmp  ) " +
                 "     select  1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt,  rap_rte ,  decode(rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2)) " +
                 "     , cert_lab, cert_no, 'Z' flg, sk1, upr, cmp , cmp " +
                 "    from mstk b " +
                 "     where  pkt_ty = 'NR' and  stt <> 'MKSL'" +
                   
                 "    and ( vnm in ("+vnmSub+") or tfl3 in ("+vnmSub+") )   ";
             
             ct = db.execUpd(" Srch Ref ", srchRefQ, new ArrayList());
              
             }

         
                  
     params = new ArrayList();
      String pktPrp = "pkgmkt.pktPrp(0,?)";
      params = new ArrayList();
      params.add("PRC_PRP");
      ct = db.execCall(" Srch Prp ", pktPrp, params);
      }else{
         msrch(req, res , af);
      }
      
      ArrayList ViewPrp = (ArrayList)session.getAttribute("PRCPRPViewLst");
      if(ViewPrp==null)
       ViewPrp = getPRC_PRP(req, res);
      
      SearchQuery srchQuery = new SearchQuery();
      HashMap pktList = srchQuery.SearchResult(req, res,"'Z'",ViewPrp);
      if(pktList!=null && pktList.size() >0){
          HashMap totals = srchQuery.GetTotal(req,res,"Z");
          req.setAttribute("totalMap", totals);
      }
      req.setAttribute("pktList", pktList);
      req.setAttribute("view", "Y");
    util.updAccessLog(req,res,"reprice", "fetch end");  
  }
  public int msrch(HttpServletRequest req , HttpServletResponse res, ActionForm af){
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
    util.updAccessLog(req,res,"reprice", "srch");  
      RepriceFrm form = (RepriceFrm)af;
      ArrayList prcPrpList = (ArrayList)session.getAttribute("prcPrpList");
      int lSrchId =0;
      String getSrchId = "srch_pkg.get_srch_id(pRlnId => ?, pTyp => ?, pTrmVal => ?, pMdl => ?, pFlg => ?, pSrchId => ?)";
      ArrayList params = new ArrayList();
      params.add("104");
      params.add("Z");  
      params.add("1");
      params.add("Z");
      params.add("");  
        
      ArrayList outParams = new ArrayList();
      outParams.add("I");
        
       CallableStatement cst = db.execCall(" Add Srch Hdr ", getSrchId, params, outParams);
        try {
          lSrchId = cst.getInt(params.size()+1);
          cst.close();
          cst=null;
        } catch (SQLException e) {
        }   
      
      if(lSrchId > 0) {
      HashMap prp = info.getPrp();
      HashMap mprp = info.getMprp();
      String addSrchDtl = "srch_pkg.add_srch_dtl(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
      String addSrchDtlVal = "srch_pkg.add_srch_dtl_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
      String addSrchDtlSub = "srch_pkg.add_srch_dtl_sub(pSrchId => ?, pPrp => ?, pSrtFr => ?, pSrtTo => ?)";
      String addSrchDtlSubVal = "srch_pkg.add_srch_dtl_sub_val(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
      String addSrchDtlDte = "srch_pkg.add_srch_dtl_dte(pSrchId => ?, pPrp => ?, pValFr => ?, pValTo => ?)";
      for (int i = 0; i < prcPrpList.size(); i++) {
        boolean dtlAddedOnce = false ;
          int cnt =0;
        ArrayList srchPrp = (ArrayList) prcPrpList.get(i);
        String lprp = (String)srchPrp.get(0);
        String flg= (String)srchPrp.get(1);
        String prpSrt = lprp ;  
        String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
       
          if(lprp.equals("CRTWT"))
              prpSrt = "SIZE";
          ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
          ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
          
         
          
          if(flg.equals("M")) {
              if(lprp.equalsIgnoreCase("CRTWT")) {
                
                    for(int j=0; j < lprpS.size(); j++) {
                      String lSrt = (String)lprpS.get(j);
                      String reqVal1 = util.nvl((String)form.getValue(lprp + "_1_" + lSrt),"");
                      String reqVal2 = util.nvl((String)form.getValue(lprp + "_2_" + lSrt),"");
                        if((reqVal1.length() == 0) || (reqVal2.length() == 0)) {
                          //ignore no value selected;
                        } else {
                            if(!dtlAddedOnce) {
                              params = new ArrayList();
                              params.add(String.valueOf(lSrchId));
                              params.add(lprp);
                              params.add(reqVal1);
                              params.add(reqVal2);
                              cnt = db.execCall(" SrchDtl ", addSrchDtl, params);
                              dtlAddedOnce = true;
                            }
                            params = new ArrayList();
                            params.add(String.valueOf(lSrchId));
                            params.add(lprp);
                            params.add(reqVal1);
                            params.add(reqVal2);
                            cnt = db.execCall(" SrchDtl ", addSrchDtlSub, params);
                        }
                    }
                }    
               else {
                //All Other Multiple Properties
                  
                for(int j=0; j < lprpS.size(); j++) {
                  String lSrt = (String)lprpS.get(j);
                  String lVal = (String)lprpV.get(j);
                  String lFld =  lprp + "_" + lVal; 
                  String reqVal = util.nvl((String)form.getValue(lFld));
                  //util.SOP(lprp + " : " + lFld + " : " + reqVal);  
                    if(reqVal.length() > 0) {  
                      if(!dtlAddedOnce) {
                        params = new ArrayList();
                        params.add(String.valueOf(lSrchId));
                        params.add(lprp);
                        params.add(reqVal);
                        params.add(reqVal);
                        cnt = db.execCall(" SrchDtl ", addSrchDtlVal, params);
                        dtlAddedOnce = true;
                      }
                      params = new ArrayList();
                      params.add(String.valueOf(lSrchId));
                      params.add(lprp);
                      params.add(reqVal);
                      params.add(reqVal);
                      cnt = db.execCall(" SrchDtl ", addSrchDtlSubVal, params);
                  }
                }
              }     
          } else {
              
              
            String reqVal1 = util.nvl((String)form.getValue(lprp + "_1"),"");
            String reqVal2 = util.nvl((String)form.getValue(lprp + "_2"),"");
              if(lprpTyp.equals("T"))
                  reqVal2 = reqVal1;
            //util.SOP(lprp + " : "+ reqVal1 + " : " + reqVal2);  
            
            if(((reqVal1.length() == 0) || (reqVal2.length() == 0))
              || ((reqVal1.equals("0")) && (reqVal2.equals("0")) )) {
                //ignore no selection;
            } else {
             
              params = new ArrayList();
              params.add(String.valueOf(lSrchId));
              params.add(lprp);
              params.add(reqVal1);
              params.add(reqVal2);
            if(lprpTyp.equals("T"))
              cnt = db.execCall(" SrchDtl ", addSrchDtlVal, params);
            else if(lprpTyp.equals("D"))
              cnt = db.execCall(" SrchDtl ", addSrchDtlDte, params);
            else
                cnt = db.execCall(" SrchDtl ", addSrchDtl, params);  
              
            }
          }
        }    
      }   
      
          String dbSrch = "pkgmkt.srch(?, 'PRC_PRP')";
          params = new ArrayList();
          params.add(String.valueOf(lSrchId));
          int cnt = db.execCall(" Srch : " + lSrchId, dbSrch, params);
    util.updAccessLog(req,res,"reprice", "srch end");  
          return cnt;
  }
  public ArrayList getPRC_PRP(HttpServletRequest req , HttpServletResponse res){
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
      ArrayList ViewPrp = (ArrayList)session.getAttribute("PRCPRPViewLst");
      try {
          if (ViewPrp == null) {

              ViewPrp = new ArrayList();
        
            ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'PRC_PRP' and flg='Y' order by rnk ",
                             new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs1 = (ResultSet)outLst.get(1);
              while (rs1.next()) {

                  ViewPrp.add(rs1.getString("prp"));
              }
              rs1.close();
              pst.close();
              session.setAttribute("PRCPRPViewLst", ViewPrp);
          }

      } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
      }
      
      return ViewPrp;
  }
  
    public ActionForward status(ActionMapping mapping,
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
          return mapping.findForward(rtnPg);   
      }else{
          util.updAccessLog(req,res,"reprice", "status");  
        RepriceFrm form = (RepriceFrm)af;
        int out = 0;
        String msg="Re Pricing in process....";
        String lseq = util.nvl(form.getSeq());
        if(lseq.equals(""))
            lseq = req.getParameter("lseq");
        getstt(req , lseq);
          util.updAccessLog(req,res,"reprice", "status");  
          return mapping.findForward("loadPrc");
        }
     
    }
    
    public void getstt( HttpServletRequest req , String lseq){
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
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    }
    public ActionForward loadRT(ActionMapping mapping,
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
          return mapping.findForward(rtnPg);   
      }else{
          util.updAccessLog(req,res,"reprice", "load rt");  
        String stt = util.nvl(req.getParameter("stt"));
        if(stt.equals(""))
            stt = "Pending";
        
     
        String sql="select pri_seq,  cnt, to_char(min(mn_dte), 'dd-Mon-rrrr HH24:mi') FrmDte, to_char(max(mx_dte), 'dd-Mon-rrrr HH24:mi') ToDte , stt " +
            " from PRI_STT_V a where stt ='"+stt+"' "+
            " and not exists (select 1 from PRI_STT_V b where stt <> '"+stt+"'  and cnt = 0 and a.pri_seq = b.pri_seq) group by pri_seq , cnt, stt order by pri_seq desc";
          ArrayList outLst = db.execSqlLst("sql", sql, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        ArrayList pktList = new ArrayList();
        while(rs.next()){
            HashMap pktDtl = new HashMap();
            pktDtl.put("seq", util.nvl(rs.getString("pri_seq")));
            pktDtl.put("cnt", util.nvl(rs.getString("cnt")));
            pktDtl.put("FrmDte", util.nvl(rs.getString("FrmDte")));
            pktDtl.put("ToDte", util.nvl(rs.getString("ToDte")));
            pktDtl.put("stt", util.nvl(rs.getString("stt")));
            pktList.add(pktDtl);
        }
        rs.close();
          pst.close();
        req.setAttribute("pktList", pktList);
          util.updAccessLog(req,res,"reprice", "load rt"); 
          return mapping.findForward("loadRT");
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
          util.updAccessLog(req,res,"Assort Issue Action", "init");
          }
          }
          return rtnPg;
          }
}

   

