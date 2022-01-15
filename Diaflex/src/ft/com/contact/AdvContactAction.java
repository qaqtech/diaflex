	package ft.com.contact;

	//~--- non-JDK imports --------------------------------------------------------

	import ft.com.*;
	import ft.com.DBMgr;
	import ft.com.DBUtil;
	import ft.com.InfoMgr;
	import ft.com.dao.*;


import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;

import ft.com.masters.ProcessMasterFrm;
import ft.com.report.ReportForm;
import ft.com.website.WebLoginAndSrchDtlForm;

import java.io.IOException;

import java.net.InetSocketAddress;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;

	
	import org.apache.struts.action.ActionForm;
	import org.apache.struts.action.ActionForward;
	import org.apache.struts.action.ActionMapping;
	
	import org.apache.struts.actions.DispatchAction;

	//~--- JDK imports ------------------------------------------------------------

	import java.sql.ResultSet;
	import java.sql.SQLException;

	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
	import java.util.ArrayList;

import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.http.HttpSession;

import net.spy.memcached.MemcachedClient;

public class AdvContactAction extends DispatchAction {
	    private final String formName   = "contact";
	    public AdvContactAction() {
	        super();
	    }

	    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Adv Contact", "load start");
	    AdvContactForm udf = (AdvContactForm) af;
            GenericInterface genericInt = new GenericImpl();
            SearchQuery query = new SearchQuery();
	    udf.reset();
	    HashMap dbinfo = info.getDmbsInfoLst();
	    String cnt = (String)dbinfo.get("CNT");
	    ArrayList rolenmLst=(ArrayList)info.getRoleLst();
	    String usrFlg=util.nvl((String)info.getUsrFlg());
	    String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
            String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
            String dfNmeIdn=util.nvl((String)info.getDfNmeIdn()); 
	    String srch=util.nvl(req.getParameter("srch"));
	    String cntmrg=util.nvl((String)session.getAttribute("cntmrg"));
            ArrayList out = new ArrayList();
            String count="0";
            String sidn="0";
            ArrayList pageList=new ArrayList();
            ArrayList ary=new ArrayList(); 
            if(srch.equals("") && cntmrg.equals("Y"))
                srch="N";
	    int ct = db.execUpd("delete gt", "delete from gt_nme_srch", new ArrayList());
                    
                    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                    HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_SRCH");
                    if(pageDtl==null || pageDtl.size()==0){
                    pageDtl=new HashMap();
                    pageDtl=util.pagedef("CONTACT_SRCH");
                    allPageDtl.put("CONTACT_SRCH",pageDtl);
                    }
                    info.setPageDetails(allPageDtl);                    
	    if(srch.equals("Y")){
	        ArrayList contactSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_csGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_csGNCSrch"); 
	        info.setGncPrpLst(contactSrchList);
	        ArrayList memoList = query.getSrchType(req,res);
//	        udf.setValue("srchTypList", memoList);
                udf.setSrchTypList(memoList);
	        udf.setValue("typ", "WEB");
	        
                String dtePrpQ="select to_char(trunc(sysdate-45), 'dd-mm-rrrr') frmdte,to_char(trunc(sysdate), 'dd-mm-rrrr') todte from dual";

	        ArrayList outLst = db.execSqlLst("Date calc", dtePrpQ,new ArrayList());
	        PreparedStatement pst = (PreparedStatement)outLst.get(0);
	        ResultSet rs = (ResultSet)outLst.get(1);
	        while (rs.next()) { 
	        udf.setValue("dtefr",(util.nvl(rs.getString("frmdte"))));
	        udf.setValue("dteto",(util.nvl(rs.getString("todte"))));
	        }    
	        rs.close(); pst.close();
	    }
	    if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
            }else{
                if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
                pageList= ((ArrayList)pageDtl.get("GRP_CO") == null)?new ArrayList():(ArrayList)pageDtl.get("GRP_CO");
                if(pageList!=null && pageList.size() >0){
                for(int i=0;i<pageList.size();i++){
                out = new ArrayList();
                ary=new ArrayList(); 
                ary.add(sidn);
	        ary.add("nme_v");
	        ary.add("grp_nme_idn");
	        ary.add(dfgrpnmeidn);
	        ary.add("NME");
	        ary.add("S");
	        out.add("I");
	        out.add("I");
	            CallableStatement cst = null;
	            cst = db.execCall(
	                "NME_SRCH_PKG ",
	                "NME_SRCH_PKG.SRCH(pSrchId => ?,pTbl => ?, pFld => ?, pSrchVal => ?, pGrp => ?,pFlg => ?, pIdn => ?,pCnt => ?)", ary, out);
	            sidn = cst.getString(7);
	            count = cst.getString(8);
                  cst.close();
                  cst=null;
                }
                }
                }    
             if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
	         pageList= ((ArrayList)pageDtl.get("EMPLOYEE") == null)?new ArrayList():(ArrayList)pageDtl.get("EMPLOYEE");
	         if(pageList!=null && pageList.size() >0){
	         for(int i=0;i<pageList.size();i++){
                    out = new ArrayList();
	            ary=new ArrayList(); 
	            ary.add(sidn);
	            ary.add("NME_EMP_V");
	            ary.add("emp_idn");
	            ary.add(dfNmeIdn);
	            ary.add("NME");
	            ary.add("S");
	            out.add("I");
	            out.add("I");
	                CallableStatement cst = null;
	                cst = db.execCall(
	                    "NME_SRCH_PKG ",
	                    "NME_SRCH_PKG.SRCH(pSrchId => ?,pTbl => ?, pFld => ?, pSrchVal => ?, pGrp => ?,pFlg => ?, pIdn => ?,pCnt => ?)", ary, out);
	                sidn = cst.getString(7);
	                count = cst.getString(8);
                   cst.close();
                   cst=null;
                 }}
	     } 
            }
            req.setAttribute("searchidn", sidn);
            req.setAttribute("count", count);    
	    udf.setValue("srch", srch); 
            session.setAttribute("srchby", srch);
	    session.setAttribute("cntmrg", "N");
                    util.updAccessLog(req,res,"Adv Contact", "load end");
	    return am.findForward("load");
                }
	    }
	    public ActionForward contactsearch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                    util.updAccessLog(req,res,"Adv Contact", "contactsearch start");
                    int ct = db.execUpd("delete gt", "delete from gt_srch_rslt", new ArrayList());
	        AdvContactForm udf = (AdvContactForm) af;
	        ResultSet rs = null;
	        String reporttyp=util.nvl((String)udf.getValue("reporttyp"));
	        String merge=util.nvl((String)udf.getValue("merge"));
                String nmestatus=util.nvl((String)udf.getValue("nmestatus"),"A");
                info.setNmestatus(nmestatus);
	        if(reporttyp.equals("history")){
	            return historysearch(am,af,req,res);
	        }
	        if(reporttyp.equals("weblast")){
	            return loadweblastlogindtl(am,af,req,res);
	        }
	        if(reporttyp.equals("weblogin")){
	            return loadweblogindtl(am,af,req,res);
	        }
	        if(reporttyp.equals("webaccess")){
	            return loadwebaccessdtl(am,af,req,res);
	        }
                if(reporttyp.equals("loyalty")){
                return loadloyalty(am,af,req,res);
                }
                if(reporttyp.equals("feedback")){
                return loadFeedback(am,af,req,res);
                }
	        ArrayList alphaList = new ArrayList();
	        String page=util.nvl((String)udf.getValue("pagemnl"));
	        if(page.equals(""))
	            page=util.nvl((String)udf.getValue("page"));
                if(!merge.equals("")){
                    String gtView = "select count(*) cnt from gt_nme_srch ";
                    ResultSet rs1 = db.execSql("gtView", gtView, new ArrayList());
                    while (rs1.next()) {
                        page=util.nvl(util.nvl(rs1.getString("cnt")));
                    }
                    rs1.close();
                session.setAttribute("cntmrg", "Y");
                }
              
	        String updateFlg = " update gt_nme_srch a set flg='U' where exists (select 1 from web_usrs b , nmerel c " + 
	        " where b.rel_idn = c.nmerel_idn and b.to_dt is null and c.vld_dte is null and c.nme_idn = a.nme_idn ) ";
	        int cnt = db.execUpd("update flg", updateFlg, new ArrayList());
	        
	        String sqlQ="Select Distinct(Fnme)\n" + 
	        "        From (Select Upper(Substr(B.Fnme,1,1))Fnme From Gt_Nme_Srch A,Mnme B\n" + 
	        "         Where B.Nme_Idn = A.Nme_Idn)\n" + 
	        "        order by fnme";

                    ArrayList outLst = db.execSqlLst("Alp List",sqlQ, new ArrayList());
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
	        while(rs.next()){
	            String letter=util.nvl(rs.getString("fnme"));
	            if(!letter.equals(""));
	            alphaList.add(letter);
	        }
                rs.close(); pst.close();
	        info.setPageCt(Integer.parseInt(page));
	        session.setAttribute("alphaList", alphaList);
	        info.setChkNmeIdnList(new ArrayList());
                    util.updAccessLog(req,res,"Adv Contact", "contactsearch end");
	        return loadSearch(am, af, req, res);
                }
	    }
	    
            
	    public ActionForward loadloyalty(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception  {
	                    HttpSession session = request.getSession(false);
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
	                    rtnPg=init(request,response,session,util);
	                    }else{
	                    rtnPg="connExists";   
	                    }
	                    }else
	                    rtnPg="sessionTO";
	                    if(!rtnPg.equals("sucess")){
	                        return am.findForward(rtnPg);   
	                    }else{
	                        util.updAccessLog(request,response,"Adv Contact", "loadloyalty start");
	                      AdvContactForm udf = (AdvContactForm)af;
	                      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
	                      HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_SRCH");
	                      ArrayList pageList=new ArrayList();
	                      HashMap pageDtlMap=new HashMap();
	                      String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
                pageList= ((ArrayList)pageDtl.get("LOY_SCREEN") == null)?new ArrayList():(ArrayList)pageDtl.get("LOY_SCREEN");	                      
	                      ArrayList dtlList = new ArrayList();
	                      ArrayList ary = new ArrayList();
	                      String cnt="0";
	                      String conQSql="";
	                        String conQ="";
                                String hisfilter=util.nvl((String)udf.getValue("hisfilter"),"N");
                                if(hisfilter.equals("Y")){
                                srchGeneric(request,udf);
	                        String updateFlg = "delete gt_nme_srch a where not exists (select 1 from Gt_Srch_Rslt b , nmerel c \n" + 
	                          "                where b.Rln_Idn = c.nmerel_idn and c.nme_idn = a.nme_idn )";
	                        int del = db.execUpd("update flg", updateFlg, new ArrayList());
                                }
	                        if(pageList!=null && pageList.size() >0){
	                        for(int k=0;k<pageList.size();k++){
	                        pageDtlMap=(HashMap)pageList.get(k);
	                        String lprp=(String)pageDtlMap.get("dflt_val");
                                    conQSql+=",byr.get_AttrData('"+lprp+"',a.nme_idn) "+lprp;
                                }
                                }
	                    String gtView = "select count(*) cnt from gt_nme_srch ";
	                    try {
	                        ArrayList outLst = db.execSqlLst("gtView", gtView, new ArrayList());
	                        PreparedStatement pst = (PreparedStatement)outLst.get(0);
	                        ResultSet rs1 = (ResultSet)outLst.get(1);
	                    while (rs1.next()) {
	                        cnt=util.nvl(util.nvl(rs1.getString("cnt")));
	                    }
	                        rs1.close();
	                        pst.close();
	                    } catch (SQLException sqle) {
	                            // TODO: Add catch code
	                            sqle.printStackTrace();
	                    }
	                    if(!cnt.equals("0")){
	                      conQ="   and exists (select 1 from gt_nme_srch where a.nme_idn = gt_nme_srch.nme_idn) ";
	                    }
	                    
	                      String loginDtlSql = "select b.nme emp,a.nme byr\n" + conQSql+
	                      " from nme_v a , nme_v b\n" + 
	                      " Where a.emp_idn=b.Nme_Idn\n" +conQ+ 
	                      " order by b.nme,b.nme";
//	                    System.out.println(loginDtlSql); 
	                    ArrayList rsLst = db.execSqlLst("loginDtl", loginDtlSql, ary);
	                    PreparedStatement pst = (PreparedStatement)rsLst.get(0);
	                    ResultSet rs = (ResultSet)rsLst.get(1);
	                    int srno=0;
	                    while(rs.next()){
	                        srno = srno+1;
	                        HashMap dtls = new HashMap();
	                        dtls.put("SR No", String.valueOf(srno));
	                        dtls.put("Sale EX", util.nvl(rs.getString("emp")));
	                        dtls.put("Buyer", util.nvl(rs.getString("byr")));
	                        for(int k=0;k<pageList.size();k++){
	                        pageDtlMap=(HashMap)pageList.get(k);
	                        String lprp=(String)pageDtlMap.get("dflt_val");
	                        fld_ttl=(String)pageDtlMap.get("fld_ttl");
	                        dtls.put(fld_ttl, util.nvl(rs.getString(lprp)));
	                        }
	                        dtlList.add(dtls);
	                    }
	                    rs.close(); pst.close();

	                    ArrayList itemHdr = new ArrayList();
	                    itemHdr.add("SR No");
                            itemHdr.add("Sale EX");
	                    itemHdr.add("Buyer");
	                    for(int k=0;k<pageList.size();k++){
	                        pageDtlMap=(HashMap)pageList.get(k);
	                        fld_ttl=(String)pageDtlMap.get("fld_ttl");
	                        itemHdr.add(fld_ttl);
	                    }
	                    request.setAttribute("itemHdr",itemHdr);
	                    request.setAttribute("dtlList", dtlList);
	                    udf.setPktList(dtlList);
	                    udf.setReportNme("Loyalty Report");
	                        util.updAccessLog(request,response,"Adv Contact", "loadloyalty end");
	                   return am.findForward("loadloyrpt");
	                    }
	                }           
	    public ActionForward loadSearch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                    util.updAccessLog(req,res,"Adv Contact", "loadSearch start");
                    AdvContactForm udf = (AdvContactForm) af;
	            HashMap  formFields = info.getFormFields(formName);
                    String nmestatus=util.nvl((String)info.getNmestatus());
	            if ((formFields == null) || (formFields.size() == 0)) {
	                formFields = util.getFormFields(formName);
	            }

	            UIForms  uiForms = (UIForms) formFields.get("DAOS");
	            ArrayList  daos    = uiForms.getFields();
	            FormsUtil helper  = new FormsUtil();
	            ArrayList errors  = new ArrayList();
	            helper.init(db, util, info);
                    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                    HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_SRCH");
                    ArrayList pageList=new ArrayList();
	        HashMap tbls       = new HashMap();
	        ArrayList    params     = new ArrayList();
	        String   namelike=util.nvl(req.getParameter("namelike")).trim();
	        String    srchQ      = " and exists (select 1 from gt_nme_srch where mnme.nme_idn = gt_nme_srch.nme_idn) ",
	                  srchFields = "";
	        
	        srchFields = getSrchFields(daos);
                if(nmestatus.equals("A"))
	        srchQ      += " and vld_dte is null ";
                else
                srchQ      += " and vld_dte is not null ";
	        if(!namelike.equals("")){
	            srchQ      += " and fnme like '"+namelike+"%' ";   
	        }

	        ArrayList list = getSrchList(srchFields, srchQ, params, daos, "LOADSRCH",uiForms,req,udf);

	        info.setNmeSrchList(list);
	      
	        ArrayList nonUsrList = new ArrayList();
	        ArrayList outLst = db.execSqlLst("webusr", "select nme_idn from gt_nme_srch where flg='N'", new ArrayList());
	        PreparedStatement pst = (PreparedStatement)outLst.get(0);
	        ResultSet rs = (ResultSet)outLst.get(1);
	        while(rs.next()){
	            nonUsrList.add(rs.getString("nme_idn"));
	        }
	        req.setAttribute("NonUsrList", nonUsrList);
	        rs.close(); pst.close();
                
                pageList= ((ArrayList)pageDtl.get("GSTIN") == null)?new ArrayList():(ArrayList)pageDtl.get("GSTIN");
                if(pageList!=null && pageList.size() >0){
                HashMap gstDtl=new HashMap();
                outLst = db.execSqlLst("GstIn", "select a.nme_idn,b.txt \n" + 
                "from \n" + 
                "gt_nme_srch a,nme_dtl b\n" + 
                "where\n" + 
                "a.nme_idn=b.nme_idn and b.mprp='GSTIN' and b.vld_dte is null", new ArrayList());
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                    gstDtl.put(util.nvl(rs.getString("nme_idn")), util.nvl(rs.getString("txt")));
                }
                rs.close(); pst.close();
                session.setAttribute("gstDtl", gstDtl);
                }
                    
//	        HashMap contViewMap = (HashMap)session.getAttribute("ContViewMap");
//	        if(contViewMap==null){
//	            try {
//	            HashMap dbinfo = info.getDmbsInfoLst();
//	            String cnt=util.nvl((String)dbinfo.get("CNT"));
//	            String mem_ip=util.nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//	            int mem_port=Integer.parseInt(util.nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//	            MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//	            contViewMap=(HashMap)mcc.get(cnt+"_contViewMap");
//	            if(contViewMap==null){
//	            contViewMap=new HashMap();
//	        String contVw =  " select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + 
//	                         " b.mdl = 'JFLEX' and b.nme_rule = 'CONT_VW' and a.til_dte is null order by a.srt_fr ";
//	        outLst = db.execSqlLst("contView", contVw, new ArrayList());
//	         pst = (PreparedStatement)outLst.get(0);
//	         rs = (ResultSet)outLst.get(1);
//	        while(rs.next()){
//	            contViewMap.put(util.nvl(rs.getString("dsc")), util.nvl(rs.getString("chr_fr")));
//	        }
//	            rs.close(); pst.close();
//	            pst.close();
//	            rs.close(); pst.close();
//	            Future fo = mcc.delete(cnt+"_contViewMap");
//	            System.out.println("add status:_contViewMap" + fo.get());
//	            fo = mcc.set(cnt+"_contViewMap", 24*60*60, contViewMap);
//	            System.out.println("add status:_contViewMap" + fo.get());
//	            }
//	            mcc.shutdown();
//	            session.setAttribute("ContViewMap", contViewMap);
//	            }catch(Exception ex){
//	             System.out.println( ex.getMessage() );
//	            }
//	        }
	        
	        info.setNmemassmaillist(new ArrayList());
                    util.updAccessLog(req,res,"Adv Contact", "loadSearch end");
	        return am.findForward("searchlst");
                }
	    }
	    public ActionForward contactEntries(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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

	        ResultSet rs = null;
	        res.setContentType("text/xml"); 
	        res.setHeader("Cache-Control", "no-cache"); 
	        ArrayList RtnMsg = new ArrayList();
	        ArrayList out = new ArrayList();
	        ArrayList ary= new ArrayList();
	        StringBuffer sb = new StringBuffer();
	        int cnt=0;
	        int sidn=0;
	        String srchid=util.nvl(req.getParameter("srchid"),"0");
	        String tblnm=util.nvl(req.getParameter("tbl"));
	        String tblfld=util.nvl(req.getParameter("tblfld"));
	        String srchgrp=util.nvl(req.getParameter("srchgrp"));
	        String val=util.nvl(req.getParameter("val"));
	        String flg=util.nvl(req.getParameter("flg"));
                String nmestatus=util.nvl((String)info.getNmestatus());
	        String execnn=util.nvl(req.getParameter("execnn"),"N");
                String procCallQ="NME_SRCH_PKG.SRCH(pSrchId => ?,pTbl => ?, pFld => ?, pSrchVal => ?, pGrp => ?,pFlg => ?, pIdn => ?,pCnt => ?)";
                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_SRCH");
                ArrayList pageList=new ArrayList();
                String statusconQ="";
                int paramCnt=7;
                pageList= ((ArrayList)pageDtl.get("STATUS") == null)?new ArrayList():(ArrayList)pageDtl.get("STATUS");
                if(pageList!=null && pageList.size() >0){
                for(int i=0;i<pageList.size();i++){
                statusconQ="A";
                }
                }
                if(statusconQ.equals("A"))
                procCallQ="NME_SRCH_PKG.SRCH(pSrchId => ?,pTbl => ?, pFld => ?, pSrchVal => ?, pGrp => ?,pFlg => ?,pStt => ?, pIdn => ?,pCnt => ?)";
                if(val.equals(""))
	        val="NN";
//	        String[] param = val.split(",");
                if(flg.equals("T")){
//                    flg="S";
                    flg="M";
                if(!srchid.equals("0") && !srchid.equals("") && execnn.equals("Y")){
                    ary= new ArrayList();
                    out= new ArrayList();
                    ary.add(srchid);
                    ary.add(tblnm);
                    ary.add(tblfld);
                    ary.add("NN");
                    ary.add(srchgrp);
                    ary.add(flg);
                    if(statusconQ.equals("A"))
                    ary.add(nmestatus); 
                    out.add("I");
                    out.add("I");
                        CallableStatement cst = null;
                        cst = db.execCall(
                            "NME_SRCH_PKG ",
                            procCallQ, ary, out);
                        sidn = cst.getInt(ary.size()+1);
                        cnt = cst.getInt(ary.size()+2);
                        srchid=String.valueOf(sidn);
                  cst.close();
                  cst=null;
                }
                }
	        if(flg.equals("T"))
	        flg="M";
	        if(!flg.equals("M") && !flg.equals("DT") && !flg.equals("DR"))
	        flg="S";
//                for(int i=0;i<param.length;i++){
	        ary= new ArrayList();
	        out= new ArrayList();
                ary.add(srchid);
	        ary.add(tblnm);
	        ary.add(tblfld);
	        ary.add(val);
//                if(!flg.equals("M") && !flg.equals("DT") && !flg.equals("DR")){
//	        ary.add(param[i]);
//                }else{
//                ary.add(val);
//                i=param.length;
//                }
	        ary.add(srchgrp);
	        ary.add(flg);
                if(statusconQ.equals("A"))
                ary.add(nmestatus); 
	        out.add("I");
	        out.add("I");
	            CallableStatement cst = null;
	            cst = db.execCall(
	                "NME_SRCH_PKG ",
	                procCallQ, ary, out);
	            sidn = cst.getInt(ary.size()+1);
	            cnt = cst.getInt(ary.size()+2);
	            srchid=String.valueOf(sidn);
                  cst.close();
                  cst=null;
//	        }
                if(sidn>0){
	        sb.append("<consrch>");
	        sb.append("<srchid>"+sidn+"</srchid>");
	        sb.append("<cnt>"+cnt+"</cnt>");
	        sb.append("</consrch>");
	        }else{
	            sb.append("<consrch>");
	            sb.append("<srchid>0</srchid>");
	            sb.append("<cnt>0</cnt>");
	            sb.append("</consrch>");
	        }
	        
	        res.getWriter().write("<consrchs>" +sb.toString()+ "</consrchs>");
	        
	        return null;
                }
	    }   
	    public ActionForward contactEmailId(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
	        HttpSession session = request.getSession(false);
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
	        rtnPg=init(request,response,session,util);
	        }else
	        rtnPg="sessionTO";
	        if(!rtnPg.equals("sucess")){
	            return mapping.findForward(rtnPg);   
	        }else{
	        AdvContactForm udf = (AdvContactForm) form;
	        ArrayList ary = new ArrayList();
	        ArrayList maillst = info.getNmemassmaillist();
	        ArrayList chkNmeIdnList = (info.getChkNmeIdnList() == null)?new ArrayList():(ArrayList)info.getChkNmeIdnList();
	        String nmeIdnLst = util.nvl(request.getParameter("nmeId"));
	        String stt = util.nvl(request.getParameter("stt"));
	        if(nmeIdnLst.equals("")){
	            nmeIdnLst=startEndIdn(request);
	        }
	        String[] nmeLst = nmeIdnLst.split(",");
	        int loopCnt = 1 ;
//	        System.out.println(nmeLst.length);
	        float loops = ((float)nmeLst.length)/900;
	        loopCnt = Math.round(loops);
	            if(nmeLst.length <= 900 || new Float(loopCnt)>=loops) {
	            
	        } else
	            loopCnt += 1 ;
	        if(loopCnt==0)
	            loopCnt += 1 ;
	        int fromLoc = 0 ;
	        int toLoc = 0 ;
	        for(int i=1; i <= loopCnt; i++) {
	               int aryLoc = Math.min(i*900, nmeLst.length-1) ;
	               String lookupVnm = nmeLst[aryLoc];
	               if(i == 1)
	                   fromLoc = 0 ;
	               else
	                   fromLoc = toLoc+1;
	               toLoc = Math.min(nmeIdnLst.lastIndexOf(lookupVnm) + lookupVnm.length(), nmeIdnLst.length());
	               String vnmSub = nmeIdnLst.substring(fromLoc, toLoc);
               
               String flg="N";
                    if(stt.equals("true"))
                        flg="M";
                    ary = new ArrayList();
                    ary.add(flg);
                String upGtSrch = "update gt_nme_srch set flg = ? where nme_idn in ("+vnmSub+") ";
//                String upGtSrch= " update gt_nme_srch a set flg = ? where exists (select 1 from TABLE(PARSE_TO_TBL('"+vnmSub+"')) b where a.nme_idn = b.vnm)";
	        int ct=db.execUpd("update gt_nme_srch", upGtSrch, ary);
	        
	        String sqlEml = "select byr.get_eml(nme_idn) eml,nme_idn from gt_nme_srch where nme_idn in ("+vnmSub+") ";

                    ArrayList outLst = db.execSqlLst("sql",sqlEml, new ArrayList());
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs = (ResultSet)outLst.get(1);
	        while(rs.next()){
	            String eml = util.nvl(rs.getString("eml"));
	            String nmeidn = util.nvl(rs.getString("nme_idn"));
	            if(stt.equals("true")){
	            if(!maillst.contains(eml) && !eml.equals(""))
	             maillst.add(eml);
	            if(!chkNmeIdnList.contains(nmeidn))
	            chkNmeIdnList.add(nmeidn);
	            }else{
	            maillst.remove(eml);
	            chkNmeIdnList.remove(nmeidn);
	            }
	        }
	        rs.close(); pst.close();
                }
	       info.setNmemassmaillist(maillst);
	       info.setChkNmeIdnList(chkNmeIdnList);
	        response.setContentType("text/xml");
	        response.setHeader("Cache-Control", "no-cache");
	        response.getWriter().write("<msg>yes</msg>");
	        return null;
                }
	    }
	    public ActionForward contactmerge(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
                HttpSession session = request.getSession(false);
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
                rtnPg=init(request,response,session,util);
                }else
                rtnPg="sessionTO";
                if(!rtnPg.equals("sucess")){
                    return mapping.findForward(rtnPg);   
                }else{
                    util.updAccessLog(request,response,"Adv Contact", "contactmerge start");
	        AdvContactForm udf = (AdvContactForm) form;
	        ArrayList chkNmeIdnList = (info.getChkNmeIdnList() == null)?new ArrayList():(ArrayList)info.getChkNmeIdnList();
                String masterIdn = util.nvl(request.getParameter("masteridn"));
                ArrayList ary = new ArrayList();
                if(chkNmeIdnList.contains(masterIdn))
                chkNmeIdnList.remove(chkNmeIdnList.indexOf(masterIdn));
            if(chkNmeIdnList.size()>0 && !masterIdn.equals("")){
                for(int i=0;i<chkNmeIdnList.size();i++){
                ary = new ArrayList(); 
                ary.add(chkNmeIdnList.get(i));  
                ary.add(masterIdn); 
                int ct = db.execCall("Merge Proc", "NME_SRCH_PKG.MERGE_CONTACT( pIdn =>?, pMstIdn=> ?)", ary);
                }
            }
                    util.updAccessLog(request,response,"Adv Contact", "contactmerge end");
            return load(mapping,form,request,response);
                }
            }
	    public String startEndIdn(HttpServletRequest req) {
	            HttpSession session = req.getSession(false);
	            InfoMgr info = (InfoMgr)session.getAttribute("info");
	            DBUtil util = new DBUtil();
	            DBMgr db = new DBMgr(); 
	            db.setCon(info.getCon());
	            util.setDb(db);
	            util.setInfo(info);
	            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
	            util.setLogApplNm(info.getLoApplNm());
	            int startR=0;
	            int endR = 0;
	            int totalRecord = 0;
	            String nmeIdnLst="";
	            List subnmeList = new ArrayList();
	            ArrayList nmeSrchList = (info.getNmeSrchList() == null)?new ArrayList():(ArrayList)info.getNmeSrchList();
	            int nmeSrchListsz=nmeSrchList.size();
	            int iPageNo =info.getIPageNo();;
	            int recordShow =info.getPageCt();
	            totalRecord= nmeSrchListsz;
	             if(iPageNo==0)
	             startR=0;
	            else
	             startR = (iPageNo-1)*recordShow;
	            endR = startR + recordShow;
	            if(totalRecord < endR){
	                endR = totalRecord;
	            }
	            subnmeList = nmeSrchList.subList(startR,endR);
	            for(int i=0; i < subnmeList.size(); i++) {
	            MNme nme = (MNme)subnmeList.get(i);
	            nmeIdnLst+=","+nme.getIdn();
	            }
	            nmeIdnLst=nmeIdnLst.replaceFirst(",", "");
	            return nmeIdnLst ;
	        }
	    public ActionForward delete(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                    util.updAccessLog(req,res,"Adv Contact", "delete start");
	        AdvContactForm udf = (AdvContactForm) af;
                MailAction mailFt = new MailAction();
	        ArrayList mjanIsidnLst=new ArrayList();
	        ArrayList mjanApidnLst=new ArrayList();
	        ArrayList msalidnLst=new ArrayList();
	        ArrayList params = new ArrayList();
	        String delIdn = req.getParameter("nmeIdn");
	        String msg="";
	        String mjanQ="select distinct a.idn\n" + 
	        "                           from jandtl a, mjan b \n" + 
	        "                           where a.idn = b.idn and a.stt = 'IS'  \n" + 
	        "                          and b.nme_idn =?\n"; 
	        params = new ArrayList();
	        params.add(delIdn);
                    ArrayList outLst = db.execSqlLst("mjanQ", mjanQ, params);
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs = (ResultSet)outLst.get(1);
	        while (rs.next()) {
	        mjanIsidnLst.add(util.nvl(rs.getString("idn")));
	        }
                    rs.close(); pst.close();
	        mjanQ=" select distinct c.idn  from jandtl a, mstk b , mjan c\n" + 
	        "             where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='AP' and c.typ like '%AP' and c.stt='IS'\n" + 
	        "             and c.nme_idn=?\n" + 
	        "             and b.stt in ('MKAP','MKWA')";
	                params = new ArrayList();
	                params.add(delIdn);
                    outLst = db.execSqlLst("mjanQ", mjanQ, params);
                    pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
	        while (rs.next()) {
	        mjanApidnLst.add(util.nvl(rs.getString("idn")));
	        }
                    rs.close(); pst.close();
	        String msalQ="select distinct c.idn  from jansal a, mstk b , msal c \n" + 
	        "             where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='SL' \n" + 
	        "             and c.typ in ('SL') and c.stt='IS'\n" + 
	        "             and c.nme_idn=?";
	        params = new ArrayList();
	        params.add(delIdn);
                    outLst = db.execSqlLst("msalQ", msalQ,params);
                    pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
	        while (rs.next()) {
	            msalidnLst.add(util.nvl(rs.getString("idn")));
	        }
                    rs.close(); pst.close();
	        if(mjanIsidnLst.size()==0 && msalidnLst.size()==0 && mjanApidnLst.size()==0){
//	        String delQ   = " update mnme set vld_dte = sysdate where nme_idn = ? ";
//	        params = new ArrayList();
//	        params.add(delIdn);
//	        int cnt = db.execUpd(" Del " + delIdn, delQ, params);
	        String delNmeRel = "update nmerel set vld_dte = sysdate where nme_idn = ? ";
	        int cnt = db.execUpd("delNmeRel", delNmeRel, params);
	        String delWebUsr = "update web_usrs a set to_dt = sysdate " +
                                    " where exists (select 1 from nmerel b where a.rel_idn = b.nmerel_idn and b.nme_idn = ?) and to_dt is null" ;
	        cnt = db.execUpd("delWebUsr", delWebUsr, params);
                String delnmedtl = "update nme_dtl set vld_dte = sysdate where nme_idn = ?" ;
                cnt = db.execUpd("delnmedtl", delnmedtl, params);
                String delmaddr = "update maddr set vld_dte = sysdate where nme_idn = ?" ;
                cnt = db.execUpd("delnmedtl", delmaddr, params);
                String delweb = "update web_inv_dtl wi set wi.alc_stt='NA' where exists (select 1 from  web_minv wm where wi.inv_id=wm.inv_id and wm.mcust_idn=?) and nvl(wi.alc_stt, 'NA') = 'NA'" ;
                cnt = db.execUpd("web_inv_dtl", delweb, params);
                mailFt.sendmail(req ,res ,delIdn ,"CON_DEL" ,"BYR");
                }
	        if(mjanIsidnLst.size()>0){
	            String mjanIdn=mjanIsidnLst.toString();
	            mjanIdn = mjanIdn.replaceAll("\\[", "");
	            mjanIdn = mjanIdn.replaceAll("\\]", "");
	            msg=" Memo Issue Idn:-"+mjanIdn;
	        }
	        if(mjanApidnLst.size()>0){
	            String mjanIdn=mjanApidnLst.toString();
	            mjanIdn = mjanIdn.replaceAll("\\[", "");
	            mjanIdn = mjanIdn.replaceAll("\\]", "");
	            msg=" Memo Approve Idn:-"+mjanIdn;
	        }
	        if(msalidnLst.size()>0){
	            String msalIdn=msalidnLst.toString();
	            msalIdn = msalIdn.replaceAll("\\[", "");
	            msalIdn = msalIdn.replaceAll("\\]", "");
	            msg=" SaleIdn:-"+msalIdn;
	        }
	        if(!msg.equals("")){
	            req.setAttribute("MSG", "Please take return "+msg+" then Only Possible To delete Contact");
	        }
                    util.updAccessLog(req,res,"Adv Contact", "delete end");
	        return loadSearch(am, af, req, res);
                }
	    }


	    public ActionForward active(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
	            util.updAccessLog(req,res,"Adv Contact", "delete start");
	        AdvContactForm udf = (AdvContactForm) af;
	        MailAction mailFt = new MailAction();
	        ArrayList mjanIsidnLst=new ArrayList();
	        ArrayList mjanApidnLst=new ArrayList();
	        ArrayList msalidnLst=new ArrayList();
	        ArrayList params = new ArrayList();
	        String delIdn = req.getParameter("nmeIdn");
	        String nmestatus=util.nvl((String)info.getNmestatus());
	        String msg="";
	        String delQ   = " update mnme set vld_dte = null where nme_idn = ? ";
	        params = new ArrayList();
	        params.add(delIdn);
	        int cnt = db.execUpd(" Del " + delIdn, delQ, params);
	        String delNmeRel = "update nmerel set vld_dte = null where nme_idn = ? ";
	        cnt = db.execUpd("delNmeRel", delNmeRel, params);
	        String delWebUsr = "update web_usrs a set to_dt = null " +
	                            " where exists (select 1 from nmerel b where a.rel_idn = b.nmerel_idn and b.nme_idn = ?) and to_dt is null" ;
	        cnt = db.execUpd("delWebUsr", delWebUsr, params);
	        String delnmedtl = "update nme_dtl set vld_dte = null where nme_idn = ?" ;
	        cnt = db.execUpd("delnmedtl", delnmedtl, params);
	        String delmaddr = "update maddr set vld_dte = null where nme_idn = ?" ;
	        cnt = db.execUpd("delnmedtl", delmaddr, params);
	        util.updAccessLog(req,res,"Adv Contact", "delete end");
	        return loadSearch(am, af, req, res);
	        }
	    }
	    public ActionForward search(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
                    util.updAccessLog(req,res,"Adv Contact", "search start");
	            HashMap  formFields = info.getFormFields(formName);

	            if ((formFields == null) || (formFields.size() == 0)) {
	                formFields = util.getFormFields(formName);
	            }

	            UIForms  uiForms = (UIForms) formFields.get("DAOS");
	            ArrayList  daos    = uiForms.getFields();
	            FormsUtil helper  = new FormsUtil();
	            ArrayList errors  = new ArrayList();
	            helper.init(db, util, info);
	        AdvContactForm udf = (AdvContactForm) af;

	        HashMap tbls       = new HashMap();
	        ArrayList    params     = new ArrayList();
	        String    srchQ      = "",
	                  srchFields = "";

	        for (int i = 0; i < daos.size(); i++) {
	            UIFormsFields dao    = (UIFormsFields) daos.get(i);
	            String        lFld   = dao.getFORM_FLD();
	            String        fldTyp = dao.getFLD_TYP();
	            String        tblNm  = dao.getTBL_NME();

	            // tbls.put(tblNm, tbls.size());
	            // String tblFld = tblNm + "." + dao.getTBL_FLD().toLowerCase();
	            String tblFld = dao.getTBL_FLD().toLowerCase();
	            String usrVal = util.nvl((String) udf.getValue(lFld), "NA");

	            // util.SOP(tblFld + " : " + usrVal);
	            if ((usrVal.equals("0")) || (usrVal.equals(""))) {
	                usrVal = "NA";
	            }

	            if (!(usrVal.equals("NA"))) {
	                srchQ += " and upper(" + tblFld + ")  like upper(?) ";
	                params.add("%" + usrVal + "%");
	            }

	            // srchFields += ", " + tblFld   ;
	        }

	        srchFields = getSrchFields(daos);
	        srchQ      += " and vld_dte is null ";

	        ArrayList list = getSrchList(srchFields, srchQ, params, daos, "SRCH",uiForms,req,udf);

	        info.setNmeSrchList(list);
	        
	        ArrayList nonUsrList = new ArrayList();
	        ArrayList outLst = db.execSqlLst("webusr", "select nme_idn from gt_nme_srch where flg='N'", new ArrayList());
	        PreparedStatement pst = (PreparedStatement)outLst.get(0);
	        ResultSet rs = (ResultSet)outLst.get(1);
	        while(rs.next()){
	            nonUsrList.add(rs.getString("nme_idn"));
	        }
                    rs.close(); pst.close();
	        req.setAttribute("NonUsrList", nonUsrList);
//                    HashMap contViewMap = (HashMap)session.getAttribute("ContViewMap");
//                    if(contViewMap==null){
//                        try {
//                        HashMap dbinfo = info.getDmbsInfoLst();
//                        String cnt=util.nvl((String)dbinfo.get("CNT"));
//                        String mem_ip=util.nvl((String)dbinfo.get("MEM_IP"),"13.229.255.212");
//                        int mem_port=Integer.parseInt(util.nvl((String)dbinfo.get("MEM_PORT"),"11211"));
//                        MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(mem_ip, mem_port));
//                        contViewMap=(HashMap)mcc.get(cnt+"_contViewMap");
//                        if(contViewMap==null){
//                        contViewMap=new HashMap();
//                    String contVw =  " select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + 
//                                     " b.mdl = 'JFLEX' and b.nme_rule = 'CONT_VW' and a.til_dte is null order by a.srt_fr ";
//                    outLst = db.execSqlLst("contView", contVw, new ArrayList());
//                     pst = (PreparedStatement)outLst.get(0);
//                     rs = (ResultSet)outLst.get(1);
//                    while(rs.next()){
//                        contViewMap.put(util.nvl(rs.getString("dsc")), util.nvl(rs.getString("chr_fr")));
//                    }
//                        rs.close(); pst.close();
//                        pst.close();
//                        rs.close(); pst.close();
//                        Future fo = mcc.delete(cnt+"_contViewMap");
//                        System.out.println("add status:_contViewMap" + fo.get());
//                        fo = mcc.set(cnt+"_contViewMap", 24*60*60, contViewMap);
//                        System.out.println("add status:_contViewMap" + fo.get());
//                        }
//                        mcc.shutdown();
//                        session.setAttribute("ContViewMap", contViewMap);
//                        }catch(Exception ex){
//                         System.out.println( ex.getMessage() );
//                        }
//                    }
                    util.updAccessLog(req,res,"Adv Contact", "search end");
	        return am.findForward("searchlst");
                }
	    }

	    // loadSearch
	    public ArrayList getSrchList(String srchFields, String srchQ, ArrayList params, ArrayList daos, String typ,UIForms  uiForms,HttpServletRequest req,AdvContactForm udf) {
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
                ArrayList list = new ArrayList();

	        try {
	        
	            String getDataQ = " select nme_idn ,emp_idn " + srchFields + " from mnme where 1 =1 " + srchQ
	                              + " order by fnme, mnme, lnme ";
	            ArrayList outLst = db.execSqlLst(" get Search data", getDataQ, params);
	            PreparedStatement pst = (PreparedStatement)outLst.get(0);
	            ResultSet rs = (ResultSet)outLst.get(1);
	            while (rs.next()) {
	                MNme   nme    = new MNme();
	                String nmeIdn = rs.getString(1);
	                String empidn = rs.getString(2);

	                nme.setIdn(nmeIdn);
	                nme.setEmp_idn(empidn);

	                // nme.setValue("idn", nmeIdn);
	                for (int i = 0; i < daos.size(); i++) {
	                    UIFormsFields dao    = (UIFormsFields) daos.get(i);
	                    String        tblFld = dao.getTBL_FLD().toLowerCase();
	                    String        lFld   = dao.getFORM_FLD();
	                    String        dbVal  = util.nvl(rs.getString(tblFld));
	                    String fldAlias = util.nvl(dao.getALIAS());
	                    String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);
	                    if(aliasFld.length() > 0){
	                        udf.setValue(aliasFld, util.nvl(rs.getString(aliasFld)));
	                        nme.setValue(aliasFld, util.nvl(rs.getString(aliasFld)));                     

	                    }
	                                
	                    if (typ.equalsIgnoreCase("VIEW")) {
	                        udf.setValue(lFld, dbVal);
	                    }

	                    nme.setValue(lFld, dbVal);
	                }

	                list.add(nme);
	            }
	            rs.close(); pst.close();
	        } catch (SQLException sqle) {

	            // TODO: Add catch code
	            sqle.printStackTrace();
	        }

	        return list;
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
	                rtnPg=util.checkUserPageRights("contact/advcontact.do?","");
	                if(rtnPg.equals("unauthorized"))
	                util.updAccessLog(req,res,"Adv Contact", "Unauthorized Access");
	                else
	                util.updAccessLog(req,res,"Adv Contact", "init");
	            }
	            }
	            return rtnPg;
	         }

	    public String getSrchFields(ArrayList daos) {
	        String srchFields = " nme_idn ";
	        JspUtil util=new JspUtil();
	        ArrayList ukFld = new ArrayList();

	        for (int j = 0; j < daos.size(); j++) {
	            UIFormsFields dao     = (UIFormsFields) daos.get(j);
	            String        lTblFld = dao.getTBL_FLD().toLowerCase();
	            String        fldTyp  = dao.getFLD_TYP();

	            if (fldTyp.equalsIgnoreCase("DT")) {
	                lTblFld = "to_char(" + lTblFld + ", 'dd-mm-rrrr') " + lTblFld;
	            }

	            if (util.nvl(dao.getFLG()).equals("UK")) {
	                ukFld.add(lTblFld);
	            }

	            String delim = ", ";

	            /*
	             * if (j==0) {
	             *   delim = "";
	             * }
	             */
	            
	            srchFields += delim + lTblFld;
	            if (util.nvl(dao.getALIAS()).length() > 0) {
	                srchFields += delim + dao.getALIAS();
	            }
	        }

	        return srchFields;
	    }
	    
	    
	    public void addParty(HttpServletRequest req , HttpServletResponse res){
	            HttpSession session = req.getSession(false);
	            InfoMgr info = (InfoMgr)session.getAttribute("info");
	            DBUtil util = new DBUtil();
	            DBMgr db = new DBMgr(); 
	            db.setCon(info.getCon());
	            util.setDb(db);
	            util.setInfo(info);
	            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
	            util.setLogApplNm(info.getLoApplNm());
	        String party = "select nme_idn, nme from nme_v a " +
	            " where exists (select 1 from nmerel b where a.nme_idn = b.nme_idn and b.vld_dte is null and flg = 'CNF') order by nme";
	        ArrayList partyList = new ArrayList();

	            ArrayList outLst = db.execSqlLst("byr", party, new ArrayList());
	            PreparedStatement pst = (PreparedStatement)outLst.get(0);
	            ResultSet rs = (ResultSet)outLst.get(1);
	        try {
	            while (rs.next()) {
	                ByrDao byr = new ByrDao();
	                byr.setByrIdn(rs.getInt("nme_idn"));
	                byr.setByrNme(util.nvl(rs.getString("nme")));
	                partyList.add(byr);
	            }
	            rs.close(); pst.close();
	        } catch (SQLException sqle) {
	            // TODO: Add catch code
	            sqle.printStackTrace();
	        }
	            session.setAttribute("partyList", partyList);
	        }
	    public ActionForward loadwebaccessdtl(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception  {
	        HttpSession session = request.getSession(false);
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
	        rtnPg=init(request,response,session,util);
	        }else
	        rtnPg="sessionTO";
	        if(!rtnPg.equals("sucess")){
	            return am.findForward(rtnPg);   
	        }else{
                    util.updAccessLog(request,response,"Adv Contact", "loadwebaccessdtl start");
	          AdvContactForm udf = (AdvContactForm)af;
	          ArrayList dtlList = new ArrayList();
	          ArrayList ary = new ArrayList();
	          String frmdte = util.nvl((String)udf.getValue("dtefr"));
	          String todte = util.nvl((String)udf.getValue("dteto"));
	          String cnt="0";
	          String conQ="";
                    String hisfilter=util.nvl((String)udf.getValue("hisfilter"),"N");
                if(hisfilter.equals("Y")){
                    srchGeneric(request,udf);
                    String updateFlg = "delete gt_nme_srch a where not exists (select 1 from Gt_Srch_Rslt b , nmerel c \n" + 
                      "                where b.Rln_Idn = c.nmerel_idn and c.nme_idn = a.nme_idn )";
                    int del = db.execUpd("update flg", updateFlg, new ArrayList());
                }
	        String conQdte=" and trunc(b.dt_tm)=trunc(sysdate)";
	        if(!frmdte.equals("") && !todte.equals(""))
	        conQdte = " and trunc(b.dt_tm) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
	        if(frmdte.equals("") && !todte.equals(""))
	        conQdte = " and trunc(b.dt_tm) between to_date('"+todte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
	        if(!frmdte.equals("") && todte.equals(""))
	        conQdte = " and trunc(b.dt_tm) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+frmdte+"' , 'dd-mm-yyyy') ";
	        
	        String gtView = "select count(*) cnt from gt_nme_srch ";
	        ResultSet rs1 = db.execSql("gtView", gtView, new ArrayList());
	        try {
	        while (rs1.next()) {
	            cnt=util.nvl(util.nvl(rs1.getString("cnt")));
	        }
	            rs1.close();
	        } catch (SQLException sqle) {
	                // TODO: Add catch code
	                sqle.printStackTrace();
	        }
	        if(!cnt.equals("0")){
	          conQ="   and exists (select 1 from gt_nme_srch where e.nme_idn = gt_nme_srch.nme_idn) ";
	        }
	        
	          String loginDtlSql = "select count(*) cnt,a.pg,byr.get_nm(d.nme_idn) byr,c.usr from\n" + 
	          "            web_access_log a,web_login_log b,web_usrs c,nmerel d,mnme e\n" + 
	          "            where a.log_id=b.log_id\n" + 
	          "            and B.USR_ID=c.usr_id\n" + 
	          "            and c.rel_idn=d.nmerel_idn\n" + 
	          "            and d.nme_idn=e.nme_idn\n" + 
	          "            and d.vld_dte is null\n" + 
	          "            and e.vld_dte is null \n" +conQdte+conQ+
	          "            Group By A.Pg,Byr.Get_Nm(D.Nme_Idn),c.usr\n" + 
	          "            Order By a.pg,Cnt Desc";
	        System.out.println(loginDtlSql); 
	        ArrayList rsLst = db.execSqlLst("loginDtl", loginDtlSql, ary);
	        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
	        ResultSet rs = (ResultSet)rsLst.get(1);
	        int srno=0;
	        while(rs.next()){
	            srno = srno+1;
	            HashMap dtls = new HashMap();
	            dtls.put("SR No", String.valueOf(srno));
	            dtls.put("Page", util.nvl(rs.getString("pg")));
	            dtls.put("Buyer", util.nvl(rs.getString("byr")));
	            dtls.put("User", util.nvl(rs.getString("usr")));
	            dtls.put("Count", util.nvl(rs.getString("cnt")));
                    dtlList.add(dtls);
	        }
	        rs.close(); pst.close();
	        ArrayList itemHdr = new ArrayList();
	        itemHdr.add("SR No");
	        itemHdr.add("Page");
	        itemHdr.add("Buyer");
	        itemHdr.add("User");
	        itemHdr.add("Count");
	        request.setAttribute("itemHdr",itemHdr);
	        request.setAttribute("dtlList", dtlList);
	        udf.setPktList(dtlList);
	        udf.setReportNme("Web Access Details Report");
                    util.updAccessLog(request,response,"Adv Contact", "loadwebaccessdtl end");
	       return am.findForward("loadDtl");
                }
	    }
	    public ActionForward loadweblogindtl(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception  {
	        HttpSession session = request.getSession(false);
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
	        rtnPg=init(request,response,session,util);
	        }else
	        rtnPg="sessionTO";
	        if(!rtnPg.equals("sucess")){
	            return am.findForward(rtnPg);   
	        }else{
                    util.updAccessLog(request,response,"Adv Contact", "loadweblogindtl start");
	          AdvContactForm udf = (AdvContactForm)af;
	          ArrayList dtlList = new ArrayList();
	          ArrayList ary = new ArrayList();
	          String frmdte = util.nvl((String)udf.getValue("dtefr"));
	          String todte = util.nvl((String)udf.getValue("dteto"));
	          String cnt="0";
	          String conQ="";
                    String hisfilter=util.nvl((String)udf.getValue("hisfilter"),"N");
                if(hisfilter.equals("Y")){
                    srchGeneric(request,udf);
                    String updateFlg = "delete gt_nme_srch a where not exists (select 1 from Gt_Srch_Rslt b , nmerel c \n" + 
                      "                where b.Rln_Idn = c.nmerel_idn and c.nme_idn = a.nme_idn )";
                    int del = db.execUpd("update flg", updateFlg, new ArrayList());
                }
	        String conQdte=" and trunc(a.dt_tm)=trunc(sysdate)";
	        if(!frmdte.equals("") && !todte.equals(""))
	        conQdte = " and trunc(a.dt_tm) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
	        if(frmdte.equals("") && !todte.equals(""))
	        conQdte = " and trunc(a.dt_tm) between to_date('"+todte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
	        if(!frmdte.equals("") && todte.equals(""))
	        conQdte = " and trunc(a.dt_tm) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+frmdte+"' , 'dd-mm-yyyy') ";
	        
	        String gtView = "select count(*) cnt from gt_nme_srch ";
	        ResultSet rs1 = db.execSql("gtView", gtView, new ArrayList());
	        try {
	        while (rs1.next()) {
	            cnt=util.nvl(util.nvl(rs1.getString("cnt")));
	        }
	            rs1.close();
	        } catch (SQLException sqle) {
	                // TODO: Add catch code
	                sqle.printStackTrace();
	        }
	        if(!cnt.equals("0")){
	          conQ="   and exists (select 1 from gt_nme_srch where e.nme_idn = gt_nme_srch.nme_idn) ";
	        }
	        
	          String loginDtlSql = "select e.nme byr, d.usr usr, count(*) cnt from web_login_log a , nmerel c , web_usrs d , nme_v e\n" + 
	          "      Where C.Nmerel_Idn = D.Rel_Idn And D.Usr_Id = A.Usr_Id And C.Nme_Idn=E.Nme_Idn\n" +conQdte+conQ+ 
	          "      Group By E.Nme, D.Usr\n" + 
	          "      order by cnt desc";
	        System.out.println(loginDtlSql); 
	        ArrayList rsLst = db.execSqlLst("loginDtl", loginDtlSql, ary);
	        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
	        ResultSet rs = (ResultSet)rsLst.get(1);
	        int srno=0;
	        while(rs.next()){
	            srno = srno+1;
	            HashMap dtls = new HashMap();
	            dtls.put("SR No", String.valueOf(srno));
	            dtls.put("Buyer", util.nvl(rs.getString("byr")));
	            dtls.put("User", util.nvl(rs.getString("usr")));
//	            dtls.put("IP", util.nvl(rs.getString("cl_ip")));
	            dtls.put("Count", util.nvl(rs.getString("cnt")));
	            dtlList.add(dtls);
	        }
	        rs.close(); pst.close();
	        ArrayList itemHdr = new ArrayList();
	        itemHdr.add("SR No");
	        itemHdr.add("Buyer");
	        itemHdr.add("User");
//                itemHdr.add("IP");
	        itemHdr.add("Count");
	        request.setAttribute("itemHdr",itemHdr);
	        request.setAttribute("dtlList", dtlList);
	        udf.setPktList(dtlList);
	        udf.setReportNme("Web login Details Report");
                    util.updAccessLog(request,response,"Adv Contact", "loadweblogindtl end");
	       return am.findForward("loadDtl");
                }
	    }            
	    public ActionForward loadweblastlogindtl(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception  {
	        HttpSession session = request.getSession(false);
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
	        rtnPg=init(request,response,session,util);
	        }else
	        rtnPg="sessionTO";
	        if(!rtnPg.equals("sucess")){
	            return am.findForward(rtnPg);   
	        }else{
                    util.updAccessLog(request,response,"Adv Contact", "loadweblastlogindtl start");
	          AdvContactForm udf = (AdvContactForm)af;
	          ArrayList dtlList = new ArrayList();
	          ArrayList ary = new ArrayList();
                  String frmdte = util.nvl((String)udf.getValue("dtefr"));
	          String todte = util.nvl((String)udf.getValue("dteto"));
                  String cnt="0";
                  String conQ="";
                    String hisfilter=util.nvl((String)udf.getValue("hisfilter"),"N");
                if(hisfilter.equals("Y")){
                    srchGeneric(request,udf);
                  String updateFlg = "delete gt_nme_srch a where not exists (select 1 from Gt_Srch_Rslt b , nmerel c \n" + 
                    "                where b.Rln_Idn = c.nmerel_idn and c.nme_idn = a.nme_idn )";
                  int del = db.execUpd("update flg", updateFlg, new ArrayList());
                }
                String conQdte=" trunc(b.dt_tm)=trunc(sysdate)";
	        if(!frmdte.equals("") && !todte.equals(""))
	        conQdte = " trunc(b.dt_tm) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
	        if(frmdte.equals("") && !todte.equals(""))
	        conQdte = " trunc(b.dt_tm) between to_date('"+todte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') ";
	        if(!frmdte.equals("") && todte.equals(""))
	        conQdte = " trunc(b.dt_tm) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+frmdte+"' , 'dd-mm-yyyy') ";
	        
                String gtView = "select count(*) cnt from gt_nme_srch ";
	        try {
                    ArrayList outLst = db.execSqlLst("gtView", gtView, new ArrayList());
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs1 = (ResultSet)outLst.get(1);
	        while (rs1.next()) {
                    cnt=util.nvl(util.nvl(rs1.getString("cnt")));
                }
                    rs1.close();pst.close();
                } catch (SQLException sqle) {
                        // TODO: Add catch code
                        sqle.printStackTrace();
                }
                if(!cnt.equals("0")){
                  conQ="   and exists (select 1 from gt_nme_srch where e.nme_idn = gt_nme_srch.nme_idn) ";
                }
                
                  String loginDtlSql = "select  e.nme , d.usr , a.cl_ip , to_char(dt_tm, 'dd-Mon HH24:mi:ss') dsp_dt_Tm  \n" + 
	          "           ,  lower(byr.get_eml(e.nme_idn,'N')) eml,byr.get_bsdndte(e.nme_idn) Bdte,a.CL_COUNTRY cnt From Web_Login_Log A\n" + 
	          "          , (select max(log_id) log_id, usr_id from web_login_log b where "+conQdte+" group by usr_id) b\n" + 
	          "          , nmerel c , web_usrs d , nme_v e \n" + 
	          "          where a.log_id = b.log_id and c.nmerel_idn = d.rel_idn and d.usr_id = a.usr_id and c.nme_idn=e.nme_idn  \n"+conQ+
                  "          order by dt_tm desc";
	        System.out.println(loginDtlSql); 
	        ArrayList rsLst = db.execSqlLst("loginDtl", loginDtlSql, ary);
	        PreparedStatement pst = (PreparedStatement)rsLst.get(0);
	        ResultSet rs = (ResultSet)rsLst.get(1);
	        int srno=0;
	        while(rs.next()){
	            srno = srno+1;
	            HashMap dtls = new HashMap();
	            dtls.put("SR No", String.valueOf(srno));
	            dtls.put("Byr Name", util.nvl(rs.getString("nme")));
	            dtls.put("User", util.nvl(rs.getString("usr")));
	            dtls.put("Date", util.nvl(rs.getString("dsp_dt_Tm")));
	            dtls.put("Client IP",  util.nvl(rs.getString("cl_ip")));
	            dtls.put("Country",  util.nvl(rs.getString("cnt")));
	            dtls.put("Email Id",  util.nvl(rs.getString("eml")));
	            dtls.put("Business Done",  util.nvl(rs.getString("Bdte")));

	            dtlList.add(dtls);
	        }
	        rs.close(); pst.close();
	        ArrayList itemHdr = new ArrayList();
	        itemHdr.add("SR No");
	        itemHdr.add("User");
	        itemHdr.add("Byr Name");
	        itemHdr.add("Email Id");
	        itemHdr.add("Date");
	        itemHdr.add("Client IP");
	        itemHdr.add("Country");
                itemHdr.add("Business Done");
	        request.setAttribute("itemHdr",itemHdr);
	         request.setAttribute("dtlList", dtlList);
	         udf.setPktList(dtlList);
	        udf.setReportNme("Web Details Report");
                    util.updAccessLog(request,response,"Adv Contact", "loadweblastlogindtl end");
	       return am.findForward("loadDtl");
                }
	    }
	    public ActionForward historysearch(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception  {
	        HttpSession session = request.getSession(false);
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
	        rtnPg=init(request,response,session,util);
	        }else
	        rtnPg="sessionTO";
	        if(!rtnPg.equals("sucess")){
	            return am.findForward(rtnPg);   
	        }else{
                    util.updAccessLog(request,response,"Adv Contact", "historysearch start");
                AdvContactForm udf = (AdvContactForm)af;
	        ArrayList ary = new ArrayList();
                ArrayList dtlList = new ArrayList();
                srchGeneric(request,udf);
                int srno=0;	        
                String hSql = "Select C.Nme, A.Pkt_Dte, A.Stk_Idn, Web_Pkg.Get_Srch_Dscr(A.Stk_Idn) Dscr \n" + 
                "From Gt_Srch_Rslt A, Nmerel B, Nme_All_V C \n" + 
                "Where A.Rln_Idn = B.Nmerel_Idn And B.Nme_Idn = C.Nme_Idn \n" + 
                "Order By 1,2";

                    ArrayList outLst = db.execSqlLst("historydataQ", hSql, new ArrayList());
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs = (ResultSet)outLst.get(1);
	        while(rs.next()){
	          srno = srno+1;
	          HashMap dtls = new HashMap();
	          dtls.put("SR No", String.valueOf(srno));
	          dtls.put("Byr Name", util.nvl(rs.getString("Nme")));
	          dtls.put("Date", util.nvl(rs.getString("Pkt_Dte")));
	          dtls.put("Srch Id", util.nvl(rs.getString("Stk_Idn")));
	          dtls.put("Srch Dsc", util.nvl(rs.getString("Dscr")));
	          dtlList.add(dtls);
	        }
	        rs.close(); pst.close();
	        ArrayList itemHdr = new ArrayList();
	        itemHdr.add("SR No");
	        itemHdr.add("Byr Name");
	        itemHdr.add("Date");
	        itemHdr.add("Srch Id");
	        itemHdr.add("Srch Dsc");
	        request.setAttribute("itemHdr",itemHdr);
	        request.setAttribute("dtlList", dtlList);
	        udf.setPktList(dtlList);
	        udf.setReportNme("Search History Report");
                    util.updAccessLog(request,response,"Adv Contact", "historysearch end");
	        return am.findForward("loadDtl");  
                }
	    }
            
	    public void srchGeneric(HttpServletRequest req , AdvContactForm udf){
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
	        String dfr = util.nvl((String)udf.getValue("dtefr"));
	        String dto = util.nvl((String)udf.getValue("dteto"));
	        String typ = util.nvl((String)udf.getValue("typ"), "WEB");
	        ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_csGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_csGNCSrch"); 
	        info.setGncPrpLst(genricSrchLst);
	        HashMap prp = info.getPrp();
	        HashMap mprp = info.getMprp();
	        HashMap paramsMap = new HashMap();
	        int lSrchId=0;
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
	        paramsMap.put("stt", typ);
	        paramsMap.put("mdl", "CONTACT_SRCH");
	        lSrchId=util.genericSrchEntries(paramsMap);
	        System.out.println(lSrchId);
	            String dateQ="to_date(?, 'dd-mm-rrrr') , to_date(?, 'dd-mm-rrrr') from dual";
	            if(dfr.equals("") && dto.equals(""))
	                dateQ="to_date(SYSDATE, 'dd-mm-rrrr') , to_date(SYSDATE, 'dd-mm-rrrr') from dual"; 
	            if(dfr.equals("") && !dto.equals(""))
	                dateQ="to_date(?, 'dd-mm-rrrr') , to_date(?, 'dd-mm-rrrr') from dual";
	            if(!dfr.equals("") && dto.equals(""))
	                dateQ="to_date(?, 'dd-mm-rrrr') , to_date(?, 'dd-mm-rrrr') from dual"; 
	            String insrtAddon = " insert into srch_addon( srch_id , cprp ,cstt, frm_dte , to_dte ) "+
	            "select ? , ? , ? ,"+dateQ;
	            ary = new ArrayList();
	            ary.add(String.valueOf(lSrchId));
	            ary.add("SRCH_DTE");
	            ary.add("ALL");
	            if(!dfr.equals("") && !dto.equals("")){
	            ary.add(dfr);
	            ary.add(dto);
	            }
	        if(!dfr.equals("") && dto.equals("")){
	            ary.add(dfr);
	            ary.add(dfr);
	        }
	        if(dfr.equals("") && !dto.equals("")){
	            ary.add(dto);
	            ary.add(dto);
	        }
	        int cnt = db.execUpd("insert SRCH_DTE", insrtAddon, ary);    
	            
	        ary = new ArrayList();
	        ary.add(String.valueOf(lSrchId));
	        int ct = db.execCall("NME_SRCH_PKG", "NME_SRCH_PKG.SRCH_HISTORY(pSrchId => ?)", ary);
	    }
	    public ActionForward loadreport(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
	        util.updAccessLog(req,res,"Adv Contact", "loadreport start");
	    AdvContactForm udf = (AdvContactForm) af;
	    udf.reset();
	        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
	        HashMap pageDtl=(HashMap)allPageDtl.get("MAIL_REPORT");
	        if(pageDtl==null || pageDtl.size()==0){
	        pageDtl=new HashMap();
	        pageDtl=util.pagedef("MAIL_REPORT");
	        allPageDtl.put("MAIL_REPORT",pageDtl);
	        }
	        info.setPageDetails(allPageDtl);
	        util.updAccessLog(req,res,"Adv Contact", "loadreport end");
	    return am.findForward("loadreport");
	    }
	    }
	    public ActionForward fetchreport(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
	        util.updAccessLog(req,res,"Adv Contact", "fetchreport start");
	    AdvContactForm udf = (AdvContactForm) af;
	        String seqNo = util.nvl((String)udf.getValue("seqNo"));
	        String stt = util.nvl((String)udf.getValue("stt"));
	        String mailTyp = util.nvl((String)udf.getValue("mailTyp"));
	        String typ = util.nvl((String)udf.getValue("typ"));
	        String dfr = util.nvl((String)udf.getValue("dtefr"));
	        String dto = util.nvl((String)udf.getValue("dteto"));
                ArrayList params=new ArrayList();
	        ArrayList dtlList=new ArrayList();
                String conQ="";
                if(!seqNo.equals("")){
                    conQ+=" and ref_idn=? " ;
                    params.add(seqNo);
                }
                if(!mailTyp.equals("")){
	            conQ+=" and ref_typ=? " ;
	        params.add(mailTyp);
	        }
	        if(!stt.equals("")){
	            conQ+=" and stt=? " ;
	        params.add(stt);
	        }
                if(!typ.equals("")){
	        conQ+=" and flg=? " ;
	        params.add(typ);
	        }
	        if(!dfr.equals("") && !dto.equals(""))
	            conQ+= " and trunc(dte) between to_date('"+dfr+"' , 'dd-mm-yyyy') and to_date('"+dto+"' , 'dd-mm-yyyy') ";    
	        else if(seqNo.equals(""))
	            conQ+= " and trunc(dte)=trunc(sysdate)";
                String sqlQ=" select to_eml,cc_eml,bcc_eml,to_char(dte, 'dd-Mon-rrrr HH24:mi:ss') dte,decode(del_yn,'Y','Delivered','Failed') del_yn,stt,decode(flg,'DF','Diaflex','Website') flg,unm,ref_typ,ref_idn\n" + 
                "from mail_log where 1=1 "+conQ +" order by dte desc";   
	        ArrayList outLst = db.execSqlLst("Mail report", sqlQ, params);
	        PreparedStatement pst = (PreparedStatement)outLst.get(0);
	        ResultSet rs = (ResultSet)outLst.get(1);
	        while (rs.next()) {
	            HashMap data = new HashMap();
	            data.put("TOEML", util.nvl((String)rs.getString("to_eml")));
	            data.put("CCEML", util.nvl((String)rs.getString("cc_eml")));
	            data.put("BCCEML", util.nvl((String)rs.getString("bcc_eml")));
	            data.put("DTE", util.nvl((String)rs.getString("dte")));
	            data.put("DELYN", util.nvl((String)rs.getString("del_yn")));
	            data.put("STT", util.nvl((String)rs.getString("stt")));
	            data.put("FLG", util.nvl((String)rs.getString("flg")));
	            data.put("UNM", util.nvl((String)rs.getString("unm")));
	            data.put("REFTYP", util.nvl((String)rs.getString("ref_typ")));
	            data.put("REFIDN", util.nvl((String)rs.getString("ref_idn")));
	            dtlList.add(data);
	        }
	        rs.close(); pst.close();
                req.setAttribute("View", "Y");
	        req.setAttribute("dtlList", dtlList);
	        util.updAccessLog(req,res,"Adv Contact", "fetchreport end");
	    return am.findForward("loadreport");
	    }
	    }     
	    public ActionForward loadclientFeedback(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
	          util.updAccessLog(req,res,"load Feed back", "loadcntFeedback start");
	          AdvContactForm udf = (AdvContactForm)af;	             
	            ArrayList  FeedbackList =new ArrayList();
                      ArrayList  ary =new ArrayList();
                      String nmeidn =util.nvl((String)req.getParameter("nmeIdn"),"0");
	              String empidn =util.nvl((String)req.getParameter("empIdn"),"0"); 
                      if(nmeidn.equals("0") && empidn.equals("0")){
                          empidn=util.nvl((String)udf.getValue("emp_Idn")); 
                          nmeidn=util.nvl((String)udf.getValue("nme_Idn"));  
                        }
                      udf.reset();
	              ary =new ArrayList();
                      ary.add(nmeidn);
	              ary.add(empidn);
                      String  srchQ =" select idn, nme_idn ,emp_idn ,to_char(dte,'dd-mm-yyyy') dte ,comm_mode,memo_ref ,emp_comm ,byr_comm , byr_stk_comm,to_char(est_nxt_dte,'dd-mm-yyyy') est_nxt_dte,to_char(START_DT_TM, 'dd-MON-rrrr HH24:mi') starttm,to_char(END_DT_TM, 'dd-MON-rrrr HH24:mi') endtm from dly_feedback  where nme_idn= ? and emp_idn = ? order by idn desc ";

	              ArrayList outLst = db.execSqlLst("FeedBack List", srchQ, ary);
	              PreparedStatement pst = (PreparedStatement)outLst.get(0);
	              ResultSet rs = (ResultSet)outLst.get(1);
	              while (rs.next()) {
	                  HashMap data = new HashMap();
                          data.put("Idn", util.nvl((String)rs.getString("idn")));
	                  data.put("NmeIdn", util.nvl((String)rs.getString("nme_idn")));
                          data.put("EmpIdn", util.nvl((String)rs.getString("emp_idn")));
                          data.put("Dte", util.nvl((String)rs.getString("dte")));
                          data.put("Mode", util.nvl((String)rs.getString("comm_mode")));
                          data.put("MemoRef", util.nvl((String)rs.getString("memo_ref")));
                          data.put("EmpComm", util.nvl((String)rs.getString("emp_comm")));
                          data.put("ByrComm", util.nvl((String)rs.getString("byr_comm")));
                          data.put("ByrstkComm", util.nvl((String)rs.getString("byr_stk_comm")));
                          data.put("NxtDte", util.nvl((String)rs.getString("est_nxt_dte")));
                          data.put("starttm", util.nvl((String)rs.getString("starttm")));
                          data.put("endtm", util.nvl((String)rs.getString("endtm")));
                          FeedbackList.add(data);
                      }  
                      rs.close(); pst.close();
	              ary =new ArrayList();
	              ary.add(nmeidn);
                      String getByr ="select byr , byr.get_nm(emp_idn) emp from nme_cntct_v where nme_id = ?"; 

	              outLst = db.execSqlLst("FeedBack List", getByr, ary);
	              pst = (PreparedStatement)outLst.get(0);
	              rs = (ResultSet)outLst.get(1);
	              while (rs.next()){
                      String byr =util.nvl((String)rs.getString("byr"));
                      String emp =util.nvl((String)rs.getString("emp"));
                      session.setAttribute("byr",byr);
                      session.setAttribute("emp",emp);
                      }
                      rs.close(); pst.close();
                   req.setAttribute("view",'Y');  
                   session.setAttribute("EmpFeedbackList", FeedbackList);
	           udf.setValue("emp_Idn",empidn); 
	           udf.setValue("nme_Idn",nmeidn);          
                   util.updAccessLog(req,res," load Feed back ", "loadcntFeedback end");
	          return am.findForward("loadclientFeedback");
	          }
	      }
	    public ActionForward saveclientFeedback(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
	          util.updAccessLog(req,res,"save Feed back", "saveclientFeedback start");
	          AdvContactForm udf = (AdvContactForm)af;
                          ArrayList ary =new ArrayList();
	                  String btn = util.nvl((String)udf.getValue("btnUpd"));
                          String mode = util.nvl((String)udf.getValue("mode"));
                          String memoref = util.nvl((String)udf.getValue("memoref"));
                          String flDte = util.nvl((String)udf.getValue("flDte"));
                          String dicuss = util.nvl((String)udf.getValue("dicuss"));
                          String byrcomm = util.nvl((String)udf.getValue("byrcomm"));
                          String empcomm = util.nvl((String)udf.getValue("empcomm"));
                          String empidn = util.nvl((String)udf.getValue("emp_Idn"));
                          String nmeidn = util.nvl((String)udf.getValue("nme_Idn"));
                          String fbkidn = util.nvl((String)udf.getValue("idn"));
                          String starttm = util.nvl((String)udf.getValue("starttm"));
	                  String endtm = util.nvl((String)udf.getValue("endtm"));
	                  HashMap feedlist = new HashMap();
                          String status ="";
                          String sqlQ="";                    
	                  udf.reset();
                  if(!btn.equals("Update")){                      
                   sqlQ =" insert into dly_feedback(idn,nme_idn,emp_idn,dte ,comm_mode,memo_ref,emp_comm,byr_comm, byr_stk_comm ,est_nxt_dte,sys_dte,df_log_idn,START_DT_TM,END_DT_TM ) " +
                       " select DLY_FEEDBACK_SEQ.nextval ,? , ?, sysdate, ?, ?, ?,?, ?, to_date(?,'dd-mm-yyyy'),sysdate,?,TO_TIMESTAMP(?, 'DD-MON-YYYY HH24.MI'),TO_TIMESTAMP(?, 'DD-MON-YYYY HH24.MI') from dual ";
                      ary =new ArrayList();
                      ary.add(nmeidn);              
                      ary.add(empidn);
                      ary.add(mode);
                      ary.add(memoref);
                      ary.add(empcomm);
                      ary.add(byrcomm);
                      ary.add(dicuss);
                      ary.add(flDte);
                      ary.add(String.valueOf(info.getLogId()));
                      ary.add(starttm);
                      ary.add(endtm);
                      status ="Add New  Feedback  Details";
                   }else{
                      sqlQ ="update dly_feedback set comm_mode=? ,memo_ref=? ,emp_comm =? , byr_comm= ? ,byr_stk_comm =?,est_nxt_dte=to_date(?,'dd-mm-yyyy') , dte =sysdate, df_log_idn = ?,START_DT_TM=TO_TIMESTAMP(?, 'DD-MON-YYYY HH24.MI'),END_DT_TM=TO_TIMESTAMP(?, 'DD-MON-YYYY HH24.MI') where idn = ?  ";
                          ary =new ArrayList();
                          ary.add(mode);
                          ary.add(memoref);
                          ary.add(empcomm);
                          ary.add(byrcomm);
                          ary.add(dicuss);
                          ary.add(flDte);
                          ary.add(String.valueOf(info.getLogId()));
                          ary.add(starttm);
                          ary.add(endtm);
                          ary.add(fbkidn);
                          status ="Update Feedback Details";
                      }
	              int ct = db.execDirUpd(" insert log ", sqlQ, ary);
                  if(ct>0){ 
                          feedlist.put("status",status);
                          feedlist.put("mode",mode);
                          feedlist.put("memoref",memoref);
                          feedlist.put("empcomm",empcomm);
                          feedlist.put("byrcomm",byrcomm);
                          feedlist.put("dicuss",dicuss);
                          feedlist.put("flDte",flDte);
                          feedlist.put("dicuss",dicuss);
                          feedlist.put("flDte",flDte);
                          feedlist.put("starttm",starttm);
                          feedlist.put("endtm",endtm);
                          HashMap mailDtl = util.getMailFMT("FBK_BYR");
                          sendmail(req ,res ,mailDtl ,feedlist);
                      }
                 
	            
	        
	              udf.setValue("emp_Idn",empidn); 
	              udf.setValue("nme_Idn",nmeidn);  
	           util.updAccessLog(req,res," save Feed back ", "saveclientFeedback end");
	            return  loadclientFeedback(am,af,req,res);
	          }
	      }
	    public ActionForward editclientFeedback(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
	          util.updAccessLog(req,res,"edit Feed back", "editclientFeedback start");
	          AdvContactForm udf = (AdvContactForm)af;
	              String idn =util.nvl((String)req.getParameter("idn")); 
	              String empidn = "";
	              String nmeidn = "";
                      udf.reset();
	              ArrayList ary =new ArrayList();
                  if(!idn.equals("")){
                          ary =new ArrayList();
                          ary.add(idn);
                          String srchQ ="select idn, nme_idn ,emp_idn ,comm_mode,memo_ref,emp_comm ,byr_comm ,byr_stk_comm, to_char(est_nxt_dte,'dd-mm-yyyy') est_nxt_dte,to_char(START_DT_TM, 'dd-MON-rrrr HH24:mi') starttm,to_char(END_DT_TM, 'dd-MON-rrrr HH24:mi') endtm " +
                                    " from dly_feedback where idn = ? " ;

                          ArrayList outLst = db.execSqlLst("FeedBack List", srchQ, ary);
                          PreparedStatement pst = (PreparedStatement)outLst.get(0);
                          ResultSet rs = (ResultSet)outLst.get(1);
                          while (rs.next()) {
                              empidn=util.nvl((String)rs.getString("emp_idn"));
                              nmeidn= util.nvl((String)rs.getString("nme_idn")) ;
                              udf.setValue("mode", util.nvl((String)rs.getString("comm_mode")));
                              udf.setValue("memoref", util.nvl((String)rs.getString("memo_ref")));
                              udf.setValue("empcomm", util.nvl((String)rs.getString("emp_comm")));
                              udf.setValue("byrcomm", util.nvl((String)rs.getString("byr_comm")));
                              udf.setValue("dicuss", util.nvl((String)rs.getString("byr_stk_comm")));
                              udf.setValue("flDte", util.nvl((String)rs.getString("est_nxt_dte")));
                              udf.setValue("NmeIdn", util.nvl((String)rs.getString("nme_idn")));
                              udf.setValue("empId", util.nvl((String)rs.getString("emp_idn")));
                              udf.setValue("idn", util.nvl((String)rs.getString("idn")));
                              udf.setValue("starttm", util.nvl((String)rs.getString("starttm")));
                              udf.setValue("endtm", util.nvl((String)rs.getString("endtm")));
                          }
                          rs.close(); pst.close();
                      
                      }


                    udf.setValue("disButton","Update");
                    udf.setValue("idn",idn);
	            udf.setValue("emp_Idn",empidn); 
	            udf.setValue("nme_Idn",nmeidn);  
	           util.updAccessLog(req,res," save Feed back ", "editclientFeedback end");
	          return am.findForward("loadclientFeedback");
	          }
	      }
	    public ActionForward loadFeedback(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception  {
	                                HttpSession session = request.getSession(false);
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
	                                rtnPg=init(request,response,session,util);
	                                }else{
	                                rtnPg="connExists";   
	                                }
	                                }else
	                                rtnPg="sessionTO";
	                                if(!rtnPg.equals("sucess")){
	                                    return am.findForward(rtnPg);   
	                                }else{
	                                    util.updAccessLog(request,response,"Adv Contact", "loadFeedback start");
	                                    AdvContactForm udf = (AdvContactForm) af;
	                                    ArrayList byrList = new ArrayList();
	                                    ArrayList itemhdr = new ArrayList();
	                                    HashMap data = new HashMap();
	                                    String hisfilter=util.nvl((String)udf.getValue("hisfilter"),"N");
	                                    if(hisfilter.equals("Y")){
	                                    srchGeneric(request,udf);
	                                    String updateFlg = "delete gt_nme_srch a where not exists (select 1 from Gt_Srch_Rslt b , nmerel c \n" + 
	                                      "                where b.Rln_Idn = c.nmerel_idn and c.nme_idn = a.nme_idn )";
	                                    int del = db.execUpd("update flg", updateFlg, new ArrayList());
	                                    }
	                                    String cntSrchQ ="select nme_id ,emp_idn ,eml ,byr ,cp , byr.get_country(nme_id) country, mbl,qbc ,www ,fax ,bankaccount bank ,skype_id,last_call from  nme_cntct_v where  exists (select 1 from gt_nme_srch where nme_cntct_v.nme_id = gt_nme_srch.nme_idn) and nme_cntct_v.typ <>'EMPLOYEE' order by byr ";

	                                    ArrayList outLst = db.execSqlLst("Buyer List", cntSrchQ , new ArrayList());
	                                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
	                                    ResultSet rs = (ResultSet)outLst.get(1);
	                                    while (rs.next()) {   
	                                        data = new HashMap();
	                                        data.put("NmeIdn", util.nvl((String)rs.getString("nme_id")));
	                                        data.put("EmpIdn", util.nvl((String)rs.getString("emp_idn")));
	                                        data.put("Company Name", util.nvl((String)rs.getString("byr")));
	                                        data.put("Client Name", util.nvl((String)rs.getString("cp")));
	                                        data.put("Country", util.nvl((String)rs.getString("country")));
	                                        data.put("Last Call", util.nvl((String)rs.getString("last_call")));
	                                        data.put("Mobile", util.nvl((String)rs.getString("mbl")));
	                                        data.put("Tel", util.nvl((String)rs.getString("qbc")));
	                                        data.put("Email", util.nvl((String)rs.getString("eml")));
	    //                                  data.put("WWW", util.nvl((String)rs.getString("www")));
	    //                                  data.put("Fax", util.nvl((String)rs.getString("fax")));
	    //                                  data.put("Bank Account", util.nvl((String)rs.getString("bank")));
	    //                                  data.put("Skype Id", util.nvl((String)rs.getString("skype_id")));
	                                        byrList.add(data);
	                                    }
	                                    rs.close(); pst.close();
	                                    itemhdr.add("Company Name");
	                                    itemhdr.add("Client Name");
	                                    itemhdr.add("Country");
	                                    itemhdr.add("Last Call");
	                                    itemhdr.add("Mobile");
	                                    itemhdr.add("Tel");
	                                    itemhdr.add("Email");
	                                    //                              itemhdr.add("WWW");
	                                    //                              itemhdr.add("Fax");
	                                    //                              itemhdr.add("Bank Account");
	                                    //                              itemhdr.add("Skype Id");
	                                    session.setAttribute("Feeditemhdr", itemhdr);
	                                    session.setAttribute("byrList", byrList);
	                                    util.updAccessLog(request,response,"Adv Contact", "loadFeedback end");
	                               return am.findForward("loadFeedback");
	                                }
	                            }   
	    public ActionForward fromsearchrslt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
	        util.updAccessLog(req,res,"Adv Contact", "contactsearch start");
	        AdvContactForm udf = (AdvContactForm) af;
	        String nme_idn=util.nvl((String)req.getParameter("nme_idn"));
	        int del = db.execUpd("update flg", "delete gt_nme_srch", new ArrayList());
	        ArrayList ary =new ArrayList();
	        ary.add(nme_idn);
	        int ct = db.execDirUpd(" insert log ", "insert into gt_nme_srch(nme_idn) select ? from dual", ary);    
                    
	            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
	            HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_SRCH");
	            if(pageDtl==null || pageDtl.size()==0){
	            pageDtl=new HashMap();
	            pageDtl=util.pagedef("CONTACT_SRCH");
	            allPageDtl.put("CONTACT_SRCH",pageDtl);
	            }
	            info.setPageDetails(allPageDtl); 
	        util.updAccessLog(req,res,"Adv Contact", "contactsearch end");
	        return loadFeedback(am,af,req,res);
	        }
	    }            
            
	    public ActionForward loadCreateNew(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
	        return am.findForward("loadCreateNew");
	        }
	    }
            
	    public ActionForward loadpa(ActionMapping am, ActionForm af, HttpServletRequest req,
	                                  HttpServletResponse res) throws Exception {
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
	            util.updAccessLog(req,res,"Adv Contact", "loadpa start");
	            HashMap dbinfo = info.getDmbsInfoLst();
	            String cnt = (String)dbinfo.get("CNT");
	            AdvContactForm udf = (AdvContactForm) af;
                    udf.reset();
	        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
	        HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_SRCH");
	        if(pageDtl==null || pageDtl.size()==0){
	        pageDtl=new HashMap();
	        pageDtl=util.pagedef("CONTACT_SRCH");
	        allPageDtl.put("CONTACT_SRCH",pageDtl);
	        }
	        info.setPageDetails(allPageDtl);
                ArrayList paLst=new ArrayList();
	        HashMap paDtl=new HashMap();
                String sqlQ="select nme_idn,pfx,fnme,mnme,lnme,byr.get_nm(grp_nme_idn) grp,byr.get_nm(emp_idn) emp,to_char(frm_dte, 'DD-MON-YYYY') frm_dte \n" + 
                "from mnme where typ='PA' and vld_dte is null\n" + 
                "order by frm_dte";
	            ArrayList outLst = db.execSqlLst("Repeat Customer", sqlQ, new ArrayList());
	            PreparedStatement pst = (PreparedStatement)outLst.get(0);
	            ResultSet rs = (ResultSet)outLst.get(1);
	            while (rs.next()) {
	                paDtl = new HashMap();
	                paDtl.put("nme_idn", util.nvl((String)rs.getString("nme_idn")));
	                paDtl.put("pfx", util.nvl((String)rs.getString("pfx")));
	                paDtl.put("fnme", util.nvl((String)rs.getString("fnme")));
	                paDtl.put("mnme", util.nvl((String)rs.getString("mnme")));
	                paDtl.put("lnme", util.nvl((String)rs.getString("lnme")));
	                paDtl.put("grp", util.nvl((String)rs.getString("grp")));
	                paDtl.put("emp", util.nvl((String)rs.getString("emp")));
	                paDtl.put("frm_dte", util.nvl((String)rs.getString("frm_dte")));
	                paLst.add(paDtl);
	            }
	            rs.close(); pst.close();  
                session.setAttribute("paLst", paLst);
	        util.updAccessLog(req,res,"Adv Contact", "loadpa end");
	        return am.findForward("loadpa");
	        }
	    }
            
            
	    public ActionForward savepa(ActionMapping am, ActionForm af, HttpServletRequest req,
	                                  HttpServletResponse res) throws Exception {
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
	            util.updAccessLog(req,res,"Adv Contact", "savepa start");
	            HashMap dbinfo = info.getDmbsInfoLst();
	            String cnt = (String)dbinfo.get("CNT");
	            AdvContactForm udf = (AdvContactForm) af;
	            ArrayList  paLst= (session.getAttribute("paLst") == null)?new ArrayList():(ArrayList)session.getAttribute("paLst");
                    int paLstsz=paLst.size();
	            ArrayList ary=new ArrayList();
                    int updcnt=0;
                for(int i=0;i<paLstsz;i++){
                    HashMap paDtl=(HashMap)paLst.get(i);
                    String nme_idn=util.nvl((String)paDtl.get("nme_idn"));
                    String chk=util.nvl((String)udf.getValue("stt_"+nme_idn));
                    if(chk.equals("Y")){
                        ary=new ArrayList();
                        ary.add(nme_idn);
                        int upd = db.execUpd("update typ", "update mnme set typ='BUYER' where nme_idn=? and typ='PA'", ary);
                        if(upd>0){
                            updcnt++;
                            ary = new ArrayList();
                            ary.add(nme_idn);
                            ary.add("CONTACT_APPROVED_USER");
                            ary.add(util.nvl(((String)info.getUsr())));
                            int ct =db.execCall("nme_dtlUpd", "NME_PRP_PKG.Nme_prp_upd(pNmeIdn => ?, pPrp => ?, pVal => ?)",ary);
                            ary = new ArrayList();
                            ary.add(nme_idn);
                            ary.add("CONTACT_APPROVED_DATE");
                            ary.add(util.getToDteDDMMMYYYY());
                            ct =db.execCall("nme_dtlUpd", "NME_PRP_PKG.Nme_prp_upd(pNmeIdn => ?, pPrp => ?, pVal => ?)",ary);
                        }
                    }
                }
                if(updcnt>0){
                    req.setAttribute("rtnmsg","Pending Contact Approved Sucessfully");
                }
	        util.updAccessLog(req,res,"Adv Contact", "savepa end");
	        return loadpa(am,af,req,res);
	        }
	    }
	    public void sendmail(HttpServletRequest req ,HttpServletResponse res, HashMap mailDtl , HashMap FeedList){
	        HttpSession session = req.getSession(false);
	        InfoMgr info = (InfoMgr)session.getAttribute("info");
	        DBUtil util = new DBUtil();
	        DBMgr db = new DBMgr();
	        db.setCon(info.getCon());
	        util.setDb(db);
	        util.setInfo(info);
	        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
	        util.setLogApplNm(info.getLoApplNm());
	              HashMap dbmsInfo = info.getDmbsInfoLst();
	              GenMail mail = new GenMail();
	              HashMap logDetails=new HashMap();
	              HashMap dbinfo = info.getDmbsInfoLst();        
	              String cnt = util.nvl((String)dbinfo.get("CNT"));
	              info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));    
	              info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));    
	              info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));    
	              info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));  
	              mail.setInfo(info);
	              mail.init();
	                    StringBuffer msg=new StringBuffer();
	                   String hdr = "<html><head><title>Registration</title>\n"+
	                   "<style type=\"text/css\">\n"+
	                   "body{\n" + 
	                   "   margin-left: 10px;\n" + 
	                   "   margin-top: 10px;\n" + 
	                   "   margin-right: 0px;\n" + 
	                   "   margin-bottom: 0px;\n" + 
	                   "   font-family: Arial, Helvetica, sans-serif;\n" + 
	                   "   font-size: 12px;\n" + 
	                   "   color: #333333;\n" + 
	                   "}\n" +
	                   "</style>\n"+         
	                   "</head>";
	                   msg.append(hdr);
	                   msg.append("<body>");
	              String bodymsg=util.nvl((String)mailDtl.get("MAILBODY"));
                     
	              String datatbl = "<table  border=\"1\" id=\"fd\" cellspacing=\"0\" cellpadding=\"5\" ><tr bgcolor=\"\" style=\"font-size: 10px;\"><th nowrap=\"nowrap\">Sale Ex.</th><th nowrap=\"nowrap\">Buyer</th><th nowrap=\"nowrap\">Mode</th><th nowrap=\"nowrap\">Reference</th> " +
	                           "<th nowrap=\"nowrap\">Employee Feedback</th><th nowrap=\"nowrap\">Buyer Feedback </th><th nowrap=\"nowrap\">Discussion</th><th nowrap=\"nowrap\">Next FollowUp</th><th nowrap=\"nowrap\">Start Time</th><th nowrap=\"nowrap\">End Time</th></tr>";
                      if(FeedList.size()>0 && FeedList!=null){ 
                              datatbl+="<tr>";  
                              datatbl+="<td align=\"center\" nowrap=\"nowrap\">"+session.getAttribute("emp")+"</td>";
                              datatbl+="<td align=\"center\" nowrap=\"nowrap\">"+session.getAttribute("byr")+"</td>";                  
                              datatbl+="<td align=\"center\" nowrap=\"nowrap\">"+FeedList.get("mode")+"</td>";
                              datatbl+="<td align=\"right\" nowrap=\"nowrap\">"+FeedList.get("memoref")+"</td>";
                              datatbl+="<td align=\"left\" nowrap=\"nowrap\">"+FeedList.get("empcomm")+"</td>";
                              datatbl+="<td align=\"left\" nowrap=\"nowrap\">"+FeedList.get("byrcomm")+"</td>";
                              datatbl+="<td align=\"left\" nowrap=\"nowrap\">"+FeedList.get("dicuss")+"</td>";
                              datatbl+="<td align=\"center\" nowrap=\"nowrap\">"+FeedList.get("flDte")+"</td>";
                              datatbl+="<td align=\"left\" nowrap=\"nowrap\">"+FeedList.get("starttm")+"</td>";
                              datatbl+="<td align=\"center\" nowrap=\"nowrap\">"+FeedList.get("endtm")+"</td>";
                              datatbl+="</tr></table>";
       
                          }
                      if(bodymsg.indexOf("~CONFIRMED~") > -1)     
                      bodymsg = bodymsg.replace("~CONFIRMED~", datatbl); 
	              msg.append(bodymsg);
	              msg.append("</body>");
	              msg.append("</html>");
	              String mailSub=util.nvl((String)mailDtl.get("SUBJECT"));
	              if(mailSub.indexOf("~STT~") > -1)
	              mailSub = mailSub.replaceFirst("~STT~",(String)FeedList.get("status")); 
	              if(mailSub.indexOf("~DTE~") > -1)
	              mailSub = mailSub.replaceFirst("~DTE~", util.getToDteMarker());           
	              mail.setSubject(mailSub);
	              String senderID =(String)dbmsInfo.get("SENDERID");
	              String senderNm =(String)dbmsInfo.get("SENDERNM");
	              mail.setSender(senderID, senderNm); 
	              String toEml = util.nvl((String)mailDtl.get("TOEML"));
	             String[] emlToLst = toEml.split(","); 
	             if(emlToLst!=null){
	             for(int i=0 ; i <emlToLst.length; i++)
	             {
	             String to=util.nvl(emlToLst[i]);
	             if(!to.equals("")){   
	             mail.setTO(to);
	             }
	             }
	             }
	              String cceml = util.nvl((String)mailDtl.get("CCEML"));
	              String[] emlLst = cceml.split(",");
	                              if(emlLst!=null){
	                              for(int i=0 ; i <emlLst.length; i++)
	                              {
	                              mail.setCC(emlLst[i]);
	                              }
	                              }

	                               String bcceml = util.nvl((String)mailDtl.get("BCCEML"));
	                               String[] bccemlLst = bcceml.split(",");
	                               if(bccemlLst!=null){
	                                for(int i=0 ; i <bccemlLst.length; i++)
	                                {
	                                mail.setBCC(bccemlLst[i]);
	                                }
	                              }
	                          String mailMag = msg.toString();
	                          mail.setMsgText(mailMag);
	          logDetails.put("BYRID","");
	          logDetails.put("RELID","");
	          logDetails.put("TYP","FBK_BYR");
	          logDetails.put("IDN","");
	          String mailLogIdn=util.mailLogDetails(req,logDetails,"I");  
	          logDetails.put("MSGLOGIDN",mailLogIdn);
	          logDetails.put("MAILDTL",mail.send(""));
	          util.mailLogDetails(req,logDetails,"U");
	      }

	    public ActionForward contactGstSave(ActionMapping mapping, ActionForm form,
	                               HttpServletRequest req,
	                               HttpServletResponse response) throws Exception {
	         
	       HttpSession session = req.getSession(false);
	       InfoMgr info = (InfoMgr)session.getAttribute("info");
	       DBUtil util = new DBUtil();
	       DBMgr db = new DBMgr(); 
	       db.setCon(info.getCon());
	       util.setDb(db);
	       util.setInfo(info);
	       db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
	       util.setLogApplNm(info.getLoApplNm());
	       StringBuffer sb = new StringBuffer();
	       String nmeIdn = util.nvl(req.getParameter("nmeIdn"));
	       String gstin = util.nvl(req.getParameter("gstin"));
	       ArrayList ary = new ArrayList();
	       ary.add(nmeIdn);
	       ary.add(gstin);
	       String nmeUpd = "NME_PRP_PKG.NME_PRP_UPD(pNmeIdn => ?, pPrp => 'GSTIN', pVal => ?)";
	       int ct = db.execCall("nmeUpd",nmeUpd, ary);
	       if(ct > 0){
	         response.setContentType("text/xml"); 
	         response.setHeader("Cache-Control", "no-cache"); 
	         response.getWriter().write("<message>Goods and Services Tax Identification Number Saved Sucessfully</message>");
	       }
	       else{
	         response.setContentType("text/xml"); 
	         response.setHeader("Cache-Control", "no-cache"); 
	         response.getWriter().write("<message>Goods and Services Tax Identification Number Saved Fail</message>");
	       }
	       return null;
	       }
	}


	//~ Formatted by Jindent --- http://www.jindent.com
