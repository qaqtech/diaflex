package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ConsignmentReceiveAction  extends DispatchAction {
    public ConsignmentReceiveAction() {
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
            ConsignmentReceiveForm udf;
            udf = (ConsignmentReceiveForm)af;
           util.updAccessLog(req,res,"Branch Received", "load");
           udf.resetALL();
           dataCollection(req, new HashMap());
           util.updAccessLog(req,res,"Branch Received", "End");
        }
          finalizeObject(db, util);

        return am.findForward("load");
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
            ConsignmentReceiveForm udf;
            udf = (ConsignmentReceiveForm)af;
            util.updAccessLog(req,res,"Branch Received", "Fetch");
            HashMap params = new HashMap();
           
           dataCollection(req, params);
          udf.reset();
            util.updAccessLog(req,res,"Branch Received", "End");
          util.setMdl("Branch Received");
        }
          finalizeObject(db, util);

        return am.findForward("load");
      }
    
 
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            ConsignmentReceiveForm udf;
            udf = (ConsignmentReceiveForm)af;
            Enumeration reqNme = req.getParameterNames();
           String memoIds = "";
            while (reqNme.hasMoreElements()) {
                String paramNm = (String) reqNme.nextElement();

                if (paramNm.indexOf("cb_memo") > -1) {
                    String val = req.getParameter(paramNm);
                   memoIds = memoIds + "," + val;
                    
                }
            }
            ArrayList msgLst = new ArrayList(); 
            try {
              
              db.setAutoCommit(false);
                memoIds = memoIds.replaceFirst(",", "");
                String updateMjan = "update mjan set stt='IS' where idn in (" + memoIds + ")";

                int ct = db.execUpd("update mjan", updateMjan, new ArrayList());
                if (ct > 0){
                  ArrayList  outLst = db.execSqlLst("mjan fetch", "select idn, mstk_idn from jandtl where idn in (" + memoIds + ") ", new ArrayList());
      
                  PreparedStatement pst = (PreparedStatement)outLst.get(0);
                  ResultSet rs = (ResultSet)outLst.get(1);
                 
                    while(rs.next()){
                    String idn= rs.getString("idn");
                    String stkIdn = rs.getString("mstk_idn");
                    ArrayList ary = new ArrayList();
                        ary.add(stkIdn);
                        ary.add(idn);
                          ArrayList out = new ArrayList();
                        out.add("I");
                        out.add("V");
                   CallableStatement cts = db.execCall("DP_GEN_BRANCH_PKT", "DP_GEN_BRANCH_PKT(pStkIdn => ?,  pRefIdn => ?,pBrcStkIdn => ?, pMsg => ?)",ary,out);
                    String msg = cts.getString(ary.size()+2);
                        msgLst.add(msg);
                        cts.close();
                        cts=null;
                    }
                    rs.close();
                    pst.close();
                }else
                    req.setAttribute("msg", "Error in process");
                
              
                
               
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
                req.setAttribute("msg", "Error in process");
                db.doRollBack();
                }finally{
                    db.doCommit();
                    db.setAutoCommit(true);
                }
            HashMap params = new HashMap();
            
            dataCollection(req, params);
            if(msgLst.size()>0)
                req.setAttribute("msgList", msgLst);
        }
         finalizeObject(db, util);
       

         return am.findForward("load");   
     }
    public void dataCollection(HttpServletRequest req, HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
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
        
        String sql = "With CON_RECIVE_V as (\n" + 
        "select c.nme_idn,c.idn,to_char(c.dte,'dd-mm-yyyy') dte,byr.get_nm(c.nme_idn) byrNme, sum(a.qty) qty,sum(a.cts) cts,trunc(sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)), 2) vlu,\n" + 
        "trunc(((sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts,2)* greatest(b.rap_rte,1) ))*100) - 100, 2) avg_dis ,\n" + 
        "trunc(((sum(trunc(a.cts,2)*nvl(a.fnl_sal, a.quot)) / sum(trunc(a.cts,2))))) avg_Rte\n" + 
        "from jandtl a, mstk b  , mjan c\n" + 
        "where a.mstk_idn = b.idn and a.stt = 'IS' and b.pkt_ty in ('NR') and c.idn = a.idn and c.stt='PND' \n" + 
        "group by c.nme_idn,c.idn,byr.get_nm(c.nme_idn),c.dte \n" + 
        ")\n" + 
        "select j.idn,j.dte,j.byrnme,j.qty,j.cts,j.vlu,j.avg_dis,j.avg_rte\n" + 
        "from nme_v n, con_recive_v j\n" + 
        "where n.nme_idn = j.nme_idn \n" + conQ+
        "order by 2";
       ArrayList pktDtlLst = new ArrayList();
       
           
      ArrayList  outLst = db.execSqlLst("getDate", sql, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
       try {
              while (rs.next()) {
                  HashMap pktDtl = new HashMap();
                  pktDtl.put("IDN", rs.getString("idn"));
                  pktDtl.put("DTE", rs.getString("dte"));
                  pktDtl.put("BYR", rs.getString("byrNme"));
                  pktDtl.put("QTY", rs.getString("qty"));
                  pktDtl.put("CTS", rs.getString("cts"));
                  pktDtl.put("VLU", rs.getString("vlu"));
                  pktDtl.put("AVGDIS", rs.getString("avg_dis"));
                  pktDtl.put("AVGRTE", rs.getString("avg_rte"));
                  pktDtlLst.add(pktDtl);
                 
              }
              rs.close();
           pst.close();
          } catch (SQLException sqle) {
              // TODO: Add catch code
              sqle.printStackTrace();
          }
        req.setAttribute("pktDtlList", pktDtlLst);
        finalizeObject(db, util);

    }
    public ActionForward pktDtlExcel(ActionMapping am, ActionForm form,
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
            util.updAccessLog(req,res,"Consignment Report", "pktDtlExcel start");
            GenericInterface genericInt = new GenericImpl();
        ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"DAILY_VW","DAILY_VW");
            ConsignmentReceiveForm udf = (ConsignmentReceiveForm)form;
        String memoid = util.nvl(req.getParameter("memoid"));
        String dte = util.nvl(req.getParameter("dte"));
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        ArrayList ary = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        itemHdr.add("MEMOID");
        itemHdr.add("VNM");
        itemHdr.add("BYR");
        itemHdr.add("COUNTRY");
            for(int i=0;i<vwprpLst.size();i++){
                String prp=util.nvl((String)vwprpLst.get(i));
                if(prpDspBlocked.contains(prp)){
                }else if(!itemHdr.contains(prp)){
                itemHdr.add(prp);
                if(prp.equals("RTE")){
                    itemHdr.add("SALE RTE");
                    itemHdr.add("AMOUNT");
                 }
                 if(prp.equals("RAP_RTE")){
                        itemHdr.add("RAPVLU");
                 }
                }              
            }
        memoid=memoid.replaceFirst(",", "");
        dte=dte.replaceFirst(",", "");
        String[] memoidnLst=memoid.split(",");
        String[] dteLst=dte.split(",");
        System.out.println(memoid);
            String mprpStr = "";
            String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||Upper(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1')) str From Rep_Prp Rp Where rp.MDL = ? order by srt " ;
            ary = new ArrayList();
            ary.add("DAILY_VW");
          ArrayList  outLst = db.execSqlLst("mprp ", mdlPrpsQ , ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()) {
            String val = util.nvl((String)rs.getString("str"));
            mprpStr = mprpStr +" "+val;
            }
            rs.close();
            pst.close();
        for(int i=0;i<memoidnLst.length;i++){
        memoid=memoidnLst[i];  
        dte=dteLst[i];  
       String dtefrom = " trunc(sysdate) ";
       String dteto = " trunc(sysdate) ";
        if(!memoid.equals("")){
             // dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
             // dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
         String pktDtlSql = " with  STKDTL as  ( Select c.sk1,a.idn memoid,c.idn,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , mprp,to_char(b.cts,'99999999990.99') cts,\n" + 
        " DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,nvl(c.upr,c.cmp) rate,\n" + 
        " c.rap_rte raprte,byr.get_nm(nvl(d.emp_idn,0)) sale_name,byr.get_nm(nvl(d.nme_idn,0)) byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu,\n" + 
        " to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis  \n" + 
        " from stk_dtl st,mjan a,JAN_PKT_DTL_V b, mstk c,mnme d\n" + 
        " where st.mstk_idn=c.idn and a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn and a.stt='PND'\n" + 
        " And a.idn=?\n" +
            //  " and trunc(b.dte)  between "+dtefrom+" and "+dteto +"\n "+
            "and exists (select 1 from rep_prp rp where rp.MDL = 'DAILY_VW' and st.mprp = rp.prp)  And st.Grp = 1) " +
            " Select * from stkDtl PIVOT " +
            " ( max(atr) " +
            " for mprp in ( "+mprpStr+" ) ) order by 1 " ;
        ary = new ArrayList();
        ary.add(memoid);
       
         outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
          pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap pktDtl = new HashMap();
            pktDtl.put("MEMOID", util.nvl(rs.getString("memoid")));
            pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
            pktDtl.put("BYR", util.nvl(rs.getString("byr")));
            pktDtl.put("COUNTRY", util.nvl(rs.getString("country")));
            pktDtl.put("RTE", util.nvl(rs.getString("rate")));
            pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
            pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));           
            for (int v = 0; v < vwprpLst.size(); v++) {
            String vwPrp = (String)vwprpLst.get(v);
            String fldName = vwPrp;
            if(vwPrp.toUpperCase().equals("H&A"))
            fldName = "H_A";
            
            if(vwPrp.toUpperCase().equals("COMMENT"))
            fldName = "COM1";
            
            if(vwPrp.toUpperCase().equals("REMARKS"))
            fldName = "REM1";
            
            if(vwPrp.toUpperCase().equals("COL-DESC"))
            fldName = "COL_DESC";
            
            if(vwPrp.toUpperCase().equals("COL-SHADE"))
            fldName = "COL_SHADE";
            
            if(vwPrp.toUpperCase().equals("FL-SHADE"))
            fldName = "FL_SHADE";
            
            if(vwPrp.toUpperCase().equals("STK-CTG"))
            fldName = "STK_CTG";
            
            if(vwPrp.toUpperCase().equals("STK-CODE"))
            fldName = "STK_CODE";
            
            if(vwPrp.toUpperCase().equals("SUBSIZE"))
            fldName = "SUBSIZE1";
            
            if(vwPrp.toUpperCase().equals("SIZE"))
            fldName = "SIZE1";
            
            if(vwPrp.toUpperCase().equals("MIX_SIZE"))
            fldName = "MIX_SIZE1";
            
            if(vwPrp.toUpperCase().equals("STK-CTG"))
            fldName = "STK_CTG";
            
            if(vwPrp.toUpperCase().equals("CRN-OP"))
            fldName = "CRN_OP";
            
            if(vwPrp.toUpperCase().equals("CRTWT"))
            fldName = "cts";
            
            if(vwPrp.toUpperCase().equals("RAP_DIS"))
            fldName = "r_dis";
            
            if(vwPrp.toUpperCase().equals("RTE"))
            fldName = "rate";
            
            if (vwPrp.toUpperCase().equals("RAP_RTE"))
            fldName = "raprte";
                if(vwPrp.toUpperCase().equals("MEM_COMMENT"))
                fldName = "MEM_COM1";
            String fldVal = util.nvl((String)rs.getString(fldName));
            
            pktDtl.put(vwPrp, fldVal);
            }
            pktDtl.put("RTE",util.nvl(rs.getString("rate")));
            pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
            pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
            pktDtl.put("RAP_RTE",util.nvl(rs.getString("raprte")));
            pktDtl.put("RAPVLU",util.nvl(rs.getString("rapvlu")));
            pktDtlList.add(pktDtl);
        }
            rs.close();
            pst.close();
            
        }
        }
        session.setAttribute("pktList", pktDtlList);
        session.setAttribute("itemHdr",itemHdr);
            util.updAccessLog(req,res,"Consignment Report", "pktDtlExcel end");
        return am.findForward("pktDtl");  
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
                util.updAccessLog(req,res,"Branch Received", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Branch Received", "End");
            }
            }
            return rtnPg;
            }
}
