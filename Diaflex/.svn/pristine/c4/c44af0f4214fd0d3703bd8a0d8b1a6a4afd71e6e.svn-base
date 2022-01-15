package ft.com.mpur;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.dao.ByrDao;
import ft.com.dao.GenDAO;
import ft.com.fileupload.FileUploadAction;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.inward.TransferToMktSHForm;
import ft.com.marketing.SearchForm;

import ft.com.marketing.SearchQuery;

import java.io.ByteArrayOutputStream;
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

import java.util.Vector;

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

public class PurchaseActPricingAction  extends DispatchAction {
    public PurchaseActPricingAction() {
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
        util.updAccessLog(req,res,"Purchase Action", "load start");
        PurchasePricingForm purchaseForm = (PurchasePricingForm) af;
        GenericInterface genericInt = new GenericImpl();
        purchaseForm.resetAll();
        ArrayList venderIdnList = getvndridn(req);
        purchaseForm.setVenderList(venderIdnList);
           ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_purGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_purGNCSrch");
       info.setGncPrpLst(assortSrchList);
           HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
           HashMap pageDtl=(HashMap)allPageDtl.get("PUR_ACTION");
           if(pageDtl==null || pageDtl.size()==0){
           pageDtl=new HashMap();
           pageDtl=util.pagedef("PUR_ACTION");
           allPageDtl.put("PUR_ACTION",pageDtl);
           }
           info.setPageDetails(allPageDtl);
           util.updAccessLog(req,res,"Purchase Action", "load end");
        return am.findForward("PurPri");
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
            util.updAccessLog(req,res,"Purchase Action", "view start");
        PurchasePricingForm purchaseForm = (PurchasePricingForm) af;
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        boolean isGencSrch = false;
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_purGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_purGNCSrch");
        info.setGncPrpLst(genricSrchLst);
        //      ArrayList genricSrchLst = info.getGncPrpLst();
        String refno = util.nvl((String)purchaseForm.getValue("refno"));
        String venderId = util.nvl((String)purchaseForm.getValue("vender"));
        String purIdn = util.nvl((String)purchaseForm.getValue("purIdn"));
        String typ = util.nvl((String)purchaseForm.getValue("typ"));
        ArrayList puridLst = new ArrayList();
        ArrayList typList = new ArrayList();
        HashMap paramsMap = new HashMap();
        HashMap avgDtl = new HashMap();
        paramsMap.put("stt", typ);
        paramsMap.put("rlnId",venderId);
        paramsMap.put("purId",purIdn);
        paramsMap.put("mdl", "PUR_VW");
        if(refno.equals("")){
                HashMap prp = info.getPrp();
                HashMap mprp = info.getMprp();
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
                            String reqVal1 = util.nvl((String)purchaseForm.getValue(lprp + "_" + lVal),"");
                            if(!reqVal1.equals("")){
                            paramsMap.put(lprp + "_" + lVal, reqVal1);
                            }
                            }
                        }else{
                            String fldVal1 = util.nvl((String)purchaseForm.getValue(lprp+"_1"));
                           String fldVal2 = util.nvl((String)purchaseForm.getValue(lprp+"_2"));
                           if(fldVal2.equals(""))
                               fldVal2=fldVal1;
                             if(!fldVal1.equals("") && !fldVal2.equals("")){
                                            paramsMap.put(lprp+"_1", fldVal1);
                                            paramsMap.put(lprp+"_2", fldVal2);
                            }   
                        }            
                }
                isGencSrch = true; 
                int lSrchId=util.genericSrchEntries(paramsMap);
            String pur_pkg = "PUR_PKG.SRCH(? ,?)";
            ArrayList ary = new ArrayList();
            ary.add(String.valueOf(lSrchId));
            ary.add("PUR_VW");
            System.out.println("pur_pkg"+pur_pkg);     
            ct = db.execCall("Purchase_srch", pur_pkg, ary);
            purchaseForm.reset();
        }else{
            purchaseForm.reset();
            paramsMap.put("refno", refno);
            purchaseForm.setValue("refno", refno);
            FecthResult(req,res,paramsMap);
        }
        insertGtmemo(req,res);
        String getMemoInfo = "Select Trunc(Sum(Quot*Cts),2) vlu,Trunc(Sum(Quot*Cts)/Sum(Cts),2) avg,\n" + 
        "trunc(((sum(trunc(cts,2)*Quot) / sum(trunc(cts,2)* greatest(rap_rte,1) ))*100) - 100, 2) avg_dis,\n" + 
        "         Trunc(Sum(Cmp*Cts),2) cmpvlu,Trunc(Sum(Cmp*Cts)/Sum(Cts),2) cmpavg,\n" + 
        "         trunc(((sum(trunc(cts,2)*cmp) / sum(trunc(cts,2)* greatest(rap_rte,1) ))*100) - 100, 2) cmpavg_dis\n" + 
        "         from gt_srch_rslt  ";

          ArrayList rsList = db.execSqlLst(" Memo Info", getMemoInfo, new ArrayList());    
          PreparedStatement pst = (PreparedStatement)rsList.get(0);
        ResultSet  mrs = (ResultSet)rsList.get(1);
        if (mrs.next()) {
            purchaseForm.setAvgDis(mrs.getString("avg_dis"));
            purchaseForm.setVlu(mrs.getString("vlu"));
            purchaseForm.setAvg(mrs.getString("avg"));
            avgDtl.put("vlu",util.nvl(mrs.getString("vlu")));
            avgDtl.put("avg_dis",util.nvl(mrs.getString("avg_dis")));
            avgDtl.put("avg",util.nvl(mrs.getString("avg")));
            avgDtl.put("cmpvlu",util.nvl(mrs.getString("cmpvlu")));
            avgDtl.put("cmpavg_dis",util.nvl(mrs.getString("cmpavg_dis")));
            avgDtl.put("cmpavg",util.nvl(mrs.getString("cmpavg")));
        }
        mrs.close();
         pst.close();
        ArrayList pktList = SearchResult(req,res,"Z",typ,af);
        HashMap totalMap = GetTotal(req);
        GenDAO gen=new GenDAO();
        ArrayList ary=new ArrayList();
        String purQ="select pur_idn, pur_idn||' '||nvl(flg,'') dsc from pur_pndg_idn_v where pur_idn=? ";
        ary.add(purIdn);
          rsList = db.execSqlLst("Pur Dsc", purQ,ary); 
           pst = (PreparedStatement)rsList.get(0);
           mrs = (ResultSet)rsList.get(1);
        if (mrs.next()) {
            gen.setIdn(util.nvl(mrs.getString("dsc")));
            gen.setNmeIdn(util.nvl(mrs.getString("pur_idn")));
        }
        mrs.close();
        pst.close();
            
            ArrayList params = new ArrayList();
            params.add(purIdn);
            String lastCnt =" select  flg from mpur where pur_idn=?";
          rsList = db.execSqlLst("lastCnt", lastCnt, params);
           pst = (PreparedStatement)rsList.get(0);
           mrs = (ResultSet)rsList.get(1);
            while(mrs.next()){
                 req.setAttribute("flg", mrs.getString("flg"));
            }
            mrs.close();
            pst.close();
        puridLst.add(gen);
        GenDAO gentyp=new GenDAO();
        gentyp.setIdn(typ);
        if(typ.equals("O"))
        gentyp.setNmeIdn("Offer");
        if(typ.equals("B"))
        gentyp.setNmeIdn("Buy");
        if(typ.equals("R"))
        gentyp.setNmeIdn("Review");
        if(typ.equals("E"))
        gentyp.setNmeIdn("External");
        if(typ.equals("ON"))
        gentyp.setNmeIdn("On Approval");
        typList.add(gentyp);
        purchaseForm.setTypList(typList);
        purchaseForm.setPurIdnList(puridLst);
        purchaseForm.setValue("vender", venderId);
        purchaseForm.setValue("purIdn", purIdn);
        purchaseForm.setValue("typ", typ);
        req.setAttribute("totalMap", totalMap);
        req.setAttribute("type", typ);
        session.setAttribute("pktList", pktList);
        req.setAttribute("view", "Y");
        req.setAttribute("avgDtl",avgDtl);
            util.updAccessLog(req,res,"Purchase Action", "view end");
        return am.findForward("PurPri");
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
            util.updAccessLog(req,res,"Purchase Action", "save start");
        PurchasePricingForm purchaseForm = (PurchasePricingForm) af;
        ArrayList params = null;
        String trfStkidn="";
        int stkidn;
        String newVnm="";
        int newpurIdn;
        ArrayList stockList = (ArrayList)session.getAttribute("pktList");
        String cmptPri = util.nvl((String)purchaseForm.getValue("cmptPri"));
        String trfToStk = util.nvl((String)purchaseForm.getValue("trfToStk"));
        String trfToBuy = util.nvl((String)purchaseForm.getValue("trfToBuy"));
        String offer = util.nvl((String)purchaseForm.getValue("offer"));
        String venderId = util.nvl((String)purchaseForm.getValue("vender"));
        String purIdn = util.nvl((String)purchaseForm.getValue("purIdn"));
        String stk_stt = util.nvl((String)purchaseForm.getValue("stk_stt"));
        if(!offer.equals("")){
        String updateQ="update gt_srch_rslt set flg='O' where flg='S'";    
        int updGt = db.execUpd("updateQ", updateQ, new ArrayList());    
        }
        if(!trfToStk.equals("") || !offer.equals("")){
        String trftostkproc="PUR_PKG.TRF_TO_STK(pPurDtlIdn => ?, pStkIdn => ?,  pMsg => ?)";
        for(int i=0;i< stockList.size();i++){
            HashMap stockPkt = (HashMap)stockList.get(i);
             String purdtlIdn = (String)stockPkt.get("stk_idn");
             String isChecked = util.nvl((String)purchaseForm.getValue(purdtlIdn));
            if(isChecked.equals("yes")){
                    if(!trfToStk.equals("")){
                        params = new ArrayList();
                        params.add(purdtlIdn);
                        if(!stk_stt.equals("")){
                        trftostkproc="PUR_PKG.TRF_TO_STK(pPurDtlIdn => ? ,pStt => ? , pStkIdn => ?,  pMsg => ?)";
                        params.add(stk_stt);
                        }
                        ArrayList out = new ArrayList();
                        out.add("I");
                        out.add("V");
                        CallableStatement cst = db.execCall("TrfToStk",trftostkproc, params ,out);
                        newVnm= cst.getString(params.size()+2); 
                        trfStkidn=trfStkidn+","+newVnm;
                      cst.close();
                      cst=null;
                    }
                    if(!offer.equals("")){
                        String prcVal = util.nvl((String)purchaseForm.getValue("nwprice_"+purdtlIdn),"0");
                        String prcDis = util.nvl((String)purchaseForm.getValue("nwdis_"+purdtlIdn),"0");
                        String updateOffQ="update pur_dtl set typ='OFR',flg='TRF',ofr_rte=?,ofr_dis=?,ofr_dte=sysdate where pur_dtl_idn=? ";
                        params = new ArrayList();
                        params.add(prcVal);
                        params.add(prcDis);
                        params.add(purdtlIdn);
                        int updG = db.execUpd("updateOffQ", updateOffQ, params);  
                    }  
               
             }
        }
            if(!offer.equals("")){
                
                params = new ArrayList();
                params.add(venderId);
                ArrayList out = new ArrayList();
                out.add("I");
                CallableStatement cst = db.execCall("offer","PUR_PKG.TRF_TO_OFFER(pVndrIdn => ?,  pPurIdn => ?)", params ,out);
                newpurIdn = cst.getInt(2); 
                
                req.setAttribute("offer", "New PurIdn :"+newpurIdn);
              cst.close();
              cst=null;
            }
        }
        if(!trfToBuy.equals("")){
            String updatepurDtl = "update pur_dtl a set a.typ='BUY',flg='TRF' where a.pur_idn= ? and  exists (select 1 from gt_srch_rslt b where a.pur_dtl_idn = b.stk_idn and b.flg = 'S')";
            params = new ArrayList();
            params.add(purIdn);
            int ct = db.execUpd("updatePurDtl", updatepurDtl, params);
            params = new ArrayList();
            params.add(venderId);
            ArrayList out = new ArrayList();
            out.add("I");
            CallableStatement cst = db.execCall("offer","PUR_PKG.TRF_TO_BUY(pVndrIdn => ?,  pPurIdn => ?)", params ,out);
            newpurIdn = cst.getInt(2); 
            req.setAttribute("offer", "New PurIdn :"+newpurIdn);
          cst.close();
          cst=null;
            
        }
        if(!cmptPri.equals("")){
            String insQ = " insert into gt_pkt(mstk_idn) select stk_idn from gt_srch_rslt where flg = ? ";
            params = new ArrayList();
            params.add("S");
            int ct = db.execUpd(" reprc memo", insQ, params);
            if(ct>0){
                String purPkg = "PUR_PKG.REPRC(lJob => ? , lSeq => ?) ";
                params = new ArrayList();
                params.add("2");
                ArrayList out = new  ArrayList();
                out.add("I");
                CallableStatement cts = db.execCall("reprc", purPkg, params, out);
                int lseq = cts.getInt(params.size()+1);
                req.setAttribute("msg", "process.."); 
            }else{
                req.setAttribute("msg", "Error in process..");
            }    
        }
        if(!trfStkidn.equals("")){
        trfStkidn=trfStkidn.replaceFirst(",", "");  
        req.setAttribute("trf", trfStkidn);
        }
            util.updAccessLog(req,res,"Purchase Action", "save end");
        return load(am,af,req,res);
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
            util.updAccessLog(req,res,"Purchase Action", "fetch start");
        PurchasePricingForm purchaseForm = (PurchasePricingForm) af;
        int delGt = db.execUpd("delete", "delete from gt_srch_rslt", new ArrayList());
        String vender = util.nvl((String)purchaseForm.getValue("vender"));
        String purIdn = util.nvl((String)purchaseForm.getValue("purIdn"));
        purchaseForm.reset();
           
            
       ArrayList params = new ArrayList();
        ArrayList puridLst = new ArrayList();
        params.add(purIdn);
        params.add("PUR_VW");
        int ct = db.execCall("POP_GT", "PUR_PKG.POP_GT(purIdn => ? , pMdl => ?)", params);
        ArrayList pktList = SearchResult(req,res,"Z","",af);
        req.setAttribute("pktList", pktList);
        HashMap totalMap = GetTotal(req);
        req.setAttribute("totalMap", totalMap);
        GenDAO gen=new GenDAO();
        gen.setIdn(purIdn);
        gen.setNmeIdn(purIdn);
        puridLst.add(gen);
        purchaseForm.setPurIdnList(puridLst);
        purchaseForm.setValue("vender", vender);
        purchaseForm.setValue("purIdn", purIdn);
            util.updAccessLog(req,res,"Purchase Action", "fetch end");
        return am.findForward("PurPri");
        }
    }
    public ActionForward reprc(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"Purchase Action", "reprc start");
      PurchasePricingForm  purchaseForm = (PurchasePricingForm) af;
      String insQ = " insert into gt_pkt(mstk_idn) select stk_idn from gt_srch_rslt where flg = ? ";
      ArrayList params = new ArrayList();
      params.add("S");
      int ct = db.execUpd(" reprc memo", insQ, params);
      if(ct>0){
          String purPkg = "DP_JOB('PUR_PKG.REPRC',sysdate,null) ";
          ct = db.execCall("reprc", purPkg, new ArrayList());
          if(ct>0)
          req.setAttribute("msg", "Pricing job initiated....");
          else
          req.setAttribute("msg", "Error in process.."); 
      }else{
          req.setAttribute("msg", "Error in process..");
      }
          util.updAccessLog(req,res,"Purchase Action", "reprc end");
      return fetch(am, af, req, res);
      }
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
            util.updAccessLog(req,res,"Purchase Action", "createXL start");
            HashMap dbinfo = info.getDmbsInfoLst();
            String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N");
            int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
        String mail=util.nvl(req.getParameter("mail"));
        String nmeIdn=util.nvl(req.getParameter("nmeIdn"));
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        ArrayList pktList = SearchResult(req,res,"S","",form);
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
            int pktListsz=pktList.size();
            String fileNm = "PurchaseAction"+util.getToDteTime()+".xls";
        if(mail.equals("")){
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
            String contentTypezip = "application/zip";
            String fileNmzip = "PurchaseAction"+util.getToDteTime();
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
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        hwb.write(out);
        out.flush();
        out.close();
        }
        return null;
        }else{
            SearchQuery query=new SearchQuery();
            query.MailExcelmass(hwb, fileNm, req,res);  
            ArrayList emailids=util.byrAllEmail(nmeIdn);
            req.setAttribute("ByrEmailIds",emailids);
            return am.findForward("mail");
        }
        }
    }
    public ArrayList SearchResult(HttpServletRequest req,HttpServletResponse res,String flg,String typ,ActionForm af){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        PurchasePricingForm purchaseForm = (PurchasePricingForm) af;
        GenericInterface genericInt = new GenericImpl();
        ArrayList pktList = new ArrayList();
        ArrayList vwPrpLst =         genericInt.genericPrprVw(req, res, "PurViewLst", "PUR_VW");
        String cpPrp = "quot";
        if(vwPrpLst.contains("CP"))
        cpPrp =  util.prpsrtcolumn("srt", vwPrpLst.indexOf("CP")+1);
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte,rap_rte, stt , qty ,to_char(trunc(cts,2),'999990.99') cts , cmp , quot , rmk ,  decode(rap_rte, 1, '', trunc(((cmp/greatest(rap_rte,1))*100)-100,2)) cmpdis, decode(rap_rte, 1, '', trunc(((fquot/greatest(rap_rte,1))*100)-100,2)) dis , fquot,kts_vw kts,cmnt,Trunc((quot*Cts)*nvl(exh_rte,1),2) inrvlu,Trunc(quot*Cts,2) vlu, "+
            "nvl(nvl("+cpPrp+",prte),0)*nvl(cts,0) cptotal ,trunc(((greatest(nvl("+cpPrp+",prte),1)*100)/greatest(rap_rte,1)) - 100,2) cpdis ";
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
        ArrayList ary = new ArrayList();
        ary.add(flg);
        System.out.println("rsltQ"+rsltQ);   
      ArrayList rsList = db.execSqlLst("search Result", rsltQ, ary);
      PreparedStatement pst = (PreparedStatement)rsList.get(0);
      ResultSet rs = (ResultSet)rsList.get(1);
        try {
            while(rs.next()) {

                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                long   pktIdn = rs.getLong("stk_idn");
                pktPrpMap.put("vnm",vnm);
                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                    pktPrpMap.put("cmp",util.nvl(rs.getString("cmp")));
                    pktPrpMap.put("quot",util.nvl(rs.getString("quot")));
                    pktPrpMap.put("cmpDis",util.nvl(rs.getString("cmpdis")));
                    pktPrpMap.put("dis",util.nvl(rs.getString("dis")));
                    pktPrpMap.put("vlu",util.nvl(rs.getString("vlu")));
                    pktPrpMap.put("cpVlu", util.nvl(rs.getString("cptotal")));
                    pktPrpMap.put("cpDis", util.nvl(rs.getString("cpdis")));
                    pktPrpMap.put("inrvlu",util.nvl(rs.getString("inrvlu")));
                    if(typ.equals("O") || typ.equals("R")){
                    purchaseForm.setValue("nwprice_"+pktIdn, util.nvl(rs.getString("fquot")));
                    purchaseForm.setValue("nwdis_"+pktIdn, util.nvl(rs.getString("dis")));
                    }
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                             if (prp.toUpperCase().equals("CRTWT"))
                                 val = util.nvl(rs.getString("cts"));
                             if (prp.toUpperCase().equals("RAP_DIS"))
                                 val = util.nvl(rs.getString("dis"));
                             if (prp.toUpperCase().equals("CMP_DIS")){
                                 if(val.equals(""))
                                 val = util.nvl(rs.getString("cmpdis")); 
                             }
                             if (prp.toUpperCase().equals("RAP_RTE"))
                                 val = util.nvl(rs.getString("rap_rte"));
                             if (prp.toUpperCase().equals("CMP_RTE"))
                                 val = util.nvl(rs.getString("cmp"));
                             if(prp.equals("RTE"))
                                 val = util.nvl(rs.getString("quot"));
                             if(prp.equals("KTSVIEW"))
                                 val = util.nvl(rs.getString("kts"));
                             if(prp.equals("COMMENT"))
                                 val = util.nvl(rs.getString("cmnt"));
                             if(prp.equals("CP_DIS"))
                                 val = util.nvl(rs.getString("cpdis"));
                             
                                 pktPrpMap.put(prp, val);
                        
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
       
        return pktList;
    }
    public void FecthResult(HttpServletRequest req,HttpServletResponse res, HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
       ArrayList pktList  = new ArrayList();
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ArrayList ary = new ArrayList();
        String typ = (String)params.get("stt");
        String refno = (String)params.get("refno");
        String venderId = (String)params.get("rlnId");
        String purIdn = (String)params.get("purId");
        String mdl = (String)params.get("mdl");
        String srchRefQ = "Insert into gt_srch_rslt (rln_idn, pkt_ty, stk_idn, vnm, qty, cts, pkt_dte, stt, dsp_stt, cmp, rap_rte, cert_lab, cert_no, flg, sk1, quot, rap_dis,fquot,exh_rte) \n" + 
        "          select ?, 'NR', a.pur_dtl_idn, a.ref_idn, 1, a.cts, a1.dte\n" + 
        "            , a1.typ stt, decode(a1.typ, 'R', 'Review', 'O', 'Offer', 'B', 'Buy', a1.typ)\n" + 
        "            , a.cmp, a.rap, 'NA', null, 'Z' flg, a.pur_dtl_idn, a.rte, a.dis,nvl(a.ofr_rte, a.rte),nvl(a1.exh_rte,get_xrt(a1.cur))\n" + 
        "          From Mpur A1, Pur_Dtl A Where A.Pur_Idn = A1.Pur_Idn  and nvl(a1.flg2, 'NA') <> 'DEL' and nvl(a.flg2, 'NA') <> 'DEL'\n" + 
        "            And A1.Vndr_Idn =?\n" + 
        "           And A1.Pur_Idn  = ?\n" + 
        "           And A1.typ  = ?";
        
        refno = util.getVnm(refno);
        srchRefQ = srchRefQ+" and A.ref_idn in ("+refno+") " ;
        ary = new ArrayList();
        ary.add(venderId);
        ary.add(venderId);
        ary.add(purIdn);
        ary.add(typ);
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "PUR_PKG.POP_PKT_PRP(?)";
        ary = new ArrayList();
        ary.add(mdl);
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
    }
    public void insertGtmemo(HttpServletRequest req,HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String delQ = " Delete from gt_memo_pri_chng ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        String srchRefQ = "Insert Into Gt_Memo_Pri_Chng(Memo_Id, Exh_Rte, Mstk_Idn, Cts, Rap_Rte, Rte, Quot) " + 
        "        Select 0, Nvl(A.Exh_Rte, 1), A.Stk_Idn, Trunc(A.Cts, 2) Cts, A.Rap_Rte,A.Cmp,A.FQuot " + 
        "        from gt_srch_rslt A";
        

        ct = db.execUpd(" Insert gt_memo_pri_chng", srchRefQ, new ArrayList());
    }
    public HashMap GetTotal(HttpServletRequest req){
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
      ArrayList rsList = db.execSqlLst("getTotal", gtTotal, ary);
      PreparedStatement pst = (PreparedStatement)rsList.get(0);
     ResultSet rs = (ResultSet)rsList.get(1);
        try {
            if (rs.next()) {
             gtTotalMap.put("qty", util.nvl(rs.getString("qty")));
             gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return gtTotalMap ;
    }
    
    public ArrayList getvndridn(HttpServletRequest req){
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
        ArrayList venderList = new ArrayList();
        String venderQ = "select distinct vndr_idn,vndr from PUR_PNDG_V order by vndr";
      ArrayList rsList = db.execSqlLst("empSql", venderQ, ary);
      PreparedStatement pst = (PreparedStatement)rsList.get(0);
      ResultSet rs = (ResultSet)rsList.get(1);
     try {
            while (rs.next()) {
                ByrDao byr = new ByrDao();

                byr.setByrIdn(rs.getInt("vndr_idn"));
                byr.setByrNme(rs.getString("vndr"));
                venderList.add(byr);
            }
            rs.close();
         pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return venderList;
    }
    
    public ActionForward loadtyp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      int fav = 0;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      StringBuffer sb = new StringBuffer();
      String nmeId = util.nvl(req.getParameter("VenderIdn"));
      String favSrch = "select distinct decode(typ, 'R', 'Review', 'O', 'Offer', 'B', 'Buy', 'E', 'External',typ) dsc, typ from PUR_PNDG_V where vndr_idn='"+nmeId+"'";
      ArrayList rsList = db.execSqlLst("favSrch",favSrch, new ArrayList());
      PreparedStatement pst = (PreparedStatement)rsList.get(0);
      ResultSet rs = (ResultSet)rsList.get(1);
      while(rs.next()){
          fav=1;
          sb.append("<type>");
         sb.append("<nme>" + util.nvl(rs.getString("dsc")) +"</nme>");
         sb.append("<nmeid>" + util.nvl(rs.getString("typ")) +"</nmeid>");
          sb.append("</type>");
     }
        rs.close();
        pst.close();
       if(fav==1)
        res.getWriter().write("<types>" +sb.toString()+ "</types>");
        return null;
    }
    
    public ActionForward loadtyppurId(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
      int fav = 0;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      StringBuffer sb = new StringBuffer();
      String typ = util.nvl(req.getParameter("typ"));
      String nmeId = util.nvl(req.getParameter("VenderIdn"));
      ArrayList ary=new ArrayList();
      if(!typ.equals("0")){
        ary.add(nmeId);
        ary.add(typ);
        String favSrch = "select pur_idn, pur_idn||' '||nvl(flg,'') dsc from pur_pndg_idn_v where vndr_idn=? and typ=? order by pur_idn";
        ArrayList rsList = db.execSqlLst("favSrch",favSrch,ary);
        PreparedStatement pst = (PreparedStatement)rsList.get(0);
        ResultSet rs = (ResultSet)rsList.get(1);
        while(rs.next()){
            fav=1;
            sb.append("<vender>");
            sb.append("<nme>" + util.nvl(rs.getString("dsc")) +"</nme>");
            sb.append("<nmeid>" + util.nvl(rs.getString("pur_idn")) +"</nmeid>");
            sb.append("</vender>");
        }
        rs.close();
        pst.close();
      }
       if(fav==1)
        res.getWriter().write("<venders>" +sb.toString()+ "</venders>");
        return null;
    }
    
    
    public ActionForward similar(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
        HashMap dbinfo = info.getDmbsInfoLst();
        String days = util.nvl((String)dbinfo.get("PUR_HIS_AVG_DYS"),"30");
       String stkIdn = util.nvl(req.getParameter("stkIdn"));
        GenericInterface genericInt = new GenericImpl();
        ArrayList ANLSVWList=genericInt.genericPrprVw(req, res, "PUR_RTE_HIS_VW","PUR_RTE_HIS_VW");

       String sql = "    select b.cts,b.rte,b.dis,b.ref_idn,b.rap,b.pur_idn\n" + 
       "    from\n" + 
       "    (  \n" + 
       "    select count(*),a.pur_dtl_idn    \n" + 
       "    from\n" + 
       "    mpur mp,pur_dtl a,pur_prp b,(\n" + 
       "    select a.pur_dtl_idn,b.mprp,b.val,b.num,b.dte,b.txt,d.dta_typ\n" + 
       "    from pur_dtl a,pur_prp b,rep_prp c,mprp d\n" + 
       "    where \n" + 
       "    a.pur_dtl_idn=b.pur_dtl_idn and b.mprp=c.prp and c.prp=d.prp\n" + 
       "    and c.mdl='PUR_RTE_HIS_VW' and c.flg='Y' and a.pur_dtl_idn=?) c\n" + 
       "    where\n" + 
       "    mp.pur_idn=a.pur_idn and a.pur_dtl_idn=b.pur_dtl_idn and a.pur_dtl_idn <> c.pur_dtl_idn and b.mprp=c.mprp\n" + 
       "    and decode(c.dta_typ, 'T', c.txt, 'D', c.dte, 'N', c.num,c.val) = decode(c.dta_typ, 'T', b.txt, 'D', b.dte, 'N', b.num,b.val)\n" + 
       "    and trunc(mp.pur_dte)>=trunc(sysdate)-?\n" + 
       "    group by a.pur_dtl_idn    \n" + 
       "    having count(*) =?\n" + 
       "    ) a, pur_dtl b\n" + 
       "    where a.pur_dtl_idn = b.pur_dtl_idn\n" + 
       "    order by 1";
       ArrayList ary = new ArrayList();
       ary.add(stkIdn);
       ary.add(days);
       ary.add(String.valueOf(ANLSVWList.size()));
      ArrayList outLst = db.execSqlLst("memo pkt", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
       while(rs.next()){
       sb.append("<purchase>");
       sb.append("<pur_idn>"+util.nvl(rs.getString("pur_idn"),"0") +"</pur_idn>");
       sb.append("<ref_idn>"+util.nvl(rs.getString("ref_idn"),"0") +"</ref_idn>");
       sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
       sb.append("<rap>"+util.nvl(rs.getString("rap"),"0") +"</rap>");
       sb.append("<rte>"+util.nvl(rs.getString("rte"),"0") +"</rte>");
       sb.append("<dis>"+util.nvl(rs.getString("dis"),"0") +"</dis>");
       sb.append("</purchase>");
       }
       rs.close();
       pst.close();
    res.setContentType("text/xml");
    res.setHeader("Cache-Control", "no-cache");
    res.getWriter().write("<purchases>"+sb.toString()+"</purchases>");
    return null;
    }
//    public ArrayList ASGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("purGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp, flg  from rep_prp where mdl = 'PURACT_SRCH' and flg in ('M','Y','S') order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("purGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
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
                util.updAccessLog(req,res,"Repairing Return", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Purchase Action", "init");
            }
            }
            return rtnPg;
            }
}

