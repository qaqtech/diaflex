package ft.com.box;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;


import ft.com.dao.labDet;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchQuery;



import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class BoxPrpUpdAction extends DispatchAction {
 
    
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
            util.updAccessLog(req,res,"Box Prp Upd", "load start");
                    BoxPrpUpdForm udf = (BoxPrpUpdForm)af;
                    udf.setBname("");
                    udf.setPtyp("");
                    udf.setQty("");
                    udf.setCts("");
                    udf.setBprice("");
                    udf.setVlu("");
                    udf.setFcpr("");
                    udf.setTfl3("");
                    udf.setFlgtyp("");
               String loc=util.nvl(req.getParameter("loc"));
               if(loc.equals(""))
               loc=util.nvl((String)udf.getValue("loc"));
                String conQ="";
                    HashMap boxList = new HashMap();
                    String getBoxValue = "select IDN, DSC from mbox where till_dt is NULL  ";
                    ArrayList ary1 = new ArrayList();
                    try{
                    ResultSet rs = db.execSql(" Fav Lst ", getBoxValue, ary1);
                    while(rs.next())
                    {
                    boxList.put(rs.getString(1),rs.getString(2));
                    }
                        rs.close();
                    session.setAttribute("boxList", boxList);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    
//                            ArrayList exsCrtra = new ArrayList();
//        String exsCrt =
//            "select crt_idn,typ||'-'||dsc exsDes from stk_crt where stt='A' and vld_dte is null";
//        rs = db.execSql(" ExistingCriteria ", exsCrt, new ArrayList());
//        while (rs.next()) {
//            ArrayList crta = new ArrayList();
//            crta.add(rs.getString("crt_idn"));
//            crta.add(rs.getString("exsDes"));
//            exsCrtra.add(crta);
//        }
//        session.setAttribute("exsCrtra", exsCrtra);
          ArrayList params = new ArrayList();
          
          if(!loc.equals("")){
              conQ=" and Exists (select * from stk_dtl b where b.mstk_idn=a.idn and b.grp=1 and b.mprp='LOC' and b.val=?) ";
            params.add(loc);
            params.add(loc);
          }
                    
            ResultSet rs = null;
            String getBoxList ="select IDN, VNM, PKT_TY, QTY, CTS, qty_iss , cts_iss, UPR, FCPR, TFL3,FLG, sk1\n" + 
            "from MSTK a\n" + 
            "where a.pkt_ty in ('MIX','MX')  "+conQ+" and Not Exists (select 1 from jandtl jd where jd.mstk_idn=a.idn and jd.typ = 'CS' and jd.stt = 'IS')\n" + 
            "UNION\n" + 
            "select a.IDN, a.VNM, a.PKT_TY, a.QTY - a.qty_iss, a.CTS - a.cts_iss, a.qty_iss - jd.qty, a.cts_iss - jd.cts, a.UPR, a.FCPR, a.TFL3, a.FLG, a.sk1\n" + 
            "from MSTK a, (select mstk_idn, sum(qty) qty, sum(cts) cts from jandtl where typ = 'CS' and stt = 'IS' group by mstk_idn) jd\n" + 
            "where a.idn = jd.mstk_idn and a.pkt_ty in ('MIX','MX') "+conQ+" \n" + 
            "order by sk1, idn  ";
            rs = db.execSql(" Get Inv Id ", getBoxList, params);
            ArrayList listtable=new ArrayList();
            try {
                while(rs.next()){
                  HashMap listBox=new HashMap();
                  String mstkidn =rs.getString("IDN");
                  String vnm =rs.getString("VNM");
                  String ptype =util.nvl(rs.getString("PKT_TY")) ;
                  String qty =util.nvl(rs.getString("QTY")) ;
                  String cts =util.nvl(rs.getString("CTS")) ;
                  String upr =util.nvl(rs.getString("UPR")) ;
                  String fcpr =util.nvl(rs.getString("FCPR")) ;
                  String tfl3 =util.nvl(rs.getString("TFL3")) ; 
                  String flgtype =util.nvl(rs.getString("FLG")) ; 
                  String qty_iss =util.nvl(rs.getString("qty_iss")) ; 
                  String cts_iss =util.nvl(rs.getString("cts_iss")) ;
               //   String shape =util.nvl(rs.getString("VAL")) ;
                 
                  listBox.put("stk_idn",mstkidn);
                  listBox.put("vnm",vnm);
                  listBox.put("typ",ptype);
                  listBox.put("qty",qty);
                  listBox.put("cts",cts);
                  listBox.put("upr",upr);
                  listBox.put("fcpr",fcpr);
                  listBox.put("tfl3",tfl3);
                  listBox.put("typ",flgtype);
                  listBox.put("qty_iss",qty_iss);
                  listBox.put("cts_iss",cts_iss);
               //   listBox.add(shape);
                  listtable .add(listBox);

                }
                  rs.close();
              } catch (Exception e) {
                  // TODO: Add catch code
                  e.printStackTrace();
              }
                    session.setAttribute("listtable",listtable);
     
//                    ArrayList shp = new ArrayList();
//                    String getShapeLst = " select VAL from prp where mprp = 'SHAPE'";
//                    ArrayList ary = new ArrayList();
//                    rs = db.execSql(" Shape Lst ", getShapeLst, ary);
//                    while(rs.next()) {
//                    String shapeName= rs.getString(1);
//                    ary.add(shapeName);
//                    //shp.add(ary);    
//                    }
//                    session.setAttribute("shapelst",ary);
//                 
        
//        HashMap crtList = new HashMap();
//        String getcrtValue = "select VAL, DSC from prp where mprp = 'SIZE' and DSC IS NOT NULL ";
//        ArrayList crt = new ArrayList();
//        try{
//        rs = db.execSql(" carat Lst ", getcrtValue, crt);
//        while(rs.next())
//        {
//        crtList.put(util.nvl(rs.getString(1)),util.nvl(rs.getString(2)));
//        }
//        session.setAttribute("crtList", crtList);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//            
//        
//        HashMap clrList = new HashMap();
//        String getclrValue = "select VAL, DSC from prp where mprp = 'CLR' and DSC IS NOT NULL";
//        ArrayList clr = new ArrayList();
//        try{
//        rs = db.execSql(" color Lst ", getclrValue, clr);
//        while(rs.next())
//        {
//        clrList.put(util.nvl(rs.getString(1)),util.nvl(rs.getString(2)));
//        }
//        session.setAttribute("clrList", clrList);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
        
        udf.setValue("loc", loc);
        req.setAttribute("loc", loc);
            util.updAccessLog(req,res,"Box Prp Upd", "load end");
        return am.findForward("view");
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
             util.updAccessLog(req,res,"Box Prp Upd", "save start");
         BoxPrpUpdForm udf = (BoxPrpUpdForm)af;
         String loc=util.nvl((String)udf.getValue("loc"));
             if(req.getParameter("upd")!=null){
                      return update(am, af, req, res);
                 } 
             
         ArrayList ary = new ArrayList();
         ArrayList out = new ArrayList();
         String dsc = util.nvl((String)udf.getBname());
         String ptype = util.nvl((String)udf.getPtyp());
         String qty = util.nvl((String)udf.getQty());
         String cts = util.nvl((String)udf.getCts());
         String upr = util.nvl((String)udf.getBprice());
         String fcpr = util.nvl((String)udf.getFcpr());
         String tfl3 = util.nvl((String)udf.getTfl3());
         String flgtype = util.nvl((String)udf.getFlgtyp());
         
         ary.add(dsc);
         ary.add(ptype);
         ary.add(qty);
         ary.add(cts);
         ary.add(upr);
         ary.add(fcpr);
         ary.add(tfl3);
         ary.add(flgtype);
      
         out.add("I");
         out.add("V");
         StringBuffer sb = new StringBuffer();
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
             util.updAccessLog(req,res,"Box Prp Upd", "save end");
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
             util.updAccessLog(req,res,"Box Prp Upd", "saveprp start");
        BoxPrpUpdForm udf = (BoxPrpUpdForm)af;
        HashMap mprp = info.getMprp();
        ArrayList prpUpdList = (ArrayList)info.getValue("prpUpdLst");
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
            }}
            
        }
            udf.setMsg("Propeties Get update successfully");
      
            boxUpd(am, af , req, res);
             util.updAccessLog(req,res,"Box Prp Upd", "saveprp end");
         return am.findForward("updprp");
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
                    util.updAccessLog(req,res,"Box Prp Upd", "edit start");
             String stkIDN =(String)req.getParameter("mstkIdn");
             session.setAttribute("idn", stkIDN);
            BoxPrpUpdForm udf = (BoxPrpUpdForm)af;
            ActionForward forward=new ActionForward();
            ActionMessages messages = new ActionMessages();
                String loc=util.nvl((String)req.getParameter("loc"));
            ResultSet rs = null;
              ArrayList idn = new ArrayList();
              idn.add(stkIDN);
                 String getData = " select VNM, PKT_TY, QTY, CTS, UPR, FCPR, TFL3,FLG from MSTK a  where IDN= ? and a.pkt_ty in ('MIX','MX') order by sk1 , idn " ;
                 rs = db.execSql(" Get Inv Id ", getData, idn);
                  try {
               while(rs.next()){
                   String bname =util.nvl(rs.getString("VNM"));
                   String ptyp =util.nvl(rs.getString("PKT_TY")) ;
                   String qty =util.nvl(rs.getString("QTY")) ;
                   String cts =util.nvl(rs.getString("CTS")) ;
                   String bprice =util.nvl(rs.getString("UPR")) ;
                   String fcpr =util.nvl(rs.getString("FCPR")) ;
                   String tfl3 =util.nvl(rs.getString("TFL3")) ;
                   String flgtype =util.nvl(rs.getString("FLG")) ;
       //            String bshp =util.nvl(rs.getString("VAL")) ;
                   
                     udf.setBname(bname);   
                     udf.setPtyp(ptyp);
                     udf.setQty(qty);
                     udf.setCts(cts);
                     udf.setBprice(bprice);
                     Float i = Float.parseFloat(cts);
                     Float j = Float.parseFloat(bprice);
                     Float k;
                     k = i * j;
                     String value = Float.toString(k);
                     udf.setVlu(value);
                     udf.setFcpr(fcpr);
                     udf.setTfl3(tfl3);
                     udf.setFlgtyp(flgtype);
         //            udf.setBshp(bshp);
               
               }
                 rs.close();
             } catch (Exception e) {
                 // TODO: Add catch code
                 e.printStackTrace();
             }
                udf.setValue("loc", loc);
                req.setAttribute("loc", loc);
                    util.updAccessLog(req,res,"Box Prp Upd", "edit end");
                return am.findForward("view");
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
             util.updAccessLog(req,res,"Box Prp Upd", "update start");
        BoxPrpUpdForm udf = (BoxPrpUpdForm)af;
        
        String stkIDN =(String)session.getAttribute("idn");
        ActionForward forward=new ActionForward();
        ActionMessages messages = new ActionMessages();
          
          try {
         
            ArrayList ary = new ArrayList();
            ArrayList ary1 = new ArrayList(); 
            ary.add(util.nvl((String)udf.getBname()));
            ary.add(util.nvl((String)udf.getPtyp()));
            ary.add(util.nvl((String)udf.getQty()));
            ary.add(util.nvl((String)udf.getCts()));
            ary.add(util.nvl((String)udf.getBprice()));
            ary.add(util.nvl((String)udf.getFcpr()));
            ary.add(util.nvl((String)udf.getTfl3()));  
            ary.add(util.nvl((String)udf.getFlgtyp()));  
        //    ary1.add(udf.getBshp());
            String updateMstkData =
                "update MSTK set VNM=?, pkt_ty= ?, QTY=?, CTS=?, UPR=?, fcpr=?, tfl3=?,flg=? where IDN="+stkIDN;
//            String updateStldtlData =
//                "update STK_DTL set VAL=? where MPRP='SHAPE' and MSTK_IDN="+stkIDN;  ;
        int ct=db.execUpd(" Box Mst ",updateMstkData , ary);
//        int ct1=db.execUpd(" Shape Mst ",updateStldtlData , ary1);
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
             util.updAccessLog(req,res,"Box Prp Upd", "update end");
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
            util.updAccessLog(req,res,"Box Prp Upd", "boxUpd start");
        BoxPrpUpdForm udf = (BoxPrpUpdForm)fm;
        SearchQuery     srchQuery=new SearchQuery();
        GenericInterface genericInt=new GenericImpl(); 
        String stkIdn = req.getParameter("mstkIdn");
        String mdl = req.getParameter("mdl");
        
        if(stkIdn==null){
           stkIdn = (String)udf.getValue("mstkIdn");
            mdl = (String)udf.getValue("mdl");
        //    stkIdn = (String)udf.getMstkIdn1();
        //    mdl = (String)udf.getMdl1();
        }
       
        ArrayList grpList = new ArrayList();
        HashMap stockPrpUpd = new HashMap();
        
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
        udf.setValue("mstkIdn", stkIdn);
        udf.setValue("mdl", mdl);
        genericInt.genericPrprVw(req, res, "BOXUPDDFLTList", "BOX_UPD_DFLT");
        req.setAttribute("stockList",stockPrpUpd);
        req.setAttribute("grpList", grpList);
        req.setAttribute("mstkIdn", stkIdn);
        ArrayList  prpUpdList = srchQuery.getPRPUPGrp(req ,res, mdl);
        info.setValue("prpUpdLst", prpUpdList);
            util.updAccessLog(req,res,"Box Prp Upd", "boxUpd end");
        return am.findForward("updprp");
        }
    } 

    
    public ArrayList FetchResult(HttpServletRequest req,HttpServletResponse res){
     //   String vnm = util.nvl((String)params.get("vnm"));  
     HttpSession session = req.getSession(false);
     InfoMgr info = (InfoMgr)session.getAttribute("info");
     DBUtil util = new DBUtil();
     DBMgr db = new DBMgr(); 
     db.setCon(info.getCon());
     util.setDb(db);
     util.setInfo(info);
     db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
     util.setLogApplNm(info.getLoApplNm());
        ArrayList stockList = new ArrayList();
        ArrayList ary = null;
        ArrayList delAry=new ArrayList();
        delAry.add("M");
        String delQ = " Delete from gt_srch_rslt where flg in (?)";
        int ct =db.execUpd(" Del Old Recds ", delQ, delAry);
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn , vnm , pkt_dte, stt , flg , qty , cts, rmk ) " + 
        " select pkt_ty, b.idn, b.vnm, b.dte, stt , 'M', qty , cts, tfl3  from mstk b where b.pkt_ty='MIX' ";
        
        ary = new ArrayList();
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("MEMO_VIEW");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        stockList = SearchResult(req,res);
        req.setAttribute("pktList", stockList);
        return stockList;
    }
    
    public ArrayList SearchResult(HttpServletRequest req,HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        GenericInterface genericInt=new GenericImpl(); 
        ArrayList pktList = new ArrayList();
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "BoxViewLst", "MEMO_VIEW");
        String  srchQ =  " select pkt_ty, stk_idn, vnm, pkt_dte, stt , flg, qty , cts, rmk ";
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

        String rsltQ = srchQ + " from gt_srch_rslt where flg =? ";
        
        ArrayList ary = new ArrayList();
        ary.add("M");
        ResultSet rs = db.execSql("search Result", rsltQ, ary);
        try {
            while(rs.next()) {

                HashMap boxPrpMap = new HashMap();
                boxPrpMap.put("stt", util.nvl(rs.getString("stt")));
//                String vnm = util.nvl(rs.getString("vnm"));
//                boxPrpMap.put("vnm",vnm);
//                    String tfl3 = util.nvl(rs.getString("rmk"));
//                    if(vnmLst.indexOf(tfl3)!=-1){
//                        if(vnmLst.indexOf("'")==-1 && !tfl3.equals(""))
//                            vnmLst =  vnmLst.replaceAll(tfl3,"");
//                        else
//                            vnmLst =  vnmLst.replaceAll("'"+tfl3+"'", "");
//                    } else if(vnmLst.indexOf(vnm)!=-1 && !vnm.equals("")){
//                        if(vnmLst.indexOf("'")==-1)
//                            vnmLst =  vnmLst.replaceAll(vnm,"");
//                        else
//                            vnmLst =  vnmLst.replaceAll("'"+vnm+"'", "");
//                    }       
                boxPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                boxPrpMap.put("qty",util.nvl(rs.getString("qty")));
                boxPrpMap.put("cts",util.nvl(rs.getString("cts")));
                for(int j=0; j < vwPrpLst.size(); j++){
                     String prp = (String)vwPrpLst.get(j);
                      
                      String fld="prp_";
                      if(j < 9)
                              fld="prp_00"+(j+1);
                      else    
                              fld="prp_0"+(j+1);
                      
                      String val = util.nvl(rs.getString(fld)) ;
                     
                        
                        boxPrpMap.put(prp, val);
                         }
                              
                    pktList.add(boxPrpMap);
                }
            rs.close();
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
//        if(!vnmLst.equals("")){
//        vnmLst = vnmLst.replaceAll(",", "");
//        req.setAttribute("vnmNotFnd", vnmLst+" : Result Not Found");
//        }
        return pktList;
    }
    public ActionForward adddata(ActionMapping am,ActionForm fm,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Box Prp Upd", "adddata start");
            BoxPrpUpdForm udf = (BoxPrpUpdForm)fm;
   //     String labIdn = "6";
   //     String vnm = util.nvl((String)udf.getValue("vnmLst")).trim();
        HashMap params = new HashMap();
   //     params.put("labIdn", labIdn);
   //     params.put("vnm", vnm);
        ArrayList stockList = FetchResult(req,res);
        if(stockList.size()>0){
        HashMap totals = GetTotal(req);
        req.setAttribute("totalMap", totals);
        
        ArrayList serviceList = getService(req);
        udf.setServiceList(serviceList);
        }
      req.setAttribute("view", "Y");
      session.setAttribute("StockList", stockList);
            util.updAccessLog(req,res,"Box Prp Upd", "adddata end");
    return am.findForward("boxadd");
        }
    }
    public HashMap GetTotal(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    HashMap gtTotalMap = new HashMap();
    String gtTotal ="Select count(*) qty, sum(cts) cts from gt_srch_rslt where flg = ?";
    ArrayList ary = new ArrayList();
    ary.add("M");
    ResultSet rs = db.execSql("getTotal", gtTotal, ary);
    try {
    if (rs.next()) {
    gtTotalMap.put("qty",util.nvl(rs.getString("qty")));
    gtTotalMap.put("cts", util.nvl(rs.getString("cts")));
    }
        rs.close();
    } catch (SQLException sqle) {
    // TODO: Add catch code
    sqle.printStackTrace();
    }

    return gtTotalMap ;
    }
    public ArrayList getService(HttpServletRequest req){
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
        ArrayList serviceList = new ArrayList();
       
        String labQry=" select val , dsc from prp where mprp = ?  and vld_till is null and flg is null";
        ary = new ArrayList();
        ary.add("SERVICE");
        ResultSet rs = db.execSql("labQry", labQry, ary);
        try {
        while (rs.next()) {
            labDet labDet = new labDet();
            String labVal = rs.getString("val");
            labDet.setLabVal(labVal);
            labDet.setLabDesc(rs.getString("dsc"));
            serviceList.add(labDet);
           
        }
            rs.close();
        } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
        }
        
        return serviceList;
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
        util.updAccessLog(req,res,"Box Prp Upd", "Unauthorized Access");
        else
        util.updAccessLog(req,res,"Box Prp Upd", "init");
    }
    }
    return rtnPg;
    }
    public BoxPrpUpdAction() {
        super();
    }
}

