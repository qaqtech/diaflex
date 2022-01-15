package ft.com.rough;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class RoughGridingReport  extends DispatchAction {
    public RoughGridingReport() {
        super();
    }
    
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String rtnPg="sucess";
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg=init(req,res,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
          RoughGridingForm udf = (RoughGridingForm)af;
          ArrayList params = null;
          String pkt_ty = "RGH";
          String stMprp = "SIGHT";
          String atrMprp = "ARTICLE";
          String szMprp = "RGH_SZ";
          String getGridingReport = " select count(*) cnt,sum(qty-nvl(qty_iss,0)) qty, to_char(sum(cts-nvl(cts_iss,0)),'999990.00') cts\n" + 
                          "      , st.val st, atr.val atr, sz.val sz,st.srt stsrt, atr.srt atrSrt, sz.srt szSrt\n" + 
                          "    from  mstk m \n" + 
                          "      , stk_dtl st, stk_dtl atr, stk_dtl sz\n" + 
                          "    where m.pkt_ty=? and \n" + 
                          "       m.idn = st.mstk_idn and st.grp = 1 and st.mprp = ? \n" + 
                          "      and m.idn = atr.mstk_idn and atr.grp = 1 and atr.mprp = ? \n" + 
                          "      and m.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = ? \n" + 
                          "      and (m.cts-nvl(m.cts_iss,0)) > 0 and stt not in ('RGH_MFG_IS','RGH_MFG_AP')\n" + 
                          "      group by st.val, atr.val, sz.val,st.srt, atr.srt, sz.srt  order by  atr.srt, sz.srt";
          params = new ArrayList();
          params.add(pkt_ty);
          params.add(stMprp);
          params.add(atrMprp);
          params.add(szMprp);
          String particle="";
          ArrayList rhgSizeList = new ArrayList();
          ArrayList ARTICLEValList = new ArrayList();
          ArrayList ARTICLESrtList = new ArrayList();
          ArrayList outLst = db.execSqlLst("getGridingReport", getGridingReport, params);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet mrs = (ResultSet)outLst.get(1);
          HashMap gridDtlmap = new HashMap();
          while(mrs.next()){
              String larticle = mrs.getString("atrSrt");
              if(particle.equals(""))
                  particle = larticle;
              if(!particle.equals(larticle)){
                  gridDtlmap.put(particle+"_RGHSIZE",rhgSizeList);
                  particle = larticle;
                  rhgSizeList = new ArrayList();
              }
              String qty = mrs.getString("qty");
              String cts = mrs.getString("cts");
              String stsrt = mrs.getString("stsrt");
              String atrSrt = mrs.getString("atrSrt");
              String atrVal = mrs.getString("atr");
              String szSrt = mrs.getString("szSrt");
              gridDtlmap.put(stsrt+"_"+atrSrt+"_"+szSrt+"_qty",qty);
              gridDtlmap.put(stsrt+"_"+atrSrt+"_"+szSrt+"_cts",cts);  
              if(!rhgSizeList.contains(szSrt))
                  rhgSizeList.add(szSrt);
              if(!ARTICLESrtList.contains(atrSrt)){
                  ARTICLESrtList.add(atrSrt);
                  ARTICLEValList.add(atrVal);
              }
              
          }
          if(!particle.equals("")){
              gridDtlmap.put(particle+"_RGHSIZE",rhgSizeList);
          }
          mrs.close();
          pst.close();
          
          req.setAttribute("ARTICLESrtList", ARTICLESrtList);
          req.setAttribute("ARTICLEValList", ARTICLEValList);
          req.setAttribute("gridDtlmap",gridDtlmap);
          return am.findForward("load");
      }
      
     }
    
    public ActionForward pktDtl(ActionMapping am, ActionForm form,
    HttpServletRequest req,
    HttpServletResponse res) throws Exception {
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
            int ct=0;
            ArrayList delAry = null;
            String delQ = " Delete from gt_srch_rslt ";
            delAry = new ArrayList();
            ct =db.execUpd(" Del Old Pkts ", delQ, delAry);
            
            String stightSrt = util.nvl((String)req.getParameter("stightSrt"));
            String articleSrt = util.nvl((String)req.getParameter("articleSrt"));
            String sizeSrt = util.nvl((String)req.getParameter("sizeSrt"));
            ArrayList params=null;
            ArrayList ary = null;
            String inGTSrch ="insert into gt_srch_rslt (stk_idn, vnm, qty, cts, cmp, quot, stt, sk1,rap_rte)"+
                    "select m.idn, m.vnm,m.qty-nvl(m.qty_iss,0), m.cts-nvl(m.cts_iss,0), m.cmp, nvl(m.upr, m.cmp), m.stt, m.sk1 , m.rap_rte "+
                    "from  mstk m "+
                    ", stk_dtl st, stk_dtl atr, stk_dtl sz "+
                    "where m.pkt_ty='RGH' and "+
                    "m.idn = st.mstk_idn and st.grp = 1 and st.mprp = 'SIGHT' and st.srt= ? "+
                    "and m.idn = atr.mstk_idn and atr.grp = 1 and atr.mprp = 'ARTICLE' and atr.srt= ? "+
                    "and m.idn = sz.mstk_idn and sz.grp = 1 and sz.mprp = 'RGH_SZ' and sz.srt= ? "+
                    "and (m.cts-nvl(m.cts_iss,0)) > 0 and stt not in ('RGH_MFG_IS','RGH_MFG_AP')";
            params = new ArrayList();
            params.add(stightSrt);
            params.add(articleSrt);
            params.add(sizeSrt);
          
             ct = db.execUpd("Insert Search Result", inGTSrch, params);
            
            
            String pktPrp = "srch_pkg.POP_PKT_PRP(pMdl => ?, pTyp => 'RPM')";
                     ary = new ArrayList();
                     ary.add("RGHMKT_VW");
                     ct = db.execCall(" Srch Prp ", pktPrp, ary);
            
            GenericInterface genericInt = new GenericImpl();
           
            ArrayList vWPrpList=genericInt.genericPrprVw(req, res, "RGHMKT_VW","RGHMKT_VW");

            ArrayList pktList = new ArrayList();
            String srchQ =" select stk_idn , pkt_ty, vnm, pkt_dte, stt , qty , cts , cert_lab,rmk , quot,rchk  ";

                for (int i = 0; i < vWPrpList.size(); i++) {
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
            String rsltQ = srchQ + " from gt_srch_rslt where 1=1 and cts > 0 ";
            
            ary = new ArrayList();

          ArrayList outLst = db.execSqlLst("search Result", rsltQ, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);

            try {
            while (rs.next()) {
            String cert_lab = util.nvl(rs.getString("cert_lab"));
            String stkIdn = util.nvl(rs.getString("stk_idn"));
            HashMap pktPrpMap = new HashMap();
            pktPrpMap.put("dsc", util.nvl(rs.getString("rmk")));
            pktPrpMap.put("stt", util.nvl(rs.getString("stt")));
            String vnm = util.nvl(rs.getString("vnm"));
            pktPrpMap.put("vnm", vnm);
            pktPrpMap.put("stk_idn", stkIdn);
            pktPrpMap.put("qty", util.nvl(rs.getString("qty")));
            pktPrpMap.put("cts", util.nvl(rs.getString("cts")));
            pktPrpMap.put("BoxDsc", util.nvl(rs.getString("rchk")));

            for (int j = 0; j < vWPrpList.size(); j++) {
            String prp = (String)vWPrpList.get(j);

            String fld = "prp_";
            if (j < 9)
            fld = "prp_00" + (j + 1);
            else
            fld = "prp_0" + (j + 1);
                if(prp.equals("RTE"))
                   fld = "quot";
                if (prp.equals("CRTWT"))
                   fld = "cts";
            String val = util.nvl(rs.getString(fld));
            pktPrpMap.put(prp, val);
            }
            pktList.add(pktPrpMap);
            }
                rs.close();
                pst.close();
            session.setAttribute("pktList", pktList);
            } catch (SQLException sqle) {

            // TODO: Add catch code
            sqle.printStackTrace();
            }
            
            return am.findForward("loadPktDtl");
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
                util.updAccessLog(req,res,"Planning Return", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Planning Return", "init");
            }
            }
            return rtnPg;
            }
    
}
