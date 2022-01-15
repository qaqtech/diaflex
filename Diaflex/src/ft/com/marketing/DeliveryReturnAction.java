package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.ByrDao;

import ft.com.dao.MAddr;
import ft.com.dao.PktDtl;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class DeliveryReturnAction extends DispatchAction {
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
           util.updAccessLog(req,res,"Delivery Return", "Delivery Return load");
        DeliveryReturnForm udf = (DeliveryReturnForm)af;
        String restrict_sale_rtn_days = util.nvl((String)info.getDmbsInfoLst().get("RESTRICT_SALE_RTN_DAYS"),"30");
        ArrayList byrList = new ArrayList();
        String    getByr  = "select  distinct dv.byr , dv.byr_idn from  dlv_pndg_v dv where exists (select 1 from mdlv md where dv.byr_idn=md.nme_idn and trunc(md.dte)>=trunc(sysdate)-"+restrict_sale_rtn_days+")  order by dv.byr";
        ArrayList  outLst = db.execSqlLst("byr", getByr, new ArrayList());
         PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            ByrDao byr = new ByrDao();
            byr.setByrIdn(rs.getInt("byr_idn"));
            byr.setByrNme(rs.getString("byr"));
            byrList.add(byr);
        }
         rs.close();
             pst.close();
         udf.setByrLst(byrList);
         
         HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
         HashMap pageDtl=(HashMap)allPageDtl.get("DELIVERY_RETURN");
         if(pageDtl==null || pageDtl.size()==0){
         pageDtl=new HashMap();
         pageDtl=util.pagedef("DELIVERY_RETURN");
         allPageDtl.put("DELIVERY_RETURN",pageDtl);
         }
         info.setPageDetails(allPageDtl);
           util.updAccessLog(req,res,"Delivery Return", "Delivery Return load done");
             finalizeObject(db, util);
         return am.findForward("load");
         }
     }
    
    public ActionForward loadPkt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"Delivery Return", "Delivery Return load pkts");
        DeliveryReturnForm udf = (DeliveryReturnForm) af;
        ArrayList pkts        = new ArrayList();
        String    flnByr      = util.nvl((String)udf.getValue("prtyIdn"));
        String    byrId  = util.nvl((String)udf.getValue("byrIdn"));;
        String    saleId      = "";
        String    vnmLst      = util.nvl((String)udf.getValue("vnmLst"));
        ArrayList vwprpLst = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
        String restrict_sale_rtn_days = util.nvl((String)info.getDmbsInfoLst().get("RESTRICT_SALE_RTN_DAYS"),"30");
        ResultSet rs          = null;
        ArrayList    params      = null;
        Enumeration reqNme = req.getParameterNames();
        saleId=util.nvl(req.getParameter("saleId"));
        while (reqNme.hasMoreElements()) {
            String paramNm = (String) reqNme.nextElement();
           if (paramNm.indexOf("cb_memo") > -1) {
                String val = req.getParameter(paramNm);

                if (saleId.equals("")) {
                    saleId = val;
                } else {
                   saleId = saleId + "," + val;
                }
            }
        }
        double exh_rte=1;
        String    getMemoInfo =
            "select nme_idn, nmerel_idn,byr.get_trms(nmerel_idn) trms, byr.get_nm(nme_idn) byr, stt, inv_nme_idn , fnl_trms_idn , inv_addr_idn , exh_rte exhRte, "
            + " typ, qty, cts, vlu, to_char(dte, 'dd-Mon-rrrr HH24:mi:ss') dte from mdlv where  typ in ('DLV') and stt='IS'  and trunc(dte)>=trunc(sysdate)-"+restrict_sale_rtn_days+" ";
        params = new ArrayList();
        if(!flnByr.equals("") && !flnByr.equals("0") ){
        getMemoInfo = getMemoInfo+" and inv_nme_idn =?  " ;
        params.add(flnByr);
        }
        if(!saleId.equals("")){
        getMemoInfo = getMemoInfo+" and idn in ("+saleId+")  " ;

        }

          ArrayList  outLst = db.execSqlLst(" Memo Info", getMemoInfo, params);
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet mrs = (ResultSet)outLst.get(1);
        if (mrs.next()) {
            udf.setValue("nmeIdn",mrs.getInt("nme_idn"));
            udf.setValue("nmerelIdn",mrs.getInt("nme_idn"));
            exh_rte = Double.parseDouble(util.nvl(mrs.getString("exhRte"),"1"));
        }
        mrs.close();
            pst.close();
            String mprpStr = "";
            String mdlPrpsQ = "Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||\n" + 
            "Upper(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT')) \n" + 
            "str From Rep_Prp Rp Where rp.MDL = ? order by srt " ;
            ArrayList ary = new ArrayList();
            ary.add("MEMO_RTRN");
           outLst = db.execSqlLst("mprp ", mdlPrpsQ , ary);
            pst = (PreparedStatement)outLst.get(0);
             mrs = (ResultSet)outLst.get(1);
            while(mrs.next()) {
            String val = util.nvl((String)mrs.getString("str"));
            mprpStr = mprpStr +" "+val;
            }
            mrs.close();
            pst.close();
        String getPktData= " with STKDTL as " +
                    " ( Select b.sk1,b.cert_no certno,a.idn , a.qty ,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , st.mprp, a.mstk_idn, nvl(fnl_sal, quot) memoQuot, quot, fnl_sal,a.rmk "+
              " , a.cts, nvl(b.upr, b.cmp) rate, b.rap_rte raprte, a.stt,to_char(trunc(a.cts,2) * b.rap_rte, '99999999990.00') rapVlu, DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), b.vnm) vnm "+
             " , to_char(trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2),9990.99) dis, to_char(decode(b.rap_rte, 1, '',trunc(((nvl(fnl_sal, quot)/"+exh_rte+"/greatest(b.rap_rte,1))*100)-100,2)),9990.99) mDis "+
             " from stk_dtl st,dlv_dtl a, mstk b , mdlv c where st.mstk_idn=b.idn and a.mstk_idn = b.idn and c.idn = a.idn and a.stt in ('DLV','IS','PD') and c.typ in ('DLV') and c.stt='IS' and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA'))" ;
      
         params = new ArrayList();
        if(!flnByr.equals("") && !flnByr.equals("0")){
        getPktData = getPktData+" and c.inv_nme_idn =?  " ;
        params.add(flnByr);
        }
        if(!saleId.equals("")){
        getPktData = getPktData+" and c.idn in ("+saleId+")  " ;
      
        }
        if(!vnmLst.equals("")){
        vnmLst = util.getVnm(vnmLst);
        getPktData = getPktData + " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+") ) ";
        }
        getPktData = getPktData +" \n"+
        " and trunc(a.dte)>=trunc(sysdate)-"+restrict_sale_rtn_days+"\n"+
            " and exists (select 1 from rep_prp rp where rp.MDL = 'MEMO_RTRN' and st.mprp = rp.prp)  And st.Grp = 1) " +
            " Select * from stkDtl PIVOT " +
            " ( max(atr) " +
            " for mprp in ( "+mprpStr+" ) ) order by 1 " ;
            
          outLst = db.execSqlLst(" memo pkts", getPktData, params);
           pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);

        while (rs.next()) {
            PktDtl pkt    = new PktDtl();
            long   pktIdn = rs.getLong("mstk_idn");

            pkt.setPktIdn(pktIdn);
            pkt.setSaleId(util.nvl(rs.getString("idn")));
            pkt.setRapRte(util.nvl(rs.getString("raprte")));
            pkt.setQty(util.nvl(rs.getString("qty")));
            pkt.setCts(util.nvl(rs.getString("cts")));
            pkt.setRte(util.nvl(rs.getString("rate")));
            pkt.setMemoQuot(util.nvl(rs.getString("memoQuot")));
            pkt.setByrRte(util.nvl(rs.getString("quot")));
            pkt.setFnlRte(util.nvl(rs.getString("fnl_sal")));
            pkt.setDis(rs.getString("dis"));
            pkt.setByrDis(rs.getString("mDis"));
            pkt.setVnm(util.nvl(rs.getString("vnm")));
            String lStt = rs.getString("stt");

            pkt.setStt(lStt);
            udf.setValue("stt_" + pktIdn, lStt);
            pkt.setValue("rapVlu", (rs.getString("rapVlu")));
            pkt.setValue("rmk", rs.getString("rmk"));
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
                if (vwPrp.toUpperCase().equals("CERT NO."))
                fldName = "certno";
                if(vwPrp.toUpperCase().equals("MEM_COMMENT"))
                fldName = "MEM_COM1";
            String fldVal = util.nvl((String)rs.getString(fldName));
            
            pkt.setValue(vwPrp, fldVal);
            }
            pkts.add(pkt);
        }
        rs.close();
            pst.close();
        req.setAttribute("view", "Y");
        udf.setValue("byrIdn", byrId);
        session.setAttribute("PktList", pkts);
          util.updAccessLog(req,res,"Delivery Return", "Delivery Return load pkt done size "+pkts.size());
            finalizeObject(db, util);
       return load(am, af, req, res);
        }
    }
    public ActionForward RtnPkt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
         synchronized(this){
         ArrayList pktList = (ArrayList)session.getAttribute("PktList");
           if(pktList!=null){
         util.updAccessLog(req,res,"Delivery Return", "Delivery Return rtn pkts size "+pktList.size());
        DeliveryReturnForm udf = (DeliveryReturnForm) af;
        String msg="";
        String msgCan="";
        String msgAV="";
        String msgRv = "";
        String msgHd="";
        ArrayList ary = new ArrayList();
       String brcStkIdnStr="";
        if(pktList!=null && pktList.size()>0){
            for(int i=0; i<pktList.size() ; i++){
               PktDtl pktDtl = (PktDtl)pktList.get(i);
                String dlvIdn = pktDtl.getSaleId();
                String pktIdn = String.valueOf(pktDtl.getPktIdn());
                String vnm = pktDtl.getVnm();
                String isCheckecd = util.nvl((String)udf.getValue("stt_"+pktIdn));
                if(isCheckecd.equals("RT")){
                String rmk = util.nvl((String)udf.getValue("rmk_"+pktIdn));
                ary = new ArrayList();
                ary.add(dlvIdn);
                ary.add(pktIdn);
                ary.add("STK");
                ary.add(rmk);
                int ct = db.execCall("rtnDlv", "DLV_PKG.Rtn_Dlv_Pkt(pDlvIdn => ? , pStkIdn => ? , pTyp =>? , pRem => ?)", ary);
                if(ct>0)
                  msg = msg+","+vnm+"";
               
                  ary = new ArrayList();
                  ary.add(dlvIdn);
                  int upQuery = db.execCall("calQuot", "DLV_PKG.UPD_QTY(pIdn => ? )", ary);

                }else if(isCheckecd.equals("IS")){
                  ary = new ArrayList();
                  ary.add(pktIdn);
                  ary.add("DLV");
                  ary.add(dlvIdn);
                  int ct = db.execCall(" Cancel Pkts ", "DLV_PKG.DLV_TRNS(pStkIdn =>?, pStt => ?,  pDlvIdn =>? )", ary);
                  msgRv = msgRv+","+vnm+"";
                }else if(isCheckecd.equals("HD")){
                    ary = new ArrayList();
                    ary.add(pktIdn);
                    ary.add("HD");
                    ary.add(dlvIdn);
                  int ct = db.execCall(" Hold Pkts ", "DLV_PKG.DLV_TRNS(pStkIdn =>?, pStt => ?,  pDlvIdn =>? )", ary);
                  msgHd = msgHd+","+vnm+"";
                }else if(isCheckecd.equals("CAN")) {
                    ary = new ArrayList();
                    ary.add(dlvIdn);
                    ary.add(pktIdn);
                    int ct = db.execCall(" Cancel Pkts ", "dlv_pkg.Cancel_Pkt( pDlvIdn =>?, pStkIdn=> ?)", ary);
                    msgCan = msgCan+","+vnm+"";
                }else if(isCheckecd.equals("CANBRC")) {
                    ary = new ArrayList();
                    ary.add(dlvIdn);
                    ary.add(pktIdn);
                    int ct = db.execCall(" Cancel Branch Pkts ", "DP_GEN_BRC_CAN(pDlvIdn =>?, pStkIdn=> ?)", ary);
                    msgCan = msgCan+","+vnm+"";
                }else if(isCheckecd.equals("BRCAV")) {
                    ary = new ArrayList();
                    ary.add(pktIdn);
                    ary.add(dlvIdn);
                    
                    ArrayList out = new ArrayList();
                    out.add("I");
                    out.add("V");
                    CallableStatement cts = db.execCall("Branch Pkts ", "DP_GEN_BRANCH_PKT(pStkIdn =>?, pRefIdn=> ?,pBrcStkIdn=>?,pMsg=>?)", ary,out);
                    int brcStkIdn = cts.getInt(ary.size()+1);
                    String brcMsg = cts.getString(ary.size()+2);
                    msgAV = msgAV+","+brcMsg+"";
                    brcStkIdnStr=brcStkIdnStr+","+brcStkIdn;
                }
                
                
                
                
                
                
            }
        }
        msg=msg.replaceFirst("\\,", "");
          msgCan=msgCan.replaceFirst("\\,", "");
          msgRv=msgRv.replaceFirst("\\,", "");
          msgAV=msgAV.replaceFirst("\\,", "");
        if(!msg.equals(""))
        req.setAttribute("msg", "Packets get return : "+msg);
        
        if(!msgCan.equals(""))
        req.setAttribute("msgCan", "Packets get cancel : "+msgCan);
        if(!msgRv.equals(""))
        req.setAttribute("msgRv", " Recevie payment of Packets : "+msgRv);
        
        if(!msgAV.equals("")){
          req.setAttribute("msgAv", msgAV);
          
        }
          
       
        util.updAccessLog(req,res,"Delivery Return", "Delivery Return rtn done");
      }
           finalizeObject(db, util);
     return load(am, af, req, res);
       }
            }
   }
    public DeliveryReturnAction() {
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
                util.updAccessLog(req,res,"Delivery Return", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Delivery Return", "init");
            }
            }
            return rtnPg;
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
}
