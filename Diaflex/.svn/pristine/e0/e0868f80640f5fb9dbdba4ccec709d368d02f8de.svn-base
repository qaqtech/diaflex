package ft.com.pri;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.GetPktItempri;
import ft.com.GtMgr;
import ft.com.ImageChecker;
import ft.com.InfoMgr;

import ft.com.assort.AssortReturnForm;
import ft.com.assort.AssortReturnInterface;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;

import ft.com.generic.GenericImpl;
import ft.com.marketing.PacketPrintForm;
import ft.com.marketing.SearchQuery;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.io.LineNumberReader;
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
import org.apache.struts.upload.FormFile;
import java.io.FileOutputStream;

import java.text.DecimalFormat;

import java.util.LinkedHashMap;
import java.util.Set;

public class PriceRtnAction extends DispatchAction {
    
   
    
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
          util.updAccessLog(req,res,"PriceRtn", "load");
       PriceRtnForm  priceRtnForm = (PriceRtnForm)form;
        priceRtnForm.reset();
        ArrayList mprcList = getPrc(req,res);
        priceRtnForm.setMprcList(mprcList);
        ArrayList empList = getEmp(req,res);
        priceRtnForm.setEmpList(empList);  
          util.updAccessLog(req,res,"PriceRtn", "load end");
          
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("PRI_RETURN");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("PRI_RETURN");
            allPageDtl.put("PRI_RETURN",pageDtl);
            }
            info.setPageDetails(allPageDtl);
      return am.findForward("load");
        }
    }
    public ActionForward fecth(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"PriceRtn", "fetch");
       PriceRtnForm  priceRtnForm = (PriceRtnForm)form;
          SearchQuery srchQuery = new SearchQuery();
        String mprcIdn = util.nvl((String)priceRtnForm.getValue("mprcIdn"));
        String stoneId = util.nvl((String)priceRtnForm.getValue("vnmLst"));
        String empId = util.nvl((String)priceRtnForm.getValue("empIdn"));
        String issueId = util.nvl((String)priceRtnForm.getValue("issueId"));
        FormFile uploadFile = priceRtnForm.getFileUpload();
        HashMap prcBeansList = (HashMap)session.getAttribute("mprcBean");
        HashMap fileDataMap=new HashMap();
          if(prcBeansList==null){
            getPrc(req, res);
            prcBeansList = (HashMap)session.getAttribute("mprcBean");
          }
        ArrayList vnmImgLst=new ArrayList();
        ArrayList vnmStkCtgLst=new ArrayList();
        String prcStt = "",prcisstt = "";
            String pri_yn = "";
        String dept = "DEPT";
        ArrayList vwPrpLst = PriPrprViw(req,res);
        int indexdept = vwPrpLst.indexOf(dept)+1;
        String deptPrp = util.prpsrtcolumn("prp",indexdept);
        int indexstkctg = vwPrpLst.indexOf("STK-CTG")+1;
        String stkctgPrp = util.prpsrtcolumn("prp",indexstkctg);
        if(!issueId.equals("") && mprcIdn.equals("0")){
            ArrayList ary = new ArrayList();
            String issuStt = " select b.is_stt , b.idn,b.pri_yn from iss_rtn a , mprc b where a.prc_id = b.idn and a.iss_id= ? ";
            ary.add(issueId);
          ArrayList rsLst = db.execSqlLst("issueStt", issuStt, ary);
          PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
          ResultSet rs = (ResultSet)rsLst.get(1);
            while(rs.next()){
              prcStt = util.nvl(rs.getString("is_stt"));
              mprcIdn = util.nvl(rs.getString("idn"));
                pri_yn = util.nvl(rs.getString("pri_yn"));
            }
            rs.close();
            stmt.close();
        }else{
         Mprc mprcDto = (Mprc)prcBeansList.get(mprcIdn);
         prcStt = mprcDto.getIs_stt();
         pri_yn= mprcDto.getPri_yn();
        }
        if(uploadFile!=null){
            fileDataMap=getdatafromFile(req,uploadFile,util,info);
            String stoneIdfile=util.nvl((String)req.getAttribute("srchRefVal"));
            if(!stoneIdfile.equals(""))
            stoneId=stoneIdfile;
        }
        HashMap params = new HashMap();
        params.put("stt", prcStt);
        params.put("vnm",stoneId);
        params.put("empIdn", empId);
        params.put("issueId", issueId);
        HashMap pktList = FecthResult(req,res, params);
        if(pktList!=null && pktList.size() >0){
          HashMap totals = srchQuery.GetTotal(req,res,"Z");
          ArrayList prpList = new ArrayList();
                          ArrayList options = getOptions(req ,res, mprcIdn);
                         
                          req.setAttribute("totalMap", totals);
                          req.setAttribute("OPTIONS", options);
                      String issPrp ="Select A.mprp ,A.Iss_Stk_Idn,Decode(C.Dta_Typ, 'C', Nvl(A.Rtn_Val,A.Iss_Val), 'N', Nvl(A.Rtn_Num,A.Iss_Num),'D',format_to_date(a.txt), A.Txt) val \n" + 
                      "From Iss_Rtn_Prp A, Gt_Srch_Rslt B , Mprp C , Prc_Prp_Alw D,Iss_Rtn E\n" + 
                      "Where A.Iss_Id=B.Srch_Id And A.Iss_Stk_Idn = B.Stk_Idn \n" + 
                      "And E.Iss_Id=B.Srch_Id \n" + 
                      "and d.prc_idn= E.prc_id and d.mprp=a.mprp and d.flg ='ISSEDIT' \n" + 
                      "And A.Mprp = C.Prp\n" + 
                      "Order By A.Iss_Id , A.Iss_Stk_Idn";
                     ArrayList rsLst = db.execSqlLst("Dtl", issPrp, new ArrayList());
                    PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
                    ResultSet rs = (ResultSet)rsLst.get(1);
                          while(rs.next()) {
                              String lprp = util.nvl(rs.getString("mprp"));
                              String val = util.nvl(rs.getString("val"));
                              String stkidn = util.nvl(rs.getString("Iss_Stk_Idn"));
                              priceRtnForm.setValue(lprp+"_"+stkidn, val);
                              if(!prpList.contains(lprp))
                                   prpList.add(lprp);
                          }
                        session.setAttribute("labSVCPrpList", prpList);
                          rs.close();
                          stmt.close();
            Mprc mprcDto = (Mprc)prcBeansList.get(mprcIdn);
            prcisstt = mprcDto.getIs_stt();
            if(prcisstt.equals("TRF_MKT_IS")){
            if(indexdept>0){
            String validQ="select vnm from gt_srch_rslt where "+deptPrp+"='96-UP-GIA'";
               rsLst = db.execSqlLst("validQ", validQ, new ArrayList());
              stmt = (PreparedStatement)rsLst.get(0);
              rs = (ResultSet)rsLst.get(1);
            while(rs.next()) {
            vnmImgLst.add(util.nvl(rs.getString("vnm")))  ; 
            }
            rs.close();
            stmt.close();
            }
                if(indexstkctg>0){
                String validQ="select vnm from gt_srch_rslt where nvl("+stkctgPrp+",'NA')='NA'";
                  rsLst = db.execSqlLst("validQ", validQ, new ArrayList());
                  stmt = (PreparedStatement)rsLst.get(0);
                  rs = (ResultSet)rsLst.get(1);
                while(rs.next()) {
                vnmStkCtgLst.add(util.nvl(rs.getString("vnm")))  ; 
                }
                rs.close();
                stmt.close();
                }
            }
            if(prcisstt.equals("MKT_GEN_IS"))
            req.setAttribute("generate", "Y");   
          int accessidn=util.updAccessLog(req,res,"Pricing Return", "Pricing Return");
          req.setAttribute("accessidn", String.valueOf(accessidn));
          req.setAttribute("totalMap", totals);
        }
        if(fileDataMap!=null && fileDataMap.size()>0)
        setUpload(req,priceRtnForm,fileDataMap);
        req.setAttribute("view", "Y");
        priceRtnForm.setValue("prcId", mprcIdn);
        priceRtnForm.setValue("empId", empId);
        priceRtnForm.setValue("packetId", stoneId);
        priceRtnForm.setValue("issueIdn", issueId);
        priceRtnForm.setValue("pri_yn", pri_yn);
        req.setAttribute("prcId", mprcIdn);
        req.setAttribute("vnmImgLst", vnmImgLst);
        req.setAttribute("vnmStkCtgLst", vnmStkCtgLst);
        req.setAttribute("prcisstt", prcisstt);
        session.setAttribute("pktList", pktList);

          util.updAccessLog(req,res,"PriceRtn", "fetch end");
        return am.findForward("load");
        }
    }
    public void setUpload(HttpServletRequest req,PriceRtnForm  priceRtnForm,HashMap fileData) {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm()); 
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        if(fileData.size() > 0) {
            String getGT = "select stk_idn idn, vnm, rap_rte,  quot, nvl(rap_dis, '') rap_dis from gt_srch_rslt" ;
            ArrayList rsLst = db.execSqlLst(" gt vnm", getGT, new ArrayList());
            PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
              try {
                  while(rs.next()) {
                      String idn = rs.getString("idn");
                      String vnm = rs.getString("vnm");
                      int rapRte = rs.getInt("rap_rte");
                      double quot = rs.getDouble("quot");
                      String rapDis = rs.getString("rap_dis");
                      
                      if(fileData.get(vnm) != null) {
                          HashMap reqData = (HashMap)fileData.get(vnm);
                          String dis = util.nvl((String)reqData.get("dis")).trim();
                          String prc = util.nvl((String)reqData.get("prc")).trim();
                          String mfg_prifrmfile = util.nvl((String)reqData.get("mfg_pri")).trim();
                          double fQuot = quot ;
                          String fDis = rapDis ;
                          String mfg_rte="";
                          if(prc.length() > 0) {
                              fQuot = Double.parseDouble(prc) ;    
                              if(rapRte > 1)
                              fDis = String.valueOf(((fQuot/rapRte*100)- 100));
                          }
                          if(dis.length() > 0) {
                              fQuot = get2Decimal(rapRte * (100 - Double.parseDouble(dis))/100);
                              fDis = String.valueOf(dis);
                          }
                          String color="";
                          double percent = ((quot * Integer.parseInt("10"))/100);
                          double diff = quot - fQuot;
                          double minPrc = fQuot - percent;
                          if(diff > percent){
                              color="red";   
                          }
                           priceRtnForm.setValue("mnl_"+idn, String.valueOf(fQuot));
                           priceRtnForm.setValue("modDis_"+idn, fDis);
                           priceRtnForm.setValue("DAMT_"+idn, String.valueOf(diff));
                           if(cnt.equals("hk")){
                           if(mfg_prifrmfile.equals("")){
                          try{
                          Double.parseDouble(dis);
                          String get_mnj_pri = "select PRC.GET_MNJ_PRI(? , ?) from dual";
                          ArrayList params = new ArrayList();
                          params.add(idn);
                          params.add(fDis);
                              ResultSet rs1 = db.execSql("mnj_pri", get_mnj_pri, params);
                              while(rs1.next()){
                              mfg_rte=util.nvl((String)rs1.getString(1));
                              }
                           rs1.close();
                           } catch(NumberFormatException nfe){  
                           } 
                           if(!mfg_rte.equals(""))
                           priceRtnForm.setValue("MFG_PRI_"+idn, get2Decimal(rapRte * (100 + Double.parseDouble(mfg_rte))/100));
                           }else{
                               priceRtnForm.setValue("MFG_PRI_"+idn,mfg_prifrmfile);
                           }
                           }
                          
                           
                      }
                      
                  }
                  rs.close();
                  stmt.close();
              } catch (SQLException e) {
              }
          }
    }
    
    public double get2Decimal(double val) {
      DecimalFormat df = new  DecimalFormat ("0.##");
      String d = df.format (val);
      System.out.println ("\tformatted: " + d);
      d = d.replaceAll (",", ".");
      Double dbl = new Double (d);
      return dbl.doubleValue ();
    }
    public HashMap getdatafromFile(HttpServletRequest req,FormFile uploadFile,DBUtil util,InfoMgr info)throws Exception{
        int fnCnt = 4;
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("PRI_RETURN");
        ArrayList pageList= ((ArrayList)pageDtl.get("FNCNT") == null)?new ArrayList():(ArrayList)pageDtl.get("FNCNT");
         if(pageList!=null && pageList.size() ==1){
            HashMap pageDtlMap=(HashMap)pageList.get(0);
             fnCnt = Integer.parseInt((String)pageDtlMap.get("dflt_val"));
         }
                  
    String fileName = uploadFile.getFileName();
    fileName = fileName.replaceAll(".csv", util.getToDteTime()+".csv");
    HashMap fileDataMap = new HashMap();
    String srchRefVal="";
    if(!fileName.equals("")){
    String fileTyp = uploadFile.getContentType();
    String path = getServlet().getServletContext().getRealPath("/") + fileName;
    File readFile = new File(path);
    if(!readFile.exists()){

    FileOutputStream fileOutStream = new FileOutputStream(readFile);

    fileOutStream.write(uploadFile.getFileData());

    fileOutStream.flush();

    fileOutStream.close();

    }

    FileReader fileReader = new FileReader(readFile);
    LineNumberReader lnr = new LineNumberReader(fileReader);
    String line = "";
    String stoneId = "";
    String reportNo = "";
    ArrayList fileFields = new ArrayList();
    fileFields.add("vnm");
    fileFields.add("prc");
    fileFields.add("dis");
    fileFields.add("mfg_pri");

    while ((line = lnr.readLine()) != null){
    String vnm = "";
    String[] vals = line.split(",");
    HashMap fileData = new HashMap();
    int lmt = 4 ;
    boolean valid=true;
    if (vals.length < 4)
    lmt = vals.length;
    for(int i=0; i < lmt; i++) {
    fileData.put((String)fileFields.get(i), vals[i]) ;
    if(i==0) {
    vnm = util.nvl((String)vals[i]);
    if(!vnm.equals(""))
    srchRefVal = srchRefVal+","+vnm;
    else
    valid=false;
    }
    }
    if(fileData.size() > 0 &&fileData.size() < fnCnt && valid) {
    for(int i=fileData.size() - 1; i < fnCnt; i++) {
    fileData.put((String)fileFields.get(i),"");
    }
    }
    fileDataMap.put(vnm, fileData);
    /*
    * Code by SK (Live as on 08Mar
    * Modified with the above code by PC for accepting price as well
    StringTokenizer vals = new StringTokenizer(line, ",");
    ArrayList dataLst = new ArrayList();
    while(vals.hasMoreTokens()) {
    dataLst.add(vals.nextToken());
    }
    srchRef = "vnm";
    for(int j=0;j<dataLst.size();j++ ){
    srchRefVal = srchRefVal+","+dataLst.get(j);
    }
    */
    }
    fileReader.close();
    }
    req.setAttribute("srchRefVal",srchRefVal);
    return fileDataMap;
    }

  public ActionForward stockUpd(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
         util.updAccessLog(req,res,"PriceRtn", "stock upd");
      PriceRtnForm  priceRtnForm = (PriceRtnForm)form;
           HashMap dbinfo = info.getDmbsInfoLst();
           String cnt = (String)dbinfo.get("CNT");
           String adv_pri = util.nvl((String)dbinfo.get("ADV_PRI"),"N");
      String prcId= util.nvl((String)priceRtnForm.getValue("prcId"));
      String issId = util.nvl(req.getParameter("issIdn"));
      String mstkIdn = util.nvl(req.getParameter("mstkIdn"));
      String lastpage = util.nvl(req.getParameter("lastpage"));
      String currentpage = util.nvl(req.getParameter("currentpage"));
      String findVnm = util.nvl(req.getParameter("findVnm"));
      String stt = util.nvl(req.getParameter("stt"));
      HashMap params = new HashMap();
       String vnm="";
       if(!currentpage.equals("") && findVnm.equals("")){
           HashMap stockList = (HashMap)session.getAttribute("pktList");  
         ArrayList pktStkIdnList = (ArrayList)session.getAttribute("pktStkIdnList");
          mstkIdn = (String)pktStkIdnList.get(Integer.parseInt(currentpage));
         HashMap pktData = (HashMap)stockList.get(mstkIdn);
         vnm = util.nvl((String)pktData.get("vnm"));
         stt = util.nvl((String)pktData.get("stt1"));
         issId = util.nvl((String)pktData.get("iss_idn"),issId);
       }
         
      if(!findVnm.equals("")){
          HashMap stockList = (HashMap)session.getAttribute("pktList");
          ArrayList pktStkIdnList = (ArrayList)session.getAttribute("pktStkIdnList");
          String fromIdn = util.nvl((String)util.getMstkIdn(findVnm));
          if(!fromIdn.equals("")){
              int indexOfIdn=pktStkIdnList.indexOf(fromIdn);
              if(indexOfIdn >= 0){
              mstkIdn = (String)pktStkIdnList.get(indexOfIdn);
              HashMap pktData = (HashMap)stockList.get(mstkIdn);
              vnm = util.nvl((String)pktData.get("vnm"));
              stt = util.nvl((String)pktData.get("stt1"));
              issId = util.nvl((String)pktData.get("iss_idn"),issId);
              currentpage=String.valueOf(indexOfIdn);
              }
          }else{
              mstkIdn = (String)pktStkIdnList.get(Integer.parseInt(currentpage));
              HashMap pktData = (HashMap)stockList.get(mstkIdn);
              vnm = util.nvl((String)pktData.get("vnm"));
              stt = util.nvl((String)pktData.get("stt1"));
              issId = util.nvl((String)pktData.get("iss_idn"),issId); 
          }
      }
         
      params.put("prcId", prcId);
      params.put("issId", issId);
      params.put("mstkIdn", mstkIdn);  
      ArrayList stockPrpList = StockUpdPrp(req, priceRtnForm , params );
       String isRepair = util.nvl(req.getParameter("isRepair"));
       ArrayList ary = new ArrayList();
               if(isRepair.equals("Y")){
                   int prcChkIdn = 0 ;
                   String newmstkIdn="";
                   String getPrcChkIdn = " select prc_chk_pkg.get_idn from dual ";
                 ArrayList rsLst = db.execSqlLst(" Prc Chk Idn", getPrcChkIdn, new ArrayList());
                 PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
                 ResultSet rs = (ResultSet)rsLst.get(1);
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
                   priceRtnForm.setValue("stkIdn", newmstkIdn);
               }
       if(stt.equals("PRI_FN_IS") && cnt.equals("hk")){
           if(!adv_pri.equals("Y")){
           ArrayList grpList=util.sheetDtl(mstkIdn);
           String getmfgpri=util.GET_MFG_PRI(grpList);
           req.setAttribute("grpSheetList", grpList);
           String cmp="",fa_pri="";
           float raprte=0,mfg_rte=0;
           String getPrcChkIdn = "select trunc(ms.rap_rte*(100 + "+getmfgpri+")/100,2) mfg_rte,ms.rap_rte,cmp.num cmp, fapri.num fa_pri\n" + 
           "from mstk ms,stk_dtl cmp,stk_dtl fapri\n" + 
           "where ms.idn= ? and\n" + 
           "ms.idn=cmp.mstk_idn and cmp.grp=1 and cmp.mprp='CMP_RTE' and \n" + 
           "ms.idn=fapri.mstk_idn and fapri.grp=1 and fapri.mprp='FA_PRI'";
           ary = new ArrayList();
           ary.add(mstkIdn);
             ArrayList  outLst = db.execSqlLst("CMP_RTE", getPrcChkIdn, ary);
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
           while(rs.next()) {
               cmp=util.nvl((String)rs.getString("cmp")); 
               fa_pri=util.nvl((String)rs.getString("fa_pri")); 
               mfg_rte = rs.getFloat("mfg_rte");
               raprte = rs.getFloat("rap_rte");
           }
           rs.close();
           pst.close();
           
//           ary = new ArrayList();
//           ary.add(sh);
//           ary.add(sz);
//           ary.add(co);
//           ary.add(clr);
//           ArrayList out = new ArrayList();
//           out.add("I");
//           out.add("I");
//           CallableStatement cst = db.execCall(
//               "GET_NR_RTE ",
//               "PRC.GET_NR_RTE(pShp => ?, pSz => ?, pCol => ?, pClr => ?, pRte => ?, pRapRte => ?)", ary, out);
//           mfg_rte = cst.getInt(ary.size()+1);
//           raprte = cst.getInt(ary.size()+2);
//           cst.close();
           
           priceRtnForm.setValue("CMP_RTE_FIX", cmp);
           priceRtnForm.setValue("FA_PRI_FIX", fa_pri);
           priceRtnForm.setValue("MFG_RTE_FIX", mfg_rte);
           priceRtnForm.setValue("RAP_RTE_FIX", raprte);
           }else{
                   int getmfgpri=0;
                   int getcmp=0;
                   String sheetappliedCmp="";
                   String sheetappliedmfg="";
                   ary = new ArrayList();
                   ary.add(mstkIdn);
                   ary.add("NR");
                   ArrayList out = new ArrayList();
                   out.add("I");
                   out.add("I");
                   out.add("V");
                   out.add("V");
                   out.add("I");
                   CallableStatement cst = null;
                   cst = db.execCall("dpp_pri.AryPri","dpp_pri.AryPri(pIdn =>?, pTyp =>?, NrmDis =>?, MfgDis => ?, StrDis =>?, StrMfg=>?,pRap =>?)", ary, out);
                   getcmp = cst.getInt(ary.size()+1);
                   getmfgpri = cst.getInt(ary.size()+2);
                   sheetappliedCmp = cst.getString(ary.size()+3);
                   sheetappliedmfg = cst.getString(ary.size()+4);
                   
                 cst.close();
                 cst=null;
                   String sh="",sz="",co="",clr="",cmp="",fa_pri="";
                   float raprte=0,mfg_rte=0;
                   
                   String getPrcChkIdn = "select trunc(ms.rap_rte*(100 + "+getmfgpri+")/100,2) mfg_rte,ms.rap_rte,cmp.num cmp, fapri.num fa_pri\n" + 
                   "from mstk ms,stk_dtl cmp,stk_dtl fapri\n" + 
                   "where ms.idn= ? and\n" + 
                   "ms.idn=cmp.mstk_idn and cmp.grp=1 and cmp.mprp='CMP_RTE' and \n" + 
                   "ms.idn=fapri.mstk_idn and fapri.grp=1 and fapri.mprp='FA_PRI'";
                   ary = new ArrayList();
                   ary.add(mstkIdn);
                 ArrayList  outLst = db.execSqlLst("CMP_RTE", getPrcChkIdn, ary);
                 PreparedStatement pst = (PreparedStatement)outLst.get(0);
                 ResultSet rs = (ResultSet)outLst.get(1);
                   while(rs.next()) {
                       cmp=util.nvl((String)rs.getString("cmp")); 
                       fa_pri=util.nvl((String)rs.getString("fa_pri")); 
                       mfg_rte = rs.getFloat("mfg_rte");
                       raprte = rs.getFloat("rap_rte");
                   }
                   rs.close();
                 pst.close();
                   priceRtnForm.setValue("CMP_RTE_FIX", cmp);
                   priceRtnForm.setValue("FA_PRI_FIX", fa_pri);
                   priceRtnForm.setValue("MFG_RTE_FIX", mfg_rte);
                   priceRtnForm.setValue("RAP_RTE_FIX", raprte);
                   priceRtnForm.setValue("APPLIED_MFG_SHEET", sheetappliedmfg);
               }
       }
               
       if(isRepair.equals("Y"))
       session.setAttribute("ISREPAIR", "Y");
       else
       session.setAttribute("ISREPAIR", "N");
       
      req.setAttribute("StockList", stockPrpList);
      priceRtnForm.setValue("prcId", prcId);
      priceRtnForm.setValue("issIdn", issId);
      priceRtnForm.setValue("mstkIdn", mstkIdn);
      priceRtnForm.setValue("lastpage", lastpage);
      priceRtnForm.setValue("isRepair", isRepair);
      priceRtnForm.setValue("applyYN", stt);
      priceRtnForm.setValue("vnm", vnm);
      priceRtnForm.setValue("stt", stt);
      priceRtnForm.setValue("currentpage", currentpage);
      req.setAttribute("mstkIdn", mstkIdn);
   
      priceRtnForm.setValue("url", "pricertn.do?method=stockUpd&mstkIdn="+mstkIdn+"&issIdn="+issId+"&stt="+stt+"&isRepair="+isRepair);
      req.setAttribute("PopUpidn", mstkIdn);
         util.updAccessLog(req,res,"PriceRtn", "stock updend");
      return am.findForward("loadStock");
       }
   }
  
  public ActionForward savePrpUpd(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"PriceRtn", "savePrpUpd");
     PriceRtnForm  priceRtnForm = (PriceRtnForm)form;
      String prcId= (String) priceRtnForm.getValue("prcId");
      String issId = (String) priceRtnForm.getValue("issIdn");
      String mstkIdn = (String) priceRtnForm.getValue("mstkIdn");
      String vnm="";
      HashMap mprpLst = info.getMprp();
      HashMap dbinfo = info.getDmbsInfoLst();
      String cnt = (String)dbinfo.get("CNT");
      String adv_pri = util.nvl((String)dbinfo.get("ADV_PRI"),"N");
      ArrayList ary = new ArrayList();
      HashMap mprp = info.getMprp();
      String lastpage = util.nvl(req.getParameter("lastpage"));
      String currentpage = util.nvl(req.getParameter("currentpage"));
      HashMap stockList = (HashMap)session.getAttribute("pktList");
      String stt= (String)priceRtnForm.getValue("stt");  
      if(lastpage.equals("") && currentpage.equals("")){
          lastpage = util.nvl((String) priceRtnForm.getValue("lastpage"));
          currentpage = util.nvl((String) priceRtnForm.getValue("currentpage"));
          vnm=util.nvl((String) priceRtnForm.getValue("vnm"));
      }
      String apply=util.nvl((String) priceRtnForm.getValue("apply"));
      ArrayList assortPrpUpd = (ArrayList)session.getAttribute("assortUpdPrp");
      int ct =0;
      for(int i=0 ; i <assortPrpUpd.size();i++){
          
          String lprp = (String)assortPrpUpd.get(i);
          String lprpTyp = util.nvl((String)mprpLst.get(lprp+"T"));
          String fldVal =util.nvl((String) priceRtnForm.getValue(lprp));
         // String repVal=util.nvl((String) priceRtnForm.getValue("RP_"+lprp),"NA");
          if(!fldVal.equals("") && !fldVal.equals("0")){
          ary = new ArrayList();
          ary.add(issId);
          ary.add(mstkIdn);
          ary.add(lprp);
          ary.add(fldVal);
        //  ary.add(repVal);
          ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
         
       }
          
      }
          ArrayList prcPrpUpd = (ArrayList)session.getAttribute("prcPrpList");
          String isRepair = util.nvl((String)session.getAttribute("ISREPAIR"));
          if(isRepair.equals("Y")){
          for(int i=0 ; i < prcPrpUpd.size();i++){
              
              String lprp = (String)prcPrpUpd.get(i);
              String repVal =util.nvl((String)priceRtnForm.getValue("RP_"+lprp),"NA");
              String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
              if(lprpTyp.equals("N") && repVal.equals("NA"))
              repVal="";
              if(!repVal.equals("") && !repVal.equals("0")){
              ary = new ArrayList();
              ary.add(issId);
              ary.add(mstkIdn);
              ary.add(lprp);
              ary.add(repVal);
             ct = db.execCall("updateRepPrp", "ISS_RTN_PKG.REP_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pRepVal => ? )", ary);
                  
              }
            
          }}
        if(!apply.equals("")){
        ary = new ArrayList();
        ary.add(issId);
        ary.add(mstkIdn);
        ct = db.execCall("apply_rtn_prp", "iss_rtn_pkg.apply_rtn_prp(?, ?, 'MOD')", ary);  
        
        ary = new ArrayList();
        ary.add(mstkIdn);
        ct = db.execCall("apply_rtn_prp", "STK_SRT(?)", ary);
        ary = new ArrayList();
        ary.add(mstkIdn);
        ct = db.execCall("apply_rtn_prp", "STK_RAP_UPD(?)", ary);
        ary = new ArrayList();
        ary.add(mstkIdn);
        ct = db.execCall("apply_rtn_prp", "DP_UPD_MEAS(?)", ary);
        ary = new ArrayList();
        ary.add(mstkIdn);
        ct = db.execCall("apply_rtn_prp", "DP_UPD_LWR(?)", ary);
            if(!adv_pri.equals("Y")){
                ary = new ArrayList();
                ary.add(mstkIdn);
                ct = db.execCall("Itm_pri", "PRC.Itm_pri(?)", ary);
            }
        }
            if(stt.equals("PRI_FN_IS") && cnt.equals("hk")){
            if(!adv_pri.equals("Y")){
            ArrayList grpList=util.sheetDtl(mstkIdn);
            String getmfgpri=util.GET_MFG_PRI(grpList);
            req.setAttribute("grpSheetList", grpList);
            String sh="",sz="",co="",clr="",cmp="",fa_pri="";
            float raprte=0,mfg_rte=0;
            String getPrcChkIdn = "select trunc(ms.rap_rte*(100 + "+getmfgpri+")/100,2) mfg_rte,ms.rap_rte,cmp.num cmp, fapri.num fa_pri\n" + 
            "from mstk ms,stk_dtl cmp,stk_dtl fapri\n" + 
            "where ms.idn= ? and\n" + 
            "ms.idn=cmp.mstk_idn and cmp.grp=1 and cmp.mprp='CMP_RTE' and \n" + 
            "ms.idn=fapri.mstk_idn and fapri.grp=1 and fapri.mprp='FA_PRI'";
            ary = new ArrayList();
            ary.add(mstkIdn);
              ArrayList rsLst = db.execSqlLst("CMP_RTE", getPrcChkIdn, ary);
              PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
              ResultSet rs = (ResultSet)rsLst.get(1);
            while(rs.next()) {
                cmp=util.nvl((String)rs.getString("cmp")); 
                mfg_rte = rs.getFloat("mfg_rte");
                raprte = rs.getFloat("rap_rte");
                fa_pri=util.nvl((String)rs.getString("fa_pri")); 
            }
            rs.close();
            stmt.close();
            
//            ary = new ArrayList();
//            ary.add(sh);
//            ary.add(sz);
//            ary.add(co);
//            ary.add(clr);
//            ArrayList out = new ArrayList();
//            out.add("I");
//            out.add("I");
//            CallableStatement cst = db.execCall(
//                "GET_NR_RTE ",
//                "PRC.GET_NR_RTE(pShp => ?, pSz => ?, pCol => ?, pClr => ?, pRte => ?, pRapRte => ?)", ary, out);
//            mfg_rte = cst.getInt(ary.size()+1);
//            raprte = cst.getInt(ary.size()+2);
//            cst.close();
            
            priceRtnForm.setValue("CMP_RTE_FIX", cmp);
            priceRtnForm.setValue("FA_PRI_FIX", fa_pri);
            priceRtnForm.setValue("MFG_RTE_FIX", mfg_rte);
            priceRtnForm.setValue("RAP_RTE_FIX", raprte);
            }else{
                                     int getmfgpri=0;
                                     int getcmp=0;
                                     int pkt_rap_rte=0;
                                     String sheetappliedCmp="";
                                     String sheetappliedmfg="";
                                     String sh="",sz="",co="",clr="",cmp="",fa_pri="";
                                     float raprte=0,mfg_rte=0;
                                     ary = new ArrayList();
                                     ary.add(mstkIdn);
                                     ary.add("NR");
                                     ArrayList out = new ArrayList();
                                     out.add("I");
                                     out.add("I");
                                     out.add("V");
                                     out.add("V");
                                     out.add("I");
                                     CallableStatement cst = null;
                                     cst = db.execCall("dpp_pri.AryPri","dpp_pri.AryPri(pIdn =>?, pTyp =>?, NrmDis =>?, MfgDis => ?, StrDis =>?, StrMfg=>?,pRap =>?)", ary, out);
                                     getcmp = cst.getInt(ary.size()+1);
                                     getmfgpri = cst.getInt(ary.size()+2);
                                     sheetappliedCmp = cst.getString(ary.size()+3);
                                     sheetappliedmfg = cst.getString(ary.size()+4);
                                     pkt_rap_rte = cst.getInt(ary.size()+5);
                                   cst.close();
                                   cst=null;
                                     
                                     ary = new ArrayList();
                                     ary.add(mstkIdn);
                                     ary.add("CMP_RTE");
                                     ary.add(String.valueOf(pkt_rap_rte));
                                     ary.add(String.valueOf(getcmp));
                                     String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> Ceil(?*(100+?)/100) ,pPrpTyp => 'N', pgrp => 1 )";
                                     ct = db.execCall("stockUpd",stockUpd, ary);
                                     
                                     ary = new ArrayList();
                                     ary.add(mstkIdn);
                                     ary.add("CMP_DIS");
                                     ary.add(String.valueOf(getcmp));
                                     stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? ,pPrpTyp => 'N', pgrp => 1 )";
                                     ct = db.execCall("stockUpd",stockUpd, ary);
                                     
                                     String getPrcChkIdn = "select trunc(ms.rap_rte*(100 + "+getmfgpri+")/100,2) mfg_rte,ms.rap_rte,cmp.num cmp, fapri.num fa_pri\n" + 
                                     "from mstk ms,stk_dtl cmp,stk_dtl fapri\n" + 
                                     "where ms.idn= ? and\n" + 
                                     "ms.idn=cmp.mstk_idn and cmp.grp=1 and cmp.mprp='CMP_RTE' and \n" + 
                                     "ms.idn=fapri.mstk_idn and fapri.grp=1 and fapri.mprp='FA_PRI'";
                                     ary = new ArrayList();
                                     ary.add(mstkIdn);
                                   ArrayList rsLst = db.execSqlLst("CMP_RTE", getPrcChkIdn, ary);
                                   PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
                                   ResultSet rs = (ResultSet)rsLst.get(1);
                                     while(rs.next()) {
                                         cmp=util.nvl((String)rs.getString("cmp")); 
                                         fa_pri=util.nvl((String)rs.getString("fa_pri")); 
                                         mfg_rte = rs.getFloat("mfg_rte");
                                         raprte = rs.getFloat("rap_rte");
                                     }
                                     rs.close();
                                    stmt.close();
                                     priceRtnForm.setValue("CMP_RTE_FIX", cmp);
                                     priceRtnForm.setValue("FA_PRI_FIX", fa_pri);
                                     priceRtnForm.setValue("MFG_RTE_FIX", mfg_rte);
                                     priceRtnForm.setValue("RAP_RTE_FIX", raprte);
                                     priceRtnForm.setValue("APPLIED_MFG_SHEET", sheetappliedmfg);
                                 }
        }
          
      ary = new ArrayList();
      ary.add(issId);
      ary.add(mstkIdn);
      ct = db.execUpd("update iss_rtn_dtl", "update iss_rtn_dtl set lot_id='Y' where iss_id=? and iss_stk_idn=?", ary) ;
      HashMap pktData = (HashMap)stockList.get(mstkIdn);
      pktData.put("rtnprpupdated", "Y");
      stockList.put(mstkIdn, pktData);
        
      session.setAttribute("pktList",stockList);
      HashMap params = new HashMap();
      params.put("prcId", prcId);
      params.put("issId", issId);
      params.put("mstkIdn", mstkIdn);
      req.setAttribute("PopUpidn", mstkIdn);
       req.setAttribute("mstkIdn", mstkIdn);
       priceRtnForm.setValue("vnm", vnm);
          priceRtnForm.setValue("stt", stt);
       priceRtnForm.setValue("lastpage", lastpage);
       priceRtnForm.setValue("currentpage", currentpage);
          priceRtnForm.setValue("isRepair", isRepair);
     
    priceRtnForm.setValue("url", "pricertn.do?method=stockUpd&mstkIdn="+mstkIdn+"&issIdn="+issId+"&stt="+stt+"&isRepair="+isRepair);
      ArrayList stockPrpList = StockUpdPrp(req, priceRtnForm , params );
      req.setAttribute( "msg","Propeties Get update successfully");
      req.setAttribute("StockList", stockPrpList);
        util.updAccessLog(req,res,"PriceRtn", "savePrpUpEnd");
      return am.findForward("loadStock");
      }
  }
  public ArrayList StockUpdPrp(HttpServletRequest req, PriceRtnForm form ,HashMap params){
      
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
      String mstkIdn = (String)params.get("mstkIdn");
      ArrayList stockPrpList = new ArrayList();
      ArrayList updatePrpList = new ArrayList();
      ArrayList prcPrpList = new ArrayList();
      ArrayList prcPrpIssueEditList=new ArrayList();
      HashMap mprp = info.getMprp();
    String stockPrp = "select a.mprp ,a.iss_stk_idn, b.flg" +
                      ",  Decode(C.Dta_Typ, 'C', A.Iss_Val, 'N', A.Iss_Num ,'D',format_to_date(a.txt), A.Txt) iss_val,\n" + 
                      " Decode(C.Dta_Typ, 'C', nvl(A.rtn_Val,A.Iss_Val), 'N', A.rtn_Num ,'D',format_to_date(a.txt), A.Txt) rtn_val ,  "+
                       "  Decode(C.Dta_Typ, 'C', A.rep_Val, 'N', A.rep_Num ,'D',format_to_date(a.rep_txt), A.rep_Txt) rep_val "+
    
        //", a.iss_val, decode(b.flg, 'COMP', a.rtn_val, nvl(a.rtn_val, a.iss_val)) rtn_val " +
        //", a.iss_num , decode(b.flg, 'COMP', a.rtn_num, nvl(a.rtn_num, a.iss_num)) rtn_num , a.txt " +
        " from iss_rtn_prp a , prc_prp_alw b , mprp c where  a.mprp = b.mprp and b.mprp=c.prp and " +
                      "a.iss_id=? and a.iss_stk_idn = ? and b.flg not in ('ISSEDIT') and b.prc_idn =? order by b.srt ";

      ArrayList ary = new ArrayList();
      ary.add(params.get("issId"));
      ary.add(params.get("mstkIdn"));
      ary.add(params.get("prcId"));
    ArrayList rsLst = db.execSqlLst("stockDtl", stockPrp, ary);
    PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
    ResultSet rs = (ResultSet)rsLst.get(1);
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
              if(!flg.equals("FTCH"))
              prcPrpIssueEditList.add(lprp);
              prcPrpList.add(lprp);
          }
          
          rs.close();
          stmt.close();
      } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
      }
      
      req.setAttribute("prcPrpIssueEditList", prcPrpIssueEditList);
      session.setAttribute("assortUpdPrp", updatePrpList);
      session.setAttribute("prcPrpList", prcPrpList);
     
      return stockPrpList;
  }
  
  
    public ActionForward priRtn(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"PriceRtn", "pri Rtn");
       PriceRtnForm  priceRtnForm = (PriceRtnForm)form;
        String mprcIdn = util.nvl((String)priceRtnForm.getValue("prcId"));
        String stoneId = util.nvl((String)priceRtnForm.getValue("packetId"));
        String empId = util.nvl((String)priceRtnForm.getValue("empId"));
        String issueId = util.nvl((String)priceRtnForm.getValue("issueIdn"));
        ArrayList pktStkIdnList = (ArrayList)session.getAttribute("pktStkIdnList");
        HashMap pktList = (HashMap)session.getAttribute("pktList");
        ArrayList prpList = (ArrayList)session.getAttribute("labSVCPrpList");
        String generate = util.nvl((String)priceRtnForm.getValue("generate"));
        String premktrpt = util.nvl((String)req.getParameter("premktrpt"));
        String reprc = util.nvl((String)priceRtnForm.getValue("reprc"));
        ArrayList returnPkt = new ArrayList();
        ArrayList RtnMsgList = new ArrayList();
        HashMap dbinfo = info.getDmbsInfoLst();
        String sqlDb = (String)dbinfo.get("CNT");
        String threadStt="";
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("PRI_RETURN");
            ArrayList pageList= ((ArrayList)pageDtl.get("THREAD_STT") == null)?new ArrayList():(ArrayList)pageDtl.get("THREAD_STT");
             if(pageList!=null && pageList.size() > 0){
                HashMap pageDtlMap=(HashMap)pageList.get(0);
                threadStt =util.nvl(util.nvl((String)pageDtlMap.get("dflt_val")));
             }
        prpList.add("RTE");
        prpList.add("RAP_DIS");
        int ctv=0;
        String msg = "";
        ArrayList ary = new ArrayList();
        ArrayList stkIdnList=new ArrayList();
        int cnt = 0;
        if(!premktrpt.equals("")){
           return loadPreMktRpt(am, form, req, res);
        }else if(generate.equals("")){
        String insQ = " insert into gt_pkt(mstk_idn) select ? from dual ";
        for(int m=0;m< pktStkIdnList.size();m++){ 
        String stkIdn = (String)pktStkIdnList.get(m);
            String chk=util.nvl((String)priceRtnForm.getValue("check_"+stkIdn));
            if(chk.equals("Y")){
                HashMap pktData = (HashMap)pktList.get(stkIdn); 
                String issidn = (String)pktData.get("iss_idn");
                if(reprc.equals("")){
                String lStkStt = util.nvl((String)priceRtnForm.getValue("STT_"+stkIdn), "NA");
                returnPkt.add(stkIdn);
                ary = new ArrayList();
                for(int j=0 ; j< prpList.size() ; j++){
                String lprp = (String)prpList.get(j);   
                String fldVal =util.nvl((String)priceRtnForm.getValue(lprp+"_"+stkIdn));
                if(lprp.equals("RTE"))
                fldVal=util.nvl((String)priceRtnForm.getValue("mnl_"+stkIdn),"0");
                else if(lprp.equals("RAP_DIS"))
                fldVal=util.nvl((String)priceRtnForm.getValue("modDis_"+stkIdn),"0");  
                if(!fldVal.equals("") && !fldVal.equals("0")){
                 ary = new ArrayList();
                 ary.add(issidn);
                 ary.add(stkIdn);
                 ary.add(lprp);
                 ary.add(fldVal);
                ctv = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
                priceRtnForm.setValue(lprp, "");
                }}
                System.out.println("pktUpd:---"+ctv);
                ArrayList RtnMsg = new ArrayList();
                returnPkt.add(stkIdn);
                ary = new ArrayList();
                ary.add(issidn);
                ary.add(stkIdn);
                ary.add("RT");
                ary.add(lStkStt);
                ArrayList out = new ArrayList();
                out.add("I");
                out.add("V");
                String issuePkt = "ISS_RTN_PKG.RTN_PKT(pIssId => ?, pStkIdn => ? , pStt => ? , pStkStt => ? , pCnt=>? , pMsg => ? )";
                CallableStatement ct = db.execCall("issue Rtn", issuePkt, ary, out);
                cnt = ct.getInt(ary.size()+1);
                msg = ct.getString(ary.size()+2);
                RtnMsg.add(cnt);
                RtnMsg.add(msg);
                ct.close();
                }else{
                    ary = new ArrayList();
                    ary.add(issidn);
                    ary.add(stkIdn);
                    ctv = db.execCall("apply_rtn_prp", "iss_rtn_pkg.apply_rtn_prp(?, ?, 'MOD')", ary);
                    
                    ary = new ArrayList();
                    ary.add(stkIdn);
                    ctv = db.execUpd("reprc", insQ, ary);
                }
                if(!stkIdnList.contains(stkIdn))
                    stkIdnList.add(stkIdn);
            }
        }
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
            
            if(stkIdnList.size()>0){
                HashMap prcBeansList = (HashMap)session.getAttribute("mprcBean");
                Mprc mprcDto = (Mprc)prcBeansList.get(mprcIdn);
                String outStt = mprcDto.getOut_stt();
                if(threadStt.indexOf(outStt) >= 0){
                    new GetPktItempri(sqlDb,stkIdnList).start();
                }
            }
        }else{
         int ct = db.execCall("DP_GEN_ALT_VNM", "DP_GEN_ALT_VNM", new ArrayList());   
         priceRtnForm.reset();
         priceRtnForm.setValue("mprcIdn", mprcIdn);
         priceRtnForm.setValue("empIdn", empId);
         priceRtnForm.setValue("vnmLst", stoneId);
         priceRtnForm.setValue("issueId", issueId);
         return fecth(am,priceRtnForm,req,res);  
         }
        if(returnPkt.size()>0)
            req.setAttribute("msgList",returnPkt.toString()+" Return successfully..");
        priceRtnForm.reset();
          util.updAccessLog(req,res,"PriceRtn", "priRtnEnd");
        return am.findForward("load");
        }
    }
    
    public ActionForward loadPreMktRpt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
              util.updAccessLog(req,res,"PriceRtn", "pktPtint");
              PriceRtnForm  priceRtnForm = (PriceRtnForm)af;
              String delQ = " Delete from mkt_prc ";
              int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
              ArrayList ary = new ArrayList();
              HashMap dbinfo = info.getDmbsInfoLst();
              ArrayList pktStkIdnList = (ArrayList)session.getAttribute("pktStkIdnList");
              HashMap pktList = (HashMap)session.getAttribute("pktList");
              String accessidn = util.nvl(req.getParameter("accessidn"));
              String cnt = (String)dbinfo.get("CNT");
              String webUrl = (String)dbinfo.get("REP_URL");
              String reqUrl = (String)dbinfo.get("HOME_DIR");
              String repPath = (String)dbinfo.get("REP_PATH");
              String repNme = "pre_mark.jsp";
              int mkt_prc = 0;
              ResultSet rs = db.execSql("mkt_prc", "select  seq_mkt_prc.nextval from dual ", new ArrayList());
              String insertPrc = " insert into mkt_prc(prc_idn ,mstk_idn ,rep_dte) \n"+
              "select ?, ?, sysdate from dual"; 
              if(rs.next())
              mkt_prc = rs.getInt(1);
              rs.close();
              String issIdnParam="";
          
              for(int m=0;m< pktStkIdnList.size();m++){ 
                String stkIdn = (String)pktStkIdnList.get(m);
                String chk=util.nvl((String)priceRtnForm.getValue("check_"+stkIdn));
                    if(chk.equals("Y")){
                        HashMap pktData = (HashMap)pktList.get(stkIdn); 
                        String iss_idn=util.nvl((String)pktData.get("iss_idn"));
                        ary = new ArrayList();
                        ary.add(String.valueOf(mkt_prc));
                        ary.add(stkIdn);
                        ct = db.execUpd("insert mkt_prc", insertPrc, ary);
                        if(issIdnParam.equals(""))
                        issIdnParam=iss_idn;
                        else if(issIdnParam.indexOf(iss_idn) < 0)
                        issIdnParam+=","+iss_idn;
                    }
                  priceRtnForm.setValue("check_"+stkIdn,"");
              }
              if(ct>0){
                 String url = repPath+"/reports/rwservlet?"+cnt+"&report="+reqUrl+"\\reports\\"+repNme+"&P_ISS_ID="+issIdnParam+"&P_PRC_IDN="+String.valueOf(mkt_prc) ;
                  res.sendRedirect(url);    
              }
            return null;
        }
    }
    public ActionForward details(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"PriceRtn", "dtl");
       PriceRtnForm  priceRtnForm = (PriceRtnForm)form;
        String stkIdn = util.nvl(req.getParameter("mstkIdn"));
        HashMap dataTable=new HashMap();
        ArrayList pktList=new ArrayList();
        ArrayList ary=new ArrayList();
        ary.add(stkIdn);
        ary.add("PRIRTNDTL_VW");
        String dpTrf = "DP_TRF_TO_MKTG_CMP(?,?)";
        int ct = db.execCall("DP_TRF_TO_MKTG_CMP", dpTrf,ary);
        String mktQ="select count(*) cnt, trunc(sum(trunc(cts, 2)*quot)/sum(trunc(cts,2)), 2) stk_avg from gt_srch_rslt where stt = 'MKT'";
          ArrayList rsLst = db.execSqlLst("MKT DATA", mktQ, new ArrayList());
          PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
          ResultSet rs = (ResultSet)rsLst.get(1);
        while(rs.next()) {
            dataTable.put("MKT_QTY",util.nvl(rs.getString("cnt"))); 
            dataTable.put("MKT_AVG",util.nvl(rs.getString("stk_avg"))); 
        }
        rs.close();
          stmt.close();
        String soldQ="select count(*) cnt, trunc(sum(trunc(cts, 2)*quot)/sum(trunc(cts,2)), 2) stk_avg from gt_srch_rslt where stt = 'SOLD'";
         rsLst = db.execSqlLst("SOLD DATA", soldQ, new ArrayList());
          stmt = (PreparedStatement)rsLst.get(0);
          rs = (ResultSet)rsLst.get(1);
        while(rs.next()) {
            dataTable.put("SOLD_QTY",util.nvl(rs.getString("cnt"))); 
            dataTable.put("SOLD_AVG",util.nvl(rs.getString("stk_avg"))); 
        }
        rs.close();
          stmt.close();
        ArrayList vwPrpLst = PriPrpDTLViw(req,res);
        String srchQ="";
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
        String dataQ="select a.* from\n" + 
        "(select byr,stt, sl_dte, quot stk_avg,vnm "+srchQ+", rank() Over (order by quot desc) rnk\n" + 
        "from gt_srch_rslt \n" + 
        "order by sk1) a";
          rsLst = db.execSqlLst("DATA", dataQ, new ArrayList());
           stmt = (PreparedStatement)rsLst.get(0);
           rs = (ResultSet)rsLst.get(1);
        while(rs.next()) {
            HashMap pktPrpMap=new HashMap();
            pktPrpMap.put("BUYER",util.nvl(rs.getString("byr")));
            pktPrpMap.put("DTE",util.nvl(rs.getString("sl_dte")));
            pktPrpMap.put("AVG",util.nvl(rs.getString("stk_avg")));
            pktPrpMap.put("VNM",util.nvl(rs.getString("vnm")));
            pktPrpMap.put("STT",util.nvl(rs.getString("stt")));
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
            pktList.add(pktPrpMap);
        }
        rs.close();
          stmt.close();
        req.setAttribute("pktList", pktList);
        req.setAttribute("dataTable", dataTable);
          util.updAccessLog(req,res,"PriceRtn", "dtl end");
      return am.findForward("details");
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
          util.updAccessLog(req,res,"PriceRtn", "pktPtint");
              String delQ = " Delete from mkt_prc ";
              int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
              ArrayList ary = new ArrayList();
              HashMap dbinfo = info.getDmbsInfoLst();
              String accessidn = util.nvl(req.getParameter("accessidn"));
              String cnt = (String)dbinfo.get("CNT");
              String webUrl = (String)dbinfo.get("REP_URL");
              String reqUrl = (String)dbinfo.get("HOME_DIR");
               String repPath = (String)dbinfo.get("REP_PATH");
              String repNme = "pktprint_prc.rdf";
              int mkt_prc = 0;
              ResultSet rs = db.execSql("mkt_prc", "select  seq_mkt_prc.nextval from dual ", new ArrayList());
              if(rs.next())
                  mkt_prc = rs.getInt(1);
             rs.close();
              String insertPrc = " insert into mkt_prc(prc_idn ,mstk_idn ,rep_dte) select ?, stk_idn, sysdate from" +
                  " gt_srch_rslt where flg = ? ";
              ary.add(String.valueOf(mkt_prc));
              ary.add("M");
               ct = db.execUpd("insert mkt_prc", insertPrc, ary);                 
              if(ct>0){
                 String url = repPath+"/reports/rwservlet?"+cnt+"&report="+reqUrl+"\\reports\\"+repNme+"&p_access="+accessidn ;
                  res.sendRedirect(url);    
              }
            return null;
        }
    }
    public ArrayList getOptions(HttpServletRequest req ,HttpServletResponse res, String issueId){
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
    ArrayList prcStt = new ArrayList();

    String getPrcToPrc = " select a1.in_stt from mprc a1, mprc a, prc_to_prc b "+
    " where a.idn = b.prc_id and a1.idn = b.prc_to_id and a.idn = ? "+
    " order by b.srt ";

    ArrayList ary = new ArrayList();
    ary.add(issueId);

      ArrayList rsLst = db.execSqlLst(" get options", getPrcToPrc, ary);
      PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
      ResultSet rs = (ResultSet)rsLst.get(1);
    try {
    while(rs.next()){
    prcStt.add(rs.getString("in_stt"));
    //prcStt.put(rs.getString("in_stt"), rs.getString("in_stt"));
    }
        rs.close();
        stmt.close();
    } catch (SQLException e) {

    }
    return prcStt ;
    }
    
    public ArrayList getPrc(HttpServletRequest req ,HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = null;

           String grp = util.nvl(req.getParameter("grp"));
           grp=util.getVnm(grp);
           ArrayList prcList = new ArrayList();
           HashMap prcSttMap = new HashMap();
           String prcSql = "select idn, prc , in_stt , ot_stt , is_stt,pri_yn from mprc where grp in ("+grp+") and is_stt <> 'NA' and stt = ? order by srt";
           ary = new ArrayList();
         
        ary.add("A");
      ArrayList rsLst = db.execSqlLst("prcSql", prcSql, ary);
      PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
      ResultSet rs = (ResultSet)rsLst.get(1);
        try {
            while (rs.next()) {
                Mprc mprc = new Mprc();
                String mprcId = rs.getString("idn");
                mprc.setMprcId(rs.getString("idn"));
                mprc.setPrc(rs.getString("prc"));
                mprc.setIn_stt(rs.getString("in_stt"));
                mprc.setOut_stt(rs.getString("ot_stt"));
                mprc.setIs_stt(rs.getString("is_stt"));
                mprc.setPri_yn(rs.getString("pri_yn"));
                prcList.add(mprc);
                prcSttMap.put(mprcId, mprc);
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        session.setAttribute("mprcBean", prcSttMap);
        return prcList;
    }
    
    public ArrayList getEmp(HttpServletRequest req ,HttpServletResponse res){
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
        ArrayList empList = new ArrayList();
        String empSql = "select nme_idn, nme from nme_v where typ = 'EMPLOYEE' order by nme";
      ArrayList rsLst = db.execSqlLst("empSql", empSql, ary);
      PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
      ResultSet rs = (ResultSet)rsLst.get(1);
        try {
            while (rs.next()) {
                MNme emp = new MNme();
                emp.setEmp_idn(rs.getString("nme_idn"));
                emp.setEmp_nme(rs.getString("nme"));
                empList.add(emp);
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return empList;
    }
    public ArrayList prcPrpList(HttpServletRequest req ,HttpServletResponse res ,String prcIdn){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList prcPrpList = new ArrayList();
        String prcPrpSql = "select mprp from prc_prp_alw where  prc_idn=? and flg in('EDIT','COMP') order by srt  ";
        ArrayList ary = new ArrayList();
        ary.add(prcIdn);
      ArrayList rsLst = db.execSqlLst("prpPrcSql", prcPrpSql ,ary);
      PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
      ResultSet rs = (ResultSet)rsLst.get(1);
        try {
            while (rs.next()) {
               prcPrpList.add(util.nvl(rs.getString("mprp")));
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return prcPrpList;
    }
    
    public HashMap FecthResult(HttpServletRequest req,HttpServletResponse res, HashMap params){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        HashMap pktList = new HashMap();
        String stt = (String)params.get("stt");
        String vnm = (String)params.get("vnm");
        String issId = (String)params.get("issueId");
        String empIdn = (String)params.get("empIdn");
        ArrayList ary = null;
        ArrayList vwPrpLst = PriPrprViw(req,res);
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , srch_id , rmk , rln_idn , sk1,cmp, rap_rte, cert_lab, cert_no, quot, rap_dis,rchk ) " + 
        " select  a.pkt_ty, a.idn, a.vnm,  a.dte, a.stt , 'Z' , a.qty , a.cts , b.iss_id , a.tfl3 , b.iss_emp_id , a.sk1, a.cmp , a.rap_rte " + 
        " , a.cert_lab, a.cert_no,  nvl(upr, a.cmp) " + 
        " , decode(greatest(nvl(a.rap_rte,1), 1), 1, null, trunc((nvl(a.upr,a.cmp)/a.rap_rte*100) - 100, 2)) dis,b.lot_id "+
        "   from mstk a , iss_rtn_dtl b where a.idn = b.iss_stk_idn and b.stt='IS'  "+
        " and a.stt =? and a.cts > 0   ";
        if(vnm.length()<=2){
        ary = new ArrayList();
        ary.add(stt);
        if(!issId.equals("")){
            srchRefQ = srchRefQ+" and b.iss_id = ? " ;
            ary.add(issId);
        }
        if(!empIdn.equals("0")){
            
            srchRefQ = srchRefQ+" and b.iss_emp_id = ? " ;
            ary.add(empIdn);
        }
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
            System.out.println(srchRefQ);
        }else if(vnm.length()>2){
              vnm = util.getVnm(vnm);
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
                  
                  vnmSub=vnmSub.toUpperCase();
                  vnmSub= vnmSub.replaceAll (" ", "");
                  ary = new ArrayList();
                  ary.add(stt);
                  String srchRefQVnm=srchRefQ;
                  if(!issId.equals("")){
                      srchRefQVnm = srchRefQVnm+" and b.iss_id = ? " ;
                      ary.add(issId);
                  }
                  if(!empIdn.equals("0")){
                      
                      srchRefQVnm = srchRefQVnm+" and b.iss_emp_id = ? " ;
                      ary.add(empIdn);
                  }
                  srchRefQVnm = srchRefQVnm+" and ( a.vnm in ("+vnmSub+") or a.tfl3 in ("+vnmSub+") ) " ;
                  ct = db.execUpd(" ins gt_srch_rslt", srchRefQVnm,ary); 
                  System.out.println(srchRefQVnm);
              }
            }
        
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("PRI_VW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        if(stt.equals("MKT_GEN_IS"))
        ct = db.execCall("DP_GEN_ALT_SK1", "DP_GEN_ALT_SK1", new ArrayList());
        pktList = SearchResult(req,res,"'Z'", vwPrpLst);
       
        return pktList;
    }
    public HashMap SearchResult(HttpServletRequest req,HttpServletResponse res, String flg , ArrayList vwPrpLst) {
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr();
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
            HashMap pktListMap = new HashMap();
            ArrayList pktStkIdnList = new ArrayList();    
            String srchQ = "";
            HashMap pktPrpMap = new HashMap();
            String srchQ1 = " select sk1, 0 dsp , a.stk_idn , cert_lab , dsp_stt , decode(stt " + 
            ", 'MKPP','color:Green'" + 
            " , 'MKLB','color:Maroon'" + 
            " , 'MKAV', decode(instr(upper(dsp_stt), 'NEW'), 0, ' ', 'color:Blue')" + 
            " , 'MKIS', 'color:Red'" + 
            " , 'SOLD') class ,  ";
            String srchQ2 = " select sk1, 1 dsp , a.stk_idn , cert_lab , '' dsp_stt , '' class , ";
            String dspStk = "stkDspFF";
            String getQuot = "quot rte";
           srchQ += "  cts crtwt, " +
                    //decode(quot, 0, 0, decode(nvl(rap_dis,0), 0, 101, trunc(((quot*100)/greatest(rap_rte,100)) - 100,2))) rr_dis, "+
                    " decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  r_dis " +
                    " , stk_idn  , vnm, kts_vw kts, cert_lab cert, cert_no, rmk, img, cmp , trunc(((cmp*100)/greatest(rap_rte,1)) - 100,2) cmp_dis ,  to_char(trunc(cts,2),'9990.99') cts, qty , rap_rte , " +
                    getQuot +
                    ", cmnt,nvl(rchk,'N') rtnprpupdated,stt stt1,  nvl(fquot,0) fquot , flg , to_char(trunc(cts,2) * quot, '99999990.00') amt , " +
                    " decode(nvl(fquot,0),0, 'NA', decode(fquot, quot, 'NA', decode(greatest(fquot, quot),quot, 'UP','DN'))) cmp_flg,srch_id ";

         

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

         
            String rsltQ =
               srchQ1+" "+srchQ + " from gt_srch_rslt a where flg in ("+flg+") union " +srchQ2+ " "+srchQ +"   from gt_srch_multi a " ;
                rsltQ = rsltQ+  " order by sk1";
            
            ArrayList ary = new ArrayList();
            
          ArrayList rsLst = db.execSqlLst("search Result", rsltQ, ary);
          PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
          ResultSet rs = (ResultSet)rsLst.get(1);
            pktListMap = new HashMap();
            try {
                while (rs.next()) {
                    String vnm =util.nvl(rs.getString("vnm"));
                    String cert_No = util.nvl(rs.getString("cert_no"));
                    pktPrpMap = new HashMap();
                    String stkIdn = util.nvl(rs.getString("stk_idn"));
                    pktPrpMap.put("flg", util.nvl(rs.getString("flg")));
                    pktPrpMap.put("stt", util.nvl(rs.getString("dsp_stt")));
                    pktPrpMap.put("iss_idn",util.nvl(rs.getString("srch_id")));
                    pktPrpMap.put("stk_idn",stkIdn);
                    pktPrpMap.put("vnm",vnm);
                    pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                    pktPrpMap.put("class",util.nvl(rs.getString("class")));
                    pktPrpMap.put("cts", util.nvl(rs.getString("cts")));
                    pktPrpMap.put("cert_no",cert_No);
                    pktPrpMap.put("stt1",util.nvl(rs.getString("stt1")));
                    pktPrpMap.put("quot",util.nvl(rs.getString("rte")));
                    pktPrpMap.put("fquot",util.nvl(rs.getString("fquot")));
                    pktPrpMap.put("cmp",util.nvl(rs.getString("cmp")));
                    pktPrpMap.put("cmp_dis",util.nvl(rs.getString("cmp_dis")));
                    pktPrpMap.put("rap_rte",util.nvl(rs.getString("rap_rte")));
                    pktPrpMap.put("r_dis",util.nvl(rs.getString("r_dis")));
                    pktPrpMap.put("cert_lab", util.nvl(rs.getString("cert_lab")));
                    pktPrpMap.put("amt", util.nvl(rs.getString("amt")));
                    pktPrpMap.put("rtnprpupdated", util.nvl(rs.getString("rtnprpupdated")));
                    for (int j = 0; j < vwPrpLst.size(); j++) {
                        String prp = (String)vwPrpLst.get(j);

                        String fld = "prp_";
                        if (j < 9)
                            fld = "prp_00" + (j + 1);
                        else
                            fld = "prp_0" + (j + 1);

                        String val = util.nvl(rs.getString(fld));
                        if (prp.toUpperCase().equals("RAP_DIS"))
                            val = util.nvl(rs.getString("r_dis"));
                        if (prp.toUpperCase().equals("RAP_RTE"))
                            val = util.nvl(rs.getString("rap_rte"));
                        if(prp.equals("RTE"))
                            val = util.nvl(rs.getString("rte"));
                        if(prp.equals("KTSVIEW"))
                            val = util.nvl(rs.getString("kts"));
                        if(prp.equals("COMMENT"))
                            val = util.nvl(rs.getString("cmnt"));
                           

                            pktPrpMap.put(prp, val);
                    }

                    pktListMap.put(stkIdn , pktPrpMap);
                    pktStkIdnList.add(stkIdn);
                }
                rs.close();
                stmt.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }           
           session.setAttribute("pktStkIdnList", pktStkIdnList);
            return pktListMap;
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
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "PriceReturn"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        HSSFWorkbook hwb = null;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        int ct = db.execUpd("gtUpdate","update gt_srch_rslt set prp_001 = null where flg='M'", new ArrayList());
         String pktPrp = "pkgmkt.pktPrp(0,?)";
         ArrayList ary = new ArrayList();
         ary.add("PRI_VW_XL");
         int ct1 = db.execCall(" Srch Prp ", pktPrp, ary);
        hwb = xlUtil.getInXl("Price", req, "PRI_VW_XL");
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
        return null;
        }
    }
    public ArrayList PriPrprViw(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("priViewLst");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
            
              ArrayList rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'PRI_VW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
              ResultSet rs1 = (ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                stmt.close();
                session.setAttribute("priViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
    public ArrayList PriPrpDTLViw(HttpServletRequest req , HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
        ArrayList asViewPrp = (ArrayList)session.getAttribute("priDTLViewLst");
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
        
              ArrayList rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'PRIRTNDTL_VW' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement stmt = (PreparedStatement)rsLst.get(0);
              ResultSet rs1 = (ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                stmt.close();
                session.setAttribute("priDTLViewLst", asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return asViewPrp;
    }
    
    public ActionForward selectRtnPrppkt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
        ArrayList pktStkIdnList = (ArrayList)session.getAttribute("pktStkIdnList");
        HashMap stockList = (HashMap)session.getAttribute("pktList");
        StringBuffer sb = new StringBuffer();
        boolean setdflt=false;
        if(pktStkIdnList.size()>0){
        for(int i=0;i<pktStkIdnList.size();i++){
            String mstkIdn = (String)pktStkIdnList.get(i);
            HashMap pktData = (HashMap)stockList.get(mstkIdn);
            String rtnprpupdated= util.nvl((String)pktData.get("rtnprpupdated"));
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
    public PriceRtnAction() {
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
          util.updAccessLog(req,res,"Price Rtn Action", "init");
          }
          }
          return rtnPg;
          }

   
}

