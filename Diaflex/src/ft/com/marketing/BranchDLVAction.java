package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.ByrDao;

import ft.com.dao.MAddr;
import ft.com.dao.PktDtl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class BranchDLVAction extends DispatchAction {

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
          BranchDLVForm    udf;
          udf = (BranchDLVForm)af;
          udf.resetAll();
        ArrayList byrList = new ArrayList();
          String pkt_ty = util.nvl(req.getParameter("PKT_TY"));
          util.updAccessLog(req,res,"Branch Delivery", "load");
          ArrayList ary =new ArrayList();
          String sqlQury="select distinct a.nme_idn, a.nme from nme_v a, msal b, mstk d, brc_dlv_dtl c\n" + 
          "where a.nme_idn = b.nme_idn  and b.idn = c.sal_idn and c.mstk_idn=d.idn " ;
          if(!pkt_ty.equals("")){
              sqlQury=sqlQury+" and d.pkt_ty = ?";
              ary.add(pkt_ty);
          }
              sqlQury=sqlQury+" and c.stt not in ('DLV','AV','RT','IS','CL') order by 2";
        
         
        ArrayList  outLst = db.execSqlLst("sqlQuery", sqlQury, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
          while(rs.next()){
            ByrDao byr = new ByrDao();

            byr.setByrIdn(rs.getInt("nme_idn"));
            byr.setByrNme(rs.getString("nme"));
            byrList.add(byr);
          }
          rs.close();
          pst.close();
          udf.setByrList(byrList);
        util.updAccessLog(req,res,"Branch Delivery", "End");
        util.setMdl("Branch Delivery");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("BRNCH_DELIVERY");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("BRNCH_DELIVERY");
        allPageDtl.put("BRNCH_DELIVERY",pageDtl);
        }
        info.setPageDetails(allPageDtl);
          finalizeObject(db, util);
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
          BranchDLVForm    udf = (BranchDLVForm)af;
          SearchQuery      srchQuery=new SearchQuery();
          String ByrNme="";
          util.updAccessLog(req,res,"Branch Delivery", "Fetch");
        ArrayList pkts        = new ArrayList();
        String    byrIdn      = util.nvl((String)udf.getValue("byrIdn"));
        String    saleId      = "";
        String    vnmLst      = util.nvl((String)udf.getValue("vnmLst"));
          String pkt_ty = util.nvl(req.getParameter("PKT_TY"));
        ArrayList    params      = null;
        boolean valid = false;
        HashMap avgdtl=new HashMap();
        Enumeration reqNme = req.getParameterNames();
          HashMap dbmsInfo = info.getDmbsInfoLst();
          String types =util.nvl((String)dbmsInfo.get("BANK"));
          ArrayList vwprpLst = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
//        saleId=util.nvl(req.getParameter("saleId"));
//        while (reqNme.hasMoreElements()) {
//            String paramNm = (String) reqNme.nextElement();
//           if (paramNm.indexOf("cb_memo") > -1) {
//                String val = req.getParameter(paramNm);
//
//                if (saleId.equals("")) {
//                    saleId = val;
//                } else {
//                   saleId = saleId + "," + val;
//                }
//            }
//        }
        
        if(!byrIdn.equals("") && !byrIdn.equals("0")){
            String shpfrmdte = util.nvl((String)udf.getValue("shpfrmdte"));
            String shptodte = util.nvl((String)udf.getValue("shptodte"));
            String conQdte="";
            if(!shpfrmdte.equals("") && !shptodte.equals(""))
            conQdte = " and trunc(a.dte) between to_date('"+shpfrmdte+"' , 'dd-mm-yyyy') and to_date('"+shptodte+"' , 'dd-mm-yyyy') ";
            if(shpfrmdte.equals("") && !shptodte.equals(""))
            conQdte = " and trunc(a.dte) between to_date('"+shptodte+"' , 'dd-mm-yyyy') and to_date('"+shptodte+"' , 'dd-mm-yyyy') ";
            if(!shpfrmdte.equals("") && shptodte.equals(""))
            conQdte = " and trunc(a.dte) between to_date('"+shpfrmdte+"' , 'dd-mm-yyyy') and to_date('"+shpfrmdte+"' , 'dd-mm-yyyy') ";
            
            String checkSql ="select c.dlv_idn,b.vnm  from brc_dlv_dtl a, mstk b , brc_mdlv c,msal d\n" + 
            "  where a.mstk_idn = b.idn and c.idn = a.idn  and a.sal_idn=d.idn and d.nme_idn=?  and a.stt not in ('DLV','RT','AV','IS','CL') \n"+conQdte;
            
            params = new ArrayList();
            params.add(byrIdn);
            if(!pkt_ty.equals("")){
                checkSql=checkSql+" and b.pkt_ty = ?";
                params.add(pkt_ty);
            }
          ArrayList  outLst = db.execSqlLst("check rel", checkSql, params);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs1 = (ResultSet)outLst.get(1);
            while(rs1.next()){
                String salIdnval = util.nvl(rs1.getString("dlv_idn"));
                if (saleId.equals("")) {
                    saleId = salIdnval;
                } else if(saleId.indexOf(salIdnval) < 0) {
                   saleId = saleId + "," + salIdnval;
                }
                String packetid = util.nvl(rs1.getString("vnm"));
                if (vnmLst.equals("")) {
                    vnmLst = packetid;
                } else {
                   vnmLst = vnmLst + "," + packetid;
                }
            }
            rs1.close();
            pst.close();
            vnmLst = util.getVnm(vnmLst);
        }
          
          String pendingsaleIdn=util.nvl(req.getParameter("saleIdn"));
          if(!pendingsaleIdn.equals("")){
              String checkSql ="select c.dlv_idn,b.vnm  from brc_dlv_dtl a, mstk b , brc_mdlv c,msal d\n" + 
              "  where a.mstk_idn = b.idn and c.idn = a.idn  and a.sal_idn=d.idn and a.sal_idn=?  and a.stt not in ('DLV','RT','AV','IS','CL') \n";
              
              params = new ArrayList();
              params.add(pendingsaleIdn);
              if(!pkt_ty.equals("")){
                  checkSql=checkSql+" and b.pkt_ty = ?";
                  params.add(pkt_ty);
              }
            ArrayList  outLst = db.execSqlLst("check rel", checkSql, params);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs1 = (ResultSet)outLst.get(1);
              while(rs1.next()){
                  String salIdnval = util.nvl(rs1.getString("dlv_idn"));
                  if (saleId.equals("")) {
                      saleId = salIdnval;
                  } else if(saleId.indexOf(salIdnval) < 0) {
                     saleId = saleId + "," + salIdnval;
                  }
                  String packetid = util.nvl(rs1.getString("vnm"));
                  if (vnmLst.equals("")) {
                      vnmLst = packetid;
                  } else {
                     vnmLst = vnmLst + "," + packetid;
                  }
              }
              rs1.close();
              pst.close();
              vnmLst = util.getVnm(vnmLst);
          }
          String saleIdn="";
        if(saleId.equals("")){
            boolean isRtn = true;
            if(vnmLst.length()>0){
            int cnt=0;
                params = new ArrayList();
                vnmLst = util.getVnm(vnmLst);
                String checkSql ="select distinct d.nmerel_idn  from brc_dlv_dtl a, mstk b , brc_mdlv c,msal d\n" + 
                "  where a.mstk_idn = b.idn and c.idn = a.idn and a.sal_idn=d.idn and a.stt not in ('DLV','RT','AV','IS','CL') \n" + 
               " and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
                if(!pkt_ty.equals("")){
                  checkSql=checkSql +" and b.pkt_ty=? ";
                 params.add(pkt_ty);
                }
              ArrayList  outLst = db.execSqlLst("check rel", checkSql, params);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs1 = (ResultSet)outLst.get(1);
                while(rs1.next()){
                    cnt++;
                   
                }
                rs1.close();
                pst.close();
                if(cnt==1){   
                    isRtn = false;
                    String saleIdSql = "select distinct c.dlv_idn  from brc_dlv_dtl a, mstk b , brc_mdlv c\n" + 
                    "  where a.mstk_idn = b.idn and c.idn = a.idn and a.stt not in ('DLV','RT','AV','IS','CL') \n" + 
                    "  and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
                    params = new ArrayList();
                    if(!pkt_ty.equals("")){
                    saleIdSql=saleIdSql+" and b.pkt_ty=?";
                    params.add(pkt_ty);
                    }
                   outLst = db.execSqlLst("saleID",saleIdSql, params);
                 pst = (PreparedStatement)outLst.get(0);
               rs1 = (ResultSet)outLst.get(1);
                    while(rs1.next()){
                        String salIdnval = util.nvl(rs1.getString("dlv_idn"));
                        if (saleId.equals("")) {
                            saleId = salIdnval;
                        } else {
                           saleId = saleId + "," + salIdnval;
                        }
                    }
                    rs1.close();
                   pst.close();
                    String saleIdnSql = "select distinct a.sal_idn  from brc_dlv_dtl a, mstk b , brc_mdlv c\n" + 
                    "  where a.mstk_idn = b.idn and c.idn = a.idn and a.stt not in ('DLV','RT','AV','IS','CL') \n" + 
                    "  and  ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
                    params = new ArrayList();
                    if(!pkt_ty.equals("")){
                    saleIdSql=saleIdSql+" and b.pkt_ty=? ";
                    params.add(pkt_ty);
                    }
                  outLst = db.execSqlLst("saleID",saleIdnSql, params);
                  pst = (PreparedStatement)outLst.get(0);
                  rs1 = (ResultSet)outLst.get(1);
                    while(rs1.next()){
                        String salIdnval = util.nvl(rs1.getString("sal_idn"));
                        if (saleIdn.equals("")) {
                            saleIdn = salIdnval;
                        } else {
                           saleIdn = saleIdn + "," + salIdnval;
                        }
                    }
                    rs1.close();
                    pst.close();
                }
            }
            if(isRtn){
                req.setAttribute("RTMSG", "Please verify packets");
               return load(am, af, req, res);
            }
        }
        
        if(!saleId.equals("")) {
        saleId = util.getVnm(saleId);
        String getMemoInfo ="     select ms.nme_idn, ms.nmerel_idn,byr.get_trms(a.nmerel_idn) trms, byr.get_nm(ms.nme_idn) byr,a.exh_rte, a.inv_nme_idn , a.fnl_trms_idn , a.inv_addr_idn , \n" + 
        "                 sum(b.qty) qty,sum(b.cts) cts,trunc(sum(trunc(b.cts,2)*nvl(b.fnl_sal, b.quot)), 2) vlu,\n" + 
        "                 trunc(((sum(trunc(b.cts,2)*(nvl(b.fnl_sal, b.quot)/a.exh_rte)) / sum(trunc(b.cts,2)*c.rap_rte))*100) - 100, 2) avg_dis ,\n" + 
        "                 trunc(((sum(trunc(b.cts,2)*nvl(b.fnl_sal, b.quot)) / sum(trunc(b.cts,2))))) avg_Rte \n" + 
        "                 from brc_mdlv a , brc_dlv_dtl b ,msal ms, mstk c where a.dlv_idn in ("+saleId+") and b.stt not in  ('DLV','RT','AV','IS','CL') and b.sal_idn=ms.idn  and a.idn=b.idn and b.mstk_idn=c.idn \n" ;
       params = new ArrayList();
            
       if(!byrIdn.equals("") && !byrIdn.equals("0")){
           getMemoInfo+=" and ms.nme_idn=?";
           params.add(byrIdn);
       }
       if(!saleIdn.equals("")){
           getMemoInfo+=" and ms.idn in ("+saleIdn+")";
       }
       
       if(!pkt_ty.equals("")){
           getMemoInfo+=" and c.pkt_ty=?  ";
           params.add(pkt_ty);
       }
       getMemoInfo+=" group by ms.nme_idn, ms.nmerel_idn, byr.get_trms(a.nmerel_idn),\n" + 
        "           byr.get_nm(ms.nme_idn), a.exh_rte , a.inv_nme_idn , a.fnl_trms_idn , a.inv_addr_idn  ";
         ArrayList outLst = db.execSqlLst(" Memo Info", getMemoInfo, params);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet mrs = (ResultSet)outLst.get(1);

        if (mrs.next()) {
        
            udf.setValue("trmsLb", mrs.getString("trms"));
          udf.setNmeIdn(mrs.getInt("nme_idn"));
          udf.setRelIdn(mrs.getInt("nmerel_idn"));
          udf.setByr(mrs.getString("byr"));
            udf.setValue("rc_qty", mrs.getString("qty"));
            udf.setValue("rc_cts", mrs.getString("cts"));
            udf.setValue("rc_vlu", mrs.getString("vlu"));
          udf.setValue("rc_avgPrc", mrs.getString("avg_Rte"));
          udf.setValue("rc_avgDis", mrs.getString("avg_dis"));
          udf.setInvByrIdn(mrs.getInt("inv_nme_idn"));
          udf.setInvTrmsIdn(mrs.getInt("fnl_trms_idn"));
          udf.setInvAddrIdn(mrs.getInt("inv_addr_idn"));
          udf.setByrIdn(String.valueOf(mrs.getInt("nme_idn")));
            String exh_rte = util.nvl( mrs.getString("exh_rte"),"1") ;
            ByrNme= util.nvl((String) mrs.getString("byr")) ;
            params = new ArrayList();
            String mprpStr = "";
            String mdlPrpsQ = "Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||\n" + 
            "Upper(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT')) \n" + 
            "str From Rep_Prp Rp Where rp.MDL = ? order by srt " ;
            params = new ArrayList();
            params.add("MEMO_RTRN");
          ArrayList outLst1 = db.execSqlLst("mprp ", mdlPrpsQ , params);
          PreparedStatement pst2 = (PreparedStatement)outLst1.get(0);
          ResultSet rs2 = (ResultSet)outLst1.get(1);

            while(rs2.next()) {
            String val = util.nvl((String)rs2.getString("str"));
            mprpStr = mprpStr +" "+val;
            }
            rs2.close();
            pst2.close();
                params = new ArrayList();
                String getPktData =
                    " with STKDTL as  ( Select b.sk1,a.idn ,a.sal_idn,a.dlv_idn,b.cert_no certno, a.qty ,a.mstk_idn,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , \n" + 
                    "st.mprp, nvl(fnl_sal, quot) memoQuot,trunc(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot), 2) vlu, quot, nvl(fnl_sal, quot) fnl_sal , \n" + 
                    "DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), b.vnm) vnm , b.cts, nvl(b.upr, b.cmp) rate, \n" + 
                    "b.rap_rte raprte, a.stt,decode(nvl(a.flg,'NO'),'PR','YES','NO' ) flg,to_char(trunc(a.cts,2) * b.rap_rte, '99999999990.00') rapVlu,\n" + 
                    "trunc((((nvl(a.fnl_sal,a.quot)/1.0)-nvl(a.rte,0))/greatest(a.rte,1))*100, 2) plper,\n" + 
                    "b.stt pktstt  ,  to_char(decode(rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)),9990.99) dis, \n" + 
                    "to_char(decode(rap_rte, 1, '', trunc(((nvl(fnl_sal, quot)/1.0/greatest(b.rap_rte,1))*100)-100,2)),9990.99) mDis,to_char(a.dte, 'dd-Mon-rrrr') dte  \n" + 
                    "from \n" + 
                    "stk_dtl st,brc_dlv_dtl a,msal ms, mstk b \n" + 
                    "where st.mstk_idn=b.idn and a.sal_idn=ms.idn and a.mstk_idn = b.idn   \n" + 
                    "and a.dlv_idn in (" +saleId+ ") and a.stt not in  ('DLV','RT','AV','IS','CL')";
          
                if(!vnmLst.equals("")){
                getPktData = getPktData + " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
                }
                
                if(!byrIdn.equals("") && !byrIdn.equals("0")){
                    getPktData+=" and ms.nme_idn=?";
                    params.add(byrIdn);
                }
                if(!pkt_ty.equals("")){
                   getPktData=getPktData+" and b.pkt_ty=? ";
                    params.add(pkt_ty);
                }
                
                getPktData = getPktData +" \n"+
                "and exists (select 1 from rep_prp rp where rp.MDL = 'MEMO_RTRN' and st.mprp = rp.prp)  And st.Grp = 1) " +
                " Select * from stkDtl PIVOT " +
                " ( max(atr) " +
                " for mprp in ( "+mprpStr+" ) ) order by 1 " ;

             outLst1 = db.execSqlLst(" memo pkts", getPktData, params);
            pst2 = (PreparedStatement)outLst1.get(0);
           rs2 = (ResultSet)outLst1.get(1);
                String saleIDn="";
                while (rs2.next()) {
                    PktDtl pkt    = new PktDtl();
                    long   pktIdn = rs2.getLong("mstk_idn");
                    String salId = util.nvl(rs2.getString("sal_idn"));
                    if(saleIDn.indexOf(salId)==-1){
                    if(saleIDn.equals(""))
                    saleIDn=salId;
                    else
                    saleIDn=saleIDn+","+salId;
                    }
                    pkt.setPktIdn(pktIdn);
                    pkt.setIdn(util.nvl(rs2.getString("idn")));
                    pkt.setMemoId(util.nvl(rs2.getString("dlv_idn")));
                    pkt.setRapRte(util.nvl(rs2.getString("raprte")));
                    pkt.setQty(util.nvl(rs2.getString("qty")));
                    pkt.setCts(util.nvl(rs2.getString("cts")));
                    pkt.setRte(util.nvl(rs2.getString("rate")));
                    pkt.setMemoQuot(util.nvl(rs2.getString("memoQuot")));
                    pkt.setByrRte(util.nvl(rs2.getString("quot")));
                    pkt.setFnlRte(util.nvl(rs2.getString("fnl_sal")));
                    pkt.setDis(rs2.getString("dis"));
                    pkt.setByrDis(rs2.getString("mDis"));
                    pkt.setVnm(rs2.getString("vnm"));
                    String lStt = rs2.getString("stt");
                    pkt.setValue("flg",util.nvl(rs2.getString("flg")));
                    pkt.setStt(lStt);
                    pkt.setValue("byramt", util.nvl(rs2.getString("vlu")));
                    pkt.setValue("ByrNme", ByrNme);
                    udf.setValue("stt_" + pktIdn, lStt+"_"+pktIdn);
                    for (int v = 0; v < vwprpLst.size(); v++) {
                    String vwPrp = (String)vwprpLst.get(v);
                    String fldName = vwPrp;
                    if(vwPrp.toUpperCase().equals("H&A"))
                    fldName = "H_A";
                    
                    if(vwPrp.toUpperCase().equals("COMMENT"))
                    fldName = "COM1";
                    
                    if(vwPrp.toUpperCase().equals("REMARKS"))
                    fldName = "REM1";
                    
                    if(vwPrp.toUpperCase().equals("COL-DESC"))
                    fldName = "COL_DESC";
                    
                    if(vwPrp.toUpperCase().equals("COL-SHADE"))
                    fldName = "COL_SHADE";
                    
                    if(vwPrp.toUpperCase().equals("FL-SHADE"))
                    fldName = "FL_SHADE";
                    
                    if(vwPrp.toUpperCase().equals("STK-CTG"))
                    fldName = "STK_CTG";
                    
                    if(vwPrp.toUpperCase().equals("STK-CODE"))
                    fldName = "STK_CODE";
                    
                    if(vwPrp.toUpperCase().equals("SUBSIZE"))
                    fldName = "SUBSIZE1";
                    
                    if(vwPrp.toUpperCase().equals("SIZE"))
                    fldName = "SIZE1";
                    
                    if(vwPrp.toUpperCase().equals("MIX_SIZE"))
                    fldName = "MIX_SIZE1";
                    
                    if(vwPrp.toUpperCase().equals("STK-CTG"))
                    fldName = "STK_CTG";
                    
                    if(vwPrp.toUpperCase().equals("CRN-OP"))
                    fldName = "CRN_OP";
                    
                    if(vwPrp.toUpperCase().equals("CRTWT"))
                    fldName = "cts";
                    
                    if(vwPrp.toUpperCase().equals("RAP_DIS"))
                    fldName = "r_dis";
                    
                    if(vwPrp.toUpperCase().equals("RTE"))
                    fldName = "rate";
                    
                    if (vwPrp.toUpperCase().equals("RAP_RTE"))
                    fldName = "raprte";
                        if (vwPrp.toUpperCase().equals("CERT NO."))
                        fldName = "certno";
                        if(vwPrp.toUpperCase().equals("MEM_COMMENT"))
                        fldName = "MEM_COM1";
                    String fldVal = util.nvl((String)rs2.getString(fldName));
                    
                    pkt.setValue(vwPrp, fldVal);
                    }
                    pkts.add(pkt);
                }
            udf.setValue("saleIdns", saleIDn);
                rs2.close();
                pst2.close();
            
            if(pkts!=null && pkts.size()>0)
          
            req.setAttribute("pktsList", pkts);
          info.setValue("PKTS", pkts);
          
          ArrayList trmList = new ArrayList();
          ArrayList ary = new ArrayList();
          trmList = srchQuery.getTermALL(req,res, udf.getNmeIdn());
          udf.setInvTrmsLst(trmList);
          udf.setValue("invByrTrm",udf.getInvTrmsIdn());
          ArrayList byrList = new ArrayList();
          String    getByr  =
             "select nme_idn,  byr.get_nm(nme_idn) byr from mnme a " + " where nme_idn = ? "
             + " or exists (select 1 from nme_grp_dtl c, nme_grp b where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn "
             + " and b.nme_idn = ? and b.typ = 'BUYER') " + " order by 2 ";

          ary = new ArrayList();
          ary.add(String.valueOf(udf.getNmeIdn()));
          ary.add(String.valueOf(udf.getNmeIdn()));
          outLst1 = db.execSqlLst("byr", getByr, ary);
          pst2 = (PreparedStatement)outLst1.get(0);
          rs2 = (ResultSet)outLst1.get(1);
          int nmeIdn = 0;
          while (rs2.next()) {
             ByrDao byr = new ByrDao();
            if(nmeIdn==0)
              nmeIdn = rs2.getInt("nme_idn");
             byr.setByrIdn(rs2.getInt("nme_idn"));

             String nme = util.nvl(rs2.getString("byr"));

             if (nme.indexOf("&") > -1) {
                 nme = nme.replaceAll("&", "&amp;");
             }

             byr.setByrNme(nme);
             byrList.add(byr);
          }
          rs2.close();
          pst2.close();
          udf.setInvByrLst(byrList);
          udf.setValue("invByrIdn", udf.getNmeIdn());
          String brokerSql =
             "select   byr.get_nm(mbrk1_idn) brk1  , mbrk1_comm , mbrk1_paid , byr.get_nm(mbrk2_idn) brk2  ,mbrk2_comm , mbrk2_paid, byr.get_nm(mbrk3_idn) brk3  ,"
             + "mbrk3_comm , mbrk3_paid, byr.get_nm(mbrk4_idn) brk4  ,mbrk4_comm , mbrk4_paid , byr.get_nm(aadat_idn) aaDat  , aadat_paid , aadat_comm  from nmerel where nmerel_idn = ?";

          ary = new ArrayList();
          ary.add(String.valueOf(udf.getRelIdn()));
          outLst1 = db.execSqlLst("", brokerSql, ary);
          pst2 = (PreparedStatement)outLst1.get(0);
          rs2 = (ResultSet)outLst1.get(1);
          if (rs2.next()) {
             udf.setValue("brk1", rs2.getString("brk1"));
             udf.setValue("brk2", rs2.getString("brk2"));
             udf.setValue("brk3", rs2.getString("brk3"));
             udf.setValue("brk4", rs2.getString("brk4"));
             udf.setValue("brk1comm", rs2.getString("mbrk1_comm"));
             udf.setValue("brk2comm", rs2.getString("mbrk2_comm"));
             udf.setValue("brk3comm", rs2.getString("mbrk3_comm"));
             udf.setValue("brk4comm", rs2.getString("mbrk4_comm"));
             udf.setValue("brk1paid", rs2.getString("mbrk1_paid"));
             udf.setValue("brk2paid", rs2.getString("mbrk2_paid"));
             udf.setValue("brk3paid", rs2.getString("mbrk3_paid"));
             udf.setValue("brk4paid", rs2.getString("mbrk4_paid"));
             udf.setValue("aaDat", rs2.getString("aaDat"));
             udf.setValue("aadatpaid", rs2.getString("aadat_paid"));
             udf.setValue("aadatcomm", rs2.getString("aadat_comm"));
          }
          rs2.close();
          pst2.close();
          ary = new ArrayList();

          String sql =
             "select a.addr_idn , unt_num, bldg ||''|| street ||''|| landmark ||''|| area ||''|| b.city_nm||''|| c.country_nm addr "
             + " from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn = ? order by a.dflt_yn desc ";

            ary.add(String.valueOf(udf.getNmeIdn()));

          ArrayList maddrList = new ArrayList();

          outLst1 = db.execSqlLst("memo pkt", sql, ary);
          pst2 = (PreparedStatement)outLst1.get(0);
          rs2 = (ResultSet)outLst1.get(1);
          while (rs2.next()) {
             MAddr addr = new MAddr();

             addr.setIdn(rs2.getString("addr_idn"));
             addr.setAddr(rs2.getString("addr"));
             maddrList.add(addr);
          }
          rs2.close();
          pst2.close();
          
          types=types.replaceAll(",", "','");
          String conQ =" and typ in('GROUP','BANK') ";
          if(!types.equals(""))
          conQ =" and typ in('"+types+"') ";
          String bankSql = "select nme_idn, fnme , flg1  from mnme  where 1 = 1 and vld_dte is null  "+conQ;
          ArrayList bankList = new ArrayList();
          ArrayList groupList = new ArrayList();
          outLst1 = db.execSqlLst("bank Sql", bankSql, new ArrayList());
          pst2 = (PreparedStatement)outLst1.get(0);
          rs2 = (ResultSet)outLst1.get(1);
          while(rs2.next()){
             MAddr addr = new MAddr();
             addr.setIdn(rs2.getString("nme_idn"));
             addr.setAddr(rs2.getString("fnme"));
             bankList.add(addr);
              String flg1= util.nvl(rs2.getString("flg1"));
              if(flg1.equals("BDY"))
                  udf.setValue("comIdn", rs2.getString("nme_idn"));
          }
          rs2.close();
          pst2.close();
          String companyQ="select a.nme_idn, a.fnme  from mnme A , nme_dtl b where 1 = 1  " +
          "and a.nme_idn=b.nme_idn and  b.mprp='PERFORMA' and b.txt='YES' " +
          "and a.vld_dte is null  and typ = 'GROUP'";
          outLst1 = db.execSqlLst("Group Sql", companyQ, new ArrayList());
          pst2 = (PreparedStatement)outLst1.get(0);
          rs2 = (ResultSet)outLst1.get(1);
          while(rs2.next()){
             MAddr addr = new MAddr();
             addr.setIdn(rs2.getString("nme_idn"));
             addr.setAddr(rs2.getString("fnme"));
             groupList.add(addr);
          }
          rs2.close();
          pst2.close();
          ArrayList bnkAddrList = new ArrayList();
            boolean dfltbankgrp=true;
            String banknmeIdn=  "" ;
            ary = new ArrayList();
            ary.add(String.valueOf(udf.getNmeIdn()));
            String setbnkCouQ="select bnk_idn,courier from  mdlv where idn in(select max(idn) from mdlv where nme_idn=?)";
          outLst1 = db.execSqlLst("setbnkCouQ", setbnkCouQ, ary);
          pst2 = (PreparedStatement)outLst1.get(0);
          rs2 = (ResultSet)outLst1.get(1);
            if(rs2.next()){
                banknmeIdn=util.nvl(rs2.getString("bnk_idn"));
                udf.setValue("courier", util.nvl(rs2.getString("courier"),"NA"));
            }
            rs2.close();
            pst2.close();
            if(!banknmeIdn.equals("")){
                String defltBnkQ="select b.nme_idn bnkidn,a.addr_idn addr_idn,  a.bldg ||''|| a.street ||''|| a.landmark ||''|| a.area addr \n" + 
                "from maddr a, mnme b\n" + 
                "where 1 = 1 \n" + 
                "and a.nme_idn = b.nme_idn  and b.typ in('GROUP','BANK')\n" + 
                "and b.nme_idn = ? order by a.dflt_yn desc";
                ary = new ArrayList();
                ary.add(banknmeIdn);
              outLst1 = db.execSqlLst("defltBnkQ", defltBnkQ, ary);
              pst2 = (PreparedStatement)outLst1.get(0);
              rs2 = (ResultSet)outLst1.get(1);
                while(rs2.next()){
                    udf.setValue("bankIdn", util.nvl(rs2.getString("bnkidn"),"NA"));
                    MAddr addr = new MAddr();
                    addr.setIdn(rs2.getString("addr_idn"));
                    addr.setAddr(rs2.getString("addr"));
                    bnkAddrList.add(addr);
                }
                rs2.close();   
                pst2.close();
                dfltbankgrp=false;
            }
            if(dfltbankgrp){
          String defltBnkQ="select c.flg1, c.nme_idn bnkidn,b.mprp,b.txt,a.addr_idn addr_idn,  a.bldg ||''|| a.street ||''|| a.landmark ||''|| a.area addr  \n" +
          "        from maddr a,nme_dtl b , mnme c\n" +
          "        where 1 = 1 and a.nme_idn=b.nme_idn\n" +
          "        and a.nme_idn = c.nme_idn  and c.typ in('GROUP','BANK')\n" +
          "        and b.mprp='PERFORMABANK' and b.txt='Y'\n" +
          "        and b.vld_dte is null";
              outLst1 = db.execSqlLst("defltBnkQ", defltBnkQ, new ArrayList());
              pst2 = (PreparedStatement)outLst1.get(0);
              rs2 = (ResultSet)outLst1.get(1);
          while(rs2.next()){
           
             MAddr addr = new MAddr();
             addr.setIdn(rs2.getString("addr_idn"));
             addr.setAddr(rs2.getString("addr"));
             bnkAddrList.add(addr);
            String flg1= util.nvl(rs2.getString("flg1"));
            if(flg1.equals("BDY"))
                udf.setValue("bankIdn", rs2.getString("addr_idn"));
          }
          rs2.close();
          pst2.close();
            }
//          ArrayList courierList = new ArrayList();
//          String courierQ="select chr_fr,dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and\n" + 
//          "             b.mdl = 'JFLEX' and b.nme_rule = 'COURIER' and a.til_dte is null order by a.srt_fr";
//          rs = db.execSql("courierQ", courierQ, new ArrayList());
//          while(rs.next()){
//              MAddr addr = new MAddr();
//              addr.setIdn(util.nvl(rs.getString("chr_fr")));
//              addr.setAddr(util.nvl(rs.getString("dsc")));
//              courierList.add(addr);
//          }
//          rs.close();
          String locationSql =" select txt from nme_dtl where nme_idn = ? and mprp = 'LOC' ";
          params = new ArrayList();
          params.add(String.valueOf(udf.getNmeIdn()));
          outLst1 = db.execSqlLst("LocationSql", locationSql, params);
          pst2 = (PreparedStatement)outLst1.get(0);
          rs2 = (ResultSet)outLst1.get(1);
          while(rs2.next()){
            udf.setValue("location",util.nvl(rs2.getString("txt")));
          }
            rs2.close();
            pst2.close();
          ArrayList courierList = srchQuery.getcourierList(req, res);
          udf.setCourierList(courierList);
          udf.setBnkAddrList(bnkAddrList);
          udf.setGroupList(groupList);
          udf.setBankList(bankList);
          udf.setInvAddLst(maddrList);
          req.setAttribute("view", "Y");
        
         
          
        }
        mrs.close();
        pst.close();
        }else {
                  req.setAttribute("error", "please select memoIds of one buyer");
                  req.setAttribute("view", "N");
                  return  load(am, af, req, res);
        }
        req.setAttribute("view", "Y");
          
      }
      
       util.updAccessLog(req,res,"Branch Delivery", "END");
       util.setMdl("Branch Delivery");
       
       HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
       HashMap pageDtl=(HashMap)allPageDtl.get("PERFORMA_INV");
       if(pageDtl==null || pageDtl.size()==0){
       pageDtl=new HashMap();
       pageDtl=util.pagedef("PERFORMA_INV");
       allPageDtl.put("PERFORMA_INV",pageDtl);
       }
       info.setPageDetails(allPageDtl);
       finalizeObject(db, util);
     return am.findForward("load");
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
      BranchDLVForm    udf;
      ArrayList params = null;

        udf = (BranchDLVForm)af;
          util.updAccessLog(req,res,"Branch Delivery", "Save");
      ArrayList pkts      = new ArrayList();
     ArrayList dlvIdnLst = new ArrayList();
      pkts = (ArrayList) info.getValue("PKTS");    // udf.getPkts();

      for (int i = 0; i < pkts.size(); i++) {
          PktDtl pkt     = (PktDtl) pkts.get(i);
          long   lPktIdn = pkt.getPktIdn();
          String Idn = pkt.getIdn();
          String vnm = pkt.getVnm();
          String lSttLst    = util.nvl((String)udf.getValue("stt_" + lPktIdn));
          if(lSttLst.indexOf("_")!=-1){
          String lstt[] = lSttLst.split("_");
          String lStt = util.nvl(lstt[0],"DLV");
          if(!lStt.equals("RC")&&!lStt.equals("INV")){
          params = new ArrayList();
          params.add(Idn);
          params.add(Long.toString(lPktIdn));
          params.add(lStt);
          String brancStt = "BRC_DLV_PKG.CHG_STT(pIdn => ?, pStkIdn => ?, pStt => ?)";
          int ct = db.execCall("brc_dlv", brancStt, params);
          if(ct>0){
              if(!dlvIdnLst.contains(Idn)){
            dlvIdnLst.add(Idn);
              }
          }
          }}
        }
          if(dlvIdnLst.size()>0){
      req.setAttribute("msg", "Process Done Successfully...."+dlvIdnLst.toString());
          }else{
              req.setAttribute("msg", "Error in process");
      
          }
      util.updAccessLog(req,res,"Branch Delivery", "End");
      util.setMdl("Branch Delivery");
          finalizeObject(db, util);
      return load(am, af, req, res);
      }
  }
    public BranchDLVAction() {
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
                  util.updAccessLog(req,res,"Branch Delivery", "Unauthorized Access");
                  else
              util.updAccessLog(req,res,"Branch Delivery", "init");
              }
              }
              return rtnPg;
              }
      public void finalizeObject(DBMgr db, DBUtil util){
          try {
              db=null;
              util=null;
          } catch (Throwable t) {
              // TODO: Add catch code
              t.printStackTrace();
          }
         
      }
  }
