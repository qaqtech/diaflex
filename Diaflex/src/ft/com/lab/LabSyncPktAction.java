package ft.com.lab;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.dao.GtPktDtl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LabSyncPktAction extends DispatchAction{
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        String mprpStr ="";
        ArrayList prpViewLst = new ArrayList();
        String mdlPrpsQ = "Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||mprp||''''||' '|| \n" + 
                      " Upper((Replace(Replace(Replace(mprp,'-','_'),'&','_')),'COMMENT','COM1'))\n" + 
                      " str From GIA_SYNC_PRP_MAP Rp Where stt ='A' order by srt ";
        ArrayList ary = new ArrayList();

            ArrayList outLst = db.execSqlLst("mprp ", mdlPrpsQ , ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
       while(rs.next()) {
         String val = util.nvl((String)rs.getString("str"));
          mprpStr = mprpStr +" "+val;
          val = val.substring(val.indexOf("'"),val.lastIndexOf("'"));
           prpViewLst.add(val);
       }
       rs.close(); pst.close();
       HashMap pktDtlMap = new HashMap();
       String lab_syncDate = "with LABRS as ( " + 
       "select b.control_no, b.mstk_idn,c.mprp,nvl(nvl(nvl(nvl(c.val,c.num),c.txt),c.dte),c.lab_val) atr from lab_sync a , lab_sync_pkt b,lab_sync_pkt_prp c " + 
       "where a.idn = b.sync_idn and b.idn = c.sync_pkt_idn and a.res_stt='SUCCESS' and b.stt='POST' ) " + 
       "select * from LABRS PIVOT (max(atr)  for mprp in ("+mprpStr+")";
        ArrayList rsLst = db.execSqlLst("lab_syncDate", lab_syncDate, new ArrayList());
        pst =(PreparedStatement)rsLst.get(0);
        rs = (ResultSet)rsLst.get(1);
        while(rs.next()){
            String stk_idn = rs.getString("mstk_idn");
             GtPktDtl pktDtl = new GtPktDtl();
            pktDtl.setValue("vnm", rs.getString("control_no"));
            pktDtl.setValue("stk_idn", stk_idn);
            for(int i=0;i<prpViewLst.size();i++){
                String lprp = (String)prpViewLst.get(i);
                String val = rs.getString(lprp);
                pktDtl.setValue(lprp, val);
            }
            pktDtlMap.put(stk_idn, pktDtl);
        }
        rs.close(); pst.close();

         String lstNme = "LABSYN_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
          gtMgr.setValue(lstNme+"_SEL",new ArrayList());
            gtMgr.setValue(lstNme, pktDtlMap);
            gtMgr.setValue("lstNmeLABSYN", lstNme);
            
            return am.findForward(rtnPg);   
        }else{
            return am.findForward("load"); 
        }
    }
    public LabSyncPktAction() {
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
                util.updAccessLog(req,res,"Lab Sync Pkt", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Lab Sync Pkt", "init");
            }
            }
            return rtnPg;
  }
}
