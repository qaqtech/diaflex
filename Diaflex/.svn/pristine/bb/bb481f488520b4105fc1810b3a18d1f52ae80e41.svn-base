package ft.com.box;

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
import ft.com.generic.GenericInterface;
import ft.com.marketing.PacketPrintForm;

import ft.com.report.CommonForm;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.swing.Box;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class BoxMasterAction extends DispatchAction {
   
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
            util.updAccessLog(req,res,"Box Master Form", "load start");
         BoxMasterForm udf = (BoxMasterForm)af;
         ArrayList ary = new ArrayList();
         HashMap pktPrpMap = new HashMap();
         ArrayList pktList=new ArrayList();
         ResultSet rs = null;
         String conQ="";
         String loc=util.nvl(req.getParameter("loc"));
         if(loc.equals(""))
         loc=util.nvl((String)udf.getValue("loc"));
            udf.resetALL();
             
            ary = new ArrayList();
            if(!loc.equals("")){
                conQ=" and Exists (select * from stk_dtl b where b.mstk_idn=a.idn and b.grp=1 and b.mprp='LOC' and b.val=?) ";
              ary.add(loc);
              ary.add(loc);
            }
             
            String getBoxlistQ ="select IDN, VNM, PKT_TY, QTY, CTS, qty_iss , cts_iss, UPR, FCPR, TFL3,FLG, sk1\n" + 
            "from MSTK a\n" + 
            "where a.stt in('MKAV') and a.pkt_ty in ('MIX','MX')  "+conQ+" and Not Exists (select 1 from jandtl jd where jd.mstk_idn=a.idn and jd.typ = 'CS' and jd.stt = 'IS')\n" + 
            "UNION\n" + 
            "select a.IDN, a.VNM, a.PKT_TY, a.QTY - a.qty_iss, a.CTS - a.cts_iss, a.qty_iss - jd.qty, a.cts_iss - jd.cts, a.UPR, a.FCPR, a.TFL3, a.FLG, a.sk1\n" + 
            "from MSTK a, (select mstk_idn, sum(qty) qty, sum(cts) cts from jandtl where typ = 'CS' and stt = 'IS' group by mstk_idn) jd\n" + 
            "where a.stt in('MKAV') and a.idn = jd.mstk_idn and a.pkt_ty in ('MIX','MX') "+conQ+" \n" + 
            "order by sk1, idn  ";
            rs = db.execSql(" getBoxlistQ ", getBoxlistQ, ary);
             
            while(rs.next()){
              pktPrpMap = new HashMap();
              pktPrpMap.put("stk_idn",util.nvl((String)rs.getString("IDN")));
              pktPrpMap.put("vnm",util.nvl((String)rs.getString("VNM")));
              pktPrpMap.put("typ",util.nvl((String)rs.getString("PKT_TY")));
              pktPrpMap.put("qty",util.nvl((String)rs.getString("QTY")));
              pktPrpMap.put("cts",util.nvl((String)rs.getString("CTS")));
              pktPrpMap.put("upr",util.nvl((String)rs.getString("UPR")));
              pktPrpMap.put("fcpr",util.nvl((String)rs.getString("FCPR")));
              pktPrpMap.put("tfl3",util.nvl((String)rs.getString("TFL3")));
              pktPrpMap.put("flgtype",util.nvl((String)rs.getString("FLG")));
              pktPrpMap.put("qty_iss",util.nvl((String)rs.getString("qty_iss")));
              pktPrpMap.put("cts_iss",util.nvl((String)rs.getString("cts_iss")));
              pktList.add(pktPrpMap);
            }
              rs.close();
         session.setAttribute("pktList",pktList);
         udf.setValue("loc", loc);
         req.setAttribute("loc", loc); 
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("BOX_MASTER");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("BOX_MASTER");
            allPageDtl.put("BOX_MASTER",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Box Master Form", "load end");
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
        util.updAccessLog(req,res,"Box Master Form", "save start");
        BoxMasterForm udf = (BoxMasterForm)af;
        ArrayList ary = new ArrayList();
        String loc=util.nvl((String)udf.getValue("loc"));
        String save=util.nvl((String)udf.getValue("save"));
        if(save.equals("Update"))
        return update(am, af, req, res);
        ArrayList out = new ArrayList();     

        ary.add(util.nvl((String)udf.getBname()));
        ary.add(util.nvl((String)udf.getPtyp()));
        ary.add(util.nvl((String)udf.getQty()));
        ary.add(util.nvl((String)udf.getCts()));
        ary.add(util.nvl((String)udf.getBprice()));
        ary.add(util.nvl((String)udf.getFcpr()));
        ary.add(util.nvl((String)udf.getTfl3()));
        ary.add(util.nvl((String)udf.getFlgtyp()));     
        out.add("I");
        out.add("V");
        CallableStatement cst =
        db.execCall("addBox", "STK_PKG.box_genpktidn( pidn => 8000, vnm => ?, pkt_typ => ? , qty => ? , cts => ? , upr => ? , fcpr => ? , tfl3 => ?, pflg => ? , pnewidn => ? , pnewvnm => ?)",
        ary, out);
        int stkIdn = cst.getInt(ary.size() + 1);
        String vnm = cst.getString(ary.size() + 2);
      cst.close();
      cst=null;
        if(!loc.equals("")){
        ary = new ArrayList();
        ary.add(String.valueOf(stkIdn));
        ary.add("LOC");
        ary.add(loc);
        ary.add("C");
        String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pPrpTyp => ? )";
        int ct = db.execCall("stockUpd",stockUpd, ary);
        } 
        req.setAttribute("msg", "Box Created Sucessfully "+vnm);    
        udf.setValue("loc", loc);
        util.updAccessLog(req,res,"Box Master Form", "save end");
    return load(am, af, req, res);
    }
    }
    
    public ActionForward saveprp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
             util.updAccessLog(req,res,"Box Master Form", "saveprp start");
        BoxMasterForm udf = (BoxMasterForm)af;
        HashMap mprp = info.getMprp();
        ArrayList prpUpdList=((ArrayList)session.getAttribute("prpUpdLst") == null)?new ArrayList():(ArrayList)session.getAttribute("prpUpdLst");
        if(prpUpdList!=null && prpUpdList.size() >0){
            for(int i=0 ; i<prpUpdList.size() ;i++){
            String lmprp = (String)prpUpdList.get(i);
            String mprpTyp = (String)mprp.get(lmprp+"T");
            String updVal = util.nvl((String)udf.getValue(lmprp));
          if(!updVal.equals("") && !updVal.equals("0")){
            String mstkIdn = (String)udf.getValue("mstkIdn");
            String lab = (String)udf.getValue("lab");
            ArrayList ary = new ArrayList();
            ary.add(mstkIdn);
            ary.add(lmprp);
            ary.add(updVal);
            ary.add(lab);
            ary.add(mprpTyp);
            String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?, pPrpTyp => ? )";
            int ct = db.execCall("stockUpd",stockUpd, ary);
            }
            }
        }
            udf.setMsg("Propeties Get update successfully");
             util.updAccessLog(req,res,"Box Master Form", "saveprp end");
         return boxUpd(am, af , req, res);
         }
     }
    
    public ActionForward edit(ActionMapping am , ActionForm af , HttpServletRequest req , HttpServletResponse res) throws Exception{
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
           util.updAccessLog(req,res,"Box Master Form", "edit start");
             BoxMasterForm udf = (BoxMasterForm)af;
             ResultSet rs = null;
             ArrayList ary = new ArrayList();
             String stkIDN =(String)req.getParameter("mstkIdn");
             String loc=util.nvl((String)req.getParameter("loc"));
              ary = new ArrayList();
              ary.add(stkIDN);
              String getData = " select VNM, PKT_TY, QTY, CTS, UPR, FCPR, TFL3,FLG from MSTK a  where a.stt in('MKAV') and IDN= ? and a.pkt_ty in ('MIX','MX') order by sk1 , idn " ;
              rs = db.execSql(" getData ", getData, ary);
               while(rs.next()){
                   String bname =util.nvl(rs.getString("VNM"));
                   String ptyp =util.nvl(rs.getString("PKT_TY")) ;
                   String qty =util.nvl(rs.getString("QTY"),"0") ;
                   String cts =util.nvl(rs.getString("CTS"),"0") ;
                   String bprice =util.nvl(rs.getString("UPR"),"0") ;
                   String fcpr =util.nvl(rs.getString("FCPR"),"0") ;
                   String tfl3 =util.nvl(rs.getString("TFL3")) ;
                   String flgtype =util.nvl(rs.getString("FLG")) ;
                     udf.setBname(bname);   
                     udf.setPtyp(ptyp);
                     udf.setQty(qty);
                     udf.setCts(cts);
                     udf.setBprice(bprice);
                     udf.setVlu(Float.toString(Float.parseFloat(cts)*Float.parseFloat(bprice)));
                     udf.setFcpr(fcpr);
                     udf.setTfl3(tfl3);
                     udf.setFlgtyp(flgtype);
               }
                rs.close();
                udf.setValue("loc", loc);
                udf.setValue("mstkIdn", stkIDN);
           util.updAccessLog(req,res,"Box Master Form", "edit end");
                return am.findForward("load");
       }
    }
    public ActionForward update(ActionMapping am,ActionForm af,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Box Master Form", "update start");
        BoxMasterForm udf = (BoxMasterForm)af;
        String stkIDN =util.nvl((String)udf.getValue("mstkIdn")); 
        if(!stkIDN.equals("") && !stkIDN.equals("0")){
        ArrayList ary = new ArrayList();
        ary.add(util.nvl((String)udf.getBname()));
        ary.add(util.nvl((String)udf.getPtyp()));
        ary.add(util.nvl((String)udf.getQty()));
        ary.add(util.nvl((String)udf.getCts()));
        ary.add(util.nvl((String)udf.getBprice()));
        ary.add(util.nvl((String)udf.getFcpr()));
        ary.add(util.nvl((String)udf.getTfl3()));  
        ary.add(util.nvl((String)udf.getFlgtyp()));  
        ary.add(stkIDN);  
        String updateMstkData ="update MSTK set VNM=?, pkt_ty= ?, QTY=?, CTS=?, UPR=?, fcpr=?, tfl3=?,flg=? where IDN=?";
        int ct=db.execUpd(" Box Mst ",updateMstkData , ary);
        if(ct>0)
            req.setAttribute("msg", "Save Changes Sucessfully");
        else
            req.setAttribute("msg", "Save Changes Failed");
        }
        util.updAccessLog(req,res,"Box Master Form", "update end");
    return load(am, af, req, res);
    }
    }
    
    public ActionForward boxUpd(ActionMapping am , ActionForm fm , HttpServletRequest req , HttpServletResponse res){
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
            util.updAccessLog(req,res,"Box Master Form", "boxUpd start");
        BoxMasterForm udf = (BoxMasterForm)fm;
        GenericInterface genericInt=new GenericImpl();  
        ArrayList grpList = new ArrayList();
        HashMap stockPrpUpd = new HashMap();
        String stkIdn = req.getParameter("mstkIdn");
        
        if(stkIdn==null){
           stkIdn = (String)udf.getValue("mstkIdn");
        }
        
        String stockPrp = "select c.rnk, mstk_idn, lab, b.dta_typ, mprp, a.srt, grp , decode(b.dta_typ, 'C', val , 'T', txt,'N', num) val " + 
        "from stk_dtl a, mprp b , rep_prp c " + 
        "where a.mprp = b.prp and a.grp=1 and mstk_idn =? and c.prp = b.prp and c.flg = 'Y' and mdl='BOX_UPD_DFLT' " + 
        "and a.grp = 1 "+ 
        "UNION "+  
        "Select c.rnk, a.idn mstk_idn, nvl(a.cert_lab, 'NA') lab, b.dta_typ, b.prp mprp, 0 srt, 1 grp, 'NA' val " + 
        "from mstk a, mprp b, rep_prp c " + 
        "where b.prp = c.prp  and c.mdl = 'BOX_UPD_DFLT' and c.flg = 'Y' " + 
        "and a.idn = ? and not exists (select 1 from stk_dtl d where a.idn = d.mstk_idn and d.grp = 1 and d.mprp = b.prp) " + 
        "order by 1";

        ArrayList ary = new ArrayList();
        ary.add(stkIdn);
        ary.add(stkIdn);
        ResultSet rs = db.execSql("stockDtl", stockPrp, ary);
        try {
            while(rs.next()) {
                String grp = util.nvl(rs.getString("grp"));
                if(!grpList.contains(grp))
                    grpList.add(grp);
                
                String mprp =util.nvl(rs.getString("mprp"));
                String val = util.nvl(rs.getString("val"));
              
                String msktIdn = util.nvl(rs.getString("mstk_idn"));
                stockPrpUpd.put(msktIdn+"_"+mprp+"_"+grp, val);
                if(grp.equals("1")){
                udf.setValue(mprp, util.nvl(rs.getString("val")));
                udf.setValue("lab", util.nvl(rs.getString("lab")));
                }
               
            }
            rs.close();
          
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        genericInt.genericPrprVw(req,res,"BOXUPDDFLTList","BOX_UPD_DFLT");
        genericInt.genericPrprVw(req,res,"prpUpdLst","BOX_UPD");
        udf.setValue("mstkIdn", stkIdn);

        req.setAttribute("stockList",stockPrpUpd);
        req.setAttribute("grpList", grpList);
        req.setAttribute("mstkIdn", stkIdn);
            util.updAccessLog(req,res,"Box Master Form", "boxUpd end");
        return am.findForward("updprp");
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
        util.updAccessLog(req,res,"Box Master Form", "Unauthorized Access");
        else
        util.updAccessLog(req,res,"Box Master Form", "init");
    }
    }
    return rtnPg;
    }
    public BoxMasterAction() {
        super();
    }
}
