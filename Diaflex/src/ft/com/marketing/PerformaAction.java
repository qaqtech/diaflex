package ft.com.marketing;

//~--- non-JDK imports --------------------------------------------------------

import com.google.gson.Gson;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.GenMail;
import ft.com.HtmlMailUtil;
import ft.com.InfoMgr;
import ft.com.dao.ByrDao;
import ft.com.dao.MAddr;
import ft.com.dao.PktDtl;
import ft.com.dao.TrmsDao;

import ft.com.generic.GenericImpl;

import ft.com.generic.GenericInterface;

import java.io.File;
import java.io.FileWriter;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class PerformaAction extends DispatchAction {
    public PerformaAction() {
        super();
    }

    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
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
        SaleDeliveryForm udf = (SaleDeliveryForm)af;
        udf.reset();
        SearchQuery srchQuery = new SearchQuery();
        ResultSet rs = null;
        util.updAccessLog(req,res,"Performa", "Performa load start");
        ArrayList groupList = srchQuery.getgroupList(req, res);
        udf.setGroupList(groupList);
        udf.setValue("invoice", "SL");
        udf.setValue("stt", "ALL");
        udf.setValue("pktTyp", "SINGLE");
        ArrayList bankList = srchQuery.getBankList(req, res);
        udf.setBankList(bankList);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("PERFORMA_INV");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("PERFORMA_INV");
        allPageDtl.put("PERFORMA_INV",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        util.updAccessLog(req,res,"Performa", "Performa load end");
        return am.findForward("load");
        }
    }


  public ActionForward loadFromForm(ActionMapping am, ActionForm af, HttpServletRequest req,
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
         SaleDeliveryForm udf = (SaleDeliveryForm)af;
         udf.reset();
         SearchQuery srchQuery = new SearchQuery();
         util.updAccessLog(req,res,"Performa", "Performa load start");
         String typ = util.nvl(req.getParameter("typ"));
         String grpIdn = util.nvl(req.getParameter("grpIdn"));
         String fmt = util.nvl(req.getParameter("fmt"));
         String idns = util.nvl(req.getParameter("idn"));
         String pktTyp = util.nvl(req.getParameter("pktTyp"));
         ArrayList groupList = srchQuery.getgroupList(req, res);
         udf.setGroupList(groupList);
         ArrayList bankList = srchQuery.getBankList(req, res);
         udf.setBankList(bankList);
         ArrayList ary=new ArrayList();
         String Bank_Id="",Co_Idn="";
         String validQ ="Select Bank_Id,Co_Idn From Msal Where Idn =?";
         if (typ.equals("DLV")) {
         validQ ="Select bnk_idn Bank_Id,Co_Idn From mdlv Where Idn =?";
         }
         ary=new ArrayList();
         ary.add(idns);
         ArrayList  outLst = db.execSqlLst("valid", validQ,ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet  rs = (ResultSet)outLst.get(1);
         while (rs.next()) {
         Co_Idn = util.nvl(rs.getString("Co_Idn"));
         Bank_Id = util.nvl(rs.getString("Bank_Id"));
         }
         rs.close();
         pst.close();
         udf.setValue("invoice", typ);
         udf.setValue("stt", "ALL");
         udf.setValue("pktTyp", pktTyp);
         udf.setValue("saleid", idns);
         udf.setValue("grpIdn", Co_Idn);
         
         ArrayList pageList=new ArrayList();
         HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
         HashMap pageDtl=(HashMap)allPageDtl.get("PERFORMA_INV");
         if(pageDtl==null || pageDtl.size()==0){
         pageDtl=new HashMap();
         pageDtl=util.pagedef("PERFORMA_INV");
         allPageDtl.put("PERFORMA_INV",pageDtl);
         }
         info.setPageDetails(allPageDtl);
         pageList=(ArrayList)pageDtl.get("BANK_LOC");
         if(pageList!=null && pageList.size() >0){
             udf.setValue("bankIdnLoc", Bank_Id);
         }
         util.updAccessLog(req,res,"Performa", "Performa load end");
         return am.findForward("load");
         }
     }
  
  
  
  public ActionForward loadPkt(ActionMapping am, ActionForm af, HttpServletRequest req,
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
        SaleDeliveryForm udf = (SaleDeliveryForm)af;
        GenericInterface genericInt = new GenericImpl();
        SearchQuery srchQuery = new SearchQuery();
        util.updAccessLog(req,res,"Performa", "Performa loadpkt start");
        String typ = util.nvl((String)udf.getValue("invoice"));
        String stt = util.nvl((String)udf.getValue("stt"));
        String grpIdn = util.nvl((String)udf.getValue("grpIdn"));
        String fmt = util.nvl((String)udf.getValue("fmt"));
        String idns = util.nvl((String)udf.getValue("saleid"));
        String pktTyp = util.nvl((String)udf.getValue("pktTyp"));
        String consign = util.nvl((String)udf.getValue("consign"));
        udf.reset();
        String idnLst = "", ttl = null, brocommval = "", sttChk = " = '" + typ + "'";
        ArrayList ary = new ArrayList();
        ArrayList pktList = new ArrayList();
        HashMap dtl = new HashMap();
        ArrayList params = new ArrayList();
        String sname = "perInvViewLst", mdl = "PERINV_VW";
        if (!idns.equals("")) {
            if(dtl.containsValue("YES")){
                
            }
            if (typ.equals(""))
                typ = "SL";
            if (stt.equals("ALL"))
                sttChk = " <> 'RT'";
            idnLst = util.getVnm(idns);
            idnLst = "(" + idnLst + ")";
            idnLst = idnLst.replaceAll("\\_", "").trim();
            if (idnLst.indexOf(",") > -1) {
                boolean valid = validateidns(req, res, typ, idnLst);
                if (valid) {
                    req.setAttribute("msg", "Please Verify Idns There is data Differerence");
                    ArrayList bankList = srchQuery.getBankList(req, res);
                    udf.setBankList(bankList);
                    return am.findForward("load");
                }
            }
            String byrNme =
                "select distinct nvl(inv_nme_idn, nme_idn) nme_idn, inv_addr_idn,nmerel_idn,bank_id,courier,nvl(Aadat_Comm,0)+nvl(brk1_comm,0)+nvl(brk2_comm,0)+nvl(brk3_comm,0)+nvl(brk4_comm,0) comm from msal where idn in " +
                idnLst;
            if (typ.equals("DLV")) {
                byrNme =
                        "select distinct nvl(inv_nme_idn, nme_idn) nme_idn, inv_addr_idn,nmerel_idn,bnk_idn bank_id,courier,\n" +
                        "nvl(Aadat_Comm,0)+nvl(brk1_comm,0)+nvl(brk2_comm,0)+nvl(brk3_comm,0)+nvl(brk4_comm,0) comm from mdlv where idn in " +
                        idnLst;
            }
          ArrayList  outLst = db.execSqlLst("byrName", byrNme, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet  rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                brocommval = util.nvl(rs.getString("comm"));
            }
            rs.close();
            pst.close();
            if (!brocommval.equals("") && brocommval.equals("0"))
                brocommval = String.valueOf(Float.parseFloat(brocommval) / 100);
            else
                brocommval = "0";
            String conQ = " c.idn in " + idnLst;
            String getInvPkts =
                " select get_inv_pkt_srt(a.mstk_idn) ,a.idn ,c.thru_bank_idn, a.qty ,b.vnm , a.mstk_idn, to_char((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                brocommval + "),'9999999999990.00') quot, " +
                " to_char(a.cts,'9990.00') cts , to_char(trunc(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                brocommval + "), 2),'9999990.00') vlu , " + " trunc((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                brocommval +
                ")*trunc(a.cts,2) *((trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)))*12/100/365),2) inc_vlu ," +
                " to_char(sum(trunc(a.cts,2)) over(partition by 'IS'),'99990.99') ttl_cts , " +
                " to_char(sum(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" + brocommval +
                ")) over(partition by 'IS') ,'999999990.00') ttl_vlu , sum(a.qty) over(partition by 'IS') ttl_qty " +
                " from jansal a, mstk b , msal c,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn and a.stt " +
                sttChk + " and c.typ in ('SL')and c.nmerel_idn = d.nmerel_idn and c.stt='IS' and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA')) and " + conQ +
                " order by 1 ";

            if (typ.equals("DLV")) {
                getInvPkts =
                        " select get_inv_pkt_srt(a.mstk_idn) ,a.idn ,c.thru_bank_idn thru_bank_idn, a.qty ,b.vnm , a.mstk_idn, to_char((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                        brocommval + "),'9999999999990.00') quot, " +
                        " to_char(a.cts,'9999990.00') cts , to_char(trunc(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                        brocommval + "), 2),'9999990.00') vlu , " + " trunc((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                        brocommval +
                        ")*trunc(a.cts,2) *((trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)))*12/100/365),2) inc_vlu ," +
                        " to_char(sum(trunc(a.cts,2)) over(partition by 'IS'),'90.99') ttl_cts , " +
                        " to_char(sum(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" + brocommval +
                        ")) over(partition by 'IS') ,'999999990.00') ttl_vlu , sum(a.qty) over(partition by 'IS') ttl_qty " +
                        " from dlv_dtl a, mstk b , mdlv c,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn and a.stt " +
                        sttChk + " and c.typ in ('DLV')and c.nmerel_idn = d.nmerel_idn and c.stt='IS' and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA')) and " + conQ +
                        " order by 1 ";

            }
            if (typ.equals("CS")) {
                getInvPkts =
                        " select get_inv_pkt_srt(a.mstk_idn) ,a.idn , a.qty ,b.vnm ,0 thru_bank_idn, a.mstk_idn,  to_char((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                        brocommval + "),'9999999999,990.00') quot, " +
                        " to_char(a.cts,'9990.00') cts , to_char(trunc(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                        brocommval + "), 2),'9999990.00') vlu , " +
                        " to_char(sum(trunc(a.cts,2)) over(partition by 'IS'),'99990.99') ttl_cts , " +
                        " to_char(sum(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" + brocommval +
                        ")) over(partition by 'IS') ,'999999990.00') ttl_vlu , sum(a.qty) over(partition by 'IS') ttl_qty " +
                        " from jansal a, mstk b , msal c where a.mstk_idn = b.idn and c.idn = a.idn and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA')) and a.stt " +
                        sttChk + " and c.typ in ('CS') and c.stt='IS' and " + conQ + " order by 1 ";
            }
            if (typ.equals("BOX")) {
                getInvPkts =
                        "select get_inv_pkt_srt(a.mstk_idn) ,a.idn , a.qty ,b.vnm ,0 thru_bank_idn, a.mstk_idn, to_char((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                        brocommval + "),'9999999999990.00') quot," +
                        " to_char(a.cts,'9990.00') cts , to_char(trunc(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                        brocommval + "), 2),'9999990.00') vlu ," + " trunc((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                        brocommval +
                        ")*trunc(a.cts,2) *((trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)))*12/100/365),2) inc_vlu ," +
                        " to_char(sum(trunc(a.cts,2)) over(partition by 'IS'),'999990.99') ttl_cts ," +
                        "  to_char(sum(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" + brocommval +
                        ")) over(partition by 'IS') ,'999999990.00') ttl_vlu , sum(a.qty) over(partition by 'IS') ttl_qty " +
                        "  from jansal a, mstk b , msal c,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA')) and a.stt " +
                        sttChk + " and c.typ in ('SL')and c.nmerel_idn = d.nmerel_idn and c.stt='IS' and c.idn in " +
                        idnLst + " order by 1 ";
                sname = "perInvBoxViewLst";
                mdl = "PERINV_BOX_VW";
            }


                outLst = db.execSqlLst("performInv", getInvPkts, params);
                 pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                HashMap pktListMap = new HashMap();
                String mstkIdn = rs.getString("mstk_idn");
                udf.setValue(mstkIdn, "yes");
                pktListMap.put("stkidn", util.nvl(rs.getString("mstk_idn")));
                pktListMap.put("idn", util.nvl(rs.getString("idn")));
                pktListMap.put("cts", util.nvl(rs.getString("cts")));
                pktListMap.put("qty", util.nvl(rs.getString("qty")));
                pktListMap.put("quot", util.nvl(rs.getString("quot")));
                pktListMap.put("vlu", util.nvl(rs.getString("vlu")));
                String getPktPrp =
                    " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val " +
                    " from stk_dtl a, mprp b, rep_prp c " +
                    " where a.mprp = b.prp and b.prp = c.prp and c.mdl = ? and grp=1 and a.mstk_idn = ? " +
                    " order by c.rnk, c.srt ";

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
                pktListMap.put("VNM", util.nvl(rs.getString("vnm")));
                pktList.add(pktListMap);
                ttl = util.nvl(rs.getString("ttl_vlu")).trim();
                dtl.put("ttlCts", rs.getString("ttl_cts"));
                dtl.put("ttlQty", rs.getString("ttl_qty"));
                dtl.put("ttlVlu", ttl);
            }
            rs.close();
            pst.close();
            if (ttl != null && !ttl.equals("")) {
                String Gtotal = ttl;
                params = new ArrayList();
                if (!idnLst.equals("")) {
                    String refTyp="SAL";
                    if(typ.equals("DLV"))
                    refTyp="DLV";
                    String imcQ =
                        "Select A.Typ typ,A.dsc dsc,Sum(B.Charges) charges,a.app_typ,a.inv , b.rmk,A.srt From Charges_Typ A,Trns_Charges B\n" +
                        "  where a.idn=b.charges_idn and a.stt='A' and nvl(b.flg,'NA') not in('Y') and  ref_typ= ?  and ref_idn in " + idnLst +
                        "  GROUP BY A.Typ, A.Dsc, A.App_Typ, A.Inv, B.Rmk, A.srt order by A.srt";
                    params.add(refTyp);
                  outLst = db.execSqlLst("Charges", imcQ, params);
                   pst = (PreparedStatement)outLst.get(0);
                  rs = (ResultSet)outLst.get(1);
                    ArrayList chargeLst = new ArrayList();
                    while (rs.next()) {
                        HashMap charData = new HashMap();
                        String charge = util.nvl(rs.getString("charges"));
                        String app_typ = util.nvl(rs.getString("app_typ"));
                        String inv = util.nvl(rs.getString("inv"));
                        String chargetyp = util.nvl(rs.getString("typ"));
                        if (inv.equals("Y")) {
                            charData.put("DSC", util.nvl(rs.getString("dsc")));
                            charData.put("RMK", util.nvl(rs.getString("rmk")));
                            charData.put("TYP", chargetyp);
                            charData.put("CHARGE", charge);
                            chargeLst.add(charData);
                            udf.setValue(chargetyp, "yes");
                        }
                        if (app_typ.equals("TTL"))
                            Gtotal =
                                    String.valueOf(util.Round(Double.parseDouble(Gtotal) + Double.parseDouble(charge), 2));
                    }
                    rs.close();
                    pst.close();
                    session.setAttribute("pktchargeLst", chargeLst);
                }
                dtl.put("grandttlVlu", Gtotal);
            }
            genericInt.genericPrprVw(req, res, sname, mdl);
            req.setAttribute("typ", typ);
            session.setAttribute("pktPerformaList", pktList);
        }
        udf.setValue("typ", typ);
        udf.setValue("stt", stt);
        udf.setValue("grpIdn", grpIdn);
        udf.setValue("location", fmt);
        udf.setValue("consign", consign);
        req.setAttribute("dtl", dtl);
        req.setAttribute("typ", typ);
        ArrayList groupList = srchQuery.getgroupList(req, res);
        udf.setGroupList(groupList);
        udf.setValue("invoice", typ);
        udf.setValue("pktTyp", pktTyp);
        udf.setValue("saleid", idns);
        req.setAttribute("view", "Y");
        ArrayList bankList = srchQuery.getBankList(req, res);
        udf.setBankList(bankList);
        util.updAccessLog(req,res,"Performa", "Performa loadpkt end");
        return am.findForward("load");
        }
    }

  public ActionForward pktPerforma(ActionMapping am, ActionForm af, HttpServletRequest req,
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
       SaleDeliveryForm udf = (SaleDeliveryForm)af;
       SearchQuery srchQuery=new SearchQuery();
           util.updAccessLog(req,res,"Performa", "Performa pktPerforma start");
       String typ = util.nvl((String)udf.getValue("typ"));
       String stt = util.nvl((String)udf.getValue("stt"));
       String grpIdn = util.nvl((String)udf.getValue("grpIdn"));
       String location = util.nvl((String)udf.getValue("location"));
       String pktTyp = util.nvl((String)udf.getValue("pktTyp"));
       String consign = util.nvl((String)udf.getValue("consign"));
       ArrayList pktList = (ArrayList)session.getAttribute("pktPerformaList");
       ArrayList idnLst = new ArrayList();
       ArrayList pktidnLst = new ArrayList();
       for (int i = 0; i < pktList.size(); i++) {
           HashMap pktDtl = (HashMap)pktList.get(i);
           String stkIdn = (String)pktDtl.get("stkidn");
           String idn = (String)pktDtl.get("idn");
           String isChecked = util.nvl((String)udf.getValue(stkIdn));
           if (isChecked.equals("yes")) {
               if (!pktidnLst.contains(stkIdn))
                   pktidnLst.add(stkIdn);
           }
           if (!idnLst.contains(idn))
               idnLst.add(idn);
       }
       if (idnLst.size() > 0) {
           String idn = idnLst.toString();
           idn = idn.replaceAll("\\[", "");
           idn = idn.replaceAll("\\]", "").replaceAll(" ", "");
           String stkidn = pktidnLst.toString();
           stkidn = stkidn.replaceAll("\\[", "");
           stkidn = stkidn.replaceAll("\\]", "").replaceAll(" ", "");
           ArrayList chargeLst = (ArrayList)session.getAttribute("pktchargeLst");
           String applycharge = "";
           if (chargeLst != null && chargeLst.size() > 0) {
               for (int k = 0; k < chargeLst.size(); k++) {
                   HashMap charData = new HashMap();
                   charData = (HashMap)chargeLst.get(k);
                   String chargetyp = util.nvl((String)charData.get("TYP"));
                   String isChecked = util.nvl((String)udf.getValue(chargetyp));
                   if (isChecked.equals("yes")) {
                   } else {
                       applycharge = applycharge + "," + chargetyp;
                   }
               }
           }
           applycharge = applycharge.replaceFirst("\\,", "").trim();
           String url =
               info.getReqUrl() + "/marketing/performa.do?method=perInv&idn=" + idn + "&perInvIdn=" + stkidn +
               "&form=Y&typ=" + typ + "&grpIdn=" + grpIdn + "&stt=" + stt + "&location=" + location +
               "&applycharge=" + applycharge+"&pktTyp="+pktTyp+"&consign="+consign;
           req.setAttribute("performaLink", url);
       }
       udf.reset();
       ArrayList groupList = srchQuery.getgroupList(req, res);
       udf.setGroupList(groupList);
       udf.setValue("invoice", "SL");
       udf.setValue("stt", "ALL");
       udf.setValue("pktTyp", "SINGLE");
       ArrayList bankList = srchQuery.getBankList(req, res);
       udf.setBankList(bankList);
       util.updAccessLog(req,res,"Performa", "Performa pktPerforma end");
       return am.findForward("load");
       }
   }
  
  
  
  public ActionForward perInv(ActionMapping am, ActionForm af, HttpServletRequest req,
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
         rtnPg=initnormal(req,res,session,util);
         }else{
         rtnPg="connExists";   
         }
         }else
         rtnPg="sessionTO";
         if(!rtnPg.equals("sucess")){
             return am.findForward(rtnPg);   
         }else{
         SaleDeliveryForm udf = (SaleDeliveryForm)af;
         GenericInterface genericInt = new GenericImpl();
         util.updAccessLog(req,res,"Performa", "Performa perInv start");
         ArrayList ary = new ArrayList();
         ArrayList pktList = new ArrayList();
         ArrayList itemHdr = new ArrayList();
         ArrayList params = new ArrayList();
         String terms = "";
         String ttl = null;
         String grand_inc_vlu = "";
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
         String echarge = util.nvl(req.getParameter("echarge"));
         String salidn = util.nvl(req.getParameter("idn"));
         String form = util.nvl(req.getParameter("form"));
         String sname = "perInvViewLst", mdl = "PERINV_VW";
         String location = util.nvl(req.getParameter("location"));
         String applycharge = util.nvl(req.getParameter("applycharge"));
         String stt = util.nvl(req.getParameter("stt"));
         String pktTyp = util.nvl(req.getParameter("pktTyp"));
             String datePrt = util.nvl(req.getParameter("datePrt"),"SYS");
             String datePrtVal="";
         HashMap performaDtl = new HashMap();
             HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
             HashMap pageDtl=(HashMap)allPageDtl.get("PERFORMA_INV");
             if(pageDtl==null || pageDtl.size()==0){
             pageDtl=new HashMap();
             pageDtl=util.pagedef("PERFORMA_INV");
             allPageDtl.put("PERFORMA_INV",pageDtl);
             }
             info.setPageDetails(allPageDtl);
         ArrayList pageList=new ArrayList();
         HashMap pageDtlMap=new HashMap();
         String queryStr = " distinct nvl(inv_nme_idn,nme_idn) nmeIdn, nvl(inv_addr_idn,addr_idn) addrIdn ,nvl(fnl_trms_idn,nmerel_idn) nmerelIdn , ";

         String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="";
         String buy = util.nvl(req.getParameter("buy"));
          if(buy.equals("nme_idn"))
              queryStr = " distinct nvl(nme_idn,inv_nme_idn) nmeIdn, nvl(addr_idn,inv_addr_idn) addrIdn ,nvl(nmerel_idn,fnl_trms_idn) nmerelIdn , ";
          else if(typ.equals("SL")  && !buy.equals("") && !buy.equals("inv_nme_idn"))
              queryStr = " distinct nvl(nme_idn,inv_nme_idn) nmeIdn, nvl(addr_idn,inv_addr_idn) addrIdn ,nvl(nmerel_idn,fnl_trms_idn) nmerelIdn , ";
        String consign = util.nvl(req.getParameter("consign"));
             if(consign.equals(""))
                 consign="Yes";
        req.setAttribute("consign", consign);
         String idnLst = "";
         if (typ.equals(""))
             typ = "SL";
         String sttChk = " = '" + typ + "'";
         if (stt.equals("ALL"))
             sttChk = " <> 'RT'";
         if(pktTyp.equals("MIX")){
             sname = "perInvBoxViewLst";
             mdl = "PERINV_BOX_VW";
         }
         if (location.equals("HK") || location.equals("HKE")) {
             return perInvhk(am, udf, req, res);
         }
         ArrayList perFormInvLst=genericInt.genericPrprVw(req, res, sname, mdl);
         if(perFormInvLst.contains("VNM"))
             itemHdr.add("VNM"); 
         if (!form.equals("")) {
             idnLst = util.getVnm(salidn);
             idnLst = "(" + idnLst + ")";
             idnLst = idnLst.replaceAll("\\_", "").trim();
             if (idnLst.indexOf(",") > -1) {
                 boolean valid = validateidns(req, res, typ, idnLst);
                 if (valid) {
                     req.setAttribute("msg", "Please Verify Idns There is data Differerence");
                     return am.findForward("perInv");
                 }
             }
             String byrNme =
                 "select "+queryStr+" bank_id,courier,nvl(Aadat_Comm,0)+nvl(brk1_comm,0)+nvl(brk2_comm,0)+nvl(brk3_comm,0)+nvl(brk4_comm,0) comm,to_char(dte,'dd-mm-yyyy') dte from msal where idn in " +
                 idnLst;
             if (typ.equals("DLV")) {
                 byrNme =
                         "select "+queryStr+" bnk_idn bank_id,courier,\n" +
                         "nvl(Aadat_Comm,0)+nvl(brk1_comm,0)+nvl(brk2_comm,0)+nvl(brk3_comm,0)+nvl(brk4_comm,0) comm,to_char(dte,'dd-mm-yyyy') dte from mdlv where idn in " +
                         idnLst;
             }
           ArrayList  outLst = db.execSqlLst("byrName", byrNme, new ArrayList());
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet  rs = (ResultSet)outLst.get(1);
             while (rs.next()) {
                 byrIdn = util.nvl(rs.getString("nmeIdn"));
                 addrIdn = util.nvl(rs.getString("addrIdn"));
                 relIdn = util.nvl(rs.getString("nmerelIdn"));
                 bankIdn = util.nvl(rs.getString("bank_id"));
                 courier = util.nvl(rs.getString("courier"));
                 if (courier.equals("0"))
                     courier = "NONE";
                 brocommval = util.nvl(rs.getString("comm"));
                 datePrtVal = util.nvl(rs.getString("dte"));
             }
             rs.close();
             pst.close();
             if(datePrt.equals("SYS"))
                 datePrtVal=util.getToDte();
             String mddr = "select addr_idn from maddr where nme_idn=?";
             ary = new ArrayList();
             ary.add(bankIdn);
           outLst = db.execSqlLst("Company Adder", mddr, ary);
            pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
             while (rs.next()) {
                 bankaddrIdn = util.nvl(rs.getString("addr_idn"));
             }
             rs.close();
             pst.close();
         }

         if (!brocommval.equals("") && brocommval.equals("0"))
             brocommval = String.valueOf(Float.parseFloat(brocommval) / 100);
         else
             brocommval = "0";
         if (!stkIdnList.equals("")) {
             stkIdnList = util.getVnm(stkIdnList);
         }
         if (grpIdn.equals("")) {
             String companyQ =
                 "select a.nme_idn, a.fnme  from mnme A , nme_dtl b where 1 = 1  " + "and a.nme_idn=b.nme_idn and  b.mprp='PERFORMA' and b.txt='YES' " +
                 "and a.vld_dte is null  and typ = 'GROUP'";
           ArrayList  outLst = db.execSqlLst("Group Sql", companyQ, new ArrayList());
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet  rs = (ResultSet)outLst.get(1);
             if (rs.next()) {
                 grpIdn = util.nvl(rs.getString("nme_idn"));
             }
             rs.close();
             pst.close();
         }
         String grp = "select mprp, txt from nme_dtl where nme_idn=? and vld_dte is null";
         ary = new ArrayList();
         ary.add(grpIdn);
           ArrayList  outLst = db.execSqlLst("Company DtlQ", grp, ary);
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet  rs = (ResultSet)outLst.get(1);
         while (rs.next()) {
             udf.setValue(util.nvl(rs.getString("mprp")), util.nvl(rs.getString("txt"), "NA"));
             performaDtl.put(util.nvl(rs.getString("mprp")), util.nvl(rs.getString("txt"), "NA"));
         }
         rs.close();
         pst.close();
         String nmev = "select co_nme from nme_v where nme_idn=? ";
         ary = new ArrayList();
         ary.add(byrIdn);
          outLst = db.execSqlLst("addr", nmev, ary);
           pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
         if (rs.next()) {
             udf.setValue("byrnme", util.nvl(rs.getString("co_nme")));
             performaDtl.put("byrnme", util.nvl(rs.getString("co_nme")));
         }
         rs.close();
         pst.close();

         String addrSql =
             "select a.addr_idn , unt_num, bldg ||''|| street bldg , landmark ||''|| area landMk , b.city_nm||' '|| c.country_nm||' '|| a.zip addr " +
             " from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.addr_idn = ? order by a.dflt_yn desc ";
         ary = new ArrayList();
         ary.add(addrIdn);
           outLst = db.execSqlLst("addr", addrSql, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
         if (rs.next()) {
             String bldg=util.nvl((String)rs.getString("bldg"));
             udf.setValue("unit_no", rs.getString("unt_num"));
             udf.setValue("bldg", rs.getString("bldg"));
             udf.setValue("landMk", rs.getString("landMk"));
             if(!bldg.equals("")){
                 udf.setValue("addr", rs.getString("addr"));
                 performaDtl.put("addr", rs.getString("addr"));
             }
             performaDtl.put("unit_no", rs.getString("unt_num"));
             performaDtl.put("bldg", rs.getString("bldg"));
             performaDtl.put("landMk", rs.getString("landMk"));
         }

         rs.close();
         pst.close();
         //    String addDtl = "select mprp, txt from nme_dtl where nme_idn = ? and mprp in ('TEL_NO','EMAIL','FAX','PRE-CARRIAGE-BY','VESSEL/FLIGHT.NO','PORT OF DISCHARGE','FINAL DESTINATION') ";
         String addDtl =
             "Select byr,eml,mbl,qbc,fax,finaldest,preby,vesselno,portdis,ifsc,bankaccount,attn from nme_cntct_v where nme_id=? ";
         ary = new ArrayList();
         ary.add(byrIdn);
           outLst = db.execSqlLst("addDtl", addDtl, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
         while (rs.next()) {
             //    udf.setValue(util.nvl(rs.getString("mprp")), util.nvl(rs.getString("txt"),"NA"));
             udf.setValue("BYTEL_NO", util.nvl(rs.getString("qbc")));
             udf.setValue("BYEMAIL", util.nvl(rs.getString("eml")));
             udf.setValue("BYMOBILE", util.nvl(rs.getString("mbl")));
             udf.setValue("BYFAX", util.nvl(rs.getString("fax")));
             udf.setValue("BYFINALDEST", util.nvl(rs.getString("finaldest")));
             udf.setValue("BYPREBY", util.nvl(rs.getString("preby")));
             udf.setValue("BYVESSELNO", util.nvl(rs.getString("vesselno")));
             udf.setValue("BYPORTDIS", util.nvl(rs.getString("portdis")));
             udf.setValue("BYATTN", util.nvl(rs.getString("attn")));
             performaDtl.put("BYTEL_NO", util.nvl(rs.getString("qbc")));
             performaDtl.put("BYEMAIL", util.nvl(rs.getString("eml")));
             performaDtl.put("BYMOBILE", util.nvl(rs.getString("mbl")));
             performaDtl.put("BYFAX", util.nvl(rs.getString("fax")));
             performaDtl.put("BYFINALDEST", util.nvl(rs.getString("finaldest")));
             performaDtl.put("BYPREBY", util.nvl(rs.getString("preby")));
             performaDtl.put("BYVESSELNO", util.nvl(rs.getString("vesselno")));
             performaDtl.put("BYPORTDIS", util.nvl(rs.getString("portdis")));
             performaDtl.put("BYATTN", util.nvl(rs.getString("attn")));
         }
         rs.close();
         pst.close();
         addDtl = "Select ifsc,bankaccount from nme_cntct_v where nme_id=? ";
         ary = new ArrayList();
         ary.add(bankIdn);
           outLst = db.execSqlLst("addDtl", addDtl, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
         while (rs.next()) {
             udf.setValue("BYIFSC", util.nvl(rs.getString("ifsc")));
             udf.setValue("BYBANKACCOUNT", util.nvl(rs.getString("bankaccount")));
             performaDtl.put("BYIFSC", util.nvl(rs.getString("ifsc")));
             performaDtl.put("BYBANKACCOUNT", util.nvl(rs.getString("bankaccount")));
         }
         rs.close();
         pst.close();

         String termsDtl =
             "select b.nmerel_idn, nvl(c.trm_prt, c.term) trms ,b.del_typ,b.cur  from nmerel b, mtrms c  where b.trms_idn = c.idn and b.nmerel_idn=? ";
         ary = new ArrayList();
         ary.add(relIdn);
           outLst = db.execSqlLst("termsDtl", termsDtl, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
         if (rs.next()) {
             terms = util.nvl(rs.getString("trms"));
             // udf.setValue("terms", util.nvl(rs.getString("dtls")));
             req.setAttribute("consignee", util.nvl(rs.getString("del_typ")));
             udf.setValue("termsSign", util.nvl(rs.getString("cur")));
             udf.setValue("terms", terms);
             performaDtl.put("termsSign", util.nvl(rs.getString("cur")));
             performaDtl.put("terms", terms);
         }
         rs.close();
         pst.close();
         //    String consignee = "select del_typ,cur from nmerel where nmerel_idn=? ";
         //    ary = new ArrayList();
         //    ary.add(relIdn);
         //    rs = db.execSql("consignee", consignee, ary);
         //    if(rs.next()){
         //    // udf.setValue("terms", util.nvl(rs.getString("dtls")));
         //
         //    }
         //        rs.close();
         String bankDtl =
             "select a.addr_idn , d.rmk , b.city_nm , d.fnme byrNme, bldg ||''|| street bldg , landmark ||''|| area landMk , b.city_nm||' '|| c.country_nm addr " +
             " from maddr a, mcity b, mcountry c , mnme d where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn = d.nme_idn " +
             " and a.nme_idn = ? and a.addr_idn=? order by a.dflt_yn desc ";
         ary = new ArrayList();
         ary.add(bankIdn);
         ary.add(bankaddrIdn);
           outLst = db.execSqlLst("addr", bankDtl, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
         if (rs.next()) {
             udf.setValue("bankBldg", rs.getString("bldg"));
             udf.setValue("bankLand", rs.getString("landMk"));
             udf.setValue("bankCity", rs.getString("addr"));
             udf.setValue("bankNme", rs.getString("byrNme"));
             udf.setValue("rmk", rs.getString("rmk"));
             udf.setValue("cityNme", rs.getString("city_nm"));
             performaDtl.put("bankBldg", rs.getString("bldg"));
             performaDtl.put("bankLand", rs.getString("landMk"));
             performaDtl.put("bankCity", rs.getString("addr"));
             performaDtl.put("bankNme", rs.getString("byrNme"));
             performaDtl.put("rmk", rs.getString("rmk"));
             performaDtl.put("cityNme", rs.getString("city_nm"));
         }
         rs.close();
         pst.close();
         String throubnk = "";

         String invno = salidn.replaceAll("\\,", "");
         invno = invno.replaceAll(" ", "");
         if (invno.length() > 10) {
             invno = invno.substring(0, 10);
         }
         invno = typ + "_" + invno;
         String conQ = "";
         if (!stkIdnList.equals("")) {
             conQ = " and a.mstk_idn in (" + stkIdnList + ") ";
         }
         if (!form.equals(""))
             conQ = conQ + " and c.idn in " + idnLst;
        
         brocommval="0"; //MB
         String getInvPkts =
             " select get_inv_pkt_srt(a.mstk_idn) ,a.idn ,a.rmk ,b.pkt_ty,c.thru_bank_idn, a.qty ,b.vnm , a.mstk_idn, to_char((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
             brocommval + "),'9999999999990.00') quot, " +
             " to_char(trunc(a.cts,2),'9990.99') cts , to_char(trunc(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
             brocommval + "), 2),'9999990.00') vlu , " + " trunc((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" + brocommval +
             ")*trunc(a.cts,2) *((trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)))*12/100/365),2) inc_vlu ," +
             " to_char(sum(trunc(a.cts,2)) over(partition by 'IS'),'99990.99') ttl_cts , " +
             " to_char(sum(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" + brocommval +
             ")) over(partition by 'IS') ,'999999990.99') ttl_vlu , sum(a.qty) over(partition by 'IS') ttl_qty " +
             " from jansal a, mstk b , msal c,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn and a.stt " +
             sttChk + " and c.typ in ('SL') and a.stt not in ('CL','RT') and c.nmerel_idn = d.nmerel_idn and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA')) " + conQ + " order by 1 ";

         if (typ.equals("DLV")) {
             getInvPkts =
                     " select get_inv_pkt_srt(a.mstk_idn) ,a.idn ,a.rmk, b.pkt_ty,c.thru_bank_idn thru_bank_idn, a.qty ,b.vnm , a.mstk_idn, to_char((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                     brocommval + "),'9999999999990.00') quot, " +
                     " to_char(trunc(a.cts,2),'9990.99') cts , to_char(trunc(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                     brocommval + "), 2),'9999990.00') vlu , " + " trunc((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                     brocommval +
                     ")*trunc(a.cts,2) *((trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)))*12/100/365),2) inc_vlu ," +
                     " to_char(sum(trunc(a.cts,2)) over(partition by 'IS'),'99990.99') ttl_cts , " +
                     " to_char(sum(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" + brocommval +
                     ")) over(partition by 'IS') ,'999999990.00') ttl_vlu , sum(a.qty) over(partition by 'IS') ttl_qty " +
                     " from dlv_dtl a, mstk b , mdlv c,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn and a.stt " +
                     sttChk + " and a.stt not in ('CL','RT') and  c.typ in ('DLV')and c.nmerel_idn = d.nmerel_idn and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA')) and c.stt='IS' " + conQ +
                     " order by 1 ";
             

         }
         if (typ.equals("CS")) {
             getInvPkts =
                     " select get_inv_pkt_srt(a.mstk_idn) ,a.idn ,a.rmk, b.pkt_ty, a.qty ,b.vnm ,0 thru_bank_idn, a.mstk_idn,  to_char((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                     brocommval + "),'9999999999,990.00') quot, " +
                     " to_char(trunc(a.cts,2),'9990.99') cts , to_char(trunc(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                     brocommval + "), 2),'9999990.00') vlu , " +
                     " to_char(sum(trunc(a.cts,2)) over(partition by 'IS'),'999990.99') ttl_cts , " +
                     " to_char(sum(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" + brocommval +
                     ")) over(partition by 'IS') ,'999999990.00') ttl_vlu , sum(a.qty) over(partition by 'IS') ttl_qty " +
                     " from jansal a, mstk b , msal c where a.mstk_idn = b.idn and c.idn = a.idn and a.stt " + sttChk +
                     " and c.typ in ('CS') and a.stt not in ('CL','RT') and  c.stt='IS' and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA')) " + conQ + " order by 1 ";
         }
         


           outLst = db.execSqlLst("performInv", getInvPkts, params);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);

         while (rs.next()) {
             HashMap pktListMap = new HashMap();
             String mstkIdn = rs.getString("mstk_idn");
             String pkt_ty = rs.getString("pkt_ty");
             pktListMap.put("pkttyp", pkt_ty);
             pktListMap.put("cts", util.nvl(rs.getString("cts")));
             pktListMap.put("qty", util.nvl(rs.getString("qty")));
             pktListMap.put("quot", util.nvl(rs.getString("quot")));
             pktListMap.put("vlu", util.nvl(rs.getString("vlu")));
             throubnk = util.nvl(rs.getString("thru_bank_idn"));
             //    if(typ.equals("DLY")){
             //    pktListMap.put("inc_vlu",util.nvl(rs.getString("inc_vlu")));
             //        req.setAttribute("inc_vlu", "Y");
             //    }
             String getPktPrp =
                 " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val " + " from stk_dtl a, mprp b, rep_prp c " +
                 " where a.mprp = b.prp and b.prp = c.prp and c.mdl = ? and grp=1 and a.mstk_idn = ? " +
                 " order by c.rnk, c.srt ";

             if(!pkt_ty.equals("NR") && cnt.equals("hk")){
                 String getPktPrpQ="";
                 pageList=(ArrayList)pageDtl.get("MIX_INV");
                 if(pageList!=null && pageList.size() >0){
                     for(int j=0;j<pageList.size();j++){
                     pageDtlMap=(HashMap)pageList.get(j);
                     fld_nme=(String)pageDtlMap.get("fld_nme");
                     lov_qry=(String)pageDtlMap.get("lov_qry");
                     val_cond=(String)pageDtlMap.get("val_cond");
                     if(val_cond.equals(""))
                     getPktPrpQ=getPktPrpQ+" WHEN m.cmp >= "+lov_qry+" then '"+fld_nme+"' ";    
                     else
                     getPktPrpQ=getPktPrpQ+" WHEN m.cmp between "+lov_qry+" and "+val_cond+" then '"+fld_nme+"' ";    
                 }}
                 getPktPrp =" select mprp, nvl(alt_val, val) val from (\n" + 
                 "select a.mprp\n" + 
                 ", decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val\n" + 
                 ", decode(dept.val, '96-UP-MIX', decode(b.prp, 'MIX_CLARITY',\n" + 
                 "   CASE\n" + 
                 "    WHEN a.srt >= 420 Then\n" + 
                 "    CASE\n" +getPktPrpQ+ 
                 "    END\n" + 
                 "   Else\n" + 
                 "    Null\n" + 
                 "   End\n" + 
                 ", null), null) alt_val\n" + 
                 "from mstk m, stk_dtl a, mprp b, rep_prp c, stk_dtl dept\n" + 
                 "where m.idn = a.mstk_idn and m.idn = dept.mstk_idn\n" + 
                 "and a.mprp = b.prp and b.prp = c.prp\n" + 
                 "and a.grp = 1 and dept.grp = 1\n" + 
                 "and dept.mprp = 'DEPT' and a.mstk_idn = dept.mstk_idn\n" + 
                 "and c.mdl = ?\n" + 
                 "and m.idn = ?\n" + 
                 "order by c.rnk, c.srt)"; 
                 
             }

             ary = new ArrayList();
             if(pkt_ty.equals("NR"))
             ary.add("PERINV_VW");
             else
             ary.add("PERINV_BOX_VW"); 
             ary.add(mstkIdn);
          ArrayList outLst1 = db.execSqlLst("performInv", getPktPrp, ary);
           PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
          ResultSet  rs1 = (ResultSet)outLst1.get(1);
             while (rs1.next()) {
                 String lPrp = util.nvl(rs1.getString("mprp"));
                                    
                 String lVal = util.nvl(rs1.getString("val"));
                 if(lPrp.equals("MIX_MM_SIZE") && cnt.equalsIgnoreCase("fa")){
                    lVal = util.nvl(rs.getString("rmk"));
                 }
                 if(lPrp.equals("MIX_CLARITY"))
                     lPrp="CLR" ;  
                 if(lPrp.equals("MIX_SIZE"))
                     lPrp="SIZE" ;   
                 pktListMap.put(lPrp, lVal);
                 if(!itemHdr.contains(lPrp))
                 itemHdr.add(lPrp);
             }
             rs1.close();
             pst1.close();
             pktListMap.put("VNM", util.nvl(rs.getString("vnm")));
             pktList.add(pktListMap);
             udf.setValue("ttlCts", rs.getString("ttl_cts"));
             udf.setValue("ttlQty", rs.getString("ttl_qty"));
             ttl = util.nvl(rs.getString("ttl_vlu")).trim();
             udf.setValue("ttlVlu", ttl);
             udf.setValue("echarge", echarge);
             performaDtl.put("ttlCts", rs.getString("ttl_cts"));
             performaDtl.put("ttlQty", rs.getString("ttl_qty"));
             performaDtl.put("ttlVlu", ttl);
             performaDtl.put("echarge", echarge);


         }
         rs.close();
        pst.close();
         if (!pktTyp.equals("MIX") && !typ.equals("CS")) {
             String throubnkSql = "select mprp, txt from nme_dtl where nme_dtl_idn=?";
             ary = new ArrayList();
             ary.add(throubnk);
           ArrayList outLst1 = db.execSqlLst("Throug bank", throubnkSql, ary);
            PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
           ResultSet  rs1 = (ResultSet)outLst1.get(1);
             while (rs1.next()) {
                 String conSignee = util.nvl(rs1.getString("txt"), "NA");
                 req.setAttribute("consignee", conSignee);
             }
             rs1.close();
             pst1.close();
         }

         if (ttl != null && !ttl.equals("")) {
             String Gtotal = "0";
             if (!echarge.equals(""))
                 Gtotal = String.valueOf(Double.parseDouble(ttl) + Double.parseDouble(echarge));
             else
                 Gtotal = ttl;
             params = new ArrayList();
             if (!idnLst.equals("")) {
                 String refTyp="SAL";
                 if(typ.equals("DLV"))
                 refTyp="DLV";
                 String imcQ =
                     "Select A.Typ typ,A.dsc dsc,Sum(B.Charges) charges,a.app_typ,a.inv , b.rmk, A.srt From Charges_Typ A,Trns_Charges B\n" +
                     "  where a.idn=b.charges_idn and a.stt='A' and nvl(b.flg,'NA') not in('Y') and  ref_typ= ? and ref_idn in " + idnLst +
                     "  GROUP BY A.Typ, A.Dsc, A.App_Typ, A.Inv, B.Rmk, A.srt order by A.srt";
               params.add(refTyp);
               outLst = db.execSqlLst("Charges", imcQ, params);
                pst = (PreparedStatement)outLst.get(0);
                rs = (ResultSet)outLst.get(1);
                 ArrayList chargeLst = new ArrayList();
                 while (rs.next()) {
                     HashMap charData = new HashMap();
                     String charge = util.nvl(rs.getString("charges"));
                     String app_typ = util.nvl(rs.getString("app_typ"));
                     String inv = util.nvl(rs.getString("inv"));
                     String chargetyp = util.nvl(rs.getString("typ"));

                     if (inv.equals("Y") && (applycharge.indexOf(chargetyp) == -1 || applycharge.equals(""))) {
                         charData.put("DSC", util.nvl(rs.getString("dsc")));
                         charData.put("RMK", util.nvl(rs.getString("rmk")));
                         charData.put("CHARGE", charge);
                         chargeLst.add(charData);
                     }
                     if (app_typ.equals("TTL") && (applycharge.indexOf(chargetyp) == -1 || applycharge.equals("")))
                         Gtotal =
                                 String.valueOf(util.Round(Double.parseDouble(Gtotal) + Double.parseDouble(charge), 2));
                 }
                 rs.close();
                 pst.close();
                 req.setAttribute("chargeLst", chargeLst);
             }
             udf.setValue("grandttlVlu", Gtotal);
             //String num=rs.getString("ttl_vlu").trim();
             String words = util.convertNumToAlp(Gtotal);
             udf.setValue("inwords", words);
             performaDtl.put("grandttlVlu", Gtotal);
             performaDtl.put("inwords", words);
         }
         performaDtl.put("datePrtVal", datePrtVal);
         genericInt.genericPrprVw(req, res, sname, mdl);
         req.setAttribute("typ", pktTyp);
         req.setAttribute("pktList", pktList);
         req.setAttribute("itemHdr", itemHdr);
         udf.setValue("courierS", courier);
         udf.setValue("invno", invno);
         performaDtl.put("courierS", courier);
         performaDtl.put("invno", invno);
         performaDtl.put("byrIdn", byrIdn);
         req.setAttribute("performaDtl", performaDtl);
        String consignt = util.nvl(req.getParameter("consign"));
             if(!consignt.equals(""))
            req.setAttribute("consignee", consignt);
         String invrefno = util.nvl(req.getParameter("invno"));
           if(!invrefno.equals(""))
           req.setAttribute("invrefno", invrefno);
         util.updAccessLog(req,res,"Performa", "Performa perInv end");
         return am.findForward("perInv");
         }
     }


    public ActionForward perInvhk(ActionMapping am, ActionForm af, HttpServletRequest req,
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
        rtnPg=initnormal(req,res,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
        util.updAccessLog(req,res,"Performa", "Performa perInvhk start");
        GenericInterface genericInt = new GenericImpl();
        SaleDeliveryForm udf = (SaleDeliveryForm)af;
        ArrayList ary = new ArrayList();
        ArrayList params = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        String performaHdrTxt = util.nvl((String)dbinfo.get("PERFORMAHDR"));
        String byrIdn = util.nvl(req.getParameter("byrIdn"));
        String grpIdn = util.nvl(req.getParameter("grpIdn"));
        String relIdn = util.nvl(req.getParameter("relIdn"));
        String addrIdn = util.nvl(req.getParameter("byrAddIdn"));
        String bankIdn = util.nvl(req.getParameter("bankIdn"));
        String bankIdnLoc = util.nvl(req.getParameter("bankIdnLoc"));
        String bankaddrIdn = util.nvl(req.getParameter("bankAddIdn"));
        String stkIdnList = util.nvl(req.getParameter("perInvIdn"));
        String brocommval = util.nvl(req.getParameter("brocommval"));
        String location = util.nvl(req.getParameter("location"));
        String echarge = util.nvl(req.getParameter("echarge"));
        String salidn = util.nvl(req.getParameter("idn"));
        String courier = util.nvl(req.getParameter("courier"));
        String applycharge = util.nvl(req.getParameter("applycharge"));
        String typ = util.nvl(req.getParameter("typ"));
        String form = util.nvl(req.getParameter("form"));
        String stt = util.nvl(req.getParameter("stt"));
        String pktTyp = util.nvl(req.getParameter("pktTyp"));
        String sendm=util.nvl(req.getParameter("mail"));  
        String format = util.nvl(req.getParameter("format"),"N");
        String datePrt = util.nvl(req.getParameter("datePrt"),"SYS");
        String datePrtVal="";
        String sname = "perInvViewLstHK", mdl = "PERINV_VW_LOC";
        String frmNme ="perInvHK";
        String ttl = null;
        String idnLst = "";
            HashMap prp = info.getPrp();
            ArrayList boxidList = (ArrayList)prp.get("BOX_IDP");
            ArrayList boxidDsc = (ArrayList)prp.get("BOX_IDD");
            req.setAttribute("fmt", (String)udf.getValue("fmt"));
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
             HashMap pageDtl=(HashMap)allPageDtl.get("PERFORMA_INV");
             ArrayList pageList=new ArrayList();
             HashMap pageDtlMap=new HashMap();
             String fld_ttl="",dflt_val="",lov_qry="",flg1="";
             String val_cond="",fld_nme="",fld_typ="";
        ArrayList pktList = new ArrayList();
            String queryStr = " distinct nvl(inv_nme_idn,nme_idn) nmeIdn, nvl(inv_addr_idn,addr_idn) addrIdn ,nvl(fnl_trms_idn,nmerel_idn) nmerelIdn , ";

            String buy = util.nvl(req.getParameter("buy"));
             if(buy.equals("nme_idn"))
                 queryStr = " distinct nvl(nme_idn,inv_nme_idn) nmeIdn, nvl(addr_idn,inv_addr_idn) addrIdn ,nvl(nmerel_idn,fnl_trms_idn) nmerelIdn , ";
             else if(typ.equals("SL"))
                 queryStr = " distinct nvl(nme_idn,inv_nme_idn) nmeIdn, nvl(addr_idn,inv_addr_idn) addrIdn ,nvl(nmerel_idn,fnl_trms_idn) nmerelIdn , ";
        HashMap rtnObj = new HashMap();
        HashMap performaDtl = new HashMap();
        if (typ.equals(""))
            typ = "SL";
        String sttChk = " = '" + typ + "'";
        if (stt.equals("ALL"))
            sttChk = " <> 'RT'";
        if(pktTyp.equals("MIX")){
            sname = "perInvBoxViewLst";
            mdl = "PERINV_BOX_VW";
        }
        ArrayList perFormInvLst= genericInt.genericPrprVw(req, res, sname, mdl);
        if(perFormInvLst.contains("VNM"))
            itemHdr.add("VNM");    
        if (!form.equals("")) {
            idnLst = util.getVnm(salidn);
            idnLst = "(" + idnLst + ")";
            idnLst = idnLst.replaceAll("\\_", "").trim();
            if (idnLst.indexOf(",") > -1) {
                boolean valid = validateidns(req, res, typ, idnLst);
                if (valid) {
                    req.setAttribute("msg", "Please Verify Idns There is data Differerence");
                    return am.findForward("perInvHK");
                }
            }
            String byrNme =
                "select  "+queryStr+" bank_id,courier,nvl(Aadat_Comm,0)+nvl(brk1_comm,0)+nvl(brk2_comm,0)+nvl(brk3_comm,0)+nvl(brk4_comm,0) comm,to_char(dte,'dd-mm-yyyy') dte from msal where idn in " +
                idnLst;
            if (typ.equals("DLV")) {
                byrNme =
                        "select  "+queryStr+" bnk_idn bank_id,courier,\n" +
                        "nvl(Aadat_Comm,0)+nvl(brk1_comm,0)+nvl(brk2_comm,0)+nvl(brk3_comm,0)+nvl(brk4_comm,0) comm,to_char(dte,'dd-mm-yyyy') dte from mdlv where idn in " +
                        idnLst;
            }
          ArrayList  outLst = db.execSqlLst("byrName", byrNme, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                byrIdn = util.nvl(rs.getString("nmeIdn"));
                addrIdn = util.nvl(rs.getString("addrIdn"));
                relIdn = util.nvl(rs.getString("nmerelIdn"));
                bankIdn = util.nvl(rs.getString("bank_id"));
                courier = util.nvl(rs.getString("courier"));
                if (courier.equals("0"))
                    courier = "NONE";
                brocommval = util.nvl(rs.getString("comm"));
                datePrtVal = util.nvl(rs.getString("dte"));

            }
            rs.close();
            pst.close();
            
            if(datePrt.equals("SYS"))
                datePrtVal=util.getToDte();
            String mddr = "select addr_idn from maddr where nme_idn=?";
            ary = new ArrayList();
            ary.add(bankIdn);
          outLst = db.execSqlLst("Company Adder", mddr, ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                bankaddrIdn = util.nvl(rs.getString("addr_idn"));
            }
            rs.close();
            pst.close();
        }
        if (!brocommval.equals(""))
            brocommval = String.valueOf(Float.parseFloat(brocommval) / 100);
        else
            brocommval = "0";
        if (!stkIdnList.equals("")) {
            stkIdnList = util.getVnm(stkIdnList);
        }

        if (grpIdn.equals("")) {
            String companyQ =
                "select a.nme_idn, a.fnme  from mnme A , nme_dtl b where 1 = 1  " + "and a.nme_idn=b.nme_idn and  b.mprp='PERFORMA' and b.txt='YES' " +
                "and a.vld_dte is null  and typ = 'GROUP'";
         ArrayList outLst = db.execSqlLst("Group Sql", companyQ, new ArrayList());
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            if (rs.next()) {
                grpIdn = util.nvl(rs.getString("nme_idn"));
            }
            rs.close();
            pst.close();
        }
        String grp = "select mprp, txt from nme_dtl where nme_idn=? and vld_dte is null";
        ary = new ArrayList();
        ary.add(grpIdn);
         ArrayList outLst = db.execSqlLst("Company DtlQ", grp, ary);
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
         ResultSet  rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            udf.setValue(util.nvl(rs.getString("mprp")), util.nvl(rs.getString("txt"), "NA"));
            performaDtl.put(util.nvl(rs.getString("mprp")), util.nvl(rs.getString("txt"), "NA"));
            rtnObj.put(util.nvl(rs.getString("mprp").toUpperCase()), util.nvl(rs.getString("txt"), "NA"));

        }
        rs.close();
         pst.close();
        String nmev = "select co_nme from nme_v where nme_idn=? ";
        ary = new ArrayList();
        ary.add(byrIdn);
          outLst = db.execSqlLst("addr", nmev, ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        if (rs.next()) {
            udf.setValue("byrnme", util.nvl(rs.getString("co_nme")));
            performaDtl.put("byrnme", util.nvl(rs.getString("co_nme")));
            rtnObj.put("BYRNME", util.nvl(rs.getString("co_nme")).trim());
        }
        rs.close();
            pst.close();
        String addrSql =
            "select a.addr_idn , unt_num, byr.get_nm(nme_idn) byr, bldg ||''|| street bldg , landmark ||''|| area landMk , b.city_nm||' '|| c.country_nm||' '|| a.zip addr " +
            " from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.addr_idn = ? order by a.dflt_yn desc ";
        ary = new ArrayList();
        ary.add(addrIdn);
          outLst = db.execSqlLst("addr", addrSql, ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        if (rs.next()) {
            String bldg=util.nvl((String)rs.getString("bldg"));
            udf.setValue("unit_no", rs.getString("unt_num"));
            udf.setValue("bldg", rs.getString("bldg"));
            udf.setValue("landMk", rs.getString("landMk"));
            //udf.setValue("city", rs.getString("addr"));
            if(!bldg.equals("")){
                udf.setValue("addr", rs.getString("addr"));
                performaDtl.put("addr", rs.getString("addr"));
                rtnObj.put("ADDR", util.nvl(rs.getString("addr")).trim());

            }
            performaDtl.put("unit_no", rs.getString("unt_num"));
            performaDtl.put("bldg", rs.getString("bldg"));
            performaDtl.put("landMk", rs.getString("landMk"));
            rtnObj.put("UNIT_NO", rs.getString("unt_num"));
            rtnObj.put("BLDG", rs.getString("bldg"));
            rtnObj.put("LANDMK", rs.getString("landMk"));
        }

        rs.close();
        pst.close();
        //    String addDtl = "select mprp, txt from nme_dtl where nme_idn = ? and mprp in ('TEL_NO','EMAIL','FAX','PRE-CARRIAGE-BY','VESSEL/FLIGHT.NO','PORT OF DISCHARGE','FINAL DESTINATION') ";
        String addDtl =
            "Select byr,eml,mbl,qbc,fax,finaldest,preby,vesselno,portdis,ifsc,bankaccount,attn from nme_cntct_v where nme_id=? ";
        ary = new ArrayList();
        ary.add(byrIdn);
          outLst = db.execSqlLst("addDtl", addDtl, ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            //    udf.setValue(util.nvl(rs.getString("mprp")), util.nvl(rs.getString("txt"),"NA"));
            udf.setValue("BYTEL_NO", util.nvl(rs.getString("qbc")));
            udf.setValue("BYEMAIL", util.nvl(rs.getString("eml")));
            udf.setValue("BYMOBILE", util.nvl(rs.getString("mbl")));
            udf.setValue("BYFAX", util.nvl(rs.getString("fax")));
            udf.setValue("BYFINALDEST", util.nvl(rs.getString("finaldest")));
            udf.setValue("BYPREBY", util.nvl(rs.getString("preby")));
            udf.setValue("BYVESSELNO", util.nvl(rs.getString("vesselno")));
            udf.setValue("BYPORTDIS", util.nvl(rs.getString("portdis")));
            udf.setValue("BYATTN", util.nvl(rs.getString("attn")));
            performaDtl.put("BYTEL_NO", util.nvl(rs.getString("qbc")));
            performaDtl.put("BYEMAIL", util.nvl(rs.getString("eml")));
            performaDtl.put("BYMOBILE", util.nvl(rs.getString("mbl")));
            performaDtl.put("BYFAX", util.nvl(rs.getString("fax")));
            performaDtl.put("BYFINALDEST", util.nvl(rs.getString("finaldest")));
            performaDtl.put("BYPREBY", util.nvl(rs.getString("preby")));
            performaDtl.put("BYVESSELNO", util.nvl(rs.getString("vesselno")));
            performaDtl.put("BYPORTDIS", util.nvl(rs.getString("portdis")));
            performaDtl.put("BYATTN", util.nvl(rs.getString("attn")));
            rtnObj.put("BYTEL_NO", util.nvl(rs.getString("qbc")));
            rtnObj.put("BYEMAIL", util.nvl(rs.getString("eml")));
            rtnObj.put("BYMOBILE", util.nvl(rs.getString("mbl")));
            rtnObj.put("BYFAX", util.nvl(rs.getString("fax")));
            rtnObj.put("BYFINALDEST", util.nvl(rs.getString("finaldest")));
            rtnObj.put("BYPREBY", util.nvl(rs.getString("preby")));
            rtnObj.put("BYVESSELNO", util.nvl(rs.getString("vesselno")));
            rtnObj.put("BYPORTDIS", util.nvl(rs.getString("portdis")));
            rtnObj.put("BYATTN", util.nvl(rs.getString("attn")));
        }
        rs.close();
        pst.close();
        addDtl = "Select ifsc,bankaccount from nme_cntct_v where nme_id=? ";
        ary = new ArrayList();
        ary.add(bankIdn);
          outLst = db.execSqlLst("addDtl", addDtl, ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            udf.setValue("BYIFSC", util.nvl(rs.getString("ifsc")));
            udf.setValue("BYBANKACCOUNT", util.nvl(rs.getString("bankaccount")));
            performaDtl.put("BYIFSC", util.nvl(rs.getString("ifsc")));
            performaDtl.put("BYBANKACCOUNT", util.nvl(rs.getString("bankaccount")));
            rtnObj.put("BYIFSC", util.nvl(rs.getString("ifsc")));
            rtnObj.put("BYBANKACCOUNT", util.nvl(rs.getString("bankaccount")));
        }
        rs.close();
        pst.close();
        String terms = "";
        String termsDtl =
            "select b.nmerel_idn, nvl(c.trm_prt, c.term) trms ,b.del_typ,b.cur  from nmerel b, mtrms c  where b.trms_idn = c.idn and b.nmerel_idn=? ";
        ary = new ArrayList();
        ary.add(relIdn);
          outLst = db.execSqlLst("termsDtl", termsDtl, ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        if (rs.next()) {
            terms = util.nvl(rs.getString("trms"));
            // udf.setValue("terms", util.nvl(rs.getString("dtls")));
            req.setAttribute("consignee", util.nvl(rs.getString("del_typ")));
            udf.setValue("termsSign", util.nvl(rs.getString("cur")));
            udf.setValue("terms", terms);
            performaDtl.put("termsSign", util.nvl(rs.getString("cur")));
            performaDtl.put("terms", terms);
            rtnObj.put("TERMSSIGN", util.nvl(rs.getString("cur")));
            rtnObj.put("TERMS", terms);
        }
        //    String consignee = "select del_typ,cur from nmerel where nmerel_idn=? ";
        //    ary = new ArrayList();
        //    ary.add(relIdn);
        //    rs = db.execSql("consignee", consignee, ary);
        //    if(rs.next()){
        //    // udf.setValue("terms", util.nvl(rs.getString("dtls")));
        //    udf.setValue("consignee", util.nvl(rs.getString("del_typ")));
        //    udf.setValue("termsSign",util.nvl(rs.getString("cur")));
        //    }
        rs.close();
        pst.close();
        String bankDtl =
            "select a.addr_idn , d.rmk , b.city_nm , d.fnme byrNme, bldg ||''|| street bldg , landmark ||''|| area landMk , b.city_nm||' '|| c.country_nm||' '|| a.zip addr  " +
            " from maddr a, mcity b, mcountry c , mnme d where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn = d.nme_idn " +
            " and a.nme_idn = ? and a.addr_idn=? order by a.dflt_yn desc ";
        ary = new ArrayList();
        ary.add(bankIdn);
        ary.add(bankaddrIdn);
          outLst = db.execSqlLst("addr", bankDtl, ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        if (rs.next()) {
            udf.setValue("bankBldg", rs.getString("bldg"));
            udf.setValue("bankLand", rs.getString("landMk"));
            udf.setValue("bankCity", rs.getString("addr"));
            udf.setValue("bankNme", rs.getString("byrNme"));
            udf.setValue("rmk", rs.getString("rmk"));
            udf.setValue("cityNme", rs.getString("city_nm"));
            performaDtl.put("bankBldg", rs.getString("bldg"));
            performaDtl.put("bankLand", rs.getString("landMk"));
            performaDtl.put("bankCity", rs.getString("addr"));
            performaDtl.put("bankNme", rs.getString("byrNme"));
            performaDtl.put("rmk", rs.getString("rmk"));
            performaDtl.put("cityNme", rs.getString("city_nm"));
            rtnObj.put("BANKBLDG", rs.getString("bldg"));
            rtnObj.put("BANKLAND", rs.getString("landMk"));
            rtnObj.put("BANKCITY", rs.getString("addr"));
            rtnObj.put("BANKNME", rs.getString("byrNme"));
            rtnObj.put("RMK", rs.getString("rmk"));
            rtnObj.put("CITYNME", rs.getString("city_nm"));
        }
        rs.close();
        pst.close();

        String throubnk = "";
        String invno = salidn.replaceAll("\\,", "-");
        invno = invno.replaceAll(" ", "");
//        if (invno.length() > 10) {
//            invno = invno.substring(0, 10);
//        }
        invno = typ + "_" + invno;
        String conQ = "";
        if (!stkIdnList.equals("")) {
            conQ = " and a.mstk_idn in (" + stkIdnList + ") ";
        }
        if (!form.equals(""))
            conQ = conQ + " and c.idn in " + idnLst;
            
        brocommval="0"; //MB
        String getInvPkts =
            " select get_inv_pkt_srt(a.mstk_idn) ,b.cert_no,a.idn ,b.pkt_ty,c.thru_bank_idn, a.qty ,b.vnm , a.mstk_idn, to_char((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
            brocommval + "),'9999999999990.00') quot, " +
            " to_char(a.cts,'9990.00') cts ,b.rap_rte, to_char(trunc(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
            brocommval + "), 2),'9999990.00') vlu , " + " trunc((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" + brocommval +
            ")*trunc(a.cts,2) *((trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)))*12/100/365),2) inc_vlu ," +
            " To_Char(trunc((((Nvl(Fnl_Sal, Quot)-Nvl(Fnl_Sal,Quot)*" + brocommval +
            ")/greatest(b.rap_rte,1))*100)-100,2),'99990.99') dis," +
            " to_char(sum(trunc(a.cts,2)) over(partition by 'IS'),'99990.99') ttl_cts , " +
            " to_char(sum(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" + brocommval +
            ")) over(partition by 'IS') ,'999999990.00') ttl_vlu , sum(a.qty) over(partition by 'IS') ttl_qty " +
            " from jansal a, mstk b , msal c,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn and a.stt " +
            sttChk + " and c.typ in ('SL') and a.stt not in ('CL','RT') and c.nmerel_idn = d.nmerel_idn and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA')) " + conQ + " order by 1 ";

          if (typ.equals("DLV") && cnt.equalsIgnoreCase("kj")) {
              
              getInvPkts =
                      " select get_inv_pkt_srt(a.mstk_idn) ,b.cert_no,a.idn ,b.pkt_ty,c.thru_bank_idn thru_bank_idn, a.qty ,b.vnm , a.mstk_idn, to_char((Nvl(Quot,Fnl_Sal)-nvl(Quot,Fnl_Sal)*" +
                      brocommval + "),'9999999999990.00') quot, " +
                      " to_char(a.cts,'9990.00') cts ,b.rap_rte, to_char(trunc(trunc(a.cts,2)*(Nvl(Quot,Fnl_Sal)-nvl(Quot,Fnl_Sal)*" +
                      brocommval + "), 2),'9999990.00') vlu , " +
                      " To_Char(trunc((((Nvl(Quot,Fnl_Sal)-Nvl(Quot,Fnl_Sal)*" + brocommval +
                      ")/greatest(b.rap_rte,1))*100)-100,2),'99990.99') dis," +
                      " trunc((Nvl(Quot,Fnl_Sal)-nvl(Quot,Fnl_Sal)*" + brocommval +
                      ")*trunc(a.cts,2) *((trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)))*12/100/365),2) inc_vlu ," +
                      " to_char(sum(trunc(a.cts,2)) over(partition by 'IS'),'99990.99') ttl_cts , " +
                      " to_char(sum(trunc(a.cts,2)*(Nvl(Quot,Fnl_Sal)-nvl(Quot,Fnl_Sal)*" + brocommval +
                      ")) over(partition by 'IS') ,'999999990.00') ttl_vlu , sum(a.qty) over(partition by 'IS') ttl_qty " +
                      " from dlv_dtl a, mstk b , mdlv c,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn and a.stt " +
                      sttChk + " and c.typ in ('DLV') and a.stt not in ('CL','RT') and c.nmerel_idn = d.nmerel_idn and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA')) and c.stt='IS' " + conQ +
                      " order by 1 ";

          }else if(typ.equals("DLV")){
            
            getInvPkts =
                    " select get_inv_pkt_srt(a.mstk_idn) ,b.cert_no,a.idn ,b.pkt_ty,c.thru_bank_idn thru_bank_idn, a.qty ,b.vnm , a.mstk_idn, to_char((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                    brocommval + "),'9999999999990.00') quot, " +
                    " to_char(a.cts,'9990.00') cts ,b.rap_rte, to_char(trunc(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                    brocommval + "), 2),'9999990.00') vlu , " +
                    " To_Char(trunc((((Nvl(Fnl_Sal, Quot)-Nvl(Fnl_Sal,Quot)*" + brocommval +
                    ")/greatest(b.rap_rte,1))*100)-100,2),'99990.99') dis," +
                    " trunc((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" + brocommval +
                    ")*trunc(a.cts,2) *((trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)))*12/100/365),2) inc_vlu ," +
                    " to_char(sum(trunc(a.cts,2)) over(partition by 'IS'),'99990.99') ttl_cts , " +
                    " to_char(sum(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" + brocommval +
                    ")) over(partition by 'IS') ,'999999990.00') ttl_vlu , sum(a.qty) over(partition by 'IS') ttl_qty " +
                    " from dlv_dtl a, mstk b , mdlv c,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn and a.stt " +
                    sttChk + " and c.typ in ('DLV') and a.stt not in ('CL','RT') and c.nmerel_idn = d.nmerel_idn and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA')) and c.stt='IS' " + conQ +
                    " order by 1 ";

        }
        if (typ.equals("CS")) {
            getInvPkts =
                    " select get_inv_pkt_srt(a.mstk_idn) ,b.cert_no,a.idn ,b.pkt_ty, a.qty ,b.vnm ,0 thru_bank_idn, a.mstk_idn,  to_char((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                    brocommval + "),'9999999999,990.00') quot, " +
                    " to_char(a.cts,'9990.00') cts ,b.rap_rte, to_char(trunc(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                    brocommval + "), 2),'9999990.00') vlu , " +
                    " to_char(sum(trunc(a.cts,2)) over(partition by 'IS'),'99990.99') ttl_cts , " +
                    " To_Char(trunc((((Nvl(Fnl_Sal, Quot)-Nvl(Fnl_Sal,Quot)*" + brocommval +
                    ")/greatest(b.rap_rte,1))*100)-100,2),'99990.99') dis," +
                    " to_char(sum(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" + brocommval +
                    ")) over(partition by 'IS') ,'999999990.00') ttl_vlu , sum(a.qty) over(partition by 'IS') ttl_qty " +
                    " from jansal a, mstk b , msal c where a.mstk_idn = b.idn and c.idn = a.idn and a.stt  " + sttChk +
                    " and c.typ in ('CS') and a.stt not in ('CL','RT') and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA')) and c.stt='IS' " + conQ + " order by 1 ";
        }
       

          outLst = db.execSqlLst("performInv", getInvPkts, params);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
        int ct=1;
        while (rs.next()) {
            HashMap pktListMap = new HashMap();
            String mstkIdn = rs.getString("mstk_idn");
            String pkt_ty = rs.getString("pkt_ty");
            pktListMap.put("srNo","  "+ct);
            pktListMap.put("pkttyp", pkt_ty);
            pktListMap.put("cts", util.nvl(rs.getString("cts")).trim());
            pktListMap.put("qty", util.nvl(rs.getString("qty")).trim());
            pktListMap.put("quot", util.nvl(rs.getString("quot")).trim());
            pktListMap.put("vlu", util.nvl(rs.getString("vlu")).trim());
            pktListMap.put("rap_rte", util.nvl(rs.getString("rap_rte")).trim());
            pktListMap.put("rapdis", util.nvl(rs.getString("dis")).trim());
            pktListMap.put("cert_no", util.nvl(rs.getString("cert_no")).trim());
            throubnk = util.nvl(rs.getString("thru_bank_idn"));
            //    if(typ.equals("DLY")){
            //    pktListMap.put("inc_vlu",util.nvl(rs.getString("inc_vlu")));
            //        req.setAttribute("inc_vlu", "Y");
            //    }


            String getPktPrp =
                " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val " + " from stk_dtl a, mprp b, rep_prp c " +
                " where a.mprp = b.prp and b.prp = c.prp and c.mdl = ? and grp=1 and a.mstk_idn = ? " +
                " order by c.rnk, c.srt ";

            ary = new ArrayList();
            if(pkt_ty.equals("NR"))
            ary.add("PERINV_VW_LOC");
            else
            ary.add("PERINV_BOX_VW"); 
            ary.add(mstkIdn);
         ArrayList outLst1 = db.execSqlLst("performInv", getPktPrp, ary);
         PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
         ResultSet  rs1 = (ResultSet)outLst1.get(1);
            while (rs1.next()) {
                String lPrp = util.nvl(rs1.getString("mprp"));
                if(lPrp.equals("MIX_CLARITY"))
                    lPrp="CLR" ;  
                if(lPrp.equals("MIX_SIZE"))
                    lPrp="SIZE" ;                       
                String lVal = util.nvl(rs1.getString("val")).trim();


                if(cnt.equals("ag")){
                    if(lPrp.equals("BOX_ID")){
                        lPrp="Description";
                        lVal=(String)boxidDsc.get(boxidList.indexOf(lVal));
                    }
                    if(!itemHdr.contains(lPrp))
                    itemHdr.add(lPrp);
                    
                }else{
                if(!itemHdr.contains(lPrp))
                itemHdr.add(lPrp);
                }
                pktListMap.put(lPrp, lVal);
            }
            rs1.close();
            pst1.close();
            pktListMap.put("VNM", util.nvl(rs.getString("vnm")));
            pktList.add(pktListMap);
            udf.setValue("ttlCts", rs.getString("ttl_cts"));
            udf.setValue("ttlQty", rs.getString("ttl_qty"));
            ttl = util.nvl(rs.getString("ttl_vlu")).trim();
            udf.setValue("ttlVlu", ttl);
            udf.setValue("echarge", echarge);
            performaDtl.put("ttlCts", rs.getString("ttl_cts"));
            performaDtl.put("ttlQty", rs.getString("ttl_qty"));
            performaDtl.put("ttlVlu", ttl);
            performaDtl.put("echarge", echarge);
            rtnObj.put("TTLCTS", rs.getString("ttl_cts"));
            rtnObj.put("TTLQTY", rs.getString("ttl_qty"));
            rtnObj.put("TTLVLU", ttl);
            rtnObj.put("ECHARGE", echarge);

           ct++;
        }
        rs.close();
        pst.close();

        if (!typ.equals("BOX") && !typ.equals("CS")) {
            String throubnkSql = "select mprp, txt from nme_dtl where nme_dtl_idn=?";
            ary = new ArrayList();
            ary.add(throubnk);
          ArrayList outLst1 = db.execSqlLst("Throug bank", throubnkSql, ary);
          PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
          ResultSet  rs1 = (ResultSet)outLst1.get(1);
            while (rs1.next()) {
                String conSignee = util.nvl(rs1.getString("txt"), "NA");
                req.setAttribute("consignee", conSignee);
            }
            rs1.close();
            pst1.close();
        }

        if (ttl != null && !ttl.equals("")) {
            String Gtotal = "0";
            if (!echarge.equals(""))
                Gtotal = String.valueOf(Double.parseDouble(ttl) + Double.parseDouble(echarge));
            else
                Gtotal = ttl;
            params = new ArrayList();
            if (!idnLst.equals("")) {
                String refTyp="SAL";
                if(typ.equals("DLV"))
                refTyp="DLV";
                String imcQ =
                    "Select A.Typ typ,A.dsc dsc,Sum(B.Charges) charges,a.app_typ,a.inv , b.rmk, A.srt From Charges_Typ A,Trns_Charges B\n" +
                    "  where a.idn=b.charges_idn and a.stt='A' and nvl(b.flg,'NA') not in('Y') and  ref_typ= ?  and ref_idn in " + idnLst +
                    "  GROUP BY A.Typ, A.Dsc, A.App_Typ, A.Inv, B.Rmk, A.srt order by A.srt";
                params.add(refTyp);
              ArrayList outLst1 = db.execSqlLst("Charges", imcQ, params);
              PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
              ResultSet  rs1 = (ResultSet)outLst1.get(1);
                ArrayList chargeLst = new ArrayList();
                int i=1;
                while (rs1.next()) {
                    
                    HashMap charData = new HashMap();
                    String charge = util.nvl(rs1.getString("charges"));
                    String app_typ = util.nvl(rs1.getString("app_typ"));
                    String inv = util.nvl(rs1.getString("inv"));
                    String chargetyp = util.nvl(rs1.getString("typ"));
                    if (inv.equals("Y") && (applycharge.indexOf(chargetyp) == -1 || applycharge.equals(""))) {
                        charData.put("DSC", util.nvl(rs1.getString("dsc")));
                        charData.put("RMK", util.nvl(rs1.getString("rmk")));
                        charData.put("CHARGE", charge);
                        rtnObj.put("DSC_"+i, util.nvl(rs1.getString("dsc")));
                        rtnObj.put("RMK_"+i, util.nvl(rs1.getString("rmk")));
                        rtnObj.put("CHARGE_"+i, charge);                     
                        chargeLst.add(charData);
                        i++;
                    }
                    if (app_typ.equals("TTL") && (applycharge.indexOf(chargetyp) == -1 || applycharge.equals("")))
                        Gtotal =
                                String.valueOf(util.Round(Double.parseDouble(Gtotal) + Double.parseDouble(charge), 2));
                }
                rs1.close();
                pst1.close();
                req.setAttribute("chargeLst", chargeLst);
            }
            if(cnt.equals("ri")){
                if((Double.parseDouble(Gtotal)-(double) Math.floor(Double.parseDouble(Gtotal))) <=0.30)
                Gtotal=String.valueOf(Math.round(Math.floor(Double.parseDouble(Gtotal))));
                else
                Gtotal=String.valueOf(Math.round(Math.ceil(Double.parseDouble(Gtotal))));                    
            }
            udf.setValue("grandttlVlu", Gtotal);
            performaDtl.put("grandttlVlu", Gtotal);
            rtnObj.put("GRANDTTLVLU", Gtotal);
            String words = util.convertNumToAlp(Gtotal);
            udf.setValue("inwords", words);
            performaDtl.put("inwords", words);
            rtnObj.put("INWORDS", words);
        }
        udf.setValue("courierS", courier);
        udf.setValue("invno", invno);
        performaDtl.put("courierS", courier);
        performaDtl.put("invno", invno);
        performaDtl.put("byrIdn", byrIdn);
        performaDtl.put("location", location);
            performaDtl.put("datePrtVal", datePrtVal);
            rtnObj.put("COURIERS", courier);
            rtnObj.put("INVNO", invno);
            rtnObj.put("BYRIDN", byrIdn);
            rtnObj.put("LOCATION", location);
            pageList=(ArrayList)pageDtl.get("BANK_LOC");
            if(pageList!=null && pageList.size() >0){
            if(!bankIdnLoc.equals("") && !bankIdnLoc.equals("0")){
            String mddr = "select decode(mprp,'comadd1','banklocaddr','comlogo','logoloc',mprp) mprp,txt,byr.get_nm(nme_idn) banknme from nme_dtl where nme_idn=? and mprp in('BANK ACCOUNT NO','Swift Code','comadd1','comlogo')";
            ary = new ArrayList();
            ary.add(bankIdnLoc);
              ArrayList outLst1 = db.execSqlLst("Company Adder", mddr, ary);
              PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
              ResultSet  rs1 = (ResultSet)outLst1.get(1);
            while (rs1.next()) {
                udf.setValue(util.nvl(rs1.getString("mprp")), util.nvl(rs1.getString("txt"), "NA"));
                udf.setValue("banklocnme", util.nvl(rs1.getString("banknme"), "NA"));
                performaDtl.put(util.nvl(rs1.getString("mprp")), util.nvl(rs1.getString("txt"), "NA"));
                rtnObj.put(util.nvl(rs1.getString("mprp").toUpperCase()), util.nvl(rs1.getString("txt"), "NA"));

                performaDtl.put("banklocnme", util.nvl(rs1.getString("banknme"), "NA"));
                rtnObj.put("BANKLOCNME", util.nvl(rs1.getString("banknme"), "NA"));

            }
            rs1.close();
            pst1.close();
            if(performaHdrTxt.equals("Y")){
                performaDtl.put("headertext", "Y");
                udf.setValue("headertext", "Y");
            }else{
                udf.setValue("headerimg", "Y");
            }
                req.setAttribute("performaDtl", performaDtl);
                
            }}else{
            displayDtlsLoc(req, udf, location, performaDtl);
            HashMap performa =(HashMap)req.getAttribute("performaDtl");
            rtnObj.put("AC",util.nvl((String)performa.get("AC")));
            rtnObj.put("BANKNAME",util.nvl((String)performa.get("BANKNAME")));
            rtnObj.put("USAC",util.nvl((String)performa.get("USAC")));
            rtnObj.put("SWIFT",util.nvl((String)performa.get("SWIFT")));
        }
        rtnObj.put("TYP", pktTyp);
        rtnObj.put("PKTDTL", pktList);
          
            if(format.equals("P")){           
            frmNme="perInvHKPDF";
            if(pktList.size()>0){
            String jsonPath = (String)info.getDmbsInfoLst().get("JSJSONPATH");
            String jsonImgPath = (String)info.getDmbsInfoLst().get("JSHDRIMG");
            String REP_URL = (String)info.getDmbsInfoLst().get("REP_URL");
            rtnObj.put("HDRIMG", jsonImgPath);
            Gson gson = new Gson();
            String jsonStr= gson.toJson(rtnObj);
            System.out.print(jsonStr);
//            File jsonFile = new File("C:/Users/shivkumar/Json/perfomaINV.json");
            File jsonFile = new File(jsonPath+"perfomaINV.json");
            jsonFile.createNewFile();
            FileWriter writer = new FileWriter(jsonFile);
            writer.write(jsonStr); 
            writer.flush();
            writer.close(); 
            
            req.setAttribute("rptUrl", REP_URL+"/jasper/reportAction.do?method=viewRPT&DS="+info.getDbTyp()+"&KEY=HKINV&INVIDN="+invno);
           if(sendm.equals("Y"))
            req.setAttribute("mailUrl",info.getReqUrl()+"/marketing/performa.do?method=mailHkInv&idn="+invno+"&location="+location+"&mail="+sendm+"&byrIdn="+byrIdn+"&typ="+typ);
            else
            req.setAttribute("mailUrl","N");
            }else{
                req.setAttribute("msg", "Sorry no result found.");
            }
            }
        req.setAttribute("pktList", pktList);           
        req.setAttribute("typ", pktTyp);
        req.setAttribute("itemHdr", itemHdr);
          String consignt = util.nvl(req.getParameter("consign"));
               if(!consignt.equals(""))
              req.setAttribute("consignee", consignt);
           String invrefno = util.nvl(req.getParameter("invno"));
             if(!invrefno.equals(""))
             req.setAttribute("invrefno", invrefno);
        util.updAccessLog(req,res,"Performa", "Performa perInvhk end");
        return am.findForward(frmNme);
        }
    }
        
            public ActionForward mailHkInv(ActionMapping am, ActionForm af, HttpServletRequest req,
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
        rtnPg=initnormal(req,res,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
           return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,res,"Performa", "Performa mail start");
            HashMap logDetails=new HashMap();
            HtmlMailUtil html = new HtmlMailUtil();
            ArrayList attAttachFilNme = new ArrayList();
            ArrayList attAttachTyp = new ArrayList();
            ArrayList attAttachFile = new ArrayList();
              String typ = util.nvl(req.getParameter("typ"));
              String invno = util.nvl(req.getParameter("idn"));
              String sendm=util.nvl(req.getParameter("mail"));  
              String byrIdn = util.nvl(req.getParameter("byrIdn"));
              String location = util.nvl(req.getParameter("location"));
           //   invno=typ+"_"+invno;
        if(sendm.equals("Y")){
        GenMail mail = new GenMail();
        HashMap dbmsInfo = info.getDmbsInfoLst();
        HashMap mailDtl = util.getMailFMT("PERFORMAHK");
        String bodymsg=util.nvl((String)mailDtl.get("MAILBODY"));
        String mailSub=util.nvl((String)mailDtl.get("SUBJECT"));
        StringBuffer msg = new StringBuffer();
        String hdr = "<html><head>\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\n" +
            "    <title>Performa Invoice</title>\n";
        hdr = hdr + "  <link type=\"text/css\" rel=\"stylesheet\" href=\"" + info.getReqUrl() + "/css/style.css\">\n";
        hdr = hdr + "<style type=\"text/css\">\n";
        if (location.equals("")) {
            hdr = hdr + "\n" +
                    ".heightTd{\n" +
                    "height: 25px;\n" +
                    "padding: 5px;\n" +
                    "}" + ".perInvoice{\n" +
                    "    border: 1px solid Black ;\n" +
                    "    \n" +
                    "}" + ".perInvoice td {\n" +
                    "    border-left: 1px solid Black  ;\n" +
                    "      border-top: 1px solid  Black  ;\n" +
                    "      font-family: Geneva, Arial, Helvetica, sans-serif;\n" +
                    "    font-weight: normal; \n" +
                    "    font-size: 12px; \n" +
                    "    vertical-align: top;\n" +
                    "    \n" +
                    "}";

        } else {
            hdr = hdr + "\n" +
                    ".perInvoiceloc{\n" +
                    "border: 1px solid Black ;\n" +
                    "}\n" +
                    "\n" +
                    ".perInvoiceloc td {\n" +
                    "border-left: 1px solid Black ;\n" +
                    "border-top: 1px solid Black ;\n" +
                    "font-family: Geneva, Arial, Helvetica, sans-serif;\n" +
                    "font-weight: normal;\n" +
                    "font-size: 12px;\n" +
                    "vertical-align: top;\n" +
                    "}\n" +
                    ".perInvoiceloc{\n" +
                    "border: 1px solid Black ;\n" +
                    "}\n" +
                    "            .paddinleftrightloc{\n" +
                    "            padding-left: 10px;\n" +
                    "            padding-right: 10px;\n" +
                    "            }\n " + ".perInvoicelocfont{\n" +
                    "font-size: 11px;\n" +
                    "}";

        }
        hdr = hdr + "\n" +
                ".paddin{\n" +
                "padding: 0px;\n" +
                "}";
        hdr = hdr + "</style>\n" +
                "  </head>";
        msg.append(hdr);
        msg.append("<body>");
        msg.append(bodymsg);
        msg.append("</body>");
        msg.append("</html>");
        System.out.println(msg);
        info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
        info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
        info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
        info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
        mail.setInfo(info);
        mail.init();
        String filePath = (String)dbmsInfo.get("JSFILESAVE");
          String cnt = (String)dbmsInfo.get("CNT");
        String senderID = (String)dbmsInfo.get("SENDERID");
        String senderNm = (String)dbmsInfo.get("SENDERNM");
        mail.setSender(senderID, senderNm);
        //mail.setBCC("shiv@faunatechnologies.com");
        String fileNme="INVOICE_"+invno+".pdf";
        filePath =filePath+"perfomaINV_"+cnt+"_"+invno+".pdf";
         // filePath ="C:/Users/shivkumar/Json/PerformaHk.pdf";

            boolean isExit=false;
            while(!isExit){
            File f = new File(filePath);
            if(f.exists() && !f.isDirectory()) { 
            isExit=true;          
            }
            }
            if(isExit){
            attAttachFilNme.add(fileNme);
            attAttachTyp.add("application/pdf");
            attAttachFile.add(filePath);
            if(attAttachFilNme!=null && attAttachFilNme.size() > 0 ){
                mail.setFileName(attAttachFilNme);
                mail.setAttachmentType(attAttachTyp);
                mail.setAttachments(attAttachFile);
            }
        ArrayList ary = new ArrayList();
        ary.add(byrIdn);
        String sqlQ =
            "select lower(byr.get_eml(nme_idn,'N')) byreml,lower(byr.get_eml(emp_idn,'N')) sxeml from nme_v where nme_idn=? ";
          ArrayList  outLst1 = db.execSqlLst("Email Id", sqlQ, ary);
          PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
          ResultSet  rs = (ResultSet)outLst1.get(1);
        if (rs.next()) {
            String byreml = util.nvl(rs.getString("byreml"));
            String sxeml = util.nvl(rs.getString("sxeml"));
    
            String toEml = util.nvl((String)mailDtl.get("TOEML"));
            if(toEml.indexOf("BYR")!=-1 && !byreml.equals("")){
            mail.setTO(byreml);
            }
            if(toEml.indexOf("SALEXC")!=-1 && !sxeml.equals("")){
            mail.setCC(sxeml);
           }
        }
            rs.close();
            pst1.close();
        System.out.println(msg);
        mail.setSubject("Performa Invoice " + util.getToDteTime());
        String mailMag = msg.toString();
        mail.setMsgText(mailMag);
            logDetails.put("BYRID",byrIdn);
            logDetails.put("RELID","");
            logDetails.put("TYP","PERFORMAHK");
            logDetails.put("IDN","");
            String mailLogIdn=util.mailLogDetails(req,logDetails,"I");  
            logDetails.put("MSGLOGIDN",mailLogIdn);
            logDetails.put("MAILDTL",mail.send(""));
            util.mailLogDetails(req,logDetails,"U");
            util.updAccessLog(req,res,"Performa", "Performa mail end");
           
        }
        }
      return am.findForward("mail");
      }
    }


  public ActionForward perInvBrnch(ActionMapping am, ActionForm af, HttpServletRequest req,
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
      rtnPg=initnormal(req,res,session,util);
      }else{
      rtnPg="connExists";   
      }
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
          util.updAccessLog(req,res,"Performa", "Performa perInvBrnch start");
      SaleDeliveryForm udf = (SaleDeliveryForm)af;
      GenericInterface genericInt = new GenericImpl();
      ArrayList ary = new ArrayList();
      ArrayList params = new ArrayList();
      ArrayList itemHdr = new ArrayList();
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
      String location = util.nvl(req.getParameter("location"));
      String echarge = util.nvl(req.getParameter("echarge"));
      String benefit = util.nvl(req.getParameter("benefit"));
      String salidn = util.nvl(req.getParameter("idn"));
      String courier = util.nvl(req.getParameter("courier"));
      String applycharge = util.nvl(req.getParameter("applycharge"));
      String typ = util.nvl(req.getParameter("typ"));
      String form = util.nvl(req.getParameter("form"));
      String stt = util.nvl(req.getParameter("stt"));
          String isbrc = util.nvl(req.getParameter("BRCLINK"));
      String pktTyp = util.nvl(req.getParameter("pktTyp"));
      String sname = "perInvViewLstHK", mdl = "PERINV_VW_LOC";
      String ttl = null;
      String idnLst = "";
      ArrayList pktList = new ArrayList();
          String queryStr = " distinct nvl(inv_nme_idn,nme_idn) nmeIdn, nvl(inv_addr_idn,addr_idn) addrIdn ,nvl(fnl_trms_idn,nmerel_idn) nmerelIdn , ";

          String buy = util.nvl((String)udf.getValue("buy"));
           if(buy.equals("nme_idn"))
               queryStr = " distinct nvl(nme_idn,inv_nme_idn) nmeIdn, nvl(addr_idn,inv_addr_idn) addrIdn ,nvl(nmerel_idn,fnl_trms_idn) nmerelIdn , ";
           else if(typ.equals("SL"))
               queryStr = " distinct nvl(nme_idn,inv_nme_idn) nmeIdn, nvl(addr_idn,inv_addr_idn) addrIdn ,nvl(nmerel_idn,fnl_trms_idn) nmerelIdn , ";

          
      HashMap performaDtl = new HashMap();
      if (typ.equals(""))
          typ = "SL";
      String sttChk = " = '" + typ + "'";
      if (stt.equals("ALL"))
          sttChk = " <> 'RT'";
      if(pktTyp.equals("MIX")){
          sname = "perInvBoxViewLst";
          mdl = "PERINV_BOX_VW";
      }
      ArrayList perFormInvLst=genericInt.genericPrprVw(req, res, sname, mdl);
      if(perFormInvLst.contains("VNM"))
          itemHdr.add("VNM");    
      if (!form.equals("")) {
          idnLst = util.getVnm(salidn);
          idnLst = "(" + idnLst + ")";
          idnLst = idnLst.replaceAll("\\_", "").trim();
          if (idnLst.indexOf(",") > -1) {
              boolean valid = validateidns(req, res, typ, idnLst);
              if (valid) {
                  req.setAttribute("msg", "Please Verify Idns There is data Differerence");
                  return am.findForward("perInvHK");
              }
          }
          String byrNme =
              "select "+queryStr+" bank_id,courier,nvl(Aadat_Comm,0)+nvl(brk1_comm,0)+nvl(brk2_comm,0)+nvl(brk3_comm,0)+nvl(brk4_comm,0) comm from msal where idn in " +
              idnLst;
          if (typ.equals("DLV")) {
              byrNme =
                      "select "+queryStr+"  bnk_idn bank_id,courier,\n" +
                      "nvl(Aadat_Comm,0)+nvl(brk1_comm,0)+nvl(brk2_comm,0)+nvl(brk3_comm,0)+nvl(brk4_comm,0) comm from mdlv where idn in " +
                      idnLst;
          }
        ArrayList  outLst = db.execSqlLst("byrName", byrNme, new ArrayList());
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet  rs = (ResultSet)outLst.get(1);
          while (rs.next()) {
              byrIdn = util.nvl(rs.getString("nmeIdn"));
              addrIdn = util.nvl(rs.getString("addrIdn"));
              relIdn = util.nvl(rs.getString("nmerelIdn"));
              bankIdn = util.nvl(rs.getString("bank_id"));
              courier = util.nvl(rs.getString("courier"));
              if (courier.equals("0"))
                  courier = "NONE";
              brocommval = util.nvl(rs.getString("comm"));

          }
          rs.close();
          pst.close();

          String mddr = "select addr_idn from maddr where nme_idn=?";
          ary = new ArrayList();
          ary.add(bankIdn);
         outLst = db.execSqlLst("Company Adder", mddr, ary);
         pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
          while (rs.next()) {
              bankaddrIdn = util.nvl(rs.getString("addr_idn"));
          }
          rs.close();
          pst.close();
      }
      if (!brocommval.equals(""))
          brocommval = String.valueOf(Float.parseFloat(brocommval) / 100);
      else
          brocommval = "0";
      if (!stkIdnList.equals("")) {
          stkIdnList = util.getVnm(stkIdnList);
      }

      if (grpIdn.equals("")) {
          String companyQ =
              "select a.nme_idn, a.fnme  from mnme A , nme_dtl b where 1 = 1  " + "and a.nme_idn=b.nme_idn and  b.mprp='PERFORMA' and b.txt='YES' " +
              "and a.vld_dte is null  and typ = 'GROUP'";
       ArrayList outLst = db.execSqlLst("Group Sql", companyQ, new ArrayList());
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
          if (rs.next()) {
              grpIdn = util.nvl(rs.getString("nme_idn"));
          }
          rs.close();
          pst.close();
      }
      String grp = "select mprp, txt from nme_dtl where nme_idn=? and vld_dte is null";
      ary = new ArrayList();
      ary.add(grpIdn);
       ArrayList outLst = db.execSqlLst("Company DtlQ", grp, ary);
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet  rs = (ResultSet)outLst.get(1);
      while (rs.next()) {
          udf.setValue(util.nvl(rs.getString("mprp")), util.nvl(rs.getString("txt"), "NA"));
          performaDtl.put(util.nvl(rs.getString("mprp")), util.nvl(rs.getString("txt"), "NA"));
      }
      rs.close();
      pst.close();
          
      String nmev = "select co_nme from nme_v where nme_idn=? ";
      ary = new ArrayList();
      ary.add(byrIdn);
        outLst = db.execSqlLst("addr", nmev, ary);
        pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);
      if (rs.next()) {
          udf.setValue("byrnme", util.nvl(rs.getString("co_nme")));
          performaDtl.put("byrnme", util.nvl(rs.getString("co_nme")));
      }
      rs.close();
      pst.close();

      String addrSql =
          "select a.addr_idn , unt_num, byr.get_nm(nme_idn) byr, bldg ||''|| street bldg , landmark ||''|| area landMk , b.city_nm||' '|| c.country_nm||' '|| a.zip addr " +
          " from maddr a, mcity b, mcountry c where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.addr_idn = ? order by a.dflt_yn desc ";
      ary = new ArrayList();
      ary.add(addrIdn);
        outLst = db.execSqlLst("addr", addrSql, ary);
        pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);
      if (rs.next()) {
          String bldg=util.nvl((String)rs.getString("bldg"));
          udf.setValue("unit_no", rs.getString("unt_num"));
          udf.setValue("bldg", rs.getString("bldg"));
          udf.setValue("landMk", rs.getString("landMk"));
          if(!bldg.equals("")){
              udf.setValue("addr", rs.getString("addr"));
              performaDtl.put("addr", rs.getString("addr"));
          }
          performaDtl.put("unit_no", rs.getString("unt_num"));
          performaDtl.put("bldg", rs.getString("bldg"));
          performaDtl.put("landMk", rs.getString("landMk"));
      }

      rs.close();
        pst.close();
      //    String addDtl = "select mprp, txt from nme_dtl where nme_idn = ? and mprp in ('TEL_NO','EMAIL','FAX','PRE-CARRIAGE-BY','VESSEL/FLIGHT.NO','PORT OF DISCHARGE','FINAL DESTINATION') ";
      String addDtl =
          "Select byr,eml,mbl,qbc,fax,finaldest,preby,vesselno,portdis,ifsc,bankaccount,attn from nme_cntct_v where nme_id=? ";
      ary = new ArrayList();
      ary.add(byrIdn);
        outLst = db.execSqlLst("addDtl", addDtl, ary);
        pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);
      while (rs.next()) {
          //    udf.setValue(util.nvl(rs.getString("mprp")), util.nvl(rs.getString("txt"),"NA"));
          udf.setValue("BYTEL_NO", util.nvl(rs.getString("qbc")));
          udf.setValue("BYEMAIL", util.nvl(rs.getString("eml")));
          udf.setValue("BYMOBILE", util.nvl(rs.getString("mbl")));
          udf.setValue("BYFAX", util.nvl(rs.getString("fax")));
          udf.setValue("BYFINALDEST", util.nvl(rs.getString("finaldest")));
          udf.setValue("BYPREBY", util.nvl(rs.getString("preby")));
          udf.setValue("BYVESSELNO", util.nvl(rs.getString("vesselno")));
          udf.setValue("BYPORTDIS", util.nvl(rs.getString("portdis")));
          udf.setValue("BYATTN", util.nvl(rs.getString("attn")));
          performaDtl.put("BYTEL_NO", util.nvl(rs.getString("qbc")));
          performaDtl.put("BYEMAIL", util.nvl(rs.getString("eml")));
          performaDtl.put("BYMOBILE", util.nvl(rs.getString("mbl")));
          performaDtl.put("BYFAX", util.nvl(rs.getString("fax")));
          performaDtl.put("BYFINALDEST", util.nvl(rs.getString("finaldest")));
          performaDtl.put("BYPREBY", util.nvl(rs.getString("preby")));
          performaDtl.put("BYVESSELNO", util.nvl(rs.getString("vesselno")));
          performaDtl.put("BYPORTDIS", util.nvl(rs.getString("portdis")));
          performaDtl.put("BYATTN", util.nvl(rs.getString("attn")));
      }
      rs.close();
      pst.close();
      addDtl = "Select ifsc,bankaccount from nme_cntct_v where nme_id=? ";
      ary = new ArrayList();
      ary.add(bankIdn);
        outLst = db.execSqlLst("addDtl", addDtl, ary);
        pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);
      while (rs.next()) {
          udf.setValue("BYIFSC", util.nvl(rs.getString("ifsc")));
          udf.setValue("BYBANKACCOUNT", util.nvl(rs.getString("bankaccount")));
          performaDtl.put("BYIFSC", util.nvl(rs.getString("ifsc")));
          performaDtl.put("BYBANKACCOUNT", util.nvl(rs.getString("bankaccount")));
      }
      rs.close();
          pst.close();
      String terms = "";
      String termsDtl =
          "select b.nmerel_idn, nvl(c.trm_prt, c.term) trms ,b.del_typ,b.cur  from nmerel b, mtrms c  where b.trms_idn = c.idn and b.nmerel_idn=? ";
      ary = new ArrayList();
      ary.add(relIdn);
        outLst = db.execSqlLst("termsDtl", termsDtl, ary);
        pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);
      if (rs.next()) {
          terms = util.nvl(rs.getString("trms"));
          // udf.setValue("terms", util.nvl(rs.getString("dtls")));
          req.setAttribute("consignee", util.nvl(rs.getString("del_typ")));
          udf.setValue("termsSign", util.nvl(rs.getString("cur")));
          udf.setValue("terms", terms);
          performaDtl.put("termsSign", util.nvl(rs.getString("cur")));
          performaDtl.put("terms", terms);
      }
      //    String consignee = "select del_typ,cur from nmerel where nmerel_idn=? ";
      //    ary = new ArrayList();
      //    ary.add(relIdn);
      //    rs = db.execSql("consignee", consignee, ary);
      //    if(rs.next()){
      //    // udf.setValue("terms", util.nvl(rs.getString("dtls")));
      //    udf.setValue("consignee", util.nvl(rs.getString("del_typ")));
      //    udf.setValue("termsSign",util.nvl(rs.getString("cur")));
      //    }
      rs.close();
        pst.close();
          if(!cnt.equals("kj")){
      String bankDtl =
          "select a.addr_idn , d.rmk , b.city_nm , d.fnme byrNme, bldg ||''|| street bldg , landmark ||''|| area landMk , b.city_nm||' '|| c.country_nm||' '|| a.zip addr  " +
          " from maddr a, mcity b, mcountry c , mnme d where a.city_idn = b.city_idn and b.country_idn = c.country_idn and a.nme_idn = d.nme_idn " +
          " and a.nme_idn = ? and a.addr_idn=? order by a.dflt_yn desc ";
      ary = new ArrayList();
      ary.add(bankIdn);
      ary.add(bankaddrIdn);
            outLst = db.execSqlLst("addr", bankDtl, ary);
            pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
      if (rs.next()) {
          udf.setValue("bankBldg", rs.getString("bldg"));
          udf.setValue("bankLand", rs.getString("landMk"));
          udf.setValue("bankCity", rs.getString("addr"));
          udf.setValue("bankNme", rs.getString("byrNme"));
          udf.setValue("rmk", rs.getString("rmk"));
          udf.setValue("cityNme", rs.getString("city_nm"));
          performaDtl.put("bankBldg", rs.getString("bldg"));
          performaDtl.put("bankLand", rs.getString("landMk"));
          performaDtl.put("bankCity", rs.getString("addr"));
          performaDtl.put("bankNme", rs.getString("byrNme"));
          performaDtl.put("rmk", rs.getString("rmk"));
          performaDtl.put("cityNme", rs.getString("city_nm"));
      }
      rs.close();
      pst.close();
          }
      String throubnk = "";
      String invno = salidn.replaceAll("\\,", "");
      invno = invno.replaceAll(" ", "");
      if (invno.length() > 10) {
          invno = invno.substring(0, 10);
      }
      if(invno.equals("")){
      invno=bankIdn;
      }
      invno = typ + "_" + invno;
      String conQ = "";
      if(!byrIdn.equals("") && !byrIdn.equals("0")){
              conQ = conQ +" and ms.nme_idn in ("+byrIdn+") ";
      }
      if (!stkIdnList.equals("")) {
          conQ = conQ +" and a.mstk_idn in (" + stkIdnList + ") ";
      }
      if (!form.equals("") && isbrc.equals(""))
          conQ = conQ + " and c.idn in " + idnLst;
     
    
       String  getInvPkts =
                  " select get_inv_pkt_srt(a.mstk_idn) ,b.cert_no,a.idn ,b.pkt_ty,c.thru_bank_idn thru_bank_idn, a.qty ,b.vnm , a.mstk_idn, to_char((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                  brocommval + "),'9999999999990.00') quot, " +
                  " to_char(a.cts,'9990.00') cts ,b.rap_rte, to_char(trunc(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" +
                  brocommval + "), 2),'9999990.00') vlu , " +
                  " decode(b.pkt_ty,'NR',To_Char(trunc((((Nvl(Fnl_Sal, Quot)-Nvl(Fnl_Sal,Quot)*" + brocommval +
                  ")/greatest(b.rap_rte,1))*100)-100,2),'99990.99'),'') dis," +
                  " trunc((Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" + brocommval +
                  ")*trunc(a.cts,2) *((trunc(sysdate) - (trunc(a.dte) + nvl(d.del_dys, 5)))*12/100/365),2) inc_vlu ," +
                  " to_char(sum(trunc(a.cts,2)) over(partition by 'IS'),'99990.99') ttl_cts , " +
                  " to_char(sum(trunc(a.cts,2)*(Nvl(Fnl_Sal, Quot)-nvl(fnl_sal,Quot)*" + brocommval +
                  ")) over(partition by 'IS') ,'999999990.00') ttl_vlu , sum(a.qty) over(partition by 'IS') ttl_qty " +
                  "  from  brc_dlv_dtl a, mstk b , brc_mdlv c,msal ms,  nmerel d where a.mstk_idn = b.idn and c.idn = a.idn and  a.stt  not in  ('DLV','RT','AV','IS','CL') and a.sal_idn=ms.idn " +
                  " and c.typ in ('DLV','SL') and a.stt not in ('CL','RT') and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA')) and c.nmerel_idn = d.nmerel_idn and c.stt='IS' " + conQ +
                  " order by 1 ";

      
     

        outLst = db.execSqlLst("performInv", getInvPkts, params);
        pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);

      while (rs.next()) {
          HashMap pktListMap = new HashMap();
          String mstkIdn = rs.getString("mstk_idn");
          String pkt_ty = rs.getString("pkt_ty");
          pktListMap.put("pkttyp", pkt_ty);
          pktListMap.put("cts", util.nvl(rs.getString("cts")));
          pktListMap.put("qty", util.nvl(rs.getString("qty")));
          pktListMap.put("quot", util.nvl(rs.getString("quot")));
          pktListMap.put("vlu", util.nvl(rs.getString("vlu")));
          pktListMap.put("rap_rte", util.nvl(rs.getString("rap_rte")));
          pktListMap.put("rapdis", util.nvl(rs.getString("dis")));
          pktListMap.put("cert_no", util.nvl(rs.getString("cert_no")));
          throubnk = util.nvl(rs.getString("thru_bank_idn"));
          //    if(typ.equals("DLY")){
          //    pktListMap.put("inc_vlu",util.nvl(rs.getString("inc_vlu")));
          //        req.setAttribute("inc_vlu", "Y");
          //    }


          String getPktPrp =
              " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val " + " from stk_dtl a, mprp b, rep_prp c " +
              " where a.mprp = b.prp and b.prp = c.prp and c.mdl = ? and grp=1 and a.mstk_idn = ? " +
              " order by c.rnk, c.srt ";

          ary = new ArrayList();
          if(pkt_ty.equals("NR"))
          ary.add("PERINV_VW_LOC");
          else
          ary.add("PERINV_BOX_VW"); 
          ary.add(mstkIdn);
       ArrayList  outLst1 = db.execSqlLst("performInv", getPktPrp, ary);
       PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
        ResultSet rs1 = (ResultSet)outLst1.get(1);
          while (rs1.next()) {
              String lPrp = util.nvl(rs1.getString("mprp"));
              if(lPrp.equals("MIX_CLARITY"))
                  lPrp="CLR" ;  
              if(lPrp.equals("MIX_SIZE"))
                  lPrp="SIZE" ;                       
              String lVal = util.nvl(rs1.getString("val"));

              pktListMap.put(lPrp, lVal);
              if(!itemHdr.contains(lPrp))
              itemHdr.add(lPrp);
          }
          rs1.close();
          pst1.close();
          pktListMap.put("VNM", util.nvl(rs.getString("vnm")));
          pktList.add(pktListMap);
          udf.setValue("ttlCts", rs.getString("ttl_cts"));
          udf.setValue("ttlQty", rs.getString("ttl_qty"));
          ttl = util.nvl(rs.getString("ttl_vlu")).trim();
          udf.setValue("ttlVlu", ttl);
          udf.setValue("echarge", echarge);
          udf.setValue("benefit", benefit);
          performaDtl.put("ttlCts", rs.getString("ttl_cts"));
          performaDtl.put("ttlQty", rs.getString("ttl_qty"));
          performaDtl.put("ttlVlu", ttl);
          performaDtl.put("echarge", echarge);
          performaDtl.put("benefit", benefit);


      }
      rs.close();
      pst.close();

      if (!typ.equals("BOX") && !typ.equals("CS")) {
          String throubnkSql = "select mprp, txt from nme_dtl where nme_dtl_idn=?";
          ary = new ArrayList();
          ary.add(throubnk);
        outLst = db.execSqlLst("Throug bank", throubnkSql, ary);
        pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);
          while (rs.next()) {
              String conSignee = util.nvl(rs.getString("txt"), "NA");
              req.setAttribute("consignee", conSignee);
          }
          rs.close();
          pst.close();
      }

      if (ttl != null && !ttl.equals("")) {
          String Gtotal = "0";
          if (!echarge.equals(""))
              Gtotal = String.valueOf(Double.parseDouble(ttl) + Double.parseDouble(echarge));
          else
              Gtotal = ttl;
          if (!benefit.equals(""))
              Gtotal = String.valueOf(Double.parseDouble(Gtotal) - Double.parseDouble(benefit));
          else
              Gtotal = Gtotal;
          params = new ArrayList();
          if (!idnLst.equals("")) {
              String refTyp="SAL";
              if(typ.equals("DLV"))
              refTyp="DLV";
              String imcQ =
                  "Select A.Typ typ,A.dsc dsc,Sum(B.Charges) charges,a.app_typ,a.inv , b.rmk, A.srt From Charges_Typ A,Trns_Charges B\n" +
                  "  where a.idn=b.charges_idn and a.stt='A' and nvl(b.flg,'NA') not in('Y') and  ref_typ= ?  and ref_idn in " + idnLst +
                  "  GROUP BY A.Typ, A.Dsc, A.App_Typ, A.Inv, B.Rmk, A.srt order by A.srt";
              params.add(refTyp);
            outLst = db.execSqlLst("Charges", imcQ, params);
            pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
              ArrayList chargeLst = new ArrayList();
              while (rs.next()) {
                  HashMap charData = new HashMap();
                  String charge = util.nvl(rs.getString("charges"));
                  String app_typ = util.nvl(rs.getString("app_typ"));
                  String inv = util.nvl(rs.getString("inv"));
                  String chargetyp = util.nvl(rs.getString("typ"));
                  if (inv.equals("Y") && (applycharge.indexOf(chargetyp) == -1 || applycharge.equals(""))) {
                      charData.put("DSC", util.nvl(rs.getString("dsc")));
                      charData.put("RMK", util.nvl(rs.getString("rmk")));
                      charData.put("CHARGE", charge);
                      chargeLst.add(charData);
                  }
                  if (app_typ.equals("TTL") && (applycharge.indexOf(chargetyp) == -1 || applycharge.equals("")))
                      Gtotal =
                              String.valueOf(util.Round(Double.parseDouble(Gtotal) + Double.parseDouble(charge), 2));
              }
              rs.close();
              pst.close();
              req.setAttribute("chargeLst", chargeLst);
          }
          udf.setValue("grandttlVlu", Gtotal);
          performaDtl.put("grandttlVlu", Gtotal);
          String words = util.convertNumToAlp(Gtotal);
          udf.setValue("inwords", words);
          performaDtl.put("inwords", words);
      }
      udf.setValue("courierS", courier);
      udf.setValue("invno", invno);
      performaDtl.put("courierS", courier);
      performaDtl.put("invno", invno);
      performaDtl.put("byrIdn", byrIdn);
      performaDtl.put("location", location);
      displayDtlsLoc(req, udf, location, performaDtl);
      req.setAttribute("pktList", pktList);
      req.setAttribute("typ", pktTyp);
      req.setAttribute("itemHdr", itemHdr);
      util.updAccessLog(req,res,"Performa", "Performa perInvBrnch end");
      return am.findForward("perInvHK");
      }
  }

    public boolean validateidns(HttpServletRequest req, HttpServletResponse res, String typ,
                                String idnLst) throws Exception {
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
        boolean valid = false;
        String validQ =
            "Select Inv_Addr_Idn,nvl(fnl_trms_idn, Nmerel_Idn),Bank_Id, Courier,Co_Idn From Msal Where Idn In " + idnLst + " And Inv_Addr_Idn Is Not Null And Nmerel_Idn Is Not Null " +
            " and Bank_Id is not null and Courier is not null and co_idn is not null having count(*)=1 Group By Inv_Addr_Idn,nvl(fnl_trms_idn, Nmerel_Idn),Bank_Id,Courier,co_idn";
        if (typ.equals("DLV")) {
            validQ =
                    "Select Inv_Addr_Idn,nvl(fnl_trms_idn, Nmerel_Idn),bnk_idn, Courier,Co_Idn From Mdlv Where Idn In " + idnLst + " And Inv_Addr_Idn Is Not Null And Nmerel_Idn Is Not Null " +
                    " and bnk_idn is not null and Courier is not null and co_idn is not null having count(*)=1 Group By Inv_Addr_Idn,nvl(fnl_trms_idn, Nmerel_Idn),bnk_idn,Courier,co_idn";
        }
      ArrayList  outLst = db.execSqlLst("valid", validQ, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            valid = true;
        }
        rs.close();
        pst.close();
        return valid;
    }

    public ActionForward mail(ActionMapping am, ActionForm af, HttpServletRequest req,
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
        rtnPg=initnormal(req,res,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
            util.updAccessLog(req,res,"Performa", "Performa mail start");
        HashMap logDetails=new HashMap();
        HtmlMailUtil html = new HtmlMailUtil();
        StringBuffer bodymsg = (StringBuffer)session.getAttribute("mailmsg");
        String byrIdn = util.nvl((String)req.getParameter("byrIdn"));
        String location = util.nvl((String)req.getParameter("location"));
        GenMail mail = new GenMail();
        HashMap dbmsInfo = info.getDmbsInfoLst();
        StringBuffer msg = new StringBuffer();
        String hdr = "<html><head>\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\n" +
            "    <title>Performa Invoice</title>\n";
        hdr = hdr + "  <link type=\"text/css\" rel=\"stylesheet\" href=\"" + info.getReqUrl() + "/css/style.css\">\n";
        hdr = hdr + "<style type=\"text/css\">\n";
        if (location.equals("")) {
            hdr = hdr + "\n" +
                    ".heightTd{\n" +
                    "height: 25px;\n" +
                    "padding: 5px;\n" +
                    "}" + ".perInvoice{\n" +
                    "    border: 1px solid Black ;\n" +
                    "    \n" +
                    "}" + ".perInvoice td {\n" +
                    "    border-left: 1px solid Black  ;\n" +
                    "      border-top: 1px solid  Black  ;\n" +
                    "      font-family: Geneva, Arial, Helvetica, sans-serif;\n" +
                    "    font-weight: normal; \n" +
                    "    font-size: 12px; \n" +
                    "    vertical-align: top;\n" +
                    "    \n" +
                    "}";

        } else {
            hdr = hdr + "\n" +
                    ".perInvoiceloc{\n" +
                    "border: 1px solid Black ;\n" +
                    "}\n" +
                    "\n" +
                    ".perInvoiceloc td {\n" +
                    "border-left: 1px solid Black ;\n" +
                    "border-top: 1px solid Black ;\n" +
                    "font-family: Geneva, Arial, Helvetica, sans-serif;\n" +
                    "font-weight: normal;\n" +
                    "font-size: 12px;\n" +
                    "vertical-align: top;\n" +
                    "}\n" +
                    ".perInvoiceloc{\n" +
                    "border: 1px solid Black ;\n" +
                    "}\n" +
                    "            .paddinleftrightloc{\n" +
                    "            padding-left: 10px;\n" +
                    "            padding-right: 10px;\n" +
                    "            }\n " + ".perInvoicelocfont{\n" +
                    "font-size: 11px;\n" +
                    "}";

        }
        hdr = hdr + "\n" +
                ".paddin{\n" +
                "padding: 0px;\n" +
                "}";
        hdr = hdr + "</style>\n" +
                "  </head>";
        msg.append(hdr);
        msg.append("<body>");
        msg.append(bodymsg);
        msg.append("</body>");
        msg.append("</html>");
        System.out.println(msg);
        info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
        info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
        info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
        info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
        mail.setInfo(info);
        mail.init();
        String senderID = (String)dbmsInfo.get("SENDERID");
        String senderNm = (String)dbmsInfo.get("SENDERNM");
        mail.setSender(senderID, senderNm);
     //   mail.setBCC("mayur.boob@faunatechnologies.com");
        ArrayList ary = new ArrayList();
        ary.add(byrIdn);
        String sqlQ =
            "select lower(byr.get_eml(nme_idn,'N')) byreml,lower(byr.get_eml(emp_idn,'N')) sxeml from nme_v where nme_idn=? ";
          ArrayList  outLst = db.execSqlLst("Email Id", sqlQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        if (rs.next()) {
            String byreml = util.nvl(rs.getString("byreml"));
            String sxeml = util.nvl(rs.getString("sxeml"));
            if (byreml.length() > 0)
                mail.setTO(byreml);
            if (sxeml.length() > 0)
                mail.setCC(sxeml);
        }
            rs.close();
            pst.close();
        System.out.println(msg);
        mail.setSubject("Performa Invoice " + util.getToDteTime());
        String mailMag = msg.toString();
        mail.setMsgText(mailMag);
            logDetails.put("BYRID",byrIdn);
            logDetails.put("RELID","");
            logDetails.put("TYP","PERFORMA");
            logDetails.put("IDN","");
            String mailLogIdn=util.mailLogDetails(req,logDetails,"I");  
            logDetails.put("MSGLOGIDN",mailLogIdn);
            logDetails.put("MAILDTL",mail.send(""));
            util.mailLogDetails(req,logDetails,"U");
            util.updAccessLog(req,res,"Performa", "Performa mail end");
        return am.findForward("mail");
        }
    }

    public void displayDtlsLoc(HttpServletRequest req, SaleDeliveryForm udf, String loc, HashMap performaDtl) {
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
        if(loc.equals(""))
            loc="HK";
        try {
            String gtView =
                "select chr_fr,dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and " + " b.mdl = 'JFLEX' and b.nme_rule = 'LOC" +
                loc + "' and a.til_dte is null order by a.srt_fr ";
          ArrayList  outLst = db.execSqlLst("gtView", gtView, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                udf.setValue(util.nvl(rs.getString("dsc")), util.nvl(rs.getString("chr_fr"), "NA"));
                performaDtl.put(util.nvl(rs.getString("dsc")), util.nvl(rs.getString("chr_fr"), "NA"));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        req.setAttribute("performaDtl", performaDtl);
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
                util.updAccessLog(req,res,"Performa", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Performa", "init");
            }
            }
            return rtnPg;
            }   
    
    public String initnormal(HttpServletRequest req , HttpServletResponse res,HttpSession session,DBUtil util) {
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
                util.updAccessLog(req,res,"Performa", "init");
            }
            }
            return rtnPg;
            }
    
}


//~ Formatted by Jindent --- http://www.jindent.com
