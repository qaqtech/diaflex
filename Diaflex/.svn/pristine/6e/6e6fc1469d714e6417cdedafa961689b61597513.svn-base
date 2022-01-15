package ft.com.inward;


import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.LabComparisionExcel;
import ft.com.dao.labDet;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchForm;
import ft.com.marketing.SearchQuery;

import ft.com.marketing.StockPrpUpdForm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class TransferToMktSH extends DispatchAction {
    public TransferToMktSH() {
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
        util.updAccessLog(req,res,"Transfer to Marketing", "Unauthorized Access");
        else
        util.updAccessLog(req,res,"Transfer to Marketing", "init");
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
            util.updAccessLog(req,res,"Transfer to Marketing", "load start");
        TransferToMktSHForm form = (TransferToMktSHForm)af;
        GenericInterface genericInt=new GenericImpl(); 
        form.resetAll();
        ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_TFMKTSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_TFMKTSrch"); 
        info.setGncPrpLst(assortSrchList);
        form.setValue("srchRef", "vnm");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("Trans_To_MktSH");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("Trans_To_MktSH");
        allPageDtl.put("Trans_To_MktSH",pageDtl);
        }
        info.setPageDetails(allPageDtl);
//         util.imageDtls();           
            util.updAccessLog(req,res,"Transfer to Marketing", "load end");
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
            util.updAccessLog(req,res,"Transfer to Marketing", "view start");
        SearchQuery srchQuery=new SearchQuery();
        GenericInterface genericInt=new GenericImpl(); 
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        boolean isGencSrch = false;
        HashMap pktList = new HashMap();
        HashMap dbinfo = info.getDmbsInfoLst();
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        String cnt = (String)dbinfo.get("CNT");
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_TFMKTSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_TFMKTSrch"); 
        info.setGncPrpLst(genricSrchLst);
        //      ArrayList genricSrchLst = info.getGncPrpLst();
        TransferToMktSHForm form = (TransferToMktSHForm)af;
        ArrayList repMemoList = genericInt.genericPrprVw(req, res, "tfmktPrpList", "TFMKT_VW");
        
        String srchTyp =util.nvl((String)form.getValue("srchRef"));
        String vnm = util.nvl((String)form.getValue("vnmLst"));
        
        String seq = util.nvl((String)form.getValue("seq"));
         String frmdte = util.nvl((String)form.getValue("frmdte"));
        String todte = util.nvl((String)form.getValue("todte"));
        String Stt = util.nvl((String)form.getValue("Stt"));
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        ArrayList parms=new ArrayList();
        parms.add(Stt);
        String view = util.nvl(form.getView());
        if(vnm.equals("")){
            if(vnm.equals("") && frmdte.equals("") && todte.equals("")){
               
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
                Stt="AS_PRC,MKPP,MKLB,LB_CF";
            }
            if(cnt.equals("sbs")){
                if(Stt.equals("TFMKT")){
                    Stt="AS_PRC,MKPP,MKLB";
                }
            }
            
            Stt = util.getVnm(Stt);
        ArrayList params = new ArrayList();
        String srchRefQ = 
        "    Insert into gt_srch_rslt ( srch_id, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis, CERTIMG, DIAMONDIMG, JEWIMG, SRAYIMG, AGSIMG, MRAYIMG, RINGIMG, LIGHTIMG, REFIMG, VIDEOS, VIDEO_3D ) " + 
        "     select  1 srchId , pkt_ty, b.idn, b.vnm, b.qty qty, b.cts cts, b.dte, stt, nvl(cmp,upr) , rap_rte " +
        "     , cert_lab, cert_no, 'Z' flg, sk1, nvl(upr, cmp) " + 
        ", decode(rap_rte ,'1',null,'0',null, trunc((nvl(upr,cmp)/greatest(rap_rte,1)*100)-100, 2)), CERTIMG, DIAMONDIMG, JEWIMG, SRAYIMG, AGSIMG, MRAYIMG, RINGIMG, LIGHTIMG, REFIMG, VIDEOS, VIDEO_3D " + 
        "    from mstk b " +
        "     where stt in ("+Stt+") " + 
        //"     where stt in ('AS_PRC','LB_CF') " + 
        "    and pkt_ty = 'NR'" ;
       
        if (!frmdte.equals("") && !todte.equals("")){
            
          srchRefQ = 
                  "    Insert into gt_srch_rslt (stk_idn , vnm , pkt_dte, stt , flg , qty , cts , rmk , cert_lab , sk1, pkt_ty, quot, CERTIMG, DIAMONDIMG, JEWIMG, SRAYIMG, AGSIMG, MRAYIMG, RINGIMG, LIGHTIMG, REFIMG, VIDEOS, VIDEO_3D ) " + 
                  " select distinct b.idn, b.vnm,  b.dte, stt , 'Z' , qty , cts , tfl3 , b.cert_lab , b.sk1, pkt_ty, nvl(upr, cmp), CERTIMG, DIAMONDIMG, JEWIMG, SRAYIMG, AGSIMG, MRAYIMG, RINGIMG, LIGHTIMG, REFIMG, VIDEOS, VIDEO_3D  "+
                  "     from lab_inward_ora a , mstk b  "+
                  " where a.idn = b.idn  and b.stt in ("+Stt+") "+
              "and b.cts > 0  and a.flg='P' " +            
             " and trunc(a.sdt) between to_date('"+frmdte+"' , 'dd-mm-yyyy') and to_date('"+todte+"' , 'dd-mm-yyyy') and a.idn = b.idn" ;            
        }
         
            if(!view.equals("")){
            if(!vnm.equals(""))
                vnm = util.getVnm(vnm);
            if(!vnm.equals("")){
                String[] vnmLst = vnm.split(",");
                int loopCnt = 1 ;
                System.out.println(vnmLst.length);
                float loops = ((float)vnmLst.length)/stepCnt;
                loopCnt = Math.round(loops);
//                if(new Float(String.valueOf(new Float(loops).intValue())) == loops) {
//                    
//                } else
//                    loopCnt += 1 ;
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
                    
                   
                    String srchStr = "";
                    
                    if(srchTyp.equals("vnm"))
                        srchStr = " and ( b.vnm in ("+vnmSub+") or b.tfl3 in ("+vnmSub+") ) ";
                    else if(srchTyp.equals("cert_no"))
                        srchStr = " and b.cert_no in ("+vnmSub+")";
                   
                   
                   String srchRefQloc = srchRefQ + srchStr ;
                  // ary.add(vnm);
                  
                  ct = db.execUpd(" Srch Prp ", srchRefQloc, new ArrayList());
                 
                }
                
            }else{
                ct = db.execUpd(" Srch Prp ", srchRefQ, new ArrayList());
            }
            }else{
            ct = db.execUpd(" Srch Ref ", srchRefQ, params);
            }
//        String pktPrp = "ISS_RTN_PKG.VERIFY_TRF_TO_MKTG(?)";
//        params = new ArrayList();
//        params.add("TFMKT_VW");
//        ct = db.execCall(" Srch Prp ", pktPrp, params);
        
    ct = db.execUpd("updte gt","update gt_srch_rslt a set (cmp, quot) = (select cmp, upr from mstk b where a.stk_idn = b.idn)", new ArrayList());
            
        req.setAttribute("vnmLst", vnm);
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ArrayList ary=null;
        ary = new ArrayList();
        ary.add("TFMKT_VW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        pktList = srchQuery.SearchResult(req,res,"'Z'", repMemoList);
        
        
        }
            
        
            
        if(pktList!=null && pktList.size() >0){
            ct = db.execUpd("updte gt","update gt_srch_rslt set prp_090='TRF' where flg='Z'", new ArrayList());
            HashMap totals = GetTotal(req,res,"Z",repMemoList);
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
                 
      ResultSet rs = db.execSql("tfrMktlb", tfrMktlb, new ArrayList());
      
      while(rs.next()){
           tfrDtl = new labDet();
          tfrDtl.setLabDesc(rs.getString("dsc"));
          tfrDtl.setLabVal(rs.getString("chr_fr"));
          tfrListLb.add(tfrDtl);
       }
        rs.close();
      form.setTfrSttPRIList(tfrListLb);
        if(util.nvl((String)form.getValue("Stt")).equals("TFMKT") || view.equals("")) { 
        parms=new ArrayList();    
        parms.add("AS_PRC"); 
        }
        String tfrMkt ="select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and a.chr_to=? and" + 
                       " b.mdl = 'TFMKT' and b.nme_rule = 'TFMKT' and a.til_dte is null order by a.srt_fr ";
       rs = db.execSql("tfrMkt", tfrMkt, parms);
        
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
        rs.close();
        form.setTfrSttList(tfrList);
        genericInt.genericPrprVw(req, res, "TFMKTEDITList", "TFMKT_EDIT");
        session.setAttribute("pktList", pktList);
        req.setAttribute("view", "Y");
            util.updAccessLog(req,res,"Transfer to Marketing", "view end");
        return am.findForward("load");
        }
    }
    public ActionForward details(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Transfer to Marketing", "details start");
    TransferToMktSHForm form = (TransferToMktSHForm)af;
    GenericInterface genericInt=new GenericImpl(); 
    genericInt.genericPrprVw(req, res, "PLOT_VW", "PLOT_VW");
    String mstkidn= util.nvl((String)req.getParameter(("mstkidn")));
    String vnm= util.nvl((String)req.getParameter(("vnm"))).trim();
    HashMap pktDtl=new HashMap();
    String dtlQ="select a.mstk_idn, a.lab,b.dta_typ, a.mprp, a.srt, a.grp , c.mdl , decode(b.dta_typ, 'C', a.val,'N', to_char(a.num), 'D', to_char(a.dte,'dd-mm-rrrr'), nvl(a.txt,'')) val " +
    " from stk_dtl a, mprp b , rep_prp c,mstk d" +
    " where a.mprp = b.prp and a.mstk_idn=d.idn and d.vnm= '"+vnm+"'"+
    " and b.dta_typ in ('C','N','T','D') and c.prp = b.prp and c.flg = 'Y' and " +
    " c.mdl ='PLOT_VW' and a.grp=1 " +
    " order by c.mdl , c.rnk";
    ResultSet rs = db.execSql("Packet Dtl", dtlQ , new ArrayList());
    while(rs.next()){
    String mprp=util.nvl(rs.getString("mprp"));
    pktDtl.put(mprp,util.nvl2(rs.getString("val"),"-"));
    }
    rs.close();
        String certQ="select cert_no,diamondImg,jewImg,srayImg,agsImg,mrayImg,ringImg,lightImg,refImg,videos,certImg from mstk where vnm= '"+vnm+"'";
        rs = db.execSql("Cert No", certQ , new ArrayList());
        while(rs.next()){
        pktDtl.put("diamondImg",util.nvl(rs.getString("diamondImg"),"N"));
        pktDtl.put("jewImg",util.nvl(rs.getString("jewImg"),"N"));
            pktDtl.put("srayImg",util.nvl(rs.getString("srayImg"),"N"));
            pktDtl.put("agsImg",util.nvl(rs.getString("agsImg"),"N"));
            pktDtl.put("mrayImg",util.nvl(rs.getString("mrayImg"),"N"));
            pktDtl.put("ringImg",util.nvl(rs.getString("ringImg"),"N"));
            pktDtl.put("lightImg",util.nvl(rs.getString("lightImg"),"N"));
            pktDtl.put("refImg",util.nvl(rs.getString("refImg"),"N"));
            pktDtl.put("videos",util.nvl(rs.getString("videos"),"N"));
            pktDtl.put("certImg",util.nvl(rs.getString("certImg"),"N"));
        }
            rs.close();
    req.setAttribute("pktDtl", pktDtl);
    req.setAttribute("vnm", vnm);
    if(pktDtl!=null && pktDtl.size()>0)
    req.setAttribute("View", "Y");
            util.updAccessLog(req,res,"Transfer to Marketing", "details end");
    return am.findForward("details");
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
            util.updAccessLog(req,res,"Transfer to Marketing", "save start");
        TransferToMktSHForm form = (TransferToMktSHForm)af;
        String Stt = util.nvl((String)form.getValue("Stt"));
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
        getstt(req , String.valueOf(lseq));
            util.updAccessLog(req,res,"Transfer to Marketing", "save end");
        return am.findForward("loadPrc");
        }
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
            util.updAccessLog(req,res,"Transfer to Marketing", "memo start");
        int empId = 0;
        String empIdqry="select nme_idn from nme_v where typ='EMPLOYEE'";
        ResultSet rs = db.execSql("empId", empIdqry, new ArrayList());
        if(rs.next()) {
          empId=rs.getInt(1);
        }
        int prcid=0;
        rs = db.execSql("mprcId", "select idn from mprc where in_stt='AS_PRC' and stt='A'", new ArrayList());
        if(rs.next()) {
          prcid = rs.getInt(1);
        }  
        rs.close();
        int issueIdn = 0;
        ArrayList params = new ArrayList();
        String gtFetch = " select stk_idn , cts , qty from gt_srch_rslt where flg='S'";
        rs = db.execSql("gtSrch", gtFetch ,new ArrayList());
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
        if(issueIdn>0){
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            String webUrl = (String)dbinfo.get("REP_URL");
            String reqUrl = (String)dbinfo.get("HOME_DIR");
            String repPath = (String)dbinfo.get("REP_PATH");
           String url = repPath+"/reports/rwservlet?"+cnt+"&report="+reqUrl+"\\reports\\assort_rpt.jsp&p_iss_id="+issueIdn ;
            res.sendRedirect(url);    
        }
            util.updAccessLog(req,res,"Transfer to Marketing", "memo end");
    return null;
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
            util.updAccessLog(req,res,"Transfer to Marketing", "pktPrint start");
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
            
             
              ResultSet rs = db.execSql("mkt_prc", "select  seq_mkt_prc.nextval from dual ", new ArrayList());
              if(rs.next())
                  mkt_prc = rs.getInt(1);
              rs.close();
              String insertPrc = " insert into mkt_prc(prc_idn ,mstk_idn ,rep_dte) select ?, idn, sysdate from" +
                  " pri_batch where pri_seq = ? ";
              ary.add(String.valueOf(mkt_prc));
              ary.add(seq);
               ct = db.execUpd("insert mkt_prc", insertPrc, ary);
              if(ct>0){
                 String url = repPath+"/reports/rwservlet?"+cnt+"&report="+reqUrl+"\\reports\\"+repNme ;
                  res.sendRedirect(url);    
              }
             util.updAccessLog(req,res,"Transfer to Marketing", "pktPrint end");
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
            util.updAccessLog(req,res,"Transfer to Marketing", "transMkt start");
              int AVCount = 0;
              int PPCount = 0;
              int LBCount = 0;
              int NNCount = 0;
              String errorMsg="";
             ArrayList ary = null;
            TransferToMktSHForm form = (TransferToMktSHForm)af;
            String updateMstk = "";
            String tranMkt = util.nvl((String)form.getValue("save"));
            String appyLab = util.nvl((String)form.getValue("saveLab"));
            String saveChg = util.nvl((String)form.getValue("saveChg"));
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("Trans_To_MktSH");
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            String gtFetch = "select stk_idn , vnm , cert_lab from gt_srch_rslt where flg='S' and prp_090='TRF' ";
            pageList= ((ArrayList)pageDtl.get("ALLOWTRF") == null)?new ArrayList():(ArrayList)pageDtl.get("ALLOWTRF");            
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            String dflt_val=(String)pageDtlMap.get("dflt_val");
            if(dflt_val.equals("Y"))
            gtFetch = "select stk_idn , vnm , cert_lab from gt_srch_rslt where flg='S' and nvl(quot,0) > 0  and prp_090='TRF'";
            }
            }
            int ct =0;
            ResultSet rs = db.execSql("getFecth", gtFetch, new ArrayList());
            while(rs.next()){
                    ct =0;
                String stk_idn = util.nvl(rs.getString("stk_idn"));
                String vnm = util.nvl(rs.getString("vnm"));
                String certLab = util.nvl(rs.getString("cert_lab"));
                String sttVal = util.nvl((String)form.getValue("STT_"+stk_idn));
                
                String mnlVal = util.nvl((String)form.getValue("mnl_"+stk_idn));
                String diffVal = util.nvl((String)form.getValue("DAMT_"+stk_idn)); 
                if(!sttVal.equals("")){
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
                        if(sttVal.equals("NONE"))
                            NNCount++;
                      }else{
                             errorMsg = errorMsg+ vnm+"," ;
                        }
                                     
                
                }
        rs.close();
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
                       ct = db.execUpd("insertPrc", tffLog, ary);
                   } if(PPCount!=0){
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
                   }if(NNCount!=0){
                      msg = msg+" None : "+NNCount ;
                       ary = new ArrayList();
                       ary.add("NONE");
                       ary.add(info.getByrNm());
                       ary.add(String.valueOf(NNCount));
                       ct = db.execUpd("insertPrc", tffLog, ary);
                   }
              
                req.setAttribute("msg", msg);
            }
            if(!errorMsg.equals("")){
                req.setAttribute("errormsg", errorMsg+" Price not found");
                
            }
            util.updAccessLog(req,res,"Transfer to Marketing", "transMkt end");
           return am.findForward("load");
        }
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
          util.updAccessLog(req,res,"Transfer to Marketing", "CrtExcel start");
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
          util.updAccessLog(req,res,"Transfer to Marketing", "CrtExcel end");
       return null;
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
            util.updAccessLog(req,res,"Transfer to Marketing", "status start");
        TransferToMktSHForm form = (TransferToMktSHForm)af;
        String lseq = req.getParameter("seq");
        getstt(req , lseq);
            util.updAccessLog(req,res,"Transfer to Marketing", "status end");
        return am.findForward("loadPrc");
        }
    }
    public void getstt( HttpServletRequest req , String lseq){
        try {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            ResultSet rs = null;
            int count = 0;
            int out = 0;
            ArrayList ary = new ArrayList();
            String msg = "Re Pricing in process....";
          
            if (!lseq.equals("")) {
                String df_pricing_status =
                    "select df_pricing_status(?) stt from dual";
                ary = new ArrayList();
                ary.add(lseq);
                rs = db.execSql("price Status", df_pricing_status, ary);
                if (rs.next())
                    out = rs.getInt(1);
                rs.close();
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
            rs = db.execSql("price count ", sql, ary);
            if (rs.next())
                count = rs.getInt(1);
            rs.close();
            req.setAttribute("seq", lseq);
            req.setAttribute("msg", msg);
            req.setAttribute("out", String.valueOf(out));
            req.setAttribute("count", String.valueOf(count));
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    }
    
    
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

       

    String reportQuery = " select vnm ,sk1, stk_idn ,to_char(pkt_dte,'dd-MON-yyyy') pkt_dte, rap_rte ,cts , decode(rap_rte ,'1',null, trunc((quot/greatest(rap_rte,1)*100)-100, 2)) r_dis, to_char(trunc(cts,2) * quot, '99999990.00') vlu,to_char(trunc(cts,2) * rap_rte, '99999990.00') rapvlu,kts_vw,cmnt ";

    

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

     }

    

     itmHdr.add("RAP_RTE");

     itmHdr.add("RAP VLU");

     reportQuery = reportQuery +" from GT_SRCH_MULTI order by sk1,vnm,stt";

    

     ResultSet rs = db.execSql("conditionSql", reportQuery ,new ArrayList() );

    

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

         rs.close();

     } catch (SQLException sqle) {

         // TODO: Add catch code

         sqle.printStackTrace();

     }

        req.setAttribute("ItemHdr", itmHdr);

    return pktDtlList ;   

    }
    
    public ActionForward compExcel(ActionMapping am,

                                     ActionForm af,

                                     HttpServletRequest req,

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

              util.updAccessLog(req,res,"Transfer to Marketing", "CrtExcel start");

          HashMap dbinfo = info.getDmbsInfoLst();

          String clnt = (String)dbinfo.get("CNT");

          ExcelUtil xlUtil = new ExcelUtil();

          xlUtil.init(db, util, info);

          String btn = util.nvl(req.getParameter("btn"));

           ArrayList pktDtl = pktList(req, res,"S");

              String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N");

              int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100"));

              HSSFWorkbook hwb = null;

              ArrayList itemHdr = (ArrayList)req.getAttribute("ItemHdr");

              hwb =  xlUtil.getGenXl(itemHdr, pktDtl);

              int pktListsz=pktDtl.size();

              if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){

              String contentTypezip = "application/zip";

              String fileNmzip = "AssotLabCompExcel"+util.getToDteTime();

              OutputStream outstm = res.getOutputStream();

              ZipOutputStream zipOut = new ZipOutputStream(outstm);

              ZipEntry entry = new ZipEntry(fileNmzip+".xls");

              zipOut.putNextEntry(entry);

              res.setHeader("Content-Disposition","attachment; filename="+fileNmzip+".zip");

              res.setContentType(contentTypezip);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 hwb.write(bos);
                 bos.writeTo(zipOut);      
                zipOut.closeEntry();
                 zipOut.flush();
                 zipOut.close();
                 outstm.flush();
                 outstm.close(); 

              }else{

                  String CONTENT_TYPE = "getServletContext()/vnd-excel";

                  String fileNm = "AssotLabCompExcel"+util.getToDteTime()+".xls";

                  res.setContentType(CONTENT_TYPE);

                  res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);

                  OutputStream out = res.getOutputStream();

                  hwb.write(out);

                  out.flush();

                  out.close();

                  }

          

              util.updAccessLog(req,res,"Transfer to Marketing", "CrtExcel end");

           return null;

          }

        }
    public ActionForward stockUpd(ActionMapping am , ActionForm fm , HttpServletRequest req , HttpServletResponse res){
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
            util.updAccessLog(req,res,"Transfer to Marketing", "stockUpd start");
        TransferToMktSHForm form = (TransferToMktSHForm)fm;
        GenericInterface genericInt=new GenericImpl(); 
        String stkIdn = req.getParameter("mstkIdn");
        if(stkIdn==null)
            stkIdn = (String)form .getValue("mstkIdn");
       
        ArrayList grpList = new ArrayList();
        HashMap stockPrpUpd = new HashMap();
        String stockPrp = "select mstk_idn, lab,b.dta_typ, mprp, a.srt, grp , decode(b.dta_typ, 'C', a.val,'N', to_char(a.num), 'D', to_char(dte,'dd-mm-rrrr'), nvl(txt,'')) val " + 
        "from stk_dtl a, mprp b , rep_prp c " + 
        "where a.mprp = b.prp and mstk_idn =? and a.grp=1  and b.dta_typ in ('C','N','T','D') and c.prp = b.prp and c.flg = 'Y' and mdl='AS_UPD_DFLT' " + 
        "order by grp, psrt, mprp,lab ";
        ArrayList ary = new ArrayList();
        ary.add(stkIdn);
        String pgrp ="";
        ResultSet rs = db.execSql("stockDtl", stockPrp, ary);
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
          
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        form.setValue("mstkIdn", stkIdn);
        genericInt.genericPrprVw(req, res, "ASPRCEDITList", "AS_PRC_EDIT");
        req.setAttribute("stockList",stockPrpUpd);
        req.setAttribute("grpList", grpList);
        req.setAttribute("mstkIdn", stkIdn);
            util.updAccessLog(req,res,"Transfer to Marketing", "stockUpd end");
      return am.findForward("update");
        }
    }
    
    public ActionForward stockUpdTrfMkt(ActionMapping am , ActionForm fm , HttpServletRequest req , HttpServletResponse res){
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
        
        TransferToMktSHForm udf = (TransferToMktSHForm)fm;
        SearchQuery srchQuery=new SearchQuery();
        String stkIdn = req.getParameter("mstkIdn");
        String mdl = req.getParameter("mdl");
        String viewMdl = "PRP_UPD_DFLT";
        String lastpage = util.nvl(req.getParameter("lastpage"));
        String currentpage = util.nvl(req.getParameter("currentpage"));
        String issueId = util.nvl(req.getParameter("issueID"));
        String vnm="";
        if(stkIdn==null){
            stkIdn = (String)udf.getValue("mstkIdn");
            mdl = (String)udf.getValue("mdl");
        }
        String reqviewMdl = util.nvl(req.getParameter("viewMdl"));
        if(!reqviewMdl.equals(""))
            viewMdl = reqviewMdl;
           HashMap stockList = (HashMap)session.getAttribute("pktList");  
           ArrayList pktStkIdnList = (ArrayList)session.getAttribute("pktStkIdnList");
           
        if(!currentpage.equals("")){
             stkIdn = (String)pktStkIdnList.get(Integer.parseInt(currentpage));
             mdl="AS_PRC_EDIT";
        }
        HashMap pktData = (HashMap)stockList.get(stkIdn);
        vnm = util.nvl((String)pktData.get("vnm"));
        ArrayList grpList = new ArrayList();
        HashMap stockPrpUpd = new HashMap();
        String stockPrp = "select mstk_idn, lab,b.dta_typ, mprp, a.srt, grp , decode(b.dta_typ, 'C', a.val,'N', to_char(a.num), 'D', format_to_date(dte), nvl(txt,'')) val " + 
        "from stk_dtl a, mprp b , rep_prp c " + 
        "where a.mprp = b.prp and mstk_idn =?  and b.dta_typ in ('C','N','T','D') and c.prp = b.prp and c.flg = 'Y' and mdl='"+viewMdl+"' " + 
        "order by grp, psrt, mprp,lab ";
        ArrayList ary = new ArrayList();
        ary.add(stkIdn);
        String pgrp ="";
        ResultSet rs = db.execSql("stockDtl", stockPrp, ary);
        try {
            while(rs.next()) {
                String grp = util.nvl(rs.getString("grp"));
                if(!grpList.contains(grp))
                    grpList.add(grp);
                
                String mprp =util.nvl(rs.getString("mprp"));
                String val = util.nvl(rs.getString("val"));
              
                String msktIdn = util.nvl(rs.getString("mstk_idn"));
                stockPrpUpd.put(msktIdn+"_"+mprp+"_"+grp, val);
                udf.setValue(mprp+"_"+grp, util.nvl(rs.getString("val")));
                udf.setValue("lab_"+grp, util.nvl(rs.getString("lab")));
            }
            rs.close();
          
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        udf.setValue("mstkIdn", stkIdn);
         udf.setValue("mdl", mdl);
         udf.setValue("issueId", issueId);
         
        PRPUPDDFLTList(req);
        req.setAttribute("stockList",stockPrpUpd);
        session.setAttribute("grpList", grpList);
        req.setAttribute("mstkIdn", stkIdn);
        ArrayList  prpUpdList = srchQuery.getPRPUPGrp(req,res,mdl);
        info.setValue("prpUpdLst", prpUpdList);
        String grp = util.labGroup();
        req.setAttribute("grp", grp);
        String cutClrGrp = util.CUTCRLGroup();
        req.setAttribute("cutClr", cutClrGrp);
       req.setAttribute("viewMdl", viewMdl);
       req.setAttribute("editmdl", mdl);
           udf.setValue("currentpage", currentpage);
           req.setAttribute("mstkIdn", stkIdn);
           udf.setValue("lastpage", lastpage);
           udf.setValue("vnm", vnm);
           udf.setValue("url", "transferMktSH.do?method=stockUpdTrfMkt&mstkIdn="+stkIdn+"&&mdl="+mdl);
           req.setAttribute("PopUpidn", stkIdn);
      return am.findForward("updatetrf");
       }
    }
    
    
    public ActionForward saveTrfMkt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
         TransferToMktSHForm udf = (TransferToMktSHForm)af;
         SearchQuery srchQuery=new SearchQuery();
         util.updAccessLog(req,res,"stock properties update", "stock properties update save");
         HashMap mprp = info.getMprp();
         String mstkIdn = (String)udf.getValue("mstkIdn");
         String issueId = util.nvl((String)udf.getValue("issueId"));
        ArrayList prpUpdList = (ArrayList)info.getValue("prpUpdLst");
        ArrayList grpList = (ArrayList)session.getAttribute("grpList");
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        String rapsync = util.nvl((String)dbinfo.get("RAPSYNC"));
             String lastpage = util.nvl(req.getParameter("lastpage"));
             String currentpage = util.nvl(req.getParameter("currentpage"));
             String vnm="";
             if(lastpage.equals("") && currentpage.equals("")){
                 lastpage = util.nvl((String) udf.getValue("lastpage"));
                 currentpage = util.nvl((String) udf.getValue("currentpage"));
                 vnm=util.nvl((String) udf.getValue("vnm"));
             }
             HashMap stockList = (HashMap)session.getAttribute("pktList");  
             ArrayList pktStkIdnList = (ArrayList)session.getAttribute("pktStkIdnList");
             HashMap pktData = (HashMap)stockList.get(mstkIdn);
             vnm = util.nvl((String)pktData.get("vnm"));
        if(grpList!=null && grpList.size() >0){
        for(int g=0 ; g<grpList.size() ;g++){
            String grp=(String)grpList.get(g);
        if(grp.equals("1") || cnt.equals("hk")){  
        if(prpUpdList!=null && prpUpdList.size() >0){
            for(int i=0 ; i<prpUpdList.size() ;i++){
            String lmprp = (String)prpUpdList.get(i);
            String mprpTyp = util.nvl((String)mprp.get(lmprp+"T"));
            String updVal = util.nvl((String)udf.getValue(lmprp+"_"+grp));
           if(!updVal.equals("") && !updVal.equals("0")){
            
            String lab = (String)udf.getValue("lab_"+grp);
            ArrayList ary = new ArrayList();
            ary.add(mstkIdn);
            ary.add(lmprp);
            ary.add(updVal);
            ary.add(lab);
            ary.add(mprpTyp);
            ary.add(grp);
            String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?, pPrpTyp => ?, pgrp => ? )";
            int ct = db.execCall("stockUpd",stockUpd, ary);
            if(lmprp.equals("LAB")){
                ary = new ArrayList();
                ary.add(updVal);
                ary.add(mstkIdn);
                ary.add(grp);
                ary.add(updVal);
             String sql= "update stk_dtl set lab = ? where mstk_idn = ? and grp = ? and lab <> ?";
             db.execUpd("update stkDtl", sql, ary);
            }
            if(!issueId.equals("")){
                ary = new ArrayList();
                ary.add(issueId);
                ary.add(mstkIdn);
                ary.add(lmprp);
                ary.add(updVal);
                 ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
                
            }
            }}
        }}  
        }
        }
        ArrayList ary1 = new ArrayList();
         ary1.add(mstkIdn);
        int ct =  db.execCall("stockUpd","STK_RAP_UPD(pIdn => ?)", ary1);
        
    //        if(rapsync.equals("Y"))
    //         new SyncOnRap(cnt).start();
        
        udf.setMsg("Propeties Get update successfully");
           util.updAccessLog(req,res,"stock properties update", "stock properties update save Done");
             stockUpdTrfMkt(am, af , req, res);
             udf.setValue("currentpage", currentpage);
             req.setAttribute("mstkIdn", mstkIdn);
             udf.setValue("lastpage", lastpage);
             udf.setValue("vnm", vnm);
             udf.setValue("url", "transferMktSH.do?method=stockUpdTrfMkt&mstkIdn="+mstkIdn+"&&mdl=AS_PRC_EDIT");
             return am.findForward("updatetrf");
         }
     }
    public ArrayList PRPUPDDFLTList(HttpServletRequest req){
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
        String viewMdl = "PRP_UPD_DFLT";
        String reqviewMdl = util.nvl(req.getParameter("viewMdl"));
        if(!reqviewMdl.equals(""))
            viewMdl = reqviewMdl;
            ArrayList viewPrp = null;
            try {
                if (viewPrp == null) {

                    viewPrp = new ArrayList();
                    ResultSet rs1 =
                        db.execSql(" Vw Lst ", "Select prp  from rep_prp where mdl = '"+viewMdl+"' and flg='Y' order by rnk ",
                                   new ArrayList());
                    while (rs1.next()) {

                       viewPrp.add(rs1.getString("prp"));
                    }
                    rs1.close();
                    session.setAttribute("PRPUPDDFLTList", viewPrp);
                }

            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        
        return viewPrp;
    }
    
    public HashMap GetTotal(HttpServletRequest req ,HttpServletResponse res, String flg,ArrayList repMemoList){
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
        int indexCp = repMemoList.indexOf("NET_PUR_RTE");
        indexCp=indexCp+1;
        String prpCp = "";
        String vlustr="";
        if(indexCp!=0){
            if(indexCp<10)
          prpCp = "prp_00"+indexCp;
            else
          prpCp = "prp_0"+indexCp;
            vlustr=" ,  to_char(sum( (cts* "+prpCp+") / NVL (exh_rte, 1)),'99999999999999990.00') vlu  ";
        }else{
             vlustr=" , to_char(sum( (cts* quot) / NVL (exh_rte, 1)),'99999999999999990.00') vlu  ";
         }
        String gtTotal ="Select count(*) qty, sum(cts) cts "+vlustr+" from gt_srch_rslt where flg = ?";
        ArrayList ary = new ArrayList();
        ary.add(flg);
        ResultSet rs = db.execSql("getTotal", gtTotal, ary);
        try {
            if (rs.next()) {
             gtTotalMap.put("qty", rs.getString("qty"));
             gtTotalMap.put("cts", rs.getString("cts"));
                gtTotalMap.put("vlu", rs.getString("vlu"));
            }
            rs.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return gtTotalMap ;
    }
}
