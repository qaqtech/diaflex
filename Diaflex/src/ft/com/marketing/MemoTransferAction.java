package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.MemoTransThread;
import ft.com.dao.ByrDao;

import ft.com.dao.PktDtl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.Enumeration;

import java.util.HashMap;

import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MemoTransferAction extends DispatchAction {

    public MemoTransferAction() {
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
       MemoTransferForm form = (MemoTransferForm)af;
       SearchQuery query = new SearchQuery();
       form.resetAll();
     ArrayList byrList = new ArrayList();
       String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
       String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
       String usrFlg=util.nvl((String)info.getUsrFlg());
       ArrayList rolenmLst=(ArrayList)info.getRoleLst();
       String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
       String pktTy = util.nvl(req.getParameter("PKT_TY"));
       String conQ="";
       if(pktTy.equals(""))
          pktTy="NR";
       ArrayList ary=new ArrayList();
       ary.add(pktTy);
       if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
       }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
       conQ +=" and nvl(nv.grp_nme_idn,0) =? "; 
       ary.add(dfgrpnmeidn);
       } 
       if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
               if(util.nvl((String)info.getDmbsInfoLst().get("EMP_LOCK")).equals("Y")){
                   conQ += " and (nv.emp_idn= ? or nv.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
                   ary.add(dfNmeIdn);
                   ary.add(dfNmeIdn);
               }
       }
       
     String    getMemoByr  =
         "select distinct nv.nme_idn,nv.byr from memo_pndg_v nv where nv.pkt_ty=? "+conQ+ " order by 2";

     ArrayList  outLst = db.execSqlLst("byr", getMemoByr, ary);
     PreparedStatement pst = (PreparedStatement)outLst.get(0);
     ResultSet  rs = (ResultSet)outLst.get(1);

     while (rs.next()) {
         ByrDao byr = new ByrDao();

         byr.setByrIdn(rs.getInt("nme_idn"));
         byr.setByrNme(rs.getString("byr"));
         byrList.add(byr);
     }
     rs.close();
       pst.close();
     form.setMemoByrList(byrList);
       
       ArrayList memoTypList = query.getMemoType(req, res);
       form.setMemoTypList(memoTypList);
       ArrayList expDaysList = query.getExpDay(req,res);
        if(expDaysList !=null && expDaysList.size()>0){
        form.setValue("ExpDayList", expDaysList);
        }
      req.setAttribute("PKT_TY", pktTy);
       
       HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
       HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_TRF");
       if(pageDtl==null || pageDtl.size()==0){
       pageDtl=new HashMap();
       pageDtl=util.pagedef("MEMO_TRF");
       allPageDtl.put("MEMO_TRF",pageDtl);
       }
       info.setPageDetails(allPageDtl);
     return am.findForward("load");   

   }}
  
  public ActionForward view(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
       MemoTransferForm udf = (MemoTransferForm)af;
       String view = util.nvl((String)udf.getValue("fetch"));
       String trans = util.nvl((String)udf.getValue("trans"));
       String PKT_TY = util.nvl((String)req.getAttribute("PKT_TY"));
       ArrayList pktsList = new ArrayList();
       ArrayList memoList = new ArrayList();
       String memoIdn="";
       SearchQuery srchQuery = new SearchQuery();
       Enumeration reqNme = req.getParameterNames();
        while (reqNme.hasMoreElements()) {
           String paramNm = (String) reqNme.nextElement();
           if (paramNm.indexOf("cb_memo") > -1) {
               String memoval = req.getParameter(paramNm);
               memoIdn = memoIdn+","+memoval;
               memoList.add(memoval);
           }
         }
       if(PKT_TY.equals(""))
           PKT_TY="NR";
         int newMemoIdn =0;
         String  msg="";
       if(!memoIdn.equals("")){
           memoIdn = memoIdn.replaceFirst(",", "");
       if(trans.equals("MEMO") || trans.equals("PKT")){
           ArrayList pktList = new ArrayList();

         if(trans.equals("MEMO")){
         
             String trnsByrTrm = util.nvl((String)udf.getValue("trnsByrTrm"));
             String memoTyp = util.nvl((String)udf.getValue("memoTyp"));
             String bryIdn = util.nvl((String)udf.getValue("party"));
              String genHdr="memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ? , pTyp => ? , pflg => ? , pMemoIdn => ?)";
               ArrayList ary = new ArrayList();
               ary.add(bryIdn);
               ary.add(trnsByrTrm);
               ary.add("IS");
               ary.add(memoTyp);
               ary.add("NN");
               if(memoTyp.equals("E")){
                   ary.add(util.nvl((String)udf.getValue("day")));
                   ary.add(util.nvl((String)udf.getValue("extme")));
                   ary.add(util.nvl((String)udf.getValue("extconf")));
                   genHdr = "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ?  , pTyp => ? ,pflg => ?  , pExpDys=>? , pExpTm => ? , pExtCnfPct=>?, pMemoIdn => ?)";
               }
               ArrayList out = new ArrayList();
               out.add("I");
               CallableStatement cst = null;

               cst = db.execCall("MKE_HDR ",genHdr,ary, out);
               newMemoIdn = cst.getInt(ary.size()+1);
           cst.close();
           cst=null;
               if(newMemoIdn > 0){
          String qury="select mstk_idn ,idn from jandtl where DECODE (typ,'O','IS','K','IS','BID','IS', 'I', 'IS','H','IS', 'E', 'IS', 'WH', 'IS', 'WA', 'IS','CS','IS', 'AP' ) = stt and idn in ("+memoIdn+") ";
                 ArrayList  outLst = db.execSqlLst("sql", qury, new ArrayList());
                 PreparedStatement pst = (PreparedStatement)outLst.get(0);
                 ResultSet  rs = (ResultSet)outLst.get(1);

          while(rs.next()){
            String stkIdn = rs.getString("mstk_idn");
            String memoId = rs.getString("idn");
                  HashMap memoDtl = new HashMap();
                  memoDtl.put("stkIdn", stkIdn);
                  memoDtl.put("memoId", memoId);
                  memoDtl.put("newMemoIdn",String.valueOf(newMemoIdn));
                  memoDtl.put("memoTyp", memoTyp);
                  pktList.add(memoDtl);
          // int ct = db.execCall("","MEMO_TRF_PKT(pStkIdn => ?, pFrmIdn => ?, pToIdn => ?, pTyp => ?)", ary);
              }
                   rs.close();
                   pst.close();
               }else{
                  req.setAttribute("msg", "Some Error during memo transfer. please try again..");

              }
         }else{
             
             String trnsByrTrm = util.nvl((String)udf.getValue("trnsfByrTrm"));
             String memoTyp = util.nvl((String)udf.getValue("memofTyp"));
             String byrIdn = util.nvl((String)udf.getValue("fparty"));
             String genHdr="memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ? , pTyp => ? , pflg => ? , pMemoIdn => ?)";
             ArrayList ary = new ArrayList();

             ary.add(byrIdn);
             ary.add(trnsByrTrm);
             ary.add("IS");
             ary.add(memoTyp);
             ary.add("NN");
             if(memoTyp.equals("E")){
                   ary.add(util.nvl((String)udf.getValue("dayf")));
                   ary.add(util.nvl((String)udf.getValue("extmef")));
                   ary.add(util.nvl((String)udf.getValue("extconff")));
                   genHdr = "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ?  , pTyp => ? ,pflg => ?  , pExpDys=>? , pExpTm => ? , pExtCnfPct=>?, pMemoIdn => ?)";
             }
            ArrayList  out = new ArrayList();

             out.add("I");

             CallableStatement cst = null;

             cst = db.execCall("MKE_HDR ",genHdr, ary, out);
             newMemoIdn = cst.getInt(ary.size()+1);
           cst.close();
           cst=null;
             if(newMemoIdn>0){

             for(int i=0;i<memoList.size();i++){
                 String memoVal = (String)memoList.get(i);
                 String[] memoIdVal = memoVal.split("_");
                 String memoId = memoIdVal[1];
                 String stkIdn = memoIdVal[0];
                     HashMap memoDtl = new HashMap();
                     memoDtl.put("stkIdn", stkIdn);
                     memoDtl.put("memoId", memoId);
                     memoDtl.put("newMemoIdn", String.valueOf(newMemoIdn));
                     memoDtl.put("memoTyp", memoTyp);
                     pktList.add(memoDtl);
                 //int ct = db.execCall("","MEMO_TRF_PKT(pStkIdn => ?, pFrmIdn => ?, pToIdn => ?, pTyp => ?)", ary);

                 }}else{
                     req.setAttribute("msg", "Some Error during memo transfer. please try again..");
 
                 }
         
        
         udf.reset();
         }
           if(pktList.size()>0){
               int perThreadCnt=100;
               int pktCnt=pktList.size();
               int noThread = pktCnt/perThreadCnt;
               int divMod =  pktCnt%perThreadCnt;
               if(divMod!=0)
                  noThread=noThread+1;
               int fromIndex=0;
               ExecutorService executor = Executors.newFixedThreadPool(noThread);
               for (int i = 1; i <= noThread; i++) {
                
                   int toIndex=fromIndex+perThreadCnt;
                   if(toIndex>=pktCnt)
                       toIndex=pktCnt;
                  List tilepktLst = new ArrayList();
                  tilepktLst= pktList.subList(fromIndex, toIndex);
                   fromIndex=toIndex;
                   MemoTransThread memoTrans = new MemoTransThread(req,tilepktLst);
                   executor.execute(memoTrans);
               }
               executor.shutdown();            
               while (!executor.isTerminated()) {
               } 
           }
              
         
       }else{
         String getMemoInfo =
             " select a.nme_idn, a.nmerel_idn, byr.get_nm(a.nme_idn) byr,  a.qty, a.cts, a.vlu, a.exh_rte,a.typ  "
             + " from mjan a where a.idn in ("+memoIdn+") and a.stt <> 'RT' ";

         ArrayList ary = new ArrayList();

           ArrayList  outLst = db.execSqlLst(" Memo Info", getMemoInfo, ary);
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet  rs = (ResultSet)outLst.get(1);
         if (rs.next()) {
             int nmeIdn= rs.getInt("nme_idn");
             udf.setValue("byrNme", rs.getString("byr"));
               udf.setValue("byrIdn", nmeIdn);
               udf.setValue("memosTyp", rs.getString("typ"));
               udf.setValue("memoTyp", rs.getString("typ"));
               String exhRte = util.nvl(rs.getString("exh_rte"),"1");
               double exh_rte = Double.parseDouble(exhRte);
              String pktCd = util.nvl(req.getParameter("vnm"));
              pktCd = util.getVnm(pktCd);
             String vnmStr="";
             if(!pktCd.equals("")){                                                  
                 vnmStr = " and ( b.vnm in ("+pktCd+") or b.tfl3 in ("+pktCd+") ) ";
             }

          String getPktData =
                     " select mstk_idn,a.idn,  DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), b.vnm) vnm , b.tfl3 , nvl(fnl_sal, quot) memoQuot, quot, fnl_sal"
                     + ", b.cts, nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte,to_char(trunc(a.cts,2) * b.rap_rte, '99999999990.00') rapVlu, a.stt "
                     + " , decode(rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)) dis,  decode(rap_rte, 1, '', trunc((((nvl(fnl_sal, quot)/"+exh_rte+")/greatest(b.rap_rte,1))*100)-100,2)) mDis "
                     + " from jandtl a, mstk b where a.mstk_idn = b.idn and DECODE (a.typ,'O','IS','K','IS','BID','IS', 'I', 'IS', 'E', 'IS','H','IS', 'WH', 'IS', 'WA', 'IS','CS','IS', 'AP' ) = a.stt and b.pkt_ty=?  and a.idn in ("+memoIdn+") "+vnmStr+" order by b.sk1  ";
                
               
               
                   ArrayList params = new ArrayList();
                   params.add(PKT_TY);
               ArrayList  outLst1 = db.execSqlLst(" memo pkts", getPktData, params);
               PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
               ResultSet  rs1 = (ResultSet)outLst1.get(1);
                 while (rs1.next()) {
                     PktDtl pkt    = new PktDtl();
                     long   pktIdn = rs1.getLong("mstk_idn");
                     pkt.setMemoId(rs1.getString("idn"));
                     pkt.setPktIdn(pktIdn);
                     pkt.setRapRte(util.nvl(rs1.getString("rap_rte")));
                     pkt.setCts(util.nvl(rs1.getString("cts")));
                     pkt.setRte(util.nvl(rs1.getString("rte")));
                     pkt.setMemoQuot(util.nvl(rs1.getString("memoQuot")));
                     pkt.setByrRte(util.nvl(rs1.getString("quot")));
                     pkt.setFnlRte(util.nvl(rs1.getString("fnl_sal")));
                     pkt.setDis(rs1.getString("dis"));
                     pkt.setByrDis(rs1.getString("mDis"));
                     pkt.setVnm(util.nvl(rs1.getString("vnm")));
                     pkt.setValue("memoIdn", String.valueOf(memoIdn));
                     pkt.setValue("rapVlu", util.nvl(rs1.getString("rapVlu")));
                     String lStt = rs1.getString("stt");
                    
                     pkt.setStt(lStt);
                     udf.setValue("stt_" + pktIdn, lStt);
                     String vnm = util.nvl(rs1.getString("vnm"));
                     String tfl3 = util.nvl(rs1.getString("tfl3"));

                     String getPktPrp =
                         " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                         + " from stk_dtl a, mprp b, rep_prp c "
                         + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'MEMO_RTRN' and a.mstk_idn = ? and a.grp=1 "
                         + " order by c.rnk, c.srt ";

                     params = new ArrayList();
                     params.add(Long.toString(pktIdn));

                   ArrayList  outLst2 = db.execSqlLst(" Pkt Prp", getPktPrp, params);
                   PreparedStatement pst2 = (PreparedStatement)outLst2.get(0);
                   ResultSet  rs2 = (ResultSet)outLst2.get(1);
                     while (rs2.next()) {
                         String lPrp = rs2.getString("mprp");
                         String lVal = rs2.getString("val");

                         pkt.setValue(lPrp, lVal);
                     }
                     rs2.close();
                     pst2.close();
                     pktsList.add(pkt);
                 }
                 rs1.close();
                 pst1.close();
                 
                 ArrayList trmList = srchQuery.getTerm(req,res, nmeIdn);
                 udf.setTrmList(trmList);
                 udf.setValue("byrTrm", rs.getInt("nmerel_idn"));
             }
             
           rs.close();
           pst.close();
           req.setAttribute("pktList", pktsList);
         }
         
         
       }else{
           msg="sorry no result found..";
       }
       udf.setValue("fetch", "");
         udf.setValue("trans", "");
         req.setAttribute("view", "yes");
           req.setAttribute("PKT_TY", PKT_TY);
       if(newMemoIdn>0){
         session.setAttribute("memoId", String.valueOf(newMemoIdn));
          return am.findForward("memo"); 

       }else{
         return am.findForward("load"); 
       }

       
       }
  
   }
  
  
  
  public ActionForward trans(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
       MemoTransferForm udf = (MemoTransferForm)af;
       String PKT_TY = util.nvl((String)req.getAttribute("PKT_TY"));
      String  memoTrf = util.nvl((String)info.getDmbsInfoLst().get("MEMOTRF"));
       int newMemoIdn =0;
      int ct=0;    
       
     String trnsByrTrm = util.nvl((String)udf.getValue("trnsfByrTrm"));
     String memoTyp = util.nvl((String)udf.getValue("memofTyp"));
     String byrIdn = util.nvl((String)udf.getValue("fparty"));
     String genHdr="memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ? , pTyp => ? , pflg => ? , pMemoIdn => ?)";
     ArrayList ary = new ArrayList();

     ary.add(byrIdn);
     ary.add(trnsByrTrm);
     ary.add("IS");
     ary.add(memoTyp);
     ary.add("NN");
     if(memoTyp.equals("E")){
           ary.add(util.nvl((String)udf.getValue("dayf")));
           ary.add(util.nvl((String)udf.getValue("extmef")));
           ary.add(util.nvl((String)udf.getValue("extconff")));
           genHdr = "memo_pkg.gen_hdr(pByrId => ?, pRlnIdn => ?, pStt => ?  , pTyp => ? ,pflg => ?  , pExpDys=>? , pExpTm => ? , pExtCnfPct=>?, pMemoIdn => ?)";
     }
     ArrayList out = new ArrayList();

     out.add("I");

     CallableStatement cst = null;

     cst = db.execCall("MKE_HDR ",genHdr, ary, out);
     newMemoIdn = cst.getInt(ary.size()+1);
      cst.close();
      cst=null;
       
       req.setAttribute("PKT_TY", PKT_TY);
           
                    ArrayList pktList = new ArrayList();
                    Enumeration reqNme = req.getParameterNames();
                    while (reqNme.hasMoreElements()) {
                        String paramNm = (String)reqNme.nextElement();
                        if (paramNm.indexOf("cb_memo") > -1) {
                            String memoval = req.getParameter(paramNm);
                            String[] valLst = memoval.split("_");
                            String stkIdn = valLst[0];
                            String memoId = valLst[1];
                            if (newMemoIdn > 0) {
                             HashMap memoDtl = new HashMap();
                             memoDtl.put("stkIdn", stkIdn);
                             memoDtl.put("memoId", memoId);
                             memoDtl.put("newMemoIdn", String.valueOf(newMemoIdn));
                             memoDtl.put("memoTyp", memoTyp);
                             pktList.add(memoDtl);
                          }
                        }
                    }
       
       if(pktList.size()>0){
                 int perThreadCnt=100;
                 int pktCnt=pktList.size();
                 int noThread = pktCnt/perThreadCnt;
                 int divMod =  pktCnt%perThreadCnt;
                 if(divMod!=0)
                    noThread=noThread+1;
                 int fromIndex=0;
                 ExecutorService executor = Executors.newFixedThreadPool(noThread);
                 for (int i = 1; i <= noThread; i++) {
                  
                     int toIndex=fromIndex+perThreadCnt;
                     if(toIndex>=pktCnt)
                         toIndex=pktCnt;
                    List tilepktLst = new ArrayList();
                    tilepktLst= pktList.subList(fromIndex, toIndex);
                     fromIndex=toIndex;
                     MemoTransThread memoTrans = new MemoTransThread(req,tilepktLst);
                     executor.execute(memoTrans);
                 }
                 executor.shutdown();            
                 while (!executor.isTerminated()) {
                 } 
         session.setAttribute("memoId", String.valueOf(newMemoIdn));
        return am.findForward("memo"); 
       }else{
        req.setAttribute("msg", "Some Error during memo transfer. please try again..");
        return am.findForward("load");  
       }
    }
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
              util.updAccessLog(req,res,"Memo Transfer", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Memo Transfer", "init");
          }
          }
          return rtnPg;
          }
  
  
}
