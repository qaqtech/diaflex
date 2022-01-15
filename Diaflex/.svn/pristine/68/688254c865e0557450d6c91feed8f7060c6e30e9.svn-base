package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.ByrDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Enumeration;

import java.util.HashMap;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class SmxSaleReturnAction  extends DispatchAction {
    public SmxSaleReturnAction() {
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
            SmxSaleReturnForm udf = (SmxSaleReturnForm) af;
            udf.resetAll();
            util.updAccessLog(req,res,"smxSale Return", "load");
            ArrayList byrList = new ArrayList();
            String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
            String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
            String usrFlg=util.nvl((String)info.getUsrFlg());
            ArrayList rolenmLst=(ArrayList)info.getRoleLst();
            String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
            String conQ="";
            ArrayList ary=new ArrayList();
            if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
            }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
            conQ +=" and nvl(n.grp_nme_idn,0) =? "; 
            ary.add(dfgrpnmeidn);
            } 
            if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                    if(util.nvl((String)info.getDmbsInfoLst().get("EMP_LOCK")).equals("Y")){
                        conQ += " and (n.emp_idn= ? or n.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
                        ary.add(dfNmeIdn);
                        ary.add(dfNmeIdn);
                    }
            }
            
            String    getByr  =
                "With JAN_IS_PNDG_V as (\n" + 
                "select distinct c.nme_idn\n" + 
                "from mstk a, jandtl b , mjan c \n" + 
                "Where a.idn = b.mstk_idn and a.pkt_ty in ('SMX') and c.typ like '%AP' and b.stt in ('SL','LS') And b.idn=c.idn\n" + 
                ")\n" + 
                "select n.nme_idn,n.nme byr\n" + 
                "from nme_v n, jan_is_pndg_v j\n" + 
                "where n.nme_idn = j.nme_idn \n" +conQ +
                "order by 2";

              ArrayList  outLst = db.execSqlLst("byr", getByr, ary);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet  rs = (ResultSet)outLst.get(1);

            while (rs.next()) {
                ByrDao byr = new ByrDao();

                byr.setByrIdn(rs.getInt("nme_idn"));
                byr.setByrNme(rs.getString("byr"));
                byrList.add(byr);
            }
            rs.close();
                pst.close();
            udf.setByrLst(byrList);
            util.updAccessLog(req,res,"smxSale Return", "End");
                finalizeObject(db, util);
            return am.findForward("load");
            }
        }
    
    public ActionForward View(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        util.updAccessLog(req,res,"Smx Return ", "Memo Return load pkts");
            SmxSaleReturnForm udf = (SmxSaleReturnForm) af;
            ArrayList memoIdnLst=new ArrayList();
            String outMsg="";
            Enumeration reqNme = req.getParameterNames();
            while (reqNme.hasMoreElements()) {
                String paramNm = (String) reqNme.nextElement();

                if (paramNm.indexOf("cb_memo") > -1) {
                    String val = req.getParameter(paramNm);
                     memoIdnLst.add(val);
                 }
            }
            if(memoIdnLst.size()==0){
                String memoIdn = util.nvl((String)udf.getValue("memoIdn"));
                memoIdnLst.add(memoIdn);
            }
            if(memoIdnLst.size() > 0){
                try {
               
                    String idnPcs = memoIdnLst.toString();
                    idnPcs = idnPcs.replace('[','(');
                    idnPcs = idnPcs.replace(']',')'); 
                    String mstkPtkRtStr ="";
                    ArrayList summyPktDtl = new ArrayList();
                    String summaryDtl = 
                    " select c.idn, to_char(c.dte, 'dd-Mon-rrrr') dte, bx.val boxTyp , bi.val boxId , b.qty , b.cts ,nvl(fnl_sal,quot) rte, a.idn pkt_rt from mstk a , jandtl b , mjan c , stk_dtl bx,stk_dtl bi\n" + 
                    " where a.idn=b.mstk_idn and b.idn=c.idn and a.pkt_ty in ('SMX') and b.stt='SL' \n" + 
                    " and a.idn=bx.mstk_idn and bx.mprp='BOX_TYP' and bx.grp=1\n" + 
                    " and a.idn = bi.mstk_idn and bi.mprp='BOX_ID' and bi.grp=1  and c.idn in "+idnPcs;
                    ArrayList outLst = db.execSqlLst("prp List", summaryDtl, new ArrayList());
                    PreparedStatement pst = (PreparedStatement)outLst.get(0);
                    ResultSet rs = (ResultSet)outLst.get(1);
                    while(rs.next()){
                        HashMap pktDtl = new HashMap();
                        pktDtl.put("BOXTYP", rs.getString("boxTyp"));
                        pktDtl.put("BOXID", rs.getString("boxId"));
                        pktDtl.put("QTY", rs.getString("qty"));
                        pktDtl.put("CTS", rs.getString("cts"));
                        pktDtl.put("RTE", rs.getString("rte"));
                        pktDtl.put("DTE", rs.getString("dte"));
                       String pkt_rt = rs.getString("pkt_rt");
                       mstkPtkRtStr=mstkPtkRtStr+","+pkt_rt;
                        summyPktDtl.add(pktDtl);
                    }
                    
                    rs.close();
                    pst.close();
                    if(!mstkPtkRtStr.equals("")){
                    mstkPtkRtStr = mstkPtkRtStr.replaceFirst(",", "");
                    ArrayList pktDtlList = new ArrayList();
                    String rootPktDtl = "select a.idn,c.idn stk_idn, to_char(a.dte, 'dd-Mon-rrrr') dte,c.vnm, bx.val boxTyp, bi.val boxId ,  b.qty , b.cts ,nvl(fnl_sal,quot) rte from mjan a , jandtl b, mstk c,\n" + 
                    "stk_dtl bx,stk_dtl bi\n" + 
                    "where a.idn=b.idn and b.mstk_idn=c.idn\n" + 
                    "and c.idn=bx.mstk_idn and bx.mprp='BOX_TYP' and bx.grp=1\n" + 
                    " and c.idn = bi.mstk_idn and bi.mprp='BOX_ID' and bi.grp=1\n" + 
                    " and c.pkt_rt in ("+mstkPtkRtStr+")\n" + 
                    " and b.stt='MRG'";
                    outLst = db.execSqlLst("rootPktDtl", rootPktDtl, new ArrayList());
                    pst = (PreparedStatement)outLst.get(0);
                    rs = (ResultSet)outLst.get(1);
                    while(rs.next()){
                        HashMap pktDtl = new HashMap();
                        pktDtl.put("BOXTYP", rs.getString("boxTyp"));
                        pktDtl.put("BOXID", rs.getString("boxId"));
                        pktDtl.put("QTY", rs.getString("qty"));
                        pktDtl.put("CTS", rs.getString("cts"));
                        pktDtl.put("RTE", rs.getString("rte"));
                        pktDtl.put("DTE", rs.getString("dte"));
                        pktDtl.put("IDN", rs.getString("idn"));
                        pktDtl.put("STKIDN", rs.getString("stk_idn"));
                        pktDtl.put("VNM", rs.getString("vnm"));
                        pktDtlList.add(pktDtl);
                    }
                    rs.close();
                    pst.close();
                   req.setAttribute("PktDtlList", pktDtlList);
                  req.setAttribute("SUMMRYPKTDTL", summyPktDtl);
                   
                    }else{
                        outMsg="Error in Process";
                    }
                    req.setAttribute("VIEW", "Y");
                
                } catch (SQLException sqle) {
                    // TODO: Add catch code
                    sqle.printStackTrace();
                    outMsg="Error in Process";
                 
                
                } finally {
                    
                }
                
            }else{
                outMsg="Memo Idn not found..";
            }
           
            return am.findForward("load"); 
        }
     }
    
    public ActionForward Save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        util.updAccessLog(req,res,"Smx Return ", "save");
            SmxSaleReturnForm udf = (SmxSaleReturnForm) af;
            ArrayList mstkIdnLst = new ArrayList();
            HashMap values = (HashMap)udf.getValues();
            Set<String> keys = values.keySet();
                for(String key: keys){
                    if(key.indexOf("STT_")!=-1){
                        String val = (String)values.get(key);
                        if(val.equals("RT")){
                            String[] valLst=key.split("_");
                            String mstkIdn = valLst[1];
                            mstkIdnLst.add(mstkIdn);
                        }
                    }
                }
            String outMsg = "";
            String rtnPktIdn="";
            if(mstkIdnLst!=null && mstkIdnLst.size()>0){
                for(int i=0;i<mstkIdnLst.size();i++){
                    String mstkIdn = (String)mstkIdnLst.get(i);
                    ArrayList params = new ArrayList();
                    params.add(mstkIdn);
                    int ct = db.execCall("SMX Rtn", "MIX_MEMO_PKG.SMX_RTN(pStkIdn => ?)", params);
                   if(ct>0)
                       rtnPktIdn = rtnPktIdn+","+mstkIdn;
                }
            }else{
                outMsg="Sorry there are no packets in return..";
            }
            rtnPktIdn = rtnPktIdn.replaceFirst(",", "");
            if(rtnPktIdn.length()>0)
                outMsg="Packets Return Successfully :-"+rtnPktIdn;
            udf.reset();
            req.setAttribute("msg", outMsg);
            return am.findForward("load");   
        }
            }
    public void finalizeObject(DBMgr db, DBUtil util){
        try {
            db=null;
            util=null;
        } catch (Throwable t) {
            // TODO: Add catch code
            t.printStackTrace();
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
                util.updAccessLog(req,res,"Memo Return", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Memo Return", "init");
            }
            }
            return rtnPg;
            }
}
