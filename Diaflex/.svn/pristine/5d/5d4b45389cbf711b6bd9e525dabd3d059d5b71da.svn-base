package ft.com.pri;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.PdfforReport;
import ft.com.assort.AssortIssueImpl;
import ft.com.assort.AssortIssueInterface;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;
import ft.com.dao.ObjBean;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.PacketPrintForm;

import ft.com.marketing.SearchQuery;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PriceDtlMatrixAction extends DispatchAction {
   
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
           util.updAccessLog(req,res,"PriceDtlMatrix", "load");
         PriceDtlMatrixForm udf = (PriceDtlMatrixForm)af;
           GenericInterface genericInt = new GenericImpl();
         udf.resetALL();
         ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_PRICEMatrixGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_PRICEMatrixGNCSrch");
         info.setGncPrpLst(assortSrchList);
             HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
             HashMap pageDtl=(HashMap)allPageDtl.get("PRICE_DETAIL_MATRIX");
             if(pageDtl==null || pageDtl.size()==0){
             pageDtl=new HashMap();
             pageDtl=util.pagedef("PRICE_DETAIL_MATRIX");
             allPageDtl.put("PRICE_DETAIL_MATRIX",pageDtl);
             }
             info.setPageDetails(allPageDtl);
           util.updAccessLog(req,res,"PriceDtlMatrix", "end");
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
           util.updAccessLog(req,res,"PriceDtlMatrix", "fetch");
         PriceDtlMatrixForm udf = (PriceDtlMatrixForm)af;
           GenericInterface genericInt = new GenericImpl();
         HashMap prp = info.getPrp();
         HashMap mprp = info.getMprp();
         HashMap dbinfo = info.getDmbsInfoLst();
         String shval = (String)dbinfo.get("SHAPE");
         String szval = (String)dbinfo.get("SIZE");
         String mixszval = "MIX_SIZE";
             ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_PRICEMatrixGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_PRICEMatrixGNCSrch");
         info.setGncPrpLst(genricSrchLst);
         String priceondte = util.nvl((String)udf.getValue("priceondte"));
         String compondte = util.nvl((String)udf.getValue("compondte"));
         String mfg = util.nvl((String)udf.getValue("mfg"));
         String assort = util.nvl((String)udf.getValue("assort"));
         String mfgdiff = util.nvl((String)udf.getValue("mfgdiff"));
         String assortdiff = util.nvl((String)udf.getValue("assortdiff"));
         String chkcmp = util.nvl((String)udf.getValue("chkcmp"));
         String chkmixcmp = util.nvl((String)udf.getValue("chkmixcmp"));
         String chkmfgrte = util.nvl((String)udf.getValue("chkmfgrte"));
         ArrayList shapeLst=new ArrayList();
         ArrayList szLst=new ArrayList();
         ArrayList mixszLst=new ArrayList();
         HashMap dataDtl=new HashMap();
         ArrayList shSzList = new ArrayList();
         ArrayList cheadList = new ArrayList();
         ArrayList rheadList = new ArrayList();
         String title="";
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
//                 if(!reqVal1.equals("")){
//                 paramsMap.put(lprp + "_" + lVal, reqVal1);
//                 }
                     if(lprp.equals(shval) && !reqVal1.equals("")){
                         shapeLst.add(lSrt);    
                     }
                     if(lprp.equals(szval) && !reqVal1.equals("") && assort.equals("") && assortdiff.equals("")){
                         szLst.add(lSrt);    
                     }
                     if(lprp.equals(mixszval) && !reqVal1.equals("") && mfg.equals("") && mfgdiff.equals("")){
                         mixszLst.add(lSrt);    
                     }
                 }
             }else{
                 String fldVal1 = util.nvl((String)udf.getValue(lprp+"_1"));
                String fldVal2 = util.nvl((String)udf.getValue(lprp+"_2"));
                if(fldVal2.equals(""))
                    fldVal2=fldVal1; 
             }            
         }
         String sh="",sz="",mixsz="",conQ="";
         if(shapeLst.size() > 0) {    
         sh=shapeLst.toString();
         sh=sh.replaceAll("\\[","\\(");
         sh=sh.replaceAll("\\]","\\)").replaceAll(" ", "");
         }
         if(szLst.size() > 0){
         sz=szLst.toString();
         sz=sz.replaceAll("\\[","\\(");
         sz=sz.replaceAll("\\]","\\)").replaceAll(" ", "");
         }
         if(mixszLst.size() > 0){
         mixsz=mixszLst.toString();
         mixsz=mixsz.replaceAll("\\[","\\(");
         mixsz=mixsz.replaceAll("\\]","\\)").replaceAll(" ", "");
         }
         if(!sh.equals("")){
             conQ=" and sh_so in "+sh;
         }if(!sz.equals("")){
             conQ=conQ+" and sz_so in "+sz; 
         }if(!mixsz.equals("")){
             conQ=conQ+" and c.srt in "+mixsz; 
         }
         if(!assort.equals("") || !mfg.equals("")){
         if (!priceondte.equals("")){
         conQ=conQ+" And trunc(to_date('"+priceondte+"','dd-mm-rrrr')) Between B.Frm_Dte And Nvl(B.To_Dte, Sysdate) ";  
         }else{
         conQ=conQ+" and B.Stt='A' ";
         }
         if(assort.equals("")){
         String SrchQ="Select A.Shape shape,A.Sz sz,A.Col col,A.Clr clr,B.Mfg_Dis mfg_dis From Itm_Bse A,Itm_Bse_Pri B "+ 
                      " where a.idn=b.itm_bse_idn and B.Mfg_Dis is not null "+conQ+"  order by b.idn";
           ArrayList rsList = db.execSqlLst("Price Matrix", SrchQ, new ArrayList());
           PreparedStatement pst=(PreparedStatement)rsList.get(0);
           ResultSet rs = (ResultSet)rsList.get(1);
         while (rs.next()) { 
             String shape=util.nvl(rs.getString("shape"));
             String size=util.nvl(rs.getString("sz"));
             String col=util.nvl(rs.getString("col"));
             String clr=util.nvl(rs.getString("clr"));
             String key=shape+"_"+size;             
             if(!shSzList.contains(key))
                 shSzList.add(key);
             dataDtl.put(key+"_"+col+"_"+clr,util.nvl(rs.getString("mfg_dis"))); 
         }
         rs.close();
         pst.close();
         String colPrp="select distinct col,col_so from Itm_Bse A,Itm_Bse_Pri B where a.idn=b.itm_bse_idn and B.Mfg_Dis is not null "+conQ+" order by a.col_so";
           rsList = db.execSqlLst("COlor Prp", colPrp, new ArrayList());
          pst=(PreparedStatement)rsList.get(0);
           rs = (ResultSet)rsList.get(1);
         while (rs.next()) {
             rheadList.add(util.nvl(rs.getString("col")));   
         }
         rs.close();
         pst.close();
         String clrPrp="select distinct clr,clr_so from Itm_Bse A,Itm_Bse_Pri B where  a.idn=b.itm_bse_idn and B.Mfg_Dis is not null "+conQ+"  order by a.clr_so";
           rsList = db.execSqlLst("Clr Prp", clrPrp, new ArrayList());
           pst=(PreparedStatement)rsList.get(0);
           rs = (ResultSet)rsList.get(1);
         while (rs.next()) { 
             cheadList.add(util.nvl(rs.getString("clr")));
         }
         rs.close();
         pst.close();
         title="Color/Clarity";
         req.setAttribute("recal", "Y");
         
         if(!chkcmp.equals("") || !chkmixcmp.equals("") || !chkmfgrte.equals("")){
             ArrayList gridDtl=new ArrayList();
             SrchQ="Select A.Shape shape,A.Sz sz,A.Col col,A.Clr clr,B.Mfg_Dis mfg_dis,decode(b.cmp, 1, '',null,'',0,'', trunc(((b.cmp*100)/greatest(b.rap_rte,1)) - 100,2)) cmp_dis,\n" + 
             "decode(b.mix_cmp, 1, '',null,'',0,'', trunc(((b.mix_cmp*100)/greatest(b.rap_rte,1)) - 100,2)) mixcmp_dis,b.mfg_rte From Itm_Bse A,Itm_Bse_Pri B "+ 
                          " where a.idn=b.itm_bse_idn and B.Mfg_Dis is not null "+conQ+"  order by b.idn";
           rsList = db.execSqlLst("Price Matrix", SrchQ, new ArrayList());
           pst=(PreparedStatement)rsList.get(0);
           rs = (ResultSet)rsList.get(1);
             while (rs.next()) { 
                 String shape=util.nvl(rs.getString("shape"));
                 String size=util.nvl(rs.getString("sz"));
                 String col=util.nvl(rs.getString("col"));
                 String clr=util.nvl(rs.getString("clr"));
                 String key=shape+"_"+size;             
                 dataDtl.put(key+"_"+col+"_"+clr+"_CMP",util.nvl(rs.getString("cmp_dis"))); 
                 dataDtl.put(key+"_"+col+"_"+clr+"_MIXCMP",util.nvl(rs.getString("mixcmp_dis"))); 
                 dataDtl.put(key+"_"+col+"_"+clr+"_MFGRTE",util.nvl(rs.getString("mfg_rte"))); 
             }
             rs.close();
             pst.close();
             if(!chkcmp.equals(""))
                 gridDtl.add("CMP");   
             if(!chkmixcmp.equals(""))
                 gridDtl.add("MIXCMP"); 
             if(!chkmfgrte.equals(""))
                 gridDtl.add("MFGRTE"); 
             req.setAttribute("gridDtl", gridDtl);
         }
         }else{
         String SrchQ="Select A.Shape shape,a.mix_size sz,a.mix_size mixsize,a.mix_clr mixclr,B.mix_asrt_cmp mfg_dis From Itm_Bse A,Itm_Bse_Pri B,Prp C "+ 
         " where a.idn=b.itm_bse_idn and B.mix_asrt_cmp is not null "+conQ+" And C.Mprp='MIX_SIZE' And A.Mix_Size=C.Val order by b.idn";   
          ArrayList rsList = db.execSqlLst("Price Matrix", SrchQ, new ArrayList());
          PreparedStatement pst=(PreparedStatement)rsList.get(0);
          ResultSet rs = (ResultSet)rsList.get(1);
         while (rs.next()) { 
         String shape=util.nvl(rs.getString("shape"));
         String mixsize=util.nvl(rs.getString("mixsize"));
         String mixclr=util.nvl(rs.getString("mixclr"));
         String key=shape;            
         if(!shSzList.contains(key))
         shSzList.add(key);
         dataDtl.put(key+"_"+mixclr+"_"+mixsize,util.nvl(rs.getString("mfg_dis"))); 
         }
         rs.close();
         pst.close();
            
         String colPrp="select distinct A.Mix_size mixsize,c.srt from Itm_Bse A,Itm_Bse_Pri B,Prp C where a.idn=b.itm_bse_idn and B.mix_asrt_cmp is not null "+conQ+" And C.Mprp='MIX_SIZE' " + 
         " And A.Mix_size=C.Val order by c.srt"; 
          rsList = db.execSqlLst("MIXSIXE Prp", colPrp, new ArrayList());
           pst=(PreparedStatement)rsList.get(0);
           rs = (ResultSet)rsList.get(1);
         while (rs.next()) {
         cheadList.add(util.nvl(rs.getString("mixsize")));   
         }
         rs.close();
         pst.close();
         String clrPrp="Select Distinct A.Mix_Clr mixclr, d.srt from Itm_Bse A,Itm_Bse_Pri B,Prp C, prp d where  a.idn=b.itm_bse_idn and B.mix_asrt_cmp is not null "+conQ+" And C.Mprp='MIX_SIZE' And A.Mix_size=C.Val "+
         "And D.Mprp='MIX_CLARITY' And A.Mix_Clr = D.Val order by d.srt";
           rsList = db.execSqlLst("MIXCLARITY Prp", clrPrp, new ArrayList());
            pst=(PreparedStatement)rsList.get(0);
            rs = (ResultSet)rsList.get(1);
         while (rs.next()) { 
         rheadList.add(util.nvl(rs.getString("mixclr")));
         }
         rs.close();
          pst.close();
         title="Mix Clarity/Mix Size";
         }

         req.setAttribute("view", "Y");
         session.setAttribute("rheadList", rheadList);
         }else{
             if(assortdiff.equals("")){
             String SrchQ="Select A.Shape shape,A.Sz sz,A.Col col,A.Clr clr,B.Mfg_Dis mfg_dis From Itm_Bse A,Itm_Bse_Pri B "+ 
                          " where a.idn=b.itm_bse_idn and B.Mfg_Dis is not null "+conQ+"  order by b.idn";
             ArrayList  rsList = db.execSqlLst("Price Matrix", SrchQ, new ArrayList());
              PreparedStatement pst=(PreparedStatement)rsList.get(0);
              ResultSet  rs = (ResultSet)rsList.get(1);
             while (rs.next()) { 
                 String shape=util.nvl(rs.getString("shape"));
                 String size=util.nvl(rs.getString("sz"));
                 String col=util.nvl(rs.getString("col"));
                 String clr=util.nvl(rs.getString("clr"));
                 String key=shape+"_"+size;             
                 if(!shSzList.contains(key))
                     shSzList.add(key);
                 dataDtl.put(key+"_"+col+"_"+clr,util.nvl(rs.getString("mfg_dis"))); 
             }
             rs.close();
             pst.close();
             SrchQ="Select A.Shape shape,A.Sz sz,A.Col col,A.Clr clr,B.Mfg_Dis mfg_dis From Itm_Bse A,Itm_Bse_Pri B "+ 
             " where a.idn=b.itm_bse_idn and B.Mfg_Dis is not null "+conQ+"  order by b.idn";
             rsList = db.execSqlLst("Price Matrix", SrchQ, new ArrayList());
             pst=(PreparedStatement)rsList.get(0);
              rs = (ResultSet)rsList.get(1);
             while (rs.next()) { 
             String shape=util.nvl(rs.getString("shape"));
             String size=util.nvl(rs.getString("sz"));
             String col=util.nvl(rs.getString("col"));
             String clr=util.nvl(rs.getString("clr"));
             String key=shape+"_"+size;             
             if(!shSzList.contains(key))
             shSzList.add(key);
             dataDtl.put(key+"_"+col+"_"+clr,util.nvl(rs.getString("mfg_dis"))); 
             }
             rs.close();
             pst.close();
             title="Color/Clarity";
             }else{
             String SrchQ="Select A.Shape shape,a.mix_size mixsize,a.mix_clr mixclr,Nvl(B.Mix_Asrt_Cmp, 0) mfg_dis From Itm_Bse A,Itm_Bse_Pri B,Prp C "+ 
             " where a.idn=b.itm_bse_idn "+conQ+"  and C.Mprp='MIX_SIZE' And A.Mix_Size=C.Val And trunc(to_date('"+priceondte+"','dd-mm-rrrr')) Between B.Frm_Dte And Nvl(B.To_Dte, Sysdate) And a.mix_clr Is Not Null order by b.idn";   
              ArrayList rsList = db.execSqlLst("Price On Matrix", SrchQ, new ArrayList());
              PreparedStatement pst=(PreparedStatement)rsList.get(0);
              ResultSet  rs = (ResultSet)rsList.get(1);
             while (rs.next()) { 
             String shape=util.nvl(rs.getString("shape"));
             String mixsize=util.nvl(rs.getString("mixsize"));
             String mixclr=util.nvl(rs.getString("mixclr"));
             String key=shape;            
             if(!shSzList.contains(key))
             shSzList.add(key);
             dataDtl.put(key+"_"+mixclr+"_"+mixsize+"_PRON",util.nvl(rs.getString("mfg_dis"))); 
             }
             rs.close();
             pst.close();
             SrchQ="Select A.Shape shape,a.mix_size mixsize,a.mix_clr mixclr,Nvl(B.Mix_Asrt_Cmp, 0) mfg_dis From Itm_Bse A,Itm_Bse_Pri B,Prp C "+ 
             " where a.idn=b.itm_bse_idn "+conQ+" and C.Mprp='MIX_SIZE' And A.Mix_Size=C.Val And trunc(to_date('"+compondte+"','dd-mm-rrrr')) Between B.Frm_Dte And Nvl(B.To_Dte, Sysdate) And a.mix_clr Is Not Null";   
              rsList = db.execSqlLst("Compare Matrix", SrchQ, new ArrayList());
               pst=(PreparedStatement)rsList.get(0);
                rs = (ResultSet)rsList.get(1);
             while (rs.next()) { 
             String shape=util.nvl(rs.getString("shape"));
             String mixsize=util.nvl(rs.getString("mixsize"));
             String mixclr=util.nvl(rs.getString("mixclr"));
             String key=shape;            
             if(!shSzList.contains(key))
             shSzList.add(key);
             dataDtl.put(key+"_"+mixclr+"_"+mixsize+"_COMP",util.nvl(rs.getString("mfg_dis"))); 
             }
             rs.close(); 
              pst.close();
             String colPrp="select distinct A.Mix_size mixsize,c.srt from Itm_Bse A,Itm_Bse_Pri B,Prp C where a.idn=b.itm_bse_idn and B.mix_asrt_cmp is not null "+conQ+" And C.Mprp='MIX_SIZE' " + 
             " And A.Mix_size=C.Val order by c.srt"; 
               rsList = db.execSqlLst("MIXSIXE Prp", colPrp, new ArrayList());
                pst=(PreparedStatement)rsList.get(0);
                rs = (ResultSet)rsList.get(1);
             while (rs.next()) {
             cheadList.add(util.nvl(rs.getString("mixsize")));   
             }
             rs.close();
              pst.close();
             title="Mix Clarity/Mix Size";
             req.setAttribute("priceondte", priceondte);
             req.setAttribute("compondte", compondte);    
             }
         req.setAttribute("view", "N");
         }
         dataDtl.put("title", title);
         session.setAttribute("shSzList", shSzList);
         session.setAttribute("dataDtl", dataDtl);
         session.setAttribute("cheadList", cheadList);
           util.updAccessLog(req,res,"PriceDtlMatrix", "fetch end");
        return am.findForward("load");
         }
     }
    
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req,
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
          util.updAccessLog(req,res,"PriceDtlMatrix", "save");
        PriceDtlMatrixForm udf = (PriceDtlMatrixForm)af;
        ArrayList shSzList = (ArrayList)session.getAttribute("shSzList");
        String loopindex = util.nvl(req.getParameter("loopindex"));
        String key="";
        ArrayList ary = new ArrayList();
        for(int l=0;l<shSzList.size();l++){
        if(loopindex.equals(""))
        key=(String)shSzList.get(l);
        else{
        key=(String)shSzList.get(Integer.parseInt(loopindex));
        l=shSzList.size()+1;
        }
        ary = new ArrayList();
        String[] keyList = key.split("_");
        ary.add("CRT");   
        ary.add(keyList[0].trim());
        ary.add(keyList[1].trim());  
        String popitm = "pop_itm_bse_pri(pTyp => ?, pShp => ?, pSz => ?)";
        int ct = db.execCall("pop_itm_bse_pri",popitm,ary);    
        }
          util.updAccessLog(req,res,"PriceDtlMatrix", "fetch end");
     return am.findForward("load");
        }
    }
    public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"PriceDtlMatrix", "create XL");
        String CONTENT_TYPE = "getServletContext()/vnd-excel";  
        String loopindex = util.nvl(req.getParameter("loopindex"));
        String fileNm = "PriceMatrix"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        HSSFWorkbook hwb = null;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        ArrayList shSzList = (ArrayList)session.getAttribute("shSzList");
        ArrayList cheadList = (ArrayList)session.getAttribute("cheadList");
        ArrayList rheadList = (ArrayList)session.getAttribute("rheadList");
        HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
        hwb = xlUtil.getMatrixXl(loopindex,rheadList,cheadList,dataDtl,shSzList);
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
        return null;
        }
    }
//    public ArrayList priceGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("PRICEMatrixGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp,flg  from rep_prp where mdl = 'PRICE_SRCH' and flg in ('Y','S','M') order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("PRICEMatrixGNCSrch", asViewPrp);
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
          util.updAccessLog(req,res,"Price Dtl Martix", "init");
          }
          }
          return rtnPg;
          }

    
    public PriceDtlMatrixAction() {
        super();
    }
}
