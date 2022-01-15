package ft.com.marketing;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;
import ft.com.dao.ByrDao;
import ft.com.dao.MAddr;
import ft.com.dao.MNme;
import ft.com.dao.PktDtl;
import ft.com.dao.TrmsDao;

import ft.com.generic.GenericImpl;

import ft.com.generic.GenericInterface;

import java.io.IOException;

import java.io.OutputStream;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class SaleDeliveryAction extends DispatchAction {

    public SaleDeliveryAction() {
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
           SaleDeliveryForm udf = (SaleDeliveryForm) af;
           util.updAccessLog(req,res,"Sale Delivery", "Sale Delivery load");
           ArrayList byrList = new ArrayList();
           String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
           String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
           String usrFlg=util.nvl((String)info.getUsrFlg());
           ArrayList rolenmLst=(ArrayList)info.getRoleLst();
           String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
           String conQ="";
    //            ArrayList rfiddevice = ((ArrayList)info.getRfiddevice() == null)?new ArrayList():(ArrayList)info.getRfiddevice();
    //            if(rfiddevice.size() == 0) {
    //            util.rfidDevice();
    //            }
           ArrayList ary=new ArrayList();
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
               String strTyp="('NR','SMX')";
               String typ = util.nvl(req.getParameter("TYP"));
                   if(!typ.equals(""))
                       strTyp="('"+typ+"')" ;
               
             String    getByr  = "With DLV_PNDG_V as (\n" + 
             "select distinct nme_idn\n" + 
             "from msal a , jansal b , mstk c\n" + 
             "where a.idn = b.idn  and b.mstk_idn = c.idn and c.stt = 'MKSL' and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) and  c.pkt_ty in "+strTyp+"\n" + 
             "and a.stt='IS'  and b.stt = 'SL' and a.typ='SL'  and a.flg1='CNF'\n" + 
             ")\n" + 
             "select n.nme_idn,n.nme byr\n" + 
             "from nme_v n, dlv_pndg_v j\n" + 
             "where n.nme_idn = j.nme_idn \n" + conQ+
             "order by 2";
             ArrayList  outLst = db.execSqlLst("byr", getByr, ary);
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
           udf.setByrLst(byrList);
               udf.setValue("srchRef", "vnm");
           HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
           HashMap pageDtl=(HashMap)allPageDtl.get("DELIVERY_CONFIRMATION");
           if(pageDtl==null || pageDtl.size()==0){
           pageDtl=new HashMap();
           pageDtl=util.pagedef("DELIVERY_CONFIRMATION");
           allPageDtl.put("DELIVERY_CONFIRMATION",pageDtl);
           }
           info.setPageDetails(allPageDtl);
             util.updAccessLog(req,res,"Sale Delivery", "Sale Delivery load done");
               udf.setValue("PKTTYP", typ);
               finalizeObject(db,util);

           return am.findForward("load");
           }
       }

       public ActionForward loadPkt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
           SaleDeliveryForm udf = (SaleDeliveryForm) af;
           util.updAccessLog(req,res,"Sale Delivery", "Sale Delivery load pkts");
               ArrayList vwprpLst = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
           SearchQuery srchQuery = new SearchQuery();
           info.setDlvPktList(new ArrayList());
           ArrayList pkts        = new ArrayList();
           ArrayList loclock        = new ArrayList();
           String    flnByr      = util.nvl(udf.getPrtyIdn());
           String    saleId      = "";
           String    locpktLst      = "";
           String    vnmLst      = util.nvl((String)udf.getValue("vnmLst"));
           ArrayList    params      = null;
           boolean valid = false,sl_typ=true;
           HashMap avgdtl=new HashMap();
           String ByrNme="";
           HashMap dbinfo = info.getDmbsInfoLst();
           String locPrp = util.nvl((String)dbinfo.get("LOC"),"LOC");
           Enumeration reqNme = req.getParameterNames();
           saleId=util.nvl(req.getParameter("saleId"));
           if(saleId.equals(""))
           saleId=util.nvl((String)req.getAttribute("saleId"));
           util.noteperson();
               HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
               HashMap pageDtl=(HashMap)allPageDtl.get("DELIVERY_CONFIRMATION");
               ArrayList pageList=new ArrayList();
               HashMap pageDtlMap=new HashMap();
               String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="",locvalnotin="";
               
               pageList=(ArrayList)pageDtl.get("LOC_LOCK");
               if(pageList!=null && pageList.size() >0){
                   for(int j=0;j<pageList.size();j++){
                   pageDtlMap=(HashMap)pageList.get(j);
                   locvalnotin=(String)pageDtlMap.get("val_cond");
                   }
               }
           while (reqNme.hasMoreElements()) {
               String paramNm = (String) reqNme.nextElement();
              if (paramNm.indexOf("cb_memo") > -1) {
                   String val = req.getParameter(paramNm);

                   if (saleId.equals("")) {
                       saleId = val;
                   } else {
                      saleId = saleId + "," + val;
                   }
               }
           }
           String vnmString = "";
           if(!vnmLst.equals("")){
                   vnmLst = util.getVnm(vnmLst);
                   String checkValue  =(String)udf.getValue("srchRef");
                   udf.setValue("srchRef",checkValue);
                   if(checkValue.equals("vnm")){
                   vnmString =  " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+")) ";
                   } else if(checkValue.equals("cert")){
                   
                       vnmString =  " and ( b.cert_no in ("+vnmLst+")) ";
                   }
               }
           if(saleId.equals("")){
               boolean isRtn = true;
               if(vnmLst.length()>0){
               int cnt=0;
             
                   
                   String checkSql ="select distinct c.nmerel_idn  from jansal a, mstk b , msal c " + 
                   "where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='SL' and c.typ in ('SL') and c.stt='IS' and c.flg1='CNF' and b.pkt_ty in('NR','SMX') and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA'))" + 
                   vnmString;
                 ArrayList  outLst = db.execSqlLst("check rel", checkSql, new ArrayList());
                 PreparedStatement pst = (PreparedStatement)outLst.get(0);
                 ResultSet  rs = (ResultSet)outLst.get(1);

                   while(rs.next()){
                       cnt++;
                      
                   }
                   rs.close();
                   pst.close();
                   if(cnt==1){   
                       isRtn = false;
                       String saleIdSql = "select distinct c.idn  from jansal a, mstk b , msal c " + 
                       "  where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='SL' and c.typ in ('SL') and c.stt='IS' and c.flg1='CNF' and b.pkt_ty in('NR','SMX') and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA')) " + 
                       vnmString;
                     ArrayList  outLst1 = db.execSqlLst("saleID",saleIdSql, new ArrayList());
                     PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
                     ResultSet  rs1 = (ResultSet)outLst1.get(1);
                       while(rs1.next()){
                           String salIdnval = util.nvl(rs1.getString("idn"));
                           if (saleId.equals("")) {
                               saleId = salIdnval;
                           } else {
                              saleId = saleId + "," + salIdnval;
                           }
                       }
                       rs1.close();
                       pst1.close();
                   }
               }
               if(isRtn){
                   req.setAttribute("RTMSG", "Please verify packets");
                  return load(am, af, req, res);
               }
           }
           
           String    getMemoInfo =
               "select  a.nme_idn, a.nmerel_idn,byr.get_trms(nmerel_idn) trms, byr.get_nm(a.nme_idn) byr,byr.get_nm(c.emp_idn) SalEmp, a.stt, a.inv_nme_idn , a.fnl_trms_idn , a.inv_addr_idn , a.exh_rte exhRte , "
               + " a.typ, a.qty, a.cts, a.vlu, to_char(a.dte, 'dd-Mon-rrrr HH24:mi:ss') dte,a.rmk,a.note_person from msal a , mnme c where  a.typ in ('SL') and a.stt='IS' and a.nme_idn=c.nme_idn and a.flg1='CNF'  ";
           params = new ArrayList();
           if(!flnByr.equals("") && !flnByr.equals("0")){
           getMemoInfo = getMemoInfo+" and a.inv_nme_idn =?  " ;
           params.add(flnByr);
           }
           if(!saleId.equals("")){
           getMemoInfo = getMemoInfo+" and a.idn in ("+saleId+")  " ;

           }
             ArrayList  outLst = db.execSqlLst(" Memo Info", getMemoInfo, params);
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet  mrs = (ResultSet)outLst.get(1);
           if (mrs.next()) {
               udf.setNmeIdn(mrs.getInt("nme_idn"));
               udf.setRelIdn(mrs.getInt("nmerel_idn"));
               udf.setByr(mrs.getString("byr"));
               udf.setTyp(mrs.getString("typ"));
               udf.setQty(mrs.getString("qty"));
               udf.setCts(mrs.getString("cts"));
               udf.setVlu(mrs.getString("vlu"));
               udf.setDte(mrs.getString("dte"));
               udf.setInvByrIdn(mrs.getInt("inv_nme_idn"));
               udf.setValue("cuIdn", mrs.getString("inv_nme_idn"));
               udf.setInvTrmsIdn(mrs.getInt("fnl_trms_idn"));
               udf.setValue("trmsLb", mrs.getString("trms"));
               udf.setInvAddrIdn(mrs.getInt("inv_addr_idn"));
               udf.setByrIdn(String.valueOf(mrs.getInt("nme_idn")));
               udf.setValue("noteperson", util.nvl(mrs.getString("note_person")));
               udf.setValue("rmk", util.nvl(mrs.getString("rmk")));
               HashMap buyerDtlMap=util.getBuyerDtl(String.valueOf(mrs.getInt("nme_idn")));
               udf.setValue("email",util.nvl((String)buyerDtlMap.get("EML")));
               udf.setValue("mobile",util.nvl((String)buyerDtlMap.get("MBL")));
               udf.setValue("office",util.nvl((String)buyerDtlMap.get("OFC")));
               udf.setValue("exhRte", util.nvl(mrs.getString("exhRte"),"1")); 
               udf.setValue("SALEMP", util.nvl(mrs.getString("SalEmp")));
               ByrNme = util.nvl(mrs.getString("byr"));
               double exh_rte = Double.parseDouble(util.nvl(mrs.getString("exhRte"),"1"));
               String getAvgDis = "select sum(a.qty) qty,sum(trunc(a.cts, 2)) cts,decode(b.pkt_ty,'SMX', trunc((sum(trunc(a.cts, 2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts, 2)))*trunc(sum(trunc(a.cts, 2)),2),2) , trunc(sum(trunc(a.cts, 2)*nvl(a.fnl_sal, a.quot)), 2))  vlu,trunc(((sum(trunc(a.cts, 2)*(nvl(a.fnl_sal, a.quot)/"+exh_rte+")) / sum(trunc(a.cts, 2)*b.rap_rte))*100) - 100, 2) byravg_dis ," +
                                 " trunc(((sum(trunc(a.cts, 2)*a.quot) / sum(trunc(a.cts, 2))))) orgavg_Rte,trunc(((sum(trunc(a.cts, 2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts, 2))))) byravg_Rte," + 
                                 "trunc(((sum(trunc(a.cts, 2)*(nvl(a.fnl_sal, a.quot)/"+exh_rte+")) / sum(trunc(a.cts, 2)*b.rap_rte))*100) - 100, 2) orgavg_dis "+
                                  " from jansal a, mstk b , msal c ,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='SL' and c.typ in ('SL') and c.flg1='CNF' and c.nmerel_idn = d.nmerel_idn and c.stt='IS'  and b.pkt_ty in('NR','SMX') "; 
               params = new ArrayList();
               if(!flnByr.equals("") && !flnByr.equals("0")){
               getAvgDis = getAvgDis+" and c.inv_nme_idn =?  " ;
               params.add(flnByr);
               }
               if(!saleId.equals("")){
               getAvgDis = getAvgDis+" and c.idn in ("+saleId+")  " ;
               
               }
               getAvgDis = getAvgDis + vnmString;
               getAvgDis= getAvgDis+" and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA')) GROUP BY b.pkt_ty ";
             ArrayList  outLst1 = db.execSqlLst(" Memo Info", getAvgDis , params);
             PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
             ResultSet  mrs1 = (ResultSet)outLst1.get(1);
               if(mrs1.next()){
                   avgdtl.put("ByrDis",util.nvl(mrs1.getString("byravg_dis")));
                   avgdtl.put("orgDis",util.nvl(mrs1.getString("orgavg_dis")));
                   avgdtl.put("Byr Quot",util.nvl(mrs1.getString("byravg_Rte")));
                   avgdtl.put("org Quot",util.nvl(mrs1.getString("orgavg_Rte")));
               }
               mrs1.close();
               pst1.close();
               String mprpStr = "";
               String mdlPrpsQ = "Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||\n" + 
               "Upper(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT')) \n" + 
               "str From Rep_Prp Rp Where rp.MDL = ? order by srt " ;
               ArrayList ary = new ArrayList();
               ary.add("MEMO_RTRN");
               outLst1 = db.execSqlLst("mprp ", mdlPrpsQ , ary);
              pst1 = (PreparedStatement)outLst1.get(0);
               mrs1 = (ResultSet)outLst1.get(1);
               while(mrs1.next()) {
               String val = util.nvl((String)mrs1.getString("str"));
               mprpStr = mprpStr +" "+val;
               }
               mrs1.close();
               pst1.close();
           String getPktData = " with STKDTL as " +
                       " ( Select b.sk1,b.cert_no certno,a.idn , a.qty ,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , st.mprp, a.mstk_idn, nvl(fnl_sal, quot) memoQuot, quot, nvl(fnl_sal, quot) fnl_sal,to_char(trunc(a.cts, 2) * quot, '99999990.00') orgamt,to_char(trunc(a.cts, 2) * nvl(fnl_sal, quot), '99999990.00') byramt "+
               " , trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)) inc_dys "+
               " , trunc(nvl(fnl_sal, quot)*trunc(a.cts, 2) *((trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)))*12/100/365),2) inc_vlu "
               + " , trunc(a.cts, 2) cts, nvl(b.upr, b.cmp) rate, b.rap_rte raprte,to_char(trunc(a.cts, 2) * b.rap_rte, '99999999990.00') rapVlu, a.stt , DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), b.vnm) vnm , b.tfl3,b.cert_lab  "
               + "  , to_char(decode(b.rap_rte, 1, '', trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2)),9990.99) dis,  to_char(decode(b.rap_rte, 1, '',trunc(((nvl(fnl_sal, quot)/"+exh_rte+"/greatest(b.rap_rte,1))*100)-100,2)),9990.99) mDis "
               + "  ,decode(b.rap_rte, 1, '',trunc(((quot/"+exh_rte+"/greatest(b.rap_rte,1))*100)-100,2)) oDis,to_char(a.dte, 'dd-Mon-rrrr') dte "
               + " from stk_dtl st,jansal a, mstk b , msal c ,  nmerel d where st.mstk_idn=b.idn and a.mstk_idn = b.idn and c.idn = a.idn and a.stt='SL' and c.typ in ('SL') and b.stt='MKSL' and c.flg1='CNF'   and c.nmerel_idn = d.nmerel_idn and c.stt='IS'  and b.pkt_ty in('NR','SMX') ";
           params = new ArrayList();
           if(!flnByr.equals("") && !flnByr.equals("0")){
           getPktData = getPktData+" and c.inv_nme_idn =?  " ;
           params.add(flnByr);
           }
           if(!saleId.equals("")){
           getPktData = getPktData+" and c.idn in ("+saleId+")  " ;
         
           }
               getPktData = getPktData + vnmString;
           getPktData = getPktData +" \n"+
               "and exists (select 1 from rep_prp rp where rp.MDL = 'MEMO_RTRN' and st.mprp = rp.prp)  And st.Grp = 1) " +
               " Select * from stkDtl PIVOT " +
               " ( max(atr) " +
               " for mprp in ( "+mprpStr+" ) ) order by 1 " ;
             outLst1 = db.execSqlLst(" memo pkts", getPktData, params);
             pst1 = (PreparedStatement)outLst1.get(0);
            ResultSet rs = (ResultSet)outLst1.get(1);
           while (rs.next()) {
               PktDtl pkt    = new PktDtl();
               long   pktIdn = rs.getLong("mstk_idn");

               pkt.setPktIdn(pktIdn);
               
               if(!locpktLst.equals(""))
               locpktLst=locpktLst+","+String.valueOf(pktIdn);
               else
               locpktLst=String.valueOf(pktIdn);
               
               pkt.setSaleId(util.nvl(rs.getString("idn")));
               pkt.setRapRte(util.nvl(rs.getString("raprte")));
               pkt.setQty(util.nvl(rs.getString("qty")));
               pkt.setCts(util.nvl(rs.getString("cts")));
               pkt.setRte(util.nvl(rs.getString("rate")));
               pkt.setMemoQuot(util.nvl(rs.getString("memoQuot")));
               pkt.setByrRte(util.nvl(rs.getString("quot")));
               pkt.setFnlRte(util.nvl(rs.getString("fnl_sal")));
               pkt.setDis(rs.getString("dis"));
               pkt.setByrDis(rs.getString("mDis"));
               pkt.setVnm(util.nvl(rs.getString("vnm")));
               pkt.setInc_day(util.nvl(rs.getString("inc_dys")));
               pkt.setInc_vlu(util.nvl(rs.getString("inc_vlu")));
               pkt.setValue("inc_dys", util.nvl(rs.getString("inc_dys")));
               pkt.setValue("inc_vlu", util.nvl(rs.getString("inc_vlu")));
               pkt.setValue("orgamt",util.nvl(rs.getString("orgamt")));  
               pkt.setValue("byramt",util.nvl(rs.getString("byramt")));
               pkt.setValue("orgDis",util.nvl(rs.getString("oDis"))); 
               pkt.setValue("saledte", util.nvl(rs.getString("dte")));
               pkt.setValue("rapVlu", util.nvl(rs.getString("rapVlu")));
               pkt.setValue("ByrNme", ByrNme);
               String lStt = util.nvl(rs.getString("stt")).trim();
               pkt.setStt(lStt);
               udf.setValue("stt_" + pktIdn, lStt);
               String vnm = util.nvl(rs.getString("vnm"));
               String tfl3 = util.nvl(rs.getString("tfl3"));
               pkt.setValue("cert_lab",util.nvl(rs.getString("cert_lab")));
               if(!vnmLst.equals("")){
                   if(vnmLst.indexOf(tfl3)!=-1 && !tfl3.equals("")){
                       if(vnmLst.indexOf("'")==-1)
                           vnmLst =  vnmLst.replaceAll(tfl3,"");
                       else
                           vnmLst =  vnmLst.replaceAll("'"+tfl3+"'", "");
                   } else if(vnmLst.indexOf(vnm)!=-1 && !vnm.equals("")){
                       if(vnmLst.indexOf("'")==-1)
                           vnmLst =  vnmLst.replaceAll(vnm,"");
                       else
                           vnmLst =  vnmLst.replaceAll("'"+vnm+"'", "");
                   }  
               }
                  
                  
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
               String fldVal = util.nvl((String)rs.getString(fldName));
               
               pkt.setValue(vwPrp, fldVal);
               }
               pkts.add(pkt);
               if(sl_typ){
                   ary = new ArrayList();
                   ary.add(Long.toString(pktIdn));
                 ArrayList  outLst2 = db.execSqlLst(" Pkt Prp", "select val from stk_dtl where mstk_idn=? and mprp='SL_TYP' and grp=1", ary);

                 PreparedStatement pst2 = (PreparedStatement)outLst2.get(0);
                 ResultSet  rs2 = (ResultSet)outLst2.get(1);
                   while (rs2.next()) {
                   udf.setValue("sale_typ", rs2.getString("val"));
                   }
                   rs2.close();
                   pst2.close();
                   sl_typ=false;
               }
           }
               rs.close();
               pst.close();
               if(!locvalnotin.equals("")){
               locvalnotin=locvalnotin.replaceAll(",", "','");
               locvalnotin=locvalnotin.replaceAll(" ", "");
               locpktLst=locpktLst.replaceAll(" ", "");
               getPktData="select mstk_idn from stk_dtl where mprp=? and grp=1 and mstk_idn in("+locpktLst+") and val not in('"+locvalnotin+"')"; 
               params=new ArrayList();
               params.add(locPrp);
                 outLst = db.execSqlLst(" memo pkts", getPktData, params);
               pst = (PreparedStatement)outLst.get(0);
               rs = (ResultSet)outLst.get(1);
               while (rs.next()) {
                   loclock.add(rs.getString("mstk_idn"));
               }
               rs.close();
               pst.close();
               req.setAttribute("loclock", loclock);
               }
           }
           mrs.close();
               pst.close();
           if(!vnmLst.equals("")){
               vnmLst = util.pktNotFound(vnmLst);
               req.setAttribute("vnmNotFnd", vnmLst);
           }

           load(am, af, req, res);

            ArrayList trmList = new ArrayList();
            ArrayList ary = new ArrayList();
           trmList = srchQuery.getTermALL(req,res, udf.getNmeIdn());
           udf.setInvTrmsLst(trmList);
           udf.setValue("invByrTrm",udf.getInvTrmsIdn());
           ArrayList byrList = new ArrayList();
           String    getByr  =
               "select nme_idn,  byr.get_nm(nme_idn) byr from mnme a " + " where nme_idn = ? "
               + " or exists (select 1 from nme_grp_dtl c, nme_grp b where a.nme_idn = c.nme_idn and b.nme_grp_idn = c.nme_grp_idn "
               + " and b.nme_idn = ? and b.typ = 'BUYER' and c.vld_dte is null) " + " order by 2 ";

           ary = new ArrayList();
           ary.add(String.valueOf(udf.getNmeIdn()));
           ary.add(String.valueOf(udf.getNmeIdn()));
             outLst = db.execSqlLst("byr", getByr, ary);
             pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
          int nmeIdn = 0;
           while (rs.next()) {
               ByrDao byr = new ByrDao();
              if(nmeIdn==0)
                nmeIdn = rs.getInt("nme_idn");
               byr.setByrIdn(rs.getInt("nme_idn"));

               String nme = util.nvl(rs.getString("byr"));

               if (nme.indexOf("&") > -1) {
                   nme = nme.replaceAll("&", "&amp;");
               }

               byr.setByrNme(nme);
               byrList.add(byr);
           }
           rs.close();
               pst.close();
           udf.setInvByrLst(byrList);
           udf.setValue("invByrIdn", udf.getInvByrIdn());
           String brokerSql =
               "select   byr.get_nm(mbrk1_idn) brk1  , mbrk1_comm , mbrk1_paid , byr.get_nm(mbrk2_idn) brk2  ,mbrk2_comm , mbrk2_paid, byr.get_nm(mbrk3_idn) brk3  ,"
               + "mbrk3_comm , mbrk3_paid, byr.get_nm(mbrk4_idn) brk4  ,mbrk4_comm , mbrk4_paid , byr.get_nm(aadat_idn) aaDat  , aadat_paid , aadat_comm  from nmerel where nmerel_idn = ?";

           ary = new ArrayList();
           ary.add(String.valueOf(udf.getRelIdn()));
             outLst = db.execSqlLst("", brokerSql, ary);
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);

           if (rs.next()) {
               udf.setValue("brk1", rs.getString("brk1"));
               udf.setValue("brk2", rs.getString("brk2"));
               udf.setValue("brk3", rs.getString("brk3"));
               udf.setValue("brk4", rs.getString("brk4"));
               udf.setValue("brk1comm", rs.getString("mbrk1_comm"));
               udf.setValue("brk2comm", rs.getString("mbrk2_comm"));
               udf.setValue("brk3comm", rs.getString("mbrk3_comm"));
               udf.setValue("brk4comm", rs.getString("mbrk4_comm"));
               udf.setValue("brk1paid", rs.getString("mbrk1_paid"));
               udf.setValue("brk2paid", rs.getString("mbrk2_paid"));
               udf.setValue("brk3paid", rs.getString("mbrk3_paid"));
               udf.setValue("brk4paid", rs.getString("mbrk4_paid"));
               udf.setValue("aaDat", rs.getString("aaDat"));
               udf.setValue("aadatpaid", rs.getString("aadat_paid"));
               udf.setValue("aadatcomm", rs.getString("aadat_comm"));
           }
           rs.close();
            pst.close();
           ary = new ArrayList();

           String sql =
               "select a.addr_idn , unt_num, bldg ||''|| street ||''|| landmark ||''|| area ||''|| b.city_nm||''|| c.country_nm addr "
               + " from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn = ? and a.vld_dte is null order by a.dflt_yn desc ";

           ary.add(String.valueOf(udf.getNmeIdn()));

           ArrayList maddrList = new ArrayList();

             outLst = db.execSqlLst("memo pkt", sql, ary);
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
           while (rs.next()) {
               MAddr addr = new MAddr();

               addr.setIdn(rs.getString("addr_idn"));
               addr.setAddr(rs.getString("addr"));
               maddrList.add(addr);
           }
           rs.close();
               pst.close();
    //        String bankSql = "select nme_idn, fnme  from mnme  where 1 = 1 and vld_dte is null  and typ  in('GROUP','BANK')";
    //        ArrayList bankList = new ArrayList();
    //        ArrayList groupList = new ArrayList();
    //        rs = db.execSql("bank Sql", bankSql, new ArrayList());
    //        while(rs.next()){
    //            MAddr addr = new MAddr();
    //            addr.setIdn(rs.getString("nme_idn"));
    //            addr.setAddr(rs.getString("fnme"));
    //            bankList.add(addr);
    //        }
    //        rs.close();
    //        String companyQ="select a.nme_idn, a.fnme  from mnme A , nme_dtl b where 1 = 1  " +
    //        "and a.nme_idn=b.nme_idn and  b.mprp='PERFORMA' and b.txt='YES' " +
    //        "and a.vld_dte is null  and typ = 'GROUP'";
    //        rs = db.execSql("Group Sql", companyQ, new ArrayList());
    //        while(rs.next()){
    //            MAddr addr = new MAddr();
    //            addr.setIdn(rs.getString("nme_idn"));
    //            addr.setAddr(rs.getString("fnme"));
    //            groupList.add(addr);
    //        }
    //        rs.close();
           ArrayList bnkAddrList = new ArrayList();
               boolean dfltbankgrp=true;
               String banknmeIdn=  "" ;
               ary = new ArrayList();
               ary.add(String.valueOf(udf.getNmeIdn()));
               String setbnkCouQ="select bank_id bnk_idn,courier from  msal where idn in(select max(idn) from msal where nme_idn=?)";
             outLst = db.execSqlLst("setbnkCouQ", setbnkCouQ, ary);
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
               if(rs.next()){
                   banknmeIdn=util.nvl(rs.getString("bnk_idn"));
                   udf.setValue("courier", util.nvl(rs.getString("courier"),"NA"));
               }
               rs.close();
               pst.close();
               if(!banknmeIdn.equals("")){
                   String defltBnkQ="select b.nme_idn bnkidn,a.addr_idn addr_idn,  a.bldg ||''|| a.street ||''|| a.landmark ||''|| a.area addr \n" + 
                   "from maddr a, mnme b\n" + 
                   "where 1 = 1 \n" + 
                   "and a.nme_idn = b.nme_idn  and b.typ in('GROUP','BANK')\n" + 
                   "and b.nme_idn = ? order by a.dflt_yn desc";
                   ary = new ArrayList();
                   ary.add(banknmeIdn);
                 outLst = db.execSqlLst("defltBnkQ", defltBnkQ, ary);
                 pst = (PreparedStatement)outLst.get(0);
                 rs = (ResultSet)outLst.get(1);
                   while(rs.next()){
                       udf.setValue("bankIdn", util.nvl(rs.getString("bnkidn"),"NA"));
                       MAddr addr = new MAddr();
                       addr.setIdn(rs.getString("addr_idn"));
                       addr.setAddr(rs.getString("addr"));
                       bnkAddrList.add(addr);
                   }
                   rs.close();  
                   pst.close();
                   dfltbankgrp=false;
               }
               if(dfltbankgrp){
           String defltBnkQ="select c.nme_idn bnkidn,b.mprp,b.txt,a.addr_idn addr_idn,  a.bldg ||''|| a.street ||''|| a.landmark ||''|| a.area addr  \n" + 
           "        from maddr a,nme_dtl b , mnme c\n" + 
           "        where 1 = 1 and a.nme_idn=b.nme_idn\n" + 
           "        and a.nme_idn = c.nme_idn  and c.typ in('GROUP','BANK')\n" + 
           "        and b.mprp='PERFORMABANK' and b.txt='Y'\n" + 
           "        and b.vld_dte is null and not exists (select 1 from mnme n where n.grp_nme_idn <> c.grp_nme_idn and n.nme_idn = ?)";
                   ary = new ArrayList();
                   ary.add(String.valueOf(udf.getNmeIdn()));
                 outLst = db.execSqlLst("defltBnkQ", defltBnkQ, ary);
                 pst = (PreparedStatement)outLst.get(0);
                 rs = (ResultSet)outLst.get(1);
           while(rs.next()){
               udf.setValue("bankIdn", util.nvl(rs.getString("bnkidn"),"NA"));
               MAddr addr = new MAddr();
               addr.setIdn(rs.getString("addr_idn"));
               addr.setAddr(rs.getString("addr"));
               bnkAddrList.add(addr);
           }
           rs.close();
           pst.close();
               }
               
               pageList=(ArrayList)pageDtl.get("SET_CO_IDN");
               if(pageList!=null && pageList.size() >0){
                   for(int j=0;j<pageList.size();j++){
                   pageDtlMap=(HashMap)pageList.get(j);
                   dflt_val=(String)pageDtlMap.get("dflt_val");
                   if(dflt_val.equals("Y")){
                       String defltcoQ="select grp_nme_idn from mnme n where n.nme_idn = ?";
                       ary = new ArrayList();
                       ary.add(String.valueOf(udf.getNmeIdn()));
                     outLst = db.execSqlLst("defltcoQ", defltcoQ,ary);
                     pst = (PreparedStatement)outLst.get(0);
                     rs = (ResultSet)outLst.get(1);
                       while(rs.next()){
                           udf.setValue("grpIdn", util.nvl(rs.getString("grp_nme_idn"),"NA"));
                       }
                       rs.close();
                       pst.close();
                   }
                   }
               }
           int invAddIdn=0;
           String validQ="Select Inv_Addr_Idn,Nmerel_Idn,Bank_Id, Courier,Co_Idn From Msal Where Idn in ("+saleId+") And Inv_Addr_Idn Is Not Null And Nmerel_Idn Is Not Null " +
               " and Bank_Id is not null and Courier is not null and co_idn is not null having count(*)=1 Group By Inv_Addr_Idn,Nmerel_Idn,Bank_Id,Courier,co_idn";
             outLst = db.execSqlLst("validQ", validQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
           while (rs.next()) {
           valid = true;
               invAddIdn = rs.getInt("Inv_Addr_Idn");
           }
               rs.close();
               pst.close();
    //        if(!valid){
    //        bnkAddrList=new ArrayList();
    //        String setQ="Select C.Nme_Idn bnkidn,d.courier,A.Addr_Idn addr_idn,  A.Bldg ||''|| A.Street ||''|| A.Landmark ||''|| A.Area addr \n" +
    //        "From Maddr A,Nme_Dtl B , Mnme C,Msal D\n" +
    //        "Where D.Idn In ("+saleId+")  And A.Nme_Idn=d.Bank_Id\n" +
    //        "And B.Nme_Idn=d.Bank_Id And C.Nme_Idn=d.Bank_Id\n" +
    //        "And A.Nme_Idn=B.Nme_Idn\n" +
    //        "and a.nme_idn = c.nme_idn \n" +
    //        "and b.vld_dte is null";
    //        rs = db.execSql("setQ", setQ, new ArrayList());
    //        while(rs.next()){
    //        udf.setValue("bankIdn", util.nvl(rs.getString("bnkidn"),"NA"));
    //        udf.setValue("courier", util.nvl(rs.getString("courier")));
    //        MAddr addr = new MAddr();
    //        addr.setIdn(rs.getString("addr_idn"));
    //        addr.setAddr(rs.getString("addr"));
    //        bnkAddrList.add(addr);
    //        }
    //        }
    //        ArrayList courierList = new ArrayList();
    //        String courierQ="select chr_fr,dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and\n" +
    //        "             b.mdl = 'JFLEX' and b.nme_rule = 'COURIER' and a.til_dte is null order by a.srt_fr";
    //        rs = db.execSql("courierQ", courierQ, new ArrayList());
    //        while(rs.next()){
    //            MAddr addr = new MAddr();
    //            addr.setIdn(util.nvl(rs.getString("chr_fr")));
    //            addr.setAddr(util.nvl(rs.getString("dsc")));
    //            courierList.add(addr);
    //        }
    //        rs.close();
           ArrayList chargesLst=new ArrayList();
           String chargesQ="Select  c.idn,c.typ,c.dsc,c.stg optional,c.flg,c.fctr,c.db_call,c.rmk  From Rule_Dtl A, Mrule B , Charges_Typ C Where A.Rule_Idn = B.Rule_Idn And \n" + 
           "       c.typ = a.Chr_Fr and b.mdl = 'JFLEX' and b.nme_rule = 'DLV_CHARGES' and c.stt='A' and a.til_dte is null order by a.srt_fr , a.dsc , a.chr_fr";
             outLst = db.execSqlLst("chargesQ", chargesQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
           while(rs.next()){
               HashMap dtl=new HashMap();
               dtl.put("idn",util.nvl(rs.getString("idn")));
               dtl.put("dsc",util.nvl(rs.getString("dsc")));
               dtl.put("autoopt",util.nvl(rs.getString("optional")));
               dtl.put("flg",util.nvl(rs.getString("flg")));
               dtl.put("typ",util.nvl(rs.getString("typ")));
               dtl.put("fctr",util.nvl(rs.getString("fctr")));
               dtl.put("fun",util.nvl(rs.getString("db_call")));
               dtl.put("rmk",util.nvl(rs.getString("rmk")));
               chargesLst.add(dtl);
           }
           rs.close();
            pst.close();
           ArrayList setcharge=new ArrayList();
           String chQ="Select A.Typ Typ From Charges_Typ A,Trns_Charges B\n" + 
           "Where A.Idn=B.Charges_Idn And A.Stt='A' And B.Flg='Y' And Ref_Idn In ("+saleId+")\n" + 
           "and a.stg='OPT' and ref_typ='SAL'\n" + 
           "GROUP BY A.Typ";
             outLst = db.execSqlLst("chQ", chQ, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
           while(rs.next()){
           String chtyp=util.nvl(rs.getString("Typ"));
           setcharge.add(chtyp);
           udf.setValue(chtyp+"_AUTOOPT","Y");
           }
           rs.close();
            pst.close();
        
             String salCharge = " Select A.Typ typ, to_char(Sum(B.Charges),'99999990.00') charges,a.app_typ,a.inv ,a.stg, b.rmk, A.srt , a.flg From Charges_Typ A,Trns_Charges B\n" + 
               " where a.idn=b.charges_idn and a.stt='A' and  ref_typ='SAL' and a.flg='MNL' and ref_idn  In ("+saleId+") and App_Typ='TTL'\n" + 
               " GROUP BY A.Typ, a.app_typ, a.inv, a.stg, b.rmk, A.srt, a.flg order by A.srt";
             outLst = db.execSqlLst("salCharge", salCharge, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
               while(rs.next()){
                 String chtyp=util.nvl(rs.getString("Typ")).trim();
                 String charges = util.nvl(rs.getString("charges"));
                   udf.setValue(chtyp,charges);
                 udf.setValue(chtyp+"_TTL",charges);  
                 udf.setValue(chtyp+"_save",charges);  
               }
               rs.close();
               pst.close();
           ArrayList thruBankList = new ArrayList();
           String thruBankSql = "select b.nme_dtl_idn , b.txt from mnme a , nme_dtl b " + 
                                "where a.nme_idn = b.nme_idn and b.mprp like 'THRU_BANK%' and a.nme_idn=? and b.vld_dte is null";
           ary = new ArrayList();
               ary.add(String.valueOf(udf.getNmeIdn()));
             outLst = db.execSqlLst("thruBank", thruBankSql, ary );
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
           String nmeDtlIdn = "";
           while(rs.next()){
               MNme dtl = new MNme();
               String txt=util.nvl(rs.getString("txt"));
               dtl.setIdn(util.nvl(rs.getString("nme_dtl_idn")));
               txt.replaceAll("\\#"," ");
               dtl.setFnme(txt);
               thruBankList.add(dtl);
               nmeDtlIdn = util.nvl(rs.getString("nme_dtl_idn"));
           }
               rs.close();
               pst.close();
           req.setAttribute("setcharge", setcharge);
           req.setAttribute("avgdtl", avgdtl);
           ArrayList groupList = srchQuery.getgroupList(req, res);
           ArrayList bankList = srchQuery.getBankList(req, res);
           ArrayList courierList = srchQuery.getcourierList(req, res);
               ArrayList dayTermsList = srchQuery.getdayTerm(req, res);
           session.setAttribute("chargesLst", chargesLst);
           udf.setDayTrmsLst(dayTermsList);
           udf.setThruBankList(thruBankList);
           udf.setValue("throubnk", nmeDtlIdn);
           udf.setCourierList(courierList);
           udf.setBnkAddrList(bnkAddrList);
           udf.setGroupList(groupList);
           udf.setBankList(bankList);
           udf.setInvAddLst(maddrList);
           udf.setInvAddrIdn(invAddIdn);
               
           info.setValue("PKTS", pkts);
           req.setAttribute("view", "Y");
           util.updAccessLog(req,res,"Sale Delivery", "Sale Delivery load pkts size "+pkts.size());
           req.setAttribute("NMEIDN",String.valueOf(udf.getNmeIdn()));
               finalizeObject(db,util);

           return am.findForward("load");
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
        synchronized(this){
        ArrayList params = null;

        SaleDeliveryForm udf = (SaleDeliveryForm) af;
        util.updAccessLog(req,res,"Sale Delivery", "Sale Delivery Save");
        ArrayList pkts      = new ArrayList();
        String    pktNmsSl  = "";
        String    pktNmsRT  = "";
        String    pktNmsCAN = "";
        int       appDlvIdn = 0;
        boolean isRtn = false;
        String sl_typ = util.nvl((String)udf.getValue("sale_typ"));
        String mnldlv_id = util.nvl((String)udf.getValue("mnldlv_id"));
        String noteperson = util.nvl((String)udf.getValue("noteperson"));
        String globalrmk = util.nvl((String)udf.getValue("rmk"));
        pkts = (ArrayList) info.getValue("PKTS");    // udf.getPkts();

        for (int i = 0; i < pkts.size(); i++) {
            PktDtl pkt     = (PktDtl) pkts.get(i);
            long   lPktIdn = pkt.getPktIdn();
            String saleIdn = pkt.getSaleId();
            String vnm = pkt.getVnm();
            String lStt    = util.nvl((String) udf.getValue("stt_" + lPktIdn));

           

            String updPkt = "";

            if (lStt.equals("DLV")) {
                if (appDlvIdn == 0) {
                    ArrayList ary = new ArrayList();

                    ary.add(Integer.toString(udf.getNmeIdn()));
                    ary.add(Integer.toString(udf.getRelIdn()));
                    ary.add(util.nvl((String)udf.getValue("invByrTrm"),Integer.toString(udf.getRelIdn())));
                    ary.add(udf.getValue("invByrIdn"));
                    ary.add(udf.getValue("invAddr"));
                    ary.add("DLV");
                    ary.add("IS");
                    ary.add(saleIdn);
                    ary.add("NN");
                    ary.add(nvl((String) udf.getValue("aadatpaid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk1paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk2paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk3paid"), "Y"));
                    ary.add(nvl((String) udf.getValue("brk4paid"), "Y"));
                    ary.add(util.nvl((String) udf.getValue("bankIdn")));
                    ary.add(util.nvl((String) udf.getValue("grpIdn")));
                    ary.add(util.nvl((String) udf.getValue("courier")));
                    ary.add(util.nvl((String)udf.getValue("throubnk")));
                    ArrayList out = new ArrayList();

                    out.add("I");

                    CallableStatement cst = null;

                    cst = db.execCall(
                        "Gen_HDR ",
                        "dlv_pkg.Gen_Hdr(pByrId => ? , pRlnIdn => ? , pTrmsIdn => ? , pInvNmeIdn => ? , pInvAddrIdn => ? , "
                        + "pTyp => ? , pStt => ?,pFrmId => ?, pFlg => ? ,paadat_paid => ?, pMBRK1_PAID => ?, pMBRK2_PAID => ?, pMBRK3_PAID => ? , "
                        + " pMBRK4_PAID => ?, bnk_idn =>? , co_idn => ? ,p_courier => ? ,pThruBnk => ? , pIdn => ?) ", ary, out);
                    appDlvIdn = cst.getInt(ary.size()+1);
                  cst.close();
                  cst=null;
                }
                if(appDlvIdn!=0){
                String dlvrmk    = util.nvl((String)udf.getValue("dlvrmk_"+Long.toString(lPktIdn)));
                params = new ArrayList();
                params.add("DLV");
                params.add(Long.toString(lPktIdn));
                params.add(saleIdn);

                int upJan = db.execUpd("updateJAN", "update jansal set stt=? , dte_sal=sysdate where mstk_idn=? and idn= ? ", params);

                params = new ArrayList();
                params.add(String.valueOf(appDlvIdn));
                params.add(saleIdn);
                params.add(Long.toString(lPktIdn));
                params.add(pkt.getQty());
                params.add(pkt.getCts());
                params.add(pkt.getMemoQuot());
                params.add("DLV");

                int SalPkt =
                    db.execCall(
                        "sale from memo",
                        " DLV_PKG.Dlv_Pkt(pIdn => ? , pSaleIdn => ?, pStkIdn => ?, pQty => ? , pCts => ?, pFnlSal => ?, pStt => ?)",
                        params);

                pktNmsSl = pktNmsSl + "," +vnm;
                    
                    if(!dlvrmk.equals("")){
                        params = new ArrayList();
                        params.add(dlvrmk);
                        params.add(Long.toString(lPktIdn));
                        params.add(String.valueOf(appDlvIdn));
                        int upDlv = db.execUpd("dlv_dtl", "update dlv_dtl set rmk=? where mstk_idn=? and idn= ? ", params);
                    }
                    if(!sl_typ.equals("")){
                          String cert_lab = util.nvl((String)pkt.getValue("cert_lab"));
                          ArrayList ary = new ArrayList();
                          ary.add(Long.toString(lPktIdn));
                          ary.add("SL_TYP");
                          ary.add(sl_typ);
                          ary.add(cert_lab);
                          ary.add("C");
                          ary.add("1");
                          String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?, pPrpTyp => ?, pgrp => ? )";
                          int ct = db.execCall("stockUpd",stockUpd, ary);
                    }
                    if(!mnldlv_id.equals("")){
                          ArrayList ary = new ArrayList();
                          ary.add(Long.toString(lPktIdn));
                          ary.add("DLV_ID");
                          ary.add(mnldlv_id);
                          ary.add("T");
                          ary.add("1");
                          String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? ,pPrpTyp => ?, pgrp => ? )";
                          int ct = db.execCall("stockUpd",stockUpd, ary);
                    }
                }} else if (lStt.equals("RT")) {
                        String rmk = util.nvl((String)udf.getValue("rmk_"+Long.toString(lPktIdn)));
                        params = new ArrayList();
                        params.add(saleIdn);
                        params.add(Long.toString(lPktIdn));
                        params.add(rmk);
                        int ct = db.execCall(" App Pkts", " DLV_PKG.rtn_pkt(pIdn => ? , pStkIdn => ?,pRem => ?)", params);

                        pktNmsRT = pktNmsRT + "," +vnm;
                        req.setAttribute("RTMSG", "Packets get return: " + pktNmsRT);
                        isRtn = true;
            } else if (lStt.equals("CAN")) {
                    params = new ArrayList();
                    params.add(saleIdn);
                    params.add(Long.toString(lPktIdn));
                    int ct = db.execCall(" Cancel Pkts ", "sl_pkg.Cancel_Pkt( pIdn =>?, pStkIdn=> ?)", params);
                    pktNmsCAN = pktNmsCAN + "," +vnm;
                    req.setAttribute("CANMSG", "Packets cancelled " + pktNmsCAN);
            }else if (lStt.equals("SL")) {
                if(!noteperson.equals("")){
                params = new ArrayList();
                params=new ArrayList();
                params.add(noteperson);
                params.add(globalrmk);
                params.add(saleIdn);
                String updateFlg = "update msal set NOTE_PERSON=?,rmk=? where idn=?";
                int cntmj = db.execUpd("update msal", updateFlg, params);
                }else if(!globalrmk.equals("")){
                    params = new ArrayList();
                    params.add(globalrmk);
                    params.add(saleIdn);
                    String updateFlg = "update msal set rmk=? where idn=?";
                    int cntmj = db.execUpd("update msal", updateFlg, params);
                }
            } 
            
            
            params = new ArrayList();
            params.add(saleIdn);
            int upQuery = db.execCall("calQuot", "SL_PKG.UPD_QTY(pIdn => ? , pTyp => 'DLV')", params);
          }
        
       
        if (appDlvIdn != 0) {
            if(!noteperson.equals("")){
            params = new ArrayList();
            params=new ArrayList();
            params.add(noteperson);
            params.add(globalrmk);
            params.add(String.valueOf(appDlvIdn));
            String updateFlg = "update mdlv set NOTE_PERSON=?,rmk=? where idn=?";
            int cntmj = db.execUpd("update mdlv", updateFlg, params);
            }else if(!globalrmk.equals("")){
                params = new ArrayList();
                params.add(globalrmk);
                params.add(String.valueOf(appDlvIdn));
                String updateFlg = "update mdlv set rmk=? where idn=?";
                int cntmj = db.execUpd("update mdlv", updateFlg, params);
            }
            String fnlexhRte = util.nvl((String)udf.getValue("fnlexhRte"));
            if(!fnlexhRte.equals("")){
                params = new ArrayList();
                params.add(fnlexhRte);
                params.add(String.valueOf(appDlvIdn));
                String updateFlg = "update mdlv set FNL_EXH_RTE=? where idn=?";
                int cntmj = db.execUpd("update mdlv", updateFlg, params);
            }
            String dayTerms = util.nvl((String)udf.getValue("dayTerms"));
            if(!dayTerms.equals("")){
                params = new ArrayList();
                params.add(dayTerms);
                params.add(String.valueOf(appDlvIdn));
                String updateFlg = "update mdlv set trms_idn=? where idn=?";
                int cntmj = db.execUpd("update mdlv", updateFlg, params);
            }
            
            ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
            if(chargesLst!=null && chargesLst.size()>0){
            for(int i=0;i<chargesLst.size();i++){
            HashMap dtl=new HashMap();
            dtl=(HashMap)chargesLst.get(i);
            String idn=(String)dtl.get("idn");
            String dsc=(String)dtl.get("dsc");
            String flg=(String)dtl.get("flg");
            String typcharge=(String)dtl.get("typ");
            String fctr=(String)dtl.get("fctr");
            String fun=(String)dtl.get("fun");
            String autoopt=util.nvl((String)dtl.get("autoopt"));
            String autooptional= util.nvl((String)udf.getValue(typcharge+"_AUTOOPT"));  
            String calcdis= util.nvl((String)udf.getValue(typcharge+"_save"),"0").trim();  
            String vlu= util.nvl((String)udf.getValue("vluamt"));
            String exhRte=util.nvl((String)udf.getValue("exhRte"),"1");
            String extrarmk= util.nvl((String)udf.getValue(typcharge+"_rmksave")); 
                if(!vlu.equals("") && !vlu.equals("0")  && !calcdis.equals("NaN")  && !vlu.equals("NaN")){
            if((!calcdis.equals("") && !calcdis.equals("0")) || (autoopt.equals("OPT"))){
            String insertQ="Insert Into Trns_Charges (trns_idn, ref_typ, ref_idn,charges_idn,amt,amt_usd,charges,CHARGES_PCT,net_usd,rmk,stt,flg)\n" + 
            "VALUES (TRNS_CHARGES_SEQ.nextval, 'DLV', ?,?,?,?,?,?,?,?,'A',?)";   
            ArrayList ary=new ArrayList();
            ary.add(String.valueOf(appDlvIdn));
            ary.add(idn);
            ary.add(vlu);
            float amt_usd=Float.parseFloat(vlu)/Float.parseFloat(exhRte);
            ary.add(String.valueOf(amt_usd));
            ary.add(calcdis);
            ary.add(calcdis);
            float net_usd=amt_usd+Float.parseFloat(calcdis);
            ary.add(String.valueOf(net_usd));
            ary.add(extrarmk);
            ary.add(autooptional);
            int ct = db.execUpd("Insert", insertQ, ary);
            }
            }
            }
            }
            params = new ArrayList();
            params.add(String.valueOf(appDlvIdn));

            int calCt = db.execCall("Cal_Fnl_Quot", "DLV_PKG.Cal_Fnl_Quot(pIdn => ?)", params);

            params = new ArrayList();
            params.add(String.valueOf(appDlvIdn));

            int upQuery = db.execCall("UPD_QTY", "DLV_PKG.UPD_QTY(pIdn => ? )", params);
            
            params = new ArrayList();
            params.add(String.valueOf(appDlvIdn));
            int upbrcDlv = db.execCall("DP_GEN_BRC_DLV", "DP_GEN_BRC_DLV(pDlvIdn => ? )", params);
            
            req.setAttribute("dlvId", String.valueOf(appDlvIdn));
            pktNmsSl=pktNmsSl.replaceFirst("\\,", "");
            req.setAttribute("SLMSG", "Packets get Deliver : " + pktNmsSl + " with delivery Id" + appDlvIdn);
            req.setAttribute("performaLink","Y");
          util.updAccessLog(req,res,"Sale Delivery", "Sale Delivery Save done dlv Id"+appDlvIdn);
        }
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("PERFORMA_INV");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("PERFORMA_INV");
            allPageDtl.put("PERFORMA_INV",pageDtl);
            }
            info.setPageDetails(allPageDtl);
          int accessidn=util.updAccessLog(req,res,"Sale Delivery", "Sale Delivery Save done");
          req.setAttribute("accessidn", String.valueOf(accessidn));
            finalizeObject(db,util);

        return load(am, af, req, res);
        }
            }
    }
    public ActionForward perInv(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
    SaleDeliveryForm udf = (SaleDeliveryForm) af;
    GenericInterface genericInt = new GenericImpl();
    util.updAccessLog(req,res,"Sale Delivery", "Sale Delivery Performa");
    ArrayList ary = new ArrayList();
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
    String byrIdn = util.nvl(req.getParameter("byrIdn"));
    String grpIdn = util.nvl(req.getParameter("grpIdn"));
    String relIdn = util.nvl(req.getParameter("relIdn"));
    String addrIdn = util.nvl(req.getParameter("byrAddIdn"));
    String bankIdn = util.nvl(req.getParameter("bankIdn"));
    String bankaddrIdn = util.nvl(req.getParameter("bankAddIdn"));
    String stkIdnList = util.nvl(req.getParameter("perInvIdn"));
    String brocommval = util.nvl(req.getParameter("brocommval"));
    String courier = util.nvl(req.getParameter("courier"));
    String typ = util.nvl(req.getParameter("typ"));
    String echarge= util.nvl(req.getParameter("echarge"));
    String salidn= util.nvl(req.getParameter("idn"));
    String sname="perInvViewLst",mdl="PERINV_VW";
    if(!brocommval.equals(""))
        brocommval=String.valueOf(Float.parseFloat(brocommval)/100);
    else
        brocommval="0";  
    if(stkIdnList!=null){
        stkIdnList = util.getVnm(stkIdnList);
    }
//    String byrNme ="select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " +
//    " b.mdl = 'JFLEX' and b.nme_rule ='PRM_INV' and a.til_dte is null order by a.srt_fr ";
//    ary = new ArrayList();
//
//    rs = db.execSql("byrName", byrNme, ary);
//    while(rs.next()){
//    udf.setValue(rs.getString("dsc"), rs.getString("chr_fr"));
//    }
        String grp="select mprp, txt from nme_dtl where nme_idn=? and vld_dte is null";
        ary = new ArrayList();
        ary.add(grpIdn);
          ArrayList  outLst = db.execSqlLst("Company DtlQ", grp, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        while(rs.next()){
        udf.setValue(util.nvl(rs.getString("mprp")), util.nvl(rs.getString("txt"),"NA"));
        }
        rs.close();
        pst.close();
    String addrSql = "select a.addr_idn , unt_num, byr.get_nm(nme_idn) byr, bldg ||''|| street bldg , landmark ||''|| area landMk , b.city_nm||' '|| c.country_nm||' '|| a.zip addr "+
    " from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.addr_idn = ? order by a.dflt_yn desc ";
    ary = new ArrayList();
    ary.add(addrIdn);
     outLst = db.execSqlLst("addr", addrSql, ary);
     pst = (PreparedStatement)outLst.get(0);
     rs = (ResultSet)outLst.get(1);
    if(rs.next()){
    udf.setValue("unit_no", rs.getString("unt_num"));
    udf.setValue("bldg", rs.getString("bldg"));
    udf.setValue("landMk", rs.getString("landMk"));
    //udf.setValue("city", rs.getString("addr"));
    udf.setValue("addr", rs.getString("addr"));
    udf.setValue("byrnme", rs.getString("byr"));
    }

        rs.close();
        pst.close();
//    String addDtl = "select mprp, txt from nme_dtl where nme_idn = ? and mprp in ('TEL_NO','EMAIL','FAX','PRE-CARRIAGE-BY','VESSEL/FLIGHT.NO','PORT OF DISCHARGE','FINAL DESTINATION') ";
    String addDtl ="Select eml,mbl,qbc,fax,finaldest,preby,vesselno,portdis,ifsc,bankaccount from nme_cntct_v where nme_id=? ";
    ary = new ArrayList();
    ary.add(byrIdn);
          outLst = db.execSqlLst("addDtl", addDtl, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
    while(rs.next()){
//    udf.setValue(util.nvl(rs.getString("mprp")), util.nvl(rs.getString("txt"),"NA"));
        udf.setValue("TEL_NO", util.nvl(rs.getString("qbc")));
        udf.setValue("EMAIL", util.nvl(rs.getString("eml"))); 
        udf.setValue("MOBILE", util.nvl(rs.getString("mbl"))); 
        udf.setValue("FAX", util.nvl(rs.getString("fax"))); 
        udf.setValue("FINALDEST", util.nvl(rs.getString("finaldest"))); 
        udf.setValue("PREBY", util.nvl(rs.getString("preby"))); 
        udf.setValue("VESSELNO", util.nvl(rs.getString("vesselno"))); 
        udf.setValue("PORTDIS", util.nvl(rs.getString("portdis"))); 
    }
        rs.close();
            pst.close();
     addDtl ="Select ifsc,bankaccount from nme_cntct_v where nme_id=? ";
     ary = new ArrayList();
     ary.add(bankIdn);
          outLst = db.execSqlLst("addDtl", addDtl, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
     while(rs.next()){
     udf.setValue("IFSC", util.nvl(rs.getString("ifsc"))); 
     udf.setValue("BANKACCOUNT", util.nvl(rs.getString("bankaccount"))); 
     }
        rs.close();
        pst.close();
    String terms="";
    String termsDtl ="select b.nmerel_idn, nvl(c.trm_prt, c.term) trms from nmerel b, mtrms c where b.trms_idn = c.idn and b.nmerel_idn=? ";
    ary = new ArrayList();
    ary.add(relIdn);
          outLst = db.execSqlLst("termsDtl", termsDtl, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
    if(rs.next()){
        terms=util.nvl(rs.getString("trms"));
    // udf.setValue("terms", util.nvl(rs.getString("dtls")));
    udf.setValue("terms",terms);

    }
        rs.close();
        pst.close();
    String consignee = "select del_typ,cur from nmerel where nmerel_idn=? ";
    ary = new ArrayList();
    ary.add(relIdn);
          outLst = db.execSqlLst("consignee", consignee, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
    if(rs.next()){
    // udf.setValue("terms", util.nvl(rs.getString("dtls")));
    udf.setValue("consignee", util.nvl(rs.getString("del_typ")));
    udf.setValue("termsSign",util.nvl(rs.getString("cur")));
    }
        rs.close();
        pst.close();
    String bankDtl = "select a.addr_idn , d.rmk , b.city_nm , d.fnme byrNme, bldg ||''|| street bldg , landmark ||''|| area landMk , b.city_nm||' '|| c.country_nm addr " +
    " from maddr a, mcity b, mcountry c , mnme d where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn = d.nme_idn " +
    " and a.nme_idn = ? and a.addr_idn=? order by a.dflt_yn desc ";
    ary = new ArrayList();
    ary.add(bankIdn);
    ary.add(bankaddrIdn);
          outLst = db.execSqlLst("addr",bankDtl, ary);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
    if(rs.next()){
    udf.setValue("bankBldg", rs.getString("bldg"));
    udf.setValue("bankLand", rs.getString("landMk"));
    udf.setValue("bankCity", rs.getString("addr"));
    udf.setValue("bankNme", rs.getString("byrNme"));
    udf.setValue("rmk", rs.getString("rmk"));
    udf.setValue("cityNme", rs.getString("city_nm"));
    }
        rs.close();
        pst.close();
    ArrayList pktList = new ArrayList();
    String getInvPkts = " select a.idn , a.qty ,b.vnm , a.mstk_idn, to_char((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?),'9999999999990.00') quot, "+
    " to_char(b.cts,'90.00') cts , to_char(trunc(trunc(b.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?), 2),'9999990.00') vlu , "+
    " trunc((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?)*trunc(a.cts,2) *((trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)))*12/100/365),2) inc_vlu ,"+
    " to_char(sum(trunc(a.cts,2)) over(partition by a.stt),'90.99') ttl_cts , "+
    " to_char(sum(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?)) over(partition by a.stt) ,'999999990.00') ttl_vlu , sum(a.qty) over(partition by a.stt) ttl_qty "+
    " from jansal a, mstk b , msal c,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='SL' and c.typ in ('SL')and c.nmerel_idn = d.nmerel_idn and c.stt='IS' and a.mstk_idn in ("+stkIdnList+") ";
    if(typ.equals("SL")){
    getInvPkts = " select a.idn , a.qty ,b.vnm , a.mstk_idn,  to_char((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?),'9999999999990.00') quot, "+
    " to_char(b.cts,'90.00') cts , to_char(trunc(trunc(b.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?), 2),'9999990.00') vlu , "+
    " to_char(sum(trunc(a.cts,2)) over(partition by a.stt),'90.99') ttl_cts , "+
    " to_char(sum(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?)) over(partition by a.stt) ,'999999990.00') ttl_vlu , sum(a.qty) over(partition by a.stt) ttl_qty "+
    " from jandtl a, mstk b , mjan c where a.mstk_idn = b.idn and a.stt = 'AP' and a.idn = c.idn and c.stt='IS' " +
    " and a.mstk_idn in ("+stkIdnList+") ";
    }
    if(typ.equals("CS")){
        getInvPkts = " select a.idn , a.qty ,b.vnm , a.mstk_idn,  to_char((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?),'9999999999,990.00') quot, "+
            " to_char(b.cts,'90.00') cts , to_char(trunc(trunc(b.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?), 2),'9999990.00') vlu , "+
            " to_char(sum(trunc(a.cts,2)) over(partition by a.stt),'90.99') ttl_cts , "+
            " to_char(sum(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?)) over(partition by a.stt) ,'999999990.00') ttl_vlu , sum(a.qty) over(partition by a.stt) ttl_qty "+
            " from jansal a, mstk b , msal c where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='CS' and c.typ in ('CS') and c.stt='IS' and a.mstk_idn in ("+stkIdnList+") ";
    }
    if(typ.equals("BOX")){
            getInvPkts="select a.idn , a.qty ,b.vnm , a.mstk_idn, to_char((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?),'9999999999990.00') quot," + 
            " to_char(a.cts,'990.00') cts , to_char(trunc(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?), 2),'9999990.00') vlu ," + 
            " trunc((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?)*trunc(a.cts,2) *((trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)))*12/100/365),2) inc_vlu ," + 
            " to_char(sum(trunc(a.cts,2)) over(partition by a.stt),'999990.99') ttl_cts ," + 
            "  to_char(sum(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*?)) over(partition by a.stt) ,'999999990.00') ttl_vlu , sum(a.qty) over(partition by a.stt) ttl_qty " + 
            "  from jansal a, mstk b , msal c,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='SL' and c.typ in ('SL')and c.nmerel_idn = d.nmerel_idn and c.stt='IS' and c.idn="+salidn;
        sname="perInvBoxViewLst";
        mdl="PERINV_BOX_VW";
    }
    ArrayList params=new ArrayList();
        params.add(brocommval);
        params.add(brocommval);
        params.add(brocommval);
    if(!typ.equals("SL") && !typ.equals("CS"))
        params.add(brocommval);  
    
          outLst = db.execSqlLst("performInv", getInvPkts, params);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        String ttl = null;
    while(rs.next()){
    HashMap pktListMap = new HashMap();
    String mstkIdn = rs.getString("mstk_idn");
    pktListMap.put("cts",util.nvl(rs.getString("cts")));
    pktListMap.put("qty",util.nvl(rs.getString("qty")));
    pktListMap.put("quot",util.nvl(rs.getString("quot")));
    pktListMap.put("vlu",util.nvl(rs.getString("vlu")));
    //    if(typ.equals("DLY")){
    //    pktListMap.put("inc_vlu",util.nvl(rs.getString("inc_vlu")));
    //        req.setAttribute("inc_vlu", "Y");
    //    }



    String getPktPrp =
    " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
    + " from stk_dtl a, mprp b, rep_prp c "
    + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = ? and grp=1 and a.mstk_idn = ? "
    + " order by c.rnk, c.srt ";

    ary = new ArrayList();
    ary.add(mdl);    
    ary.add(mstkIdn);
    ArrayList  outLst1 = db.execSqlLst("performInv", getPktPrp, ary);
     PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
    ResultSet  rs1 = (ResultSet)outLst1.get(1);
    while (rs1.next()) {
    String lPrp = util.nvl(rs1.getString("mprp"));
    String lVal = util.nvl(rs1.getString("val"));

    pktListMap.put(lPrp, lVal);  
    }
        rs1.close();
        pst1.close();
    pktListMap.put("VNM",util.nvl(rs.getString("vnm")));
    pktList.add(pktListMap);
    udf.setValue("ttlCts", rs.getString("ttl_cts"));
    udf.setValue("ttlQty", rs.getString("ttl_qty"));
    ttl=util.nvl(rs.getString("ttl_vlu")).trim();
    udf.setValue("ttlVlu", ttl);
    udf.setValue("echarge", echarge);
        
        
    
    }
        rs.close();
        pst.close();
    String grand_inc_vlu="";
    if(typ.equals("DLY") && cnt.equals("jb")){
        params=new ArrayList();
        params.add(brocommval);
    String imcQ="select sum(trunc(nvl(fnl_sal, quot)*trunc(a.cts,2) *((trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)))*12/100/365),2)) grand_inc_vlu "+
        " from jansal a, mstk b , msal c,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn and a.stt='SL' and c.typ in ('SL')and c.nmerel_idn = d.nmerel_idn and c.stt='IS' and a.mstk_idn in ("+stkIdnList+") ";
      outLst = db.execSqlLst("Grand inc Value", imcQ, params);
      pst = (PreparedStatement)outLst.get(0);
      rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            grand_inc_vlu=util.nvl(rs.getString("grand_inc_vlu"));
            udf.setValue("inc_vlu",grand_inc_vlu);
            ttl=String.valueOf(Math.round((Double.parseDouble(ttl)+Double.parseDouble(grand_inc_vlu))*100.0)/100.0);
            req.setAttribute("inc_vlu", "Y");
        }
        rs.close();
        pst.close();
    }
    if(ttl!=null && !ttl.equals("")){
     String Gtotal="0";    
    if(!echarge.equals(""))
        Gtotal=String.valueOf(Double.parseDouble(ttl)+Double.parseDouble(echarge));
    else
        Gtotal=ttl;
    params=new ArrayList();
    if(!salidn.equals("")){
    params.add(salidn);
    String imcQ="Select A.Typ typ,A.dsc dsc,b.charges,a.app_typ,a.inv From Charges_Typ A,Trns_Charges B\n" + 
    "  where a.idn=b.charges_idn and a.stt='A' and ref_idn=?  order by srt";
      outLst = db.execSqlLst("Charges", imcQ, params); 
      pst = (PreparedStatement)outLst.get(0);
      rs = (ResultSet)outLst.get(1);
    ArrayList chargeLst=new ArrayList();      
    while(rs.next()){
        HashMap charData=new HashMap();
        String charge=util.nvl(rs.getString("charges"));
        String app_typ=util.nvl(rs.getString("app_typ"));
        String inv=util.nvl(rs.getString("inv"));
        if(inv.equals("Y")){
        charData.put("DSC", util.nvl(rs.getString("dsc")));
        charData.put("CHARGE",charge);
        chargeLst.add(charData);
        }
        if(app_typ.equals("TTL"))
        Gtotal=String.valueOf(Math.round(Double.parseDouble(Gtotal)+Double.parseDouble(charge)));
    }
        rs.close();
        pst.close();
        req.setAttribute("chargeLst", chargeLst);
    }
    udf.setValue("grandttlVlu", Gtotal);
        //String num=rs.getString("ttl_vlu").trim();
    String words=util.convertNumToAlp(Gtotal);
     udf.setValue("inwords",words);
    }
    genericInt.genericPrprVw(req, res, sname,mdl);
    req.setAttribute("typ", typ);
    req.setAttribute("pktList", pktList);
    udf.setValue("courierS",courier);
          util.updAccessLog(req,res,"Sale Delivery", "Sale Delivery Performa done");
            finalizeObject(db,util);

    return am.findForward("perInv");
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
        String mail=util.nvl(req.getParameter("mail"));
        String nmeIdn=util.nvl(req.getParameter("nmeIdn"));
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "SaleDelivery"+util.getToDteTime()+".xls";
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
        if(mail.equals("")){
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        hwb.write(out);
        out.flush();
        out.close();
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


    public String nvl(String pVal, String rVal) {
        String val = pVal;

        if (pVal == null) {
            val = rVal;
        }

        return val;
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
                util.updAccessLog(req,res,"Sale Delivery", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Sale Delivery", "init");
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


//~ Formatted by Jindent --- http://www.jindent.com
