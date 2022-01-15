package ft.com.assorthk;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.MailAction;

import ft.com.assort.AssortFinalRtnForm;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.lab.LabRecheckImpl;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class VerificationHKAction extends DispatchAction {
    
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
          util.updAccessLog(req,res,"VerificationHkAction", "load");
       VerificationHkForm udf = (VerificationHkForm)form;
        HashMap stkTypDtl=new HashMap();
        HashMap stkTypDeptDtl=new HashMap();
        HashMap GrandTtlDtl=new HashMap();
        HashMap GrandTtl=new HashMap();
        ArrayList typ=new ArrayList();
        String key="";
        ResultSet rs=null;

        String stkDtlQ="SELECT MFG_TYP, DEPT, SUM(QTY) PCS, to_char(trunc(sum(trunc(CTS, 2)),2),'99999990.00') CTS FROM MFG_SMRY_V GROUP BY MFG_TYP, DEPT";
        rs = db.execSql("Stock Type Summary", stkDtlQ, new ArrayList());
        while(rs.next()) {
            stkTypDeptDtl=new HashMap();
            stkTypDeptDtl.put("PCS",util.nvl(rs.getString("PCS")));
            stkTypDeptDtl.put("CTS",util.nvl(rs.getString("CTS")));
            key=util.nvl(rs.getString("MFG_TYP"))+"_"+util.nvl(rs.getString("DEPT"));
            stkTypDtl.put(key,stkTypDeptDtl);   
        }
        rs.close();
        String typGrandQ="SELECT MFG_TYP,SUM(QTY) PCS, to_char(trunc(sum(trunc(CTS, 2)),2),'99999990.00') CTS FROM MFG_SMRY_V GROUP BY MFG_TYP";
        rs = db.execSql("Type Wise Grand", typGrandQ, new ArrayList());
        while(rs.next()) {
            GrandTtl=new HashMap();
            GrandTtl.put("PCS",util.nvl(rs.getString("PCS")));  
            GrandTtl.put("CTS",util.nvl(rs.getString("CTS")));
            GrandTtlDtl.put(util.nvl(rs.getString("MFG_TYP")),GrandTtl);
        }
        rs.close();
        String deptGrandQ="SELECT DEPT,SUM(QTY) PCS, to_char(trunc(sum(trunc(CTS, 2)),2),'99999990.00') CTS FROM MFG_SMRY_V GROUP BY DEPT";
        rs = db.execSql("Type Wise Grand", deptGrandQ, new ArrayList());
        while(rs.next()) {
            GrandTtl=new HashMap();
            GrandTtl.put("PCS",util.nvl(rs.getString("PCS")));  
            GrandTtl.put("CTS",util.nvl(rs.getString("CTS")));
            GrandTtlDtl.put(util.nvl(rs.getString("DEPT")),GrandTtl);
        }
        rs.close();
        String GrandQ="SELECT nvl(SUM(QTY),0) PCS, nvl(to_char(trunc(sum(trunc(CTS, 2)),2),'99999990.00'),0) CTS FROM MFG_SMRY_V";
        rs = db.execSql("Type Wise Grand", GrandQ, new ArrayList());
        while(rs.next()) {
            GrandTtl=new HashMap();
            GrandTtl.put("PCS",util.nvl(rs.getString("PCS")));  
            GrandTtl.put("CTS",util.nvl(rs.getString("CTS")));
            GrandTtlDtl.put("GTOTAL",GrandTtl);
        }
        rs.close();
        HashMap memoDtl=new HashMap();
        HashMap memoDtlQC=new HashMap();
        HashMap memoTypDtl=new HashMap();
        ArrayList memoIDN=new ArrayList();
        String memoQ="select MFG_TYP, MEMO, DEPT, QTY PCS,CTS from MFG_SMRY_V order BY MFG_TYP, MEMO, DEPT";
        rs = db.execSql("Memo Wise Details", memoQ, new ArrayList());
        while(rs.next()) {
            memoDtlQC=new HashMap();
            String memo=util.nvl(rs.getString("MEMO"));
            String mfg_typ=util.nvl(rs.getString("MFG_TYP"));
            String dept=util.nvl(rs.getString("DEPT"));
            if(!memoIDN.contains(memo)){
                memoIDN.add(memo);
                memoTypDtl.put(memo,mfg_typ);
            }
            key=mfg_typ+"_"+memo+"_"+dept;
            memoDtlQC.put("PCS",util.nvl(rs.getString("PCS")));  
            memoDtlQC.put("CTS",util.nvl(rs.getString("CTS")));   
            memoDtl.put(key,memoDtlQC);
            
        }
        rs.close();
        String memoGrandQ="Select distinct A.MFG_TYP,A.MEMO,Sum(A.Qty) PCS, To_Char(Trunc(Sum(Trunc(A.Cts, 2)),2),'99999990.00') CTS,\n" + 
        "Decode(B.Memo_Status, Null,'UNCOMPLETED',B.Memo_Status) STT\n" + 
        "From Mfg_Smry_V A,(select distinct dsc,memo_status from mlot) b\n" + 
        "Where a.memo=b.dsc\n" + 
        "GROUP BY A.Mfg_Typ, A.Memo\n" + 
        ", Decode(B.Memo_Status, Null,'UNCOMPLETED',B.Memo_Status)\n";
        rs = db.execSql("Memo Wise Grand", memoGrandQ, new ArrayList());
        while(rs.next()) {
            GrandTtl=new HashMap();
            String memo=util.nvl(rs.getString("MEMO"));
            String mfg_typ=util.nvl(rs.getString("MFG_TYP"));
            GrandTtl.put("PCS",util.nvl(rs.getString("PCS")));  
            GrandTtl.put("CTS",util.nvl(rs.getString("CTS")));
            GrandTtl.put("STT",util.nvl(rs.getString("STT")));
            GrandTtlDtl.put(mfg_typ+"_"+memo,GrandTtl);
        }
        rs.close();
        typ.add("NEW");
        typ.add("REP");
        typ.add("REVIEW");
        session.setAttribute("typ", typ);
        req.setAttribute("stkTypDtl", stkTypDtl);
        req.setAttribute("GrandTtlDtl", GrandTtlDtl);
        req.setAttribute("memoTypDtl", memoTypDtl);
        req.setAttribute("memoIDN", memoIDN);
        session.setAttribute("memoDtl", memoDtl);
          
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("VERIFICATION");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("VERIFICATION");
            allPageDtl.put("VERIFICATION",pageDtl);
            }
            info.setPageDetails(allPageDtl);
          util.updAccessLog(req,res,"VerificationHkAction", "load end");
        return am.findForward("load");
        }
    }
    
    
    public ActionForward processForm(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"VerificationHkAction", "process");
       VerificationHkForm udf = (VerificationHkForm)form;
        String[] memowise = req.getParameterValues("memowisechk");
        String vertyp = util.nvl(req.getParameter("vertyp"),"NEW").trim();
        String query="";
        String sucessMemoId="";
        ArrayList memoLst=new ArrayList();
        HashMap prp = info.getPrp();
        ArrayList PrpDtl = (ArrayList)prp.get("DEPT"+"V");
        ResultSet rs=null;
        
        MailAction mailObj = new MailAction();
        ArrayList memoIDN=new ArrayList();
        HashMap memoDtl=new HashMap();
        HashMap memoDtlQC=new HashMap();
        String key="";
        String memo="";
        if(memowise!=null) 
        {
            for(int i=0; i<memowise.length; i++)
            {
                String memono = memowise[i];
                memono = memono.trim();
                memoLst.add(memono);
            } 
            if(memoLst!=null && memoLst.size()>0){
                memo = memoLst.toString();
                memo = memo.replace("[","('");
                memo = memo.replace("]","')");
                memo = memo.replace(",", "','");
                memo = memo.replace("' ", "'");
                memo = memo.trim();
            query ="  select MEMO, DEPT, sum(QTY) PCS,to_char(trunc(sum(trunc(CTS, 2)),3),'99999990.000') CTS from MFG_SMRY_V where MEMO in "+memo+" and mfg_typ='"+vertyp+"'  group by MEMO, DEPT order by MEMO";
            rs = db.execSql("Memo_Dtl", query, new ArrayList());
            while(rs.next()){
                memoDtlQC=new HashMap();
                String memoid=util.nvl(rs.getString("MEMO"));
                String dept=util.nvl(rs.getString("DEPT"));
                if(!memoIDN.contains(memoid)){
                    memoIDN.add(memoid);
                }
                key=memoid+"_"+dept;
                memoDtlQC.put("PCS",util.nvl(rs.getString("PCS")));  
                memoDtlQC.put("CTS",util.nvl(rs.getString("CTS")));   
                memoDtl.put(key,memoDtlQC);
             }
                rs.close();
                query="select DEPT, sum(QTY) PCS,to_char(trunc(sum(trunc(CTS, 2)),3),'99999990.000') CTS from MFG_SMRY_V where memo in "+memo+" and mfg_typ='"+vertyp+"' group by DEPT";
                rs = db.execSql("Memo_Dtl", query, new ArrayList()); 
                while(rs.next()){
                    memoDtlQC=new HashMap();
                    String dept=util.nvl(rs.getString("DEPT"));
                    memoDtlQC.put("PCS",util.nvl(rs.getString("PCS")));  
                    memoDtlQC.put("CTS",util.nvl(rs.getString("CTS")));   
                    memoDtl.put(dept,memoDtlQC);
                 }
                rs.close();
                query="select MEMO, sum(QTY) PCS,to_char(trunc(sum(trunc(CTS, 2)),3),'99999990.000') CTS from MFG_SMRY_V where memo in "+memo+" and mfg_typ='"+vertyp+"' group by MEMO";
                rs = db.execSql("Memo_Dtl", query, new ArrayList()); 
                while(rs.next()){
                    memoDtlQC=new HashMap();
                    String memoid=util.nvl(rs.getString("MEMO"));
                    memoDtlQC.put("PCS",util.nvl(rs.getString("PCS")));  
                    memoDtlQC.put("CTS",util.nvl(rs.getString("CTS")));   
                    memoDtl.put(memoid,memoDtlQC);
                 }
                rs.close();
                query="select sum(QTY) PCS,to_char(trunc(sum(trunc(CTS, 2)),3),'99999990.000') CTS from MFG_SMRY_V where memo in "+memo+" and mfg_typ='"+vertyp+"'";
                rs = db.execSql("Memo_Dtl", query, new ArrayList()); 
                while(rs.next()){
                    memoDtlQC=new HashMap();
                    memoDtlQC.put("PCS",util.nvl(rs.getString("PCS")));  
                    memoDtlQC.put("CTS",util.nvl(rs.getString("CTS")));   
                    memoDtl.put("GRAND",memoDtlQC);
                 }
                rs.close();
            } 
        }       
       
        if(memowise!=null) 
        {
            for(int i=0; i<memowise.length; i++)
            {
                String memono = memowise[i];
                memono=memono.substring(memono.indexOf("_")+1, memono.length());
                    //update all the pkt status to "MF_FL"
                  query = "update mstk set stt='MF_FL'  " + 
                  "where idn in (select distinct b.mstk_idn from mstk a, stk_dtl b,stk_dtl c  " + 
                  "where a.idn = b.mstk_idn and a.idn = c.mstk_idn and  a.stt = 'MF_IN' and  " + 
                  "b.mprp = 'MEMO' and b.txt in ('"+memono+"') and b.grp=1 and c.mprp = 'MFG_TYP' and c.val in ('"+vertyp+"') and c.grp=1 )";
                  
                  int ct = db.execUpd("Update status memo wise", query, new ArrayList());
                    if(ct>0){
                        sucessMemoId=sucessMemoId+","+memono;
                    }
                    query = "update mstk set stt='REP_IS'  " + 
                    "where idn in (select distinct b.mstk_idn from mstk a, stk_dtl b,stk_dtl c " + 
                    "where a.idn = b.mstk_idn and a.idn = c.mstk_idn and  a.stt = 'REP_IN' and  " + 
                    "b.mprp = 'MEMO' and b.txt in ('"+memono+"')  and b.grp=1 and c.mprp = 'MFG_TYP' and c.val in ('"+vertyp+"') and c.grp=1 )";
                   ct = db.execUpd("Update status memo wise", query, new ArrayList());
                if(ct>0){
                    sucessMemoId=sucessMemoId+","+memono;
                }
                }
                req.setAttribute("sucessMemoId", sucessMemoId); 
                
            }
      int issueIdn = 0;
        if(memoLst!=null && memoLst.size()>0){
            String prcId=prcid(req);
            for(int l=0;l<PrpDtl.size();l++){
                String dept=(String)PrpDtl.get(l);
                ArrayList pktList = new ArrayList();
                ArrayList params = null;
              
                String empId="";
                String sqlQ="select a.idn stk_idn, a.qty qty, a.cts cts from mstk a , stk_dtl b , stk_dtl c,stk_dtl d " + 
                "where a.idn = b.mstk_idn and b.mprp='MEMO' and b.txt in "+memo+" and a.stt in ('MF_FL') and c.mstk_idn = a.idn and c.mprp ='DEPT' and c.val='"+dept+"' " + 
                "and a.idn = d.mstk_idn and d.mprp = 'MFG_TYP' and d.val in ('"+vertyp+"') and d.grp=1 "+
                "and b.grp=1 and c.grp=1 ";
                rs = db.execSql("Stk_Dtl", sqlQ, new ArrayList());
                while(rs.next()){
                    if(issueIdn==0){
                        empId= empid(req,dept);     
                        params = new ArrayList();
                        params.add(prcId);
                        params.add(empId);
                        ArrayList out = new ArrayList();
                        out.add("I");
                        CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", params ,out);
                        issueIdn = cst.getInt(3);
                      cst.close();
                      cst=null;
                    }
                    if(issueIdn<=0){
                    params = new ArrayList();
                    params.add("MF_IN");
                    params.add(util.nvl(rs.getString("stk_idn")));
                    int ct = db.execUpd("update mstk", "update mstk set stt=? where idn=?", params);
                    }else{
                    params = new ArrayList();
                    params.add(String.valueOf(issueIdn));
                    params.add(util.nvl(rs.getString("stk_idn")));
                    params.add(util.nvl(rs.getString("qty")));
                    params.add(util.nvl(rs.getString("cts")));
                    params.add("IS");
                    String issuePkt = "ISS_RTN_PKG.ISS_PKT(pIssId => ?, pStkIdn => ? , pCts => ?,  pQty => ? , pStt => ?)";
                    int ct = db.execCall("issuePkt", issuePkt, params); 
                    }
                   
                }
                rs.close();
            }
            if(issueIdn!=0){
            mailObj.sendVerifyMail(memoIDN,memoDtl, req, res);
            }else{
              req.setAttribute("msg", "Error in Stone Issues...");
            }
        }
          util.updAccessLog(req,res,"VerificationHkAction", "process end");
      return load(am, form, req, res);
        }
    }
    
    

    public ActionForward loadSizePkt(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"VerificationHkAction", "loadSize");
       VerificationHkForm udf = (VerificationHkForm)form;
          GenericInterface genericInt = new GenericImpl();
      ArrayList checkSrchList =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SZPKTGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SZPKTGNCSrch");
      info.setGncPrpLst(checkSrchList);
      udf.setValue("sizepkt", "SUMMARY");  
          util.updAccessLog(req,res,"VerificationHkAction", "loadSZ end");
    return am.findForward("loadSizePkt");
        }
    }    
    
    public ActionForward fetchSizePkt(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"VerificationHkAction", "fetchSzpkt");
       VerificationHkForm udf = (VerificationHkForm)form;
        ArrayList stockList = null;
        String sizepkt = util.nvl((String)udf.getValue("sizepkt"));
//        String memoId = util.nvl((String)udf.getValue("MEMO_1"));
        String mfg = util.nvl((String)udf.getValue("MFG_TYP_1"));
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        HashMap paramsMap = new HashMap();
        ArrayList genricSrchLst =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SZPKTGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_SZPKTGNCSrch");
        info.setGncPrpLst(genricSrchLst);
//        ArrayList genricSrchLst = info.getGncPrpLst();
        for (int i = 0; i < genricSrchLst.size(); i++) {
        ArrayList srchPrp = (ArrayList)genricSrchLst.get(i);
        String lprp = (String)srchPrp.get(0);
        String flg= (String)srchPrp.get(1);
        String prpSrt = lprp ;  
        String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
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
                            if(fldVal2.equals(""))
                            fldVal2=fldVal1;
                            if(!fldVal1.equals("") && !fldVal2.equals("")){
                                paramsMap.put(lprp+"_1", fldVal1);
                                paramsMap.put(lprp+"_2", fldVal2);
                            }   
            }            
        }
        paramsMap.put("stt", "MF_REP_IN");
        paramsMap.put("mdl", "SZP_VIEW");
        paramsMap.put("ALLSRCH", "Y");
        util.genericSrch(paramsMap);

        if(sizepkt.equals("SUMMARY")){
            SearchResult(req);  
        }else{
            SearchResult1(req);  
        }
        req.setAttribute("option", sizepkt);   
        req.setAttribute("view", "Y");   
        udf.setValue("sizepkt", sizepkt);
//        req.setAttribute("memoId", memoId);   
        ArrayList prpSrtSize = (ArrayList)prp.get("MFG_TYP"+"S");
        ArrayList prpValSize = (ArrayList)prp.get("MFG_TYP"+"V");
        mfg=(String)prpValSize.get(prpSrtSize.indexOf(mfg));
        req.setAttribute("mfg", mfg);   
        session.setAttribute("StockList", stockList);
          util.updAccessLog(req,res,"VerificationHkAction", "sizePkt end");
    return am.findForward("loadSizePkt");
        }
    } 
    public ActionForward verifySizeWise(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception 
    {
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
          util.updAccessLog(req,res,"VerificationHkAction", "verifySzWs");
       VerificationHkForm udf = (VerificationHkForm)form;
        String[] sizewisechk = req.getParameterValues("sizewisechk");
        String mfg_typ = util.nvl(req.getParameter("mfg_typ"));
        String query="";
        String sz="";
        String stk_idn="";
        String stk_idnUpdt="";
        ArrayList vwPrpLst = VRPrprViw(req);
        int indexSD = vwPrpLst.indexOf("SIZE")+1;
        String sdPrp = "prp_00"+indexSD;
        int indexmf = vwPrpLst.indexOf("MFG_TYP")+1;
        String mfPrp = "prp_00"+indexmf;
        int indexDEP = vwPrpLst.indexOf("DEPT")+1;
        String dpPrp = "prp_00"+indexDEP;
        int indexMO = vwPrpLst.indexOf("MEMO")+1;       
        String moPrp = "prp_00"+indexMO;
        HashMap prp = info.getPrp();
        String prcId=prcid(req);
        ArrayList PrpDtl = (ArrayList)prp.get("DEPT"+"V");
        if(sizewisechk!=null) 
        {
            for(int i=0; i<sizewisechk.length; i++)
            {
                sz =sz+","+ sizewisechk[i].trim();
            }
        }
        if(sz.indexOf(",") > -1) {
            sz=sz.replaceFirst(",", ""); 
             sz=util.getVnm(sz);
             
             query="select stk_idn from gt_srch_rslt where "+sdPrp+" in ("+sz+") ";
             ResultSet rs = db.execSql("search Result", query, new ArrayList());
             try {
             while(rs.next()) {
             stk_idn=util.nvl(rs.getString("stk_idn"));
             stk_idnUpdt=stk_idnUpdt+","+stk_idn;
             }
                rs.close();
            } catch (SQLException sqle) {
                 sqle.printStackTrace();
             }
             if(stk_idnUpdt.indexOf(",") > -1) {
             stk_idnUpdt=stk_idnUpdt.replaceFirst(",", ""); 
             updatePKt(req,stk_idn,mfg_typ); 
             }
             for(int l=0;l<PrpDtl.size();l++){
                 String dept=(String)PrpDtl.get(l);
                 ArrayList params = null;
                 int issueIdn = 0;
                 String empId="";
                 String sqlQ="select stk_idn,cts,qty from gt_srch_rslt where "+sdPrp+" in ("+sz+") and "+dpPrp+"='"+ dept+"' and "+mfPrp+" ='NEW'";
                 rs = db.execSql("Stk_Dtl", sqlQ, new ArrayList());
                 while(rs.next()){
                     if(issueIdn==0){
                         empId= empid(req,dept);     
                         params = new ArrayList();
                         params.add(prcId);
                         params.add(empId);
                         ArrayList out = new ArrayList();
                         out.add("I");
                         CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", params ,out);
                         issueIdn = cst.getInt(3);
                       cst.close();
                       cst=null;
                     }
                     params = new ArrayList();
                     params.add(String.valueOf(issueIdn));
                     params.add(util.nvl(rs.getString("stk_idn")));
                     params.add(util.nvl(rs.getString("qty")));
                     params.add(util.nvl(rs.getString("cts")));
                     params.add("IS");
                     String issuePkt = "ISS_RTN_PKG.ISS_PKT(pIssId => ?, pStkIdn => ? , pCts => ?,  pQty => ? , pStt => ?)";
                     int ct = db.execCall("issuePkt", issuePkt, params); 
                     System.out.println(ct);
                 }
                 rs.close();
             }
             req.setAttribute("SizeVerify",sz);  
             String key="";
             MailAction mailObj = new MailAction();
             ArrayList memoIDN=new ArrayList();
             HashMap memoDtl=new HashMap();
             HashMap memoDtlQC=new HashMap();
             query ="select to_char(trunc(sum(trunc(cts, 2)),3),'99999990.000') CTS,sum(qty) PCS,"+moPrp+" MEMO,"+dpPrp+" DEPT from gt_srch_rslt where "+sdPrp+" in ("+sz+") group by "+moPrp+","+dpPrp;
             rs = db.execSql("Memo_Dtl", query, new ArrayList());
             while(rs.next()){
                 memoDtlQC=new HashMap();
                 String memoid=util.nvl(rs.getString("MEMO"));
                 String dept=util.nvl(rs.getString("DEPT"));
                 if(!memoIDN.contains(memoid)){
                     memoIDN.add(memoid);
                 }
                 key=memoid+"_"+dept;
                 memoDtlQC.put("PCS",util.nvl(rs.getString("PCS")));  
                 memoDtlQC.put("CTS",util.nvl(rs.getString("CTS")));   
                 memoDtl.put(key,memoDtlQC);
              }
             rs.close();
             query ="select to_char(trunc(sum(trunc(cts, 2)),3),'99999990.000') CTS,sum(qty) PCS,"+dpPrp+" DEPT from gt_srch_rslt where "+sdPrp+" in ("+sz+") group by "+dpPrp;
             rs = db.execSql("Memo_Dtl", query, new ArrayList());
             while(rs.next()){
                 memoDtlQC=new HashMap();
                 String dept=util.nvl(rs.getString("DEPT"));
                 memoDtlQC.put("PCS",util.nvl(rs.getString("PCS")));  
                 memoDtlQC.put("CTS",util.nvl(rs.getString("CTS")));   
                 memoDtl.put(dept,memoDtlQC);
              }
             rs.close();
             query="select to_char(trunc(sum(trunc(cts, 2)),3),'99999990.000') CTS,sum(qty) PCS,"+moPrp+" MEMO from gt_srch_rslt where "+sdPrp+" in ("+sz+") group by "+moPrp;
             rs = db.execSql("Memo_Dtl", query, new ArrayList()); 
             while(rs.next()){
                 memoDtlQC=new HashMap();
                 String memoid=util.nvl(rs.getString("MEMO"));
                 memoDtlQC.put("PCS",util.nvl(rs.getString("PCS")));  
                 memoDtlQC.put("CTS",util.nvl(rs.getString("CTS")));   
                 memoDtl.put(memoid,memoDtlQC);
              }
             rs.close();
             query="select to_char(trunc(sum(trunc(cts, 2)),3),'99999990.000') CTS,sum(qty) PCS from gt_srch_rslt where "+sdPrp+" in ("+sz+") ";
             rs = db.execSql("Memo_Dtl", query, new ArrayList()); 
             while(rs.next()){
                 memoDtlQC=new HashMap();
                 memoDtlQC.put("PCS",util.nvl(rs.getString("PCS")));  
                 memoDtlQC.put("CTS",util.nvl(rs.getString("CTS")));   
                 memoDtl.put("GRAND",memoDtlQC);
              }
             rs.close();
             mailObj.sendVerifyMail(memoIDN,memoDtl, req, res);
         }
          util.updAccessLog(req,res,"VerificationHkAction", "verifySzWs end");
      return loadSizePkt(am, form, req, res);
        }
    }
    public ActionForward verifyPktWise(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception 
    {
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
           util.updAccessLog(req,res,"VerificationHkAction", "pktWs");
        VerificationHkForm udf = (VerificationHkForm)form;
        Enumeration reqNme = req.getParameterNames();
         String mfg_typ = req.getParameter("mfg_typ");
        String stk_idn="";
        String stk_idnfail="";
        ArrayList ary=null;
        String idnPcs="";
         HashMap prp = info.getPrp();
         String prcId=prcid(req);
         ResultSet rs=null;
         ArrayList PrpDtl = (ArrayList)prp.get("DEPT"+"V");
         ArrayList vwPrpLst = VRPrprViw(req);
         int indexDEP = vwPrpLst.indexOf("DEPT")+1;
         String dpPrp = "prp_00"+indexDEP;
         int indexmf = vwPrpLst.indexOf("MFG_TYP")+1;
         String mfPrp = "prp_00"+indexmf;
         int indexMO = vwPrpLst.indexOf("MEMO")+1;       
         String moPrp = "prp_00"+indexMO;
        while(reqNme.hasMoreElements()) {
            String paramNm = (String)reqNme.nextElement();
            if(paramNm.indexOf("VFY_") > -1) {
                String val = req.getParameter(paramNm);
                if(val.indexOf("fail_") > -1) {
                stk_idn=val.substring(val.indexOf("_")+1, val.length());
                ary = new ArrayList();
                ary.add(stk_idn);
                ary.add("VFY_FAIL");
                ary.add("FAIL");
                String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? ,  pPrpTyp => 'T' )";
                int ct = db.execCall("stockUpd",stockUpd, ary);
                if(ct>0)
                stk_idnfail =stk_idnfail+","+ stk_idn;
                }
                else if(val.indexOf("Verify_") > -1){
                stk_idn=val.substring(val.indexOf("_")+1, val.length()); 
                idnPcs =idnPcs+","+ stk_idn;
            }    
         }
        }
        if(idnPcs.indexOf(",") > -1){
                idnPcs=idnPcs.replaceFirst(",",""); 
            updatePKt(req,idnPcs,mfg_typ); 
            for(int l=0;l<PrpDtl.size();l++){
                String dept=(String)PrpDtl.get(l);
                ArrayList params = null;
                int issueIdn = 0;
                String empId="";
                String sqlQ="select stk_idn,cts,qty from gt_srch_rslt where stk_idn in ("+idnPcs+") and "+dpPrp+"='"+ dept+"' and "+mfPrp+" ='NEW'";
                rs = db.execSql("Stk_Dtl", sqlQ, new ArrayList());
                while(rs.next()){
                    if(issueIdn==0){
                        empId= empid(req,dept);     
                        params = new ArrayList();
                        params.add(prcId);
                        params.add(empId);
                        ArrayList out = new ArrayList();
                        out.add("I");
                        CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", params ,out);
                        issueIdn = cst.getInt(3);
                      cst.close();
                      cst=null;
                    }
                    params = new ArrayList();
                    params.add(String.valueOf(issueIdn));
                    params.add(util.nvl(rs.getString("stk_idn")));
                    params.add(util.nvl(rs.getString("qty")));
                    params.add(util.nvl(rs.getString("cts")));
                    params.add("IS");
                    String issuePkt = "ISS_RTN_PKG.ISS_PKT(pIssId => ?, pStkIdn => ? , pCts => ?,  pQty => ? , pStt => ?)";
                    int ct = db.execCall("issuePkt", issuePkt, params); 
                    System.out.println(ct);
                }
                rs.close();
            }  
            String key="";
            MailAction mailObj = new MailAction();
            ArrayList memoIDN=new ArrayList();
            HashMap memoDtl=new HashMap();
            HashMap memoDtlQC=new HashMap();
            String query ="select to_char(trunc(sum(trunc(cts, 2)),3),'99999990.000') CTS,sum(qty) PCS,"+moPrp+" MEMO,"+dpPrp+" DEPT from gt_srch_rslt where stk_idn in ("+idnPcs+")  group by "+moPrp+","+dpPrp;
            rs = db.execSql("Memo_Dtl", query, new ArrayList());
            while(rs.next()){
                memoDtlQC=new HashMap();
                String memoid=util.nvl(rs.getString("MEMO"));
                String dept=util.nvl(rs.getString("DEPT"));
                if(!memoIDN.contains(memoid)){
                    memoIDN.add(memoid);
                }
                key=memoid+"_"+dept;
                memoDtlQC.put("PCS",util.nvl(rs.getString("PCS")));  
                memoDtlQC.put("CTS",util.nvl(rs.getString("CTS")));   
                memoDtl.put(key,memoDtlQC);
             }
            rs.close();
            query ="select to_char(trunc(sum(trunc(cts, 2)),3),'99999990.000') CTS,sum(qty) PCS,"+dpPrp+" DEPT from gt_srch_rslt where stk_idn in ("+idnPcs+")  group by "+dpPrp;
            rs = db.execSql("Memo_Dtl", query, new ArrayList());
            while(rs.next()){
                memoDtlQC=new HashMap();
                String dept=util.nvl(rs.getString("DEPT"));
                memoDtlQC.put("PCS",util.nvl(rs.getString("PCS")));  
                memoDtlQC.put("CTS",util.nvl(rs.getString("CTS")));   
                memoDtl.put(dept,memoDtlQC);
             }
            rs.close();
            query ="select to_char(trunc(sum(trunc(cts, 2)),3),'99999990.000') CTS,sum(qty) PCS,"+moPrp+" MEMO from gt_srch_rslt where stk_idn in ("+idnPcs+")  group by "+moPrp;
            rs = db.execSql("Memo_Dtl", query, new ArrayList()); 
            while(rs.next()){
                memoDtlQC=new HashMap();
                String memoid=util.nvl(rs.getString("MEMO"));
                memoDtlQC.put("PCS",util.nvl(rs.getString("PCS")));  
                memoDtlQC.put("CTS",util.nvl(rs.getString("CTS")));   
                memoDtl.put(memoid,memoDtlQC);
             }
            rs.close();
            query ="select to_char(trunc(sum(trunc(cts, 2)),3),'99999990.000') CTS,sum(qty) PCS from gt_srch_rslt where stk_idn in ("+idnPcs+")";
            rs = db.execSql("Memo_Dtl", query, new ArrayList()); 
            while(rs.next()){
                memoDtlQC=new HashMap();
                memoDtlQC.put("PCS",util.nvl(rs.getString("PCS")));  
                memoDtlQC.put("CTS",util.nvl(rs.getString("CTS")));   
                memoDtl.put("GRAND",memoDtlQC);
             }
            rs.close();
            mailObj.sendVerifyMail(memoIDN,memoDtl, req, res);
           
        }
             req.setAttribute("PacketVerify",idnPcs);  
        
             if(!stk_idnfail.equals("")){
            if(stk_idnfail.indexOf(",") > -1) 
            stk_idnfail = stk_idnfail.replaceFirst(",","");
            req.setAttribute("Packetfail",stk_idnfail); 
        }
           util.updAccessLog(req,res,"VerificationHkAction", "pktws end");
       return loadSizePkt(am, form, req, res);
         }
     }
    
    
    public void SearchResult(HttpServletRequest req){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList vwPrpLst = VRPrprViw(req);
        int indexMO = vwPrpLst.indexOf("MEMO")+1;
        int indexDP = vwPrpLst.indexOf("DEPT")+1;
        int indexSD = vwPrpLst.indexOf("SIZE")+1;
        
        String moPrp = "prp_00"+indexMO;
        String dplPrp = "prp_00"+indexDP;
        String sdPrp = "prp_00"+indexSD;
        String  srchQ =  " select SUM(qty) qty , to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts, ";
        srchQ+=sdPrp+" sz,";
        srchQ+=dplPrp+" dept ";
        
        String rsltQ = srchQ + " from gt_srch_rslt where flg =? GROUP BY ";
        rsltQ+=sdPrp+",";
        rsltQ+=dplPrp;
        
        ArrayList ary = new ArrayList();
        ary.add("Z");
        ArrayList sizeDtl=new ArrayList();
        HashMap dataMap = new HashMap();
        
        ResultSet rs = db.execSql("search Result", rsltQ, ary);
        try {
            while(rs.next()) {
                String sz=util.nvl(rs.getString("sz"));
                String dept=util.nvl(rs.getString("dept"));
                if(!sizeDtl.contains(sz))
                    sizeDtl.add(sz);  
                String key=sz+"_"+dept;
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                dataMap.put(key,pktPrpMap);
                }
            rs.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        req.setAttribute("sizeDtl", sizeDtl);   
        session.setAttribute("dataMap",dataMap);
        
    }
    public void SearchResult1(HttpServletRequest req){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList vwPrpLst = VRPrprViw(req);
        int indexMO = vwPrpLst.indexOf("MEMO")+1;
        int indexDP = vwPrpLst.indexOf("DEPT")+1;
        int indexSD = vwPrpLst.indexOf("SIZE")+1;
        
        String moPrp = "prp_00"+indexMO;
        String dplPrp = "prp_00"+indexDP;
        String sdPrp = "prp_00"+indexSD;
        String  srchQ =  " select SUM(qty) qty , to_char(trunc(sum(trunc(cts, 2)),2),'99999990.00') cts,stk_idn,vnm, ";
        srchQ+=sdPrp+" sz,";
        srchQ+=dplPrp+" dept, ";
        srchQ+=moPrp+" memo ";
        
        String rsltQ = srchQ + " from gt_srch_rslt where flg =? GROUP BY ";
        rsltQ+=sdPrp+",";
        rsltQ+=moPrp+",";
        rsltQ+=dplPrp+",stk_idn,vnm order by "+sdPrp ;
        
        ArrayList ary = new ArrayList();
        ary.add("Z");
        ArrayList pktDtl=new ArrayList();        
        ResultSet rs = db.execSql("search Result", rsltQ, ary);
        try {
            while(rs.next()) {
                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                pktPrpMap.put("sz",util.nvl(rs.getString("sz")));
                pktPrpMap.put("dept",util.nvl(rs.getString("dept")));
                pktPrpMap.put("memo",util.nvl(rs.getString("memo")));
                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                pktPrpMap.put("vnm",util.nvl(rs.getString("vnm")));
                
                pktDtl.add(pktPrpMap);
                }
            rs.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        req.setAttribute("pktDtl", pktDtl);   
    }
    public ActionForward loadsizePktfail(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"VerificationHkAction", "loadPktDtl");
       VerificationHkForm udf = (VerificationHkForm)form;
        ArrayList ary = null;
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ary = new ArrayList();
        ary.add("MF_IN");
        ArrayList pktDtl=new ArrayList();  
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk ) " + 
        " select  pkt_ty, a.idn,a.vnm,  a.dte, stt , 'Z' , qty , cts , sk1 , tfl3  "+
        "     from mstk a , stk_dtl b where a.idn = b.mstk_idn and b.mprp='VFY_FAIL' and a.stt=? and grp=1 order by a.sk1 ";
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("SZPFAIL_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        ArrayList vwPrpLst = VRPrprViwFail(req);
        
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , rmk ";
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
        
        ary = new ArrayList();
        ary.add("Z");
        ResultSet rs = db.execSql("search Result", rsltQ, ary);
        try {
            while(rs.next()) {

                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.put("vnm",vnm);
                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
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
                     
                        
                        pktPrpMap.put(prp, val);
                         }
                              
                    pktDtl.add(pktPrpMap);
                }
            rs.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
        req.setAttribute("pktDtl", pktDtl);   
          util.updAccessLog(req,res,"VerificationHkAction", "loadPktdtl end");
    return am.findForward("loadSizePktFail");
        }
    }    
    public ActionForward verifySIZEPKTFAIL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
       VerificationHkForm udf = (VerificationHkForm)form;
        Enumeration reqNme = req.getParameterNames();
        String stk_idn="";
        String stk_idnfail="";
        String idnPcs="";
        HashMap prp = info.getPrp();
        String prcId=prcid(req);
        ArrayList PrpDtl = (ArrayList)prp.get("DEPT"+"V");
        ArrayList vwPrpLst = VRPrprViwFail(req);
        int indexDEP = vwPrpLst.indexOf("DEPT")+1;
        String dpPrp = "prp_00"+indexDEP;
        int indexMO = vwPrpLst.indexOf("MEMO")+1;        
        String moPrp = "prp_00"+indexMO;
        while(reqNme.hasMoreElements()) {
            String paramNm = (String)reqNme.nextElement();
            if(paramNm.indexOf("VFY_") > -1) {
                String val = req.getParameter(paramNm);
                if(val.indexOf("fail_") > -1) {
                stk_idn=val.substring(val.indexOf("_")+1, val.length());
                String query = "delete from stk_dtl where mstk_idn="+stk_idn;
                int ct = db.execUpd("Delete", query, new ArrayList());
                if(ct>0)
                stk_idnfail =stk_idnfail+","+ stk_idn;
                query = " delete from mstk where idn=  "+stk_idn;
                ct = db.execUpd("Delete", query, new ArrayList());
                }else if(val.indexOf("Verify_") > -1){
                stk_idn=val.substring(val.indexOf("_")+1, val.length());
                idnPcs =idnPcs+","+ stk_idn;
            }    
         }
        }
        if(idnPcs.indexOf(",") > -1){
            idnPcs=idnPcs.replaceFirst(",",""); 
            updatePKt(req,idnPcs,"NEW");   
            for(int l=0;l<PrpDtl.size();l++){
                String dept=(String)PrpDtl.get(l);
                ArrayList params = null;
                int issueIdn = 0;
                String empId="";
//                String sqlQ="select stk_idn,cts,qty from gt_srch_rslt where stk_idn in ("+idnPcs+") and "+dpPrp+"='"+ dept+"' and "+mfPrp+" ='NEW'";
               String sqlQ="select stk_idn,cts,qty from gt_srch_rslt where  stt='MF_IN' and stk_idn in ("+idnPcs+") and "+dpPrp+"='"+ dept+"'";
                ResultSet rs = db.execSql("Stk_Dtl", sqlQ, new ArrayList());
                while(rs.next()){
                    if(issueIdn==0){
                        empId= empid(req,dept);     
                        params = new ArrayList();
                        params.add(prcId);
                        params.add(empId);
                        ArrayList out = new ArrayList();
                        out.add("I");
                        CallableStatement cst = db.execCall("findIssueId","ISS_RTN_PKG.GEN_HDR(pPRCID => ?, pEMPID => ?,  pIssId => ?)", params ,out);
                        issueIdn = cst.getInt(3);
                      cst.close();
                      cst=null;
                    }
                    params = new ArrayList();
                    params.add(String.valueOf(issueIdn));
                    params.add(util.nvl(rs.getString("stk_idn")));
                    params.add(util.nvl(rs.getString("qty")));
                    params.add(util.nvl(rs.getString("cts")));
                    params.add("IS");
                    String issuePkt = "ISS_RTN_PKG.ISS_PKT(pIssId => ?, pStkIdn => ? , pCts => ?,  pQty => ? , pStt => ?)";
                    int ct = db.execCall("issuePkt", issuePkt, params); 
                    System.out.println(ct);
                }
                rs.close();
            }    
            String key="";
            MailAction mailObj = new MailAction();
            ArrayList memoIDN=new ArrayList();
            HashMap memoDtl=new HashMap();
            HashMap memoDtlQC=new HashMap();
            String query ="select to_char(trunc(sum(trunc(cts, 2)),3),'99999990.000') CTS,sum(qty) PCS,"+moPrp+" MEMO,"+dpPrp+" DEPT from gt_srch_rslt where stk_idn in ("+idnPcs+")  group by "+moPrp+","+dpPrp;
            ResultSet rs = db.execSql("Memo_Dtl", query, new ArrayList());
            while(rs.next()){
                memoDtlQC=new HashMap();
                String memoid=util.nvl(rs.getString("MEMO"));
                String dept=util.nvl(rs.getString("DEPT"));
                if(!memoIDN.contains(memoid)){
                    memoIDN.add(memoid);
                }
                key=memoid+"_"+dept;
                memoDtlQC.put("PCS",util.nvl(rs.getString("PCS")));  
                memoDtlQC.put("CTS",util.nvl(rs.getString("CTS")));   
                memoDtl.put(key,memoDtlQC);
             }
            rs.close();
            query ="select to_char(trunc(sum(trunc(cts, 2)),3),'99999990.000') CTS,sum(qty) PCS,"+dpPrp+" DEPT from gt_srch_rslt where stk_idn in ("+idnPcs+")  group by "+dpPrp;
            rs = db.execSql("Memo_Dtl", query, new ArrayList());
            while(rs.next()){
                memoDtlQC=new HashMap();
                String dept=util.nvl(rs.getString("DEPT"));
                memoDtlQC.put("PCS",util.nvl(rs.getString("PCS")));  
                memoDtlQC.put("CTS",util.nvl(rs.getString("CTS")));   
                memoDtl.put(dept,memoDtlQC);
             }
            rs.close();
            mailObj.sendVerifyMail(memoIDN,memoDtl, req, res);
            
        }
         req.setAttribute("PacketVerify",idnPcs);  
        
        if(!stk_idnfail.equals("")){
            if(stk_idnfail.indexOf(",") > -1) 
            stk_idnfail = stk_idnfail.replaceFirst(",","");
            req.setAttribute("Packetfail",stk_idnfail); 
        }
    return loadsizePktfail(am, form, req, res);
        }
    }    
    
    public void updatePKt(HttpServletRequest req, String stk_idn,String mfg_typ){  
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr();
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
        String query="";
        if(mfg_typ.equals("NEW"))
        query = "update mstk set stt='MF_FL' where stt <> 'REP_IN' and idn in("+stk_idn+")";
        else if(mfg_typ.equals("REP"))
        query = "update mstk set stt='REP_IS' where stt='REP_IN' and idn in("+stk_idn+")";
        int ct = db.execUpd("Update status", query, new ArrayList());
        System.out.println(ct);
        }
//        String query = "update mstk set stt='MF_FL' where stt <> 'REP_IN' and idn="+stk_idn;
//        int ct = db.execUpd("Update status size wise", query, new ArrayList());
//        if(ct<=0){
//            query = "update mstk set stt='REP_IS' where stt='REP_IN' and idn="+stk_idn;
//            ct = db.execUpd("Update status size wise REP_IS", query, new ArrayList());  
//        }
//    }   
    public String prcid(HttpServletRequest req) {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    String prcId="";    
    String sqlQ="select idn , in_stt from mprc where is_stt='MF_FL_IS' and stt='A'"; 
    ResultSet rs = db.execSql("ProcessId", sqlQ, new ArrayList());
    try {
    if(rs.next()){
        prcId= util.nvl(rs.getString("idn") );            
    }    
        rs.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    return prcId;
    }
    
    public String empid(HttpServletRequest req, String dept) {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    String empId="";    
    String sqlQ="select a.nme_idn from mnme a , nme_dtl b where a.nme_idn = b.nme_idn and mprp='HOD' and txt ='"+dept+"' and a.typ='EMPLOYEE' and a.vld_dte is null and b.vld_dte is null ";
    ResultSet rs = db.execSql("Employee", sqlQ, new ArrayList());
    try {
    if(rs.next()){
        empId= util.nvl(rs.getString("nme_idn") );            
    }    
        rs.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    return empId;
    }
    public ArrayList VRPrprViw(HttpServletRequest req){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("VRViewLst");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
                ResultSet rs1 =
                    db.execSql(" Vw Lst ", "Select prp  from rep_prp where mdl = 'SZP_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                session.setAttribute("VRViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
    
    
    public ArrayList VRPrprViwFail(HttpServletRequest req){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("VRFailViewLst");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
                ResultSet rs1 =
                    db.execSql(" Vw Lst ", "Select prp  from rep_prp where mdl = 'SZPFAIL_VIEW' and flg='Y' order by rnk ",
                               new ArrayList());
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                session.setAttribute("VRFailViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
//    public ArrayList VRGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("SZPKTGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp,flg  from rep_prp where mdl = 'SZP_SRCH' and flg in ('Y','S','M') order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("SZPKTGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        
//        return asViewPrp;
//    }

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
              util.updAccessLog(req,res,"VerificationHkAction", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"VerificationHkAction", "init");
          }
          }
          return rtnPg;
          }
   
    public VerificationHKAction() {
        super();
    }
}
