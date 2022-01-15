package ft.com.box;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;
import ft.com.report.AssortLabPendingForm;

import java.io.IOException;

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

public class PendingMixAction extends DispatchAction {
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
        util.updAccessLog(req,res,"Pending Mix", "loadstart");
        PendingMixForm udf = (PendingMixForm)af;
        GenericInterface genericInt=new GenericImpl();
        int ct = db.execUpd("delete gt", "delete from gt_srch_rslt", new ArrayList());
        int count=0;
         ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        String srchRefQ = " Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts,sk1 ) " + 
        " select  a.pkt_ty, a.idn, a.vnm,  a.dte, a.stt , 'Z' , a.qty , a.cts,a.sk1 "+
        " from mstk a where a.stt = 'MX_AV' ";
        
        ArrayList  ary = new ArrayList();         
        ct = db.execUpd(" Srch Prp ", srchRefQ, ary);
        
        String pktPrp = "pkgmkt.pktPrp(0,?)";
        ary = new ArrayList();
        ary.add("BOX_MIX");
        ct = db.execCall(" Srch Prp ", pktPrp, ary);
        
        ArrayList vwPrpLst = genericInt.genericPrprVw(req, res, "BOX_MIX", "BOX_MIX");
        String  srchQ =  " select stk_idn , pkt_ty,  vnm, pkt_dte, stt , qty , cts,sk1 ";
        
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
        
        
        String rsltQ = srchQ + " from gt_srch_rslt where flg =? order by sk1,stk_idn,cts";        
        ary = new ArrayList();
        ary.add("Z");
        ResultSet rs = db.execSql("search Result", rsltQ, ary);
        
        ArrayList pktDtlList = new ArrayList();
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
          
            pktDtl.put(lprp, util.nvl(rs.getString(fld)));
            
          }
            
           pktDtlList.add(pktDtl);
        }
         rs.close();
        udf.setPktDtlList(pktDtlList);
         req.setAttribute("mdl","BOX_MIX");
             util.updAccessLog(req,res,"Pending Mix", "load end");
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
        util.updAccessLog(req,res,"Pending Mix", "Unauthorized Access");
        else
        util.updAccessLog(req,res,"Pending Mix", "init");
    }
    }
    return rtnPg;
    }
    
    public PendingMixAction() {
        super();
    }
}
