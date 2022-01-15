package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.PdfforReport;
import ft.com.assort.AssortIssueImpl;
import ft.com.assort.AssortIssueInterface;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;
import ft.com.generic.GenericImpl;
import ft.com.marketing.PacketPrintForm;

import ft.com.report.CommonForm;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Connection;
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

public class ReCalChargeAction extends DispatchAction {

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
            util.updAccessLog(req,res,"Recalculate Charges", "load");
         ReCalChargeForm udf = (ReCalChargeForm)af;
         udf.resetALL();
         ArrayList chargesLst= ((ArrayList)session.getAttribute("ReCalchargesLst") == null)?new ArrayList():(ArrayList)session.getAttribute("ReCalchargesLst");
         if (chargesLst == null || chargesLst.size()==0) {
         String chargesQ="Select  c.idn,c.typ,c.dsc,c.stg optional,c.flg,c.fctr,c.db_call,c.rmk  \n" + 
         "From Charges_Typ C \n" + 
         "Where  \n" + 
         "c.stt='A' and\n" + 
         "c.app_typ='TTL'\n" + 
         "order by c.srt";
           ArrayList  outLst = db.execSqlLst("chargesQ", chargesQ, new ArrayList());
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet  rs = (ResultSet)outLst.get(1);
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
            session.setAttribute("ReCalchargesLst", chargesLst);
         }
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("RE_CAL_CHARGES");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("RE_CAL_CHARGES");
            allPageDtl.put("RE_CAL_CHARGES",pageDtl);
            }
            info.setPageDetails(allPageDtl);
         return am.findForward("load");
        }
     }
     
    public ActionForward fetchExistsCharges(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Recalculate Charges", "fetchExistsCharges");
         ReCalChargeForm udf = (ReCalChargeForm)af;
         ArrayList params=new ArrayList();    
         boolean  rtnview=false;
         String ref_idn=util.nvl((String)udf.getValue("ref_idn"));
         String ref_typ=util.nvl((String)udf.getValue("ref_typ"));
         udf.resetALL();
         String fetchchQ="Select A.Typ typ,A.dsc dsc,Sum(B.Charges) charges,a.app_typ,a.inv , b.rmk, A.srt,nvl(B.Flg,'N') Flg,a.stg\n" + 
         "From Charges_Typ A,Trns_Charges B\n" + 
         "where a.idn=b.charges_idn and a.stt='A' and  ref_typ= ? and \n" + 
         "ref_idn in (?)\n" + 
         "GROUP BY A.Typ, A.Dsc, A.App_Typ, A.Inv, B.Rmk, A.srt,B.Flg,a.stg order by A.srt";
            params.add(ref_typ);
            params.add(ref_idn);
          ArrayList  outLst = db.execSqlLst("chargesQ", fetchchQ, params);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet  rs = (ResultSet)outLst.get(1);
            while(rs.next()){
                String chtyp=util.nvl(rs.getString("Typ"));
                String stg=util.nvl(rs.getString("stg"));
                if(stg.equals("OPT"))
                udf.setValue(chtyp+"_AUTOOPT", util.nvl(rs.getString("Flg")));
                udf.setValue(chtyp+"_rmksave", util.nvl(rs.getString("rmk")));
                udf.setValue(chtyp+"_charges", util.nvl(rs.getString("charges")));
                udf.setValue(chtyp+"_existscharges", util.nvl(rs.getString("charges")));
                udf.setValue(chtyp+"_dsc", util.nvl(rs.getString("dsc")));
                rtnview=true;
            }
            rs.close();
             pst.close();
//         if(rtnview){
         req.setAttribute("view", "Y");
         udf.setValue("idn", ref_idn);
         udf.setValue("typ", ref_typ);
//         }else{
//             req.setAttribute("msg", "Please Verify Idn");
//         }
         return am.findForward("load");
        }
     }
    
    
    public ActionForward saveCharges(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Recalculate Charges", "saveCharges");
         ReCalChargeForm udf = (ReCalChargeForm)af;
            String ref_typ=util.nvl((String)udf.getValue("typ"));
            String ref_idn=util.nvl((String)udf.getValue("idn"));
            ArrayList ary=new ArrayList();
            ArrayList chargesLst=(ArrayList)session.getAttribute("ReCalchargesLst");
            String updQ="update Trns_Charges set charges=?,CHARGES_PCT=?,flg=?,rmk=? where ref_typ=? and ref_idn=? and charges_idn=?";
            String insertQ="Insert Into Trns_Charges (trns_idn, ref_typ, ref_idn,charges_idn,amt,amt_usd,charges,CHARGES_PCT,net_usd,rmk,stt,flg)\n" + 
            "VALUES (TRNS_CHARGES_SEQ.nextval, ?, ?,?,?,?,?,?,?,?,'A',?)";
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
            String chk= util.nvl((String)udf.getValue(typcharge));
            if(!chk.equals("")){
            String autooptional= util.nvl((String)udf.getValue(typcharge+"_AUTOOPT"));  
            String extrarmk= util.nvl((String)udf.getValue(typcharge+"_rmksave")); 
            String charges= util.nvl((String)udf.getValue(typcharge+"_charges"),"0");  
            ary=new ArrayList();
            ary.add(charges);
            ary.add(charges);
            ary.add(autooptional);
            ary.add(extrarmk);
            ary.add(ref_typ);
            ary.add(ref_idn);
            ary.add(idn);
            int up = db.execUpd("update", updQ, ary);
            if(up<=0){
                ary=new ArrayList();
                ary.add(ref_typ);
                ary.add(ref_idn);
                ary.add(idn);
                ary.add("");
                ary.add("");
                ary.add(charges);
                ary.add(charges);
                ary.add("");
                ary.add(extrarmk);
                ary.add(autooptional);
                int ct = db.execUpd("Insert", insertQ, ary);  
            }
            }
            }
            }
            udf.resetALL();
            req.setAttribute("msg", "Save Changes Done SucessFully");
         return am.findForward("load");
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
                util.updAccessLog(req,res,"Recalculate Charges", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Recalculate Charges", "init");
            }
            }
            return rtnPg;
            }
    public ReCalChargeAction() {
        super();
    }
}
