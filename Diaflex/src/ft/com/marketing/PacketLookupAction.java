package ft.com.marketing;



import ft.com.DBMgr;


import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.ExcelUtilObj;
import ft.com.GenMail;
import ft.com.GtMgr;
import ft.com.ImageChecker;
import ft.com.InfoMgr;
import ft.com.JspUtil;

import java.io.*;
import java.net.*;

import ft.com.dao.RepPrp;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import ft.com.lab.LabResultImpl;

import java.io.IOException;

import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.ArrayList;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PacketLookupAction extends DispatchAction{

    final static int size=1024;
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
          util.getOpenCursorConnection(db,util,info);
        util.updAccessLog(req,res,"Packet Lookup", "Packet Lookup Load");
        PacketLookupForm form = (PacketLookupForm)af;
          GenericInterface genericInt = new GenericImpl();
        ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_PKTLUPGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_PKTLUPGNCSrch"); 
        form.setValue("srchRef", "vnm");
        info.setGncPrpLst(assortSrchList);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("PACKET_LOOKUP");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("PACKET_LOOKUP");
        allPageDtl.put("PACKET_LOOKUP",pageDtl);
        }
        info.setPageDetails(allPageDtl);
//        ArrayList rfiddevice = ((ArrayList)info.getRfiddevice() == null)?new ArrayList():(ArrayList)info.getRfiddevice();
//        if(rfiddevice.size() == 0) {
//        util.rfidDevice();    
//        }
        util.updAccessLog(req,res,"Packet Lookup", "Packet Lookup Load done");
          finalizeObject(db,util);

        return am.findForward("view");
      }
    }
   
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Packet Lookup", "Packet Lookup fetch");
        PacketLookupForm form = (PacketLookupForm)af;
        String imageChk = util.nvl((String)form.getValue("imageChk"));
        String prp = util.nvl(req.getParameter("prp"));
        String consignmentRtnExcel = util.nvl((String)form.getValue("consignmentRtnExcel"));
        
        
        String vnm = util.nvl(form.getVnm());
                
        String srchTyp =util.nvl((String)form.getValue("srchRef"));
        
        if(vnm.equals(""))
            vnm = util.nvl(req.getParameter("vnm"));
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_PKTLUPGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_PKTLUPGNCSrch"); 
            info.setGncPrpLst(genricSrchLst);
        if(!consignmentRtnExcel.equals(""))
            return consignmentRtnExcel(am, form, req, res, form, srchTyp,vnm);
        HashMap pktCurrent =  packetData(req, res , vnm , form, srchTyp);
        req.setAttribute("pktCurrent", pktCurrent);
            
        if(!imageChk.equals(""))
        new ImageChecker(req).start();
            
        util.updAccessLog(req,res,"Packet Lookup", "Packet Lookup fetch done size : "+pktCurrent.size());
            finalizeObject(db,util);

        return am.findForward("view");
        }
    }
    
    public ActionForward view(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
        System.out.println("in Action");
        String vnm = util.nvl(req.getParameter("vnm"));
        String idn = util.nvl(req.getParameter("idn"));
        String typ = util.nvl(req.getParameter("typ")).toUpperCase();
        req.setAttribute("vnm", vnm);
        util.updAccessLog(req,res,"Packet Lookup", "Packet Lookup "+typ);
        if((idn.length() == 0) && (vnm.length() > 0)) {
            idn = String.valueOf(util.getIdn(vnm));
        }
        if(idn.length() == 0)
            idn = "0";
        ArrayList hdr = new ArrayList();
        ArrayList items = new ArrayList();
        
        String lookUpQ = "";
        if((typ.equals("MEMO")) || (typ.equals("SOLD")) ||(typ.equals("DLV"))|| (typ.equals("LIST")) ) {
            hdr.add("Idn");
            hdr.add("Employee");
            hdr.add("Buyer/Process");
            hdr.add("Iss Date");
            hdr.add("Trns Date");
            hdr.add("Status");
            hdr.add("CREATED BY");
            hdr.add("MODIFIED BY");
            if(typ.equals("LIST"))
            hdr.add("RMK");
            //hdr.add("Typ");
            
            if(typ.equals("MEMO")) {
                lookUpQ = " select idn, emp, byr, iss_dte, rtn_dte, dsp_typ , AUD_CREATED_BY , AUD_MODIFIED_BY  " +
                    " from jan_pkt_dtl_v  where mstk_idn = ? and typ  in ('K','I','O','E','H','WH','N','IAP','EAP','WAP','CS','HAP','OAP','BAP','SAP','BID','KAP','BIDAP') "+
                    " order by idn desc";
            }
            
            if(typ.equals("SOLD")) {
                lookUpQ = "select a.idn, byr.get_nm(c.emp_idn) emp,byr.get_nm(c.nme_idn) byr, TO_CHAR (b.dte, 'dd/Mon/rrrr HH24:mi') iss_dte,\n" + 
                "NVL2 (b.dte_rtn, TO_CHAR (b.dte_rtn, 'dd/Mon/rrrr HH24:mi'), ' ') rtn_dte, DECODE (b.stt, 'SL', 'Delivery Pending', 'DLV', 'Delivered', 'Sale Returned') dsp_typ , b.AUD_CREATED_BY , b.AUD_MODIFIED_BY \n" + 
                "from msal a,jansal b,mnme c where a.idn=b.idn and a.nme_idn=c.nme_idn and b.mstk_idn = ?\n" + 
                "order by a.idn desc";
            }
            if(typ.equals("DLV")) {
                lookUpQ = "select idn, emp, byr, iss_dte, rtn_dte, dsp_typ , aud_created_by , aud_modified_by \n" + 
                "from dlv_pkt_dtl_v where mstk_idn =?\n" + 
                "union\n" + 
                "select b.idn, b.emp, b.byr, b.iss_dte, b.rtn_dte, b.dsp_typ , b.AUD_CREATED_BY , b.AUD_MODIFIED_BY                     \n" + 
                "from msal a, Sal_pkt_dtl_v b, mstk c\n" + 
                "where a.idn=b.idn and b.mstk_idn=c.idn and c.stt in ('MKSD','BRC_MKSD') \n" + 
                "and a.typ in ('LS') and mstk_idn =?    \n" + 
                "order by 1 desc";
            }
           
            if(typ.equals("LIST")) {
                lookUpQ = " select a.idn, a.emp, a.byr, a.iss_dte, a.rtn_dte, a.dsp_typ , a.AUD_CREATED_BY , a.AUD_MODIFIED_BY,b.rmk\n" + 
                "from jan_pkt_dtl_v a,mjan b where a.idn=b.idn and a.mstk_idn = ? and\n" + 
                "a.typ in ('Z', 'NG', 'PP')\n" + 
                "order by a.idn desc";
            }

            
            
            ArrayList params = new ArrayList();
            params.add(idn);
            if(typ.equals("DLV"))
            params.add(idn);
            ArrayList execLst = db.execSqlLst(" pkt lookup ", lookUpQ, params);
            PreparedStatement pst = (PreparedStatement)execLst.get(0);
            ResultSet rs = (ResultSet)execLst.get(1);
            while(rs.next()){
                ArrayList dtls = new ArrayList();
                dtls.add(util.nvl(rs.getString("idn")));
                dtls.add(util.nvl(rs.getString("emp")));
                dtls.add(util.nvl(rs.getString("byr")));
                dtls.add(util.nvl(rs.getString("iss_dte")));
                dtls.add(util.nvl(rs.getString("rtn_dte")));
                dtls.add(util.nvl(rs.getString("dsp_typ")));
                dtls.add(util.nvl(rs.getString("AUD_CREATED_BY")));
                dtls.add(util.nvl(rs.getString("AUD_MODIFIED_BY")));
                if(typ.equals("LIST"))
                dtls.add(util.nvl(rs.getString("rmk")));
                items.add(dtls);
            }
            rs.close();
            pst.close();
        }
      if(typ.equals("ISS")) {
          String recDte ="";
          ArrayList params = new ArrayList();
          String recDate = "select  to_char(dte,'dd-MON-yyyy') issDte from stk_dtl where mstk_idn=? and mprp =? ORDER by grp asc";
           params.add(idn);
           params.add("RECPT_DT");
           ArrayList execLst = db.execSqlLst(" pkt lookup ", recDate, params);
           PreparedStatement pst = (PreparedStatement)execLst.get(0);
           ResultSet rs = (ResultSet)execLst.get(1);
           if(rs.next()){
             recDte = util.nvl(rs.getString("issDte"));
           }
           rs.close();
           pst.close();
           if(recDte.equals("")){
                params = new ArrayList();
                recDate = "select to_char(iss_dt,'dd-MON-yyyy') issDte from iss_rtn_dtl where iss_stk_idn=? order by iss_id asc";
                params.add(idn);
                execLst = db.execSqlLst(" pkt lookup ", recDate, params);
                pst = (PreparedStatement)execLst.get(0);
                 rs = (ResultSet)execLst.get(1);
                if(rs.next()){
                  recDte = util.nvl(rs.getString("issDte"));
                }
               rs.close();
               pst.close();
           }
           if(recDte.equals(""))
               recDte = "sysdate";
          
           hdr.add("Idn");
           hdr.add("Employee");
           hdr.add("Buyer/Process");
           hdr.add("Iss Date");
           hdr.add("Trns Date");
           hdr.add("In Days");
           hdr.add("P Days");
           hdr.add("Status");
           hdr.add("CREATED BY");
           hdr.add("MODIFIED BY");
           lookUpQ = " select iss_id idn, emp, prc byr,trunc(rtn_dte) - to_date('"+recDte+"', 'dd-MON-yyyy') in_day ,trunc(rtn_dte) - to_date(to_char(iss_dte, 'dd-MON-yyyy'), 'dd-MON-yyyy')  P_days,  iss_dt iss_dte, nvl(rtn_dt,iss_dt) rtn_dte, stt dsp_typ  , AUD_CREATED_BY , AUD_MODIFIED_BY " +
               " from iss_rtn_pkt_v where iss_stk_idn = ? "+
               " order by iss_id desc";
           
            params = new ArrayList();
           params.add(idn);
            execLst = db.execSqlLst(" pkt lookup ", lookUpQ, params);
            pst = (PreparedStatement)execLst.get(0);
           rs = (ResultSet)execLst.get(1);
           while(rs.next()){
               ArrayList dtls = new ArrayList();
               dtls.add(util.nvl(rs.getString("idn")));
               dtls.add(util.nvl(rs.getString("emp")));
               dtls.add(util.nvl(rs.getString("byr")));
               dtls.add(util.nvl(rs.getString("iss_dte")));
               dtls.add(util.nvl(rs.getString("rtn_dte")));
               dtls.add(util.nvl(rs.getString("in_day")));
               dtls.add(util.nvl(rs.getString("P_days")));
               dtls.add(util.nvl(rs.getString("dsp_typ")));
               dtls.add(util.nvl(rs.getString("AUD_CREATED_BY")));
               dtls.add(util.nvl(rs.getString("AUD_MODIFIED_BY")));
               items.add(dtls);
           }
           rs.close();
           pst.close();
                
       }
        if(typ.equals("PRI")) {
            hdr.add("Dte");
            hdr.add("Stt");
            hdr.add("Typ");
            hdr.add("Old Rte");
            hdr.add("New Rte");
            hdr.add("Old Dis");
            hdr.add("New Dis");
            hdr.add("Old Rap");
            hdr.add("New Rap");
            hdr.add("Create By");
            lookUpQ = " select to_number(to_char(a.dte, 'rrrrmmddHH24miss')) dte_srt "+
                     " , to_char(a.dte, 'dd/Mon/rrrr HH24:mi:ss') dte "+
                    " , a.stt , a.flg ,  decode(a.flg, 'CMP', a.old_cmp, nvl(a.old_upr,a.old_cmp)) old_rte "+
                   " , decode(a.flg, 'CMP',a.cmp, nvl(a.upr, a.cmp)) nw_rte "+
                  " , a.old_rap_rte , a.rap_rte , "+
                  "  trunc(((decode(a.flg, 'CMP',a.old_cmp , nvl(a.old_upr, a.old_cmp))/greatest(a.old_rap_rte,1))*100)-100,2) old_dis , "+
                 " trunc(((decode(a.flg, 'CMP',a.cmp, nvl(a.upr, a.cmp))/greatest(a.rap_rte,1))*100)-100,2) new_dis , unm "+
                " from stk_pri_log a where a.mstk_idn = ? " +
                 " order by a.idn desc ";


            ArrayList params = new ArrayList();
            params.add(idn);
            ArrayList execLst = db.execSqlLst(" pkt lookup ", lookUpQ, params);
            PreparedStatement pst = (PreparedStatement)execLst.get(0);
            ResultSet rs = (ResultSet)execLst.get(1);
            while(rs.next()){
                ArrayList dtls = new ArrayList();
                dtls.add(util.nvl(rs.getString("dte")));
                dtls.add(util.nvl(rs.getString("stt")));
                dtls.add(util.nvl(rs.getString("flg")));
                dtls.add(util.nvl(rs.getString("old_rte")));
                dtls.add(util.nvl(rs.getString("nw_rte")));
                dtls.add(util.nvl(rs.getString("old_dis")));
                dtls.add(util.nvl(rs.getString("new_dis")));
                dtls.add(util.nvl(rs.getString("old_rap_rte")));
                dtls.add(util.nvl(rs.getString("rap_rte")));
                dtls.add(util.nvl(rs.getString("unm")));
                items.add(dtls);
            }
            rs.close();
            pst.close();
            
        }
        if(typ.equals("PRP")) {
            hdr.add("Dte");
            hdr.add("Grp");
            hdr.add("Property");
            hdr.add("Value");
            hdr.add("MODIFIED BY");
            
            lookUpQ = " select to_char(upd_date, 'dd/Mon/rrrr HH24:mi') dte, a.grp, a.mprp" +
                ", decode(b.dta_typ, 'C', a.val, 'D', trunc(a.dte), 'N', to_char(a.num), a.txt) val,user_name"+
                " from stk_dtl_log a, mprp b, rep_prp c"+
                " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'STK_DTL_LOG' and c.flg = 'Y'" +
                " and a.mstk_idn = ? "+      
                " order by upd_date desc";
            
            ArrayList params = new ArrayList();
            params.add(idn);
            ArrayList execLst = db.execSqlLst(" pkt lookup ", lookUpQ, params);
            PreparedStatement pst = (PreparedStatement)execLst.get(0);
            ResultSet rs = (ResultSet)execLst.get(1);
            while(rs.next()){
                ArrayList dtls = new ArrayList();
                dtls.add(util.nvl(rs.getString("dte")));
                dtls.add(util.nvl(rs.getString("grp")));
                dtls.add(util.nvl(rs.getString("mprp")));
                dtls.add(util.nvl(rs.getString("val")));
                dtls.add(util.nvl(rs.getString("user_name")));
                items.add(dtls);
            }
            rs.close();
            pst.close();

        }
        if(typ.equals("ISS_RTN")) {
            hdr.add("Idn");
            hdr.add("Dte");
            hdr.add("Process");
            hdr.add("Employee");
            hdr.add("Property");
            hdr.add("Issue");
            hdr.add("Return");
            
            lookUpQ = " select iss_id idn, dte, prc, emp, stt, mprp, iss_val, rtn_val  " +
                " from iss_rtn_mod_prp_v where iss_stk_idn = ? "+
                " order by iss_id desc ";

            ArrayList params = new ArrayList();
            params.add(idn);
            ArrayList execLst = db.execSqlLst(" pkt lookup ", lookUpQ, params);
            PreparedStatement pst = (PreparedStatement)execLst.get(0);
            ResultSet rs = (ResultSet)execLst.get(1);
            while(rs.next()){
                ArrayList dtls = new ArrayList();
                dtls.add(util.nvl(rs.getString("idn")));
                dtls.add(util.nvl(rs.getString("dte")));
                dtls.add(util.nvl(rs.getString("prc")));
                dtls.add(util.nvl(rs.getString("emp")));
                dtls.add(util.nvl(rs.getString("mprp")));
                dtls.add(util.nvl(rs.getString("iss_val")));
                dtls.add(util.nvl(rs.getString("rtn_val")));
                items.add(dtls);
            }
            rs.close();
            pst.close();

        }
        if(typ.equals("BRANCH")){
            hdr.add("BranchIdn");
            hdr.add("DeliveryIdn");
            hdr.add("SaleIdn");
            hdr.add("Branch");
            hdr.add("Buyer");
            hdr.add("Iss_dte");
            hdr.add("Branch_dte");
            hdr.add("Status");
            hdr.add("CREATED BY");
            hdr.add("MODIFIED BY");
            lookUpQ = "select e.idn BranchIdn,e.dlv_idn,e.sal_idn,d.nme branch,b.nme buyer,TO_CHAR(e.dte, 'dd/Mon/rrrr HH24:mi') iss_dte,TO_CHAR(e.dte_dlv, 'dd/Mon/rrrr HH24:mi') Branch_dte,e.stt,e.AUD_CREATED_BY,e.AUD_MODIFIED_BY\n" + 
            "from brc_mdlv a , nme_v b , nme_v d , brc_dlv_dtl e\n" + 
            "where a.nme_idn = b.nme_idn \n" + 
            "and d.nme_idn = a.inv_nme_idn and a.idn = e.idn and mstk_idn=?\n" + 
            "order by 1 desc";

            ArrayList params = new ArrayList();
            params.add(idn);
            ArrayList execLst = db.execSqlLst(" pkt lookup ", lookUpQ, params);
            PreparedStatement pst = (PreparedStatement)execLst.get(0);
            ResultSet rs = (ResultSet)execLst.get(1);
            while(rs.next()){
                ArrayList dtls = new ArrayList();
                dtls.add(util.nvl(rs.getString("BranchIdn")));
                dtls.add(util.nvl(rs.getString("dlv_idn")));
                dtls.add(util.nvl(rs.getString("sal_idn")));
                dtls.add(util.nvl(rs.getString("branch")));
                dtls.add(util.nvl(rs.getString("buyer")));
                dtls.add(util.nvl(rs.getString("iss_dte")));
                dtls.add(util.nvl(rs.getString("Branch_dte")));
                dtls.add(util.nvl(rs.getString("stt")));
                dtls.add(util.nvl(rs.getString("AUD_CREATED_BY")));
                dtls.add(util.nvl(rs.getString("AUD_MODIFIED_BY")));
                items.add(dtls);
            }
            rs.close();
            pst.close();     
        }
            
          if(typ.equals("LOY")) {
              hdr.add("Ref ID");
              hdr.add("Ref Typ");
              hdr.add("Type");
              hdr.add("Trns_Dte");
              hdr.add("Pkt Charge");
              hdr.add("Charge Pct");
              hdr.add("Rte");
              hdr.add("Quot");
              hdr.add("fnl sal");
              
              lookUpQ = " select ref_idn,ref_typ, typ,trns_dte ,  pkt_charges , charges_pct,quot,fnl_sal,rte" +
                  " from PKT_TRNS_CHARGES_V where mstk_idn = ? "+
                  " order by trns_idn desc ";

              ArrayList params = new ArrayList();
              params.add(idn);
              ArrayList execLst = db.execSqlLst(" pkt lookup ", lookUpQ, params);
              PreparedStatement pst = (PreparedStatement)execLst.get(0);
              ResultSet rs = (ResultSet)execLst.get(1);
              while(rs.next()){
                  ArrayList dtls = new ArrayList();
                  dtls.add(util.nvl(rs.getString("ref_idn")));
                  dtls.add(util.nvl(rs.getString("ref_typ")));

                  dtls.add(util.nvl(rs.getString("typ")));
                  dtls.add(util.nvl(rs.getString("trns_dte")));
                  dtls.add(util.nvl(rs.getString("pkt_charges")));
                  dtls.add(util.nvl(rs.getString("charges_pct")));
                  dtls.add(util.nvl(rs.getString("rte")));
                  dtls.add(util.nvl(rs.getString("quot")));
                  dtls.add(util.nvl(rs.getString("fnl_sal")));
                  items.add(dtls);
              }
              rs.close();
              pst.close();

          }
            
            if(typ.equals("PRI_LOG")) {
                hdr.add("Process");
                hdr.add("Employee Name");
                hdr.add("Rate");
                hdr.add("Issue Date");
                
                lookUpQ = " select a.prc , n.nme , p.rtn_num , b.iss_dt from mprc a , iss_rtn b ,nme_v n, iss_rtn_dtl c,iss_rtn_prp p\n" + 
                "where a.idn=b.prc_id and a.grp='PRICING'  and b.iss_id=c.iss_id\n" + 
                "and b.emp_id=n.nme_idn and b.iss_id=p.iss_id and c.iss_stk_idn=p.iss_stk_idn  and p.mprp='RTE'\n" + 
                "and c.iss_stk_idn= ? order by b.iss_id desc ";

                ArrayList params = new ArrayList();
                params.add(idn);
                ArrayList execLst = db.execSqlLst(" pkt lookup ", lookUpQ, params);
                PreparedStatement pst = (PreparedStatement)execLst.get(0);
                ResultSet rs = (ResultSet)execLst.get(1);
                while(rs.next()){
                    ArrayList dtls = new ArrayList();
                    dtls.add(util.nvl(rs.getString("prc")));
                    dtls.add(util.nvl(rs.getString("nme")));

                    dtls.add(util.nvl(rs.getString("rtn_num")));
                    dtls.add(util.nvl(rs.getString("iss_dt")));
                    items.add(dtls);
                }
                rs.close();
                pst.close();

            }
            
          
            if(typ.equals("PRIDTL")) {
                hdr.add("Group");
                hdr.add("Pct");
              
                
                lookUpQ = "select grp, pct from itm_prm_dis_v where mstk_idn = ? order by grp_srt, sub_grp_srt ";

                ArrayList params = new ArrayList();
                params.add(idn);
                ArrayList execLst = db.execSqlLst(" pkt lookup ", lookUpQ, params);
                PreparedStatement pst = (PreparedStatement)execLst.get(0);
                ResultSet rs = (ResultSet)execLst.get(1);
                while(rs.next()){
                    ArrayList dtls = new ArrayList();
                    dtls.add(util.nvl(rs.getString("grp")));
                    dtls.add(util.nvl(rs.getString("pct")));

                    items.add(dtls);
                }
                rs.close();
                pst.close();

            }
            
        req.setAttribute("HDR", hdr);
        req.setAttribute("ITEMS", items);
            finalizeObject(db,util);

        return am.findForward("viewDtl");
        }
    }
   
    public ActionForward pktGrpDtl(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
        lookupList(req);
        ArrayList grpList = new ArrayList();
        util.updAccessLog(req,res,"Packet Lookup", "Packet Lookup pktGrpDtl");
        String stkIdn = util.nvl(req.getParameter("mstkIdn"));
        HashMap stockPrpUpd = new HashMap();
        String stockPrp = "select mstk_idn, lab,b.dta_typ, mprp, a.srt, grp " +
            ", decode(b.dta_typ, 'C', a.val,'N', to_char(a.num)||prop_pkg.get_val(mstk_idn, mprp)" +
            ", 'D', to_char(dte,'dd-mm-rrrr'), nvl(txt,'')) val " + 
        "from stk_dtl a, mprp b , rep_prp c " + 
        "where a.mprp = b.prp and mstk_idn =?  and b.dta_typ in ('C','N','T','D') and c.prp = b.prp and c.flg = 'Y' and mdl in ('LOOKUP_BSC_LST','LOOKUP_ADV_LST','LOOKUP_FIX_LST') " + 
        "order by grp, psrt, mprp,lab ";
        ArrayList ary = new ArrayList();
        ary.add(stkIdn);
          ArrayList  outLst = db.execSqlLst("stockDtl", stockPrp, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
        try {
            while(rs.next()) {
                String grp = util.nvl(rs.getString("grp"));
                if(!grpList.contains(grp))
                    grpList.add(grp);
                
                String mprp =util.nvl(rs.getString("mprp"));
                String val = util.nvl(rs.getString("val"));
              
                String msktIdn = util.nvl(rs.getString("mstk_idn"));
                stockPrpUpd.put(msktIdn+"_"+mprp+"_"+grp, val);
               
            }
            
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        
        req.setAttribute("labDtlList",stockPrpUpd);
        req.setAttribute("grpList", grpList);
            finalizeObject(db,util);

        return am.findForward("grpDtl");
        }
    }
    
    
    public ActionForward consignmentRtnExcel(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res, PacketLookupForm form, String srchTyp, String vnm) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
        HashMap dbinfo = info.getDmbsInfoLst();
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        String client = util.nvl((String)dbinfo.get("CNT"));
            
            vnm = util.getVnm(vnm);
            int ct=0;
            String delQ = " Delete from gt_srch_rslt ";
            ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
            ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
            ct =db.execUpd(" Del Old Pkts ", "Delete from gt_pkt_scan", new ArrayList());
            
               if(!vnm.equals("")){
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
              ArrayList params = new ArrayList();
            //        params.add(vnmSub);

              String insScanPkt = " insert into gt_pkt_scan select * from TABLE(PARSE_TO_TBL("+vnmSub+"))";
                ct = db.execDirUpd(" ins scan", insScanPkt,params);
                System.out.println(insScanPkt);  
            }
            }
              
              
                String srchStr = "";
                  
                if(srchTyp.equals("vnm"))
                    srchStr = " ( b.vnm= a.vnm or b.tfl3 = a.vnm ) ";
                else if(srchTyp.equals("cert_no"))
                    srchStr = " b.cert_no= a.vnm";
                else if(srchTyp.equals("show_id"))
                   srchStr = " b.tfl1= a.vnm";
                
                
                String srchRefQ =
                "    Insert into gt_srch_rslt (stk_idn , vnm  , flg , qty , cts , stt ,  cmp, rap_rte , quot , rap_dis , sk1 ,rmk, pkt_ty, cert_lab, cert_no,diamondImg,jewImg,srayImg,agsImg,mrayImg,ringImg,lightImg,refImg,videos,certImg,video_3d) " +
                " select  b.idn, b.vnm,  'Z' , b.qty , b.cts , b.stt ,nvl(c.fnl_sal,c.quot) , b.rap_rte , nvl(c.fnl_sal,c.quot) , decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(c.fnl_sal,c.quot)/greatest(b.rap_rte,1)*100)-100, 2)) , b.sk1,b.tfl3  "+
                "  ,b.pkt_ty, b.cert_lab, b.cert_no,b.diamondImg,b.jewImg,b.srayImg,b.agsImg,b.mrayImg,b.ringImg,b.lightImg,refImg,videos,certImg,video_3d   from mstk b , jandtl c, gt_pkt_scan a where b.pkt_ty <> 'DEL' and b.idn=c.mstk_idn and c.stt = 'IS' and c.quot is not null and  "+srchStr;
                
                
                // ary.add(vnm);
                
                ct = db.execDirUpd(" Srch Prp ", srchRefQ, new ArrayList()); 
            
            ArrayList ary = new ArrayList();
            ArrayList pktList = new ArrayList();
            HashMap ttlDtl=new HashMap();
            String updProp= "DP_GT_PRP_UPD(pMdl => ?, pTyp => 'PRT')";
            ary = new ArrayList();
            ary.add("CON_RTN_VW");
            ct = db.execCall(" Srch Prp ", updProp, ary);
            
            GenericInterface genericInt = new GenericImpl();
            ArrayList vwPrpLst = genericInt.genericPrprVw(req,res,"CON_RTN_VW","CON_RTN_VW");
            
            String  srchQ =  " select sk1,cert_no,vnm, qty ,to_char(cts,'9999999999990.99') cts, quot "+
                ",Trunc(Quot*Cts,2) vlu ";
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

            String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by byr,sk1 , cts ";
            ary = new ArrayList();
            ary.add("Z");
             ArrayList  outLst = db.execSqlLst("search Result", rsltQ, ary);
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet  rs = (ResultSet)outLst.get(1);
                while(rs.next()) {
                    HashMap pktPrpMap = new HashMap();
                    String vnm1 = util.nvl(rs.getString("vnm"));
                    pktPrpMap.put("vnm",vnm1);
                    pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                    pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                    pktPrpMap.put("rte",util.nvl(rs.getString("quot")));
                    pktPrpMap.put("vlu",util.nvl(rs.getString("vlu")));
                    pktPrpMap.put("cert_no",util.nvl(rs.getString("cert_no")));
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
                pst.close();
            String ttlQ =
                "select to_char(sum(cts),'9999999999990.99') cts,sum(qty) qty,to_char(sum(Trunc(Quot*Cts,2)),'9999999999990.00') vlu from gt_srch_rslt";
             outLst = db.execSqlLst("Totals", ttlQ, new ArrayList());
                 pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
            while (rs.next()) {
                ttlDtl.put("cts", util.nvl(rs.getString("cts")));
                ttlDtl.put("vlu", util.nvl(rs.getString("vlu")));
                ttlDtl.put("qty", util.nvl(rs.getString("qty")));
            }
            rs.close();
            pst.close();
            
            ExcelUtilObj excelUtil = new ExcelUtilObj();
            excelUtil.init(db, util, info,gtMgr);
            OutputStream out = res.getOutputStream();
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "ConsignmentReturn"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
            HSSFWorkbook hwb = excelUtil.getConRtnGenXl(ttlDtl,vwPrpLst, pktList);
            hwb.write(out);
            out.flush();
            out.close();
           }
            return null;
        }
    }
    public HashMap packetData(HttpServletRequest req, HttpServletResponse res, String vnm , PacketLookupForm form, String srchTyp)throws Exception  {
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
        int ct=0;
        GenericInterface genericInt = new GenericImpl();
        ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "PKTLKUP_VW", "PKTLKUP_VW");
        SearchQuery          searchQuery=new SearchQuery();
        HashMap dbinfo = info.getDmbsInfoLst();
        int stepCnt = Integer.parseInt((String)dbinfo.get("STEPCNT"));
        String client = util.nvl((String)dbinfo.get("CNT"));
        String connectBy="";
        HashMap stockList=new HashMap();
        String recalRap = util.nvl((String)form.getValue("recalRap"));
        String delRfid = util.nvl((String)form.getValue("delRfid"));
        String delpkt = util.nvl((String)form.getValue("delpkt"));
        String delpktParam = util.nvl((String)form.getValue("delpktParam"));
        String recut = util.nvl((String)form.getValue("recut"));
        String delQ = " Delete from gt_srch_rslt ";
        ArrayList ary = new ArrayList();
        ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_pkt_scan", new ArrayList());
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("PACKET_LOOKUP");
      if(!vnm.equals(""))
      vnm = util.getVnm(vnm);
     
         if(!vnm.equals("")){
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
        ArrayList params = new ArrayList();
    //        params.add(vnmSub);

        String insScanPkt = " insert into gt_pkt_scan select * from TABLE(PARSE_TO_TBL("+vnmSub+"))";
          ct = db.execDirUpd(" ins scan", insScanPkt,params);
          System.out.println(insScanPkt);
    }
      }
        
        
          String srchStr = "";
            
          if(srchTyp.equals("vnm"))
              srchStr = " ( b.vnm= a.vnm or b.tfl3 = a.vnm ) ";
          else if(srchTyp.equals("cert_no"))
              srchStr = " b.cert_no= a.vnm";
          else if(srchTyp.equals("show_id"))
              srchStr = " b.tfl1= a.vnm";
          
          if(!srchStr.equals("")){
          String srchRefQ =
          "    Insert into gt_srch_rslt (stk_idn , vnm  , flg , qty , cts , stt ,  cmp, rap_rte , quot , rap_dis , sk1 ,rmk, pkt_ty, cert_lab, cert_no,diamondImg,jewImg,srayImg,agsImg,mrayImg,ringImg,lightImg,refImg,videos,certImg,video_3d) " +
          " select  distinct b.idn, b.vnm,  'Z' , qty , cts , stt ,nvl(cmp,upr) , rap_rte , nvl(upr,cmp) , decode(rap_rte ,'1',null,'0',null, trunc((nvl(upr,cmp)/greatest(rap_rte,1)*100)-100, 2)) , sk1,tfl3  "+
          "  ,pkt_ty, b.cert_lab, b.cert_no,diamondImg,jewImg,srayImg,agsImg,mrayImg,ringImg,lightImg,refImg,videos,certImg,video_3d   from mstk b , gt_pkt_scan a where b.pkt_ty <> 'DEL' and  "+srchStr;
          
          
          // ary.add(vnm);
          
          ct = db.execDirUpd(" Srch Prp ", srchRefQ, new ArrayList()); 
          System.out.println(srchRefQ);  
          }
      if(delpkt.equals("") && delpktParam.equals("")){
      if(!connectBy.equals("CP")){
      ary = new ArrayList();
      String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
      ary = new ArrayList();
      ary.add("PKTLKUP_VW");
      ct = db.execCall(" Srch Prp ", pktPrp, ary);
     
        String updProp= "pkgmkt.updProp(pMdl => ?)";
        ary = new ArrayList();
        ary.add("PKTLKUP_VW");
        ct = db.execCall(" Srch Prp ", updProp, ary);
        db.doCommit();
      }
      }else{
          String dpdelpkt= "DP_DEL_PKT(pStkIdn => ?)";
          if(!delpktParam.equals(""))
          dpdelpkt= "DP_DEL_PKT(pStkIdn => ?,pSttDel=>'Y')";
          String sqlQ="select stk_idn,vnm from gt_srch_rslt";
          ResultSet rs = db.execSql("delpkt", sqlQ, new ArrayList());
          while(rs.next()){
              String stk_idn=util.nvl(rs.getString("stk_idn"));
              ary = new ArrayList();
              ary.add(stk_idn); 
              ct = db.execCall("DP_DEL_PKT", dpdelpkt, ary);
              
              util.updAccessLog(req,res,"Packet Lookup DEL", util.nvl(rs.getString("vnm")));
          }
          rs.close();
          ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
          req.setAttribute("RAPMSG", "Packet Deletion Done Sucessfully");
      }
          if(!recalRap.equals("")){
              try{
              ary = new ArrayList();
              String sruQ="select stk_idn from gt_srch_rslt";
              ResultSet rs = db.execSql("sruQ", sruQ, new ArrayList());
              while(rs.next()){
                  ary = new ArrayList();
                  ary.add(util.nvl(rs.getString("stk_idn")));
                  ct = db.execCall("STK_RAP_UPD", "STK_RAP_UPD(pIdn => ?)", ary);
              }
              rs.close();
                  String updateFlg = "update gt_srch_rslt a set (a.rap_rte,a.rap_dis)=\n" + 
                  "(select b.rap_rte, decode(b.rap_rte ,'1',null,'0',null, trunc((nvl(b.upr,b.cmp)/greatest(b.rap_rte,1)*100)-100, 2))\n" + 
                  "from mstk b where a.stk_idn = b.idn) ";
              int cnt = db.execUpd("update Raprte", updateFlg, new ArrayList()); 
                  if(cnt>0){
                      req.setAttribute("RAPMSG", "Rap recalculation Done Sucessfully");
                  }
              } catch (SQLException sqle) {
                  // TODO: Add catch code
                  sqle.printStackTrace();
              } 
                 
          }
          if(!recut.equals("")){
              try{
              ary = new ArrayList();
              String sruQ="select stk_idn from gt_srch_rslt";
              ResultSet rs = db.execSql("sruQ", sruQ, new ArrayList());
              while(rs.next()){
                  ary = new ArrayList();
                  ary.add(util.nvl(rs.getString("stk_idn")));
                  ct = db.execCall("DP_CUT", "DP_CUT(pIdn => ?)", ary);
              }
              rs.close();
              } catch (SQLException sqle) {
                  // TODO: Add catch code
                  sqle.printStackTrace();
              } 
                 
          }
          
            if(!delRfid.equals("")){
                String updateTfl3 = "update mstk a  set a.tfl3=null where exists (select 1 from gt_srch_rslt b where a.idn = b.stk_idn and b.rmk = a.tfl3) ";
                int cnt = db.execUpd("update tfl3", updateTfl3, new ArrayList()); 
                updateTfl3 = "update stk_dtl a  set a.txt=null where\n" + 
                    "exists (select 1 from gt_srch_rslt b where a.mstk_idn = b.stk_idn) and a.mprp='RFIDCD' and a.grp=1";
                cnt = db.execUpd("update tfl3", updateTfl3, new ArrayList()); 
                if(cnt>0){
                req.setAttribute("RAPMSG", "Rfid Deleted Sucessfully");
                }
                updateTfl3 = "update gt_srch_rslt a set a.rmk=(select b.tfl3 from mstk b where a.stk_idn = b.idn)";
                cnt = db.execUpd("update tfl3", updateTfl3, new ArrayList()); 
             }
      
      }else{
          HashMap mprp = info.getMprp();
          HashMap prp = info.getPrp();
          ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_PKTLUPGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_PKTLUPGNCSrch"); 
          info.setGncPrpLst(genricSrchLst);
          HashMap paramsMap = new HashMap();
              for(int i=0;i<genricSrchLst.size();i++){
                  ArrayList prplist =(ArrayList)genricSrchLst.get(i);
                  String lprp = (String)prplist.get(0);
                  String flg= (String)prplist.get(1);
                  String typ = util.nvl((String)mprp.get(lprp+"T"));
                  String prpSrt = lprp ;  
                  if(flg.equals("M")) {
                  
                      ArrayList lprpS = (ArrayList)prp.get(prpSrt + "S");
                      ArrayList lprpV = (ArrayList)prp.get(prpSrt + "V");
                      for(int j=0; j < lprpS.size(); j++) {
                      String lSrt = (String)lprpS.get(j);
                      String lVal = (String)lprpV.get(j);    
                      String reqVal1 = util.nvl((String)form.getValue(lprp + "_" + lVal),"");
                      if(!reqVal1.equals("")){
                      paramsMap.put(lprp + "_" + lVal, reqVal1);
                      }
                         
                      }
                  }else{
                  String fldVal1 = util.nvl((String)form.getValue(lprp+"_1"));
                  String fldVal2 = util.nvl((String)form.getValue(lprp+"_2"));
                  if(typ.equals("T")){
                      fldVal1 = util.nvl((String)form.getValue(lprp+"_1"));
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
          paramsMap.put("stt", "RECPT");
          paramsMap.put("mdl", "PKTLKUP_VW");
          if(connectBy.equals("CP"))
          paramsMap.put("PPOP","N");
          util.genericSrch(paramsMap);
      }
         
        ct = db.execUpd("delete gt", "delete from gt_srch_rslt where stt='PROINV'", new ArrayList());
     //   ct = db.execUpd("delete gt", "delete from gt_srch_rslt where stt like 'SUS%'", new ArrayList());

        if(client.equals("asha")){
            ct = db.execCall("DP_CUSTOM_SRT", "DP_CUSTOM_SRT", new ArrayList());
        }
      req.setAttribute("vnmLst", vnm);
      if(connectBy.equals("CP")){
        String display=util.nvl2((String)((HashMap)((ArrayList)pageDtl.get("DISPLAY")).get(0)).get("dflt_val"),"VAL");
        stockList = genericInt.SearchResult(req,res,display,"PKTLKUP_VW", "'Z'", vwPrpLst);
      }else
        stockList = searchQuery.SearchResult(req,res, "'Z'", vwPrpLst);
      
      HashMap totals = GetTotal(req,res);
      req.setAttribute("totalMap", totals);
      
      if(stockList.size()==1){
          ArrayList pktStkIdnList = (ArrayList)req.getAttribute("pktStkIdnList");
          String stkIdn = (String)pktStkIdnList.get(0);
          HashMap pktDtl = (HashMap)stockList.get(stkIdn);
          String stt = util.nvl((String)pktDtl.get("stt1")).trim();
          if(stt.equals("MKIS")){
          String sttdsc=stt;
          String memoIdn = " No Memo";
          String memoStt = " No Memo";
          String memoDte = " No Memo";
          String memoByr = " No Memo";
          ary = new ArrayList();
          ary.add(stt);
            ArrayList  outLst = db.execSqlLst("sttDsc", "select dsc from df_stk_stt where stt=? ", ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet  rs = (ResultSet)outLst.get(1);
          try {
                if (rs.next()) {
                sttdsc = util.nvl(rs.getString("dsc"));
                }
           rs.close();
          pst.close();
          String memoDsc = "select max(a.idn),decode(a.stt , 'IS' ,'Issue','AP','Approved') stt , b.dte , b.byr from jandtl a , jan_v b \n" + 
          "where a.typ not in ('Z','REP') and a.mstk_idn=? and a.idn = b.idn and a.stt in('IS','AP') and a.idn in(select max(idn) \n" + 
          "from jandtl where typ not in ('Z','REP') and stt in('IS','AP') and mstk_idn=?)\n" + 
          "group by decode(a.stt , 'IS' ,'Issue','AP','Approved'), b.dte , b.byr order by 1 desc ";
          ary = new ArrayList();
          ary.add(stkIdn);
          ary.add(stkIdn);
          outLst = db.execSqlLst("memoDsc", memoDsc, ary);
          pst = (PreparedStatement)outLst.get(0);
           rs = (ResultSet)outLst.get(1);
              if(rs.next()){
                  memoIdn = util.nvl(rs.getString(1));
                  memoStt = util.nvl(rs.getString("stt"));
                  memoDte = util.nvl(rs.getString("dte"));
                  memoByr = util.nvl(rs.getString("byr"));
              }
                 rs.close();
              pst.close();
             } catch (SQLException sqle) {
                 // TODO: Add catch code
                 sqle.printStackTrace();
             } 
          req.setAttribute("sttMsg", "Stone Status :-<b>"+sttdsc+"</b> Last Memo Details :- ID: <B>"+memoIdn+"</b>  Status :<B>"+memoStt+"</b> Buyer Name:<B>"+memoByr+"</B> Date:<B>"+memoDte+"<B>");
          }}
        ArrayList repPrpList = getLookXlGrpFrm(req,res);
        form.setValue("memoXlList",repPrpList);
        finalizeObject(db,util);

     return stockList;

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
       
        String gtTotal ="Select count(*) qty, sum(cts) cts from gt_srch_rslt where flg = ?";
        ArrayList ary = new ArrayList();
        ary.add("Z");
        ArrayList  outLst = db.execSqlLst("getTotal", gtTotal, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet  rs = (ResultSet)outLst.get(1);
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
      }
        return gtTotalMap ;
    }
    public ActionForward createXL(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        PacketLookupForm form = (PacketLookupForm)af;
        util.updAccessLog(req,res,"Packet Lookup", "Create Excel");
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "PacketLookUpExcel"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
        HSSFWorkbook hwb = null;
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        String xlMdl= util.nvl((String)req.getParameter("mdl"));           
        if(xlMdl.equals(""))
           xlMdl="PKT_LKUP_XL";
        int ct = db.execUpd("gtUpdate","update gt_srch_rslt set prp_001 = null", new ArrayList());
         String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
         ArrayList ary = new ArrayList();
         ary.add(xlMdl);
         int ct1 = db.execCall(" Srch Prp ", pktPrp, ary);
        req.setAttribute("PACKETLOOKSTT", "Y");
        hwb = xlUtil.getDataAllInXl("SRCH", req, xlMdl);
        OutputStream out = res.getOutputStream();
        hwb.write(out);
        out.flush();
        out.close();
            finalizeObject(db,util);

        return null;
        }
    }
    
    public ActionForward dtlview(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
    System.out.println("in Action");
    String vnm = util.nvl(req.getParameter("vnm"));
    vnm = util.getVnm(vnm);
    int count=0;
    GenericInterface genericInt = new GenericImpl();
    ArrayList vwPrpLst=genericInt.genericPrprVw(req, res, "PKT_PRP_VW", "PKT_PRP_VW");
    util.updAccessLog(req,res,"Packet Lookup", "Details");
    int ct = db.execUpd("delete gt", "delete from gt_srch_rslt", new ArrayList());

    String srchRefQ = " Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts, cert_no , quot, rap_dis ) " +
    " select a.pkt_ty, a.idn, a.vnm, a.dte, a.stt , 'Z' , a.qty , a.cts, a.cert_no , nvl(upr, cmp) ,  decode(rap_rte ,'1',null, trunc((nvl(upr,cmp)/rap_rte*100)-100, 2)) "+
    " from mstk a where a.vnm in ("+vnm+") ";

    ArrayList ary = new ArrayList();
    
    ct = db.execUpd(" Srch Prp ", srchRefQ, ary);

    String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
    ary = new ArrayList();
    ary.add("PKT_PRP_VW");
    ct = db.execCall(" Srch Prp ", pktPrp, ary);
    String sql = " select stk_idn , pkt_ty, vnm, pkt_dte, stt , qty , cts, cert_no , quot ,  decode(rap_rte, 1, '', decode(least(rap_dis, 0),0, '+'||rap_dis, rap_dis))  rap_dis  ";

    for (int i = 0; i < vwPrpLst.size(); i++) {
    String fld = "prp_";
    int j = i + 1;
    if (j < 10)
    fld += "00" + j;
    else if (j < 100)
    fld += "0" + j;
    else if (j > 100)
    fld += j;

    sql += ", " + fld;

    }


    sql += " from gt_srch_rslt where flg ='Z' order by sk1";

    PacketLookupForm oPacketLookupForm = null;

    ResultSet rs = db.execSql("Packet Lookup Test", sql , new ArrayList());

    ArrayList pktDtlList = new ArrayList();

    try{

    while(rs.next()){
    count++;
    HashMap pktDtl = new HashMap();
    pktDtl.put("Count", String.valueOf(count));
    pktDtl.put("stk_idn", util.nvl(rs.getString("stk_idn")));
    pktDtl.put("pkt_ty", util.nvl(rs.getString("pkt_ty")));
    pktDtl.put("vnm", util.nvl(rs.getString("vnm")));
    pktDtl.put("pkt_dte", util.nvl(rs.getString("pkt_dte")));
    pktDtl.put("stt", util.nvl(rs.getString("stt")));
    pktDtl.put("qty", util.nvl(rs.getString("qty")));
    pktDtl.put("cts", util.nvl(rs.getString("cts")));
    pktDtl.put("cert_no", util.nvl(rs.getString("cert_no")));

    for (int i = 0; i < vwPrpLst.size(); i++) {
    String lprp = (String)vwPrpLst.get(i);
    String fld = "prp_";
    int j = i + 1;
    if (j < 10)
    fld += "00" + j;
    else if (j < 100)
    fld += "0" + j;
    else if (j > 100)
    fld += j;
    if(lprp.equals("RTE"))
        fld="quot";
    if(lprp.equals("RAP_DIS"))
        fld="rap_dis";
    pktDtl.put(lprp, util.nvl(rs.getString(fld)));

    }

    pktDtlList.add(pktDtl);
    }
        rs.close();
    req.setAttribute("packetdtllst", pktDtlList);
    System.out.println("result set in request ");

    }
    catch(Exception e) {
    System.out.println("exception display"+e);
    e.printStackTrace();
    }
    req.setAttribute("vnm", vnm);
            finalizeObject(db,util);

    return am.findForward("detailvw");
        }
    }
    
    public ActionForward rapSync(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
    String vnm = util.nvl(req.getParameter("vnm")).trim();
    ArrayList dtls=new ArrayList();
    util.updAccessLog(req,res,"Packet Lookup", "Rap Sync");
    String rapSyncQ = "select to_char(l.log_tm, 'dd-Mon-rrrr HH24:mi:ss') log_tm, l.act_typ, to_char(s.rapnet_tm, 'dd-Mon-rrrr HH24:mi:ss') upd_tm, decode(s.rapnet, 'C', 'Complete', 'E', 'Error', 'Pending') rapnet, s.rapnet_msg\n" + 
    "from rap_sync_log l, rap_sync_stt s\n" + 
    "where l.idn = s.log_idn\n" + 
    "and l.vnm = '"+vnm+"'\n" + 
    "order by l.idn";
        
        ArrayList params = new ArrayList();
        ArrayList execLst = db.execSqlLst("rapSync", rapSyncQ, params);
        PreparedStatement pst = (PreparedStatement)execLst.get(0);
        ResultSet rs = (ResultSet)execLst.get(1);
        while(rs.next()){
            HashMap dtl = new HashMap();
            dtl.put("log_tm",util.nvl(rs.getString("log_tm")));
            dtl.put("act_typ",util.nvl(rs.getString("act_typ")));
            dtl.put("upd_tm",util.nvl(rs.getString("upd_tm")));
            dtl.put("rapnet",util.nvl(rs.getString("rapnet")));
            dtl.put("rapnet_msg",util.nvl(rs.getString("rapnet_msg")));
            dtls.add(dtl);
        }
        rs.close();
        pst.close();
    req.setAttribute("vnm", vnm);
    req.setAttribute("dtls", dtls);
            finalizeObject(db,util);

    return am.findForward("rapSync");
        }
    }
    public ActionForward PktDetail(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
    GenericInterface genericInt = new GenericImpl();
    genericInt.genericPrprVw(req, res, "pktViewLst", "LOOKUP_PKTDTL_LST");
    String stkIdn = util.nvl(req.getParameter("mstkIdn"));
    ArrayList grpList = new ArrayList();
    HashMap stockPrpUpd = new HashMap();
    util.updAccessLog(req,res,"Packet Lookup", "Packet Details");
    String stockPrp = "select mstk_idn, lab,b.dta_typ, mprp, a.srt, grp " +
    ", decode(b.dta_typ, 'C', a.val,'N', to_char(a.num) " +
    ", 'D', to_char(dte,'dd-mm-rrrr'), nvl(txt,'')) val " +
    "from stk_dtl a, mprp b , rep_prp c " +
    "where a.mprp = b.prp and mstk_idn =? and b.dta_typ in ('C','N','T','D') and c.prp = b.prp and c.flg = 'Y' and mdl='LOOKUP_PKTDTL_LST' " +
    "order by grp, psrt, mprp,lab ";
    ArrayList ary = new ArrayList();
    ary.add(stkIdn);
          ArrayList execLst = db.execSqlLst("stockDtl", stockPrp, ary);
          PreparedStatement pst = (PreparedStatement)execLst.get(0);
          ResultSet rs = (ResultSet)execLst.get(1);
    try {
    while(rs.next()) {
    String grp = util.nvl(rs.getString("grp"));
    if(!grpList.contains(grp))
    grpList.add(grp);

    String mprp =util.nvl(rs.getString("mprp"));
    String val = util.nvl(rs.getString("val"));

    String msktIdn = util.nvl(rs.getString("mstk_idn"));
    stockPrpUpd.put(msktIdn+"_"+mprp+"_"+grp, val);
    }
        rs.close();
        pst.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }
    ArrayList empLst=new ArrayList();
    HashMap dataDtl=new HashMap();
      String rtnQ="select a.iss_id, a.emp, a.prc, b.mprp\n" + 
      ", decode(e.dta_typ, 'C', b.rtn_val,'N', to_char(b.rtn_num), 'D',format_to_date(b.txt), nvl(b.txt,'')) rtn_val\n" + 
      "from iss_rtn_v a, iss_rtn_dtl ird, iss_rtn_prp b , mprp e, rep_prp r, prc_prp_alw p\n" + 
      "where 1 = 1\n" + 
      "and a.iss_id = b.iss_id and a.iss_id = ird.iss_id and ird.iss_stk_idn = b.iss_stk_idn\n" + 
      "and r.prp = b.mprp and r.mdl = 'LOOKUP_PKTDTL_LST' and r.flg = 'Y'\n" + 
      "and e.prp = p.mprp\n" + 
      "and a.prc_id = p.prc_idn and b.mprp = p.mprp and p.flg in ('EDIT', 'COMP')\n" + 
      "and ird.stt <> 'CL'\n" + 
      "and a.prc_grp in ('ASRT', 'FNL ASRT')\n" + 
      "and ird.iss_stk_idn = ?\n" + 
      "Order By A.Iss_Id, B.Mprp";
      execLst = db.execSqlLst("rtnQ", rtnQ, ary);
      pst = (PreparedStatement)execLst.get(0);
     rs = (ResultSet)execLst.get(1);
    while(rs.next()) {
    String emp=util.nvl(rs.getString("emp"));
    String issid=util.nvl(rs.getString("iss_id"));
    String prc=util.nvl(rs.getString("prc"));
    String mprp=util.nvl(rs.getString("mprp"));
    String key=emp+"_"+issid;
    if(!empLst.contains(key)){
    empLst.add(key);
    dataDtl.put(key,issid);
    dataDtl.put(issid,emp);
    dataDtl.put(emp,prc);
    }
    dataDtl.put(stkIdn+"_"+mprp+"_"+key,util.nvl(rs.getString("rtn_val")));
    }
    rs.close();
    pst.close();
    req.setAttribute("labDtlList",stockPrpUpd);
    req.setAttribute("grpList", grpList);
    req.setAttribute("empLst", empLst);
    req.setAttribute("dataDtl", dataDtl);
            finalizeObject(db,util);

    return am.findForward("loadtl");
        }
    }

    public ActionForward PktLabhistory(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Packet Lookup", "PktLabhistory");
            GenericInterface genericInt = new GenericImpl();
            String stkIdn = util.nvl(req.getParameter("idn"));
            String vnm = util.nvl(req.getParameter("vnm")).trim();
            HashMap params=new HashMap();
            LabResultImpl labResultImpl = new  LabResultImpl();
//            int issIdn=0;
//            ResultSet rs =null;
//            ArrayList ary = null;
//            ary = new ArrayList();
//            ary.add(stkIdn);
//            String getIssIdn = "select ISS_RTN_PKG.LST_ISS_ID(?) issIdn from dual";
//            try {
//                rs = db.execSql("issIdn", getIssIdn, ary);
//                if(rs.next()){
//                    issIdn = rs.getInt(1);
//                }
//                rs.close();
//            } catch (Exception e) {
//                // TODO: Add catch code
//                e.printStackTrace();
//            }
//            params.put("iss_id", issIdn);
            params.put("iss_id", 1);
            params.put("mstkIdn", stkIdn);
            HashMap stockHis = labResultImpl.StockHistory(req,res,params);
            req.setAttribute("StockHisList", stockHis);
            req.setAttribute("vnm", vnm);
            genericInt.genericPrprVw(req,res,"pktViewLabPrpLst","LAB_RS");
            util.updAccessLog(req,res,"Packet Lookup", "PktLabhistory  end");
            finalizeObject(db,util);

        return am.findForward("pktLabhistory");
        }
    }
  
    public ActionForward mailExcelMass(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
    form = (PacketLookupForm)form;
    OutputStream outStream = null;
    URLConnection  uCon = null;
    InputStream is = null;
    util.updAccessLog(req,res,"Packet Lookup", "Mail Image");
    String fileLst=util.nvl(req.getParameter("file"));
    ArrayList attAttachFilNme = new ArrayList();
    ArrayList attAttachTyp = new ArrayList();
    ArrayList attAttachFile = new ArrayList();
    if(!fileLst.equals("")){
        String[] subfileLst = fileLst.split(",");
        for (int k = 0; k < subfileLst.length; k++) {
        String filedir=subfileLst[k];
        String typ="";
        String filenm=filedir.substring(filedir.lastIndexOf("/")+1, filedir.length());    
            if(filedir.indexOf("jpeg") > -1) {
                typ="image/jpeg";
            }
            if(filedir.indexOf("jpg") > -1) {
                typ="image/jpg";
            }
            if(filedir.indexOf("JPG") > -1) {
                typ="image/JPG";
            }
            if(filedir.indexOf("png") > -1) {
                typ="image/png";
            }
            if(filedir.indexOf("gif") > -1) {
                typ="image/gif";
            }
            if(filedir.indexOf("mov") > -1) {
                typ="video/quicktime";
            }
            if(filedir.indexOf("wmv") > -1) {
                typ="video/x-ms-wmv";
            }
            if(filedir.indexOf("mp4") > -1) {
                typ="video/mp4";
            }
            if(filedir.indexOf("txt") > -1) {
                typ="text/plain";
            } 
            if(filedir.indexOf("doc") > -1) {
                typ="application/msword";
            } 
            if(filedir.indexOf("pdf") > -1) {
                typ="application/pdf";
            } 
            if(filedir.indexOf("gif") > -1) {
                typ="image/gif";
            }
            if(filedir.indexOf("html") > -1) {
                typ="text/html";
            }
            if(!typ.equals("")){
                try {
                        URL Url;
                        byte[] buf;
                        int ByteRead,ByteWritten=0;
                        String filePath = session.getServletContext().getRealPath("/") + String.valueOf(k)+filenm;
                        Url= new URL(subfileLst[k]);
                        outStream = new BufferedOutputStream(new
                        FileOutputStream(filePath));
                        uCon = Url.openConnection();
                        is = uCon.getInputStream();
                        buf = new byte[size];
                        while ((ByteRead = is.read(buf)) != -1) {
                            outStream.write(buf, 0, ByteRead);
                            ByteWritten += ByteRead;
                        }
                        outStream.flush();
                        System.out.println("Downloaded Successfully.");
                        System.out.println("File name:\""+filePath+ "\"\nNo ofbytes :" + ByteWritten);
                        attAttachFilNme.add(filenm);
                        attAttachTyp.add(typ);
                        attAttachFile.add(filePath);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                    try {
                    is.close();
                    outStream.close();
                    }
                    catch (IOException e) {
                    e.printStackTrace();
                    }}
                
            }
        }
        session.setAttribute("attAttachFilNme", attAttachFilNme);
        session.setAttribute("attAttachTyp", attAttachTyp);
        session.setAttribute("attAttachFile", attAttachFile);
    }
            util.updAccessLog(req,res,"Packet Lookup", "Mail Image end");
            finalizeObject(db,util);

    return am.findForward("mail");
        }
    }
    public void lookupList(HttpServletRequest req){
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
      try {
        ArrayList viewPrp = (ArrayList)session.getAttribute("LOOKUP_BSC_LST");
         if (viewPrp == null) {
             viewPrp = new ArrayList();
   
             
              ArrayList  outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'LOOKUP_BSC_LST' and flg='Y' order by rnk ",
                               new ArrayList());
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet  rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    viewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                pst.close();
                session.setAttribute("LOOKUP_BSC_LST", viewPrp);
            }
           
            viewPrp = (ArrayList)session.getAttribute("LOOKUP_ADV_LST");
            if (viewPrp == null) {
              viewPrp = new ArrayList();
    
               
                ArrayList  outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'LOOKUP_ADV_LST' and flg='Y' order by rnk ",
                                           new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet  rs1 = (ResultSet)outLst.get(1);
                 while (rs1.next()) {

                  viewPrp.add(rs1.getString("prp"));
                }
                  rs1.close();
                  pst.close();
                session.setAttribute("LOOKUP_ADV_LST", viewPrp);
              }
            viewPrp = (ArrayList)session.getAttribute("LOOKUP_FIX_LST");
            if (viewPrp == null) {
               viewPrp = new ArrayList();
             
            ArrayList  outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = 'LOOKUP_FIX_LST' and flg='Y' order by rnk ",
                                           new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet  rs1 = (ResultSet)outLst.get(1);
                  while (rs1.next()) {

                                viewPrp.add(rs1.getString("prp"));
                     }
              rs1.close();
              pst.close();
              session.setAttribute("LOOKUP_FIX_LST", viewPrp);
          }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        finalizeObject(db,util);

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
                util.updAccessLog(req,res,"Packet Lookup", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Packet Lookup", "init");
            }
            }
            return rtnPg;
            }

    
//    public ArrayList PKTGenricSrch(HttpServletRequest req , HttpServletResponse res){
//        init(req,res);
//        ArrayList asViewPrp = (ArrayList)session.getAttribute("PKTLUPGNCSrch");
//        try {
//            if (asViewPrp == null) {
//
//                asViewPrp = new ArrayList();
//           
//                ResultSet rs1 =
//                    db.execSql(" Vw Lst ", "Select prp , flg  from rep_prp where mdl = 'PKTLUP_SRCH'  and flg <> 'N'  order by rnk ",
//                               new ArrayList());
//                while (rs1.next()) {
//
//                    ArrayList asViewdtl=new ArrayList();
//                    asViewdtl.add(rs1.getString("prp"));
//                    asViewdtl.add(rs1.getString("flg"));
//                    asViewPrp.add(asViewdtl);
//                }
//                rs1.close();
//                session.setAttribute("PKTLUPGNCSrch", asViewPrp);
//            }
//
//        } catch (SQLException sqle) {
//            // TODO: Add catch code
//            sqle.printStackTrace();
//        }
//        
//        return asViewPrp;
//    }
    
    
    public ActionForward    multiImgDtl(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          SearchQuery query = new SearchQuery();
          PacketLookupForm form = (PacketLookupForm)af;
         String where=util.nvl(req.getParameter("where"));
          if(where.equals(""))
          where=util.nvl((String)form.getValue("where"));
          form.reset();
          String sub="Show Multiple Types Images";
          String pgDef = "PACKET_LOOKUP";
          HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
          HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);
          ArrayList pageList=new ArrayList();
          HashMap pageDtlMap=new HashMap();
          pageList= ((ArrayList)pageDtl.get("MULTIIMG_SUB") == null)?new ArrayList():(ArrayList)pageDtl.get("MULTIIMG_SUB");
          if(pageList!=null && pageList.size() >0){
          pageDtlMap=(HashMap)pageList.get(0);
          sub=util.nvl((String)pageDtlMap.get("dflt_val"));
          }
          form.setValue("subject", sub);
          if(where.equals("SRCH")){
              form.setValue("to_eml", info.getByrEml());  
              form.setValue("cc_eml", info.getSaleExeEml());
              form.setValue("flg", "M");
          }
          if(where.equals("PKTLOOKUP")){
              form.setValue("flg", "Z");
          }
          form.setValue("where", where);
//          util.imageDtls();
          finalizeObject(db,util);

         return am.findForward("multiImgDtl");   
      }
    }    
    
    public ActionForward multiImgSend(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        PacketLookupForm form = (PacketLookupForm)af;
        SearchQuery query = new SearchQuery();
    String subject=util.nvl((String)form.getValue("subject"),"NA");
    String to_eml=util.nvl((String)form.getValue("to_eml"));
    String cc_eml=util.nvl((String)form.getValue("cc_eml"));
    String bcc_eml=util.nvl((String)form.getValue("bcc_eml"));
    String flg=util.nvl((String)form.getValue("flg"));
    String where=util.nvl((String)form.getValue("where"));
    ArrayList vwPrpLst=new ArrayList();
    if(where.equals("SRCH"))
    vwPrpLst = info.getVwPrpLst();
    if(where.equals("PKTLOOKUP"))
    vwPrpLst= (session.getAttribute("PKTLKUP_VW") == null)?new ArrayList():(ArrayList)session.getAttribute("PKTLKUP_VW");
    HashMap dbmsInfo = info.getDmbsInfoLst();
    String client = (String)dbmsInfo.get("CNT");
    String sh = (String)dbmsInfo.get("SHAPE");
    String colval = (String)dbmsInfo.get("COL");
    String polval = (String)dbmsInfo.get("POL");
    String clrlval = (String)dbmsInfo.get("CLR");
    String symval = (String)dbmsInfo.get("SYM");
    String cutval = (String)dbmsInfo.get("CUT");
    String flval = (String)dbmsInfo.get("FL");
        
    int indexSH = vwPrpLst.indexOf(sh)+1;
    int indexCol = vwPrpLst.indexOf(colval)+1;
    int indexpol = vwPrpLst.indexOf(polval)+1;
    int indexClr = vwPrpLst.indexOf(clrlval)+1;
    int indexSym = vwPrpLst.indexOf(symval)+1;
    int indexCut = vwPrpLst.indexOf(cutval)+1;
    int indexFl = vwPrpLst.indexOf(flval)+1;
        
    String shPrp =  util.prpsrtcolumn("prp",indexSH);
    String colPrp =  util.prpsrtcolumn("prp",indexCol);
    String polPrp =  util.prpsrtcolumn("prp",indexpol);
    String clrPrp =  util.prpsrtcolumn("prp",indexClr);
    String symPrp =  util.prpsrtcolumn("prp",indexSym);
    String cutPrp =  util.prpsrtcolumn("prp",indexCut);
    String flPrp =  util.prpsrtcolumn("prp",indexFl);
     
     String prpVal=" "+shPrp+" sh, "+colPrp+" co, "+polPrp+" po ,"+clrPrp+" clr,"+symPrp+" sym ,"+cutPrp+" cut,"+flPrp+" fl" ; 
        
    boolean send=false;
//    String ByrEml = info.getByrEml();
//    String salExEml = info.getSaleExeEml();
    StringBuffer msg=new StringBuffer();
    ArrayList ary=new ArrayList();
    ArrayList types=new ArrayList();
    ArrayList imgList=new ArrayList();
    HashMap imgDtl=new HashMap();
    HashMap dtls=new HashMap();
    String gtCol="";
    ArrayList imagelistDtl =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ImaageDtls") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_ImaageDtls");
    Enumeration reqNme = req.getParameterNames();
    while(reqNme.hasMoreElements()) {
    String paramNm = (String)reqNme.nextElement();
    if(paramNm.indexOf("cb_img") > -1) {
    gtCol = req.getParameter(paramNm);
    types.add(gtCol);
    }
    }

    String getimgsql="select vnm,cts,cert_lab,cert_no,"+prpVal+",certimg ," +

    "diamondImg,jewImg,srayImg,agsImg,mrayImg,ringImg,lightImg,refImg,videos,certImg,video_3d from GT_SRCH_RSLT where flg=?";
    ary.add(flg);
      ArrayList  outLst = db.execSqlLst("Image Dtl",getimgsql,ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
    while (rs.next()) {
    imgDtl=new HashMap();
    imgDtl.put("PacketNo",util.nvl(rs.getString("vnm")));
    imgDtl.put("carat",util.nvl(rs.getString("cts")));
    imgDtl.put("Shape",util.nvl(rs.getString("sh")));
    imgDtl.put("color",util.nvl(rs.getString("co")));
        imgDtl.put("clr",util.nvl(rs.getString("clr")));
        imgDtl.put("pol",util.nvl(rs.getString("po")));
        imgDtl.put("sym",util.nvl(rs.getString("sym")));
        imgDtl.put("cut",util.nvl(rs.getString("cut")));
        imgDtl.put("fl",util.nvl(rs.getString("fl")));
    imgDtl.put("cert_lab",util.nvl(rs.getString("cert_lab")));
    imgDtl.put("cert_no",util.nvl(rs.getString("cert_no")));

        imgDtl.put("diamondImg",util.nvl(rs.getString("diamondImg"),"N"));
        imgDtl.put("jewImg",util.nvl(rs.getString("jewImg"),"N"));
        imgDtl.put("srayImg",util.nvl(rs.getString("srayImg"),"N"));
        imgDtl.put("agsImg",util.nvl(rs.getString("agsImg"),"N"));
        imgDtl.put("mrayImg",util.nvl(rs.getString("mrayImg"),"N"));
        imgDtl.put("ringImg",util.nvl(rs.getString("ringImg"),"N"));
        imgDtl.put("lightImg",util.nvl(rs.getString("lightImg"),"N"));
        imgDtl.put("refImg",util.nvl(rs.getString("refImg"),"N"));
        imgDtl.put("videos",util.nvl(rs.getString("videos"),"N"));
        imgDtl.put("certImg",util.nvl(rs.getString("certImg"),"N"));
        imgDtl.put("video_3d",util.nvl(rs.getString("video_3d"),"N"));
    imgList.add(imgDtl);

    }
    rs.close();
        pst.close();
    String img="";

    String hdr = "<html><head><title></title>\n"+
    "<style type=\"text/css\">\n"+
    "body{\n" +
    " margin-left: 10px;\n" +
    " margin-top: 10px;\n" +
    " margin-right: 0px;\n" +
    " margin-bottom: 0px;\n" +
    " font-family: Arial, Helvetica, sans-serif;\n" +
    " font-size: 12px;\n" +
    " color: #333333;\n" +
    "}\n" +
    "</style>\n"+
    "</head>";
    msg.append(hdr);
    msg.append("<body>");

    msg.append("<table border=\"1\" >");
    for(int i=0; i<imgList.size();i++){

    msg.append("<tr>");
    msg.append("<th>Packet No.</th>");
    msg.append("<th>Shape</th>");
    msg.append("<th>Carat</th>");
    msg.append("<th>Clarity</th>");
    msg.append("<th>Color</th>");
    msg.append("<th>Cut</th>");
    msg.append("<th>Pol</th>");
    msg.append("<th>Sym</th>");
    msg.append("<th>FL</th>");
    msg.append("<th>Lab</th>");
    msg.append("<th>Cert_No</th>");
    msg.append("</tr>");
    msg.append("<tr>");
    HashMap Dtl=(HashMap)imgList.get(i);
    String vnm=(String)Dtl.get("PacketNo");
    String serPathVid="";
    msg.append("<td>"+Dtl.get("PacketNo")+"</td>");
    msg.append("<td>"+Dtl.get("Shape")+"</td>");
    msg.append("<td>"+Dtl.get("carat")+"</td>");
    msg.append("<td>"+Dtl.get("clr")+"</td>");
    msg.append("<td>"+Dtl.get("color")+"</td>");
    msg.append("<td>"+Dtl.get("cut")+"</td>");
    msg.append("<td>"+Dtl.get("pol")+"</td>");
    msg.append("<td>"+Dtl.get("sym")+"</td>");
    msg.append("<td>"+Dtl.get("fl")+"</td>");
    msg.append("<td>"+Dtl.get("cert_lab")+"</td>");
    msg.append("<td>"+Dtl.get("cert_no")+"</td>");
    msg.append("</tr>");

    msg.append("<tr>");
    msg.append("<td colspan=\"8\"><table><tr>");
    for(int j=0;j<imagelistDtl.size();j++){
    dtls=new HashMap();
    dtls=(HashMap)imagelistDtl.get(j);
    gtCol=util.nvl((String)dtls.get("GTCOL"));
    if(types.contains(gtCol)){
        String val=util.nvl((String)Dtl.get(gtCol));
        String path=util.nvl((String)dtls.get("PATH"));
        String typ=util.nvl((String)dtls.get("TYP"));
        String allowon=util.nvl((String)dtls.get("ALLOWON"));
        if(!val.equals("N")){
        if(typ.equals("V") && (allowon.indexOf("MULTI") > -1)){
        if(val.indexOf(".js") <=-1 || val.indexOf(".mov") > -1){
        path=path+val;
        path=path.replaceAll("SPECIAL", ".mov");
        path=path.replaceAll("imaged", "videos");
        serPathVid=path;
        }else{
        String imgPath = (String)dbmsInfo.get("IMG_PATH");
        String videoFoundUrl = imgPath+"XraySample/video.jpg";
        serPathVid = "";
        if(val.equals("3.js")){
        serPathVid = imgPath+"viewer.html?d="+vnm+"&sm=1&sd=50&sv=0,1,2,3,4&z=1&v=3";
        }else if(val.equals("2.js")) {
        serPathVid = imgPath+"viewer.html?d="+vnm+"&sm=1&sd=50&sv=0,1,2,3,4&z=1&v=2";
        }else if(val.equals("1.js")) {
        serPathVid = imgPath+"viewer.html?d="+vnm+"&sm=1&sd=50&sv=0,1,2,3,4&z=1&v=1";
        }else if(val.equals("0.js")) {
        serPathVid = imgPath+"viewer.html?d="+vnm+"&sm=1&sd=50&sv=0,1,2,3,4&z=1&v=1";
        }else if(val.indexOf("json") > -1) {
            String conQ=vnm;
            if(val.indexOf("/") > -1){
                conQ=val.substring(0,val.indexOf("/"));
            }
        serPathVid = imgPath+"Vision360.html?sd=50&z=1&v=2&sv=0&d="+conQ;
        }else {
        serPathVid = "";
        }
        }
            img="<td valign=\"middle\"><a href="+serPathVid+" target=\"_blank\">PLAY VIDEO</a></td>";
            msg.append(img);
        }else{
        path=path+val;
        img="<td><a href="+path+" target=\"_blank\"><img height=\"100\" width=\"100\" src="+path+" title="+gtCol+"></a></td><td></td>";    
        msg.append(img);
    }
    }
    }
    }
    msg.append("</tr></table></td></tr>");
    }

    msg.append("</table>");
    msg.append("</body>");
    msg.append("</html>");
    GenMail mail = new GenMail();
    info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
    info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
    info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
    info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
    mail.setInfo(info);
    mail.init();
           ArrayList param = new ArrayList();
           String salExcEml ="";  
           String salNm ="";
            if(client.equals("hk") && !to_eml.equals("")){
           String[] emlLst = to_eml.split(",");
           if(emlLst.length<=1){
           param.add(emlLst[0].trim());
           String Qrymid =" select lower(byr.get_nm(emp_idn)) salNm ,  lower(byr.get_eml(emp_idn,'N')) salEml from nme_cntct_v where  eml= ? ";
           outLst = db.execSqlLst("Image Dtl",Qrymid,param);
             pst = (PreparedStatement)outLst.get(0);
              rs = (ResultSet)outLst.get(1);
               if(rs.next()) {
                       salNm = util.nvl(rs.getString("salNm"));
                   salExcEml = util.nvl(rs.getString("salEml"));
                   
            }
                rs.close();
                pst.close();
            }
            }
           String senderID =util.nvl((String)dbmsInfo.get("SENDERID"));
           String senderNm =(String)dbmsInfo.get("SENDERNM");
           if(senderID.equals("NA"))
              senderID=info.getSaleExeEml();   
           if(!salExcEml.equals("") && !salNm.equals("")){
               senderID=salExcEml;
               senderNm=salNm;
           } 
    mail.setReplyTo(senderID);
    mail.setSender(senderID, senderNm);
    mail.setSubject(subject);

    if(!to_eml.equals("")){
        send=true;
        String[] emlLst = to_eml.split(",");
        if(emlLst!=null){
        for(int i=0 ; i <emlLst.length; i++)
        {
        mail.setTO(emlLst[i]);
        }
        }
    }
    if(!cc_eml.equals("")){
            send=true;
            String[] emlLst = cc_eml.split(",");
            if(emlLst!=null){
            for(int i=0 ; i <emlLst.length; i++)
            {
            mail.setCC(emlLst[i]);
            }
            }
    }
    if(!bcc_eml.equals("")){
        send=true;
                String[] emlLst = bcc_eml.split(",");
                if(emlLst!=null){
                for(int i=0 ; i <emlLst.length; i++)
                {
                mail.setBCC(emlLst[i]);
                }
                }
    }
    form.setValue("where", where);
    req.setAttribute("msg", "Mail delivered successfully...");
    String mailMag = msg.toString();
    mail.setMsgText(mailMag);
    if(imgList.size()>0 && send){
    HashMap logDetails=new HashMap();
    logDetails.put("BYRID","0");
    logDetails.put("TYP","MULTIIMGSND");
    logDetails.put("RELID","0");
    String mailLogIdn=util.mailLogDetails(req,logDetails,"I");
    logDetails.put("MSGLOGIDN",mailLogIdn);
    logDetails.put("MAILDTL",mail.send(""));
    util.mailLogDetails(req,logDetails,"U");
    }else
    req.setAttribute("msg", "Mail sending failed...");
        finalizeObject(db,util);

    return multiImgDtl(am, form, req, res);
    }

    }
    public ArrayList getLookXlGrpFrm(HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        String xlGrp = "select distinct mdl from rep_prp where mdl like '%_LKUP_XL' ";
      ArrayList  outLst = db.execSqlLst("xlGrp",xlGrp,new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet  rs = (ResultSet)outLst.get(1);
        ArrayList repPrpLst = new ArrayList();
        RepPrp repPrp = new RepPrp();
        repPrp.setMdl("PKTLKUP_VW");
        repPrpLst.add(repPrp);
        try {
            while (rs.next()) {
              repPrp = new RepPrp();
               
                repPrp.setMdl(rs.getString("mdl").trim());
                
                repPrpLst.add(repPrp);
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        finalizeObject(db,util);
        return repPrpLst;
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
    public PacketLookupAction() {
        super();
    }
}
