package ft.com.box;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.lab.LabSelectionForm;

import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class BoxtoBoxAction extends DispatchAction{
   
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
        util.updAccessLog(req,res,"Box to Box", "load start");
        BoxtoBoxForm boxtoBoxForm = (BoxtoBoxForm)form;
        GenericInterface genericInt=new GenericImpl();
        boxtoBoxForm.resetALL();    
        ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch"); 
        info.setGncPrpLst(assortSrchList);
        
        req.setAttribute("PageVW", "MIX");
        
        HashMap boxTyp = new HashMap();
        String getBoxtyp = "select VAL, DSC from prp where mprp = 'BOX_TYP' and dte is NULL";
        ArrayList ary1 = new ArrayList();
        ResultSet rs = null;
        try{
        rs = db.execSql(" Fav Lst ", getBoxtyp, ary1);
        while(rs.next())
        {
        boxTyp.put(util.nvl(rs.getString(1)),util.nvl(rs.getString(2)));
        }
            rs.close();
        session.setAttribute("boxTyp", boxTyp);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        HashMap boxnme = new HashMap();
        String getBoxname = "select idn, vnm from mstk where pkt_ty in ('MIX','MX') order by vnm";
        ArrayList ary2 = new ArrayList();
        ResultSet rs1 = null;
        try{
        rs1 = db.execSql(" Fav Lst ", getBoxname, ary2);
        while(rs1.next())
        {
        boxnme.put(util.nvl(rs1.getString(1)),util.nvl(rs1.getString(2)));
        }
            rs1.close();
        session.setAttribute("boxnme", boxnme);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

            util.updAccessLog(req,res,"Box to Box", "load end");
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
                util.updAccessLog(req,res,"Box to Box", "view start");
            BoxtoBoxForm boxtoBoxForm = (BoxtoBoxForm)form;
            BoxtoBoxInterface boxtoBoxInt=new BoxtoBoxImpl();
            String s = util.nvl((String)boxtoBoxForm.getValue("pb_lab1"));
            String m = util.nvl((String)boxtoBoxForm.getValue("pb_lab2"));
            
            String sourcevnm = (String)req.getParameter("bnme");
            session.setAttribute("stkid", sourcevnm);
            
            if(!s.equals("")){
                req.setAttribute("page1",s);    
            }
            if(!m.equals("")){
                req.setAttribute("page1",m);    
            }
            
            boolean isGencSrch = false;
            ArrayList stockList = new ArrayList();
            
            String vnm = util.nvl((String)boxtoBoxForm.getValue("vnmLst"));
            
            stockList = boxtoBoxInt.FetchResult(req,res , boxtoBoxForm );
            req.setAttribute("view", "Y");
            session.setAttribute("StockList", stockList);
            
            
            String vnmId = util.nvl(req.getParameter("bnme"));
            
            String favSrch = "select VNM,nvl(qty, 0) - nvl(qty_iss,0) QTY,nvl(cts, 0) - nvl(cts_iss, 0) CTS, UPR,qty avlQTY,cts avlCTS from mstk where idn= "+vnmId;
            ArrayList ary = new ArrayList();
            ResultSet rs = db.execSql("favSrch",favSrch, ary);
            try{
            while(rs.next()){
                
                String ptyp =util.nvl(rs.getString("VNM")) ;
                String qty =util.nvl(rs.getString("QTY")) ;
                String cts =util.nvl(rs.getString("CTS")) ;
                String bprice =util.nvl(rs.getString("UPR")) ;
                
                boxtoBoxForm.setValue("avlqty",util.nvl(rs.getString("avlQTY")));
                boxtoBoxForm.setValue("avlcts",util.nvl(rs.getString("avlCTS")));
                boxtoBoxForm.setValue("avlrte",bprice);
                boxtoBoxForm.setVnm(ptyp);
                boxtoBoxForm.setFromqty(qty);
                boxtoBoxForm.setFromcts(cts);
                boxtoBoxForm.setFromrte(bprice);
                Float j = Float.parseFloat("0");
                Float i = Float.parseFloat("0");
                Float k = Float.parseFloat("0");
                Float p, q;
                String value = "";
                if(qty.equals("") && cts.equals("") && bprice.equals("")){
                
                 i = Float.parseFloat(qty);
                 j = Float.parseFloat(cts);
                 k = Float.parseFloat(bprice);
               
                q = j * k;
                value = Float.toString(Math.round(q));    
                }else{
                    j=Float.parseFloat("0");
                }
                if (j != 0) {
                p = j / i;
                String size = Float.toString(p);
                boxtoBoxForm.setFromsize(size);
                boxtoBoxForm.setValue("avlsize",size);
                } else {
                boxtoBoxForm.setFromsize("0");
                boxtoBoxForm.setValue("avlsize","0");
                }
                
                boxtoBoxForm.setFromvalue(value);
                boxtoBoxForm.setValue("avlvalue",value);
                   
            }
                rs.close();
            }catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
            
            boxtoBoxForm.setView("");
            boxtoBoxForm.setViewAll("");
             
                util.updAccessLog(req,res,"Box to Box", "view end");
            return am.findForward("load");
            }
        }
    

    public ActionForward save(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Box to Box", "save start");
    BoxtoBoxForm boxtoBoxForm = (BoxtoBoxForm)form;
    ArrayList stockList = (ArrayList)session.getAttribute("StockList");

    String lStt = util.nvl((String) boxtoBoxForm.getValue("split"));
    String stkIdn = (String)session.getAttribute("stkid");

    String frmBox = "";
    String toBox = "";

    for(int i=1; i <= 3; i++) {
    String lStkIdn = (String)req.getParameter("bnmeto_"+i);

    if(lStkIdn.length() > 0) {
    if(lStt.equalsIgnoreCase("SPLIT")) {
    toBox = lStkIdn ;
    frmBox = stkIdn ;
    } else {
    frmBox = lStkIdn;
    toBox = stkIdn ;
    }

    String usrQty = req.getParameter("userqty_"+i);
    String usrCts = req.getParameter("usercts_"+i);
    String usrRte = req.getParameter("userrte_"+i);

    String lQty = getDataVal(usrQty, req.getParameter("addqty_"+i));
    String lCts = getDataVal(usrCts, req.getParameter("addcts_"+i));
    String lRte = getDataVal(usrRte, req.getParameter("addrte_"+i));

    ArrayList ary = new ArrayList();
    ary.add(frmBox);
    ary.add(toBox);
    ary.add(lCts);

    String mergeParams = "pFrm => ?, pTo => ?, pCts => ?";
    if(lQty.length() > 0) {
    mergeParams += ", pQty => ?";
    ary.add(lQty);
    }
    if(lRte.length() > 0) {
    mergeParams += ", pAvgRte => ?";
    ary.add(lRte);
    }
    String mergeSplitMixPkt = "MIX_PKG.BOX_MERGE_PKT("+ mergeParams +")";
//    System.out.println("mergeParams:=>"+ary.toString());
    int cnt = db.execCall("Merge : "+ frmBox + " to " + toBox, mergeSplitMixPkt, ary);

    if (usrCts.length() > 0) {
    String modRtPkt = "MIX_PKG.MOD_RT_PKT(pIdn => ?, pCts => ?, pRte => ?, pQty => ?)";
    ArrayList params = new ArrayList();
    params.add(toBox);
    params.add(lCts);
    params.add(lRte);
    params.add(lQty);

    cnt = db.execCall("ModRt : " + toBox, modRtPkt, params);
    }
    }
    }

    String usrQty = req.getParameter("userfromqty");
    String usrCts = req.getParameter("userfromcts");
    String usrRte = req.getParameter("userfromrte");

    String lQty = getDataVal(usrQty, req.getParameter("newfromqty"));
    String lCts = getDataVal(usrCts, req.getParameter("newfromcts"));
    String lRte = getDataVal(usrRte, req.getParameter("newfromrte"));

    if (usrCts.length() > 0) {
    String modRtPkt = "MIX_PKG.MOD_RT_PKT(pIdn => ?, pCts => ?, pRte => ?, pQty => ?)";
    ArrayList params = new ArrayList();
    params.add(frmBox);
    params.add(lCts);
    params.add(lRte);
    params.add(lQty);

    int cnt = db.execCall("ModRt : " + frmBox, modRtPkt, params);
    }

    boxtoBoxForm.setView("");
    boxtoBoxForm.setViewAll("");
    req.setAttribute("msg", "Box selection done successfully..");
    session.setAttribute("StockList", stockList);
            util.updAccessLog(req,res,"Box to Box", "save end");
    return load(am, form, req, res);
        }
    }
    
    public String getDataVal(String usrVal, String sysVal) {
        if(usrVal.length() == 0)
            return sysVal ;
        else
            return usrVal ;
    }
    
    public ActionForward saveRP(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Box to Box", "saveRP start");
        BoxtoBoxForm boxtoBoxForm = (BoxtoBoxForm)form;
        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
        
        String lStt  = util.nvl((String) boxtoBoxForm.getValue("split"));
                
                if(lStt.equals("SPLIT")){ 
                         
                int count = 3;
                int ct=0;
                    ArrayList ary = new ArrayList();
                    ArrayList ary1 = new ArrayList();
                    ArrayList ary2 = new ArrayList();
                    String stkIdnfrm = (String)session.getAttribute("stkid");
                
                    String ufqty = (String)req.getParameter("userfromqty");
                    String ufcts = (String)req.getParameter("userfromcts");
                    String ufrte = (String)req.getParameter("userfromrte");
                    String ctsdiff = (String)req.getParameter("fromctsdiff");
                     
                
                for(int i=1;i<= count;i++){
        
                    String stkIdnto = (String)req.getParameter("bnmeto_"+i);
                    String cts = (String)req.getParameter("addcts_"+i);
                    String rte = (String)req.getParameter("addrte_"+i);
                    String qty = (String)req.getParameter("addqty_"+i);
                    
                    String uqty = (String)req.getParameter("userqty_"+i);
                    String ucts = (String)req.getParameter("usercts_"+i);
                    String urte = (String)req.getParameter("userrte_"+i);
            
                    String tcts = (String)req.getParameter("totalcts_"+i);
                    
                    if(!stkIdnto.equals("") && stkIdnto != null  ){ 
                  
                ary = new ArrayList();
                ary.add(stkIdnfrm);
                ary.add(stkIdnto);
                
                    if(!ucts.equals("")){
                        ary.add(ucts);
                        ary.add(uqty);
                        ary.add(urte);
                        
                    }else {
                            ary.add(cts);
                            ary.add(qty);
                            ary.add(rte);
                            
                        }
                    
                ct = db.execCall("box_sel","MIX_PKG.BOX_MERGE_PKT(pFrm => ?, pTo => ?, pCts => ?, pQty => ?, pAvgRte => ?)", ary);
                
                    if(!ucts.equals("") && ucts != null  ){
                            ary1 = new ArrayList();
                        
                            Float k = Float.parseFloat(tcts);
                            Float l = Float.parseFloat(ucts);
                            Float m;
                            m = k - l;
                            String value = Float.toString(m);
                            
                        if(m > 0){
                            
                        ary1.add(stkIdnfrm);
                        ary1.add(ucts);
                        ary1.add(urte);
                        ary1.add(value);
                        ary1.add(uqty);
                            
                        ct = db.execCall("box_sel","MIX_PKG.MOD_RT_PKT(pIdn => ?, pCts => ?, pRte => ?, pWtDiff => ?, pQty => ?)", ary1);    
                        
                        } 
                        }
                
                
                
                }
                }   
                    if(!ctsdiff.equals("")){
                        ary2.add(stkIdnfrm);
                        ary2.add(ufcts);
                        ary2.add(ufrte);
                        ary2.add(ctsdiff);
                        ary2.add(ufqty);
                        ct = db.execCall("box_sel","MIX_PKG.MOD_RT_PKT(pIdn => ?, pCts => ?, pRte => ?, pWtDiff => ?, pQty => ?)", ary2);    
                    }
                }
                    
        
        if(lStt.equals("MERGE")){ 
                 
        int count = 3;
        int ct=0;
        ArrayList ary = new ArrayList();
            ArrayList ary1 = new ArrayList();
            ArrayList ary2 = new ArrayList();
        String stkIdnto = (String)session.getAttribute("stkid");
        
            String ufqty = (String)req.getParameter("userfromqty");
            String ufcts = (String)req.getParameter("userfromcts");
            String ufrte = (String)req.getParameter("userfromrte");
            String ctsdiff = (String)req.getParameter("fromctsdiff");
            
        
        
        for(int i=1;i<= count;i++){
        
            String stkIdnfrm = (String)req.getParameter("bnmeto_"+i);
            
            String cts = (String)req.getParameter("addcts_"+i);
            String rte = (String)req.getParameter("addrte_"+i);
            String qty = (String)req.getParameter("addqty_"+i);
            
            String uqty = (String)req.getParameter("userqty_"+i);
            String ucts = (String)req.getParameter("usercts_"+i);
            String urte = (String)req.getParameter("userrte_"+i);
            
            String tcts = (String)req.getParameter("totalcts_"+i);
        
            if(!stkIdnfrm.equals("") && stkIdnfrm != null  ){ 
                
            
            
        ary = new ArrayList();
        ary.add(stkIdnfrm);
        ary.add(stkIdnto);
        
            if(!ucts.equals("")){
                ary.add(ucts);
                ary.add(uqty);
                ary.add(urte);
                
            }else {
                    ary.add(cts);
                    ary.add(qty);
                    ary.add(rte);
                    
            }
            
        ct = db.execCall("box_sel","MIX_PKG.BOX_MERGE_PKT(pFrm => ?, pTo => ?, pCts => ?, pQty => ?, pAvgRte => ?)", ary);
        
            if(!ucts.equals("") && ucts != null  ){
                    ary1 = new ArrayList();
                    Float k = Float.parseFloat(tcts);
                    Float l = Float.parseFloat(ucts);
                    Float m;
                    m = k - l;
                    String value = Float.toString(m);
                    
                    if(m > 0){
                    
                ary1.add(stkIdnfrm);
                ary1.add(ucts);
                ary1.add(urte);
                ary1.add(value);
                ary1.add(uqty);
                    
                ct = db.execCall("box_sel","MIX_PKG.MOD_RT_PKT(pIdn => ?, pCts => ?, pRte => ?, pWtDiff => ?, pQty => ?)", ary1);    
                
                } }
        
        
        }
        }       
            if(!ctsdiff.equals("")){
                ary2.add(stkIdnto);
                ary2.add(ufcts);
                ary2.add(ufrte);
                ary2.add(ctsdiff);
                ary2.add(ufqty);
                ct = db.execCall("box_sel","MIX_PKG.MOD_RT_PKT(pIdn => ?, pCts => ?, pRte => ?, pWtDiff => ?, pQty => ?)", ary2);    
            }
        }
                    
                    
                    
                    
            
            boxtoBoxForm.setView("");
            boxtoBoxForm.setViewAll("");
            req.setAttribute("msg", "Box selection done successfully..");
            session.setAttribute("StockList", stockList);
            util.updAccessLog(req,res,"Box to Box", "saveRP end");
            return load(am, form, req, res);
        
        }
    }
    
    
    public ActionForward view1(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Box to Box", "view1 start");
        BoxtoBoxForm boxtoBoxForm = (BoxtoBoxForm)form;
        
        String vnmlst = (String) boxtoBoxForm.getValue("vnmLst");
        
        
        String getpktlist = "";
        
        if(vnmlst.equals("")){   
            getpktlist = "select vnm,sh,co,cts,qty,upr from vstk_a where stt='MIX_AV'";    
            
        } else
        getpktlist = "select vnm,sh,co,cts,qty,upr from vstk_a where vnm in ('"+vnmlst+"') and stt='MIX_AV'";
        
        ResultSet rs = db.execSql(" Get Inv Id ", getpktlist, new ArrayList());
        ArrayList listpkt=new ArrayList();
        try {
            while(rs.next()){
              ArrayList listpt=new ArrayList();
              String vnm =util.nvl(rs.getString("vnm")) ;
              String sh =util.nvl(rs.getString("sh")) ;
              String co =util.nvl(rs.getString("co")) ;
              String cts =util.nvl(rs.getString("cts")) ;
              String qty =util.nvl(rs.getString("qty")) ;
              String upr =util.nvl(rs.getString("upr")) ;
                
              listpt.add(vnm);
              listpt.add(sh);
              listpt.add(co);
              listpt.add(cts);
              listpt.add(qty);
              listpt.add(upr);
              listpkt.add(listpt);
            }
              rs.close();
          } catch (Exception e) {
              e.printStackTrace();
          }
          req.setAttribute("listpkt",listpkt);
        
        
        
        HashMap boxnme = new HashMap();
        String getBoxname = "select idn, vnm from mstk where pkt_ty='MIX' order by vnm";
        ArrayList ary1 = new ArrayList();
        ResultSet rs1 = null;
        try{
        rs1 = db.execSql(" Fav Lst ", getBoxname, ary1);
        while(rs1.next())
        {
        boxnme.put(rs1.getString(1),rs1.getString(2));
        }
            rs1.close();
        session.setAttribute("boxnme", boxnme);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

                     
        
        boxtoBoxForm.setView("");
        boxtoBoxForm.setViewAll("");
        
            util.updAccessLog(req,res,"Box to Box", "view1 end");
        return am.findForward("load");
        }
    }
    
    
    
    
        
    
    
    public ActionForward saveOLD(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Box to Box", "saveOLD start");
        BoxtoBoxForm boxtoBoxForm = (BoxtoBoxForm)form;
        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
        int ct=0;
        ArrayList ary = new ArrayList();
        for(int i=0;i< stockList.size();i++){
            HashMap stockPkt = (HashMap)stockList.get(i);
             String stkIdn = (String)stockPkt.get("stk_idn");
            String lStt    = util.nvl((String) boxtoBoxForm.getValue("stt_" + stkIdn));
            if(!lStt.equals("")){
            String newStt = lStt+"_AV";
            if(lStt.equals("LB")){
                String lab= util.nvl((String) boxtoBoxForm.getValue("lab_" + stkIdn));
                if(lab.equals("NA") || lab.equals("NONE") || lab.equals("NC"))
                    newStt = "LB_CF";
                ary = new ArrayList();
                ary.add(stkIdn);
                ary.add(lab);
                ct = db.execCall("lab_sel","ISS_RTN_PKG.LAB_SEL(pStkIdn => ?, pLab => ?)", ary);
                
                 ary = new ArrayList();
                 ary.add(newStt);
                 ary.add(lab);
                 ary.add(stkIdn);
                 String updGt ="update mstk set stt=? , cert_lab=? where idn=? ";
                ct = db.execUpd("update Gt", updGt, ary);
            }else if(lStt.equals("REP") || lStt.equals("MIX")){
                ary = new ArrayList();
                ary.add(newStt);
                ary.add(stkIdn);
                String updGt ="update mstk set stt=? where idn=? ";
                ct = db.execUpd("update Gt", updGt, ary);
            }else{
                
            }
            
            }
            
        }
        boxtoBoxForm.setView("");
        boxtoBoxForm.setViewAll("");
         req.setAttribute("msg", "Box selection done successfully..");
        req.setAttribute("view", "Y");
        session.setAttribute("StockList", stockList);
            util.updAccessLog(req,res,"Box to Box", "saveOLD end");
        return am.findForward("load");
        }
    }
    public ActionForward loadsplit(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Box to Box", "loadsplit start");
        BoxtoBoxForm boxtoBoxForm = (BoxtoBoxForm)form;
        GenericInterface genericInt=new GenericImpl();
        boxtoBoxForm.resetALL();
            ArrayList assortSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch"); 
        info.setGncPrpLst(assortSrchList);
        
        HashMap boxTyp = new HashMap();
        String getBoxtyp = "select VAL, DSC from prp where mprp = 'BOX_TYP' and dte is NULL";
        ArrayList ary1 = new ArrayList();
        ResultSet rs = null;
        try{
        rs = db.execSql(" Fav Lst ", getBoxtyp, ary1);
        while(rs.next())
        {
        boxTyp.put(rs.getString(1),rs.getString(2));
        }
            rs.close();
        session.setAttribute("boxTyp", boxTyp);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        HashMap boxnme = new HashMap();
        String getBoxname = "select idn, vnm from mstk where pkt_ty='MIX' order by vnm";
        ArrayList ary2 = new ArrayList();
        ResultSet rs1 = null;
        try{
        rs1 = db.execSql(" Fav Lst ", getBoxname, ary2);
        while(rs1.next())
        {
        boxnme.put(rs1.getString(1),rs1.getString(2));
        }
            rs1.close();
        session.setAttribute("boxnme", boxnme);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
            util.updAccessLog(req,res,"Box to Box", "loadsplit end");
        
        return am.findForward("splitload");
        }
    }
    public ActionForward splitview(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Box to Box", "splitview start");
        BoxtoBoxForm boxtoBoxForm = (BoxtoBoxForm)form;
        String bxnme = (String) boxtoBoxForm.getBnme();
        
        String getpktlist = "";
        
        if(bxnme.equals("")){   
            getpktlist = "select idn,vnm,cts,qty,upr from vstk_a where pkt_ty= 'MX'";    
            
        } else
        getpktlist = "select idn,vnm,cts,qty,upr from vstk_a where pkt_ty= 'MX' and pkt_rt="+bxnme;
        
        ResultSet rs = db.execSql(" Get Inv Id ", getpktlist, new ArrayList());
        ArrayList listpkt=new ArrayList();
        try {
            while(rs.next()){
              ArrayList listpt=new ArrayList();
              String idn =util.nvl(rs.getString("idn")) ;
              String vnm =util.nvl(rs.getString("vnm")) ;
              String cts =util.nvl(rs.getString("cts")) ;
              String qty =util.nvl(rs.getString("qty")) ;
              String upr =util.nvl(rs.getString("upr")) ;
              
              listpt.add(idn);
              listpt.add(vnm);
              listpt.add(cts);
              listpt.add(qty);
              listpt.add(upr);
              listpkt .add(listpt);
            }
              rs.close();
          } catch (Exception e) {
              e.printStackTrace();
          }
          req.setAttribute("listpkt",listpkt);
        
        String getsumpkt = "";
        
        if(bxnme.equals("")){   
            getsumpkt = "select sum(cts) ttlcts,sum(qty) ttlqty,sum(upr) ttlupr  from vstk_a where pkt_ty= 'MX'";    
        } else
        getsumpkt = "select sum(cts) ttlcts,sum(qty) ttlqty,sum(upr) ttlupr from vstk_a where pkt_ty= 'MX' and pkt_rt="+bxnme;
        
        ResultSet rs1 = db.execSql(" Get Inv Id ", getsumpkt, new ArrayList());
        ArrayList ttlpkt=new ArrayList();
        try {
            while(rs1.next()){
              
              String cts =util.nvl(rs1.getString("ttlcts")) ;
              String qty =util.nvl(rs1.getString("ttlqty")) ;
              String upr =util.nvl(rs1.getString("ttlupr")) ;
              ttlpkt.add(cts);
              ttlpkt.add(qty);
              ttlpkt.add(upr);
            }
              rs1.close();
          } catch (Exception e) {
              e.printStackTrace();
          }
        req.setAttribute("ttlpkt",ttlpkt);
        

        boxtoBoxForm.setView("");
        boxtoBoxForm.setViewAll("");
        
            util.updAccessLog(req,res,"Box to Box", "splitview end");
        return am.findForward("splitload");
        }
    }
    
  public BoxtoBoxAction() {
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
        util.updAccessLog(req,res,"Box to Box", "Unauthorized Access");
        else
        util.updAccessLog(req,res,"Box to Box", "init");
    }
    }
    return rtnPg;
    }
}
