package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.PktDtl;

import ft.com.generic.GenericImpl;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PostShipmentAction extends DispatchAction{

    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
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
        rtnPg=init(request,response,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
      PostShipmentForm form = (PostShipmentForm)af;
          util.updAccessLog(request,response,"Post Shipment", "Post Shipment load");
      return am.findForward("load");
        }
    }
    
    public ActionForward view(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
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
        rtnPg=init(request,response,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
      PostShipmentForm form = (PostShipmentForm)af;
      util.updAccessLog(request,response,"Post Shipment", "Post Shipment view");
      String vnmLst = util.nvl((String)form.getValue("vnm"));
      ArrayList params = new ArrayList();
      ArrayList pkts = new ArrayList();
      if(!vnmLst.equals("")){
          vnmLst = util.getVnm(vnmLst);
          String sqlDlv = " select a.idn , a.qty , mstk_idn, nvl(fnl_sal, quot) memoQuot, quot, fnl_sal " + 
          ", b.cts, nvl(b.upr, b.cmp) rte, nvl(b.sk1,1) sk1, b.rap_rte, a.stt , b.vnm " + 
          ", trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2) dis " + 
          ", trunc(((nvl(fnl_sal, quot)/greatest(b.rap_rte,1))*100)-100,2) mDis , b.tfl3 " + 
          "from dlv_dtl a, mstk b , mdlv c, nme_dtl e " + 
          "where a.mstk_idn = b.idn and c.idn = a.idn and a.stt in ('DLV','EI') " + 
          "and c.typ in ('DLV') and c.stt='IS' " + 
          "and e.mprp = 'POST_SHIPMENT' and e.txt='Y' " + 
          "and (c.nme_idn = e.nme_idn or c.inv_nme_idn = e.nme_idn) and b.vnm in  ("+vnmLst+")" ;
          
            ArrayList  outLst = db.execSqlLst(" memo pkts", sqlDlv, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet  rs = (ResultSet)outLst.get(1);
          while (rs.next()) {
              PktDtl pkt    = new PktDtl();
              long   pktIdn = rs.getLong("mstk_idn");

              pkt.setPktIdn(pktIdn);
              pkt.setSaleId((rs.getString("idn")));
              pkt.setRapRte(util.nvl(rs.getString("rap_rte")));
              pkt.setQty(util.nvl(rs.getString("qty")));
              pkt.setCts(util.nvl(rs.getString("cts")));
              pkt.setRte(util.nvl(rs.getString("rte")));
              pkt.setMemoQuot(util.nvl(rs.getString("memoQuot")));
              pkt.setByrRte(util.nvl(rs.getString("quot")));
              pkt.setFnlRte(util.nvl(rs.getString("fnl_sal")));
              pkt.setDis(rs.getString("dis"));
              pkt.setByrDis(rs.getString("mDis"));
              pkt.setVnm(util.nvl(rs.getString("vnm")));
           
            
                 
              String vnm = util.nvl(rs.getString("vnm"));
              String tfl3 = util.nvl(rs.getString("tfl3"));
              if(!vnmLst.equals("")){
                  if(vnmLst.indexOf(tfl3)!=-1 && !tfl3.equals("")){
                      if(vnmLst.indexOf("'")==-1)
                          vnmLst =  vnmLst.replaceAll(tfl3,"");
                      else
                          vnmLst =  vnmLst.replaceAll("'"+tfl3+"'", "");
                  } else if(vnmLst.indexOf(vnm)!=-1 && !vnm.equals("")){
                      if(vnmLst.indexOf("'")==-1)
                          vnmLst =  vnmLst.replaceAll(vnm,"");
                      else
                          vnmLst =  vnmLst.replaceAll("'"+vnm+"'", "");
                  }  
              }
                 
                 
              String getPktPrp = " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
                                 + " from stk_dtl a, mprp b, rep_prp c "
                                 + " where a.mprp = b.prp and a.grp=1 and b.prp = c.prp and c.mdl = 'MEMO_RTRN' and a.mstk_idn = ? "
                                 + " order by c.rnk, c.srt ";

              params = new ArrayList();
              params.add(Long.toString(pktIdn));

            ArrayList  outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, params);
            PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
            ResultSet  rs1 = (ResultSet)outLst1.get(1);

              while (rs1.next()) {
                  String lPrp = rs1.getString("mprp");
                  String lVal = rs1.getString("val");

                  pkt.setValue(lPrp, lVal);
              }
              rs1.close();
              pst1.close();
              pkts.add(pkt);
          }
              rs.close();
              pst.close();
          }
          if(!vnmLst.equals("")){
              vnmLst = util.pktNotFound(vnmLst);
              request.setAttribute("vnmNotFnd", vnmLst);
          }
          
          session.setAttribute("PKTS",pkts);
          request.setAttribute("VIEW", "Y");
          util.updAccessLog(request,response,"Post Shipment", "Post Shipment view");
       return am.findForward("load");
        }
    }
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
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
        rtnPg=init(request,response,session,util);
        }else{
        rtnPg="connExists";   
        }
        }else
        rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
       PostShipmentForm form = (PostShipmentForm)af;
       ArrayList  pkts = (ArrayList)session.getAttribute("PKTS");    // udf.getPkts();
       for (int i = 0; i < pkts.size(); i++) {
            PktDtl pkt     = (PktDtl) pkts.get(i);
            long   lPktIdn = pkt.getPktIdn();
            String isCheckecd = util.nvl((String)form.getValue(String.valueOf(lPktIdn)));
           if(isCheckecd.equals("yes")){
            String dlvIdn = pkt.getSaleId();
            String updatedlvDtl ="update dlv_dtl set dte_app = sysdate , stt='PS' where idn = ?  and mstk_idn = ?";
           ArrayList ary = new ArrayList();
            ary.add(dlvIdn);
            ary.add(String.valueOf(lPktIdn));
           int ct = db.execUpd("update dlv_dtl", updatedlvDtl, ary);
           if(ct>0)
               request.setAttribute("msg", "process done successfully... ");
           }}
        return am.findForward("load");
        }
    }
    public PostShipmentAction() {
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
                util.updAccessLog(req,res,"Post Shipment", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Post Shipment", "init");
            }
            }
            return rtnPg;
            }
    
}
