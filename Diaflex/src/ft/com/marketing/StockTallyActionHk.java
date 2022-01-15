package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.IOException;

import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.Connection;
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

public class StockTallyActionHk  extends DispatchAction {
    public StockTallyActionHk() {
        super();
    }
    
    public ActionForward loadTally(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
         util.updAccessLog(req,res,"Stock Tally", "loadTally start"); 
         StockTallyForm udf = (StockTallyForm)af;
         GenericInterface genericInt = new GenericImpl();
         udf.reset();
       ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_StkTllySrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_StkTllySrch");
       info.setGncPrpLst(assortSrchList);
       TallyStockstatus(req);
       TallyStock(req,"");
       HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
       HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_TALLY");
       if(pageDtl==null || pageDtl.size()==0){
       pageDtl=new HashMap();
       pageDtl=util.pagedef("STOCK_TALLY");
       allPageDtl.put("STOCK_TALLY",pageDtl);
       }
       info.setPageDetails(allPageDtl);
           util.updAccessLog(req,res,"Stock Tally", "loadTally end"); 
       return am.findForward("load");
       }
   }
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Stock Tally", "fetch start"); 
            StockTallyForm udf = (StockTallyForm)af;
         HashMap mprp = info.getMprp();
         HashMap prp = info.getPrp();
        String issuehk = util.nvl((String)udf.getValue("issuehk"));
        if(!issuehk.equals("")){
        int ct = db.execCall("stk_srch", "DP_HK_STK_TLY", new ArrayList());  
        }else{
        ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_StkTllySrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_StkTllySrch");
        info.setGncPrpLst(genricSrchLst);
        //      ArrayList genricSrchLst = info.getGncPrpLst();
        HashMap  paramsMap = new HashMap();
        String mstkStt = "ALL";
        String[] stt =  udf.getSttValLst();
        if(stt!=null){
        if(stt.length>0)
            mstkStt = stt[0];
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
                    String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                    if(!reqVal1.equals("")){
                    paramsMap.put(lprp + "_" + lVal, reqVal1);
                    }
                       
                    }
                }else{
                String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                if(typ.equals("T")){
                    fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
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
        
        if(paramsMap.size()>0){
        paramsMap.put("stt", mstkStt);
        paramsMap.put("cprp", "GRP_STT");
        paramsMap.put("cprpValLst", stt);
        paramsMap.put("mdl", "RFID_VW");
        paramsMap.put("PRCD","STK_TLY");
        util.genericSrch(paramsMap);
        req.setAttribute("seqMsg", "Y");
        }else{
            req.setAttribute("msg", "Please Select Stock Criteria.");
        }
        }else{
                req.setAttribute("msg", "Please Select Status.");  
        }
            
        }
         TallyStock(req,"");
       
        udf.reset();
        util.updAccessLog(req,res,"Stock Tally", "fetch end"); 
      return am.findForward("load");
        }
    }
    
    public ActionForward loadsetupseq(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
         util.updAccessLog(req,res,"Stock Tally", "loadsetupseq start"); 
         StockTallyForm udf = (StockTallyForm)af;
           ArrayList seqLst=new ArrayList();
           String tallySeq = " select distinct seq_idn from stk_tly where trunc(iss_dte) = trunc(sysdate) order by seq_idn desc";
         ArrayList outLst = db.execSqlLst("tallySeq", tallySeq, new ArrayList());
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet rs = (ResultSet)outLst.get(1);
           while(rs.next()){
               seqLst.add(rs.getString("seq_idn"));
           }
           rs.close();
           pst.close();
           req.setAttribute("seqLst", seqLst);
         util.updAccessLog(req,res,"Stock Tally", "loadsetupseq end"); 
       return am.findForward("loadsetupseq");
       }
    }
    
    public ActionForward savesetupseq(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
         util.updAccessLog(req,res,"Stock Tally", "savesetupseq start"); 
         StockTallyForm udf = (StockTallyForm)af;
         String seqidn=util.nvl((String)udf.getValue("seq"));
         info.setSeqidn(seqidn);
         util.updAccessLog(req,res,"Stock Tally", "savesetupseq end"); 
       return loadRtn(am,af,req,res);
       }
    }
    public ActionForward fetchSeq(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Stock Tally", "fetchseq start"); 
            StockTallyForm udf = (StockTallyForm)af;
        String loadSeq = util.nvl((String)udf.getValue("seq"));
        String redirect = util.nvl((String)udf.getValue("redirect"),"load");
        if(loadSeq.equals(""))
        loadSeq=info.getSeqidn();
            
        TallyStock(req,loadSeq);
        req.setAttribute("seqNo", loadSeq);
        if(redirect.equals("loadRtn")){
        HashMap flgMap=getstktlynf(req,loadSeq);
        req.setAttribute("sttMap", flgMap);
        }
            util.updAccessLog(req,res,"Stock Tally", "fetchseq end"); 
        return am.findForward(redirect);
        }
    }
    
       
    public void TallyStock(HttpServletRequest req , String seq){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        Connection conn=info.getCon();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList seqLst = new ArrayList();
        HashMap pktDtl = new HashMap();
        HashMap  pDtl = new HashMap();
        ArrayList deptLst=new ArrayList();
        ArrayList grpList = new ArrayList();
        String str = "";
        ArrayList ary = new ArrayList();
        if(!seq.equals("") && !seq.equals("0")){
            str = " and seq_idn = ? ";
            ary.add(seq);
            ary.add(seq);
        }
       String sqlTally = " select sum(a.qty) qty, to_char(trunc(sum(trunc(a.cts,2)),2),'99999999990.99') cts, b.stt, b.stk_stt \n" + 
       "from mstk a, stk_tly b where a.idn = b.stk_idn and trunc(iss_dte) = trunc(sysdate) \n"+str+
       "group by b.stt, b.stk_stt\n" + 
       "union\n" +
       "select sum(a.qty) qty, to_char(trunc(sum(trunc(a.cts,2)),2),'99999999990.99') cts , b.stt, 'TOTAL' \n" + 
       "from mstk a, stk_tly b where a.idn = b.stk_idn and trunc(iss_dte) = trunc(sysdate) \n"+str+ 
       "group by b.stt  order by stk_stt";
      ArrayList outLst = db.execSqlLst("sql", sqlTally, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                String qty = util.nvl(rs.getString("qty"));
                String cts = util.nvl(rs.getString("cts"));
                String grp1 = util.nvl(rs.getString("stk_stt"));
                String tlyStt = util.nvl(rs.getString("stt"));
                pktDtl.put(grp1 + "_" + tlyStt + "_Q", qty);
                pktDtl.put(grp1 + "_" + tlyStt + "_C", cts);
                if (!grpList.contains(grp1))
                    grpList.add(grp1);
            }
        
            rs.close();
            pst.close();
           
        if(grpList.size()>0){
            String setupseq=info.getSeqidn();
            if(setupseq.equals("")){
            String tallySeq = " select distinct seq_idn from stk_tly where trunc(iss_dte) = trunc(sysdate) order by seq_idn desc";
              outLst = db.execSqlLst("tallySeq", tallySeq, new ArrayList());
               pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                seqLst.add(rs.getString("seq_idn"));
            }
                rs.close();
                pst.close();
            }else{
                seqLst.add(setupseq); 
            }
            
            
        }
            String tallySeq = " select sum(c.qty) qty, to_char(trunc(sum(trunc(c.cts,2)),2),'99999999990.99') cts,decode(md.dta_typ, 'C', d.val, 'N', to_char(d.num), 'D', to_char(d.dte, 'dd-Mon-rrrr'), d.txt) dept\n" + 
            "from mstk c,stk_dtl d,stk_dtl e,mprp md,mprp ms\n" + 
            "Where C.Idn=D.Mstk_Idn And C.Idn=E.Mstk_Idn And D.Mprp='DEPT' And D.Grp=1  \n" + 
            "And E.Mprp='STK_LOC' And E.Grp=1 and d.mprp=md.prp and e.mprp=ms.prp\n" + 
            " and c.stt in('MKAV','MKWH','MKAP','MKWA','MKSA','MKCS','MKSL') and c.pkt_ty='NR'  and decode(ms.dta_typ, 'C', e.val, 'N', to_char(e.num), 'D', to_char(e.dte, 'dd-Mon-rrrr'), e.txt) <> 'NA'\n" + 
            "group by decode(md.dta_typ, 'C', d.val, 'N', to_char(d.num), 'D', to_char(d.dte, 'dd-Mon-rrrr'), d.txt)";
          outLst = db.execSqlLst("tallySeq", tallySeq, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                String dept=util.nvl(rs.getString("dept"));
                pktDtl.put(dept+"_Q", util.nvl(rs.getString("qty")));
                pktDtl.put(dept+"_C", util.nvl(rs.getString("cts")));
               if(!deptLst.contains(dept))
                   deptLst.add(dept);
            }
            
            rs.close();
            pst.close();
            String tallySeqs = " select sum(c.qty) qty, to_char(trunc(sum(trunc(c.cts,2)),2),'99999999990.99') cts,decode(md.dta_typ, 'C', d.val, 'N', to_char(d.num), 'D', to_char(d.dte, 'dd-Mon-rrrr'), d.txt) dept\n" + 
                        "from mstk c,stk_dtl d,stk_dtl e,mprp md,mprp ms\n" + 
                        "Where C.Idn=D.Mstk_Idn And C.Idn=E.Mstk_Idn And D.Mprp='DEPT' And D.Grp=1  \n" + 
                        "And E.Mprp='STK_LOC' And E.Grp=1 and d.mprp=md.prp and e.mprp=ms.prp\n" + 
                        " and c.stt in('MKIS','MKEI','SHIS','MKKS_IS','MKOS_IS') and c.pkt_ty='NR'  and decode(ms.dta_typ, 'C', e.val, 'N', to_char(e.num), 'D', to_char(e.dte, 'dd-Mon-rrrr'), e.txt) <> 'NA'\n" + 
                        "group by decode(md.dta_typ, 'C', d.val, 'N', to_char(d.num), 'D', to_char(d.dte, 'dd-Mon-rrrr'), d.txt)";
                      outLst = db.execSqlLst("tallySeq", tallySeq, new ArrayList());
                       pst = (PreparedStatement)outLst.get(0);
                      rs = (ResultSet)outLst.get(1);
                        while(rs.next()){
                            String dept=util.nvl(rs.getString("dept"));
                            pktDtl.put(dept+"_QIS", util.nvl(rs.getString("qty")));
                            pktDtl.put(dept+"_CIS", util.nvl(rs.getString("cts")));
                           if(!deptLst.contains(dept))
                               deptLst.add(dept);
                        }
                        
                        rs.close();
                     pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        session.setAttribute("grpList",grpList);
        session.setAttribute("pktDtl", pktDtl);
        session.setAttribute("seqLst", seqLst);
        session.setAttribute("depTlytLst", deptLst);
    }
    
    public ActionForward TallyCRT(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Stock Tally", "TallyCrt start"); 
        StockTallyForm udf = (StockTallyForm)af;
        HashMap pktDtl = new HashMap();
        ArrayList crtList = new ArrayList();
        String loadSeq = util.nvl(req.getParameter("loadSeq"));
        String stt = util.nvl(req.getParameter("stt"));
        String str ="";
        ArrayList ary = new ArrayList();
        
        if(!loadSeq.equals("")  && !loadSeq.equals("0")){
            str = " and b.seq_idn = ? ";
            ary.add(loadSeq);
        }
        if(!stt.equals("") && !stt.equals("TOTAL")){
         str += "   and b.stk_stt=? ";
         ary.add(stt);
        }
        String tallyCrt = " select sum(a.qty) qty, trunc(sum(trunc(a.cts,2)),2) cts, b.flg1 stt , rd.dsc , b.stt tlyStt  from mstk a, stk_tly b , rule_dtl rd, mrule mr "+
                          " where a.idn = b.stk_idn and trunc(iss_dte) = trunc(sysdate) and b.flg1 = rd.chr_fr and rd.rule_idn = mr.rule_idn \n" + 
                          " and mr.mdl = 'JFLEX' and mr.nme_rule = 'STK_TTY' and rd.dsc <> 'DLV' and rd.til_dte is null "+str+
                         " group by b.flg1 , rd.dsc , b.stt ";
        
          ArrayList outLst = db.execSqlLst("tallyCrt", tallyCrt, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
               String cnt = util.nvl(rs.getString("qty"));
               String dsc = util.nvl(rs.getString("dsc"));
               String cts =  util.nvl(rs.getString("cts"));
               String stkStt = util.nvl(rs.getString("Stt"));
               String tlyStt = util.nvl(rs.getString("tlyStt"));
               pktDtl.put(stkStt+"_"+tlyStt+"_Q" , cnt);
               pktDtl.put(stkStt+"_"+tlyStt+"_C" , cts);
               pktDtl.put(stkStt , dsc);
               if(!crtList.contains(stkStt))
                   crtList.add(stkStt);
         }
        rs.close();
            pst.close();
            req.setAttribute("crtList",crtList);
            pktDtl.put("GRP", stt);
            req.setAttribute("pktDtl", pktDtl);
            req.setAttribute("seqNo", loadSeq);
            util.updAccessLog(req,res,"Stock Tally", "Tallycrt end"); 
        return am.findForward("TallyCRT");
        }
    }
    public ActionForward TallyCRTonApproval(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                util.updAccessLog(req,res,"Stock Tally", "TallyCRTonApproval start"); 
            StockTallyForm udf = (StockTallyForm)af;
            HashMap pktDtl = new HashMap();
            ArrayList crtList = new ArrayList();
            ArrayList ary =new ArrayList();
            String dept=util.nvl(req.getParameter("dept"));
            String status=util.nvl(req.getParameter("status"));
            String stkloc = util.nvl(req.getParameter("stkloc"));
            String tallyCrtonApp ="";
            String conQ=" and decode(md.dta_typ, 'C', d.val, 'N', to_char(d.num), 'D', to_char(d.dte, 'dd-Mon-rrrr'), d.txt)=? ";
                String sttQ=" ";
                if(status.equals("IS"))
                sttQ=" and c.stt in('MKIS','MKEI','SHIS','MKKS_IS','MKOS_IS') ";  
                if(status.equals("AP"))
                sttQ=" and c.stt in('MKAV','MKWH','MKAP','MKWA','MKSA','MKCS','MKSL') ";
               
            tallyCrtonApp = "select sum(c.qty) qty, to_char(trunc(sum(trunc(c.cts,2)),2),'99999999990.99') cts,c.stt\n" + 
            "from mstk c,stk_dtl d,stk_dtl e,mprp md,mprp ms\n" + 
            "where c.idn=d.mstk_idn and c.idn=e.mstk_idn and d.mprp='DEPT' and d.grp=1  and e.mprp='STK_LOC' and e.grp=1 and d.mprp=md.prp and e.mprp=ms.prp\n" + conQ+ sttQ+
            " and c.pkt_ty='NR'  and decode(ms.dta_typ, 'C', e.val, 'N', to_char(e.num), 'D', to_char(e.dte, 'dd-Mon-rrrr'), e.txt) <> 'NA'\n";

                ary =new ArrayList();
                ary.add(dept);
            if(!stkloc.equals("") && !stkloc.equals("TOTAL")){
                tallyCrtonApp = tallyCrtonApp+" and decode(ms.dta_typ, 'C', e.val, 'N', to_char(e.num), 'D', to_char(e.dte, 'dd-Mon-rrrr'), e.txt) = ?" ;
                ary.add(stkloc);
                
            }
                tallyCrtonApp+=" group by c.stt\n" + 
                "order by 2 desc ";
              ArrayList outLst = db.execSqlLst("tallyCrt", tallyCrtonApp, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                   pktDtl = new HashMap();
                   pktDtl.put("qty" ,(String)util.nvl(rs.getString("qty")));
                   pktDtl.put("cts" ,(String)util.nvl(rs.getString("cts")));
                   pktDtl.put("stt" ,(String)util.nvl(rs.getString("stt")));
                   crtList.add(pktDtl);
             }
            rs.close();
            pst.close();
                
                tallyCrtonApp ="select sum(c.qty) qty, to_char(trunc(sum(trunc(c.cts,2)),2),'99999999990.99') cts,'TOTAL' stt\n" + 
                "from mstk c,stk_dtl d,stk_dtl e,mprp md,mprp ms\n" + 
                "where c.idn=d.mstk_idn and c.idn=e.mstk_idn and d.mprp='DEPT' and d.grp=1  and e.mprp='STK_LOC' and e.grp=1 and d.mprp=md.prp and e.mprp=ms.prp\n" +conQ+ sttQ+
                " and c.pkt_ty='NR'  and decode(ms.dta_typ, 'C', e.val, 'N', to_char(e.num), 'D', to_char(e.dte, 'dd-Mon-rrrr'), e.txt) <> 'NA'\n" ; 
                  ary =new ArrayList();
                  ary.add(dept);
                if(!stkloc.equals("") && !stkloc.equals("TOTAL")){
                    tallyCrtonApp = tallyCrtonApp+" and decode(ms.dta_typ, 'C', e.val, 'N', to_char(e.num), 'D', to_char(e.dte, 'dd-Mon-rrrr'), e.txt) = ?" ;
                    ary.add(stkloc);
                }
                tallyCrtonApp+="order by 2 desc ";
              outLst = db.execSqlLst("tallyCrt", tallyCrtonApp, ary);
               pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                       pktDtl = new HashMap();
                       pktDtl.put("qty" ,(String)util.nvl(rs.getString("qty")));
                       pktDtl.put("cts" ,(String)util.nvl(rs.getString("cts")));
                       pktDtl.put("stt" ,(String)util.nvl(rs.getString("stt")));
                       crtList.add(pktDtl);
                 }
                rs.close();
                pst.close();
                req.setAttribute("crtList",crtList);
                req.setAttribute("pktDtl", pktDtl);
                req.setAttribute("dept", dept);
                req.setAttribute("status", status);
                req.setAttribute("stkloc", stkloc);
                

                util.updAccessLog(req,res,"Stock Tally", "TallyCRTonApproval end"); 
            return am.findForward("TallyCRTonApproval");
            }
        }
    public ActionForward TallyCRTonApprovalLoc(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                util.updAccessLog(req,res,"Stock Tally", "TallyCRTonApprovalLoc start"); 
            StockTallyForm udf = (StockTallyForm)af;
            HashMap pktDtl = new HashMap();
            ArrayList crtList = new ArrayList();
            ArrayList ary =new ArrayList();
            String dept=util.nvl(req.getParameter("dept"));
            String stt=util.nvl(req.getParameter("stt"));
            String status=util.nvl(req.getParameter("status"));
            String conQ=" and decode(md.dta_typ, 'C', d.val, 'N', to_char(d.num), 'D', to_char(d.dte, 'dd-Mon-rrrr'), d.txt)=? ";
            String sttQ=" ";
            if(status.equals("IS"))
            sttQ=" and c.stt in('MKIS','MKEI','SHIS','MKKS_IS','MKOS_IS') ";  
            if(status.equals("AP"))
            sttQ=" and c.stt in('MKAV','MKWH','MKAP','MKWA','MKSA','MKCS','MKSL') ";
                if(!stt.equals("TOTAL") &&  !stt.equals("")){
                    sttQ=" and c.stt in('"+stt+"')" ;
                }
            String tallyCrtonApp = "select sum(c.qty) qty, to_char(trunc(sum(trunc(c.cts,2)),2),'99999999990.99') cts,decode(ms.dta_typ, 'C', e.val, 'N', to_char(e.num), 'D', to_char(e.dte, 'dd-Mon-rrrr'), e.txt) stkloc\n" + 
            "from mstk c,stk_dtl d,stk_dtl e,mprp md,mprp ms\n" + 
            "where c.idn=d.mstk_idn and c.idn=e.mstk_idn and d.mprp='DEPT' and d.grp=1  and e.mprp='STK_LOC' and e.grp=1 and d.mprp=md.prp and e.mprp=ms.prp\n" +sttQ+ conQ+
            "and c.pkt_ty='NR'  and decode(ms.dta_typ, 'C', e.val, 'N', to_char(e.num), 'D', to_char(e.dte, 'dd-Mon-rrrr'), e.txt) <> 'NA'\n" + 
            "group by decode(ms.dta_typ, 'C', e.val, 'N', to_char(e.num), 'D', to_char(e.dte, 'dd-Mon-rrrr'), e.txt)\n" + 
            "order by 2 desc ";
                ary =new ArrayList();
                ary.add(dept);
              ArrayList outLst = db.execSqlLst("tallyCrt", tallyCrtonApp, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                   pktDtl = new HashMap();
                   pktDtl.put("qty" ,(String)util.nvl(rs.getString("qty")));
                   pktDtl.put("cts" ,(String)util.nvl(rs.getString("cts")));
                   pktDtl.put("stkloc" ,(String)util.nvl(rs.getString("stkloc")));
                   crtList.add(pktDtl);
             }
            rs.close();
                pst.close();
            
                tallyCrtonApp = "select sum(c.qty) qty, to_char(trunc(sum(trunc(c.cts,2)),2),'99999999990.99') cts,'TOTAL' stkloc\n" + 
                            "from mstk c,stk_dtl d,stk_dtl e,mprp md,mprp ms\n" + 
                            "where c.idn=d.mstk_idn and c.idn=e.mstk_idn and d.mprp='DEPT' and d.grp=1  and e.mprp='STK_LOC' and e.grp=1 and d.mprp=md.prp and e.mprp=ms.prp\n" +sttQ+conQ+
                            "and c.pkt_ty='NR'  and decode(ms.dta_typ, 'C', e.val, 'N', to_char(e.num), 'D', to_char(e.dte, 'dd-Mon-rrrr'), e.txt) <> 'NA'\n" + 
                            "order by 2 desc ";
                                ary =new ArrayList();
                                ary.add(dept);
               outLst = db.execSqlLst("tallyCrt", tallyCrtonApp, ary);
              pst = (PreparedStatement)outLst.get(0);
              rs = (ResultSet)outLst.get(1);
                            while(rs.next()){
                                   pktDtl = new HashMap();
                                   pktDtl.put("qty" ,(String)util.nvl(rs.getString("qty")));
                                   pktDtl.put("cts" ,(String)util.nvl(rs.getString("cts")));
                                   pktDtl.put("stkloc" ,(String)util.nvl(rs.getString("stkloc")));
                                   crtList.add(pktDtl);
                             }
                 rs.close();
                pst.close();
                
                req.setAttribute("crtList",crtList);
                req.setAttribute("pktDtl", pktDtl);
                req.setAttribute("dept", dept);
                req.setAttribute("stt", stt);
                req.setAttribute("status", status);      
                util.updAccessLog(req,res,"Stock Tally", "TallyCRTonApprovalLoc end");
            return am.findForward("TallyCRTonApprovalLoc");
            }
        }
        public ActionForward loadTallyhistory(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Stock Tally", "loadTallyhistory start");
         StockTallyForm udf = (StockTallyForm)af;
         udf.reset();
         TallyStockstatus(req);
            session.setAttribute("grpList",new ArrayList());
            session.setAttribute("pktDtl", new HashMap());
            session.setAttribute("seqLst", new ArrayList());
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_TALLY");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("STOCK_TALLY");
            allPageDtl.put("STOCK_TALLY",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Stock Tally", "loadTallyhistory end");
       return am.findForward("history");
        }
    }
    public ActionForward history(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Stock Tally", "history start");
        StockTallyForm udf = (StockTallyForm)af;
        String mstkStt = "";
         String[] stt =  udf.getSttValLst();
        String issdte = util.nvl((String)udf.getValue("issdte"));
        String rtndte = util.nvl((String)udf.getValue("rtndte"));
        String seq = util.nvl((String)udf.getValue("seq"));
        String conQ="";
        udf.reset();
        
        ArrayList seqLst = new ArrayList();
        String str = "";
        ArrayList ary = new ArrayList();
        if(!seq.equals("") && !seq.equals("0")){
        str = " and b.seq_idn = ? ";
        ary.add(seq);
        req.setAttribute("seqNo", seq);
        }
        if(!issdte.equals("")){
            conQ = conQ+" and trunc(b.iss_dte) between to_date('"+issdte+"' , 'dd-mm-yyyy') and to_date('"+issdte+"' , 'dd-mm-yyyy') ";
        }
        if(!rtndte.equals("")){
            conQ = conQ+" and trunc(b.rtn_dte) between to_date('"+rtndte+"' , 'dd-mm-yyyy') and to_date('"+rtndte+"' , 'dd-mm-yyyy') ";
        }
            if(stt!=null){
                for(int i=0;i<stt.length;i++){
                    mstkStt=mstkStt+","+stt[i];
                }
                mstkStt=mstkStt.replaceFirst(",", "");
                if(!mstkStt.equals("")){
                mstkStt = util.getVnm(mstkStt);
                conQ=conQ+" and stk_stt in("+mstkStt+") ";
                }
            }
               String sqlTally = " select sum(a.qty) qty, trunc(sum(trunc(a.cts,2)),2) cts, b.stt, b.stk_stt "+
                                 " from mstk a, stk_tly b where a.idn = b.stk_idn "+conQ+str+
                                 " group by b.stt, b.stk_stt ";
                HashMap pktDtl = new HashMap();
                ArrayList grpList = new ArrayList();
          ArrayList outLst = db.execSqlLst("sql", sqlTally, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
                try {
                    while (rs.next()) {
                        String qty = util.nvl(rs.getString("qty"));
                        String cts = util.nvl(rs.getString("cts"));
                        String grp1 = util.nvl(rs.getString("stk_stt"));
                        String tlyStt = util.nvl(rs.getString("stt"));
                        pktDtl.put(grp1 + "_" + tlyStt + "_Q", qty);
                        pktDtl.put(grp1 + "_" + tlyStt + "_C", cts);
                        if (!grpList.contains(grp1))
                            grpList.add(grp1);
                    }
                
                    rs.close();
                    pst.close();
                if(grpList.size()>0){
                    ary = new ArrayList();
                    String tallySeq = " select distinct seq_idn from stk_tly b where 1=1 "+conQ+" order by seq_idn desc";
                  outLst = db.execSqlLst("tallySeq", tallySeq, ary);
                   pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                    while(rs.next()){
                        seqLst.add(rs.getString("seq_idn"));
                    }
                    
                    rs.close();
                    pst.close();
                }
                } catch (SQLException sqle) {
                    // TODO: Add catch code
                    sqle.printStackTrace();
                }
                session.setAttribute("grpList",grpList);
                session.setAttribute("pktDtl", pktDtl);
                session.setAttribute("seqLst", seqLst);
        req.setAttribute("view", "Y");
        req.setAttribute("issdte",issdte);
        req.setAttribute("rtndte",rtndte);
        udf.setValue("issdte",issdte);
        udf.setValue("rtndte",rtndte);
        udf.setValue("seq",seq);
            util.updAccessLog(req,res,"Stock Tally", "history end");
        return am.findForward("history");
        }
    }
    public ActionForward TallyCRTHistory(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        util.updAccessLog(req,res,"Stock Tally", "TallyCRTHistory start");
        StockTallyForm udf = (StockTallyForm)af;
        HashMap pktDtl = new HashMap();
        ArrayList crtList = new ArrayList();
        String loadSeq = util.nvl(req.getParameter("loadSeq"));
        String stt = util.nvl(req.getParameter("stt"));
        String issdte = util.nvl(req.getParameter("issdte"));
        String rtndte = util.nvl(req.getParameter("rtndte"));
        String conQ="";
        String str ="";
        ArrayList ary = new ArrayList();
        ary.add(stt);
        if(!loadSeq.equals("")  && !loadSeq.equals("0")){
            str = " and b.seq_idn = ? ";
            ary.add(loadSeq);
        }
        if(!issdte.equals("")){
            conQ = conQ+" and trunc(b.iss_dte) between to_date('"+issdte+"' , 'dd-mm-yyyy') and to_date('"+issdte+"' , 'dd-mm-yyyy') ";
        }
        if(!rtndte.equals("")){
            conQ = conQ+" and trunc(b.rtn_dte) between to_date('"+rtndte+"' , 'dd-mm-yyyy') and to_date('"+rtndte+"' , 'dd-mm-yyyy') ";
        }
        String tallyCrt = " select sum(a.qty) qty, trunc(sum(trunc(a.cts,2)),2) cts, b.flg1 stt , rd.dsc , b.stt tlyStt  from mstk a, stk_tly b , rule_dtl rd, mrule mr "+
                          " where a.idn = b.stk_idn and b.stk_stt=? and b.flg1 = rd.chr_fr and rd.rule_idn = mr.rule_idn \n" + 
                          " and mr.mdl = 'JFLEX' and mr.nme_rule = 'STK_TTY' and rd.dsc <> 'DLV' and rd.til_dte is null "+str+conQ+
                         " group by b.flg1 , rd.dsc , b.stt ";
        
          ArrayList outLst = db.execSqlLst("tallyCrt", tallyCrt, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
               String cnt = util.nvl(rs.getString("qty"));
               String dsc = util.nvl(rs.getString("dsc"));
               String cts =  util.nvl(rs.getString("cts"));
               String stkStt = util.nvl(rs.getString("Stt"));
               String tlyStt = util.nvl(rs.getString("tlyStt"));
               pktDtl.put(stkStt+"_"+tlyStt+"_Q" , cnt);
               pktDtl.put(stkStt+"_"+tlyStt+"_C" , cts);
               pktDtl.put(stkStt , dsc);
               if(!crtList.contains(stkStt))
                   crtList.add(stkStt);
         }
        rs.close();
            pst.close();
            req.setAttribute("crtList",crtList);
            req.setAttribute("pktDtl", pktDtl);
        req.setAttribute("issdte",issdte);
        req.setAttribute("rtndte",rtndte);
        req.setAttribute("seqNo",loadSeq);
            util.updAccessLog(req,res,"Stock Tally", "TallyCRTHistory end");
        return am.findForward("TallyCRTHistory");
        }
    }
    public ActionForward dayWisehistory(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Stock Tally", "dayWisehistory start");
        StockTallyForm udf = (StockTallyForm)af;
        String reqstt = util.nvl(req.getParameter("reqstt"));
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_TALLY");
        HashMap dbinfo = info.getDmbsInfoLst();
        String locval = (String)dbinfo.get("LOC");
        String hisdays=util.nvl((String)((HashMap)((ArrayList)pageDtl.get("HISDAYS")).get(0)).get("dflt_val"),"7");
        String conQ=" and a.idn=b1.mstk_idn and b1.grp=1 and b1.mprp='"+locval+"' and trunc(b.iss_dte) between trunc(sysdate) -"+hisdays+" and trunc(sysdate) ";
        ArrayList ary = new ArrayList();
        HashMap pktDtl = new HashMap();
        ArrayList dteList = new ArrayList();
        ArrayList grpList = new ArrayList();
        ArrayList reqsttList = new ArrayList();
        ArrayList locList = new ArrayList();
        reqsttList.add("IS");reqsttList.add("RT");
        if(reqstt.equals("RT")){
                reqsttList = new ArrayList();
                reqsttList.add("RT");
        }
               String sqlTally = " select sum(a.qty) qty, trunc(sum(trunc(a.cts,2)),2) cts, b.stt, b.stk_stt,to_char(b.iss_dte , 'dd-mm-rrrr') dte,b1.val loc\n" + 
               "from mstk a, stk_tly b,stk_dtl b1 \n" + 
               "where a.idn = b.stk_idn \n" + conQ+
               "group by b.stt, b.stk_stt , to_char(b.iss_dte , 'dd-mm-rrrr'),b1.val order by 5 ";
          ArrayList outLst = db.execSqlLst("sql", sqlTally, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
                try {
                    while (rs.next()) {
                        String loc = util.nvl(rs.getString("loc"));
                        String qty = util.nvl(rs.getString("qty"));
                        String cts = util.nvl(rs.getString("cts"));
                        String grp = util.nvl(rs.getString("stk_stt"));
                        String tlyStt = util.nvl(rs.getString("stt"));
                        String dte = util.nvl(rs.getString("dte"));
                        pktDtl.put(loc+ "_" +dte+ "_" +grp + "_" + tlyStt + "_Q", qty);
                        pktDtl.put(loc+ "_" +dte+ "_" +grp + "_" + tlyStt + "_C", cts);
                        if (!dteList.contains(dte))
                            dteList.add(dte);
                        if (!locList.contains(loc))
                            locList.add(loc);
                    }
                    rs.close();
                    pst.close();
                    sqlTally = " select sum(a.qty) qty, trunc(sum(trunc(a.cts,2)),2) cts, b.stt,to_char(b.iss_dte , 'dd-mm-rrrr') dte,b1.val loc\n" + 
                    "from mstk a, stk_tly b,stk_dtl b1 \n" + 
                    "where a.idn = b.stk_idn \n" + conQ+
                    "group by b.stt,to_char(b.iss_dte , 'dd-mm-rrrr'),b1.val\n" + 
                    "order by 4 ";
                 outLst = db.execSqlLst("sql", sqlTally, ary);
                   pst = (PreparedStatement)outLst.get(0);
                   rs = (ResultSet)outLst.get(1);
                    while (rs.next()) {
                        String loc = util.nvl(rs.getString("loc"));
                        String qty = util.nvl(rs.getString("qty"));
                        String cts = util.nvl(rs.getString("cts"));
                        String tlyStt = util.nvl(rs.getString("stt"));
                        String dte = util.nvl(rs.getString("dte"));
                        pktDtl.put(loc+ "_" +dte+ "_" + tlyStt + "_Q", qty);
                        pktDtl.put(loc+ "_" +dte+ "_" + tlyStt + "_C", cts);
                    }
                    rs.close();
                    pst.close();
                    sqlTally = " select sum(a.qty) qty, trunc(sum(trunc(a.cts,2)),2) cts, b.stt, b.stk_stt,c.srt_fr,b1.val loc\n" + 
                    "from mstk a, stk_tly b,stk_dtl b1,rule_dtl c,mrule d \n" + 
                    "where a.idn = b.stk_idn \n" + conQ+
                    "and c.rule_idn = d.rule_idn and d.mdl = 'JFLEX' and d.nme_rule = 'STK_TTY' and b.stk_stt=dsc\n" + 
                    "group by b.stt, b.stk_stt,c.srt_fr,b1.val\n" + 
                    "union\n" + 
                    "select sum(a.qty) qty, trunc(sum(trunc(a.cts,2)),2) cts, b.stt, 'TTL' stk_stt,10000 srt_fr,b1.val loc\n" + 
                    "from mstk a, stk_tly b,stk_dtl b1 \n" + 
                    "where a.idn = b.stk_idn \n" + conQ+
                    "group by b.stt,b1.val\n" + 
                    "order by 5";
                  outLst = db.execSqlLst("sql", sqlTally, ary);
                    pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                    while (rs.next()) {
                        String loc = util.nvl(rs.getString("loc"));
                        String qty = util.nvl(rs.getString("qty"));
                        String cts = util.nvl(rs.getString("cts"));
                        String grp = util.nvl(rs.getString("stk_stt"));
                        String tlyStt = util.nvl(rs.getString("stt"));
                        pktDtl.put(loc+ "_" +grp + "_" + tlyStt + "_Q", qty);
                        pktDtl.put(loc+ "_" +grp + "_" + tlyStt + "_C", cts);
                        if (!grpList.contains(grp))
                            grpList.add(grp);
                    }
                    rs.close();
                    pst.close();
                } catch (SQLException sqle) {
                    // TODO: Add catch code
                    sqle.printStackTrace();
                }
                req.setAttribute("dteList",dteList);
                req.setAttribute("pktDtl", pktDtl);
                req.setAttribute("grpList", grpList);
                req.setAttribute("reqsttList", reqsttList);
                req.setAttribute("locList", locList);
            util.updAccessLog(req,res,"Stock Tally", "dayWisehistory end");
        return am.findForward("dayWisehistory");
        }
    }
    
    public ActionForward PktDtlHistory(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Stock Tally", "PktDtlHistory start");
        String dfStt = util.nvl(req.getParameter("dfstt"));
        String mstkStt = util.nvl(req.getParameter("mstkstt"));
        String issTyp = util.nvl(req.getParameter("issTyp"));
        String loadSeq = util.nvl(req.getParameter("loadSeq"));
        String issdte = util.nvl(req.getParameter("issdte"));
        String rtndte = util.nvl(req.getParameter("rtndte"));
        int ct = db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
        ArrayList ary = new ArrayList();
        String pktDtl =" Insert into gt_srch_rslt (stk_idn, vnm ,cts,stt, flg,sk1,rmk ) " + 
                       " select distinct a.stk_idn , a.vnm ,b.cts,b.stt, 'Z',b.sk1,b.tfl3 from stk_tly a,mstk b  " + 
                       " where 1=1 and a.stk_idn=b.idn ";
        if(!dfStt.equals("")){
            pktDtl = pktDtl+" and a.stk_stt= ?" ;
            ary.add(dfStt);
        }
        if(!mstkStt.equals("")){
            pktDtl = pktDtl+" and a.flg1 = ?" ;
            ary.add(mstkStt);
        }
        if(!issTyp.equals("ALL")){
            pktDtl = pktDtl+" and a.stt = ?" ;
            ary.add(issTyp);
        }
        if(!loadSeq.equals("")  && !loadSeq.equals("0")){
            pktDtl = pktDtl+" and a.seq_idn = ?" ;
            ary.add(loadSeq);
        }
        if(!issdte.equals("")){
            pktDtl = pktDtl+" and trunc(a.iss_dte) between to_date('"+issdte+"' , 'dd-mm-yyyy') and to_date('"+issdte+"' , 'dd-mm-yyyy') ";
        }
        if(!rtndte.equals("")){
            pktDtl = pktDtl+" and trunc(a.rtn_dte) between to_date('"+rtndte+"' , 'dd-mm-yyyy') and to_date('"+rtndte+"' , 'dd-mm-yyyy') ";
        }
         ct = db.execUpd("pktDtl", pktDtl, ary);
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("RFID_VW");
         ct = db.execCall(" Srch Prp ", pktPrp, ary);
            util.updAccessLog(req,res,"Stock Tally", "PktDtlHistory end");
     return SearchResult(am, af, req, res);
        }
    }
    public void TallyStockstatus(HttpServletRequest req)throws Exception{
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        Connection conn=info.getCon();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap grp3List = new HashMap();
        String stkStt =  "             select  distinct a.dsc  , a.chr_fr , a.srt_fr  from rule_dtl a, mrule b where a.rule_idn = b.rule_idn \n" + 
        " and b.mdl = 'JFLEX' and b.nme_rule = 'STK_TTY_GRP' and a.dsc <> 'DLV' and a.til_dte is null order by a.srt_fr , a.dsc , a.chr_fr ";
        
      ArrayList outLst = db.execSqlLst("stkStt", stkStt, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        ArrayList sttList = new ArrayList();
        while(rs.next()){
            ArrayList sttDtl = new ArrayList();
            sttDtl.add(rs.getString("chr_fr"));
            sttDtl.add(rs.getString("dsc"));
            sttList.add(sttDtl);
            grp3List.put(rs.getString("dsc"), rs.getString("chr_fr"));
        }
        rs.close();
        pst.close();
        session.setAttribute("grp3List", grp3List);
        session.setAttribute("sttLst",sttList);
    }
    public ActionForward PktDtl(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Stock Tally", "PktDtl start");
        String dfStt = util.nvl(req.getParameter("dfstt"));
        String mstkStt = util.nvl(req.getParameter("mstkstt"));
        String issTyp = util.nvl(req.getParameter("issTyp"));
        String loadSeq = util.nvl(req.getParameter("loadSeq"));
        int ct = db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
        ArrayList ary = new ArrayList();
        String pktDtl =" Insert into gt_srch_rslt (stk_idn, vnm ,cts,stt,dsp_stt, flg,sk1,rmk ) " + 
                       " select distinct a.stk_idn , a.vnm ,b.cts,b.stt,a.flg1, 'Z',b.sk1,b.tfl3 from stk_tly a,mstk b " + 
                       " where  a.stk_idn=b.idn and trunc(a.iss_dte) = trunc(sysdate) ";
        if(!dfStt.equals("") && !dfStt.equals("TOTAL")){
            pktDtl = pktDtl+" and a.stk_stt= ?" ;
            ary.add(dfStt);
        }
        if(!mstkStt.equals("")){
            pktDtl = pktDtl+" and a.flg1 = ?" ;
            ary.add(mstkStt);
        }
        if(!issTyp.equals("ALL")){
            pktDtl = pktDtl+" and a.stt = ?" ;
            ary.add(issTyp);
        }
        if(!loadSeq.equals("")  && !loadSeq.equals("0")){
            pktDtl = pktDtl+" and a.seq_idn = ?" ;
            ary.add(loadSeq);
        }
         ct = db.execUpd("pktDtl", pktDtl, ary);
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("RFID_VW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        ct = db.execCall("update gt","DP_UPD_GT_BYR", new ArrayList());
            util.updAccessLog(req,res,"Stock Tally", "PktDtl end");
     return SearchResult(am, af, req, res);
        }
    }
    public ActionForward PktDtlOnApprv(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                util.updAccessLog(req,res,"Stock Tally", "PktDtlOnApprv start");
            String stt = util.nvl(req.getParameter("stt"));
            String dept = util.nvl(req.getParameter("dept"));   
            String status = util.nvl(req.getParameter("status")); 
            String stkloc = util.nvl(req.getParameter("stkloc"));
                                                          
            int ct = db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
            ArrayList ary = new ArrayList();
            String pktDtl ="Insert into gt_srch_rslt (byr,emp,srch_id,stk_idn, vnm ,cts,qty,stt, flg,sk1,rmk)\n" + 
            "select '','','' memoid,c.idn,c.vnm,c.cts,c.qty,c.stt,'Z',c.sk1,c.tfl3\n" + 
            "from mstk c,stk_dtl d,stk_dtl e,mprp md,mprp ms\n" + 
            "where c.idn=d.mstk_idn and c.idn=e.mstk_idn and d.mprp='DEPT' and d.grp=1 and e.mprp='STK_LOC' and e.grp=1 and d.mprp=md.prp and e.mprp=ms.prp\n" +       
            "and decode(ms.dta_typ, 'C', e.val, 'N', to_char(e.num), 'D', to_char(e.dte, 'dd-Mon-rrrr'), e.txt) <> 'NA'\n" + 
            "and c.pkt_ty='NR'";
          
            if(!stt.equals("") && !stt.equals("TOTAL")){
            pktDtl = pktDtl+" and c.stt = ?" ;
            ary.add(stt);
            }else{
                
                if(status.equals("IS"))
                pktDtl=pktDtl+" and c.stt in('MKIS','MKEI','SHIS','MKKS_IS','MKOS_IS') ";  
                if(status.equals("AP"))
                pktDtl=pktDtl+" and c.stt in('MKAV','MKWH','MKAP','MKWA','MKSA','MKCS','MKSL') ";
                }
                
            pktDtl = pktDtl+" and decode(md.dta_typ, 'C', d.val, 'N', to_char(d.num), 'D', to_char(d.dte, 'dd-Mon-rrrr'), d.txt) = ?" ;
            ary.add(dept);
            if(!stkloc.equals("") && !stkloc.equals("TOTAL")){
            pktDtl = pktDtl+" and decode(ms.dta_typ, 'C', e.val, 'N', to_char(e.num), 'D', to_char(e.dte, 'dd-Mon-rrrr'), e.txt) = ?" ;
            ary.add(stkloc);
            }
            ct = db.execUpd("pktDtl", pktDtl, ary);
            
            String pktPrp = "pkgmkt.pktPrp(0,?)";
            ary = new ArrayList();
            ary.add("RFID_VW");
             ct = db.execCall(" Srch Prp ", pktPrp, ary);
            ct = db.execCall("update gt","DP_UPD_GT_BYR", new ArrayList());
                util.updAccessLog(req,res,"Stock Tally", "PktDtlOnApprv end");
         return SearchResultOnApprv(am, af, req, res);
            }
        }
    public ActionForward PktDtlOnApprvLoc(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                util.updAccessLog(req,res,"Stock Tally", "PktDtlOnApprvLoc start");
            String stt = util.nvl(req.getParameter("stt"));
            String dept = util.nvl(req.getParameter("dept"));  
            String stkloc = util.nvl(req.getParameter("stkloc"));
            String status = util.nvl(req.getParameter("status"));  

            int ct = db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
            ArrayList ary = new ArrayList();
            String pktDtl ="Insert into gt_srch_rslt (byr,emp,srch_id,stk_idn, vnm ,cts,qty,stt, flg,sk1,rmk)\n" + 
            "select '','','' memoid,c.idn,c.vnm,c.cts,c.qty,c.stt,'Z',c.sk1,c.tfl3\n" + 
            "from mstk c,stk_dtl d,stk_dtl e,mprp md,mprp ms\n" + 
            "where c.idn=d.mstk_idn and c.idn=e.mstk_idn and d.mprp='DEPT' and d.grp=1 and e.mprp='STK_LOC' and e.grp=1 and d.mprp=md.prp and e.mprp=ms.prp\n" +       
            "and decode(ms.dta_typ, 'C', e.val, 'N', to_char(e.num), 'D', to_char(e.dte, 'dd-Mon-rrrr'), e.txt) <> 'NA'\n" + 
            "and c.pkt_ty='NR'";
          
            if(!stt.equals("") && !stt.equals("TOTAL")){
            pktDtl = pktDtl+" and c.stt = ?" ;
            ary.add(stt);
                }else{
                    
                    if(status.equals("IS"))
                    pktDtl=pktDtl+" and c.stt in('MKIS','MKEI','SHIS','MKKS_IS') ";  
                    if(status.equals("AP"))
                    pktDtl=pktDtl+" and c.stt in('MKAV','MKWH','MKAP','MKWA','MKSA','MKCS','MKSL') ";
                    }
                
            pktDtl = pktDtl+" and decode(md.dta_typ, 'C', d.val, 'N', to_char(d.num), 'D', to_char(d.dte, 'dd-Mon-rrrr'), d.txt) = ?" ;
            ary.add(dept);
                
                if(!stkloc.equals("") && !stkloc.equals("TOTAL")){
                pktDtl = pktDtl+" and decode(ms.dta_typ, 'C', e.val, 'N', to_char(e.num), 'D', to_char(e.dte, 'dd-Mon-rrrr'), e.txt) = ?" ;
                ary.add(stkloc);
                }
            ct = db.execUpd("pktDtl", pktDtl, ary);
            
            String pktPrp = "pkgmkt.pktPrp(0,?)";
            ary = new ArrayList();
            ary.add("RFID_VW");
            ct = db.execCall(" Srch Prp ", pktPrp, ary);
            ct = db.execCall("update gt","DP_UPD_GT_BYR", new ArrayList());
            util.updAccessLog(req,res,"Stock Tally", "PktDtlOnApprvLoc end");
         return SearchResultOnApprv(am, af, req, res);
            }
        }
    
    public ActionForward blankrfidpackets(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Stock Tally", "PktDtl start");
        String loadSeq = util.nvl(req.getParameter("loadSeq"));
        int ct = db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
        ArrayList ary = new ArrayList();
        String pktDtl =" Insert into gt_srch_rslt (stk_idn, vnm ,cts,stt,dsp_stt, flg,sk1,rmk ) " + 
                       " select distinct a.stk_idn , a.vnm ,b.cts,b.stt,a.flg1, 'Z',b.sk1,b.tfl3 from stk_tly a,mstk b " + 
                       " where  a.stk_idn=b.idn and b.tfl3 is null and trunc(a.iss_dte) = trunc(sysdate) ";
        if(!loadSeq.equals("")  && !loadSeq.equals("0")){
            pktDtl = pktDtl+" and a.seq_idn = ?" ;
            ary.add(loadSeq);
        }
         ct = db.execUpd("pktDtl", pktDtl, ary);
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("RFID_VW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        ct = db.execCall("update gt","DP_UPD_GT_BYR", new ArrayList());
            util.updAccessLog(req,res,"Stock Tally", "PktDtl end");
     return SearchResult(am, af, req, res);
        }
    }
    public ActionForward PktDtlExtra(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                util.updAccessLog(req,res,"Stock Tally", "PktDtlExtra start");
            GenericInterface genericInt = new GenericImpl();
            String stt = util.nvl(req.getParameter("stt"));
            String box = util.nvl(req.getParameter("box"));
            String loadSeq = util.nvl(req.getParameter("loadSeq"));
            HashMap dbinfo = info.getDmbsInfoLst();
            String BOX_RFID = (String)dbinfo.get("BOX_RFID");
            ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "rfidPrpLst", "RFID_VW");
            int ct = db.execUpd("delete gt", "delete from gt_srch_rslt ", new ArrayList());
            ArrayList ary = new ArrayList();
            String pktDtl =" Insert into gt_srch_rslt (stk_idn,vnm ,cts,stt, flg,sk1,rmk) \n" + 
            "            select distinct b.idn , b.vnm ,b.cts,b.stt, a.flg,b.sk1,b.tfl3 from stk_tly_nf a,mstk b \n" + 
            "            where  1 = 1 and ( b.vnm=a.ref_idn or b.tfl3 =a.ref_idn) and trunc(a.rtn_dte) = trunc(sysdate) ";
            if(stt.equals("NF")){
            int indexBX = vwPrpLst.indexOf(BOX_RFID)+1;
            String lbPrp = util.nvl(util.prpsrtcolumn("prp",indexBX),"prp_001");
                    pktDtl =" Insert into gt_srch_rslt (stk_idn,vnm,flg,rmk,"+lbPrp+") \n" + 
                                "            select distinct replace(a.ref_idn,'-',''),a.ref_idn,a.flg,a.ref_idn,box_id from stk_tly_nf a \n" + 
                                "            where  1 = 1 and trunc(a.rtn_dte) = trunc(sysdate) ";
            
            }
            if(stt.equals("RT")){
                pktDtl =" Insert into gt_srch_rslt (stk_idn,vnm ,cts,stt,dsp_stt, flg,sk1,rmk) \n" + 
                "select distinct b.idn , b.vnm ,b.cts,b.stt,c.flg1, a.flg,b.sk1,b.tfl3 \n" + 
                "from stk_tly_nf a,mstk b,stk_tly c\n" + 
                "where  1 = 1 and c.seq_idn=a.seq_idn \n" + 
                "and (c.vnm=a.ref_idn or c.tfl3 =a.ref_idn) and ( b.vnm=a.ref_idn or b.tfl3 =a.ref_idn) and trunc(a.rtn_dte) = trunc(sysdate) ";
                
            }
            if(!stt.equals("")){
                pktDtl = pktDtl+" and a.flg =?" ;
                ary.add(stt);
            }
                if(!loadSeq.equals("") && !loadSeq.equals("0")){
                    pktDtl = pktDtl+" and a.seq_idn =?" ;
                    ary.add(loadSeq);
                }
                if(!box.equals("") && !box.equals("TOTAL")){
                    pktDtl = pktDtl+" and a.box_id =?" ;
                    ary.add(box);
                }
            ct = db.execUpd("pktDtl", pktDtl, ary);
            if(!stt.equals("NF")){
            String pktPrp = "pkgmkt.pktPrp(0,?)";
            ary = new ArrayList();
            ary.add("RFID_VW");
             ct = db.execCall(" Srch Prp ", pktPrp, ary);
            }
                util.updAccessLog(req,res,"Stock Tally", "PktDtlExtra end");
         return SearchResult(am, af, req, res);
            }
        }
    public ActionForward SearchResult(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
      GenericInterface genericInt = new GenericImpl();
      String stt = util.nvl(req.getParameter("stt"));
      String issTyp = util.nvl(req.getParameter("issTyp"));
      String gtFlg = "'EX','RT'";
 
      if(stt.equals("EX")) 
          gtFlg = "'EX'";
          
       if(stt.equals("RT")) 
           gtFlg = "'RT'";
       if(stt.equals("NF")) 
       gtFlg = "'NF'";
       if(!issTyp.equals(""))
           gtFlg="'Z'";
      
      ArrayList pktList = new ArrayList();
      ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "rfidPrpLst", "RFID_VW");

              String Flds = "";
              
                         for (int i = 0; i < vwPrpLst.size(); i++) {
                                   String fld = "prp_";
                                   int j = i + 1;
                                   if (j < 10)
                                       fld += "00" + j;
                                   else if (j < 100)
                                       fld += "0" + j;
                                   else if (j > 100)
                                       fld += j;
                         if(!Flds.equals(""))
                                   Flds += ", " + fld;
                         else
                                    Flds =  fld;
                         }



              String  rsltQ =  "  select stk_idn ,vnm ,dsp_stt, stt,cts,sk1,tfl3 ,Customer, " + Flds +
              "  ,max(dte)dte,max(byr_cabin)byr_cabin,max(dsc)dsc" +
              "  from (" +
              "  select a.stk_idn ,  a.vnm ,a.dsp_stt, a.stt,to_char(a.cts,'99999999990.99') cts,a.sk1,a.rmk tfl3,(case when a.dsp_stt='MKSL' then '' else a.byr end) Customer , " +Flds +
              "  ,'' dte,'' byr_cabin,'' dsc  from gt_srch_rslt a " +
              "   where a.flg in ("+gtFlg+") "+
              "  union all " +
              "  select a.stk_idn ,  a.vnm ,a.dsp_stt, a.stt,to_char(a.cts,'99999999990.99') cts,a.sk1,a.rmk tfl3,(case when a.dsp_stt='MKSL' then '' else a.byr end) Customer , " +Flds +
              "  ,to_char(b.dte,'dd-Mon-rrrr') dte,b.byr_cabin,c.dsc  from gt_srch_rslt a ,jan_v b , memo_typ c " +
              "   where a.SRCH_ID=b.idn and b.typ = c.typ and a.flg in ("+gtFlg+") and a.dsp_stt not in ('MKAV','MKSL')  " +
              "   ) Group by stk_idn ,vnm ,dsp_stt, stt,cts,sk1,tfl3 ,Customer,  " + Flds  ;
            
            ArrayList outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
              
              try {
          while(rs.next()) {
            
              String stkIdn = util.nvl(rs.getString("stk_idn"));
            
              HashMap pktPrpMap = new HashMap();
              pktPrpMap.put("currentstt", util.nvl(rs.getString("stt")));
              pktPrpMap.put("iss_stt", util.nvl(rs.getString("dsp_stt")));
              String vnm = util.nvl(rs.getString("vnm"));
              pktPrpMap.put("vnm",vnm);
              pktPrpMap.put("stk_idn",stkIdn);
             
              for(int j=0; j < vwPrpLst.size(); j++){
                   String prp = (String)vwPrpLst.get(j);
                    
                    String fld="prp_";
                    if(j < 9)
                            fld="prp_00"+(j+1);
                    else    
                            fld="prp_0"+(j+1);
                    
                    String val = util.nvl(rs.getString(fld)) ;
                    if(prp.equals("CRTWT"))
                    val = util.nvl(rs.getString("cts"));
                    if (prp.toUpperCase().equals("RFIDCD"))
                    val = util.nvl(rs.getString("tfl3")); 
                      pktPrpMap.put(prp, val);
                       }
                  pktPrpMap.put("customer", util.nvl(rs.getString("customer")));
                                   pktPrpMap.put("memo_type", util.nvl(rs.getString("dsc")));
                                   pktPrpMap.put("date", util.nvl(rs.getString("dte")));
                                   pktPrpMap.put("cabin", util.nvl(rs.getString("byr_cabin"))); 

   
                  pktList.add(pktPrpMap);
              }
          rs.close();
                  pst.close();
      } catch (SQLException sqle) {

          // TODO: Add catch code
          sqle.printStackTrace();
      }

      req.setAttribute("stockList", pktList);
          return am.findForward("loadRSHk");
          }
      }
    
    public ActionForward delete(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
           util.updAccessLog(req,res,"Stock Tally", "delete start");
         StockTallyForm udf = (StockTallyForm)af;
           ArrayList ary=new ArrayList();
           String seq=util.nvl((String)udf.getValue("seq"));
           String conQ="";
           if(!seq.equals("")){
               conQ=" and st.seq_idn=?"; 
               ary.add(seq);
           }
           String updateStk_tly="delete stk_tly_nf st where trunc(st.rtn_dte)=trunc(sysdate) \n"+conQ;
//           "and exists (select 1 from stk_tly st where trunc(st.iss_dte)=trunc(sysdate) "+conQ+" and ( upper(trim(st.vnm)) = upper(trim(nf.ref_idn)) or upper(trim(st.tfl3)) = upper(trim(nf.ref_idn ))))";
           int upCnt = db.execUpd("upda", updateStk_tly, ary);
           
           updateStk_tly="delete stk_tly st where trunc(st.iss_dte)=trunc(sysdate)"+conQ;
           upCnt = db.execUpd("upda", updateStk_tly, ary);
           
//           updateStk_tly="delete stk_tly_nf sf where trunc(sf.rtn_dte)=trunc(sysdate) and sf.flg in('NF','EX')";
//           upCnt = db.execUpd("upda", updateStk_tly, new ArrayList());
           
           TallyStockstatus(req);
           TallyStock(req,"");
           req.setAttribute("RTNMSG", "Deletion Done Sucessfully");
           util.updAccessLog(req,res,"Stock Tally", "delete end");
       return am.findForward("delete");
       }
    }
    public ActionForward SearchResultOnApprv(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
              util.updAccessLog(req,res,"Stock Tally", "SearchResultOnApprv start");
      GenericInterface genericInt = new GenericImpl();
      ArrayList pktList = new ArrayList();
      ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "rfidPrpLst", "RFID_VW");
      String  srchQ =  " select stk_idn ,byr,srch_id,  vnm , stt,to_char(cts,'99999999990.99') cts,sk1,rmk tfl3 ";

      

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

      
      String rsltQ = srchQ + " from gt_srch_rslt  order by byr,sk1,stk_idn,cts";
     
            ArrayList outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
      try {
          while(rs.next()) {
                String  byr =util.nvl(rs.getString("byr"));
               String stkIdn = util.nvl(rs.getString("stk_idn"));
                  String memoid = util.nvl(rs.getString("srch_id"));
            
              HashMap pktPrpMap = new HashMap();
              pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
              String vnm = util.nvl(rs.getString("vnm"));
              pktPrpMap.put("byr",byr); 
              pktPrpMap.put("memoid",memoid);
              pktPrpMap.put("vnm",vnm);
              pktPrpMap.put("stk_idn",stkIdn);
              
              
             
              for(int j=0; j < vwPrpLst.size(); j++){
                   String prp = (String)vwPrpLst.get(j);
                    
                    String fld="prp_";
                    if(j < 9)
                            fld="prp_00"+(j+1);
                    else    
                            fld="prp_0"+(j+1);
                    
                    String val = util.nvl(rs.getString(fld)) ;
                    if(prp.equals("CRTWT"))
                    val = util.nvl(rs.getString("cts"));
                    if (prp.toUpperCase().equals("RFIDCD"))
                    val = util.nvl(rs.getString("tfl3")); 
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
      req.setAttribute("view", "A");
      req.setAttribute("stockList", pktList);
              util.updAccessLog(req,res,"Stock Tally", "SearchResultOnApprv end");
          return am.findForward("loadRSPkt");
          }
      }
    
    
    public void SearchResult(HttpServletRequest req, HttpServletResponse res,String stt)
            throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        Connection conn=info.getCon();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      ArrayList pktList = new ArrayList();
      GenericInterface genericInt = new GenericImpl();
      ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "rfidPrpLst", "RFID_VW");
      ArrayList itemHdr = new ArrayList();
      ArrayList prpDspBlocked = info.getPageblockList();
      itemHdr.add("vnm");
      itemHdr.add("stt");
      itemHdr.add("flg");
      String  srchQ =  " select stk_idn ,  vnm , stt,flg,rmk tfl3,to_char(cts,'99999999990.99') cts,sk1  ";
      
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

      
      String rsltQ = srchQ + " from gt_srch_rslt where flg in ('"+stt+"') order by sk1,stk_idn,cts ";
     
      ArrayList outLst = db.execSqlLst("search Result", rsltQ, new ArrayList());
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
              pktPrpMap.put("flg", util.nvl(rs.getString("flg")));
             
              for(int j=0; j < vwPrpLst.size(); j++){
                   String prp = (String)vwPrpLst.get(j);
                    
                    String fld="prp_";
                    if(j < 9)
                            fld="prp_00"+(j+1);
                    else    
                            fld="prp_0"+(j+1);
                    
                    String val = util.nvl(rs.getString(fld)) ;
                    if(prp.equals("CRTWT"))
                    val = util.nvl(rs.getString("cts"));
                    if (prp.toUpperCase().equals("RFIDCD"))
                    val = util.nvl(rs.getString("tfl3"));
                      pktPrpMap.put(prp, val);
                      if(prpDspBlocked.contains(prp)){
                      }else if(!itemHdr.contains(prp)){
                      itemHdr.add(prp);
                      }
                      }
                            
                  pktList.add(pktPrpMap);
              }
          rs.close();
          pst.close();
      } catch (SQLException sqle) {

          // TODO: Add catch code
          sqle.printStackTrace();
      }
        session.setAttribute("pktList", pktList);
        session.setAttribute("itemHdr",itemHdr);
    }
    public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Stock Tally", "Create Excel");
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "stocktallyreturn"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
        hwb.write(out);
        out.flush();
        out.close();
        return null;
        }
    }
    
    public ActionForward createXLstocktally(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Stock Tally", "createXLstocktally");
    ExcelUtil xlUtil = new ExcelUtil();
    xlUtil.init(db, util, info);
    OutputStream out = res.getOutputStream();
    String CONTENT_TYPE = "getServletContext()/vnd-excel";
    String fileNm = "PacketDtls"+util.getToDteTime()+".xls";
    res.setContentType(CONTENT_TYPE);
    res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
    ArrayList pktList = (ArrayList)session.getAttribute("pktList");
    ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
    HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
    hwb.write(out);
    out.flush();
    out.close();
    return null;
    }
    }
//    public ArrayList GenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("StkTllySrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'STK_TLLY_SRCH' and flg in ('Y','S') order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("StkTllySrch", asViewPrp);
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
    public ActionForward loadRtn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
         util.updAccessLog(req,res,"Stock Tally", "loadRtn start");
         String seq="";
         String setupseq=info.getSeqidn();
         if(!setupseq.equals(""))
         seq=setupseq;
         StockTallyForm udf = (StockTallyForm)af;
         udf.reset();
//         ArrayList rfiddevice = ((ArrayList)info.getRfiddevice() == null)?new ArrayList():(ArrayList)info.getRfiddevice();
//         if(rfiddevice.size() == 0) {
//         util.rfidDevice();    
//         }
         HashMap flgMap=getstktlynf(req,seq);
         req.setAttribute("sttMap", flgMap);
         TallyStockstatus(req);
         TallyStock(req,seq);
          HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
          HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_TALLY");
          if(pageDtl==null || pageDtl.size()==0){
          pageDtl=new HashMap();
          pageDtl=util.pagedef("STOCK_TALLY");
          allPageDtl.put("STOCK_TALLY",pageDtl);
          }
          info.setPageDetails(allPageDtl);
          req.setAttribute("seqNo", seq);
          util.updAccessLog(req,res,"Stock Tally", "loadRtn end");
          return am.findForward("loadRtn");
         }
     }
    
    public ActionForward Return(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Stock Tally", "return start");
        StockTallyForm udf = (StockTallyForm)af;
            HashMap dbinfo = info.getDmbsInfoLst();
            String BOX_RFID = (String)dbinfo.get("BOX_RFID");
        String rtnassign = util.nvl((String)udf.getValue("rtnassign"));  
        String vnm = util.nvl((String)udf.getValue("vnm"));
        String rtnseq = util.nvl((String)udf.getValue("rtnseq"));
        String boxrfidval = util.nvl((String)udf.getValue("boxrfidval"));
            String box = util.nvl((String)udf.getValue("box"));
            db.execUpd("delete", "delete from gt_srch_rslt", new ArrayList());
            db.execUpd("delete", "delete from gt_file_trf", new ArrayList());
            ArrayList dummyLst=new ArrayList();
            String insertGt="";
            ResultSet rs = null;
            ArrayList ary = new ArrayList();
            HashMap prp = info.getPrp();
            ArrayList prpValSize = (ArrayList)prp.get(BOX_RFID+"V");
            ArrayList prpPrt2Size = (ArrayList)prp.get(BOX_RFID+"P2");
            int ct=0;
            if(!box.equals("")){
                int getIndex=prpPrt2Size.indexOf(box);  
                boxrfidval = util.nvl((String)prpValSize.get(getIndex));
            }
            vnm = util.getVnm(vnm);
            ArrayList vnmList = new ArrayList();
            vnm = vnm.substring(1, vnm.length()-1);
            String[] vnmStr = vnm.split("','");
            for(int i=0;i<vnmStr.length;i++){
                vnm = vnmStr[i];
               vnm = vnm.replaceAll(",", "");
               vnm = vnm.replaceAll("'", "");
               vnm = vnm.trim();
              
             vnmList.add(vnm);
            }
            for(int k=0 ;k<vnmList.size();k++){
                insertGt = "insert into gt_file_trf(val ,flg) select ? , ? from dual  ";
                String fromvnmLst=util.nvl((String)vnmList.get(k));
                if(!dummyLst.contains(fromvnmLst)){
                dummyLst.add(fromvnmLst);
                
                ary = new ArrayList();
                ary.add(fromvnmLst);
                ary.add("RT");
                ct = db.execDirUpd("insert gt_file_trf", insertGt, ary);
                }
            }
            String boxrfids = prpPrt2Size.toString();
            boxrfids=boxrfids.replaceAll(",", "','");
            boxrfids = boxrfids.replaceAll("\\[","('");
            boxrfids = boxrfids.replaceAll("\\]","')");
            String deleteQ="delete gt_file_trf where val in "+boxrfids;
            ct = db.execDirUpd("delete gt_file_trf", deleteQ, new ArrayList());
            
            String updGt="update gt_file_trf a set (a.stk_idn,a.mprp)=(select b.idn,decode(a.val,b.vnm,'VNM','RFID')\n" + 
            "from mstk b where 1 = 1 and (a.val=b.vnm or a.val=b.tfl3))";  
            ct = db.execDirUpd("upd gt_file_trf", updGt, new ArrayList());
            
            updGt="update gt_file_trf a set flg='NF' where stk_idn is null";  
            ct = db.execDirUpd("upd gt_file_trf", updGt, new ArrayList());
            
            ary = new ArrayList();
            ary.add(rtnseq);
            updGt="update gt_file_trf a set flg='EX'\n" + 
            "where not exists (select 1 from stk_tly b where b.stk_idn = a.stk_idn and stt in ('IS','RT') and b.seq_idn=? and trunc(nvl(b.iss_dte, sysdate)) = trunc(sysdate) )   and a.flg not in('NF') ";  
            ct = db.execDirUpd("upd gt_file_trf", updGt, ary);
            
            ary = new ArrayList();
            ary.add(rtnseq);
            updGt="delete gt_file_trf a \n" + 
            "where exists (select 1 from stk_tly b where b.stk_idn = a.stk_idn and stt in ('RT') and b.seq_idn=? and trunc(nvl(b.iss_dte, sysdate)) = trunc(sysdate) )   and a.flg in('RT') ";  
            ct = db.execDirUpd("upd gt_file_trf", updGt, ary);
            
            ary = new ArrayList();
            ary.add(rtnseq);
            String updateStk_tly="delete stk_tly_nf nf where   nf.seq_idn=? and trunc(nvl(nf.rtn_dte, sysdate)) = trunc(sysdate) \n" + 
            "and exists (select 1 from gt_file_trf a where nf.ref_idn=a.val)";
            int upCnt = db.execUpd("upda", updateStk_tly, ary);
            
            ary = new ArrayList();
            ary.add(rtnseq);
            ary.add(boxrfidval);
            insertGt="insert into stk_tly_nf(seq_idn,box_id , ref_idn ,ref_typ, flg,rtn_usr)\n" + 
            "select ?,? , val ,mprp, flg,pack_var.get_usr from gt_file_trf ";  
            ct = db.execDirUpd("insert gt_file_trf", insertGt, ary);
                       
            String insgtPkt = "Insert into gt_srch_rslt (stk_idn, vnm ,qty,cts,stt, flg,sk1,rmk,cert_lab,pkt_ty ) " + 
                  "select b.idn, b.vnm ,b.qty,b.cts,b.stt, 'RT',b.sk1,b.tfl3,b.cert_lab,b.pkt_ty  " +
                  " from mstk b, gt_file_trf a where 1 = 1 and b.idn=a.stk_idn and a.flg in('RT')" ;
               
            ct = db.execUpd(" ins gt", insgtPkt, new ArrayList());
            
            ary = new ArrayList();
            ary.add(rtnseq);
             updateStk_tly = "update stk_tly a set a.rtn_dte=sysdate  , a.stt='RT',rtn_usr=pack_var.get_usr "+
                               " where exists (select 1 from gt_srch_rslt b where a.stk_idn=b.stk_idn and b.flg='RT') and trunc(nvl(a.iss_dte, sysdate)) = trunc(sysdate) and a.stt='IS' and a.seq_idn=?   ";
             upCnt = db.execUpd("upda", updateStk_tly, ary);
            
            String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
            ary = new ArrayList();
            ary.add("RFID_VW");
            ct = db.execCall(" Srch Prp ", pktPrp, ary);
            
            HashMap flgMap=getstktlynf(req,rtnseq);
            req.setAttribute("sttMap", flgMap);
            SearchResult(req,res,"RT");
            TallyStock(req,rtnseq);
            
            if(!rtnassign.equals("")){
                ct = db.execCall("delete gt", "delete from gt_file_trf", new ArrayList());
                insertGt = "insert into gt_file_trf(stk_idn , lab , mprp , val ,flg) select stk_idn , cert_lab , ? , ? , ? from gt_srch_rslt where pkt_ty='NR' and flg in('RT') ";
                insertGt+=" and not exists(select 1 from gt_file_trf b where gt_srch_rslt.stk_idn=b.stk_idn)";
                ary = new ArrayList();
                ary.add(BOX_RFID);
                ary.add(boxrfidval);
                ary.add("N");
                ct = db.execDirUpd("insert gt_file_trf", insertGt, ary);
                if(ct>0){
                ArrayList out = new ArrayList();
                out.add("I");
                out.add("V");

                CallableStatement ct1 = db.execCall("update pkt", "PRP_PKG.BLK_UPD(pCnt => ? , pMsg=> ?)", new ArrayList(), out);
                int count = ct1.getInt(1);
                String msg = ct1.getString(2);
                req.setAttribute("msg","Number of Packet Allocation Done : "+count);
                req.setAttribute("msg1",msg);
                }
            }
        udf.reset();
            util.updAccessLog(req,res,"Stock Tally", "return end");
        return am.findForward("loadRtn");
        }
    }
    
    public HashMap  getstktlynf(HttpServletRequest req,String seq){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        Connection conn=info.getCon();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        HashMap flgMap = new HashMap();
        ArrayList boxidLst=new ArrayList();
        ArrayList ary=new ArrayList();
        String str="";
        if(!seq.equals("") && !seq.equals("0")){
            str = " and seq_idn = ? ";
        }
        String gtFtch = "select count(*) cnt, flg,box_id \n" + 
        "from stk_tly_nf \n" + 
        "where\n" + 
        "trunc(rtn_dte)=trunc(sysdate) and  decode(flg,'RT',flg,upper(rtn_usr))=decode(flg,'RT',flg,upper(?))\n" +str +
        "group by flg, box_id\n" +
        "union\n" + 
        "select count(*) cnt, flg,'TOTAL'  \n" + 
        "from stk_tly_nf \n" + 
        "where \n" + 
        "trunc(rtn_dte)=trunc(sysdate) and  decode(flg,'RT',flg,upper(rtn_usr))=decode(flg,'RT',flg,upper(?))\n" +str+ 
        "group by flg";
        ary.add(util.nvl(((String)info.getUsr())));
        if(!seq.equals("") && !seq.equals("0"))
            ary.add(seq); 
        ary.add(util.nvl(((String)info.getUsr())));
        if(!seq.equals("") && !seq.equals("0"))
            ary.add(seq); 
      ArrayList outLst = db.execSqlLst("gt_srch", gtFtch, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
        try {
        while(rs.next()){
            String cnt = util.nvl(rs.getString("cnt"));
            String flg = util.nvl(rs.getString("flg"));
            String box_id = util.nvl(rs.getString("box_id"));
            flgMap.put(box_id+"_"+flg, cnt);
            if(!boxidLst.contains(box_id))
                boxidLst.add(box_id);
        } 
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        if(flgMap.size()>0)
        flgMap.put("BOXID", boxidLst);
       return flgMap;
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
                util.updAccessLog(req,res,"Stock Tally", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Stock Tally", "init");
            }
            }
            return rtnPg;
            }
}
