package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.dao.ByrDao;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import ft.com.lab.LabIssueImpl;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MixSaleReportAction extends DispatchAction {
    public MixSaleReportAction() {
        super();
    }
   public ActionForward load(ActionMapping am, ActionForm form,
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
           util.updAccessLog(req,res,"Mix Sale Report", "load start");
      MixSaleReportForm udf = (MixSaleReportForm)form;
           udf.reset();
      GenericInterface genericInt = new GenericImpl();
           String conQ="";
           HashMap dbinfo = info.getDmbsInfoLst();
           String cnt = (String)dbinfo.get("CNT");
           ArrayList ary=new ArrayList();
           String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
           String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
           String usrFlg=util.nvl((String)info.getUsrFlg());
           ArrayList rolenmLst=(ArrayList)info.getRoleLst();
           String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
           if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
           }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
           conQ +=" and nvl(n.grp_nme_idn,0) =? "; 
           ary.add(dfgrpnmeidn);
           } 
               
               if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                       if(util.nvl((String)info.getDmbsInfoLst().get("EMP_LOCK")).equals("Y")){
                           conQ += " and (n.emp_idn= ? or n.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
                           ary.add(dfNmeIdn);
                           ary.add(dfNmeIdn);
                       }
               }
      udf.reset();
           String dtePrpQ="select to_char(trunc(sysdate), 'dd-mm-rrrr') frmdte,to_char(trunc(sysdate), 'dd-mm-rrrr') todte from dual";

           ArrayList outLst = db.execSqlLst("Date calc", dtePrpQ,new ArrayList());
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet rs = (ResultSet)outLst.get(1);
           while (rs.next()) { 
           udf.setValue("dtefr",(util.nvl(rs.getString("frmdte"))));
           udf.setValue("dteto",(util.nvl(rs.getString("todte"))));
           }  
           rs.close(); pst.close();
           
           String typQ=" and a.typ in('SL','LS') ";
           if(info.getBlockLs().equals("Y"))
               typQ=" and a.typ='SL' ";
           if(cnt.equals("ag"))
               typQ=" and a.typ = 'SL' ";
           
           ArrayList byrList = new ArrayList();
           String    getByr  = " With DLV_PNDG_V as (\n" + 
           "        select distinct nme_idn\n" + 
           "          from msal a , jansal b , mstk c\n" + 
           "          where a.idn = b.idn  and b.mstk_idn = c.idn and  c.pkt_ty ='MIX'\n" + 
           "          and a.stt='IS'  and b.stt <> 'RT'   \n" + typQ+
           "          )\n" + 
           "          select n.nme_idn,n.nme byr\n" + 
           "          from nme_v n, dlv_pndg_v j\n" + 
           "          where n.nme_idn = j.nme_idn\n" + conQ+
           "          order by 2";


           outLst = db.execSqlLst("byr", getByr, ary);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
                 while (rs.next()) {
                     ByrDao byr = new ByrDao();

                     byr.setByrIdn(rs.getInt("nme_idn"));
                     byr.setByrNme(rs.getString("byr"));
                     byrList.add(byr);
                 }
           rs.close(); pst.close();
           udf.setByrLst(byrList);
           
           ArrayList addatList = new ArrayList();
           String addatSql="select nme_idn,nme from nme_v where typ='AADAT' order by nme ";

           outLst = db.execSqlLst("memoPkt", addatSql, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
           while(rs.next()){
               ByrDao byr = new ByrDao();

               byr.setByrIdn(rs.getInt("nme_idn"));
               byr.setByrNme(rs.getString("nme"));
               addatList.add(byr);
           }
           rs.close(); pst.close();
           udf.setAddatList(addatList);
           
           
           ArrayList brokerList = new ArrayList();
           String brokerSql="select nme_idn,nme from nme_v where typ='BROKER' order by nme ";
           outLst = db.execSqlLst("memoPkt", brokerSql, new ArrayList());
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
           while(rs.next()){
               ByrDao byr = new ByrDao();

               byr.setByrIdn(rs.getInt("nme_idn"));
               byr.setByrNme(rs.getString("nme"));
               brokerList.add(byr);
           }
           rs.close(); pst.close();
           pst.close();
           udf.setBrokerList(brokerList);
           
           HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
           HashMap pageDtl=(HashMap)allPageDtl.get("MIX_SALE");
           if(pageDtl==null || pageDtl.size()==0){
           pageDtl=new HashMap();
           pageDtl=util.pagedef("MIX_SALE");
           allPageDtl.put("MIX_SALE",pageDtl);
           }
           info.setPageDetails(allPageDtl);
           
     ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXSALSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXSALSrch");
     info.setGncPrpLst(assortSrchList);
     util.updAccessLog(req,res,"Mix Sale Report", "load end");
     return am.findForward("load");
       }
   }
  public ActionForward fetch(ActionMapping am, ActionForm form,
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
          util.updAccessLog(req,res,"Mix Sale Report", "fetch start");
    MixSaleReportForm udf = (MixSaleReportForm)form;
    String delQ = " Delete from gt_srch_rslt ";
    int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
    
    ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXSALSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_MIXSALSrch");
    info.setGncPrpLst(genricSrchLst);
    HashMap prp = info.getPrp();
    HashMap mprp = info.getMprp();
    String dfr = util.nvl((String)udf.getValue("dtefr"));
    String dto = util.nvl((String)udf.getValue("dteto"));
    String byrIdn = util.nvl((String)udf.getValue("byrIdn"));
    String addatIdn = util.nvl((String)udf.getValue("addatIdn"));
    String brokerIdn = util.nvl((String)udf.getValue("brokerIdn"));
    String groupby = util.nvl((String)udf.getValue("groupby")); 
    HashMap paramsMap = new HashMap();
    paramsMap.put("dfr", dfr);
    paramsMap.put("dto", dto);
          for(int i=0;i<genricSrchLst.size();i++){
              ArrayList prplist =(ArrayList)genricSrchLst.get(i);
              String lprp = (String)prplist.get(0);
              String flg= (String)prplist.get(1);
              String typ = util.nvl((String)mprp.get(lprp+"T"));
              String prpSrt = lprp ;  
              if(flg.equals("M") || flg.equals("CTA")) {
                  ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                  ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                  ArrayList lprpP = (ArrayList)prp.get(prpSrt + "P");
                  if(flg.equals("M")) {
                  for(int j=0; j < lprpS.size(); j++) {
                  String lSrt = (String)lprpS.get(j);
                  String lVal = (String)lprpV.get(j);    
                  String reqVal1 = util.nvl((String)udf.getValue(lprp + "_" + lVal),"");
                  if(!reqVal1.equals("")){
                  paramsMap.put(lprp + "_" + lVal, reqVal1);
                  } 
                  }
                  }else if(flg.equals("CTA")){
                      String reqVal1 = util.nvl((String)udf.getValue(lprp + "_1"),"");
                      if(!reqVal1.equals("")){
                      ArrayList fmtVal = util.getStrToArr(reqVal1);
                      for(int j=0; j < lprpS.size(); j++) {
                      String lSrt = (String)lprpS.get(j);
                      String lVal = (String)lprpV.get(j);   
                      String lprt = (String)lprpP.get(j);
                      if(fmtVal.contains(lVal) || fmtVal.contains(lprt)){
                      reqVal1 = lVal;
                      paramsMap.put(lprp + "_" + lVal, reqVal1);
                      }
                      } 
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
    ArrayList stockList = null;
    if(paramsMap.size()>2){
    String[] addonstt = { "MKAV","MKAV", "MKSL", "MKSD" };
    paramsMap.put("stt", "MKAV");
    paramsMap.put("mdl", "MIX_SL_VW");
    paramsMap.put("MIX","Y");
    paramsMap.put("cprp", "STT");
    paramsMap.put("cprpValLst", addonstt);
    paramsMap.put("byrIdn", byrIdn);
    paramsMap.put("addatIdn", addatIdn);
    paramsMap.put("brokerIdn", brokerIdn);
    util.genericSrch(paramsMap);
  
    }else{
    String vnm = util.nvl((String)udf.getValue("vnm"));
    if(!vnm.equals(""))
    paramsMap.put("vnm", vnm);
    paramsMap.put("byrIdn", byrIdn);
    paramsMap.put("addatIdn", addatIdn);
    paramsMap.put("brokerIdn", brokerIdn);
   FecthResult(paramsMap, req,res);
  
    }
     if(groupby.equals("DATE")){
         ArrayList dateWiseList =dateWise(paramsMap, req,res);  
         req.setAttribute("DateWiseList", dateWiseList);
         
     }else if(groupby.equals("PARTY")){
      ArrayList  partyWiseList =partyWise(paramsMap, req,res);  
        req.setAttribute("PartyWiseList", partyWiseList);
     }else{
          stockList =SearchResult(paramsMap, req,res);  
         req.setAttribute("PktList", stockList);
     }
          util.updAccessLog(req,res,"Mix Sale Report", "fetch end");
    return am.findForward("load");
      }
  }
  public void FecthResult(HashMap params,HttpServletRequest req,HttpServletResponse res){
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
      int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
      String dfr = util.nvl((String)params.get("dfr"));
      String dto = util.nvl((String)params.get("dto"));
      String vnm = util.nvl((String)params.get("vnm"));
      String dteConQ="";
      ArrayList ary = new ArrayList();
      ArrayList stockList = new ArrayList();
      int ct=0;
      if(!dfr.equals("") && !dto.equals(""))
          dteConQ = " and trunc(a.dte) between to_date('"+dfr+"' , 'dd-mm-yyyy') and to_date('"+dto+"' , 'dd-mm-yyyy') ";    
     
      if(!vnm.equals("")){
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
         
      vnmSub = vnmSub.replaceAll(",", "");
      vnmSub = vnmSub.replaceAll("''", ",");
      vnmSub = vnmSub.replaceAll("'", "");
          if(!vnmSub.equals("")){
      vnmSub="'"+vnmSub+"'";
      ary = new ArrayList();
      String insScanPkt = " insert into gt_pkt_scan select * from TABLE(PARSE_TO_TBL("+vnmSub+"))";
       ct = db.execDirUpd(" ins scan", insScanPkt,ary);
       System.out.println(insScanPkt);
          }
      }
      
      
       String srchStr = " ( c.vnm = d.vnm or c.tfl3 = d.vnm ) ";
       String srchRefQ ="insert into gt_srch_rslt (stk_idn , vnm , qty , cts , quot , sk1 )"+
      "  select distinct c.idn ,c.vnm , c.qty , c.cts , nvl(c.cmp , c.upr) , sk1  from msal a , jansal b , mstk c,gt_pkt_scan d " + 
      "  where a.idn = b.idn and b.mstk_idn = c.idn and c.pkt_ty <> 'NR' and  b.stt in ('SL','DLV') and " +srchStr; 
       if(!dfr.equals("") && !dto.equals("")) {
           srchRefQ+=dteConQ;
       }
       ct = db.execDirUpd(" Srch Prp ", srchRefQ, new ArrayList()); 
       System.out.println(srchRefQ);  
      }else{  
      String gtInsert = "insert into gt_srch_rslt (stk_idn , vnm , qty , cts , quot , sk1 )"+
      "  select distinct c.idn ,c.vnm , c.qty , c.cts , nvl(c.cmp , c.upr) , sk1  from msal a , jansal b , mstk c " + 
      "  where a.idn = b.idn and b.mstk_idn = c.idn and c.pkt_ty <> 'NR' and b.stt in ('SL','DLV') " + dteConQ;
      ct = db.execUpd("gtInsert", gtInsert, new ArrayList());
      }
    String pktPrp = "pkgmkt.pktPrp(0,?)";
    ary = new ArrayList();
    ary.add("MIX_SL_VW");
    ct = db.execCall(" Srch Prp ", pktPrp, ary);
    
    
   
  }
    public ArrayList partyWise(HashMap params , HttpServletRequest req,HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList partyWiseList = new  ArrayList();
        String dfr = util.nvl((String)params.get("dfr"));
        String dto = util.nvl((String)params.get("dto"));
        String vnmLst = util.nvl((String)params.get("vnm"));
        String byrIdn =  util.nvl((String)params.get("byrIdn"));
          String addatIdn =  util.nvl((String)params.get("addatIdn"));
          String brokerIdn =  util.nvl((String)params.get("brokerIdn"));
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        String dteConQ="";
        String conQ="";
        ArrayList ary = new ArrayList();
        if(info.getBlockLs().equals("Y"))
            conQ=" and a.typ <> 'LS' ";
        if(cnt.equals("ag"))
            conQ=" and a.typ <> 'LS' ";
        
        if(!dfr.equals("") && !dto.equals(""))
            dteConQ = " and trunc(a.dte) between to_date('"+dfr+"' , 'dd-mm-yyyy') and to_date('"+dto+"' , 'dd-mm-yyyy') ";    
       
        
        
        int ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_rslt where vnm='131.001'", new ArrayList());

        
        String partyWiseSql="select sum(b.qty) qtySal ,   to_char(trunc(sum(b.cts),2),'99999990.00') ctsSal ,\n" + 
        "trunc(((sum(b.cts*nvl(b.fnl_sal,b.quot)/nvl(decode('"+cnt+"','kj',1,a.exh_rte),1)) / sum(b.cts)))) salRte ,\n" + 
        "to_char(trunc(sum(b.cts*nvl(b.fnl_sal, b.quot)/nvl(decode('"+cnt+"','kj',1,a.exh_rte),1)), 2),'9999999999990.00') vlu ,  \n" + 
        "d.nme nme,d.nme_idn\n" + 
        "from  msal a , jansal b , gt_srch_rslt c , nme_v d\n" + 
        "where a.idn = b.idn and b.mstk_idn = c.stk_idn and b.stt in ('SL','DLV')  and a.nme_idn=d.nme_idn\n" +conQ; 
       
          if(vnmLst.equals("") || (!dfr.equals("") && !dto.equals(""))){
                partyWiseSql+=dteConQ;    
            }
              if(!byrIdn.equals("") && !byrIdn.equals("0")){
                   partyWiseSql+=" and a.nme_idn = ? ";
                  ary.add(byrIdn);
              }
              if(!addatIdn.equals("") && !addatIdn.equals("0") ){
                   partyWiseSql+=" and a.aadat_idn = ? or  a.mbrk1_idn = ?";
                  ary.add(addatIdn);
                  ary.add(addatIdn);
                  
              }
              
              if(!brokerIdn.equals("") && !brokerIdn.equals("0")){
                   partyWiseSql+=" and a.mbrk2_idn= ? or a.mbrk3_idn = ? or a.mbrk4_idn = ?";
                  ary.add(brokerIdn);
                  ary.add(brokerIdn);
                  ary.add(brokerIdn);
             }
              partyWiseSql+=" group by d.nme,d.nme_idn order by d.nme ";
              

        ArrayList outLst = db.execSqlLst("search Result", partyWiseSql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                HashMap pktPrpMap = new HashMap();
                String ctsSal = util.nvl(rs.getString("ctsSal"));
                 String salRte = util.nvl(rs.getString("salRte"));
                 String qtySal = util.nvl(rs.getString("qtySal"));
                 String vlu = util.nvl(rs.getString("vlu"));
                 String nme = util.nvl(rs.getString("nme"));
                pktPrpMap.put("ctsSal", ctsSal);
                pktPrpMap.put("salRte", salRte);
                pktPrpMap.put("qtySal", qtySal);
                pktPrpMap.put("vlu", vlu);
                pktPrpMap.put("nme", nme);
                pktPrpMap.put("nme_idn", util.nvl(rs.getString("nme_idn")));
                partyWiseList.add(pktPrpMap);
            }
            rs.close(); pst.close();
            
            ary = new ArrayList();
               String gtTtl = "select sum(b.qty) qtySal ,   to_char(trunc(sum(b.cts),2),'99999990.00') ctsSal , trunc(((sum(b.cts*nvl(b.fnl_sal,b.quot)/nvl(decode('"+cnt+"','kj',1,a.exh_rte),1)) / sum(b.cts)))) salRte , to_char(trunc(sum(b.cts*nvl(b.fnl_sal, b.quot)/nvl(decode('"+cnt+"','kj',1,a.exh_rte),1)), 2),'9999999999990.00') vlu "+
                           " from msal a , jansal b , gt_srch_rslt c where a.idn = b.idn and b.mstk_idn = c.stk_idn and b.stt in ('SL','DLV') "+conQ;
            if(vnmLst.equals("") || (!dfr.equals("") && !dto.equals(""))){
             gtTtl+=dteConQ;    
             }
            if(!byrIdn.equals("") && !byrIdn.equals("0")){
                gtTtl+=" and a.nme_idn = ? ";
               ary.add(byrIdn);
            }
               if(!addatIdn.equals("") && !addatIdn.equals("0") ){
                    gtTtl+=" and a.aadat_idn = ? or  a.mbrk1_idn = ?";
                   ary.add(addatIdn);
                   ary.add(addatIdn);
                   
               }
               
               if(!brokerIdn.equals("") && !brokerIdn.equals("0")){
                    gtTtl+=" and a.mbrk2_idn= ? or a.mbrk3_idn = ? or a.mbrk4_idn = ?";
                   ary.add(brokerIdn);
                   ary.add(brokerIdn);
                   ary.add(brokerIdn);
               }
            

            outLst = db.execSqlLst("gtTtl", gtTtl, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while(rs.next()){
             String qty = util.nvl(rs.getString("qtySal"));
             String cts = util.nvl(rs.getString("ctsSal"));
             String vlu = util.nvl(rs.getString("vlu"));
             req.setAttribute("qty", qty);
             req.setAttribute("cts", cts);
             req.setAttribute("vlu", vlu);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
           return  partyWiseList;
    }
    
    public ArrayList dateWise(HashMap params , HttpServletRequest req,HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList dateWiseList = new  ArrayList();
        String dfr = util.nvl((String)params.get("dfr"));
        String dto = util.nvl((String)params.get("dto"));
        String vnmLst = util.nvl((String)params.get("vnm"));
        String byrIdn =  util.nvl((String)params.get("byrIdn"));
          String addatIdn =  util.nvl((String)params.get("addatIdn"));
          String brokerIdn =  util.nvl((String)params.get("brokerIdn"));
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        String dteConQ="";
        String conQ="";
        ArrayList ary = new ArrayList();
        if(info.getBlockLs().equals("Y"))
            conQ=" and a.typ <> 'LS' ";
        if(cnt.equals("ag"))
            conQ=" and a.typ <> 'LS' ";
        
        if(!dfr.equals("") && !dto.equals(""))
            dteConQ = " and trunc(a.dte) between to_date('"+dfr+"' , 'dd-mm-yyyy') and to_date('"+dto+"' , 'dd-mm-yyyy') ";    
        else
            dteConQ = " and trunc(a.dte)=trunc(sysdate)";
        
        
        int ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_rslt where vnm='131.001'", new ArrayList());

        
        String dteWiseSql="select sum(b.qty) qtySal ,   to_char(trunc(sum(b.cts),2),'99999990.00') ctsSal ,\n" + 
        "trunc(((sum(b.cts*nvl(b.fnl_sal,b.quot)/nvl(decode('"+cnt+"','kj',1,a.exh_rte),1)) / sum(b.cts)))) salRte ,\n" + 
        "to_char(trunc(sum(b.cts*nvl(b.fnl_sal, b.quot)/nvl(decode('"+cnt+"','kj',1,a.exh_rte),1)), 2),'9999999999990.00') vlu ,  \n" + 
        "to_char(a.dte,'dd-mm-yyyy') dte,to_number(to_char(a.dte, 'rrrrmmdd'))\n" + 
        "from  msal a , jansal b , gt_srch_rslt c \n" + 
        "where a.idn = b.idn and b.mstk_idn = c.stk_idn and b.stt in ('SL','DLV')  \n" +conQ; 
        
          if(vnmLst.equals("") || (!dfr.equals("") && !dto.equals(""))){
                dteWiseSql+=dteConQ;    
            }
              if(!byrIdn.equals("") && !byrIdn.equals("0")){
                   dteWiseSql+=" and a.nme_idn = ? ";
                  ary.add(byrIdn);
              }
              if(!addatIdn.equals("") && !addatIdn.equals("0") ){
                   dteWiseSql+=" and a.aadat_idn = ? or  a.mbrk1_idn = ?";
                  ary.add(addatIdn);
                  ary.add(addatIdn);
                  
              }
              
              if(!brokerIdn.equals("") && !brokerIdn.equals("0")){
                   dteWiseSql+=" and a.mbrk2_idn= ? or a.mbrk3_idn = ? or a.mbrk4_idn = ?";
                  ary.add(brokerIdn);
                  ary.add(brokerIdn);
                  ary.add(brokerIdn);
             }
              dteWiseSql+=" group by to_char(a.dte,'dd-mm-yyyy'),to_number(to_char(a.dte, 'rrrrmmdd')) order by to_number(to_char(a.dte, 'rrrrmmdd')) ";
              

             ArrayList outLst = db.execSqlLst("search Result", dteWiseSql, ary);
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet rs = (ResultSet)outLst.get(1);

        try {
            while (rs.next()) {
                HashMap pktPrpMap = new HashMap();
                String ctsSal = util.nvl(rs.getString("ctsSal"));
                 String salRte = util.nvl(rs.getString("salRte"));
                 String qtySal = util.nvl(rs.getString("qtySal"));
                 String vlu = util.nvl(rs.getString("vlu"));
                 String date = util.nvl(rs.getString("dte"));
                pktPrpMap.put("ctsSal", ctsSal);
                pktPrpMap.put("salRte", salRte);
                pktPrpMap.put("qtySal", qtySal);
                pktPrpMap.put("vlu", vlu);
                pktPrpMap.put("date", date);
                dateWiseList.add(pktPrpMap);
            }
            rs.close(); pst.close();
            
            ary = new ArrayList();
               String gtTtl = "select sum(b.qty) qtySal ,   to_char(trunc(sum(b.cts),2),'99999990.00') ctsSal , trunc(((sum(b.cts*nvl(b.fnl_sal,b.quot)/nvl(decode('"+cnt+"','kj',1,a.exh_rte),1)) / sum(b.cts)))) salRte , to_char(trunc(sum(b.cts*nvl(b.fnl_sal, b.quot)/nvl(decode('"+cnt+"','kj',1,a.exh_rte),1)), 2),'9999999999990.00') vlu "+
                           " from msal a , jansal b , gt_srch_rslt c where a.idn = b.idn and b.mstk_idn = c.stk_idn and b.stt in ('SL','DLV') "+conQ;
            if(vnmLst.equals("") || (!dfr.equals("") && !dto.equals(""))){
             gtTtl+=dteConQ;    
             }
            if(!byrIdn.equals("") && !byrIdn.equals("0")){
                gtTtl+=" and a.nme_idn = ? ";
               ary.add(byrIdn);
            }
               if(!addatIdn.equals("") && !addatIdn.equals("0") ){
                    gtTtl+=" and a.aadat_idn = ? or  a.mbrk1_idn = ?";
                   ary.add(addatIdn);
                   ary.add(addatIdn);
                   
               }
               
               if(!brokerIdn.equals("") && !brokerIdn.equals("0")){
                    gtTtl+=" and a.mbrk2_idn= ? or a.mbrk3_idn = ? or a.mbrk4_idn = ?";
                   ary.add(brokerIdn);
                   ary.add(brokerIdn);
                   ary.add(brokerIdn);
               }
            

            outLst = db.execSqlLst("gtTtl", gtTtl, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
            while(rs.next()){
             String qty = util.nvl(rs.getString("qtySal"));
             String cts = util.nvl(rs.getString("ctsSal"));
             String vlu = util.nvl(rs.getString("vlu"));
             req.setAttribute("qty", qty);
             req.setAttribute("cts", cts);
             req.setAttribute("vlu", vlu);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
           return  dateWiseList;
        
         }
  public ArrayList SearchResult(HashMap params , HttpServletRequest req,HttpServletResponse res){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
    String dfr = util.nvl((String)params.get("dfr"));
    String dto = util.nvl((String)params.get("dto"));
    String vnmLst = util.nvl((String)params.get("vnm"));
    String byrIdn =  util.nvl((String)params.get("byrIdn"));
      String addatIdn =  util.nvl((String)params.get("addatIdn"));
      String brokerIdn =  util.nvl((String)params.get("brokerIdn"));
    ArrayList ary = new ArrayList();
    ArrayList pktList = new ArrayList();
    HashMap dbinfo = info.getDmbsInfoLst();
    String cnt = (String)dbinfo.get("CNT");
    GenericInterface genericInt = new GenericImpl();
    ArrayList vwPrpLst =genericInt.genericPrprVw(req, res, "MIX_SL_VW","MIX_SL_VW");;
      String dteConQ="";
      String conQ="";
      if(info.getBlockLs().equals("Y"))
          conQ=" and a.typ <> 'LS' ";
      if(cnt.equals("ag"))
          conQ=" and a.typ <> 'LS' ";
      
      if(!dfr.equals("") && !dto.equals(""))
          dteConQ = " and trunc(a.dte) between to_date('"+dfr+"' , 'dd-mm-yyyy') and to_date('"+dto+"' , 'dd-mm-yyyy') ";    
      
      
      
    int ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_rslt where vnm='131.001'", new ArrayList());
    
    String  srchQ =  "select sum(b.qty) qtySal ,   to_char(trunc(sum(b.cts),2),'99999990.00') ctsSal , trunc(((sum(b.cts*nvl(b.fnl_sal,b.quot)/nvl(decode('"+cnt+"','kj',1,a.exh_rte),1)) / sum(b.cts)))) salRte , to_char(trunc(sum(b.cts*nvl(b.fnl_sal, b.quot)*nvl(decode('"+cnt+"','kj',1,a.exh_rte),1)), 2),'9999999999990.00') vlu , ";
   String grpStr = " c.stk_idn ,c.qty, c.cts , c.quot , c.vnm , sk1  ";
    for (int i = 0; i < vwPrpLst.size(); i++) {
      
        String fld = "prp_";
        int j = i + 1;
        if (j < 10)
            fld += "00" + j;
        else if (j < 100)
            fld += "0" + j;
        else if (j > 100)
            fld += j;

         grpStr += ", " + fld;
      
        }
    
    srchQ = srchQ+" "+grpStr ;
    int sr=1;
    String rsltQ = srchQ +  " from  msal a , jansal b , gt_srch_rslt c where a.idn = b.idn and b.mstk_idn = c.stk_idn and b.stt in ('SL','DLV')  "+conQ ;
    if(vnmLst.equals("") || (!dfr.equals("") && !dto.equals(""))){
        rsltQ+=dteConQ;    
    }
      if(!byrIdn.equals("") && !byrIdn.equals("0")){
           rsltQ+=" and a.nme_idn = ? ";
          ary.add(byrIdn);
      }
      if(!addatIdn.equals("") && !addatIdn.equals("0") ){
           rsltQ+=" and a.aadat_idn = ? or  a.mbrk1_idn = ?";
          ary.add(addatIdn);
          ary.add(addatIdn);
          
      }
      
      if(!brokerIdn.equals("") && !brokerIdn.equals("0")){
           rsltQ+=" and a.mbrk2_idn= ? or a.mbrk3_idn = ? or a.mbrk4_idn = ?";
          ary.add(brokerIdn);
          ary.add(brokerIdn);
          ary.add(brokerIdn);
     }
      rsltQ+=" group by "+grpStr+" order by sk1 ";

      ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
    try {
        while(rs.next()) {
              HashMap pktPrpMap = new HashMap();
            String stkIdn = util.nvl(rs.getString("stk_idn"));
             String vnm = util.nvl(rs.getString("vnm"));
             String ctsSal = util.nvl(rs.getString("ctsSal"));
              String salRte = util.nvl(rs.getString("salRte"));
              String qtySal = util.nvl(rs.getString("qtySal"));
              String cts = util.nvl(rs.getString("cts"));
              String qty = util.nvl(rs.getString("qty"));
              String quot = util.nvl(rs.getString("quot"));
             String vlu = util.nvl(rs.getString("vlu"));
              pktPrpMap.put("stkIdn", stkIdn);
              pktPrpMap.put("vnm", vnm);
              pktPrpMap.put("salcts", ctsSal);
              pktPrpMap.put("salrte", salRte);
              pktPrpMap.put("qtySal", qtySal);
              pktPrpMap.put("cts", cts);
              pktPrpMap.put("qty", qty);
              pktPrpMap.put("quot", quot);
              pktPrpMap.put("vlu", vlu);
               for(int j=0; j < vwPrpLst.size(); j++){
                 String prp = (String)vwPrpLst.get(j);
                  
                  String fld="prp_";
                  if(j < 9)
                          fld="prp_00"+(j+1);
                  else    
                          fld="prp_0"+(j+1);
                   
                  if (prp.toUpperCase().equals("RAP_DIS"))
                           fld = "rap_dis";
                  if (prp.toUpperCase().equals("RAP_RTE"))
                            fld = "rap_rte";
                  if(prp.toUpperCase().equals("RTE"))
                              fld = "quot";                   
                       
                  String val = util.nvl(rs.getString(fld)) ;
                     
                       pktPrpMap.put(prp, val);
                     }
                          
              pktList.add(pktPrpMap);
            }
        rs.close(); pst.close();
         ary = new ArrayList();
            String gtTtl = "select sum(b.qty) qtySal ,   to_char(trunc(sum(b.cts),2),'99999990.00') ctsSal , trunc(((sum(b.cts*nvl(b.fnl_sal,b.quot)/nvl(decode('"+cnt+"','kj',1,a.exh_rte),1)) / sum(b.cts)))) salRte , to_char(trunc(sum(b.cts*nvl(b.fnl_sal, b.quot)/nvl(decode('"+cnt+"','kj',1,a.exh_rte),1)), 2),'9999999999990.00') vlu "+
                        " from msal a , jansal b , gt_srch_rslt c where a.idn = b.idn and b.mstk_idn = c.stk_idn and b.stt in ('SL','DLV') "+conQ;
        if(vnmLst.equals("") || (!dfr.equals("") && !dto.equals(""))){
          gtTtl+=dteConQ;    
          }
        if(!byrIdn.equals("") && !byrIdn.equals("0")){
             gtTtl+=" and a.nme_idn = ? ";
            ary.add(byrIdn);
        }
            if(!addatIdn.equals("") && !addatIdn.equals("0") ){
                 gtTtl+=" and a.aadat_idn = ? or  a.mbrk1_idn = ?";
                ary.add(addatIdn);
                ary.add(addatIdn);
                
            }
            
            if(!brokerIdn.equals("") && !brokerIdn.equals("0")){
                 gtTtl+=" and a.mbrk2_idn= ? or a.mbrk3_idn = ? or a.mbrk4_idn = ?";
                ary.add(brokerIdn);
                ary.add(brokerIdn);
                ary.add(brokerIdn);
            }
       

        outLst = db.execSqlLst("gtTtl", gtTtl, ary);
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        while(rs.next()){
          String qty = util.nvl(rs.getString("qtySal"));
          String cts = util.nvl(rs.getString("ctsSal"));
          String vlu = util.nvl(rs.getString("vlu"));
          req.setAttribute("qty", qty);
          req.setAttribute("cts", cts);
          req.setAttribute("vlu", vlu);
        }
        rs.close(); pst.close();
    } catch (SQLException sqle) {

        // TODO: Add catch code
        sqle.printStackTrace();
    }
    
    
    return pktList;
  }
  
 
  
  public ActionForward saleHis(ActionMapping am, ActionForm form,
                           HttpServletRequest req,
                           HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
     String stkIdn = util.nvl(req.getParameter("stkIdn"));
     StringBuffer sb = new StringBuffer();
     String dto = req.getParameter("dteto");
     String dfr = req.getParameter("dtefr");
     String vnm = req.getParameter("vnm");
      String addatIdn = util.nvl(req.getParameter("addatIdn"));
      String brokerIdn = util.nvl(req.getParameter("brokerIdn"));
      String byrId = util.nvl(req.getParameter("byrId"));
      String dte = util.nvl(req.getParameter("dte"));
      String typ = req.getParameter("typ");
      HashMap dbinfo = info.getDmbsInfoLst();
      String cnt = (String)dbinfo.get("CNT");
      ArrayList ary = new ArrayList();
      String dteConQ="";
      if(!dfr.equals("") && !dto.equals(""))
          dteConQ = " and trunc(a.dte) between to_date('"+dfr+"' , 'dd-mm-yyyy') and to_date('"+dto+"' , 'dd-mm-yyyy') ";    
      else
          dteConQ = " and trunc(a.dte)=trunc(sysdate)";
   
      String saleHis= "select a.idn , form_url_encode(c.fnme) fnme , sum(b.qty) qty ,to_char(trunc(sum(b.cts),2),'99999990.00') cts,\n" + 
      "to_char(trunc(sum(b.cts*nvl(b.fnl_sal, b.quot)/nvl(decode('"+cnt+"','kj',1,a.exh_rte),1)), 2),'9999999999990.00') vlu ,\n" + 
      "trunc(((sum(b.cts*nvl(b.fnl_sal,b.quot)/nvl(decode('"+cnt+"','kj',1,a.exh_rte),1)) / sum(b.cts)))) fnlsal ,to_char(a.dte,'dd-Mon-rrrr') dte,to_number(to_char(b.dte, 'rrrrmmdd')) \n" + 
      "from msal a , jansal b, mnme c,mstk d  where a.idn = b.idn and a.nme_idn = c.nme_idn and b.mstk_idn=d.idn and d.pkt_ty <> 'NR'\n" + 
      "and b.stt in ('SL','DLV') ";
      if(typ.equals("DLV")){
          saleHis= "select a.idn , form_url_encode(c.fnme) fnme , sum(b.qty) qty ,to_char(trunc(sum(b.cts),2),'99999990.00') cts,\n" + 
          "to_char(trunc(sum(b.cts*nvl(b.fnl_sal, b.quot)/nvl(decode('"+cnt+"','kj',1,a.exh_rte),1)), 2),'9999999999990.00') vlu ,\n" + 
          "trunc(((sum(b.cts*nvl(b.fnl_sal,b.quot)/nvl(decode('"+cnt+"','kj',1,a.exh_rte),1)) / sum(b.cts)))) fnlsal ,to_char(a.dte,'dd-Mon-rrrr') dte,to_number(to_char(b.dte, 'rrrrmmdd')) \n" + 
          "from mdlv a , dlv_dtl b, mnme c,mstk d  where a.idn = b.idn and a.nme_idn = c.nme_idn and b.mstk_idn=d.idn and d.pkt_ty <> 'NR'\n" + 
          "and b.stt in ('DLV') ";
      }
      if(!stkIdn.equals("")){
          saleHis+=" and b.mstk_idn=? ";
          ary.add(stkIdn);
      }
      if(!dte.equals("")){
          saleHis+= " and trunc(a.dte) between to_date('"+dte+"' , 'dd-mm-yyyy') and to_date('"+dte+"' , 'dd-mm-yyyy') ";      
      }
      if(vnm.equals("") || (!dfr.equals("") && !dto.equals(""))){
          saleHis+=dteConQ;    
      }
      if(!byrId.equals("") && !byrId.equals("0")){
         saleHis+=" and a.nme_idn = ? ";
          ary.add(byrId);
      }
      
      if(!addatIdn.equals("") && !addatIdn.equals("0")){
          saleHis+=" and a.aadat_idn = ? or a.mbrk1_idn = ? ";
          ary.add(addatIdn);
          ary.add(addatIdn);
         
      }
      
      if(!brokerIdn.equals("") && !brokerIdn.equals("0")){
          saleHis+=" and a.mbrk2_idn= ? or a.mbrk3_idn = ? or a.mbrk4_idn = ?";
          ary.add(brokerIdn);
          ary.add(brokerIdn);
          ary.add(brokerIdn);
         
      }
      saleHis+=" group by a.idn , form_url_encode(c.fnme),to_char(a.dte,'dd-Mon-rrrr'),to_number(to_char(b.dte, 'rrrrmmdd')) order by 8 desc ";
      
    ArrayList outLst = db.execSqlLst("sal His", saleHis, ary);
    PreparedStatement pst = (PreparedStatement)outLst.get(0);
    ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
      sb.append("<sale>");
      sb.append("<id>"+util.nvl(rs.getString("idn"),"0") +"</id>");
      sb.append("<fnme>"+util.nvl(rs.getString("fnme"),"0") +"</fnme>");
      sb.append("<fnlsal>"+util.nvl(rs.getString("fnlsal"),"0") +"</fnlsal>");
      sb.append("<qty>"+util.nvl(rs.getString("qty"),"0") +"</qty>");
      sb.append("<cts>"+util.nvl(rs.getString("cts"),"0") +"</cts>");
      sb.append("<vlu>"+util.nvl(rs.getString("vlu"),"0") +"</vlu>");
      sb.append("<dte>"+util.nvl(rs.getString("dte"),"0") +"</dte>");
      
      sb.append("</sale>");
    }
   rs.close(); pst.close();
   pst.close();
    res.setContentType("text/xml"); 
    res.setHeader("Cache-Control", "no-cache"); 
    res.getWriter().write("<sales>"+sb.toString()+"</sales>");
    return null;
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
                util.updAccessLog(req,res,"Mix Sale Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Mix Sale Report", "init");
            }
            }
            return rtnPg;
            }
}
