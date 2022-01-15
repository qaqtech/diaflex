package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;
import ft.com.assort.AssortReturnAction;
import ft.com.assort.AssortReturnForm;
import ft.com.assort.AssortReturnImpl;
import ft.com.assort.AssortReturnInterface;
import ft.com.dao.GtPktDtl;
import ft.com.dao.Mprc;

import ft.com.marketing.SearchQuery;

import java.io.OutputStream;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MixAssortEditReturnAction extends DispatchAction {
    public MixAssortEditReturnAction() {
        super();
    }
    
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
       AssortReturnForm assortForm = (AssortReturnForm)form;
        AssortReturnInterface assortInt = new AssortReturnImpl();
          util.updAccessLog(req,res,"Assort Return", "load");
        assortForm.reset();
        String grp = util.nvl(req.getParameter("grp"));
        ArrayList mprcList = assortInt.getPrc(req,res);
        assortForm.setMprcList(mprcList);
        
        ArrayList empList = assortInt.getEmp(req,res);
        assortForm.setEmpList(empList);
        assortForm.setValue("grpList",grp);
        //assortInt.isRepairIS(req, res);Mayur
          
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("ASSORT_RETURN");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("ASSORT_RETURN");
        allPageDtl.put("ASSORT_RETURN",pageDtl);
        }
        info.setPageDetails(allPageDtl);
       
        util.updAccessLog(req,res,"Mix Assort Return", "End");
      return am.findForward("load");
        }
    }
    public ActionForward fecth(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
       AssortReturnForm assortForm = (AssortReturnForm)form;
        util.updAccessLog(req,res,"Assort Return", "fecth");
        gtMgr.reset();
        String mprcIdn = util.nvl((String)assortForm.getValue("mprcIdn"));
        String stoneId = util.nvl((String)assortForm.getValue("vnmLst"));
        String empId = util.nvl((String)assortForm.getValue("empIdn"));
        String issueId = util.nvl((String)assortForm.getValue("issueId"));
        HashMap prcBeansList = (HashMap)session.getAttribute("mprcBean");
        String grpList = util.nvl((String)assortForm.getValue("grpList"));
            String lotNo = util.nvl((String)assortForm.getValue("lotNo"));
            String AtrlotNo = util.nvl((String)assortForm.getValue("AtrlotNo"));
          String ctInwardNo = util.nvl((String)assortForm.getValue("ctInwardNo"));
        String prcStt = "";
        String pri_yn = "";
        HashMap stockList=new HashMap();
        session.setAttribute("AssortStockList", new ArrayList());
        if(!issueId.equals("") && mprcIdn.equals("0")){
            util.updAccessLog(req,res,"Assort Return", "Issue Id : "+issueId);
            ArrayList ary = new ArrayList();
            String issuStt = " select b.is_stt , b.idn,b.pri_yn from iss_rtn a , mprc b where a.prc_id = b.idn and a.iss_id= ? and b.grp in ("+grpList+") ";
            ary.add(issueId);
            
          ArrayList  rsLst = db.execSqlLst("issueStt", issuStt, ary);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
            while(rs.next()){
              prcStt = util.nvl(rs.getString("is_stt"));
              mprcIdn = util.nvl(rs.getString("idn"));
              pri_yn = util.nvl(rs.getString("pri_yn"));
            }
            rs.close();
            stmt.close();
        }else{
            util.updAccessLog(req,res,"Assort Return", "process Id : "+mprcIdn);
         Mprc mprcDto = (Mprc)prcBeansList.get(mprcIdn);
         prcStt = mprcDto.getIs_stt();
         pri_yn= mprcDto.getPri_yn();
        }
        if(!prcStt.equals("")){
        HashMap params = new HashMap();
        params.put("stt", prcStt);
        params.put("vnm",stoneId);
        params.put("empIdn", empId);
        params.put("issueId", issueId);
        params.put("grpList", grpList);
            params.put("lotNo", lotNo);
            params.put("altLotNO", AtrlotNo);
            params.put("ctInwardNo", ctInwardNo);
        stockList = FecthResultGt(req,res, params);
        if(stockList.size()>0){
        HashMap totals = GetTotal(req,res);
        req.setAttribute("totalMap", totals);
            ArrayList options = getOptions(req ,res, mprcIdn);
           
            req.setAttribute("OPTIONS", options);


        }
        }
        req.setAttribute("view", "Y");
        assortForm.setValue("prcId", mprcIdn);
        assortForm.setValue("empId", empId);
        req.setAttribute("prcId", mprcIdn);
            assortForm.setValue("pri_yn", pri_yn);
          String lstNme = "ASRT_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
          assortForm.setValue("lstNme", lstNme);
          gtMgr.setValue(lstNme, stockList);
          req.setAttribute("lstNme", lstNme);
          gtMgr.setValue(lstNme+"_SEL", new ArrayList());

        return am.findForward("load");
        }
    }
    
    public ActionForward Openpop(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
       AssortReturnForm assortForm = (AssortReturnForm)af;
        AssortReturnInterface assortInt = new AssortReturnImpl();
            util.updAccessLog(req,res,"Assort Return", "Openpop");
      
        ArrayList prpList=new ArrayList();
        
        String prcId = req.getParameter("prcID");
        String sql="select mprp from prc_prp_alw where prc_idn=? and flg <> 'FTCH' order by srt";
        ArrayList ary = new ArrayList();
        ary.add(prcId);
          ArrayList  rsLst = db.execSqlLst("sql", sql, ary);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
        while(rs.next()){
          String lprp = rs.getString("mprp");
           prpList.add(lprp);
            assortForm.setValue(lprp,"");
        }
        rs.close();
          stmt.close();
        session.setAttribute("prpList",prpList);
            util.updAccessLog(req,res,"Assort Return", "prpList :"+prpList.size());
            util.updAccessLog(req,res,"Assort Return", "End");
        return am.findForward("multiPrp");
        }
    }
    
    public ActionForward bulkUpdate(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
       AssortReturnForm assortForm = (AssortReturnForm)form;
       util.updAccessLog(req,res,"Assort Return", "bulkUpdate");
        assortForm = (AssortReturnForm)form;
        int ct=0;
        ArrayList ary = new ArrayList();
        ArrayList prpList = (ArrayList)session.getAttribute("prpList");
        String prcId= (String)assortForm.getValue("prcId");
          String lstNme= (String)assortForm.getValue("lstNme");

        String grp = "select grp from mprc where idn=?";
        ary = new ArrayList();
        ary.add(prcId);
          ArrayList  rsLst = db.execSqlLst("grp", grp, ary);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
        String grpVal="";
        if(rs.next())
        grpVal = rs.getString("grp");
        rs.close();
          stmt.close();
        String mstkIdn="";
        ArrayList selectList = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
        try{
        if(selectList!=null && selectList.size()>0) {
            for(int i=0;i<selectList.size();i++){
                
            String stkIdn = (String)selectList.get(i);
            ary = new ArrayList();
            ary.add(stkIdn);
            ary.add(grpVal);
            int issIdn =0;
            String getIssIdn = "select ISS_RTN_PKG.LST_ISS_ID(?,?) issIdn from dual";
            rsLst = db.execSqlLst("issIdn", getIssIdn, ary);
              stmt =(PreparedStatement)rsLst.get(0);
               rs =(ResultSet)rsLst.get(1);
             if(rs.next())
             issIdn = rs.getInt(1);
                rs.close();
                stmt.close();
            for(int j=0 ; j< prpList.size() ; j++){
            String lprp = (String)prpList.get(j);
            String fldVal =util.nvl((String)assortForm.getValue(lprp));
            if(!fldVal.equals("") && !fldVal.equals("0")){
             ary = new ArrayList();
             ary.add(String.valueOf(issIdn));
             ary.add(stkIdn);
             ary.add(lprp);
             ary.add(fldVal);
            ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
           
            }}
            
            }
        }
            rs.close();
        }catch (SQLException sqle) {
                 // TODO: Add catch code
                 sqle.printStackTrace();
             } 
        if(ct>0)
            req.setAttribute( "msg","Propeties Get update successfully");
            util.updAccessLog(req,res,"Assort Return", "End");
        return am.findForward("multiPrp");
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
              AssortReturnForm assortForm = (AssortReturnForm)form;

              String lstNme= (String)assortForm.getValue("lstNme");

                util.updAccessLog(req,res,"Assort Return", "createXL");
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "AssortReturnExcel"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
            ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
              HashMap excelDtl = new HashMap();
              excelDtl.put("HDR", itemHdr);
              excelDtl.put("lstNme", lstNme);
            HSSFWorkbook hwb = null;
            ExcelUtil xlUtil = new ExcelUtil();
            xlUtil.init(db, util, info);
            hwb = xlUtil.getGenXlObj(excelDtl,req);
            OutputStream out = res.getOutputStream();
            hwb.write(out);
            out.flush();
            out.close();
                util.updAccessLog(req,res,"Assort Return", "End");
            return null;
            }
        }
    public ActionForward stockUpd(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        AssortReturnForm assortForm = (AssortReturnForm)form;
         AssortReturnInterface assortInt = new AssortReturnImpl();
           util.updAccessLog(req,res,"Assort Return", "stockUpd");
     
        String prcId= util.nvl((String)assortForm.getValue("prcId"));
        String issId = util.nvl(req.getParameter("issIdn"));
        String mstkIdn = util.nvl(req.getParameter("mstkIdn"));
        String lastpage = util.nvl(req.getParameter("lastpage"));
        String currentpage = util.nvl(req.getParameter("currentpage"));
        String findVnm = util.nvl(req.getParameter("findVnm"));
        HashMap params = new HashMap();
         String vnm="";
         if(!currentpage.equals("") && findVnm.equals("")){
             String lstNme = (String)assortForm.getValue("lstNme");
             HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
             ArrayList stkIdnList = (ArrayList)gtMgr.getValue(lstNme+"_ALLLST"); 
             mstkIdn = (String)stkIdnList.get(Integer.parseInt(currentpage));
             GtPktDtl pktDtl = (GtPktDtl)stockList.get(mstkIdn);
             vnm = util.nvl(pktDtl.getVnm());
             issId = util.nvl(pktDtl.getValue("issIdn"),issId);
         }
          if(!findVnm.equals("")){
                 String lstNme = (String)assortForm.getValue("lstNme");
                 HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
                 ArrayList stkIdnList = (ArrayList)gtMgr.getValue(lstNme+"_ALLLST");
                 String fromIdn = util.nvl((String)util.getMstkIdn(findVnm));
                 if(!fromIdn.equals("")){
                     int indexOfIdn=stkIdnList.indexOf(fromIdn);
                     if(indexOfIdn >= 0){
                     mstkIdn = (String)stkIdnList.get(indexOfIdn);
                     GtPktDtl pktDtl = (GtPktDtl)stockList.get(mstkIdn);
                     vnm = util.nvl(pktDtl.getVnm());
                     issId = util.nvl(pktDtl.getValue("issIdn"),issId);
                     currentpage=String.valueOf(indexOfIdn);
                     }
                 }else{
                     mstkIdn = (String)stkIdnList.get(Integer.parseInt(currentpage));
                     GtPktDtl pktDtl = (GtPktDtl)stockList.get(mstkIdn);
                     vnm = util.nvl(pktDtl.getVnm());
                     issId = util.nvl(pktDtl.getValue("issIdn"),issId);
                 }
             }
        params.put("prcId", prcId);
        params.put("issId", issId);
        params.put("mstkIdn", mstkIdn);  
        ArrayList stockPrpList = assortInt.StockUpdPrp(req,res, assortForm , params );
         String isRepair = util.nvl(req.getParameter("isRepair"));
         ArrayList ary = new ArrayList();
                 if(isRepair.equals("Y")){
                     int prcChkIdn = 0 ;
                     String newmstkIdn="";
                     String getPrcChkIdn = " select prc_chk_pkg.get_idn from dual ";
                   ArrayList  rsLst = db.execSqlLst(" Prc Chk Idn", getPrcChkIdn, new ArrayList());
                   PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
                   ResultSet  rs =(ResultSet)rsLst.get(1);
                     if(rs.next()) {
                         prcChkIdn = rs.getInt(1);
                         String insMst = " prc_chk_pkg.add_new(?, ?) ";
                         ary = new ArrayList();
                         ary.add(Integer.toString(prcChkIdn));
                         ary.add(mstkIdn);
                         
                     db.execCall(" Prc Mst", insMst, ary);
                     newmstkIdn=String.valueOf(prcChkIdn);  
                     }
                     rs.close();
                     stmt.close();
                     ary = new ArrayList();
                     ary.add(newmstkIdn);
                     int ct =  db.execCall("stk_rap_upd", "stk_rap_upd(pIdn => ?)", ary);
                     
                   
                     String getPrcDtl = " select b.rap_rte, trunc(sum(a.pct), 2) dis, ceil(b.rap_rte*(100+sum(a.pct))/100) rte "+
                                      " from prm_dis_v a , mstk b "+
                                     " where a.mstk_idn = b.idn and mstk_idn=? " +
                                     " group by a.mstk_idn, b.rap_rte ";
                     ArrayList  outLst = db.execSqlLst(" get vals", getPrcDtl, ary);
                     PreparedStatement pst = (PreparedStatement)outLst.get(0);
                     rs = (ResultSet)outLst.get(1);
                     while(rs.next()){
                         req.setAttribute("uprDis", util.nvl(rs.getString("dis")));
                         req.setAttribute("cmp", util.nvl(rs.getString("rte")));
                     }
                     rs.close();
                     pst.close();
                     assortForm.setValue("stkIdn", newmstkIdn);
                 }
                 
        
        req.setAttribute("StockList", stockPrpList);
        assortForm.setValue("prcId", prcId);
        assortForm.setValue("issIdn", issId);
        assortForm.setValue("mstkIdn", mstkIdn);
        assortForm.setValue("lastpage", lastpage);
        assortForm.setValue("isRepair", isRepair);
        assortForm.setValue("vnm", vnm);
        assortForm.setValue("currentpage", currentpage);
        assortForm.setValue("url", "assortReturn.do?method=stockUpd&mstkIdn="+mstkIdn+"&issIdn="+issId+"&isRepair="+isRepair);
        req.setAttribute("PopUpidn", mstkIdn);
             util.updAccessLog(req,res,"Assort Return", mstkIdn);
        return am.findForward("loadStock");
         }
     }
    
    public ActionForward savePrpUpd(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
       AssortReturnForm assortForm = (AssortReturnForm)form;
        AssortReturnInterface assortInt = new AssortReturnImpl();
          util.updAccessLog(req,res,"Assort Return", "savePrpUpd");
      
        String prcId= (String)assortForm.getValue("prcId");
        String issId = (String)assortForm.getValue("issIdn");
        String mstkIdn = (String)assortForm.getValue("mstkIdn");
        String vnm="";
        HashMap mprpLst = info.getMprp();
        String issStt = assortInt.issPrcStt(req, res, Integer.parseInt(prcId));
        String lastpage = util.nvl(req.getParameter("lastpage"));
        String currentpage = util.nvl(req.getParameter("currentpage"));
        String lstNme = (String)assortForm.getValue("lstNme");
        HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
        ArrayList stkIdnList = (ArrayList)gtMgr.getValue(lstNme+"_ALLLST"); 
        GtPktDtl pktDtl = (GtPktDtl)stockList.get(mstkIdn);
        HashMap mprp = info.getMprp();  
        if(lastpage.equals("") && currentpage.equals("")){
            lastpage = util.nvl((String)assortForm.getValue("lastpage"));
            currentpage = util.nvl((String)assortForm.getValue("currentpage"));
            vnm=util.nvl((String)assortForm.getValue("vnm"));
        }
        ArrayList ary = new ArrayList();
        ArrayList assortPrpUpd = (ArrayList)session.getAttribute("assortUpdPrp");
        int ct =0;
        for(int i=0 ; i <assortPrpUpd.size();i++){
            
            String lprp = (String)assortPrpUpd.get(i);
            String lprpTyp = util.nvl((String)mprpLst.get(lprp+"T"));
            String fldVal =util.nvl((String)assortForm.getValue(lprp));
           // String repVal=util.nvl((String)assortForm.getValue("RP_"+lprp),"NA");
            if(!fldVal.equals("") && !fldVal.equals("0")){
            ary = new ArrayList();
            ary.add(issId);
            ary.add(mstkIdn);
            ary.add(lprp);
            ary.add(fldVal);
          //  ary.add(repVal);
            ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
            if(issStt.equals("WT_CHK_IS")){
                String lab = assortInt.getLab(req, res, mstkIdn);
                ary = new ArrayList();
                ary.add(mstkIdn);
                ary.add(lprp);
                ary.add(fldVal);
                ary.add(lprpTyp);
                ary.add(lab);
                String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? ,  pPrpTyp => ? , plab => ? )";
                ct = db.execCall("stockUpd",stockUpd, ary);
            }
         }
            
        }
        
        ArrayList prcPrpUpd = (ArrayList)session.getAttribute("prcPrpList");
      
        HashMap params = new HashMap();
        params.put("prcId", prcId);
        params.put("issId", issId);
        params.put("mstkIdn", mstkIdn);
        req.setAttribute("PopUpidn", mstkIdn);
        assortForm.setValue("vnm", vnm);
        assortForm.setValue("lastpage", lastpage);
        assortForm.setValue("currentpage", currentpage);
        assortForm.setValue("url", "assortReturn.do?method=stockUpd&mstkIdn="+mstkIdn+"&issIdn="+issId+"&isRepair=N");
        ArrayList stockPrpList = assortInt.StockUpdPrp(req,res, assortForm , params );
        req.setAttribute( "msg","Propeties Get update successfully");
        req.setAttribute("StockList", stockPrpList);
            util.updAccessLog(req,res,"Assort Return", mstkIdn);
            util.updAccessLog(req,res,"Assort Return", "End");
        return am.findForward("loadStock");
        }
    }
    
    public ActionForward Return(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
       AssortReturnForm assortForm = (AssortReturnForm)form;
       util.updAccessLog(req,res,"Assort Return", "Return");
       String lstNme = (String)assortForm.getValue("lstNme");
        ArrayList RtnMsgList = new ArrayList();
        String msg = "";
        int cnt = 0;
            String delQ = " Delete from gt_srch_rslt ";
            int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            
        ArrayList params = null;
        ArrayList returnPkt = new ArrayList();
        ArrayList selectList = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
        HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
         if(selectList!=null && selectList.size()>0) {
          for(int i=0;i<selectList.size();i++){
              String stkIdn = (String)selectList.get(i);
              GtPktDtl pktDtl = (GtPktDtl)stockList.get(stkIdn);
              String issDtlIdn = pktDtl.getValue("issDtlIdn");
              String issId = pktDtl.getValue("issIdn");
              String srchRefQ = 
              "    Insert into gt_srch_rslt (srch_id, stk_idn,sk1) select ?,?,? from dual";
              params = new ArrayList();
              params.add(issId);
              params.add(stkIdn);
              params.add(issDtlIdn);
              ct = db.execUpd(" Srch Prp ", srchRefQ, params);
          }
        String reprc = util.nvl((String)assortForm.getValue("reprc"));
        String insQ = " insert into gt_pkt(mstk_idn) select ? from dual ";
        String gtSelect="select srch_id, stk_idn,sk1 from gt_srch_rslt order by sk1 ";
        ArrayList outLst = db.execSqlLst("gtSelect", gtSelect, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
         while(rs.next()){
           String stkIdn = rs.getString("stk_idn");
            String issId = rs.getString("srch_id");
           if(reprc.equals("")){
            String lStkStt = util.nvl((String)assortForm.getValue("STT_"+stkIdn), "NA");
            ArrayList RtnMsg = new ArrayList();
            returnPkt.add(stkIdn);
            params = new ArrayList();
            params.add(issId);
            params.add(stkIdn);
            params.add("RT");
            params.add(lStkStt);
            ArrayList out = new ArrayList();
            out.add("I");
            out.add("V");
            String issuePkt = "ISS_RTN_PKG.RTN_PKT(pIssId => ?, pStkIdn => ? , pStt => ? , pStkStt => ? , pCnt=>? , pMsg => ? )";
            CallableStatement cts = db.execCall("issue Rtn", issuePkt, params, out);
            cnt = cts.getInt(5);
            msg = cts.getString(6);
            RtnMsg.add(cnt);
            RtnMsg.add(msg);
            RtnMsgList.add(RtnMsg);
            assortForm.setValue(stkIdn,"");
            }else{
               params = new ArrayList();
               params.add(issId);
               params.add(stkIdn);
               int ctv = db.execCall("apply_rtn_prp", "iss_rtn_pkg.apply_rtn_prp(?, ?, 'MOD')", params);
               
               params = new ArrayList();
               params.add(stkIdn);
               ctv = db.execUpd("reprc", insQ, params);
           }
       }
         rs.close();
         pst.close();
                  if(!reprc.equals("")){
                       String  jbCntDb = "1";
                       ArrayList out = new ArrayList();
                       out.add("I");
                       String reprcProc = "reprc(num_job => ?, lstt1 => 'AS_PRC', lstt2 => 'FORM',lSeq=> ?)";
                       ArrayList reprcParams = new ArrayList();
                       int jobCnt = Integer.parseInt(jbCntDb);
                       reprcParams.add(String.valueOf(jobCnt));
                       int lseq = 0;
                       CallableStatement cnt1 = db.execCall(" reprc : ",reprcProc, reprcParams,out );
                       lseq = cnt1.getInt(reprcParams.size()+1);
                       req.setAttribute("seqNo","Current Repricing Sequence Number :  "+String.valueOf(lseq));
                  }
                  }
    if(returnPkt.size()>0)
        req.setAttribute("msgList",RtnMsgList);
            util.updAccessLog(req,res,"Assort Return", "End");
          gtMgr.reset();
     return fecth(am, form, req, res);
        }
    }
    
    public ActionForward selectRtnPrppktAsrtRtn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        AssortReturnForm assortForm = (AssortReturnForm)af;
        String lstNme = (String)assortForm.getValue("lstNme");
        HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
        ArrayList stkIdnList = (ArrayList)gtMgr.getValue(lstNme+"_ALLLST"); 
        StringBuffer sb = new StringBuffer();
        boolean setdflt=false;
        if(stkIdnList.size()>0){
        for(int i=0;i<stkIdnList.size();i++){
            String mstkIdn = (String)stkIdnList.get(i);
            GtPktDtl pktDtl = (GtPktDtl)stockList.get(mstkIdn);
            String rtnprpupdated= util.nvl((String)pktDtl.getValue("rtnprpupdated"));
            if(rtnprpupdated.equals("Y")){
            sb.append("<detail>");
            sb.append("<stkidn>"+mstkIdn+"</stkidn>");
            sb.append("</detail>");
            setdflt=true;
            }
        }
        }
        
        if(!setdflt){
            sb.append("<detail>");
            sb.append("<stkidn>1</stkidn>");
            sb.append("</detail>"); 
        }
        res.getWriter().write("<details>" +sb.toString()+ "</details>");
        return null;
    }
        public HashMap FecthResultGt(HttpServletRequest req,HttpServletResponse res, HashMap params){
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr();
          HashMap stockList = new HashMap();
          if(info!=null){
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
           init(req,res,session,util);
          
            String stt = (String)params.get("stt");
            String vnm = (String)params.get("vnm");
            String issId = (String)params.get("issueId");
            String empIdn = (String)params.get("empIdn");
            String grpList = (String)params.get("grpList");
              String lotNo = util.nvl((String)params.get("lotNo"));
              String altLotNo = util.nvl((String)params.get("altLotNO"));
              String ctInwardNo = util.nvl((String)params.get("ctInwardNo"));
            ArrayList ary = null;
            String delQ = " Delete from gt_srch_rslt ";
            int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            
            String srchRefQ = 
            "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty ,quot, cts , srch_id , rmk , rln_idn , sk1,rap_rte,rap_dis,rchk,pair_id ) " + 
            " select  a.pkt_ty, a.idn, a.vnm,  a.dte, a.stt , 'Z' , a.qty , a.upr,a.cts , b.iss_id , a.tfl3 , b.iss_emp_id , a.sk1,a.rap_rte,decode(a.rap_rte ,'1',null,'0',null, trunc((nvl(a.upr,a.cmp)/greatest(a.rap_rte,1)*100)-100, 2)),b.lot_id, "+
            "  b.iss_rtn_dtl_id from mstk a , iss_rtn_dtl b where a.idn = b.iss_stk_idn and b.stt='IS'  "+
            " and a.stt =? and a.cts > 0   ";
            ary = new ArrayList();
            ary.add(stt);
            if(vnm.length()>2){
               vnm = util.getVnm(vnm);
                srchRefQ = srchRefQ+" and ( a.vnm in ("+vnm+") or a.tfl3 in ("+vnm+") ) " ;
            }
            if(!issId.equals("")){
                srchRefQ = srchRefQ+" and b.iss_id = ? " ;
                ary.add(issId);
            }
            if(!empIdn.equals("0")){
                
                srchRefQ = srchRefQ+" and b.iss_emp_id = ? " ;
                ary.add(empIdn);
            }
              if(!lotNo.equals("")){
                  srchRefQ = srchRefQ+" and EXISTS ( select 1 from stk_dtl c where a.idn = c.mstk_idn and c.mprp='LOTNO' and c.txt = ?)";
                  ary.add(lotNo);
              }
              if(!altLotNo.equals("")){
                  srchRefQ = srchRefQ+" and EXISTS ( select 1 from stk_dtl d where a.idn = d.mstk_idn and d.mprp='ALT_LOTNO' and d.txt = ?)";
                  ary.add(altLotNo);
              }
              if(!ctInwardNo.equals("")){
                  srchRefQ = srchRefQ+" and EXISTS ( select 1 from stk_dtl d where a.idn = d.mstk_idn and d.mprp='CT_INWARD_NO' and d.txt = ?)";
                  ary.add(ctInwardNo);
              }
            ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
            
            String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
            ary = new ArrayList();
            ary.add("MIX_VIEW");
            ct = db.execCall(" Srch Prp ", pktPrp, ary);
            
          
            
            stockList = SearchResultGt(req ,res, vnm);
          }
            return stockList;
        }
        
        public HashMap SearchResultGt(HttpServletRequest req ,HttpServletResponse res, String vnmLst ){
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr();
          HashMap pktList = new HashMap();
          if(info!=null){
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
           init(req,res,session,util);
           
            SearchQuery query=new SearchQuery();
            ArrayList vwPrpLst = ASPrprViw(req,res);
            String  srchQ =  " select stk_idn , pkt_ty, pair_id, vnm, pkt_dte, stt ,sk1, qty,to_char(quot,'99999990.90') quot ,to_char(quot*cts,'99990.90') amt, cts , srch_id ,rmk , byr.get_nm(rln_idn) emp,decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis,nvl(rchk,'N') rtnprpupdated ";

            

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
            ary.add("Z");
            ArrayList  rsLst = db.execSqlLst("search Result", rsltQ, ary);
            PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
            ResultSet  rs =(ResultSet)rsLst.get(1);
            try {
                while(rs.next()) {
                   
                      String stk_idn = util.nvl(rs.getString("stk_idn"));
                      GtPktDtl pktPrpMap = new GtPktDtl();
                      pktPrpMap.setStt(util.nvl(rs.getString("stt")));
                      String vnm = util.nvl(rs.getString("vnm"));
                      pktPrpMap.setVnm(vnm);
                      pktPrpMap.setValue("emp", util.nvl(rs.getString("emp")));
                      pktPrpMap.setValue("vnm", vnm);
                      pktPrpMap.setSk1(new BigDecimal(util.nvl(rs.getString("sk1"),"0")));
                      pktPrpMap.setValue("rtnprpupdated", util.nvl(rs.getString("rtnprpupdated")));
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
                      pktPrpMap.setPktIdn(rs.getLong("stk_idn"));
                      pktPrpMap.setQty(util.nvl(rs.getString("qty")));
                      pktPrpMap.setCts(util.nvl(rs.getString("cts")));
                        pktPrpMap.setValue("amt",util.nvl(rs.getString("amt")));
                      pktPrpMap.setValue("issIdn",util.nvl(rs.getString("srch_id")));
                     pktPrpMap.setValue("issDtlIdn",util.nvl(rs.getString("pair_id")));
                    for(int j=0; j < vwPrpLst.size(); j++){
                         String prp = (String)vwPrpLst.get(j);
                          
                          String fld="prp_";
                          if(j < 9)
                                  fld="prp_00"+(j+1);
                          else    
                                  fld="prp_0"+(j+1);
                          
                            if(prp.equals("RTE"))
                            fld="quot";
                          
                               String val = util.nvl(rs.getString(fld)) ;
                            if (prp.toUpperCase().equals("RAP_DIS"))
                            val = util.nvl(rs.getString("r_dis")) ; 
                               pktPrpMap.setValue(prp, val);
                        }
                                  
                      pktList.put(stk_idn,pktPrpMap);
                    }
                rs.close();
                stmt.close();
                pktList =(HashMap)query.sortByComparator(pktList);
            } catch (SQLException sqle) {

                // TODO: Add catch code
                sqle.printStackTrace();
            }
            ArrayList list = (ArrayList)req.getAttribute("msgList");
            if(list!=null && list.size()>0){
            }else{
            if(!vnmLst.equals("")){
                vnmLst = util.pktNotFound(vnmLst);
                req.setAttribute("vnmNotFnd", vnmLst);
            }
            }
          }
            return pktList;
        }
        
          public ArrayList ASPrprViw(HttpServletRequest req , HttpServletResponse res){
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr();
            ArrayList asViewPrp = (ArrayList)session.getAttribute("MIX_VIEW");
            if(info!=null){
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
             init(req,res,session,util);
             
              try {
                  if (asViewPrp == null) {

                      asViewPrp = new ArrayList();
                  
                    ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'MIX_VIEW' and flg='Y' order by rnk ",
                                     new ArrayList());
                    PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
                    ResultSet  rs1 =(ResultSet)rsLst.get(1);
                      while (rs1.next()) {

                          asViewPrp.add(rs1.getString("prp"));
                      }
                      rs1.close();
                      stmt.close();
                      session.setAttribute("MIX_VIEW", asViewPrp);
                  }

              } catch (SQLException sqle) {
                  // TODO: Add catch code
                  sqle.printStackTrace();
              }
            }
              return asViewPrp;
          }
          
          public ArrayList StockUpdPrp(HttpServletRequest req, HttpServletResponse res, AssortReturnForm form , HashMap params){
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr();
            ArrayList stockPrpList = new ArrayList();
            if(info!=null){
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
             init(req,res,session,util);
              AssortReturnForm assortForm = (AssortReturnForm)form;
              String mstkIdn = (String)params.get("mstkIdn");
            
              ArrayList updatePrpList = new ArrayList();
              ArrayList prcPrpList = new ArrayList();
              HashMap mprp = info.getMprp();
            String stockPrp = "select a.mprp ,a.iss_stk_idn, b.flg" +
                              ",  Decode(C.Dta_Typ, 'C', A.Iss_Val, 'N', A.Iss_Num ,'D',format_to_date(a.txt), A.Txt) iss_val,\n" + 
                              " Decode(C.Dta_Typ, 'C', A.rtn_Val, 'N', A.rtn_Num ,'D',format_to_date(a.txt), A.Txt) rtn_val ,  "+
                               "  Decode(C.Dta_Typ, 'C', A.rep_Val, 'N', A.rep_Num ,'D',format_to_date(a.rep_txt), A.rep_Txt) rep_val "+
            
                //", a.iss_val, decode(b.flg, 'COMP', a.rtn_val, nvl(a.rtn_val, a.iss_val)) rtn_val " +
                //", a.iss_num , decode(b.flg, 'COMP', a.rtn_num, nvl(a.rtn_num, a.iss_num)) rtn_num , a.txt " +
                " from iss_rtn_prp a , prc_prp_alw b , mprp c where  a.mprp = b.mprp and b.mprp=c.prp and " +
                              "a.iss_id=? and a.iss_stk_idn = ?  and b.prc_idn =? order by b.srt ";

              ArrayList ary = new ArrayList();
              ary.add(params.get("issId"));
              ary.add(params.get("mstkIdn"));
              ary.add(params.get("prcId"));
              ArrayList  rsLst = db.execSqlLst("stockDtl", stockPrp, ary);
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs =(ResultSet)rsLst.get(1);
              try {
                  while(rs.next()) {
                      HashMap stockPrpUpd = new HashMap();
                      String lprp = util.nvl(rs.getString("mprp"));
                      String issVal = util.nvl(rs.getString("iss_val"));
                      String rtnVal = util.nvl(rs.getString("rtn_val"));
                      String repVal = util.nvl(rs.getString("rep_val"));
                     
                      String flg = util.nvl(rs.getString("flg"));
                    
                      stockPrpUpd.put("mprp",lprp);
                      stockPrpUpd.put("issVal",issVal);
                      stockPrpUpd.put("rtnVal",rtnVal) ;
                      stockPrpUpd.put("flg",flg);
                      stockPrpUpd.put("mstkIdn",util.nvl(rs.getString("iss_stk_idn")));
                 
                      form.setValue(lprp, rtnVal);
                       if(!flg.equals("FTCH"))
                          updatePrpList.add(lprp);
                      stockPrpList.add(stockPrpUpd);
                      form.setValue("RP_"+lprp,repVal);
                      prcPrpList.add(lprp);
                  }
                  
                  rs.close();
                  stmt.close();
              } catch (SQLException sqle) {
                  // TODO: Add catch code
                  sqle.printStackTrace();
              }
              
              session.setAttribute("assortUpdPrp", updatePrpList);
              session.setAttribute("prcPrpList", prcPrpList);
              req.setAttribute("mstkIdn", mstkIdn);
              assortForm.setValue("issIdn", params.get("issId"));
              assortForm.setValue("prcId", params.get("prcId"));
              assortForm.setValue("mstkIdn", mstkIdn);
            }
              return stockPrpList;
          }
          
          public HashMap GetTotal(HttpServletRequest req , HttpServletResponse res){
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr();
            HashMap gtTotalMap = new HashMap();
            if(info!=null){
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
             init(req,res,session,util);
             
              String gtTotal ="Select sum(qty) qty, sum(cts) cts from gt_srch_rslt where flg = ?";
              ArrayList ary = new ArrayList();
              ary.add("Z");
              ArrayList  rsLst = db.execSqlLst("getTotal", gtTotal, ary);
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs =(ResultSet)rsLst.get(1);
              try {
                  if (rs.next()) {
                   gtTotalMap.put("qty", util.nvl(rs.getString("qty")));
                   gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
                  }
                  rs.close();
                  stmt.close();
              } catch (SQLException sqle) {
                  // TODO: Add catch code
                  sqle.printStackTrace();
              }
            }
              return gtTotalMap ;
          }
          public String getLab(HttpServletRequest req , HttpServletResponse res ,String stkIdn){
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr();
            String lab="";
            if(info!=null){
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
             init(req,res,session,util);
            
              String getLab = "select cert_lab from mstk where idn=? ";
              ArrayList ary = new ArrayList();
              ary.add(stkIdn);
              ArrayList  rsLst = db.execSqlLst("getLab", getLab, ary);
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs =(ResultSet)rsLst.get(1);
              try {
                  while (rs.next()) {
                         lab = rs.getString("cert_lab");
                  }
                  rs.close();
                  stmt.close();
              } catch (SQLException sqle) {
                  // TODO: Add catch code
                  sqle.printStackTrace();
              }
            }
              return  lab;
          }
          
        public ArrayList getOptions( HttpServletRequest req ,HttpServletResponse res , String issueId ){
        //HashMap prcStt = new HashMap();
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
          ArrayList prcStt = new ArrayList();
        if(info!=null){
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
         init(req,res,session,util);
        

        String getPrcToPrc =  " select a1.in_stt from mprc a1, mprc a, prc_to_prc b "+
        " where a.idn = b.prc_id and a1.idn = b.prc_to_id  and a.idn = ? and b.flg1=? "+
        " order by b.srt ";

        ArrayList ary = new ArrayList();
        ary.add(issueId);
            ary.add("Y");
          ArrayList  rsLst = db.execSqlLst(" get options", getPrcToPrc, ary);
          PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
          ResultSet  rs =(ResultSet)rsLst.get(1);
        try {
        while(rs.next()){
        prcStt.add(rs.getString("in_stt"));
        }
        rs.close();
            stmt.close();
        } catch (SQLException e) {
        
        }
        }
        return prcStt ;
        }

          public String issPrcStt(HttpServletRequest req , HttpServletResponse res ,int prcIdn){
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr();
            String issueStt="";
            if(info!=null){
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
             init(req,res,session,util);
             
              String issSttPrc = "select idn, prc , in_stt , ot_stt , is_stt from mprc where idn=? ";
              ArrayList ary = new ArrayList();
              ary.add(String.valueOf(prcIdn));
              ArrayList  rsLst = db.execSqlLst("issStt", issSttPrc, ary);
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs =(ResultSet)rsLst.get(1);
              try {
                  while (rs.next()) {
                         issueStt = rs.getString("is_stt");
                  }
                  rs.close();
                  stmt.close();
              } catch (SQLException sqle) {
                  // TODO: Add catch code
                  sqle.printStackTrace();
              }
            }
              return  issueStt;
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
              util.updAccessLog(req,res,"Assort Issue Action", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Assort Issue Action", "init");
          }
          }
          return rtnPg;
          }
    }


