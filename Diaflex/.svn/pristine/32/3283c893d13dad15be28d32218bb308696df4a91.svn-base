package ft.com.mpur;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.ByrDao;
import ft.com.dao.GenDAO;
import ft.com.fileupload.FileUploadAction;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.marketing.SearchForm;

import java.io.IOException;

import java.sql.Connection;
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

public class PurchasePricingAction  extends DispatchAction {
    public PurchasePricingAction() {
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
        util.updAccessLog(req,res,"purchase Pricing", "load start");
        PurchasePricingForm purchaseForm = (PurchasePricingForm) af;
        purchaseForm.resetAll();
        ArrayList venderIdnList = getvndridn(req);
        purchaseForm.setVenderList(venderIdnList);
           util.updAccessLog(req,res,"purchase Pricing", "load end");
        return am.findForward("PurPri");
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
            util.updAccessLog(req,res,"purchase Pricing", "fetch start");
        PurchasePricingForm purchaseForm = (PurchasePricingForm) af;
        int delGt = db.execUpd("delete", "delete from gt_srch_rslt", new ArrayList());
        String vender = util.nvl((String)purchaseForm.getValue("vender"));
        String purIdn = util.nvl((String)purchaseForm.getValue("purIdn"));
        purchaseForm.reset();
        ArrayList params = new ArrayList();
        ArrayList puridLst = new ArrayList();
        params.add(purIdn);
        params.add("PUR_VW");
        int ct = db.execCall("POP_GT", "PUR_PKG.POP_GT(purIdn => ? , pMdl => ?)", params);
        ArrayList pktList = SearchResult(req,res);
        req.setAttribute("pktList", pktList);
        HashMap totalMap = GetTotal(req);
        req.setAttribute("totalMap", totalMap);
        GenDAO gen=new GenDAO();
        gen.setIdn(purIdn);
        gen.setNmeIdn(purIdn);
        puridLst.add(gen);
        purchaseForm.setPurIdnList(puridLst);
        purchaseForm.setValue("vender", vender);
        purchaseForm.setValue("purIdn", purIdn);
            util.updAccessLog(req,res,"purchase Pricing", "fetch end");
        return am.findForward("PurPri");
        }
    }
    public ActionForward reprc(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"purchase Pricing", "reprc start");
      PurchasePricingForm purchaseForm = (PurchasePricingForm) af;
      String insQ = " insert into gt_pkt(mstk_idn) select stk_idn from gt_srch_rslt where flg = ? ";
      ArrayList params = new ArrayList();
      params.add("S");
      int ct = db.execUpd(" reprc memo", insQ, params);
      if(ct>0){
          String purPkg = "PUR_PKG.REPRC";
          ct = db.execCall("reprc", purPkg, new ArrayList());
          req.setAttribute("msg", "Pricing Done...");
      }else{
          req.setAttribute("msg", "Error in process..");
      }
          util.updAccessLog(req,res,"purchase Pricing", "reprc end");
      return fetch(am, af, req, res);
      }
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
        GenericInterface genericInt = new GenericImpl();
        ArrayList pktList = new ArrayList();
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "PurViewLst", "PUR_VW");
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts , cmp , quot , rmk ,  decode(rap_rte, 1, '', trunc(((cmp/greatest(rap_rte,1))*100)-100,2)) cmpdis, decode(rap_rte, 1, '', trunc(((quot/greatest(rap_rte,1))*100)-100,2)) dis ";
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

       String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by sk1 , cts ";
        ArrayList ary = new ArrayList();
        ary.add("Z");
      ArrayList rsList = db.execSqlLst("search Result", rsltQ, ary);
      PreparedStatement pst = (PreparedStatement)rsList.get(0);
      ResultSet rs = (ResultSet)rsList.get(1);
        try {
            while(rs.next()) {

                HashMap pktPrpMap = new HashMap();
                pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
                String vnm = util.nvl(rs.getString("vnm"));
                pktPrpMap.put("vnm",vnm);
                pktPrpMap.put("stk_idn",util.nvl(rs.getString("stk_idn")));
                pktPrpMap.put("qty",util.nvl(rs.getString("qty")));
                pktPrpMap.put("cts",util.nvl(rs.getString("cts")));
                    pktPrpMap.put("cmp",util.nvl(rs.getString("cmp")));
                    pktPrpMap.put("quot",util.nvl(rs.getString("quot")));
                    pktPrpMap.put("cmpDis",util.nvl(rs.getString("cmpdis")));
                    pktPrpMap.put("dis",util.nvl(rs.getString("dis")));
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
        } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
        }
       
        return pktList;
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
        ary.add("Z");
      ArrayList rsList = db.execSqlLst("getTotal", gtTotal, ary);
      PreparedStatement pst = (PreparedStatement)rsList.get(0);
      ResultSet rs = (ResultSet)rsList.get(1);
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
        
        return gtTotalMap ;
    }
    
    
    public ArrayList getvndridn(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList ary = new ArrayList();
        ArrayList venderList = new ArrayList();
        String venderQ = "Select distinct a.vndr_idn,b.nme from mpur a,nme_v b where a.vndr_idn=b.nme_idn and a.stt='A' order by b.nme";
      ArrayList rsList = db.execSqlLst("empSql", venderQ, ary);
      PreparedStatement pst = (PreparedStatement)rsList.get(0);
      ResultSet rs = (ResultSet)rsList.get(1);
     try {
            while (rs.next()) {
                ByrDao byr = new ByrDao();

                byr.setByrIdn(rs.getInt("vndr_idn"));
                byr.setByrNme(rs.getString("nme"));
                venderList.add(byr);
            }
            rs.close();
         pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return venderList;
    }
    
    public ActionForward loadpurId(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
      int fav = 0;
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      StringBuffer sb = new StringBuffer();
      String nmeId = util.nvl(req.getParameter("purId"));
      String favSrch = "select pur_idn from mpur where vndr_idn="+nmeId+" order by pur_idn";
          ArrayList rsList = db.execSqlLst("favSrch",favSrch, new ArrayList());
          PreparedStatement pst = (PreparedStatement)rsList.get(0);
          ResultSet rs = (ResultSet)rsList.get(1);
      while(rs.next()){
          fav=1;
          String pur_idn = util.nvl(rs.getString("pur_idn"));
          sb.append("<vender>");
          sb.append("<nme>" + pur_idn +"</nme>");
          sb.append("<nmeid>" + pur_idn +"</nmeid>");
          sb.append("</vender>");
     }
     
       if(fav==1)
        res.getWriter().write("<venders>" +sb.toString()+ "</venders>");
        rs.close();
        pst.close();
        return null;
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
                util.updAccessLog(req,res,"purchase Pricing", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"purchase Pricing", "init");
            }
            }
            return rtnPg;
            }
}
