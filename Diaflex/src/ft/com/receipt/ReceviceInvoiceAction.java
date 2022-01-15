package ft.com.receipt;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;
import ft.com.dao.ObjBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ReceviceInvoiceAction extends  DispatchAction {
    
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
        ReceviceInvoiceForm udf = (ReceviceInvoiceForm)form;
       HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("PAY_RECEIPT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("PAY_RECEIPT");
            allPageDtl.put("PAY_RECEIPT",pageDtl);
            }
            
            
            udf.resetAll();
            info.setPageDetails(allPageDtl);  
            ArrayList typList = new ArrayList();
            ArrayList pageList=(ArrayList)pageDtl.get("REC_TYP");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
                      HashMap  pageDtlMap=(HashMap)pageList.get(j);
                       String fld_nme=(String)pageDtlMap.get("fld_nme");
                       String  fld_ttl=(String)pageDtlMap.get("fld_ttl");
                       String fld_typ=(String)pageDtlMap.get("fld_typ");
                       String  lov_qry=(String)pageDtlMap.get("lov_qry");
                       String dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
                
                       ObjBean objBn = new ObjBean();
                       objBn.setDsc(fld_ttl);
                       objBn.setNme(fld_nme);
                       typList.add(objBn);
                 }}
            udf.setInvTypLst(typList);
           ArrayList slabList = new ArrayList();
           pageList=(ArrayList)pageDtl.get("BROKER_SLAB");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
                      HashMap  pageDtlMap=(HashMap)pageList.get(j);
                       String  fld_ttl=(String)pageDtlMap.get("fld_ttl");
                        String dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
                
                       ObjBean objBn = new ObjBean();
                       objBn.setDsc(fld_ttl);
                       objBn.setNme(dflt_val);
                       slabList.add(objBn);
                 }}
            udf.setSlabTypLst(slabList);
            ArrayList billList = new ArrayList();
            pageList=(ArrayList)pageDtl.get("BILL");
             if(pageList!=null && pageList.size() >0){
             for(int j=0;j<pageList.size();j++){
                       HashMap  pageDtlMap=(HashMap)pageList.get(j);
                        String  fld_ttl=(String)pageDtlMap.get("fld_ttl");
                         String dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
                 
                        ObjBean objBn = new ObjBean();
                        objBn.setDsc(fld_ttl);
                        objBn.setNme(dflt_val);
                        billList.add(objBn);
                  }}
           
            udf.setBillTypLst(billList);
            session.setAttribute("InvDataDtl", new ArrayList());
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
            ReceviceInvoiceForm udf = (ReceviceInvoiceForm)af;
            ArrayList invIdnLst = new ArrayList();
            Enumeration reqNme = req.getParameterNames(); 
            String isRemove = util.nvl((String)udf.getValue("Remove"));
            String typ=util.nvl((String)udf.getValue("typ"));
            while (reqNme.hasMoreElements()) {
                String paramNm = (String) reqNme.nextElement();
                if(paramNm.indexOf("cb_inv_") > -1) {
                    String val = req.getParameter(paramNm);
                    String[] valLst = val.split("_");
                    String idn = valLst[0];
                    String cntIdn = valLst[1];
                    String bill = util.nvl((String)udf.getValue("BILL_"+val));
                    ArrayList params = new ArrayList();
                    if(typ.equals("DLV")){
                    params.add("0");
                    params.add(idn);
                    params.add("0");
                  
                    }else if(typ.equals("LS") || typ.equals("SAL") ){
                    params.add(idn);
                    params.add("0");
                    params.add("0");
                   
                    }else{
                    params.add("0");
                    params.add("0");
                    params.add(idn);
                  
                    }
                    if(!isRemove.equals("")){
                        params = new ArrayList();
                        params.add("CL");
                        params.add(idn);
                        int ct=0;
                        if(typ.equals("DLV")){
                              ct = db.execUpd("update mdlv", "update mdlv set acc_rec=? where idn=?", params);
                        }else if(typ.equals("PUR")){
                             ct = db.execUpd("update mpur", "update mpur set acc_rec=?  where pur_idn=?", params);
                        }else{
                             ct = db.execUpd("update msal", "update msal set acc_rec=?  where idn=?", params);
                        }
                        if(ct>0)
                            invIdnLst.add(idn);
                    }else{
                    String slab = util.nvl((String)udf.getValue("SLAB_"+idn));
                  if(!slab.equals("") || !slab.equals("0")){
                      ArrayList ary = new ArrayList();
                      ary.add(slab);
                      ary.add(idn);
                      if(typ.equals("PUR")){
                      int ct = db.execUpd("update mpur", "update mpur set mbrk2_comm=?  where pur_idn=?", ary);
                      }else{
                         int  ct = db.execUpd("update msal", "update msal set brk2_comm=?  where idn=?", ary);
                      } 
                  }
                String xrt = util.nvl(req.getParameter("cb_xrt_"+idn));
                 if(!xrt.equals("") || !xrt.equals("0")){
                            ArrayList ary = new ArrayList();
                            ary.add(xrt);
                            ary.add(idn);
                            if(typ.equals("PUR")){
                            int ct = db.execUpd("update mpur", "update mpur set exh_rte=?  where pur_idn=?", ary);
                            }else{
                               int  ct = db.execUpd("update msal", "update msal set fnl_exh_rte=?  where idn=?", ary);
                            } 
                    }
                    params.add(bill);  
                    params.add(cntIdn);  
                    String DP_TRF_GL="DP_TRF_GL(pSalIdn=>?,pDlvIdn=>?,pPurIdn=>?,pAccTyp => ?,pCntIdn => ?)";
                    int ct =  db.execCall("DP_TRF_GL", DP_TRF_GL, params);
                    if(ct>0){
                     invIdnLst.add(idn);
                           
                    }
                    }
                }
            }
            
            String dfr = util.nvl((String)udf.getValue("dtefr"));
            String dto = util.nvl((String)udf.getValue("dteto"));
            String dtefrom = " trunc(sysdate) ";
            String dteto = " trunc(sysdate) ";
            
            if(!dfr.equals(""))
              dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
            
            if(!dto.equals(""))
              dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
            
            ArrayList invDtlLst = getInvDataDtl(typ,dtefrom,dteto, db,udf,util);
            req.setAttribute("InvDataDtl", invDtlLst);
            if(!isRemove.equals(""))
           req.setAttribute("msg", "Invoice Remove succesfully.:-"+invIdnLst.toString());
           else
           req.setAttribute("msg", "Invoice Recevie succesfully.:-"+invIdnLst.toString());   
            return am.findForward("load");
        }
    }
    public ActionForward InvDtl(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
    ReceviceInvoiceForm udf = (ReceviceInvoiceForm)form;
    String typ = util.nvl((String)udf.getValue("typ"));
        String dfr = util.nvl((String)udf.getValue("dtefr"));
        String dto = util.nvl((String)udf.getValue("dteto"));
        String dtefrom = " trunc(sysdate) ";
        String dteto = " trunc(sysdate) ";
        
        if(!dfr.equals(""))
          dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
        
        if(!dto.equals(""))
          dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
    ArrayList invDtlLst = getInvDataDtl(typ,dtefrom,dteto, db,udf,util);
    req.setAttribute("InvDataDtl", invDtlLst);
    return am.findForward("load");
    }
   }
    
    
  
    
    public ArrayList getInvDataDtl(String typ,String dteFrm, String dteTo,DBMgr db,ReceviceInvoiceForm udf,DBUtil util){
        ArrayList invDtlList = new ArrayList();
        String dataSql = " select TRNSIDN,REFIDN,TYP,EXH_RTE,to_char(dte,'DD-MON-YYYY') dte,byr,qty,cts,vlu,usdvlu,brk,BRK_COMM,to_number(to_char(dte, 'rrrrmmddhhmmss')) dtenum from GL_RECEIVE_PENDING_V  where typ =?  ";
           if(!dteFrm.equals("") && !dteTo.equals(""))
            dataSql = dataSql +" and trunc(dte) between "+dteFrm+" and "+dteTo ;
        
        ArrayList parsms = new ArrayList();
        parsms.add(typ);
         
      try {
            ArrayList rsLst = db.execSqlLst("data sql", dataSql, parsms);
            PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
                HashMap invDtl = new HashMap();
                String idn =  rs.getString("TRNSIDN");
                invDtl.put("INVIDN", util.nvl(rs.getString("TRNSIDN")));
                invDtl.put("NME", util.nvl(rs.getString("byr")));
                invDtl.put("QTY", util.nvl(rs.getString("qty")));
                invDtl.put("CTS", util.nvl(rs.getString("cts")));
                invDtl.put("VLU", util.nvl(rs.getString("vlu")));
                invDtl.put("USDVLU", util.nvl(rs.getString("usdvlu")));
                invDtl.put("DTE", util.nvl(rs.getString("dte")));
                invDtl.put("XRT", util.nvl(rs.getString("exh_rte")));
                invDtl.put("TYP", util.nvl(rs.getString("typ")));
                invDtl.put("IDN", util.nvl(rs.getString("REFIDN"),"NA"));
                invDtl.put("BRKNME", util.nvl(rs.getString("brk")));
                invDtl.put("BRK_COMM", util.nvl(rs.getString("BRK_COMM")));
                invDtl.put("DTENUM", util.nvl(rs.getString("dtenum")));
                udf.setValue("SLAB_"+idn, util.nvl(rs.getString("BRK_COMM")));
                invDtlList.add(invDtl);
            }
            rs.close();
            psmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      
      return invDtlList;
    }
    
    public ActionForward PktDtl(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
    ReceviceInvoiceForm udf = (ReceviceInvoiceForm)form;
    String refIdn = util.nvl(req.getParameter("refIdn"));
    String typ = util.nvl(req.getParameter("TYP"));
        HashMap viewMap = ViewPrpLst(req,"RECEIPT_VW");
        String pvPrpStr = (String)viewMap.get("STR");
        ArrayList pvPrpLst = (ArrayList)viewMap.get("PVLIST");
        ArrayList lprpLst = (ArrayList)viewMap.get("LPRPLIST");
    if(!refIdn.equals("") && !typ.equals("")){
        String pktDtlSql="";
        if(typ.equals("SAL")){
            pktDtlSql="with STKDTL as  (select a.sk1,a.vnm, a.qty,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , st.mprp, to_char(trunc(b.cts,2),'9990.00') cts ,to_char(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot)),'999,9999,999,990.00') vlu\n" + 
            "from  DLV_PKT_DTL_V b ,stk_dtl st ,mstk a\n" + 
            "where b.mstk_idn=st.mstk_idn and b.idn=? and st.mstk_idn=a.idn \n" + 
            "and  exists (select 1 from rep_prp rp where rp.MDL ='RECEIPT_VW' and st.mprp = rp.prp)) \n" + 
            "Select * from stkDtl PIVOT  ( max(atr)  for  mprp in ( "+pvPrpStr+")  ) order by 1";
        }else if(typ.equals("DLV")){
            pktDtlSql="with STKDTL as  (select a.sk1, a.vnm,a.qty,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , st.mprp, to_char(trunc(b.cts,2),'9990.00') cts ,to_char(trunc(b.cts,2)*(nvl(b.fnl_usd, b.quot)),'999,9999,999,990.00') vlu\n" + 
            "from  DLV_PKT_DTL_V b ,stk_dtl st ,mstk a\n" + 
            "where b.mstk_idn=st.mstk_idn and b.idn=? and st.mstk_idn=a.idn \n" + 
            "and  exists (select 1 from rep_prp rp where rp.MDL ='RECEIPT_VW' and st.mprp = rp.prp)) \n" + 
            "Select * from stkDtl PIVOT  ( max(atr)  for  mprp in ("+pvPrpStr+") ) order by 1";
            
        }else{
            pktDtlSql="with STKDTL as  (\n" + 
            "select nvl(nvl(nvl(st.txt,st.dte),st.num),st.val) atr , st.mprp,b.ref_idn vnm , 1 qty,to_char(trunc(b.cts,2),'9990.00') cts ,to_char(trunc(b.cts,2)*(b.rte),'999,9999,999,990.00') vlu\n" + 
            "from  pur_dtl b ,pur_prp st \n" + 
            "where b.pur_dtl_idn=st.pur_dtl_idn and b.pur_idn=? and nvl(b.flg2, 'NA') <> 'DEL'\n" + 
            "and  exists (select 1 from rep_prp rp where rp.MDL ='RECEIPT_VW' and st.mprp = rp.prp  ))\n" + 
            "Select * from stkDtl PIVOT  ( max(atr)  for  mprp in ("+pvPrpStr+") ) order by 1";  
        }
        ArrayList params = new ArrayList();
        params.add(refIdn);
         ArrayList ItemHdr = new ArrayList();
         ItemHdr.add("VNM");
         ItemHdr.add("QTY");
         ItemHdr.add("CTS");/*  */
         ItemHdr.add("VLU");
        ArrayList pktlist = new ArrayList();
         ArrayList rsLst = db.execSqlLst("data sql", pktDtlSql, params);
         PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
         ResultSet rs = (ResultSet)rsLst.get(1);
         while (rs.next()) {
             HashMap pktDtl=new HashMap();
             String vnm = rs.getString("vnm");
             String qty= rs.getString("qty");
             String cts = rs.getString("cts");
             String vlu= rs.getString("vlu");
             pktDtl.put("VNM", vnm);
             pktDtl.put("QTY", qty);
             pktDtl.put("CTS", cts);
             pktDtl.put("VLU", vlu);
             for(int i=0;i<pvPrpLst.size();i++){
                 String lprp = (String)pvPrpLst.get(i);
                 if(!ItemHdr.contains(lprp))
                     ItemHdr.add(lprp);
                 String lprpKey = (String)lprpLst.get(i);
                 pktDtl.put(lprpKey, util.nvl(rs.getString(lprp)));
             }
             pktlist.add(pktDtl);
         }
         session.setAttribute("pktList", pktlist);
         session.setAttribute("itemHdr", ItemHdr);
         session.setAttribute("EXCL", "NO");
         rs.close();
         psmt.close();
    
     }
        return am.findForward("loadRslt");   
    }
    }
    
    public ActionForward SORT(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
      ArrayList invDtlList = (ArrayList)session.getAttribute("InvDataDtl");
        String prp = req.getParameter("prp");
        String order = req.getParameter("order");
        String typ=req.getParameter("typ");
        CommperSort commSort = new CommperSort(prp,order,typ);
        Collections.sort(invDtlList,commSort) ;
        session.setAttribute("InvDataDtl", invDtlList);
        return am.findForward("load");   
    }
    }
    
    public   HashMap ViewPrpLst(HttpServletRequest req,String mdl) {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr();
                HashMap viewMap = new HashMap();
            if(info!=null){
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            
           
            ArrayList pvlprpLst = new ArrayList();
            ArrayList lprpLst = new ArrayList();
            String mprpStr = "";
              String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||\n" + 
                      "Upper(replace(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT'),' ','_')) \n" + 
                      "str , Upper(replace(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT'),' ','_')) prp , rp.prp lprp From Rep_Prp Rp Where rp.MDL = ? and flg <> 'N' order by srt " ;
                ArrayList  ary = new ArrayList();
                ary.add(mdl);
                  try {
                ResultSet rs = db.execSql("mprp ", mdlPrpsQ, ary);
                while (rs.next()) {
                    String val = util.nvl((String)rs.getString("str"));
                    mprpStr = mprpStr + " " + val;
                    String prp = util.nvl((String)rs.getString("prp"));
                    String lprp = util.nvl((String)rs.getString("lprp"));
                    pvlprpLst.add(prp);
                    lprpLst.add(lprp);
                }
                rs.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
                      viewMap.put("STR", mprpStr);
                      viewMap.put("PVLIST", pvlprpLst);
                      viewMap.put("LPRPLIST",lprpLst);
          

        }
                return viewMap;
            }
    public ReceviceInvoiceAction() {
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
                rtnPg=util.checkUserPageRights("",util.getFullURL(req));
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Recive Invoice", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Recive Invoice", "init");
            }
            }
            return rtnPg;
            }
    
}
