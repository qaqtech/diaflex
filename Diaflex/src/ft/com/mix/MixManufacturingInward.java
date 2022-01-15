package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MixManufacturingInward  extends DispatchAction {

    public ActionForward load(ActionMapping am, ActionForm form,
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
            MixManufacturingInwardform udf = (MixManufacturingInwardform)form;
            ASPrprViw(req, res, "MIX_IN");
            udf.resetAll();
            return am.findForward("load");   
        }
        }
    public ActionForward save(ActionMapping am, ActionForm form,
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
            MixManufacturingInwardform udf = (MixManufacturingInwardform)form;
           String pktCnt = util.nvl(req.getParameter("pkt_cnt"),"0");
            ArrayList asViewPrp = (ArrayList)session.getAttribute("MIX_IN");
            String pktMsg="";
            int npkt = Integer.parseInt(pktCnt);
            for(int i=1;i<=npkt;i++){
                String cts = util.nvl(req.getParameter("CRTWT_"+i));
                String qty = util.nvl(req.getParameter("qty_"+i));
                String lotNo = util.nvl(req.getParameter("LOTNO_"+i));
                String rte = util.nvl(req.getParameter("RTE_"+i));
                ArrayList params = new ArrayList();
                params.add(lotNo);
               String lotNoGen = "DP_GEN_LOT(pUsrLot => ? ,pLot => ?, pLotIdn => ?, pMsg => ?)";
                ArrayList out = new ArrayList();
                out.add("V");
                out.add("I");
                out.add("V");
              CallableStatement cst = db.execCall("lotNoGen",lotNoGen, params, out);
              int lotIdn = cst.getInt(params.size()+2);
              cst.close();
              cst=null;
                if(lotIdn > 0){
                    if(!cts.equals("")){
                    params = new ArrayList();
                    params.add(String.valueOf(lotIdn));
                    params.add(lotNo);
                    params.add("MIX");
                    params.add("MF_IN");
                    params.add(qty);
                    out = new ArrayList();
                    out.add("I");
                    out.add("V");
                    String genPkt = "DP_GEN_PKT(pLotIdn => ? ,pLotDsc  => ?, pPktTy  => ? " + 
                    "  ,  pStt  => ?, pQty => ?  \n" + 
                    "  , lStkIdn => ? , lVnm  => ? )";
                    cst = db.execCall("genPkt",genPkt, params, out);
                    long lStkIdn = cst.getLong(params.size()+1);
                    String vnm = cst.getString(params.size()+1);
                    if(lStkIdn>0){
                    for(int j=0;j<asViewPrp.size();j++){
                        String lprp = (String)asViewPrp.get(j);
                        String lprpVal = util.nvl(req.getParameter(lprp+"_"+i));
                        String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ?)";
                         ArrayList ary = new ArrayList();
                         ary.add(String.valueOf(lStkIdn));
                         ary.add(lprp);
                         ary.add(lprpVal);
                        int ct = db.execCall("stockUpd",stockUpd, ary);
                     }
                    
                        String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> to_char(sysdate, 'dd-MON-rrrr'))";
                         ArrayList ary = new ArrayList();
                         ary.add(String.valueOf(lStkIdn));
                         ary.add("RECPT_DT");
                        int ct = db.execCall("stockUpd",stockUpd, ary);
                        
                        stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> get_stk_ctg(?))";
                        ary = new ArrayList();
                        ary.add(String.valueOf(lStkIdn));
                        ary.add("STK_CTG");
                        ary.add(String.valueOf(lStkIdn));
                         ct = db.execCall("stockUpd",stockUpd, ary);
                        
                      ary = new ArrayList();
                     ary.add(String.valueOf(lStkIdn));
                      ct = db.execCall("apply_rtn_prp", "STK_SRT(?)", ary);
                     pktMsg=pktMsg+","+vnm;
                    
                    }
                  
                    String insertSql= "INSERT INTO MIX_TRF_LOG(LOG_DTE, FRM_IDN, TO_IDN, QTY, CTS,UPR,TYP,RMK,UNM)"+
                                       "VALUES(SYSDATE, ? ,?, ?, ?,?,?,?, PACK_VAR.GET_USR)";
                    
                    ArrayList ary = new ArrayList();
                     ary.add("0");
                     ary.add(String.valueOf(lStkIdn));
                     ary.add(qty);
                     ary.add(cts);
                     ary.add(rte);
                     ary.add("Mix Inward");
                     ary.add("Mix Inward");
                    int ct = db.execUpd("log insert", insertSql, ary);
                }
                }
            }
            if(pktMsg.length()>1)
                req.setAttribute("msg", "New Packets Added :"+pktMsg);
            else
                req.setAttribute("msg", "Error in process");

            return am.findForward("load");   
        }
        }
    public ActionForward LotIdnGen(ActionMapping mapping, ActionForm form,
                               HttpServletRequest req,
                               HttpServletResponse response) throws Exception {
           HttpSession session = req.getSession(false);
           InfoMgr info = (InfoMgr)session.getAttribute("info");
           DBUtil util = new DBUtil();
           DBMgr db = new DBMgr(); 
           db.setCon(info.getCon());
           util.setDb(db);
           util.setInfo(info);
           db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
           util.setLogApplNm(info.getLoApplNm());
       String usrLot = util.nvl(req.getParameter("usrLot"));
           String lotNoGen = "DP_GEN_LOT(pLot => ?, pLotIdn => ?, pMsg => ?)";
       ArrayList params = new ArrayList();
       if(!usrLot.equals("")){
          params.add(usrLot);
           lotNoGen = "DP_GEN_LOT(pUsrLot => ? ,pLot => ?, pLotIdn => ?, pMsg => ?)";
       }
       ArrayList out = new ArrayList();
       out.add("V");
       out.add("I");
       out.add("V");
     
       
       CallableStatement cst = db.execCall("lotNoGen",lotNoGen, params, out);
       String lotNo = cst.getString(params.size()+1);
       int lotIdn = cst.getInt(params.size()+2);
         cst.close();
         cst=null;
       response.setContentType("text/xml"); 
       response.setHeader("Cache-Control", "no-cache"); 
       response.getWriter().write("<lots><lot><lotno>"+lotNo+"</lotno><lotIdn>"+lotIdn+"</lotIdn></lot></lots>");
       return null;
       }
    
    public ArrayList ASPrprViw(HttpServletRequest req , HttpServletResponse res,String mdl){
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      ArrayList asViewPrp = (ArrayList)session.getAttribute(mdl);
      if(info!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
       init(req,res,session,util);
       
        try {
            if (asViewPrp == null) {

                asViewPrp = new ArrayList();
                ArrayList ary = new ArrayList();
                ary.add(mdl);
          
              ArrayList  rsLst = db.execSqlLst(" Vw Lst ", "Select prp  from rep_prp where mdl = ? and flg='Y' order by rnk ",
                          ary);
              PreparedStatement  stmt =(PreparedStatement)rsLst.get(0);
              ResultSet  rs1 =(ResultSet)rsLst.get(1);
                while (rs1.next()) {

                    asViewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                stmt.close();
                session.setAttribute(mdl, asViewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
      }
        return asViewPrp;
    }
    public MixManufacturingInward() {
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
                util.updAccessLog(req,res,"Mix Marketing Transfer", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Mix Marketing Transfer", "init");
            }
            }
            return rtnPg;
            }
}
