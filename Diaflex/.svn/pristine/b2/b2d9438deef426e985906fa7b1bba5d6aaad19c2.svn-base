package ft.com.box;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.JspUtil;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.inward.TransferToMktSHForm;
import ft.com.lab.LabSelectionForm;

import ft.com.marketing.SearchQuery;

import java.io.OutputStream;

import java.sql.Connection;
import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class BoxSelectionAction extends DispatchAction{
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
            util.updAccessLog(req,res,"Box Selection", "load start");
        BoxSelectionForm boxSelectForm = (BoxSelectionForm)form;
        GenericInterface genericInt=new GenericImpl();
        boxSelectForm.resetALL();

        ArrayList assortSrchList =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_BOXGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_BOXGNCSrch");
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
        
        ArrayList boxls = new ArrayList(); 
        ResultSet rs1 = null;
        String getBoxname = "select distinct b.val from mstk a, stk_dtl b where a.idn = b.mstk_idn and b.grp = 1 " +
            "and b.mprp = 'BOX_TYP' and a.stt = 'MX_AV' ";
        ArrayList ary2 = new ArrayList();
        
        try{
        rs1 = db.execSql(" Fav Lst ", getBoxname, ary2);
        while(rs1.next())
        {
            boxls.add(util.nvl(rs1.getString("val")));
        }
        rs1.close();
        session.setAttribute("boxnme", boxls);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

            util.updAccessLog(req,res,"Box Selection", "load end");
        return am.findForward("load");
        }
    }
    
    public ActionForward SetBoxTyp(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Box Selection", "SetBoxTyp start");
           ArrayList  ary = new ArrayList();
           int ct = db.execCall("box_allocate","MIX_PKG.SET_BOX_TYP_ALL", ary);
           req.setAttribute("msg", "Process done successfully..");
            util.updAccessLog(req,res,"Box Selection", "SetBoxTyp end");
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
            util.updAccessLog(req,res,"Box Selection", "view start");
        GenericInterface genericInt=new GenericImpl();
        String delQ = " Delete from gt_srch_rslt ";
        int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        boolean isGencSrch = false;
        HashMap pktList = new HashMap();
        ArrayList genricSrchLst =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_BOXGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_BOXGNCSrch");
        info.setGncPrpLst(genricSrchLst);
        BoxSelectionForm boxSelectForm = (BoxSelectionForm)form;
        BoxSelectionInterface boxSelectInt=new BoxSelectionImpl();
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        ArrayList stockList = new ArrayList();
        ArrayList vwPrpLst = new ArrayList();
        String mdl = "BOX_VIEW";
        
        req.setAttribute("page","MIX");
        
        String view =util.nvl(boxSelectForm.getView());
        String vnm = util.nvl((String)boxSelectForm.getValue("vnmLst"));
        //String boxNme = util.nvl(req.getParameter("boxnme"));
        if(view.length() > 0){
            if(vnm.equals("")){
               
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
                            String reqVal1 = util.nvl((String)boxSelectForm.getValue(lprp + "_" + lVal),"");
                            if(!reqVal1.equals("")){
                            paramsMap.put(lprp + "_" + lVal, reqVal1);
                            }
                               
                            }
                        }else{
                        String fldVal1 = util.nvl((String)boxSelectForm.getValue(lprp+"_1"));
                        String fldVal2 = util.nvl((String)boxSelectForm.getValue(lprp+"_2"));
                        if(typ.equals("T")){
                            fldVal1 = util.nvl((String)boxSelectForm.getValue(lprp+"_1"));
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
                paramsMap.put("stt", "MX_AV");
                paramsMap.put("mdl", "BOX_VIEW");
                isGencSrch = true; 
                util.genericSrch(paramsMap);
                stockList = boxSelectInt.SearchResult(req,res,boxSelectForm);
            }
        }
        if(!isGencSrch){
        stockList = boxSelectInt.FetchResult(req,res , boxSelectForm );
        vwPrpLst  = genericInt.genericPrprVw(req, res, "BoxViewLst", "BOX_VIEW");
        }         
        HashMap boxlst = new HashMap();
        
            String args = "select a.idn, a.vnm  from mstk a, stk_dtl b where a.idn = b.mstk_idn and a.stt <> 'MX_AV' and a.pkt_ty in ('MX', 'MIX') and b.grp = 1 and  b.mprp='BOX_TYP' order by a.sk1 ";
                
              //  args = args.substring(0, args.lastIndexOf("or"));
              //  args += " )";
                ArrayList ary2 = new ArrayList();
                ResultSet rs1 = null;
                try{
                rs1 = db.execSql(" Box Lst ", args, ary2);
                while(rs1.next())
                {
                boxlst.put(util.nvl(rs1.getString(1)),util.nvl(rs1.getString(2)));
                }
                    rs1.close();
                session.setAttribute("boxlst", boxlst);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }        
        
        
        req.setAttribute("view", "Y");
        session.setAttribute("StockList", stockList);
        
        boxSelectForm.setView("");
        boxSelectForm.setViewAll("");
        
            util.updAccessLog(req,res,"Box Selection", "view end");
        return am.findForward("load");
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
            util.updAccessLog(req,res,"Box Selection", "view1 start");
        BoxSelectionForm boxSelectForm = (BoxSelectionForm)form;
        
        String vnmlst = (String) boxSelectForm.getValue("vnmLst");
        
        
        String getpktlist = "";
        
        if(vnmlst.equals("")){   
            getpktlist = "select vnm,sh,co,cts,qty,upr from vstk_a where stt='MIX_AV'";    
            
        } else
        getpktlist = "select vnm,sh,co,cts,qty,upr from vstk_a where vnm in ('"+vnmlst+"') and  stt in ('MIX_AV','MX_AV')";
        
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
        String getBoxname = "select idn, vnm from mstk where pkt_ty in ('MIX','MX') order by vnm";
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

                     
        
        boxSelectForm.setView("");
        boxSelectForm.setViewAll("");
            util.updAccessLog(req,res,"Box Selection", "view1 end");
        
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
            util.updAccessLog(req,res,"Box Selection", "save start");
        BoxSelectionForm boxSelectForm = (BoxSelectionForm)form;
        String Crexcel =util.nvl((String)boxSelectForm.getValue("excel"));
        String allocatebox =util.nvl((String)boxSelectForm.getValue("allbox"));
        HashMap dbinfo = info.getDmbsInfoLst();
        String clnt = (String)dbinfo.get("CNT");
        
        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
        String bname = (String)req.getParameter("bnme");
        String cts = (String)req.getParameter("newcts");
        String rte = (String)req.getParameter("newrte");
        String qty = (String)req.getParameter("newqty");
        String uqty = (String)req.getParameter("userqty");
        String ucts = (String)req.getParameter("usercts");
        String urte = (String)req.getParameter("userrte");
        
                int ct=0;
                ArrayList ary = new ArrayList();
                ArrayList ary1 = new ArrayList();
                for(int i=0;i< stockList.size();i++){
                        HashMap stockPkt = (HashMap)stockList.get(i);
                         String stkIdn = (String)stockPkt.get("stk_idn");
                         String lStt    = util.nvl((String) boxSelectForm.getValue("stt_" + stkIdn));
                if(lStt.equals("MIX")){ 
                ary = new ArrayList();
                ary.add(bname);
                ary.add(stkIdn); 
                ct = db.execCall("box_sel","MIX_PKG.MERGE_PKT(pPktRt  => ?, pIdn  => ?)", ary);
                ary1 = new ArrayList();
                ary1.add(bname);
                
                if(!ucts.equals("")){
                        ary1.add(ucts);
                        ary1.add(urte);
                        ary1.add(uqty);
                }else {
                ary1.add(cts);
                ary1.add(rte);
                ary1.add(qty);
                }
                ct = db.execCall("box_sel","MIX_PKG.MOD_RT_PKT(pIdn => ?, pCts => ?, pRte => ?, pQty => ?)", ary1);
        }
        }
                

                if(!Crexcel.equals("")){
                ExcelUtil xlUtil = new ExcelUtil();
                xlUtil.init(db, util, info);
                String delQ = " Delete from gt_srch_rslt ";
                ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
                ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
                boolean isSelected=false;
                for(int i=0 ; i < stockList.size();i++){
                HashMap stockPkt = (HashMap)stockList.get(i);
                String stkIdn = (String)stockPkt.get("stk_idn");
                String vnm = (String)stockPkt.get("vnm");
                String isChecked = util.nvl((String)boxSelectForm.getValue("EXC_"+stkIdn));
                
                if(isChecked.equals("yes")){
                ary = new ArrayList();
                ary.add(stkIdn);
                ary.add(vnm);
                ary.add(util.nvl((String)stockPkt.get("stt")));
                ary.add(util.nvl((String)stockPkt.get("qty")));
                ary.add(util.nvl((String)stockPkt.get("cts")));
                ary.add(util.nvl((String)stockPkt.get("cert_lab")));
                ct = db.execUpd("insertGt", "Insert into gt_srch_rslt (stk_idn , flg , vnm , stt , qty , cts , cert_lab) " +
                    "select ? , 'Z',?,? , ? ,? ,? from dual ",ary);
                 
            }
            }
                
                    if(!Crexcel.equals("")){

                    String pktPrp = "pkgmkt.pktPrp(0,?)";
                    ary = new ArrayList();
                    ary.add("BOX_VIEW");
                    ct = db.execCall(" Srch Prp ", pktPrp, ary);
                    HSSFWorkbook hwb = null;
                    hwb =  xlUtil.getDataAllInXl("SRCH", req, "BOX_VIEW");
                   
                    OutputStream out = res.getOutputStream();
                    String CONTENT_TYPE = "getServletContext()/vnd-excel";
                    String fileNm = "BoxResultExcel"+util.getToDteTime()+".xls";
                    res.setContentType(CONTENT_TYPE);
                    res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
                    hwb.write(out);
                    out.flush();
                    out.close();
                    }
                
                
                }
                
      
        
        boxSelectForm.setView("");
        boxSelectForm.setViewAll("");
        req.setAttribute("msg", "Packet moved successfully to Box..");
        session.setAttribute("StockList", stockList);
            util.updAccessLog(req,res,"Box Selection", "save end");
        return load(am, form, req, res);
        }
    }
    
    
    
    
    public ActionForward saveold(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Box Selection", "saveold start");
        BoxSelectionForm boxSelectForm = (BoxSelectionForm)form;
        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
        int ct=0;
        ArrayList ary = new ArrayList();
        for(int i=0;i< stockList.size();i++){
            HashMap stockPkt = (HashMap)stockList.get(i);
             String stkIdn = (String)stockPkt.get("stk_idn");
            String lStt    = util.nvl((String) boxSelectForm.getValue("stt_" + stkIdn));
            if(!lStt.equals("")){
            String newStt = lStt+"_AV";
            if(lStt.equals("LB")){
                String lab= util.nvl((String) boxSelectForm.getValue("lab_" + stkIdn));
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
        boxSelectForm.setView("");
        boxSelectForm.setViewAll("");
         req.setAttribute("msg", "Box selection done successfully..");
        req.setAttribute("view", "Y");
        session.setAttribute("StockList", stockList);
            util.updAccessLog(req,res,"Box Selection", "saveold end");
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
            util.updAccessLog(req,res,"Box Selection", "loadsplit start");
        BoxSelectionForm boxSelectForm = (BoxSelectionForm)form;
        GenericInterface genericInt=new GenericImpl();
        boxSelectForm.resetALL();
        ArrayList assortSrchList =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_BOXGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_BOXGNCSrch");
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
        
            util.updAccessLog(req,res,"Box Selection", "loadsplit end");
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
            util.updAccessLog(req,res,"Box Selection", "splitview start");
        BoxSelectionForm boxSelectForm = (BoxSelectionForm)form;
        String bxnme = (String) boxSelectForm.getBnme();
        
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
        

        boxSelectForm.setView("");
        boxSelectForm.setViewAll("");
        
            util.updAccessLog(req,res,"Box Selection", "splitview end");
        return am.findForward("splitload");
        }
    }
    
public BoxSelectionAction() {
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
        util.updAccessLog(req,res,"Box Selection", "Unauthorized Access");
        else
        util.updAccessLog(req,res,"Box Selection", "init");
    }
    }
    return rtnPg;
    }
}
