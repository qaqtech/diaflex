package ft.com.lab;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.JspUtil;
import ft.com.dao.labDet;
import java.sql.Connection;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.sql.CallableStatement;

import java.sql.PreparedStatement;
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

public class FinalLabSelection extends DispatchAction{
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
            util.updAccessLog(req,res,"Final Lab Selection", "load start");
        FinalLabSelectionForm labSelectForm = (FinalLabSelectionForm)form;
        GenericInterface genericInt = new GenericImpl();
        labSelectForm.resetAll();
        ArrayList lbSrchList  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch"); 
        info.setGncPrpLst(lbSrchList);
            util.updAccessLog(req,res,"Final Lab Selection", "load end");
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
            util.updAccessLog(req,res,"Final Lab Selection", "view start");
        FinalLabSelectionForm labSelectForm = (FinalLabSelectionForm)form;
        FinalLabSelectionInterface labSelectInt=new FinalLabSelectionImpl();
        boolean isGencSrch = false;
        ArrayList stockList = new ArrayList();
            ArrayList genricSrchLst  =((ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch") == null)?new ArrayList():(ArrayList)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_LBGNCSrch"); 
        info.setGncPrpLst(genricSrchLst);
//      ArrayList genricSrchLst = info.getGncPrpLst();
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        String view =util.nvl(labSelectForm.getView());
        String vnm = util.nvl((String)labSelectForm.getValue("vnmLst"));       
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
                            String reqVal1 = util.nvl((String)labSelectForm.getValue(lprp + "_" + lVal),"");
                            if(!reqVal1.equals("")){
                            paramsMap.put(lprp + "_" + lVal, reqVal1);
                            }
                               
                            }
                        }else{
                        String fldVal1 = util.nvl((String)labSelectForm.getValue(lprp+"_1"));
                        String fldVal2 = util.nvl((String)labSelectForm.getValue(lprp+"_2"));
                        if(typ.equals("T")){
                            fldVal1 = util.nvl((String)labSelectForm.getValue(lprp+"_1"));
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
                  paramsMap.put("stt", "LB_FN");
                  paramsMap.put("mdl", "AS_VIEW");
                isGencSrch = true; 
                util.genericSrch(paramsMap);
                labSelectForm.reset();
                stockList = labSelectInt.SearchResult(req,res, labSelectForm , "");
            }
        }
 
        if(!isGencSrch)
           stockList = labSelectInt.FetchResult(req ,res, labSelectForm );
        if(stockList.size()>0){
        HashMap totals = labSelectInt.GetTotal(req,res);
        req.setAttribute("totalMap", totals);
            ArrayList options = labSelectInt.getOptions(req ,res);
            req.setAttribute("OPTIONS", options);
        }
        req.setAttribute("view", "Y");
        session.setAttribute("StockList", stockList);
      
        labSelectForm.setView("");
        labSelectForm.setViewAll("");
            util.updAccessLog(req,res,"Final Lab Selection", "view end");
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
            util.updAccessLog(req,res,"Final Lab Selection", "save start");
        FinalLabSelectionForm labSelectForm = (FinalLabSelectionForm)form;
        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
        HashMap labMap =  (HashMap)session.getAttribute("labMap");
        HashMap dbmsSys = info.getDmbsInfoLst();
      
        String msg="";
        if(stockList!=null && stockList.size()>0){
            for(int i=0;i<stockList.size();i++){
                HashMap stockPkt = (HashMap)stockList.get(i);
                String stkIdn = (String)stockPkt.get("stk_idn");
                String ischeck = util.nvl((String)labSelectForm.getValue(stkIdn));
                if(ischeck.equals("yes")){
                String lab = util.nvl((String)labSelectForm.getValue("RD_"+stkIdn));
                String lStkStt = util.nvl((String)labSelectForm.getValue("STT_"+stkIdn));
                //if(!lab.equals("")){
                    lab = lab.substring( 0,lab.indexOf("_"));
                   
                    ArrayList out = new ArrayList();
                    out.add("I");
                    ArrayList ary = new ArrayList();
                    ary.add(stkIdn);
                    ary.add(lab);
                    ary.add(lStkStt);//Mayur
                   CallableStatement ct = db.execCall("final lab sel", "ISS_RTN_PKG.FNL_LAB_SEL(pStkIdn =>?, pGrp => ? ,  pStt =>? , lCnt=> ?)", ary , out);
                  int cnt = ct.getInt(ary.size()+1);
                  
                  if(cnt > 0)
                    msg = msg + stkIdn+" , ";
                  if(cnt > 0){
                      ArrayList labList = (ArrayList)labMap.get(stkIdn);
                      if(labList !=null && labList.size() > 0){
                          for(int l=0;l<labList.size();l++){
                          labDet labObj = (labDet)labList.get(l);
                          String dsc = labObj.getLabDesc();
                          String grp = labObj.getGrp();
                          String fldNm = dsc+"_"+stkIdn+"_"+grp;
                          String fldVal = util.nvl((String)labSelectForm.getValue(fldNm));
                          ary = new ArrayList();
                          if(fldVal.equals("yes")){
                              ary.add("Y");
                         }else{
                             ary.add("N");       
                         }
                            ary.add(stkIdn);
                            ary.add(grp);
                            ary.add(dsc);
                        String updateSTKRte = "update stk_rte set flg = ? where mstk_idn = ? and grp=? and lab=? ";
                        int ct1 = db.execUpd("update", updateSTKRte, ary);
                              
                      }}
                  }
                //}
                }
                
            }
            
        }
        
       req.setAttribute("msg", msg);
            util.updAccessLog(req,res,"Final Lab Selection", "save end");
      return load(am, form, req, res);
        }
    }
    public FinalLabSelection() {
        super();
    }
    public ActionForward labDtl(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Final Lab Selection", "loadDtl start");
            FinalLabSelectionForm labSelectForm = (FinalLabSelectionForm)form;
            FinalLabSelectionInterface labSelectInt=new FinalLabSelectionImpl();
       String stkIdn = req.getParameter("mstkIdn");
       String mdl = util.nvl(req.getParameter("mdl"));
       ArrayList ary = new ArrayList();
       if(mdl.equals("")){
           String sql = "select * from mstk where pkt_ty <> 'NR' and idn = ?";
           ary.add(stkIdn);

           ArrayList outLst = db.execSqlLst("pktTy", sql, ary);
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet rs = (ResultSet)outLst.get(1);
           if(rs.next())
               mdl="BOX_VIEW";
            else
               mdl="MEMO_VW";
           rs.close(); pst.close();
       }
         
       HashMap param = new HashMap();
       param.put("stkIdn", stkIdn);
       param.put("mdl", mdl);
       labSelectInt.labDetail(req,res , param);
       req.setAttribute("mdl", mdl);
        ArrayList labViewList =LabPrprViwMdl(req,res,mdl);
            util.updAccessLog(req,res,"Final Lab Selection", "loadDtl end");
        return am.findForward("loadDtl");
        }
    }
    
    public ArrayList LabPrprViwMdl(HttpServletRequest req , HttpServletResponse res , String mdl){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList viewPrp = null;
        try {
            if (viewPrp == null) {

                viewPrp = new ArrayList();

                ArrayList outLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = '"+mdl+"' and flg='Y' order by rnk ",
                               new ArrayList());
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs1 = (ResultSet)outLst.get(1);
                while (rs1.next()) {

                    viewPrp.add(rs1.getString("prp"));
                }
                rs1.close(); pst.close();
                session.setAttribute(mdl, viewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return viewPrp;
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
                util.updAccessLog(req,res,"Final Lab Selection", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Final Lab Selection", "init");
            }
            }
            return rtnPg;
            }
}
