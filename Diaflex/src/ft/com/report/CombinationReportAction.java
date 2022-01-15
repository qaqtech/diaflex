package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.LogMgr;

import ft.com.dao.UIForms;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;

import java.util.logging.Level;

import javax.mail.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class CombinationReportAction extends DispatchAction
{
  public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
    util.updAccessLog(req,res,"Combination Report", "load start");
    CombinationReportForm combReprtForm = (CombinationReportForm)form;
    GenericInterface genericInt = new GenericImpl();
    combReprtForm.reset();
    HashMap prp=info.getPrcPrp();
    HashMap mprp=info.getMprp();
    ArrayList sttVec=new ArrayList();
      String    memoPrntOptn = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                               + "b.mdl = 'MKTG' and b.nme_rule = 'STATUS' and a.til_dte is null order by a.dsc ";
      ArrayList sttList = new ArrayList();
      ResultSet  rs = db.execSql("memoPrint", memoPrntOptn, new ArrayList());
      try {
          while (rs.next()) {
              UIForms memoOpn = new UIForms();
              memoOpn.setFORM_NME(rs.getString("chr_fr"));
              memoOpn.setFORM_TTL(rs.getString("dsc"));
              sttList.add(memoOpn);
          }
          rs.close();
      } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
      }
   
    combReprtForm.setSttList(sttList);
    
    ArrayList prpCombvw=new ArrayList();
    String qryvw="select prp , flg from rep_prp where mdl='COMB_VIEW' and flg in ('Y','S') order by rnk ";
    rs=db.execSql("Properties in COMB_VIEW",qryvw,
               new ArrayList());
    while(rs.next()) {
        ArrayList asViewdtl=new ArrayList();
        asViewdtl.add(rs.getString("prp"));
        asViewdtl.add(rs.getString("flg"));
      prpCombvw.add(asViewdtl);   
    }
      rs.close();
    info.setPrpCombvw(prpCombvw);
    //  Toget property and srt
    ArrayList mprpArry=new ArrayList();
          ArrayList trfSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_COMB_SRCH") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_COMB_SRCH");
    info.setGncPrpLst(trfSrchList);
    combReprtForm.setMprpArryRow1(mprpArry);
    combReprtForm.setMprpArryRow2(mprpArry);
    combReprtForm.setMprpArryCol1(mprpArry);
    combReprtForm.setMprpArryCol2(mprpArry);
    util.updAccessLog(req,res,"Combination Report", "load end");
  return am.findForward("load");
      }
  }
    public ActionForward view(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Combination Report", "view start");
            CombinationReportForm combReprtForm = (CombinationReportForm)form;
            GenericInterface genericInt = new GenericImpl();
            SearchQuery query = new SearchQuery();
      HashMap prp = info.getPrp();
      ArrayList params = new ArrayList();
      ArrayList srchPrpLst=null;
      ArrayList prpCombvw= info.getPrpCombvw();
      ArrayList gncPrpLst = (ArrayList)session.getAttribute("COMB_SRCH");
      info.setGncPrpLst(gncPrpLst);   //    Combination srch property list
      String stt=req.getParameter("stt");
     /************Search Id generation and Save Combinationsearch *****************/      
      int lSrchId=query.saveCombSearch(req, res ,combReprtForm);
      System.out.println("srchId"+lSrchId);
    
      String row1Prp=util.nvl((String)combReprtForm.getValue("prpRow1"));
      String row2Prp=util.nvl((String)combReprtForm.getValue("prpRow2"));
      String col1Prp=util.nvl((String)combReprtForm.getValue("prpCol1"));
      String col2Prp=util.nvl((String)combReprtForm.getValue("prpCol2"));
        String selectedPrp="";
      
      /************This will create mapping for property and gt_srch_rslt property***/
      
      HashMap combMap=new HashMap();
      for(int i=0;i<prpCombvw.size();i++) {
        String fld = "prp_";
        int k = i + 1;
        if (k < 10)
            fld += "00" + k;
        else if (k < 100)
            fld += "0" + k;
        else if (k > 100)
            fld += k;
        combMap.put(prpCombvw.get(i),fld);
      }
      
      ArrayList vwPrplist=new ArrayList();
      ArrayList curPrpary=new ArrayList(); // contains selected property
      String curPrplst="";
      ArrayList rowList=new ArrayList();  
      ArrayList colList=new ArrayList();  
      if(!row1Prp.equals("select"))
      {
         
       vwPrplist.add(row1Prp);
        rowList.add(row1Prp);
        String gtprp=(String)combMap.get(row1Prp);
        curPrpary.add(gtprp);
        curPrplst+=gtprp +","; 
          selectedPrp+=row1Prp+"_";
         
      }
      if(!row2Prp.equals("select"))
      {
       
       vwPrplist.add(row2Prp);
        rowList.add(row2Prp);
        String gtprp=(String)combMap.get(row2Prp);
        curPrpary.add(gtprp);
        curPrplst+=gtprp +",";
          selectedPrp+=row2Prp+"_";
           
      }
      if(!col1Prp.equals("select"))
      {
       
       vwPrplist.add(col1Prp);
        colList.add(col1Prp);
        String gtprp=(String)combMap.get(col1Prp);
        curPrpary.add(gtprp);
        curPrplst+=gtprp +",";
          selectedPrp+=col1Prp+"_";
         
      }
      if(!col2Prp.equals("select"))
      {
       
       vwPrplist.add(col2Prp);
        colList.add(col2Prp);
        String gtprp=(String)combMap.get(col2Prp);
        curPrpary.add(gtprp);
        curPrplst+=gtprp +",";
          selectedPrp+=col2Prp+"_"; 
      }
    if(!curPrplst.equals(""))
    {
      curPrplst=curPrplst.substring(0,curPrplst.lastIndexOf(","));
    }
      req.setAttribute("RowList", rowList);
      req.setAttribute("ColList", colList);
        session.setAttribute("selectedPrp", selectedPrp);
        session.setAttribute("combMap",combMap);
        
      /*******************************************************************************************/
      
      
      
     HashMap prp_srtAry=new HashMap(); 
     HashMap vwprpMap=new HashMap(); //  storing name of view(matrix  ) property as key and their selected values sort as value
   ArrayList rowArray=new ArrayList();
   ArrayList colArray=new ArrayList();
   String selCriteria="";
    for(int i=0;i<gncPrpLst.size();i++) {
      String srtprp=""; 
        String subCrtra="";
      String lprp=(String)gncPrpLst.get(i);
       ArrayList srt=new ArrayList();
      
      ArrayList lprpS = (ArrayList)prp.get(lprp + "S");
        if(lprpS!=null)
        {
         
     for (int j = 0; j < lprpS.size(); j++) {
        
          String lSrt = (String)lprpS.get(j);
          String lFld = lprp + "_" + lSrt;
          String reqVal =
              util.nvl((String)combReprtForm.getValue(lFld));
         if(!reqVal.equals("")) {
           srtprp+=lSrt+",";
           subCrtra+=reqVal+",";
           srt.add(reqVal);//   storing selected  values in a string.
         }     
    }
          if(vwPrplist.contains(lprp) && srt.size()==0) {              //  if selected row or column has no selected values
                                                                   //  add all values of that property 
            String srtValqry="select PRT1 from prp where mprp='"+lprp+"'";
           ResultSet rsr=db.execSql("srtValqry",srtValqry, new ArrayList()); 
           while(rsr.next()) {
            srt.add(util.nvl(rsr.getString("PRT1"))) ;
           }
              rsr.close();
          }
          
     
        if(!srtprp.equals(""))
        {
      srtprp=srtprp.substring(0, srtprp.lastIndexOf(","));
        }
        if(!subCrtra.equals("") && subCrtra.contains(",")) {
         subCrtra= subCrtra.substring(0, subCrtra.lastIndexOf(","));
          subCrtra+=";";
        }
      vwprpMap.put(lprp,srtprp);
      prp_srtAry.put(lprp,srt);
    
        }
        if(!subCrtra.equals(""))
        {
      selCriteria+=lprp+"-"+subCrtra;
        }
       
    }
     req.setAttribute("selCriteria", selCriteria);
    
    for(int i=0;i<rowList.size();i++) {
      rowArray.add(prp_srtAry.get(rowList.get(i))) ; // Arraylist of sortvalues of row property added to rowArray in their selecting order
         
    }
      for(int i=0;i<colList.size();i++) {
        colArray.add(prp_srtAry.get(colList.get(i))) ;  // Arraylist of sortvalues of column property added to colArray
          
      }
    
  
  /******************************************************************************************************/  
      
      String combVwQry="select sum(qty) ttqty,sum(cts) ttcts ," ;
      combVwQry+=curPrplst;
      combVwQry+=" from  gt_srch_rslt  " ; 
      /*****************************************************/
      String whereCond="";
      for(int k=0;k<prpCombvw.size();k++)                      //  If  property select
      {
        String prpva=(String)prpCombvw.get(k);
   //   if(vwPrplist.contains(prp)) {
         String srtPrp=(String)vwprpMap.get(prpva);
          if(!srtPrp.equals(""))
          {
         whereCond+=" srt_00"+(k+1)+" in( "+srtPrp+ ") and ";
          }
   //   }
      }
      if(!whereCond.equals("")) {
        combVwQry+=" where " +whereCond;
      }
      /*********************************************************/
      System.out.println("combqry"+combVwQry);
      if(!combVwQry.equals("") && combVwQry.contains("and"))
      {
      combVwQry=combVwQry.substring(0, combVwQry.lastIndexOf("and"));
      }
      combVwQry+=" group by ";
      
      combVwQry+=curPrplst;
      HashMap keyvaltbl=new HashMap();
      ResultSet rst = db.execSql("combVwQry", combVwQry, new ArrayList());
      
      String ttqty="";
      String ttcts="";
      String prp_001="";
      String prp_002="";
      while(rst.next()) {
        
       ttqty=util.nvl(rst.getString("ttqty"));
       ttcts=util.nvl(rst.getString("ttcts"));
      
        String prpkey="";
        for(int i=0;i<curPrpary.size();i++) {
         prpkey+=util.nvl(rst.getString((String)curPrpary.get(i)))+"_";
        }
          if(!prpkey.equals(""))
          {
        prpkey=prpkey.substring(0,prpkey.lastIndexOf("_")).toUpperCase();
          }
        String prpval=util.nvl( rst.getString("ttqty"))+" | "+util.nvl(rst.getString("ttcts"));
        keyvaltbl.put(prpkey,prpval);
        req.setAttribute("keyvaltbl", keyvaltbl);
        System.out.println("prpkey"+prpkey+"prpval"+prpval);
      }
        rst.close();
   /******************************************************************************************/ 
      if(rowArray.size()>0)
      {
      int cnt=0,j=-1;
      String str="";
      ArrayList fnRowArray=new ArrayList();
      ArrayList row=(ArrayList)rowArray.get(cnt);
      fnRowArray=   row_colmethod(row,str,cnt,rowArray,fnRowArray);
      req.setAttribute("fnRowArray", fnRowArray);
      }
      if(colArray.size()>0)
      {
      int colcnt=0;
      String strcol="";
      ArrayList fnColArray=new ArrayList();
      ArrayList col=(ArrayList)colArray.get(colcnt);
      fnColArray=   row_colmethod(col,strcol,colcnt,colArray,fnColArray);
      req.setAttribute("fnColArray", fnColArray); 
      }
            util.updAccessLog(req,res,"Combination Report", "view end");
 
 return am.findForward("viewRpt"); 
        }
    }
//    public ActionForward viewdumy(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
//      init(req,res);
//      combReprtForm = (CombinationReportForm)form;
//      prp=info.getPrcPrp();
//      ArrayList prpCombvw= info.getPrpCombvw();
//      String stt=req.getParameter("stt");
//      String lab="GIA";
//      String row1Prp=(String)combReprtForm.getValue("prpRow1");
//      String row1subPrp1=(String)combReprtForm.getValue("row1subPrp1");
//      String row1subPrp2=(String)combReprtForm.getValue("row1subPrp2");
//      String row2Prp=(String)combReprtForm.getValue("prpRow2");
//      String row2subPrp1=(String)combReprtForm.getValue("row2subPrp1");
//      String row2subPrp2=(String)combReprtForm.getValue("row2subPrp2");
//      String col1Prp=(String)combReprtForm.getValue("prpCol1");
//      String col1subPrp1=(String)combReprtForm.getValue("col1subPrp1");
//      String col1subPrp2=(String)combReprtForm.getValue("col1subPrp2");
//      String col2Prp=(String)combReprtForm.getValue("prpCol2");
//      String col2subPrp1=(String)combReprtForm.getValue("col2subPrp1");
//      String col2subPrp2=(String)combReprtForm.getValue("col2subPrp2");
//      System.out.println("row1sub1"+row1subPrp1+"row1sub2"+row1subPrp2+"row2sub1"+row2subPrp1+"row2sub2"+row2subPrp2);
//      System.out.println("col1sub1"+col1subPrp1+"col11sub2"+col1subPrp2+"col2sub1"+col2subPrp1+"col2sub2"+col2subPrp2);
//    HashMap combMap=new HashMap();
//      for(int i=0;i<prpCombvw.size();i++) {
//        String fld = "prp_";
//        int k = i + 1;
//        if (k < 10)
//            fld += "00" + k;
//        else if (k < 100)
//            fld += "0" + k;
//        else if (k > 100)
//            fld += k;
//        combMap.put(prpCombvw.get(i),fld);
//      }
//    
//      HashMap paramsMap = new HashMap();
//      HashMap prpDtls=new HashMap();
//      paramsMap.put("stt",stt);
//      paramsMap.put("mdl","COMB_VIEW");
//      ArrayList rowList=new ArrayList();  
//      ArrayList curPrpary=new ArrayList(); // contains selected property
//      String selectedPrp="";
//      if(!row1Prp.equals("select")) {
//        rowList.add(row1Prp); 
//        paramsMap.put(row1Prp+"_1",row1subPrp1);
//        paramsMap.put(row1Prp+"_2",row1subPrp2);
//        prpDtls.put(row1Prp,row1subPrp1+"_"+row1subPrp2);
//        String gtprp=(String)combMap.get(row1Prp);
//        curPrpary.add(gtprp);
//          selectedPrp+=row1Prp+"_";
//      }
//      if(!row2Prp.equals("select")) {
//        rowList.add(row2Prp);
//        paramsMap.put(row2Prp+"_1",row2subPrp1);
//        paramsMap.put(row2Prp+"_2",row2subPrp2);
//        prpDtls.put(row2Prp,row2subPrp1+"_"+row2subPrp2);
//        String gtprp=(String)combMap.get(row2Prp);
//        curPrpary.add(gtprp);
//          selectedPrp+=row1Prp+"_";
//          selectedPrp+=row2Prp+"_";
//      }
//      ArrayList colList=new ArrayList();     
//      if(!col1Prp.equals("select")) {
//        colList.add(col1Prp); 
//        paramsMap.put(col1Prp+"_1",col1subPrp1);
//        paramsMap.put(col1Prp+"_2",col1subPrp2);
//        prpDtls.put(col1Prp,col1subPrp1+"_"+col1subPrp2);
//        String gtprp=(String)combMap.get(col1Prp);
//        curPrpary.add(gtprp);
//          selectedPrp+=row1Prp+"_";
//          selectedPrp+=row2Prp+"_";
//        selectedPrp+=col1Prp+"_";  
//      }
//      if(!col2Prp.equals("select")) {
//        colList.add(col2Prp); 
//        paramsMap.put(col2Prp+"_1",col2subPrp1);
//        paramsMap.put(col2Prp+"_2",col2subPrp2);
//        prpDtls.put(col2Prp,col2subPrp1+"_"+col2subPrp2);
//        String gtprp=(String)combMap.get(col2Prp);
//        curPrpary.add(gtprp);
//          selectedPrp+=row1Prp+"_";
//          selectedPrp+=row2Prp+"_";
//          selectedPrp+=col1Prp+"_";
//          selectedPrp+=col2Prp+"_";  
//      }
//      req.setAttribute("RowList", rowList);
//      req.setAttribute("ColList", colList);
//    
//      /*****Search ***************/
//     int srchid= util.genericSrch(paramsMap);      
//    System.out.println("searchid"+srchid);
//    
//    /**********************************/
//    //    String combVwQry="select * from gt_srch_rslt";
//    String curPrplst="";
//    
//      HashMap prpVal=new HashMap(); //  contains key=selected PRP val=arraylist for values of that property.
//    ArrayList rowArray=new ArrayList();
//    ArrayList colArray=new ArrayList();
//    for(int i=0;i<prpCombvw.size();i++)
//    {
//      String prp=(String)prpCombvw.get(i);
//      
//    if(prpDtls.containsKey(prp)) {
//       
//       String srt1_srt2=util.nvl((String)prpDtls.get(prp));
//      String srt1="";
//      String srt2="";
//      if(!srt1_srt2.equals(""))
//      {
//        srt1=srt1_srt2.substring(0, srt1_srt2.lastIndexOf("_"));
//        srt2=srt1_srt2.substring(srt1_srt2.lastIndexOf("_")+1,srt1_srt2.length());
//      }
//       ArrayList srtAry=new ArrayList();
//       String prpvalqry="select mprp,val from prp where srt between ? and ? and mprp=?" ;
//       srtAry.add(srt1);
//       srtAry.add(srt2);
//       srtAry.add(prp);
//     ResultSet rst = db.execSql("prpvalqry", prpvalqry, srtAry);
//     ArrayList srt=new ArrayList();
//     while (rst.next()) {
//         String val=util.nvl(rst.getString("val")).toUpperCase();
//         srt.add(val);
//     }
//     if(rowList.contains(prp)) {
//       rowArray.add(srt);       //   adding arraylist of rowvalues in rowArray
//     }
//     if(colList.contains(prp)) {
//       colArray.add(srt);       //   adding arraylist of colvalues in colArray
//     }
//     prpVal.put(prp,srt);
//    String fld = "prp_";
//    int k = i + 1;
//    if (k < 10)
//      fld += "00" + k;
//    else if (k < 100)
//      fld += "0" + k;
//    else if (k > 100)
//      fld += k;
//     
//     curPrplst+=fld +","; 
//    }
//    
//    }
//    if(!curPrplst.equals(""))
//    {
//      curPrplst=curPrplst.substring(0,curPrplst.lastIndexOf(","));
//    }
//      String combVwQry="select sum(qty) ttqty,sum(cts) ttcts ," ;
//    /*    for(int i=0;i<prpCombvw.size();i++)
//      {
//          String prp=(String)prpCombvw.get(i);
//       if(prpDtls.containsKey(prp)) {
//         combVwQry+="prp_00"+(i+1)+" "+ prp+",";  
//       }
//      }  
//      combVwQry=combVwQry.substring(0,combVwQry.lastIndexOf(","));*/
//      combVwQry+=curPrplst;
//      combVwQry+=" from  gt_srch_rslt  where " ; 
//    
//    for(int i=0;i<prpCombvw.size();i++)
//    {
//        String prp=(String)prpCombvw.get(i);
//     if(prpDtls.containsKey(prp)) {
//         String srtval=util.nvl((String)prpDtls.get(prp));
//        
//         String srt1=srtval.substring(0,srtval.indexOf("_"));
//         String srt2=srtval.substring(srtval.indexOf("_")+1,srtval.length());
//       combVwQry+=" srt_00"+(i+1)+" between "+srt1+" and "+srt2+" and ";
//     }
//    } 
//      combVwQry=combVwQry.substring(0, combVwQry.lastIndexOf("and"));
//      combVwQry+=" group by ";
//    
//      combVwQry+=curPrplst;
//      HashMap keyvaltbl=new HashMap();
//      ResultSet rst = db.execSql("combVwQry", combVwQry, new ArrayList());
//      
//      while (rst.next()) {
//          String prpkey="";
//       for(int i=0;i<curPrpary.size();i++) {
//           prpkey+=rst.getString((String)curPrpary.get(i))+"_";
//       }
//        prpkey=prpkey.substring(0,prpkey.lastIndexOf("_")).toUpperCase();
//        String prpval= rst.getString("ttqty")+" | "+rst.getString("ttcts");
//        keyvaltbl.put(prpkey,prpval); 
//          req.setAttribute("keyvaltbl", keyvaltbl);
//          System.out.println("prpkey"+prpkey+"prpval"+prpval);
//      }
//      if(rowArray.size()>0)
//      {
//      int cnt=0,j=-1;
//      String str="";
//      ArrayList fnRowArray=new ArrayList();
//      ArrayList row=(ArrayList)rowArray.get(cnt);
//      fnRowArray=   row_colmethod(row,str,cnt,rowArray,fnRowArray);
//      req.setAttribute("fnRowArray", fnRowArray);
//      }
//      if(colArray.size()>0)
//      {
//      int colcnt=0;
//      String strcol="";
//      ArrayList fnColArray=new ArrayList();
//      ArrayList col=(ArrayList)colArray.get(colcnt);
//      fnColArray=   row_colmethod(col,strcol,colcnt,colArray,fnColArray);
//      req.setAttribute("fnColArray", fnColArray); 
//      }  
//    
//       
//    
//    return am.findForward("viewRpt");
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
                  util.updAccessLog(req,res,"Combination Report", "Unauthorized Access");
                  else
                  util.updAccessLog(req,res,"Combination Report", "init");
              }
              }
              return rtnPg;
              }
 
   public ArrayList row_colmethod(ArrayList row,String str,int cnt,ArrayList rowarray,ArrayList fnarray) {
    
   for(int i=0;i<row.size();i++) {
     str+=row.get(i)+"_";
         cnt++;
       if(cnt<rowarray.size()) {
          
         row_colmethod((ArrayList)rowarray.get(cnt),str,cnt,rowarray,fnarray);
         cnt--;
         str=str.substring(0, str.lastIndexOf("_"));
         if(str.contains("_"))
         {
         str=str.substring(0, str.lastIndexOf("_")+1);
         }
         else {
           str="";
         }
         
       }
       else {
         cnt--;
           str=str.substring(0,str.length()-1);
           fnarray.add(str);
         str=str.substring(0, str.lastIndexOf("_")+1);
       }
       
   }
   return fnarray;
   }
   
      public ActionForward pktDtl(ActionMapping am, ActionForm form,
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
              util.updAccessLog(req,res,"Combination Report", "pktDtl start");
      String selctprp=(String)session.getAttribute("selectedPrp");
      HashMap combMap=(HashMap)session.getAttribute("combMap");     
              CombinationReportForm combReprtForm = (CombinationReportForm)form;
      String cellnameval = util.nvl(req.getParameter("cellname")); //Each cells column name(row name & col name)
      cellnameval=cellnameval+"_";
     int cnt=0;
     HashMap prpValmaintable=new HashMap();
      do {
          String indprp="";
          String indPrpval="";
          HashMap prpValtable=new HashMap();
          if(selctprp.contains("_"))
          {
              indprp=selctprp.substring(0,selctprp.indexOf("_"));  
              indPrpval=cellnameval.substring(0,cellnameval.indexOf("_"));
              prpValtable.put(indprp,indPrpval);
          }
          else {
              indprp=selctprp;
              indPrpval=cellnameval;
              prpValtable.put(indprp,indPrpval);
               }
          prpValmaintable.put("prp"+cnt,prpValtable)  ;
          cnt++;
          if(selctprp.contains("_"))
          {
          selctprp=selctprp.substring(selctprp.indexOf("_")+1, selctprp.length());
          cellnameval=cellnameval.substring(cellnameval.indexOf("_")+1, cellnameval.length());
          }
      }while(selctprp.contains("_"));

      ArrayList prpAry=info.getPrpCombvw();
          session.setAttribute("prpArray", prpAry);

      ArrayList aryd=new ArrayList();
          HashMap onlySelctPrp=new HashMap();
          String selectPrp00="";
          String whereCond="";
          int k=0;
      while(k<cnt)
      {   
         
       HashMap htable=(HashMap)prpValmaintable.get("prp"+k);
          k++;
          Set<String> set = htable.keySet();

              Iterator<String> itr = set.iterator();
              while (itr.hasNext()) {
               String key = itr.next();
               String value=(String)htable.get(key);
               String prpGt=(String)combMap.get(key);
               whereCond+=prpGt+"='"+value+"' and "; 
                  }
              }
        whereCond=  whereCond.substring(0, whereCond.lastIndexOf("and"));
          for(int i=0;i<prpAry.size();i++) {
              String prp=(String)prpAry.get(i);
              String fld = "prp_";
              int j = i + 1;
              if (j < 10)
              fld += "00" + j;
              else if (j < 100)
              fld += "0" + j;
              else if (j > 100)
              fld += j;
             selectPrp00+=fld+",";
          
      }
      

      ArrayList pktList = new ArrayList();
      String cond="";
      String srchQ =" select stk_idn , pkt_ty, vnm, pkt_dte, stt , qty , cts , cert_lab ";
      ArrayList prpCombvw=info.getPrpCombvw();
         
      for (int i = 0; i < prpCombvw.size(); i++) {
      String prp=(String)prpCombvw.get(i);
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


      String rsltQ = srchQ + " from gt_srch_rslt " ;
      /* if(!cond.equals("")) {
      rsltQ+=cond;
      } */
      rsltQ+= "where "+whereCond+" order by sk1";

      ArrayList ary = new ArrayList();

      ResultSet rs = db.execSql("search Result", rsltQ, ary);

      try {
      while (rs.next()) {
      String cert_lab = util.nvl(rs.getString("cert_lab"));
      String stkIdn = util.nvl(rs.getString("stk_idn"));
      HashMap pktPrpMap = new HashMap();
      pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
      String vnm = util.nvl(rs.getString("vnm"));
      pktPrpMap.put("vnm", vnm);
      pktPrpMap.put("stk_idn", stkIdn);
      pktPrpMap.put("qty", util.nvl(rs.getString("qty")));
      pktPrpMap.put("cts", util.nvl(rs.getString("cts")));

      for (int j = 0; j < prpAry.size(); j++) {
      String prp = (String)prpAry.get(j);

      String fld = "prp_";
      if (j < 9)
      fld = "prp_00" + (j + 1);
      else
      fld = "prp_0" + (j + 1);

      String val = util.nvl(rs.getString(fld));


      pktPrpMap.put(prp, val);
      }

      pktList.add(pktPrpMap);
      }
          rs.close();
      session.setAttribute("pktList", pktList);
      } catch (SQLException sqle) {

      // TODO: Add catch code
      sqle.printStackTrace();
      }
              util.updAccessLog(req,res,"Combination Report", "pktDtl end");
      return am.findForward("loadPktDtl");
          }
      }
      

  }


